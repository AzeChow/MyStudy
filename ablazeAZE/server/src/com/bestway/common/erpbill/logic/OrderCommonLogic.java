package com.bestway.common.erpbill.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMerge;
import com.bestway.bcs.contract.dao.ContractDao;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contractexe.entity.TempBcsImpExpCommodityInfo;
import com.bestway.bcs.dictpor.entity.BcsDictPorExg;
import com.bestway.bcs.dictpor.entity.BcsDictPorImg;
import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.dao.ParameterCodeDao;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.common.CaleUtil;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.Request;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.erpbill.dao.ErpBillDao;
import com.bestway.common.erpbill.dao.OrderCommonDao;
import com.bestway.common.erpbill.entity.CustomOrder;
import com.bestway.common.erpbill.entity.CustomOrderBom;
import com.bestway.common.erpbill.entity.CustomOrderDetail;
import com.bestway.common.erpbill.entity.CustomOrderExg;
import com.bestway.common.erpbill.entity.CustomOrderImg;
import com.bestway.common.erpbill.entity.Customparames;
import com.bestway.common.erpbill.entity.MakeCustomOrderToContract;
import com.bestway.common.erpbill.entity.OrderDate;
import com.bestway.common.erpbill.entity.TempCustomOrderChangContract;
import com.bestway.common.erpbill.entity.TempDateCheck;
import com.bestway.common.erpbill.entity.TempOrderBom;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.MaterialBomDetail;
import com.bestway.common.materialbase.entity.MaterialBomMaster;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.dzsc.dzscmanage.dao.DzscDao;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsBomBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.innermerge.entity.DzscCustomsBomDetail;
import com.bestway.dzsc.innermerge.entity.DzscCustomsBomExg;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeData;
import com.bestway.dzsc.innermerge.entity.DzscTenInnerMerge;
import com.bestway.dzsc.materialapply.entity.MaterialBomDetailApply;
import com.bestway.dzsc.materialapply.entity.MaterialBomMasterApply;

public class OrderCommonLogic {

	private ErpBillDao erpBillDao;

	private ContractDao contractDao;

	private DzscDao dzscDao;

	private OrderCommonDao orderCommonDao;

	private ParameterCodeDao parameterCodeDao;

	/** 参数Map类 */
	static class ParameterMaps {
		/**
		 * 定单成品折报关成品
		 */
		Map<String, CustomOrderExg> customOrderExgMap = new HashMap<String, CustomOrderExg>();

	}

	private int cpcount = 0;
	private int ljcount = 0;

	public ContractDao getContractDao() {
		return contractDao;
	}

	public void setContractDao(ContractDao contractDao) {
		this.contractDao = contractDao;
	}

	public ErpBillDao getErpBillDao() {
		return erpBillDao;
	}

	public void setErpBillDao(ErpBillDao erpBillDao) {
		this.erpBillDao = erpBillDao;
	}

	public DzscDao getDzscDao() {
		return dzscDao;
	}

	public void setDzscDao(DzscDao dzscDao) {
		this.dzscDao = dzscDao;
	}

	public OrderCommonDao getOrderCommonDao() {
		return orderCommonDao;
	}

	public void setOrderCommonDao(OrderCommonDao orderCommonDao) {
		this.orderCommonDao = orderCommonDao;
	}

	public ParameterCodeDao getParameterCodeDao() {
		return parameterCodeDao;
	}

	public void setParameterCodeDao(ParameterCodeDao parameterCodeDao) {
		this.parameterCodeDao = parameterCodeDao;
	}

	public CustomOrder okorder(CustomOrder customOrder) {

		if (customOrder == null) {
			return customOrder;
		}

		try {
			customOrder.setIfok(true);
			this.erpBillDao.saveCustomOrder(customOrder);
			saveCustomOrderExg(customOrder);
			return customOrder;
		} catch (Exception ex) {
			return customOrder;
		}

	}

	public List okorder(List listOrder) {
		List<CustomOrder> list = new ArrayList<CustomOrder>();
		for (int i = 0; i < listOrder.size(); i++) {
			CustomOrder customOrder = (CustomOrder) listOrder.get(i);

			customOrder.setIfok(true);
			this.erpBillDao.saveCustomOrder(customOrder);

			// saveCustomOrderExg(customOrder);
			list.add(customOrder);

		}

		return list;

	}

	public CustomOrder cancelorder(CustomOrder customOrder) {

		if (customOrder == null) {
			return customOrder;
		}
		try {
			customOrder.setIfok(false);
			this.erpBillDao.saveCustomOrder(customOrder);
			return customOrder;
		} catch (Exception ex) {
			return customOrder;
		}

	}

	public void dropordermaster(CustomOrder customOrder) {

		this.orderCommonDao.delectCustomOrderDetail(customOrder);
		// this.orderCommonDao.delectCustomOrder(customOrder);

	}

	public void dropordermaster(List list) {
		for (int i = 0; i < list.size(); i++) {
			CustomOrder customOrder = (CustomOrder) list.get(i);
			this.delCustomOrderAll(customOrder);

		}
		// for(int i =0;i<list.size();i++){
		// CustomOrder customOrder = (CustomOrder) list.get(i);
		// this.orderCommonDao.delectCustomOrderDetail(customOrder);
		// }

	}

	/**
	 * 删除单据体
	 * 
	 * @param orderdetail
	 */
	public void droporderdetail(CustomOrderDetail orderdetail) {
		if (orderdetail == null) {
			return;
		}

		try {
			CustomOrderBom customOrderBom = this.orderCommonDao
					.findCustomOrderBom(orderdetail);
			if (customOrderBom != null) {
				this.orderCommonDao.delCustomsOrderBom(customOrderBom);
			}
			CustomOrderExg customOrderExg = this.orderCommonDao
					.findCustomOrderExg(orderdetail);
			if (customOrderExg != null) {

			}
			this.erpBillDao.deleteCustomOrdeDetail(orderdetail);
		} catch (Exception ex) {

		}
	}

	/**
	 * 生成新的合同
	 * 
	 * @param
	 */
	public Contract CreateNewEms(String contractno) {
		Contract contract = new Contract();
		contract.setImpContractNo(contractno);
		contract.setExpContractNo(contractno);
		contract.setCompany(null);
		contract.setDeclareState("1");
		this.contractDao.saveContract(contract);
		return contract;
	}

	/**
	 * 判断成品是否符合电子手册通关备案规则
	 * 
	 * @param dzscteninnermerge
	 * @return
	 */
	private boolean Checkdzsccp(DzscTenInnerMerge dzscteninnermerge,
			DzscEmsPorHead emshead) {

		/*
		 * List list = dzscaction.findDzscEmsPorWjHeadByEmsNo( new
		 * Request(CommonVars.getCurrUser()),
		 * ((DzscEmsPorHead)emshead).getEmsNo() ); if (list.size() <=0){
		 * JOptionPane.showMessageDialog(null, "找不到手册号为:"+
		 * ((DzscEmsPorHead)emshead).getEmsNo()+"的合同!", "提示",
		 * JOptionPane.OK_OPTION); return false; }else{ DzscEmsPorWjHead wjhead
		 * = (DzscEmsPorWjHead)list.get(0); boolean isinems =
		 * dzscaction.FourInnerMergeineinDzscEmsPorWj( new
		 * Request(CommonVars.getCurrUser()), wjhead,
		 * dzscteninnermerge.getDzscFourInnerMerge(), false); if (!isinems){
		 * JOptionPane.showMessageDialog(null, "成品四位商品编码:"+
		 * dzscteninnermerge.getDzscFourInnerMerge().getFourSeqNum()+
		 * "不存在于合同"+wjhead.getSeqNum(), "提示", JOptionPane.OK_OPTION);
		 * 
		 * return false; } }
		 */

		return true;
	}

	/**
	 * 新增电子手册信息
	 * 
	 * @param customOrder
	 * @param emshead
	 */
	public boolean Createdzscinfo(CustomOrder customOrder, BaseEmsHead emshead,
			boolean istest) {
		// this.PutMessage("开始处理订单" + customOrder.getBillCode());

		return true;
	}

	/**
	 * 新增合同信息
	 * 
	 * @param customOrder
	 * @param emshead
	 * 
	 */
	public boolean Createcontractinfo(CustomOrder customOrder,
			BaseEmsHead emshead, boolean istest) {
		// this.PutMessage("开始转换订单:"+customOrder.getBillCode());
		return true;
	}

	/**
	 * 订单转合同
	 * 
	 * @param tableModelOrder
	 * @param isdzsc
	 * @param emshead
	 */
	public void OrderChangetoContract(List list, boolean isdzsc,
			BaseEmsHead emshead) {

		/**
		 * 清除订单转换日志
		 */
		// if (LogList.size() > 0) {
		// LogList.clear();
		// }
		Customparames parames = erpBillDao.findparames();
		isdzsc = parames.getSetbgtype() == 1 ? true : false;
		// if (tableModelOrder.getCurrentRow() == null)
		// this.PutMessage("请选择需要转换的订单!");
		// /**
		// * 检测需要转合同的订单
		// */
		// if (!checkorders(tableModelOrder)) {
		// return;
		// }

		/**
		 * 清除原合同的成品，料件，BOM信息
		 */
		if (isdzsc) {
			// this.PutMessage("开始转换成电子手册通关备案清单!");
			cpcount = this.dzscDao
					.findDzscEmsExgBillCount(((DzscEmsPorHead) emshead).getId());
			ljcount = this.dzscDao
					.findDzscEmsImgBillCount(((DzscEmsPorHead) emshead).getId());

			// dzscaction.DropDzscEmsMaterialInfo(new
			// Request(CommonVars.getCurrUser()),(DzscEmsPorHead)emshead);
		} else {
			// this.PutMessage("开始转换成纸制合同物料清单!");
			cpcount = this.contractDao
					.findContractExgCount(((Contract) emshead).getId());
			ljcount = this.contractDao
					.findContractImgCount(((Contract) emshead).getId());
			// contractaction.DeleteAllMateriel(new
			// Request(CommonVars.getCurrUser()),(Contract) emshead);
		}
		/**
		 * 试转合同
		 */
		// this.PutMessage("开始检验待转订单是否合法!");
		boolean istest = true;
		boolean ifok = true;
		for (int i = 0; i < list.size(); i++) {
			CustomOrder customOrder = (CustomOrder) list.get(i);
			if (customOrder != null) {
				if (isdzsc) {
					if (!Createdzscinfo(customOrder, emshead, istest)) {
						ifok = false;// 电子手册成品料件BOM新增
					}
				} else {
					if (!Createcontractinfo(customOrder, emshead, istest)) {
						ifok = false;// 合同成品料件BOM新增
					}
				}

			}
		}
		// this.PutMessage("待转订单合法检验完毕!");
		if (!ifok) {
			// JOptionPane.showMessageDialog(null, "订单转合同或通关备案失败!", "提示",
			// JOptionPane.OK_OPTION);
			return;
		}
		/**
		 * 正式转换合同
		 */
		istest = false;
		for (int i = 0; i < list.size(); i++) {
			CustomOrder customOrder = (CustomOrder) list.get(i);
			if (customOrder != null) {
				if (isdzsc) {
					Createdzscinfo(customOrder, emshead, istest);// 电子手册成品料件BOM新增
				} else {
					Createcontractinfo(customOrder, emshead, istest);// 合同成品料件BOM新增
				}

				// customOrder.setEmsno(emshead.getEmsNo());
				// customOrder.setIsdzsc(isdzsc);
				// customOrder.setIfcustomer(true);
				this.erpBillDao.saveCustomOrder(customOrder);

			}
		}
		// JOptionPane.showMessageDialog(null, "订单转合同或通关备案成功!", "提示",
		// JOptionPane.OK_OPTION);
	}

	public boolean checkorders(List list) {
		for (int i = 0; i < list.size(); i++) {
			CustomOrder customOrder = (CustomOrder) list.get(i);
			if (!customOrder.getIfok()) {
				// this.PutMessage("订单:"+customOrder.getBillCode()+"没有生效，操作失败!");
				return false;
			}

			/*
			 * if (customOrder.isIfcustomer()){
			 * this.PutMessage("订单:"+customOrder.getBillCode()+"已经转为合同，操作失败!");
			 * return false; }
			 */
		}
		return true;
	}

	/**
	 * 查询纸制手册合同
	 * 
	 * @return
	 */
	public List findcontracthead() {
		return this.contractDao.findcontract("1");
	}

	/**
	 * 查询电子手册合同头
	 * 
	 * @return
	 */
	public List finddzscContractHead(Request request) {
		return this.dzscDao.findDzscContract("3");
	}

	/**
	 * 查询合同物料信息
	 * 
	 * @return
	 */
	public List findBcsInnerMergea() {
		return this.contractDao.findBcsInnerMerge("0");
	}

	/**
	 * 查询电子手册物料信息
	 * 
	 * @return
	 */
	public List findDzscInnerMergeData() {
		return this.dzscDao
				.findDzscInnerMergeData(MaterielType.FINISHED_PRODUCT);
	}

	/**
	 * 根据工厂成品获得其折算料件组成 (纸质手册)
	 * 
	 * @param customOrderDetail
	 * @param listBom
	 * @return
	 */
	public List<TempOrderBom> getTempOrderBomMapBCS(
			CustomOrderDetail customOrderDetail, List listBom,
			BcsInnerMerge bcsInnerMerge, int decimalSize) {
		List<TempOrderBom> valueMap = new ArrayList<TempOrderBom>();
		Map<String, TempOrderBom> tempOrderBomMap = new HashMap<String, TempOrderBom>();

		for (int j = 0; j < listBom.size(); j++) {
			TempOrderBom tempOrderBom = new TempOrderBom();
			MaterialBomDetail materialBomDetail = (MaterialBomDetail) listBom
					.get(j);
			String keyBom = materialBomDetail.getMateriel().getPtNo();
			if (tempOrderBomMap.get(keyBom) == null) {
				Double unitConvert = materialBomDetail.getMateriel()
						.getUnitConvert() == null ? 1.0 : materialBomDetail
						.getMateriel().getUnitConvert();
				tempOrderBom.setBcsInnerMerge(bcsInnerMerge);
				tempOrderBom.setUnitConvert(unitConvert);
				tempOrderBom.setPtNo(keyBom);
				tempOrderBom.setCustomOrderDetail(customOrderDetail);
				Double unitConvert1 = (customOrderDetail.getUnitConvert() == null || customOrderDetail
						.getUnitConvert() == 0.0) ? 1.0 : customOrderDetail
						.getUnitConvert();
				/**
				 * 单耗
				 */
				Double unitWaste = materialBomDetail.getUnitWaste() == null ? 1.0
						: materialBomDetail.getUnitWaste();
				tempOrderBom.setUnitWaste(unitWaste);
				/**
				 * 损耗
				 */
				Double waste = materialBomDetail.getWaste() == null ? 0.0
						: materialBomDetail.getWaste();
				tempOrderBom.setWaste(waste);
				/**
				 * 单项用量
				 */
				Double unitUsed = materialBomDetail.getUnitUsed() == null ? 1.0
						: materialBomDetail.getUnitUsed();
				tempOrderBom.setUnitUsed(unitUsed);
				/**
				 * 币别
				 */
				tempOrderBom.setCurr(customOrderDetail.getCurr());
				// 成品数量*单项用量=料件数量
				// 单项用量（总耗）=单耗/（1-损耗）
				Double amount = customOrderDetail.getAmount() == null ? 0.0
						: customOrderDetail.getAmount();
				/**
				 * 已转厂数量
				 */
				Double transNum = CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull((customOrderDetail
								.getTransNum() == null ? 0.0
								: customOrderDetail.getTransNum())
								* unitUsed / unitConvert1), decimalSize);

				tempOrderBom.setTransNum(transNum);

				/**
				 * 已转合同数量
				 */
				Double contractNum = CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull((customOrderDetail
								.getContractNum() == null ? 0.0
								: customOrderDetail.getContractNum())
								* unitUsed / unitConvert1), decimalSize);

				tempOrderBom.setContractNum(contractNum);
				/**
				 * 未转厂数量
				 */
				Double notTransNum = CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull((customOrderDetail
								.getNotTransNum() == null ? 0.0
								: customOrderDetail.getNotTransNum())
								* unitUsed / unitConvert1), decimalSize);

