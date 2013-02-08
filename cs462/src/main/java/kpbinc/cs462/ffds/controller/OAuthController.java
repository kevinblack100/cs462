package kpbinc.cs462.ffds.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import kpbinc.cs462.ffds.model.AuthorizationTokenManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/oauth")
public class OAuthController {

	@Autowired
	private AuthorizationTokenManager authorizationTokenManager;
	
	public OAuthController() {
		int tmpbrkpnt = 1;
	}
	
	@RequestMapping(value = "/v2/{api}/{username}")
	public void registerOAuthCode(
			@PathVariable("api") String api,
			@PathVariable("username") String username,
			@RequestParam(value = "code", required = true) String code,
			HttpServletResponse response) throws IOException {
		System.out.printf("received code for %s at %s: %s\n", username, api, code);
		
		// TODO retrieve the authorization token with the given code
		
		String redirectLocation = response.encodeRedirectURL("/cs462/ffds/users/" + username);
		response.sendRedirect(redirectLocation);
	}
	
}
