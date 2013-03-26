package kpbinc.cs462.shared.event;

import java.util.List;
import java.util.Map;

public interface Event {

	Event clone();
	
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
	
	/**
	 * Adds the attribute name-value pair to this event.
	 * 
	 * @param attribName name of the attribute
	 * @param value value of the attribute, or a value of the attribute if multi-valued
	 * 
	 * @throws EventRenderingException if attribName is reserved. Reserved event attributes are set through
	 * constructors or specialized methods.
	 */
	public void addAttribute(String attribName, Object value) throws EventRenderingException;
	
}