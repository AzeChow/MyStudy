/*
 * Created on 2005-3-21
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contract.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import com.bestway.bcs.bcsinnermerge.entity.BcsCustomsBomDetail;
import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contract.entity.ProcessedContractData;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclaration;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationCommInfo;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.erpbill.entity.CustomOrder;

/**
 * 合同操作com.bestway.bcs.contract.dao.ContractDao lm checked by 2009-11-05
 * 
 * @author
 */
@SuppressWarnings("unchecked")
public class ContractDao extends BaseDao {

	/**
	 * 保存BCS参数
	 * 
	 * @param parameter
	 */
	public void saveBcsParameterSet(BcsParameterSet parameter) {
		this.saveOrUpdate(parameter);
	}

	/**
	 * 获取className里最大的流水号
	 * 
	 * @param className
	 *            表名
	 * @param seqNum
	 *            流水号
	 * @return Sting className里最大的流水号
	 */
	public String getNum(String className, String seqNum) {
		String num = "1";
		List list = this.find("select max(a." + seqNum + ") from " + className
				+ " as a " + "where a.company= ?",
				new Object[] { CommonUtils.getCompany() });
		if (list.get(0) != null) {
			num = list.get(0).toString();
			num = String.valueOf(Integer.parseInt(num) + 1);
		}
		return num;
	}

