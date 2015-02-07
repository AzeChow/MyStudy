package com.bestway.bcus.cas.action;

import java.util.Date;
import java.util.List;

import com.bestway.bcs.contractstat.entity.ImpMaterialStat;
import com.bestway.bcus.cas.dao.CasDao;
import com.bestway.bcus.cas.entity.ImportExportInfo;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.cas.invoice.entity.CasConvert;
import com.bestway.bcus.cas.invoice.entity.ConditionBalance;
import com.bestway.bcus.cas.invoice.entity.LeftoverMaterielStatInfo;
import com.bestway.bcus.cas.logic.CasCheckLogic;
import com.bestway.common.Condition;
import com.bestway.common.ProjectTypeParam;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.dzsc.contractstat.entity.DzscImpMaterialStat;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
@SuppressWarnings("unchecked")
public class CasCheckActionImpl implements CasCheckAction {
	/**
	 * 海关帐Dao
	 */
	private CasDao casDao = null;
	/**
	 * 海关帐自我核查logic
	 */
	private CasCheckLogic casCheckLogic = null;
	public CasDao getCasDao() {
		return casDao;
	}
	public void setCasDao(CasDao casDao) {
		this.casDao = casDao;
	}
	public CasCheckLogic getCasCheckLogic() {
		return casCheckLogic;
	}
	public void setCasCheckLogic(CasCheckLogic casCheckLogic) {
		this.casCheckLogic = casCheckLogic;
	}
	
	/**
	 * 料件，成品，当时结在表
	 */
	public List getCurrentBillDetail(Request request,List<Condition> conditions, String orderBy,
			String materielType,Boolean ishsTaotal,Boolean isShowLess,Boolean isGroup,
				Boolean isFromCheck,List<Condition> conditionsCheck){
		return   this.casCheckLogic.getCurrentBillDetail(conditions,orderBy,materielType,ishsTaotal,isShowLess,isGroup,
				isFromCheck,conditionsCheck);
	}
	
	/**
	 * 外发加工 当日结存表
	 * @author wss(新)
	 */
	public List getCurrentBillDetailOutSource(Request request,
											List<Condition> conditions, 
											String orderBy,
											String balanceType,
											Boolean ishsTaotal,
											Boolean isShowLess,
											Boolean isGroup,
											List<Condition> conditionsM,
											Boolean isFromCheck,
											List<Condition> conditionsCheck){
//		return  this.casCheckLogic.getCurrentBillDetailNew(conditions,orderBy,billDetail,ishsTaotal,isShowLess,isGroup);
		return this.casCheckLogic.getCurrentBillDetailOutSource(conditions,
																orderBy,
																balanceType,
																ishsTaotal,
																isShowLess,
																isGroup,
																conditionsM,
																isFromCheck,
																conditionsCheck);
	}
	
	public List getWorkOrderBillDetail(Request request,
			List<Condition> conditionsBill,List<Condition> conditionsMaterial,  String materiel, boolean selected,
			boolean selected2) {
		return this.casCheckLogic.getWorkOrderBillDetail(conditionsBill,conditionsMaterial,materiel,selected,selected2);
	}
	
	
	/**
	 * 料件，成品对应盘点表
	 * @param request
	 * @param conditions
	 * @param orderBy
	 * @param billDetail
	 * @param ishsTaotal
	 * @param isShowLess
	 * @param isGroup
	 * @return
	 */
	public List getCurrentBillDetailPanDian(Request request,
											List<Condition> conditionsBill, 
											List<Condition> conditionsCheck, 
											String orderBy,
											String billDetail,
											Boolean ishsTatal,
											Boolean isShowLess,
											Boolean isGroup,
											Date date){
		return this.casCheckLogic.getCurrentBillDetailPanDian(conditionsBill,conditionsCheck,
															orderBy,billDetail,
															ishsTatal,
															isShowLess,
															isGroup,
															date);
	}
	
