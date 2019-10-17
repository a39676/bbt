package demo.selenium.pojo.bo;

/**
 * 
 * help for build xpath
 * 未规范, 暂以bo存放
 * 
 */
public class XpathBuilderBO {

	private String xpath;
	
	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

	public XpathBuilderBO start(String tagName) {
		this.xpath = "//" + tagName;
		return this;
	}
	
	public XpathBuilderBO addAttribute(String attributeName, String attributeValue) {
		this.xpath = this.xpath + String.format("[@%s='%s']", attributeName, attributeValue);
		return this;
	}
	
	public XpathBuilderBO addAttributeContain(String attributeName, String attributeValue) {
		this.xpath = this.xpath + String.format("[contains(@%s,'%s')]", attributeName, attributeValue);
		return this;
	}
	
	public XpathBuilderBO addAttributeStartWith(String attributeName, String attributeValue) {
		this.xpath = this.xpath + String.format("[starts-with(@%s,'%s')]", attributeName, attributeValue);
		return this;
	}
	
	public XpathBuilderBO addAttributeText(String text) {
		this.xpath = this.xpath + String.format("[text()='%s')]", text);
		return this;
	}
	
	public XpathBuilderBO findChild(String childTagName) {
		return findChild(childTagName, null);
	}
	
	public XpathBuilderBO findChild(String childTagName, Integer childIndex) {
		if(childIndex == null) {
			this.xpath = this.xpath + String.format("/child::%s", childTagName);
		} else {
			this.xpath = this.xpath + String.format("/child::%s[%d]", childTagName, childIndex);
		}
		return this;
	}
	
	public XpathBuilderBO findParent(String parentTagName) {
		this.xpath = this.xpath + String.format("//parent::%s", parentTagName);
		return this;
	}

	@Override
	public String toString() {
		return "XpathBuilderBO [xpath=" + xpath + "]";
	}
	
}
