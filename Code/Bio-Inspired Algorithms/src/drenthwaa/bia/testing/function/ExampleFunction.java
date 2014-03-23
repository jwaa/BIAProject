package drenthwaa.bia.testing.function;

import drenthwaa.bia.optainet.experiment.OptimisationFunction;

/**
 * Example function that is used in the original OptAineet code.
 * 
 * All other funcitons must have the getInstance method (like this one, just change the initialisation)
 * and exend OptimisationFunction
 * 
 * @author Rjd
 */
public class ExampleFunction extends OptimisationFunction
{
	@Override
    public double evaluateCell(double[] dimensions)
    {
		double fitness;

		fitness = dimensions[0] * Math.sin(4 * Math.PI * dimensions[0]) - dimensions[1] * Math.sin(4 * Math.PI * dimensions[1] + Math.PI) + 1;

		return fitness;
    } 
	
	public synchronized static OptimisationFunction getInstance()
	{
		if(of == null)
		{
			of = new ExampleFunction();
		}
		return of;
	}
}
