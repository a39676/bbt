package demo.weka.service;

import demo.weka.pojo.result.WekaCommonResult;

public interface KMeans {

	WekaCommonResult kmeans(String filePath, int k, int maxIteration) throws Exception;

}
