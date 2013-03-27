package kpbinc.cs462.guild.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kpbinc.cs462.guild.model.GuildFlowerShopEventChannel;
import kpbinc.cs462.guild.model.GuildUserEventChannel;
import kpbinc.cs462.guild.model.manage.GuildFlowerShopEventChannelManager;
import kpbinc.cs462.guild.model.manage.GuildUserEventChannelManager;
import kpbinc.cs462.shared.event.BasicEventImpl;
import kpbinc.cs462.shared.event.Event;
import kpbinc.cs462.shared.event.EventChannelUtils;
import kpbinc.cs462.shared.event.EventDispatcher;
import kpbinc.cs462.shared.event.EventGenerator;
import kpbinc.cs462.shared.event.EventChannelEventHandler;
import kpbinc.cs462.shared.event.EventRenderingException;
import kpbinc.cs462.shared.event.EventTransformer;
import kpbinc.cs462.shared.event.SingleEventTypeEventChannelEventHandler;
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
	
	@Autowired
	private GuildUserEventChannelManager guildUserEventChannelManager;
	
	private Collection<EventChannelEventHandler<GuildFlowerShopEventChannel>> shopChannelEventHandlers;
	
	private Collection<EventChannelEventHandler<GuildUserEventChannel>> userChannelEventHandlers;
	
	
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
	
	@RequestMapping(value = "/user/channel/{channel-id}")
	public void dispatchEventFromUser(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "channel-id") Long channelId) {
		EventDispatcher.dispatchEvent(
				"dispatch user (driver) event",
				request,
				response,
				eventTransformer,
				channelId,
				guildUserEventChannelManager,
				getUserChannelEventHandlers());
	}
	
	
	//= Support ========================================================================================================
	
	private Collection<EventChannelEventHandler<GuildFlowerShopEventChannel>> getShopChannelEventHandlers() {
		if (shopChannelEventHandlers == null) {
			shopChannelEventHandlers = new ArrayList<EventChannelEventHandler<GuildFlowerShopEventChannel>>();
			
			// rfq:delivery_ready handler
			shopChannelEventHandlers.add(new
				SingleEventTypeEventChannelEventHandler<GuildFlowerShopEventChannel>("rfq", "delivery_ready") {
				
				@Override
				protected void handleImpl(Event event, GuildFlowerShopEventChannel channel) {
					// TODO stash the event for later reference?

					// enhance event
					Event enhancedEvent = event.clone();
					try {
						// TODO add shop lat/lng coordinates
						enhancedEvent.addAttribute("shop_key", channel.getId());
					}
					catch (EventRenderingException e) {
						logger.warning(GlobalLogUtils.formatHandledExceptionMessage(
								String.format("Ehancing %s:%s event", getDomain(), getName()),
								e, GlobalLogUtils.DO_PRINT_STACKTRACE));
						e.printStackTrace();
					}
					
					// TODO send only to the top three drivers
					EventChannelUtils.notify(enhancedEvent, guildUserEventChannelManager.retrieveAll(), eventGenerator);
				}
				
			});
			
			// rfq:bid_awarded handler
			shopChannelEventHandlers.add(new
				SingleEventTypeEventChannelEventHandler<GuildFlowerShopEventChannel>("rfq", "bid_awarded") {

				@Override
				protected void handleImpl(Event event, GuildFlowerShopEventChannel channel) {
					// Enhance event
					Event forwardedEvent = event.clone();
					try {
						forwardedEvent.addAttribute("shop_key", channel.getId());
					}
					catch (EventRenderingException e) {
						logger.warning(GlobalLogUtils.formatHandledExceptionMessage(
								String.format("Forwarding %s:%s event", getDomain(), getName()),
								e, GlobalLogUtils.DO_PRINT_STACKTRACE));
						e.printStackTrace();
					}
					
					// Send event
					String driverUsername = (String) event.getAttribute("driver_id");
					GuildUserEventChannel userChannel = guildUserEventChannelManager.retrieveByUsername(driverUsername);
					
					EventChannelUtils.notify(forwardedEvent, userChannel, eventGenerator);
				}
			
			});
			
			// delivery:picked_up handler
			shopChannelEventHandlers.add(new
				SingleEventTypeEventChannelEventHandler<GuildFlowerShopEventChannel>("delivery", "picked_up") {
				
				@Override
				protected void handleImpl(Event event, GuildFlowerShopEventChannel channel) {
					// Enhance event
					Event forwardedEvent = event.clone();
					try {
						forwardedEvent.addAttribute("shop_key", channel.getId());
					}
					catch (EventRenderingException e) {
						logger.warning(GlobalLogUtils.formatHandledExceptionMessage(
								String.format("Forwarding %s:%s event", getDomain(), getName()),
								e, GlobalLogUtils.DO_PRINT_STACKTRACE));
						e.printStackTrace();
					}
					
					// Send Event
					String driverUsername = (String) event.getAttribute("driver_id");
					GuildUserEventChannel userChannel = guildUserEventChannelManager.retrieveByUsername(driverUsername);
					
					EventChannelUtils.notify(forwardedEvent, userChannel, eventGenerator);
				}
				
			});
		}
		return shopChannelEventHandlers;
	}
	
	private Collection<EventChannelEventHandler<GuildUserEventChannel>> getUserChannelEventHandlers() {
		if (userChannelEventHandlers == null) {
			userChannelEventHandlers = new ArrayList<EventChannelEventHandler<GuildUserEventChannel>>();
			
			// rfq:bid_available handler
			userChannelEventHandlers.add(new
				SingleEventTypeEventChannelEventHandler<GuildUserEventChannel>("rfq", "bid_available") {
				
				@Override
				protected void handleImpl(Event event, GuildUserEventChannel channel) {
					Long shopChannelID = Long.parseLong((String) event.getAttribute("shop_key"));
					GuildFlowerShopEventChannel shopChannel = guildFlowerShopEventChannelManager.retrieve(shopChannelID);
					assert(shopChannel != null);
					if (shopChannel != null) {
						Event forwardedEvent = event.clone();
						forwardedEvent.removeAttribute("shop_key");
						
						try {
							forwardedEvent.addAttribute("driver_id", channel.getRemoteEntityId());
							// TODO enhance with driver ranking
						}
						catch (EventRenderingException e) {
							logger.warning(GlobalLogUtils.formatHandledExceptionMessage(
									String.format("Forwarding %s:%s event", getDomain(), getName()),
									e, GlobalLogUtils.DO_PRINT_STACKTRACE));
							e.printStackTrace();
						}

						EventChannelUtils.notify(forwardedEvent, shopChannel, eventGenerator);
					}
				}
				
			});
			
			// delivery:complete handler
			userChannelEventHandlers.add(new
				SingleEventTypeEventChannelEventHandler<GuildUserEventChannel>("delivery", "complete") {
				
				@Override
				protected void handleImpl(Event event, GuildUserEventChannel channel) {
					Long shopChannelID = Long.parseLong((String) event.getAttribute("shop_key"));
					GuildFlowerShopEventChannel shopChannel = guildFlowerShopEventChannelManager.retrieve(shopChannelID);
					assert(shopChannel != null);
					if (shopChannel != null) {
						Event forwardedEvent = event.clone();
						forwardedEvent.removeAttribute("shop_key");
						
						try {
							forwardedEvent.addAttribute("driver_id", channel.getRemoteEntityId());
						}
						catch (EventRenderingException e) {
							logger.warning(GlobalLogUtils.formatHandledExceptionMessage(
									String.format("Forwarding %s:%s event", getDomain(), getName()),
									e, GlobalLogUtils.DO_PRINT_STACKTRACE));
							e.printStackTrace();
						}

						EventChannelUtils.notify(forwardedEvent, shopChannel, eventGenerator);
					}
				}
				
			});
		}
		return userChannelEventHandlers;
	}
}
