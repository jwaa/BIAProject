package drenthwaa.bia.testing.data;

import drenthwaa.bia.optainet.OptAinet;

public class Result
{
	public OptAinet optAiNet;
	public int nrRuns;
	public float MBF;
	public float MBF_stdev;
	public float AES;
	public float AES_stdev;
	public float SR;
	public float SR_stdev;
	public float averageFitness;
	public float averageFitness_stdev;
	public float totalAverageCells;
	public float totalAverageCells_stdev;
	public float totalAverageGens;
	public float totalAverageGens_stdev;
	
	public void printAll() 
	{
		System.out.println("Results:\n"
				+ "Number Runs = " + nrRuns + "\n"
				+ "MBF = " + MBF + "\n"
				+ "MBF stdev =" + MBF_stdev + "\n"
				+ "AES = " + AES + "\n"
				+ "AES Stdev = " + AES_stdev + "\n"
				+ "SR = " + SR + "\n"
				+ "SR Stdev = " + SR_stdev + "\n"
				+ "Average Fitness = " + averageFitness + "\n"
				+ "Average Fitness Stdev = " + averageFitness_stdev + "\n"
				+ "Total Average Cells = " + totalAverageCells + "\n"
				+ "Total Average Cells Stdev = " + totalAverageCells_stdev + "\n"
				+ "Average Nr Generations = " + totalAverageGens + "\n"
				+ "Average Nr Generations Stdev = " + totalAverageGens_stdev + "\n");
	}

}