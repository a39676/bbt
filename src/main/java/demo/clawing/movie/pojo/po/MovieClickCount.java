package demo.clawing.movie.pojo.po;

import java.time.LocalDateTime;

public class MovieClickCount {
    private Long movieId;

    private Long counting;

    private LocalDateTime createTime;

    private Boolean isDelete;

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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}