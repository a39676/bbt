package demo.base.user.pojo.po;

import java.util.Date;

import demo.base.user.pojo.type.UserPrivateLevelType;

public class UsersDetail {
	private Long userId;

	private String nickName;

	private Long lastLoginIp;

	private Integer gender;

	private Long qq;

	private String email;

	private Long mobile;

	private Date lastLoginTime;

	private Date modifyTime;

	private Integer modifyCount;

	private String reservationInformation;

	private Long registIp;

	/** {@link UserPrivateLevelType} */
	private Integer privateLevel;

	private String headImage;

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName == null ? null : nickName.trim();
	}

	public Long getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(Long lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Long getQq() {
		return qq;
	}

	public void setQq(Long qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public Long getMobile() {
		return mobile;
	}

	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getModifyCount() {
		return modifyCount;
	}

	public void setModifyCount(Integer modifyCount) {
		this.modifyCount = modifyCount;
	}

	public String getReservationInformation() {
		return reservationInformation;
	}

	public void setReservationInformation(String reservationInformation) {
		this.reservationInformation = reservationInformation == null ? null : reservationInformation.trim();
	}

	public Long getRegistIp() {
		return registIp;
	}

	public void setRegistIp(Long registIp) {
		this.registIp = registIp;
	}

	public Integer getPrivateLevel() {
		return privateLevel;
	}

	public void setPrivateLevel(Integer privateLevel) {
		this.privateLevel = privateLevel;
	}

	@Override
	public String toString() {
		return "UsersDetail [userId=" + userId + ", nickName=" + nickName + ", lastLoginIp=" + lastLoginIp + ", gender="
				+ gender + ", qq=" + qq + ", email=" + email + ", mobile=" + mobile + ", lastLoginTime=" + lastLoginTime
				+ ", modifyTime=" + modifyTime + ", modifyCount=" + modifyCount + ", reservationInformation="
				+ reservationInformation + ", registIp=" + registIp + ", privateLevel=" + privateLevel + ", headImage="
				+ headImage + "]";
	}

}