package kpbinc.cs462.shared.event;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

public class BasicEventImpl implements Event {

	//= Member Data ====================================================================================================
	
	private String domain;
	private String name;
	private Map<String, Object> attributes;
	
	
	//= Initialization =================================================================================================
	
	/**
	 * @param domain event domain name, must not be blank
	 * @param name event name, must not be blank
	 * 
	 * @throws IllegalArgumentException if domain or name are blank.
	 */
	public BasicEventImpl(String domain, String name) {
		// Preconditions
		if (StringUtils.isBlank(domain)) {
			throw new IllegalArgumentException("domain must not be blank");
		}
		if (StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("name must not be blank");
		}
		
		this.domain = domain;
		this.name = name;
		this.attributes = new TreeMap<String, Object>();
	}
	
	
	//= Interface ======================================================================================================

	@Override
	public String getDomain() {
		return domain;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return Collections.unmodifiableMap(attributes);
	}

	public Object addAttribute(String attribName, Object value) {
		Object previousValue = attributes.put(attribName, value);
		return previousValue;
	}
	
}