	/**
	 * 外发盘点
	 * @author wss
	 */
	public List getOutSourcePanDian(List<Condition> conditionsBill,
			List<Condition> conditionsCheck, String orderBy, String billDetail,
			Boolean ishsTaotal, Boolean isShowLess, Boolean isGroup,
			List<Condition> conditionsMateriel, Date date) {
		return this.casCheckLogic.getOutSourcePanDian(conditionsBill,
													conditionsCheck, 
													orderBy,  
													billDetail,
													ishsTaotal,  
													isShowLess,  
													isGroup,
													conditionsMateriel, 
													date);
	}
	
	/**
	 * 收货、送货明细表
	 * @author wss
	 */
	public List getRecvOrSendDetail(Request request,List<Condition> conditions,String materialType,String billDetail){
		return this.casCheckLogic.getRecvOrSendDetail(conditions,materialType,billDetail);
	}
	
	
	/**
	 * 查询已报关的商品（三种手册通用）
	 * @param isImport判断是否料件，true为料件
	 * @param lsContract是Contract型，合同备案表头
	 * @param state生效类型
	 * @param currentType 手册类型
	 * @return List 是TempBcsCustomsDeclarCommInfo型，
	 * @author wss
	 */
	
	public List findCustomsDeclarationCommInfoDistance(Request reqeust,
			boolean isImport, List lsContract, int state,int currentType) {
		return this.casCheckLogic.findCustomsDeclarationCommInfo(isImport,
				lsContract, state,currentType);
	}
	
	
	/**
	 * 查询转厂报关相应的客户
	 * @param reqeust请求控制
	 * @param isImport进口判断，true为进口
	 * @param lsContract是Contract型，合同备案表头
	 * @param state生效类型
	 * @return List 是customer型，存放了已报关的客户
	 * @author wss
	 */
	public List findCustomsDeclarationCustomer(Request reqeust,
			boolean isImport, List lsContract, int state,int currentType) {
		return this.casCheckLogic.findCustomsDeclarationCustomer(isImport,
				lsContract, state,currentType);
	}
	
	
	/**
	 * 进口料件结转明细表isImport=true，出口成品结转明细表isImport=false
	 * @param request 请求控制
	 * @param isImport判断是否料件，true为料件
	 * @param seqNum商品序号
	 * @param customer 客户
	 * @param impExpType进出口类型
	 * @param beginDate开始日期
	 * @param endDate结束日期
	 * @param contract合同备案表头
	 * @param state 生效类型
	 * @return List 是ImpExpCustomsDeclarationCommInfo型，结转明细表
	 * @author wss
	 */
	public List findTransferCustomsDeclarationCommInfo(Request request,
													boolean isImport, Integer seqNum, 
													String code, String name,
													String customer, String impExpType, 
													Date beginDate, Date endDate,
													List contracts, int state,
													int impExpFlag,int currentType) {
		return	this.casCheckLogic.findTransferCustomsDeclarationCommInfo(
											isImport, seqNum, code, name, 
											customer, impExpType,beginDate, endDate, 
											contracts, state, impExpFlag,currentType);

	}

	


	/**
	 * 成品折算情况表
	 * @param request
	 * @param conditions
	 * @param billDetail
	 * @param isptTaotal
	 * @param ishsTaotal
	 * @return
	 */
	public List getCurrentBillDetailBom(Request request,CasConvert casConvert,String billDetail){
		return  this.casCheckLogic.getCurrentBillDetailBom(casConvert,billDetail);
	}

