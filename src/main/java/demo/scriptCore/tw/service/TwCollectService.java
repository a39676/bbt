package demo.scriptCore.tw.service;

public interface TwCollectService {

	void monsterCollecting();

	void skillCollecting();

	void equipmentCollecting();

	void equipmentCollecting2(String sub, String prefix);

	void equipmentCollecting3(String dataUrl, boolean itemNameFirst);

	void equipmentCollectingImgOnly(String dataUrl);

	void itemCollecting(String dataUrl, boolean togetherTD);

}
