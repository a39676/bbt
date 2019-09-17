package demo.tool.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import demo.baseCommon.pojo.type.ResultType;
import demo.tool.pojo.constant.ToolPathConstant;
import demo.tool.pojo.result.UploadResult;
import demo.tool.service.UploadService;
import ioHandle.FileUtilCustom;

@Service
public class UploadServiceImpl implements UploadService {

	@Autowired
	private FileUtilCustom ioUtil;
	
	@Override
	public Map<String, MultipartFile> getFiles(MultipartHttpServletRequest request) {
		return request.getFileMap();
	}

	@Override
	public UploadResult saveFiles(Map<String, MultipartFile> fileMap, String storePath) {
		MultipartFile tmpFile = null;
		UploadResult result = new UploadResult();
		List<String> uploadSuccessFileNameList = new ArrayList<String>();
		List<String> uploadFailFileNameList = new ArrayList<String>();
		
		boolean flag = true;
		
		for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
			flag = true;
			tmpFile = entry.getValue();
			
			try {
				ioUtil.byteToFile(tmpFile.getBytes(), storePath + tmpFile.getOriginalFilename());
			} catch (IOException e) {
				e.printStackTrace();
				flag = false;
				uploadFailFileNameList.add(tmpFile.getOriginalFilename());
			}
			
			if(flag) {
				uploadSuccessFileNameList.add(tmpFile.getOriginalFilename());
			}
		}
		result.setUploadSuccessFileNameList(uploadSuccessFileNameList);
		result.setUploadFailFileNameList(uploadFailFileNameList);
		result.setUploadTime(new Date());
		result.setIsSuccess();
		return result;
	}
	
	@Override
	public UploadResult saveUploadExcel(Map<String, MultipartFile> fileMap) {
		MultipartFile tmpFile = null;
		String fileName = null;
		
		Map<String, MultipartFile> targetFileMap = new HashMap<String, MultipartFile>();
		
		for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
			tmpFile = entry.getValue();
			fileName = tmpFile.getOriginalFilename();
			if(fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
				targetFileMap.put(entry.getKey(), entry.getValue());
			}
		}
		return saveFiles(targetFileMap, ToolPathConstant.getExcelTmpStorePath());
	}
	
	@Override
	public UploadResult uploadFakeFTPHandle(MultipartHttpServletRequest request) {
		UploadResult result = new UploadResult();
		Map<String, MultipartFile> fileMap = getFiles(request);
		String savePath = request.getParameter("savePath");
		if(StringUtils.isBlank(savePath) || fileMap.size() < 1) {
			result.fillWithResult(ResultType.errorParam);
			return result;
		}
		
		result = saveFiles(fileMap, savePath);
		
		return result;
	}

}
