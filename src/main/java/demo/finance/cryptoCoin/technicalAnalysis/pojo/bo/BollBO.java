package demo.finance.cryptoCoin.technicalAnalysis.pojo.bo;

import java.math.BigDecimal;

public class BollBO {

	private BigDecimal ma;
	private BigDecimal upper;
	private BigDecimal lower;

	public BigDecimal getMa() {
		return ma;
	}

	public void setMa(BigDecimal ma) {
		this.ma = ma;
	}

	public BigDecimal getUpper() {
		return upper;
	}

	public void setUpper(BigDecimal upper) {
		this.upper = upper;
	}

	public BigDecimal getLower() {
		return lower;
	}

	public void setLower(BigDecimal lower) {
		this.lower = lower;
	}

	@Override
	public String toString() {
		return "BollBO [ma=" + ma + ", upper=" + upper + ", lower=" + lower + "]";
	}

}
