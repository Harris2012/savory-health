package cn.savory.health.servlet.api;

import cn.savory.health.servlet.api.request.UserProfileRequest;
import cn.savory.health.servlet.api.response.UserProfileResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserProfileServlet extends BaseApiServlet {

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        setResponse(httpServletResponse, profile(null));
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        setResponse(httpServletResponse, profile(null));
    }

    public UserProfileResponse profile(UserProfileRequest request) {

        UserProfileResponse response = new UserProfileResponse();

        response.setUserName("张三");
        response.setStatusCode(1);

        return response;
    }
}
