package kpbinc.cs462.guild.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kpbinc.cs462.guild.model.manage.GuildFlowerShopEventChannelManager;
import kpbinc.cs462.shared.event.BasicEventImpl;
import kpbinc.cs462.shared.event.Event;
import kpbinc.cs462.shared.event.EventChannel;
import kpbinc.cs462.shared.event.EventChannelUtils;
import kpbinc.cs462.shared.event.EventDispatcher;
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
		EventDispatcher.dispatchEvent(
				"dispatch shop event",
				request,
				response,
				eventTransformer,
				channelId,
				guildFlowerShopEventChannelManager,
				getShopChannelEventHandlers());
	}
	
	
	//= Support ========================================================================================================
	
	private Collection<EventHandler> getShopChannelEventHandlers() {
		if (shopChannelEventHandlers == null) {
			shopChannelEventHandlers = new ArrayList<EventHandler>();
			
			// rfq:delivery_ready handler
			shopChannelEventHandlers.add(new SingleEventTypeHandler("rfq", "delivery_ready") {
				
				@Override
				protected void handleImpl(Event event, EventChannel<?, ?> channel) {
					// TODO replace temporary implementation of sending back an rfq:bid_available event
					if (   channel != null
						&& StringUtils.isNotBlank(channel.getSendESL())) {
						try {
							BasicEventImpl bidAvailableEvent = new BasicEventImpl("rfq", "bid_available");
							bidAvailableEvent.addAttribute("driver_id", "guildmaster");
							bidAvailableEvent.addAttribute("driver_name", "Guild Master");
							bidAvailableEvent.addAttribute("delivery_id", event.getAttribute("delivery_id"));
							bidAvailableEvent.addAttribute("delivery_time_est", "5:00 PM");
							bidAvailableEvent.addAttribute("amount", new Float(7.0f));
							bidAvailableEvent.addAttribute("amount_units", "USD");
							
							EventChannelUtils.notify(bidAvailableEvent, channel, eventGenerator);
						}
						catch (EventRenderingException e) {
							logger.warning(GlobalLogUtils.formatHandledExceptionMessage(
									String.format("shop channel %s:%s handler", getDomain(), getName()),
									e, GlobalLogUtils.DO_PRINT_STACKTRACE));
							e.printStackTrace();
						}
					}
				}
				
			});
			
			// delivery:picked_up handler
			shopChannelEventHandlers.add(new SingleEventTypeHandler("delivery", "picked_up") {
				
				@Override
				protected void handleImpl(Event event, EventChannel<?, ?> channel) {
					// TODO replace temporary implementation of sending back a delivery:complete event
					if (   channel != null
						&& StringUtils.isNotBlank(channel.getSendESL())) {
						try {
							BasicEventImpl deliveryCompleteEvent = new BasicEventImpl("delivery", "complete");
							deliveryCompleteEvent.addAttribute("driver_id", "guildmaster");
							deliveryCompleteEvent.addAttribute("driver_name", "Guild Master");
							deliveryCompleteEvent.addAttribute("delivery_id", event.getAttribute("delivery_id"));
							deliveryCompleteEvent.addAttribute("delivery_time_act", "5:10 PM");
							
							EventChannelUtils.notify(deliveryCompleteEvent, channel, eventGenerator);
						}
						catch (EventRenderingException e) {
							logger.warning(GlobalLogUtils.formatHandledExceptionMessage(
									String.format("shop channel %s:%s handler", getDomain(), getName()),
									e, GlobalLogUtils.DO_PRINT_STACKTRACE));
							e.printStackTrace();
						}
					}
				}
				
			});
		}
		return shopChannelEventHandlers;
	}
	
}
