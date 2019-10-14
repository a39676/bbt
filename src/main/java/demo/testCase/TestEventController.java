package demo.testCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.testCase.service.TestEventService;

@Controller
@RequestMapping(value = "/testEvent")
public class TestEventController {
	
	@Autowired
	private TestEventService teService;

	@GetMapping(value = "/fixMovieClawingTestEventStatus")
	@ResponseBody
	public String fixMovieClawingTestEventStatus() {
		int c = teService.fixMovieClawingTestEventStatus();
		return String.valueOf(c);
	}
}
