package kpbinc.util.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GlobalLogUtils {

	public static final Logger logger = Logger.getLogger(GlobalLogUtils.class.getName());
	
	public static void setLogLevel(Level level) {
		logger.setLevel(level);
	}
	
	public static void logConstruction(Object object) {
		if (object != null) {
			logger.finer("Constructed instance of " + object.getClass().getName());
		}
	}
	
}
