/*
 * Created on 2005-4-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.checkcancel.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.dzsc.checkcancel.entity.DzscCheckHead;
import com.bestway.dzsc.checkcancel.entity.DzscCheckImg;
import com.bestway.dzsc.checkcancel.entity.DzscContractBomCav;
import com.bestway.dzsc.checkcancel.entity.DzscContractCav;
import com.bestway.dzsc.checkcancel.entity.DzscContractExgCav;
import com.bestway.dzsc.checkcancel.entity.DzscContractImgCav;
import com.bestway.dzsc.checkcancel.entity.DzscCustomsDeclarationCav;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsBomBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.message.entity.DzscParameterSet;
import com.bestway.common.constant.DeclareState;

/**
 * com.bestway.dzsc.checkcancel.dao.DzscContractCavDao
 * 
 * @author Administrator
 */
public class DzscContractCavDao extends BaseDao {
	/**
	 * 抓取电子帐册合同核销单头
	 * 
	 * @param isCustoms
	 *            是海关用还是自用，true代表是海关用
	 * @return List 是DzscContractCav类型，电子帐册合同核销单头
	 */
	public List findContractCav(boolean isCustoms) {
		return this.find("select a from DzscContractCav as a where "
				+ "  a.isCustoms=? and a.company.id=? ", new Object[] {
				isCustoms, CommonUtils.getCompany().getId() });
	}

	/**
	 * 抓取电子帐册合同核销单头
	 * 
	 * @param emsNo
	 *            手册编号
	 * @param isCustoms
	 *            是海关用还是自用，true代表是海关用
	 * @return List 是DzscContractCav类型，电子帐册合同核销单头
	 */
	public List findContractCav(String emsNo, boolean isCustoms) {
		return this.find("select a from DzscContractCav as a where "
				+ " a.emsNo=? and a.isCustoms=? and a.company.id=? ",
				new Object[] { emsNo, isCustoms,
						CommonUtils.getCompany().getId() });
	}

	/**
	 * 抓取电子帐册合同核销单头
	 * 
	 * @param emsNo
	 *            手册编号
	 * @param isCustoms
	 *            是海关用还是自用，true代表是海关用
	 * @return List 是DzscContractCav类型，电子帐册合同核销单头
	 */
	public DzscContractCav findDzscContractCavById(String Id) {
		List list = this.find("select a from DzscContractCav as a where "
				+ " a.id=? ", new Object[] { Id });
		if (list.size() > 0) {
			return (DzscContractCav) list.get(0);
		} else {
			return null;
		}
	}

	public DzscContractBomCav findDzscContractBomCavByIdNoCache(String id) {
		Map map = new HashMap();
		List list = this.findNoCache(
				" select a  from DzscContractBomCav  as a "
						+ "    where a.company.id=?  and a.id =? ",
				new Object[] { CommonUtils.getCompany().getId(), id });
		DzscContractBomCav bom = null;
		if (list.size() > 0) {
			bom = (DzscContractBomCav) list.get(0);
		}
		return bom;
	}

	/**
	 * 保存电子帐册合同核销单头
	 * 
	 * @param contractCav
	 *            电子帐册合同核销单头
	 */
	public void saveContractCav(DzscContractCav contractCav) {
		this.saveOrUpdate(contractCav);
	}

	/**
	 * 删除电子帐册合同核销单头
	 * 
	 * @param contractCav
	 *            电子帐册合同核销单头
	 */
	public void deleteContractCav(DzscContractCav contractCav) {
		this.delete(contractCav);
	}

	/**
	 * 抓取电子帐册合同核销报关单
	 * 
	 * @param contractCav
	 *            电子帐册合同核销单头
	 * @return List 是DzscCustomsDeclarationCav类型，电子帐册合同核销报关单
	 */
	public List findCustomsDeclarationCav(DzscContractCav contractCav) {
		return this
				.find(
						"select a from DzscCustomsDeclarationCav as a where a.contractCav.id=? ",
						new Object[] { contractCav.getId() });
	}

	/**
	 * 删除电子帐册合同核销报关单
	 * 
	 * @param contractCav
	 *            电子帐册合同核销单头
	 * @return List 是DzscCustomsDeclarationCav类型，电子帐册合同核销报关单
	 */
	public void deleteCustomsDeclarationCav(DzscContractCav contractCav) {
		this.batchUpdateOrDelete(
				"delete DzscCustomsDeclarationCav where contractCav.id=? ",
				new Object[] { contractCav.getId() });
	}

	/**
	 * 保存电子帐册合同核销报关单
	 * 
	 * @param customsDeclarationCav
	 *            电子帐册合同核销报关单
	 */
	public void saveCustomsDeclarationCav(
			DzscCustomsDeclarationCav customsDeclarationCav) {
		this.saveOrUpdate(customsDeclarationCav);
	}

	/**
	 * 删除电子帐册合同核销报关单
	 * 
	 * @param customsDeclarationCav
	 *            电子帐册合同核销报关单
	 */
	public void deleteCustomsDeclarationCav(
			DzscCustomsDeclarationCav customsDeclarationCav) {
		this.delete(customsDeclarationCav);
	}

