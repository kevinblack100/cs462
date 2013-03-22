package kpbinc.cs462.guild.controller;

import java.util.Collection;
import java.util.logging.Logger;

import kpbinc.cs462.guild.model.GuildFlowerShopEventChannel;
import kpbinc.cs462.guild.model.manage.GuildFlowerShopEventChannelManager;
import kpbinc.cs462.shared.model.FlowerShopProfile;
import kpbinc.cs462.shared.model.manage.FlowerShopProfileManager;
import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/shops")
public class FlowerShopProfilesController extends GuildBaseSiteContextController {

	//= Class Data =====================================================================================================
	
	private static final Logger logger = Logger.getLogger(FlowerShopProfilesController.class.getName());
	
	
	//= Member Data ====================================================================================================

	@Autowired
	private FlowerShopProfileManager flowerShopProfileManager;
	
	@Autowired
	private GuildFlowerShopEventChannelManager guildFlowerShopEventChannelManager;
	
	
	//= Initialization =================================================================================================
	
	public FlowerShopProfilesController() {
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	//- Web API --------------------------------------------------------------------------------------------------------
	
	@RequestMapping
	public String getShopsList(ModelMap model) {
		Collection<FlowerShopProfile> flowerShopProfiles = flowerShopProfileManager.retrieveAll();
		model.addAttribute("flowerShopProfiles", flowerShopProfiles);
		return "shops/shops_list";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createShop(
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "location", required = true) String location,
			@RequestParam(value = "latitude", required = true) Double latitude,
			@RequestParam(value = "longitude", required = true) Double longitude) {
		
		if (   name != null
			&& location != null
			&& latitude != null
			&& longitude != null) {	
			
			// Create the profile
			FlowerShopProfile profile = new FlowerShopProfile();
			profile.setName(name);
			profile.setLocation(location);
			profile.setLatitude(latitude);
			profile.setLongitude(longitude);
			
			flowerShopProfileManager.register(profile);
			
			// Create the channel stub
			GuildFlowerShopEventChannel channel = new GuildFlowerShopEventChannel();
			channel.setRemoteEntityId(profile.getId());
			
			guildFlowerShopEventChannelManager.register(channel);
		}
		else {
			// TODO set error message
		}
		
		return "redirect:/" + getContextPaths().getDynamicRelativePath() + "/shops/";
	}
	
}
