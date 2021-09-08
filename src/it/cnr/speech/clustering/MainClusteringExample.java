package it.cnr.speech.clustering;

import java.io.File;
import java.util.HashMap;

public class MainClusteringExample {

	public static void main(String[] args) throws Exception {
	
		double [] x1 = {1,2,3};
		double [] x2 = {2,4,5};
		double [] x3 = {1,2.1,3};
		double [] x4 = {2,4.5,5};
		
		double [][] featureList = {x1,x2,x3,x4};
		HashMap<String,String> vectorLabels = new HashMap<>();
		vectorLabels.put("1","F1");
		vectorLabels.put("2","F2");
		vectorLabels.put("3","F3");
		vectorLabels.put("4","F4");
		
		System.out.println("Running Kmeans clustering...");
		KMeans kmeans = new KMeans(vectorLabels);
		int kmeansClusters = 2;
		int minNofPointsToDefineACluster = 1;
		File kmeansOutputTable = new File("kmeans.csv");
		kmeans.compute(kmeansClusters, 100, 10000, minNofPointsToDefineACluster,featureList,kmeansOutputTable);
		System.out.println("...Done");
		//get results
		int sum = 0;
		for (String key : kmeans.pointsPerCluster.keySet()) {
			int np = kmeans.pointsPerCluster.get(key);
			sum = sum + np;
		}
		float uniformityScore = 1f / (float) kmeansClusters;
		System.out.println("Uniformity: " + uniformityScore);
		float difformity = 0;
		for (String key : kmeans.pointsPerCluster.keySet()) {
			int np = kmeans.pointsPerCluster.get(key);
			float score = ((float) np / (float) sum);
			System.out.println("C1%:" + score);
			difformity = difformity + (Math.abs(score - uniformityScore));
		}

		difformity = (float) difformity / (float) kmeans.pointsPerCluster.keySet().size();
		//difformity measurement is a way to check the fairness of the kmeans
		System.out.println("Difformity: " + difformity);
		System.out.println("...Done");
		
		System.out.println("Running Xmeans clustering...");
		//difformity measurement is already included in Xmeans
		XMeans xmeans = new XMeans(vectorLabels);
		int minClusters = 1;
		int maxClusters = 3;
		File xmeansOutputTable = new File("xmeans.csv");
		xmeans.compute(minNofPointsToDefineACluster,minClusters,maxClusters,10000, featureList,xmeansOutputTable);
		System.out.println("...Done");
		
		
	}
}
