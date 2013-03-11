package kpbinc.net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Logger;

public class UTF8URLEncoder {

	//= Class Data =====================================================================================================
	
	private static final Logger logger = Logger.getLogger(UTF8URLEncoder.class.getName());

	private static final String CHARACTER_ENCODING = "UTF-8";

	
	//= Interface ======================================================================================================
	
	public static String encode(Object value) {
		assert(value != null);
		
		String encodedValue = null;
		
		try {
			encodedValue = URLEncoder.encode(value.toString(), CHARACTER_ENCODING);
		}
		catch (UnsupportedEncodingException e) {
			// Don't expect failure using UTF-8
			logger.warning("Unexpected: UnsupportedEncodingException while URL encoding with UTF-8: " + e.getMessage());
			e.printStackTrace();
		}
		
		return encodedValue;
	}

}
