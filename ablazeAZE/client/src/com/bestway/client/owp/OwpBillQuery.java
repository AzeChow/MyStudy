package com.bestway.client.owp;

import java.util.List;
import java.util.Vector;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.Request;
import com.bestway.owp.action.OwpBillAction;
import com.bestway.owp.action.OwpMessageAction;

/**
 * 外发加工登记表查询弹出框
 * @author sxk
 */
public class OwpBillQuery {
private static OwpBillQuery query=null;
	
	private OwpBillQuery(){
		
	}
	public static OwpBillQuery getInstance(){
		if(query==null){
			query=new OwpBillQuery();
		}
		return query;
	}
	/**
	 * 查找申请表进出口货物
	 * @param appNo 申请表表头ID
	 * @param billHeadId 登记表表头ID 
	 * @param isout 是否进出口
	 * @return
	 * @author sxk
	 */
	public List findAllAppSendItem(String appNo,String billHeadId,boolean isout) {
		OwpBillAction owpBillAction = (OwpBillAction) CommonVars
		.getApplicationContext().getBean("owpBillAction");
		List lsDataSource= owpBillAction
		.findOwpSendItemByHead(new Request(CommonVars
				.getCurrUser()),appNo,billHeadId,isout);
		System.out.println("lsDataSource="+lsDataSource.size());
        List<JTableListColumn> list = new Vector<JTableListColumn>();
        list.add(new JTableListColumn("外发序号", "seqNum", 100));
		list.add(new JTableListColumn("商品编码", "complex.code", 100));
		list.add(new JTableListColumn("商品名称", "hsName", 150));
		list.add(new JTableListColumn("商品规格", "hsSpec", 150));
		list.add(new JTableListColumn("计量单位", "hsUnit.name", 150));
        DgCommonQuery.setTableColumns(list);
        final DgCommonQuery dgCommonQuery = new DgCommonQuery();
        dgCommonQuery.setDataSource(lsDataSource);
        DgCommonQuery.setSingleResult(false);
        if(isout){
        	dgCommonQuery.setTitle("申请表外发商品");
        }else{
        	dgCommonQuery.setTitle("申请表收回商品");
        }
        dgCommonQuery.setVisible(true);
        if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
        return null;
	}
	/**
	 * 查找单据中心进出货货物
	 * @param appNo 申请表表头ID
	 * @param billHeadId 登记表表头ID 
	 * @param isout 是否进出口
	 * @return
	 * @author sxk
	 */
	public List findBillDetailSendItem(String appNo,String billHeadId,boolean isout) {
		OwpBillAction owpBillAction = (OwpBillAction) CommonVars
		.getApplicationContext().getBean("owpBillAction");
		System.out.println("单据中心查询");
		List lsDataSource= owpBillAction
		.findBillDetailSendItem(new Request(CommonVars
				.getCurrUser()),appNo,billHeadId,isout);
		System.out.println("单据中心查询返回结果："+lsDataSource.size());
        List<JTableListColumn> list = new Vector<JTableListColumn>();
        list.add(new JTableListColumn("单据号码", "billMaster.billNo", 100));
		list.add(new JTableListColumn("商品编码", "complex.code", 100));
		list.add(new JTableListColumn("商品名称", "hsName", 150));
		list.add(new JTableListColumn("商品规格", "hsSpec", 150));
		list.add(new JTableListColumn("计量单位", "hsUnit.name", 150));
        DgCommonQuery.setTableColumns(list);
        final DgCommonQuery dgCommonQuery = new DgCommonQuery();
        dgCommonQuery.setDataSource(lsDataSource);
        DgCommonQuery.setSingleResult(false);
        if(isout){
        	dgCommonQuery.setTitle("单据中心外发商品");
        }else{
        	dgCommonQuery.setTitle("单据中心收回商品");
        }
        dgCommonQuery.setVisible(true);
        if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
        return null;
	}
}
