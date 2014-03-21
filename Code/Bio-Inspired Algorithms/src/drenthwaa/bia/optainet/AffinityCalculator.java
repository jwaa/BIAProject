package drenthwaa.bia.optainet;

import drenthwaa.bia.testing.TestingParameters;

public class AffinityCalculator
{

	public static double calculateAffinity(double d1, double d2, int affinityMeasure)
    {
		switch(affinityMeasure)
		{
			case TestingParameters.AFFINITY_EUCLIDEAN:
				return Math.pow((d1 - d2), 2);
			case TestingParameters.AFFINITY_MANHATTAN:
				return Math.abs(d1 - d2);
			default:
				System.err.println("Affinity Measure unimplemented");
				return 0;
		}
    }

}
