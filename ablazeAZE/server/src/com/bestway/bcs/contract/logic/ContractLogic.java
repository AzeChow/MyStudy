/*
 * Created on 2005-3-28
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contract.logic;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.bestway.bcs.bcsinnermerge.dao.BcsInnerMergeDao;
import com.bestway.bcs.bcsinnermerge.entity.BcsCustomsBomDetail;
import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcs.contract.dao.ContractDao;
import com.bestway.bcs.contract.entity.BcsCommdityForbid;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contract.entity.ContractUnitWaste;
import com.bestway.bcs.contract.entity.ImgExgObject;
import com.bestway.bcs.contract.entity.TempChangedContractImgExg;
import com.bestway.bcs.contract.entity.TempContractBom;
import com.bestway.bcs.contract.entity.TempContractDomesticPurchaseBill;
import com.bestway.bcs.contract.entity.TempContractExg;
import com.bestway.bcs.contract.entity.TempContractImg;
import com.bestway.bcs.contract.entity.TempImgAndExgUsedDiffAmount;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationCommInfo;
import com.bestway.bcs.dictpor.entity.BcsDictPorExg;
import com.bestway.bcs.dictpor.entity.BcsDictPorImg;
import com.bestway.bcs.message.logic.BcsMessageLogic;
import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.Request;
import com.bestway.common.constant.BcsBusinessType;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.DeleteResultMark;
import com.bestway.common.constant.DzscBusinessType;
import com.bestway.common.constant.ManageObject;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.erpbill.entity.CustomOrder;
import com.bestway.common.erpbill.entity.CustomOrderDetail;
import com.bestway.common.erpbill.entity.MakeCustomOrderToContract;
import com.bestway.common.fileimport.logic.ImportDataFromExcel;
import com.bestway.common.message.entity.CspReceiptResult;
import com.bestway.common.message.entity.CspSignInfo;
import com.bestway.common.message.entity.TempCspReceiptResultInfo;
import com.bestway.common.message.logic.CspProcessMessage;

/**
 * 合同操作logic com.bestway.bcs.contract.logic.ContractLogic * edit by cjb 2009.8
 * 
 * @author yp
 */
public class ContractLogic {
	/**
	 * 合同操作接口
	 */
	private ContractDao contractDao = null;

	/**
	 * 内部归并DAO接口
	 */
	private BcsInnerMergeDao bcsInnerMergeDao = null;

	/**
	 * 报文操作接口
	 */
	private BcsMessageLogic bcsMessageLogic = null;

	/**
	 * 数据导入接口
	 */
	private ImportDataFromExcel importDataFromExcel = null;

	/**
	 * 获取contractDao
	 * 
	 * @return Returns the contractDao.
	 */
	public ContractDao getContractDao() {
		return contractDao;
	}

	/**
	 * 设置contractDao
	 * 
	 * @param contractDao
	 *            The contractDao to set.
	 */
	public void setContractDao(ContractDao contractDao) {
		this.contractDao = contractDao;
	}

	public BcsMessageLogic getBcsMessageLogic() {
		return bcsMessageLogic;
	}

	public void setBcsMessageLogic(BcsMessageLogic bcsExportMessageLogic) {
		this.bcsMessageLogic = bcsExportMessageLogic;
	}

	public ImportDataFromExcel getImportDataFromExcel() {
		return importDataFromExcel;
	}

	public void setImportDataFromExcel(ImportDataFromExcel importDataFromExcel) {
		this.importDataFromExcel = importDataFromExcel;
	}

	/**
	 * 获取内部归并DAO接口
	 * 
	 * @return
	 */
	public BcsInnerMergeDao getBcsInnerMergeDao() {
		return bcsInnerMergeDao;
	}

	/**
	 * 设置内部归并DAO接口
	 * 
	 * @param bcsInnerMergeDao
	 */
	public void setBcsInnerMergeDao(BcsInnerMergeDao bcsInnerMergeDao) {
		this.bcsInnerMergeDao = bcsInnerMergeDao;
	}

	/**
	 * 新增合同备案表头
	 * 
	 * @return DzscEmsPorHead 通关备案表头
	 */
	public Contract newContract() {
		CompanyOther comOther = contractDao.getSysCompanyOther();

		// String copEmsNo = bcsMessageLogic.getNewCopEntNo("Contract",
		// "copEmsNo", "C", BcsBusinessType.EMS_POR_BILL);

		// 企业内部编码 必须是唯一的数值
		String copEmsNo = bcsMessageLogic.getContractNewCopEntNo("Contract",
				"copEmsNo", "C", BcsBusinessType.EMS_POR_BILL);

		BcsParameterSet parameterSet = this.contractDao.findBcsParameterSet();

		Contract head = new Contract();

		Company com = this.contractDao.findCompany();

		// 新签合同的进/出口合同号为12位（年份+企业海关十位编码后5位+3位流水号）
		String impContractNo = this.getMaxImpContractNo();

		String expContractNo = this.getMaxExpContractNo();
		// head.setSeqNum(Integer.valueOf(contractDao.getNum("DzscEmsPorHead",
		// "seqNum")));

		StringBuffer stringBuffer = new StringBuffer();

		if (com.getBuCode() == null || "".equals(com.getBuCode())) {
			stringBuffer.append("公司的经营单位编码不能为空\r\n");
		}
		if (com.getBuName() == null || "".equals(com.getBuName())) {
			stringBuffer.append("公司的经营单位名称不能为空\r\n");
		}
		if (com.getCode() == null || "".equals(com.getCode())) {
			stringBuffer.append("公司的加工单位编码不能为空\r\n");
		}
		if (com.getName() == null || "".equals(com.getName())) {
			stringBuffer.append("公司的加工单位名称不能为空\r\n");
		}
		if (!stringBuffer.toString().trim().equals("")) {
			throw new RuntimeException(stringBuffer.toString());
		}

		// 企业内部编码
		head.setCopEmsNo(copEmsNo);

		head.setTradeCode(com.getBuCode());
		head.setTradeName(com.getBuName());
		head.setMachCode(com.getCode());
		head.setMachName(com.getName());
		head.setImpContractNo(impContractNo);
		head.setExpContractNo(expContractNo);
		head.setOutTradeCo(com.getOutTradeCo());
		head.setEnterpriseAddress(com.getAddress());
		head.setLinkMan(com.getLinkman());
		head.setContactTel(com.getTel());
		head.setDeclareCustoms(com.getMasterCustoms());
		head.setRedDep(com.getMasterFtc());
		head.setDeclareState(DeclareState.APPLY_POR);
		head.setModifyMark(ModifyMarkState.ADDED);
		head.setIsContractEms(parameterSet.getIsContractEms());
		head.setOutLinkManager(com.getOutLinkManager());
		head.setCompany(com);
		if (comOther != null) {
			head.setSancEmsNo(comOther.getSancEmsNo());
			head.setIePort1(comOther.getIePort1());
			head.setIePort2(comOther.getIePort2());
			head.setIePort3(comOther.getIePort3());
			head.setIePort4(comOther.getIePort4());
			head.setIePort5(comOther.getIePort5());
			// head.setBargainType(comOther.getBargainType());
			head.setTrade(comOther.getTrade());
			head.setLevyKind(comOther.getLevyKind());
			head.setTradeCountry(comOther.getTradeCountry());
			// head.setNote(comOther.getNote());
			// head.setBargainKind(comOther.getBargainKind());
			head.setTransac(comOther.getTransac());
			head.setCurr(comOther.getCommonCurr());
		}
		head.setAclUser(CommonUtils.getAclUser());
		head.setSaveDate(new Date());
		this.contractDao.saveContract(head);
		return head;
	}

	/**
	 * 删除合同表头
	 * 
	 * @param c
	 *            合同表头
	 */
	public void deleteContract(Contract c) {
		List<Contract> list = new ArrayList<Contract>();
		if (list.add(c)) {
			this.deleteContract(list);
		}
	}

	/**
	 * 删除合同表头
	 * 
	 * @param list
	 *            是Contract型，合同表头
	 */
	public void deleteContract(List<Contract> list) {
		for (int i = 0; i < list.size(); i++) {
			Contract c = list.get(i);
			String emsNo = c.getEmsNo();
			c = this.contractDao.findContractById(c.getId());
			if (c == null) {
				throw new RuntimeException("合同" + emsNo + "已经被删除");
			}
			if (c.getIsCancel()
					&& c.getDeclareState().equals(DeclareState.PROCESS_EXE)) {
				throw new RuntimeException("合同 " + c.getEmsNo() + " 已经生效不能删除");
			}
			/**
			 * 查找合同成品 来自 合同成品ID
			 */
			List contractExgList = this.contractDao.findContractExgByParentId(c
					.getId());
			if (contractExgList != null && contractExgList.size() > 0) {
				for (int j = 0; j < contractExgList.size(); j++) {
					ContractExg contractExg = (ContractExg) contractExgList
							.get(j);
					/**
					 * 反写订单转入合同后
					 */
					witerBackCoustomOrder(contractExg.getId(), contractExg
							.getContract().getCopEmsNo());
					/**
					 * 查找合同BOM 来自 合同成品IDc
					 */
					// List contractBomList = this.contractDao
					// .findContractBomByExgId(contractExg.getId());

				}
				this.contractDao.deleteByContractBom(c.getId());
				this.contractDao.deleteByContractExg(c.getId());
			}
			/**
			 * 查找合同料件 来自 合同成品ID
			 */
			List contractImgList = this.contractDao
					.findContractImgByContractId(c.getId());
			if (contractImgList != null && contractImgList.size() > 0) {

				/**
				 * 反写订单转入合同后
				 */
				for (int m = 0; m < contractImgList.size(); m++) {
					ContractImg contractImg = (ContractImg) contractImgList
							.get(m);
					witerBackCoustomOrder(contractImg.getId(), contractImg
							.getContract().getCopEmsNo());
				}
				this.contractDao.deleteContractImg(contractImgList);
			}
		}
		this.contractDao.deleteContract(list);

	}

	public List findContractBomImg(List list) {
		/**
		 * 查找合同BOM 来自 合同料件序号
		 */
		List<ContractImg> lists = new ArrayList<ContractImg>();
		for (int i = 0; i < list.size(); i++) {
			ContractImg contractImg = (ContractImg) list.get(i);
			List contractBomList = this.contractDao
					.findContractBomByImgSeqNum(contractImg);
			if (contractBomList.size() > 0) {
				lists.add(contractImg);
			}
		}
		return lists;
	}

	/**
	 * 删除合同成品
	 * 
	 * @param list
	 *            是ContractExg型，合同成品
	 * @param isCalculateFinishProductSum
	 *            是否要反算成品总金额，true为要反算
	 * @param isCalculateMaterielSum
	 *            是否要反算成品总金额，true为要反算
	 */
	public Map<Integer, List<ContractExg>> deleteContractExg(
			List<ContractExg> list) {
		Map<Integer, List<ContractExg>> map = new HashMap<Integer, List<ContractExg>>();
		List<ContractExg> lsDelete = new ArrayList<ContractExg>();
		List<ContractExg> lsUpdate = new ArrayList<ContractExg>();
		for (int i = 0; i < list.size(); i++) {
			ContractExg contractExg = list.get(i);
			if (ModifyMarkState.DELETED.equals(contractExg.getModifyMark())) {
				continue;
			}
			/**
			 * 查找合同BOM 来自 合同成品ID
			 */
			List contractBomList = this.contractDao
					.findContractBomByExgId(contractExg.getId());
			/**
			 * 调用本地方法
			 */
			this.deleteContractBom(contractBomList);
			if (ModifyMarkState.ADDED.equals(contractExg.getModifyMark())) {
				this.contractDao.deleteContractExg(contractExg);
				lsDelete.add(contractExg);
			} else {
				int count = this.contractDao
						.findBcsCustomsDeclarationCommInfoCount(contractExg
								.getContract().getEmsNo(), contractExg
								.getSeqNum(), false);
				if (count > 0) {
					throw new RuntimeException("已经有报关单用到此成品"
							+ contractExg.getSeqNum() + ":"
							+ contractExg.getComplex().getCode() + ",所以不能删除");
				}
				contractExg.setModifyMark(ModifyMarkState.DELETED);
				this.contractDao.saveContractExg(contractExg);
				lsUpdate.add(contractExg);
			}

			/**
			 * 反写订单转入合同后
			 */
			witerBackCoustomOrder(contractExg.getId(), contractExg
					.getContract().getCopEmsNo());
		}
		if (list.size() > 0) {
			Contract contract = ((ContractExg) list.get(0)).getContract();
			this.statContractExgCount(contract);
			this.statContractExgMoney(contract);
		}
		map.put(DeleteResultMark.DELETE, lsDelete);
		map.put(DeleteResultMark.UPDATE, lsUpdate);
		return map;
	}

	/**
	 * 反写订单转入合同后
	 * 
	 * @param contractImgExgId
	 * @param copEmsNo
	 */
	private void witerBackCoustomOrder(String contractImgExgId, String copEmsNo) {

		List listMakeToContract = this.contractDao
				.findMakeCustomOrderToContract(contractImgExgId);
		for (int j = 0; j < listMakeToContract.size(); j++) {
			MakeCustomOrderToContract make = (MakeCustomOrderToContract) listMakeToContract
					.get(j);

			List listOrderDetail = this.contractDao.findCustomOrderDetail(make
					.getCustomOrderDetailId());
			for (int m = 0; m < listOrderDetail.size(); m++) {
				CustomOrderDetail customOrderDetail = (CustomOrderDetail) listOrderDetail
						.get(m);
				customOrderDetail.setIfcustomer(new Boolean(false));
				// 可能有问题暂时这样写，因为转为合同时，是全部转全了，没有只转一部分的数量。
				customOrderDetail.setNotContractNum(customOrderDetail
						.getBgamount());
				customOrderDetail.setContractNum(Double.valueOf(0));
				customOrderDetail.setCopEmsNo("");

				this.contractDao.saveOrUpdate(customOrderDetail);
				// 保存申请单头
				CustomOrder bill = customOrderDetail.getCustomOrder();
				Integer tocustomCount = this.contractDao
						.getCustomOrderDetailForToContract(bill);
				bill.setIsChangeToContract(tocustomCount);
				// bill.setCopEmsNo(deleListNo(bill.getCopEmsNo(), copEmsNo));
				this.contractDao.saveOrUpdate(bill);
			}
			this.contractDao.delete(make);
		}
	}

	/**
	 * 保存合同料件
	 * 
	 * @param list
	 *            是ContractImg型，合同料件
	 */
	public void saveContractImg(List<ContractImg> list) {
		if (list.size() <= 0) {
			return;
		}
		Map<Integer, List<ContractBom>> mapBom = this
				.findContractBomGroupByImgSeqNum(list.get(0).getContract());
		for (ContractImg img : list) {
			this.updateContractBomImgSeqNum(img, mapBom);
			this.contractDao.saveContractImg(img);
			// this.contractDao.updateContractBomImgSeqNum(img);
		}
		if (list.size() > 0) {
			Contract contract = ((ContractImg) list.get(0)).getContract();
			this.statContractImgCount(contract);
			this.statContractImgMoney(contract);
		}
	}

	/**
	 * 删除合同料件
	 * 
	 * @param list
	 *            是ContractImg型，合同料件
	 * @param isCalculateFinishProductSum
	 *            是否要反算成品总金额，true为要反算
	 * @param isCalculateMaterielSum
	 *            是否要反算成品总金额，true为要反算
	 */
	public Map<Integer, List<ContractImg>> deleteContractImg(
			List<ContractImg> list) {
		Map<Integer, List<ContractImg>> map = new HashMap<Integer, List<ContractImg>>();
		List<ContractImg> lsDelete = new ArrayList<ContractImg>();
		List<ContractImg> lsUpdate = new ArrayList<ContractImg>();
		for (int i = 0; i < list.size(); i++) {
			ContractImg contractImg = list.get(i);
			if (ModifyMarkState.ADDED.equals(contractImg.getModifyMark())) {
				this.contractDao.deleteContractImg(contractImg);
				lsDelete.add(contractImg);
			} else {
				int count = this.contractDao
						.findBcsCustomsDeclarationCommInfoCount(contractImg
								.getContract().getEmsNo(), contractImg
								.getSeqNum(), true);
				if (count > 0) {
					throw new RuntimeException("已经有报关单用到此料件"
							+ contractImg.getSeqNum() + ":"
							+ contractImg.getComplex().getCode() + ",所以不能删除");
				}
				contractImg.setModifyMark(ModifyMarkState.DELETED);
				this.contractDao.saveContractImg(contractImg);
				lsUpdate.add(contractImg);
			}
			/**
			 * 查找合同BOM 来自 合同料件序号
			 */
			List contractBomList = this.contractDao
					.findContractBomByImgSeqNum(contractImg);
			this.deleteContractBom(contractBomList);

			/**
			 * 反写订单转入合同后
			 */
			witerBackCoustomOrder(contractImg.getId(), contractImg
					.getContract().getCopEmsNo());
		}
		if (list.size() > 0) {
			Contract contract = ((ContractImg) list.get(0)).getContract();
			this.statContractImgCount(contract);
			this.statContractImgMoney(contract);
		}
		map.put(DeleteResultMark.DELETE, lsDelete);
		map.put(DeleteResultMark.UPDATE, lsUpdate);
		return map;
	}

