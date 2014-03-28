package drenthwaa.bia.testing.cell;

import java.util.ArrayList;
import java.util.Random;
import drenthwaa.bia.optainet.NetworkCell;
import drenthwaa.bia.optainet.experiment.CellGenerator;
import drenthwaa.bia.testing.TestingParameters;

public class HistogramCellGenerator extends CellGenerator
{
	private static final Random random = new Random(); // Random number generator
	protected static HistogramCellGenerator cg;
	
	public static int maxBins = 25;
	
	@Override
    public void generateCells(int numCells, TestingParameters parameters, ArrayList<NetworkCell> existingCells)
    {
		//System.out.println(" generating");
		
	    int nDimensions = parameters.numDims;
	    int nExistingCells = existingCells.size();
	    double rootNumber = 1.0/nDimensions;

	    // calculate the number of bins
	    int nBins = 0;
	    if(nExistingCells != 0)
	    {
	    	nBins = (int) Math.pow(nExistingCells, rootNumber);
	    }
	    else
	    {
	    	nBins = 1;
	    }

	    // make sure that the search space does not get too defined.
	    if(nBins > maxBins)
	    {
	    	nBins = maxBins;
	    }
	    
	    // calculating bin sizes per dimension
	    double[] binSizes = new double[nDimensions];
	    for(int i=0; i<nDimensions; i++)
	    {
	    	binSizes[i] = (parameters.upperBounds[i] - parameters.lowerBounds[i])/nBins;
	    }
	    
	    // creating the multi-dimensional histogram. 
	    int nTotalBins = (int) Math.pow(nBins, nDimensions);
	    int[] histogram = new int[nTotalBins];
	    
	    // filling the histogram
	    for(NetworkCell cell : existingCells)
	    {
	    	int[] binNumbers = new int[nDimensions];
	    	
	    	for(int i=0; i<nDimensions; i++)
	    	{
	    		binNumbers[i] = (int) (cell.getDimension(i) / binSizes[i]);
	    	}
	    	
	    	int totalBinNumber = 0;
	    	for(int i=0; i<nDimensions; i++)
	    	{
	    		totalBinNumber = binNumbers[i] + i*nBins;
	    	}
	    	
	    	histogram[totalBinNumber]++;
	    }
	    
	    // finding the max and min numbers in the histogram
	    int maxNumber = -1;
	    int minNumber = 999999;
	    for(int i=0; i<nTotalBins; i++)
	    {
	    	if(histogram[i] > maxNumber)
	    	{
	    		maxNumber = histogram[i];
	    	}
	    	if(histogram[i] < minNumber)
	    	{
	    		minNumber = histogram[i];
	    	}
	    }
	    
	    // generating new cells
	    // First loop over the histogram and generate a new cell if there are no cells present yet to
	    // guarantee those bins get covered too.
	    // After that, loop over all the other cells and use probability. 
	    if(minNumber == 0) // only if there are empty bins. otherwise, use probability.
	    {
		    for(int i=0; i<nTotalBins && numCells > 0; i++)
		    {
		    	if(histogram[i] == minNumber)
		    	{
		    		int binNumbers[] = new int[nDimensions];
		    		for(int j=0; j<nDimensions; j++)
		    		{
		    			binNumbers[j] = (int) ((i / (Math.pow(nBins, j))) % nBins);
		    		}
		    		
		    		// generate new dimensions using bin numbers
		    		double[] cellDimensions = new double[nDimensions];
		    		
		    		for(int j=0; j<nDimensions; j++)
		    		{
		    			cellDimensions[j] = binNumbers[j] * binSizes[j] + (Math.random() * binSizes[j]);
		    		}
		    		
		    		NetworkCell cell = new NetworkCell(cellDimensions, parameters);
		    		existingCells.add(cell);
		    		numCells--;
		    		histogram[i]++;
		    	}
		    }
	    }
	    
	    // add things randomly, proportionate to the amount of present cells
		while(numCells > 0)
		{
			int randomI = random.nextInt(histogram.length);
			double chance = 1;
			if(maxNumber > 0)
			{
				chance = 1 - ((histogram[randomI] - minNumber) / maxNumber);
			}
			
			if(random.nextDouble() <= chance)
			{
				int binNumbers[] = new int[nDimensions];
	    		for(int j=0; j<nDimensions; j++)
	    		{
	    			binNumbers[j] = (int) ((randomI / (Math.pow(nBins, j))) % nBins);
	    		}
	    		
	    		// generate new dimensions using bin numbers
	    		double[] cellDimensions = new double[nDimensions];
	    		
	    		for(int j=0; j<nDimensions; j++)
	    		{
	    			cellDimensions[j] = binNumbers[j] * binSizes[j] + (Math.random() * binSizes[j]);
	    		}
	    		
	    		NetworkCell cell = new NetworkCell(cellDimensions, parameters);
	    		existingCells.add(cell);
	    		
	    		histogram[randomI]++;
				numCells--;
			}
		}
    }
	
	public synchronized static CellGenerator getInstance()
	{
		if(cg == null)
		{
			cg = new HistogramCellGenerator();
		}
		return cg;
	}
}
