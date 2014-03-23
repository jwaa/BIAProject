package drenthwaa.bia.optainet.experiment;

import java.util.ArrayList;
import drenthwaa.bia.optainet.NetworkCell;

public abstract class CellGenerator
{
	protected static CellGenerator cg;
	
	public abstract double[] generateCellLocation(int nDimensions, double[] lowerBounds, double[] upperBounds, ArrayList<NetworkCell> existingCells);
}
