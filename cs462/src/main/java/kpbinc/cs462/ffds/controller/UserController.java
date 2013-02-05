package kpbinc.cs462.ffds.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

	public UserController() {
		int tmpbrkpnt = 1;
	}
	
	@RequestMapping
	public String listAll() {
		return "users/list";
	}
}
