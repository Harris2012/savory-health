package cn.savory.health.controller;

import cn.savory.health.controller.request.CheckDeadLockRequest;
import cn.savory.health.controller.request.LoadThreadInfoRequest;
import cn.savory.health.controller.response.CheckDeadLockResponse;
import cn.savory.health.controller.response.LoadThreadInfoResponse;
import cn.savory.health.controller.vo.ThreadInfoVo;
import cn.savory.health.core.jmonitor.JMConnManager;
import cn.savory.health.core.jmonitor.JMServer;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author hc_zhang
 * @date 2018/6/21.
 */
public class JavaController extends ControllerBase {

    private final static int maxDepth = 10000;

    public void checkDeadLock(HttpServletRequest request, HttpServletResponse response) throws IOException {

        setResponse(response, checkDeadLock(null));
    }

    public void loadThreadInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

        setResponse(response, loadThreadInfo(null));
    }

    private CheckDeadLockResponse checkDeadLock(CheckDeadLockRequest request) throws IOException {

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

    private LoadThreadInfoResponse loadThreadInfo(LoadThreadInfoRequest request) throws IOException {

        LoadThreadInfoResponse response = new LoadThreadInfoResponse();

        response.setCurrentTimeMillis(System.currentTimeMillis());

        ThreadMXBean threadMXBean = JMConnManager.getThreadMBean("jmonitor");
        response.setThreadCount(threadMXBean.getThreadCount());
        response.setDaemonThreadCount(threadMXBean.getDaemonThreadCount());

        List<ThreadInfoVo> threadInfoVoList = Lists.newArrayList();
        HashMap<Thread.State, Integer> stateMap = new HashMap<Thread.State, Integer>();
        ThreadInfo[] allThreads = threadMXBean.dumpAllThreads(false, false);
        for (ThreadInfo threadInfo : allThreads) {
            ThreadInfoVo threadInfoVo = new ThreadInfoVo();

            long threadId = threadInfo.getThreadId();

            threadInfoVo.setThreadId(threadId);
            threadInfoVo.setThreadCpuTime(TimeUnit.NANOSECONDS.toMillis(threadMXBean.getThreadCpuTime(threadId)));
            threadInfoVo.setThreadName(threadInfo.getThreadName());

            Thread.State threadState = threadInfo.getThreadState();
            threadInfoVo.setThreadState(threadState);
            Integer value = stateMap.get(threadState);
            if (value == null) {
                stateMap.put(threadState, 1);
            } else {
                stateMap.put(threadState, value + 1);
            }

            threadInfoVoList.add(threadInfoVo);
        }
        response.setStateMap(stateMap);
        response.setThreadInfoList(threadInfoVoList);

        return response;
    }
}
