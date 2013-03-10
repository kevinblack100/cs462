package kpbinc.cs462.driver.model.manage;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletContext;

import kpbinc.cs462.driver.model.FlowerShopProfile;
import kpbinc.io.util.JsonFileStore;
import kpbinc.io.util.JsonFileStorePersistentMap;
import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.type.TypeReference;

public class FlowerShopProfileManager {

	//= Class Data =====================================================================================================
	
	//= Member Data ====================================================================================================
	
	@Autowired
	private ServletContext servletContext;
	
	private String fileStoreRelativePath;
	
	private JsonFileStorePersistentMap<Long, FlowerShopProfile> flowerShopProfiles = null;
	
	
	//= Initialization =================================================================================================
	
	/**
	 * @param fileStoreRelativePath
	 * @throws IllegalArgumentException if fileStoreRelativePath is null
	 */
	public FlowerShopProfileManager(String fileStoreRelativePath) {
		GlobalLogUtils.logConstruction(this);
		if (fileStoreRelativePath == null) {
			throw new IllegalArgumentException("file store relative path must not be null");
		}
		this.fileStoreRelativePath = fileStoreRelativePath;
	}
	
	
	//= Interface ======================================================================================================
	
	private JsonFileStorePersistentMap<Long, FlowerShopProfile> getFlowerStoreProfiles() {
		if (flowerShopProfiles == null) {
			String fullPath = servletContext.getRealPath(fileStoreRelativePath);
			File fileStoreFile = new File(fullPath);
			JsonFileStore<Map<Long, FlowerShopProfile>> fileStore = 
					new JsonFileStore<Map<Long, FlowerShopProfile>>(fileStoreFile, 
							new TypeReference<Map<Long, FlowerShopProfile>>() {});
			flowerShopProfiles = JsonFileStorePersistentMap.
					<Long, FlowerShopProfile>buildWithDelegateFromFileStore(fileStore);
		}
		return flowerShopProfiles;
	}
	
	public void register(Long id, FlowerShopProfile profile) {
		if (id == null) {
			throw new IllegalArgumentException("id must not be null");
		}
		if (profile == null) {
			throw new IllegalArgumentException("profile must not be null");
		}
		
		getFlowerStoreProfiles().put(id, profile);
	}
	
	public FlowerShopProfile get(Long id) {
		FlowerShopProfile profile = getFlowerStoreProfiles().get(id);
		return profile;
	}
	
	public Collection<FlowerShopProfile> getAll() {
		Collection<FlowerShopProfile> profiles = getFlowerStoreProfiles().values();
		return profiles;
	}
	
	// TODO update, unregister
	
}