	/**
	 * 转抄合同数据
	 * 
	 * @param list
	 *            是Contract型，合同表头
	 */
	public List copyContract(List<Contract> list) {
		List<Contract> lsResult = new ArrayList<Contract>();
		try {
			for (int i = 0; i < list.size(); i++) {
				Contract c = (Contract) BeanUtils.cloneBean(list.get(i));
				String contractId = c.getId();
				// if (!c.getDeclareState().equals(DeclareState.PROCESS_EXE))
				// {//
				// 如果不是正在执行的不能转抄
				// continue;
				// }

				// String copEmsNo = bcsMessageLogic.getNewCopEntNo("Contract",
				// "copEmsNo", "C", BcsBusinessType.EMS_POR_BILL);
				// // 新签合同的进/出口合同号为12位（年份+企业海关十位编码后5位+3位流水号）

				String copEmsNo = bcsMessageLogic.getContractNewCopEntNo(
						"Contract", "copEmsNo", "C",
						BcsBusinessType.EMS_POR_BILL);
				// 新签合同的进/出口合同号为12位（年份+企业海关十位编码后5位+3位流水号）

				String impContractNo = this.getMaxImpContractNo();
				String expContractNo = this.getMaxExpContractNo();
				/**
				 * 转抄合同
				 */
				c.setId(null);
				c.setEmsNo(null);
				c.setCopEmsNo(copEmsNo);
				c.setDeclareCustoms(null);
				c.setImpContractNo(impContractNo);
				c.setExpContractNo(expContractNo);
				c.setApproveDate(null);
				c.setBeginDate(null);
				c.setEndDate(null);
				c.setDeferDate(null);
				c.setDestroyDate(null);
				c.setIsImportFromQP(false);
				c.setDeclareState(DeclareState.APPLY_POR);
				c.setModifyMark(ModifyMarkState.ADDED);
				c.setSaveDate(new Date());
				this.contractDao.saveContract(c);

				/**
				 * 查找合同料件 来自 合同成品ID
				 */
				Map<Integer, String> contractImgMap = new HashMap<Integer, String>();
				List contractImgList = this.contractDao
						.findContractImgByContractId(contractId);

				for (int j = 0; j < contractImgList.size(); j++) {
					ContractImg contractImg = (ContractImg) BeanUtils
							.cloneBean(contractImgList.get(j));
					/**
					 * 转抄料件
					 */
					contractImg.setId(null);
					contractImg.setContract(c);
					contractImg.setDutyRate(0.0);
					contractImg.setModifyMark(ModifyMarkState.ADDED);
					this.contractDao.saveContractImg(contractImg);
					//
					// 存入新的料件ID用于排序和变更时用
					//
					if (contractImg.getSeqNum() != null) {
						contractImgMap.put(contractImg.getSeqNum(),
								contractImg.getId());
					}
				}

				//
				// 查找合同成品 来自 合同成品ID
				//
				List contractExgList = this.contractDao
						.findContractExgByParentId(contractId);
				for (int j = 0; j < contractExgList.size(); j++) {
					ContractExg contractExg = (ContractExg) BeanUtils
							.cloneBean(contractExgList.get(j));
					String contractExgId = contractExg.getId();
					/**
					 * 转抄成品
					 */
					contractExg.setId(null);
					// contractExg.setDeclareState(DeclareState.APPLY_POR);
					contractExg.setContract(c);
					contractExg.setModifyMark(ModifyMarkState.ADDED);
					this.contractDao.saveContractExg(contractExg);
					/**
					 * 查找合同BOM 来自 合同成品ID
					 */
					List contractBomList = this.contractDao
							.findContractBomByExgId(contractExgId);
					for (int k = 0; k < contractBomList.size(); k++) {
						ContractBom bom = (ContractBom) contractBomList.get(k);
						ContractBom contractBom = (ContractBom) BeanUtils
								.cloneBean(bom);

						/**
						 * 转抄BOM
						 */
						contractBom.setId(null);
						// contractBom.setDeclareState(DeclareState.APPLY_POR);
						contractBom.setContractExg(contractExg);
						contractBom
								.setNonMnlRatio(bom.getNonMnlRatio() == null ? 0.0
										: bom.getNonMnlRatio());
						contractBom.setModifyMark(ModifyMarkState.ADDED);
						// if (contractBom.getContractImgSeqNum() != null) {
						// contractBom.setContractImgId(contractImgMap
						// .get(contractBom.getContractImgSeqNum()));
						// }
						this.contractDao.saveContractBom(contractBom);
					}
				}
				lsResult.add(c);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
		return lsResult;
	}

	/**
	 * 备案合同数据
	 * 
	 * @param contract
	 *            合同表头
	 */
	public void putOnRecordContract(Contract contract) {
		Contract contractTemp = this.contractDao.findContractByEmsNo(
				contract.getEmsNo(), DeclareState.PROCESS_EXE);
		this.effectiveContract(contract, contractTemp, null);
	}

	/**
	 * 判断合同是否可以备案
	 * 
	 * @param contract
	 *            合同表头
	 */
	public String checkContractForPutOnRecord(Contract contract) {
		// System.out.println("1----------------------------");
		StringBuffer message = new StringBuffer("");
		List<ContractImg> lsImg = this.contractDao
				.findContractImgByContractId(contract.getId());
		for (ContractImg img : lsImg) {
			if (img.getAmount() == null || img.getAmount() <= 0) {
				// throw new RuntimeException("料件" + img.getSeqNum().toString()
				// + " " + img.getName() + " 数量为空或等于零，所以不能备案");
				message.append("料件" + img.getSeqNum().toString() + "  "
						+ img.getName() + " 数量为空或等于零 \n");
			}
		}
		List<ContractExg> lsExg = this.contractDao
				.findContractExgByParentId(contract.getId());
		for (ContractExg exg : lsExg) {
			if (exg.getExportAmount() == null || exg.getExportAmount() <= 0) {
				message.append("成品" + exg.getSeqNum().toString() + "  "
						+ exg.getName() + " 数量为空或等于零 \n");
				// throw new RuntimeException("成品" + exg.getSeqNum().toString()
				// + " " + exg.getName() + " 数量为空或等于零，所以不能备案");
			}
		}
		List<ContractBom> lsBom = this.contractDao
				.findContractBomByContractParentId(contract.getId());
		for (ContractBom bom : lsBom) {
			if (bom.getUnitWaste() == null || bom.getUnitWaste() <= 0) {
				message.append("BOM 成品"
						+ bom.getContractExg().getSeqNum().toString() + "  "
						+ bom.getContractExg().getName() + " 对应料件 "
						+ bom.getContractImgSeqNum().toString() + "  "
						+ bom.getName() + "  数量为空或等于零 \n");
				// throw new RuntimeException("BOM 成品"
				// + bom.getContractExg().getSeqNum().toString() + " "
				// + bom.getContractExg().getName() + "\n" + "对应料件 "
				// + bom.getContractImgSeqNum().toString() + " "
				// + bom.getName() + " \n 数量为空或等于零，所以不能备案");
			}
		}
		return message.toString();
	}

	/**
	 * 判断合同料件表、成品表、及单耗表的数量是否必需取整
	 * 
	 * @param contract
	 *            合同表头
	 */
	public String checkContractIsAmountToInteger(Contract contract) {
		StringBuffer message = new StringBuffer("");
		List<ContractImg> lsImg = this.contractDao
				.findContractImgByContractId(contract.getId());
		for (ContractImg img : lsImg) {
			if (img.getUnit().getIsMustInt().booleanValue() == true
					&& img.getAmount() != null
					&& (Double.valueOf(img.getAmount()).intValue() == img
							.getAmount()) == false) {
				message.append("料件" + img.getSeqNum().toString() + "  "
						+ img.getName() + " 数量不能有小数 \n");
				// System.out.println("-----aaa------"+img.getUnit().getIsMustInt().booleanValue());
			}
		}
		List<ContractExg> lsExg = this.contractDao
				.findContractExgByParentId(contract.getId());
		for (ContractExg exg : lsExg) {
			if (exg.getUnit().getIsMustInt().booleanValue() == true
					&& exg.getExportAmount() != null
					&& (Double.valueOf(exg.getExportAmount()).intValue() == exg
							.getExportAmount()) == false) {
				message.append("成品" + exg.getSeqNum().toString() + "  "
						+ exg.getName() + " 数量不能有小数\n");
				// System.out.println("-----bbb------"+exg.getUnit().getIsMustInt().booleanValue());
			}
		}
		List<ContractBom> lsBom = this.contractDao
				.findContractBomByContractParentId(contract.getId());
		for (ContractBom bom : lsBom) {
			if (bom.getUnit().getIsMustInt().booleanValue() == true
					&& bom.getAmount() != null
					&& (Double.valueOf(bom.getAmount()).intValue() == bom
							.getAmount()) == false) {
				message.append("BOM 单耗"
						+ bom.getContractExg().getSeqNum().toString() + "  "
						+ bom.getContractExg().getName() + " 对应料件 "
						+ bom.getContractImgSeqNum().toString() + "  "
						+ bom.getName() + "  数量不能有小数 \n");
				// System.out.println("-----ccc------"+bom.getUnit().getIsMustInt().booleanValue());
			}
		}
		return message.toString();
	}

	/**
	 * 变更合同 如果返回null就不能变量 否则 就变更一条新的记录
	 * 
	 * @param contract
	 *            合同表头
	 * @return Contract 合同表头
	 */
	public Contract changingContract(Contract contract) {
		Contract c = new Contract();
		try {
			PropertyUtils.copyProperties(c, contract);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		/**
		 * 合同申报状态
		 */
		String declareState = c.getDeclareState();
		if (!DeclareState.PROCESS_EXE.equals(declareState)) {
			return null;
		}
		/**
		 * 存在已变更的记录
		 */
		Contract contractTemp = this.contractDao.findContractByEmsNo(
				c.getEmsNo(), DeclareState.CHANGING_EXE);
		if (contractTemp != null) {
			return null;
		}
		/**
		 * 存在已变更的记录
		 */
		Contract contractTempa = this.contractDao.findContractByEmsNo(
				c.getEmsNo(), DeclareState.WAIT_EAA);
		if (contractTempa != null) {
			return null;
		}
		/**
		 * 合同表头Id
		 */
		String contractId = c.getId();
		/**
		 * 生成新的合同表头
		 */
		c.setId(null);
		c.setDeclareState(DeclareState.CHANGING_EXE);
		c.setModifyMark(ModifyMarkState.MODIFIED);
		this.contractDao.saveContract(c);
		Map<Integer, String> contractImgMap = new HashMap<Integer, String>();

		/**
		 * 查找合同料件 来自 合同成品ID
		 */
		List contractImgList = this.contractDao
				.findContractImgByContractId(contractId);
		for (int j = 0; j < contractImgList.size(); j++) {
			ContractImg contractImg = new ContractImg();
			try {
				PropertyUtils.copyProperties(contractImg,
						(ContractImg) contractImgList.get(j));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			/**
			 * 转抄料件
			 */
			contractImg.setId(null);
			contractImg.setContract(c);
			contractImg.setDutyRate(0.0);
			this.contractDao.saveContractImg(contractImg);
			//
			// 存入新的料件ID用于排序和变更时用
			//
			if (contractImg.getSeqNum() != null) {
				contractImgMap
						.put(contractImg.getSeqNum(), contractImg.getId());
			}
		}
		/**
		 * 查找合同成品 来自 合同成品ID
		 */
		List contractExgList = this.contractDao
				.findContractExgByParentId(contractId);
		for (int j = 0; j < contractExgList.size(); j++) {
			ContractExg contractExg = new ContractExg();
			try {
				PropertyUtils.copyProperties(contractExg,
						(ContractExg) contractExgList.get(j));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			String contractExgId = contractExg.getId();
			/**
			 * 转抄成品
			 */
			contractExg.setId(null);
			contractExg.setContract(c);
			this.contractDao.saveContractExg(contractExg);
			/**
			 * 查找合同BOM 来自 合同成品ID
			 */
			List contractBomList = this.contractDao
					.findContractBomByExgId(contractExgId);
			for (int k = 0; k < contractBomList.size(); k++) {
				ContractBom old = (ContractBom) contractBomList.get(k);
				ContractBom contractBom = new ContractBom();
				try {
					PropertyUtils.copyProperties(contractBom, old);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				/**
				 * 转抄BOM
				 */
				contractBom.setId(null);
				contractBom.setNonMnlRatio(old.getNonMnlRatio() == null ? 0.0
						: old.getNonMnlRatio());
				contractBom.setContractExg(contractExg);
				// if (contractBom.getContractImgSeqNum() != null) {
				// contractBom.setContractImgId(contractImgMap.get(contractBom
				// .getContractImgSeqNum()));
				// }
				this.contractDao.saveContractBom(contractBom);
			}
		}

		return c;
	}

	/**
	 * 保存合同成品
	 * 
	 * @param contractExg
	 *            合同成品
	 */
	public void saveContractExg(ContractExg contractExg) {
		this.contractDao.saveContractExg(contractExg);
		if (contractExg.getId() != null && !"".equals(contractExg.getId())) {
			BcsParameterSet parameter = this.contractDao.findBcsParameterSet();
			if (parameter != null
					&& parameter.updateContractExgWriteBackBomImg != null
					&& parameter.updateContractExgWriteBackBomImg) {
				this.writeBackContractBomImg(contractExg);
			}
		}
		Contract contract = contractExg.getContract();
		this.statContractExgCount(contract);
		this.statContractExgMoney(contract);
	}

	/**
	 * 变更成品编码
	 * 
	 * @param contractExg
	 *            合同成品
	 */
	public void changeContractExgComplex(ContractExg contractExg) {
		List list = this.contractDao.findBcsCustomsDeclarationCommInfo(false,
				contractExg.getContract(), contractExg.getSeqNum());
		for (int i = 0; i < list.size(); i++) {
			BcsCustomsDeclarationCommInfo commInfo = (BcsCustomsDeclarationCommInfo) list
					.get(i);
			commInfo.setComplex(contractExg.getComplex());
			this.contractDao.saveOrUpdate(commInfo);
		}
	}

	/**
	 * 修改合同成品数量时，回写BOM的料件耗用数量，回写料件总表的总数量
	 * 
	 * @param contractExg
	 */
	private void writeBackContractBomImg(ContractExg contractExg) {
		List list = this.contractDao
				.findContractBomByExgId(contractExg.getId());
		for (int i = 0; i < list.size(); i++) {
			ContractBom contractBom = (ContractBom) list.get(i);
			Double amount = CommonUtils
					.getDoubleByDigit(
							(CommonUtils.getDoubleExceptNull(contractBom
									.getUnitWaste()) / (1 - CommonUtils
									.getDoubleExceptNull(contractBom.getWaste()) / 100.0))
									* CommonUtils
											.getDoubleExceptNull(contractExg
													.getExportAmount()), 5);
			if (!amount.equals(contractBom.getAmount())) {
				contractBom.setAmount(amount);
				contractBom.setTotalPrice(amount
						* CommonUtils.getDoubleExceptNull(contractBom
								.getDeclarePrice()));
				this.contractDao.saveContractBom(contractBom);
			}
		}
		this.writeBackContractImgByBom(contractExg.getContract().getId(), list);
	}

	/**
	 * 保存合同成品来自凭证成品
	 * 
	 * @param contract
	 *            合同表头
	 * @param bcsInnerMergeList
	 *            报关商品资料
	 * @return List 是ContractExg型，合同成品
	 */
	public List saveContractExg(Contract contract, List bcsInnerMergeList) {
		List contractExgList = new ArrayList();
		int maxSeqNum = this.contractDao.getMaxContractExgSeqNum(contract
				.getId()) + 1;
		for (int i = 0; i < bcsInnerMergeList.size(); i++) {
			ContractExg contractExg = new ContractExg();
			BcsTenInnerMerge bcsInnerMerge = (BcsTenInnerMerge) bcsInnerMergeList
					.get(i);
			if (bcsInnerMerge == null) {
				continue;
			}
			contractExg.setSeqNum(maxSeqNum + i);
			contractExg.setCompany(CommonUtils.getCompany());
			contractExg.setContract(contract);
			contractExg.setCredenceNo(bcsInnerMerge.getSeqNum());
			contractExg.setName(bcsInnerMerge.getName());
			contractExg.setSpec(bcsInnerMerge.getSpec());
			contractExg.setComplex(bcsInnerMerge.getComplex());
			contractExg.setUnit(bcsInnerMerge.getComUnit());
			// contractExg.setDeclareState(DeclareState.APPLY_POR);
			// contractExg.setLegalUnit(bcsInnerMerge.getComplex() == null ?
			// null
			// : bcsInnerMerge.getComplex().getFirstUnit());
			contractExg.setLegalAmount(bcsInnerMerge.getLegalAmount());
			contractExg.setUnitPrice(bcsInnerMerge.getPrice());
			contractExg.setExportAmount(0.0);
			/**
			 * 单重就是净重
			 */
			contractExg.setUnitNetWeight(bcsInnerMerge.getUnitWeight());
			contractExg.setCountry(bcsInnerMerge.getCountry());
			contractExg.setModifyMark(ModifyMarkState.ADDED);
			contractExgList.add(contractExg);
		}
		this.contractDao.saveContractExg(contractExgList);
		this.statContractExgCount(contract);
		// this.statContractExgMoney(contract);
		return contractExgList;
	}

	/**
	 * 保存合同成品来自凭证成品
	 * 
	 * @param contract
	 *            合同表头
	 * @param dictPorExgList
	 *            报关商品资料
	 * @return List 是ContractExg型，合同成品
	 */
	public List saveContractExgFromDictPorExg(Contract contract,
			List dictPorExgList) {
		List contractExgList = new ArrayList();
		int maxSeqNum = this.contractDao.getMaxContractExgSeqNum(contract
				.getId()) + 1;
		for (int i = 0; i < dictPorExgList.size(); i++) {
			ContractExg contractExg = new ContractExg();
			BcsDictPorExg bcsDictPorExg = (BcsDictPorExg) dictPorExgList.get(i);
			if (bcsDictPorExg == null) {
				continue;
			}
			contractExg.setSeqNum(maxSeqNum + i);
			contractExg.setCompany(CommonUtils.getCompany());
			contractExg.setContract(contract);
			contractExg.setCredenceNo(bcsDictPorExg.getSeqNum());
			contractExg.setName(bcsDictPorExg.getCommName());
			contractExg.setSpec(bcsDictPorExg.getCommSpec());
			contractExg.setComplex(bcsDictPorExg.getComplex());
			contractExg.setUnit(bcsDictPorExg.getComUnit());
			// contractExg.setDeclareState(DeclareState.APPLY_POR);
			// contractExg.setLegalUnit(bcsInnerMerge.getComplex() == null ?
			// null
			// : bcsInnerMerge.getComplex().getFirstUnit());
			// contractExg.setLegalAmount(bcsDictPorExg.getLegalAmount());
			contractExg.setUnitPrice(bcsDictPorExg.getUnitPrice());
			contractExg.setExportAmount(0.0);
			/**
			 * 单重就是净重
			 */
			// contractExg.setUnitNetWeight(bcsDictPorExg.getUnitWeight());
			// contractExg.setCountry(bcsDictPorExg.getCountry());
			contractExg.setModifyMark(ModifyMarkState.ADDED);
			contractExgList.add(contractExg);
		}
		this.contractDao.saveContractExg(contractExgList);
		this.statContractExgCount(contract);
		// this.statContractExgMoney(contract);
		return contractExgList;
	}

	/**
	 * 保存合同Bom来自凭证料件
	 * 
	 * @param contractExg
	 *            合同成品
	 * @param innerMergeList
	 *            报关商品资料
	 * @return List 是ContractBom型，合同BOM
	 */
	public List saveContractBom(ContractExg contractExg, List innerMergeList) {
		List contractBomList = new ArrayList();
		int maxSeqNum = this.contractDao.getMaxContractBomSeqNum(contractExg
				.getId()) + 1;
		for (int i = 0; i < innerMergeList.size(); i++) {
			ContractBom contractBom = new ContractBom();
			BcsTenInnerMerge bcsInnerMerge = (BcsTenInnerMerge) innerMergeList
					.get(i);

			contractBom.setSeqNum(maxSeqNum + i);
			contractBom.setCompany(CommonUtils.getCompany());
			contractBom.setContractExg(contractExg);
			contractBom.setName(bcsInnerMerge.getName());
			// contractBom.setDeclareState(contractExg.getDeclareState());
			contractBom.setSpec(bcsInnerMerge.getSpec());
			contractBom.setComplex(bcsInnerMerge.getComplex());
			contractBom.setUnit(bcsInnerMerge.getComUnit());
			// contractBom.setLegalUnit(credence.getComplex() == null ? null
			// : credence.getComplex().getFirstUnit());
			contractBom.setLegalAmount(bcsInnerMerge.getLegalAmount());
			contractBom.setUnitWeight(bcsInnerMerge.getUnitWeight());
			contractBom.setIsMainImg(bcsInnerMerge.getIsMainImg());
			contractBom.setCountry(bcsInnerMerge.getCountry());
			contractBomList.add(contractBom);
		}
		this.contractDao.saveContractBom(contractBomList);
		return contractBomList;
	}

	/**
	 * 保存合同料件来自凭证料件
	 * 
	 * @param contract
	 *            合同表头
	 * @param innerMergeList
	 *            报关商品资料
	 * @return List 是ContractImg型，合同料件
	 */
	public List saveContractImg(Contract contract, List innerMergeList) {
		List contractImgList = new ArrayList();
		int maxSeqNum = this.contractDao.getMaxContractImgSeqNum(contract
				.getId()) + 1;
		for (int i = 0; i < innerMergeList.size(); i++) {
			ContractImg contractImg = new ContractImg();
			BcsTenInnerMerge bcsInnerMerge = (BcsTenInnerMerge) innerMergeList
					.get(i);

			contractImg.setSeqNum(maxSeqNum + i);
			contractImg.setCredenceNo(bcsInnerMerge.getSeqNum());
			contractImg.setCompany(CommonUtils.getCompany());
			contractImg.setContract(contract);
			contractImg.setName(bcsInnerMerge.getName());
			// contractImg.setDeclareState(DeclareState.APPLY_POR);
			contractImg.setSpec(bcsInnerMerge.getSpec());
			contractImg.setComplex(bcsInnerMerge.getComplex());
			contractImg.setAmount(0.0);
			contractImg.setUnit(bcsInnerMerge.getComUnit());
			// contractImg.setLegalUnit(bcsInnerMerge.getComplex() == null ?
			// null
			// : bcsInnerMerge.getComplex().getFirstUnit());
			contractImg.setLegalAmount(bcsInnerMerge.getLegalAmount());
			contractImg.setUnitWeight(bcsInnerMerge.getUnitWeight());
			contractImg.setIsMainImg(bcsInnerMerge.getIsMainImg());
			contractImg.setCountry(bcsInnerMerge.getCountry());
			contractImg.setModifyMark(ModifyMarkState.ADDED);
			contractImgList.add(contractImg);
		}
		this.contractDao.saveContractImg(contractImgList);
		this.statContractImgCount(contract);
		// this.statContractImgMoney(contract);
		return contractImgList;
	}

	/**
	 * 保存合同料件来自凭证料件
	 * 
	 * @param contract
	 *            合同表头
	 * @param bcsInnerMergeList
	 *            报关商品资料
	 * @return List 是ContractImg型，合同料件
	 */

	public List saveContractImgFromDictPorImg(Contract contract,
			List dictPorImgList) {
		List contractImgList = new ArrayList();
		int maxSeqNum = this.contractDao.getMaxContractImgSeqNum(contract
				.getId()) + 1;
		for (int i = 0; i < dictPorImgList.size(); i++) {
			ContractImg contractImg = new ContractImg();
			BcsDictPorImg bcsDictPorImg = (BcsDictPorImg) dictPorImgList.get(i);

			contractImg.setSeqNum(maxSeqNum + i);
			contractImg.setCredenceNo(bcsDictPorImg.getSeqNum());
			contractImg.setCompany(CommonUtils.getCompany());
			contractImg.setContract(contract);
			contractImg.setName(bcsDictPorImg.getCommName());
			// contractImg.setDeclareState(DeclareState.APPLY_POR);
			contractImg.setSpec(bcsDictPorImg.getCommSpec());
			contractImg.setComplex(bcsDictPorImg.getComplex());
			contractImg.setAmount(0.0);
			contractImg.setUnit(bcsDictPorImg.getComUnit());
			contractImg.setIsMainImg(bcsDictPorImg.getIsMainImg());
			contractImg.setDeclarePrice(bcsDictPorImg.getUnitPrice());
			// contractImg.setLegalUnit(bcsInnerMerge.getComplex() == null ?
			// null
			// : bcsInnerMerge.getComplex().getFirstUnit());

			// if (bcsInnerMerge.getType() != null) {
			// if (bcsInnerMerge.getType().equals("1")) { // 主料
			// contractImg.setMaterialType(MaterielType.MATERIEL);
			// } else {
			// contractImg
			// .setMaterialType(MaterielType.ASSISTANT_MATERIAL);
			// }
			// }
			// contractImg.setCountry(bcsInnerMerge.g);
			contractImg.setModifyMark(ModifyMarkState.ADDED);
			contractImgList.add(contractImg);
		}
		this.contractDao.saveContractImg(contractImgList);
		this.statContractImgCount(contract);
		// this.statContractImgMoney(contract);
		return contractImgList;
	}

	/**
	 * 保存合同Bom来自报关商品
	 * 
	 * @param contractExg
	 *            合同成品
	 * @param innerMergeList
	 *            合同料件
	 * @return List 是ContractBom型，合同Bom
	 */
	public List saveContractBomFromInnerMerge(ContractExg contractExg,
			List innerMergeList) {
		List contractBomList = new ArrayList();
		ContractImg contractImg = null;
		int maxSeqNum = this.contractDao.getMaxContractBomSeqNum(contractExg
				.getId()) + 1;
		for (int i = 0; i < innerMergeList.size(); i++) {
			BcsTenInnerMerge tenInnerMerge = (BcsTenInnerMerge) innerMergeList
					.get(i);
			List lsImg = this.contractDao.findContractImgByCredenceNo(
					contractExg.getContract(), tenInnerMerge.getSeqNum());
			if (lsImg.size() <= 0) {
				List lsTemp = new ArrayList();
				lsTemp.add(tenInnerMerge);
				lsTemp = this
						.saveContractImg(contractExg.getContract(), lsTemp);
				contractImg = (ContractImg) lsTemp.get(0);
			} else {
				contractImg = (ContractImg) lsImg.get(0);
			}
			ContractBom contractBom = new ContractBom();
			contractBom.setSeqNum(maxSeqNum + i);
			contractBom.setCompany(CommonUtils.getCompany());
			contractBom.setContractExg(contractExg);
			contractBom.setName(contractImg.getName());
			// contractBom.setDeclareState(DeclareState.APPLY_POR);
			contractBom.setSpec(contractImg.getSpec());
			contractBom.setComplex(contractImg.getComplex());
			contractBom.setUnit(contractImg.getUnit());
			// contractBom.setLegalUnit(contractImg.getLegalUnit());
			contractBom.setDeclarePrice(contractImg.getDeclarePrice());
			// contractBom.setLegalAmount(contractImg.getLegalAmount());
			contractBom.setUnitWeight(contractImg.getUnitWeight());
			contractBom.setIsMainImg(contractImg.getIsMainImg());
			contractBom.setCountry(contractImg.getCountry());
			// 2013-8-29
			contractBom.setContractImgSeqNum(contractImg.getSeqNum());
			// contractBom.setContractImgId(contractImg.getId());
			contractBom.setImgCredenceNo(contractImg.getCredenceNo());
			contractBom.setModifyMark(ModifyMarkState.ADDED);
			contractBomList.add(contractBom);
		}
		this.contractDao.saveContractBom(contractBomList);
		return contractBomList;
	}

	/**
	 * 保存合同Bom来自报关商品
	 * 
	 * @param contractExg
	 *            合同成品
	 * @param dictPorImgList
	 *            合同料件
	 * @return List 是ContractBom型，合同Bom
	 */
	public List saveContractBomFromDictPorImg(ContractExg contractExg,
			List dictPorImgList) {
		List contractBomList = new ArrayList();
		ContractImg contractImg = null;
		int maxSeqNum = this.contractDao.getMaxContractBomSeqNum(contractExg
				.getId()) + 1;
		for (int i = 0; i < dictPorImgList.size(); i++) {
			BcsDictPorImg bcsDictPorImg = (BcsDictPorImg) dictPorImgList.get(i);
			List lsImg = this.contractDao.findContractImgByCredenceNo(
					contractExg.getContract(), bcsDictPorImg.getSeqNum());
			if (lsImg.size() <= 0) {
				List lsTemp = new ArrayList();
				lsTemp.add(bcsDictPorImg);
				// 每次循环进入导致序号重复
				lsTemp = this.saveContractImgFromDictPorImg(
						contractExg.getContract(), lsTemp);
				contractImg = (ContractImg) lsTemp.get(0);
			} else {
				contractImg = (ContractImg) lsImg.get(0);
			}
			ContractBom contractBom = new ContractBom();
			contractBom.setSeqNum(maxSeqNum + i);
			contractBom.setCompany(CommonUtils.getCompany());
			contractBom.setContractExg(contractExg);
			contractBom.setName(contractImg.getName());
			// contractBom.setDeclareState(DeclareState.APPLY_POR);
			contractBom.setSpec(contractImg.getSpec());
			contractBom.setComplex(contractImg.getComplex());
			contractBom.setUnit(contractImg.getUnit());
			// contractBom.setLegalUnit(contractImg.getLegalUnit());
			contractBom.setDeclarePrice(contractImg.getDeclarePrice());
			// contractBom.setLegalAmount(contractImg.getLegalAmount());
			contractBom.setUnitWeight(contractImg.getUnitWeight());
			contractBom.setIsMainImg(contractImg.getIsMainImg());
			contractBom.setCountry(contractImg.getCountry());
			contractBom.setContractImgSeqNum(contractImg.getSeqNum());
			// contractBom.setContractImgId(contractImg.getId());
			contractBom.setImgCredenceNo(contractImg.getCredenceNo());
			contractBom.setModifyMark(ModifyMarkState.ADDED);
			contractBomList.add(contractBom);
		}
		this.contractDao.saveContractBom(contractBomList);
		return contractBomList;
	}

	/**
	 * 保存合同Bom来自凭料件
	 * 
	 * @param contractExg
	 *            合同成品
	 * @param contractImgList
	 *            合同料件
	 * @return List 是ContractBom型，合同Bom
	 */
	public List saveContractBomFromContractImg(ContractExg contractExg,
			List contractImgList) {
		List contractBomList = new ArrayList();
		int maxSeqNum = this.contractDao.getMaxContractBomSeqNum(contractExg
				.getId()) + 1;
		for (int i = 0; i < contractImgList.size(); i++) {
			ContractBom contractBom = new ContractBom();
			ContractImg contractImg = (ContractImg) contractImgList.get(i);
			contractBom.setSeqNum(maxSeqNum + i);
			contractBom.setCompany(CommonUtils.getCompany());
			contractBom.setContractExg(contractExg);
			contractBom.setName(contractImg.getName());
			// contractBom.setDeclareState(DeclareState.APPLY_POR);
			contractBom.setSpec(contractImg.getSpec());
			contractBom.setComplex(contractImg.getComplex());
			contractBom.setUnit(contractImg.getUnit());
			// contractBom.setLegalUnit(contractImg.getLegalUnit());
			contractBom.setDeclarePrice(contractImg.getDeclarePrice());
			// contractBom.setLegalAmount(contractImg.getLegalAmount());
			contractBom.setUnitWeight(contractImg.getUnitWeight());
			contractBom.setIsMainImg(contractImg.getIsMainImg());
			contractBom.setCountry(contractImg.getCountry());
			contractBom.setContractImgSeqNum(contractImg.getSeqNum());
			// contractBom.setContractImgId(contractImg.getId());
			contractBom.setImgCredenceNo(contractImg.getCredenceNo());
			contractBom.setModifyMark(ModifyMarkState.ADDED);
			contractBomList.add(contractBom);
		}
		this.contractDao.saveContractBom(contractBomList);
		return contractBomList;
	}

	/**
	 * 保存合同Bom资料
	 * 
	 * @param contractBom
	 *            新合同BOM
	 * @param oldCotnractBom
	 *            旧合同BOM
	 * @param isCalculateFinishProductSum
	 *            是否要反算成品总金额，true为要反算
	 * @param isCalculateMaterielSum
	 *            是否要反算成品总金额，true为要反算
	 */
	public void saveContractBom(ContractBom contractBom) {
		ContractBom oldBom = (ContractBom) this.contractDao.load(
				ContractBom.class, contractBom.getId());
		if (oldBom != null) {
			if (!CommonUtils.compareObject(oldBom.getUnitWaste(),
					contractBom.getUnitWaste())
					|| !CommonUtils.compareObject(oldBom.getWaste(),
							contractBom.getWaste())
					|| !CommonUtils.compareObject(oldBom.getUnitDosage(),
							contractBom.getUnitDosage())) {
				if (ModifyMarkState.UNCHANGE
						.equals(contractBom.getModifyMark())) {
					contractBom.setModifyMark(ModifyMarkState.MODIFIED);
				}
			}
		}
		/**
		 * 保存单耗资料
		 */
		this.contractDao.saveContractBom(contractBom);
		// if (isCalculateFinishProductSum == true) {
		BcsParameterSet parameter = this.contractDao.findBcsParameterSet();

		if (parameter != null
				&& parameter.updateContractBomWriteBackExg != null
				&& parameter.updateContractBomWriteBackExg) {
			ContractExg contractExg = contractBom.getContractExg();
			writeBackContractExg(contractExg);
		}
		// if (isCalculateMaterielSum == true) {

		if (parameter != null
				&& parameter.updateContractBomWriteBackImg != null
				&& parameter.updateContractBomWriteBackImg) {
			writeBackContractImg(contractBom.getContractExg().getContract()
					.getId(), contractBom);// ,
											// oldCotnractBom
		}
	}

	private int getPriceBitFromParaSet() {
		BcsParameterSet parameter = this.contractDao.findBcsParameterSet();
		return (parameter.getPriceBit() == null ? 5 : parameter.getPriceBit());
	}

	private int getCountBitFromParaSet() {
		BcsParameterSet parameter = this.contractDao.findBcsParameterSet();
		return (parameter.getCountBit() == null ? 5 : parameter.getCountBit());
	}

	private int getMoneyBitFromParaSet() {
		BcsParameterSet parameter = this.contractDao.findBcsParameterSet();
		return (parameter.getMoneyBit() == null ? 5 : parameter.getMoneyBit());
	}

	/**
	 * 保存合同成品来自统计
	 * 
	 * @param contractExg
	 *            合同成品
	 */
	private void writeBackContractExg(ContractExg contractExg) {
		/**
		 * 单价小数位控制
		 */
		int priceBit = getPriceBitFromParaSet();
		/**
		 * 数量小数位控制
		 */
		int countBit = getCountBitFromParaSet();
		/**
		 * 金额小数位控制
		 */
		int moneyBit = getMoneyBitFromParaSet();

		if (ModifyMarkState.DELETED.equals(contractExg.getModifyMark())) {
			return;
		}
		// ll
		Double materielFee = CommonUtils
				.getDoubleByDigit(
						this.getFinishProductMaterielFee(contractExg.getId()),
						moneyBit);
		// Double unitNetWeight = CommonUtils.getDoubleByDigit(this
		// .getFinishProductUnitWeight(contractExg.getId()), 4);

		// if (materielFee.equals(contractExg.getMaterialFee())) {
		contractExg.setMaterialFee(materielFee);
		// contractExg.setUnitNetWeight(unitNetWeight);
		/**
		 * 合计时 毛重 = 净重
		 */
		// contractExg.setUnitGrossWeight(unitNetWeight);
		/**
		 * 成品单价 = 原料费 + 加工费单价
		 */
		Double unitPrice = CommonUtils.getDoubleExceptNull(materielFee)
				+ CommonUtils.getDoubleExceptNull(contractExg
						.getProcessUnitPrice());
		contractExg.setUnitPrice(CommonUtils.getDoubleByDigit(unitPrice,
				priceBit));
		Double exportAmount = CommonUtils.getDoubleByDigit(
				CommonUtils.getDoubleExceptNull(contractExg.getExportAmount()),
				countBit);
		contractExg.setExportAmount(exportAmount);
		contractExg.setTotalPrice(CommonUtils.getDoubleByDigit(
				CommonUtils.getDoubleExceptNull(contractExg.getUnitPrice()
						* exportAmount), moneyBit));

		if (ModifyMarkState.UNCHANGE.equals(contractExg.getModifyMark())) {
			contractExg.setModifyMark(ModifyMarkState.MODIFIED);
		}
		this.contractDao.saveContractExg(contractExg);
		// this.statContractExgCount(contractExg.getContract());
		// ll
		this.statContractExgMoney(contractExg.getContract());
		// }
	}

	/**
	 * 合同料件数据取整
	 * 
	 * @param list
	 *            是ContractImg型，合同料件
	 */
	public void saveContractImgAmountInteger(List<ContractImg> list) {
		for (int i = 0; i < list.size(); i++) {
			ContractImg contractImg = (ContractImg) list.get(i);
			if (contractImg.getAmount() != null) {
				Double amount = Double.valueOf(String.valueOf(Math
						.round(contractImg.getAmount())));
				contractImg.setAmount(amount);
			}
			contractImg.setTotalPrice(CommonUtils
					.getDoubleExceptNull(contractImg.getAmount())
					* CommonUtils.getDoubleExceptNull(contractImg
							.getDeclarePrice()));
			// // if (contractImg.getTotalPrice() != null) {
			// // Double totalPrice = Double.valueOf(String.valueOf(Math
			// // .round(contractImg.getTotalPrice())));
			// // contractImg.setTotalPrice(totalPrice);
			// // }
			// this.contractDao.saveContractImg(contractImg);
			this.saveContractImg(contractImg);
		}
		if (list.size() > 0) {
			Contract contract = list.get(0).getContract();
			this.statContractImgMoney(contract);
		}
	}

	/**
	 * 保存料件总表记录
	 * 
	 * @param contractId
	 *            合同表头Id
	 * @param contractBom
	 *            合同BOM
	 */
	private void writeBackContractImg(String contractId, ContractBom contractBom) {// ,
		if (contractBom == null || contractBom.getContractImgSeqNum() == null) {
			return;
		}
		ContractImg contractImg = this.contractDao.findContractImg(contractId,
				contractBom.getContractImgSeqNum().toString());
		this.writeBackContractImg(contractId, contractImg);
	}

	/**
	 * 保存料件总表记录
	 * 
	 * @param contractId
	 *            合同表头Id
	 * @param contractBom
	 *            合同BOM
	 */
	private void writeBackContractImgByBom(String contractId,
			List<ContractBom> contractBoms) {

		List<Integer> imgSeqNums = new ArrayList<Integer>();
		for (int i = 0; i < contractBoms.size(); i++) {
			ContractBom contractBom = contractBoms.get(i);
			if (contractBom == null
					|| contractBom.getContractImgSeqNum() == null) {
				continue;
			}
			imgSeqNums.add(contractBom.getContractImgSeqNum());
		}

		List<ContractImg> contractImgs = this.contractDao.findContractImgs(
				contractId, imgSeqNums);
		Map<Integer, Double> mapAmount = getAmountMap(contractId, imgSeqNums);
		this.batchWriteBackContractImg(contractId, contractImgs, mapAmount);
	}

	/**
	 * 保存料件总表记录
	 * 
	 * @param contractId
	 *            合同表头Id
	 * @param contractImg
	 *            合同料件
	 */
	private void batchWriteBackContractImg(String contractId,
			List<ContractImg> contractImgs, Map<Integer, Double> mapAmount) {// ,
		for (int i = 0; i < contractImgs.size(); i++) {
			ContractImg contractImg = contractImgs.get(i);
			/**
			 * 单价小数位控制
			 */
			int priceBit = getPriceBitFromParaSet();
			/**
			 * 数量小数位控制
			 */
			int countBit = getCountBitFromParaSet();
			/**
			 * 金额小数位控制
			 */
			int moneyBit = getMoneyBitFromParaSet();

			if (contractImg == null
					|| contractImg.getSeqNum() == null
					|| ModifyMarkState.DELETED.equals(contractImg
							.getModifyMark())) {
				return;
			}
			/**
			 * 数量
			 */
			Double amount = mapAmount.get(contractImg.getSeqNum());
			contractImg.setAmount(CommonUtils
					.getDoubleByDigit(amount, countBit));

			/**
			 * 申报单价
			 */
			Double declarePrice = CommonUtils.getDoubleByDigit(CommonUtils
					.getDoubleExceptNull(contractImg.getDeclarePrice()),
					priceBit);
			contractImg.setDeclarePrice(declarePrice);
			/**
			 * 反算总金额
			 */

			contractImg.setTotalPrice(CommonUtils.getDoubleByDigit(
					CommonUtils.getDoubleExceptNull(contractImg.getAmount())
							* declarePrice, moneyBit));
			if (ModifyMarkState.UNCHANGE.equals(contractImg.getModifyMark())) {
				contractImg.setModifyMark(ModifyMarkState.MODIFIED);
			}
			/**
			 * 保存料件总表信息
			 */
			this.contractDao.saveContractImg(contractImg);
			this.statContractImgMoney(contractImg.getContract());
		}
	}

	private Map<Integer, Double> getAmountMap(String contractId,
			List<Integer> contractImgSeqNums) {
		Map<Integer, Double> mapAmount = new HashMap<Integer, Double>();
		List list = this.contractDao.findContractBomImgUsedAmounts(contractId,
				contractImgSeqNums);
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			if (obj[0] != null) {
				mapAmount.put(
						NumberUtils.toInt(obj[0].toString()),
						obj[1] == null ? 0.0 : NumberUtils.toDouble(obj[1]
								.toString()));
			}
		}
		return mapAmount;
	}

	/**
	 * 保存料件总表记录
	 * 
	 * @param contractId
	 *            合同表头Id
	 * @param contractImg
	 *            合同料件
	 */
	private void writeBackContractImg(String contractId, ContractImg contractImg) {// ,

		/**
		 * 单价小数位控制
		 */
		int priceBit = getPriceBitFromParaSet();
		/**
		 * 数量小数位控制
		 */
		int countBit = getCountBitFromParaSet();
		/**
		 * 金额小数位控制
		 */
		int moneyBit = getMoneyBitFromParaSet();

		if (contractImg == null || contractImg.getSeqNum() == null
				|| ModifyMarkState.DELETED.equals(contractImg.getModifyMark())) {
			return;
		}
		/**
		 * 数量
		 */
		Double amount = this.contractDao.findContractBomImgUsedAmount(
				contractId, contractImg.getSeqNum());
		// if (amount.equals(contractImg.getAmount())) {
		contractImg.setAmount(CommonUtils.getDoubleByDigit(amount, countBit));

		/**
		 * 申报单价
		 */
		Double declarePrice = CommonUtils.getDoubleByDigit(
				CommonUtils.getDoubleExceptNull(contractImg.getDeclarePrice()),
				priceBit);
		contractImg.setDeclarePrice(declarePrice);
		/**
		 * 反算总金额
		 */

		contractImg.setTotalPrice(CommonUtils.getDoubleByDigit(
				CommonUtils.getDoubleExceptNull(contractImg.getAmount())
						* declarePrice, moneyBit));
		if (ModifyMarkState.UNCHANGE.equals(contractImg.getModifyMark())) {
			contractImg.setModifyMark(ModifyMarkState.MODIFIED);
		}
		/**
		 * 保存料件总表信息
		 */
		this.contractDao.saveContractImg(contractImg);
		// ll
		// this.statContractImgCount(contractImg.getContract());
		this.statContractImgMoney(contractImg.getContract());
		// }
	}

	/**
	 * 删除合同BOM
	 * 
	 * @param list
	 *            是ContractBom型，合同BOM
	 * @param isCalculateFinishProductSum
	 *            是否要反算成品总金额，true为要反算
	 * @param isCalculateMaterielSum
	 *            是否要反算成品总金额，true为要反算
	 */
	public Map<Integer, List<ContractBom>> deleteContractBom(List list) {
		Map<Integer, List<ContractBom>> map = new HashMap<Integer, List<ContractBom>>();
		List<ContractBom> lsDelete = new ArrayList<ContractBom>();
		List<ContractBom> lsUpdate = new ArrayList<ContractBom>();
		if (list.size() <= 0) {
			return map;
		}
		ContractBom temp = ((ContractBom) list.get(0));
		ContractExg contractExg = temp.getContractExg();
		BcsParameterSet parameter = this.contractDao.findBcsParameterSet();
		//
		// 删除料件Bom记录
		//
		for (int i = 0; i < list.size(); i++) {
			ContractBom bom = (ContractBom) list.get(i);
			if (ModifyMarkState.ADDED.equals(bom.getModifyMark())) {
				this.contractDao.deleteContractBom(bom);
				lsDelete.add(bom);
			} else {
				bom.setModifyMark(ModifyMarkState.DELETED);
				this.contractDao.saveContractBom(bom);
				lsUpdate.add(bom);
			}
			if (parameter != null
					&& parameter.updateContractBomWriteBackImg != null
					&& parameter.updateContractBomWriteBackImg) {
				this.writeBackContractImg(bom.getContractExg().getContract()
						.getId(), bom);
			}
		}
		/**
		 * 重新计算成品
		 */
		if (parameter != null
				&& parameter.updateContractBomWriteBackExg != null
				&& parameter.updateContractBomWriteBackExg) {
			writeBackContractExg(contractExg);
		}
		Contract contract = contractExg.getContract();
		this.statContractImgMoney(contract);

		map.put(DeleteResultMark.DELETE, lsDelete);
		map.put(DeleteResultMark.UPDATE, lsUpdate);
		return map;
	}

	/**
	 * 删除单耗为空或者为零的记录
	 * 
	 * @param contract
	 */
	public void deleteContractBomUnitWasteIsNull(Contract contract) {
		List list = this.contractDao.findAddedContractBomByContract(contract
				.getId());
		for (int i = 0; i < list.size(); i++) {
			ContractBom bom = (ContractBom) list.get(i);
			if (bom.getUnitWaste() == null || bom.getUnitWaste() <= 0) {
				this.contractDao.deleteContractBom(bom);
			}
		}
	}

	/**
	 * 获得合同成品原料费用
	 * 
	 * @param contractExgId
	 *            合同成品Id
	 * @return Double 合同成品原料费用
	 */
	public Double getFinishProductMaterielFee(String contractExgId) {
		// ContractExg contractExg = this.contractDao
		// .findContractExgById(contractExgId);
		// double returnValue = this.contractDao
		// .findContractBomTotalPrice(contractExg.getId());
		// double finishProductQantity = contractExg.getExportAmount() == null ?
		// 0
		// : contractExg.getExportAmount().doubleValue();
		// if (finishProductQantity == 0) {
		// return 0.0;
		// }
		// return returnValue / finishProductQantity;
		List list = this.contractDao.findContractBomByExgId(contractExgId);
		double exgUnitPrice = 0.0;
		for (int i = 0; i < list.size(); i++) {
			ContractBom bom = (ContractBom) list.get(i);
			if (!ModifyMarkState.DELETED.equals(bom.getModifyMark())) {
				exgUnitPrice += CommonUtils.getDoubleExceptNull(bom
						.getDeclarePrice())
						* CommonUtils.getDoubleByDigit(
								CommonUtils.getDoubleByDigit(
										bom.getUnitWaste(), 9)
										/ (1 - CommonUtils.getDoubleByDigit(
												bom.getWaste(), 5) / 100.0), 5);
			}
		}
		return CommonUtils.getDoubleByDigit(exgUnitPrice, 5);
	}

	/**
	 * 获得合同成品单位净重
	 * 
	 * @param contractExgId
	 *            合同成品Id
	 * @return Double 合同成品单位净重
	 */
	public Double getFinishProductUnitWeight(String contractExgId) {
		double returnValue = 0;
		List list = this.contractDao.findContractBomByExgId(contractExgId);
		if (list == null || list.size() == 0) {
			return returnValue;
		}
		for (int i = 0; i < list.size(); i++) {
			ContractBom contractBom = (ContractBom) list.get(i);

			/**
			 * 单耗 * 单重
			 */
			if (!ModifyMarkState.DELETED.equals(contractBom.getModifyMark())) {
				double tempValue = (contractBom.getUnitWeight() == null ? 0
						: contractBom.getUnitWeight())
						* (contractBom.getUnitWaste() == null ? 0 : contractBom
								.getUnitWaste());
				returnValue += tempValue;
			}
		}
		return returnValue;
	}

	/**
	 * 获得合同单耗来自分页,并在打印报表的时候跟据页面大小分组
	 * 
	 * @param contractId
	 *            合同表头Id
	 * @param index
	 *            是开始下标
	 * @param length
	 *            数据长度
	 * @return List 是ContractUnitWaste型，合同成品对应料件单耗表数据
	 */
	public List<ContractUnitWaste> findContractUnitWaste(String contractId,
			int index, int length) {
		List<ContractUnitWaste> contractUnitWasteList = new ArrayList<ContractUnitWaste>();
		List<ContractExg> contractExgList = null;
		List<ContractImg> contractImgList = null;
		Map<String, ContractBom> contractBomMap = new HashMap<String, ContractBom>();
		List<ContractBom> contractBomList = null;
		final int columnCount = 6;

		contractExgList = this.contractDao.findContractExg(contractId, index,
				length);
		int contractExgCount = contractExgList.size();
		contractImgList = this.contractDao
				.findContractImgByContractId(contractId);
		contractBomList = this.contractDao
				.findContractBomByContractParentId(contractId);
		for (int i = 0; i < contractBomList.size(); i++) {
			ContractBom contractBom = (ContractBom) contractBomList.get(i);
			/**
			 * 合同成品id,编码相同,名称,规格,单位相同,单重 == key
			 */
			String key = contractBom.getContractExg().getId()
					+ (contractBom.getContractImgSeqNum() == null ? ""
							: contractBom.getContractImgSeqNum());
			contractBomMap.put(key, contractBom);
		}

		/**
		 * 获得交差数据表
		 */
		int groupCount = contractExgCount / columnCount
				+ ((contractExgCount % columnCount) > 0 ? 1 : 0);
		/**
		 * 以成品行数为6条记录进行--手动分组
		 */
		for (int g = 0; g < groupCount; g++) {
			/**
			 * 获取以列的个数分组的临时成品列表
			 */
			List<ContractExg> tempContractExgList = new ArrayList<ContractExg>();
			for (int i = g * columnCount; i < (g + 1) * columnCount; i++) {
				if (i < contractExgCount) {
					tempContractExgList.add(contractExgList.get(i));
				} else {
					break;
				}
			}
			for (int i = 0; i < contractImgList.size(); i++) {
				ContractImg contractImg = contractImgList.get(i);
				boolean isExist = false;
				ContractUnitWaste contractUnitWaste = new ContractUnitWaste();
				contractUnitWaste.setGroupId(g);
				contractUnitWaste.setPtNo(String.valueOf(contractImg
						.getSeqNum()));
				// if (contractImg.getSpec() != null &&
				// contractImg.getSpec().equals("")) {
				// contractUnitWaste.setPtName(contractImg.getName());
				// } else {
				// contractUnitWaste.setPtName(contractImg.getName() + "/" +
				// contractImg.getSpec());
				// }

				// 2014-05-22
				contractUnitWaste.setPtName((contractImg.getName() == null ? ""
						: contractImg.getName())
						+ (contractImg.getSpec() == null ? "" : "/"
								+ contractImg.getSpec()));
				for (int j = 0; j < tempContractExgList.size(); j++) {
					ContractExg contractExg = tempContractExgList.get(j);
					/**
					 * 合同成品id,编码相同,名称,规格,单位相同 == key
					 */
					String key = contractExg.getId()
							+ (contractImg.getSeqNum() == null ? ""
									: contractImg.getSeqNum());
					ContractBom contractBom = contractBomMap.get(key);
					Integer seqNum = contractExg.getSeqNum();
					switch (j) {
					case 0:
						contractUnitWaste.setExgSeqNum1(seqNum);
						break;
					case 1:
						contractUnitWaste.setExgSeqNum2(seqNum);
						break;
					case 2:
						contractUnitWaste.setExgSeqNum3(seqNum);
						break;
					case 3:
						contractUnitWaste.setExgSeqNum4(seqNum);
						break;
					case 4:
						contractUnitWaste.setExgSeqNum5(seqNum);
						break;
					case 5:
						contractUnitWaste.setExgSeqNum6(seqNum);
						break;
					}
					if (contractBom == null) {
						continue;
					}
					int digitNum = 9;
					double unitWaste = CommonUtils.getDoubleByDigit(
							contractBom.getUnitWaste(), digitNum);
					double waste = CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(contractBom
									.getWaste()) / 100.0, digitNum);
					switch (j) {
					case 0:
						contractUnitWaste.setUnitWaste1(unitWaste);
						contractUnitWaste.setWasteRate1(waste);
						isExist = true;
						break;
					case 1:
						contractUnitWaste.setUnitWaste2(unitWaste);
						contractUnitWaste.setWasteRate2(waste);
						isExist = true;
						break;
					case 2:
						contractUnitWaste.setUnitWaste3(unitWaste);
						contractUnitWaste.setWasteRate3(waste);
						isExist = true;
						break;
					case 3:
						contractUnitWaste.setUnitWaste4(unitWaste);
						contractUnitWaste.setWasteRate4(waste);
						isExist = true;
						break;
					case 4:
						contractUnitWaste.setUnitWaste5(unitWaste);
						contractUnitWaste.setWasteRate5(waste);
						isExist = true;
						break;
					case 5:
						contractUnitWaste.setUnitWaste6(unitWaste);
						contractUnitWaste.setWasteRate6(waste);
						isExist = true;
						break;
					}
				}
				if (isExist == true) {
					for (int k = 0; k < contractExgList.size(); k++) {
						ContractExg contractExg = contractExgList.get(k);
						switch (k) {
						case 0:
							contractUnitWaste
									.setExg1(contractExg.getSeqNum()
											+ ":"
											+ (contractExg.getName() != null ? contractExg
													.getName() : ""));
							break;
						case 1:
							contractUnitWaste
									.setExg2(contractExg.getSeqNum()
											+ ":"
											+ (contractExg.getName() != null ? contractExg
													.getName() : ""));
							break;
						case 2:
							contractUnitWaste
									.setExg3(contractExg.getSeqNum()
											+ ":"
											+ (contractExg.getName() != null ? contractExg
													.getName() : ""));
							break;
						case 3:
							contractUnitWaste
									.setExg4(contractExg.getSeqNum()
											+ ":"
											+ (contractExg.getName() != null ? contractExg
													.getName() : ""));
							break;
						case 4:
							contractUnitWaste
									.setExg5(contractExg.getSeqNum()
											+ ":"
											+ (contractExg.getName() != null ? contractExg
													.getName() : ""));
							break;
						case 5:
							contractUnitWaste
									.setExg6(contractExg.getSeqNum()
											+ ":"
											+ (contractExg.getName() != null ? contractExg
													.getName() : ""));
							break;
						}
					}
					contractUnitWasteList.add(contractUnitWaste);
				}
			}
		}
		return contractUnitWasteList;
	}

	/**
	 * 获得合同单耗来自分页,并在打印报表的时候跟据页面大小分组
	 * 
	 * @param contractId
	 *            合同表头Id
	 * 
	 * @return List 是ContractUnitWaste型，合同成品对应料件单耗表数据
	 */
	public List<ContractUnitWaste> findContractUnitWasteAll(String contractId) {
		List<ContractUnitWaste> contractUnitWasteList = new ArrayList<ContractUnitWaste>();
		List<ContractExg> contractExgList = null;
		List<ContractImg> contractImgList = null;
		Map<String, ContractBom> contractBomMap = new HashMap<String, ContractBom>();
		List<ContractBom> contractBomList = null;
		final int columnCount = 6;
		contractExgList = this.contractDao.findContractExg(contractId);
		int contractExgCount = contractExgList.size();
		contractImgList = this.contractDao
				.findContractImgByContractId(contractId);
		contractBomList = this.contractDao
				.findContractBomByContractParentId(contractId);
		for (int i = 0; i < contractBomList.size(); i++) {
			ContractBom contractBom = (ContractBom) contractBomList.get(i);
			/**
			 * 合同成品id,编码相同,名称,规格,单位相同,单重 == key
			 */
			String key = contractBom.getContractExg().getId()
					+ (contractBom.getContractImgSeqNum() == null ? ""
							: contractBom.getContractImgSeqNum());
			contractBomMap.put(key, contractBom);
		}

		/**
		 * 获得交差数据表
		 */
		int groupCount = contractExgCount / columnCount
				+ ((contractExgCount % columnCount) > 0 ? 1 : 0);
		/**
		 * 以成品行数为6条记录进行--手动分组
		 */
		for (int g = 0; g < groupCount; g++) {
			/**
			 * 获取以列的个数分组的临时成品列表
			 */
			List<ContractExg> tempContractExgList = new ArrayList<ContractExg>();
			for (int i = g * columnCount; i < (g + 1) * columnCount; i++) {
				if (i < contractExgCount) {
					tempContractExgList.add(contractExgList.get(i));
				} else {
					break;
				}
			}
			for (int i = 0; i < contractImgList.size(); i++) {
				ContractImg contractImg = contractImgList.get(i);
				boolean isExist = false;
				ContractUnitWaste contractUnitWaste = new ContractUnitWaste();
				contractUnitWaste.setGroupId(g);
				contractUnitWaste.setPtNo(String.valueOf(contractImg
						.getSeqNum()));
				if (contractImg.getSpec() != null
						&& contractImg.getSpec().equals("")) {
					contractUnitWaste.setPtName(contractImg.getName());
				} else {
					contractUnitWaste.setPtName(contractImg.getName() + "/"
							+ contractImg.getSpec());
				}

				for (int j = 0; j < tempContractExgList.size(); j++) {
					ContractExg contractExg = tempContractExgList.get(j);
					/**
					 * 合同成品id,编码相同,名称,规格,单位相同 == key
					 */
					String key = contractExg.getId()
							+ (contractImg.getSeqNum() == null ? ""
									: contractImg.getSeqNum());
					ContractBom contractBom = contractBomMap.get(key);
					Integer seqNum = contractExg.getSeqNum();
					switch (j) {
					case 0:
						contractUnitWaste.setExgSeqNum1(seqNum);
						break;
					case 1:
						contractUnitWaste.setExgSeqNum2(seqNum);
						break;
					case 2:
						contractUnitWaste.setExgSeqNum3(seqNum);
						break;
					case 3:
						contractUnitWaste.setExgSeqNum4(seqNum);
						break;
					case 4:
						contractUnitWaste.setExgSeqNum5(seqNum);
						break;
					case 5:
						contractUnitWaste.setExgSeqNum6(seqNum);
						break;
					}
					if (contractBom == null) {
						continue;
					}
					int digitNum = 9;
					double unitWaste = CommonUtils.getDoubleByDigit(
							contractBom.getUnitWaste(), digitNum);
					double waste = CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(contractBom
									.getWaste()) / 100.0, digitNum);
					switch (j) {
					case 0:
						contractUnitWaste.setUnitWaste1(unitWaste);
						contractUnitWaste.setWasteRate1(waste);
						isExist = true;
						break;
					case 1:
						contractUnitWaste.setUnitWaste2(unitWaste);
						contractUnitWaste.setWasteRate2(waste);
						isExist = true;
						break;
					case 2:
						contractUnitWaste.setUnitWaste3(unitWaste);
						contractUnitWaste.setWasteRate3(waste);
						isExist = true;
						break;
					case 3:
						contractUnitWaste.setUnitWaste4(unitWaste);
						contractUnitWaste.setWasteRate4(waste);
						isExist = true;
						break;
					case 4:
						contractUnitWaste.setUnitWaste5(unitWaste);
						contractUnitWaste.setWasteRate5(waste);
						isExist = true;
						break;
					case 5:
						contractUnitWaste.setUnitWaste6(unitWaste);
						contractUnitWaste.setWasteRate6(waste);
						isExist = true;
						break;
					}
				}
				if (isExist == true) {
					for (int k = 0; k < contractExgList.size(); k++) {
						ContractExg contractExg = contractExgList.get(k);
						switch (k) {
						case 0:
							contractUnitWaste
									.setExg1(contractExg.getSeqNum()
											+ ":"
											+ (contractExg.getName() != null ? contractExg
													.getName() : ""));
							break;
						case 1:
							contractUnitWaste
									.setExg2(contractExg.getSeqNum()
											+ ":"
											+ (contractExg.getName() != null ? contractExg
													.getName() : ""));
							break;
						case 2:
							contractUnitWaste
									.setExg3(contractExg.getSeqNum()
											+ ":"
											+ (contractExg.getName() != null ? contractExg
													.getName() : ""));
							break;
						case 3:
							contractUnitWaste
									.setExg4(contractExg.getSeqNum()
											+ ":"
											+ (contractExg.getName() != null ? contractExg
													.getName() : ""));
							break;
						case 4:
							contractUnitWaste
									.setExg5(contractExg.getSeqNum()
											+ ":"
											+ (contractExg.getName() != null ? contractExg
													.getName() : ""));
							break;
						case 5:
							contractUnitWaste
									.setExg6(contractExg.getSeqNum()
											+ ":"
											+ (contractExg.getName() != null ? contractExg
													.getName() : ""));
							break;
						}
					}
					contractUnitWasteList.add(contractUnitWaste);
				}
			}
		}
		return contractUnitWasteList;
	}

	/**
	 * 获得合同单耗--(不分页)
	 * 
	 * @param contractId
	 *            合同表头Id
	 * 
	 * @return List 是ContractUnitWaste型，合同成品对应料件单耗表数据
	 */
	public List<ImgExgObject> findContractUnitWasteAllNoGroup(String contractId) {
		List<ImgExgObject> imgExgObjectList = new ArrayList<ImgExgObject>();
		Object[] exgObj;// 成品--表头
		Object[][] imgObj;// 料件--表体
		List<ContractExg> contractExgList = null;
		List<ContractImg> contractImgList = null;
		Map<String, ContractBom> contractBomMap = new HashMap<String, ContractBom>();
		List<ContractBom> contractBomList = null;
		contractExgList = this.contractDao.findContractExg(contractId);
		int contractExgCount = contractExgList.size();
		exgObj = new Object[contractExgCount * 2];// 成品数量
		final int columnCount = contractExgCount;
		contractImgList = this.contractDao
				.findContractImgByContractId(contractId);
		int contractImgCount = contractImgList.size();// 料件数量
		imgObj = new Object[contractImgCount][contractExgCount * 2 + 3];
		contractBomList = this.contractDao
				.findContractBomByContractParentId(contractId);
		for (int i = 0; i < contractBomList.size(); i++) {
			ContractBom contractBom = (ContractBom) contractBomList.get(i);
			/**
			 * 合同成品id,编码相同,名称,规格,单位相同,单重 == key
			 */
			String key = contractBom.getContractExg().getId()
					+ (contractBom.getContractImgSeqNum() == null ? ""
							: contractBom.getContractImgSeqNum());
			contractBomMap.put(key, contractBom);
		}
		/**
		 * 获得交差数据表
		 */
		int groupCount = contractExgCount / columnCount
				+ ((contractExgCount % columnCount) > 0 ? 1 : 0);
		/**
		 * 以成品行数为columnCount条记录进行--手动分组
		 */
		for (int g = 0; g < groupCount; g++) {
			/**
			 * 获取以列的个数分组的临时成品列表
			 */
			List<ContractExg> tempContractExgList = new ArrayList<ContractExg>();
			for (int i = g * columnCount; i < (g + 1) * columnCount; i++) {
				if (i < contractExgCount) {
					tempContractExgList.add(contractExgList.get(i));
				} else {
					break;
				}
			}
			for (int i = 0; i < contractImgList.size(); i++) {
				ContractImg contractImg = contractImgList.get(i);
				boolean isExist = false;
				imgObj[i][0] = String
						.valueOf(contractImg.getSeqNum() == null ? 0
								: contractImg.getSeqNum());
				if (contractImg.getSpec() != null
						&& contractImg.getSpec().equals("")) {
					imgObj[i][1] = contractImg.getName();// i行1列存放商品名称
				} else {
					imgObj[i][1] = contractImg.getName() + "/"// i行1列或存放商品规格
							+ contractImg.getSpec();
				}
				for (int j = 0; j < tempContractExgList.size(); j++) {
					ContractExg contractExg = tempContractExgList.get(j);
					/**
					 * 合同成品id,编码相同,名称,规格,单位相同 == key
					 */
					String key = contractExg.getId()
							+ (contractImg.getSeqNum() == null ? ""
									: contractImg.getSeqNum());
					ContractBom contractBom = contractBomMap.get(key);
					Integer seqNum = contractExg.getSeqNum();
					exgObj[j * 2] = seqNum;// 偶数行存放成品序号
					if (contractBom == null) {
						continue;
					}
					int digitNum = 9;
					double unitWaste = CommonUtils.getDoubleByDigit(
							contractBom.getUnitWaste(), digitNum);
					double waste = CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(contractBom
									.getWaste()) / 100.0, digitNum);
					imgObj[i][j * 2 + 2] = unitWaste;// 从第2列开始偶数行存放单耗，奇数行存放损耗
					imgObj[i][j * 2 + 3] = waste;// 从第2列开始偶数行存放单耗，奇数行存放损耗
					isExist = true;
				}
				imgObj[i][tempContractExgList.size() * 2 + 2] = g;
				if (isExist == true) {
					for (int k = 0; k < contractExgList.size(); k++) {
						ContractExg contractExg = contractExgList.get(k);
						exgObj[k * 2 + 1] = contractExg.getSeqNum()
								+ ":"
								+ (contractExg.getName() != null ? contractExg
										.getName() : "");
					}
				}
			}
			ImgExgObject imgExgObj = new ImgExgObject();
			imgExgObj.setImgObj(imgObj);
			imgExgObj.setExgObj(exgObj);
			imgExgObjectList.add(imgExgObj);
		}
		return imgExgObjectList;
	}

	/**
	 * 获得合同除未修改单耗来自分页,并在打印报表的时候跟据页面大小分组
	 * 
	 * @param contractId
	 *            合同表头Id
	 * @param index
	 *            是开始下标
	 * @param length
	 *            数据长度
	 * @return List 是ContractUnitWaste型，合同成品对应料件单耗表数据
	 */
	public List<ContractUnitWaste> findContractUnitWasteByModifyMark(
			Request request, String contractId, int index, int length) {
		List<ContractUnitWaste> contractUnitWasteList = new ArrayList<ContractUnitWaste>();
		List<ContractExg> contractExgList = null;
		List<ContractImg> contractImgList = null;
		Map<String, ContractBom> contractBomMap = new HashMap<String, ContractBom>();
		// 已修改BOM料件与成品对应列表
		List<ContractBom> contractBomList = null;
		final int columnCount = 6;
		// 被修改BOM成品列表
		contractExgList = this.contractDao.findContractChangedExg(contractId,
				index, length);
		int contractExgCount = contractExgList.size();
		// 合同料件列表
		contractImgList = this.contractDao
				.findContractImgByContractId(contractId);
		// 已修改BOM列表
		contractBomList = this.contractDao
				.findModifyContractBomByContractParentId(contractId);
		// 计算已修改BOM料件与成品对应列表
		for (int i = 0; i < contractBomList.size(); i++) {
			ContractBom contractBom = (ContractBom) contractBomList.get(i);
			/**
			 * 合同成品id,编码相同,名称,规格,单位相同,单重 == key
			 */
			String key = contractBom.getContractExg().getId()
					+ (contractBom.getContractImgSeqNum() == null ? ""
							: contractBom.getContractImgSeqNum());
			contractBomMap.put(key, contractBom);
		}

		/**
		 * 获得交差数据表
		 */
		int groupCount = contractExgCount / columnCount
				+ ((contractExgCount % columnCount) > 0 ? 1 : 0);
		/**
		 * 以成品行数为6条成品记录进行--手动分组
		 */
		for (int g = 0; g < groupCount; g++) {
			/**
			 * 获取以列的个数分组的临时成品列表
			 */
			List<ContractExg> tempContractExgList = new ArrayList<ContractExg>();
			for (int i = g * columnCount; i < (g + 1) * columnCount; i++) {
				if (i < contractExgCount) {
					tempContractExgList.add(contractExgList.get(i));
				} else {
					break;
				}
			}
			for (int i = 0; i < contractImgList.size(); i++) {
				ContractImg contractImg = contractImgList.get(i);
				boolean isExist = false;
				ContractUnitWaste contractUnitWaste = new ContractUnitWaste();
				contractUnitWaste.setGroupId(g);
				contractUnitWaste.setPtNo(String.valueOf(contractImg
						.getSeqNum()));
				if (contractImg.getSpec() != null
						&& contractImg.getSpec().equals("")) {
					contractUnitWaste.setPtName(contractImg.getName());
				} else {
					contractUnitWaste.setPtName(contractImg.getName() + "/"
							+ contractImg.getSpec());
				}

				for (int j = 0; j < tempContractExgList.size(); j++) {
					ContractExg contractExg = tempContractExgList.get(j);
					/**
					 * 合同成品id,编码相同,名称,规格,单位相同 == key
					 */
					String key = contractExg.getId()
							+ (contractImg.getSeqNum() == null ? ""
									: contractImg.getSeqNum());
					ContractBom contractBom = contractBomMap.get(key);
					Integer seqNum = contractExg.getSeqNum();
					switch (j) {
					case 0:
						contractUnitWaste.setExgSeqNum1(seqNum);
						break;
					case 1:
						contractUnitWaste.setExgSeqNum2(seqNum);
						break;
					case 2:
						contractUnitWaste.setExgSeqNum3(seqNum);
						break;
					case 3:
						contractUnitWaste.setExgSeqNum4(seqNum);
						break;
					case 4:
						contractUnitWaste.setExgSeqNum5(seqNum);
						break;
					case 5:
						contractUnitWaste.setExgSeqNum6(seqNum);
						break;
					}
					if (contractBom == null) {
						continue;
					}
					int digitNum = 9;
					double unitWaste = CommonUtils.getDoubleByDigit(
							contractBom.getUnitWaste(), digitNum);
					double waste = CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(contractBom
									.getWaste()) / 100.0, digitNum);
					switch (j) {
					case 0:
						contractUnitWaste.setUnitWaste1(unitWaste);
						contractUnitWaste.setWasteRate1(waste);
						isExist = true;
						break;
					case 1:
						contractUnitWaste.setUnitWaste2(unitWaste);
						contractUnitWaste.setWasteRate2(waste);
						isExist = true;
						break;
					case 2:
						contractUnitWaste.setUnitWaste3(unitWaste);
						contractUnitWaste.setWasteRate3(waste);
						isExist = true;
						break;
					case 3:
						contractUnitWaste.setUnitWaste4(unitWaste);
						contractUnitWaste.setWasteRate4(waste);
						isExist = true;
						break;
					case 4:
						contractUnitWaste.setUnitWaste5(unitWaste);
						contractUnitWaste.setWasteRate5(waste);
						isExist = true;
						break;
					case 5:
						contractUnitWaste.setUnitWaste6(unitWaste);
						contractUnitWaste.setWasteRate6(waste);
						isExist = true;
						break;
					}
				}
				if (isExist == true) {
					for (int k = 0; k < contractExgList.size(); k++) {
						ContractExg contractExg = contractExgList.get(k);
						switch (k) {
						case 0:
							contractUnitWaste
									.setExg1(contractExg.getSeqNum()
											+ ":"
											+ (contractExg.getName() != null ? contractExg
													.getName() : ""));
							break;
						case 1:
							contractUnitWaste
									.setExg2(contractExg.getSeqNum()
											+ ":"
											+ (contractExg.getName() != null ? contractExg
													.getName() : ""));

							break;
						case 2:
							contractUnitWaste
									.setExg3(contractExg.getSeqNum()
											+ ":"
											+ (contractExg.getName() != null ? contractExg
													.getName() : ""));
							break;
						case 3:
							contractUnitWaste
									.setExg4(contractExg.getSeqNum()
											+ ":"
											+ (contractExg.getName() != null ? contractExg
													.getName() : ""));
							break;
						case 4:
							contractUnitWaste
									.setExg5(contractExg.getSeqNum()
											+ ":"
											+ (contractExg.getName() != null ? contractExg
													.getName() : ""));
							break;
						case 5:
							contractUnitWaste
									.setExg6(contractExg.getSeqNum()
											+ ":"
											+ (contractExg.getName() != null ? contractExg
													.getName() : ""));
							break;
						}
					}
					contractUnitWasteList.add(contractUnitWaste);
				}
			}
		}
		return contractUnitWasteList;
	}

	/**
	 * 获得合同单耗来自分页,并在打印报表的时候跟据页面大小分组
	 * 
	 * @param contractId
	 *            合同表头Id
	 * @param index
	 *            是开始下标
	 * @param length
	 *            数据长度
	 * @return List 是ContractUnitWaste型，合同成品对应料件单耗表数据
	 */
	public List<ContractUnitWaste> findContractUnitWasteChange(
			String contractId, int index, int length) {
		List contractUnitWasteList = new ArrayList();
		List<ContractExg> contractExgList = new ArrayList();
		List<ContractImg> contractImgList = new ArrayList();
		Map<String, ContractBom> contractBomMap = new HashMap<String, ContractBom>();
		List<ContractBom> contractBomList = null;
		final int columnCount = 6;
		String exingContractId = this.contractDao
				.findExingContractByChangeId(contractId);
		// -----------------------------------------------------------
		List bomList = this.contractDao
				.findContractBomByContractId(exingContractId);
		Set set = new HashSet();
		for (int i = 0; i < bomList.size(); i++) {
			ContractBom contractBom = (ContractBom) bomList.get(i);
			String keyval = (contractBom.getContractExg() == null ? ""
					: contractBom.getContractExg().getSeqNum())
					+ "/"
					+ (contractBom.getContractImgSeqNum() == null ? ""
							: contractBom.getContractImgSeqNum().toString())
					+ (contractBom.getUnitWaste() == null ? "" : contractBom
							.getUnitWaste().toString())
					+ "/"
					+ (contractBom.getWaste() == null ? "" : contractBom
							.getWaste().toString());
			set.add(keyval);
		}
		// -----------------------------------------------------------
		contractBomList = this.contractDao
				.findContractBomByContractParentIdChange(contractId);
		List seqExg = new ArrayList();
		Map seqExga = new HashMap();
		Set seqImg = new HashSet();
		for (int m = 0; m < contractBomList.size(); m++) {
			ContractBom contractBom = (ContractBom) contractBomList.get(m);
			seqExga.put(contractBom.getContractExg().getSeqNum(),
					contractBom.getContractExg());
			seqImg.add(contractBom.getContractImgSeqNum());
		}
		contractExgList = new ArrayList(seqExga.values());
		Collections.sort(contractExgList);
		int contractExgCount = contractExgList.size();
		for (int i = 0; i < contractExgList.size(); i++) {
			seqExg.add(contractExgList.get(i).getSeqNum());
		}
		// -----------------------------------------------------------
		contractImgList = this.contractDao
				.findContractImgByContractIdChange(contractId);
		for (int k = 0; k < contractImgList.size(); k++) {
			ContractImg img = (ContractImg) contractImgList.get(k);
			if (!seqImg.contains(img.getSeqNum())) {
				contractImgList.remove(img);
			}
		}
		// -----------------------------------------------------------
		for (int i = 0; i < contractBomList.size(); i++) {
			ContractBom contractBom = (ContractBom) contractBomList.get(i);
			/**
			 * 合同成品id,编码相同,名称,规格,单位相同,单重 == key
			 */
			String key = contractBom.getContractExg().getId()
					+ (contractBom.getContractImgSeqNum() == null ? ""
							: contractBom.getContractImgSeqNum());
			contractBomMap.put(key, contractBom);
		}
		// -----------------------------------------------------------

		/**
		 * 获得交差数据表
		 */
		int groupCount = contractExgCount / columnCount
				+ ((contractExgCount % columnCount) > 0 ? 1 : 0);
		int[][] its = new int[groupCount][1];
		/**
		 * 以成品行数为6条记录进行--手动分组
		 */
		for (int g = 0; g < groupCount; g++) {
			/**
			 * 获取以列的个数分组的临时成品列表
			 */
			List<ContractExg> tempContractExgList = new ArrayList<ContractExg>();
			for (int i = g * columnCount; i < (g + 1) * columnCount; i++) {
				if (i < contractExgCount) {
					tempContractExgList.add(contractExgList.get(i));
				} else {
					break;
				}
			}
			for (int i = 0; i < contractImgList.size(); i++) {
				ContractImg contractImg = contractImgList.get(i);
				boolean isExist = false;
				ContractUnitWaste contractUnitWaste = new ContractUnitWaste();
				contractUnitWaste.setGroupId(g);
				contractUnitWaste.setPtNo(String.valueOf(contractImg
						.getSeqNum()));
				contractUnitWaste.setPtName(contractImg.getName());
				for (int j = 0; j < tempContractExgList.size(); j++) {
					ContractExg contractExg = tempContractExgList.get(j);
					contractUnitWaste.setExgSeqNum(contractExg.getSeqNum());
					/**
					 * 合同成品id,编码相同,名称,规格,单位相同 == key
					 */
					String key = contractExg.getId()
							+ (contractImg.getSeqNum() == null ? ""
									: contractImg.getSeqNum());
					ContractBom contractBom = contractBomMap.get(key);
					if (contractBom == null) {
						continue;
					}
					String keyval = (contractBom.getContractExg() == null ? ""
							: contractBom.getContractExg().getSeqNum())
							+ "/"
							+ (contractBom.getContractImgSeqNum() == null ? ""
									: contractBom.getContractImgSeqNum()
											.toString())
							+ (contractBom.getUnitWaste() == null ? ""
									: contractBom.getUnitWaste().toString())
							+ "/"
							+ (contractBom.getWaste() == null ? ""
									: contractBom.getWaste().toString());
					// if()
					if (set.contains(keyval)) {
						continue;// 说明单耗和损耗没有任何改变
					}

					int digitNum = 9;
					double unitWaste = CommonUtils.getDoubleByDigit(
							contractBom.getUnitWaste(), digitNum);
					double waste = CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(contractBom
									.getWaste()) / 100.0, digitNum);
					switch (j) {
					case 0:
						contractUnitWaste.setUnitWaste1(unitWaste);
						contractUnitWaste.setWasteRate1(waste);
						isExist = true;
						break;
					case 1:
						contractUnitWaste.setUnitWaste2(unitWaste);
						contractUnitWaste.setWasteRate2(waste);
						isExist = true;
						break;
					case 2:
						contractUnitWaste.setUnitWaste3(unitWaste);
						contractUnitWaste.setWasteRate3(waste);
						isExist = true;
						break;
					case 3:
						contractUnitWaste.setUnitWaste4(unitWaste);
						contractUnitWaste.setWasteRate4(waste);
						isExist = true;
						break;
					case 4:
						contractUnitWaste.setUnitWaste5(unitWaste);
						contractUnitWaste.setWasteRate5(waste);
						isExist = true;
						break;
					case 5:
						contractUnitWaste.setUnitWaste6(unitWaste);
						contractUnitWaste.setWasteRate6(waste);
						isExist = true;
						break;
					}
				}

				if (isExist == true) {
					its[g][0] += 1;
					contractUnitWasteList.add(contractUnitWaste);
				}
			}
		}

		int adm = 6 - (seqExg.size() % 6 == 0 ? 6 : seqExg.size() % 6);
		List tempList = new ArrayList();
		for (int k = 0; k < groupCount; k++) {
			int count = its[k][0] / 21 + ((its[k][0] % 21) > 0 ? 1 : 0);
			for (int j = 0; j < count; j++) {
				for (int m = k * 6; m < k * 6 + 6; m++) {
					if (m == seqExg.size()) {
						for (int s = 0; s < adm; s++) {
							tempList.add("");
						}
						break;
					}
					tempList.add(seqExg.get(m));
				}
			}

		}
		contractUnitWasteList.add(tempList);
		return contractUnitWasteList;
	}

