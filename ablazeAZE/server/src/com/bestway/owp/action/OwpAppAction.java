package com.bestway.owp.action;

import java.util.List;

import com.bestway.common.Condition;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.owp.entity.OwpAppHead;
import com.bestway.owp.entity.OwpAppRecvItem;
import com.bestway.owp.entity.OwpAppSendItem;

/**
 * 外发加工申请表action
 * @author wss
 */
public interface OwpAppAction {

	/**
	 * 查询外发加工申请表头
	 * @param request
	 * @return
	 * @author wss
	 */
	List findOwpAppHead(Request request);
	
	/**
	 * 根据id查询出相应的外发申请表表头
	 * @param request
	 * @param id
	 * @return
	 * @author wss
	 */
	OwpAppHead findOwpAppHeadById(Request request, String id);
	
	/**
	 * 新增外发加工申请表表头
	 * @return OwpAppHead 外发加工申请表表头
	 * @author wss
	 */
	OwpAppHead addOwpAppHead(Request request);
	
	/**
	 * 删除外发加工申请表表头
	 * @param head
	 * @author wss
	 */
	void deleteOwpAppHead(Request request, OwpAppHead head);
	
	/**
	 * 获取外发加工申请表的一些信息 包括申请表编号、承揽方企业代码、承揽方企业名称
	 * @param request
	 * @return
	 * @author wss
	 */
	List findOwpAppHeadInfos(Request request);

	/**
	 * 依组合条件查询外发加工申请表表头
	 * @param request
	 * @param list
	 * @return
	 * @author wss
	 */
	List<OwpAppHead> findOwpAppHeadByConditions(Request request,List<Condition> conditions);
	
	/**
	 * 查询相应申请表下的 外发货物
	 * @return list
	 * @author wss
	 */
	List<OwpAppSendItem> findOwpAppSendItemByHeadId(Request request, String headId);
	
	/**
	 * 查询相应申请表下的 收回货物
	 * @return list
	 * @author wss
	 */
	List<OwpAppRecvItem> findOwpAppRecvItemByHeadId(Request request, String headId);
	
	
	/**
	 * 删除外发加工申请表 外发货物
	 * @author wss
	 */
	List<OwpAppSendItem> deleteOwpAppSendItems(Request request, List<OwpAppSendItem> ls);
	
	/**
	 * 删除外发加工申请表 收回货物
	 * @author wss
	 */
	List<OwpAppRecvItem> deleteOwpAppRecvItems(Request request, List<OwpAppRecvItem> ls);
	
	/**
	 * 获取外发加工 外发货物 最大序号
	 * @author wss
	 */
	Integer getMaxSendListNo(Request request,OwpAppHead head);

	/**
	 * 获取外发加工 收回货物 最大序号
	 * @author wss
	 */
	Integer getMaxRecvListNo(Request request,OwpAppHead head);
	
	/**
	 * 保存外发加工 外发货物
	 * @author wss
	 */
	OwpAppSendItem saveOwpAppSendItem(Request request,OwpAppSendItem owpAppSendItem);
	
	/**
	 * 保存外发加工 收回货物
	 * @author wss
	 */
	OwpAppRecvItem saveOwpAppRecvItem(Request request,OwpAppRecvItem owpAppRecvItem);
	
	/**
	 * 保存外发申请表表头
	 * @param head
	 * @return
	 * @author wss
	 */
	OwpAppHead saveOwpAppHead(Request request, OwpAppHead head);
	
	/**
	 * 获取外发加工申请表表头信息
	 * 其中noNullField字段不能为空
	 * @author wss
	 */
	List<OwpAppHead> findOwpAppHeadInfo(Request request,String noNullField);

	
	/**
	 * 导入外发加工申请表 外发货物
	 * @param request
	 * @param ls
	 * @param isOverWrite
	 * @author wss
	 */
	List importOwpAppSendItemFromTxtFile(Request request, List ls, boolean isOverWrite);
	
	/**
	 * 导入外发加工申请表 收回货物
	 * @param request
	 * @param ls
	 * @param isOverWrite
	 * @author wss
	 */
	List importOwpAppRecvItemFromTxtFile(Request request, List ls, boolean isOverWrite);
	
