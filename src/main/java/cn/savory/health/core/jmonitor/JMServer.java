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
//    @HttpMapping(url = "/dumpThead")
//    public void doDumpThread(Map<String, Object> param) {
//        try {
//            HttpServletRequest req = (HttpServletRequest) param.get(JMDispatcher.REQ);
//            HttpServletResponse resp = (HttpServletResponse) param.get(JMDispatcher.RESP);
//            String app = req.getParameter("app");
//            String threadId = req.getParameter("threadId");
//            ThreadMXBean tBean = JMConnManager.getThreadMBean(app);
//            Map<String, String> data = new Map<String, String>();
//            if (threadId != null) {
//                Long id = Long.valueOf(threadId);
//                ThreadInfo threadInfo = tBean.getThreadInfo(id, Integer.MAX_VALUE);
//                data.put("info", threadInfo.toString());
//            } else {
//                ThreadInfo[] dumpAllThreads = tBean.dumpAllThreads(false, false);
//                StringBuffer info = new StringBuffer();
//                for (ThreadInfo threadInfo : dumpAllThreads) {
//                    info.append("\n").append(threadInfo);
//                }
//                data.put("info", info);
//            }
//            writeFile(req, resp, data);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @HttpMapping(url = "/loadRuntimeInfo")
//    public Map<String, String> doLoadRuntimeInfo(Map<String, Object> param) {
//        try {
//            String app = ((HttpServletRequest) param.get(JMDispatcher.REQ)).getParameter("app");
//            RuntimeMXBean mBean = JMConnManager.getRuntimeMBean(app);
//            ClassLoadingMXBean cBean = JMConnManager.getClassMbean(app);
//            Map<String, String> props = mBean.getSystemProperties();
//            DateFormat format = DateFormat.getInstance();
//            List<String> input = mBean.getInputArguments();
//            Date date = new Date(mBean.getStartTime());
//
//            TreeMap<String, Object> data = new TreeMap<String, Object>();
//
//            data.put("apppid", mBean.getName());
//            data.put("startparam", input.toString());
//            data.put("starttime", format.format(date));
//            data.put("classLoadedNow", cBean.getLoadedClassCount());
//            data.put("classUnloadedAll", cBean.getUnloadedClassCount());
//            data.put("classLoadedAll", cBean.getTotalLoadedClassCount());
//            data.putAll(props);
//
//            Map<String, String> json = new Map<String, String>(true);
//            json.putAll(data);
//            return json;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @HttpMapping(url = "/doGC")
//    public String doVMGC(Map<String, Object> param) {
//        try {
//            String app = ((HttpServletRequest) param.get(JMDispatcher.REQ)).getParameter("app");
//            JMConnManager.getMemoryMBean(app).gc();
//            return "success";
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @HttpMapping(url = "/doHeapDump")
//    public Map<String, String> doHeapDump(Map<String, Object> param) {
//        try {
//            HttpServletRequest req = (HttpServletRequest) param.get(JMDispatcher.REQ);
//            String app = req.getParameter("app");
//            JMConnBean bean = JMConnManager.getApps().get(app);
//            String host = bean.getHost();
//            boolean islocal = JMConnManager.isLocalHost(host);
//            DateFormat fmt = DateFormat.getDateTimeInstance();
//            String date = fmt.format(new Date()).replaceAll("\\D", "_");
//            if (islocal) {
//                return doLocalDump(req, app, date);
//            }
//            return doRemoteDump(app, date, host);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private Map<String, String> doRemoteDump(String app, String date, String host) throws IOException {
//        RuntimeMXBean mBean = JMConnManager.getRuntimeMBean(app);
//        String dir = mBean.getSystemProperties().get("user.dir");
//        String dumpFile = String.format("%s/%s_%s_heap.hprof", dir, app, date);
//        JMConnManager.getHotspotBean(app).dumpHeap(dumpFile, false);
//
//        Map<String, String> res = new Map<String, String>();
//        res.put("file", host + ":" + dumpFile);
//        res.put("local", false);
//        return res;
//    }
//
//    @SuppressWarnings("deprecation")
//    private Map<String, String> doLocalDump(HttpServletRequest req, String app, String date) throws IOException {
//        File root = new File(req.getRealPath(req.getRequestURI()));
//        String dir = root.getParentFile().getParent();
//        File file = new File(String.format("%s/dump/%s_%s_heap.hprof", dir, app, date));
//        file.getParentFile().mkdirs();
//        String dumpFile = file.getAbsolutePath();
//        JMConnManager.getHotspotBean(app).dumpHeap(dumpFile, false);
//
//        Map<String, String> res = new Map<String, String>();
//        res.put("local", true);
//        res.put("file", String.format("./dump/%s", file.getName()));
//
//        return res;
//    }
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
//
//    private void writeFile(HttpServletRequest req, HttpServletResponse resp, Map<String, String> rtInfo) {
//        try {
//            String javaApp = req.getParameter("app");
//            DateFormat fmt = DateFormat.getDateTimeInstance();
//            String dateStr = fmt.format(new Date()).replaceAll("\\D", "_");
//            String fileName = String.format("%s-%s.threaddump", javaApp, dateStr);
//            resp.setHeader("content-disposition", "attachment; filename=" + fileName);
//            PrintWriter out = resp.getWriter();
//            out.print(rtInfo.get("info"));
//            out.flush();
//            out.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
