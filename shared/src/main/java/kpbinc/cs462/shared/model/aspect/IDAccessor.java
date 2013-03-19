package kpbinc.cs462.shared.model.aspect;

import org.apache.commons.lang3.Validate;

import kpbinc.util.PropertyAccessor;
import kpbinc.util.logging.GlobalLogUtils;

public class IDAccessor<V> implements PropertyAccessor<HasId<V>, V> {

	//= Initialization =================================================================================================
	
	public IDAccessor() {
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	@Override
	public String getPropertyName() {
		return "id";
	}

	@Override
	public V getPropertyValue(HasId<V> object) {
		Validate.notNull(object, "object must not be null");
		V id = object.getId();
		return id;
	}

	@Override
	public void setPropertyValue(HasId<V> object, V value) {
		Validate.notNull(object, "object must not be null");
		object.setId(value);
	}

}
