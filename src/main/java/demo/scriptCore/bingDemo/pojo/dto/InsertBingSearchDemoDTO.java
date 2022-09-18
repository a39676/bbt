package demo.scriptCore.bingDemo.pojo.dto;

import autoTest.testEvent.common.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testEvent.scheduleClawing.searchingDemo.pojo.dto.BingSearchInHomePageDTO;

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
