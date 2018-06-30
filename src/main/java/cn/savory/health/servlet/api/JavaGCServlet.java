package cn.savory.health.servlet.api;

import cn.savory.health.servlet.api.request.GCRequest;
import cn.savory.health.servlet.api.response.GCResponse;
import cn.savory.health.core.jmonitor.JMConnManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JavaGCServlet extends BaseApiServlet {

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        setResponse(httpServletResponse, gc(null));
    }

    private GCResponse gc(GCRequest request) throws IOException {

        GCResponse response = new GCResponse();

        JMConnManager.getMemoryMBean("jmonitor").gc();

        response.setStatusCode(1);
        return response;
    }
}
