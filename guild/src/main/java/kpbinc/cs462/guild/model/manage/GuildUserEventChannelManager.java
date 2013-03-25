package kpbinc.cs462.guild.model.manage;

import java.io.File;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;

import kpbinc.cs462.guild.model.GuildUserEventChannel;
import kpbinc.cs462.shared.model.aspect.IDGeneratingIDAccessor;
import kpbinc.cs462.shared.model.aspect.IDGenerator;
import kpbinc.cs462.shared.model.aspect.IncreasingLongIDGeneratorStrategy;
import kpbinc.cs462.shared.model.manage.JsonFileStorePersistentMapStorageManager;
import kpbinc.io.util.JsonFileStore;
import kpbinc.util.PropertyAccessor;
import kpbinc.util.logging.GlobalLogUtils;

public class GuildUserEventChannelManager
		extends JsonFileStorePersistentMapStorageManager<Long, GuildUserEventChannel> {

	//= Initialization =================================================================================================
	
	//- Constructor ----------------------------------------------------------------------------------------------------
	
	public GuildUserEventChannelManager(String fileStoreRelativePath) {
		super(fileStoreRelativePath);
		GlobalLogUtils.logConstruction(this);
	}

	//- Support --------------------------------------------------------------------------------------------------------
	
	@Override
	protected JsonFileStore<Map<Long, GuildUserEventChannel>> getJsonFileStore(File file) {
		JsonFileStore<Map<Long, GuildUserEventChannel>> jsonFileStore =
				new JsonFileStore<Map<Long, GuildUserEventChannel>>(file,
						new TypeReference<Map<Long, GuildUserEventChannel>>() {});
		return jsonFileStore;
	}

	@Override
	protected PropertyAccessor<? super GuildUserEventChannel, Long> initializeKeyAccessor() {
		PropertyAccessor<? super GuildUserEventChannel, Long> keyAccessor =
				new IDGeneratingIDAccessor<Long>(new IDGenerator<Long>(new IncreasingLongIDGeneratorStrategy(this)));
		return keyAccessor;
	}

	
	//= Interface ======================================================================================================
	
	public GuildUserEventChannel retrieveByUsername(String username) {
		GuildUserEventChannel result = null;
		for (GuildUserEventChannel channel : retrieveAll()) {
			if (StringUtils.equalsIgnoreCase(username, channel.getRemoteEntityId())) {
				result = channel;
				break;
			}
		}
		return result;
	}
	
}
