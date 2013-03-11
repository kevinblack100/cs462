package kpbinc.cs462.driver.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kpbinc.cs462.shared.event.Event;
import kpbinc.cs462.shared.event.EventRenderingException;
import kpbinc.cs462.shared.event.EventTransformer;
import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/event")
public class EventDispatchController {

	//= Class Data =====================================================================================================
	
	private static final Logger logger = Logger.getLogger(EventDispatchController.class.getName());
	
	
	//= Member Data ====================================================================================================
	
	@Autowired
	private EventTransformer eventTransformer;
	
	
	//= Initialization =================================================================================================
	
	public EventDispatchController() {
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	//- Manual Event Dispatch Testing ----------------------------------------------------------------------------------
	
	@RequestMapping(value = "/dispatch", method = RequestMethod.GET)
	public String getDispatchForm() {
		return "events/dispatch";
	}
	
	@RequestMapping(value = "/dispatch", method = RequestMethod.POST)
	public String dispatch(HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		Map<String, String[]> parameters = request.getParameterMap();
		
		try {
			Event event = eventTransformer.transform(parameters);
			logger.info(String.format("parsed event %s:%s", event.getDomain(), event.getName()));
		}
		catch (EventRenderingException e) {
			// TODO set message
			logger.warning("EventRenderingException occurred: " + e.getMessage());
			e.printStackTrace();
		}
		
		return "events/dispatch";
	}
	
	//- rfq:delivery_ready Event Handling ------------------------------------------------------------------------------
	
	@RequestMapping(value = "/delivery_ready/{shop-profile-id}/{driver-username}", method = RequestMethod.POST)
	public void handleDeliveryReady(
			HttpServletRequest request,
			HttpServletResponse response) {
		@SuppressWarnings("unchecked")
		Map<String, String[]> parameters = request.getParameterMap();
		
		try {
			PrintWriter responsePayloadWriter = response.getWriter();
			
			try {
				Event event = eventTransformer.transform(parameters);
				responsePayloadWriter.write("received");

				// TODO other event processing
			}
			catch (EventRenderingException e) {
				responsePayloadWriter.write(e.getMessage());
				logger.warning("EventRenderingException occurred: " + e.getMessage());
				e.printStackTrace();
			}
			
			responsePayloadWriter.flush();
		}
		catch (IOException e) {
			// problem in preparing the response
			logger.warning("IOException occurred: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
}
