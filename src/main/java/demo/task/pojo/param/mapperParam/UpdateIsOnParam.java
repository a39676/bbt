package demo.task.pojo.param.mapperParam;

public class UpdateIsOnParam {

	private Long id;
	private Boolean isOn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsOn() {
		return isOn;
	}

	public void setIsOn(Boolean isOn) {
		this.isOn = isOn;
	}

	@Override
	public String toString() {
		return "UpdateIsOnParam [id=" + id + ", isOn=" + isOn + "]";
	}

}
