package demo.toyParts.weka.service;

import demo.toyParts.weka.pojo.result.WekaCommonResult;

public interface KMeans {

	WekaCommonResult kmeans(String filePath, int k, int maxIteration) throws Exception;

}
