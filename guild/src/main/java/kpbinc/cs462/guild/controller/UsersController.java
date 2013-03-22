package kpbinc.cs462.guild.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletContext;

import kpbinc.cs462.shared.model.manage.InMemoryPersistentUserDetailsManager;
import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	
	//= Initialization =================================================================================================
	
	public UsersController() {
		GlobalLogUtils.logConstruction(this);
	}
	
	
	//= Interface ======================================================================================================
	
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
		return "users/user_profile";
	}
	
	public List<String> getAllUsernames() {
		List<String> usernames = new ArrayList<String>(userDetailsManager.getAllUsernames());
		Collections.sort(usernames);
		return usernames;
	}
	
}
