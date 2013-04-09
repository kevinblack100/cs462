package kpbinc.cs462.shared.event;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = BasicEventImpl.class)
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

	/**
	 * Removes the attribute name-value pair from this event if it exists. Removes all values if attribute is
	 * multi-valued. Does not remove reserved attributes.
	 * 
	 * @param attribName name of the attribute
	 */
	public void removeAttribute(String attribName);
	
}