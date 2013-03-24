package kpbinc.cs462.shared.event;

public interface EventHandler {

	/**
	 * @param event
	 * @return
	 * 
	 * @throws NullPointerException if event is null
	 */
	boolean handles(Event event);

	/**
	 * @param event
	 * @return
	 * 
	 * @throws NullPointerException if event is null
	 */
	void handle(Event event);
	
}
