
package com.bestway.client.owp;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.bcus.client.common.DgCommonQueryPage;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.Request;
import com.bestway.common.constant.ProjectType;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.owp.action.OwpAppAction;

public class OwpQuery {
	private static OwpQuery query=null;
	
	private OwpQuery(){
		
	}
	public static OwpQuery getInstance(){
		if(query==null){
			query=new OwpQuery();
		}
		return query;
	}
	
	
	
	
	
	/**
	 * 外发加工申请表 承揽方企业代码
	 * @author wss
	 */
	public List findBrief() {
//		DgCommonQuery dgCommonQuery = new DgCommonQuery();
//		
//		CustomBaseList customBase = CustomBaseList.getInstance();
//		List list= customBase.getBriefs(); // 海关注册公司
//		
//		System.out.println("wss list.size = " + list.size());
//		
//		dgCommonQuery.setDataSource(list);
//		
//		List<JTableListColumn> tableColumns = new Vector<JTableListColumn>();
//		
//		tableColumns.add(new JTableListColumn("代码", "code", 150));
//		tableColumns.add(new JTableListColumn("名称", "name", 120));
//
//		DgCommonQuery.setTableColumns(tableColumns);
//		DgCommonQuery.setSingleResult(false);
//
//		dgCommonQuery.setTitle("选择海关注册公司");
//		
//		dgCommonQuery.setVisible(true);
//		if (dgCommonQuery.isOk()) {
//			return dgCommonQuery.getReturnList();
//		}
//		return null;
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("编码", "code", 100));
		list.add(new JTableListColumn("名称", "name", 150));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(200);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//			
				CustomBaseAction customBaseAction = (CustomBaseAction) CommonVars
						.getApplicationContext().getBean("customBaseAction");
				return customBaseAction.findPageBriefList(new Request(CommonVars
						.getCurrUser(), true), index, length, property, value,
						isLike);
			}
		};

		dgCommonQuery.setTitle("海关注册公司");
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}
	
	/**
	 * 外发加工申请表 选择手册/帐册编号
	 * @author wss
	 */
	public List findEmsNo(int projectType) {
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		
		List list = new ArrayList();
		
		List<JTableListColumn> tableColumns = new Vector<JTableListColumn>();
		
		if(ProjectType.BCS == projectType){
			ContractAction contractAction = (ContractAction) CommonVars.getApplicationContext()
																	.getBean("contractAction");
			list = contractAction.findContractByProcessExe(new Request(
					CommonVars.getCurrUser(), true));
			tableColumns.add(new JTableListColumn("手册号", "emsNo", 150));
			tableColumns.add(new JTableListColumn("合同号", "impContractNo", 120));
			
		}else if(ProjectType.BCUS == projectType){
			
			ManualDeclareAction manualDeclareAction = (ManualDeclareAction) CommonVars
			.getApplicationContext().getBean("manualdeclearAction");
			list = manualDeclareAction.findEmsHeadH2kInExecuting(new Request(
					CommonVars.getCurrUser(), true));
			
			tableColumns.add(new JTableListColumn("帐册号", "", 150));
//			tableColumns.add(new JTableListColumn("合同号", "", 120));
			
		}else if(2 == projectType){
			DzscAction dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
			"dzscAction");
			list = dzscAction.findExeEmsPorHead(new Request(CommonVars
					.getCurrUser()));
			
			tableColumns.add(new JTableListColumn("手册号", "emsNo", 150));
			tableColumns.add(new JTableListColumn("合同号", "ieContractNo", 120));
		}
		
		dgCommonQuery.setDataSource(list);
		
		System.out.println("wss list.size = " + list.size());

		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(false);

		dgCommonQuery.setTitle("选择手册/帐册编号");
		
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}
	
	/**
	 * 外发加工申请表 选择外发货物/收回货物
	 * @param projectType 手册类型
	 * @param emsNo 手册号
	 * @param isOut 外发true（收回false）
	 * @author wss
	 */
	public List findContractImgExg(Integer projectType,String headId,String emsNo,boolean isOut){
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		
		List list = new ArrayList();
		
		List<JTableListColumn> tableColumns = new Vector<JTableListColumn>();
		
		//1.电子化手册
		if(ProjectType.BCS == projectType){
			
			OwpAppAction owpAppAction = (OwpAppAction) CommonVars.getApplicationContext()
																			.getBean("owpAppAction");
			list = owpAppAction.findBcsContractDetailByProcessExe(new Request(CommonVars
					.getCurrUser()),headId,emsNo,isOut);
			
		}
		
		//2.电子帐册
		else if(ProjectType.BCUS == projectType){
			
			OwpAppAction owpAppAction = (OwpAppAction) CommonVars.getApplicationContext()
																			.getBean("owpAppAction");
			list = owpAppAction.findBcusContractDetailByProcessExe(new Request(CommonVars
					.getCurrUser()),headId,emsNo,isOut);

		}
		
		//3.电子手册
		else if(2 == projectType){
		
			OwpAppAction owpAppAction = (OwpAppAction) CommonVars.getApplicationContext()
																			.getBean("owpAppAction");
			list = owpAppAction.findDzscContractDetailByProcessExe(new Request(CommonVars
					.getCurrUser()),headId,emsNo,isOut);

		}
		
		tableColumns.add(new JTableListColumn("手册编号", "seqNum", 80));
		tableColumns.add(new JTableListColumn("商品编码", "complex.code", 80));
		tableColumns.add(new JTableListColumn("商品名称", "name", 150));
		tableColumns.add(new JTableListColumn("规格型号", "spec", 100));
		tableColumns.add(new JTableListColumn("计量单位", "unit.name", 80));
		
		System.out.println("wss list.size = " + list.size());
		
		dgCommonQuery.setDataSource(list);

		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(false);

		dgCommonQuery.setTitle("选择外发出货/收货商品");
		
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 获取申请表收发货品
	 * @author wss
	 */
	public List findAppSend() {
//		DgCommonQuery dgCommonQuery = new DgCommonQuery();
//		
//		CustomBaseList customBase = CustomBaseList.getInstance();
//		List list= customBase.getBriefs(); // 海关注册公司
//		
//		
//		System.out.println("wss list.size = " + list.size());
//		
//		dgCommonQuery.setDataSource(list);
//		
//		List<JTableListColumn> tableColumns = new Vector<JTableListColumn>();
//		
//		tableColumns.add(new JTableListColumn("代码", "code", 150));
//		tableColumns.add(new JTableListColumn("名称", "name", 120));
//
//		DgCommonQuery.setTableColumns(tableColumns);
//		DgCommonQuery.setSingleResult(false);
//
//		dgCommonQuery.setTitle("选择海关注册公司");
//		
//		dgCommonQuery.setVisible(true);
//		if (dgCommonQuery.isOk()) {
//			return dgCommonQuery.getReturnList();
//		}
//		return null;
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("编码", "code", 100));
		list.add(new JTableListColumn("名称", "name", 150));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(200);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//			
				CustomBaseAction customBaseAction = (CustomBaseAction) CommonVars
						.getApplicationContext().getBean("customBaseAction");
				return customBaseAction.findPageBriefList(new Request(CommonVars
						.getCurrUser(), true), index, length, property, value,
						isLike);
			}
		};

		dgCommonQuery.setTitle("海关注册公司");
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}
	
	/**
	 * 获取申请表表头相关信息
	 * @author wss
	 */
	public List findOwpAppHeadInfo(String columnName) {
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		
		List list = new ArrayList();
		
		OwpAppAction owpAppAction = (OwpAppAction) CommonVars.getApplicationContext()
																	.getBean("owpAppAction");
		
		list = owpAppAction.findOwpAppHeadInfos(new Request(CommonVars
															.getCurrUser()));

		List<JTableListColumn> tableColumns = new Vector<JTableListColumn>();
		if("appNo".equals(columnName)){
			dgCommonQuery.setTitle("选择申请表编号");
			tableColumns.add(new JTableListColumn("申请表编号", "object", 150));
			tableColumns.add(new JTableListColumn("承揽方企业代码", "object1", 120));
			tableColumns.add(new JTableListColumn("承揽方企业名称", "object2", 120));
		}else if("inTradeCode".equals(columnName)){
			dgCommonQuery.setTitle("选择承揽方企业代码");
			tableColumns.add(new JTableListColumn("承揽方企业代码", "object1", 120));
			tableColumns.add(new JTableListColumn("承揽方企业名称", "object2", 120));
			tableColumns.add(new JTableListColumn("申请表编号", "object", 150));
		}else if("inTradeName".equals(columnName)){
			dgCommonQuery.setTitle("选择承揽方企业名称");
			tableColumns.add(new JTableListColumn("承揽方企业名称", "object2", 120));
			tableColumns.add(new JTableListColumn("承揽方企业代码", "object1", 120));
			tableColumns.add(new JTableListColumn("申请表编号", "object", 150));
		}
		
		System.out.println("wss list.size = " + list.size());
		
		dgCommonQuery.setDataSource(list);
		
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(false);
		
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}
	
	/**
	 * 选择海关自用商品资料
	 * @return
	 * @author wss
	 */
	public List<Complex> findComplex(){
		

		DgCommonQueryPage dgQueryPage = new DgCommonQueryPage(){
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				OwpAppAction action = (OwpAppAction)CommonVars.getApplicationContext()
																.getBean("owpAppAction");
				return action.findComplex(new Request(CommonVars.getCurrUser()), 
											index, 
											length, 
											property, 
											value, 
											isLike);
			}
		};
		
		List<JTableListColumn> lsColumn = new ArrayList<JTableListColumn>();
		
		lsColumn.add(new JTableListColumn("编码", "code", 90));
		lsColumn.add(new JTableListColumn("商品名称", "name", 200));
		lsColumn.add(new JTableListColumn("第一法定单位", "firstUnit.name", 100));
		lsColumn.add(new JTableListColumn("第二法定单位", "secondUnit.name", 100));
		lsColumn.add(new JTableListColumn("备注", "note", 250));
		
		dgQueryPage.setTableColumns(lsColumn);
		dgQueryPage.setTitle("选择自用报关商品");
		DgCommonQueryPage.setSingleResult(false);
		dgQueryPage.setVisible(true);

		if(dgQueryPage.isOk()){
			return dgQueryPage.getReturnList();
		}
		return null;
	
	}
	
	
	
}
