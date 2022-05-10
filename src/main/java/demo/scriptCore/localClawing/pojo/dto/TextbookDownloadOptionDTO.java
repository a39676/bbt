package demo.scriptCore.localClawing.pojo.dto;

import java.util.List;

public class TextbookDownloadOptionDTO {

	private List<TextbookDownloadSubOption> optionList;

	public List<TextbookDownloadSubOption> getOptionList() {
		return optionList;
	}

	public void setOptionList(List<TextbookDownloadSubOption> optionList) {
		this.optionList = optionList;
	}

	@Override
	public String toString() {
		return "TextbookDownloadOptionDTO [optionList=" + optionList + "]";
	}

}
