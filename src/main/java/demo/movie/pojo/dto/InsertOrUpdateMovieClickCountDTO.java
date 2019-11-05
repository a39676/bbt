package demo.movie.pojo.dto;

public class InsertOrUpdateMovieClickCountDTO {

	private Long movieId;

	private Long counting;

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public Long getCounting() {
		return counting;
	}

	public void setCounting(Long counting) {
		this.counting = counting;
	}

	@Override
	public String toString() {
		return "InsertOrUpdateMovieClickCountBO [movieId=" + movieId + ", counting=" + counting + "]";
	}

}