	/**
	 * 抓取电子手册合同核销成品资料
	 * 
	 * @param contractCav
	 *            电子帐册合同核销单头
	 * @return List 是DzscContractExgCav类型，电子帐册合同核销成品资料
	 */
	public List findContractExgCav(DzscContractCav contractCav) {
		return this
				.find(
						"select a from DzscContractExgCav as a where a.contractCav.id=? ",
						new Object[] { contractCav.getId() });
	}

	/**
	 * 删除电子手册合同核销成品资料
	 * 
	 * @param contractCav
	 *            电子帐册合同核销单头
	 * @return List 是DzscContractExgCav类型，电子帐册合同核销成品资料
	 */
	public void deleteContractExgCav(DzscContractCav contractCav) {
		this.batchUpdateOrDelete(
				"delete DzscContractExgCav where contractCav.id=? ",
				new Object[] { contractCav.getId() });
	}

	/**
	 * 保存电子帐册合同核销成品资料
	 * 
	 * @param contractExgCav
	 *            电子帐册合同核销成品资料
	 */
	public void saveContractExgCav(DzscContractExgCav contractExgCav) {
		this.saveOrUpdate(contractExgCav);
	}

	/**
	 * 删除电子帐册合同核销成品资料
	 * 
	 * @param contractExgCav
	 *            电子帐册合同核销成品资料
	 */
	public void deleteContractExgCav(DzscContractExgCav contractExgCav) {
		this.delete(contractExgCav);
	}

	/**
	 * 抓取电子帐册合同核销料件资料
	 * 
	 * @param contractCav
	 *            电子帐册合同核销单头
	 * @return List 是DzscContractImgCav类型，电子帐册合同核销料件资料
	 */
	public List findContractImgCav(DzscContractCav contractCav) {
		return this
				.find(
						"select a from DzscContractImgCav as a where a.contractCav.id=? ",
						new Object[] { contractCav.getId() });
	}

	/**
	 * 删除电子帐册合同核销料件资料
	 * 
	 * @param contractCav
	 *            电子帐册合同核销单头
	 * @return List 是DzscContractImgCav类型，电子帐册合同核销料件资料
	 */
	public void deleteContractImgCav(DzscContractCav contractCav) {
		this.batchUpdateOrDelete(
				"delete DzscContractImgCav where contractCav.id=? ",
				new Object[] { contractCav.getId() });
	}

