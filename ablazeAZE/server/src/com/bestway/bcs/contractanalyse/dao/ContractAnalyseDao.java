/*
 * Created on 2005-6-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractanalyse.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contractanalyse.entity.TempContractImg;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.SearchType;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;

/**
 * com.bestway.bcs.contractanalyse.dao.ContractAnalyseDao checked by lm
 * 2010-03-06
 * 
 * @author Administrator 报关分析Dao接口
 */
public class ContractAnalyseDao extends BaseDao {

	/**
	 * 进口包装统计
	 * 
	 * @param contracts
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param wrapCode
	 *            包装种类编码
	 * @param state
	 *            状态类型
	 * @return List 存放报关单表头的包装名称、包装重量
	 */
	public List findImportWrapStat(List contracts, Date beginDate,
			Date endDate, String wrapCode, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a from "
				+ "BcsCustomsDeclaration as a where a.company.id=? and a.wrapType is not null ";
		// + " and a.effective=?";
		parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(new Boolean(true));
		if (contracts != null && contracts.size() > 0) {
			if (contracts.size() > 0) {
				hsql += " and ( ";
			}
			for (int i = 0; i < contracts.size(); i++) {
				Contract contract = (Contract) contracts.get(i);
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
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (a.effective=? or " + " a.effective is null )";
			parameters.add(false);
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 报关单预录入库查询
	 * 
	 * @param contracts
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return List 是BcsCustomsDeclaration型，报关单表头
	 */
	public List findPreCustomsDeclaration(List contracts, Date beginDate,
			Date endDate, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from BcsCustomsDeclaration as a where a.company.id=? ";

		parameters.add(CommonUtils.getCompany().getId());

		if (contracts != null && contracts.size() > 0) {
			if (contracts.size() > 0) {
				hsql += " and ( ";
			}
			for (int i = 0; i < contracts.size(); i++) {
				Contract contract = (Contract) contracts.get(i);
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
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (a.effective=? or " + " a.effective is null )";
			parameters.add(false);
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
	 * 查找合同备案料件 来自 合同备案表头ID
	 * 
	 * @param contracts
	 *            合同备案表头
	 * @return List 是ContractImg型，合同备案料件
	 */
	public List findContractImgByParentId(List contracts) {

		String hsql = " select a from ContractImg a ";
		List<Object> parameters = new ArrayList<Object>();

		for (int i = 0; i < contracts.size(); i++) {
			Contract contract = (Contract) contracts.get(i);
			if (i == 0) {
				hsql += " where a.contract.id=?  ";
			} else {
				hsql += " or a.contract.id=?  ";
			}
			parameters.add(contract.getId());
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找合同备案成品 来自 合同备案表头ID
	 * 
	 * @param contracts
	 *            合同备案表头
	 * @return List 是ContractExg型，合同备案成品
	 */
	public List findContractExgByParentId(List contracts) {

		String hsql = "select a  from ContractExg a ";
		List<Object> parameters = new ArrayList<Object>();

		for (int i = 0; i < contracts.size(); i++) {
			Contract contract = (Contract) contracts.get(i);
			if (i == 0) {
				hsql += " where a.contract.id=?  ";
			} else {
				hsql += " or a.contract.id=?  ";
			}
			parameters.add(contract.getId());
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 大单进口量 = 所有使用该合同进口报关单数量之和(料件进口,料件转厂类型)
	 * 
	 * @param emsNo
	 *            帐册号
	 * @param commSerialNo
	 *            商品序号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return Double 大单进口量
	 */
	public Double findOrderImpAmount(String emsNo, Integer commSerialNo,
			Date beginDate, Date endDate, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select SUM(a.commAmount) from BcsCustomsDeclarationCommInfo as a where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = ? "
				+ " and a.commSerialNo = ? "
				+ " and a.baseCustomsDeclaration.impExpType in (?,?) "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? "
				// + " and a.baseCustomsDeclaration.effective = ? "
				+ " and ( a.baseCustomsDeclaration.declarationDate >= ? and a.baseCustomsDeclaration.declarationDate <= ? )";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		parameters.add(commSerialNo);
		parameters.add(new Integer(ImpExpType.DIRECT_IMPORT)); // 料件进口类型
		parameters.add(new Integer(ImpExpType.TRANSFER_FACTORY_IMPORT)); // 料件转厂进口类型
		parameters.add(new Integer(ImpExpFlag.IMPORT)); // 进口报关单不包括特殊报关单
		// parameters.add(new Boolean(true)); // 生效的报关单
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (a.baseCustomsDeclaration.effective=? or "
					+ " a.baseCustomsDeclaration.effective is null )";
			parameters.add(false);
		}
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		}
		return 0.0;
	}

	/**
	 * 进口量 = 所有使用该合同进口报关单数量之和(料件进口,料件转厂类型，余料转入)
	 * 
	 * @param emsNo
	 *            帐册号
	 * @param commSerialNo
	 *            商品序号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return Double 大单进口量
	 */
	public Double findImpAmount(String emsNo, Integer commSerialNo,
			Date beginDate, Date endDate, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select SUM(a.commAmount) from BcsCustomsDeclarationCommInfo as a where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = ? "
				+ " and a.commSerialNo = ? "
				+ " and a.baseCustomsDeclaration.impExpType in (?,?,?) "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? "
				// + " and a.baseCustomsDeclaration.effective = ? "
				+ " and ( a.baseCustomsDeclaration.declarationDate >= ? and a.baseCustomsDeclaration.declarationDate <= ? )";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		parameters.add(commSerialNo);
		parameters.add(new Integer(ImpExpType.DIRECT_IMPORT)); // 料件进口类型
		parameters.add(new Integer(ImpExpType.TRANSFER_FACTORY_IMPORT)); // 料件转厂进口类型
		parameters.add(new Integer(ImpExpType.REMAIN_FORWARD_IMPORT)); // 余料进口
		parameters.add(new Integer(ImpExpFlag.IMPORT)); // 进口报关单不包括特殊报关单
		// parameters.add(new Boolean(true)); // 生效的报关单
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (a.baseCustomsDeclaration.effective=? or "
					+ " a.baseCustomsDeclaration.effective is null )";
			parameters.add(false);
		}
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
	 *            帐册号
	 * @param commSerialNo
	 *            商品序号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return Double 退料出口量
	 */
	public Double findBackMaterielExpAmount(String emsNo, Integer commSerialNo,
			Date beginDate, Date endDate, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select SUM(a.commAmount) from BcsCustomsDeclarationCommInfo as a where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = ? "
				+ " and a.commSerialNo = ? "
				+ " and a.baseCustomsDeclaration.impExpType = ? "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? "
				// + " and a.baseCustomsDeclaration.effective = ? "
				+ " and ( a.baseCustomsDeclaration.declarationDate >= ? and a.baseCustomsDeclaration.declarationDate <= ? )";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		parameters.add(commSerialNo);
		parameters.add(new Integer(ImpExpType.BACK_MATERIEL_EXPORT)); // 退料出口类型
		parameters.add(new Integer(ImpExpFlag.EXPORT)); // 出口报关单不包括特殊报关单
		// parameters.add(new Boolean(true)); // 生效的报关单
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (a.baseCustomsDeclaration.effective=? or "
					+ " a.baseCustomsDeclaration.effective is null )";
			parameters.add(false);
		}
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
	 *            帐册号
	 * @param commSerialNo
	 *            商品序号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return Double 退料出口量
	 */
	public Double findBackMaterielExchangeAmount(String emsNo,
			Integer commSerialNo, Date beginDate, Date endDate, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select SUM(a.commAmount) from BcsCustomsDeclarationCommInfo as a where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = ? "
				+ " and a.commSerialNo = ? "
				+ " and a.baseCustomsDeclaration.impExpType = ? "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? "
				// + " and a.baseCustomsDeclaration.effective = ? "
				+ " and ( a.baseCustomsDeclaration.declarationDate >= ? and a.baseCustomsDeclaration.declarationDate <= ? )"
				+ " and (a.baseCustomsDeclaration.tradeMode.code=? or a.baseCustomsDeclaration.tradeMode.code=?)";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		parameters.add(commSerialNo);
		parameters.add(new Integer(ImpExpType.BACK_MATERIEL_EXPORT)); // 退料出口类型
		parameters.add(new Integer(ImpExpFlag.EXPORT)); // 出口报关单不包括特殊报关单
		// parameters.add(new Boolean(true)); // 生效的报关单
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		parameters.add("0300");
		parameters.add("0700");
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (a.baseCustomsDeclaration.effective=? or "
					+ " a.baseCustomsDeclaration.effective is null )";
			parameters.add(false);
		}
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		}
		return 0.0;
	}

	// /**
	// * 获得合同备案成品用量
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
	 * 获得合同备案成品商品名称(进口料件统计)
	 * 
	 * @param contractImgList
	 *            是List<TempContractImg>型，合同料件的临时资料
	 * @param contractList
	 *            是List<Contract>型，合同备案表头
	 * @return List 存放合同备案BOM对应的成品的一些资料
	 */
	public List findContractBomByContractImg(
			List<TempContractImg> contractImgList, List<Contract> contractList) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select distinct a.unitDosage,a.contractExg.name from ContractBom as a left join a.unit "
				+ " where a.company.id= ?  ";
		parameters.add(CommonUtils.getCompany().getId());

		String whereSql = "";
		for (int i = 0; i < contractList.size(); i++) {
			Contract contract = contractList.get(i);
			if (i == 0) {
				whereSql += " and ( a.contractExg.contract.emsNo = ? ";
			} else {
				whereSql += " or a.contractExg.contract.emsNo = ? ";
			}
			if (i == contractList.size() - 1) {
				whereSql += " ) ";
			}
			parameters.add(contract.getEmsNo());
		}
		for (int i = 0; i < contractImgList.size(); i++) {
			TempContractImg contractImg = contractImgList.get(i);
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
	 * 获得余料结转数量
	 * 
	 * @param emsNo
	 *            帐册号
	 * @param commSerialNo
	 *            商品序号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return Double 余料结转数量
	 */
	public Double findRemainForwardExportAmount(String emsNo,
			Integer commSerialNo, Date beginDate, Date endDate, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select SUM(a.commAmount) from BcsCustomsDeclarationCommInfo as a where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = ? "
				+ " and a.commSerialNo = ? "
				+ " and a.baseCustomsDeclaration.impExpType = ? "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? "
				// + " and a.baseCustomsDeclaration.effective = ? "
				+ " and ( a.baseCustomsDeclaration.declarationDate >= ? and a.baseCustomsDeclaration.declarationDate <= ? )";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		parameters.add(commSerialNo);
		parameters.add(new Integer(ImpExpType.REMAIN_FORWARD_EXPORT)); // 余料结转类型
		parameters.add(new Integer(ImpExpFlag.EXPORT)); // 出口报关单不包括特殊报关单
		// parameters.add(new Boolean(true)); // 生效的报关单
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (a.baseCustomsDeclaration.effective=? or "
					+ " a.baseCustomsDeclaration.effective is null )";
			parameters.add(false);
		}
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		}
		return 0.0;
	}
	/**
	 * 获得退料出口数量
	 * 
	 * @param emsNo
	 *            帐册号
	 * @param commSerialNo
	 *            商品序号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return Double 退料出口数量
	 */
	public Double findRemaOutExportAmount(String emsNo, Integer commSerialNo,
			Date beginDate, Date endDate, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select SUM(a.commAmount) from BcsCustomsDeclarationCommInfo as a where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = ? "
				+ " and a.commSerialNo = ? "
				+ " and a.baseCustomsDeclaration.impExpType = ? "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? "
				+ " and a.baseCustomsDeclaration.tradeMode.code in ( ?,? ) "
				// + " and a.baseCustomsDeclaration.effective = ? "
				+ " and ( a.baseCustomsDeclaration.declarationDate >= ? and a.baseCustomsDeclaration.declarationDate <= ? )";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		parameters.add(commSerialNo);
		parameters.add(new Integer(ImpExpType.BACK_MATERIEL_EXPORT)); // 料件复出
		parameters.add(new Integer(ImpExpFlag.EXPORT)); // 出口报关单不包括特殊报关单
		parameters.add("0265"); // 来料料件复出
		parameters.add("0664"); // 进料料件复出
		// parameters.add(new Boolean(true)); // 生效的报关单
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (a.baseCustomsDeclaration.effective=? or "
					+ " a.baseCustomsDeclaration.effective is null )";
			parameters.add(false);
		}
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		}
		return 0.0;
	}

	/**
	 * 获得内销数量
	 * 
	 * @param emsNo
	 *            帐册号
	 * @param commSerialNo
	 *            商品序号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return Double 内销数量
	 */
	public Double findLeiXiaoExportAmount(String emsNo, Integer commSerialNo,
			Date beginDate, Date endDate, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select SUM(a.commAmount) from BcsCustomsDeclarationCommInfo as a where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = ? "
				+ " and a.commSerialNo = ? "
				+ " and a.baseCustomsDeclaration.impExpType = ? "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? "
				+ " and a.baseCustomsDeclaration.tradeMode.code in ( ?,? ) "
				// + " and a.baseCustomsDeclaration.effective = ? "
				+ " and ( a.baseCustomsDeclaration.declarationDate >= ? and a.baseCustomsDeclaration.declarationDate <= ? )";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		parameters.add(commSerialNo);
		parameters.add(new Integer(ImpExpType.MATERIAL_DOMESTIC_SALES)); // 料件复出
		parameters.add(new Integer(ImpExpFlag.IMPORT)); // 出口报关单不包括特殊报关单
		parameters.add("0245"); // 来料料件内销
		parameters.add("0644"); // 进料料件内销
		// parameters.add(new Boolean(true)); // 生效的报关单
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (a.baseCustomsDeclaration.effective=? or "
					+ " a.baseCustomsDeclaration.effective is null )";
			parameters.add(false);
		}
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		}
		return 0.0;
	}

	/**
	 * 获得余料结转数量
	 * 
	 * @param emsNo
	 *            帐册号
	 * @param commSerialNo
	 *            商品序号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return Double 余料结转数量
	 */
	public Double findRemainForwardImportAmount(String emsNo,
			Integer commSerialNo, Date beginDate, Date endDate, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select SUM(a.commAmount) from BcsCustomsDeclarationCommInfo as a where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = ? "
				+ " and a.commSerialNo = ? "
				+ " and a.baseCustomsDeclaration.impExpType = ? "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? "
				// + " and a.baseCustomsDeclaration.effective = ? "
				+ " and ( a.baseCustomsDeclaration.declarationDate >= ? and a.baseCustomsDeclaration.declarationDate <= ? )";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		parameters.add(commSerialNo);
		parameters.add(new Integer(ImpExpType.REMAIN_FORWARD_IMPORT)); // 余料结转类型
		parameters.add(new Integer(ImpExpFlag.IMPORT)); // 出口报关单不包括特殊报关单
		// parameters.add(new Boolean(true)); // 生效的报关单
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (a.baseCustomsDeclaration.effective=? or "
					+ " a.baseCustomsDeclaration.effective is null )";
			parameters.add(false);
		}
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
	 *            帐册号
	 * @param commSerialNo
	 *            商品序号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return Double 大单出口量
	 */
	public Double findOrderExpAmount(String emsNo, Integer commSerialNo,
			Date beginDate, Date endDate, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select SUM(a.commAmount) from BcsCustomsDeclarationCommInfo as a where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = ? "
				+ " and a.commSerialNo = ? "
				+ " and a.baseCustomsDeclaration.impExpType in (?,?) "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? "
				// + " and a.baseCustomsDeclaration.effective = ? "
				+ " and ( a.baseCustomsDeclaration.declarationDate >= ? and a.baseCustomsDeclaration.declarationDate <= ? )";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		parameters.add(commSerialNo);
		parameters.add(new Integer(ImpExpType.DIRECT_EXPORT)); // 成品出口
		parameters.add(new Integer(ImpExpType.TRANSFER_FACTORY_EXPORT)); // 转厂出口
		parameters.add(new Integer(ImpExpFlag.EXPORT)); // 出口报关单不包括特殊报关单
		// parameters.add(new Boolean(isEffective)); // 生效的报关单
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (a.baseCustomsDeclaration.effective=? or "
					+ " a.baseCustomsDeclaration.effective is null )";
			parameters.add(false);
		}
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
	 *            帐册号
	 * @param commSerialNo
	 *            商品序号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return Double 返工复出数量
	 */
	public Double findReturnAmount(String emsNo, Integer commSerialNo,
			Date beginDate, Date endDate, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select SUM(a.commAmount) from BcsCustomsDeclarationCommInfo as a where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = ? "
				+ " and a.commSerialNo = ? "
				+ " and a.baseCustomsDeclaration.impExpType =?  "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? "
				// + " and a.baseCustomsDeclaration.effective = ? "
				+ " and ( a.baseCustomsDeclaration.declarationDate >= ? and a.baseCustomsDeclaration.declarationDate <= ? )";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		parameters.add(commSerialNo);
		parameters.add(new Integer(ImpExpType.REWORK_EXPORT)); // 返工复出类型
		parameters.add(new Integer(ImpExpFlag.EXPORT)); // 出口报关单不包括特殊报关单
		// parameters.add(new Boolean(true)); // 生效的报关单
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (a.baseCustomsDeclaration.effective=? or "
					+ " a.baseCustomsDeclaration.effective is null )";
			parameters.add(false);
		}
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		}
		return 0.0;
	}

	/**
	 * 获得退厂返工数量
	 * 
	 * @param emsNo
	 *            帐册号
	 * @param commSerialNo
	 *            商品序号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return Double 退厂返工数量
	 */
	public Double findBackReturnAmount(String emsNo, Integer commSerialNo,
			Date beginDate, Date endDate, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select SUM(a.commAmount) from BcsCustomsDeclarationCommInfo as a where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = ? "
				+ " and a.commSerialNo = ? "
				+ " and a.baseCustomsDeclaration.impExpType =?  "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? "
				// + " and a.baseCustomsDeclaration.effective = ? "
				+ " and ( a.baseCustomsDeclaration.declarationDate >= ? and a.baseCustomsDeclaration.declarationDate <= ? )";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		parameters.add(commSerialNo);
		parameters.add(new Integer(ImpExpType.BACK_FACTORY_REWORK)); // 退厂返工类型
		parameters.add(new Integer(ImpExpFlag.IMPORT)); // 出口报关单不包括特殊报关单
		// parameters.add(new Boolean(true)); // 生效的报关单
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (a.baseCustomsDeclaration.effective=? or "
					+ " a.baseCustomsDeclaration.effective is null )";
			parameters.add(false);
		}
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		}
		return 0.0;
	}

	/**
	 * 查找合同备案料件 来自 合同备案表头ID
	 * 
	 * @param parentId
	 *            合同备案表头ID
	 * @return List 是ContractImg型，合同备案料件
	 */
	public List findContractImgByParentId(String parentId) {
		return this.find(
				"select a from ContractImg a where a.contract.id = ? ",
				new Object[] { parentId });
	}

	/**
	 * 获得所有的合同备案料件,来自编码,单位,名称,规格,单位重量不相同的记录
	 * 
	 * @return List 存放合同备案料件的一些资料
	 */
	public List findTempContractImg() {
		return this
				.find(
						"select distinct a.complex,a.name,a.spec,a.unit,a.unitWeight from ContractImg a "// left
						// join
								// fetch
								// a.complex
								+ " where a.company.id= ?  "
								+ " and a.contract.isCancel=? "
								+ " and a.contract.declareState=? ",
						new Object[] { CommonUtils.getCompany().getId(),
								new Boolean(false), DeclareState.PROCESS_EXE });
	}

	/**
	 * 查找料件执行情况 来自进口料件
	 * 
	 * @param contractList
	 *            是List<Contract>型，合同备案表头
	 * @param tempContractImg
	 *            合同料件的临时资料
	 * @param state
	 *            状态类型
	 * @return List 是BcsCustomsDeclarationCommInfo型，报关单物料资料
	 */
	public List findImpMaterielExeStateByImportMateriel(
			List<Contract> contractList, TempContractImg tempContractImg,
			int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a  from BcsCustomsDeclarationCommInfo as a "
				+ " left join fetch a.baseCustomsDeclaration "
				+ " left join fetch a.complex "
				+ " left join fetch a.unit where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.impExpType in (?,?,?)  "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? ";
		// + " and a.baseCustomsDeclaration.effective = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Integer(ImpExpType.DIRECT_IMPORT)); // 料件进口类型
		parameters.add(new Integer(ImpExpType.TRANSFER_FACTORY_IMPORT)); // 料件转厂类型
		parameters.add(new Integer(ImpExpType.REMAIN_FORWARD_IMPORT));// 余料结转(进口报关单)
		// 成品资料 ---成品
		parameters.add(new Integer(ImpExpFlag.IMPORT)); // 进口报关单不包括特殊报关单
		// parameters.add(new Boolean(true)); // 生效的报关单
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective = ? ";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective = ? ";
			parameters.add(false);
		}
		for (int i = 0; i < contractList.size(); i++) {
			Contract contract = contractList.get(i);
			if (i == 0) {
				hsql += " and ( a.baseCustomsDeclaration.emsHeadH2k = ? ";
			} else {
				hsql += "  or a.baseCustomsDeclaration.emsHeadH2k = ? ";
			}
			if (i == contractList.size() - 1) {
				hsql += " ) ";
			}
			parameters.add(contract.getEmsNo()); // 当前合同
		}
		/**
		 * 名称,规格,单位相同 == key
		 */
		hsql += " and a.complex.code = ? ";
		String code = tempContractImg.getComplexCode();
		parameters.add(code);

		hsql += " and a.commName = ? ";
		String name = tempContractImg.getName();
		parameters.add(name);

		if (tempContractImg.getSpec() == null
				|| "".equals(tempContractImg.getSpec().trim())) {
			hsql += " and (a.commSpec is null or a.commSpec='') ";
		} else {
			hsql += " and a.commSpec=?  ";
			parameters.add(tempContractImg.getSpec());
		}

		if (tempContractImg.getUnit() == null
				|| "".equals(tempContractImg.getUnit().toString())) {
			hsql += " and a.unit is null ";
		} else {
			hsql += "  and a.unit.code=? ";
			parameters.add(tempContractImg.getUnit().getCode());
		}
		
		// System.out.println("hsql1=======" + hsql);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找料件执行情况 来自出口料件
	 * 
	 * @param contractList
	 *            是List<Contract>型，合同备案表头
	 * @param tempContractImg
	 *            合同料件的临时资料
	 * @param state
	 *            状态类型
	 * @return List 是BcsCustomsDeclarationCommInfo型，报关单物料资料
	 */
	public List findImpMaterielExeStateByExportMateriel(
			List<Contract> contractList, TempContractImg tempContractImg,
			int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a  from BcsCustomsDeclarationCommInfo as a "
				+ " left join fetch a.baseCustomsDeclaration "
				+ " left join fetch a.complex "
				+ " left join fetch a.unit where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.impExpType in (?,?)"
				+ " and a.baseCustomsDeclaration.impExpFlag = ? ";
		// + " and a.baseCustomsDeclaration.effective = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Integer(ImpExpType.BACK_MATERIEL_EXPORT)); // 退料出口类型
		parameters.add(new Integer(ImpExpType.REMAIN_FORWARD_EXPORT)); // 余料结转类型（出口报关单）
		parameters.add(new Integer(ImpExpFlag.EXPORT)); // 进口报关单不包括特殊报关单
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective = ? ";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective = ? ";
			parameters.add(false);
		} else if (state == CustomsDeclarationState.ALL) {
		}
		for (int i = 0; i < contractList.size(); i++) {
			Contract contract = contractList.get(i);
			if (i == 0) {
				hsql += " and ( a.baseCustomsDeclaration.emsHeadH2k = ? ";
			} else {
				hsql += "  or a.baseCustomsDeclaration.emsHeadH2k = ? ";
			}
			if (i == contractList.size() - 1) {
				hsql += " ) ";
			}
			parameters.add(contract.getEmsNo()); // 当前合同
		}
		/**
		 * 名称,规格,单位相同 == key
		 */
		hsql += " and a.complex.code = ? ";
		String code = tempContractImg.getComplexCode();
		parameters.add(code);

		hsql += " and a.commName = ? ";
		String name = tempContractImg.getName();
		parameters.add(name);

		if (tempContractImg.getSpec() == null
				|| "".equals(tempContractImg.getSpec().trim())) {
			hsql += " and (a.commSpec is null or a.commSpec = '' )";
		} else {
			hsql += " and a.commSpec=?  ";
			parameters.add(tempContractImg.getSpec().trim());
		}

		if (tempContractImg.getUnit() == null
				|| "".equals(tempContractImg.getUnit().toString())) {
			hsql += " and a.unit is null  ";
		} else {
			hsql += "  and a.unit.code=? ";
			parameters.add(tempContractImg.getUnit().getCode());
		}
		// System.out.println("hsql2=======" + hsql);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找料件执行情况 来自进口成品(退厂返工类型)
	 * 
	 * @param contractList
	 *            是List<Contract>型，合同备案表头
	 * @param tempContractImg
	 *            合同料件的临时资料
	 * @param state
	 *            状态类型
	 * @return List 是BcsCustomsDeclarationCommInfo型，报关单商品资料
	 */
	public List findImpMaterielExeStateByImportProduct(
			List<Contract> contractList, TempContractImg tempContractImg,
			int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql =
		// "select a  from BcsCustomsDeclarationCommInfo as a "
		// + " left join fetch a.baseCustomsDeclaration "
		// + " left join fetch a.complex "
		// + " left join fetch a.unit "
		// + " where a.baseCustomsDeclaration.company.id=? "
		// + " and a.baseCustomsDeclaration.impExpType = ?  "
		// + " and a.baseCustomsDeclaration.impExpFlag = ? "

		" select a from BcsCustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.impExpType=? "
				+ " and a.baseCustomsDeclaration.impExpFlag=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Integer(ImpExpType.BACK_FACTORY_REWORK)); // 退厂返工类型
		parameters.add(new Integer(ImpExpFlag.IMPORT)); // 进口报关单不包括特殊报关单
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective = ? ";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective = ? ";
			parameters.add(false);
		} else if (state == CustomsDeclarationState.ALL) {
		}
		for (int i = 0; i < contractList.size(); i++) {
			Contract contract = contractList.get(i);
			if (i == 0) {
				hsql += " and ( a.baseCustomsDeclaration.emsHeadH2k = ? ";
			} else {
				hsql += "  or a.baseCustomsDeclaration.emsHeadH2k = ? ";
			}
			if (i == contractList.size() - 1) {
				hsql += " ) ";
			}
			parameters.add(contract.getEmsNo()); // 当前合同
		}
		hsql += " and a.commName||'/'||a.commSpec||'/'||a.unit.code in "
				+ " (select distinct htbom.contractExg.name||'/'||htbom.contractExg.spec||'/'||htbom.contractExg.unit.code from "
				+ " ContractBom as htbom where htbom.contractExg.contract.declareState=? ";
		parameters.add(DeclareState.PROCESS_EXE);
		/**
		 * 编码相同,名称,规格,单位相同== key
		 */
		// hsql +=
		// " and htbom.complex.code||'/'||htbom.name||'/'||htbom.spec||'/'||htbom.unit.code=?) ";
		hsql += " and htbom.complex.code=?";
		// parameters.add(key);
		// hsql += " and a.complex.code = ? ";
		String code = tempContractImg.getComplexCode();
		parameters.add(code);

		hsql += " and htbom.name=?";
		// hsql += " and a.Name = ? ";
		String name = tempContractImg.getName();
		parameters.add(name);

		if (tempContractImg.getSpec() == null
				|| "".equals(tempContractImg.getSpec().trim())) {
			// hsql += " and (a.spec is null or a.spec = '' ) ";
			hsql += " and (htbom.spec is null or htbom.spec = '' ) ";
		} else {
			// hsql += " and a.spec=?  ";
			hsql += " and htbom.spec=?  ";
			parameters.add(tempContractImg.getSpec().trim());
		}

		if (tempContractImg.getUnit() == null
				|| "".equals(tempContractImg.getUnit().toString())) {
			// hsql += " and a.unit is null  ";
			hsql += " and htbom.unit is null ";
		} else {
			// hsql += "  and a.unit.code=? ";
			hsql += "  and htbom.unit.code=? ";
			parameters.add(tempContractImg.getUnit().getCode());
		}
		hsql += " )";
		// System.out.println("hsql3=======" + hsql);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找料件执行情况 来自出口成品(返工复出类型,成品出口类型,转厂出口类型)
	 * 
	 * @param contractList
	 *            是List<Contract>型，合同备案表头
	 * @param tempContractImg
	 *            合同料件的临时资料
	 * @param state
	 *            状态类型
	 * @return List 是BcsCustomsDeclarationCommInfo型，报关单商品资料
	 */
	public List findImpMaterielExeStateByExportProduct(
			List<Contract> contractList, TempContractImg tempContractImg,
			int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql =
		// "select a  from BcsCustomsDeclarationCommInfo as a "
		// + " left join fetch a.baseCustomsDeclaration "
		// + " left join fetch a.complex " + " left join fetch a.unit "
		// + " where a.baseCustomsDeclaration.company.id=? "
		// + " and a.baseCustomsDeclaration.impExpType in (?,?,?)  "
		// + " and a.baseCustomsDeclaration.impExpFlag = ? ";

		"select a from BcsCustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.impExpType in (?,?,?) "
				+ " and a.baseCustomsDeclaration.impExpFlag=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Integer(ImpExpType.REWORK_EXPORT)); // 返工复出类型
		parameters.add(new Integer(ImpExpType.DIRECT_EXPORT)); // 成品出口类型
		parameters.add(new Integer(ImpExpType.TRANSFER_FACTORY_EXPORT)); // 转厂出口类型
		parameters.add(new Integer(ImpExpFlag.EXPORT)); // 出口报关单不包括特殊报关单
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective = ? ";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective = ? ";
			parameters.add(false);
		}
		for (int i = 0; i < contractList.size(); i++) {
			Contract contract = contractList.get(i);
			if (i == 0) {
				hsql += " and ( a.baseCustomsDeclaration.emsHeadH2k = ? ";
			} else {
				hsql += "  or a.baseCustomsDeclaration.emsHeadH2k = ? ";
			}
			if (i == contractList.size() - 1) {
				hsql += " ) ";
			}
			parameters.add(contract.getEmsNo()); // 当前合同
		}
		hsql += " and str(commSerialNo)||'/'||a.commName||'/'||a.commSpec||'/'||a.unit.code in "
				+ " (select distinct str(htbom.contractExg.seqNum)||'/'||htbom.contractExg.name||'/'||htbom.contractExg.spec||'/'||htbom.contractExg.unit.code from "
				+ " ContractBom as htbom where htbom.contractExg.contract.declareState=? ";
		parameters.add(DeclareState.PROCESS_EXE);
		/**
		 * 编码相同,名称,规格,单位相同 == key
		 */
		hsql += " and htbom.complex.code=?";
		String code = tempContractImg.getComplexCode();
		parameters.add(code);
		hsql += " and htbom.name=?";
		String name = tempContractImg.getName();
		parameters.add(name);

		if (tempContractImg.getSpec() == null
				|| "".equals(tempContractImg.getSpec().trim())) {
			hsql += " and (htbom.spec is null or htbom.spec = '' ) ";
		} else {
			hsql += " and htbom.spec=?  ";
			parameters.add(tempContractImg.getSpec().trim());
		}

		if (tempContractImg.getUnit() == null
				|| "".equals(tempContractImg.getUnit().toString())) {
			hsql += " and htbom.unit is null ";
		} else {
			hsql += "  and htbom.unit.code=? ";
			parameters.add(tempContractImg.getUnit().getCode());
		}
		hsql += " )";
		System.out.println("hsql4=======" + hsql);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 通过合同手册,和料件编码,名称,规格,单位,单位重量 获得,成品序号 获得其单项用量
	 * 
	 * @param contractList
	 *            是List<Contract>型，合同备案表头
	 * @param tempContractImg
	 *            合同料件的临时资料
	 * @return List 是ContractBom型，合同备案BOM
	 */
	public List<ContractBom> getUnitDosage(List<Contract> contractList,
			TempContractImg tempContractImg) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a  from ContractBom a "
				+ " left join fetch a.contractExg b"
				+ " left join fetch b.complex "
				+ " left join fetch b.contract " + " left join fetch a.unit "
				+ " left join fetch a.complex where " + " a.company.id= ? ";
		parameters.add(CommonUtils.getCompany().getId());
		for (int i = 0; i < contractList.size(); i++) {
			Contract contract = contractList.get(i);
			if (i == 0) {
				hsql += " and ( a.contractExg.contract.emsNo = ? ";
			} else {
				hsql += "  or a.contractExg.contract.emsNo = ? ";
			}
			if (i == contractList.size() - 1) {
				hsql += " ) ";
			}
			parameters.add(contract.getEmsNo()); // 当前合同
		}
		/**
		 * 编码相同,名称,规格,单位相同 == key
		 */
		hsql += " and a.complex.code = ? ";
		String code = tempContractImg.getComplexCode();
		parameters.add(code);

		hsql += " and a.name = ? ";
		String name = tempContractImg.getName();
		parameters.add(name);

		String spec = tempContractImg.getSpec();
		if (spec == null || "".equals(spec)) {
			hsql += "  and (a.spec is null or a.spec ='' )";
		} else {
			hsql += "  and a.spec = ? ";
			parameters.add(spec);
		}
		Unit unit = tempContractImg.getUnit();
		if (unit == null) {
			hsql += "  and a.unit is null ";
		} else {
			hsql += "  and a.unit = ?  ";
			parameters.add(unit);
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找料件执行明细情况 来自(进口料件,转厂出口,退料出口)
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param ContractImg
	 *            合同备案料件
	 * @param impExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return List 是BcsCustomsDeclarationCommInfo型，报关单物料资料
	 */
	public List findImpMaterielExeDetail(Contract contract,
			ContractImg ContractImg, int impExpType, Date beginDate,
			Date endDate, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a  from BcsCustomsDeclarationCommInfo as a "
				+ " left join fetch a.baseCustomsDeclaration "
				+ " left join fetch a.complex "
				+ " left join fetch a.unit where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.impExpType = ? "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? "
				// + " and a.baseCustomsDeclaration.effective = ? "
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
		// parameters.add(new Boolean(true)); // 生效的报关单

		parameters.add(contract.getEmsNo()); // 当前合同
		parameters.add(ContractImg.getSeqNum());
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		if (state == CustomsDeclarationState.EFFECTIVED) {
			parameters.add(true);
			hsql += " and a.baseCustomsDeclaration.effective = ? ";
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			parameters.add(false);
			hsql += " and a.baseCustomsDeclaration.effective = ? ";
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 获得所有的合同备案料件,来自编码,单位,名称,规格,单位重量不相同的记录
	 * 
	 * @param searchType
	 *            设置要查找的属性
	 * @param contracts
	 *            合同备案表头
	 * @return List 存放合同备案料件的一些资料
	 */
	public List findContractImg(int searchType, List contracts) {
		String hsql = " ";
		List<Object> parameters = new ArrayList<Object>();
		if (searchType == SearchType.NAME) {
			hsql += "select  a.name from ContractImg a ";
		} else if (searchType == SearchType.NAME_SPEC) {
			hsql += "select distinct a.name,a.spec from ContractImg a ";
		} else if (searchType == SearchType.NAME_SPEC_CODE) {
			hsql += "select distinct a.name,a.spec,a.complex.code from ContractImg a  ";
		} else if (searchType == SearchType.CODE) {
			hsql += "select distinct a.complex.code from ContractImg a  ";
		} else if (searchType == SearchType.CODE_NAME) {
			hsql += "select distinct a.complex.code,a.name from ContractImg a  ";
		} else if (searchType == SearchType.NAME_SPEC_CODE_UNIT) {
			hsql += "select distinct a.name,a.spec,a.complex.code ,a.unit from ContractImg a   left join  a.complex  ";
		} else if (searchType == SearchType.NAME_SPEC_CODE_UNIT_SEQNUM){
			hsql += "select distinct a.name,a.spec,a.complex.code ,a.unit ,a.seqNum from ContractImg a   left join  a.complex  ";
		}
		// hsql += " left join fetch a.complex left join fetch a.contract ";
		hsql += " where a.company.id= ? ";
		parameters.add(CommonUtils.getCompany().getId());
		for (int i = 0; i < contracts.size(); i++) {
			Contract contract = (Contract) contracts.get(i);
			if (i == 0) {
				hsql += " and (a.contract.id = ? ";
			} else {
				hsql += " or a.contract.id = ? ";
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
	 *            合同备案表头
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
	 * @param isBackMaterielExchangeImp
	 *            判断是否是退料退换进口
	 * @param state
	 *            状态类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return Double 进口料件数量
	 */
	public Double findImpMaterielExeStat(List contracts, int searchType,
			Object[] objects, int impExpType, Boolean isBackMaterielExport,
			Boolean isRemainFormardImpExp, Boolean isBackMaterielExchangeImp,
			int state, Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select SUM(a.commAmount) from BcsCustomsDeclarationCommInfo as a where "
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

			// if (isBackMaterielExport != null) {
			// if (isRemainFormardImpExp) { // 是余料结转转入
			// hsql += " and ( a.baseCustomsDeclaration.tradeMode.code = ? ";
			// hsql += " or a.baseCustomsDeclaration.tradeMode.code = ? ) ";
			// parameters.add("0258"); // 来料余料结转
			// parameters.add("0657"); // 进料余料结转
			// }
			// }
			if (isBackMaterielExchangeImp != null) {
				if (isBackMaterielExchangeImp) {// 是否是退料退换进口
					hsql += " and ( a.baseCustomsDeclaration.tradeMode.code = ? ";
					hsql += " or a.baseCustomsDeclaration.tradeMode.code = ? )";
					parameters.add("0300"); // 来料料件退换
					parameters.add("0700"); // 进料料件退换
				}
			}
		} else if (impExpType == ImpExpType.MATERIAL_DOMESTIC_SALES) {
			parameters.add(new Integer(ImpExpFlag.IMPORT)); // 料件批准内销不包括特殊报关单
			hsql += " and ( a.baseCustomsDeclaration.tradeMode.code = ? ";
			hsql += " or a.baseCustomsDeclaration.tradeMode.code = ? )";
			parameters.add("0644");
			parameters.add("0245");

		} else if (impExpType == ImpExpType.TRANSFER_FACTORY_IMPORT) {
			parameters.add(new Integer(ImpExpFlag.IMPORT)); // 进口报关单不包括特殊报关单
		} else {
			parameters.add(new Integer(ImpExpFlag.IMPORT)); // 进口报关单不包括特殊报关单
		}
		// hsql += " and a.baseCustomsDeclaration.effective = ? ";
		// parameters.add(new Boolean(true)); // 生效的报关单
		if (state == CustomsDeclarationState.EFFECTIVED) {
			parameters.add(true);
			hsql += " and a.baseCustomsDeclaration.effective = ? ";
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			parameters.add(false);
			hsql += " and a.baseCustomsDeclaration.effective = ? ";
		}
		/**
		 * 合同为条件生成 hsql
		 */
		for (int i = 0; i < contracts.size(); i++) {
			Contract contract = (Contract) contracts.get(i);
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
		if (beginDate == null && endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<= ?";
			parameters.add(CommonUtils.getEndDate(endDate));
		} else if (beginDate != null && endDate == null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>= ?";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		} else if (beginDate != null && endDate != null) {
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
	// * 获得合同备案成品使用量(用于库存分析)
	// *
	// * @param contract
	// * 合同备案表头
	// * @param contractImg
	// * 合同备案料件
	// * @param impExpType
	// * 进出口类型
	// * @param state
	// * 状态类型
	// * @return Double 合同备案成品使用量
	// */
	// public Double findFinishProductDosageAmount(Contract contract,
	// ContractImg contractImg, int impExpType, int state) {
	// List<Object> parameters = new ArrayList<Object>();
	// String hsql = "select a.unitDosage,a.contractExg.exportAmount from
	// ContractBom as a "
	// + " where a.contractExg.contract.id = ? and "
	// + " a.contractExg.seqNum in "
	// + " ("
	// + " select b.commSerialNo from BcsCustomsDeclarationCommInfo as b where "
	// + " b.baseCustomsDeclaration.company.id=? "
	// + " and b.baseCustomsDeclaration.impExpType = ? "
	// + " and b.baseCustomsDeclaration.impExpFlag = ? ";
	// parameters.add(contract.getId());
	// parameters.add(CommonUtils.getCompany().getId());
	// parameters.add(new Integer(impExpType)); // [出口成品,转厂出口,返工复出,退厂返工]
	// if (impExpType == ImpExpType.BACK_FACTORY_REWORK) {
	// parameters.add(new Integer(ImpExpFlag.IMPORT)); // 进口报关单不包括特殊报关单
	// } else {
	// parameters.add(new Integer(ImpExpFlag.EXPORT)); // 出口报关单不包括特殊报关单
	// }
	//
	// // + " and b.baseCustomsDeclaration.effective = ? "
	// if (state == CustomsDeclarationState.EFFECTIVED) {
	// hsql += " and b.baseCustomsDeclaration.effective=?";
	// parameters.add(true);
	// } else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
	// hsql += " and (b.baseCustomsDeclaration.effective=? or "
	// + " b.baseCustomsDeclaration.effective is null )";
	// parameters.add(false);
	// }
	// hsql += " and b.baseCustomsDeclaration.emsHeadH2k = ? "
	// + " ) " + " and a.name = ? "
	// + " and a.complex.code = ? ";
	// // parameters.add(new Boolean(true)); // 生效的报关单
	// parameters.add(contract.getEmsNo()); // 合同手册号
	// /**
	// * 编码相同,名称,规格,单位相同 == key
	// */
	// String name = contractImg.getName() == null ? "" : contractImg
	// .getName().trim();
	// String code = contractImg.getComplex().getCode();
	// parameters.add(name);
	// parameters.add(code);
	//
	// if (contractImg.getSpec() != null) {
	// hsql += " and a.spec = ? ";
	// String spec = contractImg.getSpec();
	// parameters.add(spec);
	// } else {
	// hsql += " and ( a.spec is null or a.spec = '') ";
	// }
	//
	// if (contractImg.getUnit() != null) {
	// String unit = contractImg.getUnit().getCode();
	// hsql += " and a.unit.code=? ";
	// parameters.add(unit);
	// } else {
	// hsql += " and ( a.unit is null ) ";
	// }
	//
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

	// /**
	// * 获得合同备案成品使用量(进口料件统计)
	// *
	// * @param contract
	// * 合同备案表头
	// * @param searchType
	// * 设置要查找的属性
	// * @param objects
	// * searchType里查找的属性对应的值
	// * @param impExpType
	// * 进出口类型
	// * @return Double 合同备案成品使用量
	// */
	// public Double findFinishProductDosageAmount(Contract contract,
	// int searchType, Object[] objects, int impExpType) {
	// List<Object> parameters = new ArrayList<Object>();
	// String hsql = "select a.unitDosage,a.contractExg.exportAmount from
	// ContractBom as a "
	// + " where a.contractExg.contract.id = ? and "
	// + " a.contractExg.seqNum in "
	// + " ("
	// + " select b.commSerialNo from BcsCustomsDeclarationCommInfo as b where "
	// + " b.baseCustomsDeclaration.company.id=? "
	// + " and b.baseCustomsDeclaration.impExpType = ? "
	// + " and b.baseCustomsDeclaration.impExpFlag = ? "
	//
	// + " and b.baseCustomsDeclaration.emsHeadH2k = ? "
	// + " )";
	// parameters.add(contract.getId());
	// parameters.add(CommonUtils.getCompany().getId());
	// parameters.add(new Integer(impExpType)); // [出口成品,转厂出口,返工复出,退厂返工]
	// if (impExpType == ImpExpType.BACK_FACTORY_REWORK) {
	// parameters.add(new Integer(ImpExpFlag.IMPORT)); // �
	// } else {
	// parameters.add(new Integer(ImpExpFlag.EXPORT)); // �
	// }
	// parameters.add(contract.getEmsNo()); // 合同手册号
	// /**
	// * 类型为条件生成 hsql
	// */
	// if (searchType == SearchType.NAME) {
	// hsql += " and a.name = ?";
	// parameters.add(objects[0] == null ? "" : objects[0].toString());
	// } else if (searchType == SearchType.NAME_SPEC) {
	// String name = (String) objects[0];
	// String spec = objects[1] == null ? "" : objects[1].toString();
	// hsql += " and a.name = ? ";
	// parameters.add(name);
	// if (spec.equals("")) {
	// hsql += " and (a.spec is null or a.spec = '' ) ";
	// } else {
	// hsql += " and a.spec = ? ";
	// parameters.add(spec);
	// }
	// } else if (searchType == SearchType.NAME_SPEC_CODE) {
	// String name = (String) objects[0];
	// String spec = objects[1] == null ? "" : objects[1].toString();
	// String code = objects[2] == null ? "" : objects[2].toString();
	// hsql += " and a.name = ? and a.complex.code = ? ";
	// parameters.add(name);
	// parameters.add(code);
	// if (spec.equals("")) {
	// hsql += " and (a.spec is null or a.spec = '' ) ";
	// } else {
	// hsql += " and a.spec = ? ";
	// parameters.add(spec);
	// }
	// } else if (searchType == SearchType.CODE) {
	// hsql += " and a.complex.code = ? ";
	// parameters.add(objects[0] == null ? "" : objects[0].toString());
	// } else if (searchType == SearchType.CODE_NAME) {
	// hsql += " and a.complex.code = ? and a.name = ? ";
	// parameters.add(objects[0] == null ? "" : objects[0].toString());
	// parameters.add(objects[1] == null ? "" : objects[1].toString());
	// } else if (searchType == SearchType.NAME_SPEC_CODE_UNIT) {
	// String name = (String) objects[0];
	// String code = objects[2] == null ? "" : objects[2].toString();
	// String spec = objects[1] == null ? "" : objects[1].toString();
	// Unit unit = objects[3] == null ? null : ((Unit) objects[3]);
	// hsql += " and a.name = ? and a.complex.code = ? ";
	// parameters.add(name);
	// parameters.add(code);
	// if (spec.equals("")) {
	// hsql += " and (a.spec is null or a.spec = '' ) ";
	// } else {
	// hsql += " and a.spec = ? ";
	// parameters.add(spec);
	// }
	// if (unit == null) {
	// hsql += " and a.unit is null ";
	// } else {
	// hsql += " and a.unit = ? ";
	// parameters.add(unit);
	// }
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
	 *            合同备案表头
	 * @param searchType
	 *            设置要查找的属性
	 * @param objects
	 *            searchType里查找的属性对应的值
	 * @return Double 进口合同总定量
	 */
	public Double findContractImportAmount(List contracts, int searchType,
			Object[] objects) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select SUM(a.amount) from ContractImg a where a.company.id= ? ";
		parameters.add(CommonUtils.getCompany().getId());
		/**
		 * 合同为条件生成 hsql
		 */
		for (int i = 0; i < contracts.size(); i++) {
			Contract contract = (Contract) contracts.get(i);
			if (i == 0) {
				hsql += " and (a.contract.id = ? ";
			} else {
				hsql += " or a.contract.id = ? ";
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
			String name = (String) objects[0];
			String spec = objects[1] == null ? "" : objects[1].toString();
			hsql += " and a.name = ?  ";
			parameters.add(name);
			if (spec.equals("")) {
				hsql += " and (a.spec is null or a.spec = '' ) ";
			} else {
				hsql += " and a.spec = ? ";
				parameters.add(spec);
			}
		} else if (searchType == SearchType.NAME_SPEC_CODE) {
			String name = (String) objects[0];
			String spec = objects[1] == null ? "" : objects[1].toString();
			String code = objects[2] == null ? "" : objects[2].toString();
			hsql += " and a.name = ?  and a.complex.code = ?  ";
			parameters.add(name);
			parameters.add(code);
			if (spec.equals("")) {
				hsql += " and (a.spec is null or a.spec = '' ) ";
			} else {
				hsql += " and a.spec = ? ";
				parameters.add(spec);
			}
		} else if (searchType == SearchType.CODE) {
			hsql += " and a.complex.code = ?  ";
			parameters.add(objects[0] == null ? "" : objects[0].toString());
		} else if (searchType == SearchType.CODE_NAME) {
			hsql += " and a.complex.code = ? and a.name = ?  ";
			parameters.add(objects[0] == null ? "" : objects[0].toString());
			parameters.add(objects[1] == null ? "" : objects[1].toString());
		} else if (searchType == SearchType.NAME_SPEC_CODE_UNIT) {
			String name = (String) objects[0];
			String code = objects[2] == null ? "" : objects[2].toString();
			String spec = objects[1] == null ? "" : objects[1].toString();
			Unit unit = objects[3] == null ? null : ((Unit) objects[3]);
			hsql += " and a.name = ?  and a.complex.code = ?  ";
			parameters.add(name);
			parameters.add(code);
			if (spec.equals("")) {
				hsql += " and (a.spec is null or a.spec = '' ) ";
			} else {
				hsql += " and a.spec = ? ";
				parameters.add(spec);
			}
			if (unit == null) {
				hsql += " and a.unit is null ";
			} else {
				hsql += " and a.unit = ? ";
				parameters.add(unit);
			}

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
	 *            合同备案表头
	 * @param ContractExg
	 *            合同备案成品
	 * @param impExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return List 是BcsCustomsDeclarationCommInfo型，报关单商品资料
	 */
	public List findExpProductExeDetail(Contract contract,
			ContractExg ContractExg, int impExpType, Date beginDate,
			Date endDate, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a from BcsCustomsDeclarationCommInfo as a where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.impExpType = ? "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? "

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
		parameters.add(contract.getEmsNo()); // 当前合同
		parameters.add(ContractExg.getSeqNum());
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (a.baseCustomsDeclaration.effective=? or "
					+ " a.baseCustomsDeclaration.effective is null )";
			parameters.add(false);
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找成品执行总表情况 来自[成品出口,转厂出口,退厂返工,返工复出]
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param ContractExg
	 *            合同备案成品
	 * @param impExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return Double 成品执行总表情况－成品总数量
	 */
	public Double findExpProductExeTotal(Contract contract,
			ContractExg ContractExg, int impExpType, Date beginDate,
			Date endDate, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select SUM(a.commAmount) from BcsCustomsDeclarationCommInfo as a where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.impExpType = ? "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? "
				// + " and a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = ? "
				+ " and  a.commSerialNo = ? ";

		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Integer(impExpType)); // [成品出口,转厂出口,退厂返工,返工复出]
		if (impExpType == ImpExpType.BACK_FACTORY_REWORK) {
			parameters.add(new Integer(ImpExpFlag.IMPORT)); // 进口报关单不包括特殊报关单
		} else {
			parameters.add(new Integer(ImpExpFlag.EXPORT)); // 出口报关单不包括特殊报关单
		}
		// parameters.add(new Boolean(true)); // 生效的报关单

		parameters.add(contract.getEmsNo()); // 当前合同
		parameters.add(ContractExg.getSeqNum());
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate >= ?";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate <= ?";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (a.baseCustomsDeclaration.effective=? or "
					+ " a.baseCustomsDeclaration.effective is null )";
			parameters.add(false);
		}
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		}
		return 0.0;
	}

	/**
	 * 获得所有的合同备案成品,来自编码,单位,名称,规格,单位重量不相同的记录
	 * 
	 * @param searchType
	 *            设置要查找的属性
	 * @param contracts
	 *            合同备案表头
	 * @return List 存放合同备案成品的一些资料
	 */
	public List findContractExg(int searchType, List contracts) {
		String hsql = " ";
		List<Object> parameters = new ArrayList<Object>();
		if (searchType == SearchType.NAME) {
			hsql += "select distinct a.name from ContractExg a  ";
		} else if (searchType == SearchType.NAME_SPEC) {
			hsql += "select distinct a.name,a.spec from ContractExg a ";
		} else if (searchType == SearchType.NAME_SPEC_CODE) {
			hsql += "select distinct a.name,a.spec,a.complex.code from ContractExg a ";
		} else if (searchType == SearchType.CODE) {
			hsql += "select distinct a.complex.code from ContractExg a ";
		} else if (searchType == SearchType.CODE_NAME) {
			hsql += "select distinct a.complex.code,a.name from ContractExg a ";
		} else if (searchType == SearchType.NAME_SPEC_CODE_UNIT) {
			hsql += "select distinct a.name,a.spec,a.complex.code ,a.unit from ContractExg a left join a.complex  ";
		}
		// hsql += " left join fetch a.complex left join fetch a.contract ";
		hsql += " where a.company.id= ? ";
		parameters.add(CommonUtils.getCompany().getId());
		for (int i = 0; i < contracts.size(); i++) {
			Contract contract = (Contract) contracts.get(i);
			if (i == 0) {
				hsql += " and (a.contract.id = ? ";
			} else {
				hsql += " or a.contract.id = ? ";
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
	 *            合同备案表头
	 * @param searchType
	 *            设置要查找的属性
	 * @param objects
	 *            searchType里查找的属性对应的值
	 * @return Double 出口合同成口总定量
	 */
	public Double findContractExportAmount(List contracts, int searchType,
			Object[] objects) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select SUM(a.exportAmount) from ContractExg a where a.company.id= ? ";
		parameters.add(CommonUtils.getCompany().getId());
		/**
		 * 合同为条件生成 hsql
		 */
		for (int i = 0; i < contracts.size(); i++) {
			Contract contract = (Contract) contracts.get(i);
			if (i == 0) {
				hsql += " and (a.contract.id = ? ";
			} else {
				hsql += " or a.contract.id = ? ";
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
			String name = (String) objects[0];
			hsql += " and a.name = ?";
			parameters.add(name);
		} else if (searchType == SearchType.NAME_SPEC) {
			String name = (String) objects[0];
			String spec = objects[1] == null ? "" : objects[1].toString();
			hsql += " and a.name = ?  ";
			parameters.add(name);
			if (spec.equals("")) {
				hsql += " and (a.spec is null or a.spec = '' ) ";
			} else {
				hsql += " and a.spec = ? ";
				parameters.add(spec);
			}
		} else if (searchType == SearchType.NAME_SPEC_CODE) {
			String name = (String) objects[0];
			String spec = objects[1] == null ? "" : objects[1].toString();
			String code = objects[2] == null ? "" : objects[2].toString();
			hsql += " and a.name = ?  and a.complex.code = ?  ";
			parameters.add(name);
			parameters.add(code);
			if (spec.equals("")) {
				hsql += " and (a.spec is null or a.spec = '' ) ";
			} else {
				hsql += " and a.spec = ? ";
				parameters.add(spec);
			}
		} else if (searchType == SearchType.CODE) {
			hsql += " and a.complex.code = ?  ";
			parameters.add(objects[0] == null ? "" : objects[0].toString());
		} else if (searchType == SearchType.CODE_NAME) {
			hsql += " and a.complex.code = ? and a.name = ?  ";
			parameters.add(objects[0] == null ? "" : objects[0].toString());
			parameters.add(objects[1] == null ? "" : objects[1].toString());
		} else if (searchType == SearchType.NAME_SPEC_CODE_UNIT) {
			String name = (String) objects[0];
			String code = objects[2] == null ? "" : objects[2].toString();
			String spec = objects[1] == null ? "" : objects[1].toString();
			Unit unit = objects[3] == null ? null : ((Unit) objects[3]);
			hsql += " and a.name = ?  and a.complex.code = ?  ";
			parameters.add(name);
			parameters.add(code);
			if (spec.equals("")) {
				hsql += " and (a.spec is null or a.spec = '' ) ";
			} else {
				hsql += " and a.spec = ? ";
				parameters.add(spec);
			}
			if (unit == null) {
				hsql += " and a.unit is null ";
			} else {
				hsql += " and a.unit = ? ";
				parameters.add(unit);
			}
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
	 *            合同备案表头
	 * @param searchType
	 *            设置要查找的属性
	 * @param objects
	 *            searchType里查找的属性对应的值
	 * @param impExpType
	 *            进出口类型
	 * @param state
	 *            状态类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return Double 出口成品统计－成品总数量
	 */
	public Double findExpFinishProductStat(List contracts, int searchType,
			Object[] objects, int impExpType, int state, Date beginDate,
			Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select SUM(a.commAmount) from BcsCustomsDeclarationCommInfo as a where "
				+ " a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.impExpType = ? "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Integer(impExpType)); // [成品出口,转厂出口,退厂返工,返工复出]
		if (impExpType == ImpExpType.BACK_FACTORY_REWORK) {
			parameters.add(new Integer(ImpExpFlag.IMPORT)); // 进口报关单不包括特殊报关单
		} else {
			parameters.add(new Integer(ImpExpFlag.EXPORT)); // 出口报关单不包括特殊报关单
		}
		// hsql += " and a.baseCustomsDeclaration.effective = ? ";
		// parameters.add(new Boolean(true)); // 生效的报关单
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (a.baseCustomsDeclaration.effective=? or "
					+ " a.baseCustomsDeclaration.effective is null )";
			parameters.add(false);
		}
		/**
		 * 合同为条件生成 hsql
		 */
		for (int i = 0; i < contracts.size(); i++) {
			Contract contract = (Contract) contracts.get(i);
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
			hsql += " and a.complex.code = ? ";
			parameters.add(objects[2] == null ? "" : objects[2].toString());
		} else if (searchType == SearchType.CODE) {
			hsql += " and a.complex.code = ?  ";
			parameters.add(objects[0] == null ? "" : objects[0].toString());
		} else if (searchType == SearchType.CODE_NAME) {
			hsql += " and a.complex.code = ? and a.commName = ?  ";
			parameters.add(objects[0] == null ? "" : objects[0].toString());
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
			hsql += " and a.complex.code = ?   ";
			parameters.add(objects[2] == null ? "" : objects[2].toString());

			Unit unit = objects[3] == null ? null : (Unit) objects[3];
			if (unit == null) {
				hsql += " and a.unit is null ";
			} else {
				hsql += " and a.unit = ? ";
				parameters.add(unit);
			}
		}
		if (beginDate == null && endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<= ?";
			parameters.add(CommonUtils.getEndDate(endDate));
		} else if (beginDate != null && endDate == null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>= ?";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		} else if (beginDate != null && endDate != null) {
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
	// * 抓取报关单物料的总金额，根据进出口类型
	// *
	// * @param impExpFlag
	// * 进出口标志
	// * @param impExpType
	// * 进出口类型
	// * @param tradeCodes
	// * 贸易方式编码
	// * @param emsNo
	// * 帐册号
	// * @param state
	// * 状态类型
	// * @return double 报关单物料的总金额
	// */
	// public double findCommInfoTotalMoney(Integer impExpFlag,
	// Integer impExpType, String[] tradeCodes, String emsNo, int state) {
	// List<Object> parameters = new ArrayList<Object>();
	// String hsql = " select sum(a.commTotalPrice) from
	// BcsCustomsDeclarationCommInfo as a "
	// + " where a.baseCustomsDeclaration.company.id=? "
	// + " and a.baseCustomsDeclaration.effective=? "
	// + " and a.baseCustomsDeclaration.emsHeadH2k=?";
	// parameters.add(CommonUtils.getCompany().getId());
	// parameters.add(new Boolean(true));
	// parameters.add(emsNo);
	// if (impExpFlag != null) {
	// hsql += " and a.baseCustomsDeclaration.impExpFlag=?";
	// parameters.add(impExpFlag);
	// }
	// if (impExpType != null) {
	// hsql += " and a.baseCustomsDeclaration.impExpType=?";
	// parameters.add(impExpType);
	// }
	// if (tradeCodes != null && tradeCodes.length > 0) {
	// hsql += " and ( ";
	// for (int i = 0; i < tradeCodes.length; i++) {
	// if (i == tradeCodes.length - 1) {
	// hsql += " a.baseCustomsDeclaration.tradeMode.code=? ";
	// } else {
	// hsql += " a.baseCustomsDeclaration.tradeMode.code=? or ";
	// }
	// parameters.add(tradeCodes[i]);
	// }
	// hsql += " ) ";
	// }
	// if (state == CustomsDeclarationState.EFFECTIVED) {
	// hsql += " and a.baseCustomsDeclaration.effective=?";
	// parameters.add(true);
	// } else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
	// hsql += " and (a.baseCustomsDeclaration.effective=? or "
	// + " a.baseCustomsDeclaration.effective is null )";
	// parameters.add(false);
	// }
	// List list = this.find(hsql, parameters.toArray());
	// if (list.size() < 1 || list.get(0) == null) {
	// return 0;
	// } else {
	// return Double.parseDouble(list.get(0).toString());
	// }
	// }
	/**
	 * 抓取报关单物料的总金额，根据进出口类型
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @param impExpType
	 *            进出口类型
	 * @param tradeCodes
	 *            贸易方式编码
	 * @param emsNo
	 *            帐册号
	 * @param state
	 *            状态类型
	 * @return double 报关单物料的总金额
	 */
	public double findCommInfoTotalMoney(Integer impExpFlag,
			Integer impExpType, String[] tradeCodes, String emsNo, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "";
		if (impExpType == ImpExpType.DIRECT_IMPORT
				|| impExpType == ImpExpType.TRANSFER_FACTORY_IMPORT
				|| impExpType == ImpExpType.BACK_MATERIEL_EXPORT
				|| impExpType == ImpExpType.REMAIN_FORWARD_IMPORT
				|| impExpType == ImpExpType.REMAIN_FORWARD_EXPORT) {
			hsql = " select  sum(a.commTotalPrice * "
					+ " case when a.baseCustomsDeclaration.exchangeRate  is null then 1.0"
					+ " when a.baseCustomsDeclaration.exchangeRate=0.0 then 1.0 "
					+ " else a.baseCustomsDeclaration.exchangeRate end) from "
					+ " BcsCustomsDeclarationCommInfo as a ,ContractImg b"
					+ " where a.commSerialNo=b.seqNum "
					+ " and a.baseCustomsDeclaration.company.id=? "
					+ " and a.baseCustomsDeclaration.emsHeadH2k=?"
					+ " and b.contract.emsNo =? and ( b.contract.isCancel=? ) and b.contract.declareState=? ";
		} else if (impExpType == ImpExpType.DIRECT_EXPORT
				|| impExpType == ImpExpType.TRANSFER_FACTORY_EXPORT
				|| impExpType == ImpExpType.BACK_FACTORY_REWORK
				|| impExpType == ImpExpType.REWORK_EXPORT) {
			hsql = " select  sum(a.commTotalPrice  * "
					+ " case when a.baseCustomsDeclaration.exchangeRate  is null then 1.0"
					+ " when a.baseCustomsDeclaration.exchangeRate=0.0 then 1.0 "
					+ " else a.baseCustomsDeclaration.exchangeRate end) from "
					+ " BcsCustomsDeclarationCommInfo as a ,ContractExg b"
					+ " where a.commSerialNo=b.seqNum "
					+ " and a.baseCustomsDeclaration.company.id=? "
					+ " and a.baseCustomsDeclaration.emsHeadH2k=?"
					+ " and b.contract.emsNo =? and ( b.contract.isCancel=? ) and b.contract.declareState=? ";
		} else {
			return 0.0;
		}
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		parameters.add(emsNo);
		parameters.add(false);
		parameters.add(DeclareState.PROCESS_EXE);
		if (impExpFlag != null) {
			hsql += " and a.baseCustomsDeclaration.impExpFlag=?";
			parameters.add(impExpFlag);
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
			hsql += " and (a.baseCustomsDeclaration.effective=? or "
					+ " a.baseCustomsDeclaration.effective is null )";
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
	 * 统计每一份进出口报关单中各自的报关单表头Id、物料数量、总金额
	 * 
	 * @param lsContract
	 *            合同备案表头
	 * @param impExpFlag
	 *            进出口标志
	 * @param impExpType
	 *            进出口类型
	 * @param customsDeclarationCode
	 *            预录入号或报关单号或入库报关单号
	 * @param fecavBillCode
	 *            批准文号(外汇核销单号)
	 * @param trade
	 *            贸易方式
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return List 存放每一份进出口报关单中各自的报关单表头Id、物料数量、总金额
	 */
	public List findImpExpCustomsDeclarationList(List lsContract,
			Integer impExpFlag, Integer impExpType,
			String customsDeclarationCode, String fecavBillCode,
			String containerCode, Trade trade, Date beginDate, Date endDate,
			int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a.baseCustomsDeclaration.id,count(a),sum(a.commTotalPrice) from BcsCustomsDeclarationCommInfo a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				// + " and a.baseCustomsDeclaration.emsHeadH2k=? "
				+ " and a.baseCustomsDeclaration.impExpFlag=? ";
		parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(emsNo);

		parameters.add(impExpFlag);
		if (lsContract != null && lsContract.size() > 0) {
			if (lsContract.size() > 0) {
				hql += " and ( ";
			}
			for (int i = 0; i < lsContract.size(); i++) {
				Contract contract = (Contract) lsContract.get(i);
				if (i != lsContract.size() - 1) {
					hql += " a.baseCustomsDeclaration.emsHeadH2k=? or ";
				} else if (i == lsContract.size() - 1) {
					hql += " a.baseCustomsDeclaration.emsHeadH2k=?  ";
				}
				parameters.add(contract.getEmsNo());
			}
			if (lsContract.size() > 0) {
				hql += " ) ";
			}
		}
		if (impExpType != null) {
			hql += " and a.baseCustomsDeclaration.impExpType=? ";
			parameters.add(impExpType);
		}
		if (customsDeclarationCode != null
				&& !"".equals(customsDeclarationCode.trim())) {
			hql += " and a.baseCustomsDeclaration.customsDeclarationCode like ? ";
			parameters.add("%" + customsDeclarationCode + "%");
		}
		if (fecavBillCode != null && !"".equals(fecavBillCode.trim())) {
			hql += " and a.baseCustomsDeclaration.authorizeFile like ? ";
			parameters.add("%" + fecavBillCode + "%");
		}
		if (trade != null) {
			hql += " and a.baseCustomsDeclaration.tradeMode=? ";
			parameters.add(trade);
		}
		if (beginDate != null) {
			hql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hql += " and (a.baseCustomsDeclaration.effective=? or "
					+ " a.baseCustomsDeclaration.effective is null )";
			parameters.add(false);
		}

		hql += " group by a.baseCustomsDeclaration.id ";
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 进出口报关单集装箱
	 * 
	 * @param lsContract
	 *            合同备案表头
	 * @param impExpFlag
	 *            进出口标志
	 * @param impExpType
	 *            进出口类型
	 * @param containerCode
	 *            集装箱号码
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return Map<String, List> string为报关单表头id，List为集装箱号码
	 */
	public Map<String, List> findImpExpContainerCodeList(List lsContract,
			Integer impExpFlag, Integer impExpType, String containerCode,
			Date beginDate, Date endDate, int state) {
		Map<String, List> hm = new HashMap<String, List>();
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a.baseCustomsDeclaration.id,a.containerCode from BcsCustomsDeclarationContainer a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				// + " and a.baseCustomsDeclaration.emsHeadH2k=? "
				+ " and a.baseCustomsDeclaration.impExpFlag=? ";
		parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(emsNo);
		parameters.add(impExpFlag);
		if (lsContract != null && lsContract.size() > 0) {
			if (lsContract.size() > 0) {
				hql += " and ( ";
			}
			for (int i = 0; i < lsContract.size(); i++) {
				Contract contract = (Contract) lsContract.get(i);
				if (i != (lsContract.size() - 1)) {
					hql += " a.baseCustomsDeclaration.emsHeadH2k=? or ";
				} else if (i == (lsContract.size() - 1)) {
					hql += " a.baseCustomsDeclaration.emsHeadH2k=?  ";
				}
				parameters.add(contract.getEmsNo());
			}
			if (lsContract.size() > 0) {
				hql += " ) ";
			}
		}
		if (impExpType != null) {
			hql += " and a.baseCustomsDeclaration.impExpType=? ";
			parameters.add(impExpType);
		}
		if (containerCode != null && (!"".equals(containerCode.trim()))) {
			hql += " and a.containerCode like ? ";
			parameters.add("%" + containerCode + "%");
		}
		if (beginDate != null) {
			hql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hql += " and (a.baseCustomsDeclaration.effective=? or "
					+ " a.baseCustomsDeclaration.effective is null )";
			parameters.add(false);
		}
		// hql += " group by a.baseCustomsDeclaration.id ";
		List list = this.find(hql, parameters.toArray());
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			String id = objs[0].toString();
			List<String> lsContainerCode = (List<String>) hm.get(id);// new
			// ArrayList<String>();
			if (lsContainerCode == null) {
				lsContainerCode = new ArrayList<String>();
				hm.put(id, lsContainerCode);
			}
			lsContainerCode.add(objs[1].toString());
		}
		return hm;
	}

	/**
	 * 查找合同备案BOM资料
	 * 
	 * @param contractList
	 *            是List<Contract>型，合同备案表头
	 * @param lsContractImg
	 *            是List<ContractImg>型，合同备案料件
	 * @return List 合同备案BOM资料
	 */
	public List findBomDetailMaterialInContract(List<Contract> contractList,
			List<ContractImg> lsContractImg) {
		String hsql = " select a from ContractBom a where a.company= ? ";
		List list = null;
		for (int i = 0; i < contractList.size(); i++) {
			String comm = "";
			String comm1 = "";
			if (i == contractList.size() - 1) {
				comm = ")";
			} else {
				comm1 = ",";
			}
			Contract contract = (Contract) contractList.get(i);
			String where = " and a.contractExg.contract in(?";
			hsql += where + comm1 + comm;
		}
		for (int i = 0; i < lsContractImg.size(); i++) {
			ContractImg contractImg = (ContractImg) lsContractImg.get(i);
		}
		return list;
	}

	/**
	 * 统计电子化手册报关单成品物料的出口数量
	 * 
	 * @param code
	 *            商品编码代码
	 * @param name
	 *            商品名称
	 * @param spec
	 *            商品规格型号
	 * @param unit
	 *            单位代码
	 * @param contract
	 *            合同表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param impExpTypes
	 *            进出口类型
	 * @return Double 成品物料的出口数量
	 */
	public Double findFinishedProductExportAmount(Integer commSerialNo,String code, String name,
			String spec, Unit unit, Contract contract, Date beginDate,
			Date endDate, Integer[] impExpTypes) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.commAmount)"
				+ " from BcsCustomsDeclarationCommInfo a "
				+ " where a.company.id=? "
				+ " and a.baseCustomsDeclaration.effective=? "
				+ " and a.commSerialNo=? "
				+ " and a.complex.code=? and a.commName=? "
				+ " and a.commSpec =? and a.unit.code =? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k= ?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(true);
		parameters.add(commSerialNo);
		parameters.add(code);
		parameters.add(name);
		parameters.add(spec);
		parameters.add(unit.getCode());
		parameters.add(contract.getEmsNo());
		for (int i = 0; i < impExpTypes.length; i++) {
			Integer impExpType = impExpTypes[i];
			if (i == 0) {
				hsql += " and ( a.baseCustomsDeclaration.impExpType = ? ";
			} else {
				hsql += " or a.baseCustomsDeclaration.impExpType = ? ";
			}
			if (i == impExpTypes.length - 1) {
				hsql += " ) ";
			}
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
		// System.out.println("sql=" + hsql);
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Double.parseDouble(list.get(0).toString());
		}
		return 0.0;
	}
}
