/*
 * Created on 2005-3-28
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.custombase;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.Request;

/**
 * @author // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class CustombaseQuery {
	private static CustombaseQuery custombaseQuery = null;

	private CustomBaseAction customBaseAction = null;

	private CustombaseQuery() {
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
	}

	public synchronized static CustombaseQuery getInstance() {
		if (custombaseQuery == null) {
			custombaseQuery = new CustombaseQuery();

		}
		return custombaseQuery;
	}

	/**
	 * 查询商品编码
	 * 
	 * @return
	 */
	public List findCustomsComplexNotInComplex() {

		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("商品编码", "code", 100));
		list.add(new JTableListColumn("商品名称", "name", 155));
		list.add(new JTableListColumn("第一法定单位", "firstUnit.name", 100));
		list.add(new JTableListColumn("第二法定单位", "secondUnit.name", 100));

		// DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(1000);
		// DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage()
		// {
		// @Override
		// public List getDataSource(int index, int length, String property,
		// Object value, boolean isLike) {
		// //
		// // 分页查询的方法
		// //
		// return customBaseAction.findCustomsComplexNotInComplex(index,
		// length, property, value, isLike);
		// }
		// };
		// dgCommonQuery.setTitle("选择【海关商品编码】");
		// DgCommonQueryPage.setSingleResult(false);
		// dgCommonQuery.setVisible(true);
		// if (dgCommonQuery.isOk()) {
		// return dgCommonQuery.getReturnList();
		// }
		// return null;
		DgCommonQuery.setTableColumns(list);
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List dataSource = customBaseAction.findCustomsComplexNotInComplex();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("选择【海关商品编码】");
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	public List findComplexNotInCustomsComplex() {

		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("商品编码", "code", 100));
		list.add(new JTableListColumn("商品名称", "name", 155));
		list.add(new JTableListColumn("第一法定单位", "firstUnit.name", 100));
		list.add(new JTableListColumn("第二法定单位", "secondUnit.name", 100));

		// DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(1000);
		// DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage()
		// {
		// @Override
		// public List getDataSource(int index, int length, String property,
		// Object value, boolean isLike) {
		// //
		// // 分页查询的方法
		// //
		// return customBaseAction.findCustomsComplexNotInComplex(index,
		// length, property, value, isLike);
		// }
		// };
		// dgCommonQuery.setTitle("选择【海关商品编码】");
		// DgCommonQueryPage.setSingleResult(false);
		// dgCommonQuery.setVisible(true);
		// if (dgCommonQuery.isOk()) {
		// return dgCommonQuery.getReturnList();
		// }
		// return null;
		DgCommonQuery.setTableColumns(list);
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		DataImportAction dataImportAction = (DataImportAction) CommonVars
				.getApplicationContext().getBean("dataImportAction");
		List dataSource = customBaseAction.findComplexNotInCustomsComplex();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("选择【海关商品编码】");
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	public List findCountrysNotCheckup() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("代码", "code", 100));
		list.add(new JTableListColumn("名称", "name", 155));
		list.add(new JTableListColumn("英文名称", "countryEnname", 150));
		DgCommonQueryForCustomBase.setTableColumns(list);
		DgCommonQueryForCustomBase dgCommonQuery = new DgCommonQueryForCustomBase();
		List coutrys = customBaseAction.findCountry("", "");
		List tempList = new ArrayList();
		for (int i = 0; i < coutrys.size(); i++) {
			Country cou = (Country) coutrys.get(i);
			if (cou.getIsChcekup() == null || !cou.getIsChcekup()) {
				tempList.add(cou);
			}
		}
		dgCommonQuery.setDataSource(tempList);
		dgCommonQuery.setTitle("选择疫区");
		DgCommonQueryForCustomBase.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	public List findComplexForCheckupNotIn(Integer flag) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("商品编码", "code", 100));
		list.add(new JTableListColumn("商品名称", "name", 225));
		list.add(new JTableListColumn("第一法定单位", "firstUnit.name", 80));
		list.add(new JTableListColumn("第二法定单位", "secondUnit.name", 80));
		list.add(new JTableListColumn("许可证代码", "ccontrol", 60));
		DgCommonQueryForCustomBase.setTableColumns(list);
		DgCommonQueryForCustomBase dgCommonQuery = new DgCommonQueryForCustomBase();
		List data = customBaseAction.findComplexForCheckupNotIn(new Request(
				CommonVars.getCurrUser()), flag);
		List tempList = new ArrayList();
		dgCommonQuery.setDataSource(data);
		dgCommonQuery.setTitle("选择商检编码");
		DgCommonQueryForCustomBase.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}
	public List findCheckupComplex(Integer flag) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("商品编码", "complex.code", 100));
		list.add(new JTableListColumn("商品名称", "complex.name", 225));
		list.add(new JTableListColumn("第一法定单位", "complex.firstUnit.name", 80));
		list.add(new JTableListColumn("第二法定单位", "complex.secondUnit.name", 80));
		list.add(new JTableListColumn("备注", "complex.note", 250));
		DgCommonQueryForCustomBase.setTableColumns(list);
		DgCommonQueryForCustomBase dgCommonQuery = new DgCommonQueryForCustomBase();
		List data = customBaseAction.findComplexByFlag(new Request(CommonVars
				.getCurrUser()), flag);
		dgCommonQuery.setDataSource(data);
		dgCommonQuery.setTitle("选择商检编码");
		DgCommonQueryForCustomBase.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}
	public List findCountrysCheckuped() {

		List coutrys = customBaseAction.findCountry("", "");
		List tempList = new ArrayList();
		for (int i = 0; i < coutrys.size(); i++) {
			Country cou = (Country) coutrys.get(i);
			if (cou == null) {
				continue;
			}
			if (cou.getIsChcekup() != null && cou.getIsChcekup()) {
				tempList.add(cou);
			}
		}
		return tempList;
	}
//	public List sortTempCodeAndseqNum
}
