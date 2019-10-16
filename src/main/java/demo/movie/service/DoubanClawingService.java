package demo.movie.service;

import org.openqa.selenium.WebDriver;

import demo.movie.pojo.result.DoubanSubClawingResult;
import demo.testCase.pojo.po.TestEvent;

public interface DoubanClawingService {

	DoubanSubClawingResult clawing(WebDriver d, String cnTitle, TestEvent te);

}
