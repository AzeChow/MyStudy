/*
 * Created on 2005-3-28
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.fixtureonorder.logic;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.net.ns.Communication;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationCommInfo;
import com.bestway.bcus.cas.entity.FactoryAndFactualCustomsRalation;
import com.bestway.bcus.cas.entity.StatCusNameRelationHsn;
import com.bestway.bcus.custombase.entity.CustomBaseEntity;
import com.bestway.bcus.custombase.entity.FactoryAndFactualCustomsRalationEntity;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.PayMode;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.custombase.entity.parametercode.Uses;
import com.bestway.bcus.enc.entity.ImportApplyCustomsProperty;
import com.bestway.bcus.system.entity.Company;

import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.entity.ProjectDept;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;

import com.bestway.fixasset.entity.ChangeLocaOptionParam;
import com.bestway.fixasset.entity.TempImportEquipmentContract;

import com.bestway.fixtureonorder.dao.FixtureContractDao;
import com.bestway.fixtureonorder.entity.FixtureLocation;
import com.bestway.fixtureonorder.entity.FixtureLocationChangeBillInfo;
import com.bestway.fixtureonorder.entity.FixtureLocationResultInfo;
import com.bestway.fixtureonorder.entity.FixtureContract;
import com.bestway.fixtureonorder.entity.FixtureContractItems;
import com.bestway.fixtureonorder.entity.ImpExpCustomsDeclarationCommInfo;
import com.bestway.fixtureonorder.entity.TempCustomsDeclarationFixture;
import com.bestway.fixtureonorder.entity.TempFixtureCustomsDeclarCommInfo;
import com.bestway.fixtureonorder.entity.TempFixtureLocationChangeInfo;
import com.bestway.fixtureonorder.entity.TempOthersBillInfo;

/**
 * checked by cjb 2009.11
 * com.bestway.fixtureonorder.logic.FixtureContractLogic
 * 设备合同loginc服务层
 * @author fhz
 */
public class FixtureContractLogic {
	
	/**
	 * 设备合同dao数据库操作
	 */
	private FixtureContractDao fixtureContractDao = null;

	/**
	 * 获取contractDao
	 * 
	 * @return Returns the contractDao.
	 */
	public FixtureContractDao getFixtureContractDao() {
		return fixtureContractDao;
	}

	/**
	 * 设置contractDao
	 * 
	 * @param contractDao
	 *            The contractDao to set.
	 */
	public void setFixtureContractDao(FixtureContractDao fixtureContractDao) {
		this.fixtureContractDao = fixtureContractDao;
	}

	/**
	 * 删除合同表头
	 * 
	 * @param c
	 *            合同表头
	 */
	public void deleteContract(FixtureContract c, boolean isCheck) {
		List<FixtureContract> list = new ArrayList<FixtureContract>();
		if (list.add(c)) {
			this.deleteContract(list, isCheck);
		}
	}

	/**
	 * 删除合同表头
	 * 
	 * @param list
	 *            是Contract型，合同表头
	 */
	public void deleteContract(List<FixtureContract> list, boolean isCheck) {
		for (int i = 0; i < list.size(); i++) {
			FixtureContract c = list.get(i);
			String emsNo = c.getEmsNo();
			c = this.fixtureContractDao.findContractById(c.getId());
			if (c == null) {
				throw new RuntimeException("合同" + emsNo + "已经被删除");
			}
			if (isCheck && c.getDeclareState().equals(DeclareState.PROCESS_EXE)) {
				throw new RuntimeException("合同 " + c.getEmsNo() + " 已经生效不能删除");
			}

			/**
			 * 查找合同料件 来自 合同ID
			 */
			List contractImgList = this.fixtureContractDao
					.findContractItemByParentId(c.getId());
			if (contractImgList != null && contractImgList.size() > 0) {
				this.fixtureContractDao.deleteContractImg(contractImgList);
			}
		}
		this.fixtureContractDao.deleteContract(list);
	}

