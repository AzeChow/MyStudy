package com.bestway.common.erpbill.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.common.Request;
import com.bestway.common.erpbill.entity.CustomOrder;
import com.bestway.common.erpbill.entity.CustomOrderDetail;
import com.bestway.common.erpbill.entity.Customparames;
import com.bestway.common.materialbase.entity.ScmCoc;

public interface ErpBillAction {
    /**
	 * 保存客户订单
	 * 
	 * @param request
	 * @param order
	 */
	 CustomOrder saveCustomOrder(Request request, CustomOrder order) ;

	/**
	 * 删除客户订单
	 * 
	 * @param request
	 * @param order
	 */
	void deleteCustomOrder(Request request, CustomOrder order) ;
	/**
	 * 查询客户订单
	 * 
	 * @param request
	 * @return
	 */
	List findCustomOrder(Request request);
	
	CustomOrder findCustomOrderBybillCode(Request request,String billcode);
	
	List findCustomOrder(Request request,Date Startdate,Date enddate);
	
	List findCustomOrder(String hsql,Object[] object);
	
	int findCustomOrderMaxImportcount(Request request,String billcode);

	/**
	 * 保存客户订单明细
	 * 
	 * @param request
	 * @param orderDetail
	 */
	CustomOrderDetail saveCustomOrderDetail(Request request,
			CustomOrderDetail orderDetail) ;

	/**
	 * 删除客户订单明细
	 * 
	 * @param request
	 * @param orderDetail
	 */
	void deleteCustomOrdeDetail(Request request,
			CustomOrderDetail orderDetail);
	/**
	 * 删除客户订单明细
	 * 
	 * @param request
	 * @param order
	 */
	void deleteCustomOrdeDetail(Request request, CustomOrder order);

	/**
	 * 查询客户订单明细
	 * 
	 * @param request
	 * @param order
	 * @return
	 */
	List findCustomOrderDetail(Request request, CustomOrder order) ;
	
	CustomOrderDetail findCustomOrderDetailByPtno(Request request, CustomOrder order,String ptno) ;
	
	/**
	 * 保存参数设置
	 * @param value
	 */
	Customparames saveparames(Request request,Customparames value);
	
	/**
	 * 查询参数设置
	 * @return
	 */
	Customparames findparames(Request request);
	/**
	 * 查询单据表头及明细
	 * 
	 * @param request
	 * @param pmap 
	 * @return
	 */
	Map<BillMaster, List<BillDetail>> findBillMaster(Request request, Map pmap);
	
	/**
	 * 根据参数查询订单明细的个数
	 * 
	 * @param isShowTransfer 是否显示转厂
	 * @param startdate  开始日期
	 * @param enddate 结束日期
	 * @param customer 客户供应商
	 * @param billCode 订单号
	 * @param ptNo BOM号
	 * @return
	 */
	public Integer findCustomOrderDetailCount(Request request,Boolean isShowTransfer, Date startdate,
			Date enddate, ScmCoc customer, String billCode, String ptNo) ;
	
	/**
	 * 根据参数查询订单明细的一些栏位
	 * 
	 * @param isShowTransfer 是否显示转厂
	 * @param startdate  开始日期
	 * @param enddate 结束日期
	 * @param customer 客户供应商
	 * @param billCode 订单号
	 * @param ptNo BOM号
	 * @return
	 */
	public List findSomeObjectInCustomOrderDetail(Request request,Boolean isShowTransfer, Date startdate,
			Date enddate, ScmCoc customer, String billCode, String ptNo);
	
	/**
	 * 合并订单成品资料
	 * 
	 * @param request
	 * @param temCustomOrderList 成品资料List
	 * @return
	 */
	public List checkAndUniteMaster(Request request,List temCustomOrderList);
	
	/**
	 * 根据订单中的成品资料合并料件资料
	 * 
	 * @param request
	 * @param temCustomOrderList 成品资料List
	 * @return
	 */
	public List checkAndUniteDetail(Request request,List temCustomOrderList) ;
}
