package drenthwaa.bia;

import java.util.ArrayList;
import drenthwaa.bia.optainet.experiment.OptimisationFunction;
import drenthwaa.bia.testing.Experiment;
import drenthwaa.bia.testing.TestingParameters;
import drenthwaa.bia.testing.cell.DistanceCellGenerator;
import drenthwaa.bia.testing.cell.HistogramCellGenerator;
import drenthwaa.bia.testing.cell.RandomCellGenerator;
import drenthwaa.bia.testing.data.Analyzer;
import drenthwaa.bia.testing.data.DataManager;
import drenthwaa.bia.testing.data.Result;
import drenthwaa.bia.testing.function.MultiFunction;
import drenthwaa.bia.testing.function.RootsFunction;
import drenthwaa.bia.testing.function.SchafferFunction;

public class Main
{
	public static void main(String[] args)
	{
		// Example:
		/*
		 * TestingParameters basicParam = new TestingParameters();
		 * basicParam.maxNrRuns = 100; basicParam.affinityMeasure =
		 * TestingParameters.AFFINITY_EUCLIDEAN; basicParam.optimisationFunction
		 * = ExampleFunction.getInstance(); basicParam.cellGenerator =
		 * RandomCellGenerator.getInstance();
		 */

		int nrRuns = 100;
		boolean withExperimentalVariation = true;

		ArrayList<TestingParameters> allParameters = createTestingParams(nrRuns, withExperimentalVariation);
		System.out.println("Main.main() - NR of ExperimentalParameters set: " + allParameters.size());
		for (int p = 0; p < allParameters.size(); p++)
		{
			TestingParameters param = allParameters.get(p);
			System.out.println(p + "\t" + param.name);

			DataManager dataManager = new DataManager(param);
			Thread[] experiments = new Thread[param.maxNrRuns];

			// System.out.println(param.maxNrRuns);

			for (int run = 0; run < param.maxNrRuns; run++)
			{
				Experiment basicExperiment = new Experiment(dataManager, param);
				experiments[run] = basicExperiment.executeExperiment();
			}

			// Wait for all threads to stop
			for (int run = 0; run < param.maxNrRuns; run++)
			{
				try
				{
					experiments[run].join();
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			
			// Analyze the results and print/write them
			analyze(dataManager, 0, param.name);
		}
		System.out.println("Main.main() - Finished with " + allParameters.size() + " experiments.");
	}

	private static void analyze(DataManager dataManager, int attempt, String experimentName)
	{
		// try
		// {
		Result result = new Analyzer(dataManager).analyze();
		result.printAll(); // or write?*/
		result.writeAll();
		
		String resultName = result.name;
		dataManager.writeFinalCellPopulation(resultName);
		
		/*
		 * } catch ( Exception e) {
		 * 
		 * analyze(dataManager, attempt++); }
		 */

	}

	private static ArrayList<TestingParameters> createTestingParams(int nrRuns, boolean withExperimentalVariation)
	{
		ArrayList<TestingParameters> list = new ArrayList<TestingParameters>();

		// our variation parameters
		int[] list_affMeasure = { 0, 1, 2 };
		int[] list_generators = { 0, 1, 2 };
		ArrayList<OptimisationFunction> list_functions = new ArrayList<>();
		list_functions.add(new MultiFunction());
		list_functions.add(new RootsFunction());
		//list_functions.add(new SchafferFunction());

		// setting function dependent parameters
		int[] list_numDims = { 2, 2, 2 };
		double[][] list_lowerBounds = { { -2.0, -2.0 }, { -2.0, -2.0 }, { -10.0, -10.0 } };
		double[][] list_upperBounds = { { 2.0, 2.0 }, { 2.0, 2.0 }, { 10.0, 10.0 } };

		// all the for-loops for iterating through these

		for (int i_affMeasure = 0; i_affMeasure < list_affMeasure.length; i_affMeasure++)
		{
			for (int i_generator = 0; i_generator < list_generators.length; i_generator++)
			{
				for (int i_function = 0; i_function < list_functions.size(); i_function++)
				{
					TestingParameters params = new TestingParameters();
					params.maxNrRuns = nrRuns;
					params.affinityMeasure = list_affMeasure[i_affMeasure];
					params.generatorType = list_generators[i_generator];
					switch (params.generatorType)
					{
						case TestingParameters.GENERATOR_HISTOGRAM:
							params.cellGenerator = HistogramCellGenerator.getInstance();
						break;
						case TestingParameters.GENERATOR_DISTANCE:
							params.cellGenerator = DistanceCellGenerator.getInstance();
						break;
						case TestingParameters.GENERATOR_RANDOM:
							params.cellGenerator = RandomCellGenerator.getInstance();
						break;
						default:
							System.out.println("Main.createTestingParameters() - ERROR: Unkown Cell Generator type.");
							System.exit(-1);
						break;
					}

					params.optimisationFunction = list_functions.get(i_function);
					params.numDims = list_numDims[i_function];
					params.lowerBounds = list_lowerBounds[i_function];
					params.upperBounds = list_upperBounds[i_function];

					// Do with experimental variation
					if(withExperimentalVariation)
						list.addAll(variateExperimentalSettings(params));
					else
					{
						params.name = createName(params);
						list.add(params);
					}
				}
			}
		}

		return list;
	}

	private static ArrayList<TestingParameters> variateExperimentalSettings(TestingParameters param)
	{
		ArrayList<TestingParameters> list = new ArrayList<>();
		int c = 0;
		// Experimental variation parameters
		int[] list_numInitCells = {20};//{ 10, 20 };
		int[] list_numClones = {10};//{ 5, 10, 15 };
		int[] list_maxIter = { 500, 1000 };
		double[] list_suppThres = { 0.1, 0.2, 0.3 };
		double[] list_errorThres = { 0.0005, 0.001, 0.0015 };
		double[] list_divRatio = { 0.3, 0.4, 0.5 };
		double[] list_mutnParam = {100};//{ 50, 100, 150 };

		for (int i_numInitCells = 0; i_numInitCells < list_numInitCells.length; i_numInitCells++)
		{
			for (int i_numClones = 0; i_numClones < list_numClones.length; i_numClones++)
			{
				for (int i_maxIter = 0; i_maxIter < list_maxIter.length; i_maxIter++)
				{
					for (int i_suppThres = 0; i_suppThres < list_suppThres.length; i_suppThres++)
					{
						for (int i_errorThres = 0; i_errorThres < list_errorThres.length; i_errorThres++)
						{
							for (int i_divRatio = 0; i_divRatio < list_divRatio.length; i_divRatio++)
							{
								for (int i_mutnclone = 0; i_mutnclone < list_mutnParam.length; i_mutnclone++)
								{
									c++;
									TestingParameters clone = new TestingParameters();
									clone.maxNrRuns = param.maxNrRuns;
									clone.affinityMeasure = param.affinityMeasure;
									clone.generatorType = param.generatorType;
									clone.cellGenerator = param.cellGenerator;
									clone.optimisationFunction = param.optimisationFunction;
									clone.numDims = param.numDims;
									clone.lowerBounds = param.lowerBounds;
									clone.upperBounds = param.upperBounds;
									clone.numInitCells = list_numInitCells[i_numInitCells];
									clone.numClones = list_numClones[i_numClones];
									clone.maxIter = list_maxIter[i_maxIter];
									clone.suppThres = list_suppThres[i_suppThres];
									clone.errorThres = list_errorThres[i_errorThres];
									clone.divRatio = list_divRatio[i_divRatio];
									clone.mutnParam = list_mutnParam[i_mutnclone];
									clone.name = createName(clone);
									list.add(clone);
								}
							}
						}
					}
				}
			}
		}
		return list;
	}

	private static String createName(TestingParameters param)
	{
		String name = "[f:" + param.optimisationFunction.getName() + "]";
		name = name + "[affM:" + param.affinityMeasure + "]";
		name = name + "[Cellg:" + param.generatorType + "]";
		name = name + "[runs:" + param.maxNrRuns + "]";
		name = name + "_[iter:" + param.maxIter + "]";
		name = name + "[initC:" + param.numInitCells + "]";
		name = name + "[numCl:" + param.numClones + "]";
		name = name + "[suppT:" + param.suppThres + "]";
		name = name + "[errT:" + param.errorThres + "]";
		name = name + "[divR:" + param.divRatio + "]";
		name = name + "[mutn:" + param.mutnParam + "]";
		return name;
	}

}
