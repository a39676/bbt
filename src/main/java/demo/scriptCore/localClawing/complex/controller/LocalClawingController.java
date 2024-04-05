package demo.scriptCore.localClawing.complex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.baseCommon.controller.CommonController;
import demo.scriptCore.localClawing.complex.service.LinkedinService;
import demo.scriptCore.localClawing.complex.service.MathBattleService;
import demo.scriptCore.localClawing.complex.service.PrankService;
import demo.scriptCore.localClawing.complex.service.StoryBerriesDownloadService;
import demo.scriptCore.localClawing.complex.service.TextbookDownloadService;

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
	@Autowired
	private StoryBerriesDownloadService storyBerriesDownloadService;

	@GetMapping(value = "/l2")
	@ResponseBody
	public String l2() {
		linkedinService.buildRelationship();
		return "done";
	}

	@GetMapping(value = "/l3")
	@ResponseBody
	public String l3() {
		prankService.prankBatch();
		return "done";
	}

	@GetMapping(value = "/l4")
	@ResponseBody
	public String l4() {
		mathBattleService.start();
		return "done";
	}

	@GetMapping(value = "/l5")
	@ResponseBody
	public String l5() {
		textbookDownloadService.downloading();
		return "done";
	}

	@GetMapping(value = "/l6")
	@ResponseBody
	public String l6(@RequestParam(value = "url") String url) {
		storyBerriesDownloadService.downloading(url);
		return "done";
	}
}
