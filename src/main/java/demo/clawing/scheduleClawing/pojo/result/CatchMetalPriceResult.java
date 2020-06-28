package demo.clawing.scheduleClawing.pojo.result;

import demo.baseCommon.pojo.result.CommonResultBBT;
import metal.pojo.dto.PreciousMetailPriceDTO;

public class CatchMetalPriceResult extends CommonResultBBT {

	private PreciousMetailPriceDTO priceDTO;

	public PreciousMetailPriceDTO getPriceDTO() {
		return priceDTO;
	}

	public void setPriceDTO(PreciousMetailPriceDTO priceDTO) {
		this.priceDTO = priceDTO;
	}

	@Override
	public String toString() {
		return "CatchMetalPriceResult [priceDTO=" + priceDTO + ", success=" + success + "]";
	}

}
