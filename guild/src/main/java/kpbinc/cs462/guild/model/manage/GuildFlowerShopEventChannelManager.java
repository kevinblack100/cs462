package kpbinc.cs462.guild.model.manage;

import java.io.File;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;

import kpbinc.cs462.guild.model.GuildFlowerShopEventChannel;
import kpbinc.cs462.shared.model.aspect.IDGeneratingIDAccessor;
import kpbinc.cs462.shared.model.aspect.IDGenerator;
import kpbinc.cs462.shared.model.aspect.IncreasingLongIDGeneratorStrategy;
import kpbinc.cs462.shared.model.manage.JsonFileStorePersistentMapStorageManager;
import kpbinc.io.util.JsonFileStore;
import kpbinc.util.PropertyAccessor;
import kpbinc.util.logging.GlobalLogUtils;

public class GuildFlowerShopEventChannelManager extends
		JsonFileStorePersistentMapStorageManager<Long, GuildFlowerShopEventChannel> {

	//= Initialization =================================================================================================
	
	//- Constructor ----------------------------------------------------------------------------------------------------
	
	public GuildFlowerShopEventChannelManager(String fileStoreRelativePath) {
		super(fileStoreRelativePath);
		GlobalLogUtils.logConstruction(this);
	}
	
	//- Support --------------------------------------------------------------------------------------------------------
	
	@Override
	protected JsonFileStore<Map<Long, GuildFlowerShopEventChannel>> getJsonFileStore(File file) {
		JsonFileStore<Map<Long, GuildFlowerShopEventChannel>> jsonFileStore =
				new JsonFileStore<Map<Long, GuildFlowerShopEventChannel>>(file,
						new TypeReference<Map<Long, GuildFlowerShopEventChannel>>() {});
		return jsonFileStore;
	}

	@Override
	protected PropertyAccessor<? super GuildFlowerShopEventChannel, Long> initializeKeyAccessor() {
		PropertyAccessor<? super GuildFlowerShopEventChannel, Long> keyAccessor =
				new IDGeneratingIDAccessor<Long>(new IDGenerator<Long>(new IncreasingLongIDGeneratorStrategy(this)));
		return keyAccessor;
	}

}
