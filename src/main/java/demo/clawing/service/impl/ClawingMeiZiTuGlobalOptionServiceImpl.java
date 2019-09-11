package demo.clawing.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.system.pojo.bo.SystemConstant;
import demo.base.system.service.impl.SystemConstantService;
import demo.baseCommon.service.CommonService;
import demo.clawing.service.ClawingMeiZiTuGlobalOptionService;
import demo.selenium.service.SeleniumGlobalOptionService;

@Service
public class ClawingMeiZiTuGlobalOptionServiceImpl extends CommonService implements ClawingMeiZiTuGlobalOptionService {

	@Autowired
	private SystemConstantService constantService;
	@Autowired
	private SeleniumGlobalOptionService globalOptionService;
	
	protected String mainSavingFolder_win = "d:/auxiliary";
	protected String mainSavingFolder_linx = "/home/u2";
	protected String meiZiTuFolder = "/meiZiTu";

	protected String meiZiTuFolderRedisKey = "meiZiTuFolder";
	
	@Override
	public String getMeiZiTuFolder() {
		String medicineDocumentFolderPath = constantService.getValByName(meiZiTuFolder);

		if (StringUtils.isNotBlank(medicineDocumentFolderPath)) {
			globalOptionService.checkFolderExists(medicineDocumentFolderPath);
			return globalOptionService.pathChangeByDetectOS(medicineDocumentFolderPath);
		}

		if (isWindows()) {
			medicineDocumentFolderPath = mainSavingFolder_win + meiZiTuFolder;
		} else {
			medicineDocumentFolderPath = mainSavingFolder_linx + meiZiTuFolder;
		}
		medicineDocumentFolderPath = globalOptionService.pathChangeByDetectOS(medicineDocumentFolderPath);

		SystemConstant constant = new SystemConstant();
		constant.setConstantName(meiZiTuFolder);
		constant.setConstantValue(medicineDocumentFolderPath);
		constantService.setValByName(constant);

		globalOptionService.checkFolderExists(medicineDocumentFolderPath);
		return medicineDocumentFolderPath;
	}
}