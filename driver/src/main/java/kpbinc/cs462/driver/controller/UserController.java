package kpbinc.cs462.driver.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletContext;

import kpbinc.cs462.driver.model.DriverGuildEventChannel;
import kpbinc.cs462.driver.model.manage.DriverGuildEventChannelManager;
import kpbinc.cs462.shared.model.manage.AuthorizationTokenManager;
import kpbinc.cs462.shared.model.manage.InMemoryPersistentUserDetailsManager;
import kpbinc.cs462.shared.model.manage.OAuthServiceManager;
import kpbinc.net.URLPathBuilder;
import kpbinc.util.logging.GlobalLogUtils;

import org.scribe.exceptions.OAuthConnectionException;
import org.scribe.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Scope(value = "request")
@RequestMapping("/users")
public class UserController extends DriverBaseSiteContextController {
	
	//= Class Data =====================================================================================================
	
	private static final Logger logger = Logger.getLogger(UserController.class.getName());
	
	
	//= Member Data ====================================================================================================

	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private OAuthServiceManager oauthServiceManager;
	
	@Autowired
	private InMemoryPersistentUserDetailsManager userDetailsManager;
	
	@Autowired
	private AuthorizationTokenManager authorizationTokenManager;
	
	@Autowired
	private DriverGuildEventChannelManager driverGuildEventChannelManager;
	
	
	//= Initialization =================================================================================================
	
	public UserController() {
		GlobalLogUtils.logConstruction(this);
	}
	
	
	//= Interface ======================================================================================================
	
	//- Read -----------------------------------------------------------------------------------------------------------
	
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
		
		DriverGuildEventChannel channel = driverGuildEventChannelManager.retrieveByUsername(username);
		model.addAttribute("channel", channel);
		
		boolean hasFoursquareAuthToken = authorizationTokenManager.hasAuthorizationToken(username, "foursquare");
		model.addAttribute("hasFoursquareAuthToken", hasFoursquareAuthToken);
		
		boolean isLoggedInUserProfile = getLoggedInUserContext().isUserLoggedIn(username);
		model.addAttribute("isLoggedInUserProfile", isLoggedInUserProfile);
		
		if (hasFoursquareAuthToken) {
			String retrieveCheckinsURL = "https://api.foursquare.com/v2/users/self/checkins?oauth_token=";
			Token accessToken = authorizationTokenManager.getAuthorizationToken(username, "foursquare");
			retrieveCheckinsURL += accessToken.getToken();
			retrieveCheckinsURL += "&sort=newestfirst";
			if (!isLoggedInUserProfile) {
				retrieveCheckinsURL += "&limit=1";
			}
			
			try {
				String checkinJsonData = oauthServiceManager.callAPI("foursquare", retrieveCheckinsURL, username);
				model.addAttribute("checkins", checkinJsonData);
				// TODO update the UserProfile for the current user with the most recent check-in information
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
	
	//- Update ---------------------------------------------------------------------------------------------------------
	
	@RequestMapping(value = "/{username}/set-send-esl", method = RequestMethod.POST)
	public String setSendESL(
			@PathVariable(value = "username") String username,
			@RequestParam(value = "send-esl", required = true) String sendESL) {
		String redirectLocation = URLPathBuilder.build(getContextPaths().getDynamicRelativePath(), "users");
		
		// TODO validate that logged-in user may perform operation
		UserDetails userDetails = userDetailsManager.retrieve(username);
		if (userDetails != null) {
			redirectLocation = URLPathBuilder.append(redirectLocation, username);
			
			DriverGuildEventChannel channel = driverGuildEventChannelManager.retrieveByUsername(username);
			assert(channel != null);
			if (channel != null) {
				channel.setSendESL(sendESL);
				driverGuildEventChannelManager.update(channel);
			}
			// else TODO set message
		}
		
		return "redirect:" + redirectLocation;
	}
	
}
