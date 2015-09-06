package com.event.message.core;

/**
 * Created by Administrator on 2015/8/29.
 */
class MainProcess extends IEventProcess {
    @Override
    protected void init() {
        mMainHandler = new WeakHandler(DEFAULT_HANDLER);
    }

    @Override
    protected MethodName getMethod() {
        return MethodName.METHOD_TYPE_MAIN;
    }

    @Override
    protected void enqueue(EventMessage message) {
        sendMessage(message, mMainHandler);
    }
}
