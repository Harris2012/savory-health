package cn.savory.health.servlet.api;

import cn.savory.health.servlet.api.request.CheckDeadLockRequest;
import cn.savory.health.servlet.api.response.CheckDeadLockResponse;
import cn.savory.health.core.jmonitor.JMConnManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class JavaCheckDeadLockServlet extends BaseApiServlet {

    private final static int maxDepth = 10000;

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {


        setResponse(httpServletResponse, checkDeadLock(null));
    }

    private CheckDeadLockResponse checkDeadLock(CheckDeadLockRequest request) throws IOException {

        CheckDeadLockResponse response = new CheckDeadLockResponse();

        ThreadMXBean threadMXBean = JMConnManager.getThreadMBean("jmonitor");
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
