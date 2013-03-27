package kpbinc.cs462.shop.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kpbinc.cs462.shared.event.Event;
import kpbinc.cs462.shared.event.EventDispatcher;
import kpbinc.cs462.shared.event.EventChannelEventHandler;
import kpbinc.cs462.shared.event.EventTransformer;
import kpbinc.cs462.shared.event.SingleEventTypeEventChannelEventHandler;
import kpbinc.cs462.shop.model.DeliveryBid;
import kpbinc.cs462.shop.model.FlowerShopGuildEventChannel;
import kpbinc.cs462.shop.model.Order;
import kpbinc.cs462.shop.model.manage.DeliveryBidManager;
import kpbinc.cs462.shop.model.manage.FlowerShopGuildEventChannelManager;
import kpbinc.cs462.shop.model.manage.OrderManager;
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
	
	@Autowired
	private OrderManager orderManager;
	
	Collection<EventChannelEventHandler<FlowerShopGuildEventChannel>> guildChannelEventHandlers;
	
	
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
	
	private Collection<EventChannelEventHandler<FlowerShopGuildEventChannel>> getGuildChannelEventHandlers() {
		if (guildChannelEventHandlers == null) {
			guildChannelEventHandlers = new ArrayList<EventChannelEventHandler<FlowerShopGuildEventChannel>>();
			
			// rfq:bid_available handler
			guildChannelEventHandlers.add(new 
				SingleEventTypeEventChannelEventHandler<FlowerShopGuildEventChannel>("rfq", "bid_available") {
				
				@Override
				protected void handleImpl(Event event, FlowerShopGuildEventChannel channel) {
					// TODO validate orderId
					Long orderID = Long.parseLong((String) event.getAttribute("delivery_id"));
					String driverId = (String) event.getAttribute("driver_id");
					String driverName = (String) event.getAttribute("driver_name");
					Long driverRanking = Long.parseLong((String) event.getAttribute("driver_ranking"));
					String estimatedDeliveryTime = (String) event.getAttribute("delivery_time_est");
					Double amount = Double.parseDouble((String) event.getAttribute("amount"));
					String amountUnits = (String) event.getAttribute("amount_units");
					
					DeliveryBid bid = new DeliveryBid();
					bid.setOrderID(orderID);
					bid.setDriverId(driverId);
					bid.setDriverName(driverName);
					bid.setDriverRanking(driverRanking);
					bid.setEstimatedDeliveryTime(estimatedDeliveryTime);
					bid.setAmount(amount);
					bid.setAmountUnits(amountUnits);
					
					deliveryBidManager.register(bid);
				}
				
			});
			
			// delivery:complete handler
			guildChannelEventHandlers.add(new 
				SingleEventTypeEventChannelEventHandler<FlowerShopGuildEventChannel>("delivery", "complete") {
				
				@Override
				protected void handleImpl(Event event, FlowerShopGuildEventChannel channel) {
					Long orderId = Long.parseLong((String) event.getAttribute("delivery_id"));
					
					Order order = orderManager.retrieve(orderId);
					if (order != null) {
						// TODO read and set actual delivery time?
						
						order.setState(Order.State.DELIVERED);
						orderManager.update(order);
					}
				}
				
			});
		}
		return guildChannelEventHandlers;
	}
	
}
