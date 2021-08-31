package demo.tool.service;

import java.time.LocalDateTime;

import auxiliaryCommon.pojo.result.CommonResult;
import net.sf.json.JSONObject;

public interface ComplexToolService {

	CommonResult cleanTmpFiles(JSONObject data);

	CommonResult cleanTmpFiles(String targetFolder, String extensionName, LocalDateTime oldestCreateTime);

}
