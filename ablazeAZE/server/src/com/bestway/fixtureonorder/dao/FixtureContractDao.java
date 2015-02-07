/*
 * Created on 2005-3-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.fixtureonorder.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ProcessedContractData;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclaration;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.FixType;
import com.bestway.common.constant.ImpExpType;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.customs.common.entity.BaseExportInvoiceItem;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclaration;

import com.bestway.fixtureonorder.entity.FixtureContract;
import com.bestway.fixtureonorder.entity.FixtureContractItems;
import com.bestway.fixtureonorder.entity.FixtureLocation;

/**
 * com.bestway.fixtureonorder.dao.FixtureContractDao
 * checked by cjb 2009.11
 * 设备合同dao数据库操作
 * @author fhz
 */
public class FixtureContractDao extends BaseDao {

	/**
	 * 查找所有的合同
	 * 
	 * @return List 是FixtureContract型，合同备案表头
	 */
	public List findContract() {
		return this.find(
				"select a from FixtureContract a where a.company.id= ? ",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据ID查找合同
	 * 
	 * @return List 是FixtureContract型，合同备案表头
	 */
	public FixtureContract findContractById(String id) {
		List list = this
				.find(
						"select a from FixtureContract a where a.company= ? and a.id=? ",
						new Object[] { CommonUtils.getCompany(), id });
		if (list.size() > 0) {
			return (FixtureContract) list.get(0);
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
				.find(
						"select max(a.preContractCodeSuffix) from FixtureContract as a where a.company= ? ",
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
	 * @return List 是FixtureContract型，合同备案表头
	 */
	public List findContract(boolean isCancel) {
		return this.find(
				"select a from FixtureContract a where a.company.id= ? ",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找所有的合同来自正在执行的
	 * 
	 * @return List 是FixtureContract型，合同备案表头
	 */
	public List findContractByProcessExe() {
		return this
				.find(
						"select a from FixtureContract a where a.company.id= ? and a.declareState=? ",
						new Object[] { CommonUtils.getCompany().getId(),
								DeclareState.PROCESS_EXE });
	}

	/**
	 * 查找所有的合同来自正在执行的
	 * 
	 * @return List 是FixtureContract型，合同备案表头
	 */
	public List findContractByProcessExe(Company company) {
		return this
				.find(
						"select a from FixtureContract a where a.company= ? and a.declareState=? ",
						new Object[] { company, DeclareState.PROCESS_EXE });
	}

	/**
	 * 查找条件是申报状态的合同
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @param declareState
	 *            申报状态
	 * @return List 是FixtureContract型，合同备案表头
	 */
	public FixtureContract findContractByEmsNo(String emsNo, String declareState) {
		List list = this
				.find(
						"select a from FixtureContract a where a.company= ? and a.emsNo= ? and a.declareState=? ",
						new Object[] { CommonUtils.getCompany(), emsNo,
								declareState });
		if (list != null && list.size() > 0) {
			return (FixtureContract) list.get(0);
		}
		return null;
	}

	/**
	 * 查找条件是申报状态的合同
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @return List 是FixtureContract型，合同备案表头
	 */
	public FixtureContract findContractByEmsNo(String emsNo) {
		List list = this
				.find(
						"select a from FixtureContract a where a.company= ? and a.emsNo= ? and a.declareState=? ",
						new Object[] { CommonUtils.getCompany(), emsNo,
								DeclareState.PROCESS_EXE });
		if (list != null && list.size() > 0) {
			return (FixtureContract) list.get(0);
		}
		return null;
	}
	
	/**
	 * 查找合同号为emsNo的合同
	 * @param emsNo
	 * @return
	 */
	public FixtureContract findContractByEms(String agreementNo){
		List list = this
		.find(
				"select a from FixtureContract a where a.company= ? and a.agreementNo= ?",
				new Object[] { CommonUtils.getCompany(), agreementNo});
		if (list != null && list.size() > 0) {
			return (FixtureContract) list.get(0);
		}
		return null;
	}
	
	/**
	 * 查找合同下编码，名称，规格一样的料件
	 * @param fixtureContract
	 * @param code
	 * @param name
	 * @param spec
	 * @return
	 */
	public FixtureContractItems findContractItemsByComplex(FixtureContract fixtureContract,String code,String name,String spec){
		String hsql = "select a from FixtureContractItems a where a.company.id= ? and a.contract.id=? and a.complex.code=?";
		List p = new ArrayList();
		p.add(CommonUtils.getCompany().getId());
		p.add(fixtureContract.getId());
		p.add(code);
		if(name!=null && !"".equals(name)){
			hsql = hsql + " and a.name=?";
			p.add(name);
		}
		if(spec!=null && !"".equals(spec)){
			hsql = hsql + " and a.spec=?";
			p.add(spec);
		}
		List list = this
		.find(
				hsql,p.toArray()
				);
		if (list != null && list.size() > 0) {
			return (FixtureContractItems) list.get(0);
		}
		return null;
	}

	/**
	 * 该帐册编号的合同是否存在
	 * 
	 * @param contract
	 *            合同备案表头
	 * @return boolean 帐册编号的合同是否存在，存在为true，否则为false
	 */
	public boolean isExistContractByEmsNo(FixtureContract contract) {
		if ("".equals(contract.getEmsNo())) {
			return false;
		}
		List list = new ArrayList();
		if (contract.getId() == null || contract.getId().equals("")) {
			list = this
					.find(
							"select a from FixtureContract a where a.company= ? and a.emsNo= ?  ",
							new Object[] { CommonUtils.getCompany(),
									contract.getEmsNo() });
		} else {
			list = this.find(
					"select a from FixtureContract a where a.company= ? and a.emsNo= ? "
							+ " and a.id<>? ", new Object[] {
							CommonUtils.getCompany(), contract.getEmsNo(),
							contract.getId() });
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
	public void saveContract(FixtureContract contract) {
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

	// ///////////
	// 合同料件
	// ///////////

	/**
	 * 查找所有的合同料件
	 * 
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractItem() {
		return this.find(
				"select a from FixtureContractItems a where a.company= ? "
						+ " order by a.seqNum ", new Object[] { CommonUtils
						.getCompany() });
	}

	/**
	 * 查找合同料件 来自 合同备案表头Id
	 * 
	 * @param parentId
	 *            合同备案表头Id
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractItemByParentId(String parentId) {
		return this
				.find(
						"select a from FixtureContractItems a where a.contract.id = ? and a.company.id = ? "
								+ " order by a.seqNum ", new Object[] {
								parentId, CommonUtils.getCompany().getId() });
	}
	
	/**
	 * 查找合同料件 来自 合同备案表头Id
	 * 
	 * @param parentId
	 *            合同备案表头Id
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractItem(String ptNo,String parentId,String secendNo,String name,String spec,Date startDate,Date endDate) {
		List p = new ArrayList();
		String hsql = "select distinct a from FixtureContractItems a";		
		if(ptNo!=null && !"".equals(ptNo)){
			hsql += ",FactoryAndFactualCustomsRalation b where a.contract.id = ? and a.company.id = ? "
				+" and b.company.id=? ";
			p.add(parentId);
			p.add(CommonUtils.getCompany().getId());
			p.add(CommonUtils.getCompany().getId());
			hsql += " and a.name=b.statCusNameRelationHsn.cusName and a.spec=b.statCusNameRelationHsn.cusSpec "
				+" and a.unit=b.statCusNameRelationHsn.cusUnit and a.complex=b.statCusNameRelationHsn.complex "
				+" and b.materiel.ptNo like ? ";
			
			
//			hsql = hsql + "and ptNo like ? ";
			p.add("%"+ptNo+"%");
		}else{
			hsql += " where a.contract.id = ? and a.company.id = ? ";
			p.add(parentId);
			p.add(CommonUtils.getCompany().getId());
		}
		if(secendNo!=null && !"".equals(secendNo)){
			hsql = hsql+"and secondProtocol like ? " ;
			p.add("%"+secendNo+"%");
		}
		if(name!=null && !"".equals(name)){
			hsql = hsql+"and name like ? " ;
			p.add("%"+name+"%");
		}
		if (spec != null && !"".equals(spec)) {
			hsql = hsql + "and spec like ? ";
			p.add("%"+spec+"%");
		} 
		if (startDate == null && endDate != null) {
			hsql = hsql + "and importDate <= ? ";
			p.add(endDate);
		} else if (startDate != null && endDate == null) {
			hsql = hsql + "and importDate >= ? and importDate<= ? ";
			p.add(startDate);
			p.add(new Date());
		} else if (startDate != null && endDate != null) {
			hsql = hsql + "and importDate >= ? and importDate<= ? ";
			p.add(startDate);
			p.add(endDate);
		}	
//		System.out.println(hsql);
		return this
				.find(hsql,p.toArray());
	}

	/**
	 * 查找合同料件 来自 合同备案表头Id
	 * 
	 * @param parentId
	 *            合同备案表头Id
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractItemByParentIdAndChange(String parentId) {
		return this
				.find(
						"select a from FixtureContractItems a where a.contract.id = ? and a.company.id=? and a.isChange=? "
								+ " order by a.seqNum ", new Object[] {
								parentId, CommonUtils.getCompany().getId(),
								new Boolean(true) });
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

		String hsql = "select a from FixtureContractItems a where a.contract.id = ? "
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
		String hsql = "select a from FixtureContractItems a where a.contract.id = ? "
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
		return this.find(
				"select a from FixtureContractItems a where a.contract.emsNo = ? "
						+ " and a.declareState=? "
						+ " and a.contract.company.id=? "
						+ " order by a.seqNum ", new Object[] { emsNo,
						DeclareState.PROCESS_EXE,
						CommonUtils.getCompany().getId() });
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
	public FixtureContractItems findContractItems(String contractId,
			String seqNum) {
		List list = this.find(
				"select a from FixtureContractItems a where a.contract.id = ? "
						+ " and a.seqNum=?", new Object[] { contractId,
						Integer.valueOf(seqNum) });
		if (list.size() <= 0) {
			return null;
		} else {
			return (FixtureContractItems) list.get(0);
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
	public FixtureContractItems findContractImgByEmsNoSeqNum(String emsNo,
			String seqNum) {
		List list = this.find(
				"select a from FixtureContractItems a where a.contract.emsNo = ? "
						+ " and a.contract.declareState=?"
						+ " and a.seqNum=? and a.contract.company.id=? ",
				new Object[] { emsNo, DeclareState.PROCESS_EXE,
						Integer.valueOf(seqNum),
						CommonUtils.getCompany().getId() });
		if (list.size() <= 0) {
			return null;
		} else {
			return (FixtureContractItems) list.get(0);
		}
	}

	/**
	 * 保存合同料件
	 * 
	 * @param contractImg
	 *            合同料件
	 */
	public void saveContractImg(FixtureContractItems contractImg) {
		this.saveOrUpdate(contractImg);
	}

	/**
	 * 删除合同料件
	 * 
	 * @param contractImg
	 *            合同料件
	 */
	public void deleteContractImg(FixtureContractItems contractImg) {
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
	 * 当前合同料件记录总数
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @return int 合同料件记录总数
	 */
	public int findContractItemCount(String contractId) {
		List list = this.find(
				" select count(a.id) from FixtureContractItems as a "
						+ "  where a.contract.id=? ",
				new Object[] { contractId });
		if (list != null && !list.isEmpty()) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
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
		return this
				.find(
						"select c from FixtureContractItems c where c.contract.id=? "
								+ " and c.seqNum>=? and c.seqNum<=? order by c.seqNum ",
						new Object[] { parentId, beginSeqNum, endSeqNum });
	}

	/**
	 * 查找已审核的凭证成品
	 * 
	 * @return List
	 */
	public List findIsAuditingCredenceExg() {
		return this
				.find(
						" select a from Credence a where a.company= ? and a.isAuditing = ? and a.materielType=? ",
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
				.find(
						"select a  from Credence a where a.company= ? and a.isAuditing = ? and (a.materielType=? or a.materielType=?)",
						new Object[] { CommonUtils.getCompany(),
								Boolean.valueOf(true), MaterielType.MATERIEL,
								MaterielType.ASSISTANT_MATERIAL });
	}

	/**
	 * 获得最大的料件总表序号来自当前合同
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @return int 最大的料件总表序号
	 */
	public int getMaxContractItemSeqNum(String contractId) {
		List list = this.find(
				"select max(a.seqNum) from FixtureContractItems as a "
						+ " where a.company.id=? and a.contract.id = ? ",
				new Object[] { CommonUtils.getCompany().getId(), contractId });
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
	public Double getItemAmountSum(String contractId) {
		List list = this.find(
				"select SUM(a.amount*a.declarePrice) from FixtureContractItems as a "
						+ " where a.company.id=? and a.contract.id = ? ",
				new Object[] { CommonUtils.getCompany().getId(), contractId });
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		}
		return 0.0;
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
				.find(
						"select a from FixtureContractItems a where a.company.id=? "
								+ " and a.contract.declareState=? and a.contract.emsNo = ? and a.seqNum = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								DeclareState.PROCESS_EXE, emsNo,
								Integer.valueOf(seqNum) });
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
				.find(
						"select a from FixtureContractItems a where a.company.id=? "
								+ " and a.contract.declareState=? and a.contract.emsNo = ? and a.credenceNo = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								DeclareState.PROCESS_EXE, emsNo,
								Integer.valueOf(credenceNo) });
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
	public List findContractImgByCredenceNo(FixtureContract contract,
			Integer credenceNo) {
		return this
				.find(
						"select a from FixtureContractItems a where a.company.id=? and a.contract.id = ? "
								+ " and a.credenceNo = ?", new Object[] {
								CommonUtils.getCompany().getId(),
								contract.getId(), credenceNo });
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
	public FixtureContractItems findFixtureContractItems(FixtureContract contract, BaseCustomsDeclarationCommInfo CommInfo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from FixtureContractItems a "
				+ " where a.company.id=? "
				+ " and a.seqNum=? "
				+ " and a.contract.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(CommInfo.getCommSerialNo());
		parameters.add(contract.getId());
		
		List list=this.find(hsql, parameters.toArray());
		if(list.size()>0)
			return (FixtureContractItems)list.get(0);
		return null;
	}

	/**
	 * 查找所有的自用商品编码
	 * 
	 * @return List 是Complex型，自用商品编码
	 */
	public List findComplex() {
		return this.find("select a from Complex a where a.company= ? ",
				new Object[] { CommonUtils.getCompany() });
	}
	
	/**
	 * 
	 * 
	 * @return 是Complex型，自用商品编码
	 */
	public Complex findComplexByCode(String code) {		
		List complexs = this.find("select a from Complex as a where a.code=?",
				new Object[] { code });
		if(complexs==null || complexs.size()<=0){
			return null;
		}
		return (Complex)complexs.get(0);
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
	 * 获取出口发票项目来自出口报关单Id
	 * 
	 * @param customsDeclarationId
	 *            出口报关单id
	 * @return 与指定报关单id匹配的报关单中的出口发票信息
	 */
	public List findExportInvoiceItemByCustomsDeclarationId(
			String customsDeclarationId) {
		String hql = "";
		hql = "select a from ExportInvoiceItem a ";

		hql += " where a.baseCustomsDeclaration.id=? and a.company.id=?";
		return this.find(hql, new Object[] { customsDeclarationId,
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 保存或修改出口发票项目
	 * 
	 * @param baseExportInvoiceItem
	 *            出口发票项目
	 */
	public void saveExportInvoiceItem(
			BaseExportInvoiceItem baseExportInvoiceItem) {
		this.saveOrUpdate(baseExportInvoiceItem);
	}

	/**
	 * 查询来料设备合同明显
	 * 
	 * @param baseCustomsDeclaration
	 * @return
	 */
	public List findFixtureContractItemsByCustom(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		String customsDeclarationCommInfo = "CustomsDeclarationCommInfo";
		if (baseCustomsDeclaration instanceof CustomsDeclaration) {
			customsDeclarationCommInfo = "CustomsDeclarationCommInfo";
		} else if (baseCustomsDeclaration instanceof BcsCustomsDeclaration) {
			customsDeclarationCommInfo = "BcsCustomsDeclarationCommInfo";
		} else if (baseCustomsDeclaration instanceof DzscCustomsDeclaration) {
			customsDeclarationCommInfo = "DzscCustomsDeclarationCommInfo";
		}
		return this.find("select a from FixtureContractItems a where "
				+ " a.company.id=? and a.contract.emsNo= ? and a.contract.declareState=? "
				//去掉重复的--修改2009.4.3--cjb--修改单号2009022802客服胡娣---客户洪梅  
//				+ " and a.seqNum not in (select b.commSerialNo from "
//				+ customsDeclarationCommInfo
//				+ " b where b.baseCustomsDeclaration.id=? "
//				+ " and b.baseCustomsDeclaration.fixType=? )"
				,new Object[] {
				CommonUtils.getCompany().getId(),
				baseCustomsDeclaration.getEmsHeadH2k(),
				DeclareState.PROCESS_EXE,
//				baseCustomsDeclaration.getId(), 
//				FixType.LAILIAO 
				});
	}

	/**
	 * 查询设备类型的特殊报关单表头
	 * 
	 * @param baseCustomsDeclaration
	 * @return
	 */
	public List findFixtureCustomsDeclaration(String baseCustomsDeclaration,
			Company company) {
		String customsDeclaration = "CustomsDeclaration";
		if (baseCustomsDeclaration.equals("CustomsDeclaration")) {
			customsDeclaration = "CustomsDeclaration";
		} else if (baseCustomsDeclaration.equals("BcsCustomsDeclaration")) {
			customsDeclaration = "BcsCustomsDeclaration";
		} else if (baseCustomsDeclaration.equals("DzscCustomsDeclaration")) {
			customsDeclaration = "DzscCustomsDeclaration";
		}
		List list = this
				.find(
						"select a from "
								+ customsDeclaration
								+ " a where a.company.id=? and a.effective=? and a.fixType in (?,?) ",
						new Object[] { company.getId(), true, FixType.SHANZHI, FixType.LAILIAO });
		
		return list;

	}

	/**
	 * 存放位置表查询存放位置表
	 * 
	 * @return
	 */
	public List findFixtureLocation() {
		return this.find(
				"select a from FixtureLocation a where a.company.id= ? ",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 删除存放位置表
	 * 
	 * @param location
	 */
	public void deleteFixtureLocation(FixtureLocation location) {
		this.delete(location);
	}

	/**
	 * 保存存放位置表
	 * 
	 * @param location
	 */
	public void saveFixtureLocation(FixtureLocation location) {
		this.saveOrUpdate(location);
	}

	/**
	 * 查询设备位置状况
	 * 
	 * @return
	 */
	public List findFixtureLocationResultInfo() {
		return this
				.find(
						"select a from FixtureLocationResultInfo a where a.company.id= ? ",
						new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询设备异动单据
	 * 
	 * @return
	 */
	public List findFixtureLocationChangeBillInfo() {
		return this
				.find(
						"select a from FixtureLocationChangeBillInfo a where a.company.id= ? ",
						new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询设备异动单据
	 * 
	 * @return
	 */
	public List findFixtureLocationChangeBillInfoByLocation(
			FixtureLocation fixtureLocation) {
		return this.find(
				"select a from FixtureLocationChangeBillInfo a where a.company.id= ? "
						+ " and (a.oldLocation.id=? or a.newLocation.id=?) ",
				new Object[] { CommonUtils.getCompany().getId(),
						fixtureLocation.getId(), fixtureLocation.getId() });
	}

	/**
	 * 查询设备报关单
	 * 
	 * @param customsDeclarationCommInfo
	 * @param impExpTypes
	 * @param agreementNo
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List findCustomsDeclaration(String customsDeclarationCommInfo,
			Integer[] impExpTypes) {
		String hql = "select a,b from " + customsDeclarationCommInfo
				+ " a,FixtureContract b "
				+ " where a.baseCustomsDeclaration.emsHeadH2k=b.emsNo "
				+ " and a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.fixType=? "
				+ " and b.company.id=? "
				+ " and a.baseCustomsDeclaration.effective=? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(FixType.LAILIAO);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(true);
		if (impExpTypes.length > 0) {
			hql += " and ( ";
			for (int i = 0; i < impExpTypes.length; i++) {
				if (i == 0) {
					hql += " a.baseCustomsDeclaration.impExpType=? ";
				} else {
					hql += " or a.baseCustomsDeclaration.impExpType=? ";
				}
				parameters.add(impExpTypes[i]);
			}
			hql += " ) ";
		}
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 抓取已经存在的设备相关的报关单信息
	 * 
	 * @return
	 */
	public List findExistFixtureCustomsInfo() {
		List lsResult = new ArrayList();
		List list = this
				.find(
						"select distinct a.customsDeclarationCode,a.customsDeclaItemNo"
								+ " from FixtureLocationResultInfo a where a.company.id= ? ",
						new Object[] { CommonUtils.getCompany().getId() });
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			lsResult.add((objs[0] == null ? "" : objs[0].toString().trim())
					+ "-" + (objs[1] == null ? "" : objs[1].toString().trim()));
		}
		return lsResult;
	}

	/**
	 * 查询设备位置状况
	 * 
	 * @return
	 */
	public List findFixtureLocationResultInfo(String customsDeclarationCode,
			String itemNo) {
		return this
				.find(
						"select a from FixtureLocationResultInfo a where a.company.id= ? "
								+ " and a.customsDeclarationCode=? and a.customsDeclaItemNo=? ",
						new Object[] { CommonUtils.getCompany().getId(),
								customsDeclarationCode, itemNo });
	}

	/**
	 * 查询设备位置状况
	 * 
	 * @return
	 */
	public List findFixtureLocationResultInfo(String emsNo,
			String customsDeclarationCode, String seqNum) {
		return this
				.find(
						"select a from FixtureLocationResultInfo a where a.company.id= ? "
								+ " and a.customsDeclarationCode=? and a.customsDeclaItemNo=? and a.emsNo=? ",
						new Object[] { CommonUtils.getCompany().getId(),
								customsDeclarationCode, seqNum,emsNo });
	}

	/**
	 * 查询设备位置状况
	 * 
	 * @return
	 */
	public List findFixtureLocationChangeBillInfo(String emsNo,
			String customsDeclarationCode, String seqNum) {
		return this
				.find(
						"select a from FixtureLocationChangeBillInfo a where a.company.id= ? "
								+ " and a.customsDeclarationCode=? and a.customsDeclaItemNo=? and a.emsNo=? ",
						new Object[] { CommonUtils.getCompany().getId(),
								customsDeclarationCode, seqNum,emsNo });
	}

	/**
	 * 查询设备位置状况
	 * 
	 * @return
	 */
	public List getFixtureLocationResultInfoMaxSeqNo(
			String customsDeclarationCode, String itemNo, Integer seqNo) {
		return this
				.find(
						"select max(a.seqNo) from FixtureLocationResultInfo a where a.company.id= ? "
								+ " and a.customsDeclarationCode=? and a.customsDeclaItemNo=? ",
						new Object[] { CommonUtils.getCompany().getId(),
								customsDeclarationCode, itemNo });
	}

	/**
	 * 查询设备位置状况
	 * 
	 * @return
	 */
	public List fixtureLocationResultInfoByLocation(FixtureLocation location) {
		return this.find(
				"select a from FixtureLocationResultInfo a where a.company.id= ? "
						+ " and a.location.id!=? ", new Object[] {
						CommonUtils.getCompany().getId(), location.getId() });
	}

	/**
	 * 查询批文协议设备明细
	 * 
	 * @param agreement
	 * @return
	 */
	public List findFixtureContractItems() {
		return this.find(
				"select a from FixtureContractItems a where a.company.id= ? ",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询设备报关单
	 * 
	 * @param customsDeclarationCommInfo
	 * @param impExpTypes
	 * @param emsNo
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List findCustomsDeclaration(String customsDeclarationCommInfo,
			Integer[] impExpTypes, String emsNo, Date beginDate, Date endDate) {
		String hql = "select a from "
				+ customsDeclarationCommInfo
				+ " a,FixtureContract b "
				+ " where a.baseCustomsDeclaration.emsHeadH2k=b.emsNo "
				+ " and a.baseCustomsDeclaration.company.id=? "
				+ " and b.company.id=? and a.baseCustomsDeclaration.effective=? "
				+ " and a.baseCustomsDeclaration.fixType=? "
				+ " and b.declareState=? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(true);
		parameters.add(FixType.LAILIAO);
		parameters.add(DeclareState.PROCESS_EXE);
		if (emsNo != null && !"".equals(emsNo.trim())) {
			hql += " and b.emsNo= ? ";
			parameters.add(emsNo);
		}
		if (impExpTypes.length > 0) {
			hql += " and ( ";
			for (int i = 0; i < impExpTypes.length; i++) {
				if (i == 0) {
					hql += " a.baseCustomsDeclaration.impExpType=? ";
				} else {
					hql += " or a.baseCustomsDeclaration.impExpType=? ";
				}
				parameters.add(impExpTypes[i]);
			}
			hql += " ) ";
		}
		if (beginDate != null) {
			hql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getBeginDate(endDate));
		}
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 查询设备报关单
	 * 
	 * @param customsDeclarationCommInfo
	 * @param impExpTypes
	 * @param emsNo
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List findCustomsDeclaration(String customsDeclarationCommInfo,
			Integer[] impExpTypes, String emsNo, Date beginDate, Date endDate,
			int state) {
		String hql = "select a from "
				+ customsDeclarationCommInfo
				+ " a,FixtureContract b "
				+ " where a.baseCustomsDeclaration.emsHeadH2k=b.emsNo "
				+ " and a.baseCustomsDeclaration.company.id=? "
				+ " and b.company.id=? and a.baseCustomsDeclaration.effective=? "
				+ " and a.baseCustomsDeclaration.fixType=? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(true);
		parameters.add(FixType.LAILIAO);
		if (emsNo != null && !"".equals(emsNo.trim())) {
			hql += " and b.emsNo= ? ";
			parameters.add(emsNo);
		}
		if (impExpTypes.length > 0) {
			hql += " and ( ";
			for (int i = 0; i < impExpTypes.length; i++) {
				if (i == 0) {
					hql += " a.baseCustomsDeclaration.impExpType=? ";
				} else {
					hql += " or a.baseCustomsDeclaration.impExpType=? ";
				}
				parameters.add(impExpTypes[i]);
			}
			hql += " ) ";
		}
		if (beginDate != null) {
			hql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getBeginDate(endDate));
		}
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hql += " and a.baseCustomsDeclaration.effective=? ";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hql += " and a.baseCustomsDeclaration.effective=? ";
			parameters.add(false);
		}
		return this.find(hql, parameters.toArray());
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
	public List findCustomsDeclarationCustomer(String BasecustomsDeclaration,
			Integer[] impExpTypes, FixtureContract fixtureContract, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select distinct a.customer from "
				+ BasecustomsDeclaration + " a where a.company.id=? "
				+ " and a.fixType=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(FixType.LAILIAO);
		hsql += " and a.emsHeadH2k=? ";
		parameters.add(fixtureContract.getEmsNo());
		if (impExpTypes.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < impExpTypes.length; i++) {
				if (i == 0) {
					hsql += " a.impExpType=? ";
				} else {
					hsql += " or a.impExpType=? ";
				}
				parameters.add(impExpTypes[i]);
			}
			hsql += " ) ";
		}
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.effective=? ";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.effective=? ";
			parameters.add(false);
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
	public List findCustomsDeclarationCommInfo(
			String BaseCustomsDeclarationCommInfo,
			FixtureContract fixtureContract, Integer seqNum) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a from " + BaseCustomsDeclarationCommInfo
				+ " a, FixtureContract b "
				+ " where a.baseCustomsDeclaration.emsHeadH2k=b.emsNo "
				+ " and b.emsNo=? " + " and a.commSerialNo=? "
				+ " and a.company.id=? and b.company.id= ? "
				+ " and a.baseCustomsDeclaration.fixType=? "
				+ " and a.baseCustomsDeclaration.impExpType in (?,?,?) ";
		parameters.add(fixtureContract.getEmsNo());
		parameters.add(seqNum);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(FixType.LAILIAO);
		parameters.add(ImpExpType.EQUIPMENT_IMPORT);
		parameters.add(ImpExpType.BACK_PORT_REPAIR);
		parameters.add(ImpExpType.EQUIPMENT_BACK_PORT);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找已报关的商品
	 * 
	 * @param isImport
	 *            判断是否进口类型，true为进口
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
	public List findImpExpCommInfoListContract(
			String BaseCustomsDeclarationCommInfo, Integer seqNum, String code,
			String name, String customer, String impExpType, Date beginDate,
			Date endDate, String emsNo, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a, b from " + BaseCustomsDeclarationCommInfo
				+ " as a left join fetch a.complex "
				+ " left join fetch a.baseCustomsDeclaration ";

		hsql += " , FixtureContractItems as b left join fetch b.complex "
				+ " left join fetch b.contract "
				+ " where a.baseCustomsDeclaration.emsHeadH2k=b.contract.emsNo "
				+ " and a.commSerialNo=b.seqNum ";
		hsql += " and b.contract.declareState=? "
				+ " and a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.fixType=? ";
		parameters.add(DeclareState.PROCESS_EXE);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(FixType.LAILIAO);
		if (emsNo != null && (!emsNo.trim().equals(""))) {
			hsql += " and b.contract.emsNo=? ";
			parameters.add(emsNo);
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
		if (impExpType != null && !impExpType.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.impExpType=? ";
			parameters.add(Integer.valueOf(impExpType));
		} else {
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?) ";
			parameters.add(ImpExpType.EQUIPMENT_IMPORT);
			parameters.add(ImpExpType.BACK_PORT_REPAIR);
			parameters.add(ImpExpType.EQUIPMENT_BACK_PORT);
		}
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=? ";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=? ";
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
	 * 按照条件查询设备所在的位置
	 * 
	 * @param reqeust 请求控制
	 * @param seqNum 商品序号
	 * @param code 商品编码
	 * @param name 商品名称
	 * @param fixtureContract 设备协议
	 * @param fixtureLocation 位置存放表
	 * @return 
	 */
	public List findFixtureLocationResultInfo(Integer seqNum,
			String code, String name, FixtureContract fixtureContract,
			FixtureLocation fixtureLocation,Date beginDate, Date endDate,String spec){
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from FixtureLocationResultInfo a "
				+ " where a.company.id=? " ;
		parameters.add(CommonUtils.getCompany().getId());
		if (seqNum != null) {
			hsql += " and a.customsDeclaItemNo=? ";
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
		if (fixtureContract != null ) {
			hsql += " and a.emsNo=? ";
			parameters.add(fixtureContract.getEmsNo());
		}
		if (fixtureLocation != null) {
			hsql += " and a.location.id=? ";
			parameters.add(fixtureLocation.getId());
		}
		if (beginDate != null) {
			hsql += " and a.impExpDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.impExpDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (spec != null && (!spec.equals(""))) {
			hsql += " and a.commSpec=? ";
			parameters.add(spec);
		}
		hsql += " order by a.customsDeclarationCode";
		return this.find(hsql, parameters.toArray());
		
	}
	
	/**
	 * 根据tableName、属性、是否有Company来查找数据,
	 * 
	 * @param tableName
	 *            实体类
	 * @param property
	 *            属性
	 * @param value
	 *            属性值
	 * @param hasCompany
	 *            是否有company栏位
	 * @return
	 */
	public List findObjectByTableNames(String tableName, String[] property,
			Object[] value, boolean hasCompany) {
		List parameters = new ArrayList();
		String hsql = " select a from " + tableName + " a ";
		if (property != null) {
			hsql += " where ";
			for (int i = 0; i < property.length; i++) {
				if (i == 0)
					hsql += " a." + property[i] + "=? ";
				else {
					hsql += " and a." + property[i] + "=? ";
				}
				parameters.add(value[i]);
			}
		}
		if (hasCompany) {
			if (hsql.contains("where"))
				hsql += " and a.company.id=? ";
			else
				hsql += " where a.company.id=? ";
			parameters.add(CommonUtils.getCompany().getId());
		}
		if (parameters.isEmpty())
			return this.find(hsql);
		return this.find(hsql, parameters.toArray());
	}
	
	/**
	 * 判断料号seqNum在设备里是否存在
	 * @param reqeust
	 * @param seqNum
	 * @return
	 */
	public boolean isExistFixtureContractItems(Integer seqNum,String fixtureContractId){
		String hsql = "select a from FixtureContractItems as a where a.company.id=? and a.seqNum=? and a.contract.id=?";
		List list = this.find(hsql, new Object[]{CommonUtils.getCompany().getId(),seqNum,fixtureContractId});
		if(list!=null && list.size()>0){
			return true;
		}
		return false;
	}

}
