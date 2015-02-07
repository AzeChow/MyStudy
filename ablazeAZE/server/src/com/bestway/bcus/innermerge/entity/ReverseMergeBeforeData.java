/*
 * Created on 2004-12-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.innermerge.entity;

import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * 内部归并－－反向归并第一层数据资料
 * @author Administrator
 */
public class ReverseMergeBeforeData extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();

	/**
	 * 把内部归并的归并前数据和反向归并的10码数据对接
	 */
	public static int BEFORE_CONCAT_TEN = 0;

	/**
	 * 把内部归并的10码数据和反向归并的4码数据对接
	 */
	public static int TEN_CONCAT_FOUR = 1;

	/**
	 * 反向归并十码资料
	 */
	private ReverseMergeTenData reverseMergeTenData; 

	/**
	 * 关联的物料基本资料
	 */
	private Materiel materiel; 

	/**
	 * 归并前法定单位
	 */
	private Unit hsBeforeLegalUnit; 

	/**
	 * 归并前企业单位
	 */
	private CalUnit hsBeforeEnterpriseUnit; 

	/**
	 * 有此生成的内部归并数据的id
	 */
	private String innerMergeDataId;

	/**
	 * 对接标志位
	 * BEFORE_CONCAT_TEN = 0;	把内部归并的归并前数据和反向归并的10码数据对接
	 * TEN_CONCAT_FOUR = 1;	把内部归并的10码数据和反向归并的4码数据对接
	 */
	private Integer concatFlag;

	/**
	 * 获取对接标志位
	 * 
	 * @return concatFlag 对接标志位
	 */
	public Integer getConcatFlag() {
		return concatFlag;
	}

	/**
	 * 设置对接标志位
	 * 
	 * @param concatFlag 对接标志位
	 */
	public void setConcatFlag(Integer concatFlag) {
		this.concatFlag = concatFlag;
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

	/**
	 * 获取反向归并十码资料
	 * 
	 * @return reverseMergeTenData 反向归并十码资料
	 */
	public ReverseMergeTenData getReverseMergeTenData() {
		return reverseMergeTenData;
	}

	/**
	 * 设置反向归并十码资料
	 * 
	 * @param reverseMergeTenData 反向归并十码资料
	 */
	public void setReverseMergeTenData(ReverseMergeTenData reverseMergeTenData) {
		this.reverseMergeTenData = reverseMergeTenData;
	}

	/**
	 * 获取有此生成的内部归并数据的id
	 * 
	 * @return innerMergeDataId 有此生成的内部归并数据的id
	 */
	public String getInnerMergeDataId() {
		return innerMergeDataId;
	}

	/**
	 * 设置有此生成的内部归并数据的id
	 * 
	 * @param innerMergeDataId 有此生成的内部归并数据的id
	 */
	public void setInnerMergeDataId(String innerMergeDataId) {
		this.innerMergeDataId = innerMergeDataId;
	}
}