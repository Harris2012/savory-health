package cn.savory.health.core.event;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;


public class JMEevntCenter implements Runnable {

    private CopyOnWriteArrayList<JMEvevntListener> listeners = new CopyOnWriteArrayList<JMEvevntListener>();
    private BlockingQueue<Map<String, String>> events = new LinkedBlockingQueue<>();
    private ExecutorService dispatchServer = Executors.newSingleThreadExecutor();
    private static JMEevntCenter INSTANCE;

    private JMEevntCenter() {
        dispatchServer.execute(this);
    }

    public static JMEevntCenter getInstance() {
        if (INSTANCE == null) {
            synchronized (JMEevntCenter.class) {
                if (INSTANCE == null) {
                    INSTANCE = new JMEevntCenter();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Map<String, String> event = events.take();
                for (JMEvevntListener listener : listeners) {
                    listener.handle(event);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addListener(JMEvevntListener listener) {
        listeners.add(listener);
    }

    public void send(Map<String, String> event) {
        events.add(event);
    }

    public void close() {
        events.clear();
        listeners.clear();
        dispatchServer.shutdownNow();
    }

}
