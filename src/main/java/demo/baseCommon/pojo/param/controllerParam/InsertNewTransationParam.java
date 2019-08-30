package demo.baseCommon.pojo.param.controllerParam;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import demo.baseCommon.pojo.param.CommonControllerParam;
import net.sf.json.JSONObject;

public class InsertNewTransationParam implements CommonControllerParam {

	private String accountNumber;
	private Integer transationType;
	private BigDecimal fixCreditQuota;
	private BigDecimal transationAmount;
	private String transationParties;
	private String transationDate;
	private String remark;

	@Override
	public InsertNewTransationParam fromJson(JSONObject j)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		InsertNewTransationParam p = mapper.readValue(j.toString(), InsertNewTransationParam.class);
		if(StringUtils.isNotBlank(p.getTransationDate())) {
			p.setTransationDate(p.getTransationDate().replaceAll("[^\\d-/:\\\\]", " "));
		}
		;
		return p;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Integer getTransationType() {
		return transationType;
	}

	public void setTransationType(Integer transationType) {
		this.transationType = transationType;
	}

	public String getTransationDate() {
		return transationDate;
	}

	public void setTransationDate(String transationDate) {
		this.transationDate = transationDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getFixCreditQuota() {
		return fixCreditQuota;
	}

	public void setFixCreditQuota(BigDecimal fixCreditQuota) {
		this.fixCreditQuota = fixCreditQuota;
	}

	public BigDecimal getTransationAmount() {
		return transationAmount;
	}

	public void setTransationAmount(BigDecimal transationAmount) {
		this.transationAmount = transationAmount;
	}

	public String getTransationParties() {
		return transationParties;
	}

	public void setTransationParties(String transationParties) {
		this.transationParties = transationParties;
	}

	@Override
	public String toString() {
		return "InsertNewTransationParam [accountNumber=" + accountNumber + ", transationType=" + transationType
				+ ", fixCreditQuota=" + fixCreditQuota + ", transationAmount=" + transationAmount
				+ ", transationParties=" + transationParties + ", transationDate=" + transationDate + ", remark="
				+ remark + "]";
	}

}
