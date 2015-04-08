package com.bestway.bcus.system.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

public class InputTableColumnSet extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 保税物流-进出仓单据-单据商品信息导入
	 */
	public static String BLS_INOUTSTOCKBILL_INPUT="BLS_INOUTSTOCKBILL_INPUT";
	/**
	 * 保税物流-进出仓单据-单据商品信息导入
	 */
	public static String BCUS_CUSTOMBASE_BRIEF="BCUS_CUSTOMBASE_BRIEF";
	/**
	 * 电子化手册-备案资料库-料件表导入
	 */
	public static String DICT_IMG_INPUT = "DICT_IMG_INPUT";

	/**
	 * 电子化手册-备案资料库-成品表导入
	 */
	public static String DICT_EXG_INPUT = "DICT_EXG_INPUT";

	/**
	 * 电子化手册-通关手册-成品表导入
	 */
	public static String CONTRACT_EXG_INPUT = "CONTRACT_EXG_INPUT";

	/**
	 * 电子化手册-通关手册-料件表导入
	 */
	public static String CONTRACT_IMG_INPUT = "CONTRACT_IMG_INPUT";

	/**
	 * 电子化手册-通关手册-单耗表导入
	 */
	public static String CONTRACT_BOM_INPUT = "CONTRACT_BOM_INPUT";
	/**
	 * 电子化手册-物料与报关对应-成品导入
	 */
	public static String BCSINMERGER_EXG_INPUT = "BCSINMERGER_EXG_INPUT";
	/**
	 * 电子化手册-物料与报关对应-料件导入
	 */
	public static String BCSINMERGER_IMG_INPUT = "BCSINMERGER_IMG_INPUT";

	/**
	 * 物流基础资料-工厂通用代码-客户供应商
	 */
	public static String SCM_COCTXT = "SCM_COCTXT";

	/**
	 * 物流基础资料-工厂通用代码-工厂司机资料
	 */
	public static String DRIVER_INFO = "DRIVER_INFO";

	/**
	 * 进出口申请单导入
	 */
	public static String IMP_EXP_REQ_BILL_INPUT = "IMP_EXP_REQ_BILL_INPUT";
	/**
	 * 电子帐册-归并导入
	 */
	public static String EMSIMGER_BOM_INPUT = "EMSIMGER_BOM_INPUT";
	/**
	 * 电子帐册-内部归并关系-成品导入
	 */
	public static String EMSEDIMERGER_EXG_INPUT = "EMSEDIMERGER_EXG_INPUT";
	/**
	 * 电子帐册-内部归并关系-料件导入
	 */
	public static String EMSEDIMERGER_IMG_INPUT = "EMSEDIMERGER_IMG_INPUT";
	/**
	 * 电子帐册-内部归并关系-半成品导入
	 */
	public static String EMSEDIMERGER_FINISHEDPRODUCT_INPUT = "EMSEDIMERGER_FINISHEDPRODUCT_INPUT";
	/**
	 * 电子帐册-帐子帐册管理-成品单耗导入
	 */
	public static String EMSH2K_BOM_INPUT = "EMSH2K_BOM_INPUT";

	public static String ENTERPRISE_BOM_INPUT = "ENTERPRISE_BOM_INPUT";

	/**
	 *电子帐册-报关清单-导入进口
	 */
	public static String IMPORT_APPLY_CUSTOMS_BILL = "IMPORT_APPLY_CUSTOMS_BILL";
	/**
	 * 单据中心－单据管理－单据导入
	 */
	public static String BILL_INPUT = "BILL_INPUT";
	/**
	 * 单据中心－单据管理－对应关系
	 */
	public static String F_C_RALATION = "F_C_RALATION";
	
	/**
	 * 深加工结转-进出货转厂单据-商品明细-发货明细
	 */
	public static String FptBill_INPUT_SEND_DETAILS="FptBill_INPUT_SEND_DETAILS";
	/**
	 * 深加工结转-进出货转厂单据-商品明细-发货明细
	 */
	public static String FptBill_INPUT_RECEIVE_DETAILS="FptBill_INPUT_RECEIVE_DETAILS";
	/**
	 * 单据中心－订单管理－单据导入
	 */
	public static String CUSTOMORDER_INPUT="CUSTOMORDER_INPUT";
	
	/**
	 * 设备协议导入
	 */
	public static String EQUIPMENT_CONTRACT = "EQUIPMENT_CONTRACT";
	
	/**
	 * 外发加工申请表 外发货物导入
	 * @author wss2010.08.20
	 */
	public static String OWP_APP_INPUT_SEND = "OWP_APP_INPUT_SEND";
	
	/**
	 * 外发加工申请表 收回货物导入
	 * @author wss2010.08.20
	 */
	public static String OWP_APP_INPUT_RECV = "OWP_APP_INPUT_RECV";
	/**
	 * 物料主档导入
	 * @author hcl
	 * 2010.11.10
	 */
	public static String ENTERPRICE_MATERIAL = "ENTERPRICE_MATERIAL";
	/**
	 * 海关帐-实际盘点过程（编码级）
	 * @author hcl
	 * 2011.03-14
	 */
	public static String CHECKBALANCE_OFCUSTOM = "CHECKBALANCE_OFCUSTOM";
	/**
	 * 海关帐-实际盘点过程（料号级）
	 * @author hcl
	 * 2011.03-14
	 */
	public static String CHECKBALANCE_OFMATERIAL = "CHECKBALANCE_OFMATERIAL";
	/**
	 * 转厂管理--进出货转厂单据导入
	 * @author lxr
	 * 2011.09.02
	 */
	public static String TRANSFERFACTORY_IEBILL="TRANSFERFACTORY_IEBILL";
	
	/**
	 * 电子帐册 -- 滚动核销  -- 内购金额导入
	 * @author zcj 
	 * 2015-4-2
	 */
	public static String CANCELCHECK_CANCELCUS_INNERBUYIMPORT = "CANCELCHECK_CANCELCUS_INNERBUYIMPORT";
	
	private String tableFlag;

	private String columnField;

	private String columnCaption;

	private Integer sortNo;

	private boolean selected;

	public InputTableColumnSet() {

	}

	public InputTableColumnSet(String columnCaption, String columnField) {
		this.columnField = columnField;
		this.columnCaption = columnCaption;
	}

	public InputTableColumnSet(String tableFlag, String columnCaption,
			String columnField) {
		this.tableFlag = tableFlag;
		this.columnField = columnField;
		this.columnCaption = columnCaption;
	}

	public String getColumnCaption() {
		return columnCaption;
	}

	public void setColumnCaption(String columnCaption) {
		this.columnCaption = columnCaption;
	}

	public String getColumnField() {
		return columnField;
	}

	public void setColumnField(String columnField) {
		this.columnField = columnField;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public String getTableFlag() {
		return tableFlag;
	}

	public void setTableFlag(String tableFlag) {
		this.tableFlag = tableFlag;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
