package kpbinc.cs462.shared.event;

/**
 * Exception to indicate that there is an error in rendering (representing) an event. Rendering exceptions may occur
 * when constructing an event, or when parsing (deserializing).
 * 
 * @author Kevin Black
 */
public class EventRenderingException extends Exception {

	//= Class Data =====================================================================================================
	
	private static final long serialVersionUID = 1L;
	
	
	//= Initialization =================================================================================================

	public EventRenderingException() {
		super();
	}

	public EventRenderingException(String message) {
		super(message);
	}

	public EventRenderingException(Throwable cause) {
		super(cause);
	}

	public EventRenderingException(String message, Throwable cause) {
		super(message, cause);
	}

}
