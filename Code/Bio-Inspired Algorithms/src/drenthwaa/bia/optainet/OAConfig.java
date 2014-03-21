package drenthwaa.bia.optainet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

//class that holds and reads the config parameters from a text file
public class OAConfig
{
	 private int numCells;           // Number of initial network cells  
	 private int numDimensions;      // Number of optimisation problem dimensions
	 private int numClones;          // Number of clones to make of each network cell
	 private int maxIter;            // Maximum number of algorithm iterations
	 private double suppThres;       // Threshold value for network cell suppression
	 private double errorThres;      // Threshold value for average population error during clonal selection
	 private double divRatio;        // Proportion of current population size to be added for diversity
	 private double mutnParam;       // Affinity proportionate mutation parameter
	 private double[] lowerBounds;   // Lower bound on each optimisation problem dimension
	 private double[] upperBounds;   // Upper bound on each optimisation problem dimension
	
	public OAConfig()
	{
	}
	
	public OAConfig(int numCells, int numDimensions, int numClones, int maxIter, double suppThres, double errorThres, double divRatio, double mutnParam, double[] lowerBounds, double[] upperBounds)
	{
		this.numCells = numCells;
		this.numDimensions = numDimensions;
		this.numClones = numClones;
		this.maxIter = maxIter;
		this.suppThres = suppThres;
		this.errorThres = errorThres;
		this.divRatio = divRatio;
		this.mutnParam = mutnParam;
		this.lowerBounds = lowerBounds;
		this.upperBounds = upperBounds;
	}
	
	/**
	 * @param filename
	 * @return null if there is a problem. Otherwise an OAConfig object.
	 */
	public static OAConfig readConfigFile(String filename)
	{
		OAConfig config = new OAConfig();
		
		File file = new File(filename);

		// Check that file exists

		if(!file.exists())
		{
			System.out.println("Config file " + filename + " does not exist");
			return null;
		}

		// Read each line from the config file, setting the relevent variables

		try
		{
			BufferedReader in = new BufferedReader(new FileReader(file));

			config.setNumCells(Integer.parseInt(readNextLine(in)));
			config.setNumClones(Integer.parseInt(readNextLine(in)));
			config.setMaxIter(Integer.parseInt(readNextLine(in)));
			config.setSuppThres(Double.parseDouble(readNextLine(in)));
			config.setErrorThres(Double.parseDouble(readNextLine(in)));
			config.setDivRatio(Double.parseDouble(readNextLine(in)));
			config.setMutnParam(Double.parseDouble(readNextLine(in)));
			config.setNumDimensions(Integer.parseInt(readNextLine(in)));

			double[] lowerBounds = new double[config.getNumDimensions()];
			double[] upperBounds = new double[config.getNumDimensions()];

			// Specific bounds for each dimension must be provided

			for (int i = 0; i < config.getNumDimensions(); i++)
			{
				String s = readNextLine(in);

				if(s == null)
				{
					System.out.println("Upper and lower bounds for each dimension must be provided");
					return null;
				}

				// Split the input string in two using the "," character

				String[] bits = s.split(",");
				lowerBounds[i] = Double.parseDouble(bits[0]);
				upperBounds[i] = Double.parseDouble(bits[1]);
			}
			
			config.setLowerBounds(lowerBounds);
			config.setUpperBounds(upperBounds);
		} catch (Exception e)
		{
			System.out.println("Error opening config file " + filename);
			return null;
		}

		return config;
	}
	
	/**
	 * Reads a single line from the configuration file, ignoring empty lines and
	 * removing commenting characters
	 * 
	 * @param in
	 *            the buffered input stream for the config file
	 * @throws java.lang.Exception
	 *             a line cannot be read
	 * @return the String containing the contents of the line read
	 */
	private static String readNextLine(BufferedReader in) throws Exception
	{
		String str = null;
		boolean proceed;

		// Read in lines from the input stream ignoring lines that are empty or
		// start with the "#" character

		do
		{
			proceed = false;

			str = in.readLine();
			str = str.trim();

			if(str.startsWith("#") || str.matches(""))
				proceed = true;

		} while (proceed);

		return str;
	}

	public int getNumCells()
	{
		return numCells;
	}

	public void setNumCells(int numCells)
	{
		this.numCells = numCells;
	}

	public int getNumDimensions()
	{
		return numDimensions;
	}

	public void setNumDimensions(int numDimensions)
	{
		this.numDimensions = numDimensions;
	}

	public int getNumClones()
	{
		return numClones;
	}

	public void setNumClones(int numClones)
	{
		this.numClones = numClones;
	}

	public int getMaxIter()
	{
		return maxIter;
	}

	public void setMaxIter(int maxIter)
	{
		this.maxIter = maxIter;
	}

	public double getSuppThres()
	{
		return suppThres;
	}

	public void setSuppThres(double suppThres)
	{
		this.suppThres = suppThres;
	}

	public double getErrorThres()
	{
		return errorThres;
	}

	public void setErrorThres(double errorThres)
	{
		this.errorThres = errorThres;
	}

	public double getDivRatio()
	{
		return divRatio;
	}

	public void setDivRatio(double divRatio)
	{
		this.divRatio = divRatio;
	}

	public double getMutnParam()
	{
		return mutnParam;
	}

	public void setMutnParam(double mutnParam)
	{
		this.mutnParam = mutnParam;
	}

	public double[] getLowerBounds()
	{
		return lowerBounds;
	}

	public void setLowerBounds(double[] lowerBounds)
	{
		this.lowerBounds = lowerBounds;
	}

	public double[] getUpperBounds()
	{
		return upperBounds;
	}

	public void setUpperBounds(double[] upperBounds)
	{
		this.upperBounds = upperBounds;
	}
}
