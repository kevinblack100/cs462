package kpbinc.cs462.ffds.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.logging.Logger;

import kpbinc.common.util.logging.GlobalLogUtils;
import kpbinc.cs462.ffds.model.DriverProfile;
import kpbinc.cs462.ffds.model.DriverProfileManager;
import kpbinc.cs462.ffds.model.ShopProfile;
import kpbinc.cs462.ffds.model.ShopProfileManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/orders")
public class OrderController extends BaseController {
	
	//~ Class Data =====================================================================================================
	
	private static final Logger logger = Logger.getLogger(OrderController.class.getName());
	
	
	//~ Member Data ====================================================================================================
	
	@Autowired
	private EventGenerator eventGenerator;
	
	@Autowired
	private ShopProfileManager shopProfileManager;
	
	@Autowired
	private DriverProfileManager driverProfileManager;
	
	
	//~ Initialization =================================================================================================
	
	public OrderController() {
		super();
		GlobalLogUtils.logConstruction(this);
	}
	
	
	//~ Interface ======================================================================================================
	
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
		
		StringBuilder builder = new StringBuilder();
		builder.append("_domain=rfq") // _domain and _name do not have to be URL encoded
			   .append("&_name=delivery_ready")
			   .append("&shop_name=").append(urlEncode(shopProfile.getName()))
			   .append("&shop_address=").append(urlEncode(shopProfile.getAddress()))
		       .append("&pickup_time=").append(urlEncode(pickupTimeRaw))
		       .append("&delivery_address=").append(urlEncode(deliveryAddressRaw));
		if (!deliveryTimeRaw.isEmpty()) {
			builder.append("&delivery_time=").append(urlEncode(deliveryTimeRaw));
		}
		String eventDetails = builder.toString();
		
		Collection<DriverProfile> driverProfiles = driverProfileManager.getAllProfiles();
		for (DriverProfile profile : driverProfiles) {
			boolean success = eventGenerator.sendEvent(profile.getEventSignalURL(), eventDetails);
			logger.info(String.format("rfq:delivery_ready sent to %s successfully?: %s", profile.getEventSignalURL(), Boolean.toString(success)));
		}
		
		return "redirect:/ffds/";
	}
	
	private static String urlEncode(String input) {
		String result = input; // send original
		try {
			result = URLEncoder.encode(input, "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			logger.warning("UTF-8 not supported?: " + e.getMessage());
		}
		return result;
	}

}
