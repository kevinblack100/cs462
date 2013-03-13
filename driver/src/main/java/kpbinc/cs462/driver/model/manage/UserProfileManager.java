package kpbinc.cs462.driver.model.manage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletContext;

import kpbinc.cs462.driver.model.UserProfile;
import kpbinc.cs462.shared.model.manage.AuthorizationTokenManager;
import kpbinc.cs462.shared.model.manage.OAuthServiceManager;
import kpbinc.io.util.JavaJsonAccess;
import kpbinc.io.util.JsonFileStore;
import kpbinc.io.util.JsonFileStorePersistentMap;
import kpbinc.io.util.JsonSerializer;
import kpbinc.util.logging.GlobalLogUtils;

import org.apache.commons.lang3.StringUtils;
import org.scribe.exceptions.OAuthConnectionException;
import org.scribe.model.Token;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.type.TypeReference;

public class UserProfileManager {

	//= Class Data =====================================================================================================
	
	private static final Logger logger = Logger.getLogger(UserProfileManager.class.getName());
	
	
	//= Member Data ====================================================================================================
	
	@Autowired
	private ServletContext servletContext;
	
	private String fileStoreRelativePath;
	
	@Autowired
	private AuthorizationTokenManager authorizationTokenManager;
	
	@Autowired
	private OAuthServiceManager oauthServiceManager;
	
	private JsonFileStorePersistentMap<String, UserProfile> userProfiles = null;
	
	
	//= Initialization =================================================================================================
	
	/**
	 * @param fileStoreRelativePath
	 * @throws IllegalArgumentException if fileStoreRelativePath is null
	 */
	public UserProfileManager(String fileStoreRelativePath) {
		GlobalLogUtils.logConstruction(this);
		if (fileStoreRelativePath == null) {
			throw new IllegalArgumentException("file store relative path must not be null");
		}
		this.fileStoreRelativePath = fileStoreRelativePath;
	}
	
	
	//= Interface ======================================================================================================
	
	private JsonFileStorePersistentMap<String, UserProfile> getUserProfiles() {
		if (userProfiles == null) {
			String fullPath = servletContext.getRealPath(fileStoreRelativePath);
			File fileStoreFile = new File(fullPath);
			JsonFileStore<Map<String, UserProfile>> fileStore = 
					new JsonFileStore<Map<String, UserProfile>>(fileStoreFile, 
							new TypeReference<Map<String, UserProfile>>() {});
			userProfiles = JsonFileStorePersistentMap.
					<String, UserProfile>buildWithDelegateFromFileStore(fileStore);
		}
		return userProfiles;
	}
	
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
						UserProfile profile = this.get(username);
						if (profile == null) {
							profile = new UserProfile(username);
						}
						profile.addApiID(api, foursquareIDAsString);
						this.update(username, profile);
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
	
	public void register(String id, UserProfile profile) {
		registerOrUpdate(id, profile);
	}
	
	public UserProfile get(String id) {
		UserProfile profile = getUserProfiles().get(id);
		return profile;
	}
	
	public Collection<UserProfile> getAll() {
		Collection<UserProfile> profiles = getUserProfiles().values();
		return profiles;
	}
	
	public Collection<UserProfile> getByApiID(String api, String id) {
		Collection<UserProfile> result = new ArrayList<UserProfile>();
		for (UserProfile profile : this.getAll()) {
			if (StringUtils.equals(id, profile.getApiIDs().get(api))) {
				result.add(profile);
			}
		}
		return result;
	}
	
	public UserProfile getByTextableNumber(String textableNumber) {
		UserProfile result = null;
		for (UserProfile profile : this.getAll()) {
			if (StringUtils.equals(textableNumber, profile.getTextableNumber())) {
				result = profile;
				break;
			}
		}
		return result;
	}
	
	public UserProfile update(String id, UserProfile profile) {
		UserProfile previous = registerOrUpdate(id, profile);
		return previous;
	}
	
	// TODO unregister

	
	//= Support ========================================================================================================
	
	private UserProfile registerOrUpdate(String id, UserProfile profile) {
		if (id == null) {
			throw new IllegalArgumentException("id must not be null");
		}
		if (profile == null) {
			throw new IllegalArgumentException("profile must not be null");
		}
		
		UserProfile previous = getUserProfiles().put(id, profile);
		
		return previous;
	}
	
}
