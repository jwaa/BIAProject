package drenthwaa.bia.optainet.experiment;

import java.util.ArrayList;
import drenthwaa.bia.optainet.NetworkCell;
import drenthwaa.bia.testing.TestingParameters;

public abstract class CellGenerator
{
	/**
	 * Generates numCells new cells and adds them to existingCells.
	 * @param numCells
	 * @param parameters
	 * @param existingCells
	 */
	public abstract void generateCells(int numCells, TestingParameters parameters, ArrayList<NetworkCell> existingCells);
}
