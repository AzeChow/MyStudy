/*
 * Created on 2004-11-9
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataimport;

import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JTextPane;

import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;

/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DbCommon {

	//按表名查找字段to vf
	public static Vector isExitFields(Vector vector,String tableName){
		Vector list = new Vector();
		if (vector != null && !vector.isEmpty()){
			for (int i=0;i<vector.size();i++){
				if (((CheckableItem) vector.get(i)).getTableName().equals(tableName)){
					list.addElement((CheckableItem) vector.get(i));
				}
			}
		}
		return list;
	 }
	//删除字段根据tableName
	public static void deleteFields(Vector vector,String tableName){
		if (vector != null && !vector.isEmpty()){
			for (int i=0;i<vector.size();i++){
				if (((CheckableItem) vector.get(i)).getTableName().equals(tableName)){
					vector.removeElement((CheckableItem) vector.get(i));
				}
			}
		}
	}
	//判断是否有被选择的字段
	public static boolean isSelectedFields(Vector vector){
		if (vector != null && !vector.isEmpty()){
			for (int i=0;i<vector.size();i++){
				if (((CheckableItem) vector.get(i)).isSelected()){
					return true;
				}
			}
		} else {
			return false;
		}
		return false;
	}
	//初始化条件过滤选项框
	public static void initComboBox(JComboBox jComboBox,JComboBox jComboBox3,
			JComboBox jComboBox6,JComboBox jComboBox7,JComboBox jComboBox1,JComboBox jComboBox4,
			Vector tableList,JComboBox jComboBox2,JComboBox jComboBox5){
		jComboBox.removeAllItems();
        jComboBox.addItem("");
        jComboBox.addItem("(");
        jComboBox.setUI(new CustomBaseComboBoxUI(96));
        
        jComboBox3.removeAllItems();
        jComboBox3.addItem("");
        jComboBox3.addItem("=");
        jComboBox3.addItem(">");
        jComboBox3.addItem("<");
        jComboBox3.addItem(">=");
        jComboBox3.addItem("<=");
        jComboBox3.addItem("<>");
        jComboBox3.addItem("like");
        jComboBox3.setUI(new CustomBaseComboBoxUI(67));
        
        jComboBox6.removeAllItems();
        jComboBox6.addItem("");
        jComboBox6.addItem(")");
        jComboBox6.setUI(new CustomBaseComboBoxUI(107));
        
        jComboBox7.removeAllItems();
        jComboBox7.addItem("");
        jComboBox7.addItem("and");
        jComboBox7.addItem("or");
        jComboBox7.addItem("not");
        jComboBox7.setUI(new CustomBaseComboBoxUI(107));
        
        jComboBox1.removeAllItems();
        jComboBox1.addItem("");
        for (int i=0;i<tableList.size();i++){
        	jComboBox1.addItem(((BillTemp)tableList.get(i)).getBill1());
        }
        jComboBox1.setUI(new CustomBaseComboBoxUI(106));
        
        jComboBox4.removeAllItems();
        jComboBox4.addItem("");
        for (int i=0;i<tableList.size();i++){
        	jComboBox4.addItem(((BillTemp)tableList.get(i)).getBill1());
        }
        jComboBox4.setUI(new CustomBaseComboBoxUI(96));   
       
        jComboBox2.removeAllItems();
        jComboBox2.addItem("");
        jComboBox2.setUI(new CustomBaseComboBoxUI(106));
        
        jComboBox5.removeAllItems();
        jComboBox5.addItem("");
        jComboBox5.setUI(new CustomBaseComboBoxUI(106));
        
	}
	//初始化字段 combobox
	public static void getComFields(Vector vectorAllFields,String tableName,JComboBox jComboBox){
		 Vector vf = isExitFields(vectorAllFields,tableName);
		 jComboBox.removeAllItems();
	     jComboBox.addItem("");
		 if (vf!=null && !vf.isEmpty()){
		 	for (int i=0;i<vf.size();i++){
		 	  if (!((CheckableItem) vf.get(i)).getCode().equals("所有字段")){
		 	    jComboBox.addItem(((CheckableItem) vf.get(i)).getCode());
		 	  }
		 	}
		 }
		 jComboBox.setUI(new CustomBaseComboBoxUI(106));    
	}
	//返回所有被选择的字段
	public static Vector allSelectedFields(Vector vector){
		Vector list = new Vector();
		if (vector != null && !vector.isEmpty()){
			for (int i=0;i<vector.size();i++){
				if (((CheckableItem) vector.get(i)).isSelected()){
					if (!((CheckableItem) vector.get(i)).getCode().equals("所有字段")){
					BillTemp temp = new BillTemp();
					temp.setBill1(((CheckableItem) vector.get(i)).getTableName()+"."+
							((CheckableItem) vector.get(i)).getCode());
					list.addElement(temp);
					}
				}
			}
		}
		return list;
	}
	public static void getSql(JTextPane jTextArea1 ,Vector vector,String whereSql,Vector tableDest,Vector groupDest,Vector orderDest){
	 	String select = "";
	 	String where = "";
	 	String from = "";
	 	String groupBy = "";
	 	String orderBy = "";
	 	if (!allSelectedFields(vector).isEmpty()){
	 		select = "select ";
	 		for (int i=0;i<allSelectedFields(vector).size();i++){
	 			if (i==0){
	 				select = select + (((BillTemp) allSelectedFields(vector).get(i)).getBill1());
	 				continue;
	 			}
	 		    select = select + ","+(((BillTemp) allSelectedFields(vector).get(i)).getBill1());
	 		}
	 	}
	 	if (!whereSql.equals("")){
	 		where = " where "+whereSql;
	 	}
	 	if (!tableDest.isEmpty()){
	 		from = " from  ";
	 		for (int j=0;j<tableDest.size();j++){
	 			if (j==0){
	 				from = from +(((BillTemp) tableDest.get(j)).getBill1());
	 				continue;
	 			}
	 			from = from + ","+(((BillTemp) tableDest.get(j)).getBill1());
	 		}
	 	}
	 	if (!groupDest.isEmpty()){
	 		groupBy = " group by ";
	 		for (int j=0;j<groupDest.size();j++){
	 			if (j==0){
	 				groupBy = groupBy + ((BillTemp) groupDest.get(j)).getBill1();
	 				continue;
	 			} 
	 			groupBy = groupBy + ","+((BillTemp) groupDest.get(j)).getBill1();
	 		}
	 	}
	 	if (!orderDest.isEmpty()){
	 		orderBy = " order by ";
	 		for (int j=0;j<orderDest.size();j++){
	 			if (j==0){
	 				orderBy = orderBy + ((BillTemp) orderDest.get(j)).getBill1();
	 				continue;
	 			} 
	 			orderBy = orderBy + ","+((BillTemp) orderDest.get(j)).getBill1();
	 		}
	 	}
	 	String sql = "";
	 	if (!select.equals("")){
	 		sql = select+"\n";
	 	}
	 	sql = sql + from+"\n";
	 	if (!where.equals("")){
	 		sql = sql+where+"\n";
	 	}
	 	if (!groupBy.equals("")){
	 		sql = sql+groupBy+"\n";
	 	}
	 	if (!orderBy.equals("")){
	 	    sql = sql+ orderBy;
	 	}
	 	jTextArea1.setText(sql);
	 }
	
}