	/**
	 * 保存合同料件
	 * 
	 * @param list
	 *            是ContractImg型，合同料件
	 */
	public void saveContractItem(List<FixtureContractItems> list) {
		for (FixtureContractItems img : list) {
			this.fixtureContractDao.saveContractImg(img);

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
	public void deleteContractItem(List<FixtureContractItems> list) {
		this.fixtureContractDao.deleteContractImg(list);

	}

	/**
	 * 转抄合同数据
	 * 
	 * @param list
	 *            是Contract型，合同表头
	 */
	public List copyContract(List<FixtureContract> list) {
		List<FixtureContract> lsResult = new ArrayList<FixtureContract>();
		try {
			for (int i = 0; i < list.size(); i++) {
				FixtureContract c = (FixtureContract) BeanUtils.cloneBean(list
						.get(i));
				String contractId = c.getId();
				/**
				 * 转抄合同
				 */
				c.setId(null);
				c.setEmsNo(null);
				c.setDeclareCustoms(null);
				c.setImpContractNo(null);
				c.setExpContractNo(null);
				c.setApproveDate(null);
				c.setAvailabilityDate(null);
				c.setBeginDate(null);
				c.setEndDate(null);
				c.setDeferDate(null);
				c.setDeclareState(DeclareState.APPLY_POR);
				this.fixtureContractDao.saveContract(c);

				/**
				 * 查找合同料件 来自 合同ID
				 */
				Map<Integer, String> contractImgMap = new HashMap<Integer, String>();
				List contractImgList = this.fixtureContractDao
						.findContractItemByParentId(contractId);

				for (int j = 0; j < contractImgList.size(); j++) {
					FixtureContractItems contractImg = (FixtureContractItems) BeanUtils
							.cloneBean(contractImgList.get(j));
					/**
					 * 转抄料件
					 */
					contractImg.setId(null);
					contractImg.setContract(c);
					this.fixtureContractDao.saveContractImg(contractImg);
					//
					// 存入新的料件ID用于排序和变更时用
					//
					if (contractImg.getSeqNum() != null) {
						contractImgMap.put(contractImg.getSeqNum(), contractImg
								.getId());
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
	 * @param fixtureContract
	 *            合同表头
	 */
	public void putOnRecordContract(FixtureContract fixtureContract) {
		FixtureContract c = fixtureContract;

		/**
		 * 合同申报状态
		 */
		String declareState = c.getDeclareState();
		/**
		 * 如果是正在变更
		 */
		if (declareState.equals(DeclareState.CHANGING_EXE)) {
			/**
			 * 1.删除相同手册号并且是正在变更的记录
			 */
			FixtureContract contractTemp = this.fixtureContractDao
					.findContractByEmsNo(c.getEmsNo(), DeclareState.PROCESS_EXE);
			if (contractTemp != null) {
				this.deleteContract(contractTemp, false);
			}
			/**
			 * 2.把正在变更的记录状态改成正在执行
			 */
			c.setDeclareState(DeclareState.PROCESS_EXE);
			this.fixtureContractDao.saveContract(c);
		}
		/**
		 * 如果是正在申请
		 */
		else if (declareState.equals(DeclareState.APPLY_POR)) {
			//
			// 
			//
			/**
			 * 1.把正在申请的记录状态改成正在执行
			 */
			c.setDeclareState(DeclareState.PROCESS_EXE);
			this.fixtureContractDao.saveContract(c);
		}
		// /////////////////////////////////
		// 把合同料件标志改成正在备案
		// /////////////////////////////////

		/**
		 * 查找合同料件 来自 合同ID
		 */
		List contractImgList = this.fixtureContractDao
				.findContractItemByParentId(c.getId());
		for (int j = 0; j < contractImgList.size(); j++) {
			FixtureContractItems contractItem = (FixtureContractItems) contractImgList
					.get(j);
			/**
			 * 备案料件
			 */
			contractItem.setDeclareState(DeclareState.PROCESS_EXE);
			this.fixtureContractDao.saveContractImg(contractItem);
		}

	}

	/**
	 * 判断合同是否可以备案
	 * 
	 * @param contract
	 *            合同表头
	 */
	public String checkContractForPutOnRecord(FixtureContract contract) {
		StringBuffer message = new StringBuffer("");
		List<FixtureContractItems> lsImg = this.fixtureContractDao
				.findContractItemByParentId(contract.getId());
		for (FixtureContractItems img : lsImg) {
			if (img.getAmount() == null || img.getAmount() <= 0) {
				message.append("料件" + img.getSeqNum().toString() + "  "
						+ img.getName() + " 数量为空或等于零 \n");
			}
		}
		return message.toString();
	}

	/**
	 * 变更合同 如果返回null就不能变量 否则 就变更一条新的记录
	 * 
	 * @param fixtureContract
	 *            合同表头
	 * @return FixtureContract 合同表头
	 */
	public FixtureContract changingContract(FixtureContract fixtureContract) {
		FixtureContract c = (FixtureContract) fixtureContract.clone();
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
		FixtureContract contractTemp = this.fixtureContractDao
				.findContractByEmsNo(c.getEmsNo(), DeclareState.CHANGING_EXE);
		if (contractTemp != null) {
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
		this.fixtureContractDao.saveContract(c);
		Map<Integer, String> contractItemMap = new HashMap<Integer, String>();

		/**
		 * 查找合同料件 来自 合同ID
		 */
		List contractItemList = this.fixtureContractDao
				.findContractItemByParentId(contractId);
		for (int j = 0; j < contractItemList.size(); j++) {
			FixtureContractItems contractItem = new FixtureContractItems();
			try {
				PropertyUtils.copyProperties(contractItem,
						(FixtureContractItems) contractItemList.get(j));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			/**
			 * 转抄料件
			 */
			contractItem.setId(null);
			contractItem.setContract(c);
			contractItem.setChangeAmount(contractItem.getAmount());
			contractItem.setIsChange(false);
			this.fixtureContractDao.saveContractImg(contractItem);
			//
			// 存入新的料件ID用于排序和变更时用
			//
			if (contractItem.getSeqNum() != null) {
				contractItemMap.put(contractItem.getSeqNum(), contractItem
						.getId());
			}
		}
		return c;
	}

	/**
	 * 合同料件数据取整
	 * 
	 * @param list
	 *            是ContractImg型，合同料件
	 */
	public void saveContractItemAmountInteger(List<FixtureContractItems> list) {
		for (int i = 0; i < list.size(); i++) {
			FixtureContractItems contractItems = (FixtureContractItems) list
					.get(i);
			if (contractItems.getAmount() != null) {
				Double amount = Double.valueOf(String.valueOf(Math
						.round(contractItems.getAmount())));
				contractItems.setAmount(amount);
			}
			contractItems.setTotalPrice(CommonUtils
					.getDoubleExceptNull(contractItems.getAmount())
					* CommonUtils.getDoubleExceptNull(contractItems
							.getDeclarePrice()));
			this.fixtureContractDao.saveContractImg(contractItems);
		}
	}

	/**
	 * 保存合同料件
	 * 
	 * @param contractImg
	 *            合同料件
	 */
	public void saveContractImg(FixtureContractItems contractImg) {
		this.fixtureContractDao.saveContractImg(contractImg);

	}

	/**
	 * 变更料件的商品编码
	 * 
	 * @param contractItem
	 * @param complex
	 *            "CustomsDeclarationCommInfo", impExpTypes));
	 *            lsResult.addAll(this.fixtureContractDao.findCustomsDeclaration(
	 *            "BcsCustomsDeclarationCommInfo", impExpTypes));
	 *            lsResult.addAll(this.fixtureContractDao.findCustomsDeclaration(
	 *            "DzscCustomsDeclarationCommInfo", impExpTypes));
	 */
	public void changeContractItemComplex(FixtureContractItems contractItem,
			Complex complex) {
		if (contractItem.getId() != null && !"".equals(contractItem.getId())) {

			// 变更报关单里的商品编码
			List list = this.fixtureContractDao.findCustomsDeclarationCommInfo(
					"CustomsDeclarationCommInfo", contractItem.getContract(),
					contractItem.getSeqNum());
			List list1 = this.fixtureContractDao
					.findCustomsDeclarationCommInfo(
							"BcsCustomsDeclarationCommInfo", contractItem
									.getContract(), contractItem.getSeqNum());
			List list2 = this.fixtureContractDao
					.findCustomsDeclarationCommInfo(
							"DzscCustomsDeclarationCommInfo", contractItem
									.getContract(), contractItem.getSeqNum());
			if (list1.size() > 0)
				list.add(list1);
			if (list2.size() > 0)
				list.add(list2);
			for (int i = 0; i < list.size(); i++) {
				BaseCustomsDeclarationCommInfo commInfo = (BaseCustomsDeclarationCommInfo) list
						.get(i);
				commInfo.setComplex(complex);
				this.fixtureContractDao.saveOrUpdate(commInfo);

				// 变更设备位置里的商品编码
				List arrayList = this.fixtureContractDao
						.findFixtureLocationResultInfo(contractItem
								.getContract().getEmsNo(), commInfo
								.getBaseCustomsDeclaration()
								.getCustomsDeclarationCode(), commInfo
								.getCommSerialNo().toString());
				for (int j = 0; j < arrayList.size(); j++) {
					FixtureLocationResultInfo fixtureLocationResultInfo = (FixtureLocationResultInfo) arrayList
							.get(j);
					fixtureLocationResultInfo.setComplex(complex);
					this.fixtureContractDao
							.saveOrUpdate(fixtureLocationResultInfo);
				}

				// 变更设备移动单据里的商品编码
				arrayList = this.fixtureContractDao
						.findFixtureLocationChangeBillInfo(contractItem
								.getContract().getEmsNo(), commInfo
								.getBaseCustomsDeclaration()
								.getCustomsDeclarationCode(), commInfo
								.getCommSerialNo().toString());
				for (int j = 0; j < arrayList.size(); j++) {
					FixtureLocationChangeBillInfo fixtureLocationChangeBillInfo = (FixtureLocationChangeBillInfo) arrayList
							.get(j);
					fixtureLocationChangeBillInfo.setComplex(complex);
					this.fixtureContractDao
							.saveOrUpdate(fixtureLocationChangeBillInfo);
				}

			}

		}
		contractItem.setComplex(complex);
		this.fixtureContractDao.saveContractImg(contractItem);
	}

	/**
	 * 变更合同料件序号
	 * 
	 * @param img
	 *            合同料件
	 * @param newSeqNum
	 *            新序号
	 */
	public void changeContractItemSeqNum(FixtureContractItems img,
			Integer newSeqNum) {
		if (this.fixtureContractDao.findContractItems(
				img.getContract().getId(), newSeqNum.toString()) != null) {
			if (newSeqNum < img.getSeqNum()) {
				List list = this.fixtureContractDao
						.findContractImgBeginAndEndSeqNum(img.getContract()
								.getId(), newSeqNum, img.getSeqNum() - 1);
				for (int i = 0; i < list.size(); i++) {
					FixtureContractItems contractImg = (FixtureContractItems) list
							.get(i);
					contractImg.setSeqNum(contractImg.getSeqNum() + 1);
					this.fixtureContractDao.saveContractImg(contractImg);
				}
			} else {
				List list = this.fixtureContractDao
						.findContractImgBeginAndEndSeqNum(img.getContract()
								.getId(), img.getSeqNum() + 1, newSeqNum);
				for (int i = 0; i < list.size(); i++) {
					FixtureContractItems contractImg = (FixtureContractItems) list
							.get(i);
					contractImg.setSeqNum(contractImg.getSeqNum() - 1);
					this.fixtureContractDao.saveContractImg(contractImg);

				}
			}
		}
		img.setSeqNum(newSeqNum);
		this.fixtureContractDao.saveContractImg(img);
	}

	/**
	 * 保存合同料件来自对应关系
	 * 
	 * @param contract
	 *            合同表头
	 * @param list
	 *            是Complex型，自用商品编码
	 * @return List 是ContractImg型，合同料件
	 */
	public List saveComtractItemByComplex(FixtureContract contract, List list) {
		List contractImgList = new ArrayList();
		int maxSeqNum = this.fixtureContractDao
				.getMaxContractItemSeqNum(contract.getId()) + 1;
		for (int i = 0; i < list.size(); i++) {
			FixtureContractItems img = new FixtureContractItems();
			FactoryAndFactualCustomsRalationEntity f = (FactoryAndFactualCustomsRalationEntity) list.get(i);
			img.setSeqNum(maxSeqNum + i);
			img.setCompany(CommonUtils.getCompany());
			img.setContract(contract);
			img.setName(f.getHsCusName());
			img.setSpec(f.getHsCusSpec());
			img.setUnit(f.getHsCusUnit());
			img.setDeclareState(DeclareState.APPLY_POR);
			img.setComplex(f.getComplex());
			img.setPtNo(f.getMaterielNo());
			img.setAmount(1.0);
			// img.setLegalUnit(c.getFirstUnit());
			// img.setSecondUnit(c.getSecondUnit());
			contractImgList.add(img);
		}
		this.fixtureContractDao.saveContractImg(contractImgList);
		return contractImgList;
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
	public Object changeContractCommNameSpec(FixtureContract fixtureContract,
			FixtureContractItems contractItem, Integer seqNum, String name,
			String spec) {
		Object obj = null;

		FixtureContractItems item = this.fixtureContractDao.findContractItems(
				fixtureContract.getId(), seqNum.toString());
		//变更设备协议里的设备名称和规格
		item.setName(name);
		item.setSpec(spec);
		obj = item;
		this.fixtureContractDao.saveContractImg(item);
		
		// 变更报关单里的设备名称和规格
		List list = this.fixtureContractDao.findCustomsDeclarationCommInfo(
				"CustomsDeclarationCommInfo", contractItem.getContract(),
				contractItem.getSeqNum());
		List list1 = this.fixtureContractDao
				.findCustomsDeclarationCommInfo(
						"BcsCustomsDeclarationCommInfo", contractItem
								.getContract(), contractItem.getSeqNum());
		List list2 = this.fixtureContractDao
				.findCustomsDeclarationCommInfo(
						"DzscCustomsDeclarationCommInfo", contractItem
								.getContract(), contractItem.getSeqNum());
		if (list1.size() > 0)
			list.add(list1);
		if (list2.size() > 0)
			list.add(list2);
		for (int i = 0; i < list.size(); i++) {
			BaseCustomsDeclarationCommInfo commInfo = (BaseCustomsDeclarationCommInfo) list
					.get(i);
			commInfo.setCommName(name);
			commInfo.setCommSpec(spec);
			this.fixtureContractDao.saveOrUpdate(commInfo);

			// 变更设备位置里的设备名称和规格
			List arrayList = this.fixtureContractDao
					.findFixtureLocationResultInfo(contractItem
							.getContract().getEmsNo(), commInfo
							.getBaseCustomsDeclaration()
							.getCustomsDeclarationCode(), commInfo
							.getCommSerialNo().toString());
			for (int j = 0; j < arrayList.size(); j++) {
				FixtureLocationResultInfo fixtureLocationResultInfo = (FixtureLocationResultInfo) arrayList
						.get(j);
				fixtureLocationResultInfo.setCommName(name);
				fixtureLocationResultInfo.setCommSpec(spec);
				this.fixtureContractDao
						.saveOrUpdate(fixtureLocationResultInfo);
			}

			// 变更设备移动单据里的设备名称和规格
			arrayList = this.fixtureContractDao
					.findFixtureLocationChangeBillInfo(contractItem
							.getContract().getEmsNo(), commInfo
							.getBaseCustomsDeclaration()
							.getCustomsDeclarationCode(), commInfo
							.getCommSerialNo().toString());
			for (int j = 0; j < arrayList.size(); j++) {
				FixtureLocationChangeBillInfo fixtureLocationChangeBillInfo = (FixtureLocationChangeBillInfo) arrayList
						.get(j);
				fixtureLocationChangeBillInfo.setCommName(name);
				fixtureLocationChangeBillInfo.setCommSpec(spec);
				this.fixtureContractDao
						.saveOrUpdate(fixtureLocationChangeBillInfo);
			}

		}
		
		
		return obj;
	}

	/**
	 * 抓取进口报关单项目
	 * 
	 * @return
	 */
	public List findCustomsDeclarationFixture() {
		List lsResult = new ArrayList();
		List list = this.findCustomsDeclarationStat();
		List lsCustomsInfo = this.fixtureContractDao
				.findExistFixtureCustomsInfo();
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			BaseCustomsDeclarationCommInfo commInfo = (BaseCustomsDeclarationCommInfo) objs[0];
			FixtureContract fixtureContract = (FixtureContract) objs[1];
			String temp = (commInfo.getBaseCustomsDeclaration()
					.getCustomsDeclarationCode() == null ? "" : commInfo
					.getBaseCustomsDeclaration().getCustomsDeclarationCode()
					.trim())
					+ "-"
					+ (commInfo.getCommSerialNo() == null ? "" : commInfo
							.getCommSerialNo().toString());
			if (lsCustomsInfo.contains(temp)) {
				continue;
			}
			FixtureContractItems fixtureContractItems=fixtureContractDao.findFixtureContractItems(fixtureContract, commInfo);
							
			TempCustomsDeclarationFixture tempFixasset = new TempCustomsDeclarationFixture();
			tempFixasset.setAgreementNo(fixtureContract.getAgreementNo());
			tempFixasset.setEmsNo(fixtureContract.getEmsNo());
			tempFixasset.setCustomsDeclarationCode(commInfo
					.getBaseCustomsDeclaration().getCustomsDeclarationCode());
			tempFixasset.setImpExpDate(commInfo.getBaseCustomsDeclaration()
					.getImpExpDate());
			tempFixasset.setCustomsBillSeqNo(commInfo
					.getBaseCustomsDeclaration().getSerialNumber().toString());
			tempFixasset.setCustomsDeclaItemNo(commInfo.getCommSerialNo()
					.toString());
			tempFixasset.setComplex(commInfo.getComplex());
			tempFixasset.setAmount(commInfo.getCommAmount());
			tempFixasset.setLocationAmount(commInfo.getCommAmount());
			tempFixasset.setCommName(commInfo.getCommName());
			tempFixasset.setCommSpec(commInfo.getCommSpec());
			if(fixtureContractItems!=null)
				tempFixasset.setFixKind(fixtureContractItems.getFixKind());
			lsResult.add(tempFixasset);
		}
		return lsResult;
	}

	/**
	 * 设备报关单查询
	 * 
	 * @param agreementNo
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	private List findCustomsDeclarationStat() {
		List lsResult = new ArrayList();
		Integer[] impExpTypes = null;
		impExpTypes = new Integer[] { ImpExpType.EQUIPMENT_IMPORT };
		lsResult.addAll(this.fixtureContractDao.findCustomsDeclaration(
				"CustomsDeclarationCommInfo", impExpTypes));
		lsResult.addAll(this.fixtureContractDao.findCustomsDeclaration(
				"BcsCustomsDeclarationCommInfo", impExpTypes));
		lsResult.addAll(this.fixtureContractDao.findCustomsDeclaration(
				"DzscCustomsDeclarationCommInfo", impExpTypes));
		return lsResult;
	}

	/**
	 * 保存最终异动单据资料 List contralDataList, List lsChangeFixture
	 * 
	 * @param list
	 */
	public void saveFixtureLocationChangeBillInfo(List list, int changeType) {
		for (int i = 0; i < list.size(); i++) {
			FixtureLocationChangeBillInfo changeBillInfo = (FixtureLocationChangeBillInfo) list
					.get(i);
			this.fixtureContractDao.saveOrUpdate(changeBillInfo);
			List lsTemp = this.fixtureContractDao
					.findFixtureLocationResultInfo(changeBillInfo
							.getCustomsDeclarationCode(), changeBillInfo
							.getCustomsDeclaItemNo());
			if (lsTemp.size() <= 0) {
				FixtureLocationResultInfo resultInfo = new FixtureLocationResultInfo();
				resultInfo.setAgreementNo(changeBillInfo.getAgreementNo());
				resultInfo.setCustomsDeclarationCode(changeBillInfo
						.getCustomsDeclarationCode());
				resultInfo.setImpExpDate(changeBillInfo.getImpExpDate());
				resultInfo.setCustomsBillSeqNo(changeBillInfo
						.getCustomsBillSeqNo());
				resultInfo.setCustomsDeclaItemNo(changeBillInfo
						.getCustomsDeclaItemNo());
				resultInfo.setComplex(changeBillInfo.getComplex());
				resultInfo.setAmount(changeBillInfo.getAmount());
				resultInfo.setAmount(changeBillInfo.getAmount());
				resultInfo.setEmsNo(changeBillInfo.getEmsNo());
				resultInfo.setCommName(changeBillInfo.getCommName());
				resultInfo.setCommSpec(changeBillInfo.getCommSpec());
				resultInfo.setLocation(changeBillInfo.getNewLocation());
				resultInfo.setFixKind(changeBillInfo.getFixKind());
				resultInfo.setSeqNo(1);
				this.fixtureContractDao.saveOrUpdate(resultInfo);
			} else {
				if (changeType == ChangeLocaOptionParam.FACT_SUBTRACT) {
					FixtureLocationResultInfo resultInfo = (FixtureLocationResultInfo) lsTemp
							.get(0);
					this.fixtureContractDao.delete(resultInfo);
				} else {
					FixtureLocationResultInfo resultInfo = (FixtureLocationResultInfo) lsTemp
							.get(0);
					resultInfo.setLocation(changeBillInfo.getNewLocation());
					this.fixtureContractDao.saveOrUpdate(resultInfo);
				}
			}
		}
	}

	/**
	 * 取得异动设备及其数量
	 * 
	 * @param list
	 * @param newLocation
	 * @return
	 */
	public List getFixtureLocationChangeInfoFromCustomsBill(
			List<TempCustomsDeclarationFixture> list,
			FixtureLocation newLocation, Integer changeType) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			TempCustomsDeclarationFixture tempFixasset = (TempCustomsDeclarationFixture) list
					.get(i);
			TempFixtureLocationChangeInfo tempChangeInfo = new TempFixtureLocationChangeInfo();
			tempChangeInfo.setAgreementNo(tempFixasset.getAgreementNo());
			tempChangeInfo.setCustomsDeclarationCode(tempFixasset
					.getCustomsDeclarationCode());
			tempChangeInfo.setImpExpDate(tempFixasset.getImpExpDate());
			tempChangeInfo.setCustomsBillSeqNo(tempFixasset
					.getCustomsBillSeqNo());
			tempChangeInfo.setCustomsDeclaItemNo(tempFixasset
					.getCustomsDeclaItemNo());
			tempChangeInfo.setComplex(tempFixasset.getComplex());
			tempChangeInfo.setAmount(tempFixasset.getAmount());
			tempChangeInfo.setLocationAmount(tempFixasset.getLocationAmount());
			tempChangeInfo.setEmsNo(tempFixasset.getEmsNo());
			tempChangeInfo.setCommName(tempFixasset.getCommName());
			tempChangeInfo.setCommSpec(tempFixasset.getCommSpec());
			tempChangeInfo.setFixKind(tempFixasset.getFixKind());
			tempChangeInfo.setNewLocation(newLocation);
			tempChangeInfo.setChangeType(changeType);
			lsResult.add(tempChangeInfo);
		}
		return lsResult;
	}

	/**
	 * 取得异动设备及其数量
	 * 
	 * @param list
	 * @param newLocation
	 * @return
	 */
	public List getFixtureLocationChangeInfoFromResultInfo(
			FixtureLocation newLocation, Integer changeType) {
		List lsResult = new ArrayList();
		List list = new ArrayList();
		if (changeType == ChangeLocaOptionParam.FACT_SUBTRACT) {
			list = this.fixtureContractDao.findFixtureLocationResultInfo();
		} else if (changeType == ChangeLocaOptionParam.FACT_CHANGE_LOCATION) {
			list = this.fixtureContractDao
					.fixtureLocationResultInfoByLocation(newLocation);
		}
		for (int i = 0; i < list.size(); i++) {
			FixtureLocationResultInfo resultInfo = (FixtureLocationResultInfo) list
					.get(i);
			TempFixtureLocationChangeInfo tempChangeInfo = new TempFixtureLocationChangeInfo();
			tempChangeInfo.setAgreementNo(resultInfo.getAgreementNo());
			tempChangeInfo.setCustomsDeclarationCode(resultInfo
					.getCustomsDeclarationCode());
			tempChangeInfo.setImpExpDate(resultInfo.getImpExpDate());
			tempChangeInfo
					.setCustomsBillSeqNo(resultInfo.getCustomsBillSeqNo());
			tempChangeInfo.setCustomsDeclaItemNo(resultInfo
					.getCustomsDeclaItemNo());
			tempChangeInfo.setEmsNo(resultInfo.getEmsNo());
			tempChangeInfo.setComplex(resultInfo.getComplex());
			tempChangeInfo.setAmount(resultInfo.getAmount());
			tempChangeInfo.setCommName(resultInfo.getCommName());
			tempChangeInfo.setLocationAmount(resultInfo.getLocationAmount());
			tempChangeInfo.setCommSpec(resultInfo.getCommSpec());
			tempChangeInfo.setOldLocation(resultInfo.getLocation());
			tempChangeInfo.setFixKind(resultInfo.getFixKind());
			tempChangeInfo.setNewLocation(newLocation);
			tempChangeInfo.setChangeType(changeType);
			lsResult.add(tempChangeInfo);
		}
		return lsResult;
	}

	/**
	 * 取得异动设备及其数量
	 * 
	 * @param list
	 * @param newLocation
	 * @return
	 */
	public List getFixtureLocationChangeInfoFromFactory(
			List<FixtureContractItems> list, FixtureLocation newLocation,
			Integer changeType) {
		List lsResult = new ArrayList();
		list = this.fixtureContractDao.findFixtureContractItems();
		for (int i = 0; i < list.size(); i++) {
			FixtureContractItems agreementCommInfo = (FixtureContractItems) list
					.get(i);
			TempFixtureLocationChangeInfo tempChangeInfo = new TempFixtureLocationChangeInfo();
			tempChangeInfo.setAgreementNo(agreementCommInfo.getContract()
					.getAgreementNo());
			tempChangeInfo.setComplex(agreementCommInfo.getComplex());
			tempChangeInfo.setAmount(agreementCommInfo.getAmount());
			tempChangeInfo.setLocationAmount(agreementCommInfo.getAmount());
			tempChangeInfo.setCommName(agreementCommInfo.getName());
			tempChangeInfo.setCommSpec(agreementCommInfo.getSpec());
			tempChangeInfo.setFixKind(agreementCommInfo.getFixKind());
			tempChangeInfo.setNewLocation(newLocation);
			tempChangeInfo.setChangeType(changeType);
			lsResult.add(tempChangeInfo);
		}
		return lsResult;
	}

	/**
	 * 取得最终异动单据资料
	 * 
	 * @param lsChangeFixture
	 * @param otherInfo
	 * @return
	 */
	public List getFixtureLocationChangeBillInfo(List contralDataList,
			List lsChangeFixture, TempOthersBillInfo otherInfo) {
		List lsResult = new ArrayList();
		for (int i = 0; i < lsChangeFixture.size(); i++) {
			TempFixtureLocationChangeInfo tempChangeInfo = (TempFixtureLocationChangeInfo) lsChangeFixture
					.get(i);
			FixtureLocationChangeBillInfo changeBillInfo = new FixtureLocationChangeBillInfo();
			changeBillInfo.setAgreementNo(tempChangeInfo.getAgreementNo());
			changeBillInfo.setEmsNo(tempChangeInfo.getEmsNo());
			changeBillInfo.setCustomsDeclarationCode(tempChangeInfo
					.getCustomsDeclarationCode());
			changeBillInfo.setImpExpDate(tempChangeInfo.getImpExpDate());
			changeBillInfo.setCustomsBillSeqNo(tempChangeInfo
					.getCustomsBillSeqNo());
			changeBillInfo.setCustomsDeclaItemNo(tempChangeInfo
					.getCustomsDeclaItemNo());
			changeBillInfo.setComplex(tempChangeInfo.getComplex());
			changeBillInfo.setAmount(tempChangeInfo.getAmount());
			changeBillInfo
					.setLocationAmount(tempChangeInfo.getLocationAmount());
			changeBillInfo.setCommName(tempChangeInfo.getCommName());
			changeBillInfo.setCommSpec(tempChangeInfo.getCommSpec());
			changeBillInfo.setOldLocation(tempChangeInfo.getOldLocation());
			changeBillInfo.setNewLocation(tempChangeInfo.getNewLocation());
			changeBillInfo.setBillCode(otherInfo.getBillCode());
			changeBillInfo.setHandMan(otherInfo.getHandMan());
			changeBillInfo.setChangeType(tempChangeInfo.getChangeType());
			changeBillInfo.setFixKind(tempChangeInfo.getFixKind());
			changeBillInfo.setChangeDate(new Date());
			lsResult.add(changeBillInfo);
		}
		return lsResult;
	}

	/**
	 * 设备报关单查询
	 * 
	 * @param emsNo
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List findCustomsDeclarationStat(String emsNo, Date beginDate,
			Date endDate, boolean isImport) {
		List lsResult = new ArrayList();
		Integer[] impExpTypes = null;
		if (isImport) {
			impExpTypes = new Integer[] { ImpExpType.EQUIPMENT_IMPORT };
		} else {
			impExpTypes = new Integer[] { ImpExpType.BACK_PORT_REPAIR,
					ImpExpType.EQUIPMENT_BACK_PORT };
		}
		lsResult.addAll(this.fixtureContractDao.findCustomsDeclaration(
				"CustomsDeclarationCommInfo", impExpTypes, emsNo, beginDate,
				endDate));
		lsResult.addAll(this.fixtureContractDao.findCustomsDeclaration(
				"BcsCustomsDeclarationCommInfo", impExpTypes, emsNo, beginDate,
				endDate));
		lsResult.addAll(this.fixtureContractDao.findCustomsDeclaration(
				"DzscCustomsDeclarationCommInfo", impExpTypes, emsNo,
				beginDate, endDate));
		return lsResult;
	}

	/**
	 * 设备报关单查询
	 * 
	 * @param emsNo
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List findCustomsDeclarationStat(String emsNo, Date beginDate,
			Date endDate, int state) {
		List lsResult = new ArrayList();
		Integer[] impExpTypes = null;
		impExpTypes = new Integer[] { ImpExpType.EQUIPMENT_IMPORT,
				ImpExpType.BACK_PORT_REPAIR, ImpExpType.EQUIPMENT_BACK_PORT };
		lsResult.addAll(this.fixtureContractDao.findCustomsDeclaration(
				"CustomsDeclarationCommInfo", impExpTypes, emsNo, beginDate,
				endDate, state));
		lsResult.addAll(this.fixtureContractDao.findCustomsDeclaration(
				"BcsCustomsDeclarationCommInfo", impExpTypes, emsNo, beginDate,
				endDate, state));
		lsResult.addAll(this.fixtureContractDao.findCustomsDeclaration(
				"DzscCustomsDeclarationCommInfo", impExpTypes, emsNo,
				beginDate, endDate, state));
		return lsResult;
	}

	/**
	 * 查询已报关的商品
	 * 
	 * @param isMaterial
	 *            判断是否料件，true为料件
	 * @param fixtureContract
	 *            是Contract型，合同备案表头
	 * @param state
	 *            生效类型
	 * @return List 是TempBcsCustomsDeclarCommInfo型，
	 */
	public List findCustomsDeclarationCommInfo(FixtureContract fixtureContract,
			int state) {
		List<TempFixtureCustomsDeclarCommInfo> lsResult = new ArrayList<TempFixtureCustomsDeclarCommInfo>();

		List list = findCustomsDeclarationStat(fixtureContract.getEmsNo(),
				null, null, state);
		for (int i = 0; i < list.size(); i++) {
			TempFixtureCustomsDeclarCommInfo commInfo = new TempFixtureCustomsDeclarCommInfo();
			if (((BaseCustomsDeclarationCommInfo) list.get(i))
					.getCommSerialNo() != null)
				commInfo.setSeqNum(((BaseCustomsDeclarationCommInfo) list
						.get(i)).getCommSerialNo());
			if (((BaseCustomsDeclarationCommInfo) list.get(i)).getComplex()
					.getCode() != null)
				commInfo.setCode(((BaseCustomsDeclarationCommInfo) list.get(i))
						.getComplex().getCode());
			if (((BaseCustomsDeclarationCommInfo) list.get(i)).getCommName() != null)
				commInfo.setName(((BaseCustomsDeclarationCommInfo) list.get(i))
						.getCommName());
			if (((BaseCustomsDeclarationCommInfo) list.get(i)).getCommSpec() != null)
				commInfo.setSpec(((BaseCustomsDeclarationCommInfo) list.get(i))
						.getCommSpec());

			lsResult.add(commInfo);
		}
		return lsResult;
	}

	/**
	 * 查询已报关的客户
	 * 
	 * @param isImport
	 *            进口判断，true为进口
	 * @param lsContract
	 *            是Contract型，合同备案表头
	 * @param state
	 *            生效类型
	 * @return List 是customer型，存放了已报关的客户
	 */
	public List findCustomsDeclarationCustomer(FixtureContract fixtureContract,
			int state) {
		List lsResult = new ArrayList();
		Integer[] impExpTypes = null;
		impExpTypes = new Integer[] { ImpExpType.EQUIPMENT_IMPORT,
				ImpExpType.BACK_PORT_REPAIR, ImpExpType.EQUIPMENT_BACK_PORT };
		lsResult.addAll(this.fixtureContractDao.findCustomsDeclarationCustomer(
				"CustomsDeclaration", impExpTypes, fixtureContract, state));
		lsResult.addAll(this.fixtureContractDao.findCustomsDeclarationCustomer(
				"BcsCustomsDeclaration", impExpTypes, fixtureContract, state));
		lsResult.addAll(this.fixtureContractDao.findCustomsDeclarationCustomer(
				"DzscCustomsDeclaration", impExpTypes, fixtureContract, state));
		return lsResult;

	}

	/**
	 * 进口料件报关登记表
	 * 
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
	 * @param fixtureContract
	 *            合同备案表头
	 * @param state
	 *            生效类型
	 * @return List 是ImpExpCustomsDeclarationCommInfo型，进出口报关登记表
	 */
	public List findImpExpCommInfoList(Integer seqNum, String code,
			String name, String customer, String impExpType, Date beginDate,
			Date endDate, FixtureContract fixtureContract, int state) {
		List<ImpExpCustomsDeclarationCommInfo> lsResult = new ArrayList<ImpExpCustomsDeclarationCommInfo>();
		String emsNo = fixtureContract.getEmsNo();
		List list = new ArrayList();
		list.addAll(this.fixtureContractDao.findImpExpCommInfoListContract(
				"CustomsDeclarationCommInfo", seqNum, code, name, customer,
				impExpType, beginDate, endDate, emsNo, state));
		list.addAll(this.fixtureContractDao.findImpExpCommInfoListContract(
				"BcsCustomsDeclarationCommInfo", seqNum, code, name, customer,
				impExpType, beginDate, endDate, emsNo, state));
		list.addAll(this.fixtureContractDao.findImpExpCommInfoListContract(
				"DzscCustomsDeclarationCommInfo", seqNum, code, name, customer,
				impExpType, beginDate, endDate, emsNo, state));

		Map<String, Double> mapIn = new HashMap<String, Double>();
		Map<String, Double> mapOut = new HashMap<String, Double>();
		for (int i = 0; i < list.size(); i++) {
			ImpExpCustomsDeclarationCommInfo tempCommInfo = new ImpExpCustomsDeclarationCommInfo();
			Object[] objs = (Object[]) list.get(i);
			BaseCustomsDeclarationCommInfo commInfo = (BaseCustomsDeclarationCommInfo) objs[0];
			FixtureContractItems fixtureContractItems = (FixtureContractItems) objs[1];
			try {
				PropertyUtils.copyProperties(tempCommInfo, commInfo);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			double addInAmount = mapIn.get(tempCommInfo.getCommSerialNo()
					.toString()) == null ? 0.0 : Double.parseDouble(mapIn.get(
					tempCommInfo.getCommSerialNo().toString()).toString());
			double addOutAmount = mapOut.get(tempCommInfo.getCommSerialNo()
					.toString()) == null ? 0.0 : Double.parseDouble(mapIn.get(
					tempCommInfo.getCommSerialNo().toString()).toString());
			Integer type = tempCommInfo.getBaseCustomsDeclaration()
					.getImpExpType();
			if (type == ImpExpType.EQUIPMENT_IMPORT) {
				addInAmount += (tempCommInfo.getCommAmount() == null ? 0.0
						: tempCommInfo.getCommAmount());
			} else if (type == ImpExpType.BACK_PORT_REPAIR
					|| type == ImpExpType.EQUIPMENT_BACK_PORT) {
				addInAmount -= (tempCommInfo.getCommAmount() == null ? 0.0
						: tempCommInfo.getCommAmount());
				addOutAmount += (tempCommInfo.getCommAmount() == null ? 0.0
						: tempCommInfo.getCommAmount());
			}
			tempCommInfo.setCommAddInAmount(addInAmount);
			mapIn.put(tempCommInfo.getCommSerialNo().toString(), addInAmount);
			tempCommInfo.setCommAddOutAmount(addOutAmount);
			mapOut.put(tempCommInfo.getCommSerialNo().toString(), addOutAmount);
			tempCommInfo.setCommNameAndSpec(tempCommInfo.getCommName() + "/"
					+ tempCommInfo.getCommSpec());
			tempCommInfo.setFixKind(fixtureContractItems.getFixKind());
			lsResult.add(tempCommInfo);
		}
		return lsResult;
	}

	/**
	 * 判断位置是否在被使用状态
	 * 
	 * @param reqeust
	 * @param location
	 * @return
	 */
	public Boolean isInUseFixtureLocation(FixtureLocation location) {
		List list = this.fixtureContractDao
				.fixtureLocationResultInfoByLocation(location);
		if (list.size() > 0)
			return true;
		list = fixtureContractDao
				.findFixtureLocationChangeBillInfoByLocation(location);
		if (list.size() > 0)
			return true;
		return false;

	}
	
	/**
	 * 从文件导入数据
	 * @param ls
	 * @param importApplyProperty
	 * @param cbIsOverwrite
	 */
	/**
	 * @param ls
	 * @param cbIsOverwrite
	 */
	public void importDataFromFile(List ls,boolean cbIsOverwrite,FixtureContract fixtureContract){
		// 监管方式Map
		Map tradeModeMap = getObjectMapByTableName("Trade");
		// 申报地海关Map
		Map declareCustomMap = getObjectMapByTableName("Customs");
		// 运输方式Map
		Map transportModeMap = getObjectMapByTableName("Transf");
		// 申报单位Map
		Map declareCompanyMap = getObjectMapByTableName("Company");
		// 进/出口岸Map
		Map entrancePortMap = getObjectMapByTableName("Customs");
		// 贸易国Map
		Map countryMap = getObjectMapByTableName("Country");
		// 征免方式Map
		Map levyKindMap = getObjectMapByTableName("LevyKind");
		// 征免方式Map
		Map levyModeMap = getObjectMapByTableName("LevyMode");
		//币制Map
		Map currMap = getObjectMapByTableName("Curr");
		//成交方式
		Map transacMap = getObjectMapByTableName("Transac");
		//PayMode保税方式 
		Map payModeMap = getObjectMapByTableName("PayMode");
		//单位
		Map unitMap = getObjectMapByTableName("Unit");
		TempImportEquipmentContract t = null;
		Company company = (Company) CommonUtils.getCompany();
		for(int i=0,size=ls.size();i<size;i++){
			t = (TempImportEquipmentContract)ls.get(i);
//			FixtureContract fixtureContract = null;
//			//表头
//			String agreementNo = t.getAgreementNo().trim();
//			FixtureContract fc = fixtureContractDao.findContractByEms(agreementNo);
//			if (fc == null || (fc!=null && cbIsOverwrite)) {
//				if(fc==null){
//				   fixtureContract = new FixtureContract();
//				}else{
//					fixtureContract = fc;
//				}
//			    
//				fixtureContract.setDeclareCustoms((Customs) declareCustomMap
//						.get(t.getDeclareCustoms()));
//				fixtureContract.setBeginDate(strToDate(t.getBeginDate()));
//				fixtureContract.setDeclareState(DeclareState.APPLY_POR);//默认申请备案状态
//				fixtureContract.setAvailabilityDate(strToDate(t
//						.getAvailabilityDate()));
//				fixtureContract.setEmsNo(t.getEmsNo());
//				fixtureContract.setIePort1((Customs) declareCustomMap.get(t
//						.getIePort1()));
//				fixtureContract.setDeclareDate(strToDate(t.getDeferDate()));
//				
//				fixtureContract
//						.setTrade((Trade) tradeModeMap.get(t.getTradeType()));//				
//				if(company!=null){
//				   fixtureContract.setMachCode(company.getCode());
//				   fixtureContract.setMachName(company.getName());
//				   fixtureContract.setOutTradeCo(company.getOutTradeCo());
//				   fixtureContract.setEnterpriseAddress(company.getAddress());
//				   fixtureContract.setLinkMan(company.getOwner());
//				   fixtureContract.setContactTel(company.getTel());
//				   fixtureContract.setTradeName(company.getBuCode());
//					fixtureContract.setTradeCode(company.getBuName());
//				}
//				fixtureContract.setTradeCountry((Country) countryMap.get(t
//						.getTradeCountryName()));//				
//				
//							
//				fixtureContract.setPayMode((PayMode) payModeMap.get(t
//						.getPayModeName()));// 保税方式
//				
//				
//				fixtureContract.setLevyKind((LevyKind) levyKindMap.get(t
//						.getLevyKindName()));				
//				fixtureContract.setSancEmsNo(t.getSancEmsNo());
//				fixtureContract.setAgreementNo(t.getAgreementNo());
//				fixtureContract.setImpContractNo(t.getImpContractNo());
//				fixtureContract.setExpContractNo(t.getExpContractNo());
////				fixtureContract.setFixtureAmount(strToDouble(t
////						.getFixtureAmount()));//设备总金额
//				fixtureContract.setEmphasisFlag(t.getEmphasisFlag());
//				fixtureContract.setCurr((Curr) currMap.get(t.getCurr()));
//				fixtureContract
//						.setWardshipRate(strToDouble(t.getWardshipRate()));
//				fixtureContract.setWardshipFee(strToDouble(t.getWardshipFee()));
//				fixtureContract.setTransac((Transac) transacMap.get(t
//						.getTransac()));
//				fixtureContract.setIePort2((Customs) declareCustomMap.get(t
//						.getIePort2()));
//				fixtureContract.setIePort3((Customs) declareCustomMap.get(t
//						.getIePort3()));
//				fixtureContract.setIePort4((Customs) declareCustomMap.get(t
//						.getIePort4()));
//				fixtureContract.setIePort5((Customs) declareCustomMap.get(t
//						.getIePort5()));
//				fixtureContract.setFirstTrialer(t.getFirstTrialer());
//				fixtureContract.setApproveDate(strToDate(t.getApproveDate()));
//				fixtureContract.setRetrialer(t.getRetrialer());
////				fixtureContract.setMachineCount(strToInteger(t.getMachineCount()));//设备个数
//				fixtureContract.setSaveDate(new Date());
//				fixtureContract.setMemo(t.getMemo());
//				fixtureContract.setCompany(company);
//			}else if(fc!=null && !cbIsOverwrite){
//				fixtureContract = fc;
//			}
//			fixtureContractDao.saveContract(fixtureContract);
			//表体		
			FixtureContractItems fixtureContractItems = null;
			String complexCode = t.getComplexCode().trim();
			if(complexCode!=null && !"".equals(complexCode)){
				Complex complex = fixtureContractDao
				.findComplexByCode(t.getComplexCode().trim());
				if(complex!=null){					
					String code = t.getComplexCode().trim();
					String name = t.getName().trim();
					String spec = t.getSpec().trim();					
//					FixtureContractItems fci = fixtureContractDao.findContractItemsByComplex(fixtureContract,code,name,spec);//TODO	
					FixtureContractItems fci=null;
					if (fci == null || (fci!=null && cbIsOverwrite)) {						
						if(fci==null){							
						    fixtureContractItems = new FixtureContractItems();
						    fixtureContract.setFixtureAmount(CommonUtils.getDoubleExceptNull(fixtureContract.getFixtureAmount())
						    		+CommonUtils.getDoubleExceptNull(strToDouble(t.getDeclarePrice()))*
									CommonUtils.getIntegerExceptNull(strToInteger(t.getAmount())));//设备总金额
						    fixtureContract.setMaterielItemCount(CommonUtils.getIntegerExceptNull(fixtureContract.getMaterielItemCount())+1);//设备个数
						    
						}else{
							fixtureContractItems = fci;
							fixtureContract.setFixtureAmount(CommonUtils.getDoubleExceptNull(fixtureContract.getFixtureAmount())
									+CommonUtils.getDoubleExceptNull(strToDouble(t.getDeclarePrice()))*CommonUtils.getIntegerExceptNull(strToInteger(t.getAmount()))
									-CommonUtils.getDoubleExceptNull(fci.getTotalPrice()));//设备总金额
						}
					
						fixtureContractItems.setSecondProtocol(t.getSecondProtocol());
						fixtureContractItems.setImportDate(strToDate(t.getImportDate()));
						fixtureContractItems.setSeqNum(new Integer(t.getSeqNum()));
						fixtureContractItems.setComplex(complex);						
						fixtureContractItems.setName(t.getName());
						fixtureContractItems.setSpec(t.getSpec());
						fixtureContractItems.setAmount(strToDouble(t.getAmount()));
						fixtureContractItems.setUnit((Unit) unitMap
								.get(t.getUnitName()));
						fixtureContractItems.setDeclarePrice(strToDouble(t
								.getDeclarePrice()));
						fixtureContractItems.setTotalPrice(CommonUtils.getDoubleExceptNull(strToDouble(t.getDeclarePrice()))*
								CommonUtils.getIntegerExceptNull(strToInteger(t.getAmount())));
						fixtureContractItems.setCountry((Country) countryMap.get(t
								.getCountryName()));
						fixtureContractItems.setLevyMode((LevyMode) levyModeMap.get(t
								.getLevyMode()));
						if(t.getEquipmentType()!=null){
						fixtureContractItems.setFixKind(Integer.valueOf(t
								.getEquipmentType()));
						}
						fixtureContractItems.setDetailNote(t.getDetailedSpec());
						fixtureContractItems.setNote(t.getItemMemo());	
						
						fixtureContractItems.setDeclareState(fixtureContract.getDeclareState());
						fixtureContractItems.setCompany(company);
					}else if(fci!=null && !cbIsOverwrite){
						fixtureContractItems = fci;
					}
					fixtureContractDao.saveContract(fixtureContract);
					fixtureContractItems.setContract(fixtureContract);
					fixtureContractDao.saveContractImg(fixtureContractItems);
				}
			}	
		}		
	}
	
	/**
	 * 字符串转的换成double型小数
	 * @param str
	 * @return
	 */
	public Double strToDouble(String str){
		if(str==null || "".equals(str)){
			return null;
		}
		return new Double(str);
	}
	
	/**
	 * 字符串转的换成整数
	 * @param str
	 * @return
	 */
	public Integer strToInteger(String str){
		if(str==null || "".equals(str)){
			return null;
		}
		return new Integer(str);
	}
	
	/**
	 * 字符串转成日期
	 * @param str
	 * @return
	 */
	public Date strToDate(String str) {
		if(str==null || "".equals(str)){
			return null;
		}
		SimpleDateFormat sdf = null;
		if(str.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")){
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}else if(str.matches("^\\d{4}/\\d{1,2}/\\d{1,2}$")){
			sdf = new SimpleDateFormat("yyyy/MM/dd");
		}else{
			return null;
		}
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据tableName，查找map
	 * 
	 * @param tableName
	 *            要找的实体类
	 * @param codeOrName
	 *            0时用code作为键,1时用name作为键
	 * @return
	 */
	private Map getObjectMapByTableName(String tableName) {
		Map map = new HashMap();
		List list = fixtureContractDao.findObjectByTableNames(tableName, null, null, false);
		if (list.isEmpty())
			return map;
		CustomBaseEntity customBaseEntity = null;
		for (int i = 0; i < list.size(); i++) {
			if (tableName.equals("Trade"))
				customBaseEntity = (Trade) list.get(i);
			else if (tableName.equals("Customs")) {
				customBaseEntity = (Customs) list.get(i);
			} else if (tableName.equals("Transf")) {
				customBaseEntity = (Transf) list.get(i);
			} else if (tableName.equals("Company")) {
				Company company = (Company) list.get(i);
				if (map.get(company.getName()) == null) {
					map.put(company.getName(), company);
				}
				return map;
			} else if (tableName.equals("ProjectDept")) {
				ProjectDept projectDept = (ProjectDept) list.get(i);
				if (map.get(projectDept.getName()) == null) {
					map.put(projectDept.getName(), projectDept);
				}
				return map;
			} else if (tableName.equals("Curr")) {
				customBaseEntity = (Curr) list.get(i);
			} else if (tableName.equals("Country")) {
				customBaseEntity = (Country) list.get(i);
			} else if (tableName.equals("LevyMode")) {
				customBaseEntity = (LevyMode) list.get(i);
			} else if (tableName.equals("LevyKind")) {
				customBaseEntity = (LevyKind) list.get(i);
			}else if (tableName.equals("Transac")){
				customBaseEntity = (Transac) list.get(i);
			} else if (tableName.equals("PayMode")){
				customBaseEntity = (PayMode) list.get(i);
			} else if (tableName.equals("Unit")){
				customBaseEntity = (Unit) list.get(i);
			} else{
				customBaseEntity = (Uses) list.get(i);
			} 

			if (map.get(customBaseEntity.getName()) == null) {
				map.put(customBaseEntity.getName(), customBaseEntity);
			}
		}
		return map;
	}
}
