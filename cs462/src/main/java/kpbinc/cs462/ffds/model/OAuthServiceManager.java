package kpbinc.cs462.ffds.model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Foursquare2Api;
import org.scribe.oauth.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
@Scope(value = "singleton")
public class OAuthServiceManager {

	private static final String API_APP_CONFIG_FILEPATH = "/WEB-INF/ffds/config/apiclientconfig.json";
	
	@SuppressWarnings("unused")
	private static class APIClientConfiguration {
		
		private String key;
		private String secret;
		private String callbackBase;
		
		public APIClientConfiguration() {
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getSecret() {
			return secret;
		}

		public void setSecret(String secret) {
			this.secret = secret;
		}

		public String getCallbackBase() {
			return callbackBase;
		}

		public void setCallbackBase(String callbackBase) {
			this.callbackBase = callbackBase;
		}
		
	}
	
	@Autowired
	private ServletContext servletContext;
	
	private Map<String, APIClientConfiguration> apiClientConfigurations;
	
	private Map<String, Map<String, OAuthService>> apiOAuthServices = new HashMap<String, Map<String, OAuthService>>();
	
	public OAuthServiceManager() {
		int tmpbrkpnt = 1;
	}
	
	public OAuthService getOAuthService(String api, String username) {
		Map<String, OAuthService> usernameToService = apiOAuthServices.get(api);
		
		if (usernameToService == null) {
			usernameToService = new HashMap<String, OAuthService>();
			apiOAuthServices.put(api, usernameToService);
		}
		
		OAuthService service = usernameToService.get(username);
		if (service == null) {
			APIClientConfiguration config = getAPIClientConfigurations().get(api);
			if (config != null) {
				if ("foursquare".equals(api)) {
				
					String apiKey = config.getKey();
					String apiSecret = config.getSecret();
					String callbackURI = config.getCallbackBase() + "?api=" + api + "&username=" + username;
					service = new ServiceBuilder()
									.provider(Foursquare2Api.class)
									.apiKey(apiKey)
									.apiSecret(apiSecret)
									.callback(callbackURI)
									.build();
					usernameToService.put(username, service);
				}
				// else don't know/handle that api
			}
			// else don't have a configuration
		}
		
		return service;
	}
	
	private Map<String, APIClientConfiguration> getAPIClientConfigurations() {
		if (apiClientConfigurations == null) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				String fullPath = servletContext.getRealPath(API_APP_CONFIG_FILEPATH);
				File apiClientConfigFile = new File(fullPath);
				apiClientConfigurations = mapper.readValue(apiClientConfigFile, new TypeReference<Map<String, APIClientConfiguration>>(){});
			}
			catch (IOException e) {
				e.printStackTrace();
				apiClientConfigurations = new HashMap<String, APIClientConfiguration>();
			}
		}
		return apiClientConfigurations;
	}
	
}
