/*
 * Created on 2004-7-2
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author 001
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class WebUtils {
    private static String webAppRoot     = "";
    private static String webAppLogsRoot = "";
    private static final Log log = LogFactory.getLog(WebUtils.class);

    public static String getWebAppRoot() {
        return webAppRoot;
    }

    public static void setWebAppRoot(String webAppRoot) {
        WebUtils.webAppRoot = webAppRoot;
    }

    public static String getWebAppLogsRoot() {
        return webAppLogsRoot;
    }

    public static void setWebAppLogsRoot(String webAppLogsRoot) {
        WebUtils.webAppLogsRoot = webAppLogsRoot;
    }
    
    
    
    public static final Properties getConfigProperties(String path) {
        Properties properties = new Properties();  
        
        if (path != null && path.startsWith("/")) {
            path = path.substring(1);
        }
        try {    
            String tempPath = path;
            if(webAppRoot != null){
                tempPath = webAppRoot + tempPath ;
            }
            log.info("初始化程序绝对路径 tempPath  -----  "+tempPath);
            InputStream in = new FileInputStream(tempPath);
            properties.load( in );
            if(in !=null){
                in.close() ;
            }   
            log.info("获取路径文件 -- "+tempPath+"  成功");
        }catch(Exception e) {
            e.printStackTrace();
            try {
                log.info("初始化类中路径 path  -----  "+path);
                ClassLoader ccl = Thread.currentThread().getContextClassLoader();
                properties.load(ccl.getResourceAsStream(path)); 
                log.info("获取路径文件 -- "+path+"  成功");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }      
        return properties;
    }

    

}
