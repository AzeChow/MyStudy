package com.bestway.bcs.client.bcsinnermerge;



import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.bestway.bcs.bcsinnermerge.action.BcsInnerMergeAction;
import com.bestway.bcs.bcsinnermerge.entity.BcsCustomsBomExg;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.Request;
import com.bestway.dzsc.innermerge.entity.DzscFourInnerMerge;
import com.bestway.dzsc.innermerge.entity.DzscTenInnerMerge;

public class BcsInnerMergeQuery {
	private static BcsInnerMergeQuery query = null;

	private BcsInnerMergeQuery() {

	}

	public static BcsInnerMergeQuery getInstance() {
		if (query == null) {
			query = new BcsInnerMergeQuery();
		}
		return query;
	}

//	/**
//	 * 查询已经存在的10码商品
//	 * 
//	 * @param version
//	 * @return
//	 */
//	public DzscTenInnerMerge findDzscTenInnerMerge(String imrType,
//			boolean isChange) {
//		BcsInnerMergeAction dzscInnerMergeAction = (BcsInnerMergeAction) CommonVars
//				.getApplicationContext().getBean("dzscInnerMergeAction");
//		List lsDataSource = dzscInnerMergeAction.findAllDzscTenInnerMerge(
//				new Request(CommonVars.getCurrUser(), true), imrType, isChange);
//		List<JTableListColumn> list = new Vector<JTableListColumn>();
//		list.add(new JTableListColumn("备案序号", "tenSeqNum", 100, Integer.class));
//		list.add(new JTableListColumn("商品编码", "tenComplex.code", 100));
//		list.add(new JTableListColumn("商品名称", "tenPtName", 150));
//		list.add(new JTableListColumn("型号规格", "tenPtSpec", 150));
//		list.add(new JTableListColumn("报关单位", "tenUnit.name", 100));
//
//		DgCommonQuery.setTableColumns(list);
//		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
//		dgCommonQuery.setDataSource(lsDataSource);
//		DgCommonQuery.setSingleResult(true);
//		dgCommonQuery.setTitle("已经存在的10码商品");
//		dgCommonQuery.setVisible(true);
//		if (dgCommonQuery.isOk()) {
//			return (DzscTenInnerMerge) dgCommonQuery.getReturnValue();
//		}
//		return null;
//	}
//	/**
//	 * 查询已经存在的4码商品
//	 * 
//	 * @param version
//	 * @return
//	 */
//	public DzscFourInnerMerge findDzscFourInnerMerge(String imrType,
//			boolean isChange) {
//		BcsInnerMergeAction dzscInnerMergeAction = (BcsInnerMergeAction) CommonVars
//				.getApplicationContext().getBean("dzscInnerMergeAction");
//		List lsDataSource = dzscInnerMergeAction.findAllDzscFourInnerMerge(
//				new Request(CommonVars.getCurrUser(), true), imrType, isChange);
//		List<JTableListColumn> list = new Vector<JTableListColumn>();
//		list.add(new JTableListColumn("备案序号", "fourSeqNum", 100, Integer.class));
//		list.add(new JTableListColumn("商品编码", "fourComplex", 100));
//		list.add(new JTableListColumn("商品名称", "fourPtName", 150));
//		list.add(new JTableListColumn("型号规格", "fourPtSpec", 150));
//		list.add(new JTableListColumn("报关单位", "fourUnit.name", 100));
//
//		DgCommonQuery.setTableColumns(list);
//		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
//		dgCommonQuery.setDataSource(lsDataSource);
//		DgCommonQuery.setSingleResult(true);
//		dgCommonQuery.setTitle("已经存在的4码商品");
//		dgCommonQuery.setVisible(true);
//		if (dgCommonQuery.isOk()) {
//			return (DzscFourInnerMerge) dgCommonQuery.getReturnValue();
//		}
//		return null;
//	}
	/**
	 * 查询10码成品资料
	 * 
	 * @param version
	 * @return
	 */
	public List findInnerMergeNotInCustomsBomExg() {
		BcsInnerMergeAction bcsInnerMergeAction = (BcsInnerMergeAction) CommonVars
				.getApplicationContext().getBean("bcsInnerMergeAction");
		List lsDataSource = bcsInnerMergeAction
				.findInnerMergeNotInCustomsBomExg(new Request(CommonVars
						.getCurrUser(), true));
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("归并序号", "bcsTenInnerMerge.seqNum", 100, Integer.class));
		list.add(new JTableListColumn("商品编码", "bcsTenInnerMerge.complex.code", 100));
		list.add(new JTableListColumn("商品名称", "bcsTenInnerMerge.name", 150));
		list.add(new JTableListColumn("型号规格", "bcsTenInnerMerge.spec", 150));
		list.add(new JTableListColumn("报关单位", "bcsTenInnerMerge.comUnit.name", 100));

		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(lsDataSource);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setTitle("10码成品资料");
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return new ArrayList();
	}

	/**
	 * 查询10码料件资料
	 * 
	 * @param version
	 * @return
	 */
	public List findInnerMergeNotInCustomsBomDetail(BcsCustomsBomExg exg) {
		BcsInnerMergeAction dzscInnerMergeAction = (BcsInnerMergeAction) CommonVars
				.getApplicationContext().getBean("bcsInnerMergeAction");
		List lsDataSource = dzscInnerMergeAction
				.findInnerMergeNotInCustomsBomDetail(new Request(CommonVars
						.getCurrUser(), true), exg);
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("备案序号", "bcsTenInnerMerge.seqNum", 100, Integer.class));
		list.add(new JTableListColumn("商品编码", "bcsTenInnerMerge.complex.code", 100));
		list.add(new JTableListColumn("商品名称", "bcsTenInnerMerge.name", 150));
		list.add(new JTableListColumn("型号规格", "bcsTenInnerMerge.spec", 150));
		list.add(new JTableListColumn("报关单位", "bcsTenInnerMerge.comUnit.name", 100));

		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(lsDataSource);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setTitle("10码料件资料");
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return new ArrayList();
	}
}
