package demo.scriptCore.scheduleClawing.currencyExchangeRate.pojo.dto;

import java.math.BigDecimal;

public class CurrencyExchageRateDataDTO {

	private Integer currencyCodeFrom;
	private Integer currencyCodeTo;
	private BigDecimal currencyAmountFrom;
	private BigDecimal currencyAmountTo;
	private BigDecimal yesterdayBugHigh;
	private BigDecimal yesterdaySellHigh;
	private BigDecimal yesterdayBuyLow;
	private BigDecimal yesterdaySellLow;

	public Integer getCurrencyCodeFrom() {
		return currencyCodeFrom;
	}

	public void setCurrencyCodeFrom(Integer currencyCodeFrom) {
		this.currencyCodeFrom = currencyCodeFrom;
	}

	public Integer getCurrencyCodeTo() {
		return currencyCodeTo;
	}

	public void setCurrencyCodeTo(Integer currencyCodeTo) {
		this.currencyCodeTo = currencyCodeTo;
	}

	public BigDecimal getCurrencyAmountFrom() {
		return currencyAmountFrom;
	}

	public void setCurrencyAmountFrom(BigDecimal currencyAmountFrom) {
		this.currencyAmountFrom = currencyAmountFrom;
	}

	public BigDecimal getCurrencyAmountTo() {
		return currencyAmountTo;
	}

	public void setCurrencyAmountTo(BigDecimal currencyAmountTo) {
		this.currencyAmountTo = currencyAmountTo;
	}

	public BigDecimal getYesterdayBugHigh() {
		return yesterdayBugHigh;
	}

	public void setYesterdayBugHigh(BigDecimal yesterdayBugHigh) {
		this.yesterdayBugHigh = yesterdayBugHigh;
	}

	public BigDecimal getYesterdaySellHigh() {
		return yesterdaySellHigh;
	}

	public void setYesterdaySellHigh(BigDecimal yesterdaySellHigh) {
		this.yesterdaySellHigh = yesterdaySellHigh;
	}

	public BigDecimal getYesterdayBuyLow() {
		return yesterdayBuyLow;
	}

	public void setYesterdayBuyLow(BigDecimal yesterdayBuyLow) {
		this.yesterdayBuyLow = yesterdayBuyLow;
	}

	public BigDecimal getYesterdaySellLow() {
		return yesterdaySellLow;
	}

	public void setYesterdaySellLow(BigDecimal yesterdaySellLow) {
		this.yesterdaySellLow = yesterdaySellLow;
	}

	@Override
	public String toString() {
		return "CurrencyExchageRateDataDTO [currencyCodeFrom=" + currencyCodeFrom + ", currencyCodeTo=" + currencyCodeTo
				+ ", currencyAmountFrom=" + currencyAmountFrom + ", currencyAmountTo=" + currencyAmountTo
				+ ", yesterdayBugHigh=" + yesterdayBugHigh + ", yesterdaySellHigh=" + yesterdaySellHigh
				+ ", yesterdayBuyLow=" + yesterdayBuyLow + ", yesterdaySellLow=" + yesterdaySellLow + "]";
	}

}
