package demo.tool.pojo.param;

import net.sf.json.JSONObject;

public class SetMailBaseParam {
	
	private String mailName;
	private String mailPasswod;

	public String getMailName() {
		return mailName;
	}

	public void setMailName(String mailName) {
		this.mailName = mailName;
	}

	public String getMailPasswod() {
		return mailPasswod;
	}

	public void setMailPasswod(String mailPasswod) {
		this.mailPasswod = mailPasswod;
	}
	
	public SetMailBaseParam fromJson(JSONObject json) {
		SetMailBaseParam param = new SetMailBaseParam();
		param.setMailName(json.getString("mailName"));
		param.setMailPasswod(json.getString("mailPassword"));
		return param;
	}

}
