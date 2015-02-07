package com.bestway.bcs.contract.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.bestway.bcs.contract.dao.ContractDao;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contract.entity.ProcessedContractData;
import com.bestway.bcus.custombase.dao.BaseCodeDao;
import com.bestway.bcus.custombase.dao.CountryCodeDao;
import com.bestway.bcus.custombase.dao.HsCodeDao;
import com.bestway.bcus.custombase.dao.ParameterCodeDao;
import com.bestway.bcus.custombase.entity.basecode.MachiningType;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.hscode.CustomsComplex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ModifyMarkState;

/**
 * com.bestway.bcs.contract.logic.ReceiveContractLogic
 * 
 * @author yp
 * 
 */
public class ReceiveContractLogic {
	/**
	 * contractDao
	 */
	private ContractDao contractDao = null;

	/**
	 * parameterCodeDao
	 */
	private ParameterCodeDao parameterCodeDao = null;

	/**
	 * countryCodeDao
	 */
	private CountryCodeDao countryCodeDao = null;

	/**
	 * baseCodeDao
	 */
	private BaseCodeDao baseCodeDao = null;

	/**
	 * hsCodeDao
	 */
	private HsCodeDao hsCodeDao = null;

	// /**
	// * logger
	// */
	// private final Log logger = LogFactory.getLog(getClass());

	/**
	 * 获取baseCodeDao
	 * 
	 * @return baseCodeDao
	 */
	public BaseCodeDao getBaseCodeDao() {
		return baseCodeDao;
	}

	/**
	 * 设置baseCodeDao
	 * 
	 * @param baseCodeDao
	 */
	public void setBaseCodeDao(BaseCodeDao baseCodeDao) {
		this.baseCodeDao = baseCodeDao;
	}

	/**
	 * 获取contractDao
	 * 
	 * @return contractDao
	 */
	public ContractDao getContractDao() {
		return contractDao;
	}

	/**
	 * 设置contractDao
	 * 
	 * @param contractDao
	 */
	public void setContractDao(ContractDao contractDao) {
		this.contractDao = contractDao;
	}

	/**
	 * 获取countryCodeDao
	 * 
	 * @return countryCodeDao
	 */
	public CountryCodeDao getCountryCodeDao() {
		return countryCodeDao;
	}

	/**
	 * 设置countryCodeDao
	 * 
	 * @param countryCodeDao
	 */
	public void setCountryCodeDao(CountryCodeDao countryCodeDao) {
		this.countryCodeDao = countryCodeDao;
	}

	/**
	 * 获取hsCodeDao
	 * 
	 * @return hsCodeDao
	 */
	public HsCodeDao getHsCodeDao() {
		return hsCodeDao;
	}

	/**
	 * 设置hsCodeDao
	 * 
	 * @param hsCodeDao
	 */
	public void setHsCodeDao(HsCodeDao hsCodeDao) {
		this.hsCodeDao = hsCodeDao;
	}

	/**
	 * 获取parameterCodeDao
	 * 
	 * @return parameterCodeDao
	 */
	public ParameterCodeDao getParameterCodeDao() {
		return parameterCodeDao;
	}

	/**
	 * 设置parameterCodeDao
	 * 
	 * @param parameterCodeDao
	 */
	public void setParameterCodeDao(ParameterCodeDao parameterCodeDao) {
		this.parameterCodeDao = parameterCodeDao;
	}

	/**
	 * 新建一个数组，是所给数组的子数组
	 * 
	 * @param rowContent
	 *            所给数组
	 * @param srcPos
	 *            所给数组的开始下标
	 * @param length
	 *            所给数组的长度
	 * @return String 返回新数组
	 */
	private String substrByByte(byte[] rowContent, int srcPos, int length) {
		byte[] dest = new byte[length];
		System.arraycopy(rowContent, srcPos, dest, 0, length);
		return new String(dest);
	}

