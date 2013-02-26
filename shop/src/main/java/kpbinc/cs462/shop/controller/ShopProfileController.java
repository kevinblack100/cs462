package kpbinc.cs462.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kpbinc.cs462.shop.model.ShopProfile;
import kpbinc.cs462.shop.model.ShopProfileManager;
import kpbinc.util.logging.GlobalLogUtils;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/shop")
public class ShopProfileController extends BaseController {

	//~ Member Data ====================================================================================================
	
	@Autowired
	private ShopProfileManager shopProfileManager;
	
	
	//~ Initialization =================================================================================================
	
	public ShopProfileController() {
		GlobalLogUtils.logConstruction(this);
	}

	
	//~ Interface ======================================================================================================
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String getShopProfileForm(ModelMap model) {
		ShopProfile profile = shopProfileManager.getProfile();
		model.addAttribute("profile", profile);		
		return "shop/profile";
	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String saveShopProfileChanges(
			@RequestParam(value = "shop-name", required = true) String name,
			@RequestParam(value = "shop-address", required = true) String address) {
		
		ShopProfile newProfile = new ShopProfile(name, address);
		shopProfileManager.updateProfile(newProfile);
		
		return "redirect:/ffds/";
	}
	
}
