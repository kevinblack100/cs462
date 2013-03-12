package kpbinc.cs462.driver.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kpbinc.cs462.driver.model.manage.DriverProfileManager;
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
	public void handleFoursquareChecking(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "checkin") String checkin,
			@RequestParam(value = "user") String user,
			@RequestParam(value = "secret") String secret) {
		
		try {
			PrintWriter responseBodyWriter = response.getWriter();
			responseBodyWriter.write("received");
			
			logger.info("checkin: " + checkin);
			logger.info("user: " + user);
			logger.info("secret: " + secret);
			logger.info("end of fourquare checking data output");
		}
		catch (IOException e) {
			logger.warning("Unexpected IOException: " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
}
