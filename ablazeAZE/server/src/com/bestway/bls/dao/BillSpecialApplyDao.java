package com.bestway.bls.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.bestway.bls.entity.BillSpecialApplyApplyList;
import com.bestway.bls.entity.BillSpecialApplyHead;
import com.bestway.bls.entity.StorageBill;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;

/**
 * 仓单特殊申请dao
 * @author Administrator
 *
 */
public class BillSpecialApplyDao extends BaseDao{

	/*******************************特殊申请单表头*****************************************/
	/**
	 * 删除仓单特殊申请单表头
	 */
	public void deleteBillSpecialApplyHead(
			BillSpecialApplyHead billSpecialApplyHead) {
		// TODO Auto-generated method stub		
		this.delete(billSpecialApplyHead);
	}

	/**
	 * 查询所有仓单特殊申请单表头
	 */
	public List<BillSpecialApplyHead> findBillSpecialApplyHead() {
		// TODO Auto-generated method stub
		return this.find("from BillSpecialApplyHead as a where a.company.id =?",new Object[]{CommonUtils.getCompany().getId()});
	}

	/**
	 * 保存仓单特殊申请单表头
	 * @return
	 */
	public void saveBillSpecialApplyHead(
			BillSpecialApplyHead billSpecialApplyHead) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(billSpecialApplyHead);
	}

	/*******************************特殊申请单表体*****************************************/
	/**
	 * 删除仓单特殊申请单表体
	 */
	public void deleteApplyList(BillSpecialApplyApplyList applyList) {
		// TODO Auto-generated method stub		
		this.delete(applyList);
	}

	/**
	 * 保存仓单特殊申请单表体
	 */
	public void saveApplyList(BillSpecialApplyApplyList applyList) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(applyList);
	}
	
	/*******************************仓单里的商品*****************************************/
	/**
	 * 根据仓单号查询商品
	 * @param billNo 仓单号
	 */
	public List<BillSpecialApplyApplyList> findApplyList(BillSpecialApplyHead billSpecialApplyHead) {
		// TODO Auto-generated method stub
		String hsql = "from BillSpecialApplyApplyList as b where b.billSpecialApplyHead=? and b.company.id =?";
		return this.find(hsql,new Object[]{billSpecialApplyHead,CommonUtils.getCompany().getId()});
	}
	
	/**
	 * 根据仓单号查询商品
	 * @param billNo
	 *        仓单号
	 * @return
	 */
	public List findStorageBillByBillNo(String billNo) {
		List para = new ArrayList();
		String hsql = " select a from StorageBillAfter  a  where a.company.id =? ";
		para.add(CommonUtils.getCompany().getId());
		if (billNo != null && !billNo.equals("")) {
			hsql += " and  a.storageBill.billNo =?  ";
			para.add(billNo.trim());
		}
		return this.find(hsql, para.toArray());
	}
	
	/**
	 * 查询仓单
	 * @return
	 */
	public List<StorageBill> findStorageBill(){
		String hsql = "from StorageBill as a where a.company.id=?";
		return this.find(hsql,new Object[]{CommonUtils.getCompany().getId()});
	}
}
