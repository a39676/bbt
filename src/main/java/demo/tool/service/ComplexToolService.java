package demo.tool.service;

import java.time.LocalDateTime;

import auxiliaryCommon.pojo.result.CommonResult;

public interface ComplexToolService {

	CommonResult cleanTmpFiles(String targetFolder, String extensionName, LocalDateTime oldestCreateTime);

}
