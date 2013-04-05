package kpbinc.cs462.taja.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kpbinc.util.logging.GlobalLogUtils;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/")
public class DefaultController extends TAJABaseSiteContextController {

	//= Initialization =================================================================================================
	
	public DefaultController() {
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	@RequestMapping
	public String getWelcomePage() {
		return "welcome";
	}
	
}
