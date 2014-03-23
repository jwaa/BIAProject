package drenthwaa.bia.testing.cell;

import java.util.ArrayList;
import java.util.Random;
import drenthwaa.bia.optainet.NetworkCell;
import drenthwaa.bia.optainet.experiment.CellGenerator;

/**
 * All other generators must have the getInstance method (like this one, just change the initialisation)
 * and extend CellGenerator.
 */
public class RandomCellGenerator extends CellGenerator
{
	private static final Random random = new Random(); // Random number generator
	
	@Override
    public double[] generateCellLocation(int nDimensions, double[] lowerBounds, double[] upperBounds, ArrayList<NetworkCell> existingCells)
    {
		double[] dimensions = new double[nDimensions];
		
		for (int i = 0; i < nDimensions; i++)
		{
			dimensions[i] = lowerBounds[i] + (upperBounds[i] - lowerBounds[i]) * random.nextDouble();
		}
		
	    return dimensions;
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
