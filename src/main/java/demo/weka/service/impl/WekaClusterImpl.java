package demo.weka.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.weka.pojo.result.WekaCommonResult;
import demo.weka.service.KMeans;
import demo.weka.service.WekaCluster;

@Service
public class WekaClusterImpl implements WekaCluster {

	@Autowired
	private KMeans kMeans;
	
	@Override
	public WekaCommonResult kMeansTest(String filePath, int k, int maxIteration) throws Exception {
		return kMeans.kmeans(filePath, k, maxIteration);
	}
	
}
