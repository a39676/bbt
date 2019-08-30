package demo.base.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import demo.baseCommon.controller.CommonController;
import net.sf.json.JSONObject;

@Controller
public class CsrfController extends CommonController {
	
	
	@GetMapping(value = {"/csrf", "/_csrf"})
    public void getCsrfToken(HttpServletRequest request, HttpServletResponse response) {
        CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        JSONObject json = JSONObject.fromObject(csrf);
        outputJson(response, json);
    }
	
}