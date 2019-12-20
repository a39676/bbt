package demo.toyParts.dfsw.pojo.dto;

public class BuildSVNUpdateCommondLineDTO {

	private String localPathPrefix;
	private String input;

	public String getLocalPathPrefix() {
		return localPathPrefix;
	}

	public void setLocalPathPrefix(String localPathPrefix) {
		this.localPathPrefix = localPathPrefix;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	@Override
	public String toString() {
		return "BuildSVNUpdateCommondLineDTO [localPathPrefix=" + localPathPrefix + ", input=" + input + "]";
	}

}
