package com.sternkn.testtasks.myexcel;

import java.io.IOException;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;



public class Config
{
	public static Logger LOG = Logger.getLogger(Config.class);
	
    public static void initLogs()
    {
        Logger rootLogger = Logger.getRootLogger();
        
        if (!rootLogger.getAllAppenders().hasMoreElements()) 
        {
            rootLogger.addAppender(new ConsoleAppender(new PatternLayout("%5p [%t] (%F:%L) - %m%n")));
            try 
            {
/*            	
            	RollingFileAppender logsAppender;
            	logsAppender = new RollingFileAppender(new PatternLayout("%d{dd/MM/yyyy HH:mm:ss } :  %m%n"), "logs.txt", true);
                logsAppender.setName("logs_appender");
                logsAppender.setMaximumFileSize(5242880); // 5MB
                logsAppender.setMaxBackupIndex(3);
	
                LevelMatchFilter noDebug = new LevelMatchFilter();
                noDebug.setLevelToMatch("DEBUG");
                noDebug.setAcceptOnMatch(false);
                
                LevelMatchFilter noWarn = new LevelMatchFilter();
                noWarn.setLevelToMatch("WARN");
                noWarn.setAcceptOnMatch(false);
                
                logsAppender.clearFilters();
                logsAppender.addFilter(noDebug);
                logsAppender.addFilter(noWarn);

                rootLogger.addAppender(logsAppender);
*/                
	
                RollingFileAppender debugLogsAppender;
                debugLogsAppender = new RollingFileAppender(new PatternLayout("%d{dd/MM/yyyy HH:mm:ss,SSS } (%F:%L) %p [%t]:  %m%n"), "debug_logs.txt", true);
                debugLogsAppender.setName("debug_logs_appender");
                debugLogsAppender.setMaximumFileSize(5242880); // 5MB
                debugLogsAppender.setMaxBackupIndex(5);
                rootLogger.addAppender(debugLogsAppender);

            } catch (IOException e) {
            	LOG.warn("IOException", e);
            }
            
        }
    }
}
