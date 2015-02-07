/*
 * Created on 2005-3-25
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.checkcancel;

import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.entity.EmsAnalyHead;
import com.bestway.bcus.checkcancel.entity.EmsBgExg;
import com.bestway.bcus.checkcancel.entity.EmsPdExg;
import com.bestway.bcus.checkcancel.entity.EmsPdExgBg;
import com.bestway.bcus.checkcancel.entity.EmsPdImg;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;

/**
 * @author xxm
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EmsLogic {	
	private static NumberFormatter numberFormatter=null;
	private static CheckCancelAction checkCancelAction = (CheckCancelAction) CommonVars
	                             .getApplicationContext().getBean("checkCancelAction");
	
	//填充盘点--料件--上
	public static JTableListModel initTablePdImgS(JTableListModel tableModel,JTable jTable,final List list) {
		return tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("料号", "ptNo", 80));
						list.add(addColumn("品名", "ptName", 100));
						list.add(addColumn("规格", "ptSpec", 100));
						list.add(addColumn("计量单位", "calUnit.name", 80));
						list.add(addColumn("保税数量", "proNum", 80));
						list.add(addColumn("非保税数量", "notProNum", 80));
						list.add(addColumn("盘点总数", "pdNum", 80));
						return list;
					}
				});
	}
	//填充盘点--料件--下
	public static JTableListModel initTablePdImgX(JTableListModel tableModel,JTable jTable,final List list) {
		return tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("帐册编号", "emsNo", 80));
						list.add(addColumn("备案序号", "seqNum", 80));
						list.add(addColumn("报关商品名称", "name", 120));
						list.add(addColumn("报关商品规格", "spec", 120));
						list.add(addColumn("计量单位", "unit.name", 80));
						list.add(addColumn("保税数量", "proNum", 80));
						list.add(addColumn("非保税数量", "notProNum", 80));
						list.add(addColumn("总数量", "totalNum", 80));
						list.add(addColumn("折算系数", "convertNUm", 80));
						return list;
					}
				});
	}
//	填充盘点--成品--上
	public static JTableListModel initTablePdExgS(JTableListModel tableModel,JTable jTable,final List list) {
		return tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						//list.add(addColumn("物料类别", "ptNo1", 80));
						list.add(addColumn("料号", "ptNo", 80));
						list.add(addColumn("品名", "ptName", 100));
						list.add(addColumn("规格", "ptSpec", 100));
						list.add(addColumn("工单号", "workBillNo", 100));			
						list.add(addColumn("版本号", "versionNo", 80));
						list.add(addColumn("计量单位", "calUnit.name", 80));
						list.add(addColumn("盘点数量", "pdNum", 80));						
						return list;
					}
				});
	}
	//填充盘点--成品--下
	public static JTableListModel initTablePdExgX(JTableListModel tableModel,JTable jTable,final List list) {
		return tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						//list.add(addColumn("物料类别", "no", 80));
						list.add(addColumn("帐册编号", "emsNo", 80));
						list.add(addColumn("备案序号", "seqNum", 80));
						list.add(addColumn("报关商品名称", "name", 120));
						list.add(addColumn("报关商品规格", "spec", 120));
						list.add(addColumn("计量单位", "unit.name", 80));
						list.add(addColumn("成品版本", "versionNo", 80));
						list.add(addColumn("分配数量", "allotNum", 80));
						list.add(addColumn("折算系数", "convertNUm", 80));
						list.add(addColumn("总数量", "totalNum", 80));
						return list;
					}
				});
	}
//	填充盘点--成品折料--下
	public static JTableListModel initTablePdExgImgX(JTableListModel tableModel,JTable jTable,final List list) {
		return tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						//list.add(addColumn("保税类别", "no", 80));
						list.add(addColumn("帐册编号", "emsNo", 80));
						list.add(addColumn("备案序号", "seqNum", 80));
						list.add(addColumn("料件名称", "name", 80));
						list.add(addColumn("型号规格", "spec", 80));
						list.add(addColumn("计量单位", "unit.name", 80));
						list.add(addColumn("单耗", "unitWare", 50));
						list.add(addColumn("损耗", "ware", 50));
						list.add(addColumn("总耗用", "totalWate", 80));
						return list;
					}
				});
	}
	
//	填充盘点--分析总表
	public static JTableListModel initTablePdAnalyTotal(JTableListModel tableModel,JTable jTable,final List list) {
		return tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("帐册编号", "emsNo", 80));
						list.add(addColumn("备案序号", "seqNum", 80));
						list.add(addColumn("商品编码", "complex", 80));
						list.add(addColumn("商品名称", "name", 80));
						list.add(addColumn("型号规格", "spec", 80));
						list.add(addColumn("计量单位", "unit.name", 80));
						list.add(addColumn("料件库存(A)", "imgNum", 120));
						list.add(addColumn("成品折料库存(B)", "exgNum", 120));
						list.add(addColumn("总库存(A+B)", "totalNum", 120));
						return list;
					}
				});
	}
	
	
	
//	填充差异分析
	public static JTableListModel initTableCy(JTableListModel tableModel,JTable jTable,final List list) {
		return tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("帐册编号", "emsNo", 80));
						list.add(addColumn("备案序号", "seqNum", 80));
						list.add(addColumn("料件名称", "name", 80));
						list.add(addColumn("型号规格", "spec", 80));
						list.add(addColumn("计量单位", "unit.name", 80));
						list.add(addColumn("帐面结余(A)", "emsBalance", 120));
						list.add(addColumn("实物库存(B)", "factNum", 120));
						list.add(addColumn("差异数(A-B)", "cyNum", 120));
						return list;
					}
				});
	}	
	
	
//	填充报关--原料进口
	public static JTableListModel initTableBgImg(JTableListModel tableModel,JTable jTable,final List list) {
		return tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("帐册编号", "emsNo", 80));
						list.add(addColumn("备案序号", "seqNum", 80));
						list.add(addColumn("料件名称", "name", 80));
						list.add(addColumn("型号规格", "spec", 80));
						list.add(addColumn("计量单位", "unit.name", 80));
						list.add(addColumn("进口数量", "inNum", 80));
						list.add(addColumn("转厂数量", "changeNum", 80));
						list.add(addColumn("退料数量", "exitNum", 80));
						list.add(addColumn("总进口", "totalNum", 80));
						return list;
					}
				});
	}	
	
	
//	填充报关--成品出口--上
	public static JTableListModel initTableBgExgS(JTableListModel tableModel,JTable jTable,final List list) {
		return tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("帐册编号", "emsNo", 80));
						list.add(addColumn("备案序号", "seqNum", 80));
						list.add(addColumn("成品名称", "name", 80));
						list.add(addColumn("版本", "versionNo", 50));
						list.add(addColumn("型号规格", "spec", 80));
						list.add(addColumn("计量单位", "unit.name", 80));
						list.add(addColumn("出口数量 ", "outNum", 80));
						list.add(addColumn("转厂数量 ", "changeNum", 80));
						list.add(addColumn("退运数量", "exitNum", 80));
						list.add(addColumn("总出口", "totalNum", 80));
						return list;
					}
				});
	}	
	
//	填充报关--成品出口--下
	public static JTableListModel initTableBgExgX(JTableListModel tableModel,JTable jTable,final List list) {
		return tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("保税类别", "no", 80));
						list.add(addColumn("帐册编号", "emsNo", 80));
						list.add(addColumn("备案序号", "seqNum", 80));
						list.add(addColumn("料件名称", "name", 80));
						list.add(addColumn("型号规格", "spec", 80));
						list.add(addColumn("计量单位", "unit.name", 80));
						list.add(addColumn("单耗 ", "unitWare", 80));
						list.add(addColumn("损耗 ", "ware", 80));
						list.add(addColumn("总耗用", "totalWare", 80));
						return list;
					}
				});
	}	
//	填充报关--进出口总表
	public static JTableListModel initTableBgImgExgTotal(JTableListModel tableModel,JTable jTable,final List list) {
		return tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("帐册编号", "emsNo", 80));
						list.add(addColumn("备案序号", "seqNum", 80));
						list.add(addColumn("料件名称", "name", 80));
						list.add(addColumn("型号规格", "spec", 80));
						list.add(addColumn("计量单位", "unit.name", 80));
						list.add(addColumn("进口数量(A) ", "inNum", 120));
						list.add(addColumn("成品耗用(B) ", "exgWare", 120));
						list.add(addColumn("结余量(A-B)", "balance", 80));
						return list;
					}
				});
	}
	//盘点料件
	public static JTableListModel[] initTableImg(JTableListModel tableModelImg,JTable jTable,EmsAnalyHead head,JTableListModel tableModelImgBg,JTable jTable1){
		JTableListModel[] jModel= new JTableListModel[2];
		List list = checkCancelAction.findEmsPdImg(new Request(CommonVars.getCurrUser()),head);
		EmsPdImg img = null;
		if (list != null && list.size()>0){
			jModel [0] = initTablePdImgS(tableModelImg,jTable,list);
			img = (EmsPdImg) list.get(0);
			jModel [1] = initTablePdImgBg(tableModelImgBg,jTable1,head);
			return jModel;
		} else {
			jModel [0] = initTablePdImgS(tableModelImg,jTable,new Vector());
			jModel [1] = initTablePdImgBg(tableModelImgBg,jTable1,head);
			return jModel;
		}
	}
	public static JTableListModel initTablePdImgBg(JTableListModel tableModel,JTable jTable,EmsAnalyHead head){
		if (head == null){
			return initTablePdImgX(tableModel,jTable,new Vector());
		}
		List list = checkCancelAction.findEmsPdImgBg(new Request(CommonVars.getCurrUser()),head);
		if (list != null && list.size()>0){
			return initTablePdImgX(tableModel,jTable,list);
		} else {
			return initTablePdImgX(tableModel,jTable,new Vector());
		}
	}
	//盘点成品
	public static JTableListModel[] initTableExg(JTableListModel tableModelExg,JTable jTable,EmsAnalyHead head,JTableListModel tableModelExgBg,JTable jTable1){
		JTableListModel[] jModel= new JTableListModel[2];
		List list = checkCancelAction.findEmsPdExg(new Request(CommonVars.getCurrUser()),head);
		EmsPdExg exg = null;
		if (list != null && list.size()>0){
			jModel [0] = initTablePdExgS(tableModelExg,jTable,list);
			exg = (EmsPdExg) list.get(0);
			jModel [1] = initTablePdExgBg(tableModelExgBg,jTable1,head);
			return jModel;
		} else {
			jModel [0] = initTablePdImgS(tableModelExg,jTable,new Vector());
			jModel [1] = initTablePdExgBg(tableModelExgBg,jTable1,head);
			return jModel;
		}
	}
	public static JTableListModel initTablePdExgBg(JTableListModel tableModel,JTable jTable,EmsAnalyHead head){
		if (head == null){
			return initTablePdExgX(tableModel,jTable,new Vector());
		}
		List list = checkCancelAction.findEmsPdExgBg(new Request(CommonVars.getCurrUser()),head);
		if (list != null && list.size()>0){
			return initTablePdExgX(tableModel,jTable,list);
		} else {
			return initTablePdExgX(tableModel,jTable,new Vector());
		}
	}
	
	
	
	
//	成品折料
	public static JTableListModel[] initTableExgImg(JTableListModel tableModelExgImg,JTable jTable,EmsAnalyHead head,JTableListModel tableModelExgImgBg,JTable jTable1){
		JTableListModel[] jModel= new JTableListModel[2];
		List list = checkCancelAction.findEmsPdExgBgAll(new Request(CommonVars.getCurrUser()),head);//所有报关成品
		EmsPdExgBg exg = null;
		if (list != null && list.size()>0){
			jModel [0] = initTablePdExgX(tableModelExgImg,jTable,list);
			exg = (EmsPdExgBg) list.get(0);
			jModel [1] = initTablePdExgImgBg(tableModelExgImgBg,jTable1,head);
			return jModel;
		} else {
			jModel [0] = initTablePdExgX(tableModelExgImg,jTable,new Vector());
			jModel [1] = initTablePdExgImgBg(tableModelExgImgBg,jTable1,head);
			return jModel;
		}
	}
	public static JTableListModel initTablePdExgImgBg(JTableListModel tableModel,JTable jTable,EmsAnalyHead head){
		if (head == null){
			return initTablePdExgImgX(tableModel,jTable,new Vector());
		}
		List list = checkCancelAction.findEmsExgImgBg(new Request(CommonVars.getCurrUser()),head);
		if (list != null && list.size()>0){
			return initTablePdExgImgX(tableModel,jTable,list);
		} else {
			return initTablePdExgImgX(tableModel,jTable,new Vector());
		}
	}
	//盘点总表
	public static JTableListModel initTableTotal(JTableListModel tableModel,JTable jTable,EmsAnalyHead head){
		List list = checkCancelAction.findEmsPdTotal(new Request(CommonVars.getCurrUser()),head);
		if (list != null && list.size()>0){
			return initTablePdAnalyTotal(tableModel,jTable,list);
		} else {
			return initTablePdAnalyTotal(tableModel,jTable,new Vector());
		}
	}
//	差异分析
	public static JTableListModel initTableCy(JTableListModel tableModel,JTable jTable,EmsAnalyHead head){
		List list = checkCancelAction.findEmsCy(new Request(CommonVars.getCurrUser()),head);
		if (list != null && list.size()>0){
			return initTableCy(tableModel,jTable,list);
		} else {
			return initTableCy(tableModel,jTable,new Vector());
		}
	}
//	报关--原料进口
	public static JTableListModel initTableBgImg(JTableListModel tableModel,JTable jTable,EmsAnalyHead head){
		List list = checkCancelAction.findEmsBgImg(new Request(CommonVars.getCurrUser()),head);
		if (list != null && list.size()>0){
			return initTableBgImg(tableModel,jTable,list);
		} else {
			return initTableBgImg(tableModel,jTable,new Vector());
		}
	}
//	报关--成品出口
	public static JTableListModel[] initTableBgExg(JTableListModel tableModelExg,JTable jTable,EmsAnalyHead head,JTableListModel tableModelExgBg,JTable jTable1){
		JTableListModel[] jModel= new JTableListModel[2];
		List list = checkCancelAction.findEmsBgExg(new Request(CommonVars.getCurrUser()),head);
		EmsBgExg exg = null;
		if (list != null && list.size()>0){
			jModel [0] = initTableBgExgS(tableModelExg,jTable,list);
			exg = (EmsBgExg) list.get(0);
			jModel [1] = initTableBgImg(tableModelExgBg,jTable1,head);
			return jModel;
		} else {
			jModel [0] = initTableBgExgS(tableModelExg,jTable,new Vector());
			jModel [1] = initTableBgImg(tableModelExgBg,jTable1,head);
			return jModel;
		}
	}
	/*public static JTableListModel initTableBgImg(JTableListModel tableModel,JTable jTable,EmsAnalyHead head){
		if (exg == null){
			return initTableBgExgX(tableModel,jTable,new Vector());
		}
		List list = checkCancelAction.findEmsBgExgBg(new Request(CommonVars.getCurrUser()),head);
		if (list != null && list.size()>0){
			return initTableBgExgX(tableModel,jTable,list);
		} else {
			return initTableBgExgX(tableModel,jTable,new Vector());
		}
	}*/