	/**
	 * 查询BCS参数
	 * 
	 * @param parameter
	 */
	public BcsParameterSet findBcsParameterSet() {
		List list = this.find(
				"select a from BcsParameterSet a where a.company.id=? ",
				new Object[] { CommonUtils.getCompany().getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return (BcsParameterSet) list.get(0);
		} else {
			BcsParameterSet parameterSet = new BcsParameterSet();
			this.saveOrUpdate(parameterSet);
			return parameterSet;
		}
	}

	/**
	 * 查询是否已备案
	 */
	public List findEquipModeState(String emsNo) {
		return this
				.find("select a.equipMode from Contract a where a.company= ? and a.emsNo = ? ",
						new Object[] { CommonUtils.getCompany(), emsNo });
	}

	/**
	 * 获取当前公司
	 * 
	 * @return Company 当前公司，返回符合条件的第一条数据
	 */
	public Company findCompany() {
		List list = this.find("select a from Company a where a.id = ?",
				new Object[] { CommonUtils.getCompany().getId() });
		return (Company) list.get(0);
	}

	/**
	 * 取得系统的其他参数设置
	 * 
	 * @return CompanyOther 其他参数设置,返回符合条件的第一条数据
	 */
	public CompanyOther getSysCompanyOther() {
		List list = this.find(
				"select a from CompanyOther a where a.company.id =?",
				new Object[] { CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			return (CompanyOther) list.get(0);
		}
		return null;
	}

	/**
	 * 返回合同料件{imgSeqNum}的总耗用量
	 * 
	 * @param head
	 *            合同备案表头
	 * @param imgSeqNum
	 *            料件总表序号
	 * @return double 合同的总耗用量
	 */
	public Double findEmsPorImgTotalNum(Contract head, Integer imgSeqNum) {
		List list = this.find(
				"select sum(a.amount) from ContractBom a where a.contractExg.contract.id = ?"
						+ " and a.contractImgSeqNum = ?",
				new Object[] { head.getId(), imgSeqNum });
		if (list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	/**
	 * 查找所有的合同
	 * 
	 * @return List 是Contract型，合同备案表头
	 */
	public List findContract() {
		return this.find("select a from Contract a where a.company= ? ",
				new Object[] { CommonUtils.getCompany() });
	}

	/**
	 * 根据ID查找合同
	 * 
	 * @return List 是Contract型，合同备案表头
	 */
	public Contract findContractById(String id) {
		List list = this.find(
				"select a from Contract a where a.company= ? and a.id=? ",
				new Object[] { CommonUtils.getCompany(), id });
		if (list.size() > 0) {
			return (Contract) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据EMS_NO查找正在执行的合同
	 * 
	 * @return List 是Contract型，合同备案表头
	 */
	public Contract findExingContractByEmsNo(String emsNo) {
		List list = this.find(
				"select a from Contract a where a.company= ? and a.emsNo=? "
						+ " and a.declareState=? ",
				new Object[] { CommonUtils.getCompany(), emsNo,
						DeclareState.PROCESS_EXE });
		if (list.size() > 0) {
			return (Contract) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 转厂申请表根据emsNO手册号查询企业合同号
	 * 
	 * @param emsNo
	 * @return
	 */
	public Contract findContractByEmsNO(String emsNo) {
		List list = this.find(
				"select a from Contract a where a.company= ? and a.emsNo=? ",
				new Object[] { CommonUtils.getCompany(), emsNo });
		if (list.size() > 0) {
			return (Contract) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 获得最大的合同流水号
	 * 
	 * @return String 最大的合同流水号
	 */
	public String getMaxPreContractCodeSuffix() {
		String code = "";
		List list = this
				.find("select max(a.preContractCodeSuffix) from Contract as a where a.company= ? ",
						CommonUtils.getCompany());
		if (list.size() < 1) {
			code = "000001";
		} else if (list.get(0) == null) {
			code = "000001";
		} else {
			String temp = list.get(0).toString();
			if (temp.trim().equals("")) {
				code = "000001";
			} else {
				int n = Integer.parseInt(temp) + 1;
				code = organizeStr(n, 6);
			}
		}
		return code;
	}

	/**
	 * 在改变数据的长度，在前面加入“0”
	 * 
	 * @param m
	 *            要改变长度的数据
	 * @param length
	 *            要改变到的长度
	 * @return String 改变好的长度
	 */
	private String organizeStr(int m, int length) {
		String s = String.valueOf(m);
		int n = length - s.length();
		for (int i = 0; i < n; i++) {
			s = "0" + s;
		}
		return s;
	}

	/**
	 * 查找所有的合同是否已审核
	 * 
	 * @param isCancel
	 *            审核判断，true表示已审核
	 * @return List 是Contract型，合同备案表头
	 */
	public List findContract(boolean isCancel) {
		return this.find(
				"select a from Contract a where a.company= ? and ( a.isCancel=? ) "
						+ " order by a.beginDate,a.newApprDate", new Object[] {
						CommonUtils.getCompany(), new Boolean(isCancel) });
	}

	/**
	 * 查找所有的合同是否已审核
	 * 
	 * @param isCancel
	 *            审核判断，true表示已审核
	 * @return List 是Contract型，合同备案表头
	 */
	public List findContractInCav(boolean isCancel) {
		if (isCancel) {
			return this.find(
					"select a from Contract a where a.company= ? and ( a.isCancel=? ) "
							+ " order by a.beginDate,a.newApprDate",
					new Object[] { CommonUtils.getCompany(),
							new Boolean(isCancel) });
		} else {
			return this
					.find("select a from Contract a where a.company= ? and ( a.isCancel=? ) and a.declareState=? order by a.beginDate",
							new Object[] { CommonUtils.getCompany(),
									new Boolean(false),
									DeclareState.PROCESS_EXE });
		}
	}

	/**
	 * 查找所有的合同来自正在执行的
	 * 
	 * @return List 是Contract型，合同备案表头
	 */
	public List findContractByProcessExe() {

		return this
				.find("select a from Contract a where a.company= ? and ( a.isCancel=? ) and a.declareState=? order by a.beginDate",
						new Object[] { CommonUtils.getCompany(),
								new Boolean(false), DeclareState.PROCESS_EXE });
	}

	/**
	 * 查找所有的合同来自正在执行的且来源于相同的备案库
	 * 
	 * @return List 是Contract型，合同备案表头
	 */
	public List findContractByProcessExecorrEmsNo(String corrEmsNo) {
		return this
				.find("select a from Contract a where a.company= ? and ( a.isCancel=? ) and a.declareState in (? ,?) and a.corrEmsNo = ?   order by a.beginDate",
						new Object[] { CommonUtils.getCompany(),
								new Boolean(false), DeclareState.PROCESS_EXE,
								DeclareState.APPLY_POR, corrEmsNo });
	}

	/**
	 * 查找所有的合同来自正在执行的
	 * 
	 * @return List 是Contract型，合同备案表头
	 */
	public List findContractByProcessExeEndDate() {
		List list1 = this.find(
				" select a from Contract a where a.company= ? and ( a.isCancel=? )"
						+ " and a.declareState=?   and a.endDate  is null ",
				new Object[] { CommonUtils.getCompany(), new Boolean(false),
						DeclareState.PROCESS_EXE });
		List list2 = this.find(
				"select a from Contract a where a.company= ? and ( a.isCancel=? )"
						+ " and a.declareState=?   and a.endDate is not null "
						+ "  order by a.endDate ",
				new Object[] { CommonUtils.getCompany(), new Boolean(false),
						DeclareState.PROCESS_EXE });
		list2.addAll(list1);
		return list2;
	}

	/**
	 * 查找所有的合同来自正在执行的
	 * 
	 * @return List 是Contract型，合同备案表头
	 */
	public List findContractByProcessExe(Company company) {
		return this
				.find("select a from Contract a where a.company= ? and ( a.isCancel=? ) and a.declareState=? ",
						new Object[] { company, new Boolean(false),
								DeclareState.PROCESS_EXE });
	}

	/**
	 * 查找所有的合同来自正在执行的
	 * 
	 * @return List 是Contract型，合同备案表头
	 */
	public List findContractEmsNOByProcessExe(Company company) {
		return this
				.find("select a.emsNo from Contract a where a.company= ? and ( a.isCancel=? ) and a.declareState=? ",
						new Object[] { company, new Boolean(false),
								DeclareState.PROCESS_EXE });
	}

	/**
	 * 查找条件是申报状态的合同
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @param declareState
	 *            申报状态
	 * @return List 是Contract型，合同备案表头
	 */
	public Contract findContractByEmsNo(String emsNo, String declareState) {
		List list = this
				.find("select a from Contract a where a.company= ? and a.emsNo= ? and a.declareState=? ",
						new Object[] { CommonUtils.getCompany(), emsNo,
								declareState });
		if (list != null && list.size() > 0) {
			return (Contract) list.get(0);
		}
		return null;
	}

	/**
	 * 查找条件是申报状态的合同
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @return List 是Contract型，合同备案表头
	 */
	public Contract findContractByEmsNo(String emsNo) {
		List list = this
				.find("select a from Contract a where a.company= ? and a.emsNo= ? and a.declareState=? ",
						new Object[] { CommonUtils.getCompany(), emsNo,
								DeclareState.PROCESS_EXE });
		if (list != null && list.size() > 0) {
			return (Contract) list.get(0);
		}
		return null;
	}

	/**
	 * 查找条件对应手册号下所有状态的合同
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @return List 是Contract型，合同备案表头
	 */
	public Contract findAllContractByEmsNo(String emsNo) {
		List list = this
				.find("select a from Contract a where a.company.id = ? and a.emsNo= ? ",
						new Object[] { CommonUtils.getCompany().getId(), emsNo });
		if (list != null && list.size() > 0) {
			return (Contract) list.get(0);
		}
		return null;
	}

	/**
	 * 根据内部编号查找备案手册
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @return List 是Contract型，合同备案表头
	 */
	public List findContractByCopEmsNo(String copEmsNo) {
		return this
				.find("select a from Contract a where a.company= ? and a.copEmsNo= ?  ",
						new Object[] { CommonUtils.getCompany(), copEmsNo });

	}

	/**
	 * 该帐册编号的合同是否存在
	 * 
	 * @param contract
	 *            合同备案表头
	 * @return boolean 帐册编号的合同是否存在，存在为true，否则为false
	 */
	public boolean isExistContractByEmsNo(Contract contract) {
		if ("".equals(contract.getEmsNo())) {
			return false;
		}
		List list = new ArrayList();
		if (contract.getId() == null || contract.getId().equals("")) {
			list = this
					.find("select a from Contract a where a.company= ? and a.emsNo= ?  ",
							new Object[] { CommonUtils.getCompany(),
									contract.getEmsNo() });
		} else {
			list = this.find(
					"select a from Contract a where a.company= ? and a.emsNo= ? "
							+ " and a.id<>? ",
					new Object[] { CommonUtils.getCompany(),
							contract.getEmsNo(), contract.getId() });
		}
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 保存合同
	 * 
	 * @param contract
	 *            合同备案表头
	 */
	public void saveContract(Contract contract) {
		this.saveOrUpdate(contract);
	}

	/**
	 * 批量保存合同
	 * 
	 * @param list
	 *            合同备案表头
	 */
	public void saveContract(List list) {
		for (int i = 0; i < list.size(); i++) {
			this.saveOrUpdate(list.get(i));
		}
	}

	/**
	 * 批量删除合同
	 * 
	 * @param list
	 *            合同备案表头
	 */
	public void deleteContract(List list) {
		this.deleteAll(list);
	}

	/**
	 * 查询订单转合同的中间表
	 * 
	 * @param contractImgExgId
	 * @return
	 */
	public List findMakeCustomOrderToContract(String contractImgExgId) {
		return this.find(
				"select a from MakeCustomOrderToContract a where a.contractImgExgId= ? "
						+ " and a.company=? ", new Object[] { contractImgExgId,
						CommonUtils.getCompany() });

	}

	/**
	 * 查询订单明细表
	 * 
	 * @param customOrderDetailId
	 * @return
	 */
	public List findCustomOrderDetail(String customOrderDetailId) {
		return this.find("select a from CustomOrderDetail a where a.id= ? "
				+ " and a.company=? ", new Object[] { customOrderDetailId,
				CommonUtils.getCompany() });

	}

	/**
	 * 统计已转合同数
	 * 
	 * @param head
	 * @return
	 */
	public Integer getCustomOrderDetailForToContract(CustomOrder head) {
		List list = this.find(
				"select count(a.id) from CustomOrderDetail a where "
						+ "a.customOrder.id = ? and a.ifcustomer = ? ",
				new Object[] { head.getId(), new Boolean(true) });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 查找所有的合同BOM
	 * 
	 * @return List 是ContractBom型，合同BOM
	 */
	public List findContractBom() {
		return this.find("select a from ContractBom a where a.company= ? "
				+ " order by a.seqNum ",
				new Object[] { CommonUtils.getCompany() });
	}

	/**
	 * 查找合同BOM 来自 合同成品ID
	 * 
	 * @param exgId
	 *            合同成品Id
	 * @return List 是ContractBom型，合同BOM
	 */
	public List<ContractBom> findContractBomByExgId(String exgId) {

		String hql = "select a from ContractBom a left join a.contractExg"
				+ " where a.contractExg.id = '" + exgId
				+ "'  and a.company.id='" + CommonUtils.getCompany().getId()
				+ "' " + "  order by a.contractImgSeqNum, a.createDate ";

		return this.find("select a from ContractBom a left join a.contractExg"
				+ " where a.contractExg.id = ?  and a.company.id=? "
				+ "  order by a.contractImgSeqNum, a.createDate ",
				new Object[] { exgId, CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找合同BOM 来自 合同成品ID
	 * 
	 * @param exgId
	 *            合同成品Id
	 * @return List 是ContractBom型，合同BOM
	 */
	public List findContractBomByExgId(Contract contract, String exgId) {
		return this
				.find("select a from ContractBom a left join a.contractExg"
						+ " where a.contractExg.contract.id = ? and a.contractExg.id = ?  and a.company.id=? "
						+ "  order by a.createDate ",
						new Object[] { contract.getId(), exgId,
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找报关常用工厂BOM 来自
	 * 
	 * @param
	 * 
	 * @return List 是ContractBom型，合同BOM
	 */
	public List findMaterialBomDetailByExgId(String ptNo, Integer version) {
		return this
				.find("select a from MaterialBomDetail a "
						+ " left join a.version b "
						+ " left join a.materiel c"
						+ " where b.master.materiel.ptNo = ?  and b.version=? and a.company.id=?  "
						+ "  order by c.ptNo ", new Object[] { ptNo, version,
						CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找合同BOM 来自 合同成品ID
	 * 
	 * @param exgId
	 *            合同成品Id
	 * @return List 是ContractBom型，合同BOM
	 */
	public List findContractBomByExgSeqNum(Contract contract, Integer exgSeqNum) {
		return this.find("select a from ContractBom a left join a.contractExg"
				+ " where a.contractExg.seqNum = ? "
				+ " and a.contractExg.contract.id=? and a.company.id=? ",
				new Object[] { exgSeqNum, contract.getId(),
						CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找合同BOM 来自 合同成品ID
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @param exgSeqNum
	 *            成品序号
	 * @param imgSeqNum
	 *            料件总表序号
	 * @return List 是ContractBom型，合同BOM
	 */
	public ContractBom findContractBom(String contractId, String exgSeqNum,
			String imgSeqNum) {
		List list = this
				.find("select a from ContractBom a where a.contractExg.contract.id = ?"
						+ " and a.contractExg.seqNum=? and a.contractImgSeqNum=? "
						+ " order by a.seqNum ",
						new Object[] { contractId, Integer.valueOf(exgSeqNum),
								Integer.valueOf(imgSeqNum) });
		if (list.size() <= 0) {
			return null;
		} else {
			return (ContractBom) list.get(0);
		}
	}

	/**
	 * 查找合同BOM 来自 合同成品ID
	 * 
	 * @param parentId
	 *            合同成品Id
	 * @return List 是ContractBom型，合同BOM
	 */
	public List findContractBomByContractParentId(String parentId) {
		return this.find(
				"select a from ContractBom a where a.contractExg.contract.id = ? "
						+ " order by a.seqNum ", new Object[] { parentId });
	}

	/**
	 * 查找合同BOM 来自 合同除未修改成品ID
	 * 
	 * @param parentId
	 *            合同成品Id
	 * @return List 是ContractBom型，合同BOM
	 */
	public List findModifyContractBomByContractParentId(String parentId) {
		return this
				.find("select a from ContractBom a where a.contractExg.contract.id = ? and a.modifyMark!=0"
						+ " order by a.seqNum ", new Object[] { parentId });
	}

	/**
	 * 查找合同合同合同BOM 来自 合同除未修改成品ID
	 * 
	 * @param parentId
	 *            合同成品Id
	 * @return List 是ContractBom型，合同BOM
	 */
	public List findContractBomByContract(String parentId) {
		return this.find(
				"select a from ContractBom a where a.contractExg.contract.id = ?"
						+ " order by a.seqNum ", new Object[] { parentId });
	}

	/**
	 * 查找合同BOM 来自 合同成品ID
	 * 
	 * @param parentId
	 *            合同成品Id
	 * @return List 是ContractBom型，合同BOM
	 */

	public List findAddedContractBomByContract(String parentId) {
		return this
				.find("select a from ContractBom a where a.contractExg.contract.id = ? and a.modifyMark=? "
						+ " order by a.seqNum ", new Object[] { parentId,
						ModifyMarkState.ADDED });
	}

	/**
	 * 查找合同BOM 来自 合同成品ID
	 * 
	 * @param parentId
	 *            合同成品Id
	 * @return List 是ContractBom型，合同BOM
	 */
	public List findContractBomByContractParentIdChange(String parentId) {
		return this
				.find("select a from ContractBom a where a.contractExg.contract.id = ?  and a.modifyMark in (?,?)  ",
						new Object[] { parentId, ModifyMarkState.MODIFIED,
								ModifyMarkState.ADDED });
	}

	/**
	 * 查找合同BOM 来自 合同成品ID
	 * 
	 * @param parentId
	 *            合同成品Id
	 * @return List 是ContractBom型，合同BOM
	 */
	public List findContractBomStateChanged(String parentId) {
		return this.find("select a from ContractBom a "
				+ " left outer join fetch a.contractExg "
				+ " left outer join fetch a.contractExg.contract "
				+ " where a.contractExg.contract.id = ? "
				+ " and a.modifyMark<>? order by a.seqNum ", new Object[] {
				parentId, ModifyMarkState.UNCHANGE });
	}

	/**
	 * 批量更新 修改状态的bom的 状态字段
	 * 
	 * @param contratId
	 *            合同ID
	 */
	public void batchUpdateContractBomStateToUnChanged(String contractId) {
		String hql = "update ContractBom a set a.modifyMark = ? where (a.modifyMark = ? or a.modifyMark = ?)"
				+ " and a.contractExg.id in (select b.id from ContractExg b where b.contract.id = ? ) ";
		this.batchUpdateOrDelete(hql, new Object[] { ModifyMarkState.UNCHANGE,
				ModifyMarkState.ADDED, ModifyMarkState.MODIFIED, contractId });
	}

	/**
	 * 删除标记为删除状态的合同BOM
	 * 
	 * @param contractId
	 */
	public void batchDeleteContractBomByDeletedState(String contractId) {
		String hql = "delete from  ContractBom a where a.modifyMark = ? "
				+ " and a.contractExg.id in (select b.id from ContractExg b where b.contract.id = ? ) ";
		this.batchUpdateOrDelete(hql, new Object[] { ModifyMarkState.DELETED,
				contractId });
	}

	/**
	 * 批量更新 修改状态的Exg的 状态字段
	 * 
	 * @param contratId
	 *            合同ID
	 */
	public void batchUpdateContractExgStateToUnChanged(String contractId) {
		String hql = "update ContractExg a set a.modifyMark = ? where (a.modifyMark = ? or a.modifyMark = ?)"
				+ " and a.contract.id = ? ";
		this.batchUpdateOrDelete(hql, new Object[] { ModifyMarkState.UNCHANGE,
				ModifyMarkState.ADDED, ModifyMarkState.MODIFIED, contractId });
	}

	/**
	 * 删除标记为删除状态的合同成品
	 * 
	 * @param contractId
	 */
	public void batchDeleteContractExgByDeletedState(String contractId) {
		String hql = "delete from ContractExg a where a.modifyMark = ? and a.contract.id = ? ";
		this.batchUpdateOrDelete(hql, new Object[] { ModifyMarkState.DELETED,
				contractId });
	}

	/**
	 * 批量更新 修改状态的Img的 状态字段
	 * 
	 * @param contratId
	 *            合同ID
	 */
	public void batchUpdateContractImgStateToUnChanged(String contractId) {
		String hql = "update ContractImg a set a.modifyMark = ? where (a.modifyMark = ? or a.modifyMark = ?)"
				+ " and a.contract.id = ? ";
		this.batchUpdateOrDelete(hql, new Object[] { ModifyMarkState.UNCHANGE,
				ModifyMarkState.ADDED, ModifyMarkState.MODIFIED, contractId });
	}

	/**
	 * 删除 标记为删除状态的合同料件
	 * 
	 * @param contractId
	 */
	public void batchDeleteContractImgByDeletedState(String contractId) {
		String hql = "delete from  ContractImg a where a.modifyMark = ? and a.contract.id = ? ";
		this.batchUpdateOrDelete(hql, new Object[] { ModifyMarkState.DELETED,
				contractId });
	}

	/**
	 * 查找合同BOM 来自 合同ID 和料件序号
	 * 
	 * @param parentId
	 *            合同Id
	 * @param seqNum
	 *            料件总表序号
	 * @return List 是ContractBom型，合同BOM
	 */
	public List findContractBom(String parentId, Integer seqNum) {
		return this.find(
				"select a from ContractBom a where a.contractExg.contract.id = ? "
						+ " and a.contractImgSeqNum=? ", new Object[] {
						parentId, seqNum });
	}

	/**
	 * 查找合同BOM 来自 合同ID 和料件序号
	 * 
	 * @param parentId
	 *            合同成品Id
	 * @param seqNum
	 *            料件总表序号
	 * @return List 是ContractBom型，合同BOM
	 */
	public List findContractBom(List<Contract> contractList,
			ContractImg contractImg) {
		String hsql = "select a from ContractBom a where a.contractExg.contract.id in (";
		for (int i = 0, size = contractList.size(); i < size; i++) {
			Contract c = contractList.get(i);
			if (i == 0) {
				hsql += "'" + c.getId() + "'";
				continue;
			}
			hsql += ",'" + c.getId() + "'";
		}
		hsql += ")";
		hsql += " and a.complex.name=? and a.name=? and a.spec=? and a.unit.name=?";
		return this.find(hsql, new Object[] {
				contractImg.getComplex().getName(), contractImg.getName(),
				contractImg.getSpec(), contractImg.getUnit().getName() });
	}

	/**
	 * 查询 成品耗用量(包含所有的报关单)
	 * 
	 * @param img
	 *            合同料件
	 * @return List <[进出标记(impExpType),成品耗用量]>
	 */
	public List<Object[]> getProductUsedAmountByImpExpTypes(ContractImg img,
			Integer[] impExpTypes) {
		String hql = " select ci.baseCustomsDeclaration.impExpType,sum(ci.commAmount*(a.unitWaste/(1-a.waste/100.0))) "
				+ " from ContractBom a,ContractExg exg, BcsCustomsDeclarationCommInfo ci  "
				+ " where a.contractExg.id=exg.id "
				+ " and exg.seqNum = ci.commSerialNo and exg.contract.emsNo = ci.baseCustomsDeclaration.emsHeadH2k "
				+ " and exg.contract.company.id = ci.baseCustomsDeclaration.company.id "
				+ " and ci.baseCustomsDeclaration.impExpType in ("
				+ StringUtils.join(impExpTypes, ",")
				+ ") "
				+ " and a.contractImgSeqNum = ? and a.company= ? and exg.contract.id = ? "
				+ " group by ci.baseCustomsDeclaration.impExpType ";
		return this.find(hql,
				new Object[] { img.getSeqNum(), CommonUtils.getCompany(),
						img.getContract().getId() });
	}

	/**
	 * 查找合同BOM 来自 料件凭证号
	 * 
	 * @param contractImg
	 *            合同料件
	 * @return List 是ContractBom型，合同BOM
	 */
	public List findContractBomByImgSeqNum(ContractImg contractImg) {
		/**
		 * 编码相同,名称,规格,单位相同,单重 == key
		 */
		return this.find(
				"select a from ContractBom a where a.contractImgSeqNum = ? "
						+ "and a.company= ? and a.contractExg.contract.id = ? "
						+ " order by a.seqNum ",
				new Object[] { contractImg.getSeqNum(),
						CommonUtils.getCompany(),
						contractImg.getContract().getId() });
	}

	/**
	 * 查找合同BOM 来自 料件，成品序号
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @param exgSeqNum
	 *            成品序号
	 * @param imgSeqNum
	 *            料件总表序号
	 * @return List 是ContractBom型，合同BOM
	 */
	public ContractBom findContractBomByImgExgSeqNum(String emsNo,
			String exgSeqNum, String imgSeqNum) {
		List list = this.find(
				"select a from ContractBom a where a.contractExg.contract.emsNo = ?"
						+ " and a.contractExg.seqNum=? "
						+ " and a.contractImgSeqNum = ? "
						+ " and a.company.id= ? ",
				new Object[] { emsNo, exgSeqNum, imgSeqNum,
						CommonUtils.getCompany().getId() });
		if (list.size() > 0) {
			return (ContractBom) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 查找合同BOM 来自 料件，成品序号
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @param exgSeqNum
	 *            成品序号
	 * @param imgSeqNum
	 *            料件总表序号
	 * @return List 是ContractBom型，合同BOM
	 */
	public List findContractBomCavByImgSeqNum(String headid, Integer imgSeqNum) {
		List list = this.find(
				"select  distinct a  from ContractBomCav a  left join  a.contractCav  b "
						+ " where b.id  = ?  and a.seqMaterialNum = ? "
						+ " and a.company.id= ? ", new Object[] { headid,
						imgSeqNum, CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 查找合同某项成品总金额来自合同成品Id
	 * 
	 * @param contractExgId
	 *            合同成品Id
	 * @return double 合同某项成品总金额
	 */
	public double findContractBomTotalPrice(String contractExgId) {
		List list = this.find("select sum(a.totalPrice) from ContractBom a "
				+ " where a.company.id= ? and a.contractExg.id = ? "
				+ " and a.modifyMark<>? ", new Object[] {
				CommonUtils.getCompany().getId(), contractExgId,
				ModifyMarkState.DELETED });
		if (list.size() > 0 && list.get(0) != null) {
			return Double.parseDouble(list.get(0).toString());
		} else {
			return 0.0;
		}
	}

	/**
	 * 保存合同BOM
	 * 
	 * @param contractBom
	 *            合同BOM
	 */
	public void saveContractBom(ContractBom contractBom) {
		this.saveOrUpdate(contractBom);
	}

	/**
	 * 删除合同BOM
	 * 
	 * @param contractBom
	 *            合同Bom
	 */
	public void deleteContractBom(ContractBom contractBom) {
		this.delete(contractBom);
	}

	/**
	 * 批量删除合同BOM
	 */
	// public void deleteByContractBom(CheckParameter head) {
	// List list = this.findFactoryCheckExgConverImgAll(head);
	// this.deleteAll(list);
	// }
	public int deleteByContractBom(String parentId) {
		return batchUpdateOrDelete(
				"delete from ContractBom a where exists (select id from ContractExg where id=a.contractExg.id and contract.id =?)",
				parentId);
	}

	/**
	 * 批量删除合同成品
	 */
	public int deleteByContractExg(String parentId) {
		return batchUpdateOrDelete(
				"delete from ContractExg  where contract.id =?", parentId);
	}

	// /**
	// * 删除单耗为空或者为零的记录
	// *
	// * @param contract
	// */
	// public void deleteContractBomUnitWasteIsNull(Contract contract) {
	// String hql = "delete ContractBom where contractExg.contract=? "
	// + " and ( unitWaste<=0 or unitWaste is null )";
	// this.batchUpdateOrDelete(hql, new Object[] { contract });
	// }

	/**
	 * 批量保存合同BOM
	 * 
	 * @param list
	 *            合同BOM
	 */
	public void saveContractBom(List list) {
		for (int i = 0; i < list.size(); i++) {
			this.saveOrUpdate(list.get(i));
		}
	}

	// /**
	// * 根据合同料件更新合同BOM
	// *
	// * @param img
	// * 合同料件
	// */
	// public void updateContractBomImgSeqNum(ContractImg img) {
	// String hql = "update ContractBom set contractImgSeqNum=? where
	// contractImgId=?";
	// this.batchUpdateOrDelete(hql, new Object[] { img.getSeqNum(),
	// img.getId() });
	// }

	/**
	 * 批量删除合同BOM
	 * 
	 * @param list
	 *            合同Bom
	 */
	public void deleteContractBom(List list) {
		this.deleteAll(list);
	}

	/**
	 * 查找所有的合同料件
	 * 
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractImg() {
		return this.find("select a from ContractImg a where a.company= ? "
				+ " order by a.seqNum ",
				new Object[] { CommonUtils.getCompany() });
	}

	/**
	 * 查找所有的合同料件来自不在Bom中存在的
	 * 
	 * @param contractExg
	 *            合同成品
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractImgNoInContractBom(ContractExg contractExg) {
		return this.find(
				"select a from ContractImg a where a.company= ?  and a.contract.id = ? "
						+ "   and a.seqNum not in ("
						+ "   select b.contractImgSeqNum from ContractBom b "
						+ "   where b.company = ? and b.contractExg.id = ? )"
						+ "  order by a.seqNum ",
				new Object[] { CommonUtils.getCompany(),
						contractExg.getContract().getId(),
						CommonUtils.getCompany(), contractExg.getId() });
	}

	/**
	 * 查找合同料件 来自 合同备案表头Id
	 * 
	 * @param parentId
	 *            合同备案表头Id
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractImgByContractId(String parentId) {
		return this.find(
				"select a from ContractImg a where a.contract.id = ? and a.company.id=?"
						+ " order by a.seqNum ", new Object[] { parentId,
						CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找合同料件+料件备案资料库归并序号 来自 合同备案表头Id
	 * 
	 * @param parentId
	 *            合同备案表头Id
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractImgAndSeqNoByContractId(String parentId) {
		return this
				.find("select a, b.innerMergeSeqNum from ContractImg a left join a.contract left join fetch a.complex,"
						+ "BcsDictPorImg b left join b.dictPorHead where a.contract.id = ? and a.company.id=?"
						+ " and a.contract.corrEmsNo=b.dictPorHead.dictPorEmsNo  and a.credenceNo=b.seqNum "
						+ " and b.dictPorHead.declareState=? order by a.seqNum ",
						new Object[] { parentId,
								CommonUtils.getCompany().getId(),
								DeclareState.PROCESS_EXE });
	}

	/**
	 * 通过合同号查找合同料件
	 * 
	 * @param parentId
	 *            合同备案表头Id
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractImgByContractExpContractNo(String expContractNo) {
		return this
				.find("select a from ContractImg a where a.contract.expContractNo = ? and a.company.id=?"
						+ " order by a.seqNum ", new Object[] { expContractNo,
						CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找合同成品 来自 合同表头Id,修改标志以外的
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同表头Id
	 * @param modifyMark
	 *            修改标志
	 * @return List 是ContractExg型，合同成品
	 */
	public List findContractImgByContractIdByModifyMark(Request request,
			String parentId) {
		String hsql = "select a from ContractImg a where a.contract.id = ?  and a.company.id=? and a.modifyMark!=0"
				+ " order by a.seqNum ";
		return this.find(hsql, new Object[] { parentId,
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找国内购买料件
	 * 
	 * @param parentId
	 * @return
	 */
	public List findContractDomesticPurchaseBill(String parentId) {
		return this
				.find("select a.contractImgSeqNum,b.name,b.unit.name, sum("
						+ "(case when a.unitDosage is null then 0.0 else a.unitDosage end)*"
						+ "(case when a.contractExg.exportAmount is null then 0.0 else a.contractExg.exportAmount end)),"
						+ "sum((case when a.unitWeight is null then 0.0 else a.unitWeight end)*"
						+ "(case when a.contractExg.exportAmount is null then 0.0 else a.contractExg.exportAmount end)),"
						+ "sum((case when a.unitDosage is null then 0.0 else a.unitDosage end)*"
						+ "(case when a.unitWeight is null then 0.0 else a.unitWeight end)),"
						+ "sum((case when b.amount is null then 0.0 else b.amount end)*"
						+ "(case when b.declarePrice is null then 0.0 else b.declarePrice end))"
						+ "from ContractBom a ,ContractImg b "
						+ "where a.contractExg.contract.id=b.contract.id "
						+ "and a.contractExg.contract.id=? "
						+ "and a.contractImgSeqNum=b.seqNum "
						+ "and (b.name like ? or b.spec like ? )"
						+ " group by a.contractImgSeqNum,b.name,b.unit.name",
						new Object[] { parentId, "%内购%", "%内购%" });
	}

	/**
	 * 查找合同料件 来自 合同表头ID,修改状态为正在变更的
	 * 
	 * @param parentId
	 *            合同表头Id
	 * @param declareStateCHANGING_EXE
	 *            合同状态
	 * @return List 是ContractImg型，合同料件
	 */

	public List findContractImgBy(String parentId,
			Boolean declareStateCHANGING_EXE, Boolean declareStateWAIT_EAA) {
		String hsql = "select a from ContractImg a where a.contract.id = ? and a.company.id= ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(parentId);
		parameters.add(CommonUtils.getCompany().getId());
		if (declareStateCHANGING_EXE) {
			hsql += " and a.modifyMark<>? ";
			parameters.add(ModifyMarkState.UNCHANGE);
		}
		if (declareStateWAIT_EAA) {
			hsql += " and ( a.modifyMark=? ";
			parameters.add(ModifyMarkState.MODIFIED);
			hsql += " or a.modifyMark=? )";
			parameters.add(ModifyMarkState.ADDED);
		}
		hsql += " order by a.seqNum ";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找合同料件 来自 合同表头ID,修改状态为正在变更的
	 * 
	 * @param parentId
	 *            合同表头Id
	 * @param declareStateCHANGING_EXE
	 *            合同状态
	 * @return List 是ContractImg型，合同料件
	 */

	public List findContractExgBy(String parentId,
			Boolean declareStateCHANGING_EXE, Boolean declareStateWAIT_EAA) {
		String hsql = "select a from ContractExg a where a.contract.id = ?  and a.company.id=? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(parentId);
		parameters.add(CommonUtils.getCompany().getId());
		if (declareStateCHANGING_EXE) {
			hsql += " and a.modifyMark<>? ";
			parameters.add(ModifyMarkState.UNCHANGE);
		}
		if (declareStateWAIT_EAA) {
			hsql += " and ( a.modifyMark=? ";
			parameters.add(ModifyMarkState.MODIFIED);
			hsql += " or a.modifyMark=? )";
			parameters.add(ModifyMarkState.ADDED);
		}
		hsql += " order by a.seqNum ";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 根据修改状态查询合同料件
	 * 
	 * @param contract
	 * @param modifyMark
	 * @return
	 */
	public List findContractImgByModifyMark(Contract contract, String modifyMark) {
		return this.find("select a from ContractImg a where a.contract.id = ? "
				+ " and a.modifyMark=? order by a.seqNum ", new Object[] {
				contract.getId(), modifyMark });
	}

	/**
	 * 查询合同的最大序号，除去新增状态的料件
	 * 
	 * @param contract
	 * @return
	 */
	public int findMaxContractImgSeqNumExceptAdd(Contract contract) {
		List list = this.find(
				"select max(a.seqNum) from ContractImg a where a.contract.id = ? "
						+ " and a.modifyMark<>? ",
				new Object[] { contract.getId(), ModifyMarkState.ADDED });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 根据合同合同合同ID，查询合同料件
	 * 
	 * @param contract
	 * @return
	 */

	public List findContractImgByContractIdChange(String parentId) {
		return this.find(
				"select a from ContractImg a where a.contract.id = ?  "
						+ " order by a.seqNum ", new Object[] { parentId, });
	}

	/**
	 * 根据合同ID，查询正在执行的合同
	 * 
	 * @param parentId
	 * @return
	 */
	public String findExingContractByChangeId(String parentId) {
		String rst = null;
		List rlit = this
				.find(" select c.id  from   Contract c  where c.declareState= ?"
						+ " and c.emsNo in ( select a.emsNo from Contract  a where a.id = ? ) ",
						new Object[] { DeclareState.PROCESS_EXE, parentId, });
		if (rlit.size() > 0) {
			rst = rlit.get(0).toString();
		}
		return rst;
	}

	/**
	 * 查找合同料件 来自 合同备案表头Id
	 * 
	 * @param parentId
	 *            合同备案表头Id
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractImgCredenceNo(String parentId) {
		return this
				.find("select a.credenceNo from ContractImg a where a.contract.id = ? ",
						new Object[] { parentId });
	}

	/**
	 * 查找合同BOM 来自 合同备案表头Id
	 * 
	 * @param parentId
	 *            合同备案表头Id
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractBomCredenceNo(String exgId) {
		return this.find("select a.imgCredenceNo from ContractBom a"
				+ " where a.contractExg.id = ? ", new Object[] { exgId });
	}

	/**
	 * 查找合同料件 来自 合同备案表头Id
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractImgStateChanged(String contractId) {
		return this.find("select a from ContractImg a where a.contract.id = ? "
				+ " and a.modifyMark<>? order by a.seqNum ", new Object[] {
				contractId, ModifyMarkState.UNCHANGE });
	}

	/**
	 * 查找合同料件 来自 合同备案表头Id
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractImgExceptDeleted(String contractId) {
		return this.find("select a from ContractImg a where a.contract.id = ? "
				+ " and a.modifyMark<>? order by a.seqNum ", new Object[] {
				contractId, ModifyMarkState.DELETED });
	}

	/**
	 * 查找合同料件 来自 合同备案表头Id
	 * 
	 * @param parentId
	 *            合同备案表头Id
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractImgByParentIdInBom(String parentId,
			List<ContractExg> contractExgList) {
		if (contractExgList == null || contractExgList.size() <= 0) {
			return new ArrayList();
		}

		String hsql = "select a from ContractImg a where a.contract.id = ? "
				+ " and a.seqNum in (select b.contractImgSeqNum from ContractBom as b ";

		List<Object> parameters = new ArrayList<Object>();
		parameters.add(parentId);
		for (int i = 0; i < contractExgList.size(); i++) {
			ContractExg c = contractExgList.get(i);
			if (i == 0) {
				hsql += " where b.contractExg.id = ? ";
			} else {
				hsql += " or b.contractExg.id = ? ";
			}
			parameters.add(c.getId());
		}
		hsql += ") order by a.seqNum ";

		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找合同料件 来自 合同备案表头Id
	 * 
	 * @param parentId
	 *            合同备案表头Id
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractImgByParentIdComplex(String contractId,
			String complexCode) {
		String hsql = "select a from ContractImg a where a.contract.id = ? "
				+ " and a.complex.code=? order by a.seqNum";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(contractId);
		parameters.add(complexCode);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找合同料件 来自 帐册编号
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractImgByEmsNo(String emsNo) {
		return this
				.find("select a from ContractImg a where a.contract.copEmsNo = ? "
						+ " and ( a.contract.isCancel=? ) and (a.contract.declareState=? or a.contract.declareState=?) "
						+ " and a.contract.company.id=? "
						+ " order by a.seqNum ", new Object[] { emsNo, false,
						DeclareState.CHANGING_EXE, DeclareState.APPLY_POR,
						CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找合同料件 来自 帐册编号
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractImgByEmsNoAndSeqNum(String emsNo, Integer seqNum) {
		return this
				.find("select a from ContractImg a where a.contract.copEmsNo = ? "
						+ " and ( a.contract.isCancel=? ) and (a.contract.declareState=? or a.contract.declareState=?) "
						+ " and a.contract.company.id=? and a.seqNum=? "
						+ " order by a.seqNum ", new Object[] { emsNo, false,
						DeclareState.CHANGING_EXE, DeclareState.APPLY_POR,
						CommonUtils.getCompany().getId(), seqNum });
	}

	/**
	 * 查找合同料件 来自 合同备案表头Id
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @param seqNum
	 *            料件序号
	 * @return List 是ContractImg型，合同料件
	 */
	public ContractImg findContractImg(String contractId, String seqNum) {
		List list = this.find(
				"select a from ContractImg a where a.contract.id = ? "
						+ " and a.seqNum=?",
				new Object[] { contractId, Integer.valueOf(seqNum) });
		if (list.size() <= 0) {
			return null;
		} else {
			return (ContractImg) list.get(0);
		}
	}

	/**
	 * 查找合同料件 来自 合同备案表头Id
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @param seqNum
	 *            料件序号
	 * @return List 是ContractImg型，合同料件
	 */
	public List<ContractImg> findContractImgs(String contractId,
			List<Integer> seqNums) {
		List returnList = new ArrayList();
		String hql = "select a from ContractImg a where a.contract.id = '"
				+ contractId + "'";
		if (seqNums.size() > 0) {
			for (int i = 0; i < seqNums.size(); i += 1000) {
				int l = seqNums.size() > 1000 ? i + 1000 : seqNums.size();
				String addHql = "and a.seqNum in ("
						+ StringUtils
								.join(seqNums.subList(i, l).toArray(), ",")
						+ ")";
				returnList.addAll(this.find(hql + addHql));
			}
		}
		return returnList;
	}

	/**
	 * 查找合同料件 来自 合同备案表头Id,和唯一的料件备案资料库序号
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @param seqNum
	 *            料件序号
	 * @return List 是ContractImg型，合同料件
	 */
	public ContractImg findImgCredenceNo(String contractId, String credenceNo) {
		List list = this.find(
				"select a from ContractImg a where a.contract.id = ? "
						+ " and a.credenceNo=?", new Object[] { contractId,
						Integer.valueOf(credenceNo) });
		if (list.size() <= 0) {
			return null;
		} else {
			return (ContractImg) list.get(0);
		}
	}

	/**
	 * 查找合同料件 来自帐册编号、料件序号
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @param seqNum
	 *            料件序号
	 * @return List 是ContractImg型，合同料件
	 */
	public ContractImg findContractImgByEmsNoSeqNum(String emsNo, Integer seqNum) {
		List list = this.find(
				"select a from ContractImg a where a.contract.emsNo = ? "
						+ " and a.contract.declareState=?"
						+ " and a.seqNum=? and a.contract.company.id=? ",
				new Object[] { emsNo, DeclareState.PROCESS_EXE,
						Integer.valueOf(seqNum),
						CommonUtils.getCompany().getId() });
		if (list.size() <= 0) {
			return null;
		} else {
			return (ContractImg) list.get(0);
		}
	}

	/**
	 * 抓取关封数量
	 * 
	 * @param emsNo
	 *            手册号
	 * @param isMaterial
	 *            料件判断，true位料件，否则为成品
	 * @param complexCode
	 *            商品编码
	 * @param customsEnvelopBillNo
	 *            关封号
	 * @return 关封数量
	 */
	public double getCustomsNum(String emsNo, boolean isMaterial,
			Integer seqNum, String customsEnvelopBillNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "";

		hsql = "select sum(a.ownerQuantity)"
				+ " from CustomsEnvelopCommodityInfo a "
				+ "where a.company.id=? and a.emsNo=? "
				+ "and a.customsEnvelopBill.isImpExpGoods=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		parameters.add(isMaterial);// 进货是出货
		hsql += " and a.customsEnvelopBill.isAvailability=? ";
		parameters.add(true);
		hsql += " and a.seqNum = ?";
		parameters.add(seqNum);
		if (CommonUtils.notEmpty(customsEnvelopBillNo)) {
			parameters.add(customsEnvelopBillNo);
			hsql += " and a.customsEnvelopBill.customsEnvelopBillNo = ?";
		}
		hsql += " group by a.seqNum ";
		List list = this.find(hsql, parameters.toArray());
		if (list.size() < 1 || list.get(0) == null) {
			return 0;
		} else {
			return Double.parseDouble(list.get(0).toString());
		}
	}

	/**
	 * 查询商品转厂数量
	 * 
	 * @param isImport
	 *            是否是料件
	 * @param seqNum
	 *            商品序号
	 * @param emsNo
	 * @param projectType
	 * @param customsEnvelopBillNo
	 *            关封号
	 * @return 符合条件的进出口报关明细
	 */
	public double getTransferNum(boolean isImport, Integer seqNum,
			String emsNo, int projectType, String customsEnvelopBillNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = null;
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select sum(a.commAmount) from CustomsDeclarationCommInfo a ";
			break;
		case ProjectType.BCS:
			hql = "select sum(a.commAmount) from BcsCustomsDeclarationCommInfo a  ";
			break;
		case ProjectType.DZSC:
			hql = "select sum(a.commAmount) from DzscCustomsDeclarationCommInfo a  ";
			break;
		default:
			hql = "select sum(a.commAmount) from CustomsDeclarationCommInfo a ";
			break;
		}
		hql += "where a.baseCustomsDeclaration.effective = true "
				+ "and a.baseCustomsDeclaration.impExpType = ?";
		if (isImport) {
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
		} else {
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		}
		if (CommonUtils.notEmpty(customsEnvelopBillNo)) {
			parameters.add(customsEnvelopBillNo);
			hql += " and a.baseCustomsDeclaration.customsEnvelopBillNo = ?";
		}

		hql += " and a.baseCustomsDeclaration.emsHeadH2k = ?";
		parameters.add(emsNo);
		hql += " and a.commSerialNo = ?";
		parameters.add(seqNum);
		hql += " group by a.commSerialNo ";
		List list = this.find(hql, parameters.toArray());
		if (list.size() < 1 || list.get(0) == null) {
			return 0;
		} else {
			return Double.parseDouble(list.get(0).toString());
		}
	}

	/**
	 * 查询报关单申报数据
	 * 
	 * @param
	 */
	public List findCustomsBrokerList(Date beginDate, Date endDate,
			String customsbrokerName) {
		List<Object> paramereas = new ArrayList<Object>();
		String sql = "";
		sql = "select a from CustomsBrokerDetail a where a.company.id = ?";
		paramereas.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			sql += " and  a.declarationDate >= ? ";
			paramereas.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			sql += " and a.declarationDate <= ? ";
			paramereas.add(CommonUtils.getEndDate(endDate));
		}
		if (customsbrokerName != null) {
			sql += " and a.customsBrokerCode = ?";
			paramereas.add(customsbrokerName);
		}

		List list = this.find(sql, paramereas.toArray());
		return list;
	}

	/**
	 * 保存合同料件
	 * 
	 * @param contractImg
	 *            合同料件
	 */
	public void saveContractImg(ContractImg contractImg) {
		this.saveOrUpdate(contractImg);
	}

	/**
	 * 删除合同料件
	 * 
	 * @param contractImg
	 *            合同料件
	 */
	public void deleteContractImg(ContractImg contractImg) {
		this.delete(contractImg);
	}

	/**
	 * 批量保存合同料件
	 * 
	 * @param list
	 *            合同料件
	 */
	public void saveContractImg(List list) {
		for (int i = 0; i < list.size(); i++) {
			this.saveOrUpdate(list.get(i));
		}
	}

	/**
	 * 批量删除合同料件
	 * 
	 * @param list
	 *            合同料件
	 */
	public void deleteContractImg(List list) {
		this.deleteAll(list);
	}

	/**
	 * 查找所有的合同成品
	 * 
	 * @return List 是ContractExg型，合同成品
	 */
	public List findContractExg() {
		return this.find("select a from ContractExg a where a.company.id= ? "
				+ " order by a.seqNum ", new Object[] { CommonUtils
				.getCompany().getId() });
	}

	/**
	 * 当前合同成品记录总数
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @return int 合同成品记录总数
	 */
	public int findContractExgCount(String contractId) {
		List list = this.find(" select count(a.id) from ContractExg as a "
				+ "  where a.contract.id=? and a.modifyMark<>? ", new Object[] {
				contractId, ModifyMarkState.DELETED });
		if (list != null && !list.isEmpty()) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 当前合同成品记录总数
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @return int 合同成品记录总数
	 */
	public int findContractExgCountByModifyMark(String contractId) {
		List list = this.find(" select count(a.id) from ContractExg as a "
				+ "  where a.contract.id=? and a.modifyMark<>?", new Object[] {
				contractId, ModifyMarkState.UNCHANGE });
		if (list != null && !list.isEmpty()) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 当前合同料件记录总数
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @return int 合同料件记录总数
	 */
	public int findContractImgCount(String contractId) {
		List list = this.find(" select count(a.id) from ContractImg as a "
				+ "  where a.contract.id=? and a.modifyMark<>? ", new Object[] {
				contractId, ModifyMarkState.DELETED });
		if (list != null && !list.isEmpty()) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 当前合同料件记录总数
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @return int 合同料件记录总数
	 */
	public int findBcsDictPorImgCount(String contractId) {
		List list = this.find(" select count(a.id) from BcsDictPorImg as a "
				+ "  where a.dictPorHead.id=? and a.modifyMark<>? ",
				new Object[] { contractId, ModifyMarkState.DELETED });
		if (list != null && !list.isEmpty()) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 当前合同料件记录总数
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @return int 合同料件记录总数
	 */
	public int findBcsDictPorExgCount(String contractId) {
		List list = this.find(" select count(a.id) from BcsDictPorExg as a "
				+ "  where a.dictPorHead.id=? and a.modifyMark<>? ",
				new Object[] { contractId, ModifyMarkState.DELETED });
		if (list != null && !list.isEmpty()) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 查询合同成品分页
	 * 
	 * @param parentId
	 *            合同成品Id
	 * @param index
	 *            开始数据下标
	 * @param length
	 *            数据长度
	 * @return List 是ContractExg型，合同成品
	 */
	public List findContractExg(String parentId, int index, int length) {
		return this
				.findPageList(
						"select c from ContractExg c where c.contract.id=? order by c.seqNum ",
						parentId, index, length);
	}

	/**
	 * 查询合同成品
	 * 
	 * @param parentId
	 *            合同成品Id
	 * 
	 * @return List 是ContractExg型，合同成品
	 */
	public List findContractExg(String parentId) {
		return this
				.find("select c from ContractExg c where c.contract.id=? order by c.seqNum ",
						parentId);
	}

	/**
	 * 查询合同BOM已经修改的成品分页
	 * 
	 * @param parentId
	 *            合同成品Id
	 * @param index
	 *            开始数据下标
	 * @param length
	 *            数据长度
	 * @return List 是ContractExg型，合同成品
	 */
	public List findContractChangedExg(String parentId, int index, int length) {
		return this
				.findPageList(
						"select distinct c.contractExg from ContractBom as c"
								+ " where c.modifyMark<>0"
								+ " and c.contractExg.contract.id=? order by c.contractExg.seqNum",
						parentId, index, length);
	}

	/**
	 * 查询合同除未修改的成品分页
	 * 
	 * @param parentId
	 *            合同成品Id
	 * @param index
	 *            开始数据下标
	 * @param length
	 *            数据长度
	 * @return List 是ContractExg型，合同成品
	 */
	public List findContractExgByModifyMark(String parentId, int index,
			int length) {
		return this
				.findPageList(
						"select c from ContractExg c where c.contract.id=? and c.modifyMark!=0 order by c.seqNum ",
						parentId, index, length);
	}

	/**
	 * 查询合同成品分页
	 * 
	 * @param parentId
	 *            合同成品Id
	 * @param index
	 *            开始数据下标
	 * @param length
	 *            数据长度
	 * @return List 是ContractExg型，合同成品
	 */
	public List findContractExgChange(String parentId, int index, int length) {
		return this
				.findPageList(
						"select c from ContractExg c where c.contract.id=?      order by c.seqNum  ",
						new Object[] { parentId }, index, length);
	}

	/**
	 * 查询料件对应的成品单耗
	 * 
	 * @param parentId
	 * @return
	 */
	public List findContractBomByContractId(String parentId) {
		if (parentId == null) {
			return new ArrayList();
		}
		return this
				.find("select a from ContractBom a left join a.contractExg   b "
						+ "  left join b.contract c  where c.id=?  order by b.seqNum asc,a.contractImgSeqNum asc",
						new Object[] { parentId });
	}

	/**
	 * 根据修改状态查询合同成品
	 * 
	 * @param contract
	 * @param modifyMark
	 * @return
	 */
	public List findContractExgByModifyMark(Contract contract, String modifyMark) {
		return this.find("select a from ContractExg a where a.contract.id = ? "
				+ " and a.modifyMark=? order by a.seqNum ", new Object[] {
				contract.getId(), modifyMark });
	}

	/**
	 * 查询合同的最大序号，除去新增状态的成品
	 * 
	 * @param contract
	 * @return
	 */
	public int findMaxContractExgSeqNumExceptAdd(Contract contract) {
		List list = this.find(
				"select max(a.seqNum) from ContractExg a where a.contract.id = ? "
						+ " and a.modifyMark<>? ",
				new Object[] { contract.getId(), ModifyMarkState.ADDED });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 查询合同成品 来自合同备案表头Id、成品序号
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @param seqNum
	 *            成品序号
	 * @return List 是ContractExg型，合同成品
	 */
	public ContractExg findContractExg(String contractId, String seqNum) {
		List list = this.find(
				"select c from ContractExg c where c.contract.id=? "
						+ " and c.seqNum=?",
				new Object[] { contractId, Integer.valueOf(seqNum) });
		if (list.size() <= 0) {
			return null;
		} else {
			return (ContractExg) list.get(0);
		}
	}

	/**
	 * 查询合同成品 来自帐册编号、成品序号
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @param seqNum
	 *            成品序号
	 * @return List 是ContractExg型，合同成品
	 */
	public ContractExg findContractExgByEmsNoSeqNum(String emsNo, Integer seqNum) {
		List list = this.find(
				"select c from ContractExg c where c.contract.emsNo=? "
						+ " and c.contract.declareState=? "
						+ " and c.seqNum=? and c.contract.company.id=? ",
				new Object[] { emsNo, DeclareState.PROCESS_EXE,
						Integer.valueOf(seqNum),
						CommonUtils.getCompany().getId() });
		if (list.size() <= 0) {
			return null;
		} else {
			return (ContractExg) list.get(0);
		}
	}

	/**
	 * 根据合同成品Id查询合同成品
	 * 
	 * @param id
	 *            合同成品Id
	 * @return List 是ContractExg型，合同成品
	 */
	public ContractExg findContractExgById(String id) {
		List list = this.find("select c from ContractExg c where c.id=? ",
				new Object[] { id });
		if (list.size() <= 0) {
			return null;
		} else {
			return (ContractExg) list.get(0);
		}
	}

	/**
	 * 查找 合同 成品
	 * 
	 * @param contractId
	 *            合同ID
	 * @param corrEmsNo
	 *            备案资料编码
	 * @param seqNum
	 *            备案序号/成品序号
	 * @return
	 */
	public ContractExg findContractExgByContractIdAndSeqnumAndCorrnum(
			String contractId, String corrEmsNo, String seqNum) {

		String hql = "select a from ContractExg a where a.contract.id = ? "
				+ " and a.company.id=? and a.seqNum = ? "
				+ " and a.contract.corrEmsNo=?";

		Object[] params = { contractId, CommonUtils.getCompany().getId(),
				Integer.valueOf(seqNum), corrEmsNo };

		List list = find(hql, params);

		if (list.size() <= 0) {

			return null;

		} else {

			return (ContractExg) list.get(0);
		}

	}

	/**
	 * 查询合同成品根据开始序号和结束序号
	 * 
	 * @param parentId
	 *            合同备案表头Id
	 * @param beginSeqNum
	 *            开始序号
	 * @param endSeqNum
	 *            结束序号
	 * @return List 是ContractExg型，合同成品
	 */
	public List findContractExgBeginAndEndSeqNum(String parentId,
			int beginSeqNum, int endSeqNum) {
		return this.find("select c from ContractExg c where c.contract.id=? "
				+ " and c.seqNum>=? and c.seqNum<=? order by c.seqNum ",
				new Object[] { parentId, beginSeqNum, endSeqNum });
	}

	/**
	 * 查询合同料件根据开始序号和结束序号
	 * 
	 * @param parentId
	 *            合同备案表头Id
	 * @param beginSeqNum
	 *            开始序号
	 * @param endSeqNum
	 *            结束序号
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractImgBeginAndEndSeqNum(String parentId,
			int beginSeqNum, int endSeqNum) {
		return this.find("select c from ContractImg c where c.contract.id=? "
				+ " and c.seqNum>=? and c.seqNum<=? order by c.seqNum ",
				new Object[] { parentId, beginSeqNum, endSeqNum });
	}

	/**
	 * 保存合同成品
	 * 
	 * @param contractExg
	 *            合同成品
	 */
	public void saveContractExg(ContractExg contractExg) {
		this.saveOrUpdate(contractExg);
	}

	/**
	 * 查找合同成品 来自 合同备案表头Id
	 * 
	 * @param parentId
	 *            合同备案表头Id
	 * @return List 是ContractExg型，合同成品
	 */
	public List findContractExgByParentId(String parentId) {
		String hsql = "select a from ContractExg a where a.contract.id = ?  and a.company.id=?"
				+ " order by a.seqNum ";

		return this.find(hsql, new Object[] { parentId,
				CommonUtils.getCompany().getId() });

	}

	/**
	 * 查找合同BOM 来自 合同成品ID
	 * 
	 * @param exgId
	 *            合同成品Id
	 * @return List 是ContractBom型，合同BOM
	 */
	public List findContractBomByContractId(Contract contractId) {
		if (null == contractId) {
			return Collections.emptyList();
		}

		return this.find("select a from ContractBom a "
				+ " where a.contractExg.contract.id = ?  "
				+ "  order by a.createDate ",
				new Object[] { contractId.getId() });
	}

	/**
	 * 查找合同成品+成品备案资料库归并序号 来自 合同备案表头Id
	 * 
	 * @param parentId
	 *            合同备案表头Id
	 * @return List 是ContractExg型，合同成品
	 */
	public List findContractExgSeqNoByParentId(String parentId) {
		return this
				.find("select a, b.innerMergeSeqNum from ContractExg a,BcsDictPorExg b where a.contract.id = ? and a.company.id=?"
						+ " and a.contract.corrEmsNo=b.dictPorHead.dictPorEmsNo  and a.credenceNo=b.seqNum "
						+ " and b.dictPorHead.declareState=? order by a.seqNum ",
						new Object[] { parentId,
								CommonUtils.getCompany().getId(),
								DeclareState.PROCESS_EXE });

	}

	/**
	 * 通过出口合同号查找合同成品
	 * 
	 * @param expContractNo
	 *            出口合同号
	 * @return List 是ContractExg型，合同成品
	 * @author sxk
	 */
	public List findContractExgByExpContractNo(String expContractNo) {
		String hsql = "select a from ContractExg a where a.contract.expContractNo = ?  and a.company.id=? "
				+ " and a.contract.declareState=? order by a.seqNum ";
		return this.find(hsql, new Object[] { expContractNo,
				CommonUtils.getCompany().getId(), DeclareState.PROCESS_EXE });

	}

	/**
	 * 查找合同成品 来自 合同表头Id,修改标志以外的
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同表头Id
	 * @param modifyMark
	 *            修改标志
	 * @return List 是ContractExg型，合同成品
	 */
	public List findContractExgByParentIdByModifyMark(Request request,
			String parentId) {
		String hsql = "select a from ContractExg a where a.contract.id = ?  and a.company.id=? and a.modifyMark!=0"
				+ " order by a.seqNum ";
		return this.find(hsql, new Object[] { parentId,
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找合同成品 来自 合同备案表头Id
	 * 
	 * @param parentId
	 *            合同备案表头Id
	 * @return List 是ContractExg型，合同成品
	 */
	public List findContractExgCredenceNo(String contractId) {
		return this.find("select a.credenceNo from ContractExg a "
				+ " where a.contract.id = ?  and a.company.id=? ",
				new Object[] { contractId, CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找合同成品 来自 合同备案表头Id
	 * 
	 * @param parentId
	 *            合同备案表头Id
	 * @return List 是ContractExg型，合同成品
	 */
	public List findContractExgStateChanged(String parentId) {
		return this.find("select a from ContractExg a where a.contract.id = ? "
				+ " and a.modifyMark<>? and a.company.id=? "
				+ " order by a.seqNum ", new Object[] { parentId,
				ModifyMarkState.UNCHANGE, CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找合同料件 来自 帐册编号
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @return List 是ContractExg型，合同成品
	 */
	public List findContractExgByEmsNo(String emsNo) {
		return this
				.find("select a from ContractExg a where a.contract.emsNo = ? "
						+ " and ( a.contract.isCancel=? ) and a.declareState=? "
						+ " and a.contract.company.id=? "
						+ " order by a.seqNum ", new Object[] { emsNo, false,
						DeclareState.PROCESS_EXE,
						CommonUtils.getCompany().getId() });
	}

	/**
	 * 批量保存合同成品
	 * 
	 * @param list
	 *            合同成品
	 */
	public void saveContractExg(List list) {
		for (int i = 0; i < list.size(); i++) {
			this.saveOrUpdate(list.get(i));
		}
	}

	/**
	 * 批量删除合同成品
	 * 
	 * @param list
	 *            合同成品
	 */
	public void deleteContractExg(List list) {
		this.deleteAll(list);
	}

	/**
	 * 批量删除合同成品
	 * 
	 * @param list
	 *            合同成品
	 */
	public void deleteContractExg(ContractExg exg) {
		this.delete(exg);
	}

	/**
	 * 查找已审核的凭证成品
	 * 
	 * @return List
	 */
	public List findIsAuditingCredenceExg() {
		return this
				.find(" select a from Credence a where a.company= ? and a.isAuditing = ? and a.materielType=? ",
						new Object[] { CommonUtils.getCompany(),
								Boolean.valueOf(true),
								MaterielType.FINISHED_PRODUCT, });
	}

	/**
	 * 查找已审核所有的凭证料件
	 * 
	 * @return List
	 */
	public List findIsAuditingCredenceImg() {
		return this
				.find("select a  from Credence a where a.company= ? and a.isAuditing = ? and (a.materielType=? or a.materielType=?)",
						new Object[] { CommonUtils.getCompany(),
								Boolean.valueOf(true), MaterielType.MATERIEL,
								MaterielType.ASSISTANT_MATERIAL });
	}

	/**
	 * 获得最大的成品序号来自当前合同
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @return int 最大的成品序号
	 */
	public int getMaxContractExgSeqNum(String contractId) {
		List list = this.find("select max(a.seqNum) from ContractExg as a "
				+ " where a.company.id=? and a.contract.id = ? ", new Object[] {
				CommonUtils.getCompany().getId(), contractId });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 获得最大的Bom序号来自当前成品
	 * 
	 * @param contractExgId
	 *            合同成品Id
	 * @return int 最大的Bom序号
	 */
	public int getMaxContractBomSeqNum(String contractExgId) {
		List list = this
				.find("select max(a.seqNum) from ContractBom as a "
						+ " where a.company.id=? "
						+ " and a.contractExg.id = ? ", new Object[] {
						CommonUtils.getCompany().getId(), contractExgId });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 查询单耗中某一料件的总数量
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @param contractImgSeqNum
	 *            料件总表序号
	 * @return List 是int型，单耗中某一料件的总数量
	 */
	public Double findContractBomImgUsedAmount(String contractId,
			Integer contractImgSeqNum) {
		List list = this
				.find("select sum(a.contractExg.exportAmount*(a.unitWaste/(1-a.waste/100.0))) from ContractBom as a "
						+ " where a.company.id=? "
						+ " and a.contractExg.contract.id = ? "
						+ " and a.contractImgSeqNum=? and a.modifyMark<>? ",
						new Object[] { CommonUtils.getCompany().getId(),
								contractId, contractImgSeqNum,
								ModifyMarkState.DELETED });
		if (list.size() > 0 && list.get(0) != null) {
			return Double.parseDouble(list.get(0).toString());
		}
		return 0.0;
	}

	/**
	 * 查询单耗中某一料件的总数量
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @param contractImgSeqNum
	 *            料件总表序号
	 * @return List 是int型，单耗中某一料件的总数量
	 */
	public List findContractBomImgUsedAmounts(String contractId,
			List<Integer> contractImgSeqNums) {
		List list = new ArrayList();
		String hql = "select a.contractImgSeqNum,sum(a.contractExg.exportAmount*(a.unitWaste/(1-a.waste/100.0))) "
				+ " from ContractBom as a where a.company.id='"
				+ CommonUtils.getCompany().getId()
				+ "'"
				+ " and a.contractExg.contract.id = '"
				+ contractId
				+ "' and a.modifyMark<>2 ";
		for (int i = 0; i < contractImgSeqNums.size(); i += 1000) {
			int l = contractImgSeqNums.size() > 1000 ? i + 1000
					: contractImgSeqNums.size();
			String addHql = " and a.contractImgSeqNum in ("
					+ StringUtils.join(contractImgSeqNums.subList(i, l)
							.toArray(), ",")
					+ ") group by a.contractImgSeqNum ";
			list.addAll(this.find(hql + addHql));
		}
		return list;
	}

	/**
	 * 获得最大的料件表序号来自当前合同
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @return int 最大的料件总表序号
	 */
	public int getMaxContractImgSeqNum(String contractId) {
		List list = this.find("select max(a.seqNum) from  ContractImg as a "
				+ " where a.company.id=? and a.contract.id = ? ", new Object[] {
				CommonUtils.getCompany().getId(), contractId });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 获得最大的单耗总表序号来自当前合同
	 * 
	 * @param contractId
	 * @return int 最大的单耗总表序号
	 */
	public int getMaxImgSeqNum(String contractId) {
		List list = this
				.find("select max(a.contractImgSeqNum) from  ContractBom as a "
						+ " where a.company.id=? and a.contractExg.contract.id = ? ",
						new Object[] { CommonUtils.getCompany().getId(),
								contractId });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 返回当前合同料件单耗表记录号
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @return Integer 当前合同记录号
	 */
	public List getContractImgSeqNum(String contractId) {
		return this.find("select a.imgCredenceNo from ContractBom as a "
				+ " where a.company.id=? and a.contractExg.contract.id = ? ",
				new Object[] { CommonUtils.getCompany().getId(), contractId });

	}

	/**
	 * 返回当前合同料件单耗表料件【对应】表总表序号
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @return Integer 当前合同记录号
	 */
	public Integer getBomCredenceNo(String contractId, Integer ImgCredenceNo) {
		List list = this
				.find("select b.seqNum from ContractImg as b"
						+ " where  b.company.id =? and b.contract.id =? and  b.credenceNo = ? ",
						new Object[] { CommonUtils.getCompany().getId(),
								contractId, ImgCredenceNo });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 获得合同的进口总金额
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @return Double 合同的进口总金额
	 */
	public Double findContractImgMoney(String contractId) {
		List list = this.find(
				"select sum(a.amount*a.declarePrice) from ContractImg as a "
						+ " where a.company.id=? and a.contract.id = ? "
						+ " and a.modifyMark<>? "
						+ " and (a.name not like ? or a.name is null) "
						+ " and (a.spec not like ? or a.spec is null) ",
				new Object[] { CommonUtils.getCompany().getId(), contractId,
						ModifyMarkState.DELETED, "%内购%", "%内购%" });
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		}
		return 0.0;
	}

	/**
	 * 获得合同的进口总金额
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @return Double 合同的进口总金额
	 */
	public Double findContractImgTotalMoney(String contractId) {
		List<Object> paramters = new ArrayList<Object>();
		String hql = "select sum(a.amount*a.declarePrice) from ContractImg as a "
				+ " where a.company.id=? and a.contract.id = ? "
				+ " and a.modifyMark<>? ";
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(contractId);
		paramters.add(ModifyMarkState.DELETED);
		List list = this.find(hql, paramters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		}
		return 0.0;
	}

	/**
	 * 获得合同出口总金额
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @return Double 合同出口总金额
	 */
	public Double findContractExgMoney(String contractId) {
		List list = this.find(
				"select sum(a.exportAmount*a.unitPrice) from ContractExg as a "
						+ " where a.company.id=? and a.contract.id = ? "
						+ " and a.modifyMark<>? "
						+ " and (a.name not like ? or a.name is null)"
						+ " and (a.spec not like ? or a.spec is null)",
				new Object[] { CommonUtils.getCompany().getId(), contractId,
						ModifyMarkState.DELETED, "%内购%", "%内购%" });
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		}
		return 0.0;
	}

	/**
	 * 查找不存在报关与物料对照表的物料来自料件类型
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param materielType
	 *            物料类别
	 * @param index
	 *            开始数据下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            对应表里的属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            property在“where”里的判断是用“like”还是“＝”，true用“like”
	 * @return List 是BcsTenInnerMerge型，报关商品资料
	 */
	public List<BcsTenInnerMerge> findBcsTenInnerMergeNotContract(
			Contract contract, String materielType, int index, int length,
			String property, Object value, Boolean isLike, boolean isInnerMerge) {

		if (isInnerMerge) {
			List<Object> paramters = new ArrayList<Object>();
			String hsql = " select distinct a.bcsTenInnerMerge from BcsInnerMerge a "
					+ " where a.bcsTenInnerMerge.company.id= ? and a.bcsTenInnerMerge.scmCoi=?  ";
			paramters.add(CommonUtils.getCompany().getId());
			paramters.add(materielType);
			if (MaterielType.MATERIEL.equals(materielType)) {
				hsql += " and a.bcsTenInnerMerge.seqNum not in"
						+ "( select c.credenceNo from ContractImg c where c.contract.id=? and c.company.id=?"
						+ " and c.credenceNo is not null ) ";
				paramters.add(contract.getId());
				paramters.add(CommonUtils.getCompany().getId());
			} else if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {
				hsql += " and a.bcsTenInnerMerge.seqNum not in"
						+ "( select c.credenceNo from ContractExg c where c.contract.id=? and c.company.id=? "
						+ " and c.credenceNo is not null) ";
				paramters.add(contract.getId());
				paramters.add(CommonUtils.getCompany().getId());
			}
			if (property != null && !"".equals(property) && value != null) {
				if (isLike) {
					hsql += " and  a.bcsTenInnerMerge." + property
							+ " like ?  ";
					paramters.add(value + "%");
				} else {
					hsql += " and  a.bcsTenInnerMerge." + property + " = ?  ";
					paramters.add(value);
				}
			}
			hsql += " order by a.bcsTenInnerMerge.seqNum asc ";
			return this.findPageList(hsql, paramters.toArray(), index, length);
		} else {
			List<Object> paramters = new ArrayList<Object>();
			String hsql = " select a from BcsTenInnerMerge a "
					+ " where a.company.id= ? and a.scmCoi=? ";
			paramters.add(CommonUtils.getCompany().getId());
			paramters.add(materielType);
			if (MaterielType.MATERIEL.equals(materielType)) {
				hsql += " and a.seqNum not in"
						+ "( select c.credenceNo from ContractImg c where c.contract.id=? and c.company.id=?"
						+ " and c.credenceNo is not null ) ";
				paramters.add(contract.getId());
				paramters.add(CommonUtils.getCompany().getId());
			} else if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {
				hsql += " and a.seqNum not in"
						+ "( select c.credenceNo from ContractExg c where c.contract.id=? and c.company.id=? "
						+ " and c.credenceNo is not null) ";
				paramters.add(contract.getId());
				paramters.add(CommonUtils.getCompany().getId());
			}
			if (property != null && !"".equals(property) && value != null) {
				if (isLike) {
					hsql += " and a." + property + " like ? ";
					paramters.add(value + "%");
				} else {
					hsql += " and a." + property + " = ? ";
					paramters.add(value);
				}
			}
			hsql += " order by a.seqNum asc ";
			return this.findPageList(hsql, paramters.toArray(), index, length);
		}
	}

	/**
	 * 查找报关与物料对照表
	 * 
	 * @param isInnerMerge
	 * @return
	 */
	public List<BcsTenInnerMerge> findBcsTenInnerMerge(boolean isInnerMerge) {
		if (isInnerMerge) {
			List<Object> paramters = new ArrayList<Object>();
			String hsql = " select distinct a.bcsTenInnerMerge from BcsInnerMerge a "
					+ " where a.bcsTenInnerMerge.company.id= ? and a.bcsTenInnerMerge.scmCoi=?  ";
			paramters.add(CommonUtils.getCompany().getId());
			paramters.add(MaterielType.MATERIEL);
			// hsql += " and a.bcsTenInnerMerge.seqNum not in"
			// + "( select c.contractImgSeqNum from ContractBom c where
			// c.contract.id=? and c.company.id=?"
			// + " and c.credenceNo is not null ) ";
			// paramters.add(contract.getId());
			// paramters.add(CommonUtils.getCompany().getId());
			hsql += " order by a.bcsTenInnerMerge.seqNum asc ";
			return this.find(hsql, paramters.toArray());
		} else {
			List<Object> paramters = new ArrayList<Object>();
			String hsql = " select a from BcsTenInnerMerge a "
					+ " where a.company.id= ? and a.scmCoi=? ";
			paramters.add(CommonUtils.getCompany().getId());
			paramters.add(MaterielType.MATERIEL);
			hsql += " order by a.seqNum asc ";
			return this.find(hsql, paramters.toArray());
		}
	}

	/**
	 * 只显示物料四码不为空的数据
	 * 
	 * @param type
	 *            物料类别
	 * @param firstIndex
	 *            数据开始下标
	 * @param ptNo
	 *            料号
	 * @return List 是materiel型，报关常用物料
	 */
	public List findMaterielFromInner(String type, int firstIndex, String ptNo) {
		List<Object> paramters = new ArrayList<Object>();
		String sql = "select a.materiel from BcsInnerMerge a "
				+ " where a.materielType = ? and a.company.id = ? and a.bcsTenInnerMerge.seqNum is not null";
		paramters.add(type);
		paramters.add(CommonUtils.getCompany().getId());
		if (ptNo != null) {
			sql += " and a.materiel.ptNo like '%" + ptNo + "%'";
		}
		return this.findPageList(sql, paramters.toArray(), firstIndex, 100);
	}

	/**
	 * 查找物料与报关对应表对应的报关商品资料的凭证序号
	 * 
	 * @param ptNo
	 *            料号
	 * @param type
	 *            物料类别
	 * @return Integer
	 */
	public Integer findSeqNumByPtNo(String ptNo, String type) {
		List list = this
				.find("select a.bcsTenInnerMerge.seqNum from BcsInnerMerge a "
						+ " where a.materielType = ? and a.company.id = ? and a.materiel.ptNo = ? and a.bcsTenInnerMerge.seqNum is not null",
						new Object[] { type, CommonUtils.getCompany().getId(),
								ptNo });
		if (list.get(0) != null) {
			return (Integer) list.get(0);
		}
		return null;
	}

	/**
	 * 根据序号、帐册编号查找正在执行的合同成品或合同料件
	 * 
	 * @param seqNum
	 *            序号
	 * @param type
	 *            物料类别
	 * @param emsNo
	 *            帐册编号
	 * @return List 是ContractImg或ContractExg型，合同料件或合同成品
	 */
	public List findImgExgBillBySeqNum(Integer seqNum, String type, String emsNo) {
		String obj = "";
		if (type.equals(MaterielType.MATERIEL)) {
			obj = "ContractImg";
		} else {
			obj = "ContractExg";
		}
		return this
				.find("select a from "
						+ obj
						+ " a where a.contract.emsNo=? "
						+ " and a.contract.company.id=? and a.credenceNo = ? and a.contract.declareState=? ",
						new Object[] { emsNo, CommonUtils.getCompany().getId(),
								seqNum, DeclareState.PROCESS_EXE });
	}

	/**
	 * 保存回执资料
	 * 
	 * @param data
	 *            回执资料
	 */
	public void saveProcessedContractData(ProcessedContractData data) {
		this.saveOrUpdate(data);
	}

	/**
	 * 删除回执资料
	 * 
	 * @param data
	 *            回执资料
	 */
	public void deleteProcessedContractData(ProcessedContractData data) {
		this.delete(data);
	}

	/**
	 * 查找回执资料
	 * 
	 * @return List 是ProcessedContractData型，回执资料
	 */
	public List findProcessedContractData() {
		return this.find("select a from ProcessedContractData as a "
				+ " where a.company.id=?  ", new Object[] { CommonUtils
				.getCompany().getId() });
	}

	/**
	 * 查找回执资料的文件名称
	 * 
	 * @return List 是String型，文件名称
	 */
	public List findProcessedFileName() {
		return this.find("select a.fileName from ProcessedContractData as a "
				+ " where a.company.id=?  ", new Object[] { CommonUtils
				.getCompany().getId() });
	}

	/**
	 * 根据帐册编号、成品序号查找执行的合同成品
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @param seqNum
	 *            成品序号
	 * @return List 是ContractExg型，合同成品
	 */
	public List findContractExgBySeqNum(String emsNo, String seqNum) {
		return this
				.find("select a from ContractExg a where a.company.id=? and a.contract.isCancel = ? "
						+ " and a.contract.declareState=? and a.contract.emsNo = ? and a.seqNum = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								new Boolean(false), DeclareState.PROCESS_EXE,
								emsNo, Integer.valueOf(seqNum) });
	}

	/**
	 * 根据帐册编号、成品序号查找执行的合同成品
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @param seqNum
	 *            成品序号
	 * @return List 是ContractExg型，合同成品
	 */
	public List findDzscEmsExgBillBySeqNum(String emsNo, String seqNum) {
		return this
				.find("select a from DzscEmsExgBill a where a.company.id=?"
						+ " and a.dzscEmsPorHead.declareState=? and a.dzscEmsPorHead.emsNo = ? and a.seqNum = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								DzscState.EXECUTE, emsNo,
								Integer.valueOf(seqNum) });
	}

	/**
	 * 根据帐册编号、成品序号查找执行的合同成品
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @param credenceNo
	 *            归并序号
	 * @return List 是ContractExg型，合同成品
	 */
	public List findContractExgByCredenceNo(String emsNo, String credenceNo) {
		return this
				.find("select a from ContractExg a where a.company.id=? and a.contract.isCancel = ? "
						+ " and a.contract.declareState=? and a.contract.emsNo = ? and a.credenceNo = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								new Boolean(false), DeclareState.PROCESS_EXE,
								emsNo, Integer.valueOf(credenceNo) });
	}

	/**
	 * 根据帐册编号、成品序号查找执行的合同料件
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @param seqNum
	 *            料件序号
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractImgBySeqNum(String emsNo, String seqNum) {
		return this
				.find("select a from ContractImg a where a.company.id=? and a.contract.isCancel = ? "
						+ " and a.contract.declareState=? and a.contract.emsNo = ? and a.seqNum = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								new Boolean(false), DeclareState.PROCESS_EXE,
								emsNo, Integer.valueOf(seqNum) });
	}

	/**
	 * 根据帐册编号、成品序号查找执行的合同料件
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @param credenceNo
	 *            归并序号
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractImgByCredenceNo(String emsNo, String credenceNo) {
		return this
				.find("select a from ContractImg a where a.company.id=? and a.contract.isCancel = ? "
						+ " and a.contract.declareState=? and a.contract.emsNo = ? and a.credenceNo = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								new Boolean(false), DeclareState.PROCESS_EXE,
								emsNo, Integer.valueOf(credenceNo) });
	}

	/**
	 * 根据帐册编号、成品序号查找执行的合同料件
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @param credenceNo
	 *            归并序号
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractImgByCredenceNo(Contract contract,
			Integer credenceNo) {
		return this.find(
				"select a from ContractImg a where a.company.id=? and a.contract.id = ? "
						+ " and a.credenceNo = ?", new Object[] {
						CommonUtils.getCompany().getId(), contract.getId(),
						credenceNo });
	}

	/**
	 * 根据合同和备案序号抓取报关单物料资料
	 * 
	 * @param isMaterial
	 *            料件判断，true为料件
	 * @param contract
	 *            合同备案表头
	 * @param seqNum
	 *            料件序号
	 * @return List 是BcsCustomsDeclarationCommInfo型，报关单物料
	 */
	public List findBcsCustomsDeclarationCommInfo(boolean isMaterial,
			Contract contract, Integer seqNum) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from BcsCustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=?"
				+ " and a.baseCustomsDeclaration.emsHeadH2k=?  "
				+ " and a.commSerialNo=?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(contract.getEmsNo());
		parameters.add(seqNum);
		if (isMaterial) {
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
		} else {
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
			parameters.add(ImpExpType.REWORK_EXPORT);
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找所有的自用商品编码
	 * 
	 * @return List 是Complex型，自用商品编码
	 */
	public List findComplex() {
		return this
				.find("select a from Complex a where (a.isOut <> '1' or a.isOut is null) and a.company= ? ",
						new Object[] { CommonUtils.getCompany() });
	}

	/**
	 * 根据手册号和商品的备案序号查询报关单的数目
	 * 
	 * @param emsNo
	 * @param seqNum
	 * @return
	 */
	public int findBcsCustomsDeclarationCommInfoCount(String emsNo,
			Integer seqNum, boolean isImg) {
		List<Object> parameters = new ArrayList<Object>();
		String sql = "select count(a.id) from BcsCustomsDeclarationCommInfo a "
				+ " where a.baseCustomsDeclaration.emsHeadH2k=? "
				+ " and a.commSerialNo=? "
				+ " and a.baseCustomsDeclaration.company.id=? ";
		parameters.add(emsNo);
		parameters.add(seqNum);
		parameters.add(CommonUtils.getCompany().getId());
		if (isImg) {
			sql += " and a.baseCustomsDeclaration.impExpType in (?,?) ";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
		} else {
			sql += " and a.baseCustomsDeclaration.impExpType in (?,?) ";
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		}
		List list = this.find(sql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 查找所有的包装种类
	 * 
	 * @return List 是Wrap型，包装种类
	 */
	public List findWrap() {
		return this.find("select a from Wrap a ");
	}

	/**
	 * 根据商品编码，名称，规格查询合同成品
	 * 
	 * @param head
	 * @param code
	 * @param name
	 * @param spec
	 * @return
	 */
	public List findContractExgByname(Contract head, String code, String name,
			String spec) {
		List list = this.find(
				"select a from ContractExg a where a.contract=? and a.complex.code = ? and "
						+ "a.name = ? and a.spec=?",
				new Object[] { head, code.trim(), name.trim(), spec.trim() });
		return list;
	}

	/**
	 * 根据物料BOM编号查询成品耗用
	 * 
	 * @param ptno
	 * @return
	 */
	public List findBcsInnerMergeByptno(String ptno) {
		List list = this
				.find("select a.bcsTenInnerMerge from  BcsInnerMerge a where a.materiel.ptNo=? and a.company=?",
						new Object[] { ptno, CommonUtils.getCompany() });
		return list;
	}

	/**
	 * 根据商品编码，名称，规格查询合同料件
	 * 
	 * @param head
	 * @param code
	 * @param name
	 * @param spec
	 * @return
	 */
	public List findContractImgByname(Contract head, String code, String name,
			String spec) {
		List list = this.find(
				"select a from ContractImg a where a.contract=? and a.complex.code = ? and "
						+ "a.name = ? and a.spec=?",
				new Object[] { head, code.trim(), name.trim(), spec.trim() });
		return list;
	}

	/**
	 * 通过成品以及料件总表序号查询BOM
	 * 
	 * @param contractExg
	 * @param contractImgSeqNum
	 * @return
	 */
	public ContractBom findContractBom(ContractExg contractExg,
			Integer contractImgSeqNum) {
		List list = this
				.find("select a from ContractBom a where a.contractExg =? and a.contractImgSeqNum =?",
						new Object[] { contractExg, contractImgSeqNum });
		if (list.size() > 0) {
			return (ContractBom) list.get(0);
		} else {
			return null;
		}

	}

	/**
	 * 查询成品所耗用的料件的数量
	 * 
	 * @param contractExg
	 * @param contractImgSeqNum
	 * @return
	 */
	public List findContractImgAmountByExgUsed(Contract contract) {
		return this
				.find("select a.contractImgSeqNum,sum(a.contractExg.exportAmount*(a.unitWaste/(1-a.waste/100.0)))"
						+ " from ContractBom  a "
						+ " where a.contractExg.contract.id =? "
						+ " and a.modifyMark<>? "
						+ " group by a.contractImgSeqNum", new Object[] {
						contract.getId(), ModifyMarkState.DELETED });
	}

	/**
	 * 通过状态查询合同
	 * 
	 * @param declareState
	 * @return
	 */
	public List findcontract(String declareState) {
		return this
				.find("select a from Contract a  where a.declareState=? and a.company=?",
						new Object[] { declareState, CommonUtils.getCompany() });
	}

	/**
	 * 取得物料信息
	 * 
	 * @param materieltype
	 * @return
	 */

	public List findBcsInnerMerge(String materieltype) {
		List list = this
				.find("select a from BcsInnerMerge a where a.materielType =? and a.company=?",
						new Object[] { materieltype, CommonUtils.getCompany() });
		return list;
	}

	/**
	 * 查询备案资料库备案料件
	 * 
	 * @param dictPorEmsNo
	 * @return
	 */
	public List findBcsDictPorImg(String dictPorEmsNo) {
		return this
				.find("select a from BcsDictPorImg a where a.dictPorHead.dictPorEmsNo=?"
						+ " and a.dictPorHead.declareState=? and a.company= ? ",
						new Object[] { dictPorEmsNo, DeclareState.PROCESS_EXE,
								CommonUtils.getCompany() });
	}

	/**
	 * 查询BCS内部归并对应表，存放合同备案成品资料或存放合同备案料件资料
	 * 
	 * @param temp
	 * @return
	 */
	public List findBcsInnerMergeAndContractExgOrImg(List<String> materielIds,
			String emsNo, Boolean isProduct) {

		List<Object> parameters = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.bcsTenInnerMerge.seqNum,a.materiel.id,c from BcsInnerMerge a,");

		if (isProduct) {
			sb.append(" BcsDictPorExg b left join b.dictPorHead ,");
			sb.append(" ContractExg c left join c.contract");
		} else {
			sb.append(" BcsDictPorImg b left join b.dictPorHead ,");
			sb.append(" ContractImg c left join c.contract");
		}

		sb.append(" where a.bcsTenInnerMerge.seqNum=b.innerMergeSeqNum ");
		sb.append(" and b.dictPorHead.dictPorEmsNo=c.contract.corrEmsNo ");
		sb.append(" and b.seqNum=c.credenceNo ");
		sb.append(" and c.contract.emsNo=? ");
		sb.append(" and c.contract.declareState=? ");
		sb.append(" and b.dictPorHead.declareState=? ");
		sb.append(" and a.company.id=? ");

		parameters.add(emsNo);// 帐册编号
		parameters.add(DeclareState.PROCESS_EXE);// 正在执行的合同
		parameters.add(DeclareState.PROCESS_EXE);// 正在执行的备案资料库
		parameters.add(CommonUtils.getCompany().getId());

		// 一千笔数据查询一次
		List returnList = new ArrayList();
		for (int i = 0; i < materielIds.size(); i += 1000) {
			Integer maxIndex = materielIds.size() > 1000 ? i + 1000
					: materielIds.size();
			String stu = sb.toString()
					+ " and a.materiel.id in ('"
					+ StringUtils.join(materielIds.subList(i, maxIndex)
							.toArray(), "','") + "') order by a.materiel.id";
			System.out.println("================" + stu);
			returnList.addAll(this.find(stu, parameters.toArray()));
		}
		return returnList;
	}

	/**
	 * 查询备案资料库备案成品
	 * 
	 * @param dictPorEmsNo
	 * @return
	 */
	public List findBcsDictPorExg(String dictPorEmsNo) {
		return this
				.find("select a from BcsDictPorExg a where a.dictPorHead.dictPorEmsNo=?"
						+ " and a.dictPorHead.declareState=? and a.company= ? ",
						new Object[] { dictPorEmsNo, DeclareState.PROCESS_EXE,
								CommonUtils.getCompany() });
	}

	/**
	 * 统计出口总金额
	 * 
	 * @param contract
	 * @return
	 */
	public Double getTotalPriceBExport(Contract contract) {
		List list = this.find("select sum(a.totalPrice) from"
				+ " ContractExg a where a.contract.id=? and a.company= ? "
				+ " group by a.contract.id ", new Object[] { contract.getId(),
				CommonUtils.getCompany() });
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		}
		return 0.0;
	}

	/**
	 * 统计进口总金额
	 * 
	 * @param contract
	 * @return
	 */
	public Double getTotalPriceBImport(Contract contract) {
		List list = this.find("select sum(a.totalPrice) from"
				+ " ContractImg a where a.contract.id=? and a.company= ? "
				+ " group by a.contract.id ", new Object[] { contract.getId(),
				CommonUtils.getCompany() });
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		}
		return 0.0;
	}

	/**
	 * 根据成品对应的合同ID,查询合同BOM资料
	 * 
	 * @param list
	 * @return
	 */
	public List findBomDetailMaterialInContract(List list) {
		String hsql = " select a from ContractBom a where a.company= ? ";
		String comm = ",";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany());
		for (int i = 0; i < list.size(); i++) {
			Contract contract = (Contract) list.get(i);
			String where = " and a.contractExg.contract.id = ? ";
			hsql += comm + where;
			parameters.add(contract.getId());
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查询通关合同备案中的料件
	 * 
	 * @param list
	 * @return
	 */
	public List findMaterialInContract(List list) {
		String hsql = "select a from ContractImg a "
				+ " where rtrim(a.complex.code)||rtrim(a.name)||rtrim(a.spec)||rtrim(a.unit.name) in "
				+ " (select rtrim(b.complex.code)||rtrim(b.name)||rtrim(b.spec)||rtrim(b.unit.name) from ContractImg b "
				+ " group by b.complex.code,b.name,b.spec,b.unit.name having count(*) >0) and "
				+ " a.company= ? ";
		// String hsql = "select a from ContractImg a "
		// +
		// " where rtrim(a.complex.code)+rtrim(a.name)+rtrim(a.spec)+rtrim(a.unit.name) in "
		// +
		// " (select rtrim(b.complex.code)+rtrim(b.name)+rtrim(b.spec)+rtrim(b.unit.name) from ContractImg b "
		// +
		// " group by b.complex.code,b.name,b.spec,b.unit.name having count(*) >0) and "
		// + " a.company= ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany());
		hsql += " and a.contract.id in ( ";
		for (int i = 0; i < list.size(); i++) {
			Contract contract = (Contract) list.get(i);
			String where = "?, ";
			if (list.size() == 1 || i == list.size() - 1) {
				where = "?)";
			}
			hsql += where;
			parameters.add(contract.getId());
		}
		System.out.println("hsql===" + hsql);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找正在执行的合同
	 * 
	 * @return List 是Contract型，合同备案表头
	 */
	public List findContract(Company company) {
		return this
				.find("select a from Contract a where a.company= ? and a.declareState=? ",
						new Object[] { company, DeclareState.PROCESS_EXE });
	}

	/**
	 * 根据起止成品序号查询合同BOM资料,去除未修改的
	 * 
	 * @param head
	 * @param be
	 * @param en
	 * @param declareStateCHANGING_EXE
	 * @return
	 */
	public List findContractBomBySeq(Contract head, Integer be, Integer en,
			Boolean declareStateCHANGING_EXE, Boolean declareStateWAIT_EAA) {
		List para = new ArrayList();
		String hsql = " select a from ContractBom a  left join  a.contractExg  b "
				+ " where a.company.id= ?   and  b.contract.id=? ";
		para.add(CommonUtils.getCompany().getId());
		para.add(head.getId());
		if (be != null) {
			hsql += " and b.seqNum>=? ";
			para.add(be);
		}
		if (en != null) {
			hsql += " and b.seqNum<=? ";
			para.add(en);
		}
		if (declareStateCHANGING_EXE) {
			hsql += " and a.modifyMark<>?";
			para.add(ModifyMarkState.UNCHANGE);
		}
		if (declareStateWAIT_EAA) {
			hsql += " and ( a.modifyMark=? ";
			para.add(ModifyMarkState.MODIFIED);
			hsql += " or a.modifyMark=? )";
			para.add(ModifyMarkState.ADDED);
		}
		hsql += " order by b.seqNum ";
		return this.find(hsql, para.toArray());
	}

	/**
	 * 根据报关单头手册号,凭证序号,商品编码,查询报关单表头流水号
	 * 
	 * @param id
	 * @param number
	 * @param complexCode
	 * @return
	 */
	public List findBcsCustomsDeclarationCommInfo(String id,
			Integer commSerialNo, String complexCode, Integer impExpFlag,
			Integer impExpType) {

		return this
				.find(" select distinct a.baseCustomsDeclaration.serialNumber from BcsCustomsDeclarationCommInfo a "
						+ " where   a.baseCustomsDeclaration.emsHeadH2k=? and a.commSerialNo= ? and a.complex.code = ?"
						+ " and a.baseCustomsDeclaration.impExpFlag=? "
						+ " and a.baseCustomsDeclaration.impExpType<>? ",
						new Object[] { id, commSerialNo, complexCode,
								impExpFlag, impExpType });
	}

	/**
	 * 查询报关单
	 * 
	 * @param emdNo
	 *            手册编号
	 * @param contractNo
	 *            合同号
	 * @return 报关单
	 */
	public List findBcsCustomsDeclaration(String emdNo) {
		return this.find(" select distinct a from BcsCustomsDeclaration as a "
				+ "where a.emsHeadH2k=?" + " and a.effective=? "
				+ " and a.company=? ",
				new Object[] { emdNo, true, CommonUtils.getCompany() });
	}

	/**
	 * 根据报关单表头进出口类型,抓取报关单明细
	 * 
	 * @param contractemsHeadH2k
	 * @param isMateriel
	 * @return
	 */
	public List findCustomsDeclaretionDetailByContract(
			String contractemsHeadH2k, boolean isMateriel) {
		List para = new ArrayList();
		String hsql = " select a from BcsCustomsDeclarationCommInfo a where a.company.id=? "
				+ "  and  a.baseCustomsDeclaration.emsHeadH2k= ?  "
				+ "  and a.baseCustomsDeclaration.effective=? ";
		para.add(CommonUtils.getCompany().getId());
		para.add(contractemsHeadH2k);
		para.add(true);
		if (isMateriel) {
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?,?,?,?,?,?,?)";
			para.add(ImpExpType.DIRECT_IMPORT);
			para.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			para.add(ImpExpType.GENERAL_TRADE_IMPORT);
			para.add(ImpExpType.BACK_MATERIEL_EXPORT);
			para.add(ImpExpType.REMIAN_MATERIAL_BACK_PORT);
			para.add(ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES);
			para.add(ImpExpType.REMAIN_FORWARD_EXPORT);
			para.add(ImpExpType.MATERIAL_DOMESTIC_SALES);
			para.add(ImpExpType.MATERIAL_EXCHANGE);
			para.add(ImpExpType.MATERIAL_REOUT);
			para.add(ImpExpType.REMAIN_FORWARD_IMPORT);
		} else {
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?,?,?,?,? )";
			para.add(ImpExpType.BACK_FACTORY_REWORK);
			para.add(ImpExpType.DIRECT_EXPORT);
			para.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			para.add(ImpExpType.REWORK_EXPORT);
			para.add(ImpExpType.GENERAL_TRADE_EXPORT);
			para.add(ImpExpType.IMPORT_EXG_BACK);
			para.add(ImpExpType.IMPORT_REPAIR_MATERIAL);
			para.add(ImpExpType.EXPORT_MATERIAL_REBACK);
			para.add(ImpExpType.EXPORT_EXG_REBACK);
		}
		return this.find(hsql, para.toArray());
	}

	/**
	 * 根据报关单表头抓取报关单明细
	 * 
	 * @param bcsCustomsDeclaration
	 * @return
	 */
	public List<BcsCustomsDeclarationCommInfo> findCommInfoByBGDHead(
			BcsCustomsDeclaration bcsCustomsDeclaration) {
		return this
				.find("from BcsCustomsDeclarationCommInfo as a where a.baseCustomsDeclaration.id=?",
						bcsCustomsDeclaration.getId());
	}

	/**
	 * 查询合同下的报关成品
	 * 
	 * @param bcsCustomsDeclaration
	 * @return
	 */
	public ContractExg findContractExgByComplex(
			BcsCustomsDeclarationCommInfo comm, Contract contract) {
		List list = this.find("from ContractExg as a "
				+ "where a.contract.id=? and a.seqNum=?", new Object[] {
				contract.getId(), comm.getCommSerialNo() });
		if (list != null && list.size() > 0)
			return (ContractExg) list.get(0);
		return null;

	}

	/**
	 * 查询合同下的报关成品
	 * 
	 * @param bcsCustomsDeclaration
	 * @return
	 */
	public ContractExg findContractExgByBcs(BcsCustomsDeclarationCommInfo comm,
			String emsNo) {
		List list = this.find("from ContractExg as a "
				+ "where a.contract.emsNo=? and a.seqNum=?", new Object[] {
				emsNo, comm.getCommSerialNo() });
		if (list != null && list.size() > 0)
			return (ContractExg) list.get(0);
		return null;

	}

	/**
	 * 查询合同下的报关成品料件
	 * 
	 * @param bcsCustomsDeclaration
	 * @return
	 */
	public ContractImg findComtractImgByComplex(
			BcsCustomsDeclarationCommInfo comm, Contract contract) {
		List list = this.find("from ContractImg as a "
				+ "where a.contract.id=? and a.seqNum=?", new Object[] {
				contract.getId(), comm.getCommSerialNo() });
		if (list != null && list.size() > 0)
			return (ContractImg) list.get(0);
		return null;

	}

	/**
	 * 查找合同已修改成品
	 * 
	 * @param contract
	 * @return
	 */
	public List findContractExgAfterModify(Contract contract) {
		String hsql = "select a from ContractExg a where a.contract.id = ?  and a.company.id=? and (a.modifyMark=1 or a.modifyMark=2)"
				+ " order by a.seqNum ";
		return this.find(hsql, new Object[] { contract.getId(),
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找合同新增成品
	 * 
	 * @param contract
	 * @return
	 */
	public List findContractExgAfterAddModify(Contract contract) {
		String hsql = "select a from ContractExg a where a.contract.id = ?  and a.company.id=? and a.modifyMark=3"
				+ " order by a.seqNum ";
		return this.find(hsql, new Object[] { contract.getId(),
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找合同已修改料件
	 * 
	 * @param contract
	 * @return
	 */
	public List findContractImgAfterModify(Contract contract) {
		String hsql = "select a from ContractImg a where a.contract.id = ?  and a.company.id=? and (a.modifyMark=1 or a.modifyMark=2)"
				+ " order by a.seqNum ";
		return this.find(hsql, new Object[] { contract.getId(),
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找合同新增料件
	 * 
	 * @param contract
	 * @return
	 */
	public List findContractImgAfterAddModify(Contract contract) {
		String hsql = "select a from ContractImg a where a.contract.id = ?  and a.company.id=? and a.modifyMark=3"
				+ " order by a.seqNum ";
		return this.find(hsql, new Object[] { contract.getId(),
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据成品序号查找合同成品资料
	 * 
	 * @param contract
	 * @param exg
	 * @return
	 */
	public ContractExg findContractExgBefore(Contract contract, ContractExg exg) {
		String hsql = "select a from ContractExg a where a.contract.id = ?  and a.company.id=? and a.seqNum=?";
		List list = this.find(hsql, new Object[] { contract.getId(),
				CommonUtils.getCompany().getId(), exg.getSeqNum() });
		if (list != null && list.size() > 0) {
			return (ContractExg) list.get(0);
		}
		return null;
	}

	/**
	 * 根据料件序号查找合同料件资料
	 * 
	 * @param contract
	 * @param img
	 * @return
	 */
	public ContractImg findContractImgBefore(Contract contract, ContractImg img) {
		String hsql = "select a from ContractImg a where a.contract.id = ?  and a.company.id=? and a.seqNum=?";
		List list = this.find(hsql, new Object[] { contract.getId(),
				CommonUtils.getCompany().getId(), img.getSeqNum() });
		if (list != null && list.size() > 0) {
			return (ContractImg) list.get(0);
		}
		return null;
	}

	/**
	 * 查找正在执行的合同
	 * 
	 * @param contract
	 * @return
	 */
	public Contract findExcuteContract(Contract contract) {
		String hsql = "select a from Contract a where a.company.id=? and a.emsNo=? and a.declareState=?";
		List list = this.find(hsql, new Object[] {
				CommonUtils.getCompany().getId(), contract.getEmsNo(), "3" });
		if (list != null && list.size() > 0) {
			return (Contract) list.get(0);
		}
		return null;
	}

	/**
	 * 查询合同料件根据序号
	 * 
	 * @param parentId
	 *            合同备案表头Id
	 * @param seqNum
	 *            开始序号
	 * 
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractImgBySeqNum(String parentId) {
		return this.find("select c from ContractImg c where c.contract.id=? "
				+ "and c.company.id=? order by c.seqNum ", new Object[] {
				parentId, CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询合同Bom中的料件总表序号
	 */
	public List findContractImgSeqNumByExgAndContract(ContractExg exg) {
		Contract contract = exg.getContract();
		String hsql = "select a.contractImgSeqNum from ContractBom a where a.contractExg.contract.company.id=? and a.contractExg.contract=? and a.contractExg=?";
		return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
				contract, exg });
	}

	/**
	 * 根据成品查找报关单耗资料 2010-05-27 hcl
	 */
	public List findCustomsBomDetailByExg(ContractExg exg) {
		List<Object> paramters = new ArrayList<Object>();
		String exgName = exg.getName();
		String exgSpce = exg.getSpec();
		Unit exgUnit = exg.getUnit();
		System.out.println("name=" + exgName);
		System.out.println("exgSpce=" + exgSpce);

		System.out.println("exgUnit=" + exgUnit.getName());

		String hsql = "select a from BcsCustomsBomDetail a where a.bcsCustomsBomExg.company.id= ?";
		paramters.add(CommonUtils.getCompany().getId());
		if (exgName != null) {
			hsql += " and a.bcsCustomsBomExg.name=? ";
			paramters.add(exgName);
		}
		if (exgSpce != null) {
			hsql += " and a.bcsCustomsBomExg.spec=? ";
			paramters.add(exgSpce);
		}
		if (exgUnit != null) {
			hsql += " and a.bcsCustomsBomExg.unit=? ";
			paramters.add(exgUnit);
		}
		return this.find(hsql, paramters.toArray());
	}

	/**
	 * 根据BOM表返回备案资料库中，料件的备案序号
	 * 
	 * @param bsb
	 *            Bom表
	 * @return 2010-06月-08 hcl
	 */
	public List getImgCredenceNo(BcsCustomsBomDetail bsb) {
		String hsql = "select a.seqNum from BcsDictPorImg a where a.company.id= ? and a.dictPorHead.declareState = ? ";
		List<Object> paramters = new ArrayList<Object>();
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(DeclareState.PROCESS_EXE);// 正在执行
		if (bsb.getTenSeqNum() != null) {
			hsql += " and a.innerMergeSeqNum=? ";
			paramters.add(bsb.getTenSeqNum());
		}
		if (bsb.getComplex() != null) {
			hsql += " and a.complex=? ";
			paramters.add(bsb.getComplex());
		}
		if (bsb.getName() != null) {
			hsql += " and a.commName=? ";
			paramters.add(bsb.getName());
		}
		if (bsb.getSpec() != null) {
			hsql += " and a.commSpec=? ";
			paramters.add(bsb.getSpec());
		}
		return this.find(hsql, paramters.toArray());
	}

	/**
	 * 返回合同中料件最大序号
	 * 
	 * @param parentId
	 * @return
	 */
	public List getMaxSeq(String parentId) {
		String hsql = "select max(a.seqNum) from ContractImg a where a.company.id=? and a.contract.id=?";
		List<Object> paramters = new ArrayList<Object>();
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(parentId);
		return this.find(hsql, paramters.toArray());
	}

	/**
	 * 判断货币
	 */
	public List findCurrRateByCurr(String curr1) {
		List list = this.find(
				"select a from CurrRate a where a.company.id = ? "
						+ " and a.curr.currSymb='CNY' and a.curr1.currSymb=? "
						+ "order by a.createDate asc ", new Object[] {
						CommonUtils.getCompany().getId(), curr1 });
		return list;
	}

	/**
	 * 判断货币
	 */
	public List findCurrRate(String curr, String curr1) {
		List list = this.find(
				"select a from CurrRate a where a.company.id = ? "
						+ " and a.curr.currSymb=? and a.curr1.currSymb=? "
						+ "order by a.createDate asc ", new Object[] {
						CommonUtils.getCompany().getId(), curr, curr1 });
		return list;
	}

	/**
	 * 判断货币
	 */
	public List findCurrRateByDeclarationDate(String curr, String curr1,
			Date declarationDate) {
		List list = this
				.find("select a from CurrRate a where a.company.id = ? "
						+ " and a.curr.currSymb=? and a.curr1.currSymb=? and a.createDate < ?"
						+ "order by a.createDate asc ", new Object[] {
						CommonUtils.getCompany().getId(), curr, curr1,
						declarationDate });
		return list;
	}

	/**
	 * 查询电子化手册合同备案料件信息(注意：商品禁用使用到)
	 * 
	 * @param isMaterial
	 *            物料类别
	 * @return
	 */
	public List findEdiMaterielInfo(boolean isMaterial, String emsNo) {
		String classname = "";
		List list = null;
		if (isMaterial) {
			classname = "ContractImg";
			list = this
					.find(" select a from "
							+ classname
							+ " as a "
							+ " where a.contract.declareState=? "
							+ " and a.contract.company.id=? "
							+ " and a.contract.emsNo = ?  and (a.isForbid <>? or a.isForbid=null)"
							+ "  order by a.seqNum", new Object[] {
							DeclareState.PROCESS_EXE,
							CommonUtils.getCompany().getId(), emsNo,
							new Boolean(true) });
		} else {
			classname = "ContractExg";
			list = this
					.find(" select a from "
							+ classname
							+ " as a "
							+ " where a.contract.declareState=? "
							+ " and a.contract.company.id=? "
							+ " and a.contract.emsNo = ? and (a.isForbid <>? or a.isForbid=null)"
							+ "  order by a.seqNum", new Object[] {
							DeclareState.PROCESS_EXE,
							CommonUtils.getCompany().getId(), emsNo,
							new Boolean(true) });
		}
		return list;
	}

	/**
	 * 查找商品禁用信息
	 * 
	 * @param isMateriel
	 *            物料类别
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	public List findCommdityForbid(String emsNo, boolean isMateriel,
			Date beginDate, Date endDate) {
		List list = new Vector();
		List<Object> parameters = new ArrayList<Object>();
		String sql = "";
		if (isMateriel) {
			sql = "select a from BcsCommdityForbid a where a.type = ? and a.company.id = ? "
					+ "and a.emsNo=?";
			parameters.add(MaterielType.MATERIEL);
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(emsNo);
			if (beginDate != null) {
				sql += " and (a.forbiddate>=?  or a.forbiddate is null) ";
				parameters.add(beginDate);
			}
			if (endDate != null) {
				sql += " and (a.forbiddate<=?  or a.forbiddate is null) ";
				parameters.add(endDate);
			}
		} else {
			sql = "select a from BcsCommdityForbid a where a.type = ? and a.company.id = ?"
					+ "and a.emsNo=?";
			parameters.add(MaterielType.FINISHED_PRODUCT);
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(emsNo);
			if (beginDate != null) {
				sql += " and (a.forbiddate>=?  or a.forbiddate is null) ";
				parameters.add(beginDate);
			}
			if (endDate != null) {
				sql += " and (a.forbiddate<=?  or a.forbiddate is null) ";
				parameters.add(endDate);
			}
		}
		// sql += " order by Convert(Integer,a.seqNum)";
		sql += " order by   a.seqNum ";
		list = this.find(sql, parameters.toArray());
		return list;
	}

	/**
	 * 获得所有进口合同号
	 */
	public List findAllImpContractNo() {
		List list = this.find(
				"select a.impContractNo from Contract a where a.company.id=? ",
				new Object[] { CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 获得最大的出口合同号
	 */
	public List findAllExpContractNo() {
		List list = this.find(
				"select a.expContractNo from Contract a where a.company.id=? ",
				new Object[] { CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 根据进口合同号查找合同
	 */
	public List findImpContractByContractNo(String impContractNo) {
		List<Object> parameters = new ArrayList<Object>();
		String sql = " select a from Contract a where a.company.id=?  ";
		parameters.add(CommonUtils.getCompany().getId());
		if (impContractNo != null && !"".equals(impContractNo)) {
			sql += " and a.impContractNo = ? ";
			parameters.add(impContractNo);
		}
		List list = this.find(sql, parameters.toArray());
		return list;
	}

	/**
	 * 根据出口合同号查找合同
	 */
	public List findExpContractByContractNo(String expContractNo) {
		List<Object> parameters = new ArrayList<Object>();
		String sql = " select a from Contract a where a.company.id=?  ";
		parameters.add(CommonUtils.getCompany().getId());
		if (expContractNo != null && !"".equals(expContractNo)) {
			sql += " and a.expContractNo = ? ";
			parameters.add(expContractNo);
		}
		List list = this.find(sql, parameters.toArray());
		return list;
	}

	/**
	 * 查看转抄的BOM中是否存在相同的记录号商品
	 */
	public List<Object[]> isExistContractByImgCredenceNo(String emsnoToid,
			StringBuffer imgCredenceNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "";
		hsql = "select credenceNo,a.contract.id  from  ContractImg a  where  a.contract.id=? and a.company.id=? and a.credenceNo in ("
				+ imgCredenceNo.toString()
				+ ") group by credenceNo,"
				+ "a.contract.id having count(credenceNo) > 1";

		parameters.add(emsnoToid);
		parameters.add(CommonUtils.getCompany().getId());
		List list = this.find(hsql, parameters.toArray());
		return list;
		// if (list.size() > 0) {
		// Double a = Double.valueOf(list.get(0).toString());
		// if(a > 1){
		// return true;
		// }
		// }
		// return false;
	}

	/**
	 * 查找【出口装箱单/出口加工发票】打印显示购货单位/发货单位
	 * 
	 * @return
	 */
	public List findExportPackinglistOrInvoice() {
		return this
				.find("select a.isExportPackinglistOrInvoice from CompanyOther a where a.company= ?",
						new Object[] { CommonUtils.getCompany() });
	}

	/**
	 * 查询正在执行的合同料件，在备案资料库中不存在的记录号
	 * 
	 * @return
	 */
	public List finddictporImgExgNo(String parentId, String materialType) {
		String tableName1 = "";
		String tableName2 = "";
		if ((materialType).equals(MaterielType.MATERIEL)) {
			tableName1 = " ContractImg ";
			tableName2 = " BcsDictPorImg ";

		} else if ((materialType).equals(MaterielType.FINISHED_PRODUCT)) {
			tableName1 = " ContractExg ";
			tableName2 = " BcsDictPorExg  ";
		}
		return this
				.find("select a  from "
						+ tableName1
						+ "a where  not exists(select 1 from "
						+ tableName2
						+ "where seqNum=a.credenceNo and dictPorHead.declareState = ? ) and a.company= ?  and a.contract.id = ? ",
						new Object[] { DeclareState.PROCESS_EXE,
								CommonUtils.getCompany(), parentId });
	}

}
