package com.bestway.owp.action;

import java.util.List;

import com.bestway.common.Condition;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.message.action.CspMessageAction;
import com.bestway.owp.entity.OwpAppHead;
import com.bestway.owp.entity.OwpBillRecvHead;
import com.bestway.owp.entity.OwpBillRecvItem;
import com.bestway.owp.entity.OwpBillSendHead;
import com.bestway.owp.entity.OwpBillSendItem;

/**
 * 外发加工登记表接口
 * @author sxk
 *
 */
public interface OwpBillAction  {
	/**
	 * 外发加工申请单报文生成
	 * @param head
	 * @return DeclareFileInfo
	 * @author sxk
	 */
	public DeclareFileInfo declareOwpBillSendHead(Request request, OwpBillSendHead head,
			String taskId);
	/**
	 * 外发加工申请单报文生成
	 * @param head
	 * @return DeclareFileInfo
	 * @author sxk
	 */
	public DeclareFileInfo declareOwpBillRecvHead(Request request, OwpBillRecvHead head,
			String taskId);
	
	/**
	 * 外发加工撤销单报文生成
	 * @param head
	 * @return DeclareFileInfo
	 * @author sxk
	 */
	public DeclareFileInfo declareOwpBillCancelHead(Request request, OwpBillSendHead head,
			String taskId);
	/**
	 * 根据id查询出相应的外发登记表表头
	 * @param id
	 * @return
	 * @author sxk
	 */
	OwpBillSendHead findOwpBillSendHeadById(Request request,String id);
	/**
	 * 根据id查询出相应的外发登记表表头
	 * @param id
	 * @return
	 * @author sxk
	 */
	OwpBillRecvHead findOwpBillRecvHeadById(Request request,String id);
	
	/**
	 * 按发货日期循序asc查找出该申请表下的所有出货登记表
	 * @param request
	 * @param id
	 * @return
	 * @author sxk
	 */
	List findOwpBillSendHead(Request request,String appNo);
	
	/**
	 * 按发货日期循序asc查找出该申请表下的所有收货登记表
	 * @param request
	 * @param id
	 * @return
	 * @author sxk
	 */
	List findOwpBillRecvHead(Request request,String appNo);
	/**
	 * 保存外发出货登记表表头
	 * @param head
	 * @return
	 * @author sxk
	 */
	OwpBillSendHead saveOwpSendBillHead(Request request, OwpBillSendHead head);
	
	/**
	 * 保存外发收货登记表表头
	 * @param head
	 * @return
	 * @author sxk
	 */
	OwpBillRecvHead saveOwpBillRecvHead(Request request, OwpBillRecvHead head);
	/**
	 * 保存外发外发登记表表体
	 * @param head
	 * @return
	 * @author sxk
	 */
	OwpBillSendItem saveOwpSendBillItem(Request request, OwpBillSendItem head);
	/**
	 * 保存外发回收登记表表体
	 * @param head
	 * @return
	 * @author sxk
	 */
	OwpBillRecvItem saveOwpBillRecvItem(Request request, OwpBillRecvItem head);
	/**
	 * 查询相应出货登记表下的 外发货物
	 * @return list
	 * @author sxk
	 */
	List findOwpSendBillItemByHead(Request request,OwpBillSendHead head);
	/**
	 * 查询相应进货登记表下的 外发货物
	 * @return list
	 * @author sxk
	 */
	List findOwpRecvBillItemByHead(Request request,OwpBillRecvHead head);
	/**
	 * 删除外发加工出货登记表表头
	 */
	void deleteOwpSendBillHead(Request request,OwpBillSendHead head);
	/**
	 * 删除外发加工收货登记表表头
	 */
	void deleteOwpRecvBillHead(Request request,OwpBillRecvHead head);
	/**
	 * 删除外发加工出货登记表表体
	 */
	void deleteOwpSendBillItem(Request request,OwpBillSendItem item);
	/**
	 * 删除外发加工收货登记表表体
	 */
	void deleteOwpBillRecvItem(Request request,OwpBillRecvItem item);
	/**
	 * 查找外发加工出货登记表头
	 * @param request
	 * @param 申请表编号
	 * @return 
	 * @author sxk
	 */
	List findOwpSendBillHeadInfo(Request request,String appNo);
	/**
	 * 查找外发加工进货登记表头
	 * @param request
	 * @param 申请表编号
	 * @return 
	 * @author sxk
	 */
	List findOwpRecvBillHeadInfo(Request request,String appNo);
	/**
	 * 查询外发加工申请表头
	 * @param request
	 * @return
	 * @author sxk
	 */
	List findOwpAppHead(Request request);
	
