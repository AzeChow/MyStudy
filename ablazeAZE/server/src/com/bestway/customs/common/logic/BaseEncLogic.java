/*
 * Created on 2005-3-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.customs.common.logic;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
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
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclaration;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationCommInfo;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationContainer;
import com.bestway.bcs.contractexe.entity.BcsLoadBGDFromQPXml;
import com.bestway.bcs.contractexe.entity.MakeBcsCustomsDeclaration;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.countrycode.PortLin;
import com.bestway.bcus.custombase.entity.countrycode.PreDock;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.hscode.CustomsComplex;
import com.bestway.bcus.custombase.entity.parametercode.BalanceMode;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.custombase.entity.parametercode.Uses;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.bcus.enc.entity.AtcMergeAfterComInfo;
import com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.enc.entity.CustomsDeclarationCommInfo;
import com.bestway.bcus.enc.entity.CustomsDeclarationContainer;
import com.bestway.bcus.enc.entity.CustomsDeclarationDelete;
import com.bestway.bcus.enc.entity.CustomsDeclarationDeleteCommInfo;
import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.enc.entity.LoadBGDFromQPXml;
import com.bestway.bcus.enc.entity.MakeListToCustoms;
import com.bestway.bcus.enc.entity.TempCustomsDeclarationCommInfo;
import com.bestway.bcus.enc.entity.TempCustomsMessage;
import com.bestway.bcus.enc.entity.TempImExportCustomsDeclarationDelete;
import com.bestway.bcus.manualdeclare.entity.CommdityForbid;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;
import com.bestway.bcus.manualdeclare.entity.TempEmsEdiHeadH2k;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.CustomsDeclarationSet;
import com.bestway.bcus.system.entity.ParameterSet;
import com.bestway.common.CommonServerBig5GBConverter;
import com.bestway.common.CommonUtils;
import com.bestway.common.CommonUtils.LabelValue;
import com.bestway.common.MaterielType;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.constant.FecavState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.InvoiceState;
import com.bestway.common.constant.ParameterType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.common.fpt.entity.FptBillHead;
import com.bestway.common.fpt.entity.FptBillItem;
import com.bestway.common.fpt.entity.FptParameterSet;
import com.bestway.common.fpt.entity.MakeFptBillCustomsDeclaration;
import com.bestway.common.materialbase.entity.CustomsUser;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ProjectDept;
import com.bestway.common.message.entity.CspParameterSet;
import com.bestway.common.transferfactory.dao.TransferFactoryManageDao;
import com.bestway.common.transferfactory.entity.CustomsEnvelopBill;
import com.bestway.common.transferfactory.entity.TransParameterSet;
import com.bestway.common.transferfactory.entity.TransferFactoryBill;
import com.bestway.customs.common.dao.BaseEncDao;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.customs.common.entity.BaseCustomsDeclarationContainer;
import com.bestway.customs.common.entity.BaseEmsExg;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.customs.common.entity.BaseEmsImg;
import com.bestway.customs.common.entity.BaseExportInvoiceItem;
import com.bestway.customs.common.entity.BaseLoadBGDFromQPXml;
import com.bestway.customs.common.entity.ImportBGDCondition;
import com.bestway.customs.common.entity.TempLoadBGDFromQPXmlErrorInfo;
import com.bestway.dec.qp.action.DecQpAction;
import com.bestway.dec.qp.action.DecQpServiceClient;
import com.bestway.dzsc.contractexe.entity.DzscContractExeInfo;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclaration;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclarationCommInfo;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclarationContainer;
import com.bestway.dzsc.contractexe.entity.DzscLoadBGDFromQPXml;
import com.bestway.dzsc.contractexe.logic.DzscContractExeLogic;
import com.bestway.dzsc.customslist.entity.DzscCustomsBillList;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.fecav.dao.FecavDao;
import com.bestway.fecav.entity.FecavBill;
import com.bestway.fixasset.entity.DutyCertDetail;
import com.bestway.fixtureonorder.entity.FixtureContractItems;
import com.bestway.invoice.dao.InvoiceDao;
import com.bestway.invoice.entity.Invoice;

/**
 * 基础Logic
 * 
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates lm
 *         checked by 2010-08-03
 */
@SuppressWarnings("unchecked")
public abstract class BaseEncLogic {
	/** 基础DAO */
	private BaseEncDao baseEncDao;
	/** 发票DAO */
	private InvoiceDao invoiceDao;
	/** 外汇核销单DAO */
	private FecavDao fecavDao;
	/** 转厂DAO */
	private TransferFactoryManageDao transferFactoryManageDao;
	/** 存放繁转简的 Hashtable */
	private Hashtable gbHash = null;
	/** 存放结转单据的 Hashtable */
	private HashMap<String, FptBillHead> fptBillEmsNo = new HashMap<String, FptBillHead>();

	/**
	 * 获取transferFactoryManageDao
	 * 
	 * @return
	 */
	public TransferFactoryManageDao getTransferFactoryManageDao() {
		return transferFactoryManageDao;
	}

	/**
	 * 设置transferFactoryManageDao
	 * 
	 * @param transferFactoryManageDao
	 */
	public void setTransferFactoryManageDao(
			TransferFactoryManageDao transferFactoryManageDao) {
		this.transferFactoryManageDao = transferFactoryManageDao;
	}

	/**
	 * 获取baseEncDao
	 * 
	 * @return Returns the contractExeDao.
	 */
	public BaseEncDao getBaseEncDao() {
		return baseEncDao;
	}

	/**
	 * 设置baseEncDao
	 * 
	 * @param contractExeDao
	 *            The contractExeDao to set.
	 */
	public void setBaseEncDao(BaseEncDao contractExeDao) {
		this.baseEncDao = contractExeDao;
	}

	/**
	 * 获取invoiceDao
	 * 
	 * @return Returns the invoiceDao.
	 */
	public InvoiceDao getInvoiceDao() {
		return invoiceDao;
	}

	/**
	 * 设置invoiceDao
	 * 
	 * @param invoiceDao
	 *            The invoiceDao to set.
	 */
	public void setInvoiceDao(InvoiceDao invoiceDao) {
		this.invoiceDao = invoiceDao;
	}

	/**
	 * 获取fecavDao
	 * 
	 * @return fecavDao
	 */
	public FecavDao getFecavDao() {
		return fecavDao;
	}

	/**
	 * 设置fecavDao
	 * 
	 * @param fecavDao
	 */
	public void setFecavDao(FecavDao fecavDao) {
		this.fecavDao = fecavDao;
	}

