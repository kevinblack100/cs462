package kpbinc.cs462.poc.spring.mvc;

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
	
}
