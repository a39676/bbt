package demo.base.system.pojo.bo;

public class IpRecordBO {

	private String remoteAddr;
	private String forwardAddr;

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public String getForwardAddr() {
		return forwardAddr;
	}

	public void setForwardAddr(String forwardAddr) {
		this.forwardAddr = forwardAddr;
	}

	@Override
	public String toString() {
		return "IpRecordBO [remoteAddr=" + remoteAddr + ", forwardAddr=" + forwardAddr + ", getRemoteAddr()="
				+ getRemoteAddr() + ", getForwardAddr()=" + getForwardAddr() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
