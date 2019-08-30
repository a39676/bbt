package demo.weka.service;

import demo.weka.pojo.result.WekaCommonResult;

public interface WekaCluster {

	WekaCommonResult kMeansTest(String filePath, int k, int maxIteration) throws Exception;

}