	/**
	 * 处理合同回执
	 * 
	 * @param fileName
	 *            回执名称
	 */
	public void processContractData(String fileName) {
		List<String> lsMessage = this.getContractFileList(fileName);
		if (lsMessage.size() < 2) {
			return;
		}
		// boolean isNew = false;
		int i = 1;
		String rowBuffer = lsMessage.get(i);
		byte[] rowContent = lsMessage.get(i).getBytes();// rowBuffer.getBytes();
		int nOffSet = 0;
		String preEmsNo = substrByByte(rowContent, nOffSet, 12).trim();// 预录入号
		System.out.println(preEmsNo);
		nOffSet += 12;
		String emsNo = substrByByte(rowContent, nOffSet, 12).trim();// 手册编号
		nOffSet += 12;
		System.out.println(emsNo);
		Contract contract = this.contractDao.findContractByEmsNo(emsNo);
		if (contract == null) {
			contract = new Contract();
			// isNew = true;
		}
		contract.setPreEmsNo(preEmsNo);
		contract.setEmsNo(emsNo);
		nOffSet += 10;
		contract.setTradeName(substrByByte(rowContent, nOffSet, 30).trim());// 经营单位
		System.out.println("经营单位:" + contract.getTradeName());
		nOffSet += 30;
		nOffSet += 5;
		contract.setMachCode(substrByByte(rowContent, nOffSet, 10).trim());// 收货单位编号
		System.out.println("收货单位编号" + contract.getMachCode());
		nOffSet += 10;
		contract.setMachName(substrByByte(rowContent, nOffSet, 30).trim());// 收货单位名称
		System.out.println("收货单位名称" + contract.getMachName());
		nOffSet += 30;
		contract.setOutTradeCo(substrByByte(rowContent, nOffSet, 30).trim());// 外商公司
		System.out.println("外商公司" + contract.getOutTradeCo());
		nOffSet += 30;
		contract.setLinkMan(substrByByte(rowContent, nOffSet, 16).trim());// 联系人
		System.out.println("联系人" + contract.getLinkMan());
		nOffSet += 16;
		String tradeCode = substrByByte(rowContent, nOffSet, 4).trim();// 贸易方式
		System.out.println("贸易方式" + contract.getTradeCode());
		nOffSet += 4;
		List list = this.parameterCodeDao.findTrade("code", tradeCode);
		contract.setTrade(list.size() < 1 ? null : (Trade) list.get(0));
		String levyKindCode = substrByByte(rowContent, nOffSet, 3).trim();// 征免性质
		System.out.println("征免性质" + substrByByte(rowContent, nOffSet, 3));
		nOffSet += 3;
		list = this.parameterCodeDao.findLevyKind("code", levyKindCode);
		contract.setLevyKind(list.size() < 1 ? null : (LevyKind) list.get(0));
		String countryCode = substrByByte(rowContent, nOffSet, 3).trim();// 贸易国别
		System.out.println("贸易国别" + substrByByte(rowContent, nOffSet, 3));
		nOffSet += 3;
		list = this.countryCodeDao.findCountry("code", countryCode);
		contract
				.setTradeCountry(list.size() < 1 ? null : (Country) list.get(0));
		String iePortCode1 = substrByByte(rowContent, nOffSet, 4).trim();// 进出口岸1
		System.out.println("进出口岸1" + iePortCode1);
		nOffSet += 4;
		list = this.countryCodeDao.findCustoms("code", iePortCode1);
		contract.setIePort1(list.size() < 1 ? null : (Customs) list.get(0));
		String iePortCode2 = substrByByte(rowContent, nOffSet, 4).trim();// 进出口岸2
		System.out.println("进出口岸2" + iePortCode2);
		nOffSet += 4;
		list = this.countryCodeDao.findCustoms("code", iePortCode2);
		contract.setIePort2(list.size() < 1 ? null : (Customs) list.get(0));
		String iePortCode3 = substrByByte(rowContent, nOffSet, 4).trim();// 进出口岸3
		System.out.println("进出口岸3" + iePortCode3);
		nOffSet += 4;
		list = this.countryCodeDao.findCustoms("code", iePortCode3);
		contract.setIePort3(list.size() < 1 ? null : (Customs) list.get(0));
		String iePortCode4 = substrByByte(rowContent, nOffSet, 4).trim();// 进出口岸4
		System.out.println("进出口岸4" + iePortCode4);
		nOffSet += 4;
		list = this.countryCodeDao.findCustoms("code", iePortCode4);
		contract.setIePort4(list.size() < 1 ? null : (Customs) list.get(0));
		String iePortCode5 = substrByByte(rowContent, nOffSet, 4).trim();// 进出口岸5
		System.out.println("进出口岸5" + iePortCode5);
		nOffSet += 4;
		list = this.countryCodeDao.findCustoms("code", iePortCode5);
		contract.setIePort5(list.size() < 1 ? null : (Customs) list.get(0));

		contract.setAgreementNo(substrByByte(rowContent, nOffSet, 20).trim());// 协议书号
		System.out.println("协议书号" + contract.getAgreementNo());
		nOffSet += 20;
		contract.setPermitNo(substrByByte(rowContent, nOffSet, 20).trim());// 批准证号
		System.out.println("批准证号" + contract.getPermitNo());
		nOffSet += 20;
		contract.setSancEmsNo(substrByByte(rowContent, nOffSet, 20).trim());// 批文号
		System.out.println("批文号" + contract.getSancEmsNo());
		nOffSet += 20;
		contract.setImpContractNo(substrByByte(rowContent, nOffSet, 20).trim());// 进口合同号
		System.out.println("进口合同号" + contract.getImpContractNo());
		nOffSet += 20;
		contract.setExpContractNo(substrByByte(rowContent, nOffSet, 20).trim());// 出口合同号
		System.out.println("出口合同号" + contract.getExpContractNo());
		nOffSet += 20;
		nOffSet++;

		String transaCode = substrByByte(rowContent, nOffSet, 1).trim();// 成交方式
		System.out.println("成交方式" + transaCode);
		nOffSet++;
		list = this.parameterCodeDao.findTransac("code", transaCode);
		contract.setTransac(list.size() < 1 ? null : (Transac) list.get(0));

		String payTaxCode = substrByByte(rowContent, nOffSet, 1).trim();// 纳税方式
		System.out.println("纳税方式" + payTaxCode);
		nOffSet++;
		nOffSet++;

		nOffSet += 4;
		String machiningType = substrByByte(rowContent, nOffSet, 2).trim();// 加工种类
		System.out.println("加工种类" + machiningType);
		nOffSet += 2;
		list = this.baseCodeDao.findMachiningType("code", machiningType);
		contract.setMachiningType(list.size() < 1 ? null : (MachiningType) list
				.get(0));

		nOffSet += 4;
		String inSaleRate = substrByByte(rowContent, nOffSet, 4).trim();
		System.out.println("aaaaaaa" + inSaleRate);
		contract.setInSaleRate(inSaleRate.equals("") ? 0 : Double
				.valueOf(inSaleRate));
		nOffSet += 4;
		nOffSet += 9;
		nOffSet += 3;
		nOffSet += 9;
		nOffSet += 9;
		String imgAmount = substrByByte(rowContent, nOffSet, 9).trim();
		contract.setImgAmount(imgAmount.equals("") ? 0 : Double
				.valueOf(imgAmount));// 进口总值
		System.out.println("进口总值" + imgAmount);
		nOffSet += 9;
		String currCode = substrByByte(rowContent, nOffSet, 3).trim();// 币制
		nOffSet += 3;
		list = this.parameterCodeDao.findCurr("code", currCode);
		contract.setCurr(list.size() < 1 ? null : (Curr) list.get(0));
		nOffSet += 9;
		String exgAmount = substrByByte(rowContent, nOffSet, 9).trim();
		contract.setExgAmount(exgAmount.equals("") ? 0 : Double
				.valueOf(exgAmount));// 出口总值
		nOffSet += 9;

		nOffSet += 3;
		nOffSet += 9;
		nOffSet += 1;

		nOffSet += 1;
		nOffSet += 10;
		nOffSet += 4;
		nOffSet += 4;
		nOffSet += 4;

		nOffSet += 4;
		nOffSet += 4;
		nOffSet += 4;
		nOffSet += 4;
		nOffSet += 4;

		String endDate = substrByByte(rowContent, nOffSet, 6).trim();// 结束有效期
		contract.setEndDate(this.converStrToDate(endDate));
		nOffSet += 6;
		nOffSet += 6;
		String deferDate = substrByByte(rowContent, nOffSet, 6).trim();// 延期期限
		contract.setDeferDate(this.converStrToDate(deferDate));
		nOffSet += 6;
		nOffSet += 4;
		String beginDate = substrByByte(rowContent, nOffSet, 6).trim();// 开始有效期
		contract.setBeginDate(this.converStrToDate(beginDate));
		nOffSet += 6;
		contract.setApprover(substrByByte(rowContent, nOffSet, 4).trim());// 审批人
		nOffSet += 4;
		String approveDate = substrByByte(rowContent, nOffSet, 6).trim();// 审批日期
		contract.setApproveDate(this.converStrToDate(approveDate));
		nOffSet += 6;
		String declareCustoms = substrByByte(rowContent, nOffSet, 4).trim();
		nOffSet += 4;
		list = this.countryCodeDao.findCustoms("code", declareCustoms);
		contract.setDeclareCustoms(list.size() < 1 ? null : (Customs) list
				.get(0));

		String wardshipRate = substrByByte(rowContent, nOffSet, 6).trim();
		contract.setWardshipRate(wardshipRate.equals("") ? 0 : Double
				.valueOf(wardshipRate));// 监管费率
		nOffSet += 6;

		String wardshipFee = substrByByte(rowContent, nOffSet, 9).trim();
		contract.setWardshipRate(wardshipFee.equals("") ? 0 : Double
				.valueOf(wardshipFee));// 监管费
		nOffSet += 9;
		nOffSet += 9;
		// contract.setDeclareType();
		contract.setIsContractEms(false);
		contract.setModifyMark(ModifyMarkState.UNCHANGE);
		contract.setDeclareState(DeclareState.PROCESS_EXE);
		this.contractDao.saveContract(contract);
		// ----------------------------------------------------------------料件
		i = i + 2;
		rowBuffer = lsMessage.get(i);
		rowContent = rowBuffer.getBytes();// lsMessage.get(i).getBytes();
		Map hmImp = new HashMap();
		int j;
		for (j = i + 1; j < lsMessage.size(); j++) {
			if (rowBuffer.equals("MNL_EXL")) {
				break;
			}
			nOffSet = 0;
			nOffSet += 12;
			String seqNum = substrByByte(rowContent, nOffSet, 4).trim();
			nOffSet += 4;
			String complexCode = substrByByte(rowContent, nOffSet, 10).trim();
			nOffSet += 8;
			ContractImg img = this.contractDao.findContractImg(
					contract.getId(), seqNum);
			if (img == null) {
				img = new ContractImg();
				img.setContract(contract);
			}
			if (seqNum != null && !"".equals(seqNum)) {
				img.setSeqNum(Integer.valueOf(seqNum));
			}
			list = this.hsCodeDao.findComplex("code", complexCode);
			if (list.size() > 0 && list.get(0) != null) {
				img.setComplex((Complex) list.get(0));
			} else {
				list = this.hsCodeDao.findCustomsComplex(complexCode);
				if (list.size() > 0 && list.get(0) != null) {
					CustomsComplex customsComplex = (CustomsComplex) list
							.get(0);
					Complex complex = new Complex();
					try {
						PropertyUtils.copyProperties(complex, customsComplex);
					} catch (Exception e) {
						e.printStackTrace();
						throw new RuntimeException(e.getMessage());
					}
					this.hsCodeDao.saveComplex(complex);
					img.setComplex(complex);
				}
				// System.out.println("--------img complex is null :"
				// + complexCode);
				throw new RuntimeException("料件" + complexCode + "在自用商品编码中不存在！");
			}
			nOffSet += 8;
			nOffSet++;
			img.setName(substrByByte(rowContent, nOffSet, 30).trim());
			nOffSet += 30;
			img.setSpec(substrByByte(rowContent, nOffSet, 30).trim());
			nOffSet += 30;
			countryCode = substrByByte(rowContent, nOffSet, 3).trim();// 原产国
			nOffSet += 3;
			list = this.countryCodeDao.findCountry("code", countryCode);
			img.setCountry(list.size() < 1 ? null : (Country) list.get(0));
			String levyModeCode = substrByByte(rowContent, nOffSet, 1).trim();// 征免方式
			nOffSet += 1;
			list = this.parameterCodeDao.findLevyMode("code", levyModeCode);
			img.setLevyMode(list.size() < 1 ? null : (LevyMode) list.get(0));

			String expTaxRate = substrByByte(rowContent, nOffSet, 3).trim();
			nOffSet += 3;// '出口征税比例

			nOffSet += 20;

			String totalPrice = substrByByte(rowContent, nOffSet, 12).trim();
			nOffSet += 12;
			img.setTotalPrice(totalPrice.equals("") ? 0 : Double
					.valueOf(totalPrice));
			nOffSet += 12;// 已进口总金额
			String unitPrice = substrByByte(rowContent, nOffSet, 12).trim();
			nOffSet += 12;
			img.setDeclarePrice(unitPrice.equals("") ? 0 : Double
					.valueOf(unitPrice));
			String amount = substrByByte(rowContent, nOffSet, 13).trim();
			nOffSet += 13;
			img.setAmount(amount.equals("") ? 0 : Double.valueOf(amount));
			nOffSet += 13;// 已进口总数量
			nOffSet += 13;
			String unitCode = substrByByte(rowContent, nOffSet, 3).trim();// 单位
			nOffSet += 3;
			list = this.parameterCodeDao.findUnit("code", unitCode);
			img.setUnit(list.size() < 1 ? null : (Unit) list.get(0));
			img.setModifyMark(ModifyMarkState.UNCHANGE);
			this.contractDao.saveContractImg(img);
			hmImp.put(img.getSeqNum(), img);
			rowBuffer = lsMessage.get(j);
			rowContent = rowBuffer.getBytes();
		}
		// ----------------------------------------------------------------------------成品
		i = j;
		rowBuffer = lsMessage.get(i);
		rowContent = rowBuffer.getBytes();
		for (j = i + 1; j < lsMessage.size(); j++) {
			if (rowBuffer.equals("MNL_CON")) {
				break;
			}
			nOffSet = 0;
			nOffSet += 12;
			String seqNum = substrByByte(rowContent, nOffSet, 4).trim();
			nOffSet += 4;
			String complexCode = substrByByte(rowContent, nOffSet, 10).trim();
			nOffSet += 8;
			ContractExg exg = this.contractDao.findContractExg(
					contract.getId(), seqNum);
			if (exg == null) {
				exg = new ContractExg();
				exg.setContract(contract);
			}
			if (seqNum != null && !"".equals(seqNum)) {
				exg.setSeqNum(Integer.valueOf(seqNum));
			}
			list = this.hsCodeDao.findComplex("code", complexCode);
			if (list.size() > 0 && list.get(0) != null) {
				exg.setComplex((Complex) list.get(0));
			} else {
				list = this.hsCodeDao.findCustomsComplex(complexCode);
				if (list.size() > 0 && list.get(0) != null) {
					CustomsComplex customsComplex = (CustomsComplex) list
							.get(0);
					Complex complex = new Complex();
					try {
						PropertyUtils.copyProperties(complex, customsComplex);
					} catch (Exception e) {
						e.printStackTrace();
						throw new RuntimeException(e.getMessage());
					}
					this.hsCodeDao.saveComplex(complex);
					exg.setComplex(complex);
				}
				// System.out.println("--------exg complex is null :"
				// + complexCode);
				throw new RuntimeException("成品" + complexCode + "在自用商品编码中不存在！");
			}
			// exg.setComplex(list.size() < 1 ? null : (Complex) list.get(0));
			nOffSet += 8;
			nOffSet++;
			exg.setName(substrByByte(rowContent, nOffSet, 30).trim());
			nOffSet += 30;
			exg.setSpec(substrByByte(rowContent, nOffSet, 30).trim());
			nOffSet += 30;
			countryCode = substrByByte(rowContent, nOffSet, 3).trim();// 原产国
			nOffSet += 3;
			list = this.countryCodeDao.findCountry("code", countryCode);
			exg.setCountry(list.size() < 1 ? null : (Country) list.get(0));
			String levyModeCode = substrByByte(rowContent, nOffSet, 1).trim();// 征免方式
			nOffSet += 1;
			list = this.parameterCodeDao.findLevyMode("code", levyModeCode);
			exg.setLevyMode(list.size() < 1 ? null : (LevyMode) list.get(0));

			String expTaxRate = substrByByte(rowContent, nOffSet, 3).trim();
			nOffSet += 3;// '出口征税比例

			nOffSet += 20;
			String totalPrice = substrByByte(rowContent, nOffSet, 12).trim();
			nOffSet += 12;
			exg.setTotalPrice(totalPrice.equals("") ? 0 : Double
					.valueOf(totalPrice));
			nOffSet += 12;
			String unitPrice = substrByByte(rowContent, nOffSet, 12).trim();
			nOffSet += 12;
			exg.setUnitPrice(unitPrice.equals("") ? 0 : Double
					.valueOf(unitPrice));
			String amount = substrByByte(rowContent, nOffSet, 13).trim();
			nOffSet += 13;
			exg.setExportAmount(amount.equals("") ? 0 : Double.valueOf(amount));
			nOffSet += 13;
			nOffSet += 13;
			String unitCode = substrByByte(rowContent, nOffSet, 3).trim();// 单位
			nOffSet += 3;
			list = this.parameterCodeDao.findUnit("code", unitCode);
			exg.setUnit(list.size() < 1 ? null : (Unit) list.get(0));
			nOffSet += 3;

			String processTotalPrice = substrByByte(rowContent, nOffSet, 9)
					.trim();
			nOffSet += 9;
			exg.setProcessTotalPrice(processTotalPrice.equals("") ? 0 : Double
					.valueOf(processTotalPrice));
			nOffSet += 3;
			exg.setModifyMark(ModifyMarkState.UNCHANGE);
			this.contractDao.saveContractExg(exg);
			rowBuffer = lsMessage.get(j);
			rowContent = rowBuffer.getBytes();
		}
		i = j;
		// int bomRow = 0;
		Map<String, Integer> hmBomRow = new HashMap<String, Integer>();
		for (j = i; j < lsMessage.size(); j++) {
			rowBuffer = lsMessage.get(j);
			rowContent = rowBuffer.getBytes();
			if (rowBuffer.trim().equals("")) {
				rowBuffer = lsMessage.get(j);
				continue;
			}
			nOffSet = 0;
			nOffSet += 12;
			String exgSeqNum = substrByByte(rowContent, nOffSet, 4).trim();
			nOffSet += 4;
			String imgSeqNum = substrByByte(rowContent, nOffSet, 4).trim();
			nOffSet += 4;
			ContractBom bom = this.contractDao.findContractBom(
					contract.getId(), exgSeqNum, imgSeqNum);
			if (bom == null) {
				bom = new ContractBom();
				ContractExg exg = this.contractDao.findContractExg(contract
						.getId(), exgSeqNum);
				bom.setContractExg(exg);
				Integer bomRow = hmBomRow.get(exgSeqNum);
				if (bomRow == null) {
					bomRow = 0;
				}
				bomRow++;
				bom.setSeqNum(bomRow);
				hmBomRow.put(exgSeqNum, bomRow);
			}
			if (imgSeqNum != null && !"".equals(imgSeqNum)) {
				bom.setContractImgSeqNum(Integer.valueOf(imgSeqNum));
				ContractImg bomImg = (ContractImg) hmImp.get(Integer
						.valueOf(imgSeqNum));
				if (bomImg != null) {
					// bom.setContractImgId(bomImg.getId());
					bom.setComplex(bomImg.getComplex());
					bom.setName(bomImg.getName());
					bom.setSpec(bomImg.getSpec());
					bom.setDeclarePrice(bomImg.getDeclarePrice());
					bom.setUnit(bomImg.getUnit());
				}
			}
			String unitWaste = substrByByte(rowContent, nOffSet, 10).trim();
			nOffSet += 10;
			bom.setUnitWaste(unitWaste.trim().equals("") ? 0 : Double
					.valueOf(unitWaste));
			nOffSet += 10;
			String waste = substrByByte(rowContent, nOffSet, 5).trim();
			bom.setWaste((waste.trim().equals("") ? 0 : Double.valueOf(waste)));// */
			// 100
			bom.setUnitDosage(bom.getUnitWaste() / (1 - bom.getWaste()));
			bom.setTotalPrice(CommonUtils.getDoubleExceptNull(bom
					.getDeclarePrice()
					* bom.getUnitDosage()));
			bom.setModifyMark(ModifyMarkState.UNCHANGE);
			this.contractDao.saveContractBom(bom);
			// rowBuffer = lsMessage.get(j);
			// rowContent = rowBuffer.getBytes();
		}
		ProcessedContractData data = new ProcessedContractData();
		data.setFileName(fileName);
		this.contractDao.saveProcessedContractData(data);
	}

