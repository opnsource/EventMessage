package com.event.message.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by liushu06 on 2015/8/25.
 */
class SubscriberMethod {

    private static final HashMap<String, MethodName> SUPPORT_METHOD = new HashMap<String, MethodName>();

    static {
        SUPPORT_METHOD.put(MethodName.METHOD_TYPE_ASY.getName(), MethodName.METHOD_TYPE_ASY);
        SUPPORT_METHOD.put(MethodName.METHOD_TYPE_BACKGROUND.getName(), MethodName.METHOD_TYPE_BACKGROUND);
        SUPPORT_METHOD.put(MethodName.METHOD_TYPE_MAIN.getName(), MethodName.METHOD_TYPE_MAIN);
        SUPPORT_METHOD.put(MethodName.METHOD_TYPE_POST.getName(), MethodName.METHOD_TYPE_POST);
        SUPPORT_METHOD.put(MethodName.METHOD_TYPE_SEND_MESSAGE.getName(), MethodName.METHOD_TYPE_SEND_MESSAGE);
    }

    private MethodName mMethodName;
    private ArrayList<Class> mMethodType;

    private SubscriberMethod(MethodName name) {
        mMethodName = name;
    }

    static MethodName getMethodType(String name) {
        if (name == null) {
            return null;
        }
        return SUPPORT_METHOD.get(name);
    }

    static void addSupportMethod(String methodName) {
        SUPPORT_METHOD.put(methodName, MethodName.create(methodName));
    }

    static void removeSupportMethod(String methodName) {
        SUPPORT_METHOD.remove(methodName);
    }

    static SubscriberMethod getSubscriberMethod(MethodName methodName, Object[] params) {
        if (methodName == null) {
            return null;
        }
        SubscriberMethod subscriberMethod = new SubscriberMethod(methodName);
        if (params != null && params.length != 0) {
            for (Object param : params) {
                subscriberMethod.add(param.getClass());
            }
        }
        return subscriberMethod;
    }

    static SubscriberMethod getSubscriberMethod(MethodName methodName, Class[] classes) {
        if (methodName == null) {
            return null;
        }
        SubscriberMethod subscriberMethod = new SubscriberMethod(methodName);
        if (classes != null && classes.length != 0) {
            subscriberMethod.add(classes);
        }
        return subscriberMethod;
    }

    static SubscriberMethod getSubscriberMethod(String name, Class[] classes) {
        MethodName methodName = getMethodType(name);
        if (methodName != null) {
            return getSubscriberMethod(methodName, classes);
        }
        return null;
    }

    SubscriberMethod add(Class type) {
        if (mMethodType == null) {
            mMethodType = new ArrayList<Class>();
        }
        mMethodType.add(type);
        return this;
    }

    SubscriberMethod add(Class[] types) {
        if (mMethodType == null) {
            mMethodType = new ArrayList<Class>();
        }
        mMethodType.addAll(Arrays.asList(types));
        return this;
    }

    private Class[] getClassType() {
        if (mMethodType == null || mMethodType.isEmpty()) {
            return null;
        } else {
            Class[] types = new Class[mMethodType.size()];
            mMethodType.toArray(types);
            return types;
        }
    }

    private int getTypeSize() {
        return mMethodType == null ? 0 : mMethodType.size();
    }

    String getKey() {
        StringBuffer buffer = new StringBuffer(mMethodName.getName());
        buffer.append("|").append(getTypeSize());
        Class[] types = getClassType();
        if (types != null) {
            for (Class type : types) {
                buffer.append("|" + type.toString()).append("|");
            }
        }
        return buffer.toString();
    }

    boolean checkMethod(MethodName methodName) {
        if (methodName == null || mMethodName == null || methodName.getName() == null || mMethodName.getName() == null) {
            LogUtils.write("methodName:" + methodName + "~~~~~this methodName:" + this.mMethodName);
            return false;
        }
        return methodName.getName().equals(mMethodName.getName());
    }

    Object invoke(Object target, Object... params) {
        if (target == null) {
            return this;
        }
        int paramSize = params == null ? 0 : params.length;
        if (paramSize != getTypeSize()) {
            LogUtils.write("params size not equals type size");
            return this;
        }
        try {
            Method method = target.getClass().getMethod(mMethodName.getName(), getClassType());
            return method.invoke(target, params);
        } catch (Exception e) {
            LogUtils.write(e);
        }
        return this;
    }

    boolean isInvokeSuccess(Object value) {
        return value != this;
    }

}
