/*
 * Created on 2005-3-28
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bls.client.blsinnermerge;

import java.util.List;
import java.util.Vector;

import com.bestway.bls.action.BlsInnerMergeAction;
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
public class BlsInnerMergeQuery {
	private static BlsInnerMergeQuery	blsQuery	= null;

	public synchronized static BlsInnerMergeQuery getInstance() {
		if (blsQuery == null) {
			blsQuery = new BlsInnerMergeQuery();
		}
		return blsQuery;
	}
	

	/**
	 * 选择十码商品归并表
	 * 
	 * @param materielType
	 * @return
	 */
	public Object getBlsTenInnerMerge() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("归并序号", "seqNum", 60));
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
				BlsInnerMergeAction blsInnerMergeAction = (BlsInnerMergeAction) CommonVars
						.getApplicationContext().getBean("blsInnerMergeAction");
				return blsInnerMergeAction.findBlsTenInnerMerge(new Request(
						CommonVars.getCurrUser(), true), index,
						length, property, value, isLike);
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

	/**
	 * 选择十码商品归并表已存在归关表中资料
	 * 
	 * @param materielType
	 * @return
	 */
	public Object getBlsTenInnerMergeInMerge(final String materielType) {
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
				BlsInnerMergeAction blsInnerMergeAction = (BlsInnerMergeAction) CommonVars
						.getApplicationContext().getBean("blsInnerMergeAction");
				return blsInnerMergeAction.findBlsTenInnerMergeInMerge(
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

	public List getComplexOfBlsTenInnerMerge() {
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
				BlsInnerMergeAction blsInnerMergeAction = (BlsInnerMergeAction) CommonVars
						.getApplicationContext().getBean("blsInnerMergeAction");
				return blsInnerMergeAction.findComplexOutOfBlsTenInnerMerge(
						new Request(CommonVars.getCurrUser(), true), index,
						length, property, value, isLike);
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

	/**
	 * 获得分页查询的物料(来自内部归并的过滤)
	 * 
	 * @param dataSource
	 * @return
	 */
	public Object getMaterielNotInBlsInnerMerge() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("料号", "ptNo", 100));
		list.add(new JTableListColumn("物料编码", "complex.code", 80));
		list.add(new JTableListColumn("商品名称", "factoryName", 100));// 工厂名称
		list.add(new JTableListColumn("商品规格", "factorySpec", 100));
		list.add(new JTableListColumn("单位", "calUnit.name", 50));
		list.add(new JTableListColumn("单价", "ptPrice", 50));
		list.add(new JTableListColumn("净重", "ptNetWeight", 50));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(200);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//			
				BlsInnerMergeAction blsInnerMergeAction = (BlsInnerMergeAction) CommonVars
						.getApplicationContext().getBean("blsInnerMergeAction");
				return blsInnerMergeAction.findMaterielForBlsInnerMerge(
						new Request(CommonVars.getCurrUser(), true),
						 index, length, property, value, isLike);
			}
		};

		dgCommonQuery.setTitle("物料查询");
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	

}
