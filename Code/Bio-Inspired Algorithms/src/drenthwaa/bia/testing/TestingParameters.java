package drenthwaa.bia.testing;

import drenthwaa.bia.optainet.experiment.CellGenerator;
import drenthwaa.bia.optainet.experiment.OptimisationFunction;
import drenthwaa.bia.testing.cell.RandomCellGenerator;
import drenthwaa.bia.testing.function.ExampleFunction;

public class TestingParameters
{
	public static final int AFFINITY_EUCLIDEAN = 0;
	public static final int AFFINITY_MANHATTAN = 1;
	public static final int AFFINITY_COSINE = 2; // implemented (in AffinityCalculator)
	
	public int affinityMeasure = AFFINITY_EUCLIDEAN; // default
	
	public static final int GENERATOR_HISTOGRAM = 0;
	public static final int GENERATOR_DISTANCE = 1;
	public static final int GENERATOR_RANDOM = 2;
	
	public int generatorType = GENERATOR_RANDOM;
	
	public int maxNrRuns = 10; // default
	
	public OptimisationFunction optimisationFunction = ExampleFunction.getInstance(); // default function
	public CellGenerator cellGenerator = RandomCellGenerator.getInstance(); // default cell generator

	
	public int numInitCells; 			// Number of initial network cells
	public int numClones; 				// Number of clones to make of each network cell
	public int maxIter; 				// Maximum number of algorithm iterations
	public int numDims;					// Number of optimisation problem dimensions
	public double suppThres; 			// Threshold value for network cell suppression
	public double errorThres; 			// Threshold value for average population error
	                           			// during clonal selection
	public double divRatio; 			// Proportion of current population size to be
	                         			// added for diversity
	public double mutnParam; 			// Affinity proportionate mutation parameter
	public double[] lowerBounds;		// Lower bound on each optimisation problem
	                            		// dimension
	public double[] upperBounds;		// Upper bound on each optimisation problem
	                           			// dimension
	
}
