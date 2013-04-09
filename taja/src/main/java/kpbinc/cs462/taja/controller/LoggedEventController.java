package kpbinc.cs462.taja.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import kpbinc.cs462.shared.model.LoggedEvent;
import kpbinc.cs462.shared.model.manage.LoggedEventManager;
import kpbinc.util.logging.GlobalLogUtils;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/events/logged")
public class LoggedEventController extends TAJABaseSiteContextController {

	//= Member Data ====================================================================================================
	
	@Autowired
	private LoggedEventManager loggedEventManager;
	
	
	//= Initialization =================================================================================================
	
	public LoggedEventController() {
		super();
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	@RequestMapping
	public String getLoggedEventList(ModelMap model) {
		Collection<LoggedEvent> loggedEvents = loggedEventManager.retrieveAll();
		model.addAttribute("loggedEvents", loggedEvents);	
		
		return "events/logged/logged_events_list";
	}
	
}
