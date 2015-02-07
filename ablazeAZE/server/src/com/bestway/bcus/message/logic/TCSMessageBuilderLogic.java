package com.bestway.bcus.message.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.message.entity.DecSupplementList;
import com.bestway.bcus.message.logic.TCSMessageLogic.LabelValue;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.TcsLinkMan;
import com.bestway.bcus.system.entity.TcsParameters;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.SupplmentType;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.customs.common.entity.BaseCustomsDeclarationContainer;
import com.bestway.customs.common.entity.TCSCommonCode;

public class TCSMessageBuilderLogic {

	/**
	 * 构建产生报文的数据对象。
	 * 
	 * @return
	 */
	static List<LabelValue> buildRootMassage(Map<String, Object> args) {
		List<LabelValue> map = new ArrayList<LabelValue>();
		// 传输头
		map.add(new LabelValue("MessageHead", buildMessageHead(args)));
		// 传输体
		map.add(new LabelValue("MessageBody", buildMessageBody(args)));

		return map;
	}

	/**
	 * 构建传输头数据
	 * 
	 * @return
	 */
	private static List<LabelValue> buildMessageHead(Map<String, Object> args) {
		List<LabelValue> map = new ArrayList<LabelValue>();
		// 报关单表头
		BaseCustomsDeclaration declaration = (BaseCustomsDeclaration) args
				.get("declaration");
		// 集成通参数
		TcsParameters p = (TcsParameters) args.get("tcsParameters");

		// 报文业务类型
		map.add(new LabelValue("MessageType", "001"));

		// 报文传输编号
		String preNo = "";
		if (declaration.getPreCustomsDeclarationCode() != null
				&& declaration.getPreCustomsDeclarationCode().length() >= 6) {
			preNo = declaration.getPreCustomsDeclarationCode().substring(
					declaration.getPreCustomsDeclarationCode().length() - 6,
					declaration.getPreCustomsDeclarationCode().length());
		} else {
			System.out.println("预录入号为空，生成的‘报文传输编号’可能重复！");
			preNo = "000001";
		}
		String messageId = p.getUserId()
				+ CommonUtils.getDate((Date) args.get("currentDate"), "yyyyMMdd")
				+ preNo;
		args.put("messageId", messageId);
		map.add(new LabelValue("MessageId", messageId));

		// 报文传输时间 当前传输时间
		map.add(new LabelValue("MessageTime", CommonUtils.getDate(
				(Date) args.get("currentDate"), "yyyyMMddHHmmss")));

		// 报文发送者编号
		map.add(new LabelValue("SenderId", p.getSenderId()));

		// 报文发送者地址
		map.add(new LabelValue("SenderAddress", p.getSenderAddress()));

		// 报文接收者编号
		map.add(new LabelValue("ReceiverId", p.getReceiverId()));

		// 报文接收者地址
		map.add(new LabelValue("ReceiverAddress", p.getReceiverAddress()));

		// 报文传输响应标志 -- 暫时隐藏
		// map.add(new LabelValue("DataResponse", "false"));

		// 报文传输展开标志 -- 暫时隐藏
		// map.add(new LabelValue("DataUnwrap", "false"));

		// 报文传输解压标志 -- 暫时隐藏
		// map.add(new LabelValue("DataCompress", ""));

		// 报文传输字符集 -- 暫时隐藏
		// map.add(new LabelValue("DataEncoding", "UTF8"));

		return map;
	}

	/**
	 * 构建传输体数据
	 * 
	 * @return
	 */
	private static List<LabelValue> buildMessageBody(Map<String, Object> args) {
		List<LabelValue> map = new ArrayList<LabelValue>();
		// 流程数据
		map
				.add(new LabelValue(
						"TcsFlow201 xmlns=\"http://www.chinaport.gov.cn/tcs/v2\" "
								+ "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"",
						buildTcsFlow201(args)));

		return map;
	}

	/**
	 * 构建业务流程数据
	 * 
	 * @return
	 */
	private static List<LabelValue> buildTcsFlow201(Map<String, Object> args) {
		List<LabelValue> map = new ArrayList<LabelValue>();
		
		// 补充申报类型
		Integer supplmentType = (Integer) args.get("supplmentType");
		
		// 协同用户描述
		map.add(new LabelValue("TcsUser", buildTcsUser(args)));
		// 协同联系信息描述 2012-5-10 修改调用 buildTcsLink();

		// 被动补充申报的时候这部分不需生成报文
		if (supplmentType != SupplmentType.PASSIVITY_SYPPLMENT) {
			map.add(new LabelValue("TcsLink", buildTcsLink(args)));
		}
		// 协同业务流程描述
		map.add(new LabelValue("TcsFlow", buildTcsFlow(args)));
		// 协同业务数据描述
		map.add(new LabelValue("TcsData", buildTcsData(args)));

		return map;
	}

	/**
	 * 构建协同用户描述数据
	 * 
	 * @return
	 */
	private static List<LabelValue> buildTcsUser(Map<String, Object> args) {
		TcsParameters p = (TcsParameters) args.get("tcsParameters");

		List<LabelValue> map = new ArrayList<LabelValue>();
		// 集成通平台用户编号
		map.add(new LabelValue("UserId", p.getUserId()));
		// 集成通平台用户私密
		map.add(new LabelValue("UserPrivateKey", p.getUserPrivateKey()));

		return map;
	}

	/**
	 * 2012-5-10 增加方法
	 * 
	 * 构建协同联系信息描述数据
	 * 
	 * @return
	 */
	private static List<LabelValue> buildTcsLink(Map<String, Object> args) {
		List<LabelValue> map = new ArrayList<LabelValue>();
		map.add(new LabelValue("TcsLinkMan", buildTcsLinkMan(args)));
		return map;
	}

	/**
	 * 构建协同联系人信息描述
	 * 
	 * @return
	 */
	private static List<LabelValue> buildTcsLinkMan(Map<String, Object> args) {
		List<LabelValue> map = new ArrayList<LabelValue>();
		TcsLinkMan man = (TcsLinkMan) args.get("tcsLinkMan");
		if (man == null) {
			throw new RuntimeException("没有设置tcs联系人！");
		}
		// 联系人 an..70
		map.add(new LabelValue("Name", man.getName()));

		// 部门 an..70
		map.add(new LabelValue("Department", man.getDepartment()));

		// 职务 an..35
		map.add(new LabelValue("Duty", man.getDuty()));

		// 电话 an..35
		map.add(new LabelValue("Telephone", man.getTelephone()));

		// 手机 an..35
		map.add(new LabelValue("Mobile", man.getMobile()));

		// 传真 an..35
		map.add(new LabelValue("Fax", man.getFax()));

		// 地址 an..255
		map.add(new LabelValue("Address", man.getAddress()));

		// 邮编 n..70
		map.add(new LabelValue("ZipCode", man.getZipCode()));

		// EMAIL an..35
		map.add(new LabelValue("Email", man.getEmail()));

		// IM编号 an..35
		map.add(new LabelValue("ImCode", man.getImCode()));

		// M类型 n3
		map.add(new LabelValue("ImType", man.getImType()));

		return map;
	}

	/**
	 * 构建协同业务流程描述数据
	 * 
	 * @return
	 */
	private static List<LabelValue> buildTcsFlow(Map<String, Object> args) {
		
		// 集成通参数
		TcsParameters p = (TcsParameters) args.get("tcsParameters");
		
		// 报关单表头
		BaseCustomsDeclaration declaration = (BaseCustomsDeclaration) args
				.get("declaration");
		
		// 补充申报类型
		Integer supplmentType = (Integer) args.get("supplmentType");
		

		List<LabelValue> map = new ArrayList<LabelValue>();
		// 报文编号 an..32 标识此次所传报文文件的编号，每传一次报文，该号变化一次，该号需保持唯一。
		// map.add(new LabelValue("MessageId", args.get("messageId")));

		// 集成通企业业务流程编号 an..20 集成通平台定义企业业务流程的编号
		// （编号规则：OB+业务流程公共模板编号（编号规则：BT+6位流水号）+8位流水号，由集成通平台分配）
		map.add(new LabelValue("BpNo", p.getBpNo()));

		// 集成通企业业务流程活动 an..30 集成通平台定义企业业务流程下的活动编号
		List<LabelValue> actionList = new ArrayList<LabelValue>();
		actionList.add(new LabelValue("ActionId", p.getActionId()));
		map.add(new LabelValue("ActionList", actionList));

		// 集成通协同任务编号 an..30 集成通平台的协同任务编号（唯一标识）,
		// 字段格式：集成通平台用户编号(12)+YYYYMMDD(8)+系统流水唯一号(10)。
		map.add(new LabelValue("TaskId", args.get("messageId")));

		// 集成通协同任务备注 an..100 企业协同任务之间的备注性沟通说明
		if (supplmentType != SupplmentType.PASSIVITY_SYPPLMENT) {
			map.add(new LabelValue("TaskNote", declaration.getTcsNote()));
			// 被动补充申报的时候不需生成报文
			// 企业协同任务内部编号 an..30 企业用户内部标识协同任务(供企业扩展)。
			map.add(new LabelValue("CorpTaskId", ""));
		}
		// 集成通任务控制节点 an..20 任务控制节点，按预设值放入命令。需要终止流程时设置为STOP。
		map.add(new LabelValue("TaskControl", ""));
		return map;
	}

