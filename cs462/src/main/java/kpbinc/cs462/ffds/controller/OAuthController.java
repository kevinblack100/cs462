package kpbinc.cs462.ffds.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kpbinc.cs462.ffds.model.AuthorizationTokenManager;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Foursquare2Api;
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
	
	public OAuthController() {
		int tmpbrkpnt = 1;
	}
	
	@RequestMapping(value = "/v2/authorize/{username}/{api}")
	public void authorizeUserAt(
			@PathVariable("username") String username,
			@PathVariable("api") String api,
			HttpServletRequest request) {
		String apiKey = "HBRKOKDL5BRHA3A5KDKNKKODADRI1EDEDMI2JNIH5U23MES2";
		String apiSecret = "EPR5PCGICL5GPYJOMGA31BAF1E01MC0V0R1KE0FRZX5U05XW";
		String callbackURI = "http://requestb.in/1ai0qxl1"; // request.getLocalName() + "/cs462/ffds/oauth/v2/requesttoken/" + api + "/" + username;
		OAuthService service = new ServiceBuilder()
								.provider(Foursquare2Api.class)
								.apiKey(apiKey)
								.apiSecret(apiSecret)
								.callback(callbackURI)
								.build();
		String authorizationURL = service.getAuthorizationUrl(null);
		System.out.println("generated authorization target URL: " + authorizationURL);
	}
	
	@RequestMapping(value = "/v2/requesttoken/{api}/{username}")
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
