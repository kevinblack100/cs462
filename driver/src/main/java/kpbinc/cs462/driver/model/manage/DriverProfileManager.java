package kpbinc.cs462.driver.model.manage;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletContext;

import kpbinc.cs462.driver.model.DriverProfile;
import kpbinc.io.util.JsonFileStore;
import kpbinc.io.util.JsonFileStorePersistentMap;
import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.type.TypeReference;

public class DriverProfileManager {

	//= Class Data =====================================================================================================
	
	//= Member Data ====================================================================================================
	
	@Autowired
	private ServletContext servletContext;
	
	private String fileStoreRelativePath;
	
	private JsonFileStorePersistentMap<String, DriverProfile> driverProfiles = null;
	
	
	//= Initialization =================================================================================================
	
	/**
	 * @param fileStoreRelativePath
	 * @throws IllegalArgumentException if fileStoreRelativePath is null
	 */
	public DriverProfileManager(String fileStoreRelativePath) {
		GlobalLogUtils.logConstruction(this);
		if (fileStoreRelativePath == null) {
			throw new IllegalArgumentException("file store relative path must not be null");
		}
		this.fileStoreRelativePath = fileStoreRelativePath;
	}
	
	
	//= Interface ======================================================================================================
	
	private JsonFileStorePersistentMap<String, DriverProfile> getDriverProfiles() {
		if (driverProfiles == null) {
			String fullPath = servletContext.getRealPath(fileStoreRelativePath);
			File fileStoreFile = new File(fullPath);
			JsonFileStore<Map<String, DriverProfile>> fileStore = 
					new JsonFileStore<Map<String, DriverProfile>>(fileStoreFile, 
							new TypeReference<Map<String, DriverProfile>>() {});
			driverProfiles = JsonFileStorePersistentMap.
					<String, DriverProfile>buildWithDelegateFromFileStore(fileStore);
		}
		return driverProfiles;
	}
	
	public void register(String id, DriverProfile profile) {
		registerOrUpdate(id, profile);
	}
	
	public DriverProfile get(String id) {
		DriverProfile profile = getDriverProfiles().get(id);
		return profile;
	}
	
	public Collection<DriverProfile> getAll() {
		Collection<DriverProfile> profiles = getDriverProfiles().values();
		return profiles;
	}
	
	public DriverProfile update(String id, DriverProfile profile) {
		DriverProfile previous = registerOrUpdate(id, profile);
		return previous;
	}
	
	// TODO unregister

	
	//= Support ========================================================================================================
	
	private DriverProfile registerOrUpdate(String id, DriverProfile profile) {
		if (id == null) {
			throw new IllegalArgumentException("id must not be null");
		}
		if (profile == null) {
			throw new IllegalArgumentException("profile must not be null");
		}
		
		DriverProfile previous = getDriverProfiles().put(id, profile);
		
		return previous;
	}
	
}
