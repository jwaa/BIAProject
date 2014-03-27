package drenthwaa.bia.optainet;

import java.util.Random;
import drenthwaa.bia.optainet.experiment.OptimisationFunction;
import drenthwaa.bia.testing.TestingParameters;

/**
 * This class represents the network cells of the opt-aiNet algorithm. Each cell
 * has a real-valued vector representing a possible solution to the function
 * being optimised. For each value in this vector, lower and upper bounds exist.
 * Each cell also holds its fitness value and provides methods to mutate
 * dimension values, clone the cell, compare affinity with another cell, and to
 * evaluate the cell against the optimiation function.
 */
public class NetworkCell implements Cloneable
{
	private static final Random random = new Random(); // Random number generator
	private final OptimisationFunction optFunc;

	private final TestingParameters testingParameters;
	
	private double mutnParam; // Affinity proportionate mutation parameter
	private double[] dims; // Network cell dimension values
	private double[] lowerBounds; // Lower bound for each dimension
	private double[] upperBounds; // Upper bound for each dimension
	private double fitness; // Fitness of network cell
	private double fitnessNorm; // Normalised fitness of network cell compared to other network cells

	/**
	 * Creates a new instance of NetworkCell and initialises variable values
	 * using the provided parameters. Each network cell dimension is set to a
	 * random value between the upper and lower bounds for that dimension.
	 * 
	 * @param mutnParam
	 *            the affinity proportionate mutation parameter
	 * @param numDims
	 *            the number of optimisation problem dimensions
	 * @param lowerBounds
	 *            the array of dimension lower bounds
	 * @param upperBounds
	 *            the array of dimension upper bounds
	 */
	public NetworkCell(double[] dimensionValues, TestingParameters testingParameters)
	{
		this.testingParameters = testingParameters;
		
		this.mutnParam = testingParameters.mutnParam;
		this.lowerBounds = testingParameters.lowerBounds;
		this.upperBounds = testingParameters.upperBounds;
		this.optFunc = testingParameters.optimisationFunction;
		
		this.dims = dimensionValues;
	}

	/**
	 * Mutates each dimension in the network cell. The mutations are generated
	 * using a Gaussian random number and an affinity proportionate variable
	 * calculated so that the fitter the network cell, the smaller the mutation
	 * value is.
	 */
	public void mutate()
	{
		double alpha;

		// Calculate alpha - the affinity proportionate variable

		alpha = (1.0 / mutnParam) * Math.exp(-1 * fitnessNorm);

		// Mutate each dimension by adding an affinity proportionate Gaussian
		// random
		// number if the new value is outside the lower or upper bound on the
		// dimension, then set to the appropriate bound value

		for (int i = 0; i < dims.length; i++)
		{
			dims[i] = dims[i] + alpha * random.nextGaussian();

			if(dims[i] > upperBounds[i])
			{
				dims[i] = upperBounds[i];
			}

			if(dims[i] < lowerBounds[i])
			{
				dims[i] = lowerBounds[i];
			}
		}

	}

	/**
	 * Gets the affinity value between this network cell and the one provided by
	 * calculating the Euclidean distance between the dimension values of the
	 * cells
	 * 
	 * @param cell
	 *            the network cell to calculate affinity with
	 * @return the affinity value
	 */
	public double getAffinity(NetworkCell cell)
	{
		return AffinityCalculator.calculateAffinity(dims, cell.getDimensions(), testingParameters.affinityMeasure);
	}

	/**
	 * Clones this network cell making a deep copy of the dimensions array
	 * 
	 * @return a clone of this network cell
	 */
	protected Object clone()
	{
		NetworkCell cell = null;

		try
		{
			cell = (NetworkCell) super.clone();

			// Make a deep copy of the dimension values
			cell.dims = (double[]) dims.clone();
		} catch (Exception e)
		{
			System.err.println("NetworkCell.clone() - Error during cloning:");
			e.printStackTrace();
		}

		return cell;
	}

	/**
	 * Sets the normalised fitness of this network cell
	 * 
	 * @param lowestFitness
	 *            the lowest fitness of all network cells
	 * @param highestFitness
	 *            the highest fitness of all network cells
	 */
	public void setFitnessNorm(double lowestFitness, double highestFitness)
	{
		fitnessNorm = (fitness - lowestFitness) / (highestFitness - lowestFitness);
	}

	/**
	 * Gets the normalised fitness of this network cell
	 * 
	 * @return the normalised fitness value
	 */
	public double getFitnessNorm()
	{
		return fitnessNorm;
	}

	/**
	 * Gets the value of the specified dimension
	 * 
	 * @param index
	 *            the index value of the dimension required
	 * @return the value of the dimension
	 */
	public double getDimension(int index)
	{
		return dims[index];
	}

	public double[] getDimensions()
	{
		return dims;
	}
	
	/**
	 * Evaluates this network cell against the optimisation function
	 */
	public void evaluate()
	{
		fitness = optFunc.evaluateCell(dims);
	}

	/**
	 * Gets the fitness of this network cell
	 * 
	 * @return the fitness of the network cell
	 */
	public double getFitness()
	{
		return fitness;
	}
}