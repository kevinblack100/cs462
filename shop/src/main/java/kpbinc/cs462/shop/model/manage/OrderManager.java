package kpbinc.cs462.shop.model.manage;

import java.io.File;
import java.util.Map;

import kpbinc.cs462.shared.model.aspect.IDAccessor;
import kpbinc.cs462.shared.model.aspect.IDGeneratingIDAccessor;
import kpbinc.cs462.shared.model.aspect.IDGenerator;
import kpbinc.cs462.shared.model.aspect.IncreasingLongIDGeneratorStrategy;
import kpbinc.cs462.shared.model.manage.JsonFileStorePersistentMapStorageManager;
import kpbinc.cs462.shop.model.Order;
import kpbinc.io.util.JsonFileStore;
import kpbinc.util.PropertyAccessor;
import kpbinc.util.logging.GlobalLogUtils;

import com.fasterxml.jackson.core.type.TypeReference;

public class OrderManager 
	extends JsonFileStorePersistentMapStorageManager<Long, Order> {	
	
	//= Member Data ====================================================================================================
	
//	private IDGenerator<Long> idGenerator;
	
	
	//= Initialization =================================================================================================
	
	//- Constructors ---------------------------------------------------------------------------------------------------
	
	/**
	 * @param fileStoreRelativePath path to the file store in which to persist the item map relative to the
	 * ServletContext
	 * 
	 * @throws NullPointerException if fileStoreRelativePath is null
	 */
	public OrderManager(String fileStoreRelativePath) {
		super(fileStoreRelativePath);
		GlobalLogUtils.logConstruction(this);
	}
	
	//- Support --------------------------------------------------------------------------------------------------------
	
	@Override
	protected JsonFileStore<Map<Long, Order>> getJsonFileStore(File file) {
		JsonFileStore<Map<Long, Order>> jsonFileStore = 
				new JsonFileStore<Map<Long, Order>>(file, new TypeReference<Map<Long, Order>>() {});
		return jsonFileStore;
	}
	
	@Override
	protected PropertyAccessor<? super Order, Long> initializeKeyAccessor() {
		PropertyAccessor<? super Order, Long> keyAccessor =
				new IDGeneratingIDAccessor<Long>(new IDGenerator<Long>(new IncreasingLongIDGeneratorStrategy(this)));
		return keyAccessor;
	}
	
	
	//= Interface ======================================================================================================

//	@Override
//	public boolean register(Order item) {
//		boolean result = super.register(item);
////		idGenerator.claimID(item.getId());
//		return result;
//	}
	
//	public Long getNextID() {
//		Long nextID = idGenerator.getNextID();
//		return nextID;
//	}

}
