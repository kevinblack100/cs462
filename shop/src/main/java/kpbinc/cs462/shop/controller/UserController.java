package kpbinc.cs462.shop.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletContext;

import kpbinc.cs462.shared.model.manage.AuthorizationTokenManager;
import kpbinc.cs462.shared.model.manage.InMemoryPersistentUserDetailsManager;
import kpbinc.util.logging.GlobalLogUtils;

import org.scribe.exceptions.OAuthConnectionException;
import org.scribe.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope(value = "request")
@RequestMapping("/users")
public class UserController extends ShopBaseSiteContextController {
	
	//==================================================================================================================
	// Class Data
	//==================================================================================================================
	
	private static final Logger logger = Logger.getLogger(UserController.class.getName());
	
	
	//==================================================================================================================
	// Member Data
	//==================================================================================================================

	@Autowired
	private OAuthController oauthController;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private InMemoryPersistentUserDetailsManager userDetailsManager;
	
	@Autowired
	private AuthorizationTokenManager authorizationTokenManager;
	
	
	//==================================================================================================================
	// Initialization
	//==================================================================================================================
	
	public UserController() {
		GlobalLogUtils.logConstruction(this);
	}
	
	//==================================================================================================================
	// Interface
	//==================================================================================================================
	
	@RequestMapping
	public String listAll(ModelMap model) {
		List<String> allUsernames = getAllUsernames();
		model.addAttribute("usernames", allUsernames);
		return "users/list";
	}
	
	@RequestMapping("/{user-name}")
	public String viewProfile(
			ModelMap model,
			@PathVariable("user-name") String username) {
		
		model.addAttribute("username", username);
		
		boolean hasFoursquareAuthToken = authorizationTokenManager.hasAuthorizationToken(username, "foursquare");
		model.addAttribute("hasFoursquareAuthToken", hasFoursquareAuthToken);
		
		boolean userLoggedIn = getLoggedInUserContext().isUserLoggedIn(username);
		model.addAttribute("userLoggedIn", userLoggedIn);
		
		if (hasFoursquareAuthToken) {
			String retrieveCheckinsURL = "https://api.foursquare.com/v2/users/self/checkins?oauth_token=";
			Token accessToken = authorizationTokenManager.getAuthorizationToken(username, "foursquare");
			retrieveCheckinsURL += accessToken.getToken();
			retrieveCheckinsURL += "&sort=newestfirst";
			if (!userLoggedIn) {
				retrieveCheckinsURL += "&limit=1";
			}
			
			try {
				String checkinJsonData = oauthController.getDetailsForUser("foursquare", retrieveCheckinsURL, username);
				model.addAttribute("checkins", checkinJsonData);
			}
			catch (OAuthConnectionException e) {
				logger.info("Caught exception: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		return "users/profile";
	}
	
	public List<String> getAllUsernames() {
		List<String> usernames = new ArrayList<String>(userDetailsManager.getAllUsernames());
		Collections.sort(usernames);
		return usernames;
	}
	
}
