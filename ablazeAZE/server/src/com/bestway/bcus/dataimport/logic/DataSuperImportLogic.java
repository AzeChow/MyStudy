package com.bestway.bcus.dataimport.logic;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestway.bcus.dataimport.dao.DataImportDao;
import com.bestway.bcus.dataimport.entity.SuperClassList;
import com.bestway.bcus.dataimport.entity.SuperFieldList;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DataSuperImportLogic {
	private DataImportDao	dataImportDao	= null;
	
	private static Log		logger			= LogFactory
													.getLog(DataSuperImportLogic.class);
	private Hashtable allHs = new Hashtable();

	/**
	 * @return Returns the dataImportDao.
	 */
	public DataImportDao getDataImportDao() {
		return dataImportDao;
	}

	/**
	 * @param dataImportDao
	 *            The dataImportDao to set.
	 */
	public void setDataImportDao(DataImportDao dataImportDao) {
		this.dataImportDao = dataImportDao;
	}

	
	
	public SuperClassList saveSuperFieldList(List fieldList,SuperClassList obj, boolean isAdd){
		if (isAdd){
			List ls = this.dataImportDao.findMaxSuperClassList();
			if (ls.size() <= 0 || ls.get(0) == null) {
				obj.setSeqNum(1);
			} else {
				String seqNums = ls.get(0).toString().trim();
				obj.setSeqNum(Integer.parseInt(seqNums)+1);
			}		
			obj.setCompany(CommonUtils.getCompany());
		}
		
		this.dataImportDao.saveSuperClassList(obj);	
		
		for (int i=0;i<fieldList.size();i++){
		   SuperFieldList field = (SuperFieldList) fieldList.get(i);
		   if (field.getIsSelected() != null && field.getIsSelected().equals(Boolean.valueOf(true))){//删除
			   this.dataImportDao.delete(field);
		   } else {
			   field.setSuperClassList(obj);
			   field.setCompany(CommonUtils.getCompany());			   
			   this.dataImportDao.saveSuperFieldList(field);
		   }
		}
		return obj;
		 
	}
	
	public void deleteSuperClassList(List ls){
		for (int i=0;i<ls.size();i++){
			SuperClassList obj = (SuperClassList) ls.get(i);
			List list = this.dataImportDao.findSuperFieldListByClass(obj);
			this.dataImportDao.deleteAll(list);
			this.dataImportDao.delete(obj);
		}
		
	}
	
	private String getFileColumnValue(String[] fileRow, int dataIndex) {
		if (dataIndex > fileRow.length - 1) {
			return null;
		}
		return fileRow[dataIndex];
	}
	
	private Hashtable getFieldHs(Hashtable <Integer,SuperFieldList>hs,SuperClassList classList){
		List fieldList = dataImportDao.findSuperFieldListByClass(classList);
		for (int i=0;i<fieldList.size();i++){
			SuperFieldList obj = (SuperFieldList) fieldList.get(i);
			if (hs.get(obj.getSeqNum()) == null){
				hs.put(obj.getSeqNum(),obj);
			}			
		}
		return hs;
	}
	
	//执行导入资料
	public List importData(List<List> lines, SuperClassList classList){
		allHs.clear();
		//最大流水号
		List ls = this.dataImportDao.findMaxSuperFieldList(classList);
		String seqNums = ls.get(0).toString().trim();		
		int maxSeq = Integer.parseInt(seqNums);
		Hashtable <Integer,SuperFieldList>fieldHs = new Hashtable<Integer,SuperFieldList>();
		fieldHs = getFieldHs(fieldHs,classList);
		List allList = new ArrayList();
		List objList = new ArrayList();
		String allError = "";		
		boolean isEdit = false;
		SuperFieldList onlyObj = null;
		if (classList.getImportSelect().equals(Integer.valueOf(0))){//更新导入
			isEdit = true;
			onlyObj = this.dataImportDao.getSuperField(classList);
			
		}
		for (int i = 0; i < lines.size(); i++) {//循环行
			String error = "";
			List line = lines.get(i);
			String[] strs = new String[line.size()];
			for (int j = 0; j < line.size(); j++) {
				Object obj = line.get(j);
				strs[j] = (obj == null ? "" : obj.toString());
			}
			//开始填充数据
			try{
				Class cls = Class.forName(classList.getClassName());
				Object obj = null;
				
				if (isEdit){//更新导入
					String value = getFileColumnValue(strs, onlyObj.getSeqNum()-1);
					obj = getObjectValue(classList.getClassName(),onlyObj.getFieldname(), value);
					if (obj == null){//没查找到
						obj = cls.newInstance();
					}
				} else {
					obj = cls.newInstance();
				}
				for (int j = 0; j < maxSeq; j++) {
					String value = getFileColumnValue(strs, j);
					if (fieldHs.get(Integer.valueOf(j+1)) != null){
						SuperFieldList fieldObj = (SuperFieldList) fieldHs.get(Integer.valueOf(j+1));
						Object objValue = value;
						//判断类型
						if (value != null && !value.equals("")){
							objValue = getType(value,fieldObj.getFieldtype());
							if (objValue == null){
								error = error + "第"+String.valueOf(j+1)+"列数据["+value+"]类型不正确，";
								continue;
							}
						}
						//检查是否为空
						if (fieldObj.getIsNullValue() != null && fieldObj.getIsNullValue().equals(Boolean.valueOf(false)) && (value == null || value.equals(""))){
							error = error + "第"+String.valueOf(j+1)+"列数据不能为空，";
							continue;
						}
						//格式值
						if (fieldObj.getFieldtype().equals("对象") && value != null && !value.equals("") ){
							objValue = getObjectValue(fieldObj.getChildClassName(),fieldObj.getChildFieldName(),value);
							System.out.println("得到的值："+objValue);
							if (objValue == null){
								error = error + "第"+String.valueOf(j+1)+"列数据["+value+"]查找对象为空，";
								continue;
							}
							
						}
						
						
						
						BeanUtils.setProperty(obj, fieldObj.getFieldname(),objValue);
					}					
				}
				//自动添加公司
				if (obj instanceof BaseScmEntity){
					BeanUtils.setProperty(obj, "company", CommonUtils.getCompany());
				}
				objList.add(obj);
				if (!error.equals("")){
					error = "第"+String.valueOf(i+1)+"行:" + error;
					allError = allError + error+"\n";	
				}
				
			} catch (Exception e){
				
			}		
		}
		allList.add(objList);
		allList.add(allError);
		return allList;
		
	}
	
 
 private Object getObjectValue(String childClassName,String childFieldName,String value){
	 if (allHs.get(childClassName) != null){
		 Hashtable objHs = (Hashtable) allHs.get(childClassName);
		 if (objHs.get(value) != null){
			 return objHs.get(value);
		 }
	 } else {
		 Hashtable objHs = new Hashtable();
		 List ls = this.dataImportDao.findCommonObj(childClassName);
		 for (int i=0;i<ls.size();i++){			 
			 try {
				Object obj = ls.get(i);				
				String key = BeanUtils.getProperty(obj,childFieldName);
				objHs.put(key,obj);				
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
		 }
		 allHs.put(childClassName,objHs);
		 if (objHs.get(value) != null){
			 return objHs.get(value);
		 }		
		
	 }
	 return null;
 }
	
  private Object getType(String value,String type){
      Object obj = null;    	  
	  try{
		  if (type.equals("String") || type.equals("对象")){
			  obj = value;
		  } else if  (type.equals("Integer")){
		      obj = Integer.valueOf(value);
		  } else if (type.equals("Double") || type.equals("double")) {
			  obj = Double.valueOf(value);
		  } else if (type.equals("Boolean")){
			  obj = Boolean.valueOf(value);
		  } else if (type.equals("Date")){
			  if(value.indexOf("/") > -1){
				 value = value.toString().replaceAll("/", "-");
			  }
			  obj = java.sql.Date.valueOf(value);
		  } 
	  } catch (Exception e){
		  return obj;
	  }
	  return obj;
  }
	
  public void saveImportData(List objList){
	  for (int i=0;i<objList.size();i++){
		  Object obj = objList.get(i);
		  this.dataImportDao.saveSuperObj(obj);
	  }
  }
	
	
}