	/**
	 * 检查重复报关单商品序号是否连续
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头
	 * @return boolean true表示报关单商品序号连续
	 */
	public boolean checkCustDeclCommInfoSerialNumber(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		List list = this.baseEncDao
				.findCustomsDeclarationCommInfo(baseCustomsDeclaration);
		for (int i = 0; i < list.size(); i++) {
			BaseCustomsDeclarationCommInfo commInfo = (BaseCustomsDeclarationCommInfo) list
					.get(i);
			if (commInfo.getSerialNumber() == null) {
				throw new RuntimeException("此报关单商品明细中有序号为空的商品！");
			}
			if (commInfo.getSerialNumber() != i + 1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查商品信息中项号所对应的商品的名称、规格与通关手册备案的名称、规格是否一致
	 * 
	 * @param isMaterial
	 *            料件还是成品
	 * @param baseCustomsDeclaration
	 *            报关单表头
	 * @return boolean true表示报关单商品信息中项号所对应的商品的名称、规格与通关手册备案的名称、规格一致
	 */
	public boolean checkCustDeclCommInfoSeqNumIsCorrespondsNameAndSpec(
			boolean isMaterial, BaseCustomsDeclaration baseCustomsDeclaration) {
		List list = new ArrayList();
		List listInfo = this.baseEncDao
				.findCustomsDeclarationCommInfoByEmsNo(baseCustomsDeclaration);
		// System.out.println("listInfo=" + listInfo.size());
		for (int i = 0; i < listInfo.size(); i++) {
			DzscCustomsDeclarationCommInfo dzscCommInfo = (DzscCustomsDeclarationCommInfo) listInfo
					.get(i);
			Integer seqNum = dzscCommInfo.getCommSerialNo();
			String name = (dzscCommInfo.getCommName() == null || dzscCommInfo
					.getCommName().equals("")) ? "" : dzscCommInfo
					.getCommName();
			String spec = (dzscCommInfo.getCommSpec() == null || dzscCommInfo
					.getCommSpec().equals("")) ? "" : dzscCommInfo
					.getCommSpec();
			if (isMaterial) {
				list = this.baseEncDao.findDzscMaterialInfo(
						baseCustomsDeclaration, seqNum, name, spec);
			} else {
				list = this.baseEncDao.findDzscProductInfo(
						baseCustomsDeclaration, seqNum, name, spec);
			}
			if (list == null || list.size() <= 0) {
				throw new RuntimeException("报关单明细行中项号为：["
						+ dzscCommInfo.getSerialNumber() + "]与手册通关备案的品名规格不一致！");
			}
		}
		return true;
	}

	/**
	 * 检查商品信息中项号所对应商品的法定单位（包括第二法定单位）法定单位数量是否为0
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头
	 * @return String 为提示信息
	 */
	public String[] checkCustDeclCommInfoLegalAmountBySeqNumIsZero(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		String[] message = new String[2];
		String msg = "";
		String mustCheckMsg = "";
		List listInfo = this.baseEncDao
				.findCustomsDeclarationCommInfoLegalAmountByEmsNo(baseCustomsDeclaration);
		for (int i = 0; i < listInfo.size(); i++) {
			BaseCustomsDeclarationCommInfo info = (BaseCustomsDeclarationCommInfo) listInfo
					.get(i);
			if (info.getLegalUnit() != null
					&& (info.getFirstAmount() == null || info.getFirstAmount() == 0)) {
				msg += "报关单明细行中项号为：[" + info.getSerialNumber()
						+ "]的商品第一法定数量为0！\n";
			}
			if (info.getSecondLegalUnit() != null
					&& (info.getSecondAmount() == null || info
							.getSecondAmount() == 0)) {
				msg += "报关单明细行中项号为：[" + info.getSerialNumber()
						+ "]的商品第二法定数量为0！\n";
			}
			Double commAmount = info.getCommAmount() == null ? 0.0 : info
					.getCommAmount();
			Double firstAmount = info.getFirstAmount() == null ? 0.0 : info
					.getFirstAmount();
			Double secondAmount = info.getSecondAmount() == null ? 0.0 : info
					.getSecondAmount();
			if (info.getUnit() != null && info.getLegalUnit() != null) {
				if (info.getUnit().getCode()
						.equals(info.getLegalUnit().getCode())
						&& !commAmount.equals(firstAmount)) {
					mustCheckMsg += "报关单明细行中项号为：[" + info.getSerialNumber()
							+ "]的商品申报单位与第一法定单位相同，申报数量与第一法定数量不相等！\n";
				}
			}
			if (info.getUnit() != null && info.getSecondLegalUnit() != null) {
				if (info.getUnit().getCode()
						.equals(info.getSecondLegalUnit().getCode())
						&& !commAmount.equals(secondAmount)) {
					mustCheckMsg += "报关单明细行中项号为：[" + info.getSerialNumber()
							+ "]的商品申报单位与第二法定单位相同，申报数量与第二法定数量不相等！\n";
				}
			}
		}
		message[0] = msg;
		message[1] = mustCheckMsg;
		return message;

	}

	/**
	 * 取得出口报关单
	 * 
	 * @param id
	 *            报关单ID
	 * @return BaseCustomsDeclaration返回报关单表头（用于出口收汇核销单打印）
	 */
	public BaseCustomsDeclaration findCustomsDeclarationById(String id) {
		if (id == null || id.equals("")) {
			throw new RuntimeException("没有此ID号的报关单,id号为" + id);
		}
		return this.baseEncDao.findCustomsDeclarationById(id);
	}

	/**
	 * 取得报关单明细
	 * 
	 * @param master
	 *            报关单表头
	 * @return List返回报关单明细列表（用于出口收汇核销单打印）
	 */
	public List findCustomsDeclarationInfo(BaseCustomsDeclaration master) {
		if (master.getId() == null || master.getId().equals("")) {
			throw new RuntimeException("没有此ID号的报关单,id号为" + master.getId());
		}
		return this.baseEncDao.findCustomsDeclarationInfo(master);
	}

	/**
	 * 取得报关单明细
	 * 
	 * @param baseID
	 *            表头ID
	 * @return 返回报关单明细列表
	 */
	public Map<String, List> findCustomsDeclarationInfos(List<String> baseID,
			Integer projectType) {

		List returnList = new ArrayList();
		Map<String, List> map = new HashMap<String, List>();
		List list = this.baseEncDao.findCustomsDeclarationInfos(baseID,
				projectType);
		for (int i = 0; i < list.size(); i++) {
			BaseCustomsDeclarationCommInfo info = null;
			Object obj = list.get(i);
			if (obj instanceof BaseCustomsDeclarationCommInfo) {
				info = (BaseCustomsDeclarationCommInfo) list.get(i);
			}

			if (info != null) {
				String key = info.getBaseCustomsDeclaration().getId();
				if (map.get(key) == null) {
					List infos = new ArrayList();
					infos.add(info);
					map.put(key, infos);
				} else {
					map.get(key).add(info);
				}
			}
		}
		return map;
	}

	/**
	 * 保存报关单表头信息
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 */
	public void saveCustomsDeclaration(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		// int
		// num=this.baseEncDao.findMaxSerialNumberFromDB(baseCustomsDeclaration
		// .getImpExpFlag(), baseCustomsDeclaration
		// .getEmsHeadH2k());
		// System.out.println("num="+num);
		// System.out.println("baseCustomsDeclaration.getSerialNumber()="+baseCustomsDeclaration.getSerialNumber());
		// (baseCustomsDeclaration.getId() == null || ""
		// .equals(baseCustomsDeclaration.getId().trim()))
		// && (
		if (baseCustomsDeclaration.getSerialNumber() == null) {
			baseCustomsDeclaration.setSerialNumber(this.baseEncDao
					.getCustomsDeclarationSerialNumber(
							baseCustomsDeclaration.getImpExpFlag(),
							baseCustomsDeclaration.getEmsHeadH2k()));
		}
		// System.out.println("111111111baseCustomsDeclaration.getSerialNumber()"+baseCustomsDeclaration.getSerialNumber());
		if (baseCustomsDeclaration.getCustomsDeclarationCode() != null
				&& !"".equals(baseCustomsDeclaration
						.getCustomsDeclarationCode().trim())) {
			List<BaseCustomsDeclaration> list = this.baseEncDao
					.findCustomsDeclarationByCode(baseCustomsDeclaration
							.getCustomsDeclarationCode().trim());
			for (BaseCustomsDeclaration c : list) {
				if (!c.getId().equals(baseCustomsDeclaration.getId())) {
					throw new RuntimeException("报关单号不能重复");
				}
			}
		}
		if (baseCustomsDeclaration.getPreCustomsDeclarationCode() == null
				|| "".equals(baseCustomsDeclaration
						.getPreCustomsDeclarationCode())) {
			baseCustomsDeclaration.setPreCustomsDeclarationCode(this.baseEncDao
					.getMaxPreCustomsDeclarationCode());
		} else {
			List<BaseCustomsDeclaration> list = this.baseEncDao
					.findCustomsDeclarationByPreCode(baseCustomsDeclaration
							.getPreCustomsDeclarationCode().trim());
			for (BaseCustomsDeclaration c : list) {
				if (!c.getId().equals(baseCustomsDeclaration.getId())) {
					throw new RuntimeException("报关单预录入号不能重复");
				}
			}
		}
		BaseCustomsDeclaration temp = null;
		String fecavBillCode = null;
		String invoiceCode = null;
		String envelopBillNo = null;
		Double exchangeRate = null;
		if (baseCustomsDeclaration.getId() != null
				&& !"".equals(baseCustomsDeclaration.getId())) {
			temp = this.baseEncDao
					.findCustomsDeclaration(baseCustomsDeclaration.getId());
			if (temp != null) {
				fecavBillCode = temp.getAuthorizeFile();
				exchangeRate = temp.getExchangeRate();
				invoiceCode = temp.getInvoiceCode();
				envelopBillNo = temp.getCustomsEnvelopBillNo();
			}

		}
		this.baseEncDao.saveCustomsDeclaration(baseCustomsDeclaration);
		this.writeBackDetail(exchangeRate, baseCustomsDeclaration);
		this.writeBackFecavBillWhenAdd(fecavBillCode, baseCustomsDeclaration);
		this.writeBackInvoiceWhenAdd(invoiceCode, baseCustomsDeclaration);
		this.writeBackCustomsEnvelopBillWhenAdd(envelopBillNo,
				baseCustomsDeclaration);
		this.writeBackCustomsDeclarationCodeWhenAdd(envelopBillNo,
				baseCustomsDeclaration);
		// 反写关封的转厂报关单数量
		if (baseCustomsDeclaration.getImpExpType() == 1
				|| baseCustomsDeclaration.getImpExpType() == 5) {
			List list = baseEncDao
					.findCustomsDeclarationCommInfoByIsEffective(baseCustomsDeclaration);
			BaseCustomsDeclarationCommInfo customsDeclarationCommInfo;
			for (int i = 0; i < list.size(); i++) {
				customsDeclarationCommInfo = (BaseCustomsDeclarationCommInfo) list
						.get(i);
				this.updateCustomsEnvelopCommodityInfo(
						customsDeclarationCommInfo, "EDIT");
			}
		}
	}

	/**
	 * 当修改报关单,汇率有变更时，回写明细金额
	 * 
	 * @param exchangeRate
	 *            汇率
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 */
	private void writeBackDetail(Double exchangeRate,
			BaseCustomsDeclaration baseCustomsDeclaration) {
		if (baseCustomsDeclaration.getId() != null
				&& !"".equals(baseCustomsDeclaration.getId())) { // 修改时
			//
			// 新的汇率
			//
			Double newExchangeRate = baseCustomsDeclaration.getExchangeRate() == null ? 0.0
					: baseCustomsDeclaration.getExchangeRate();
			Double oldExchangeRate = exchangeRate == null ? 0.0 : exchangeRate;
			if (newExchangeRate.doubleValue() != oldExchangeRate.doubleValue()) { // 如果汇率有变更
				//
				// 更新所有明细资料
				//
				List<BaseCustomsDeclarationCommInfo> list = this.baseEncDao
						.findCustomsDeclarationCommInfo(baseCustomsDeclaration);
				for (BaseCustomsDeclarationCommInfo b : list) {
					this.baseEncDao.saveCustomsDeclarationCommInfo(b);
				}
				// System.out.println("汇率有变更
				// ----------------------------------------------");
			}
		}
	}

	/**
	 * 当新增或修改报关单时，回写发票
	 * 
	 * @param fecavBillCode
	 *            发票号
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 */
	private void writeBackInvoiceWhenAdd(String fecavBillCode,
			BaseCustomsDeclaration baseCustomsDeclaration) {
		if (!baseCustomsDeclaration.getImpExpFlag().equals(ImpExpFlag.EXPORT)
				&& !baseCustomsDeclaration.getImpExpFlag().equals(
						ImpExpFlag.SPECIAL)) {
			return;
		}
		if (fecavBillCode != null && !"".equals(fecavBillCode)) {
			writeBackInvoiceSetNull(fecavBillCode, false);
		}
		writeBackInvoiceSetCode(baseCustomsDeclaration);
	}

	/**
	 * 回写发票报关单号为当前的报关单号
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 */
	private void writeBackInvoiceSetCode(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		String code = baseCustomsDeclaration.getInvoiceCode();
		/**
		 * 新的发票号为空不进行动作
		 */
		if (code == null || "".equals(code)) {
			return;
		}
		List list = this.invoiceDao.findInvoiceByCode(code);
		if (list.size() > 0) {
			int projectType = ProjectType.BCUS;
			if (baseCustomsDeclaration instanceof CustomsDeclaration) {
				projectType = ProjectType.BCUS;
			} else if (baseCustomsDeclaration instanceof BcsCustomsDeclaration) {
				projectType = ProjectType.BCS;
			} else if (baseCustomsDeclaration instanceof DzscCustomsDeclaration) {
				projectType = ProjectType.DZSC;
			}
			Invoice invoice = (Invoice) list.get(0);
			invoice.setCustomsDeclarationId(baseCustomsDeclaration.getId());
			invoice.setCustomsDeclaredCode(baseCustomsDeclaration
					.getCustomsDeclarationCode());
			invoice.setProjectType(projectType);
			invoice.setMoney(this.baseEncDao
					.findCustomsDeclarationMoney(baseCustomsDeclaration));
			invoice.setState(InvoiceState.USED);
			if (invoice.getUsedDate() == null) {
				invoice.setUsedDate(new Date());
			}
			this.invoiceDao.saveInvoice(invoice);
		}
	}

	/**
	 * 回写发票报关单号为空
	 * 
	 * @param fecavBillCode
	 *            发票号
	 */
	private void writeBackInvoiceSetNull(String fecavBillCode,
			boolean delectType) {
		// String code = baseCustomsDeclaration.getInvoiceCode();
		/**
		 * 旧的发票号为空不进行动作
		 */
		if (fecavBillCode == null || "".equals(fecavBillCode)) {
			return;
		}
		List list = this.invoiceDao.findInvoiceByCode(fecavBillCode);
		if (list.size() > 0) {
			Invoice invoice = (Invoice) list.get(0);
			invoice.setCustomsDeclarationId(null);
			invoice.setCustomsDeclaredCode(null);
			invoice.setMoney(null);
			invoice.setProjectType(null);
			if (delectType) {
				invoice.setState(InvoiceState.CANCELED);
			} else {
				invoice.setState(InvoiceState.DRAFT);
			}

			this.invoiceDao.saveInvoice(invoice);
		}
	}

	/**
	 * 当删除报关单时，回写发票
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 */
	private void writeBackInvoiceWhenRemove(
			BaseCustomsDeclaration baseCustomsDeclaration, boolean delectType) {
		if (!baseCustomsDeclaration.getImpExpFlag().equals(ImpExpFlag.EXPORT)
				&& !baseCustomsDeclaration.getImpExpFlag().equals(
						ImpExpFlag.SPECIAL)) {
			return;
		}
		writeBackInvoiceSetNull(baseCustomsDeclaration.getInvoiceCode(),
				delectType);
	}

	/**
	 * 删除报关单
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 * @param delectType
	 *            一般删单(false)和海关删单(true)
	 */
	public void deleteCustomsDeclaration(
			BaseCustomsDeclaration baseCustomsDeclaration, boolean delectType) {
		/**
		 * 删除商品信息
		 */
		List list = this.baseEncDao
				.findCustomsDeclarationCommInfo(baseCustomsDeclaration);
		BaseCustomsDeclarationCommInfo commInfo = null;
		for (int i = 0; i < list.size(); i++) {
			commInfo = (BaseCustomsDeclarationCommInfo) list.get(i);
			deleteCustomsDeclarationCommInfo(commInfo);
			/*
			 * this.deleteCustomsDeclarationCommInfo(commInfo); List listM =
			 * this.baseEncDao.getMaterielByCustoms(commInfo); if (listM.size()
			 * > 0) { this.baseEncDao.deleteAll(listM); }
			 */
		}
		/**
		 * 删除集装箱对象
		 */
		List containerList = this.baseEncDao
				.findContainerByCustomsDeclaration(baseCustomsDeclaration);
		for (int i = 0; i < containerList.size(); i++) {
			BaseCustomsDeclarationContainer c = (BaseCustomsDeclarationContainer) containerList
					.get(i);
			this.baseEncDao.delete(c);
		}
		/**
		 * 删除广东省出口商品发票项目
		 */
		if (baseCustomsDeclaration.getImpExpFlag().intValue() == ImpExpFlag.EXPORT
				|| baseCustomsDeclaration.getImpExpFlag().intValue() == ImpExpFlag.SPECIAL) {
			List exportInvoiceItemList = this.baseEncDao
					.findExportInvoiceItemByCustomsDeclarationId(baseCustomsDeclaration
							.getId());
			for (int i = 0; i < exportInvoiceItemList.size(); i++) {
				BaseExportInvoiceItem e = (BaseExportInvoiceItem) exportInvoiceItemList
						.get(i);
				this.baseEncDao.delete(e);
			}
		}
		this.writeBackInvoiceWhenRemove(baseCustomsDeclaration, delectType);
		this.writeBackFecavBillWhenRemove(baseCustomsDeclaration, delectType);
		this.writeBackCustomsEnvelopBillWhenRemove(baseCustomsDeclaration);

		/**
		 * 修改清单
		 */
		/*
		 * if (baseCustomsDeclaration instanceof CustomsDeclaration) { String
		 * billListno = baseCustomsDeclaration.getBillListId(); String ylno =
		 * String.valueOf(baseCustomsDeclaration .getSerialNumber());
		 * changeBillState(billListno, ylno); }
		 */

		this.writeBackCustomsBillList(baseCustomsDeclaration);
		this.baseEncDao.deleteCustomsDeclaration(baseCustomsDeclaration);
		/**
		 * 重置最大报关单流水号缓存
		 */
		BaseEncUtils.resetMaxCustomsDeclarationSerialNumber(
				getCustomType(baseCustomsDeclaration),
				baseCustomsDeclaration.getImpExpFlag(),
				baseCustomsDeclaration.getEmsHeadH2k(), baseEncDao);
	}

	/**
	 * 修改进出货转厂单据
	 */
	public void updateTransferFactoryBill(List baseCustomsDeclarations) {
		List<TransferFactoryBill> list = new ArrayList<TransferFactoryBill>();
		for (int i = 0; i < baseCustomsDeclarations.size(); i++) {
			BaseCustomsDeclaration baseCustomsDeclaration = (BaseCustomsDeclaration) baseCustomsDeclarations
					.get(i);
			List<TransferFactoryBill> transferFactoryBills = baseEncDao
					.findTransferFactoryBill(baseCustomsDeclaration);
			for (int j = 0; j < transferFactoryBills.size(); j++) {
				TransferFactoryBill transferFactoryBill = transferFactoryBills
						.get(j);
				if (transferFactoryBill != null) {
					transferFactoryBill.setIsCustomsDeclaration(false);
					list.add(transferFactoryBill);
				}
			}
		}
		this.baseEncDao.batchSaveOrUpdate(list);
	}

	/**
	 * 根据报关单表头判断模块类型，BCUS、BCS、DZSC
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 * @return Integer 模块类型
	 */
	private Integer getCustomType(BaseCustomsDeclaration baseCustomsDeclaration) {
		int projectType = ProjectType.BCUS;
		if (baseCustomsDeclaration instanceof CustomsDeclaration) {
			projectType = ProjectType.BCUS;
		} else if (baseCustomsDeclaration instanceof BcsCustomsDeclaration) {
			projectType = ProjectType.BCS;
		} else if (baseCustomsDeclaration instanceof DzscCustomsDeclaration) {
			projectType = ProjectType.DZSC;
		}
		return Integer.valueOf(projectType);
	}

	/**
	 * 获取进出口报关单删单信息
	 * 
	 * @param typeModel
	 *            项目类型:BCUS = 0;电子帐册,BCS = 1;电子化手册,DZSC = 3;电子手册
	 * @param emsNo
	 *            电子帐册号码
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 进出口报关单删单信息
	 */
	public List getImExportCustomsDeclarationDelete(int typeModel,
			String emsNo, Date beginDate, Date endDate) {
		List list = new ArrayList();
		List arrayList = new ArrayList();

		list = this.baseEncDao.findCustomsDeclarationDelete(typeModel, emsNo,
				beginDate, endDate);
		for (int i = 0; i < list.size(); i++) {
			CustomsDeclarationDelete head = (CustomsDeclarationDelete) list
					.get(i);
			List detailLs = this.baseEncDao
					.findImExportCustomsDeclarationDelete(typeModel,
							head.getId());

			if (detailLs != null && detailLs.size() != 0) {
				for (int j = 0; j < detailLs.size(); j++) {

					TempImExportCustomsDeclarationDelete temp = new TempImExportCustomsDeclarationDelete();
					temp.setBaseCustomsDeclaration(head);
					temp.setBaseCustomsDeclarationCommInfo((CustomsDeclarationDeleteCommInfo) detailLs
							.get(j));
					arrayList.add(temp);
				}
			} else {
				TempImExportCustomsDeclarationDelete temp = new TempImExportCustomsDeclarationDelete();
				temp.setBaseCustomsDeclaration(head);
				arrayList.add(temp);
			}

		}

		return arrayList;
	}

	/**
	 * 删单
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 * @param user
	 *            用户信息
	 */
	public void deleteCustomsDeclarationDelete(
			BaseCustomsDeclaration baseCustomsDeclaration, AclUser user) {
		/**
		 * 保存报关单头到删单库中
		 */
		CustomsDeclarationDelete obj = new CustomsDeclarationDelete();
		PropertyDescriptor[] pds = PropertyUtils
				.getPropertyDescriptors(baseCustomsDeclaration);
		for (int i = 0; i < pds.length; i++) {
			try {
				Object thisValue = PropertyUtils.getProperty(
						baseCustomsDeclaration, pds[i].getName());
				BeanUtils.setProperty(obj, pds[i].getName(), thisValue);
			} catch (Exception e) {
			}
		}
		try {
			Integer customsType = getCustomType(baseCustomsDeclaration);
			BeanUtils.setProperty(obj, "typeModel", customsType);
			BeanUtils.setProperty(obj, "workDate",
					CommonUtils.getBeginDate(new Date()));
			BeanUtils.setProperty(obj, "workEr", user.getLoginName());
		} catch (Exception e) {
		}
		obj.setId(null);
		this.baseEncDao.saveObject(obj);

		/**
		 * 保存商品信息到删单库中
		 */
		List list = this.baseEncDao
				.findCustomsDeclarationCommInfo(baseCustomsDeclaration);
		BaseCustomsDeclarationCommInfo commInfo = null;
		for (int i = 0; i < list.size(); i++) {
			commInfo = (BaseCustomsDeclarationCommInfo) list.get(i);
			CustomsDeclarationDeleteCommInfo info = new CustomsDeclarationDeleteCommInfo();

			PropertyDescriptor[] pds1 = PropertyUtils
					.getPropertyDescriptors(commInfo);
			for (int j = 0; j < pds1.length; j++) {
				try {
					Object thisValue = PropertyUtils.getProperty(commInfo,
							pds1[j].getName());
					BeanUtils.setProperty(info, pds1[j].getName(), thisValue);
				} catch (Exception e) {
				}
			}
			info.setBaseCustomsDeclaration(obj);// 设置报关单单头
			info.setId(null);
			this.baseEncDao.saveObject(info);
		}
		deleteCustomsDeclaration(baseCustomsDeclaration, true);
	}

	/**
	 * 当保存报关单时，回写外汇核销单
	 * 
	 * @param fecavBillCode
	 *            发票号
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 */
	private void writeBackFecavBillWhenAdd(String fecavBillCode,
			BaseCustomsDeclaration baseCustomsDeclaration) {
		if (!baseCustomsDeclaration.getImpExpFlag().equals(ImpExpFlag.EXPORT)
				&& !baseCustomsDeclaration.getImpExpFlag().equals(
						ImpExpFlag.SPECIAL)) {
			return;
		}
		if (fecavBillCode != null && !"".equals(fecavBillCode)) {
			writeBackFecavBillSetNull(fecavBillCode, false, false);
		}
		writeBackFecavBillSetCode(baseCustomsDeclaration);
	}

	/**
	 * 回写外汇核销单的报关单号为当前的报关单号
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 */
	private void writeBackFecavBillSetCode(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		String code = baseCustomsDeclaration.getAuthorizeFile();
		/**
		 * 新的核消单为空不进行动作
		 */
		if (code == null || "".equals(code)) {
			return;
		}
		FecavBill fecavBill = this.fecavDao.findFecavBill(code,
				FecavState.INNER_OBTAIN);
		if (fecavBill == null) {
			fecavBill = this.fecavDao.findFecavBill(code, FecavState.USED);
		}
		if (fecavBill != null) {
			int projectType = ProjectType.BCUS;
			if (baseCustomsDeclaration instanceof CustomsDeclaration) {
				projectType = ProjectType.BCUS;
			} else if (baseCustomsDeclaration instanceof BcsCustomsDeclaration) {
				projectType = ProjectType.BCS;
			} else if (baseCustomsDeclaration instanceof DzscCustomsDeclaration) {
				projectType = ProjectType.DZSC;
			}
			fecavBill.setBillState(FecavState.USED);
			fecavBill.setContractNo(baseCustomsDeclaration.getContract());
			// fecavBill.setCompany(CommonUtils.getCompany());
			fecavBill.setCustomsDeclarationId(baseCustomsDeclaration.getId());
			System.out.println("baseCustomsDeclaration.getId() :"
					+ baseCustomsDeclaration.getId());
			fecavBill.setCustomsDeclarationCode(baseCustomsDeclaration
					.getCustomsDeclarationCode());
			fecavBill.setDeclareDate(baseCustomsDeclaration
					.getDeclarationDate());
			// fecavBill.setExportDate(baseCustomsDeclaration
			// .getImpExpDate());
			fecavBill.setEmsNo(baseCustomsDeclaration.getEmsHeadH2k());
			fecavBill.setCurr(baseCustomsDeclaration.getCurrency());
			double totalPrice = this.fecavDao.findCustomsDeclarationMoney(
					projectType, baseCustomsDeclaration.getId());
			fecavBill.setTotalPrice(totalPrice);
			if (fecavBill.getUsedDate() == null) {
				fecavBill.setUsedDate(new Date());
			}
			if (fecavBill.getUsedMan() == null
					|| "".equals(fecavBill.getUsedMan())) {
				fecavBill.setUsedMan(CommonUtils.getRequest().getUser()
						.getLoginName());
			}
			this.fecavDao.saveFecavBill(fecavBill);
		}
	}

	/**
	 * 回写外汇核销单报关单号为空
	 * 
	 * @param fecavBillCode
	 *            发票号
	 * @param isDelete
	 *            是否删除
	 * @param delectType
	 *            一般删单或者海关删单
	 */
	private void writeBackFecavBillSetNull(String fecavBillCode,
			boolean isDelete, boolean delectType) {
		// String code = baseCustomsDeclaration.getAuthorizeFile();
		/**
		 * 新的核消单为空不进行动作
		 */
		if (fecavBillCode == null || "".equals(fecavBillCode)) {
			return;
		}
		FecavBill fecavBill = this.fecavDao.findFecavBill(fecavBillCode,
				FecavState.USED);
		if (fecavBill != null) {
			if (delectType) {
				fecavBill.setBillState(FecavState.USED);
			} else {
				fecavBill.setBillState(FecavState.INNER_OBTAIN);
			}
			fecavBill.setContractNo(null);
			fecavBill.setCustomsDeclarationId(null);
			fecavBill.setCustomsDeclarationCode(null);
			fecavBill.setDeclareDate(null);
			if (isDelete) {
				fecavBill.setExportDate(null);
			}
			fecavBill.setEmsNo(null);
			fecavBill.setCurr(null);
			fecavBill.setTotalPrice(null);
			this.fecavDao.saveFecavBill(fecavBill);
		}
	}

	/**
	 * 当删除报关单时，回写外汇核销单
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 */
	private void writeBackFecavBillWhenRemove(
			BaseCustomsDeclaration baseCustomsDeclaration, boolean delectType) {
		if (!baseCustomsDeclaration.getImpExpFlag().equals(ImpExpFlag.EXPORT)
				&& !baseCustomsDeclaration.getImpExpFlag().equals(
						ImpExpFlag.SPECIAL)) {
			return;
		}
		writeBackFecavBillSetNull(baseCustomsDeclaration.getAuthorizeFile(),
				true, delectType);
	}

	/**
	 * 当保存报关单时，回写关封号
	 * 
	 * @param fptAppNo
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 */

	private void writeBackCustomsEnvelopBillWhenAdd(String fptAppNo,
			BaseCustomsDeclaration baseCustomsDeclaration) {
		if (fptAppNo != null && !"".equals(fptAppNo.trim())) {
			writeBackCustomsEnvelopBillSetNull(fptAppNo, baseCustomsDeclaration);
		}
		String customsEnvelopBillNo = baseCustomsDeclaration
				.getCustomsEnvelopBillNo();
		if (customsEnvelopBillNo == null
				|| "".equals(customsEnvelopBillNo.trim())) {
			return;
		}
		FptAppHead fptAppHead = this.baseEncDao
				.findFptAppHeadByAppNo(customsEnvelopBillNo);
		if (fptAppHead == null) {
			return;
		}
		if (baseCustomsDeclaration.getCustomsDeclarationCode() != null
				&& !"".equals(baseCustomsDeclaration
						.getCustomsDeclarationCode().trim())) {
			// if (fptAppHead.getCarryForwardApplyToCustomsBillNo() == null
			// || "".equals(fptAppHead
			// .getCarryForwardApplyToCustomsBillNo().trim())) {
			// fptAppHead
			// .setCarryForwardApplyToCustomsBillNo(baseCustomsDeclaration
			// .getCustomsDeclarationCode());
			// } else {
			// if (fptAppHead.getCarryForwardApplyToCustomsBillNo()
			// .indexOf(
			// baseCustomsDeclaration
			// .getCustomsDeclarationCode()) < 0) {
			// fptAppHead
			// .setCarryForwardApplyToCustomsBillNo(fptAppHead
			// .getCarryForwardApplyToCustomsBillNo()
			// + baseCustomsDeclaration
			// .getCustomsDeclarationCode() + ";");
			// }
			// }
		}
		if (!customsEnvelopUsedMultiple()) {
			// customsEnvelopBill.setIsEndCase(true);
			// customsEnvelopBill.setEndCaseDate(baseCustomsDeclaration
			// .getDeclarationDate());
		}
		this.baseEncDao.saveOrUpdate(fptAppHead);
	}

	/**
	 * 当保存报关单时，回写报关单号至关封头
	 * 
	 * @param fptAppNo
	 *            关封申请单的关封号
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 */

	private void writeBackCustomsDeclarationCodeWhenAdd(
			String customsEnvelopBillNo,
			BaseCustomsDeclaration baseCustomsDeclaration) {
		if (customsEnvelopBillNo == null
				|| "".equals(customsEnvelopBillNo.trim())) {
			return;
		}
		CustomsEnvelopBill fptAppHead = this.baseEncDao
				.findCustomsEnvelopBillByCustomsDeclarationNo(customsEnvelopBillNo);
		if (fptAppHead == null) {
			return;
		}
		if (baseCustomsDeclaration.getCustomsDeclarationCode() != null
				&& !"".equals(baseCustomsDeclaration
						.getCustomsDeclarationCode().trim())) {
			if (fptAppHead.getCarryForwardApplyToCustomsBillNo() == null
					|| "".equals(fptAppHead
							.getCarryForwardApplyToCustomsBillNo().trim())) {
				fptAppHead
						.setCarryForwardApplyToCustomsBillNo(baseCustomsDeclaration
								.getCustomsDeclarationCode());
			} else {
				if (fptAppHead.getCarryForwardApplyToCustomsBillNo().indexOf(
						baseCustomsDeclaration.getCustomsDeclarationCode()) < 0) {
					fptAppHead.setCarryForwardApplyToCustomsBillNo(fptAppHead
							.getCarryForwardApplyToCustomsBillNo()
							+ "/"
							+ baseCustomsDeclaration
									.getCustomsDeclarationCode());
				}
			}
		}
		this.baseEncDao.saveOrUpdate(fptAppHead);
	}

	/**
	 * 当删除报关单时,回写关封号
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头
	 */
	private void writeBackCustomsEnvelopBillWhenRemove(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		String customsEnvelopBillNo = baseCustomsDeclaration
				.getCustomsEnvelopBillNo();
		if (customsEnvelopBillNo != null
				&& !"".equals(customsEnvelopBillNo.trim())) {
			writeBackCustomsEnvelopBillSetNull(customsEnvelopBillNo,
					baseCustomsDeclaration);
		}
	}

	/**
	 * 回写报关单关封号,并设置为NULL
	 * 
	 * @param fptAppNo
	 *            发票号
	 * @param baseCustomsDeclaration
	 *            报关单表头
	 */
	private void writeBackCustomsEnvelopBillSetNull(String fptAppNo,
			BaseCustomsDeclaration baseCustomsDeclaration) {
		// FptAppHead customsEnvelopBill = this.baseEncDao
		// .findFptAppHeadByAppNo(fptAppNo);
		// if (customsEnvelopBill != null) {
		// customsEnvelopBill.setEndCaseDate(null);
		// customsEnvelopBill.setIsEndCase(false);
		// String carryForwardApplyToCustomsBillNo = customsEnvelopBill
		// // .getCarryForwardApplyToCustomsBillNo();
		// if (carryForwardApplyToCustomsBillNo != null) {
		// carryForwardApplyToCustomsBillNo = carryForwardApplyToCustomsBillNo
		// .replace(baseCustomsDeclaration
		// .getCustomsDeclarationCode()
		// + ";", "");
		// customsEnvelopBill
		// .setCarryForwardApplyToCustomsBillNo(carryForwardApplyToCustomsBillNo);
		// }
		// this.baseEncDao.saveOrUpdate(customsEnvelopBill);
		// }
	}

	// /**
	// * 改变报关清单状态
	// *
	// * @param billListno
	// * 清单号
	// * @param ylno
	// * 报关单头流水号
	// */
	// private void changeBillState(String billListno, String ylno) {
	// List list = this.baseEncDao.findBillForbillNo(billListno);
	// if (list != null && list.size() > 0) {
	// ApplyToCustomsBillList obj = (ApplyToCustomsBillList) list.get(0);
	// obj.setCustomsDeclarationCode(deleListNo(obj
	// .getCustomsDeclarationCode(), ylno));
	// if (obj.getCustomsDeclarationCode() == null
	// || obj.getCustomsDeclarationCode().equals("")) {
	// obj.setListState(ApplyToCustomsBillList.DRAFT);
	// }
	// baseEncDao.saveApplyToCustomsBillList(obj);
	// }
	// }

	/**
	 * 判断一个关封是否使用于多个报关单
	 * 
	 * @return boolean 若TRUE 一个关封使用于多个报关单
	 */
	private boolean customsEnvelopUsedMultiple() {
		FptParameterSet parameterSet = this.baseEncDao.findTransParameterSet();
		if (parameterSet == null
				|| parameterSet.getCustomsEnvelopUsedMultiple() == null) {
			return false;
		}
		return parameterSet.getCustomsEnvelopUsedMultiple();
	}

	/**
	 * 根据报关单表头反写报关清单
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 */
	protected void writeBackCustomsBillList(
			BaseCustomsDeclaration baseCustomsDeclaration) {

		// 回写已转报关单的清单－－－电子手册
		if (baseCustomsDeclaration instanceof DzscCustomsDeclaration) {
			List list = baseEncDao
					.findDzscCustomsBillList(baseCustomsDeclaration
							.getBillListId());
			if (!list.isEmpty()) {
				DzscCustomsBillList dzscCustomsBillList = (DzscCustomsBillList) list
						.get(0);
				dzscCustomsBillList.setIsGenerateDeclaration(false);
				baseEncDao.saveObject(dzscCustomsBillList);
			}

		}

	}

	/**
	 * 反写转厂管理的结转单据
	 * 
	 * @param commInfo
	 *            报关单明细
	 */
	protected void writeBackTransFactBill(
			BaseCustomsDeclarationCommInfo commInfo) {
		// int count = this.baseEncDao
		// .findTransFactMakeCustomsDeclarationCount(commInfo);
		// if (count > 1) {
		// List list = this.baseEncDao
		// .findTransFactMakeCustomsDeclaration(commInfo);
		// for (int i = 0; i < list.size(); i++) {
		// MakeFptBillCustomsDeclaration makeCustomsDeclaration =
		// (MakeFptBillCustomsDeclaration) list.get(i);
		// List _list =
		// this.baseEncDao.getFptBillItemIdByMakeCustomsDeclarationAppNo(makeCustomsDeclaration);
		// for (int j = 0; j < _list.size(); j++) {
		// FptBillItem _item = (FptBillItem) _list.get(j);
		// this.baseEncDao .updateTransferFactoryBillCommInfo(_item.getId());
		// }
		//
		// }
		// this.baseEncDao.deleteAll(list);
		// }
		// if (count == 1) {
		// List list =
		// this.baseEncDao.findTransFactMakeCustomsDeclaration(commInfo);
		// for (int i = 0; i < list.size(); i++) {
		// MakeFptBillCustomsDeclaration makeCustomsDeclaration =
		// (MakeFptBillCustomsDeclaration) list.get(i);
		// //更新细项isCustomsDeclaration为false
		// List _list =
		// this.baseEncDao.getFptBillItemIdByMakeCustomsDeclarationAppNo(makeCustomsDeclaration);
		// for (int k = 0; k < _list.size(); k++) {
		// FptBillItem _item = (FptBillItem) _list.get(k);
		// this.baseEncDao .updateTransferFactoryBillCommInfo(_item.getId());
		// }
		// //根据中间表的headAppNo和trGno查出相应的数据放到map里更新表头的报关单流水号信息
		// List ls =
		// this.baseEncDao.findTransferFactoryBillId(makeCustomsDeclaration);
		//
		// for (int j = 0; j < ls.size(); j++) {
		// FptBillItem item = (FptBillItem) ls.get(j);
		// if (FptInOutFlag.OUT.equals(item.getBillSort())) {
		// String emsNo = item.getFptBillHead().getId();
		//
		// fptBillEmsNo.put(emsNo,item.getFptBillHead());
		// } else if (FptInOutFlag.IN.equals(item.getBillSort())) {
		// String emsNo = item.getFptBillHead().getId();;
		//
		// fptBillEmsNo.put(emsNo,item.getFptBillHead());
		// }
		// }
		// }
		// this.baseEncDao.deleteAll(list);
		// }
		Set<FptBillHead> billHeadSet = new HashSet<FptBillHead>();
		List list = this.baseEncDao
				.findTransFactMakeCustomsDeclaration(commInfo);
		for (int i = 0; i < list.size(); i++) {
			MakeFptBillCustomsDeclaration makeCustomsDeclaration = (MakeFptBillCustomsDeclaration) list
					.get(i);
			FptBillItem fptBillItem = (FptBillItem) this.baseEncDao.load(
					FptBillItem.class,
					makeCustomsDeclaration.getFptBillItemId());
			if (fptBillItem != null) {
				fptBillItem.setIsCustomsDeclaration(false);
				this.baseEncDao.saveOrUpdate(fptBillItem);
				billHeadSet.add(fptBillItem.getFptBillHead());
			}
		}
		this.baseEncDao.deleteAll(list);
		for (FptBillHead fptBillHead : billHeadSet) {
			List temp = this.baseEncDao.findFptBillItemByCustomsDeclaration(
					fptBillHead, commInfo.getBaseCustomsDeclaration());
			if (temp.isEmpty()) {
				this.writeBackMakeFptBillCustomsDeclaration(
						commInfo.getBaseCustomsDeclaration(), fptBillHead);
			}
		}
	}

	/**
	 * 回写转厂进出货单
	 * 
	 * @param customsDeclaration
	 *            报关单头
	 */
	public void writeBackMakeFptBillCustomsDeclaration(
			BaseCustomsDeclaration customsDeclaration, FptBillHead fptBillHead) {
		// String str1 = "";
		// String str2 = "";
		// String str3 = "";
		// String expimp = "";
		// if (customsDeclaration.getImpExpFlag().equals(ImpExpFlag.IMPORT)) {
		// expimp = "进";
		// } else {
		// expimp = "出";
		// }
		// String customsNo = customsDeclaration.getEmsHeadH2k() + "/" + expimp
		// + "/" + customsDeclaration.getSerialNumber() + ";";
		//
		// Iterator iter = fptBillEmsNo.entrySet().iterator();
		// while (iter.hasNext()) {
		// Map.Entry entry = (Map.Entry) iter.next();
		// FptBillHead head = (FptBillHead) entry.getValue();
		//
		// if (customsDeclaration.getEmsHeadH2k() != null &&
		// head.getOutEmsNo()!= null) {
		// String no1 = head.getMakeCustomsDeclarationCode();
		// if (no1.indexOf(customsNo) == 0) {
		// str3 = no1.substring(no1.indexOf(customsNo) +
		// customsNo.length(),no1.length());
		// } else if (no1.indexOf(customsNo) > 0) {
		// str1 = no1.substring(0, no1.indexOf(customsNo));
		// str2 = no1.substring(no1.indexOf(customsNo) +
		// customsNo.length(),no1.length());
		// str3 = str1 + str2;
		// }
		// baseEncDao.updateTransferFactoryBill(str3, head.getId());
		// }
		//
		// }
		String expimp = (customsDeclaration.getImpExpFlag().equals(
				ImpExpFlag.IMPORT) ? "进" : "出");
		String customsNo = customsDeclaration.getEmsHeadH2k() + "/" + expimp
				+ "/" + customsDeclaration.getSerialNumber() + ";";
		if (customsDeclaration.getEmsHeadH2k() != null) {// &&
															// head.getOutEmsNo()
															// != null
			String makeCustomsDeclarationCode = fptBillHead
					.getMakeCustomsDeclarationCode();
			makeCustomsDeclarationCode = makeCustomsDeclarationCode.replace(
					customsNo, "");
			fptBillHead
					.setMakeCustomsDeclarationCode(makeCustomsDeclarationCode);
			this.baseEncDao.saveOrUpdate(fptBillHead);
		}
	}

	// /**
	// * 在所有单据号中查找相应的单据号，删除单据号时用到
	// *
	// * @param allListNo
	// * 所有单据号
	// * @param listNo
	// * 单据号
	// * @return String listNo前面的所有单据号，allListNo的一个子字符串
	// */
	// private String deleListNo(String allListNo, String listNo) {
	// if (allListNo == null && allListNo.equals("")) {
	// return allListNo;
	// }
	// String[] yy = allListNo.split(",");
	// String newListNo = "";
	// for (int i = 0; i < yy.length; i++) {
	// if (i == 0) {
	// if (yy[i].equals(listNo)) {
	// break;
	// } else {
	// newListNo = yy[0];
	// }
	// } else {
	// if (yy[i].equals(listNo)) {
	// break;
	// }
	// if (newListNo.equals("")) {
	// newListNo = yy[i];
	// } else {
	// newListNo += "," + yy[i];
	// }
	// }
	// }
	// return newListNo;
	// }

	/**
	 * 转抄报关单
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 * @param isCopyComm
	 *            是否要转抄商品，true为要
	 * @return BaseCustomsDeclaration 报关单表头Base
	 */
	public BaseCustomsDeclaration copyCustomsDeclaration(
			BaseCustomsDeclaration baseCustomsDeclaration, boolean isCopyComm) {

		// String baseCustomsDeclarationId = baseCustomsDeclaration.getId();
		// BaseCustomsDeclaration tmepBaseCustomDeclaration = new
		// BaseCustomsDeclaration();

		// tmepBaseCustomDeclaration.setId(baseCustomsDeclarationId);
		BaseCustomsDeclaration b = null;
		try {
			b = baseCustomsDeclaration.getClass().cast(
					BeanUtils.cloneBean(baseCustomsDeclaration));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (b == null) {
			return null;
		}
		/**
		 * 转抄报关单
		 */
		b.setId(null);
		b.setSerialNumber(this.baseEncDao.getCustomsDeclarationSerialNumber(
				baseCustomsDeclaration.getImpExpFlag(),
				baseCustomsDeclaration.getEmsHeadH2k()));
		b.setPreCustomsDeclarationCode(null);
		b.setTempPreCode(null); // add by xxm for strix
		b.setIsSend(new Boolean(false));
		b.setEffective(false);
		b.setCancel(false);
		b.setIsCheck(false);
		b.setIsEmsCav(false);
		b.setConfirmTransferCIQ(false);
		b.setCustomsDeclarationCode(null);
		b.setCustomsDeclarationCodeinHouse(null); // add by ls 2005/9/7
		// b.setBillOfLading(null);//明门
		// b.setTradeMode(null);//明门
		b.setAuthorizeFile(null);// 外汇核销单号
		b.setAllContainerNum(null);
		b.setGrossWeight(null);
		b.setNetWeight(null);
		// b.setDeclarationDate(null);//明门

		// b.setImpExpDate(null);
		b.setBillOfLading(null);
		// b.setConveyance(null);//明门
		b.setUnificationCode(null); // add by xxm for strix
		b.setOverseasConveyanceCode(null);// 境外运输工具编号
		b.setOverseasConveyanceName(null);// 境外运输工具名字
		b.setOverseasConveyanceFlights(null);// 境外运输工具航次
		b.setOverseasConveyanceBillOfLading(null);// 境外运输工具提单号
		b.setDomesticTransferMode(null);// 境内运输方式
		b.setDomesticConveyanceCode(null);// 境内运输工具编号
		b.setDomesticConveyanceName(null);// 境内运输工具名字
		b.setDomesticConveyanceFlights(null);// 境内运输工具航次
		b.setInvoiceCode(null);// 发票号码
		b.setConveyance(null);// 运输工具(香提)
		b.setCustomsEnvelopBillNo(null);// 关封号
		b.setCreater(CommonUtils.getAclUser());// 转抄时，应该把录入者设为当前用户
		b.setImpExpDate(new Date());
		b.setDeclarationDate(new Date());
		b.setCreateDate(new Date());
		b.setTcsTaskId(null); // 集成通任务ID
		// b.setTcsEntryType(null);// 报关单类型
		// b.setTcsDeclareProperty(null);// 报关形式类型
		b.setTcsNote(null);// 协同任务备注
		b.setTcsResultMessage(null);// 集成通回执结果信息
		b.setTcsResultTime(null);// 集成通回执通知时间
		b.setTcsSendTime(null);// 报文传输时间
		b.setDeclaraCustomsBroker(null);
		b.setBrokerCorp(null);
		b.setVoyageNo(null);
		b.setEspTaskId(null);
		this.baseEncDao.saveCustomsDeclaration(b);
		if (isCopyComm) {
			/**
			 * 转抄商品信息
			 */
			List list = this.baseEncDao
					.findCustomsDeclarationCommInfo(baseCustomsDeclaration);
			BaseCustomsDeclarationCommInfo commInfo = null;
			for (int i = 0; i < list.size(); i++) {
				// commInfo = (BaseCustomsDeclarationCommInfo) list.get(i);
				BaseCustomsDeclarationCommInfo obj = (BaseCustomsDeclarationCommInfo) list
						.get(i);
				try {
					commInfo = obj.getClass().cast(BeanUtils.cloneBean(obj));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				if (commInfo == null) {
					continue;
				}
				commInfo.setId(null);
				commInfo.setCommAmount(null);
				commInfo.setFirstAmount(null);
				commInfo.setSecondAmount(null);
				commInfo.setCommTotalPrice(null);
				commInfo.setBaseCustomsDeclaration(b);
				this.baseEncDao.saveCustomsDeclarationCommInfo(commInfo);
			}
		}
		//
		// 转抄集装箱对象
		//
		// List containerList = this.baseEncDao
		// .findContainerByCustomsDeclaration(tmepBaseCustomDeclaration);
		// for (int i = 0; i < containerList.size(); i++) {
		// BaseCustomsDeclarationContainer c = (BaseCustomsDeclarationContainer)
		// containerList
		// .get(i);
		// c.setId(null);
		// c.setBaseCustomsDeclaration(b);
		// this.baseEncDao.saveCustomsDeclarationContainer(c);
		// }
		/**
		 * 转抄广东省出口商品发票项目
		 */
		if (b.getImpExpFlag().intValue() == ImpExpFlag.EXPORT
				|| b.getImpExpFlag().intValue() == ImpExpFlag.SPECIAL) {
			List exportInvoiceItemList = this.baseEncDao
					.findExportInvoiceItemByCustomsDeclarationId(baseCustomsDeclaration
							.getId());
			for (int i = 0; i < exportInvoiceItemList.size(); i++) {
				BaseExportInvoiceItem e = (BaseExportInvoiceItem) exportInvoiceItemList
						.get(i);
				BaseExportInvoiceItem invoiceItem = null;
				try {
					invoiceItem = e.getClass().cast(BeanUtils.cloneBean(e));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				if (invoiceItem == null) {
					continue;
				}
				invoiceItem.setId(null);
				invoiceItem.setBaseCustomsDeclaration(b);
				this.baseEncDao.saveExportInvoiceItem(invoiceItem);
			}
		}
		return b;
	}

	/**
	 * 取得进口报关单表头 BY 申报日期
	 * 
	 * @param emsNo
	 *            手册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return List 是CustomsDeclaration型，在有效期内与指定的手册号匹配的进口报关单表头
	 */
	public List findImportCustomsDeclaration(String emsNo, Date beginDate,
			Date endDate) {
		return this.baseEncDao.findImportCustomsDeclaration(emsNo, beginDate,
				endDate);
	}

	/**
	 * 取得进口报关单表头 BY 申报日期
	 * 
	 * @param emsHeads
	 *            手册号LIST
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return List 是CustomsDeclaration型，在有效期内与指定的手册号匹配的进口报关单表头
	 */
	public List findImportCustomsDeclaration(List emsHeads, Date beginDate,
			Date endDate) {
		List rlist = new ArrayList();
		for (int i = 0; i < emsHeads.size(); i++) {
			BaseEmsHead emsHead = (BaseEmsHead) emsHeads.get(i);
			rlist.addAll(this.baseEncDao.findImportCustomsDeclaration(
					emsHead.getEmsNo(), beginDate, endDate));
		}
		return rlist;
	}

	/**
	 * 取得出口报关单表头 BY 申报日期
	 * 
	 * @param emsNo
	 *            手册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return List 是CustomsDeclaration型，在有效期内与指定的手册号匹配的出口报关单表头
	 */
	public List findExportCustomsDeclaration(String emsNo, Date beginDate,
			Date endDate) {
		return this.baseEncDao.findExportCustomsDeclaration(emsNo, beginDate,
				endDate);
	}

	/**
	 * 取得出口报关单表头 BY 申报日期
	 * 
	 * @param baseHeads
	 *            手册号LIST
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return List 是CustomsDeclaration型，在有效期内与指定的手册号匹配的出口报关单表头
	 */
	// @AuthorityFunctionAnnotation(caption = "浏览报关单", index = 7)
	public List findExportCustomsDeclaration(List baseHeads, Date beginDate,
			Date endDate) {
		List rlist = new ArrayList();
		for (int i = 0; i < baseHeads.size(); i++) {
			BaseEmsHead bh = (BaseEmsHead) baseHeads.get(i);
			if (bh.getEmsNo() == null || bh.getEmsNo().equals("")) {
				continue;
			}
			rlist.addAll(findExportCustomsDeclaration(bh.getEmsNo(), beginDate,
					endDate));
		}
		return rlist;
	}

	/**
	 * 取得特殊报关单表头 BY 申报日期
	 * 
	 * @param emsNo
	 *            手册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return List 是CustomsDeclaration型，在有效期内与指定的手册号匹配的特殊报关单表头
	 */
	public List findSpecialCustomsDeclaration(String emsNo, Date beginDate,
			Date endDate) {
		return this.baseEncDao.findSpecialCustomsDeclaration(emsNo, beginDate,
				endDate);
	}

	/**
	 * 取得特殊报关单表头 BY 申报日期
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return List 是CustomsDeclaration型，在有效期内与指定的手册号匹配的特殊报关单表头
	 */
	public List findSpecialCustomsDeclaration(Date beginDate, Date endDate) {
		return this.baseEncDao
				.findSpecialCustomsDeclaration(beginDate, endDate);
	}

	/**
	 * 取得特殊报关单表头
	 * 
	 * @return List 是CustomsDeclaration型，特殊报关单表头
	 */
	public List findSpecialCustomsDeclaration() {
		return this.baseEncDao.findSpecialCustomsDeclaration();
	}

	/**
	 * 取得进口报关单表头
	 * 
	 * @return List 是CustomsDeclaration型，进口报关单表头
	 */
	public List findImportCustomsDeclaration() {
		return this.baseEncDao.findImportCustomsDeclaration();
	}

	/**
	 * 得到要申报的进口报关单表头，已检查但未生效的报关单表头
	 * 
	 * @return List 是TempCustomsMessage型，存放还没被选择的报关单表头信息
	 */
	public List getImportDataSource() {
		List<BaseCustomsDeclaration> tempList = baseEncDao
				.findCheckedImportCustomsDeclaration();
		BaseCustomsDeclaration b = null;
		for (int i = tempList.size() - 1; i > -1; i--) {
			b = tempList.get(i);
			if (Boolean.TRUE.equals(b.getEffective())) {
				tempList.remove(b);
			}
		}

		List newList = new ArrayList();
		for (int j = 0; j < tempList.size(); j++) {
			TempCustomsMessage temp = new TempCustomsMessage();
			temp.setCustomsDeclaration((BaseCustomsDeclaration) tempList.get(j));
			temp.setIsSelected(new Boolean(false));
			newList.add(temp);
		}
		return newList;
	}

	/**
	 * 得到要申报的出口报关单表头，已检查但未生效的报关单表头
	 * 
	 * @return List 是TempCustomsMessage型，存放还没被选择的报关单表头信息
	 */
	public List getExportDataSource() {
		List<BaseCustomsDeclaration> tempList = baseEncDao
				.findCheckedExportCustomsDeclaration();
		BaseCustomsDeclaration b = null;
		for (int i = tempList.size() - 1; i > -1; i--) {
			b = tempList.get(i);
			if (Boolean.TRUE.equals(b.getEffective())) {
				tempList.remove(b);
			}
		}

		List newList = new ArrayList();
		for (int j = 0; j < tempList.size(); j++) {
			TempCustomsMessage temp = new TempCustomsMessage();
			temp.setCustomsDeclaration((BaseCustomsDeclaration) tempList.get(j));
			temp.setIsSelected(new Boolean(false));
			newList.add(temp);
		}
		return newList;
	}

	/**
	 * 得到要申报的特殊报关单表头，已检查但未生效的报关单表头
	 * 
	 * @return List 是TempCustomsMessage型，存放还没被选择的报关单表头信息
	 */
	public List getSpexportDataSource() {
		List tempList = baseEncDao.findSpecialCustomsDeclaration();
		for (int i = tempList.size() - 1; i >= 0; i--) {
			if (((BaseCustomsDeclaration) tempList.get(i)).getIsCheck() == null
					|| ((BaseCustomsDeclaration) tempList.get(i)).getIsCheck()
							.booleanValue() == false
					|| (((BaseCustomsDeclaration) tempList.get(i))
							.getEffective() != null && ((BaseCustomsDeclaration) tempList
							.get(i)).getEffective().booleanValue() == true)) {
				tempList.remove(i);
			}
		}

		List newList = new ArrayList();
		for (int j = 0; j < tempList.size(); j++) {
			TempCustomsMessage temp = new TempCustomsMessage();
			temp.setCustomsDeclaration((BaseCustomsDeclaration) tempList.get(j));
			temp.setIsSelected(new Boolean(false));
			newList.add(temp);
		}
		return newList;
	}

	/**
	 * 取得出口报关单表头
	 * 
	 * @return List 是CustomsDeclaration型，出口报关单表头
	 */
	public List findExportCustomsDeclaration() {
		return this.baseEncDao.findExportCustomsDeclaration();
	}

	/**
	 * 保存报关单商品信息
	 * 
	 * @param customsDeclarationCommInfo
	 *            报关单物料信息
	 */
	public void saveCustomsDeclarationCommInfo(
			BaseCustomsDeclarationCommInfo customsDeclarationCommInfo) {
		if (customsDeclarationCommInfo.getId() == null
				|| "".equals(customsDeclarationCommInfo.getId())) {
			customsDeclarationCommInfo
					.setSerialNumber(this
							.getCustomsDeclarationCommInfoSerialNumber(customsDeclarationCommInfo
									.getBaseCustomsDeclaration()));
		}
		// // 反写关封转厂报关单数量
		// updateCustomsEnvelopCommodityInfo(customsDeclarationCommInfo,
		// "EDIT");
		this.baseEncDao
				.saveCustomsDeclarationCommInfo(customsDeclarationCommInfo);
	}

	public List findIsSpecification() {
		return baseEncDao.findIsSpecification();
	}

	/**
	 * 修改转厂数据时更新关封的转厂报关单数量
	 * 
	 * @param customsDeclarationCommInfo
	 *            报关单物料信息
	 * @param dataState
	 *            类型,修改为:"EDIT",删除为:"DELETE"
	 */
	public void updateCustomsEnvelopCommodityInfo(
			BaseCustomsDeclarationCommInfo customsDeclarationCommInfo,
			String dataState) {
		// if (customsDeclarationCommInfo == null) {
		// return;
		// }
		// CustomsEnvelopCommodityInfo customsEnvelopCommodityInfo = baseEncDao
		// .getCustomsEnvelopCommodityInfo(customsDeclarationCommInfo);
		// if (customsEnvelopCommodityInfo == null)
		// return;
		// BaseCustomsDeclarationCommInfo CommInfo =
		// (BaseCustomsDeclarationCommInfo) baseEncDao
		// .getCustomsDeclarationCommInfo(customsDeclarationCommInfo);
		// if (dataState.toLowerCase().equals("EDIT".toLowerCase())) {
		// if (CommInfo == null) {
		// customsEnvelopCommodityInfo
		// .setTransferQuantity((customsEnvelopCommodityInfo
		// .getTransferQuantity() == null ? 0.0
		// : customsEnvelopCommodityInfo
		// .getTransferQuantity())
		// + (customsDeclarationCommInfo.getCommAmount() == null ? 0.0
		// : customsDeclarationCommInfo
		// .getCommAmount()));
		//
		// } else {
		// if (customsEnvelopCommodityInfo.getTransferQuantity() != null
		// && customsEnvelopCommodityInfo.getTransferQuantity() == 0) {
		// customsEnvelopCommodityInfo
		// .setTransferQuantity((customsDeclarationCommInfo
		// .getCommAmount() == null ? 0.0
		// : customsDeclarationCommInfo
		// .getCommAmount()));
		// } else {
		// customsEnvelopCommodityInfo
		// .setTransferQuantity((customsEnvelopCommodityInfo
		// .getTransferQuantity() == null ? 0.0
		// : customsEnvelopCommodityInfo
		// .getTransferQuantity())
		// - (CommInfo.getCommAmount() == null ? 0.0
		// : CommInfo.getCommAmount())
		// + (customsDeclarationCommInfo
		// .getCommAmount() == null ? 0.0
		// : customsDeclarationCommInfo
		// .getCommAmount()));
		// }
		// }
		// } else if (dataState.toLowerCase().equals("DELETE".toLowerCase())) {
		// customsEnvelopCommodityInfo
		// .setTransferQuantity((customsEnvelopCommodityInfo
		// .getTransferQuantity() == null ? 0.0
		// : customsEnvelopCommodityInfo.getTransferQuantity())
		// - (customsDeclarationCommInfo.getCommAmount() == null ? 0.0
		// : customsDeclarationCommInfo
		// .getCommAmount()));
		// System.out
		// .println("customsDeclarationCommInfo.getCommAmount()---------Dele---------"
		// + customsDeclarationCommInfo.getCommAmount());
		// }
		// System.out
		// .println("customsEnvelopCommodityInfo.getTransferQuantity()------------------"
		// + customsEnvelopCommodityInfo.getTransferQuantity());
		// this.baseEncDao.saveOrUpdate(customsEnvelopCommodityInfo);
	}

	/**
	 * 取得报关单商品临时信息
	 * 
	 * @param isMaterial
	 *            料件成品判断，true代表料件，false代表成品
	 * @param customsDeclaration
	 *            报关单表头
	 * @return List 报关单商品临时信息
	 */
	public abstract List getTempCustomsDeclarationCommInfo(boolean isMaterial,
			BaseCustomsDeclaration customsDeclaration);

	/**
	 * 查找其它参数设置资料信息
	 * 
	 * @param type
	 *            进出口类型
	 * @return CustomsDeclarationSet 其它参数设置资料信息
	 */
	public CustomsDeclarationSet findCustomsSet(Integer type) {
		return this.baseEncDao.findCustomsSet(type);
	}

	/**
	 * 取得报关单商品临时信息(如果在报关单中已存在的话，将其过滤)
	 * 
	 * @param isMaterial
	 *            料件成品判断，true代表料件，false代表成品
	 * @param customsDeclaration
	 *            报关单表头
	 * @return List报关单商品临时信息
	 */
	public abstract List getTempCustomsDeclarationCommInfo(boolean isMaterial,
			BaseCustomsDeclaration customsDeclaration, String sfield,
			Object svalues);

	/**
	 * 保存报关单商品信息
	 * 
	 * @param isMaterial
	 *            料件成品判断，true代表料件，false代表成品
	 * @param tempCommInfoList
	 *            是List型
	 * @param customsDeclaration
	 *            报关单表头
	 */
	public abstract void saveCustomsDeclarationCommInfo(boolean isMaterial,
			List tempCommInfoList, BaseCustomsDeclaration customsDeclaration);

	/**
	 * 查询结转报关单已经与海关单据对应
	 * 
	 * @param baseCustomsDeclarations
	 *            报关单头List
	 * @return List 为MakeBillCorrespondingInfoByMateriel或
	 *         MakeBillCorrespondingInfoByProduct类型
	 */
	public List findMakeBillCorrespondingInfoBase(List baseCustomsDeclarations) {
		for (int i = 0; i < baseCustomsDeclarations.size(); i++) {
			BaseCustomsDeclaration baseCustomsDeclaration = (BaseCustomsDeclaration) baseCustomsDeclarations
					.get(i);
			List list = this.baseEncDao
					.findCustomsDeclarationCommInfo(baseCustomsDeclaration);
			BaseCustomsDeclarationCommInfo commInfo = null;
			for (int m = 0; m < list.size(); m++) {
				commInfo = (BaseCustomsDeclarationCommInfo) list.get(m);
				return this.baseEncDao.findMakeBillCorrespondingInfoBase(
						commInfo.getId(), commInfo.getBaseCustomsDeclaration()
								.getImpExpType());
			}
		}
		return null;
	}

	/**
	 * 删除报关单商品信息
	 * 
	 * @param commInfo
	 *            报关单商品信息
	 */
	public void deleteCustomsDeclarationCommInfo(
			BaseCustomsDeclarationCommInfo commInfo) {

		// 是否通过深加工结转单生成的报关单并作了对应
		List ls = baseEncDao.findMakeBillCorrespondingInfoBase(
				commInfo.getId(), commInfo.getBaseCustomsDeclaration()
						.getImpExpType());
		if (ls != null && ls.size() > 0) {
			throw new RuntimeException("结转报关单已经与海关账结转单据作了对应,不能删除!");
		}

		if (commInfo instanceof CustomsDeclarationCommInfo) {
			List list = this.baseEncDao
					.findMakeListToCustomsByCommInfo(commInfo.getId());
			// 修改清单状态及删除清单转报关单中间表
			for (int i = 0; i < list.size(); i++) {
				MakeListToCustoms make = (MakeListToCustoms) list.get(i);
				AtcMergeAfterComInfo afterinfo = make.getAtcMergeAfterComInfo();
				afterinfo.setIsTransferCustomsBill(new Boolean(false));// 归并后改为未转换
				this.baseEncDao.saveAtcMergeAfterComInfo(afterinfo);
				ApplyToCustomsBillList billList = afterinfo.getBillList();
				// billList.setListState(ApplyToCustomsBillList.DRAFT);// 草稿
				billList.setCustomsDeclarationCode("");
				billList.setEffectstate(false);
				this.baseEncDao.saveApplyToCustomsBillList(billList);
			}
			if (list.size() > 0) {
				this.baseEncDao.deleteAll(list);
			}
		} else if (commInfo instanceof BcsCustomsDeclarationCommInfo) {
			List list = this.baseEncDao
					.findMakeBcsCustomsDeclarationByCommInfo(commInfo.getId());
			List tempList = new ArrayList();
			for (int i = 0; i < list.size(); i++) {
				MakeBcsCustomsDeclaration make = (MakeBcsCustomsDeclaration) list
						.get(i);
				ImpExpCommodityInfo impExpCommodityInfo = make
						.getImpExpCommodityInfo();
				impExpCommodityInfo.setIsTransferCustomsBill(false);
				ImpExpRequestBill impExpRequestBill = impExpCommodityInfo
						.getImpExpRequestBill();
				impExpRequestBill.setIsCustomsBill(false);
				impExpRequestBill.setToCustomCount(impExpRequestBill
						.getToCustomCount() - 1);
				String serialNumber = String.valueOf(commInfo
						.getBaseCustomsDeclaration().getSerialNumber());
				String contractNo = commInfo.getBaseCustomsDeclaration()
						.getContract();

				String[] value = getExistBillAndContractNo(
						impExpRequestBill.getAllBillNo(),
						impExpRequestBill.getContractNo(), serialNumber,
						contractNo);

				impExpRequestBill.setAllBillNo(value[0]);

				impExpRequestBill.setContractNo(value[1]);

				tempList.add(impExpCommodityInfo);
				this.baseEncDao.saveImpExpRequestBill(impExpRequestBill);
			}
			if (tempList.size() > 0) {
				this.baseEncDao.saveImpExpCommodityInfo(tempList);
			}
			if (list.size() > 0) {
				this.baseEncDao.deleteAll(list);
			}
		}

		// 删除结转单据转报关单
		this.writeBackTransFactBill(commInfo);

		// 删除内部商品转换中间表
		List list = this.baseEncDao.getMaterielByCustoms(commInfo);
		if (list.size() > 0) {
			this.baseEncDao.deleteAll(list);
		}

		// // 反写关封转厂报关数量
		// updateCustomsEnvelopCommodityInfo(commInfo, "DELETE");

		// 删除报关单
		this.baseEncDao.deleteCustomsDeclarationCommInfo(commInfo);
	}

	/**
	 * 判断申清单中的已转报关单与回合同号
	 * 
	 * @param allbillNo
	 * @param contractNo
	 * @param bcsSerailNum
	 * @param bcsContractNo
	 * @return
	 */
	public String[] getExistBillAndContractNo(String allbillNo,
			String contractNo, String bcsSerailNum, String bcsContractNo) {
		Map mapTegther = new HashMap();
		String[] returnString = new String[2];

		if (allbillNo == null || "".equals(allbillNo) && contractNo == null
				|| "".equals(contractNo)) {
			returnString[0] = allbillNo;
			returnString[1] = contractNo;
			return returnString;
		}

		String[] stringBillNo = allbillNo.split(",");
		String[] stringContractNo = contractNo.split(",");

		if (stringBillNo.length == 0 && stringContractNo.length == 0) {
			mapTegther.put(stringBillNo[0] + "@" + stringContractNo[0],
					stringBillNo[0] + "@" + stringContractNo[0]);
		}
		if (stringBillNo.length == stringContractNo.length) {
			for (int i = 0; i < stringBillNo.length; i++) {
				mapTegther.put(stringBillNo[i] + "@" + stringContractNo[i],
						stringBillNo[i] + "@" + stringContractNo[i]);
			}
		}
		if (mapTegther == null || mapTegther.size() <= 0) {
			if ((allbillNo + "@" + contractNo).equals(bcsSerailNum + "@"
					+ bcsContractNo)) {
				returnString[0] = "";
				returnString[1] = "";

			} else {
				returnString[0] = allbillNo;
				returnString[1] = contractNo;
			}
			return returnString;
		}
		String str1 = "";
		String str2 = "";
		Iterator it = mapTegther.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			// System.out.print("==========key=="+key +"\n");
			if (!key.equals(bcsSerailNum + "@" + bcsContractNo)) {
				str1 = str1
						+ ","
						+ value.toString().substring(0,
								value.toString().indexOf("@"));
				str2 = str2
						+ ","
						+ value.toString().substring(
								value.toString().indexOf("@") + 1,
								value.toString().length());
			}

		}
		System.out.print("====str1======" + str1 + "====str2==" + str2 + "\n");
		str1 = "".equals(str1) ? "" : str1.substring(1, str1.length());
		str2 = "".equals(str2) ? "" : str2.substring(1, str2.length());
		returnString[0] = str1;
		returnString[1] = str2;

		return returnString;
	}

	// /**
	// * 判断申清单中的已转报关单与回合同号
	// *
	// * @param allbillNo
	// * @param newbillNo
	// * @return
	// */
	// public String getExistBillNo(String allbillNo, String newbillNo) {
	// if (allbillNo == null) {
	// return "";
	// }
	// String returnString = "";
	// String[] yy = allbillNo.split(",");
	// if (yy.length == 0) {
	// if (yy[0].toString().equals(newbillNo)) {
	// return "";
	// } else {
	// return allbillNo;
	// }
	// }
	// for (int i = 0; i < yy.length; i++) {
	// if (yy[i].toString().equals(newbillNo)) {
	// continue;
	// }
	// returnString = yy[i] + "," + returnString;
	// }
	// returnString = returnString.substring(0, returnString.length() - 1);
	// System.out.print("====returnString======" + returnString + "\n");
	// return returnString;
	// }

	/**
	 * 删除报关单商品信息
	 * 
	 * @param commInfoList
	 *            是List型，报关单商品信息
	 */
	public void deleteCustomsDeclarationCommInfo(List commInfoList) {
		BaseCustomsDeclarationCommInfo commInfo = null;
		for (int i = 0; i < commInfoList.size(); i++) {
			commInfo = (BaseCustomsDeclarationCommInfo) commInfoList.get(i);
			this.deleteCustomsDeclarationCommInfo(commInfo);
		}
	}

	/**
	 * 取得报关单商品信息
	 * 
	 * @param customsDeclaration
	 *            报关单表头
	 * @return List 是CustomsDeclarationCommInfo型，报关单商品信息
	 */
	public List findCustomsDeclarationCommInfo(
			BaseCustomsDeclaration customsDeclaration) {
		List list = new ArrayList();
		list = this.baseEncDao
				.findCustomsDeclarationCommInfo(customsDeclaration);
		return list;
	}

	/**
	 * 根据报关单查询报关单集装箱
	 * 
	 * @param customsDeclaration
	 *            报关单表头
	 * @return List 是CustomsDeclarationContainer型，报关单集装箱
	 */
	public List findContainerByCustomsDeclaration(
			BaseCustomsDeclaration customsDeclaration) {
		return this.baseEncDao
				.findContainerByCustomsDeclaration(customsDeclaration);
	}

	/**
	 * 查询报关行
	 * 
	 */
	public List findcustomsbrokerList() {
		return this.baseEncDao.findcustomsbrokerList();
	}

	/**
	 * 排序报关单商品信息后保存
	 * 
	 * @param list
	 *            报关单商品信息
	 */
	public void saveCustomsInfo(Vector list) {
		for (int i = 0; i < list.size(); i++) {
			BaseCustomsDeclarationCommInfo commInfo = (BaseCustomsDeclarationCommInfo) list
					.get(i);
			baseEncDao.saveCustomsDeclarationCommInfo(commInfo);
		}
	}

	/**
	 * 保存报关清单归并前商品信息
	 * 
	 * @param list
	 *            报关清单归并前商品信息
	 */
	public void saveAtcMergeBeforeComInfo(Vector list) {
		for (int i = 0; i < list.size(); i++) {
			AtcMergeBeforeComInfo commInfo = (AtcMergeBeforeComInfo) list
					.get(i);
			baseEncDao.saveAtcMergeBeforeComInfo(commInfo);
		}
	}

	/**
	 * 取得报关单商品流水号
	 * 
	 * @param customsDeclaration
	 *            报关单表头
	 * @return Integer 报关单商品流水号
	 */
	public Integer getCustomsDeclarationCommInfoSerialNumber(
			BaseCustomsDeclaration customsDeclaration) {
		return this.baseEncDao
				.getCustomsDeclarationCommInfoSerialNumber(customsDeclaration
						.getId());
	}

	/**
	 * 是否已经有报关单引用了外汇销销单号（jbcus,dzba,bcs)
	 * 
	 * @param authorizeFile
	 *            批准文号
	 * @param id
	 *            报关单表头Id
	 * @return List 是CustomsDeclaration型，报关单表头信息
	 */
	public List findExistAuthorizeFile(String authorizeFile, String id) {
		List returnList = new ArrayList();
		returnList.addAll(this.baseEncDao.isExistAuthorizeFile(
				ProjectType.BCUS, authorizeFile, id));
		returnList.addAll(this.baseEncDao.isExistAuthorizeFile(ProjectType.BCS,
				authorizeFile, id));
		return returnList;
	}

	/**
	 * 是否已经有报关单引用了外汇销销单号（jbcus,dzsc,bcs)
	 * 
	 * @param id
	 *            报关单表头Id
	 * @return BaseCustomsDeclaration 报关单表头Base
	 */
	public BaseCustomsDeclaration findAllCustomsDeclarationById(String id) {
		BaseCustomsDeclaration b = this.baseEncDao.findCustomsDeclaration(
				ProjectType.BCUS, id);
		if (b != null) {
			return b;
		}
		b = this.baseEncDao.findCustomsDeclaration(ProjectType.DZSC, id);
		if (b != null) {
			return b;
		}
		b = this.baseEncDao.findCustomsDeclaration(ProjectType.BCS, id);
		if (b != null) {
			return b;
		}
		return b;
	}

	/**
	 * 生效报关单
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 * @return BaseCustomsDeclaration 报关单表头
	 */
	public BaseCustomsDeclaration effectCustomsDeclaration(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		baseCustomsDeclaration = (BaseCustomsDeclaration) this.baseEncDao.load(
				baseCustomsDeclaration.getClass(),
				baseCustomsDeclaration.getId());
		if (baseCustomsDeclaration == null) {
			throw new RuntimeException("当前报关单已被删除，不能生效");
		}
		baseCustomsDeclaration.setEffective(new Boolean(true));
		baseCustomsDeclaration.setEffectiveDate(new Date());
		baseCustomsDeclaration.setEffectiveMan(CommonUtils.getRequest()
				.getUser().getUserName());
		this.saveCustomsDeclaration(baseCustomsDeclaration);
		// 关封自动结案
		TransParameterSet transParameterSet = transferFactoryManageDao
				.findTransParameterSet();
		if (transParameterSet != null && transParameterSet.getIsAutoJieAn()) {// 自动结案
			transferFactoryManageDao.autoJieAn(baseCustomsDeclaration);
		}
		return baseCustomsDeclaration;
	}

	/**
	 * 生效报关单
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 */
	public void resetWriteBackInvoiceSetCode(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		writeBackInvoiceSetCode(baseCustomsDeclaration);
	}

	/**
	 * 回卷报关单
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 */
	public BaseCustomsDeclaration unreelCustomsDeclaration(
			BaseCustomsDeclaration baseCustomsDeclaration) {

		// 查找当前报关单表头
		baseCustomsDeclaration = (BaseCustomsDeclaration) this.baseEncDao.load(
				baseCustomsDeclaration.getClass(),
				baseCustomsDeclaration.getId());

		// 没有报关单 抛出运行异常提示
		if (baseCustomsDeclaration == null) {

			throw new RuntimeException("当前报关单已被删除，不能回卷");

		}

		// 设置 为 未生效
		baseCustomsDeclaration.setEffective(new Boolean(false));

		// 设置生效日期为空
		baseCustomsDeclaration.setEffectiveDate(null);

		// 设置生效人为空
		baseCustomsDeclaration.setEffectiveMan(null);

		// 保存
		this.baseEncDao.saveCustomsDeclaration(baseCustomsDeclaration);

		/**
		 * 回卷时删除进口核销单
		 */
		this.writeBackStrikeImpCustomsDeclarationWhenUnreel(baseCustomsDeclaration);

		// 反写关封的转厂报关单数量
		if (baseCustomsDeclaration.getImpExpType() == 1
				|| baseCustomsDeclaration.getImpExpType() == 5) {

			// 查询 当前报关单 的所有商品信息
			List list = baseEncDao
					.findCustomsDeclarationCommInfoByNoEffective(baseCustomsDeclaration);

			BaseCustomsDeclarationCommInfo customsDeclarationCommInfo;

			for (int i = 0; i < list.size(); i++) {

				customsDeclarationCommInfo = (BaseCustomsDeclarationCommInfo) list
						.get(i);

				this.updateCustomsEnvelopCommodityInfo(
						customsDeclarationCommInfo, "DELETE");

			}
		}

		// 反写关封自动结案
		TransParameterSet transParameterSet = transferFactoryManageDao
				.findTransParameterSet();

		if (transParameterSet != null && transParameterSet.getIsAutoJieAn()) {// 自动结案
			transferFactoryManageDao.unAutoJieAn(baseCustomsDeclaration);
		}
		return baseCustomsDeclaration;
	}

	/**
	 * 回卷时删除进口核销单
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 */
	private void writeBackStrikeImpCustomsDeclarationWhenUnreel(
			BaseCustomsDeclaration baseCustomsDeclaration) {

		if (baseCustomsDeclaration == null
				|| baseCustomsDeclaration.getImpExpType() == null) {
			return;
		}

		if (ImpExpType.GENERAL_TRADE_IMPORT == baseCustomsDeclaration
				.getImpExpType()
				|| ImpExpType.DIRECT_IMPORT == baseCustomsDeclaration
						.getImpExpType()
				|| ImpExpType.TRANSFER_FACTORY_IMPORT == baseCustomsDeclaration
						.getImpExpType()) {

			this.fecavDao.deleteImpCustomsDeclaration(baseCustomsDeclaration
					.getId());
		}
	}

	/**
	 * 把相应的数据变为BigDecimal，并返回指定的位数
	 * 
	 * @param amount
	 *            要改变的数据
	 * @param bigD
	 *            要返回的位数
	 * @return String 变更后的数据
	 */
	public static String formatBig(Object amount, int bigD) {
		if (amount == null) {
			amount = "0";
		}
		BigDecimal bd = new BigDecimal(amount.toString());
		String amountStr = bd.setScale(bigD, BigDecimal.ROUND_HALF_UP)
				.toString();
		return amountStr;
	}

	/**
	 * 去除字符串中的空格
	 * 
	 * @param s
	 * @return
	 * @author sxk
	 */
	public static String removeSpaces(String s) {
		StringTokenizer st = new StringTokenizer(s, " ", false);
		String t = "";
		while (st.hasMoreElements()) {
			t += st.nextElement();
		}
		return t;
	}

	/**
	 * 去除字符串中重复
	 * 
	 * @param str
	 * @return
	 * @author sxk
	 */
	public static String toDeleteRepeat(String str) {
		String[] tokens = str.split("");
		int k = tokens.length;
		for (int i = 0; i < tokens.length; i++) {
			for (int j = 0; j < tokens.length; j++) {
				if (tokens[i] != null && tokens[i].equals(tokens[j]) && i != j) {
					tokens[j] = null;
					k--;
				}
			}
		}
		StringBuffer resstr = new StringBuffer(50);
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i] != null) {
				resstr.append(tokens[i]).append("");
			}
		}
		return resstr.toString();
	}

	/**
	 * 保存报关单头的随附单据
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头
	 */
	public void getAttachedBill(BaseCustomsDeclaration baseCustomsDeclaration) {
		System.out.println("进出口类型:" + baseCustomsDeclaration.getImpExpFlag());
		List list = baseEncDao.getAttachedBill(baseCustomsDeclaration);
		String AttachedBill = "";
		String temp = "";
		// SXK修改..判断是否进出..某商品许可证代码为AB，
		// 在进口报关单中，带出随附单据为A，出口带出随附单据为B。
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null && !list.get(i).equals("")) {
				System.out.println("list.get(i)=" + list.get(i));
				if (baseCustomsDeclaration.getImpExpFlag() == 0
						&& list.get(i).toString().contains("B")) {
					temp = list.get(i).toString().replaceAll("B", " ");
					AttachedBill += removeSpaces(temp);
				} else if (baseCustomsDeclaration.getImpExpFlag() == 1
						&& list.get(i).toString().contains("A")) {
					temp = list.get(i).toString().replaceAll("A", " ");
					AttachedBill += removeSpaces(temp);
				} else {
					AttachedBill += list.get(i);
				}
			}
		}
		baseCustomsDeclaration.setAttachedBill(toDeleteRepeat(AttachedBill));
		baseEncDao.saveCustomsDeclaration(baseCustomsDeclaration);
	}

