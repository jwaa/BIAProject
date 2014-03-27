package drenthwaa.bia.testing.cell;

import java.util.ArrayList;
import java.util.Random;
import drenthwaa.bia.optainet.NetworkCell;
import drenthwaa.bia.optainet.experiment.CellGenerator;
import drenthwaa.bia.testing.TestingParameters;

/**
 * All other generators must have the getInstance method (like this one, just change the initialisation)
 * and extend CellGenerator.
 */
public class RandomCellGenerator extends CellGenerator
{
	private static final Random random = new Random(); // Random number generator
	protected static RandomCellGenerator cg;
	
	@Override
    public void generateCells(int numCells, TestingParameters parameters, ArrayList<NetworkCell> existingCells)
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
	
	public synchronized static CellGenerator getInstance()
	{
		if(cg == null)
		{
			cg = new RandomCellGenerator();
		}
		return cg;
	}
}
