package kpbinc.cs462.sandbox.spring.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hello")
public class HelloController {

	@RequestMapping
	public String hello() {
		// return the name of the view to render
		return "hello";
	}
	
	@RequestMapping("/goodbye")
	public String goodbye() {
		// return the name of the view to render
		return "goodbye";
	}
	
	@RequestMapping(value = "/goodbye", params = "temporary=true")
	public String seeyou() {
		// return the name of the view to render
		return "seeyou";
	}
	
}
