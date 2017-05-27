package com.zwquick.utils;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Created by Aman on 2017/5/23.
 */

public class RunTimeLog {
    public static final int MODE_DEBUG = 1;
    public static final int MODE_RELEASE = 2;
    public static final int ERROR = 3;
    public static final int WARNING = 4;
    public static final int INFO = 5;
    public static final int DEBUG = 6;
    public static final int VERBOSE = 7;

    static int Mode = MODE_RELEASE;
    static String logfileNmae = "/sdcard/YourAppName.log";
    static Logger logger;
    static LogRecord logRecord;

    static {
        try{
            FileHandler fh = new FileHandler(logfileNmae , true);
            fh.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    TimeZone z = TimeZone.getTimeZone("GTM+8");
                    GregorianCalendar g = new GregorianCalendar(z);
                    StringBuffer buf = new StringBuffer();

                    buf.append(g.get(Calendar.YEAR));
                    buf.append("/");
                    buf.append(g.get(Calendar.MONTH)+1);
                    buf.append("/");
                    buf.append(g.get(Calendar.DAY_OF_MONTH));
                    buf.append("  ");
                    buf.append(g.get(Calendar.HOUR_OF_DAY));
                    buf.append(":");
                    buf.append(g.get(Calendar.MINUTE));
                    buf.append(":");
                    buf.append(g.get(Calendar.SECOND));
                    return buf.toString();
                }
            });
            logger = logger.getLogger(logfileNmae);
            logger.addHandler(fh);
        }catch (IOException $e){
            $e.printStackTrace();
        }
    }

    public static void log(int $logLevel , String $msg){
        if(Mode==MODE_RELEASE && $logLevel>=DEBUG){
            return;
        }
        logRecord = new LogRecord(Level.ALL , $msg);
        logRecord.setLoggerName(logfileNmae);
        try{
            Level l = null;
            switch ($logLevel){
                case ERROR:
                    l = Level.SEVERE;
                    break;
                case WARNING:
                    l = Level.WARNING;
                    break;
                case INFO:
                case DEBUG:
                case VERBOSE:
                    l = Level.INFO;
                    break;
            }
            if(l!=null){
                logRecord.setLevel(l);
                logger.log(logRecord);
            }
        }catch(Exception $e){
            $e.printStackTrace();
        }

    }


}
