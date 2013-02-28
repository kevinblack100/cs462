package kpbinc.cs462.driver.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import kpbinc.cs462.shared.model.manage.AuthorizationTokenManager;
import kpbinc.cs462.shared.model.manage.OAuthServiceManager;
import kpbinc.util.logging.GlobalLogUtils;

import org.scribe.exceptions.OAuthException;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
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
public class OAuthController extends DriverBaseSiteContextController {

	//= Member Data ====================================================================================================
	
	@Autowired
	private AuthorizationTokenManager authorizationTokenManager;
	
	@Autowired
	private OAuthServiceManager oauthServiceManager;

	
	//= Initialization =================================================================================================
	
	public OAuthController() {
		GlobalLogUtils.logConstruction(this);
	}
	
	
	//= Interface ======================================================================================================
	
	@RequestMapping(value = "/v2/authorize/{username}/{api}")
	public String authorizeUserAt(
			@PathVariable("username") String username,
			@PathVariable("api") String api,
			HttpServletResponse response) throws IOException {
		OAuthService service = oauthServiceManager.getOAuthService(api, username);
		
		String redirectLocation = null;
		if (service != null) {
			String authorizationURL = service.getAuthorizationUrl(null);
			System.out.println("generated authorization target URL: " + authorizationURL);
			
			redirectLocation = response.encodeRedirectURL(authorizationURL);
		}
		else {
			redirectLocation = "/pages/users/" + username;
		}
		
		return "redirect:" + redirectLocation;
	}
	
	@RequestMapping(value = "/v2/requesttoken")
	public String registerOAuthCode(
			@RequestParam(value = "api", required = true) String api,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "code", required = true) String code) {
		System.out.printf("received code for %s at %s: %s\n", username, api, code);
		
		OAuthService service = oauthServiceManager.getOAuthService(api, username);
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

		String redirectLocation = "/pages/users/" + username;
		return "redirect:" + redirectLocation;
	}
	
	public String getDetailsForUser(String api, String requestURL, String username) {
		String result = null;
		
		OAuthService service = oauthServiceManager.getOAuthService(api, username);
		Token accessToken = authorizationTokenManager.getAuthorizationToken(username, api);
		if (   service != null
			&& accessToken != null) {
			OAuthRequest request = new OAuthRequest(Verb.GET, requestURL);
			// TODO implement a signing mechanism to handle foursquare signing
			Response response = request.send();
			result = response.getBody();
		}
		
		return result;
	}
	
}
