package demo.base.system.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;

import demo.base.system.pojo.constant.BaseUrl;
import demo.baseCommon.controller.CommonController;

@Controller
public class AuxiliaryController extends CommonController {
	
	@GetMapping(value = { BaseUrl.robot })
	public void robots(HttpServletRequest request, HttpServletResponse response) {
		Resource resource = new ClassPathResource("/static_resources/txt/robots.txt");
		
		String mimeType= "application/text/plain";
		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + resource.getFilename() +"\""));
		try {
			response.setContentLength((int)resource.contentLength());
			InputStream is = resource.getInputStream();
			FileCopyUtils.copy(is, response.getOutputStream());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping(value = { BaseUrl.favicon })
	public void favicon(HttpServletResponse response) {
		Resource resource = new ClassPathResource("/static_resources/favicon.ico");
		
		String mimeType= "application/image/x-icon";
		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + resource.getFilename() +"\""));
		try {
			response.setContentLength((int)resource.contentLength());
			InputStream is = resource.getInputStream();
			FileCopyUtils.copy(is, response.getOutputStream());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}