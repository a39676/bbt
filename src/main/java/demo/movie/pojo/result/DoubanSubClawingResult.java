package demo.movie.pojo.result;

import demo.baseCommon.pojo.result.CommonResultBBT;

public class DoubanSubClawingResult extends CommonResultBBT {

	private String cnTitle;
	private String originalTitle;
	private String info;
	private String introduction;
	private String region;
	private String crewInfo;

	public String getCnTitle() {
		return cnTitle;
	}

	public void setCnTitle(String cnTitle) {
		this.cnTitle = cnTitle;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCrewInfo() {
		return crewInfo;
	}

	public void setCrewInfo(String crewInfo) {
		this.crewInfo = crewInfo;
	}

	@Override
	public String toString() {
		return "DoubanSubClawingResult [cnTitle=" + cnTitle + ", originalTitle=" + originalTitle + ", info=" + info
				+ ", introduction=" + introduction + ", region=" + region + ", crewInfo=" + crewInfo + "]";
	}

}
