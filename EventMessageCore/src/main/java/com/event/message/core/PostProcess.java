package com.event.message.core;

import android.os.HandlerThread;
import android.os.Looper;

/**
 * Created by Administrator on 2015/8/23.
 */
class PostProcess extends IEventProcess {

    @Override
    protected void init() {
        mMainHandler = new WeakHandler(DEFAULT_HANDLER);
        HandlerThread handlerThread = new HandlerThread(getClass().getSimpleName());
        handlerThread.start();
        mThreadHandler = new WeakHandler(DEFAULT_HANDLER, handlerThread.getLooper());
    }

    @Override
    protected MethodName getMethod() {
        return MethodName.METHOD_TYPE_POST;
    }

    @Override
    protected void enqueue(EventMessage message) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            sendMessage(message, mMainHandler);
        } else {
            sendMessage(message, mThreadHandler);
        }
    }
}
