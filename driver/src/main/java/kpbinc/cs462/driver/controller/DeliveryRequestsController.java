package kpbinc.cs462.driver.controller;

import java.util.Collection;
import java.util.logging.Logger;

import kpbinc.cs462.driver.model.DeliveryRequest;
import kpbinc.cs462.driver.model.DriverGuildEventChannel;
import kpbinc.cs462.driver.model.manage.DeliveryRequestManager;
import kpbinc.cs462.driver.model.manage.DriverGuildEventChannelManager;
import kpbinc.cs462.shared.event.BasicEventImpl;
import kpbinc.cs462.shared.event.EventChannelUtils;
import kpbinc.cs462.shared.event.EventGenerator;
import kpbinc.cs462.shared.event.EventRenderingException;
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

	//= Class Data =====================================================================================================
	
	private static final Logger logger = Logger.getLogger(DeliveryRequestsController.class.getName());
	
	
	//= Member Data ====================================================================================================
	
	@Autowired
	private EventGenerator eventGenerator;
	
	@Autowired
	private DriverGuildEventChannelManager driverGuildEventChannelManager;
	
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
	
	//- Internal Service API -------------------------------------------------------------------------------------------
	// TODO move to a service class?
	
	public void submitBid(
			Long deliveryId,
			DeliveryRequest.State quoteType,
			Float amount,
			String estimatedDeliveryTime) {
		DeliveryRequest deliveryRequest = deliveryRequestManager.retrieve(deliveryId);
		if (deliveryRequest != null) {
			// TODO validate quoteType
			deliveryRequest.setState(quoteType);
			deliveryRequest.setBidAmount(amount);
			deliveryRequest.setBidAmountUnits("USD");
			deliveryRequest.setEstimatedDeliveryTime(estimatedDeliveryTime);
			deliveryRequestManager.update(deliveryRequest);
			
			BasicEventImpl bidAvailableEvent = null;
			try {
				bidAvailableEvent = new BasicEventImpl("rfq", "bid_available");
				bidAvailableEvent.addAttribute("delivery_id", deliveryRequest.getShopDeliveryId());
				bidAvailableEvent.addAttribute("driver_name", deliveryRequest.getUsername());
				bidAvailableEvent.addAttribute("shop_key", deliveryRequest.getShopId());
				bidAvailableEvent.addAttribute("amount", deliveryRequest.getBidAmount());
				bidAvailableEvent.addAttribute("amount_units", deliveryRequest.getBidAmountUnits());
				bidAvailableEvent.addAttribute("delivery_time_est", deliveryRequest.getEstimatedDeliveryTime());
			}
			catch (EventRenderingException e) {
				logger.warning(GlobalLogUtils.formatHandledExceptionMessage(
						"submitting bid rfq:bid_available event", e, GlobalLogUtils.DO_PRINT_STACKTRACE));
				e.printStackTrace();
			}
			
			if (bidAvailableEvent != null) {
				DriverGuildEventChannel channel = 
						driverGuildEventChannelManager.retrieveByUsername(deliveryRequest.getUsername());
				EventChannelUtils.notify(bidAvailableEvent, channel, eventGenerator);
			}
		}
	}
	
}
