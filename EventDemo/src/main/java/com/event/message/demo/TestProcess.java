package com.event.message.demo;

import com.event.message.core.EventMessage;
import com.event.message.core.IEventProcess;
import com.event.message.core.MethodName;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2015/8/29.
 */
public class TestProcess extends IEventProcess {

    private static final ExecutorService EXECUTORS = Executors.newCachedThreadPool();

    @Override
    protected void init() {

    }

    @Override
    protected MethodName getMethod() {
        return MethodName.create("helloTest");
    }

    @Override
    protected void enqueue(final EventMessage message) {
        EXECUTORS.execute(new Runnable() {
            @Override
            public void run() {
                message.invoke();
            }
        });
    }
}
