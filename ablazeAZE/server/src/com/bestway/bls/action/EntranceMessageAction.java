package com.bestway.bls.action;

import java.util.List;

import com.bestway.bls.entity.BlsReceiptResult;
import com.bestway.bls.entity.EntranceMessage;
import com.bestway.common.Request;
/**
 * 货到报告服务器端接口
 * @author hw
 *
 */
public interface EntranceMessageAction {

	/**
	 * 找到正在执行的所有的车次
	 * 
	 * @param deliveryNo
	 * @return
	 */
	public List findProcessDelivery(Request request);

	/**
	 * 根据车次号找到所对应的车次信息
	 */
	public List findDeliveryByDeliveryNo(Request request, String deliveryNo);

	/**
	 * 导入车次信息
	 * 
	 * @param deliveryList
	 *            是Delivery型，车次信息
	 * 
	 * @return List 是Delivery 型
	 */
	public List importDelivery(Request request, List deliveryList);

	/**
	 * 找到货到信息中所有的车次
	 * 
	 * @param deliveryNo
	 * @return
	 */
	public List findEntranceMessage(Request request);
	
	/**
	 * 找到货到信息中所有的车次
	 * 
	 * @param deliveryNo
	 * @return
	 */
	public List findEntranceMessage(Request request,String ioFlog);

	/**
	 * 删除货到信息表头
	 */
	public void deleteEntranceMessage(Request request,
			EntranceMessage entranceMessage);

	/**
	 * 保存货到车次
	 * 
	 * @param entranceMessage
	 *            货到车次信息
	 */
	public EntranceMessage saveEntranceMessage(Request request,
			EntranceMessage entranceMessage);

	/**
	 * 车到报告海关申报
	 */
	EntranceMessage applyFreightage(Request request,
			EntranceMessage entranceMessage);

	/**
	 * 车到报告申报回执处理
	 * 
	 * @param delivery
	 * @param blsReceiptResult
	 * @return
	 */
	EntranceMessage processFreightage(Request request,
			EntranceMessage entranceMessage, BlsReceiptResult blsReceiptResult);
	/**
	 * 过滤掉货到报告中已有的车次信息
	 */
	public List findAndFiltrateDeliveryNo(Request request,String ioFlag);
	/**
     * 找出所有的企业编码
     * @return
     */
	public List findBrief(Request request);
	
	/**
	 * 重新在数据库里lode一遍
	 * @param cls
	 * @param id
	 * @return
	 */
	Object load(Class cls, String id);
	
	/**
	 * 获得货到报告基本信息（表头）所有的数据
	 * 
	 * @param index 数据的开始下标
	 *            
	 * @param length 数据长度
	 *            
	 * @param property 属性
	 *            
	 * @param value 属性的值
	 *            
	 * @param isLike 查找时用“Like”还是“＝”
	 *            
	 * @return 所有货到报告信息（表头）数据
	 */
	public List findEntranceMessage(Request request,int index, int length, String property,
			Object value, boolean isLike, String inout);
}
