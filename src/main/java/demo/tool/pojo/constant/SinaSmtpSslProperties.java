package demo.tool.pojo.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:properties/email/sinaSmtpSsl.properties")
public class SinaSmtpSslProperties {

	@Value("${sina_mail_smtp_auth}")
	private String sinaMailSmtpAuth;

	@Value("${sina_mail_smtp_host}")
	private String sinaMailSmtpHost;

	@Value("${sina_mail_smtp_port}")
	private String sinaMailSmtpPort;

	@Value("${sina_mail_smtp_starttls_enable}")
	private String sinaMailSmtpStarttlsEnable;

	@Value("${sina_mail_smtp_socketFactory_class}")
	private String sinaMailSmtpSocketFactoryClass;

	public String getSinaMailSmtpAuth() {
		return sinaMailSmtpAuth;
	}

	public void setSinaMailSmtpAuth(String sinaMailSmtpAuth) {
		this.sinaMailSmtpAuth = sinaMailSmtpAuth;
	}

	public String getSinaMailSmtpHost() {
		return sinaMailSmtpHost;
	}

	public void setSinaMailSmtpHost(String sinaMailSmtpHost) {
		this.sinaMailSmtpHost = sinaMailSmtpHost;
	}

	public String getSinaMailSmtpPort() {
		return sinaMailSmtpPort;
	}

	public void setSinaMailSmtpPort(String sinaMailSmtpPort) {
		this.sinaMailSmtpPort = sinaMailSmtpPort;
	}

	public String getSinaMailSmtpStarttlsEnable() {
		return sinaMailSmtpStarttlsEnable;
	}

	public void setSinaMailSmtpStarttlsEnable(String sinaMailSmtpStarttlsEnable) {
		this.sinaMailSmtpStarttlsEnable = sinaMailSmtpStarttlsEnable;
	}

	public String getSinaMailSmtpSocketFactoryClass() {
		return sinaMailSmtpSocketFactoryClass;
	}

	public void setSinaMailSmtpSocketFactoryClass(String sinaMailSmtpSocketFactoryClass) {
		this.sinaMailSmtpSocketFactoryClass = sinaMailSmtpSocketFactoryClass;
	}

	@Override
	public String toString() {
		return "SinaSmtpSslProperties [sinaMailSmtpAuth=" + sinaMailSmtpAuth + ", sinaMailSmtpHost=" + sinaMailSmtpHost
				+ ", sinaMailSmtpPort=" + sinaMailSmtpPort + ", sinaMailSmtpStarttlsEnable="
				+ sinaMailSmtpStarttlsEnable + ", sinaMailSmtpSocketFactoryClass=" + sinaMailSmtpSocketFactoryClass
				+ "]";
	}

}
