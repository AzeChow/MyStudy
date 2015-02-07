package com.bestway.common.erpbillcenter.authority;

import com.bestway.common.Request;

public interface ErpBillCenterAuthority {

	// -----------------
	// 单据中心参数设置
	// -----------------

	void checkErpBillCenterParameterByBrowse(Request request);

	void checkErpBillCenterParameterByUpdate(Request request);

	// -----------------
	// 商品大类关系对应
	// -----------------

	public void checkRelationByBrower(Request request);

	// -----------------
	// 工厂单据类型
	// -----------------

	void checkBillTypeByBrower(Request request);

	// -----------------
	// 单据
	// -----------------

	void checkBillByBrower(Request request);

	void checkBillByUpdate(Request request);

	void checkBillByDelete(Request request);

	// -----------------
	// 特殊控制(单据中心)
	// -----------------
	void checkByBrower(Request request); 
	void check1ByBrower(Request request);
	void check2ByBrower(Request request);
	void check3ByBrower(Request request);
	void check4ByBrower(Request request);
	void check5ByBrower(Request request);
	void check6ByBrower(Request request);
	void check7ByBrower(Request request);
	void check8ByBrower(Request request);
	void check9ByBrower(Request request);

	void checkFactualCustoms(Request request);

	void checkCustomOrder(Request request);

}
