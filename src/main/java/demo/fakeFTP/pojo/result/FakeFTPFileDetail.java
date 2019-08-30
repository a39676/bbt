package demo.fakeFTP.pojo.result;

public class FakeFTPFileDetail {

	private String path;

	private String fileName;

	private Long size;

	private String createTime;

	private String lastModifyTime;
	
	private boolean isFile = true;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public boolean getIsFile() {
		return isFile;
	}

	public void setIsFile(boolean isFile) {
		this.isFile = isFile;
	}

	@Override
	public String toString() {
		return "FakeFTPFileDetail [path=" + path + ", fileName=" + fileName + ", size=" + size + ", createTime="
				+ createTime + ", lastModifyTime=" + lastModifyTime + ", isFile=" + isFile + "]";
	}

}
