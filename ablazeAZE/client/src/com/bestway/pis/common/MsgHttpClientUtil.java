package com.bestway.pis.common;

import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author kpc
 */
public class MsgHttpClientUtil {

    public static void main(String[] args) {
        long beginTime = System.currentTimeMillis();
        int count = 1;
        for (int i = 0; i < count; i++) {
            /**
             * 列出文件名和下载文件
             */
            List<String> listDownloadResult = MsgHttpClientUtil.listFiles("webftp.bsw.com.cn", "update", "online", "/CustomsBaseData", null);
            for (String fileName : listDownloadResult) {
                MsgHttpClientUtil.download("webftp.bsw.com.cn", "update", "online", "/CustomsBaseData", fileName, "E:\\CustomsBaseData", null);
            }
            System.out.println("-----download file count:" + listDownloadResult.size());
            /**
             * 上传文件
             */
            File localDir = new File("E:\\CustomsBaseData");
            File[] files = localDir.listFiles();
            for (File file : files) {
                MsgHttpClientUtil.upload("webftp.bsw.com.cn", "bs0000", "bs0000", "/Upload", file.getName(), "E:\\CustomsBaseData", null);
            }
            /**
             * 删除本地文件
             */
            File localDeleteDir = new File("E:\\CustomsBaseData");
            File[] deleteFiles = localDeleteDir.listFiles();
            for (File file : deleteFiles) {
                try {
                    FileUtils.forceDelete(file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            /**
             * 删除服务器文件
             */
            List<String> listDeleteResult = MsgHttpClientUtil.listFiles("webftp.bsw.com.cn", "bs0000", "bs0000", "/Upload", null);
            System.out.println("-----delete file count:" + listDeleteResult.size());
            for (String fileName : listDeleteResult) {
                MsgHttpClientUtil.delete("webftp.bsw.com.cn", "bs0000", "bs0000", "/Upload", fileName, null);
            }
        }
        long endTime = System.currentTimeMillis();
        double totalTime = (endTime - beginTime) / 1000.0;
        double perTime = totalTime / count;
        System.out.println("共用时：" + totalTime + "秒，每次" + perTime + "秒");
    }

    /**
     * 获取服务器目录中所有文件列表
     *
     * @param serverName 服务器端地址
     * @param userName 用户名
     * @param pwd 密码
     * @param dir 服务器目录
     * @param proxyParam 代理设置
     * @return
     */
    public static List listFiles(String serverName, String userName, String pwd, String dir, HttpProxyParam proxyParam) {
        List listResult = new ArrayList();
        String url = "http://" + serverName + "/list";
        Map<String, String> params = new HashMap();
        params.put("username", userName);
        params.put("password", pwd);
        params.put("dir", dir);
        HttpClientInvoker httpClientInvoker = new HttpClientInvoker();
        String responseContent = httpClientInvoker.executeMethod(url, params, proxyParam);//readContentFromResponseStream(postMethod);//sb.toString();
        Gson gson = new Gson();
        Map resultMap = gson.fromJson(responseContent, Map.class);
        if (resultMap.isEmpty() || resultMap.get("errcode") == null || resultMap.get("msg") == null) {
            throw new RuntimeException("返回结果为空");
        }
        Integer errcode = Double.valueOf(resultMap.get("errcode").toString().trim()).intValue();
        if (errcode == 0) {
            if (!(resultMap.get("msg") instanceof List)) {
                throw new RuntimeException("返回结果类型错误");
            }
            listResult = (List) resultMap.get("msg");
        } else {
            String errormsg = (String) resultMap.get("msg");
            throw new RuntimeException("errorcode:" + errcode + ";msg:" + errormsg);
        }
        return listResult;
    }

    /**
     * 从服务器上下载文件
     *
     * @param serverName
     * @param userName
     * @param pwd
     * @param dir
     * @param fileName 文件名
     * @param localDir 本地地址
     * @param proxyParam
     */
    public static void download(String serverName, String userName, String pwd, String dir, String fileName, String localDir, HttpProxyParam proxyParam) {
        String url = "http://" + serverName + "/download";
        try {
        Map<String, String> params = new HashMap();
        params.put("username", userName);
        params.put("password", pwd);
        params.put("dir", dir);
        params.put("file", fileName);
        HttpClientInvoker httpClientInvoker = new HttpClientInvoker();
        String responseContent = httpClientInvoker.executeMethod(url, params, proxyParam);
            Gson gson = new Gson();
            Map resultMap = gson.fromJson(responseContent, Map.class);
            if (resultMap.isEmpty() || resultMap.get("errcode") == null || resultMap.get("msg") == null) {
                throw new RuntimeException("返回结果为空");
            }
            Integer errcode = Double.valueOf(resultMap.get("errcode").toString().trim()).intValue();
            if (errcode != 0) {
                String errormsg = (String) resultMap.get("msg");
                throw new RuntimeException("errorcode:" + errcode + ";msg:" + errormsg);
            }
            if (!(resultMap.get("msg") instanceof Map)) {
                throw new RuntimeException("返回结果类型错误");
            }
            Map mapContent = (Map) resultMap.get("msg");
            String md5 = (String) mapContent.get("md5");
            String fileContent = (String) mapContent.get("content");
            if (fileContent == null || "".equals(fileContent.trim())) {
                throw new RuntimeException("返回文件：" + fileName + "内容为空。");
            }
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(fileContent);
            String localmd5 = DigestUtils.md5Hex(bytes);
            if (!localmd5.equals(md5)) {
                throw new RuntimeException("下载文件内容不完整");
            }
            File file = new File(localDir + File.separator + fileName);
            FileUtils.writeByteArrayToFile(file, bytes);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } 
    }

    /**
     * 从服务器上删除文件
     *
     * @param serverName
     * @param userName
     * @param pwd
     * @param dir
     * @param fileName
     * @param proxyParam
     */
    public static void delete(String serverName, String userName, String pwd, String dir, String fileName, HttpProxyParam proxyParam) {
        String url = "http://" + serverName + "/delete";
              Map<String, String> params = new HashMap();
        params.put("username", userName);
        params.put("password", pwd);
        params.put("dir", dir);
        params.put("file", fileName);
        HttpClientInvoker httpClientInvoker = new HttpClientInvoker();
        String responseContent = httpClientInvoker.executeMethod(url, params, proxyParam);
            Gson gson = new Gson();
            Map resultMap = gson.fromJson(responseContent, Map.class);
            if (resultMap.isEmpty() || resultMap.get("errcode") == null || resultMap.get("msg") == null) {
                throw new RuntimeException("返回结果为空");
            }
            Integer errcode = Double.valueOf(resultMap.get("errcode").toString().trim()).intValue();
            if (errcode != 0) {
                String errormsg = (String) resultMap.get("msg");
                throw new RuntimeException("errorcode:" + errcode + ";msg:" + errormsg);
            }

    }

    /**
     * 向服务器上上传文件
     *
     * @param serverName
     * @param userName
     * @param pwd
     * @param dir
     * @param fileName
     * @param localDir
     * @param proxyParam
     */
    public static void upload(String serverName, String userName, String pwd, String dir, String fileName, String localDir, HttpProxyParam proxyParam) {
        String url = "http://" + serverName + "/upload";
        BASE64Encoder encoder = new BASE64Encoder();
        File file = new File(localDir + File.separator + fileName);
        byte[] bytes = null;
        try {
            bytes = FileUtils.readFileToByteArray(file);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        String md5 = DigestUtils.md5Hex(bytes);
        String fileContent = encoder.encode(bytes);
        Map<String, String> params = new HashMap();
        params.put("username", userName);
        params.put("password", pwd);
        params.put("dir", dir);
        params.put("file", fileName);
        params.put("md5", md5);
        params.put("content", fileContent);
        HttpClientInvoker httpClientInvoker = new HttpClientInvoker();
        String responseContent = httpClientInvoker.executeMethod(url, params, proxyParam);
            Gson gson = new Gson();
            Map resultMap = gson.fromJson(responseContent, Map.class);
            if (resultMap.isEmpty() || resultMap.get("errcode") == null || resultMap.get("msg") == null) {
                throw new RuntimeException("返回结果为空");
            }
            Integer errcode = Double.valueOf(resultMap.get("errcode").toString().trim()).intValue();
            if (errcode != 0) {
                String errormsg = (String) resultMap.get("msg");
                throw new RuntimeException("errorcode:" + errcode + ";msg:" + errormsg);
            }

    }

    public static HttpProxyParam getHttpProxyParam(String proxyServerAddress, Integer proxyPort, String proxyUserName, String proxyPwd) {
        return new HttpProxyParam(proxyServerAddress, proxyPort, proxyUserName, proxyPwd);
    }
}
