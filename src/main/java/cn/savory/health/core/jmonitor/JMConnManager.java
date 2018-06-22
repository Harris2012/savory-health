package cn.savory.health.core.jmonitor;

import cn.savory.health.core.event.JMEevntCenter;
import com.google.common.collect.Maps;
import com.sun.management.HotSpotDiagnosticMXBean;
import com.sun.management.OperatingSystemMXBean;

import javax.management.ListenerNotFoundException;
import javax.management.MBeanServerConnection;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.remote.JMXConnectionNotification;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author @author code_czp@126.com-2015年5月12日
 */

//https://github.com/coderczp/jmonitor


public class JMConnManager implements NotificationListener {

    public static final String GCCMS = "java.lang:type=GarbageCollector,name=ConcurrentMarkSweep";
    public static final String GCMSC = "java.lang:type=GarbageCollector,name=MarkSweepCompact";
    public static final String GCSCAV = "java.lang:type=GarbageCollector,name=PS Scavenge";
    public static final String GCMS = "java.lang:type=GarbageCollector,name=PS MarkSweep";
    public static final String GCPARNEW = "java.lang:type=GarbageCollector,name=ParNew";
    public static final String HOTSPOTDUMP = "com.sun.management:type=HotSpotDiagnostic";
    public static final String HEAP_ITEM = "PS Survivor Space,PS Eden Space,PS Old Gen";
    public static final String GCCOPY = "java.lang:type=GarbageCollector,name=Copy";
    public static final String OSBEANNAME = "java.lang:type=OperatingSystem";
    public static final String POOLNAME = "java.lang:type=MemoryPool";
    public static final String RUNTIMNAME = "java.lang:type=Runtime";
    public static final String NONHEAP_ITEM = "Code Cache, Perm Gen";
    private static final String CLSBEANAME = "java.lang:type=ClassLoading";
    public static final String THREAD_BEAN_NAME = "java.lang:type=Threading";
    public static final String MEMORYNAME = "java.lang:type=Memory";
    //public static final String JMONITOR = "JMonitor";
    private static ConcurrentHashMap<String, JMConnBean> conns = new ConcurrentHashMap<String, JMConnBean>();
    private static final NotificationListener INSTANCE = new JMConnManager();

    public static void addConnInfo(JMConnBean bean) {
        conns.putIfAbsent(bean.getName(), bean);
        Map<String, String> quit = Maps.newHashMap();
        quit.put("type", "add");
        quit.put("app", bean.getName());
        JMEevntCenter.getInstance().send(quit);

    }

    public static Map<String, JMConnBean> getApps() {
        return conns;
    }

    public static void init(String cfgPath) {
        JMConnBean bean = new JMConnBean();
        bean.setHost("127.0.0.1");
        bean.setPort(1099);
        bean.setName("JMonitor");
        addConnInfo(bean);
    }

    public static <T> T getServer(String app, String beanBane, Class<T> cls) throws IOException {
        T ser = ManagementFactory.newPlatformMXBeanProxy(getConn(app), beanBane, cls);
        return ser;
    }

    public static ThreadMXBean getThreadMBean(String app) throws IOException {
        return getServer(app, THREAD_BEAN_NAME, ThreadMXBean.class);
    }

    public static RuntimeMXBean getRuntimeMBean(String app) throws IOException {
        return getServer(app, RUNTIMNAME, RuntimeMXBean.class);
    }

    public static MemoryMXBean getMemoryMBean(String app) throws IOException {
        return getServer(app, MEMORYNAME, MemoryMXBean.class);
    }

    public static OperatingSystemMXBean getOSMbean(String app) throws IOException {
        return getServer(app, OSBEANNAME, OperatingSystemMXBean.class);
    }

    public static ClassLoadingMXBean getClassMbean(String app) throws IOException {
        return getServer(app, CLSBEANAME, ClassLoadingMXBean.class);
    }

    public static MBeanServerConnection getConn(String app) throws IOException {

            return ManagementFactory.getPlatformMBeanServer();

//        JMConnBean bean = conns.get(app);
//        if (bean == null)
//            throw new RuntimeException(app + ":disconnected");
//
//        if (bean.getConnector() == null) {
//            synchronized (JMConnManager.class) {
//                if (bean.getConnector() == null) {
//                    bean.setConnector(getConnection(bean));
//                }
//            }
//        }
//        return bean.getConnector().getMBeanServerConnection();
    }

    public static boolean isLocalHost(String ip) {
        return "127.0.0.1".equals(ip) || "localhost".equals(ip);
    }

    public static void close() {
        Collection<JMConnBean> values = conns.values();
        for (JMConnBean bean : values) {
            try {
                JMXConnector conn = bean.getConnector();
                conn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static JMXConnector getConnection(JMConnBean conn) {
        try {
            Map<String, String[]> map = new HashMap<String, String[]>();
            if (conn.getUser() != null && conn.getPwd() != null) {
                map.put(JMXConnector.CREDENTIALS, new String[]{conn.getUser(), conn.getPwd()});
            }
            String jmxURL = "service:jmx:rmi:///jndi/rmi://" + conn.getHost() + ":" + conn.getPort() + "/jmxrmi";
            JMXConnector connector = JMXConnectorFactory.newJMXConnector(new JMXServiceURL(jmxURL), map);
            connector.addConnectionNotificationListener(INSTANCE, null, conn.getName());
            connector.connect();
            return connector;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static HotSpotDiagnosticMXBean getHotspotBean(String app) throws IOException {
        return getServer(app, HOTSPOTDUMP, HotSpotDiagnosticMXBean.class);
    }

    @Override
    public void handleNotification(Notification notification, Object handback) {
        JMXConnectionNotification noti = (JMXConnectionNotification) notification;

        try {
            if (noti.getType().equals(JMXConnectionNotification.CLOSED)) {
                disconnect(String.valueOf(handback));
            } else if (noti.getType().equals(JMXConnectionNotification.FAILED)) {
                disconnect(String.valueOf(handback));
            } else if (noti.getType().equals(JMXConnectionNotification.NOTIFS_LOST)) {
                disconnect(String.valueOf(handback));
            }
        } catch (ListenerNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void disconnect(String app) throws ListenerNotFoundException, IOException {

        JMConnBean jmConnBean = conns.remove(app);
        Map<String, String> quit = Maps.newHashMap();
        quit.put("type", "quit");
        quit.put("app", app);
        JMEevntCenter.getInstance().send(quit);
        JMXConnector conn = jmConnBean.getConnector();
        conn.removeConnectionNotificationListener(INSTANCE);
        conn.close();
    }
}
