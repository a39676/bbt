package demo.scriptCore.medicine.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.system.pojo.bo.SystemConstant;
import demo.scriptCore.medicine.service.ClawingSinaMedicineGlobalOptionService;
import demo.selenium.service.impl.SeleniumCommonService;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class ClawingSinaMedicineGlobalOptionServiceImpl extends SeleniumCommonService implements ClawingSinaMedicineGlobalOptionService {

	@Autowired
	private FileUtilCustom ioUtil;
	
	protected String mainSavingFolder_win = "d:/auxiliary";
	protected String mainSavingFolder_linx = "/home/u2";
	protected String medicineDocumentFolder = "/medicineDocument";

	protected String medicineDocumentFolderRedisKey = "medicineDocumentFolder";
	
	@Override
	public String getMedicineDocumentDir() {
		String medicineDocumentFolderPath = redisOriginalConnectService.getValByName(medicineDocumentFolder);

		if (StringUtils.isNotBlank(medicineDocumentFolderPath)) {
			ioUtil.checkFolderExists(medicineDocumentFolderPath);
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
		redisOriginalConnectService.setValByName(constant);

		ioUtil.checkFolderExists(medicineDocumentFolderPath);
		return medicineDocumentFolderPath;
	}
}
