package cn.savory.health.servlet.api.request;

/**
 * @author hc_zhang
 * @date 2018/6/22.
 */
public class DumpThreadRequest {

    private long threadId;

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }
}
