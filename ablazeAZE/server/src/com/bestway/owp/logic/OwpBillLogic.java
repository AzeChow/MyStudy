package com.bestway.owp.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.cas.invoice.entity.CurrentBillDetailBom;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.MaterielType;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.OwpBusinessType;
import com.bestway.common.message.entity.CspReceiptResult;
import com.bestway.common.message.entity.CspSignInfo;
import com.bestway.common.message.entity.TempCspReceiptResultInfo;
import com.bestway.common.message.logic.CspProcessMessage;
import com.bestway.owp.dao.OwpBillDao;
import com.bestway.owp.entity.OwpAppRecvItem;
import com.bestway.owp.entity.OwpAppSendItem;
import com.bestway.owp.entity.OwpBillAndBillDetail;
import com.bestway.owp.entity.OwpBillRecvHead;
import com.bestway.owp.entity.OwpBillRecvItem;
import com.bestway.owp.entity.OwpBillSendHead;
import com.bestway.owp.entity.OwpBillSendItem;
import com.bestway.owp.entity.OwpReceiptResult;
import com.bestway.owp.entity.OwpSignInfo;


/**
 * 外发加工登记表逻辑类
 * @author sxk
 */
public class OwpBillLogic {

	/**
	 * 外发加工登记表数据访问接口
	 */
	private OwpBillDao owpBillDao;

	/**
	 * 外发加工信息报文逻辑
	 */
	private OwpMessageLogic owpMessageLogic;
	
	/**
	 * 获取外发加工登记表数据访问接口
	 * @return
	 */
	public OwpBillDao getOwpBillDao() {
		return owpBillDao;
	}

	/**
	 * 设置外发加工登记表数据访问接口
	 * @param owpBillDao
	 */
	public void setOwpBillDao(OwpBillDao owpBillDao) {
		this.owpBillDao = owpBillDao;
	}
	/**
	 * 获取外发加工信息报文逻辑
	 * @return
	 */
	public OwpMessageLogic getOwpMessageLogic() {
		return owpMessageLogic;
	}

	/**
	 * 设置外发加工信息报文逻辑
	 * @param owpMessageLogic
	 */
	public void setOwpMessageLogic(OwpMessageLogic owpMessageLogic) {
		this.owpMessageLogic = owpMessageLogic;
	}

	/**
	 *  删除外发加工出货登记表表头
	 * @param head 外发加工出货登记表表头
	 * @author sxk
	 */
	public void deleteOwpSendBillHead(OwpBillSendHead head) {
		List<OwpBillSendItem> list = this.owpBillDao
		.findOwpSendBillItemByHead(head);
		for(OwpBillSendItem owpBillSendItem:list){
			List list2 = this.owpBillDao.findOwpBillAndBillDetailByOwpBillSendItem(owpBillSendItem.getId());
			this.owpBillDao.deleteAll(list2);
		}
		this.owpBillDao.deleteAll(list);
		this.owpBillDao.deleteAll(this.owpBillDao
				.findOwpSendBillItemByHead(head));
		this.owpBillDao.delete(head);
	}
	/**
	 *  删除外发加工收货登记表表头
	 * @param head 外发加工收货登记表表头
	 * @author sxk
	 */
	public void deleteOwpRecvBillHead(OwpBillRecvHead head) {
		List<OwpBillRecvItem> list = this.owpBillDao
		.findOwpBillRecvItemByHead(head);
		for(OwpBillRecvItem owpBillRecvItem:list){
			List list2 = this.owpBillDao.findOwpRecvBillAndBillDetailByOwpBillSendItem(owpBillRecvItem.getId());
			this.owpBillDao.deleteAll(list2);
		}
		this.owpBillDao.deleteAll(list);
		this.owpBillDao.delete(head);
	}
	
