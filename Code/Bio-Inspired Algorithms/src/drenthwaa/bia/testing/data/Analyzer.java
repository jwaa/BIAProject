package drenthwaa.bia.testing.data;

import java.util.ArrayList;
import drenthwaa.bia.optainet.NetworkCell;
import drenthwaa.bia.optainet.OptAinet;
import drenthwaa.bia.testing.TestingParameters;

public class Analyzer
{
	private boolean isSuppressErrors = false;

	private TestingParameters masterParameters;
	private ArrayList<OptAinet> ranNetworks;
	private ArrayList<ArrayList<NetworkCell>> rawFinalGenerationData;
	private ArrayList<ArrayList<ArrayList<NetworkCell>>> rawGenerationData;

	public Analyzer(DataManager dataManager)
	{
		masterParameters = dataManager.getMasterParameters();
		new ArrayList<ArrayList<Result>>();
		ranNetworks = dataManager.getRanNetworks();
		rawGenerationData = dataManager.getAllRawGenerationData();
		rawFinalGenerationData = dataManager.getAllRawFinalGenerationData();
	}

	public Result analyze()
	{
		checkNetworks();
		Result result = new Result();
		result.nrRuns = ranNetworks.size();
		result = calculateAverageFitness(result);
		result = calculateTotalAverageNrCells(result);
		result = calculateMBF(result);
		result = calculateAES(result);
		result = calculateSR(result);
		return result;
	}

	private Result calculateSR(Result result)
	{
		ArrayList<Integer> dataPoints = new ArrayList<>();
		float SR = 0f;
		float SR_stdev = 0f;
		int N = 0;

		for (int run = 0; run < rawGenerationData.size(); run++)
		{
			for (int generation = 0; generation < rawGenerationData.get(run).size(); generation++)
			{
				for (int cell = 0; cell < rawGenerationData.get(run).size(); cell++)
				{
					NetworkCell current = rawGenerationData.get(run).get(generation).get(cell);
					if(optimumCheck(current))
					{
						SR += 1;
						dataPoints.add(1);
						N++;
					}
				}
			}
		}
		result.SR = SR;

		for (int i = 0; i < dataPoints.size(); i++)
		{
			SR_stdev += Math.pow(dataPoints.get(i) - result.SR, 2);
		}
		result.SR_stdev = (float) (Math.sqrt(SR_stdev / N));

		return result;
	}

	private Result calculateMBF(Result result)
	{
		ArrayList<Float> dataPoints = new ArrayList<>();
		float MBF = 0f;
		float MBF_stdev = 0f;
		int N = 0;

		for (int run = 0; run < rawGenerationData.size(); run++)
		{
			float bestFitness = 0f;
			for (int generation = 0; generation < rawGenerationData.get(run).size(); generation++)
			{
				for (int cell = 0; cell < rawGenerationData.get(run).size(); cell++)
				{
					NetworkCell current = rawGenerationData.get(run).get(generation).get(cell);
					if(current.getFitnessNorm() > bestFitness)
					{
						bestFitness = (float) current.getFitnessNorm();
					}
				}
			}
			MBF += bestFitness;
			dataPoints.add(bestFitness);
			N++;
		}
		result.MBF = MBF / N;

		for (int i = 0; i < dataPoints.size(); i++)
		{
			float dataPoint = dataPoints.get(i);
			MBF_stdev += Math.pow(dataPoint - result.MBF, 2);
		}
		result.MBF_stdev = (float) (Math.sqrt(MBF_stdev / N));

		return result;
	}

	private Result calculateAES(Result result)
	{
		ArrayList<Integer> dataPoints = new ArrayList<>();
		float AES = 0f;
		float AES_stdev = 0f;
		int N = 0;

		for (int run = 0; run < rawGenerationData.size(); run++)
		{
			for (int generation = 0; generation < rawGenerationData.get(run).size(); generation++)
			{
				for (int cell = 0; cell < rawGenerationData.get(run).size(); cell++)
				{
					NetworkCell current = rawGenerationData.get(run).get(generation).get(cell);
					if(optimumCheck(current))
					{
						AES += rawGenerationData.get(run).size();
						dataPoints.add(rawGenerationData.get(run).size());
						N++;
					}
				}
			}
		}
		result.AES = AES / N;

		for (int i = 0; i < dataPoints.size(); i++)
		{
			int dataPoint = dataPoints.get(i);
			AES_stdev += Math.pow((dataPoint - result.AES), 2);
		}
		result.AES_stdev = (float) (Math.sqrt(AES_stdev / N));

		return result;
	}

