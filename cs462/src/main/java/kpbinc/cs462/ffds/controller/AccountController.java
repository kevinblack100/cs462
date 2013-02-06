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

	public AccountController() {
		int tmpbrkpnt = 1;
	}
	
	@RequestMapping(value = "/register/query")
	public String getRegistrationForm() {
		return "accounts/register";
	}
	
	@RequestMapping(value = "/register/execute")
	public void registerAccount(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			HttpServletResponse response) throws IOException {
		System.out.println("registering account with username: " + username + " and password: " + password);
		String redirectLocation = response.encodeRedirectURL("/cs462/ffds/secure/signin/query");
		response.sendRedirect(redirectLocation);
	}
	
}
