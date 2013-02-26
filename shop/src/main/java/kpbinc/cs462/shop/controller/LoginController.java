package kpbinc.cs462.shop.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/secure/signin")
public class LoginController extends BaseController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ApplicationConstants applicationConstants;
	
	public LoginController() {
		GlobalLogUtils.logConstruction(this);
	}
	
	@RequestMapping(value = "/query")
	public String presentSignin() {
		return "signin";
	}
	
	@RequestMapping(value = "/success")
	public String doSignin(HttpServletResponse response) throws IOException {
		UserDetails signedInUserDetails = getLoggedInUserContext().getSignedInUserDetails();
		String redirectLocation = "/ffds/";
		if (signedInUserDetails != null) {
			String username = signedInUserDetails.getUsername();
			redirectLocation += "users/" + username;
		}
		
		return "redirect:" + redirectLocation;
	}
	
}
