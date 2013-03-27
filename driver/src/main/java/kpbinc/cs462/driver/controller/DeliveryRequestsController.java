package kpbinc.cs462.driver.controller;

import java.util.Collection;

import kpbinc.cs462.driver.model.DeliveryRequest;
import kpbinc.cs462.driver.model.manage.DeliveryRequestManager;
import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/delivery-requests")
public class DeliveryRequestsController extends DriverBaseSiteContextController {

	//= Member Data ====================================================================================================
	
	@Autowired
	private DeliveryRequestManager deliveryRequestManager;
	
	
	//= Initialization =================================================================================================
	
	public DeliveryRequestsController() {
		super();
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	//- Web API --------------------------------------------------------------------------------------------------------
	
	@RequestMapping
	public String listAllDeliveryRequests(ModelMap model) {
		UserDetails loggedInUserDetails = getLoggedInUserContext().getSignedInUserDetails();
		if (loggedInUserDetails != null) {
			String username = loggedInUserDetails.getUsername();
			Collection<DeliveryRequest> deliveryRequests = deliveryRequestManager.retrieveByUsername(username);
			model.addAttribute("deliveryRequests", deliveryRequests);
		}
		return "delivery_requests/delivery_requests_list";
	}
	
}
