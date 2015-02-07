package com.bestway.pis.logic;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationCommInfo;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationContainer;
import com.bestway.bcus.custombase.entity.CustomBaseEntity;
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
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.LicenseDocu;
import com.bestway.bcus.custombase.entity.parametercode.SrtJzx;
import com.bestway.bcus.custombase.entity.parametercode.SrtTj;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.custombase.entity.parametercode.Uses;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.enc.entity.CustomsDeclarationCommInfo;
import com.bestway.bcus.enc.entity.CustomsDeclarationContainer;
import com.bestway.bcus.enc.entity.CustomsDeclarationDelete;
import com.bestway.bcus.enc.entity.CustomsDeclarationDeleteCommInfo;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.customs.common.entity.BaseCustomsDeclarationContainer;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclarationCommInfo;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclarationContainer;
import com.bestway.pis.constant.PisSyncState;
import com.bestway.pis.dao.PisDao;
import com.bestway.pis.entity.BrokerCorp;
import com.bestway.pis.entity.EspAuthorityBrokerCorp;
import com.bestway.pis.entity.EspAuthorityBrokerCorpItem;
import com.bestway.pis.entity.PisEspServer;
import com.bestway.pis.entity.PisSyncAclUser;
import com.bestway.pis.entity.PisSyncAgentCorp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class PisLogic {

	private PisDao pisDao;

	public void setPisDao(PisDao pisDao) {
		this.pisDao = pisDao;
	}

	// /////////////////////////////////////PisDecLogicImpl///////////////////////////////////////////////

	public <T> T saveOrUpdate(Request request, T entity) {
		pisDao.saveOrUpdate(entity);
		return entity;
	}

	/**
	 * 查询加工贸易报关单
	 *
	 * @param request
	 * @param impExpFlag
	 * @param beginDate
	 * @param endDate
	 * @param isCheck
	 * @param isSend
	 * @param projectType
	 * @return
	 */
	public List<BaseCustomsDeclaration> findDecHead(Request request,
			Integer impExpFlag, String emsNo, Date beginDate, Date endDate,
			Boolean isCheck, Boolean isSend, Integer projectType) {
		return pisDao.findDecHead(impExpFlag, emsNo, beginDate, endDate,
				isCheck, isSend, projectType);
	}

	/**
	 * 导出数据到物流&报关平台
	 *
	 * @param request
	 * @param head
	 * @return
	 */
	public String makeDecJSONData(Request request, BaseCustomsDeclaration head,
			Integer projectType) {
		List<BaseCustomsDeclarationCommInfo> items = this.pisDao.findDecItem(
				head, projectType);
		List<BaseCustomsDeclarationContainer> dcs = this.pisDao
				.findDecContainer(head, projectType);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("报关单表头", exportHead(head));
		jsonMap.put("报关单表体", exportItems(items, head.getImpExpFlag()));
		jsonMap.put("集装箱", exportContainers(dcs));
		jsonMap.put("随附单证", exportdocAttachment(head));
		// return "[" + mapToJSON(jsonMap) + "]";
		return mapToJSON(jsonMap);
	}

	private Map<String, Object> exportHead(BaseCustomsDeclaration head) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("报送海关", keyValue(head.getDeclarationCustoms()));
		map.put("统一编号", head.getUnificationCode());
		if (head.getImpExpFlag().intValue() == 0) {
			map.put("进出口标志", "I");
		} else if (head.getImpExpFlag().intValue() == 1) {
			map.put("进出口标志", "E");
		} else if (head.getImpExpFlag().intValue() == 2) {
			switch (head.getImpExpType()) {
			case ImpExpType.GENERAL_TRADE_IMPORT:
			case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES:
			case ImpExpType.EQUIPMENT_IMPORT:
			case ImpExpType.BACK_PORT_REPAIR:
			case ImpExpType.IMPORT_STORAGE:
			case ImpExpType.MATERIAL_DOMESTIC_SALES:
			case ImpExpType.MATERIAL_EXCHANGE:
			case ImpExpType.REMAIN_FORWARD_IMPORT:
				map.put("进出口标志", "I");
				break;
			case ImpExpType.REMIAN_MATERIAL_BACK_PORT:
			case ImpExpType.GENERAL_TRADE_EXPORT:
			case ImpExpType.EQUIPMENT_BACK_PORT:
			case ImpExpType.REMAIN_FORWARD_EXPORT:
			case ImpExpType.EXPORT_STORAGE:
			case ImpExpType.MATERIAL_REOUT:
				map.put("进出口标志", "E");
				break;
			default:
				break;
			}

		}
		map.put("预录入编号", "");
		map.put("海关编号", head.getCustomsDeclarationCode());
		map.put("进出口口岸", keyValue(head.getCustoms()));
		map.put("备案号", head.getEmsHeadH2k());
		map.put("合同协议号", head.getContract());
		map.put("进出口日期", head.getImpExpDate());
		map.put("申报日期", head.getDeclarationDate());
		map.put("经营单位编码", head.getTradeCode());
		map.put("经营单位名称", head.getTradeName());
		map.put("运输方式", keyValue(head.getTransferMode()));
		// if (isExp) {
		// map.put("发货单位编码", head.getOwnerCode());
		// map.put("发货单位名称", head.getOwnerName());
		// } else {
		// map.put("收货单位编码", head.getOwnerCode());
		// map.put("收货单位名称", head.getOwnerName());
		// }
		map.put("发(收)货单位编码", head.getMachCode());
		map.put("发(收)货单位名称", head.getMachName());

		map.put("运输工具名称", head.getConveyance());
		if (head.getDeclarationCompany() != null) {
			map.put("申报单位编码", head.getDeclarationCompany().getCode());
			map.put("申报单位名称", head.getDeclarationCompany().getName());
		}
		map.put("航次号", head.getVoyageNo());
		map.put("提运单号", head.getBillOfLading());
		map.put("监管方式", keyValue(head.getTradeMode()));
		map.put("征免性质", keyValue(head.getLevyKind()));
		map.put("征免比例", trimToEmpty(head.getPerTax()));
		map.put("纳税单位", "");
		map.put("许可证号", head.getLicense());
		// if (isExp) {
		// map.put("运抵国", keyValue(head.getCountry()));
		// map.put("指运港", keyValue(head.getPortLin()));
		// map.put("境内货原地", keyValue(head.getDistrict()));
		// } else {
		// map.put("启运国", keyValue(head.getCountry()));
		// map.put("装货港", keyValue(head.getPortLin()));
		// map.put("境内目的地", keyValue(head.getDistrict()));
		// }
		map.put("运抵(启运)国", keyValue(head.getCountryOfLoadingOrUnloading()));
		map.put("指运(装货)港", keyValue(head.getPortOfLoadingOrUnloading()));
		map.put("境内货原(境内目的)地", keyValue(head.getDomesticDestinationOrSource()));

		map.put("批准文号", "");
		map.put("成交方式", keyValue(head.getTransac()));
		// map.put("车次", head.getTrainNo());
		if (head.getIncidentalExpensesType() != null) {
			map.put("杂费1", String.valueOf(head.getIncidentalExpensesType()));
			map.put("杂费2", trimToEmpty(head.getIncidentalExpenses()));
			map.put("杂费3", keyValue(head.getOtherCurr()));
		}
		if (head.getFreightageType() != null) {
			map.put("运费1", String.valueOf(head.getFreightageType()));
			map.put("运费2", trimToEmpty(head.getFreightage()));
			map.put("运费3", keyValue(head.getFeeCurr()));
		}
		if (head.getInsuranceType() != null) {
			map.put("保费1", String.valueOf(head.getInsuranceType()));
			map.put("保费2", trimToEmpty(head.getInsurance()));
			map.put("保费3", keyValue(head.getInsurCurr()));
		}

		map.put("结汇方式", keyValue(head.getBalanceMode()));
		map.put("装卸口岸", keyValue(head.getPredock()));
		map.put("生产厂家", head.getManufacturer() == null ? "" : head
				.getManufacturer().getName());
		map.put("补充报关类型", trimToEmpty(head.getSupplmentType()));
		map.put("用途", keyValue(head.getUses()));

		map.put("境外运输工具编号", head.getOverseasConveyanceCode());
		map.put("境外运输工具名称", head.getOverseasConveyanceName());
		map.put("境外运输工具航次", head.getOverseasConveyanceFlights());
		map.put("境外运输工具提单号", head.getOverseasConveyanceBillOfLading());
		map.put("境内运输方式", keyValue(head.getDomesticTransferMode()));
		map.put("境内运输工具编号", head.getDomesticConveyanceCode());
		map.put("境内运输工具名称", head.getDomesticConveyanceName());
		map.put("境内运输工具航次", head.getDomesticConveyanceFlights());
		// map.put("预计抵运日期", head.getPlanArriveDate());

		map.put("件数", trimToEmpty(head.getCommodityNum()));
		map.put("包装种类", head.getWrapType() == null ? "" : head.getWrapType()
				.getName());
		map.put("毛重", trimToEmpty(head.getGrossWeight()));
		map.put("净重", trimToEmpty(head.getNetWeight()));
		map.put("集装箱号", trimToEmpty(head.getContainerNum()));
		map.put("随附单证", trimToEmpty(head.getAttachedBill()));
		map.put("报关单类型", head.getTcsEntryType());
		map.put("报关标志", head.getTcsEdiId());
		if (head.getTcsType() == null || "".equals(head.getTcsType().trim())) {
			map.put("EDI申报备注", "*");
		} else {
			map.put("EDI申报备注", head.getTcsType());
		}
		map.put("备注", trimToEmpty(head.getMemos()));
		// if (head.getDeclarant() != null) {
		// map.put("报关员", head.getDeclarant().getName());
		// map.put("联系方式", head.getDeclarant().getTel());
		// }
		map.put("关联报关单", head.getRelativeId());
		map.put("关联备案", head.getRelativeManualNo());
		map.put("监管仓号", head.getBondedWarehouse());
		// map.put("货场代码", head.getFreightCode());
		map.put("发票号码", trimToEmpty(head.getInvoiceCode()));
		map.put("ESPTASKID", head.getEspTaskId());
		return map;
	}

	/**
	 * 导出报关单表体
	 *
	 * @param items
	 * @param impExpFlag
	 * @return
	 */
	private List<Map<String, String>> exportItems(
			List<BaseCustomsDeclarationCommInfo> items, Integer impExpFlag) {
		// 是否出口
		boolean isExp = ImpExpFlag.EXPORT == impExpFlag;
		List<Map<String, String>> itmLs = new ArrayList<Map<String, String>>();
		Map<String, String> m;
		for (BaseCustomsDeclarationCommInfo item : items) {
			m = new HashMap<String, String>();
			m.put("商品序号", trimToEmpty(item.getSerialNumber()));
			m.put("备案序号", trimToEmpty(item.getCommSerialNo()));
			if (item.getComplex().getCode().length() == 10) {
				m.put("商品编码", StringUtils.substring(
						item.getComplex().getCode(), 0, 8));
				m.put("附加编码",
						StringUtils.substring(item.getComplex().getCode(), 8));
			}
			m.put("商品名称", item.getCommName());
			m.put("规格型号", item.getDeclareSpec());
			// m.put("申报要素", item.getDeclareSpec());
			m.put("成交数量", trimToEmpty(item.getCommAmount()));
			m.put("成交单位", keyValue(item.getUnit()));
			m.put("成交单价", trimToEmpty(item.getCommUnitPrice()));
			m.put("成交总价", trimToEmpty(item.getCommTotalPrice()));
			m.put("币制",
					keyValue(item.getBaseCustomsDeclaration().getCurrency()));
			m.put("汇率", trimToEmpty(item.getBaseCustomsDeclaration()
					.getExchangeRate()));
			m.put("法定数量", trimToEmpty(item.getFirstAmount()));
			m.put("法定单位", keyValue(item.getLegalUnit()));
			m.put("版本号", trimToEmpty(item.getVersion()));
			m.put("箱号", item.getBoxNo());
			m.put("用途", keyValue(item.getUses()));
			m.put("第二数量", trimToEmpty(item.getSecondAmount()));
			m.put("第二单位", keyValue(item.getSecondLegalUnit()));
			// if (isExp) {
			// m.put("目的地", keyValue(item.getCountry()));
			// } else {
			// m.put("原产地", keyValue(item.getCountry()));
			// }
			m.put("目的(原产)地", keyValue(item.getCountry()));
			m.put("征免", keyValue(item.getLevyMode()));
			m.put("件数", trimToEmpty(item.getPieces()));
			m.put("净重", trimToEmpty(item.getNetWeight()));
			m.put("毛重", trimToEmpty(item.getGrossWeight()));
			itmLs.add(m);
		}
		return itmLs;
	}

	/**
	 * 导出随附单证
	 *
	 * @param head
	 * @return
	 */
	private List<Map<String, String>> exportdocAttachment(
			BaseCustomsDeclaration head) {
		List<Map<String, String>> docAmntLs = new ArrayList<Map<String, String>>();
		// String docAmt = head.getAttachedBill();
		// if (StringUtils.isNotBlank(docAmt)) {
		// docAmt = StringUtils.deleteWhitespace(docAmt);
		// char[] docAmts = docAmt.toCharArray();
		// Map<String, String> docuMap = listToMap(this.pisDao
		// .findCustomBaseEntityList(LicenseDocu.class.getName()));
		// Map<String, String> m;
		// for (char c : docAmts) {
		// m = new HashMap<String, String>();
		// m.put("代码", String.valueOf(c));
		// m.put("随附单证编号", "");
		// docAmntLs.add(m);
		// }
		// }
		String certificateCode = head.getCertificateCode();
		if (certificateCode == null || "".equals(certificateCode.trim())) {
			return docAmntLs;
		}
		certificateCode = certificateCode.replace("\t", "");
		certificateCode = certificateCode.replace("\n", ",");
		certificateCode = certificateCode.replace("，", ",");
		String[] strs = certificateCode.split(",");
		for (int i = 0; i < strs.length; i++) {
			String[] str = strs[i].split(":");
			Map<String, String> m = new HashMap<String, String>();
			if (str != null && str.length > 1) {
				m.put("代码", str[0] == null ? "" : str[0]);
				m.put("随附单证编号", str[1] == null ? "" : str[1]);
				docAmntLs.add(m);
			}
		}
		return docAmntLs;
	}

	private List<Map<String, String>> exportContainers(
			List<BaseCustomsDeclarationContainer> dcs) {
		List<Map<String, String>> dcLs = new ArrayList<Map<String, String>>();
		for (BaseCustomsDeclarationContainer dc : dcs) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("集装箱号", dc.getContainerCode());
			m.put("集装箱规格", keyValue(dc.getSrtJzx()));
			m.put("集装箱托架种类", keyValue(dc.getSrttj()));
			dcLs.add(m);
		}
		return dcLs;
	}

	private String trimToEmpty(Object o) {
		return o == null ? "" : o.toString();
	}

	private String keyValue(CustomBaseEntity data) {
		if (data != null) {
			if (data instanceof PortLin) {
				// return StringUtils.join(new
				// String[]{Integer.valueOf(data.getCode()).toString(),
				// data.getName()}, "|");
				return Integer.valueOf(data.getCode()).toString();
			}
			// return StringUtils.join(new String[]{data.getCode(),
			// data.getName()}, "|");
			return data.getCode();
		}
		return "";
	}

	private Map<String, String> listToMap(List<LicenseDocu> ldocLs) {
		Map<String, String> map = new HashMap<String, String>();
		for (LicenseDocu docu : ldocLs) {
			map.put(docu.getCode(), docu.getName());
		}
		return map;
	}

	// /////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Map<String,Object>转换成JSON字符串
	 *
	 * @param data
	 * @return
	 */
	private String mapToJSON(Map<String, Object> data) {
		return gson.toJson(data);
	}

	/**
	 * 默认日期格式
	 */
	public final String DEFALUT_DATE_PATTERN = "yyyyMMdd";
	/**
	 * JSON工具
	 */
	private Gson gson = new GsonBuilder().setDateFormat(DEFALUT_DATE_PATTERN)
			.create();

	// ///////////////////////////////PisGtsDecLogicImpl////////////////////////////////////////
	// ///////////////////////////////PisSyncLogicImpl////////////////////////////////////////

	public String getCompanyCode(Request request) {
		Company company = pisDao.findIsCurrCompany();
		return company.getCode();
	}

	/**
	 * 查找代理公司
	 *
	 * @param request
	 * @return
	 */
	public List findBrokerCorp(Request request) {
		return pisDao.findBrokerCorp();
	}

	/**
	 * 查找esp服务器
	 *
	 * @param request
	 * @return
	 */
	public List findPisEspServer(Request request) {
		return pisDao.findPisEspServer();
	}

	/**
	 * 查找授权的esp服务器
	 *
	 * @param request
	 * @return
	 */
	public List findEspAuthorityBrokerCorpPisEspServer(Request request) {
		return pisDao.findEspAuthorityBrokerCorpPisEspServer();
	}

	/**
	 *
	 * 同步代理公司
	 *
	 * @param request
	 * @param serverMap
	 * @param agtCompanyList
	 */
	public void syncBrokerCorp(Request request, Map<String, Map> serverMap,
			List<Map> agtCompanyList) {

		// 1.先保存服务器信息
		Iterator<String> serverIt = serverMap.keySet().iterator();

		// 本地上的Pis服务器列表
		List<PisEspServer> tempServerList = this.pisDao.findPisEspServer();

		// 临时服务器Map
		Map<String, PisEspServer> tempPesMap = new HashMap<String, PisEspServer>();

		for (PisEspServer server : tempServerList) {

			String key = server.getServerAddress() + "/@"
					+ server.getPortNumber();

			tempPesMap.put(key, server);

		}

		// 保存的数据库的服务器
		Map<String, PisEspServer> pesMap = new HashMap<String, PisEspServer>();

		while (serverIt.hasNext()) {

			String key = serverIt.next();

			Map map = serverMap.get(key);

			String espCompanyCode = (String) map.get("espCompanyCode");

			String espCompanyName = (String) map.get("espCompanyName");

			Integer ownership = null;

			if (map.get("ownership") != null) {

				ownership = Integer.valueOf(map.get("ownership").toString());

			}

			String serverAddress = (String) map.get("serverAddress");

			String portNumber = (String) map.get("portNumber");

			String tempKey = serverAddress + "/@" + portNumber;

			PisEspServer pes = new PisEspServer();

			if (tempPesMap.containsKey(tempKey)) {

				pes = tempPesMap.get(tempKey);

			}

			pes.setEspCompanyCode(espCompanyCode);

			pes.setEspCompanyName(espCompanyName);

			pes.setOwnership(ownership);

			pes.setServerAddress(serverAddress);

			pes.setPortNumber(portNumber);

			this.pisDao.saveOrUpdate(pes);

			pesMap.put(key, pes);

		}

		// 2.保存代理公司
		List<BrokerCorp> tempBcList = this.pisDao.findBrokerCorp();

		Map<String, BrokerCorp> tempBcMap = new HashMap<String, BrokerCorp>();

		// 以 组织机构代码为 key 这里是出现 bug 的 地方 出现了相同的组织机构代码 会导致 代理公司对象不一致
		for (BrokerCorp bc : tempBcList) {

			String address = bc.getPisEspServer().getServerAddress();

			String portnumber = bc.getPisEspServer().getPortNumber();

			String orgacode = bc.getOrgaCode();

			tempBcMap.put(address + "/" + portnumber + "/" + orgacode, bc);

		}

		List<BrokerCorp> bcList = new ArrayList<BrokerCorp>();

		for (Map map : agtCompanyList) {

			String espCompanyCode = (String) map.get("espCompanyCode");

			espCompanyCode = espCompanyCode == null ? "" : espCompanyCode;

			String serverAddress = (String) map.get("serverAddress");

			serverAddress = serverAddress == null ? "" : serverAddress;

			String portNumber = (String) map.get("portNumber");

			portNumber = portNumber == null ? "" : portNumber;

			String orgaCode = (String) map.get("orgaCode");

			String orgaName = (String) map.get("orgaName");

			String customsCode = (String) map.get("customCode");

			String email = (String) map.get("email");

			String contactMan = (String) map.get("contactMan");

			String telephone = (String) map.get("telephone");

			if (StringUtils.isEmpty(orgaCode)) {
				continue;
			}

			String serverKey = espCompanyCode + "/" + serverAddress + "/"
					+ portNumber;

			if (pesMap.containsKey(serverKey)) {

				PisEspServer pes = pesMap.get(serverKey);

				BrokerCorp bc = new BrokerCorp();

				String keyx = serverAddress + "/" + portNumber + "/" + orgaCode;

				if (tempBcMap.containsKey(keyx)) {

					bc = tempBcMap.get(keyx);

				}

				bc.setPisEspServer(pes);

				bc.setSyncState(PisSyncState.SUCCEED);

				bc.setOrgaCode(orgaCode);

				bc.setOrgaName(orgaName);

				bc.setEmail(email);

				bc.setLinkMan(contactMan);

				bc.setPhoneNumber(telephone);

				if (StringUtils.isNotEmpty(customsCode)) {

					Customs cutsoms = (Customs) this.pisDao.load(Customs.class,
							customsCode);

					bc.setCustoms(cutsoms);
				}

				bcList.add(bc);
			}
		}

		this.pisDao.batchSaveOrUpdate(bcList);

		// //3.将数据库有的，运维平台没有的代理公司删除
		// List<BrokerCorp> oldBcList = this.pisDao.findBrokerCorp();
		// oldBcList.removeAll(bcList);
		// for (BrokerCorp bc : oldBcList) {
		// this.pisDao.clearBrokerCorp(bc);
		// }
		// this.pisDao.clearPisEspServer();
		// 3.将数据库有的，运维平台没有的代理公司标记为已删除，这样的原因不用删除关联的报关单
		tempBcList.removeAll(bcList);

		for (BrokerCorp bc : tempBcList) {

			bc.setIsBcgsStop(true);

		}

		this.pisDao.batchSaveOrUpdate(tempBcList);
	}

	/**
	 * 查找同步账号esp
	 *
	 * @param request
	 * @return
	 */
	public List findAclUser(Request request) {
		return this.pisDao.findAclUser();
	}

	/**
	 * 
	 * @param request
	 * @param loginNames
	 * @return
	 */
	public List findAclUserByLoginName(Request request, List loginNames,
			Class entityName) {

		if (loginNames.isEmpty()) {

			return Collections.EMPTY_LIST;

		}

		// 参数数组 包含公司ID和登录名
		Object[] params = new Object[loginNames.size() + 1];

		// 第一个是对应的公司ID

		for (int i = 0; i < loginNames.size(); i++) {

			Object obj = loginNames.get(i);

			params[i] = obj;

		}

		params[loginNames.size()] = CommonUtils.getCompany().getId();

		return pisDao.findAclUserByLoginName(params, entityName);

	}

	/**
	 * 查找同步账号esp
	 *
	 * @param request
	 * @return
	 */
	public List findPisSyncAclUser(Request request) {

		List list = this.pisDao.findPisSyncAclUser();

		// 如果 同步用户 list 为空
		if (list.isEmpty()) {

			List saveList = new ArrayList();

			List<AclUser> aclUser = pisDao.findAclUser();

			for (AclUser user : aclUser) {

				PisSyncAclUser pUser = setPisSyncAclUser(null, user);

				pUser.setSyncState(PisSyncState.NOT_SYNC);

				saveList.add(pUser);

			}

			pisDao.batchSaveOrUpdate(saveList);

			return saveList;

		} else {

			return list;

		}
	}

	public PisSyncAclUser setPisSyncAclUser(PisSyncAclUser pUser, AclUser user) {
		if (pUser == null) {
			pUser = new PisSyncAclUser();
		}
		pUser.setUserId(user.getId());
		pUser.setEmail(user.getEmail());
		pUser.setLoginName(user.getLoginName());
		pUser.setPassword(user.getPassword());
		pUser.setUserFlag(user.getUserFlag());
		pUser.setUserName(user.getUserName());
		pUser.setCompanyCode(((Company) user.getCompany()).getCode());
		pUser.setIsForbid(user.getIsForbid());
		return pUser;
	}

	/**
	 * 查找同步申报单位esp
	 *
	 * @param request
	 * @return
	 */
	public List findPisSyncAgentCorp(Request request) {
		List list = this.pisDao.findPisSyncAgentCorp();
		return list;
	}

	/**
	 * 将新添加的用户添加到PisSyncAclUser
	 *
	 * @param request
	 */
	public void refreshPisSyncAclUser(Request request) {
		List<PisSyncAclUser> pUserlist = this.pisDao.findPisSyncAclUser();
		Set<String> aclUserSet = new HashSet<String>();
		for (PisSyncAclUser puser : pUserlist) {
			aclUserSet.add(puser.getUserId());
		}

		List saveList = new ArrayList();
		List<AclUser> aclUserList = this.pisDao.findAclUser();
		Map<String, AclUser> userMap = new HashMap<String, AclUser>();
		// 添加新的PisSyncAclUser
		for (AclUser aclUser : aclUserList) {
			userMap.put(aclUser.getId(), aclUser);
			if (!aclUserSet.contains(aclUser.getId())) {
				PisSyncAclUser pUser = setPisSyncAclUser(null, aclUser);
				pUser.setSyncState(PisSyncState.NOT_SYNC);
				saveList.add(pUser);
			}
		}
		pisDao.batchSaveOrUpdate(saveList);
		// 多余的删除
		List<PisSyncAclUser> deleteList = new ArrayList<PisSyncAclUser>();
		// 更新PisSyncAclUser
		for (PisSyncAclUser pUser : pUserlist) {
			AclUser user = userMap.get(pUser.getUserId());
			if (user != null) {
				setPisSyncAclUser(pUser, user);
			} else {
				deleteList.add(pUser);
			}
		}
		pisDao.batchSaveOrUpdate(pUserlist);
		pisDao.deleteAll(deleteList);
	}

	/**
	 * 同步用户后更新
	 *
	 * @param request
	 * @param pUserlist
	 * @param infoMap
	 * @param errorInfoMap
	 * @param userErrMap
	 */
	public void updatePisSyncAclUser(Request request,
			List<PisSyncAclUser> pUserlist, Map<PisEspServer, Boolean> infoMap,
			Map<PisEspServer, Map<String, String>> userErrMap) {

		for (PisSyncAclUser user : pUserlist) {

			Iterator<PisEspServer> it = infoMap.keySet().iterator();

			String info = "";

			boolean resultIsSucceed = true;

			while (it.hasNext()) {

				PisEspServer server = it.next();

				Boolean isSucceed = infoMap.get(server); // 连接服务器是否成功

				Map<String, String> userMap = userErrMap.get(server);// 添加用户失败的

				if (!isSucceed) {

					resultIsSucceed = false;

				}
				if (!info.equals("")) {

					info += "\r\n";

				}

				String userInfo = null;

				if (userMap != null && userMap.containsKey(user.getEmail())) {

					userInfo = userMap.get(user.getEmail());

				}

				// 连接服务器成功再验证用户添加的问题
				if (isSucceed) {

					if (StringUtils.isNotEmpty(userInfo)) {

						if (userInfo.contains("成功")) {

							info += " 同步esp服务器" + server.getServerAddress()
									+ ":" + server.getPortNumber() + userInfo;

						} else if (userInfo.contains("存在")) {

							info += " 同步esp服务器" + server.getServerAddress()
									+ ":" + server.getPortNumber() + userInfo;

							resultIsSucceed = false;

						}

					}

				} else {

					info += " 同步esp服务器" + server.getServerAddress() + ":"
							+ server.getPortNumber() + "失败";
				}

			}

			user.setInfo(info);

			user.setSyncState(resultIsSucceed ? PisSyncState.SUCCEED
					: PisSyncState.ERROR);
		}

		pisDao.batchSaveOrUpdate(pUserlist);
	}

	/**
	 * 同步申报单位后更新
	 *
	 * @param request
	 * @param pAgentlist
	 * @param infoMap
	 * @param agentErrMap
	 */
	public void updatePisSyncAgentCorp(Request request,
			List<PisSyncAgentCorp> pAgentlist,
			Map<PisEspServer, Boolean> infoMap,
			Map<PisEspServer, Map<String, String>> agentErrMap) {
		for (PisSyncAgentCorp pagent : pAgentlist) {
			Iterator<PisEspServer> it = infoMap.keySet().iterator();
			String info = "";
			boolean resultIsSucceed = true;
			int i = 1;
			while (it.hasNext()) {
				PisEspServer server = it.next();
				Boolean isSucceed = infoMap.get(server); // 连接服务器是否成功
				Map<String, String> agentMap = agentErrMap.get(server);// 添加用户失败的
				if (!isSucceed) {
					resultIsSucceed = false;
				}
				if (!info.equals("")) {
					info += "\r\n";
				}

				String agentInfo = null;
				if (agentMap != null
						&& agentMap.containsKey(pagent.getBriefCode())) {
					agentInfo = agentMap.get((pagent.getBriefCode()));
				}

				if (isSucceed) { // 连接服务器成功再验证用户添加的问题
					if (StringUtils.isNotEmpty(agentInfo)) {
						info += i + ".同步esp服务器" + server.getServerAddress()
								+ ":" + server.getPortNumber() + agentInfo;
					}
				} else {
					info += i + ".同步esp服务器" + server.getServerAddress() + ":"
							+ server.getPortNumber() + "失败";
				}
				i++;
			}
			pagent.setInfo(info);
			pagent.setSyncState(resultIsSucceed ? PisSyncState.SUCCEED
					: PisSyncState.ERROR);
		}
		this.pisDao.batchSaveOrUpdate(pAgentlist);
	}

	/**
	 * 同步申报单位后更新
	 *
	 * @param request
	 * @param agtDataList
	 */
	public void updatePisSyncAgentCorp(Request request, List<String> agtDataList) {
		this.pisDao.clearPisSyncAgentCorp();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		List<Company> tempAcList = this.pisDao.findCompany();
		Map<String, Company> tempAcMap = new HashMap<String, Company>();
		for (Company ac : tempAcList) {
			tempAcMap.put(ac.getCode(), ac);
		}

		List<Company> acList = new ArrayList<Company>();
		Set<String> ixexistSet = new HashSet<String>();
		Map<String, String> infoMap = new HashMap<String, String>();
		for (String agt : agtDataList) {
			List<Map<String, String>> agtMap = jsonToListMap(agt);
			for (Map<String, String> map : agtMap) {
				String briefCode = map.get("briefCode");
				String briefName = map.get("briefName");
				String linkMan = map.get("linkMan");
				String linkTel = map.get("linkTel");
				String address = map.get("address");
				if (StringUtils.isEmpty(briefCode)) {
					continue;
				}
				if (!ixexistSet.contains(briefCode)) {
					Company ac = null;
					if (tempAcMap.containsKey(briefCode)) {
						ac = tempAcMap.get(briefCode);
						infoMap.put(briefCode,
								"更新申报单位成功，时间" + sdf.format(new Date()));
					} else {
						ac = new Company();
						infoMap.put(briefCode,
								"同步申报单位成功，时间" + sdf.format(new Date()));
					}
					ac.setCode(briefCode);
					ac.setName(briefName);
					ac.setLinkman(linkMan);
					ac.setTel(linkTel);
					ac.setAddress(address);
					acList.add(ac);
				}
				ixexistSet.add(briefCode);
			}
		}

		this.pisDao.batchSaveOrUpdate(acList);

		List<PisSyncAgentCorp> pAcList = new ArrayList<PisSyncAgentCorp>();
		for (Company agent : acList) {
			PisSyncAgentCorp pAgent = new PisSyncAgentCorp();
			pAgent.setInfo(infoMap.get(agent.getCode()));
			pAgent.setSyncState(PisSyncState.SUCCEED);
			pAgent.setBriefCode(agent.getCode());
			pAgent.setBriefName(agent.getName());
			pAgent.setAddress(agent.getAddress());
			pAgent.setLinkMan(agent.getLinkman());
			pAgent.setLinkTel(agent.getTel());
			pAcList.add(pAgent);
		}
		this.pisDao.batchSaveOrUpdate(pAcList);
	}

	private List<Map<String, String>> jsonToListMap(String jsonData) {
		Gson gson = new Gson();
		List<Map<String, String>> list = gson.fromJson(jsonData,
				new TypeToken<List<Map<String, String>>>() {
				}.getType());
		return list;
	}

	/**
	 * 查找授权代理公司
	 *
	 * @param request
	 * @return
	 */
	public List findEspAuthorityBrokerCorp(Request request) {
		return this.pisDao.findEspAuthorityBrokerCorp();
	}

	/**
	 * 查找授权代理公司明细
	 *
	 * @param request
	 * @param espAuthorityBrokerCorp
	 * @return
	 */
	public List findEspAuthorityBrokerCorpItem(Request request,
			EspAuthorityBrokerCorp espAuthorityBrokerCorp) {
		return this.pisDao
				.findEspAuthorityBrokerCorpItem(espAuthorityBrokerCorp);
	}

	/**
	 * 授权代理公司
	 *
	 * @param request
	 * @param corpList
	 */
	public void authorityBrokerCorp(Request request, List<BrokerCorp> corpList) {
		List saveList = new ArrayList();
		for (BrokerCorp corp : corpList) {
			EspAuthorityBrokerCorp sb = new EspAuthorityBrokerCorp();
			sb.setBrokerCorp(corp);
			saveList.add(sb);
		}
		this.pisDao.batchSaveOrUpdate(saveList);
	}

	/**
	 * 取消授权代理公司
	 *
	 * @param request
	 * @param bcList
	 */
	public void cancelAuthorityBrokerBrop(Request request,
			List<EspAuthorityBrokerCorp> bcList) {
		// 删除授权代理公司相应的明细信息
		this.pisDao.delEspAuthorityBrokerCorpItem(bcList);
		// 删除授权代理公司信息
		this.pisDao.deleteAll(bcList);
	}

	public void saveEspAuthorityBrokerCorpItem(Request request,
			List<EspAuthorityBrokerCorpItem> itemList) {
		this.pisDao.batchSaveOrUpdate(itemList);
	}

	public void delEspAuthorityBrokerCorpItem(Request request,
			List<EspAuthorityBrokerCorpItem> entityList) {
		this.pisDao.deleteAll(entityList);
	}

	/**
	 * 授权代理公司后更新
	 *
	 * @param request
	 * @param brokerlist
	 * @param infoMap
	 * @param syncErrMap
	 */
	public void updateEspAuthorityBrokerCorp(Request request,
			List<EspAuthorityBrokerCorp> brokerlist,
			Map<EspAuthorityBrokerCorp, Boolean> infoMap,
			Map<EspAuthorityBrokerCorp, Map<String, String>> syncErrMap) {
		for (EspAuthorityBrokerCorp broker : brokerlist) {
			String info = "";
			boolean resultIsSucceed = true;
			int i = 1;
			Boolean isSucceed = infoMap.get(broker); // 连接服务器是否成功
			Map<String, String> errInfoMap = syncErrMap.get(broker);// 授权代理公司失败的信息
			if (!isSucceed) {
				resultIsSucceed = false;
			}
			if (!info.equals("")) {
				info += "\r\n";
			}

			String errInfo = null;
			if (errInfoMap != null
					&& errInfoMap.containsKey(broker.getBrokerCorp()
							.getOrgaCode())) {
				errInfo = errInfoMap
						.get((broker.getBrokerCorp().getOrgaCode()));
			}

			if (isSucceed) { // 连接服务器成功再验证用户添加的问题
				if (StringUtils.isNotEmpty(errInfo)) {
					info += i
							+ ".同步esp服务器"
							+ broker.getBrokerCorp().getPisEspServer()
									.getServerAddress()
							+ ":"
							+ broker.getBrokerCorp().getPisEspServer()
									.getPortNumber() + errInfo;
				}
			} else {
				info += i
						+ ".同步esp服务器"
						+ broker.getBrokerCorp().getPisEspServer()
								.getServerAddress()
						+ ":"
						+ broker.getBrokerCorp().getPisEspServer()
								.getPortNumber() + "失败";
			}
			broker.setInfo(info);
			broker.setSyncState(resultIsSucceed ? PisSyncState.SUCCEED
					: PisSyncState.ERROR);
		}
		this.pisDao.batchSaveOrUpdate(brokerlist);
	}

	/**
	 * 查询已授权的代理公司
	 *
	 * @return
	 */
	public List findBrokerCorpByMainBusiness(Request request,
			String mainBusiness) {
		return this.pisDao.findBrokerCorpByMainBusiness(mainBusiness);
	}

	public AclUser findAclUserById(Request request, String userById) {
		return this.pisDao.findAclUserById(userById);
	}

	public CompanyOther findCompanyOther(Request request) {
		return this.pisDao.findCompanyOther();
	}

	/**
	 * 查询合同
	 * 
	 * @param emsNo
	 *            合同号
	 * @param projectType
	 * @return
	 */
	public BaseEmsHead findBaseEmsHeadByEmsNo(String emsNo, Integer projectType) {
		return this.pisDao.findBaseEmsHeadByEmsNo(emsNo, projectType);
	}

	/**
	 * 查询 所有合同号
	 * 
	 * 
	 * @param projectType
	 * @return
	 */
	public List findBaseEmsHead(Integer projectType) {
		return pisDao.findBaseEmsHead(projectType);
	}

	public Integer findProjectType() {
		return this.pisDao.findProjectType();
	}

	/**
	 * 下载报关单数据
	 *
	 * @param request
	 * @param decHead
	 * @param downloadDecData
	 */
	public BaseCustomsDeclaration downloadDecData(Request request,
			BaseCustomsDeclaration decHead, String downloadDecData,
			Integer projectType) {
		if (decHead == null) {
			throw new RuntimeException("参数报关单表头{decHead}为空！");
		}
		if (downloadDecData == null || "".equals(downloadDecData.trim())) {
			throw new RuntimeException("下载的报关单数据为空！");
		}
		System.out.println(downloadDecData);
		Map decMap = jsonToMap(downloadDecData);
		Map<String, Object> mapHead = (Map<String, Object>) decMap.get("报关单表头");
		if (mapHead == null) {
			throw new RuntimeException("报关单表头为空！");
		}
		List itemList = (List) decMap.get("报关单表体");
		if (itemList == null) {
			throw new RuntimeException("报关单表体为空！");
		}
		List containerList = (List) decMap.get("集装箱");
		List docAttachmentList = (List) decMap.get("随附单证");

		this.downloadDecHead(decHead, mapHead);
		this.downloadDecItem(decHead, itemList, projectType, false);
		this.downloadContainer(decHead, containerList, projectType);
		this.downloadDocAttachment(decHead, docAttachmentList);
		return decHead;
	}

	private void downloadDecHead(BaseCustomsDeclaration decHead,
			Map<String, Object> mapHead) {
		String impExpFlag = (String) mapHead.get("进出口标志");
		if (impExpFlag == null || "".equals(impExpFlag.trim())) {
			throw new RuntimeException("进出口标志为空！");
		}
		decHead.setDeclarationCustoms((Customs) this.getCustomBaseEntity(
				Customs.class, "code", (String) mapHead.get("报送海关")));
		decHead.setUnificationCode((String) mapHead.get("统一编号"));
		decHead.setCustomsDeclarationCode((String) mapHead.get("海关编号"));
		decHead.setCustoms((Customs) this.getCustomBaseEntity(Customs.class,
				"code", (String) mapHead.get("进出口口岸")));
		decHead.setEmsHeadH2k((String) mapHead.get("备案号"));
		decHead.setContract((String) mapHead.get("合同协议号"));
		decHead.setImpExpDate(convertToDate((String) mapHead.get("进出口日期")));
		decHead.setDeclarationDate(convertToDate((String) mapHead.get("申报日期")));

		decHead.setTradeCode((String) mapHead.get("经营单位编码"));
		decHead.setTradeName((String) mapHead.get("经营单位名称"));
		decHead.setTransferMode((Transf) this.getCustomBaseEntity(Transf.class,
				"code", (String) mapHead.get("运输方式")));
		decHead.setMachCode((String) mapHead.get("发(收)货单位编码"));
		decHead.setMachName((String) mapHead.get("发(收)货单位名称"));
		decHead.setConveyance((String) mapHead.get("运输工具名称"));
		String agentCorpCode = (String) mapHead.get("申报单位编码");
		if (agentCorpCode != null && !"".equals(agentCorpCode.trim())) {
			Company company = pisDao.findCompany(agentCorpCode);
			if (company != null) {
				decHead.setDeclarationCompany(company);
			}
		}
		decHead.setVoyageNo((String) mapHead.get("航次号"));
		decHead.setBillOfLading((String) mapHead.get("提运单号"));
		decHead.setTradeMode((Trade) this.getCustomBaseEntity(Trade.class,
				"code", (String) mapHead.get("监管方式")));
		decHead.setLevyKind((LevyKind) this.getCustomBaseEntity(LevyKind.class,
				"code", (String) mapHead.get("征免性质")));
		decHead.setPerTax(convertToDouble((String) mapHead.get("征免比例")));
		// decHead.setTaxUnitFlag((String) mapHead.get("纳税单位"));
		decHead.setLicense((String) mapHead.get("许可证号"));
		decHead.setCountryOfLoadingOrUnloading((Country) this
				.getCustomBaseEntity(Country.class, "code",
						(String) mapHead.get("运抵(启运)国")));
		decHead.setPortOfLoadingOrUnloading((PortLin) this.getCustomBaseEntity(
				PortLin.class, "code", (String) mapHead.get("指运(装货)港")));
		decHead.setDomesticDestinationOrSource((District) this
				.getCustomBaseEntity(District.class, "code",
						(String) mapHead.get("境内货原(境内目的)地")));
		decHead.setTransac((Transac) this.getCustomBaseEntity(Transac.class,
				"code", (String) mapHead.get("成交方式")));
		// decHead.setTrainNo((String) mapHead.get("车次"));
		if (StringUtils.isNotBlank((String) mapHead.get("杂费1"))) {
			decHead.setIncidentalExpensesType(NumberUtils.toInt(mapHead.get(
					"杂费1").toString()));
			decHead.setIncidentalExpenses(convertToDouble((String) mapHead
					.get("杂费2")));
			decHead.setOtherCurr((Curr) this.getCustomBaseEntity(Curr.class,
					"code", (String) mapHead.get("杂费3")));
		}
		if (StringUtils.isNotBlank((String) mapHead.get("运费1"))) {
			decHead.setFreightageType(NumberUtils.toInt(mapHead.get("运费1")
					.toString()));
			decHead.setFreightage(convertToDouble((String) mapHead.get("运费2")));
			decHead.setFeeCurr((Curr) this.getCustomBaseEntity(Curr.class,
					"code", (String) mapHead.get("运费3")));
		}
		if (StringUtils.isNotBlank((String) mapHead.get("保费1"))) {
			decHead.setInsuranceType(NumberUtils.toInt(mapHead.get("保费1")
					.toString()));
			decHead.setInsurance(convertToDouble((String) mapHead.get("保费2")));
			decHead.setInsurCurr((Curr) this.getCustomBaseEntity(Curr.class,
					"code", (String) mapHead.get("保费3")));
		}

		decHead.setBalanceMode((BalanceMode) this.getCustomBaseEntity(
				BalanceMode.class, "code", (String) mapHead.get("结汇方式")));
		decHead.setPredock((PreDock) this.getCustomBaseEntity(PreDock.class,
				"code", (String) mapHead.get("装卸口岸")));
		decHead.setManufacturer((Brief) this.getCustomBaseEntity(Brief.class,
				"name", (String) mapHead.get("生产厂家")));
		decHead.setSupplmentType(convertToInteger((String) mapHead
				.get("补充报关类型")));
		decHead.setUses((Uses) this.getCustomBaseEntity(Uses.class, "code",
				(String) mapHead.get("用途")));

		decHead.setOverseasConveyanceCode((String) mapHead.get("境外运输工具编号"));
		decHead.setOverseasConveyanceName((String) mapHead.get("境外运输工具名称"));
		decHead.setOverseasConveyanceFlights((String) mapHead.get("境外运输工具航次"));
		decHead.setOverseasConveyanceBillOfLading((String) mapHead
				.get("境外运输工具提单号"));
		decHead.setDomesticTransferMode((Transf) this.getCustomBaseEntity(
				Transf.class, "code", (String) mapHead.get("境内运输方式")));
		decHead.setDomesticConveyanceCode((String) mapHead.get("境内运输工具编号"));
		decHead.setDomesticConveyanceName((String) mapHead.get("境内运输工具名称"));
		decHead.setDomesticConveyanceFlights((String) mapHead.get("境内运输工具航次"));
		// decHead.setPlanArriveDate(convertToDate((String)
		// mapHead.get("预计抵运日期")));

		decHead.setCommodityNum(convertToInteger((String) mapHead.get("件数")));
		Wrap wrap = (Wrap) this.getCustomBaseEntity(Wrap.class, "name",
				(String) mapHead.get("包装种类"));
		if (mapHead.get("包装种类") != null
				&& !"".equals(mapHead.get("包装种类").toString().trim())
				&& wrap == null) {
			throw new RuntimeException("没有找到名称为:"
					+ (String) mapHead.get("包装种类") + "的包装种类。");
		}
		decHead.setWrapType(wrap);
		decHead.setGrossWeight(convertToDouble((String) mapHead.get("毛重")));
		decHead.setNetWeight(convertToDouble((String) mapHead.get("净重")));
		decHead.setContainerNum((String) mapHead.get("集装箱号"));
		decHead.setAttachedBill((String) mapHead.get("随附单证"));
		decHead.setInvoiceCode((String) mapHead.get("发票号码"));
		decHead.setTcsEntryType((String) mapHead.get("报关单类型"));
		decHead.setTcsEdiId((String) mapHead.get("报关标志"));
		decHead.setTcsType((String) mapHead.get("EDI申报备注"));
		decHead.setMemos((String) mapHead.get("备注"));
		decHead.setRelativeId((String) mapHead.get("关联报关单"));
		decHead.setRelativeManualNo((String) mapHead.get("关联备案"));
		decHead.setBondedWarehouse((String) mapHead.get("监管仓号"));
		// decHead.setFreightCode((String) mapHead.get("货场代码"));
		if (decHead.getCustomsDeclarationCode() != null
				&& !"".equals(decHead.getCustomsDeclarationCode().trim())) {
			decHead.setEffective(true);
		}
		this.pisDao.saveOrUpdate(decHead);
	}

	private void downloadDecItem(BaseCustomsDeclaration decHead,
			List<Map<String, String>> listItem, Integer projectType,
			boolean isCustomDelete) {
		if (listItem == null || listItem.isEmpty()) {
			return;
		}
		List<BaseCustomsDeclarationCommInfo> oldList = this.pisDao
				.findBaseCustomsDeclarationCommInfo(decHead, projectType);
		Map<Integer, BaseCustomsDeclarationCommInfo> oldMap = new HashMap();
		for (BaseCustomsDeclarationCommInfo item : oldList) {
			oldMap.put(item.getSerialNumber(), item);
		}
		Map<String, Unit> mapUnit = this.findCustomBaseEntityMap(Unit.class);
		Map<String, Curr> mapCurr = this.findCustomBaseEntityMap(Curr.class);
		Map<String, Country> mapCountry = this
				.findCustomBaseEntityMap(Country.class);
		Map<String, LevyMode> mapLevyMode = this
				.findCustomBaseEntityMap(LevyMode.class);
		Map<String, Uses> mapUses = this.findCustomBaseEntityMap(Uses.class);
		Map<String, Complex> mapComplexs = getComplex();
		Map<String, CustomsComplex> mapCustomsComplexs = this
				.findCustomBaseEntityMap(CustomsComplex.class);
		for (int i = 0; i < listItem.size(); i++) {
			Map<String, String> map = listItem.get(i);
			Integer itemNo = convertToInteger((String) map.get("商品序号"));
			if (itemNo == null) {
				throw new RuntimeException("第" + (i + 1) + "条商品明细的商品序号为空。");
			}
			BaseCustomsDeclarationCommInfo item = oldMap.get(itemNo);
			if (item == null) {
				item = new BaseCustomsDeclarationCommInfo();
				if (isCustomDelete) {
					item = new CustomsDeclarationDeleteCommInfo();
				} else if (ProjectType.BCS == projectType) {
					item = new BcsCustomsDeclarationCommInfo();
				} else if (ProjectType.BCUS == projectType) {
					item = new CustomsDeclarationCommInfo();
				} else if (ProjectType.DZSC == projectType) {
					item = new DzscCustomsDeclarationCommInfo();
				}
				item.setBaseCustomsDeclaration(decHead);
				item.setSerialNumber(itemNo);
			} else {
				oldMap.remove(itemNo);
			}

			item.setCommSerialNo(convertToInteger((String) map.get("备案序号")));
			String complexCode = (String) map.get("商品编码")
					+ (String) map.get("附加编码");
			item.setComplex(getComplexByCode(mapComplexs, mapCustomsComplexs,
					complexCode));
			item.setCommName((String) map.get("商品名称"));
			item.setDeclareSpec((String) map.get("规格型号"));
			// item.setDeclareSpec((String) map.get("申报要素"));
			item.setCommAmount(convertToDouble((String) map.get("成交数量")));
			item.setUnit(mapUnit.get((String) map.get("成交单位")));
			item.setCommUnitPrice(convertToDouble((String) map.get("成交单价")));
			item.setCommTotalPrice(convertToDouble((String) map.get("成交总价")));

			item.setFirstAmount(convertToDouble((String) map.get("法定数量")));
			item.setLegalUnit(mapUnit.get((String) map.get("法定单位")));
			item.setVersion((String) map.get("版本号"));
			item.setUses(mapUses.get((String) map.get("用途")));
			item.setSecondAmount(convertToDouble((String) map.get("第二数量")));
			item.setSecondLegalUnit(mapUnit.get((String) map.get("第二单位")));
			item.setCountry(mapCountry.get((String) map.get("目的(原产)地")));
			item.setLevyMode(mapLevyMode.get((String) map.get("征免")));
			item.setPieces(convertToInteger((String) map.get("件数")));
			item.setNetWeight(convertToDouble((String) map.get("净重")));
			item.setGrossWeight(convertToDouble((String) map.get("毛重")));
			item.setBoxNo((String) map.get("箱号"));
			this.pisDao.saveOrUpdate(item);
		}
		if (!oldMap.isEmpty()) {
			for (BaseCustomsDeclarationCommInfo item : oldMap.values()) {
				this.pisDao.delete(item);
			}
		}

		if (listItem != null && listItem.size() > 0) {
			Map<String, String> map = listItem.get(0);
			decHead.setCurrency(mapCurr.get((String) map.get("币制")));
			decHead.setExchangeRate(convertToDouble((String) map.get("汇率")));
			this.pisDao.saveOrUpdate(decHead);
		}
	}

	/**
	 * 下载随附单证
	 *
	 * @param decHead
	 * @param listItem
	 */
	private void downloadDocAttachment(BaseCustomsDeclaration decHead,
			List<Map<String, String>> listItem) {
		if (listItem == null || listItem.isEmpty()) {
			return;
		}
		String docAmt = "";
		String certificateCode = "";
		for (int i = 0; i < listItem.size(); i++) {
			Map<String, String> map = listItem.get(i);
			String code = (String) map.get("代码");
			String billNo = (String) map.get("随附单证编号");
			if (code != null && !"".equals(code.trim())) {
				docAmt += code;
				if (i == 0) {
					certificateCode = (code + ":" + (billNo == null ? ""
							: billNo));
				} else {
					certificateCode += ("," + code + ":" + (billNo == null ? ""
							: billNo));
				}
			}
		}
		decHead.setAttachedBill(docAmt);
		decHead.setCertificateCode(certificateCode);
		this.pisDao.saveOrUpdate(decHead);
	}

	/**
	 *
	 * @param decHead
	 * @param listItem
	 */
	private void downloadContainer(BaseCustomsDeclaration decHead,
			List<Map<String, String>> listItem, Integer projectType) {
		if (listItem == null || listItem.isEmpty()) {
			return;
		}
		Map<String, SrtJzx> mapSrtJzx = this
				.findCustomBaseEntityMap(SrtJzx.class);
		Map<String, SrtTj> mapSrtTj = this.findCustomBaseEntityMap(SrtTj.class);
		if (decHead.getContainerNum() == null
				|| "".equals(decHead.getContainerNum())
				|| decHead.getContainerNum().equals("0")) {
			return;
		}
		List<BaseCustomsDeclarationContainer> oldList = this.pisDao
				.findDecContainer(decHead, projectType);
		Map<String, BaseCustomsDeclarationContainer> oldMap = new HashMap();
		for (BaseCustomsDeclarationContainer item : oldList) {
			oldMap.put(item.getContainerCode(), item);
		}
		for (int i = 0; i < listItem.size(); i++) {
			Map<String, String> map = listItem.get(i);
			String containerCode = (String) map.get("集装箱号");
			if (containerCode == null || "".equals(containerCode.trim())) {
				throw new RuntimeException("第" + (i + 1) + "条集装箱的集装箱号为空。");
			}
			BaseCustomsDeclarationContainer item = oldMap.get(containerCode);
			if (item == null) {
				if (ProjectType.BCS == projectType) {
					item = new BcsCustomsDeclarationContainer();
				} else if (ProjectType.BCUS == projectType) {
					item = new CustomsDeclarationContainer();
				} else if (ProjectType.DZSC == projectType) {
					item = new DzscCustomsDeclarationContainer();
				}
				item.setBaseCustomsDeclaration(decHead);
			} else {
				oldMap.remove(containerCode);
			}
			item.setSrtJzx(mapSrtJzx.get((String) map.get("集装箱规格")));
			item.setSrttj(mapSrtTj.get((String) map.get("集装箱托架种类")));
			item.setContainerCode(containerCode);
			this.pisDao.saveOrUpdate(item);
		}
		if (!oldMap.isEmpty()) {
			for (BaseCustomsDeclarationContainer item : oldMap.values()) {
				this.pisDao.delete(item);
			}
		}
	}

	private Integer convertToInteger(String integerStr) {
		Integer value = null;
		if (integerStr != null && !"".equals(integerStr.trim())) {
			try {
				value = Integer.valueOf(integerStr.trim());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return value;
	}

	private Double convertToDouble(String doubleStr) {
		Double value = null;
		if (doubleStr != null && !"".equals(doubleStr.trim())) {
			try {
				value = Double.valueOf(doubleStr.trim());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return value;
	}

	private Date convertToDate(String dateStr) {
		Date date = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		if (dateStr != null && !"".equals(dateStr.trim())) {
			try {
				date = dateFormat.parse(dateStr.trim());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return date;
	}

	private Map findCustomBaseEntityMap(Class entityClass) {
		Map map = new HashMap();
		List<CustomBaseEntity> list = this.pisDao
				.findCustomBaseEntityList(entityClass.getName());
		for (CustomBaseEntity customsBaseEntity : list) {
			map.put(customsBaseEntity.getCode(), customsBaseEntity);
		}
		return map;
	}

	private Object getCustomBaseEntity(Class entityClass, String fieldName,
			String fieldValue) {
		if (fieldValue != null && !"".equals(fieldValue.trim())) {
			return this.pisDao.findCustomBaseEntity(entityClass.getName(),
					fieldName, fieldValue.trim());
		} else {
			return null;
		}
	}

	private Map<String, Complex> getComplex() {
		Map map = new HashMap();
		List<Complex> list = this.pisDao.findCustomBaseEntityList("Complex");
		for (Complex complex : list) {
			map.put(complex.getCode(), complex);
		}
		return map;
	}

	private Map<String, Object> jsonToMap(String jsonData) {
		Gson gson = new Gson();
		Map<String, Object> map = gson.fromJson(jsonData,
				new TypeToken<Map<String, Object>>() {
				}.getType());
		return map;
	}

	private Complex getComplexByCode(Map<String, Complex> complexMap,
			Map<String, CustomsComplex> customsComplexMap, String complexCode) {
		Complex complex = complexMap.get(complexCode);
		if (complex != null) {
			return complex;
		} else {
			CustomsComplex customsComplex = customsComplexMap.get(complexCode);
			if (customsComplex != null) {
				complex = new Complex();
				try {
					PropertyUtils.copyProperties(complex, customsComplex);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
				complex.setId(customsComplex.getCode());
				this.pisDao.saveOrUpdate(complex);
				complexMap.put(complexCode, complex);
				return complex;
			} else {
				return null;
			}
		}
	}

	public void customsDeleteDecl(Request request, String downloadDecData,
			Integer projectType) {
		if (downloadDecData == null || "".equals(downloadDecData.trim())) {
			throw new RuntimeException("下载的报关单数据为空！");
		}
		System.out.println(downloadDecData);
		Map decMap = jsonToMap(downloadDecData);
		Map<String, Object> mapHead = (Map<String, Object>) decMap.get("报关单表头");
		if (mapHead == null) {
			throw new RuntimeException("报关单表头为空！");
		}
		List itemList = (List) decMap.get("报关单表体");
		if (itemList == null) {
			throw new RuntimeException("报关单表体为空！");
		}
		List containerList = (List) decMap.get("集装箱");
		List docAttachmentList = (List) decMap.get("随附单证");

		CustomsDeclarationDelete decHead = new CustomsDeclarationDelete();
		decHead.setTypeModel(projectType);
		decHead.setWorkDate(CommonUtils.getBeginDate(new Date()));
		decHead.setWorkEr(CommonUtils.getAclUser().getLoginName());
		this.downloadDecHead(decHead, mapHead);
		this.downloadDecItem(decHead, itemList, projectType, true);
		this.downloadContainer(decHead, containerList, projectType);
		this.downloadDocAttachment(decHead, docAttachmentList);
	}
}