	/**
	 * 查找电子帐册合同料件 来自电子帐册合同成品ID
	 * 
	 * @param contractCavId
	 *            电子帐册合同核销单头ID
	 * @param imgSeqNum
	 *            料件序号
	 * @return DzscContractImgCav 电子帐册合同核销料件资料，符合条件的第一条
	 */
	public DzscContractImgCav findContractImgCavBySeqNum(String contractCavId,
			Integer imgSeqNum) {
		List list = this.find(
				"select a from DzscContractImgCav a where a.contractCav.id = ? "
						+ " and a.seqNum=?  ", new Object[] { contractCavId,
						imgSeqNum });
		if (list.size() > 0) {
			return (DzscContractImgCav) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 保存电子帐册合同核销料件资料
	 * 
	 * @param contractImgCav
	 *            电子帐册合同核销料件资料
	 */
	public void saveContractImgCav(DzscContractImgCav contractImgCav) {
		this.saveOrUpdate(contractImgCav);
	}

	/**
	 * 删除电子帐册合同核销料件资料
	 * 
	 * @param contractImgCav
	 *            电子帐册合同核销料件资料
	 */
	public void deleteContractImgCav(DzscContractImgCav contractImgCav) {
		this.delete(contractImgCav);
	}

	/**
	 * 抓取电子帐册合同核销BOM资料
	 * 
	 * @param contractCav
	 *            电子帐册合同核销单头
	 * @return List 是DzscContractBomCav类型，电子帐册合同核销BOM资料
	 */
	public List findContractBomCav(DzscContractCav contractCav) {
		return this
				.find(
						"select a from DzscContractBomCav as a where a.contractCav.id=?",
						new Object[] { contractCav.getId() });
	}
	
	/**
	 * 查询电子手册 参数设置
	 */
	public DzscParameterSet findDzscParameterSet() {
		List<DzscParameterSet> list = this.find("select a from DzscParameterSet a where a.company.id=?",
						new Object[] { CommonUtils.getCompany().getId() });
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 删除电子帐册合同核销BOM资料
	 * 
	 * @param contractCav
	 *            电子帐册合同核销单头
	 * @return List 是DzscContractBomCav类型，电子帐册合同核销BOM资料
	 */
	public void deleteContractBomCav(DzscContractCav contractCav) {
		this.batchUpdateOrDelete(
				"delete DzscContractBomCav where contractCav.id=?",
				new Object[] { contractCav.getId() });
	}

	/**
	 * 抓取电子帐册合同核销BOM资料
	 * 
	 * @param contractCav
	 *            电子帐册合同核销单头
	 * @return List 是DzscContractBomCav类型，电子帐册合同核销BOM资料
	 */
	public List findContractBomCavChanged(DzscContractCav contractCav) {
		return this.find(
				"select a from DzscContractBomCav as a where a.contractCav.id=? "
						+ " and a.modifyMark in (?,?,?) ", new Object[] {
						contractCav.getId(), ModifyMarkState.ADDED,
						ModifyMarkState.DELETED, ModifyMarkState.MODIFIED });
	}

	/**
	 * 查找电子帐册合同核销BOM资料 来自 电子帐册合同核销表头ID和料件序号
	 * 
	 * @param contractCavId
	 *            电子帐册合同核销单头ID
	 * @param imgSeqNum
	 *            料件序号
	 * @return List 是DzscContractBomCav类型，电子帐册合同核销BOM资料
	 */
	public List findContractBomCavByImgSeqNum(String contractCavId,
			String imgSeqNum) {
		return this.find(
				"select a from DzscContractBomCav a where a.contractCav.id = ?"
						+ " and a.seqMaterialNum=? "
						+ " order by a.seqProductNum ", new Object[] {
						contractCavId, imgSeqNum });
	}

	/**
	 * 查找电子帐册合同核销BOM资料 来自 电子帐册合同核销表头ID和料件序号
	 * 
	 * @param contractCavId
	 *            电子帐册合同核销单头ID
	 * @param imgSeqNum
	 *            料件序号
	 * @return List 是DzscContractBomCav类型，电子帐册合同核销BOM资料
	 */
	public List findContractBomCavByExgSeqNum(String contractCavId,
			Integer seqProductNum) {
		return this.find(
				"select a from DzscContractBomCav a where a.contractCav.id = ?"
						+ " and a.seqProductNum=? "
						+ " order by a.seqMaterialNum ", new Object[] {
						contractCavId, seqProductNum });
	}

	/**
	 * 保存电子帐册合同核销BOM资料
	 * 
	 * @param contractBomCav
	 *            电子帐册合同核销BOM资料
	 */
	public void saveContractBomCav(DzscContractBomCav contractBomCav) {
		this.saveOrUpdate(contractBomCav);
	}

	/**
	 * 删除电子帐册合同核销Bom资料
	 * 
	 * @param contractBomCav
	 *            电子帐册合同核销BOM资料
	 */
	public void deleteContractBomCav(DzscContractBomCav contractBomCav) {
		this.delete(contractBomCav);
	}

	/**
	 * 统计每个报关单的总价
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单头
	 * @return List 是DzscCustomsDeclarationCommInfo类型，报关单商品信息
	 */
	public List findCustomsDeclarationTotalPrice(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		String hsql = "select sum(commTotalPrice)  from  DzscCustomsDeclarationCommInfo as a "
				+ " and a.baseCustomsDeclaration.id=? ";
		return this.find(hsql, new Object[] { baseCustomsDeclaration.getId() });
	}

	/**
	 * 根据电子帐册手册抓取报关单份数
	 * 
	 * @param contract
	 *            电子帐册手册表头
	 * @param isImport
	 *            进出口判断，true为进口
	 * @return int 返回报关单份数
	 */
	public int findCutomsDeclarationCountByContract(DzscEmsPorHead contract,
			boolean isImport) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select count(a.id) from DzscCustomsDeclaration as a where a.emsHeadH2k=? "
				+ " and a.company.id=? and a.effective=? ";
		parameters.add(contract.getEmsNo());
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Boolean(true));
		if (isImport) {
			// hsql += " and a.impExpFlag=? ";
			// parameters.add(ImpExpFlag.IMPORT);
			hsql += " and a.impExpType in (?,?,?,?,?,?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
			parameters.add(ImpExpType.GENERAL_TRADE_IMPORT);
			parameters.add(ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES);
			parameters.add(ImpExpType.EQUIPMENT_IMPORT);
			parameters.add(ImpExpType.IMPORT_STORAGE);
			parameters.add(ImpExpType.MATERIAL_DOMESTIC_SALES);
		} else {
			// hsql += " and a.impExpFlag=? ";
			// parameters.add(ImpExpFlag.EXPORT);
			hsql += " and a.impExpType in (?,?,?,?,?,?,?,?,?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
			parameters.add(ImpExpType.REWORK_EXPORT);
			parameters.add(ImpExpType.GENERAL_TRADE_EXPORT);
			parameters.add(ImpExpType.REMIAN_MATERIAL_BACK_PORT);
			parameters.add(ImpExpType.BACK_PORT_REPAIR);
			parameters.add(ImpExpType.EQUIPMENT_BACK_PORT);
			parameters.add(ImpExpType.EXPORT_STORAGE);
			parameters.add(ImpExpType.MATERIAL_EXCHANGE);
			parameters.add(ImpExpType.MATERIAL_REOUT);
		}
		// hsql += " and a.customsDeclarationCode!=? ";
		// hsql += " and a.customsDeclarationCode is not null";
		// parameters.add("");
		List list = this.find(hsql, parameters.toArray());
		if (list.size() <= 0 || list.get(0) == null) {
			return 0;
		} else {
			return Integer.parseInt(list.get(0).toString());
		}
	}

	/**
	 * 根据电子帐册手册抓取报关单金额
	 * 
	 * @param contract
	 *            电子帐册手册表头
	 * @param impExpType
	 *            进出口类型
	 * @param impExpFlag
	 *            进出口标志
	 * @param tradeCodes
	 *            贸易方式编码
	 * @return double 返回报关单金额
	 */
	public double findCutomsDeclarationMoneyByContract(DzscEmsPorHead contract,
			Integer impExpType, Integer impExpFlag, String[] tradeCodes) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.commTotalPrice* "
				+ " case when a.baseCustomsDeclaration.exchangeRate  is null then 1.0"
				+ " when a.baseCustomsDeclaration.exchangeRate=0.0 then 1.0 "
				+ " else a.baseCustomsDeclaration.exchangeRate end) "
				+ " from DzscCustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.emsHeadH2k=? "
				+ " and a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.effective=? ";
		parameters.add(contract.getEmsNo());
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Boolean(true));
		if (impExpType != null) {
			hsql += " and a.baseCustomsDeclaration.impExpType=? ";
			parameters.add(impExpType);
		}
		if (impExpFlag != null) {
			hsql += " and a.baseCustomsDeclaration.impExpFlag=? ";
			parameters.add(impExpFlag);
		}
		if (tradeCodes != null && tradeCodes.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < tradeCodes.length; i++) {
				if (i == tradeCodes.length - 1) {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? ";
				} else {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? or ";
				}
				parameters.add(tradeCodes[i]);
			}
			hsql += " ) ";
		}
		List list = this.find(hsql, parameters.toArray());
		if (list.size() <= 0 || list.get(0) == null) {
			return 0;
		} else {
			return Double.parseDouble(list.get(0).toString());
		}
	}

	/**
	 * 在报关单中查找商品序号
	 * 
	 * @param isMaterial
	 *            料件成品判断，true为料件
	 * @param emsNo
	 *            电子帐册手册编号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 返回商品序号，去掉相同的
	 */
	public List findAllCommInfo(boolean isMaterial, String emsNo,
			Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select distinct  a.commSerialNo  from  DzscCustomsDeclarationCommInfo as a ";
		hsql += " where a.baseCustomsDeclaration.company.id=? ";
		hsql += " and a.baseCustomsDeclaration.effective=? ";
		hsql += " and a.baseCustomsDeclaration.emsHeadH2k=?";
		// hsql += " and b.dzscEmsPorHead.declareState=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Boolean(true));
		parameters.add(emsNo);
		// parameters.add(DzscState.EXECUTE);
		if (isMaterial) {
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?)";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
		} else {
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?)";
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
			parameters.add(ImpExpType.REWORK_EXPORT);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " order by a.commSerialNo ";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 根据商品序号，查询料件商品信息
	 * 
	 * @param emsNo
	 *            电子帐册手册编号
	 * @param commSerialNo
	 *            商品序号
	 * @return DzscEmsImgBill 电子手册手册料件资料，符合要求的第一项
	 */
	public DzscEmsImgBill findImgByCommSerialNo(String emsNo,
			String commSerialNo) {
		String hsql = "select a from DzscEmsImgBill as a where a.seqNum=? "
				+ " and a.dzscEmsPorHead.emsNo=?"
				+ " and a.dzscEmsPorHead.declareState=? "
				+ " and a.dzscEmsPorHead.company.id=? ";
		List list = this.find(hsql, new Object[] {
				Integer.valueOf(commSerialNo), emsNo, DzscState.EXECUTE,
				CommonUtils.getCompany().getId() });
		if (list.size() < 1) {
			return null;
		} else {
			return (DzscEmsImgBill) list.get(0);
		}
	}

	/**
	 * 根据商品序号，查询成品商品信息
	 * 
	 * @param emsNo
	 *            电子帐册手册编号
	 * @param commSerialNo
	 *            商品序号
	 * @return DzscEmsExgBill 电子手册手册成品资料，符合要求的第一项
	 */
	public DzscEmsExgBill findExgByCommSerialNo(String emsNo,
			String commSerialNo) {
		String hsql = "select a from DzscEmsExgBill as a where a.seqNum=? "
				+ " and a.dzscEmsPorHead.emsNo=?"
				+ " and a.dzscEmsPorHead.declareState=? "
				+ " and a.dzscEmsPorHead.company.id=? ";
		List list = this.find(hsql, new Object[] {
				Integer.valueOf(commSerialNo), emsNo, DzscState.EXECUTE,
				CommonUtils.getCompany().getId() });
		if (list.size() < 1) {
			return null;
		} else {
			return (DzscEmsExgBill) list.get(0);
		}
	}

	/**
	 * 根据商品序号，查询成品商品信息
	 * 
	 * @param emsNo
	 *            电子手册手册编号
	 * @param exgSeqNum
	 *            商品序号
	 * @return List 是DzscEmsBomBill类型，电子手册手册Bom资料
	 */
	public List<DzscEmsBomBill> findBomByExg(String emsNo, Integer exgSeqNum,
			String declareState) {
		if (declareState == null) {
			String hsql = "select a from DzscEmsBomBill as a "
					+ " left join a.dzscEmsExgBill as b "
					+ " where b.seqNum=? " + " and b.dzscEmsPorHead.emsNo=?"
					+ " and b.dzscEmsPorHead.company.id=? ";
			return this.find(hsql, new Object[] { Integer.valueOf(exgSeqNum),
					emsNo, CommonUtils.getCompany().getId() });
		} else {
			//4是核销状态
			if (declareState != null && "5".equals(declareState)) {
				String hsql = "select a from DzscEmsBomBill as a "
						+ " left join a.dzscEmsExgBill as b "
						+ " where b.seqNum=? "
						+ " and b.dzscEmsPorHead.emsNo=?"
						+ " and b.dzscEmsPorHead.company.id=? "
						+ "and b.dzscEmsPorHead.declareState='4' ";
				return this.find(hsql, new Object[] {
						Integer.valueOf(exgSeqNum), emsNo,
						CommonUtils.getCompany().getId()});
			} else {
				//2是正在执行状态
				String hsql = "select a from DzscEmsBomBill as a "
						+ " left join a.dzscEmsExgBill as b "
						+ " where b.seqNum=? "
						+ " and b.dzscEmsPorHead.emsNo=?"
						+ " and b.dzscEmsPorHead.company.id=? "
						+ "and b.dzscEmsPorHead.declareState='2' ";
				return this.find(hsql, new Object[] {
						Integer.valueOf(exgSeqNum), emsNo,
						CommonUtils.getCompany().getId()});

			}
		}
	}

	/**
	 * 查询电子手册手册商品数量总量
	 * 
	 * @param commSerialNo
	 *            商品序号
	 * @param impExpFlag
	 *            进出口标志
	 * @param impExpType
	 *            进出口类型
	 * @param tradeCodes
	 *            贸易方式编码
	 * @param emsNo
	 *            电子手册手册编号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return double 返回手册商品数量总量
	 */
	public double findCommInfoTotalAmount(Integer commSerialNo,
			Integer impExpFlag, Integer impExpType, String[] tradeCodes,
			String emsNo, Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select  sum(a.commAmount)  from  DzscCustomsDeclarationCommInfo as a "
				+ " left join a.baseCustomsDeclaration b "
				+ " where b.company.id=? "
				+ " and b.effective=? "
				+ " and b.emsHeadH2k=?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Boolean(true));
		parameters.add(emsNo);
		if (impExpFlag != null) {
			hsql += " and b.impExpFlag=?";
			parameters.add(impExpFlag);
		}
		if (impExpType != null) {
			hsql += " and b.impExpType=?";
			parameters.add(impExpType);
		}
		if (beginDate != null) {
			hsql += " and b.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and b.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (commSerialNo != null) {
			hsql += " and a.commSerialNo=?";
			parameters.add(Integer.valueOf(commSerialNo));
		}
		if (tradeCodes != null && tradeCodes.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < tradeCodes.length; i++) {
				if (i == tradeCodes.length - 1) {
					hsql += " b.tradeMode.code=? ";
				} else {
					hsql += " b.tradeMode.code=? or ";
				}
				parameters.add(tradeCodes[i]);
			}
			hsql += " ) ";
		}
		List list = this.find(hsql, parameters.toArray());
		System.out.println("----"+hsql);
		if (list.size() < 1 || list.get(0) == null) {
			return 0;
		} else {
			return Double.parseDouble(list.get(0).toString());
		}
	}

	/**
	 * 查询电子手册手册第一法定数量与第二法定数量
	 * 
	 * @param commSerialNo
	 *            商品序号
	 * @param impExpFlag
	 *            进口标志
	 * @param impExpType
	 *            进口类型
	 * @param tradeCodes
	 *            贸易方式编码
	 * @param emsNo
	 *            电子手册手册编号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            生效类型
	 * @return double 手册商品数量总量
	 */
	public List findCommInfoLegalAmountAndSecondAmount(Integer commSerialNo,
			Integer impExpFlag, String emsNo, Date beginDate, Date endDate, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select  sum(a.firstAmount),sum(a.secondAmount)  from  DzscCustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k=?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		if (impExpFlag != null) {
			hsql += " and a.baseCustomsDeclaration.impExpFlag=?";
			parameters.add(impExpFlag);
		}
//		if (impExpType != null) {
//			hsql += " and a.baseCustomsDeclaration.impExpType=?";
//			parameters.add(impExpType);
//		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (commSerialNo != null) {
			hsql += " and a.commSerialNo=?";
			parameters.add(Integer.valueOf(commSerialNo));
		}
