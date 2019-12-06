package demo.toyParts.weka.service;

import demo.toyParts.weka.pojo.result.WekaCommonResult;

public interface WekaCluster {

	WekaCommonResult kMeansTest(String filePath, int k, int maxIteration) throws Exception;

}
