package demo.clawing.medicine.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.system.pojo.bo.SystemConstant;
import demo.base.system.service.impl.SystemConstantService;
import demo.clawing.medicine.service.ClawingSinaMedicineGlobalOptionService;
import demo.selenium.service.impl.SeleniumCommonService;

@Service
public class ClawingSinaMedicineGlobalOptionServiceImpl extends SeleniumCommonService implements ClawingSinaMedicineGlobalOptionService {

	@Autowired
	private SystemConstantService constantService;
	
	protected String mainSavingFolder_win = "d:/auxiliary";
	protected String mainSavingFolder_linx = "/home/u2";
	protected String medicineDocumentFolder = "/medicineDocument";

	protected String medicineDocumentFolderRedisKey = "medicineDocumentFolder";
	
	@Override
	public String getMedicineDocumentDir() {
		String medicineDocumentFolderPath = constantService.getValByName(medicineDocumentFolder);

		if (StringUtils.isNotBlank(medicineDocumentFolderPath)) {
			globalOptionService.checkFolderExists(medicineDocumentFolderPath);
			return pathChangeByDetectOS(medicineDocumentFolderPath);
		}

		if (isWindows()) {
			medicineDocumentFolderPath = mainSavingFolder_win + medicineDocumentFolder;
		} else {
			medicineDocumentFolderPath = mainSavingFolder_linx + medicineDocumentFolder;
		}
		medicineDocumentFolderPath = pathChangeByDetectOS(medicineDocumentFolderPath);

		SystemConstant constant = new SystemConstant();
		constant.setConstantName(medicineDocumentFolder);
		constant.setConstantValue(medicineDocumentFolderPath);
		constantService.setValByName(constant);

		globalOptionService.checkFolderExists(medicineDocumentFolderPath);
		return medicineDocumentFolderPath;
	}
}
