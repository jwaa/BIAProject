package drenthwaa.bia.testing.data;

import drenthwaa.bia.optainet.OptAinet;
import drenthwaa.bia.testing.TestingParameters;

public class Result
{
	public OptAinet optAiNet;
	public String name;
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
	private TestingParameters masterParameters;
	
	public Result(TestingParameters masterParameters)
	{
		this.masterParameters = masterParameters;
		name = createName();
	}
	
	public void printAll() 
	{
		System.out.println("Results:\n"
				+ "Network Name: " + name + "\n"
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

	public void writeAll() 
	{
		SimpleFileWriter writer = new SimpleFileWriter(name + ".csv", false);
		String attributes = "Name," + "Nr Runs," + "MBF," + "MBF stdev," + "AES," + "AES stdev," + "SR," + "SR stdev," + "Av Fitness," + "Av Fitness stedv," + "Tot Av Cells," + "Tot Av Cells stdev," + "Av Nr Gens," + "Av Nr Gens stdev";   
		String data = name + "," + nrRuns + "," + MBF + "," + MBF_stdev + "," + AES + "," + AES_stdev + "," + SR + "," + SR_stdev + "," + averageFitness + "," + averageFitness_stdev + "," + totalAverageCells + "," + totalAverageCells_stdev + "," + totalAverageGens + "," + totalAverageGens_stdev;
		
		writer.processLine(attributes);
		writer.processLine(data);
		writer.wrapUp();
	}
	
	public void writeAll(String fileName) 
	{
		SimpleFileWriter writer = new SimpleFileWriter(fileName, false);

		String attributes = "Name," + "Nr Runs," + "MBF," + "MBF stdev," + "AES," + "AES stdev," + "SR," + "SR stdev," + "Av Fitness," + "Av Fitness stedv," + "Tot Av Cells," + "Tot Av Cells stdev," + "Av Nr Gens," + "Av Nr Gens stdev";   
		String data = name + "," + nrRuns + "," + MBF + "," + MBF_stdev + "," + AES + "," + AES_stdev + "," + SR + "," + SR_stdev + "," + averageFitness + "," + averageFitness_stdev + "," + totalAverageCells + "," + totalAverageCells_stdev + "," + totalAverageGens + "," + totalAverageGens_stdev;
		
		writer.processLine(attributes);
		writer.processLine(data);
		writer.wrapUp();
	}
	
	private String createName()
	{
		String name = "[f_" + masterParameters.optimisationFunction.getName() + "]";
		name = name + "[affM_" + masterParameters.affinityMeasure + "]";
		name = name + "[Cellg_" + masterParameters.generatorType + "]";
		return name;
	}

}