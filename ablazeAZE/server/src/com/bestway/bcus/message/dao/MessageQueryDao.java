package com.bestway.bcus.message.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.message.entity.CustomReceiptMessage;
import com.bestway.bcus.message.entity.CustomSendMessage;
import com.bestway.bcus.message.entity.MessageQuery;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;

/**
 * @author 
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class MessageQueryDao extends BaseDao{
	
	/**
	 * 查询系统参数设置
	 * @return
	 */
	public CompanyOther findCompanyOther(){
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from CompanyOther a where a.company.id= ? ";
		parameters.add(CommonUtils.getCompany().getId());
		List<CompanyOther> list = this.find(hql,parameters.toArray());
		return (CompanyOther)list.get(0);
	}
	
	/**
	 * 显示发送信息
	 * @return
	 */
	public List findMessageSend(int ediType,Date beginDate,Date endDate) {
		String hql = "";
		List<Object> parameters = new ArrayList<Object>();
		hql = "from MessageQuery a where a.company.id= ? and a.sendRecvType=? and a.ediType=? ";//order by a.sendRecvTime
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(Integer.valueOf(MessageQuery.SENDTYPE));
		parameters.add(Integer.valueOf(ediType));
		if (beginDate != null) {
			hql += " and (a.sendRecvTime>=?  or a.sendRecvTime is null) ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql += " and (a.sendRecvTime<=?  or a.sendRecvTime is null) ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hql += " order by a.sendRecvTime";
		return this.find(hql, parameters.toArray());
	}
	/**
	 * 显示接收信息
	 * @param ediType
	 * @return
	 */
	public List findMessageRecv(int ediType,Date beginDate,Date endDate) {
		String hql = "";
		List<Object> parameters = new ArrayList<Object>();
		hql = "from MessageQuery a where a.company.id= ? and a.sendRecvType=? and a.ediType=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(Integer.valueOf(MessageQuery.RECVTYPE));
		parameters.add(Integer.valueOf(ediType));
		if (beginDate != null) {
			hql += " and (a.sendRecvTime>=?  or a.sendRecvTime is null) ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql += " and (a.sendRecvTime<=?  or a.sendRecvTime is null) ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hql += " order by a.sendRecvTime";
		return this.find(hql, parameters.toArray());

	}
	
	
	public CustomsDeclaration findCustomsDeclaration(String id){
		List list = this.find(
				"from CustomsDeclaration a where a.id=? and a.company.id=?",
				new Object[] {id,CommonUtils.getCompany().getId()});
		return (CustomsDeclaration) list.get(0);
	}
	/**
	 * 删除收发报文信息
	 * @param messageQuery
	 */
	public void deleteMessage(MessageQuery messageQuery){
		this.delete(messageQuery);
	}
	/**
	 * 删除所有发送信息报文
	 * @param ediType
	 */
	public void deleteAllMessageSend(int ediType){
		this.deleteAll(findMessageSend(ediType,null,null));
	}
	/**
	 * 删除所有接收信息报文
	 * @param ediType
	 */
	public void deleteAllMessageRecv(int ediType){
		this.deleteAll(findMessageRecv(ediType,null,null));
	}
	/**
	 * 保存收发报文信息
	 * @param messageQuery
	 * @throws DataAccessException
	 */
	public void saveMessage(MessageQuery messageQuery) throws DataAccessException {
		this.saveOrUpdate(messageQuery);
	}
	
	/**
	 * 根据根据预录入号查询报文信息
	 * 
	 * @return
	 */
	public MessageQuery getMessageQueryByYlrh(String ylrh,String customs){
		List list = this.find(
				"from MessageQuery a where a.company.id=? and a.sendRecvType=? and a.ylrh=? and a.customs=?",
				new Object[] {CommonUtils.getCompany().getId(),MessageQuery.SENDTYPE,ylrh,customs});
		if(list!=null && list.size()>0){
			return (MessageQuery)list.get(0);
		}
		return null;
	}
	
	/**
	 * 保存报关单发送记录
	 * @param customSendMessage
	 */
	public void saveCustomSendMessage(CustomSendMessage customSendMessage) {
		this.saveOrUpdate(customSendMessage);
	}
	/**
	 * 删除报关单发送记录
	 * @param customSendMessage
	 */
	public void deleteCustomSendMessage(CustomSendMessage customSendMessage) {
		this.delete(customSendMessage);
	}
	
	/**
	 * 查询所有的发送记录
	 * @param projectType
	 * @return
	 */
	public List<CustomSendMessage> findAllCustomSendMessage(Integer projectType, Date beginDate, Date endDate) {
		StringBuilder hql = new StringBuilder("from CustomSendMessage a where a.projectType = ?");
		List<Object> p = new ArrayList<Object>();
		p.add(projectType);
		if(beginDate != null) {
			hql.append(" and a.sendDate >= ?");
			p.add(beginDate);
		}
		if(endDate != null) {
			hql.append(" and a.sendDate <= ?");
			p.add(endDate);
		}
		List<CustomSendMessage> list = find(hql.toString(), p.toArray());
		return list;
	}
	
	
	
	
	/**
	 * 保存报关单回执记录
	 * @param customReceiptMessage
	 */
	public void saveCustomReceiptMessage(CustomReceiptMessage customReceiptMessage) {
		this.saveOrUpdate(customReceiptMessage);
	}
	/**
	 * 删除报关单回执记录
	 * @param customReceiptMessage
	 */
	public void deleteCustomReceiptMessage(CustomReceiptMessage customReceiptMessage) {
		this.delete(customReceiptMessage);
	}
	
	/**
	 * 查询所有的回执记录
	 * @param projectType
	 * @return
	 */
	public List<CustomReceiptMessage> findAllCustomReceiptMessage(Integer projectType, Date beginDate, Date endDate) {
		StringBuilder hql = new StringBuilder("from CustomReceiptMessage a where a.projectType = ?");
		List<Object> p = new ArrayList<Object>();
		p.add(projectType);
		if(beginDate != null) {
			hql.append(" and a.returnDate >= ?");
			p.add(beginDate);
		}
		if(endDate != null) {
			hql.append(" and a.returnDate <= ?");
			p.add(endDate);
		}
		List<CustomReceiptMessage> list = find(hql.toString(), p.toArray());
		return list;
	}
}
