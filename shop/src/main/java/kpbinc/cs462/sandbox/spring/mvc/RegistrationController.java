package kpbinc.cs462.sandbox.spring.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {

	@RequestMapping(value = "/view/{event-id}")
	public String view(
			@PathVariable("event-id") Long eventID,
			ModelMap model) {
		model.addAttribute("eventID", eventID);
		return "registration/view";
	}
	
}