	/**
	 * 构建协同业务数据描述数据
	 * 
	 * @return
	 */
	private static List<LabelValue> buildTcsData(Map<String, Object> args) {
		List<LabelValue> map = new ArrayList<LabelValue>();
		map.add(new LabelValue(
				"DecDocument xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
						+ "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"	"
						+ "xmlns=\"http://www.chinaport.gov.cn/lis/supdec\"",
				buildDecDocument(args)));
		return map;
	}

	/**
	 * 构建业务数据 - 报关单/转关单 2012-5-10 修改方法名称 buildDeclaration -> buildDecDocument
	 * 
	 * @return
	 */
	private static List<LabelValue> buildDecDocument(Map<String, Object> args) {

		Integer supplmentType = (Integer) args.get("supplmentType");
		// 报关单表头
		BaseCustomsDeclaration declaration = (BaseCustomsDeclaration) args
				.get("declaration");

		List<LabelValue> map = new ArrayList<LabelValue>();

		// 补充报关类型
		map.add(new LabelValue("SupplementType", buildDecInfo(args)));

		if (supplmentType == SupplmentType.NO_SYPPLMENT
				|| supplmentType == SupplmentType.INITIATIVE_SYPPLMENT) {
			// 报关单表头信息
			map.add(new LabelValue("DecHead", buildDecHead(args)));

			// 报关单表体信息 DecList
			map.add(new LabelValue("DecList", buildDecList(args)));

			// 报关单集装箱信息
			map.add(new LabelValue("DecContainerList",
					buildDecContainerList(args)));

			// 报关单随附单据信息
			map.add(new LabelValue("DecAttachedList",
					buildDecAttachedList(args)));

			// 报关单自由文本信息
			map.add(new LabelValue("DecFreeTxt", buildDecFreeTxt(args)));

			// 转关信息
			if (declaration.getConveyance() != null
					&& declaration.getConveyance().contains("@")) {

				// 转关单表头信息
				map.add(new LabelValue("TransitHead", buildTransitHead(args)));

				// 转关单表体(提单)信息
				map.add(new LabelValue("TransitList", buildTransitList(args)));

				// 转关单集装箱信息
				map.add(new LabelValue("TransitContainerList",
						buildTransitContainerList(args)));
			}

			// 集装箱与商品关联关系
			map.add(new LabelValue("ContainerGoodsRelation", ""));

			// 报关单签名信息 SignInformation
			map.add(new LabelValue("SignInformation",
					buildSignInformation(args)));
		}

		/*
		 * 当补充报关单类型为： 当值为0时只填写报关单信息，不填写补充报关单及其签名信息； 值为1时，不填写补充报关单签名信息，其它全填；
		 * 值为2时，不填写报关单基础信息，须填写补充报关单及其签名信息
		 */
		if (supplmentType == SupplmentType.PASSIVITY_SYPPLMENT
				|| supplmentType == SupplmentType.INITIATIVE_SYPPLMENT) {

			// 补充报关单信息 DecSupplementList
			map.add(new LabelValue("DecSupplementLists",
					buildDecSupplementLists(args)));

			// 补充报关单签名信息 DecSupplementSign
			map.add(new LabelValue("DecSupplementSignTypeInformation",
					buildDecSupplementSign(args)));

			// 补充报关单签名信息 PrivateList
			map.add(new LabelValue("PrivateList", buildPrivateList(args)));
		}
		return map;
	}

	/**
	 * 构建业务数据 - 报关单/转关单 - 报关信息
	 * 
	 * @return
	 */
	private static List<LabelValue> buildDecInfo(Map<String, Object> args) {
		BaseCustomsDeclaration declaration = (BaseCustomsDeclaration) args
				.get("declaration");
		List<LabelValue> map = new ArrayList<LabelValue>();

		// 补充报关类型
		map
				.add(new LabelValue(
						"SupplementType",
						declaration.getSupplmentType() == null ? SupplmentType.NO_SYPPLMENT
								: declaration.getSupplmentType()));

		return map;
	}

	/**
	 * 构建业务数据-签名信息
	 * 
	 * @return
	 */
	private static List<LabelValue> buildSignInformation(Map<String, Object> args) {
		// 集成通参数
		TcsParameters p = (TcsParameters) args.get("tcsParameters");

		List<LabelValue> map = new ArrayList<LabelValue>();

		// 操作类型 OperType 是 1
		// A:报关单上载 B：报关单、转关单上载 C:报关单申报 D：报关单、转关单申报
		// E：电子手册报关单上载（此种操作类型的报关单上载时不作非空和逻辑校验） G：报关单暂存
		map.add(new LabelValue("OperType", "G"));

		// 操作员IC卡号
		// map.add(new LabelValue("IcCardNo", p.getIcCardNo()));
		map.add(new LabelValue("IcCardNo", p.getIcCardNo()));

		// 操作员姓名
		map.add(new LabelValue("OperName", p.getOperatorName()));

		// 操作企业组织机构代码
		map.add(new LabelValue("OrganizationCode", p.getOganizationCode()));

		// 数字签名信息
		map.add(new LabelValue("SignInfo", "A001"));

		// 签名日期
		map.add(new LabelValue("SignDate", CommonUtils.getDate(
				new Date(), "yyyyMMddHHmmssss")));

		// 操作员卡的证书号
		// map.add(new LabelValue("CertificateNo", p.getCertificateNo()));
		map.add(new LabelValue("CertificateNo", p.getCertificateNo()));

		return map;
	}

