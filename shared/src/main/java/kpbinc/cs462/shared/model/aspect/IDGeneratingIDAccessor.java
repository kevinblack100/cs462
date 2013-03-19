package kpbinc.cs462.shared.model.aspect;

import org.apache.commons.lang3.Validate;

import kpbinc.util.logging.GlobalLogUtils;

/**
 * Generates IDs for items with null IDs per some given IDGenerator instance.
 * 
 * @author Kevin Black
 */
public class IDGeneratingIDAccessor<V> extends IDAccessor<V> {

	//= Member Data ====================================================================================================
	
	private IDGenerator<V> idGenerator; 
	
	
	//= Initialization =================================================================================================
	
	public IDGeneratingIDAccessor(IDGenerator<V> idGenerator) {
		GlobalLogUtils.logConstruction(this);
		
		Validate.notNull(idGenerator, "ID Generator must not be null");
		this.idGenerator = idGenerator;
	}
	
	
	//= Interface ======================================================================================================
	
	/**
	 * If the object's ID is null then will generate and set an ID for the object per the ID generator instance.
	 */
	@Override
	public V getPropertyValue(HasId<V> object) {
		V currentID = super.getPropertyValue(object);
		if (currentID == null) {
			V newID = idGenerator.claimNextID();
			object.setId(newID);
			currentID = newID;
		}
		return currentID;
	}
	
}
