package demo.baseCommon.pojo.result;

import demo.baseCommon.pojo.type.ResultType;

public abstract class CommonResultBase<T> {

	private boolean success = false;
	
	private String code;

	private String message;
	
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getResult() {
		return code;
	}

	@SuppressWarnings("rawtypes")
	public CommonResultBase setResult(String result) {
		this.code = result;
		return this;
	}

	public String getMessage() {
		return message;
	}

	@SuppressWarnings("rawtypes")
	public CommonResultBase setMessage(String message) {
		this.message = message;
		return this;
	}

	@SuppressWarnings("rawtypes")
	public CommonResultBase addMessage(String message) {
		this.message += message;
		return this;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setIsSuccess() {
		this.setResult(ResultType.success.getCode());
		this.setSuccess(true);
	}

	public void setIsFail() {
		this.setResult(ResultType.fail.getCode());
		this.setSuccess(false);
	}

	public void normalSuccess() {
		this.setResult(ResultType.success.getCode());
		this.setSuccess(true);
	}

	public void normalFail() {
		this.setResult(ResultType.fail.getCode());
		this.setSuccess(false);
	}

	public void successWithMessage(String desc) {
		this.setResult(ResultType.success.getCode());
		this.setMessage(desc);
		this.setSuccess(true);
	}

	public void failWithMessage(String desc) {
		this.setResult(ResultType.fail.getCode());
		this.setMessage(desc);
		this.setSuccess(false);
	}

	public void fillWithResult(ResultType resultType) {
		this.setResult(resultType.getCode());
		this.setMessage(resultType.getName());
		if(ResultType.success.getCode().equals(resultType.getCode())) {
			this.success = true;
		} else {
			this.success = false;
		}
	}

	@Override
	public String toString() {
		return "CommonResult [result=" + code + ", message=" + message + "]";
	}

}