	/**
	 * 构建业务数据-报关单/转关单-报关单表头信息
	 * 
	 * @return
	 */
	private static List<LabelValue> buildDecHead(Map<String, Object> args) {
		// 集成通参数
		TcsParameters p = (TcsParameters) args.get("tcsParameters");
		// 报关单表头
		BaseCustomsDeclaration declaration = (BaseCustomsDeclaration) args
				.get("declaration");

		List<LabelValue> map = new ArrayList<LabelValue>();

		// 数据中心统一编号 否 n18
		map.add(new LabelValue("EportNo", ""));

		// 预录入号 是 an..18 开放模式，必填；封闭模式，不填；
		map.add(new LabelValue("PreentryNo", ""));

		// 申报单位代码 是 an..10
		map.add(new LabelValue("AgentCode", declaration.getDeclarationCompany()
				.getCode()));

		// 申报单位名称 是 an..60
		map.add(new LabelValue("AgentName", declaration.getDeclarationCompany()
				.getName()));

		// 货主单位代码 an..10
		map.add(new LabelValue("OwnerCode", declaration.getMachCode()));

		// 货主单位名称 an..60
		map.add(new LabelValue("OwnerName", declaration.getMachName()));

		// 经营单位代码 an..10
		map.add(new LabelValue("TradeCode", declaration.getTradeCode()));

		// 经营单位名称 an..60
		map.add(new LabelValue("TradeName", declaration.getTradeName()));

		// 录入单位代码 an..10 = 组织机构代码
		map.add(new LabelValue("CopCode", p.getOganizationCode()));

		// 录入单位名称 an..60 = 当前登录公司名称
		map.add(new LabelValue("CopName", ((Company) CommonUtils.getCompany())
				.getName()));
		
		// 主管海关（申报地海关） CustomMaster 是 n4 海关标准，关区代码表
		map.add(new LabelValue("CustomMaster", declaration
				.getDeclarationCustoms().getCode()));

		// 进出口岸 IEPort n4 海关标准，关区代码表
		map.add(new LabelValue("IEPort", declaration.getCustoms().getCode()));

		// 报关/转关关系标志 DeclTrnRel 是 n1 0：一般报关单；1：转关提前报关单
		String declTrnRel = null;
		if (TCSCommonCode.TcsEdiId_northBefore
				.equals(declaration.getTcsEdiId())
				|| TCSCommonCode.TcsEdiId_southBefore.equals(declaration
						.getTcsEdiId())) {
			declTrnRel = "1";
		} else {
			declTrnRel = "0";
		}
		map.add(new LabelValue("DeclTrnRel", declTrnRel));

		// 装货港 DistinatePort 4
		if( declaration.getPortOfLoadingOrUnloading() != null) 
		map.add(new LabelValue("DistinatePort", new Integer(declaration
				.getPortOfLoadingOrUnloading().getCode())));

		// 报关标志 EdiId 1 1：普通报关；3：北方转关提前；5：南方转关提前；6：普通报关，运输工具名称以‘@’开头，南方H2000直转
		map.add(new LabelValue("EdiId", declaration.getTcsEdiId() == null ? "5"
				: declaration.getTcsEdiId()));

		// 报关单类型 EntryType 1 0普通报关单，L为带报关单清单的报关单，W无纸报关类型，D既是清单又是无纸报关的情况
		map.add(new LabelValue("EntryType", declaration.getTcsEntryType()));

		// EDI申报备注 Type 2 一般报关单填空值；ML：保税区进出境备案清单；SD: “属地申报，口岸验放”报关单。
		map.add(new LabelValue("Type", declaration.getTcsType()));

		// 贸易国别 TradeCountry 3 海关标准，国家代码表。（进口：起运国；出口：运抵国）
		map.add(new LabelValue("TradeCountry", declaration
				.getCountryOfLoadingOrUnloading().getCode()));

		// 纳税单位 TaxCorporationType n1 （纳税义务人）1经营单位、2收货单位、3申报单位
		// map.add(new LabelValue("TaxCorporationType", null));

		// 境内目的地/境内货源地 n5 海关标准，地区代码表
		map.add(new LabelValue("DestinationCode", declaration
				.getDomesticDestinationOrSource() == null ? "" : declaration
				.getDomesticDestinationOrSource().getCode()));

		// 合同协议号 an..32
		map.add(new LabelValue("ContractNo", declaration.getContract()));

		// 备案号 an12
		map.add(new LabelValue("EnrolNo", declaration.getEmsHeadH2k()));

		// 批准文号 an..30 填写“外汇核销单号”
		map.add(new LabelValue("ApprovalNo", declaration.getAuthorizeFile()));

		// 许可证编号 an..20
		map.add(new LabelValue("LicenseNo", declaration.getLicense()));

		// 运输工具名称 customsdeclaration conveyance；若有‘/’只取前面的 an..26 填写运输工具代码及名称
		String meansOfTransportName = "";
		if (declaration.getConveyance() != null
				&& declaration.getConveyance().contains("/")) {
			meansOfTransportName = declaration.getConveyance().substring(0,
					declaration.getConveyance().lastIndexOf("/"));
		} else {
			meansOfTransportName = declaration.getConveyance();
		}
		map.add(new LabelValue("MeansOfTransportName", meansOfTransportName));

		// 提单号 an..32
		map
				.add(new LabelValue("BillOfLadingNo", declaration
						.getBillOfLading()));

		// 包装种类 n1 海关标准，包装种类代码表
		if(declaration.getWrapType() != null)
		map.add(new LabelValue("PackingType",
				declaration.getWrapType() == null ? "" : declaration
						.getWrapType().getCode()));

		// 运输方式 an1 海关标准，运输方式代码表
		if (declaration.getTransferMode() != null)
			map.add(new LabelValue("MeansOfTransportMode", declaration
					.getTransferMode().getCode()));

		// 成交方式 n3 集成通标准T201
		if(declaration.getTransac() != null)
		map.add(new LabelValue("TransactionMode", declaration
						.getTransac().getCode()));

		// 监管方式（贸易方式） n4 海关标准，贸易方式代码表
		if(declaration.getTradeMode() != null)
		map.add(new LabelValue("TradeMode", declaration
						.getTradeMode().getCode()));

		// 征免性质 n3 海关标准，征免性质代码表
		if(declaration.getLevyKind() != null)
		map.add(new LabelValue("CutMode", declaration
						.getLevyKind().getCode()));

		// 进出口类型 是 a1 海关标准
		map.add(new LabelValue("ImportExportFlag", TCSMessageLogic.isImport(declaration
				.getImpExpType()) ? "I" : "E"));

		// 件数 n..9
		map.add(new LabelValue("Packages", declaration.getCommodityNum()));

		// 净重 n..19,5
		map.add(new LabelValue("NetWeight", CommonUtils.formatDoubleByDigit(
				declaration.getNetWeight(), 5)));

		// 毛重 n..19,5
		map.add(new LabelValue("GrossWeight", CommonUtils.formatDoubleByDigit(
				declaration.getGrossWeight(), 5)));

		// 运费标记 n1 海关标准，运费标记代码表
		if(declaration.getFreightageType() != null) {
			map.add(new LabelValue("FreightMark", declaration.getFreightageType()));
	
			// 运费/率 n..19,7
			map.add(new LabelValue("FreightRate", CommonUtils.formatDoubleByDigit(
					declaration.getFreightage(), 7)));
	
			// 运费币制 n3 海关标准，币制代码表
			map.add(new LabelValue("FreightCurrency",
					declaration.getFeeCurr() != null ? declaration.getFeeCurr()
							.getCode() : ""));
		}

		
		if(declaration.getInsuranceType() != null) {
			// 保费标记 n1 海关标准，保费标记代码表
			map
					.add(new LabelValue("InsuranceMark", declaration
							.getInsuranceType()));
	
			// 保费/率 n..19,7
			map.add(new LabelValue("InsuranceRate", CommonUtils
					.formatDoubleByDigit(declaration.getInsurance(), 7)));
	
			// 保费币制 n3 海关标准，币制代码表
			if(declaration.getInsurCurr() != null) 
			map.add(new LabelValue("InsuranceCurrency",
					declaration.getInsurCurr() == null ? "" : declaration
							.getInsurCurr().getCode()));
		}

		
		if(declaration.getIncidentalExpensesType() != null) {
			// 杂费标记 customsdeclaration incidentalExpensesType n1 海关标准，运费标记代码表
			map.add(new LabelValue("ExtrasMark", declaration
					.getIncidentalExpensesType()));
	
			// 杂费/率 customsdeclaration incidentalExpenses n..19,7
			map.add(new LabelValue("ExtrasRate", CommonUtils.formatDoubleByDigit(
					declaration.getIncidentalExpenses(), 7)));
	
			// 杂费币制 n3 海关标准，币制代码表
			if(declaration.getOtherCurr() != null) 
			map.add(new LabelValue("ExtrasCurrency",
					declaration.getOtherCurr() == null ? "" : declaration
							.getOtherCurr().getCode()));
		}

		// 收结汇方式（征税比例） an1 海关标准，结汇方式代码表。进口填写征税比例，出口填写收结汇方式
		String payMode = "";
		// 进：perTax/出：balanceMode
		if (TCSMessageLogic.isImport(declaration.getImpExpType())) {
			if (declaration.getPerTax() != null) {
				if (declaration.getPerTax() == 0) {
					payMode = "";
				} else {
					payMode = declaration.getPerTax().toString();
				}
			}
		} else {
			if (declaration.getBalanceMode() != null) {
				payMode = declaration.getBalanceMode().getCode();
			}
		}		
		if(!"".equals(payMode)) 
		map.add(new LabelValue("PayMode", payMode));

		// 内销比率 n..19，5
		//map.add(new LabelValue("SaleDomesticRatio", ""));

		// 进出口日期 an10 YYYY-MM-DD
		if(declaration.getImpExpDate() != null)
		map.add(new LabelValue("ImportExportDate", TCSMessageLogic.isImport(declaration
				.getImpExpType()) ? CommonUtils.getDate(declaration
				.getImpExpDate(), "yyyyMMdd") : ""));

		// 打印日期 an10 YYYY-MM-DD
		if (declaration.getDeclarationDate() != null)
		map.add(new LabelValue("PDate", CommonUtils.getDate(declaration
				.getDeclarationDate(), "yyyyMMdd")));

		// 报关备注 an..255
		map.add(new LabelValue("Note", declaration.getMemos()));

		// 申报人标识 an..20 暂存不填 2012-5-11 DeclarantID -> DeclarantId
		map.add(new LabelValue("DeclarantId", ""));

		// 录入员IC卡号 an13
		// map.add(new LabelValue("InputIcCode", p.getIcCardNo()));
		map.add(new LabelValue("InputIcCode", p.getIcCardNo()));

		// 录入员姓名 an..70
		// map.add(new LabelValue("InputName", p.getOperatorName()));
		map.add(new LabelValue("InputName", p.getOperatorName()));

		// --------------------删除移动到DecFreeTxt的-------------------------

		// // 监管仓号 an..32
		// map.add(new LabelValue("WarehouseNo", declaration
		// .getBondedWarehouse()));
		//
		// // 货场代码 an..8
		// map.add(new LabelValue("CYNo",
		// declaration.getPredock() == null ? "" : "52"
		// + declaration.getPredock().getCode()));
		// // 关联报关单号 an18
		// map.add(new LabelValue("RelativeEntryNo", declaration
		// .getRelativeId()));
		//
		// // 关联备案号 an12
		// map.add(new LabelValue("RelativeEnrolNo", declaration
		// .getRelativeManualNo()));
		//		
		// // 航次号 取customsdeclaration conveyance中‘/’后面的 an..32
		// map.add(new LabelValue("MeansOfTransportId", meansOfTransportId));
		//		
		// // 传输日期 an10 YYYY-MM-DD
		// map.add(new LabelValue("TransportDate", getDateString("yyyy-MM-dd",
		// declaration.getDeclarationDate())));
		//		
		// // 报关员联系方式 an..32 暂存不填
		// map.add(new LabelValue("DeclarantTelephone", ""));
		//
		// // 报关员号 an..13 暂存不填
		// map.add(new LabelValue("EntrydeclarantNo", ""));
		// ---------------------end---------------------------

		return map;
	}

