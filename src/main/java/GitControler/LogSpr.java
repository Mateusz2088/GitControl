package GitControler;

import org.apache.log4j.*;

import java.io.IOException;

public class LogSpr {
    private final String logDirectory;
    public Logger logger = null;

    public LogSpr(String logDirectory){
        this.logDirectory = logDirectory;
        Layout layout = new HTMLLayout();
        try{
            Appender appender = new FileAppender(layout, logDirectory+"/aplication.log");
            BasicConfigurator.configure(appender);
            logger = Logger.getRootLogger();
            logger.debug("Logger działa");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
