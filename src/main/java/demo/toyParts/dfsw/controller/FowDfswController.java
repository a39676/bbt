package demo.toyParts.dfsw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.baseCommon.controller.CommonController;
import demo.toyParts.dfsw.pojo.dto.BuildSVNUpdateCommondLineDTO;
import demo.toyParts.dfsw.pojo.result.BuildSVNUpdateCommondLineResult;
import demo.toyParts.dfsw.service.ForDfswServcie;

@Controller
@RequestMapping(value = "/dfsw")
public class FowDfswController extends CommonController {

	@Autowired
	private ForDfswServcie dfswService;
	
	@GetMapping(value = "/buildSVNUpdateCommondLine")
	public ModelAndView buildSVNUpdateCommondLineView() {
		return new ModelAndView("dfsw/buildSVNUpdateCommondLine");
	}
	
	@PostMapping(value = "/buildSVNUpdateCommondLine")
	@ResponseBody
	public BuildSVNUpdateCommondLineResult buildSVNUpdateCommondLine(@RequestBody BuildSVNUpdateCommondLineDTO dto) {
		return dfswService.buildSVNUpdateCommondLine(dto);
	}
}
