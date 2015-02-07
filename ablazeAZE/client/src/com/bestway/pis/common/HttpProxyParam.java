package com.bestway.pis.common;

/**
*
* @author kpc
*/
public class HttpProxyParam {

   private String proxyServerAddress;
   private Integer proxyPort;
   private String proxyUserName;
   private String proxyPwd;

   public HttpProxyParam(String proxyServerAddress, Integer proxyPort, String proxyUserName, String proxyPwd) {
       this.proxyServerAddress = proxyServerAddress;
       this.proxyPort = proxyPort;
       this.proxyUserName = proxyUserName;
       this.proxyPwd = proxyPwd;
   }

   public String getProxyServerAddress() {
       return proxyServerAddress;
   }

   public void setProxyServerAddress(String proxyServerAddress) {
       this.proxyServerAddress = proxyServerAddress;
   }

   public Integer getProxyPort() {
       return proxyPort;
   }

   public void setProxyPort(Integer proxyPort) {
       this.proxyPort = proxyPort;
   }

   public String getProxyUserName() {
       return proxyUserName;
   }

   public void setProxyUserName(String proxyUserName) {
       this.proxyUserName = proxyUserName;
   }

   public String getProxyPwd() {
       return proxyPwd;
   }

   public void setProxyPwd(String proxyPwd) {
       this.proxyPwd = proxyPwd;
   }
}
