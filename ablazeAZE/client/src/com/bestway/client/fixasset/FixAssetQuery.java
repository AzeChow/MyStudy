package com.bestway.client.fixasset;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.Request;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.fixasset.action.FixAssetAction;
import com.bestway.fixasset.entity.Agreement;
import com.bestway.fixasset.entity.DutyCertHead;
import com.bestway.fixtureonorder.action.FixtureContractAction;

public class FixAssetQuery {
	private static FixAssetQuery fixAssetQuery = null;

	private static FixAssetAction fixAssetAction = null;
	
	private static FixtureContractAction fixtureContractAction = null;

	private FixAssetQuery() {

	}

	public static FixAssetQuery getInstance() {
		if (fixAssetQuery == null) {
			fixAssetQuery = new FixAssetQuery();
			fixAssetAction = (FixAssetAction) CommonVars
					.getApplicationContext().getBean("fixAssetAction");
			fixtureContractAction = (FixtureContractAction) CommonVars
			.getApplicationContext().getBean("fixtureContractAction");
		}
		return fixAssetQuery;
	}

	/**
	 * 查询没有内部领用的核销单
	 * 
	 * @return
	 */
	public List findComplexNotInAgreement(Agreement agreement) {
		List dataSource = fixAssetAction.findComplexNotInAgreement(new Request(
				CommonVars.getCurrUser()), agreement);
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("编码 ", "code", 200));
		list.add(new JTableListColumn("名称", "name", 100));
		list.add(new JTableListColumn("第一法定单位", "firstUnit.name", 100));
		list.add(new JTableListColumn("第二法定单位", "secondUnit.name", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("商品");
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 抓取正在执行的征免税
	 * 
	 * @return
	 */
	public Object findDutyCertHeadExecuting() {
		List dutyCertHead = fixAssetAction.findDutyCertHeadExecuting(new Request(
				CommonVars.getCurrUser()));
		List fixtureContract=fixtureContractAction.findContractByProcessExe(new Request(
				CommonVars.getCurrUser()));
		return getDate(dutyCertHead,fixtureContract );
	}

	public Object getDate(List dutyCertHead, List fixtureContract){
		List arrayList=new ArrayList();
		if((dutyCertHead!=null&&dutyCertHead.size()>0)&&(fixtureContract!=null&&fixtureContract.size()>0)){
			arrayList.addAll(dutyCertHead);
			arrayList.addAll(fixtureContract);
			List<JTableListColumn> list = new Vector<JTableListColumn>();
			list.add(new JTableListColumn("设备类型", "type", 100));
			list.add(new JTableListColumn("手册编号", "emsNo", 100));
			list.add(new JTableListColumn("合同号", "impContractNo", 100));
			list.add(new JTableListColumn("协议号", "sancEmsNo", 100));
			list.add(new JTableListColumn("征免税号", "certNo", 100));
			list.add(new JTableListColumn("申报日期", "beginDate", 100));
			list.add(new JTableListColumn("批准日期", "endDate", 100));
			DgCommonQuery.setTableColumns(list);
			final DgCommonQuery dgCommonQuery = new DgCommonQuery();
			dgCommonQuery.setDataSource(arrayList);
			dgCommonQuery.setTitle("设备协议");
			DgCommonQuery.setSingleResult(true);
			dgCommonQuery.setVisible(true);
			if (dgCommonQuery.isOk()) {
				return dgCommonQuery.getReturnValue();
			}
		}else if(dutyCertHead==null||dutyCertHead.size()==0){
			arrayList.addAll(fixtureContract);
			List<JTableListColumn> list = new Vector<JTableListColumn>();
			list.add(new JTableListColumn("手册编号", "emsNo", 100));
			list.add(new JTableListColumn("合同号", "impContractNo", 100));
			list.add(new JTableListColumn("协议书号", "agreementNo", 100));
			list.add(new JTableListColumn("批文号", "sancEmsNo", 100));
			list.add(new JTableListColumn("开始日期", "beginDate", 100));
			DgCommonQuery.setTableColumns(list);
			final DgCommonQuery dgCommonQuery = new DgCommonQuery();
			dgCommonQuery.setDataSource(arrayList);
			dgCommonQuery.setTitle("来料设备协议");
			DgCommonQuery.setSingleResult(true);
			dgCommonQuery.setVisible(true);
			if (dgCommonQuery.isOk()) {
				return dgCommonQuery.getReturnValue();
			}
		}else if(fixtureContract==null||fixtureContract.size()==0){
			arrayList.addAll(dutyCertHead);
			List<JTableListColumn> list = new Vector<JTableListColumn>();
			list.add(new JTableListColumn("协议批文号", "sancEmsNo", 100));
			list.add(new JTableListColumn("征免税号", "certNo", 100));
			list.add(new JTableListColumn("申报日期", "beginDate", 100));
			list.add(new JTableListColumn("批准日期", "endDate", 100));
			DgCommonQuery.setTableColumns(list);
			final DgCommonQuery dgCommonQuery = new DgCommonQuery();
			dgCommonQuery.setDataSource(arrayList);
			dgCommonQuery.setTitle("设备协议");
			DgCommonQuery.setSingleResult(true);
			dgCommonQuery.setVisible(true);
			if (dgCommonQuery.isOk()) {
				return dgCommonQuery.getReturnValue();
			}
		}
		return null;
	}
	
	/**
	 * 查询批文协议设备征免税证明细
	 * 
	 * @param agreement
	 * @return
	 */
	public List findDutyCertDetailByCertNo(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		List dataSource = fixAssetAction.findDutyCertDetailByCertNo(
				new Request(CommonVars.getCurrUser()), baseCustomsDeclaration);
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("协议批文号 ", "certHead.sancEmsNo", 200));
		list.add(new JTableListColumn("征免税号", "certHead.certNo", 100));
		list.add(new JTableListColumn("海关编号", "complex.code", 100));
		list.add(new JTableListColumn("名称", "commName", 100));
		list.add(new JTableListColumn("规格", "commSpec", 100));
		list.add(new JTableListColumn("单价", "unitPrice", 100));
		list.add(new JTableListColumn("数量", "amount", 100));
		list.add(new JTableListColumn("金额", "totalPrice", 100));
		list.add(new JTableListColumn("单位", "unit.name", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("征免税证明");
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}
}
