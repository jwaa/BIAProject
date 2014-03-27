package drenthwaa.bia;

import drenthwaa.bia.optainet.AffinityCalculator;
import drenthwaa.bia.optainet.OAConfig;
import drenthwaa.bia.testing.Experiment;
import drenthwaa.bia.testing.TestingParameters;
import drenthwaa.bia.testing.cell.RandomCellGenerator;
import drenthwaa.bia.testing.data.Analyzer;
import drenthwaa.bia.testing.data.DataManager;
import drenthwaa.bia.testing.data.Result;
import drenthwaa.bia.testing.function.ExampleFunction;

public class Main
{
	public static void main(String[] args)
	{
		double[] d1 = {1.0, 0.5};
		double[] d2 = {0.5, 1.0};
		
		System.out.println(AffinityCalculator.calculateAffinity(d1, d2, TestingParameters.AFFINITY_COSINE));
		
		System.exit(0);
		
		/*TestingParameters test = new TestingParameters();
		test.numDims = 2;
		double[] lowerBounds = {0, 0};
		double[] upperBounds = {3, 3};
		test.upperBounds = upperBounds;
		test.lowerBounds = lowerBounds;
		
		ArrayList<NetworkCell> cells = new ArrayList<NetworkCell>();
		
		for(int i=0; i<9; i++)
		{
			double[] dimensionValues = {i*0.2, i*0.2};
			System.out.println("started: " + Arrays.toString(dimensionValues));
			NetworkCell cell = new NetworkCell(dimensionValues, test);
			cells.add(cell);
		}
		
		//CellGenerator hcg = HistogramCellGenerator().getInstance();
		CellGenerator hcg = RandomCellGenerator.getInstance();
		hcg.generateCells(9, test, cells);
		
		System.exit(0);*/
		
		OAConfig config = OAConfig.readConfigFile("OptAinet_config.txt");
		
		TestingParameters basicParam = new TestingParameters();
		basicParam.maxNrRuns = 100;
		basicParam.affinityMeasure = TestingParameters.AFFINITY_EUCLIDEAN;
		basicParam.optimisationFunction = ExampleFunction.getInstance();
		basicParam.cellGenerator = RandomCellGenerator.getInstance();
		
		
		DataManager dataManager = new DataManager(basicParam);
		Thread[] experiments = new Thread[basicParam.maxNrRuns];
		@SuppressWarnings("unused")
        boolean isDone;
		for(int run = 0; run < basicParam.maxNrRuns; run++)
		{
			Experiment basicExperiment = new Experiment(config, basicParam, dataManager);
			experiments[run] = basicExperiment.executeExperiment();
		}
		
		for(int run = 0; run < basicParam.maxNrRuns; run++)
		{
			try {
				experiments[run].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Result result = new Analyzer(dataManager).analyze();
		result.printAll();
		
		/*
		 * To demonstrate multi-threaded capabilities
		Experiment e2 = new Experiment(config, testParam);
		e2.executeExperiment();
		*/
	}
}
