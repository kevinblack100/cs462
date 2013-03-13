package kpbinc.cs462.driver.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kpbinc.cs462.driver.model.DriverProfile;
import kpbinc.cs462.driver.model.FlowerShopProfile;
import kpbinc.cs462.driver.model.UserProfile;
import kpbinc.cs462.driver.model.manage.DriverProfileManager;
import kpbinc.cs462.driver.model.manage.FlowerShopProfileManager;
import kpbinc.cs462.driver.model.manage.UserProfileManager;
import kpbinc.cs462.shared.event.BasicEventImpl;
import kpbinc.cs462.shared.event.Event;
import kpbinc.cs462.shared.event.EventGenerator;
import kpbinc.cs462.shared.event.EventRenderingException;
import kpbinc.cs462.shared.event.EventTransformer;
import kpbinc.cs462.shared.model.manage.EventManager;
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
@RequestMapping(value = "/event")
public class EventDispatchController {

	//= Class Data =====================================================================================================
	
	private static final Logger logger = Logger.getLogger(EventDispatchController.class.getName());
	
	public static final String ACCOUNT_SID = "AC5f497520d983b14a8d1dd57851d5e85c";
	public static final String AUTH_TOKEN = "759921c102d3e9a9a0d2b49c0ac88656";
	public static final String TWILIO_PHONE_NUMBER = "+18014710490";
	public static final String DEFAULT_SMS_DEST_NUMBER = "+18013618342";
	
	
	//= Member Data ====================================================================================================
	
	@Autowired
	private EventTransformer eventTransformer;
	
	@Autowired
	private EventGenerator eventGenerator;
	
	@Autowired
	private DriverProfileManager driverProfileManager;
	
	@Autowired
	private EventManager eventManager;
	
	@Autowired
	private FlowerShopProfileManager flowerShopProfileManager;
	
