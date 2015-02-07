/*
 * Created on 2005-3-28
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataimport;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.bcus.dataimport.entity.ClassList;
import com.bestway.bcus.dataimport.entity.FieldList;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.common.tools.entity.TempNodeItem;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DataImportCommonQuery {
	private static DataImportCommonQuery	bcsQuery	= null;

	public synchronized static DataImportCommonQuery getInstance() {
		if (bcsQuery == null) {
			bcsQuery = new DataImportCommonQuery();
		}
		return bcsQuery;
	}

	/**
	 * 获得预警项目
	 * 
	 * @param existItem
	 * @return
	 */
	public List<TempNodeItem> getClassEnity() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("类名", "name", 150));
		list.add(new JTableListColumn("类名", "className", 255));
		list.add(new JTableListColumn("中文名", "cnName", 255));

		DgCommonQuery.setTableColumns(list);
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		ToolsAction toolsAction = (ToolsAction) CommonVars
				.getApplicationContext().getBean("toolsAction");

		List<TempNodeItem> dataSource = toolsAction.getTempNodeItems();
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("选择实体名");
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	public Map<String, String>	classMap	= null;

	public String getClassByKey(String key) {
		if (classMap == null) {
			classMap = new HashMap<String, String>();

			ToolsAction toolsAction = (ToolsAction) CommonVars
					.getApplicationContext().getBean("toolsAction");
			List<TempNodeItem> dataSource = toolsAction.getTempNodeItems();
			for(TempNodeItem temp : dataSource){
				classMap.put(temp.getName(), temp.getClassName());
			}
		}
		String className = classMap.get(key) ;
		if(className != null){
			return className;
		}
		return key;
	}
	
	
	public List<FieldList> getFieldListByClass(ClassList classList ){	
		String className = classList.getClassPath();
		className = DataImportCommonQuery.getInstance()
				.getClassByKey(className);
		List <FieldList> dataSource = new ArrayList<FieldList>();
		try {
			PropertyDescriptor[] props = PropertyUtils
					.getPropertyDescriptors(Class.forName(className));
			
			for (int i = 0; i < props.length; i++) {
				String tempField = props[i].getName();
				if (tempField.equals("class")) {
					continue;
				}
				Class clazz = props[i].getPropertyType();
			    FieldList f = new FieldList();
			    f.setClassList(classList);
			    f.setFieldname(tempField);
			    f.setFielddesc(tempField);
			    f.setName(tempField);
			    f.setIskey(false);
			    f.setIsNull(false);
			    //
			    // 这个暂时不做修改，只有先这样了．
			    //
			    if(clazz.getName().indexOf("String")>-1){
			    	f.setFieldtype("String");
			    	f.setFieldlen(255);
			    }else if(clazz.getName().indexOf("Double")>-1){
			    	f.setFieldtype("Double");
			    }else if(clazz.getName().indexOf("double")>-1){
			    	f.setFieldtype("double");
			    }else if(clazz.getName().indexOf("Integer")>-1){
			    	f.setFieldtype("Integer");
			    }else if(clazz.getName().indexOf("Boolean")>-1){
			    	f.setFieldtype("Boolean");
			    }else if(clazz.getName().indexOf("Date")>-1){
			    	f.setFieldtype("Date");
			    }else {
			    	f.setFieldtype("对象");
			    }			    
			    dataSource.add(f);
			    
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		List<JTableListColumn> list = new Vector<JTableListColumn>();		
		list.add(new JTableListColumn("中文名称", "name", 100));
		list.add(new JTableListColumn("字段名称", "fieldname", 100));
		list.add(new JTableListColumn("字段类型", "fieldtype", 100));
		list.add(new JTableListColumn("字段说明", "fielddesc", 100));
		list.add(new JTableListColumn("是否主键", "iskey", 80));
		list.add(new JTableListColumn("是否为空", "isNull", 80));
		
		
		DgCommonQuery.setTableColumns(list);		
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("选择字段名");
		dgCommonQuery.setVisible(true);		
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
		
	}

}
