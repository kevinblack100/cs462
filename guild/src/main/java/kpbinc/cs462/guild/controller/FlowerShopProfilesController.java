package kpbinc.cs462.guild.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import kpbinc.cs462.guild.model.GuildFlowerShopEventChannel;
import kpbinc.cs462.guild.model.manage.GuildFlowerShopEventChannelManager;
import kpbinc.cs462.shared.event.ESLGenerator;
import kpbinc.cs462.shared.model.FlowerShopProfile;
import kpbinc.cs462.shared.model.manage.FlowerShopProfileManager;
import kpbinc.net.URLPathBuilder;
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
	
	public static class FlowerShopDataContainer {
		private FlowerShopProfile profile;
		private GuildFlowerShopEventChannel channel;
		
		public FlowerShopDataContainer(
				FlowerShopProfile profile,
				GuildFlowerShopEventChannel channel) {
			GlobalLogUtils.logConstruction(this);
			setProfile(profile);
			setChannel(channel);
		}

		public FlowerShopProfile getProfile() {
			return profile;
		}

		public void setProfile(FlowerShopProfile profile) {
			this.profile = profile;
		}

		public GuildFlowerShopEventChannel getChannel() {
			return channel;
		}

		public void setChannel(GuildFlowerShopEventChannel channel) {
			this.channel = channel;
		}
		
	}
	
	
	//= Member Data ====================================================================================================

	@Autowired
	private ESLGenerator eslGenerator;
	
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
		Collection<FlowerShopDataContainer> containers = new ArrayList<FlowerShopDataContainer>();
		Collection<FlowerShopProfile> flowerShopProfiles = flowerShopProfileManager.retrieveAll();
		for (FlowerShopProfile profile : flowerShopProfiles) {
			GuildFlowerShopEventChannel channel = guildFlowerShopEventChannelManager.retrieveByFlowerShopId(profile.getId());
			FlowerShopDataContainer container = new FlowerShopDataContainer(profile, channel);
			containers.add(container);
		}
		model.addAttribute("flowerShopDataContainers", containers);
		
		return "shops/shops_list";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createShop(
			HttpServletRequest request,
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
			
			// Generate and store the receive ESL
			try {
				String receiveESLPath = URLPathBuilder.build("esl", "shop", "channel", channel.getId().toString());
				URL receiveESL = eslGenerator.generate(request, receiveESLPath);
				channel.setReceiveESL(receiveESL.toString());
				guildFlowerShopEventChannelManager.update(channel);
			}
			catch (MalformedURLException e) {
				logger.warning(GlobalLogUtils.formatHandledExceptionMessage(
						"Receive ESL for Flower Shop to Guild", e, GlobalLogUtils.DO_PRINT_STACKTRACE));
				e.printStackTrace();
			}
		}
		else {
			// TODO set error message
		}
		
		return "redirect:/" + getContextPaths().getDynamicRelativePath() + "/shops/";
	}
	
	@RequestMapping(value = "/update-send-esl")
	public String updateSendESL(
			@RequestParam(value = "channel-id", required = true) Long channelId, 
			@RequestParam(value = "send-esl", required = true) String sendESL) {
		
		GuildFlowerShopEventChannel channel = guildFlowerShopEventChannelManager.retrieve(channelId);
		if (channel != null) {
			channel.setSendESL(sendESL);
			guildFlowerShopEventChannelManager.update(channel);
		}
		
		return "redirect:/" + getContextPaths().getDynamicRelativePath() + "/shops/";
	}
}