	/**
	 * 半成品折算情况表
	 * @param request
	 * @param conditions
	 * @param billDetail
	 * @param isptTaotal
	 * @param ishsTaotal
	 * @return
	 */
	public List getSemiCurrentBillDetailBom(Request request,CasConvert casConvert,String billDetail){
		return this.casCheckLogic.getSemiCurrentBillDetailBom(casConvert,billDetail);
	}
	/**
	 * 残次品折算情况表
	 * @param request
	 * @param conditions
	 * @param billDetail
	 * @param isptTaotal
	 * @param ishsTaotal
	 * @return
	 */
	public List getCurrentBadProductBOM(Request request,CasConvert casConvert,String billDetail){
		return this.casCheckLogic.getCurrentBadProductBOM(casConvert,billDetail);
	}
	public List<ImportExportInfo> findImpExpInfos(boolean isWaiFa,Request request,
			String materialType, List<Condition> conditions1,List<Condition> conditions2, Boolean isSplitBomVersion,
			Date beginDate) {
		return casCheckLogic.findImpExpInfos(isWaiFa,materialType,
				conditions1,conditions2,isSplitBomVersion,
				 beginDate);
		
	}
	public List<ImportExportInfo> findImpExpInfos(boolean isWaiFa,Request request,
			String materialType, List<Condition> conditions1,List<Condition> conditions2, Boolean isSplitBomVersion) {
		
		
		return casCheckLogic. findImpExpInfos( isWaiFa,materialType,
				conditions1,conditions2,isSplitBomVersion) ;
	}
	/**
	 * 外发加工折算情况表
	 * @param request
	 * @param conditions
	 * @param billDetail
	 * @param isptTaotal
	 * @param ishsTaotal
	 * @return
	 */
	public List getCurrentOutSource(Request request,CasConvert casConvert,String billDetail){
		return this.casCheckLogic.getCurrentOutSource(casConvert,billDetail);
	}
	
	/**
	 * 查询差额总表
	 * @param request
	 * @param casCheckAction
	 * @return
	 */
    public List getCurrentBalanceInfo(Request request,ConditionBalance conditionBalance){
    	return this.casCheckLogic.getCurrentBalanceInfo(conditionBalance);
    }
    /**
	 * 查询差额总表new
	 * @param request
	 * @param casCheckAction
	 * @return
	 */
    public List getCurrentBalanceInfoNew(Request request,
			ConditionBalance conditionBalance) {
    	return this.casCheckLogic.getCurrentBalanceInfoNew(conditionBalance);
	}
    
    /**
	 * 查询差额总表（台达）
	 * @param request
	 * @param casCheckAction
	 * @return
	 */
    public List getCurrentBalanceInfoTaiDa(Request request,
			ConditionBalance conditionBalance) {
    	return this.casCheckLogic.getCurrentBalanceInfoTaiDa(conditionBalance);
	}
    /**
	 * 查询差额分表
	 * @param request
	 * @param casCheckAction
	 * @return
	 */
    public List getCurrentBalanceInfoTotal(Request request,ConditionBalance conditionBalance){
    	return this.casCheckLogic.getCurrentBalanceInfoTotal(conditionBalance);
    }
	public List getCurrentBalanceBOMInfo(Request request,
			ConditionBalance conditionBalance, List<Condition> conditionsMaterial,List<Condition> conditionsContract,int contractType) {
		
		return casCheckLogic.getCurrentBalanceBOMInfo(conditionBalance,conditionsMaterial,conditionsContract,contractType);
	}
	public List<TempObject> findMertailOrFinishedProductHs(Request request,
			String materiel, int type, String[] contracts) {
		return  casCheckLogic.findMertailOrFinishedProductHs(materiel,type,contracts);
		
	}
	public List<TempObject> findMertailOrFinishedProductSpecHs(String materiel,
			String product) {
		return  casCheckLogic.findMertailOrFinishedProductSpecHs(materiel,product);
	}
	
	
	/**
	 * 
	 * 合同分析
	 * 
	 * @param reqeust    请求控制
	 * @param contract 合同备案表头
	 * @param beginDate  开始日期
	 * @param endDate 结束日期
	 * @param state 生效类型
	 * @param isDetachCompute 是否分开统计
	 * @return List 是ImpMaterialStat型，存放统计报表中的进口料件报关情况表资料
	 * @author wss抄改
	 */
	@AuthorityFunctionAnnotation(caption = "浏览统计报表", index = 15)
	public List<ImpMaterialStat> findImpMaterialStatByContracts(
			Request reqeust, List contracts, Date beginDate, Date endDate,
			int state, boolean isCountInvoice, boolean isDetachCompute) {
		return this.casCheckLogic.findImpMaterialStatByContracts(contracts,
				beginDate, endDate, state,isCountInvoice,isDetachCompute);
	}
	
