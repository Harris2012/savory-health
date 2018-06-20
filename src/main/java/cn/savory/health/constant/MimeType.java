package cn.savory.health.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hc_zhang
 * @date 2018/6/20.
 */
public final class MimeType {

    final static Map<String, String> MIMEMAP = new HashMap<String, String>();

    static {
        MIMEMAP.put("js", "text/javascript");
        MIMEMAP.put("png", "image/png");
        MIMEMAP.put("gif", "image/gif");
        MIMEMAP.put("css", "text/css");
        MIMEMAP.put("jpg", "image/jpeg");
        MIMEMAP.put("jpeg", "image/jpeg");
        MIMEMAP.put("html", "text/html");
    }

    public static String get(String extension) {

        return MIMEMAP.get(extension);
    }
}
