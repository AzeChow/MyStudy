/*
 * Created on 2005-7-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.invoice.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.bestway.common.Request;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.invoice.dao.InvoiceDao;
import com.bestway.invoice.entity.Invoice;
import com.bestway.invoice.entity.InvoiceParameters;
import com.bestway.invoice.entity.TempInvoiceUsedStatInfo;
import com.bestway.invoice.logic.InvoiceLogic;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.fecav.entity.FecavParameters;


/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
@AuthorityClassAnnotation(caption = "出口专用发票管理", index = 8)
public class InvoiceActionImpl extends BaseActionImpl implements InvoiceAction {
	/**
	 * 发票Dao层
	 */
	private InvoiceDao invoiceDao;

	/**
	 * 发票Logic层
	 */
	private InvoiceLogic invoiceLogic;

	/**
	 * @return invoiceLogic 
	 */
	public InvoiceLogic getInvoiceLogic() {
		return invoiceLogic;
	}

	/**
	 * @param invoiceLogic  发票Logic层
	 */
	public void setInvoiceLogic(InvoiceLogic invoiceLogic) {
		this.invoiceLogic = invoiceLogic;
	}

	/**
	 * @return Returns  invoiceDao.
	 */
	public InvoiceDao getInvoiceDao() {
		return invoiceDao;
	}

	/**
	 * @param invoiceDao 发票Dao层
	 */
	public void setInvoiceDao(InvoiceDao invoiceDao) {
		this.invoiceDao = invoiceDao;
	}

	/**
	 * 抓取报关单资料
	 * @param request 发送请求
	 * @param invoice 发票 
	 * @return 与指定的发票号匹配的出口报关单资料
	 */
	public List findExportCustomsDeclaration(Request request, Invoice invoice) {
		return this.invoiceLogic.findExportCustomsDeclaration(invoice);
	}

	/**
	 * 抓取所有发票
	 * 
	 * @param request 发送请求
	 * @return 按版次号排列的发票号
	 */
	@AuthorityFunctionAnnotation(caption = "出口专用发票管理--浏览", index = 1)
	public List findInvoice(Request request) {
		return this.invoiceDao.findInvoice();
	}

	/**
	 * 根据发票号码抓取发票
	 * @param request 发送请求 
	 * @param invoicecode 发票号
	 * @return 与指定的发票号匹配的发票
	 */
	public List findInvoiceByCode(Request request, String invoicecode) {
		return this.invoiceDao.findInvoiceByCode(invoicecode);
	}

	/**
	 * 根据状态抓取发票
	 * @param request 发送请求
	 * @param state 状态
	 * @return 指定状态匹配的所有发票 按发票号排列
	 */
	@AuthorityFunctionAnnotation(caption = "出口专用发票管理--浏览", index = 1)
	public List findInvoiceByState(Request request, Integer state) {
		return this.invoiceDao.findInvoiceByState(state);
	}

	/**
	 * 根据状态和开始、结束的发票号抓取发票
	 * 
	 * @param state 状态
	 * @param startInvoiceCode 开始发票号
	 * @param endInvoiceCode 结束发票号
	 * @return 指定状态匹配的所有发票 按发票号排列
	 */
	@AuthorityFunctionAnnotation(caption = "出口专用发票管理--浏览", index = 1)
	public List findInvoiceByState(Request request, Integer state,String startInvoiceCode,String endInvoiceCode){
		return this.invoiceLogic.findInvoiceByState(state,startInvoiceCode,endInvoiceCode);
	}

	/**
	 * 保存发票
	 * @param request 发送请求 
	 * @param invoice  发票
	 * @return 保存后的发票
	 */
	@AuthorityFunctionAnnotation(caption = "出口专用发票管理--编辑", index = 2)
	public Invoice saveInvoice(Request request, Invoice invoice) {
		this.invoiceLogic.saveInvoice(invoice);
		return invoice;
	}
	/**
	 * 删除发票
	 * @param request 发送请求
	 * @param invoice 发票
	 */
	@AuthorityFunctionAnnotation(caption = "出口专用发票管理--删除", index = 3)
	public void deleteInvoice(Request request, Invoice invoice) {
		this.invoiceLogic.deleteInvoice(invoice);
	}

	/**
	 * 自动批量生成发票
	 * 
	 * @param versionCode
	 *            发票版次号
	 * @param beginSerialNo
	 *            开始流水号
	 * @param endSerialNo
	 *            结束流水号
	 * @return 自动批量生成发票
	 */
	@AuthorityFunctionAnnotation(caption = "出口专用发票管理--编辑", index = 2)
	public List autoMakeInvoice(Request request, String versionCode,
            String beginSerialNo,
            String endSerialNo,
            String year) {
		return this.invoiceLogic.autoMakeInvoice(versionCode, beginSerialNo,
				endSerialNo,year);
	}

	/**
	 * 使用发票
	 * @param request 发送请求
	 * @param invoice 发票
	 * @return 使用状态的发票
	 */
	@AuthorityFunctionAnnotation(caption = "出口专用发票管理--编辑", index = 2)
	public Invoice useInvoice(Request request, Invoice invoice) {
		this.invoiceLogic.useInvoice(invoice);
		return invoice;
	}

	/**
	 * 作废发票
	 * @param request 发送请求
	 * @param invoice 发票
	 * @return 作废状态的发票
	 */
	@AuthorityFunctionAnnotation(caption = "出口专用发票管理--编辑", index = 2)
	public Invoice cancelInvoice(Request request, Invoice invoice) {
		this.invoiceLogic.cancelInvoice(invoice);
		return invoice;
	}

	/**
	 * 核销发票
	 * @param request 发送请求
	 * @param invoice  发票
	 * @return 核销状态的发票
	 */
	@AuthorityFunctionAnnotation(caption = "出口专用发票管理--编辑", index = 2)
	public Invoice cancelAfterVerificationInvoice(Request request,
			Invoice invoice) {
		this.invoiceLogic.cancelAfterVerificationInvoice(invoice);
		return invoice;
	}

	/**
	 * 抓取报关单号为空的发票
	 * @param request 发送请求
	 * @param customsDeclarationDate  有效期
	 * @return 在有效期内状态为领用和使用且报关单号为空的发票
	 */
	public List findInvoiceCustomsDeclarationCodeIsNull(Request request,
			Date customsDeclarationDate) {
		return this.invoiceDao
				.findInvoiceCustomsDeclarationCodeIsNull(customsDeclarationDate);
	}

	/**
	 * 发票使用状态时,回卷
	 * @param request 发送请求
	 * @param invoice 发票
	 * @param invoiceState 发票状态
	 * @return 回卷后的发票
	 */
	@AuthorityFunctionAnnotation(caption = "出口专用发票管理--编辑", index = 2)
	public Invoice rollBackInvoice(Request request, Invoice invoice,int invoiceState) {
		this.invoiceLogic.rollBackInvoice(invoice,invoiceState);
		return invoice;
	}

	/**
	 * 抓取报关单号为空的发票
	 * @param request 发送请求
	 * @return 状态为领用和使用且报关单号为空的所有发票
	 */
	public List<Invoice> findInvoiceCustomsDeclarationCodeIsNull(Request request) {
		return this.invoiceDao.findInvoiceCustomsDeclarationCodeIsNull();
	}

	/**
	 * 保存发票
	 * @param request 发送请求
	 * @param projectType
	 *            工程类型
	 * @param baseCustomsDeclaration
	 *            修改过的报关单
	 * @param oldInvoiceCode
	 *            修改前的核消单号 null
	 * @param  actionState 动作状态 选择 新增 修改 删除       
	 */
	@AuthorityFunctionAnnotation(caption = "出口专用发票管理--编辑", index = 2)
	public void saveInvoice(Request request,int projectType, int actionState,
			BaseCustomsDeclaration baseCustomsDeclaration, String oldInvoiceCode) {
		this.invoiceLogic.saveInvoice(projectType, actionState,
				baseCustomsDeclaration, oldInvoiceCode);
	}
	
	public void saveInvoiceforcustoms(Request request,int projectType, int actionState,
			BaseCustomsDeclaration baseCustomsDeclaration, String oldInvoiceCode) {
		this.invoiceLogic.saveInvoice(projectType, actionState,
				baseCustomsDeclaration, oldInvoiceCode);
	}
	
	/**
	 * 出口商品发票各种情况统计表
	 * @param request 发送请求
	 * @param beginDate 录入日期
	 * @param endDate  截止日期
	 * @return 发票各种状态的份数
	 */
	@AuthorityFunctionAnnotation(caption = "出口商品发票领用统计表--查询", index = 4)
	public List findInvoiceForObtainStat(Request request,Date beginDate, Date endDate) {
		return this.invoiceLogic.findInvoiceForObtainStat(beginDate, endDate);
	}
	/**
	 * 检查发票
	 * @param  request 发送请求
	 * @param  invoice 发票
	 * @return 初始状态为领用 输入使用日期 自动改状态为使用状态 
	 */
	@AuthorityFunctionAnnotation(caption = "出口专用发票管理--编辑", index = 2)
	public Invoice checkInvoice(Request request, Invoice invoice) {
	    return this.invoiceLogic.checkInvoice(invoice);
		
	}

	/**
	 * 批量删除发票
	 * 
	 * @param  request 请求控制
	 * @param invoiceList 发票list
	 */
	public void deleteAll(Request request,List invoiceList){
		this.invoiceDao.deleteAll(invoiceList);
	}

	public InvoiceParameters findInvoiceParameters(Request request) {
		// TODO Auto-generated method stub
		return this.invoiceDao.findInvoiceParameters();
	}

	public InvoiceParameters saveInvoiceParameters(Request request,
			InvoiceParameters invoiceParameters) {
		// TODO Auto-generated method stub
		this.invoiceDao.saveInvoiceParameters(invoiceParameters);
		return invoiceParameters;
	}
	/**
	 *根据状态抓取发票号最小的发票
	 * @param request
	 * @param invoiceState
	 * @return
	 */
	public Invoice findInvoiceOfMinInvoiceCodeByInvoiceState(Request request,int invoiceState){
		return this.invoiceDao.findInvoiceOfMinInvoiceCodeByInvoiceState(invoiceState);
	}
//	public Invoice checkInvoice(Request request, Invoice invoice) {
//		// 
//		return null;
//	}
}
