package kpbinc.cs462.guild.controller;

import java.util.Collection;

import kpbinc.cs462.guild.model.GuildFlowerShopEventChannel;
import kpbinc.cs462.guild.model.manage.GuildFlowerShopEventChannelManager;
import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/events/channels")
public class EventChannelsController extends GuildBaseSiteContextController {

	//= Member Data ====================================================================================================
	
	@Autowired
	private GuildFlowerShopEventChannelManager guildFlowerShopEventChannelManager;
	
	
	//= Initialization =================================================================================================
	
	public EventChannelsController() {
		super();
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	//- Web API --------------------------------------------------------------------------------------------------------
	
	@RequestMapping
	public String listAllChannels(ModelMap model) {
		Collection<GuildFlowerShopEventChannel> guildFlowerShopEventChannels = 
				guildFlowerShopEventChannelManager.retrieveAll();
		model.addAttribute("guildFlowerShopEventChannels", guildFlowerShopEventChannels);
		return "events/channels/channels_list";
	}
	
}
