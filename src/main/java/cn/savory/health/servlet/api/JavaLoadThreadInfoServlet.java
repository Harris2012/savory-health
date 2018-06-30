package cn.savory.health.servlet.api;

import cn.savory.health.servlet.api.request.LoadThreadInfoRequest;
import cn.savory.health.servlet.api.response.LoadThreadInfoResponse;
import cn.savory.health.core.jmonitor.JMConnManager;
import cn.savory.health.vo.ThreadInfoVo;
import com.google.common.collect.Lists;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class JavaLoadThreadInfoServlet extends BaseApiServlet {

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        setResponse(httpServletResponse, loadThreadInfo(null));
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

        response.setStatusCode(1);
        return response;
    }
}
