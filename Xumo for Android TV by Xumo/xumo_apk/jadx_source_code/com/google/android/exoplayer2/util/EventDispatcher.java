package com.google.android.exoplayer2.util;

import android.os.Handler;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public final class EventDispatcher<T> {
    private final CopyOnWriteArrayList<HandlerAndListener<T>> listeners = new CopyOnWriteArrayList();

    public interface Event<T> {
        void sendTo(T t);
    }

    private static final class HandlerAndListener<T> {
        public final Handler handler;
        public final T listener;

        public HandlerAndListener(Handler handler, T t) {
            this.handler = handler;
            this.listener = t;
        }
    }

    public void addListener(Handler handler, T t) {
        boolean z = (handler == null || t == null) ? false : true;
        Assertions.checkArgument(z);
        removeListener(t);
        this.listeners.add(new HandlerAndListener(handler, t));
    }

    public void removeListener(T t) {
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            HandlerAndListener handlerAndListener = (HandlerAndListener) it.next();
            if (handlerAndListener.listener == t) {
                this.listeners.remove(handlerAndListener);
            }
        }
    }

    public void dispatch(Event<T> event) {
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            HandlerAndListener handlerAndListener = (HandlerAndListener) it.next();
            handlerAndListener.handler.post(new -$$Lambda$EventDispatcher$N5z9NZy9yo_9QWPNCHLnel_5AjM(event, handlerAndListener.listener));
        }
    }
}
