package drenthwaa.bia.testing;

import drenthwaa.bia.optainet.function.OptimisationFunction;
import drenthwaa.bia.testing.function.ExampleFunction;

public class TestingParameters
{
	public static final int AFFINITY_EUCLIDEAN = 0;
	public static final int AFFINITY_MANHATTAN = 1;
	public static final int AFFINITY_SINE = 2; // unimplemented (in AffinityCalculator)
	public static final int AFFINITY_COSINE = 3; // unimplemented (in AffinityCalculator)
	public int affinityMeasure = AFFINITY_EUCLIDEAN; // default
	
	public OptimisationFunction optimisationFunction = ExampleFunction.getInstance(); // default function
}
