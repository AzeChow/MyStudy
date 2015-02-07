package com.bestway.bcs.contract.logic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestway.bcs.contract.dao.ContractDao;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.dictpor.dao.BcsDictPorDao;
import com.bestway.bcs.dictpor.entity.BcsDictPorExg;
import com.bestway.bcs.dictpor.entity.BcsDictPorHead;
import com.bestway.bcs.dictpor.entity.BcsDictPorImg;
import com.bestway.bcs.qp.action.BcsQpServiceClient;
import com.bestway.bcs.qp.entity.BcsQPContract;
import com.bestway.bcs.qp.entity.BcsQPContractBom;
import com.bestway.bcs.qp.entity.BcsQPContractExg;
import com.bestway.bcs.qp.entity.BcsQPContractImg;
import com.bestway.bcs.qp.entity.BcsQPDictPorExg;
import com.bestway.bcs.qp.entity.BcsQPDictPorHead;
import com.bestway.bcs.qp.entity.BcsQPDictPorImg;
import com.bestway.bcs.qp.entity.TempQPContractData;
import com.bestway.bcs.qp.entity.TempQPDictPorData;
import com.bestway.bcus.custombase.entity.basecode.InvestMode;
import com.bestway.bcus.custombase.entity.basecode.MachiningType;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.depcode.RedDep;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.hscode.CustomsComplex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.FetchInMode;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.PayMode;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.LimitFlag;
import com.bestway.common.constant.ModifyMarkState;

public class BcsImportQPDataLogic {

	private ContractDao contractDao = null;

	private BcsDictPorDao bcsDictPorDao = null;

	public ContractDao getContractDao() {
		return contractDao;
	}

	public void setContractDao(ContractDao dzscDao) {
		this.contractDao = dzscDao;
	}

	public BcsDictPorDao getBcsDictPorDao() {
		return bcsDictPorDao;
	}

	public void setBcsDictPorDao(BcsDictPorDao bcsDictPorDao) {
		this.bcsDictPorDao = bcsDictPorDao;
	}

	/**
	 * 从QP中查询电子化手册备案资料库表头
	 * 
	 * @return
	 */
	public List findBcsQPDictPorHead() {
		BcsParameterSet paraSet = contractDao.findBcsParameterSet();
		String machCode = ((Company) CommonUtils.getCompany()).getCode();
		if (machCode == null || "".equals(machCode.trim())) {
			throw new RuntimeException("公司的加工单位编码为空");
		}
		return BcsQpServiceClient.getInstance(paraSet).findBcsQPDictPorHead(
				paraSet.getRemoteHostICPwd(), machCode, true);
	}

	/**
	 * 从QP中查询电子化手册通关备案表头
	 * 
	 * @return
	 */
	public List findBcsQPContract() {
		BcsParameterSet paraSet = contractDao.findBcsParameterSet();
		String machCode = ((Company) CommonUtils.getCompany()).getCode();
		if (machCode == null || "".equals(machCode.trim())) {
			throw new RuntimeException("公司的加工单位编码为空");
		}
		return BcsQpServiceClient.getInstance(paraSet).findBcsQPContract(
				paraSet.getRemoteHostICPwd(), machCode, true);
	}

	/**
	 * 从QP中导入电子化手册通关备案
	 * 
	 * @param tempQPEmsPorData
	 */
	public void importContractFromQP(List listPtsEmsHead, boolean isOverWrite) {
		for (int i = 0; i < listPtsEmsHead.size(); i++) {
			BcsQPContract bcsQPEmsPorHead = (BcsQPContract) listPtsEmsHead
					.get(i);
			String tradeCode = bcsQPEmsPorHead.getTradeCode();// 经营单位
			String copEmsNo = bcsQPEmsPorHead.getCopEmsNo();// 内部编号
			BcsParameterSet paraSet = contractDao.findBcsParameterSet();
			TempQPContractData tempQPEmsPorData = BcsQpServiceClient
					.getInstance(paraSet).findTempQPContractData(
							paraSet.getRemoteHostICPwd(), tradeCode, copEmsNo,
							true);// 根据 经营单位 ，内部编号 抓取单据资料
			importContract(tempQPEmsPorData, isOverWrite);
		}
	}

	/**
	 * 从QP中导入电子手册通关备案
	 * 
	 * @param tempQPContractData
	 */
	private void importContract(TempQPContractData tempQPContractData,
			boolean isOverWrite) {
		if (tempQPContractData.getBcsQPContract() == null) {
			throw new RuntimeException("从远程服务中抓取的通关备案表头为空");
		}
		String emsNo = tempQPContractData.getBcsQPContract().getEmsNo().trim();
		Contract contract = this.contractDao.findExingContractByEmsNo(emsNo);
		if (contract != null && !isOverWrite) {
			return;
		}
		contract = this.importContract(tempQPContractData.getBcsQPContract());
		if (tempQPContractData.getPtsEmsListAImg() != null) {
			this.importContractImg(tempQPContractData.getPtsEmsListAImg(),
					contract);
		}
		if (tempQPContractData.getPtsEmsListAExg() != null) {
			this.importContractExg(tempQPContractData.getPtsEmsListAExg(),
					contract);
		}
		if (tempQPContractData.getPtsEmsListCm() != null) {
			this.importContractBom(tempQPContractData.getPtsEmsListCm(),
					contract);
		}
	}

