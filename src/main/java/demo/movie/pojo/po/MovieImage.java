package demo.movie.pojo.po;

public class MovieImage {
    private Long imageId;

    private Long movidId;

    private Boolean isDelete;

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Long getMovidId() {
        return movidId;
    }

    public void setMovidId(Long movidId) {
        this.movidId = movidId;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}