package kpbinc.cs462.driver.model.manage;

import java.io.File;
import java.util.Map;

import kpbinc.cs462.driver.model.FlowerShopProfile;
import kpbinc.cs462.shared.model.aspect.IDGeneratingIDAccessor;
import kpbinc.cs462.shared.model.aspect.IDGenerator;
import kpbinc.cs462.shared.model.aspect.IncreasingLongIDGeneratorStrategy;
import kpbinc.cs462.shared.model.manage.JsonFileStorePersistentMapStorageManager;
import kpbinc.io.util.JsonFileStore;
import kpbinc.util.PropertyAccessor;
import kpbinc.util.logging.GlobalLogUtils;

import com.fasterxml.jackson.core.type.TypeReference;

public class FlowerShopProfileManager 
	extends JsonFileStorePersistentMapStorageManager<Long, FlowerShopProfile> {

	//= Initialization =================================================================================================
	
	//- Constructor ----------------------------------------------------------------------------------------------------
	
	/**
	 * @see JsonFileStorePersistentMapStorageManager
	 */
	public FlowerShopProfileManager(String fileStoreRelativePath) {
		super(fileStoreRelativePath);
		GlobalLogUtils.logConstruction(this);
	}
	
	//- Support --------------------------------------------------------------------------------------------------------
	
	@Override
	protected JsonFileStore<Map<Long, FlowerShopProfile>> getJsonFileStore(File file) {
		JsonFileStore<Map<Long, FlowerShopProfile>> jsonFileStore = 
				new JsonFileStore<Map<Long, FlowerShopProfile>>(file, new TypeReference<Map<Long, FlowerShopProfile>>() {});
		return jsonFileStore;
	}
	
	@Override
	protected PropertyAccessor<? super FlowerShopProfile, Long> initializeKeyAccessor() {
		PropertyAccessor<? super FlowerShopProfile, Long> keyAccessor =
				new IDGeneratingIDAccessor<Long>(new IDGenerator<Long>(new IncreasingLongIDGeneratorStrategy(this)));
		return keyAccessor;
	}
	
}
