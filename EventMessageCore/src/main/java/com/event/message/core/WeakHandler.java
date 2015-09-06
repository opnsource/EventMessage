package com.event.message.core;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2015/8/16.
 */
public class WeakHandler {

    private static final IHandlerListener EMPTY_HANDLER = new IHandlerListener() {

        @Override
        public void handleMessage(Message msg) {

        }
    };
    private final EventHandler mEventHandler;

    public WeakHandler(IHandlerListener handlerListener, Looper looper) {
        IHandlerListener listener = handlerListener == null ? EMPTY_HANDLER : handlerListener;
        if (looper != null) {
            mEventHandler = new EventHandler(listener, looper);
        } else {
            mEventHandler = new EventHandler(listener);
        }
    }

    public WeakHandler(IHandlerListener listener) {
        this(listener, null);
    }

    public final Message obtainMessage() {
        return mEventHandler.obtainMessage();
    }

    public final Message obtainMessage(int what) {
        return mEventHandler.obtainMessage(what);
    }

    public final Message obtainMessage(int what, Object obj) {
        return mEventHandler.obtainMessage(what, obj);
    }

    public final Message obtainMessage(int what, int arg1, int arg2) {
        return mEventHandler.obtainMessage(what, arg1, arg2);
    }

    public final Message obtainMessage(int what, int arg1, int arg2, Object obj) {
        return mEventHandler.obtainMessage(what, arg1, arg2, obj);
    }

    public final boolean post(Runnable r) {
        return mEventHandler.post(r);
    }

    public final boolean postAtTime(Runnable r, long uptimeMillis) {
        return mEventHandler.postAtTime(getPost(r), uptimeMillis);
    }

    public final boolean postAtTime(Runnable r, Object token, long uptimeMillis) {
        return mEventHandler.postAtTime(getPost(r), token, uptimeMillis);
    }

    public final boolean postDelayed(Runnable r, long delayMillis) {
        return mEventHandler.postDelayed(getPost(r), delayMillis);
    }

    public final boolean postAtFrontOfQueue(Runnable r) {
        return mEventHandler.postAtFrontOfQueue(getPost(r));
    }

    public final boolean sendMessage(Message msg) {
        return sendMessageDelayed(msg, 0);
    }

    public final boolean sendEmptyMessage(int what) {
        return sendEmptyMessageDelayed(what, 0);
    }

    public final boolean sendEmptyMessageDelayed(int what, long delayMillis) {
        Message msg = Message.obtain();
        msg.what = what;
        return sendMessageDelayed(msg, delayMillis);
    }

    public final boolean sendEmptyMessageAtTime(int what, long uptimeMillis) {
        Message msg = Message.obtain();
        msg.what = what;
        return sendMessageAtTime(msg, uptimeMillis);
    }

    public final boolean sendMessageDelayed(Message msg, long delayMillis) {
        if (delayMillis < 0) {
            delayMillis = 0;
        }
        return sendMessageAtTime(msg, SystemClock.uptimeMillis() + delayMillis);
    }

    public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
        return mEventHandler.sendMessageAtTime(msg, uptimeMillis);
    }

    public final boolean sendMessageAtFrontOfQueue(Message msg) {
        return mEventHandler.sendMessageAtFrontOfQueue(msg);
    }

    public final boolean hasMessages(int what) {
        return mEventHandler.hasMessages(what);
    }

    public final boolean hasMessages(int what, Object object) {
        return mEventHandler.hasMessages(what, object);
    }

    public final Looper getLooper() {
        return mEventHandler.getLooper();
    }

    @Override
    public String toString() {
        return mEventHandler.toString();
    }

    private Runnable getPost(Runnable runnable) {
        if (runnable != null) {
            return new WeakRunnable(runnable);
        }
        return null;
    }

    private static final class EventHandler extends Handler {

        private final WeakReference<IHandlerListener> mHandlerListenerWeakReference;

        private EventHandler(IHandlerListener listener) {
            super();
            mHandlerListenerWeakReference = new WeakReference<IHandlerListener>(listener);
        }

        private EventHandler(IHandlerListener listener, Looper looper) {
            super(looper);
            mHandlerListenerWeakReference = new WeakReference<IHandlerListener>(listener);
        }

        @Override
        public void handleMessage(Message msg) {
            IHandlerListener handlerListener = getHandler();
            if (handlerListener != null) {
                handlerListener.handleMessage(msg);
            } else {
                super.handleMessage(msg);
            }
            LogUtils.write(msg.toString());
        }


        private IHandlerListener getHandler() {
            if (mHandlerListenerWeakReference != null) {
                return mHandlerListenerWeakReference.get();
            }
            return null;
        }
    }

    private static class WeakRunnable implements Runnable {

        private final WeakReference<Runnable> mWeakRunnable;

        WeakRunnable(Runnable runnable) {
            mWeakRunnable = new WeakReference<Runnable>(runnable);
        }

        @Override
        public void run() {
            Runnable run = getRunnable();
            if (run != null) {
                run.run();
            }
        }

        private Runnable getRunnable() {
            if (mWeakRunnable != null) {
                return mWeakRunnable.get();
            }
            return null;
        }
    }
}