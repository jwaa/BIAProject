package drenthwaa.bia.optainet;

import drenthwaa.bia.testing.TestingParameters;

public class AffinityCalculator
{
	public static double calculateAffinity(double[] d1, double[] d2, int affinityMeasure)
    {
		switch(affinityMeasure)
		{
			case TestingParameters.AFFINITY_EUCLIDEAN:
				return euclideanDistance(d1, d2);
				//return Math.pow((d1 - d2), 2);
			case TestingParameters.AFFINITY_MANHATTAN:
				return manhattenDistance(d1, d2);
				//return Math.abs(d1 - d2);
			case TestingParameters.AFFINITY_BRAYCURTIS:
				return brayCurtisDistance(d1, d2);
			default:
				System.err.println("Affinity Measure unimplemented");
				return 0;
		}
    }

	private static double euclideanDistance(double[] d1, double[] d2)
	{
		double d = 0;
		
		for(int i=0; i<d1.length; i++)
		{
			d += Math.pow(d1[i] - d2[i], 2);
		}
		
		return Math.sqrt(d);
	}
	
	private static double manhattenDistance(double[] d1, double[] d2)
	{
		double d = 0;
		
		for(int i=0; i<d1.length; i++)
		{
			d += Math.abs(d1[i] - d2[i]);
		}
		
		return d;
	}
	
	private static double brayCurtisDistance(double[] d1, double[] d2)
	{
		double n = 0;
		double d = 0;
				
		for (int i=0; i<d1.length; i++)
		{
			n += Math.abs(d1[i]-d2[i]);
			d += Math.abs(d1[i]+d2[i]);
		}
		return n/d;
	}
}
