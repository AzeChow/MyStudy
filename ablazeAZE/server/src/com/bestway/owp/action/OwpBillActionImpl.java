package com.bestway.owp.action;

import java.util.List;

import antlr.CommonAST;

import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.jptds.client.common.CommonVars;
import com.bestway.owp.dao.OwpBillDao;
import com.bestway.owp.entity.OwpAppHead;
import com.bestway.owp.entity.OwpBillRecvHead;
import com.bestway.owp.entity.OwpBillRecvItem;
import com.bestway.owp.entity.OwpBillSendHead;
import com.bestway.owp.entity.OwpBillSendItem;
import com.bestway.owp.logic.OwpBillLogic;

/**
 * 外发加工登记表接口实现
 * @author sxk
 *
 */
public class OwpBillActionImpl implements OwpBillAction {

	//数据接口层对象
	private OwpBillDao owpBillDao;
	
	//逻辑层对象
	private OwpBillLogic OwpBillLogic;

	public OwpBillDao getOwpBillDao() {
		return owpBillDao;
	}

	public void setOwpBillDao(OwpBillDao owpBillDao) {
		this.owpBillDao = owpBillDao;
	}

	public OwpBillLogic getOwpBillLogic() {
		return OwpBillLogic;
	}

	public void setOwpBillLogic(OwpBillLogic owpBillLogic) {
		OwpBillLogic = owpBillLogic;
	}
	/**
	 * 外发加工申请单报文生成
	 * @param head
	 * @return DeclareFileInfo
	 * @author sxk
	 */
	public DeclareFileInfo declareOwpBillSendHead(Request request, OwpBillSendHead head,
			String taskId) {
		return this.OwpBillLogic.declareOwpBillSendHead(head, taskId);
//		return null;
	}
	/**
	 * 外发加工申请单报文生成
	 * @param head
	 * @return DeclareFileInfo
	 * @author sxk
	 */
	public DeclareFileInfo declareOwpBillRecvHead(Request request, OwpBillRecvHead head,
			String taskId) {
		return this.OwpBillLogic.declareOwpBillRecvHead(head, taskId);
//		return null;
	}
	/**
	 * 外发加工申请单报文生成
	 * @param head
	 * @return DeclareFileInfo
	 * @author sxk
	 */
	public DeclareFileInfo declareOwpBillCancelHead(Request request, OwpBillSendHead head,
			String taskId) {
		return this.OwpBillLogic.declareOwpBillCancelHead(head, taskId);
//		return null;
	}
	
	/**
	 * 根据id查询出相应的外发登记表表头
	 * @param id
	 * @return
	 * @author sxk
	 */
	public OwpBillSendHead findOwpBillSendHeadById(Request request,String id){
		return this.owpBillDao.findOwpBillSendHeadById(id);
	}
	/**
	 * 根据id查询出相应的外发登记表表头
	 * @param id
	 * @return
	 * @author sxk
	 */
	public OwpBillRecvHead findOwpBillRecvHeadById(Request request,String id){
		return this.owpBillDao.findOwpBillRecvHeadById(id);
	}
	/**
	 * 按发货日期循序asc查找出该申请表下的所有出货登记表
	 * @param request
	 * @param id
	 * @return
	 * @author sxk
	 */
	public List findOwpBillSendHead(Request request,String appNo){
		return this.owpBillDao.findOwpBillSendHead(appNo);
	}
	
