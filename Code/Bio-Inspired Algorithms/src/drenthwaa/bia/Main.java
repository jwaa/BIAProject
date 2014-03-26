package drenthwaa.bia;

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
