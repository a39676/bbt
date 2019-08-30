package demo.baseCommon.pojo.param;

public abstract class PageDTO {

	protected long pageNo = 1;
	protected long pageSize = 10;

	public long getPageNo() {
		return pageNo;
	}

	public void setPageNo(long pageNo) {
		this.pageNo = pageNo;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

}
