package demo.tool.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import demo.baseCommon.controller.CommonController;
import demo.tool.pojo.constant.ToolUrlConstant;
import demo.tool.service.impl.ComplexToolServiceImpl;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = ToolUrlConstant.root)
public class ComplexToolController extends CommonController {

	@Autowired
	private ComplexToolServiceImpl complexToolServiceImpl;

	@PostMapping(value = "/cleanTmpFiles")
	public void cleanTmpFiles(@RequestBody String data, HttpServletResponse response) {
		JSONObject jsonInput = getJson(data);
		
		// TODO 已经有几个临时文件夹,请参照 ToolPathConstant
		JSONObject jsonOutput = JSONObject.fromObject(complexToolServiceImpl.cleanTmpFiles(jsonInput));
		try {
			response.getWriter().print(jsonOutput);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping(value = "/systemOption")
	public ModelAndView systemOption() {
		return new ModelAndView("toolJSP/systemOption");
	}
}
