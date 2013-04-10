package kpbinc.cs462.taja.model.manage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;

import kpbinc.cs462.shared.model.aspect.IDGeneratingIDAccessor;
import kpbinc.cs462.shared.model.aspect.IDGenerator;
import kpbinc.cs462.shared.model.aspect.IncreasingLongIDGeneratorStrategy;
import kpbinc.cs462.shared.model.manage.JsonFileStorePersistentMapStorageManager;
import kpbinc.cs462.taja.model.WordCountTaskResults;
import kpbinc.io.util.JsonFileStore;
import kpbinc.util.PropertyAccessor;
import kpbinc.util.logging.GlobalLogUtils;

public class WordCountTaskResultsManager
	extends JsonFileStorePersistentMapStorageManager<Long, WordCountTaskResults> {

	//= Initialization =================================================================================================
	
	//- Constructor ----------------------------------------------------------------------------------------------------
	
	public WordCountTaskResultsManager(String fileStoreRelativePath) {
		super(fileStoreRelativePath);
		GlobalLogUtils.logConstruction(this);
	}
	
	//- Support --------------------------------------------------------------------------------------------------------
	
	@Override
	protected JsonFileStore<Map<Long, WordCountTaskResults>> getJsonFileStore(File file) {
		JsonFileStore<Map<Long, WordCountTaskResults>> jsonFileStore =
				new JsonFileStore<Map<Long, WordCountTaskResults>>(file,
						new TypeReference<Map<Long, WordCountTaskResults>>() {});
		return jsonFileStore;
	}

	@Override
	protected PropertyAccessor<? super WordCountTaskResults, Long> initializeKeyAccessor() {
		PropertyAccessor<? super WordCountTaskResults, Long> keyAccessor =
				new IDGeneratingIDAccessor<Long>(new IDGenerator<Long>(new IncreasingLongIDGeneratorStrategy(this)));
		return keyAccessor;
	}

	
	//= Interface ======================================================================================================
	
	public Collection<WordCountTaskResults> retrieveByJobId(String jobId) {
		Collection<WordCountTaskResults> result = new ArrayList<WordCountTaskResults>();
		for (WordCountTaskResults task : this.retrieveAll()) {
			if (StringUtils.equals(task.getJobId(), jobId)) {
				result.add(task);
			}
		}
		return result;
	}
	
	public WordCountTaskResults retrieveByTaskId(String taskId) {
		WordCountTaskResults result = null;
		for (WordCountTaskResults task : this.retrieveAll()) {
			if (StringUtils.equals(task.getTaskId(), taskId)) {
				result = task;
				break;
			}
		}
		return result;
	}
	
}
