package demo.tool.service;

import java.time.LocalDateTime;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.baseCommon.pojo.result.CommonResultBBT;
import net.sf.json.JSONObject;

public interface ComplexToolService {

	CommonResult cleanTmpFiles(JSONObject data);

	CommonResultBBT cleanTmpFiles(String targetFolder, String extensionName, LocalDateTime oldestCreateTime);

}