	/**
	 * wss：抄改
	 * 查询已报关的商品名称（电子帐册）
	 * 
	 * @param request    请求控制
	 * @param isImport   进口料件报关登记表isImport=true，出口成品报关登记表isImport=false
	 * @return 已报关的商品名称
	 */
	public List findCustomsDeclarationCommInfo(Request request, boolean isImport) {
		return casCheckLogic
				.findCustomsDeclarationCommInfo(isImport);
	}
	
	/**
	 * wss:抄改
	 * 查询已报关的客户
	 * 
	 * @param request   请求控制
	 * @param isImport    进口料件报关登记表isImport=true，出口成品报关登记表isImport=false
	 * @return 以报关的客户
	 */
	public List findCustomsDeclarationCustomer(Request request, boolean isImport) {
		return casCheckLogic
				.findCustomsDeclarationCustomer(isImport);
	}
	
	/**
	 * wss：抄改
	 * 不分事业部 进口料件情况统计表
	 */
	public List findImpExpCommInfoUseNoDept(Request request, boolean isImport,
			Integer seqNum, String customer, String IEType, Date beginDate,
			Date endDate, boolean isDeclaration, int isEffect) {
		return casCheckLogic.findImpExpCommInfoUseNoDept(
				isImport, seqNum, customer, IEType, beginDate, endDate,
				isDeclaration, isEffect);
	}
	
	/**
	 * 不分事业部出口成品情况统计表
	 */
	public List findImpExpCommInfoUseForExgNoDept(Request request,
			boolean isImport, Integer seqNum, String customer, String IEType,
			Date beginDate, Date endDate, boolean isDeclaration, int isEffect) {
		return casCheckLogic
				.findImpExpCommInfoUseForExgNoDept(isImport, seqNum, customer,
						IEType, beginDate, endDate, isDeclaration, isEffect);
	}
	
	/**
	 * wss:抄改
	 * 进口料件使用情况
	 * 
	 * @param request   请求控制
	 * @param isImport   是否进口
	 * @param seqNum  料件序号
	 * @param customer    客户供应商
	 * @param IEType   进出口类型
	 * @param beginDate   开始日期
	 * @param endDate   截止日期
	 * @return 有效期内符合条件的进口料件情况
	 */
	public List findImpExpCommInfoUseForDept(Request request, boolean isImport,
			Integer seqNum, String customer, String IEType, Date beginDate,
			Date endDate, boolean isDeclaration, String deptCode, int isEffect) {
		return casCheckLogic.findImpExpCommInfoUseForDept(
				isImport, seqNum, customer, IEType, beginDate, endDate,
				isDeclaration, deptCode, isEffect);
	}
	
	/**
	 * wss:抄改
	 * 进口料件情况统计表
	 * 
	 * @param contract     合同备案表头
	 * @param beginDate    开始日期
	 * @param endDate    结束日期
	 * @param state   生效类型
	 * @return List 
	 */
	public List<DzscImpMaterialStat> findImpMaterialStatByDzscEmsPorHeads(Request reqeust,List<DzscEmsPorHead> heads,
			Date beginDate, Date endDate, int state, boolean isDetachCompute){
		return this.casCheckLogic.findImpMaterialStatByDzscEmsPorHeads(heads, beginDate, endDate,state,isDetachCompute);
	}
	/**
	 * 将成品当日结存表的结果进行折料
	 * @param request
	 * @param list 需要折料的成品
	 * @return
	 * 2010-06-2hcl
	 */
	public List getCurrentBillDtailToBom(Request request,
			List<Condition> finishedConditions,
			List<Condition> materialConditions,List<Condition> finishedConditionsBalance,
			 boolean isBill,String orderBy,
			String materielType, boolean ishsTaotal, boolean isShowLess,
			boolean isGroup) {
		return this.casCheckLogic.getCurrentBillDtailToBom(finishedConditions,materialConditions,finishedConditionsBalance,isBill,orderBy,materielType,ishsTaotal,isShowLess,isGroup);
	}
	
