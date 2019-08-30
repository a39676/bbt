package demo.tool.pojo.vo;

import java.util.Properties;

public class MailLoginElement {

	private String userName;
	private String password;
	private Properties smtpProperties;
	private Properties imapProperties;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Properties getSmtpProperties() {
		return smtpProperties;
	}
	public void setSmtpProperties(Properties smtpProperties) {
		this.smtpProperties = smtpProperties;
	}
	public Properties getImapProperties() {
		return imapProperties;
	}
	public void setImapProperties(Properties imapProperties) {
		this.imapProperties = imapProperties;
	}
	
	@Override
	public String toString() {
		return "MailLoginElement [userName=" + userName + ", password=" + password + ", smtpProperties="
				+ smtpProperties + ", imapProperties=" + imapProperties + "]";
	}

}
