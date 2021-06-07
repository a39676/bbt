package demo.clawing.scheduleClawing.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;
import finance.cryptoCoin.pojo.dto.CryptoCoinDataDTO;

public class CryptoCoinDailyDataResult extends CommonResult {

	private CryptoCoinDataDTO data;

	public CryptoCoinDataDTO getData() {
		return data;
	}

	public void setData(CryptoCoinDataDTO data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "CryptoCoinDailyDataResult [data=" + data + ", getCode()=" + getCode() + ", getResult()=" + getResult()
				+ ", getMessage()=" + getMessage() + ", isSuccess()=" + isSuccess() + ", isFail()=" + isFail()
				+ ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}

}