	private boolean optimumCheck(NetworkCell cell)
	{
		double[] optimum = masterParameters.optimisationFunction.getOptimum();
		double optimumBound = masterParameters.optimisationFunction.getOptimumBound();
		for (int i = 0; i < optimum.length; i++)
		{
			if((cell.getDimension(i) > (optimum[i] + optimumBound)) || (cell.getDimension(i) < (optimum[i] - optimumBound)))
			{
				return false;
			}
		}

		return true;
	}

	private Result calculateTotalAverageNrCells(Result result)
	{
		ArrayList<Integer> dataPoints_cells = new ArrayList<>();
		ArrayList<Integer> dataPoints_gens = new ArrayList<>();
		float nrCells = 0f;
		float nrGenerations = 0f;
		float nrCells_stdev = 0f;
		float nrGens_stdev = 0f;
		int N_cells = 0;
		int N_gens = 0;

		for (int Run = 0; Run < rawGenerationData.size(); Run++)
		{
			nrGenerations += rawGenerationData.size();
			dataPoints_gens.add(rawGenerationData.size());
			N_gens++;
			for (int generation = 0; generation < rawGenerationData.get(Run).size(); generation++)
			{
				nrCells += rawGenerationData.get(Run).get(generation).size();
				dataPoints_cells.add(rawGenerationData.get(Run).get(generation).size());
				N_cells++;
			}
		}
		result.totalAverageCells = nrCells / N_cells;
		result.totalAverageGens = nrGenerations / N_gens;

		for (int i = 0; i < dataPoints_cells.size(); i++)
		{
			int dataPoint = dataPoints_cells.get(i);
			nrCells_stdev += Math.pow(dataPoint - result.totalAverageCells, 2);
		}
		for (int i = 0; i < dataPoints_gens.size(); i++)
		{
			int dataPoint = dataPoints_gens.get(i);
			nrGens_stdev += Math.pow(dataPoint - result.totalAverageGens, 2);
		}
		result.totalAverageCells_stdev = (float) (Math.sqrt(nrCells_stdev / N_cells));
		result.totalAverageGens_stdev = (float) (Math.sqrt(nrGens_stdev / N_gens));
		return result;
	}

	private Result calculateAverageFitness(Result result)
	{
		ArrayList<Double> dataPoints = new ArrayList<>();
		float fitness = 0f;
		float fitness_stdev = 0f;
		int N = 0;

		for (int Run = 0; Run < rawGenerationData.size(); Run++)
		{
			for (int generation = 0; generation < rawGenerationData.get(Run).size(); generation++)
			{
				for (int cell = 0; cell < rawGenerationData.get(Run).get(generation).size(); cell++)
				{
					fitness += rawGenerationData.get(Run).get(generation).get(cell).getFitnessNorm();
					dataPoints.add(rawGenerationData.get(Run).get(generation).get(cell).getFitnessNorm());
					N++;
				}
			}
		}
		result.averageFitness = fitness / N;

		for (int i = 0; i < dataPoints.size(); i++)
		{
			Double dataPoint = dataPoints.get(i);
			fitness_stdev += Math.pow(dataPoint - result.averageFitness, 2);
		}
		result.averageFitness_stdev = (float) (Math.sqrt(fitness_stdev / N));

		return result;
	}

	private void checkNetworks()
	{
		if(rawFinalGenerationData.size() != ranNetworks.size() && rawGenerationData.size() != ranNetworks.size() && !isSuppressErrors)
		{
			System.err.println("Analyzer.checkNetworks() - ERROR: Analyzer found inconsistencies in the received data and number of networks tested (number of runs).");
			System.exit(-1);
		}
		for (int i = 0; i < ranNetworks.size(); i++)
		{
			if(!ranNetworks.get(i).getTestingParameters().equals(masterParameters) && !isSuppressErrors)
			{
				System.err.println("Analyzer.checkNetworks() - ERROR: Analyzer found inconsistencies in the received parameters of network indexed by " + i + " and the master parameters.");
				System.exit(-1);
			}
		}
	}
}
