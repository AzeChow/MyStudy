/*
 * Created on 2005-5-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractstat.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationCommInfo;
import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.entity.CurrRate;

/**
 * 电子化手册合同执行情况DAO com.bestway.bcs.contractstat.dao.ContractStatDao
 * 
 * @author Administrator lm checked by 2010-07-06
 */
@SuppressWarnings("unchecked")
public class ContractStatDao extends BaseDao {

	/**
	 * 查找已报关的商品
	 * 
	 * @param isMaterial
	 *            判断是否为料件，true为料件,false为成品
	 * @param seqNum
	 *            商品序号
	 * @param customer
	 *            客户
	 * @param impExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param emsNo
	 *            手册号
	 * @param state
	 *            状态类型
	 * @return List 是BcsCustomsDeclarationCommInfo型，报关单物料资料
	 */
	public List findImpExpCommInfoList(boolean isMaterial, Integer seqNum,
			String customer, String impExpType, Date beginDate, Date endDate,
			String emsNo, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from BcsCustomsDeclarationCommInfo as a ";
		// if (isImport) {
		// hsql += " , ContractImg as b where
		// a.baseCustomsDeclaration.emsHeadH2k=b.contract.emsNo "
		// + " and a.commSerialNo=b.seqNum"
		// + " and a.complex.code=b.complex.code ";
		// } else {
		// hsql += " , ContractExg as b where
		// a.baseCustomsDeclaration.emsHeadH2k=b.contract.emsNo "
		// + " and a.commSerialNo=b.seqNum "
		// + " and a.complex.code=b.complex.code ";
		// }
		if (isMaterial) {
			hsql += " where a.baseCustomsDeclaration.impExpType in (?,?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
		} else {
			hsql += " where a.baseCustomsDeclaration.impExpType in (?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
			parameters.add(ImpExpType.REWORK_EXPORT);
		}
		// hsql += " and b.contract.isCancel=? and b.contract.declareState=? "
		// + " and a.baseCustomsDeclaration.effective=? "
		hsql += " and a.baseCustomsDeclaration.company.id=? ";
		// parameters.add(new Boolean(false));
		// parameters.add(DeclareState.PROCESS_EXE);
		// parameters.add(new Boolean(true));
		parameters.add(CommonUtils.getCompany().getId());
		if (emsNo != null && !emsNo.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.emsHeadH2k=? ";
			parameters.add(emsNo);
		}
		if (seqNum != null) {
			hsql += " and a.commSerialNo=? ";
			parameters.add(seqNum);
		}
		if (customer != null && !customer.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.customer.id=? ";
			parameters.add(customer);
		}
		if (impExpType != null && !impExpType.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.impExpType=? ";
			parameters.add(Integer.valueOf(impExpType));
		}
		// else {
		// if (isImport) {
		// hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?)";
		// parameters.add(ImpExpType.DIRECT_IMPORT);
		// parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
		// parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		// parameters.add(ImpExpType.GENERAL_TRADE_IMPORT);
		// parameters.add(ImpExpType.EQUIPMENT_IMPORT);
		// } else {
		// hsql += " and a.baseCustomsDeclaration.impExpType in
		// (?,?,?,?,?,?,?,?,?)";
		// parameters.add(ImpExpType.DIRECT_EXPORT);
		// parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		// parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
		// parameters.add(ImpExpType.REWORK_EXPORT);
		// parameters.add(ImpExpType.REMIAN_MATERIAL_BACK_PORT);
		// parameters.add(ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES);
		// parameters.add(ImpExpType.GENERAL_TRADE_EXPORT);
		// parameters.add(ImpExpType.EQUIPMENT_BACK_PORT);
		// parameters.add(ImpExpType.BACK_PORT_REPAIR);
		// }
		// }
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(false);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " order by a.baseCustomsDeclaration.declarationDate,a.commSerialNo";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找已报关的商品
	 * 
	 * @param isMaterial
	 *            判断是否料件，true为料件，false为成品
	 * @param seqNum
	 *            商品序号
	 * @param code
	 *            商品编码代码
	 * @param name
	 *            商品名称
	 * @param customer
	 *            客户
	 * @param impExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param contracts
	 *            合同表头
	 * @param state
	 *            状态类型
	 * @param impExpFlag
	 *            进出口标志
	 * @return List 是BcsCustomsDeclarationCommInfo型，报关单物料资料
	 */
	public List findImpExpCommInfoListContract(boolean isMaterial,
			Integer seqNum, String code, String name, String customer,
			String impExpType, Date beginDate, Date endDate, List contracts,
			int state, int impExpFlag) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a, b from BcsCustomsDeclarationCommInfo as a "
				+ " left join fetch a.complex "
				+ " left join fetch a.baseCustomsDeclaration ";
		if (isMaterial) {
			hsql += " , ContractImg as b left join fetch b.complex"
					+ " left join fetch b.contract "
					+ " where a.baseCustomsDeclaration.emsHeadH2k=b.contract.emsNo "
					+ " and a.commSerialNo=b.seqNum";
		} else {
			hsql += " , ContractExg as b left join fetch b.complex "
					+ " left join fetch b.contract "
					+ " where a.baseCustomsDeclaration.emsHeadH2k=b.contract.emsNo "
					+ " and a.commSerialNo=b.seqNum ";
		}
		hsql += " and a.baseCustomsDeclaration.company.id=? "
				+ " and b.contract.declareState=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(DeclareState.PROCESS_EXE);// 只统计正在执行 小凯修改 2010/5/25
		int temp = 0;
		for (int i = 0; i < contracts.size(); i++) {
			String emsNo = ((Contract) contracts.get(i)).getEmsNo();
			if (emsNo != null && (!emsNo.trim().equals(""))) {
				temp++;
				if (temp == 1) {
					hsql += " and ( b.contract.emsNo=? ";
					parameters.add(emsNo);
				} else {
					hsql += " or b.contract.emsNo=? ";
					parameters.add(emsNo);
				}
			}
		}
		if (temp != 0) {
			hsql += " )";
		}
		if (seqNum != null) {
			hsql += " and a.commSerialNo=? ";
			parameters.add(seqNum);
		}
		if (code != null && (!code.equals(""))) {
			hsql += " and a.complex.code=? ";
			parameters.add(code);
		}
		if (name != null && (!name.equals(""))) {
			hsql += " and a.commName=? ";
			parameters.add(name);
		}
		if (customer != null && !customer.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.customer.id=? ";
			parameters.add(customer);
		}
		if (impExpFlag == 2) {
			hsql += " and a.baseCustomsDeclaration.impExpFlag=?";
			parameters.add(2);
			if (isMaterial) {
				hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?,?)";
				parameters.add(ImpExpType.DIRECT_IMPORT);
				parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
				parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
				parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
				parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
				parameters.add(ImpExpType.MATERIAL_DOMESTIC_SALES);
			} else {
				hsql += " and a.baseCustomsDeclaration.impExpType in "
						+ "(?,?,?,?)";
				parameters.add(ImpExpType.DIRECT_EXPORT);
				parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
				parameters.add(ImpExpType.BACK_FACTORY_REWORK);
				parameters.add(ImpExpType.REWORK_EXPORT);
			}
		} else {
			if (impExpType != null && !impExpType.trim().equals("")) {
				hsql += " and a.baseCustomsDeclaration.impExpType=? ";
				parameters.add(Integer.valueOf(impExpType));
			} else {
				if (isMaterial) {
					hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?,?)";
					parameters.add(ImpExpType.DIRECT_IMPORT);
					parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
					parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
					parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
					parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
					parameters.add(ImpExpType.MATERIAL_DOMESTIC_SALES);// edit
				} else {
					hsql += " and a.baseCustomsDeclaration.impExpType in "
							+ "(?,?,?,?)";
					parameters.add(ImpExpType.DIRECT_EXPORT);
					parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
					parameters.add(ImpExpType.BACK_FACTORY_REWORK);
					parameters.add(ImpExpType.REWORK_EXPORT);
				}
			}
		}

		if (impExpFlag == 2) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		}
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(false);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " order by a.baseCustomsDeclaration.declarationDate,a.baseCustomsDeclaration.customsDeclarationCode,a.commSerialNo";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找已报关的商品
	 * 
	 * @param isMaterial
	 *            判断是否料件，true为料件，false为成品
	 * @param seqNum
	 *            商品序号
	 * @param code
	 *            商品编码代码
	 * @param name
	 *            商品名称
	 * @param customer
	 *            客户
	 * @param impExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param contracts
	 *            合同表头
	 * @param state
	 *            状态类型
	 * @param impExpFlag
	 *            进出口标志
	 * @return List 是BcsCustomsDeclarationCommInfo型，报关单物料资料
	 */
	public List findImpExpCommInfoListContractCHANGING(boolean isMaterial,
			Integer seqNum, String code, String name, String customer,
			String impExpType, Date beginDate, Date endDate, List contracts,
			int state, int impExpFlag) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a, b from BcsCustomsDeclarationCommInfo as a "
				+ " left join fetch a.complex "
				+ " left join fetch a.baseCustomsDeclaration ";
		if (isMaterial) {
			hsql += " , ContractImg as b left join fetch b.complex"
					+ " left join fetch b.contract "
					+ " where a.baseCustomsDeclaration.emsHeadH2k=b.contract.emsNo "
					+ " and a.commSerialNo=b.seqNum";
		} else {
			hsql += " , ContractExg as b left join fetch b.complex "
					+ " left join fetch b.contract "
					+ " where a.baseCustomsDeclaration.emsHeadH2k=b.contract.emsNo "
					+ " and a.commSerialNo=b.seqNum ";
		}
		hsql += " and a.baseCustomsDeclaration.company.id=? "
				+ " and b.contract.declareState=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(DeclareState.CHANGING_CANCEL);// 只统计已经核销小凯修改 2010/5/25
		int temp = 0;
		for (int i = 0; i < contracts.size(); i++) {
			String emsNo = ((Contract) contracts.get(i)).getEmsNo();
			if (emsNo != null && (!emsNo.trim().equals(""))) {
				temp++;
				if (temp == 1) {
					hsql += " and ( b.contract.emsNo=? ";
					parameters.add(emsNo);
				} else {
					hsql += " or b.contract.emsNo=? ";
					parameters.add(emsNo);
				}
			}
		}
		if (temp != 0) {
			hsql += " )";
		}
		if (seqNum != null) {
			hsql += " and a.commSerialNo=? ";
			parameters.add(seqNum);
		}
		if (code != null && (!code.equals(""))) {
			hsql += " and a.complex.code=? ";
			parameters.add(code);
		}
		if (name != null && (!name.equals(""))) {
			hsql += " and a.commName=? ";
			parameters.add(name);
		}
		if (customer != null && !customer.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.customer.id=? ";
			parameters.add(customer);
		}
		if (impExpFlag == 2) {
			hsql += " and a.baseCustomsDeclaration.impExpFlag=?";
			parameters.add(2);
			if (isMaterial) {
				hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?)";
				parameters.add(ImpExpType.DIRECT_IMPORT);
				parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
				parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
				parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
				parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
				parameters.add(ImpExpType.MATERIAL_DOMESTIC_SALES);// edit
			} else {
				hsql += " and a.baseCustomsDeclaration.impExpType in "
						+ "(?,?,?,?)";
				parameters.add(ImpExpType.DIRECT_EXPORT);
				parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
				parameters.add(ImpExpType.BACK_FACTORY_REWORK);
				parameters.add(ImpExpType.REWORK_EXPORT);
			}
		} else {
			if (impExpType != null && !impExpType.trim().equals("")) {
				hsql += " and a.baseCustomsDeclaration.impExpType=? ";
				parameters.add(Integer.valueOf(impExpType));
			} else {
				if (isMaterial) {
					hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?,?)";
					parameters.add(ImpExpType.DIRECT_IMPORT);
					parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
					parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
					parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
					parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
					parameters.add(ImpExpType.MATERIAL_DOMESTIC_SALES);// edit
				} else {
					hsql += " and a.baseCustomsDeclaration.impExpType in "
							+ "(?,?,?,?)";
					parameters.add(ImpExpType.DIRECT_EXPORT);
					parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
					parameters.add(ImpExpType.BACK_FACTORY_REWORK);
					parameters.add(ImpExpType.REWORK_EXPORT);
				}
			}
		}

		if (impExpFlag == 2) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		}
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(false);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " order by a.baseCustomsDeclaration.declarationDate,a.baseCustomsDeclaration.customsDeclarationCode,a.commSerialNo";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找已报关的商品
	 * 
	 * @param isMaterial
	 *            判断是否料件，true为料件，false为成品
	 * @param code
	 *            商品编码代码
	 * @param name
	 *            商品名称
	 * @param spec
	 *            商品规格型号
	 * @param unitName
	 *            单位名称
	 * @param seqNo
	 *            商品序号
	 * @param customer
	 *            客户
	 * @param impExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param lsContract
	 *            合同表头
	 * @param state
	 *            状态类型
	 * @return List 是BcsCustomsDeclarationCommInfo型，报关单物料资料
	 */
	public List findImpExpCommInfoList(boolean isMaterial, String code,
			String name, String spec, String unitName, Integer seqNo,
			String customer, String impExpType, Date beginDate, Date endDate,
			List lsContract, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from BcsCustomsDeclarationCommInfo as a ";
		// if (isImport) {
		// hsql += " , ContractImg as b where
		// a.baseCustomsDeclaration.emsHeadH2k=b.contract.emsNo "
		// + " and a.commSerialNo=b.seqNum"
		// + " and a.complex.code=b.complex.code ";
		// } else {
		// hsql += " , ContractExg as b where
		// a.baseCustomsDeclaration.emsHeadH2k=b.contract.emsNo "
		// + " and a.commSerialNo=b.seqNum "
		// + " and a.complex.code=b.complex.code ";
		// }
		if (isMaterial) {
			hsql += " where a.baseCustomsDeclaration.impExpType in (?,?,?,?,?) ";
			// hsql +=
			// " where a.baseCustomsDeclaration.impExpType in (?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);

		} else {
			hsql += " where a.baseCustomsDeclaration.impExpType in (?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
			parameters.add(ImpExpType.REWORK_EXPORT);
		}

		// hsql += " and b.contract.isCancel=? and b.contract.declareState=? "
		// + " and a.baseCustomsDeclaration.effective=? "
		hsql += " and a.baseCustomsDeclaration.company.id=? ";
		// parameters.add(new Boolean(false));
		// parameters.add(DeclareState.PROCESS_EXE);
		// parameters.add(new Boolean(true));
		parameters.add(CommonUtils.getCompany().getId());
		// if (emsNo != null && !emsNo.trim().equals("")) {
		// hsql += " and b.contract.emsNo=? ";
		// parameters.add(emsNo);
		// }
		if (lsContract.size() == 1) {
			Contract contract = (Contract) lsContract.get(0);
			hsql += " and a.baseCustomsDeclaration.emsHeadH2k=? ";
			parameters.add(contract.getEmsNo());
		} else {
			for (int i = 0; i < lsContract.size(); i++) {
				Contract contract = (Contract) lsContract.get(i);
				if (i == 0) {
					hsql += " and ( a.baseCustomsDeclaration.emsHeadH2k=? ";
				} else if (i > 0 && i < lsContract.size() - 1) {
					hsql += " or  a.baseCustomsDeclaration.emsHeadH2k=? ";
				} else if (i == lsContract.size() - 1) {
					hsql += " or  a.baseCustomsDeclaration.emsHeadH2k=? ) ";
				}
				parameters.add(contract.getEmsNo());
			}
		}
		// if (seqNum != null) {
		// hsql += " and a.commSerialNo=? ";
		// parameters.add(seqNum);
		// }
		if (code != null && !code.trim().equals("")) {
			hsql += " and a.complex.code=? ";
			parameters.add(code);
		}
		if (name != null && !name.trim().equals("")) {
			hsql += " and a.commName = ? ";
			parameters.add(name);
		}
		if (spec != null && !spec.trim().equals("")) {
			hsql += " and a.commSpec = ? ";
			parameters.add(spec);
		}
		if (unitName != null && !unitName.trim().equals("")) {
			hsql += " and a.unit.name = ? ";
			parameters.add(unitName);
		}
		if (seqNo != null) {
			hsql += " and a.commSerialNo =? ";
			parameters.add(seqNo);
		}

		if (customer != null && !customer.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.customer.id=? ";
			parameters.add(customer);
		}
		if (impExpType != null && !impExpType.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.impExpType=? ";
			parameters.add(Integer.valueOf(impExpType));
		}
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(false);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " order by a.baseCustomsDeclaration.declarationDate,a.commSerialNo";

		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查询已报关的商品
	 * 
	 * @param isMaterial
	 *            料件判断，true位料件，否则为成品
	 * @param emsNo
	 *            手册号
	 * @param state
	 *            状态类型
	 * @return List 存放报关商品信息的一些资料
	 */
	public List findCustomsDeclarationCommInfo(boolean isMaterial,
			String emsNo, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select distinct a.commSerialNo,a.complex.code,a.commName,a.commSpec,a.unit.name"
				+ " from BcsCustomsDeclarationCommInfo as a ";
		// if (isMaterial) {
		// hsql += " , ContractImg as b where
		// a.baseCustomsDeclaration.emsHeadH2k=b.contract.emsNo "
		// + " and a.commSerialNo=b.seqNum"
		// + " and a.complex.code=b.complex.code ";
		// } else {
		// hsql += " , ContractExg as b where
		// a.baseCustomsDeclaration.emsHeadH2k=b.contract.emsNo "
		// + " and a.commSerialNo=b.seqNum"
		// + " and a.complex.code=b.complex.code ";
		// }
		if (isMaterial) {
			hsql += " where a.baseCustomsDeclaration.impExpType in (?,?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
		} else {
			hsql += " where a.baseCustomsDeclaration.impExpType in (?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
			parameters.add(ImpExpType.REWORK_EXPORT);
		}
		// hsql += " and b.contract.isCancel=? and b.contract.declareState=? "
		// + " and a.baseCustomsDeclaration.effective=? "
		hsql += " and a.baseCustomsDeclaration.company.id=? ";
		// parameters.add(new Boolean(false));
		// parameters.add(DeclareState.PROCESS_EXE);
		// parameters.add(new Boolean(true));
		parameters.add(CommonUtils.getCompany().getId());
		if (emsNo != null && !emsNo.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.emsHeadH2k=? ";
			parameters.add(emsNo);
		}
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(false);
		}
		hsql += " order by a.commSerialNo";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查询已报关的商品
	 * 
	 * @param isMaterial
	 *            料件判断，true位料件，否则为成品
	 * @param lsContract
	 *            合同表头
	 * @param state
	 *            状态类型
	 * @return List 存放报关单商品的一些资料
	 */
	public List findCustomsDeclarationCommInfo(boolean isMaterial,
			List lsContract, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select distinct a.commSerialNo,a.complex.code,a.commName,a.commSpec,a.unit.name "
				+ " from BcsCustomsDeclarationCommInfo as a ";
		// if (isMaterial) {
		// hsql += " , ContractImg as b where
		// a.baseCustomsDeclaration.emsHeadH2k=b.contract.emsNo "
		// + " and a.commSerialNo=b.seqNum"
		// + " and a.complex.code=b.complex.code ";
		// } else {
		// hsql += " , ContractExg as b where
		// a.baseCustomsDeclaration.emsHeadH2k=b.contract.emsNo "
		// + " and a.commSerialNo=b.seqNum"
		// + " and a.complex.code=b.complex.code ";
		// }
		if (isMaterial) {
			hsql += " where a.baseCustomsDeclaration.impExpType in (?,?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
		} else {
			hsql += " where a.baseCustomsDeclaration.impExpType in (?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
			parameters.add(ImpExpType.REWORK_EXPORT);
		}
		// hsql += " and b.contract.isCancel=? and b.contract.declareState=? "
		// hsql += " and a.baseCustomsDeclaration.effective=? "
		hsql += " and a.baseCustomsDeclaration.company.id=? ";
		// parameters.add(new Boolean(false));
		// parameters.add(DeclareState.PROCESS_EXE);
		// parameters.add(new Boolean(true));
		parameters.add(CommonUtils.getCompany().getId());
		// if (emsNo != null && !emsNo.trim().equals("")) {
		// hsql += " and a.baseCustomsDeclaration.emsHeadH2k=? ";
		// parameters.add(emsNo);
		// }
		if (lsContract.size() == 1) {
			Contract contract = (Contract) lsContract.get(0);
			hsql += " and a.baseCustomsDeclaration.emsHeadH2k=? ";
			parameters.add(contract.getEmsNo());
		} else {
			for (int i = 0; i < lsContract.size(); i++) {
				Contract contract = (Contract) lsContract.get(i);
				if (i == 0) {
					hsql += " and (a.baseCustomsDeclaration.emsHeadH2k=? ";
				} else if (i > 0 && i < lsContract.size() - 1) {
					hsql += " or a.baseCustomsDeclaration.emsHeadH2k=? ";
				} else if (i == lsContract.size() - 1) {
					hsql += " or a.baseCustomsDeclaration.emsHeadH2k=? ) ";
				}
				parameters.add(contract.getEmsNo());
			}
		}
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(false);
		}
		hsql += " order by a.commSerialNo";
		return this.find(hsql, parameters.toArray());
	}

	// //修改后
	// public List findCustomsDeclarationCommInfoBySeqNum(boolean isMaterial,
	// String emsNo, int state) {
	// List<Object> parameters = new ArrayList<Object>();
	// String hsql = " select distinct
	// a.commSerialNo,a.complex.code,a.commName,a.commSpec"//a.commSerialNo,
	// + " from BcsCustomsDeclarationCommInfo as a ";
	// if (isMaterial) {
	// hsql += " , ContractImg as b where
	// a.baseCustomsDeclaration.emsHeadH2k=b.contract.emsNo "
	// + " and a.commSerialNo=b.seqNum"
	// + " and a.complex.code=b.complex.code ";
	// } else {
	// hsql += " , ContractExg as b where
	// a.baseCustomsDeclaration.emsHeadH2k=b.contract.emsNo "
	// + " and a.commSerialNo=b.seqNum"
	// + " and a.complex.code=b.complex.code ";
	// }
	// hsql += " and b.contract.isCancel=? and b.contract.declareState=? "
	// // + " and a.baseCustomsDeclaration.effective=? "
	// + " and a.baseCustomsDeclaration.company.id=? ";
	// parameters.add(new Boolean(false));
	// parameters.add(DeclareState.PROCESS_EXE);
	// // parameters.add(new Boolean(true));
	// parameters.add(CommonUtils.getCompany().getId());
	// if (emsNo != null && !emsNo.trim().equals("")) {
	// hsql += " and a.baseCustomsDeclaration.emsHeadH2k=? ";
	// parameters.add(emsNo);
	// }
	// if (state == CustomsDeclarationState.EFFECTIVED) {
	// hsql += " and a.baseCustomsDeclaration.effective=?";
	// parameters.add(true);
	// } else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
	// hsql += " and a.baseCustomsDeclaration.effective=?";
	// parameters.add(false);
	// }
	// return this.find(hsql, parameters.toArray());
	// }
	//

	/**
	 * 查询已报关的商品
	 * 
	 * @param isMaterial
	 *            料件判断，true位料件，否则为成品
	 * @param impExpType
	 *            进出口类型
	 * @param emsNo
	 *            手册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 是BcsCustomsDeclarationCommInfo型，报关单物料
	 */
	public List findCustomsDeclarationCommInfo(boolean isMaterial,
			String impExpType, String emsNo, Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from BcsCustomsDeclarationCommInfo as a ";
		// if (isMaterial) {
		// hsql += " , ContractImg as b where
		// a.baseCustomsDeclaration.emsHeadH2k=b.contract.emsNo "
		// + " and a.commSerialNo=b.seqNum"
		// + " and a.complex.code=b.complex.code ";
		// } else {
		// hsql += " , ContractExg as b where
		// a.baseCustomsDeclaration.emsHeadH2k=b.contract.emsNo "
		// + " and a.commSerialNo=b.seqNum"
		// + " and a.complex.code=b.complex.code ";
		// }
		if (isMaterial) {
			hsql += " where a.baseCustomsDeclaration.impExpType in (?,?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
		} else {
			hsql += " where a.baseCustomsDeclaration.impExpType in (?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
			parameters.add(ImpExpType.REWORK_EXPORT);
		}
		// hsql += " and b.contract.isCancel=? and b.contract.declareState=? "
		hsql += " and a.baseCustomsDeclaration.effective=? "
				+ " and a.baseCustomsDeclaration.company.id=? ";
		// parameters.add(new Boolean(false));
		// parameters.add(DeclareState.PROCESS_EXE);
		parameters.add(new Boolean(true));
		parameters.add(CommonUtils.getCompany().getId());
		if (emsNo != null && !emsNo.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.emsHeadH2k=? ";
			parameters.add(emsNo);
		}
		if (impExpType != null && !"".equals(impExpType)) {
			hsql += " and a.baseCustomsDeclaration.impExpType=? ";
			parameters.add(Integer.valueOf(impExpType));
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " order by a.commSerialNo,a.baseCustomsDeclaration.declarationDate";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查询已报关的客户
	 * 
	 * @param isMateriel
	 *            料件判断，true位料件，否则为成品
	 * @param emsNo
	 *            手册号
	 * @param state
	 *            状态类型
	 * @return List 是customer型，存放了已报关的客户
	 */
	public List findCustomsDeclarationCustomer(boolean isMateriel,
			String emsNo, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select distinct a.customer from BcsCustomsDeclaration as a where  "
				+ " a.company.id=?  ";// + " and a.effective=? ";
		parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(new Boolean(true));
		if (emsNo != null && !emsNo.trim().equals("")) {
			hsql += " and a.emsHeadH2k=? ";
			parameters.add(emsNo);
		}
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.effective=?";
			parameters.add(false);
		}
		// if (isImport) {
		// hsql += " and a.impExpFlag=? ";
		// parameters.add(ImpExpFlag.IMPORT);
		// } else {
		// hsql += " and a.impExpFlag=? ";
		// parameters.add(ImpExpFlag.IMPORT);
		// }
		// else {
		if (isMateriel) {
			hsql += " and a.impExpType in (?,?,?,?)";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
			parameters.add(ImpExpType.GENERAL_TRADE_IMPORT);
		} else {
			hsql += " and a.impExpType in (?,?,?,?,?)";
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
			parameters.add(ImpExpType.REWORK_EXPORT);
			parameters.add(ImpExpType.GENERAL_TRADE_EXPORT);
		}

		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查询已报关的客户
	 * 
	 * @param isMateriel
	 *            料件判断，true位料件，否则为成品
	 * @param lsContract
	 *            合同表头
	 * @param state
	 *            状态类型
	 * @return List 是customer型，存放了已报关的客户
	 */
	public List findCustomsDeclarationCustomer(boolean isMateriel,
			List lsContract, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select distinct a.customer from BcsCustomsDeclaration as a where  "
				+ " a.company.id=?  ";// + " and a.effective=? ";
		parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(new Boolean(true));
		// if (emsNo != null && !emsNo.trim().equals("")) {
		// hsql += " and a.emsHeadH2k=? ";
		// parameters.add(emsNo);
		// }
		if (lsContract.size() == 1) {
			Contract contract = (Contract) lsContract.get(0);
			hsql += " and a.emsHeadH2k=? ";
			parameters.add(contract.getEmsNo());
		} else {
			for (int i = 0; i < lsContract.size(); i++) {
				Contract contract = (Contract) lsContract.get(i);
				if (i == 0) {
					hsql += " and (a.emsHeadH2k=? ";
				} else if (i > 0 && i < lsContract.size() - 1) {
					hsql += " or a.emsHeadH2k=? ";
				} else if (i == lsContract.size() - 1) {
					hsql += " or a.emsHeadH2k=? ) ";
				}
				parameters.add(contract.getEmsNo());
			}
		}
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.effective=?";
			parameters.add(false);
		}
		// if (isImport) {
		// hsql += " and a.impExpFlag=? ";
		// parameters.add(ImpExpFlag.IMPORT);
		// } else {
		// hsql += " and a.impExpFlag=? ";
		// parameters.add(ImpExpFlag.IMPORT);
		// }
		if (isMateriel) {
			hsql += " and a.impExpType in (?,?,?,?)";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
			parameters.add(ImpExpType.GENERAL_TRADE_IMPORT);
		} else {
			hsql += " and a.impExpType in (?,?,?,?,?)";
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
			parameters.add(ImpExpType.REWORK_EXPORT);
			parameters.add(ImpExpType.GENERAL_TRADE_EXPORT);
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 核销单登记表
	 * 
	 * @param emsNo
	 *            手册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 是BcsCustomsDeclarationCommInfo型，报关单物料
	 */
	public List findCancelAfterVerificationList(String emsNo, Date beginDate,
			Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a from BcsCustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.impExpFlag=? "
				+ " and a.baseCustomsDeclaration.effective=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(ImpExpFlag.EXPORT);
		parameters.add(new Boolean(true));
		if (emsNo != null && !emsNo.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.emsHeadH2k=? ";
			parameters.add(emsNo);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " order by a.baseCustomsDeclaration.customsDeclarationCode";
		// hsql += " group by
		// a.baseCustomsDeclaration.customsDeclarationCode,a.baseCustomsDeclaration.authorizeFile";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 抓取关封数量
	 * 
	 * @param emsNo
	 *            手册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 存放关封管理明细中的帐册序号、海关商品编码、本厂数量(申请数量)
	 */
	public List findCustomsEnvelopCommInfoCount(String emsNo,
			boolean isMaterial, Date beginDate, Date endDate) {
		// List<Object> parameters = new ArrayList<Object>();
		// String hsql = "select b.seqNum,b.complex,sum(b.ownerQuantity) "
		// + " from BcsCustomsDeclarationCommInfo as
		// a,CustomsEnvelopCommodityInfo as b"
		// + " where a.baseCustomsDeclaration.customsEnvelopBillNo= "
		// + " b.customsEnvelopBill.customsEnvelopBillNo "
		// + " and a.commSerialNo=b.seqNum"
		// + " and a.complex.code=b.complex"
		// + " and a.baseCustomsDeclaration.emsHeadH2k=b.emsNo"
		// + " and a.baseCustomsDeclaration.company.id=? "
		// + " and b.customsEnvelopBill.company.id=? ";
		// parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(CommonUtils.getCompany().getId());
		// if (emsNo != null && !emsNo.trim().equals("")) {
		// hsql += " and a.baseCustomsDeclaration.emsHeadH2k=? ";
		// parameters.add(emsNo);
		// }
		// if (beginDate != null) {
		// hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
		// parameters.add(CommonUtils.getBeginDate(beginDate));
		// }
		// if (endDate != null) {
		// hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
		// parameters.add(CommonUtils.getEndDate(endDate));
		// }
		// hsql += " group by b.seqNum,b.complex ";
		// return this.find(hsql, parameters.toArray());
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select b.seqNum,b.complex.code,sum(b.ownerQuantity) "
				+ " from CustomsEnvelopCommodityInfo as b"
				+ " where b.customsEnvelopBill.company.id=? "
				+ " and b.emsNo=? "
				+ " and b.customsEnvelopBill.isImpExpGoods=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		parameters.add(isMaterial);
		// if (emsNo != null && !emsNo.trim().equals("")) {
		// hsql += " and b.emsNo=? ";
		//
		// }
		// if (beginDate != null) {
		// hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
		// parameters.add(CommonUtils.getBeginDate(beginDate));
		// }
		// if (endDate != null) {
		// hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
		// parameters.add(CommonUtils.getEndDate(endDate));
		// }

		// 只抓取未结案的数据
		// 2013-11-26 料件情况统计表中的关封余量 = 关封数量（所有） - 转厂报关单数量
		// hsql += " and b.customsEnvelopBill.isEndCase=0 ";
		hsql += " group by b.seqNum,b.complex.code ";
		List list = this.find(hsql, parameters.toArray());
		// for(int i=0;i<list.size();i++){
		// Object[] objs = (Object[]) list.get(i);
		// String key = (objs[0] == null ? "" : objs[0].toString())
		// + (objs[1] == null ? "" : objs[1].toString());
		// Double value = objs[2] == null ? 0 : Double.parseDouble(objs[2]
		// .toString());
		// System.out.println("关封：：：："+key+" "+value);
		// }
		return list;
	}

	/**
	 * 抓取关封数量
	 * 
	 * @param emsNo
	 *            手册号
	 * @param isMaterial
	 *            料件判断，true位料件，否则为成品
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 存放关封管理明细中的帐册序号、海关商品编码、本厂数量(申请数量)
	 */
	public List findCommodityInfoRemain(String emsNo, boolean isMaterial,
			Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "";

		hsql = "select a.seqNum,a.complex.code,sum(a.ownerQuantity)"
				+ " from CustomsEnvelopCommodityInfo a "
				+ "where a.company.id=? and a.emsNo=? "
				+ "and a.customsEnvelopBill.isImpExpGoods=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		parameters.add(isMaterial);// 进货是出货
		hsql += "and a.customsEnvelopBill.isAvailability=? ";
		parameters.add(true);
		hsql += " group by a.seqNum,a.complex.code ";
		List list = this.find(hsql, parameters.toArray());
		return list;
	}

	/**
	 * 抓取关封数量
	 * 
	 * @param emsNo
	 *            手册号
	 * @param isMaterial
	 *            料件判断，true位料件，否则为成品
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 存放关封管理明细中的帐册序号、海关商品编码、本厂数量(申请数量)
	 */
	public List findFptAppItemCount(String emsNo, boolean isMaterial,
			Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "";
		if (isMaterial) {// 料件
			hsql = " select b.trNo,b.codeTs.code,sum(b.qty) "
					+ " from FptAppItem as b"
					+ " where b.fptAppHead.company.id=? and b.inEmsNo=? "
					+ " and b.fptAppHead.inOutFlag=? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(emsNo);
			parameters.add(FptInOutFlag.IN);
		} else {// 成品
			hsql = " select b.trNo,b.codeTs.code,sum(b.qty) "
					+ " from FptAppItem as b"
					+ " where b.fptAppHead.company.id=? and b.fptAppHead.emsNo=? "
					+ " and b.fptAppHead.inOutFlag=? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(emsNo);
			parameters.add(FptInOutFlag.OUT);
		}
		hsql += " and b.fptAppHead.declareState=? ";
		parameters.add(DeclareState.PROCESS_EXE);
		hsql += " and b.inOutFlag=b.fptAppHead.inOutFlag ";
		hsql += " group by b.trNo,b.codeTs.code ";
		List list = this.find(hsql, parameters.toArray());
		return list;
	}

	/**
	 * 根据进出口标志查找电子化手册报关单类型
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @param state
	 *            是否生效标志
	 * @return List 是BcsCustomsDeclaration型,查找报关单进出口类型.
	 */
	public List findSpecialImpExpType(Integer impExpFlag, Integer state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select distinct a.impExpType from BcsCustomsDeclaration as a where "
				+ "a.company.id=? and a.impExpFlag=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(impExpFlag);
		if (state != null && state != CustomsDeclarationState.ALL) {
			hsql += " and a.effective=?";
			if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
				parameters.add(false);
			} else {
				parameters.add(true);
			}
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 根据进出口标志查找电子手册报关单类型
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @param state
	 *            是否生效标志
	 * @return List 是DzscCustomsDeclaration型,查找报关单进出口类型.
	 */
	public List findDZSCSpecialImpExpType(Integer impExpFlag, Integer state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select distinct a.impExpType from DzscCustomsDeclaration as a where "
				+ "a.company.id=? and a.impExpFlag=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(impExpFlag);
		if (state != null && state != CustomsDeclarationState.ALL) {
			hsql += " and a.effective=?";
			if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
				parameters.add(false);
			} else {
				parameters.add(true);
			}
		}

		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查询特殊报关的商品
	 * 
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @param state
	 *            是否生效标志
	 * @return List 存放报关单商品的一些资料
	 */

	public List findSpecialCustomsDeclarationCommInfo(Integer impExpFlag,
			int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select distinct a.commSerialNo,a.complex.code,a.commName,a.commSpec "
				+ " from BcsCustomsDeclarationCommInfo as a "
				+ "where a.baseCustomsDeclaration.impExpFlag = ? "
				+ " and a.baseCustomsDeclaration.company.id=? ";
		parameters.add(2);
		parameters.add(CommonUtils.getCompany().getId());
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(false);
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找电子化手册报关单商品明细资料
	 * 
	 * @param seqNum
	 *            商品序号
	 * @param code
	 *            商品编码
	 * @param name
	 *            商品名称
	 * @param customer
	 *            客户
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param contracts
	 *            合同表头
	 * @param state
	 *            生效标志
	 * @param impExpFlag
	 *            进出口标志
	 * @return List 是BcsCustomsDeclarationCommInfo型,报关单商品明细资料
	 */
	public List findSpecialCustomsDeclarationCommInfo(Integer seqNum,
			String code, String name, String customer, Date beginDate,
			Date endDate, List contracts, int state, int impExpFlag) {

		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from BcsCustomsDeclarationCommInfo as a "
				+ " left join fetch a.complex "
				+ " left join fetch a.baseCustomsDeclaration "
				+ " where a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? ";

		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(2);
		hsql += " and a.baseCustomsDeclaration.impExpType in (";
		for (int i = 0; i < contracts.size(); i++) {
			Integer index = Integer.valueOf(contracts.get(i).toString());
			String sql = "?,";
			if (contracts.size() == 1 || i == contracts.size() - 1) {
				sql = "?)";
			}
			hsql += sql;
			parameters.add(index);
		}
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(false);
		}
		if (seqNum != null) {
			hsql += " and a.commSerialNo=? ";
			parameters.add(seqNum);
		}
		if (code != null && (!code.equals(""))) {
			hsql += " and a.complex.code=? ";
			parameters.add(code);
		}
		if (name != null && (!name.equals(""))) {
			hsql += " and a.commName=? ";
			parameters.add(name);
		}
		if (customer != null && !customer.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.customer.id=? ";
			parameters.add(customer);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " order by a.baseCustomsDeclaration.declarationDate,a.baseCustomsDeclaration.customsDeclarationCode, a.commSerialNo";
		System.out.println(hsql);
		return this.find(hsql, parameters.toArray());

	}

	/**
	 * 查找电子手册报关单商品明细资料
	 * 
	 * @param seqNum
	 *            商品序号
	 * @param code
	 *            商品编码
	 * @param name
	 *            商品名称
	 * @param customer
	 *            客户
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param contracts
	 *            合同表头
	 * @param state
	 *            生效标志
	 * @param impExpFlag
	 *            进出口标志
	 * @return List 是DzscCustomsDeclarationCommInfo型,报关单商品明细资料
	 */
	public List findSpecialDzscCustomsDeclarationCommInfo(Integer seqNum,
			String code, String name, String customer, Date beginDate,
			Date endDate, List contracts, int state, int impExpFlag) {

		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from DzscCustomsDeclarationCommInfo as a "
				+ " left join fetch a.complex "
				+ " left join fetch a.baseCustomsDeclaration "
				+ " where a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.impExpFlag = ? ";

		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(2);
		hsql += " and a.baseCustomsDeclaration.impExpType in (";
		for (int i = 0; i < contracts.size(); i++) {
			Integer index = Integer.valueOf(contracts.get(i).toString());
			String sql = "?,";
			if (contracts.size() == 1 || i == contracts.size() - 1) {
				sql = "?)";
			}
			hsql += sql;
			parameters.add(index);
		}
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(false);
		}
		if (seqNum != null) {
			hsql += " and a.commSerialNo=? ";
			parameters.add(seqNum);
		}
		if (code != null && (!code.equals(""))) {
			hsql += " and a.complex.code=? ";
			parameters.add(code);
		}
		if (name != null && (!name.equals(""))) {
			hsql += " and a.commName=? ";
			parameters.add(name);
		}
		if (customer != null && !customer.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.customer.id=? ";
			parameters.add(customer);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " order by a.baseCustomsDeclaration.declarationDate,a.baseCustomsDeclaration.customsDeclarationCode, a.commSerialNo";
		System.out.println(hsql);
		return this.find(hsql, parameters.toArray());

	}

	/**
	 * 查询特殊报关的客户
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @param state
	 *            状态类型
	 * @return List 是customer型，存放了已报关的客户
	 */
	public List findSpecialCustomsDeclarationCustomer(Integer impExpFlag,
			int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select distinct a.customer from BcsCustomsDeclaration as a where  "
				+ " a.company.id=?  and a.impExpFlag = ? ";// + " and
		// a.effective=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(2);

		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.effective=?";
			parameters.add(false);
		}

		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查询申请单转报关单
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param state
	 * @return
	 */
	public List findBillToCustomsList(Date beginDate, Date endDate,
			String billType, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a from ImpExpCommodityInfo  as a"
				+ " left join fetch a.impExpRequestBill "
				+ " where a.company.id=?   ";
		parameters.add(CommonUtils.getCompany().getId());
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.impExpRequestBill.isAvailability=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.impExpRequestBill.isAvailability=?";
			parameters.add(false);
		}
		if (billType != null && !"".equals(billType)) {
			hsql += " and a.impExpRequestBill.billType=? ";
			parameters.add(Integer.valueOf(billType));
		}
		if (beginDate != null) {
			hsql += " and a.impExpRequestBill.imputDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.impExpRequestBill.imputDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 根据申请单号查询申请单转报关单的中间过程
	 * 
	 * @param impExpCommodityInfoId
	 * @return
	 */
	public List findMakeBcsCustomsDeclarationByImpExg(
			String impExpCommodityInfoId) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a from MakeBcsCustomsDeclaration  as a"
				+ " left join fetch a.impExpCommodityInfo "
				+ " where a.company.id=?  and a.impExpCommodityInfo.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(impExpCommodityInfoId);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查询进口报关单总值（寮步外经办用）
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param state
	 * @return
	 */
	public double findImpdirectMoneywj(Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.commTotalPrice * "
				+ "case when a.baseCustomsDeclaration.exchangeRate  is null then 1.0"
				+ "when a.baseCustomsDeclaration.exchangeRate=0.0 then 1.0 else a.baseCustomsDeclaration.exchangeRate end) "
				+ " from BcsCustomsDeclarationCommInfo as a where  a.baseCustomsDeclaration.company.id=?"
				+ " and a.baseCustomsDeclaration.impExpFlag=0 "
				+ " and a.baseCustomsDeclaration.impExpType=0 "
				+ " and a.baseCustomsDeclaration.effective=1 ";
		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		List list = this.find(hsql, parameters.toArray());
		double hmDirectImportmoneywj = 0;
		if (list.size() > 0 && list.get(0) != null) {
			hmDirectImportmoneywj = Double.parseDouble(list.get(0).toString());
		}
		return hmDirectImportmoneywj;

	}

	/**
	 * 查询黄埔关区内 进口报关单总值（寮步外经办用）
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param state
	 * @return
	 */
	public double findImpdirectMoneywjIn(Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.commTotalPrice * "
				+ "case when a.baseCustomsDeclaration.exchangeRate  is null then 1.0"
				+ "when a.baseCustomsDeclaration.exchangeRate=0.0 then 1.0 else a.baseCustomsDeclaration.exchangeRate end) "
				+ " from BcsCustomsDeclarationCommInfo as a where  a.baseCustomsDeclaration.company.id=?"
				+ " and a.baseCustomsDeclaration.impExpFlag=0 "
				+ " and a.baseCustomsDeclaration.impExpType=0 "
				+ " and a.baseCustomsDeclaration.effective=1 "
				+ " and a.baseCustomsDeclaration.customs in "
				+ "('5200','5201','5202','5203','5204','5205',"
				+ "'5206','5207','5208','5209','5210','5211',"
				+ "'5212','5213','5214','5215','5216','5217',"
				+ "'5218','5219','5220','5221')";
		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		List list = this.find(hsql, parameters.toArray());
		double hmDirectImportmoneywj = 0;
		if (list.size() > 0 && list.get(0) != null) {
			hmDirectImportmoneywj = Double.parseDouble(list.get(0).toString());
		}
		return hmDirectImportmoneywj;

	}

	/**
	 * 查询黄埔关区外 进口报关单总值（寮步外经办用）
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param state
	 * @return
	 */
	public double findImpdirectMoneywjOut(Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.commTotalPrice * "
				+ "case when a.baseCustomsDeclaration.exchangeRate  is null then 1.0"
				+ "when a.baseCustomsDeclaration.exchangeRate=0.0 then 1.0 else a.baseCustomsDeclaration.exchangeRate end) "
				+ " from BcsCustomsDeclarationCommInfo as a where  a.baseCustomsDeclaration.company.id=?"
				+ " and a.baseCustomsDeclaration.impExpFlag=0 "
				+ " and a.baseCustomsDeclaration.impExpType=0 "
				+ " and a.baseCustomsDeclaration.effective=1 "
				+ " and a.baseCustomsDeclaration.customs not in "
				+ "('5200','5201','5202','5203','5204','5205',"
				+ "'5206','5207','5208','5209','5210','5211',"
				+ "'5212','5213','5214','5215','5216','5217',"
				+ "'5218','5219','5220','5221')";
		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		List list = this.find(hsql, parameters.toArray());
		double hmDirectImportmoneywj = 0;
		if (list.size() > 0 && list.get(0) != null) {
			hmDirectImportmoneywj = Double.parseDouble(list.get(0).toString());
		}
		return hmDirectImportmoneywj;

	}

	/**
	 * 结转进口总值关区外总值（寮步外经办用）
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param state
	 * @return
	 */
	public double findimptransEMoneywj(Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.commTotalPrice * "
				+ "case when a.baseCustomsDeclaration.exchangeRate  is null then 1.0"
				+ "when a.baseCustomsDeclaration.exchangeRate=0.0 then 1.0 else a.baseCustomsDeclaration.exchangeRate end) "
				+ " from BcsCustomsDeclarationCommInfo as a where  a.baseCustomsDeclaration.company.id=?"
				+ " and a.baseCustomsDeclaration.impExpFlag=0 "
				+ " and a.baseCustomsDeclaration.impExpType=1 "
				+ " and a.baseCustomsDeclaration.effective=1 "
				// +
				// " and a.baseCustomsDeclaration.transferMode in ('x','7')"//z
				+ " and a.baseCustomsDeclaration.customs not in "
				+ "('5200','5201','5202','5203','5204','5205',"
				+ "'5206','5207','5208','5209','5210','5211',"
				+ "'5212','5213','5214','5215','5216','5217',"
				+ "'5218','5219','5220','5221')";// 5308
		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		List list = this.find(hsql, parameters.toArray());
		double imptransEMoneywj = 0;
		if (list.size() > 0 && list.get(0) != null) {
			imptransEMoneywj = Double.parseDouble(list.get(0).toString());
		}
		return imptransEMoneywj;
	}

	/**
	 * 结转进口总值关区内总值（寮步外经办用）
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public double findImptransIMoneywj(Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.commTotalPrice * "
				+ "case when a.baseCustomsDeclaration.exchangeRate  is null then 1.0"
				+ "when a.baseCustomsDeclaration.exchangeRate=0.0 then 1.0 else a.baseCustomsDeclaration.exchangeRate end) "
				+ " from BcsCustomsDeclarationCommInfo as a where  a.baseCustomsDeclaration.company.id=?"
				+ " and a.baseCustomsDeclaration.impExpFlag=0 "
				+ " and a.baseCustomsDeclaration.impExpType=1 "
				+ " and a.baseCustomsDeclaration.effective=1 "
				// +
				// " and a.baseCustomsDeclaration.transferMode in ('x','7') "//Z
				+ " and a.baseCustomsDeclaration.customs  in "
				+ "('5200','5201','5202','5203','5204','5205',"
				+ "'5206','5207','5208','5209','5210','5211',"
				+ "'5212','5213','5214','5215','5216','5217',"
				+ "'5218','5219','5220','5221')";
		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += "and a.baseCustomsDeclaration.declarationDate<=?";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		List list = this.find(hsql, parameters.toArray());
		double ImptransIMoneywj = 0;
		if (list.size() > 0 && list.get(0) != null) {
			ImptransIMoneywj = Double.parseDouble(list.get(0).toString());
		}
		return ImptransIMoneywj;
	}

	/**
	 * 直接出口总值[寮步外经办用]
	 */
	public double findOutdirectMoneywj(Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.commTotalPrice * "
				+ "case when a.baseCustomsDeclaration.exchangeRate  is null then 1.0"
				+ "when a.baseCustomsDeclaration.exchangeRate=0.0 then 1.0 else a.baseCustomsDeclaration.exchangeRate end) "
				+ " from BcsCustomsDeclarationCommInfo as a where  a.baseCustomsDeclaration.company.id=?"
				+ " and a.baseCustomsDeclaration.impExpFlag=1 "
				+ " and a.baseCustomsDeclaration.impExpType=4 "
				+ " and a.baseCustomsDeclaration.effective=1 ";
		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += "and a.baseCustomsDeclaration.declarationDate<=?";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		List list = this.find(hsql, parameters.toArray());
		double OutdirectMoneywj = 0;
		if (list.size() > 0 && list.get(0) != null) {
			OutdirectMoneywj = Double.parseDouble(list.get(0).toString());
		}
		return OutdirectMoneywj;
	}

	/**
	 * 直接黄埔关区内 出口总值[寮步外经办用]
	 */
	public double findOutdirectMoneywjIn(Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.commTotalPrice * "
				+ "case when a.baseCustomsDeclaration.exchangeRate  is null then 1.0"
				+ "when a.baseCustomsDeclaration.exchangeRate=0.0 then 1.0 else a.baseCustomsDeclaration.exchangeRate end) "
				+ " from BcsCustomsDeclarationCommInfo as a where  a.baseCustomsDeclaration.company.id=?"
				+ " and a.baseCustomsDeclaration.impExpFlag=1 "
				+ " and a.baseCustomsDeclaration.impExpType=4 "
				+ " and a.baseCustomsDeclaration.effective=1 "
				+ " and a.baseCustomsDeclaration.customs in "
				+ "('5200','5201','5202','5203','5204','5205',"
				+ "'5206','5207','5208','5209','5210','5211',"
				+ "'5212','5213','5214','5215','5216','5217',"
				+ "'5218','5219','5220','5221')";
		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += "and a.baseCustomsDeclaration.declarationDate<=?";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		List list = this.find(hsql, parameters.toArray());
		double OutdirectMoneywj = 0;
		if (list.size() > 0 && list.get(0) != null) {
			OutdirectMoneywj = Double.parseDouble(list.get(0).toString());
		}
		return OutdirectMoneywj;
	}

	/**
	 * 直接黄埔关区外 出口总值[寮步外经办用]
	 */
	public double findOutdirectMoneywjOut(Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.commTotalPrice * "
				+ "case when a.baseCustomsDeclaration.exchangeRate  is null then 1.0"
				+ "when a.baseCustomsDeclaration.exchangeRate=0.0 then 1.0 else a.baseCustomsDeclaration.exchangeRate end) "
				+ " from BcsCustomsDeclarationCommInfo as a where  a.baseCustomsDeclaration.company.id=?"
				+ " and a.baseCustomsDeclaration.impExpFlag=1 "
				+ " and a.baseCustomsDeclaration.impExpType=4 "
				+ " and a.baseCustomsDeclaration.effective=1 "
				+ " and a.baseCustomsDeclaration.customs not in "
				+ "('5200','5201','5202','5203','5204','5205',"
				+ "'5206','5207','5208','5209','5210','5211',"
				+ "'5212','5213','5214','5215','5216','5217',"
				+ "'5218','5219','5220','5221')";
		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += "and a.baseCustomsDeclaration.declarationDate<=?";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		List list = this.find(hsql, parameters.toArray());
		double OutdirectMoneywj = 0;
		if (list.size() > 0 && list.get(0) != null) {
			OutdirectMoneywj = Double.parseDouble(list.get(0).toString());
		}
		return OutdirectMoneywj;
	}

	/**
	 * 结转出口关区外总值
	 */
	public double findouttransEMoneywj(Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.commTotalPrice * "
				+ "case when a.baseCustomsDeclaration.exchangeRate  is null then 1.0"
				+ "when a.baseCustomsDeclaration.exchangeRate=0.0 then 1.0 else a.baseCustomsDeclaration.exchangeRate end) "
				+ " from BcsCustomsDeclarationCommInfo as a where  a.baseCustomsDeclaration.company.id=?"
				+ " and a.baseCustomsDeclaration.impExpFlag=1 "
				+ " and a.baseCustomsDeclaration.impExpType=5 "
				+ " and a.baseCustomsDeclaration.effective=1 "
				// + " and a.baseCustomsDeclaration.transferMode in ('x','Z')"
				+ " and a.baseCustomsDeclaration.customs not in "
				+ "('5200','5201','5202','5203','5204','5205',"
				+ "'5206','5207','5208','5209','5210','5211',"
				+ "'5212','5213','5214','5215','5216','5217',"
				+ "'5218','5219','5220','5221')";
		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += "and a.baseCustomsDeclaration.declarationDate<=?";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		List list = this.find(hsql, parameters.toArray());
		double outtransEMoneywj = 0;
		if (list.size() > 0 && list.get(0) != null) {
			outtransEMoneywj = Double.parseDouble(list.get(0).toString());
		}
		return outtransEMoneywj;
	}

	/**
	 * 结转出口关区内总值
	 */
	public double findouttransIMoneywj(Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.commTotalPrice * "
				+ "case when a.baseCustomsDeclaration.exchangeRate  is null then 1.0"
				+ "when a.baseCustomsDeclaration.exchangeRate=0.0 then 1.0 else a.baseCustomsDeclaration.exchangeRate end) "
				+ " from BcsCustomsDeclarationCommInfo as a where  a.baseCustomsDeclaration.company.id=?"
				+ "and a.baseCustomsDeclaration.impExpFlag=1"
				+ "and a.baseCustomsDeclaration.impExpType=5"
				+ "and a.baseCustomsDeclaration.effective=1 "
				// + "and a.baseCustomsDeclaration.transferMode in ('x','Z')"
				+ "and a.baseCustomsDeclaration.customs  in "
				+ "('5200','5201','5202','5203','5204','5205',"
				+ "'5206','5207','5208','5209','5210','5211',"
				+ "'5212','5213','5214','5215','5216','5217',"
				+ "'5218','5219','5220','5221')";
		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += "and a.baseCustomsDeclaration.declarationDate<=?";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		List list = this.find(hsql, parameters.toArray());
		double outtransIMoneywj = 0;
		if (list.size() > 0 && list.get(0) != null) {
			outtransIMoneywj = Double.parseDouble(list.get(0).toString());
		}
		return outtransIMoneywj;
	}

	/**
	 * 月份
	 */
	public String findMonth(Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.declarationDate  from BcsCustomsDeclaration a  "
				+ "where a.company.id=?";
		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hsql += " and a.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += "and a.declarationDate<=?";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		List list = this.find(hsql, parameters.toArray());
		String month = null;
		if (list.size() > 0 && list.get(0) != null) {
			month = list.get(0).toString();
		}
		return month;
	}

	/**
	 * 通过中间过程，根据报关单商品资料查询申请单商品对应的料号
	 * 
	 * @param isImport
	 * @param lsContract
	 * @param state
	 * @return
	 */
	public List findMaterielByImpExpCommodityInfoToCustoms(boolean isImport,
			List lsContract, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select distinct a.impExpCommodityInfo.materiel from MakeBcsCustomsDeclaration a "
				+ "where a.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.company.id=?";
		parameters.add(CommonUtils.getCompany().getId());
		if (isImport) {
			hsql += " and a.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.impExpType in (?,?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
		} else {
			hsql += " and a.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.impExpType in (?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
			parameters.add(ImpExpType.REWORK_EXPORT);
		}
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.effective=?";
			parameters.add(false);
		}
		if (lsContract.size() == 1) {
			Contract contract = (Contract) lsContract.get(0);
			hsql += " and a.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.emsHeadH2k=? ";
			parameters.add(contract.getEmsNo());
		} else {
			for (int i = 0; i < lsContract.size(); i++) {
				Contract contract = (Contract) lsContract.get(i);
				if (i == 0) {
					hsql += " and ( a.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.emsHeadH2k=? ";
				} else if (i > 0 && i < lsContract.size() - 1) {
					hsql += " or  a.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.emsHeadH2k=? ";
				} else if (i == lsContract.size() - 1) {
					hsql += " or  a.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.emsHeadH2k=? ) ";
				}
				parameters.add(contract.getEmsNo());
			}
		}
		hsql += " order by a.impExpCommodityInfo.materiel.ptNo";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找报关单中的料号（针对直接新增的报关单，而不通过申请单转报关单）
	 * 
	 * @param isImport
	 * @param lsContract
	 * @param state
	 * @return
	 */
	public List findMaterielByImpExpCommodityInfo(boolean isImport,
			List lsContract, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select distinct a.ptNo "
				+ " from BcsCustomsDeclarationCommInfo as a ";
		if (isImport) {
			hsql += " where a.baseCustomsDeclaration.impExpType in (?,?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
		} else {
			hsql += " where a.baseCustomsDeclaration.impExpType in (?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
			parameters.add(ImpExpType.REWORK_EXPORT);
		}
		hsql += " and a.baseCustomsDeclaration.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (lsContract.size() == 1) {
			Contract contract = (Contract) lsContract.get(0);
			hsql += " and a.baseCustomsDeclaration.emsHeadH2k=? ";
			parameters.add(contract.getEmsNo());
		} else {
			for (int i = 0; i < lsContract.size(); i++) {
				Contract contract = (Contract) lsContract.get(i);
				if (i == 0) {
					hsql += " and (a.baseCustomsDeclaration.emsHeadH2k=? ";
				} else if (i > 0 && i < lsContract.size() - 1) {
					hsql += " or a.baseCustomsDeclaration.emsHeadH2k=? ";
				} else if (i == lsContract.size() - 1) {
					hsql += " or a.baseCustomsDeclaration.emsHeadH2k=? ) ";
				}
				parameters.add(contract.getEmsNo());
			}
		}
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(false);
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找正执行中的合同下的报关单商品(lyh)
	 * 
	 * @param isImport
	 *            判断是否料件，true为料件
	 * @param seqNum
	 *            商品序号
	 * @param code
	 *            商品编码
	 * @param customer
	 *            客户
	 * @param impExpType
	 *            ,可能是多个进出口类型例如："1,2,3,4" 进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param contract
	 *            合同备案表头
	 * @param state
	 *            生效类型
	 * 
	 * @return List 是ImpExpCustomsDeclarationCommInfo型，进出口报关登记表
	 */
	public List findCustomsDeclarationCommInfoContract(boolean isMaterial,
			Integer seqNum, String code, String name, String customer,
			String impExpType, Date beginDate, Date endDate, List contracts,
			int state, Integer impExpFlag) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from BcsCustomsDeclarationCommInfo a where  a.baseCustomsDeclaration.company.id=?";
		parameters.add(CommonUtils.getCompany().getId());
		if (seqNum != null) {
			hsql += " and a.commSerialNo=? ";
			parameters.add(seqNum);
		}
		if (code != null && (!code.equals(""))) {
			hsql += " and a.complex.code=? ";
			parameters.add(code);
		}
		if (name != null && (!name.equals(""))) {
			hsql += " and a.commName=? ";
			parameters.add(name);
		}
		if (customer != null && !customer.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.customer.id=? ";
			parameters.add(customer);
		}
		if (isMaterial) {
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
			parameters.add(ImpExpType.MATERIAL_DOMESTIC_SALES);
		} else {
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
			parameters.add(ImpExpType.REWORK_EXPORT);
		}
		if (impExpType != null && !impExpType.trim().equals("")) {
			// impExpType可能是多个进出口类型
			String[] impExpTypes = impExpType.split(",");
			hsql += " and( ";
			for (int i = 0; i < impExpTypes.length; i++) {
				if (i == impExpTypes.length - 1) {
					hsql += " a.baseCustomsDeclaration.impExpType=? ";
				} else {
					hsql += " a.baseCustomsDeclaration.impExpType=? or ";
				}
				parameters.add(Integer.valueOf(impExpTypes[i]));
			}
			hsql += " ) ";
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(false);
		}
		if (contracts.size() == 1) {
			Contract contract = (Contract) contracts.get(0);
			hsql += " and a.baseCustomsDeclaration.emsHeadH2k=? ";
			parameters.add(contract.getEmsNo());
		} else {
			for (int i = 0; i < contracts.size(); i++) {
				Contract contract = (Contract) contracts.get(i);
				if (i == 0) {
					hsql += " and ( a.baseCustomsDeclaration.emsHeadH2k=? ";
				} else if (i > 0 && i < contracts.size() - 1) {
					hsql += " or  a.baseCustomsDeclaration.emsHeadH2k=? ";
				} else if (i == contracts.size() - 1) {
					hsql += " or  a.baseCustomsDeclaration.emsHeadH2k=? ) ";
				}
				parameters.add(contract.getEmsNo());
			}
		}
		List list = this.find(hsql, parameters.toArray());
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 申请单商品信息来自申请单转报关单中间表
	 * 
	 * @param customs
	 *            报关商品信息
	 * @return 报关清单内容
	 */
	public List findImpExpCommodityInfoByCustomsInfo(
			BcsCustomsDeclarationCommInfo customs) {
		return this
				.find("select a.impExpCommodityInfo from MakeBcsCustomsDeclaration a "
						+ " where a.company.id = ? and a.bcsCustomsDeclarationCommInfo.id  = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								customs.getId() });
	}

	/**
	 * 根据条件查询统计申请单已转报关单商品数量，
	 * 
	 * @param lsContract
	 * @param state
	 * @param ptNo
	 * @param begin
	 * @param end
	 * @param IsGroupEms
	 * @return
	 */
	public List findCaleMaterialNum(int state, String ptNo, Date begin,
			Date end, List<Contract> contracts, boolean IsGroupEms) {
		StringBuffer hql = new StringBuffer(1000);
		List params = new ArrayList();

		hql.append("select b.materiel.ptNo, b.impExpRequestBill.billType, mk.bcsCustomsDeclarationCommInfo.complex.code,"
				+ " b.afterName, b.afterSpec, b.afterUnit, sum(b.quantity),"
				+ " b.materiel.factoryName, b.materiel.factorySpec, b.materiel.calUnit.name, b.materiel.unitConvert"
				+ (IsGroupEms ? (" ,mk.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.emsHeadH2k"
						+ " ,mk.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.contract")
						: ",'' as tmp1, '' as tmp2"));
		hql.append(" from MakeBcsCustomsDeclaration mk join mk.impExpCommodityInfo b"
				+ " where b.company.id = ? and mk.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.impExpType in (");
		// 进出口类型
		hql.append(ImpExpType.DIRECT_IMPORT + ",")
				.append(ImpExpType.TRANSFER_FACTORY_IMPORT + ",")
				.append(ImpExpType.REMAIN_FORWARD_IMPORT + ",")
				.append(ImpExpType.BACK_MATERIEL_EXPORT + ",")
				.append(ImpExpType.REMAIN_FORWARD_EXPORT + ",")
				.append(ImpExpType.MATERIAL_DOMESTIC_SALES + ")");
		params.add(CommonUtils.getCompany().getId());

		// 生效条件
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hql.append(" and mk.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.effective = ?");
			params.add(Boolean.TRUE);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hql.append(" and mk.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.effective = ?");
			params.add(Boolean.FALSE);
		}

		// 料号条件
		if (CommonUtils.notEmpty(ptNo)) {
			hql.append(" and b.materiel.ptNo = ?");
			params.add(ptNo);
		}

		// 开始时间
		if (begin != null) {
			hql.append(" and mk.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.declarationDate >= ?");
			params.add(begin);
		}

		// 结束时间
		if (end != null) {
			hql.append(" and mk.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.declarationDate <= ?");
			params.add(end);
		}

		// 合同
		if (contracts != null && contracts.size() > 0) {
			hql.append(" and mk.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.emsHeadH2k in ('"
					+ contracts.get(0).getEmsNo());
			for (int i = 1; i < contracts.size(); i++) {
				hql.append("','" + contracts.get(i).getEmsNo());
			}
			hql.append("')");
		}

		hql.append(" group by"
				+ (IsGroupEms ? (" mk.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.emsHeadH2k,"
						+ " mk.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.contract,")
						: "")
				+ " b.materiel.ptNo, b.impExpRequestBill.billType,"
				+ " mk.bcsCustomsDeclarationCommInfo.complex.code, b.afterName, b.afterSpec, b.afterUnit,"
				+ " b.materiel.factoryName, b.materiel.factorySpec, b.materiel.calUnit.name, b.materiel.unitConvert");
		hql.append(" order by"
				+ (IsGroupEms ? (" mk.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.emsHeadH2k,"
						+ " mk.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.contract,")
						: "")
				+ " b.materiel.ptNo, b.impExpRequestBill.billType,"
				+ " mk.bcsCustomsDeclarationCommInfo.complex.code, b.afterName, b.afterSpec, b.afterUnit,"
				+ " b.materiel.factoryName, b.materiel.factorySpec, b.materiel.calUnit.name, b.materiel.unitConvert");

		return find(hql.toString(), params.toArray());
	}

	/**
	 * 根据条件查询统计报关单商品，用成品料号关联bom折料计算料件耗用量
	 * 
	 * @param lsContract
	 * @param state
	 * @param ptNo
	 * @param begin
	 * @param end
	 * @param IsGroupEms
	 * @return
	 */
	public List findCaleMaterialNumRelationBOM(int state, String ptNo,
			Date begin, Date end, List<Contract> contracts, boolean IsGroupEms) {
		StringBuffer hql = new StringBuffer(1000);
		List params = new ArrayList();
		hql.append("select m.materiel.ptNo, b.impExpRequestBill.billType,"
				+ " '' as tmp1, '' as tmp2,'' as tmp3,"
				+ " '' as tmp4, sum(b.quantity*m.unitUsed),"
				+ " m.materiel.factoryName, m.materiel.factorySpec, m.materiel.calUnit.name, m.materiel.unitConvert"
				+ (IsGroupEms ? (" ,mk.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.emsHeadH2k"
						+ " ,mk.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.contract")
						: ",'' as tmp5, '' as tmp6")
				+ " ,sum(mk.bcsCustomsDeclarationCommInfo.commAmount*m.waste)");
		hql.append(" from MakeBcsCustomsDeclaration mk join mk.impExpCommodityInfo b, MaterialBomDetail m");
		hql.append(" where b.company.id = ? and b.materiel.ptNo = m.version.master.materiel.ptNo and b.version = m.version.version"
				+ " and mk.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.impExpType in (");
		hql.append(ImpExpType.DIRECT_EXPORT + ",")
				.append(ImpExpType.TRANSFER_FACTORY_EXPORT + ",")
				.append(ImpExpType.BACK_FACTORY_REWORK + ",")
				.append(ImpExpType.REWORK_EXPORT + ")");
		params.add(CommonUtils.getCompany().getId());
		// 生效条件
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hql.append(" and mk.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.effective = ?");
			params.add(Boolean.TRUE);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hql.append(" and mk.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.effective = ?");
			params.add(Boolean.FALSE);
		}

		// 料号条件
		if (CommonUtils.notEmpty(ptNo)) {
			hql.append(" and m.materiel.ptNo like ?");
			params.add("%" + ptNo + "%");
		}

		// 开始时间
		if (begin != null) {
			hql.append(" and mk.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.declarationDate >= ?");
			params.add(begin);
		}

		// 结束时间
		if (end != null) {
			hql.append(" and mk.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.declarationDate <= ?");
			params.add(end);
		}

		// 合同
		if (contracts != null && contracts.size() > 0) {
			hql.append(" and mk.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.emsHeadH2k in ('"
					+ contracts.get(0).getEmsNo());
			for (int i = 1; i < contracts.size(); i++) {
				hql.append("','" + contracts.get(i).getEmsNo());
			}
			hql.append("')");
		}

		hql.append(" group by mk.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.emsHeadH2k,"
				+ " mk.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.contract,"
				+ " m.materiel.ptNo, b.impExpRequestBill.billType,"
				+ " m.materiel.factoryName, m.materiel.factorySpec, m.materiel.calUnit.name, m.materiel.unitConvert");
		hql.append(" order by mk.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.emsHeadH2k,"
				+ " mk.bcsCustomsDeclarationCommInfo.baseCustomsDeclaration.contract,"
				+ " m.materiel.ptNo, b.impExpRequestBill.billType,"
				+ " m.materiel.factoryName, m.materiel.factorySpec, m.materiel.calUnit.name, m.materiel.unitConvert");

		return find(hql.toString(), params.toArray());
	}

	/**
	 * 根据条件查询统计申请单已转报关单商品数量，
	 * 
	 * @param lsContract
	 * @param state
	 * @param ptNo
	 * @param begin
	 * @param end
	 * @param isGroupEms
	 * @return
	 */
	public List findCaleProduceNum(int state, String ptNo, Date begin,
			Date end, List<Contract> contracts, boolean isGroupEms) {
		StringBuffer hql = new StringBuffer(1000);
		List params = new ArrayList();

		hql.append("select "
				+ (isGroupEms ? (" a.baseCustomsDeclaration.emsHeadH2k,")
						: "'' as tmp,")
				+ " a.commSerialNo,"
				+ " mk.impExpCommodityInfo.materiel.ptNo, a.baseCustomsDeclaration.impExpType,"
				+ " a.complex.code, a.commName, a.commSpec, a.unit.name, sum(mk.impExpCommodityInfo.quantity)"
				+ (isGroupEms ? (" ,a.baseCustomsDeclaration.contract")
						: ",'' as tmp1"));
		hql.append(" from MakeBcsCustomsDeclaration mk"
				+ " join mk.bcsCustomsDeclarationCommInfo a"
				+ " where a.company.id = ? and a.baseCustomsDeclaration.impExpType in (");
		hql.append(ImpExpType.DIRECT_EXPORT + ",")
				.append(ImpExpType.TRANSFER_FACTORY_EXPORT + ",")
				.append(ImpExpType.BACK_FACTORY_REWORK + ",")
				.append(ImpExpType.REWORK_EXPORT + ")");

		params.add(CommonUtils.getCompany().getId());

		// 生效条件
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hql.append(" and a.baseCustomsDeclaration.effective = ?");
			params.add(Boolean.TRUE);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hql.append(" and a.baseCustomsDeclaration.effective = ?");
			params.add(Boolean.FALSE);
		}

		// 料号条件
		if (CommonUtils.notEmpty(ptNo)) {
			hql.append(" and mk.impExpCommodityInfo.materiel.ptNo = ?");
			params.add(ptNo);
		}

		// 开始时间
		if (begin != null) {
			hql.append(" and a.baseCustomsDeclaration.declarationDate >= ?");
			params.add(begin);
		}

		// 结束时间
		if (end != null) {
			hql.append(" and a.baseCustomsDeclaration.declarationDate <= ?");
			params.add(end);
		}

		// 合同
		if (contracts != null && contracts.size() > 0) {
			hql.append(" and a.baseCustomsDeclaration.emsHeadH2k in ('"
					+ contracts.get(0).getEmsNo());
			for (int i = 1; i < contracts.size(); i++) {
				hql.append("','" + contracts.get(i).getEmsNo());
			}
			hql.append("')");
		}

		hql.append(" group by"
				+ (isGroupEms ? (" a.baseCustomsDeclaration.emsHeadH2k, a.baseCustomsDeclaration.contract,")
						: "")
				+ " a.commSerialNo,"
				+ " mk.impExpCommodityInfo.materiel.ptNo, a.baseCustomsDeclaration.impExpType,"
				+ " a.complex.code, a.commName, a.commSpec, a.unit.name");
		hql.append(" order by"
				+ (isGroupEms ? (" a.baseCustomsDeclaration.emsHeadH2k, a.baseCustomsDeclaration.contract,")
						: "")
				+ " a.commSerialNo,"
				+ " mk.impExpCommodityInfo.materiel.ptNo, a.baseCustomsDeclaration.impExpType,"
				+ " a.complex.code, a.commName, a.commSpec, a.unit.name");

		return find(hql.toString(), params.toArray());
	}

	/**
	 * 根据出口申请单商品料号+版本查找子料件
	 * 
	 * @param impExpCommodityInfo
	 */
	public List findCaleMaterialNumByPtnoVersion(
			ImpExpCommodityInfo impExpCommodityInfo) {
		List params = new ArrayList();
		String hsql = "select a from MaterialBomDetail a where a.company.id = ? and a.version.master.materiel.ptNo = ?";
		params.add(CommonUtils.getCompany().getId());
		params.add(impExpCommodityInfo.getMateriel().getPtNo());
		if (impExpCommodityInfo.getVersion() != null
				&& !"".equals(impExpCommodityInfo.getVersion())) {
			hsql += " and a.version.version = ?";
			params.add(Integer.valueOf(impExpCommodityInfo.getVersion()));
		}
		return find(hsql.toString(), params.toArray());
	}

	/**
	 * 查找在物料与报关对应表对应工厂料件对应后的报关资料
	 * 
	 * @param ptNo
	 *            料号
	 * @return List 是seqNum，归并序号
	 */
	public List findBcsInnerMergeImgByPtNo(String ptNo) {
		List<Object> paramterList = new ArrayList<Object>();
		String hsql = "select d.bcsTenInnerMerge from BcsInnerMerge d "
				+ "	where d.company.id = ?" + "	and   d.materielType = ?"
				+ " and d.materiel.ptNo = ?";
		paramterList.add(CommonUtils.getCompany().getId());
		paramterList.add(MaterielType.MATERIEL);
		paramterList.add(ptNo);
		return this.find(hsql, paramterList.toArray());
	}

	/**
	 * 查询港币转美元的汇率
	 * 
	 * @return
	 */
	public Double findCurrRate() {
		String hql = "select a from CurrRate a where a.company.id = ? "
				+ " and a.curr.code=? and a.curr1.code=? "
				+ "order by a.createDate desc ";
		List list = this.findListNoCache(hql, new Object[] {
				CommonUtils.getCompany().getId(), "502", "110" }, -1, 1);
		if (list.size() != 0) {
			CurrRate cr = (CurrRate) list.get(0);
			return cr.getRate() == null ? 0.0 : cr.getRate();
		} else {
			return 0.0;
		}
	}
}
