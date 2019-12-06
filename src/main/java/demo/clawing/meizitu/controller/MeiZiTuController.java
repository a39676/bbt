package demo.clawing.meizitu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.clawing.meizitu.service.ClawingMeiZiTuService;

@Controller
@RequestMapping(value = "/mz")
public class MeiZiTuController {

	@Autowired
	private ClawingMeiZiTuService meiZiTuService;
	
	@GetMapping(value = "/mz")
	public void mzTest() {
		meiZiTuService.meiZiTuMain();
	}
}
