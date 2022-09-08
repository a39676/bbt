package demo.scriptCore.localClawing.complex.pojo.dto;

import java.util.List;

public class PrankOptionDTO {

	private String mainUrl;
	private List<String> lastname;
	private List<String> firstname;
	private Integer minId;
	private Integer maxId;

	public String getMainUrl() {
		return mainUrl;
	}

	public void setMainUrl(String mainUrl) {
		this.mainUrl = mainUrl;
	}

	public List<String> getLastname() {
		return lastname;
	}

	public void setLastname(List<String> lastname) {
		this.lastname = lastname;
	}

	public List<String> getFirstname() {
		return firstname;
	}

	public void setFirstname(List<String> firstname) {
		this.firstname = firstname;
	}

	public Integer getMinId() {
		return minId;
	}

	public void setMinId(Integer minId) {
		this.minId = minId;
	}

	public Integer getMaxId() {
		return maxId;
	}

	public void setMaxId(Integer maxId) {
		this.maxId = maxId;
	}

	@Override
	public String toString() {
		return "PrankOptionDTO [mainUrl=" + mainUrl + ", lastname=" + lastname + ", firstname=" + firstname + ", minId="
				+ minId + ", maxId=" + maxId + "]";
	}

}
