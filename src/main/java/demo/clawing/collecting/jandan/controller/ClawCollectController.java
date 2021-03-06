package demo.clawing.collecting.jandan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.clawing.collecting.jandan.pojo.constant.ClawCollectingUrl;
import demo.clawing.collecting.jandan.service.JianDanCollectingService;

@Controller
@RequestMapping(value = ClawCollectingUrl.root)
public class ClawCollectController {

	@Autowired
	private JianDanCollectingService jianDanCollectingService;

	@GetMapping(value = ClawCollectingUrl.insertCollectingJianDanEvent)
	public void insertCollectingJianDanEvent() {
		jianDanCollectingService.insertCollectingJianDanEvent();
	}
	
}
