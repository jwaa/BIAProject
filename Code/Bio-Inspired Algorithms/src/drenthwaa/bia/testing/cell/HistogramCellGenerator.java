package drenthwaa.bia.testing.cell;

import java.util.ArrayList;
import java.util.Random;
import drenthwaa.bia.optainet.NetworkCell;
import drenthwaa.bia.optainet.experiment.CellGenerator;
import drenthwaa.bia.testing.TestingParameters;

public class HistogramCellGenerator extends CellGenerator
{
	private static final Random random = new Random(); // Random number generator
	
	@Override
    public void generateCells(int numCells, TestingParameters parameters, ArrayList<NetworkCell> existingCells)
    {
	    
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
