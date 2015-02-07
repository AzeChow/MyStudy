/*
 * Created on 2004-8-5
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.owp.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.Request;
import com.bestway.common.constant.AppClass;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.FptBusinessType;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.OwpBusinessType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.common.fpt.entity.FptAppItem;
import com.bestway.common.fpt.entity.FptReceiptResult;
import com.bestway.common.message.entity.CspReceiptResult;
import com.bestway.common.message.entity.CspSignInfo;
import com.bestway.common.message.entity.TempCspReceiptResultInfo;
import com.bestway.common.message.logic.CspProcessMessage;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.owp.dao.OwpAppDao;
import com.bestway.owp.entity.OwpAppHead;
import com.bestway.owp.entity.OwpAppRecvItem;
import com.bestway.owp.entity.OwpAppSendItem;
import com.bestway.owp.entity.OwpReceiptResult;
import com.bestway.owp.entity.OwpSignInfo;
import com.bestway.owp.entity.TempOwpAppRecvItem;
import com.bestway.owp.entity.TempOwpAppSendItem;

/**
 * 外发加工申请表logic
 * @author wss
 */
public class OwpAppLogic {

	/**
	 * 外发加工申请表dao
	 */
	private OwpAppDao owpAppDao;
	
	/**
	 * 外发加工信息logic
	 */
	private OwpMessageLogic owpMessageLogic;
	
	/**
	 * 外发加工申请表dao
	 * @return
	 */
	OwpAppDao getOwpAppDao() {
		return owpAppDao;
	}

	/**
	 * 外发加工申请表dao
	 * @param owpDao
	 */
	public void setOwpAppDao(OwpAppDao owpDao) {
		this.owpAppDao = owpDao;
	}	

	/**
	 * 外发加工信息logic
	 * @return
	 */
	public OwpMessageLogic getOwpMessageLogic() {
		return owpMessageLogic;
	}

	/**
	 * 外发加工信息logic
	 * @param owpMessageLogic
	 */
	public void setOwpMessageLogic(OwpMessageLogic owpMessageLogic) {
		this.owpMessageLogic = owpMessageLogic;
	}

	/**
	 * 新增外发加工申请表表头
	 * @return OwpAppHead 外发加工申请表表头
	 * @author wss
	 */
	public OwpAppHead addOwpAppHead() {
		OwpAppHead head = new OwpAppHead();
		
		//自动产生流水号
		head.setSeqNum(Integer.valueOf(owpAppDao.getNum("OwpAppHead",
				"seqNum")));
		//当前公司
		Company com = this.owpAppDao.findCompany();
		StringBuffer stringBuffer = new StringBuffer();
		if (com.getBuCode() == null || "".equals(com.getBuCode())) {
			stringBuffer.append("公司的经营单位编码不能为空\r\n");
		}
		if (com.getBuName() == null || "".equals(com.getBuName())) {
			stringBuffer.append("公司的经营单位名称不能为空\r\n");
		}
		if (com.getCode() == null || "".equals(com.getCode())) {
			stringBuffer.append("公司的加工单位编码不能为空\r\n");
		}
		if (com.getName() == null || "".equals(com.getName())) {
			stringBuffer.append("公司的加工单位名称不能为空\r\n");
		}
		if (!stringBuffer.toString().trim().equals("")) {
			throw new RuntimeException(stringBuffer.toString());
		}
		head.setTradeCode(com.getCode());//委托方企业代码
		head.setTradeName(com.getName());//委托方企业名称
		head.setMastCust(com.getMasterCustoms());//报关海关
		
		//委托企业联系人/联系电话
		head.setCorp(com.getOwner() == null ? "" :com.getOwner() + com.getTel() == null ? "":com.getOwner());
		
		head.setDeclareState(DeclareState.APPLY_POR);//申报状态
		head.setModifyMark(ModifyMarkState.ADDED);//修改标志
		head.setDeclareType(DelcareType.APPLICATION);//申报类型
		head.setAppClass(AppClass.G);//申请表类型默认为G.外发加工
		
		head.setCopAppNo(getCopAppNo());//企业内部编码
		
		head.setCompany(CommonUtils.getCompany());//公司
		
		head.setCreatePeople(CommonUtils.getAclUser().getUserName());//创建人
		head.setCreateDate(new Date());//创建日期
		
		head.setInputMan(CommonUtils.getAclUser().getUserName());//录入人
		head.setInputDate(new Date());//录入日期
		
		this.owpAppDao.saveOrUpdate(head);
		
		return head;
		
	}
	
	/**
	 *  自动生成外发加工申请表内部编号(20位的字符串)
	 * @return
	 * @author wss
	 */
	public String getCopAppNo(){
		Company company = (Company) CommonUtils.getCompany();
		String tradeCode = company.getCode();
		if (tradeCode == null || "".equals(tradeCode.trim())) {
			throw new RuntimeException("请在公司资料设定里面先设定好加工单位和经营单位");
		}
		
		String prefix = tradeCode + "O" + OwpBusinessType.OWP_APP;

		String maxCopEntNo = "";
		List<Object> parameters = new ArrayList<Object>();
		String hql =
				" select max(a.copAppNo) from OwpAppHead a "
				+ " where a.company.id= ? " 
				+ " and a.copAppNo like ? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(prefix + "%");
		
		List list = this.owpAppDao.find(hql, parameters.toArray());
		
		if (list.size() > 0 && list.get(0) != null) {
			maxCopEntNo = list.get(0).toString().trim();
		}
		
				
		String newCopEntNO = "";
		if ("".equals(maxCopEntNo.trim())) {
			newCopEntNO = prefix + CommonUtils.convertIntToStr(1, 8);
		} else {
			int maxNo = 0;
			String serialNo = maxCopEntNo.substring(prefix.length());
			try {
				maxNo = Integer.parseInt(serialNo);
			} catch (Exception ex) {
				throw new RuntimeException("在获取OwpAppHead最大内部编号出错，"
						+ serialNo + "不是一个有效的整数");
			}
			newCopEntNO = prefix + CommonUtils.convertIntToStr(maxNo + 1, 8);
		}
		return newCopEntNO;
	}
	
