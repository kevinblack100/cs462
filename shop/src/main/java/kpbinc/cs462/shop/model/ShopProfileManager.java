package kpbinc.cs462.shop.model;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;

import kpbinc.io.util.JsonFileStore;
import kpbinc.util.logging.GlobalLogUtils;

@Repository
@Scope(value = "singleton")
public class ShopProfileManager {

	//~ Class Data =====================================================================================================
	
	private static final String SHOP_PROFILE_STORE_FILEPATH = "/WEB-INF/ffds/stores/shopprofile.json";
	
	
	//~ Member Data ====================================================================================================
	
	@Autowired
	private ServletContext servletContext;
	
	private JsonFileStore<ShopProfile> fileStore;
	
	private ShopProfile profile;
	
	
	//~ Initialization =================================================================================================
	
	public ShopProfileManager() {
		GlobalLogUtils.logConstruction(this);
		
	}
	
	@PostConstruct
	private void initializeProfile() {
		this.profile = getFileStore().read();
		if (this.profile == null) {
			this.profile = new ShopProfile();
		}
	}

	private JsonFileStore<ShopProfile> getFileStore() {
		if (fileStore == null) {
			String fullFileStoreFilepath = servletContext.getRealPath(SHOP_PROFILE_STORE_FILEPATH);
			File fileStoreFile = new File(fullFileStoreFilepath);
			fileStore = new JsonFileStore<ShopProfile>(fileStoreFile, new TypeReference<ShopProfile>() {});
		}
		return fileStore;
	}
	
	
	//~ Interface ======================================================================================================
	
	public ShopProfile getProfile() {
		return profile;
	}
	
	public void updateProfile(ShopProfile profile) {
		assert(profile != null);
		this.profile = profile;
		getFileStore().commit(this.profile);
	}
	
}
