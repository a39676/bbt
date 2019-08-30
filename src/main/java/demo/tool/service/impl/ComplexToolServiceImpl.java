package demo.tool.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import demo.baseCommon.pojo.result.CommonResult;
import demo.baseCommon.pojo.type.ResultType;
import demo.tool.pojo.constant.ToolPathConstant;
import demo.tool.service.ComplexToolService;
import net.sf.json.JSONObject;
import numericHandel.NumericUtilCustom;

@Service
public class ComplexToolServiceImpl implements ComplexToolService {
	
	@Override
	public CommonResult cleanTmpFiles(JSONObject data) {
		Integer passTime = 30; // minute
		CommonResult result = new CommonResult();
		
		if(data.containsKey("passTime") 
				&& NumericUtilCustom.matchInteger(data.getString("passTime")) 
				&& (data.getInt("passTime") > passTime)) {
			passTime = data.getInt("passTime");
		}
		
		File mainFolder = new File(ToolPathConstant.getTmpStorePath());
		File[] files = mainFolder.listFiles();
		BasicFileAttributes attr;
		List<String> successFile = new ArrayList<String>();
		List<String> failFile = new ArrayList<String>();
		
		StringBuffer messageBuffer = new StringBuffer();
		
		for(File file : files) {
			try {
				attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
				if((System.currentTimeMillis() - attr.creationTime().toMillis()) >= (passTime * 1000L * 60)) {
					file.delete();
				}
				successFile.add(file.getName());
			} catch (IOException e) {
				failFile.add(file.getName());
				e.printStackTrace();
			}
		}
		
		if(failFile.size() > 0) {
			result.setResult(ResultType.fail.getCode());
			messageBuffer.append("fail on :" + failFile.toString());
			messageBuffer.append("\n");
		} else {
			result.setResult(ResultType.success.getCode());
		}
		messageBuffer.append("deleted :" + successFile.toString());
		result.setMessage(messageBuffer.toString());
		
		return result;
	}
	
}