	/**
	 * 通过内部料号找到帐册信息
	 * 
	 * @param emsNo
	 *            手册号
	 * @param ptNo
	 *            料号
	 * @param type
	 *            物料类型
	 * @param materielVersion
	 *            电子帐册历版本
	 * @return List 是EmsHeadH2kImg或EmsHeadH2kExg型，电子帐册料件或成品
	 */
	public List findBillByMaterielPtNo(String emsNo, String ptNo, String type,
			String materielVersion) {
		List listForbid = new ArrayList();
		List listForbid1 = new ArrayList();
		List rlist = new ArrayList();// 返回数据
		List<Integer> seqNums = this.baseEncDao.findAllSeqNumByPtNo(ptNo, type);// 十码序号
		if (seqNums != null && seqNums.size() > 0) {
			for (Integer seqNum : seqNums) {
				// System.out.println("序号："+seqNum);
				if (type.equals(MaterielType.MATERIEL)) {
					listForbid = this.baseEncDao
							.findCommdityForbid(MaterielType.MATERIEL);
					List list = this.baseEncDao.findImgExgBillBySeqNum(seqNum,
							type, emsNo);
					List tempList = new ArrayList();
					for (int j = 0; j < list.size(); j++) {
						EmsHeadH2kImg img = (EmsHeadH2kImg) list.get(j);
						TempEmsEdiHeadH2k obj = new TempEmsEdiHeadH2k();
						obj.setIsSelected(new Boolean(true));
						obj.setEmsHeadH2kImg(img);
						tempList.add(obj);
					}
					// -------------------------------------------------
					List maoForbid = new ArrayList();
					for (int i = 0; i < listForbid.size(); i++) {
						CommdityForbid f = (CommdityForbid) listForbid.get(i);
						if (f == null || f.getSeqNum() == null) {
							continue;
						}
						String key = f.getSeqNum().toString()
								+ (f.getVersion() == null ? "" : f.getVersion()
										.trim());
						maoForbid.add(key);
					}

					for (int i = 0; i < tempList.size(); i++) {
						TempEmsEdiHeadH2k info = (TempEmsEdiHeadH2k) tempList
								.get(i);
						if (info.getEmsHeadH2kImg() != null) {
							String key = info.getEmsHeadH2kImg().getSeqNum() == null ? ""
									: info.getEmsHeadH2kImg().getSeqNum()
											.toString();
							if (!maoForbid.contains(key)) {
								rlist.add(info);
							} else {
								System.out.println("商品已禁用：" + key);
							}
						} else if (info.getEmsHeadH2kExg() != null) {
							String key = (info.getEmsHeadH2kExg().getSeqNum() == null ? ""
									: info.getEmsHeadH2kExg().getSeqNum()
											.toString())
									+ (info.getEmsHeadH2kExg().getVersion() == null ? ""
											: info.getEmsHeadH2kExg()
													.getVersion().toString());
							if (!maoForbid.contains(key)) {
								rlist.add(info);
							} else {
								System.out.println("商品已禁用：" + key);
							}
						}
					}
				} else {
					System.out.println("成品===============1");
					listForbid1 = this.baseEncDao
							.findCommdityForbid(MaterielType.FINISHED_PRODUCT);
					// 成品要获取多个版本号
					List list = this.baseEncDao.findImgExgBillBySeqNum(seqNum,
							type, emsNo);
					List tempList = new ArrayList();
					// System.out.println("size="+list.size());
					if (list != null && list.size() > 0) {
						EmsHeadH2kExg exg = (EmsHeadH2kExg) list.get(0);
						List versionList = this.baseEncDao
								.findEmsHeadH2kVersionByExgSeqNum(exg);
						// System.out.println("版本size="+versionList.size());
						for (int i = 0; i < versionList.size(); i++) {
							EmsHeadH2kExg copyexg = (EmsHeadH2kExg) exg.clone();
							EmsHeadH2kVersion v = (EmsHeadH2kVersion) versionList
									.get(i);
							copyexg.setVersion(v.getVersion());

							TempEmsEdiHeadH2k obj = new TempEmsEdiHeadH2k();
							if (materielVersion != null
									&& materielVersion.equals(v.getVersion()
											.toString())) {
								obj.setIsSelected(new Boolean(true));
							} else {
								obj.setIsSelected(new Boolean(false));
							}
							obj.setEmsHeadH2kExg(copyexg);
							tempList.add(obj);
						}
					}
					// -------------------------------------------------
					List maoForbid = new ArrayList();
					for (int i = 0; i < listForbid1.size(); i++) {
						CommdityForbid f = (CommdityForbid) listForbid1.get(i);
						if (f == null || f.getSeqNum() == null) {
							continue;
						}
						String key = f.getSeqNum().toString()
								+ "/"
								+ (f.getVersion() == null ? "" : f.getVersion()
										.trim());
						maoForbid.add(key);
					}
					// System.out.println("tempListsize="+tempList.size());
					for (int i = 0; i < tempList.size(); i++) {
						TempEmsEdiHeadH2k info = (TempEmsEdiHeadH2k) tempList
								.get(i);
						if (info.getEmsHeadH2kImg() != null) {
							String key = info.getEmsHeadH2kImg().getSeqNum() == null ? ""
									: info.getEmsHeadH2kImg().getSeqNum()
											.toString();
							if (!maoForbid.contains(key)) {
								rlist.add(info);
							} else {
								System.out.println("商品已禁用：" + key);
							}
						} else if (info.getEmsHeadH2kExg() != null) {
							String key = (info.getEmsHeadH2kExg().getSeqNum() == null ? ""
									: info.getEmsHeadH2kExg().getSeqNum()
											.toString())
									+ "/"
									+ (info.getEmsHeadH2kExg().getVersion() == null ? ""
											: info.getEmsHeadH2kExg()
													.getVersion().toString());
							if (!maoForbid.contains(key)) {
								rlist.add(info);
							} else {
								System.out.println("商品已禁用：" + key);
							}
						}
					}
				}
			}
			return rlist;
		} else {
			return new Vector();
		}
	}