	/**
	 * 按发货日期循序asc查找出该申请表下的所有收货登记表
	 * @param request
	 * @param id
	 * @return
	 * @author sxk
	 */
	public List findOwpBillRecvHead(Request request,String appNo){
		return this.owpBillDao.findOwpBillRecvHead(appNo);
	}
	/**
	 * 保存外发登记表表头
	 * @param head
	 * @return
	 * @author sxk
	 */
	public OwpBillSendHead saveOwpSendBillHead(Request request, OwpBillSendHead head){
		head.setCreater(CommonUtils.getAclUser().getUserName());
		this.owpBillDao.saveOrUpdate(head);
		return head;
	}
	/**
	 * 保存外发收货登记表表头
	 * @param head
	 * @return
	 * @author sxk
	 */
	public OwpBillRecvHead saveOwpBillRecvHead(Request request, OwpBillRecvHead head){
		head.setCreater(CommonUtils.getAclUser().getUserName());
		this.owpBillDao.saveOrUpdate(head);
		return head;
	}
	/**
	 * 保存外发登记表表体
	 * @param head
	 * @return
	 * @author sxk
	 */
	public OwpBillSendItem saveOwpSendBillItem(Request request, OwpBillSendItem head){
		this.owpBillDao.saveOrUpdate(head);
		return head;
	}
	/**
	 * 保存外发回收登记表表体
	 * @param head
	 * @return
	 * @author sxk
	 */
	public OwpBillRecvItem saveOwpBillRecvItem(Request request, OwpBillRecvItem head){
		this.owpBillDao.saveOrUpdate(head);
		return head;
	}
	/**
	 * 查询相应登记表下的 外发货物
	 * @return list
	 * @author sxk
	 */
	public List findOwpSendBillItemByHead(Request request,OwpBillSendHead head){
		return owpBillDao.findOwpSendBillItemByHead(head);
	}
	/**
	 * 查询相应登记表下的 外发货物
	 * @return list
	 * @author sxk
	 */
	public List findOwpRecvBillItemByHead(Request request,OwpBillRecvHead head){
		return owpBillDao.findOwpBillRecvItemByHead(head);
	}
	/**
	 *  删除外发加工登记表表头
	 * @param head
	 * @author sxk
	 */
	public void deleteOwpSendBillHead(Request request, OwpBillSendHead head) {
		this.OwpBillLogic.deleteOwpSendBillHead(head);
	}
	/**
	 * 删除外发加工收货登记表表头
	 */
	public void deleteOwpRecvBillHead(Request request,OwpBillRecvHead head){
		this.OwpBillLogic.deleteOwpRecvBillHead(head);
	}
	/**
	 * 删除外发加工收货登记表表体
	 */
	public void deleteOwpBillRecvItem(Request request,OwpBillRecvItem item){
		List list = this.owpBillDao.findOwpRecvBillAndBillDetailByOwpBillSendItem(item.getId());
		this.owpBillDao.deleteAll(list);
		this.owpBillDao.delete(item);
	}
	/**
	 *  删除外发加工登记表表体
	 * @param head
	 * @author sxk
	 */
	public void deleteOwpSendBillItem(Request request, OwpBillSendItem item) {
		List list = this.owpBillDao.findOwpBillAndBillDetailByOwpBillSendItem(item.getId());
		this.owpBillDao.deleteAll(list);
		this.owpBillDao.delete(item);
		
	}
	/**
	 * 查找外发加工出货登记表头
	 * @param request
	 * @param 申请表编号
	 * @return 
	 * @author sxk
	 */
	public List findOwpSendBillHeadInfo(Request request,String appNo){
		return this.owpBillDao.findOwpSendBillHeadInfo(appNo);
	}
	/**
	 * 查找外发加工进货登记表头
	 * @param request
	 * @param 申请表编号
	 * @return 
	 * @author sxk
	 */
	public List findOwpRecvBillHeadInfo(Request request,String appNo){
		return this.owpBillDao.findOwpRecvBillHeadInfo(appNo);
	}
	/**
	 * 查询外发加工申请表头
	 * @param request
	 * @return
	 * @author sxk
	 */
	public List findOwpAppHead(Request request) {
		return this.owpBillDao.findOwpAppHead();
	}
	/**
	 * 查询外发加工申请表进出货物
	 * @param appNo 外发加工申请表标号
	 * @param billHeadId 外发加工登记表id
	 * @param isout 是否收发货
	 * @return
	 * @author sxk
	 */
	public List findOwpSendItemByHead(Request request,String appNo,String billHeadId,Boolean isout){
//		return this.owpBillDao.findOwpSendItemByHead(head);
		return this.OwpBillLogic.findOwpSendItemByHead(appNo,billHeadId,isout);
	}
	/**
	
	 * @author sxk
	 */
	public List findBillDetailSendItem(Request request,String appNo,String billHeadId,Boolean isout){
//		return this.owpBillDao.findOwpSendItemByHead(head);
		return this.OwpBillLogic.findBillDetailSendItem(appNo,billHeadId,isout);
	}
	/**
	 * 来源于申请表的登记表体资料进行保存
	 * @param request
	 * @param owpBillSendHead 外发加工出货登记表头
	 * @param list 申请表已选择商品LIST
	 * @return
	 * @author sxk
	 */
	public List addOwpBillSendItem(Request request,OwpBillSendHead owpBillSendHead,List list){
		return this.OwpBillLogic.addOwpBillSendItem(owpBillSendHead,list);
	}
	/**
	 * 来源于申请表的登记表体资料进行保存
	 * @param request
	 * @param owpBillSendHead 外发加工进货登记表头
	 * @param list 申请表已选择商品LIST
	 * @return
	 * @author sxk
	 */
	public List addOwpBillRecvItem(Request request,OwpBillRecvHead owpBillRecvHead,List list){
		return this.OwpBillLogic.addOwpBillRecvItem(owpBillRecvHead,list);
	}
	/**
	 * 来源于单据中心的登记表体资料进行保存
	 * @param request
	 * @param owpBillSendHead
	 * @param list
	 * @return
	 * @author sxk
	 */
	public List addBillDetailSendItem(Request request,OwpBillSendHead owpBillSendHead,List list){
		return this.OwpBillLogic.addBillDetailSendItem(owpBillSendHead,list);
	}
	/**
	 * 来源于单据中心的进货登记表体资料进行保存
	 * @param request
	 * @param owpBillSendHead
	 * @param list
	 * @return
	 * @author sxk
	 */
	public List addBillDetailRecvItem(Request request,OwpBillRecvHead owpBillRecvHead,List list){
		return this.OwpBillLogic.addBillDetailRecvItem(owpBillRecvHead,list);
	}
	/**
	 * 查询申请表外发料件申报数量
	 * @param request
	 * @param appNo 外发加工申请表编号
	 * @param seqNum 外发加工申请表商品序号
	 * @return
	 * @author sxk
	 */
	public List findOwpAppSendItemQty(Request request,String appNo,Integer seqNum){
		return this.owpBillDao.findOwpAppSendItemQty(appNo,seqNum);
	}
	/**
	 * 查询申请表进货料件申报数量
	 * @param request
	 * @param appNo 外发加工申请表编号
	 * @param seqNum 外发加工申请表商品序号
	 * @return
	 * @author sxk
	 */
	public List findOwpAppRecvItemQty(Request request,String appNo,Integer seqNum){
		return this.owpBillDao.findOwpAppRecvItemQty(appNo,seqNum);
	}
	/**
	 * 查询登记表外发料件
	 * @param request
	 * @param appNo 外发加工申请表编号
	 * @param seqNum 外发加工申请表商品序号
	 * @return
	 * @author sxk
	 */
	public List findOwpBillSendItemBySeqNum(Request request,String appNo,Integer seqNum){
		return this.owpBillDao.findOwpBillSendItemBySeqNum(appNo,seqNum);
	}
	/**
	 * 查询登记表收回料件
	 * @param request
	 * @param appNo 外发加工申请表编号
	 * @param seqNum 外发加工申请表商品序号
	 * @return
	 * @author sxk
	 */
	public List findOwpBillRecvItemBySeqNum(Request request,String appNo,Integer seqNum){
		return this.owpBillDao.findOwpBillRecvItemBySeqNum(appNo,seqNum);
	}
	/**
	 * 查找 owpBillSendHead 来自 copAppNo
	 * @author sxk
	 */
    public OwpBillSendHead findOwpBillSendHeadByCopBillNo(Request request,String copBillNo){
    	return this.owpBillDao.findOwpBillSendHeadByCopBillNo(copBillNo);
    }
    /**
	 * 查找 owpBillRecvHead 来自 copAppNo
	 * @author sxk
	 */
    public OwpBillRecvHead findOwpBillRecvHeadByCopBillNo(Request request,String copBillNo){
    	return this.owpBillDao.findOwpBillRecvHeadByCopBillNo(copBillNo);
    }
    /**
	 * 外发加工申请单回执处理
	 * 
	 * @param owpAppHead
	 * @param existOwpAppHead
	 * @return
	 * @author sxk
	 */
    public String processOwpBillSendHead(Request request, OwpBillSendHead owpBillSendHead,
			OwpBillSendHead existOwpBillSendHead, List lsReturnFile){
		return OwpBillLogic.processOwpBillSendHead(owpBillSendHead,existOwpBillSendHead, lsReturnFile);
	}
    /**
	 * 外发加工申请单回执处理
	 * 
	 * @param owpAppHead
	 * @param existOwpAppHead
	 * @return
	 * @author sxk
	 */
    public String processOwpBillRecvHead(Request request, OwpBillRecvHead owpBillRecvHead,
    		OwpBillRecvHead existOwpBillRecvHead, List lsReturnFile){
		return OwpBillLogic.processOwpBillRecvHead(owpBillRecvHead,existOwpBillRecvHead, lsReturnFile);
	}
    /**
	 * 依组合条件查询外发加工出货登记表表头
	 * @param request
	 * @param list
	 * @return
	 * @author sxk
	 */
	public List findOwpBillSendHeadByConditions(Request request,List conditions){
		return OwpBillLogic.findOwpBillSendHeadByConditions(conditions);
	}

	/**
	 * 依组合条件查询外发加工收获登记表表头
	 * @param request
	 * @param list
	 * @return
	 * @author sxk
	 */
	public List findOwpBillRecvHeadByConditions(Request request,List conditions){
		return OwpBillLogic.findOwpBillRecvHeadByConditions(conditions);
	}
	/**
	 * 查询外发加工出货登记表表头最大流水号
	 * @param request
	 * @param appNo
	 * @return
	 */
	public Integer findBillSendHeadMaxNo(Request request,String appNo){
		return owpBillDao.findBillSendHeadMaxNo(appNo);
	}
	/**
	 * 查询外发加工收货登记表表头最大流水号
	 * @param request
	 * @param appNo
	 * @return
	 */
	public Integer findBillRecvHeadMaxNo(Request request,String appNo){
		return owpBillDao.findBillRecvHeadMaxNo(appNo);
	}
}
