package kpbinc.cs462.shop.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kpbinc.cs462.shared.event.ESLGenerator;
import kpbinc.cs462.shop.model.FlowerShopGuildEventChannel;
import kpbinc.cs462.shop.model.ShopProfile;
import kpbinc.cs462.shop.model.manage.FlowerShopGuildEventChannelManager;
import kpbinc.cs462.shop.model.manage.ShopProfileManager;
import kpbinc.net.URLPathBuilder;
import kpbinc.util.logging.GlobalLogUtils;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/shop")
public class ShopProfileController extends ShopBaseSiteContextController {

	//= Class Data =====================================================================================================
	
	private static final Logger logger = Logger.getLogger(ShopProfileController.class.getName());
	
	
	//= Member Data ====================================================================================================
	
	@Autowired
	private ESLGenerator eslGenerator;
	
	@Autowired
	private ShopProfileManager shopProfileManager;
	
	@Autowired
	private FlowerShopGuildEventChannelManager flowerShopGuildEventChannelManager;
	
	
	//= Initialization =================================================================================================
	
	public ShopProfileController() {
		super();
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String getShopProfileForm(
			HttpServletRequest request,
			ModelMap model) {
		ShopProfile profile = shopProfileManager.getProfile();
		model.addAttribute("profile", profile);
		
		FlowerShopGuildEventChannel channel = ensureChannelInitialized(request);
		model.addAttribute("channel", channel);
		
		return "shop/profile";
	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String saveShopProfileChanges(
			HttpServletRequest request,
			@RequestParam(value = "shop-name", required = true) String name,
			@RequestParam(value = "shop-address", required = true) String address,
			@RequestParam(value = "shop-latitude", required = true) Double latitude,
			@RequestParam(value = "shop-longitude", required = true) Double longitude,
			@RequestParam(value = "channel-send-esl", required = true) String sendESL) {
		
		ShopProfile newProfile = new ShopProfile(name, address);
		newProfile.setLatitude(latitude);
		newProfile.setLongitude(longitude);
		shopProfileManager.updateProfile(newProfile);
		
		// Update the Event Channel
		FlowerShopGuildEventChannel channel = ensureChannelInitialized(request);
		channel.setSendESL(sendESL);
		flowerShopGuildEventChannelManager.update(channel);
		
		return "redirect:/" + getContextPaths().getDynamicRelativePath() + "/";
	}
	
	
	//= Support ========================================================================================================
	
	private FlowerShopGuildEventChannel ensureChannelInitialized(HttpServletRequest request) {
		// Get/Create Channel
		FlowerShopGuildEventChannel channel = 
				flowerShopGuildEventChannelManager.retrieve(FlowerShopGuildEventChannelManager.DEFAULT_EVENT_CHANNEL_ID);
		if (channel == null) {
			channel = new FlowerShopGuildEventChannel();
			channel.setId(FlowerShopGuildEventChannelManager.DEFAULT_EVENT_CHANNEL_ID);
			flowerShopGuildEventChannelManager.register(channel);
		}
		
		if (StringUtils.isBlank(channel.getReceiveESL())) {
			// Generate and store the receive ESL
			try {
				String receiveESLPath = URLPathBuilder.build("esl", "guild", "channel", channel.getId().toString());
				URL receiveESL = eslGenerator.generate(request, receiveESLPath);
				channel.setReceiveESL(receiveESL.toString());
				flowerShopGuildEventChannelManager.update(channel);
			}
			catch (MalformedURLException e) {
				logger.warning(GlobalLogUtils.formatHandledExceptionMessage(
						"Receive ESL for Guild to Flower Shop", e, GlobalLogUtils.DO_PRINT_STACKTRACE));
				e.printStackTrace();
			}
		}
		
		return channel;
	}
}
