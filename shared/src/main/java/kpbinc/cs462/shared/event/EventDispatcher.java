package kpbinc.cs462.shared.event;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kpbinc.cs462.shared.model.manage.StorageManager;
import kpbinc.util.logging.GlobalLogUtils;

public class EventDispatcher {

	//= Class Data =====================================================================================================
	
	private static final Logger logger = Logger.getLogger(EventDispatcher.class.getName());
	
	
	//= Interface ======================================================================================================
	
	public static <T extends EventChannel<?, ?>> void dispatchEvent(
			String logMessagePrefix,
			HttpServletRequest request,
			HttpServletResponse response,
			EventTransformer eventTransformer,
			Long channelId,
			StorageManager<Long, T> channelManager,
			Collection<EventHandler> channelEventHandlers) {
		// Parse/Render Event and Prepare the Response
		Event event = null;
		String responseString = "Received.";
		
		T channel = channelManager.retrieve(channelId);
		if (channel != null) {
			if (StringUtils.isNotBlank(channel.getSendESL())) {
				try {
					@SuppressWarnings("unchecked")
					Map<String, String[]> parameters = request.getParameterMap();
					
					event = eventTransformer.transform(parameters);
					
					logger.info(String.format("%s: parsed %s:%s event",
							logMessagePrefix, event.getDomain(), event.getName()));
				}
				catch (EventRenderingException e) {
					responseString = "Could not render event: " + e.getMessage();
					logger.warning(GlobalLogUtils.formatHandledExceptionMessage(
							logMessagePrefix + ": could not render event", e, GlobalLogUtils.DO_PRINT_STACKTRACE));
					e.printStackTrace();
				}
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
					logMessagePrefix + ": may not have sent response", e, GlobalLogUtils.DO_PRINT_STACKTRACE));
			e.printStackTrace();
		}
		
		// Process Event
		for (EventHandler handler : channelEventHandlers) {
			handler.handle(event, channel);
		}
	}

}