	/**
	 *从盘点表中获取在产品当日结存
	 * @return
	 * @author wss
	 */
	public List getCurrentCheckBalance(Request request,
			List<Condition> conditionsCheck,List<Condition> conditionsBill,
			String orderBy,
			boolean isGroup,boolean isFromCheck,boolean isHsTaotal,boolean isShowLess) {
		return this.casCheckLogic.getCurrentCheckBalance(
				conditionsCheck,conditionsBill, orderBy, 
				isGroup,isFromCheck,isHsTaotal,isShowLess);
	}

	/**
	 * 取导入的在产品结存表的结果进行折料
	 * @param request
	 * @param list 需要折料的成品
	 * @return
	 * 2010-07-05hcl
	 */
	public List getSemiBillDtailToBom(Request request,
			List<Condition> finishedConditions,
			List<Condition> finishedConditionsBill,
			List<Condition> materialConditions, String orderBy,
			String materielType, boolean ishsTaotal, boolean isShowLess,
			boolean isWareSet,boolean isFromBill) {
		return casCheckLogic.getSemiBillDtailToBom(finishedConditions,
				finishedConditionsBill,
				materialConditions,
				orderBy,
				materielType,
				ishsTaotal,
				isShowLess,
				isWareSet,
				isFromBill);
	}

	/**
	 * 在产品当日结存与盘点差额表
	 * @param request
	 * @param finishedConditions
	 * @param materialConditions
	 * @param orderBy
	 * @param materielType
	 * @param ishsTaotal
	 * @param isShowLess
	 * @param isGroup
	 * @return
	 * @author wss
	 */
	public List getCurrentDifferent(Request request,
			List<Condition> finishedConditions,
			List<Condition> materialConditionsCheck,
			List<Condition> materialConditions, String orderBy,
			String materielType, boolean ishsTaotal, boolean isShowLess,
			boolean isGroup) {
		return this.casCheckLogic.getCurrentDifferent(finishedConditions,materialConditionsCheck,materialConditions,orderBy,materielType,ishsTaotal,isShowLess,isGroup);
	}
	/**
	 * 查询残次品当日结存折料情况
	 */
	public List getBadProductBillDtailToBom(Request request,
			List<Condition> finishedConditions,
			List<Condition> materialConditions, 
			List<Condition> finishedConditionsBalance,
			 boolean isCheckBalance,String orderBy,
			String materielType, boolean ishsTaotal, boolean isShowLess,
			boolean isGroup) {
		// TODO Auto-generated method stub
		return this.casCheckLogic.getBadProductBillDtailToBom(finishedConditions,materialConditions,finishedConditionsBalance,
				isCheckBalance,orderBy	,materielType,ishsTaotal,isShowLess,isGroup);
	}
	
	
	/**
	 * 自我核查 获得边角料查询报表内容
	 * 
	 * @param request 请求控制
	 * @return 边角料状况查询报表
	 * @author wss
	 */
	public List<LeftoverMaterielStatInfo> findLeftoverMaterielStatInfo(
			Request request) {
		return this.casCheckLogic.findLeftoverMaterielStatInfo();
	}
	
	/**
	 * 查找并计算边角料(按报关名称分时间段进行查询)
	 * 
	 * @param request请求控制
	 * @param beginDate开始日期
	 * @param endDate 结束日期
	 * @param projectTypeParam模块类型参数(是来自bcs还是bcus......)
	 * @return 边角料计算结果
	 * @author wss
	 */
	public List<LeftoverMaterielStatInfo> findCalLeftoverMateriel(
			Request request, Date beginDate, Date endDate,
			ProjectTypeParam projectTypeParam, 
			String taskId,Boolean isCancelBefore){
		return this.casCheckLogic.findCalLeftoverMateriel(
				beginDate, endDate, projectTypeParam, taskId, isCancelBefore);
	}
	 /**
	 * (海关帐)查询差额总表
	 * @param request
	 * @param casCheckAction
	 * @return
	 */
	public List getCasTransferfactoryDiffAllInfo(Request request,
			ConditionBalance conditionBalance) {
		return casCheckLogic.getCasTransferfactoryDiffAllInfo(conditionBalance);
	}
	
	
}
