package demo.movie.po.bo;

import java.time.LocalDate;
import java.util.List;

/**
 * 
 * 2019/10/08 往后可能统一电影详细信息(如: 简介, 上映日期, 产地, 演员表等...)的来源 故创建此bo
 *
 */
public class MovieInfoDetailBO {

	/**
	 * 原电影名
	 */
	private String originalTitle;
	/**
	 * 中文译名
	 */
	private String cnTitle;

	/**
	 * 豆瓣评分
	 * 
	 */
	private Double score;
	/**
	 * 评分人数
	 */
	private Integer scoreCount;

	/**
	 * 导演
	 */
	private List<String> directers;
	/**
	 * 编剧
	 */
	private List<String> screenwriters;
	/**
	 * 主演
	 */
	private List<String> starrings;
	/**
	 * 类型
	 */
	private List<String> genres;

	/**
	 * 语言
	 */
	private String language;

	/**
	 * 制片国家/地区
	 */
	private List<String> productArea;

	/**
	 * 上映日期(只取其一)
	 */
	private LocalDate initialReleaseDate;

	private String imdbLink;

	public String getOriginalTitle() {
		return originalTitle;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public String getCnTitle() {
		return cnTitle;
	}

	public void setCnTitle(String cnTitle) {
		this.cnTitle = cnTitle;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Integer getScoreCount() {
		return scoreCount;
	}

	public void setScoreCount(Integer scoreCount) {
		this.scoreCount = scoreCount;
	}

	public List<String> getDirecters() {
		return directers;
	}

	public void setDirecters(List<String> directers) {
		this.directers = directers;
	}

	public List<String> getScreenwriters() {
		return screenwriters;
	}

	public void setScreenwriters(List<String> screenwriters) {
		this.screenwriters = screenwriters;
	}

	public List<String> getStarrings() {
		return starrings;
	}

	public void setStarrings(List<String> starrings) {
		this.starrings = starrings;
	}

	public List<String> getGenres() {
		return genres;
	}

	public void setGenres(List<String> genres) {
		this.genres = genres;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public List<String> getProductArea() {
		return productArea;
	}

	public void setProductArea(List<String> productArea) {
		this.productArea = productArea;
	}

	public LocalDate getInitialReleaseDate() {
		return initialReleaseDate;
	}

	public void setInitialReleaseDate(LocalDate initialReleaseDate) {
		this.initialReleaseDate = initialReleaseDate;
	}

	public String getImdbLink() {
		return imdbLink;
	}

	public void setImdbLink(String imdbLink) {
		this.imdbLink = imdbLink;
	}

	@Override
	public String toString() {
		return "MovieInfoDetailBO [originalTitle=" + originalTitle + ", cnTitle=" + cnTitle + ", score=" + score
				+ ", scoreCount=" + scoreCount + ", directers=" + directers + ", screenwriters=" + screenwriters
				+ ", starrings=" + starrings + ", genres=" + genres + ", language=" + language + ", productArea="
				+ productArea + ", initialReleaseDate=" + initialReleaseDate + ", imdbLink=" + imdbLink + "]";
	}

}
