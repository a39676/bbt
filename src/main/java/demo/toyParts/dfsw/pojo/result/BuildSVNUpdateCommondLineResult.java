package demo.toyParts.dfsw.pojo.result;

import demo.baseCommon.pojo.result.CommonResultBBT;

public class BuildSVNUpdateCommondLineResult extends CommonResultBBT {

	private String output;
	
	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	@Override
	public String toString() {
		return "BuildSVNUpdateCommondLineResult [output=" + output + "]";
	}

}
