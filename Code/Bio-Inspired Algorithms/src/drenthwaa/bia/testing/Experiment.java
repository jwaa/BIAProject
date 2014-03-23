package drenthwaa.bia.testing;

import drenthwaa.bia.optainet.OAConfig;
import drenthwaa.bia.optainet.OptAinet;

public class Experiment implements Runnable
{
	private OAConfig config;
	private TestingParameters testingParameters;
	private OptAinet optAinet;
	
	public Experiment(OAConfig config, TestingParameters testParam)
	{
		this.config = config;
		this.testingParameters = testParam;
	}
	
	public Experiment(OAConfig config)
	{
		this.config = config;
		testingParameters = new TestingParameters();
	}
	
	public void executeExperiment()
	{
		optAinet = new OptAinet(config, testingParameters);
		
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
