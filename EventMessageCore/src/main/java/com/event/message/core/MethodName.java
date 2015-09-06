package com.event.message.core;

/**
 * Created by Administrator on 2015/8/29.
 */
public class MethodName {

    static final MethodName METHOD_TYPE_POST = new MethodName("onPostEvent");

    static final MethodName METHOD_TYPE_ASY = new MethodName("onAsyEvent");

    static final MethodName METHOD_TYPE_BACKGROUND = new MethodName("onBackgroundEvent");

    static final MethodName METHOD_TYPE_MAIN = new MethodName("onMainEvent");

    static final MethodName METHOD_TYPE_SEND_MESSAGE = new MethodName("handlerMessage");

    private final String mName;

    private MethodName(String name) {
        mName = name;
    }

    public static MethodName create(String name) {
        if (name == null) {
            throw new NullPointerException("method name is null");
        }
        if (name.equals(METHOD_TYPE_POST.mName) || name.equals(METHOD_TYPE_BACKGROUND.mName)
                || name.equals(METHOD_TYPE_ASY.mName) || name.equals(METHOD_TYPE_MAIN.mName)
                || name.equals(METHOD_TYPE_SEND_MESSAGE.mName)) {
            throw new IllegalStateException("method name can't " + name);
        }
        return new MethodName(name);
    }

    String getName() {
        return mName;
    }
}
