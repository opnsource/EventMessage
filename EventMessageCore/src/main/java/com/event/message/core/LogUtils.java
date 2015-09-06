package com.event.message.core;

import static com.event.message.core.EventLog.LOG_TYPE;

/**
 * Created by Administrator on 2015/8/23.
 */
public class LogUtils {

    private static final EventLog EVENT_LOG = new EventLog();


    public static void init(EventLog.LOG_TYPE maxType, String defaultTag) {
        EVENT_LOG.setMaxType(maxType);
        EVENT_LOG.setDefaulLogTag(defaultTag);
    }


    public static void write(LOG_TYPE type, String logCat, String detail) {
        EVENT_LOG.write(type, logCat, detail);
    }

    public static void write(LOG_TYPE type, String logCat, Throwable e) {
        EVENT_LOG.write(type, logCat, e);
    }

    public static void write(String logcat, String detail) {
        EVENT_LOG.write(logcat, detail);
    }


    public static void write(String logcat, Throwable e) {
        EVENT_LOG.write(logcat, e);
    }

    public static void write(String detail) {
        EVENT_LOG.write(detail);
    }

    public static void write(Throwable e) {
        EVENT_LOG.write(e);
    }
}
