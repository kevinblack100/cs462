package kpbinc.cs462.shop.model.manage;

import java.io.File;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;

import kpbinc.cs462.shared.model.aspect.IDGeneratingIDAccessor;
import kpbinc.cs462.shared.model.aspect.IDGenerator;
import kpbinc.cs462.shared.model.aspect.IncreasingLongIDGeneratorStrategy;
import kpbinc.cs462.shared.model.manage.JsonFileStorePersistentMapStorageManager;
import kpbinc.cs462.shop.model.FlowerShopGuildEventChannel;
import kpbinc.io.util.JsonFileStore;
import kpbinc.util.PropertyAccessor;
import kpbinc.util.logging.GlobalLogUtils;

public class FlowerShopGuildEventChannelManager
		extends JsonFileStorePersistentMapStorageManager<Long, FlowerShopGuildEventChannel> {

	//= Class Data =====================================================================================================
	
	public static final Long DEFAULT_EVENT_CHANNEL_ID = 1L;
	
	
	//= Initialization =================================================================================================
	
	//- Constructor ----------------------------------------------------------------------------------------------------
	
	public FlowerShopGuildEventChannelManager(String fileStoreRelativePath) {
		super(fileStoreRelativePath);
		GlobalLogUtils.logConstruction(this);
	}
	
	//- Support --------------------------------------------------------------------------------------------------------
	
	@Override
	protected JsonFileStore<Map<Long, FlowerShopGuildEventChannel>> getJsonFileStore(File file) {
		JsonFileStore<Map<Long, FlowerShopGuildEventChannel>> jsonFileStore =
				new JsonFileStore<Map<Long, FlowerShopGuildEventChannel>>(file,
						new TypeReference<Map<Long, FlowerShopGuildEventChannel>> () {});
		return jsonFileStore;
	}

	@Override
	protected PropertyAccessor<? super FlowerShopGuildEventChannel, Long> initializeKeyAccessor() {
		PropertyAccessor<? super FlowerShopGuildEventChannel, Long> keyAccessor =
				new IDGeneratingIDAccessor<Long>(new IDGenerator<Long>(new IncreasingLongIDGeneratorStrategy(this)));
		return keyAccessor;
	}
	
}
