package kpbinc.cs462.guild.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletContext;

import kpbinc.cs462.guild.model.GuildUserEventChannel;
import kpbinc.cs462.guild.model.manage.GuildUserEventChannelManager;
import kpbinc.cs462.shared.model.manage.InMemoryPersistentUserDetailsManager;
import kpbinc.net.URLPathBuilder;
import kpbinc.util.logging.GlobalLogUtils;

import org.apache.commons.lang3.StringUtils;
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
public class UsersController extends GuildBaseSiteContextController {
	
	//= Class Data =====================================================================================================
	
	private static final Logger logger = Logger.getLogger(UsersController.class.getName());
	
	
	//= Member Data ====================================================================================================

	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private InMemoryPersistentUserDetailsManager userDetailsManager;
	
	@Autowired
	private GuildUserEventChannelManager guildUserEventChannelManager;
	
	
	//= Initialization =================================================================================================
	
	public UsersController() {
		super();
		GlobalLogUtils.logConstruction(this);
	}
	
	
	//= Interface ======================================================================================================
	
	//- Read -----------------------------------------------------------------------------------------------------------
	
	@RequestMapping
	public String listAll(ModelMap model) {
		List<String> allUsernames = getAllUsernames();
		model.addAttribute("usernames", allUsernames);
		return "users/users_list";
	}
	
	@RequestMapping("/{username}")
	public String viewProfile(
			ModelMap model,
			@PathVariable("username") String username) {
		model.addAttribute("username", username);
		
		GuildUserEventChannel channel = guildUserEventChannelManager.retrieveByUsername(username);
		model.addAttribute("channel", channel);
		
		UserDetails loggedInUserDetails = getLoggedInUserContext().getSignedInUserDetails();
		boolean isLoggedInUserProfile = (   loggedInUserDetails != null
										 && StringUtils.equalsIgnoreCase(username, loggedInUserDetails.getUsername()));
		model.addAttribute("isLoggedInUserProfile", isLoggedInUserProfile);
		
		return "users/user_profile";
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

		// TODO validate the logged in user may perform the operation
		UserDetails userDetails = userDetailsManager.retrieve(username);
		if (userDetails != null) {
			redirectLocation = URLPathBuilder.append(redirectLocation, username);
			
			GuildUserEventChannel channel = guildUserEventChannelManager.retrieveByUsername(username);
			assert(channel != null);
			if (channel != null) {
				channel.setSendESL(sendESL);
				guildUserEventChannelManager.update(channel);
			}
			// else TODO set message
		}
		
		return "redirect:" + redirectLocation;
	}
	
}
