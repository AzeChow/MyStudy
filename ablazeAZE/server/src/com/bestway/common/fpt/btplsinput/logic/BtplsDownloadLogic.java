package com.bestway.common.fpt.btplsinput.logic;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;

import bsh.This;

import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.FptBusinessType;
import com.bestway.common.fpt.btplsinput.dao.BtplsDownloadDao;
import com.bestway.common.fpt.btplsinput.entity.CustomsDeclarationBodyTemp;
import com.bestway.common.fpt.btplsinput.entity.CustomsDeclarationHeadTemp;
import com.bestway.common.fpt.btplsinput.entity.GYSMessageIndentureMergerTemp;
import com.bestway.common.fpt.btplsinput.entity.GYSMessageIndentureTemp;
import com.bestway.common.fpt.btplsinput.entity.KHMessageIndentureMergerTemp;
import com.bestway.common.fpt.btplsinput.entity.KHMessageIndentureTemp;
import com.bestway.common.fpt.btplsinput.entity.MessageCustomsCoverBodyTemp;
import com.bestway.common.fpt.btplsinput.entity.MessageCustomsCoverHeadTemp;
import com.bestway.common.fpt.btplsinput.entity.MessageIndentureHeadTemp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.org.apache.bcel.internal.generic.NEW;

@SuppressWarnings("unchecked")
public class BtplsDownloadLogic {
	private BtplsDownloadDao btplsDownloadDao;

	public void setBtplsDownloadDao(BtplsDownloadDao btplsDownloadDao) {
		this.btplsDownloadDao = btplsDownloadDao;
	}
	public static void main(String[] args) {
		BtplsDownloadLogic logic = new BtplsDownloadLogic();
		System.out.println(logic.hello());
		
	}
	/**
	 * 查找报关单表头
	 * 
	 * @param condition
	 *            参数：查询条件
	 * @param condition
	 *            查询的参数值
	 * @return
	 */
	public static List<CustomsDeclarationHeadTemp> findCustomsDeclarationHead(
			HashMap<String, String> parameter, HashMap condition) {
		// 指定条件查询的参数值
		// HashMap<String, Object> condition = new HashMap<String, Object>();
		// condition.put(" a.companyCode = ? ", "00002");//转出代码
		// condition.put(" a.sendDate >= ? ", getBeginDate(new Date()));//申请日期
		// condition.put(" a.sendDate <= ? ", getBeginDate(new Date()));//申请日期
		// condition.put(" a.serialNumber = ? ", 123);//流水号

		String ip = parameter.get("ipAddre");
		String port = parameter.get("port");
		String companyCode = parameter.get("companyCode");// 企业海关编码
		String projectType = parameter.get("projectType");// 项目 1:电子账册 2:电子化手册
		String emsNo = parameter.get("emsNo");// 项目 1:电子账册 2:电子化手册
		GsonBuilder builder = new GsonBuilder();
		builder.serializeNulls();
		builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		Gson gson = builder.create();
		Type type = new TypeToken<HashMap<String, Object>>() {
		}.getType();

		Company c = (Company) CommonUtils.getCompany();

		String conditonmap = gson.toJson(condition, type);
		System.out.println(conditonmap);
		// Object[] opAddEntryArgs = new Object[] { conditonmap, '1' };
		Object[] opAddEntryArgs = new Object[] { conditonmap, companyCode,
				emsNo, projectType };// 参数：查询条件，企业海关编码，合同手册号，1:电子账册 2:电子化手册
		String methodName = "findCustomsDeclarationHead";
		// // 调用hello方法并输出该方法的返回值
		Object[] obj = invokeBtpls(ip, port, methodName, opAddEntryArgs);
		List<CustomsDeclarationHeadTemp> list = null;
		if (obj[0] != null) {
			String temp = obj[0].toString();
			// System.out.println("--------" + obj[0]);
			list = gson.fromJson(temp,
					new TypeToken<List<CustomsDeclarationHeadTemp>>() {
					}.getType());
		}

		return list;
	}

