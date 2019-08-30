package demo.base.user.pojo.po;

import java.util.Date;

public class UserIp {
	private Long userId;

	private Long ip;

	private Long forwardIp;

	private String serverName;

	private String uri;

	private Date createTime;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getIp() {
		return ip;
	}

	public void setIp(Long ip) {
		this.ip = ip;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri == null ? null : uri.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getForwardIp() {
		return forwardIp;
	}

	public void setForwardIp(Long forwardIp) {
		this.forwardIp = forwardIp;
	}

	@Override
	public String toString() {
		return "UserIp [userId=" + userId + ", ip=" + ip + ", forwardIp=" + forwardIp + ", serverName=" + serverName
				+ ", uri=" + uri + ", createTime=" + createTime + "]";
	}

}