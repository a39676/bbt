package demo.tool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import demo.tool.pojo.constant.ToolUrlConstant;

@Controller
@RequestMapping(value = ToolUrlConstant.root + ToolUrlConstant.database)
public class DatabaseToolController extends ComplexToolController {
	
	@GetMapping(value = ToolUrlConstant.databaseImport)
	public ModelAndView databaseImport() {
		return new ModelAndView("toolJSP/database/databaseImport");
	}
	
}
