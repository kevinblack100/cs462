package kpbinc.cs462.shop.controller;

import java.util.Collection;
import java.util.logging.Logger;

import kpbinc.cs462.shared.event.BasicEventImpl;
import kpbinc.cs462.shared.event.EventGenerator;
import kpbinc.cs462.shared.event.EventSerializer;
import kpbinc.cs462.shop.model.DriverProfile;
import kpbinc.cs462.shop.model.ShopProfile;
import kpbinc.cs462.shop.model.manage.DriverProfileManager;
import kpbinc.cs462.shop.model.manage.ShopProfileManager;
import kpbinc.util.logging.GlobalLogUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
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
	
	
	//= Initialization =================================================================================================
	
	public OrdersController() {
		super();
		GlobalLogUtils.logConstruction(this);
	}
	
	
	//= Interface ======================================================================================================
	
	@RequestMapping(value = "/submit", method = RequestMethod.GET)
	public String getOrderSubmissionForm() {
		return "orders/submit_order";
	}
	
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String submitOrder(
			@RequestParam(value = "pickup-time", required = true) String pickupTimeRaw,
			@RequestParam(value = "delivery-address", required = true) String deliveryAddressRaw,
			@RequestParam(value = "delivery-time") String deliveryTimeRaw) {
		
		ShopProfile shopProfile = shopProfileManager.getProfile();
		
		BasicEventImpl event = new BasicEventImpl("rfq", "delivery_ready");
		event.addAttribute("shop_name", shopProfile.getName());
		event.addAttribute("shop_address", shopProfile.getAddress());
		event.addAttribute("pickup_time", pickupTimeRaw);
		event.addAttribute("delivery_address", deliveryAddressRaw);
		if (StringUtils.isNotBlank(deliveryTimeRaw)) {
			event.addAttribute("delivery_time", deliveryTimeRaw);
		}

		Collection<DriverProfile> driverProfiles = driverProfileManager.getAllProfiles();
		for (DriverProfile profile : driverProfiles) {
			boolean success = eventGenerator.sendEvent(profile.getEventSignalURL(), event);
			logger.info(String.format("rfq:delivery_ready sent to %s successfully?: %s", profile.getEventSignalURL(), Boolean.toString(success)));
		}
		
		return "redirect:/ffds/";
	}

}
