package demo.interaction.movieInteraction.pojo.dto;

import java.time.LocalDateTime;
import java.util.List;

public class FindLastHotClickDTO {

	private List<Long> movieIdList;
	private LocalDateTime startTime = LocalDateTime.now().minusWeeks(2);
	private Long limit;

	public List<Long> getMovieIdList() {
		return movieIdList;
	}

	public void setMovieIdList(List<Long> movieIdList) {
		this.movieIdList = movieIdList;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public Long getLimit() {
		return limit;
	}

	public void setLimit(Long limit) {
		this.limit = limit;
	}

	@Override
	public String toString() {
		return "FindLastHotClickDTO [movieIdList=" + movieIdList + ", startTime=" + startTime + ", limit=" + limit
				+ ", getMovieIdList()=" + getMovieIdList() + ", getStartTime()=" + getStartTime() + ", getLimit()="
				+ getLimit() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
