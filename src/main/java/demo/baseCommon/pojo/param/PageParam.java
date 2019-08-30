package demo.baseCommon.pojo.param;

public class PageParam {

	private Integer pageStart = 1;
	private Integer pageEnd = 1;
	private Integer pageSize = 1;

	public Integer getPageStart() {
		return pageStart;
	}

	public void setPageStart(Integer pageStart) {
		this.pageStart = pageStart;
	}

	public Integer getPageEnd() {
		return pageEnd;
	}

	public void setPageEnd(Integer pageEnd) {
		this.pageEnd = pageEnd;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return "PageParam [pageStart=" + pageStart + ", pageEnd=" + pageEnd + ", pageSize=" + pageSize + "]";
	}

}
