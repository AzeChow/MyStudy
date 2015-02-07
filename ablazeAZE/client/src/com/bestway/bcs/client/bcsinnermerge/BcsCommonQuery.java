/*
 * Created on 2005-3-28
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.bcsinnermerge;

import java.util.List;
import java.util.Vector;

import com.bestway.bcs.bcsinnermerge.action.BcsInnerMergeAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgCommonQueryPage;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.Request;

/**
 * @author xxm
 * 客户端查询方法类
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class BcsCommonQuery {
	private static BcsCommonQuery	bcsQuery	= null;

	public synchronized static BcsCommonQuery getInstance() {
		if (bcsQuery == null) {
			bcsQuery = new BcsCommonQuery();
		}
		return bcsQuery;
	}
	

	/**
	 * 选择十码商品归并表
	 * 
	 * @param materielType
	 * @return
	 */
	public Object getBcsTenInnerMerge(final String materielType) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("凭证序号", "seqNum", 60));
		list.add(new JTableListColumn("编码", "complex.code", 100));
		list.add(new JTableListColumn("商品名称", "name", 180));
		list.add(new JTableListColumn("商品规格", "spec", 250));
		list.add(new JTableListColumn("常用单位", "comUnit.name", 80));
		
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(200);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				BcsInnerMergeAction bcsInnerMergeAction = (BcsInnerMergeAction) CommonVars
						.getApplicationContext().getBean("bcsInnerMergeAction");
				return bcsInnerMergeAction.findBcsTenInnerMerge(new Request(
						CommonVars.getCurrUser(), true), materielType, index,
						length, property, value, isLike);
			}
		};

		dgCommonQuery.setTitle("【已归并】的报关商品资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 选择十码商品归并表已存在归关表中资料
	 * 
	 * @param materielType
	 * @return
	 */
	public Object getBcsTenInnerMergeInMerge(final String materielType) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("凭证序号", "seqNum", 60));
		list.add(new JTableListColumn("编码", "complex.code", 80));
		list.add(new JTableListColumn("商品名称", "name", 100));
		list.add(new JTableListColumn("商品规格", "spec", 100));
		list.add(new JTableListColumn("常用单位", "comUnit.name", 80));

		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(200);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				BcsInnerMergeAction bcsInnerMergeAction = (BcsInnerMergeAction) CommonVars
						.getApplicationContext().getBean("bcsInnerMergeAction");
				return bcsInnerMergeAction.findBcsTenInnerMergeInMerge(
						new Request(CommonVars.getCurrUser(), true),
						materielType, index, length, property, value, isLike);
			}
		};

		dgCommonQuery.setTitle("选择【BCS】报关资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	public List getComplexOfBcsTenInnerMerge() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("编码", "code", 100));
		list.add(new JTableListColumn("名称", "name", 150));
		list.add(new JTableListColumn("第一法定单位", "firstUnit.name", 100));
		list.add(new JTableListColumn("第二法定单位", "secondUnit.name", 100));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(200);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//			
				BcsInnerMergeAction bcsInnerMergeAction = (BcsInnerMergeAction) CommonVars
						.getApplicationContext().getBean("bcsInnerMergeAction");
				
					long beginTime=System.currentTimeMillis();
				
					List list = bcsInnerMergeAction.findComplexOutOfBcsTenInnerMerge(
						new Request(CommonVars.getCurrUser(), true), index,
						length, property, value, isLike);
					
					long endTime=System.currentTimeMillis();
					
					System.out.println("-----Query time :"+(endTime-beginTime)/1000.0);
					return list;
			}
		};

		dgCommonQuery.setTitle("商品编码查询");
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	
	

}
