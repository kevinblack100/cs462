package kpbinc.cs462.shared.event;

import java.util.List;
import java.util.Map;

public interface Event {

	String getDomain();
	
	String getName();
	
	/**
	 * @return a readable attributes map; does not have to be writable.
	 */
	Map<String, List<Object>> getAttributes();
	
}