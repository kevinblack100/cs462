package kpbinc.cs462.shared.model.manage;

import java.io.File;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;

import kpbinc.cs462.shared.model.LoggedEvent;
import kpbinc.cs462.shared.model.aspect.IDGeneratingIDAccessor;
import kpbinc.cs462.shared.model.aspect.IDGenerator;
import kpbinc.cs462.shared.model.aspect.IncreasingLongIDGeneratorStrategy;
import kpbinc.io.util.JsonFileStore;
import kpbinc.util.PropertyAccessor;

public class LoggedEventManager extends
		JsonFileStorePersistentMapStorageManager<Long, LoggedEvent> {

	//= Initialization =================================================================================================
	
	//- Constructor ----------------------------------------------------------------------------------------------------
	
	public LoggedEventManager(String fileStoreRelativePath) {
		super(fileStoreRelativePath);
	}

	//- Support --------------------------------------------------------------------------------------------------------
	
	@Override
	protected JsonFileStore<Map<Long, LoggedEvent>> getJsonFileStore(File file) {
		JsonFileStore<Map<Long, LoggedEvent>> jsonFileStore =
				new JsonFileStore<Map<Long, LoggedEvent>>(file, new TypeReference<Map<Long, LoggedEvent>>() {});
		return jsonFileStore;
	}

	@Override
	protected PropertyAccessor<? super LoggedEvent, Long> initializeKeyAccessor() {
		PropertyAccessor<? super LoggedEvent, Long> keyAccessor =
				new IDGeneratingIDAccessor<Long>(new IDGenerator<Long>(new IncreasingLongIDGeneratorStrategy(this)));
		return keyAccessor;
	}

}
