package kpbinc.cs462.shared.event;

public interface EventChannelEventHandler {

	/**
	 * @param event
	 * @return
	 * 
	 * @throws NullPointerException if event is null
	 */
	boolean handles(Event event);

	/**
	 * @param event
	 * @param channel channel on which the event was received, may be null if the channel is unknown or the event should
	 * be processed in a free-standing fashion
	 * 
	 * @throws NullPointerException if event is null
	 */
	void handle(Event event, EventChannel<?, ?> channel);
	
}
