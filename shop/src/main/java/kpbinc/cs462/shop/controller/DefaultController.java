package kpbinc.cs462.shop.controller;

import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/")
public class DefaultController extends BaseController {

	public DefaultController() {
		GlobalLogUtils.logConstruction(this);
	}
	
	@RequestMapping
	public String welcome() {
		return "welcome";
	}
	
}
