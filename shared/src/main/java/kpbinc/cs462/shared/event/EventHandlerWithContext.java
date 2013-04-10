package kpbinc.cs462.shared.event;

public interface EventHandlerWithContext<C> extends EventHandler {

	void handle(Event event, C context);
	
}
