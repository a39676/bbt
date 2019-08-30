package demo.tool.service;

import demo.baseCommon.pojo.result.CommonResult;
import net.sf.json.JSONObject;

public interface ComplexToolService {

	CommonResult cleanTmpFiles(JSONObject data);

}
