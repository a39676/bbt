package demo.image.pojo.po;

public class ImageStore {
    private Long id;

    private String imagePath;

    private Byte imageType;

    private Boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath == null ? null : imagePath.trim();
    }

    public Byte getImageType() {
        return imageType;
    }

    public void setImageType(Byte imageType) {
        this.imageType = imageType;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}