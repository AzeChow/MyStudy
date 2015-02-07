package com.bestway.bls.action;

import java.util.List;

import com.bestway.bls.dao.EntranceMessageDao;
import com.bestway.bls.entity.BlsReceiptResult;
import com.bestway.bls.entity.EntranceMessage;
import com.bestway.bls.logic.BillSpecialApplyLogic;
import com.bestway.bls.logic.EntranceMessageLogic;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;

/**
 * 货到报告接口实现
 * 
 * @author hw
 * 
 */
//保税物流-货到信息
@AuthorityClassAnnotation(caption = "保税仓管理", index = 17)
public class EntranceMessageActionImpl extends BaseActionImpl implements EntranceMessageAction {
	/**
	 * 货到报告Dao类
	 */
	private EntranceMessageDao entranceMessageDao = null;
	/**
	 * 货到报告Logic类
	 */
	private EntranceMessageLogic entranceMessageLogic = null;

	public EntranceMessageDao getEntranceMessageDao() {
		return entranceMessageDao;
	}

	public void setEntranceMessageDao(EntranceMessageDao entranceMessageDao) {
		this.entranceMessageDao = entranceMessageDao;
	}

	public EntranceMessageLogic getEntranceMessageLogic() {
		return entranceMessageLogic;
	}

	public void setEntranceMessageLogic(
			EntranceMessageLogic entranceMessageLogic) {
		this.entranceMessageLogic = entranceMessageLogic;
	}

	/**
	 * 找到正在执行的所有的车次
	 * 
	 * @param deliveryNo
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "货到信息-查询", index = 1)
	public List findProcessDelivery(Request request) {
		return this.entranceMessageDao.findProcessDelivery();
	}

	/**
	 * 根据车次号找到所对应的车次信息
	 */
	@AuthorityFunctionAnnotation(caption = "货到信息-查询", index = 1)
	public List findDeliveryByDeliveryNo(Request request, String deliveryNo) {
		return this.entranceMessageDao.findDeliveryByDeliveryNo(deliveryNo);
	}

	/**
	 * 导入车次信息
	 * 
	 * @param deliveryList
	 *            是Delivery型，车次信息
	 * 
	 * @return List 是Delivery 型
	 */
	@AuthorityFunctionAnnotation(caption = "导入车次信息", index = 3)
	public List importDelivery(Request request, List deliveryList) {
		return this.entranceMessageLogic.importDelivery(deliveryList);
	}

	/**
	 * 找到货到信息中所有的车次
	 * 
	 * @param deliveryNo
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "货到信息-查询", index = 1)
	public List findEntranceMessage(Request request) {
		return this.entranceMessageDao.findEntranceMessage();
	}

	/**
	 * 找到货到信息中所有的车次
	 * 
	 * @param deliveryNo
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "货到信息-查询", index = 1)
	public List findEntranceMessage(Request request, String ioFlog) {
		return this.entranceMessageDao.findEntranceMessage(ioFlog);
	}

	/**
	 * 删除货到信息表头
	 */
	@AuthorityFunctionAnnotation(caption = "货到信息-删除", index = 6)
	public void deleteEntranceMessage(Request request,
			EntranceMessage entranceMessage) {
		this.entranceMessageDao.deleteEntranceMessage(entranceMessage);
	}

	/**
	 * 保存货到车次
	 * 
	 * @param entranceMessage
	 *            货到车次信息
	 */
	@AuthorityFunctionAnnotation(caption = "保存货到车次", index = 7)
	public EntranceMessage saveEntranceMessage(Request request,
			EntranceMessage entranceMessage) {
		this.entranceMessageDao.saveEntranceMessage(entranceMessage);
		return entranceMessage;
	}

	/**
	 * 车到报告海关申报
	 */
	@AuthorityFunctionAnnotation(caption = "车到报告-海关申报", index = 8)
	public EntranceMessage applyFreightage(Request request,
			EntranceMessage entranceMessage) {
		return this.entranceMessageLogic.applyFreightage(entranceMessage);
	}

	/**
	 * 车到报告申报回执处理
	 * 
	 * @param delivery
	 * @param blsReceiptResult
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "车到报告申报-回执处理", index = 9)
	public EntranceMessage processFreightage(Request request,
			EntranceMessage entranceMessage, BlsReceiptResult blsReceiptResult) {
		return this.entranceMessageLogic.processFreightage(entranceMessage,
				blsReceiptResult);
	}

	/**
	 * 过滤掉货到报告中已有的车次信息
	 */
	@AuthorityFunctionAnnotation(caption = "过滤掉货到报告中已有的车次信息", index = 10)
	public List findAndFiltrateDeliveryNo(Request request, String ioFlag) {
		return this.entranceMessageLogic.findAndFiltrateDeliveryNo(ioFlag);
	}

	/**
	 * 找出所有的企业编码
	 * 
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "货到信息-查询", index = 1)
	public List findBrief(Request request) {
		return this.entranceMessageDao.findBrief();
	}

	/**
	 * 重新在数据库里lode一遍
	 * 
	 * @param cls
	 * @param id
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "货到信息-查询", index = 1)
	public Object load(Class cls, String id) {
		return this.entranceMessageDao.load(cls, id);
	}

	/**
	 * 获得货到报告基本信息（表头）所有的数据
	 * 
	 * @param index
	 *            数据的开始下标
	 * 
	 * @param length
	 *            数据长度
	 * 
	 * @param property
	 *            属性
	 * 
	 * @param value
	 *            属性的值
	 * 
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * 
	 * @return 所有货到报告信息（表头）数据
	 */
	@AuthorityFunctionAnnotation(caption = "货到信息-查询", index = 1)
	public List findEntranceMessage(Request request, int index, int length,
			String property, Object value, boolean isLike, String inout) {
		return this.entranceMessageDao.findEntranceMessage(index, length,
				property, value, isLike, inout);
	}
}
