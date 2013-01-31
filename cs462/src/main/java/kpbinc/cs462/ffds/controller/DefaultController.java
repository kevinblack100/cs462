package kpbinc.cs462.ffds.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class DefaultController {

	@RequestMapping
	public String welcome() {
		return "welcome";
	}
	
}
