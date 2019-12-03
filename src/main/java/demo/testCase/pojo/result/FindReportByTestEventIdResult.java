package demo.testCase.pojo.result;

import demo.baseCommon.pojo.result.CommonResultBBT;

public class FindReportByTestEventIdResult extends CommonResultBBT {

	private String reportStr;

	public String getReportStr() {
		return reportStr;
	}

	public void setReportStr(String reportStr) {
		this.reportStr = reportStr;
	}

	@Override
	public String toString() {
		return "FindReportByTestEventIdResult [reportStr=" + reportStr + ", getCode()=" + getCode() + ", getResult()="
				+ getResult() + ", getMessage()=" + getMessage() + ", isSuccess()=" + isSuccess() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
