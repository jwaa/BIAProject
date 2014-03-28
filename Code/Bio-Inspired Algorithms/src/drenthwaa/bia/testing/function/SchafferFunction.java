package drenthwaa.bia.testing.function;

import drenthwaa.bia.optainet.experiment.OptimisationFunction;

/**
 * Example function that is used in the original OptAineet code.
 * 
 * All other funcitons must have the getInstance method (like this one, just change the initialisation)
 * and exend OptimisationFunction
 * 
 * @author JSWaa
 */
public class SchafferFunction extends OptimisationFunction
{
	public final double[] OPTIMUM = {0.0, 0.0};
	public final double optimumBound = 0.00000000000001;
	public final String NAME = "Schaffer";
	
	@Override
    public double evaluateCell(double[] dimensions)
    {
		double fitness;

		fitness = 0.5+(Math.pow(Math.sin(Math.sqrt(Math.pow(dimensions[0],2)+Math.pow(dimensions[1],2))),2)-0.5)/(1.0+0.001*(Math.pow(dimensions[0],2)+Math.pow(dimensions[1],2)));
				
		return fitness;
    } 
	
	public synchronized static OptimisationFunction getInstance()
	{
		if(of == null)
		{
			of = new SchafferFunction();
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
