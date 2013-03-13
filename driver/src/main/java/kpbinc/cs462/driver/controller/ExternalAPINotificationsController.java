package kpbinc.cs462.driver.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kpbinc.cs462.driver.model.manage.DriverProfileManager;
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
	private DriverProfileManager driverProfileManager;
	
	
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
			
			Object rawUserID = JavaJsonAccess.getValue(checkinObject, "user", "id");
			String userID = rawUserID.toString();
			
			Object rawLatitude = JavaJsonAccess.getValue(checkinObject, "venue", "location", "lat");
			
			Object rawLongitude = JavaJsonAccess.getValue(checkinObject, "venue", "location", "lng");
			
			logger.info(String.format("checkin for user %s at: %s lat, %s lng", userID, rawLatitude.toString(), rawLongitude.toString()));
		}
		catch (IOException e) {
			logger.warning("Unexpected IOException: " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
}
