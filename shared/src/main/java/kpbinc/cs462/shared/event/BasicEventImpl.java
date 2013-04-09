package kpbinc.cs462.shared.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import kpbinc.util.logging.GlobalLogUtils;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BasicEventImpl implements Event {

	//= Class Data =====================================================================================================
	
	private static final Logger logger = Logger.getLogger(BasicEventImpl.class.getName());
	
	
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
	public BasicEventImpl(
			@JsonProperty(value = "domain") String domain,
			@JsonProperty(value = "name") String name) throws EventRenderingException {
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
	public Event clone() {
		Event copy = null;
		try {
			copy = new BasicEventImpl(getDomain(), getName());
			for (Map.Entry<String, List<Object>> attribute : this.getAttributes().entrySet()) {
				for (Object value : attribute.getValue()) {
					copy.addAttribute(attribute.getKey(), value);
				}
			}
		}
		catch (EventRenderingException e) {
			logger.warning(GlobalLogUtils.formatHandledExceptionMessage(
					"cloning event", e, GlobalLogUtils.DO_PRINT_STACKTRACE));
			e.printStackTrace();
		}
		return copy;
	}
	
	@Override
	public String getDomain() {
		return domain;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Object getAttribute(String attribName) {
		Object value = null;
		if (getAttributes().containsKey(attribName)) {
			List<Object> values = getAttributes().get(attribName);
			if (!values.isEmpty()) {
				value = values.get(0);
			}
		}
		return value;
	}
	
	@Override
	public Map<String, List<Object>> getAttributes() {
		return Collections.unmodifiableMap(attributes);
	}
	
	@Override
	public void addAttribute(String attribName, Object value) throws EventRenderingException {
		// TODO validate that the name and value are not null
		if (CommonEventSerializationConstants.isReservedAttributeName(attribName)) {
			throw new EventRenderingException("attribute name must not be reserved");
		}
		
		if (!attributes.containsKey(attribName)) {
			attributes.put(attribName, new ArrayList<Object>());
		}
		attributes.get(attribName).add(value);
	}
	
	@Override
	public void removeAttribute(String attribName) {
		attributes.remove(attribName);
	}
	
}
