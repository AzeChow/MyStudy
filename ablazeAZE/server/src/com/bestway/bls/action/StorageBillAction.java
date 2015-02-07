package com.bestway.bls.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bestway.bls.entity.BlsReceiptResult;
import com.bestway.bls.entity.CollateBind;
import com.bestway.bls.entity.Delivery;
import com.bestway.bls.entity.StorageBill;
import com.bestway.bls.entity.StorageBillAfter;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityFunctionAnnotation;

/**
 * 车次管理 ACTION 接口 checked by kcb 2009-1-10
 * 
 * @author kangbo
 * 
 */
public interface StorageBillAction {
	/**
	 * 保存一个对像
	 * 
	 * @param objs
	 * @return
	 */
	public Object saveOrUpdateObject(Request request, Object obj);

	/**
	 * 保存多个对像
	 * 
	 * @param obj
	 * @return
	 */
	public List saveOrUpdateObjects(Request request, List objs);

	/**
	 * 删除一个对像
	 * 
	 * @param obj
	 * @return
	 */
	public void deleteObject(Request request, Object obj);

	/**
	 * 删除多个对像
	 * 
	 * @param obj
	 * @return
	 */
	public void deleteObjects(Request request, List objs);

	/**
	 * 删除车次
	 * 
	 * @param delivery
	 */
	public void deleteDelivery(Request request, Delivery objs);

	/**
	 * 删除仓单
	 * 
	 * @param storageBill
	 */

	public void deleteStorageBill(Request request, StorageBill storageBill);

	/**
	 * 删除仓单－－归并后
	 * 
	 * @param storageBill
	 */
	public void deleteStorageBillAfter(Request request,
			StorageBillAfter storageBill);

	/**
	 * 删除仓单--出口--归并后，要反写数据
	 * 
	 * @param storageBillafter
	 */
	public void deleteStorageBillAfterByExport(Request request,
			StorageBillAfter storageBillafter);

	/**
	 * 根据车次号 查找车次
	 * 
	 * @param deliveryNo
	 * @return
	 */
	public List findDelivery(Request request, String deliveryNo);

	/**
	 * 根据进出口标志 查找车次
	 * 
	 * @param deliveryNo
	 * @return
	 */
	public List findDeliveryByInOut(Request request, int inOut);

	/**
	 * 根据仓单号查找仓单
	 */
	public List findStorageBill(Request request, String billNo);

	/**
	 * 根据有效期查询
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List findStorageBill(Request request, Date startDate, Date endDate,
			String inout);

	/**
	 * 根据进出口标志查找仓单
	 */
	public List findStorageBillByInOut(Request request, String inout);

	/**
	 * 根据车次查找仓单
	 * 
	 * @param delivery
	 * @return
	 */
	public List findStorageBillForDelivery(Request request, Delivery delivery);

	/**
	 * 根据进出标志及仓单号查找仓单
	 * 
	 * @param billNo
	 * @param inOutFlag
	 * @return
	 */
	public List findStorageBillForDeliveryAdd(Request request,
			Delivery delivery, String inOutFlag);

	/**
	 * 根据仓单查找仓单归并后
	 * 
	 * @param storageBill
	 * @return
	 */
	public List findStorageBillAfterForDelivery(Request request,
			StorageBill storageBill);

	/**
	 * 根据ID查找仓单
	 * 
	 * @param id
	 * @return
	 */
	public StorageBill findStorageBillByID(Request request, String id);

	/**
	 * 根据ID查找车次信息
	 * 
	 * @param id
	 * @return
	 */
	public Delivery findDeliveryByID(Request request, String id);

	/**
	 * 查找内部归并，根据车次号过滤
	 * 
	 * @param storageBill
	 * @return
	 */
	public List findStorageBillAfterForDeliveryAdd(Request request,
			StorageBill storageBill);

	/**
	 * 根据仓单归并后查找归并前
	 * 
	 * @param storageBillAfter
	 * @return
	 */
	public List findStorageBillBeforeForDelivery(Request request,
			StorageBillAfter storageBillAfter);

