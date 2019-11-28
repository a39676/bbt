package demo.movie.pojo.type;

public enum MovieClawingCaseType {

	/** dytt */
	dytt(5L, "dytt"),
	/** homeFei 搜集新帖链接 */
	homeFeiCollection(6L, "homeFeiCollection"),
	/** homeFei 按帖链接下载 */
	homeFeiDownload(7L, "homeFeiDownload"),
	/** 豆瓣爬取电影简介 */
	doubanMovieInfoClaw(8L, "doubanMovieInfoClaw"),
	/** bt电影天堂 搜集新帖链接 */
	btbtdyCollection(9L, "btbtdyCollection"),
	/** bt电影天堂 按帖链接下载 */
	btbtdyDownload(10L, "btbtdyDownload"),
	;

	private Long id;
	private String eventName;

	MovieClawingCaseType(Long id, String eventName) {
		this.id = id;
		this.eventName = eventName;
	}

	public Long getId() {
		return id;
	}

	public String getEventName() {
		return eventName;
	}

}
