package kpbinc.cs462.shared.event;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static kpbinc.cs462.shared.event.CommonEventSerializationConstants.*;
import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implements URL encoding as indicated in the Evented API specification: www.eventedapi.org/spec
 * 
 * @author Kevin Black
 */
@Component(value = "urlEncodeEventSerializer")
@Scope(value = "singleton")
public class URLEncodeEventSerializer implements EventSerializer {

	//= Class Data =====================================================================================================
	
	private static final Logger logger = Logger.getLogger(URLEncodeEventSerializer.class.getName());

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
		appendKeyValuePair(builder, DOMAIN_KEY, event.getDomain());
		appendKeyValuePair(builder, NAME_KEY, event.getName());
		for (Map.Entry<String, List<Object>> attribute : event.getAttributes().entrySet()) {
			for (Object value : attribute.getValue()) {
				String serializedValue = encode(value);
				appendKeyValuePair(builder, attribute.getKey(), serializedValue);
			}
		}
		
		String serializedEvent = builder.toString();
		return serializedEvent;
	}

	
	//= Support ========================================================================================================
	
	private static void appendKeyValuePair(StringBuilder builder, String key, String value) {
		if (0 < builder.length()) {
			builder.append(KEY_VALUE_PAIR_SEPARATOR);
		}
		builder.append(key).append(EQUALS).append(value);
	}
	
	private static String encode(Object value) {
		assert(value != null);
		
		String encodedValue = null;
		
		try {
			encodedValue = URLEncoder.encode(value.toString(), CHARACTER_ENCODING);
		}
		catch (UnsupportedEncodingException e) {
			logger.warning("Unsupported character encoding while URL encoding: " + e.getMessage());
			e.printStackTrace(); // TODO instead throw an EventSerializationException? Don't expect failure since using UTF-8
		}
		
		return encodedValue;
	}
	
}
