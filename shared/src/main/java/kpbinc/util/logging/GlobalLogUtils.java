package kpbinc.util.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GlobalLogUtils {

	//= Global Constants ===============================================================================================
	
	public static final Logger logger = Logger.getLogger(GlobalLogUtils.class.getName());
	
	public static final boolean DO_PRINT_STACKTRACE = true;
	public static final boolean DO_NOT_PRINT_STACKTRACE = false;
	
	
	//= Interface ======================================================================================================
	
	public static void setLogLevel(Level level) {
		logger.setLevel(level);
	}
	
	public static void logConstruction(Object object) {
		if (object != null) {
			logger.finer("Constructed instance of " + object.getClass().getName());
		}
	}
	
	public static void infoHandledException(
			String handledMessage,
			Exception exception,
			boolean printStackTrace) {
		logHandledException(logger, Level.INFO, handledMessage, exception, printStackTrace);
	}
	
	public static void infoHandledException(
			Logger logger,
			String handledMessage,
			Exception exception,
			boolean printStackTrace) {
		logHandledException(logger, Level.INFO, handledMessage, exception, printStackTrace);
	}
	
	public static void warningHandledException(
			String handledMessage,
			Exception exception,
			boolean printStackTrace) {
		logHandledException(logger, Level.WARNING, handledMessage, exception, printStackTrace);
	}
	
	public static void warningHandledException(
			Logger logger,
			String handledMessage,
			Exception exception,
			boolean printStackTrace) {
		logHandledException(logger, Level.WARNING, handledMessage, exception, printStackTrace);
	}
	
	public static void logHandledException(
			Logger logger,
			Level level,
			String handledMessage,
			Exception exception,
			boolean printStackTrace) {
		String message = String.format("%s. Handled %s: %s. Stack trace %s.",
							handledMessage,
							exception.getClass().getName(),
							exception.getMessage(),
							(printStackTrace ? "follows" : "elided"));
		
		logger.log(level, message);

		if (printStackTrace) {
			exception.printStackTrace();
		}
	}
	
	public static String formatHandledExceptionMessage(
			String handledMessage,
			Exception exception,
			boolean printStackTrace) {
		String message = String.format("%s.\n Handled %s: %s.\n Stack trace %s.",
							handledMessage,
							exception.getClass().getName(),
							exception.getMessage(),
							(printStackTrace ? "follows" : "elided"));
		return message;
	}
}
