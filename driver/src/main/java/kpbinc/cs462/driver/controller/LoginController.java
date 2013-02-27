package kpbinc.cs462.driver.controller;

import java.io.Serializable;

import kpbinc.cs462.shared.controller.context.CommonApplicationConstants;
import kpbinc.cs462.shared.controller.context.LoggedInUserContext;
import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/secure/signin")
public class LoginController implements Serializable {

	//= Class Data =====================================================================================================
	
	private static final long serialVersionUID = 1L;

	
	//= Member Data ====================================================================================================
	
	@Autowired
	private LoggedInUserContext loggedInUserContext;
	
	@Autowired
	private CommonApplicationConstants applicationConstants;
	
	
	//= Initialization =================================================================================================
	
	public LoginController() {
		GlobalLogUtils.logConstruction(this);
	}
	
	
	//= Interface ======================================================================================================
	
	@RequestMapping(value = "/query")
	public String presentSignin() {
		return "signin";
	}
	
	@RequestMapping(value = "/success")
	public String doSignin() {
		String redirectLocation = "/";
		return "redirect:" + redirectLocation;
	}
	
}
