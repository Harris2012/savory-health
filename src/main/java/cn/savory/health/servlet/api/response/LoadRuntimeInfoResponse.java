package cn.savory.health.servlet.api.response;

import java.util.List;

/**
 * @author hc_zhang
 * @date 2018/6/22.
 */
public class LoadRuntimeInfoResponse extends ResponseBase {

    private String apppId;
    private List<String> startParam;
    private String startTime;
    private int classLoadedNow;
    private long classUnloadedAll;
    private long classLoadedAll;

    public String getApppId() {
        return apppId;
    }

    public void setApppId(String apppId) {
        this.apppId = apppId;
    }

    public List<String> getStartParam() {
        return startParam;
    }

    public void setStartParam(List<String> startParam) {
        this.startParam = startParam;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getClassLoadedNow() {
        return classLoadedNow;
    }

    public void setClassLoadedNow(int classLoadedNow) {
        this.classLoadedNow = classLoadedNow;
    }

    public long getClassUnloadedAll() {
        return classUnloadedAll;
    }

    public void setClassUnloadedAll(long classUnloadedAll) {
        this.classUnloadedAll = classUnloadedAll;
    }

    public long getClassLoadedAll() {
        return classLoadedAll;
    }

    public void setClassLoadedAll(long classLoadedAll) {
        this.classLoadedAll = classLoadedAll;
    }
}
