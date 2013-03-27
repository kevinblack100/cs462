package kpbinc.cs462.driver.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kpbinc.cs462.driver.model.DeliveryRequest;
import kpbinc.cs462.driver.model.DriverGuildEventChannel;
import kpbinc.cs462.driver.model.UserProfile;
import kpbinc.cs462.driver.model.manage.DeliveryRequestManager;
import kpbinc.cs462.driver.model.manage.DriverGuildEventChannelManager;
import kpbinc.cs462.driver.model.manage.DriverProfileManager;
import kpbinc.cs462.driver.model.manage.StashedEventManager;
import kpbinc.cs462.driver.model.manage.UserProfileManager;
import kpbinc.cs462.shared.event.Event;
import kpbinc.cs462.shared.event.EventDispatcher;
import kpbinc.cs462.shared.event.EventGenerator;
import kpbinc.cs462.shared.event.EventChannelEventHandler;
import kpbinc.cs462.shared.event.EventRenderingException;
import kpbinc.cs462.shared.event.EventTransformer;
import kpbinc.cs462.shared.event.SingleEventTypeEventChannelEventHandler;
import kpbinc.math.SphericalUtils;
import kpbinc.util.logging.GlobalLogUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Sms;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/esl")
public class EventDispatchController {

	//= Class Data =====================================================================================================
	
	private static final Logger logger = Logger.getLogger(EventDispatchController.class.getName());
	
	public static final String ACCOUNT_SID = "AC5f497520d983b14a8d1dd57851d5e85c";
	public static final String AUTH_TOKEN = "759921c102d3e9a9a0d2b49c0ac88656";
	public static final String TWILIO_PHONE_NUMBER = "+18014710490";
	public static final String DEFAULT_SMS_DEST_NUMBER = "+18013618342";
	
	public static final boolean doDistanceCheck = true;
	
	
	//= Member Data ====================================================================================================
	
	@Autowired
	private DeliveryRequestsController deliveryRequestsController;
	
	@Autowired
	private EventTransformer eventTransformer;
	
	@Autowired
	private EventGenerator eventGenerator;
	
	@Autowired
	private DriverProfileManager driverProfileManager;
	
	@Autowired
	private StashedEventManager stashedEventManager;
	
	@Autowired
	private UserProfileManager userProfileManager;
	
	@Autowired
	private DriverGuildEventChannelManager driverGuildEventChannelManager;
	
	@Autowired
	private DeliveryRequestManager deliveryRequestManager;
	
	private Collection<EventChannelEventHandler<DriverGuildEventChannel>> guildChannelEventHandlers;
	
	
	//= Initialization =================================================================================================
	
