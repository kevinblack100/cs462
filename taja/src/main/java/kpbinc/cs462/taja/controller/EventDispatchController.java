package kpbinc.cs462.taja.controller;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kpbinc.cs462.shared.event.EventDispatcher;
import kpbinc.cs462.shared.event.EventHandler;
import kpbinc.cs462.shared.event.EventTransformer;
import kpbinc.cs462.taja.model.manage.WordCountTaskResultsManager;
import kpbinc.util.logging.GlobalLogUtils;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/esl")
public class EventDispatchController extends TAJABaseSiteContextController {

	//= Member Data ====================================================================================================
	
	@Autowired
	private EventTransformer eventTransformer;
	
	@Autowired
	private WordCountTaskResultsManager wordCountTaskResultsManager;
	
	private Collection<EventHandler> publicEventHandlers;
	
	
	//= Initialization =================================================================================================
	
	public EventDispatchController() {
		super();
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	@RequestMapping(value = "/public/channel") // allow any HTTP method
	public void dispatchEvent(
		HttpServletRequest request,
		HttpServletResponse response) {
		EventDispatcher.dispatchEvent(
				"dispatch public channel event",
				request,
				response,
				eventTransformer,
				new ArrayList<EventHandler>());
	}
	
	
	//= Support ========================================================================================================
	
	private Collection<EventHandler> getPublicEventHandlers() {
		if (publicEventHandlers == null) {
			publicEventHandlers = new ArrayList<EventHandler>();
			
			// job:task_results
//			publicEventHandlers.add(new SingleEventTypeEventHandler("job", "task_results") {
//				
//			});
		}
		return publicEventHandlers;
	}
}
