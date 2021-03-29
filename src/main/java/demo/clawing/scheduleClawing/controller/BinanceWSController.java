package demo.clawing.scheduleClawing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.baseCommon.controller.CommonController;
import demo.clawing.scheduleClawing.service.component.webSocket.BinanceWSClient;

@Controller
@RequestMapping(value = { "/binanceWS" })
public class BinanceWSController extends CommonController {

	@Autowired
	private BinanceWSClient ws;
	
	@GetMapping("/wsManager")
	public ModelAndView wsManager() {
		return new ModelAndView("toolJSP/binanceWebSocketManager");
	}
	
	@GetMapping("/destory")
	@ResponseBody
	public String destoryWS() {
		ws.wsDestory();
		return "done";
	}
}
