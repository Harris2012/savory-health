package cn.savory.health.vo;

public class ThreadInfoVo {

    private long threadId;
    private long threadCpuTime;
    private String threadName;
    private Thread.State threadState;

    public Thread.State getThreadState() {
        return threadState;
    }

    public void setThreadState(Thread.State threadState) {
        this.threadState = threadState;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public long getThreadCpuTime() {
        return threadCpuTime;
    }

    public void setThreadCpuTime(long threadCpuTime) {
        this.threadCpuTime = threadCpuTime;
    }
}
