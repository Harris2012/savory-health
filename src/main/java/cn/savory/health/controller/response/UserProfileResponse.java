package cn.savory.health.controller.response;

/**
 * @author hc_zhang
 * @date 2018/6/21.
 */
public class UserProfileResponse extends ResponseBase {

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
