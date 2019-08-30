package demo.fakeFTP.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import demo.baseCommon.controller.CommonController;
import demo.fakeFTP.pojo.constant.FakeFTPUrlConstant;
import demo.fakeFTP.pojo.param.controllerParam.DownloadTargetFileParam;
import demo.fakeFTP.pojo.param.controllerParam.GetFilePathDetailParam;
import demo.fakeFTP.pojo.result.FakeFTPFilePathDetailResult;
import demo.fakeFTP.service.FakeFTPServcie;
import demo.tool.pojo.result.UploadResult;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = FakeFTPUrlConstant.root)
public class FakeFTPController extends CommonController {
	
	@Autowired
	private FakeFTPServcie fakeFTPService;
	
	@GetMapping(value = FakeFTPUrlConstant.getFilePathDetail)
	public ModelAndView getFilePathDetailGet(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView("fakeFTPJSP/filePathDetail");
		GetFilePathDetailParam param = new GetFilePathDetailParam();
		FakeFTPFilePathDetailResult result = fakeFTPService.GetFilePathDetail(param);
		view.addObject("folderPath", param.getFilePath());
		view.addObject("folderDetails", result.getFolderDetails());
		view.addObject("fileDetails", result.getFileDetails());
		view.addObject("message", result.getMessage());
		
		return view;
	}
	
	@PostMapping(value = FakeFTPUrlConstant.getFilePathDetail)
	public ModelAndView getFilePathDetailPost(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView("fakeFTPJSP/fileListDetail");
		GetFilePathDetailParam param = new GetFilePathDetailParam().fromJson(getJson(data));
		FakeFTPFilePathDetailResult result = fakeFTPService.GetFilePathDetail(param);
		if(!param.getFilePath().endsWith("/")) {
			param.setFilePath(param.getFilePath() + "/");
		}
		view.addObject("folderPath", param.getFilePath());
		view.addObject("folderDetails", result.getFolderDetails());
		view.addObject("fileDetails", result.getFileDetails());
		view.addObject("message", result.getMessage());
		
		return view;
	}
	
	@PostMapping(value = FakeFTPUrlConstant.downloadThis)
	public void downloadThis(@RequestParam(value = "filePath", defaultValue = "" ) String filePath, HttpServletRequest request, HttpServletResponse response) throws IOException {
		DownloadTargetFileParam param = new DownloadTargetFileParam();
		param.setFilePath(filePath);
		fakeFTPService.downloadTargetFile(response, param);
	}
	
	@PostMapping(value = FakeFTPUrlConstant.fakeFTPUpload)
	public void uploadFakeFTPHandle(MultipartHttpServletRequest request, HttpServletResponse response) {
		UploadResult result = fakeFTPService.fakeFTPuploadFile(request, response);
		outputJson(response, JSONObject.fromObject(result));
	}
}
