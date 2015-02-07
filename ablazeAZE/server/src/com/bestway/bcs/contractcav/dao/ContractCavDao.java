/*
 * Created on 2005-4-21
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractcav.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contractcav.entity.ContractBomCav;
import com.bestway.bcs.contractcav.entity.ContractCav;
import com.bestway.bcs.contractcav.entity.ContractExgCav;
import com.bestway.bcs.contractcav.entity.ContractImgCav;
import com.bestway.bcs.contractcav.entity.ContractUnitWasteCav;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;

/**
 * 合同核销DAO com.bestway.bcs.contractcav.dao.ContractCavDao
 * 
 * @author Administrator lm check by 2010-06-08
 */
@SuppressWarnings("unchecked")
public class ContractCavDao extends BaseDao {

	/**
	 * 根据手册编码查询合同核销表头，包括自用和海关用
	 * 
	 * @param emsNo
	 *            手册编号
	 * @param isCustoms
	 *            是海关用还是自用，true代表是海关用
	 * @return List 是ContractCav型，合同核销表头
	 */
	public ContractCav findContractCav(String emsNo, boolean isCustoms) {
		List list = this.find("select a from ContractCav as a where "
				+ " a.emsNo=? and a.company.id=? and a.isCustoms=? ",
				new Object[] { emsNo, CommonUtils.getCompany().getId(),
						isCustoms });
		if (list.size() > 0) {
			return (ContractCav) list.get(0);
		}
		return null;
	}

	/**
	 * 保存合同核销表头
	 * 
	 * @param contractCav
	 *            合同核销表头
	 */
	public void saveContractCav(ContractCav contractCav) {
		this.saveOrUpdate(contractCav);
	}

	/**
	 * 删除合同核销表头
	 * 
	 * @param contractCav
	 *            合同核销表头
	 */
	public void deleteContractCav(ContractCav contractCav) {
		this.delete(contractCav);
	}
	/**
	 * 抓取合同报关单
	 * 
	 * @param contractCav
	 *            电子化手册合同报关单
	 */
	public List findBcsCustomsDeclarationCav(ContractCav contractCav) {
		return this
				.find(
						"select a from BcsCustomsDeclaration as a where a.contract=? and a.impExpType=21 and a.effective=true",
						new Object[] { contractCav.getContractNo() });
	}
	/**
	 * 抓取合同核销报关单
	 * 
	 * @param contractCav
	 *            电子帐册合同核销单头
	 */
	public List findCustomsDeclarationCav(ContractCav contractCav) {
		return this
				.find(
						"select a from BcsCustomsDeclarationCav as a where a.contractCav.id=?  ",
						new Object[] { contractCav.getId() });
	}

	/**
	 * 删除合同核销报关单
	 * 
	 * @param contractCav
	 *            电子帐册合同核销单头
	 */
	public void deleteCustomsDeclarationCav(ContractCav contractCav) {
		this.batchUpdateOrDelete(
				"delete from BcsCustomsDeclarationCav where contractCav.id=? ",
				new Object[] { contractCav.getId() });
	}

	/**
	 * 抓取合同核销成品资料
	 * 
	 * @param contractCav
	 *            合同核销表头
	 * @return List 是ContractExgCav型，合同核销成品资料
	 */
	public List findContractExgCav(ContractCav contractCav) {
		return this.find(
				"select a from ContractExgCav as a where a.contractCav.id=? "
						+ " order by seqNum ", new Object[] { contractCav
						.getId() });
	}
	/**
	 * 取得报关单金额
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @return 指定报关单的报关商品信息的统计情况
	 */
	public List findCustomsDeclarationMoney(Integer impExpFlag) {
		String hql = "select a.baseCustomsDeclaration.id,sum(a.commTotalPrice) from BcsCustomsDeclarationCommInfo a  ";
			
		hql += " where a.baseCustomsDeclaration.company.id =? "
				+ " and a.baseCustomsDeclaration.impExpFlag=? "
				+ " group by a.baseCustomsDeclaration.id";
		return this.find(hql, new Object[] { CommonUtils.getCompany().getId(),
				impExpFlag });
	}
	/**
	 * 删除合同核销成品资料
	 * 
	 * @param contractCav
	 *            合同核销表头
	 * @return List 是ContractExgCav型，合同核销成品资料
	 */
	public void deleteContractExgCav(ContractCav contractCav) {
		this.batchUpdateOrDelete(
				"delete from ContractExgCav where contractCav.id=? ",
				new Object[] { contractCav.getId() });
	}

	/**
	 * 抓取合同核销成品资料
	 * 
	 * @param contractCav
	 *            合同核销表头
	 * @return List 是ContractExgCav型，合同核销成品资料
	 */
	public List findContractBomCav(ContractImgCav contractCav) {
		return this.find(
				"select a from ContractBomCav as a where a.contractCav.id=? "
						+ "  and  a.seqMaterialNum=?  and a.materialName=? ",
				new Object[] { contractCav.getContractCav().getId(),
						contractCav.getSeqNum(), contractCav.getName() });
	}

	/**
	 * 保存合同核销成品资料
	 * 
	 * @param contractExgCav
	 *            合同核销成品
	 */
	public void saveContractExgCav(ContractExgCav contractExgCav) {
		this.saveOrUpdate(contractExgCav);
	}

	/**
	 * 删除合同核销成品资料
	 * 
	 * @param contractExgCav
	 *            合同核销成品
	 */
	public void deleteContractExgCav(ContractExgCav contractExgCav) {
		this.delete(contractExgCav);
	}

	/**
	 * 抓取合同核销料件资料
	 * 
	 * @param contractCav
	 *            合同核销表头
	 * @return List 是ContractImgCav型，合同核销料件
	 */
	public List findContractImgCav(ContractCav contractCav) {
		return this.find(
				"select a from ContractImgCav as a where a.contractCav.id=? "
						+ " order by seqNum ", new Object[] { contractCav
						.getId() });
	}

	/**
	 * 删除合同核销料件资料
	 * 
	 * @param contractCav
	 *            合同核销表头
	 * @return List 是ContractImgCav型，合同核销料件
	 */
	public void deleteContractImgCav(ContractCav contractCav) {
		this.batchUpdateOrDelete(
				"delete ContractImgCav where contractCav.id=? ",
				new Object[] { contractCav.getId() });
	}

	/**
	 * 保存合同核销料件资料
	 * 
	 * @param contractImgCav
	 *            合同核销料件
	 */
	public void saveContractImgCav(ContractImgCav contractImgCav) {
		this.saveOrUpdate(contractImgCav);
	}

