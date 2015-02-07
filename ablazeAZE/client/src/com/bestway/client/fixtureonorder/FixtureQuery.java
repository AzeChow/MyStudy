package com.bestway.client.fixtureonorder;

import java.util.List;
import java.util.Vector;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.bcus.client.common.DgCommonQueryPage;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.Request;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.fixtureonorder.action.FixtureContractAction;

/**
 * @author fhz
 *
 */
public class FixtureQuery {
	private static FixtureQuery fixtureQuery = null;
	
	private static FixtureContractAction fixtureContractAction = null;

	private FixtureQuery() {

	}

	public static FixtureQuery getInstance() {
		if (fixtureQuery == null) {
			fixtureQuery = new FixtureQuery();
			fixtureContractAction = (FixtureContractAction) CommonVars
			.getApplicationContext().getBean("fixtureContractAction");
		}
		return fixtureQuery;
	}

	
	/**
	 * 查询批文协议设备征免税证明细
	 * 
	 * @param agreement
	 * @return
	 */
	public List findFixtureContractItemsByCustom(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		List dataSource = fixtureContractAction.findFixtureContractItemsByCustom(
				new Request(CommonVars.getCurrUser()), baseCustomsDeclaration);
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("手册号 ", "contract.emsNo", 150));
		list.add(new JTableListColumn("合同号", "contract.impContractNo", 150));
		list.add(new JTableListColumn("海关编号", "complex.code", 100));
		list.add(new JTableListColumn("名称", "name", 100));
		list.add(new JTableListColumn("规格", "spec", 100));
		list.add(new JTableListColumn("单价", "declarePrice", 100));
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
	
	/**
	 * 获得海关商品编码complex对象//联网监管//bcs使用
	 */
	public List getComplex() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("编码", "code", 100));
		list.add(new JTableListColumn("名称", "name", 150));
		list.add(new JTableListColumn("第一法定单位", "firstUnit.name", 100));
		list.add(new JTableListColumn("第二法定单位", "secondUnit.name", 100));
		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//			
				CustomBaseAction customBaseAction = (CustomBaseAction) CommonVars
						.getApplicationContext().getBean("customBaseAction");
				return customBaseAction.findComplex(new Request(CommonVars
						.getCurrUser(), true), index, length, property, value,
						isLike);
			}
		};

		dgCommonQuery.setTitle("设备选择");
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;

	}
	
	public List getMachine(){
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("料号", "materielNo", 100));
		list.add(new JTableListColumn("编码", "complex.code", 100));
		list.add(new JTableListColumn("名称", "hsCusName", 150));
		list.add(new JTableListColumn("规格", "hsCusSpec", 150));
		list.add(new JTableListColumn("计量单位", "hsCusUnit.name", 100));
//		list.add(new JTableListColumn("第二法定单位", "secondUnit.name", 100));
		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//			
				CustomBaseAction customBaseAction = (CustomBaseAction) CommonVars
						.getApplicationContext().getBean("customBaseAction");
				long start = System.currentTimeMillis();
				
				List list = customBaseAction.getMachine(new Request(CommonVars
						.getCurrUser(), true), index, length, property, value,
						isLike);
				System.out.println("时间："+(System.currentTimeMillis()-start));
				return list;
			}
		};

		dgCommonQuery.setTitle("设备选择");
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}
}
