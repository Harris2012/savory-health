package cn.savory.health.core.jmonitor;

import com.google.common.collect.Maps;

import java.io.IOException;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Map;

/***
 *
 * @author coder_czp@126.com
 *
 */
public class JMServer {


//
//    @HttpMapping(url = "/loadMonitorData")
//    public Map<String, String> doLoadMonitorData(Map<String, Object> param) {
//        try {
//            String app = ((HttpServletRequest) param.get(JMDispatcher.REQ)).getParameter("app");
//            long now = System.currentTimeMillis();
//            Map<String, String> data = new Map<String, String>();
//
//            Map<String, String> gc = geGCInfo(app);
//            Map<String, String> cpu = findCpuInfo(app);
//            Map<String, String> memory = loadMemoryInfo(app);
//
//            data.put("gc", gc);
//            data.put("cpu", cpu);
//            data.put("time", now);
//            data.put("memory", memory);
//
//            return data;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @HttpMapping(url = "/loadCluster")
//    public JSONArray doRequestLoadCluster(Map<String, Object> param) {
//        Map<String, JMConnBean> apps = JMConnManager.getApps();
//        JSONArray tree = new JSONArray();
//        for (JMConnBean bean : apps.values()) {
//            Map<String, String> node = new Map<String, String>();
//            node.put("host", bean.getHost());
//            node.put("port", bean.getPort());
//            node.put("text", bean.getName());
//            node.put("cluster", "test");
//            tree.add(node);
//        }
//        return tree;
//
//    }
//
//
//    public static Map<String, String> loadMemoryInfo(String app) {
//        try {
//            MemoryMXBean mBean = JMConnManager.getMemoryMBean(app);
//            MemoryUsage nonHeap = mBean.getNonHeapMemoryUsage();
//            MemoryUsage heap = mBean.getHeapMemoryUsage();
//
//            Map<String, String> map = new Map<String, String>(true);
//            buildMemoryJSon(heap, "heap", map);
//            buildMemoryJSon(nonHeap, "nonheap", map);
//
//            Map<String, String> heapChild = new Map<String, String>();
//            Map<String, String> nonheapChild = new Map<String, String>();
//
//            Map<String, String> heapUsed = new Map<String, String>();
//            Map<String, String> heapMax = new Map<String, String>();
//            heapUsed.put("used", heap.getUsed());
//            heapMax.put("used", heap.getCommitted());
//            heapChild.put("HeapUsed", heapUsed);
//            heapChild.put("HeapCommit", heapMax);
//
//            Map<String, String> nonheapUsed = new Map<String, String>();
//            Map<String, String> noheapMax = new Map<String, String>();
//            nonheapUsed.put("used", nonHeap.getUsed());
//            noheapMax.put("used", nonHeap.getCommitted());
//
//            nonheapChild.put("NonheapUsed", nonheapUsed);
//            nonheapChild.put("NonheapCommit", noheapMax);
//
//            ObjectName obj = new ObjectName("java.lang:type=MemoryPool,*");
//            MBeanServerConnection conn = JMConnManager.getConn(app);
//            Set<ObjectInstance> MBeanset = conn.queryMBeans(obj, null);
//            for (ObjectInstance objx : MBeanset) {
//                String name = objx.getObjectName().getCanonicalName();
//                String keyName = objx.getObjectName().getKeyProperty("name");
//                MemoryPoolMXBean bean = JMConnManager.getServer(app, name, MemoryPoolMXBean.class);
//                Map<String, String> item = toJson(bean.getUsage());
//                if (JMConnManager.HEAP_ITEM.contains(keyName)) {
//                    heapChild.put(keyName, item);
//                } else {
//                    nonheapChild.put(keyName, item);
//                }
//            }
//            map.getMap<String, String> ("heap").put("childs", heapChild);
//            map.getMap<String, String> ("nonheap").put("childs", nonheapChild);
//
//            return map;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static Map<String, String> findCpuInfo(String app) {
//        try {
//            Map<String, String> map = new Map<String, String>(true);
//            OperatingSystemMXBean os = JMConnManager.getOSMbean(app);
//            map.put("os", (long) (os.getSystemCpuLoad() * 100));
//            map.put("vm", (long) (os.getProcessCpuLoad() * 100));
//            map.put("cores", (long) (os.getAvailableProcessors()));
//            map.put("freememory", os.getFreePhysicalMemorySize());
//            return map;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static Map<String, String> geGCInfo(String app) throws Exception {
//        ObjectName obj = new ObjectName("java.lang:type=GarbageCollector,*");
//        MBeanServer conn = ManagementFactory.getPlatformMBeanServer();
//        Set<ObjectInstance> MBeanset = conn.queryMBeans(obj, null);
//        Class<GarbageCollectorMXBean> cls = GarbageCollectorMXBean.class;
//        Map<String, String> data = new Map<String, String>();
//        for (ObjectInstance objx : MBeanset) {
//            String name = objx.getObjectName().getCanonicalName();
//            String keyName = objx.getObjectName().getKeyProperty("name");
//            GarbageCollectorMXBean gc = ManagementFactory.newPlatformMXBeanProxy(conn, name, cls);
//            data.put(keyName + "-time", gc.getCollectionTime() / 1000.0);
//            data.put(keyName + "-count", gc.getCollectionCount());
//        }
//        return data;
//    }
//
//    private static void buildMemoryJSon(MemoryUsage useage, String name, Map<String, String> map) {
//        Map<String, String> item = toJson(useage);
//        map.put(name, item);
//    }
//
//    private static Map<String, String> toJson(MemoryUsage useage) {
//        Map<String, String> item = new Map<String, String>();
//        item.put("commit", useage.getCommitted());
//        item.put("used", useage.getUsed());
//        item.put("init", useage.getInit());
//        item.put("max", useage.getMax());
//        return item;
//    }

}
