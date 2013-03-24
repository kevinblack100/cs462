package kpbinc.cs462.guild.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kpbinc.cs462.guild.model.GuildFlowerShopEventChannel;
import kpbinc.cs462.guild.model.manage.GuildFlowerShopEventChannelManager;
import kpbinc.cs462.shared.event.BasicEventImpl;
import kpbinc.cs462.shared.event.Event;
import kpbinc.cs462.shared.event.EventChannel;
import kpbinc.cs462.shared.event.EventGenerator;
import kpbinc.cs462.shared.event.EventHandler;
import kpbinc.cs462.shared.event.EventRenderingException;
import kpbinc.cs462.shared.event.EventTransformer;
import kpbinc.cs462.shared.event.SingleEventTypeHandler;
import kpbinc.util.logging.GlobalLogUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/esl")
public class EventDispatchController extends GuildBaseSiteContextController {

	//= Class Data =====================================================================================================
	
	private static final Logger logger = Logger.getLogger(EventDispatchController.class.getName());
	
	
	//= Member Data ====================================================================================================
	
	@Autowired
	private EventGenerator eventGenerator;
	
	@Autowired
	private EventTransformer eventTransformer;
	
	@Autowired
	private GuildFlowerShopEventChannelManager guildFlowerShopEventChannelManager;
	
	private Collection<EventHandler> shopChannelEventHandlers;
	
	
	//= Initialization =================================================================================================
	
	public EventDispatchController() {
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	//- Web API --------------------------------------------------------------------------------------------------------
	
	@RequestMapping(value = "/shop/channel/{channel-id}")
	public void dispatchEventFromShop(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "channel-id") Long channelId) {
		// Parse/Render Event and Prepare the Response
		Event event = null;
		String responseString = "Received.";
		
		GuildFlowerShopEventChannel channel = guildFlowerShopEventChannelManager.retrieve(channelId);
		if (channel != null) {
			if (StringUtils.isNotBlank(channel.getSendESL())) {
				try {
					@SuppressWarnings("unchecked")
					Map<String, String[]> parameters = request.getParameterMap();
					
					event = eventTransformer.transform(parameters);
					
					logger.info(String.format("parsed %s:%s event", event.getDomain(), event.getName()));
				}
				catch (EventRenderingException e) {
					responseString = "Could not render event: " + e.getMessage();
					logger.warning(GlobalLogUtils.formatHandledExceptionMessage(
							"dispatch shop event: could not render event", e, GlobalLogUtils.DO_PRINT_STACKTRACE));
					e.printStackTrace();
				}
			}
			else {
				responseString = "Channel not fully configured. Has no send ESL.";
			}
		}
		else {
			responseString = "Channel invalid.";
		}
		
		// Send the response
		try {
			PrintWriter responsePayloadWriter = response.getWriter();
			responsePayloadWriter.write(responseString);
			responsePayloadWriter.flush();
		}
		catch (IOException e) {
			logger.warning(GlobalLogUtils.formatHandledExceptionMessage(
					"dispatch shop event: may not have sent response", e, GlobalLogUtils.DO_PRINT_STACKTRACE));
			e.printStackTrace();
		}
		
		// Process Event
		for (EventHandler handler : getShopChannelEventHandlers()) {
			handler.handle(event, channel);
		}
	}
	
	
	//= Support ========================================================================================================
	
	private Collection<EventHandler> getShopChannelEventHandlers() {
		if (shopChannelEventHandlers == null) {
			shopChannelEventHandlers = new ArrayList<EventHandler>();
			
			// rfq:delivery_ready handler
			shopChannelEventHandlers.add(new SingleEventTypeHandler("rfq", "delivery_ready") {
				
				@Override
				protected void handleImpl(Event event, EventChannel<?, ?> channel) {
					logger.info(String.format("processing %s:%s event...", event.getDomain(), event.getName()));
					
					// TODO replace temporary implementation of sending back an rfq:bid_available event
					if (   channel != null
						&& StringUtils.isNotBlank(channel.getSendESL())) {
						try {
							BasicEventImpl bidAvailableEvent = new BasicEventImpl("rfq", "bid_available");
							bidAvailableEvent.addAttribute("driver_name", "guild master");
							bidAvailableEvent.addAttribute("delivery_id", event.getAttribute("delivery_id"));
							bidAvailableEvent.addAttribute("delivery_time_est", "5:00 PM");
							bidAvailableEvent.addAttribute("amount", new Float(7.0f));
							bidAvailableEvent.addAttribute("amount_units", "USD");
							
							eventGenerator.sendEvent(channel.getSendESL(), bidAvailableEvent);
						}
						catch (EventRenderingException e) {
							logger.warning(GlobalLogUtils.formatHandledExceptionMessage(
									"shop channel rfq:delivery_ready handler", e, GlobalLogUtils.DO_PRINT_STACKTRACE));
							e.printStackTrace();
						}
					}
					
					logger.info(String.format("done processing %s:%s event...", event.getDomain(), event.getName()));
				}
				
			});
		}
		return shopChannelEventHandlers;
	}
	
}
