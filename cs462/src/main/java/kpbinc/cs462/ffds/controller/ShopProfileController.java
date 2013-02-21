package kpbinc.cs462.ffds.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kpbinc.common.util.logging.GlobalLogUtils;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/shop")
public class ShopProfileController extends BaseController {

	//~ Initialization =================================================================================================
	
	public ShopProfileController() {
		GlobalLogUtils.logConstruction(this);
	}

	
	//~ Interface ======================================================================================================
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String getShopProfileForm() {
		return "shop/profile";
	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String saveShopProfileChanges() {
		return "redirect:/ffds/";
	}
	
}
