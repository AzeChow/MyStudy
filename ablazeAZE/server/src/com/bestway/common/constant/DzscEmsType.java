package com.bestway.common.constant;

public class DzscEmsType {
	public static final String COME_PROCESS_CONTRACT = "X";// 来料加工合同手册

	public static final String IMPORT_PROCESS_CONTRACT = "Y"; // 进料加工合同手册

	public static final String COME_PROCESS_EMS_BILL = "B"; // 加工贸易来料手册

	public static final String IMPORT_PROCESS_EMS_BILL = "C"; // 加工贸易进料手册

	public static final String MACHINE_PROCESS_EMS_BILL = "T"; // 加工贸易设备手册
	
	public static String getDzscEmsType(String type){
		if(type==null || "".equals(type)){
			return "";
		}
		if("X".equals(type)){
			return "来料加工合同手册";
		}else if("Y".equals(type)){
			return "进料加工合同手册";
		}else if("B".equals(type)){
			return "加工贸易来料手册";
		}else if("C".equals(type)){
			return "加工贸易进料手册";
		}else if("T".equals(type)){
			return "加工贸易设备手册";
		}
		return "";
		
	}

}
