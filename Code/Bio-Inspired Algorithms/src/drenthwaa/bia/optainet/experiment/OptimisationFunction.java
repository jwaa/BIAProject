package drenthwaa.bia.optainet.experiment;

/**
 * Interface for a function to optimise
 * @author Rjd
 */
public abstract class OptimisationFunction
{
	protected static OptimisationFunction of;
	
	public abstract double[] getOptimum();
	public abstract double getOptimumBound();
	public abstract double evaluateCell(double[] dimensions);
	public abstract String getName();
}
