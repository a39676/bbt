package demo.task.pojo.param.mapperParam;

public class UpdateRunResultParam {

	private Boolean runSuccess;
	private Long id;

	public Boolean getRunSuccess() {
		return runSuccess;
	}

	public void setRunSuccess(Boolean runSuccess) {
		this.runSuccess = runSuccess;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "UpdateRunResultParam [runSuccess=" + runSuccess + ", id=" + id + "]";
	}

}
