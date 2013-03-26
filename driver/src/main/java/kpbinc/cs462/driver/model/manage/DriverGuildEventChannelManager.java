package kpbinc.cs462.driver.model.manage;

import java.io.File;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;

import kpbinc.cs462.driver.model.DriverGuildEventChannel;
import kpbinc.cs462.shared.model.aspect.IDGeneratingIDAccessor;
import kpbinc.cs462.shared.model.aspect.IDGenerator;
import kpbinc.cs462.shared.model.aspect.IncreasingLongIDGeneratorStrategy;
import kpbinc.cs462.shared.model.manage.JsonFileStorePersistentMapStorageManager;
import kpbinc.io.util.JsonFileStore;
import kpbinc.util.PropertyAccessor;
import kpbinc.util.logging.GlobalLogUtils;

public class DriverGuildEventChannelManager
	extends JsonFileStorePersistentMapStorageManager<Long, DriverGuildEventChannel> {

	//= Initialization =================================================================================================
	
	//- Constructor ----------------------------------------------------------------------------------------------------
	
	public DriverGuildEventChannelManager(String fileStoreRelativePath) {
		super(fileStoreRelativePath);
		GlobalLogUtils.logConstruction(this);
	}

	//- Support --------------------------------------------------------------------------------------------------------
	
	@Override
	protected JsonFileStore<Map<Long, DriverGuildEventChannel>> getJsonFileStore(File file) {
		JsonFileStore<Map<Long, DriverGuildEventChannel>> jsonFileStore =
				new JsonFileStore<Map<Long, DriverGuildEventChannel>>(file,
						new TypeReference<Map<Long, DriverGuildEventChannel>>() {});
		return jsonFileStore;
	}

	@Override
	protected PropertyAccessor<? super DriverGuildEventChannel, Long> initializeKeyAccessor() {
		PropertyAccessor<? super DriverGuildEventChannel, Long> keyAccessor =
				new IDGeneratingIDAccessor<Long>(new IDGenerator<Long>(new IncreasingLongIDGeneratorStrategy(this)));
		return keyAccessor;
	}

	
	//= Interface ======================================================================================================
	
	public DriverGuildEventChannel retrieveByUsername(String username) {
		DriverGuildEventChannel result = null;
		for (DriverGuildEventChannel channel : retrieveAll()) {
			if (StringUtils.equalsIgnoreCase(channel.getLocalEntityId(), username)) {
				result = channel;
				break;
			}
		}
		return result;
	}
	
}
