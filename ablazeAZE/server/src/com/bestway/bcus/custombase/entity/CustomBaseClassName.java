package com.bestway.bcus.custombase.entity;

import com.bestway.common.constant.ImpExpType;

public class CustomBaseClassName {
	
	
	public static final String ApplicationMode = "ApplicationMode";
	public static final String BalanceMode = "BalanceMode";
	public static final String BargainType = "BargainType";
	public static final String BillType  = "BillType";
	public static final String Brief = "Brief";
	public static final String ClassList = "ClassList";
	public static final String ContaModel = "ContaModel";
	public static final String ContaSize = "ContaSize";
	public static final String CoType = "CoType";
	public static final String Country = "Country";
	public static final String Curr  = "Curr";
	public static final String Customs = "Customs";
	public static final String CustomsComplex = "CustomsComplex";
	public static final String Deduc = "Deduc";
	public static final String District = "District";
	public static final String DriverList  = "DriverList";
	public static final String FieldList = "FieldList";
	public static final String Gbtobig = "Gbtobig";
	public static final String InvClass = "InvClass";
	public static final String InvestMode = "InvestMode";
	public static final String LevyKind  = "LevyKind";
	public static final String SrtJzx  = "SrtJzx";
	public static final String SrtTj  = "SrtTj";
	public static final String StsCode  = "StsCode";
	public static final String LevyMode   = "LevyMode";
	public static final String LicenseDocu  = "LicenseDocu";
	public static final String LicenseNote  = "LicenseNote"; 
	public static final String MachiningType  = "MachiningType";
	public static final String TaxCode  = "TaxCode";
	public static final String PayerType  = "PayerType";
	public static final String PayMode  = "PayMode";
	public static final String PayType  = "PayType";
	public static final String PortInternal  = "PortInternal";
	public static final String PortLin   = "PortInternal";
	public static final String PreDock   = "PreDock";
	public static final String RedDep  = "RedDep";
	public static final String SaicCode  = "SaicCode";
	public static final String Trade  = "Trade";
	public static final String Transac  = "Transac";
	public static final String Transf   = "Transf";
	public static final String Unit  = "Unit";
	public static final String Uses  = "Uses";
	public static final String Wrap  = "Wrap";
	
	public static String getCustomBaseClassName(String customBaseClass){

		String str = "";
		if(ApplicationMode.equalsIgnoreCase(customBaseClass)){
			str = "申报/报关方式资料";
		}else if (BalanceMode.equalsIgnoreCase(customBaseClass)){
			str = "结汇方式资料";
		}else if (BargainType.equalsIgnoreCase(customBaseClass)){
			str = "合同类型资料";
		}
		else if (BillType.equalsIgnoreCase(customBaseClass)){
			str = "单据类别";
		}
		else if (Brief.equalsIgnoreCase(customBaseClass)){
			str = "海关注册公司的资料";
		}
		else if (ClassList.equalsIgnoreCase(customBaseClass)){
			str = "类列表";
		}
		else if (ContaModel.equalsIgnoreCase(customBaseClass)){
			str = "集装箱规格资料";
		}
		else if (ContaSize.equalsIgnoreCase(customBaseClass)){
			str = "集装箱尺寸资料";
		}
		else if (CoType.equalsIgnoreCase(customBaseClass)){
			str = "企业性质资料";
		}
		else if (Country.equalsIgnoreCase(customBaseClass)){
			str = "国家地区资料";
		}
		else if (Curr.equalsIgnoreCase(customBaseClass)){
			str = "币制代码资料";
		}
		else if (Customs.equalsIgnoreCase(customBaseClass)){
			str = "海关关区资料";
		}
		else if (CustomsComplex.equalsIgnoreCase(customBaseClass)){
			str = "海关商品编码资料";
		}
		else if (Deduc.equalsIgnoreCase(customBaseClass)){
			str = "核扣方式资料";
		}
		else if (District.equalsIgnoreCase(customBaseClass)){
			str = "地区代码资料";
		}
		else if (DriverList.equalsIgnoreCase(customBaseClass)){
			str = "驱动列表";
		}
		else if (FieldList.equalsIgnoreCase(customBaseClass)){
			str = "字段列表";
		}
		else if (Gbtobig.equalsIgnoreCase(customBaseClass)){
			str = "繁简体对照表资料";
		}
		else if (InvClass.equalsIgnoreCase(customBaseClass)){
			str = "投资类型资料";
		}
		else if (InvestMode.equalsIgnoreCase(customBaseClass)){
			str = "投资方式资料";
		}
		else if (InvestMode.equalsIgnoreCase(customBaseClass)){
			str = "投资方式资料";
		}
		else if (LevyKind.equalsIgnoreCase(customBaseClass)){
			str = "征免性质";
		}
		else if (SrtJzx.equalsIgnoreCase(customBaseClass)){
			str = "集装箱代码资料";
		}
		else if (SrtTj.equalsIgnoreCase(customBaseClass)){
			str = "集装箱托架种类资料";
		}
		else if (StsCode.equalsIgnoreCase(customBaseClass)){
			str = "技术监督局资料";
		}
		else if (TaxCode.equalsIgnoreCase(customBaseClass)){
			str = "税务部门资料";
		}
		else if (LevyMode.equalsIgnoreCase(customBaseClass)){
			str = "征免方式";
		}
		else if (LicenseDocu.equalsIgnoreCase(customBaseClass)){
			str = "证件代码资料";
		}
		else if (LicenseNote.equalsIgnoreCase(customBaseClass)){
			str = "领证商品备注表资料";
		}
		else if (MachiningType.equalsIgnoreCase(customBaseClass)){
			str = "加工种类资料";
		}else if (PayerType.equalsIgnoreCase(customBaseClass)){
			str = "付款者类型资料";
		}else if (PayMode.equalsIgnoreCase(customBaseClass)){
			str = "保税方式资料";
		}else if (PayType.equalsIgnoreCase(customBaseClass)){
			str = "付款类型";
		}else if (PortInternal.equalsIgnoreCase(customBaseClass)){
			str = "国内进出口口岸资料";
		}else if (PortLin.equalsIgnoreCase(customBaseClass)){
			str = "国际进出口港口资料";
		}else if (PreDock.equalsIgnoreCase(customBaseClass)){
			str = "国内进出口码头资料";
		}else if (RedDep.equalsIgnoreCase(customBaseClass)){
			str = "外经贸部门资料";
		}else if (SaicCode.equalsIgnoreCase(customBaseClass)){
			str = "工商管理行政局资料";
		}else if (Trade.equalsIgnoreCase(customBaseClass)){
			str = "贸易方式资料";
		}else if (Transac.equalsIgnoreCase(customBaseClass)){
			str = "成交方式资料";
		}else if (Transf.equalsIgnoreCase(customBaseClass)){
			str = "成交方式资料";
		}else if (Unit.equalsIgnoreCase(customBaseClass)){
			str = "运输方式资料";
		}else if (Uses.equalsIgnoreCase(customBaseClass)){
			str = "用途代码资料";
		}else if (Wrap.equalsIgnoreCase(customBaseClass)){
			str = "包装种类资料";
		}
		return str;
	
	}
}
