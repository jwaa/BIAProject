package drenthwaa.bia.testing;

import drenthwaa.bia.optainet.OAConfig;
import drenthwaa.bia.optainet.OptAinet;
import drenthwaa.bia.testing.data.DataManager;

public class Experiment implements Runnable
{
	private OAConfig config;
	private TestingParameters testingParameters;
	private DataManager dataManager;
	private OptAinet optAinet;
	
	public Experiment(OAConfig config, TestingParameters testParam, DataManager dataManager)
	{
		this.config = config;
		this.testingParameters = testParam;
		this.dataManager = dataManager;
	}
	
	public Experiment(OAConfig config)
	{
		this.config = config;
		testingParameters = new TestingParameters();
	}
	
	public void executeExperiment()
	{
		optAinet = new OptAinet(config, testingParameters, dataManager);
		
		Thread t = new Thread(this);
		t.start();
	}

	@Override
    public void run()
    {
		System.out.println("Running");
		optAinet.optimise();
    }
}
