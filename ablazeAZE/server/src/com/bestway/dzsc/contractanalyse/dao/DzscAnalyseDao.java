/*
 * Created on 2005-6-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.contractanalyse.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.SearchType;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.dzsc.contractanalyse.entity.TempDzscContractImg;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsBomBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;

/**
 * com.bestway.dzsc.contractanalyse.dao.DzscAnalyseDao 报关分析Dao
 * 
 * @author Administrator
 */
public class DzscAnalyseDao extends BaseDao {

	/**
	 * 进口包装统计
	 * 
	 * @param contracts
	 *            手册通关备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param wrapCode
	 *            包装种类编码
	 * @return List 存放报关单表头的包装名称、包装重量
	 */
	public List findImportWrapStat(List contracts, Date beginDate,
			Date endDate, String wrapCode) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a from "
				+ "DzscCustomsDeclaration as a where a.company.id=? and a.wrapType is not null ";
		parameters.add(CommonUtils.getCompany().getId());
		if (contracts != null && contracts.size() > 0) {
			if (contracts.size() > 0) {
				hsql += " and ( ";
			}
			for (int i = 0; i < contracts.size(); i++) {
				DzscEmsPorHead contract = (DzscEmsPorHead) contracts.get(i);
				if (i != contracts.size() - 1) {
					hsql += " a.emsHeadH2k=?  or ";
				} else if (i == contracts.size() - 1) {
					hsql += " a.emsHeadH2k=? ";
				}
				parameters.add(contract.getEmsNo());
			}
			if (contracts.size() > 0) {
				hsql += " ) ";
			}
		}
		if (wrapCode != null && !wrapCode.trim().equals("")) {
			hsql += " and a.wrapType.code=? ";
			parameters.add(wrapCode);
		}
		hsql += " and a.impExpFlag=? ";
		parameters.add(ImpExpFlag.IMPORT);

		if (beginDate != null) {
			hsql += " and a.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 报关单预录入库查询
	 * 
	 * @param contracts
	 *            手册通关备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param isEffective
	 *            是否生效，true为生效
	 * @return List 是DzscCustomsDeclaration型，报关单表头
	 */
	public List findPreCustomsDeclaration(List contracts, Date beginDate,
			Date endDate, boolean isEffective) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from DzscCustomsDeclaration as a where a.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (contracts != null && contracts.size() > 0) {
			if (contracts.size() > 0) {
				hsql += " and ( ";
			}
			for (int i = 0; i < contracts.size(); i++) {
				DzscEmsPorHead contract = (DzscEmsPorHead) contracts.get(i);
				if (i != contracts.size() - 1) {
					hsql += " a.emsHeadH2k=?  or ";
				} else if (i == contracts.size() - 1) {
					hsql += " a.emsHeadH2k=?  ";
				}
				parameters.add(contract.getEmsNo());
			}
			if (contracts.size() > 0) {
				hsql += " ) ";
			}
		}
		if (isEffective) {
			hsql += " and a.effective=? ";
			parameters.add(isEffective);
		}

		if (beginDate != null) {
			hsql += " and a.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找通关备案料件 来自 通关备案表头ID
	 * 
	 * @param contracts
	 *            手册通关备案表头
	 * @return List 是DzscEmsImgBill型，手册通关备案料件
	 */
	public List findContractImgByParentId(List contracts) {

		String hsql = " select a from DzscEmsImgBill a ";
		List<Object> parameters = new ArrayList<Object>();

		for (int i = 0; i < contracts.size(); i++) {
			DzscEmsPorHead contract = (DzscEmsPorHead) contracts.get(i);
			if (i == 0) {
				hsql += " where a.dzscEmsPorHead.id=?  ";
			} else {
				hsql += " or a.dzscEmsPorHead.id=?  ";
			}
			parameters.add(contract.getId());
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找通关备案成品 来自 通关备案表头ID
	 * 
	 * @param contracts
	 *            手册通关备案表头
	 * @return List 是DzscEmsExgBill型，手册通关备案成品
	 */
	public List findContractExgByParentId(List contracts) {

		String hsql = "select a  from DzscEmsExgBill a ";
		List<Object> parameters = new ArrayList<Object>();

		for (int i = 0; i < contracts.size(); i++) {
			DzscEmsPorHead contract = (DzscEmsPorHead) contracts.get(i);
			if (i == 0) {
				hsql += " where a.dzscEmsPorHead.id=?  ";
			} else {
				hsql += " or a.dzscEmsPorHead.id=?  ";
			}
			parameters.add(contract.getId());
		}
		return this.find(hsql, parameters.toArray());
	}

	// /**
	// * 大单进口量 = 所有使用该合同进口报关单数量之和(料件进口,料件转厂类型)
	// *
	// * @param emsNo
	// * 手册号
	// * @param commSerialNo
	// * 商品序号
	// * @param beginDate
	// * 开始日期
	// * @param endDate
	// * 结束日期
	// * @return Double 进口量
	// */
	// public Double findOrderImpAmount(String emsNo, Integer commSerialNo,
	// Date beginDate, Date endDate) {
	// List<Object> parameters = new ArrayList<Object>();
	// String hsql = " select SUM(a.commAmount) from
	// DzscCustomsDeclarationCommInfo as a where "
	// + " a.baseCustomsDeclaration.company.id=? "
	// + " and a.baseCustomsDeclaration.emsHeadH2k = ? "
	// + " and a.commSerialNo = ? "
	// + " and a.baseCustomsDeclaration.impExpType in (?,?) "
	// + " and a.baseCustomsDeclaration.impExpFlag = ? "
	// + " and a.baseCustomsDeclaration.effective = ? "
	// + " and ( a.baseCustomsDeclaration.declarationDate >= ? and
	// a.baseCustomsDeclaration.declarationDate <= ? )";
	// parameters.add(CommonUtils.getCompany().getId());
	// parameters.add(emsNo);
	// parameters.add(commSerialNo);
	// parameters.add(new Integer(ImpExpType.DIRECT_IMPORT)); // 料件进口类型
	// parameters.add(new Integer(ImpExpType.TRANSFER_FACTORY_IMPORT)); //
	// 料件转厂进口类型
	// parameters.add(new Integer(ImpExpFlag.IMPORT)); // 进口报关单不包括特殊报关单
	// parameters.add(new Boolean(true)); // 生效的报关单
	// parameters.add(CommonUtils.getBeginDate(beginDate));
	// parameters.add(CommonUtils.getEndDate(endDate));
	// List list = this.find(hsql, parameters.toArray());
	// if (list.size() > 0 && list.get(0) != null) {
	// return Double.valueOf(list.get(0).toString());
	// }
	// return 0.0;
	// }
	/**
	 * 大单进口量 = 所有使用该合同进口报关单数量之和(料件进口,料件转厂类型)
	 * 
	 * @param emsNo
	 *            手册号
	 * @param commSerialNo
	 *            商品序号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return Double 进口量
	 */
	public Double findOrderImpAmount(String emsNo, Integer commSerialNo,
			Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select SUM(a.commAmount) from DzscCustomsDeclarationCommInfo as a where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = ? "
				+ " and a.commSerialNo = ? "
				+ " and a.baseCustomsDeclaration.impExpType in (?,?) "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? "
				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and ( a.baseCustomsDeclaration.declarationDate >= ? and a.baseCustomsDeclaration.declarationDate <= ? )";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		parameters.add(commSerialNo);
		parameters.add(new Integer(ImpExpType.DIRECT_IMPORT)); // 料件进口类型
		parameters.add(new Integer(ImpExpType.TRANSFER_FACTORY_IMPORT)); // 料件转厂进口类型
		parameters.add(new Integer(ImpExpFlag.IMPORT)); // 进口报关单不包括特殊报关单
		parameters.add(new Boolean(true)); // 生效的报关单
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		}
		return 0.0;
	}

	/**
	 * 大单进口量 = 所有使用该合同进口报关单数量之和(料件进口,料件转厂类型)
	 * 
	 * @param emsNo
	 *            手册号
	 * @param commSerialNo
	 *            商品序号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return Double 进口量
	 */
	public Double findImpAmount(String emsNo, Integer commSerialNo,
			Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select SUM(a.commAmount) from DzscCustomsDeclarationCommInfo as a where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = ? "
				+ " and a.commSerialNo = ? "
				+ " and a.baseCustomsDeclaration.impExpType in (?,?,?) "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? "
				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and ( a.baseCustomsDeclaration.declarationDate >= ? and a.baseCustomsDeclaration.declarationDate <= ? )";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		parameters.add(commSerialNo);
		parameters.add(new Integer(ImpExpType.DIRECT_IMPORT)); // 料件进口类型
		parameters.add(new Integer(ImpExpType.TRANSFER_FACTORY_IMPORT)); // 料件转厂进口类型
		parameters.add(new Integer(ImpExpType.REMAIN_FORWARD_IMPORT)); // 料件转厂进口类型
		parameters.add(new Integer(ImpExpFlag.IMPORT)); // 进口报关单不包括特殊报关单
		parameters.add(new Boolean(true)); // 生效的报关单
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		}
		return 0.0;
	}

	/**
	 * 获得退料出口量
	 * 
	 * @param emsNo
	 *            手册号
	 * @param commSerialNo
	 *            商品序号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return Double 退料出口量
	 */
	public Double findBackMaterielExpAmount(String emsNo, Integer commSerialNo,
			Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select SUM(a.commAmount) from DzscCustomsDeclarationCommInfo as a where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = ? "
				+ " and a.commSerialNo = ? "
				+ " and a.baseCustomsDeclaration.impExpType = ? "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? "
				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and ( a.baseCustomsDeclaration.declarationDate >= ? and a.baseCustomsDeclaration.declarationDate <= ? )";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		parameters.add(commSerialNo);
		parameters.add(new Integer(ImpExpType.BACK_MATERIEL_EXPORT)); // 退料出口类型
		parameters.add(new Integer(ImpExpFlag.EXPORT)); // 出口报关单不包括特殊报关单
		parameters.add(new Boolean(true)); // 生效的报关单
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		}
		return 0.0;
	}

	/**
	 * 获得退料出口量
	 * 
	 * @param emsNo
	 *            手册号
	 * @param commSerialNo
	 *            商品序号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return Double 退料出口量
	 */
	public Double findBackMaterielExchangeAmount(String emsNo,
			Integer commSerialNo, Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select SUM(a.commAmount) from DzscCustomsDeclarationCommInfo as a where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = ? "
				+ " and a.commSerialNo = ? "
				+ " and a.baseCustomsDeclaration.impExpType = ? "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? "
				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and ( a.baseCustomsDeclaration.declarationDate >= ? and a.baseCustomsDeclaration.declarationDate <= ? )"
				+ " and (a.baseCustomsDeclaration.tradeMode.code=? or a.baseCustomsDeclaration.tradeMode.code=?)";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		parameters.add(commSerialNo);
		parameters.add(new Integer(ImpExpType.BACK_MATERIEL_EXPORT)); // 退料出口类型
		parameters.add(new Integer(ImpExpFlag.EXPORT)); // 出口报关单不包括特殊报关单
		parameters.add(new Boolean(true)); // 生效的报关单
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		parameters.add("0300");
		parameters.add("0700");
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		}
		return 0.0;
	}

