package com.bestway.common.constant;

/**
 * 
 * @author Administrator
 * 各种补充申报的类型
 */
public class RepDeclarationType {
	/**
	 * 补充申报单申报
	 */
	public static final String OperTypeREPORT  = "C"; 

	/**
	 * 补充申报单暂存
	 */
	public static final String OperTypeSUSPEND = "G";
	
	/**
	 * 价格补充申报单
	 */
	public static final String SupTypePrice = "A";
	
	/**
	 * 商品归类补充申报单
	 */
	public static final String SupTypeMerger = "B";
	
	/**
	 * 原产地补充申报单
	 */
	public static final String SupTypeOrigin = "C";
	
	/**
	 * 申报内容是否需要海关予以保密-否
	 */
	public static final String 	IsSecretN = "0";
	
	/**
	 * 申报内容是否需要海关予以保密-是
	 */
	public static final String 	IsSecretY = "1";
	
	/**
	 * 买卖双方关系是否影响进口货物成交价格-不影響
	 */
	public static final String 	I_PriceEffectN = "0";
	
	/**
	 * 买卖双方关系是否影响进口货物成交价格-影響
	 */
	public static final String 	I_PriceEffectY = "1";
	/**
	 * 与同时或大约同时向境内无特殊关系的买方出售的相同或类似货物的成交价格相近
	 */
	public static final String 	I_PriceCloseT="1";
	/**
	 * 与同时或大约同时相同或类似货物的倒扣价格相近
	 */
	public static final String 	I_PriceCloseC="2";
	/**
	 * 与同时或大约同时相同或类似货物的计算价格相近
	 */
	public static final String 	I_PriceCloseUd="3";
	/**
	 * 没有以上相近的价格
	 */
	public static final String 	I_PriceCloseN="4";
	
	/**
	 * 不受限制
	 */
	public static final String I_OtherLimitedN="0";
	/**
	 * 受限制
	 */
	public static final String I_OtherLimitedY="1";
	/**
	 * 不受影響
	 */
	public static final String I_OtherEffectN="0";
	/**
	 * 受影響
	 */
	public static final String I_OtherEffectY="1";
	/**
	 * 该商品是否取得过海关预归类决定书-否
	 */
	public static final String IsClassDecisionN="0";
	/**
	 * 该商品是否取得过海关预归类决定书-是
	 */
	public static final String IsClassDecisionY="1";
	/**
	 * 该商品是否曾被海关取样化验-否
	 */
	public static final String IsSampleTestN="0";
	/**
	 * 该商品是否曾被海关取样化验-是
	 */
	public static final String IsSampleTestY="1";
	/**
	 *是否直接运输-否
	 */
	public static final String IsDirectTrafN="0";
	/**
	 * 是否直接运输-是
	 */
	public static final String IsDirectTrafY="1";
	
	/**
	 *适用的原产地标准-完全获得
	 */
	public static final String CertStandardAll="1";
	/**
	 * 适用的原产地标准-税号改变
	 */
	public static final String CertStandardT="2";
	/**
	 *适用的原产地标准-制造或加工工序
	 */
	public static final String CertStandardP="3";
	/**
	 * 适用的原产地标准-从价百分比
	 */
	public static final String CertStandardPt="4";
	/**
	 *适用的原产地标准-混合标准
	 */
	public static final String CertStandardM="5";
	/**
	 * 适用的原产地标准-其他标准
	 */
	public static final String CertStandardO="6";
	
	/**
	 * 买卖双方无以下任何一种关系
	 */
	public static final String I_BabRel0="00000000";
	/**
	 * 买卖双方为同一家族成员
	 */
	public static final String I_BabRel1="1";
	/**
	 * 买卖双方互为商业上的高级职员或董事
	 */
	public static final String I_BabRel2="2";
	/**
	 * 买卖双方互为商业上的高级职员或董事
	 */
	public static final String I_BabRel3="3";
	/**
	 * 买卖双方都直接或间接地受第三方控制
	 */
	public static final String I_BabRel4="4";
	/**
	 * 买卖双方共同直接或间接地控制第三方
	 */
	public static final String I_BabRel5="5";
	/**
	 * 一方直接或间接地拥有控制或持有对方5%或以上的公开发行的有表决权的股票或股份
	 */
	public static final String I_BabRel6="6";
	/**
	 * 一方是另一方雇员、高级职员或董事
	 */
	public static final String I_BabRel7="7";
	/**
	 * 买卖双方是同一合伙的成员
	 */
	public static final String I_BabRel8="8";
	/**
	 * 申报单位类型,进出口货物收发货人
	 */
	public static final Integer AgentType_PerSon = Integer.valueOf(1);
	/**
	 * 申报单位类型,委托申报的报关企业
	 */
	public static final Integer AgentType_Factory = Integer.valueOf(2);
	/**
	 * 出口关税是否已经从申报价格中扣除-是
	 */
	public static final String E_IsDutyDelY="1";
	/**
	 * 出口关税是否已经从申报价格中扣除-否
	 */
	public static final String E_IsDutyDelN="0";
	