	/**
	 * 读取回执里的内容
	 * 
	 * @param fileName
	 *            回执名称
	 * @return List 是String型，存放了回执里的内容
	 */
	public List<String> getContractFileList(String fileName) {
		List lsResult = new ArrayList();
		InputStream messageStream;
		try {
			messageStream = new FileInputStream(CommonUtils.getRecvDir()
					+ File.separator + "bcs" + File.separator + fileName);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		try {
			InputStreamReader fr = new InputStreamReader(messageStream, "GBK");
			LineNumberReader lnr = new LineNumberReader(fr);
			String line;
			while ((line = lnr.readLine()) != null) {
				if (line.trim().equals("")) {
					continue;
				}
				lsResult.add(line);
				// messageLines.add(new String(line.getBytes(),"GBK"));
			}
			fr.close();
			lnr.close();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return lsResult;
	}

	/**
	 * 数据转换，把String型转换为Date型
	 * 
	 * @param str
	 *            要转换的String
	 * @return Date 转换后的Date
	 */
	private Date converStrToDate(String str) {
		if (str == null || str.trim().equals("") || str.trim().length() < 6) {
			return null;
		}
		String year = str.substring(0, 2);
		if (Integer.valueOf(year) >= 80) {
			year = ("19" + year);
		} else {
			year = ("20" + year);
		}
		String month = str.substring(2, 4);
		String day = str.substring(4, 6);
		// Calendar calendar = Calendar.getInstance();
		// calendar.setTime(new Date());
		// calendar.set(Calendar.YEAR, Integer.valueOf(str.substring(0, 2)));
		// calendar.set(Calendar.MONTH, Integer.valueOf(str.substring(2, 4)));
		// calendar.set(Calendar.DAY_OF_MONTH,
		// Integer.valueOf(str.substring(4)));
		// return calendar.getTime();
		String strDate = year + "-" + month + "-" + day;
		return java.sql.Date.valueOf(strDate);
	}

	/**
	 * 从设置ftp地址、用户、密码然后下载(报关单)
	 * 
	 * @return List 下载的文件
	 */
	public List ftpDownload() {
		List downloadFiles = new Vector();
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.setDefaultTimeout(300000);
			ftpClient.setDataTimeout(300000);
			Company company = (Company) CommonUtils.getCompany();
			String ftpAddress = company.getBcsFtpAddress();
			String ftpUser = company.getBcsFtpUser();
			String ftpPassword = company.getBcsFtpPassword();
			// logger.info("Connecting to: " + ftpAddress + "...");
			ftpClient.connect(ftpAddress);
			// logger.info("Connected to: " + ftpAddress + " OK!");
			// logger.info(ftpClient.getReplyString());
			if (ftpClient.login(ftpUser, ftpPassword)) {
				// logger.info("Login OK!");
			} else {
				// logger.info("Login failure!");
				throw new RuntimeException("未能成功的登录到远程服务器，操作被终止。");
			}
			if (ftpClient.changeWorkingDirectory("bgd_out")) {
				FTPFile[] ftpFiles = ftpClient.listFiles();
				Thread.currentThread().sleep(3000);
				for (int i = 0; i < ftpFiles.length; i++) {
					if (!ftpFiles[i].isFile()) {
						continue;
					}
					// 在报文接受目录下建立一个BCS专用目录
					File file = new File(CommonUtils.getRecvDir()
							+ File.separator + "bcs" + File.separator
							+ ftpFiles[i].getName());
					System.out.println(file.getName());
					if (!file.exists()) {
						if (!file.createNewFile()) {
							throw new RuntimeException("未能建立新文件："
									+ file.getName() + "。");
						}
					}
					FileOutputStream fos = new FileOutputStream(file);
					if (ftpClient.retrieveFile("/bgd_out/"
							+ ftpFiles[i].getName(), fos)) {
						ftpClient.deleteFile("/bgd_out/"
								+ ftpFiles[i].getName());
						downloadFiles.add(file.getName());
						// logger.info("成功下载" + ftpFiles[i].getName() + "文件。");
					} else {
						throw new RuntimeException("未能下载文件"
								+ ftpFiles[i].getName() + "。");
					}
					fos.close();
				}
				ftpClient.logout();
			} else {
				throw new RuntimeException("未能变更目录到" + CommonUtils.getRecvDir());
			}
			return downloadFiles;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 从设置ftp地址、用户、密码然后下载(合同)
	 * 
	 * @return List 下载的文件
	 */
	public List ftpContractDownload() {
		List downloadFiles = new Vector();
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.setDefaultTimeout(300000);
			ftpClient.setDataTimeout(300000);
			Company company = (Company) CommonUtils.getCompany();
			String ftpAddress = company.getBcsFtpAddress();
			String ftpUser = company.getBcsFtpUser();
			String ftpPassword = company.getBcsFtpPassword();
			// logger.info("Connecting to: " + ftpAddress + "...");
			ftpClient.connect(ftpAddress);
			// logger.info("Connected to: " + ftpAddress + " OK!");
			// logger.info(ftpClient.getReplyString());
			if (ftpClient.login(ftpUser, ftpPassword)) {
				// logger.info("Login OK!");
			} else {
				// logger.info("Login failure!");
				throw new RuntimeException("未能成功的登录到远程服务器，操作被终止。");
			}
			if (ftpClient.changeWorkingDirectory("htpz_out")) {
				FTPFile[] ftpFiles = ftpClient.listFiles();
				Thread.currentThread().sleep(3000);
				for (int i = 0; i < ftpFiles.length; i++) {
					if (!ftpFiles[i].isFile()) {
						continue;
					}
					// 创建回执下载的目录
					File file1 = new File(CommonUtils.getRecvDir()
							+ File.separator + "bcs");
					if (!file1.exists()) {
						file1.mkdir();
					}

					// 在报文接受目录下建立一个BCS专用目录
					File file = new File(CommonUtils.getRecvDir()
							+ File.separator + "bcs" + File.separator
							+ ftpFiles[i].getName());
					if (!file.exists()) {
						if (!file.createNewFile()) {
							throw new RuntimeException("未能建立新文件："
									+ file.getName() + "。");
						}
					}
					FileOutputStream fos = new FileOutputStream(file);
					if (ftpClient.retrieveFile("/htpz_out/"
							+ ftpFiles[i].getName(), fos)) {
						ftpClient.deleteFile("/htpz_out/"
								+ ftpFiles[i].getName());
						downloadFiles.add(file.getName());
						// logger.info("成功下载" + ftpFiles[i].getName() + "文件。");
					} else {
						throw new RuntimeException("未能下载文件"
								+ ftpFiles[i].getName() + "。");
					}
					fos.close();
				}
				ftpClient.logout();
			} else {
				throw new RuntimeException("未能变更目录到" + CommonUtils.getRecvDir());
			}
			return downloadFiles;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 查找未处理的回执
	 * 
	 * @return List 是String型，回执名称
	 * 
	 */
	public List findNotProcessedFile() {
		List lsResult = new ArrayList();
		File file = new File(CommonUtils.getRecvDir() + File.separator + "bcs");
		File[] files = file.listFiles();
		if (files == null) {
			return lsResult;
		}
		List list = this.contractDao.findProcessedFileName();
		for (int i = 0; i < files.length; i++) {
			String fileName = files[i].getName();
			if (!list.contains(fileName)) {
				lsResult.add(fileName);
			}
		}
		return lsResult;
	}

	/**
	 * 查找已处理的回执
	 * 
	 * @return List 是String型，回执名称
	 */
	public List findProcessedFile() {
		List lsResult = new ArrayList();
		File file = new File(CommonUtils.getRecvDir() + File.separator + "bcs");
		File[] files = file.listFiles();
		if (files == null) {
			return lsResult;
		}
		List list = this.contractDao.findProcessedFileName();
		for (int i = 0; i < files.length; i++) {
			String fileName = files[i].getName();
			if (list.contains(fileName)) {
				lsResult.add(fileName);
			}
		}
		return lsResult;
	}
}
