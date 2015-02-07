package com.bestway.bls.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bestway.bcus.system.entity.Company;
import com.bestway.bls.entity.BlsAutoBackBillInfo;
import com.bestway.bls.entity.BlsMessageSend;
import com.bestway.bls.entity.BlsParameterSet;
import com.bestway.bls.entity.BlsReceiptResult;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;

public class BlsMessageDao extends BaseDao {

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
			parameterSet
					.setHpServiceUrl("http://www3.hpedi.com.cn/HpService/HpService.asmx");
			this.saveOrUpdate(parameterSet);
			return parameterSet;
		}
	}

	/**
	 * 查询保税物流参数参数
	 * 
	 * @param parameter
	 */
	public BlsParameterSet findBlsParameterSet(Company company) {
		List list = this.find(
				"select a from BlsParameterSet a where a.company.id=? ",
				new Object[] { company.getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return (BlsParameterSet) list.get(0);
		} else {
			BlsParameterSet parameterSet = new BlsParameterSet();
			parameterSet.setCompany(company);
			parameterSet
					.setHpServiceUrl("http://www3.hpedi.com.cn/HpService/HpService.asmx");
			this.saveOrUpdate(parameterSet);
			return parameterSet;
		}
	}

	/**
	 * 保税物流参数设置保存
	 * 
	 * @param parameterSet
	 */
	public void saveBlsParameterSet(BlsParameterSet parameterSet) {
		this.saveOrUpdate(parameterSet);
	}

	/**
	 * 保存报文发送信息
	 * 
	 * @param blsMessageSend
	 */
	public void saveBlsMessageSend(BlsMessageSend blsMessageSend) {
		this.saveOrUpdate(blsMessageSend);
	}

	/**
	 * 查询发送报文信息
	 * 
	 * @return
	 */
	public List findBlsMessageSend(String ediType, Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from BlsMessageSend a "
				+ " where a.company.id= ? and a.messageType=? ";
		parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(Integer.valueOf(BcsMessageQuery.SENDTYPE));
		parameters.add(ediType);
		if (beginDate != null) {
			hql += " and (a.sendTime>=?  or a.sendTime is null) ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql += " and (a.sendTime<=?  or a.sendTime is null) ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hql += " order by a.sendTime";
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 查询发送报文信息
	 * 
	 * @return
	 */
	public BlsMessageSend findLastBlsMessageSend(String ediType, String keyCode) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from BlsMessageSend a "
				+ " where a.company.id= ? and a.messageType=? "
				+ " and a.keyCode=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(ediType);
		parameters.add(keyCode);
		hql += " order by a.sendTime desc ";
		List list = this.find(hql, parameters.toArray());
		if (list.size() > 0) {
			return (BlsMessageSend) list.get(0);
		}
		return null;
	}

	/**
	 * 保存回执处理结果
	 * 
	 * @param blsMessageSend
	 */
	public void saveBlsReceiptResult(BlsReceiptResult blsReceiptResult) {
		this.saveOrUpdate(blsReceiptResult);
	}

	/**
	 * 查询回执信息
	 * 
	 * @return
	 */
	public List findBlsReceiptResult(String ediType, Date beginDate,
			Date endDate, String keyCode) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from BlsReceiptResult a"
				+ " where a.company.id=? and a.messageType=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(ediType);
		if (beginDate != null) {
			hql += " and a.noticeDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql += " and a.noticeDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (keyCode != null && !"".equals(keyCode.trim())) {
			hql += " and a.keyCode=? ";
			parameters.add(keyCode);
		}
		hql += " order by a.noticeDate ";
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 查询最近一次未完成的回执信息，向海关查询最终结果而用
	 * 
	 * @return
	 */
	public BlsReceiptResult findBlsLastNotCompleteReceiptResult(
			Company company,String messageType, String relateID) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from BlsReceiptResult a"
				+ " where a.company.id=? and a.messageType=? ";
		parameters.add(company.getId());
		parameters.add(messageType);
		if (relateID != null && !"".equals(relateID.trim())) {
			hql += " and a.relateID=? ";
			parameters.add(relateID);
		}
		// if (keyCode != null && !"".equals(keyCode.trim())) {
		// hql += " and a.keyCode=? ";
		// parameters.add(keyCode);
		// }
		hql += " order by a.noticeDate desc";
		List list = this.find(hql, parameters.toArray());
		if (list.size() > 0) {
			return (BlsReceiptResult) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 查询自动申报或回执处理记录
	 * 
	 * @param company
	 * @param serviceType
	 * @param inOut
	 * @param billCode
	 * @return
	 */
	public List findBlsAutoDeclareProcessInfo(Company company,
			String serviceType, String inOut, String billCode, Date beginDate,
			Date endDate) {// Boolean isAutoDeclare,
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from BlsAutoDeclareProcessInfo a "
				+ " where a.company.id= ?";
		parameters.add(company.getId());
		if (serviceType != null && !"".equals(serviceType.trim())) {
			hql += " and a.serviceType=? ";
			parameters.add(serviceType);
		}
		if (inOut != null && !"".equals(inOut.trim())) {
			hql += " and a.inOut=? ";
			parameters.add(inOut);
		}
		if (billCode != null && !"".equals(billCode.trim())) {
			hql += " and a.billCode=? ";
			parameters.add(billCode);
		}
		// if (isAutoDeclare != null) {
		// hql += " and a.isAutoDeclare=? ";
		// parameters.add(isAutoDeclare);
		// }
		if (beginDate != null) {
			hql += " and a.declareProcessDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql += " and a.declareProcessDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hql += " order by a.declareProcessDate";
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 删除自动申报或回执处理记录
	 * 
	 * @param company
	 * @param serviceType
	 * @param inOut
	 * @param billCode
	 * @return
	 */
	public void deleteBlsAutoDeclareProcessInfo(Company company,
			Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "delete from BlsAutoDeclareProcessInfo "
				+ " where company= ?";
		parameters.add(company);
		if (beginDate != null) {
			hql += " and declareProcessDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql += " and declareProcessDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		this.batchUpdateOrDelete(hql, parameters.toArray());
	}

	/**
	 * 删除自动申报或回执处理记录
	 * 
	 * @param company
	 * @param serviceType
	 * @param inOut
	 * @param billCode
	 * @return
	 */
	public void deleteBlsAutoDeclareProcessInfo(List list) {
		this.deleteAll(list);
	}

	/**
	 * 查询自动申报退单记录
	 * 
	 * @param company
	 * @param serviceType
	 * @param inOut
	 * @param billCode
	 * @return
	 */
	public List findBlsAutoBackBillInfo(Company company, String serviceType,
			String inOut, String billCode) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from BlsAutoBackBillInfo a "
				+ " where a.company.id= ?";
		parameters.add(company.getId());
		if (serviceType != null && !"".equals(serviceType.trim())) {
			hql += " and a.serviceType=? ";
			parameters.add(serviceType);
		}
		if (inOut != null && !"".equals(inOut.trim())) {
			hql += " and a.inOut=? ";
			parameters.add(inOut);
		}
		if (billCode != null && !"".equals(billCode.trim())) {
			hql += " and a.billCode=? ";
			parameters.add(billCode);
		}
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 删除自动申报退单记录
	 * 
	 * @param blsAutoBackBillInfo
	 */
	public void deleteBlsAutoBackBillInfo(
			BlsAutoBackBillInfo blsAutoBackBillInfo) {
		this.delete(blsAutoBackBillInfo);
	}

	/**
	 * 查询自动申报退单记录
	 * 
	 * @param company
	 * @param serviceType
	 * @param inOut
	 * @param billCode
	 * @return
	 */
	public BlsAutoBackBillInfo findBlsAutoBackBillInfoByRelateId(
			Company company, String relateId) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from BlsAutoBackBillInfo a "
				+ " where a.company.id= ? and a.relateId=? ";
		parameters.add(company.getId());
		parameters.add(relateId);
		List list = this.find(hql, parameters.toArray());
		if (list.size() > 0) {
			return (BlsAutoBackBillInfo) list.get(0);
		}
		return null;
	}
}
