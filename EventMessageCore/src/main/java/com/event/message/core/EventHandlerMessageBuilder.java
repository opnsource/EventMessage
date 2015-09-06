package com.event.message.core;

import android.os.Message;

/**
 * Created by Administrator on 2015/8/29.
 */
public class EventHandlerMessageBuilder {

    private final MethodName mMethodName = MethodName.METHOD_TYPE_SEND_MESSAGE;

    private int what;

    private int arg1;

    private int arg2;

    private int mDelay;

    private Object obj;

    private Object mTarget;

    public EventHandlerMessageBuilder what(int what) {
        this.what = what;
        return this;
    }

    public EventHandlerMessageBuilder obj(Object obj) {
        this.obj = obj;
        return this;
    }

    public EventHandlerMessageBuilder args(int arg1, int arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
        return this;
    }

    public EventHandlerMessageBuilder target(Object target) {
        mTarget = target;
        return this;
    }

    public EventHandlerMessageBuilder delay(int delay) {
        mDelay = delay;
        return this;
    }


    void sendMessage() {
        Message msg = Message.obtain();
        msg.what = what;
        msg.arg1 = arg1;
        msg.arg2 = arg2;
        msg.obj = obj;
        Object[] params = new Object[]{msg};
        SubscriberMethod subscriberMethod = SubscriberMethod.getSubscriberMethod(mMethodName, params);
        EventPublisher.handler(subscriberMethod, mTarget, mDelay, params);
    }
}