	/**
	 * 查询外发加工申请表进出货物
	 * @param appNo 外发加工申请表编号
	 * @param billHeadId 外发加工登记表id
	 * @param isout 是否收发货
	 * @return
	 * @author sxk
	 */
	List findOwpSendItemByHead(Request request,String appNo,String billHeadId,Boolean isout);
	
	
	List findBillDetailSendItem(Request request,String appNo,String billHeadId,Boolean isout);
	
	/**
	 * 来源于申请表的登记表体资料进行保存
	 * @param request
	 * @param owpBillSendHead 外发加工出货登记表头
	 * @param list 申请表已选择商品LIST
	 * @return
	 * @author sxk
	 */
	List addOwpBillSendItem(Request request,OwpBillSendHead owpBillSendHead,List list);
	/**
	 * 来源于申请表的登记表体资料进行保存
	 * @param request
	 * @param owpBillSendHead 外发加工进货登记表头
	 * @param list 申请表已选择商品LIST
	 * @return
	 * @author sxk
	 */
	List addOwpBillRecvItem(Request request,OwpBillRecvHead owpBillRecvHead,List list);
	
	
	/**
	 * 来源于单据中心的出货登记表体资料进行保存
	 * @param request
	 * @param owpBillSendHead
	 * @param list
	 * @return
	 * @author sxk
	 */
	List addBillDetailSendItem(Request request,OwpBillSendHead owpBillSendHead,List list);
	
	/**
	 * 来源于单据中心的进货登记表体资料进行保存
	 * @param request
	 * @param owpBillSendHead
	 * @param list
	 * @return
	 * @author sxk
	 */
	List addBillDetailRecvItem(Request request,OwpBillRecvHead owpBillRecvHead,List list);
	
	/**
	 * 查询申请表外发料件申报数量
	 * @param request
	 * @param appNo 外发加工申请表编号
	 * @param seqNum 外发加工申请表商品序号
	 * @return
	 * @author sxk
	 */
	List findOwpAppSendItemQty(Request request,String appNo,Integer seqNum);
	
	/**
	 * 查询申请表进货料件申报数量
	 * @param request
	 * @param appNo 外发加工申请表编号
	 * @param seqNum 外发加工申请表商品序号
	 * @return
	 * @author sxk
	 */
	List findOwpAppRecvItemQty(Request request,String appNo,Integer seqNum);
	/**
	 * 查询登记表外发料件
	 * @param request
	 * @param appNo 外发加工申请表编号
	 * @param seqNum 外发加工申请表商品序号
	 * @return
	 * @author sxk
	 */
	List findOwpBillSendItemBySeqNum(Request request,String appNo,Integer seqNum);
	
	/**
	 * 查询登记表收回料件
	 * @param request
	 * @param appNo 外发加工申请表编号
	 * @param seqNum 外发加工申请表商品序号
	 * @return
	 * @author sxk
	 */
	List findOwpBillRecvItemBySeqNum(Request request,String appNo,Integer seqNum);
	/**
	 * 查找 owpBillSendHead 来自 copAppNo
	 * @author sxk
	 */
    OwpBillSendHead findOwpBillSendHeadByCopBillNo(Request request,String copBillNo);
    /**
	 * 查找 owpBillRecvHead 来自 copAppNo
	 * @author sxk
	 */
    OwpBillRecvHead findOwpBillRecvHeadByCopBillNo(Request request,String copBillNo);
    
    /**
	 * 外发加工申请单回执处理
	 * 
	 * @param owpBillSendHead
	 * @param existOwpBillSendHead
	 * @return
	 * @author sxk
	 */
	String processOwpBillSendHead(Request request, OwpBillSendHead owpBillSendHead,
			OwpBillSendHead existOwpBillSendHead, List lsReturnFile);
	/**
	 * 外发加工申请单回执处理
	 * 
	 * @param owpBillRecvHead
	 * @param existOwpBillRecvHead
	 * @return
	 * @author sxk
	 */
	String processOwpBillRecvHead(Request request, OwpBillRecvHead owpBillRecvHead,
			OwpBillRecvHead existOwpBillRecvHead, List lsReturnFile);
	/**
	 * 依组合条件查询外发加工出货登记表表头
	 * @param request
	 * @param list
	 * @return
	 * @author sxk
	 */
	List findOwpBillSendHeadByConditions(Request request,List conditions);
	/**
	 * 依组合条件查询外发加工收获登记表表头
	 * @param request
	 * @param list
	 * @return
	 * @author sxk
	 */
	List findOwpBillRecvHeadByConditions(Request request,List conditions);
	
	
	/**
	 * 查询外发加工出货登记表表头最大流水号
	 * @param request
	 * @param appNo
	 * @return
	 */
	Integer findBillSendHeadMaxNo(Request request,String appNo);
	
	/**
	 * 查询外发加工收货登记表表头最大流水号
	 * @param request
	 * @param appNo
	 * @return
	 */
	Integer findBillRecvHeadMaxNo(Request request,String appNo);
}
