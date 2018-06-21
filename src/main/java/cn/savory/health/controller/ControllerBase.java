package cn.savory.health.controller;

import cn.savory.health.servlet.ApiServlet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author hc_zhang
 * @date 2018/6/21.
 */
public abstract class ControllerBase {

    private final static Charset CHARSET = Charset.forName("UTF-8");
    private final static Gson GSON = new GsonBuilder().create();

    protected void setResponse(HttpServletResponse response, Object item) throws IOException {

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type, Accept");
        response.setStatus(HttpServletResponse.SC_OK);

        if (item instanceof String) {

            response.setContentType("text/plain");
            response.getOutputStream().write(((String) item).getBytes(CHARSET));
            response.getOutputStream().close();
            return;
        }

        String content = GSON.toJson(item);
        setResponse(response, content.getBytes(CHARSET));
        response.getOutputStream().close();
    }
}
