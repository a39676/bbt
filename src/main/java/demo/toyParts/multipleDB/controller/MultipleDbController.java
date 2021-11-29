package demo.toyParts.multipleDB.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.toyParts.multipleDB.mapper.ArticleLongMapper;
import demo.toyParts.multipleDB.pojo.po.ArticleLong;
import demo.toyParts.multipleDB.pojo.po.ArticleLongExample;

@Controller
@RequestMapping(value = "/multipleDbTest")
public class MultipleDbController {

	@Autowired
	private ArticleLongMapper mapper;
	
	@GetMapping(value = "/t1")
	@ResponseBody
	public List<ArticleLong> test() {
		ArticleLongExample example = new ArticleLongExample();
		example.createCriteria().andIsDeleteEqualTo(false);
		return mapper.selectByExample(example);
	}
}
