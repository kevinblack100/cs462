package kpbinc.cs462.driver.model.manage;

import java.io.File;
import java.util.Map;

import kpbinc.cs462.driver.model.StashedEvent;
import kpbinc.cs462.shared.model.aspect.IDGeneratingIDAccessor;
import kpbinc.cs462.shared.model.aspect.IDGenerator;
import kpbinc.cs462.shared.model.aspect.IncreasingLongIDGeneratorStrategy;
import kpbinc.cs462.shared.model.manage.JsonFileStorePersistentMapStorageManager;
import kpbinc.io.util.JsonFileStore;
import kpbinc.util.PropertyAccessor;
import kpbinc.util.logging.GlobalLogUtils;

import com.fasterxml.jackson.core.type.TypeReference;

public class StashedEventManager 
	extends JsonFileStorePersistentMapStorageManager<Long, StashedEvent> {
	
	//= Initialization =================================================================================================
	
	//- Constructor ----------------------------------------------------------------------------------------------------
	
	/**
	 * @see JsonFileStorePersistentMapStorageManager
	 */
	public StashedEventManager(String fileStoreRelativePath) {
		super(fileStoreRelativePath);
		GlobalLogUtils.logConstruction(this);
	}
	
	//- Support --------------------------------------------------------------------------------------------------------
	
	protected JsonFileStore<Map<Long, StashedEvent>> getJsonFileStore(File file) {
		JsonFileStore<Map<Long, StashedEvent>> jsonFileStore = 
				new JsonFileStore<Map<Long, StashedEvent>>(file, new TypeReference<Map<Long, StashedEvent>>() {});
		return jsonFileStore;
	}
	
	protected PropertyAccessor<? super StashedEvent, Long> initializeKeyAccessor() {
		PropertyAccessor<? super StashedEvent, Long> keyAccessor =
				new IDGeneratingIDAccessor<Long>(new IDGenerator<Long>(new IncreasingLongIDGeneratorStrategy(this)));
		return keyAccessor;
	}

}
