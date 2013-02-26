package kpbinc.cs462.ffds.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Logger;

import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request")
public class EventGenerator {

	// ~ Class Data ====================================================================================================

	private static final Logger logger = Logger.getLogger(EventGenerator.class.getName());

	
	// ~ Initialization ================================================================================================

	public EventGenerator() {
		GlobalLogUtils.logConstruction(this);
	}

	// ~ Interface =====================================================================================================

	/**
	 * @param eventSignalURL event signal URL
	 * @param eventDetails URL encoded query string with event details 
	 * @return true if the event was sent successfully, false otherwise
	 */
	public boolean sendEvent(String eventSignalURL, String eventDetails) {
		// based on examples at http://www.xyzws.com/Javafaq/how-to-use-httpurlconnection-post-data-to-web-server/139

		assert (eventSignalURL != null);
		assert (eventDetails != null);

		boolean wasSuccessful = false;

		URL url = null;
		HttpURLConnection connection = null;
		try {
			// Create connection
			url = new URL(eventSignalURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			connection.setRequestProperty("Content-Length",	Integer.toString(eventDetails.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(eventDetails);
			wr.flush();
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			logger.info("Response was:\n" + response.toString());
			wasSuccessful = true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		
		return wasSuccessful;
	}

}
