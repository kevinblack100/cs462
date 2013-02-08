package kpbinc.cs462.ffds.model;

import java.util.HashMap;
import java.util.Map;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Foursquare2Api;
import org.scribe.oauth.OAuthService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope(value = "singleton")
public class OAuthServiceManager {

	private Map<String, OAuthService> apiOAuthServices = new HashMap<String, OAuthService>();
	
	public OAuthServiceManager() {
		int tmpbrkpnt = 1;
	}
	
	public OAuthService getOAuthService(String api) {
		OAuthService service = apiOAuthServices.get(api);
		
		if (service == null) {
			if ("foursquare".equals(api)) {
				String apiKey = "HBRKOKDL5BRHA3A5KDKNKKODADRI1EDEDMI2JNIH5U23MES2";
				String apiSecret = "EPR5PCGICL5GPYJOMGA31BAF1E01MC0V0R1KE0FRZX5U05XW";
				String callbackURI = "http://requestb.in/1ai0qxl1"; // request.getLocalName() + "/cs462/ffds/oauth/v2/requesttoken/" + api + "/" + username;
				service = new ServiceBuilder()
								.provider(Foursquare2Api.class)
								.apiKey(apiKey)
								.apiSecret(apiSecret)
								.callback(callbackURI)
								.build();
				apiOAuthServices.put(api, service);
			}
			// else don't know/handle that api
		}
		
		return service;
	}
	
}
