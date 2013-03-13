package kpbinc.cs462.shop.controller;

import java.util.Collection;
import java.util.logging.Logger;

import kpbinc.cs462.shared.event.BasicEventImpl;
import kpbinc.cs462.shared.event.EventGenerator;
import kpbinc.cs462.shared.event.EventRenderingException;
import kpbinc.cs462.shared.event.EventSerializer;
import kpbinc.cs462.shop.model.DeliveryBid;
import kpbinc.cs462.shop.model.DriverProfile;
import kpbinc.cs462.shop.model.Order;
import kpbinc.cs462.shop.model.ShopProfile;
import kpbinc.cs462.shop.model.manage.DeliveryBidManager;
import kpbinc.cs462.shop.model.manage.DriverProfileManager;
import kpbinc.cs462.shop.model.manage.OrderManager;
import kpbinc.cs462.shop.model.manage.ShopProfileManager;
import kpbinc.util.logging.GlobalLogUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/orders")
public class OrdersController extends ShopBaseSiteContextController {
	
	//= Class Data =====================================================================================================
	
	private static final Logger logger = Logger.getLogger(OrdersController.class.getName());
	
	
	//= Member Data ====================================================================================================
	
	@Autowired
	private EventGenerator eventGenerator;
		
	@Autowired
	private ShopProfileManager shopProfileManager;
	
	@Autowired
	private DriverProfileManager driverProfileManager;
	
	@Autowired
	private OrderManager orderManager;
	
	@Autowired
	private DeliveryBidManager deliveryBidManager;
	
	
	//= Initialization =================================================================================================
	
	public OrdersController() {
		super();
		GlobalLogUtils.logConstruction(this);
	}
	
	
	//= Interface ======================================================================================================
	
	//- Create ---------------------------------------------------------------------------------------------------------
	
	@RequestMapping(value = "/submit", method = RequestMethod.GET)
	public String getOrderSubmissionForm() {
		return "orders/submit_order";
	}
	
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String submitOrder(
			@RequestParam(value = "pickup-time", required = true) String pickupTimeRaw,
			@RequestParam(value = "delivery-address", required = true) String deliveryAddressRaw,
			@RequestParam(value = "delivery-time") String deliveryTimeRaw) {
		// Create the Order
		Long orderID = orderManager.getNextID();
		Order order = new Order();
		order.setOrderID(orderID);
		order.setPickupTime(pickupTimeRaw);
		order.setDeliveryAddress(deliveryAddressRaw);
		order.setDeliveryTime(deliveryTimeRaw);
		
		orderManager.register(orderID, order);
		
		// Create the rfq:delivery_ready event
		ShopProfile shopProfile = shopProfileManager.getProfile();
		
		BasicEventImpl event = null;
		try {
			event = new BasicEventImpl("rfq", "delivery_ready");
			event.addAttribute("shop_name", shopProfile.getName());
			event.addAttribute("shop_address", shopProfile.getAddress());
			event.addAttribute("delivery_id", orderID);
			event.addAttribute("pickup_time", pickupTimeRaw);
			event.addAttribute("delivery_address", deliveryAddressRaw);
			if (StringUtils.isNotBlank(deliveryTimeRaw)) {
				event.addAttribute("delivery_time", deliveryTimeRaw);
			}
		}
		catch (EventRenderingException e) {
			// TODO set message, change redirect location
			logger.warning(e.getMessage());
			e.printStackTrace();
		}

		// Send the event
		if (event != null) {
			Collection<DriverProfile> driverProfiles = driverProfileManager.getAllProfiles();
			for (DriverProfile profile : driverProfiles) {
				boolean success = eventGenerator.sendEvent(profile.getEventSignalURL(), event);
				logger.info(String.format("rfq:delivery_ready sent to %s successfully?: %s", profile.getEventSignalURL(), Boolean.toString(success)));
			}
		}
		
		return "redirect:/ffds/orders";
	}

	//- Read -----------------------------------------------------------------------------------------------------------
	
	@RequestMapping
	public String getOrdersList(ModelMap model) {
		Collection<Order> orders = orderManager.getAll();
		model.addAttribute("orders", orders);
		return "orders/list_orders";
	}
	
	@RequestMapping(value = "/{order-id}")
	public String getOrderProfile(
			@PathVariable(value = "order-id") Long orderID,
			ModelMap model) {
		prepareOrderProfile(model, orderID);
		return "orders/profile_order";
	}
	
	//- Update ---------------------------------------------------------------------------------------------------------
	
	@RequestMapping(value = "/{order-id}/selectbid", method = RequestMethod.POST)
	public String setSelectedBid(
			@PathVariable(value = "order-id") Long orderID,
			@RequestParam(value = "selected-bid-id") Long selectedBidID,
			ModelMap model) {
		String redirectLocation = null;
		
		Order order = orderManager.get(orderID);
		
		if (order != null) {
			DeliveryBid bid = deliveryBidManager.get(selectedBidID);	
			
			if (bid != null) {
				order.setSelectedBidID(selectedBidID);
				orderManager.update(orderID, order);
			}
			
			prepareOrderProfile(model, orderID);
			redirectLocation = "/" + getContextPaths().getDynamicRelativePath() + "/orders/" + orderID;
		}
		else {
			redirectLocation = "/" + getContextPaths().getDynamicRelativePath() + "/orders";
		}
		
		return "redirect:" + redirectLocation;
	}
	
	
	//= Support ========================================================================================================
	
	private void prepareOrderProfile(ModelMap model, Long orderID) {
		Order order = orderManager.get(orderID);
		model.put("order", order);
		
		Collection<DeliveryBid> bids = deliveryBidManager.getByOrderID(orderID);
		model.put("bids", bids);
	}
	
}
