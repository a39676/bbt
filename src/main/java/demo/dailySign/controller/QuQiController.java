package demo.dailySign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.dailySign.pojo.constant.QuQiUrl;
import demo.dailySign.service.QuQiDailySignService;

@Controller
@RequestMapping(value = QuQiUrl.root)
public class QuQiController {

	@Autowired
	private QuQiDailySignService service;
	
	@GetMapping(value = QuQiUrl.insertSign)
	public void testSign() {
		service.insertclawingEvent();
	}
}
