package com.event.message.core;

/**
 * Created by Administrator on 2015/8/29.
 */
public class EventPostBuilder {

    private final MethodName mMethodName;

    private Object[] mParams;

    private int mDelay;

    private Object mTarget;


    private EventPostBuilder(MethodName methodName) {
        this.mMethodName = methodName;
    }

    public static EventPostBuilder createPost() {
        return new EventPostBuilder(MethodName.METHOD_TYPE_POST);
    }

    public static EventPostBuilder createAsyPost() {
        return new EventPostBuilder(MethodName.METHOD_TYPE_ASY);
    }

    public static EventPostBuilder createMainPost() {
        return new EventPostBuilder(MethodName.METHOD_TYPE_MAIN);
    }

    public static EventPostBuilder createBackgroundPost() {
        return new EventPostBuilder(MethodName.METHOD_TYPE_BACKGROUND);
    }

    public static EventPostBuilder createBuilder(String methodName) {
        return new EventPostBuilder(MethodName.create(methodName));
    }

    public EventPostBuilder params(Object... params) {
        mParams = params;
        return this;
    }

    public EventPostBuilder delay(int delay) {
        mDelay = delay;
        return this;
    }

    public EventPostBuilder target(Object target) {
        mTarget = target;
        return this;
    }

    void post() {
        SubscriberMethod subscriberMethod = SubscriberMethod.getSubscriberMethod(mMethodName, mParams);
        EventPublisher.handler(subscriberMethod, mTarget, mDelay, mParams);
    }
}
