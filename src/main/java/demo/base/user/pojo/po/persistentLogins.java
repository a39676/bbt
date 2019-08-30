package demo.base.user.pojo.po;

import java.util.Date;

public class persistentLogins {
    private String series;

    private String userName;

    private String token;

    private Date lastUse;

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series == null ? null : series.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public Date getLastUse() {
        return lastUse;
    }

    public void setLastUse(Date lastUse) {
        this.lastUse = lastUse;
    }
}