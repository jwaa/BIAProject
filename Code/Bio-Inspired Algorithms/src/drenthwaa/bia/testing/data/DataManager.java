package drenthwaa.bia.testing.data;

import java.util.ArrayList;

import drenthwaa.bia.optainet.NetworkCell;
import drenthwaa.bia.optainet.OptAinet;
import drenthwaa.bia.testing.TestingParameters;

/**
 * The DataManager is used to manage the data over one specific test run with parameters.
 * This is due to the dependencies the Analyzer and DataManager have between eachother; the Analyzer
 * gets its data from this class with the assumption that all data is from the same sample (same network parameters
 * and same experimental settings).
 * @author Jasper
 *
 */
public class DataManager {
	
	private boolean isSuppressWarnings;
	private boolean isSuppressErrors;
	
	private TestingParameters masterParameters;
	private ArrayList<OptAinet> ranNetworks;
	
	// 1st dimension; all ran networks, 2nd dimension; all generations of a ran network, 3rd dimension; all cells in each generation
	private ArrayList<ArrayList<ArrayList<NetworkCell>>> rawGenerationData;
	private ArrayList<ArrayList<NetworkCell>> rawFinalGenerationData;
	
	public DataManager(TestingParameters masterParameters)
	{
		this.masterParameters = masterParameters;

		ranNetworks = new ArrayList<OptAinet>();
		rawGenerationData = new ArrayList<ArrayList<ArrayList<NetworkCell>>>();
		rawFinalGenerationData = new ArrayList<ArrayList<NetworkCell>>();
		isSuppressWarnings = false;
		isSuppressErrors = false;
	}

	public void addNet(OptAinet optAinet) {
		checkSettings(optAinet);
		ranNetworks.add(optAinet);
	}
	
	public void addGeneration(OptAinet optAinet, int nrGeneration, ArrayList<NetworkCell> generation){
		checkValidility(optAinet);
		int networkIndex = ranNetworks.indexOf(optAinet);
		if(rawGenerationData.isEmpty())
		{
			ArrayList<ArrayList<NetworkCell>> list = new ArrayList<ArrayList<NetworkCell>>();
			list.add(generation);
			rawGenerationData.add(list);
		}
		else if(rawGenerationData.get(networkIndex).size()+1 == nrGeneration)
		{
			rawGenerationData.get(networkIndex).add(generation);
		}
		else if(!isSuppressWarnings)
			System.err.println("WARNING: Generation is overwritten by new generation.");		
	}
	
	public void addNetworkCell(OptAinet optAinet, int nrGeneration, NetworkCell cell){
		checkValidility(optAinet);
		int networkIndex = ranNetworks.indexOf(optAinet);
		
		if(rawGenerationData.isEmpty() && ranNetworks.size() == 1)
		{
			ArrayList<NetworkCell> list = new ArrayList<NetworkCell>();
			list.add(cell);
			ArrayList<ArrayList<NetworkCell>> list1 = new ArrayList<ArrayList<NetworkCell>>();
			list1.add(list);
			rawGenerationData.add(list1);
		}
		else if(rawGenerationData.get(networkIndex).isEmpty())
		{
			ArrayList<NetworkCell> list = new ArrayList<NetworkCell>();
			list.add(cell);
			rawGenerationData.get(networkIndex).add(list);
		}
		else if(rawGenerationData.get(networkIndex).size() > nrGeneration)
			rawGenerationData.get(networkIndex).get(nrGeneration).add(cell);
	}

	public void addFinalGeneration(OptAinet optAinet, ArrayList<NetworkCell> generation) {
		checkValidility(optAinet);
		int networkIndex = ranNetworks.indexOf(optAinet);
		
		if(rawFinalGenerationData.isEmpty() && ranNetworks.size() == 1)
		{
			rawFinalGenerationData.add(generation);
		}
		else if(rawFinalGenerationData.size() == ranNetworks.size())
			rawGenerationData.get(networkIndex).add(generation);
		else if(!isSuppressWarnings)
			System.err.println("WARNING: Final feneration is overwritten by new final outcome generation.");		
		
		rawFinalGenerationData.get(networkIndex).addAll(generation);
	}
	
	
	
	public void printGeneration(OptAinet optAinet, int nrGeneration){
		checkValidility(optAinet);
		int networkIndex = ranNetworks.indexOf(optAinet);

		if((rawGenerationData.get(networkIndex).isEmpty() || rawGenerationData.get(networkIndex).size() < nrGeneration)
				&& !isSuppressErrors)
		{
			System.err.println("ERROR: Data Manager tries to print an non-existent generation.");
			System.exit(-1);
		}
			
		ArrayList<NetworkCell> cellList = rawGenerationData.get(networkIndex).get(nrGeneration);
		
		for (int i = 0; i < cellList.size(); i++)
		{
			for (int j = 0; j < optAinet.getNumDims(); j++)
			{
				System.out.print(cellList.get(i).getDimension(j) + " ");
			}
			System.out.println(cellList.get(i).getFitness());
		}
	}
	
	private void checkValidility(OptAinet optAinet){
		if(ranNetworks.isEmpty() && !isSuppressErrors)
		{
			System.err.println("ERROR: List of running/ran OptAiNet is empty.");
			System.exit(-1);
		}
		else
		{
			int networkIndex = ranNetworks.indexOf(optAinet);
			if(networkIndex == -1 && !isSuppressErrors)
			{
				System.err.println("ERROR: Given OptAinet not found in the data manager.");
				System.exit(-1);
			}
		}
	}
	
	private void checkSettings(OptAinet comparison){
		if(!comparison.getTestingParameters().equals(masterParameters) && !isSuppressErrors)
		{
			System.err.println("ERROR: Settings of the networks given to the Data Manager should be the same as "
					+ "its given master parameters.\nThis due to dependency between Analyzer and DataManager.");
			System.exit(-1);
		}
	}

	public ArrayList<OptAinet> getRanNetworks() {
		return ranNetworks;
	}
	
	public ArrayList<ArrayList<ArrayList<NetworkCell>>> getAllRawGenerationData(){
		return rawGenerationData;
	}
	
	public ArrayList<ArrayList<NetworkCell>> getAllRawFinalGenerationData(){
		return rawFinalGenerationData;
	}

	public TestingParameters getMasterParameters() {
		return masterParameters;
	}

}
