package kpbinc.util;

/**
 * Object wrapper.
 * 
 * @author Kevin Black
 *
 * @param <T> wrapped object type
 */
public class Wrapper<T> {

	private T wrappedObject;
	
	
	public Wrapper() {
		setWrappedObject(null);
	}
	
	public Wrapper(T objectToWrap) {
		setWrappedObject(objectToWrap);
	}
	
	
	@Override
	public boolean equals(Object object) {
		boolean result = false;
		if (   object != null
			&& object instanceof Wrapper) {
			try {
				Wrapper<?> other = (Wrapper<?>) object;
				if (this.wrappedObject == null) {
					result = (other.wrappedObject == null);
				}
				else {
					result = this.wrappedObject.equals(other.wrappedObject);
				}
			}
			catch (ClassCastException e) {
				// not equal
			}
		}
		return result;
	}
	
	
	public T getWrappedObject() {
		return wrappedObject;
	}
	
	public void setWrappedObject(T wrappedObject) {
		this.wrappedObject = wrappedObject;
	}
	
}
