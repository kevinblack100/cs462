package kpbinc.cs462.shop.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@RequestMapping(value = "/rfq/bid_available/{username}")
	public void dispatchBidAvailable(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "username") String username) {
		try {
			PrintWriter responsePayloadWriter = response.getWriter();
			
			try {
				@SuppressWarnings("unchecked")
				Map<String, String[]> parameters = request.getParameterMap();
				
				Event event = eventTransformer.transform(parameters);
				responsePayloadWriter.write("received");
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
