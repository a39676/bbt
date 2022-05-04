package demo.scriptCore.scheduleClawing.pojo.dto;

import java.util.List;
import java.util.Map;

public class EducationInfoOptionDTO {

	private Map<String, String> sourceUrl;
	private List<String> urlHistory;

	public Map<String, String> getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(Map<String, String> sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public List<String> getUrlHistory() {
		return urlHistory;
	}

	public void setUrlHistory(List<String> urlHistory) {
		this.urlHistory = urlHistory;
	}

	@Override
	public String toString() {
		return "EducationInfoOptionDTO [sourceUrl=" + sourceUrl + ", urlHistory=" + urlHistory + "]";
	}

}
