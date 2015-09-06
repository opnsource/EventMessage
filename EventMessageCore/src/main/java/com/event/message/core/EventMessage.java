package com.event.message.core;

/**
 * Created by Administrator on 2015/8/26.
 */
public class EventMessage {

    final SubscriberMethod methodName;

    final Object target;

    Object[] params;

    int delay;


    EventMessage(SubscriberMethod method, Object target, int delay, Object[] params) {
        this.methodName = method;
        this.target = target;
        this.delay = delay;
        this.params = params;
    }

    public Object invoke() {
        return methodName.invoke(target, params);
    }

    /**
     * @param value Return value by invoke()
     */
    public void isInvokeSuccess(Object value) {
        methodName.isInvokeSuccess(value);
    }


    static class EventMessageBuilder {
        public final SubscriberMethod methodName;

        public final Object target;

        public Object[] params;

        public int delay;

        EventMessageBuilder(SubscriberMethod methodName, Object target) {
            this.methodName = methodName;
            this.target = target;
        }

        EventMessageBuilder params(Object... params) {
            this.params = params;
            return this;
        }

        EventMessageBuilder delay(int delay) {
            this.delay = delay;
            return this;
        }

        EventMessage builder() {
            return new EventMessage(methodName, target, delay, params);
        }
    }
}