	// /**
	// * 获得合同成品用量
	// *
	// * @param contractId
	// * @return List 是型，
	// */
	// public Double findFinishProductDosageAmount(String emsNo, Integer SeqNum)
	// {
	// List list = this
	// .find(
	// "select a.unitDosage,a.contractExg.exportAmount from ContractBom as a "
	// + " where a.contractExg.contract.emsNo = ? and a.contractImgSeqNum = ? ",
	// new Object[] { emsNo, SeqNum });
	// if (list.size() > 0) {
	// Double returnValue = 0.0;
	// for (int i = 0; i < list.size(); i++) {
	// Object[] objects = (Object[]) list.get(0);
	// returnValue += ((objects[0] != null) ? Double
	// .valueOf(objects[0].toString()) : 0.0)
	// * ((objects[1] != null) ? Double.valueOf(objects[1]
	// .toString()) : 0.0);
	// }
	// return Double.valueOf(returnValue);
	// }
	// return 0.0;
	// }

	/**
	 * 获得余料结转数量
	 * 
	 * @param emsNo
	 *            手册号
	 * @param commSerialNo
	 *            商品序号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return Double 余料结转数量，
	 */
	public Double findRemainForwardAmount(String emsNo, Integer commSerialNo,
			Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select SUM(a.commAmount) from DzscCustomsDeclarationCommInfo as a where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = ? "
				+ " and a.commSerialNo = ? "
				+ " and a.baseCustomsDeclaration.impExpType = ? "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? "
				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and ( a.baseCustomsDeclaration.declarationDate >= ? and a.baseCustomsDeclaration.declarationDate <= ? )";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		parameters.add(commSerialNo);
		parameters.add(new Integer(ImpExpType.REMAIN_FORWARD_EXPORT)); // 余料结转类型
		parameters.add(new Integer(ImpExpFlag.EXPORT)); // 出口报关单不包括特殊报关单
		parameters.add(new Boolean(true)); // 生效的报关单
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		}
		return 0.0;
	}

	/**
	 * 大单出口量=所有使用该合同出口报关单数量之和（成品出口，成品转厂出口）
	 * 
	 * @param emsNo
	 *            手册号
	 * @param commSerialNo
	 *            商品序号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param isEffective
	 *            生效类型
	 * @return Double 大单出口量
	 */
	public Double findOrderExpAmount(String emsNo, Integer commSerialNo,
			Date beginDate, Date endDate, boolean isEffective) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select SUM(a.commAmount) from DzscCustomsDeclarationCommInfo as a where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = ? "
				+ " and a.commSerialNo = ? "
				+ " and a.baseCustomsDeclaration.impExpType in (?,?) "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? "
				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and ( a.baseCustomsDeclaration.declarationDate >= ? and a.baseCustomsDeclaration.declarationDate <= ? )";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		parameters.add(commSerialNo);
		parameters.add(new Integer(ImpExpType.DIRECT_EXPORT)); // 成品出口 成品资料
		parameters.add(new Integer(ImpExpType.TRANSFER_FACTORY_EXPORT)); // 转厂出口
		// 成品资料
		parameters.add(new Integer(ImpExpFlag.EXPORT)); // 出口报关单不包括特殊报关单
		parameters.add(new Boolean(isEffective)); // 生效的报关单
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		}
		return 0.0;
	}

	/**
	 * 获得返工复出数量
	 * 
	 * @param emsNo
	 *            手册号
	 * @param commSerialNo
	 *            商品序号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return Double 返工复出数量
	 */
	public Double findReturnAmount(String emsNo, Integer commSerialNo,
			Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select SUM(a.commAmount) from DzscCustomsDeclarationCommInfo as a where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ "and a.baseCustomsDeclaration.emsHeadH2k = ? "
				+ " and a.commSerialNo = ? "
				+ " and a.baseCustomsDeclaration.impExpType =?  "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? "
				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and ( a.baseCustomsDeclaration.declarationDate >= ? and a.baseCustomsDeclaration.declarationDate <= ? )";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		parameters.add(commSerialNo);
		parameters.add(new Integer(ImpExpType.REWORK_EXPORT)); // 返工复出类型
		parameters.add(new Integer(ImpExpFlag.EXPORT)); // 出口报关单不包括特殊报关单
		parameters.add(new Boolean(true)); // 生效的报关单
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		}
		return 0.0;
	}

	/**
	 * 查找合同料件 来自 合同成品ID
	 * 
	 * @param parentId
	 *            手册通关备案表头Id
	 * @return List 是DzscEmsImgBill型，手册通关备案表头
	 */
	public List findContractImgByParentId(String parentId) {
		return this
				.find(
						"select a from DzscEmsImgBill a where a.dzscEmsPorHead.id = ? ",
						new Object[] { parentId });
	}

	/**
	 * 获得所有的合同料件,来自编码,单位,名称,规格,单位重量不相同的记录
	 * 
	 * @return List 存放手册通关备案料件的一些资料
	 */
	public List findTempContractImg() {
		return this
				.find(
						"select distinct b.code,a.name,a.spec,a.unit,a.unitWeight from DzscEmsImgBill a "
								+ " left join a.complex b "
								+ " where a.company.id= ?  "
								+ " and a.dzscEmsPorHead.declareState=? ",
						new Object[] { CommonUtils.getCompany().getId(),
								DzscState.EXECUTE });
	}

	/**
	 * 查找料件执行情况 来自进口料件
	 * 
	 * @param contract
	 *            手册通关备案表头
	 * @param tempContractImg
	 * @return List 是DzscCustomsDeclarationCommInfo型，报关单报关单物料
	 */
	public List findImpMaterielExeStateByImportMateriel(
			DzscEmsPorHead contract, TempDzscContractImg tempContractImg) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a  from DzscCustomsDeclarationCommInfo as a where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.impExpType in (?,?)  "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? "
				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = ? "
				+ " and  a.commSerialNo in "
				+ "     (select b.seqNum from DzscEmsImgBill b where "
				+ "                 b.name = ?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Integer(ImpExpType.DIRECT_IMPORT)); // 料件进口类型
		parameters.add(new Integer(ImpExpType.TRANSFER_FACTORY_IMPORT)); // 料件转厂类型
		parameters.add(new Integer(ImpExpFlag.IMPORT)); // 进口报关单不包括特殊报关单
		parameters.add(new Boolean(true)); // 生效的报关单
		parameters.add(contract.getEmsNo()); // 当前合同
		/**
		 * 编码相同,名称,规格,单位相同,单位重量 == key
		 */
		String name = tempContractImg.getName() == null ? "" : tempContractImg
				.getName().trim();
		parameters.add(name);
		if (tempContractImg.getSpec() == null
				|| "".equals(tempContractImg.getSpec().trim())) {
			hsql += " and b.spec is null ";
		} else {
			hsql += " and b.spec=?  ";
			parameters.add(tempContractImg.getSpec().trim());
		}

		if (tempContractImg.getUnit() == null
				|| "".equals(tempContractImg.getUnit().toString())) {
			hsql += " and b.unit is null ) ";
		} else {
			hsql += "  and b.unit.code=? ";
			parameters.add(tempContractImg.getUnit().getCode());
		}

		hsql += "    and b.dzscEmsPorHead.id = ?  )";

		// parameters.add(CommonUtils.getCompany().getId());
		parameters.add(contract.getId());

		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找料件执行情况 来自出口料件
	 * 
	 * @param contract
	 *            手册通关备案表头
	 * @param tempContractImg
	 *            存放合同料件的临时资料
	 * @return List 是DzscCustomsDeclarationCommInfo型，报关单报关单物料
	 */
	public List findImpMaterielExeStateByExportMateriel(
			DzscEmsPorHead contract, TempDzscContractImg tempContractImg) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a from DzscCustomsDeclarationCommInfo as a where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.impExpType in (?,?)  "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? "
				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = ? "
				+ " and  a.commSerialNo in "
				+ "     (select b.seqNum from DzscEmsImgBill b where "
				+ "                 b.name = ?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Integer(ImpExpType.BACK_MATERIEL_EXPORT)); // 退料出口类型
		parameters.add(new Integer(ImpExpType.REMAIN_FORWARD_EXPORT)); // 余料结转类型
		parameters.add(new Integer(ImpExpFlag.EXPORT)); // 进口报关单不包括特殊报关单
		parameters.add(new Boolean(true)); // 生效的报关单
		parameters.add(contract.getEmsNo()); // 当前合同
		/**
		 * 编码相同,名称,规格,单位相同,单位重量 == key
		 */
		String name = tempContractImg.getName() == null ? "" : tempContractImg
				.getName().trim();

		parameters.add(name);
		if (tempContractImg.getSpec() == null
				|| "".equals(tempContractImg.getSpec().trim())) {
			hsql += " and b.spec is null ";
		} else {
			hsql += " and b.spec=?  ";
			parameters.add(tempContractImg.getSpec().trim());
		}

		if (tempContractImg.getUnit() == null
				|| "".equals(tempContractImg.getUnit().toString())) {
			hsql += " and b.unit is null ) ";
		} else {
			hsql += "  and b.unit.code=? ";
			parameters.add(tempContractImg.getUnit().getCode());
		}

		hsql += "    and b.dzscEmsPorHead.id = ?  )";

		// parameters.add(CommonUtils.getCompany().getId());
		parameters.add(contract.getId());

		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找料件执行情况 来自进口成品(退厂返工类型)
	 * 
	 * @param contract
	 *            手册通关备案表头
	 * @param tempContractImg
	 *            存放合同料件的临时资料
	 * @return List 是DzscCustomsDeclarationCommInfo型，报关单报关单物料
	 */
	public List findImpMaterielExeStateByImportProduct(DzscEmsPorHead contract,
			TempDzscContractImg tempContractImg) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a  from DzscCustomsDeclarationCommInfo as a where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.impExpType = ?  "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? "
				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = ? "
				+ " and  a.commSerialNo in "
				+ "     (select b.dzscEmsExgBill.seqNum from DzscEmsBomBill b where "
				+ "                 b.name = ?";

		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Integer(ImpExpType.BACK_FACTORY_REWORK)); // 退厂返工类型
		parameters.add(new Integer(ImpExpFlag.IMPORT)); // 进口报关单不包括特殊报关单
		parameters.add(new Boolean(true)); // 生效的报关单
		parameters.add(contract.getEmsNo()); // 当前合同
		/**
		 * 编码相同,名称,规格,单位相同,单位重量 == key
		 */
		String name = tempContractImg.getName() == null ? "" : tempContractImg
				.getName().trim();

		parameters.add(name);
		if (tempContractImg.getSpec() == null
				|| "".equals(tempContractImg.getSpec().trim())) {
			hsql += " and b.spec is null ";
		} else {
			hsql += " and b.spec=?  ";
			parameters.add(tempContractImg.getSpec().trim());
		}

		if (tempContractImg.getUnit() == null
				|| "".equals(tempContractImg.getUnit().toString())) {
			hsql += " and b.unit is null ) ";
		} else {
			hsql += "  and b.unit.code=? ";
			parameters.add(tempContractImg.getUnit().getCode());
		}

		hsql += " and b.dzscEmsExgBill.dzscEmsPorHead.id = ?  )";
		// parameters.add(CommonUtils.getCompany().getId());
		parameters.add(contract.getId());

		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找料件执行情况 来自出口成品(返工复出类型)
	 * 
	 * @param contract
	 *            手册通关备案表头
	 * @param tempContractImg
	 *            存放合同料件的临时资料
	 * @return List 是DzscCustomsDeclarationCommInfo型，报关单报关单物料
	 */
	public List findImpMaterielExeStateByExportProduct(DzscEmsPorHead contract,
			TempDzscContractImg tempContractImg) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a from DzscCustomsDeclarationCommInfo as a where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.impExpType = ?  "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? "
				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = ? "
				+ " and  a.commSerialNo in "
				+ "     (select b.dzscEmsExgBill.seqNum from DzscEmsBomBill b where "
				+ "                 b.name = ?";

		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Integer(ImpExpType.REWORK_EXPORT)); // 返工复出类型
		parameters.add(new Integer(ImpExpFlag.EXPORT)); // 出口报关单不包括特殊报关单
		parameters.add(new Boolean(true)); // 生效的报关单
		parameters.add(contract.getEmsNo()); // 当前合同
		/**
		 * 编码相同,名称,规格,单位相同,单位重量 == key
		 */
		String name = tempContractImg.getName() == null ? "" : tempContractImg
				.getName().trim();

		parameters.add(name);
		if (tempContractImg.getSpec() == null
				|| "".equals(tempContractImg.getSpec().trim())) {
			hsql += " and b.spec is null ";
		} else {
			hsql += " and b.spec=?  ";
			parameters.add(tempContractImg.getSpec().trim());
		}

		if (tempContractImg.getUnit() == null
				|| "".equals(tempContractImg.getUnit().toString())) {
			hsql += " and b.unit is null ) ";
		} else {
			hsql += "  and b.unit.code=? ";
			parameters.add(tempContractImg.getUnit().getCode());
		}

		hsql += "  and b.dzscEmsExgBill.dzscEmsPorHead.id = ?  )";
		// parameters.add(CommonUtils.getCompany().getId());
		parameters.add(contract.getId());
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 通过合同手册,和料件编码,名称,规格,单位,单位重量 获得,成品序号 获得其单项用量
	 * 
	 * @param contractId
	 *            手册通关备案表头Id
	 * @param seqNum
	 *            合同成品序号
	 * @param tempContractImg
	 *            存放合同料件的临时资料
	 * @return List 是DzscEmsBomBill型，手册通过备案的BOM资料
	 */
	public Double getUnitDosage(String contractId, Integer seqNum,
			TempDzscContractImg tempContractImg) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a  from DzscEmsBomBill a where "
				+ "                     a.dzscEmsExgBill.dzscEmsPorHead.id = ? "
				+ "                 and  a.dzscEmsExgBill.seqNum = ? "
				+ "                 and a.name = ?"
				+ "                 and a.spec = ? "
				+ "                 and a.unit.code=? "
				+ "                 and a.complex.code = ?  "
				// + " and a.unitWeight = ? "
				+ "                 and a.company.id= ? ";

		parameters.add(contractId); // 合同id
		parameters.add(seqNum); // 合同成品序号
		/**
		 * 编码相同,名称,规格,单位相同,单位重量 == key
		 */
		String name = tempContractImg.getName() == null ? "" : tempContractImg
				.getName().trim();
		String spec = tempContractImg.getSpec() == null ? "" : tempContractImg
				.getSpec().trim();
		String unit = tempContractImg.getUnit() == null ? "" : tempContractImg
				.getUnit().getCode();
		String code = tempContractImg.getComplexCode();
		Double unitWeight = tempContractImg.getUnitWeight();
		parameters.add(name);
		parameters.add(spec);
		parameters.add(unit);
		parameters.add(code);
		// parameters.add(unitWeight);
		parameters.add(CommonUtils.getCompany().getId());
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			DzscEmsBomBill c = (DzscEmsBomBill) list.get(0);
			return c.getUnitDosage();
		}
		return 0.0;
	}

	/**
	 * 查找料件执行明细情况 来自[进口料件,转厂出口,退料出口],
	 * 
	 * @param contract
	 *            手册通关备案表头
	 * @param ContractImg
	 *            手册通关备案表头料件
	 * @param impExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 是DzscCustomsDeclarationCommInfo型，报关单报关单物料
	 */
	public List findImpMaterielExeDetail(DzscEmsPorHead contract,
			DzscEmsImgBill ContractImg, int impExpType, Date beginDate,
			Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a  from DzscCustomsDeclarationCommInfo as a where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.impExpType = ? "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? "
				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = ? "
				+ " and  a.commSerialNo = ? "
				+ " and ( a.baseCustomsDeclaration.declarationDate >= ? and a.baseCustomsDeclaration.declarationDate <= ? )";

		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Integer(impExpType)); // [进口料件,料件转厂,退料出口]
		if (impExpType == ImpExpType.BACK_MATERIEL_EXPORT) {
			parameters.add(new Integer(ImpExpFlag.EXPORT)); // 出口报关单不包括特殊报关单
		} else {
			parameters.add(new Integer(ImpExpFlag.IMPORT)); // 进口报关单不包括特殊报关单
		}
		parameters.add(new Boolean(true)); // 生效的报关单
		parameters.add(contract.getEmsNo()); // 当前合同
		parameters.add(ContractImg.getSeqNum());
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 获得所有的合同料件,来自编码,单位,名称,规格,单位重量不相同的记录
	 * 
	 * @param searchType
	 *            设置要查找的属性
	 * @param contracts
	 *            手册通关备案表头
	 * @return List 存放手册通关备案料件的一些资料
	 */
	public List findContractImg(int searchType, List contracts) {
		String hsql = " ";
		List<Object> parameters = new ArrayList<Object>();
		if (searchType == SearchType.NAME) {
			hsql += "select distinct a.name from DzscEmsImgBill a where a.company= ? ";
		} else if (searchType == SearchType.NAME_SPEC) {
			hsql += "select distinct a.name,a.spec from DzscEmsImgBill a where a.company= ?";
		} else if (searchType == SearchType.NAME_SPEC_CODE) {
			hsql += "select distinct a.name,a.spec,a.complex.code from DzscEmsImgBill a   left join a.complex where a.company= ?";
		} else if (searchType == SearchType.CODE) {
			hsql += "select distinct a.complex.code from DzscEmsImgBill a    left join a.complex where a.company= ?";
		} else if (searchType == SearchType.CODE_NAME) {
			hsql += "select distinct a.complex.code,a.name from DzscEmsImgBill a  left join a.complex   where a.company= ?";
		} else if (searchType == SearchType.NAME_SPEC_CODE_UNIT) {
			hsql += "select distinct a.name,a.spec,a.complex.code,a.unit from DzscEmsImgBill a left join a.complex  where a.company= ?";
		}
		parameters.add(CommonUtils.getCompany());
		for (int i = 0; i < contracts.size(); i++) {
			DzscEmsPorHead contract = (DzscEmsPorHead) contracts.get(i);
			if (i == 0) {
				hsql += " and (a.dzscEmsPorHead.id = ? ";
			} else {
				hsql += " or a.dzscEmsPorHead.id = ? ";
			}
			if (i == contracts.size() - 1) {
				hsql += " ) ";
			}
			parameters.add(contract.getId());
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找进口料件统计 来自料件
	 * 
	 * @param contracts
	 *            手册通关备案表头
	 * @param searchType
	 *            设置要查找的属性
	 * @param objects
	 *            searchType里查找的属性对应的值
	 * @param impExpType
	 *            进出口类型
	 * @param isBackMaterielExport
	 *            判断是退料复出还是退料出口
	 * @param isRemainFormardImpExp
	 *            判断是余料结转(转入)还是余料结转(转出)
	 * @return Double 进口料件数量
	 */
	public Double findImpMaterielExeStat(List contracts, int searchType,
			Object[] objects, int impExpType, Boolean isBackMaterielExport,
			Boolean isRemainFormardImpExp,Date beginDate,
			Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select SUM(a.commAmount) from DzscCustomsDeclarationCommInfo as a where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.impExpType = ?  "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Integer(impExpType)); // [进口料件,料件转厂,退料出口,余料结转]
		if (impExpType == ImpExpType.BACK_MATERIEL_EXPORT) {
			parameters.add(new Integer(ImpExpFlag.EXPORT)); // 出口报关单不包括特殊报关单
			if (isBackMaterielExport != null) {
				if (isBackMaterielExport) {// 是退料出口
					hsql += " and ( a.baseCustomsDeclaration.tradeMode.code = ? ";
					hsql += " or a.baseCustomsDeclaration.tradeMode.code = ? )";
					parameters.add("0265"); // 来料料件复出
					parameters.add("0664"); // 进料料件复出
				} else {// 是退料退换
					hsql += " and ( a.baseCustomsDeclaration.tradeMode.code = ? ";
					hsql += " or a.baseCustomsDeclaration.tradeMode.code = ? )";
					parameters.add("0300"); // 来料料件退换
					parameters.add("0700"); // 进料料件退换
				}
			}
		} else if (impExpType == ImpExpType.REMAIN_FORWARD_EXPORT) {
			parameters.add(new Integer(ImpExpFlag.EXPORT)); // 出口报关单不包括特殊报关单

			if (isBackMaterielExport != null) {
				if (isRemainFormardImpExp) { // 是余料结转转出
					hsql += " and ( a.baseCustomsDeclaration.tradeMode.code = ? ";
					hsql += " or a.baseCustomsDeclaration.tradeMode.code = ? )";
					parameters.add("0258"); // 来料余料结转
					parameters.add("0657"); // 进料余料结转
				}
			}

		} else if (impExpType == ImpExpType.REMAIN_FORWARD_IMPORT) {// 
			parameters.add(new Integer(ImpExpFlag.IMPORT)); // 进口报关单不包括特殊报关单
			if (isBackMaterielExport != null) {
				if (isRemainFormardImpExp) { // 是余料结转转出
					hsql += " and ( a.baseCustomsDeclaration.tradeMode.code = ? ";
					hsql += " or a.baseCustomsDeclaration.tradeMode.code = ? )";
					parameters.add("0258"); // 来料余料结转
					parameters.add("0657"); // 进料余料结转
				}
			}
		} else if (impExpType == ImpExpType.DIRECT_IMPORT) {
			parameters.add(new Integer(ImpExpFlag.IMPORT)); // 进口报关单不包括特殊报关单

//			if (isBackMaterielExport != null) {
//				if (isRemainFormardImpExp) { // 是余料结转转入
//					hsql += " and ( a.baseCustomsDeclaration.tradeMode.code = ? ";
//					hsql += " or a.baseCustomsDeclaration.tradeMode.code = ? ) ";
//					parameters.add("0258"); // 来料余料结转
//					parameters.add("0657"); // 进料余料结转
//				}
//			}

		} else if (impExpType == ImpExpType.TRANSFER_FACTORY_IMPORT) {
			parameters.add(new Integer(ImpExpFlag.IMPORT)); // 进口报关单不包括特殊报关单
		} else {
			parameters.add(new Integer(ImpExpFlag.IMPORT)); // 进口报关单不包括特殊报关单
		}
		hsql += " and a.baseCustomsDeclaration.effective = ? ";
		parameters.add(new Boolean(true)); // 生效的报关单
		/**
		 * 合同为条件生成 hsql
		 */
		for (int i = 0; i < contracts.size(); i++) {
			DzscEmsPorHead contract = (DzscEmsPorHead) contracts.get(i);
			if (i == 0) {
				hsql += " and (a.baseCustomsDeclaration.emsHeadH2k = ? ";
			} else {
				hsql += " or a.baseCustomsDeclaration.emsHeadH2k = ? ";
			}
			if (i == contracts.size() - 1) {
				hsql += " ) ";
			}
			parameters.add(contract.getEmsNo());
		}
		/**
		 * 类型为条件生成 hsql
		 */
		// if (searchType == SearchType.NAME) {
		// hsql += " and a.commName = ?";
		// parameters.add(objects[0] == null ? "" : objects[0].toString());
		// } else if (searchType == SearchType.NAME_SPEC) {
		// hsql += " and a.commName = ? and a.commSpec = ? ";
		// parameters.add(objects[0] == null ? "" : objects[0].toString());
		// parameters.add(objects[1] == null ? "" : objects[1].toString());
		// } else if (searchType == SearchType.NAME_SPEC_CODE) {
		// hsql += " and a.commName = ? and a.commSpec = ? and a.complex.code =
		// ? ";
		// parameters.add(objects[0] == null ? "" : objects[0].toString());
		// parameters.add(objects[1] == null ? "" : objects[1].toString());
		// parameters.add(objects[2] == null ? "" : objects[2].toString());
		// } else if (searchType == SearchType.CODE) {
		// hsql += " and a.complex.code = ? ";
		// parameters.add(objects[0] == null ? "" : objects[0].toString());
		// } else if (searchType == SearchType.CODE_NAME) {
		// hsql += " and a.complex.code = ? and a.commName = ? ";
		// parameters.add(objects[0] == null ? "" : objects[0].toString());
		// parameters.add(objects[1] == null ? "" : objects[1].toString());
		// } else if (searchType == SearchType.NAME_SPEC_CODE_UNIT) {
		// hsql += " and a.commName = ? and a.commSpec = ? and a.complex.code =
		// ? and a.unit.code=? ";
		// parameters.add(objects[0] == null ? "" : objects[0].toString());
		// parameters.add(objects[1] == null ? "" : objects[1].toString());
		// parameters.add(objects[2] == null ? "" : objects[2].toString());
		// parameters.add(objects[3] == null ? "" : ((Unit) objects[3])
		// .getCode());
		// }
		if (searchType == SearchType.NAME) {
			hsql += " and a.commName = ?";
			parameters.add(objects[0] == null ? "" : objects[0].toString());
		} else if (searchType == SearchType.NAME_SPEC) {
			hsql += " and a.commName = ? ";
			parameters.add(objects[0] == null ? "" : objects[0].toString());
			String spec = objects[1] == null ? "" : objects[1].toString();
			if (spec.equals("")) {
				hsql += " and (a.commSpec is null or a.commSpec = '') ";
			} else {
				hsql += " and a.commSpec = ? ";
				parameters.add(spec);
			}
		} else if (searchType == SearchType.NAME_SPEC_CODE) {
			hsql += " and a.commName = ?  ";
			parameters.add(objects[0] == null ? "" : objects[0].toString());
			String spec = objects[1] == null ? "" : objects[1].toString();
			if (spec.equals("")) {
				hsql += " and (a.commSpec is null or a.commSpec = '') ";
			} else {
				hsql += " and a.commSpec = ? ";
				parameters.add(spec);
			}
			String code = objects[2] == null ? "" : objects[2].toString();
			if (code.equals("")) {
				hsql += " and (a.complex.code is null or a.complex.code = '') ";
			} else {
				hsql += " and a.complex.code = ? ";
				parameters.add(code);
			}
			// hsql += " and a.complex.code = ? ";
			// parameters.add(objects[2] == null ? "" : objects[2].toString());
		} else if (searchType == SearchType.CODE) {
			String code = objects[0] == null ? "" : objects[0].toString();
			if (code.equals("")) {
				hsql += " and (a.complex.code is null or a.complex.code = '') ";
			} else {
				hsql += " and a.complex.code = ? ";
				parameters.add(code);
			}

			// hsql += " and a.complex.code = ? ";
			// parameters.add(objects[0] == null ? "" : objects[0].toString());
		} else if (searchType == SearchType.CODE_NAME) {
			String code = objects[0] == null ? "" : objects[0].toString();
			if (code.equals("")) {
				hsql += " and (a.complex.code is null or a.complex.code = '') ";
			} else {
				hsql += " and a.complex.code = ? ";
				parameters.add(code);
			}
			hsql += " and a.commName = ?";

			parameters.add(objects[1] == null ? "" : objects[1].toString());
		} else if (searchType == SearchType.NAME_SPEC_CODE_UNIT) {
			hsql += " and a.commName = ?  ";
			parameters.add(objects[0] == null ? "" : objects[0].toString());
			String spec = objects[1] == null ? "" : objects[1].toString();
			if (spec.equals("")) {
				hsql += " and (a.commSpec is null or a.commSpec = '') ";
			} else {
				hsql += " and a.commSpec = ? ";
				parameters.add(spec);
			}
			String code = objects[2] == null ? "" : objects[2].toString();
			if (code.equals("")) {
				hsql += " and (a.complex.code is null or a.complex.code = '') ";
			} else {
				hsql += " and a.complex.code = ? ";
				parameters.add(code);
			}

			Unit unit = objects[3] == null ? null : (Unit) objects[3];
			if (unit == null) {
				hsql += " and a.unit is null ";
			} else {
				hsql += " and a.unit = ? ";
				parameters.add(unit);
			}
		}	
		if(beginDate == null && endDate !=null){
			hsql += " and a.baseCustomsDeclaration.declarationDate<= ?";			
			parameters.add(CommonUtils.getEndDate(endDate));
		}else if(beginDate != null && endDate == null){
			hsql += " and a.baseCustomsDeclaration.declarationDate>= ?";			
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}else if(beginDate != null && endDate!=null){
			hsql += "  and a.baseCustomsDeclaration.declarationDate>= ? and a.baseCustomsDeclaration.declarationDate<= ?";
			parameters.add(CommonUtils.getBeginDate(beginDate));
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		}
		return 0.0;
	}

	// /**
	// * 获得合同成品使用量(用于库存分析)
	// *
	// * @param contract
	// * 手册通关备案表头
	// * @param contractImg
	// * 手册通关备案料件
	// * @param impExpType
	// * 进出口类型
	// * @return Double 合同成品使用量
	// */
	// // select sum( a.commAmount* b.unitDosage)
	// // from DzscCustomsDeclarationCommInfo a ,DzscEmsBomBill b
	// // where b.dzscEmsExgBill.tenSeqNum = a.commSerialNo
	// // and a.company='402881760b16d76e010b16f0acd70036'
	// // and a.baseCustomsDeclaration.emsHeadH2k='E20060913182'
	// // and a.baseCustomsDeclaration.effective is true
	// // and a.baseCustomsDeclaration.impExpFlag=1
	// // and b.imgSeqNum=5
	// // and b.company='402881760b16d76e010b16f0acd70036'
	// // and b.dzscEmsExgBill.dzscEmsPorHead.emsNo='E20060913182'
	// // and b.dzscEmsExgBill.dzscEmsPorHead.declareState='2'
	// public Double findFinishProductDosageAmount(DzscEmsPorHead contract,
	// DzscEmsImgBill contractImg, int impExpType) {
	// List<Object> parameters = new ArrayList<Object>();
	// String hsql = " select sum( a.commAmount* b.unitDosage) "
	// + " from DzscCustomsDeclarationCommInfo a ,DzscEmsBomBill b "
	// + " where b.dzscEmsExgBill.seqNum = a.commSerialNo ";
	// hsql += " and a.company.id =? ";
	// parameters.add(CommonUtils.getCompany().getId());
	// hsql += " and a.baseCustomsDeclaration.emsHeadH2k= ? ";
	// parameters.add(contract.getEmsNo());
	// hsql += " and a.baseCustomsDeclaration.effective = ? ";
	// parameters.add(new Boolean(true));
	// hsql += " and a.baseCustomsDeclaration.impExpFlag = ? ";
	// if (impExpType == ImpExpType.BACK_FACTORY_REWORK) {
	// parameters.add(new Integer(ImpExpFlag.IMPORT)); // 进口报关单不包括特殊报关单
	// } else {
	// parameters.add(new Integer(ImpExpFlag.EXPORT)); // 出口报关单不包括特殊报关单
	// }
	// hsql += " and a.baseCustomsDeclaration.impExpType = ? ";
	// parameters.add(new Integer(impExpType)); // [出口成品,转厂出口,返工复出,退厂返工]
	// // ----------------------------------------
	// hsql += " and b.imgSeqNum= ?";
	// parameters.add(contractImg.getSeqNum());
	// hsql += " and b.company.id =? ";
	// parameters.add(CommonUtils.getCompany().getId());
	// hsql += " and b.dzscEmsExgBill.dzscEmsPorHead.emsNo= ? ";
	// parameters.add(contract.getEmsNo());
	// hsql += " and b.dzscEmsExgBill.dzscEmsPorHead.declareState=? ";
	// parameters.add(DzscState.EXECUTE);
	// List list = this.find(hsql, parameters.toArray());
	// if (list.get(0) == null) {
	// return 0.0;
	// }
	// return Double.valueOf(list.get(0).toString());
	// }

	// /**
	// * 获得合同成品使用量(进口料件统计)
	// *
	// * @param contract
	// * 手册通关备案表头
	// * @param searchType
	// * 设置要查找的属性
	// * @param objects
	// * searchType里查找的属性对应的值
	// * @param impExpType
	// * 进出口类型
	// * @return Double 合同成品使用量
	// */
	// public Double findFinishProductDosageAmount(DzscEmsPorHead contract,
	// int searchType, Object[] objects, int impExpType) {
	// List<Object> parameters = new ArrayList<Object>();
	// String hsql = "select a.unitDosage,a.dzscEmsExgBill.amount from
	// DzscEmsBomBill as a "
	// + " where a.dzscEmsExgBill.dzscEmsPorHead.id = ? and "
	// + " a.dzscEmsExgBill.seqNum in "
	// + " ("
	// + " select b.commSerialNo from DzscCustomsDeclarationCommInfo as b where
	// "
	// + " b.baseCustomsDeclaration.company.id=? "
	// + " and b.baseCustomsDeclaration.impExpType = ? "
	// + " and b.baseCustomsDeclaration.impExpFlag = ? "
	// + " and b.baseCustomsDeclaration.effective = ? "
	// + " and b.baseCustomsDeclaration.emsHeadH2k = ? "
	// + " )";
	// parameters.add(contract.getId());
	// parameters.add(CommonUtils.getCompany().getId());
	// parameters.add(new Integer(impExpType)); // [出口成品,转厂出口,返工复出,退厂返工]
	// if (impExpType == ImpExpType.BACK_FACTORY_REWORK) {
	// parameters.add(new Integer(ImpExpFlag.IMPORT)); // 进口报关单不包括特殊报关单
	// } else {
	// parameters.add(new Integer(ImpExpFlag.EXPORT)); // 出口报关单不包括特殊报关单
	// }
	// parameters.add(new Boolean(true)); // 生效的报关单
	// parameters.add(contract.getEmsNo()); // 合同手册号
	// /**
	// * 类型为条件生成 hsql
	// */
	// if (searchType == SearchType.NAME) {
	// hsql += " and a.name = ?";
	// parameters.add(objects[0] == null ? "" : objects[0].toString());
	// } else if (searchType == SearchType.NAME_SPEC) {
	// hsql += " and a.name = ? and a.spec = ? ";
	// parameters.add(objects[0] == null ? "" : objects[0].toString());
	// parameters.add(objects[1] == null ? "" : objects[1].toString());
	// } else if (searchType == SearchType.NAME_SPEC_CODE) {
	// hsql += " and a.name = ? and a.spec = ? and a.complex.code = ? ";
	// parameters.add(objects[0] == null ? "" : objects[0].toString());
	// parameters.add(objects[1] == null ? "" : objects[1].toString());
	// parameters.add(objects[2] == null ? "" : objects[2].toString());
	// } else if (searchType == SearchType.CODE) {
	// hsql += " and a.complex.code = ? ";
	// parameters.add(objects[0] == null ? "" : objects[0].toString());
	// } else if (searchType == SearchType.CODE_NAME) {
	// hsql += " and a.complex.code = ? and a.name = ? ";
	// parameters.add(objects[0] == null ? "" : objects[0].toString());
	// parameters.add(objects[1] == null ? "" : objects[1].toString());
	// } else if (searchType == SearchType.NAME_SPEC_CODE_UNIT) {
	// hsql += " and a.name = ? and a.spec = ? and a.complex.code = ? and
	// a.unit.code=? ";
	// parameters.add(objects[0] == null ? "" : objects[0].toString());
	// parameters.add(objects[1] == null ? "" : objects[1].toString());
	// parameters.add(objects[2] == null ? "" : objects[2].toString());
	// parameters.add(objects[3] == null ? "" : ((Unit) objects[3])
	// .getCode());
	// }
	// List list = this.find(hsql, parameters.toArray());
	// if (list.size() > 0) {
	// Double returnValue = 0.0;
	// for (int i = 0; i < list.size(); i++) {
	// Object[] objs = (Object[]) list.get(0);
	// returnValue += ((objs[0] != null) ? Double.valueOf(objs[0]
	// .toString()) : 0.0)
	// * ((objs[1] != null) ? Double.valueOf(objs[1]
	// .toString()) : 0.0);
	// }
	// return Double.valueOf(returnValue);
	// }
	// return 0.0;
	// }

	/**
	 * 进口合同总定量
	 * 
	 * @param contracts
	 *            手册通关备案表头
	 * @param searchType
	 *            设置要查找的属性
	 * @param objects
	 *            searchType里查找的属性对应的值
	 * @return Double 进口合同总定量
	 */
	public Double findContractImportAmount(List contracts, int searchType,
			Object[] objects) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select SUM(a.amount) from DzscEmsImgBill a where a.company.id= ? ";
		parameters.add(CommonUtils.getCompany().getId());
		/**
		 * 合同为条件生成 hsql
		 */
		for (int i = 0; i < contracts.size(); i++) {
			DzscEmsPorHead contract = (DzscEmsPorHead) contracts.get(i);
			if (i == 0) {
				hsql += " and (a.dzscEmsPorHead.id = ? ";
			} else {
				hsql += " or a.dzscEmsPorHead.id = ? ";
			}
			if (i == contracts.size() - 1) {
				hsql += " ) ";
			}
			parameters.add(contract.getId());
		}
		/**
		 * 类型为条件生成 hsql
		 */
		if (searchType == SearchType.NAME) {
			hsql += " and a.name = ?";
			parameters.add(objects[0] == null ? "" : objects[0].toString());
		} else if (searchType == SearchType.NAME_SPEC) {
			hsql += " and a.name = ?  and a.spec = ? ";
			parameters.add(objects[0] == null ? "" : objects[0].toString());
			parameters.add(objects[1] == null ? "" : objects[1].toString());
		} else if (searchType == SearchType.NAME_SPEC_CODE) {
			hsql += " and a.name = ?  and a.spec = ?  and a.complex.code = ?  ";
			parameters.add(objects[0] == null ? "" : objects[0].toString());
			parameters.add(objects[1] == null ? "" : objects[1].toString());
			parameters.add(objects[2] == null ? "" : objects[2].toString());
		} else if (searchType == SearchType.CODE) {
			hsql += " and a.complex.code = ?  ";
			parameters.add(objects[0] == null ? "" : objects[0].toString());
		} else if (searchType == SearchType.CODE_NAME) {
			hsql += " and a.complex.code = ? and a.name = ?  ";
			parameters.add(objects[0] == null ? "" : objects[0].toString());
			parameters.add(objects[1] == null ? "" : objects[1].toString());
		} else if (searchType == SearchType.NAME_SPEC_CODE_UNIT) {
			hsql += " and a.name = ?  and a.spec = ?  and a.complex.code = ?  and a.unit.code=? ";
			parameters.add(objects[0] == null ? "" : objects[0].toString());
			parameters.add(objects[1] == null ? "" : objects[1].toString());
			parameters.add(objects[2] == null ? "" : objects[2].toString());
			parameters.add(objects[3] == null ? "" : ((Unit) objects[3])
					.getCode());
		}
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		}
		return 0.0;
	}

	/**
	 * 查找成品执行明细情况 来自[成品出口,转厂出口,退厂返工,返工复出]
	 * 
	 * @param contract
	 *            手册通关备案表头
	 * @param ContractExg
	 *            手册通关备案成品
	 * @param impExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 是DzscCustomsDeclarationCommInfo型，报关单物料
	 */
	public List findExpProductExeDetail(DzscEmsPorHead contract,
			DzscEmsExgBill ContractExg, int impExpType, Date beginDate,
			Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a from DzscCustomsDeclarationCommInfo as a where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.impExpType = ? "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? "
				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = ? "
				+ " and  a.commSerialNo = ? "
				+ " and ( a.baseCustomsDeclaration.declarationDate >= ? and a.baseCustomsDeclaration.declarationDate <= ? )";

		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Integer(impExpType)); // [成品出口,转厂出口,退厂返工,返工复出]
		if (impExpType == ImpExpType.BACK_FACTORY_REWORK) {
			parameters.add(new Integer(ImpExpFlag.IMPORT)); // 进口报关单不包括特殊报关单
		} else {
			parameters.add(new Integer(ImpExpFlag.EXPORT)); // 出口报关单不包括特殊报关单
		}
		parameters.add(new Boolean(true)); // 生效的报关单
		parameters.add(contract.getEmsNo()); // 当前合同
		parameters.add(ContractExg.getSeqNum());
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找成品执行总表情况 来自[成品出口,转厂出口,退厂返工,返工复出]
	 * 
	 * @param contract
	 *            手册通关备案表头
	 * @param ContractExg
	 *            手册通关备案成品
	 * @param impExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return Double 成品执行总表情况－成品总数量
	 */
	public Double findExpProductExeTotal(DzscEmsPorHead contract,
			DzscEmsExgBill ContractExg, int impExpType, Date beginDate,
			Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select SUM(a.commAmount) from DzscCustomsDeclarationCommInfo as a where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.impExpType = ? "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? "
				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = ? "
				+ " and  a.commSerialNo = ? "
				+ " and ( a.baseCustomsDeclaration.declarationDate >= ? and a.baseCustomsDeclaration.declarationDate <= ? )";

		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Integer(impExpType)); // [成品出口,转厂出口,退厂返工,返工复出]
		if (impExpType == ImpExpType.BACK_FACTORY_REWORK) {
			parameters.add(new Integer(ImpExpFlag.IMPORT)); // 进口报关单不包括特殊报关单
		} else {
			parameters.add(new Integer(ImpExpFlag.EXPORT)); // 出口报关单不包括特殊报关单
		}
		parameters.add(new Boolean(true)); // 生效的报关单
		parameters.add(contract.getEmsNo()); // 当前合同
		parameters.add(ContractExg.getSeqNum());
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		}
		return 0.0;
	}

	/**
	 * 获得所有的合同成品,来自编码,单位,名称,规格,单位重量不相同的记录
	 * 
	 * @param searchType
	 *            设置要查找的属性
	 * @param contracts
	 *            手册通关备案表头
	 * @return List 存放手册通关备案成品的一些资料
	 */
	public List findContractExg(int searchType, List contracts) {
		String hsql = " ";
		List<Object> parameters = new ArrayList<Object>();
		if (searchType == SearchType.NAME) {
			hsql += "select distinct a.name from DzscEmsExgBill a where a.company= ? ";
		} else if (searchType == SearchType.NAME_SPEC) {
			hsql += "select distinct a.name,a.spec from DzscEmsExgBill a where a.company= ?";
		} else if (searchType == SearchType.NAME_SPEC_CODE) {
			hsql += "select distinct a.name,a.spec,a.complex.code from DzscEmsExgBill a  left join a.complex  where a.company= ?";
		} else if (searchType == SearchType.CODE) {
			hsql += "select distinct a.complex.code from DzscEmsExgBill a left join a.complex  where a.company= ?";
		} else if (searchType == SearchType.CODE_NAME) {
			hsql += "select distinct a.complex.code,a.name from DzscEmsExgBill a left join a.complex where a.company= ?";
		} else if (searchType == SearchType.NAME_SPEC_CODE_UNIT) {
			hsql += "select distinct a.name,a.spec,a.complex.code,a.unit from DzscEmsExgBill a  left join a.complex where a.company= ?";
		}
		parameters.add(CommonUtils.getCompany());
		for (int i = 0; i < contracts.size(); i++) {
			DzscEmsPorHead contract = (DzscEmsPorHead) contracts.get(i);
			if (i == 0) {
				hsql += " and (a.dzscEmsPorHead.id = ? ";
			} else {
				hsql += " or a.dzscEmsPorHead.id = ? ";
			}
			if (i == contracts.size() - 1) {
				hsql += " ) ";
			}
			parameters.add(contract.getId());
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找出口合同成口总定量
	 * 
	 * @param contracts
	 *            手册通关备案表头
	 * @param searchType
	 *            设置要查找的属性
	 * @param objects
	 *            searchType里查找的属性对应的值
	 * @return LiDoublest 出口合同成口总定量
	 */
	public Double findContractExportAmount(List contracts, int searchType,
			Object[] objects) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select SUM(a.amount) from DzscEmsExgBill a where a.company.id= ? ";
		parameters.add(CommonUtils.getCompany().getId());
		/**
		 * 合同为条件生成 hsql
		 */
		for (int i = 0; i < contracts.size(); i++) {
			DzscEmsPorHead contract = (DzscEmsPorHead) contracts.get(i);
			if (i == 0) {
				hsql += " and (a.dzscEmsPorHead.id = ? ";
			} else {
				hsql += " or a.dzscEmsPorHead.id = ? ";
			}
			if (i == contracts.size() - 1) {
				hsql += " ) ";
			}
			parameters.add(contract.getId());
		}
		/**
		 * 类型为条件生成 hsql
		 */
		if (searchType == SearchType.NAME) {
			hsql += " and a.name = ?";
			parameters.add(objects[0] == null ? "" : objects[0].toString());
		} else if (searchType == SearchType.NAME_SPEC) {
			hsql += " and a.name = ?  and a.spec = ? ";
			parameters.add(objects[0] == null ? "" : objects[0].toString());
			parameters.add(objects[1] == null ? "" : objects[1].toString());
		} else if (searchType == SearchType.NAME_SPEC_CODE) {
			hsql += " and a.name = ?  and a.spec = ?  and a.complex.code = ?  ";
			parameters.add(objects[0] == null ? "" : objects[0].toString());
			parameters.add(objects[1] == null ? "" : objects[1].toString());
			parameters.add(objects[2] == null ? "" : objects[2].toString());
		} else if (searchType == SearchType.CODE) {
			hsql += " and a.complex.code = ?  ";
			parameters.add(objects[0] == null ? "" : objects[0].toString());
		} else if (searchType == SearchType.CODE_NAME) {
			hsql += " and a.complex.code = ? and a.name = ?  ";
			parameters.add(objects[0] == null ? "" : objects[0].toString());
			parameters.add(objects[1] == null ? "" : objects[1].toString());
		} else if (searchType == SearchType.NAME_SPEC_CODE_UNIT) {
			hsql += " and a.name = ?  and a.spec = ?  and a.complex.code = ?  and a.unit.code=? ";
			parameters.add(objects[0] == null ? "" : objects[0].toString());
			parameters.add(objects[1] == null ? "" : objects[1].toString());
			parameters.add(objects[2] == null ? "" : objects[2].toString());
			parameters.add(objects[3] == null ? "" : ((Unit) objects[3])
					.getCode());
		}
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		}
		return 0.0;
	}

	/**
	 * 查找出口成品统计 来自[成品出口,转厂出口,退厂返工,返工复出]
	 * 
	 * @param contracts
	 *            手册通关备案表头
	 * @param searchType
	 *            设置要查找的属性
	 * @param objects
	 *            searchType里查找的属性对应的值
	 * @param impExpType
	 *            进出口类型
	 * @return Double 出口成品统计－成品总数量
	 */
	public Double findExpFinishProductStat(List contracts, int searchType,
			Object[] objects, int impExpType, Date beginDate,
			Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select SUM(a.commAmount) from DzscCustomsDeclarationCommInfo as a where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.impExpType = ?  "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Integer(impExpType)); // [成品出口,转厂出口,退厂返工,返工复出]
		if (impExpType == ImpExpType.BACK_FACTORY_REWORK) {
			parameters.add(new Integer(ImpExpFlag.IMPORT)); // 进口报关单不包括特殊报关单
		} else {
			parameters.add(new Integer(ImpExpFlag.EXPORT)); // 出口报关单不包括特殊报关单
		}
		hsql += " and a.baseCustomsDeclaration.effective = ? ";
		parameters.add(new Boolean(true)); // 生效的报关单
		/**
		 * 合同为条件生成 hsql
		 */
		for (int i = 0; i < contracts.size(); i++) {
			DzscEmsPorHead contract = (DzscEmsPorHead) contracts.get(i);
			if (i == 0) {
				hsql += " and (a.baseCustomsDeclaration.emsHeadH2k = ? ";
			} else {
				hsql += " or a.baseCustomsDeclaration.emsHeadH2k = ? ";
			}
			if (i == contracts.size() - 1) {
				hsql += " ) ";
			}
			parameters.add(contract.getEmsNo());
		}
		/**
		 * 类型为条件生成 hsql
		 */
		if (searchType == SearchType.NAME) {
			hsql += " and a.commName = ?";
			parameters.add(objects[0] == null ? "" : objects[0].toString());
		} else if (searchType == SearchType.NAME_SPEC) {
			hsql += " and a.commName = ?  and a.commSpec = ? ";
			parameters.add(objects[0] == null ? "" : objects[0].toString());
			parameters.add(objects[1] == null ? "" : objects[1].toString());
		} else if (searchType == SearchType.NAME_SPEC_CODE) {
			hsql += " and a.commName = ?  and a.commSpec = ?  and a.complex.code = ?  ";
			parameters.add(objects[0] == null ? "" : objects[0].toString());
			parameters.add(objects[1] == null ? "" : objects[1].toString());
			parameters.add(objects[2] == null ? "" : objects[2].toString());
		} else if (searchType == SearchType.CODE) {
			hsql += " and a.complex.code = ?  ";
			parameters.add(objects[0] == null ? "" : objects[0].toString());
		} else if (searchType == SearchType.CODE_NAME) {
			hsql += " and a.complex.code = ? and a.commName = ?  ";
			parameters.add(objects[0] == null ? "" : objects[0].toString());
			parameters.add(objects[1] == null ? "" : objects[1].toString());
		} else if (searchType == SearchType.NAME_SPEC_CODE_UNIT) {
			hsql += " and a.commName = ?  and a.commSpec = ?  and a.complex.code = ?  and a.unit.code=? ";
			parameters.add(objects[0] == null ? "" : objects[0].toString());
			parameters.add(objects[1] == null ? "" : objects[1].toString());
			parameters.add(objects[2] == null ? "" : objects[2].toString());
			parameters.add(objects[3] == null ? "" : ((Unit) objects[3])
					.getCode());
		}	
		if(beginDate == null && endDate !=null){
			hsql += " and a.baseCustomsDeclaration.declarationDate<= ?";			
			parameters.add(endDate);
		}else if(beginDate != null && endDate == null){
			hsql += " and a.baseCustomsDeclaration.declarationDate>= ?";			
			parameters.add(beginDate);
		}else if(beginDate != null && endDate!=null){
			hsql += "  and a.baseCustomsDeclaration.declarationDate>= ? and a.baseCustomsDeclaration.declarationDate<= ?";
			parameters.add(beginDate);
			parameters.add(endDate);
		}
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		}
		return 0.0;
	}

	/**
	 * 获得合同成品商品名称(进口料件统计)
	 * 
	 * @param contractImgList
	 *            合同料件的临时资料
	 * @param contractList
	 *            手册通关备案表头
	 * @return List 存放通关备案BOM对应的成品的一些资料
	 */
	public List findContractBomByContractImg(
			List<TempDzscContractImg> contractImgList,
			List<DzscEmsPorHead> contractList) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select distinct a.unitDosage,a.dzscEmsExgBill.name from DzscEmsBomBill as a "
				+ " where a.company.id= ?  ";
		parameters.add(CommonUtils.getCompany().getId());

		String whereSql = "";
		for (int i = 0; i < contractList.size(); i++) {
			DzscEmsPorHead contract = contractList.get(i);

			if (i == 0) {
				whereSql += " and ( a.dzscEmsExgBill.dzscEmsPorHead.emsNo = ? ";
			} else {
				whereSql += " or and a.dzscEmsExgBill.dzscEmsPorHead.emsNo = ? ";
			}
			if (i == contractList.size() - 1) {
				whereSql += " ) ";
			}
			parameters.add(contract.getEmsNo());
		}
		for (int i = 0; i < contractImgList.size(); i++) {
			TempDzscContractImg contractImg = contractImgList.get(i);
			String name = contractImg.getName();
			String spec = contractImg.getSpec();
			Unit unit = contractImg.getUnit();

			whereSql += " and a.name = ? ";
			parameters.add(name);

			if (spec != null && !"".equals(spec)) {
				whereSql += "  and a.spec = ? ";
				parameters.add(spec);
			} else {
				whereSql += "  and ( a.spec is null or  a.spec = '' ) ";
			}
			if (unit != null) {
				whereSql += "  and a.unit = ? ";
				parameters.add(unit);
			}
		}
		if (whereSql.equals("")) {
			return new ArrayList();
		}
		hsql += whereSql;
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查询成品物料的出口数量
	 * 
	 * @param ptNo
	 * @param versionNo
	 * @param contractList
	 * @param beginDate
	 * @param endDate
	 * @param impExpTypes
	 * @return
	 */
	public double findFinishedProductExportAmount(String ptNo,
			Integer versionNo, List<DzscEmsPorHead> contractList,
			Date beginDate, Date endDate, Integer[] impExpTypes) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.declaredAmount)"
				+ " from DzscBillListBeforeCommInfo as a, "
				+ " DzscCustomsDeclaration b "
				+ " where a.afterComInfo.billList.company.id=? "
				+ " and a.afterComInfo.billList.customsSeqNo=b.unificationCode "
				+ " and b.company.id=? and b.effective=? "
				+ " and a.materiel.ptNo=? and a.bomVersion=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(true);
		parameters.add(ptNo);
		parameters.add(versionNo);
		for (int i = 0; i < contractList.size(); i++) {
			DzscEmsPorHead contract = contractList.get(i);
			if (i == 0) {
				hsql += " and ( a.afterComInfo.billList.emsHeadH2k = ? ";
			} else {
				hsql += " or a.afterComInfo.billList.emsHeadH2k = ? ";
			}
			if (i == contractList.size() - 1) {
				hsql += " ) ";
			}
			parameters.add(contract.getEmsNo());
		}
		for (int i = 0; i < impExpTypes.length; i++) {
			Integer impExpType = impExpTypes[i];
			if (i == 0) {
				hsql += " and ( b.impExpType = ? ";
			} else {
				hsql += " or b.impExpType = ? ";
			}
			if (i == impExpTypes.length - 1) {
				hsql += " ) ";
			}
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
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Double.parseDouble(list.get(0).toString());
		}
		return 0.0;
	}

	/**
	 * 根据料件查询BOM
	 * 
	 * @param materiel
	 * @return
	 */
	public List findMaterialBomApplyByDetail(Materiel materiel) {
		return this.find(
				"select a from MaterialBomDetailApply a where a.company.id = ?"
						+ " and a.materiel.id=? "
						+ " and a.versionApply.bomMasterApply.isChanged=? ",
				new Object[] { CommonUtils.getCompany().getId(),
						materiel.getId(), false });
	}

	/**
	 * 查询已生效的报关单中各项商品数量总量
	 * 
	 * @param commSerialNo
	 *            料件序号
	 * @param impExpFlag
	 *            进出口标志
	 * @param impExpType
	 *            进出口类型
	 * @param tradeCodes
	 *            贸易方式编码
	 * @param emsNo
	 *            手册编号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return double 商品数量总量
	 */
	public List findCommInfoTotalAmount(Integer impExpFlag, Integer impExpType,
			String[] tradeCodes, String emsNo, Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a.commSerialNo,  sum(a.commAmount)  from  DzscCustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.effective=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k=?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Boolean(true));
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
		// if (commSerialNo != null) {
		// hsql += " and a.commSerialNo=?";
		// parameters.add(Integer.valueOf(commSerialNo));
		// }
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
		hsql += " group by a.commSerialNo";
		return this.find(hsql, parameters.toArray());
	}
	//
	// public List findDzscExgUsedImgAmount(String emsNo)
	// {//a.dzscEmsExgBill.amount*a.unitWare/(1-a.ware/100.0))
	// String hql = " select
	// a.imgSeqNum,sum(a.dzscEmsExgBill.amount*a.unitDosage)"
	// + " from DzscEmsBomBill a where a.company.id=? "
	// + " and a.dzscEmsExgBill.dzscEmsPorHead.emsNo=? "
	// + " and a.dzscEmsExgBill.dzscEmsPorHead.declareState=? "
	// + " group by a.imgSeqNum ";
	// return this.find(hql, new Object[] { CommonUtils.getCompany().getId(),
	// emsNo, DzscState.EXECUTE });
	// }
}
