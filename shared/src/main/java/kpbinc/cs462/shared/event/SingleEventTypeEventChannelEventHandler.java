package kpbinc.cs462.shared.event;

import java.util.logging.Logger;

import kpbinc.util.logging.GlobalLogUtils;

public abstract class SingleEventTypeEventChannelEventHandler<C extends EventChannel<?, ?>>
	extends SingleEventTypeEventHandler
	implements EventChannelEventHandler<C> {

	//= Class Data =====================================================================================================
	
	private static final Logger logger = Logger.getLogger(SingleEventTypeEventChannelEventHandler.class.getName());
	
	
	//= Initialization =================================================================================================
	
	/**
	 * @param domain
	 * @param name
	 * 
	 * @throws IllegalArgumentException if domain or name are blank (null, empty, or just whitespace)
	 */
	public SingleEventTypeEventChannelEventHandler(String domain, String name) {
		super(domain, name);
		GlobalLogUtils.logConstruction(this);
	}
	
	
	//= Interface ======================================================================================================
	
	@Override
	public void handle(Event event, C channel) {
		if (handles(event)) {
			String coreMessage = String.format("handling %s:%s event received on channel %d.",
					getDomain(), getName(), (channel != null ? channel.getId() : -1));
			
			logger.info(coreMessage + "..");
			
			handleImpl(event, channel);
			
			logger.info("done " + coreMessage);
		}
		// else ignore the event
	}

	
	//= Support/Handler Implementation =================================================================================
	
	@Override
	protected void handleImpl(Event event) {
		C channel = null;
		handle(event, channel);
	}
	
	protected abstract void handleImpl(Event event, C channel);
	
}
