package demo.tool.captcha.pojo.dto;

import auxiliaryCommon.pojo.result.CommonResult;

public class CleanImageResult extends CommonResult {

	private String outputPath;

	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	@Override
	public String toString() {
		return "CleanImageResult [outputPath=" + outputPath + ", success=" + success + ", getOutputPath()="
				+ getOutputPath() + ", getCode()=" + getCode() + ", getResult()=" + getResult() + ", getMessage()="
				+ getMessage() + ", isSuccess()=" + isSuccess() + ", toString()=" + super.toString() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
