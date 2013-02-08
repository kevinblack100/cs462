package kpbinc.cs462.ffds.model;

import java.util.Map;
import java.util.HashMap;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope(value = "singleton")
public class AuthorizationTokenManager {

	@Autowired
	private ServletContext servletContext;
	
	private Map<String, Map<String, String>> authorizationTokensIndex = null;
	
	public AuthorizationTokenManager() {
		int tmpbrkpnt = 1;
	}
	
	private Map<String, Map<String, String>> getIndex() {
		if (authorizationTokensIndex == null) {
			authorizationTokensIndex = new HashMap<String, Map<String, String>>();
			// TODO read in persistent file
		}
		return authorizationTokensIndex;
	}
	
	public String getAuthorizationToken(String username, String api) {
		String authToken = null;
		
		Map<String, String> apiAuthTokenIndex = getIndex().get(username);
		if (apiAuthTokenIndex != null) {
			authToken = apiAuthTokenIndex.get(api);
		}
		
		return authToken;
	}
	
	public boolean hasAuthorizationToken(String username, String api) {
		String authToken = getAuthorizationToken(username, api);
		boolean result = (authToken != null);
		return result;
	}
	
	public String createOrUpdateAuthorizationToken(String username, String api, String authToken) {
		Map<String, String> apiAuthTokenIndex = getIndex().get(username);
		if (apiAuthTokenIndex == null) {
			apiAuthTokenIndex = new HashMap<String, String>();
			getIndex().put(username, apiAuthTokenIndex);
		}
		
		String formerAuthToken = apiAuthTokenIndex.put(api, authToken);
		
		// TODO update persistent file
		
		return formerAuthToken;
	}
	
}
