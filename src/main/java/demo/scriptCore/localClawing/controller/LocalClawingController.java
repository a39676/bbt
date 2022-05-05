package demo.scriptCore.localClawing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.baseCommon.controller.CommonController;
import demo.scriptCore.localClawing.service.LinkedinService;
import demo.scriptCore.localClawing.service.MathBattleService;
import demo.scriptCore.localClawing.service.PrankService;
import demo.scriptCore.localClawing.service.TextbookDownloadService;

@Controller
@RequestMapping(value = "/l")
public class LocalClawingController extends CommonController {

	@Autowired
	private LinkedinService linkedinService;
	@Autowired
	private PrankService prankService;
	@Autowired
	private MathBattleService mathBattleService;
	@Autowired
	private TextbookDownloadService textbookDownloadService;
	
	@GetMapping(value = "/l2")
	@ResponseBody
	public String l2() throws InterruptedException {
		linkedinService.buildRelationship();
		return "done";
	}
	
	@GetMapping(value = "/l3")
	@ResponseBody
	public String l3() throws InterruptedException {
		prankService.prankBatch();
		return "done";
	}
	
	@GetMapping(value = "/l4")
	@ResponseBody
	public String l4() throws Exception {
		mathBattleService.start();
		return "done";
	}
	
	@GetMapping(value = "/l5")
	@ResponseBody
	public String l5() throws Exception {
		textbookDownloadService.downloading();
		return "done";
	}
}