	/**
	 * 删处备案资料库表头
	 * @param head
	 * @author wss
	 */
	public void deleteOwpAppHead(OwpAppHead head) {
		this.owpAppDao.deleteAll(this.owpAppDao
				.findOwpAppSendItemByHeadId(head.getId()));
		this.owpAppDao.deleteAll(this.owpAppDao
				.findOwpAppRecvItemByHeadId(head.getId()));
		this.owpAppDao.delete(head);
	}
	
	/**
	 * 获取外发加工申请表的一些信息 包括申请表编号、承揽方企业代码、承揽方企业名称
	 * @param request
	 * @return
	 * @author wss
	 */
	public List findOwpAppHeadInfos(){
		List<TempObject> lsResult = new ArrayList<TempObject>();
		List<Object[]> list = this.owpAppDao.find(
				"select a.appNo,a.inTradeCode,a.inTradeName "
				+ " from OwpAppHead a " 
				+ " where a.company.id = ? ",new Object[] {CommonUtils.getCompany().getId()});
		
		TempObject temp;
		Object[] objs;
		
		if(list != null && list.size() >= 0){
			for(int i=0;i<list.size();i++){
				objs = (Object[])list.get(i);
				temp = new TempObject();
				temp.setObject(objs[0] == null ? "":(String)objs[0]);
				temp.setObject1(objs[1] == null ? "":(String)objs[1]);
				temp.setObject2(objs[2] == null ? "":(String)objs[2]);
				lsResult.add(temp);
			}
		}
		
		return lsResult;
		
	}

