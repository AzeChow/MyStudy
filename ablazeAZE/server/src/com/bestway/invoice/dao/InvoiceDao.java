/*
 * Created on 2005-7-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.invoice.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.InvoiceState;
import com.bestway.common.constant.ProjectType;
import com.bestway.fecav.entity.FecavParameters;
import com.bestway.invoice.entity.Invoice;
import com.bestway.invoice.entity.InvoiceParameters;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class InvoiceDao extends BaseDao {

	/**
	 * 抓取报关单资料
	 * @param projectType 报关单资料来源
	 * @param beginDate 申报日期
	 * @param endDate 截止日期
	 * @return 出口报关单资料
	 */
	public List findExportCustomsDeclaration(int projectType, Date beginDate,
			Date endDate) {
		String hql = "select a.baseCustomsDeclaration.id,a.baseCustomsDeclaration.customsDeclarationCode,"
				+ "sum(a.commTotalPrice) ";
		switch (projectType) {
		case ProjectType.BCUS:
			hql += " from CustomsDeclarationCommInfo as a";
			break;
		case ProjectType.BCS:
			hql += " from BcsCustomsDeclarationCommInfo as a";
			break;
		default:
			hql += " from CustomsDeclarationCommInfo as a";
			break;
		}
		hql += " where a.baseCustomsDeclaration.impExpFlag=? and a.baseCustomsDeclaration.company.id=?"
				+ " and (a.baseCustomsDeclaration.invoiceCode is null or a.baseCustomsDeclaration.invoiceCode=?) "
				+ " and a.baseCustomsDeclaration.customsDeclarationCode not in"
				+ " (select distinct b.customsDeclaredCode from Invoice as b where b.company.id=? "
				+ " and b.customsDeclaredCode is not null and b.state!=? ) "
				+ " and a.baseCustomsDeclaration.declarationDate>=? and a.baseCustomsDeclaration.declarationDate<=? "
				+ " group by a.baseCustomsDeclaration.id,a.baseCustomsDeclaration.customsDeclarationCode ";
		return this.find(hql, new Object[] {
				Integer.valueOf(ImpExpFlag.EXPORT),
				CommonUtils.getCompany().getId(), "",
				CommonUtils.getCompany().getId(), InvoiceState.CANCELED,
				beginDate, endDate });
	}

	/**
	 * 抓取所有发票
	 * @return 按版次号排列的发票号
	 */
	public List findInvoice() {
		return this.find("select a from Invoice as a where a.company.id=? "
				+ " order by a.versionCode, a.invoiceCode ",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 抓取所有发票通过id
	 * @param id 公司id
	 * @return 以公司分类的所有发票
	 */
	public Invoice findInvoiceById(String id) {
		List list = this.find(
				"select a from Invoice as a where a.company.id=? and a.id=?"
						+ " order by a.versionCode,a.invoiceCode ",
				new Object[] { CommonUtils.getCompany().getId(), id });
		if (list.size() > 0) {
			return (Invoice) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据发票号码抓取发票
	 * 
	 * @param invoicecode 发票号
	 * @return 与指定的发票号匹配的发票
	 */
	public List findInvoiceByCode(String invoicecode) {
		return this.find(
				"select a from Invoice as a where a.invoiceCode=? and a.company.id=? ",
				new Object[] { invoicecode, CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据状态抓取发票
	 * @param state 状态
	 * @return 指定状态匹配的所有发票 按发票号排列 
	 */
	public List findInvoiceByState(Integer state) {
		return this.findNoCache("select a from Invoice as a where a.state=? and a.company.id=?"
				+ " order by a.versionCode,a.invoiceCode ", new Object[] {
				state, CommonUtils.getCompany().getId() });
	}
	
	/**
	 * 根据状态和开始、结束的发票号抓取发票
	 * 
	 * @param state 状态
	 * @param startInvoiceCode 开始发票号
	 * @param endInvoiceCode 结束发票号
	 * @return 指定状态匹配的所有发票 按发票号排列
	 */
	public List findInvoiceByState(Integer state,String startInvoiceCode,String endInvoiceCode) {
		List<Object> parameters = new ArrayList<Object>();
		String hql;
		hql="select a from Invoice as a where a.state=? and a.company.id=? ";
		parameters.add(state);
		parameters.add(CommonUtils.getCompany().getId());
		if(startInvoiceCode!=null&&!(startInvoiceCode.equals(""))){
			hql=hql+ " and a.invoiceCode>=? ";
			parameters.add(startInvoiceCode);
		}
		if(endInvoiceCode!=null&&!(endInvoiceCode.equals(""))){
			hql=hql+ " and a.invoiceCode<=? ";
			parameters.add(endInvoiceCode);
		}
		hql=hql+ " order by a.versionCode,a.invoiceCode ";
		return this.findNoCache(hql,parameters.toArray());
	}
	
	
	/**
	 * 抓取报关单号为空的发票,根据
	 * @param customsDeclarationDate 有效期
	 * @return  在有效期内状态为领用和使用且报关单号为空的发票
	 */
	public List findInvoiceCustomsDeclarationCodeIsNull(
			Date customsDeclarationDate) {
		return this.find(
				"select a from Invoice as a where a.state in (?,?) and a.company.id=?"
						+ " and a.beginDate<=? and a.endDate>=? "
						+ " and a.customsDeclarationId is not null"
						+ " and a.customsDeclarationId!=? "
						+ " order by a.versionCode,a.invoiceCode ",
				new Object[] { InvoiceState.DRAFT, InvoiceState.USED,
						CommonUtils.getCompany().getId(),
						customsDeclarationDate, customsDeclarationDate, "" });
	}

	/**
	 * 抓取报关单号为空的发票
	 * @return 状态为领用和使用且报关单号为空的所有发票
	 */
	public List findInvoiceCustomsDeclarationCodeIsNull() {
		return this.find(
				"select a from Invoice as a where a.state in (?,?) and a.company.id=?"
				// + " and a.beginDate<=? and a.endDate>=? "
						+ " and (a.customsDeclarationId is null"
						+ " or a.customsDeclarationId = ? ) "
						+ " order by a.versionCode,a.invoiceCode ",
				new Object[] { InvoiceState.DRAFT, InvoiceState.USED,
						CommonUtils.getCompany().getId(), "" });
	}

	/**
	 * 保存发票
	 * 
	 * @param invoice 发票
	 */
	public void saveInvoice(Invoice invoice) {
		this.saveOrUpdate(invoice);
	}

	/**
	 * 删除发票
	 * 
	 * @param invoice 发票
	 */
	public void deleteInvoice(Invoice invoice) {
		this.delete(invoice);
	}

	/**
	 * 取得报关单商品总金额
	 * @param projectType 报关单来源
	 * @param customsDeclarationId 报关单id
	 * @return 与指定的报关单id匹配的报关单中的金额
	 */
	public double findCustomsDeclarationMoney(int projectType,
			String customsDeclarationId) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select sum(a.commTotalPrice) from CustomsDeclarationCommInfo a ";
			break;
		case ProjectType.BCS:
			hql = "select sum(a.commTotalPrice) from BcsCustomsDeclarationCommInfo a ";
			break;
		default:
			hql = "select sum(a.commTotalPrice) from CustomsDeclarationCommInfo a ";
			break;
		}
		hql += " where a.baseCustomsDeclaration.company.id=?"
				+ " and a.baseCustomsDeclaration.id=? ";
		List list = this.find(hql, new Object[] {
				CommonUtils.getCompany().getId(), customsDeclarationId });
		if (list.size() < 0 || list.get(0) == null) {
			return 0.0;
		} else {
			return Double.parseDouble(list.get(0).toString());
		}
	}

	/**
	 * 根据日期区间查询,查询期初发票的库存数
	 * 
	 * @param beginDate 录入日期
	 * @param endDate   截止日期
	 * @return 领用状态且未被录入的库存发票
	 */
	public List findOriginalInvoiceForStat(Date beginDate, Date endDate) {
		String hql = "select a  from Invoice as a where a.company.id=? and a.state=? "
				+ " and a.draftDate<? ";
		return this.find(hql, new Object[] { CommonUtils.getCompany().getId(),
				InvoiceState.DRAFT, beginDate });
	}

	/**
	 * 根据日期区间查询,这个区间内领用的发票(领用,使用,核销,作废)
	 * 
	 * @param beginDate 录入日期
	 * @param endDate   截止日期
	 * @return  在指定的时间区域内所有领用的发票
	 */
	public List findDraftInvoiceForStat(Date beginDate, Date endDate) {
		String hql = "select a  from Invoice as a where a.company.id=? "
				+ " and a.draftDate>=? and a.draftDate<=? ";
		return this.find(hql, new Object[] { CommonUtils.getCompany().getId(),
				beginDate, endDate });
	}

	/**
	 * 根据日期区间查询,这个区间内使用的发票(使用)
	 * 
	 * @param beginDate 录入日期
	 * @param endDate   截止日期
	 * @return 在有效期内使用状态的发票
	 */
	public List findUsedInvoiceForStat(Date beginDate, Date endDate) {
		String hql = "select a  from Invoice as a where a.company.id=? and a.state=? "
				+ " and a.usedDate>=? and a.usedDate<=? ";
		return this.find(hql, new Object[] { CommonUtils.getCompany().getId(),
				InvoiceState.USED, beginDate, endDate });
	}

	/**
	 * 根据日期区间查询,这个区间内作废的发票(使用)
	 * 
	 * @param beginDate  录入日期
	 * @param endDate    截止日期
	 * @return 在指定的时间内作废的发票
	 */
	public List findCanceledInvoiceForStat(Date beginDate, Date endDate) {
		String hql = "select a  from Invoice as a where a.company.id=? and a.state=? "
				+ " and a.canceledDate>=? and a.canceledDate<=? ";
		return this.find(hql, new Object[] { CommonUtils.getCompany().getId(),
				InvoiceState.CANCELED, beginDate, endDate });
	}

	/**
	 * 根据日期区间查询,查询期初发票的库存数
	 * 
	 * @param beginDate 录入日期
	 * @param endDate  截止日期
	 * @return 领用状态的期初发票
	 */
	public List findFinalInvoiceForStat(Date beginDate, Date endDate) {
		String hql = "select a  from Invoice as a where a.company.id=? and a.state=? "
				+ " and a.draftDate<=? ";
		return this.find(hql, new Object[] { CommonUtils.getCompany().getId(),
				InvoiceState.DRAFT, endDate });
	}
	
	/**
	 * 批量删除发票
	 * 
	 * @param invoiceList 发票list
	 */
	public void deleteAll(List invoiceList) {
		Iterator iterator = invoiceList.iterator();
		while (iterator.hasNext()) {
			this.delete(iterator.next());
		}
	} 
	
	/**
	 * 查询发票参数
	 * @return
	 */
	public InvoiceParameters findInvoiceParameters() {
		List list = this.find(
				"select a from InvoiceParameters a where a.company.id= ? ",
				new Object[] { CommonUtils.getCompany().getId() });
		if (list.size() > 0) {
			return (InvoiceParameters) list.get(0);
		} else {
			InvoiceParameters p = new InvoiceParameters();
			p.setIsInvoiceWithyear(false);
			this.saveInvoiceParameters(p);
			return p;
		}
	}
	
	/**
	 * 保存出口专用发票参数
	 * 
	 * @param InvoiceParameters
	 */
	public void saveInvoiceParameters(InvoiceParameters invoiceParameters) {
		this.saveOrUpdate(invoiceParameters);
	}
	/**
	 *根据状态抓取发票号最小的发票
	 * @param request
	 * @param invoiceState
	 * @return
	 */
	public Invoice findInvoiceOfMinInvoiceCodeByInvoiceState(int invoiceState){
		List list = this.find(
				"select a from Invoice as a where a.company.id=? and a.state=?"
						+ " order by a.versionCode,a.invoiceCode ",
				new Object[] { CommonUtils.getCompany().getId(), invoiceState});
		if (list.size() > 0) {
			return (Invoice) list.get(0);
		} else {
			return null;
		}
	}
}
