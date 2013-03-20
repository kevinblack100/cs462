package kpbinc.cs462.driver.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kpbinc.cs462.driver.model.UserProfile;
import kpbinc.cs462.driver.model.manage.UserProfileManager;
import kpbinc.io.util.JavaJsonAccess;
import kpbinc.io.util.JsonSerializer;
import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles notifications (pushes) from external APIs.
 * 
 * @author Kevin Black
 */
@Controller
@Scope(value = "request")
@RequestMapping(value = "/notification")
public class ExternalAPINotificationsController {

	//= Class Data =====================================================================================================
	
	private static final Logger logger = Logger.getLogger(ExternalAPINotificationsController.class.getName());
	
	
	//= Member Data ====================================================================================================
	
	@Autowired
	private UserProfileManager userProfileManager;
	
	
	//= Initialization =================================================================================================
	
	public ExternalAPINotificationsController() {
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	@RequestMapping(value = "/foursquare/checkin")
	public void handleFoursquareCheckin(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "checkin") String checkin,
			@RequestParam(value = "user") String user,
			@RequestParam(value = "secret") String secret) {
		
		try {
			PrintWriter responseBodyWriter = response.getWriter();
			responseBodyWriter.write("received");
			
			// TODO check the given secret
			
			@SuppressWarnings("unchecked")
			Map<String, Object> checkinObject = JsonSerializer.deserialize(checkin, Map.class);
			
			String userID = JavaJsonAccess.getValueAs(checkinObject, "user", "id");
			Double latitude = JavaJsonAccess.getValueAs(checkinObject, "venue", "location", "lat");
			Double longitude = JavaJsonAccess.getValueAs(checkinObject, "venue", "location", "lng");
			
			logger.info(String.format("checkin for user %s at: %f lat, %f lng", userID, latitude, longitude));
			
			Collection<UserProfile> profiles = userProfileManager.getByApiID("foursquare", userID);
			for (UserProfile profile : profiles) {
				profile.setLastKnownLatitude(latitude);
				profile.setLastKnownLongitude(longitude);
				userProfileManager.update(profile);
			}
		}
		catch (IOException e) {
			logger.warning("Unexpected IOException: " + e.getMessage());
			e.printStackTrace();
		}
		catch (ClassCastException e) {
			logger.warning("ClassCastException: " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
}
