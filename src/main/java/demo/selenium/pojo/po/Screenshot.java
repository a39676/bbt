package demo.selenium.pojo.po;

import java.time.LocalDateTime;

public class Screenshot {
    private Long id;

    private Long testEventId;

    private String url;

    private String cloudid;

    private LocalDateTime createTime;

    private String localPath;

    private Boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTestEventId() {
        return testEventId;
    }

    public void setTestEventId(Long testEventId) {
        this.testEventId = testEventId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getCloudid() {
        return cloudid;
    }

    public void setCloudid(String cloudid) {
        this.cloudid = cloudid == null ? null : cloudid.trim();
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath == null ? null : localPath.trim();
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}