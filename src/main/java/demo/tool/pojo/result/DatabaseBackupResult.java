package demo.tool.pojo.result;

import demo.baseCommon.pojo.result.CommonResult;

public class DatabaseBackupResult extends CommonResult {

	private String filePath;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
