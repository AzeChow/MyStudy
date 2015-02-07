package com.bestway.bcus;

import java.io.File;

import org.springframework.web.util.Log4jConfigServlet;

import com.bestway.common.WebUtils;

public class CustomLog4jConfigServlet extends Log4jConfigServlet {
    /**
     * Initialize the root web application context.
     */
    public void init() {
        String webAppRoot = this.getServletContext().getRealPath("/");  
        String jbcusLogsRoot = webAppRoot+"/"+"jbcus_logs";        
        File logs = new File(jbcusLogsRoot);        
        if (!logs.exists()){
            logs.mkdir();
        }
        String initTomcatLogs = webAppRoot+"/"+"jbcus_logs/log_tomcat";
        File initTomcatLogsFile = new File(initTomcatLogs);   
        if (!initTomcatLogsFile.exists()){
            initTomcatLogsFile.mkdir();
        }
        WebUtils.setWebAppRoot(webAppRoot);
        WebUtils.setWebAppLogsRoot(jbcusLogsRoot);
        super.init();
    }
}
