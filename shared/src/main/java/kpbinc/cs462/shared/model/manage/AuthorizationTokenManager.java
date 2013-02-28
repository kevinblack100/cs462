package kpbinc.cs462.shared.model.manage;

import java.io.File;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.ServletContext;

import kpbinc.io.util.JsonFileStore;
import kpbinc.io.util.JsonFileStorePersistentMap;
import kpbinc.util.logging.GlobalLogUtils;

import org.scribe.model.Token;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;

public class AuthorizationTokenManager {

	//= Class Data =====================================================================================================
	
	public static class TokenWrapper {
		
		// usually delegate to this
		private Token wrappedToken;
		
		// store these when reading in the file and then will construct a token with them
		private String token;
		private String secret;
		
		public TokenWrapper() {
		}
		
		public TokenWrapper(Token tokenToWrap) {
			this.wrappedToken = tokenToWrap;
		}
		
		@JsonIgnore
		public Token getWrappedToken() {
			if (   wrappedToken == null
				&& token != null
				&& secret != null) {
				// reading back in
				wrappedToken = new Token(token, secret);
			}
			return wrappedToken;
		}
		
		public String getToken() {
			String result = token;
			if (wrappedToken != null) {
				result = wrappedToken.getToken();
			}
			return result;
		}
		
		public void setToken(String token) {
			this.token = token;
		}
		
		public String getSecret() {
			String result = secret;
			if (wrappedToken != null) {
				result = wrappedToken.getSecret();
			}
			return result;
		}
		
		public void setSecret(String secret) {
			this.secret = secret;
		}
	}
	
	//= Member Data ====================================================================================================
	
	@Autowired
	private ServletContext servletContext;
	
	private String fileStoreRelativePath;
	
	private JsonFileStorePersistentMap<String, Map<String, TokenWrapper>> authorizationTokensIndex = null;
	
	
	//= Initialization =================================================================================================
	
	/**
	 * @param fileStoreRelativePath
	 * @throws IllegalArgumentException if fileStoreRelativePath is null
	 */
	public AuthorizationTokenManager(String fileStoreRelativePath) {
		GlobalLogUtils.logConstruction(this);
		if (fileStoreRelativePath == null) {
			throw new IllegalArgumentException("file store relative path must not be null");
		}
		this.fileStoreRelativePath = fileStoreRelativePath;
	}
	
	
	//= Interface ======================================================================================================
	
	private JsonFileStorePersistentMap<String, Map<String, TokenWrapper>> getIndex() {
		if (authorizationTokensIndex == null) {
			String fullPath = servletContext.getRealPath(fileStoreRelativePath);
			File authTokenStoreFile = new File(fullPath);
			JsonFileStore<Map<String, Map<String, TokenWrapper>>> fileStore = 
					new JsonFileStore<Map<String, Map<String, TokenWrapper>>>(authTokenStoreFile, 
							new TypeReference<Map<String, Map<String, TokenWrapper>>>() {});
			authorizationTokensIndex = JsonFileStorePersistentMap.
					<String, Map<String,TokenWrapper>>buildWithDelegateFromFileStore(fileStore);
		}
		return authorizationTokensIndex;
	}
	
	public Token getAuthorizationToken(String username, String api) {
		Token authToken = null;
		
		Map<String, TokenWrapper> apiAuthTokenIndex = getIndex().get(username);
		if (apiAuthTokenIndex != null) {
			authToken = apiAuthTokenIndex.get(api).getWrappedToken();
		}
		
		return authToken;
	}
	
	public boolean hasAuthorizationToken(String username, String api) {
		Token authToken = getAuthorizationToken(username, api);
		boolean result = (authToken != null);
		return result;
	}
	
	public Token createOrUpdateAuthorizationToken(String username, String api, Token authToken) {
		Map<String, TokenWrapper> apiAuthTokenIndex = getIndex().get(username);
		if (apiAuthTokenIndex == null) {
			apiAuthTokenIndex = new HashMap<String, TokenWrapper>();
			getIndex().put(username, apiAuthTokenIndex);
		}
		
		TokenWrapper newWrappedToken = new TokenWrapper(authToken);
		TokenWrapper formerWrappedToken = apiAuthTokenIndex.put(api, newWrappedToken);
		Token formerAuthToken = (formerWrappedToken != null ? formerWrappedToken.getWrappedToken() : null);
		
		getIndex().commit();

		return formerAuthToken;
	}
	
}
