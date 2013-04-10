package kpbinc.cs462.taja.model.manage;

import java.io.File;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;

import kpbinc.cs462.shared.model.aspect.IDGeneratingIDAccessor;
import kpbinc.cs462.shared.model.aspect.IDGenerator;
import kpbinc.cs462.shared.model.aspect.IncreasingLongIDGeneratorStrategy;
import kpbinc.cs462.shared.model.manage.JsonFileStorePersistentMapStorageManager;
import kpbinc.cs462.taja.model.WordCountJobResults;
import kpbinc.io.util.JsonFileStore;
import kpbinc.util.PropertyAccessor;
import kpbinc.util.logging.GlobalLogUtils;

public class WordCountJobResultsManager
	extends JsonFileStorePersistentMapStorageManager<Long, WordCountJobResults> {

	//= Initialization =================================================================================================
	
	//- Constructor ----------------------------------------------------------------------------------------------------
	
	public WordCountJobResultsManager(String fileStoreRelativePath) {
		super(fileStoreRelativePath);
		GlobalLogUtils.logConstruction(this);
	}

	//- Support --------------------------------------------------------------------------------------------------------
	
	@Override
	protected JsonFileStore<Map<Long, WordCountJobResults>> getJsonFileStore(File file) {
		JsonFileStore<Map<Long, WordCountJobResults>> jsonFileStore =
				new JsonFileStore<Map<Long, WordCountJobResults>>(file, 
						new TypeReference<Map<Long, WordCountJobResults>>() {});
		return jsonFileStore;
	}

	@Override
	protected PropertyAccessor<? super WordCountJobResults, Long> initializeKeyAccessor() {
		PropertyAccessor<? super WordCountJobResults, Long> keyAccessor =
				new IDGeneratingIDAccessor<Long>(new IDGenerator<Long>(new IncreasingLongIDGeneratorStrategy(this)));
		return keyAccessor;
	}
	
	
	//= Interface ======================================================================================================
	
	public WordCountJobResults retrieveByJobId(String jobId) {
		WordCountJobResults result = null;
		for (WordCountJobResults job : retrieveAll()) {
			if (StringUtils.equals(job.getJobId(), jobId)) {
				result = job;
				break;
			}
		}
		return result;
	}

}