	/**
	 * 根据id查找报关单表体 projectType = 1代表电子账册、2电子化手册
	 * 
	 * @return
	 */
	public static Map<CustomsDeclarationHeadTemp, List<CustomsDeclarationBodyTemp>> findCustomsDeclarationBody(
			Map<String, Object> parameter) {
		String ip = (String) parameter.get("ipAddre");
		String port = (String) parameter.get("port");
		List idList = (List) parameter.get("idList");
		String projectType = (String) parameter.get("projectType");

		// 指定add方法的参数值
		GsonBuilder builder = new GsonBuilder();
		builder.serializeNulls();
		builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		Gson gson = builder.create();
		Type type = new TypeToken<List<String>>() {
		}.getType();

		String headids = gson.toJson(idList, type);

		// String headIds 表头id, String customCode 公司海关编码, String dsType,
		Company c = (Company) CommonUtils.getCompany();

		Object[] opAddEntryArgs = new Object[] { headids, "1111111111",
				projectType };

		// 远程调用
		Object[] obj = invokeBtpls(ip, port, "findCustomsDeclarationBody",
				opAddEntryArgs);

		Map<String, List<CustomsDeclarationBodyTemp>> map = null;
		if (obj[0] != null) {
			String temp = obj[0].toString();
			map = gson
					.fromJson(
							temp,
							new TypeToken<Map<String, List<CustomsDeclarationBodyTemp>>>() {
							}.getType());
		}

		Map<CustomsDeclarationHeadTemp, List<CustomsDeclarationBodyTemp>> maps = new HashMap<CustomsDeclarationHeadTemp, List<CustomsDeclarationBodyTemp>>();
		if (map != null && !map.isEmpty()) {
			Iterator it = map.keySet().iterator();
			while (it.hasNext()) {
				String temp = it.next().toString();
				List<CustomsDeclarationBodyTemp> list = map.get(temp);
				CustomsDeclarationHeadTemp htemp = gson.fromJson(temp,
						CustomsDeclarationHeadTemp.class);
				maps.put(htemp, list);
			}
		}

		return maps;
	}

	/**
	 * 调用webService方法
	 * 
	 * @param serverIP
	 *            ip地址
	 * @param serverPort
	 *            端口
	 * @param methodName
	 *            方法名
	 * @param parameters
	 *            参数
	 * @param classes
	 *            返回值类型
	 * @return
	 */
	public static Object[] invokeBtpls(String IP, String Port,
			String methodName, Object[] parameters) {

		String serverIP = IP;
		String serverPort = Port;
		if (serverIP == null || "".equals(serverIP)) {
			return null;
		}
		if (serverPort == null || "".equals(serverPort)) {
			return null;
		}
		try {
			String urlPath = "http://" + serverIP + ":" + serverPort+ "/btpls-ejb/BtplsWS";
			// 使用RPC方式调用WebService
			RPCServiceClient serviceClient = new RPCServiceClient();
			serviceClient.getOptions()
					.setProperty(HTTPConstants.CHUNKED, false);
			Options options = serviceClient.getOptions();
			// 指定调用WebService的URL
			EndpointReference targetEPR = new EndpointReference(urlPath);
			options.setTo(targetEPR);
			// 指定add方法返回值的数据类型的Class对象
			Class[] clazz = new Class[] { String[].class };
			QName opAddEntry = new QName(
					"http://service.webservice.btpls.bsw.com.cn/", methodName);
			Object[] objs = (Object[]) serviceClient.invokeBlocking(opAddEntry,
					parameters, clazz)[0];
			return objs;
		} catch (Exception e) {
			// throw new AppException("连接不上服务器!", e);
		}
		return null;
	}

	/**
	 * 获得这一天的开始日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getBeginDate(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获得这一天的结束日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getEndDate(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}
	/**
	 * 查找海关申请表表头
	 * 
	 * @return
	 */
	public static List<MessageCustomsCoverHeadTemp> findMessageCustomsCoverHead(HashMap<String, Object> parameter, HashMap condition) {
		// 指定条件查询的参数值

		GsonBuilder builder = new GsonBuilder();
		builder.serializeNulls();
		builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		Gson gson = builder.create();
		Type type = new TypeToken<HashMap<String, Object>>() {
		}.getType();		
		String conditonmap = gson.toJson(condition, type);		
		String port =(String) parameter.get("port");
		String ipAddr =(String) parameter.get("ipAddre");
		String comCode =(String) parameter.get("companyCode");
		String projectType =(String) parameter.get("projectType");
		
		Object[] opAddEntryArgs = new Object[] { conditonmap, comCode, projectType };// 参数：查询条件，企业海关编码，1:电子账册  2:电子化手册
		String methodName = "findMessageCustomsCoverHeadToJBCUS";
		Object[] obj = invokeBtpls(ipAddr, port, methodName,opAddEntryArgs);

		List<MessageCustomsCoverHeadTemp> list = new ArrayList<MessageCustomsCoverHeadTemp>();
	
		if (obj[0] != null) {
			String temp = obj[0].toString();
			list = gson.fromJson(temp,
					new TypeToken<List<MessageCustomsCoverHeadTemp>>() {
					}.getType());
		}else {
			
		}
		return list;
	}

