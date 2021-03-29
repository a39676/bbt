package demo.clawing.scheduleClawing.pojo.bo;

import java.util.List;

public class BinanceWebSocketConfigBO {

	private String uri;
	private List<String> subs;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public List<String> getSubs() {
		return subs;
	}

	public void setSubs(List<String> subs) {
		this.subs = subs;
	}

	@Override
	public String toString() {
		return "BinanceWebSocketConfigBO [uri=" + uri + ", subs=" + subs + "]";
	}

}
