package demo.scriptCore.localClawing.complex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.baseCommon.controller.CommonController;
import demo.scriptCore.localClawing.complex.service.BinanceSymbolCollectService;
import demo.scriptCore.localClawing.complex.service.GateIoSymbolCollectService;
import demo.scriptCore.localClawing.complex.service.LinkedinService;
import demo.scriptCore.localClawing.complex.service.MathBattleService;
import demo.scriptCore.localClawing.complex.service.PrankService;
import demo.scriptCore.localClawing.complex.service.StoryBerriesDownloadService;
import demo.scriptCore.localClawing.complex.service.TextbookDownloadService;
import demo.scriptCore.localClawing.complex.subway.service.SubwayPracticeService;

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
	@Autowired
	private BinanceSymbolCollectService binanceSymbolCollectService;
	@Autowired
	private GateIoSymbolCollectService gateIoSymbolCollectService;
	@Autowired
	private SubwayPracticeService subwayPracticeService;

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
	
	@GetMapping(value = "/l7")
	@ResponseBody
	public String l7() {
		binanceSymbolCollectService.collect();
		return "done";
	}
	
	@GetMapping(value = "/l8")
	@ResponseBody
	public String l8() {
		gateIoSymbolCollectService.collect();
		return "done";
	}
	
	@GetMapping(value = "/l9")
	@ResponseBody
	public String l9() {
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJvcmdJZCI6ImQ1YmNiZjhjLWU1OTUtNDBmZi05YzBjLWYzOWE1ZDAyZjFiYyIsInVzZXJJZCI6IjM0YTI3M2Y2LWU4MDgtNDgzYi1iMTAxLTk4NDczM2QxMDIzOCIsImNsdXN0ZXJJZCI6Imh1YXdlaS1pdGFpIiwiZXhwIjoxNzQwNDYwMTUwfQ.mn0uhJ6XgpD-1MB0o1ncLYhNXirlmKZgua41UKiKGBDRdzdPZyOM10jqIOSVBqMx_y0b-7iDftV2rOC9i7nx0g";
		subwayPracticeService.forPractice(token);
		return "done";
	}
	
}
