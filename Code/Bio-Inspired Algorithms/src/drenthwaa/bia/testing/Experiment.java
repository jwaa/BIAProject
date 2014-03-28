package drenthwaa.bia.testing;

import drenthwaa.bia.optainet.OAConfig;
import drenthwaa.bia.optainet.OptAinet;
import drenthwaa.bia.testing.data.DataManager;

public class Experiment implements Runnable
{
	@SuppressWarnings("unused")
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
	
	public Experiment( DataManager dataManager, TestingParameters params)
	{
		testingParameters = params;
		this.dataManager = dataManager;
	}
	
	public Experiment(OAConfig config, DataManager dataManager)
	{
		this.config = config;
		testingParameters = new TestingParameters();
		
		this.testingParameters.numInitCells = config.getNumCells();
		this.testingParameters.numClones = config.getNumClones();
		this.testingParameters.maxIter = config.getMaxIter();
		this.testingParameters.numDims = config.getNumDimensions();
		this.testingParameters.suppThres = config.getSuppThres();
		this.testingParameters.errorThres = config.getErrorThres();
		this.testingParameters.divRatio = config.getDivRatio();
		this.testingParameters.mutnParam = config.getMutnParam();
		this.testingParameters.lowerBounds = config.getLowerBounds();
		this.testingParameters.upperBounds = config.getUpperBounds();
		
		this.dataManager = dataManager;
	}
	
	public Thread executeExperiment()
	{
		optAinet = new OptAinet(testingParameters, dataManager);
		Thread t = new Thread(this);
		t.start();
		return t;
	}

	@Override
    public void run()
    {
		optAinet.optimise();
    }
}
