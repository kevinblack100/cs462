package kpbinc.cs462.shop.model.manage;

import java.io.File;
import java.util.Map;

import kpbinc.cs462.shared.model.manage.JsonFileStorePersistentMapStorageManager;
import kpbinc.cs462.shop.model.Order;
import kpbinc.io.util.JsonFileStore;
import kpbinc.util.PropertyAccessor;
import kpbinc.util.logging.GlobalLogUtils;

import org.apache.commons.lang3.Validate;

import com.fasterxml.jackson.core.type.TypeReference;

public class OrderManager 
	extends JsonFileStorePersistentMapStorageManager<Long, Order> {
	
	//= Class Data =====================================================================================================
	
	private static final Long seedID = 1L;
	
	
	//= Member Data ====================================================================================================
	
	private Long nextID;
	
	
	//= Initialization =================================================================================================
	
	//- Constructors ---------------------------------------------------------------------------------------------------
	
	/**
	 * @param fileStoreRelativePath path to the file store in which to persist the item map relative to the
	 * ServletContext
	 * 
	 * @throws NullPointerException if fileStoreRelativePath is null
	 */
	public OrderManager(String fileStoreRelativePath) {
		super(fileStoreRelativePath, new PropertyAccessor<Order, Long>() {

			@Override
			public String getPropertyName() {
				return "id";
			}

			@Override
			public Long getPropertyValue(Order object) {
				Validate.notNull(object, "object must not be null");
				Long id = object.getId();
				return id;
			}

			@Override
			public void setPropertyValue(Order object, Long value) {
				Validate.notNull(object, "object must not be null");
				object.setId(value);
			}
			
		});
		GlobalLogUtils.logConstruction(this);
		
		this.nextID = null;
	}
	
	//- Support --------------------------------------------------------------------------------------------------------
	
	protected JsonFileStore<Map<Long, Order>> getJsonFileStore(File file) {
		JsonFileStore<Map<Long, Order>> jsonFileStore = 
				new JsonFileStore<Map<Long, Order>>(file, new TypeReference<Map<Long, Order>>() {});
		return jsonFileStore;
	}
	
	
	//= Interface ======================================================================================================

	@Override
	public boolean register(Order item) {
		boolean result = super.register(item);
		updateNextID(item.getId());
		return result;
	}
	
	public Long getNextID() {
		if (nextID == null) {
			// find the max ID
			for (Long id : getItemMap().keySet()) {
				updateNextID(id);
			}
			
			if (nextID == null) {
				nextID = seedID;
			}
		}
		return nextID;
	}
	
	
	//= Support ========================================================================================================
	
	private void updateNextID(Long candidateID) {
		if (   nextID == null
			|| nextID <= candidateID) {
			nextID = new Long(candidateID.longValue() + 1L);
		}
	}

}