	/**
	 * 构建业务数据-报关单/转关单-报关单表体信息列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List<LabelValue> buildDecList(Map<String, Object> args) {
		List<LabelValue> map = new ArrayList<LabelValue>();

		List<BaseCustomsDeclarationCommInfo> list = (List<BaseCustomsDeclarationCommInfo>) args
				.get("goodsList");

		for (BaseCustomsDeclarationCommInfo baseCustomsDeclarationCommInfo : list) {
			map.add(new LabelValue("DecListInformation",
					buildDecListInformation(args,
							baseCustomsDeclarationCommInfo)));
		}
		return map;
	}

	/**
	 * 构建业务数据-报关单/转关单-随附单证列表
	 * 
	 * @return
	 */
	private static List<LabelValue> buildDecAttachedList(Map<String, Object> args) {
		// 报关单表头
		BaseCustomsDeclaration declaration = (BaseCustomsDeclaration) args
				.get("declaration");
		List<LabelValue> map = new ArrayList<LabelValue>();

		// 单证代码 DocumentAttachedCode 是 customsdeclaration an1 海关标准，随附单证代码表
		// 单证编号 DocumentAttachedNo customsdeclaration certificateCode an..32
		// 都取certificateCode，'：'或'*'前是CODE,后面是NO,按分号区分多个
		if (declaration.getCertificateCode() != null) {
			String split = declaration.getCertificateCode().contains(",") ? ","
					: ";";

			String[] cards = declaration.getCertificateCode() != null ? declaration
					.getCertificateCode().split(split)
					: new String[0];
			for (String str : cards) {
				String cardName = null;
				String cardNo = null;
				String sp = "";
				if (str != null) {
					if (str.contains(":")) {
						sp = ":";
					} else if (str.contains("*")) {
						sp = "*";
					} else {
						continue;
					}
				} else {
					continue;
				}
				int p = str.indexOf(sp);
				cardName = str.substring(0, p);
				cardNo = str.substring(p + 1);

				List<LabelValue> lvs = new ArrayList<LabelValue>();
				lvs.add(new LabelValue("DocumentAttachedCode", cardName));
				lvs.add(new LabelValue("DocumentAttachedNo", cardNo));

				map.add(new LabelValue("DecAttachedInformation", lvs));
			}
		}

		return map;
	}

	/**
	 * 构建业务数据-报关单/转关单-集装箱列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List<LabelValue> buildDecContainerList(Map<String, Object> args) {
		List<LabelValue> map = new ArrayList<LabelValue>();

		List<BaseCustomsDeclarationContainer> containerList = (List<BaseCustomsDeclarationContainer>) args
				.get("containerList");

		for (BaseCustomsDeclarationContainer container : containerList) {
			List<LabelValue> lvs = new ArrayList<LabelValue>();

			// 集装箱号 是 customsdeclarationcontainer containerCode an..11
			lvs
					.add(new LabelValue("ContainerNo", container
							.getContainerCode()));

			// 集装箱规格 customsdeclarationcontainer 20#柜填S、40及45#柜填L(关联表srtjzx） a1
			// 海关标准，集装箱尺寸代码表
			String size, weight = null;
			if (container.getSrtJzx() != null) {
				if (Integer.parseInt(container.getSrtJzx().getName().substring(
						0, 2)) >= 40) {
					size = "L";
				} else {
					size = "S";
				}
				if (container.getSrtJzx().getSrtWeight() != null) {
					weight = container.getSrtJzx().getSrtWeight().toString();
				} else {
					weight = "";
				}
			} else {
				size = "";
				weight = "";
			}
			lvs.add(new LabelValue("ContainerSize", size));

			// 集装箱自重 目前是先用空 n..13,5
			lvs.add(new LabelValue("ContainerWeight", weight));

			// 集装箱信息
			map.add(new LabelValue("DecContainerInformation", lvs));
		}
		return map;
	}

	private static List<LabelValue> buildDecFreeTxt(Map<String, Object> args) {
		List<LabelValue> map = new ArrayList<LabelValue>();
		// 报关单表头
		BaseCustomsDeclaration declaration = (BaseCustomsDeclaration) args
				.get("declaration");

		// 监管仓号 an..32
		map
				.add(new LabelValue("WarehouseNo", declaration
						.getBondedWarehouse()));

		// 货场代码 an..8
		map.add(new LabelValue("CYNo", declaration.getPredock() == null ? ""
				: "52" + declaration.getPredock().getCode()));

		// 报关员联系方式 an..32 暂存不填
		map.add(new LabelValue("DeclarantTelephone", ""));

		// 报关员号 an..13 暂存不填
		//map.add(new LabelValue("EntrydeclarantNo", ""));

		// 关联报关单号 an18
		map.add(new LabelValue("RelativeEntryNo", declaration.getRelativeId()));

		// 关联备案号 an12
		map.add(new LabelValue("RelativeEnrolNo", declaration
				.getRelativeManualNo()));

		// 运输工具名称 customsdeclaration conveyance；若有‘/’只取前面的 an..26 填写运输工具代码及名称
		// 航次号 取customsdeclaration conveyance中‘/’后面的 an..32
		String meansOfTransportId = "";
		if (declaration.getConveyance() != null
				&& declaration.getConveyance().contains("/")) {
			meansOfTransportId = declaration.getConveyance().substring(
					declaration.getConveyance().lastIndexOf("/") + 1);
		}
		map.add(new LabelValue("MeansOfTransportId", meansOfTransportId));

		return map;
	}

	/**
	 * 构建业务数据-报关单/转关单-报关单表体信息列表-报关单表体信息
	 * 
	 * @return
	 */
	private static List<LabelValue> buildDecListInformation(Map<String, Object> args,
			BaseCustomsDeclarationCommInfo info) {
		List<LabelValue> map = new ArrayList<LabelValue>();
		// 商品序号 是 customsdeclarationcomminfo serialNumber n9 必填
		map.add(new LabelValue("No", info.getSerialNumber()));

		// 商品编码 是 customsdeclarationcomminfo complex n10 国际标准
		map.add(new LabelValue("HsCode", info.getComplex() != null ? info
				.getComplex().getCode() : ""));

		// 货号 customsdeclarationcomminfo materielNo an..30
		map.add(new LabelValue("MaterialNo", info.getMaterielNo()));

		// 中文品名 customsdeclarationcomminfo commName an..255 必填
		map.add(new LabelValue("GoodsName", info.getCommName()));

		// 2012-5-14 注释掉
		// // 英文品名 customsdeclarationcomminfo 目前是先用空 an..255
		// map.add(new LabelValue("EnglishName", ""));

		// 规格型号 customsdeclarationcomminfo commSpec an..255 
		map.add(new LabelValue("Model", info.getCommSpec()));

		// 备案序号 customsdeclarationcomminfo commSerialNo n..5
		map.add(new LabelValue("EnrolNo", info.getCommSerialNo()));

		// 成交数量 customsdeclarationcomminfo commAmount n..19,4
		map.add(new LabelValue("Quantity", CommonUtils.formatDoubleByDigit(info
				.getCommAmount(), 4)));

		// 成交单位 customsdeclarationcomminfo unit an3 国家标准，单位代码表
		if(info.getUnit() != null)
		map.add(new LabelValue("QuantityUnit", info.getUnit().getCode()));

		// 单价 customsdeclarationcomminfo commUnitPrice n..15,4
		map.add(new LabelValue("UnitPrice", CommonUtils.formatDoubleByDigit(
				info.getCommUnitPrice(), 4)));

		// 总价 customsdeclarationcomminfo commTotalPrice n..17,2
		map.add(new LabelValue("TotalPrice", CommonUtils.formatDoubleByDigit(
				info.getCommTotalPrice(), 2)));

		// 币制 customsdeclaration currency n3 海关标准，币制代码表
		map.add(new LabelValue("Currency", info.getBaseCustomsDeclaration()
				.getCurrency() == null ? "" : info.getBaseCustomsDeclaration()
				.getCurrency().getCode()));

		// 第一法定数量 customsdeclarationcomminfo firstAmount n..19,4
		map.add(new LabelValue("FirstQuantity", CommonUtils
				.formatDoubleByDigitNull(info.getFirstAmount(), 4)));

		// 第一法定单位 customsdeclarationcomminfo legalUnit an3 国家标准，单位代码表
		map.add(new LabelValue("FirstUnit", info.getLegalUnit() == null ? ""
				: info.getLegalUnit().getCode()));

		// 第二法定数量 customsdeclarationcomminfo secondAmount n..19,4
		// 如果为空，则发空字符串。如果第二法定单位是空的时候，数量也要发空字符串。
		String secondQuantity = null;
		if (info.getSecondLegalUnit() == null
				|| info.getSecondLegalUnit().getCode() == null
				|| "".equals(info.getSecondLegalUnit().getCode())) {
			secondQuantity = "";
		} else {

			secondQuantity = CommonUtils.formatDoubleByDigitNull(info
					.getSecondAmount(), 4);
		}
		if(secondQuantity != null && !secondQuantity.equals("")) {
			map.add(new LabelValue("SecondQuantity", secondQuantity));
	
			// 第二法定单位 customsdeclarationcomminfo secondLegalUnit an3 国家标准，单位代码表
			if(info.getSecondLegalUnit()!=null)
			map.add(new LabelValue("SecondUnit",
					info.getSecondLegalUnit() == null ? "" : info
							.getSecondLegalUnit().getCode()));
		}

		// 原产地 customsdeclarationcomminfo country a3 海关标准，国别代码表
		if(info.getCountry() != null)
		map.add(new LabelValue("OriginCode", info.getCountry().getCode()));

		// 用途/生产厂家 customsdeclarationcomminfo uses an..2 海关标准，用途代码表
		map.add(new LabelValue("Use", info.getUses() == null ? "" : info
				.getUses().getCode()));

		// 征免方式 customsdeclarationcomminfo levyMode n3 海关标准，征免方式代码表
		map.add(new LabelValue("DutyMode", info.getLevyMode() == null ? ""
				: info.getLevyMode().getCode()));

		// 工缴费 目前是先用空 n17，2
		//map.add(new LabelValue("ProcessingCharges", ""));

		// 版本号 customsdeclarationcomminfo version n..8
		map.add(new LabelValue("GoodsVersion", info.getVersion()));

		// 归类标志 目前是先用空 an1 海关标准
		// map.add(new LabelValue("ClassificationMark", ""));

		// 备注 Note ? 目前是先用空 an..255
		map.add(new LabelValue("Note", ""));

		return map;
	}