	/**
	 * 查询外发加工申请表进出货物
	 * @param appNo 外发加工申请表标号
	 * @param billHeadId 外发加工登记表id
	 * @param isout 是否收发货
	 * @return
	 * @author sxk
	 */
	public List findOwpSendItemByHead(String appNo,String billHeadId,Boolean isOut){
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "";
		if (isOut) {
			hsql += "select a from  OwpAppSendItem a left join a.head b "
					+ "  where a.company= ? " + "and b.declareState = ? ";
		} else {
			hsql += "select a from  OwpAppRecvItem a left join a.head b "
				+ "  where a.company= ? " + "and b.declareState = ? ";
		}
		paramters.add(CommonUtils.getCompany());
		paramters.add(DeclareState.PROCESS_EXE);//正在执行的合同
		System.out.println("appNo="+appNo);
		if (appNo != null && !"".equals(appNo)) {
			hsql += " and b.appNo = ? ";
			paramters.add(appNo);
		}
		if (billHeadId != null && !"".equals(billHeadId)) {
			if (isOut) {
				hsql += " and a.seqNum not in "
						+ "( select c.appGNo from OwpBillSendItem c where c.head.id=? ) ";
				paramters.add(billHeadId);
			} else {
				hsql += " and a.seqNum not in "
						+ "( select c.appGNo from OwpBillRecvItem c where c.head.id=? ) ";
				paramters.add(billHeadId);
			}
		}
		hsql += " order by a.seqNum ";
		
		return this.owpBillDao.find(hsql, paramters.toArray());
	}
	/**
	 * 外发加工出货登记单报文生成
	 * @param head 外发加工出货登记表表头
	 * @return DeclareFileInfo
	 * @author sxk
	 */
	public DeclareFileInfo declareOwpBillSendHead(OwpBillSendHead head, String taskId) {
		
		head = this.owpBillDao.findOwpBillSendHeadById(head.getId());
		System.out.println("createDate1="+head.getCreateDate());
		Map<String, List> hmData = new HashMap<String, List>();
		List<CspSignInfo> lsSignInfo = new ArrayList<CspSignInfo>();
		
		List<OwpBillSendHead> lsOwpBillSendHead = new ArrayList<OwpBillSendHead>();
		List<OwpBillSendItem> lsOwpBillSendItem = new ArrayList<OwpBillSendItem>();
		
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("正在获取要申报的资料");
		}
		OwpSignInfo signInfo = (OwpSignInfo)owpMessageLogic.getCspPtsSignInfo(info, null);
			
		signInfo.setCopNo(head.getCopBillNo());//企业内部编码
		signInfo.setInOutFlag("0");
		signInfo.setColDcrTime(0);//核查报核次数

		signInfo.setAppNo(head.getAppNo());//申请表编号
		signInfo.setSeqNo(head.getSeqNo());//序列号
		
		
		signInfo.setSignDate(new Date());//签名日期
		
		if (DeclareState.CHANGING_EXE.equals(head.getDeclareState())) {
			signInfo.setDeclareType(DelcareType.MODIFY);//申请类型
		}
		lsSignInfo.add(signInfo);
		if (signInfo.getIdCard() == null
				|| "".equals(signInfo.getIdCard().trim())) {
			throw new RuntimeException("签名信息中操作员卡号为空");
		}
		if (signInfo.getIdCard().length() < 4) {
			throw new RuntimeException("签名信息中操作员卡号的长度小于4位");
		}
		head.setCollectDate(new Date());//申报日期
			
		head.setDeclareState(DeclareState.WAIT_EAA);
		
		lsOwpBillSendHead.add(head);
		
		lsOwpBillSendItem = this.owpBillDao.findOwpSendBillItemByHead(head);
			
		String formatFile = "OwpBillFormat.xml";// 转入

		hmData.put("OwpBillSign", lsSignInfo);
		hmData.put("OwpBillSendHead", lsOwpBillSendHead);
		hmData.put("OwpBillSendItem", lsOwpBillSendItem);
		
		DeclareFileInfo fileInfo = owpMessageLogic.exportMessage(formatFile,
				hmData, info);
		
