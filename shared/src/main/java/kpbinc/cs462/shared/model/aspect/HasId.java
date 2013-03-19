package kpbinc.cs462.shared.model.aspect;

/**
 * Interface-derived aspect that indicates an object has an ID.
 * 
 * @author Kevin Black
 *
 * @param <I> ID type
 */
public interface HasId<I> {

	I getId();
	
}
