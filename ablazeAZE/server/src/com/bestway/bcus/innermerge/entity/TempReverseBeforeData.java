/*
 * Created on 2004-12-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.innermerge.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * 临时实体类，存放临时的反向归并第一层资料
 * 
 * @author Administrator
 */
public class TempReverseBeforeData implements Serializable{
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
	/**
	 * id
	 */
	private String id;
	
	/**
	 * 关联的物料基本资料
	 */
	private Materiel materiel;

	/**
	 * 归并前法定单位(从物料主档中的海关编码的第一单位中得来)
	 */
	private Unit hsBeforeLegalUnit;

	/**
	 * 归并前企业单位(从物料主档中的企业单位对应的海关单位得来)
	 */
	private CalUnit hsBeforeEnterpriseUnit;
	
	/**
	 * 获取id
	 * 
	 * @return id 
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 设置id
	 * 
	 * @param id 
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 获取归并前企业单位(从物料主档中的企业单位对应的海关单位得来)
	 * 
	 * @return hsBeforeEnterpriseUnit 归并前企业单位(从物料主档中的企业单位对应的海关单位得来)
	 */
	public CalUnit getHsBeforeEnterpriseUnit() {
		return hsBeforeEnterpriseUnit;
	}
	
	/**
	 * 设置归并前企业单位(从物料主档中的企业单位对应的海关单位得来)
	 * 
	 * @param hsBeforeEnterpriseUnit 归并前企业单位(从物料主档中的企业单位对应的海关单位得来)
	 */
	public void setHsBeforeEnterpriseUnit(CalUnit hsBeforeEnterpriseUnit) {
		this.hsBeforeEnterpriseUnit = hsBeforeEnterpriseUnit;
	}
	
	/**
	 * 获取归并前法定单位(从物料主档中的海关编码的第一单位中得来)
	 * 
	 * @return hsBeforeLegalUnit 归并前法定单位(从物料主档中的海关编码的第一单位中得来)
	 */
	public Unit getHsBeforeLegalUnit() {
		return hsBeforeLegalUnit;
	}
	
	/**
	 * 设置归并前法定单位(从物料主档中的海关编码的第一单位中得来)
	 * 
	 * @param hsBeforeLegalUnit 归并前法定单位(从物料主档中的海关编码的第一单位中得来)
	 */
	public void setHsBeforeLegalUnit(Unit hsBeforeLegalUnit) {
		this.hsBeforeLegalUnit = hsBeforeLegalUnit;
	}
	
	/**
	 * 获取关联的物料基本资料
	 * 
	 * @return materiel 关联的物料基本资料
	 */
	public Materiel getMateriel() {
		return materiel;
	}
	
	/**
	 * 设置关联的物料基本资料
	 * 
	 * @param materiel 关联的物料基本资料
	 */
	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}
}
