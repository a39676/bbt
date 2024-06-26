package demo.base.system.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import demo.base.system.service.impl.SystemOptionService;
import demo.baseCommon.controller.CommonController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionController extends CommonController implements HandlerExceptionResolver {

	private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);

	@Autowired
	protected SystemOptionService systemConstantService;

	@ExceptionHandler({ Exception.class })
	public ModelAndView handleException(HttpServletRequest request, Exception e, String message) {
		ModelAndView view = new ModelAndView("baseJSP/errorCustom");
		log.error(e.toString());
		if (systemConstantService.getIsDebuging()) {
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
		if (systemConstantService.getIsDebuging()) {
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
		if (systemConstantService.getIsDebuging()) {
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
		if (systemConstantService.getIsDebuging()) {
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
		if (systemConstantService.getIsDebuging()) {
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

}
