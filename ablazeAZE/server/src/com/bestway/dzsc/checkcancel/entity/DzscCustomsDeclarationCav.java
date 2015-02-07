package com.bestway.dzsc.checkcancel.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Deduc;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.ImpExpType;

/**
 * 存放电子手册核销报关单
 * 
 * @author Administrator
 */
public class DzscCustomsDeclarationCav extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 合同审核单头
	 */
	private DzscContractCav contractCav;

	/**
	 * 进出口标志
	 * IMPORT=0;	进口标志
	 * EXPORT=1;	出口标志
	 * SPECIAL=2;	特殊报关单
	 */
	private Integer impExpFlag;  //  @jve:decl-index=0:

	/**
	 * 进出口类型
	 * DIRECT_IMPORT = 0;	料件进口 料件资料 直接进口---料件
	 * TRANSFER_FACTORY_IMPORT = 1;	料件转厂 料件资料 转厂进口---料件
	 * BACK_FACTORY_REWORK = 2;	退厂返工 成品资料 ---成品
	 * GENERAL_TRADE_IMPORT = 3;	一般贸易进口 --- 料件
	 * DIRECT_EXPORT = 4;	成品出口 成品资料 ---成品
	 * TRANSFER_FACTORY_EXPORT = 5;	转厂出口 成品资料 ---成品
	 * BACK_MATERIEL_EXPORT = 6;	退料出口 料件资料 ---料件
	 * REWORK_EXPORT = 7;	返工复出 成品资料 ---成品
	 * REMIAN_MATERIAL_BACK_PORT = 8;	边角料退港 ----料件
	 * REMIAN_MATERIAL_DOMESTIC_SALES = 9;	边角料内销 ---料件
	 * GENERAL_TRADE_EXPORT = 10;	一般贸易出口 ---成品
	 * EQUIPMENT_IMPORT = 11;	设备进口
	 * BACK_PORT_REPAIR = 12;	退港维修
	 * EQUIPMENT_BACK_PORT = 13;	设备退港
	 * REMAIN_FORWARD_EXPORT = 14;	余料结转(出口报关单)
	 * EXPORT_STORAGE = 15;	出口仓储
	 * IMPORT_STORAGE = 16;	进口仓储
	 * MATERIAL_DOMESTIC_SALES = 17;	料件内销，海关批准内销 ---料件
	 * MATERIAL_EXCHANGE = 18;	料件退换 ---料件
	 * MATERIAL_REOUT = 19;	料件复出 ---料件
	 * All_Type = 20;	所有（注意：不存在这样的类型，方便在参数设置中使用到）
	 * IMPORT_EXG_BACK = 25;	进料成品退换 ---成品
	 * IMPORT_REPAIR_MATERIAL = 26;	修理物品 ---成品
	 * EXPORT_MATERIAL_REBACK = 27;	修理物品复出 ---成品
	 * EXPORT_EXG_REBACK = 28;	进料成品退换复出 ---成品
	 * REMAIN_FORWARD_IMPORT = 21;	余料结转(进口报关单)
	 */
	private Integer impExpType;

	/**
	 * 报关单号
	 */
	private String customsDeclarationCode;

	/**
	 * 进出口日期
	 */
	private Date impExpDate;

	/**
	 * 申报日期
	 */
	private Date declarationDate;

	/**
	 * 报送海关
	 */
	private Customs declarationCustoms;

	/**
	 * 核扣方式
	 */
	private Deduc deduc;

	/**
	 * 是海关
	 */
	private Boolean isCustoms = false;

	/**
	 * 获取合同审核单头
	 * 
	 * @return contractCav 合同审核单头
	 */
	public DzscContractCav getContractCav() {
		return contractCav;
	}

	/**
	 * 设置 合同审核单头
	 * 
	 * @param contractCav
	 *            合同审核单头
	 */
	public void setContractCav(DzscContractCav contractCav) {
		this.contractCav = contractCav;
	}

	/**
	 * 获取报关单号
	 * 
	 * @return customsDeclarationCode 报关单号
	 */
	public String getCustomsDeclarationCode() {
		return customsDeclarationCode;
	}

	/**
	 * 设置报关单号
	 * 
	 * @param customsDeclarationCode
	 *            报关单号
	 */
	public void setCustomsDeclarationCode(String customsDeclarationCode) {
		this.customsDeclarationCode = customsDeclarationCode;
	}

	/**
	 * 获取报送海关
	 * 
	 * @return declarationCustoms 报送海关
	 */
	public Customs getDeclarationCustoms() {
		return declarationCustoms;
	}

	/**
	 * 设置报送海关
	 * 
	 * @param declarationCustoms
	 *            报送海关
	 */
	public void setDeclarationCustoms(Customs declarationCustoms) {
		this.declarationCustoms = declarationCustoms;
	}

	/**
	 * 获取申报日期
	 * 
	 * @return declarationDate 申报日期
	 */
	public Date getDeclarationDate() {
		return declarationDate;
	}

	/**
	 * 设置申报日期
	 * 
	 * @param declarationDate
	 *            申报日期
	 */
	public void setDeclarationDate(Date declarationDate) {
		this.declarationDate = declarationDate;
	}

	/**
	 * 获取进出口日期
	 * 
	 * @return impExpDate 进出口日期
	 */
	public Date getImpExpDate() {
		return impExpDate;
	}

	/**
	 * 设置进出口日期
	 * 
	 * @param impExpDate
	 *            进出口日期
	 */
	public void setImpExpDate(Date impExpDate) {
		this.impExpDate = impExpDate;
	}

	/**
	 * 获取进出口标志
	 * 
	 * @return impExpFlag 进出口标志
	 */
	public Integer getImpExpFlag() {
		return impExpFlag;
	}

	// /**
	// * 获取进出口标志
	// *
	// * @return impExpFlag 进出口标志
	// */
	// public Integer getCustomsImpExpFlag() {
	// return impExpFlag;
	// }
	/**
	 * 获取进出口标志
	 * 
	 * @return impExpFlag 进出口标志
	 */
	public String getCustomsImpExpType() {
		if (this.impExpType.equals(ImpExpType.DIRECT_IMPORT)
				|| this.impExpType.equals(ImpExpType.TRANSFER_FACTORY_IMPORT)
				|| this.impExpType.equals(ImpExpType.REMAIN_FORWARD_IMPORT)
				|| this.impExpType.equals(ImpExpType.BACK_FACTORY_REWORK)
				|| this.impExpType.equals(ImpExpType.GENERAL_TRADE_IMPORT)
				|| this.impExpType
						.equals(ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES)
				|| this.impExpType.equals(ImpExpType.EQUIPMENT_IMPORT)
				|| this.impExpType.equals(ImpExpType.IMPORT_STORAGE)
				|| this.impExpType.equals(ImpExpType.MATERIAL_DOMESTIC_SALES)) {
			return "I";
		} else if (this.impExpType.equals(ImpExpType.DIRECT_EXPORT)
				|| this.impExpType.equals(ImpExpType.TRANSFER_FACTORY_EXPORT)
				|| this.impExpType.equals(ImpExpType.REMAIN_FORWARD_EXPORT)
				|| this.impExpType.equals(ImpExpType.BACK_MATERIEL_EXPORT)
				|| this.impExpType.equals(ImpExpType.REWORK_EXPORT)
				|| this.impExpType.equals(ImpExpType.GENERAL_TRADE_EXPORT)
				|| this.impExpType.equals(ImpExpType.REMIAN_MATERIAL_BACK_PORT)
				|| this.impExpType.equals(ImpExpType.BACK_PORT_REPAIR)
				|| this.impExpType.equals(ImpExpType.EQUIPMENT_BACK_PORT)
				|| this.impExpType.equals(ImpExpType.EXPORT_STORAGE)
				|| this.impExpType.equals(ImpExpType.MATERIAL_EXCHANGE)
				|| this.impExpType.equals(ImpExpType.MATERIAL_REOUT)) {
			return "E";
		}
		return "";
	}

	/**
	 * 设置进出口标志
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 */
	public void setImpExpFlag(Integer impExpFlag) {
		this.impExpFlag = impExpFlag;
	}

	/**
	 * 获取是海关，还是自用 true代表是海关用
	 * 
	 * @return isCustoms 是海关，还是自用 true代表是海关用
	 */
	public Boolean getIsCustoms() {
		return isCustoms;
	}

	/**
	 * 设置是海关，还是自用 true代表是海关用
	 * 
	 * @param isCustoms
	 *            是海关，还是自用 true代表是海关用
	 */
	public void setIsCustoms(Boolean isCustoms) {
		this.isCustoms = isCustoms;
	}

	public Integer getImpExpType() {
		return impExpType;
	}

	public void setImpExpType(Integer impExpType) {
		this.impExpType = impExpType;
	}

	public Deduc getDeduc() {
		return deduc;
	}

	public void setDeduc(Deduc deduc) {
		this.deduc = deduc;
	}

}