	/**
	 * 构建业务数据-报关单/转关单-转关基本信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List<LabelValue> buildTransitHead(Map<String, Object> args) {
		// 报关单表头
		BaseCustomsDeclaration declaration = (BaseCustomsDeclaration) args
				.get("declaration");
		List<LabelValue> map = new ArrayList<LabelValue>();

		// 转关单统一编号 an18
		map.add(new LabelValue("TransitEportNo", ""));

		// 载货清单号 customsdeclaration conveyance取‘@’后面数据 an..16
		map.add(new LabelValue("WaybillNo", declaration.getConveyance() != null
				&& declaration.getConveyance().contains("@") ? declaration
				.getConveyance().substring(
						declaration.getConveyance().indexOf("@") + 1) : ""));

		// 转关申报单号 ? 如果 customsdeclaration conveyance为 13 位
		// 000+conveyance取‘@’后面数据;若是16位直取@后面的 an16
		String transitDeclareNo = declaration.getConveyance() != null
				&& declaration.getConveyance().contains("@") ? declaration
				.getConveyance().substring(
						declaration.getConveyance().indexOf("@") + 1) : "";
		if (transitDeclareNo.length() <= 13) {
			transitDeclareNo = "000" + transitDeclareNo;
		}
		map.add(new LabelValue("TransitDeclareNo", transitDeclareNo));

		// 境内运输方式 customsdeclaration domesticTransferMode
		// an1 海关标准，运输方式代码表
		map.add(new LabelValue("MeansOfTransportMode", declaration
				.getDomesticTransferMode() != null ? declaration
				.getDomesticTransferMode().getCode() : ""));

		// 境内运输工具编号 customsdeclaration
		// domesticConveyanceCode an..35
		map.add(new LabelValue("MeansOfTransportCode", declaration
				.getDomesticConveyanceCode()));

		// 境内运输工具名称 customsdeclaration
		// domesticConveyanceName an..70
		map.add(new LabelValue("MeansOfTransportName", declaration
				.getDomesticConveyanceName()));

		// 境内运输工具航次 customsdeclaration
		// domesticConveyanceFlights an..32
		map.add(new LabelValue("MeansOfTransportId", declaration
				.getDomesticConveyanceFlights()));

		// 承运单位名称 ？ 目前是先用空 an..255
		map.add(new LabelValue("CorporationName", ""));

		// 承运单位组织机构代码 ？ 目前是先用空 an9
		map.add(new LabelValue("OrganizationCode", ""));

		// // 承运单位联系人姓名 ？ 目前是先用空 a..70
		// map.add(new LabelValue("Name", ""));
		//
		// // 承运单位联系人 Email ？ 目前是先用空 an..35
		// map.add(new LabelValue("Email", ""));
		//
		// // 承运单位联系人电话 ？ 目前是先用空 an..35
		// map.add(new LabelValue("Telphone", ""));

		// 转关类型 TransFlag an..2 转关类型：1—转关提前； AA—出口二次转关
		map.add(new LabelValue("TransFlag", "1"));

		// 预计运抵指运地时间 ？ 目前是先用空 an8 格式为：YYYYMMDD
		//map.add(new LabelValue("ArrivalEstimateDate", ""));

		// 是否启用电子关锁标志 ？ 目前是先用空 an..128 Y启用；N不启用
		map.add(new LabelValue("ElectronicSeal", "N"));

		// 备注 ？ 目前是先用空 an..255
		// 如果有设置发送报关单备注附页，并且是转关有@的，则把集装箱转化为代码保存到note里。
		// 代码格式：#A1#www12345678$4$2#
		// 多个的格式：#A1#www12345678$4$2#www12345679$4$2#
		StringBuilder note = new StringBuilder();
		TcsParameters tcsParameters = (TcsParameters) args.get("tcsParameters");
		if (new Boolean(true).equals(tcsParameters.getSendNote())) {
			List<BaseCustomsDeclarationContainer> containerList = (List<BaseCustomsDeclarationContainer>) args
					.get("containerList");
			if (declaration.getConveyance() != null
					&& declaration.getConveyance().contains("@")) {
				BaseCustomsDeclarationContainer container = null;
				for (int i = 0; containerList != null
						&& i < containerList.size(); i++) {
					container = containerList.get(i);
					if (container != null&&container.getSrttj() !=null) {
						if (i == 0) {
							note.append("#");
							note.append(container.getSrttj().getCode());
							note.append("#");
						}
						note.append(container.getContainerCode());
						note.append("$");
						note.append(Integer.parseInt(container.getSrtJzx()
								.getCode()));
						note.append("$");
						note.append("4".equals(container.getSrtJzx().getName()
								.substring(0, 1)) ? "2" : "1");
						note.append("#");
					}
				}
				if (declaration.getMemos() != null
						&& declaration.getMemos().contains("#1")) {
					note = new StringBuilder("#");
				}
			}
		}

		map.add(new LabelValue("Note", note.toString()));

		return map;
	}

	/**
	 * 构建业务数据-报关单/转关单-提单信息
	 * 
	 * @return
	 */
	private static List<LabelValue> buildTransitList(Map<String, Object> args) {
		// 报关单表头
		BaseCustomsDeclaration declaration = (BaseCustomsDeclaration) args
				.get("declaration");

		List<LabelValue> map = new ArrayList<LabelValue>();

		// 提单号 customsdeclaration overseasConveyanceBillOfLading an..32
		map.add(new LabelValue("BillOfLadingNo", declaration
				.getOverseasConveyanceBillOfLading()));

		// 进出境运输方式 customsdeclaration transferMode an1 海关标准，运输方式代码表
		map.add(new LabelValue("MeansOfTransportMode", declaration
				.getTransferMode() == null ? "" : declaration.getTransferMode()
				.getCode()));

		// 进出境运输工具编号 customsdeclaration overseasConveyanceCode an..35
		map.add(new LabelValue("MeansOfTransportCode", declaration
				.getOverseasConveyanceCode()));

		// 进出境运输工具名称 customsdeclaration overseasConveyanceName an..70
		map.add(new LabelValue("MeansOfTransportName", declaration
				.getOverseasConveyanceName()));

		// 进出境运输工具航次 customsdeclaration overseasConveyanceFlights an..32
		map.add(new LabelValue("MeansOfTransportId", declaration
				.getOverseasConveyanceFlights()));

		// 实际进出境日期 customsdeclaration impExpDate an8 YYYYMMDD
		if(declaration.getImpExpDate() != null) 
		map.add(new LabelValue("FactImportExportDATE", CommonUtils.getDate(
				declaration.getImpExpDate(), "yyyyMMdd")));

		return map;
	}

