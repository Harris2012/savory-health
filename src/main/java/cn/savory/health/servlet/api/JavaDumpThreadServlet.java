package cn.savory.health.servlet.api;

import cn.savory.health.servlet.api.request.DumpThreadRequest;
import cn.savory.health.servlet.api.response.DumpThreadResponse;
import cn.savory.health.core.jmonitor.JMConnManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.text.DateFormat;
import java.util.Date;

public class JavaDumpThreadServlet extends BaseApiServlet {

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        DumpThreadRequest dumpThreadRequest = null;
        try {
            dumpThreadRequest = parseRequest(httpServletRequest, DumpThreadRequest.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        DumpThreadResponse dumpThreadResponse = dumpThread(dumpThreadRequest);

        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        String dateString = dateFormat.format(new Date()).replaceAll("\\D", "_");
        String fileName = String.format("%s.threaddump", dateString);

        setFileResponse(httpServletResponse, fileName, dumpThreadResponse.getInfo());
    }

    public DumpThreadResponse dumpThread(DumpThreadRequest request) throws IOException {

        DumpThreadResponse response = new DumpThreadResponse();

        ThreadMXBean tBean = JMConnManager.getThreadMBean("jmonitor");
        if (request.getThreadId() > 0) {
            ThreadInfo threadInfo = tBean.getThreadInfo(request.getThreadId(), Integer.MAX_VALUE);
            response.setInfo(threadInfo.toString());
        } else {
            StringBuffer stringBuffer = new StringBuffer();

            ThreadInfo[] dumpAllThreads = tBean.dumpAllThreads(false, false);
            for (ThreadInfo threadInfo : dumpAllThreads) {
                stringBuffer.append("\n").append(threadInfo);
            }
            response.setInfo(stringBuffer.toString());
        }

        return response;
    }
}
