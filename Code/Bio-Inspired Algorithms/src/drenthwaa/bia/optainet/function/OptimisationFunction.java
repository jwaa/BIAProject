package drenthwaa.bia.optainet.function;

/**
 * Interface for a function to optimise
 * @author Rjd
 */
public abstract class OptimisationFunction
{
	protected static OptimisationFunction of;
	
	public abstract double evaluateCell(double[] dimensions);
}
