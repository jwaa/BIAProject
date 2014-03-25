package drenthwaa.bia.testing.data;

import java.util.ArrayList;

import drenthwaa.bia.optainet.NetworkCell;
import drenthwaa.bia.optainet.OptAinet;
import drenthwaa.bia.testing.TestingParameters;

public class Analyzer 
{	
	private boolean isSuppressErrors = false;
	private boolean isSuppressWarnings = false;
	
	private TestingParameters masterParameters;
	private ArrayList<OptAinet> ranNetworks;
	private ArrayList<ArrayList<NetworkCell>> rawFinalGenerationData;
	private ArrayList<ArrayList<ArrayList<NetworkCell>>> rawGenerationData;
	private ArrayList<ArrayList<Result>> results;
	
	public Analyzer(DataManager dataManager)
	{
		masterParameters = dataManager.getMasterParameters();
		results = new ArrayList<ArrayList<Result>>();
		ranNetworks = dataManager.getRanNetworks();
		rawGenerationData = dataManager.getAllRawGenerationData();
		rawFinalGenerationData = dataManager.getAllRawFinalGenerationData();
	}
	
	public void analyze()
	{
		checkNetworks();
		calculateAverageFitness();
		calculateAverageNrCells();
		calculateMBF();
		calculateAES();
		calculateSR();
		
	}
	
	private void calculateSR() {
		// TODO Auto-generated method stub
		
	}

	private void calculateMBF() {
		// TODO Auto-generated method stub
		
	}

	private void calculateAES() {
		// TODO Auto-generated method stub
		
	}

	private void calculateAverageNrCells() {
		// TODO Auto-generated method stub
		
	}

	private void calculateAverageFitness() {
		// TODO Auto-generated method stub
		
	}

	private void checkNetworks() {
		if(rawFinalGenerationData.size() != ranNetworks.size() && rawGenerationData.size() != ranNetworks.size()
				&& !isSuppressErrors)
		{
			System.err.println("ERROR: Analyzer found inconsistencies in the received data and number of networks tested (sample size).");
			System.exit(-1);
		}
		for(int i=0;i<ranNetworks.size();i++)
		{
			if(!ranNetworks.get(i).getTestingParameters().equals(masterParameters) && !isSuppressErrors)
			{
				System.err.println("ERROR: Analyzer found inconsistencies in the received parameters of network indexed by " + i 
						+ " and the master parameters.");
				System.exit(-1);
			}
		}
		
	}

	private class Result
	{
		public OptAinet optAiNet;
		public float MBF;
		public float MBF_stdev;
		public float AES;
		public float AES_stdev;
		public float SR;
		public float SR_stdev;
		public float averageFitniss;
		public float averageFitniss_stdev;
		public float averageCells;
		public float averageCells_stdev;

	}
}