	/**
	 * 删除合同核销料件资料
	 * 
	 * @param contractImgCav
	 *            合同核销料件
	 */
	public void deleteContractImgCav(ContractImgCav contractImgCav) {
		this.delete(contractImgCav);
	}

	/**
	 * 抓取合同核销单耗资料
	 * 
	 * @param contractCav
	 *            合同核销表头
	 * @return List 是ContractUnitWasteCav型，存放分组后的单耗、损耗量、总用量
	 */
	public List findContractUnitWasteCav(ContractCav contractCav) {
		return this
				.find(
						"select a from ContractUnitWasteCav as a where a.contractCav.id = ?  ",
						new Object[] { contractCav.getId() });
	}

	/**
	 * 保存合同核销单耗资料
	 * 
	 * @param contractUnitWasteCav
	 *            存放分组后的单耗、损耗量、总用量
	 */
	public void saveContractUnitWasteCav(
			ContractUnitWasteCav contractUnitWasteCav) {
		this.saveOrUpdate(contractUnitWasteCav);
	}

	/**
	 * 删除合同核销单耗资料
	 * 
	 * @param contractUnitWasteCav
	 *            存放分组后的单耗、损耗量、总用量
	 */
	public void deleteContractUnitWasteCav(
			ContractUnitWasteCav contractUnitWasteCav) {
		this.delete(contractUnitWasteCav);
	}

	/**
	 * 抓取合同核销BOM资料
	 * 
	 * @param contractCav
	 *            合同核销表头
	 * @return List 是ContractBomCav型，合同核销BOM
	 */
	public List findContractBomCav(ContractCav contractCav) {
		String hql = "select a from ContractBomCav as a where a.contractCav.id=?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(contractCav.getId());
		hql += " order by seqProductNum,seqMaterialNum";
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 删除合同核销BOM资料
	 * 
	 * @param contractCav
	 *            合同核销表头
	 * @return List 是ContractBomCav型，合同核销BOM
	 */
	public void deleteContractBomCav(ContractCav contractCav) {
		String hql = "delete ContractBomCav where contractCav.id=?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(contractCav.getId());
		this.batchUpdateOrDelete(hql, parameters.toArray());
	}

	/**
	 * 抓取合同核销BOM资料
	 * 
	 * @param contractCav
	 *            合同核销表头
	 * @return List 是ContractBomCav型，合同核销BOM
	 */
	public List findContractBomCav(ContractCav contractCav,
			boolean notShowAmountIsZero) {
		String hql = "select a from ContractBomCav as a where a.contractCav.id=?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(contractCav.getId());
		if (notShowAmountIsZero) {
			hql += " and a.exgExpTotalAmount>0 ";
		}
		hql += " order by seqProductNum,seqMaterialNum";
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 保存合同核销BOM资料
	 * 
	 * @param contractBomCav
	 *            合同核销BOM
	 */
	public void saveContractBomCav(ContractBomCav contractBomCav) {
		this.saveOrUpdate(contractBomCav);
	}

	/**
	 * 删除合同核销Bom资料
	 * 
	 * @param contractBomCav
	 *            合同核销BOM
	 */
	public void deleteContractBomCav(ContractBomCav contractBomCav) {
		this.delete(contractBomCav);
	}

	/**
	 * 统计每份报关单的总价
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头
	 * @return List 是Double型，报关单的总价
	 */
	public List findCustomsDeclarationTotalPrice(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		String hsql = "select sum(commTotalPrice)  from  BcsCustomsDeclarationCommInfo as a "
				+ " and a.baseCustomsDeclaration.id=? ";
		return this.find(hsql, new Object[] { baseCustomsDeclaration.getId() });
	}

	/**
	 * 根据合同抓取报关单份数
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param isImport
	 *            进出口判断，true为进口
	 * @return int 合同对应的报关单份数
	 */
	public int findCutomsDeclarationCountByContract(Contract contract,
			boolean isImport) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select count(a.id) from BcsCustomsDeclaration as a where a.emsHeadH2k=? "
				+ " and a.company.id=? and a.effective=? ";
		parameters.add(contract.getEmsNo());
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Boolean(true));
		if (isImport) {
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
		List list = this.find(hsql, parameters.toArray());
		if (list.size() <= 0 || list.get(0) == null) {
			return 0;
		} else {
			return Integer.parseInt(list.get(0).toString());
		}
	}

	/**
	 * 根据合同手册号抓取报关单金额
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param impExpType
	 *            进出口类型
	 * @param impExpFlag
	 *            进出口标志
	 * @param tradeCodes
	 *            贸易方式编码
	 * @return double 合同对应的报关单金额
	 */
	public double findCutomsDeclarationMoneyByContract(Contract contract,
			Integer impExpType, Integer impExpFlag, String[] tradeCodes) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.commTotalPrice * "
				+ " case when a.baseCustomsDeclaration.exchangeRate  is null then 1.0"
				+ " when a.baseCustomsDeclaration.exchangeRate=0.0 then 1.0 "
				+ " else a.baseCustomsDeclaration.exchangeRate end) "
				+ " from BcsCustomsDeclarationCommInfo as a "
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
	 * 根据合同手册号抓取报关单金额
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param impExpType
	 *            进出口类型
	 * @param impExpFlag
	 *            进出口标志
	 * @param tradeCodes
	 *            贸易方式编码
	 * @return double 合同对应的报关单金额
	 */
	public String findMemosByContract(Contract contract, Integer impExpType,
			Integer impExpFlag, String[] tradeCodes) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select distinct a.baseCustomsDeclaration.memos "
				+ " from BcsCustomsDeclarationCommInfo as a "
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
		List<String> list = this.find(hsql, parameters.toArray());
		String resultString = "";
		for (String str : list) {
			if (str.startsWith("转至")) {
				if (!"".equals(resultString)) {
					resultString += ",";
				}
				resultString += str.substring(2);
			}
		}
		return resultString;
	}

