package com.bestway.bls.dao;

/**
 * 车次管理DAO层checked by kcb 2009-1-10
 * 
 * @author kangbo
 * 
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bestway.bcus.system.entity.Company;
import com.bestway.bls.entity.BlsParameterSet;
import com.bestway.bls.entity.Delivery;
import com.bestway.bls.entity.StorageBill;
import com.bestway.bls.entity.StorageBillAfter;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DeclareState;

public class StorageBillDao extends BaseDao {
	/**
	 * 根据车次号 查找车次
	 * 
	 * @param deliveryNo
	 * @return
	 */
	public List findDelivery(String deliveryNo) {
		List para = new ArrayList();
		String hsql = " select a from Delivery  a  where a.company.id =? ";
		para.add(CommonUtils.getCompany().getId());
		if (deliveryNo != null && !deliveryNo.equals("")) {
			hsql += " and  a.deliveryNo =?  ";
			para.add(deliveryNo.trim());
		}
		return this.find(hsql, para.toArray());
	}

	/**
	 * 根据车次号 查找车次
	 * 
	 * @param deliveryNo
	 * @return
	 */
	public List findDelivery(Company company, String deliveryNo) {
		List para = new ArrayList();
		String hsql = " select a from Delivery  a  where a.company.id =? ";
		para.add(company.getId());
		if (deliveryNo != null && !deliveryNo.equals("")) {
			hsql += " and  a.deliveryNo =?  ";
			para.add(deliveryNo.trim());
		}
		return this.find(hsql, para.toArray());
	}

	/**
	 * 根据进出标志查找车次
	 * 
	 * @param inOut
	 * @return
	 */
	public List findDeliveryByInOut(int inOut) {
		List para = new ArrayList();
		String hsql = " select a from Delivery  a  where a.company.id =? and a.inOut= ? ";
		para.add(CommonUtils.getCompany().getId());
		para.add(inOut);
		return this.find(hsql, para.toArray());
	}

	/**
	 * 根据仓单号查找仓单
	 * 
	 * @param billNo
	 * @return
	 */
	public List findStorageBill(String billNo) {
		List para = new ArrayList();
		String hsql = " select a from StorageBill  a  where a.company.id =?"
				+ " and  a.delivery  is null ";
		para.add(CommonUtils.getCompany().getId());
		if (billNo != null && !billNo.equals("")) {
			hsql += " and  a.billNo =?  ";
			para.add(billNo.trim());
		}
		return this.find(hsql, para.toArray());
	}
	
	/**
	 * 根据仓单号查找车次
	 * 
	 * @param billNo
	 * @return
	 */
	public List<?> findDeliveryByBillNo(String billNo,Company company) {
		List<String> para = new ArrayList<String>();
		String hsql = " select distinct a.delivery from StorageBill  a  where a.company.id =?"
				+ " and  a.delivery is not null ";
		para.add(company.getId());
		if (billNo != null && !billNo.equals("")) {
			hsql += " and  a.billNo =?  ";
			para.add(billNo.trim());
		}
		return this.find(hsql, para.toArray());
	}

	/**
	 * 根据进出标志查找仓单
	 * 
	 * @param inout
	 * @return
	 */
	public List findStorageBillByInOut(String inout) {
		List para = new ArrayList();
		String hsql = " select a from StorageBill  a  where a.company.id =?"
				+ "   and a.ioFlag=? ";
		para.add(CommonUtils.getCompany().getId());
		para.add(inout);
		return this.find(hsql, para.toArray());
	}

	/**
	 * 根据进出标志及仓单号查找仓单
	 * 
	 * @param billNo
	 * @param inOutFlag
	 * @return
	 */
	public List findStorageBill(String billNo, String inOutFlag) {
		List para = new ArrayList();
		String hsql = " select a from StorageBill  a  where a.company.id =?"
				+ " and  a.delivery  is null  and a.ioFlag= ?  and a.effective=? ";
		para.add(CommonUtils.getCompany().getId());
		para.add(inOutFlag);
		// para.add("00");
		para.add(true);
		if (billNo != null && !billNo.equals("")) {
			hsql += " and  a.billNo =?  ";
			para.add(billNo.trim());
		}

		return this.find(hsql, para.toArray());
	}

	/**
	 * 根据车次查找仓单
	 * 
	 * @param delivery
	 * @return
	 */
	public List findStorageBillForDelivery(Delivery delivery) {
		if (delivery.getId() == null) {
			System.out.println("the  delivery   id  is null !");
			return new ArrayList();
		}
		String hsql = " select a from StorageBill a where  a.company.id =?  and a.delivery= ? ";
		List para = new ArrayList();
		para.add(CommonUtils.getCompany().getId());
		para.add(delivery);
		return this.find(hsql, para.toArray());
	}

	/**
	 * 根据车次查找仓单
	 * 
	 * @param delivery
	 * @return
	 */
	public List findStorageBillByDelivery(Delivery delivery) {
		if (delivery.getId() == null) {
			System.out.println("the  delivery   id  is null !");
			return new ArrayList();
		}
		String hsql = " select a from StorageBill a where  a.company.id =?  and a.delivery= ? ";
		List para = new ArrayList();
		para.add(CommonUtils.getCompany().getId());
		para.add(delivery);
		return this.find(hsql, para.toArray());
	}

	/**
	 * 根据仓单查找仓单归并后
	 * 
	 * @param storageBill
	 * @return
	 */
	public List findStorageBillAfterForDelivery(StorageBill storageBill) {
		if (storageBill.getId() == null) {
			System.out.println("the  storageBill   id  is null !");
			return new ArrayList();
		}
		String hsql = " select a from StorageBillAfter a where  a.company.id =?  and a.storageBill= ? ";
		List para = new ArrayList();
		para.add(CommonUtils.getCompany().getId());
		para.add(storageBill);
		return this.find(hsql, para.toArray());
	}

	/**
	 * 查找内部归并，根据车次号过滤
	 * 
	 * @param storageBill
	 * @return
	 */
	public List findBlsTenInnerMerge(StorageBill storageBill) {
		String hsql = " select  a  from BlsTenInnerMerge a where  a.company.id =? "
				+ "  and  a.complex  not in"
				+ " (  select a.codeTS from StorageBillAfter a "
				+ " where  a.company.id =?  and a.storageBill= ?  and a.codeTS  is not null  )  ";
		List para = new ArrayList();
		para.add(CommonUtils.getCompany().getId());
		para.add(CommonUtils.getCompany().getId());
		para.add(storageBill);
		return this.find(hsql, para.toArray());
	}

	/**
	 * 查找仓单号归并后最大序号
	 * 
	 * @param storageBill
	 * @return
	 */
	public Integer findMaxStorageBillAfterSeqNo(StorageBill storageBill) {
		if (storageBill.getId() == null) {
			System.out.println("the  storageBill   id  is null !");
			return 0;
		}
		String hsql = " select MAX(a.seqNo) from StorageBillAfter a where  a.company.id =?  and a.storageBill= ? ";
		List para = new ArrayList();
		para.add(CommonUtils.getCompany().getId());
		para.add(storageBill);

		List list = this.find(hsql, para.toArray());
		if (list == null || list.isEmpty() || list.get(0) == null) {
			return 0;
		} else {
			return Integer.parseInt(list.get(0).toString());
		}
	}

	/**
	 * 根据仓单号查询商品
	 * 
	 * @param billNo
	 *            仓单号
	 * @return
	 */
	public List findStorageBillByBillNo(String billNo) {
		List para = new ArrayList();
		String hsql = " select a from StorageBill  a  where a.company.id =? ";
		para.add(CommonUtils.getCompany().getId());
		if (billNo != null && !billNo.equals("")) {
			hsql += " and  a.billNo =?  ";
			para.add(billNo.trim());
		}
		return this.find(hsql, para.toArray());
	}

	/**
	 * 根据仓单归并后查找归并前
	 * 
	 * @param storageBillAfter
	 * @return
	 */
	public List findStorageBillBeforeForDelivery(
			StorageBillAfter storageBillAfter) {
		List para = new ArrayList();
		String hsql = " select a from StorageBillBefore  a  where a.company.id =? ";
		para.add(CommonUtils.getCompany().getId());
		hsql += " and  a.storageBillAfter =?  ";
		para.add(storageBillAfter);
		return this.find(hsql, para.toArray());
	}

	/**
	 * 查找内部归并
	 * 
	 * @return
	 */
	public List findStorageBillBeforeForDeliveryAdd() {
		String hsql = " select  a  from BlsInnerMerge a "
				+ " where  a.company.id =?  and a.blsTenInnerMerge  is not null ";
		// + " and   a.materiel  not in "
		// + " (  select a.partNo from StorageBillBefore a "
		// +
		// " where  a.company.id =?  and    a.storageBillAfter.storageBill =?  and  a.partNo  is not null  )  ";
		List para = new ArrayList();
		para.add(CommonUtils.getCompany().getId());
		// para.add(CommonUtils.getCompany().getId());
		// para.add(storageBill);
		return this.find(hsql, para.toArray());
	}

	/**
	 * 查找进口仓单归并后
	 * 
	 * @return
	 */
	public List findBillAfterNotOut() {
		List para = new ArrayList();
		String hsql = " select a from StorageBillAfter  a  where a.company.id =? "
				// + " and ( a.isOut is null  or a.isOut=? )"
				+ "  and  a.storageBill.ioFlag=? and a.storageBill.delivery.declareState in (?,?) order by a.storageBill.validDate asc";
		para.add(CommonUtils.getCompany().getId());
		// para.add(new Boolean(false));
		para.add("I");
		para.add(DeclareState.WAIT_EAA);
		para.add(DeclareState.PROCESS_EXE);
		return this.find(hsql, para.toArray());
	}

	/**
	 * 根据仓单号及序号查找归并后
	 * 
	 * @param billNo
	 * @param seqNo
	 * @return
	 */
	public List findStorageBillAfterByBillNoAndSeqNo(String billNo,
			Integer seqNo) {
		List para = new ArrayList();
		String hsql = "select a from  StorageBillAfter  a "
				+ " where a.storageBill.billNo=?  and a.seqNo=? "
				+ " and a.company.id=?  and  a.storageBill.ioFlag=?  ";
		para.add(billNo);
		para.add(seqNo);
		para.add(CommonUtils.getCompany().getId());
		para.add("I");
		return this.find(hsql, para.toArray());
	}

	/**
	 * 查找仓单表项目个数
	 * 
	 * @param sbill
	 * @return
	 */
	public Integer findAmountpicesFromStorageBill(StorageBill sbill) {
		List para = new ArrayList();
		String hsql = "select COUNT(a) from  StorageBillAfter  a "
				+ " where a.storageBill=?   and a.company.id=?  ";
		para.add(sbill);
		para.add(CommonUtils.getCompany().getId());
		List lsit = this.find(hsql, para.toArray());
		if (lsit == null || lsit.size() == 0) {
			return 0;
		} else {
			return Integer.parseInt(lsit.get(0).toString());
		}
	}

	/**
	 * 查找车次表体项目个数
	 * 
	 * @param sbill
	 * @return
	 */
	public Integer findCountFromDelivery(Delivery sbill) {
		List para = new ArrayList();
		String hsql = "select COUNT(a) from  StorageBill  a "
				+ " where a.delivery=?   and a.company.id=?  ";
		para.add(sbill);
		para.add(CommonUtils.getCompany().getId());
		List lsit = this.find(hsql, para.toArray());
		if (lsit == null || lsit.size() == 0 || lsit.get(0) == null) {
			return 0;
		} else {
			return Integer.parseInt(lsit.get(0).toString());
		}
	}

	/**
	 * 查找车次总件数
	 * 
	 * @param sbill
	 * @return
	 */
	public Integer findAmountpicesFromDelivery(Delivery sbill) {
		List para = new ArrayList();
		String hsql = "select SUM(a.packCount) from  StorageBill  a "
				+ " where a.delivery=?   and a.company.id=?  ";
		para.add(sbill);
		para.add(CommonUtils.getCompany().getId());
		List lsit = this.find(hsql, para.toArray());
		if (lsit == null || lsit.size() == 0 || lsit.get(0) == null) {
			return 0;
		} else {
			return Integer.parseInt(lsit.get(0).toString());
		}
	}

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
	public List findDelivery(int index, int length, String property,
			Object value, boolean isLike, String inout) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a  from Delivery "
				+ " as a  where  a.company= ? and a.inOut= ?  ";

		parameters.add(CommonUtils.getCompany());
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
	public List findDelivery(Date startDate, Date endDate, String inout) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a  from Delivery "
				+ " as a  where  a.company= ? and a.inOut =? ";
		parameters.add(CommonUtils.getCompany());
		parameters.add(inout);
		if (startDate == null && endDate != null) {
			hsql = hsql + " and a.schedularArrivalDate<=?";
			parameters.add(endDate);
		} else if (startDate != null && endDate == null) {
			hsql = hsql + " and a.schedularArrivalDate>=?";
			parameters.add(startDate);
		} else if (startDate != null && endDate != null) {
			hsql = hsql + " and a.schedularArrivalDate>=?";
			hsql = hsql + " and a.schedularArrivalDate<=?";
			parameters.add(startDate);
			parameters.add(endDate);
		}
		return super.findListNoCache(hsql, parameters.toArray());
	}

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
	public List findStorageBill(int index, int length, String property,
			Object value, boolean isLike, String inout) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a  from StorageBill  "
				+ " as a  where  a.company= ? and a.ioFlag =? ";

		parameters.add(CommonUtils.getCompany());
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

	/**
	 * 根据有效期查询
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List findStorageBill(Date startDate, Date endDate, String inout) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from StorageBill  "
				+ " as a  where  a.company= ? and a.ioFlag =?";
		parameters.add(CommonUtils.getCompany());
		parameters.add(inout);
		if (startDate == null && endDate != null) {
			hsql = hsql + " and a.validDate<=?";
			parameters.add(endDate);
		} else if (startDate != null && endDate == null) {
			hsql = hsql + " and a.validDate>=?";
			parameters.add(startDate);
		} else if (startDate != null && endDate != null) {
			hsql = hsql + " and a.validDate>=?";
			hsql = hsql + " and a.validDate<=?";
			parameters.add(startDate);
			parameters.add(endDate);
		}
		hsql=hsql+ " order by a.validDate asc ";
		return super.findListNoCache(hsql, parameters.toArray());
	}

	/**
	 * 根据仓单查找仓单归并前所有商品
	 * 
	 * @param sbill
	 * @return
	 */
	public List findStorageBillBefore(StorageBill sbill) {
		List para = new ArrayList();
		String hsql = " select a from StorageBillBefore  a  where a.company.id =? ";
		para.add(CommonUtils.getCompany().getId());
		hsql += " and  a.storageBillAfter.storageBill =?  ";
		para.add(sbill);
		return this.find(hsql, para.toArray());
	}

	/**
	 * 根据ID查找仓单
	 * 
	 * @param id
	 * @return
	 */
	public List findStorageBillByID(String id) {
		List para = new ArrayList();
		String hsql = " select a from StorageBill  a  where a.company.id =? ";
		para.add(CommonUtils.getCompany().getId());
		hsql += " and  a.id =?  ";
		para.add(id);
		return this.find(hsql, para.toArray());
	}

	/**
	 * 根据ID查找车次信息
	 * 
	 * @param id
	 * @return
	 */
	public List findDeliveryByID(String id) {
		List para = new ArrayList();
		String hsql = " select a from Delivery  a  where a.company.id =? ";
		para.add(CommonUtils.getCompany().getId());
		hsql += " and  a.id =?  ";
		para.add(id);
		return this.find(hsql, para.toArray());
	}

	/**
	 * 查询保税物流参数参数
	 * 
	 * @param parameter
	 */
	public BlsParameterSet findBlsParameterSet() {
		List list = this.find(
				"select a from BlsParameterSet a where a.company.id=? ",
				new Object[] { CommonUtils.getCompany().getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return (BlsParameterSet) list.get(0);
		} else {
			BlsParameterSet parameterSet = new BlsParameterSet();
			this.saveOrUpdate(parameterSet);
			return parameterSet;
		}
	}

	/**
	 * 根据进仓出仓，是否生效，申报状态查询车次资料
	 * 
	 * @param company
	 * @param inOut
	 * @return
	 */
	public List findDelivery(Company company, String inOut,
			boolean isEffective, String declareState) {
		List para = new ArrayList();
		String hsql = " select a from Delivery  a  "
				+ " where a.company.id =? and a.inOut= ? "
				+ " and a.effective=? and a.declareState=? ";
		para.add(company.getId());
		para.add(inOut);
		para.add(isEffective);
		para.add(declareState);
		return this.find(hsql, para.toArray());
	}

	/**
	 * 找到剩余数量
	 */
	public Double findRemainingQuantity(String corrBillNo,Integer seqNum) {
		List para = new ArrayList();
		String hsql = "select SUM(a.qty) from StorageBillAfter a where a.corrBillNo=?"
				+ " and a.storageBill.ioFlag=? and a.storageBill.company.id=? and a.seqNum=?";
		para.add(corrBillNo);
		para.add("O");
		para.add(CommonUtils.getCompany().getId());
		para.add(seqNum);
		List list = this.find(hsql, para.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return (Double) list.get(0);
		}
		return 0.0;
	}
}