//	报关--原料进口总表
	public static JTableListModel initTableBgTotal(JTableListModel tableModel,JTable jTable,EmsAnalyHead head){
		List list = checkCancelAction.findEmsBgTotal(new Request(CommonVars.getCurrUser()),head);
		if (list != null && list.size()>0){
			return initTableBgImgExgTotal(tableModel,jTable,list);
		} else {
			return initTableBgImgExgTotal(tableModel,jTable,new Vector());
		}
	}
//	/**
//	 * 新增盘点--料件
//	 */
//    public static JTableListModel newPdImg(JTableListModel tableModel,EmsAnalyHead head){
//    	EmsPdImg img = null;
//		List list = (List) DzbaCommonQuery.getInstance().getEmsPdImgFromMateriel(
//				false,head);
//		if (list == null || list.isEmpty())
//			return tableModel;
//		for (int i = 0; i < list.size(); i++) {
//			img = (EmsPdImg) list.get(i);
//			img.setHead(head);						
//			img.setCompany(CommonVars.getCurrUser().getCompany());
//			img = checkCancelAction.SaveEmsPdImg(new Request(CommonVars.getCurrUser()),img);
//			tableModel.addRow(img);
//		}
//		return tableModel;
//    }
//    /**
//	 * 新增盘点--成品
//	 */
//    public static JTableListModel newPdExg(JTableListModel tableModel,EmsAnalyHead head){
//    	EmsPdExg exg = null;
//		List list = (List) DzbaCommonQuery.getInstance().getEmsPdExgFromMateriel(
//				false,head);
//		if (list == null || list.isEmpty())
//			return tableModel;
//		for (int i = 0; i < list.size(); i++) {
//			exg = (EmsPdExg) list.get(i);
//			exg.setHead(head);						
//			exg.setCompany(CommonVars.getCurrUser().getCompany());
//			exg = checkCancelAction.SaveEmsPdExg(new Request(CommonVars.getCurrUser()),exg);
//			tableModel.addRow(exg);
//		}
//		return tableModel;
//    }
}
