package demo.tool.service;

import auxiliaryCommon.pojo.result.CommonResult;
import net.sf.json.JSONObject;

public interface ComplexToolService {

	CommonResult cleanTmpFiles(JSONObject data);

}
