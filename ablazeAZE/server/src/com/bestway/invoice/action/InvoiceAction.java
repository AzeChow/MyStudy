/*
 * Created on 2005-7-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.invoice.action;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.bestway.common.Request;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.fecav.entity.FecavParameters;
import com.bestway.invoice.entity.Invoice;
import com.bestway.invoice.entity.InvoiceParameters;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface InvoiceAction {
	/**
	 * 抓取报关单资料
	 * @param request 请求控制
	 * @param invoice 发票 
	 * @return 与指定的发票号匹配的出口报关单资料
	 */
	List findExportCustomsDeclaration(Request request, Invoice invoice);

	/**
	 * 抓取所有发票
	 * 
	 * @param request 请求控制
	 * @return 按版次号排列的发票号
	 */
	List findInvoice(Request request);

	/**
	 * 根据发票号码抓取发票
	 * @param request 请求控制 
	 * @param invoicecode 发票号
	 * @return 与指定的发票号匹配的发票
	 */
	List findInvoiceByCode(Request request, String invoicecode);

	/**
	 * 根据状态抓取发票
	 * @param request 请求控制
	 * @param state 状态
	 * @return 指定状态匹配的所有发票 按发票号排列
	 */
	List findInvoiceByState(Request request, Integer state);
	
	/**
	 * 根据状态和开始、结束的发票号抓取发票
	 * 
	 * @param state 状态
	 * @param startInvoiceCode 开始发票号
	 * @param endInvoiceCode 结束发票号
	 * @return 指定状态匹配的所有发票 按发票号排列
	 */
	List findInvoiceByState(Request request, Integer state,String startInvoiceCode,String endInvoiceCode);

	/**
	 * 保存发票
	 * @param request 请求控制 
	 * @param invoice  发票
	 * @return 保存后的发票
	 */
	Invoice saveInvoice(Request request, Invoice invoice);

	/**
	 * 删除发票
	 * @param request 请求控制
	 * @param invoice 发票
	 */
	void deleteInvoice(Request request, Invoice invoice);

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
	 * 
	 * @param year
	 *        年号
	 */
	List autoMakeInvoice(Request request, String versionCode,
            String beginSerialNo, String endSerialNo,String year);

	/**
	 * 使用发票
	 * @param request 请求控制
	 * @param invoice 发票
	 * @return 使用状态的发票
	 */
	Invoice useInvoice(Request request, Invoice invoice);

	/**
	 * 作废发票
	 * @param request 请求控制
	 * @param invoice 发票
	 * @return 作废状态的发票
	 */
	Invoice cancelInvoice(Request request, Invoice invoice);

	/**
	 * 核销发票
	 * @param request 请求控制
	 * @param invoice  发票
	 * @return 核销状态的发票
	 */
	Invoice cancelAfterVerificationInvoice(Request request, Invoice invoice);

	/**
	 * 抓取报关单号为空的发票
	 * @param request 请求控制
	 * @param customsDeclarationDate  有效期
	 * @return 在有效期内状态为领用和使用且报关单号为空的发票
	 */
	List findInvoiceCustomsDeclarationCodeIsNull(Request request,
			Date customsDeclarationDate);

	/**
	 * 发票使用状态时,回卷
	 * @param request 请求控制
	 * @param invoice 发票
	 * @param invoiceState 发票状态
	 * @return 回卷后的发票
	 */
	Invoice rollBackInvoice(Request request, Invoice invoice,int invoiceState);

	/**
	 * 抓取报关单号为空的发票
	 * @param request 请求控制
	 * @return 状态为领用和使用且报关单号为空的所有发票
	 */
	List<Invoice> findInvoiceCustomsDeclarationCodeIsNull(Request request);

	/**
	 * 保存发票
	 * @param request 请求控制
	 * @param projectType
	 *            工程类型
	 * @param baseCustomsDeclaration
	 *            修改过的报关单
	 * @param oldInvoiceCode
	 *            修改前的核消单号 null
	 * @param  actionState 动作状态 选择 新增 修改 删除       
	 */
	void saveInvoice(Request request,int projectType, int actionState,
			BaseCustomsDeclaration baseCustomsDeclaration, String oldInvoiceCode);
	
	void saveInvoiceforcustoms(Request request,int projectType, int actionState,
			BaseCustomsDeclaration baseCustomsDeclaration, String oldInvoiceCode);
	/**
	 * 出口商品发票各种情况统计表
	 * @param request 请求控制
	 * @param beginDate 录入日期
	 * @param endDate  截止日期
	 * @return 发票各种状态的份数
	 */
	List findInvoiceForObtainStat(Request request,Date beginDate, Date endDate);
	/**
	 * 检查发票
	 * @param  request 请求控制
	 * @param  invoice 发票 
	 * @return 初始状态为领用 输入使用日期 自动改状态为使用状态 
	 */ 
	Invoice checkInvoice(Request request,Invoice invoice);
	
	/**
	 * 批量删除发票
	 * 
	 * @param  request 请求控制
	 * @param invoiceList 发票list
	 */
	void deleteAll(Request request,List invoiceList);
	/**
	 * 查询出口专用发票参数
	 * 
	 * @return
	 */
	InvoiceParameters findInvoiceParameters(Request request);

	/**
	 * 保存出口专用发票参数
	 * 
	 * @param fecavParameters
	 */
	InvoiceParameters saveInvoiceParameters(Request request,
			InvoiceParameters invoiceParameters);
	/**
	 *根据状态抓取发票号最小的发票
	 * @param request
	 * @param invoiceState
	 * @return
	 */
	public Invoice findInvoiceOfMinInvoiceCodeByInvoiceState(Request request,int invoiceState);
}
