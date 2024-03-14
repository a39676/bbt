package demo.scriptCore.scheduleClawing.cnStockMarketData.pojo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SinaStockMarketDataDTO {

	/*
	 * { "day": "2024-01-29 10:50:00", "open": "2898.364", "high": "2900.827",
	 * "low": "2898.066", "close": "2900.827", "volume": "234887800", "ma_price5":
	 * 2898.55, "ma_volume5": 564478280 }
	 */

	private LocalDateTime day;
	private BigDecimal open;
	private BigDecimal high;
	private BigDecimal low;
	private BigDecimal close;
	private BigDecimal volume;

	public LocalDateTime getDay() {
		return day;
	}

	public void setDay(LocalDateTime day) {
		this.day = day;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	public BigDecimal getClose() {
		return close;
	}

	public void setClose(BigDecimal close) {
		this.close = close;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	@Override
	public String toString() {
		return "SinaStockMarketDataDTO [day=" + day + ", open=" + open + ", high=" + high + ", low=" + low + ", close="
				+ close + ", volume=" + volume + "]";
	}

}
