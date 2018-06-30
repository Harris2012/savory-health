package cn.savory.health.servlet.api;

import cn.savory.health.servlet.api.request.LoadRuntimeInfoRequest;
import cn.savory.health.servlet.api.response.LoadRuntimeInfoResponse;
import cn.savory.health.core.jmonitor.JMConnManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.RuntimeMXBean;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

public class JavaLoadRuntimeInfoServlet extends BaseApiServlet {

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        setResponse(httpServletResponse, loadRuntimeInfo(null));
    }

    private LoadRuntimeInfoResponse loadRuntimeInfo(LoadRuntimeInfoRequest request) throws IOException {

        LoadRuntimeInfoResponse response = new LoadRuntimeInfoResponse();

        RuntimeMXBean runtimeMXBean = JMConnManager.getRuntimeMBean("jmonitor");
        ClassLoadingMXBean classLoadingMXBean = JMConnManager.getClassMbean("jmonitor");
        Map<String, String> props = runtimeMXBean.getSystemProperties();
        DateFormat format = DateFormat.getInstance();

        response.setApppId(runtimeMXBean.getName());
        response.setStartParam(runtimeMXBean.getInputArguments());
        response.setStartTime(format.format(new Date(runtimeMXBean.getStartTime())));
        response.setClassLoadedNow(classLoadingMXBean.getLoadedClassCount());
        response.setClassLoadedAll(classLoadingMXBean.getTotalLoadedClassCount());
        response.setClassUnloadedAll(classLoadingMXBean.getUnloadedClassCount());

        response.setStatusCode(1);
        return response;
    }
}
