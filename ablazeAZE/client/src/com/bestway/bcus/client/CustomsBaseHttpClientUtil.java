package com.bestway.bcus.client;

import java.util.List;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.common.HttpClientUtil;
import com.bestway.common.Request;

public class CustomsBaseHttpClientUtil {


    private static SystemAction systemAction = null;

    static {
    	systemAction = (SystemAction) CommonVars.getApplicationContext()
				.getBean("systemAction");
    }

    /**
     * 测试连接
     *
     * @return
     */
    public static boolean testConnect() {
        try {
            listFiles();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 读取目录文件列表
     *
     * @param dir
     * @return
     */
    public static List listFiles() {
        String serverName = "webftp.bsw.com.cn";
        String userName = "update";
        String pwd = "online";
        String dir = "/CustomsBaseData";
        HttpClientUtil.HttpProxyParam proxyParam = null;
        CompanyOther p = getCompanyOther();
        if (p.getProxyAddress() != null && !"".equals(p.getProxyAddress().trim())) {
            proxyParam = HttpClientUtil.getHttpProxyParam(p.getProxyAddress(), p.getProxyPort(), p.getProxyUserName(), p.getProxyPwd());
        }
        return HttpClientUtil.listFiles(serverName, userName, pwd, dir, proxyParam);
    }

    /**
     * 下载文件
     *
     * @param dir
     * @param fileName
     * @param localDir
     */
    public static void download(String fileName, String localDir) {
        String serverName = "webftp.bsw.com.cn";
        String userName = "update";
        String pwd = "online";
        String dir = "/CustomsBaseData";
        HttpClientUtil.HttpProxyParam proxyParam = null;
        CompanyOther p = getCompanyOther();
        if (p.getProxyAddress() != null && !"".equals(p.getProxyAddress().trim())) {
            proxyParam = HttpClientUtil.getHttpProxyParam(p.getProxyAddress(), p.getProxyPort(), p.getProxyUserName(), p.getProxyPwd());
        }
        HttpClientUtil.download(serverName, userName, pwd, dir, fileName, localDir, proxyParam);
    }

    private static CompanyOther getCompanyOther() {
        List list= systemAction.findCompanyOther(new Request(
				CommonVars.getCurrUser()));
        if(list.isEmpty()){
        	throw new RuntimeException("请先进行系统参数设置。");
        }
        CompanyOther p = (CompanyOther)list.get(0);
        if(null == p.getHttpAddress()){
        	throw new RuntimeException("请先进行系统参数设置 -> 其他设置 -> 服务器。");
        }
        if(null == p.getHttpTcsPwd()){
        	throw new RuntimeException("请先进行系统参数设置 -> 其他设置 -> 用户密码。");
        }
        if(null == p.getHttpTcsUserName()){
        	throw new RuntimeException("请先进行系统参数设置 -> 其他设置 -> 用户名。");
        }
        return p;
    }
    
    


}
