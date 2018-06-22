package cn.savory.health.controller;

import cn.savory.health.controller.request.CheckDeadLockRequest;
import cn.savory.health.controller.response.CheckDeadLockResponse;
import cn.savory.health.core.jmonitor.JMConnManager;
import cn.savory.health.core.jmonitor.JMServer;
import com.google.common.collect.Maps;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Map;

/**
 * @author hc_zhang
 * @date 2018/6/21.
 */
public class JavaController extends ControllerBase {

    private final static int maxDepth = 10000;

    public void checkDeadLock(HttpServletRequest request, HttpServletResponse response) throws IOException {

        setResponse(response, checkDeadLock(null));
    }

    public CheckDeadLockResponse checkDeadLock(CheckDeadLockRequest request) throws IOException {

        CheckDeadLockResponse response = new CheckDeadLockResponse();

        ThreadMXBean threadMXBean = JMConnManager.getThreadMBean("jmonitor");
        Map<String, Object> json = Maps.newHashMap();
        long[] deadlockedThreads = threadMXBean.findDeadlockedThreads();
        if (deadlockedThreads == null || deadlockedThreads.length == 0) {

            return response;
        }

        response.setHasDeadLock(true);

        ThreadInfo[] threadInfo = threadMXBean.getThreadInfo(deadlockedThreads, maxDepth);
        StringBuffer stringBuffer = new StringBuffer();
        for (ThreadInfo info : threadInfo) {
            stringBuffer.append("\n").append(info);
        }
        response.setDeadLockInfo(stringBuffer.toString());

        return response;
    }
}
