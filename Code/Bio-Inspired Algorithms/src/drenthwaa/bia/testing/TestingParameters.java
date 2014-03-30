package drenthwaa.bia.testing;

import drenthwaa.bia.optainet.experiment.CellGenerator;
import drenthwaa.bia.optainet.experiment.OptimisationFunction;
import drenthwaa.bia.testing.cell.RandomCellGenerator;
import drenthwaa.bia.testing.function.MultiFunction;

public class TestingParameters
{
	public String name;
	
	public static final int AFFINITY_EUCLIDEAN = 0;
	public static final int AFFINITY_MANHATTAN = 1;
	public static final int AFFINITY_BRAYCURTIS = 2; // implemented (in AffinityCalculator)
	
	public int affinityMeasure = AFFINITY_EUCLIDEAN; // default
	
	public static final int GENERATOR_HISTOGRAM = 0;
	public static final int GENERATOR_DISTANCE = 1;
	public static final int GENERATOR_RANDOM = 2;
	
	public int generatorType = GENERATOR_RANDOM;
	
	public int maxNrRuns = 10; // default
	
	public OptimisationFunction optimisationFunction = MultiFunction.getInstance(); // default function
	public CellGenerator cellGenerator = RandomCellGenerator.getInstance(); // default cell generator

	
	public int numInitCells 	= 20; 				// Number of initial network cells
	public int numClones		= 10; 				// Number of clones to make of each network cell
	public int maxIter			= 500; 				// Maximum number of algorithm iterations
	public int numDims			= 2;				// Number of optimisation problem dimensions
	public double suppThres		= 0.2; 				// Threshold value for network cell suppression
	public double errorThres	= 0.001; 			// Threshold value for average population error
	                           						// during clonal selection
	public double divRatio		= 0.4; 				// Proportion of current population size to be
	                         						// added for diversity
	public double mutnParam		= 100; 				// Affinity proportionate mutation parameter
	public double[] lowerBounds	= {-2.0, -2.0};		// Lower bound on each optimisation problem
	                            					// dimension
	public double[] upperBounds	= {2.0, 2.0};		// Upper bound on each optimisation problem
													// dimension
	
}
