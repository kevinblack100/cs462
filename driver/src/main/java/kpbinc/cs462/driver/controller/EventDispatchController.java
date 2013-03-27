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

import kpbinc.cs462.driver.model.DriverGuildEventChannel;
import kpbinc.cs462.driver.model.DriverProfile;
import kpbinc.cs462.driver.model.StashedEvent;
import kpbinc.cs462.driver.model.UserProfile;
import kpbinc.cs462.driver.model.manage.DriverGuildEventChannelManager;
import kpbinc.cs462.driver.model.manage.DriverProfileManager;
import kpbinc.cs462.driver.model.manage.StashedEventManager;
import kpbinc.cs462.driver.model.manage.UserProfileManager;
import kpbinc.cs462.shared.event.BasicEventImpl;
import kpbinc.cs462.shared.event.Event;
import kpbinc.cs462.shared.event.EventChannelUtils;
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
	
	public static final boolean doDistanceCheck = false;
	
	
	//= Member Data ====================================================================================================
	
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
				"dispatch shop event",
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
			
			UserProfile profile = userProfileManager.getByTextableNumber(fromNumber);
			
			if (profile != null) {
				DriverProfile driverProfile = driverProfileManager.retrieve(profile.getUsername());
				
				if (driverProfile != null) {
					StringTokenizer tokenizer = new StringTokenizer(messageBody, " ");
					String firstToken = tokenizer.nextToken();
					Long eventID = Long.parseLong(firstToken);
					StashedEvent stashed = stashedEventManager.retrieve(eventID);
					
					if (   stashed != null
						&& stashed.getEvent() != null
						&& StringUtils.equals(stashed.getDriverUsername(), profile.getUsername())
						&& stashed.getShopProfileID() != null) {
						Event event = stashed.getEvent();
						
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
							
							Long shopProfileID = stashed.getShopProfileID();
							String bidAvailableESL = driverProfile.getRegisteredESLs().get(shopProfileID).get("rfq:bid_available");
							eventGenerator.sendEvent(bidAvailableESL, bidAvailableEvent);
						}
					}
				}
			}
		}
		catch (EventRenderingException e) {
			logger.warning(GlobalLogUtils.formatHandledExceptionMessage("EDC: Can't parse Event", e, GlobalLogUtils.DO_PRINT_STACKTRACE));
			e.printStackTrace();
		}
		catch (NumberFormatException e) {
			logger.warning(GlobalLogUtils.formatHandledExceptionMessage("EDC: Can't parse Number", e, GlobalLogUtils.DO_PRINT_STACKTRACE));
			e.printStackTrace();
		}
		catch (IOException e) {
			// problem in preparing the response
			logger.warning(GlobalLogUtils.formatHandledExceptionMessage("EDC: Couldn't prepare response", e, GlobalLogUtils.DO_PRINT_STACKTRACE));
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

					double distanceInMiles = -1.0; // don't know where the user is
					if (   userProfile != null
						&& userProfile.getLastKnownLatitude() != null
						&& userProfile.getLastKnownLongitude() != null) {
						
						distanceInMiles = SphericalUtils.greatCircleVincenty(
							SphericalUtils.EARTH_RADIUS_mi,
							40, -111, // TODO remove magic values, extract these from the event
							userProfile.getLastKnownLatitude(), userProfile.getLastKnownLongitude());	
					}
					
					if (	!doDistanceCheck
						||  (   0.0 <= distanceInMiles
						     && distanceInMiles <= 5.0)) {
						BasicEventImpl bidAvailableEvent = null;
						try {
							bidAvailableEvent = new BasicEventImpl("rfq", "bid_available");
							bidAvailableEvent.addAttribute("driver_name", driverUsername);
							bidAvailableEvent.addAttribute("shop_key", event.getAttribute("shop_key"));
							bidAvailableEvent.addAttribute("delivery_id", event.getAttribute("delivery_id"));
							bidAvailableEvent.addAttribute("delivery_time_est", "5:00 PM");
							bidAvailableEvent.addAttribute("amount", new Float(5.0f));
							bidAvailableEvent.addAttribute("amount_units", "USD");
						}
						catch (EventRenderingException e) {
							logger.warning(GlobalLogUtils.formatHandledExceptionMessage(
									String.format("guild channel %s:%s handler", getDomain(), getName()),
									e, GlobalLogUtils.DO_PRINT_STACKTRACE));
							e.printStackTrace();
						}
						
						if (bidAvailableEvent != null) {
							EventChannelUtils.notify(bidAvailableEvent, channel, eventGenerator);
						}
					}
					else if (   userProfile != null
							 && userProfile.getTextableNumber() != null) {
						// TODO double check logic here
						logger.info("EDC: stashing event...");
						
						StashedEvent stashed = new StashedEvent();
						stashed.setEvent(event);
						stashed.setShopProfileID(1L);
						stashed.setDriverUsername(driverUsername);
						stashedEventManager.register(stashed);
						Long stashID = stashed.getId();
						
						logger.info("EDC: event stashed.");
						
						// Based on Twilio's documentation: http://www.twilio.com/docs/api/rest/sending-sms
						logger.info("EDC: preparing twilio message");
						TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
						
					    Map<String, String> params = new HashMap<String, String>();
					    String messageContent = String.format("Flower Delivery Ready: id: %d, shop: %s, pickup: %s, address: %s",
					    		stashID,
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
					logger.info(String.format("received %s:%s", getDomain(), getName()));
				}
				
			});
		}
		return guildChannelEventHandlers;
	}
}
