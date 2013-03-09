package kpbinc.cs462.shared.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

public class BasicEventImpl implements Event {

	//= Member Data ====================================================================================================
	
	private String domain;
	private String name;
	private Map<String, List<Object>> attributes;
	
	
	//= Initialization =================================================================================================
	
	/**
	 * @param domain event domain name, must not be blank
	 * @param name event name, must not be blank
	 * 
	 * @throws EventRenderingException if domain or name are blank.
	 */
	public BasicEventImpl(String domain, String name) throws EventRenderingException {
		// Preconditions
		if (StringUtils.isBlank(domain)) {
			throw new EventRenderingException("domain must not be blank");
		}
		if (StringUtils.isBlank(name)) {
			throw new EventRenderingException("name must not be blank");
		}
		
		this.domain = domain;
		this.name = name;
		this.attributes = new TreeMap<String, List<Object>>();
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
	public Map<String, List<Object>> getAttributes() {
		return Collections.unmodifiableMap(attributes);
	}

	/**
	 * Adds the attribute name-value pair to this event.
	 * 
	 * @param attribName name of the attribute
	 * @param value value of the attribute, or a value of the attribute if multi-valued
	 */
	public void addAttribute(String attribName, Object value) {
		if (!attributes.containsKey(attribName)) {
			attributes.put(attribName, new ArrayList<Object>());
		}
		attributes.get(attribName).add(value);
	}
	
}
