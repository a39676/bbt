package demo.base.system.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import demo.base.system.pojo.bo.SystemConstantStore;
import demo.base.system.pojo.constant.DebugStatusConstant;
import demo.base.system.service.impl.SystemConstantService;
import demo.baseCommon.controller.CommonController;

@ControllerAdvice
public class ExceptionController extends CommonController implements HandlerExceptionResolver {
	
	private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);

	@Autowired
	protected SystemConstantService systemConstantService;

	@ExceptionHandler({ Exception.class })
	public ModelAndView handleException(HttpServletRequest request, Exception e, String message) {
		ModelAndView view = new ModelAndView("baseJSP/errorCustom");
		log.error(e.toString());
		findDebugStatus();
		if(findDebugStatus()) {
			view.addObject("message", e.toString());
		} else {
			view.addObject("message", "很抱歉,居然出现了异常");
		}
		view.addObject("urlRedirect", "/");

		e.printStackTrace();
		return view;
	}

	@ExceptionHandler({ IOException.class })
	public ModelAndView handleIOException(HttpServletRequest request, Exception e) {
		log.error(e.toString());
		ModelAndView view = new ModelAndView("baseJSP/errorCustom");
		if(findDebugStatus()) {
			view.addObject("message", e.toString());
		} else {
			view.addObject("message", "IOException");
		}
		view.addObject("urlRedirect", "/");
		e.printStackTrace();
		return view;
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView handleNoHandlerFoundException(HttpServletRequest request, Exception e) {
		log.error(e.toString());
		ModelAndView view = new ModelAndView("baseJSP/errorCustom");
		if(findDebugStatus()) {
			view.addObject("message", e.toString());
		} else {
			view.addObject("message", "NoHandlerFoundException");
		}
		view.addObject("urlRedirect", "/");

		e.printStackTrace();
		return view;
	}

	@ExceptionHandler({ SQLException.class })
	public ModelAndView handleSQLException(HttpServletRequest request, Exception e) {
		log.error(e.toString());
		ModelAndView view = new ModelAndView("baseJSP/errorCustom");
		if(findDebugStatus()) {
			view.addObject("message", e.toString());
		} else {
			view.addObject("message", "SQLException");
		}
		view.addObject("urlRedirect", "/");
		e.printStackTrace();
		return view;
	}

	@ExceptionHandler({ RuntimeException.class })
	public ModelAndView hanedleRuntimeException(HttpServletRequest request, Exception e) {
		log.error(e.toString());
		ModelAndView view = new ModelAndView("baseJSP/errorCustom");
		if(findDebugStatus()) {
			view.addObject("message", e.toString());
		} else {
			view.addObject("message", "RuntimeException");
		}
		view.addObject("urlRedirect", "/");
		e.printStackTrace();
		return view;
	}
	

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		log.error(ex.toString());
		try {

			if (response.getStatus() == 500) {
				return handleException(request, ex, "500");
			} else {
				return handleException(request, ex, "???");
			}
		} catch (Exception handlerException) {
			
		}
		return null;
	}

	private boolean findDebugStatus() {
		String debugStatusStr = systemConstantService.getValByName(SystemConstantStore.debugStatus);
		if(DebugStatusConstant.debuging.equals(debugStatusStr)) {
			return true;
		} else {
			return false;
		}
	}
}
