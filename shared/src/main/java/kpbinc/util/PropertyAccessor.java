package kpbinc.util;

/**
 * @author Kevin Black
 *
 * @param <E> entity with the property
 * @param <V> property value type
 */
public interface PropertyAccessor<E, V> {

	String getPropertyName();
	
	V getPropertyValue(E object);
	
	void setPropertyValue(E object, V value);
	
}
