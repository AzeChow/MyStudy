package com.bestway.owp.action;

import java.util.List;

import com.bestway.common.Condition;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.owp.dao.OwpAppDao;
import com.bestway.owp.entity.OwpAppHead;
import com.bestway.owp.entity.OwpAppRecvItem;
import com.bestway.owp.entity.OwpAppSendItem;
import com.bestway.owp.logic.OwpAppLogic;

/**
 * 外发加工申请表action实现
 * @author wss
 */

public class OwpAppActionImpl implements
		OwpAppAction {
	
	
	private OwpAppDao owpAppDao;
	
	private OwpAppLogic owpAppLogic;
	
	
	public OwpAppDao getOwpAppDao() {
		return owpAppDao;
	}

	public void setOwpAppDao(OwpAppDao owpAppDao) {
		this.owpAppDao = owpAppDao;
	}

	public OwpAppLogic getOwpAppLogic() {
		return owpAppLogic;
	}

	public void setOwpAppLogic(OwpAppLogic owpAppLogic) {
		this.owpAppLogic = owpAppLogic;
	}


	
	/**
	 * 查询外发加工申请表头
	 * @param request
	 * @return
	 * @author wss
	 */
	public List findOwpAppHead(Request request) {
		return this.owpAppDao.findOwpAppHead();
	}
	
	/**
	 * 根据id查询出相应的外发申请表表头
	 * @param request
	 * @param id
	 * @return
	 * @author wss
	 */
	public OwpAppHead findOwpAppHeadById(Request request, String id){
		return this.owpAppDao.findOwpAppHeadById(id);
	}
	
	/**
	 * 新增外发加工申请表表头
	 * @return OwpAppHead 外发加工申请表表头
	 * @author wss
	 */
	public OwpAppHead addOwpAppHead(Request request) {
		return this.owpAppLogic.addOwpAppHead();
	}
	
	/**
	 * 删除外发加工申请表表头
	 * @param head
	 * @author wss
	 */
	public void deleteOwpAppHead(Request request, OwpAppHead head) {
		this.owpAppLogic.deleteOwpAppHead(head);
	}
	
	/**
	 * 获取外发加工申请表的一些信息 包括申请表编号、承揽方企业代码、承揽方企业名称
	 * @param request
	 * @return
	 * @author wss
	 */
	public List findOwpAppHeadInfos(Request request){
		return owpAppLogic.findOwpAppHeadInfos();
	}

	/**
	 * 依组合条件查询外发加工申请表表头
	 * @param request
	 * @param list
	 * @return
	 * @author wss
	 */
	public List<OwpAppHead> findOwpAppHeadByConditions(Request request,List<Condition> conditions){
		return owpAppLogic.findOwpAppHeadByConditions(conditions);
	}
	
	/**
	 * 查询相应申请表下的 外发货物
	 * @return list
	 * @author wss
	 */
	public List<OwpAppSendItem> findOwpAppSendItemByHeadId(Request request, String headId){
		return owpAppDao.findOwpAppSendItemByHeadId(headId);
	}
	
	/**
	 * 查询相应申请表下的 收回货物
	 * @return list
	 * @author wss
	 */
	public List<OwpAppRecvItem> findOwpAppRecvItemByHeadId(Request request, String headId){
		return owpAppDao.findOwpAppRecvItemByHeadId(headId);
	}
	
	/**
	 * 删除外发加工申请表 外发货物
	 * @author wss
	 */
	public List<OwpAppSendItem> deleteOwpAppSendItems(Request request, List<OwpAppSendItem> ls){
		owpAppDao.deleteAll(ls);
		return ls;
	}
	
	/**
	 * 删除外发加工申请表 收回货物
	 * @author wss
	 */
	public List<OwpAppRecvItem> deleteOwpAppRecvItems(Request request, List<OwpAppRecvItem> ls){
		owpAppDao.deleteAll(ls);
		return ls;
	}
	
	/**
	 * 获取外发加工 外发货物 最大序号
	 */
	public Integer getMaxSendListNo(Request request,OwpAppHead head){
		return this.owpAppLogic.getMaxSendListNo(head);
//		return Integer.valueOf(owpAppDao.getNum("OwpSendItem","listNo"));
	}

	/**
	 * 获取外发加工 收回货物 最大序号
	 */
	public Integer getMaxRecvListNo(Request request,OwpAppHead head){
		return this.owpAppLogic.getMaxRecvListNo(head);
//		return Integer.valueOf(owpAppDao.getNum("OwpRecvItem","listNo"));
	}
	
	/**
	 * 保存外发加工 外发货物
	 * @author wss
	 */
	public OwpAppSendItem saveOwpAppSendItem(Request request,OwpAppSendItem owpSendItem){
		this.owpAppDao.saveOrUpdate(owpSendItem);
		return owpSendItem;
	}
	
	/**
	 * 保存外发加工 收回货物
	 * @author wss
	 */
	public OwpAppRecvItem saveOwpAppRecvItem(Request request,OwpAppRecvItem owpRecvItem){
		this.owpAppDao.saveOrUpdate(owpRecvItem);
		return owpRecvItem;
	}
	
	/**
	 * 保存外发申请表表头
	 * @param head
	 * @return
	 * @author wss
	 */
	public OwpAppHead saveOwpAppHead(Request request, OwpAppHead head){
		this.owpAppDao.saveOrUpdate(head);
		return head;
	}
	
	/**
	 * 获取外发加工申请表表头信息
	 * 其中noNullField字段不能为空
	 * @author wss
	 */
	public List<OwpAppHead> findOwpAppHeadInfo(Request request,String noNullField){
		return this.owpAppDao.findOwpAppHeadInfo(noNullField);
	}

	
	/**
	 * 导入外发加工申请表 外发货物
	 * @param request
	 * @param ls
	 * @param isOverWrite
	 * @author wss
	 */
	public List importOwpAppSendItemFromTxtFile(Request request, List ls, boolean isOverWrite){
		return this.owpAppLogic.importOwpAppSendItemFromTxtFile(ls, isOverWrite);
	}
	
	
	/**
	 * 导入外发加工申请表 收回货物
	 * @param request
	 * @param ls
	 * @param isOverWrite
	 * @author wss
	 */
	public List importOwpAppRecvItemFromTxtFile(Request request, List ls, boolean isOverWrite){
		return this.owpAppLogic.importOwpAppRecvItemFromTxtFile(ls, isOverWrite);
	}
	
	/**
	 * 查找通关手册备案中的进出口货物信息
	 * @param request
	 * @param headId 外发加工申请表表头id
	 * @param emsNo 手册号
	 * @param isOut是否是料件
	 * @return
	 * @author wss
	 */
	public List findBcsContractDetailByProcessExe(Request request,String headId,String emsNo,Boolean isOut){
		return this.owpAppLogic.findBcsContractDetailByProcessExe(headId,emsNo,isOut);
	}
	
	/**
	 * 查找通关手册备案中的进出口货物信息
	 * @param request
	 * @param headId 外发加工申请表表头id
	 * @param emsNo 手册号
	 * @param isOut是否是料件
	 * @return
	 * @author wss
	 */
	public List findBcusContractDetailByProcessExe(Request request,String headId,String emsNo,Boolean isOut){
		return this.owpAppLogic.findBcusContractDetailByProcessExe(headId,emsNo,isOut);
	}
	
	/**
	 * 查找通关手册备案中的进出口货物信息
	 * @param request
	 * @param headId 外发加工申请表表头id
	 * @param emsNo 手册号
	 * @param isOut是否是料件
	 * @return
	 * @author wss
	 */
	public List findDzscContractDetailByProcessExe(Request request,String headId,String emsNo,Boolean isOut){
		return this.owpAppLogic.findDzscContractDetailByProcessExe(headId,emsNo,isOut);
	}
	
	/**
	 * 添加外发货物(从合同)
	 * @param request
	 * @param owpAppHead
	 * @param list
	 * @return List<OwpAppSendItem>
	 * @author wss
	 */
	public List<OwpAppSendItem> addOwpAppSendItemFromContract(Request request,OwpAppHead owpAppHead,List list){
		return owpAppLogic. addOwpAppSendItemFromContract(owpAppHead,list);
	}
	
	/**
	 * 添加外发货物(自用海关商品)
	 * @param request
	 * @param owpAppHead
	 * @param list
	 * @return List<OwpAppSendItem>
	 * @author wss
	 */
	public List<OwpAppSendItem> addOwpAppSendItemFromComplex(Request request,OwpAppHead owpAppHead,List list){
		return owpAppLogic. addOwpAppSendItemFromComplex(owpAppHead,list);
	}
	
	/**
	 * 添加收回货物
	 * @param request
	 * @param owpAppHead
	 * @param list
	 * @return List<OwpAppRecvItem>
	 * @author wss
	 */
	public List<OwpAppRecvItem> addOwpAppRecvItemFromContract(Request request,OwpAppHead owpAppHead,List list){
		return owpAppLogic.addOwpAppRecvItemFromContract(owpAppHead,list);
	}
	
	/**
	 * 添加收回货物(从自用海关商品)
	 * @param request
	 * @param owpAppHead
	 * @param list
	 * @return List<OwpAppRecvItem>
	 * @author wss
	 */
	public List<OwpAppRecvItem> addOwpAppRecvItemFromComplex(Request request,OwpAppHead owpAppHead,List list){
		return owpAppLogic. addOwpAppRecvItemFromComplex(owpAppHead,list);
	}

	/**
	 * 外发加工申请单报文生成
	 * @param head
	 * @return DeclareFileInfo
	 * @author wss
	 */
	public DeclareFileInfo declareOwpAppHead(Request request, OwpAppHead head,
			String taskId) {
		return this.owpAppLogic.declareOwpAppHead(head, taskId);
//		return null;
	}
	
	/**
	 * 变更外发加工申请单 （返回null表示不能变更， 否则 就变更一条新的记录）
	 * @param owpAppHead申请单表头
	 * @return owpAppHead 变更的申请单表头
	 * @author wss
	 */
	public OwpAppHead changingOwpAppHead(Request request, OwpAppHead owpAppHead){
		return this.owpAppLogic.changingOwpAppHead(owpAppHead);
	}

	/**
	 * 查找 owpAppHead 来自 copAppNo
	 * @author wss
	 */
	public OwpAppHead findOwpAppHeadByCopAppNo(Request request,
			String copAppNo){
		return this.owpAppDao.findOwpAppHeadByCopAppNo(copAppNo);
	}
	

	/**
	 * 外发加工申请单回执处理
	 * 
	 * @param owpAppHead
	 * @param existOwpAppHead
	 * @return
	 * @author wss
	 */
	public String processOwpAppHead(Request request, OwpAppHead owpAppHead,
			OwpAppHead existOwpAppHead, List lsReturnFile){
		return owpAppLogic.processOwpAppHead(owpAppHead,existOwpAppHead, lsReturnFile);
	}

	/**
	 * 分页条件查询商品编码
	 * 
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List findComplex(Request request, int index, int length,
			String property, Object value, boolean isLike) {
		return this.owpAppLogic.findComplex(index, length, property, value,
				isLike);
	}
	
	/**
	 * 申请表 外发货物序号重排
	 * @param headId 表头id
	 * @author wss
	 */
	public void sortSeqNumOfOwpAppSendItem(Request request,String headId){
		this.owpAppLogic.sortSeqNumOfOwpAppSendItem(headId);
	}
	/**
	 * 申请表 外发货物序号重排
	 * @param headId 表头id
	 * @author wss
	 */
	public void sortSeqNumOfOwpAppRecvItem(Request request,String headId){
		this.owpAppLogic.sortSeqNumOfOwpAppRecvItem(headId);
	}
	
}