	/**
	 * 通过内部件号找到帐册信息
	 * 
	 * @param emsNo
	 *            手册号
	 * @param ptNo
	 *            料号
	 * @param type
	 *            物料类型
	 * @param materielVersion
	 *            电子帐册历版本
	 * @return List 是EmsHeadH2kImg或EmsHeadH2kExg型，电子帐册料件或成品
	 */
	public List findBillByMaterielPtNo2(String emsNo, String ptNo, String type,
			String materielVersion) {
		List listForbid = new ArrayList();
		List listForbid1 = new ArrayList();
		List rlist = new ArrayList();// 返回数据
		List<Integer> seqNums = this.baseEncDao.findAllSeqNumByPtNo(ptNo, type);// 十码序号
		if (seqNums != null && seqNums.size() > 0) {
			for (Integer seqNum : seqNums) {
				if (type.equals(MaterielType.MATERIEL)) {
					List tempList = new ArrayList();
					listForbid = this.baseEncDao
							.findCommdityForbid(MaterielType.MATERIEL);
					List list = this.baseEncDao.findImgExgBillBySeqNum(seqNum,
							type, emsNo);
					for (int j = 0; j < list.size(); j++) {
						EmsHeadH2kImg img = (EmsHeadH2kImg) list.get(j);
						TempEmsEdiHeadH2k obj = new TempEmsEdiHeadH2k();
						obj.setIsSelected(new Boolean(true));
						obj.setEmsHeadH2kImg(img);
						tempList.add(obj);
					}
					// -------------------------------------------------
					List maoForbid = new ArrayList();
					for (int i = 0; i < listForbid.size(); i++) {
						CommdityForbid f = (CommdityForbid) listForbid.get(i);
						if (f == null || f.getSeqNum() == null) {
							continue;
						}
						String key = f.getSeqNum().toString()
								+ (f.getVersion() == null ? "" : f.getVersion()
										.trim());
						maoForbid.add(key);
					}

					for (int i = 0; i < tempList.size(); i++) {
						TempEmsEdiHeadH2k info = (TempEmsEdiHeadH2k) tempList
								.get(i);
						if (info.getEmsHeadH2kImg() != null) {
							String key = info.getEmsHeadH2kImg().getSeqNum() == null ? ""
									: info.getEmsHeadH2kImg().getSeqNum()
											.toString();
							if (maoForbid.contains(key)) {
								rlist.add(key);
								System.out.println("商品已禁用：" + key);
							}
						} else if (info.getEmsHeadH2kExg() != null) {
							String key = (info.getEmsHeadH2kExg().getSeqNum() == null ? ""
									: info.getEmsHeadH2kExg().getSeqNum()
											.toString())
									+ (info.getEmsHeadH2kExg().getVersion() == null ? ""
											: info.getEmsHeadH2kExg()
													.getVersion().toString());
							if (maoForbid.contains(key)) {
								rlist.add(key);
								System.out.println("商品已禁用：" + key);
							}
						}
					}
				} else {
					System.out.println("成品------------2");
					List tempList = new ArrayList();
					listForbid1 = this.baseEncDao
							.findCommdityForbid(MaterielType.FINISHED_PRODUCT);
					// 成品要获取多个版本号
					List list = this.baseEncDao.findImgExgBillBySeqNum(seqNum,
							type, emsNo);
					if (list != null && list.size() > 0) {
						EmsHeadH2kExg exg = (EmsHeadH2kExg) list.get(0);
						List versionList = this.baseEncDao
								.findEmsHeadH2kVersionByExgSeqNum(exg);
						for (int i = 0; i < versionList.size(); i++) {
							EmsHeadH2kExg copyexg = (EmsHeadH2kExg) exg.clone();
							EmsHeadH2kVersion v = (EmsHeadH2kVersion) versionList
									.get(i);
							copyexg.setVersion(v.getVersion());

							TempEmsEdiHeadH2k obj = new TempEmsEdiHeadH2k();
							if (materielVersion != null
									&& materielVersion.equals(v.getVersion()
											.toString())) {
								obj.setIsSelected(new Boolean(true));
							} else {
								obj.setIsSelected(new Boolean(false));
							}
							obj.setEmsHeadH2kExg(copyexg);
							tempList.add(obj);
						}
					}
					// -------------------------------------------------
					List maoForbid = new ArrayList();
					for (int i = 0; i < listForbid1.size(); i++) {
						CommdityForbid f = (CommdityForbid) listForbid1.get(i);
						if (f == null || f.getSeqNum() == null) {
							continue;
						}
						String key = f.getSeqNum().toString()
								+ (f.getVersion() == null ? "" : f.getVersion()
										.trim());
						maoForbid.add(key);
					}
					for (int i = 0; i < tempList.size(); i++) {
						TempEmsEdiHeadH2k info = (TempEmsEdiHeadH2k) tempList
								.get(i);
						if (info.getEmsHeadH2kImg() != null) {
							String key = info.getEmsHeadH2kImg().getSeqNum() == null ? ""
									: info.getEmsHeadH2kImg().getSeqNum()
											.toString();
							if (maoForbid.contains(key)) {
								rlist.add(key);
								System.out.println("商品已禁用：" + key);
							}
						} else if (info.getEmsHeadH2kExg() != null) {
							String key = (info.getEmsHeadH2kExg().getSeqNum() == null ? ""
									: info.getEmsHeadH2kExg().getSeqNum()
											.toString())
									+ (info.getEmsHeadH2kExg().getVersion() == null ? ""
											: info.getEmsHeadH2kExg()
													.getVersion().toString());
							if (maoForbid.contains(key)) {
								rlist.add(key);
								System.out.println("商品已禁用：" + key);
							}
						}
					}
				}
			}
			return rlist;
		} else {
			return new Vector();
		}
	}

	/**
	 * 计算申请单物料的仓库总毛重、仓库总净重
	 * 
	 * @param list
	 *            是ImpExpCommodityInfo型，申请单表头
	 * @return Materiel 报关常用物料
	 */
	public Materiel getNetGossWeight(List list) {
		Materiel m = new Materiel();
		Double netweight = Double.valueOf(0);
		Double gossweight = Double.valueOf(0);
		for (int i = 0; i < list.size(); i++) {
			ImpExpCommodityInfo info = (ImpExpCommodityInfo) list.get(i);
			if (info.getMateriel() != null
					&& info.getMateriel().getCalWeightUnit() != null) {
				if (info.getMateriel().getCalWeightUnit().getName().equals("克")) {
					netweight = netweight
							+ (fd(info.getMateriel().getCknetWeight()) * 0.001);
					gossweight = gossweight
							+ (fd(info.getMateriel().getCkoutWeight()) * 0.001);
				} else {
					netweight = netweight
							+ fd(info.getMateriel().getCknetWeight());
					gossweight = gossweight
							+ fd(info.getMateriel().getCkoutWeight());
				}
			}
		}
		m.setCknetWeight(netweight);
		m.setCkoutWeight(gossweight);
		return m;
	}

	/**
	 * 当d不为null时返回d，否则返回0
	 * 
	 * @param d
	 *            Double数据
	 * @return Double 处理后返回的数据
	 */
	private Double fd(Double d) {
		if (d != null) {
			return d;
		}
		return Double.valueOf(0);
	}

