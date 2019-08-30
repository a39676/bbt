package demo.tool.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import demo.tool.ToolViewConstant;
import demo.tool.pojo.constant.ToolPathConstant;
import demo.tool.pojo.constant.UploadUrlConstant;
import demo.tool.pojo.result.UploadResult;
import demo.tool.service.UploadService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = UploadUrlConstant.uploadPriRoot)
public class UploadPriController extends ComplexToolController {
	
	@Autowired
	private UploadService uploadService;
	
	@GetMapping(value = { UploadUrlConstant.uploadTmp })
	public ModelAndView uploadTestView() {
		ModelAndView view = new ModelAndView(ToolViewConstant.uploadPri);
		view.addObject("storePath", ToolPathConstant.getTmpStorePath());
		view.addObject("uploadUrl", UploadUrlConstant.uploadTmpHandle);
		return view;
	}
	
	@GetMapping(value = { UploadUrlConstant.uploadDatabaseDate })
	public ModelAndView uploadDatabaseDate() {
		ModelAndView view = new ModelAndView(ToolViewConstant.uploadPri);
		view.addObject("storePath", ToolPathConstant.getDatabaseDataImportPath());
		view.addObject("uploadUrl", UploadUrlConstant.uploadDatabaseDateHandle);
		return view;
	}
	
	@PostMapping(value = { UploadUrlConstant.uploadTmpHandle })
	public void uploadTmpHandle(MultipartHttpServletRequest request, HttpServletResponse response) {
		Map<String, MultipartFile> fileMap = uploadService.getFiles(request);
		UploadResult result = uploadService.saveFiles(fileMap, ToolPathConstant.getTmpStorePath());
		outputJson(response, JSONObject.fromObject(result));
	}
	
	@PostMapping(value = { UploadUrlConstant.uploadDatabaseDateHandle })
	public void uploadDatabaseDateHandle(MultipartHttpServletRequest request, HttpServletResponse response) {
		Map<String, MultipartFile> fileMap = uploadService.getFiles(request);
		UploadResult result = uploadService.saveFiles(fileMap, ToolPathConstant.getDatabaseDataImportPath());
		outputJson(response, JSONObject.fromObject(result));
	}
	
	public UploadResult uploadFakeFTPHandle(MultipartHttpServletRequest request, HttpServletResponse response) {
		UploadResult result = uploadService.uploadFakeFTPHandle(request);
		return result;
	}

}
