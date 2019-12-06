package demo.clawing.movie.pojo.dto;

public class MovieIntroductionDTO {

	private String introduction;
	private Long movieId;
	private String sk;

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

	public String getSk() {
		return sk;
	}

	public void setSk(String sk) {
		this.sk = sk;
	}

	@Override
	public String toString() {
		return "MovieIntroductionDTO [introduction=" + introduction + ", movieId=" + movieId + ", sk=" + sk + "]";
	}

}
