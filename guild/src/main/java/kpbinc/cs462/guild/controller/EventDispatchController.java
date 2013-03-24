package kpbinc.cs462.guild.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kpbinc.cs462.guild.model.GuildFlowerShopEventChannel;
import kpbinc.cs462.guild.model.manage.GuildFlowerShopEventChannelManager;
import kpbinc.cs462.shared.event.EventTransformer;
import kpbinc.util.logging.GlobalLogUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/esl")
public class EventDispatchController extends GuildBaseSiteContextController {

	//= Class Data =====================================================================================================
	
	private static final Logger logger = Logger.getLogger(EventDispatchController.class.getName());
	
	
	//= Member Data ====================================================================================================
	
	@Autowired
	private EventTransformer eventTransformer;
	
	@Autowired
	private GuildFlowerShopEventChannelManager guildFlowerShopEventChannelManager;
	
	
	//= Initialization =================================================================================================
	
	public EventDispatchController() {
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	//- Web API --------------------------------------------------------------------------------------------------------
	
	@RequestMapping(value = "/shop/channel/{channel-id}")
	public void dispatchEventFromShop(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "channel-id") Long channelId) {
		// Process Event and Prepare the Response
		String responseString = "Received.";
		
		GuildFlowerShopEventChannel channel = guildFlowerShopEventChannelManager.retrieve(channelId);
		if (channel != null) {
			if (StringUtils.isNotBlank(channel.getSendESL())) {
				// TODO process the event
			}
			else {
				responseString = "Channel not fully configured. Has no send ESL.";
			}
		}
		else {
			responseString = "Channel invalid.";
		}
		
		// Send the response
		try {
			PrintWriter responsePayloadWriter = response.getWriter();
			responsePayloadWriter.write(responseString);
			responsePayloadWriter.flush();
		}
		catch (IOException e) {
			logger.warning(GlobalLogUtils.formatHandledExceptionMessage(
					"dispatch shop event: may not have sent response", e, GlobalLogUtils.DO_PRINT_STACKTRACE));
			e.printStackTrace();
		}
	}
	
}
