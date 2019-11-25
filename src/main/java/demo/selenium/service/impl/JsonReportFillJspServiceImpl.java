package demo.selenium.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.selenium.service.JsonReportFillJspService;
import ioHandle.FileUtilCustom;

@Service
public class JsonReportFillJspServiceImpl extends CommonService implements JsonReportFillJspService {

	@Autowired
	private FileUtilCustom ioUtil;
	
	
}
