package demo.tool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.baseCommon.controller.CommonController;
import demo.tool.pojo.constant.ToolUrlConstant;
import demo.tool.service.impl.ComplexToolServiceImpl;

@Controller
@RequestMapping(value = ToolUrlConstant.root)
public class ComplexToolController extends CommonController {

	@SuppressWarnings("unused")
	@Autowired
	private ComplexToolServiceImpl complexToolServiceImpl;

}