	/**
	 * 计算申请单物料与报关单物料的总毛重、总净重的差异
	 * 
	 * @param info
	 *            报关单商品信息
	 * @return List 是ImpExpCommodityInfo型，进出口申请单表体资料.
	 */
	public List findAppFromMaterielByCustomsInfoAndNetGossWeight(
			BaseCustomsDeclarationCommInfo info) {
		List list = this.baseEncDao.findAppFromMaterielByCustomsInfo(info);
		Double bnetweight = info.getNetWeight() == null ? Double.valueOf(0)
				: info.getNetWeight();// 申报净重
		Double bgossweight = info.getGrossWeight() == null ? Double.valueOf(0)
				: info.getGrossWeight();// 申报毛重
		List allList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			ImpExpCommodityInfo cinfo = (ImpExpCommodityInfo) list.get(i);
			Double commamount = cinfo.getQuantity();// 数量
			Double cknetweight = cinfo.getNetWeight();
			Double ckgossweight = cinfo.getGrossWeight();
			Double netcy = bnetweight - (cknetweight * commamount);
			Double gosscy = bgossweight - (ckgossweight * commamount);
			cinfo.setNetweightcy(netcy);// 净重差异
			cinfo.setGossweightcy(gosscy);// 毛重差异
			allList.add(cinfo);
		}
		return allList;
	}

	/**
	 * 只显示物料四码不为空
	 * 
	 * @param type
	 *            物料类型
	 * @param firstIndex
	 *            第一个索引
	 * @param ptNo
	 *            料号
	 * @param emsNo
	 *            手册号
	 * @return List 是Materiel型，报关常用物料
	 */
	public List findMaterielFromInner(String type, int firstIndex, String ptNo,
			String emsNo) {
		List resultList = new ArrayList();
		List listm = this.baseEncDao.findMaterielFromInner(type, firstIndex,
				ptNo);

		for (int i = 0; i < listm.size(); i++) {
			Materiel materiel = (Materiel) listm.get(i);
			String ptNos = materiel.getPtNo();
			Integer seqNum = this.baseEncDao.findSeqNumByPtNo(ptNos, type);
			if (seqNum == null) {
				continue;
			}
			List list = this.baseEncDao.findImgExgBillBySeqNum(seqNum, type,
					emsNo);
			if (list == null || list.size() == 0) {
				continue;
			}
			resultList.add(materiel);
		}
		return resultList;
	}

	/**
	 * 查询归并关系归并前料件表OR归并关系归并前成品表相关信息
	 * 
	 * @param type
	 *            类型
	 * @param firstIndex
	 *            第一个索引
	 * @param obj
	 *            查询的值
	 * @param sFields
	 *            查询的属性
	 * @return List 是Materiel型,报关常用工厂物料
	 */
	public List findMaterielFromInnerAndInEmsMerger(String type,
			int firstIndex, Object obj, String sFields) {
		List resultList = new ArrayList();
		List listm = this.baseEncDao.findMaterielFromInner(type, firstIndex,
				obj, sFields);
		for (int i = 0; i < listm.size(); i++) {
			Materiel materiel = (Materiel) listm.get(i);
			String ptNos = materiel.getPtNo();
			List list = this.baseEncDao.findEmsEdiMergerImgBeforeByGNo(ptNos,
					type);
			if (list != null && list.size() > 0) {
				resultList.add(materiel);
			}
		}
		return resultList;
	}

	// public List findMaterielFromInnerAndInEmsMerger(String type,
	// int firstIndex, String ptNo) {
	// List resultList = new ArrayList();
	// List listm = this.baseEncDao.findMaterielFromInner(type, firstIndex,
	// ptNo);
	// for (int i = 0; i < listm.size(); i++) {
	// Materiel materiel = (Materiel) listm.get(i);
	// String ptNos = materiel.getPtNo();
	// List list = this.baseEncDao.findEmsEdiMergerImgBeforeByGNo(ptNos,
	// type);
	// if (list != null && list.size() > 0) {
	// resultList.add(materiel);
	// }
	// }
	// return resultList;
	// }

	/**
	 * 报关单明细排序
	 * 
	 * @param customsDeclaration
	 *            报关单表头
	 */
	public void commInfoAutoOrder(BaseCustomsDeclaration customsDeclaration) {
		List list = this.baseEncDao
				.findCustomsDeclarationCommInfoOrderByCommSerialNo(customsDeclaration);
		for (int i = 0; i < list.size(); i++) {
			BaseCustomsDeclarationCommInfo commInfo = (BaseCustomsDeclarationCommInfo) list
					.get(i);
			commInfo.setSerialNumber(Integer.valueOf(i + 1));
			baseEncDao.saveCustomsDeclarationCommInfo(commInfo);
		}
	}

	/**
	 * 第一次从数据库抓取最大值出来
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @param emsHeadH2k
	 *            电子帐册号码
	 * @param baseEncDao
	 *            基础DAO
	 * @return int 最大流水号
	 */
	public static int findMaxSerialNumberFromDB(int impExpFlag,
			String emsHeadH2k, BaseEncDao baseEncDao) {
		return baseEncDao.findMaxSerialNumberFromDB(impExpFlag, emsHeadH2k);
	}

	/**
	 * 进口报关单表头流水号排序
	 * 
	 * @param emsNo
	 *            电子帐册号码
	 */
	public void resortImportCustomsDeclarationSerialNumber(String emsNo) {
		int temp = -1;
		List list = this.baseEncDao
				.findRepeatImportCustomsDeclarationByEmsNo(emsNo);
		int maxSerialNumber = findMaxSerialNumberFromDB(ImpExpFlag.IMPORT,
				emsNo, baseEncDao);
		for (int i = 0; i < list.size(); i++) {
			BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) list
					.get(i);
			if (customsDeclaration.getSerialNumber().equals(temp)) {
				maxSerialNumber++;
				customsDeclaration.setSerialNumber(Integer
						.valueOf(maxSerialNumber));
				baseEncDao.saveCustomsDeclarationHead(customsDeclaration);
			} else {
				temp = customsDeclaration.getSerialNumber();
			}
		}
		BaseEncUtils.resetMaxCustomsDeclarationSerialNumber(
				baseEncDao.projectType, ImpExpFlag.IMPORT, emsNo, baseEncDao);
	}

	/**
	 * 出口报关单表头流水号排序
	 * 
	 * @param emsNo
	 *            电子帐册号码
	 */
	public void resortExportCustomsDeclarationSerialNumber(String emsNo) {
		int temp = -1;
		int maxSerialNumber = findMaxSerialNumberFromDB(ImpExpFlag.EXPORT,
				emsNo, baseEncDao);
		List list = this.baseEncDao
				.findRepeatExportCustomsDeclarationByEmsNo(emsNo);
		for (int i = 0; i < list.size(); i++) {
			BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) list
					.get(i);
			if (customsDeclaration.getSerialNumber().equals(temp)) {
				maxSerialNumber++;
				customsDeclaration.setSerialNumber(Integer
						.valueOf(maxSerialNumber));
				baseEncDao.saveCustomsDeclarationHead(customsDeclaration);
			} else {
				temp = customsDeclaration.getSerialNumber();
			}
		}
		BaseEncUtils.resetMaxCustomsDeclarationSerialNumber(
				baseEncDao.projectType, ImpExpFlag.EXPORT, emsNo, baseEncDao);
	}

	/**
	 * 特殊报关单表头流水号排序
	 * 
	 * @param emsNo
	 *            电子帐册号码
	 */
	public void resortSpecialCustomsDeclarationSerialNumber() {
		int temp = -1;
		int maxSerialNumber = findMaxSerialNumberFromDB(ImpExpFlag.SPECIAL,
				null, baseEncDao);
		List list = this.baseEncDao
				.findRepeatSpecialCustomsDeclarationByEmsNo();
		for (int i = 0; i < list.size(); i++) {
			BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) list
					.get(i);
			if (customsDeclaration.getSerialNumber().equals(temp)) {
				maxSerialNumber++;
				customsDeclaration.setSerialNumber(Integer
						.valueOf(maxSerialNumber));
				baseEncDao.saveCustomsDeclarationHead(customsDeclaration);
			} else {
				temp = customsDeclaration.getSerialNumber();
			}
		}
		BaseEncUtils.resetMaxCustomsDeclarationSerialNumber(
				baseEncDao.projectType, ImpExpFlag.SPECIAL, null, baseEncDao);
	}

	/**
	 * 增加设备管理的特殊报关单
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头
	 * @param list
	 *            批文协议设备征免税证明细LIST
	 * @return List为BaseCustomsDeclarationCommInfo型,报关单明细.
	 */
	public List addFixAssetCustomsDeclarationCommInfo(
			BaseCustomsDeclaration baseCustomsDeclaration, List list) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			BaseCustomsDeclarationCommInfo commInfo = null;
			if (baseCustomsDeclaration instanceof CustomsDeclaration) {
				commInfo = new CustomsDeclarationCommInfo();
			} else if (baseCustomsDeclaration instanceof BcsCustomsDeclaration) {
				commInfo = new BcsCustomsDeclarationCommInfo();
			} else if (baseCustomsDeclaration instanceof DzscCustomsDeclaration) {
				commInfo = new DzscCustomsDeclarationCommInfo();
			}
			if (commInfo == null) {
				return new ArrayList();
			}
			DutyCertDetail dutyCertDetail = (DutyCertDetail) list.get(i);
			commInfo.setSerialNumber(this
					.getCustomsDeclarationCommInfoSerialNumber(baseCustomsDeclaration));
			commInfo.setCommSerialNo(dutyCertDetail.getMainNo());
			commInfo.setComplex(dutyCertDetail.getComplex());
			commInfo.setCommName(dutyCertDetail.getCommName());
			commInfo.setCommSpec(dutyCertDetail.getCommSpec());
			commInfo.setUnit(dutyCertDetail.getUnit());
			commInfo.setCommUnitPrice(dutyCertDetail.getUnitPrice());
			commInfo.setCommAmount(dutyCertDetail.getAmount());
			commInfo.setCommTotalPrice(dutyCertDetail.getTotalPrice());
			commInfo.setCountry(dutyCertDetail.getCountry());
			commInfo.setBaseCustomsDeclaration(baseCustomsDeclaration);
			this.baseEncDao.saveCustomsDeclarationCommInfo(commInfo);
			lsResult.add(commInfo);
		}
		return lsResult;
	}

	/**
	 * 增加设备管理的特殊报关单
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头
	 * @param list
	 *            合同备案料件资料LIST
	 * @return List为BaseCustomsDeclarationCommInfo型,报关单明细.
	 */
	public List addFixtureCustomsDeclarationCommInfo(
			BaseCustomsDeclaration baseCustomsDeclaration, List list) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			BaseCustomsDeclarationCommInfo commInfo = null;
			if (baseCustomsDeclaration instanceof CustomsDeclaration) {
				commInfo = new CustomsDeclarationCommInfo();
			} else if (baseCustomsDeclaration instanceof BcsCustomsDeclaration) {
				commInfo = new BcsCustomsDeclarationCommInfo();
			} else if (baseCustomsDeclaration instanceof DzscCustomsDeclaration) {
				commInfo = new DzscCustomsDeclarationCommInfo();
			}
			if (commInfo == null) {
				return new ArrayList();
			}
			FixtureContractItems fixtureContractItems = (FixtureContractItems) list
					.get(i);
			commInfo.setSerialNumber(this
					.getCustomsDeclarationCommInfoSerialNumber(baseCustomsDeclaration));
			commInfo.setCommSerialNo(fixtureContractItems.getSeqNum());
			commInfo.setComplex(fixtureContractItems.getComplex());
			commInfo.setCommName(fixtureContractItems.getName());
			commInfo.setCommSpec(fixtureContractItems.getSpec());
			commInfo.setUnit(fixtureContractItems.getUnit());
			commInfo.setCommUnitPrice(fixtureContractItems.getDeclarePrice());
			commInfo.setCommAmount(fixtureContractItems.getAmount());
			commInfo.setCommTotalPrice(fixtureContractItems.getTotalPrice());
			commInfo.setCountry(fixtureContractItems.getCountry());
			commInfo.setBaseCustomsDeclaration(baseCustomsDeclaration);
			this.baseEncDao.saveCustomsDeclarationCommInfo(commInfo);
			this.baseEncDao.saveOrUpdate(fixtureContractItems);
			lsResult.add(commInfo);
		}
		return lsResult;
	}

	/**
	 * 取得报关单金额
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @return Map 指定报关单的报关商品信息的统计情况
	 */
	public Map findCustomsDeclarationMoney(Integer impExpFlag) {
		Map<String, Double> map = new HashMap<String, Double>();
		List list = this.baseEncDao.findCustomsDeclarationMoney(impExpFlag);
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			if (objs[0] != null) {
				map.put(objs[0].toString().trim(), objs[1] == null ? 0.0
						: Double.parseDouble(objs[1].toString()));
			}
		}
		return map;
	}

	/**
	 * 取得报关单商品信息
	 * 
	 * @param conveyance
	 *            运输工具
	 * @param impExpFlag
	 *            进出口标志
	 * @return 报关单商品信息
	 */
	public List findMergerCustomsDeclarationCommInfoByConveyance(
			String conveyance, int impExpFlag, boolean isReworkTotalPrice) {
		List returnList = new ArrayList();
		List<BaseCustomsDeclarationCommInfo> allList = this.baseEncDao
				.findMergerCustomsDeclarationCommInfoByConveyance(conveyance,
						impExpFlag);
		double totalPrice = 0.0;
		double netWeight = 0.0;
		double grossWeight = 0.0;
		int pieces = 0;
		Map<String, BaseCustomsDeclarationCommInfo> map = new HashMap<String, BaseCustomsDeclarationCommInfo>();
		BaseCustomsDeclarationCommInfo comInfo = null;
		for (BaseCustomsDeclarationCommInfo obj : allList) {
			int impExpType = obj.getBaseCustomsDeclaration().getImpExpType();
			String key = (obj.getComplex() == null ? "" : obj.getComplex()
					.getCode())
					+ "/"
					+ obj.getCommName()
					+ "/"
					+ obj.getCommSpec()
					+ "/"
					+ (obj.getWrapType() == null ? "" : obj.getWrapType()
							.getCode());
			System.out.println("===key==" + key);
			comInfo = map.get(key);
			if (comInfo != null) {
				if (!isReworkTotalPrice
						|| impExpType != ImpExpType.REWORK_EXPORT) {
					comInfo.setCommTotalPrice(comInfo.getCommTotalPrice()
							+ obj.getCommTotalPrice());
				}
				comInfo.setNetWeight(comInfo.getNetWeight()
						+ (obj.getNetWeight() == null ? 0.0 : obj
								.getNetWeight()));
				comInfo.setPieces(comInfo.getPieces()
						+ (obj.getPieces() == null ? 0 : obj.getPieces()));
				comInfo.setGrossWeight(comInfo.getGrossWeight()
						+ (obj.getGrossWeight() == null ? 0.0 : obj
								.getGrossWeight()));// 毛重
			} else {
				totalPrice = 0.0;
				netWeight = 0.0;
				pieces = 0;
				grossWeight = 0.0;
				comInfo = new BaseCustomsDeclarationCommInfo();
				comInfo.setIsSelected(new Boolean(false));
				comInfo.setComplex(obj.getComplex());
				comInfo.setCommName(obj.getCommName());// 商品名称
				comInfo.setCommSpec(obj.getCommSpec());// 规格
				comInfo.setWrapType(obj.getWrapType());// 包装类型
				netWeight = (obj.getNetWeight() == null ? 0.0 : obj
						.getNetWeight());// 净得
				grossWeight = (obj.getGrossWeight() == null ? 0.0 : obj
						.getGrossWeight());// 毛重
				pieces = (obj.getPieces() == null ? 0 : obj.getPieces());// 件数
				if (!isReworkTotalPrice
						|| impExpType != ImpExpType.REWORK_EXPORT) {
					if (obj.getCommTotalPrice() != null) {
						totalPrice = obj.getCommTotalPrice();// 总价
					}
				}
				comInfo.setCommTotalPrice(totalPrice);// 总价
				comInfo.setNetWeight(netWeight);// 净得
				comInfo.setPieces(pieces);// 件数
				comInfo.setAddType("否");
				comInfo.setBaseCustomsDeclaration(obj
						.getBaseCustomsDeclaration());
				comInfo.setGrossWeight(grossWeight);// 毛重
				map.put(key, comInfo);
				returnList.add(comInfo);
			}

		}
		return returnList;
	}

	/**
	 * 取得最后一次导入报关单的日期，并且将最后一次的日期-3
	 * 
	 * @param isImportBGD
	 *            为进口还是出口报关单
	 * @return Date 最后一次导入报关单的日期
	 */
	public Date getLastImportBGDDate(boolean isImportBGD) {
		Date date = null;
		List lsLoadInfo = this.baseEncDao
				.findLoadBGDFromQPXml(isImportBGD ? ImpExpFlag.IMPORT
						: ImpExpFlag.EXPORT);
		if (lsLoadInfo.size() > 0) {
			date = ((BaseLoadBGDFromQPXml) lsLoadInfo.get(0)).getDate();
		}
		return date;
	}

	/**
	 * 读取目录下的所有文件
	 * 
	 * @param path
	 *            设定的报关单导入路径
	 * @param projectType
	 *            项目类型
	 * @param isImportBGD
	 *            为进口还是出口报关单
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return Lis 为File型,文件格式
	 */
	private List<File> getAllXmlFiles(String path, int projectType,
			boolean isImportBGD, Date beginDate, Date endDate) {
		File curdir = new File(path);
		if (!curdir.exists()) {
			throw new RuntimeException("设定的报关单导入路径：" + path + " 不存在！");
		}
		List<File> lsXmlFile = new ArrayList<File>();
		File[] dateDir = curdir.listFiles();
		if (dateDir == null) {
			return lsXmlFile;
		}
		SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");
		String bdate = (beginDate == null ? "" : dateFormate.format(beginDate));
		String edate = (endDate == null ? "" : dateFormate.format(endDate));
		// String fdate = (date == null ? "" : dateFormate.format(date));
		for (int i = 0; i < dateDir.length; i++) {
			if (!dateDir[i].isDirectory()) {
				continue;
			}
			// System.out.println("--------------"+fdate);
			if (!"".equals(bdate.trim())
					&& dateDir[i].getName().compareTo(bdate) < 0) {
				continue;
			}
			if (!"".equals(edate.trim())
					&& dateDir[i].getName().compareTo(edate) > 0) {
				continue;
			}
			File[] ieDir = dateDir[i].listFiles();
			for (int j = 0; j < ieDir.length; j++) {
				if (!ieDir[j].isDirectory()) {
					continue;
				}
				if (isImportBGD) {
					if (!"I".equals(ieDir[j].getName())) {
						continue;
					}
				} else {
					if (!"E".equals(ieDir[j].getName())) {
						continue;
					}
				}
				File[] xmlFiles = ieDir[j].listFiles();
				for (int m = 0; m < xmlFiles.length; m++) {
					if (xmlFiles[m].isDirectory()) {
						continue;
					}
					lsXmlFile.add(xmlFiles[m]);
				}
			}
		}
		return lsXmlFile;
	}

	/**
	 * 判断是否是XML文件
	 * 
	 * @param file
	 *            文件类型
	 * @return boolean TRUE则为文件类型
	 */
	private boolean isxmlfile(File file) {
		if (file.isDirectory()) {
			return false;
		} else {
			String str = new String();
			str = file.getName();
			str = str.substring(file.getName().length() - 4).toLowerCase();
			return str.equals(".xml");
		}
	}

	/**
	 * 读取XML文件内容
	 * 
	 * @param element
	 *            元素
	 * @param tagname
	 *            子节点名
	 * @return List XML文件内容
	 */
	private List<Map<String, String>> loadXmlData(Element element,
			String tagname) {

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		List<Element> childList = element.getChildren(tagname);

		for (Element childElement : childList) {

			List<Element> leafList = childElement.getChildren();

			Map<String, String> map = new HashMap();

			for (Element leafElement : leafList) {

				map.put(leafElement.getName(),
						CommonServerBig5GBConverter.getInstance()
								.big5ConvertToGB(leafElement.getTextTrim()));
			}
			list.add(map);
		}
		return list;
	}

	/**
	 * 读取XML文件内容
	 * 
	 * @param element
	 *            元素
	 * @param tagname
	 *            子节点名
	 * @return Map XML文件内容
	 */
	private Map<String, String> loadXMLHEADData(Element element, String tagname) {
		List<Map<String, String>> list = loadXmlData(element, tagname);
		if (list.size() > 0) {
			return list.get(0);
		}
		return new HashMap();
	}

	/**
	 * 读取报关单单体信息
	 * 
	 * @param element
	 *            元素
	 * @param tagname
	 *            子节点名
	 * @return List XML文件内容
	 */
	private List<Map<String, String>> loadXMLListData(Element element,
			String tagname) {
		return loadXmlData(element, tagname);
	}

	/**
	 * 根据贸易方式判断是否是特殊报关单
	 * 
	 * @param tradeCode
	 *            贸易方式代码
	 * @return boolean 为TRUE 为特殊报关单
	 */
	private boolean isSpecialBGD(String tradeCode) {
		// 0110:一般贸易
		// 0864:进料边角料复出
		// 0865:来料边角料复出
		// 0844:进料边角料内销
		// 0845:来料边角料内销
		if ("0110".equals(tradeCode)
				|| "0864".equals(tradeCode)
				|| "0865".equals(tradeCode)
				|| "0844".equals(tradeCode)
				|| "0845".equals(tradeCode)
				// 设备
				|| "0320".equals(tradeCode) || "0420".equals(tradeCode)
				|| "0446".equals(tradeCode) || "0456".equals(tradeCode)
				|| "0466".equals(tradeCode) || "0500".equals(tradeCode)
				|| "2025".equals(tradeCode) || "2225".equals(tradeCode)
				|| "5300".equals(tradeCode) || "5335".equals(tradeCode)
				|| "5361".equals(tradeCode) || "3010".equals(tradeCode)
				|| "3039".equals(tradeCode)) {
			return true;
		}
		return false;
	}

	// /**
	// * 获取element子节点childTagName的值
	// *
	// * @param element
	// * 元素
	// * @param childTagName
	// * 子节点名
	// * @return String childTagName的值
	// */
	// private String getChildElementStringValue(Element element,
	// String childTagName) {
	// Element childElement = element.getChild(childTagName);
	// if (childElement != null) {
	// return childElement.getTextTrim();
	// } else {
	// return "";
	// }
	// }

	/**
	 * 新增报关单 10 电子手册进口报关单 11 电子手册出口报关单 20 联网监管进口报关单 21 联网监管出口报关单
	 * 
	 * @param root
	 *            根目录
	 * @param isFromFile
	 *            是否文本格式导入
	 * @param projectType
	 *            项目类型
	 * @param isImport
	 *            进出口标志
	 * @param headtagname
	 *            头节点名
	 * @param detailtagname
	 *            明细节点名
	 * @param jzxtagname
	 *            集装箱节点名
	 * @param lictagname
	 *            监管证编码节点名
	 * @param freetxttagname
	 *            自由文本标记名
	 * @param lsSuccess
	 *            成功信息
	 * @param lsError
	 *            错误信息
	 * 
	 */
	private void loadImpExpBGD(Element root, boolean isFromFile,
			int projectType, boolean isImport, String headtagname,
			String detailtagname, String jzxtagname, String lictagname,
			String freetxttagname, List<BaseLoadBGDFromQPXml> lsSuccess,
			List<TempLoadBGDFromQPXmlErrorInfo> lsError,
			ImportBGDCondition condition) {

		Map<String, String> BGDHead = loadXMLHEADData(root, headtagname);

		List<Map<String, String>> BGDDetails = loadXMLListData(root,
				detailtagname);

		Date pDate = null;

		List customAmountOutList = baseEncDao
				.findnameValues(ParameterType.CUSTOM_AMOUNT_OUT);

		ParameterSet customAmountOut = (customAmountOutList.size() > 0 ? (ParameterSet) customAmountOutList
				.get(0) : null);

		if (BGDHead.get("P_DATE") != null
				&& !"".equals(BGDHead.get("P_DATE").trim())) {

			if (BGDHead.get("P_DATE").indexOf("-") > 0) {

				try {

					if (BGDHead.get("P_DATE")
							.substring(0, BGDHead.get("P_DATE").indexOf('-'))
							.length() > 2) {

						pDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
								.parse(BGDHead.get("P_DATE"));

					} else {

						pDate = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
								.parse(BGDHead.get("P_DATE"));
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else if (BGDHead.get("P_DATE").indexOf("/") > 0) {
				try {
					if (BGDHead.get("P_DATE")
							.substring(0, BGDHead.get("P_DATE").indexOf('/'))
							.length() > 2) {
						pDate = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss")
								.parse(BGDHead.get("P_DATE"));
					} else {
						pDate = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
								.parse(BGDHead.get("P_DATE"));
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else {
				throw new RuntimeException("无效的日期格式:"
						+ BGDHead.get("P_DATE").trim());
			}
		}
		Date dDate = null;
		if (BGDHead.get("D_DATE") != null
				&& !"".equals(BGDHead.get("D_DATE").trim())) {
			if (BGDHead.get("D_DATE").indexOf("-") > 0) {
				try {
					if (BGDHead.get("D_DATE")
							.substring(0, BGDHead.get("D_DATE").indexOf('-'))
							.length() > 2) {
						dDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
								.parse(BGDHead.get("D_DATE"));
					} else {
						dDate = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
								.parse(BGDHead.get("D_DATE"));
					}

				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else if (BGDHead.get("D_DATE").indexOf("/") > 0) {
				try {

					if (BGDHead.get("D_DATE")
							.substring(0, BGDHead.get("D_DATE").indexOf('/'))
							.length() > 2) {
						dDate = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss")
								.parse(BGDHead.get("D_DATE"));
					} else {
						dDate = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
								.parse(BGDHead.get("D_DATE"));
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else {
				throw new RuntimeException("无效的日期格式:"
						+ BGDHead.get("D_DATE").trim());
			}
		}
		Date zDate = (isFromFile ? pDate : dDate);
		// Date zDate = dDate;
		/**
		 * 贸易方式
		 */
		String tradeCode = BGDHead.get("TRADE_MODE");
		String seqNo = BGDHead.get("SEQ_NO");
		// if (condition != null && condition.getTrade() != null &&
		// !"".equals(condition.getTrade().trim())) {
		// if (condition.getTrade().indexOf(
		// tradeCode == null ? "" : tradeCode.trim()) < 0) {
		// return;
		// }
		// }
		if (tradeCode == null || "".equals(tradeCode.trim())) {
			lsError.add(this.writeLoadBGDErrorInfo(projectType, seqNo, zDate,
					null, "贸易方式为空"));
			return;
		}
		tradeCode = tradeCode.trim();
		Integer impExpFlag = null;
		if (isSpecialBGD(tradeCode)) {
			impExpFlag = ImpExpFlag.SPECIAL;
		} else {
			if (isImport) {
				impExpFlag = ImpExpFlag.IMPORT;
			} else {
				impExpFlag = ImpExpFlag.EXPORT;
			}
		}

		if ((BGDHead.get("ENTRY_ID") == null || "".equals(BGDHead.get(
				"ENTRY_ID").trim()))
				&& (BGDHead.get("PRE_ENTRY_ID") == null || "".equals(BGDHead
						.get("PRE_ENTRY_ID").trim()))) {
			lsError.add(this.writeLoadBGDErrorInfo(projectType, seqNo, zDate,
					impExpFlag, "报关单号和报关单预录入号不能同时为空!"));
			return;
		}

		String entryId = BGDHead.get("ENTRY_ID") == null ? "" : BGDHead.get(
				"ENTRY_ID").trim();

		if (condition != null && condition.getEntryId() != null) {
			if (!condition.getEntryId().equals(entryId)) {
				return;
			}
		}

		/**
		 * 新增报关单单头
		 */
		BaseCustomsDeclaration bgdhead = null;

		List codelist = new ArrayList();

		if (BGDHead.get("ENTRY_ID") != null
				&& !"".equals(BGDHead.get("ENTRY_ID").trim())) {
			codelist = this.baseEncDao.findCustomsDeclarationByCode(BGDHead
					.get("ENTRY_ID").trim());

		} else if (BGDHead.get("PRE_ENTRY_ID") != null
				&& !"".equals(BGDHead.get("PRE_ENTRY_ID").trim())) {

			codelist = this.baseEncDao.findCustomsDeclarationByPreCode(BGDHead
					.get("PRE_ENTRY_ID").trim());
		}

		if (codelist.size() > 0) {
			return;
		} else {

			String unificationCode = BGDHead.get("SEQ_NO");
			if (projectType == ProjectType.DZSC) {
				bgdhead = new DzscCustomsDeclaration();
			} else if (projectType == ProjectType.BCUS) {

				// /
				// 开始判断是否有清单转入,如果是就覆盖已转入的报关单
				List listQd = this.baseEncDao
						.findBcusApplyToCustomsBillList(unificationCode);
				if (listQd != null && listQd.size() > 0
						&& listQd.get(0) != null) {
					ApplyToCustomsBillList bill = (ApplyToCustomsBillList) listQd
							.get(0);
					List listBgd = this.baseEncDao
							.findCustomsDeclarationByListNo(bill.getListNo());
					if (listBgd != null && listBgd.size() > 0
							&& listBgd.get(0) != null) {
						bgdhead = (CustomsDeclaration) listBgd.get(0);
					} else {
						bgdhead = new CustomsDeclaration();
					}
				} else {
					bgdhead = new CustomsDeclaration();
				}

			} else if (projectType == ProjectType.BCS) {
				bgdhead = new BcsCustomsDeclaration();
			} else {
				throw new RuntimeException("不识别系统类型");
			}
		}
		try {
			List list = null;
			/**
			 * 贸易方式
			 */
			list = findClass("Trade", "code", tradeCode);
			Trade trade = null;
			if (list.size() > 0) {
				trade = (Trade) list.get(0);
				bgdhead.setTradeMode(trade);
			}
			String emsNo = BGDHead.get("MANUAL_NO") == null ? "" : BGDHead.get(
					"MANUAL_NO").trim();
			if (condition != null && condition.getEmsNo() != null) {
				if (condition.getEmsNo().indexOf(emsNo) < 0) {
					return;
				}
			}
			// 如果不是特殊报关单，将进行手册账册号判断
			if (!isSpecialBGD(tradeCode)) {
				if (BGDHead.get("MANUAL_NO") == null
						|| "".equals(BGDHead.get("MANUAL_NO").trim())) {
					lsError.add(this.writeLoadBGDErrorInfo(projectType, seqNo,
							zDate, impExpFlag, "手册/账册号为空"));
					return;
				}
				if (this.baseEncDao.findExingEMSCountByEmsNo(BGDHead
						.get("MANUAL_NO")) <= 0) {
					lsError.add(this.writeLoadBGDErrorInfo(projectType, seqNo,
							zDate, impExpFlag,
							"系统中没有正在执行的手册/账册号是" + BGDHead.get("MANUAL_NO")
									+ "的手册/账册"));
					return;
				}
			}
			bgdhead.setEmsHeadH2k(BGDHead.get("MANUAL_NO"));// 帐/手册号

			/**
			 * 合同表头
			 */
			DzscEmsPorHead contract = baseEncDao
					.findExingDzscEmsPorHeadByEmsNo(bgdhead.getEmsHeadH2k());

			/**
			 * 进出口标志
			 */
			bgdhead.setImpExpFlag(impExpFlag);

			/**
			 * 进出口类型
			 */
			Integer impexptype = 99;
			if (isSpecialBGD(tradeCode)) {
				String tradeName = trade.getName().trim();
				if ("一般贸易".equals(tradeName)) {
					if (isImport) {
						tradeName += "进口";
					} else {
						tradeName += "出口";
					}
				}

				impexptype = MapTrade.getImpExpType(tradeName, impExpFlag);
			} else {
				if (isImport) {
					impexptype = MapTrade.getImpExpType(trade.getName().trim(),
							0);
				} else {
					impexptype = MapTrade.getImpExpType(trade.getName().trim(),
							1);
				}
			}
			bgdhead.setImpExpType(impexptype);

			/**
			 * 流水号
			 */
			bgdhead.setSerialNumber(this.baseEncDao
					.getCustomsDeclarationSerialNumber(bgdhead.getImpExpFlag(),
							bgdhead.getEmsHeadH2k()));
			/**
			 * 进出口岸
			 */
			list = findClass("Customs", "code", BGDHead.get("I_E_PORT"));
			if (list.size() > 0) {
				Customs custom = (Customs) list.get(0);
				bgdhead.setCustoms(custom);
			}
			/**
			 * 统一编号
			 */
			bgdhead.setUnificationCode(BGDHead.get("SEQ_NO"));
			/**
			 * 合同协议号
			 */
			bgdhead.setContract(BGDHead.get("CONTR_NO"));
			/**
			 * 申报日期
			 */
			String strDDate = BGDHead.get("D_DATE");
			if (strDDate == null || "".equals(strDDate.trim())) {
				bgdhead.setDeclarationDate(null);
			} else {
				if (strDDate.indexOf("-") > 0) {
					try {

						if (strDDate.substring(0, strDDate.indexOf('-'))
								.length() > 2) {
							bgdhead.setDeclarationDate(new SimpleDateFormat(
									"yyyy-MM-dd hh:mm:ss").parse(strDDate));
						} else {
							bgdhead.setDeclarationDate(new SimpleDateFormat(
									"dd-MM-yyyy hh:mm:ss").parse(strDDate));
						}
					} catch (ParseException e) {
						throw new RuntimeException(e);
					}
				} else if (strDDate.indexOf("/") > 0) {
					try {

						if (strDDate.substring(0, strDDate.indexOf('/'))
								.length() > 2) {
							bgdhead.setDeclarationDate(new SimpleDateFormat(
									"yyyy/MM/dd hh:mm:ss").parse(strDDate));
						} else {
							bgdhead.setDeclarationDate(new SimpleDateFormat(
									"dd/MM/yyyy hh:mm:ss").parse(strDDate));
						}
					} catch (ParseException e) {
						throw new RuntimeException(e);
					}
				} else {
					throw new RuntimeException("无效的日期格式:" + strDDate.trim());
				}
			}
			/**
			 * 进口日期
			 */
			String strIDate = BGDHead.get("I_E_DATE");
			if (strIDate == null || "".equals(strIDate.trim())) {
				bgdhead.setImpExpDate(null);
			} else {
				if (strIDate.indexOf("-") > 0) {
					try {
						if (strIDate.substring(0, strIDate.indexOf('-'))
								.length() > 2) {
							bgdhead.setImpExpDate(new SimpleDateFormat(
									"yyyy-MM-dd hh:mm:ss").parse(strIDate));
						} else {
							bgdhead.setImpExpDate(new SimpleDateFormat(
									"dd-MM-yyyy hh:mm:ss").parse(strIDate));
						}

					} catch (ParseException e) {
						throw new RuntimeException(e);
					}
				} else if (strIDate.indexOf("/") > 0) {
					try {
						if (strIDate.substring(0, strIDate.indexOf('/'))
								.length() > 2) {
							bgdhead.setImpExpDate(new SimpleDateFormat(
									"yyyy/MM/dd hh:mm:ss").parse(strIDate));
						} else {
							bgdhead.setImpExpDate(new SimpleDateFormat(
									"dd/MM/yyyy hh:mm:ss").parse(strIDate));
						}
					} catch (ParseException e) {
						throw new RuntimeException(e);
					}
				} else {
					throw new RuntimeException("无效的日期格式:" + strIDate.trim());
				}
			}
			// 如果进出口日期为空的话，那么就默认进出口日期为申报日期
			if (bgdhead.getImpExpDate() == null) {
				bgdhead.setImpExpDate(bgdhead.getDeclarationDate());
			}
			/**
			 * 经营单位
			 */
			bgdhead.setTradeCode(BGDHead.get("TRADE_CO"));
			bgdhead.setTradeName(BGDHead.get("TRADE_NAME"));
			/**
			 * 收货单位
			 */
			bgdhead.setMachCode(BGDHead.get("OWNER_CODE"));
			bgdhead.setMachName(BGDHead.get("OWNER_NAME"));
			/**
			 * 运输方式
			 */
			list = findClass("Transf", "code", BGDHead.get("TRAF_MODE"));
			if (list.size() > 0) {
				Transf trans = (Transf) list.get(0);
				bgdhead.setTransferMode(trans);
			}
			/**
			 * 运输工具
			 */
			bgdhead.setConveyance(BGDHead.get("TRAF_NAME"));

			/**
			 * 提运单号
			 */
			bgdhead.setBillOfLading(BGDHead.get("BILL_NO"));

			/**
			 * 征免性质
			 */
			list = findClass("LevyKind", "code", BGDHead.get("CUT_MODE"));
			if (list.size() > 0) {
				LevyKind levykind = (LevyKind) list.get(0);
				bgdhead.setLevyKind(levykind);
			}
			/**
			 * 起运国
			 */
			list = findClass("Country", "code", BGDHead.get("TRADE_COUNTRY"));
			if (list.size() > 0) {
				Country trade_contry = (Country) list.get(0);
				bgdhead.setCountryOfLoadingOrUnloading(trade_contry);
			}
			/**
			 * 装货港
			 */
			String portLinCode = BGDHead.get("DISTINATE_PORT");
			if (portLinCode != null && portLinCode.length() == 3) {
				portLinCode = ("0" + portLinCode);
			}
			list = findClass("PortLin", "code", portLinCode);
			if (list.size() > 0) {
				PortLin portOfLoading = (PortLin) list.get(0);
				bgdhead.setPortOfLoadingOrUnloading(portOfLoading);
			}
			/**
			 * 批准文号(核销单号)
			 */
			bgdhead.setAuthorizeFile(BGDHead.get("APPR_NO"));

			/**
			 * 结汇方式
			 */
			list = findClass("BalanceMode", "code", BGDHead.get("PAY_WAY"));
			if (list.size() > 0) {
				BalanceMode balan = (BalanceMode) list.get(0);
				bgdhead.setBalanceMode(balan);
			}
			/**
			 * 境内目的地
			 */
			list = findClass("District", "code", BGDHead.get("DISTRICT_CODE"));
			if (list.size() > 0) {
				District DomesticDestination = (District) list.get(0);
				bgdhead.setDomesticDestinationOrSource(DomesticDestination);
			}
			/**
			 * 成交方式
			 */
			list = findClass("Transac", "code", BGDHead.get("TRANS_MODE"));
			if (list.size() > 0) {
				Transac trans = (Transac) list.get(0);
				bgdhead.setTransac(trans);
			}
			/**
			 * 件数
			 */
			String commoditynum = BGDHead.get("PACK_NO");
			if (commoditynum == null || commoditynum.equals(""))
				commoditynum = "0";
			bgdhead.setCommodityNum(Integer.valueOf(commoditynum));

			/**
			 * 包装种类
			 */
			list = findClass("Wrap", "code", BGDHead.get("WRAP_TYPE"));
			if (list.size() > 0) {
				Wrap wrap = (Wrap) list.get(0);
				bgdhead.setWrapType(wrap);
			}

			/**
			 * 用途
			 */
			list = findClass("Uses", "code", BGDHead.get("USE_TO"));
			if (list.size() > 0) {
				Uses user = (Uses) list.get(0);
				bgdhead.setUses(user);
			}

			/**
			 * 毛重
			 */
			String strgrosswt = BGDHead.get("GROSS_WT");
			if (strgrosswt == null || strgrosswt.equals(""))
				strgrosswt = "0";
			bgdhead.setGrossWeight(Double.valueOf(strgrosswt));

			/**
			 * 净重
			 */
			String strnetwt = BGDHead.get("NET_WT");
			if (strnetwt == null || strnetwt.equals(""))
				strnetwt = "0";
			bgdhead.setNetWeight(Double.valueOf(strnetwt));
			/**
			 * 报关单号
			 */
			bgdhead.setCustomsDeclarationCode(BGDHead.get("ENTRY_ID").trim());

			/**
			 * 申报单位名称
			 */
			list = findClass("Company", "code", BGDHead.get("AGENT_CODE"));
			if (list.size() > 0) {
				Company compa = (Company) list.get(0);
				bgdhead.setDeclarationCompany(compa);
			}
			/**
			 * 生产厂家
			 */
			list = findClass("Brief", "code", BGDHead.get("OWNER_CODE"));
			if (list.size() > 0) {
				Brief brief = (Brief) list.get(0);
				bgdhead.setManufacturer(brief);
			}
			/**
			 * 录入员
			 */
			list = findClass("AclUser", "userName", BGDHead.get("INPUTER_NAME"));
			if (list.size() > 0) {
				AclUser user = (AclUser) list.get(0);
				bgdhead.setCreater(user);
			}
			/**
			 * 备注
			 */
			bgdhead.setMemos(BGDHead.get("NOTE_S"));

			/**
			 * 许可证号
			 */
			bgdhead.setLicense(BGDHead.get("LICENSE_NO"));
			/**
			 * 码头 2010-09-07 hcl ADD
			 */
			list = findClass("PreDock", "code", BGDHead.get("PREDOCK"));
			if (list.size() > 0) {
				PreDock preDock = (PreDock) list.get(0);
				bgdhead.setPredock(preDock);
			}
			/**
			 * 发票号 2010-09-07 hcl ADD
			 */
			bgdhead.setInvoiceCode(BGDHead.get("INVOICECODE"));
			/**
			 * 报送海关
			 */
			list = findClass("Customs", "code", BGDHead.get("CUSTOM_MASTER"));
			if (list.size() > 0) {
				Customs cust = (Customs) list.get(0);
				bgdhead.setDeclarationCustoms(cust);
			}

			String customsCode = BGDHead.get("CUSTOM_MASTER") == null ? ""
					: BGDHead.get("CUSTOM_MASTER").trim();
			// if (condition != null && condition.getCustoms() != null) {
			// if (condition.getCustoms().indexOf(customsCode) < 0) {
			// return;
			// }
			// }
			/**
			 * 预录入编号
			 */
			bgdhead.setPreCustomsDeclarationCode(BGDHead.get("PRE_ENTRY_ID"));
			/**
			 * 依附单据
			 */
			bgdhead.setAttachedBill(BGDHead.get("CERT_MARK"));
			// /**
			// * 统一编号
			// */
			// bgdhead.setUnificationCode(BGDHead.get("SEQ_NO"));

			/**
			 * 集装箱信息
			 */
			List<Map<String, String>> BGDjzx = loadXMLListData(root, jzxtagname);
			String jzxstring = "";
			String pzjzxstring = "";
			String sJZXModel = "";
			String sJZXCode = "";
			int jzcount = 0;
			int pzjzcount = 0;
			List<BaseCustomsDeclarationContainer> lsBGDjzx = new ArrayList<BaseCustomsDeclarationContainer>();
			if (BGDjzx.size() > 0) {
				for (int i = 0; i < BGDjzx.size(); i++) {
					BaseCustomsDeclarationContainer bgdcontainer = null;
					if (projectType == ProjectType.DZSC) {
						bgdcontainer = new DzscCustomsDeclarationContainer();
					} else if (projectType == ProjectType.BCUS) {
						bgdcontainer = new CustomsDeclarationContainer();
					} else if (projectType == ProjectType.BCS) {
						bgdcontainer = new BcsCustomsDeclarationContainer();
					}
					jzcount = jzcount + 1;
					Map<String, String> delist = BGDjzx.get(i);
					sJZXCode = delist.get("CONTAINER_ID");

					if (CommonUtils.isEmpty(sJZXCode)) {
						continue;
					}

					bgdcontainer.setContainerCode(sJZXCode);
					jzxstring = jzxstring + sJZXCode;
					sJZXModel = delist.get("CONTAINER_MD");
					if (i < BGDjzx.size() - 1) {
						jzxstring = jzxstring + ",";
					}
					if (sJZXModel.equals("L")) {
						pzjzcount = pzjzcount + 2;
					}
					if (sJZXModel.equals("S")) {
						pzjzcount = pzjzcount + 1;
					}
					lsBGDjzx.add(bgdcontainer);
				}

				if (CommonUtils.notEmpty(sJZXCode)) {
					pzjzxstring = sJZXCode + "*" + String.valueOf(jzcount)
							+ "(" + String.valueOf(pzjzcount) + ")";
					/**
					 * 集装箱号
					 */
					bgdhead.setAllContainerNum(jzxstring);
					bgdhead.setContainerNum(pzjzxstring);
				}
			} else {
				if (bgdhead.getImpExpType() != null
						&& (ImpExpType.TRANSFER_FACTORY_EXPORT == bgdhead
								.getImpExpType() || ImpExpType.TRANSFER_FACTORY_IMPORT == bgdhead
								.getImpExpType())) {
					/**
					 * 如果是转厂报关单，如果集装箱为空，则集装箱填写“0”
					 */
					bgdhead.setContainerNum("0");
				}
			}

			/**
			 * 监管证编码
			 */
			List<Map<String, String>> BGDlic = loadXMLListData(root, lictagname);
			if (BGDlic != null && BGDlic.size() > 0) {
				String certCode = "";
				for (int i = 0; i < BGDlic.size(); i++) {
					Map<String, String> delist = BGDlic.get(i);
					String docuCode = (delist.get("DOCU_CODE") == null ? ""
							: delist.get("DOCU_CODE"));
					String temp = (delist.get("CERT_CODE") == null ? ""
							: delist.get("CERT_CODE"));
					if (temp != null && !"".equals(temp) && docuCode != null
							&& !"".equals(docuCode)) {
						certCode += ((temp.indexOf(docuCode + ":") >= 0 ? temp
								: (docuCode + ":" + temp)) + ",");
					}
				}
				bgdhead.setCertificateCode(certCode);
			}
			/**
			 * 航次,根据运输方式(2,5)判断
			 */
			String trafMode = BGDHead.get("TRAF_MODE");
			if ("2".equals(trafMode) || "5".equals(trafMode)) {
				List<Map<String, String>> listFreetxt = loadXmlData(
						root.getChild(freetxttagname), "FREE_TEXTS");
				// System.out.println("-----------------------listFreetxt.size:"+listFreetxt.size());
				if (listFreetxt.size() > 0) {
					Map<String, String> BGDFreetxt = listFreetxt.get(0);
					bgdhead.setDomesticConveyanceFlights(BGDFreetxt
							.get("VOYNO"));
				}
			}
			/**
			 * 币别
			 */
			if (BGDDetails.size() > 0) {
				Map<String, String> detailmap = (Map<String, String>) BGDDetails
						.get(0);
				list = findClass("Curr", "code", detailmap.get("TRADE_CURR"));
				if (list.size() > 0) {
					Curr cur = (Curr) list.get(0);
					bgdhead.setCurrency(cur);
				}
			}
			/**
			 * 汇率
			 */
			if (BGDDetails.size() > 0) {
				Map<String, String> detailmap = (Map<String, String>) BGDDetails
						.get(0);
				String exchangeRate = detailmap.get("EXCHANGE_RATE");
				if (exchangeRate == null || "".equals(exchangeRate.trim())) {
					exchangeRate = "0.0";
				}
				bgdhead.setExchangeRate(Double.valueOf(exchangeRate.trim()));
			}
			/**
			 * 设置报关员
			 */
			List lsCustomer = this.baseEncDao.findCustomsUser();
			if (lsCustomer.size() > 0 && lsCustomer.get(0) != null) {
				bgdhead.setCustomser(((CustomsUser) lsCustomer.get(0))
						.getName());
			}
			/**
			 * 报关员电话
			 */
			bgdhead.setTelephone(BGDHead.get("TELPHONE"));

			/**
			 * 客户/供应商
			 */
			String scmCocCode = BGDHead.get("SCM_COC_CODE");
			if (scmCocCode != null && !"".equals(scmCocCode.trim())) {
				bgdhead.setCustomer(this.baseEncDao.findScmCocByCode(scmCocCode
						.trim()));
			}

			/**
			 * 运费类型
			 */
			String feeMark = BGDHead.get("FEE_MARK");
			if (!"".equals(feeMark.trim())) {
				bgdhead.setFreightageType(Integer.valueOf(feeMark.trim()));
			}

			/**
			 * 运费价值
			 */
			String feeRate = BGDHead.get("FEE_RATE");
			if (!"".equals(feeRate.trim()) && "1".equals(feeMark.trim())) {
				bgdhead.setFreightage(Double.valueOf(feeRate.trim()) / 10000);
			} else if (!"".equals(feeRate.trim())) {
				bgdhead.setFreightage(Double.valueOf(feeRate.trim()));
			}

			/**
			 * 运费币制
			 */
			list = findClass("Curr", "code", BGDHead.get("FEE_CURR"));
			if (list.size() > 0) {
				Curr cur = (Curr) list.get(0);
				bgdhead.setFeeCurr(cur);
			}

			/**
			 * 保费类型
			 */
			String insurMark = BGDHead.get("INSUR_MARK");
			if (!"".equals(insurMark.trim())) {
				bgdhead.setInsuranceType(Integer.valueOf(insurMark.trim()));
			}

			/**
			 * 保费价值
			 */
			String insurRate = BGDHead.get("INSUR_RATE");
			if (!"".equals(insurRate.trim()) && "1".equals(insurMark.trim())) {
				bgdhead.setInsurance(Double.valueOf(insurRate.trim()) / 10000);
			} else if (!"".equals(insurRate.trim())) {
				bgdhead.setInsurance(Double.valueOf(insurRate.trim()));
			}

			/**
			 * 保费币制
			 */
			list = findClass("Curr", "code", BGDHead.get("INSUR_CURR"));
			if (list.size() > 0) {
				Curr cur = (Curr) list.get(0);
				bgdhead.setInsurCurr(cur);
			}

			/**
			 * 杂费类型
			 */
			String otherMark = BGDHead.get("OTHER_MARK");
			if (!"".equals(otherMark.trim())) {
				bgdhead.setIncidentalExpensesType(Integer.valueOf(otherMark
						.trim()));
			}

			/**
			 * 杂费价值
			 */
			String otherRate = BGDHead.get("OTHER_RATE");
			if (!"".equals(otherRate.trim()) && "1".equals(otherMark.trim())) {
				bgdhead.setIncidentalExpenses(Double.valueOf(otherRate.trim()) / 10000);
			} else if (!"".equals(otherRate.trim())) {
				bgdhead.setIncidentalExpenses(Double.valueOf(otherRate.trim()));
			}

			/**
			 * 杂费币制
			 */
			list = findClass("Curr", "code", BGDHead.get("OTHER_CURR"));
			if (list.size() > 0) {
				Curr cur = (Curr) list.get(0);
				bgdhead.setOtherCurr(cur);
			}

			bgdhead.setCompany(CommonUtils.getCompany());// 公司
			bgdhead.setCreater(CommonUtils.getAclUser());

			// 当【贸易方式】为0110 一般贸易，且检查报关单号第九位，当为0时，更新进出口类型为
			// 【一般贸易出口】，当为1时，更新进出口类型为【一般贸易进口】并标记为特殊报关单SPECIAL=2

			if (bgdhead.getTradeMode() != null
					&& bgdhead.getCustomsDeclarationCode() != null
					&& !"".equals(bgdhead.getCustomsDeclarationCode())) {
				if (bgdhead.getCustomsDeclarationCode().trim().length() > 9) {
					// key = 贸易方式+报关单号第九位
					String key = bgdhead.getTradeMode().getCode()
							+ bgdhead.getCustomsDeclarationCode().trim()
									.substring(8, 9);
					if ("01100".equals(key)) {// 出口
						bgdhead.setImpExpType(ImpExpType.GENERAL_TRADE_EXPORT);
						bgdhead.setImpExpFlag(ImpExpFlag.SPECIAL);
					} else if ("01101".equals(key)) {// 进口
						bgdhead.setImpExpType(ImpExpType.GENERAL_TRADE_IMPORT);
						bgdhead.setImpExpFlag(ImpExpFlag.SPECIAL);
					}
				}
			}

			/**
			 * 保存报关单头
			 */
			this.baseEncDao.saveCustomsDeclaration(bgdhead);

			/**
			 * 保存集装箱明细
			 */
			for (BaseCustomsDeclarationContainer bgdcontainer : lsBGDjzx) {
				bgdcontainer.setBaseCustomsDeclaration(bgdhead);
				this.baseEncDao.saveCustomsDeclarationContainer(bgdcontainer);
			}
			List lsit = baseEncDao.findCommInfoByCustomsDeclarationID(bgdhead
					.getId());
			if (lsit != null && lsit.size() > 0) {
				for (int j = 0; j < lsit.size(); j++) {
					CustomsDeclarationCommInfo info = (CustomsDeclarationCommInfo) lsit
							.get(j);
					List listMake = this.baseEncDao
							.findMakeListToCustomsByCommInfo(info.getId());
					if (listMake.size() > 0) {
						this.baseEncDao.deleteAll(listMake);
					}
					baseEncDao.deleteCustomsDeclarationCommInfo(info);
				}

			}

			// 获取规格型号 对应的 Map
			Map<Integer, String> map = getCommSpec(projectType, bgdhead);

			System.out.println("对应规格 --- Map 这里 测试 所有的 规格数据 ------");

			System.out.println(map);

			System.out.println("----------------------------");

			/**
			 * 新增报关单表体
			 */
			for (int i = 0; i < BGDDetails.size(); i++) {
				Map<String, String> detailmap = (Map<String, String>) BGDDetails
						.get(i);
				BaseCustomsDeclarationCommInfo bgddetailinfo = null;
				/**
				 * 合同对应序号
				 */
				String strcontract_item = detailmap.get("CONTR_ITEM");

				System.out.println("   合同序号 >>>>>>>>>>>>>>>>>>>>>>>");

				System.out.println(" >>>>>>>  " + strcontract_item
						+ "    <<<<<");

				System.out.println(strcontract_item.length());

				System.out.println(" ======= > 字符长度 检测 < ==========");

				/**
				 * 原产国
				 */
				list = findClass("Country", "code",
						detailmap.get("ORIGIN_COUNTRY"));
				Country country = null;
				if (list.size() > 0) {
					country = (Country) list.get(0);
				}
				/**
				 * 版本
				 */
				// String version = detailmap.get("EXG_VERSION");
				if (projectType == ProjectType.DZSC) {
					bgddetailinfo = new DzscCustomsDeclarationCommInfo();

				} else if (projectType == ProjectType.BCUS) {

					bgddetailinfo = new CustomsDeclarationCommInfo();

				} else if (projectType == ProjectType.BCS) {

					bgddetailinfo = new BcsCustomsDeclarationCommInfo();
				}
				bgddetailinfo.setBaseCustomsDeclaration(bgdhead);
				/**
				 * 合同对应序号
				 */
				if (strcontract_item == null || strcontract_item.equals("")) {
					bgddetailinfo.setCommSerialNo(null);
				} else {
					bgddetailinfo.setCommSerialNo(Integer
							.valueOf(strcontract_item));
				}

				System.out.println(" 合同对应 序号   >>>>>>  "
						+ bgddetailinfo.getCommSerialNo());

				System.out.println("+++++++++++++++++++++++++++++++++++=");

				/**
				 * 项号
				 */
				String strnum = detailmap.get("G_NO");
				if (strnum.equals(""))
					strnum = "0";
				bgddetailinfo.setSerialNumber(Integer.valueOf(strnum));
				/**
				 * 商品信息
				 */
				String CODE_S = detailmap.get("CODE_S");
				String CODE_T = detailmap.get("CODE_T");
				if (CODE_T == null || "".equals(CODE_T.trim())) {

					throw new RuntimeException("第" + (i + 1) + "项商品编码为空");
				}
				if (!CODE_S.trim().equals("")) {
					CODE_T = CODE_T + CODE_S;
				} else {
					CODE_T = CODE_T + "00";
				}
				list = findClass("Complex", "code", CODE_T);
				if (list.size() > 0) {
					Complex com = (Complex) list.get(0);
					bgddetailinfo.setComplex(com);
				} else {
					list = findClass("CustomsComplex", "code", CODE_T);
					if (list.size() > 0) {
						CustomsComplex customsComplex = (CustomsComplex) list
								.get(0);
						Complex complex = new Complex();
						try {
							PropertyUtils.copyProperties(complex,
									customsComplex);
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
						complex.setId(complex.getCode());
						this.baseEncDao.saveOrUpdate(complex);
						bgddetailinfo.setComplex(complex);
					} else {
						// lsError.add(this.writeLoadBGDErrorInfo(projectType,
						// seqNo, zDate, impExpFlag, "企业自用商品编码中没有商品编码"
						// + CODE_T));
						// return;
						throw new RuntimeException("企业自用商品编码中没有商品编码" + CODE_T);
					}
				}
				/**
				 * 名称
				 */
				bgddetailinfo.setCommName(detailmap.get("G_NAME"));

				/**
				 * 规格型号 --特殊报关单没有手册规格
				 */
				if (bgdhead.getImpExpFlag() == ImpExpFlag.SPECIAL) {
					bgddetailinfo.setCommSpec(detailmap.get("G_MODEL"));
				} else {
					bgddetailinfo.setCommSpec(map.get(bgddetailinfo
							.getCommSerialNo()));
				}

				System.out.println("bgddetailinfo.getCommSerialNo()====="
						+ bgddetailinfo.getCommSerialNo());

				/**
				 * 规范申报规格
				 */
				bgddetailinfo.setDeclareSpec(detailmap.get("G_MODEL"));

				/**
				 * 数量
				 */
				String strQty = detailmap.get("QTY_1");
				if (strQty.equals(""))
					strQty = "0";
				Double qty = Double.valueOf(strQty);

				// 根据参数设置，在导入时进行数量超量检查
				if (Boolean
						.valueOf(customAmountOut == null ? "false"
								: ((customAmountOut.getNameValues() == null || ""
										.equals(customAmountOut.getNameValues())) ? "false"
										: "true"))) {

				} else {
					if (projectType == ProjectType.DZSC) {
						DzscContractExeInfo info = ((DzscContractExeLogic) this)
								.findDzscContractExeInfo(isImport, impexptype,
										tradeCode, contract,
										bgddetailinfo.getCommSerialNo());
						if (qty > info.getCurrentRemain()) {
							throw new RuntimeException("导入的商品'"
									+ bgddetailinfo.getCommName()
									+ "'数量大于当前余量，不能导入");
						}
					}
				}

				bgddetailinfo.setCommAmount(qty);

				/**
				 * 单位
				 */
				list = findClass("Unit", "code", detailmap.get("UNIT_1"));
				if (list.size() > 0) {
					Unit unit = (Unit) list.get(0);
					bgddetailinfo.setUnit(unit);
				}
				/**
				 * 第一法定数量
				 */
				String GQty1 = detailmap.get("G_QTY");
				if (GQty1.equals(""))
					GQty1 = "0";
				bgddetailinfo.setFirstAmount(Double.valueOf(GQty1));

				/**
				 * 第一法定单位
				 */
				list = findClass("Unit", "code", detailmap.get("G_UNIT"));
				if (list.size() > 0) {
					Unit Gunit1 = (Unit) list.get(0);
					bgddetailinfo.setLegalUnit(Gunit1);
				}
				/**
				 * 原产国
				 */
				// list = findClass("Country", "code", detailmap
				// .get("ORIGIN_COUNTRY"));
				// if (list.size() > 0) {
				// Country country = (Country) list.get(0);
				bgddetailinfo.setCountry(country);
				// }

				/**
				 * 第二法定单位数量
				 */
				String StrQty2 = detailmap.get("QTY_2");
				if (StrQty2.equals(""))
					StrQty2 = "0";
				bgddetailinfo.setSecondAmount(Double.valueOf(StrQty2));

				/**
				 * 第二法定单位
				 */
				list = findClass("Unit", "code", detailmap.get("UNIT_2"));
				if (list.size() > 0) {
					Unit Gunit2 = (Unit) list.get(0);
					bgddetailinfo.setSecondLegalUnit(Gunit2);
				}
				/**
				 * 征减免税方式
				 */
				list = findClass("LevyMode", "code", detailmap.get("DUTY_MODE"));
				if (list.size() > 0) {
					LevyMode levymode = (LevyMode) list.get(0);
					bgddetailinfo.setLevyMode(levymode);
				}

				/**
				 * 用途
				 */
				list = findClass("Uses", "code", detailmap.get("USE_TO"));
				if (list.size() > 0) {
					Uses user = (Uses) list.get(0);
					bgddetailinfo.setUses(user);
				}
				/**
				 * 货号
				 */
				bgddetailinfo.setMaterielNo(detailmap.get("EXG_NO"));

				/**
				 * 版本
				 */
				bgddetailinfo.setVersion(detailmap.get("EXG_VERSION"));

				/**
				 * 单价
				 */
				String strprice = detailmap.get("DECL_PRICE");
				if (strprice == null || strprice.equals(""))
					strprice = "0";
				bgddetailinfo.setCommUnitPrice(Double.valueOf(strprice));

				/**
				 * 总价
				 */
				String strtotal = detailmap.get("DECL_TOTAL");
				if (strtotal == null || strtotal.equals(""))
					strtotal = "0";
				bgddetailinfo.setCommTotalPrice(Double.valueOf(strtotal));

				/**
				 * 件数
				 */
				String detailcommoditynum = detailmap.get("PACK_NO");
				if (detailcommoditynum == null || detailcommoditynum.equals(""))
					detailcommoditynum = "0";
				bgddetailinfo.setPieces(Integer.valueOf(detailcommoditynum));

				/**
				 * 包装种类
				 */
				list = findClass("Wrap", "code", detailmap.get("WRAP_TYPE"));
				if (list.size() > 0) {
					Wrap wrap = (Wrap) list.get(0);
					bgddetailinfo.setWrapType(wrap);
				}
				/**
				 * 毛重
				 */
				String detailstrgrosswt = detailmap.get("GROSS_WT");
				if (detailstrgrosswt == null || detailstrgrosswt.equals(""))
					detailstrgrosswt = "0";
				bgddetailinfo.setGrossWeight(Double.valueOf(detailstrgrosswt));

				/**
				 * 净重
				 */
				String detailstrnetwt = detailmap.get("NET_WT");
				if (detailstrnetwt == null || detailstrnetwt.equals(""))
					detailstrnetwt = "0";
				bgddetailinfo.setNetWeight(Double.valueOf(detailstrnetwt));

				/**
				 * 事业部
				 */
				list = findClass("ProjectDept", "code",
						detailmap.get("PROJECT_DEPT"));
				if (list.size() > 0) {
					ProjectDept projectDept = (ProjectDept) list.get(0);
					bgddetailinfo.setProjectDept(projectDept);
				}
				/**
				 * 详细规格型号 2010-08-11 hcl
				 */
				bgddetailinfo.setDetailNote(detailmap.get("EXTEND_FIELD"));
				bgddetailinfo.setCompany(CommonUtils.getCompany());

				// 设置报关单商品信息创建时间
				if (bgddetailinfo.getCreateDate() == null
						|| "".equals(bgddetailinfo.getCreateDate())) {
					SimpleDateFormat defaulDateFormat = new SimpleDateFormat(
							"yyyy-MM-dd");
					String defaulDate = defaulDateFormat.format(new Date());
					bgddetailinfo.setCreateDate(java.sql.Date
							.valueOf(defaulDate));
				}
				this.baseEncDao.saveCustomsDeclarationCommInfo(bgddetailinfo);
			}
		} catch (Exception ex) {
			if (isFromFile) {
				String sImpExp = "";
				if (0 == impExpFlag) {
					sImpExp = "进口";
				} else if (1 == impExpFlag) {
					sImpExp = "出口";
				} else if (2 == impExpFlag) {
					sImpExp = "特殊";
				}
				throw new RuntimeException("导入" + sImpExp + "报关单文件" + seqNo
						+ ".xml发生错误:" + ex.getMessage());
			} else {
				lsError.add(this.writeLoadBGDErrorInfo(projectType, seqNo,
						zDate, impExpFlag, ex.getMessage()));
				ex.printStackTrace();
				return;
			}

		}
		lsSuccess.add(this.writeLoadBGDLogInfo(projectType, seqNo, zDate,
				impExpFlag, bgdhead.getCustomsDeclarationCode(),
				bgdhead.getSerialNumber()));
	}

	/**
	 * 
	 * @param projectType
	 * @param bgdhead
	 * @return
	 */
	public Map<Integer, String> getCommSpec(Integer projectType,
			BaseCustomsDeclaration bgdhead) {

		Map<Integer, String> map = new HashMap<Integer, String>();

		List list = baseEncDao.findContractHead(projectType, bgdhead);

		if (list != null && list.size() > 0) {

			BaseEmsHead baseContractHead = (BaseEmsHead) list.get(0);// 获取通关备案表头

			boolean boo = isMaterial1(bgdhead.getImpExpType());// 判断是料件还是成品：true
																// 为料件 false 为成品
			List ls = null;

			if (boo) {
				ls = baseEncDao.findBaseEmsImg(projectType, baseContractHead);// 获取料架数据
			} else {
				ls = baseEncDao.findBaseEmsExg(projectType, baseContractHead);// 获取成品数据
			}

			for (int i = 0; i < ls.size(); i++) {
				Integer key = null;
				String value = null;

				if (boo) {
					if (ls.get(i) instanceof BaseEmsImg) {
						BaseEmsImg baseEmsImg = (BaseEmsImg) ls.get(i);
						key = baseEmsImg.getSeqNum();
						value = baseEmsImg.getSpec();
					} else {
						DzscEmsImgBill emsImg = (DzscEmsImgBill) ls.get(i);
						key = emsImg.getSeqNum();
						value = emsImg.getSpec();
					}
				} else {
					if (ls.get(i) instanceof BaseEmsExg) {
						BaseEmsExg baseEmsExg = (BaseEmsExg) ls.get(i);
						key = baseEmsExg.getSeqNum();
						value = baseEmsExg.getSpec();
					} else {
						DzscEmsExgBill emsExg = (DzscEmsExgBill) ls.get(i);
						key = emsExg.getSeqNum();
						value = emsExg.getSpec();
					}
				}
				System.out.println("key=======" + key + "     value====="
						+ value);
				System.out.println();

				if (map.get(key) == null) {
					map.put(key, value);
				}
			}
		}

		return map;
	}

	/**
	 * 是料件还是成品
	 * 
	 * @param impExpType
	 * @return
	 */
	public boolean isMaterial1(int impExpType) {

		boolean isMaterial = false;

		// 获取料件类型
		int materielType = getMaterielTypeByBillType1(impExpType);

		// 判断是否主料
		if (materielType == Integer.parseInt(MaterielType.MATERIEL)) {

			isMaterial = true;

			// 判断是否成品
		} else if (materielType == Integer
				.parseInt(MaterielType.FINISHED_PRODUCT)) {

			isMaterial = false;
		}

		// 不符合 主料或成品 就返回成品
		return isMaterial;
	}

	/**
	 * 获得料件成品标识来自进出口申请单类型
	 */
	public int getMaterielTypeByBillType1(int billType) {
		int temp = Integer.parseInt(MaterielType.MATERIEL);
		switch (billType) {
		case ImpExpType.DIRECT_IMPORT:
		case ImpExpType.TRANSFER_FACTORY_IMPORT:
		case ImpExpType.GENERAL_TRADE_IMPORT:
		case ImpExpType.BACK_MATERIEL_EXPORT:
		case ImpExpType.REMIAN_MATERIAL_BACK_PORT:
		case ImpExpType.REMAIN_FORWARD_EXPORT:
		case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES:
		case ImpExpType.REMAIN_FORWARD_IMPORT:
			temp = Integer.parseInt(MaterielType.MATERIEL);
			break;
		case ImpExpType.BACK_FACTORY_REWORK:
		case ImpExpType.DIRECT_EXPORT:
		case ImpExpType.TRANSFER_FACTORY_EXPORT:
		case ImpExpType.REWORK_EXPORT:
		case ImpExpType.GENERAL_TRADE_EXPORT:
			temp = Integer.parseInt(MaterielType.FINISHED_PRODUCT);
			break;
		}
		return temp;
	}

	public BaseCustomsDeclaration getProject(int projectType) {
		if (ProjectType.BCUS == projectType) {
			new CustomsDeclaration();
		} else if (ProjectType.BCS == projectType) {
			new BcsCustomsDeclaration();
		} else if (ProjectType.DZSC == projectType) {
			new DzscCustomsDeclaration();
		}
		return null;
	}

	/**
	 * 回写导入报关单日志信息
	 * 
	 * @param projectType
	 *            项目类型
	 * @param seqNo
	 *            报关单xml文件名
	 * @param zDate
	 *            报关单申报日期
	 * @param impExpFlag
	 *            进出口标志
	 * @param customsDeclarationCode
	 *            报关单号码
	 * @param serialNumber
	 *            报关单流水号
	 * @return BaseLoadBGDFromQPXml 从QP中导入报关单到ＪＢＣＵＳ系统中的记录
	 */
	private BaseLoadBGDFromQPXml writeLoadBGDLogInfo(int projectType,
			String seqNo, Date zDate, Integer impExpFlag,
			String customsDeclarationCode, Integer serialNumber) {
		BaseLoadBGDFromQPXml loadXmlInfo = null;
		if (projectType == ProjectType.DZSC) {
			loadXmlInfo = new DzscLoadBGDFromQPXml();
		} else if (projectType == ProjectType.BCUS) {
			loadXmlInfo = new LoadBGDFromQPXml();
		} else if (projectType == ProjectType.BCS) {
			loadXmlInfo = new BcsLoadBGDFromQPXml();
		} else {
			throw new RuntimeException("不识别系统类型");
		}
		loadXmlInfo.setFileName(seqNo);
		loadXmlInfo.setImpExpFlag(impExpFlag);
		loadXmlInfo.setDate(zDate);
		loadXmlInfo.setCustomsDeclarationCode(customsDeclarationCode);
		loadXmlInfo.setSerialNumber(serialNumber);
		this.baseEncDao.saveOrUpdate(loadXmlInfo);
		return loadXmlInfo;
	}

	/**
	 * 回写导入报关单错误日志信息
	 * 
	 * @param projectType
	 *            项目类型
	 * @param seqNo
	 *            报关单xml文件名
	 * @param zDate
	 *            报关单申报日期
	 * @param impExpFlag
	 *            进出口标志
	 * @param errorInfo
	 *            错误信息
	 * @return TempLoadBGDFromQPXmlErrorInfo 从QP中导入报关单到ＪＢＣＵＳ系统中的记录临时类
	 */
	private TempLoadBGDFromQPXmlErrorInfo writeLoadBGDErrorInfo(
			int projectType, String seqNo, Date zDate, Integer impExpFlag,
			String errorInfo) {
		TempLoadBGDFromQPXmlErrorInfo loadXmlInfo = new TempLoadBGDFromQPXmlErrorInfo();
		loadXmlInfo.setFileName(seqNo);
		loadXmlInfo.setImpExpFlag(impExpFlag);
		loadXmlInfo.setDate(zDate);
		loadXmlInfo.setErrorInfo(errorInfo);
		// this.baseEncDao.saveOrUpdate(loadXmlInfo);
		return loadXmlInfo;
	}

	/**
	 * 取得存放从QP中导出报关单的目录
	 * 
	 * @return
	 */
	protected abstract String getQPBGDXmlPath();

	/**
	 * 取得存放从QP中导出报关单的目录
	 * 
	 * @return
	 */
	protected abstract CspParameterSet getCspParameterSet();

	/**
	 * 导入报关单资料
	 * 
	 * @param isImportBGD
	 *            为进口还是出口报关单
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return Map 导入日志信息
	 */
	public Map loadBGDFromQPXml(ImportBGDCondition condition) {
		int projectType = this.baseEncDao.getProjectType();
		String path = this.getQPBGDXmlPath();

		if (path == null || path.equals("")) {
			throw new RuntimeException("没有设定导入报关单文件存放路径");
		}
		List<File> lsFiles = getAllXmlFiles(path, projectType,
				condition.isImportBGD(), condition.getBeginDate(),
				condition.getEndDate());
		System.out.println("文件size=" + lsFiles.size());
		List<BaseLoadBGDFromQPXml> lsSuccess = new ArrayList<BaseLoadBGDFromQPXml>();
		List<TempLoadBGDFromQPXmlErrorInfo> lsError = new ArrayList<TempLoadBGDFromQPXmlErrorInfo>();
		for (File file : lsFiles) {
			if (isxmlfile(file)) {
				// String content = readXmlData(file);
				// System.out.println("content=" + content);
				SAXBuilder sax = new SAXBuilder();
				InputStream inputStream = null;
				try {
					inputStream = new FileInputStream(file);
					Document doc = sax.build(inputStream);
					Element root = doc.getRootElement();
					if (condition.isImportBGD()) {
						loadImpExpBGD(root, true, projectType, true,
								"DEC_I_HEAD", "DEC_I_LIST", "DEC_I_CONTAINER",
								"DEC_I_LICENSEDOCU", "DEC_I_FREETXT",
								lsSuccess, lsError, condition);// 进口报关单
					} else {
						loadImpExpBGD(root, true, projectType, false,
								"DEC_E_HEAD", "DEC_E_LIST", "DEC_E_CONTAINER",
								"DEC_E_LICENSEDOCU", "DEC_E_FREETXT",
								lsSuccess, lsError, condition);// 出口报关单
					}
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				} finally {
					if (inputStream != null) {
						try {
							inputStream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		Map<Integer, List> map = new HashMap<Integer, List>();
		map.put(0, lsSuccess);
		map.put(-1, lsError);
		return map;
	}

	/**
	 * 导入报关单资料
	 * 
	 * @param isImportBGD
	 *            为进口还是出口报关单
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return Map 导入日志信息
	 */
	public Map loadBGDFromQPDirect(ImportBGDCondition condition) {

		int projectType = this.baseEncDao.getProjectType();

		// 获取直接导入 参数
		CspParameterSet paraSet = this.getCspParameterSet();

		// 报关单 qp 执行对象
		DecQpAction decQpAction = DecQpServiceClient.getDecQpAction(paraSet);

		List<String> lsContents = new ArrayList<String>();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		String ownerCode = ((Company) CommonUtils.getCompany()).getCode();

		if (condition.isImportBGD()) {

			// 下载 ------进口报关单
			lsContents = decQpAction.download_I_Dec(
					paraSet.getRemoteHostICPwd(),
					ownerCode,
					condition.getBeginDate() == null ? null : dateFormat
							.format(condition.getBeginDate()),
					condition.getEndDate() == null ? null : dateFormat
							.format(condition.getEndDate()));

		} else {

			// 下载 ------ 出口报关单
			lsContents = decQpAction.download_E_Dec(
					paraSet.getRemoteHostICPwd(),
					ownerCode,
					condition.getBeginDate() == null ? null : dateFormat
							.format(condition.getBeginDate()),
					condition.getEndDate() == null ? null : dateFormat
							.format(condition.getEndDate()));
		}

		if (lsContents.size() > 0) {

			// 判断远程服务是否发生错误
			String info = lsContents.get(0);

			if (info == null) {
				throw new RuntimeException("从远程服务器读取的报关单内容为空");
			}

			if (info.indexOf("网络异常") >= 0) {
				throw new RuntimeException("远程服务发生错误，错误信息如下：\n" + info);
			}
		}

		for (int i = 0; i < lsContents.size(); i++) {

			System.out
					.println("------------------- 这里 显示 remote 导入的 xml 内容 ------------");

			System.out.println(lsContents.get(i));

			System.out
					.println("================================================");

		}

		/*
		 * 下载回来的报关单 : 是一份 xml 文件 解析xml文件导入
		 */
		List<BaseLoadBGDFromQPXml> lsSuccess = new ArrayList<BaseLoadBGDFromQPXml>();

		List<TempLoadBGDFromQPXmlErrorInfo> lsError = new ArrayList<TempLoadBGDFromQPXmlErrorInfo>();

		for (String content : lsContents) {

			if (content == null || "".equals(content.trim())) {
				continue;
			}

			if (!content.contains("<ROOT>")) {
				throw new RuntimeException("下载的报关单报文有误，" + content);
			}

			SAXBuilder sax = new SAXBuilder();

			InputStream inputStream = null;

			try {

				inputStream = new ByteArrayInputStream(filter(content.trim())
						.getBytes("utf-8"));

				Document doc = sax.build(inputStream);

				Element root = doc.getRootElement();

				if (condition.isImportBGD()) {

					loadImpExpBGD(root, false, projectType, true, "DEC_I_HEAD",
							"DEC_I_LIST", "DEC_I_CONTAINER",
							"DEC_I_LICENSEDOCU", "DEC_I_FREETXT", lsSuccess,
							lsError, condition);// 进口报关单

				} else {
					loadImpExpBGD(root, false, projectType, false,
							"DEC_E_HEAD", "DEC_E_LIST", "DEC_E_CONTAINER",
							"DEC_E_LICENSEDOCU", "DEC_E_FREETXT", lsSuccess,
							lsError, condition);// 出口报关单
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e + "下载的报关单报文有误，" + content);
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		Map<Integer, List> map = new HashMap<Integer, List>();
		map.put(0, lsSuccess);
		map.put(-1, lsError);
		return map;
	}

	/**
	 * 过虑xml的无效字符。
	 * <p/>
	 * <ol>
	 * <li>0x00 - 0x08</li>
	 * <li>0x0b - 0x0c</li>
	 * <li>0x0e - 0x1f</li>
	 * </ol>
	 * 
	 * @author chenlb 2014-02-7 下午04:27:48
	 */
	private String filter(String xmlStr) {
		StringBuilder sb = new StringBuilder();
		char[] chs = xmlStr.toCharArray();
		for (char ch : chs) {
			if ((ch >= 0x00 && ch <= 0x08) || (ch >= 0x0b && ch <= 0x0c)
					|| (ch >= 0x0e && ch <= 0x1f)) {
				// eat...
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	/**
	 * 导入报关单资料
	 * 
	 * @param isImportBGD
	 *            为进口还是出口报关单
	 * @param xmlContent
	 *            xml报文内容
	 * @return Map 导入日志信息
	 */
	public Map loadBGDFromQPXMLString(boolean isImportBGD, String xmlContent) {
		int projectType = this.baseEncDao.getProjectType();
		List<BaseLoadBGDFromQPXml> lsSuccess = new ArrayList<BaseLoadBGDFromQPXml>();
		List<TempLoadBGDFromQPXmlErrorInfo> lsError = new ArrayList<TempLoadBGDFromQPXmlErrorInfo>();
		// System.out.println(content);
		if (xmlContent != null && !"".equals(xmlContent.trim())) {
			if (!xmlContent.contains("<ROOT>")) {
				throw new RuntimeException("下载的报关单报文有误，" + xmlContent);
			}
			SAXBuilder sax = new SAXBuilder();
			InputStream inputStream = null;
			try {
				inputStream = new ByteArrayInputStream(xmlContent.trim()
						.getBytes("utf-8"));
				Document doc = sax.build(inputStream);
				Element root = doc.getRootElement();
				if (isImportBGD) {
					loadImpExpBGD(root, false, projectType, true, "DEC_I_HEAD",
							"DEC_I_LIST", "DEC_I_CONTAINER",
							"DEC_I_LICENSEDOCU", "DEC_I_FREETXT", lsSuccess,
							lsError, null);// 进口报关单
				} else {
					loadImpExpBGD(root, false, projectType, false,
							"DEC_E_HEAD", "DEC_E_LIST", "DEC_E_CONTAINER",
							"DEC_E_LICENSEDOCU", "DEC_E_FREETXT", lsSuccess,
							lsError, null);// 出口报关单
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		Map<Integer, List> map = new HashMap<Integer, List>();
		map.put(0, lsSuccess);
		map.put(-1, lsError);
		return map;
	}

	public String buildXML(String headStr, String bodyStr) {
		String[] headDatas = headStr.split("\\|\\|");
		String[] bodyDatas = bodyStr.split("!!");

		boolean isImport = "I".equals(getImpExpFlag(headDatas[1]));

		/*
		 * 准备生成xml的数据，放入 dataMap
		 */

		List<LabelValue> dataMap = new ArrayList<LabelValue>();
		// 生成head
		List<LabelValue> headMap = new ArrayList<LabelValue>();
		dataMap.add(new LabelValue("DEC_I_HEAD", headMap));
		{
			// AGENT_CODE 申报单位代码
			headMap.add(new LabelValue("AGENT_CODE", headDatas[18]));

			// AGENT_NAME 申报单位名称
			headMap.add(new LabelValue("AGENT_NAME", headDatas[19]));

			// APPR_NO 批准文号(核销单号)
			headMap.add(new LabelValue("APPR_NO", headDatas[34]));

			// BILL_NO 提单号/运单号
			headMap.add(new LabelValue("BILL_NO", headDatas[8]));

			// CERT_MARK 随附单据
			headMap.add(new LabelValue("CERT_MARK", headDatas[42]));

			// CO_OWNER
			headMap.add(new LabelValue("CO_OWNER", ""));

			// CONTR_NO 合同协议号
			headMap.add(new LabelValue("CONTR_NO", headDatas[5]));

			// CUSTOM_MASTER 主管海关
			headMap.add(new LabelValue("CUSTOM_MASTER", headDatas[3]));

			// CUT_MODE 征免性质
			headMap.add(new LabelValue("CUT_MODE", headDatas[26]));

			// D_DATE 申报日期
			headMap.add(new LabelValue("D_DATE", formatDate(headDatas[7])));

			// DEC_PORT
			headMap.add(new LabelValue("DEC_PORT", ""));

			// DISTINATE_PORT 装货港
			headMap.add(new LabelValue("DISTINATE_PORT", headDatas[24]));

			// DISTRICT_CODE 境内目的地
			headMap.add(new LabelValue("DISTRICT_CODE", headDatas[20]));

			// EX_SOURCE
			headMap.add(new LabelValue("EX_SOURCE", ""));

			// FEE_CURR 运费币制
			headMap.add(new LabelValue("FEE_CURR", headDatas[29]));

			// FEE_MARK 运费标记
			headMap.add(new LabelValue("FEE_MARK", headDatas[27]));

			// FEE_RATE 运费/率
			headMap.add(new LabelValue("FEE_RATE", headDatas[28]));

			// GROSS_WT 总毛重
			headMap.add(new LabelValue("GROSS_WT", headDatas[40]));

			// I_E_DATE 进出口日期
			headMap.add(new LabelValue("I_E_DATE", formatDate(headDatas[6])));

			// I_E_PORT 进出口岸
			headMap.add(new LabelValue("I_E_PORT", headDatas[4]));

			// IN_RATIO
			headMap.add(new LabelValue("IN_RATIO", ""));

			// INSUR_CURR 保费币制
			headMap.add(new LabelValue("INSUR_CURR", headDatas[33]));

			// INSUR_MARK 保费标记
			headMap.add(new LabelValue("INSUR_MARK", headDatas[31]));

			// INSUR_RATE 保费/率
			headMap.add(new LabelValue("INSUR_RATE", headDatas[32]));

			// ITEMS_NO
			headMap.add(new LabelValue("ITEMS_NO", ""));

			// LICENSE_NO 许可证编号
			headMap.add(new LabelValue("LICENSE_NO", headDatas[30]));

			// MANUAL_NO 手册/账册号
			headMap.add(new LabelValue("MANUAL_NO", headDatas[2]));

			// MOD_NUM
			headMap.add(new LabelValue("MOD_NUM", ""));

			// NET_WT 总净重
			headMap.add(new LabelValue("NET_WT", headDatas[41]));

			// NOTE_S 备注
			headMap.add(new LabelValue("NOTE_S", headDatas[43]));

			// OTHER_CURR 杂费币制
			headMap.add(new LabelValue("OTHER_CURR", headDatas[37]));

			// OTHER_MARK 杂费标记
			headMap.add(new LabelValue("OTHER_MARK", headDatas[35]));

			// OTHER_RATE 杂费/率
			headMap.add(new LabelValue("OTHER_RATE", headDatas[36]));

			// OWNER_CODE 货主单位代码
			headMap.add(new LabelValue("OWNER_CODE", headDatas[16]));

			// OWNER_NAME 货主单位名称
			headMap.add(new LabelValue("OWNER_NAME", headDatas[17]));

			// P_DATE
			headMap.add(new LabelValue("P_DATE", formatDate("")));

			// PACK_NO 件数
			headMap.add(new LabelValue("PACK_NO", headDatas[39]));

			// PAY_MODE 收结汇方式
			headMap.add(new LabelValue("PAY_MODE", headDatas[12]));

			// PAY_WAY 结汇方式
			headMap.add(new LabelValue("PAY_WAY", headDatas[12]));

			// PAYMENT_MARK
			headMap.add(new LabelValue("PAYMENT_MARK", ""));

			// SEQ_NO 统一编号
			headMap.add(new LabelValue("SEQ_NO", headDatas[0]));

			// SERVICE_FEE
			headMap.add(new LabelValue("SERVICE_FEE", ""));

			// TRADE_CO 经营单位代码
			headMap.add(new LabelValue("TRADE_CO", headDatas[14]));

			// TRADE_COUNTRY 贸易国别/起运国
			headMap.add(new LabelValue("TRADE_COUNTRY", headDatas[21]));

			// TRADE_MODE 监管方式/贸易方式
			headMap.add(new LabelValue("TRADE_MODE", headDatas[23]));

			// TRADE_NAME 经营单位名称
			headMap.add(new LabelValue("TRADE_NAME", headDatas[15]));

			// TRAF_MODE 运输方式
			headMap.add(new LabelValue("TRAF_MODE", headDatas[9]));

			// TRAF_NAME 运输工具名称
			headMap.add(new LabelValue("TRAF_NAME", headDatas[10]));

			// TRANS_MODE 成交方式
			headMap.add(new LabelValue("TRANS_MODE", headDatas[25]));

			// TYPIST_NO
			headMap.add(new LabelValue("TYPIST_NO", ""));

			// TYPE_ID
			headMap.add(new LabelValue("TYPE_ID", ""));

			// WRAP_TYPE 包装种类
			headMap.add(new LabelValue("WRAP_TYPE", headDatas[38]));

			// ENTRY_ID 报关单号
			headMap.add(new LabelValue("ENTRY_ID", headDatas[1]));

			// PRE_ENTRY_ID 预录入编号
			headMap.add(new LabelValue("PRE_ENTRY_ID", headDatas[1]));

			// JZXSL
			headMap.add(new LabelValue("JZXSL", ""));

			// BONDED_NO
			headMap.add(new LabelValue("BONDED_NO", ""));

			// BP_NO
			headMap.add(new LabelValue("BP_NO", ""));

			// DECL_PORT 申报口岸
			headMap.add(new LabelValue("DECL_PORT", ""));

			// EDI_ID 报关标志
			headMap.add(new LabelValue("EDI_ID", ""));

			// EDI_REMARK
			headMap.add(new LabelValue("EDI_REMARK", ""));

			// I_E_FLAG 进出口标志 报关单号的第9位：0为出口，1为进口。
			headMap.add(new LabelValue("I_E_FLAG", getImpExpFlag(headDatas[1])));

			// ID_CHK
			headMap.add(new LabelValue("ID_CHK", ""));

			// PARTNER_ID
			headMap.add(new LabelValue("PARTNER_ID", ""));

			// RELATIVE_ID
			headMap.add(new LabelValue("RELATIVE_ID", ""));

			// VOYAGE_NO
			headMap.add(new LabelValue("VOYAGE_NO", ""));

			// BOSS_ID
			headMap.add(new LabelValue("BOSS_ID", ""));

			// COP_NAME 录入单位名称
			headMap.add(new LabelValue("COP_NAME", ""));

			// COP_CODE 录入单位代码
			headMap.add(new LabelValue("COP_CODE", ""));

			// INPUTER_NAME 录入员姓名
			headMap.add(new LabelValue("INPUTER_NAME", ""));

			// ENTRY_TYPE 报关单类型
			headMap.add(new LabelValue("ENTRY_TYPE", headDatas[44]));

			// EXTEND_FIELD
			headMap.add(new LabelValue("EXTEND_FIELD", ""));
		}
		{
			String[] bodyData = null;
			for (int i = 0; i < bodyDatas.length; i++) {
				List<LabelValue> listMap = new ArrayList<LabelValue>();
				dataMap.add(new LabelValue("DEC_I_LIST", listMap));

				bodyData = bodyDatas[i].split("\\|\\|");
				// CLASS_MARK 归类标志
				listMap.add(new LabelValue("CLASS_MARK", ""));

				// CODE_S 附加编号
				listMap.add(new LabelValue("CODE_S", bodyData[4]));

				// CODE_T 商品编号
				listMap.add(new LabelValue("CODE_T", bodyData[3]));

				// CONTR_ITEM 合同对应序号
				listMap.add(new LabelValue("CONTR_ITEM", bodyData[2]));

				// DECL_PRICE 申报单价
				listMap.add(new LabelValue("DECL_PRICE", bodyData[15]));

				// DUTY_MODE 征免方式
				listMap.add(new LabelValue("DUTY_MODE", bodyData[18]));

				// FACTOR
				listMap.add(new LabelValue("FACTOR", ""));

				// G_MODEL 规格型号
				listMap.add(new LabelValue("G_MODEL", bodyData[6]));

				// G_NAME 商品名称
				listMap.add(new LabelValue("G_NAME", bodyData[5]));

				// G_NO 商品序号
				listMap.add(new LabelValue("G_NO", bodyData[1]));

				// G_UNIT 第一法定单位
				listMap.add(new LabelValue("G_UNIT", bodyData[10]));

				// MOD_NUM
				listMap.add(new LabelValue("MOD_NUM", ""));

				// ORIGIN_COUNTRY 原产地
				listMap.add(new LabelValue("ORIGIN_COUNTRY", bodyData[13]));

				// G_QTY 第一法定数量
				listMap.add(new LabelValue("G_QTY", bodyData[9]));

				// QTY_2 第二法定数量
				listMap.add(new LabelValue("QTY_2", bodyData[11]));

				// QTY_1 数量
				listMap.add(new LabelValue("QTY_1", bodyData[7]));

				// SEQ_NO
				listMap.add(new LabelValue("SEQ_NO", bodyData[0]));

				// TRADE_CURR 币制
				listMap.add(new LabelValue("TRADE_CURR", bodyData[14]));

				// DECL_TOTAL 总价
				listMap.add(new LabelValue("DECL_TOTAL", bodyData[16]));

				// UNIT_1 单位
				listMap.add(new LabelValue("UNIT_1", bodyData[8]));

				// UNIT_2 第二法定单位
				listMap.add(new LabelValue("UNIT_2", bodyData[12]));

				// USE_TO 用途
				listMap.add(new LabelValue("USE_TO", ""));

				// WORK_USD 生产厂家
				listMap.add(new LabelValue("WORK_USD", bodyData[17]));

				// EXG_NO
				listMap.add(new LabelValue("EXG_NO", ""));

				// EXG_VERSION 版本号
				listMap.add(new LabelValue("EXG_VERSION", ""));

				// EXTEND_FIELD
				listMap.add(new LabelValue("EXTEND_FIELD", ""));
			}
		}
		List<LabelValue> ContainerMap = new ArrayList<LabelValue>();
		dataMap.add(new LabelValue("DEC_I_CONTAINER", ContainerMap));
		{
			// SEQ_NO0200039127SEQ_NO
			ContainerMap.add(new LabelValue("SEQ_NO", ""));
			// CONTAINER_NO
			ContainerMap.add(new LabelValue("CONTAINER_NO", ""));
			// CONTAINER_ID
			ContainerMap.add(new LabelValue("CONTAINER_ID", ""));
			// CONTAINER_MD
			ContainerMap.add(new LabelValue("CONTAINER_MD", ""));
			// CONTAINER_WT
			ContainerMap.add(new LabelValue("CONTAINER_WT", ""));
			// NOTE_S
			ContainerMap.add(new LabelValue("NOTE_S", ""));
		}
		headMap.add(new LabelValue("CUSTOM_MASTER", ""));
		List<LabelValue> FreetxtMap = new ArrayList<LabelValue>();
		dataMap.add(new LabelValue("DEC_I_FREETXT", FreetxtMap));
		{
			// SEQ_NO
			FreetxtMap.add(new LabelValue("SEQ_NO", ""));
			// FREE_TEXT
			List<LabelValue> TextsMap = new ArrayList<LabelValue>();
			FreetxtMap.add(new LabelValue("FREE_TEXT", TextsMap));
			{
				// RELID
				TextsMap.add(new LabelValue("RELID", ""));
				// RELMANNO
				TextsMap.add(new LabelValue("RELMANNO", ""));
				// BONNO
				TextsMap.add(new LabelValue("BONNO", ""));
				// VOYNO
				TextsMap.add(new LabelValue("VOYNO", ""));
				// DECBPNO
				TextsMap.add(new LabelValue("DECBPNO", ""));
				// CUSFIE
				TextsMap.add(new LabelValue("CUSFIE", ""));
				// DECNO
				TextsMap.add(new LabelValue("DECNO", ""));
				// TYPCLIENTID
				TextsMap.add(new LabelValue("TYPCLIENTID", ""));
			}
		}
		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
		LabelValue l = new LabelValue("ROOT", dataMap);
		sb.append(CommonUtils.coverter(l, null));
		Map map = loadBGDFromQPXMLString(isImport, sb.toString());
		// List<BaseLoadBGDFromQPXml> success = (List) map.get(0);
		List<TempLoadBGDFromQPXmlErrorInfo> error = (List) map.get(-1);
		String msg = null;
		if (error.size() > 0) {
			msg = "报关单【" + headDatas[1] + "】导入失败！失败原因："
					+ error.get(0).getErrorInfo();
		} else {
			msg = "报关单【" + headDatas[1] + "】导入成功！";
		}

		return msg;
	}

	private String getImpExpFlag(String code) {
		String flag = code.substring(8, 9);
		if ("0".equals(flag)) {
			flag = "E";
		} else {
			flag = "I";
		}

		return flag;
	}

	private String formatDate(String date) {
		if (date != null && date.length() == 8) {
			date = date.substring(0, 4) + "-" + date.substring(4, 6) + "-"
					+ date.substring(6, 8) + " 00:00:00";
		}

		return date;
	}

	/**
	 * 查询实体类
	 * 
	 * @param classname
	 *            类名
	 * @param Fieldname
	 *            字段名
	 * @param value
	 *            值
	 * @return List 返回的类名
	 */
	private List findClass(String classname, String Fieldname, String value) {
		String hsql = "select a from " + classname + " a where a." + Fieldname
				+ " = ?";
		try {
			return this.baseEncDao.find(hsql, new Object[] { value });
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 统计设备的合同定量、报关已出数量、剩余可进数量
	 * 
	 * @param basecustomsDeclarationCommInfo
	 *            内部商品新增报关单表体
	 * @return List 为BaseCustomsDeclarationCommInfo型,内部商品新增报关单表体
	 */
	public List statisticsFixInCommInfo(
			BaseCustomsDeclarationCommInfo basecustomsDeclarationCommInfo) {
		// 设备表体中的数量
		Double rationAmount = baseEncDao
				.findFixItemAmount(basecustomsDeclarationCommInfo);

		List list = baseEncDao
				.findCustomsDeclarationCommInfo(basecustomsDeclarationCommInfo);

		Double inAmount = 0.0;// 进口数量
		Double outAmount = 0.0;// 已出口数量
		// Double leftAmount = 0.0;// 可进口量
		for (int i = 0; i < list.size(); i++) {
			BaseCustomsDeclarationCommInfo baseCommInfo = (BaseCustomsDeclarationCommInfo) list
					.get(i);
			Integer type = baseCommInfo.getBaseCustomsDeclaration()
					.getImpExpType();
			if (type == ImpExpType.EQUIPMENT_IMPORT) {
				inAmount += (baseCommInfo.getCommAmount() == null ? 0.0
						: baseCommInfo.getCommAmount());
			} else if (type == ImpExpType.BACK_PORT_REPAIR
					|| type == ImpExpType.EQUIPMENT_BACK_PORT) {
				inAmount -= (baseCommInfo.getCommAmount() == null ? 0.0
						: baseCommInfo.getCommAmount());
				outAmount += (baseCommInfo.getCommAmount() == null ? 0.0
						: baseCommInfo.getCommAmount());
			}

		}
		// leftAmount = rationAmount - inAmount;

		List arrayList = new ArrayList();
		arrayList.add(rationAmount);
		arrayList.add(inAmount);
		arrayList.add(outAmount);
		return arrayList;

	}

	/**
	 * 获取汇率
	 * 
	 * @param currs
	 *            币制
	 * @param date
	 *            日期
	 * @param emsno
	 *            手册/帐册号
	 * @return Double 返回汇率
	 */
	public Double getCurrRateByCurr(Curr currs, Date date, String emsno) {
		if (currs == null) {
			return null;
		}
		String currCode = null;
		List dList = this.baseEncDao.findAllEmsHeadH2k(emsno);
		for (int j = 0; j < dList.size(); j++) {
			if (j == 1) {
				break;
			}
			Object obj = dList.get(j);
			if (obj instanceof Contract) {
				Contract bx = (Contract) obj;
				currCode = bx.getCurr() == null ? null : bx.getCurr().getCode();
			} else if (obj instanceof DzscEmsPorHead) {
				DzscEmsPorHead bx = (DzscEmsPorHead) obj;
				currCode = bx.getCurr() == null ? null : bx.getCurr().getCode();

			} else if (obj instanceof EmsHeadH2k) {
				currCode = "502";
			}
		}// 以报关单帐册号为KEY，所报关单币制代码存入
		Double dou = 0.0;
		if (currCode != null
				&& currCode.equals(currs.getCode() == null ? "" : currs
						.getCode())) {
			dou = 1.0;
		} else {
			dou = this.baseEncDao.findCurrRateByM(currCode, currs.getCode(),
					date);
		}
		return dou;
	}

	/**
	 * 出口报关单明细
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param isFtoJ
	 *            是否繁转简
	 * @return List为BaseCustomsDeclarationCommInfo型,报关单明细.
	 */
	public List customsDeclarationExport(int impExpFlag, Date beginDate,
			Date endDate, boolean isFtoJ) {
		List lsGbtobig = baseEncDao.findGbtobig("", "");
		infTojHsTable(lsGbtobig);
		List ls = this.baseEncDao.findCustomsDeclarationCommInfoForExport(
				impExpFlag, beginDate, endDate);
		for (int i = 0; i < ls.size(); i++) {
			BaseCustomsDeclarationCommInfo info = (BaseCustomsDeclarationCommInfo) ls
					.get(i);
			BaseCustomsDeclaration head = info.getBaseCustomsDeclaration();
			// if
			// (head.getImpExpFlag().equals(Integer.valueOf(ImpExpFlag.IMPORT)))
			// {
			// head.setExtendField1("进口报关单");
			// } else if (head.getImpExpFlag().equals(
			// Integer.valueOf(ImpExpFlag.EXPORT))) {
			// head.setExtendField1("出口报关单");
			// } else if (head.getImpExpFlag().equals(
			// Integer.valueOf(ImpExpFlag.SPECIAL))) {
			// head.setExtendField1("特殊报关单");
			// }
			if (isFtoJ) {
				head.setMemos(changeStr(head.getMemos()));
				head.setConveyance(changeStr(head.getConveyance()));
				head.setBillOfLading(changeStr(head.getBillOfLading()));
				// 包装种类
				Wrap w = head.getWrapType();
				if (w != null) {
					w.setName(changeStr(w.getName()));
				}
				// 录入员
				AclUser u = head.getCreater();
				if (u != null) {
					u.setUserName(changeStr(u.getUserName()));
				}
				head.setCustomser(changeStr(head.getCustomser()));
				head.setOverseasConveyanceName(changeStr(head
						.getOverseasConveyanceName()));
				head.setOverseasConveyanceBillOfLading(changeStr(head
						.getOverseasConveyanceBillOfLading()));
				head.setDomesticConveyanceName(changeStr(head
						.getDomesticConveyanceName()));

				info.setCommName(changeStr(info.getCommName()));
				info.setCommSpec(changeStr(info.getCommSpec()));
			}
		}
		return ls;
	}

	/**
	 * 繁体转简体
	 * 
	 * @param s
	 *            要转换的内容
	 * @return String 转换后的内容
	 */
	private String changeStr(String s) {
		if (s == null || s.equals("")) {
			return s;
		}
		String temp = "";
		char[] strContent = s.toCharArray();
		for (int i = 0; i < strContent.length; i++) {
			String z = String.valueOf(strContent[i]);
			if (String.valueOf(strContent[i]).getBytes().length == 2) {
				if (gbHash.get(String.valueOf(strContent[i])) != null) {
					z = (String) gbHash.get(String.valueOf(strContent[i]));
				}
			}
			temp = temp + z;
		}
		return temp;
	}

	/**
	 * 繁转简
	 * 
	 * @param list
	 *            繁简体对照表资料 LIST
	 */
	private void infTojHsTable(List list) {
		if (gbHash == null) {
			gbHash = new Hashtable();
			for (int i = 0; i < list.size(); i++) {
				Gbtobig gb = (Gbtobig) list.get(i);
				gbHash.put(gb.getBigname(), gb.getName());// 繁转简
				// gbHash.put(gb.getName(), gb.getBigname());//简转繁
			}
		}
	}

	/**
	 * 得到要申报的进口报关单表头，已检查但未生效的报关单表头
	 * 
	 * @param str1
	 *            账册号
	 * @param str2
	 *            合同号
	 * @return List 是TempCustomsMessage型，存放还没被选择的报关单表头信息.
	 */
	public List getImportDataSource(String str1, String str2) {
		List tempList = baseEncDao.findImportCustomsDeclaration(str1, str2);
		for (int i = tempList.size() - 1; i >= 0; i--) {
			if (((BaseCustomsDeclaration) tempList.get(i)).getIsCheck() == null
					|| ((BaseCustomsDeclaration) tempList.get(i)).getIsCheck()
							.booleanValue() == false
					|| (((BaseCustomsDeclaration) tempList.get(i))
							.getEffective() != null && ((BaseCustomsDeclaration) tempList
							.get(i)).getEffective().booleanValue() == true)) {
				tempList.remove(i);
			}
		}

		List newList = new ArrayList();
		for (int j = 0; j < tempList.size(); j++) {
			TempCustomsMessage temp = new TempCustomsMessage();
			temp.setCustomsDeclaration((BaseCustomsDeclaration) tempList.get(j));
			temp.setIsSelected(new Boolean(false));
			newList.add(temp);
		}
		return newList;
	}

	/**
	 * 得到要申报的出口报关单表头，已检查但未生效的报关单表头
	 * 
	 * @param str1
	 *            账册号
	 * @param str2
	 *            合同号
	 * @return List为TempCustomsMessage型,存放还没被选择的报关单表头信息.
	 */
	public List getExportDataSource(String str1, String str2) {
		List tempList = baseEncDao.findExportCustomsDeclaration(str1, str2);
		for (int i = tempList.size() - 1; i >= 0; i--) {
			if (((BaseCustomsDeclaration) tempList.get(i)).getIsCheck() == null
					|| ((BaseCustomsDeclaration) tempList.get(i)).getIsCheck()
							.booleanValue() == false
					|| (((BaseCustomsDeclaration) tempList.get(i))
							.getEffective() != null && ((BaseCustomsDeclaration) tempList
							.get(i)).getEffective().booleanValue() == true)) {
				tempList.remove(i);
			}
		}

		List newList = new ArrayList();
		for (int j = 0; j < tempList.size(); j++) {
			TempCustomsMessage temp = new TempCustomsMessage();
			temp.setCustomsDeclaration((BaseCustomsDeclaration) tempList.get(j));
			temp.setIsSelected(new Boolean(false));
			newList.add(temp);
		}
		return newList;
	}

	/**
	 * 得到要申报的特殊报关单表头，已检查但未生效的报关单表头
	 * 
	 * @param str1
	 *            账册号
	 * @param str2
	 *            合同号
	 * @return List为TempCustomsMessage型,存放还没被选择的报关单表头信息.
	 */
	public List getSpexportDataSource(String str1, String str2) {
		List tempList = baseEncDao.findSpecialCustomsDeclaration(str1, str2);
		for (int i = tempList.size() - 1; i >= 0; i--) {
			if (((BaseCustomsDeclaration) tempList.get(i)).getIsCheck() == null
					|| ((BaseCustomsDeclaration) tempList.get(i)).getIsCheck()
							.booleanValue() == false
					|| (((BaseCustomsDeclaration) tempList.get(i))
							.getEffective() != null && ((BaseCustomsDeclaration) tempList
							.get(i)).getEffective().booleanValue() == true)) {
				tempList.remove(i);
			}
		}

		List newList = new ArrayList();
		for (int j = 0; j < tempList.size(); j++) {
			TempCustomsMessage temp = new TempCustomsMessage();
			temp.setCustomsDeclaration((BaseCustomsDeclaration) tempList.get(j));
			temp.setIsSelected(new Boolean(false));
			newList.add(temp);
		}
		return newList;
	}

	/**
	 * 当报关单明细超出20项的时候，将当前报关单拆分成多份报关单
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单头
	 * @return List 为BaseCustomsDeclaration型,报关单头资料.
	 */
	public List splitCustomsDeclaration(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		List<BaseCustomsDeclaration> result = new ArrayList<BaseCustomsDeclaration>();
		List list = this.baseEncDao
				.findCustomsDeclarationCommInfo(baseCustomsDeclaration);
		int commInfoSize = list.size();
		if (commInfoSize <= 20) {
			throw new RuntimeException("当前报关单明细没有超出20项，所以不能拆分！");
		}
		int newHeadCount = (commInfoSize - 20) / 20;
		if (((commInfoSize - 20) % 20) > 0) {
			newHeadCount++;
		}
		for (int i = 0; i < newHeadCount; i++) {
			BaseCustomsDeclaration b = null;
			try {
				b = baseCustomsDeclaration.getClass().cast(
						BeanUtils.cloneBean(baseCustomsDeclaration));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			b.setId(null);
			b.setSerialNumber(this.baseEncDao
					.getCustomsDeclarationSerialNumber(
							baseCustomsDeclaration.getImpExpFlag(),
							baseCustomsDeclaration.getEmsHeadH2k()));
			b.setCustomsDeclarationCode(null);
			b.setPreCustomsDeclarationCode(null);
			b.setCreater(CommonUtils.getAclUser());
			b.setImpExpDate(new Date());
			b.setDeclarationDate(new Date());
			b.setCreateDate(new Date());
			this.baseEncDao.saveCustomsDeclaration(b);
			for (int j = 0; j < 20; j++) {
				int commIndex = (i + 1) * 20 + j;
				if (commIndex >= commInfoSize) {
					break;
				}
				BaseCustomsDeclarationCommInfo obj = (BaseCustomsDeclarationCommInfo) list
						.get(commIndex);
				BaseCustomsDeclarationCommInfo commInfo = null;
				try {
					commInfo = obj.getClass().cast(BeanUtils.cloneBean(obj));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				if (commInfo == null) {
					continue;
				}
				commInfo.setSerialNumber(j + 1);
				commInfo.setId(null);
				commInfo.setBaseCustomsDeclaration(b);
				this.baseEncDao.saveCustomsDeclarationCommInfo(commInfo);
				// 拆分时将旧的数据删除
				this.baseEncDao.deleteCustomsDeclarationCommInfo(obj);
			}
			result.add(b);
		}
		return result;
	}

	/**
	 * 获得计量单位的比例
	 * 
	 * @return
	 */
	public Map getUnitRateMap() {
		Map<String, Double> map = new HashMap<String, Double>();
		map.put("克+千克", 0.001);
		map.put("个+千个", 0.001);
		map.put("支+千支", 0.001);
		map.put("块+千块", 0.001);
		map.put("米+千米", 0.001);
		map.put("千克+克", 1000.0);
		map.put("千个+个", 1000.0);
		map.put("千支+支", 1000.0);
		map.put("千块+块", 1000.0);
		map.put("千米+米", 1000.0);
		return map;
	}

	public List<BaseCustomsDeclarationCommInfo> findCustomsDeclarationsForPrint(
			String traffic) {
		return baseEncDao.findCustomsDeclarationsForPrint(traffic);
	}

	/**
	 * 取得大批量修改商品编的备案资料
	 * 
	 * @param isMaterial
	 * @return
	 */
	public List getBatchUpdateComplex(String declareState, String contractNo,
			String emsType, Boolean isMaterial) {
		List list = new ArrayList();
		List result = new ArrayList();
		if ("0".equals(emsType)) {// 合同备案
			list = baseEncDao.findControntImgOrExgComplex(declareState,
					contractNo, isMaterial);
		} else if ("1".equals(emsType)) {// 备案资料库
			list = baseEncDao.findBcsDictPorImgOrExgComplex(declareState,
					isMaterial);
		} else if ("2".equals(emsType)) {// 十码对应关系
			list = baseEncDao.findBcsTenInnerMergeComplex(isMaterial);
		}
		TempCustomsDeclarationCommInfo commInfo = new TempCustomsDeclarationCommInfo();
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			commInfo = new TempCustomsDeclarationCommInfo();
			if (objs[0] != null) {
				commInfo.setEmsSerialNo(objs[0].toString());
			}
			if (objs[1] != null) {
				commInfo.setComplex((Complex) objs[1]);
			}
			if (objs[2] != null) {
				commInfo.setName(objs[2].toString());
			}
			if (!"2".equals(emsType)) {// 十码对应关系
				if (objs[3] != null) {
					commInfo.setCredenceNo((Integer) objs[3]);
				}
			}
			result.add(commInfo);

		}
		return result;
	}

	/**
	 * create by chl 查询未复进数据报表。
	 * 
	 * @param request
	 * @return
	 */
	public List queryReturnImportReport(Date begin, Date end, int impExpType) {
		// 未复进数据报表
		List reportData = new ArrayList();

		// 统计符合条件的退料复进数据
		List impList = baseEncDao.queryReturnImportReport(begin, end,
				ImpExpType.DIRECT_IMPORT);
		//
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		Object[] tmp = null;
		// key = 备案序号+商品名称+商品规格
		String key = null;
		for (int i = 0; i < impList.size(); i++) {
			tmp = (Object[]) impList.get(i);
			key = (tmp[0] == null ? "" : tmp[0].toString())
					+ (tmp[1] == null ? "" : tmp[1].toString())
					+ (tmp[2] == null ? "" : tmp[2].toString());
			map.put(key, tmp);
		}

		// 统计符合条件的退料出口数据
		List expList = baseEncDao.queryReturnImportReport(begin, end,
				ImpExpType.BACK_MATERIEL_EXPORT);
		Object[] item = null;
		Object[] impItem = null;

		// 关联出口进口数据，统计料件的未复进数量
		for (int i = 0; i < expList.size(); i++) {
			item = new Object[13];
			// 退料数据
			tmp = (Object[]) expList.get(i);
			key = (tmp[0] == null ? "" : tmp[0].toString())
					+ (tmp[1] == null ? "" : tmp[1].toString())
					+ (tmp[2] == null ? "" : tmp[2].toString());

			item[4] = tmp[0];// 备案序号
			item[5] = tmp[3];// 版本
			item[6] = tmp[1];// 名称
			item[7] = tmp[4];// 出口数量
			item[8] = tmp[5];// 出口金额
			item[10] = tmp[2];// 商品id
			item[11] = tmp[6];// 商品规格
			item[12] = tmp[7];// 单位id

			impItem = map.get(key);
			// 有复进数据
			if (impItem != null) {
				item[0] = impItem[3];// 版本
				item[1] = impItem[1];// 名称
				item[2] = impItem[4];// 进口数量
				item[3] = impItem[5];// 进口金额

				// 计算未复进数据 = 出口数量 - 进口数量
				item[9] = (tmp[4] == null ? 0 : (Double) tmp[4])
						- (impItem[4] == null ? 0 : (Double) impItem[4]);
			} else {

				// 计算未复进数据 = 出口数量
				item[9] = tmp[4];
			}

			// 过滤 未复进数 = 0 的数据，就是说过滤进出平衡的数据。
			if (item[9] != null && Double.parseDouble(item[9].toString()) != 0) {
				reportData.add(item);
			}
		}

		return reportData;
	}

	/**
	 * create by chl 查询未复进数据报表。
	 * 
	 * @param request
	 * @return
	 */
	public List queryReturnImportReport(Date begin, Date end, int impExpType,
			String emsNo, int projectType) {
		// 未复进数据报表
		List reportData = new ArrayList();

		// 统计符合条件的退料复进数据
		List impList = baseEncDao.queryReturnImportReport(begin, end,
				ImpExpType.DIRECT_IMPORT, emsNo, projectType);

		//
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		Object[] tmp = null;
		// key = 备案序号+商品名称+商品规格
		String key = null;
		for (int i = 0; i < impList.size(); i++) {
			tmp = (Object[]) impList.get(i);
			key = (tmp[0] == null ? "" : tmp[0].toString())
					+ (tmp[1] == null ? "" : tmp[1].toString())
					+ (tmp[2] == null ? "" : tmp[2].toString());
			map.put(key, tmp);
		}

		// 统计符合条件的退料出口数据
		List expList = baseEncDao.queryReturnImportReport(begin, end,
				ImpExpType.BACK_MATERIEL_EXPORT, emsNo, projectType);
		// ////////
		Object[] item = null;
		Object[] impItem = null;

		// 关联出口进口数据，统计料件的未复进数量
		for (int i = 0; i < expList.size(); i++) {
			item = new Object[13];
			// 退料数据
			tmp = (Object[]) expList.get(i);
			key = (tmp[0] == null ? "" : tmp[0].toString())
					+ (tmp[1] == null ? "" : tmp[1].toString())
					+ (tmp[2] == null ? "" : tmp[2].toString());

			item[4] = tmp[0];// 备案序号
			item[5] = tmp[3];// 版本
			item[6] = tmp[1];// 名称
			item[7] = tmp[4];// 出口数量
			item[8] = tmp[5];// 出口金额
			item[10] = tmp[2];// 商品id
			item[11] = tmp[6];// 商品规格
			item[12] = tmp[7];// 单位id

			impItem = map.get(key);
			// 有复进数据
			if (impItem != null) {
				item[0] = impItem[3];// 版本
				item[1] = impItem[1];// 名称
				item[2] = impItem[4];// 进口数量
				item[3] = impItem[5];// 进口金额

				// 计算未复进数据 = 出口数量 - 进口数量
				item[9] = (tmp[4] == null ? 0 : (Double) tmp[4])
						- (impItem[4] == null ? 0 : (Double) impItem[4]);
			} else {

				// 计算未复进数据 = 出口数量
				item[9] = tmp[4];
			}

			// 过滤 未复进数 = 0 的数据，就是说过滤进出平衡的数据。
			if (item[9] != null && Double.parseDouble(item[9].toString()) != 0) {
				reportData.add(item);
			}
		}

		return reportData;
	}

	/**
	 * create by chl 查询未复出数据报表。
	 * 
	 * @param request
	 * @return
	 */
	public List queryReturnExportReport(Date begin, Date end, int impExpType) {
		// 未复出数据报表
		List reportData = new ArrayList();

		// 统计符合条件的返工复出数据
		List expList = baseEncDao.queryReturnExportReport(begin, end,
				ImpExpType.REWORK_EXPORT);
		//
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		Object[] tmp = null;
		// key = 备案序号+商品名称+商品规格+版本
		String key = null;
		for (int i = 0; i < expList.size(); i++) {
			tmp = (Object[]) expList.get(i);
			key = (tmp[0] == null ? "" : tmp[0].toString())
					+ (tmp[1] == null ? "" : tmp[1].toString())
					+ (tmp[2] == null ? "" : tmp[2].toString())
					+ (tmp[3] == null ? "" : tmp[3].toString());
			map.put(key, tmp);
		}

		// 统计符合条件的退厂返工数据
		List impList = baseEncDao.queryReturnExportReport(begin, end,
				ImpExpType.BACK_FACTORY_REWORK);
		Object[] item = null;
		// 返工复出数据
		Object[] expItem = null;

		// 关联出退厂返工和返工复出数据，统计料件的未复出数量
		for (int i = 0; i < impList.size(); i++) {
			item = new Object[14];
			// 退料数据
			tmp = (Object[]) impList.get(i);
			key = (tmp[0] == null ? "" : tmp[0].toString())
					+ (tmp[1] == null ? "" : tmp[1].toString())
					+ (tmp[2] == null ? "" : tmp[2].toString())
					+ (tmp[3] == null ? "" : tmp[3].toString());

			item[5] = tmp[0].toString();// 备案序号
			item[6] = tmp[3]; // 版本
			item[7] = tmp[1]; // 名称
			item[8] = tmp[4]; // 出口数量
			item[9] = tmp[5]; // 出口金额
			item[11] = tmp[2]; // 商品id
			item[12] = tmp[6]; // 商品规格
			item[13] = tmp[7]; // 单位id

			expItem = map.get(key);
			// 有返工复出数据
			if (expItem != null) {
				item[0] = expItem[0];// 备案序号
				item[1] = expItem[3];// 版本
				item[2] = expItem[1];// 名称
				item[3] = expItem[4];// 进口数量
				item[4] = expItem[5];// 进口金额

				// 计算未复进数据 = 出口数量 - 进口数量
				item[10] = (tmp[4] == null ? 0 : (Double) tmp[4])
						- (expItem[4] == null ? 0 : (Double) expItem[4]);
			} else {

				// 计算未复出数据 = 出口数量
				item[10] = tmp[4];
			}

			// 过滤 未复出数 = 0 的数据，就是说过滤进出平衡的数据。
			if (item[10] != null
					&& Double.parseDouble(item[10].toString()) != 0) {
				reportData.add(item);
			}
		}

		return reportData;
	}

	/**
	 * create by chl 查询未复出数据报表。
	 * 
	 * @param request
	 * @return
	 */
	public List queryReturnExportReport(Date begin, Date end, int impExpType,
			String emsHeadH2k, int projectType) {
		// 未复出数据报表
		List reportData = new ArrayList();

		// 统计符合条件的返工复出数据
		List expList = baseEncDao.queryReturnExportReport(begin, end,
				ImpExpType.REWORK_EXPORT, emsHeadH2k, projectType);
		//
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		Object[] tmp = null;
		// key = 备案序号+商品名称+商品规格+版本
		String key = null;
		for (int i = 0; i < expList.size(); i++) {
			tmp = (Object[]) expList.get(i);
			key = (tmp[0] == null ? "" : tmp[0].toString())
					+ (tmp[1] == null ? "" : tmp[1].toString())
					+ (tmp[2] == null ? "" : tmp[2].toString())
					+ (tmp[3] == null ? "" : tmp[3].toString());
			map.put(key, tmp);
		}

		// 统计符合条件的退厂返工数据
		List impList = baseEncDao.queryReturnExportReport(begin, end,
				ImpExpType.BACK_FACTORY_REWORK, emsHeadH2k, projectType);
		Object[] item = null;
		// 返工复出数据
		Object[] expItem = null;

		// 关联出退厂返工和返工复出数据，统计料件的未复出数量
		for (int i = 0; i < impList.size(); i++) {
			item = new Object[14];
			// 退料数据
			tmp = (Object[]) impList.get(i);
			key = (tmp[0] == null ? "" : tmp[0].toString())
					+ (tmp[1] == null ? "" : tmp[1].toString())
					+ (tmp[2] == null ? "" : tmp[2].toString())
					+ (tmp[3] == null ? "" : tmp[3].toString());

			item[5] = tmp[0].toString();// 备案序号
			item[6] = tmp[3]; // 版本
			item[7] = tmp[1]; // 名称
			item[8] = tmp[4]; // 出口数量
			item[9] = tmp[5]; // 出口金额
			item[11] = tmp[2]; // 商品id
			item[12] = tmp[6]; // 商品规格
			item[13] = tmp[7]; // 单位id

			expItem = map.get(key);
			// 有返工复出数据
			if (expItem != null) {
				item[0] = expItem[0];// 备案序号
				item[1] = expItem[3];// 版本
				item[2] = expItem[1];// 名称
				item[3] = expItem[4];// 进口数量
				item[4] = expItem[5];// 进口金额

				// 计算未复进数据 = 出口数量 - 进口数量
				item[10] = (tmp[4] == null ? 0 : (Double) tmp[4])
						- (expItem[4] == null ? 0 : (Double) expItem[4]);
			} else {

				// 计算未复出数据 = 出口数量
				item[10] = tmp[4];
			}

			// 过滤 未复出数 = 0 的数据，就是说过滤进出平衡的数据。
			if (item[10] != null
					&& Double.parseDouble(item[10].toString()) != 0) {
				reportData.add(item);
			}
		}

		return reportData;
	}

	/**
	 * 查询统计未复出数据或未复进数据。
	 * 
	 * @param begin
	 * @param end
	 * @param complexId
	 * @param impExpFlag
	 *            true 统计未复进,false 统计未复出
	 * @return
	 */
	public Double countReturnNumbers(Date begin, Date end, String complexId,
			boolean impExpFlag, String version, int commSerialNo) {

		if (impExpFlag) {
			// 统计商品的退料出口数
			double exports = baseEncDao.countReturnNumbers(begin, end,
					complexId, ImpExpType.BACK_MATERIEL_EXPORT, version,
					commSerialNo);
			// 统计商品的退料复进数
			double returns = baseEncDao.countReturnNumbers(begin, end,
					complexId, ImpExpType.DIRECT_IMPORT, version, commSerialNo);

			return exports - returns;
		} else {
			// 统计商品的退厂返工数
			double returns = baseEncDao.countReturnNumbers(begin, end,
					complexId, ImpExpType.BACK_FACTORY_REWORK, version,
					commSerialNo);
			// 统计商品的返工复出数
			double exports = baseEncDao.countReturnNumbers(begin, end,
					complexId, ImpExpType.REWORK_EXPORT, version, commSerialNo);

			return returns - exports;
		}

	}

	/**
	 * 查询统计未复出数据或未复进数据。
	 * 
	 * @param begin
	 * @param end
	 * @param complexId
	 * @param impExpFlag
	 *            true 统计未复进,false 统计未复出
	 * @param emsHeadH2k
	 *            手册号
	 * @return
	 */
	public Double countReturnNumbers(Date begin, Date end, String emsHeadH2k,
			String complexId, boolean impExpFlag, String version,
			int commSerialNo) {

		if (impExpFlag) {
			// 统计商品的退料出口数
			double exports = baseEncDao.countReturnNumbers(begin, end,
					emsHeadH2k, complexId, ImpExpType.BACK_MATERIEL_EXPORT,
					version, commSerialNo);
			// 统计商品的退料复进数
			double returns = baseEncDao.countReturnNumbers(begin, end,
					emsHeadH2k, complexId, ImpExpType.DIRECT_IMPORT, version,
					commSerialNo);

			return exports - returns;
		} else {
			// 统计商品的退厂返工数
			double returns = baseEncDao.countReturnNumbers(begin, end,
					emsHeadH2k, complexId, ImpExpType.BACK_FACTORY_REWORK,
					version, commSerialNo);
			// 统计商品的返工复出数
			double exports = baseEncDao.countReturnNumbers(begin, end,
					emsHeadH2k, complexId, ImpExpType.REWORK_EXPORT, version,
					commSerialNo);

			return returns - exports;
		}

	}

	/**
	 * 查询统计可退换料件出口数或退厂返工可进口数
	 * 
	 * @param begin
	 * @param end
	 * @param complexId
	 * @param impExpFlag
	 *            true 可退换料件出口数,false 退厂返工可进口数
	 * @param emsHeadH2k
	 *            手册号
	 * @return
	 */
	public Double countRetreatNumbers(Date begin, Date end, String emsHeadH2k,
			String complexId, boolean impExpFlag, String version,
			int commSerialNo) {

		if (impExpFlag) {// 出口
			// 统计商品的料件进口数
			double allimports = baseEncDao.countRetreatNumbers(begin, end,
					emsHeadH2k, complexId, ImpExpType.DIRECT_IMPORT, version,
					commSerialNo);
			// 统计商品的退料出口数
			double exports = baseEncDao.countReturnNumbers(begin, end,
					emsHeadH2k, complexId, ImpExpType.BACK_MATERIEL_EXPORT,
					version, commSerialNo);
			// 统计商品的退料复进数
			double returns = baseEncDao.countReturnNumbers(begin, end,
					emsHeadH2k, complexId, ImpExpType.DIRECT_IMPORT, version,
					commSerialNo);

			return allimports - exports + returns;
		} else {// 进口数
			// 统计商品的出口数
			double allexports = baseEncDao.countRetreatNumbers(begin, end,
					emsHeadH2k, complexId, ImpExpType.DIRECT_EXPORT, version,
					commSerialNo);
			// 统计商品的退厂返工数
			double returns = baseEncDao.countReturnNumbers(begin, end,
					emsHeadH2k, complexId, ImpExpType.BACK_FACTORY_REWORK,
					version, commSerialNo);
			// 统计商品的返工复出数
			double exports = baseEncDao.countReturnNumbers(begin, end,
					emsHeadH2k, complexId, ImpExpType.REWORK_EXPORT, version,
					commSerialNo);

			return allexports - returns + exports;
		}

	}

	public BaseCustomsDeclaration findCustomsDeclarationByCustomsDeclarationCode(
			String customsDeclarationCode, boolean impExpFlag) {
		return baseEncDao.findCustomsDeclarationByCustomsDeclarationCode(
				customsDeclarationCode, impExpFlag);
	}
}