	/**
	 * 构建业务数据-报关单/转关单-集装箱信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List<LabelValue> buildTransitContainerList(Map<String, Object> args) {
		// 报关单表头
		BaseCustomsDeclaration declaration = (BaseCustomsDeclaration) args
				.get("declaration");
		List<LabelValue> map = new ArrayList<LabelValue>();

		List<BaseCustomsDeclarationContainer> containerList = (List<BaseCustomsDeclarationContainer>) args
				.get("containerList");
		// 如果集装箱号 = 0
		if ("0".equals(declaration.getContainerNum())) {
			List<LabelValue> lvs = new ArrayList<LabelValue>();

			// 集装箱号 是 # + domesticConveyanceCode
			lvs.add(new LabelValue("ContainerNo", "#"
					+ declaration.getDomesticConveyanceCode()));

			// 集装箱序号 n9
			lvs.add(new LabelValue("ContainerNum", 1));

			// 集装箱规格 customsdeclarationcontainer N a1
			lvs.add(new LabelValue("ContainerSize", "N"));

			// 电子关锁号 目前是先用空 an..128
			lvs.add(new LabelValue("ElectronicSealNo", ""));

			// 电子关锁厂商 目前是先用空 an..100
			//lvs.add(new LabelValue("ManufacturerOfElectronicSeal", ""));

			// 境内运输工具名称 customsdeclaration domesticConveyanceCode an..70
			lvs.add(new LabelValue("MeansOfTransportName", declaration
					.getDomesticConveyanceCode()));

			// 工具实际重量 n..19,5
			//lvs.add(new LabelValue("MeansOfTransportWeight", ""));

			// 集装箱信息
			map.add(new LabelValue("TransitContainerInformation", lvs));
		} else {
			int i = 1;
			for (BaseCustomsDeclarationContainer container : containerList) {
				List<LabelValue> lvs = new ArrayList<LabelValue>();

				// 集装箱号 是 customsdeclarationcontainer containerCode an..11
				lvs.add(new LabelValue("ContainerNo", container
						.getContainerCode()));

				// 集装箱序号 n9
				lvs.add(new LabelValue("ContainerNum", i++));

				// 集装箱规格 customsdeclarationcontainer 20#柜填S、40及45#柜填L(关联表srtjzx）
				// a1
				// 海关标准，集装箱尺寸代码表
				String size = null;
				if (container.getSrtJzx() != null) {
					if (Integer.parseInt(container.getSrtJzx().getName()
							.substring(0, 2)) >= 40) {
						size = "L";
					} else {
						size = "S";
					}
				} else {
					size = "";
				}
				lvs.add(new LabelValue("ContainerSize", size));

				// 电子关锁号 目前是先用空 an..128
				lvs.add(new LabelValue("ElectronicSealNo", ""));

				// 电子关锁厂商 目前是先用空 an..100
				lvs.add(new LabelValue("ManufacturerOfElectronicSeal", ""));

				// 境内运输工具名称 customsdeclaration domesticConveyanceCode an..70
				lvs.add(new LabelValue("MeansOfTransportName", declaration
						.getDomesticConveyanceCode()));

				// 工具实际重量 n..19,5
				String weight = "";
				if (declaration.getConveyance() != null
						&& declaration.getConveyance().contains("@")) {
					if (container != null && container.getSrttj() != null
							&& container.getSrttj().getTjWeight() != null) {
						weight = container.getSrttj().getTjWeight().toString();
					}
				}
				if(weight !=null && !weight.equals("")) 
				lvs.add(new LabelValue("MeansOfTransportWeight", weight));

				// 集装箱信息
				map.add(new LabelValue("TransitContainerInformation", lvs));
			}
		}

		return map;
	}

	/**
	 * 构建业务数据-报关单/转关单-补充报关单列表
	 * 
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List<LabelValue> buildDecSupplementLists(Map<String, Object> args) {
		List<DecSupplementList> decSupplementLists = (List<DecSupplementList>) args
				.get("decSupplementLists");
		List<LabelValue> map = new ArrayList<LabelValue>();

		DecSupplementList supplment = null;
		for (int i = 0; i < decSupplementLists.size(); i++) {
			supplment = decSupplementLists.get(i);
			args.put("supplment", supplment);
			if (supplment != null) {
				// 补充申报单商品
				map.add(new LabelValue("DecSupplementList",
						buildDecSupplementList(args)));
			}
		}

		return map;
	}

	/**
	 * 构建业务数据-报关单/转关单-补充报关单信息
	 * 
	 * @param args
	 * @return
	 */
	private static List<LabelValue> buildDecSupplementList(Map<String, Object> args) {
		DecSupplementList supplment = (DecSupplementList) args.get("supplment");
		if (supplment == null) {
			return null;
		}

		List<LabelValue> map = new ArrayList<LabelValue>();
		// 补充申报单商品序号 GNo 是 an1..2
		map.add(new LabelValue("GNo", supplment.getGno()));

		// 补充申报单类型 SupType 是 an1 A：价格补充申报单；B：商品归类补充申报单；C：原产地补充申报单
		map.add(new LabelValue("SupType", supplment.getSupType()));

		// 品牌中文 BrandCN an..50
		map.add(new LabelValue("BrandCN", supplment.getBrandCN()));

		// 品牌英文 BrandEN an..100
		map.add(new LabelValue("BrandEN", supplment.getBrandEN()));

		// 买方名称 Buyer an..150
		map.add(new LabelValue("Buyer", supplment.getBuyer()));

		// 买方联系人 BuyerContact an..50
		map.add(new LabelValue("BuyerContact", supplment.getBuyerContact()));

		// 买方地址 BuyerAddr an..255
		map.add(new LabelValue("BuyerAddr", supplment.getBuyerAddr()));

		// 买方电话 BuyerTel an..20
		map.add(new LabelValue("BuyerTel", supplment.getBuyerTel()));

		// 卖方名称 Seller an..150
		map.add(new LabelValue("Seller", supplment.getSeller()));

		// 卖方联系人 SellerContact an..50
		map.add(new LabelValue("SellerContact", supplment.getSellerContact()));

		// 卖方地址 SellerAddr an..255
		map.add(new LabelValue("SellerAddr", supplment.getSellerAddr()));

		// 卖方电话 SellerTel an..20
		map.add(new LabelValue("SellerTel", supplment.getSellerTel()));

		// 生产厂商名称 Factory an..150
		map.add(new LabelValue("Factory", supplment.getFactory()));

		// 生产厂商联系人 FactoryContact an..50
		map
				.add(new LabelValue("FactoryContact", supplment
						.getFactoryContact()));

		// 生产厂商地址 FactoryAddr an..255
		map.add(new LabelValue("FactoryAddr", supplment.getFactoryAddr()));

		// 生产厂商电话 FactoryTel an..20
		map.add(new LabelValue("FactoryTel", supplment.getFactoryTel()));

		// 合同协议号 ContrNo an..20
		map.add(new LabelValue("ContrNo", supplment.getContrNo()));

		// 签约日期 ContrDate an..8
		map.add(new LabelValue("ContrDate", CommonUtils.getDate(
				supplment.getContrDate(), "yyyyMMdd")));

		// 发票编号 InvoiceNo an..50
		map.add(new LabelValue("InvoiceNo", supplment.getInvoiceNo()));

		// 发票日期 InvoiceDate an..8
		map.add(new LabelValue("InvoiceDate", CommonUtils.getDate(
				supplment.getInvoiceDate(), "yyyyMMdd")));

		// 买卖双方之间存在的关系 I_BabRel an..20 价格申报项：
		// 1-8项可多选，但0与1-8项不可同时选
		// 0：买卖双方无以下任何一种关系；
		// 1：买卖双方为同一家族成员；
		// 2：买卖双方互为商业上的高级职员或董事；
		// 3：一方直接或间接地受另一方控制；
		// 4：买卖双方都直接或间接地受第三方控制；
		// 5：买卖双方共同直接或间接地控制第三方；
		// 6：一方直接或间接地拥有控制或持有对方5%或以上的公开发行的有表决权的股票或股份；
		// 7：一方是另一方雇员、高级职员或董事；
		// 8：买卖双方是同一合伙的成员
		// 存储方式：8位有效位，选择了前1－8项的任何一个，则对应位为1，否则为0；选择0则不允许选择1－8，8位全为0。
		map.add(new LabelValue("I_BabRel", supplment.getI_BabRel()));

		// 买卖双方关系是否影响进口货物成交价格： I_PriceEffect an..1 价格申报项：
		// 0：不影响
		// 1：影响
		map.add(new LabelValue("I_PriceEffect", supplment.getI_PriceEffect()));

		// 进口货物成交价格相近情况 I_PriceClose an..20 价格申报项：
		// 1：与同时或大约同时向境内无特殊关系的买方出售的相同或类似货物的成交价格相近；
		// 2：与同时或大约同时相同或类似货物的倒扣价格相近；
		// 3：与同时或大约同时相同或类似货物的计算价格相近；
		// 4：没有以上相近的价格
		map.add(new LabelValue("I_PriceClose", supplment.getI_PriceClose()));

		// 买方处置或使用货物时是否受到除行政法规规定的限制以及对货物销售地域限制以外的限制 I_OtherLimited an..1 价格申报项：
		// 0：不受限制
		// 1：受限制
		map
				.add(new LabelValue("I_OtherLimited", supplment
						.getI_OtherLimited()));

		// 货物的价格是否受到使货物的成交价格无法确定的条件或因素的影响 I_OtherEffect an..1 价格申报项：
		// 0：不受影响
		// 1：受影响
		map.add(new LabelValue("I_OtherEffect", supplment.getI_OtherEffect()));

		// 备注 I_Note1 an..255 价格申报项：
		// 如果上述任一问题的回答为是，请说明限制、条件或因素的内容
		map.add(new LabelValue("I_Note1", supplment.getI_Note1()));

		// 买方是否应直接或间接支付与进口货物有关并作为货物销售条件的特许权使用费 I_IsUsefee an..1 价格申报项：
		// 0：否 1：是
		map.add(new LabelValue("I_IsUsefee", supplment.getI_IsUsefee()));

		// 卖方是否直接或间接从买方对该货物进口后销售、处置或者使用所得中获得收益 I_IsProfit an..1 价格申报项：
		// 0：否 1：是
		map.add(new LabelValue("I_IsProfit", supplment.getI_IsProfit()));

		// 备注 I_Note2 an..255 价格申报项：
		// 如存在以上特许权使用费和收益的支付，且其金额在进口时不能确定的，请说明
		map.add(new LabelValue("I_Note2", supplment.getI_Note2()));

		// 币制 Curr 是 an3 价格申报项：
		// map.add(new LabelValue("Curr", supplment.getGno()));

		// 发票价格单位金额 InvoicePrice an..20 价格申报项：
		map.add(new LabelValue("InvoicePrice", supplment.getInvoicePrice()));

		// 发票价格总金额 InvoiceAmount an..20 价格申报项：
		map.add(new LabelValue("InvoiceAmount", supplment.getInvoiceAmount()));

		// 发票价格备注 InvoiceNote an..255 价格申报项：
		map.add(new LabelValue("InvoiceNote", supplment.getInvoiceNote()));

		// 间接支付/收取的货款单位金额，进口是间接支付，出口是间接收取 GoodsPrice an..20 价格申报项：
		map.add(new LabelValue("GoodsPrice", supplment.getGoodsPrice()));

		// 间接支付/收取的货款总金额，进口是间接支付，出口是间接收取 GoodsAmount an..20 价格申报项：
		map.add(new LabelValue("GoodsAmount", supplment.getGoodsAmount()));

		// 间接支付/收取的货款备注，进口是间接支付，出口是间接收取 GoodsNote an..255 价格申报项：
		map.add(new LabelValue("GoodsNote", supplment.getGoodsNote()));

		// 进口货物除购货佣金以外的佣金和经纪费单位金额 I_CommissionPrice an..20 价格申报项：
		map.add(new LabelValue("I_CommissionPrice", supplment
				.getI_CommissionPrice()));

		// 进口货物除购货佣金以外的佣金和经纪费总金额 I_CommissionAmount an..20 价格申报项：
		map.add(new LabelValue("I_CommissionAmount", supplment
				.getI_CommissionAmount()));

		// 进口货物除购货佣金以外的佣金和经纪费备注 I_CommissionNote an..255 价格申报项：
		map.add(new LabelValue("I_CommissionNote", supplment
				.getI_CommissionNote()));

		// 与该进口货物视为一体的容器费用单位金额 I_ContainerPrice an..20 价格申报项：
		map.add(new LabelValue("I_ContainerPrice", supplment
				.getI_ContainerPrice()));

		// 与该进口货物视为一体的容器费用总金额 I_ContainerAmount an..20 价格申报项：
		map.add(new LabelValue("I_ContainerAmount", supplment
				.getI_ContainerAmount()));

		// 与该进口货物视为一体的容器费用备注 I_ContainerNote an..255 价格申报项：
		map.add(new LabelValue("I_ContainerNote", supplment
				.getI_ContainerNote()));

		// 与该进口货物视为一体的容器费用单位金额 I_PackPrice an..20 价格申报项：
		map.add(new LabelValue("I_PackPrice", supplment.getI_PackPrice()));

		// 与该进口货物视为一体的容器费用总金额 I_PackAmount an..20 价格申报项：
		map.add(new LabelValue("I_PackAmount", supplment.getI_PackAmount()));

		// 与该进口货物视为一体的容器费用备注 I_PackNote an..255 价格申报项：
		map.add(new LabelValue("I_PackNote", supplment.getI_PackNote()));

		// 进口货物包装材料和包装劳务费用单位金额 I_PartPrice an..20 价格申报项：
		map.add(new LabelValue("I_PartPrice", supplment.getI_PackPrice()));

		// 进口货物包装材料和包装劳务费用总金额 I_PartAmount an..20 价格申报项：
		map.add(new LabelValue("I_PartAmount", supplment.getI_PackAmount()));

		// 进口货物包装材料和包装劳务费用备注 I_PartNote an..255 价格申报项：
		map.add(new LabelValue("I_PartNote", supplment.getI_PackNote()));

		// 进口货物包含的材料、部件、零件和类似货物单位金额 I_ToolPrice an..20 价格申报项：
		map.add(new LabelValue("I_ToolPrice", supplment.getI_ToolPrice()));

		// 进口货物包含的材料、部件、零件和类似货物总金额 I_ToolAmount an..20 价格申报项：
		map.add(new LabelValue("I_ToolAmount", supplment.getI_ToolAmount()));

		// 进口货物包含的材料、部件、零件和类似货物备注 I_ToolNote an..255 价格申报项：
		map.add(new LabelValue("I_ToolNote", supplment.getI_ToolNote()));

		// 在生产进口货物过程中消耗的材料单位金额 I_LossPrice an..20 价格申报项：
		map.add(new LabelValue("I_LossPrice", supplment.getI_LossPrice()));

		// 在生产进口货物过程中消耗的材料总金额 I_LossAmount an..20 价格申报项：
		map.add(new LabelValue("I_LossAmount", supplment.getI_LossAmount()));

		// 在生产进口货物过程中消耗的材料备注 I_LossNote an..255 价格申报项：
		map.add(new LabelValue("I_LossNote", supplment.getI_LossNote()));

		// 进口货物在境外进行的为生产进口货物所需的工程设计、技术研发、工艺及制图等相关服务单位金额 I_DesignPrice an..20
		// 价格申报项：
		map.add(new LabelValue("I_DesignPrice", supplment.getI_DesignPrice()));

		// 进口货物在境外进行的为生产进口货物所需的工程设计、技术研发、工艺及制图等相关服务总金额 I_DesignAmount an..20
		// 价格申报项：
		map
				.add(new LabelValue("I_DesignAmount", supplment
						.getI_DesignAmount()));

		// 进口货物在境外进行的为生产进口货物所需的工程设计、技术研发、工艺及制图等相关服务备注 I_DesignNote an..255
		// 价格申报项：
		map.add(new LabelValue("I_DesignNote", supplment.getI_DesignNote()));

		// 特许权使用费单位金额 I_UsefeePrice an..20 价格申报项：
		map.add(new LabelValue("I_UsefeePrice", supplment.getI_UsefeePrice()));

		// 特许权使用费总金额 I_UsefeeAmount an..20 价格申报项：
		map
				.add(new LabelValue("I_UsefeeAmount", supplment
						.getI_UsefeeAmount()));

		// 特许权使用费备注 I_UsefeeNote an..255 价格申报项：
		map.add(new LabelValue("I_UsefeeNote", supplment.getI_UsefeeNote()));

		// 卖方直接或间接从买方对货物进口后转售、处置或使用所得中获得的收益单位金额 I_ProfitPrice an..20 价格申报项：
		map.add(new LabelValue("I_ProfitPrice", supplment.getI_ProfitPrice()));

		// 卖方直接或间接从买方对货物进口后转售、处置或使用所得中获得的收益总金额 I_ProfitAmount an..20 价格申报项：
		map
				.add(new LabelValue("I_ProfitAmount", supplment
						.getI_ProfitAmount()));

		// 价格申报项，卖方直接或间接从买方对货物进口后转售、处置或使用所得中获得的收益备注 I_ProfitNote an..255 价格申报项：
		map.add(new LabelValue("I_ProfitNote", supplment.getI_ProfitNote()));

		// 进口货物运输费用单位金额 I_FeePrice an..20 价格申报项：
		map.add(new LabelValue("I_FeePrice", supplment.getI_FeePrice()));

		// 进口货物运输费用总金额 I_FeeAmount an..20 价格申报项：
		map.add(new LabelValue("I_FeeAmount", supplment.getI_FeeAmount()));

		// 进口货物运输费用备注 I_FeeNote an..255 价格申报项：
		map.add(new LabelValue("I_FeeNote", supplment.getI_FeeNote()));

		// 进口货物运输相关费用单位金额 I_OtherPrice an..20 价格申报项：
		map.add(new LabelValue("I_OtherPrice", supplment.getI_OtherPrice()));

		// 进口货物运输相关费用总金额 I_OtherAmount an..20 价格申报项：
		map.add(new LabelValue("I_OtherAmount", supplment.getI_OtherAmount()));

		// 进口货物运输相关费用备注 I_OtherNote an..255 价格申报项：
		map.add(new LabelValue("I_OtherNote", supplment.getI_OtherNote()));

		// 进口货物保险费单位金额 I_InsurPrice an..20 价格申报项：
		map.add(new LabelValue("I_InsurPrice", supplment.getI_InsurPrice()));

		// 进口货物保险费总金额 I_InsurAmount an..20 价格申报项：
		map.add(new LabelValue("I_InsurAmount", supplment.getI_InsurAmount()));

		// 进口货物保险费备注 I_InsurNote an..255 价格申报项：
		map.add(new LabelValue("I_InsurNote", supplment.getI_InsurNote()));

		// 出口关税是否已经从申报价格中扣除 E_IsDutyDel an..1 价格申报项：
		map.add(new LabelValue("E_IsDutyDel", supplment.getE_IsDutyDel()));

		// 商品其他名称 GNameOther an..255 归类申报项：
		map.add(new LabelValue("GNameOther", supplment.getGnameOther()));

		// 进/出口国地区海关商品编码 CodeTsOther 是 an10 归类申报项：
		map.add(new LabelValue("CodeTsOther", supplment.getCodeTsOther()));

		// 该商品是否取得过海关预归类决定书 IsClassDecision 是 an1 归类申报项：
		// 0：否 1：是
		// 如选择是，则后面三项必填。
		map.add(new LabelValue("IsClassDecision", supplment
				.getIsClassDecision()));

		// 预归类决定书编号 DecisionNO an..30 归类申报项：
		map.add(new LabelValue("DecisionNO", supplment.getDecisionNo()));

		// 预归类决定书商品编码 CodeTsDecision 是 an10 归类申报项：
		map
				.add(new LabelValue("CodeTsDecision", supplment
						.getCodeTsDecision()));

		// 作出预归类决定的直属海关 DecisionCus 是 an4 归类申报项：
		map.add(new LabelValue("DecisionCus",
				supplment.getDecisionCus() == null ? "" : supplment
						.getDecisionCus().getCode()));

		// 该商品是否曾被海关取样化验 IsSampleTest 是 an1 归类申报项：
		// 0：否 1：是
		map.add(new LabelValue("IsSampleTest", supplment.getIsSampleTest()));

		// 商品信息选项 Goptions an..32 归类申报项：
		// A成分及比例
		// B原料及组成
		// C生产加工工艺
		// D构成
		// E技术参数
		// F具体规格
		// G工作原理
		// H车型、排量
		// I功能
		// J用途
		// K加工程度
		// L性能指标
		// M其他信息
		// 以上可多选，至少选择一项。
		// 存储方式：13位有效位，选择了A－M项的任何一个，则对应位为1，否则为0。
		map.add(new LabelValue("Goptions", supplment.getGoptions()));

		// 运输方式 TrafMode 是 an1 原产地申报项：
		map.add(new LabelValue("TrafMode", supplment.getTrafMode() == null ? ""
				: supplment.getTrafMode().getCode()));

		// 是否直接运输 IsDirectTraf 是 an1 原产地申报项：
		// 0：否 1：是
		map.add(new LabelValue("IsDirectTraf", supplment.getIsDirectTraf()));

		// 中转国地区 TransitCountry an..20 原产地申报项：
		// 如果选择非直接运输，必填
		map.add(new LabelValue("TransitCountry",
				supplment.getTransitCountry() == null ? "" : supplment
						.getTransitCountry().getCode()));

		// 到货口岸 DestPort 是 an4 原产地申报项：
		// 海关参数
		map.add(new LabelValue("DestPort", supplment.getDestPort() == null ? ""
				: supplment.getDestPort().getCode()));

		// 申报口岸 DeclPort 是 an4 原产地申报项：
		// 海关参数
		map.add(new LabelValue("DeclPort", supplment.getDeclPort() == null ? ""
				: supplment.getDeclPort().getCode()));

		// 提单编号 BillNo an..32 原产地申报项：
		map.add(new LabelValue("BillNo", supplment.getBillNo()));

		// 原产国（地区） OriginCountry 是 an3 原产地申报项：
		// COUNTRY参数
		map.add(new LabelValue("OriginCountry",
				supplment.getOriginCountry() == null ? "" : supplment
						.getOriginCountry().getCode()));

		// 原产国（地区）标记的位置 OriginMark an..5 原产地申报项：
		// 非参数选项，可录入汉字或字母
		map.add(new LabelValue("OriginMark", supplment.getOriginMark()));

		// 原产地证书签发机构及所在国家（地区） CertCountry an..30 原产地申报项
		map.add(new LabelValue("CertCountry",
				supplment.getCertCountry() == null ? "" : supplment
						.getCertCountry().getCode()));
		// 原产地证书编号 CertNO an..30 原产地申报项：
		map.add(new LabelValue("CertNO", supplment.getCertNo()));

		// 适用的原产地标准 CertStandard 是 an1 原产地申报项：
		// 1：完全获得 2：税号改变 3：制造或加工工序 4：从价百分比 5：混合标准 6：其他标准
		map.add(new LabelValue("CertStandard", supplment.getCertStandard()));

		// 其他需要说明的情况 OtherNote an..1000 公共申报项：
		map.add(new LabelValue("OtherNote", supplment.getOtherNote()));

		// 对以上申报内容是否需要海关予以保密 IsSecret 是 an1 公共申报项：
		// 0：否
		// 1：是
		map.add(new LabelValue("IsSecret", supplment.getIsSecret()));

		// 申报单位类型 AgentType 是 an1 公共申报项：
		// 1:进出口货物收发货人
		// 2:委托申报的报关企业
		map.add(new LabelValue("AgentType", supplment.getAgentType()));

		// 申报人单位地址 DeclAddr an..255 公共申报项：
		map.add(new LabelValue("DeclAddr", supplment.getDeclAddr()));

		// 申报人邮编 DeclPost an..10 公共申报项：
		map.add(new LabelValue("DeclPost", supplment.getDeclPost()));

		// 申报人电话 DeclTel an..20 公共申报项：
		map.add(new LabelValue("DeclTel", supplment.getDeclTel()));

		return map;
	}

