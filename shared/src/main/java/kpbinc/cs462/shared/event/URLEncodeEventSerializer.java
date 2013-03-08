package kpbinc.cs462.shared.event;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.logging.Logger;

import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implements URL encoding as indicated in the Evented API specification: www.eventedapi.org/spec
 * 
 * @author Kevin Black
 */
@Component
@Scope(value = "singleton")
public class URLEncodeEventSerializer implements EventSerializer {

	//= Class Data =====================================================================================================
	
	private static final Logger logger = Logger.getLogger(URLEncodeEventSerializer.class.getName());
	
	private static final String RESERVED_DOMAIN_KEY = "_domain";
	private static final String RESERVED_NAME_KEY = "_name";
	
	private static final String EQUALS = "=";
	private static final String KEY_VALUE_PAIR_SEPARATOR = "&";
	
	private static final String CHARACTER_ENCODING = "UTF-8";
	
	
	//= Initialization =================================================================================================
	
	public URLEncodeEventSerializer() {
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	/**
	 * @param event Event to be serialized
	 * 
	 * @throws IllegalArgumentException if event is null
	 */
	@Override
	public String serialize(Event event) {
		if (event == null) {
			throw new IllegalArgumentException("event must not be null");
		}
		
		StringBuilder builder = new StringBuilder();
		builder.append(RESERVED_DOMAIN_KEY).append(EQUALS).append(event.getDomain());
		builder.append(KEY_VALUE_PAIR_SEPARATOR).append(RESERVED_NAME_KEY).append(EQUALS).append(event.getName());
		for (Map.Entry<String, Object> attribute : event.getAttributes().entrySet()) {
			String serializedValue = encode(attribute.getValue());
			builder.append(KEY_VALUE_PAIR_SEPARATOR).append(attribute.getKey()).append(EQUALS).append(serializedValue);
		}
		
		String serializedEvent = builder.toString();
		return serializedEvent;
	}

	
	//= Support ========================================================================================================
	
	private String encode(Object value) {
		assert(value != null);
		
		String encodedValue = null;
		
		try {
			encodedValue = URLEncoder.encode(value.toString(), CHARACTER_ENCODING);
		}
		catch (UnsupportedEncodingException e) {
			logger.warning("Unsupported character encoding while URL encoding: " + e.getMessage());
			e.printStackTrace(); // TODO instead throw an EventSerializationException?
		}
		
		return encodedValue;
	}
	
}
