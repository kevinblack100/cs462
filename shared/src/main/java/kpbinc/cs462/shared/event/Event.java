package kpbinc.cs462.shared.event;

import java.util.List;
import java.util.Map;

public interface Event {

	String getDomain();
	
	String getName();
	
	/**
	 * @param attribName
	 * @return the first value of attribute with the given name, or null if there is no attribute with that name.
	 */
	Object getAttribute(String attribName);
	
	/**
	 * @return a readable attributes map; does not have to be writable.
	 */
	Map<String, List<Object>> getAttributes();
	
	// TODO define addAttribute method
	
}