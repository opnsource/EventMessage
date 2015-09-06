package com.event.message.core;

import android.os.HandlerThread;

/**
 * Created by Administrator on 2015/8/28.
 */
class BackgroundProcess extends IEventProcess {

    @Override
    protected void init() {
        HandlerThread background = new HandlerThread(getClass().getSimpleName());
        background.start();
        mThreadHandler = new WeakHandler(DEFAULT_HANDLER, background.getLooper());
    }

    @Override
    protected MethodName getMethod() {
        return MethodName.METHOD_TYPE_BACKGROUND;
    }

    @Override
    protected void enqueue(EventMessage message) {
        sendMessage(message, mThreadHandler);
    }
}
