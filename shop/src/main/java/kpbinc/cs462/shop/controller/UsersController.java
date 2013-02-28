package kpbinc.cs462.shop.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
public class UsersController extends ShopBaseSiteContextController {
	
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
		return "users/list";
	}
	
	@RequestMapping("/{username}")
	public String viewProfile(
			ModelMap model,
			@PathVariable("username") String username) {
		
		model.addAttribute("username", username);
		
		return "users/profile";
	}
	
	public List<String> getAllUsernames() {
		List<String> usernames = new ArrayList<String>(userDetailsManager.getAllUsernames());
		Collections.sort(usernames);
		return usernames;
	}
	
}
