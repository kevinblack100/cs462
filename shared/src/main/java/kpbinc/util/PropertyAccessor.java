package kpbinc.util;

/**
 * @author Kevin Black
 *
 * @param <E> entity with the property
 * @param <V> property value type
 */
public interface PropertyAccessor<E, V> {

	/**
	 * @return non-blank string containing the property name
	 */
	String getPropertyName();
	
	/**
	 * @param object object whose property is to be accessed
	 * @return the value of the object's property
	 * 
	 * @throws NullPointerException if the object is null
	 */
	V getPropertyValue(E object);
	
	/**
	 * @param object object whose property is to be accessed
	 * @param value new value for the object's property
	 * 
	 * @throws NullPointerException if the object is null
	 */
	void setPropertyValue(E object, V value);
	
}
