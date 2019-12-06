package demo.clawing.movie.pojo.dto;

import java.time.LocalDateTime;

import auxiliaryCommon.pojo.dto.PageDTO;
import movie.pojo.type.MovieRegionType;

public class FindMovieListByConditionDTO extends PageDTO {

	/** {@link MovieRegionType} */
	private Integer movieRegionType;
	private LocalDateTime createTimeStart;
	private LocalDateTime createTimeEnd;
	private String title;

	public Integer getMovieRegionType() {
		return movieRegionType;
	}

	public void setMovieRegionType(Integer movieRegionType) {
		this.movieRegionType = movieRegionType;
	}

	public LocalDateTime getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(LocalDateTime createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public LocalDateTime getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(LocalDateTime createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "FindMovieListByConditionDTO [MovieRegionType=" + movieRegionType + ", createTimeStart="
				+ createTimeStart + ", createTimeEnd=" + createTimeEnd + ", title=" + title + ", pageNo=" + pageNo
				+ ", pageSize=" + pageSize + ", pageStart=" + pageStart + ", pageEnd=" + pageEnd + "]";
	}

}
