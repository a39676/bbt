package demo.task.pojo.po;

import java.util.Date;

public class TaskManage {
    private Long id;

    private String remark;

    private Date createTime;

    private Date lastRunTime;

    private Date lastEndTime;

    private Date lastErrorTime;

    private Integer runCount;

    private Integer errorCount;

    private Boolean isDelete;

    private Boolean isOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastRunTime() {
        return lastRunTime;
    }

    public void setLastRunTime(Date lastRunTime) {
        this.lastRunTime = lastRunTime;
    }

    public Date getLastEndTime() {
        return lastEndTime;
    }

    public void setLastEndTime(Date lastEndTime) {
        this.lastEndTime = lastEndTime;
    }

    public Date getLastErrorTime() {
        return lastErrorTime;
    }

    public void setLastErrorTime(Date lastErrorTime) {
        this.lastErrorTime = lastErrorTime;
    }

    public Integer getRunCount() {
        return runCount;
    }

    public void setRunCount(Integer runCount) {
        this.runCount = runCount;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Boolean getIsOn() {
        return isOn;
    }

    public void setIsOn(Boolean isOn) {
        this.isOn = isOn;
    }
}