	/**
	 * 保存合同料件
	 * 
	 * @param contractImg
	 *            合同料件
	 */
	public void saveContractImg(ContractImg contractImg) {
		this.contractDao.saveContractImg(contractImg);
		if (contractImg.getId() != null && !"".equals(contractImg.getId())) {
			List list = this.contractDao
					.findContractBomByImgSeqNum(contractImg);
			for (int i = 0; i < list.size(); i++) {
				ContractBom contractBom = (ContractBom) list.get(i);
				contractBom.setName(contractImg.getName());
				contractBom.setSpec(contractImg.getSpec());
				contractBom.setComplex(contractImg.getComplex());
				contractBom.setUnit(contractImg.getUnit());
				contractBom.setUnitWeight(contractImg.getUnitWeight());
				contractBom.setIsMainImg(contractImg.getIsMainImg());
				contractBom.setCountry(contractImg.getCountry());
				contractBom.setDeclarePrice(contractImg.getDeclarePrice());
				contractBom.setTotalPrice(CommonUtils
						.getDoubleExceptNull(contractBom.getAmount())
						* CommonUtils.getDoubleExceptNull(contractBom
								.getDeclarePrice()));
				this.contractDao.saveContractBom(contractBom);
				BcsParameterSet parameter = this.contractDao
						.findBcsParameterSet();
				if (parameter != null
						&& parameter.updateContractBomWriteBackExg != null
						&& parameter.updateContractBomWriteBackExg) {
					ContractExg contractExg = contractBom.getContractExg();
					writeBackContractExg(contractExg);
				}
			}
		}
		Contract contract = contractImg.getContract();
		this.statContractImgMoney(contract);
	}

