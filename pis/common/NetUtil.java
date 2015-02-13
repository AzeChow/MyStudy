package com.bestway.pis.common;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 *
 * @author kpc
 */
public class NetUtil {

    /**
     * 测试网络连接
     *
     * @param serverName
     * @param serverPort
     * @return
     */
    public static boolean testConnnect(String serverName, Integer serverPort) {
        Socket my = new Socket(); //实例化socket对象
        try {
            InetSocketAddress mySock = new InetSocketAddress(InetAddress.getByName(serverName), serverPort);//
            my.connect(mySock, 1000);//100毫秒超时
            return true;
        } catch (SocketTimeoutException e) {//捉到了!
            System.out.println("cannot connect ...");
        } catch (Exception e) {//其他有IOException等
            //other Exception do...
        }
        return false;
    }
}