	/**
	 * 根据商品编码编号获取自用商品编码对象，如果自用商品编码不存在，则从海关商品编码抓取，生成自用商品编码
	 * 
	 * @param complexCode
	 * @return
	 */
	private Complex getComplex(String complexCode) {
		Complex complex = (Complex) this.contractDao.findCustomBaseEntity(
				"Complex", "code", complexCode);
		if (complex != null) {
			return complex;
		} else {
			CustomsComplex customsComplex = (CustomsComplex) this.contractDao
					.findCustomBaseEntity("CustomsComplex", "code", complexCode);
			if (customsComplex != null) {
				complex = new Complex();
				complex.setId(customsComplex.getCode());
				complex.setCode(customsComplex.getCode());
				complex.setName(customsComplex.getName());
				complex.setIsOut(customsComplex.getIsOut());
				complex.setNote(customsComplex.getNote());
				complex.setFirstUnit(customsComplex.getFirstUnit());
				complex.setSecondUnit(customsComplex.getSecondUnit());
				complex.setLowrate(customsComplex.getLowrate());
				complex.setHighrate(customsComplex.getHighrate());
				this.contractDao.saveOrUpdate(complex);
				return complex;
			} else {
				return null;
			}
		}
	}

	/**
	 * 导入通关备案表头
	 * 
	 * @param ptsEmsHead
	 */
	private Contract importContract(BcsQPContract bcsQPContract) {
		if (bcsQPContract.getEmsNo() == null
				|| "".equals(bcsQPContract.getEmsNo().trim())) {
			throw new RuntimeException("从远程服务中抓取的通关备案"
					+ bcsQPContract.getCopEmsNo() + "的手册号为空");
		}
		String emsNo = bcsQPContract.getEmsNo().trim();
		Contract contract = this.contractDao.findExingContractByEmsNo(emsNo);
		if (contract == null) {
			contract = new Contract();
			// contract.setSeqNum(Integer.parseInt(this.contractDao.getNum(
			// "Contract", "seqNum")));
		}
		contract.setEmsNo(bcsQPContract.getEmsNo());
		contract.setSancEmsNo(bcsQPContract.getSancEmsNo());
		contract.setTradeCode(bcsQPContract.getTradeCode());
		contract.setTradeName(bcsQPContract.getTradeName());
		contract.setMachCode(bcsQPContract.getMachCode());
		contract.setMachName(bcsQPContract.getMachName());
		contract.setBeginDate(CommonUtils.convertStrToDate(bcsQPContract
				.getBeginDate()));
		contract.setEndDate(CommonUtils.convertStrToDate(bcsQPContract
				.getEndDate()));
		contract.setOutTradeCo(bcsQPContract.getOutTradeCo());
		contract.setLevyMode((LevyMode) this.contractDao.findCustomBaseEntity(
				"LevyMode", "code", bcsQPContract.getLevyModeCode()));
		contract.setLevyKind((LevyKind) this.contractDao.findCustomBaseEntity(
				"LevyKind", "code", bcsQPContract.getLevyKindCode()));
		contract.setPayMode((PayMode) this.contractDao.findCustomBaseEntity(
				"PayMode", "code", bcsQPContract.getPayModeCode()));
		contract.setMachiningType((MachiningType) this.contractDao
				.findCustomBaseEntity("MachiningType", "code", bcsQPContract
						.getMachiningTypeCode()));
		contract.setEmsApprNo(bcsQPContract.getEmsApprNo());
		contract.setReceiveArea((District) this.contractDao
				.findCustomBaseEntity("District", "code", bcsQPContract
						.getReceiveAreaCode()));
		contract.setInvestMode((InvestMode) this.contractDao
				.findCustomBaseEntity("InvestMode", "code", bcsQPContract
						.getInvestModeCode()));
		contract.setFetchInMode((FetchInMode) this.contractDao
				.findCustomBaseEntity("FetchInMode", "code", bcsQPContract
						.getFetchInModeCode()));
		contract.setInSaleRate(bcsQPContract.getInSaleRate());
		contract.setDeclareDate(CommonUtils.convertStrToDate(bcsQPContract
				.getDeclareDate()));
		contract.setMaterielItemCount(bcsQPContract.getMaterielItemCount());
		contract.setMachineCount(bcsQPContract.getMachineCount());
		contract.setProductItemCount(bcsQPContract.getProductItemCount());
		contract.setManageObject(bcsQPContract.getManageObject());
		contract.setLimitFlag(bcsQPContract.getLimitFlag());
		if (contract.getLimitFlag() == null
				|| "".equals(contract.getLimitFlag().trim())) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String beginDate = dateFormat.format(contract.getBeginDate());
			if (beginDate.compareTo("2008-08-23") < 0) {
				contract.setLimitFlag(LimitFlag.ADJUST_BEFORE_EMS);
			} else {
				contract.setLimitFlag(LimitFlag.ADJUST_AFTER_EMS);
			}
		}
		contract.setEquipMode(bcsQPContract.getEquipMode());
		contract.setDeclareCustoms((Customs) this.contractDao
				.findCustomBaseEntity("Customs", "code", bcsQPContract
						.getDeclareCustomsCode()));
		contract.setRedDep((RedDep) this.contractDao.findCustomBaseEntity(
				"RedDep", "code", bcsQPContract.getRedDepCode()));
		contract.setEmsType(bcsQPContract.getEmsType());
		contract.setCopEmsNo(bcsQPContract.getCopEmsNo());
		contract.setIePort1((Customs) this.contractDao.findCustomBaseEntity(
				"Customs", "code", bcsQPContract.getIePort1Code()));
		contract.setIePort2((Customs) this.contractDao.findCustomBaseEntity(
				"Customs", "code", bcsQPContract.getIePort2Code()));
		contract.setIePort3((Customs) this.contractDao.findCustomBaseEntity(
				"Customs", "code", bcsQPContract.getIePort3Code()));
		contract.setIePort4((Customs) this.contractDao.findCustomBaseEntity(
				"Customs", "code", bcsQPContract.getIePort4Code()));
		contract.setIePort5((Customs) this.contractDao.findCustomBaseEntity(
				"Customs", "code", bcsQPContract.getIePort5Code()));
		contract.setDeferDate(CommonUtils.convertStrToDate(bcsQPContract
				.getDeferDate()));
		contract.setDestroyDate(CommonUtils.convertStrToDate(bcsQPContract
				.getDestroyDate()));
		contract.setTrade((Trade) this.contractDao.findCustomBaseEntity(
				"Trade", "code", bcsQPContract.getTradeModeCode()));
		contract.setTradeCountry((Country) this.contractDao
				.findCustomBaseEntity("Country", "code", bcsQPContract
						.getTradeCountryCode()));
		contract.setEnterpriseAddress(bcsQPContract.getEnterpriseAddress());
		contract.setLinkMan(bcsQPContract.getLinkMan());
		contract.setContactTel(bcsQPContract.getContactTel());
		contract.setAgreementNo(bcsQPContract.getAgreementNo());
		contract.setImpContractNo(bcsQPContract.getImpContractNo());
		contract.setExpContractNo(bcsQPContract.getExpContractNo());
		contract.setImgAmount(bcsQPContract.getImgAmount());
		contract.setExgAmount(bcsQPContract.getExgAmount());
		contract.setCurr((Curr) this.contractDao.findCustomBaseEntity("Curr",
				"code", bcsQPContract.getCurrCode()));
		contract.setWardshipRate(bcsQPContract.getWardshipRate());
		contract.setWardshipFee(bcsQPContract.getWardshipFee());
		contract.setTransac((Transac) this.contractDao.findCustomBaseEntity(
				"Transac", "code", bcsQPContract.getTransacCode()));
		contract.setMemo(bcsQPContract.getMemo());
		contract.setCorrEmsNo(bcsQPContract.getCorrEmsNo());
		contract.setInputER(bcsQPContract.getInputER());
		contract.setIsContractEms(true);
		contract.setDeclareState(DeclareState.PROCESS_EXE);
		contract.setModifyMark(ModifyMarkState.UNCHANGE);

