package kpbinc.cs462.shared.model.manage;

import java.io.File;
import java.util.Map;

import javax.servlet.ServletContext;

import kpbinc.cs462.shared.event.Event;
import kpbinc.io.util.JsonFileStore;
import kpbinc.io.util.JsonFileStorePersistentMap;
import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.type.TypeReference;

public class EventManager {
	
	//= Member Data ====================================================================================================
	
	@Autowired
	private ServletContext servletContext;
	
	private String fileStoreRelativePath;
	
	private JsonFileStorePersistentMap<Long, Event> eventIndex;
	
	private Long nextID;
	
	
	//= Initialization =================================================================================================
	
	/**
	 * @param fileStoreRelativePath
	 * @throws IllegalArgumentException if fileStoreRelativePath is null
	 */
	public EventManager(String fileStoreRelativePath) {
		GlobalLogUtils.logConstruction(this);
		if (fileStoreRelativePath == null) {
			throw new IllegalArgumentException("file store relative path must not be null");
		}
		this.fileStoreRelativePath = fileStoreRelativePath;
		
		this.eventIndex = null;
		
		this.nextID = null;
	}
	
	
	//= Interface ======================================================================================================
	
	private JsonFileStorePersistentMap<Long, Event> getIndex() {
		if (eventIndex == null) {
			String fullPath = servletContext.getRealPath(fileStoreRelativePath);
			File authTokenStoreFile = new File(fullPath);
			JsonFileStore<Map<Long, Event>> fileStore = 
					new JsonFileStore<Map<Long, Event>>(authTokenStoreFile, 
							new TypeReference<Map<Long, Event>>() {});
			eventIndex = JsonFileStorePersistentMap.<Long, Event>buildWithDelegateFromFileStore(fileStore);
			
			// find the max ID
			for (Long id : eventIndex.keySet()) {
				updateNextID(id);
			}
		}
		return eventIndex;
	}

	public void register(Long id, Event event) {
		getIndex().put(id, event);
		updateNextID(id);
	}
	
	public Event get(Long id) {
		Event event = getIndex().get(id);
		return event;
	}
	
	public Long getNextID() {
		return nextID;
	}
	
	
	//= Support ========================================================================================================
	
	private void updateNextID(Long candidateID) {
		if (   nextID == null
			|| nextID < candidateID) {
			nextID = new Long(candidateID.longValue() + 1l);
		}
	}
}
