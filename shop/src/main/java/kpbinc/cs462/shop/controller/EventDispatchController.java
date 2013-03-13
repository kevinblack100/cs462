package kpbinc.cs462.shop.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kpbinc.cs462.shared.event.Event;
import kpbinc.cs462.shared.event.EventRenderingException;
import kpbinc.cs462.shared.event.EventTransformer;
import kpbinc.cs462.shop.model.DeliveryBid;
import kpbinc.cs462.shop.model.manage.DeliveryBidManager;
import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/event")
public class EventDispatchController {

	//= Class Data =====================================================================================================
	
	private static final Logger logger = Logger.getLogger(EventDispatchController.class.getName());
	
	
	//= Member Data ====================================================================================================
	
	@Autowired
	private EventTransformer eventTransformer;
	
	@Autowired
	private DeliveryBidManager deliveryBidManager;
	
	
	//= Initialization =================================================================================================
	
	public EventDispatchController() {
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	@RequestMapping(value = "/rfq/bid_available/{username}")
	public void dispatchBidAvailable(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "username") String username) {
		try {
			PrintWriter responsePayloadWriter = response.getWriter();
			
			try {
				@SuppressWarnings("unchecked")
				Map<String, String[]> parameters = request.getParameterMap();
				
				Event event = eventTransformer.transform(parameters);
				
				if (   event.getDomain().equals("rfq")
					&& event.getName().equals("bid_available")) {
					responsePayloadWriter.write("received");
				
					Long orderID = Long.parseLong((String) event.getAttributes().get("delivery_id").get(0));
					String estimatedDeliveryTime = (String) event.getAttributes().get("delivery_time_est").get(0);
					Double amount = Double.parseDouble((String) event.getAttributes().get("amount").get(0));
					String amountUnits = (String) event.getAttributes().get("amount_units").get(0);
					
					Long bidID = deliveryBidManager.getNextID();
					DeliveryBid bid = new DeliveryBid();
					bid.setBidID(bidID);
					bid.setOrderID(orderID);
					bid.setEstimatedDeliveryTime(estimatedDeliveryTime);
					bid.setAmount(amount);
					bid.setAmountUnits(amountUnits);
					
					deliveryBidManager.register(bidID, bid);
				}
				else {
					responsePayloadWriter.write("expected an rfq:bid_available event");
				}
			}
			catch (EventRenderingException e) {
				responsePayloadWriter.write(e.getMessage());
				logger.warning("EventRenderingException occurred: " + e.getMessage());
				e.printStackTrace();
			}
		
			responsePayloadWriter.flush();
		}
		catch (IOException e) {
			// problem in preparing the response
			logger.warning("IOException occurred: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
}
