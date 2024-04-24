package demo.finance.cryptoCoin.technicalAnalysis.pojo.bo;

import java.math.BigDecimal;

public class KdjBO {

	private BigDecimal rsv;
	private BigDecimal k;
	private BigDecimal d;
	private BigDecimal j;

	public BigDecimal getRsv() {
		return rsv;
	}

	public void setRsv(BigDecimal rsv) {
		this.rsv = rsv;
	}

	public BigDecimal getK() {
		return k;
	}

	public void setK(BigDecimal k) {
		this.k = k;
	}

	public BigDecimal getD() {
		return d;
	}

	public void setD(BigDecimal d) {
		this.d = d;
	}

	public BigDecimal getJ() {
		return j;
	}

	public void setJ(BigDecimal j) {
		this.j = j;
	}

	@Override
	public String toString() {
		return "KdjBO [rsv=" + rsv + ", k=" + k + ", d=" + d + ", j=" + j + "]";
	}

}