		this.owpBillDao.saveOrUpdate(head);//保存
		return fileInfo;
	}
	/**
	 * 外发加工进货登记单报文生成
	 * @param head 外发加工收货登记表表头
	 * @return DeclareFileInfo
	 * @author sxk
	 */
	public DeclareFileInfo declareOwpBillRecvHead(OwpBillRecvHead head, String taskId) {
		
		head = this.owpBillDao.findOwpBillRecvHeadById(head.getId());
		Map<String, List> hmData = new HashMap<String, List>();
		List<CspSignInfo> lsSignInfo = new ArrayList<CspSignInfo>();
		
		List<OwpBillRecvHead> lsOwpBillRecvHead = new ArrayList<OwpBillRecvHead>();
		List<OwpBillRecvItem> lsOwpBillRecvItem = new ArrayList<OwpBillRecvItem>();
		
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("正在获取要申报的资料");
		}
		OwpSignInfo signInfo = (OwpSignInfo)owpMessageLogic.getCspPtsSignInfo(info, null);
			
		signInfo.setCopNo(head.getCopBillNo());//企业内部编码
		signInfo.setInOutFlag("1");
		signInfo.setColDcrTime(0);//核查报核次数

		signInfo.setAppNo(head.getAppNo());//申请表编号
		signInfo.setSeqNo(head.getSeqNo());//序列号
		
		
		signInfo.setSignDate(new Date());//签名日期
		
		if (DeclareState.CHANGING_EXE.equals(head.getDeclareState())) {
			signInfo.setDeclareType(DelcareType.MODIFY);//申请类型
		}
		lsSignInfo.add(signInfo);
		if (signInfo.getIdCard() == null
				|| "".equals(signInfo.getIdCard().trim())) {
			throw new RuntimeException("签名信息中操作员卡号为空");
		}
		if (signInfo.getIdCard().length() < 4) {
			throw new RuntimeException("签名信息中操作员卡号的长度小于4位");
		}
		head.setCollectDate(new Date());//申报日期
			
		head.setDeclareState(DeclareState.WAIT_EAA);
		
		lsOwpBillRecvHead.add(head);
		
		lsOwpBillRecvItem = this.owpBillDao.findOwpBillRecvItemByHead(head);
			
		String formatFile = "OwpBillRecvFormat.xml";// 转入

		hmData.put("OwpBillSign", lsSignInfo);
		hmData.put("OwpBillRecvHead", lsOwpBillRecvHead);
		hmData.put("OwpBillRecvItem", lsOwpBillRecvItem);
		
		DeclareFileInfo fileInfo = owpMessageLogic.exportMessage(formatFile,
				hmData, info);
		
		this.owpBillDao.saveOrUpdate(head);//保存
		return fileInfo;
	}
	/**
	 * 外发加工出货登记单报文生成
	 * @param head 外发加工出货登记表表头
	 * @return DeclareFileInfo
	 * @author sxk
	 */
	public DeclareFileInfo declareOwpBillCancelHead(OwpBillSendHead head, String taskId) {
		
		System.out.println("head.getCancelReason="+head.getCancelReason());
//		head = this.owpBillDao.findOwpBillSendHeadById(head.getId());
		Map<String, List> hmData = new HashMap<String, List>();
		List<CspSignInfo> lsSignInfo = new ArrayList<CspSignInfo>();
		
		List<OwpBillSendHead> lsOwpBillSendHead = new ArrayList<OwpBillSendHead>();
//		List<OwpBillSendItem> lsOwpBillSendItem = new ArrayList<OwpBillSendItem>();
		
		
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("正在获取要申报的资料");
		}
		OwpSignInfo signInfo = (OwpSignInfo)owpMessageLogic.getCspPtsSignInfo(info, null);
			
		signInfo.setAppNo(head.getAppNo());
		signInfo.setCopBillNo(head.getCopBillNo());//企业内部编码
//		signInfo.setInOutFlag("0");
		signInfo.setColDcrTime(0);//核查报核次数
		signInfo.setSignDate(new Date());//签名日期
		
		if (DeclareState.CHANGING_EXE.equals(head.getDeclareState())) {
			signInfo.setDeclareType(DelcareType.MODIFY);//申请类型
		}
		lsSignInfo.add(signInfo);
		if (signInfo.getIdCard() == null
				|| "".equals(signInfo.getIdCard().trim())) {
			throw new RuntimeException("签名信息中操作员卡号为空");
		}
		if (signInfo.getIdCard().length() < 4) {
			throw new RuntimeException("签名信息中操作员卡号的长度小于4位");
		}
		head.setCollectDate(new Date());//申报日期
			
		head.setDeclareState(DeclareState.CHANGING_EXE);
		
		head.setCanelSort("123");
		
		lsOwpBillSendHead.add(head);
		
//		lsOwpBillSendItem = this.owpBillDao.findOwpSendBillItemByHead(head);
			
		String formatFile = "OwpCancelBillFormat.xml";// 转入

		hmData.put("OwpCancelBillSign", lsSignInfo);
		hmData.put("OwpCancelBillHead", lsOwpBillSendHead);
