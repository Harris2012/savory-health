package cn.savory.health.controller.response;

/**
 * @author hc_zhang
 * @date 2018/6/21.
 */
public class CheckDeadLockResponse extends ResponseBase {

    private boolean hasDeadLock;
    private String deadLockInfo;

    public boolean isHasDeadLock() {
        return hasDeadLock;
    }

    public void setHasDeadLock(boolean hasDeadLock) {
        this.hasDeadLock = hasDeadLock;
    }

    public String getDeadLockInfo() {
        return deadLockInfo;
    }

    public void setDeadLockInfo(String deadLockInfo) {
        this.deadLockInfo = deadLockInfo;
    }
}
