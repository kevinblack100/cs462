package kpbinc.cs462.shop.model.manage;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletContext;

import kpbinc.cs462.shop.model.DriverProfile;
import kpbinc.io.util.JsonFileStore;
import kpbinc.io.util.JsonFileStorePersistentMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;

@Repository
@Scope(value = "singleton")
public class DriverProfileManager {

	//==================================================================================================================
	// Class Data
	//==================================================================================================================
	
	private static final String DRIVER_PROFILES_STORE_FILEPATH = "/WEB-INF/ffds/stores/driverprofiles.json"; 
	
	
	//==================================================================================================================
	// Member Data
	//==================================================================================================================
	
	@Autowired
	private ServletContext servletContext;
	
	private JsonFileStorePersistentMap<String, DriverProfile> driverProfiles;
	
	
	//==================================================================================================================
	// Initialization
	//==================================================================================================================
	
	public DriverProfileManager() {
	}

	private JsonFileStorePersistentMap<String, DriverProfile> getDriverProfiles() {
		if (driverProfiles == null) {
			String fullFileStorePath = servletContext.getRealPath(DRIVER_PROFILES_STORE_FILEPATH);
			File fileStoreFile = new File(fullFileStorePath);
			JsonFileStore<Map<String, DriverProfile>> fileStore =
					new JsonFileStore<Map<String, DriverProfile>>(fileStoreFile,
							new TypeReference<Map<String, DriverProfile>>() {});
			driverProfiles = JsonFileStorePersistentMap.<String, DriverProfile>buildWithDelegateFromFileStore(fileStore);
		}
		return driverProfiles;
	}
	
	//==================================================================================================================
	// Interface
	//==================================================================================================================
	
	public DriverProfile createOrUpdate(DriverProfile profile) {
		DriverProfile formerProfile = getDriverProfiles().put(profile.getUsername(), profile);
		return formerProfile;
	}
	
	public Collection<DriverProfile> getAllProfiles() {
		Collection<DriverProfile> profiles = getDriverProfiles().values();
		return profiles;
	}
	
	public DriverProfile getProfileFor(String username) {
		DriverProfile profile = getDriverProfiles().get(username);
		return profile;
	}
	
	public DriverProfile deleteProfileFor(String username) {
		DriverProfile profile = getDriverProfiles().remove(username);
		return profile;
	}
	
}
