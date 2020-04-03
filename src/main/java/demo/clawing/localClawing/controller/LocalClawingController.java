package demo.clawing.localClawing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.baseCommon.controller.CommonController;
import demo.clawing.localClawing.service.BossZhiPinLocalClawingService;

@Controller
@RequestMapping(value = "/localClawing")
public class LocalClawingController extends CommonController {

	@Autowired
	private BossZhiPinLocalClawingService bossZhiPinLocalClawingService;
	
	@GetMapping(value = "/insertBossZhiPinLocalClawing")
	public void insertWuYiSign() {
		bossZhiPinLocalClawingService.insertLocalClawingEvent();
	}
	
}
