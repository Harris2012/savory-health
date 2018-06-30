package cn.savory.health.servlet.api;

import cn.savory.health.servlet.api.request.HeapDumpRequest;
import cn.savory.health.servlet.api.response.HeapDumpResponse;
import cn.savory.health.core.jmonitor.JMConnManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class JavaHeapDumpServlet extends BaseApiServlet {

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        setResponse(httpServletResponse, heapDump(null, httpServletRequest));
    }

    private HeapDumpResponse heapDump(HeapDumpRequest request, HttpServletRequest httpServletRequest) throws IOException {

        HeapDumpResponse response = new HeapDumpResponse();

        DateFormat fmt = DateFormat.getDateTimeInstance();
        String date = fmt.format(new Date()).replaceAll("\\D", "_");

        String filePath = httpServletRequest.getServletContext().getRealPath(httpServletRequest.getRequestURI());

        File root = new File(filePath);
        String dir = root.getParentFile().getParent();
        File file = new File(String.format("%s/dump/%s_%s_heap.hprof", dir, "jmonitor", date));
        file.getParentFile().mkdirs();
        String dumpFile = file.getAbsolutePath();
        JMConnManager.getHotspotBean("jmonitor").dumpHeap(dumpFile, false);

        String fileX = String.format("./dump/%s", file.getName());
        System.out.println(fileX);

        return response;
    }
}