	/**
	 * 根据条件下载申请单表体
	 * 
	 * @return
	 */
	public static Map<MessageCustomsCoverHeadTemp, List<MessageCustomsCoverBodyTemp>> findMessageCustomsCoverBody(Map<String, Object> parameter) {
		// 指定add方法的参数值
		GsonBuilder builder = new GsonBuilder();
		builder.serializeNulls();
		builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		Gson gson = builder.create();
		Type type = new TypeToken<List<String>>() {
		}.getType();
		List<String> idList = (List<String>)parameter.get("idList");
		String headids = gson.toJson(idList, type);
		String port =(String) parameter.get("port");
		String ipAddr =(String) parameter.get("ipAddre");
		String comCode =(String) parameter.get("companyCode");
		String projectType =(String) parameter.get("projectType");
		
		
		// String headIds 表头id, String customsCode 海关编码, String dsType,
		Object[] opAddEntryArgs = new Object[] { headids, comCode,projectType };
		String methodName = "findMessageCustomsCoverBodyToJBCUS";
		Object[] obj = invokeBtpls(ipAddr,port,methodName,opAddEntryArgs);
		Map<String, List<MessageCustomsCoverBodyTemp>> map = null;
		if (obj[0] != null) {
			String temp = obj[0].toString();
			map = gson
					.fromJson(
							temp,
							new TypeToken<Map<String, List<MessageCustomsCoverBodyTemp>>>() {
							}.getType());
		}

		Map<MessageCustomsCoverHeadTemp, List<MessageCustomsCoverBodyTemp>> maps = new HashMap<MessageCustomsCoverHeadTemp, List<MessageCustomsCoverBodyTemp>>();
		if (map != null && !map.isEmpty()) {
			Iterator it = map.keySet().iterator();
			while (it.hasNext()) {
				String temp = it.next().toString();
				List<MessageCustomsCoverBodyTemp> list = map.get(temp);
				MessageCustomsCoverHeadTemp htemp = gson.fromJson(temp,
						MessageCustomsCoverHeadTemp.class);
				maps.put(htemp, list);
			}
		}

		return maps;
	}
	 /**
     * 查找海关收发货表头
     *
     * @return
     */
    public static List<MessageIndentureHeadTemp> findMessageIndentureHead(HashMap<String, Object> p,HashMap h ) {
        HashMap<String, Object> condition = new HashMap<String, Object>();
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        Gson gson = builder.create();
        Type type = new TypeToken<HashMap<String, Object>>() {}.getType();
        Integer index = (Integer)p.get("selectIndex");
        condition.put(" messageIndentureType =  ?", index);
        
        String conditonmap = gson.toJson(condition, type);
        String ipAddre = (String) p.get("ipAddre");
        String port = (String) p.get("port");
        String companyCode = (String) p.get("companyCode");
      

        Object[] opAddEntryArgs = new Object[]{conditonmap, companyCode};//参数：查询条件，企业海关编码
        String methodName = "findMessageIndentureHeadToJBCUS";
        Object[] obj = invokeBtpls(ipAddre,port,methodName, opAddEntryArgs);
        
        List<MessageIndentureHeadTemp> list = null;
        if (obj[0] != null || !obj[0].toString().equals("")) {
            String temp = obj[0].toString();
            list = gson.fromJson(temp, new TypeToken<List<MessageIndentureHeadTemp>>() {}.getType());
        }
        return list;
    }
    /**
     * 根据id查找海关收发货申报表体
     *
     * @return
     */
    public static Map<MessageIndentureHeadTemp, List<List>> findMessageIndentureBody(Map<String, Object> pMap) {
        Map<MessageIndentureHeadTemp, List<List>> returnmap = new HashMap<MessageIndentureHeadTemp, List<List>>();
        Map<String, List<List>> tmap = new HashMap<String, List<List>>();
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        Gson gson = builder.create();
        Type type = new TypeToken<List<String>>() {
        }.getType();
        
        List idlist = (List) pMap.get("idList");
        String comCode = (String)pMap.get("companyCode");
        String port = (String)pMap.get("port");
        String ipAddr = (String)pMap.get("ipAddre");        
        String headids = gson.toJson(idlist, type);
        Integer index = (Integer)pMap.get("selectIndex");
        
        Object[] opAddEntryArgs = new Object[]{headids, comCode};
        String methodName ="";
        Object[] obj = null;
        Map<String, List<GYSMessageIndentureTemp>> gmap = new HashMap<String, List<GYSMessageIndentureTemp>>();
        Map<String, List<KHMessageIndentureTemp>> kmap = new HashMap<String, List<KHMessageIndentureTemp>>();
        
      //发退货商品明细表体
//        if(index==0){
	        methodName = "findGYSMessageIndentureToJBCUS";
	        obj = invokeBtpls(ipAddr,port,methodName, opAddEntryArgs); 
	        if (obj[0] != null || !obj[0].toString().equals("")) {
	            String temp = obj[0].toString();
	            gmap = gson.fromJson(temp, new TypeToken<Map<String, List<GYSMessageIndentureTemp>>>() {
	            }.getType());
	        }
//        }else if(index==1){ //收退货商品明细表体
	        methodName = "findKHMessageIndentureToJBCUS";
	        obj = invokeBtpls(ipAddr,port,methodName, opAddEntryArgs);
	        if (obj[0] != null || !obj[0].toString().equals("")) {
	            String temp = obj[0].toString();
	            kmap = gson.fromJson(temp, new TypeToken<Map<String, List<KHMessageIndentureTemp>>>() {
	            }.getType());
	        } 
//        }
	    
        if(gmap !=null){
	        Iterator it = gmap.keySet().iterator();
	          while (it.hasNext()) {
	          String key = it.next().toString();
	          List alist = new ArrayList();
//	          MessageIndentureHeadTemp htemp = gson.fromJson(key, MessageIndentureHeadTemp.class);
	          if(gmap!=null){
	        	  alist.add(gmap.get(key));
	          }	         	          
	          tmap.put(key, alist);
	        }
        }
        if(kmap !=null){
	        Iterator it = kmap.keySet().iterator();
	          while (it.hasNext()) {
	          String key = it.next().toString();
	          List alist = new ArrayList();
//	          MessageIndentureHeadTemp htemp = gson.fromJson(key, MessageIndentureHeadTemp.class);
	          alist = tmap.get(key);
	          if(kmap !=null){
	        	  alist.add(kmap.get(key));
	          }	          
//	          returnmap.put(htemp, alist);
	        }
        }
        Iterator its = tmap.keySet().iterator();
        while(its.hasNext()){
        	String key = its.next().toString();
        	MessageIndentureHeadTemp htemp = gson.fromJson(key, MessageIndentureHeadTemp.class);
        	returnmap.put(htemp, tmap.get(key));
        }
        return returnmap;
    }

