package com.event.message.core;

import android.os.Message;

/**
 * Created by Administrator on 2015/8/23.
 */

public abstract class IEventProcess {


    protected static final int POST = Integer.MIN_VALUE;
    protected final IHandlerListener DEFAULT_HANDLER = new IHandlerListener() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == POST) {
                Object obj = msg.obj;
                if (obj instanceof EventMessage) {
                    EventMessage message = (EventMessage) obj;
                    SubscriberMethod subscriberMethod = message.methodName;
                    Object target = message.target;
                    Object[] params = message.params;
                    subscriberMethod.invoke(target, params);
                }
            }
        }
    };
    WeakHandler mMainHandler;

    WeakHandler mThreadHandler;
    private IEventProcess mNextEventProcess;

    public IEventProcess() {
        init();
    }

    IEventProcess setNextEventProcess(IEventProcess nextEventProcess) {
        nextEventProcess.mNextEventProcess = this;
        return nextEventProcess;
    }

    void process(EventMessage message) {
        if (message != null && message.methodName != null) {
            if (message.methodName.checkMethod(getMethod())) {
                enqueue(message);
            } else {
                if (mNextEventProcess != null) {
                    mNextEventProcess.process(message);
                }
            }
        }
    }

    protected abstract void init();

    protected abstract MethodName getMethod();

    protected abstract void enqueue(EventMessage message);

    protected void sendMessage(EventMessage msg, WeakHandler handler) {
        Message message = new Message();
        message.what = POST;
        message.obj = msg;
        handler.sendMessageDelayed(message, msg.delay);
    }

}

