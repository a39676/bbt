package demo.movieInteraction.pojo.dto;

import java.time.LocalDateTime;
import java.util.List;

public class FindPosterIdByMovieIdListDTO {

	private List<Long> movieIdList;
	private boolean isDelete = false;
	private LocalDateTime startTime;

	public List<Long> getMovieIdList() {
		return movieIdList;
	}

	public void setMovieIdList(List<Long> movieIdList) {
		this.movieIdList = movieIdList;
	}

	public boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	@Override
	public String toString() {
		return "FindFirstTimeByMovieIdListDto [movieIdList=" + movieIdList + ", isDelete=" + isDelete + ", startTime="
				+ startTime + "]";
	}

}
