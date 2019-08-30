package demo.selenium.pojo.po;

import java.time.LocalDateTime;

public class VerificationCode {
    private Long id;

    private Long testEventId;

    private String url;

    private String cloudid;

    private LocalDateTime createTime;

    private String correctText;

    private Boolean isDelete;

    private Boolean isPass;

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

    public String getCorrectText() {
        return correctText;
    }

    public void setCorrectText(String correctText) {
        this.correctText = correctText == null ? null : correctText.trim();
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Boolean getIsPass() {
        return isPass;
    }

    public void setIsPass(Boolean isPass) {
        this.isPass = isPass;
    }
}