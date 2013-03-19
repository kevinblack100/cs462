package kpbinc.cs462.shared.model.aspect;

/**
 * Interface-derived aspect that indicates an object has an ID property.
 * 
 * @author Kevin Black
 *
 * @param <I> ID type
 */
public interface HasId<I> {

	/**
	 * @return the ID of the object.
	 */
	I getId();
	
	/**
	 * @param id new ID value
	 * 
	 * @throws UnsupportedOperationException if setting the ID property is not allowed (at least via this interface).
	 */
	void setId(I id);
	
}
