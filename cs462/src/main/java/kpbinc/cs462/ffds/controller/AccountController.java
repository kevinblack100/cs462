package kpbinc.cs462.ffds.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/secure/accounts")
public class AccountController {

	public static final String DEFAULT_PASSWORD = "password";
	
	public AccountController() {
		int tmpbrkpnt = 1;
	}
	
	public String getDefaultPassword() {
		return DEFAULT_PASSWORD;
	}
	
	@RequestMapping(value = "/register/query")
	public String getRegistrationForm() {
		return "accounts/register";
	}
	
	@RequestMapping(value = "/register/execute")
	public void registerAccount(
			@RequestParam(value = "username", required = true) String username,
			HttpServletResponse response) throws IOException {
		System.out.println("registering account with username: " + username + " and default password: " + getDefaultPassword());
		String redirectLocation = response.encodeRedirectURL("/cs462/ffds/secure/signin/query");
		response.sendRedirect(redirectLocation);
	}
	
}
