package kpbinc.cs462.driver.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import kpbinc.cs462.driver.model.DriverProfile;
import kpbinc.cs462.driver.model.FlowerShopProfile;
import kpbinc.cs462.driver.model.manage.DriverProfileManager;
import kpbinc.cs462.driver.model.manage.FlowerShopProfileManager;
import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/shops")
public class FlowerShopProfilesController extends DriverBaseSiteContextController {

	//= Class Data =====================================================================================================
	
	private static final Logger logger = Logger.getLogger(FlowerShopProfilesController.class.getName());
	
	
	//= Member Data ====================================================================================================
	
	@Autowired
	private FlowerShopProfileManager flowerShopProfileManager;
	
	@Autowired
	private DriverProfileManager driverProfileManager;
	
	
	//= Initialization =================================================================================================
	
	public FlowerShopProfilesController() {
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	//- Web API --------------------------------------------------------------------------------------------------------
	
	@RequestMapping
	public String getShopsList(ModelMap model) {
		setDriverProfile(model);
		return "shops/list";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createShop(
			@RequestParam(value = "name") String name,
			@RequestParam(value = "location") String location,
			@RequestParam(value = "esl") String eventSignalURL) {
		
		if (   name != null
			&& location != null
			&& eventSignalURL != null) {
			
			// ID hack
			String combined = name + location + eventSignalURL;
			Long id = (long) combined.hashCode();
			
			FlowerShopProfile profile = new FlowerShopProfile();
			profile.setId(id);
			profile.setName(name);
			profile.setLocation(location);
			profile.setEventSignalURL(eventSignalURL);
			
			flowerShopProfileManager.register(id, profile);
		}
		else {
			// TODO set error message
		}
		
		return "redirect:/pages/shops/";
	}
	
	@RequestMapping(value = "/generate-delivery-ready-esl", method = RequestMethod.POST)
	public String generateDeliveryReadyESL(
			HttpServletRequest request,
			@RequestParam(value = "shop-profile-id") Long shopProfileID) {
		
		// check that the ID is valid
		FlowerShopProfile shopProfile = flowerShopProfileManager.get(shopProfileID);
		if (shopProfile != null) {
			UserDetails loggedInUserDetails = getLoggedInUserContext().getSignedInUserDetails();
			if (loggedInUserDetails != null) {
				String driverName = loggedInUserDetails.getUsername();
				DriverProfile driverProfile = driverProfileManager.get(driverName);
				if (driverProfile == null) {
					driverProfile = new DriverProfile(driverName);
				}
				
				if (!driverProfile.getDeliveryReadyESLs().containsKey(shopProfileID)) {
					try {
						URL requestURL = new URL(request.getRequestURL().toString());
						String eslProtocol = requestURL.getProtocol();
						String eslHost = requestURL.getHost();
						int eslPort = requestURL.getPort();
						String eslFile = new StringBuilder(request.getContextPath())
								.append("/pages/esl/delivery_ready/")
								.append(shopProfileID)
								.append("/").append(driverName)
								.toString();
						URL esl = new URL(eslProtocol, eslHost, eslPort, eslFile);
						driverProfile.addDeliveryReadyESL(shopProfileID, esl.toString());
						driverProfileManager.update(driverName, driverProfile);
					}
					catch (MalformedURLException e) {
						logger.warning("Unexpected MalformedURLException: " + e.getMessage());
						e.printStackTrace();
					}
				}
				// else ESL already generated, so the call to this method was erroneous
			}
			else {
				// No driver, can't generate ESL
				// TODO set error message
			}
		}
		else {
			// TODO set error message
		}
		
		return "redirect:/pages/shops/";
	}
	
	//- Member API -----------------------------------------------------------------------------------------------------
	
	public FlowerShopProfileManager getFlowerShopManager() {
		return flowerShopProfileManager;
	}
	
	
	//= Support ========================================================================================================
	
	private void setDriverProfile(ModelMap model) {
		UserDetails loggedInUserDetails = getLoggedInUserContext().getSignedInUserDetails();
		if (loggedInUserDetails != null) {
			String username = loggedInUserDetails.getUsername();
			DriverProfile profile = driverProfileManager.get(username);
			model.addAttribute("driverProfile", profile);
		}
	}
	
}
