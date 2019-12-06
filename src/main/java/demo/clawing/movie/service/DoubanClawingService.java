package demo.clawing.movie.service;

import org.openqa.selenium.WebDriver;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.clawing.movie.pojo.result.DoubanSubClawingResult;

public interface DoubanClawingService {

	DoubanSubClawingResult clawing(WebDriver d, String cnTitle, TestEvent te);

}