//		hmData.put("OwpBillSendItem", lsOwpBillSendItem);
		
		DeclareFileInfo fileInfo = owpMessageLogic.exportMessage(formatFile,
				hmData, info);
		
		this.owpBillDao.saveOrUpdate(head);//保存
		return fileInfo;
	}
	/**
	 * 查找单据中心进出单据
	 * @param appNo 申请表编号
	 * @param billHeadId 登记表表头ID
	 * @param isOut 是否进出
	 * @return
	 * @author sxk
	 */
	public List findBillDetailSendItem(String appNo,String billHeadId,Boolean isOut){
		// 成品查询结果
		List<BillDetail> ms = null;
		// 返回折算最终结果
		List<CurrentBillDetailBom> billBoms = new ArrayList<CurrentBillDetailBom>();
		// 组织成品查询条件
		List<Condition> conditions = getConditionsConvert();

		List<BillDetail> result = new ArrayList<BillDetail>();
		ms = new ArrayList<BillDetail>();
		String[] codes = null;
		String billDetailTableName = null;
		//是否出货
		if(isOut){
		//料件
		billDetailTableName = BillUtil
				.getBillDetailTableNameByMaterielType
				(MaterielType.MATERIEL);
		codes = new String[] { "1113", "1017" };
		conditions.add(new Condition("and", null, "billMaster.billType.code",
				" in (", codes,")"));// 查询半成品外发
		ms = owpBillDao.commonSearch("", billDetailTableName, conditions, "","");
		
		//半成品
		billDetailTableName = BillUtil
		.getBillDetailTableNameByMaterielType
		(MaterielType.SEMI_FINISHED_PRODUCT);
		codes = new String[] { "4106", "4004" };
		conditions.clear();
		conditions.add(new Condition("and", null, "billMaster.billType.code",
				" in (", codes,")"));// 查询半成品外发
		if(null != owpBillDao.commonSearch("", billDetailTableName, conditions, "","")){
			ms.addAll(owpBillDao.commonSearch("", billDetailTableName, conditions, "",""));
		}
		
			for(BillDetail billDetail:ms){
				//查找登记表和单据中间表是否存在已引用、存在的话该商品就不能继续被使用
				List list = this.owpBillDao.findOwpBillAndBillDetail(billDetail.getId());
				if(null==list || list.size()==0){
					
					//查找申请表是否有改商品、实际在SQL语句中判断更好
					List<OwpAppSendItem> list1 = this.owpBillDao.findOwpSendItemByHead(appNo);
					Map<String,OwpAppSendItem> map = new HashMap<String,OwpAppSendItem>();
					if(null != list1 && list1.size()>0){
						for(OwpAppSendItem owpAppSendItem : list1){
							map.put(owpAppSendItem.getComplex().getCode(), owpAppSendItem);
						}
					}
//					if(billDetail.getComplex()==null){
//						 throw new RuntimeException("单据号["+billDetail.getBillNo()+"]"+"料号为["+billDetail.getPtPart()+"]的商品编码在自用商编中不存在");
//					}
					OwpAppSendItem owpAppSendItem = map.get(billDetail.getComplex()==null?null:billDetail.getComplex().getCode());
					if(owpAppSendItem != null){
						result.add(billDetail);
					}
				}
			}
		}else{
			//半成品
			billDetailTableName = BillUtil
			.getBillDetailTableNameByMaterielType
			(MaterielType.SEMI_FINISHED_PRODUCT);
			codes = new String[] { "4006", "4104" };
			conditions.add(new Condition("and", null, "billMaster.billType.code",
					" in (", codes,")"));// 查询半成品外发
			ms = owpBillDao.commonSearch("", billDetailTableName, conditions, "","");
			
			//成品
			billDetailTableName = BillUtil
			.getBillDetailTableNameByMaterielType
			(MaterielType.FINISHED_PRODUCT);
			codes = new String[] { "2015", "2113" };
			conditions.clear();
			conditions.add(new Condition("and", null, "billMaster.billType.code",
					" in (", codes,")"));
			List list3 = owpBillDao.commonSearch("", billDetailTableName, conditions, "","");
			if(null != owpBillDao.commonSearch("", billDetailTableName, conditions, "","")){
				ms.addAll(owpBillDao.commonSearch("", billDetailTableName, conditions, "",""));
			}
			
			//料件
			billDetailTableName = BillUtil
			.getBillDetailTableNameByMaterielType
			(MaterielType.MATERIEL);
			conditions.clear();
			codes = new String[] { "1018" };
			conditions.add(new Condition("and", null, "billMaster.billType.code",
					" in (", codes,")"));
			if(null != owpBillDao.commonSearch("", billDetailTableName, conditions, "","")){
				ms.addAll(owpBillDao.commonSearch("", billDetailTableName, conditions, "",""));
			}
			
			//边角料
			billDetailTableName = BillUtil
			.getBillDetailTableNameByMaterielType
			(MaterielType.REMAIN_MATERIEL);
			conditions.clear();
			codes = new String[] { "6005" };
			conditions.add(new Condition("and", null, "billMaster.billType.code",
					" in (", codes,")"));// 查询半成品外发
			if(null != owpBillDao.commonSearch("", billDetailTableName, conditions, "","")){
				ms.addAll(owpBillDao.commonSearch("", billDetailTableName, conditions, "",""));
			}
			
			//残次品
			billDetailTableName = BillUtil
			.getBillDetailTableNameByMaterielType
			(MaterielType.BAD_PRODUCT);
			conditions.clear();
			codes = new String[] { "5003" };
			conditions.add(new Condition("and", null, "billMaster.billType.code",
					" in (", codes,")"));// 查询半成品外发
			if(null != owpBillDao.commonSearch("", billDetailTableName, conditions, "","")){
				ms.addAll(owpBillDao.commonSearch("", billDetailTableName, conditions, "",""));
			}
		
		
		//查找申请表是否有改商品、实际在SQL语句中判断更好
		List<OwpAppRecvItem> list1 = this.owpBillDao.findOwpRecvItemByHead(appNo);
		for(BillDetail billDetail:ms){
			//查找登记表和单据中间表是否存在已引用、存在的话该商品就不能继续被使用
			List list = this.owpBillDao.findOwpBillAndBillDetail(billDetail.getId());
			if(null==list || list.size()==0){
				
				Map<String,OwpAppRecvItem> map = new HashMap<String,OwpAppRecvItem>();
				if(null != list1 && list1.size()>0){
					for(OwpAppRecvItem owpAppRecvItem : list1){
						map.put(owpAppRecvItem.getComplex().getCode(), owpAppRecvItem);
					}
				}
//				if(billDetail.getComplex()==null){
//					 throw new RuntimeException("单据号["+billDetail.getBillNo()+"]"+"料号为["+billDetail.getPtPart()+"]的商品编码在自用商编中不存在");
//				}
				OwpAppRecvItem owpAppRecvItem = map.get(billDetail.getComplex()==null?null:billDetail.getComplex().getCode());
				if(owpAppRecvItem != null){
					result.add(billDetail);
				}
			}
		}
		}
		return result;
	}
	/**
	 * 生成查询条件,过滤成品查询条件
	 * @author sxk
	 * @return
	 */
	public List<Condition> getConditionsConvert() {
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				new Boolean(true), null));

		return conditions;
	}
	/**
	 * 来源于申请表的出货登记表体资料进行保存
	 * @param owpBillSendHead 外发加工出货登记表头
	 * @param list 申请表已选择商品LIST
	 * @return
	 * @author sxk
	 */
	public List addOwpBillSendItem(OwpBillSendHead owpBillSendHead,List list){
		List<OwpBillSendItem> owpBillSendItemList = new ArrayList<OwpBillSendItem>();
		
		int billMaxNo = this.owpBillDao.findBillSendMaxNo(owpBillSendHead.getBillNo());
		int index = 0 ;
		
		for(int i = 0;i<list.size();i++){
			index++;
			OwpAppSendItem owpAppSendItem =(OwpAppSendItem) list.get(i);
			System.out.println("owpAppSendItem.getTrNo()="+owpAppSendItem.getTrNo());
			OwpBillSendItem owpBillSendItem = new OwpBillSendItem();
			owpBillSendItem.setHead(owpBillSendHead);
			owpBillSendItem.setListNo(billMaxNo+index);
			owpBillSendItem.setAppGNo(owpAppSendItem.getSeqNum());
			owpBillSendItem.setTrNo(owpAppSendItem.getTrNo());
			owpBillSendItem.setComplex(owpAppSendItem.getComplex());
			owpBillSendItem.setHsName(owpAppSendItem.getHsName());
			owpBillSendItem.setHsSpec(owpAppSendItem.getHsSpec());
			owpBillSendItem.setHsUnit(owpAppSendItem.getHsUnit());
			owpBillSendItem.setModifyMark(ModifyMarkState.ADDED);
			this.owpBillDao.saveOwpBillSendItem(owpBillSendItem);
			owpBillSendItemList.add(owpBillSendItem);
		}
		return owpBillSendItemList;
		
	}
	/**
	 * 来源于申请表的收货登记表体资料进行保存
	 * @param request
	 * @param owpBillSendHead 外发加工进货登记表头
	 * @param list 申请表已选择商品LIST
	 * @return
	 * @author sxk
	 */
	public List addOwpBillRecvItem(OwpBillRecvHead owpBillRecvHead,List list){
		List<OwpBillRecvItem> owpBillRecvItemList = new ArrayList<OwpBillRecvItem>();
		
		int billMaxNo = this.owpBillDao.findBillRecvMaxNo(owpBillRecvHead.getBillNo());
		int index = 0 ;
		
		for(int i = 0;i<list.size();i++){
			index++;
			OwpAppRecvItem owpAppRecvItem =(OwpAppRecvItem) list.get(i);
			OwpBillRecvItem owpBillRecvItem = new OwpBillRecvItem();
			owpBillRecvItem.setHead(owpBillRecvHead);
			owpBillRecvItem.setListNo(billMaxNo+index);
			owpBillRecvItem.setAppGNo(owpAppRecvItem.getSeqNum());
			owpBillRecvItem.setTrNo(owpAppRecvItem.getTrNo());
			owpBillRecvItem.setComplex(owpAppRecvItem.getComplex());
			owpBillRecvItem.setHsName(owpAppRecvItem.getHsName());
			owpBillRecvItem.setHsSpec(owpBillRecvItem.getHsSpec());
			owpBillRecvItem.setHsUnit(owpAppRecvItem.getHsUnit());
			owpBillRecvItem.setModifyMark(ModifyMarkState.ADDED);
			this.owpBillDao.saveOwpBillRecvItem(owpBillRecvItem);
			owpBillRecvItemList.add(owpBillRecvItem);
		}
		return owpBillRecvItemList;
		
	}
	/**
	 * 来源于单据中心的出货登记表体资料进行保存
	 * @param owpBillSendHead 外发加工出货登记表头
	 * @param list 申请表已选择商品LIST
	 * @return
	 * @author sxk
	 */
	public List addBillDetailSendItem(OwpBillSendHead owpBillSendHead,List list){
		List<OwpBillSendItem> owpBillSendItemList = new ArrayList<OwpBillSendItem>();
		
		int billMaxNo = this.owpBillDao.findBillSendMaxNo(owpBillSendHead.getBillNo());
		int index = 0 ;
		//查找申请表是否有改商品、实际在SQL语句中判断更好
		List<OwpAppSendItem> list1 = this.owpBillDao.findOwpSendItemByHead(owpBillSendHead.getAppNo());
		Map<String,Integer> map = new HashMap<String,Integer>();
		Map<String,Integer> map1 = new HashMap<String,Integer>();
		if(null != list1 && list1.size()>0){
			for(OwpAppSendItem owpAppSendItem : list1){
				map.put(owpAppSendItem.getComplex().getCode(), owpAppSendItem.getSeqNum());
				map1.put(owpAppSendItem.getComplex().getCode(), owpAppSendItem.getTrNo());
			}
		}
		for(int i = 0;i<list.size();i++){
			index++;
			BillDetail billDetail =(BillDetail) list.get(i);
			
			OwpBillSendItem owpBillSendItem = new OwpBillSendItem();
			owpBillSendItem.setHead(owpBillSendHead);
			owpBillSendItem.setListNo(billMaxNo+index);
			owpBillSendItem.setComplex(billDetail.getComplex());
			owpBillSendItem.setHsName(billDetail.getHsName());
			owpBillSendItem.setHsSpec(billDetail.getHsSpec());
			owpBillSendItem.setHsUnit(billDetail.getHsUnit());
			if(billDetail.getBillMaster().getBillType().getCode().equals("1017") ||
					billDetail.getBillMaster().getBillType().getCode().equals("4004")	){
				owpBillSendItem.setQty((billDetail.getHsAmount()==null?0.0:billDetail.getHsAmount())*(-1)
					);
			}else{
				owpBillSendItem.setQty(billDetail.getHsAmount()==null?0.0:billDetail.getHsAmount());
			}
			owpBillSendItem.setModifyMark(ModifyMarkState.ADDED);
			try{
				owpBillSendItem.setAppGNo(map.get(billDetail.getComplex().getCode()));
				owpBillSendItem.setTrNo(map1.get(billDetail.getComplex().getCode()));
			}catch(Exception e){
				e.printStackTrace();
			}
			
			//保存所新增的出口登记表
			try{
				this.owpBillDao.saveOwpBillSendItem(owpBillSendItem);
			}catch(Exception e){
				e.printStackTrace();
			}
			OwpBillAndBillDetail owpBillAndBillDetail = new OwpBillAndBillDetail();
			owpBillAndBillDetail.setBill(billDetail.getId());
			if(null!=billDetail.getBillMaster().getBillType()){
				owpBillAndBillDetail.setBillTypeCode(billDetail.getBillMaster().getBillType().getCode());
				owpBillAndBillDetail.setProduceType(billDetail.getBillMaster().getBillType().getProduceType()==null?
						"" : billDetail.getBillMaster().getBillType().getProduceType().toString());
			}
			owpBillAndBillDetail.setOwpBillSendItem(owpBillSendItem);
			owpBillAndBillDetail.setIsOut("out");
			//保存所新增的登记表和单据关联关系
			try{
				this.owpBillDao.savaOwpBillAndBillDetail(owpBillAndBillDetail);
			}catch(Exception e){
				e.printStackTrace();
			}
			owpBillSendItemList.add(owpBillSendItem);
		}
		return owpBillSendItemList;
		
	}
	
	/**
	 * 来源于单据中心的进货登记表体资料进行保存
	 * @param owpBillSendHead 外发加工进货登记表头
	 * @param list 申请表已选择商品LIST
	 * @return
	 * @author sxk
	 */
	public List addBillDetailRecvItem(OwpBillRecvHead owpBillRecvHead,List list){
		List<OwpBillRecvItem> owpBillRecvItemList = new ArrayList<OwpBillRecvItem>();
		
		int billMaxNo = this.owpBillDao.findBillRecvMaxNo(owpBillRecvHead.getBillNo());
		int index = 0 ;
		//查找申请表是否有改商品、实际在SQL语句中判断更好
		List<OwpAppRecvItem> list1 = this.owpBillDao.findOwpRecvItemByHead(owpBillRecvHead.getAppNo());
		
		Map<String,Integer> map = new HashMap<String,Integer>();
		Map<String,Integer> map1 = new HashMap<String,Integer>();

		if(null != list1 && list1.size()>0){
			for(OwpAppRecvItem owpAppRecvItem : list1){
				map.put(owpAppRecvItem.getComplex().getCode(), owpAppRecvItem.getSeqNum());
				map1.put(owpAppRecvItem.getComplex().getCode(), owpAppRecvItem.getTrNo());
			}
		}
		for(int i = 0;i<list.size();i++){
			index++;
			BillDetail billDetail =(BillDetail) list.get(i);
			
			OwpBillRecvItem owpBillRecvItem = new OwpBillRecvItem();
			owpBillRecvItem.setHead(owpBillRecvHead);
			owpBillRecvItem.setListNo(billMaxNo+index);
			owpBillRecvItem.setComplex(billDetail.getComplex());
			owpBillRecvItem.setHsName(billDetail.getHsName());
			owpBillRecvItem.setHsSpec(billDetail.getHsSpec());
			owpBillRecvItem.setHsUnit(billDetail.getHsUnit());
			if(billDetail.getBillMaster().getBillType().getCode().equals("2113") ||
					billDetail.getBillMaster().getBillType().getCode().equals("4104")	){
			owpBillRecvItem.setQty((billDetail.getHsAmount()==null?0.0:billDetail.getHsAmount())*(-1)
					);
			}else{
				owpBillRecvItem.setQty(billDetail.getHsAmount()==null?0.0:billDetail.getHsAmount());
			}
			owpBillRecvItem.setModifyMark(ModifyMarkState.ADDED);
			try{
				owpBillRecvItem.setAppGNo(map.get(billDetail.getComplex().getCode()));
				owpBillRecvItem.setTrNo(map1.get(billDetail.getComplex().getCode()));
			}catch(Exception e){
				e.printStackTrace();
			}
			//保存所新增的出口登记表
			try{
				this.owpBillDao.saveOwpBillRecvItem(owpBillRecvItem);
			}catch(Exception e){
				e.printStackTrace();
			}
			OwpBillAndBillDetail owpBillAndBillDetail = new OwpBillAndBillDetail();
			owpBillAndBillDetail.setBill(billDetail.getId());
			if(null!=billDetail.getBillMaster().getBillType()){
				owpBillAndBillDetail.setBillTypeCode(billDetail.getBillMaster().getBillType().getCode());
				owpBillAndBillDetail.setProduceType(billDetail.getBillMaster().getBillType().getProduceType()==null?
						"" : billDetail.getBillMaster().getBillType().getProduceType().toString());
			}
			owpBillAndBillDetail.setOwpBillRecvItem(owpBillRecvItem);
			owpBillAndBillDetail.setIsOut("in");
			//保存所新增的登记表和单据关联关系
			try{
				this.owpBillDao.savaOwpBillAndBillDetail(owpBillAndBillDetail);
			}catch(Exception e){
				e.printStackTrace();
			}
			owpBillRecvItemList.add(owpBillRecvItem);
		}
		return owpBillRecvItemList;
		
	}
	/**
	 * 外发加工出货登记表回执处理
	 * @param owpBillSendHead 外发加工出货登记表表头
	 * @param existOwpBillSendHead 变更前存在的外发加工出货登记表表头
	 * @return
	 */
	public String processOwpBillSendHead(final OwpBillSendHead owpBillSendHead,
			final OwpBillSendHead existOwpBillSendHead, List lsReturnFile) {
		String copBillNo = "";
		copBillNo = owpBillSendHead.getCopBillNo();
		String result =owpMessageLogic.processMessage(OwpBusinessType.OWP_BILL_SEND,
				copBillNo, new CspProcessMessage() {
			
					public void failureHandling(
							TempCspReceiptResultInfo tempReceiptResult) {
						resetOwpBillSendHead(owpBillSendHead, existOwpBillSendHead);
					}

					public void successHandling(
							TempCspReceiptResultInfo tempReceiptResult) {
						CspReceiptResult receiptResult = tempReceiptResult
								.getReceiptResult();
						effectiveOwpBillSendHead(owpBillSendHead, existOwpBillSendHead,
								receiptResult);
					}
				}, lsReturnFile);
		return  result;
	}
	/**
	 * 外发加工收货登记表回执处理
	 * 
	 * @param owpBillRecvHead 外发加工收货登记表表头
	 * @param existOwpBillRecvHead 变更前存在的外发加工收货登记表表头 
	 * @return
	 */
	public String processOwpBillRecvHead(final  OwpBillRecvHead owpBillRecvHead,
			final OwpBillRecvHead existOwpBillRecvHead, List lsReturnFile) {
		String copBillNo = "";
		copBillNo = owpBillRecvHead.getCopBillNo();
		return owpMessageLogic.processMessage(OwpBusinessType.OWP_BILL_RECV,
				copBillNo, new CspProcessMessage() {
			
					public void failureHandling(
							TempCspReceiptResultInfo tempReceiptResult) {
						resetOwpBillRecvHead(owpBillRecvHead, existOwpBillRecvHead);
					}

					public void successHandling(
							TempCspReceiptResultInfo tempReceiptResult) {
						CspReceiptResult receiptResult = tempReceiptResult
								.getReceiptResult();
						effectiveOwpBillRecvHead(owpBillRecvHead, existOwpBillRecvHead,
								receiptResult);
					}
				}, lsReturnFile);
	}
	/**
	 * 重设外发加工出货登记表表头
	 * @param OwpBillSendHead 外发加工出货登记表表头
	 * @param existOwpBillSendHead 变更前存在的外发加工出货登记表表头
	 * @author sxk
	 */
	private void resetOwpBillSendHead(OwpBillSendHead owpBillSendHead,
			OwpBillSendHead existOwpBillSendHead) {
		if (existOwpBillSendHead == null) {
			owpBillSendHead.setDeclareState(DeclareState.APPLY_POR);
		} else {
			owpBillSendHead.setDeclareState(DeclareState.CHANGING_EXE);
		}
		this.owpBillDao.saveOrUpdate(owpBillSendHead);
	}
	/**
	 * 重设外发加工收货登记表表头
	 * @param OwpBillRecvHead 外发加工进货登记表表头
	 * @param existOwpBillRecvHead 变更前存在的外发加工进货登记表表头
	 * @author sxk 
	 */
	private void resetOwpBillRecvHead(OwpBillRecvHead owpBillRecvHead,
			OwpBillRecvHead existOwpBillRecvHead) {
		if (existOwpBillRecvHead == null) {
			owpBillRecvHead.setDeclareState(DeclareState.APPLY_POR);
		} else {
			owpBillRecvHead.setDeclareState(DeclareState.CHANGING_EXE);
		}
		this.owpBillDao.saveOrUpdate(owpBillRecvHead);
	}
	
	/**
	 * 处理回执
	 * @param owpBillSendHead 外发加工出货登记表表头
	 * @author sxk
	 */
	private void effectiveOwpBillSendHead(OwpBillSendHead owpBillSendHead,
			OwpBillSendHead existOwpBillSendHead,CspReceiptResult receiptResult) {
		Map<Integer, Complex> mapExg = new HashMap<Integer, Complex>();
		owpBillSendHead = this.owpBillDao.findOwpBillSendHeadById(owpBillSendHead.getId());
		if (existOwpBillSendHead != null) {
			this.deleteOwpSendBillHead(existOwpBillSendHead);
		} else {// 第一次申报的时候根据回执反写统一编号和发货单编号，变更的时候不反写 
			if (receiptResult != null) {
				// <APP_NO> 发货单编号 X(12) 不填
				owpBillSendHead.setBillNo(((OwpReceiptResult) receiptResult)
						.getEmsNo());
				// 电子口岸统一编号
				owpBillSendHead.setSeqNo(receiptResult.getSeqNo());
			}
		}
		owpBillSendHead.setDeclareState(DeclareState.PROCESS_EXE);
		owpBillSendHead.setModifyMark(ModifyMarkState.UNCHANGE);
		this.owpBillDao.saveOrUpdate(owpBillSendHead);
	}
	/**
	 * @param owpBillSendHead登记表表头
	 * @author sxk
	 */
	private void effectiveOwpBillRecvHead(OwpBillRecvHead owpBillRecvHead,
			OwpBillRecvHead existOwpBillRecvHead,CspReceiptResult receiptResult) {
		Map<Integer, Complex> mapExg = new HashMap<Integer, Complex>();
		owpBillRecvHead = this.owpBillDao.findOwpBillRecvHeadById(owpBillRecvHead.getId());
		if (existOwpBillRecvHead != null) {
			this.deleteOwpRecvBillHead(existOwpBillRecvHead);
		} else {// 第一次申报的时候根据回执反写统一编号和发货单编号，变更的时候不反写 
			if (receiptResult != null) {
				// <Bill_NO> 发货单编号 X(12) 不填
				owpBillRecvHead.setBillNo(((OwpReceiptResult) receiptResult)
						.getEmsNo());
				// 电子口岸统一编号
				owpBillRecvHead.setSeqNo(receiptResult.getSeqNo());
			}
		}
		owpBillRecvHead.setDeclareState(DeclareState.PROCESS_EXE);
		owpBillRecvHead.setModifyMark(ModifyMarkState.UNCHANGE);
		this.owpBillDao.saveOrUpdate(owpBillRecvHead);
	}
	/**
	 * 依组合条件查询外发加工出货登记表表头
	 * @param list 查询条件
	 * @return
	 * @author sxk
	 */
	public List findOwpBillSendHeadByConditions(List conditions){
		String orderBy = "order by a.seqNum ASC ";
		return owpBillDao.commonSearch("", "OwpBillSendHead",
				conditions, "", "", orderBy);
	}
	/**
	 * 依组合条件查询外发加工收货登记表表头
	 * @param list 查询条件
	 * @return
	 * @author sxk
	 */
	public List findOwpBillRecvHeadByConditions(List conditions){
		String orderBy = "order by a.seqNum ASC ";
		return owpBillDao.commonSearch("", "OwpBillRecvHead",
				conditions, "", "", orderBy);
	}
}
