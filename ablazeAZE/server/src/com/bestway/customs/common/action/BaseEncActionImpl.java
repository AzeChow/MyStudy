/*
 * Created on 2005-3-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.customs.common.action;

import java.security.acl.Acl;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.dictpor.entity.BcsDictPorHead;
import com.bestway.bcus.custombase.entity.hscode.CheckupComplex;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.enc.dao.EncDao;
import com.bestway.bcus.enc.logic.EncLogic;
import com.bestway.bcus.message.entity.DecSupplementList;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.customs.common.dao.BaseEncDao;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.customs.common.entity.BaseCustomsDeclarationContainer;
import com.bestway.customs.common.entity.BaseExportInvoiceItem;
import com.bestway.customs.common.entity.ImportBGDCondition;
import com.bestway.customs.common.logic.BaseEncLogic;
import com.bestway.customs.common.logic.EncImportLogic;

/**
 * 基础Logic
 * 
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 * 
 *         lm checked by 2010-07-06
 */
@SuppressWarnings("unchecked")
public class BaseEncActionImpl extends BaseActionImpl implements BaseEncAction {
	private BaseEncLogic baseEncLogic;

	private BaseEncDao baseEncDao;
	
	private EncImportLogic encImportLogic;

	/**
	 * 获取baseEncLogic
	 * 
	 * @return Returns the contractExeLogic.
	 */
	public BaseEncLogic getBaseEncLogic() {
		return baseEncLogic;
	}
	
	public void setEncImportLogic(EncImportLogic encImportLogic) {
		this.encImportLogic = encImportLogic;
	}

	public List getCustomsDeclaratn(){
		return null;
	}

	/**
	 * 设置baseEncLogic
	 * 
	 * @param contractExeLogic
	 *            The contractExeLogic to set.
	 */
	public void setBaseEncLogic(BaseEncLogic contractExeLogic) {
		this.baseEncLogic = contractExeLogic;
	}

	/**
	 * 获取baseEncDao
	 * 
	 * @return Returns the contractExeDao.
	 */
	public BaseEncDao getBaseEncDao() {
		return baseEncDao;
	}

