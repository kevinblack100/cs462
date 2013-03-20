package kpbinc.cs462.shop.model.manage;

import java.io.File;
import java.util.Map;

import kpbinc.cs462.shared.model.manage.JsonFileStorePersistentMapStorageManager;
import kpbinc.cs462.shop.model.DriverProfile;
import kpbinc.io.util.JsonFileStore;
import kpbinc.util.PropertyAccessor;
import kpbinc.util.logging.GlobalLogUtils;

import org.apache.commons.lang3.Validate;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;

@Repository
@Scope(value = "singleton")
public class DriverProfileManager 
	extends JsonFileStorePersistentMapStorageManager<String, DriverProfile> {	
	
	//= Initialization =================================================================================================
	
	//- Constructor ----------------------------------------------------------------------------------------------------
	
	/**
	 * @see JsonFileStorePersistentMapStorageManager
	 */
	public DriverProfileManager(String fileStoreRelativePath) {
		super(fileStoreRelativePath);
		GlobalLogUtils.logConstruction(this);
	}
	
	//- Support --------------------------------------------------------------------------------------------------------
	
	protected JsonFileStore<Map<String, DriverProfile>> getJsonFileStore(File file) {
		JsonFileStore<Map<String, DriverProfile>> jsonFileStore =
				new JsonFileStore<Map<String, DriverProfile>>(file, new TypeReference<Map<String, DriverProfile>>() {});
		return jsonFileStore;
	}
	
	protected PropertyAccessor<? super DriverProfile, String> initializeKeyAccessor() {
		PropertyAccessor<? super DriverProfile, String> keyAccessor =
				new PropertyAccessor<DriverProfile, String>() {

					@Override
					public String getPropertyName() {
						return "username";
					}

					@Override
					public String getPropertyValue(DriverProfile object) {
						Validate.notNull(object, "object must not be null");
						String username = object.getUsername();
						return username;
					}

					@Override
					public void setPropertyValue(DriverProfile object, String value) {
						Validate.notNull(object, "object must not be null");
						throw new UnsupportedOperationException("setPropertyValue not supported (yet)");
					}
			
				};
		return keyAccessor;
	}
	
}
