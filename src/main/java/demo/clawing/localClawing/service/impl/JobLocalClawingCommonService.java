package demo.clawing.localClawing.service.impl;

import java.util.List;

import demo.selenium.service.impl.SeleniumCommonService;

public abstract class JobLocalClawingCommonService extends SeleniumCommonService {

	/* 不察看的公司名(contain?) */
	protected static List<String> filterName = List.of(
			"联想利泰", "腾讯", "网易", "阿里", "合众", "蜜源", "华为", "东方思维", "三盟",
			"新大陆金融", "网商微贷", "贷", "游戏"
			);
	
	/* 每个关键词, 进入点击条数 */
	protected static final int keywordClickMaxCount = 50;
	
	protected boolean inFilter(String str) {
		if(filterName == null || filterName.isEmpty() || str == null) {
			return false;
		}
		
		for(String f : filterName) {
			if(str.contains(f)) {
				return true;
			}
		}
		
		return false;
	}
}
