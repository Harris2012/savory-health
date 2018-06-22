package cn.savory.health.servlet;

import cn.savory.health.controller.JavaController;
import cn.savory.health.controller.UserController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

    private final static Gson GSON = new GsonBuilder().create();
    private final static Charset CHARSET = Charset.forName("UTF-8");

    private UserController userController;
    private JavaController javaController;

    public ApiServlet() {
        userController = new UserController();
        javaController = new JavaController();
    }

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

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, IllegalAccessException, InstantiationException {

        String path = request.getServletPath().substring(11);

        switch (path.toLowerCase()) {
            case "/user/profile": {
                userController.profile(request, response);
            }
            break;

            case "/java/checkdeadlock": {
                javaController.checkDeadLock(request, response);
            }
            break;

            case "/java/loadthreadinfo": {
                javaController.loadThreadInfo(request, response);
            }
            break;

            case "/java/dumpthread": {
                javaController.dumpThread(request, response);
            }
            break;

            default: {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            break;
        }
    }
}
