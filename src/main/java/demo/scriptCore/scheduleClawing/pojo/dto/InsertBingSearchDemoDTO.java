package demo.scriptCore.scheduleClawing.pojo.dto;

import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testEvent.searchingDemo.pojo.dto.BingSearchInHomePageDTO;

public class InsertBingSearchDemoDTO extends AutomationTestInsertEventDTO {

	private BingSearchInHomePageDTO bingSearchInHomePageDTO;

	public BingSearchInHomePageDTO getBingSearchInHomePageDTO() {
		return bingSearchInHomePageDTO;
	}

	public void setBingSearchInHomePageDTO(BingSearchInHomePageDTO bingSearchInHomePageDTO) {
		this.bingSearchInHomePageDTO = bingSearchInHomePageDTO;
	}

	@Override
	public String toString() {
		return "InsertBingSearchDemoDTO [bingSearchInHomePageDTO=" + bingSearchInHomePageDTO + "]";
	}

}
