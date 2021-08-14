package demo.clawing.demo.pojo.dto;

public class ATBingDemoDTO {

	private BingSearchInHomePageDTO bingSearchInHomePageDTO;

	public BingSearchInHomePageDTO getBingSearchInHomePageDTO() {
		return bingSearchInHomePageDTO;
	}

	public void setBingSearchInHomePageDTO(BingSearchInHomePageDTO bingSearchInHomePageDTO) {
		this.bingSearchInHomePageDTO = bingSearchInHomePageDTO;
	}

	@Override
	public String toString() {
		return "ATBingDemoDTO [bingSearchInHomePageDTO=" + bingSearchInHomePageDTO + "]";
	}

}
