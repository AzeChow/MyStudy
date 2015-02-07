/*
 * Created on 2005-3-28
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.innermerge;

import java.util.List;
import java.util.Vector;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.Request;

/**
 * @author xxm
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BcusCommonQuery {
	private static BcusCommonQuery bcusQuery = null;
	private CustomBaseAction customBaseAction = (CustomBaseAction) CommonVars
	                       .getApplicationContext().getBean("customBaseAction");
	private CommonBaseCodeAction commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
                          .getApplicationContext().getBean("commonBaseCodeAction");
	
	public synchronized static BcusCommonQuery getInstance() {
		if (bcusQuery == null) {
			bcusQuery = new BcusCommonQuery();
		}
		return bcusQuery;
	}
	
	
	
	
	/**
	 * 抓取十码Distinct资料
	 * @param projectType
	 * @return
	 */
	public Object findDistinctTenInner(String type) {		
        List<JTableListColumn> list = new Vector<JTableListColumn>();
        list.add(new JTableListColumn("序号", "bill1", 50,Integer.class,true));
        list.add(new JTableListColumn("商品编码", "bill2", 100));
        list.add(new JTableListColumn("商品名称", "bill3", 100));
        list.add(new JTableListColumn("商品规格", "bill4", 100));
        list.add(new JTableListColumn("备案单位", "bill5", 60));
        DgCommonByValueQuery.setTableColumns(list);
        final DgCommonByValueQuery dgCommonQuery = new DgCommonByValueQuery();
        List dataSource = commonBaseCodeAction.getDistinctTenInner(new Request(CommonVars.getCurrUser()),type);
        dgCommonQuery.setDataSource(dataSource);
        dgCommonQuery.setTitle("十码归并资料");
        DgCommonQuery.setSingleResult(true);
        dgCommonQuery.setVisible(true);
        if (dgCommonQuery.isOk()) {
            return dgCommonQuery.getReturnValue();
        }
        return null;
	}
	
	
	/**
	 * 抓取四码Distinct资料
	 * @param projectType
	 * @return
	 */
	public Object findDistinctFourceInner(String type) {		
        List<JTableListColumn> list = new Vector<JTableListColumn>();
        list.add(new JTableListColumn("序号", "bill1", 50));
        list.add(new JTableListColumn("商品编码", "bill2", 100));
        list.add(new JTableListColumn("商品名称", "bill3", 100));
        DgCommonByValueQuery.setTableColumns(list);
        final DgCommonByValueQuery dgCommonQuery = new DgCommonByValueQuery();
        List dataSource = commonBaseCodeAction.getDistinctFourceInner(new Request(CommonVars.getCurrUser()),type);
        dgCommonQuery.setDataSource(dataSource);
        dgCommonQuery.setTitle("四码归并资料");
        DgCommonQuery.setSingleResult(true);
        dgCommonQuery.setVisible(true);
        if (dgCommonQuery.isOk()) {
            return dgCommonQuery.getReturnValue();
        }
        return null;
	}
   

	private Double formatDouble(Double value){
		if (value == null){
			return Double.valueOf(0);
		}
		return value;
	}
}