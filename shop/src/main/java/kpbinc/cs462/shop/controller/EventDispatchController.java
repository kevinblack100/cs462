package kpbinc.cs462.shop.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kpbinc.cs462.shared.event.Event;
import kpbinc.cs462.shared.event.EventChannel;
import kpbinc.cs462.shared.event.EventDispatcher;
import kpbinc.cs462.shared.event.EventHandler;
import kpbinc.cs462.shared.event.EventTransformer;
import kpbinc.cs462.shared.event.SingleEventTypeHandler;
import kpbinc.cs462.shop.model.DeliveryBid;
import kpbinc.cs462.shop.model.manage.DeliveryBidManager;
import kpbinc.cs462.shop.model.manage.FlowerShopGuildEventChannelManager;
import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/esl")
public class EventDispatchController extends ShopBaseSiteContextController {

	//= Class Data =====================================================================================================
	
	private static final Logger logger = Logger.getLogger(EventDispatchController.class.getName());
	
	
	//= Member Data ====================================================================================================
	
	@Autowired
	private EventTransformer eventTransformer;
	
	@Autowired
	private FlowerShopGuildEventChannelManager flowerShopGuildEventChannelManager;
	
	@Autowired
	private DeliveryBidManager deliveryBidManager;
	
	Collection<EventHandler> guildChannelEventHandlers;
	
	
	//= Initialization =================================================================================================
	
	public EventDispatchController() {
		super();
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	@RequestMapping(value = "/guild/channel/{channel-id}")
	public void dispatchEventFromGuild(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "channel-id") Long channelId) {
		EventDispatcher.dispatchEvent(
				"dispatch guild event",
				request,
				response,
				eventTransformer,
				channelId,
				flowerShopGuildEventChannelManager,
				getGuildChannelEventHandlers());
	}
	
	
	//= Support ========================================================================================================
	
	private Collection<EventHandler> getGuildChannelEventHandlers() {
		if (guildChannelEventHandlers == null) {
			guildChannelEventHandlers = new ArrayList<EventHandler>();
			
			// rfq:bid_available handler
			guildChannelEventHandlers.add(new SingleEventTypeHandler("rfq", "bid_available") {
				
				@Override
				protected void handleImpl(Event event, EventChannel<?, ?> channel) {
					logger.info(String.format("processing %s:%s event...", event.getDomain(), event.getName()));
					
					Long orderID = Long.parseLong((String) event.getAttribute("delivery_id"));
					String driverName = (String) event.getAttribute("driver_name");
					String estimatedDeliveryTime = (String) event.getAttribute("delivery_time_est");
					Double amount = Double.parseDouble((String) event.getAttribute("amount"));
					String amountUnits = (String) event.getAttribute("amount_units");
					
					DeliveryBid bid = new DeliveryBid();
					bid.setOrderID(orderID);
					bid.setUsername("unknown");
					bid.setDriverName(driverName);
					bid.setEstimatedDeliveryTime(estimatedDeliveryTime);
					bid.setAmount(amount);
					bid.setAmountUnits(amountUnits);
					
					deliveryBidManager.register(bid);
					
					logger.info(String.format("done processing %s:%s event...", event.getDomain(), event.getName()));
				}
				
			});
		}
		return guildChannelEventHandlers;
	}
	
}