	/**
	 * 新增仓单管理－－归并前
	 * 
	 * @param sbill
	 * @return
	 */
	public List findStorageBillBeforeForDeliveryAdd(Request request,
			StorageBill sbill);

	/**
	 * 查找仓单号归并后最大序号
	 * 
	 * @param storageBill
	 * @return
	 */
	public Integer findMaxStorageBillAfterSeqNo(Request request,
			StorageBill storageBill);

	/**
	 * 新增仓单管理－－归并后
	 * 
	 * @param sbill
	 * @return
	 */
	public List saveStorageBillAfterAndBefore(Request request, List list);

	/**
	 * 查找进口仓单归并后
	 * 
	 * @return
	 */
	public List findBillAfterNotOut(Request request);

	/**
	 * 保存仓单归并后－－新增多个时用到
	 * 
	 * @param map
	 */
	public void saveBillAfterForExport(Request request, Map map);

	/**
	 * 查找仓单表项目个数
	 * 
	 * @param sbill
	 * @return
	 */
	public Integer findAmountpicesFromStorageBill(Request request,
			StorageBill sbill);

	/**
	 * 查找车次总件数
	 * 
	 * @param sbill
	 * @return
	 */
	public Integer findAmountpicesFromDelivery(Request request, Delivery dev);

	/**
	 * 查找车次表体项目个数
	 * 
	 * @param sbill
	 * @return
	 */
	public Integer findCountFromDelivery(Request request, Delivery dev);

	// public List findStorageBillBeforeForDelivery(Request request,
	// Delivery delivery);
	//
	// public List findStorageBillBeforeForDeliveryAdd(Request request,
	// Delivery delivery);

	/**
	 * 车次海关申报
	 */
	Delivery applyDelivery(Request request, Delivery delivery);

	/**
	 * 车次申报回执处理
	 * 
	 * @param delivery
	 * @param blsReceiptResult
	 * @return
	 */
	Delivery processDelivery(Request request, Delivery delivery,
			BlsReceiptResult blsReceiptResult);

	/**
	 * 新增仓单归并前
	 * 
	 * @param request
	 * @param addList
	 * @param storageBill
	 */
	void addStorageBillBefore(Request request, List addList,
			StorageBill storageBill);

	/**
	 * 获得车次基本信息（表头）所有的数据
	 * 
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 所有核销捆绑关系基本信息（表头）数据
	 */
	public List<CollateBind> findDelivery(Request request, int index,
			int length, String property, Object value, boolean isLike,
			String inout);

	/**
	 * 获得核销捆绑关系基本信息（表头）所有的数据
	 * 
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 所有核销捆绑关系基本信息（表头）数据
	 */
	public List findDelivery(Request request, Date startDate, Date endDate,
			String inout);

	/**
	 * 获得仓单基本信息（表头）所有的数据
	 * 
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 所有核销捆绑关系基本信息（表头）数据
	 */
	public List<CollateBind> findStorageBill(Request request, int index,
			int length, String property, Object value, boolean isLike,
			String inout);

	/**
	 * 检查仓单数据合法性
	 * 
	 * @param sbill
	 * @return
	 */
	public String checkStorageBill(Request request, StorageBill sbill);

	/**
	 * 查询库存信息
	 * 
	 * @param serviceInfostring
	 * @param handlestring
	 * @return
	 */
	public List findStockFromHP(Request request, String emsNo,
			String warehouseCode, String customsCode, String contrItem,
			String codeTS, String gName, String gModel, String gUnit);

	/**
	 * 查询仓单信息 hw
	 * 
	 * @param serviceInfostring
	 * @param handlestring
	 * @return List
	 */
	public List findStorageBillStatusFromHP(Request request, String tradeCo,
			String billNo);

	/**
	 * 找到剩余数量
	 */
	public Double findRemainingQuantity(Request request, String corrBillNo,Integer seqNum);
	/**
	 * 改变车次管理表头的申报状态
	 */
	public Delivery changeDeliveryDeclareState(Request request,Delivery delivery,String declareState);
}
