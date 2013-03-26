package kpbinc.cs462.shared.event;

import java.util.Collection;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

public class EventChannelUtils {

	//= Class Data =====================================================================================================
	
	private static final Logger logger = Logger.getLogger(EventChannelUtils.class.getName());
	
	
	//= Interface ======================================================================================================
	
	/**
	 * @param event
	 * @param channel
	 * @param eventGenerator
	 * 
	 * @throws NullPointerException if event, channel, or eventGenerator are null
	 */
	public static void notify(Event event, EventChannel<?, ?> channel, EventGenerator eventGenerator) {
		Validate.notNull(event, "event must not be null");
		Validate.notNull(channel, "channel must not be null");
		Validate.notNull(eventGenerator, "eventGenerator must not be null");
		
		if (StringUtils.isNotBlank(channel.getSendESL())) {
			boolean success = eventGenerator.sendEvent(channel.getSendESL(), event);
			logger.info(String.format("%s:%s sent to %s: %s", 
					event.getDomain(),
					event.getName(),
					channel.getSendESL(),
					(success ? "SUCCESS" : "FAILED")));
		}
	}

	/**
	 * @param event
	 * @param channels
	 * @param eventGenerator
	 * 
	 * @throws NullPointerException if event, any of the channels, or eventGenerator are null
	 */
	public static void notify(Event event, Collection<? extends EventChannel<?, ?>> channels, EventGenerator eventGenerator) {
		for (EventChannel<?, ?> channel : channels) {
			notify(event, channel, eventGenerator);
		}
	}
	
}
