package demo.weka.service.impl;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.type.BaseResultType;
import demo.weka.pojo.result.WekaCommonResult;
import demo.weka.service.KMeans;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

@Service
public class KMeansImpl implements KMeans {

	@Override
	public WekaCommonResult kmeans(String filePath, int k, int maxIteration) {
		WekaCommonResult result = new WekaCommonResult();
		int maxK = 1000;
		if (k < 0 || k > maxK) {
			result.fillWithResult(BaseResultType.errorParam);
			return result;
		}

		File inFile = new File(filePath);
		if (!inFile.exists()) {
			result.fillWithResult(BaseResultType.serviceError);
			return result;
		}

		maxIteration = 1000;

		CSVLoader loader = new CSVLoader();
		try {
			loader.setSource(inFile);
		} catch (IOException e) {
			e.printStackTrace();
			result.fillWithResult(BaseResultType.serviceError);
			return result;
		}
		Instances data;
		try {
			data = loader.getDataSet();
		} catch (IOException e) {
			e.printStackTrace();
			result.fillWithResult(BaseResultType.serviceError);
			return result;
		}

		// Create the KMeans object.
		SimpleKMeans kmeans = new SimpleKMeans();
		try {
			kmeans.setNumClusters(k);
			kmeans.setMaxIterations(maxIteration);
		} catch (Exception e) {
			e.printStackTrace();
			result.fillWithResult(BaseResultType.serviceError);
			return result;
		}
		kmeans.setPreserveInstancesOrder(true);

		// Perform K-Means clustering.
		try {
			kmeans.buildClusterer(data);
		} catch (Exception ex) {
			ex.printStackTrace();
			result.fillWithResult(BaseResultType.serviceError);
			return result;
		}

		// print out the cluster centroids
		Instances centroids = kmeans.getClusterCentroids();
		StringBuffer resultBuilder = new StringBuffer();
		for (int i = 0; i < k; i++) {
//			System.out.print("Cluster " + i + " size: " + kmeans.getClusterSizes()[i]);
//			System.out.println(" Centroid: " + centroids.instance(i));
			resultBuilder.append("Cluster " + i + " size: " + kmeans.getClusterSizes()[i] + " Centroid: " + centroids.instance(i) + "\n");
		}
		
		result.successWithMessage(resultBuilder.toString());
		return result;
	}
}
