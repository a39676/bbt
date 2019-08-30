package demo.selenium.pojo.po;

import java.time.LocalDateTime;

public class TestEvent {
    private Long id;

    private String eventName;

    private String remark;

    private String reportPath;

    private LocalDateTime createTime;

    private LocalDateTime endTime;

    private Boolean isPass;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName == null ? null : eventName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getReportPath() {
        return reportPath;
    }

    public void setReportPath(String reportPath) {
        this.reportPath = reportPath == null ? null : reportPath.trim();
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Boolean getIsPass() {
        return isPass;
    }

    public void setIsPass(Boolean isPass) {
        this.isPass = isPass;
    }
}