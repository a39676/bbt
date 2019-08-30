package demo.tool.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import demo.tool.pojo.result.UploadResult;

public interface UploadService {

	public Map<String, MultipartFile> getFiles(MultipartHttpServletRequest request);

	public UploadResult saveFiles(Map<String, MultipartFile> fileMap, String storePath);

	UploadResult uploadFakeFTPHandle(MultipartHttpServletRequest request);

	UploadResult saveUploadExcel(Map<String, MultipartFile> fileMap);

}
