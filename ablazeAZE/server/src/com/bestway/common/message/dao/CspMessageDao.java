package com.bestway.common.message.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.message.entity.CspMessageSend;
import com.bestway.common.message.entity.CspParameterSet;
import com.bestway.common.message.entity.CspReceiptResult;
import com.bestway.common.message.entity.CspReceiptResultDetail;

/**
 * @author // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public abstract class CspMessageDao extends BaseDao {

	public abstract Class getParameterSetClass();

	public abstract Class getMessageSendClass();

	public abstract Class getReceiptResultClass();

	public abstract Class getReceiptResultDetailClass();

	public abstract Class getSignInfoClass();

	/**
	 * 查询出实体{entityName}最大的内部编号
	 * 
	 * @param entityName
	 * @param propertyName
	 * @param prefix
	 * @return
	 */
	public String findContractMaxCopEntNo(String entityName,
			String propertyName, String prefix) {
		List<Object> parameters = new ArrayList<Object>();
		String maxCopEntNo = "";
		// String hql = "select max(a." + propertyName + ") from " + entityName
		// + " a " + " where a.company.id= ? and a." + propertyName
		// + " like ? ";
		String hql = "select max(a." + propertyName + ") from " + entityName
				+ " a " + "where a.company.id = ? " + " and a." + propertyName
				+ " like ?" + "  and length(a." + propertyName
				+ ") = (select max(length(b." + propertyName + ")) from "
				+ entityName + " b "
				+ "where b.company.id = b.company.id and b." + propertyName
				+ " like ? )";
		// SELECT MAX(a.copEmsNo)
		// from Contract a
		// where a.company.id= '2c9ba4892ea3b054012ea3b0a1710001'
		// and LEN(a.copEmsNo) = (
		// SELECT MAX(LEN(b.copEmsNo)) from Contract b where
		// b.company.id=b.company.id and b.copEmsNo like '4469940016C2%')
		parameters.add(CommonUtils.getCompany().getId());

		parameters.add(prefix + "%");

		parameters.add(prefix + "%");

		System.out.println("hql======" + hql);
		List list = this.find(hql, parameters.toArray());

		if (list.size() > 0 && list.get(0) != null) {
			maxCopEntNo = list.get(0).toString().trim();

		}
		return maxCopEntNo;
	}

	/**
	 * 查询出实体{entityName}最大的内部编号
	 * 
	 * @param entityName
	 * @param propertyName
	 * @param prefix
	 * @return
	 */
	public String findMaxCopEntNo(String entityName, String propertyName,
			String prefix) {
		List<Object> parameters = new ArrayList<Object>();
		String maxCopEntNo = "";
		String hql = "select max(a." + propertyName + ") from " + entityName
				+ " a " + " where a.company.id= ? and a." + propertyName
				+ " like ? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(prefix + "%");
		List list = this.find(hql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			maxCopEntNo = list.get(0).toString().trim();
		}
		return maxCopEntNo;
	}

	/**
	 * 获取企业内部编号
	 */
	public List findAllCopEntNo(String entityName, String propertyName,
			String copTrNo) {
		List<Object> parameters = new ArrayList<Object>();

		String hql = "select a." + propertyName + " from " + entityName + " a "
				+ " where a.company.id= ? and a." + propertyName + " = ? ";

		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(copTrNo);
		List list = this.find(hql, parameters.toArray());
		System.out.println("hql=" + hql);
		return list;
	}

	/**
	 * 获取className里最大的流水号
	 * 
	 * @param className
	 *            表名
	 * @param seqNum
	 *            流水号
	 * @return Sting className里最大的流水号
	 */
	public String getNum(String className, String seqNum) {
		String num = "1";
		List list = this.find("select max(a." + seqNum + ") from " + className
				+ " as a " + "where a.company= ?",
				new Object[] { CommonUtils.getCompany() });
		if (list.get(0) != null) {
			num = list.get(0).toString();
			num = String.valueOf(Integer.parseInt(num) + 1);
		}
		return num;
	}

	/**
	 * 查询报文收发存放路径设定
	 * 
	 * @return
	 */
	public CspParameterSet findCspParameterSet() {
		List<Object> parameters = new ArrayList<Object>();
		Class parameterSetClass = this.getParameterSetClass();
		if (parameterSetClass == null) {
			return null;
		}
		System.out.println("class name:" + parameterSetClass.getName());
		CspParameterSet dirSet = null;
		String hql = "select a from " + parameterSetClass.getName() + " a "
				+ " where a.company.id= ?";
		System.out.println("hql :" + hql);
		parameters.add(CommonUtils.getCompany().getId());
		List list = this.find(hql, parameters.toArray());
		if (list.size() > 0) {
			dirSet = (CspParameterSet) list.get(0);
		} else {
			try {
				dirSet = (CspParameterSet) parameterSetClass.newInstance();
				this.saveOrUpdate(dirSet);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dirSet;
	}

	public CompanyOther findCompanyOther() {
		List list = this.find(
				"select a from CompanyOther a where a.company= ?",
				new Object[] { CommonUtils.getCompany() });
		return (CompanyOther) list.get(0);
	}

	/**
	 * 保存 报文收发存放路径设定
	 * 
	 * @param dirSet
	 */
	public void saveCspMessageDirSet(CspParameterSet dirSet) {
		this.saveOrUpdate(dirSet);
	}

	/**
	 * 查询发送报文信息
	 * 
	 * @return
	 */
	public List findCspMessageSend(String ediType, Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		Class messageSendClass = this.getMessageSendClass();
		if (messageSendClass == null) {
			return new ArrayList();
		}
		String hql = "select a from " + messageSendClass.getName() + " a "
				+ " where a.company.id= ? and a.sysType=? ";
		parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(Integer.valueOf(BcsMessageQuery.SENDTYPE));
		parameters.add(ediType);
		if (beginDate != null) {
			hql += " and (a.sendRecvTime>=?  or a.sendRecvTime is null) ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql += " and (a.sendRecvTime<=?  or a.sendRecvTime is null) ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hql += " order by a.sendRecvTime DESC"; // HH 2013.11.21 添加 DESC 排序
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 查询发送报文信息
	 * 
	 * @return
	 */
	public CspMessageSend findLastCspMessageSend(String ediType, String copEmsNo) {
		List<Object> parameters = new ArrayList<Object>();
		Class messageSendClass = this.getMessageSendClass();
		if (messageSendClass == null) {
			return null;
		}
		String hql = "select a from " + messageSendClass.getName() + " a "
				+ " where a.company.id= ? and a.sysType=? "
				+ " and a.copEmsNo=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(ediType);
		parameters.add(copEmsNo);
		hql += " order by a.sendRecvTime desc ";
		List list = this.find(hql, parameters.toArray());
		if (list.size() > 0) {
			return (CspMessageSend) list.get(0);
		}
		return null;
	}

	public void saveCspMessageSend(CspMessageSend messageQuery) {
		this.saveOrUpdate(messageQuery);
	}

	public void deleteCspMessageQuery(CspMessageSend messageQuery) {
		this.delete(messageQuery);
	}

	// /**
	// * 显示接收信息
	// *
	// * @param ediType
	// * @return
	// */
	// public List findMessageRecv(int ediType, Date beginDate, Date endDate) {
	// String hql = "";
	// List<Object> parameters = new ArrayList<Object>();
	// hql = "from BcsMessageQuery a where a.company.id= ? and a.sendRecvType=?
	// and a.ediType=? ";
	// parameters.add(CommonUtils.getCompany().getId());
	// parameters.add(Integer.valueOf(BcsMessageQuery.RECVTYPE));
	// parameters.add(Integer.valueOf(ediType));
	// if (beginDate != null) {
	// hql += " and (a.sendRecvTime>=? or a.sendRecvTime is null) ";
	// parameters.add(beginDate);
	// }
	// if (endDate != null) {
	// hql += " and (a.sendRecvTime<=? or a.sendRecvTime is null) ";
	// parameters.add(endDate);
	// }
	// hql += " order by a.sendRecvTime";
	// return this.find(hql, parameters.toArray());
	//
	// }

	/**
	 * 删除收发报文信息
	 * 
	 * @param messageQuery
	 */
	public void deleteMessage(CspMessageSend messageQuery) {
		this.delete(messageQuery);
	}

	// /**
	// * 删除所有发送信息报文
	// *
	// * @param ediType
	// */
	// public void deleteAllMessageSend(int ediType) {
	// this.deleteAll(findMessageSend(ediType, null, null));
	// }
	//
	// /**
	// * 删除所有接收信息报文
	// *
	// * @param ediType
	// */
	// public void deleteAllMessageRecv(int ediType) {
	// this.deleteAll(findMessageRecv(ediType, null, null));
	// }

	/**
	 * 保存收发报文信息
	 * 
	 * @param messageQuery
	 * @throws DataAccessException
	 */
	public void saveMessage(CspMessageSend messageQuery) {
		this.saveOrUpdate(messageQuery);
	}

	public List commonSearch(String className, Hashtable conditions) {
		String sql = "from " + className + " a";
		Set keySet = conditions.keySet();

		Object[] params = new Object[keySet.size()];

		if (keySet.size() > 0) {
			for (int i = 0; i < keySet.size(); i++) {
				if (i == 0) {
					sql += " where a." + (String) keySet.toArray()[i] + "=?";
				} else {
					sql += " and a." + (String) keySet.toArray()[i] + "=?";
				}
				params[i] = conditions.get(keySet.toArray()[i]);
			}
		}
		List result = this.find(sql, params);

		return result;
	}

	public void commonSaveObject(Object obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 查询回执信息
	 * 
	 * @return
	 */
	public List findCspReceiptResult(String ediType, Date beginDate,
			Date endDate, String sendFileName) {
		List<Object> parameters = new ArrayList<Object>();
		Class receiptResultClass = this.getReceiptResultClass();
		if (receiptResultClass == null) {
			return null;
		}
		String hql = "select a from " + receiptResultClass.getName() + " a"
				+ " where a.company.id=? and a.applType=? ";
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
		if (sendFileName != null && !"".equals(sendFileName.trim())) {
			hql += " and a.sendFileName=? ";
			parameters.add(sendFileName);
		}
		hql += " order by a.noticeDate ";
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 查询回执明细信息
	 * 
	 * @param cspReceiptResult
	 * @return
	 */
	public List findCspReceiptResultDetail(CspReceiptResult cspReceiptResult) {
		if (cspReceiptResult == null) {
			return new ArrayList();
		}
		Class receiptResultDetailClass = this.getReceiptResultDetailClass();
		if (receiptResultDetailClass == null) {
			return new ArrayList();
		}
		return this.find("select a from " + receiptResultDetailClass.getName()
				+ " a" + " where a.company.id=? and a.cspReceiptResult.id=? ",
				new Object[] { CommonUtils.getCompany().getId(),
						cspReceiptResult.getId() });
	}

	public void saveReceiptResult(CspReceiptResult receiptResult) {
		this.saveOrUpdate(receiptResult);
	}

	public void saveReceiptResultDetail(
			CspReceiptResultDetail receiptResultDetail) {
		this.saveOrUpdate(receiptResultDetail);
	}
}
