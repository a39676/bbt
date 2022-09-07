package demo.scriptCore.scheduleClawing.educationInfo.pojo.dto;

import java.util.List;
import java.util.Map;

import demo.scriptCore.scheduleClawing.common.pojo.dto.CollectUrlHistoryDTO;

public class EducationInfoOptionDTO {

	private Map<String, String> sourceUrl;
	private Map<String, List<CollectUrlHistoryDTO>> urlHistory;

	public Map<String, String> getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(Map<String, String> sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public Map<String, List<CollectUrlHistoryDTO>> getUrlHistory() {
		return urlHistory;
	}

	public void setUrlHistory(Map<String, List<CollectUrlHistoryDTO>> urlHistory) {
		this.urlHistory = urlHistory;
	}

	@Override
	public String toString() {
		return "EducationInfoOptionDTO [sourceUrl=" + sourceUrl + ", urlHistory=" + urlHistory + "]";
	}

}
