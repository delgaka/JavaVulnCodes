import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
...
// No special action needed because security actions are 
// performed at the logging policy level
Logger logger = LogManager.getLogger(MyClass.class);
logger.info(logMessage);
...
