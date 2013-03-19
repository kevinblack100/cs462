package kpbinc.cs462.shop.model.manage;

import java.io.File;
import java.util.Map;

import kpbinc.cs462.shared.model.aspect.IDAccessor;
import kpbinc.cs462.shared.model.manage.JsonFileStorePersistentMapStorageManager;
import kpbinc.cs462.shop.model.Order;
import kpbinc.io.util.JsonFileStore;
import kpbinc.util.logging.GlobalLogUtils;

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
		super(fileStoreRelativePath, new IDAccessor<Long>());
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
