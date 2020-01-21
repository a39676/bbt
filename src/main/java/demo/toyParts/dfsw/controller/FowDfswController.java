package demo.toyParts.dfsw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import demo.baseCommon.controller.CommonController;

@Controller
@RequestMapping(value = "/dfsw")
public class FowDfswController extends CommonController {

//	@Autowired
//	private ForDfswServcie dfswService;
//	
//	@GetMapping(value = "/buildSVNUpdateCommondLine")
//	public ModelAndView buildSVNUpdateCommondLineView() {
//		return new ModelAndView("dfsw/buildSVNUpdateCommondLine");
//	}
//	
//	@PostMapping(value = "/buildSVNUpdateCommondLine")
//	@ResponseBody
//	public BuildSVNUpdateCommondLineResult buildSVNUpdateCommondLine(@RequestBody BuildSVNUpdateCommondLineDTO dto) {
//		return dfswService.buildSVNUpdateCommondLine(dto);
//	}
	
	@GetMapping(value = "/heTongDocument")
	public ModelAndView heTongDocument() {
		return new ModelAndView("dfsw/heTongDocument");
	}
}
