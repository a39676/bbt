package demo.clawing.common.service;

import java.util.List;

import demo.selenium.service.impl.SeleniumCommonService;

public abstract class JobClawingCommonService extends SeleniumCommonService {

	/* 不察看的公司名(contain?) */
	protected static List<String> filterName = List.of(
			"联想利泰", "腾讯", "网易", "阿里", "合众", "蜜源", "华为", "东方思维", "三盟",
			"广州战游网络科技有限公司", "中邮消费金融", "广发", "多益网络", "有车以后", "新大陆金融", 
			"车行易", "阿尔法医疗", "新文溯", "各有所爱", "大圣科技", "真义", "星火网络", "禾多云力", "Veeking",
			"斗游", "晓教育", "casa systems", "花生日记", "美云智数", "美的", "斗游", "奇异果科技", "群接龙", 
			"猎游", "互娱", "贷", "游戏", "GAME", "G啊"
			);
	
	protected static List<String> targetJobTitle = List.of(
			"猎头", "人事", "HR", "HRBP", "招聘", "测试"
			);
	
	/* 每个关键词, 进入点击条数 */
	protected static final int keywordClickMaxCount = 50;
	
	protected boolean inFilter(String str) {
		if(filterName == null || filterName.isEmpty() || str == null) {
			return false;
		}
		
		for(String f : filterName) {
			if(str.toLowerCase().contains(f.toLowerCase())) {
				return true;
			}
		}
		
		return false;
	}
	
}
