package demo.base.system.service.impl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import demo.autoTestBase.testEvent.service.TestEventService;
import demo.base.system.service.ShutdownService;
import demo.baseCommon.service.CommonService;

@Service
public class ShutdownServiceImpl extends CommonService implements ShutdownService, ApplicationContextAware{

	private ApplicationContext context;
	
	@Autowired
	private TestEventService testEventService;
	
	@Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.context = ctx;
    }
	
	@Override
	public String shutdownContext(String keyInput) {
		
		String key = constantService.getValByName("shutdownKey", true);
		
		if(key == null || !key.equals(keyInput)) {
			return "false";
		}
		
		if(!testEventService.checkExistsRuningEvent()) {
			((ConfigurableApplicationContext) context).close();
			// if context close success, will NOT return anything 
			return "close error";
		} else {
			return "still running event";
		}
		
    }
}
