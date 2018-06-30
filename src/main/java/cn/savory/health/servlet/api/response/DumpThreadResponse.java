package cn.savory.health.servlet.api.response;

/**
 * @author hc_zhang
 * @date 2018/6/22.
 */
public class DumpThreadResponse extends ResponseBase {

    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
