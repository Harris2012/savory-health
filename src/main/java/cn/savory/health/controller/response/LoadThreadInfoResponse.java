package cn.savory.health.controller.response;

import cn.savory.health.controller.vo.ThreadInfoVo;

import java.util.HashMap;
import java.util.List;

public class LoadThreadInfoResponse extends ResponseBase {

    private int threadCount;
    private long daemonThreadCount;
    private long currentTimeMillis;
    private List<ThreadInfoVo> threadInfoList;
    private HashMap<Thread.State, Integer> stateMap;

    public long getDaemonThreadCount() {
        return daemonThreadCount;
    }

    public void setDaemonThreadCount(long daemonThreadCount) {
        this.daemonThreadCount = daemonThreadCount;
    }

    public long getCurrentTimeMillis() {
        return currentTimeMillis;
    }

    public void setCurrentTimeMillis(long currentTimeMillis) {
        this.currentTimeMillis = currentTimeMillis;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public List<ThreadInfoVo> getThreadInfoList() {
        return threadInfoList;
    }

    public void setThreadInfoList(List<ThreadInfoVo> threadInfoList) {
        this.threadInfoList = threadInfoList;
    }

    public HashMap<Thread.State, Integer> getStateMap() {
        return stateMap;
    }

    public void setStateMap(HashMap<Thread.State, Integer> stateMap) {
        this.stateMap = stateMap;
    }
}