	/**
	 * 构建业务数据-报关单/转关单-补充报关单签名信息
	 * 
	 * @param args
	 * @return
	 */
	private static List<LabelValue> buildDecSupplementSign(Map<String, Object> args) {
		DecSupplementList supplment = (DecSupplementList) args.get("supplment");
		List<LabelValue> map = new ArrayList<LabelValue>();

		// OperType 是 an1 操作类型 默认为G：OperType
		// C:补充申报单申报
		// G：补充申报单暂存
		// 正则表达式: [A-G]
		map.add(new LabelValue("OperType", supplment.getOperType()));

		// IcCardId 是 an13 当前操作的操作员IC卡号。 当前操作的操作员IC卡号IcCardId
		// 正则表达式: [0-9]{13}
		map.add(new LabelValue("IcCardId", supplment.getIcCardId()));

		// ClientSeqNo 是 an18 可空，数据中心报文给回ClientSeqNo
		// 正则表达式: .{18}
		map.add(new LabelValue("ClientSeqNo", supplment.getClientSeqNo()));

		// SignDate 是 an16 YYYYMMDDHHMMSSmm SignDate
		map.add(new LabelValue("SignDate", CommonUtils.getDate(supplment
				.getSignDate(), "yyyyMMddHHmmssss")));

		// SignData 是 an..201 SignData
		map.add(new LabelValue("SignData", supplment.getSignInfo()));

		return map;
	}

	/**
	 * 构建业务数据-报关单/转关单-自定义信息
	 * 
	 * @param args
	 * @return
	 */
	private static List<LabelValue> buildPrivateList(Map<String, Object> args) {
		List<LabelValue> map = new ArrayList<LabelValue>();
		// PrivateInformation
		map.add(new LabelValue("PrivateInformation",
				buildPrivateInformation(args)));
		return map;
	}

	/**
	 * 构建业务数据-报关单/转关单-自定义信息
	 * 
	 * @param args
	 * @return
	 */
	private static List<LabelValue> buildPrivateInformation(Map<String, Object> args) {
		List<LabelValue> map = new ArrayList<LabelValue>();
		map.add(new LabelValue("FieldType", "aaa"));
		map.add(new LabelValue("FieldName", "aaa"));
		map.add(new LabelValue("FieldValue", "aaa"));
		return map;
	}
}
