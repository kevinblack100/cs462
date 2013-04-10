package kpbinc.cs462.shared.event;

import java.util.Arrays;
import java.util.Map;
import java.util.logging.Logger;

import static kpbinc.cs462.shared.event.CommonEventSerializationConstants.*;
import kpbinc.util.MapUtils;
import kpbinc.util.logging.GlobalLogUtils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "singleton")
public class EventTransformer {

	//= Class Data =====================================================================================================
	
	private static final Logger logger = Logger.getLogger(EventTransformer.class.getName());
	
	public static final String DEFAULT_DOMAIN_VALUE_WHEN_NOT_EXPLICIT = "unknown";
	public static final String DEFAULT_NAME_VALUE_WHEN_NOT_EXPLICIT = "unknown";
	
	
	//= Initialization =================================================================================================
	
	public EventTransformer() {
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	public Event transform(Map<String, String[]> nameMultiValuePairs) throws EventRenderingException {
		boolean domainAndNameMustBeExplicit = true;
		return transform(nameMultiValuePairs, domainAndNameMustBeExplicit);
	}
	
	public Event transform(
			Map<String, String[]> nameMultiValuePairs,
			boolean domainAndNameMustBeExplicit) throws EventRenderingException {
		// TODO later require an EventDefinition which will contain more detailed information on transforming the event,
		// for now will just construct a BasicEventImpl event with String values.

		Validate.notNull(nameMultiValuePairs, "nameMultiValuePairs must not be null");
		
		// Get Domain
		String domain = null;
		String domainKey = MapUtils.containsOneOf(nameMultiValuePairs,
				Arrays.asList(STANDARD_DOMAIN_KEY, NON_STANDARD_DOMAIN_KEY));
		if (domainKey == null) {
			if (domainAndNameMustBeExplicit) {
				throw new EventRenderingException(String.format("domain attribute not specified"));
			}
			else {
				domain = DEFAULT_DOMAIN_VALUE_WHEN_NOT_EXPLICIT;
			}
		}
		else {
			if (1 < nameMultiValuePairs.get(domainKey).length) {
				throw new EventRenderingException(String.format("domain attribute '%s' has more than one value", domainKey));
			}
			
			domain = nameMultiValuePairs.get(domainKey)[0];
		}
		
		// Get Name
		String name = null;
		String nameKey = MapUtils.containsOneOf(nameMultiValuePairs,
				Arrays.asList(STANDARD_NAME_KEY, NON_STANDARD_NAME_KEY));
		if (nameKey == null) {
			if (domainAndNameMustBeExplicit) {
				throw new EventRenderingException(String.format("name attribute not specified"));
			}
			else {
				name = DEFAULT_NAME_VALUE_WHEN_NOT_EXPLICIT;
			}
		}
		else {
			if (1 < nameMultiValuePairs.get(nameKey).length) {
				throw new EventRenderingException(String.format("name attribute '%s' has more than one value", nameKey));
			}
			
			name = nameMultiValuePairs.get(nameKey)[0];
		}
		
		// construct event container
		BasicEventImpl event = new BasicEventImpl(domain, name);
		
		for (Map.Entry<String, String[]> attribute : nameMultiValuePairs.entrySet()) {
			String attribName = attribute.getKey();
			if (isReservedAttributeName(attribName)) {
				if (   !StringUtils.equals(attribName, domainKey)
					&& !StringUtils.equals(attribName, nameKey)) {
					logger.warning(String.format("ignoring reserved attribute '%s'", attribName));
				}
			}
			else {
				if (ArrayUtils.isEmpty(attribute.getValue())) {
					throw new EventRenderingException(String.format("attribute '%s' has no values", attribName));
				}
				
				for (String value : attribute.getValue()) {
					event.addAttribute(attribName, value);
				}
			}
		}
		
		return event;
	}
	
}
