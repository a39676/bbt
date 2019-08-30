package demo.base.system.pojo.bo;

import java.util.Date;

public class SystemConstant {

	private Integer id;

	private String constantName;

	private String constantValue;

	private Date createTime;

	private Boolean isDelete;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getConstantName() {
		return constantName;
	}

	public void setConstantName(String constantName) {
		this.constantName = constantName;
	}

	public String getConstantValue() {
		return constantValue;
	}

	public void setConstantValue(String constantValue) {
		this.constantValue = constantValue;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

}