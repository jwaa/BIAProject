package drenthwaa.bia.testing.cell;

import java.util.ArrayList;
import java.util.Random;

import drenthwaa.bia.optainet.AffinityCalculator;
import drenthwaa.bia.optainet.NetworkCell;
import drenthwaa.bia.optainet.experiment.CellGenerator;
import drenthwaa.bia.testing.TestingParameters;

public class DistanceCellGenerator extends CellGenerator
{
	private static final Random random = new Random(); // Random number generator
	private static double affThreshold;
	private static double affDecay;
	private static final int maxAttempts = 100000;
	protected static DistanceCellGenerator cg;
	
	
	@Override
    public void generateCells(int numCells, TestingParameters parameters, ArrayList<NetworkCell> existingCells)
    {	
		if(!existingCells.isEmpty()) // add a bit smarter (based on affinities)
		{
			affThreshold = calcThreshold(existingCells, parameters);
			affDecay = calcDecay(existingCells, parameters);
			int nrAttempts = 0;
			for(int j=0; j<numCells; j++)
			{
				double[] dimensions = new double[parameters.numDims];
				
				for (int i = 0; i < parameters.numDims; i++)
				{
					dimensions[i] = parameters.lowerBounds[i] + (parameters.upperBounds[i] - parameters.lowerBounds[i]) * random.nextDouble();
				}
				NetworkCell cell = new NetworkCell(dimensions, parameters);
				if(isAllowed(cell, existingCells, parameters))
				{
					existingCells.add(cell);
					nrAttempts = 0;
				}
				else
				{
					j--;
					nrAttempts += 1;
					if(nrAttempts >= maxAttempts)
					{
						affThreshold = affThreshold + affDecay;
					}
				}
			}
		}
		else // add randomly
		{
			for(int j=0; j<numCells; j++)
			{
				double[] dimensions = new double[parameters.numDims];
				
				for (int i = 0; i < parameters.numDims; i++)
				{
					dimensions[i] = parameters.lowerBounds[i] + (parameters.upperBounds[i] - parameters.lowerBounds[i]) * random.nextDouble();
				}
				
				existingCells.add(new NetworkCell(dimensions, parameters));
			}
		}
		
    }
	
	private double calcDecay(ArrayList<NetworkCell> cellList, TestingParameters params)
	{
		double stdev = calcStdev(cellList, params);
		double decay = 0;
		
		if(maxAttempts*stdev > 10)
			decay = (1-1/Math.log(maxAttempts*stdev)) *stdev; 
		else
			decay = (1-1/Math.log(10)) *stdev;
		return decay;
	}
	
	private double calcThreshold(ArrayList<NetworkCell> cellList, TestingParameters params)
	{
		double mean = calcMean(cellList, params);
		double stdev = calcStdev(cellList, params);
		double threshold = 0;

		threshold = mean-(stdev/mean);
	
		return threshold;
	}
	
	
	private boolean isAllowed(NetworkCell current, ArrayList<NetworkCell> existingCells, TestingParameters parameters) 
	{
		double meanAffWithRest = calcMean(current, existingCells, parameters);
		if(meanAffWithRest > affThreshold)
		{
			return false;
		}
		return true;
	}
	
	private double calcStdev(ArrayList<NetworkCell> cellList, TestingParameters params) 
	{
		double stdev = 0.0;
		int nrAffs = 0;
		for (int i = 0; i < cellList.size(); i++)
		{
			NetworkCell cell1 = cellList.get(i);
			for (int j = 0; j < cellList.size(); j++)
			{
				
				if(j > i)
				{
					NetworkCell cell2 = cellList.get(j);
					double affinity = AffinityCalculator.calculateAffinity(cell1.getDimensions(), cell2.getDimensions(), params.affinityMeasure);
					stdev += Math.pow(affinity - affThreshold, 2);
					nrAffs += 1;
				}
			}
		}

		stdev = (float) (Math.sqrt(stdev / nrAffs));

		return stdev;
	}

	private double calcMean(ArrayList<NetworkCell> cellList, TestingParameters params) 
	{
		double aff = 0.0;
		int nrAffs = 0;
		for (int i = 0; i < cellList.size(); i++)
		{
			NetworkCell cell1 = cellList.get(i);
			for (int j = 0; j < cellList.size(); j++)
			{
				if(j > i)
				{
					NetworkCell cell2 = cellList.get(j);
					aff += AffinityCalculator.calculateAffinity(cell1.getDimensions(), cell2.getDimensions(), params.affinityMeasure);
					nrAffs += 1;
				}
			}
		}

		return aff/nrAffs;
	}
	
	private double calcMean(NetworkCell cell, ArrayList<NetworkCell> cellList, TestingParameters params) 
	{
		double aff = 0.0;
		int nrAffs = 0;
		int index = cellList.indexOf(cell);
		
		for (int i = 0; i < cellList.size(); i++)
		{
			NetworkCell cell1 = cellList.get(i);
			if(index != -1 && index != i)
			{
				aff += AffinityCalculator.calculateAffinity(cell.getDimensions(), cell1.getDimensions(), params.affinityMeasure);
				nrAffs += 1;
			}
			else if(index == -1)
			{
				aff += AffinityCalculator.calculateAffinity(cell.getDimensions(), cell1.getDimensions(), params.affinityMeasure);
				nrAffs += 1;
			}
		}
		return aff/nrAffs;
	}


	public synchronized static CellGenerator getInstance()
	{
		if(cg == null)
		{
			cg = new DistanceCellGenerator();
		}
		return cg;
	}
}
