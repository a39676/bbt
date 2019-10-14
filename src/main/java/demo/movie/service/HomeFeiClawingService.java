package demo.movie.service;

import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.testCase.pojo.po.TestEvent;

public interface HomeFeiClawingService {

	CommonResultBBT collection(TestEvent te);

	CommonResultBBT download(TestEvent te);

	Integer insertCollectionEvent();

	Integer insertDownloadEvent();

}
