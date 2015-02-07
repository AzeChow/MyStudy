package com.bestway.common.erpbill.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.erpbill.dao.ErpBillDao;
import com.bestway.common.erpbill.entity.CustomOrder;
import com.bestway.common.erpbill.entity.CustomOrderDetail;
import com.bestway.common.erpbill.entity.Customparames;
import com.bestway.common.erpbill.logic.ErpBillLogic;
import com.bestway.common.materialbase.entity.ScmCoc;

public class ErpBillActionImpl implements ErpBillAction {
	private ErpBillDao erpBillDao;

	private ErpBillLogic erpBillLogic;

	public ErpBillDao getErpBillDao() {
		return erpBillDao;
	}

	public void setErpBillDao(ErpBillDao erpBillDao) {
		this.erpBillDao = erpBillDao;
	}

	public ErpBillLogic getErpBillLogic() {
		return erpBillLogic;
	}

	public void setErpBillLogic(ErpBillLogic erpBillLogic) {
		this.erpBillLogic = erpBillLogic;
	}

	/**
	 * 保存客户订单
	 * 
	 * @param request
	 * @param order
	 */
	public CustomOrder saveCustomOrder(Request request, CustomOrder order) {
		this.erpBillDao.saveCustomOrder(order);
		return order;
	}

	/**
	 * 删除客户订单
	 * 
	 * @param request
	 * @param order
	 */
	public void deleteCustomOrder(Request request, CustomOrder order) {
		this.erpBillLogic.deleteCustomOrder(order);
	}

	/**
	 * 查询客户订单
	 * 
	 * @param request
	 * @return
	 */
	public List findCustomOrder(Request request) {
		return this.erpBillDao.findCustomOrder();
	}

	/**
	 * 保存客户订单明细
	 * 
	 * @param request
	 * @param orderDetail
	 */
	public CustomOrderDetail saveCustomOrderDetail(Request request,
			CustomOrderDetail orderDetail) {
		this.erpBillDao.saveCustomOrderDetail(orderDetail);
		return orderDetail;
	}

	/**
	 * 删除客户订单明细
	 * 
	 * @param request
	 * @param orderDetail
	 */
	public void deleteCustomOrdeDetail(Request request,
			CustomOrderDetail orderDetail) {
		this.erpBillDao.deleteCustomOrdeDetail(orderDetail);
	}

	/**
	 * 删除客户订单明细
	 * 
	 * @param request
	 * @param order
	 */
	public void deleteCustomOrdeDetail(Request request, CustomOrder order) {
		this.erpBillDao.deleteCustomOrdeDetail(order);
	}

	/**
	 * 查询客户订单明细
	 * 
	 * @param request
	 * @param order
	 * @return
	 */
	public List findCustomOrderDetail(Request request, CustomOrder order) {
		return this.erpBillDao.findCustomOrderDetail(order);
	}

	/**
	 * 查询单据表头及明细
	 * 
	 * @param request
	 * @param pmap
	 * @return
	 */
	public Map<BillMaster, List<BillDetail>> findBillMaster(Request request,
			Map pmap) {
		return this.erpBillLogic.findBillMaster(pmap);
	}

	/**
	 * 保存参数设置
	 * 
	 * @param value
	 */
	public Customparames saveparames(Request request, Customparames value) {
		this.erpBillDao.saveparames(value);
		return value;
	}

	/**
	 * 查询参数设置
	 * 
	 * @return
	 */
	public Customparames findparames(Request request) {
		return this.erpBillDao.findparames();
	}

	public CustomOrder findCustomOrderBybillCode(Request request,
			String billcode) {
		return this.erpBillDao.findCustomOrderBybillCode(billcode);
	}

	public List findCustomOrder(Request request, Date Startdate, Date enddate) {

		return this.erpBillDao.findCustomOrder(Startdate, enddate);
	}

	public CustomOrderDetail findCustomOrderDetailByPtno(Request request,
			CustomOrder order, String ptno) {
		return this.erpBillDao.findCustomOrderDetailByPtno(order, ptno);
	}

	public int findCustomOrderMaxImportcount(Request request, String billcode) {
		return this.erpBillDao.findCustomOrderMaxImportcount(billcode);
	}

	public List findCustomOrder(String hsql, Object[] object) {
		return this.erpBillDao.find(hsql, object);
	}

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
			Date enddate, ScmCoc customer, String billCode, String ptNo) {
		return erpBillDao.findCustomOrderDetailCount(isShowTransfer, startdate,
				enddate, customer, billCode, ptNo);
	}
	
	/**
	 *  根据参数查询订单明细的一些栏位
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
			Date enddate, ScmCoc customer, String billCode, String ptNo) {
		return erpBillLogic.findSomeObjectInCustomOrderDetail(isShowTransfer, startdate,
				enddate, customer, billCode, ptNo);
	}
	
	
	/**
	 * 合并订单成品资料
	 * 
	 * @param request
	 * @param temCustomOrderList 成品资料List
	 * @return
	 */
	public List checkAndUniteMaster(Request request,List temCustomOrderList) {
		return erpBillLogic.checkAndUniteMaster(temCustomOrderList);
	}
	
	/**
	 * 根据订单中的成品资料合并料件资料
	 * 
	 * @param request
	 * @param temCustomOrderList 成品资料List
	 * @return
	 */
	public List checkAndUniteDetail(Request request,List temCustomOrderList) {
		return erpBillLogic.checkAndUniteDetail(temCustomOrderList);
	}
	
}
