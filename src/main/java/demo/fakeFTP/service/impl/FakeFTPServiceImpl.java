package demo.fakeFTP.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import demo.base.system.pojo.bo.SystemConstantStore;
import demo.base.system.service.impl.SystemConstantService;
import demo.baseCommon.pojo.type.ResultType;
import demo.baseCommon.service.CommonService;
import demo.fakeFTP.pojo.param.controllerParam.DownloadTargetFileParam;
import demo.fakeFTP.pojo.param.controllerParam.GetFilePathDetailParam;
import demo.fakeFTP.pojo.result.FakeFTPFileDetail;
import demo.fakeFTP.pojo.result.FakeFTPFilePathDetailResult;
import demo.fakeFTP.service.FakeFTPServcie;
import demo.tool.controller.UploadPriController;
import demo.tool.pojo.result.UploadResult;
import demo.tool.service.DownloadService;

@Service
public class FakeFTPServiceImpl extends CommonService implements FakeFTPServcie {
	
	@Autowired
	private UploadPriController uploadController;
	
	@Autowired
	private DownloadService downloadService;
	@Autowired
	private SystemConstantService systemConstantService;
	
	private String getFateFTPHome() {
		return systemConstantService.getValByName(SystemConstantStore.fakeFTPHome);
	}
	
	@Override
	public FakeFTPFilePathDetailResult GetFilePathDetail(GetFilePathDetailParam param) {
		FakeFTPFilePathDetailResult result = new FakeFTPFilePathDetailResult();
		if(StringUtils.isBlank(param.getFilePath()) || !validPath(param.getFilePath())) {
			result.fillWithResult(ResultType.errorParam);
			return result;
		}
		
		File targetFolder = new File(param.getFilePath());
		
		if(!targetFolder.exists() || !targetFolder.isDirectory()) {
			result.fillWithResult(ResultType.errorParam);
			return result;
		}
		
		List<File> files = Arrays.asList(targetFolder.listFiles());
		List<FakeFTPFileDetail> fileDetails = new ArrayList<FakeFTPFileDetail>();
		List<FakeFTPFileDetail> folderDetails = new ArrayList<FakeFTPFileDetail>();
		
		for(File f : files) {
			if(f.isFile()) {
				fileDetails.add(buildFakeFTPFileDetailFromFile(f));
			} else {
				folderDetails.add(buildFakeFTPFileDetailFromFile(f));
			}
		}
		
		result.setFileDetails(fileDetails);
		result.setFolderDetails(folderDetails);
		result.setIsSuccess();
		
		return result;
	}
	
	private FakeFTPFileDetail buildFakeFTPFileDetailFromFile(File file) {
		FakeFTPFileDetail fileDetail = new FakeFTPFileDetail();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		fileDetail.setPath(file.getAbsolutePath().replaceAll("\\\\", "/"));
		fileDetail.setFileName(file.getName());
		fileDetail.setLastModifyTime(sdf.format(new Date(file.lastModified())));
		if(file.isFile()) {
			fileDetail.setIsFile(true);
		} else {
			fileDetail.setIsFile(false);
		}
		
		Path path = Paths.get(file.getAbsolutePath());
		try {
			BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
			fileDetail.setCreateTime(sdf.format(new Date(attr.creationTime().toMillis())));
			fileDetail.setSize(attr.size());
		} catch (IOException e) {
			return fileDetail;
		}
		return fileDetail;
	}

	@Override
	public void downloadTargetFile(HttpServletResponse response, DownloadTargetFileParam param) throws IOException {
		if(StringUtils.isBlank(param.getFilePath()) || !validPath(param.getFilePath())) {
			downloadService.downloadFile(response, "");
			return;
		}
		downloadService.downloadFile(response, param.getFilePath());
	}

	@Override
	public UploadResult fakeFTPuploadFile(MultipartHttpServletRequest request, HttpServletResponse response) {
		UploadResult result = new UploadResult();
		String savePath = request.getParameter("savePath");
		if(!validPath(savePath)) {
			result.fillWithResult(ResultType.errorParam);
			return result;
		}
		result = uploadController.uploadFakeFTPHandle(request, response);
		return result;
	}
	
	private boolean validPath(String path) {
		if(StringUtils.isBlank(path)) {
			return false;
		}
		
		getFateFTPHome();
		
		if(!path.startsWith(getFateFTPHome()) || path.contains("..")) {
			return false;
		}
		
		return true;
	}
}
