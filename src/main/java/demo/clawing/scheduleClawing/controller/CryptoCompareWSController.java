package demo.clawing.scheduleClawing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.baseCommon.controller.CommonController;
import demo.clawing.scheduleClawing.service.component.webSocket.CryptoCompareWSClient;

@Controller
@RequestMapping(value = { "/cryptoCompareWS" })
public class CryptoCompareWSController extends CommonController {

	@Autowired
	private CryptoCompareWSClient ws;
	
	@GetMapping("/addChannel")
	@ResponseBody
	public String addChannel(@RequestParam(value = "channel") String channelStr) {
		ws.addSubscription(channelStr);
		return "done";
	}
	
	@GetMapping("/removeChannel")
	@ResponseBody
	public String removeChannel(@RequestParam(value = "channel") String channelStr) {
		ws.removeSubscription(channelStr);
		return "done";
	}
	
	@GetMapping("/destory")
	@ResponseBody
	public String destoryWS() {
		ws.wsDestory();
		return "done";
	}
}
