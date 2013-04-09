package kpbinc.cs462.taja.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

	//= Class Data =====================================================================================================
	
	private static class MostRecentFirstLoggedEventComparator implements Comparator<LoggedEvent> {

		private static final int BEFORE = -1;
		private static final int AFTER = 1;
		private static final int EQUALS = 0;
		
		
		@Override
		public int compare(LoggedEvent lhs, LoggedEvent rhs) {
			if (lhs == null) {
				return AFTER;
			}
			// else
			if (rhs == null) {
				return BEFORE;
			}
			// else
			if (lhs.getId() < rhs.getId()) {
				return AFTER;
			}
			// else
			if (rhs.getId() < lhs.getId()) {
				return BEFORE;
			}
			// else
			return EQUALS;
		}
		
	}
	
	
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
		List<LoggedEvent> loggedEvents = new ArrayList<LoggedEvent>(loggedEventManager.retrieveAll());
		Collections.sort(loggedEvents, new MostRecentFirstLoggedEventComparator());
		model.addAttribute("loggedEvents", loggedEvents);	
		
		return "events/logged/logged_events_list";
	}
	
}
