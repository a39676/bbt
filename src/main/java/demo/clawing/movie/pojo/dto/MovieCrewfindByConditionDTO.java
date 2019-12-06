package demo.clawing.movie.pojo.dto;

import demo.clawing.movie.pojo.type.MovieCrewType;

public class MovieCrewfindByConditionDTO {

	private String cnName;
	private String engName;

	/** {@link MovieCrewType} */
	private Integer crewType;

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getEngName() {
		return engName;
	}

	public void setEngName(String engName) {
		this.engName = engName;
	}

	public Integer getCrewType() {
		return crewType;
	}

	public void setCrewType(Integer crewType) {
		this.crewType = crewType;
	}

	@Override
	public String toString() {
		return "MovieCrewfindByConditionDTO [cnName=" + cnName + ", engName=" + engName + ", crewType=" + crewType
				+ "]";
	}

}
