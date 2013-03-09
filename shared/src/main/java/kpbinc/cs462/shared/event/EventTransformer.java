package kpbinc.cs462.shared.event;

import java.util.Map;
import java.util.logging.Logger;

import static kpbinc.cs462.shared.event.CommonEventSerializationConstants.*;
import kpbinc.util.logging.GlobalLogUtils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "singleton")
public class EventTransformer {

	//= Class Data =====================================================================================================
	
	private static final Logger logger = Logger.getLogger(EventTransformer.class.getName());
	
	
	//= Initialization =================================================================================================
	
	public EventTransformer() {
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	public Event transform(Map<String, String[]> nameMultiValuePairs) throws EventRenderingException {
		// TODO later require an EventDefinition which will contain more detailed information on transforming the event,
		// for now will just construct a BasicEventImpl event with String values.

		assert (nameMultiValuePairs != null);
		
		if (!nameMultiValuePairs.containsKey(DOMAIN_KEY)) {
			throw new EventRenderingException(String.format("domain attribute '%s' not specified", DOMAIN_KEY));
		}
		if (1 < nameMultiValuePairs.get(DOMAIN_KEY).length) {
			throw new EventRenderingException(String.format("domain attribute '%s' has more than one value", DOMAIN_KEY));
		}
		String domain = nameMultiValuePairs.get(DOMAIN_KEY)[0];
		
		if (!nameMultiValuePairs.containsKey(NAME_KEY)) {
			throw new EventRenderingException(String.format("name attribute '%s' not specified", NAME_KEY));
		}
		if (1 < nameMultiValuePairs.get(NAME_KEY).length) {
			throw new EventRenderingException(String.format("name attribute '%s' has more than one value", NAME_KEY));
		}
		String name = nameMultiValuePairs.get(NAME_KEY)[0];
		
		// construct event container
		BasicEventImpl event = new BasicEventImpl(domain, name);
		
		for (Map.Entry<String, String[]> attribute : nameMultiValuePairs.entrySet()) {
			String attribName = attribute.getKey();
			if (isReservedAttributeName(attribName)) {
				if (   !StringUtils.equals(attribName, DOMAIN_KEY)
					&& !StringUtils.equals(attribName, NAME_KEY)) {
					logger.warning(String.format("ignoring reserved attribute '%s'", attribName));
				}
			}
			else {
				if (ArrayUtils.isEmpty(attribute.getValue())) {
					throw new EventRenderingException(String.format("attribute '%s' has not values", attribName));
				}
				
				for (String value : attribute.getValue()) {
					event.addAttribute(attribName, value);
				}
			}
		}
		
		return event;
	}
	
}