//		if (tradeCodes != null && tradeCodes.length > 0) {
//			hsql += " and ( ";
//			for (int i = 0; i < tradeCodes.length; i++) {
//				if (i == tradeCodes.length - 1) {
//					hsql += " a.baseCustomsDeclaration.tradeMode.code=? ";
//				} else {
//					hsql += " a.baseCustomsDeclaration.tradeMode.code=? or ";
//				}
//				parameters.add(tradeCodes[i]);
//			}
//			hsql += " ) ";
//		}
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(false);
		}
		return this.find(hsql, parameters.toArray());
		// if (list.size() < 1 || list.get(0) == null) {
		// return 0;
		// } else {
		// return Double.parseDouble(list.get(0).toString());
		// }
	}

	/**
	 * 查询电子手册手册商品数量总量
	 * 
	 * @param commSerialNo
	 *            商品序号
	 * @param impExpFlag
	 *            进口标志
	 * @param impExpType
	 *            进口类型
	 * @param tradeCodes
	 *            贸易方式编码
	 * @param emsNo
	 *            电子手册手册编号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            生效类型
	 * @return double 手册商品数量总量
	 */
	public double findCommInfoTotalAmount(Integer commSerialNo,
			Integer impExpFlag, Integer impExpType, String[] tradeCodes,
			String emsNo, Date beginDate, Date endDate, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select  sum(a.commAmount)  from  DzscCustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k=?";
		parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(new Boolean(true));
		parameters.add(emsNo);
		if (impExpFlag != null) {
			hsql += " and a.baseCustomsDeclaration.impExpFlag=?";
			parameters.add(impExpFlag);
		}
		if (impExpType != null) {
			hsql += " and a.baseCustomsDeclaration.impExpType=?";
			parameters.add(impExpType);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (commSerialNo != null) {
			hsql += " and a.commSerialNo=?";
			parameters.add(Integer.valueOf(commSerialNo));
		}
		if (tradeCodes != null && tradeCodes.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < tradeCodes.length; i++) {
				if (i == tradeCodes.length - 1) {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? ";
				} else {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? or ";
				}
				parameters.add(tradeCodes[i]);
			}
			hsql += " ) ";
		}
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(false);
		}
		List list = this.find(hsql, parameters.toArray());
		if (list.size() < 1 || list.get(0) == null) {
			return 0;
		} else {
			return Double.parseDouble(list.get(0).toString());
		}
	}

	/**
	 * 查询报关单出口总件数
	 * 
	 * @param emsNo
	 *            电子手册手册编号
	 * @return double 出口总件数
	 */
	public double findExpTotalPieces(String emsNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.commodityNum) from  DzscCustomsDeclaration as a "
				+ " where a.emsHeadH2k=? "
				+ " and a.effective=? "
				+ " and a.company.id=? ";
		parameters.add(emsNo);
		parameters.add(new Boolean(true));
		parameters.add(CommonUtils.getCompany().getId());
		hsql += " and a.impExpType in (?,?,?) ";
		parameters.add(ImpExpType.DIRECT_EXPORT);
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		parameters.add(ImpExpType.REWORK_EXPORT);
		List list = this.find(hsql, parameters.toArray());
		double exportAmount = 0.0, backAmount = 0.0;
		if (list.size() > 0 && list.get(0) != null) {
			exportAmount = Double.parseDouble(list.get(0).toString());
		}
		parameters = new ArrayList<Object>();
		hsql = "select sum(a.commodityNum) from  DzscCustomsDeclaration as a "
				+ " where a.emsHeadH2k=? " + " and a.effective=? "
				+ " and a.company.id=? ";
		parameters.add(emsNo);
		parameters.add(new Boolean(true));
		parameters.add(CommonUtils.getCompany().getId());
		hsql += " and a.impExpType=? ";
		parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			backAmount = Double.parseDouble(list.get(0).toString());
		}
		return exportAmount - backAmount;
	}

	/**
	 * 查询进口重量，包含毛重和净重
	 * 
	 * @param emsNo
	 *            电子手册手册编号
	 * @return List 包含毛重总量和净重总量
	 */
	public double[] findImportTotalWeight(String emsNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.grossWeight),sum(a.netWeight)"
				+ " from DzscCustomsDeclaration as a "
				+ " where a.emsHeadH2k=? and a.company.id=? "
				+ " and a.effective=? ";
		parameters.add(emsNo);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Boolean(true));
		hsql += " and a.impExpType in (?,?) ";// ,?
		parameters.add(ImpExpType.DIRECT_IMPORT);
		parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
		// parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
		List list = this.find(hsql, parameters.toArray());
		double[] importWeight = new double[] { 0.0, 0.0 };
		if (list.size() > 0 && list.get(0) != null) {
			Object[] objs = (Object[]) list.get(0);
			importWeight[0] = (objs[0] == null ? 0.0 : Double
					.parseDouble(objs[0].toString()));
			importWeight[1] = (objs[1] == null ? 0.0 : Double
					.parseDouble(objs[1].toString()));
		}
		// parameters = new ArrayList<Object>();
		// hsql = "select sum(a.grossWeight),sum(a.netWeight)"
		// + " from DzscCustomsDeclaration as a "
		// + " where a.emsHeadH2k=? and a.company.id=? "
		// + " and a.effective=? ";
		// parameters.add(emsNo);
		// parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(new Boolean(true));
		// hsql += " and a.impExpType =? ";
		// parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
		// hsql += " and a.tradeMode.code in (?,?) ";
		// parameters.add("0300");
		// parameters.add("0700");
		// list = this.find(hsql, parameters.toArray());
		// if (list.size() > 0 && list.get(0) != null) {
		// Object[] objs = (Object[]) list.get(0);
		// importWeight[0] = importWeight[0]
		// - (objs[0] == null ? 0.0 : Double.parseDouble(objs[0]
		// .toString()));
		// importWeight[1] = importWeight[1]
		// - (objs[1] == null ? 0.0 : Double.parseDouble(objs[1]
		// .toString()));
		// }
		return importWeight;
	}

	/**
	 * 查询进口重量，包含毛重和净重
	 * 
	 * @param emsNo
	 *            电子手册手册编号
	 * @return List 包含毛重总量和净重总量
	 */
	public double[] findExportTotalWeight(String emsNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.grossWeight),sum(a.netWeight)"
				+ " from DzscCustomsDeclaration as a "
				+ " where a.emsHeadH2k=? and a.company.id=? "
				+ " and a.effective=? ";
		parameters.add(emsNo);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Boolean(true));
		hsql += " and a.impExpType in (?,?) ";// ,?
		parameters.add(ImpExpType.DIRECT_EXPORT);
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		// parameters.add(ImpExpType.REWORK_EXPORT);
		List list = this.find(hsql, parameters.toArray());
		double[] exportWeight = new double[] { 0.0, 0.0 };
		if (list.size() > 0 && list.get(0) != null) {
			Object[] objs = (Object[]) list.get(0);
			exportWeight[0] = (objs[0] == null ? 0.0 : Double
					.parseDouble(objs[0].toString()));
			exportWeight[1] = (objs[1] == null ? 0.0 : Double
					.parseDouble(objs[1].toString()));
		}
		//
		// parameters = new ArrayList<Object>();
		// hsql = "select sum(a.grossWeight),sum(a.netWeight)"
		// + " from DzscCustomsDeclaration as a "
		// + " where a.emsHeadH2k=? and a.company.id=? "
		// + " and a.effective=? ";
		// parameters.add(emsNo);
		// parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(new Boolean(true));
		// hsql += " and a.impExpType=? ";
		// parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		// list = this.find(hsql, parameters.toArray());
		// if (list.size() > 0 && list.get(0) != null) {
		// Object[] objs = (Object[]) list.get(0);
		// exportWeight[0] = exportWeight[0]
		// - (objs[0] == null ? 0.0 : Double.parseDouble(objs[0]
		// .toString()));
		// exportWeight[1] = exportWeight[1]
		// - (objs[1] == null ? 0.0 : Double.parseDouble(objs[1]
		// .toString()));
		// }
		return exportWeight;
	}

	/**
	 * 根据商品编码查找商品
	 * 
	 * @param complexCode
	 *            商品编码
	 * @return Complex 商品
	 */
	public Complex findComplexByCode(String complexCode) {
		return (Complex) this.load(Complex.class, complexCode);
	}

	/**
	 * 根据手册号码查询报关单
	 * 
	 * @param emsNo
	 *            电子手册手册编号
	 * @return List 是DzscCustomsDeclaration类型，电子手册报关单
	 */
	public List findDzscCustomsDeclaration(String emsNo) {
		String hql = "select a from DzscCustomsDeclaration a "
				+ " where a.emsHeadH2k=? "
				+ " and a.effective=? and a.company.id=? "
				+ " order by a.impExpFlag";
		return this.find(hql, new Object[] { emsNo, new Boolean(true),
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询中期核查表头
	 * 
	 * @return List 是DzscCheckHead类型，中期核查表头
	 */
	public List findDzscCheckHead() {
		String hql = "select a from DzscCheckHead a where a.company.id=? ";
		return this
				.find(hql, new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 保存中期核查表头
	 * 
	 * @param head
	 *            中期核查表头
	 */
	public void saveDzscCheckHead(DzscCheckHead head) {
		this.saveOrUpdate(head);
	}

	/**
	 * 删除中期核查表头
	 * 
	 * @param head
	 *            中期核查表头
	 */
	public void deleteDzscCheckHead(DzscCheckHead head) {
		this.delete(head);
	}

	/**
	 * 查询中期核查料件表
	 * 
	 * @param head
	 *            电子手册中期核查表头
	 * @return List 是DzscCheckImg类型，中期核查料件资料
	 */
	public List findDzscCheckImg(DzscCheckHead head) {
		String hql = "select a from DzscCheckImg a where a.checkHead.id=? "
				+ " and a.checkHead.company.id=? ";
		return this.find(hql, new Object[] { head.getId(),
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 保存中期核查料件表
	 * 
	 * @param img
	 *            中期核查料件资料
	 */
	public void saveDzscCheckImg(DzscCheckImg img) {
		this.saveOrUpdate(img);
	}

	/**
	 * 删除中期核查料件表
	 * 
	 * @param img
	 *            中期核查料件资料
	 */
	public void deleteDzscCheckImg(DzscCheckImg img) {
		this.delete(img);
	}

	/**
	 * 中期核查--添加料件--来自内部归并关系
	 * 
	 * @param dzscEmsPorHead
	 *            电子手册手册单头
	 * @param dzscCheckHead
	 *            中期核查表头
	 * @return List 是DzscInnerMergeData类型，电子手册内部归并资料
	 */
	public List findImgFromInnerMerge(DzscEmsPorHead dzscEmsPorHead,
			DzscCheckHead dzscCheckHead) {
		return this
				.find(
						"select a from DzscInnerMergeData a  left join  a.materiel "
								+ " where a.company= ? and a.imrType=?"
								+ " and a.dzscTenInnerMerge.tenSeqNum in "
								+ " (select b.tenSeqNum from DzscEmsImgBill b where b.dzscEmsPorHead.id=?)"
								+ " and a.materiel.ptNo not in "
								+ " (select c.ptNum from DzscCheckImg c where c.checkHead.id=?)",
						new Object[] { CommonUtils.getCompany(),
								MaterielType.MATERIEL, dzscEmsPorHead.getId(),
								dzscCheckHead.getId() });
	}

	/**
	 * 获得电子手册合同核销成品资料的分页 List
	 * 
	 * @param parentId
	 *            电子手册合同核销单头ID
	 * @param index
	 *            开始数据下标
	 * @param length
	 *            数据长度
	 * @return List 是DzscContractExgCav型，电子手册合同核销成品资料
	 */
	public List findDzscContractExgCav(String parentId, int index, int length) {
		return this.findPageList(
				"select a from DzscContractExgCav a where a.contractCav.id=? ",
				parentId, index, length);
	}

	/**
	 * 获得电子手册合同核销成品资料的分页 List
	 * 
	 * @param parentId
	 *            电子手册合同核销单头ID
	 * @param index
	 *            开始数据下标
	 * @param length
	 *            数据长度
	 * @return List 是DzscContractExgCav型，电子手册合同核销成品资料
	 */
	public List findDzscContractExgCavByParentId(String parentId) {
		return this
				.find(
						"select a from DzscContractExgCav a where a.contractCav.id=? order by a.seqNum ",
						parentId);
	}

	/**
	 * 查找电子手册合同核销料件资料 来自 电子手册合同核销单头ID
	 * 
	 * @param parentId
	 *            电子手册合同核销单头ID
	 * @return List 是DzscContractImgCav类型，电子手册合同核销料件资料
	 */
	public List findDzscContractImgCavByParentId(String parentId) {
		return this.find(
				"select a from DzscContractImgCav a where a.contractCav.id = ? "
						+ " order by a.seqNum ", new Object[] { parentId });
	}

	/**
	 * 查找电子手册合同核销料件资料 来自 电子帐册电子帐册合同料件ID、料件序号
	 * 
	 * @param contractCavId
	 *            电子手册合同核销单头ID
	 * @param imgSeqNum
	 *            料件序号
	 * @return DzscContractImgCav 电子手册合同核销料件资料，返回符合条件的第一条
	 */
	public DzscContractImgCav findDzscContractImgCavBySeqNum(
			String contractCavId, String imgSeqNum) {
		List list = this.find(
				"select a from DzscContractImgCav a where a.contractCav.id = ? "
						+ " and a.seqNum=?  ", new Object[] { contractCavId,
						imgSeqNum });
		if (list.size() > 0) {
			return (DzscContractImgCav) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 查找电子手册合同核销BOM资料 来自 电子帐册电子帐册合同料件ID
	 * 
	 * @param parentId
	 *            电子手册合同核销单头ID
	 * @return List 是DzscContractBomCav类型，电子手册合同核销BOM资料
	 */
	public List findDzscContractBomCavByContractParentId(String parentId) {
		return this
				.find(
						"select a from DzscContractBomCav a where a.contractCav.id = ? ",
						new Object[] { parentId });
	}

	/**
	 * 查找电子手册合同核销BOM资料 来自 电子帐册电子帐册合同料件ID、成品序号
	 * 
	 * @param contractCavId
	 *            电子手册合同核销单头ID
	 * @param imgSeqNum
	 *            成品序号
	 * @return List 是DzscContractBomCav类型，电子手册合同核销BOM资料
	 */
	public List findDzscContractBomCavByImgSeqNum(String contractCavId,
			String imgSeqNum) {
		return this.find(
				"select a from DzscContractBomCav a where a.contractCav.id = ?"
						+ " and a.seqMaterialNum=? "
						+ " order by a.seqProductNum ", new Object[] {
						contractCavId, imgSeqNum });
	}

	// + " order by a.seqNum "

	/**
	 * 查找电子手册合同核销成品数量 来自 电子手册合同核销单头ID
	 * 
	 * @param contractCavId
	 *            电子手册合同核销单头ID
	 * @return int 电子手册合同核销成品数量
	 */
	public int findDzscContractExgCavCount(String contractCavId) {
		List list = this.find(
				" select count(a.id) from DzscContractExgCav as a "
						+ "  where a.contractCav.id=? ",
				new Object[] { contractCavId });
		if (list != null && !list.isEmpty()) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	public List findDeduc(String tradeCode, String impExpFlag) {
		List list = this.find(" select a from Deduc as a "
				+ "  where a.tradeCode=? and a.inOutType=?", new Object[] {
				tradeCode, impExpFlag });
		return list;
	}

	/**
	 * 查询统计报关单数量，金额
	 */
	public double[] findCommInfoTotalAmountAndMoney(Integer impExpFlag,
			Integer commSerialNo, String emsNo, Integer impExpType,
			String[] tradeCodes, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.commAmount),sum(a.commTotalPrice)  from  DzscCustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k=?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		if (impExpFlag != null) {
			hsql += " and a.baseCustomsDeclaration.impExpFlag=?";
			parameters.add(impExpFlag);
		}
		if (commSerialNo != null) {
			hsql += " and a.commSerialNo=?";
			parameters.add(commSerialNo);
		}
		if (impExpType != null) {
			hsql += " and a.baseCustomsDeclaration.impExpType=?";
			parameters.add(impExpType);
		}
		if (tradeCodes != null && tradeCodes.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < tradeCodes.length; i++) {
				if (i == tradeCodes.length - 1) {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? ";
				} else {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? or ";
				}
				parameters.add(tradeCodes[i]);
			}
			hsql += " ) ";
		}
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(false);
		}
		List list = this.find(hsql, parameters.toArray());
		if (list.size() < 1 || list.get(0) == null) {
			return new double[] { 0d, 0d };
		} else {
			Object[] rtn = (Object[]) list.get(0);
			if (rtn[0] == null) {
				rtn[0] = 0d;
			}
			if (rtn[1] == null) {
				rtn[1] = 0d;
			}
			return new double[] { Double.parseDouble(rtn[0].toString()),
					Double.parseDouble(rtn[1].toString()) };
		}
	}

}
