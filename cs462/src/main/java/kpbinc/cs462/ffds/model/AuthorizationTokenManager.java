package kpbinc.cs462.ffds.model;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.ServletContext;

import org.scribe.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope(value = "singleton")
public class AuthorizationTokenManager {

	private static final String AUTH_TOKEN_STORE_FILEPATH = "/WEB-INF/ffds/logs/authtokens.json";
	
	@Autowired
	private ServletContext servletContext;
	
	private Map<String, Map<String, Token>> authorizationTokensIndex = null;
	
	public AuthorizationTokenManager() {
		int tmpbrkpnt = 1;
	}
	
	private Map<String, Map<String, Token>> getIndex() {
		if (authorizationTokensIndex == null) {
			authorizationTokensIndex = new HashMap<String, Map<String, Token>>();
			// TODO read in persistent file
		}
		return authorizationTokensIndex;
	}
	
	public Token getAuthorizationToken(String username, String api) {
		Token authToken = null;
		
		Map<String, Token> apiAuthTokenIndex = getIndex().get(username);
		if (apiAuthTokenIndex != null) {
			authToken = apiAuthTokenIndex.get(api);
		}
		
		return authToken;
	}
	
	public boolean hasAuthorizationToken(String username, String api) {
		Token authToken = getAuthorizationToken(username, api);
		boolean result = (authToken != null);
		return result;
	}
	
	public Token createOrUpdateAuthorizationToken(String username, String api, Token authToken) {
		Map<String, Token> apiAuthTokenIndex = getIndex().get(username);
		if (apiAuthTokenIndex == null) {
			apiAuthTokenIndex = new HashMap<String, Token>();
			getIndex().put(username, apiAuthTokenIndex);
		}
		
		Token formerAuthToken = apiAuthTokenIndex.put(api, authToken);
		
		// TODO update persistent file
		
		return formerAuthToken;
	}
	
}
