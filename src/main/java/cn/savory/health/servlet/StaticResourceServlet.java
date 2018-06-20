package cn.savory.health.servlet;

import cn.savory.health.constant.MimeType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hc_zhang
 * @date 2018/6/20.
 */
public class StaticResourceServlet extends HttpServlet {

    final static ConcurrentHashMap<String, byte[]> CONTENT_MAP = new ConcurrentHashMap<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String servletPath = request.getServletPath();

        if ("/health".equalsIgnoreCase(servletPath)) {
            response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
            response.setHeader("Location", servletPath.substring(1) + "/index.html");
            return;
        }
        if ("/health/".equalsIgnoreCase(servletPath)) {
            response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
            response.setHeader("Location", "index.html");
            return;
        }

        String extension = servletPath.substring(servletPath.lastIndexOf(".") + 1);
        String mimeType = MimeType.get(extension);

        byte[] bytes = CONTENT_MAP.get(servletPath);
        if (bytes != null) {
            setResponse(response, mimeType, bytes);
            return;
        }

        InputStream inputStream = StaticResourceServlet.class.getClassLoader().getResourceAsStream("/static" + servletPath.substring(7));
        if (inputStream != null) {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(4096);
            byte[] buffer = new byte[4096];
            int length = 0;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            bytes = outputStream.toByteArray();
            CONTENT_MAP.putIfAbsent(servletPath, bytes);

            setResponse(response, mimeType, bytes);
            return;
        }

        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private void setResponse(HttpServletResponse response, String mimeType, byte[] bytes) throws IOException {

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type, Accept");
        response.setContentType(mimeType);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getOutputStream().write(bytes);
        response.getOutputStream().close();
    }
}
