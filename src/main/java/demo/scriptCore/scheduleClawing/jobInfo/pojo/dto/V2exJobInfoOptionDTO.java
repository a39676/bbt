package demo.scriptCore.scheduleClawing.jobInfo.pojo.dto;

import java.util.List;

import demo.scriptCore.scheduleClawing.common.pojo.dto.CollectUrlHistoryDTO;

public class V2exJobInfoOptionDTO {

	private String mainUrl;
	private List<String> keywords;
	private List<CollectUrlHistoryDTO> urlHistory;
	private Integer maxHistorySize;
	private Integer maxPageSize;

	public String getMainUrl() {
		return mainUrl;
	}

	public void setMainUrl(String mainUrl) {
		this.mainUrl = mainUrl;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public List<CollectUrlHistoryDTO> getUrlHistory() {
		return urlHistory;
	}

	public void setUrlHistory(List<CollectUrlHistoryDTO> urlHistory) {
		this.urlHistory = urlHistory;
	}

	public Integer getMaxHistorySize() {
		return maxHistorySize;
	}

	public void setMaxHistorySize(Integer maxHistorySize) {
		this.maxHistorySize = maxHistorySize;
	}

	public Integer getMaxPageSize() {
		return maxPageSize;
	}

	public void setMaxPageSize(Integer maxPageSize) {
		this.maxPageSize = maxPageSize;
	}

	@Override
	public String toString() {
		return "V2exJobInfoOptionDTO [mainUrl=" + mainUrl + ", keywords=" + keywords + ", urlHistory=" + urlHistory
				+ ", maxHistorySize=" + maxHistorySize + ", maxPageSize=" + maxPageSize + "]";
	}

}
