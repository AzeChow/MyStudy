/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bsw.core.client.eport;

import java.util.Map;

/**
 * 上传数据到QP接口
 * @author xc
 */
public interface UploadDataToEport{
     /**
     * 导入数据 （数据导入QP）
     * @param params 导入条件 
     * @param jsonData 导入数据 数据格式:JSON
     */
    public String upload(String jsonData,Map<String,Object> params);
}
