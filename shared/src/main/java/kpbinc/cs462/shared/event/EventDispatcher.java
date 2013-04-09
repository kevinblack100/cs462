package kpbinc.cs462.shared.event;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kpbinc.cs462.shared.model.LoggedEvent;
import kpbinc.cs462.shared.model.manage.LoggedEventManager;
import kpbinc.cs462.shared.model.manage.StorageManager;
import kpbinc.util.ValueResult;
import kpbinc.util.logging.GlobalLogUtils;

public class EventDispatcher {

	//= Class Data =====================================================================================================
	
	private static final Logger logger = Logger.getLogger(EventDispatcher.class.getName());
	
	
	//= Interface ======================================================================================================
	
	public static void dispatchEvent(
			String logMessagePrefix,
			HttpServletRequest request,
			HttpServletResponse response,
			EventTransformer eventTransformer,
			Collection<EventHandler> eventHandlers) {
		dispatchEvent(
				logMessagePrefix,
				request,
				response,
				eventTransformer,
				eventHandlers,
				null,
				null);
	}
	
	/**
	 * 
	 * @param logMessagePrefix
	 * @param request
	 * @param response
	 * @param eventTransformer
	 * @param eventHandlers
	 * @param loggedEventManager may be null
	 */
	public static void dispatchEvent(
			String logMessagePrefix,
			HttpServletRequest request,
			HttpServletResponse response,
			EventTransformer eventTransformer,
			Collection<EventHandler> eventHandlers,
			LoggedEventManager loggedEventManager,
			String receiveEsl) {
		// Render Event and Prepare the Response
		ValueResult<Event> result = renderEvent(logMessagePrefix, request, eventTransformer);
		String responseString = result.getMessage();
		Event event = result.getValue();
		
		// Log reception of the Event
		logEvent(event, loggedEventManager, receiveEsl);
		
		// Send response
		sendResponse(logMessagePrefix, response, responseString);
		
		// Process Event
		if (event != null) {
			for (EventHandler handler : eventHandlers) {
				handler.handle(event);
			}
		}
	}
	
	public static <T extends EventChannel<?, ?>> void dispatchEventFromChannel(
			String logMessagePrefix,
			HttpServletRequest request,
			HttpServletResponse response,
			EventTransformer eventTransformer,
			Long channelId,
			StorageManager<Long, T> channelManager,
			Collection<EventChannelEventHandler<T>> channelEventHandlers) {
		dispatchEventFromChannel(
				logMessagePrefix,
				request,
				response,
				eventTransformer,
				channelId,
				channelManager,
				channelEventHandlers,
				null);
	}
	
	public static <T extends EventChannel<?, ?>> void dispatchEventFromChannel(
			String logMessagePrefix,
			HttpServletRequest request,
			HttpServletResponse response,
			EventTransformer eventTransformer,
			Long channelId,
			StorageManager<Long, T> channelManager,
			Collection<EventChannelEventHandler<T>> channelEventHandlers,
			LoggedEventManager loggedEventManager) {
		// Parse/Render Event and Prepare the Response
		Event event = null;
		String responseString = null;
		
		T channel = channelManager.retrieve(channelId);
		if (channel != null) {
			if (StringUtils.isNotBlank(channel.getSendESL())) {
				ValueResult<Event> result = renderEvent(logMessagePrefix, request, eventTransformer);
				responseString = result.getMessage();
				event = result.getValue();
			}
			else {
				responseString = "Channel not fully configured. Has no send ESL.";
			}
		}
		else {
			responseString = "Channel invalid.";
		}
		
		// Log reception of the Event
		logEvent(event, loggedEventManager, channel.getReceiveESL());
		
		// Send the response
		sendResponse(logMessagePrefix, response, responseString);
		
		// Process Event
		if (event != null) {
			for (EventChannelEventHandler<T> handler : channelEventHandlers) {
				handler.handle(event, channel);
			}
		}
	}

	
	//= Support ========================================================================================================
	
	private static ValueResult<Event> renderEvent(
			String logMessagePrefix,
			HttpServletRequest request,
			EventTransformer eventTransformer) {
		try {
			@SuppressWarnings("unchecked")
			Map<String, String[]> parameters = request.getParameterMap();
			
			Event event = eventTransformer.transform(parameters);
			
			logger.info(String.format("%s: parsed %s:%s event",
					logMessagePrefix, event.getDomain(), event.getName()));
			
			return new ValueResult<Event>("Received.", event);
		}
		catch (EventRenderingException e) {
			String message = String.format("Could not render event: %s.", e.getMessage());
			
			logger.warning(GlobalLogUtils.formatHandledExceptionMessage(
					logMessagePrefix + ": could not render event", e, GlobalLogUtils.DO_PRINT_STACKTRACE));
			e.printStackTrace();
			
			return new ValueResult<Event>(message);
		}
	}
	
	private static void logEvent(Event event, LoggedEventManager loggedEventManager, String receiveESL) {
		if (loggedEventManager != null) {
			LoggedEvent loggedEvent = new LoggedEvent();
			loggedEvent.setEvent(event);
			loggedEvent.setTransmissionType(LoggedEvent.TransmissionType.RECEIVED);
			loggedEvent.setEsl(receiveESL);
			loggedEventManager.register(loggedEvent);
		}
	}
	
	private static void sendResponse(String logMessagePrefix, HttpServletResponse response, String message) {
		try {
			PrintWriter responsePayloadWriter = response.getWriter();
			responsePayloadWriter.write(message);
			responsePayloadWriter.flush();
		}
		catch (IOException e) {
			logger.warning(GlobalLogUtils.formatHandledExceptionMessage(
					logMessagePrefix + ": may not have sent response", e, GlobalLogUtils.DO_PRINT_STACKTRACE));
			e.printStackTrace();
		}
	}
	
}
