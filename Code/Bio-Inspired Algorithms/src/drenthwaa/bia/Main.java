package drenthwaa.bia;

import drenthwaa.bia.optainet.OAConfig;
import drenthwaa.bia.testing.Experiment;
import drenthwaa.bia.testing.TestingParameters;
import drenthwaa.bia.testing.cell.RandomCellGenerator;
import drenthwaa.bia.testing.data.DataManager;
import drenthwaa.bia.testing.function.ExampleFunction;

public class Main
{
	public static void main(String[] args)
	{
		OAConfig config = OAConfig.readConfigFile("OptAinet_config.txt");
		
		TestingParameters basicParam = new TestingParameters();
		DataManager dataManager = new DataManager(basicParam);
		basicParam.affinityMeasure = TestingParameters.AFFINITY_EUCLIDEAN;
		basicParam.optimisationFunction = ExampleFunction.getInstance();
		basicParam.cellGenerator = RandomCellGenerator.getInstance();
		
		Experiment basicExperiment = new Experiment(config, basicParam, dataManager);
		basicExperiment.executeExperiment();
		
		/*
		 * To demonstrate multi-threaded capabilities
		Experiment e2 = new Experiment(config, testParam);
		e2.executeExperiment();
		*/
	}
}
