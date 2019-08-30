package demo.base.user.pojo.po;

import java.util.Date;

public class UserAttempts {
    private Integer userId;

    private String userName;

    private Date attemptTime;

    private Boolean statu;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Date getAttemptTime() {
        return attemptTime;
    }

    public void setAttemptTime(Date attemptTime) {
        this.attemptTime = attemptTime;
    }

    public Boolean getStatu() {
        return statu;
    }

    public void setStatu(Boolean statu) {
        this.statu = statu;
    }
}