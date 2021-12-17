package demo.toyParts.multipleDB.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.toyParts.multipleDB.mapper.ArticleLongMapper;

@Controller
@RequestMapping(value = "/multipleDbTest")
public class MultipleDbController {

	@Autowired
	private ArticleLongMapper mapper;
	
	@GetMapping(value = "/t1")
	@ResponseBody
	public Long test() {
		return mapper.testMultipleDB(LocalDateTime.now().minusDays(1L));
	}
}
