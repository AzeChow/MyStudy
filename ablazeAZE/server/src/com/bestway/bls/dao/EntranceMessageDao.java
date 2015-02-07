package com.bestway.bls.dao;

import java.util.ArrayList;
import java.util.List;

import com.bestway.bcus.cas.entity.BillType;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bls.entity.BlsInOutStockBillDetail;
import com.bestway.bls.entity.EntranceMessage;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.DeclareState;
/**
 * 货到报告DAO
 * @author hw
 *
 */
public class EntranceMessageDao extends BaseDao {
	/**
	 * 找到车次信息中所有的申报状态为等待审批的车次
	 * 
	 * @param deliveryNo
	 * @return
	 */
	public List findProcessDelivery() {
		List para = new ArrayList();
		String hsql = " select a from Delivery  a  where a.company.id =? and a.declareState=?";
		para.add(CommonUtils.getCompany().getId());
		para.add(DeclareState.WAIT_EAA);
		return this.find(hsql, para.toArray());
	}

	/**
	 * 找到车次信息中所有的申报状态为等待审批的车次
	 * 
	 * @param deliveryNo
	 * @return
	 */
	public List findProcessDelivery(String ioFlag) {
		List para = new ArrayList();
		String hsql = " select distinct a.delivery from StorageBill  a  where a.company.id =? " +
				" and a.delivery.declareState=? and a.ioFlag=? and a.billType!=?";
		para.add(CommonUtils.getCompany().getId());
		para.add(DeclareState.WAIT_EAA);
		para.add(ioFlag);
		para.add("00");//00-申报初始库存
		return this.find(hsql, para.toArray());
	}
	/**
	 * 找到货到信息中所有的车次
	 * 
	 * @param deliveryNo
	 * @return
	 */
	public List findEntranceMessage() {
		List para = new ArrayList();
		String hsql = " select a from EntranceMessage  a  where a.company.id =? ";
		para.add(CommonUtils.getCompany().getId());
		return this.find(hsql, para.toArray());
	}
	/**
	 * 找到货到信息中所有的车次
	 * 
	 * @param deliveryNo
	 * @return
	 */
	public List findEntranceMessage(String ioFlog) {
		List para = new ArrayList();
		String hsql = " select a from EntranceMessage  a  where a.company.id =? and a.ioFlag=? ";
		para.add(CommonUtils.getCompany().getId());
		para.add(ioFlog);
		return this.find(hsql, para.toArray());
	}
	

	/**
	 * 根据车次号找到所对应的车次信息(用来防止车次倒入重复)
	 */
	public List findDeliveryByDeliveryNo(String deliveryNo) {
		List para = new ArrayList();
		String hsql = " select a from EntranceMessage  a  where a.company.id =? ";
		para.add(CommonUtils.getCompany().getId());
		if (deliveryNo != null && !deliveryNo.equals("")) {
			hsql += " and  a.deliveryNo =?  ";
			para.add(deliveryNo.trim());
		}
		return this.find(hsql, para.toArray());
	}

	/**
	 * 保存货到车次
	 * 
	 * @param entranceMessage
	 *            货到车次信息
	 */
	public void saveEntranceMessage(EntranceMessage entranceMessage) {
		this.saveOrUpdate(entranceMessage);
	}

	/**
	 * 删除货到信息表头
	 */
	public void deleteEntranceMessage(EntranceMessage entranceMessage) {
		this.delete(entranceMessage);
	}

	/**
	 * 查出现有货到报告中的所有车次号
	 */
	public List findDeliveryNoFromEntranceMessage(String ioFlag) {
		List para = new ArrayList();
		String hsql = " select a.deliveryNo from EntranceMessage  a  where a.company.id =? and a.ioFlag=?";
		para.add(CommonUtils.getCompany().getId());
		para.add(ioFlag);
		return this.find(hsql, para.toArray());
	}

	/**
	 * 通过车次号找到车次信息中所有的车次
	 * 
	 * @param deliveryNo
	 * @return
	 */
	public List findDeliveryByDeliveryNos(String deliveryNo) {
		List<Object> para = new ArrayList<Object>();
		String hsql = " select a from Delivery  a  where a.company.id =? and a.declareState=? and a.deliveryNo=?";
		para.add(CommonUtils.getCompany().getId());
		para.add(DeclareState.PROCESS_EXE);
		para.add(deliveryNo.trim());
		return this.find(hsql, para.toArray());
	}
    /**
     * 找出所有的企业编码
     * @return
     */
	public List findBrief() {
		return this.find("select a from Brief a");
	}
	
	/**
	 * 根据进仓出仓，是否生效，申报状态查询车次资料
	 * 
	 * @param company
	 * @param inOut
	 * @return
	 */
	public List findEntranceMessage(Company company, String inOut,
			String declareState) {
		List para = new ArrayList();
		String hsql = " select a from EntranceMessage  a  "
				+ " where a.company.id =? and a.ioFlag= ? "
				+ " and a.declareState=? ";
		para.add(company.getId());
		para.add(inOut);
		para.add(declareState);
		return this.find(hsql, para.toArray());
	}
	

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
	public List findEntranceMessage(int index, int length, String property,
			Object value, boolean isLike, String inout) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a  from EntranceMessage"
				+ " as a  where  a.company.id= ? and a.ioFlag= ?  ";

		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(inout);
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				parameters.add(value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				parameters.add(value);
			}
		}
		return super.findListNoCache(hsql, parameters.toArray(), index, length);
	}
}
