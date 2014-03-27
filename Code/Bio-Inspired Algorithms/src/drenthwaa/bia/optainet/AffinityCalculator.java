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
			case TestingParameters.AFFINITY_COSINE:
				return cosineSimilarity(d1, d2);
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
	
	private static double cosineSimilarity(double[] d1, double[] d2)
	{
		double magnitudeD1 = 0;
		double magnitudeD2 = 0;
		double dot = 0;
		
		double d1sumSq = 0;
		double d2sumSq = 0;
		
		for(int i=0; i<d1.length; i++)
		{
			d1sumSq += d1[i]*d1[i];
			d2sumSq += d2[i]*d2[i];
			
			dot += d1[i]*d2[i];
		}
		
		magnitudeD1 = Math.sqrt(d1sumSq);
		magnitudeD2 = Math.sqrt(d2sumSq);
		
		return dot / (magnitudeD1 * magnitudeD2);
	}
}