	/**
	 * 在报关单中查找商品序号
	 * 
	 * @param isMaterial
	 *            料件成品判断，true为料件
	 * @param emsNo
	 *            手册编号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            生效类型
	 * @return List 是String型，报关单中的商品序号，去掉相同的
	 */
	public List findAllCommInfo(boolean isMaterial, String emsNo,
			Date beginDate, Date endDate, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select distinct  a.commSerialNo  from  BcsCustomsDeclarationCommInfo as a ";
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
		hsql += " and a.baseCustomsDeclaration.company.id=? ";
		// hsql += " and b.contract.declareState=? ";
		hsql += " and a.baseCustomsDeclaration.emsHeadH2k=?";
		parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(DeclareState.PROCESS_EXE);
		parameters.add(emsNo);
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
		hsql += " order by a.commSerialNo ";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 在报关单中查找商品序号
	 * 
	 * @param isMaterial
	 *            料件成品判断，true为料件
	 * @param emsNo
	 *            手册编号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 是String型，报关单中的商品序号，去掉相同的
	 */
	public List findAllCommInfo(boolean isMaterial, String emsNo,
			Date beginDate, Date endDate) {
		// List<Object> parameters = new ArrayList<Object>();
		// String hsql = "select distinct a.commSerialNo from
		// BcsCustomsDeclarationCommInfo as a ";
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
		// hsql += " and a.baseCustomsDeclaration.company.id=? ";
		// hsql += " and a.baseCustomsDeclaration.effective=? ";
		// hsql += " and a.baseCustomsDeclaration.emsHeadH2k=?";
		// parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(new Boolean(true));
		// parameters.add(emsNo);
		// if (beginDate != null) {
		// hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
		// parameters.add(beginDate);
		// }
		// if (endDate != null) {
		// hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
		// parameters.add(endDate);
		// }
		// return this.find(hsql, parameters.toArray());
		return this.findAllCommInfo(isMaterial, emsNo, beginDate, endDate,
				CustomsDeclarationState.EFFECTIVED);
	}

	/**
	 * 根据商品序号，查询合同料件信息
	 * 
	 * @param emsNo
	 *            手册编号
	 * @param commSerialNo
	 *            料件序号
	 * @return List 是ContractImg型，合同备案料件
	 */
	public ContractImg findImgByCommSerialNo(String emsNo, String commSerialNo) {
		String hsql = "select a from ContractImg as a where a.seqNum=? "
				+ " and a.contract.emsNo=? and a.contract.company.id=? "
				+ " and a.contract.declareState=? ";
		List list = this.find(hsql, new Object[] {
				Integer.valueOf(commSerialNo), emsNo,
				CommonUtils.getCompany().getId(), DeclareState.PROCESS_EXE });
		if (list.size() < 1) {
			return null;
		} else {
			return (ContractImg) list.get(0);
		}
	}

	/**
	 * 根据手册号查找合同备案成品或料件
	 * 
	 * @param isMaterial
	 *            料件成品判断，true为料件
	 * @param emsNo
	 *            手册编号
	 * @return List 是ContractImg或ContractExg型，合同备案成品、合同备案料件
	 */
	public List findImgExg(boolean isMaterial, String emsNo) {
		String className = null;
		if (isMaterial) {
			className = "ContractImg";
		} else {
			className = "ContractExg";
		}
		String hsql = "select a from " + className + " as a where "
				+ " a.contract.emsNo=? and a.contract.company.id=? "
				+ " and a.contract.declareState=? ";
		List list = this.find(hsql, new Object[] { emsNo,
				CommonUtils.getCompany().getId(), DeclareState.PROCESS_EXE });
		return list;
	}

	/**
	 * 根据商品序号，查询合同成品信息
	 * 
	 * @param emsNo
	 *            手册编号
	 * @param commSerialNo
	 *            成品序号
	 * @return List 是ContractExg型，合同备案成品
	 */
	public ContractExg findExgByCommSerialNo(String emsNo, String commSerialNo) {
		String hsql = "select a from ContractExg as a where a.seqNum=? "
				+ " and a.contract.emsNo=? and a.contract.company.id=? "
				+ " and a.contract.declareState=? ";
		List list = this.find(hsql, new Object[] {
				Integer.valueOf(commSerialNo), emsNo,
				CommonUtils.getCompany().getId(), DeclareState.PROCESS_EXE });
		if (list.size() < 1) {
			return null;
		} else {
			return (ContractExg) list.get(0);
		}
	}

	/**
	 * 根据商品序号，查询合同BOM信息
	 * 
	 * @param emsNo
	 *            手册编号
	 * @param exgSeqNum
	 *            商品序号
	 * @param declareState
	 *            合同状态
	 * @return List 是ContractBom型，合同备案BOM
	 */
	public List<ContractBom> findBomByExg(String emsNo, Integer exgSeqNum,
			String declareState) {
		if (declareState == null) {
			String hsql = "select a from ContractBom as a where a.contractExg.seqNum=? "
					+ " and a.contractExg.contract.emsNo=?"
					+ " and a.contractExg.contract.company.id=? ";
			return this.find(hsql, new Object[] { exgSeqNum, emsNo,
					CommonUtils.getCompany().getId() });
		} else {
			if (declareState != null && "5".equals(declareState)) {
				String hsql = "select a from ContractBom as a where a.contractExg.seqNum=? "
						+ " and a.contractExg.contract.emsNo=?"
						+ " and a.contractExg.contract.declareState=? "
						+ " and a.contractExg.contract.isCancel=? "
						+ " and a.contractExg.contract.company.id=? ";
				return this.find(hsql, new Object[] { exgSeqNum, emsNo,
						DeclareState.CHANGING_CANCEL, new Boolean(true),
						CommonUtils.getCompany().getId() });
			} else {
				String hsql = "select a from ContractBom as a where a.contractExg.seqNum=? "
						+ " and a.contractExg.contract.emsNo=?"
						+ " and a.contractExg.contract.declareState=? "
						+ " and a.contractExg.contract.isCancel=? "
						+ " and a.contractExg.contract.company.id=? ";
				return this.find(hsql, new Object[] { exgSeqNum, emsNo,
						DeclareState.PROCESS_EXE, new Boolean(false),
						CommonUtils.getCompany().getId() });
			}
		}
	}

	// /**
	// * 统计数量(根据进出口类型 )
	// *
	// * @param commSerialNo
	// * @param impExpType
	// * @return List 是型，
	// */
	// public double findCommInfoTotalAmountByType(String commSerialNo,
	// Integer impExpType, String emsNo, Date beginDate, Date endDate) {
	// List<Object> parameters = new ArrayList<Object>();
	// String hsql = " select sum(a.commAmount) from
	// BcsCustomsDeclarationCommInfo as a "
	// + " and a.baseCustomsDeclaration.company.id=? "
	// + " and a.baseCustomsDeclaration.effective=? "
	// + " and a.baseCustomsDeclaration.emsHeadH2k=?";
	// parameters.add(CommonUtils.getCompany().getId());
	// parameters.add(new Boolean(true));
	// parameters.add(emsNo);
	// if (beginDate != null) {
	// hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
	// parameters.add(beginDate);
	// }
	// if (endDate != null) {
	// hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
	// parameters.add(endDate);
	// }
	// if (commSerialNo != null) {
	// hsql += " and a.commSerialNo=?";
	// parameters.add(commSerialNo);
	// }
	// if (impExpType != null) {
	// hsql += " and a.impExpType=?";
	// parameters.add(impExpType);
	// }
	// List list = this.find(hsql, parameters.toArray());
	// if (list.size() < 1 || list.get(0) == null) {
	// return 0;
	// } else {
	// return Double.parseDouble(list.get(0).toString());
	// }
	// }
	/**
	 * 查询合同备案料件资料中某项商品序号、未转报关单对应的数量总量
	 * 
	 * @param isLj
	 *            是否为料件
	 * @param emsNo
	 *            手册编号
	 * 
	 * @return List 合同备案料件资料
	 */
	public List findNoTransCustomsNum(Boolean isLj, String emsNo) {
		return this.find("select a.seqNum,sum(a.num) from ImpExpGoodsBill a"
				+ " where a.contractNo = ? and a.isTcustoms = ?"
				+ "  and a.company.id = ? and a.isLj = ? group by  a.seqNum",
				new Object[] { emsNo, new Boolean(false),
						CommonUtils.getCompany().getId(), isLj });
	}

	/**
	 * 查询报关单中某项商品序号、商品编码对应的数量总量
	 * 
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
	 * @param state
	 *            生效类型
	 * @return List 存放了商品序号、商品编码和对应的数量总量
	 */
	public List findCommInfoTotalAmount(Integer impExpFlag, Integer impExpType,
			String[] tradeCodes, String emsNo, Date beginDate, Date endDate,
			int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a.commSerialNo, sum(a.commAmount) "
				+ " from  BcsCustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				// + " and a.baseCustomsDeclaration.effective=? "
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
		hsql += " group by a.commSerialNo";
		// System.out.println("--hsql:" + hsql);
		return this.find(hsql, parameters.toArray());
	}
	/**
	 * 查询总进口量（根据合同有效期内时间查询）
	 */
	public List findCommInfoTotalAmountContract(Integer impExpFlag, Integer impExpType,
			String[] tradeCodes, String emsNo, Date beginDate, Date endDate,
			int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a.commSerialNo, sum(a.commAmount) "
				+ " from  BcsCustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				// + " and a.baseCustomsDeclaration.effective=? "
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
		hsql += " group by a.commSerialNo";
		// System.out.println("--hsql:" + hsql);
		return this.find(hsql, parameters.toArray());
	}
	
	/**
	 * 查询bcs报关单的进口折算合同币制汇率金额
	 * 
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
	 * @param state
	 *            生效类型
	 * @return List 存放了bcs报关单的进口折算合同币制汇率金额
	 */
	public List findCommInfoTotalMoney(Integer impExpFlag, Integer impExpType,
			String[] tradeCodes, String emsNo, Date beginDate, Date endDate,
			int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a.commSerialNo, sum(a.commTotalPrice* "
				+ " case when a.baseCustomsDeclaration.exchangeRate  is null then 1.0"
				+ " when a.baseCustomsDeclaration.exchangeRate=0.0 then 1.0 "
				+ " else a.baseCustomsDeclaration.exchangeRate end) "
				+ " from  BcsCustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k=?";
		parameters.add(CommonUtils.getCompany().getId());
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
		hsql += " group by a.commSerialNo";
		// System.out.println("--hsql:" + hsql);
		return this.find(hsql, parameters.toArray());
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
	public double findCommInfoTotalAmount(Integer commSerialNo,
			Integer impExpFlag, Integer impExpType, String[] tradeCodes,
			String emsNo, Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select  sum(a.commAmount)  from  BcsCustomsDeclarationCommInfo as a "
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
		List list = this.find(hsql, parameters.toArray());
		if (list.size() < 1 || list.get(0) == null) {
			return 0;
		} else {
			return Double.parseDouble(list.get(0).toString());
		}
	}

	/**
	 * 查询已生效的报关单中各项商品净重总量
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
	 * @return double 商品净重总量
	 */
	public double findCommInfoTotalNetWeight(Integer commSerialNo,
			Integer impExpFlag, Integer impExpType, String[] tradeCodes,
			String emsNo, Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select  sum(a.netWeight)  from  BcsCustomsDeclarationCommInfo as a "
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
		List list = this.find(hsql, parameters.toArray());
		if (list.size() < 1 || list.get(0) == null) {
			return 0;
		} else {
			return Double.parseDouble(list.get(0).toString());
		}
	}

	/**
	 * 查询所有报关单中各项商品数量总量
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
	 * @param state
	 *            生效类型
	 * @return double 商品数量总量
	 */
	public double findCommInfoTotalAmount(Integer commSerialNo,
			Integer impExpFlag, Integer impExpType, String[] tradeCodes,
			String emsNo, Date beginDate, Date endDate, int state) {
		long b = System.currentTimeMillis();
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select  sum(a.commAmount)  from  BcsCustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				// + " and a.baseCustomsDeclaration.effective=? "
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
		
		long e = System.currentTimeMillis();
		System.out.println("findCommInfoTotalAmount 使用时间：" + (e - b));
		if (list.size() < 1 || list.get(0) == null) {
			return 0;
		} else {
			return Double.parseDouble(list.get(0).toString());
		}
	}
	
	
	/**
	 * 查询 指定序号 报关单 各类型的 数量总量
	 * 
	 * @param commSerialNo
	 *            料件序号
	 * @param impExpFlag
	 *            进出口标志
	 * @param impExpTypes
	 *            进出口类型
	 * @param emsNo
	 *            手册编号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            生效类型
	 * @return double 商品数量总量
	 */
	public List<Object[]> countCommInfoTotalAmount(Integer commSerialNo,
			Integer impExpFlag, Integer[] impExpTypes,
			String emsNo, Date beginDate, Date endDate, int state) {
		List<Object> parameters = new ArrayList<Object>();
		StringBuilder hsql = new StringBuilder("select a.baseCustomsDeclaration.impExpType," 
				+ " sum(a.commAmount) from BcsCustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k=?");
		parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(new Boolean(true));
		parameters.add(emsNo);
		if (impExpFlag != null) {
			hsql.append(" and a.baseCustomsDeclaration.impExpFlag=?");
			parameters.add(impExpFlag);
		}
		if (impExpTypes != null && impExpTypes.length > 0) {
			hsql.append(" and a.baseCustomsDeclaration.impExpType in (" + impExpTypes[0]);
			for (int i = 1; i < impExpTypes.length; i++) {
				hsql.append("," + impExpTypes[i]);
			}
			hsql.append(")");
		}
		if (beginDate != null) {
			hsql.append(" and a.baseCustomsDeclaration.declarationDate>=? ");
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql.append(" and a.baseCustomsDeclaration.declarationDate<=? ");
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		
		if (commSerialNo != null) {
			hsql.append(" and a.commSerialNo=?");
			parameters.add(commSerialNo);
		}		
		
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql.append(" and a.baseCustomsDeclaration.effective=?");
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql.append(" and a.baseCustomsDeclaration.effective=?");
			parameters.add(false);
		}
		
		hsql.append("group by a.baseCustomsDeclaration.impExpType");
		List<Object[]> list = this.find(hsql.toString(), parameters.toArray());
		
		return list;
	}
	/**
	 * 根据序号查询进出口类型对应的数量
	 * @return
	 */
	public Double getImpExpTypeNum(String emsNo, Integer seqNum,
			Date beginDate, Date endDate, Integer impExpFlag, Integer impExpType,
			String[] tradeCodes, Integer state) {
		List parameters = new ArrayList();
		String hsql = "select sum(a.commAmount) "
				+ " from  BcsCustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k=?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		
		hsql += " and a.commSerialNo = ?";
		parameters.add(seqNum);
		
		hsql += " and a.baseCustomsDeclaration.impExpFlag=?";
		parameters.add(impExpFlag);
		
		hsql += " and a.baseCustomsDeclaration.impExpType=?";
		parameters.add(impExpType);
		
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
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
		
		System.out.println(hsql);

		return (Double) createQuery(hsql, parameters.toArray()).uniqueResult();
	}
	/**
	 * 根据序号查询进出口类型对应的数量
	 * @return
	 */
	public List getImpExpByTypeNum(String emsNo,Date beginDate, Date endDate,
			Integer impExpFlag,Integer state) {
		List parameters = new ArrayList();
		String hsql = "select a.commSerialNo,a.baseCustomsDeclaration.impExpType, sum(a.commAmount) "
				+ " from  BcsCustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k=?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		
		hsql += " and a.baseCustomsDeclaration.impExpFlag=?";
		parameters.add(impExpFlag);
		
		hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?)";
		parameters.add(ImpExpType.DIRECT_EXPORT);
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		parameters.add(ImpExpType.REWORK_EXPORT);
		
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
		hsql+=" group by a.commSerialNo,a.baseCustomsDeclaration.impExpType ";
		System.out.println(hsql);
		
		return this.find(hsql, parameters.toArray());
	}
	
	
	/**
	 * 在报关单表头中查询出口总件数
	 * 
	 * @param emsNo
	 *            手册编号
	 * @return double 出口总件数
	 */
	public double findExpTotalPieces(String emsNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.commodityNum) from  BcsCustomsDeclaration as a "
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
		hsql = "select sum(a.commodityNum) from  BcsCustomsDeclaration as a "
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
	 * @return double[] 包含毛重总量和净重总量
	 */
	public double[] findImportTotalWeight(String emsNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.grossWeight),sum(a.netWeight)"
				+ " from BcsCustomsDeclaration as a "
				+ " where a.emsHeadH2k=? and a.company.id=? "
				+ " and a.effective=? ";
		parameters.add(emsNo);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Boolean(true));
		hsql += " and a.impExpType in (?,?,?) ";// ,?
		parameters.add(ImpExpType.DIRECT_IMPORT);
		parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
		parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
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
		// edit by cjb date 2010-2-22 2010020403
		parameters = new ArrayList<Object>();
		hsql = "select sum(a.grossWeight),sum(a.netWeight)"
				+ " from BcsCustomsDeclaration as a "
				+ " where a.emsHeadH2k=? and a.company.id=? "
				+ " and a.effective=? ";
		parameters.add(emsNo);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Boolean(true));
		hsql += " and a.impExpType in (?,?) ";
		parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
		parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
		// hsql += " and a.tradeMode.code in (?,?) ";
		// parameters.add("0300");
		// parameters.add("0700");
		list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			Object[] objs = (Object[]) list.get(0);
			importWeight[0] = importWeight[0]
					- (objs[0] == null ? 0.0 : Double.parseDouble(objs[0]
							.toString()));
			importWeight[1] = importWeight[1]
					- (objs[1] == null ? 0.0 : Double.parseDouble(objs[1]
							.toString()));
		}
		return importWeight;
	}

	/**
	 * 查询贸易方式为4561的退运出口货物净重毛重
	 * 
	 * @param emsNo
	 *            电子手册手册编号
	 * @return double[] 包含毛重总量和净重总量
	 */
	public double[] findExportReturnGoods(String emsNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.grossWeight),sum(a.netWeight)"
				+ " from BcsCustomsDeclaration as a "
				+ " where a.emsHeadH2k=? and a.company.id=? "
				+ " and a.effective=? ";
		parameters.add(emsNo);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Boolean(true));
		hsql += " and a.impExpType in (?) ";// ,?
		parameters.add(ImpExpType.BACK_FACTORY_REWORK);

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
		// + " from BcsCustomsDeclaration as a "
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
	 * 查询出口重量，包含毛重和净重
	 * 
	 * @param emsNo
	 *            电子手册手册编号
	 * @return double[] 包含毛重总量和净重总量
	 */
	public double[] findExportTotalWeights(String emsNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.grossWeight),sum(a.netWeight)"
				+ " from BcsCustomsDeclaration as a "
				+ " where a.emsHeadH2k=? and a.company.id=? "
				+ " and a.effective=? ";
		parameters.add(emsNo);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Boolean(true));
		hsql += " and a.impExpType in (?,?,?,?,?,?) ";// ,?
		parameters.add(ImpExpType.DIRECT_EXPORT);
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
		parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
		parameters.add(ImpExpType.REWORK_EXPORT);
		parameters.add(ImpExpType.MATERIAL_REOUT);
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
		// + " from BcsCustomsDeclaration as a "
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
	 * 查询贸易方式为4561的退运货物的重量，包含毛重和净重
	 * 
	 * @param emsNo
	 *            电子手册手册编号
	 * @return double[] 包含毛重总量和净重总量
	 */
	public double[] findImportReturnedGoods(String emsNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.netWeight),sum(a.grossWeight)"
				+ " from BcsCustomsDeclaration as a "
				+ " where a.emsHeadH2k=? and a.company.id=? "
				+ " and a.effective=? ";
		parameters.add(emsNo);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Boolean(true));
		hsql += " and a.impExpType in (?) ";// ,?
		parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);

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
		// + " from BcsCustomsDeclaration as a "
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
	 * @return double[] 包含毛重总量和净重总量
	 */
	public double[] findImportTotalWeights(String emsNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.grossWeight),sum(a.netWeight)"
				+ " from BcsCustomsDeclaration as a "
				+ " where a.emsHeadH2k=? and a.company.id=? "
				+ " and a.effective=? ";
		parameters.add(emsNo);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Boolean(true));
		hsql += " and a.impExpType in (?,?,?,?,?) ";// ,?
		parameters.add(ImpExpType.DIRECT_IMPORT);
		parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
		parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
		parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		parameters.add(ImpExpType.MATERIAL_EXCHANGE);

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
		// + " from BcsCustomsDeclaration as a "
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
	 * 查询料件内销重量，包含毛重和净重
	 * 
	 * @param emsNo
	 *            电子手册手册编号
	 * @return double[] 包含毛重总量和净重总量
	 */
	public double[] findImportDomesticSalesTotalWeights(String emsNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.grossWeight),sum(a.netWeight)"
				+ " from BcsCustomsDeclaration as a "
				+ " where a.emsHeadH2k=? and a.company.id=? "
				+ " and a.effective=? ";
		parameters.add(emsNo);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Boolean(true));
		hsql += " and a.impExpType in (?) ";// ,?
		parameters.add(ImpExpType.MATERIAL_DOMESTIC_SALES);
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
		// + " from BcsCustomsDeclaration as a "
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
	 * 查询出口重量，包含毛重和净重
	 * 
	 * @param emsNo
	 *            电子手册手册编号
	 * @return double[] 包含毛重总量和净重总量
	 */
	public double[] findExportTotalWeight(String emsNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.grossWeight),sum(a.netWeight)"
				+ " from BcsCustomsDeclaration as a "
				+ " where a.emsHeadH2k=? and a.company.id=? "
				+ " and a.effective=? ";
		parameters.add(emsNo);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Boolean(true));
		hsql += " and a.impExpType in (?,?,?) ";
		parameters.add(ImpExpType.DIRECT_EXPORT);
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		parameters.add(ImpExpType.REWORK_EXPORT);
		List list = this.find(hsql, parameters.toArray());
		double[] exportWeight = new double[] { 0.0, 0.0 };
		if (list.size() > 0 && list.get(0) != null) {
			Object[] objs = (Object[]) list.get(0);
			exportWeight[0] = (objs[0] == null ? 0.0 : Double
					.parseDouble(objs[0].toString()));
			exportWeight[1] = (objs[1] == null ? 0.0 : Double
					.parseDouble(objs[1].toString()));
		}
		// 公式修改 edit by cjb 2009.11.10 老卞
		parameters = new ArrayList<Object>();
		hsql = "select sum(a.grossWeight),sum(a.netWeight)"
				+ " from BcsCustomsDeclaration as a "
				+ " where a.emsHeadH2k=? and a.company.id=? "
				+ " and a.effective=? ";
		parameters.add(emsNo);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Boolean(true));
		hsql += " and a.impExpType=? ";
		parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			Object[] objs = (Object[]) list.get(0);
			exportWeight[0] = exportWeight[0]
					- (objs[0] == null ? 0.0 : Double.parseDouble(objs[0]
							.toString()));
			exportWeight[1] = exportWeight[1]
					- (objs[1] == null ? 0.0 : Double.parseDouble(objs[1]
							.toString()));
		}
		return exportWeight;
	}

	/**
	 * 抓取合同核销成品资料
	 * 
	 * @param parentId
	 *            合同核销表头Id
	 * @param index
	 *            开始数据下标
	 * @param length
	 *            数据长度
	 * @return List 是ContractExgCav型，合同核销成品
	 */
	public List findContractExgCav(String parentId, int index, int length) {
		return this
				.findPageList(
						"select a from ContractExgCav a where a.contractCav.id=? order by a.seqNum ",
						parentId, index, length);
	}

	/**
	 * 抓取合同核销成品资料
	 * 
	 * @param id
	 *            合同核销表头Id
	 * @param seqNum
	 *            成品序号
	 * @return List 是ContractExgCav型，合同核销成品
	 */
	public List findContractExgCav(String id, Integer seqNum) {
		return this
				.find(
						" select a from ContractExgCav a where a.contractCav.id=? and  a.seqNum =? ",
						new Object[] { id, seqNum });
	}

	/**
	 * 查找合同核销料件 来自 合同核销表头ID
	 * 
	 * @param parentId
	 *            合同核销表头Id
	 * @return List 是ContractImgCav型，合同核销料件
	 */
	public List findContractImgCavByParentId(String parentId) {
		return this.find(
				"select a from ContractImgCav a where a.contractCav.id = ? "
						+ " order by a.seqNum ", new Object[] { parentId });
	}

	/**
	 * 根据手册号及料件序号 抓取合同备案料件资料
	 * 
	 * @param emsNo
	 *            手册号
	 * @param seqNum
	 *            料件序号
	 * @return 为ContractImg型，合同备案料件资料。
	 */
	public ContractImg findContractImg(String emsNo, Integer seqNum) {
		List list = this
				.find(
						"select a from ContractImg a where a.seqNum=? and a.contract.emsNo=? and a.contract.declareState=?",
						new Object[] { seqNum, emsNo, DeclareState.PROCESS_EXE });
		if (list.size() > 0) {
			return (ContractImg) list.get(0);
		} else {
			return null;
		}

	}

	/**
	 * 根据手册号及料件序号 统计报关单体数量
	 * 
	 * @param emsNo
	 *            手册号
	 * @param seqNum
	 *            料件序号
	 * @return List为 BcsCustomsDeclarationCommInfo型，统计报关单体数量
	 */
	public List findBcsCustomsDeclarationCommInfo(String emsNo, Integer seqNum) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.commSerialNo,sum(a.commAmount),a.unit.code,sum(a.firstAmount),a.legalUnit.code,sum(a.secondAmount),a.secondLegalUnit.code "
				+ " from BcsCustomsDeclarationCommInfo  a  "
				+ " where a.baseCustomsDeclaration.emsHeadH2k=? "
				+ " and a.commSerialNo=? "
				+ " and a.baseCustomsDeclaration.company.id=? "
				+ " group by a.commSerialNo,a.unit.code,a.legalUnit.code,a.secondLegalUnit.code ";
		parameters.add(emsNo);
		parameters.add(seqNum);
		parameters.add(CommonUtils.getCompany().getId());
		return this.find(hsql, parameters.toArray());

	}

	/**
	 * 查找合同核销料件 来自 合同核销表头ID和料件序号
	 * 
	 * @param contractCavId
	 *            合同核销表头ID
	 * @param imgSeqNum
	 *            料件序号
	 * @param mname
	 *            商品名称
	 * @return List 是ContractImgCav型，合同核销料件
	 */
	public ContractImgCav findContractImgCavBySeqNum(String contractCavId,
			Integer imgSeqNum, String mname) {
		List list = this.find(
				"select a from ContractImgCav a where a.contractCav.id = ? "
						+ " and a.seqNum=?  and a.name=?  ", new Object[] {
						contractCavId, imgSeqNum, mname });
		if (list.size() > 0) {
			return (ContractImgCav) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 查找合同核销BOM 来自 合同核销表头ID
	 * 
	 * @param parentId
	 *            合同核销表头Id
	 * @return List 是ContractBomCav型，合同核销BOM
	 */
	public List findContractBomCavByContractParentId(String parentId) {
		return this.find(
				"select a from ContractBomCav a where a.contractCav.id = ? ",
				new Object[] { parentId });
	}

	/**
	 * 查找合同核销BOM 来自 合同核销表头ID和商品序号
	 * 
	 * @param contractCavId
	 *            合同核销表头ID
	 * @param imgSeqNum
	 *            商品序号
	 * @return List 是ContractBomCav型，合同核销BOM
	 */
	public List findContractBomCavByImgSeqNum(String contractCavId,
			String imgSeqNum) {
		return this.find(
				"select a from ContractBomCav a where a.contractCav.id = ?"
						+ " and a.seqMaterialNum=? "
						+ " order by a.seqProductNum ", new Object[] {
						contractCavId, imgSeqNum });
	}

	/**
	 * 查找合同核销BOM 来自 合同核销表头ID和商品序号
	 * 
	 * @param contractCavId
	 *            合同核销表头ID
	 * @param imgSeqNum
	 *            商品序号
	 * @return List 是ContractBomCav型，合同核销BOM
	 */
	public List findContractBomCavByImgSeqNum(String contractCavId,
			Integer imgSeqNum) {
		return this.find(
				"select a from ContractBomCav a where a.contractCav.id = ?"
						+ " and a.seqMaterialNum=? "
						+ " order by a.seqProductNum ", new Object[] {
						contractCavId, imgSeqNum });
	}

	/**
	 * 查找合同核销BOM 来自 合同核销表头ID和商品序号
	 * 
	 * @param contractCavId
	 *            合同核销表头ID
	 * @param imgSeqNum
	 *            商品序号
	 * @return List 是ContractBomCav型，合同核销BOM
	 */
	public List findContractBomCavByExgSeqNum(String contractCavId,
			Integer seqProductNum) {
		return this.find(
				"select a from ContractBomCav a where a.contractCav.id = ?"
						+ " and a.seqProductNum=? "
						+ " order by a.seqMaterialNum ", new Object[] {
						contractCavId, seqProductNum });
	}

	/**
	 * 根据合同核销表头查找合同核销成品表里有多少条数据
	 * 
	 * @param contractCavId
	 *            合同核销表头ID
	 * @return int 数据条数
	 */
	public int findContractExgCavCount(String contractCavId) {
		List list = this
				.find(" select count(a.id) from ContractExgCav as a "
						+ "  where a.contractCav.id=? ",
						new Object[] { contractCavId });
		if (list != null && !list.isEmpty()) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 根据手册号码查询报关单
	 * 
	 * @param emsNo
	 *            电子手册手册编号
	 * @return List 是DzscCustomsDeclaration类型，电子手册报关单
	 */
	public List findBcsCustomsDeclaration(String emsNo) {
		String hql = "select a from BcsCustomsDeclaration a "
				+ " where a.emsHeadH2k=? "
				+ " and a.effective=? and a.company.id=? "
				+ " order by a.impExpFlag";
		return this.find(hql, new Object[] { emsNo, new Boolean(true),
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 抓取电子帐册合同核销BOM资料
	 * 
	 * @param contractCav
	 *            电子帐册合同核销单头
	 * @return List 是BcsContractBomCav类型，电子帐册合同核销BOM资料
	 */
	public List findContractBomCavChanged(ContractCav contractCav) {
		return this.find(
				"select a from ContractBomCav as a where a.contractCav.id=? "
						+ " and a.modifyMark in (?,?,?) ", new Object[] {
						contractCav.getId(), ModifyMarkState.ADDED,
						ModifyMarkState.DELETED, ModifyMarkState.MODIFIED });
	}

	/**
	 * 统计核销发票数量
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @param seqNum
	 *            对应帐册核销序号
	 * @return Double 统计核销发票数量
	 */

	public Double findCasInvoiceInfoNum(String emsNo, Integer seqNum) {
		Double sum = 0.0;
		List list = this
				.find(
						"select SUM(a.canceledNum) from CasInvoiceInfo as a where a.company.id=? "
								+ " and a.casInvoice.emsNo=?  and a.cancelEmsSeqNum= ? "
								+ " and  a.canceledNum is not null ",
						new Object[] { CommonUtils.getCompany().getId(), emsNo,
								seqNum });
		if (list.size() > 0 && list.get(0) != null) {
			sum = Double.parseDouble(list.get(0).toString());
		}
		return sum;
	}

	/**
	 * 根据贸易方式代码，进出口标志。抓取核扣方式资料
	 * 
	 * @param tradeCode
	 *            贸易方式代码
	 * @param impExpFlag
	 *            进出口标志
	 * @return List 为Deduc型，核扣方式资料。
	 */
	public List findDeduc(String tradeCode, String impExpFlag) {
		List list = this.find(" select a from Deduc as a "
				+ "  where a.tradeCode=? and a.inOutType=?", new Object[] {
				tradeCode, impExpFlag });
		return list;
	}

	/**
	 * 根据帐册编号抓取合同备案表头资料
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @return List 为Contract型，合同备案表头资料。
	 */
	public List findCavContractByEmsNo(String emsNo) {
		List list = this
				.find(
						" select a from Contract  as a "
								+ "  where a.company.id=? and  a.emsNo=? and a.declareState in(?,?) ",
						new Object[] { CommonUtils.getCompany().getId(), emsNo,
								DeclareState.CHANGING_CANCEL,
								DeclareState.PROCESS_EXE });
		return list;
	}

	/**
	 * 根据核销id抓取合同核销料件资料
	 * 
	 * @param cavheadId
	 *            核销id
	 * @return List为ContractImgCav型，合同核销料件资料。
	 */
	public List findContractImgCavByBuiner(String cavheadId) {
		List list = this
				.find(
						" select   a from   ContractImgCav a where "
								+ " a.company.id=?  and  a.name   like  ?  and a.contractCav.id=?"
								+ " order  by a.seqNum ", new Object[] {
								CommonUtils.getCompany().getId(), "(国内购买)%",
								cavheadId });
		return list;
	}

	/**
	 * 查找国内购买料件
	 * 
	 * @param contractCavId
	 *            核销id
	 * @return List 合同核销资料
	 */
	public List findContractCavDomesticPurchaseBill(String contractCavId) {
		return this
				.find(
						"select a.seqMaterialNum,b.name,b.unit.name,"
								+ "sum((case when (a.unitWaste/(1-a.waste/100.0)) is null then 0.0 else (a.unitWaste/(1-a.waste/100.0)) end)*"
								+ "(case when a.exgExpTotalAmount is null then 0.0 else a.exgExpTotalAmount end)),"
								+ "sum((case when c.unitWeight is null then 0.0 else c.unitWeight end)*"
								+ "(case when a.exgExpTotalAmount is null then 0.0 else a.exgExpTotalAmount end)*"
								+ "(case when (a.unitWaste/(1-a.waste/100.0)) is null then 0.0 else (a.unitWaste/(1-a.waste/100.0)) end)),"
								+ "sum((case when c.amount is null then 0.0 else c.amount end)*"
								+ "(case when c.unitWeight is null then 0.0 else c.unitWeight end)),"
								+ "sum((case when c.amount is null then 0.0 else c.amount end)*"
								+ "(case when c.declarePrice is null then 0.0 else c.declarePrice end))"
								+ "from ContractBomCav a,ContractImgCav b, ContractImg c "
								+ "where a.contractCav.id=b.contractCav.id "
								+ "and a.seqMaterialNum=b.seqNum "
								+ "and a.contractCav.emsNo=c.contract.emsNo "
								+ "and b.seqNum=c.seqNum "
								+ "and b.contractCav.id=? "
								+ "and c.contract.declareState=? "
								+ "and (b.name like ? or b.spec like ?)"
								+ " group by a.seqMaterialNum,b.name,b.unit.name",
						new Object[] { contractCavId, DeclareState.PROCESS_EXE,
								"%内购%", "%内购%" });
	}

	// b.totalAmount
	/**
	 * 打印备案总耗与实际总耗对照表
	 * 
	 * @param contractCavId
	 *            核销id
	 * @return List 为合同核销资料
	 */
	// and a.contractCav.id=b.contractCav.id
	// a.contractCav.emsNo=c.contractExg.contract.emsNo
	public List findBomFilingRealityCompareBill(String contractCavId) {
		return this
				.find(
						"select b.seqMaterialNum,a.name,a.unit.name,"
								+ "sum((case when c.unitDosage is null then 0.0 else c.unitDosage end)*"
								+ "(case when c.contractExg.exportAmount is null then 0.0 else c.contractExg.exportAmount end)),"
								+ "sum((case when b.totalAmount is null then 0.0 else b.totalAmount end)),"
								+ "sum((case when a.directImport is null then 0.0 else a.directImport end)+"
								+ "(case when a.transferFactoryImport is null then 0.0 else a.transferFactoryImport end)),"
								+ "sum((case when b.totalAmount is null then 0.0 else b.totalAmount end)-"
								+ "(case when c.unitDosage is null then 0.0 else c.unitDosage end)*"
								+ "(case when c.contractExg.exportAmount is null then 0.0 else c.contractExg.exportAmount end)) "
								+ "from ContractImgCav a,ContractBomCav b,ContractBom c"
								+ " where a.contractCav.id=b.contractCav.id "
								+ "and a.seqNum=b.seqMaterialNum "
								+ "and a.contractCav.emsNo=c.contractExg.contract.emsNo "
								+ "and a.seqNum=c.contractImgSeqNum "
								+ "and b.seqMaterialNum=c.contractImgSeqNum "
								+ "and b.contractCav.emsNo=c.contractExg.contract.emsNo "
								+ "and c.contractExg.contract.declareState=? "
								+ "and a.contractCav.id=? "
								+ "group by a.name,a.unit.name,b.seqMaterialNum",
						new Object[] { DeclareState.PROCESS_EXE, contractCavId });// b.totalAmount
		// b.seqMaterialNum,
		// sum((case when c.unitDosage is null then 0.0 else c.unitDosage
		// end)*(case when c.contractExg.exportAmount is null then 0.0 else
		// c.contractExg.exportAmount end))
	}

	/**
	 * 根据帐册编号，成品备案序号，合同号。查找合同备案成品资料
	 * 
	 * @param contractId
	 *            帐册编号
	 * @param exgId
	 *            成品备案序号
	 * @param contractNo
	 *            合同号
	 * @return ContractExg 为ContractExg型，合同备案成品资料
	 */
	public ContractExg getContractExgByContract(String contractId,
			Integer exgId, String contractNo) {
		List list = this
				.find(
						"from ContractExg as a where a.contract.emsNo=? and a.seqNum=? and  a.company.id=? and a.contract.declareState=3",
						new Object[] { contractId, exgId,
								CommonUtils.getCompany().getId() });
		if (list.size() > 0) {
			return (ContractExg) list.get(0);
		}
		return null;
	}

	/**
	 * 根据手册好抓取核销表头
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @return List 是ContractCav类型，合同核销表头
	 */
	public List findContractCavByEmsNo(String emsNo) {
		String hsql = "select a from ContractCav a where a.emsNo=?";
		return this.find(hsql, new Object[] { emsNo });
	}

	/**
	 * 根据帐册编号，成品备案序号，合同号。查找手册通关备案的成品资料
	 * 
	 * @param contractId
	 *            帐册编号
	 * @param exgId
	 *            成品备案序号
	 * @param contractNo
	 *            合同号
	 * @return DzscEmsExgBill 手册通关备案的成品资料
	 */
	public DzscEmsExgBill getDzscEmsExgBillExgByContract(String contractId,
			Integer exgId, String contractNo) {
		List list = this
				.find(
						"from DzscEmsExgBill as a where a.dzscEmsPorHead.emsNo=? and a.seqNum=? and  a.company.id=? and a.dzscEmsPorHead.declareState=?",
						new Object[] { contractId, exgId,
								CommonUtils.getCompany().getId(),
								DzscState.EXECUTE });
		
		if (list.size() > 0) {
			return (DzscEmsExgBill) list.get(0);
		}
		return null;
	}
}
