package kpbinc.cs462.guild.model.manage;

import java.io.File;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import com.fasterxml.jackson.core.type.TypeReference;

import kpbinc.cs462.guild.model.UserProfile;
import kpbinc.cs462.shared.model.manage.JsonFileStorePersistentMapStorageManager;
import kpbinc.io.util.JsonFileStore;
import kpbinc.util.PropertyAccessor;
import kpbinc.util.logging.GlobalLogUtils;

public class UserProfileManager 
	extends JsonFileStorePersistentMapStorageManager<String, UserProfile>{

	//= Initialization =================================================================================================
	
	//- Constructor ----------------------------------------------------------------------------------------------------
	
	public UserProfileManager(String fileStoreRelativePath) {
		super(fileStoreRelativePath);
		GlobalLogUtils.logConstruction(this);
	}

	//- Support --------------------------------------------------------------------------------------------------------
	
	@Override
	protected JsonFileStore<Map<String, UserProfile>> getJsonFileStore(File file) {
		JsonFileStore<Map<String, UserProfile>> jsonFileStore =
				new JsonFileStore<Map<String, UserProfile>>(file, new TypeReference<Map<String, UserProfile>>() {});
		return jsonFileStore;
	}

	@Override
	protected PropertyAccessor<? super UserProfile, String> initializeKeyAccessor() {
		PropertyAccessor<? super UserProfile, String> keyAccessor = new PropertyAccessor<UserProfile, String>() {

			@Override
			public String getPropertyName() {
				return "username";
			}

			@Override
			public String getPropertyValue(UserProfile object) {
				Validate.notNull(object, "object must not be null");
				String username = object.getUsername();
				return username;
			}

			@Override
			public void setPropertyValue(UserProfile object, String value) {
				Validate.notNull(object, "object must not be null");
				object.setUsername(value);						
			}
			
		};
		
		return keyAccessor;
	}
	
}
