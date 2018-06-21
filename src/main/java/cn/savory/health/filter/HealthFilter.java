package cn.savory.health.filter;

import cn.savory.health.servlet.ApiServlet;
import cn.savory.health.servlet.StaticResourceServlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hc_zhang
 * @date 2018/6/20.
 */
public class HealthFilter implements Filter {

    private ApiServlet apiServlet;
    private StaticResourceServlet staticResourceServlet;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        apiServlet = new ApiServlet();
        apiServlet.init();
        staticResourceServlet = new StaticResourceServlet();
        staticResourceServlet.init();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String contextPath = httpRequest.getContextPath();
        String path = httpRequest.getRequestURI();

        if (!path.startsWith(contextPath + "/health/") && !path.equalsIgnoreCase(contextPath + "/health")) {
            chain.doFilter(request, response);
            return;
        }

        //api
        if (path.startsWith(contextPath + "/health/api/")) {
            apiServlet.service(request, response);
            return;
        }

        staticResourceServlet.service(request, response);
    }

    @Override
    public void destroy() {
        apiServlet.destroy();
        apiServlet = null;
        staticResourceServlet.destroy();
        staticResourceServlet = null;
    }
}
