/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bsw.core.client.eport;

import java.util.Map;

/**
 * 从QP下载数据
 * @author xc
 */
public interface DownloadDataFromEport {
    /**
     * 导出数据 (从QP中下载数据)
     * @param params 导出条件
     * @return 导出数据 数据格式:JSON
     */
    public String download(Map<String,Object> params);

    /**
     * 测试连接
     * @return 
     */
    public boolean testConnect();
}
