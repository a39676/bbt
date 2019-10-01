package demo.movie.service;

public interface HomeFeiClawingService {

	void clawing();
	
	/**
	 * 个别异常情况下, testEvent 数据表未记录正常结束, 会导致其他 event 无法进行, 需要手动修正状态
	 * @return 
	 */
	int fixMovieClawingTestEventStatus();
}
