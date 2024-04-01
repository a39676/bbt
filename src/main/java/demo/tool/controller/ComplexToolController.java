package demo.tool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.baseCommon.controller.CommonController;
import demo.tool.pojo.constant.ToolUrlConstant;
import demo.tool.service.ComplexToolService;
import tool.pojo.constant.CxBbtInteractionUrl;

@Controller
@RequestMapping(value = { ToolUrlConstant.ROOT, CxBbtInteractionUrl.ROOT })
public class ComplexToolController extends CommonController {

	@Autowired
	private ComplexToolService complexToolService;

	@GetMapping(value = CxBbtInteractionUrl.WORKER_PING)
	@ResponseBody
	public String ping() {
		return complexToolService.ping();
	}
}
