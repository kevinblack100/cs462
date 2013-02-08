package kpbinc.cs462.ffds.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;

import kpbinc.cs462.ffds.model.AuthorizationTokenManager;

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
public class UserController {
	
	@Autowired
	private LoginController loginController;
	
	@Autowired
	private OAuthController oauthController;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private AuthorizationTokenManager authorizationTokenManager;
	
	public UserController() {
		int tmpbrkpnt = 1;
	}
	
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
		
		boolean userLoggedIn = loginController.isUserLoggedIn(username);
		model.addAttribute("userLoggedIn", userLoggedIn);
		
		if (hasFoursquareAuthToken) {
			String retrieveCheckinsURL = "https://api.foursquare.com/v2/users/self/checkins?oauth_token=";
			Token accessToken = authorizationTokenManager.getAuthorizationToken(username, "foursquare");
			retrieveCheckinsURL += accessToken.getToken();
			retrieveCheckinsURL += "&sort=newestfirst";
			if (!userLoggedIn) {
				retrieveCheckinsURL += "&limit=1";
			}
			String checkinJsonData = oauthController.getDetailsForUser("foursquare", retrieveCheckinsURL, username);
			model.addAttribute("checkins", checkinJsonData);
		}
		
		return "users/profile";
	}
	
	public List<String> getAllUsernames() {
		List<String> usernames = new ArrayList<String>();
		
		try {
			// TODO extract and use file parsing code from CS 679 project
			String filePath = servletContext.getRealPath("/WEB-INF/ffds/users.properties");
			File userPropertiesFile = new File(filePath);
			FileReader fr = new FileReader(userPropertiesFile);
			BufferedReader br = new BufferedReader(fr);
			
			String line = br.readLine();
			while (line != null) {
				int equalsIndex = line.indexOf("=");
				String username = line.substring(0, equalsIndex);
				usernames.add(username);
				
				line = br.readLine();
			}
			
			br.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		Collections.sort(usernames);
		
		return usernames;
	}
	
}