		contract.setEnterpriseAddress(((Company) CommonUtils.getCompany())
				.getAddress());
		contract.setLinkMan(((Company) CommonUtils.getCompany()).getLinkman());
		// contract.setContactTel(((Company)CommonUtils.getCompany()).getLinkman());
		contract.setIsImportFromQP(true);

		this.contractDao.saveContract(contract);
		return contract;
	}

	/**
	 * 导入通关备案料件
	 * 
	 * @param ptsEmsListAImg
	 * @param contract
	 */
	private void importContractImg(List ptsEmsListAImg, Contract contract) {
		Map<Integer, ContractImg> imgBillMap = new HashMap<Integer, ContractImg>();
		List list = this.contractDao.findContractImgByContractId(contract
				.getId());
		for (int i = 0; i < list.size(); i++) {
			ContractImg contractImg = (ContractImg) list.get(i);
			imgBillMap.put(contractImg.getSeqNum(), contractImg);
		}
		for (int i = 0; i < ptsEmsListAImg.size(); i++) {
			BcsQPContractImg qpImgBill = (BcsQPContractImg) ptsEmsListAImg
					.get(i);
			ContractImg contractImg = imgBillMap.get(qpImgBill.getSeqNum());
			if (contractImg == null) {
				contractImg = new ContractImg();
				contractImg.setContract(contract);
			}
			contractImg.setSeqNum(qpImgBill.getSeqNum());
			contractImg.setCredenceNo(qpImgBill.getCredenceNo());
			contractImg.setComplex(getComplex(qpImgBill.getComplexCode()));
			contractImg.setName(qpImgBill.getName());
			contractImg.setSpec(qpImgBill.getSpec());
			contractImg.setDeclarePrice(qpImgBill.getDeclarePrice());
			contractImg.setAmount(qpImgBill.getAmount());
			contractImg.setTotalPrice(qpImgBill.getTotalPrice());
			contractImg.setUnit((Unit) this.contractDao.findCustomBaseEntity(
					"Unit", "code", qpImgBill.getUnitCode()));
			contractImg.setUnitWeight(qpImgBill.getUnitWeight());
			contractImg.setTotalWeight(qpImgBill.getTotalWeight());
			contractImg.setCountry((Country) this.contractDao
					.findCustomBaseEntity("Country", "code", qpImgBill
							.getCountryCode()));
			contractImg.setLevyMode((LevyMode) this.contractDao
					.findCustomBaseEntity("LevyMode", "code", qpImgBill
							.getLevyModeCode()));
			if (contractImg.getLevyMode() == null) {
				contractImg.setLevyMode((LevyMode) this.contractDao
						.findCustomBaseEntity("LevyMode", "code", "3"));
			}
			contractImg.setNote(qpImgBill.getNote());
			contractImg.setLegalUnitGene(qpImgBill.getLegalUnitGene());
			contractImg.setLegalUnit2Gene(qpImgBill.getLegalUnit2Gene());
			// imgBill.set(qpImgBill.getWeigthUnitGene());
			contractImg.setDutyRate(qpImgBill.getDutyRate());
			// imgBill.setDutyRatio(qpImgBill.get());
			contractImg.setDetailNote(qpImgBill.getDetailNote());
			contractImg.setModifyMark(ModifyMarkState.UNCHANGE);
			this.contractDao.saveContractImg(contractImg);
		}
	}

	/**
	 * 导入通关备案成品
	 * 
	 * @param ptsEmsListAExg
	 * @param contract
	 */
	private void importContractExg(List ptsEmsListAExg, Contract contract) {
		Map<Integer, ContractExg> exgBillMap = new HashMap<Integer, ContractExg>();
		List list = this.contractDao
				.findContractExgByParentId(contract.getId());
		for (int i = 0; i < list.size(); i++) {
			ContractExg contractExg = (ContractExg) list.get(i);
			exgBillMap.put(contractExg.getSeqNum(), contractExg);
		}
		for (int i = 0; i < ptsEmsListAExg.size(); i++) {
			BcsQPContractExg qpExgBill = (BcsQPContractExg) ptsEmsListAExg
					.get(i);
			ContractExg contractExg = exgBillMap.get(qpExgBill.getSeqNum());
			if (contractExg == null) {
				contractExg = new ContractExg();
				contractExg.setContract(contract);
			}
			contractExg.setSeqNum(qpExgBill.getSeqNum());
			contractExg.setCredenceNo(qpExgBill.getCredenceNo());
			contractExg.setComplex(getComplex(qpExgBill.getComplexCode()));
			contractExg.setName(qpExgBill.getName());
			contractExg.setSpec(qpExgBill.getSpec());
			contractExg.setUnitPrice(qpExgBill.getUnitPrice());
			contractExg.setExportAmount(qpExgBill.getExportAmount());
			contractExg.setTotalPrice(qpExgBill.getTotalPrice());
			contractExg.setUnit((Unit) this.contractDao.findCustomBaseEntity(
					"Unit", "code", qpExgBill.getUnitCode()));
			contractExg.setCountry((Country) this.contractDao
					.findCustomBaseEntity("Country", "code", qpExgBill
							.getCountryCode()));
			contractExg.setLevyMode((LevyMode) this.contractDao
					.findCustomBaseEntity("LevyMode", "code", qpExgBill
							.getLevyModeCode()));
			if (contractExg.getLevyMode() == null) {
				contractExg.setLevyMode((LevyMode) this.contractDao
						.findCustomBaseEntity("LevyMode", "code", "3"));
			}
			contractExg.setNote(qpExgBill.getNote());
			contractExg.setLegalUnitGene(qpExgBill.getLegalUnitGene());
			contractExg.setLegalUnit2Gene(qpExgBill.getLegalUnit2Gene());
			// exgBill.setWeigthUnitGene(qpExgBill.getWeigthUnitGene());
			contractExg.setDutyRate(qpExgBill.getDutyRate());
			// exgBill.setDutyRatio(qpExgBill.getDutyRatio());
			contractExg.setModifyMark(ModifyMarkState.UNCHANGE);
			this.contractDao.saveContractExg(contractExg);
		}
	}

	/**
	 * 导入通关备案单耗
	 * 
	 * @param ptsEmsListAExg
	 * @param contract
	 */
	private void importContractBom(List ptsEmsListCm, Contract contract) {
		Map<Integer, ContractExg> exgBillMap = new HashMap<Integer, ContractExg>();
		List list = this.contractDao
				.findContractExgByParentId(contract.getId());
		for (int i = 0; i < list.size(); i++) {
			ContractExg contractExg = (ContractExg) list.get(i);
			exgBillMap.put(contractExg.getSeqNum(), contractExg);
		}
		Map<Integer, ContractImg> imgBillMap = new HashMap<Integer, ContractImg>();
		list = this.contractDao.findContractImgByContractId(contract.getId());
		for (int i = 0; i < list.size(); i++) {
			ContractImg imgBill = (ContractImg) list.get(i);
			imgBillMap.put(imgBill.getSeqNum(), imgBill);
		}
		Map<String, ContractBom> bomBillMap = new HashMap<String, ContractBom>();
		list = this.contractDao.findContractBomByContract(contract.getId());
		for (int i = 0; i < list.size(); i++) {
			ContractBom bomBill = (ContractBom) list.get(i);
			if(bomBill.getContractExg()==null){
				continue;
			}
			bomBillMap.put(bomBill.getContractExg().getSeqNum() + "-"
					+ bomBill.getContractImgSeqNum(), bomBill);
		}
		for (int i = 0; i < ptsEmsListCm.size(); i++) {
			BcsQPContractBom qpBomBill = (BcsQPContractBom) ptsEmsListCm.get(i);
			ContractExg exgBill = exgBillMap.get(qpBomBill.getExgSeqNum());
			ContractImg imgBill = imgBillMap.get(qpBomBill.getImgSeqNum());
			if(exgBill==null||imgBill==null){
				continue;
			}
			ContractBom contractBom = bomBillMap.get(qpBomBill.getExgSeqNum()
					+ "-" + qpBomBill.getImgSeqNum());
			if (contractBom == null) {
				contractBom = new ContractBom();
				contractBom.setContractExg(exgBill);
			}
			contractBom.setContractImgSeqNum(qpBomBill.getImgSeqNum());
			contractBom.setComplex(imgBill.getComplex());
			contractBom.setName(imgBill.getName());
			contractBom.setSpec(imgBill.getSpec());
			contractBom.setDeclarePrice(imgBill.getDeclarePrice());
			contractBom.setUnitWaste(qpBomBill.getUnitWare());
			contractBom.setWaste(qpBomBill.getWare());
//			contractBom.setNonMnlRatio(qpBomBill.getNonMnlRatio());//非保税料件比例%
			Double totalExgAmount = CommonUtils.getDoubleExceptNull(exgBill
					.getExportAmount());

			Double unitDosage = 0.0;
			if (contractBom.getWaste() != null && contractBom.getWaste() != 0.0) {
				unitDosage = CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(contractBom.getUnitWaste())
						/ (1.0 - CommonUtils.getDoubleExceptNull(contractBom
								.getWaste()) / 100.0), 5);
			}

			contractBom.setUnitDosage(unitDosage);
			contractBom.setAmount(CommonUtils.getDoubleByDigit(totalExgAmount
					* unitDosage, 6));
			contractBom.setTotalPrice(CommonUtils.getDoubleByDigit(CommonUtils
					.getDoubleExceptNull(contractBom.getDeclarePrice())
					* CommonUtils.getDoubleExceptNull(contractBom.getAmount()),
					6));
			contractBom.setUnit(imgBill.getUnit());
			contractBom.setCountry(imgBill.getCountry());
			contractBom.setModifyMark(ModifyMarkState.UNCHANGE);
			this.contractDao.saveContractBom(contractBom);
		}
	}

	/**
	 * 从QP中导入电子化手册备案资料库
	 * 
	 * @param tempQPEmsPorData
	 */
	public void importBcsDictPorHeadFromQP(List listPtsEmsWjHead,
			boolean isOverWrite) {
		for (int i = 0; i < listPtsEmsWjHead.size(); i++) {
			BcsQPDictPorHead bcsQPDictPorHead = (BcsQPDictPorHead) listPtsEmsWjHead
					.get(i);
			String tradeCode = bcsQPDictPorHead.getTradeCode();// 经营单位
			String copEmsNo = bcsQPDictPorHead.getCopEmsNo();// 内部编号
			BcsParameterSet paraSet = contractDao.findBcsParameterSet();
			TempQPDictPorData tempQPEmsTrData = BcsQpServiceClient.getInstance(
					paraSet).findTempQPDictPorData(
					paraSet.getRemoteHostICPwd(), tradeCode, copEmsNo, true);// 根据经营单位
			// ，内部编号
			// 抓取单据资料
			importBcsDictPorHead(tempQPEmsTrData, isOverWrite);
		}
	}

	/**
	 * 从QP中导入电子手册合同备案
	 * 
	 * @param tempQPEmsTrData
	 */
	private void importBcsDictPorHead(TempQPDictPorData tempQPEmsTrData,
			boolean isOverWrite) {
		if (tempQPEmsTrData.getPtsDictPorHead() == null) {
			throw new RuntimeException("从远程服务中抓取的合同备案表头为空");
		}
		String emsNo = tempQPEmsTrData.getPtsDictPorHead().getDictPorEmsNo()
				.trim();
		BcsDictPorHead bcsDictPorHead = this.bcsDictPorDao
				.findExingBcsDictPorHeadByEmsNo(emsNo);
		if (bcsDictPorHead != null && !isOverWrite) {
			return;
		}
		bcsDictPorHead = this.importBcsDictPorHead(tempQPEmsTrData
				.getPtsDictPorHead());
		if (tempQPEmsTrData.getPtsDictListImg() != null) {
			this.importBcsDictPorImg(tempQPEmsTrData.getPtsDictListImg(),
					bcsDictPorHead);
		}
		if (tempQPEmsTrData.getPtsDictListExg() != null) {
			this.importBcsDictPorExg(tempQPEmsTrData.getPtsDictListExg(),
					bcsDictPorHead);
		}
	}

	/**
	 * 导入合同备案表头
	 * 
	 * @param ptsEmsHead
	 */
	private BcsDictPorHead importBcsDictPorHead(
			BcsQPDictPorHead bcsQPDictPorHead) {
		if (bcsQPDictPorHead.getDictPorEmsNo() == null
				|| "".equals(bcsQPDictPorHead.getDictPorEmsNo().trim())) {
			throw new RuntimeException("从远程服务中抓取的备案资料库"
					+ bcsQPDictPorHead.getCopEmsNo() + "的手册号为空");
		}
		String emsNo = bcsQPDictPorHead.getDictPorEmsNo().trim();
		BcsDictPorHead bcsDictPorHead = this.bcsDictPorDao
				.findExingBcsDictPorHeadByEmsNo(emsNo);
		if (bcsDictPorHead == null) {
			bcsDictPorHead = new BcsDictPorHead();
			bcsDictPorHead.setSeqNum(Integer.parseInt(this.contractDao.getNum(
					"BcsDictPorHead", "seqNum")));
		}
		bcsDictPorHead.setDictPorEmsNo(bcsQPDictPorHead.getDictPorEmsNo());
		// dzscEmsPorWjHead.setSancEmsNo(tempPtsEmsWjHead.());
		bcsDictPorHead.setTradeCode(bcsQPDictPorHead.getTradeCode());
		bcsDictPorHead.setTradeName(bcsQPDictPorHead.getTradeName());
		bcsDictPorHead.setMachCode(bcsQPDictPorHead.getMachCode());
		bcsDictPorHead.setMachName(bcsQPDictPorHead.getMachName());
		bcsDictPorHead.setBeginDate(CommonUtils
				.convertStrToDate(bcsQPDictPorHead.getBeginDate()));
		bcsDictPorHead.setEndDate(CommonUtils.convertStrToDate(bcsQPDictPorHead
				.getEndDate()));
		// dzscEmsPorWjHead.setOutTradeCo(tempPtsEmsWjHead.getOutTradeCo());
		// dzscEmsPorWjHead.setLevyMode((LevyMode) this.contractDao
		// .findCustomBaseEntity("LevyMode", "code", tempPtsEmsWjHead
		// .getLevyModeCode()));
		bcsDictPorHead.setLevyKind((LevyKind) this.contractDao
				.findCustomBaseEntity("LevyKind", "code", bcsQPDictPorHead
						.getLevyKindCode()));
		// dzscEmsPorWjHead.setPayMode((PayMode) this.contractDao
		// .findCustomBaseEntity("PayMode", "code", tempPtsEmsWjHead
		// .getPayModeCode()));
		bcsDictPorHead.setMachiningType((MachiningType) this.contractDao
				.findCustomBaseEntity("MachiningType", "code", bcsQPDictPorHead
						.getMachiningTypeCode()));
		// dzscEmsPorWjHead.setEmsApprNo(tempPtsEmsWjHead.get());
		bcsDictPorHead.setReceiveArea((District) this.contractDao
				.findCustomBaseEntity("District", "code", bcsQPDictPorHead
						.getReceiveAreaCode()));
		// dzscEmsPorWjHead.setInvestMode((InvestMode) this.contractDao
		// .findCustomBaseEntity("InvestMode", "code", tempPtsEmsWjHead
		// .getInvestModeCode()));
		// dzscEmsPorWjHead.setFetchInMode((FetchInMode) this.contractDao
		// .findCustomBaseEntity("FetchInMode", "code", tempPtsEmsWjHead
		// .getFetchInModeCode()));
		// dzscEmsPorWjHead.setInSaleRate(tempPtsEmsWjHead.getInSaleRate());
		bcsDictPorHead.setDeclareDate(CommonUtils
				.convertStrToDate(bcsQPDictPorHead.getDeclareDate()));
		// dzscEmsPorWjHead.set(tempPtsEmsWjHead
		// .getMaterielItemCount());
		// dzscEmsPorWjHead.setMachineCount(tempPtsEmsWjHead.getMachineCount());
		// dzscEmsPorWjHead.setProductItemCount(tempPtsEmsWjHead
		// .getProductItemCount());
		bcsDictPorHead.setManageObject(bcsQPDictPorHead.getManageObject());
		bcsDictPorHead.setLimitFlag(bcsQPDictPorHead.getLimitFlag());
		// dzscEmsPorWjHead.setEquipMode(tempPtsEmsWjHead.getEquipMode());
		bcsDictPorHead.setDeclareCustoms((Customs) this.contractDao
				.findCustomBaseEntity("Customs", "code", bcsQPDictPorHead
						.getDeclareCustomsCode()));
		bcsDictPorHead.setRedDep((RedDep) this.contractDao
				.findCustomBaseEntity("RedDep", "code", bcsQPDictPorHead
						.getRedDepCode()));
		// dzscEmsPorWjHead.set(tempPtsEmsWjHead.getEmsType());
		bcsDictPorHead.setCopEmsNo(bcsQPDictPorHead.getCopEmsNo());
		bcsDictPorHead.setProductRatio(bcsQPDictPorHead.getProductRatio());
		// dzscEmsPorWjHead.setIePort1((Customs) this.contractDao
		// .findCustomBaseEntity("Customs", "code", tempPtsEmsWjHead
		// .getIePort1Code()));
		// dzscEmsPorWjHead.setIePort2((Customs) this.contractDao
		// .findCustomBaseEntity("Customs", "code", tempPtsEmsWjHead
		// .getIePort2Code()));
		// dzscEmsPorWjHead.setIePort3((Customs) this.contractDao
		// .findCustomBaseEntity("Customs", "code", tempPtsEmsWjHead
		// .getIePort3Code()));
		// dzscEmsPorWjHead.setIePort4((Customs) this.contractDao
		// .findCustomBaseEntity("Customs", "code", tempPtsEmsWjHead
		// .getIePort4Code()));
		// dzscEmsPorWjHead.setIePort5((Customs) this.contractDao
		// .findCustomBaseEntity("Customs", "code", tempPtsEmsWjHead
		// .getIePort5Code()));
		// dzscEmsPorWjHead.setDeferDate(CommonUtils
		// .convertStrToDate(tempPtsEmsWjHead.getDeferDate()));
		// dzscEmsPorWjHead.setDestroyDate(CommonUtils
		// .convertStrToDate(tempPtsEmsWjHead.getDestroyDate()));
		bcsDictPorHead.setTrade((Trade) this.contractDao.findCustomBaseEntity(
				"Trade", "code", bcsQPDictPorHead.getTradeModeCode()));
		// dzscEmsPorWjHead.setTradeCountry((Country) this.contractDao
		// .findCustomBaseEntity("Country", "code", tempPtsEmsWjHead
		// .getTradeCountryCode()));
		// dzscEmsPorWjHead.setEnterpriseAddress(tempPtsEmsWjHead
		// .getEnterpriseAddress());
		// dzscEmsPorWjHead.setLinkMan(tempPtsEmsWjHead.getLinkMan());
		// dzscEmsPorWjHead.setContactTel(tempPtsEmsWjHead.getContactTel());
		// dzscEmsPorWjHead.setAgreementNo(tempPtsEmsWjHead.getAgreementNo());
		// dzscEmsPorWjHead.setIeContractNo(tempPtsEmsWjHead.getIeContractNo());
		// dzscEmsPorWjHead.setImContractNo(tempPtsEmsWjHead.getImContractNo());
		// dzscEmsPorWjHead.setImgAmount(tempPtsEmsWjHead.getImgAmount());
		// dzscEmsPorWjHead.setExgAmount(tempPtsEmsWjHead.getExgAmount());
		bcsDictPorHead.setCurr((Curr) this.contractDao.findCustomBaseEntity(
				"Curr", "code", bcsQPDictPorHead.getCurrCode()));
		// dzscEmsPorWjHead.setWardshipRate(tempPtsEmsWjHead.getWardshipRate());
		// dzscEmsPorWjHead.setWardshipFee(tempPtsEmsWjHead.getWardshipFee());
		// dzscEmsPorWjHead.setTransac((Transac) this.contractDao
		// .findCustomBaseEntity("Transac", "code", tempPtsEmsWjHead
		// .getTransacCode()));
		// dzscEmsPorWjHead.setNote(tempPtsEmsWjHead.getNote());
		// dzscEmsPorWjHead.setLastEmsNo(tempPtsEmsWjHead.getLastEmsNo());
		// dzscEmsPorWjHead.setCorrEmsNo(tempPtsEmsWjHead.getCorrEmsNo());
		bcsDictPorHead.setInputER(bcsQPDictPorHead.getInputER());
		bcsDictPorHead.setExgRangeSpec(bcsQPDictPorHead.getExgRangeSpec());
		bcsDictPorHead.setImgRangeSpec(bcsQPDictPorHead.getImgRangeSpec());
		bcsDictPorHead.setDeclareState(DeclareState.PROCESS_EXE);
		bcsDictPorHead.setModifyMark(ModifyMarkState.UNCHANGE);

		this.bcsDictPorDao.saveOrUpdate(bcsDictPorHead);
		return bcsDictPorHead;
	}

	/**
	 * 导入合同备案料件
	 * 
	 * @param ptsTrListImg
	 * @param dictPorHead
	 */
	private void importBcsDictPorImg(List ptsTrListImg,
			BcsDictPorHead dictPorHead) {
		Map<Integer, BcsDictPorImg> imgBillMap = new HashMap<Integer, BcsDictPorImg>();
		List list = this.bcsDictPorDao.findBcsDictPorImgByHead(dictPorHead);
		for (int i = 0; i < list.size(); i++) {
			BcsDictPorImg imgBill = (BcsDictPorImg) list.get(i);
			imgBillMap.put(imgBill.getSeqNum(), imgBill);
		}
		for (int i = 0; i < ptsTrListImg.size(); i++) {
			BcsQPDictPorImg qpImgBill = (BcsQPDictPorImg) ptsTrListImg.get(i);
			BcsDictPorImg imgBill = imgBillMap.get(qpImgBill.getSeqNum());
			if (imgBill == null) {
				imgBill = new BcsDictPorImg();
				imgBill.setDictPorHead(dictPorHead);
			}
			imgBill.setSeqNum(qpImgBill.getSeqNum());
			imgBill.setComplex(getComplex(qpImgBill.getComplexCode()));
			imgBill.setCommName(qpImgBill.getCommName());
			imgBill.setCommSpec(qpImgBill.getCommSpec());
			imgBill.setUnitPrice(qpImgBill.getUnitPrice());
			imgBill.setComUnit((Unit) this.contractDao.findCustomBaseEntity(
					"Unit", "code", qpImgBill.getComUnitCode()));
			imgBill.setIsSelected(qpImgBill.getIsMainImg());
			imgBill.setCurr((Curr) this.contractDao.findCustomBaseEntity(
					"Curr", "code", qpImgBill.getCurrCode()));
			imgBill.setMemo(qpImgBill.getMemo());
			imgBill.setModifyMark(ModifyMarkState.UNCHANGE);
			this.bcsDictPorDao.saveOrUpdate(imgBill);
		}
	}

	/**
	 * 导入合同备案成品
	 * 
	 * @param ptsTrListExg
	 * @param dictPorHead
	 */
	private void importBcsDictPorExg(List ptsTrListExg,
			BcsDictPorHead dictPorHead) {
		Map<Integer, BcsDictPorExg> imgBillMap = new HashMap<Integer, BcsDictPorExg>();
		List list = this.bcsDictPorDao.findBcsDictPorExgByHead(dictPorHead);
		for (int i = 0; i < list.size(); i++) {
			BcsDictPorExg imgBill = (BcsDictPorExg) list.get(i);
			imgBillMap.put(imgBill.getSeqNum(), imgBill);
		}
		for (int i = 0; i < ptsTrListExg.size(); i++) {
			BcsQPDictPorExg qpExgBill = (BcsQPDictPorExg) ptsTrListExg.get(i);
			BcsDictPorExg exgBill = imgBillMap.get(qpExgBill.getSeqNum());
			if (exgBill == null) {
				exgBill = new BcsDictPorExg();
				exgBill.setDictPorHead(dictPorHead);
			}
			exgBill.setSeqNum(qpExgBill.getSeqNum());
			exgBill.setComplex(getComplex(qpExgBill.getComplexCode()));
			exgBill.setCommName(qpExgBill.getCommName());
			exgBill.setCommSpec(qpExgBill.getCommSpec());
			exgBill.setUnitPrice(qpExgBill.getUnitPrice());
			exgBill.setComUnit((Unit) this.contractDao.findCustomBaseEntity(
					"Unit", "code", qpExgBill.getComUnitCode()));
			exgBill.setCurr((Curr) this.contractDao.findCustomBaseEntity(
					"Curr", "code", qpExgBill.getCurrCode()));
			exgBill.setMemo(qpExgBill.getMemo());
			exgBill.setModifyMark(ModifyMarkState.UNCHANGE);
			this.bcsDictPorDao.saveOrUpdate(exgBill);
		}
	}

}
