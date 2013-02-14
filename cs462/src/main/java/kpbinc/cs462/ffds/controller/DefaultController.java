package kpbinc.cs462.ffds.controller;

import kpbinc.common.util.logging.GlobalLogUtils;
import kpbinc.cs462.ffds.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/")
public class DefaultController {

	@Autowired
	private LoginController loginController;
	
	@Autowired
	private User subject;
	
	public DefaultController() {
		GlobalLogUtils.logConstruction(this);
	}
	
	@RequestMapping
	public String welcome() {
		return "welcome";
	}
	
}
