package demo.selenium.pojo.bo;

import java.util.ArrayList;
import java.util.List;

public class ByXpathConditionBO {

	private String tagName;

	private List<ByXpathConditionSubBO> kvList;

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public List<ByXpathConditionSubBO> getKvList() {
		return kvList;
	}

	public void setKvList(List<ByXpathConditionSubBO> kvList) {
		this.kvList = kvList;
	}

	@Override
	public String toString() {
		return "ByXpathBO [kvList=" + kvList + "]";
	}
	
	public static ByXpathConditionBO build(String tagName, String key, String value) {
		ByXpathConditionBO bo = new ByXpathConditionBO();
		if (bo.kvList == null) {
			bo.setKvList(new ArrayList<ByXpathConditionSubBO>());
		}
		bo.setTagName(tagName);
		ByXpathConditionSubBO ele = new ByXpathConditionSubBO();
		ele.setKey(key);
		ele.setValue(value);
		bo.getKvList().add(ele);
		return bo;
	}

	public ByXpathConditionBO addCondition(String key, String value) {
		ByXpathConditionSubBO ele = new ByXpathConditionSubBO();
		ele.setKey(key);
		ele.setValue(value);
		this.kvList.add(ele);
		return this;
	}
}
