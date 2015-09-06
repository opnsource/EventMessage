package com.event.message.core;

import android.util.Log;

/**
 * Created by Administrator on 2015/8/23.
 */
class EventLog {


    private static final LOG_TYPE DEFAULT_TYPE = LOG_TYPE.LOG_TYPE_DEBUG;
    private static String EVENT_LOG = EventLog.class.getSimpleName();
    private EventLog.LOG_TYPE mMaxType = EventLog.LOG_TYPE.LOG_TYPE_DEBUG;


    public void setMaxType(EventLog.LOG_TYPE maxType) {
        mMaxType = maxType;
    }

    public void setDefaulLogTag(String tag) {
        EVENT_LOG = tag;
    }

    public void write(LOG_TYPE type, String logCat, String detail) {
        if (!isLog(type, logCat)) {
            return;
        }
        switch (type) {
            case LOG_TYPE_VERBOSE:
                Log.v(logCat, detail);
                break;
            case LOG_TYPE_DEBUG:
                Log.d(logCat, detail);
                break;
            case LOG_TYPE_INFO:
                Log.i(logCat, detail);
                break;
            case LOG_TYPE_WARN:
                Log.w(logCat, detail);
                break;
            case LOG_TYPE_ERROR:
                Log.e(logCat, detail);
                break;
        }

    }

    public void write(LOG_TYPE type, String logCat, Throwable e) {
        write(type, logCat, getMessage(e));
    }

    public void write(String logcat, String detail) {
        write(DEFAULT_TYPE, logcat, detail);
    }


    public void write(String logcat, Throwable e) {
        write(logcat, getMessage(e));
    }

    public void write(String detail) {
        write(DEFAULT_TYPE, EVENT_LOG, detail);
    }

    public void write(Throwable e) {
        write(getMessage(e));
    }

    private String getMessage(Throwable e) {
        if (e != null) {
            return "" + Log.getStackTraceString(e);
        }
        return "";
    }

    private boolean isLog(LOG_TYPE type, String tag) {
        return type.ordinal() > mMaxType.ordinal() && Log.isLoggable(tag, type.getType());
    }

    enum LOG_TYPE {
        LOG_TYPE_VERBOSE(Log.VERBOSE), LOG_TYPE_DEBUG(Log.DEBUG), LOG_TYPE_INFO(Log.INFO),
        LOG_TYPE_WARN(Log.WARN), LOG_TYPE_ERROR(Log.ERROR);

        private int mType;

        LOG_TYPE(int type) {
            mType = type;
        }

        private int getType() {
            return mType;
        }
    }
}
