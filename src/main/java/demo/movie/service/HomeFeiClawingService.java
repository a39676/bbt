package demo.movie.service;

import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.movie.pojo.dto.MovieIntroductionDTO;
import demo.testCase.pojo.po.TestEvent;
import demo.testCase.pojo.result.InsertTestEventResult;

public interface HomeFeiClawingService {

	CommonResultBBT collection(TestEvent te);

	CommonResultBBT download(TestEvent te);

	InsertTestEventResult insertCollectionEvent();

	InsertTestEventResult insertDownloadEvent();

	void handleMovieIntroductionRecive(MovieIntroductionDTO dto);

}
