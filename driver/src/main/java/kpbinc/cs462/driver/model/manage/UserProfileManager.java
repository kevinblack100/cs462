package kpbinc.cs462.driver.model.manage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

import kpbinc.cs462.driver.model.UserProfile;
import kpbinc.cs462.shared.model.manage.AuthorizationTokenManager;
import kpbinc.cs462.shared.model.manage.JsonFileStorePersistentMapStorageManager;
import kpbinc.cs462.shared.model.manage.OAuthServiceManager;
import kpbinc.io.util.JavaJsonAccess;
import kpbinc.io.util.JsonFileStore;
import kpbinc.io.util.JsonSerializer;
import kpbinc.util.PropertyAccessor;
import kpbinc.util.logging.GlobalLogUtils;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang3.StringUtils;
import org.scribe.exceptions.OAuthConnectionException;
import org.scribe.model.Token;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.type.TypeReference;

public class UserProfileManager 
	extends JsonFileStorePersistentMapStorageManager<String, UserProfile> {

	//= Class Data =====================================================================================================
	
	private static final Logger logger = Logger.getLogger(UserProfileManager.class.getName());
	
	
	//= Member Data ====================================================================================================
	
	@Autowired
	private AuthorizationTokenManager authorizationTokenManager;
	
	@Autowired
	private OAuthServiceManager oauthServiceManager;

	
	//= Initialization =================================================================================================
	
	//- Constructor ----------------------------------------------------------------------------------------------------
	
	/**
	 * @see JsonFileStorePersistentMapStorageManager
	 */
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
		PropertyAccessor<? super UserProfile, String> keyAccessor =
				new PropertyAccessor<UserProfile, String>() {

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
	
	
	//= Interface ======================================================================================================
	
	public void updateApiID(String username, String api) {
		// TODO move to a UserProfile service
		if (api.equals("foursquare")) {
			boolean hasFoursquareAuthToken = authorizationTokenManager.hasAuthorizationToken(username, api);
			
			if (hasFoursquareAuthToken) {
				String retrieveUserInformationURL = "https://api.foursquare.com/v2/users/self?oauth_token=";
				Token accessToken = authorizationTokenManager.getAuthorizationToken(username, api);
				retrieveUserInformationURL += accessToken.getToken();
				
				try {
					String userInformationJsonData =
							oauthServiceManager.callAPI(api, retrieveUserInformationURL, username);
					@SuppressWarnings("unchecked")
					Map<String, Object> userInfo = JsonSerializer.deserialize(userInformationJsonData, Map.class);
					Object foursquareID = JavaJsonAccess.getValue(userInfo, "response", "user", "id");
					if (foursquareID != null) {
						String foursquareIDAsString = foursquareID.toString();
						UserProfile profile = this.retrieve(username);
						if (profile == null) {
							profile = new UserProfile(username);
						}
						profile.addApiID(api, foursquareIDAsString);
						this.update(profile);
					}
					else {
						logger.warning("User information object doesn't contain an id property as expected");
					}
				}
				catch (OAuthConnectionException e) {
					logger.warning("OAuthConnectionException: " + e.getMessage());
					e.printStackTrace();
				}
				catch (IOException e) {
					logger.warning("IOException: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}
	
	public Collection<UserProfile> getByApiID(String api, String id) {
		Collection<UserProfile> result = new ArrayList<UserProfile>();
		for (UserProfile profile : this.retrieveAll()) {
			if (StringUtils.equals(id, profile.getApiIDs().get(api))) {
				result.add(profile);
			}
		}
		return result;
	}
	
	public UserProfile getByTextableNumber(String textableNumber) {
		UserProfile result = null;
		for (UserProfile profile : this.retrieveAll()) {
			if (StringUtils.equals(textableNumber, profile.getTextableNumber())) {
				result = profile;
				break;
			}
		}
		return result;
	}
	
}