	/**
	 * 变更料件的商品编码
	 * 
	 * @param contractImg
	 * @param complex
	 */
	private void changeContractImgComplex(ContractImg contractImg) {
		if (contractImg.getId() != null && !"".equals(contractImg.getId())) {
			// List list = this.contractDao
			// .findContractBomByImgSeqNum(contractImg);
			// for (int i = 0; i < list.size(); i++) {
			// ContractBom contractBom = (ContractBom) list.get(i);
			// contractBom.setComplex(contractImg.getComplex());
			// this.contractDao.saveContractBom(contractBom);
			// }
			List list = this.contractDao.findBcsCustomsDeclarationCommInfo(
					true, contractImg.getContract(), contractImg.getSeqNum());
			for (int i = 0; i < list.size(); i++) {
				BcsCustomsDeclarationCommInfo commInfo = (BcsCustomsDeclarationCommInfo) list
						.get(i);
				commInfo.setComplex(contractImg.getComplex());
				this.contractDao.saveOrUpdate(commInfo);
			}
		}
		// contractImg.setComplex(complex);
		// this.contractDao.saveContractImg(contractImg);
	}

	/**
	 * 保存合同料件
	 * 
	 * @param contractImg
	 *            合同料件
	 */
	public void saveContractImg(ContractImg contractImg,
			List<ContractBom> lsContractBom) {
		if (contractImg.getId() == null) {
			this.contractDao.saveContractImg(contractImg);
		} else {
			List list = this.contractDao
					.findContractBomByImgSeqNum(contractImg);
			for (int i = 0; i < list.size(); i++) {
				ContractBom contractBom = (ContractBom) list.get(i);
				contractBom.setName(contractImg.getName());
				contractBom.setSpec(contractImg.getSpec());
				contractBom.setComplex(contractImg.getComplex());
				contractBom.setUnit(contractImg.getUnit());
				contractBom.setUnitWeight(contractImg.getUnitWeight());
				contractBom.setIsMainImg(contractImg.getIsMainImg());
				contractBom.setCountry(contractImg.getCountry());
				contractBom.setDeclarePrice(contractImg.getDeclarePrice());
				contractBom.setTotalPrice(CommonUtils
						.getDoubleExceptNull(contractBom.getAmount())
						* CommonUtils.getDoubleExceptNull(contractBom
								.getDeclarePrice()));
				this.contractDao.saveContractBom(contractBom);
				BcsParameterSet parameter = this.contractDao
						.findBcsParameterSet();
				if (parameter != null
						&& parameter.updateContractBomWriteBackExg != null
						&& parameter.updateContractBomWriteBackExg) {
					ContractExg contractExg = contractBom.getContractExg();
					writeBackContractExg(contractExg);
				}
			}
			BcsParameterSet parameter = this.contractDao.findBcsParameterSet();
			if (parameter != null
					&& parameter.updateContractImgWriteBackBomExg != null
					&& parameter.updateContractImgWriteBackBomExg) {
				this.writeBackContractBomExg(contractImg, lsContractBom);
			}
			this.contractDao.saveContractImg(contractImg);
			Contract contract = contractImg.getContract();
			this.statContractImgMoney(contract);
		}
	}

	/**
	 * 修改料件时，反写ＢＯＭ物料数量，成品单价金额
	 * 
	 * @param contractImg
	 * @param lsContractBom
	 */
	public void writeBackContractBomExg(ContractImg contractImg,
			List<ContractBom> lsContractBom) {
		// this.contractDao.findContractImgByEmsNo()
		ContractImg oldImg = (ContractImg) this.contractDao.load(
				ContractImg.class, contractImg.getId());
		double diffAmount = CommonUtils.getDoubleExceptNull(contractImg
				.getAmount())
				- CommonUtils.getDoubleExceptNull(oldImg.getAmount());
		if (diffAmount == 0) {
			return;
		}
		double totalUsedAmount = 0.0;
		for (ContractBom contractBom : lsContractBom) {
			totalUsedAmount += CommonUtils.getDoubleExceptNull(contractBom
					.getAmount());
		}
		if (totalUsedAmount <= 0) {
			return;
		}
		// double count=lsContractBom.size();
		// double m=CommonUtils.getDoubleByDigit(diffAmount/count,3);
		for (ContractBom contractBom : lsContractBom) {
			double m = CommonUtils
					.getDoubleByDigit(
							((CommonUtils.getDoubleExceptNull(contractBom
									.getAmount()) / totalUsedAmount) * diffAmount),
							5);
			contractBom.setAmount(CommonUtils.getDoubleExceptNull(contractBom
					.getAmount()) + m);
			double newTotalAmount = CommonUtils.getDoubleExceptNull(contractBom
					.getAmount());
			double exgAmount = CommonUtils.getDoubleExceptNull(contractBom
					.getContractExg().getExportAmount());
			double unitDosage = CommonUtils.getDoubleByDigit(newTotalAmount
					/ exgAmount, 5);
			double waste = CommonUtils.getDoubleExceptNull(contractBom
					.getWaste()) / 100.0;
			double unitWaste = CommonUtils.getDoubleByDigit(unitDosage
					* (1 - waste), 5);
			contractBom.setUnitDosage(unitDosage);
			contractBom.setUnitWaste(unitWaste);
			contractBom.setWaste(waste * 100);
			contractBom.setTotalPrice(CommonUtils
					.getDoubleExceptNull(contractBom.getAmount())
					* CommonUtils.getDoubleExceptNull(contractBom
							.getDeclarePrice()));
			this.contractDao.saveContractBom(contractBom);
			this.writeBackContractExg(contractBom.getContractExg());
		}
	}

	/**
	 * 通过报关商品资料的凭证序号查找合同料件或合同成品
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @param ptNo
	 *            料号
	 * @param type
	 *            物料类别
	 * @return List 是ContractImg或ContractExg型，合同料件或合同成品
	 */
	public List findBillByMaterielPtNo(String emsNo, String ptNo, String type) {
		Integer seqNum = this.contractDao.findSeqNumByPtNo(ptNo, type);
		if (seqNum != null) {
			return this.contractDao.findImgExgBillBySeqNum(seqNum, type, emsNo);
		} else {
			return new Vector();
		}
	}

	/**
	 * 保存合同料件来自商品编码
	 * 
	 * @param contract
	 *            合同表头
	 * @param list
	 *            是Complex型，自用商品编码
	 * @return List 是ContractImg型，合同料件
	 */
	public List saveComtractImgByComplex(Contract contract, List list) {
		List contractImgList = new ArrayList();
		int maxSeqNum = this.contractDao.getMaxContractImgSeqNum(contract
				.getId()) + 1;
		for (int i = 0; i < list.size(); i++) {
			ContractImg img = new ContractImg();
			Complex c = (Complex) list.get(i);
			img.setSeqNum(maxSeqNum + i);
			img.setCompany(CommonUtils.getCompany());
			img.setContract(contract);
			img.setName(c.getName());
			// img.setDeclareState(DeclareState.APPLY_POR);
			img.setComplex(c);
			img.setAmount(0.0);
			img.setModifyMark(ModifyMarkState.ADDED);
			// img.setLegalUnit(c.getFirstUnit());
			// img.setSecondUnit(c.getSecondUnit());
			contractImgList.add(img);
		}
		this.contractDao.saveContractImg(contractImgList);
		this.statContractImgCount(contract);
		return contractImgList;
	}

	/**
	 * 保存合同成品来自商品编码
	 * 
	 * @param contract
	 *            合同表头
	 * @param list
	 *            是Complex型，自用商品编码
	 * @return List 是ContractExg型，合同成品
	 */
	public List saveComtractExgByComplex(Contract contract, List list) {
		List contractExgList = new ArrayList();
		int maxSeqNum = this.contractDao.getMaxContractExgSeqNum(contract
				.getId()) + 1;
		for (int i = 0; i < list.size(); i++) {
			ContractExg exg = new ContractExg();
			Complex c = (Complex) list.get(i);
			if (c == null) {
				continue;
			}
			exg.setSeqNum(maxSeqNum + i);
			exg.setCompany(CommonUtils.getCompany());
			exg.setContract(contract);
			exg.setName(c.getName());
			// exg.setDeclareState(DeclareState.APPLY_POR);
			exg.setComplex(c);
			exg.setExportAmount(0.0);
			exg.setModifyMark(ModifyMarkState.ADDED);
			// exg.setLegalUnit(c.getFirstUnit());
			// exg.setSecondUnit(c.getSecondUnit());
			contractExgList.add(exg);
		}
		this.contractDao.saveContractExg(contractExgList);
		this.statContractExgCount(contract);
		return contractExgList;
	}

	/**
	 * 保存合同料件来自商品编码
	 * 
	 * @param contract
	 *            合同表头
	 * @param list
	 *            是Complex型，自用商品编码
	 * @return List 是ContractImg型，合同料件
	 */
	public List saveComtractBomByComplex(ContractExg contractExg, List list) {
		List contractBomList = new ArrayList();
		List contractImgList = new ArrayList();
		int maxSeqNum = this.contractDao.getMaxContractBomSeqNum(contractExg
				.getId()) + 1;
		ContractImg img = null;
		for (int i = 0; i < list.size(); i++) {
			Complex complex = (Complex) list.get(i);
			List lsImg = this.contractDao.findContractImgByParentIdComplex(
					contractExg.getContract().getId(), complex.getCode());
			if (lsImg.size() <= 0) {
				List lsTemp = new ArrayList();
				lsTemp.add(complex);
				lsTemp = saveComtractImgByComplex(contractExg.getContract(),
						lsTemp);
				img = (ContractImg) lsTemp.get(0);
			} else {
				img = (ContractImg) lsImg.get(0);
			}
			ContractBom contractBom = new ContractBom();
			contractBom.setSeqNum(maxSeqNum + i);
			contractBom.setCompany(CommonUtils.getCompany());
			contractBom.setContractExg(contractExg);
			contractBom.setName(img.getName());
			// contractBom.setDeclareState(DeclareState.APPLY_POR);
			contractBom.setSpec(img.getSpec());
			contractBom.setComplex(img.getComplex());
			contractBom.setUnit(img.getUnit());
			// contractBom.setLegalUnit(contractImg.getLegalUnit());
			contractBom.setDeclarePrice(img.getDeclarePrice());
			// contractBom.setLegalAmount(contractImg.getLegalAmount());
			contractBom.setUnitWeight(img.getUnitWeight());
			contractBom.setIsMainImg(img.getIsMainImg());
			contractBom.setCountry(img.getCountry());
			contractBom.setContractImgSeqNum(img.getSeqNum());
			// contractBom.setContractImgId(img.getId());
			contractBom.setModifyMark(ModifyMarkState.ADDED);
			contractBomList.add(contractBom);
			// img.setLegalUnit(c.getFirstUnit());
			// img.setSecondUnit(c.getSecondUnit());
			contractImgList.add(img);
		}
		this.contractDao.saveContractBom(contractBomList);
		this.contractDao.saveContractBom(contractImgList);
		return contractBomList;
	}

	/**
	 * 返回合同单耗
	 * 
	 * @param exgList
	 *            是ContractExg型，合同成品
	 * @return List 是ContractBom型，合同BOM
	 */
	public List findUnitWaste(List exgList) {
		List unitList = new ArrayList();
		for (int i = 0; i < exgList.size(); i++) {
			ContractExg exg = (ContractExg) exgList.get(i);
			List bomList = contractDao.findContractBomByExgId(exg.getId());
			for (int j = 0; j < bomList.size(); j++) {
				ContractBom bom = (ContractBom) bomList.get(j);
				unitList.add(bom);
			}
		}
		return unitList;
	}

	/**
	 * 变更合同料件序号
	 * 
	 * @param img
	 *            合同料件
	 * @param newSeqNum
	 *            新序号
	 */
	public void changeContractImgSeqNum(Contract contract, ContractImg img,
			Integer newSeqNum, String modifyMark) {

		Contract base = (Contract) img.getContract();
		List<Object[]> os = new ArrayList<Object[]>();

		Map<Integer, List<ContractBom>> mapBom = this
				.findContractBomGroupByImgSeqNum(img.getContract());

		if (this.contractDao.findContractImg(img.getContract().getId(),
				newSeqNum.toString()) != null) {

			if (newSeqNum < img.getSeqNum()) {

				List list = this.contractDao.findContractImgBeginAndEndSeqNum(
						img.getContract().getId(), newSeqNum,
						img.getSeqNum() - 1);

				for (int i = 0; i < list.size(); i++) {
					ContractImg contractImg = (ContractImg) list.get(i);
					os.add(new Object[] { contractImg.getSeqNum(),
							contractImg.getSeqNum() + 1,
							contractImg.getCredenceNo() });

					if (base.getIsContractEms() != null
							&& !base.getIsContractEms()) {
						updateBcsCustomsDeclarationCommInfoImgSeqNum(contract,
								img, contractImg.getSeqNum() + 1);
						if (modifyMark.equals(ModifyMarkState.UNCHANGE)) {
							img.setModifyMark(ModifyMarkState.MODIFIED);
						}
					}

					contractImg.setSeqNum(contractImg.getSeqNum() + 1);

					this.contractDao.saveContractImg(contractImg);
				}
			} else {
				List list = this.contractDao.findContractImgBeginAndEndSeqNum(
						img.getContract().getId(), img.getSeqNum() + 1,
						newSeqNum);

				for (int i = 0; i < list.size(); i++) {
					ContractImg contractImg = (ContractImg) list.get(i);
					os.add(new Object[] { contractImg.getSeqNum(),
							contractImg.getSeqNum() - 1,
							contractImg.getCredenceNo() });

					if (base.getIsContractEms() != null
							&& !base.getIsContractEms()) {
						updateBcsCustomsDeclarationCommInfoImgSeqNum(contract,
								img, contractImg.getSeqNum() - 1);
						if (modifyMark.equals(ModifyMarkState.UNCHANGE)) {
							img.setModifyMark(ModifyMarkState.MODIFIED);
						}
					}

					contractImg.setSeqNum(contractImg.getSeqNum() - 1);

					this.contractDao.saveContractImg(contractImg);

				}
			}
		}

		if (base.getIsContractEms() != null && !base.getIsContractEms()) {
			updateBcsCustomsDeclarationCommInfoImgSeqNum(contract, img,
					newSeqNum);
			if (modifyMark.equals(ModifyMarkState.UNCHANGE)) {
				img.setModifyMark(ModifyMarkState.MODIFIED);
			}
		}
		os.add(new Object[] { img.getSeqNum(), newSeqNum, img.getCredenceNo() });
		// os.add(new Object[]{newSeqNum, img.getSeqNum(),img.getCredenceNo()});
		img.setSeqNum(newSeqNum);

		this.contractDao.saveContractImg(img);
		this.updateContractBomImgSeqNum(os, mapBom, null);
	}