				tempOrderBom.setNotTransNum(notTransNum);
				/**
				 * 未转合同数量
				 */
				Double notContractNum = CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull((customOrderDetail
								.getNotContractNum() == null ? 0.0
								: customOrderDetail.getNotContractNum())
								* unitUsed / unitConvert1), decimalSize);

				tempOrderBom.setNotContractNum(notContractNum);

				tempOrderBom.setAmount(CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(amount * unitUsed), decimalSize));
				Double ptPrice = customOrderDetail.getUnitPrice() == null ? 0.0
						: customOrderDetail.getUnitPrice();
				tempOrderBom.setPtPrice(ptPrice);

				tempOrderBomMap.put(keyBom, tempOrderBom);

			} else {
				tempOrderBom = tempOrderBomMap.get(keyBom);
				Double unitConvert1 = (customOrderDetail.getUnitConvert() == null || customOrderDetail
						.getUnitConvert() == 0.0) ? 1.0 : customOrderDetail
						.getUnitConvert();
				Double unitConvert = materialBomDetail.getMateriel()
						.getUnitConvert() == null ? 1.0 : materialBomDetail
						.getMateriel().getUnitConvert();
				tempOrderBom.setUnitConvert((unitConvert + tempOrderBom
						.getUnitConvert()) / 2);
				Double unitWaste = materialBomDetail.getUnitWaste() == null ? 1.0
						: materialBomDetail.getUnitWaste();
				Double unitUsed = materialBomDetail.getUnitUsed() == null ? 1.0
						: materialBomDetail.getUnitUsed();
				Double waste = 1 - unitWaste / unitUsed;

				unitWaste = (tempOrderBom.getUnitWaste() + unitWaste) / 2;
				tempOrderBom.setUnitWaste(unitWaste);
				waste = (tempOrderBom.getWaste() + waste) / 2;
				tempOrderBom.setWaste(waste);
				tempOrderBom
						.setUnitUsed((unitUsed + tempOrderBom.getUnitUsed()) / 2);

				Double amount = CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(tempOrderBom.getAmount()
								+ customOrderDetail.getAmount() * unitUsed),
						decimalSize);

				tempOrderBom.setAmount(amount);
				/**
				 * 已转厂数量
				 */
				Double transNum = CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull((customOrderDetail
								.getTransNum() == null ? 0.0
								: customOrderDetail.getTransNum())
								* unitUsed / unitConvert1), decimalSize);

				tempOrderBom.setTransNum(transNum);

				/**
				 * 已转合同数量
				 */
				Double contractNum = CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull((customOrderDetail
								.getContractNum() == null ? 0.0
								: customOrderDetail.getContractNum())
								* unitUsed / unitConvert1), decimalSize);

				tempOrderBom.setContractNum(contractNum);
				/**
				 * 未转厂数量
				 */
				Double notTransNum = CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull((customOrderDetail
								.getNotTransNum() == null ? 0.0
								: customOrderDetail.getNotTransNum())
								* unitUsed / unitConvert1), decimalSize);

				tempOrderBom.setNotTransNum(notTransNum);
				/**
				 * 未转合同数量
				 */
				Double notContractNum = CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull((customOrderDetail
								.getNotContractNum() == null ? 0.0
								: customOrderDetail.getNotContractNum())
								* unitUsed / unitConvert1), decimalSize);

				tempOrderBom.setNotContractNum(notContractNum);
				Double ptPrice = customOrderDetail.getUnitPrice() == null ? 0.0
						: customOrderDetail.getUnitPrice();
				tempOrderBom
						.setPtPrice((ptPrice + tempOrderBom.getPtPrice()) / 2);

				/**
				 * 币别
				 */
				tempOrderBom.setCurr(customOrderDetail.getCurr());
				tempOrderBomMap.put(keyBom, tempOrderBom);
			}

		}

		valueMap.addAll(tempOrderBomMap.values());

		return valueMap;

	}

	/**
	 * 根据工厂成品获得其折算料件组成 (电子手册)
	 * 
	 * @param customOrderDetail
	 * @param listBom
	 * @return
	 */
	public List<TempOrderBom> getTempOrderBomMapDZSC(
			CustomOrderDetail customOrderDetail, List listBom,
			DzscInnerMergeData dzscInnerMergeData, int decimalSize) {
		List<TempOrderBom> valueMap = new ArrayList<TempOrderBom>();
		Map<String, TempOrderBom> tempOrderBomMap = new HashMap<String, TempOrderBom>();
		for (int j = 0; j < listBom.size(); j++) {

			TempOrderBom tempOrderBom = new TempOrderBom();
			MaterialBomDetailApply materialBomDetail = (MaterialBomDetailApply) listBom
					.get(j);
			String keyBom = materialBomDetail.getMateriel().getPtNo() + "/"
					+ customOrderDetail.getId();
			if (tempOrderBomMap.get(keyBom) == null) {
				tempOrderBom.setPtNo(keyBom);
				Double unitConvert = materialBomDetail.getMateriel()
						.getUnitConvert() == null ? 1.0 : materialBomDetail
						.getMateriel().getUnitConvert();
				tempOrderBom.setDzscInnerMergeData(dzscInnerMergeData);
				tempOrderBom.setUnitConvert(unitConvert);
				tempOrderBom.setCustomOrderDetail(customOrderDetail);
				/**
				 * 单耗
				 */
				Double unitWaste = materialBomDetail.getUnitWaste() == null ? 1.0
						: materialBomDetail.getUnitWaste();
				tempOrderBom.setUnitWaste(unitWaste);
				/**
				 * 损耗
				 */
				Double waste = materialBomDetail.getWaste() == null ? 0.0
						: materialBomDetail.getWaste();
				tempOrderBom.setWaste(waste);
				/**
				 * 单项用量
				 */
				Double unitUsed = (materialBomDetail.getUnitUsed() == null || materialBomDetail
						.getUnitUsed() == 0.0) ? 1.0 : materialBomDetail
						.getUnitUsed();
				tempOrderBom.setUnitUsed(unitUsed);
				/**
				 * 币别
				 */
				tempOrderBom.setCurr(customOrderDetail.getCurr());
				// 成品数量*单项用量=料件数量
				// 单项用量（总耗）=单耗/（1-损耗）

				Double amount = CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(customOrderDetail.getAmount()
								* unitUsed), decimalSize);

				tempOrderBom.setAmount(amount);
				/**
				 * 已转厂数量
				 */

				Double unitConvert1 = (customOrderDetail.getUnitConvert() == null || customOrderDetail
						.getUnitConvert() == 0.0) ? 1.0 : customOrderDetail
						.getUnitConvert();
				Double transNum = CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull((customOrderDetail
								.getTransNum() == null ? 0.0
								: customOrderDetail.getTransNum())
								* unitUsed / unitConvert1), decimalSize);

				tempOrderBom.setTransNum(transNum);

				/**
				 * 已转合同数量
				 */

				Double contractNum = CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull((customOrderDetail
								.getContractNum() == null ? 0.0
								: customOrderDetail.getContractNum())
								* unitUsed / unitConvert1), decimalSize);

				tempOrderBom.setContractNum(contractNum);
				/**
				 * 未转厂数量
				 */

				Double notTransNum = CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(customOrderDetail
								.getNotTransNum() == null ? 0.0
								: customOrderDetail.getNotTransNum() * unitUsed
										/ unitConvert1), decimalSize);

				tempOrderBom.setNotTransNum(notTransNum);
				/**
				 * 未转合同数量
				 */

				Double notContractNum = CommonUtils
						.getDoubleByDigit(CommonUtils
								.getDoubleExceptNull(customOrderDetail
										.getNotContractNum() == null ? 0.0
										: customOrderDetail.getNotContractNum()
												* unitUsed / unitConvert1),
								decimalSize);

				tempOrderBom.setNotContractNum(notContractNum);

				Double ptPrice = customOrderDetail.getUnitPrice() == null ? 0.0
						: customOrderDetail.getUnitPrice();
				tempOrderBom.setPtPrice(ptPrice);

				tempOrderBomMap.put(keyBom, tempOrderBom);

			} else {
				tempOrderBom = tempOrderBomMap.get(keyBom);
				Double unitConvert = materialBomDetail.getMateriel()
						.getUnitConvert() == null ? 1.0 : materialBomDetail
						.getMateriel().getUnitConvert();
				tempOrderBom.setUnitConvert((unitConvert + tempOrderBom
						.getUnitConvert()) / 2);
				Double unitConvert1 = (customOrderDetail.getUnitConvert() == null || customOrderDetail
						.getUnitConvert() == 0.0) ? 1.0 : customOrderDetail
						.getUnitConvert();
				/**
				 * 单耗
				 */
				Double unitWaste = materialBomDetail.getUnitWaste() == null ? 1.0
						: materialBomDetail.getUnitWaste();
				Double unitUsed = materialBomDetail.getUnitUsed() == null ? 1.0
						: materialBomDetail.getUnitUsed();
				Double waste = CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(1 - unitWaste / unitUsed), 9);

				unitWaste = (tempOrderBom.getUnitWaste() + unitWaste) / 2;
				tempOrderBom.setUnitWaste(unitWaste);
				waste = (tempOrderBom.getWaste() + waste) / 2;
				tempOrderBom.setWaste(waste);
				tempOrderBom
						.setUnitUsed((unitUsed + tempOrderBom.getUnitUsed()) / 2);
				Double amount = CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(tempOrderBom.getAmount()
								+ customOrderDetail.getAmount() * unitUsed),
						decimalSize);

				tempOrderBom.setAmount(amount);
				/**
				 * 已转厂数量
				 */
				Double transNum = CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(customOrderDetail
								.getTransNum() == null ? 0.0
								: customOrderDetail.getTransNum() * unitUsed
										/ unitConvert1), decimalSize);

				tempOrderBom.setTransNum(transNum);

				/**
				 * 已转合同数量
				 */
				Double contractNum = CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull((customOrderDetail
								.getContractNum() == null ? 0.0
								: customOrderDetail.getContractNum())
								* unitUsed / unitConvert1), decimalSize);

				tempOrderBom.setContractNum(contractNum);
				/**
				 * 未转厂数量
				 */
				Double notTransNum = CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull((customOrderDetail
								.getNotTransNum() == null ? 0.0
								: customOrderDetail.getNotTransNum())
								* unitUsed / unitConvert1), decimalSize);

				tempOrderBom.setNotTransNum(notTransNum);
				/**
				 * 未转合同数量
				 */
				Double notContractNum = CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull((customOrderDetail
								.getNotContractNum() == null ? 0.0
								: customOrderDetail.getNotContractNum())
								* unitUsed / unitConvert1), decimalSize);

				tempOrderBom.setNotContractNum(notContractNum);

				Double ptPrice = customOrderDetail.getUnitPrice() == null ? 0.0
						: customOrderDetail.getUnitPrice();
				tempOrderBom.setPtPrice(ptPrice);

				/**
				 * 币别
				 */
				tempOrderBom.setCurr(customOrderDetail.getCurr());
				tempOrderBomMap.put(keyBom, tempOrderBom);
			}

		}

		valueMap.addAll(tempOrderBomMap.values());
		return valueMap;

	}

	/**
	 * 由工厂料件转报关品名 (bcs)
	 * 
	 * @param customOrder
	 * @param valueMap
	 * @return
	 */
	public void getCustomOrderBomBCS(CustomOrderDetail customOrderDetail,
			List valueMap, int decimalSize) {
		List<CustomOrderBom> customOrderBomList = new ArrayList<CustomOrderBom>();

		CustomOrderExg customOrderExg = this.orderCommonDao
				.findCustomOrderExg(customOrderDetail);
		if (customOrderExg != null) {
			for (int k = 0; k < valueMap.size(); k++) {

				CustomOrderBom customOrderBom = new CustomOrderBom();
				TempOrderBom tob = (TempOrderBom) valueMap.get(k);
				String ptNo1 = tob.getPtNo();
				// System.out.println("ptNo1ptNo1---"+ptNo1);
				String materieltype1 = "2";
				BcsInnerMerge bcsInnerMerge = this.orderCommonDao
						.findBcsInnerMerge(ptNo1, materieltype1);
				if (bcsInnerMerge == null) {
					continue;
				}
				Double unitConvert = (tob.getUnitConvert() == null || tob
						.getUnitConvert() == 0) ? 1.0 : tob.getUnitConvert();
				String tenName = bcsInnerMerge.getBcsTenInnerMerge() == null ? ""
						: bcsInnerMerge.getBcsTenInnerMerge().getName();
				String tenSpec = bcsInnerMerge.getBcsTenInnerMerge() == null ? ""
						: bcsInnerMerge.getBcsTenInnerMerge().getSpec();
				customOrderBom.setPtNo(ptNo1);
				customOrderBom.setCustomOrderExg(customOrderExg);

				/**
				 * 商品编码
				 */
				customOrderBom.setComplex(bcsInnerMerge.getBcsTenInnerMerge()
						.getComplex());
				/**
				 * 商品名称
				 */
				customOrderBom.setName(tenName);
				/**
				 * 规格型号
				 */
				customOrderBom.setSpec(tenSpec);
				/**
				 * 计量单位
				 */
				customOrderBom.setUnit(bcsInnerMerge.getBcsTenInnerMerge()
						.getComUnit());

				/**
				 * 单耗
				 */

				customOrderBom.setUnitWaste(CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(tob.getUnitWaste()
								* unitConvert), 9));
				/**
				 * 损耗率
				 */

				customOrderBom.setWaste(CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(tob.getWaste()
								* unitConvert), 9));

				/**
				 * 单项用量
				 */

				customOrderBom.setUnitDosage(CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(tob.getUnitUsed()
								* unitConvert), 9));
				/**
				 * 币别
				 */
				customOrderBom.setCurr(tob.getCurr());
				/**
				 * 单价
				 */
				Double declarePrice = tob.getPtPrice() * unitConvert;
				customOrderBom.setUnitPrice(declarePrice);

				/**
				 * 数量
				 */
				Double amount = CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(tob.getAmount() * unitConvert),
						decimalSize);

				customOrderBom.setAmount(amount);

				/**
				 * 已转厂数量
				 */
				Double transNum = CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(tob.getTransNum() * unitConvert),
						decimalSize);

				customOrderBom.setTransNum(transNum);

				/**
				 * 已转合同数量
				 */
				Double contractNum = CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(tob.getContractNum()
								* unitConvert), decimalSize);

				customOrderBom.setContractNum(contractNum);
				/**
				 * 未转厂数量
				 */
				Double notTransNum = CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(tob.getNotTransNum()
								* unitConvert), decimalSize);

				customOrderBom.setNotTransNum(notTransNum);
				/**
				 * 未转合同数量
				 */
				Double notContractNum = CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(tob.getNotContractNum()
								* unitConvert), decimalSize);

				customOrderBom.setNotContractNum(notContractNum);
				/**
				 * 总金额
				 */
				Double totalPrice = CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(declarePrice * amount),
						decimalSize);

				customOrderBom.setTotalPrice(totalPrice);
				customOrderBomList.add(customOrderBom);

			}
		}
		saveCustomOrderBom(customOrderBomList);

	}

	/**
	 * 由工厂料件转报关品名(DZSC)
	 * 
	 * @param customOrder
	 * @param valueMap
	 * @return
	 */
	public void getCustomOrderBomDZSC(CustomOrderDetail customOrderDetail,
			List valueMap, int decimalSize) {
		List<CustomOrderBom> customOrderBomList = new ArrayList<CustomOrderBom>();
		// 定单报关成品
		CustomOrderExg customOrderExg = this.orderCommonDao
				.findCustomOrderExg(customOrderDetail);
		if (customOrderExg != null) {
			for (int k = 0; k < valueMap.size(); k++) {
				CustomOrderBom customOrderBom = new CustomOrderBom();
				TempOrderBom tob = (TempOrderBom) valueMap.get(k);
				String ptNo = tob.getPtNo();
				String materieltype1 = "2";
				DzscInnerMergeData dzscInnerMergeData = this.orderCommonDao
						.findDzscInnerMergeData(ptNo, materieltype1);

				if (dzscInnerMergeData == null) {
					continue;
				}
				Double unitConvert = (tob.getUnitConvert() == null || tob
						.getUnitConvert() == 0) ? 1.0 : tob.getUnitConvert();
				String tenName = dzscInnerMergeData.getDzscTenInnerMerge() == null ? ""
						: dzscInnerMergeData.getDzscTenInnerMerge()
								.getTenPtName();
				String tenSpec = dzscInnerMergeData.getDzscTenInnerMerge() == null ? ""
						: dzscInnerMergeData.getDzscTenInnerMerge()
								.getTenPtSpec();
				customOrderBom.setCustomOrderExg(customOrderExg);
				customOrderBom.setPtNo(ptNo);
				/**
				 * 商品编码
				 */
				customOrderBom.setComplex(dzscInnerMergeData
						.getDzscTenInnerMerge().getTenComplex());
				/**
				 * 商品名称
				 */
				customOrderBom.setName(tenName);
				/**
				 * 规格型号
				 */
				customOrderBom.setSpec(tenSpec);
				/**
				 * 计量单位
				 */
				customOrderBom.setUnit(dzscInnerMergeData
						.getDzscTenInnerMerge().getTenUnit());

				/**
				 * 单耗
				 */

				customOrderBom.setUnitWaste(CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(tob.getUnitUsed()
								* unitConvert), 9));
				/**
				 * 损耗率
				 */

				customOrderBom.setWaste(CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(tob.getWaste()
								* unitConvert), 9));

				/**
				 * 单项用量
				 */

				customOrderBom.setUnitDosage(CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(tob.getUnitWaste()
								* unitConvert), 9));
				/**
				 * 企业申报单价
				 */
				Double declarePrice = tob.getPtPrice() * tob.getUnitWaste();
				customOrderBom.setUnitPrice(declarePrice);

				/**
				 * 数量
				 */
				Double amount = CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(tob.getAmount() * unitConvert),
						decimalSize);

				customOrderBom.setAmount(amount);

				/**
				 * 已转厂数量
				 */
				Double transNum = CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(tob.getTransNum() * unitConvert),
						decimalSize);

				customOrderBom.setTransNum(transNum);

				/**
				 * 已转合同数量
				 */
				Double contractNum = CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(tob.getContractNum()
								* unitConvert), decimalSize);

				customOrderBom.setContractNum(contractNum);
				/**
				 * 未转厂数量
				 */
				Double notTransNum = CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(tob.getNotTransNum()
								* unitConvert), decimalSize);

				customOrderBom.setNotTransNum(notTransNum);
				/**
				 * 未转合同数量
				 */
				Double notContractNum = CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(tob.getNotContractNum()
								* unitConvert), decimalSize);

				customOrderBom.setNotContractNum(notContractNum);

				/**
				 * 币别
				 */
				customOrderBom.setCurr(tob.getCurr());
				/**
				 * 总金额
				 */
				Double totalPrice = CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(declarePrice * amount),
						decimalSize);

				customOrderBom.setTotalPrice(totalPrice);
				customOrderBomList.add(customOrderBom);

			}
		}

		saveCustomOrderBom(customOrderBomList);

	}

	/**
	 * 纸质手册工厂成品转报关成品 (bcs)
	 * 
	 * @param customOrder
	 * @param bcsInnerMerge
	 * @param customOrderDetail
	 */
	public void ChageFactoryToCustomExg(CustomOrder customOrder,
			BcsInnerMerge bcsInnerMerge, CustomOrderDetail customOrderDetail,
			ParameterMaps parameterMaps, int decimalSize) {
		CustomOrderExg customOrderExg = new CustomOrderExg();
		CustomOrderExg coe = this.orderCommonDao
				.findCustomOrderExg(customOrderDetail);

		if (!checkDate(customOrderDetail, coe)) {
			Map<String, CustomOrderExg> customOrderExgMap = parameterMaps.customOrderExgMap;

			String tenName = bcsInnerMerge.getBcsTenInnerMerge().getName();
			String tenSpec = bcsInnerMerge.getBcsTenInnerMerge().getSpec();

			String key = customOrderDetail.getId();
			if (coe == null) {
				customOrderExg.setCustomOrderDetail(customOrderDetail);
				/**
				 * 商品编码
				 */
				customOrderExg.setComplex(bcsInnerMerge.getBcsTenInnerMerge()
						.getComplex());
				/**
				 * 商品名称
				 */
				customOrderExg.setName(tenName);
				/**
				 * 规格型号
				 */
				customOrderExg.setSpec(tenSpec);
				/**
				 * 计量单位
				 */
				customOrderExg.setUnit(bcsInnerMerge.getBcsTenInnerMerge()
						.getComUnit());
				/**
				 * 单价
				 */
				Double unitPrice = customOrderDetail.getUnitPrice() == null ? 0.0
						: customOrderDetail.getUnitPrice();
				customOrderExg.setUnitPrice(unitPrice);
				/**
				 * 出口数量
				 */
				Double orderAmount = customOrderDetail.getBgamount() == null ? 0.0
						: customOrderDetail.getBgamount();
				customOrderExg.setAmount(orderAmount);
				/**
				 * 物料与报关商品的折算系数
				 */
				Double unitConvert = customOrderDetail.getUnitConvert() == null
						|| customOrderDetail.getUnitConvert() == 0.0 ? 1.0
						: customOrderDetail.getUnitConvert();

				/**
				 * 已转厂数量
				 */
				// Double transNum = customOrderDetail.getTransNum() == null ?
				// 0.0
				// : customOrderDetail.getTransNum() * unitConvert;
				Double transNum = customOrderDetail.getTransNum() == null ? 0.0
						: customOrderDetail.getTransNum();
				customOrderExg.setTransNum(transNum);
				/**
				 * 已转合同数量
				 */
				// Double contractNum = customOrderDetail.getContractNum() ==
				// null ? 0.0
				// : customOrderDetail.getContractNum() * unitConvert;
				Double contractNum = customOrderDetail.getContractNum() == null ? 0.0
						: customOrderDetail.getContractNum();
				customOrderExg.setContractNum(contractNum);
				/**
				 * 未转厂数量
				 */
				Double notContractNum = customOrderDetail.getNotContractNum() == null ? 0.0
						: customOrderDetail.getNotContractNum();
				customOrderExg.setNotContractNum(notContractNum);
				/**
				 * 未转合同数量
				 */
				// Double notTransNum = customOrderDetail.getNotTransNum() ==
				// null ? 0.0
				// : customOrderDetail.getNotTransNum() * unitConvert;
				Double notTransNum = customOrderDetail.getNotTransNum() == null ? 0.0
						: customOrderDetail.getNotTransNum();
				customOrderExg.setNotTransNum(notTransNum);
				/**
				 * 总金额
				 */
				Double totalPrice = CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(customOrderExg.getUnitPrice()
								* orderAmount), decimalSize);

				customOrderExg.setTotalPrice(totalPrice);
				/**
				 * 加工费单价
				 */
				Double processUnitPrice = bcsInnerMerge.getBcsTenInnerMerge()
						.getMachPrice() == null ? 0.0 : bcsInnerMerge
						.getBcsTenInnerMerge().getMachPrice();
				customOrderExg.setProcessUnitPrice(processUnitPrice);
				/**
				 * 加工费总价
				 */
				Double processTotalPrice = CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(customOrderExg
								.getProcessUnitPrice()
								* orderAmount), decimalSize);

				customOrderExg.setProcessTotalPrice(processTotalPrice);
				/**
				 * 单位净重
				 */
				Double unitNetWeight = bcsInnerMerge.getMateriel()
						.getPtNetWeight() == null ? 0.0 : bcsInnerMerge
						.getMateriel().getPtNetWeight();
				customOrderExg.setUnitNetWeight(unitNetWeight);
				/**
				 * 币别
				 */
				customOrderExg.setCurr(customOrderDetail.getCurr());
				/**
				 * 单位毛重
				 */
				customOrderExg.setUnitGrossWeight(bcsInnerMerge.getMateriel()
						.getPtOutWeight());
				this.orderCommonDao.saveCustomOrderExg(customOrderExg);
				// customOrderExgMap.put(key,customOrderExg);

			} else {
				customOrderExg = coe;
				/**
				 * 出口数量
				 */
				Double orderAmount = customOrderDetail.getBgamount() == null ? 0.0
						: customOrderDetail.getBgamount();
				/**
				 * 物料与报关商品的折算系数
				 */
				Double unitConvert = customOrderDetail.getUnitConvert() == null
						|| customOrderDetail.getUnitConvert() == 0.0 ? 1.0
						: customOrderDetail.getUnitConvert();

				/**
				 * 已转厂数量
				 */
				Double transNum = customOrderDetail.getTransNum() == null ? 0.0
						: customOrderDetail.getTransNum();

				/**
				 * 已转合同数量
				 */
				Double contractNum = customOrderDetail.getContractNum() == null ? 0.0
						: customOrderDetail.getContractNum();

				/**
				 * 未转厂数量
				 */
				Double notContractNum = customOrderDetail.getNotContractNum() == null ? 0.0
						: customOrderDetail.getNotContractNum();

				/**
				 * 未转合同数量
				 */
				Double notTransNum = customOrderDetail.getNotTransNum() == null ? 0.0
						: customOrderDetail.getNotTransNum();

				/**
				 * 总金额
				 */
				Double totalPrice = CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(customOrderExg.getUnitPrice()
								* orderAmount), decimalSize);

				/**
				 * 加工费总价
				 */
				Double processTotalPrice = CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(customOrderExg
								.getProcessUnitPrice()
								* orderAmount), decimalSize);

				// customOrderExgMap.remove(customOrderExg);
				customOrderExg.setProcessTotalPrice(processTotalPrice);
				customOrderExg.setAmount(orderAmount);
				customOrderExg.setTransNum(transNum);
				customOrderExg.setContractNum(contractNum);
				customOrderExg.setNotContractNum(notContractNum);
				customOrderExg.setNotTransNum(notTransNum);
				customOrderExg.setTotalPrice(totalPrice);

				/**
				 * 单价
				 */
				Double price = 0.0;
				if (customOrderExg.getTotalPrice() != null
						&& customOrderExg.getAmount() != null
						&& customOrderExg.getAmount() != 0.0) {
					price = CommonUtils.getDoubleByDigit(customOrderExg
							.getTotalPrice()
							/ customOrderExg.getAmount(), decimalSize);

				}
				// Double unitPrice = (customOrderDetail.getUnitPrice() == null
				// ? 0.0
				// : customOrderDetail.getUnitPrice()
				// + customOrderExg.getUnitPrice()) / 2;
				customOrderExg.setUnitPrice(price);
				// customOrderExg.setCustomOrder(customOrder);
				/**
				 * 商品编码
				 */
				customOrderExg.setComplex(bcsInnerMerge.getBcsTenInnerMerge()
						.getComplex());
				/**
				 * 商品名称
				 */
				customOrderExg.setName(tenName);
				/**
				 * 规格型号
				 */
				customOrderExg.setSpec(tenSpec);
				/**
				 * 计量单位
				 */
				customOrderExg.setUnit(bcsInnerMerge.getBcsTenInnerMerge()
						.getComUnit());

				/**
				 * 加工费单价
				 */
				Double processUnitPrice = bcsInnerMerge.getBcsTenInnerMerge()
						.getMachPrice() == null ? 0.0 : bcsInnerMerge
						.getBcsTenInnerMerge().getMachPrice();
				customOrderExg.setProcessUnitPrice(processUnitPrice);
				/**
				 * 单位净重
				 */
				Double unitNetWeight = bcsInnerMerge.getMateriel()
						.getPtNetWeight() == null ? 0.0 : bcsInnerMerge
						.getMateriel().getPtNetWeight();
				customOrderExg.setUnitNetWeight(unitNetWeight);
				/**
				 * 币别
				 */
				customOrderExg.setCurr(customOrderDetail.getCurr());
				/**
				 * 单位毛重
				 */
				Double unitGrossWeight = bcsInnerMerge.getMateriel()
						.getPtOutWeight() == null ? 0.0 : bcsInnerMerge
						.getMateriel().getPtOutWeight();
				customOrderExg.setUnitGrossWeight(unitGrossWeight);
				this.orderCommonDao.saveCustomOrderExg(customOrderExg);
				// customOrderExgMap.put(key,customOrderExg);
			}

		}

	}

	public Boolean checkDate(CustomOrderDetail customOrderDetail,
			CustomOrderExg coe) {
		Boolean flag = true;
		if (coe != null) {
			Double notContractNumByCOE = coe.getNotContractNum() == null ? 0.0
					: coe.getNotContractNum();
			Double notContractNumByCOD = customOrderDetail.getNotContractNum() == null ? 0.0
					: customOrderDetail.getNotContractNum();
			Double contractNumByCOE = coe.getContractNum() == null ? 0.0 : coe
					.getContractNum();
			Double contractNumByCOD = customOrderDetail.getContractNum() == null ? 0.0
					: customOrderDetail.getContractNum();
			Double notTransNumByCOE = coe.getNotTransNum() == null ? 0.0 : coe
					.getNotTransNum();
			Double notTransNumByCOD = customOrderDetail.getNotTransNum() == null ? 0.0
					: customOrderDetail.getNotTransNum();
			Double transNumByCOD = coe.getTransNum() == null ? 0.0 : coe
					.getTransNum();
			Double transNumByCOE = customOrderDetail.getTransNum() == null ? 0.0
					: customOrderDetail.getTransNum();
			if (notContractNumByCOE.doubleValue() != notContractNumByCOD
					.doubleValue()) {
				flag = false;
			} else if (contractNumByCOE.doubleValue() != contractNumByCOD
					.doubleValue()) {
				flag = false;
			} else if (notTransNumByCOE.doubleValue() != notTransNumByCOD
					.doubleValue()) {
				flag = false;
			} else if (transNumByCOD.doubleValue() != transNumByCOE
					.doubleValue()) {
				flag = false;
			}
		} else {
			flag = false;
		}
		return flag;

	}

	/**
	 * 电子手册工厂成品转报关成品 (dzsc)
	 * 
	 * @param customOrder
	 * @param bcsInnerMerge
	 * @param customOrderDetail
	 */
	public void ChageFactoryToCustomExg(CustomOrder customOrder,
			DzscInnerMergeData dzscInnerMergeData,
			CustomOrderDetail customOrderDetail, ParameterMaps parameterMaps,
			int decimalSize) {
		CustomOrderExg customOrderExg = new CustomOrderExg();
		CustomOrderExg coe = this.orderCommonDao
				.findCustomOrderExg(customOrderDetail);

		if (!checkDate(customOrderDetail, coe)) {
			Map<String, CustomOrderExg> customOrderExgMap = parameterMaps.customOrderExgMap;
			String tenName = dzscInnerMergeData.getDzscTenInnerMerge() == null ? ""
					: dzscInnerMergeData.getDzscTenInnerMerge().getTenPtName();
			String tenSpec = dzscInnerMergeData.getDzscTenInnerMerge() == null ? ""
					: dzscInnerMergeData.getDzscTenInnerMerge().getTenPtSpec();

			String key = customOrderDetail.getId();
			if (coe == null) {

				if (customOrderExgMap.get(key) == null) {

					customOrderExg.setCustomOrderDetail(customOrderDetail);
					/**
					 * 商品编码
					 */
					customOrderExg.setComplex(dzscInnerMergeData
							.getDzscTenInnerMerge().getTenComplex());
					/**
					 * 商品名称
					 */
					customOrderExg.setName(tenName);
					/**
					 * 规格型号
					 */
					customOrderExg.setSpec(tenSpec);
					/**
					 * 计量单位
					 */
					customOrderExg.setUnit(dzscInnerMergeData
							.getDzscTenInnerMerge().getTenUnit());
					/**
					 * 单价
					 */
					Double unitPrice = customOrderDetail.getUnitPrice() == null ? 0.0
							: customOrderDetail.getUnitPrice();

					customOrderExg.setUnitPrice(unitPrice);
					/**
					 * 出口数量
					 */
					Double orderAmount = customOrderDetail.getBgamount() == null ? 0.0
							: customOrderDetail.getBgamount();
					customOrderExg.setAmount(orderAmount);
					/**
					 * 物料与报关商品的折算系数
					 */
					Double unitConvert = customOrderDetail.getUnitConvert() == null
							|| customOrderDetail.getUnitConvert() == 0.0 ? 1.0
							: customOrderDetail.getUnitConvert();

					/**
					 * 已转厂数量
					 */
					Double transNum = customOrderDetail.getTransNum() == null ? 0.0
							: customOrderDetail.getTransNum() * unitConvert;
					customOrderExg.setTransNum(transNum);
					/**
					 * 已转合同数量
					 */
					Double contractNum = customOrderDetail.getContractNum() == null ? 0.0
							: customOrderDetail.getContractNum() * unitConvert;
					customOrderExg.setContractNum(contractNum);
					/**
					 * 未转厂数量
					 */
					Double notContractNum = customOrderDetail
							.getNotContractNum() == null ? 0.0
							: customOrderDetail.getNotContractNum()
									* unitConvert;
					customOrderExg.setNotContractNum(notContractNum);
					/**
					 * 未转合同数量
					 */
					Double notTransNum = customOrderDetail.getNotTransNum() == null ? 0.0
							: customOrderDetail.getNotTransNum() * unitConvert;
					customOrderExg.setNotTransNum(notTransNum);
					/**
					 * 总金额
					 */
					Double totalPrice = CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(customOrderExg
									.getUnitPrice()
									* orderAmount), decimalSize);

					customOrderExg.setTotalPrice(totalPrice);
					/**
					 * 加工费单价
					 */
					Double processUnitPrice = dzscInnerMergeData
							.getDzscTenInnerMerge().getMachinPrice() == null ? 0.0
							: dzscInnerMergeData.getDzscTenInnerMerge()
									.getMachinPrice();
					customOrderExg.setProcessUnitPrice(processUnitPrice);
					/**
					 * 加工费总价
					 */
					Double processTotalPrice = CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(customOrderExg
									.getProcessUnitPrice()
									* orderAmount), decimalSize);
					customOrderExg.setProcessTotalPrice(processTotalPrice);
					/**
					 * 单位净重
					 */
					Double unitNetWeight = dzscInnerMergeData.getMateriel()
							.getPtNetWeight() == null ? 0.0
							: dzscInnerMergeData.getMateriel().getPtNetWeight();
					customOrderExg.setUnitNetWeight(unitNetWeight);
					/**
					 * 币别
					 */
					customOrderExg.setCurr(customOrderDetail.getCurr());
					/**
					 * 单位毛重
					 */

					customOrderExg.setUnitGrossWeight(dzscInnerMergeData
							.getMateriel().getPtOutWeight());
					this.orderCommonDao.saveCustomOrderExg(customOrderExg);
					// customOrderExgMap.put(key,customOrderExg);
				}

			} else {
				customOrderExg = coe;
				// this.orderCommonDao.delCustomsOrderExg(customOrderExg);
				/**
				 * 单价
				 */
				Double unitPrice = (customOrderDetail.getUnitPrice() == null ? 0.0
						: customOrderDetail.getUnitPrice()
								+ customOrderExg.getUnitPrice()) / 2;
				/**
				 * 加工费单价
				 */
				Double processUnitPrice = dzscInnerMergeData
						.getDzscTenInnerMerge().getMachinPrice() == null ? 0.0
						: dzscInnerMergeData.getDzscTenInnerMerge()
								.getMachinPrice();
				/**
				 * 出口数量
				 */
				Double orderAmount = customOrderDetail.getBgamount() == null ? 0.0
						: customOrderDetail.getBgamount();

				/**
				 * 物料与报关商品的折算系数
				 */
				Double unitConvert = customOrderDetail.getUnitConvert() == null
						|| customOrderDetail.getUnitConvert() == 0.0 ? 1.0
						: customOrderDetail.getUnitConvert();

				/**
				 * 已转厂数量
				 */
				Double transNum = customOrderDetail.getTransNum() == null ? 0.0
						: customOrderDetail.getTransNum() * unitConvert;

				/**
				 * 已转合同数量
				 */
				Double contractNum = customOrderDetail.getContractNum() == null ? 0.0
						: customOrderDetail.getContractNum() * unitConvert;

				/**
				 * 未转厂数量
				 */
				Double notContractNum = customOrderDetail.getNotContractNum() == null ? 0.0
						: customOrderDetail.getNotContractNum() * unitConvert;
				/**
				 * 未转合同数量
				 */
				Double notTransNum = customOrderDetail.getNotTransNum() == null ? 0.0
						: customOrderDetail.getNotTransNum() * unitConvert;

				/**
				 * 总金额
				 */
				Double totalPrice = CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(customOrderExg.getUnitPrice()
								* orderAmount), decimalSize);

				/**
				 * 加工费总价
				 */
				Double processTotalPrice = CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(customOrderExg
								.getProcessUnitPrice()
								* orderAmount), decimalSize);
				// customOrderExgMap.remove(customOrderExg);
				customOrderExg.setProcessTotalPrice(processTotalPrice);
				customOrderExg.setAmount(orderAmount);
				customOrderExg.setTransNum(transNum);
				customOrderExg.setContractNum(contractNum);
				customOrderExg.setNotContractNum(notContractNum);
				customOrderExg.setNotTransNum(notTransNum);
				customOrderExg.setTotalPrice(totalPrice);
				/**
				 * 商品编码
				 */
				customOrderExg.setComplex(dzscInnerMergeData
						.getDzscTenInnerMerge().getTenComplex());
				/**
				 * 商品名称
				 */
				customOrderExg.setName(tenName);
				/**
				 * 规格型号
				 */
				customOrderExg.setSpec(tenSpec);
				/**
				 * 计量单位
				 */
				customOrderExg.setUnit(dzscInnerMergeData
						.getDzscTenInnerMerge().getTenUnit());

				customOrderExg.setUnitPrice(unitPrice);

				customOrderExg.setProcessUnitPrice(processUnitPrice);
				/**
				 * 单位净重
				 */
				Double unitNetWeight = dzscInnerMergeData.getMateriel()
						.getPtNetWeight() == null ? 0.0 : dzscInnerMergeData
						.getMateriel().getPtNetWeight();
				customOrderExg.setUnitNetWeight(unitNetWeight);
				/**
				 * 币别
				 */
				customOrderExg.setCurr(customOrderDetail.getCurr());
				/**
				 * 单位毛重
				 */
				Double unitGrossWeight = dzscInnerMergeData.getMateriel()
						.getPtOutWeight() == null ? 0.0 : dzscInnerMergeData
						.getMateriel().getPtOutWeight();
				customOrderExg.setUnitGrossWeight(unitGrossWeight);
				this.orderCommonDao.saveCustomOrderExg(customOrderExg);

			}
		}
	}

	/**
	 * 保存定单成品
	 */
	public void saveCustomOrderExg(CustomOrder customOrder) {
		ParameterMaps parameterMaps = new ParameterMaps();
		String orderNo = customOrder.getBillCode();

		// wss:2010-05-20加上用户设定的小数保留位数
		Customparames customparam = this.orderCommonDao.findCustomparames();
		int decimalSize = customparam == null ? 5 : customparam
				.getDecimalSize();
		/**
		 * 获取定单明细
		 */
		List list = this.orderCommonDao.findCustomOrderDetail(orderNo);

		List<CustomOrderExg> listCustomOrderExg = new ArrayList<CustomOrderExg>();
		Integer type = customOrder.getCustomType();// 手册类型
		Integer rateSource = customOrder.getRateSource();

		/**
		 * type==1 为电子手册 type==2 or 3 为纸质手册或电子化手册
		 */
		if (type == 1) {
			if (rateSource == 3) { // １：报关常用工厂ＢＯＭ ２：ＢＯＭ备案 ３：报关单耗
				for (int i = 0; i < list.size(); i++) {
					CustomOrderDetail customOrderDetail = (CustomOrderDetail) list
							.get(i);
					String ptNo = customOrderDetail.getMateriel().getPtNo();
					String materieltype = "0"; // 0为成品，２为料件

					/**
					 * 工厂成品转报关品名
					 */
					DzscInnerMergeData dzscInnerMergeData = this.orderCommonDao
							.findDzscInnerMergeData(ptNo, materieltype);

					ChageFactoryToCustomExg(customOrder, dzscInnerMergeData,
							customOrderDetail, parameterMaps, decimalSize);

				}
			} else if (rateSource == 1 || rateSource == 2) {
				for (int i = 0; i < list.size(); i++) {
					CustomOrderDetail customOrderDetail = (CustomOrderDetail) list
							.get(i);
					String ptNo = customOrderDetail.getMateriel().getPtNo();
					String materieltype = "0"; // 0为成品，２为料件
					/**
					 * 工厂成品转报关品名
					 */
					DzscInnerMergeData dzscInnerMergeData = this.orderCommonDao
							.findDzscInnerMergeData(ptNo, materieltype);

					ChageFactoryToCustomExg(customOrder, dzscInnerMergeData,
							customOrderDetail, parameterMaps, decimalSize);

				}
			}

		} else if (type == 2 || type == 3) {//为纸质手册或电子化手册

			for (int i = 0; i < list.size(); i++) {

				CustomOrderDetail customOrderDetail = (CustomOrderDetail) list
						.get(i);
				String ptNo = customOrderDetail.getMateriel().getPtNo();
				String materieltype = "0";

				/**
				 * 工厂成品转报关品名
				 */
				BcsInnerMerge bcsInnerMerge = this.orderCommonDao
						.findBcsInnerMerge(ptNo, materieltype);

				ChageFactoryToCustomExg(customOrder, bcsInnerMerge,
						customOrderDetail, parameterMaps, decimalSize);

			}

		}

		saveCustomOrderBom(customOrder, decimalSize);

	}

	/**
	 * 保存订单ＢＯＭ
	 * 
	 * @param customOrder
	 */
	public void saveCustomOrderBom(CustomOrder customOrder, int decimalSize) {
		ParameterMaps parameterMaps = new ParameterMaps();
		String orderNo = customOrder.getBillCode();

		/**
		 * 获取定单明细
		 */
		List list = this.orderCommonDao.findCustomOrderDetail(orderNo);
		// 删除该定单号的BOM
		this.delCustomsOrderBom(customOrder);
		Integer type = customOrder.getCustomType();
		Integer rateSource = customOrder.getRateSource();
		/**
		 * type==1 为电子手册 type==2 or 3 为纸质手册或电子化手册
		 */

		if (type == 1) {
			if (rateSource == 3) { // １：报关常用工厂ＢＯＭ ２：ＢＯＭ备案 ３：报关单耗
				// 获取转报关成品的资料
				List listCustomOrderExg = this.orderCommonDao
						.findCustomOrderExg(customOrder);

				for (int i = 0; i < listCustomOrderExg.size(); i++) {
					CustomOrderExg customOrderExg = (CustomOrderExg) listCustomOrderExg
							.get(i);
					Complex complex = customOrderExg.getComplex();
					String tenName = customOrderExg.getName();
					String tenSpec = customOrderExg.getSpec();
					String comPlexName = complex == null ? null : complex
							.getName();

					Unit unit = customOrderExg.getUnit();
					String unitName = unit == null ? null : unit.getName();

					List listBom = this.orderCommonDao
							.findDzscCustomsBomDetail(comPlexName, tenName,
									tenSpec, unitName);

					for (int j = 0; j < listBom.size(); j++) {
						DzscCustomsBomDetail dzscCustomsBomDetail = (DzscCustomsBomDetail) listBom
								.get(j);
						CustomOrderBom customOrderBom = new CustomOrderBom();
						// customOrderBom.setCustomOrderDetail(customOrderExg.getCustomOrderDetail());
						/**
						 * 商品编码
						 */
						Complex complex1 = dzscCustomsBomDetail.getComplex();
						customOrderBom.setComplex(complex1);

						/**
						 * 料件名称
						 */
						String name = dzscCustomsBomDetail.getName();
						customOrderBom.setName(name);
						/**
						 * 型号规格
						 */
						String spec = dzscCustomsBomDetail.getSpec();
						customOrderBom.setSpec(spec);
						/**
						 * 单耗
						 */
						Double unitWare = dzscCustomsBomDetail.getUnitWare();
						customOrderBom.setUnitWaste(unitWare);
						/**
						 * 损耗
						 */
						Double ware = dzscCustomsBomDetail.getWare();
						customOrderBom.setWaste(ware);
						/**
						 * 单项用量
						 */
						Double unitDosage = (dzscCustomsBomDetail
								.getUnitDosage() == 0 || dzscCustomsBomDetail
								.getUnitDosage() == null) ? 1.0
								: dzscCustomsBomDetail.getUnitDosage();
						customOrderBom.setUnitDosage(unitDosage);
						/**
						 * 单位
						 */
						Unit unit1 = dzscCustomsBomDetail.getUnit();
						customOrderBom.setUnit(unit1);
						/**
						 * 数量
						 */
						Double amount = CommonUtils.getDoubleByDigit(
								CommonUtils.getDoubleExceptNull(customOrderExg
										.getAmount()
										* unitDosage), decimalSize);

						customOrderBom.setAmount(amount);
						/**
						 * 已转厂数量
						 */
						Double transNum = customOrderExg.getTransNum() == null ? 0.0
								: customOrderExg.getTransNum() * unitDosage;
						customOrderBom.setTransNum(transNum);

						/**
						 * 已转合同数量
						 */
						Double contractNum = customOrderExg.getContractNum() == null ? 0.0
								: customOrderExg.getContractNum() * unitDosage;
						customOrderBom.setContractNum(contractNum);
						/**
						 * 未转厂数量
						 */
						Double notTransNum = customOrderExg.getNotTransNum() == null ? 0.0
								: customOrderExg.getNotTransNum() * unitDosage;
						customOrderBom.setNotTransNum(notTransNum);
						/**
						 * 未转合同数量
						 */
						Double notContractNum = customOrderExg
								.getNotContractNum() == null ? 0.0
								: customOrderExg.getNotContractNum()
										* unitDosage;
						customOrderBom.setNotContractNum(notContractNum);
						/**
						 * 单价
						 */
						Double unitPrice = customOrderExg.getUnitPrice()
								* unitDosage;
						customOrderBom.setUnitPrice(unitPrice);
						/**
						 * 金额
						 */
						Double totalPrice = CommonUtils.getDoubleByDigit(
								CommonUtils.getDoubleExceptNull(unitPrice
										* amount), decimalSize);

						customOrderBom.setTotalPrice(totalPrice);
						customOrderBom.setCustomOrderExg(customOrderExg);
						// customOrderBom.setCustomOrder(customOrderExg.getCustomOrder());
						this.orderCommonDao.saveCustomOrderBom(customOrderBom);
					}

				}

			} else if (rateSource == 2) {

				for (int i = 0; i < list.size(); i++) {

					List<TempOrderBom> valueMap = new ArrayList<TempOrderBom>();
					CustomOrderDetail customOrderDetail = (CustomOrderDetail) list
							.get(i);
					String ptNo = customOrderDetail.getMateriel().getPtNo();
					String materieltype = "0"; // 0为成品，２为料件
					/**
					 * 工厂成品转报关品名
					 */
					DzscInnerMergeData dzscInnerMergeData = this.orderCommonDao
							.findDzscInnerMergeData(ptNo, materieltype);
					// 成品的料件组成
					List listBom = findCustomOrderBom(ptNo, type);

					/**
					 * 根据工厂成品获得其折算料件组成
					 */

					valueMap = getTempOrderBomMapDZSC(customOrderDetail,
							listBom, dzscInnerMergeData, decimalSize);
					/**
					 * 由工厂料件转报关品名
					 */

					getCustomOrderBomDZSC(customOrderDetail, valueMap,
							decimalSize);

				}
			}
			/**
			 * 合并报关料件总表
			 */
			this.delCustomsOrderImg(customOrder);

			uniteCustomOrderBomDzsc(customOrder, decimalSize);
		} else if (type == 2 || type == 3) {

			for (int i = 0; i < list.size(); i++) {

				List<TempOrderBom> valueMap = new ArrayList<TempOrderBom>();
				CustomOrderDetail customOrderDetail = (CustomOrderDetail) list
						.get(i);
				String ptNo = customOrderDetail.getMateriel().getPtNo();
				String materieltype = "0";
				BcsInnerMerge bcsInnerMerge = this.orderCommonDao
						.findBcsInnerMerge(ptNo, materieltype);

				List listBom = findCustomOrderBom(ptNo, type);
				/**
				 * 根据工厂成品获得其折算料件组成
				 */

				valueMap = getTempOrderBomMapBCS(customOrderDetail, listBom,
						bcsInnerMerge, decimalSize);
				/**
				 * 由工厂料件转报关品名
				 */

				getCustomOrderBomBCS(customOrderDetail, valueMap, decimalSize);

			}
			/**
			 * 合并报关料件总表
			 */
			this.orderCommonDao.delCustomsOrderImg(customOrder);
			uniteCustomOrderBom(customOrder, decimalSize);

		}

	}

	/**
	 * 保存定单成品
	 * 
	 * @param list
	 */
	public void saveCustomOrderExg(List<CustomOrderExg> list) {
		for (CustomOrderExg coe : list) {
			this.orderCommonDao.saveCustomOrderExg(coe);
		}
	}

	public void saveCustomOrderBom(List<CustomOrderBom> list) {
		for (CustomOrderBom cob : list) {
			this.orderCommonDao.saveCustomOrderBom(cob);
		}
	}

	/**
	 * 根据成品料号查询报关常用工厂BOM料件资料
	 * 
	 * @param list
	 */
	public List findCustomOrderBom(String ptNo, Integer type) {
		return this.orderCommonDao.findMaterialBomDetail(ptNo, type);
	}

	/**
	 * 根据定单表头查询定单ＢＯＭ
	 * 
	 * @param list
	 */
	public List findCustomOrderBom(CustomOrder customOrder) {
		return this.orderCommonDao.findCustomOrderBom(customOrder);
	}

	/**
	 * 合并报关料件总表(bcs)
	 * 
	 * @param customOrder
	 */
	public void uniteCustomOrderBom(CustomOrder customOrder, int decimalSize) {

		List list = findCustomOrderBom(customOrder);

		Map<String, CustomOrderImg> customOrderImgMap = new HashMap<String, CustomOrderImg>();
		List<CustomOrderImg> listCustomOrderImg = new ArrayList<CustomOrderImg>();

		for (int i = 0; i < list.size(); i++) {
			CustomOrderImg customOrderImg = new CustomOrderImg();
			CustomOrderBom customOrderBom = (CustomOrderBom) list.get(i);
			String complex = customOrderBom.getComplex() == null ? ""
					: customOrderBom.getComplex().getName();
			String tenName = customOrderBom.getName();
			String tenSpec = customOrderBom.getSpec();
			String tenUnit = customOrderBom.getUnit() == null ? ""
					: customOrderBom.getUnit().getName();
			String key = complex + (tenName == null ? "" : "/" + tenName)
					+ (tenSpec == null ? "" : "/" + tenSpec)
					+ (tenUnit == null ? "" : "/" + tenUnit);
			if (customOrderImgMap.get(key) == null) {
				// customOrderImg.setComplex(complex);
				customOrderImg.setCustomOrder(customOrder);
				/**
				 * 商品编码
				 */
				customOrderImg.setComplex(customOrderBom.getComplex());
				/**
				 * 商品名称
				 */
				customOrderImg.setName(customOrderBom.getName());
				/**
				 * 规格型号
				 */
				customOrderImg.setSpec(customOrderBom.getSpec());
				/**
				 * 计量单位
				 */
				customOrderImg.setUnit(customOrderBom.getUnit());

				/**
				 * 单项用量
				 */
				Double wasteAmount = CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(customOrderBom.getUnitWaste()
								* customOrderBom.getAmount()), 9);
				customOrderImg.setWasteAmount(wasteAmount);
				/**
				 * 企业申报单价
				 */
				customOrderImg.setUnitPrice(customOrderBom.getUnitPrice());

				/**
				 * 数量
				 */

				customOrderImg.setAmount(customOrderBom.getAmount());
				/**
				 * 币别
				 */
				customOrderImg.setCurr(customOrderBom.getCurr());
				customOrderImg.setContractNum(customOrderBom.getContractNum());
				customOrderImg.setNotContractNum(customOrderBom
						.getNotContractNum());
				customOrderImg.setTransNum(customOrderBom.getTransNum());
				customOrderImg.setNotTransNum(customOrderBom.getNotTransNum());

				/**
				 * 总金额
				 */
				Double totalPrice = CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(customOrderImg.getUnitPrice()
								* customOrderImg.getAmount()), decimalSize);
				customOrderImg.setTotalPrice(totalPrice);
				customOrderImgMap.put(key, customOrderImg);
			} else {
				customOrderImg = customOrderImgMap.get(key);
				/**
				 * 损耗数量
				 */
				Double wasteAmount = (customOrderImg.getWasteAmount() + customOrderBom
						.getUnitWaste()
						* customOrderBom.getAmount()) / 2;

				/**
				 * 企业申报单价
				 */
				Double declarePrice = (customOrderImg.getUnitPrice() + customOrderBom
						.getUnitPrice()) / 2;

				/**
				 * 数量
				 */
				Double amount = customOrderImg.getAmount()
						+ customOrderBom.getAmount();
				Double contractNum = customOrderImg.getContractNum()
						+ customOrderBom.getContractNum();
				Double notContractNum = customOrderImg.getNotContractNum()
						+ customOrderBom.getContractNum();
				Double transNum = customOrderImg.getTransNum()
						+ customOrderBom.getTransNum();
				Double notTransNum = customOrderImg.getNotTransNum()
						+ customOrderBom.getNotTransNum();

				/**
				 * 总金额
				 */
				Double totalPrice = CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(customOrderImg.getTotalPrice()
								+ customOrderImg.getUnitPrice()
								* customOrderImg.getAmount()), decimalSize);

				customOrderImgMap.remove(customOrderImg);
				customOrderImg.setUnitPrice(declarePrice);
				customOrderImg.setAmount(amount);
				customOrderImg.setContractNum(contractNum);
				customOrderImg.setNotContractNum(notContractNum);
				customOrderImg.setTransNum(transNum);
				customOrderImg.setNotTransNum(notTransNum);
				customOrderImg.setWasteAmount(wasteAmount);
				customOrderImg.setTotalPrice(totalPrice);
				// customOrderImg.setCustomOrder(customOrder);
				/**
				 * 商品编码
				 */
				customOrderImg.setComplex(customOrderBom.getComplex());
				/**
				 * 商品名称
				 */
				customOrderImg.setName(customOrderBom.getName());
				/**
				 * 规格型号
				 */
				customOrderImg.setSpec(customOrderBom.getSpec());
				/**
				 * 币别
				 */
				customOrderImg.setCurr(customOrderBom.getCurr());
				/**
				 * 计量单位
				 */
				customOrderImg.setUnit(customOrderBom.getUnit());
				customOrderImgMap.put(key, customOrderImg);

			}

		}
		listCustomOrderImg.addAll(customOrderImgMap.values());
		/**
		 * 保存报关料件
		 */
		for (int j = 0; j < listCustomOrderImg.size(); j++) {
			CustomOrderImg coi = (CustomOrderImg) listCustomOrderImg.get(j);
			coi.setSeqNum(j);
			this.orderCommonDao.saveCustomOrderImg(coi);
		}
	}

	/**
	 * 合并报关料件总表(dzsc)
	 * 
	 * @param customOrder
	 */
	public void uniteCustomOrderBomDzsc(CustomOrder customOrder, int decimalSize) {
		List list = findCustomOrderBom(customOrder);
		Map<String, CustomOrderImg> customOrderImgMap = new HashMap<String, CustomOrderImg>();
		List<CustomOrderImg> listCustomOrderImg = new ArrayList<CustomOrderImg>();
		for (int i = 0; i < list.size(); i++) {
			CustomOrderImg customOrderImg = new CustomOrderImg();
			CustomOrderBom customOrderBom = (CustomOrderBom) list.get(i);
			String complex = customOrderBom.getComplex() == null ? ""
					: customOrderBom.getComplex().getName();
			String tenName = customOrderBom.getName();
			String tenSpec = customOrderBom.getSpec();
			String tenUnit = customOrderBom.getUnit() == null ? ""
					: customOrderBom.getUnit().getName();
			String key = complex + (tenName == null ? "" : "/" + tenName)
					+ (tenSpec == null ? "" : "/" + tenSpec)
					+ (tenUnit == null ? "" : "/" + tenUnit);
			if (customOrderImgMap.get(key) == null) {
				customOrderImg.setCustomOrder(customOrder);
				/**
				 * 商品编码
				 */
				customOrderImg.setComplex(customOrderBom.getComplex());
				/**
				 * 商品名称
				 */
				customOrderImg.setName(customOrderBom.getName());
				/**
				 * 规格型号
				 */
				customOrderImg.setSpec(customOrderBom.getSpec());
				/**
				 * 计量单位
				 */
				customOrderImg.setUnit(customOrderBom.getUnit());

				/**
				 * 单项用量
				 */

				customOrderImg.setWasteAmount(CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(customOrderBom
								.getUnitWaste()
								* customOrderBom.getAmount()), 9));
				/**
				 * 单价
				 */
				customOrderImg.setUnitPrice(customOrderBom.getUnitPrice());

				/**
				 * 数量
				 */

				customOrderImg.setAmount(customOrderBom.getAmount());
				customOrderImg.setContractNum(customOrderBom.getContractNum());
				customOrderImg.setNotContractNum(customOrderBom
						.getNotContractNum());
				customOrderImg.setTransNum(customOrderBom.getTransNum());
				customOrderImg.setNotTransNum(customOrderBom.getNotTransNum());

				/**
				 * 币别
				 */
				customOrderImg.setCurr(customOrderBom.getCurr());
				/**
				 * 总金额
				 */

				customOrderImg.setTotalPrice(CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(customOrderImg
								.getUnitPrice()
								* customOrderImg.getAmount()), decimalSize));
				customOrderImgMap.put(key, customOrderImg);
			} else {
				customOrderImg = customOrderImgMap.get(key);
				/**
				 * 损耗数量
				 */
				Double wasteAmount = (customOrderImg.getWasteAmount() + customOrderBom
						.getUnitWaste()
						* customOrderBom.getAmount()) / 2;

				/**
				 * 单价
				 */
				Double unitPrice = (customOrderImg.getUnitPrice() + customOrderBom
						.getUnitPrice()) / 2;

				/**
				 * 数量
				 */
				Double amount = customOrderImg.getAmount()
						+ customOrderBom.getAmount();
				Double contractNum = customOrderImg.getContractNum()
						+ customOrderBom.getContractNum();
				Double notContractNum = customOrderImg.getNotContractNum()
						+ customOrderBom.getContractNum();
				Double transNum = customOrderImg.getTransNum()
						+ customOrderBom.getTransNum();
				Double notTransNum = customOrderImg.getNotTransNum()
						+ customOrderBom.getNotTransNum();

				/**
				 * 总金额
				 */

				Double totalPrice = CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(customOrderImg.getTotalPrice()
								+ customOrderImg.getUnitPrice()
								* customOrderImg.getAmount()), decimalSize);
				customOrderImgMap.remove(customOrderImg);
				customOrderImg.setUnitPrice(unitPrice);
				customOrderImg.setAmount(amount);
				customOrderImg.setContractNum(contractNum);
				customOrderImg.setNotContractNum(notContractNum);
				customOrderImg.setTransNum(transNum);
				customOrderImg.setNotTransNum(notTransNum);
				customOrderImg.setWasteAmount(wasteAmount);
				customOrderImg.setTotalPrice(totalPrice);

				/**
				 * 商品编码
				 */
				customOrderImg.setComplex(customOrderBom.getComplex());
				/**
				 * 商品名称
				 */
				customOrderImg.setName(customOrderBom.getName());
				/**
				 * 规格型号
				 */
				customOrderImg.setSpec(customOrderBom.getSpec());
				/**
				 * 币别
				 */
				customOrderImg.setCurr(customOrderBom.getCurr());
				/**
				 * 计量单位
				 */
				customOrderImg.setUnit(customOrderBom.getUnit());
				customOrderImgMap.put(key, customOrderImg);

			}

		}
		listCustomOrderImg.addAll(customOrderImgMap.values());
		/**
		 * 保存报关料件
		 */
		for (int j = 0; j < listCustomOrderImg.size(); j++) {

			CustomOrderImg coi = (CustomOrderImg) listCustomOrderImg.get(j);

			coi.setSeqNum(j);
			this.orderCommonDao.saveCustomOrderImg(coi);
		}
	}

	/**
	 * 根据定单表头查询定单报关成品
	 * 
	 * @param customOrder
	 * @return
	 */
	public List findCustomOrderExg(CustomOrder customOrder) {
		return this.orderCommonDao.findCustomOrderExg(customOrder);
	}

	public List findFactoryExg(Complex complex, String tenName, String tenSpec,
			String tenUnit, Integer type) {
		return this.orderCommonDao.findFactoryExg(complex, tenName, tenSpec,
				tenUnit, type);

	}

	public void delCustomOrderAll(CustomOrder customOrder) {
		List listCustomOrderBom = this.findCustomOrderBom(customOrder);
		List listCustomOrderExg = this.findCustomOrderExg(customOrder);
		List listCustomOrderImg = this.orderCommonDao
				.findCustomOrderImg(customOrder);

		for (int i = 0; i < listCustomOrderBom.size(); i++) {
			CustomOrderBom customOrderBom = (CustomOrderBom) listCustomOrderBom
					.get(i);
			this.orderCommonDao.delCustomsOrderBom(customOrderBom);
		}

		for (int i = 0; i < listCustomOrderImg.size(); i++) {
			CustomOrderImg customOrderImg = (CustomOrderImg) listCustomOrderImg
					.get(i);
			this.orderCommonDao.delCustomsOrderImg(customOrderImg);
		}

		for (int i = 0; i < listCustomOrderExg.size(); i++) {
			CustomOrderExg customOrderExg = (CustomOrderExg) listCustomOrderExg
					.get(i);
			this.orderCommonDao.delCustomsOrderExg(customOrderExg);
		}

	}

	public void delectCustomOrder(List list) {

		for (int i = 0; i < list.size(); i++) {
			CustomOrder customOrder = (CustomOrder) list.get(i);
			this.orderCommonDao.delectCustomOrderDetail(customOrder);
		}

	}

	/**
	 * 逻辑检控：检查定单明细中的成品是否在报关常用ＢＯＭ存在
	 */

	public List isExitInCustomBom(CustomOrder customOrder, String materieltype) {
		List<String> listPtNo = new ArrayList<String>();
		String billCode = customOrder.getBillCode();
		Integer rateSource = customOrder.getRateSource();
		Map<String, DzscCustomsBomExg> dcbeMap = new HashMap<String, DzscCustomsBomExg>();
		List listCustomOrderDetail = this.orderCommonDao
				.findCustomOrderDetail(billCode);
		/**
		 * 查询报关单耗中的报关成品
		 */
		List listExg = getDzscCustomBomExg();

		// 报关类型: 1:电子手册 2:纸质手册 3:电子化手册
		Integer type = customOrder.getCustomType();

		for (int i = 0; i < listCustomOrderDetail.size(); i++) {
			CustomOrderDetail cod = (CustomOrderDetail) listCustomOrderDetail
					.get(i);
			String ptNo = cod.getMateriel().getPtNo();

			if ("0".equals(materieltype)) { // 成品
				if (rateSource == 3) {
					if (listExg.size() == 0) {
						listPtNo.add(ptNo);
					}
				} else {
					Boolean flag = notExitInCustomBom(ptNo, type, rateSource,
							materieltype, listExg);
					if (!flag) {
						listPtNo.add(ptNo);
					}
				}

			} else if ("2".equals(materieltype)) { // 料件
				if (rateSource == 3) {
					if (listExg.size() == 0) {
						listPtNo.add(ptNo);
					}
				} else {
					Boolean flag = notExitInCustomBomByMaterial(ptNo, type,
							rateSource, materieltype, listExg);
					if (!flag) {
						listPtNo.add(ptNo);
					}
				}

			}

		}
		return listPtNo;
	}

	/**
	 * 逻辑检控：检查定单明细中的成品是否在归并关系存在
	 */

	public List isExitInInner(CustomOrder customOrder, String materieltype) {
		List<String> listPtNo = new ArrayList<String>();

		String billCode = customOrder.getBillCode();
		Integer type = customOrder.getCustomType();
		List listCustomOrderDetail = this.orderCommonDao
				.findCustomOrderDetail(billCode);

		for (int i = 0; i < listCustomOrderDetail.size(); i++) {
			CustomOrderDetail cod = (CustomOrderDetail) listCustomOrderDetail
					.get(i);
			String ptNo = cod.getMateriel().getPtNo();
			if ("0".equals(materieltype)) { // 成品
				Boolean flag = notExitInInner(type, ptNo);
				if (!flag) {
					listPtNo.add(ptNo);
				}

			} else if ("2".equals(materieltype)) { // 料件
				Boolean flag = notExitInInnerByMaterial(type, ptNo);
				if (!flag) {
					listPtNo.add(ptNo);
				}

			}
		}
		return listPtNo;
	}

	/**
	 * 逻辑检控：检查定单明细中的成品是否已备案
	 */

	public List isExitInRecords(CustomOrder customOrder, String materieltype) {
		List<String> listTenPtNo = new ArrayList<String>();

		String billCode = customOrder.getBillCode();
		Integer type = customOrder.getCustomType();

		List listCustomOrderDetail = this.orderCommonDao
				.findCustomOrderDetail(billCode);
		for (int i = 0; i < listCustomOrderDetail.size(); i++) {
			CustomOrderDetail cod = (CustomOrderDetail) listCustomOrderDetail
					.get(i);
			String ptNo = cod.getMateriel().getPtNo();
			if ("0".equals(materieltype)) {

				Boolean flag = ExitInRecords(type, ptNo);
				if (!flag) {
					listTenPtNo.add(ptNo);
				}

			} else if ("2".equals(materieltype)) {
				Boolean flag = ExitInRecordsByMaterial(type, ptNo);
				if (!flag) {
					listTenPtNo.add(ptNo);
				}

			}
		}
		return listTenPtNo;
	}

	public List findOrders(String type, ScmCoc scmcoc, Date dateBegin,
			Date dateEnd, String orderCode, Boolean ifzc, Integer customType,
			String groupingColumn) {
		String[] strs = split(orderCode, ",");
		String[] strsGroupingColumn = split(groupingColumn, ",");

		if (ifzc) {
			return this.orderCommonDao.findOrders(type, scmcoc, dateBegin,
					dateEnd, strs, ifzc, customType, strsGroupingColumn);
		} else {
			return this.orderCommonDao.findOrders(type, scmcoc, dateBegin,
					dateEnd, strs, customType, strsGroupingColumn);
		}

	}

	public List findCustomOrder(Integer type, ScmCoc scmCoc, Date beginDate,
			Date endDate, String orderCode, Boolean isTransfer) {

		String[] strs = split(orderCode, ",");

		return this.orderCommonDao.findCustomOrder(type, scmCoc, beginDate,
				endDate, strs, isTransfer);
	}

	/**
	 * 用于订单统计报表中取得订单号码
	 */
	public List findTempCustomsOrder(Integer customType) {
		List list = orderCommonDao.findCustomOrder(customType);
		List tempListExg = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			CustomOrder order = (CustomOrder) list.get(i);
			BillTemp obj = new BillTemp();
			obj.setBill1(order.getBillCode());
			obj.setBill2(order.getOrderDate().toString());
			obj.setBill3(order.getCustomer().getName());
			tempListExg.add(obj);
		}
		return tempListExg;
	}

	/**
	 * 字符串分离
	 * 
	 * @param sourceStr
	 * @param matchStr
	 * @return
	 */
	private String[] split(String sourceStr, String matchStr) {
		if (sourceStr == null) {
			return new String[0];
		}
		List<String> list = new ArrayList<String>();
		while (sourceStr.indexOf(matchStr) != -1) {
			String tempS = sourceStr.substring(0, sourceStr.indexOf(matchStr));
			sourceStr = sourceStr.substring(sourceStr.indexOf(matchStr)
					+ matchStr.length(), sourceStr.length());

			if (!"".equals(tempS.trim()) && tempS != null) {
				list.add(tempS);
			}

			// list.add(tempS);

		}

		list.add(sourceStr);
		String[] str = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {

			str[i] = list.get(i);

		}
		return str;
	}

	/**
	 * 电子手册工厂成品转报关成品 (dzsc)
	 * 
	 * @param customOrder
	 * @param bcsInnerMerge
	 * @param customOrderDetail
	 */
	public String setCustomExg(CustomOrder customOrder,
			DzscInnerMergeData dzscInnerMergeData,
			CustomOrderDetail customOrderDetail) {
		String key = null;
		if (dzscInnerMergeData != null) {
			Complex complex = dzscInnerMergeData.getDzscTenInnerMerge()
					.getTenComplex();
			String complexCode = complex == null ? "" : complex.getCode();
			String tenName = dzscInnerMergeData.getDzscTenInnerMerge() == null
					|| dzscInnerMergeData.getDzscTenInnerMerge().getTenPtName() == null ? ""
					: dzscInnerMergeData.getDzscTenInnerMerge().getTenPtName();
			String tenSpec = dzscInnerMergeData.getDzscTenInnerMerge() == null
					|| dzscInnerMergeData.getDzscTenInnerMerge().getTenPtSpec() == null ? ""
					: dzscInnerMergeData.getDzscTenInnerMerge().getTenPtSpec();

			String tenUnit = dzscInnerMergeData.getDzscTenInnerMerge()
					.getTenUnit() == null
					|| dzscInnerMergeData.getDzscTenInnerMerge().getTenUnit()
							.getName() == null ? "" : dzscInnerMergeData
					.getDzscTenInnerMerge().getTenUnit().getName();
			key = complexCode + "/" + tenName + "/" + tenSpec + "/" + tenUnit;
		}

		return key;

	}

	public List getDzscCustomBomExg() {
		return this.orderCommonDao.getDzscCustomBomExg();
	}

	public List getDzscCustomsBomDetail(DzscCustomsBomExg dcbe) {
		return this.orderCommonDao.getDzscCustomsBomDetail(dcbe);
	}

	/**
	 * 订单导入文件来自文件
	 * 
	 * @param list
	 */
	public void importDataFromTxtFile(List list, String taskId,
			Boolean cbIsOverwrite) {
		// wss:2010-05-20加上用户设定的小数保留位数
		Customparames customparam = this.orderCommonDao.findCustomparames();
		int decimalSize = customparam == null ? 5 : customparam
				.getDecimalSize();

		OrderDate fileData = null;
		Map<String, String> importOrderMap = new HashMap<String, String>();
		Map<String, String> importOrderDetailMap = new HashMap<String, String>();
		// 对作废的订单进行处理(删除)
		List<OrderDate> initOrderDateList = new ArrayList<OrderDate>();
		for (int i = 0; i < list.size(); i++) {
			fileData = (OrderDate) list.get(i);
			String billCode = fileData.getBillCode();
			String orderERPId = fileData.getOrderERPId();
			String ptNo = fileData.getPtNo();
			CustomOrderDetail customOrderDetail = this.orderCommonDao
					.findCustomOrderDetailByOrderERPId(orderERPId);

			if (fileData.getState() != null
					&& "2".equals(fileData.getState().trim())
					&& customOrderDetail != null) {
				if (!customOrderDetail.getCustomOrder().getIfok()) {
					this.orderCommonDao
							.deleteCustomOrdeDetail(customOrderDetail);
				} else {
					this.orderCommonDao.delCustomOrderDetail(customOrderDetail);
				}
			} else {
				initOrderDateList.add(fileData);
				if (importOrderMap.get(billCode) == null) {
					importOrderMap.put(billCode, billCode);
				}
				// if (orderERPId != null && !"".equals(orderERPId)) {
				// if (importOrderDetailMap.get(orderERPId) == null) {
				// importOrderDetailMap.put(orderERPId, orderERPId);
				// }
				// }
				if (importOrderDetailMap.get(ptNo) == null) {
					importOrderDetailMap.put(ptNo, ptNo);
				}
			}
		}

		Customparames customparames = this.orderCommonDao.findCustomparames();
		Integer customType = customparames.getSetbgtype();
		CustomOrderDetail customOrderDetail = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<String> listKeyValue = new ArrayList<String>();
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setBeginTime(System.currentTimeMillis());
			info.setTotalNum(list.size());
			info.setCurrentNum(0);
			info.setMethodName("系统正在导入文件资料");
		}
		/**
		 * 获得已存在的定单数据 key = 定单号＋客户＋是否转厂
		 */

		Map<String, CustomOrder> existOrderMap = new HashMap<String, CustomOrder>();
		List<String> listOrderNo = new ArrayList<String>();
		listOrderNo.addAll(importOrderMap.values());
		List<CustomOrder> existCustomOrderDataList = orderCommonDao
				.findCustomOrder(customType, listOrderNo);

		for (int i = 0, n = existCustomOrderDataList.size(); i < n; i++) {
			CustomOrder temp = existCustomOrderDataList.get(i);
			String scmCocName = temp.getCustomer() == null ? "" : temp
					.getCustomer().getName();
			String billCode = temp.getBillCode() == null ? "" : temp
					.getBillCode();
			// String ifOk = temp.getIfzc() == null
			// || "false".equals(temp.getIfzc()) ? "false" :
			// temp.getIfzc().toString();
			Integer orderType = temp.getOrdertype();
			String type = String.valueOf(temp.getCustomType());
			String key = scmCocName + "/" + billCode + "/" + orderType + "/"
					+ type;
			existOrderMap.put(key, temp);

		}
		/**
		 * 获得已存在的定单明细数据 key1 = 定单号＋客户＋是否转厂 key２ ＝ ERP系统中ID //定单号＋客户＋是否转厂＋料号＋送货日期
		 */
		Map<String, CustomOrderDetail> existOrderDetailMap = new HashMap<String, CustomOrderDetail>();
		Map<String, OrderDate> keyValueMap = new HashMap<String, OrderDate>();

		List<String> listOrderDetail = new ArrayList<String>();
		// listOrderDetail.addAll(importOrderDetailMap.values());
		listOrderDetail.addAll(importOrderMap.values());
		List<CustomOrderDetail> existOrderDetailList = orderCommonDao
				.findCustomOrderDetail(customType, listOrderDetail);

		for (int i = 0; i < existOrderDetailList.size(); i++) {
			CustomOrderDetail cod = existOrderDetailList.get(i);
			String orderERPId = cod.getOrderERPId();
			String scmCocName = cod.getCustomOrder().getCustomer() == null ? ""
					: cod.getCustomOrder().getCustomer().getName();
			String billCode = cod.getCustomOrder().getBillCode() == null ? ""
					: cod.getCustomOrder().getBillCode();
			Integer orderType = cod.getCustomOrder().getOrdertype();
			String type = String.valueOf(cod.getCustomOrder().getCustomType());
			// key2=定单号＋客户＋料号＋订单类型＋报关类型＋
			String key2 = billCode + "/" + scmCocName + "/"
					+ cod.getMateriel().getPtNo() + "/" + orderType + "/"
					+ type + "/" + cod.getAmount() + "/" + cod.getUnitPrice()
					+ "/" + cod.getCalUnit().getName() + "/"
					+ cod.getCurr().getName();
			if (key2 != null && !key2.equals("")) {
				existOrderDetailMap.put(key2, cod);
			}

		}
		/**
		 * 获得导入的定单表头数据
		 */

		for (int i = 0; i < initOrderDateList.size(); i++) {
			fileData = (OrderDate) initOrderDateList.get(i);
			String scmCocName = fileData.getScmCoc() == null ? "" : fileData
					.getScmCoc().trim();
			String billCode = fileData.getBillCode() == null ? "" : fileData
					.getBillCode().trim();
			// String ifzc = fileData.getIfzc() == null
			// || "false".equals(fileData.getIfzc().trim()) ? "false"
			// : fileData.getIfzc().trim();
			Integer orderType = 0;
			if ("采购订单".equals(fileData.getOrderType())) {
				orderType = 1;
			} else {
				orderType = 0;
			}
			String type = String.valueOf(customparames.getSetbgtype());
			String key = scmCocName + "/" + billCode + "/" + orderType + "/"
					+ type;
			if (keyValueMap.get(key) != null) {
				continue;
			}
			keyValueMap.put(key, fileData);
			listKeyValue.add(key);

		}
		// 保存订单表头
		for (int i = 0; i < listKeyValue.size(); i++) {
			String key = listKeyValue.get(i).toString();
			OrderDate orderDate = keyValueMap.get(key);
			String scmCocName = orderDate.getScmCoc() == null ? "" : orderDate
					.getScmCoc().trim();
			String billCode = orderDate.getBillCode() == null ? "" : orderDate
					.getBillCode().trim();
			String date = fileData.getOrderDate().trim();
			ScmCoc sc = orderCommonDao.findScmCoc(scmCocName);
			if (existOrderMap.get(key) != null) {
				CustomOrder customOrder = existOrderMap.get(key);
				Integer importcount = customOrder.getImportcount() + 1;
				customOrder.setImportcount(importcount);
				try {
					customOrder.setOrderDate(sdf.parse(date));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				existOrderMap.remove(customOrder);
				customOrder = orderCommonDao.saveCustomOrder(customOrder);
				existOrderMap.put(key, customOrder);

			} else {
				Boolean ifZc = false;
				CustomOrder customOrder = new CustomOrder();
				customOrder.setBillCode(billCode);
				customOrder.setCustomer(sc);
				if ("true".equals(orderDate.getIfzc() == null
						|| "false".equals(fileData.getIfzc().trim()) ? "false"
						: orderDate.getIfzc().trim())) {
					ifZc = true;
				}
				if ("采购订单".equals(orderDate.getOrderType())) {
					customOrder.setOrdertype(1);
				} else {
					customOrder.setOrdertype(0);
				}
				customOrder.setIfzc(ifZc);
				customOrder.setCustomType(customparames.getSetbgtype());
				customOrder.setCustomparames(customparames);
				try {
					customOrder.setOrderDate(sdf.parse(date));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				customOrder.setImportcount(1);
				customOrder.setRateSource(customparames.getRateSource());
				customOrder = orderCommonDao.saveCustomOrder(customOrder);
				existOrderMap.put(key, customOrder);
			}
		}

		/**
		 * 获得导入的定单表体数据
		 */
		for (int i = 0; i < initOrderDateList.size(); i++) {
			fileData = (OrderDate) initOrderDateList.get(i);
			String orderERPId = fileData.getOrderERPId();
			String date = fileData.getOrderDate() == null ? "" : fileData
					.getOrderDate().trim();
			String scmCocName = fileData.getScmCoc() == null ? "" : fileData
					.getScmCoc().trim();
			String billCode = fileData.getBillCode() == null ? "" : fileData
					.getBillCode().trim();
			// String ifzc = fileData.getIfzc() == null
			// || "false".equals(fileData.getIfzc().trim()) ? "false"
			// : fileData.getIfzc().trim();
			Integer orderType = 0;
			if ("采购订单".equals(fileData.getOrderType())) {
				orderType = 1;
			} else {
				orderType = 0;
			}
			String ptNo = fileData.getPtNo() == null ? "" : fileData.getPtNo()
					.trim();
			String type = String.valueOf(customparames.getSetbgtype());
			String salesDate = fileData.getSalesDate() == null ? "" : fileData
					.getSalesDate().toString();

			String key1 = scmCocName + "/" + billCode + "/" + orderType + "/"
					+ type;
			// String key = orderERPId;
			// key2=定单号＋客户＋料号＋订单类型＋报关类型
			String key = billCode + "/" + scmCocName + "/" + fileData.getPtNo()
					+ "/" + orderType + "/" + type + "/"
					+ fileData.getBgAmount() + "/" + fileData.getUnitPrice()
					+ "/" + fileData.getBgUnit() + "/" + fileData.getCurr();
			if (existOrderDetailMap.get(key) == null) {
				Materiel ml = this.orderCommonDao.findMateriel(ptNo);// materialManageDao.findMaterielByPtNo(ptNo);
				CalUnit unit = orderCommonDao.findCalUnit(fileData.getBgUnit()
						.trim());
				CustomOrder customOrder = new CustomOrder();
				CustomOrderDetail cod = new CustomOrderDetail();
				/**
				 * 报关折算系数
				 */
				Double unitConvert = (ml.getUnitConvert() == null || ml
						.getUnitConvert() == 0.0) ? 1.0 : ml.getUnitConvert();
				cod.setUnitConvert(unitConvert);
				customOrder = existOrderMap.get(key1);
				customOrder.setContractCount(i + 1);
				cod.setCustomOrder(customOrder);
				cod.setMateriel(ml);
				cod.setAmount(Double.valueOf(fileData.getBgAmount()));
				// cod.setBgunit(unit);
				cod.setState(fileData.getState() == null
						|| "".equals(fileData.getState().trim()) ? 0 : Integer
						.valueOf(fileData.getState().trim()));
				cod.setOrderERPId(fileData.getOrderERPId());

				/**
				 * 单位
				 */
				cod.setCalUnit(unit);

				/**
				 * 币别
				 */
				Curr curr = orderCommonDao.findCurr(fileData.getCurr().trim());
				if (curr != null) {
					cod.setCurr(curr);
				}

				/**
				 * 单价
				 */
				Double unitPrice = fileData.getUnitPrice() == null ? 0.0
						: Double.valueOf(fileData.getUnitPrice());
				cod.setUnitPrice(unitPrice);
				/**
				 * 数量
				 */
				Double amount = Double.valueOf(fileData.getBgAmount());
				cod.setAmount(amount);

				/**
				 * 报关数量
				 */
				Double bgamount = CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(unitConvert * cod.getAmount()),
						decimalSize);
				cod.setBgamount(bgamount);
				/**
				 * 未转合同数量
				 */
				Double notContractNum = bgamount;
				cod.setNotContractNum(notContractNum);
				cod.setNotTransNum(0.0);
				cod.setContractNum(0.0);
				cod.setTransNum(0.0);
				/**
				 * 总金额
				 */
				Double totalPrice = CommonUtils
						.getDoubleByDigit(CommonUtils
								.getDoubleExceptNull(unitPrice * bgamount),
								decimalSize);
				cod.setTotalPrice(totalPrice);

				/**
				 * 报关名称
				 */
				cod.setBgname(ml.getPtName());

				/**
				 * 报关规格
				 */
				cod.setBgspec(ml.getPtSpec());

				/**
				 * 报关单位
				 */
				cod.setBgunit(ml.getPtUnit());
				try {
					cod.setSalesDate(sdf.parse(salesDate));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				System.out.println("ssssssssssssssssssssssssssssssssssss");
				this.orderCommonDao.saveCustomOrderDetail(cod);

			} else {
				System.out.println(existOrderDetailMap.get(key));
				customOrderDetail = existOrderDetailMap.get(key);
				Double unitConvert = customOrderDetail.getUnitConvert();
				customOrderDetail.setAmount(Double.valueOf(fileData
						.getBgAmount()));
				Double amount = customOrderDetail.getAmount() == null ? 0.0
						: customOrderDetail.getAmount();
				Double bgAmount = CommonUtils
						.getDoubleByDigit(CommonUtils
								.getDoubleExceptNull(unitConvert * amount),
								decimalSize);
				customOrderDetail.setBgamount(bgAmount);
				Double contractNum = customOrderDetail.getContractNum();
				if (bgAmount.doubleValue() >= contractNum.doubleValue()) {
					customOrderDetail.setNotContractNum(bgAmount - contractNum);
				} else {
					customOrderDetail.setNotContractNum(0.0);
				}
				Double transNum = customOrderDetail.getTransNum();
				if (customOrderDetail.getCustomOrder().getIfzc()) {
					if (contractNum.doubleValue() >= transNum.doubleValue()) {
						customOrderDetail
								.setNotTransNum(contractNum - transNum);
					} else {
						customOrderDetail.setNotTransNum(0.0);
					}
				}

				this.orderCommonDao.saveCustomOrderDetail(customOrderDetail);
				System.out.println("fileData.getBgAmount()="
						+ customOrderDetail.getAmount());
			}
		}

	}

	/**
	 * 现在暂时没有用订单数据检查
	 * 
	 * @param list
	 * @param ht
	 * @param taskId
	 * @param type
	 * @param rateSource
	 * @return
	 */
	public List checkFileData(List list, Hashtable ht, String taskId,
			Integer type, Integer rateSource) {
		ArrayList<OrderDate> ls = new ArrayList<OrderDate>();
		ArrayList<Integer> temp = new ArrayList<Integer>();
		List listExg = getDzscCustomBomExg();

		OrderDate fileData = null;
		int[] invalidationColNo;
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setBeginTime(System.currentTimeMillis());
			info.setTotalNum(list.size());
			info.setCurrentNum(0);
			info.setMethodName("系统正在检验文件资料");
		}
		for (int i = 0; i < list.size(); i++) {
			temp.clear();
			fileData = (OrderDate) list.get(i);
			String ptNo = fileData.getPtNo() == null ? "" : fileData.getPtNo()
					.trim();
			String billcode = fileData.getBillCode() == null ? "" : fileData
					.getBillCode().trim();
			String code = fileData.getScmCoc() == null ? "" : fileData
					.getScmCoc().trim();
			String orderERPId = fileData.getOrderERPId() == null ? ""
					: fileData.getOrderERPId().trim();
			String state = fileData.getState() == null ? "" : fileData
					.getState().trim();
			String orderDate = fileData.getOrderDate() == null ? "" : fileData
					.getOrderDate().trim();
			String salesDate = fileData.getSalesDate() == null ? "" : fileData
					.getSalesDate().trim();
			if (Integer.parseInt(ht
					.get(Integer.valueOf(OrderDate.order_scmCoc)).toString()) > -1) {
				if (code == null || "".equals(code)) {
					fileData.setErrinfo(fileData.getErrinfo() + "该客户代码不能为空;");
				} else {
					ScmCoc sc = this.orderCommonDao.findScmCoc(code);
					if (sc == null) {
						temp.add(Integer.valueOf(OrderDate.order_scmCoc));
						fileData.setErrinfo(fileData.getErrinfo()
								+ "该客户在系统中不存在;");
					}
				}
			}

			if (Integer.parseInt(ht.get(Integer.valueOf(OrderDate.order_ptNo))
					.toString()) > -1) {
				if ((ptNo == null) || ("".equals(ptNo))) {
					temp.add(Integer.valueOf(OrderDate.order_ptNo));
					fileData.setErrinfo(fileData.getErrinfo() + "该成品货号不能为空;");
				}

			}
			if (Integer.parseInt(ht.get(Integer.valueOf(OrderDate.order_ptNo))
					.toString()) > -1) {
				if ((billcode == null) || ("".equals(billcode))) {
					temp.add(Integer.valueOf(OrderDate.order_ptNo));
					fileData.setErrinfo(fileData.getErrinfo() + "该订单号不能为空;");
				}
			}
			if (ptNo != null && (!"".equals(ptNo))) {
				if (listExg.size() == 0 && rateSource == 3 && type == 1) {
					temp.add(Integer.valueOf(OrderDate.order_ptNo));
					fileData.setErrinfo(fileData.getErrinfo() + "报关单耗中没有相关资料;");
				}
				Boolean flag = notExitInCustomBom(ptNo, type, rateSource, "0",
						listExg);
				Boolean flag1 = notExitInCustomBomByMaterial(ptNo, type,
						rateSource, "2", listExg);

				Boolean flag2 = notExitInInner(type, ptNo);

				Boolean flag3 = notExitInInnerByMaterial(type, ptNo);

				Boolean flag4 = ExitInRecords(type, ptNo);

				Boolean flag5 = ExitInRecordsByMaterial(type, ptNo);

				Materiel ml = this.orderCommonDao.findMateriel(ptNo);
				if (Integer.parseInt(ht.get(
						Integer.valueOf(OrderDate.order_ptNo)).toString()) > -1) {
					if (ml == null) {
						temp.add(Integer.valueOf(OrderDate.order_ptNo));
						fileData.setErrinfo(fileData.getErrinfo()
								+ "该成品货号在物料资料中不存在;");
					}
				}

				if (!flag) {
					temp.add(Integer.valueOf(OrderDate.order_ptNo));
					fileData.setErrinfo(fileData.getErrinfo()
							+ "该成品货号在报关ＢＯＭ中不存在;");
				}
				if (!flag1) {
					temp.add(Integer.valueOf(OrderDate.order_ptNo));
					fileData.setErrinfo(fileData.getErrinfo()
							+ "该成品货号在报关ＢＯＭ中没有料件;");
				}
				if (!flag2) {
					temp.add(Integer.valueOf(OrderDate.order_ptNo));
					fileData.setErrinfo(fileData.getErrinfo()
							+ "该成品货号在归并关系中不存在;");
				}
				if (!flag3) {
					temp.add(Integer.valueOf(OrderDate.order_ptNo));
					fileData.setErrinfo(fileData.getErrinfo()
							+ "该成品货号所用料件在归并关系中不存在;");
				}
				if (!flag4) {
					temp.add(Integer.valueOf(OrderDate.order_ptNo));
					fileData.setErrinfo(fileData.getErrinfo() + "该成品货号没有备案;");
				}
				if (!flag5) {
					temp.add(Integer.valueOf(OrderDate.order_ptNo));
					fileData.setErrinfo(fileData.getErrinfo()
							+ "该成品货号的所用料件没有备案;");
				}
			}

			if (Integer.parseInt(ht.get(
					Integer.valueOf(OrderDate.order_orderDate)).toString()) > -1) {
				if ("".equals(orderDate)) {
					temp.add(Integer.valueOf(OrderDate.order_orderDate));
					fileData.setErrinfo(fileData.getErrinfo() + "订单日期不能为空；");
				} else {
					try {
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"yyyy-MM-dd");
						java.util.Date outDate = dateFormat.parse(orderDate);
						java.sql.Date simpleDate = new java.sql.Date(outDate
								.getTime());
						orderDate = simpleDate.toString();
					} catch (Exception f) {
						temp.add(Integer.valueOf(OrderDate.order_orderDate));
						fileData.setErrinfo(fileData.getErrinfo()
								+ "订单日期格式请用:yyyy-mm-dd；");
					}
				}

			}

			if (Integer.parseInt(ht.get(
					Integer.valueOf(OrderDate.order_salesDate)).toString()) > -1) {
				if ("".equals(salesDate)) {
					temp.add(Integer.valueOf(OrderDate.order_salesDate));
					fileData.setErrinfo(fileData.getErrinfo() + "出货日期不能为空；");
				} else {
					try {
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"yyyy-MM-dd");
						java.util.Date outDate = dateFormat.parse(salesDate);
						java.sql.Date simpleDate = new java.sql.Date(outDate
								.getTime());
						salesDate = simpleDate.toString();
					} catch (Exception f) {
						temp.add(Integer.valueOf(OrderDate.order_salesDate));
						fileData.setErrinfo(fileData.getErrinfo()
								+ "出货日期格式请用:yyyy-mm-dd；");

					}
				}

			}
			if (Integer.parseInt(ht.get(Integer.valueOf(OrderDate.order_state))
					.toString()) > -1) {
				if (state == null || "".equals(state)) {
					temp.add(Integer.valueOf(OrderDate.order_state));
					fileData.setErrinfo(fileData.getErrinfo() + "该订单状态不能为空;");
				}

			}

			if (Integer.parseInt(ht.get(Integer.valueOf(OrderDate.order_erpId))
					.toString()) > -1) {
				if (orderERPId == null || "".equals(orderERPId)) {
					temp.add(Integer.valueOf(OrderDate.order_erpId));
					fileData.setErrinfo(fileData.getErrinfo() + "该订单ID不能为空;");
				}

			}

			if (temp.size() > 0) {
				invalidationColNo = new int[temp.size()];

				for (int j = 0; j < temp.size(); j++) {
					invalidationColNo[j] = Integer.parseInt(temp.get(j)
							.toString());
				}
				fileData.setInvalidationColNo(invalidationColNo);
				fileData.setRecordRow(Integer.valueOf(i));
				ls.add(fileData);

			}
			if (info != null) {
				info.setCurrentNum(i);
			}
		}

		return ls;
	}

	/**
	 * 验证成品在ＢＯＭ中不存在
	 * 
	 * @param ptNo
	 * @return
	 */

	public Boolean notExitInCustomBom(String ptNo, Integer type,
			Integer rateSource, String materieltype, List listExg) {
		Boolean flag = true;
		if (type == 1) {
			if (rateSource == 2) {
				MaterialBomMasterApply mbma = null;
				List list = this.orderCommonDao.findMaterialBomMaster(ptNo,
						type);

				if (list.size() > 0 && list.get(0) != null) {
					mbma = (MaterialBomMasterApply) list.get(0);
				}

				if (mbma == null) {
					flag = false;
				}
			} else if (rateSource == 3) {

				if (listExg.size() == 0) {
					flag = true;
				} else {
					HashMap<String, DzscCustomsBomExg> dcbeMap = new HashMap<String, DzscCustomsBomExg>();
					dcbeMap = this.dzscCustomsBomExgMap(listExg);
					/**
					 * 工厂成品转报关品名
					 */

					DzscInnerMergeData bcsInnerMerge = this.orderCommonDao
							.findDzscInnerMergeData(ptNo, materieltype);

					String key = this.setCustomExg(null, bcsInnerMerge, null);

					if (dcbeMap.get(key) == null) {
						flag = false;
					}
				}

			}

		} else if (type == 2 || type == 3) {
			MaterialBomMaster mbm = null;
			List list = this.orderCommonDao.findMaterialBomMaster(ptNo, type);

			if (list.size() > 0 && list.get(0) != null) {
				mbm = (MaterialBomMaster) list.get(0);
			}

			if (mbm == null) {
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * 验证相应的成品在ＢＯＭ中是否有料件
	 * 
	 * @param ptNo
	 * @param type
	 * @param rateSource
	 * @param materieltype
	 * @param listExg
	 * @return
	 */
	public Boolean notExitInCustomBomByMaterial(String ptNo, Integer type,
			Integer rateSource, String materieltype, List listExg) {
		Boolean flag = true;
		// 料件
		if (type == 1) {
			if (rateSource == 2) { // ２：ＢＯＭ备案
				List list = this.orderCommonDao.findMaterialBomDetail(ptNo,
						type);

				if (list.size() == 0) {
					flag = false;
				}
			} else if (rateSource == 3) { // 报关单耗

				if (listExg.size() == 0) {
					flag = true;
				} else {

					HashMap<String, DzscCustomsBomExg> dcbeMap = new HashMap<String, DzscCustomsBomExg>();
					dcbeMap = this.dzscCustomsBomExgMap(listExg);
					DzscInnerMergeData bcsInnerMerge = this.orderCommonDao
							.findDzscInnerMergeData(ptNo, materieltype);
					String key = this.setCustomExg(null, bcsInnerMerge, null);
					DzscCustomsBomExg dcbe = dcbeMap.get(key);

					if (dcbeMap.get(key) != null) {
						List listdcbd = this.orderCommonDao
								.findDzscCustomsBomDetail(dcbe);

						if (listdcbd.size() == 0) {
							flag = false;
						}
					}

				}

			}

		} else if (type == 2 || type == 3) {
			List list = this.orderCommonDao.findMaterialBomDetail(ptNo, type);

			if (list.size() == 0) {
				flag = false;
			}
		}

		return flag;

	}

	/**
	 * 存放电子手册的成品ＢＯＭ
	 * 
	 * @param listExg
	 * @return
	 */
	private HashMap<String, DzscCustomsBomExg> dzscCustomsBomExgMap(List listExg) {
		HashMap<String, DzscCustomsBomExg> dcbeMap = new HashMap<String, DzscCustomsBomExg>();
		for (int i = 0; i < listExg.size(); i++) {
			DzscCustomsBomExg dcbe = (DzscCustomsBomExg) listExg.get(i);
			Complex complex = dcbe.getComplex();
			String complexCode = complex == null ? "" : complex.getCode();
			String name = dcbe.getName() == null ? "" : dcbe.getName();
			String spec = dcbe.getSpec() == null ? "" : dcbe.getSpec();
			String unit = dcbe.getUnit() == null ? "" : dcbe.getUnit()
					.getName();
			String key = complexCode + "/" + name + "/" + spec + "/" + unit;

			dcbeMap.put(key, dcbe);
		}
		return dcbeMap;
	}

	/**
	 * 验证成品是否在归并关系中存在
	 * 
	 * @param type
	 * @param ptNo
	 * @return
	 */
	public Boolean notExitInInner(Integer type, String ptNo) {
		Boolean flag = true;
		if (type == 1) {
			DzscInnerMergeData dimd = this.orderCommonDao
					.findDzscInnerMergeData(ptNo, "0");
			if (dimd == null) {
				flag = false;
			}
		} else if (type == 2 || type == 3) {
			BcsInnerMerge bim = this.orderCommonDao
					.findBcsInnerMerge(ptNo, "0");

			if (bim == null) {
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * 验证相应成品的料件组成是否在归并关系中存在
	 * 
	 * @param type
	 * @param ptNo
	 * @return
	 */
	public Boolean notExitInInnerByMaterial(Integer type, String ptNo) {
		Boolean flag = true;
		// 料件

		if (type == 1) {
			List list = this.orderCommonDao.findMaterialBomDetail(ptNo, type);
			for (int j = 0; j < list.size(); j++) {
				MaterialBomDetailApply mbda = (MaterialBomDetailApply) list
						.get(j);
				String mbdaPtNo = mbda.getMateriel().getPtNo();
				DzscInnerMergeData dimd = this.orderCommonDao
						.findDzscInnerMergeData(mbdaPtNo, "2");

				if (dimd == null) {
					flag = false;
				}
			}

		} else if (type == 2 || type == 3) {
			List list = this.orderCommonDao.findMaterialBomDetail(ptNo, type);
			for (int j = 0; j < list.size(); j++) {
				MaterialBomDetail mbd = (MaterialBomDetail) list.get(j);
				String mbdPtNo = mbd.getMateriel().getPtNo();
				BcsInnerMerge bim = this.orderCommonDao.findBcsInnerMerge(
						mbdPtNo, "2");
				if (bim == null) {
					flag = false;
				}

			}
		}

		return flag;
	}

	/**
	 * 验证成品是否已备案
	 * 
	 * @param type
	 * @param ptNo
	 * @return
	 */
	public Boolean ExitInRecords(Integer type, String ptNo) {
		Boolean flag = true;
		if (type == 1) {
			Complex complex = this.orderCommonDao
					.findDzscInnerMergeDataComplex(ptNo, "0");
			if (complex == null) {
				flag = false;
			}

		} else if (type == 2 || type == 3) {
			Complex complex = this.orderCommonDao.findBcsInnerMergeComplex(
					ptNo, "0");
			if (complex == null) {
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * 验证相应成品的料件组成是否已备案
	 * 
	 * @param type
	 * @param ptNo
	 * @return
	 */
	public Boolean ExitInRecordsByMaterial(Integer type, String ptNo) {
		Boolean flag = true;

		List listMaterialBomDetail = this.orderCommonDao.findMaterialBomDetail(
				ptNo, type);
		if (type == 1) {

			for (int j = 0; j < listMaterialBomDetail.size(); j++) {
				MaterialBomDetailApply mbda = (MaterialBomDetailApply) listMaterialBomDetail
						.get(j);
				String mbdPtNo = mbda.getMateriel().getPtNo();
				Complex complex = this.orderCommonDao
						.findDzscInnerMergeDataComplex(mbdPtNo, "2");
				if (complex == null) {
					flag = false;
				}
			}

		} else if (type == 2 || type == 3) {
			for (int j = 0; j < listMaterialBomDetail.size(); j++) {
				MaterialBomDetail mbd = (MaterialBomDetail) listMaterialBomDetail
						.get(j);
				String mbdPtNo = mbd.getMateriel().getPtNo();
				Complex complex = this.orderCommonDao.findBcsInnerMergeComplex(
						mbdPtNo, "2");
				if (complex == null) {
					flag = false;
				}

			}

		}

		return flag;
	}

	public List orderChangetoContract(CustomOrder customOrder) {
		List list = this.orderCommonDao.findCustomOrderDetail(customOrder);
		List<CustomOrderDetail> listCustomOrderDetail = new ArrayList<CustomOrderDetail>();
		for (int i = 0; i < list.size(); i++) {
			CustomOrderDetail customOrderDetail = (CustomOrderDetail) list
					.get(i);
			Double amount = customOrderDetail.getAmount() == null ? 0.0
					: customOrderDetail.getAmount();
			Double contractNum = customOrderDetail.getContractNum() == null ? 0.0
					: customOrderDetail.getContractNum();
			customOrderDetail.setContractNum(contractNum);
			if (amount >= contractNum) {
				customOrderDetail.setNotContractNum(amount - contractNum);
			} else {
				customOrderDetail.setNotContractNum(0.0);
			}
			this.orderCommonDao.saveCustomOrderDetail(customOrderDetail);
			listCustomOrderDetail.add(customOrderDetail);
		}
		return listCustomOrderDetail;
	}

	public List orderChangetoFactory(CustomOrder customOrder) {
		List list = this.orderCommonDao.findCustomOrderDetail(customOrder);
		List<CustomOrderDetail> listCustomOrderDetail = new ArrayList<CustomOrderDetail>();
		for (int i = 0; i < list.size(); i++) {
			CustomOrderDetail customOrderDetail = (CustomOrderDetail) list
					.get(i);
			Double contractNum = customOrderDetail.getContractNum() == null ? 0.0
					: customOrderDetail.getContractNum();
			Double transNum = customOrderDetail.getTransNum() == null ? 0.0
					: customOrderDetail.getTransNum();
			customOrderDetail.setTransNum(transNum);
			if (contractNum >= transNum) {
				customOrderDetail.setNotTransNum(contractNum - transNum);
			} else {
				customOrderDetail.setNotTransNum(0.0);
			}
			this.orderCommonDao.saveCustomOrderDetail(customOrderDetail);
			listCustomOrderDetail.add(customOrderDetail);
		}
		return listCustomOrderDetail;
	}

	public void delCustomsOrderImg(CustomOrder customOrder) {
		List list = this.orderCommonDao.findCustomOrderImg(customOrder);
		for (int i = 0; i < list.size(); i++) {
			CustomOrderImg customOrderImg = (CustomOrderImg) list.get(i);
			this.orderCommonDao.delCustomsOrderImg(customOrderImg);
		}
	}

	public void delCustomsOrderBom(CustomOrder customOrder) {
		List list = this.orderCommonDao.findCustomOrderBom(customOrder);
		for (int i = 0; i < list.size(); i++) {
			CustomOrderBom customOrderBom = (CustomOrderBom) list.get(i);
			this.orderCommonDao.delCustomsOrderBom(customOrderBom);
		}
	}

	/**
	 * 查询未转合同的订单表体
	 * 
	 * @param customOrder
	 * @return
	 */
	public List findCustomOrderDetailByNotChangeToContract(List customOrder) {
		List result = new ArrayList();
		for (int k = 0; k < customOrder.size(); k++) {
			CustomOrder ap = (CustomOrder) customOrder.get(k);
			result.addAll(this.orderCommonDao
					.findCustomOrderDetailByNotChangeToContract(ap));
		}
		return result;
	}

	/**
	 * 根据料号查找其对应的报关资料
	 * 
	 * @param ptNo
	 * @return
	 */

	public List findInnerMergeDataByPtNo(String ptNo, String materieltype,
			Integer customType) {

		return this.orderCommonDao.findListBcsInnerMerge(ptNo, "0", customType);

	}

	public TempDateCheck dateCheckedList(List list, Integer customType,
			String emsNo) {
		List<CustomOrderDetail> listCustomOrderDetail = new ArrayList<CustomOrderDetail>();
		List<String> listError = new ArrayList<String>();
		TempDateCheck tempDateCheck = new TempDateCheck();
		if (customType > 1) {

			Map<String, ContractExg> contractExgMap = new HashMap<String, ContractExg>();
			List contractExgList = this.orderCommonDao.findContractExgByEmsNo(
					emsNo, customType);
			for (int i = 0; i < contractExgList.size(); i++) {
				ContractExg contractExg = (ContractExg) contractExgList.get(i);
				String complexCode = contractExg.getComplex().getCode();
				String tenName = contractExg.getName() == null ? ""
						: contractExg.getName();
				String tenSpec = contractExg.getSpec() == null ? ""
						: contractExg.getSpec();
				String unit = contractExg.getUnit().getName() == null ? ""
						: contractExg.getUnit().getName();
				String key = complexCode + "/" + tenName + "/" + tenSpec + "/"
						+ unit;
				if (contractExgMap.get(key) == null) {
					contractExgMap.put(key, contractExg);
				}

			}

			for (int i = 0; i < list.size(); i++) {
				CustomOrderDetail customOrderDetail = (CustomOrderDetail) list
						.get(i);
				String ptNo = customOrderDetail.getMateriel().getPtNo();
				BcsInnerMerge bim = this.orderCommonDao.findBcsInnerMerge(ptNo,
						"0");
				if (bim != null && bim.getBcsTenInnerMerge() != null) {
					String complexCode = bim.getBcsTenInnerMerge().getComplex()
							.getCode();
					String tenName = bim.getBcsTenInnerMerge().getName() == null ? ""
							: bim.getBcsTenInnerMerge().getName();
					String tenSpec = bim.getBcsTenInnerMerge().getSpec() == null ? ""
							: bim.getBcsTenInnerMerge().getSpec();
					String unit = bim.getBcsTenInnerMerge().getComUnit()
							.getName() == null ? "" : bim.getBcsTenInnerMerge()
							.getComUnit().getName();
					String key1 = complexCode + "/" + tenName + "/" + tenSpec
							+ "/" + unit;
					if (contractExgMap.get(key1) != null) {
						listCustomOrderDetail.add(customOrderDetail);
					} else {
						listError.add(ptNo);
					}

				}

			}
		} else if (customType == 1) {
			Map<String, DzscEmsExgBill> contractExgMap = new HashMap<String, DzscEmsExgBill>();
			List contractExgList = this.orderCommonDao.findContractExgByEmsNo(
					emsNo, customType);
			for (int i = 0; i < contractExgList.size(); i++) {
				DzscEmsExgBill contractExg = (DzscEmsExgBill) contractExgList
						.get(i);
				String complexCode = contractExg.getComplex().getCode();
				String tenName = contractExg.getName() == null ? ""
						: contractExg.getName();
				String tenSpec = contractExg.getSpec() == null ? ""
						: contractExg.getSpec();
				String unit = contractExg.getUnit().getName() == null ? ""
						: contractExg.getUnit().getName();
				String key = complexCode + "/" + tenName + "/" + tenSpec + "/"
						+ unit;
				if (contractExgMap.get(key) == null) {
					contractExgMap.put(key, contractExg);
				}

			}

			for (int i = 0; i < list.size(); i++) {
				CustomOrderDetail customOrderDetail = (CustomOrderDetail) list
						.get(i);
				String ptNo = customOrderDetail.getMateriel().getPtNo();
				DzscInnerMergeData dimd = this.orderCommonDao
						.findDzscInnerMergeData(ptNo, "0");

				if (dimd != null && dimd.getDzscTenInnerMerge() != null) {
					String complexCode = dimd.getDzscTenInnerMerge()
							.getTenComplex().getCode();
					String tenName = dimd.getDzscTenInnerMerge().getTenPtName() == null ? ""
							: dimd.getDzscTenInnerMerge().getTenPtName();
					String tenSpec = dimd.getDzscTenInnerMerge().getTenPtSpec() == null ? ""
							: dimd.getDzscTenInnerMerge().getTenPtSpec();
					String unit = dimd.getDzscTenInnerMerge().getTenUnit()
							.getName() == null ? "" : dimd
							.getDzscTenInnerMerge().getTenUnit().getName();
					String key1 = complexCode + "/" + tenName + "/" + tenSpec
							+ "/" + unit;
					if (contractExgMap.get(key1) != null) {
						listCustomOrderDetail.add(customOrderDetail);
					} else {
						listError.add(ptNo);
					}

				}

			}

		}

		tempDateCheck.setListCustomOrderDetail(listCustomOrderDetail);
		tempDateCheck.setListError(listError);

		return tempDateCheck;
	}

	/**
	 * 从订单转合同料件
	 * 
	 * @param customOrderDetail
	 * @param emsNo
	 * @param customType
	 * @param contract
	 */
	private ContractImg changeToContractImg(String emsNo, int customType,
			Contract contract, String ptNo, Double detailCount,
			Double detailTotalPrice) {
		/**
		 * 数量小数位控制
		 */
		int countBit = getCountBitFromParaSet();
		Map<Integer, ContractImg> imgCommInfo = new HashMap<Integer, ContractImg>();
		ContractImg img = null;
		List ContractImgList = this.contractDao.findContractImgByEmsNo(emsNo);
		for (int i = 0; i < ContractImgList.size(); i++) {
			ContractImg contractImg = (ContractImg) ContractImgList.get(i);
			if (imgCommInfo.get(contractImg.getSeqNum()) == null) {
				imgCommInfo.put(contractImg.getSeqNum(), contractImg);
			}
		}
		//
		// 转料件
		//
		BcsInnerMerge bimimg = this.orderCommonDao.findBcsInnerMerge(ptNo, "2");
		if (bimimg == null || bimimg.getBcsTenInnerMerge() == null) {
			throw new RuntimeException("料号[" + ptNo + "]在物料与报关对应表中不存在对应的十码资料!");
		}
		if (bimimg != null && bimimg.getBcsTenInnerMerge() != null) {
			String tenName = bimimg.getBcsTenInnerMerge().getName() == null ? ""
					: bimimg.getBcsTenInnerMerge().getName();
			String tenSpec = bimimg.getBcsTenInnerMerge().getSpec() == null ? ""
					: bimimg.getBcsTenInnerMerge().getSpec();
			Integer credenceNoImg = null;
			if (customType > 1) { // 电子化手册
				//
				// 归并序号是否在备案资料库中存在
				//
				BcsDictPorImg dictPorimg = this.orderCommonDao
						.findBcsDictPorImg(bimimg.getBcsTenInnerMerge()
								.getSeqNum());
				if (dictPorimg == null) {
					throw new RuntimeException("料号[" + ptNo + "]对应归并序号["
							+ bimimg.getBcsTenInnerMerge().getSeqNum()
							+ "]在备案资料库中不存在!");
				} else {
					credenceNoImg = dictPorimg.getSeqNum();
				}
			}
			img = imgCommInfo.get(bimimg.getBcsTenInnerMerge().getSeqNum());
			if (img == null) {
				img = new ContractImg();
				img.setContract(contract);
				img.setSeqNum(bimimg.getBcsTenInnerMerge().getSeqNum());
				if (credenceNoImg != null) {
					img.setCredenceNo(credenceNoImg);
				}
				img.setName(tenName);
				img.setSpec(tenSpec);
				img.setComplex(bimimg.getBcsTenInnerMerge().getComplex());
				img.setUnit(bimimg.getBcsTenInnerMerge().getComUnit());

				// wss:2010.05.13 料件单价栏位应该取报关商品资料中的单价(原产地,征免方式)
				img.setDeclarePrice(bimimg.getBcsTenInnerMerge().getPrice());// 单价
				img.setCountry(bimimg.getBcsTenInnerMerge().getCountry());// 原产地

				List ls = parameterCodeDao.findLevyMode("code", "3");
				if (ls != null && ls.size() != 0) {
					img.setLevyMode((LevyMode) ls.get(0));// 默认征免方式(LevyMode)
				}

				Double exportAmount = detailCount;
				img.setAmount(CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(exportAmount), countBit));
				img.setTotalPrice(CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(detailTotalPrice), countBit));
				img.setModifyMark(ModifyMarkState.ADDED);
				img.setIsMainImg(bimimg.getBcsTenInnerMerge().getIsMainImg());
				imgCommInfo.put(bimimg.getBcsTenInnerMerge().getSeqNum(), img);
				this.contractDao.saveContractImg(img);
			} else {
				System.out.println("@@@@~~~~~~");
				Double exportAmount = detailCount + img.getAmount();
				img.setAmount(CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(exportAmount), countBit));
				img.setTotalPrice(CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(img.getAmount()
								* (img.getDeclarePrice() == null ? 0.0 : img
										.getDeclarePrice())), countBit));
				if (ModifyMarkState.UNCHANGE.equals(img.getModifyMark())) {
					img.setModifyMark(ModifyMarkState.MODIFIED);
				}
				this.contractDao.saveContractImg(img);
			}

		}
		return img;
	}

	/**
	 * 写订单转合同的中间过程表
	 * 
	 * @param imgExgId
	 * @param customOrderDetail
	 */
	private void witerMakeCustomOrderToContract(String imgExgId,
			CustomOrderDetail customOrderDetail) {

		// =============写中间过程表====================
		// =============写中间过程表====================
		// System.out.print("=============imgExgid==" + imgExgId +"\n");
		MakeCustomOrderToContract make = new MakeCustomOrderToContract();
		make.setCompany(CommonUtils.getCompany());
		make.setProjectType(customOrderDetail.getCustomOrder().getCustomType());
		make.setCustomOrderDetailId(customOrderDetail.getId());
		make.setContractImgExgId(imgExgId);
		this.orderCommonDao.saveOrUpdate(make);
	}

	/**
	 * 从订单转合同BOM
	 * 
	 * @param detailCount
	 * @param emsNo
	 * @param customType
	 * @param contract
	 * @param version 指定BOM版本号
	 */
	private void changeToContractBom(String emsNo, int customType,
			ContractExg exg, Contract contract, Double detailCount, String ptNo, Integer version) {
		CustomBaseAction customBaseAction;
		// customBaseAction.f
		/**
		 * 数量小数位控制
		 */
		int countBit = getCountBitFromParaSet();
		/**
		 * 金额小数位控制
		 */
		int moneyBit = getMoneyBitFromParaSet();
		ContractBom bom = null;
		Double exportAmount = detailCount;
		Map<String, ContractBom> bomMap = new HashMap<String, ContractBom>();

		List lsBomBill = this.contractDao
				.findContractBomByContractParentId(contract.getId());
		for (int i = 0; i < lsBomBill.size(); i++) {
			ContractBom bomBill = (ContractBom) lsBomBill.get(i);
			String bomkey = (bomBill.getContractExg().getSeqNum() == null ? ""
					: bomBill.getContractExg().getSeqNum().toString())
					+ '/'
					+ (bomBill.getContractImgSeqNum() == null ? "" : bomBill
							.getContractImgSeqNum().toString());
			System.out.println("bomMapkey---old>" + bomkey);
			bomMap.put(bomkey, bomBill);
		}
		// 如果 version=null 取得最大版本
		if (version == null) {
			version = orderCommonDao.findMaterialBomDetailVersioId(ptNo);
		}
		if (version == null) {
			return;
		}
		List listMaterialBom = this.contractDao.findMaterialBomDetailByExgId(
				ptNo, version);
		for (int i = 0; i < listMaterialBom.size(); i++) {
			MaterialBomDetail materialBom = (MaterialBomDetail) listMaterialBom
					.get(i);
			if (materialBom.getMateriel() != null) {
				//
				// 把子级料件转为十码资料
				//
				BcsInnerMerge bimmaterial = this.orderCommonDao.findBcsInnerMerge(materialBom.getMateriel().getPtNo(),"2");
				if (bimmaterial != null
						&& bimmaterial.getBcsTenInnerMerge() != null
						&& bimmaterial.getMateriel() != null) {
					if (bimmaterial.getMateriel().getUnitConvert() == null) {
						throw new RuntimeException("成品货号[" + ptNo + "折BOM中的料号"
								+ materialBom.getMateriel().getPtNo()
								+ "]单位折算系数为空！ ");
					}

					Double unitConvert = bimmaterial.getMateriel()
							.getUnitConvert() == null ? 1.0 : bimmaterial
							.getMateriel().getUnitConvert();
					System.out.println("unitConvert--->" + unitConvert);
					Integer credenceNoImg = null;
					if (customType > 1) { // 电子化手册
						//
						// 归并序号是否在备案资料库中存在
						//
						BcsDictPorImg dictPorimg = this.orderCommonDao
								.findBcsDictPorImg(bimmaterial
										.getBcsTenInnerMerge().getSeqNum());
						if (dictPorimg == null) {
							System.out.println(materialBom.getMateriel().getPtNo()+"====================");
							throw new RuntimeException("成品料号["
									+ ptNo
									+"]耗用料件["+ materialBom.getMateriel().getPtNo()
									+ "]对应归并序号["
									+ bimmaterial.getBcsTenInnerMerge()
											.getSeqNum() + "]在备案资料库中不存在!");
							
						} else {
							credenceNoImg = dictPorimg.getSeqNum();
						}
					}
					String bomkey = (exg.getSeqNum() == null ? "" : exg
							.getSeqNum().toString())
							+ '/'
							+ bimmaterial.getBcsTenInnerMerge().getSeqNum();
					bom = bomMap.get(bomkey);
					System.out.println("bomMapkey--->" + bomkey);
					if (bom == null) {
						bom = new ContractBom();
						bom.setContractExg(exg);
						bom.setContractImgSeqNum(bimmaterial
								.getBcsTenInnerMerge().getSeqNum());
						if (credenceNoImg != null) {
							bom.setImgCredenceNo(credenceNoImg);
						}
						bom.setComplex(bimmaterial.getBcsTenInnerMerge()
								.getComplex());
						bom
								.setName(bimmaterial.getBcsTenInnerMerge()
										.getName());
						bom
								.setSpec(bimmaterial.getBcsTenInnerMerge()
										.getSpec());
						bom.setUnit(bimmaterial.getBcsTenInnerMerge()
								.getComUnit());
						bom
								.setUnitWaste(materialBom.getUnitWaste() == null ? 0.0
										: materialBom.getUnitWaste()
												* unitConvert);
						bom
								.setUnitDosage(materialBom.getUnitUsed() == null ? 0.0
										: materialBom.getUnitUsed()
												* unitConvert);
						Double waste = 0.0;
						if (bom.getUnitDosage() != null
								&& bom.getUnitDosage() != 0.0) {
							waste = 1 - (bom.getUnitWaste() / bom
									.getUnitDosage());
						}
						bom.setWaste(waste * 100);
						// 数量＝成品数量＊单耗
						Double amount = exportAmount
								* (bom.getUnitDosage() == null ? 0.0 : bom
										.getUnitDosage());
						bom.setAmount(CommonUtils.getDoubleByDigit(CommonUtils
								.getDoubleExceptNull(amount), countBit));
						bom.setModifyMark(ModifyMarkState.ADDED);
						bom.setDeclarePrice(bimmaterial.getBcsTenInnerMerge().getPrice());
						bom.setTotalPrice(CaleUtil.multiply(bimmaterial.getBcsTenInnerMerge().getPrice(), bom.getAmount()));
						bom.setIsMainImg(bimmaterial.getBcsTenInnerMerge().getIsMainImg());
						bom.setCountry(bimmaterial.getBcsTenInnerMerge().getCountry());
						bomMap.put(bomkey, bom);
						this.contractDao.saveContractBom(bom);
					} else {
						Double unitwasteold = CommonUtils
								.getDoubleExceptNull(bom.getUnitWaste());
						Double unitDosageold = CommonUtils
								.getDoubleExceptNull(bom.getUnitDosage());
						Double unitwaste = materialBom.getUnitWaste() == null ? 0.0
								: materialBom.getUnitWaste() * unitConvert;
						Double unitUsed = materialBom.getUnitUsed() == null ? 0.0
								: materialBom.getUnitUsed() * unitConvert;
						bom.setUnitWaste(unitwaste + unitwasteold);
						bom.setUnitDosage(unitUsed + unitDosageold);
						Double waste = 0.0;

						if (bom.getUnitDosage() != null
								&& bom.getUnitDosage() != 0.0) {
							waste = 1 - (bom.getUnitWaste() / bom
									.getUnitDosage());
						}
						bom.setWaste(waste * 100);
						Double amount = exportAmount
								* (bom.getUnitDosage() == null ? 0.0 : bom
										.getUnitDosage());
						bom.setAmount(CommonUtils.getDoubleByDigit(CommonUtils
								.getDoubleExceptNull(amount), countBit));

						this.contractDao.saveContractBom(bom);
						bomMap.put(bomkey, bom);
					}

					Double amount = this.orderCommonDao.StateContractBomAmount(
							customType, bimmaterial.getBcsTenInnerMerge()
									.getSeqNum(),  contract.getId());
					System.out.print("@@@" + amount);
					// changeToContractImg(emsNo, customType, contract,
					// materialBom.getMateriel().getPtNo(), amount, 0.0);
					changeContractBomToContractImg(contract, bimmaterial,
							emsNo, bimmaterial.getBcsTenInnerMerge()
									.getSeqNum(), amount);
				}else{
					throw new RuntimeException("成品折料后，料件料号[" + materialBom.getMateriel().getPtNo()+ "]在【物料与报关对应表】中没有或不存在【当前使用的报关资料】!");
				}

			}
		}

	}

	/**
	 * 把生成好的总数回写料件表中
	 * 
	 * @param emsNo
	 * @param seqNum
	 * @param amount
	 */
	private void changeContractBomToContractImg(Contract contract,
			BcsInnerMerge bimimg, String emsNo, Integer seqNum, Double amount) {
		/**
		 * 数量小数位控制
		 */
		int countBit = getCountBitFromParaSet();
		if (bimimg != null && bimimg.getBcsTenInnerMerge() != null) {
			String tenName = bimimg.getBcsTenInnerMerge().getName() == null ? ""
					: bimimg.getBcsTenInnerMerge().getName();
			String tenSpec = bimimg.getBcsTenInnerMerge().getSpec() == null ? ""
					: bimimg.getBcsTenInnerMerge().getSpec();
			Integer credenceNoImg = null;
			//
			// 归并序号是否在备案资料库中存在
			//
			BcsDictPorImg dictPorimg = this.orderCommonDao
					.findBcsDictPorImg(bimimg.getBcsTenInnerMerge().getSeqNum());
			if (dictPorimg == null) {
				return;
			}
			credenceNoImg = dictPorimg.getSeqNum();
			ContractImg img = null;
			List contractImgList = this.contractDao
					.findContractImgByEmsNoAndSeqNum(emsNo, seqNum);
			if (contractImgList != null && contractImgList.size() > 0) {
				img = (ContractImg) contractImgList.get(0);
				img.setAmount(CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(amount), countBit));
				img.setTotalPrice(CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(img.getAmount()
								* (img.getDeclarePrice() == null ? 0.0 : img
										.getDeclarePrice())), countBit));
				if (ModifyMarkState.UNCHANGE.equals(img.getModifyMark())) {
					img.setModifyMark(ModifyMarkState.MODIFIED);
				}
			} else {
				img = new ContractImg();
				img.setContract(contract);
				img.setSeqNum(seqNum);
				if (credenceNoImg != null) {
					img.setCredenceNo(credenceNoImg);
				}
				img.setName(tenName);
				img.setSpec(tenSpec);
				img.setComplex(bimimg.getBcsTenInnerMerge().getComplex());
				img.setUnit(bimimg.getBcsTenInnerMerge().getComUnit());

				// wss:2010.05.13 料件单价栏位应该取报关商品资料中的单价(原产地,征免方式)
				img.setDeclarePrice(bimimg.getBcsTenInnerMerge().getPrice());// 单价
				img.setCountry(bimimg.getBcsTenInnerMerge().getCountry());// 原产地

				List ls = parameterCodeDao.findLevyMode("code", "3");
				if (ls != null && ls.size() != 0) {
					img.setLevyMode((LevyMode) ls.get(0));// 默认征免方式(LevyMode)
				}
				img.setAmount(CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(amount), countBit));
				img.setTotalPrice(CaleUtil.multiply(
						CommonUtils.getDoubleExceptNull(img.getDeclarePrice()),
						CommonUtils.getDoubleExceptNull(img.getAmount())));
				img.setTotalPrice(CommonUtils.getDoubleByDigit(img.getTotalPrice(),countBit));
				img.setIsMainImg(bimimg.getBcsTenInnerMerge().getIsMainImg());
				img.setModifyMark(ModifyMarkState.ADDED);
			}
			this.contractDao.saveContractImg(img);
		}
	}

	/**
	 * 从订单转合同成品
	 * 
	 * @param customOrderDetail
	 * @param emsNo
	 * @param customType
	 * @param contract
	 */
	private ContractExg changeToContractExg(String emsNo, int customType,
			Contract contract, String ptNo, Double detailCount,
			Double detailTotalPrice,Integer version) {
		ContractExg exg = null;
		Map<Integer, ContractExg> exgCommInfo = new HashMap<Integer, ContractExg>();
		/**
		 * 数量小数位控制
		 */
		int countBit = getCountBitFromParaSet();
		/**
		 * 金额小数位控制
		 */
		int moneyBit = getMoneyBitFromParaSet();

		List contractExgList = this.orderCommonDao.findContractExgByEmsNo(
				emsNo, customType);// 相应合同下的所有出口成品资料
		for (int i = 0; i < contractExgList.size(); i++) {
			ContractExg contractExg = (ContractExg) contractExgList.get(i);
			if (exgCommInfo.get(contractExg.getSeqNum()) == null) {
				exgCommInfo.put(contractExg.getSeqNum(), contractExg);// 将出口成品资料序列号
				// 与 自身
				// 相关联
			}
		}
		// 取得十码资料
		//
		BcsInnerMerge bim = this.orderCommonDao.findBcsInnerMerge(ptNo, "0");
		if (bim == null || bim.getBcsTenInnerMerge() == null) {
			throw new RuntimeException("成品料号[" + ptNo
					+ "]在物料与报关对应表中不存在对应的十码资料!");
		}
		if (bim != null && bim.getBcsTenInnerMerge() != null) {
			String tenName = bim.getBcsTenInnerMerge().getName() == null ? ""
					: bim.getBcsTenInnerMerge().getName();
			String tenSpec = bim.getBcsTenInnerMerge().getSpec() == null ? ""
					: bim.getBcsTenInnerMerge().getSpec();
			Integer credenceNoExg = null;
			if (customType > 1) { // 电子化手册
				//
				// 归并序号是否在备案资料库中存在
				//
				BcsDictPorExg dictPorexg = this.orderCommonDao
						.findBcsDictPorExg(bim.getBcsTenInnerMerge()
								.getSeqNum());
				if (dictPorexg == null) {
					throw new RuntimeException("成品[" + ptNo + "]对应归并序号["
							+ bim.getBcsTenInnerMerge().getSeqNum()
							+ "]在备案资料库中不存在!");
				} else {
					credenceNoExg = dictPorexg.getSeqNum();
				}
			}
			exg = exgCommInfo.get(bim.getBcsTenInnerMerge().getSeqNum());
			if (exg == null) {
				exg = new ContractExg();
				exg.setContract(contract);
				exg.setSeqNum(bim.getBcsTenInnerMerge().getSeqNum());
				if (credenceNoExg != null) {
					exg.setCredenceNo(credenceNoExg);
				}
				exg.setName(tenName);
				exg.setSpec(tenSpec);
				exg.setComplex(bim.getBcsTenInnerMerge().getComplex());
				exg.setUnit(bim.getBcsTenInnerMerge().getComUnit());
				Double exportAmount = detailCount;
				exg.setExportAmount(CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(exportAmount), countBit));
				exg.setTotalPrice(CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(detailTotalPrice), moneyBit));
				// 通关手册 成品单价 = 总价/数量
				exg.setUnitPrice(CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(exg.getTotalPrice()
								/ exg.getExportAmount()), countBit));

				exg.setModifyMark(ModifyMarkState.ADDED);
				exg.setCountry(bim.getBcsTenInnerMerge().getCountry());
				exgCommInfo.put(bim.getBcsTenInnerMerge().getSeqNum(), exg);
				this.contractDao.saveContractExg(exg);
			} else {
				Double exportAmount = exg.getExportAmount() + detailCount;
				exg.setExportAmount(CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(exportAmount), countBit));
				exg
						.setTotalPrice(CommonUtils.getDoubleByDigit(
								CommonUtils.getDoubleExceptNull(exg
										.getUnitPrice() == null ? 0.0 : exg
										.getUnitPrice()
										* exportAmount), moneyBit));
				exg.setProcessTotalPrice(CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(exg
								.getProcessUnitPrice() == null ? 0.0 : exg
								.getProcessUnitPrice()
								* exportAmount), moneyBit));
				if (ModifyMarkState.UNCHANGE.equals(exg.getModifyMark())) {
					exg.setModifyMark(ModifyMarkState.MODIFIED);
				}
				this.contractDao.saveContractExg(exg);

			}

			//
			// 转合同BOM
			//
			changeToContractBom(emsNo, customType, exg, contract, exg
					.getExportAmount(), ptNo, version);

		}
		return exg;
	}

	/**
	 * 现使用的下面方法实现订单转合同
	 * 
	 * @param list
	 * @param customType
	 * @param emsNo
	 * @return
	 */
	public List ChangeToContract(List list, Integer customType, String emsNo) {
		List returnList = new ArrayList();
		// CustomOrder head = null;
		if (customType > 1) {
			Contract contract = this.orderCommonDao.findContractByEmsNo(emsNo);
			for (int i = 0; i < list.size(); i++) {
				CustomOrderDetail customOrderDetail = (CustomOrderDetail) list
						.get(i);
				String ptNo = customOrderDetail.getMateriel().getPtNo();
				Double detailCount = customOrderDetail.getNotContractNum() == null ? 0.0
						: customOrderDetail.getNotContractNum();// 未转合同数量（报关级别）
				Double detailTotalPrice = customOrderDetail.getTotalPrice() == null ? 0.0
						: customOrderDetail.getTotalPrice();// 总价

				if (customOrderDetail.getCustomOrder().getOrdertype() == 0) {
					//
					// 转成品
					//
					//
					ContractExg exg = changeToContractExg(emsNo, customType,
							contract, ptNo, detailCount, detailTotalPrice, customOrderDetail.getVersion());
					/**
					 * 写订单转合同的中间过程表
					 */
					if (exg != null) {
						witerMakeCustomOrderToContract(exg.getId(),
								customOrderDetail);
					}

				} else if (customOrderDetail.getCustomOrder().getOrdertype() == 1) {
					//
					// 转料件
					//
					ContractImg img = changeToContractImg(emsNo, customType,
							contract, ptNo, detailCount, detailTotalPrice);
					/**
					 * 写中间过程表
					 */
					if (img != null) {
						witerMakeCustomOrderToContract(img.getId(),
								customOrderDetail);
					}
				}
				customOrderDetail.setContractNum(customOrderDetail
						.getContractNum()
						+ customOrderDetail.getNotContractNum());
				customOrderDetail.setNotTransNum(customOrderDetail
						.getNotContractNum()
						+ customOrderDetail.getNotTransNum());
				customOrderDetail.setNotContractNum(0.0);
				customOrderDetail.setCopEmsNo(emsNo);// 合同内部编号
				customOrderDetail.setIfcustomer(new Boolean(true));
				this.orderCommonDao.saveCustomOrderDetail(customOrderDetail);
			}

			this.getTotalPriceBExport(contract);
			this.getTotalPriceBImport(contract);

			// ========反写订单的项号与内部编号============
			// ========反写订单的项号与内部编号============
			List<String> listId = new ArrayList<String>();
			Set<CustomOrder> set = new HashSet<CustomOrder>();
			for (int i = 0; i < list.size(); i++) {
				CustomOrderDetail t = (CustomOrderDetail) list.get(i);
				if (!listId.contains(t.getId())) {
					listId.add(t.getId());
					set.add(t.getCustomOrder());
				}
			}
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				CustomOrder head = (CustomOrder) iterator.next();
				Integer tocustomCount = this.orderCommonDao
						.getCustomOrderDetailForToContract(head);
				head.setIsChangeToContract(tocustomCount);
				// 存放通关手册的企业内部编号
				// if (!isExistBillNo(head.getCopEmsNo(), emsNo)) {
				// if (head.getCopEmsNo() != null
				// && !"".equals(head.getCopEmsNo())) {
				// head.setCopEmsNo(head.getCopEmsNo() + "," + emsNo);
				// } else {
				// head.setCopEmsNo(emsNo);
				// }
				// }
				orderCommonDao.saveCustomOrder(head);
				returnList.add(head);
			}

		} else if (customType == 1) {
			// DzscEmsPorHead dzscEmsPorHead = this.orderCommonDao
			// .findDzscEmsPorHeadByEmsNo(emsNo);
			//
			// for (int i = 0; i < list.size(); i++) {
			// CustomOrderDetail customOrderDetail = (CustomOrderDetail) list
			// .get(i);
			// String ptNo = customOrderDetail.getMateriel().getPtNo();
			// DzscInnerMergeData dimd = this.orderCommonDao
			// .findDzscInnerMergeData(ptNo, "0");
			//
			// Double detailCount = customOrderDetail.getNotContractNum() ==
			// null ? 0.0
			// : customOrderDetail.getNotContractNum();
			// if (dimd != null && dimd.getDzscTenInnerMerge() != null) {
			// String complexCode = dimd.getDzscTenInnerMerge()
			// .getTenComplex().getCode();
			// String tenName = dimd.getDzscTenInnerMerge().getTenPtName() ==
			// null ? ""
			// : dimd.getDzscTenInnerMerge().getTenPtName();
			// String tenSpec = dimd.getDzscTenInnerMerge().getTenPtSpec() ==
			// null ? ""
			// : dimd.getDzscTenInnerMerge().getTenPtSpec();
			// String unit = dimd.getDzscTenInnerMerge().getTenUnit()
			// .getName() == null ? "" : dimd
			// .getDzscTenInnerMerge().getTenUnit().getName();
			// String key1 = complexCode + "/" + tenName + "/" + tenSpec
			// + "/" + unit;
			// if (customOrderDetailMap.get(key1) != null) {
			// Double count = customOrderDetailMap.get(key1)
			// + detailCount;
			// customOrderDetailMap.put(key1, count);
			// } else {
			// customOrderDetailMap.put(key1, detailCount);
			// }
			// customOrderDetail.setContractNum(customOrderDetail
			// .getContractNum()
			// + customOrderDetail.getNotContractNum());
			// customOrderDetail.setNotTransNum(customOrderDetail
			// .getNotContractNum()
			// + customOrderDetail.getNotTransNum());
			// customOrderDetail.setNotContractNum(0.0);
			//
			// this.orderCommonDao
			// .saveCustomOrderDetail(customOrderDetail);
			//
			// }
			//
			// }
			// List contractExgList =
			// this.orderCommonDao.findContractExgByEmsNo(
			// emsNo, customType);
			//
			// for (int i = 0; i < contractExgList.size(); i++) {
			// DzscEmsExgBill contractExg = (DzscEmsExgBill) contractExgList
			// .get(i);
			// String complexCode = contractExg.getComplex().getCode();
			// String tenName = contractExg.getName() == null ? ""
			// : contractExg.getName();
			// String tenSpec = contractExg.getSpec() == null ? ""
			// : contractExg.getSpec();
			// String unit = contractExg.getUnit().getName() == null ? ""
			// : contractExg.getUnit().getName();
			// String key = complexCode + "/" + tenName + "/" + tenSpec + "/"
			// + unit;
			// if (customOrderDetailMap.get(key) != null) {
			// Double count = customOrderDetailMap.get(key);
			// Double exportAmount = contractExg.getAmount() + count;
			// contractExg.setAmount(CommonUtils.getDoubleByDigit(
			// CommonUtils.getDoubleExceptNull(exportAmount),
			// countBit));
			// contractExg.setMoney(CommonUtils.getDoubleByDigit(
			// CommonUtils.getDoubleExceptNull(contractExg
			// .getPrice()
			// * exportAmount), moneyBit));
			//
			// contractExg.setMachinMoney(CommonUtils.getDoubleByDigit(
			// CommonUtils.getDoubleExceptNull(contractExg
			// .getMachinPrice()
			// * exportAmount), moneyBit));
			//
			// this.dzscDao.saveDzscEmsExgBill(contractExg);
			//
			// List listContractBom = this.dzscDao
			// .findDzscEmsBomBill(contractExg);
			//
			// for (int j = 0; j < listContractBom.size(); j++) {
			// DzscEmsBomBill contractBom = (DzscEmsBomBill) listContractBom
			// .get(j);
			// Double amount = exportAmount
			// * (contractBom.getUnitDosage() == null ? 0.0
			// : contractBom.getUnitDosage());
			// contractBom.setAmount(CommonUtils.getDoubleByDigit(
			// CommonUtils.getDoubleExceptNull(amount),
			// countBit));
			// contractBom.setMoney(CommonUtils.getDoubleByDigit(
			// CommonUtils.getDoubleExceptNull(amount
			// * contractBom.getPrice()), moneyBit));
			// this.dzscDao.saveDzscEmsBomBill(contractBom);
			//
			// }
			//
			// }
			//
			// }
			//
			// List contractImgList = this.dzscDao
			// .findDzscEmsImgBillByEmsNoChange(emsNo);
			//
			// for (int k = 0; k < contractImgList.size(); k++) {
			// DzscEmsImgBill contractImg = (DzscEmsImgBill) contractImgList
			// .get(k);
			// Integer seqNum = contractImg.getTenSeqNum();
			// Double amount = this.orderCommonDao.StateContractBomAmount(
			// customType, seqNum, emsNo);
			//
			// contractImg.setAmount(CommonUtils.getDoubleByDigit(CommonUtils
			// .getDoubleExceptNull(amount), countBit));
			// contractImg.setMoney(CommonUtils.getDoubleByDigit(CommonUtils
			// .getDoubleExceptNull(amount * contractImg.getPrice()),
			// moneyBit));
			// this.dzscDao.saveDzscEmsImgBill(contractImg);
			// }
			//
			// this.getTotalPriceBExport(dzscEmsPorHead);
			//
			// this.getTotalPriceBImport(dzscEmsPorHead);

		}
		return returnList;
	}

	/**
	 * 判断单据号是否存在
	 * 
	 * @param allbillNo
	 *            所有的单据号
	 * @param newbillNo
	 *            新单据号
	 * @return 若存在为true 否则为false
	 */
	public boolean isExistBillNo(String allbillNo, String newbillNo) {
		if (allbillNo == null) {
			return false;
		}
		String[] yy = allbillNo.split(",");
		for (int i = 0; i < yy.length; i++) {
			if (yy[i].toString().equals(newbillNo)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 订单转合的过程中实现报关资料
	 * 
	 * @param list
	 * @param customType
	 * @param emsNo
	 * @return
	 */
	public List CustomOrderToContractByBgCommInfo(List list, Integer customType) {
		List returnList = new ArrayList();
		if (customType > 1) { // 电子化手册
			for (int i = 0; i < list.size(); i++) {
				CustomOrderDetail customOrderDetail = (CustomOrderDetail) list.get(i);
				String ptNo = customOrderDetail.getMateriel().getPtNo();
				Double detailCount = customOrderDetail.getNotContractNum() == null ? 0.0 : customOrderDetail.getNotContractNum();
				Double detailTotalPrice = customOrderDetail.getTotalPrice() == null ? 0.0 : customOrderDetail.getTotalPrice();
				if (customOrderDetail.getCustomOrder().getOrdertype() == 0) {
					//
					// 生成成品报关资料
					//
					//
					// 取得十码资料
					//
					BcsInnerMerge bim = this.orderCommonDao.findBcsInnerMerge(
							ptNo, "0");
					if (bim == null || bim.getBcsTenInnerMerge() == null) {
						throw new RuntimeException("成品料号[" + ptNo+ "]在【物料与报关对应表】中没有或不存在【当前使用的报关资料】!");
					}
					Integer version = orderCommonDao
							.findMaterialBomDetailVersioId(ptNo);
					if (bim != null && bim.getBcsTenInnerMerge() != null) {
						String tenName = bim.getBcsTenInnerMerge().getName() == null ? ""
								: bim.getBcsTenInnerMerge().getName();
						String tenSpec = bim.getBcsTenInnerMerge().getSpec() == null ? ""
								: bim.getBcsTenInnerMerge().getSpec();
						//
						// 归并序号是否在备案资料库中存在
						//
						BcsDictPorExg dictPorexg = this.orderCommonDao
								.findBcsDictPorExg(bim.getBcsTenInnerMerge()
										.getSeqNum());
						if (dictPorexg == null) {
							throw new RuntimeException("成品[" + ptNo
									+ "]对应归并序号["
									+ bim.getBcsTenInnerMerge().getSeqNum()
									+ "]在备案资料库中不存在!");
						}
						TempCustomOrderChangContract exg = new TempCustomOrderChangContract();
						exg.setSeqNum(bim.getBcsTenInnerMerge().getSeqNum());
						exg.setPtNo(ptNo);
						exg.setVersion(version);
						exg.setName(tenName);
						exg.setSpec(tenSpec);
						exg.setCode(bim.getBcsTenInnerMerge().getComplex());
						exg.setUnit(bim.getBcsTenInnerMerge().getComUnit());
						exg.setNotContractNum(detailCount);
						exg.setTotalPrice(detailTotalPrice);

						// 折算的 海关单价 = 总价/折算的报关数量
						exg.setUnitPrice(exg.getTotalPrice()
								/ exg.getNotContractNum());

						returnList.add(exg);
					}

				} else if (customOrderDetail.getCustomOrder().getOrdertype() == 1) {
					//
					// 生成料件报关资料
					//
					BcsInnerMerge bimimg = this.orderCommonDao.findBcsInnerMerge(ptNo, "2");
					if (bimimg == null || bimimg.getBcsTenInnerMerge() == null) {
						throw new RuntimeException("料件料号[" + ptNo+ "]在【物料与报关对应表】中没有或不存在【当前使用的报关资料】!");
					}
					if (bimimg != null && bimimg.getBcsTenInnerMerge() != null) {
						String tenName = bimimg.getBcsTenInnerMerge().getName() == null ? ""
								: bimimg.getBcsTenInnerMerge().getName();
						String tenSpec = bimimg.getBcsTenInnerMerge().getSpec() == null ? ""
								: bimimg.getBcsTenInnerMerge().getSpec();
						//
						// 归并序号是否在备案资料库中存在
						//
						BcsDictPorImg dictPorimg = this.orderCommonDao
								.findBcsDictPorImg(bimimg.getBcsTenInnerMerge()
										.getSeqNum());
						if (dictPorimg == null) {
							throw new RuntimeException("料号[" + ptNo
									+ "]对应归并序号["
									+ bimimg.getBcsTenInnerMerge().getSeqNum()
									+ "]在备案资料库中不存在!");
						}
						TempCustomOrderChangContract img = new TempCustomOrderChangContract();
						img.setSeqNum(bimimg.getBcsTenInnerMerge().getSeqNum());
						img.setPtNo(ptNo);
						img.setName(tenName);
						img.setSpec(tenSpec);
						img.setCode(bimimg.getBcsTenInnerMerge().getComplex());
						img.setUnit(bimimg.getBcsTenInnerMerge().getComUnit());
						img.setNotContractNum(detailCount);
						img.setTotalPrice(detailTotalPrice);
						// wss:2010.05.12修改 成品属下的料件单价栏位应该取报关商品资料中的单价
						// img.setUnitPrice(img.getTotalPrice()
						// / img.getNotContractNum());
						img.setUnitPrice(bimimg.getBcsTenInnerMerge()
								.getPrice());
						returnList.add(img);

					}
				}
			}
		} else if (customType == 1) { // 电子手册

		}
		return returnList;
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
	 * 统计出口总金额(电子手册)
	 * 
	 * @param request
	 * @param dzscEmsPorHead
	 */
	public void getTotalPriceBExport(DzscEmsPorHead dzscEmsPorHead) {

		Double exgAmount = this.dzscDao.getTotalPriceBExport(dzscEmsPorHead);
		dzscEmsPorHead.setExgAmount(exgAmount);
		this.dzscDao.saveOrUpdate(dzscEmsPorHead);

	}

	/**
	 * 统计进口总金额(电子手册)
	 * 
	 * @param request
	 * @param dzscEmsPorHead
	 */
	public void getTotalPriceBImport(DzscEmsPorHead dzscEmsPorHead) {

		Double imgAmount = this.dzscDao.getTotalPriceBImport(dzscEmsPorHead);
		dzscEmsPorHead.setImgAmount(imgAmount);
		this.dzscDao.saveOrUpdate(dzscEmsPorHead);
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
		contract.setExgAmount(CommonUtils.getDoubleByDigit(CommonUtils
				.getDoubleExceptNull(exgAmount), moneyBit));
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
		contract.setImgAmount(CommonUtils.getDoubleByDigit(CommonUtils
				.getDoubleExceptNull(imgAmount), moneyBit));
		this.contractDao.saveOrUpdate(contract);
	}

	public void ChangeToContract(List list, Integer customType, String emsNo,
			Double toContractCount) {
		/**
		 * 数量小数位控制
		 */
		int countBit = getCountBitFromParaSet();
		/**
		 * 金额小数位控制
		 */
		int moneyBit = getMoneyBitFromParaSet();
		Map<String, Double> customOrderDetailMap = new HashMap<String, Double>();
		if (customType > 1) {
			Contract contract = this.orderCommonDao.findContractByEmsNo(emsNo);
			for (int i = 0; i < list.size(); i++) {
				CustomOrderDetail customOrderDetail = (CustomOrderDetail) list
						.get(i);
				String ptNo = customOrderDetail.getMateriel().getPtNo();
				BcsInnerMerge bim = this.orderCommonDao.findBcsInnerMerge(ptNo,
						"0");
				Double detailCount = toContractCount;
				if (bim != null && bim.getBcsTenInnerMerge() != null) {
					String complexCode = bim.getBcsTenInnerMerge().getComplex()
							.getCode();
					String tenName = bim.getBcsTenInnerMerge().getName() == null ? ""
							: bim.getBcsTenInnerMerge().getName();
					String tenSpec = bim.getBcsTenInnerMerge().getSpec() == null ? ""
							: bim.getBcsTenInnerMerge().getSpec();
					String unit = bim.getBcsTenInnerMerge().getComUnit()
							.getName() == null ? "" : bim.getBcsTenInnerMerge()
							.getComUnit().getName();
					String key1 = complexCode + "/" + tenName + "/" + tenSpec
							+ "/" + unit;

					customOrderDetailMap.put(key1, detailCount);
					customOrderDetail.setContractNum(customOrderDetail
							.getContractNum()
							+ customOrderDetail.getNotContractNum());
					customOrderDetail.setNotTransNum(customOrderDetail
							.getNotContractNum()
							+ customOrderDetail.getNotTransNum());
					customOrderDetail.setNotContractNum(0.0);
					this.orderCommonDao
							.saveCustomOrderDetail(customOrderDetail);
				}
			}

			List contractExgList = this.orderCommonDao.findContractExgByEmsNo(
					emsNo, customType);
			for (int i = 0; i < contractExgList.size(); i++) {
				ContractExg contractExg = (ContractExg) contractExgList.get(i);
				String complexCode = contractExg.getComplex().getCode();
				String tenName = contractExg.getName() == null ? ""
						: contractExg.getName();
				String tenSpec = contractExg.getSpec() == null ? ""
						: contractExg.getSpec();
				String unit = contractExg.getUnit().getName() == null ? ""
						: contractExg.getUnit().getName();
				String key = complexCode + "/" + tenName + "/" + tenSpec + "/"
						+ unit;
				if (customOrderDetailMap.get(key) == null) {
					Double count = customOrderDetailMap.get(key);

					Double exportAmount = contractExg.getExportAmount() + count;
					contractExg.setExportAmount(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(exportAmount),
							countBit));
					contractExg.setTotalPrice(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(contractExg
									.getUnitPrice()
									* exportAmount), moneyBit));

					contractExg.setProcessTotalPrice(CommonUtils
							.getDoubleByDigit(CommonUtils
									.getDoubleExceptNull(contractExg
											.getProcessUnitPrice()
											* exportAmount), moneyBit));
					this.contractDao.saveContractExg(contractExg);
					List listContractBom = this.contractDao
							.findContractBomByExgId(contractExg.getId());
					for (int j = 0; j < listContractBom.size(); j++) {
						ContractBom contractBom = (ContractBom) listContractBom
								.get(j);
						Double amount = exportAmount
								* (contractBom.getUnitDosage() == null ? 0.0
										: contractBom.getUnitDosage());
						contractBom.setAmount(CommonUtils.getDoubleByDigit(
								CommonUtils.getDoubleExceptNull(amount),
								countBit));
						contractBom.setTotalPrice(CommonUtils.getDoubleByDigit(
								CommonUtils.getDoubleExceptNull(amount
										* contractBom.getDeclarePrice()),
								moneyBit));
						this.contractDao.saveContractBom(contractBom);
					}
				}

			}
			List ContractImgList = this.contractDao
					.findContractImgByEmsNo(emsNo);
			for (int k = 0; k < ContractImgList.size(); k++) {
				ContractImg contractImg = (ContractImg) ContractImgList.get(k);
				Integer seqNum = contractImg.getSeqNum();
				Double amount = this.orderCommonDao.StateContractBomAmount(
						customType, seqNum, emsNo);
				contractImg.setAmount(CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(amount), countBit));
				contractImg.setTotalPrice(CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(amount
								* contractImg.getDeclarePrice()), countBit));
				this.contractDao.saveContractImg(contractImg);
			}
			this.getTotalPriceBExport(contract);
			this.getTotalPriceBImport(contract);
		} else if (customType == 1) {
			DzscEmsPorHead dzscEmsPorHead = this.orderCommonDao
					.findDzscEmsPorHeadByEmsNo(emsNo);

			for (int i = 0; i < list.size(); i++) {
				CustomOrderDetail customOrderDetail = (CustomOrderDetail) list
						.get(i);
				String ptNo = customOrderDetail.getMateriel().getPtNo();
				DzscInnerMergeData dimd = this.orderCommonDao
						.findDzscInnerMergeData(ptNo, "0");

				Double detailCount = toContractCount;
				if (dimd != null && dimd.getDzscTenInnerMerge() != null) {
					String complexCode = dimd.getDzscTenInnerMerge()
							.getTenComplex().getCode();
					String tenName = dimd.getDzscTenInnerMerge().getTenPtName() == null ? ""
							: dimd.getDzscTenInnerMerge().getTenPtName();
					String tenSpec = dimd.getDzscTenInnerMerge().getTenPtSpec() == null ? ""
							: dimd.getDzscTenInnerMerge().getTenPtSpec();
					String unit = dimd.getDzscTenInnerMerge().getTenUnit()
							.getName() == null ? "" : dimd
							.getDzscTenInnerMerge().getTenUnit().getName();
					String key1 = complexCode + "/" + tenName + "/" + tenSpec
							+ "/" + unit;
					customOrderDetailMap.put(key1, detailCount);

					customOrderDetail.setContractNum(customOrderDetail
							.getContractNum()
							+ customOrderDetail.getNotContractNum());
					customOrderDetail.setNotTransNum(customOrderDetail
							.getNotContractNum()
							+ customOrderDetail.getNotTransNum());
					customOrderDetail.setNotContractNum(0.0);

					this.orderCommonDao
							.saveCustomOrderDetail(customOrderDetail);

				}

			}
			List contractExgList = this.orderCommonDao.findContractExgByEmsNo(
					emsNo, customType);

			for (int i = 0; i < contractExgList.size(); i++) {
				DzscEmsExgBill contractExg = (DzscEmsExgBill) contractExgList
						.get(i);
				String complexCode = contractExg.getComplex().getCode();
				String tenName = contractExg.getName() == null ? ""
						: contractExg.getName();
				String tenSpec = contractExg.getSpec() == null ? ""
						: contractExg.getSpec();
				String unit = contractExg.getUnit().getName() == null ? ""
						: contractExg.getUnit().getName();
				String key = complexCode + "/" + tenName + "/" + tenSpec + "/"
						+ unit;
				if (customOrderDetailMap.get(key) != null) {
					Double count = customOrderDetailMap.get(key);
					Double exportAmount = contractExg.getAmount() + count;
					contractExg.setAmount(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(exportAmount),
							countBit));
					contractExg.setMoney(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(contractExg
									.getPrice()
									* exportAmount), moneyBit));

					contractExg.setMachinMoney(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(contractExg
									.getMachinPrice()
									* exportAmount), moneyBit));

					this.dzscDao.saveDzscEmsExgBill(contractExg);

					List listContractBom = this.dzscDao
							.findDzscEmsBomBill(contractExg);

					for (int j = 0; j < listContractBom.size(); j++) {
						DzscEmsBomBill contractBom = (DzscEmsBomBill) listContractBom
								.get(j);
						Double amount = exportAmount
								* (contractBom.getUnitDosage() == null ? 0.0
										: contractBom.getUnitDosage());
						contractBom.setAmount(CommonUtils.getDoubleByDigit(
								CommonUtils.getDoubleExceptNull(amount),
								countBit));
						contractBom.setMoney(CommonUtils.getDoubleByDigit(
								CommonUtils.getDoubleExceptNull(amount
										* contractBom.getPrice()), moneyBit));
						this.dzscDao.saveDzscEmsBomBill(contractBom);

					}

				}

			}

			List contractImgList = this.dzscDao
					.findDzscEmsImgBillByEmsNoChange(emsNo);

			for (int k = 0; k < contractImgList.size(); k++) {
				DzscEmsImgBill contractImg = (DzscEmsImgBill) contractImgList
						.get(k);
				Integer seqNum = contractImg.getTenSeqNum();
				Double amount = this.orderCommonDao.StateContractBomAmount(
						customType, seqNum, emsNo);

				contractImg.setAmount(CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(amount), countBit));
				contractImg.setMoney(CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(amount * contractImg.getPrice()),
						moneyBit));
				this.dzscDao.saveDzscEmsImgBill(contractImg);
			}

			this.getTotalPriceBExport(dzscEmsPorHead);

			this.getTotalPriceBImport(dzscEmsPorHead);

		}
	}

	/**
	 * 分页查询订单表头信息
	 */
	public List<CustomOrder> findOrderMaster(int index, int length,
			String property, Object value, boolean isLike) {

		List list = this.orderCommonDao.findOrderMaster(index, length,
				property, value, isLike);

		return list;
	}

	/**
	 * 得到所有的订单明细
	 * 
	 * @return
	 */
	public Map<String, String> findAllCustomOrderDetail() {
		Map<String, String> map = new HashMap<String, String>();
		List<String> lsLine = new ArrayList<String>();

		List list = orderCommonDao.findAllCustomOrderDetail();
		for (int i = 0; i < list.size(); i++) {
			CustomOrderDetail detail = (CustomOrderDetail) list.get(i);
			if (detail.getCopEmsNo() == null || "".equals(detail.getCopEmsNo())) {
				continue;
			}
			if (!lsLine.contains(detail.getCustomOrder().getId() + "@"
					+ detail.getCopEmsNo())) {
				lsLine.add(detail.getCustomOrder().getId() + "@"
						+ detail.getCopEmsNo());

				String copEmsNo = map.get(detail.getCustomOrder().getId());
				if (copEmsNo != null && !"".equals(copEmsNo)) {
					copEmsNo = detail.getCopEmsNo() + "," + copEmsNo;
				} else {
					copEmsNo = detail.getCopEmsNo();
				}
				map.put(detail.getCustomOrder().getId(), copEmsNo);
			}
		}
		return map;
	}

	/**
	 * 统计是否已转合同
	 * 
	 * @param list
	 */
	public String countExsitsNotChangeContract(List list) {
		String customNo = "";
		for (int i = 0; i < list.size(); i++) {
			CustomOrder customOrder = (CustomOrder) list.get(i);
			int countIsChange = this.orderCommonDao
					.getCustomOrderDetailForToContract(customOrder);
			if (countIsChange > 0) {
				return customOrder.getBillCode();
			}
		}
		return customNo;
	}
}
