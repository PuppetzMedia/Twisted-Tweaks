package io.puppetzmedia.ttweaks;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;

@SuppressWarnings("unused")
public final class TTLogger {

	private static Logger logger;

	/* Make the constructor private to disable instantiation */
	private TTLogger() {
		throw new UnsupportedOperationException();
	}

	static void init(Logger logger) {

		if (TTLogger.logger == null) {
			TTLogger.logger = logger;
		} else {
			logger.warn("Trying to initialize mod logger more then once");
		}
	}

	@Contract(pure = true)
	public static Logger get() {
		return logger;
	}
	/*
	 * Short-hand methods to print logs to console.
	 */
	public static void info(String log) {
		logger.info(log);
	}
	public static void error(String log) {
		logger.error(log);
	}
	public static void error(String log, Object...args) {
		logger.printf(Level.ERROR, String.format(log, args));
	}
	public static void error(String log, Throwable t) {
		logger.error(log, t);
	}
	public static void warn(String log) {
		logger.warn(log);
	}
	public static void debug(String log) {
		logger.debug(log);
	}
	public static void debug(String format, Object...args) {
		logger.debug(String.format(format, args));
	}
	public static void debug(String log, Throwable t) {
		logger.debug(log, t);
	}
}