	/**
	 * 设置baseEncDao
	 * 
	 * @param contractExeDao
	 *            The contractExeDao to set.
	 */
	public void setBaseEncDao(BaseEncDao contractExeDao) {
		this.baseEncDao = contractExeDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bestway.common.BaseAction#getAcl()
	 */
	public Acl getAcl() {
		// 
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bestway.common.BaseAction#getModuleName()
	 */
	public String getModuleName() {
		// 
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bestway.common.BaseAction#setModuleName(java.lang.String)
	 */
	public void setModuleName(String moduleName) {
		// 

	}

	/**
	 * 检查重复报关单商品序号是否连续
	 * 
	 * @param request
	 *            请求控制
	 * @param baseCustomsDeclaration
	 *            报关单头
	 * @return boolean 检查重复报关单商品序号是否连续
	 */
	public boolean checkCustDeclCommInfoSerialNumber(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration) {
		return this.baseEncLogic
				.checkCustDeclCommInfoSerialNumber(baseCustomsDeclaration);
	}

	/**
	 * 检查商品信息中项号所对应的商品的名称、规格与通关手册备案的名称、规格是否一致
	 * 
	 * @param request
	 *            请求控制
	 * @param isMaterial
	 *            料件还是成品
	 * @param baseCustomsDeclaration
	 *            报关单头
	 * @return boolean 检查商品信息中项号所对应的商品的名称、规格与通关手册备案的名称、规格是否一致
	 */
	public boolean checkCustDeclCommInfoSeqNumIsCorrespondsNameAndSpec(
			Request request, boolean isMaterial,
			BaseCustomsDeclaration baseCustomsDeclaration) {
		return this.baseEncLogic
				.checkCustDeclCommInfoSeqNumIsCorrespondsNameAndSpec(
						isMaterial, baseCustomsDeclaration);
	}

	/**
	 * 检查商品信息中项号所对应商品的法定单位（包括第二法定单位）法定单位数量是否为0
	 * 
	 * @param request
	 *            请求控制
	 * @param baseCustomsDeclaration
	 *            报关单头
	 * @return String 检查商品信息中项号所对应商品的法定单位
	 */
	public String[] checkCustDeclCommInfoLegalAmountBySeqNumIsZero(
			Request request, BaseCustomsDeclaration baseCustomsDeclaration) {
		return this.baseEncLogic
				.checkCustDeclCommInfoLegalAmountBySeqNumIsZero(baseCustomsDeclaration);
	}

	/**
	 * 保存报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 */
	// @AuthorityFunctionAnnotation(caption = "保存报关单", index = 8)
	public BaseCustomsDeclaration saveCustomsDeclaration(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration) {
		this.baseEncLogic.saveCustomsDeclaration(baseCustomsDeclaration);
		return baseCustomsDeclaration;
	}

	/**
	 * 删除报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 */
	// @AuthorityFunctionAnnotation(caption = "删除报关单(或一般作废)", index = 9)
	public void deleteCustomsDeclaration(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration) {
		this.baseEncLogic.deleteCustomsDeclaration(baseCustomsDeclaration,false);
	}

	/**
	 * 删除报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 */
	// @AuthorityFunctionAnnotation(caption = "删除报关单(或一般作废)", index = 9)
	public void deleteCustomsDeclaration(Request request,
			List baseCustomsDeclarations) {
		for (int i = 0; i < baseCustomsDeclarations.size(); i++) {
			BaseCustomsDeclaration baseCustomsDeclaration = (BaseCustomsDeclaration) baseCustomsDeclarations
					.get(i);
			this.baseEncLogic.deleteCustomsDeclaration(baseCustomsDeclaration,false);
		}

	}
	/**
	 * 删除报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 */
	// @AuthorityFunctionAnnotation(caption = "删除报关单(或一般作废)", index = 9)
	public void deleteCustomsDeclaration(Request request,
		BaseCustomsDeclaration baseCustomsDeclaration,boolean isCustomDelete) {
		this.baseEncLogic.deleteCustomsDeclaration(baseCustomsDeclaration,isCustomDelete);
	}
	
	/**
	 * 修改进出货转厂单据
	 */
	public void updateTransferFactoryBill(Request request, List baseCustomsDeclarations){
		this.baseEncLogic.updateTransferFactoryBill(baseCustomsDeclarations);
	}

	/**
	 * 删单
	 * 
	 * @param request
	 *            请求控制
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 * @param user
	 *            用户信息
	 */
	// @AuthorityFunctionAnnotation(caption = "删除报关单(或海关删单)", index = 9)
	public void deleteCustomsDeclarationDelete(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration, AclUser user) {
		this.baseEncLogic.deleteCustomsDeclarationDelete(
				baseCustomsDeclaration, user);
	}

	/**
	 * 删单
	 * 
	 * @param request
	 *            请求控制
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 * @param user
	 *            用户信息
	 */
	// @AuthorityFunctionAnnotation(caption = "删除报关单(或海关删单)", index = 9)
	public void deleteCustomsDeclarationDelete(Request request,
			List baseCustomsDeclarations, AclUser user) {
		for (int i = 0; i < baseCustomsDeclarations.size(); i++) {
			BaseCustomsDeclaration baseCustomsDeclaration = (BaseCustomsDeclaration) baseCustomsDeclarations
					.get(i);
			this.baseEncLogic.deleteCustomsDeclarationDelete(
					baseCustomsDeclaration, user);
		}

	}

	/**
	 * 取得进口报关单表头 BY 申报日期
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            手册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return List 是CustomsDeclaration型，在有效期内与指定的手册号匹配的进口报关单表头
	 */
	// @AuthorityFunctionAnnotation(caption = "浏览报关单", index = 7)
	public List findImportCustomsDeclaration(Request request, String emsNo,
			Date beginDate, Date endDate) {
		return this.baseEncLogic.findImportCustomsDeclaration(emsNo, beginDate,
				endDate);
	}

	/**
	 * 取得进口报关单表头 BY 申报日期
	 * 
	 * @param request
	 *            请求控制
	 * @param emsHeads
	 *            帐册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return List 是CustomsDeclaration型，在有效期内与指定的手册号匹配的进口报关单表头
	 */
	public List findImportCustomsDeclaration(Request request, List emsHeads,
			Date beginDate, Date endDate) {
		return this.baseEncLogic.findImportCustomsDeclaration(emsHeads,
				beginDate, endDate);
	}

	/**
	 * 取得出口报关单表头 BY 申报日期
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            手册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return List 是CustomsDeclaration型，在有效期内与指定的手册号匹配的出口报关单表头
	 */
	// @AuthorityFunctionAnnotation(caption = "浏览报关单", index = 7)
	public List findExportCustomsDeclaration(Request request, String emsNo,
			Date beginDate, Date endDate) {
		return this.baseEncLogic.findExportCustomsDeclaration(emsNo, beginDate,
				endDate);
	}

	/**
	 * 取得出口报关单表头 BY 申报日期
	 * 
	 * @param request
	 *            请求控制
	 * @param baseHeads
	 *            帐册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return List 是CustomsDeclaration型，在有效期内与指定的手册号匹配的出口报关单表头
	 */
	// @AuthorityFunctionAnnotation(caption = "浏览报关单", index = 7)
	public List findExportCustomsDeclaration(Request request, List baseHeads,
			Date beginDate, Date endDate) {
		return this.baseEncLogic.findExportCustomsDeclaration(baseHeads,
				beginDate, endDate);
	}

	/**
	 * 返回报关单商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpFlag
	 *            进出口标志
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param isFtoJ
	 *            繁转简
	 * @return List 报关单商品信息
	 */
	public List customsDeclarationExport(Request request, int impExpFlag,
			Date beginDate, Date endDate, boolean isFtoJ) {
		return this.baseEncLogic.customsDeclarationExport(impExpFlag,
				beginDate, endDate, isFtoJ);
	}

	/**
	 * 取得特殊报关单表头 BY 申报日期
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            手册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return List 是CustomsDeclaration型，在有效期内与指定的手册号匹配的特殊报关单表头
	 */
	public List findSpecialCustomsDeclaration(Request request, String emsNo,
			Date beginDate, Date endDate) {
		return this.baseEncLogic.findSpecialCustomsDeclaration(emsNo,
				beginDate, endDate);
	}

	/**
	 * 取得特殊报关单表头 BY 申报日期
	 * 
	 * @param request
	 *            请求控制
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return List 是CustomsDeclaration型，在有效期内与指定的手册号匹配的特殊报关单表头
	 */
	public List findSpecialCustomsDeclaration(Request request, Date beginDate,
			Date endDate) {
		return this.baseEncLogic.findSpecialCustomsDeclaration(beginDate,
				endDate);
	}

	/**
	 * 取得出口报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param id
	 *            报关单ID
	 * @return 返回报关单表头（用于出口收汇核销单打印）
	 */
	public BaseCustomsDeclaration findCustomsDeclarationById(Request request,
			String id) {
		return this.baseEncLogic.findCustomsDeclarationById(id);
	}

	/**
	 * 取得报关单明细
	 * 
	 * @param request
	 *            请求控制
	 * @param master
	 *            报关单头
	 * @return 返回报关单明细列表（用于出口收汇核销单打印）
	 */
	public List findCustomsDeclarationInfo(Request request,
			BaseCustomsDeclaration master) {
		return this.baseEncLogic.findCustomsDeclarationInfo(master);
	}

	/**
	 * 取得报关单明细
	 * 
	 * @param baseID 表头ID
	 * @return 返回报关单明细列表
	 */
	public Map<String,List> findCustomsDeclarationInfos(Request request,
			List<String> baseID,Integer projectType){
		return this.baseEncLogic.findCustomsDeclarationInfos(baseID,projectType);
	}
	
	/**
	 * 取得进口报关单表头
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是CustomsDeclaration型，进口报关单表头
	 */
	public List findImportCustomsDeclaration(Request request) {
		return this.baseEncLogic.findImportCustomsDeclaration();
	}

	/**
	 * 得到要申报的进口报关单表头，已检查但未生效的报关单表头
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是TempCustomsMessage型，存放还没被选择的报关单表头信息
	 */
	public List getImportDataSource(Request request) {
		return this.baseEncLogic.getImportDataSource();
	}
	
	/**
	 * 取得已生成taskId、未生效的报关单
	 * @param request
	 * @return
	 */
	public List<BaseCustomsDeclaration> findDeclarationHasTaskIdUnEffect(Request request) {
		return baseEncDao.findDeclarationHasTaskIdUnEffect();
	}

	/**
	 * 取得出口报关单表头
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是CustomsDeclaration型，出口报关单表头
	 */
	public List findExportCustomsDeclaration(Request request) {
		return this.baseEncLogic.findExportCustomsDeclaration();
	}

	/**
	 * 取得特殊报关单表头
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是CustomsDeclaration型，特殊报关单表头
	 */
	public List findSpecialCustomsDeclaration(Request request) {
		return this.baseEncLogic.findSpecialCustomsDeclaration();
	}

	/**
	 * 保存报关单商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param customsDeclarationCommInfo
	 *            报关单物料信息
	 * @return BaseCustomsDeclarationCommInfo 报关单商品信息
	 */
	// @AuthorityFunctionAnnotation(caption = "保存报关单表体", index = 11)
	public BaseCustomsDeclarationCommInfo saveCustomsDeclarationCommInfo(
			Request request,
			BaseCustomsDeclarationCommInfo customsDeclarationCommInfo) {
		this.baseEncLogic
				.saveCustomsDeclarationCommInfo(customsDeclarationCommInfo);
		return customsDeclarationCommInfo;
	}

	/**
	 * 查询是否选中规范申报规格
	 */
	public List findIsSpecification(Request request) {
		return baseEncLogic.findIsSpecification();
	}
	
	/**
	 * 取得报关单商品临时信息(如果在报关单中已存在的话，将其过滤)
	 * 
	 * @param request
	 *            请求控制
	 * @param isMaterial
	 *            料件成品判断，true代表料件，false代表成品
	 * @param customsDeclaration
	 *            报关单表头
	 * @param sfield
	 *            传递过来的参数
	 * @param svalues
	 *            参数对应的值
	 * @return List 报关单商品临时信息
	 */
	public List getTempCustomsDeclarationCommInfo(Request request,
			boolean isMaterial, BaseCustomsDeclaration customsDeclaration,
			String sfield, Object svalues) {
		return this.baseEncLogic.getTempCustomsDeclarationCommInfo(isMaterial,
				customsDeclaration, sfield, svalues);
	}

	/**
	 * 保存报关单商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param isMaterial
	 *            料件成品判断，true代表料件，false代表成品
	 * @param customsDeclaration
	 *            报关单表头
	 * @return List 报关单商品临时信息
	 */
	public List getTempCustomsDeclarationCommInfo(Request request,
			boolean isMaterial, BaseCustomsDeclaration customsDeclaration) {
		return this.baseEncLogic.getTempCustomsDeclarationCommInfo(isMaterial,
				customsDeclaration);
	}

	/**
	 * 保存报关单商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param isMaterial
	 *            料件成品判断，true代表料件，false代表成品
	 * @param tempCommInfoList
	 *            是List型
	 * @param customsDeclaration
	 *            报关单表头
	 */
	// @AuthorityFunctionAnnotation(caption = "保存报关单表体", index = 11)
	public void saveCustomsDeclarationCommInfo(Request request,
			boolean isMaterial, List tempCommInfoList,
			BaseCustomsDeclaration customsDeclaration) {
		this.baseEncLogic.saveCustomsDeclarationCommInfo(isMaterial,
				tempCommInfoList, customsDeclaration);
	}

	/**
	 * 查询结转报关单已经与海关单据对应
	 * 
	 * @param request
	 *            请求控制
	 * @param baseCustomsDeclarations
	 *            报关单表头是List型
	 * @return List 结转报关单已经与海关单据对应
	 */
	public List findMakeBillCorrespondingInfoBase(Request request,
			List baseCustomsDeclarations) {
		return this.baseEncLogic
				.findMakeBillCorrespondingInfoBase(baseCustomsDeclarations);
	}

	/**
	 * 删除报关单商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param customsDeclarationCommInfo
	 *            报关单商品信息
	 */
	// @AuthorityFunctionAnnotation(caption = "删除报关单表体", index = 11)
	public void deleteCustomsDeclarationCommInfo(Request request,
			BaseCustomsDeclarationCommInfo customsDeclarationCommInfo) {
		this.baseEncLogic
				.deleteCustomsDeclarationCommInfo(customsDeclarationCommInfo);
	}

	/**
	 * 删除报关单商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param commInfoList
	 *            是List型，报关单商品信息
	 */
	// @AuthorityFunctionAnnotation(caption = "删除报关单表体", index = 11)
	public void deleteCustomsDeclarationCommInfo(Request request,
			List commInfoList) {
		this.baseEncLogic.deleteCustomsDeclarationCommInfo(commInfoList);
	}

	/**
	 * 取得报关单商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param customsDeclaration
	 *            报关单表头
	 * @return List 是CustomsDeclarationCommInfo型，报关单商品信息
	 */
	public List findCustomsDeclarationCommInfo(Request request,
			BaseCustomsDeclaration customsDeclaration) {
		return this.baseEncLogic
				.findCustomsDeclarationCommInfo(customsDeclaration);
	}

	/**
	 * 根据报关单查询报关单集装箱
	 * 
	 * @param request
	 *            请求控制
	 * @param customsDeclaration
	 *            报关单表头
	 * @return List 是CustomsDeclarationContainer型，报关单集装箱
	 */
	public List findContainerByCustomsDeclaration(Request request,
			BaseCustomsDeclaration customsDeclaration) {
		return this.baseEncLogic
				.findContainerByCustomsDeclaration(customsDeclaration);
	}
	
	/**
	 *查询报关行 
	 */
	public List findcustomsbrokerList(Request request){
			return this.baseEncLogic.findcustomsbrokerList();
	}
	/**
	 * 取得报关单商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpFlag
	 *            进出口标志
	 * @return List 符合指定的进出口标志的报关单商品信息
	 */
	public List findCustomsDeclarationCommInfo(Request request, int impExpFlag) {
		return this.baseEncDao.findCustomsDeclarationCommInfo(impExpFlag);
	}

	/**
	 * 保存集装箱信息
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            报关单集装箱信息
	 */
	// @AuthorityFunctionAnnotation(caption = "保存报关单", index = 8)
	public BaseCustomsDeclarationContainer saveCustomsDeclarationContainer(
			Request request, BaseCustomsDeclarationContainer list) {
		this.baseEncDao.saveCustomsDeclarationContainer(list);
		return list;
	}

	/**
	 * 保存集装箱信息
	 * 
	 * @param request
	 *            请求控制
	 * @param b
	 *            报关单表头
	 * @param list
	 *            报关单集装箱信息
	 */
	// @AuthorityFunctionAnnotation(caption = "保存报关单", index = 8)
	public void saveCustomsDeclarationContainer(Request request,
			BaseCustomsDeclaration b, List list) {
		this.baseEncDao.saveCustomsDeclarationContainer(b, list);
	}

	/**
	 * 删除集装箱信息
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            报关单集装箱信息
	 */
	// @AuthorityFunctionAnnotation(caption = "保存报关单", index = 8)
	public void deleteCustomsDeclarationContainer(Request request,
			BaseCustomsDeclarationContainer list) {
		this.baseEncDao.deleteCustomsDeclarationContainer(list);
	}

	/**
	 * 查询报关单集装箱数据
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpFlag
	 *            进出口标志
	 * @return List 符合指定进出口标志的报关单集装箱数据
	 */
	public List findCustomsDeclarationContainer(Request request, int impExpFlag) {
		return this.baseEncDao.findCustomsDeclarationContainer(impExpFlag);
	}

	/**
	 * 取得报关单预计录入号
	 * 
	 * @param request
	 *            请求控制
	 * @param projectType
	 *            模块类型
	 * @return String 最大的报关单预录入号
	 */
	public String getMaxPreCustomsDeclarationCode(Request request) {
		return this.baseEncDao.getMaxPreCustomsDeclarationCode();
	}

	/**
	 * 取得报关单流水号
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpFlag
	 *            进出口标志
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @return Integer 与指定的进出口标志匹配的报关单流水号
	 */
	public Integer getCustomsDeclarationSerialNumber(Request request,
			int impExpFlag, String emsHeadH2k) {
		return this.baseEncDao.getCustomsDeclarationSerialNumber(impExpFlag,
				emsHeadH2k);
	}

	/**
	 * 获取出口发票项目来自出口报关单Id
	 * 
	 * @param request
	 *            请求控制
	 * @param customsDeclarationId
	 *            出口报关单id
	 * @return List 与指定报关单id匹配的报关单中的出口发票信息
	 */
	public List findExportInvoiceItemByCustomsDeclarationId(Request request,
			String customsDeclarationId) {
		return this.baseEncDao
				.findExportInvoiceItemByCustomsDeclarationId(customsDeclarationId);
	}

	/**
	 * 删除出口发票项目
	 * 
	 * @param request
	 *            请求控制
	 * @param exportInvoiceItem
	 *            出口发票项目
	 */
	// @AuthorityFunctionAnnotation(caption = "删除报关单", index = 9)
	public void deleteExportInvoiceItem(Request request,
			BaseExportInvoiceItem exportInvoiceItem) {
		this.baseEncDao.deleteExportInvoiceItem(exportInvoiceItem);
	}

	/**
	 * 保存或修改出口发票项目
	 * 
	 * @param request
	 *            请求控制
	 * @param exportInvoiceItem
	 *            出口发票项目
	 */
	// @AuthorityFunctionAnnotation(caption = "保存报关单", index = 8)
	public void saveExportInvoiceItem(Request request,
			BaseExportInvoiceItem exportInvoiceItem) {
		this.baseEncDao.saveExportInvoiceItem(exportInvoiceItem);
	}

	/**
	 * 转抄报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 * @param isCopyComm
	 *            是否要转抄商品，true为要
	 * @return BaseCustomsDeclaration 报关单表头Base
	 */
	// @AuthorityFunctionAnnotation(caption = "生效报关单", index = 8.3)
	public BaseCustomsDeclaration copyCustomsDeclaration(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration, boolean isCopyComm) {
		return this.baseEncLogic.copyCustomsDeclaration(baseCustomsDeclaration,
				isCopyComm);
	}

	// /**
	// * 检查重复报关单号
	// */
	// public BaseCustomsDeclaration saveCustoms(Request request,
	// BaseCustomsDeclaration baseCustomsDeclaration) {
	// this.baseEncLogic.saveCustoms(baseCustomsDeclaration);
	// return baseCustomsDeclaration;
	// }

	/**
	 * 取得报关单商品流水号
	 * 
	 * @param request
	 *            请求控制
	 * @param customsDeclaration
	 *            报关单表头
	 * @return Integer 报关单商品流水号
	 */
	public Integer getCustomsDeclarationCommInfoSerialNumber(Request request,
			BaseCustomsDeclaration customsDeclaration) {
		return this.baseEncLogic
				.getCustomsDeclarationCommInfoSerialNumber(customsDeclaration);
	}

	/**
	 * 是否已经有报关单引用了外汇销销单号（jbcus,dzba,bcs)
	 * 
	 * @param request
	 *            请求控制
	 * @param authorizeFile
	 *            批准文号
	 * @param id
	 *            报关单表头Id
	 * @return List 是CustomsDeclaration型，报关单表头信息
	 */
	public List findExistAuthorizeFile(Request request, String authorizeFile,
			String id) {
		return this.baseEncLogic.findExistAuthorizeFile(authorizeFile, id);
	}

	/**
	 * 取得报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param id
	 *            报关单id
	 * @return BaseCustomsDeclaration 与指定id匹配的报关单
	 */
	public BaseCustomsDeclaration findCustomsDeclaration(Request request,
			String id) {
		return this.baseEncDao.findCustomsDeclaration(id);
	}

	/**
	 * 排序报关单商品信息后保存
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            报关单商品信息
	 */
	public void saveCustomsInfo(Request request, Vector list) {
		this.baseEncLogic.saveCustomsInfo(list);
	}

	/**
	 * 排序报关单商品信息后保存
	 * 
	 * @param request
	 *            请求控制
	 *@param list
	 *            报关单商品信息
	 */
	public void saveAtcMergeBeforeComInfo(Request request, Vector list) {
		this.baseEncLogic.saveAtcMergeBeforeComInfo(list);
	}

	/**
	 * 返回系统参数设置中的其他设置
	 * 
	 * @param request
	 *            请求控制
	 * @return 系统参数设置中的其他设置
	 */
	public CompanyOther findCompanyOther(Request request) {
		return this.baseEncDao.findCompanyOther();
	}

	/**
	 * 取得未生效报关单来自进出口类型
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpType
	 *            进出口类型
	 * 
	 * @return List与指定的进出口类型匹配没有生效的报关单
	 */
	public List<BaseCustomsDeclaration> findCustomsDeclarationByImpExpType(
			Request request, int impExpType) {
		return this.baseEncDao.findCustomsDeclarationByImpExpType(impExpType);

	}

	/**
	 * 生效报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 */
	public BaseCustomsDeclaration effectCustomsDeclaration(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration) {
		baseCustomsDeclaration = this.baseEncLogic
				.effectCustomsDeclaration(baseCustomsDeclaration);
		return baseCustomsDeclaration;
	}

	/**
	 * 回卷报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 */
	// @AuthorityFunctionAnnotation(caption = "回卷报关单", index = 8.2)
	public BaseCustomsDeclaration unreelCustomsDeclaration(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration) {
		baseCustomsDeclaration = this.baseEncLogic
				.unreelCustomsDeclaration(baseCustomsDeclaration);
		return baseCustomsDeclaration;
	}

	/**
	 * 计算报关单头件，毛，净重
	 * 
	 * @param request
	 *            请求控制
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 */
	public BaseCustomsDeclaration getPiceAndWeight(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration) {
		baseEncDao.getPiceAndWeight(baseCustomsDeclaration);
		return baseCustomsDeclaration;
	}

	/**
	 * 只显示物料四码不为空
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            物料类型
	 * @param firstIndex
	 *            第一个索引
	 * @param ptNo
	 *            料号
	 * @param emsNo
	 *            手册号
	 * @return List 是Materiel型，报关常用物料
	 */
	// @AuthorityFunctionAnnotation(caption = "内部商品", index = 8.4)
	public List findMaterielFromInner(Request request, String type,
			int firstIndex, String ptNo, String emsNo) {
		return this.baseEncLogic.findMaterielFromInner(type, firstIndex, ptNo,
				emsNo);
	}

	/**
	 * 通过内部件号找到帐册信息
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            手册号
	 * @param ptNo
	 *            料号
	 * @param type
	 *            物料类型
	 * @param materielVersion
	 *            版本
	 * @return List 是EmsHeadH2kImg或EmsHeadH2kExg型，电子帐册料件或成品
	 */
	public List findBillByMaterielPtNo(Request request, String emsNo,
			String ptNo, String type, String materielVersion) {
		return this.baseEncLogic.findBillByMaterielPtNo(emsNo, ptNo, type,
				materielVersion);
	}

	/**
	 * 通过内部件号找到帐册信息
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            手册号
	 * @param ptNo
	 *            料号
	 * @param type
	 *            物料类型
	 * @param materielVersion
	 *            版本
	 * @return List 是EmsHeadH2kImg或EmsHeadH2kExg型，电子帐册料件或成品
	 */
	public List findBillByMaterielPtNo2(Request request, String emsNo,
			String ptNo, String type, String materielVersion) {
		return this.baseEncLogic.findBillByMaterielPtNo2(emsNo, ptNo, type,
				materielVersion);
	}

	/**
	 * 取得物料内容来自报关单商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param info
	 *            报关单商品信息
	 * @return List 报关单商品信息中物料内容
	 */
	public List getMaterielByCustoms(Request request,
			BaseCustomsDeclarationCommInfo info) {
		return this.baseEncDao.getMaterielByCustoms(info);
	}

	/**
	 * 是否已经有报关单引用了外汇销销单号（jbcus,dzsc,bcs)
	 * 
	 * @param request
	 *            请求控制
	 * @param id
	 *            报关单表头Id
	 * @return BaseCustomsDeclaration 报关单表头Base
	 */
	public BaseCustomsDeclaration findAllCustomsDeclarationById(
			Request request, String id) {
		return this.baseEncLogic.findAllCustomsDeclarationById(id);
	}

	/**
	 * 报关清单来自报关商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param customs
	 *            报关商品信息
	 * @return List 报关清单内容
	 */
	public List findAppFromMaterielByCustomsInfo(Request request,
			BaseCustomsDeclarationCommInfo customs) {
		return this.baseEncDao.findAppFromMaterielByCustomsInfo(customs);
	}

	/**
	 * 取得进口报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param condition
	 *            查询条件
	 * @param parameters
	 *            参数
	 * @return List 进口报关单
	 */
	public List findImportCustomsDeclaration(Request request, String condition,
			List<Object> parameters) {
		return this.baseEncDao.findImportCustomsDeclaration(condition,
				parameters);
	}

	/**
	 * 取得出口报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param condition
	 *            查询条件
	 * @param parameters
	 *            参数
	 * @return List 出口报关单
	 */
	public List findExportCustomsDeclaration(Request request, String condition,
			List<Object> parameters) {
		return this.baseEncDao.findExportCustomsDeclaration(condition,
				parameters);
	}

	/**
	 * 得到申请单号通过清单号码
	 * 
	 * @param request
	 *            请求控制
	 * @param billNo
	 *            单据号
	 * @return String 进出口申请单单据号
	 */
	public String getImpExpNoByBillNo(Request request, String billNo) {
		return this.baseEncDao.getImpExpNoByBillNo(billNo);
	}
	/**
	 * 得到多个申请单号通过报关清单号码
	 * 
	 * @param request
	 *            请求控制
	 * @param billNo
	 *            报关清单号码
	 * @return 进出口申请单单据号
	 */
	public List getImpExpMultiNoByBillNo(Request request, String billNo){
		return this.baseEncDao.getImpExpMultiNoByBillNo(billNo);
	}

	/**
	 * 计算申请单物料的仓库总毛重、仓库总净重
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是ImpExpCommodityInfo型，申请单表头
	 * @return Materiel 报关常用物料
	 */
	public Materiel getNetGossWeight(Request request, List list) {
		return this.baseEncLogic.getNetGossWeight(list);
	}

	/**
	 * 计算申请单物料与报关单物料的总毛重、总净重的差异
	 * 
	 * @param request
	 *            请求控制
	 * @param info
	 *            报关单商品信息
	 * @return List 是BaseCustomsDeclarationCommInfo型，计算申请单物料与报关单物料的总毛重、总净重的差异
	 */
	public List findAppFromMaterielByCustomsInfoAndNetGossWeight(
			Request request, BaseCustomsDeclarationCommInfo info) {
		return this.baseEncLogic
				.findAppFromMaterielByCustomsInfoAndNetGossWeight(info);
	}

	/**
	 * 删除进出口报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param typeModel
	 *            模块类型
	 * @param emsNo
	 *            手册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 删除符合条件的进出口报关单
	 */
	/*
	 * public List findImExportCustomsDeclarationDelete(Request request, int
	 * typeModel, String emsNo, Date beginDate, Date endDate) { return
	 * this.baseEncDao.findImExportCustomsDeclarationDelete(typeModel, emsNo,
	 * beginDate, endDate); }
	 */
	/**
	 * 数量取整
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            报关单商品信息
	 */
	// @AuthorityFunctionAnnotation(caption = "数据取整", index = 8.5)
	public void customsInfoForMatInt(Request request, List list) {
		((EncLogic) getBaseEncLogic()).customsInfoForMatInt(list);
	}

	// @AuthorityFunctionAnnotation(caption = "自定义排序", index = 8.6)
	public void userDefinedOrder(Request request) {

	}

	/**
	 * 得到要申报的出口报关单表头，已检查但未生效的报关单表头
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是TempCustomsMessage型，存放还没被选择的报关单表头信息
	 */
	public List getExportDataSource(Request request) {
		return this.baseEncLogic.getExportDataSource();
	}

	/**
	 * 得到要申报的特殊报关单表头，已检查但未生效的报关单表头
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是TempCustomsMessage型，存放还没被选择的报关单表头信息
	 */
	public List getSpexportDataSource(Request request) {
		return this.baseEncLogic.getSpexportDataSource();
	}

	/**
	 * 报关单明细排序
	 * 
	 * @param request
	 *            请求控制
	 * @param customsDeclaration
	 *            报关单头
	 */
	// @AuthorityFunctionAnnotation(caption = "自动排序", index = 8.7)
	public void commInfoAutoOrder(Request request,
			BaseCustomsDeclaration customsDeclaration) {
		this.baseEncLogic.commInfoAutoOrder(customsDeclaration);
	}

	/**
	 * 增加设备管理的特殊报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param baseCustomsDeclaration
	 *            报关单头
	 * @param list
	 *            合同备案料件资料
	 * @return List 增加设备管理的特殊报关单
	 */
	public List addFixAssetCustomsDeclarationCommInfo(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration, List list) {
		return this.baseEncLogic.addFixAssetCustomsDeclarationCommInfo(
				baseCustomsDeclaration, list);
	}

	/**
	 * 增加设备管理的特殊报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param baseCustomsDeclaration
	 *            报关单头
	 * @param list
	 *            合同备案料件资料
	 * @return List 增加设备管理的特殊报关单
	 */
	public List addFixtureCustomsDeclarationCommInfo(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration, List list) {
		return this.baseEncLogic.addFixtureCustomsDeclarationCommInfo(
				baseCustomsDeclaration, list);
	}

	/**
	 * 检查发票，核销单号，司机纸号码是否重复
	 * 
	 * @param request
	 *            请求控制
	 * @param projectType
	 *            项目类型
	 * @param serialNumber
	 *            报关单流水号
	 * @param fields
	 *            传递过来的字段
	 * @param checkValue
	 *            字段对应的值
	 */
	public boolean isReCustoms(Request request, int projectType,
			Integer serialNumber, String fields, String checkValue) {
		return ((EncDao) getBaseEncDao()).isReCustoms(projectType,
				serialNumber, fields, checkValue);
	}

	/**
	 * 取得报关单商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param conveyance
	 *            报关单表头
	 *@param impExpFlag
	 *            进出口标志
	 * @return 报关单商品信息
	 */
	public List findCustomsDeclarationCommInfoByConveyance(Request request,
			String conveyance, int impExpFlag) {
		return this.baseEncDao.findCustomsDeclarationCommInfoByConveyance(
				conveyance, impExpFlag);
	}
	/**
	 * 取得报关单商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param conveyance
	 *            报关单表头
	 *@param impExpFlag
	 *            进出口标志
	 * @return 报关单商品信息
	 */
	public List findCustomsDeclarationCommInfoByConveyance(Request request,
			String conveyance, Integer impExpFlag,Integer projectType) {
		return this.baseEncDao.findCustomsDeclarationCommInfoByConveyance(
				conveyance, impExpFlag,projectType);
	}

	/**
	 * 取得报关单商品信息
	 * 
	 * @param conveyance
	 *            运输工具
	 * @param impExpFlag
	 *            进出口标志
	 * @return 报关单商品信息
	 */
	public List findMergerCustomsDeclarationCommInfoByConveyance(
			Request request, String conveyance, int impExpFlag,
			boolean isReworkTotalPrice) {
		return this.baseEncLogic
				.findMergerCustomsDeclarationCommInfoByConveyance(conveyance,
						impExpFlag, isReworkTotalPrice);
	}

	/**
	 * 取得进口报关单
	 * 
	 * @param condition
	 *            查询条件
	 * @param parameters
	 *            参数
	 * @return 进口报关单
	 */
	public List findCustomsDeclarationByConveyance(Request request,
			String conveyance, int impExpFlag) {
		return baseEncDao.findCustomsDeclarationByConveyance(conveyance,
				impExpFlag);

	}

	/**
	 * 取得报关单金额
	 * 
	 * @param request
	 *            请求控制
	 *@param impExpFlag
	 *            进出口标志
	 * @return 指定报关单的报关商品信息的统计情况
	 */
	public Map findCustomsDeclarationMoney(Request request, Integer impExpFlag) {
		return this.baseEncLogic.findCustomsDeclarationMoney(impExpFlag);
	}

	/**
	 * 取得最后一次导入报关单的日期，并且将最后一次的日期-3
	 * 
	 * @param request
	 *            请求控制
	 * @param isImportBGD
	 *            是进口或是出口报关单
	 */
	public Date getLastImportBGDDate(Request request, boolean isImportBGD) {
		return this.baseEncLogic.getLastImportBGDDate(isImportBGD);
	}

	/**
	 * 导入报关单资料
	 * 
	 * @param request
	 *            请求控制
	 *@param isImportBGD
	 *            是进口或是出口报关单
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 */
	public Map loadBGDFromQPXml(Request request, ImportBGDCondition condition) {
		return this.baseEncLogic.loadBGDFromQPXml(condition);
	}

	/**
	 * 导入报关单资料
	 * 
	 * @param request
	 *            请求控制
	 *@param isImportBGD
	 *            是进口或是出口报关单
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 */
	public Map loadBGDFromQPDirect(Request request,ImportBGDCondition condition) {
		return this.baseEncLogic.loadBGDFromQPDirect(condition);
	}

	/**
	 * 报关单通过币制获取汇率
	 * 
	 * @param request
	 *            请求控制
	 * @param curr
	 *            币制
	 * @param date
	 *            创建日期
	 * @param emsno
	 *            手册号
	 * @return Double 汇率
	 */
	public Double getCurrRateByCurr(Request request, Curr curr, Date date,
			String emsno) {
		return this.baseEncLogic.getCurrRateByCurr(curr, date, emsno);
	}

	/**
	 * 查询归并关系归并前料件表OR归并关系归并前成品表相关信息
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            物料类型
	 * @param firstIndex
	 *            分页开始位置
	 * @param obj
	 *            参数
	 * @param sFields
	 *            字段
	 * @return List 归并关系归并前料件表OR归并关系归并前成品表相关信息
	 */
	public List findMaterielFromInnerAndInEmsMerger(Request request,
			String type, int firstIndex, Object obj, String sFields) {
		return this.baseEncLogic.findMaterielFromInnerAndInEmsMerger(type,
				firstIndex, obj, sFields);
	}

	/**
	 * 统计设备的合同定量、报关已出数量、剩余可进数量
	 * 
	 * @param request
	 *            请求控制
	 * @param basecustomsDeclarationCommInfo
	 *            报关单表体
	 * @return List 统计设备的合同定量、报关已出数量、剩余可进数量
	 */
	public List statisticsFixInCommInfo(Request request,
			BaseCustomsDeclarationCommInfo basecustomsDeclarationCommInfo) {
		return this.baseEncLogic
				.statisticsFixInCommInfo(basecustomsDeclarationCommInfo);
	}

	/**
	 * 获取报关单头的运输工具相同的所有集装箱号
	 * 
	 * @param request
	 *            请求控制
	 * @param baseCustomsDeclaration
	 *            报关单表头
	 * @return List 获取报关单头的运输工具相同的所有集装箱号
	 */
	public List findAllContainerByConveyance(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration) {
		return this.baseEncDao
				.findAllContainerByConveyance(baseCustomsDeclaration);
	}

	/**
	 * 根据进出口标志查询报关单头信息
	 * 
	 * @param request
	 *            请求控制
	 * @param flag
	 *            进出口标志
	 * @return List 报关单头信息
	 */
	public List findCustomsDeclarationByImpExpFlag(Request request, int flag) {
		return this.baseEncDao.findCustomsDeclarationByImpExpFlag(flag);
	}

	/**
	 * 获取报关单删单信息
	 * 
	 * @param request
	 *            请求控制
	 * @param typeModel
	 *            项目类型
	 * @param emsNo
	 *            帐册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 进出口报关单删单信息
	 */
	public List getImExportCustomsDeclarationDelete(Request request,
			int typeModel, String emsNo, Date beginDate, Date endDate) {
		return this.baseEncLogic.getImExportCustomsDeclarationDelete(typeModel,
				emsNo, beginDate, endDate);
	}

	/**
	 * 返回电子化手册合同备案or电子手册通关备案or电子账册表头信息
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            项目类型
	 * @return List 电子化手册合同备案or电子手册通关备案or电子账册表头信息
	 */
	public List findEmsByProjectType(Request request, int type) {
		return this.baseEncDao.findEmsByProjectType(type);
	}
	/**
	 * 通过单据类型找正在执行的电子帐册资料
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            单据类型
	 * @return
	 */
	public List findExcuteEmsByProjectType(Request request, int type){
		return this.baseEncDao.findExcuteEmsByProjectType(type);
	}
	/**
	 * 返回相关表信息
	 * 
	 * @param request
	 *            请求控制
	 * @param headid
	 *            相关表表头ID
	 * @param type
	 *            项目类型
	 * @param impExpFlag
	 *            进出扣标志
	 * @param couId
	 *            原产国/产销国ID
	 * @param code
	 *            商品编码
	 * @return List 相关表信息
	 */
	public List findImgOrExg(Request request, String headid, int type,
			int impExpFlag, String couId, String code) {
		return this.baseEncDao.findImgOrExg(headid, type, impExpFlag, couId,
				code);
	}

	/**
	 * 保存报关单头的随附单据
	 * 
	 * @param request
	 *            请求控制
	 * @param baseCustomsDeclaration
	 *            报关单信息
	 */
	public BaseCustomsDeclaration getAttachedBill(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration) {
		baseEncLogic.getAttachedBill(baseCustomsDeclaration);
		return baseCustomsDeclaration;
	}

	/**
	 * 找到录入了QP系统导出的进口报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpFlag
	 *            进出口标志
	 * @param condition
	 *            报关单号
	 * @param parameters
	 *            参数
	 * @return 与指定的报关单号匹配的出口报关单
	 */
	@SuppressWarnings("unchecked")
	public List findBGDFromQPXml(Request request, Integer impExpFlag,
			String condition, List<Object> parameters) {
		return this.baseEncDao.findBGDFromQPXml(impExpFlag, condition,
				parameters);
	}

	/**
	 * 根据报关单号取得所有的从QP倒出的报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param customsDeclarationCode
	 *            报关单号
	 * @return 与指定的报关单号匹配的出口报关单
	 */
	public List findAllLoadBGDFromQPXml(Request request,
			String customsDeclarationCode) {
		return this.baseEncDao.findAllLoadBGDFromQPXml(customsDeclarationCode);
	}

	/**
	 * 得到要申报的进口报关单表头，已检查但未生效的报关单表头
	 * 
	 * @param request
	 *            请求控制
	 * @param str1
	 *            帐册编号
	 * @param str2
	 *            预申报帐册编号
	 * @return List 是TempCustomsMessage型，存放还没被选择的报关单表头信息
	 */
	public List getImportDataSource(Request request, String str1, String str2) {
		return this.baseEncLogic.getImportDataSource(str1, str2);
	}

	/**
	 * 得到要申报的出口报关单表头，已检查但未生效的报关单表头
	 * 
	 * @param request
	 *            请求控制
	 * @param str1
	 *            帐册编号
	 * @param str2
	 *            预申报帐册编号
	 * @return List 是TempCustomsMessage型，存放还没被选择的报关单表头信息
	 */
	public List getExportDataSource(Request request, String str1, String str2) {
		return this.baseEncLogic.getExportDataSource(str1, str2);
	}

	/**
	 * 得到要申报的特殊报关单表头，已检查但未生效的报关单表头
	 * 
	 * @param request
	 *            请求控制
	 * @param str1
	 *            帐册编号
	 * @param str2
	 *            预申报帐册编号
	 * @return List 是TempCustomsMessage型，存放还没被选择的报关单表头信息
	 */
	public List getSpexportDataSource(Request request, String str1, String str2) {
		return this.baseEncLogic.getSpexportDataSource(str1, str2);
	}

	/**
	 * 根据当前商品编码查询对应的海关商品编码
	 * 
	 * @param request
	 *            请求控制
	 * @param code
	 *            商品编码
	 * 
	 */
	public CheckupComplex findCheckupComplexByCode(Request request,
			String code, int impExpFlag) {
		return this.baseEncDao.findCheckupComplexByCode(code, impExpFlag);
	}

	/**
	 * 当报关单明细超出20项的时候，将当前报关单拆分成多份报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param baseCustomsDeclaration
	 *            报关单表头
	 * @return List 是BaseCustomsDeclaration型,报关单表头
	 */
	public List splitCustomsDeclaration(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration) {
		return this.baseEncLogic
				.splitCustomsDeclaration(baseCustomsDeclaration);
	}

	/**
	 * 进口报关单按流水号重新排序
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            手册号
	 */
	public void resortImportCustomsDeclarationSerialNumber(Request request,
			String emsNo) {
		this.baseEncLogic.resortImportCustomsDeclarationSerialNumber(emsNo);
	}

	/**
	 * 出口报关单按流水号重新排序
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            手册号
	 */
	public void resortExportCustomsDeclarationSerialNumber(Request request,
			String emsNo) {
		this.baseEncLogic.resortExportCustomsDeclarationSerialNumber(emsNo);
	}

	/**
	 * 特殊报关单按流水号重新排序
	 * 
	 * @param request
	 *            请求控制
	 */
	public void resortSpecialCustomsDeclarationSerialNumber(Request request) {
		this.baseEncLogic.resortSpecialCustomsDeclarationSerialNumber();
	}

	/**
	 * 计量单折算比例位资料
	 * 
	 * @param request
	 * @return
	 */
	public Map findAllUnitRateMap(Request request) {
		return this.baseEncLogic.getUnitRateMap();
	}

	/**
	 * 根据运输工具查询报关单
	 * 
	 * @param request
	 * @param traffic
	 * @return
	 */
	public List<BaseCustomsDeclarationCommInfo> findCustomsDeclarationsForPrint(
			Request request, String traffic) {
		return baseEncLogic.findCustomsDeclarationsForPrint(traffic);
	}

	/**
	 * 取得大批量修改商品编的备案资料
	 * 
	 * @param isMaterial
	 * @return
	 */
	public List getBatchUpdateComplex(Request request, String declareState,
			String contractNo, String emsType, Boolean isMaterial) {
		return baseEncLogic.getBatchUpdateComplex(declareState, contractNo,
				emsType, isMaterial);
	}

	/**
	 * 查询所有状态的合同
	 * 
	 * @param declareState
	 * @return
	 */
	public List findContractByDeclareState(Request request,String declareState){
		return baseEncDao.findContractByDeclareState(declareState);
	}
	/**
	 * 更新合同备案的商品编码
	 * 
	 * @param isMaterial
	 * @param complex
	 * @param seqNum
	 */
	public void updateAllContractComplex(Request request, String declareState,
			Boolean isMaterial, Complex complex, String oldComplexId, Integer seqNum,
			boolean isSendData, String contractId) {
		baseEncDao.updateAllContractComplex(contractId, isMaterial, complex, oldComplexId,
				seqNum, isSendData);
	}

	/**
	 * 更新所有备案资料库的商品编码
	 * 
	 * @param isMaterial
	 * @param complex
	 * @param seqNum
	 */
	public void updateAllBcsDictPorComplex(Request request,
			String declareState, Boolean isMaterial, Complex complex, String oldComplexId,
			Integer seqNum, boolean isSendData) {
		List lbcs = baseEncDao.findBcsDictPorHeadByDeclareState(declareState);
		for (int i = 0; i < lbcs.size(); i++) {
			BcsDictPorHead bcsDictPorHead=(BcsDictPorHead)lbcs.get(i);
		baseEncDao.updateAllBcsDictPorComplex(bcsDictPorHead.getId(), isMaterial,
				complex, oldComplexId, seqNum, isSendData);}
	}

	/**
	 * 更新所有内部归并的商品编码
	 * 
	 * @param complex
	 * @param seqNum
	 */
	public void updateAllBcsInnerMergeDataComplex(Request request,
			Boolean isMaterial,Complex complex, Integer seqNum) {
		baseEncDao.updateAllBcsInnerMergeDataComplex( isMaterial,complex, seqNum);
	}

	/**
	 * 取得备案资料库的归并关系
	 * 
	 * @param isMaterial
	 * @return
	 */
	public List findBcsDictPorImgOrExgInnerMergeSeqNum(Request request,
			String declareState, Boolean isMaterial, Integer seqNum) {
		return baseEncDao.findBcsDictPorImgOrExgInnerMergeSeqNum(declareState,
				isMaterial, seqNum);
	}

	public List queryReturnImportReport(Request request, Date begin, 
			Date end, int impExpType, int impExpFlag) {
		return baseEncLogic.queryReturnImportReport(begin, end, impExpType);
	}
	
	public List queryReturnImportReport(Request request, Date begin, 
			Date end, int impExpType, int impExpFlag,String emsNo,int projectType) {
		return baseEncLogic.queryReturnImportReport(begin, end, impExpType,emsNo,projectType);
	}
	
	public List queryReturnExportReport(Request request, Date begin, 
			Date end, int impExpType, int impExpFlag) {
		return baseEncLogic.queryReturnExportReport(begin, end, impExpType);
	}
	
	public List queryReturnExportReport(Request request, Date begin, 
			Date end, int impExpType, int impExpFlag , String emsHeadH2k,int projectType) {
		return baseEncLogic.queryReturnExportReport(begin, end, impExpType,emsHeadH2k,projectType);
	}
	/**
	 * 查询统计未复出数据或未复进数据。
	 * @param request
	 * @param complexId 商品编号
	 * @param impExpFlag 进出口标志
	 * @return
	 */
	public Double countReturnNumbers(Request request, String complexId, boolean impExpFlag,String version,int commSerialNo) {
		return baseEncLogic.countReturnNumbers(null, null, complexId, impExpFlag,version,commSerialNo);
	}
	/**
	 * 查询统计未复出数据或未复进数据。
	 * @param request
	 * @param complexId 商品编号
	 * @param impExpFlag 进出口标志
	 * @param emsHeadH2k 手册号
	 * @return
	 */
	public Double countReturnNumbers(Request request,String emsHeadH2k, String complexId, boolean impExpFlag,String version,int commSerialNo){
		return baseEncLogic.countReturnNumbers(null, null,emsHeadH2k,complexId, impExpFlag,version,commSerialNo);
	}
	/**
	 *查询统计可退换料件出口数或退厂返工可进口数
	 * @param request
	 * @param complexId 商品编号
	 * @param impExpFlag 进出口标志
	 * @param emsHeadH2k 手册号
	 * @return
	 */
	public Double countRetreatNumbers(Request request, String emsHeadH2k,String complexId, boolean impExpFlag,String version,int commSerialNo) {
		return baseEncLogic.countRetreatNumbers(null, null,emsHeadH2k, complexId, impExpFlag,version,commSerialNo);
	}
	/**
	 * 取得出口报关单
	 * 
	 * @param customsDeclarationCode
	 *            报关单号 customsDeclarationCode
	 * @return 返回报关单表头
	 */
	public BaseCustomsDeclaration findCustomsDeclarationByCustomsDeclarationCode(
			Request request, String customsDeclarationCode, boolean impExpFlag) {
		return baseEncLogic.findCustomsDeclarationByCustomsDeclarationCode(customsDeclarationCode, impExpFlag);
	}

	/**
	 * 根据 
	 * 手册号,补充报关单类型,流水号,
	 * 开始日期（进出口日期在该日期之后）,
	 * 结束日期（进出口日期在该日期之前）, 
	 * 查询报关单
	 * @param ems
	 * @param supplmentType
	 * @param serialNumber
	 * @param begin
	 * @param end
	 * @return
	 */
	public List getCustomsDeclaration(Request request, String ems, Integer supplmentType, 
			Integer serialNumber, Date begin, Date end) {
		return baseEncDao.getCustomsDeclaration(ems, supplmentType, serialNumber, begin, end);
	}
	/**
	 * 保存补充报关单信息
	 * @param decSupplementList
	 */
	public String saveDecSupplementList(DecSupplementList decSupplementList){
	    return baseEncDao.saveDecSupplementList(decSupplementList);
	}
	public List getDecSupplementList(String supType,String baseCustomsDeclarationCommInfo){
		return baseEncDao.getDecSupplementList(supType, baseCustomsDeclarationCommInfo);
	}
	/**
	 * 通过报关单id获得报关单底下的补充报关单信息列表
	 * @param request
	 * @param decSupplementList
	 * @return
	 */
	public List getDecSupplementList(Request request, String baseCustomsDeclarationId){
		return baseEncDao.getDecSupplementList(baseCustomsDeclarationId);
	}
	/**
	 * 根据时间 补充报关单
	 * @param request
	 * @param begin
	 * @param end
	 * @return
	 */
	public List queryDecSupplementList(Request request, Date begin, Date end){
		return baseEncDao.queryDecSupplementList(begin,end);
	}
	/**
	 * 根据全部补充报关单
	 * @param request
	 * @param begin
	 * @param end
	 * @return
	 */
	public List queryDecSupplementListAll(Request request){
		return baseEncDao.queryDecSupplementListAll();
	}
	/**
	 * 删除补充报关单
	 * @param request
	 * @param date
	 * @return
	 */
	public void deleteDecSupplementList(Request request,String id){
		this.baseEncDao.deleteDecSupplementList(id);
	}
	/**
	 * 根据ID查找补充报关单
	 * @param request
	 * @param date
	 * @return
	 */
	public List getDecSupplementListById(Request request,String id){
		return this.baseEncDao.getDecSupplementListById(id);
	}
	/**
	 * 根据ID查找内部商品新增补充报关单表体
	 * @param request
	 * @param date
	 * @return
	 */
	public BaseCustomsDeclarationCommInfo getBaseCustomsDeclarationCommInfoById(String id){
		return this.baseEncDao.getBaseCustomsDeclarationCommInfoById(id);
	}
	/**
	 * 查找所有未发送的补充申报单
	 * @param request
	 * @param isSend
	 * @return
	 */
	public List findDecSupplementListByIsSend(Request request,String isSend){
		return this.baseEncDao.findDecSupplementListByIsSend(isSend);
	}

	/**
	 * 申请单商品信息来自申请单转报关单中间表
	 * 
	 * @param request
	 *            请求控制
	 * @param customs
	 *            报关商品信息
	 * @return 报关清单内容
	 */
	public List findImpExpCommodityInfoByCustomsInfo(Request request,
			BaseCustomsDeclarationCommInfo customs){
		return this.baseEncDao.findImpExpCommodityInfoByCustomsInfo(customs);
	}
	/**
	 * 获得多个申请单,报关单通过申请单转报关单中间表获得申请单
	 * @param custom 报关单
	 * @return 申请单
	 */
	public List getMultiImpExpBillByCustomsDeclaration(Request request,
			BaseCustomsDeclaration custom){
		return this.baseEncDao.getMultiImpExpBillByCustomsDeclaration(custom);
	}
	
	
//	public List importQp(Request request, String json) {
//		
////		return encImportLogic.importQp(json);
//		encImportLogic.chooseImportOrExport(json);
//		return  null;
//	}
	
	public Object transferImportDeclaration(Request request,String gson,Integer impExpFlag,Integer projectType){
		if(ImpExpFlag.IMPORT==impExpFlag){
			return encImportLogic.transferImportDeclaration(gson,impExpFlag,projectType);
		}else{
			return encImportLogic.transferExportDeclaration(gson,impExpFlag,projectType);
		}
	}
	
	public Object transferExportDeclaration(Request request,String gson,Integer impExpFlag,Integer projectType){
		return encImportLogic.transferExportDeclaration(gson,impExpFlag,projectType);
	}
	public List findCustomsDeclaration(Request request,Integer projectType,Date date,Integer impExpFlag){
		return encImportLogic.findCustomsDeclaration(projectType,date,impExpFlag);
	}
	
	public List<BaseCustomsDeclaration> findCustomsDeclaration(Request request,Integer impExpFlag,Date beginDate,Date endDate,String customsDecCode,Integer projectType,String emsNo,List impExpTypeList){
		return encImportLogic.findCustomsDeclaration(impExpFlag,beginDate,endDate,customsDecCode,projectType,emsNo,impExpTypeList);
	}
	
	public List<BaseCustomsDeclaration> findCustomsDeclaration(Request request,Integer impExpFlag,Date beginDate,Date endDate,String customsDecCode,Integer projectType,String emsNo){
		return encImportLogic.findCustomsDeclaration(impExpFlag,beginDate,endDate,customsDecCode,projectType,emsNo);
	}

    public List findAllCustomsDeclaration(Request request,Integer projectType,Integer impExpFlag){
    	return encImportLogic.findAllCustomsDeclaration(projectType,impExpFlag);
    }
	@Override
	public List importQp(Request request, String json) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getReceiverUnit(Request request){
		return this.baseEncDao.getReceiverUnit();
	}
	
	/**
	 * 查询是否显示出口装箱单或者发票
	 */
	public List findExportPackinglistOrInvoice(Request request) {
		return this.baseEncDao.findExportPackinglistOrInvoice();
	}
	

}
