package com.event.message.core;

import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

/**
 * Created by Administrator on 2015/8/28.
 */
class AsyProcess extends IEventProcess implements IHandlerListener {


    @Override
    protected void init() {
        mMainHandler = new WeakHandler(this, Looper.getMainLooper());
        HandlerThread asy = new HandlerThread(getClass().getSimpleName());
        asy.start();
        mThreadHandler = new WeakHandler(this, asy.getLooper());
    }

    @Override
    protected MethodName getMethod() {
        return MethodName.METHOD_TYPE_ASY;
    }

    @Override
    protected void enqueue(EventMessage message) {
        if (message.methodName.checkMethod(MethodName.METHOD_TYPE_MAIN)) {
            sendMessage(message, mMainHandler);
        } else {
            sendMessage(message, mThreadHandler);
        }
    }

    public void handleMessage(Message msg) {
        if (msg.what == POST) {
            Object obj = msg.obj;
            if (obj instanceof EventMessage) {
                EventMessage message = (EventMessage) obj;
                SubscriberMethod subscriberMethod = message.methodName;
                Object target = message.target;
                Object[] params = message.params;
                Object value = subscriberMethod.invoke(target, params);
                if (subscriberMethod.isInvokeSuccess(value) && Looper.myLooper() != Looper.getMainLooper()) {
                    done(value, target);
                }
            }
        }
    }

    public void done(Object value, Object target) {
        Object[] params = new Object[]{value};
        EventMessage message = new EventMessage.EventMessageBuilder(SubscriberMethod.getSubscriberMethod(MethodName.METHOD_TYPE_MAIN, params), target).params(params).builder();
        enqueue(message);
    }
}