	/**
	 * 依组合条件查询外发加工申请表表头
	 * @param list
	 * @return
	 * @author wss
	 */
	public List<OwpAppHead> findOwpAppHeadByConditions(List<Condition> conditions){
		String orderBy = "order by a.seqNum ASC ";
		
//		String leftOuter = " left join fetch a.billMaster left join fetch a.wareSet  ";
		
		return owpAppDao.commonSearch("", "OwpAppHead",
				conditions, "", "", orderBy);
	}


	
	/**
	 * 导入外发加工申请表 外发货物
	 * @param request
	 * @param ls
	 * @param isOverWrite
	 * @author wss
	 */
	public List importOwpAppSendItemFromTxtFile(List ls, boolean isOverwrite){

		OwpAppHead head = null;
		if (ls.size() > 0) {
			head = ((TempOwpAppSendItem) ls.get(0)).getOwpAppSendItem()
					.getHead();
		} else {
			return null;
		}
		// 先获取已有的外发数据
		List lsSend = this.owpAppDao.findOwpAppSendItemByHeadId(head.getId());
		
		
		Map<Integer, OwpAppSendItem> mapTrNoExistedSendItem = new HashMap<Integer, OwpAppSendItem>();
		Map<String, OwpAppSendItem> mapHsInfoExistedSendItem = new HashMap<String, OwpAppSendItem>();
		List<OwpAppSendItem> lsOKItems  = new ArrayList<OwpAppSendItem>();
		List<TempOwpAppSendItem> lsErrorItems = new ArrayList<TempOwpAppSendItem>();
		
		
		for (int i = 0; i < lsSend.size(); i++) {
			OwpAppSendItem item = (OwpAppSendItem) lsSend.get(i);
			mapTrNoExistedSendItem.put(item.getTrNo(), item);//将手册序号 与 已有的外发数据 关联
			
			String key = item.getComplex().getCode() + "/" //编码
						+ item.getHsName() + "/"           //名称
						+ item.getHsSpec() + "/"           //规格
						+ item.getHsName();                //单位
			
			mapHsInfoExistedSendItem.put(key,item);//将资料信息 与 已有的外发数据 关联
		}
		
		
		//遍历每一条资料
		for (int i = 0; i < ls.size(); i++) {
			
			TempOwpAppSendItem tempItem = (TempOwpAppSendItem) ls.get(i);
			OwpAppSendItem sendItem = tempItem.getOwpAppSendItem();
			OwpAppSendItem existedItem;
			
			//如果导入的资料没有【手册序号】
			if(sendItem.getTrNo() == null ){
				String key = sendItem.getComplex().getCode() + "/" //编码
								+ sendItem.getHsName() + "/"           //名称
								+ sendItem.getHsSpec() + "/"           //规格
								+ sendItem.getHsName();                //单位
				
				//判断是否已经存在相同【编码、名称、规格、单位】的资料
				existedItem = mapHsInfoExistedSendItem.get(key);
				
				//如果不存在,则可以保存
				if(existedItem == null){
					sendItem.setModifyMark(ModifyMarkState.ADDED);	
					sendItem.setCompany(CommonUtils.getCompany());
					
					lsOKItems.add(sendItem);
				}
				//如果存在
				else{
					if(isOverwrite){//是否覆盖
						existedItem.setQty(sendItem.getQty());
						existedItem.setNote(sendItem.getNote());

						if (ModifyMarkState.UNCHANGE
								.equals(existedItem.getModifyMark())) {
							existedItem.setModifyMark(ModifyMarkState.MODIFIED);
						}
						lsOKItems.add(existedItem);
					}else{
						tempItem.setErrinfo("资料库中已经存在相同[编码、名称、规格、单位]的资料！");
						lsErrorItems.add(tempItem);
					}
				}
				
			}
			//如果导入的数据含有【手册序号】
			else{
				//是否已经存在 相同【手册序号】的资料
				existedItem = mapTrNoExistedSendItem.get(sendItem.getTrNo());
				
				//如果不存在,则表示可以保存
				if(existedItem == null){
					sendItem.setModifyMark(ModifyMarkState.ADDED);	
					sendItem.setCompany(CommonUtils.getCompany());
					
					lsOKItems.add(sendItem);
				}
				//如果存在
				else{
					if (isOverwrite) {//如果覆盖导入			
						existedItem.setComplex(sendItem.getComplex());
						existedItem.setHsName(sendItem.getHsName());
						existedItem.setHsSpec(sendItem.getHsSpec());
						existedItem.setHsUnit(sendItem.getHsUnit());
						existedItem.setQty(sendItem.getQty());
						existedItem.setNote(sendItem.getNote());

						if (ModifyMarkState.UNCHANGE
								.equals(existedItem.getModifyMark())) {
							existedItem.setModifyMark(ModifyMarkState.MODIFIED);
						}
						lsOKItems.add(existedItem);
					} else {
						tempItem.setErrinfo("资料库中已经存在相同[手册序号]的资料！");
						lsErrorItems.add(tempItem);
					}
				}
			}
			
		}
			
					
		//没有错误信息则保存数据
		if(lsErrorItems.size() <= 0 ){
			for(int i=0;i<lsOKItems.size();i++){
				if(lsOKItems.get(i).getSeqNum() == null){
					lsOKItems.get(i).setSeqNum(getMaxSendListNo(head));
				}
				this.owpAppDao.saveOrUpdate(lsOKItems.get(i));
			}
			return null;
		}
		//有错误信息则返回错误
		else{
			return lsErrorItems;
		}

	
	}
	
	
	/**
	 * 导入外发加工申请表 收回货物
	 * @param request
	 * @param ls
	 * @param isOverWrite
	 * @author wss
	 */
	public List importOwpAppRecvItemFromTxtFile(List ls, boolean isOverwrite){

		OwpAppHead head = null;
		if (ls.size() > 0) {
			head = ((TempOwpAppRecvItem) ls.get(0)).getOwpAppRecvItem()
					.getHead();
		} else {
			return null;
		}
		// 先获取已有的外发数据
		List lsRecv = this.owpAppDao.findOwpAppRecvItemByHeadId(head.getId());
		
		
		Map<Integer, OwpAppRecvItem> mapTrNoExistedRecvItem = new HashMap<Integer, OwpAppRecvItem>();
		Map<String, OwpAppRecvItem> mapHsInfoExistedRecvItem = new HashMap<String, OwpAppRecvItem>();
		List<OwpAppRecvItem> lsOKItems  = new ArrayList<OwpAppRecvItem>();
		List<TempOwpAppRecvItem> lsErrorItems = new ArrayList<TempOwpAppRecvItem>();
		
		
		for (int i = 0; i < lsRecv.size(); i++) {
			OwpAppRecvItem item = (OwpAppRecvItem) lsRecv.get(i);
			mapTrNoExistedRecvItem.put(item.getTrNo(), item);//将手册序号 与 已有的外发数据 关联
			
			String key = item.getComplex().getCode() + "/" //编码
						+ item.getHsName() + "/"           //名称
						+ item.getHsSpec() + "/"           //规格
						+ item.getHsName();                //单位
			
			mapHsInfoExistedRecvItem.put(key,item);//将资料信息 与 已有的外发数据 关联
		}
		
		
		//遍历每一条资料
		for (int i = 0; i < ls.size(); i++) {
			
			TempOwpAppRecvItem tempItem = (TempOwpAppRecvItem) ls.get(i);
			OwpAppRecvItem recvItem = tempItem.getOwpAppRecvItem();
			OwpAppRecvItem existedItem;
			
			//如果导入的资料没有【手册序号】
			if(recvItem.getTrNo() == null ){
				String key = recvItem.getComplex().getCode() + "/" //编码
								+ recvItem.getHsName() + "/"           //名称
								+ recvItem.getHsSpec() + "/"           //规格
								+ recvItem.getHsName();                //单位
				
				//判断是否已经存在相同【编码、名称、规格、单位】的资料
				existedItem = mapHsInfoExistedRecvItem.get(key);
				
				//如果不存在,则可以保存
				if(existedItem == null){
					recvItem.setModifyMark(ModifyMarkState.ADDED);	
					recvItem.setCompany(CommonUtils.getCompany());
					
					lsOKItems.add(recvItem);
				}
				//如果存在
				else{
					if(isOverwrite){//是否覆盖
						existedItem.setQty(recvItem.getQty());
						existedItem.setNote(recvItem.getNote());

						if (ModifyMarkState.UNCHANGE
								.equals(existedItem.getModifyMark())) {
							existedItem.setModifyMark(ModifyMarkState.MODIFIED);
						}
						lsOKItems.add(existedItem);
					}else{
						tempItem.setErrinfo("资料库中已经存在相同[编码、名称、规格、单位]的资料！");
						lsErrorItems.add(tempItem);
					}
				}
				
			}
			//如果导入的数据含有【手册序号】
			else{
				//是否已经存在 相同【手册序号】的资料
				existedItem = mapTrNoExistedRecvItem.get(recvItem.getTrNo());
				
				//如果不存在,则表示可以保存
				if(existedItem == null){
					recvItem.setModifyMark(ModifyMarkState.ADDED);	
					recvItem.setCompany(CommonUtils.getCompany());
					
					lsOKItems.add(recvItem);
				}
				//如果存在
				else{
					if (isOverwrite) {//如果覆盖导入			
						existedItem.setComplex(recvItem.getComplex());
						existedItem.setHsName(recvItem.getHsName());
						existedItem.setHsSpec(recvItem.getHsSpec());
						existedItem.setHsUnit(recvItem.getHsUnit());
						existedItem.setQty(recvItem.getQty());
						existedItem.setNote(recvItem.getNote());

						if (ModifyMarkState.UNCHANGE
								.equals(existedItem.getModifyMark())) {
							existedItem.setModifyMark(ModifyMarkState.MODIFIED);
						}
						lsOKItems.add(existedItem);
					} else {
						tempItem.setErrinfo("资料库中已经存在相同[手册序号]的资料！");
						lsErrorItems.add(tempItem);
					}
				}
			}
			
		}
			
					
		//没有错误信息则保存数据
		if(lsErrorItems.size() <= 0 ){
			for(int i=0;i<lsOKItems.size();i++){
				if(lsOKItems.get(i).getTrNo() == null){
					lsOKItems.get(i).setTrNo(getMaxRecvListNo(head));
				}
				this.owpAppDao.saveOrUpdate(lsOKItems.get(i));
			}
			return null;
		}
		//有错误信息则返回错误
		else{
			return lsErrorItems;
		}

	
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
	public List findBcsContractDetailByProcessExe(String headId,String emsNo,Boolean isOut){

		List<Object> paramters = new ArrayList<Object>();
		String hsql = "";
		//
		// 进口是 is materiel
		//
		if (isOut) {
			hsql += "select a from  ContractImg a  left join  a.contract b"
					+ "  where a.company= ? " + "	and b.declareState in ( ?) ";
		} else {
			hsql += "select a from  ContractExg a  left join  a.contract b"
					+ "  where a.company= ? " + "	and b.declareState in ( ?) ";
		}
		paramters.add(CommonUtils.getCompany());
		paramters.add(DeclareState.PROCESS_EXE);//正在执行的合同


		if (emsNo != null && !"".equals(emsNo)) {
			hsql += " and b.emsNo = ? ";
			paramters.add(emsNo);
		}
		
		// 过滤已存在的
		if (headId != null && !"".equals(headId)) {
			if (isOut) {
				hsql += " and a.seqNum not in "
						+ "( select c.trNo from OwpAppSendItem c where c.head.id=? and c.trNo is not null) ";
				paramters.add(headId);
			} else {
				hsql += " and a.seqNum not in "
						+ "( select c.trNo from OwpAppRecvItem c where c.head.id=? and c.trNo is not null) ";
				paramters.add(headId);
			}
		}

			hsql += " order by b.emsNo ,a.seqNum ";
			
		
		System.out.println("wss hsql = " + hsql);
		return this.owpAppDao.find(hsql, paramters.toArray());
	
	}
	
	/**
	 * 查找通关手册备案中的进出口货物信息
	 * @param headId 外发加工申请表表头id
	 * @param emsNo 手册号
	 * @param isOut是否是料件
	 * @return
	 * @author wss
	 */
	public List findBcusContractDetailByProcessExe(String headId,String emsNo,Boolean isOut){
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "";
		if (isOut) {
			hsql += "select a from  EmsHeadH2kImg a  left join  a.emsHeadH2k b"
					+ "  where a.company= ? and (a.isForbid is null or a.isForbid = ? ) ";
			paramters.add(CommonUtils.getCompany());
			paramters.add(false);
		} else {
			hsql += "select a from  EmsHeadH2kExg a  left join  a.emsHeadH2k b"
					+ "  where a.company= ? ";
			paramters.add(CommonUtils.getCompany());
		}

		if ("1".equals((getBpara(BcusParameter.EmsEdiH2kSend)))) {// 分批申报
			hsql += " and a.modifyMark = ? " + " and b.historyState=? ";
			paramters.add(ModifyMarkState.UNCHANGE);
			paramters.add(false);
		} else {
			hsql += " and b.declareState = ? " + " and b.historyState=? ";
			paramters.add(DeclareState.PROCESS_EXE);
			paramters.add(false);
		}
		//
		// 是否有
		//
		if (emsNo != null && !"".equals(emsNo)) {
			hsql += " and b.emsNo = ? ";
			paramters.add(emsNo);
		}

		// 过滤已存在的
		if (headId != null && !"".equals(headId)) {
			if (isOut) {
				hsql += " and a.seqNum not in "
					+ "( select c.trNo from OwpAppSendItem c where c.head.id=? and c.trNo is not null) ";
				paramters.add(headId);
			} else {
				hsql += " and a.seqNum not in "
					+ "( select c.trNo from OwpAppRecvItem c where c.head.id=? and c.trNo is not null) ";
				paramters.add(headId);
			}
		}

		hsql += " order by b.emsNo ,a.seqNum ";

		System.out.println("wss hsql = " + hsql);
		return this.owpAppDao.find(hsql, paramters.toArray());
	}
	
	/**
	 * 返回Bcus参数设定 
	 *  @author wss
	 */
	private String getBpara(int type) {
		List list = this.owpAppDao.find(
						" select a from BcusParameter a where a.type = ? and a.company.id = ?",
						new Object[] { type, CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			BcusParameter obj = (BcusParameter) list.get(0);
			return obj.getStrValue();
		}
		return null;
	}
	/**
	 * 查找通关手册备案中的进出口货物信息
	 * @param headId 外发加工申请表表头id
	 * @param emsNo 手册号
	 * @param isOut是否是料件
	 * @return
	 * @author wss
	 */
	public List findDzscContractDetailByProcessExe(String headId,String emsNo,Boolean isOut){
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "";

		if (isOut) {
			hsql += "select a from  DzscEmsImgBill a left join  a.dzscEmsPorHead b"
					+ "  where a.company= ? "
					+ "	and b.declareState in ( ? )  ";
		} else {
			hsql += "select a from  DzscEmsExgBill a left join  a.dzscEmsPorHead b"
					+ "  where a.company= ? " + "	and b.declareState in ( ? ) ";
		}
		paramters.add(CommonUtils.getCompany());
		paramters.add(DeclareState.PROCESS_EXE);


		if (emsNo != null && !"".equals(emsNo)) {
			hsql += " and b.emsNo = ? ";
			paramters.add(emsNo);
		}


		//
		// 过滤已存在的
		//
		if (headId != null && !"".equals(headId)) {
			if (isOut) {
				hsql += " and a.seqNum not in "
						+ "( select c.trNo from OwpAppSendItem c where c.head.id=? and c.trNo is not null) ";
				paramters.add(headId);
			} else {
				hsql += " and a.seqNum not in "
						+ "( select c.trNo from OwpAppRecvItem c where c.head.id=? and c.trNo is not null) ";
				paramters.add(headId);
			}
		}
		
		hsql += " order by b.emsNo ,a.seqNum ";

		System.out.println("wss hsql = " + hsql);
		return this.owpAppDao.find(hsql, paramters.toArray());

	}
	
	/**
	 * 获取外发货物最大序列号
	 * @param head
	 * @return
	 * @author wss
	 */
	public Integer getMaxSendListNo(OwpAppHead head){
		String num = "1";
		String hsql = " select max(a.seqNum) "
					+ " from OwpAppSendItem as a " 
					+ " where a.head = ? "
					+ " and a.company = ? ";
		
		List list = owpAppDao.find(hsql,new Object[] {head,CommonUtils
				.getCompany() });
		
		if (list.get(0) != null) {
			num = list.get(0).toString();
			num = String.valueOf(Integer.parseInt(num) + 1);
		}
		return Integer.valueOf(num);
	}
	
	/**
	 * 获取收回货物最大序列号
	 * @param head
	 * @return
	 * @author wss
	 */
	public Integer getMaxRecvListNo(OwpAppHead head){
		String num = "1";
		String hsql = " select max(a.seqNum) "
					+ " from OwpAppRecvItem as a " 
					+ " where a.head = ? "
					+ " and a.company = ? ";
		
		List list = owpAppDao.find(hsql,new Object[] {head,CommonUtils
				.getCompany() });
		
		if (list.get(0) != null) {
			num = list.get(0).toString();
			num = String.valueOf(Integer.parseInt(num) + 1);
		}
		return Integer.valueOf(num);
	}
	
	
	/**
	 * 添加外发货物
	 * @param request
	 * @param owpAppHead
	 * @param list
	 * @return List<OwpAppSendItem>
	 * @author wss
	 */
	public List<OwpAppSendItem> addOwpAppSendItemFromContract(OwpAppHead owpAppHead,List list){
		
		List<OwpAppSendItem> lsAdd = new ArrayList<OwpAppSendItem>();
		
		switch(owpAppHead.getEmsType()){
		case ProjectType.BCS:
			for(int i=0;i<list.size();i++){
				ContractImg bcsImg = (ContractImg)list.get(i);
				
				
				OwpAppSendItem item = new OwpAppSendItem();
				
				item.setHead(owpAppHead);
				item.setCompany(CommonUtils.getCompany());
				item.setSeqNum(getMaxSendListNo(owpAppHead));
				item.setTrNo(bcsImg.getSeqNum());
				item.setModifyMark(ModifyMarkState.ADDED);
				
				
				item.setComplex(bcsImg.getComplex());
				
				item.setHsName(bcsImg.getName());
				item.setHsSpec(bcsImg.getSpec());
				item.setHsUnit(bcsImg.getUnit());
				item.setQty(0.0);	
				
				this.owpAppDao.saveOrUpdate(item);
				
				lsAdd.add(item);
			}
			break;
			
		case ProjectType.BCUS:
			for(int i=0;i<list.size();i++){
				EmsHeadH2kImg bcusImg = (EmsHeadH2kImg)list.get(0);
				OwpAppSendItem item = new OwpAppSendItem();
				
				item.setHead(owpAppHead);
				item.setCompany(CommonUtils.getCompany());
				item.setSeqNum(getMaxSendListNo(owpAppHead));
				item.setTrNo(bcusImg.getSeqNum());
				item.setModifyMark(ModifyMarkState.ADDED);
				
				
				item.setComplex(bcusImg.getComplex());
				
				item.setHsName(bcusImg.getName());
				item.setHsSpec(bcusImg.getSpec());
				item.setHsUnit(bcusImg.getUnit());
				item.setQty(0.0);	
				
				this.owpAppDao.saveOrUpdate(item);
				
				lsAdd.add(item);
			}
			break;
		case 2:
			
			for(int i=0;i<list.size();i++){
				
				DzscEmsImgBill dzscImg = (DzscEmsImgBill)list.get(0);
	
				OwpAppSendItem item = new OwpAppSendItem();
				
				item.setHead(owpAppHead);
				item.setCompany(CommonUtils.getCompany());
				item.setSeqNum(getMaxSendListNo(owpAppHead));
				item.setTrNo(dzscImg.getSeqNum());
				item.setModifyMark(ModifyMarkState.ADDED);
				
				
				item.setComplex(dzscImg.getComplex());
				
				item.setHsName(dzscImg.getName());
				item.setHsSpec(dzscImg.getSpec());
				item.setHsUnit(dzscImg.getUnit());
				item.setQty(0.0);	
				
				this.owpAppDao.saveOrUpdate(item);
				
				lsAdd.add(item);
			}
			break;
		}
		
		return lsAdd;
		
	}
	
	/**
	 * 添加外发货物 (从自用报关商品)
	 * @param request
	 * @param owpAppHead
	 * @param list
	 * @return List<OwpAppSendItem>
	 * @author wss
	 */
	public List<OwpAppSendItem> addOwpAppSendItemFromComplex(OwpAppHead owpAppHead,List list){
		
		List<OwpAppSendItem> lsAdd = new ArrayList<OwpAppSendItem>();
		OwpAppSendItem item;
		
		System.out.println("wss list.size = " + list.size());
		
		for(int i=0;i<list.size();i++){
			Complex c = (Complex)list.get(i);
			
			
			item = new OwpAppSendItem();
			
			item.setHead(owpAppHead);//表头
			item.setCompany(CommonUtils.getCompany());//公司
			item.setSeqNum(getMaxSendListNo(owpAppHead));//流水号
			item.setModifyMark(ModifyMarkState.ADDED);//修改标志
			item.setComplex(c);//编码
			item.setHsName(c.getName());//名称
			item.setQty(0.0);//数量
			
			this.owpAppDao.saveOrUpdate(item);//保存
			
			lsAdd.add(item);
		}
			
		System.out.println("wss lsAdd.size = " + lsAdd.size());
		return lsAdd;
		
	}
	
	/**
	 * 添加收回货物
	 * @param request
	 * @param owpAppHead
	 * @param list
	 * @return List<OwpAppRecvItem>   
	 * @author wss
	 */
	public List<OwpAppRecvItem> addOwpAppRecvItemFromContract(OwpAppHead owpAppHead,List list){
		
		List<OwpAppRecvItem> lsAdd = new ArrayList<OwpAppRecvItem>();
		
		switch(owpAppHead.getEmsType()){
		case ProjectType.BCS:
			for(int i=0;i<list.size();i++){
				ContractExg bcsExg = (ContractExg)list.get(i);
				
				
				OwpAppRecvItem item = new OwpAppRecvItem();
				
				item.setHead(owpAppHead);
				item.setCompany(CommonUtils.getCompany());
				item.setSeqNum(getMaxRecvListNo(owpAppHead));
				item.setTrNo(bcsExg.getSeqNum());
				item.setModifyMark(ModifyMarkState.ADDED);
				
				item.setComplex(bcsExg.getComplex());
				
				item.setHsName(bcsExg.getName());
				item.setHsSpec(bcsExg.getSpec());
				item.setHsUnit(bcsExg.getUnit());
				item.setQty(0.0);	
				
				this.owpAppDao.saveOrUpdate(item);
				
				lsAdd.add(item);
			}
			break;
			
		case ProjectType.BCUS:
			for(int i=0;i<list.size();i++){
				EmsHeadH2kExg bcusExg = (EmsHeadH2kExg)list.get(i);
				OwpAppRecvItem item = new OwpAppRecvItem();
				
				item.setHead(owpAppHead);
				item.setCompany(CommonUtils.getCompany());
				item.setSeqNum(getMaxRecvListNo(owpAppHead));
				item.setTrNo(bcusExg.getSeqNum());
				item.setModifyMark(ModifyMarkState.ADDED);
				
				item.setComplex(bcusExg.getComplex());
				
				item.setHsName(bcusExg.getName());
				item.setHsSpec(bcusExg.getSpec());
				item.setHsUnit(bcusExg.getUnit());
				item.setQty(0.0);	
				
				this.owpAppDao.saveOrUpdate(item);
				
				lsAdd.add(item);
			}
			break;
		case 2:
			
			for(int i=0;i<list.size();i++){
				
				DzscEmsExgBill dzscExg = (DzscEmsExgBill)list.get(i);
	
				OwpAppRecvItem item = new OwpAppRecvItem();
				
				item.setHead(owpAppHead);
				item.setCompany(CommonUtils.getCompany());
				item.setSeqNum(getMaxRecvListNo(owpAppHead));
				item.setTrNo(dzscExg.getSeqNum());
				item.setModifyMark(ModifyMarkState.ADDED);
				
				item.setComplex(dzscExg.getComplex());
				
				item.setHsName(dzscExg.getName());
				item.setHsSpec(dzscExg.getSpec());
				item.setHsUnit(dzscExg.getUnit());
				item.setQty(0.0);	
				
				this.owpAppDao.saveOrUpdate(item);
				
				lsAdd.add(item);
			}
			break;
		}
		
		return lsAdd;
		
	}

	
	/**
	 * 添加收回货物 (从自用报关商品)
	 * @param request
	 * @param owpAppHead
	 * @param list
	 * @return List<OwpAppSendItem>
	 * @author wss
	 */
	public List<OwpAppRecvItem> addOwpAppRecvItemFromComplex(OwpAppHead owpAppHead,List list){
		
		List<OwpAppRecvItem> lsAdd = new ArrayList<OwpAppRecvItem>();
		OwpAppRecvItem item;
		
		for(int i=0;i<list.size();i++){
			Complex c = (Complex)list.get(i);
			
			
			 item = new OwpAppRecvItem();
			
			item.setHead(owpAppHead);
			item.setCompany(CommonUtils.getCompany());
			item.setSeqNum(getMaxRecvListNo(owpAppHead));
			item.setModifyMark(ModifyMarkState.ADDED);
			item.setComplex(c);
			item.setHsName(c.getName());
			item.setQty(0.0);	
			
			this.owpAppDao.saveOrUpdate(item);
			
			lsAdd.add(item);
		}
			
		return lsAdd;
		
	}
	
	/**
	 * 外发加工申请单报文生成
	 * @param head
	 * @return DeclareFileInfo
	 * @author wss
	 */
	public DeclareFileInfo declareOwpAppHead(OwpAppHead head, String taskId) {
		
		head = this.owpAppDao.findOwpAppHeadById(head.getId());
		Map<String, List> hmData = new HashMap<String, List>();
		List<CspSignInfo> lsSignInfo = new ArrayList<CspSignInfo>();
		
		List<OwpAppHead> lsOwpHead = new ArrayList<OwpAppHead>();
		List<OwpAppSendItem> lsOwpSendItem = new ArrayList<OwpAppSendItem>();
		List<OwpAppRecvItem> lsOwpRecvItem = new ArrayList<OwpAppRecvItem>();
		
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("正在获取要申报的资料");
		}
		OwpSignInfo signInfo = (OwpSignInfo)owpMessageLogic.getCspPtsSignInfo(info, null);
		System.out.println("wss singInfo = null ? " + (signInfo == null ));

			
		signInfo.setCopNo(head.getCopAppNo());//企业内部编码

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
		
		head.setAppDate(new Date());//申报日期
			

		head.setDeclareState(DeclareState.WAIT_EAA);
		lsOwpHead.add(head);
		
		lsOwpSendItem = this.owpAppDao.findOwpAppSendItemsStateChanged(head.getId());
		lsOwpRecvItem = this.owpAppDao.findOwpAppRecvItemsStateChanged(head.getId());

		String formatFile = "OwpAppFormat.xml";// 转入

		hmData.put("OwpAppSign", lsSignInfo);
		hmData.put("OwpAppHead", lsOwpHead);
		hmData.put("OwpAppSendItem", lsOwpSendItem);
		hmData.put("OwpAppRecvItem", lsOwpRecvItem);
		
		DeclareFileInfo fileInfo = owpMessageLogic.exportMessage(formatFile,
				hmData, info);
		
		this.owpAppDao.saveOrUpdate(head);//保存
		return fileInfo;
	}
	
	
	/**
	 * 变更外发加工申请单 （返回null表示不能变更， 否则 就变更一条新的记录）
	 * @param owpAppHead申请单表头
	 * @return owpAppHead 变更的申请单表头
	 * @author wss
	 */
	public OwpAppHead changingOwpAppHead(OwpAppHead owpAppHead){
		
		//拷贝一份
		OwpAppHead head = new OwpAppHead();
		try {
			PropertyUtils.copyProperties(head, owpAppHead);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		/**
		 * 申请单申报状态必须是正在执行的
		 */
		String declareState = head.getDeclareState();
		if (!DeclareState.PROCESS_EXE.equals(declareState)) {
			return null;
		}
		
		
		//存在已变更的记录  则不能变更
		OwpAppHead owpAppHeadTemp = this.owpAppDao.findOwpAppHeadByInTradeCode(
					owpAppHead.getInTradeCode(), DeclareState.CHANGING_EXE);
		if (owpAppHeadTemp != null) {
			return null;
		}

		// 存在已等待审批的记录  则不能变更
		OwpAppHead owpAppHeadTempa = this.owpAppDao.findOwpAppHeadByInTradeCode(
				owpAppHead.getInTradeCode(), DeclareState.WAIT_EAA);

		if (owpAppHeadTempa != null) {
			return null;
		}
		/**
		 * 申请单表头Id
		 */
		String owpAppHeadId = head.getId();
		
		/**
		 * 生成新的申请单表头
		 */
		head.setId(null);
		head.setDeclareState(DeclareState.CHANGING_EXE);
		head.setDeclareType(DelcareType.MODIFY);
		this.owpAppDao.saveOrUpdate(head);

		/**
		 * 查找申请单外发货物
		 */
		List owpAppSendItems = this.owpAppDao.findOwpAppSendItemByHeadId(owpAppHeadId);
		
		for (int j = 0; j < owpAppSendItems.size(); j++) {
			OwpAppSendItem item = new OwpAppSendItem();
			try {
				PropertyUtils.copyProperties(item,
						(OwpAppSendItem) owpAppSendItems.get(j));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

			//转抄外发货物
			item.setId(null);
			item.setModifyMark(ModifyMarkState.UNCHANGE);
			item.setHead(head);
			this.owpAppDao.saveOrUpdate(item);
			
		}
		
		/**
		 * 查找申请单收回货物
		 */
		List owpAppRecvItems = this.owpAppDao.findOwpAppRecvItemByHeadId(owpAppHeadId);
		
		for (int j = 0; j < owpAppRecvItems.size(); j++) {
			OwpAppRecvItem item = new OwpAppRecvItem();
			try {
				PropertyUtils.copyProperties(item,
						(OwpAppRecvItem) owpAppRecvItems.get(j));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

			//转抄收回货物
			item.setId(null);
			item.setModifyMark(ModifyMarkState.UNCHANGE);
			item.setHead(head);
			this.owpAppDao.saveOrUpdate(item);
			
		}
		
		return head;
	}
	
	
	/**
	 * 外发加工申请单回执处理
	 * 
	 * @param owpAppHead
	 * @param existOwpAppHead
	 * @return
	 */
	public String processOwpAppHead(final OwpAppHead owpAppHead,
			final OwpAppHead existOwpAppHead, List lsReturnFile) {
		String copAppNo = "";
			copAppNo = owpAppHead.getCopAppNo();
	
		return owpMessageLogic.processMessage(OwpBusinessType.OWP_APP,
				copAppNo, new CspProcessMessage() {
					public void failureHandling(
							TempCspReceiptResultInfo tempReceiptResult) {
						resetOwpAppHead(owpAppHead, existOwpAppHead);
					}

					public void successHandling(
							TempCspReceiptResultInfo tempReceiptResult) {
						CspReceiptResult receiptResult = tempReceiptResult
								.getReceiptResult();
						effectiveOwpAppHead(owpAppHead, existOwpAppHead,
								receiptResult);
					}
				}, lsReturnFile);
	}

	/**
	 * 重设外外加工申请表表头
	 * @param owpAppHead
	 * @param existOwpAppHead
	 * @author wss
	 */
	private void resetOwpAppHead(OwpAppHead owpAppHead,
			OwpAppHead existOwpAppHead) {
		if (existOwpAppHead == null) {
			owpAppHead.setDeclareState(DeclareState.APPLY_POR);
		} else {
			owpAppHead.setDeclareState(DeclareState.CHANGING_EXE);
		}
		this.owpAppDao.saveOrUpdate(owpAppHead);
	}
	
	
	/**
	 * @param owpAppHead申请单表头
	 * @author wss
	 */
	private void effectiveOwpAppHead(OwpAppHead owpAppHead,
			OwpAppHead exingOwpAppHead, CspReceiptResult receiptResult) {
		
		Map<Integer, Complex> mapExg = new HashMap<Integer, Complex>();
		
		if (exingOwpAppHead != null) {
			
			this.deleteOwpAppHead(exingOwpAppHead);
			
		} else {// 第一次申报的时候根据回执反写统一编号和申请表编号，变更的时候不反写 
			if (receiptResult != null) {
				// <APP_NO> 申请表编号 X(12) 不填
				owpAppHead.setAppNo(((OwpReceiptResult) receiptResult)
						.getEmsNo());
				// 电子口岸统一编号
				owpAppHead.setSeqNo(receiptResult.getSeqNo());
			}
		}
		
		owpAppHead.setDeclareState(DeclareState.PROCESS_EXE);
		owpAppHead.setModifyMark(ModifyMarkState.UNCHANGE);
		this.owpAppDao.saveOrUpdate(owpAppHead);
		
		
		List owpAppSendItems = this.owpAppDao.findOwpAppSendItemsStateChanged(
				owpAppHead.getId());
		for (int i = 0; i < owpAppSendItems.size(); i++) {
			OwpAppSendItem owpAppsendItem = (OwpAppSendItem) owpAppSendItems.get(i);
			if (ModifyMarkState.DELETED.equals(owpAppsendItem.getModifyMark())) {
				this.owpAppDao.delete(owpAppsendItem);
			} else {
				owpAppsendItem.setModifyMark(ModifyMarkState.UNCHANGE);
				this.owpAppDao.saveOrUpdate(owpAppsendItem);
			}
		}
		
		List owpAppRecvItems = this.owpAppDao.findOwpAppRecvItemsStateChanged(
				owpAppHead.getId());
		for (int i = 0; i < owpAppRecvItems.size(); i++) {
			OwpAppRecvItem owpAppRecvItem = (OwpAppRecvItem) owpAppRecvItems.get(i);
			if (ModifyMarkState.DELETED.equals(owpAppRecvItem.getModifyMark())) {
				this.owpAppDao.delete(owpAppRecvItem);
			} else {
				owpAppRecvItem.setModifyMark(ModifyMarkState.UNCHANGE);
				this.owpAppDao.saveOrUpdate(owpAppRecvItem);
			}
		}

	}
	
	
	/**
	 * 分页条件查询商品编码
	 * 
	 * @param index 分页查询参数
	 * @param length分页查询参数
	 * @param property属性
	 * @param value值
	 * @param isLike是否模糊查询
	 * @return
	 * @author wss
	 */
	public List findComplex(int index, int length, String property,
			Object value, boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from Complex a "
				+ " left join fetch a.firstUnit "
				+ " left join fetch a.secondUnit "
				+ " where (a.isOut <> '1' or a.isOut is null) ";

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
		}
		return owpAppDao.findPageList(hsql, paramters.toArray(), index, length);
	}
	
	/**
	 * 申请表 外发货物序号重排
	 * @param headId 表头id
	 * @author wss
	 */
	public void sortSeqNumOfOwpAppSendItem(String headId){
		List<OwpAppSendItem> ls = this.owpAppDao.find(
										" select a from OwpAppSendItem a "
										+ " where a.company.id = ? "
										+ " and a.head.id = ? "
										+ " order by a.seqNum ",
												new Object[]{CommonUtils.getCompany().getId(),headId});
		for(int i=0;i<ls.size();i++){
			OwpAppSendItem item = ls.get(i);
			item.setSeqNum(i+1);
			owpAppDao.saveOrUpdate(item);
		}
		
	}
	/**
	 * 申请表 外发货物序号重排
	 * @param headId 表头id
	 * @author wss
	 */
	public void sortSeqNumOfOwpAppRecvItem(String headId){
		List<OwpAppRecvItem> ls = this.owpAppDao.find(
								" select a from OwpAppRecvItem a "
								+ " where a.company.id = ? "
								+ " and a.head.id = ? "
								+ " order by a.seqNum ",
										new Object[]{CommonUtils.getCompany().getId(),headId});
		for(int i=0;i<ls.size();i++){
			OwpAppRecvItem item = ls.get(i);
			item.setSeqNum(i+1);
			owpAppDao.saveOrUpdate(item);
		}
	}
	
	
}