	@Autowired
	private UserProfileManager userProfileManager;
	
	
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
			logger.warning("EventRenderingException occurred: " + e.getMessage());
			e.printStackTrace();
		}
		
		return "events/dispatch";
	}
	
	//- rfq:delivery_ready Event Handling ------------------------------------------------------------------------------
	
	@RequestMapping(value = "/rfq/delivery_ready/{shop-profile-id}/{driver-username}", method = RequestMethod.POST)
	public void handleDeliveryReady(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "shop-profile-id") Long shopProfileID,
			@PathVariable(value = "driver-username") String driverUsername) {
		try {
			PrintWriter responsePayloadWriter = response.getWriter();
			
			DriverProfile driverProfile = driverProfileManager.get(driverUsername);
			if (   driverProfile.getRegisteredESLs().containsKey(shopProfileID)
				&& driverProfile.getRegisteredESLs().get(shopProfileID).containsKey("rfq:delivery_ready")) {
				try {
					@SuppressWarnings("unchecked")
					Map<String, String[]> parameters = request.getParameterMap();
					
					Event event = eventTransformer.transform(parameters);
					if (   event.getDomain().equals("rfq")
						&& event.getName().equals("delivery_ready")) {
						responsePayloadWriter.write("received");
						
						FlowerShopProfile shopProfile = flowerShopProfileManager.get(shopProfileID);
						UserProfile userProfile = userProfileManager.get(driverUsername);

						double distanceInMiles = -1.0; // don't know where the user is
						if (   userProfile != null
							&& userProfile.getLastKnownLatitude() != null
							&& userProfile.getLastKnownLongitude() != null) {
							
							distanceInMiles = SphericalUtils.greatCircleVincenty(
								SphericalUtils.EARTH_RADIUS_mi,
								shopProfile.getLatitude(), shopProfile.getLongitude(),
								userProfile.getLastKnownLatitude(), userProfile.getLastKnownLongitude());	
						}
						
						if (   0.0 <= distanceInMiles
							&& distanceInMiles <= 5.0) {
							BasicEventImpl bidAvailableEvent = new BasicEventImpl("rfq", "bid_available");
							bidAvailableEvent.addAttribute("driver_name", driverUsername);
							bidAvailableEvent.addAttribute("delivery_id", event.getAttributes().get("delivery_id").get(0));
							bidAvailableEvent.addAttribute("delivery_time_est", "5:00 PM");
							bidAvailableEvent.addAttribute("amount", new Float(5.0f));
							bidAvailableEvent.addAttribute("amount_units", "USD");
							
							String bidAvailableESL = driverProfile.getRegisteredESLs().get(shopProfileID).get("rfq:bid_available");
							eventGenerator.sendEvent(bidAvailableESL, bidAvailableEvent);
						}
						else if (   userProfile != null
								 && userProfile.getTextableNumber() != null) {
							Long eventID = eventManager.getNextID();							
							eventManager.register(eventID, event);
							
							// Based on Twilio's documentation: http://www.twilio.com/docs/api/rest/sending-sms
							TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
							 
						    Map<String, String> params = new HashMap<String, String>();
						    String messageContent = String.format("Flower Delivery Ready: id: %d, shop: %s, pickup: %s, address: %s, time: %s",
						    		eventID,
						    		event.getAttributes().get("shop_name").get(0),
						    		event.getAttributes().get("pickup_time").get(0),
						    		event.getAttributes().get("delivery_address").get(0),
						    		event.getAttributes().get("delivery_time").get(0));
						    params.put("Body", messageContent);
						    params.put("To", userProfile.getTextableNumber());
						    params.put("From", TWILIO_PHONE_NUMBER);
							     
						    SmsFactory messageFactory = client.getAccount().getSmsFactory();
						    try {
						    	Sms message = messageFactory.create(params);
						    	logger.info("Sent SMS message: SID " + message.getSid());
						    }
						    catch (TwilioRestException e) {
						    	logger.warning("TwilioRestException: " + e.getMessage());
						    	e.printStackTrace();
						    }
						}
					}
					else {
						responsePayloadWriter.write("expected an rfq:delivery_ready event");
					}
				}
				catch (EventRenderingException e) {
					responsePayloadWriter.write(e.getMessage());
					logger.warning("EventRenderingException occurred: " + e.getMessage());
					e.printStackTrace();
				}
			}
			else {
				responsePayloadWriter.write("channel is not registered");
			}
			
			responsePayloadWriter.flush();
		}
		catch (IOException e) {
			// problem in preparing the response
			logger.warning("IOException occurred: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	//- Twilio Request Handling ----------------------------------------------------------------------------------------
	
	@RequestMapping(value = "/twiliorequest")
	public void handleTwilioRequest(
			HttpServletResponse response,
			@RequestParam(value = "From", required = true) String fromNumber,
			@RequestParam(value = "Body", required = true) String messageBody) {
		try {
			PrintWriter responsePayloadWriter = response.getWriter();
			responsePayloadWriter.write("received");
			responsePayloadWriter.flush();
			
			UserProfile profile = userProfileManager.getByTextableNumber(fromNumber);
			
			if (profile != null) {
				DriverProfile driverProfile = driverProfileManager.get(profile.getUsername());
				
				StringTokenizer tokenizer = new StringTokenizer(messageBody, " ");
				String firstToken = tokenizer.nextToken();
				Long eventID = Long.parseLong(firstToken);
				Event event = eventManager.get(eventID);
				
				if (event != null) {
					String command = messageBody.substring(messageBody.indexOf(" ") + 1);
					if (   StringUtils.equalsIgnoreCase(command, "bid anyway")
						&& event.getDomain().equals("rfq")
						&& event.getName().equals("delivery_ready")) {
						
						BasicEventImpl bidAvailableEvent = new BasicEventImpl("rfq", "bid_available");
						bidAvailableEvent.addAttribute("driver_name", profile.getUsername());
						bidAvailableEvent.addAttribute("delivery_id", event.getAttributes().get("delivery_id").get(0));
						bidAvailableEvent.addAttribute("delivery_time_est", "5:00 PM");
						bidAvailableEvent.addAttribute("amount", new Float(6.0f));
						bidAvailableEvent.addAttribute("amount_units", "USD");
						
//						String bidAvailableESL = driverProfile.getRegisteredESLs().get(shopProfileID).get("rfq:bid_available");
//						eventGenerator.sendEvent(bidAvailableESL, bidAvailableEvent);
					}
				}
			}
		}
		catch (EventRenderingException e) {
			logger.warning("EventRenderingException: " + e.getMessage());
			e.printStackTrace();
		}
		catch (NumberFormatException e) {
			logger.warning("NumberFormatException: " + e.getMessage());
			e.printStackTrace();
		}
		catch (IOException e) {
			// problem in preparing the response
			logger.warning("IOException occurred: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
}
