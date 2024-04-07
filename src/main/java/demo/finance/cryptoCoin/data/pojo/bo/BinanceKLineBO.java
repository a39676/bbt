package demo.finance.cryptoCoin.data.pojo.bo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BinanceKLineBO {

	private LocalDateTime openTime;
	private BigDecimal open;
	private BigDecimal High;
	private BigDecimal Low;
	private BigDecimal Close;
	private BigDecimal Volume;
	private LocalDateTime closeTime;
	private BigDecimal baseAssetVolume;
	private BigDecimal numberOfTrades;
	private BigDecimal takerBuyVolume;
	private BigDecimal takerBuyBaseAssetVolume;
	private String unknown;

	public LocalDateTime getOpenTime() {
		return openTime;
	}

	public void setOpenTime(LocalDateTime openTime) {
		this.openTime = openTime;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	public BigDecimal getHigh() {
		return High;
	}

	public void setHigh(BigDecimal high) {
		High = high;
	}

	public BigDecimal getLow() {
		return Low;
	}

	public void setLow(BigDecimal low) {
		Low = low;
	}

	public BigDecimal getClose() {
		return Close;
	}

	public void setClose(BigDecimal close) {
		Close = close;
	}

	public BigDecimal getVolume() {
		return Volume;
	}

	public void setVolume(BigDecimal volume) {
		Volume = volume;
	}

	public LocalDateTime getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(LocalDateTime closeTime) {
		this.closeTime = closeTime;
	}

	public BigDecimal getBaseAssetVolume() {
		return baseAssetVolume;
	}

	public void setBaseAssetVolume(BigDecimal baseAssetVolume) {
		this.baseAssetVolume = baseAssetVolume;
	}

	public BigDecimal getNumberOfTrades() {
		return numberOfTrades;
	}

	public void setNumberOfTrades(BigDecimal numberOfTrades) {
		this.numberOfTrades = numberOfTrades;
	}

	public BigDecimal getTakerBuyVolume() {
		return takerBuyVolume;
	}

	public void setTakerBuyVolume(BigDecimal takerBuyVolume) {
		this.takerBuyVolume = takerBuyVolume;
	}

	public BigDecimal getTakerBuyBaseAssetVolume() {
		return takerBuyBaseAssetVolume;
	}

	public void setTakerBuyBaseAssetVolume(BigDecimal takerBuyBaseAssetVolume) {
		this.takerBuyBaseAssetVolume = takerBuyBaseAssetVolume;
	}

	public String getUnknown() {
		return unknown;
	}

	public void setUnknown(String unknown) {
		this.unknown = unknown;
	}

	@Override
	public String toString() {
		return "BinanceKLineBO [openTime=" + openTime + ", open=" + open + ", High=" + High + ", Low=" + Low
				+ ", Close=" + Close + ", Volume=" + Volume + ", closeTime=" + closeTime + ", baseAssetVolume="
				+ baseAssetVolume + ", numberOfTrades=" + numberOfTrades + ", takerBuyVolume=" + takerBuyVolume
				+ ", takerBuyBaseAssetVolume=" + takerBuyBaseAssetVolume + ", unknown=" + unknown + "]";
	}

}
