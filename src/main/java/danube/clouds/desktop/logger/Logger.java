package danube.clouds.desktop.logger;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

import org.slf4j.LoggerFactory;

import danube.clouds.desktop.DanubeCloudsDesktopApplication;
import danube.clouds.desktop.events.LogEvent;

/**
 * Logger system for the web interface.
 * 
 * Every log entry has the following:
 * - A level (INFO, WARNING or PROBLEM)
 * - An arbitrary message
 * - A stacktrace (if the level is PROBLEM)
 * - An arbitrary type -optional-
 * - An application instance ID of the application instance where the event occurred -optional-
 *
 * Every log entry is saved in the Store and remains associated with the storeObject if one was
 * provided.
 * 
 * Every log entry also gets distributed as an event in the Events system, if an applicationInstanceId
 * was provided.
 */
public class Logger implements Serializable {

	private static final long serialVersionUID = 7234006165333916570L;

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(Logger.class.getName());

	public void info(String message, String type) {

		LogEntry logEntry = null;

		try {

			logEntry = new LogEntry();
			logEntry.setLevel("INFO");
			logEntry.setType(type);
			logEntry.setMessage(message);
		} catch (Exception ex) {

			log.error("Cannot save INFO log event: " + message + ": " + ex.getMessage(), ex);
			if (logEntry == null) return;
		}

		DanubeCloudsDesktopApplication.getApp().getEvents().fireLogEvent(new LogEvent(this, logEntry));

		log.info(message);
	}

	public void warning(String message, String type) {

		LogEntry logEntry = null;

		try {

			logEntry = new LogEntry();
			logEntry.setLevel("WARNING");
			logEntry.setType(type);
			logEntry.setMessage(message);
		} catch (Exception ex) {

			log.error("Cannot save WARNING log event: " + message + ": " + ex.getMessage(), ex);
			if (logEntry == null) return;
		}

		DanubeCloudsDesktopApplication.getApp().getEvents().fireLogEvent(new LogEvent(this, logEntry));

		log.warn(message);
	}

	public void problem(String message, String type, Throwable ex) {

		LogEntry logEntry = null;

		try {

			logEntry = new LogEntry();
			logEntry.setLevel("PROBLEM");
			logEntry.setType(type);
			logEntry.setMessage(message);

			if (ex != null) {

				StringWriter writer = new StringWriter();
				ex.printStackTrace(new PrintWriter(writer));
				logEntry.setStacktrace(writer.getBuffer().toString());
			}
		} catch (Exception ex2) {

			log.error("Cannot save PROBLEM log event: " + message + ": " + ex2.getMessage(), ex2);
			if (logEntry == null) return;
		}

		DanubeCloudsDesktopApplication.getApp().getEvents().fireLogEvent(new LogEvent(this, logEntry));

		if (ex != null) log.error(message, ex); else log.error(message);
	}
}
