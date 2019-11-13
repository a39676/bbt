package demo.captcha.pojo.dto;

import demo.baseCommon.pojo.result.CommonResultBBT;

public class CleanImageResult extends CommonResultBBT {

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
