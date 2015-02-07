/*
 * Created on 2004-9-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.dataimport.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 数据视图
 * @author 
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DBView extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    /**
     * 数据源
     */
	private DBDataRoot db;          
	/**
	 * 视图名称
	 */
	private String name;            
	/**
	 * 执行语句1
	 */
	private String sqlStr1;     
	/**
	 * 执行语句2
	 */
	private String sqlStr2;
	/**
	 * 执行语句3
	 */
	private String sqlStr3;
	/**
	 * 执行语句4
	 */
	private String sqlStr4;
	/**
	 * 执行语句5
	 */
	private String sqlStr5;
	/**
	 * sql脚本
	 */
	private String sqlScript;         
	/**
	 * 是否生效
	 */
	private Boolean isEffect=false;    
	
	/**
	 * 执行的语句6
	 */
	private String				sqlStr6;
	/**
	 * 执行的语句7
	 */
	private String				sqlStr7;
	/**
	 * 执行的语句8
	 */
	private String				sqlStr8;
	/**
	 * 执行的语句9
	 */
	private String				sqlStr9;
	/**
	 * 执行的语句10
	 */
	private String				sqlStr10;
	
	
	/**
	 * @return Returns the isEffect.
	 */
	public Boolean getIsEffect() {
		return isEffect;
	}
	/**
	 * @param isEffect The isEffect to set.
	 */
	public void setIsEffect(Boolean isEffect) {
		this.isEffect = isEffect;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the db.
	 */
	public DBDataRoot getDb() {
		return db;
	}
	/**
	 * @param db The db to set.
	 */
	public void setDb(DBDataRoot db) {
		this.db = db;
	}
	
	
	
	
	public String getSqlScript() {		
		if (sqlStr1 == null)
		{
			sqlStr1 = "";
		}
		if (sqlStr2 == null)
		{
			sqlStr2 = "";
		}
		if (sqlStr3 == null)
		{
			sqlStr3 = "";
		}
		if (sqlStr4 == null)
		{
			sqlStr4 = "";
		}
		if (sqlStr5 == null)
		{
			sqlStr5 = "";
		}
		if (sqlStr6 == null)
		{
			sqlStr6 = "";
		}
		if (sqlStr7 == null)
		{
			sqlStr7 = "";
		}
		if (sqlStr8 == null)
		{
			sqlStr8 = "";
		}
		if (sqlStr9 == null)
		{
			sqlStr9 = "";
		}
		if (sqlStr10 == null)
		{
			sqlStr10 = "";
		}
		return this.sqlStr1 + this.sqlStr2 + this.sqlStr3 + this.sqlStr4 + this.sqlStr5  + this.sqlStr6  
		+ this.sqlStr7  + this.sqlStr8  + this.sqlStr9  + this.sqlStr10 ;
	}
	
    //设置语句
	public void setSqlScript(String sqlScript) {
		if (sqlScript == null || "".equals(sqlScript.trim())) {
			return;
		}
		int length = sqlScript.getBytes().length;
		if (length <= 225) {
			this.setSqlStr1(substring(sqlScript,0, length));
			this.setSqlStr2("");
			this.setSqlStr3("");
			this.setSqlStr4("");
			this.setSqlStr5("");
			this.setSqlStr6("");
			this.setSqlStr7("");
			this.setSqlStr8("");
			this.setSqlStr9("");
			this.setSqlStr10("");	
		} else if (length <= 450) {
			this.setSqlStr1(substring(sqlScript,0, 225));
			this.setSqlStr2(substring(sqlScript,225, length));
			this.setSqlStr3("");
			this.setSqlStr4("");
			this.setSqlStr5("");
			this.setSqlStr6("");
			this.setSqlStr7("");
			this.setSqlStr8("");
			this.setSqlStr9("");
			this.setSqlStr10("");			
		} else if (length <= 675) {
			this.setSqlStr1(substring(sqlScript,0, 225));
			this.setSqlStr2(substring(sqlScript,225, 450));
			this.setSqlStr3(substring(sqlScript,450, length));
			this.setSqlStr4("");
			this.setSqlStr5("");
			this.setSqlStr6("");
			this.setSqlStr7("");
			this.setSqlStr8("");
			this.setSqlStr9("");
			this.setSqlStr10("");
		} else if (length <= 900) {
			this.setSqlStr1(substring(sqlScript,0, 225));
			this.setSqlStr2(substring(sqlScript,225, 450));
			this.setSqlStr3(substring(sqlScript,450, 675));
			this.setSqlStr4(substring(sqlScript,675, length));
			this.setSqlStr5("");
			this.setSqlStr6("");
			this.setSqlStr7("");
			this.setSqlStr8("");
			this.setSqlStr9("");
			this.setSqlStr10("");
		} else if (length <= 1125) {
			this.setSqlStr1(substring(sqlScript,0, 225));
			this.setSqlStr2(substring(sqlScript,225, 450));
			this.setSqlStr3(substring(sqlScript,450, 675));
			this.setSqlStr4(substring(sqlScript,675, 900));
			this.setSqlStr5(substring(sqlScript,900, length));
			this.setSqlStr6("");
			this.setSqlStr7("");
			this.setSqlStr8("");
			this.setSqlStr9("");
			this.setSqlStr10("");
		}else if (length <= 1350) {
			this.setSqlStr1(substring(sqlScript,0, 225));
			this.setSqlStr2(substring(sqlScript,225, 450));
			this.setSqlStr3(substring(sqlScript,450, 675));
			this.setSqlStr4(substring(sqlScript,675, 900));
			this.setSqlStr5(substring(sqlScript,900, 1125));
			this.setSqlStr6(substring(sqlScript,1125,length));
			this.setSqlStr7("");
			this.setSqlStr8("");
			this.setSqlStr9("");
			this.setSqlStr10("");
		} else if (length <= 1575) {
			this.setSqlStr1(substring(sqlScript,0, 225));
			this.setSqlStr2(substring(sqlScript,225, 450));
			this.setSqlStr3(substring(sqlScript,450, 675));
			this.setSqlStr4(substring(sqlScript,675, 900));
			this.setSqlStr5(substring(sqlScript,900, 1125));
			this.setSqlStr6(substring(sqlScript,1125,1350));
			this.setSqlStr7(substring(sqlScript,1350,length));
			this.setSqlStr8("");
			this.setSqlStr9("");
			this.setSqlStr10("");
		} else if (length <= 1800) {
			this.setSqlStr1(substring(sqlScript,0, 225));
			this.setSqlStr2(substring(sqlScript,225, 450));
			this.setSqlStr3(substring(sqlScript,450, 675));
			this.setSqlStr4(substring(sqlScript,675, 900));
			this.setSqlStr5(substring(sqlScript,900, 1125));
			this.setSqlStr6(substring(sqlScript,1125,1350));
			this.setSqlStr7(substring(sqlScript,1350,1575));
			this.setSqlStr8(substring(sqlScript,1575,length));
			this.setSqlStr9("");
			this.setSqlStr10("");
		} else if (length <= 2025) {
			this.setSqlStr1(substring(sqlScript,0, 225));
			this.setSqlStr2(substring(sqlScript,225, 450));
			this.setSqlStr3(substring(sqlScript,450, 675));
			this.setSqlStr4(substring(sqlScript,675, 900));
			this.setSqlStr5(substring(sqlScript,900, 1125));
			this.setSqlStr6(substring(sqlScript,1125,1350));
			this.setSqlStr7(substring(sqlScript,1350,1575));
			this.setSqlStr8(substring(sqlScript,1575,1800));
			this.setSqlStr9(substring(sqlScript,1800,length));
			this.setSqlStr10("");
		}else if (length <= 2250) {
			this.setSqlStr1(substring(sqlScript,0, 225));			
			this.setSqlStr2(substring(sqlScript,225, 450));			
			this.setSqlStr3(substring(sqlScript,450, 675));			
			this.setSqlStr4(substring(sqlScript,675, 900));			
			this.setSqlStr5(substring(sqlScript,900, 1125));			
			this.setSqlStr6(substring(sqlScript,1125,1350));			
			this.setSqlStr7(substring(sqlScript,1350,1575));			
			this.setSqlStr8(substring(sqlScript,1575,1800));			
			this.setSqlStr9(substring(sqlScript,1800,2025));			
			this.setSqlStr10(substring(sqlScript,2025,length));
		}
	}
	 
   private  String substring(String str, int start, int end) {
		if (str != null && str.getBytes().length >= end) {
			return new String(str.getBytes(), start, end - start);
		} else if (str != null && str.getBytes().length > start){
				return new String(str.getBytes(), start, str.getBytes().length
				- start);
		}
		return "";
   }
	
	
	
	
	public String getSqlStr1() {
		return sqlStr1;
	}
	public void setSqlStr1(String sqlStr1) {
		this.sqlStr1 = sqlStr1;
	}
	public String getSqlStr2() {
		return sqlStr2;
	}
	public void setSqlStr2(String sqlStr2) {
		this.sqlStr2 = sqlStr2;
	}
	public String getSqlStr3() {
		return sqlStr3;
	}
	public void setSqlStr3(String sqlStr3) {
		this.sqlStr3 = sqlStr3;
	}
	public String getSqlStr4() {
		return sqlStr4;
	}
	public void setSqlStr4(String sqlStr4) {
		this.sqlStr4 = sqlStr4;
	}
	public String getSqlStr5() {
		return sqlStr5;
	}
	public void setSqlStr5(String sqlStr5) {
		this.sqlStr5 = sqlStr5;
	}
	public String getSqlStr10() {
		return sqlStr10;
	}
	public void setSqlStr10(String sqlStr10) {
		this.sqlStr10 = sqlStr10;
	}
	public String getSqlStr6() {
		return sqlStr6;
	}
	public void setSqlStr6(String sqlStr6) {
		this.sqlStr6 = sqlStr6;
	}
	public String getSqlStr7() {
		return sqlStr7;
	}
	public void setSqlStr7(String sqlStr7) {
		this.sqlStr7 = sqlStr7;
	}
	public String getSqlStr8() {
		return sqlStr8;
	}
	public void setSqlStr8(String sqlStr8) {
		this.sqlStr8 = sqlStr8;
	}
	public String getSqlStr9() {
		return sqlStr9;
	}
	public void setSqlStr9(String sqlStr9) {
		this.sqlStr9 = sqlStr9;
	}
}