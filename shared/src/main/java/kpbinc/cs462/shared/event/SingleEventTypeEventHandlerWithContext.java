package kpbinc.cs462.shared.event;

import java.util.logging.Logger;

import kpbinc.util.logging.GlobalLogUtils;

public abstract class SingleEventTypeEventHandlerWithContext<C>
	extends SingleEventTypeEventHandler
	implements EventHandlerWithContext<C> {
	
	//= Class Data =====================================================================================================
	
	private static final Logger logger = Logger.getLogger(SingleEventTypeEventHandlerWithContext.class.getName());
	
	
	//= Initialization =================================================================================================
	
	public SingleEventTypeEventHandlerWithContext(String domain, String name) {
		super(domain, name);
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	@Override
	public void handle(Event event, C context) {
		if (handles(event)) {
			String coreMessage = String.format("handling %s:%s event received in context %s.",
					getDomain(), getName(), (context != null ? context.toString() : "null"));
			
			logger.info(coreMessage + "..");
			
			handleImpl(event, context);
			
			logger.info("done " + coreMessage);
		}
		// else ignore the event	
	}
	
	
	//= Support ========================================================================================================
	
	@Override
	protected void handleImpl(Event event) {
		handle(event, null);
	}

	protected abstract void handleImpl(Event event, C context);

}
