package cn.savory.health.servlet;

import cn.savory.health.servlet.api.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.omg.CORBA.PRIVATE_MEMBER;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author hc_zhang
 * @date 2018/6/20.
 */
public class ApiServlet extends HttpServlet {

    private final static int PREFIX_LENGTH = "/health/api".length();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            process(req, resp);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            process(req, resp);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, IllegalAccessException, InstantiationException, ServletException {

        String path = request.getServletPath().substring(PREFIX_LENGTH);

        switch (path.toLowerCase()) {
            case "/user/profile": {
                UserProfileServlet.class.newInstance().service(request, response);
            }
            break;

            case "/java/checkdeadlock": {
                JavaCheckDeadLockServlet.class.newInstance().service(request, response);
            }
            break;

            case "/java/loadthreadinfo": {
                JavaLoadRuntimeInfoServlet.class.newInstance().service(request, response);
            }
            break;

            case "/java/dumpthread": {
                JavaDumpThreadServlet.class.newInstance().service(request, response);
            }
            break;

            case "/java/loadruntimeinfo": {
                JavaLoadRuntimeInfoServlet.class.newInstance().service(request, response);
            }
            break;

            case "/java/gc": {
                JavaGCServlet.class.newInstance().service(request, response);
            }
            break;

            case "/java/heapdump": {
                JavaHeapDumpServlet.class.newInstance().service(request, response);
            }
            break;

            default: {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            break;
        }
    }
}
