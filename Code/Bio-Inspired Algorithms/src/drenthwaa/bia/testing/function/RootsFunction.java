package drenthwaa.bia.testing.function;

import org.apache.commons.math3.complex.Complex;

import drenthwaa.bia.optainet.experiment.OptimisationFunction;

/**
 * Example function that is used in the original OptAineet code.
 * 
 * All other funcitons must have the getInstance method (like this one, just change the initialisation)
 * and exend OptimisationFunction
 * 
 * @author JSWaa
 */
public class RootsFunction extends OptimisationFunction
{
	public final double[] OPTIMUM = {0.0, 0.0};
	public final double optimumBound = 0.00000000000001;
	public final String NAME = "Roots";
	
	@Override
    public double evaluateCell(double[] dimensions)
    {
		double fitness;

		Complex z = new Complex(dimensions[0], dimensions[1]);
		z = (z.pow(6.0).subtract(1.0));
		fitness = 1.0/(1.0+z.abs());

		return fitness;
    } 
	
	public synchronized static OptimisationFunction getInstance()
	{
		if(of == null)
		{
			of = new RootsFunction();
		}
		return of;
	}

	@Override
	public double[] getOptimum() {
		return OPTIMUM;
	}

	@Override
	public double getOptimumBound() {
		return optimumBound;
	}

	@Override
	public String getName() {
		return NAME;
	}
}
