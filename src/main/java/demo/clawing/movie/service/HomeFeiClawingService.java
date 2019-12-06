package demo.clawing.movie.service;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.clawing.movie.pojo.dto.MovieIntroductionDTO;

public interface HomeFeiClawingService {

	CommonResultBBT collection(TestEvent te);

	CommonResultBBT download(TestEvent te);

	InsertTestEventResult insertCollectionEvent();

	InsertTestEventResult insertDownloadEvent();

	void handleMovieIntroductionRecive(MovieIntroductionDTO dto);

}
