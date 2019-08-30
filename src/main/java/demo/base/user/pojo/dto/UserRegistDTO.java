package demo.base.user.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户注册参数")
public class UserRegistDTO {

	@ApiModelProperty(name = "用户名", dataType = "string")
	private String userName;
	@ApiModelProperty(name = "用户昵称", dataType = "string")
	private String nickName;
	private String email;
	private String pwd;
	private String pwdRepeat;
	private String pwdd;
	private Integer gender;
	private String qq;
	private String mobile;
	private String reservationInformation;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPwdRepeat() {
		return pwdRepeat;
	}

	public void setPwdRepeat(String pwdRepeat) {
		this.pwdRepeat = pwdRepeat;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getReservationInformation() {
		return reservationInformation;
	}

	public void setReservationInformation(String reservationInformation) {
		this.reservationInformation = reservationInformation;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwdd() {
		return pwdd;
	}

	public void setPwdd(String pwdd) {
		this.pwdd = pwdd;
	}

}
