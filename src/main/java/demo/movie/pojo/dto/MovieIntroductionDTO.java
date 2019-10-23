package demo.movie.pojo.dto;

public class MovieIntroductionDTO {

	private String introduction;
	private Long movieId;

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	@Override
	public String toString() {
		return "MovieIntroductionDTO [introduction=" + introduction + ", movieId=" + movieId + "]";
	}

}
