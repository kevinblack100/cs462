package kpbinc.cs462.ffds.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kpbinc.cs462.ffds.model.AuthorizationTokenManager;
import kpbinc.cs462.ffds.model.OAuthServiceManager;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Foursquare2Api;
import org.scribe.exceptions.OAuthException;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
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
	
	@Autowired
	private OAuthServiceManager oauthServiceManager;
	
	public OAuthController() {
		int tmpbrkpnt = 1;
	}
	
	@RequestMapping(value = "/v2/authorize/{username}/{api}")
	public void authorizeUserAt(
			@PathVariable("username") String username,
			@PathVariable("api") String api,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		OAuthService service = oauthServiceManager.getOAuthService(api);
		
		String redirectLocation = null;
		if (service != null) {
			String authorizationURL = service.getAuthorizationUrl(null);
			System.out.println("generated authorization target URL: " + authorizationURL);
			
			redirectLocation = response.encodeRedirectURL(authorizationURL);
		}
		else {
			redirectLocation = response.encodeRedirectURL("/cs462/ffds/users/" + username);
		}
		
		response.sendRedirect(redirectLocation);
	}
	
	@RequestMapping(value = "/v2/requesttoken/{api}/{username}")
	public void registerOAuthCode(
			@PathVariable("api") String api,
			@PathVariable("username") String username,
			@RequestParam(value = "code", required = true) String code,
			HttpServletResponse response) throws IOException {
		System.out.printf("received code for %s at %s: %s\n", username, api, code);
		
		OAuthService service = oauthServiceManager.getOAuthService(api);
		if (service != null) {
			try {
				Verifier verifier = new Verifier(code);
				Token accessToken = service.getAccessToken(null, verifier);
				authorizationTokenManager.createOrUpdateAuthorizationToken(username, api, accessToken);
			}
			catch (OAuthException e) {
				e.printStackTrace();
			}
		}

		String redirectLocation = response.encodeRedirectURL("/cs462/ffds/users/" + username);
		response.sendRedirect(redirectLocation);
	}
	
}
