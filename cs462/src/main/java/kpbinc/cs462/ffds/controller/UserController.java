package kpbinc.cs462.ffds.controller;

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
	
	public UserController() {
		int tmpbrkpnt = 1;
	}
	
	@RequestMapping
	public String listAll() {
		return "users/list";
	}
	
	@RequestMapping("/{user-name}")
	public String viewProfile(
			ModelMap model,
			@PathVariable("user-name") String username) {
		model.addAttribute("username", username);
		return "users/profile";
	}
	
}
