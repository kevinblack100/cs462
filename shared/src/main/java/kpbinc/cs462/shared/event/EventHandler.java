package kpbinc.cs462.shared.event;

public interface EventHandler {

	/**
	 * @param event
	 * @return true if this EventHandler may handle the event, false otherwise.
	 * 
	 * @throws NullPointerException if event is null
	 */
	boolean handles(Event event);

	/**
	 * @param event
	 * 
	 * @throws NullPointerException if event is null
	 */
	void handle(Event event);
	
}
