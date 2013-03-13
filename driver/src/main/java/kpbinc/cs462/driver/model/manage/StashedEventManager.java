package kpbinc.cs462.driver.model.manage;

import java.io.File;
import java.util.Map;

import javax.servlet.ServletContext;

import kpbinc.cs462.driver.model.StashedEvent;
import kpbinc.io.util.JsonFileStore;
import kpbinc.io.util.JsonFileStorePersistentMap;
import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.type.TypeReference;

public class StashedEventManager {

	//= Class Data =====================================================================================================
	
	private static final Long seedID = 1L;

	
	//= Member Data ====================================================================================================
	
	@Autowired
	private ServletContext servletContext;
	
	private String fileStoreRelativePath;
	
	private JsonFileStorePersistentMap<Long, StashedEvent> eventIndex;
	
	private Long nextID;
	
	
	//= Initialization =================================================================================================
	
	/**
	 * @param fileStoreRelativePath
	 * @throws IllegalArgumentException if fileStoreRelativePath is null
	 */
	public StashedEventManager(String fileStoreRelativePath) {
		GlobalLogUtils.logConstruction(this);
		if (fileStoreRelativePath == null) {
			throw new IllegalArgumentException("file store relative path must not be null");
		}
		this.fileStoreRelativePath = fileStoreRelativePath;
		
		this.eventIndex = null;
		
		this.nextID = null;
	}
	
	
	//= Interface ======================================================================================================
	
	private JsonFileStorePersistentMap<Long, StashedEvent> getIndex() {
		if (eventIndex == null) {
			String fullPath = servletContext.getRealPath(fileStoreRelativePath);
			File authTokenStoreFile = new File(fullPath);
			JsonFileStore<Map<Long, StashedEvent>> fileStore = 
					new JsonFileStore<Map<Long, StashedEvent>>(authTokenStoreFile, 
							new TypeReference<Map<Long, StashedEvent>>() {});
			eventIndex = JsonFileStorePersistentMap.<Long, StashedEvent>buildWithDelegateFromFileStore(fileStore);
			
			
		}
		return eventIndex;
	}

	public void register(Long id, StashedEvent event) {
		getIndex().put(id, event);
		updateNextID(id);
	}
	
	public StashedEvent get(Long id) {
		StashedEvent event = getIndex().get(id);
		return event;
	}
	
	public Long getNextID() {
		if (nextID == null) {
			// find the max ID
			for (Long id : getIndex().keySet()) {
				updateNextID(id);
			}
			
			if (nextID == null) {
				nextID = seedID;
			}
		}
		return nextID;
	}
	
	
	//= Support ========================================================================================================
	
	private void updateNextID(Long candidateID) {
		if (   nextID == null
			|| nextID <= candidateID) {
			nextID = new Long(candidateID.longValue() + 1L);
		}
	}

}