	/**
	 * 查找通关手册备案中的进出口货物信息
	 * @param request
	 * @param headId 外发加工申请表表头id
	 * @param emsNo 手册号
	 * @param isOut是否是料件
	 * @return
	 * @author wss
	 */
	List findBcsContractDetailByProcessExe(Request request,String headId,String emsNo,Boolean isOut);
	
	/**
	 * 查找通关手册备案中的进出口货物信息
	 * @param request
	 * @param headId 外发加工申请表表头id
	 * @param emsNo 手册号
	 * @param isOut是否是料件
	 * @return
	 * @author wss
	 */
	List findBcusContractDetailByProcessExe(Request request,String headId,String emsNo,Boolean isOut);
	
	/**
	 * 查找通关手册备案中的进出口货物信息
	 * @param request
	 * @param headId 外发加工申请表表头id
	 * @param emsNo 手册号
	 * @param isOut是否是料件
	 * @return
	 * @author wss
	 */
	List findDzscContractDetailByProcessExe(Request request,String headId,String emsNo,Boolean isOut);
	
	/**
	 * 添加外发货物(从合同)
	 * @param request
	 * @param owpAppHead
	 * @param list
	 * @return List<OwpAppSendItem>
	 * @author wss
	 */
	List<OwpAppSendItem> addOwpAppSendItemFromContract(Request request,OwpAppHead owpAppHead,List list);
	
	/**
	 * 添加外发货物(从自用海关商品)
	 * @param request
	 * @param owpAppHead
	 * @param list
	 * @return List<OwpAppSendItem>
	 * @author wss
	 */
	List<OwpAppSendItem> addOwpAppSendItemFromComplex(Request request,OwpAppHead owpAppHead,List list);
	
	/**
	 * 添加收回货物
	 * @param request
	 * @param owpAppHead
	 * @param list
	 * @return List<OwpAppRecvItem>
	 * @author wss
	 */
	List<OwpAppRecvItem> addOwpAppRecvItemFromContract(Request request,OwpAppHead owpAppHead,List list);
	
	/**
	 * 添加收回货物(从自用海关商品)
	 * @param request
	 * @param owpAppHead
	 * @param list
	 * @return List<OwpAppRecvItem>
	 * @author wss
	 */
	List<OwpAppRecvItem> addOwpAppRecvItemFromComplex(Request request,OwpAppHead owpAppHead,List list);
	
	/**
	 * 外发加工申请单报文生成
	 * @param head
	 * @return DeclareFileInfo
	 * @author wss
	 */
	public DeclareFileInfo declareOwpAppHead(Request request, OwpAppHead head,
			String taskId);
	
	/**
	 * 变更外发加工申请单 （返回null表示不能变更， 否则 就变更一条新的记录）
	 * @param owpAppHead申请单表头
	 * @return OwpAppHead 变更的申请单表头
	 * @author wss
	 */
	public OwpAppHead changingOwpAppHead(Request request, OwpAppHead owpAppHead);
	
	/**
	 * 查找 owpAppHead 来自 copAppNo
	 * @author wss
	 */
	public OwpAppHead findOwpAppHeadByCopAppNo(Request request,
			String copAppNo);

	/**
	 * 外发加工申请单回执处理
	 * 
	 * @param owpAppHead
	 * @param existOwpAppHead
	 * @return
	 * @author wss
	 */
	public String processOwpAppHead(Request request, OwpAppHead OwpAppHead,
			OwpAppHead existOwpAppHead, List lsReturnFile);

	/**
	 * 分页条件查询商品编码
	 * 
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 * @author wss
	 */
	public List findComplex(Request request, int index, int length,
			String property, Object value, boolean isLike);
	
	/**
	 * 申请表 外发货物序号重排
	 * @param headId 表头id
	 * @author wss
	 */
	public void sortSeqNumOfOwpAppSendItem(Request request,String headId);
	/**
	 * 申请表 外发货物序号重排
	 * @param headId 表头id
	 * @author wss
	 */
	public void sortSeqNumOfOwpAppRecvItem(Request request,String headId);
}
