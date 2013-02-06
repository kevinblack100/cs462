package kpbinc.cs462.ffds.controller;

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
		int tmpbrkpnt = 1;
	}
	
	@RequestMapping
	public String welcome() {
		return "welcome";
	}
	
}
