package demo.scriptCore.localClawing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.baseCommon.controller.CommonController;
import demo.scriptCore.localClawing.service.HsbcService;

@Controller
@RequestMapping(value = "/l")
public class LocalClawingController extends CommonController {

	@Autowired
	private HsbcService hsbcService;

	@GetMapping(value = "/l1")
	@ResponseBody
	public String l1() throws InterruptedException {
		hsbcService.weixinPreRegBatch();
		return "done";
	}

}
