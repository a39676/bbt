package demo.clawing.localClawing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.baseCommon.controller.CommonController;
import demo.clawing.localClawing.service.BossZhiPinLocalClawingService;
import demo.clawing.localClawing.service.LaGouLocalClawingService;
import demo.clawing.scheduleClawing.service.impl.MaiMaiScheduleClawingServiceImpl;

@Controller
@RequestMapping(value = "/localClawing")
public class LocalClawingController extends CommonController {

	@Autowired
	private BossZhiPinLocalClawingService bossZhiPinLocalClawingService;
	@Autowired
	private LaGouLocalClawingService laGouLocalClawingService;
	@Autowired
	private MaiMaiScheduleClawingServiceImpl maiMaiLocalClawingServiceImpl;
	
	@GetMapping(value = "/insertBossZhiPinLocalClawing")
	public void insertBossZhiPinLocalClawing() {
		bossZhiPinLocalClawingService.insertLocalClawingEvent();
	}
	
	@GetMapping(value = "/insertLaGouLocalClawing")
	public void insertLaGouLocalClawing() {
		laGouLocalClawingService.insertLocalClawingEvent();
	}
	
	@GetMapping(value = "/insertMaiMaiLocalClawing")
	public void insertMaiMaiLocalClawing() {
		maiMaiLocalClawingServiceImpl.insertLocalClawingEvent();
	}
	
}