    /******************************************************下载**********************************************************/
    
    /**
	 * 报关单下载
	 * @param parameter
	 * @return
	 */
	public List downloadCustomsDeclaration(Map<String, Object> parameter) {
		String projectType = (String) parameter.get("projectType");
		Map<CustomsDeclarationHeadTemp, List<CustomsDeclarationBodyTemp>> map = findCustomsDeclarationBody(parameter);

		return this.btplsDownloadDao.saveCustomsDeclaration(map,
				Integer.valueOf(projectType));
	}
	/**
	 * 申请表下载
	 * @param paramerter
	 * @return
	 */
	public List downloadBtplsFptAppBody(Map<String, Object> paramerter){
		Map<MessageCustomsCoverHeadTemp, List<MessageCustomsCoverBodyTemp>> bodyMap = findMessageCustomsCoverBody(paramerter);
		
		return this.btplsDownloadDao.saveBtplsCustomsCoverBody(bodyMap,paramerter);
	}
	/**
	 * 收发货下载
	 * @param paramerter
	 * @return
	 */
	public List downloadMessageIndentureBody(Map<String, Object> paramerter){
		
		Map<MessageIndentureHeadTemp, List<List>> bodyMap = findMessageIndentureBody(paramerter);
		
		return this.btplsDownloadDao.saveBtplsIndentureBody(bodyMap,paramerter);
		
	}
	

    //远程调用测试
	public static String hello() {
		Object[] opAddEntryArgs = new Object[] { "4419940A93", '1' };//阿龙 4419940A93      志江：4419001001
		String methodName = "hello";
		Object[] obj = invokeBtpls("192.168.2.78", "8080",methodName, opAddEntryArgs);
		return obj[0].toString();
	}
}
