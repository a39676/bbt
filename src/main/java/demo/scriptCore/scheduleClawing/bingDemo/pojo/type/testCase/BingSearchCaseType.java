package demo.scriptCore.scheduleClawing.bingDemo.pojo.type.testCase;

public enum BingSearchCaseType {

	/** search in home page */
	SEARCH_IN_HOMEPAGE(1L, 1L, "searchInHomepage"),
	/** click "image" tag in search result page */
	CLICK_IMAGE_TAG(1L, 2L, "clickImageTag"),

	;

	private Long pid;
	private Long sid;
	private String caseName;

	BingSearchCaseType(Long pid, Long sid, String caseName) {
		this.pid = pid;
		this.sid = sid;
		this.caseName = caseName;
	}

	public Long getPid() {
		return pid;
	}
	
	public Long getSid() {
		return sid;
	}

	public String getCaseName() {
		return caseName;
	}

	public static BingSearchCaseType getType(Long pid, Long sid) {
		for (BingSearchCaseType t : BingSearchCaseType.values()) {
			if(t.getPid().equals(pid) && t.getSid().equals(sid)) {
				return t;
			}
		}
		return null;
	}

}
