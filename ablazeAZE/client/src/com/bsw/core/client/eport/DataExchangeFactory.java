/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsw.core.client.eport;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * QP数据接口工厂
 *
 * @author xc
 */
public class DataExchangeFactory {

    private static final Map<String, Object> cacheMap = new ConcurrentHashMap<String, Object>();

    private static final Properties config = new Properties();

    private final Object lock = new Object();

    private static DataExchangeFactory instance = null;

    private static URLClassLoader classLoader;

    private final String configPath = "/eport.properties";

    static {
        instance = new DataExchangeFactory();        
    }

    private DataExchangeFactory() {
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        InputStream in = null;
        try {
            in = DataExchangeFactory.class.getResourceAsStream(configPath);
            config.load(in);
        } catch (IOException ex) {
            Logger.getLogger(DataExchangeFactory.class.getName()).log(Level.SEVERE, "加载QP配置文件：" + configPath, ex);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(DataExchangeFactory.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    /**
     * 获取工厂实例
     * @return 
     */
    public static DataExchangeFactory getInstance() {
        return instance;
    }
    /**
     * 获取QP下载数据接口
     * @param mouduleId 模块ID
     * @return 
     */
    public DownloadDataFromEport getDownloadDataFormEport(String mouduleId){
        return getDataExchange(mouduleId, DownloadDataFromEport.class);
    }
    /**
     * 获取QP上传数据接口
     * @param mouduleId 模块ID
     * @return 
     */
    public UploadDataToEport getUploadDataToEport(String mouduleId){
        return getDataExchange(mouduleId, UploadDataToEport.class);
    }
    
    /**
     * 根据模块ID 获取数据交换接口实例
     * @param <T>
     * @param moduleId
     * @param interfClass 接口类型
     * @return 
     */
    private <T> T getDataExchange(String moduleId,Class<T> interfClass) {
        T exchange = (T)cacheMap.get(moduleId+interfClass);
        if (exchange == null) {
            synchronized (lock) {
                exchange = getProxyObject(moduleId,interfClass);
                if(exchange != null)
                    cacheMap.put(moduleId+interfClass, exchange);
            }
        }
        return (T)exchange;
    }
    /**
     * 获取接口代理实现
     * @param moduleId
     * @return 
     */
    private <T> T getProxyObject(String moduleId,Class<T> interfClass) {
        String className = config.getProperty(moduleId+".class");        
        try {
            Class<?> targetClass = getClassLoader().loadClass(className);
            Constructor constructor = targetClass.getConstructor();            
            Object target = constructor.newInstance();
            T proxy = (T)Proxy.newProxyInstance(classLoader,new Class[]{interfClass},new MethodInvocation(target));
            System.out.println("proxy instance : "+proxy);
            return proxy;
        } catch (Exception ex) {
            throw new RuntimeException("加载"+moduleId+"数据交换接口失败:"+className,ex);
        }
    }
    
    private URL[] getJarURLs(String jarUrls){
        String[] surls = jarUrls.split(",");        
        List<URL> urls = new ArrayList<URL>();
        for(String surl : surls){
            if(surl != null && !surl.trim().isEmpty()){
                try {
                    urls.add(new URL(surl));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(DataExchangeFactory.class.getName()).log(Level.SEVERE, null, ex);
                    throw new RuntimeException(ex);
                }
            }
        }
        
        return urls.toArray(new URL[0]);
    }
    
    /**
     * 获取ClassLoader
     * @return 
     */
    private URLClassLoader getClassLoader() {        
        if (classLoader == null) {
            String jarUrls = config.getProperty("jar.url");
            try {           
            	jarUrls += "?j="+System.currentTimeMillis();
                classLoader = new CustomURLClassLoader(getJarURLs(jarUrls), Thread.currentThread().getContextClassLoader());                
            } catch (Exception ex) {
                Logger.getLogger(DataExchangeFactory.class.getName()).log(Level.SEVERE, "无法加载QP 数据交换接口jar:" + jarUrls, ex);
                throw new RuntimeException(ex);
            }
        }
        return classLoader;
    }
}
