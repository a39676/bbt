package demo.socket.domain.bo;

public class SocketMessageTemplate {

	private String from;
	private String text;
	private String timeStr;

	public SocketMessageTemplate(String from, String text, String timeStr) {
		this.from = from;
		this.text = text;
		this.timeStr = timeStr;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTimeStr() {
		return timeStr;
	}

	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}

}
