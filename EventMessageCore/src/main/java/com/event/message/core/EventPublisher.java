package com.event.message.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2015/8/23.
 */
public class EventPublisher {

    static final String LOG = EventPublisher.class.getSimpleName();


    private static final ConcurrentHashMap<String, ArrayList<Object>> REGISTEROBJ = new ConcurrentHashMap<String, ArrayList<Object>>();

    private static IEventProcess sEventProcess;

    static {
        sEventProcess = new PostProcess().setNextEventProcess(new AsyProcess()).setNextEventProcess(new BackgroundProcess()).setNextEventProcess(new MainProcess());
    }

    public static void register(Object target) {
        parseAndRegisterMethod(target, true);
    }

    public static void unregister(Object target) {
        parseAndRegisterMethod(target, false);
    }


    /**
     * import a new IEventProcess
     *
     * @param process
     */
    public static void addEventProcess(IEventProcess process) {
        sEventProcess = sEventProcess.setNextEventProcess(process);
    }

    public static void post(EventPostBuilder builder) {
        if (builder != null) {
            builder.post();
        }
    }

    public static void sendMessage(EventHandlerMessageBuilder builder) {
        if (builder != null) {
            builder.sendMessage();
        }
    }


    /**
     * Call before @see EventPublisher#register(Object)
     *
     * @param methodName
     */
    public static void addSupportMethod(String methodName) {
        SubscriberMethod.addSupportMethod(methodName);
    }

    public static void removeSupportMethod(String methodName) {
        SubscriberMethod.removeSupportMethod(methodName);
    }

    static void handler(SubscriberMethod subscriberMethod, Object target, int delay, Object[] params) {
        ArrayList<Object> targets = REGISTEROBJ.get(subscriberMethod.getKey());
        if (targets != null && !targets.isEmpty()) {
            int size = targets.size();
            if (target == null) {
                while (size > 0) {
                    target = targets.get(--size);
                    EventMessage message = new EventMessage.EventMessageBuilder(subscriberMethod, target).delay(delay).params(params).builder();
                    sEventProcess.process(message);
                }
            } else {
                if (targets.contains(target)) {
                    EventMessage message = new EventMessage.EventMessageBuilder(subscriberMethod, target).delay(delay).params(params).builder();
                    sEventProcess.process(message);
                }
            }
        }
    }

    private static void parseAndRegisterMethod(Object obj, boolean register) {
        if (obj == null) {
            return;
        }
        Method[] methods = obj.getClass().getDeclaredMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            Class[] parameterTypes = method.getParameterTypes();
            SubscriberMethod subscriberMethod = SubscriberMethod.getSubscriberMethod(methodName, parameterTypes);
            if (subscriberMethod == null) {
                continue;
            }
            String key = subscriberMethod.getKey();
            ArrayList<Object> array = REGISTEROBJ.get(key);
            if (register) {
                if (array == null) {
                    array = new ArrayList<Object>();
                    REGISTEROBJ.put(key, array);
                }
                array.add(obj);
            } else {
                if (array != null) {
                    array.remove(obj);
                }
            }
        }
    }
}