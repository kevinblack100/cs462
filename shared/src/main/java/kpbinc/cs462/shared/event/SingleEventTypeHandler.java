package kpbinc.cs462.shared.event;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import kpbinc.util.logging.GlobalLogUtils;

public abstract class SingleEventTypeHandler implements EventHandler {

	//= Member Data ====================================================================================================
	
	private String domain;
	private String name;
	
	
	//= Initialization =================================================================================================
	
	/**
	 * @param domain
	 * @param name
	 * 
	 * @throws IllegalArgumentException if domain or name are blank (null, empty, or just whitespace)
	 */
	public SingleEventTypeHandler(String domain, String name) {
		GlobalLogUtils.logConstruction(this);
		setDomain(domain);
		setName(name);
	}
	
	
	//= Interface ======================================================================================================

	@Override
	public boolean handles(Event event) {
		Validate.notNull(event, "event must not be null");
		boolean result = (   StringUtils.equalsIgnoreCase(domain, event.getDomain())
						  && StringUtils.equalsIgnoreCase(name, event.getName()));
		return result;
	}

	@Override
	public void handle(Event event, EventChannel<?, ?> channel) {
		if (handles(event)) {
			handleImpl(event, channel);
		}
		// else ignore the event
	}

	public String getDomain() {
		return domain;
	}

	/**
	 * @param domain
	 * 
	 * @throws IllegalArgumentException if domain is blank
	 */
	public void setDomain(String domain) {
		Validate.notBlank(domain, "domain must not be black");
		this.domain = domain;
	}
	
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 * 
	 * @throws IllegalArgumentException if name is blank
	 */
	public void setName(String name) {
		Validate.notBlank(name, "name must not be blank");
		this.name = name;
	}

	
	//= Support/Handler Implementation =================================================================================
	
	protected abstract void handleImpl(Event event, EventChannel<?, ?> channel);
	
}