	public EventDispatchController() {
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	//- Manual Event Dispatch Testing ----------------------------------------------------------------------------------
	
	@RequestMapping(value = "/dispatch", method = RequestMethod.GET)
	public String getDispatchForm() {
		return "events/dispatch";
	}
	
	@RequestMapping(value = "/dispatch", method = RequestMethod.POST)
	public String dispatch(HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		Map<String, String[]> parameters = request.getParameterMap();
		
		try {
			Event event = eventTransformer.transform(parameters);
			logger.info(String.format("parsed event %s:%s", event.getDomain(), event.getName()));
		}
		catch (EventRenderingException e) {
			// TODO set message
			logger.warning(GlobalLogUtils.formatHandledExceptionMessage("Can't parse Event", e, GlobalLogUtils.DO_PRINT_STACKTRACE));
			e.printStackTrace();
		}
		
		return "events/dispatch";
	}
	
	//- Guild Channel Event Handling -----------------------------------------------------------------------------------
	
	@RequestMapping(value = "/guild/channel/{channel-id}", method = RequestMethod.POST)
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
				driverGuildEventChannelManager,
				getGuildChannelEventHandlers());
	}
	
	//- Twilio Request Handling ----------------------------------------------------------------------------------------
	
	@RequestMapping(value = "/twiliorequest")
	public void handleTwilioRequest(
			HttpServletResponse response,
			@RequestParam(value = "From", required = true) String fromNumber,
			@RequestParam(value = "Body", required = true) String messageBody) {
		logger.entering(this.getClass().getName(), "handleTwilioRequest");
		
		logger.info(String.format("EDC: twilio: request received with parameters\n fromNumber: %s\n messageBody: %s\n", fromNumber, messageBody));
		try {
			PrintWriter responsePayloadWriter = response.getWriter();
			responsePayloadWriter.write("received");
			responsePayloadWriter.flush();
			
			// TODO validate that the phone number is valid
//			UserProfile profile = userProfileManager.getByTextableNumber(fromNumber);
//			
//			if (profile != null) {
				StringTokenizer tokenizer = new StringTokenizer(messageBody, " ");
				String firstToken = tokenizer.nextToken();
				Long deliveryRequestId = Long.parseLong(firstToken);
				DeliveryRequest deliveryRequest = deliveryRequestManager.retrieve(deliveryRequestId);
				
				if (   deliveryRequest != null) {
//					&& StringUtils.equals(deliveryRequest.getUsername(), profile.getUsername())) {
					
					String command = messageBody.substring(messageBody.indexOf(" ") + 1);
					if (   StringUtils.equalsIgnoreCase(command, "bid anyway")
						&& deliveryRequest.getState().equals(DeliveryRequest.State.AVAILABLE_FOR_BID)) {
						deliveryRequestsController.submitBid(
								deliveryRequestId,
								DeliveryRequest.State.QUOTED_SEMIAUTOMATICALLY,
								6.0f,
								"6:00 PM");
					}
				}
//			}
			// else non-registered person sent text
		}
		catch (NumberFormatException e) {
			logger.warning(GlobalLogUtils.formatHandledExceptionMessage(
					"EDC: Can't parse Number", e, GlobalLogUtils.DO_PRINT_STACKTRACE));
			e.printStackTrace();
		}
		catch (IOException e) {
			// problem in preparing the response
			logger.warning(GlobalLogUtils.formatHandledExceptionMessage(
					"EDC: Couldn't prepare response", e, GlobalLogUtils.DO_PRINT_STACKTRACE));
			e.printStackTrace();
		}
		
		logger.exiting(this.getClass().getName(), "handleTwilioRequest");
	}
	
	
	//= Support ========================================================================================================

	private Collection<EventChannelEventHandler<DriverGuildEventChannel>> getGuildChannelEventHandlers() {
		if (guildChannelEventHandlers == null) {
			guildChannelEventHandlers = new ArrayList<EventChannelEventHandler<DriverGuildEventChannel>>();
			
			// rfq:delivery_ready handler
			guildChannelEventHandlers.add(new
				SingleEventTypeEventChannelEventHandler<DriverGuildEventChannel>("rfq", "delivery_ready") {
				
				@Override
				protected void handleImpl(Event event, DriverGuildEventChannel channel) {
					String driverUsername = channel.getLocalEntityId();
					UserProfile userProfile = userProfileManager.retrieve(driverUsername);

					// Store the DeliveryRequest
					DeliveryRequest deliveryRequest = new DeliveryRequest();
					deliveryRequest.setUsername(driverUsername);
					deliveryRequest.setShopDeliveryId(Long.parseLong((String) event.getAttribute("delivery_id")));
					deliveryRequest.setShopId(Long.parseLong((String) event.getAttribute("shop_key")));
					deliveryRequest.setShopLatitude(Double.parseDouble((String) event.getAttribute("shop_latitude")));
					deliveryRequest.setShopLongitude(Double.parseDouble((String) event.getAttribute("shop_longitude")));
					deliveryRequest.setDeliveryAddress((String) event.getAttribute("delivery_address"));
					deliveryRequest.setRequestedPickupTime((String) event.getAttribute("pickup_time"));
					deliveryRequest.setRequestedDeliveryTime((String) event.getAttribute("delivery_time"));
					deliveryRequest.setState(DeliveryRequest.State.AVAILABLE_FOR_BID);
					deliveryRequestManager.register(deliveryRequest);
					
					// Calculate distance
					double distanceInMiles = -1.0; // don't know where the user is
					if (   userProfile != null
						&& userProfile.getLastKnownLatitude() != null
						&& userProfile.getLastKnownLongitude() != null) {
						
						distanceInMiles = SphericalUtils.greatCircleVincenty(
							SphericalUtils.EARTH_RADIUS_mi,
							deliveryRequest.getShopLatitude(), deliveryRequest.getShopLongitude(),
							userProfile.getLastKnownLatitude(), userProfile.getLastKnownLongitude());	
					}
					
					if (	!doDistanceCheck
						||  (   0.0 <= distanceInMiles
						     && distanceInMiles <= 5.0)) {
						deliveryRequestsController.submitBid(
								deliveryRequest.getId(),
								DeliveryRequest.State.QUOTED_AUTOMATICALLY,
								5.0f,
								"5:00 PM");
					}
					else if (   userProfile != null
							 && userProfile.getTextableNumber() != null) {
						// TODO double check logic here
						
						// Based on Twilio's documentation: http://www.twilio.com/docs/api/rest/sending-sms
						logger.info("EDC: preparing twilio message");
						TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
						
					    Map<String, String> params = new HashMap<String, String>();
					    String messageContent = String.format("Flower Delivery Ready: id: %d, shop: %s, pickup: %s, address: %s",
					    		deliveryRequest.getId(),
					    		event.getAttribute("shop_name"),
					    		event.getAttribute("pickup_time"),
					    		event.getAttribute("delivery_address"));
					    if (event.getAttribute("delivery_time") != null) {
					    	messageContent += ", time: " + event.getAttribute("delivery_time");
					    }
					    params.put("Body", messageContent);
					    params.put("To", userProfile.getTextableNumber());
					    params.put("From", TWILIO_PHONE_NUMBER);
						     
					    SmsFactory messageFactory = client.getAccount().getSmsFactory();
					    try {
					    	logger.info(String.format("EDC: twilio: sending text to %s with message %s",
					    			userProfile.getTextableNumber(), messageContent));
					    	Sms message = messageFactory.create(params);
					    	logger.info("EDC: Sent SMS message: SID " + message.getSid());
					    }
					    catch (TwilioRestException e) {
					    	logger.warning(GlobalLogUtils.formatHandledExceptionMessage(
					    			"EDC: Couldn't send message", e, GlobalLogUtils.DO_PRINT_STACKTRACE));
					    	e.printStackTrace();
					    }
					}
					else {
						logger.warning("EDC: Either no user, user not within distance, or has no textable number");
					}
				}
				
			});
			
			// rfq:bid_awarded handler
			guildChannelEventHandlers.add(new
				SingleEventTypeEventChannelEventHandler<DriverGuildEventChannel>("rfq", "bid_awarded") {
				
				@Override
				protected void handleImpl(Event event, DriverGuildEventChannel channel) {
					String username = channel.getLocalEntityId();
					Long shopId = Long.parseLong((String) event.getAttribute("shop_key"));
					Long shopDeliveryId = Long.parseLong((String) event.getAttribute("delivery_id"));
					
					DeliveryRequest deliveryRequest = 
							deliveryRequestManager.retrieveByUsernameShopIdAndShopDeliveryId(username, shopId, shopDeliveryId);
					if (deliveryRequest != null) {
						deliveryRequest.setState(DeliveryRequest.State.AWARDED);
						deliveryRequestManager.update(deliveryRequest);
					}
				}
				
			});
			
			// delivery:picked_up handler
			guildChannelEventHandlers.add(new
				SingleEventTypeEventChannelEventHandler<DriverGuildEventChannel>("delivery", "picked_up") {
				
				@Override
				protected void handleImpl(Event event, DriverGuildEventChannel channel) {
					String username = channel.getLocalEntityId();
					Long shopId = Long.parseLong((String) event.getAttribute("shop_key"));
					Long shopDeliveryId = Long.parseLong((String) event.getAttribute("delivery_id"));
					
					DeliveryRequest deliveryRequest = 
							deliveryRequestManager.retrieveByUsernameShopIdAndShopDeliveryId(username, shopId, shopDeliveryId);
					if (deliveryRequest != null) {
						deliveryRequest.setState(DeliveryRequest.State.PICKED_UP);
						deliveryRequestManager.update(deliveryRequest);
					}
				}
				
			});
		}
		return guildChannelEventHandlers;
	}

}
