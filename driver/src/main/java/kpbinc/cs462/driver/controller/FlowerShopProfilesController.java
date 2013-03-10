package kpbinc.cs462.driver.controller;

import kpbinc.cs462.driver.model.FlowerShopProfile;
import kpbinc.cs462.driver.model.manage.FlowerShopProfileManager;
import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/shops")
public class FlowerShopProfilesController {

	//= Class Data =====================================================================================================
	
	
	//= Member Data ====================================================================================================
	
	@Autowired
	private FlowerShopProfileManager flowerShopProfileManager;
	
	
	//= Initialization =================================================================================================
	
	public FlowerShopProfilesController() {
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	//- Web API --------------------------------------------------------------------------------------------------------
	
	@RequestMapping
	public String getShopsList() {
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
	
	//- Member API -----------------------------------------------------------------------------------------------------
	
	public FlowerShopProfileManager getManager() {
		return flowerShopProfileManager;
	}
	
}