	/**
	 * 更新报关单物料商品序号
	 * 
	 * @param contract
	 * @param img
	 * @param newSeqNum
	 */
	private void updateBcsCustomsDeclarationCommInfoImgSeqNum(
			Contract contract, ContractImg img, Integer newSeqNum) {
		if (!img.getSeqNum().equals(newSeqNum)) {
			List list = this.contractDao.findBcsCustomsDeclarationCommInfo(
					true, contract, img.getSeqNum());
			for (int i = 0; i < list.size(); i++) {
				BcsCustomsDeclarationCommInfo commInfo = (BcsCustomsDeclarationCommInfo) list
						.get(i);
				commInfo.setCommSerialNo(newSeqNum);
				this.contractDao.saveOrUpdate(commInfo);
			}
		}
	}

	/**
	 * 根据料件的备案序号更新单耗中的料件序号
	 * 
	 * @param img
	 */
	private void updateContractBomImgSeqNum(ContractImg img, Integer newSeqNum,
			Map<Integer, List<ContractBom>> mapBom) {
		if (!img.getSeqNum().equals(newSeqNum)) {
			List list = mapBom.get(img.getSeqNum());
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					ContractBom contractBom = (ContractBom) list.get(i);
					contractBom.setContractImgSeqNum(newSeqNum);
					contractBom.setModifyMark(ModifyMarkState.MODIFIED);
					this.contractDao.saveContractBom(contractBom);
				}
			}
		}
	}

	/**
	 * 根据料件的备案序号更新单耗中的料件序号
	 * 
	 * @param modified
	 *            修改标志
	 * @param img
	 */
	private void updateContractBomImgSeqNum(ContractImg img, Integer newSeqNum,
			Map<Integer, List<ContractBom>> mapBom, String modified) {
		List list = mapBom.get(img.getCredenceNo());
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				ContractBom contractBom = (ContractBom) list.get(i);
				contractBom.setContractImgSeqNum(101);

				if (modified != null && !"".equals(modified)) {
					contractBom.setModifyMark(ModifyMarkState.MODIFIED);
				}
				this.contractDao.saveContractBom(contractBom);
			}
		}
	}

	/**
	 * 根据料件的备案序号更新单耗中的料件序号
	 * 
	 * @param modified
	 *            修改标志
	 * @param img
	 */
	private void updateContractBomImgSeqNum(List<Object[]> Imgs,
			Map<Integer, List<ContractBom>> mapBom, String modified) {

		for (Object[] os : Imgs) {
			List list = mapBom.get(os[0]);
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					ContractBom contractBom = (ContractBom) list.get(i);
					contractBom.setContractImgSeqNum(Integer.parseInt(os[1]
							.toString()));

					if (modified != null && !"".equals(modified)) {
						contractBom.setModifyMark(ModifyMarkState.MODIFIED);
					}
					this.contractDao.saveContractBom(contractBom);
				}
			}
		}
	}

	/**
	 * 根据料件的备案序号更新单耗中的料件序号
	 * 
	 * @param img
	 */
	private void updateContractBomImgSeqNum(ContractImg img,
			Map<Integer, List<ContractBom>> mapBom) {
		ContractImg oldImg = (ContractImg) this.contractDao.load(
				ContractImg.class, img.getId());
		if (oldImg == null) {
			throw new RuntimeException("备案序号是:" + img.getSeqNum() + "的料件不存在");
		}
		if (!oldImg.getSeqNum().equals(img.getSeqNum())) {
			List list = mapBom.get(oldImg.getSeqNum());
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					ContractBom contractBom = (ContractBom) list.get(i);
					contractBom.setContractImgSeqNum(img.getSeqNum());
					this.contractDao.saveContractBom(contractBom);
				}
			}
		}
	}

	/**
	 * 查询料件对应的成品单耗
	 * 
	 * @param contract
	 * @return
	 */
	private Map<Integer, List<ContractBom>> findContractBomGroupByImgSeqNum(
			Contract contract) {
		Map<Integer, List<ContractBom>> map = new HashMap<Integer, List<ContractBom>>();
		List list = contractDao.findContractBomByContractId(contract);
		// List list =
		// contractDao.findContractBomByContractId(contract.getId());
		for (int i = 0; i < list.size(); i++) {
			ContractBom bom = (ContractBom) list.get(i);
			// List<ContractBom> lsBom = map.get(bom.getImgCredenceNo());
			List<ContractBom> lsBom = map.get(bom.getContractImgSeqNum());
			if (lsBom == null) {
				lsBom = new ArrayList<ContractBom>();
				map.put(bom.getContractImgSeqNum(), lsBom);
				// map.put(bom.getImgCredenceNo(), lsBom);
			}
			lsBom.add(bom);
		}
		return map;
	}

	/**
	 * 变更合同成品序号
	 * 
	 * @param exg
	 *            合同成品
	 * @param newSeqNum
	 *            新序号
	 */
	public void changeContractExgSeqNum(Contract contract, ContractExg exg,
			Integer newSeqNum, String modifyMark) {
		Contract base = (Contract) exg.getContract();
		if (this.contractDao.findContractExg(exg.getContract().getId(),
				newSeqNum.toString()) != null) {
			if (newSeqNum < exg.getSeqNum()) {
				List list = this.contractDao.findContractExgBeginAndEndSeqNum(
						exg.getContract().getId(), newSeqNum,
						exg.getSeqNum() - 1);
				for (int i = 0; i < list.size(); i++) {
					ContractExg contractExg = (ContractExg) list.get(i);
					if (base.getIsContractEms() != null
							&& !base.getIsContractEms()) {
						updateBcsCustomsDeclarationCommInfoexgSeqNum(contract,
								exg, newSeqNum);
						if (modifyMark.equals(ModifyMarkState.UNCHANGE)) {
							exg.setModifyMark(ModifyMarkState.MODIFIED);
						}
					}
					contractExg.setSeqNum(contractExg.getSeqNum() + 1);

					this.contractDao.saveContractExg(contractExg);
				}
			} else {
				List list = this.contractDao.findContractExgBeginAndEndSeqNum(
						exg.getContract().getId(), exg.getSeqNum() + 1,
						newSeqNum);
				for (int i = 0; i < list.size(); i++) {
					ContractExg contractExg = (ContractExg) list.get(i);
					if (base.getIsContractEms() != null
							&& !base.getIsContractEms()) {
						updateBcsCustomsDeclarationCommInfoexgSeqNum(contract,
								exg, newSeqNum);
						if (modifyMark.equals(ModifyMarkState.UNCHANGE)) {
							exg.setModifyMark(ModifyMarkState.MODIFIED);
						}
					}
					contractExg.setSeqNum(contractExg.getSeqNum() - 1);

					this.contractDao.saveContractExg(contractExg);
				}
			}
			// throw new RuntimeException("料件序号" + exg.getSeqNum().toString()
			// + "已经存在");
		}
		if (base.getIsContractEms() != null && !base.getIsContractEms()) {
			updateBcsCustomsDeclarationCommInfoexgSeqNum(contract, exg,
					newSeqNum);
			exg.setModifyMark(ModifyMarkState.MODIFIED);
		}
		exg.setSeqNum(newSeqNum);
		this.contractDao.saveContractExg(exg);

	}

	/**
	 * 更新报关单物料商品序号
	 * 
	 * @param contract
	 * @param img
	 * @param newSeqNum
	 */
	private void updateBcsCustomsDeclarationCommInfoexgSeqNum(
			Contract contract, ContractExg img, Integer newSeqNum) {
		if (!img.getSeqNum().equals(newSeqNum)) {
			List list = this.contractDao.findBcsCustomsDeclarationCommInfo(
					false, contract, img.getSeqNum());
			for (int i = 0; i < list.size(); i++) {
				BcsCustomsDeclarationCommInfo commInfo = (BcsCustomsDeclarationCommInfo) list
						.get(i);
				commInfo.setCommSerialNo(newSeqNum);
				this.contractDao.saveOrUpdate(commInfo);
			}
		}
	}

	/**
	 * 转抄合同成品单耗
	 * 
	 * @param exgFrom
	 *            要被拷贝的合同成品
	 * @param exgTo
	 *            要拷贝的合同成品
	 * @param isCalculateFinishProductSum
	 *            是否要反算成品总金额，true为要反算
	 * @param isCalculateMaterielSum
	 *            是否要反算成品总金额，true为要反算
	 */
	public void copyContractUnitWaste(ContractExg exgFrom, ContractExg exgTo) {

		// 先删除 当前需要转抄单耗的合同成品
		this.contractDao.deleteAll(this.contractDao
				.findContractBomByExgId(exgTo.getId()));

		// 获取 被转抄的合同单耗列表
		List lsFrom = contractDao.findContractBomByExgId(exgFrom.getId());

		if (lsFrom == null || lsFrom.size() == 0) {

			return;

		}

		// 查找账册 参数设定
		BcsParameterSet parameter = this.contractDao.findBcsParameterSet();

		// 需要保存的 合同料件
		Map<Integer, ContractImg> needSaveImg = new HashMap<Integer, ContractImg>();

		// 本合同的料件序号中的备案序号
		Map<Integer, ContractImg> contractCredenceNo = initImgMap(exgTo
				.getContract().getId());

		Integer credenceNo = null;// KEY备案序号

		ContractImg img = null;// 料件

		// 转抄来源的 账册编号
		String emsnoFrom = exgFrom.getContract().getEmsNo();

		// 需转抄 到 的 账册编号
		String emsnoexgTo = exgTo.getContract().getEmsNo();

		// 需转抄 到 的 合同ID
		String emsnoToid = exgTo.getContract().getId();

		// 本合同的料件序号中的备案序号
		Map<String, ContractImg> contractCredenceNo1 = initImgMapNew(exgTo
				.getContract().getId());

		StringBuffer str = new StringBuffer();

		for (int i = 0; i < lsFrom.size(); i++) {

			ContractBom bom = (ContractBom) lsFrom.get(i);

			if ((bom.getImgCredenceNo() == null || "".equals(bom
					.getImgCredenceNo()))) {

				continue;

			}

			if (i == 0) {

				str.append(bom.getImgCredenceNo());

				continue;

			}

			str.append(",");

			str.append(bom.getImgCredenceNo());

		}

		if (str.toString().equals("")) {

			return;

		}

		/**
		 * 第一步：判断转抄的单耗中的记录号在本料件总表中是否存在多笔，当没有记录号重复时，执行
		 */
		List<Object[]> byImgCredenceNo = this.contractDao
				.isExistContractByImgCredenceNo(emsnoToid, str);

		// 用于装载 重复的记录号
		Map<Integer, Object> objmap = new HashMap<Integer, Object>();

		for (Object[] obj : byImgCredenceNo) {

			Integer key = (Integer) obj[0];

			objmap.put(key, obj);

		}

		for (int i = 0; i < lsFrom.size(); i++) {

			ContractBom bom = (ContractBom) lsFrom.get(i);

			Integer key = bom.getImgCredenceNo();

			objmap.get(key);

			if ((bom.getImgCredenceNo() == null || "".equals(bom
					.getImgCredenceNo()))) {

				continue;
			}

			boolean isExistContractByImgCredenceNo;
			/**
			 * 第一步：判断转抄的单耗中的记录号在本料件总表中是否存在多笔，当没有记录号重复时，执行
			 */
			isExistContractByImgCredenceNo = null == objmap.get(key) ? true
					: false;

			if (isExistContractByImgCredenceNo) {

				credenceNo = bom.getImgCredenceNo();

				img = contractCredenceNo.get(credenceNo);

				/**
				 * 第二步：判断是否是转抄本合同数据，当转抄本合同单耗时，直接复制单耗表
				 */
				if (img == null && !emsnoFrom.equals(emsnoexgTo)) {

					int maxSeqNum = this.contractDao
							.getMaxContractImgSeqNum(exgTo.getContract()
									.getId());

					img = new ContractImg();

					img.setId(null);
					img.setCredenceNo(bom.getImgCredenceNo());
					img.setName(bom.getName());
					img.setSpec(bom.getSpec());
					img.setComplex(bom.getComplex());
					img.setUnit(bom.getUnit());
					img.setDeclarePrice(bom.getDeclarePrice());
					img.setUnitWeight(bom.getUnitWeight());

					// 如果 是否主要料件 抓取为空的时候 那么就直接给 false
					img.setIsMainImg(bom.getIsMainImg() == null ? false : bom
							.getIsMainImg());

					img.setCountry(bom.getCountry());
					img.setSeqNum(maxSeqNum + 1);
					img.setAmount(0.0);
					img.setModifyMark(ModifyMarkState.ADDED);
					img.setContract(exgTo.getContract());
					// needSaveImg.put(credenceNo, img);
					this.contractDao.saveContractImg(img);
				}
				ContractBom newBom = new ContractBom();// 单耗
				try {
					PropertyUtils.copyProperties(newBom, bom);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
				newBom.setContractExg(exgTo);
				newBom.setId(null);
				newBom.setModifyMark(ModifyMarkState.ADDED);
				/**
				 * 当转抄其他合同单耗时，单耗表中总表序号获取料件表序号
				 */
				if (!emsnoFrom.equals(emsnoexgTo)) {
					newBom.setContractImgSeqNum(img.getSeqNum());
				}
				this.contractDao.saveContractBom(newBom);

				if (parameter != null
						&& parameter.updateContractBomWriteBackImg != null
						&& parameter.updateContractBomWriteBackImg) {
					writeBackContractImg(newBom.getContractExg().getContract()
							.getId(), newBom);
				}
			} else {
				ContractBom newBom = new ContractBom();// 单耗
				try {
					PropertyUtils.copyProperties(newBom, bom);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
				newBom.setContractExg(exgTo);
				newBom.setId(null);
				newBom.setModifyMark(ModifyMarkState.ADDED);
				/**
				 * 当从本合同转抄单耗的记录号在料件总表存在多个时，先查询出料件总表list并组装map，再根据key：编码+名称+规格+单位
				 * ，取出料件总表序号并在单耗中赋值
				 */
				if (!emsnoFrom.equals(emsnoexgTo)) {
					String key1 = bom.getComplex() + bom.getName()
							+ bom.getSpec() + bom.getUnit();

					ContractImg newimg = contractCredenceNo1.get(key1);
					if (newimg != null) {
						Integer bomSeqnum = newimg.getSeqNum();
						newBom.setContractImgSeqNum(bomSeqnum);
					}
				}
				this.contractDao.saveContractBom(newBom);

				if (parameter != null
						&& parameter.updateContractBomWriteBackImg != null
						&& parameter.updateContractBomWriteBackImg) {
					writeBackContractImg(newBom.getContractExg().getContract()
							.getId(), newBom);
				}
			}
		}
		if (parameter != null
				&& parameter.updateContractBomWriteBackExg != null
				&& parameter.updateContractBomWriteBackExg) {
			writeBackContractExg(exgTo);
		}
	}

	/**
	 * 电子化手册料件Map
	 */
	private Map<Integer, ContractImg> initImgMap(String contractid) {

		// 合同料件 转抄对应后的对应
		Map<Integer, ContractImg> credencenoImg = new HashMap<Integer, ContractImg>();

		List<ContractImg> list = this.contractDao
				.findContractImgByContractIdChange(contractid);

		for (int i = 0; i < list.size(); i++) {

			// 获取 合同料件
			ContractImg obj = list.get(i);

			// if (credencenoImg.get(obj.getCredenceNo()) == null) {
			// 料件记录号为 key 合同料件对象为 value 保存
			credencenoImg.put(obj.getCredenceNo(), obj);
			// }
		}

		return credencenoImg;
	}

	/**
	 * 电子化手册当前合同料件Map
	 */
	private Map<String, ContractImg> initImgMapNew(String contractid) {

		Map<String, ContractImg> credencenoImg = new HashMap<String, ContractImg>();

		List<ContractImg> list = this.contractDao
				.findContractImgByContractIdChange(contractid);

		for (int i = 0; i < list.size(); i++) {

			ContractImg obj = list.get(i);

			String key = obj.getComplex() + obj.getName() + obj.getSpec()
					+ obj.getUnit();

			// if (credencenoImg.get(obj.getCredenceNo()) == null) {
			credencenoImg.put(key, obj);
			// }
		}

		return credencenoImg;
	}

	/**
	 * 根据合同单耗自动反算成品，料件的数量，金额等
	 * 
	 * @param contract
	 *            合同表头
	 * @param isCalculateFinishProductSum
	 *            是否要反算成品总金额，true为要反算
	 * @param isCalculateMaterielSum
	 *            是否要反算成品总金额，true为要反算
	 */
	public void reCalContractUnitWaste(Contract contract) {
		List list = this.contractDao.findContractBomByContractParentId(contract
				.getId());
		BcsParameterSet parameter = this.contractDao.findBcsParameterSet();

		/**
		 * 单价小数位控制
		 */
		int priceBit = getPriceBitFromParaSet();
		/**
		 * 数量小数位控制
		 */
		int countBit = getCountBitFromParaSet();
		/**
		 * 金额小数位控制
		 */
		int moneyBit = getMoneyBitFromParaSet();
		for (int i = 0; i < list.size(); i++) {
			ContractBom bom = (ContractBom) list.get(i);
			double exgAmount = (bom.getContractExg().getExportAmount() == null ? 0.0
					: bom.getContractExg().getExportAmount());
			double unitWaste = (bom.getUnitWaste() == null ? 0.0 : bom
					.getUnitWaste());
			double waste = (bom.getWaste() == null ? 0.0 : bom.getWaste()) / 100.0;
			double unitDosage = CommonUtils.getDoubleByDigit((waste >= 1 ? 0
					: unitWaste / (1 - waste)), 9);
			bom.setUnitDosage(CommonUtils.getDoubleByDigit(unitDosage, 9));
			bom.setAmount(CommonUtils.getDoubleByDigit(
					(exgAmount * unitDosage), countBit));

			bom.setDeclarePrice(CommonUtils.getDoubleByDigit(
					CommonUtils.getDoubleExceptNull(bom.getDeclarePrice()),
					priceBit));
			double totalPrice = (bom.getAmount() == null ? 0 : bom.getAmount())
					* (bom.getDeclarePrice() == null ? 0 : bom
							.getDeclarePrice());
			bom.setTotalPrice(CommonUtils
					.getDoubleByDigit(totalPrice, moneyBit));
			this.contractDao.saveContractBom(bom);
		}
		// if (isCalculateFinishProductSum) {

		if (parameter != null
				&& parameter.updateContractBomWriteBackExg != null
				&& parameter.updateContractBomWriteBackExg) {
			list = this.contractDao.findContractExgByParentId(contract.getId());

			for (int i = 0; i < list.size(); i++) {
				ContractExg exg = (ContractExg) list.get(i);

				// llaa;
				writeBackContractExg(exg);
			}
		}
		// if (isCalculateMaterielSum) {
		if (parameter != null
				&& parameter.updateContractBomWriteBackImg != null
				&& parameter.updateContractBomWriteBackImg) {
			list = this.contractDao.findContractImgByContractId(contract
					.getId());
			for (int i = 0; i < list.size(); i++) {
				ContractImg img = (ContractImg) list.get(i);
				// ll
				writeBackContractImg(contract.getId(), img);
			}
		}
		this.statContractImgMoney(contract);
		this.statContractExgMoney(contract);
	}

	/**
	 * 更改合同商品的名称规格
	 * 
	 * @param contract
	 *            合同表头
	 * @param isMaterial
	 *            料件判断，true为料件
	 * @param seqNum
	 *            料件序号
	 * @param name
	 *            商品名称
	 * @param spec
	 *            商品规格
	 * @return List 是ContractExg或ContractImg型，合同成品或合同料件
	 */
	public Object changeContractCommNameSpec(Contract contract,
			boolean isMaterial, Integer seqNum, String name, String spec) {
		Object obj = null;
		if (isMaterial) {
			ContractImg img = this.contractDao.findContractImg(
					contract.getId(), seqNum.toString());
			img.setName(name);
			img.setSpec(spec);
			if (!ModifyMarkState.ADDED.equals(img.getModifyMark())) {
				img.setModifyMark(ModifyMarkState.MODIFIED);
			}
			obj = img;
			this.contractDao.saveContractImg(img);
			List list = this.contractDao.findContractBom(contract.getId(),
					seqNum);
			for (int i = 0; i < list.size(); i++) {
				ContractBom bom = (ContractBom) list.get(i);
				bom.setName(name);
				bom.setSpec(spec);
				this.contractDao.saveContractBom(bom);
			}
		} else {
			ContractExg exg = this.contractDao.findContractExg(
					contract.getId(), seqNum.toString());
			exg.setName(name);
			exg.setSpec(spec);
			if (!ModifyMarkState.ADDED.equals(exg.getModifyMark())) {
				exg.setModifyMark(ModifyMarkState.MODIFIED);
			}
			this.contractDao.saveContractExg(exg);
			obj = exg;
		}
		List list = this.contractDao.findBcsCustomsDeclarationCommInfo(
				isMaterial, contract, seqNum);
		for (int i = 0; i < list.size(); i++) {
			BcsCustomsDeclarationCommInfo commInfo = (BcsCustomsDeclarationCommInfo) list
					.get(i);
			commInfo.setCommName(name);
			commInfo.setCommSpec(spec);
			this.contractDao.saveOrUpdate(commInfo);
		}
		return obj;
	}

	/**
	 * 查找合同成品 来自 合同表头Id
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同表头Id
	 * @return List 是ContractExg型，合同成品
	 */
	public List findTempContractExgByParentId(String parentId) {
		List returnList = new ArrayList();
		List list = this.contractDao.findContractExgByParentId(parentId);
		for (int i = 0; i < list.size(); i++) {
			ContractExg c = (ContractExg) list.get(i);
			TempContractExg t = new TempContractExg();
			t.setIsSelected(false);
			t.setContractExg(c);
			returnList.add(t);
		}
		return returnList;
	}

	/**
	 * 查找合同料件 来自 合同表头Id
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同表头Id
	 * @return List 是ContractImg型，合同料件
	 */
	public List findTempContractImgByParentId(String parentId) {
		List returnList = new ArrayList();
		List list = this.contractDao.findContractImgByContractId(parentId);
		for (int i = 0; i < list.size(); i++) {
			ContractImg c = (ContractImg) list.get(i);
			TempContractImg t = new TempContractImg();
			t.setIsSelected(false);
			t.setContractImg(c);
			returnList.add(t);
		}
		return returnList;
	}

	/**
	 * 转抄合同成品(包括BOM)数据
	 * 
	 * @param list
	 *            是Contract型，合同表头
	 */
	public Contract copyContractProduct(Contract contract,
			List<ContractExg> contractExgList) {
		try {
			String copEmsNo = bcsMessageLogic.getNewCopEntNo("Contract",
					"copEmsNo", "C", BcsBusinessType.EMS_POR_BILL);
			// 新签合同的进/出口合同号为12位（年份+企业海关十位编码后5位+3位流水号）
			String impContractNo = this.getMaxImpContractNo();
			String expContractNo = this.getMaxExpContractNo();
			Contract c = (Contract) BeanUtils.cloneBean(contract);
			String contractId = c.getId();
			c.setId(null);
			c.setEmsNo(null);
			c.setCopEmsNo(copEmsNo);
			c.setDeclareCustoms(null);
			c.setImpContractNo(impContractNo);
			c.setExpContractNo(expContractNo);
			c.setApproveDate(null);
			c.setBeginDate(null);
			c.setEndDate(null);
			c.setDeferDate(null);
			c.setDestroyDate(null);
			c.setDeclareState(DeclareState.APPLY_POR);
			c.setModifyMark(ModifyMarkState.ADDED);
			this.contractDao.saveContract(c);

			/**
			 * 查找合同料件 来自 合同成品ID
			 */
			Map<Integer, String> contractImgMap = new HashMap<Integer, String>();
			List contractImgList = this.contractDao
					.findContractImgByParentIdInBom(contractId, contractExgList);

			for (int j = 0; j < contractImgList.size(); j++) {
				ContractImg contractImg = (ContractImg) BeanUtils
						.cloneBean(contractImgList.get(j));
				/**
				 * 转抄料件
				 */
				contractImg.setId(null);
				contractImg.setContract(c);
				contractImg.setDutyRate(0.0);
				contractImg.setModifyMark(ModifyMarkState.ADDED);
				this.contractDao.saveContractImg(contractImg);
				//
				// 存入新的料件ID用于排序和变更时用
				//
				if (contractImg.getSeqNum() != null) {
					contractImgMap.put(contractImg.getSeqNum(),
							contractImg.getId());
				}
			}

			for (int j = 0; j < contractExgList.size(); j++) {
				ContractExg contractExg = (ContractExg) BeanUtils
						.cloneBean(contractExgList.get(j));
				String contractExgId = contractExg.getId();
				/**
				 * 转抄成品
				 */
				contractExg.setId(null);
				// contractExg.setDeclareState(DeclareState.APPLY_POR);
				contractExg.setContract(c);
				contractExg.setModifyMark(ModifyMarkState.ADDED);
				this.contractDao.saveContractExg(contractExg);
				/**
				 * 查找合同BOM 来自 合同成品ID
				 */
				List contractBomList = this.contractDao
						.findContractBomByExgId(contractExgId);
				for (int k = 0; k < contractBomList.size(); k++) {
					ContractBom bom = (ContractBom) contractBomList.get(k);
					ContractBom contractBom = (ContractBom) BeanUtils
							.cloneBean(bom);

					/**
					 * 转抄BOM
					 */
					contractBom.setId(null);
					// contractBom.setDeclareState(DeclareState.APPLY_POR);
					contractBom.setContractExg(contractExg);
					contractBom
							.setNonMnlRatio(bom.getNonMnlRatio() == null ? 0.0
									: bom.getNonMnlRatio());
					contractBom.setModifyMark(ModifyMarkState.ADDED);
					// if (contractBom.getContractImgSeqNum() != null) {
					// contractBom.setContractImgId(contractImgMap
					// .get(contractBom.getContractImgSeqNum()));
					// }
					this.contractDao.saveContractBom(contractBom);
				}
			}
			this.statContractExgCount(c);
			this.statContractExgMoney(c);
			this.statContractImgCount(c);
			this.statContractImgMoney(c);
			return c;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
	}

	/**
	 * 转抄合同成品料件(不包括BOM)数据
	 * 
	 * @param list
	 *            是Contract型，合同表头
	 */
	public Contract copyContractProductMateriel(Contract contract,
			List<ContractExg> contractExgList, List<ContractImg> contractImgList) {
		try {
			String copEmsNo = bcsMessageLogic.getNewCopEntNo("Contract",
					"copEmsNo", "C", BcsBusinessType.EMS_POR_BILL);
			// 新签合同的进/出口合同号为12位（年份+企业海关十位编码后5位+3位流水号）
			String impContractNo = this.getMaxImpContractNo();
			String expContractNo = this.getMaxExpContractNo();
			Contract c = (Contract) BeanUtils.cloneBean(contract);
			c.setId(null);
			c.setEmsNo(null);
			c.setCopEmsNo(copEmsNo);
			c.setDeclareCustoms(null);
			c.setImpContractNo(impContractNo);
			c.setExpContractNo(expContractNo);
			c.setApproveDate(null);
			c.setBeginDate(null);
			c.setEndDate(null);
			c.setDeferDate(null);
			c.setDestroyDate(null);
			c.setDeclareState(DeclareState.APPLY_POR);
			this.contractDao.saveContract(c);
			c.setModifyMark(ModifyMarkState.ADDED);
			for (int j = 0; j < contractImgList.size()
					&& contractExgList.size() > 0; j++) {
				ContractImg contractImg = (ContractImg) BeanUtils
						.cloneBean(contractImgList.get(j));
				contractImg.setId(null);
				contractImg.setContract(c);
				contractImg.setDutyRate(0.0);
				contractImg.setModifyMark(ModifyMarkState.ADDED);
				this.contractDao.saveContractImg(contractImg);
			}

			for (int j = 0; j < contractExgList.size(); j++) {
				ContractExg contractExg = (ContractExg) BeanUtils
						.cloneBean(contractExgList.get(j));
				contractExg.setId(null);
				// contractExg.setDeclareState(DeclareState.APPLY_POR);
				contractExg.setContract(c);
				contractExg.setModifyMark(ModifyMarkState.ADDED);
				this.contractDao.saveContractExg(contractExg);
			}
			this.statContractExgCount(c);
			this.statContractExgMoney(c);
			this.statContractImgCount(c);
			this.statContractImgMoney(c);
			return c;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
	}

	/**
	 * 查询不在合同里面的备案资料库的商品资料
	 * 
	 * @param materielType
	 * @param contractId
	 * @param dictPorEmsNo
	 * @return
	 */
	public List findDictPorImgExgNotInContract(String materielType,
			String contractId, String dictPorEmsNo, boolean isFilt) {
		List lsDictPorImgExg = new ArrayList();
		List lsContractImgExg = new ArrayList();
		if (MaterielType.MATERIEL.equals(materielType)) {
			lsDictPorImgExg = this.contractDao.findBcsDictPorImg(dictPorEmsNo);
			if (isFilt) {
				lsContractImgExg = this.contractDao
						.findContractImgCredenceNo(contractId);
				if (lsContractImgExg.size() <= 0) {
					return lsDictPorImgExg;
				}
				for (int i = lsDictPorImgExg.size() - 1; i >= 0; i--) {
					BcsDictPorImg img = (BcsDictPorImg) lsDictPorImgExg.get(i);
					int index = lsContractImgExg.indexOf(img.getSeqNum());
					if (index >= 0) {
						lsContractImgExg.remove(index);
						lsDictPorImgExg.remove(i);
					}
					if (lsContractImgExg.size() <= 0) {
						return lsDictPorImgExg;
					}
				}
			}
		} else if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {
			lsDictPorImgExg = this.contractDao.findBcsDictPorExg(dictPorEmsNo);
			if (isFilt) {
				lsContractImgExg = this.contractDao
						.findContractExgCredenceNo(contractId);
				if (lsContractImgExg.size() <= 0) {
					return lsDictPorImgExg;
				}
				for (int i = lsDictPorImgExg.size() - 1; i >= 0; i--) {
					BcsDictPorExg exg = (BcsDictPorExg) lsDictPorImgExg.get(i);
					int index = lsContractImgExg.indexOf(exg.getSeqNum());
					if (index >= 0) {
						lsContractImgExg.remove(index);
						lsDictPorImgExg.remove(i);
					}
					if (lsContractImgExg.size() <= 0) {
						return lsDictPorImgExg;
					}
				}
			}
		}
		return lsDictPorImgExg;
	}

	/**
	 * 查询不在合同BOM里面的备案资料库的商品资料
	 * 
	 * @param materielType
	 * @param contractId
	 * @param dictPorEmsNo
	 * @return
	 */
	public List findDictPorImgNotInContractBom(String exgId, String dictPorEmsNo) {
		List lsContractBom = this.contractDao.findContractBomCredenceNo(exgId);
		List lsDictPorImg = this.contractDao.findBcsDictPorImg(dictPorEmsNo);
		if (lsContractBom.size() <= 0) {
			return lsDictPorImg;
		}
		for (int i = lsDictPorImg.size() - 1; i >= 0; i--) {
			BcsDictPorImg img = (BcsDictPorImg) lsDictPorImg.get(i);
			int index = lsContractBom.indexOf(img.getSeqNum());
			if (index >= 0) {
				lsContractBom.remove(index);
				lsDictPorImg.remove(i);
			}
			if (lsContractBom.size() <= 0) {
				return lsDictPorImg;
			}
		}
		return lsDictPorImg;
	}

	/**
	 * 合同备案海关申报
	 * 
	 * @param head
	 *            通关备案表头
	 * @return DzscEmsPorHead 通关备案表头
	 */
	public DeclareFileInfo applyContract(Contract head, String taskId) {
		head = this.contractDao.findContractById(head.getId());
		// checkEmsPorHeadForApply(head, materielHead);
		Map<String, List> hmData = new HashMap<String, List>();
		List<CspSignInfo> lsSignInfo = new ArrayList<CspSignInfo>();
		List<Contract> lsHead = new ArrayList<Contract>();
		List lsExgBill = new ArrayList();
		List lsImgBill = new ArrayList();
		List lsBomBill = new ArrayList();
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("正在获取要申报的资料");
		}
		CspSignInfo signInfo = bcsMessageLogic.getCspPtsSignInfo(info,
				head.getManageObject());
		signInfo.setSignDate(new Date());
		signInfo.setCopNo(head.getCopEmsNo());
		signInfo.setColDcrTime(0);
		signInfo.setSysType(DzscBusinessType.EMS_POR_BILL);
		if (ManageObject.MANUFACTURE_COP.equals(head.getManageObject())) {
			signInfo.setTradeCode(head.getMachCode());
		} else if (ManageObject.MANAGE_COP.equals(head.getManageObject())) {
			signInfo.setTradeCode(head.getTradeCode());
		} else {
			throw new RuntimeException("获取签名信息出错：管理对象："
					+ head.getManageObject());
		}
		if (DeclareState.CHANGING_EXE.equals(head.getDeclareState())) {
			signInfo.setDeclareType(DelcareType.MODIFY);
		} else if (DeclareState.APPLY_POR.equals(head.getDeclareState())) {
			signInfo.setDeclareType(DelcareType.APPLICATION);
		} else {
			throw new RuntimeException("手册的申报状态不是<初始状态>或<变更状态>，所以不能申报!");
		}
		lsSignInfo.add(signInfo);
		if (signInfo.getIdCard() == null
				|| "".equals(signInfo.getIdCard().trim())) {
			throw new RuntimeException("签名信息中操作员卡号为空");
		}
		if (signInfo.getIdCard().length() < 4) {
			throw new RuntimeException("签名信息中操作员卡号的长度小于4位");
		}
		head.setInputER(signInfo.getIdCard().substring(
				signInfo.getIdCard().length() - 4));
		head.setDeclareDate(new Date());
		head.setDeclareState(DeclareState.WAIT_EAA);
		lsHead.add(head);
		lsExgBill = this.contractDao.findContractExgStateChanged(head.getId());
		lsImgBill = this.contractDao.findContractImgStateChanged(head.getId());
		lsBomBill = this.contractDao.findContractBomStateChanged(head.getId());
		String formatFile = "BcsContractFormat.xml";
		hmData.put("BcsPtsSignInfo", lsSignInfo);
		hmData.put("BcsContractData", lsHead);
		hmData.put("BcsContractExgData", lsExgBill);
		hmData.put("BcsContractImgData", lsImgBill);
		hmData.put("BcsContractBomData", lsBomBill);
		DeclareFileInfo fileInfo = bcsMessageLogic.exportMessage(formatFile,
				hmData, info);
		this.contractDao.saveContract(head);
		return fileInfo;
	}

	/**
	 * 合同备案回执处理
	 * 
	 * @param contract
	 * @param exingContract
	 * @return
	 */
	public String processContract(final Contract contract,
			final Contract exingContract, List lsReturnFile) {
		return bcsMessageLogic.processMessage(BcsBusinessType.EMS_POR_BILL,
				contract.getCopEmsNo(), new CspProcessMessage() {
					public void failureHandling(
							TempCspReceiptResultInfo tempReceiptResult) {
						backBillContract(contract, exingContract);
					}

					public void successHandling(
							TempCspReceiptResultInfo tempReceiptResult) {
						CspReceiptResult receiptResult = tempReceiptResult
								.getReceiptResult();
						effectiveContract(contract, exingContract,
								receiptResult);
					}
				}, lsReturnFile);
	}

	/**
	 * 备案合同数据
	 * 
	 * @param contract
	 *            合同表头
	 */
	private void effectiveContract(Contract contract, Contract exingContract,
			CspReceiptResult receiptResult) {
		Map<Integer, Complex> mapImg = new HashMap<Integer, Complex>();
		Map<Integer, Complex> mapExg = new HashMap<Integer, Complex>();
		if (exingContract != null) {
			List lsTemp = this.contractDao
					.findContractImgByContractId(exingContract.getId());
			for (int i = 0; i < lsTemp.size(); i++) {
				ContractImg img = (ContractImg) lsTemp.get(i);
				mapImg.put(img.getSeqNum(), img.getComplex());
			}
			lsTemp = this.contractDao.findContractExgByParentId(exingContract
					.getId());
			for (int i = 0; i < lsTemp.size(); i++) {
				ContractExg exg = (ContractExg) lsTemp.get(i);
				mapExg.put(exg.getSeqNum(), exg.getComplex());
			}
			this.deleteContract(exingContract);
		} else {// 第一次申报的时候根据回执反写手册号，变更的时候不反写
			if (receiptResult != null) {
				contract.setEmsNo(receiptResult.getEmsNo());
			}
		}
		contract.setDeclareState(DeclareState.PROCESS_EXE);
		contract.setModifyMark(ModifyMarkState.UNCHANGE);
		this.contractDao.saveContract(contract);
		/*
		 * List lsBomBill =
		 * this.contractDao.findContractBomStateChanged(contract.getId()); List
		 * tempContractBom = new ArrayList(); for (int i = 0; i <
		 * lsBomBill.size(); i++) { ContractBom contractBom = (ContractBom)
		 * lsBomBill.get(i); if
		 * (ModifyMarkState.DELETED.equals(contractBom.getModifyMark())) {
		 * this.contractDao.deleteContractBom(contractBom); } else {
		 * contractBom.setModifyMark(ModifyMarkState.UNCHANGE); //
		 * this.contractDao.saveContractBom(contractBom);
		 * tempContractBom.add(contractBom); } }
		 * this.contractDao.batchSaveOrUpdate(tempContractBom);
		 */
		// 更新BOM
		this.contractDao.batchDeleteContractBomByDeletedState(contract.getId());
		this.contractDao.batchUpdateContractBomStateToUnChanged(contract
				.getId());

		List lsExgBill = this.contractDao.findContractExgStateChanged(contract
				.getId());
		// List tempContractExg = new ArrayList();
		for (int i = 0; i < lsExgBill.size(); i++) {
			ContractExg contractExg = (ContractExg) lsExgBill.get(i);
			/*
			 * if (ModifyMarkState.DELETED.equals(contractExg.getModifyMark()))
			 * { this.contractDao.deleteContractExg(contractExg); } else { if
			 * (ModifyMarkState.MODIFIED .equals(contractExg.getModifyMark())) {
			 * Complex oldComplex = mapExg.get(contractExg.getSeqNum()); // 变更编码
			 * if (!contractExg.getComplex().equals(oldComplex)) {
			 * changeContractExgComplex(contractExg); } }
			 * contractExg.setModifyMark(ModifyMarkState.UNCHANGE);
			 * tempContractExg.add(contractExg); }
			 */
			if (ModifyMarkState.MODIFIED.equals(contractExg.getModifyMark())) {
				Complex oldComplex = mapExg.get(contractExg.getSeqNum());
				// 变更编码
				if (!contractExg.getComplex().equals(oldComplex)) {
					changeContractExgComplex(contractExg);
				}
			}
		}
		// this.contractDao.batchSaveOrUpdate(tempContractExg);
		// 更新EXG
		this.contractDao.batchDeleteContractExgByDeletedState(contract.getId());
		this.contractDao.batchUpdateContractExgStateToUnChanged(contract
				.getId());

		List lsImgBill = this.contractDao.findContractImgStateChanged(contract
				.getId());
		List tempContractImg = new ArrayList();
		// long t = System.currentTimeMillis();
		for (int i = 0; i < lsImgBill.size(); i++) {
			ContractImg contractImg = (ContractImg) lsImgBill.get(i);
			/*
			 * if (ModifyMarkState.DELETED.equals(contractImg.getModifyMark()))
			 * { this.contractDao.deleteContractImg(contractImg); } else { if
			 * (ModifyMarkState.MODIFIED .equals(contractImg.getModifyMark())) {
			 * Complex oldComplex = mapImg.get(contractImg.getSeqNum()); // 变更编码
			 * if (!contractImg.getComplex().equals(oldComplex)) {
			 * changeContractImgComplex(contractImg); } }
			 * contractImg.setModifyMark(ModifyMarkState.UNCHANGE);
			 * tempContractImg.add(contractImg); }
			 */
			if (ModifyMarkState.MODIFIED.equals(contractImg.getModifyMark())) {
				Complex oldComplex = mapImg.get(contractImg.getSeqNum());
				// 变更编码
				if (!contractImg.getComplex().equals(oldComplex)) {
					changeContractImgComplex(contractImg);
				}
			}
		}
		// this.contractDao.batchSaveOrUpdate(tempContractImg);

		// 更新IMG
		this.contractDao.batchDeleteContractImgByDeletedState(contract.getId());
		this.contractDao.batchUpdateContractImgStateToUnChanged(contract
				.getId());
	}

	/**
	 * 合同备案退单
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param exingContract
	 *            变更后合同备案表头
	 */
	private void backBillContract(Contract contract, Contract exingContract) {
		if (exingContract == null) {
			contract.setDeclareState(DeclareState.APPLY_POR);
		} else {
			contract.setDeclareState(DeclareState.CHANGING_EXE);
		}
		this.contractDao.saveContract(contract);
	}

	/**
	 * 统计合同备案的料件项数
	 * 
	 * @param head
	 */
	private void statContractImgCount(Contract head) {
		head = this.contractDao.findContractById(head.getId());
		if (head == null) {
			return;
		}
		head.setMaterielItemCount(this.contractDao.findContractImgCount(head
				.getId()));
		// System.out.println("---------"+head.getMaterielItemCount());
		if (ModifyMarkState.UNCHANGE.equals(head.getModifyMark())) {
			head.setModifyMark(ModifyMarkState.ADDED);
		}
		this.contractDao.saveContract(head);
	}

	/**
	 * 统计合同备案的料件项数
	 * 
	 * @param head
	 */
	private void statContractExgCount(Contract head) {
		head = this.contractDao.findContractById(head.getId());
		if (head == null) {
			return;
		}
		head.setProductItemCount(this.contractDao.findContractExgCount(head
				.getId()));
		if (ModifyMarkState.UNCHANGE.equals(head.getModifyMark())) {
			head.setModifyMark(ModifyMarkState.ADDED);
		}
		this.contractDao.saveContract(head);
	}

	/**
	 * 统计合同备案的进口总值
	 * 
	 * @param head
	 */
	private void statContractImgMoney(Contract head) {
		/**
		 * 单价小数位控制
		 */
		int priceBit = getPriceBitFromParaSet();
		/**
		 * 数量小数位控制
		 */
		int countBit = getCountBitFromParaSet();
		/**
		 * 金额小数位控制
		 */
		int moneyBit = getMoneyBitFromParaSet();
		head = this.contractDao.findContractById(head.getId());
		if (head == null) {
			return;
		}
		// 柯鹏程 根据电子化手册的参数设置中的“是否统计备案料件内购金额”进行统计金额
		BcsParameterSet parameter = this.contractDao.findBcsParameterSet();

		if (parameter.getIsTotalMoney() == null) {// 等于空时 不统计内购的数量
			head.setImgAmount(CommonUtils.getDoubleByDigit(
					this.contractDao.findContractImgMoney(head.getId()),
					moneyBit));
		} else if (parameter.getIsTotalMoney() == true) {// 等于true时 不统计内购的数量
			head.setImgAmount(CommonUtils.getDoubleByDigit(
					this.contractDao.findContractImgTotalMoney(head.getId()),
					moneyBit));
		} else {
			head.setImgAmount(CommonUtils.getDoubleByDigit(
					this.contractDao.findContractImgMoney(head.getId()),
					moneyBit));
		}

		if (ModifyMarkState.UNCHANGE.equals(head.getModifyMark())) {
			head.setModifyMark(ModifyMarkState.ADDED);
		}
		this.contractDao.saveContract(head);
	}

	/**
	 * 统计合同备案的出口总值
	 * 
	 * @param head
	 */
	private void statContractExgMoney(Contract head) {

		/**
		 * 金额小数位控制
		 */
		int moneyBit = this.getMoneyBitFromParaSet();
		head = this.contractDao.findContractById(head.getId());
		if (head == null) {
			return;
		}

		head.setExgAmount(CommonUtils.getDoubleByDigit(
				this.contractDao.findContractExgMoney(head.getId()), moneyBit));

		if (ModifyMarkState.UNCHANGE.equals(head.getModifyMark())) {
			head.setModifyMark(ModifyMarkState.ADDED);
		}
		this.contractDao.saveContract(head);
	}

	/**
	 * 合同料件进出平衡检查
	 * 
	 * @return
	 */
	public List findContractImgAndExgUsedDiffAmount(Contract contract) {
		List lsResult = new ArrayList();
		List lsImg = this.contractDao.findContractImgExceptDeleted(contract
				.getId());
		List lsUsed = this.contractDao.findContractImgAmountByExgUsed(contract);
		Map<Integer, Double> map = new HashMap<Integer, Double>();
		for (int i = 0; i < lsUsed.size(); i++) {
			Object[] objs = (Object[]) lsUsed.get(i);
			if (objs[0] != null) {
				map.put(Integer.valueOf(objs[0].toString().trim()),
						objs[1] == null ? 0.0 : Double.parseDouble(objs[1]
								.toString().trim()));
			}
		}
		for (int i = 0; i < lsImg.size(); i++) {
			ContractImg img = (ContractImg) lsImg.get(i);
			TempImgAndExgUsedDiffAmount diff = new TempImgAndExgUsedDiffAmount();
			diff.setSeqNum(img.getSeqNum());
			diff.setCommNameSpec((img.getName() == null ? "" : img.getName())
					+ "/" + (img.getSpec() == null ? "" : img.getSpec()));
			diff.setImgAmount(CommonUtils.getDoubleByDigit(img.getAmount(), 5));
			diff.setExgUsedAmount(CommonUtils.getDoubleByDigit(
					map.get(img.getSeqNum()), 5));
			diff.setDiffAmount(diff.getImgAmount() - diff.getExgUsedAmount());
			diff.setDiffRate(CommonUtils.getDoubleByDigit(
					(diff.getImgAmount() == 0.0 ? 0.0 : diff.getDiffAmount()
							/ diff.getImgAmount()) * 100.0, 2));
			lsResult.add(diff);
		}
		return lsResult;
	}

	/**
	 * 查询变更的进口料件
	 * 
	 * @param contract
	 * @return
	 */
	public List[] findChangedContractImg(Contract contract) {
		List[] datas = new ArrayList[3];
		List<TempChangedContractImgExg> lsAdd = new ArrayList<TempChangedContractImgExg>();
		List<TempChangedContractImgExg> lsDelete = new ArrayList<TempChangedContractImgExg>();
		int groupNo = 0;
		Double addTotalPrice = 0.0, delTotalPrice = 0.0;// 分别记录增加和删除的总金额
		List list = this.contractDao.findContractImgStateChanged(contract
				.getId());
		Map<Integer, ContractImg> mapContractImg = new HashMap<Integer, ContractImg>();
		if (list.size() > 0) {
			Contract exingContract = this.contractDao
					.findExingContractByEmsNo(contract.getEmsNo());
			if (exingContract == null) {
				throw new RuntimeException("没有找到正在执行的手册：" + contract.getEmsNo());
			}
			List<ContractImg> lsBefore = this.contractDao
					.findContractImgByContractId(exingContract.getId());
			for (ContractImg img : lsBefore) {
				mapContractImg.put(img.getSeqNum(), img);
			}
		}
		for (int i = 0; i < list.size(); i++) {
			ContractImg img = (ContractImg) list.get(i);
			TempChangedContractImgExg tempImg = new TempChangedContractImgExg();
			// if (i % 8 == 0) {
			// groupNo++;
			// }
			// tempImg.setGroupNo(groupNo);
			tempImg.setSeqNum(img.getSeqNum());
			tempImg.setComplex(img.getComplex());
			tempImg.setAmount(img.getAmount());
			tempImg.setName(img.getName());
			tempImg.setSpec(img.getSpec());
			tempImg.setUnit(img.getUnit());
			tempImg.setCountry(img.getCountry());
			tempImg.setLevyMode(img.getLevyMode());
			tempImg.setUnitPrice(img.getDeclarePrice());
			tempImg.setTotalPrice(img.getTotalPrice());
			if (ModifyMarkState.ADDED.equals(img.getModifyMark())) {
				lsAdd.add(tempImg);
				addTotalPrice += CommonUtils.getDoubleExceptNull(tempImg
						.getTotalPrice());
			} else if (ModifyMarkState.DELETED.equals(img.getModifyMark())) {
				lsDelete.add(tempImg);
				delTotalPrice += CommonUtils.getDoubleExceptNull(tempImg
						.getTotalPrice());
			} else if (ModifyMarkState.MODIFIED.equals(img.getModifyMark())) {
				ContractImg beforeImg = mapContractImg.get(img.getSeqNum());
				if (beforeImg == null) {
					throw new RuntimeException("没有找到备案序号是" + img.getSeqNum()
							+ "的变更前料件。");
				}
				double diffAmount = CommonUtils.getDoubleExceptNull(tempImg
						.getAmount())
						- CommonUtils
								.getDoubleExceptNull(beforeImg.getAmount());
				if (diffAmount > 0) {
					tempImg.setAmount(diffAmount);
					tempImg.setTotalPrice(diffAmount
							* CommonUtils.getDoubleExceptNull(tempImg
									.getUnitPrice()));
					lsAdd.add(tempImg);
					addTotalPrice += CommonUtils.getDoubleExceptNull(tempImg
							.getTotalPrice());
				} else if (diffAmount < 0) {
					tempImg.setAmount(-diffAmount);
					tempImg.setTotalPrice(-diffAmount
							* CommonUtils.getDoubleExceptNull(tempImg
									.getUnitPrice()));
					lsDelete.add(tempImg);
					delTotalPrice += CommonUtils.getDoubleExceptNull(tempImg
							.getTotalPrice());
				}
			}
		}
		int iCount = 0;
		if (lsAdd.size() > lsDelete.size()) {
			iCount = lsAdd.size();
		} else {
			iCount = lsDelete.size();
		}
		int idiff = (iCount % 8);
		if (idiff > 0) {
			iCount = iCount + (8 - idiff);
		}
		if (iCount == 0) {
			iCount = 8;
		}
		int countdiff = iCount - lsAdd.size();
		for (int i = 0; i < countdiff; i++) {
			lsAdd.add(new TempChangedContractImgExg());
		}
		countdiff = iCount - lsDelete.size();
		for (int i = 0; i < countdiff; i++) {
			lsDelete.add(new TempChangedContractImgExg());
		}
		for (int i = 0; i < lsAdd.size(); i++) {
			TempChangedContractImgExg tempImg = (TempChangedContractImgExg) lsAdd
					.get(i);
			if (i % 8 == 0) {
				groupNo++;
			}
			tempImg.setGroupNo(groupNo);
		}
		groupNo = 0;
		for (int i = 0; i < lsDelete.size(); i++) {
			TempChangedContractImgExg tempImg = (TempChangedContractImgExg) lsDelete
					.get(i);
			if (i % 8 == 0) {
				groupNo++;
			}
			tempImg.setGroupNo(groupNo);
		}
		datas[0] = lsDelete;
		datas[1] = lsAdd;
		datas[2] = new ArrayList();
		datas[2].add(addTotalPrice - delTotalPrice);// 返回增加-减少的金额
		datas[2].add(iCount % 8 == 0 ? new Integer(iCount / 8) : new Integer(
				iCount / 8 + 1));// 返回页数
		return datas;
	}

	/**
	 * 查询变更的出口成品
	 * 
	 * @param contract
	 * @return
	 */
	public List[] findChangedContractExg(Contract contract) {
		List[] datas = new ArrayList[3];
		List<TempChangedContractImgExg> lsAdd = new ArrayList<TempChangedContractImgExg>();
		List<TempChangedContractImgExg> lsDelete = new ArrayList<TempChangedContractImgExg>();
		int groupNo = 0;
		Double addTotalPrice = 0.0, delTotalPrice = 0.0;// 分别记录增加和删除的总金额
		List list = this.contractDao.findContractExgStateChanged(contract
				.getId());
		Map<Integer, ContractExg> mapContractExg = new HashMap<Integer, ContractExg>();
		if (list.size() > 0) {
			Contract exingContract = this.contractDao
					.findExingContractByEmsNo(contract.getEmsNo());
			if (exingContract == null) {
				throw new RuntimeException("没有找到正在执行的手册：" + contract.getEmsNo());
			}
			List<ContractExg> lsBefore = this.contractDao
					.findContractExgByParentId(exingContract.getId());
			for (ContractExg exg : lsBefore) {
				mapContractExg.put(exg.getSeqNum(), exg);
			}
		}
		for (int i = 0; i < list.size(); i++) {
			ContractExg exg = (ContractExg) list.get(i);
			TempChangedContractImgExg tempExg = new TempChangedContractImgExg();
			// if (i % 8 == 0) {
			// groupNo++;
			// }
			// tempImg.setGroupNo(groupNo);
			tempExg.setSeqNum(exg.getSeqNum());
			tempExg.setComplex(exg.getComplex());
			tempExg.setAmount(exg.getExportAmount());
			tempExg.setName(exg.getName());
			tempExg.setSpec(exg.getSpec());
			tempExg.setUnit(exg.getUnit());
			tempExg.setCountry(exg.getCountry());
			tempExg.setLevyMode(exg.getLevyMode());
			tempExg.setUnitPrice(exg.getUnitPrice());
			tempExg.setTotalPrice(exg.getTotalPrice());
			if (ModifyMarkState.ADDED.equals(exg.getModifyMark())) {
				lsAdd.add(tempExg);
				addTotalPrice += CommonUtils.getDoubleExceptNull(tempExg
						.getTotalPrice());
			} else if (ModifyMarkState.DELETED.equals(exg.getModifyMark())) {
				lsDelete.add(tempExg);
				delTotalPrice += CommonUtils.getDoubleExceptNull(tempExg
						.getTotalPrice());
			} else if (ModifyMarkState.MODIFIED.equals(exg.getModifyMark())) {
				ContractExg beforeExg = mapContractExg.get(exg.getSeqNum());
				if (beforeExg == null) {
					throw new RuntimeException("没有找到备案序号是" + exg.getSeqNum()
							+ "的变更前成品。");
				}
				double diffAmount = CommonUtils.getDoubleExceptNull(tempExg
						.getAmount())
						- CommonUtils.getDoubleExceptNull(beforeExg
								.getExportAmount());
				if (diffAmount > 0) {
					tempExg.setAmount(diffAmount);
					tempExg.setTotalPrice(diffAmount
							* CommonUtils.getDoubleExceptNull(tempExg
									.getUnitPrice()));
					lsAdd.add(tempExg);
					addTotalPrice += CommonUtils.getDoubleExceptNull(tempExg
							.getTotalPrice());
				} else if (diffAmount < 0) {
					tempExg.setAmount(-diffAmount);
					tempExg.setTotalPrice(-diffAmount
							* CommonUtils.getDoubleExceptNull(tempExg
									.getUnitPrice()));
					lsDelete.add(tempExg);
					delTotalPrice += CommonUtils.getDoubleExceptNull(tempExg
							.getTotalPrice());
				}
			}
		}
		int iCount = 0;
		if (lsAdd.size() > lsDelete.size()) {
			iCount = lsAdd.size();
		} else {
			iCount = lsDelete.size();
		}
		int idiff = iCount % 8;
		iCount = iCount + (8 - idiff);
		if (iCount == 0) {
			iCount = 8;
		}
		int countdiff = iCount - lsAdd.size();
		for (int i = 0; i < countdiff; i++) {
			lsAdd.add(new TempChangedContractImgExg());
		}
		countdiff = iCount - lsDelete.size();
		for (int i = 0; i < countdiff; i++) {
			lsDelete.add(new TempChangedContractImgExg());
		}
		for (int i = 0; i < lsAdd.size(); i++) {
			TempChangedContractImgExg tempExg = (TempChangedContractImgExg) lsAdd
					.get(i);
			if (i % 8 == 0) {
				groupNo++;
			}
			tempExg.setGroupNo(groupNo);
		}
		groupNo = 0;
		for (int i = 0; i < lsDelete.size(); i++) {
			TempChangedContractImgExg tempExg = (TempChangedContractImgExg) lsDelete
					.get(i);
			if (i % 8 == 0) {
				groupNo++;
			}
			tempExg.setGroupNo(groupNo);
		}

		datas[0] = lsDelete;
		datas[1] = lsAdd;
		datas[2] = new ArrayList();
		datas[2].add(addTotalPrice - delTotalPrice);// 返回增加-减少的金额
		datas[2].add(iCount % 8 == 0 ? new Integer(iCount / 8) : new Integer(
				iCount / 8 + 1));// 返回页数
		return datas;
	}

	/**
	 * 保存合同备案料件
	 * 
	 * @param ls
	 * @param isOverwrite
	 */
	public void saveBcsImgFromImport(List ls, boolean isOverwrite) {
		Contract contract = null;
		/**
		 * 单价小数位控制
		 */
		int priceBit = getPriceBitFromParaSet();
		/**
		 * 数量小数位控制
		 */
		int countBit = getCountBitFromParaSet();
		/**
		 * 金额小数位控制
		 */
		int moneyBit = getMoneyBitFromParaSet();
		if (ls.size() > 0) {
			contract = ((TempContractImg) ls.get(0)).getContractImg()
					.getContract();
		} else {
			return;
		}
		// 先获取已有的数据
		List lsImgBill = this.contractDao.findContractImgByContractId(contract
				.getId());
		Map<Integer, ContractImg> imgMap = new HashMap<Integer, ContractImg>();
		for (int i = 0; i < lsImgBill.size(); i++) {
			ContractImg imgBill = (ContractImg) lsImgBill.get(i);
			imgMap.put(imgBill.getSeqNum(), imgBill);
		}

		for (int i = 0; i < ls.size(); i++) {
			ContractImg imgBill = imgMap.get(((TempContractImg) ls.get(i))
					.getContractImg().getSeqNum());
			if (imgBill == null) {// 如果不存在，则直接保存
				imgBill = ((TempContractImg) ls.get(i)).getContractImg();
				imgBill.setModifyMark(ModifyMarkState.ADDED);
				imgBill.setDeclarePrice(CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(imgBill
								.getDeclarePrice()), priceBit));
				imgBill.setAmount(CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(imgBill.getAmount()),
						countBit));
				imgBill.setTotalPrice(CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(imgBill
								.getDeclarePrice() * imgBill.getAmount()),
						moneyBit));
				this.contractDao.saveOrUpdate(imgBill);
			} else {
				if (isOverwrite) {
					ContractImg img = ((TempContractImg) ls.get(i))
							.getContractImg();
					imgBill.setComplex(img.getComplex());
					imgBill.setName(img.getName());
					imgBill.setSpec(img.getSpec());
					imgBill.setDeclarePrice(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(img
									.getDeclarePrice()), priceBit));

					imgBill.setUnit(img.getUnit());
					imgBill.setAmount(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(img.getAmount()),
							countBit));

					imgBill.setCredenceNo(img.getCredenceNo());
					imgBill.setCountry(img.getCountry());
					imgBill.setLevyMode(img.getLevyMode());
					imgBill.setIsMainImg(img.getIsMainImg());
					imgBill.setUnitWeight(img.getUnitWeight());
					imgBill.setNote(img.getNote());
					imgBill.setTotalPrice(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(imgBill
									.getDeclarePrice() * imgBill.getAmount()),
							moneyBit));

					if (ModifyMarkState.UNCHANGE
							.equals(imgBill.getModifyMark())
							|| ModifyMarkState.DELETED.equals(imgBill
									.getModifyMark())) {
						imgBill.setModifyMark(ModifyMarkState.MODIFIED);
					}
					this.contractDao.saveOrUpdate(imgBill);
				} else {
					continue;
				}
			}
		}
		this.statContractImgCount(contract);
		this.statContractImgMoney(contract);
	}

	/**
	 * 保存合同备案成品
	 * 
	 * @param ls
	 * @param isOverwrite
	 */
	public void saveBcsExgFromImport(List ls, boolean isOverwrite, String taskId) {

		BcsParameterSet parameter = this.contractDao.findBcsParameterSet();

		Contract contract = null;
		/**
		 * 单价小数位控制
		 */
		int priceBit = getPriceBitFromParaSet();
		/**
		 * 数量小数位控制
		 */
		int countBit = getCountBitFromParaSet();
		/**
		 * 金额小数位控制
		 */
		int moneyBit = getMoneyBitFromParaSet();
		if (ls.size() > 0) {
			contract = ((TempContractExg) ls.get(0)).getContractExg()
					.getContract();
		} else {
			return;
		}
		// 先获取已有的数据
		List lsImgBill = this.contractDao.findContractExgByParentId(contract
				.getId());
		Map<Integer, ContractExg> exgMap = new HashMap<Integer, ContractExg>();
		for (int i = 0; i < lsImgBill.size(); i++) {
			ContractExg exgBill = (ContractExg) lsImgBill.get(i);
			exgMap.put(exgBill.getSeqNum(), exgBill);
		}
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("系统正在保存数据,请稍等...");
			info.setTotalNum(ls.size());
		}
		long beginTime = System.currentTimeMillis();
		for (int i = 0; i < ls.size(); i++) {
			if (info != null) {
				info.setCurrentNum(i);
			}
			ContractExg exgBill = exgMap.get(((TempContractExg) ls.get(i))
					.getContractExg().getSeqNum());
			if (exgBill == null) {// 如果不存在，则直接保存
				exgBill = ((TempContractExg) ls.get(i)).getContractExg();
				exgBill.setModifyMark(ModifyMarkState.ADDED);
				exgBill.setUnitPrice(CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(exgBill.getUnitPrice()),
						priceBit));
				exgBill.setExportAmount(CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(exgBill
								.getExportAmount()), countBit));
				exgBill.setTotalPrice(CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(exgBill.getUnitPrice()
								* exgBill.getExportAmount()), moneyBit));
				exgBill.setProcessUnitPrice(CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(exgBill
								.getProcessUnitPrice()), priceBit));
				// -----------------------------edit by kcb
				double ptotal = (exgBill.getProcessUnitPrice() == null ? 0.0
						: exgBill.getProcessUnitPrice())
						* (exgBill.getExportAmount() == null ? 0.0 : exgBill
								.getExportAmount());
				exgBill.setProcessTotalPrice(CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(ptotal), moneyBit));
				// -----------------------------
				this.contractDao.saveOrUpdate(exgBill);
			} else {

				if (isOverwrite) {
					ContractExg img = ((TempContractExg) ls.get(i))
							.getContractExg();
					exgBill.setComplex(img.getComplex());
					exgBill.setName(img.getName());
					exgBill.setSpec(img.getSpec());
					exgBill.setUnitPrice(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(img.getUnitPrice()),
							priceBit));

					exgBill.setUnit(img.getUnit());
					exgBill.setExportAmount(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(img
									.getExportAmount()), countBit));

					exgBill.setCredenceNo(img.getCredenceNo());
					exgBill.setCountry(img.getCountry());
					exgBill.setLevyMode(img.getLevyMode());

					exgBill.setTotalPrice(CommonUtils.getDoubleByDigit(
							CommonUtils
									.getDoubleExceptNull(exgBill.getUnitPrice()
											* exgBill.getExportAmount()),
							moneyBit));

					exgBill.setUnitGrossWeight(img.getUnitGrossWeight());
					exgBill.setUnitNetWeight(img.getUnitNetWeight());
					exgBill.setProcessUnitPrice(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(img
									.getProcessUnitPrice()), priceBit));
					// -----------------------------edit by kcb
					double ptotal = (exgBill.getProcessUnitPrice() == null ? 0.0
							: exgBill.getProcessUnitPrice())
							* (exgBill.getExportAmount() == null ? 0.0
									: exgBill.getExportAmount());
					exgBill.setProcessTotalPrice(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(ptotal), moneyBit));
					// -----------------------------
					exgBill.setMaterialFee(img.getMaterialFee());
					exgBill.setNote(img.getNote());
					exgBill.setDutyRate(img.getDutyRate());
					if (ModifyMarkState.UNCHANGE
							.equals(exgBill.getModifyMark())
							|| ModifyMarkState.DELETED.equals(exgBill
									.getModifyMark())) {
						exgBill.setModifyMark(ModifyMarkState.MODIFIED);
					}
					this.contractDao.saveOrUpdate(exgBill);
				} else {
					continue;
				}
			}

			// 根据系统参数设置,判断是否需要反写合同料件
			if (parameter != null
					&& parameter.updateContractExgWriteBackBomImg != null
					&& parameter.updateContractExgWriteBackBomImg) {
				if (exgBill.getId() != null && !"".equals(exgBill.getId())) {
					this.writeBackContractBomImg(exgBill);
				}
			}
			if (info != null) {
				long endTime = System.currentTimeMillis();
				double m = ((double) (endTime - beginTime)) / (1000 * 60)
						* (ls.size() - i);
				System.out.println(m);
				beginTime = endTime;
				info.setMethodName("系统正在保存数据,估计剩余时间：" + (int) m + "分钟,请稍等...");
			}
		}
		this.statContractExgCount(contract);
		this.statContractExgMoney(contract);
	}

	/**
	 * 保存合同备案bom
	 * 
	 * @param ls
	 * @param isOverwrite
	 */
	public void saveContractEmsBomFromImport(List ls, boolean isOverwrite) {
		Contract contract = null;
		/**
		 * 单价小数位控制
		 */
		int priceBit = getPriceBitFromParaSet();
		/**
		 * 数量小数位控制
		 */
		int countBit = getCountBitFromParaSet();
		/**
		 * 金额小数位控制
		 */
		int moneyBit = getMoneyBitFromParaSet();

		// 导入Bom时是否反算
		BcsParameterSet parameter = this.contractDao.findBcsParameterSet();

		if (ls.size() > 0) {
			contract = ((TempContractBom) ls.get(0)).getContractBom()
					.getContractExg().getContract();
		} else {
			return;
		}

		List lsBomBill = this.contractDao
				.findContractBomByContractParentId(contract.getId());

		Map<String, ContractBom> bomMap = new HashMap<String, ContractBom>();
		for (int i = 0; i < lsBomBill.size(); i++) {
			ContractBom bomBill = (ContractBom) lsBomBill.get(i);
			bomMap.put((bomBill.getContractExg().getSeqNum() == null ? ""
					: bomBill.getContractExg().getSeqNum().toString())
					+ '/'
					+ (bomBill.getContractImgSeqNum() == null ? "" : bomBill
							.getContractImgSeqNum().toString()), bomBill);
		}
		Map<Integer, ContractImg> mapImg = new HashMap<Integer, ContractImg>();
		initContractImg(mapImg, contract.getId());

		for (int i = 0; i < ls.size(); i++) {
			ContractBom bom = ((TempContractBom) ls.get(i)).getContractBom();
			ContractBom bomBill = bomMap
					.get((bom.getContractExg().getSeqNum() == null ? "" : bom
							.getContractExg().getSeqNum().toString())
							+ '/'
							+ (bom.getContractImgSeqNum() == null ? "" : bom
									.getContractImgSeqNum().toString()));
			ContractExg contractExg = bom.getContractExg();
			// ContractImg contractImg =
			// this.contractDao.findContractImg(bom.getContractExg().getContract().getId(),
			// bom
			// .getContractImgSeqNum().toString());
			ContractImg contractImg = mapImg.get(bom.getContractImgSeqNum());
			if (bomBill == null) {
				bomBill = bom;
				Double exgAmount = contractExg.getExportAmount();
				Double unitDosage = bomBill.getUnitWaste()
						/ (1 - bomBill.getWaste() / 100);
				bomBill.setUnitDosage(CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(unitDosage), 9));
				bomBill.setNonMnlRatio(CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(bomBill.getNonMnlRatio()), 5));
				if (contractImg != null) {
					bomBill.setDeclarePrice(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(contractImg
									.getDeclarePrice()), priceBit));
				} else {
					bomBill.setDeclarePrice(0.0);
				}

				bomBill.setAmount(CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(exgAmount * unitDosage),
						countBit));
				bomBill.setTotalPrice(CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(bomBill.getAmount()
								* bomBill.getDeclarePrice()), moneyBit));

				bomBill.setModifyMark(ModifyMarkState.ADDED);
			} else {
				if (isOverwrite) {

					String allKey1 = bom.getUnitWaste() + "/" + bom.getWaste();
					String allKey2 = bomBill.getUnitWaste() + "/"
							+ bomBill.getWaste();
					if (!allKey1.equals(allKey2)
							&& ModifyMarkState.UNCHANGE.equals(bomBill
									.getModifyMark())) {
						bomBill.setModifyMark(ModifyMarkState.MODIFIED);
					}
					// if (bom.getWaste().equals(bomBill.getWaste())) {
					// if (bom.getUnitWaste().equals(bomBill.getUnitWaste())) {
					// bomBill.setModifyMark(ModifyMarkState.UNCHANGE);
					// } else {
					// if (ModifyMarkState.UNCHANGE.equals(bomBill
					// .getModifyMark())) {
					// bomBill.setModifyMark(ModifyMarkState.MODIFIED);
					// }
					// }
					// } else {
					// if (ModifyMarkState.UNCHANGE.equals(bomBill
					// .getModifyMark())) {
					// bomBill.setModifyMark(ModifyMarkState.MODIFIED);
					// }
					// }
					bomBill.setComplex(bom.getComplex());
					bomBill.setName(bom.getName());
					bomBill.setSpec(bom.getSpec());
					bomBill.setUnit(bom.getUnit());
					bomBill.setCountry(bom.getCountry());
					bomBill.setContractImgSeqNum(bom.getContractImgSeqNum());
					bomBill.setUnitWaste(bom.getUnitWaste());
					bomBill.setWaste(bom.getWaste());
					bomBill.setNonMnlRatio(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(bom
									.getNonMnlRatio()), 5));
					Double exgAmount = contractExg.getExportAmount();
					Double unitDosage = bomBill.getUnitWaste()
							/ (1 - bomBill.getWaste() / 100);
					bomBill.setUnitDosage(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(unitDosage), 9));
					bomBill.setAmount(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(exgAmount
									* unitDosage), countBit));

					bomBill.setDeclarePrice(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(contractImg
									.getDeclarePrice()), priceBit));
					bomBill.setTotalPrice(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(bomBill.getAmount()
									* bomBill.getDeclarePrice()), moneyBit));

				} else {
					continue;
				}
			}
			this.contractDao.saveOrUpdate(bomBill);

			if (parameter != null
					&& parameter.updateContractBomWriteBackExg != null
					&& parameter.updateContractBomWriteBackExg) {
				ContractExg tExg = bomBill.getContractExg();
				writeBackContractExg(tExg);
			}
			if (parameter != null
					&& parameter.updateContractBomWriteBackImg != null
					&& parameter.updateContractBomWriteBackImg) {
				// writeBackContractImg(bomBill.getContractExg().getContract().getId(),
				// bomBill);
				writeBackContractImg(bomBill.getContractExg().getContract()
						.getId(), mapImg.get(bomBill.getContractImgSeqNum()));
			}
		}

	}

	/**
	 * 初始化合同料件
	 * 
	 * @param map
	 */
	private void initContractImg(Map<Integer, ContractImg> map,
			String contractId) {
		List<ContractImg> lsImg = this.contractDao
				.findContractImgByContractId(contractId);
		for (int i = 0; i < lsImg.size(); i++) {
			ContractImg img = lsImg.get(i);
			map.put(img.getSeqNum(), img);
		}
	}

	/**
	 * 统计出口总金额
	 * 
	 * @param request
	 * @param contract
	 */
	public void getTotalPriceBExport(Contract contract) {
		int moneyBit = getMoneyBitFromParaSet();
		Double exgAmount = this.contractDao.getTotalPriceBExport(contract);
		contract.setExgAmount(CommonUtils.getDoubleByDigit(
				CommonUtils.getDoubleExceptNull(exgAmount), moneyBit));
		this.contractDao.saveOrUpdate(contract);

	}

	/**
	 * 统计进口总金额
	 * 
	 * @param request
	 * @param contract
	 */
	public void getTotalPriceBImport(Contract contract) {
		int moneyBit = getMoneyBitFromParaSet();
		Double imgAmount = this.contractDao.getTotalPriceBImport(contract);
		contract.setImgAmount(CommonUtils.getDoubleByDigit(
				CommonUtils.getDoubleExceptNull(imgAmount), moneyBit));
		this.contractDao.saveOrUpdate(contract);
	}

	/**
	 * 改变通关手册表头的申报状态
	 * 
	 * @param head
	 * @param declareState
	 */
	public Contract changeContractDeclareState(Contract head,
			String declareState) {
		head = (Contract) this.contractDao.load(head.getClass(), head.getId());
		if (head == null
				|| !DeclareState.WAIT_EAA.equals(head.getDeclareState())) {
			return head;
		}
		head.setDeclareState(declareState);
		this.contractDao.saveOrUpdate(head);
		return head;
	}

	/**
	 * 修改通关手册料件的修改标志
	 * 
	 * @param list
	 * @param modifyMark
	 */
	public void changeContractImgModifyMark(List list, String modifyMark) {
		if (ModifyMarkState.DELETED.equals(modifyMark)) {
			for (int i = 0; i < list.size(); i++) {
				ContractImg contractImg = (ContractImg) list.get(i);
				if (contractImg.getModifyMark().equals(modifyMark)) {
					continue;
				}
				// if
				// (ModifyMarkState.ADDED.equals(contractImg.getModifyMark())) {
				// // this.contractDao.deleteContractImg(contractImg);
				// contractImg.setModifyMark(ModifyMarkState.DELETED);
				// this.contractDao.saveContractImg(contractImg);
				// } else {
				int count = this.contractDao
						.findBcsCustomsDeclarationCommInfoCount(contractImg
								.getContract().getEmsNo(), contractImg
								.getSeqNum(), true);
				if (count > 0) {
					throw new RuntimeException("已经有报关单用到此料件"
							+ contractImg.getSeqNum() + ":"
							+ contractImg.getComplex().getCode() + ",所以不能删除");
				}
				contractImg.setModifyMark(ModifyMarkState.DELETED);
				this.contractDao.saveContractImg(contractImg);
				// }
				/**
				 * 查找合同BOM 来自 合同料件序号
				 */
				List contractBomList = this.contractDao
						.findContractBomByImgSeqNum(contractImg);
				this.deleteContractBom(contractBomList);
			}

		} else if (ModifyMarkState.UNCHANGE.equals(modifyMark)
				|| ModifyMarkState.MODIFIED.equals(modifyMark)
				// || ModifyMarkState.DELETED.equals(modifyMark)
				|| ModifyMarkState.ADDED.equals(modifyMark)) {
			for (int i = 0; i < list.size(); i++) {
				ContractImg contractImg = (ContractImg) list.get(i);
				// if (contractImg.getModifyMark().equals(modifyMark)) {
				// continue;
				// }
				// if
				// (ModifyMarkState.ADDED.equals(contractImg.getModifyMark())) {
				// continue;
				// } else
				if (ModifyMarkState.DELETED.equals(contractImg.getModifyMark())) {
					contractImg.setModifyMark(modifyMark);
					this.contractDao.saveContractImg(contractImg);
					/**
					 * 查找合同BOM 来自 合同料件序号
					 */
					List contractBomList = this.contractDao
							.findContractBomByImgSeqNum(contractImg);
					this.rollbackContractExg(contractBomList, modifyMark);
				} else {
					contractImg.setModifyMark(modifyMark);
					this.contractDao.saveContractImg(contractImg);
				}
			}
		}
		if (list.size() > 0) {
			Contract contract = ((ContractImg) list.get(0)).getContract();
			this.statContractImgCount(contract);
			this.statContractImgMoney(contract);
		}
	}

	/**
	 * 成品回卷
	 * 
	 * @param list
	 */
	private void rollbackContractExg(List list, String modifyMark) {
		if (list.size() <= 0) {
			return;
		}
		ContractExg contractExg = ((ContractBom) list.get(0)).getContractExg();
		BcsParameterSet parameter = this.contractDao.findBcsParameterSet();
		//
		// 删除料件Bom记录
		//
		for (int i = 0; i < list.size(); i++) {
			ContractBom bom = (ContractBom) list.get(i);
			bom.setModifyMark(modifyMark);
			this.contractDao.saveContractBom(bom);
			if (parameter != null
					&& parameter.updateContractBomWriteBackImg != null
					&& parameter.updateContractBomWriteBackImg) {
				this.writeBackContractImg(bom.getContractExg().getContract()
						.getId(), bom);
			}
		}
		/**
		 * 重新计算成品
		 */
		if (parameter != null
				&& parameter.updateContractBomWriteBackExg != null
				&& parameter.updateContractBomWriteBackExg) {
			writeBackContractExg(contractExg);
		}
	}

	/**
	 * 修改通关手册成品的修改标志
	 * 
	 * @param list
	 * @param modifyMark
	 */
	public void changeContractExgModifyMark(List list, String modifyMark) {
		if (ModifyMarkState.DELETED.equals(modifyMark)) {
			for (int i = 0; i < list.size(); i++) {
				ContractExg contractExg = (ContractExg) list.get(i);
				if (ModifyMarkState.DELETED.equals(contractExg.getModifyMark())) {
					continue;
				}
				/**
				 * 查找合同BOM 来自 合同成品ID
				 */
				List contractBomList = this.contractDao
						.findContractBomByExgId(contractExg.getId());
				/**
				 * 调用本地方法
				 */
				// this.deleteContractBom(contractBomList);
				// if
				// (ModifyMarkState.ADDED.equals(contractExg.getModifyMark())) {
				// // this.contractDao.deleteContractExg(contractExg);
				// contractExg.setModifyMark(ModifyMarkState.DELETED);
				// this.contractDao.saveContractExg(contractExg);
				// } else {
				int count = this.contractDao
						.findBcsCustomsDeclarationCommInfoCount(contractExg
								.getContract().getEmsNo(), contractExg
								.getSeqNum(), false);
				if (count > 0) {
					throw new RuntimeException("已经有报关单用到此成品"
							+ contractExg.getSeqNum() + ":"
							+ contractExg.getComplex().getCode() + ",所以不能删除");
				}
				contractExg.setModifyMark(ModifyMarkState.DELETED);
				this.contractDao.saveContractExg(contractExg);
				this.rollbackContractBom(contractBomList, modifyMark);
			}
			// }

		} else if (ModifyMarkState.UNCHANGE.equals(modifyMark)
				|| ModifyMarkState.MODIFIED.equals(modifyMark)
				|| ModifyMarkState.ADDED.equals(modifyMark)) {
			for (int i = 0; i < list.size(); i++) {
				ContractExg contractExg = (ContractExg) list.get(i);
				// if (contractExg.getModifyMark().equals(modifyMark)) {
				// continue;
				// }
				// if
				// (ModifyMarkState.ADDED.equals(contractExg.getModifyMark())) {
				// continue;
				// } else
				if (ModifyMarkState.DELETED.equals(contractExg.getModifyMark())) {
					contractExg.setModifyMark(modifyMark);
					this.contractDao.saveContractExg(contractExg);
					/**
					 * 查找合同BOM 来自 合同料件序号
					 */
					List contractBomList = this.contractDao
							.findContractBomByExgId(contractExg.getId());
					this.rollbackContractBom(contractBomList, modifyMark);
				} else {
					contractExg.setModifyMark(modifyMark);
					this.contractDao.saveContractExg(contractExg);
				}
			}
		}
		if (list.size() > 0) {
			Contract contract = ((ContractExg) list.get(0)).getContract();
			this.statContractExgCount(contract);
			this.statContractExgMoney(contract);
		}
	}

	/**
	 * 
	 * 
	 * @param list
	 *            是ContractBom型，合同BOM
	 * @param isCalculateFinishProductSum
	 *            是否要反算成品总金额，true为要反算
	 * @param isCalculateMaterielSum
	 *            是否要反算成品总金额，true为要反算
	 */
	private void rollbackContractBom(List list, String modifyMark) {
		if (list.size() <= 0) {
			return;
		}
		ContractBom temp = ((ContractBom) list.get(0));
		ContractExg contractExg = temp.getContractExg();
		BcsParameterSet parameter = this.contractDao.findBcsParameterSet();
		//
		// 删除料件Bom记录
		//
		for (int i = 0; i < list.size(); i++) {
			ContractBom bom = (ContractBom) list.get(i);
			bom.setModifyMark(modifyMark);
			this.contractDao.saveContractBom(bom);
			if (parameter != null
					&& parameter.updateContractBomWriteBackImg != null
					&& parameter.updateContractBomWriteBackImg) {
				this.writeBackContractImg(bom.getContractExg().getContract()
						.getId(), bom);
			}
		}
		/**
		 * 重新计算成品
		 */
		if (parameter != null
				&& parameter.updateContractBomWriteBackExg != null
				&& parameter.updateContractBomWriteBackExg) {
			writeBackContractExg(contractExg);
		}
		Contract contract = contractExg.getContract();
		this.statContractImgMoney(contract);
	}

	/**
	 * 修改通关手册单耗的修改标志
	 * 
	 * @param list
	 *            是ContractBom型，合同BOM
	 * @param isCalculateFinishProductSum
	 *            是否要反算成品总金额，true为要反算
	 * @param isCalculateMaterielSum
	 *            是否要反算成品总金额，true为要反算
	 */
	public void changeContractBomModifyMark(List list, String modifyMark) {
		if (list.size() <= 0) {
			return;
		}
		ContractBom temp = ((ContractBom) list.get(0));
		ContractExg contractExg = temp.getContractExg();
		if (ModifyMarkState.DELETED.equals(modifyMark)) {
			BcsParameterSet parameter = this.contractDao.findBcsParameterSet();
			//
			// 删除料件Bom记录
			//
			for (int i = 0; i < list.size(); i++) {
				ContractBom bom = (ContractBom) list.get(i);
				bom.setModifyMark(ModifyMarkState.DELETED);
				this.contractDao.saveContractBom(bom);
				if (parameter != null
						&& parameter.updateContractBomWriteBackImg != null
						&& parameter.updateContractBomWriteBackImg) {
					this.writeBackContractImg(bom.getContractExg()
							.getContract().getId(), bom);
				}
			}
			/**
			 * 重新计算成品
			 */
			if (parameter != null
					&& parameter.updateContractBomWriteBackExg != null
					&& parameter.updateContractBomWriteBackExg) {
				writeBackContractExg(contractExg);
			}
			Contract contract = contractExg.getContract();
			this.statContractImgMoney(contract);
		} else if (ModifyMarkState.UNCHANGE.equals(modifyMark)
				|| ModifyMarkState.MODIFIED.equals(modifyMark)
				|| ModifyMarkState.ADDED.equals(modifyMark)) {
			for (int i = 0; i < list.size(); i++) {
				ContractBom contractBom = (ContractBom) list.get(i);
				contractBom.setModifyMark(modifyMark);
				this.contractDao.saveContractBom(contractBom);
				if (ModifyMarkState.DELETED.equals(contractBom.getModifyMark())) {
					this.writeBackContractImg(contractBom.getContractExg()
							.getContract().getId(), contractBom);
				}
				if (ModifyMarkState.MODIFIED
						.equals(contractBom.getModifyMark())) {
					contractExg.setModifyMark(modifyMark);
					this.contractDao.saveContractExg(contractExg);
				}
			}
		}
	}

	/**
	 * 从QP的导出文件中导入通关手册资料
	 * 
	 * @param excelFileContent
	 */
	public Contract importContractFromQPExportFile(byte[] excelFileContent,
			String declareState, String dictPorEmsNo, String limitFlag,
			String taskId, boolean isCover) {

		Map<Integer, Integer> mapImg = new HashMap<Integer, Integer>();

		String xmlFileName = "BcsContractImportFormat.xml";

		// 这里开始导入 Excel 文件 里面的数据
		Map map = this.importDataFromExcel.importData(xmlFileName,
				excelFileContent, taskId, isCover, this, declareState);

		Map dataMap = (Map) map.get(Contract.class.getName());

		if (dataMap != null && dataMap.values().iterator().hasNext()) {

			Contract contract = (Contract) dataMap.values().iterator().next();

			contract.setIsContractEms(true);

			if (contract.getBeginDate() == null) {

				contract.setBeginDate(contract.getSaveDate());

			}
			contract.setCorrEmsNo(dictPorEmsNo);

			contract.setDeclareState(declareState);

			contract.setLimitFlag(limitFlag);

			this.contractDao.saveContract(contract);

			initContractImgMap(mapImg, contract.getId());

		}

		dataMap = (Map) map.get(ContractBom.class.getName());

		if (dataMap != null) {

			Iterator bomIterator = dataMap.values().iterator();

			while (bomIterator.hasNext()) {

				ContractBom bom = (ContractBom) bomIterator.next();

				ContractExg exg = bom.getContractExg();

				bom.setUnitDosage(CommonUtils.getDoubleByDigit(
						(CommonUtils.getDoubleExceptNull(bom.getUnitWaste()) / (1 - CommonUtils
								.getDoubleExceptNull(bom.getWaste()) / 100.0)),
						5));

				bom.setAmount(CommonUtils.getDoubleByDigit(
						(CommonUtils.getDoubleExceptNull(bom.getUnitWaste()) / (1 - CommonUtils
								.getDoubleExceptNull(bom.getWaste()) / 100.0))
								* CommonUtils.getDoubleExceptNull(exg
										.getExportAmount()), 5));

				bom.setTotalPrice(CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(bom.getAmount())
								* CommonUtils.getDoubleExceptNull(bom
										.getDeclarePrice()), 5));

				if (mapImg.get(bom.getContractImgSeqNum()) != null) {

					bom.setImgCredenceNo(mapImg.get(bom.getContractImgSeqNum()));

				}

				// 当非保税料件比例为空时，改为0
				if (bom.getNonMnlRatio() == null) {
					bom.setNonMnlRatio(0.0);
				}
				this.contractDao.saveContractBom(bom);
			}
		}
		dataMap = (Map) map.get(Contract.class.getName());
		if (dataMap != null) {
			Iterator headIterator = dataMap.values().iterator();
			while (headIterator.hasNext()) {
				return (Contract) headIterator.next();
			}
		}
		return null;
	}

	/**
	 * 初始化料件总表 用于查找合同bom的备案序号
	 * 
	 * @param parentId
	 *            表头id
	 * @return
	 */
	private void initContractImgMap(Map<Integer, Integer> mapImg,
			String parentId) {
		if (parentId == null) {
			return;
		}
		List<ContractImg> list = contractDao
				.findContractImgByContractId(parentId);
		for (int i = 0; i < list.size(); i++) {
			ContractImg img = list.get(i);
			mapImg.put(img.getSeqNum(), img.getCredenceNo());
		}
	}

	/**
	 * 合同国内购料清单表
	 */
	public List setContractDomesticPurchaseBill(String parentId) {
		List list1 = new ArrayList();
		List list = contractDao.findContractDomesticPurchaseBill(parentId);
		// TempContractDomesticPurchaseBill tcb=null;
		Integer serialNumber = null;
		String imgName = null;
		String unit = null;
		double amountForDomesticPurchase = 0.0;
		double wightForExgDomesticPurchase = 0.0;
		double imgWight = 0.0;
		double totalPrice = 0.0;
		for (int i = 0; i < list.size(); i++) {
			TempContractDomesticPurchaseBill tcb = new TempContractDomesticPurchaseBill();
			Object[] obj = (Object[]) list.get(i);
			serialNumber = (Integer) obj[0];
			imgName = (String) obj[1];
			unit = (String) obj[2];
			amountForDomesticPurchase = (Double) obj[3];
			wightForExgDomesticPurchase = (Double) obj[4];
			System.out.println("wightForExgDomesticPurchase="
					+ wightForExgDomesticPurchase);
			imgWight = (Double) obj[5];
			totalPrice = (Double) obj[6];
			tcb.setSerialNumber(serialNumber);
			tcb.setImgName(imgName);
			tcb.setUnit(unit);
			tcb.setAmountForDomesticPurchase(amountForDomesticPurchase);
			tcb.setWightForExgDomesticPurchase(wightForExgDomesticPurchase);
			tcb.setImgWight(imgWight);
			tcb.setTotalPrice(totalPrice);
			list1.add(tcb);
		}

		return list1;
	}

	/**
	 * 查找合同变更前成品
	 * 
	 * @param request
	 * @param contract
	 * @return
	 */
	public List findContractExgChangeBeforeByContract(Request request,
			Contract contract) {
		Contract excuteContract = contractDao.findExcuteContract(contract);
		if (excuteContract == null) {
			return null;
		}
		List<ContractExg> afterExgs = contractDao
				.findContractExgAfterModify(contract);
		if (afterExgs == null || afterExgs.size() == 0) {
			return null;
		}
		List<ContractExg> beforeExgs = new ArrayList<ContractExg>();
		for (ContractExg exg : afterExgs) {
			ContractExg beforeExg = contractDao.findContractExgBefore(
					excuteContract, exg);
			beforeExgs.add(beforeExg);
			// System.out.println(beforeExg.getName());//
		}
		return beforeExgs;
	}

	/**
	 * 查找合同变更后成品
	 * 
	 * @param request
	 * @param contract
	 * @return
	 */
	public List findContractExgChangeAfterByContract(Request request,
			Contract contract) {
		Contract excuteContract = contractDao.findExcuteContract(contract);
		if (excuteContract == null) {
			return null;
		}
		List<ContractExg> afterExgs = contractDao
				.findContractExgAfterModify(contract);
		if (afterExgs == null || afterExgs.size() == 0) {
			afterExgs = new ArrayList<ContractExg>();
		}
		List<ContractExg> beforeExgs = new ArrayList<ContractExg>();
		for (ContractExg exg : afterExgs) {
			ContractExg beforeExg = contractDao.findContractExgBefore(
					excuteContract, exg);
			beforeExgs.add(beforeExg);
			// System.out.println(beforeExg.getName());//
		}
		List<ContractExg> addExgs = contractDao
				.findContractExgAfterAddModify(contract);
		if (addExgs != null && addExgs.size() > 0) {
			for (ContractExg exg : addExgs) {
				afterExgs.add(exg);
			}
		}
		return afterExgs;
	}

	/**
	 * 查找合同变更前料件
	 * 
	 * @param request
	 * @param contract
	 * @return
	 */
	public List findContractImgChangeBeforeByContract(Request request,
			Contract contract) {
		Contract excuteContract = contractDao.findExcuteContract(contract);
		if (excuteContract == null) {
			return null;
		}
		List<ContractImg> afterImgs = contractDao
				.findContractImgAfterModify(contract);
		if (afterImgs == null || afterImgs.size() == 0) {
			return null;
		}
		List<ContractImg> beforeImgs = new ArrayList<ContractImg>();
		for (ContractImg exg : afterImgs) {
			ContractImg beforeImg = contractDao.findContractImgBefore(
					excuteContract, exg);
			beforeImgs.add(beforeImg);
			// System.out.println(beforeImg.getName());//
		}
		return beforeImgs;
	}

	/**
	 * 查找合同变更后料件
	 * 
	 * @param request
	 * @param contract
	 * @return
	 */
	public List findContractImgChangeAfterByContract(Request request,
			Contract contract) {
		Contract excuteContract = contractDao.findExcuteContract(contract);
		if (excuteContract == null) {
			return null;
		}
		List<ContractImg> afterImgs = contractDao
				.findContractImgAfterModify(contract);
		if (afterImgs == null || afterImgs.size() == 0) {
			afterImgs = new ArrayList<ContractImg>();
		}
		List<ContractImg> addImgs = contractDao
				.findContractImgAfterAddModify(contract);
		if (addImgs != null && addImgs.size() > 0) {
			for (ContractImg exg : addImgs) {
				afterImgs.add(exg);
			}
		}
		return afterImgs;
	}

	/**
	 * 成品变更表
	 * 
	 * @param contract
	 * @return
	 */
	public List findConractExgChangedByContract(Contract contract) {
		Contract excuteContract = contractDao.findExcuteContract(contract);
		if (excuteContract == null) {
			return null;
		}
		List<ContractExg> afterExgs = contractDao
				.findContractExgAfterModify(contract);
		if (afterExgs == null || afterExgs.size() == 0) {
			return null;
		}
		List<ContractExg> beforeExgs = new ArrayList<ContractExg>();
		for (ContractExg exg : afterExgs) {
			ContractExg beforeExg = contractDao.findContractExgBefore(
					excuteContract, exg);
			beforeExgs.add(beforeExg);
			// System.out.println(beforeExg.getName());//
		}
		List<ContractExg> addExgs = contractDao
				.findContractExgAfterAddModify(contract);
		if (addExgs != null && addExgs.size() > 0) {
			for (ContractExg exg : addExgs) {
				afterExgs.add(exg);
				beforeExgs.add(new ContractExg());
			}
		}
		// System.out.println(contract.getId());
		// System.out.println(excuteContract.getId());
		// System.out.println("修改后的有："+afterExgs.size());
		// System.out.println("修改前的有："+beforeExgs.size());
		if (afterExgs.size() % 8 != 0) {
			int n = 8 - afterExgs.size() % 8;
			for (int i = 0; i < n; i++) {
				ContractExg exg = new ContractExg();
				afterExgs.add(exg);
				beforeExgs.add(exg);
			}
		}
		List<ContractExg> printList = new ArrayList<ContractExg>();
		Double afterExgMoney = 0.0;
		Double beforeExgMoney = 0.0;
		for (int i = 0, size = beforeExgs.size(); i < size; i++) {
			beforeExgMoney += CommonUtils.getDoubleExceptNull(beforeExgs.get(i)
					.getTotalPrice());
			printList.add(beforeExgs.get(i));
			if (i > 0 && (i + 1) % 8 == 0) {
				ContractExg e = new ContractExg();
				e.setTotalPrice(beforeExgMoney);
				beforeExgMoney = 0.0;
				printList.add(e);
				printList.add(new ContractExg());
				printList.add(new ContractExg());
				for (int j = i - 7; j <= i; j++) {
					afterExgMoney += CommonUtils.getDoubleExceptNull(afterExgs
							.get(j).getTotalPrice());
					printList.add(afterExgs.get(j));
				}
				ContractExg e1 = new ContractExg();
				e1.setTotalPrice(afterExgMoney);
				afterExgMoney = 0.0;
				printList.add(e1);
			}
		}
		return printList;
	}

	/**
	 * 料件变更表
	 * 
	 * @param contract
	 * @return
	 */
	public List findConractImgChangedByContract(Contract contract) {
		Contract excuteContract = contractDao.findExcuteContract(contract);
		if (excuteContract == null) {
			return null;
		}
		List<ContractImg> afterImgs = contractDao
				.findContractImgAfterModify(contract);
		if (afterImgs == null || afterImgs.size() == 0) {
			return null;
		}
		List<ContractImg> beforeImgs = new ArrayList<ContractImg>();
		for (ContractImg img : afterImgs) {
			ContractImg beforeImg = contractDao.findContractImgBefore(
					excuteContract, img);
			beforeImgs.add(beforeImg);
			// System.out.println(beforeImg.getName());//
		}

		List<ContractImg> addImgs = contractDao
				.findContractImgAfterAddModify(contract);
		if (addImgs != null && addImgs.size() > 0) {
			for (ContractImg img : addImgs) {
				afterImgs.add(img);
				beforeImgs.add(new ContractImg());
			}
		}
		// System.out.println(contract.getId());
		// System.out.println(excuteContract.getId());
		// System.out.println("修改后的有："+afterImgs.size());
		// System.out.println("修改前的有："+beforeImgs.size());
		if (afterImgs.size() % 8 != 0) {
			int n = 8 - afterImgs.size() % 8;
			for (int i = 0; i < n; i++) {
				ContractImg Img = new ContractImg();
				afterImgs.add(Img);
				beforeImgs.add(Img);
			}
		}

		List<ContractImg> printList = new ArrayList<ContractImg>();
		Double afterImgMoney = 0.0;
		Double beforeImgMoney = 0.0;
		for (int i = 0, size = beforeImgs.size(); i < size; i++) {
			beforeImgMoney += CommonUtils.getDoubleExceptNull(beforeImgs.get(i)
					.getTotalPrice());
			printList.add(beforeImgs.get(i));
			if (i > 0 && (i + 1) % 8 == 0) {
				ContractImg e = new ContractImg();
				e.setTotalPrice(beforeImgMoney);
				beforeImgMoney = 0.0;
				printList.add(e);
				printList.add(new ContractImg());
				printList.add(new ContractImg());
				for (int j = i - 7; j <= i; j++) {
					afterImgMoney += CommonUtils.getDoubleExceptNull(afterImgs
							.get(j).getTotalPrice());
					printList.add(afterImgs.get(j));
				}
				ContractImg e1 = new ContractImg();
				e1.setTotalPrice(afterImgMoney);
				afterImgMoney = 0.0;
				printList.add(e1);
			}
		}
		return printList;
	}

	/**
	 * 查询通关合同备案中的料件
	 * 
	 * @param request
	 * @param contract
	 */
	// hwy20120925
	public List findMaterialInContract(List list) {
		// 过滤相同
		if (contractDao == null) {
			System.out.println("contractDao=null");
		} else {
			System.out.println("contractDao--live");
		}
		List<ContractImg> rlist = this.contractDao.findMaterialInContract(list);
		List<ContractImg> result = new ArrayList<ContractImg>();
		Map map = new HashMap<String, String>();
		for (ContractImg img : rlist) {
			String key = img.getComplex().getCode() + img.getName()
					+ img.getSpec() + img.getUnit().getName();
			if (!map.containsKey(key)) {
				map.put(key, "");
				result.add(img);
			}
		}
		return result;
	}

	/**
	 * 反写入料件总表同时放些bom 2010-06月-08hcl
	 */
	public List writeToImgAndBomTable(String parentId, ContractExg exg,
			List bomList) {
		List returnList = new ArrayList();
		HashMap map = new HashMap();// 合同料件Map
		HashMap BcsDictPorImgMap = new HashMap();// 备案资料库料件Map
		BcsParameterSet parameterSet = contractDao.findBcsParameterSet();
		// 得到数量，单价，金额小数位
		int countBit = parameterSet.getCountBit();
		int priceBit = parameterSet.getPriceBit();
		int moneyBit = parameterSet.getMoneyBit();
		// 按合同号得到所有料件
		List imgList = this.contractDao.findContractImgBySeqNum(parentId);
		// 备案资料库编号
		String corrEmsNo = exg.getContract().getCorrEmsNo();
		List BcsDictPorImgList = this.contractDao.findBcsDictPorImg(corrEmsNo);
		// 按成品得到所有Bom >>
		List list = this.contractDao.findCustomsBomDetailByExg(exg);

		Contract contract = exg.getContract();
		// 合同料件放map
		for (int i = 0; i < imgList.size(); i++) {
			ContractImg ci = (ContractImg) imgList.get(i);
			contract = ci.getContract();
			Integer key = ci.getCredenceNo();
			if (map.get(key) == null) {
				map.put(key, ci);
			}
		}
		// 资料库料件放map
		for (int i = 0; i < BcsDictPorImgList.size(); i++) {
			BcsDictPorImg ci = (BcsDictPorImg) BcsDictPorImgList.get(i);
			Integer key = ci.getInnerMergeSeqNum();// 以归并序号为KEY
			if (BcsDictPorImgMap.get(key) == null) {
				BcsDictPorImgMap.put(key, ci);
			}
		}
		// 查找合同料件最大序号（这里可用上面拿到的imgList进行优化）
		List MaxTenSeqList = this.contractDao.getMaxSeq(parentId);
		Integer MaxTenSeq = 0;

		if (MaxTenSeqList != null && null != MaxTenSeqList.get(0)
				&& !MaxTenSeqList.isEmpty())
			MaxTenSeq = (Integer) (MaxTenSeqList.get(0));

		StringBuilder err = new StringBuilder();
		for (int j = 0; j < list.size(); j++) {
			BcsCustomsBomDetail bsb = (BcsCustomsBomDetail) list.get(j);
			List<Integer> credenceNoList = this.contractDao
					.getImgCredenceNo(bsb);
			// 以记录号（备案资料库序号）为判断
			// 2013.10.9 HH 有可能备案资料库中的料件 与 报关单耗中的料件 不对应 所以做以下判断
			Integer tenSeqNum = null;
			if (!credenceNoList.isEmpty()) {
				tenSeqNum = credenceNoList.get(0);
			} else {
				err.append(bsb.getTenSeqNum()).append(",");
				continue;
			}

			ContractImg cci = null;
			if (map.get(tenSeqNum) != null) {
				cci = (ContractImg) map.get(tenSeqNum);

				cci.setAmount(CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(cci.getAmount()
								+ (exg.getExportAmount() == null ? 0.0 : exg
										.getExportAmount()
										* ((bsb.getUnitWare() == null && bsb
												.getWare() == null) ? 0.0
												: (bsb.getUnitWare() / (1 - bsb
														.getWare() * 0.01))))),
						countBit));
				cci.setTotalPrice(cci.getAmount() * cci.getDeclarePrice());

				// 2013.10.9 HH 已经存在料件总表中 修改为“已修改”标记
				cci.setModifyMark(ModifyMarkState.MODIFIED);
				this.contractDao.saveContractImg(cci);
				returnList.add(saveToBom(cci, bsb, exg, countBit, moneyBit,
						priceBit));
			} else { // 查无此料件需到备案资料库中查找增加
				MaxTenSeq++;
				BcsDictPorImg bImg = (BcsDictPorImg) BcsDictPorImgMap.get(bsb
						.getTenSeqNum());
				cci = new ContractImg();
				cci.setCredenceNo(tenSeqNum);
				// 料件数量=成品数量*（单耗/(1-损耗)）
				cci.setAmount(CommonUtils.getDoubleByDigit(
						CommonUtils
								.getDoubleExceptNull((exg.getExportAmount() == null ? 0.0
										: exg.getExportAmount()
												* ((bsb.getUnitWare() == null && bsb
														.getWare() == null) ? 0.0
														: (bsb.getUnitWare() / (1 - bsb
																.getWare() * 0.01))))),
						countBit));
				cci.setSeqNum(MaxTenSeq);
				cci.setName(bsb.getName());
				cci.setSpec(bsb.getSpec());
				cci.setComplex(bsb.getComplex());
				cci.setUnit(bsb.getUnit());
				cci.setContract(contract);
				cci.setDeclarePrice(CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(bImg.getUnitPrice() == null ? 0.0
								: bImg.getUnitPrice()), priceBit));
				cci.setTotalPrice(cci.getAmount() * cci.getDeclarePrice());

				// 2013.10.9 HH 不存在料件总表中 修改为“新增”标记
				cci.setModifyMark(ModifyMarkState.ADDED);
				this.contractDao.saveContractImg(cci);
				Integer key = cci.getCredenceNo();
				map.put(key, cci);
				returnList.add(saveToBom(cci, bsb, exg, countBit, moneyBit,
						priceBit));
			}

			// 2013.10.9 HH
			if (err.toString().trim() != null && err.toString().length() > 0) {
				throw new RuntimeException(
						"以下报关单耗料件｛(商品编码+商品名称+规格型号）为标识｝ 在备案资料库中不存在："
								+ err.toString());
			}
		}
		return returnList;
	}

	private ContractBom saveToBom(ContractImg cci, BcsCustomsBomDetail bsb,
			ContractExg exg, int countBit, int moneyBit, int priceBit) {
		ContractBom cb = null;
		List lists = new ArrayList();
		cb = new ContractBom();
		String name = bsb.getName() == null ? "" : bsb.getName();
		String spec = bsb.getSpec() == null ? "" : bsb.getSpec();
		Complex complex = bsb.getComplex();
		Double unitWare = bsb.getUnitWare() == null ? 0.0 : bsb.getUnitWare();
		Double ware = bsb.getWare() == null ? 0.0 : bsb.getWare();
		Double amount = CommonUtils
				.getDoubleByDigit(
						CommonUtils
								.getDoubleExceptNull((exg.getExportAmount() == null ? 0.0
										: exg.getExportAmount()
												* ((bsb.getUnitWare() == null && bsb
														.getWare() == null) ? 0.0
														: (bsb.getUnitWare() / (1 - bsb
																.getWare() * 0.01))))),
						countBit);
		Double unitDosage = bsb.getUnitDosage() == null ? 0.0 : bsb
				.getUnitDosage();
		Unit unit = bsb.getUnit();
		// 取已在合同中料件的序号
		Integer seqNum = cci.getSeqNum();
		cb.setContractImgSeqNum(seqNum == null ? 0 : seqNum);
		cb.setComplex(complex);
		cb.setContractExg(exg);
		cb.setName(name);
		cb.setSpec(spec);
		cb.setUnit(unit);
		cb.setUnitWaste(unitWare);
		cb.setWaste(ware);
		cb.setUnitDosage(unitDosage);
		cb.setAmount(amount);
		cb.setDeclarePrice(cci.getDeclarePrice());
		cb.setTotalPrice(amount * cci.getTotalPrice());
		cb.setModifyMark(ModifyMarkState.ADDED);
		this.contractDao.saveOrUpdate(cb);
		return cb;
	}

	/**
	 * 得到禁用商品
	 * 
	 * @param isMaterial
	 * @return
	 */
	public List getTempContractForBid(boolean isMaterial, String emsNo) {
		List list = contractDao.findEdiMaterielInfo(isMaterial, emsNo);
		List tempListExg = new ArrayList();
		if (isMaterial) {
			for (int i = 0; i < list.size(); i++) {
				ContractImg img = (ContractImg) list.get(i);
				BillTemp obj = new BillTemp();
				System.out.println("seqNum=" + img.getSeqNum());
				obj.setBill1(String.valueOf(img.getSeqNum()));
				obj.setBill2(img.getComplex() == null ? null : img.getComplex()
						.getCode());
				obj.setBill3(img.getName());
				obj.setBill4(img.getSpec());
				obj.setBill5(img.getUnit() == null ? null : img.getUnit()
						.getName());
				obj.setBill7(String.valueOf(img.getAmount() == null ? 0.0 : img
						.getAmount()));
				tempListExg.add(obj);
			}
		} else {
			for (int i = 0; i < list.size(); i++) {
				ContractExg exg = (ContractExg) list.get(i);
				BillTemp obj = new BillTemp();
				obj.setBill1(String.valueOf(exg.getSeqNum()));
				obj.setBill2(exg.getComplex() == null ? null : exg.getComplex()
						.getCode());
				obj.setBill3(exg.getName());
				obj.setBill4(exg.getSpec());
				obj.setBill5(exg.getUnit() == null ? null : exg.getUnit()
						.getName());
				obj.setBill7(String.valueOf(exg.getExportAmount() == null ? 0.0
						: exg.getExportAmount()));
				tempListExg.add(obj);
			}
		}
		return tempListExg;
	}

	public List addCommdityForbid(List list, boolean isMaterial, String emsNo) {
		List result = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			BillTemp temp = (BillTemp) list.get(i);
			BcsCommdityForbid bcsForbid = new BcsCommdityForbid();
			bcsForbid.setCompany(CommonUtils.getCompany());
			bcsForbid.setEmsNo(emsNo);
			bcsForbid.setSeqNum(temp.getBill1());
			bcsForbid.setComplex(temp.getBill2());
			bcsForbid.setName(temp.getBill3());
			bcsForbid.setSpec(temp.getBill4());
			bcsForbid.setUnit(temp.getBill5());
			bcsForbid.setVersion(temp.getBill6());
			bcsForbid.setForbiddate(dateToStandDate(new Date()));
			bcsForbid.setForbiduser(CommonUtils.getRequest().getUser()
					.getUserName());
			if (isMaterial) {
				bcsForbid.setType(MaterielType.MATERIEL);
			} else {
				bcsForbid.setType(MaterielType.FINISHED_PRODUCT);
			}
			contractDao.saveOrUpdate(bcsForbid);
			System.out.println("getbill1=" + temp.getBill1());
			changeEmsEdiForbid(emsNo, temp.getBill1(), isMaterial, true);
			result.add(bcsForbid);
		}
		return result;
	}

	/**
	 * 日期转成字符串
	 * 
	 * @param date
	 * @return
	 */
	public static Date dateToStandDate(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String defaultDate = bartDateFormat.format(date);
		return java.sql.Date.valueOf(defaultDate);
	}

	public void changeEmsEdiForbid(String emsNo, String seqNum,
			boolean isMateriel, boolean isForbid) {
		List list = null;
		System.out.println("isForbid=" + isForbid);
		if (isMateriel) {// 料件
			ContractImg img = contractDao.findContractImgByEmsNoSeqNum(emsNo,
					Integer.parseInt(seqNum));
			System.out.println("=L=isForbid=" + isForbid);
			img.setIsForbid(Boolean.valueOf(isForbid));
			contractDao.saveOrUpdate(img);
		} else {// 成品
			ContractExg exg = contractDao.findContractExgByEmsNoSeqNum(emsNo,
					Integer.parseInt(seqNum));
			System.out.println("=C=isForbid=" + isForbid);
			exg.setIsForbid(Boolean.valueOf(isForbid));
			contractDao.saveOrUpdate(exg);
		}
	}

	public void deleteCommdityForbid(BcsCommdityForbid obj) {
		contractDao.delete(obj);
	}

	/**
	 * 判断合同成品表、及单耗表的修改标志是否一致
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同表头
	 */
	public String checkContractIsUnitModifyMarkExgBom(Contract contract) {
		StringBuffer message = new StringBuffer();
		List<ContractExg> lsExg = this.contractDao
				.findContractExgByParentId(contract.getId());
		for (ContractExg exg : lsExg) {
			List bomList = this.contractDao.findContractBomByExgId(contract,
					exg.getId());
			for (int i = 0; i < bomList.size(); i++) {
				ContractBom bom = (ContractBom) bomList.get(i);
				if (!bom.getModifyMark().equals(exg.getModifyMark())) {
					message.append(exg.getSeqNum().toString() + ",");
					break;
				}
			}
		}
		return message.toString();
	}

	/**
	 * 获得最大的进口合同号
	 */
	public String getMaxImpContractNo() {
		List list = this.contractDao.findAllImpContractNo();
		String newContractNo = producedMaxContractNoSerialNumber(list, true);
		return newContractNo;
	}

	/**
	 * 获得最大的出口合同号
	 */
	public String getMaxExpContractNo() {
		List list = this.contractDao.findAllExpContractNo();
		String newContractNo = producedMaxContractNoSerialNumber(list, false);
		return newContractNo;
	}

	/**
	 * 根据现有合同号尾数三位流水号生成最大合同号
	 */
	private String producedMaxContractNoSerialNumber(List list, boolean impOrExp) {
		String ontractNo = "";
		String serialNumber = "000";
		String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		Company com = this.contractDao.findCompany();
		String code = com.getCode().substring(5);
		if (list == null) {
			ontractNo = year + code + serialNumber;
		} else {
			List<String> nums = new ArrayList<String>();
			Pattern p = Pattern.compile("^[0-9]*$");
			Matcher m;
			String contractNo;
			for (int i = 0; i < list.size(); i++) {
				contractNo = (String) list.get(i);
				// 判断合同号最后三位是否为数字，获得合同号最后三位数字
				if (contractNo != null && !"".equals(contractNo)
						&& contractNo.length() > 11) {
					String num = contractNo.toString().substring(
							contractNo.length() - 3, contractNo.length());
					m = p.matcher(num);
					if (m.find()) {
						nums.add(num);
					}
				}
			}
			if (nums != null && !nums.isEmpty()) {
				// 排序
				Collections.sort(nums, new Comparator<String>() {
					@Override
					public int compare(String num1, String num2) {
						return new Integer(num1).compareTo(new Integer(num2));
					}
				});
			} else {
				nums.add("000");
			}
			serialNumber = nums.get(nums.size() - 1);
			Pattern p1 = Pattern.compile("^00[0-9]$");// 00n
			Pattern p2 = Pattern.compile("^0[1-9][0-9]$");// 0nn
			Pattern p3 = Pattern.compile("^[1-9][0-9]{2}$");// nnn
			if (p1.matcher(serialNumber).find()) {
				Integer addNum = Integer.parseInt(serialNumber
						.substring(serialNumber.length() - 1)) + 1;// 原来数字个位+1
				if (addNum > 9) {// 替换十位,个位
					String addNumStr = String.valueOf(addNum);
					serialNumber = serialNumber.substring(0,
							serialNumber.length() - 2)
							+ addNumStr;
				} else {// 替换个位
					String addNumStr = String.valueOf(addNum);
					serialNumber = serialNumber.substring(0,
							serialNumber.length() - 1)
							+ addNumStr;
				}
			} else if (p2.matcher(serialNumber).find()) {
				Integer addNum = Integer.parseInt(serialNumber
						.substring(serialNumber.length() - 2)) + 1;// 原来数字+1
				if (addNum > 99) {// 替换百位,十位,个位
					serialNumber = String.valueOf(addNum);
				} else {// 替换十位,个位
					String addNumStr = String.valueOf(addNum);
					serialNumber = serialNumber.substring(0,
							serialNumber.length() - 2)
							+ addNumStr;
				}
			} else if (p3.matcher(serialNumber).find()) {
				serialNumber = String
						.valueOf(Integer.parseInt(serialNumber) + 1);// 原来数字+1
			}
		}
		boolean isExist = false;
		if (impOrExp) {// 判断生成的合同号是否已经存在
			isExist = this.checkImpContractNoIsExist(serialNumber);
		} else {
			isExist = this.checkExpContractNoIsExist(serialNumber);
		}
		if (isExist) {// 如果合同号存在，循环生成
			list.clear();
			list.add(serialNumber);
			producedMaxContractNoSerialNumber(list, impOrExp);
		}
		ontractNo = year + code + serialNumber;
		return ontractNo;
	}

	/**
	 * 判断进口合同号是否存在
	 * 
	 * @param request
	 *            请求控制
	 * @param contractNo
	 *            合同号
	 */
	public boolean checkImpContractNoIsExist(String contractNo) {
		List list = this.contractDao.findImpContractByContractNo(contractNo);
		if (list != null && !list.isEmpty()) {
			return true;// 存在
		}
		return false;
	}

	/**
	 * 判断出口合同号是否存在
	 * 
	 * @param request
	 *            请求控制
	 * @param contractNo
	 *            合同号
	 */
	public boolean checkExpContractNoIsExist(String contractNo) {
		List list = this.contractDao.findExpContractByContractNo(contractNo);
		if (list != null && !list.isEmpty()) {
			return true;// 存在
		}
		return false;
	}
}
