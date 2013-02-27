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
@RequestMapping(value = "/secure/accounts")
public class AccountsController extends DriverBaseSiteContextController implements Serializable {

	//= Class Data =====================================================================================================
	
	private static final long serialVersionUID = 1L;

	
	//= Member Data ====================================================================================================
	
	@Autowired
	private LoggedInUserContext loggedInUserContext;
	
	@Autowired
	private CommonApplicationConstants applicationConstants;
	
	
	//= Initialization =================================================================================================
	
	public AccountsController() {
		GlobalLogUtils.logConstruction(this);
	}
	
	
	//= Interface ======================================================================================================
	
	@RequestMapping(value = "/signin")
	public String getSigninForm() {
		return "signin";
	}
	
	@RequestMapping(value = "/authenticate/success")
	public String authenticatedSuccessfully() {
		String redirectLocation = "/pages/";
		return "redirect:" + redirectLocation;
	}
	
}
