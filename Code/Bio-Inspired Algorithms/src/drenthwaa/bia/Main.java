package drenthwaa.bia;

import drenthwaa.bia.optainet.OAConfig;
import drenthwaa.bia.testing.Experiment;
import drenthwaa.bia.testing.TestingParameters;
import drenthwaa.bia.testing.cell.RandomCellGenerator;
import drenthwaa.bia.testing.function.ExampleFunction;

public class Main
{
	public static void main(String[] args)
	{
		OAConfig config = OAConfig.readConfigFile("OptAinet_config.txt");
		
		TestingParameters testParam = new TestingParameters();
		testParam.affinityMeasure = TestingParameters.AFFINITY_EUCLIDEAN;
		testParam.optimisationFunction = ExampleFunction.getInstance();
		testParam.cellGenerator = RandomCellGenerator.getInstance();
		
		Experiment e = new Experiment(config, testParam);
		e.executeExperiment();
		
		/*
		 * To demonstrate multi-threaded capabilities
		Experiment e2 = new Experiment(config, testParam);
		e2.executeExperiment();
		*/
	}
}