	/**
	 * 买方是否应直接或间接支付与进口货物有关并作为货物销售条件的特许权使用费-是
	 */
	public static final String I_IsUsefeeY="1";
	/**
	 * 买方是否应直接或间接支付与进口货物有关并作为货物销售条件的特许权使用费-否
	 */
	public static final String I_IsUsefeeN="0";
	
	/**
	 * 卖方是否直接或间接从买方对该货物进口后销售、处置或者使用所得中获得收益-是
	 */
	public static final String I_IsProfitY="1";
	/**
	 * 卖方是否直接或间接从买方对该货物进口后销售、处置或者使用所得中获得收益-否
	 */
	public static final String I_IsProfitN="0";
	
	/**
	 * 原产国（地区）标记的位置-外包装
	 */
	public static final String OriginMarkOUT="1";
	/**
	 * 原产国（地区）标记的位置-内包装
	 */
	public static final String OriginMarkIN="2";
	/**
	 * 原产国（地区）标记的位置-产品本体
	 */
	public static final String OriginMarkItSelf="3";
	/**
	 * 原产国（地区）标记的位置-无
	 */
	public static final String OriginMarkNo="0";
	
	public static final String getOperType(String value) {
		String returnValue = "";
		if (RepDeclarationType.OperTypeREPORT.equals(value)) {
			returnValue = "补充申报单申报";
		} else if (RepDeclarationType.OperTypeSUSPEND.equals(value)) {
			returnValue = "补充申报单暂存";
		} 
		return returnValue;
	}
	public static final String getSupType(String value) {
		String returnValue = "";
		if (RepDeclarationType.SupTypePrice.equals(value)) {
			returnValue = "价格补充申报单";
		} else if (RepDeclarationType.SupTypeMerger.equals(value)) {
			returnValue = "商品归类补充申报单";
		} else if (RepDeclarationType.SupTypeOrigin.equals(value)) {
			returnValue = "原产地补充申报单";
		} 
		return returnValue;
	}
	public static final String getE_IsDutyDel(String value) {
		String returnValue = "";
		if (RepDeclarationType.E_IsDutyDelN.equals(value)) {
			returnValue = "否";
		} else if (RepDeclarationType.E_IsDutyDelY.equals(value)) {
			returnValue = "是";
		}
		return returnValue;
	}
	public static final String getI_IsUsefee(String value) {
		String returnValue = "";
		if (RepDeclarationType.I_IsUsefeeN.equals(value)) {
			returnValue = "否";
		} else if (RepDeclarationType.I_IsUsefeeY.equals(value)) {
			returnValue = "是";
		}
		return returnValue;
	}
	public static final String getI_IsProfit(String value) {
		String returnValue = "";
		if (RepDeclarationType.I_IsProfitN.equals(value)) {
			returnValue = "否";
		} else if (RepDeclarationType.I_IsProfitY.equals(value)) {
			returnValue = "是";
		}
		return returnValue;
	}
	public static final String getIsSecret(String value) {
		String returnValue = "";
		if (RepDeclarationType.IsSecretN.equals(value)) {
			returnValue = "否";
		} else if (RepDeclarationType.IsSecretY.equals(value)) {
			returnValue = "是";
		}
		return returnValue;
	}
	public static final String getI_PriceEffect(String value) {
		String returnValue = "";
		if (RepDeclarationType.I_PriceEffectN.equals(value)) {
			returnValue = "不影响";
		} else if (RepDeclarationType.I_PriceEffectY.equals(value)) {
			returnValue = "影响";
		}
		return returnValue;
	}
	public static final String getI_PriceClose(String value) {
		String returnValue = "";
		if (RepDeclarationType.	I_PriceCloseT.equals(value)) {
			returnValue = "同时或大约同时向境内无特殊关系的买方出售的相同或类似货物的成交价格相近";
		} else if (RepDeclarationType.I_PriceCloseC.equals(value)) {
			returnValue = "同时或大约同时相同或类似货物的倒扣价格相近";
		}else if (RepDeclarationType.I_PriceCloseUd.equals(value)) {
			returnValue = "同时或大约同时相同或类似货物的计算价格相近";
		}else if (RepDeclarationType.I_PriceCloseN.equals(value)) {
			returnValue = "没有以上相近的价格";
		}
		return returnValue;
	}
	public static final String getI_OtherLimited(String value) {
		String returnValue = "";
		if (RepDeclarationType.I_OtherLimitedN.equals(value)) {
			returnValue = "不受限制";
		} else if (RepDeclarationType.I_OtherLimitedY.equals(value)) {
			returnValue = "受限制";
		}
		return returnValue;
	}
	public static final String getI_OtherEffect(String value) {
		String returnValue = "";
		if (RepDeclarationType.I_OtherEffectN.equals(value)) {
			returnValue = "不影响";
		} else if (RepDeclarationType.I_OtherEffectY.equals(value)) {
			returnValue = "影响";
		}
		return returnValue;
	}
	public static final String getIsClassDecision(String value) {
		String returnValue = "";
		if (RepDeclarationType.IsClassDecisionN.equals(value)) {
			returnValue = "否";
		} else if (RepDeclarationType.IsClassDecisionY.equals(value)) {
			returnValue = "是";
		}
		return returnValue;
	}
	public static final String getIsSampleTest(String value) {
		String returnValue = "";
		if (RepDeclarationType.IsSampleTestN.equals(value)) {
			returnValue = "否";
		} else if (RepDeclarationType.IsSampleTestY.equals(value)) {
			returnValue = "是";
		}
		return returnValue;
	}
	public static final String getIsDirectTraf(String value) {
		String returnValue = "";
		if (RepDeclarationType.IsDirectTrafN.equals(value)) {
			returnValue = "否";
		} else if (RepDeclarationType.IsDirectTrafY.equals(value)) {
			returnValue = "是";
		}
		return returnValue;
	}
	public static final String getCertStandard(String value) {
		String returnValue = "";
		if (RepDeclarationType.CertStandardAll.equals(value)) {
			returnValue = "完全获得";
		} else if (RepDeclarationType.CertStandardT.equals(value)) {
			returnValue = "税号改变 ";
		}else if (RepDeclarationType.CertStandardP.equals(value)) {
			returnValue = "制造或加工工序";
		}else if (RepDeclarationType.CertStandardPt.equals(value)) {
			returnValue = "从价百分比";
		}else if (RepDeclarationType.CertStandardM.equals(value)) {
			returnValue = "混合标准";
		}else if (RepDeclarationType.CertStandardO.equals(value)) {
			returnValue = "其他标准";
		}
		return returnValue;
	}
	/**
	 * 把01的存储方式转为买卖双方关系
	 * @param value
	 * @return
	 */
	public static final String getI_BabRel(String value) {
		String returnString = "";
		if("00000000".equals(value)){
			returnString = "0.买卖双方无以下任何一种关系";
		}else{
			char[] babRel = value.toCharArray();
			if ("1".equals(babRel[0])) {
				returnString += "1.买卖..";
			}if("1".equals(babRel[1])){
				returnString += "2.买卖..";
			}if ("1".equals(babRel[2])) {
				returnString += "3.一方..";
			}if ("1".equals(babRel[3])) {
				returnString += "4.买卖.";
			}if ("1".equals(babRel[4])) {
				returnString += "5.买卖..";
			}if ("1".equals(babRel[5])) {
				returnString += "6.一方..";
			}if ("1".equals(String.valueOf(babRel[6]))) {
				returnString += "7.一方..";
			}if ("1".equals(String.valueOf(babRel[7]))) {
				returnString += "8.买卖..";
			}
		}
		return returnString;
	}
	/**
	 * 把买卖双方关系转为01的存储方式
	 * @param value
	 * @return
	 */
	public static final String getI_BabRelToNum(String value) {
		String returnValue = "";
		if(value.indexOf("0")>0){
			returnValue="00000000";
		}else {
			if(value.indexOf("1")>-1){
				returnValue="1";
			}else{
				returnValue="0";
			}
			if(value.indexOf("2")>-1){
				returnValue+="1";
			}else{
				returnValue+="0";
			}
			if(value.indexOf("3")>-1){
				returnValue+="1";
			}else{
				returnValue+="0";
			}
			if(value.indexOf("4")>-1){
				returnValue+="1";
			}else{
				returnValue+="0";
			}
			if(value.indexOf("5")>-1){
				returnValue+="1";
			}else{
				returnValue+="0";
			}
			if(value.indexOf("6")>-1){
				returnValue+="1";
			}else{
				returnValue+="0";
			}
			if(value.indexOf("7")>-1){
				returnValue+="1";
			}else{
				returnValue+="0";
			}
			if(value.indexOf("8")>-1){
				returnValue+="1";
			}else{
				returnValue+="0";
			}
		}
		return returnValue; 
	}
	/**
	 * 把01的存储方式转为商品信息选项
	 * @param value
	 * @return
	 */
	public static final String getGoptions(String value) {
		String returnString = "";
		if(value!=null){
			char[] babRel = value.toCharArray();
			if ("1".equals(String.valueOf(babRel[0]))) {
				returnString += "A.成分..";
			}if("1".equals(String.valueOf(babRel[1]))){
				returnString += "B.原料..";
			}if ("1".equals(String.valueOf(babRel[2]))) {
				returnString += "C.生产..";
			}if ("1".equals(String.valueOf(babRel[3]))) {
				returnString += "D.构成";
			}if ("1".equals(String.valueOf(babRel[4]))) {
				returnString += "E.技术..";
			}if ("1".equals(String.valueOf(babRel[5]))) {
				returnString += "F.具体..";
			}if ("1".equals(String.valueOf(babRel[6]))) {
				returnString += "G.工作..";
			}if ("1".equals(String.valueOf(babRel[7]))) {
				returnString += "H.车型..";
			}
			if ("1".equals(String.valueOf(babRel[8]))) {
				returnString += "I.功能";
			}
			if ("1".equals(String.valueOf(babRel[9]))) {
				returnString += "J.用途";
			}
			if ("1".equals(String.valueOf(babRel[10]))) {
				returnString += "K.加工..";
			}
			if ("1".equals(String.valueOf(babRel[11]))) {
				returnString += "L.性能..";
			}
			if ("1".equals(String.valueOf(babRel[12]))) {
				returnString += "M其他信息";
			}
		}
		return returnString;
	}
	/**
	 * 把商品信息选项转为01的存储方式
	 * @param value
	 * @return
	 */
	public static final String getGoptionsToNum(String value) {
		String returnValue = "";
		if(value!=null){
			if(value.indexOf("A")>-1){
				returnValue="1";
			}else{
				returnValue="0";
			}
			if(value.indexOf("B")>-1){
				returnValue+="1";
			}else{
				returnValue+="0";
			}
			if(value.indexOf("C")>-1){
				returnValue+="1";
			}else{
				returnValue+="0";
			}
			if(value.indexOf("D")>-1){
				returnValue+="1";
			}else{
				returnValue+="0";
			}
			if(value.indexOf("E")>-1){
				returnValue+="1";
			}else{
				returnValue+="0";
			}
			if(value.indexOf("F")>-1){
				returnValue+="1";
			}else{
				returnValue+="0";
			}
			if(value.indexOf("G")>-1){
				returnValue+="1";
			}else{
				returnValue+="0";
			}
			if(value.indexOf("H")>-1){
				returnValue+="1";
			}else{
				returnValue+="0";
			}
			if(value.indexOf("I")>-1){
				returnValue+="1";
			}else{
				returnValue+="0";
			}
			if(value.indexOf("J")>-1){
				returnValue+="1";
			}else{
				returnValue+="0";
			}
			if(value.indexOf("K")>-1){
				returnValue+="1";
			}else{
				returnValue+="0";
			}
			if(value.indexOf("L")>-1){
				returnValue+="1";
			}else{
				returnValue+="0";
			}
			if(value.indexOf("M")>-1){
				returnValue+="1";
			}else{
				returnValue+="0";
			}
		}
		return returnValue; 
	}
	public static String getAgentType(int agentType) {
		if(agentType == AgentType_PerSon) {
			return "进出口货物收发货人";
		} else if(agentType == AgentType_Factory) {
			return "委托申报的报关企业";
		} else {
			return "进出口货物收发货人";
		}
	}
	public static String getOriginMark(String value) {
		if(RepDeclarationType.OriginMarkNo.equals(value)) {
			return "无";
		} else if(RepDeclarationType.OriginMarkOUT.equals(value)) {
			return "外包装";
		} else if(RepDeclarationType.OriginMarkIN.equals(value)){
			return "内包装";
		}else if(RepDeclarationType.OriginMarkItSelf.equals(value)){
			return "产品本体";
		}else{
			return "无";
		}
	}
}
