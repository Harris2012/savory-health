package cn.savory.health.controller;

import cn.savory.health.controller.request.UserProfileRequest;
import cn.savory.health.controller.response.UserProfileResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hc_zhang
 * @date 2018/6/21.
 */
public class UserController extends ControllerBase {

    public void profile(HttpServletRequest request, HttpServletResponse response) throws IOException {

        setResponse(response, profile(null));
    }

    public UserProfileResponse profile(UserProfileRequest request) {

        UserProfileResponse response = new UserProfileResponse();

        response.setUserName("张三");
        response.setStatusCode(1);

        return response;
    }
}
