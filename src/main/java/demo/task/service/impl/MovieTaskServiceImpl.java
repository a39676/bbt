package demo.task.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.clawing.movie.service.DyttClawingService;
import demo.clawing.movie.service.MovieManagerService;

@Component
public class MovieTaskServiceImpl extends SeleniumTaskCommonServiceImpl {
	
	/*
	 * 2019-01-02
	 * 暂时取消 HomeFei 的爬取
	 */
//	@Autowired
//	private HomeFeiClawingService homeFeiClawingService;
	@Autowired
	private DyttClawingService dyttClawingService;
	@Autowired
	private MovieManagerService movieManagerService;
	
	@Scheduled(cron="43 32 02 * * *") 
	public void insertHomeFeiEvent1() {
//		homeFeiClawingService.insertCollectionEvent();
		dyttClawingService.insertclawingEvent();
	}
	@Scheduled(cron="34 12 08 * * *") 
	public void insertHomeFeiEvent2() {
//		homeFeiClawingService.insertCollectionEvent();
		dyttClawingService.insertclawingEvent();
	}
	@Scheduled(cron="40 39 14 * * *") 
	public void insertHomeFeiEvent3() {
//		homeFeiClawingService.insertCollectionEvent();
		dyttClawingService.insertclawingEvent();
	}
	@Scheduled(cron="40 49 20 * * *") 
	public void insertHomeFeiEvent4() {
//		homeFeiClawingService.insertCollectionEvent();
		dyttClawingService.insertclawingEvent();
	}
	
	@Scheduled(cron="08 12 01 * * *") 
	public void deleteOldHistory() throws IOException {
		movieManagerService.deleteOldHistory();
	}
	
}
