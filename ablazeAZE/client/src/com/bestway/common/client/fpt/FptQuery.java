package com.bestway.common.client.fpt;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.bcus.client.common.DgCommonQueryPage;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.DataType;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.FptBusinessType;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.common.fpt.entity.FptAppItem;
import com.bestway.common.fpt.entity.FptBillHead;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.dzsc.customslist.action.DzscListAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class FptQuery {
	private static FptQuery transferFactoryQuery = null;

	private static FptManageAction fptManageAction = null;
	private static SystemAction systemAction = null;
	public static MaterialManageAction materialManageAction = null;

	public static FptQuery getInstance() {
		if (transferFactoryQuery == null) {
			transferFactoryQuery = new FptQuery();
			fptManageAction = (FptManageAction) CommonVars
					.getApplicationContext().getBean("fptManageAction");
			materialManageAction = (MaterialManageAction) CommonVars
					.getApplicationContext().getBean("materialManageAction");
			systemAction = (SystemAction) CommonVars.getApplicationContext()
					.getBean("systemAction");
		}
		return transferFactoryQuery;

	}

	public Object findDistinctProperiesFromFptBillItem(String caption,
			String prop) {
		List dataSource = fptManageAction.findDistinctProperiesFromFptBillItem(
				new Request(CommonVars.getCurrUser()), prop);
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn(caption, prop, 280));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("请选择！");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	public Object findDistinctTranceCustomsDeclaration(String caption,
			int projecttype, String prop) {
		List dataSource = fptManageAction.findDistinctTranceCustomsDeclaration(
				new Request(CommonVars.getCurrUser()), projecttype, prop);
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn(caption, prop, 280));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("请选择！");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 获得是深加工申请表客户供应商,供应商的对象列表
	 */
	public Object getFptAppScmCon(String fptInOutFlag) {
		List customerList = this.materialManageAction.findScmCocs(new Request(
				CommonVars.getCurrUser(), true));
		List dataSource = new ArrayList();
		String tite = "";
		for (int i = 0; i < customerList.size(); i++) {
			ScmCoc scmCoc = (ScmCoc) customerList.get(i);
			if (FptInOutFlag.IN.equals(fptInOutFlag)) {
				tite = "客户";
				if (scmCoc.getIsCustomer() != null
						&& scmCoc.getIsCustomer().booleanValue()) {
					dataSource.add(scmCoc);
				}
			} else {
				tite = "供应商";
				if (scmCoc.getIsManufacturer() != null
						&& scmCoc.getIsManufacturer().booleanValue()) {
					dataSource.add(scmCoc);
				}
			}
		}
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("编码号", "code", 100));
		list.add(new JTableListColumn("名称", "name", 200));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("请选择！");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}

	/**
	 * 获得是供应商的对象列表
	 */
	public List<ScmCoc> getManufacturer() {
		List list = this.materialManageAction.findScmCocs(new Request(
				CommonVars.getCurrUser(), true));
		List manufacturerList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			ScmCoc scmCoc = (ScmCoc) list.get(i);
			if (scmCoc.getIsManufacturer() != null
					&& scmCoc.getIsManufacturer().booleanValue()) {
				manufacturerList.add(scmCoc);
			}
		}
		return manufacturerList;
	}

	public Object findDistinctComCocFptBillItem(String fptInOutFlag) {
		List dataSource = new ArrayList();
		String tite = "";
		if (fptInOutFlag.equals(FptInOutFlag.OUT)) {
			dataSource = fptManageAction.findDistinctProperiesFromFptBillItem(
					new Request(CommonVars.getCurrUser()),
					"fptBillHead.receiveTradeName");
			tite = "客户";
		} else if (fptInOutFlag.equals(FptInOutFlag.IN)) {
			dataSource = fptManageAction.findDistinctProperiesFromFptBillItem(
					new Request(CommonVars.getCurrUser()),
					"fptBillHead.issueTradeName");
			tite = "供应商";
		}
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn(tite, "", 280));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("请选择！");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	public Object findDistinctEmsNoFptBillItem(String fptInOutFlag) {
		List dataSource = new ArrayList();
		String tite = "";
		if (fptInOutFlag.equals(FptInOutFlag.OUT)) {
			dataSource = fptManageAction.findDistinctProperiesFromFptBillItem(
					new Request(CommonVars.getCurrUser()),
					"fptBillHead.outEmsNo");
			tite = "客户";
		} else if (fptInOutFlag.equals(FptInOutFlag.IN)) {
			dataSource = fptManageAction.findDistinctProperiesFromFptBillItem(
					new Request(CommonVars.getCurrUser()), "inEmsNo");
			tite = "供应商";
		}
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn(tite, "", 280));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("请选择！");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	public List findFptBillItemBillNo(String caption, String prop) {
		List dataSource = fptManageAction.findDistinctProperiesFromFptBillItem(
				new Request(CommonVars.getCurrUser()), prop);
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn(caption, prop, 280));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("请选择！");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	// /**
	// * 抓取出口报关单资料
	// *
	// * @param projectType
	// * @return
	// */
	// public Object findExportCustomsDeclaration(
	// CustomsEnvelopBill customsEnvelopBill) {
	// // List dataSource =
	// // fptManageAction.findExportCustomsDeclaration(
	// // new Request(CommonVars.getCurrUser()), customsEnvelopBill);
	// List dataSource = fptManageAction.findCustomsEnvelopBill(
	// new Request(CommonVars.getCurrUser()), false, null, null, null,
	// null, null);
	// List<JTableListColumn> list = new Vector<JTableListColumn>();
	// list.add(new JTableListColumn("报关单号码", "customsDeclarationCode", 100));
	// list.add(new JTableListColumn("金额", "money", 100));
	// list.add(new JTableListColumn("进出口类型", "impExpType", 100));
	// list.add(new JTableListColumn("报关单来源", "projectType", 100));
	// DgCommonQuery.setTableColumns(list);
	// final DgCommonQuery dgCommonQuery = new DgCommonQuery();
	// dgCommonQuery.setDataSource(dataSource);
	// dgCommonQuery.setTitle("报关单资料");
	// dgCommonQuery.setLike(false);
	// dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
	// public void windowOpened(java.awt.event.WindowEvent e) {
	// JTable table = dgCommonQuery.getJTable();
	// table.getColumnModel().getColumn(3).setCellRenderer(
	// new DefaultTableCellRenderer() {
	// public Component getTableCellRendererComponent(
	// JTable table, Object value,
	// boolean isSelected, boolean hasFocus,
	// int row, int column) {
	// super.getTableCellRendererComponent(table,
	// value, isSelected, hasFocus, row,
	// column);
	// String str = "";
	// if (value != null) {
	// Integer projectType = Integer.valueOf(value
	// .toString());
	// switch (projectType) {
	// case ProjectType.BCUS:
	// str = "BCUS";
	// break;
	// case ProjectType.BCS:
	// str = "BCS";
	// break;
	// }
	// }
	// this.setText(str);
	// return this;
	// }
	// });
	// }
	// });
	// DgCommonQuery.setSingleResult(true);
	// dgCommonQuery.setVisible(true);
	// if (dgCommonQuery.isOk()) {
	// return dgCommonQuery.getReturnValue();
	// }
	// return null;
	// }

	// /**
	// * 抓取出口报关单资料
	// *
	// * @param projectType
	// * @return
	// */
	// public Object findImportCustomsDeclaration(
	// CustomsEnvelopBill customsEnvelopBill) {
	// // List dataSource =
	// // fptManageAction.findExportCustomsDeclaration(
	// // new Request(CommonVars.getCurrUser()), customsEnvelopBill);
	// List dataSource = fptManageAction.findCustomsEnvelopBill(
	// new Request(CommonVars.getCurrUser()), false, null, null, null,
	// null, null);
	// List<JTableListColumn> list = new Vector<JTableListColumn>();
	// list.add(new JTableListColumn("报关单号码", "customsDeclarationCode", 100));
	// list.add(new JTableListColumn("金额", "money", 100));
	// list.add(new JTableListColumn("进出口类型", "impExpType", 100));
	// list.add(new JTableListColumn("报关单来源", "projectType", 100));
	// DgCommonQuery.setTableColumns(list);
	// final DgCommonQuery dgCommonQuery = new DgCommonQuery();
	// dgCommonQuery.setDataSource(dataSource);
	// dgCommonQuery.setTitle("报关单资料");
	// dgCommonQuery.setLike(false);
	// dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
	// public void windowOpened(java.awt.event.WindowEvent e) {
	// JTable table = dgCommonQuery.getJTable();
	// table.getColumnModel().getColumn(3).setCellRenderer(
	// new DefaultTableCellRenderer() {
	// public Component getTableCellRendererComponent(
	// JTable table, Object value,
	// boolean isSelected, boolean hasFocus,
	// int row, int column) {
	// super.getTableCellRendererComponent(table,
	// value, isSelected, hasFocus, row,
	// column);
	// String str = "";
	// if (value != null) {
	// Integer projectType = Integer.valueOf(value
	// .toString());
	// switch (projectType) {
	// case ProjectType.BCUS:
	// str = "BCUS";
	// break;
	// case ProjectType.BCS:
	// str = "BCS";
	// break;
	// }
	// }
	// this.setText(str);
	// return this;
	// }
	// });
	// }
	// });
	// DgCommonQuery.setSingleResult(true);
	// dgCommonQuery.setVisible(true);
	// if (dgCommonQuery.isOk()) {
	// return dgCommonQuery.getReturnValue();
	// }
	// return null;
	// }

	// /**
	// * 抓取报关单号为空的涉及转厂的关封资料
	// *
	// * @param
	// * @return
	// */
	// public Object findCustomsEnvelopBill(boolean isImport,
	// boolean isAvailability, ScmCoc scmCoc) {
	// String scmCocName = (isImport ? "供应商名称" : "客户名称");
	// List dataSource = fptManageAction.findCustomsEnvelopBill(
	// new Request(CommonVars.getCurrUser(), true), isImport,
	// isAvailability, scmCoc);
	// List<JTableListColumn> list = new Vector<JTableListColumn>();
	// list.add(new JTableListColumn("关封号", "customsEnvelopBillNo", 150));
	// list.add(new JTableListColumn("生效日期", "beginAvailability", 100));
	// list.add(new JTableListColumn(scmCocName, "scmCoc.name", 150));
	// DgCommonQuery.setTableColumns(list);
	// final DgCommonQuery dgCommonQuery = new DgCommonQuery();
	// dgCommonQuery.setDataSource(dataSource);
	// dgCommonQuery.setTitle("关封资料");
	// dgCommonQuery.setLike(false);
	// DgCommonQuery.setSingleResult(true);
	// dgCommonQuery.setVisible(true);
	// if (dgCommonQuery.isOk()) {
	// return dgCommonQuery.getReturnValue();
	// }
	// return null;
	// }

	/**
	 * 抓取报关单号为空的涉及转厂的关封资料
	 * 
	 * @param
	 * @return
	 */
	public Object findFptAppItemByCustomsDeclaration(int impExpType,
			String emsNo, ScmCoc scmCoc) {
		// String scmCocName = (ImpExpType.TRANSFER_FACTORY_IMPORT == impExpType
		// ? "供应商名称"
		// : "客户名称");
		String scmCocName = "对方名称";
		List dataSource = fptManageAction.findFptAppItemByCustomsDeclaration(
				new Request(CommonVars.getCurrUser()), impExpType, emsNo,
				scmCoc);
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("结转申请单号", "fptAppHead.appNo", 150));
		// list.add(new JTableListColumn("生效日期",
		// "fptAppHead.beginAvailability", 100));
		if (ImpExpType.TRANSFER_FACTORY_IMPORT == impExpType) {
			list.add(new JTableListColumn(scmCocName,
					"fptAppHead.outTradeName", 150));
		} else {
			list.add(new JTableListColumn(scmCocName, "fptAppHead.inTradeName",
					150));
		}
		list.add(new JTableListColumn("商品编码", "codeTs.code", 100));
		list.add(new JTableListColumn("商品名称", "name", 100));
		list.add(new JTableListColumn("商品规格", "spec", 100));
		list.add(new JTableListColumn("申请数量", "qty", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("结转申请单");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	// /**
	// * 取得没有在关封中的商品资料
	// *
	// * @param projectType
	// * @param emsNo
	// * @param envelopBillId
	// * @param isMaterial
	// * @return
	// */
	// public List findTempCustomsEnvelopCommInfo(int projectType, String emsNo,
	// CustomsEnvelopBill customsEnvelopBill, boolean isMaterial) {
	// List dataSource = fptManageAction
	// .findTempCustomsEnvelopRequestCommInfo(new Request(CommonVars
	// .getCurrUser(), true), projectType, emsNo,
	// customsEnvelopBill, isMaterial);
	// List<JTableListColumn> list = new Vector<JTableListColumn>();
	// String str = "";
	// switch (projectType) {
	// case ProjectType.BCS:
	// str = "手册";
	// break;
	// case ProjectType.BCUS:
	// str = "帐册";
	// break;
	// case ProjectType.DZSC:
	// str = "手册";
	// break;
	// }
	// list.add(new JTableListColumn(str + "号", "emsNo", 100));
	// list.add(new JTableListColumn(str + "序号", "seqNum", 100));
	// list.add(new JTableListColumn("海关商品编码", "complex", 100));
	// list.add(new JTableListColumn("商品名称", "ptName", 100));
	// list.add(new JTableListColumn("商品规格", "ptSpec", 100));
	// list.add(new JTableListColumn("单位", "unit.name", 100));
	// list.add(new JTableListColumn("币制", "curr.name", 100));
	// DgCommonQuery.setTableColumns(list);
	// final DgCommonQuery dgCommonQuery = new DgCommonQuery();
	// dgCommonQuery.setDataSource(dataSource);
	// dgCommonQuery.setTitle("商品资料");
	// dgCommonQuery.setLike(false);
	// DgCommonQuery.setSingleResult(false);
	// dgCommonQuery.setVisible(true);
	// if (dgCommonQuery.isOk()) {
	// return dgCommonQuery.getReturnList();
	// }
	// return new ArrayList();
	// }

	// public List findTempTransferFactoryCommInfo(String
	// customsEnvelopBillCode) {
	// List dataSource = fptManageAction
	// .findTempTransferFactoryCommInfo(new Request(CommonVars
	// .getCurrUser(), true), customsEnvelopBillCode);
	// List<JTableListColumn> list = new Vector<JTableListColumn>();
	// list.add(new JTableListColumn("帐册/手册号", "emsNo", 100));
	// list.add(new JTableListColumn("帐册/手册序号", "seqNum", 90));
	// list.add(new JTableListColumn("海关商品编码", "complex.code", 120));
	// list.add(new JTableListColumn("商品名称", "ptName", 120));
	// list.add(new JTableListColumn("规格型号", "ptSpec", 120));
	// list.add(new JTableListColumn("单位", "unit.name", 60));
	// list.add(new JTableListColumn("币制", "curr.name", 60));
	// DgCommonQuery.setTableColumns(list);
	// final DgCommonQuery dgCommonQuery = new DgCommonQuery();
	// dgCommonQuery.setDataSource(dataSource);
	// dgCommonQuery.setTitle("关封商品资料");
	// dgCommonQuery.setLike(false);
	// DgCommonQuery.setSingleResult(false);
	// dgCommonQuery.setVisible(true);
	// if (dgCommonQuery.isOk()) {
	// return dgCommonQuery.getReturnList();
	// }
	// return new ArrayList();
	// }
	/**
	 * 获取单据中心的资料
	 */
	public Object getCasCommodityInfoByPara(boolean isImport, String dialogTitle) {
		List<TempObject> dataSource = fptManageAction.findBomNoByPara(
				new Request(CommonVars.getCurrUser(), true), isImport, null);
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("工厂料号", "object", "ptPart",
				DataType.STRING, 150));
		list.add(new JTableListColumn("工厂名称", "object1", "ptName",
				DataType.STRING, 150));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle(dialogTitle);
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 获得物料对象-->关封申请单表头
	 */
	public List findFptAppHeadType(final String isImportGoods) {
		List dataSource = fptManageAction.findMaterielByFptAppHeadType(
				new Request(CommonVars.getCurrUser()), isImportGoods);
		String title = "";
		if (isImportGoods.equals(FptInOutFlag.OUT)) {
			title = "转厂申请表---转出";
		} else if (isImportGoods.equals(FptInOutFlag.IN)) {
			title = "转厂申请表---转入";
		}
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("申请表编号", "appNo", 100));
		list.add(new JTableListColumn("客户/供应商", "scmCoc.name", 140));
		list.add(new JTableListColumn("转出企业手册/账册号", "emsNo", 100));
		list.add(new JTableListColumn("申请表有效期", "endDate", 100));
		list.add(new JTableListColumn("转入/转出", "inOutFlag", 80));
		list.add(new JTableListColumn("申报状态", "declareState", 80));
		list.add(new JTableListColumn("转出企业编码", "outTradeCode", 100));
		list.add(new JTableListColumn("转出企业名称", "outTradeName", 140));

		if (isImportGoods.equals(FptInOutFlag.OUT)) {
			list.add(new JTableListColumn("发货申报企业9位组织机构代码", "outAgentCode", 100));
			list.add(new JTableListColumn("发货申报企业9位组织机构名称", "outAgentName", 140));
		} else {
			list.add(new JTableListColumn("转入申报企业9位组织机构代码", "inAgentCode", 50));
			list.add(new JTableListColumn("转入申报企业9位组织机构名称", "inAgentName", 140));
		}
		list.add(new JTableListColumn("转入企业编码", "inTradeCode", 100));
		list.add(new JTableListColumn("转入企业名称", "inTradeName", 140));
		list.add(new JTableListColumn("项目类型", "projectType", 100, Integer.class));

		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		DgCommonQuery.setTableColumns(list);
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle(title);
		dgCommonQuery.setLike(true);
		dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				JTable table = dgCommonQuery.getJTable();
				table.getColumnModel().getColumn(13).setCellRenderer(// 项目类型
						new DefaultTableCellRenderer() {
							public Component getTableCellRendererComponent(
									JTable table, Object value,
									boolean isSelected, boolean hasFocus,
									int row, int column) {
								super.getTableCellRendererComponent(table,
										value, isSelected, hasFocus, row,
										column);
								super.setText((value == null) ? ""
										: ProjectType.getNote(Integer
												.valueOf(value.toString())));
								return this;
							}
						});
				table.getColumnModel().getColumn(5).setCellRenderer(// 转入/转出
						new DefaultTableCellRenderer() {
							public Component getTableCellRendererComponent(
									JTable table, Object value,
									boolean isSelected, boolean hasFocus,
									int row, int column) {
								super.getTableCellRendererComponent(table,
										value, isSelected, hasFocus, row,
										column);
								super.setText((value == null) ? ""
										: FptInOutFlag.getNote(value.toString()));
								return this;
							}
						});
				table.getColumnModel().getColumn(6).setCellRenderer(// 申报状态
						new DefaultTableCellRenderer() {
							public Component getTableCellRendererComponent(
									JTable table, Object value,
									boolean isSelected, boolean hasFocus,
									int row, int column) {
								super.getTableCellRendererComponent(table,
										value, isSelected, hasFocus, row,
										column);
								super.setText((value == null) ? ""
										: castValue(value));
								return this;
							}

							private String castValue(Object value) {
								return DeclareState.getDeclareStateSpec(value
										.toString());
							}
						});
				/** 申报状态 非空 1：原始状态，2：等待审批，3：正在执行，4：变更，5：退单 */
				// table.getColumnModel().getColumn(12).setCellRenderer(
				// new DefaultTableCellRenderer() {
				// public Component getTableCellRendererComponent(
				// JTable table, Object value,
				// boolean isSelected, boolean hasFocus,
				// int row, int column) {
				// super.getTableCellRendererComponent(table,
				// value, isSelected, hasFocus, row,
				// column);
				// super.setText((value == null) ? ""
				// : castValue(value));
				// return this;
				// }
				//
				// private String castValue(Object value) {
				// return DeclareState.getDeclareStateSpec(value
				// .toString());
				// }
				// });
				table.repaint();
			}
		});
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 获得所有关封单据
	 */
	public List findFptAppItemtype(String isImportGoods, String appNO) {
		List list = new Vector();
		list.add(new JTableListColumn("项号", "trNo", 40));
		list.add(new JTableListColumn("申表请序号", "listNo", 60));
		if (isImportGoods.equals(FptInOutFlag.IN)) {
			list.add(new JTableListColumn("转入手册/账册号", "inEmsNo", 100));
		}
		list.add(new JTableListColumn("商品编码", "codeTs.code", 100));
		list.add(new JTableListColumn("商品名称", "name", 150));
		list.add(new JTableListColumn("商品规格", "spec", 80));
		list.add(new JTableListColumn("计量单位", "unit.name", 50));
		list.add(new JTableListColumn("申报数量", "qty", 50));
		list.add(new JTableListColumn("法定数量", "qty1", 50));
		list.add(new JTableListColumn("法定单位", "unit1.name", 50));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQueryEnvelop = new DgCommonQuery();

		List dataSource = fptManageAction.findFptAppItemToFptBillItem(
				new Request(CommonVars.getCurrUser()), isImportGoods, appNO);
		String title = "";
		if (isImportGoods.equals(FptInOutFlag.OUT)) {
			title = "转厂申请表明细---转出";
		} else if (isImportGoods.equals(FptInOutFlag.IN)) {
			title = "转厂申请表明细---转入";
		}
		dgCommonQueryEnvelop.setDataSource(dataSource);
		dgCommonQueryEnvelop.setTitle(title);
		dgCommonQueryEnvelop.setLike(true);
		DgCommonQuery.setSingleResult(false);
		dgCommonQueryEnvelop.setVisible(true);
		if (dgCommonQueryEnvelop.isOk()) {
			return dgCommonQueryEnvelop.getReturnList();
		}
		return null;
	}

	/**
	 * 获得所有结转单料号级的明细
	 */
	public List findFptEmsNoCopBillNoToFptBillItemCopBillNo(int projectType,
			String isImportGoods, String appNo) {
		List list = new Vector();
		String str = "";
		switch (projectType) {
		case ProjectType.BCS:
			str = "手册";
			break;
		case ProjectType.BCUS:
			str = "帐册";
			break;
		case ProjectType.DZSC:
			str = "手册";
			break;
		}
		list.add(new JTableListColumn("申请表序号", "listNo", 60));
		if (isImportGoods.equals(FptInOutFlag.IN)) {
			list.add(new JTableListColumn("转入手册/账册号", "emsNo", 100));
		}
		list.add(new JTableListColumn("料号", "copNo", 100));
		list.add(new JTableListColumn("归并前名称", "copNoName", 100));
		list.add(new JTableListColumn("归并后名称", "copNoSpec", 100));
		list.add(new JTableListColumn(str + "序号", "seqNum", 100));
		list.add(new JTableListColumn("商品编码", "complex", 100));
		list.add(new JTableListColumn("商品名称", "ptName", 150));
		list.add(new JTableListColumn("商品规格", "ptSpec", 80));
		list.add(new JTableListColumn("计量单位", "unit", 50));
		list.add(new JTableListColumn("申报数量", "qty", 50));
		list.add(new JTableListColumn("工厂单位", "ptUnit.name", 50));
		list.add(new JTableListColumn("工厂数量", "ptAmount", 50));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQueryEnvelop = new DgCommonQuery();

		List dataSource = fptManageAction
				.findFptEmsNoCopBillNoToFptBillItemCopBillNo(new Request(
						CommonVars.getCurrUser()), projectType, isImportGoods,
						appNo);
		dgCommonQueryEnvelop.setDataSource(dataSource);
		dgCommonQueryEnvelop.setTitle("来源于归并关系资料");
		dgCommonQueryEnvelop.setLike(true);
		DgCommonQuery.setSingleResult(false);
		dgCommonQueryEnvelop.setVisible(true);
		if (dgCommonQueryEnvelop.isOk()) {
			return dgCommonQueryEnvelop.getReturnList();
		}
		return null;
	}

	/**
	 * 查询不在转厂起初单的商品编码
	 * 
	 * @param initBillId
	 * @return
	 */
	public List findComplexNotInInitBill(String initBillId,
			String customsEnvelopBillCode) {
		// List dataSource =
		// fptManageAction.findComplexNotInInitBill(
		// new Request(CommonVars.getCurrUser(), true), initBillId,
		// customsEnvelopBillCode);
		// List listData = checkSame(dataSource);
		// List<JTableListColumn> list = new Vector<JTableListColumn>();
		// list.add(new JTableListColumn("帐册/手册号", "emsNo", 100));
		// list.add(new JTableListColumn("账册/手册序号", "seqNum", 50));
		// list.add(new JTableListColumn("海关商品编码", "complex.code", 120));
		// list.add(new JTableListColumn("商品名称", "ptName", 120));
		// list.add(new JTableListColumn("单位", "unit.name", 60));
		// DgCommonQuery.setTableColumns(list);
		// final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		// dgCommonQuery.setDataSource(listData);
		// dgCommonQuery.setTitle("商品资料");
		// dgCommonQuery.setLike(false);
		// DgCommonQuery.setSingleResult(false);
		// dgCommonQuery.setVisible(true);
		// if (dgCommonQuery.isOk()) {
		// return dgCommonQuery.getReturnList();
		// }
		return new ArrayList();
	}

	// /**
	// * 去掉相同的数据
	// *
	// * @param dataSource
	// * @return
	// */
	// private List checkSame(List<CustomsEnvelopCommodityInfo> dataSource) {
	// List listData = new ArrayList();
	// for (int i = 0; i < dataSource.size(); i++) {
	// CustomsEnvelopCommodityInfo info_i = (CustomsEnvelopCommodityInfo)
	// dataSource
	// .get(i);
	// for (int j = i + 1; j < dataSource.size(); j++) {
	// if (j > dataSource.size())
	// break;
	// CustomsEnvelopCommodityInfo info_j = (CustomsEnvelopCommodityInfo)
	// dataSource
	// .get(j);
	// if (info_j.getSeqNum().equals(info_i.getSeqNum())
	// && info_j.getPtName().equals(info_i.getPtName())
	// && info_j.getComplex().equals(info_i.getComplex())
	// && info_j.getUnit().equals(info_i.getUnit())) {
	// dataSource.remove(j);
	//
	// }
	// }
	// listData.add((CustomsEnvelopCommodityInfo) dataSource.get(i));
	// }
	// return listData;
	// }

	/**
	 * 获得状态为草稿的报关清单来自进出口类型
	 */
	public Object getApplyToCustomsBillListByType(int impExpType) {
		List list = new Vector();
		list.add(new JTableListColumn("进出口类型", "impExpType", 100));
		list.add(new JTableListColumn("清单状态", "listState", 100));
		list.add(new JTableListColumn("电子帐册编号", "emsHeadH2k.emsNo", 80));
		list.add(new JTableListColumn("清单编号", "listNo", 100));
		list.add(new JTableListColumn("经营单位编码", "emsHeadH2k.tradeCode", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();

		EncAction encAction = (EncAction) CommonVars.getApplicationContext()
				.getBean("encAction");
		List dataSource = encAction.findApplyToCustomsBillListByTypeAndState(
				new Request(CommonVars.getCurrUser(), true), impExpType,
				ApplyToCustomsBillList.DRAFT);
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("请选择报关清单");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				JTable table = dgCommonQuery.getJTable();
				table.getColumnModel().getColumn(1)
						.setCellRenderer(new DefaultTableCellRenderer() {
							public Component getTableCellRendererComponent(
									JTable table, Object value,
									boolean isSelected, boolean hasFocus,
									int row, int column) {
								super.getTableCellRendererComponent(table,
										value, isSelected, hasFocus, row,
										column);
								String str = "";
								if (value != null) {
									switch (Integer.parseInt(value.toString())) {
									case ImpExpType.DIRECT_IMPORT:
										str = "料件进口";
										break;
									case ImpExpType.TRANSFER_FACTORY_IMPORT:
										str = "料件转厂";
										break;
									case ImpExpType.BACK_FACTORY_REWORK:
										str = "退厂返工";
										break;
									case ImpExpType.GENERAL_TRADE_IMPORT:
										str = "一般贸易进口";
										break;
									case ImpExpType.DIRECT_EXPORT:
										str = "成品出口";
										break;
									case ImpExpType.TRANSFER_FACTORY_EXPORT:
										str = "转厂出口";
										break;
									case ImpExpType.BACK_MATERIEL_EXPORT:
										str = "退料出口";
										break;
									case ImpExpType.REWORK_EXPORT:
										str = "返工复出";
										break;
									case ImpExpType.REMIAN_MATERIAL_BACK_PORT:
										str = "边角料退港";
										break;
									case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES:
										str = "边角料内销";
										break;
									case ImpExpType.GENERAL_TRADE_EXPORT:
										str = "一般贸易出口";
										break;
									}
								}
								this.setText(str);
								return this;
							}
						});
				table.getColumnModel().getColumn(2)
						.setCellRenderer(new DefaultTableCellRenderer() {
							public Component getTableCellRendererComponent(
									JTable table, Object value,
									boolean isSelected, boolean hasFocus,
									int row, int column) {
								super.getTableCellRendererComponent(table,
										value, isSelected, hasFocus, row,
										column);
								String str = "";
								if (value != null) {
									switch (Integer.parseInt(value.toString())) {
									case ApplyToCustomsBillList.DRAFT:
										str = "草稿";
										break;
									case ApplyToCustomsBillList.ALREADY_SEND:
										str = "审批通过";
										break;
									case ApplyToCustomsBillList.PASSED:
										str = "审批未通过";
										break;
									// case
									// ApplyToCustomsBillList.GENERATED_CUSTOMS_DECLARATION:
									// str = "已转报关单";
									// break;
									}
								}
								this.setText(str);
								return this;
							}
						});
			}
		});

		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 获得状态为草稿的报关清单来自进出口类型
	 */
	public Object getDzscCustomsBillListByType(int impExpType) {
		List list = new Vector();
		list.add(new JTableListColumn("进出口类型", "impExpType", 100));
		list.add(new JTableListColumn("清单状态", "listState", 100));
		list.add(new JTableListColumn("电子帐册编号", "emsHeadH2k.emsNo", 80));
		list.add(new JTableListColumn("清单编号", "listNo", 100));
		list.add(new JTableListColumn("经营单位编码", "emsHeadH2k.tradeCode", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		DzscListAction dzscListAction = (DzscListAction) CommonVars
				.getApplicationContext().getBean("dzscListAction");
		List dataSource = dzscListAction.findDzscCustomsBillListByTypeAndState(
				new Request(CommonVars.getCurrUser(), true), impExpType,
				ApplyToCustomsBillList.DRAFT);
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("请选择报关清单");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				JTable table = dgCommonQuery.getJTable();
				table.getColumnModel().getColumn(1)
						.setCellRenderer(new DefaultTableCellRenderer() {
							public Component getTableCellRendererComponent(
									JTable table, Object value,
									boolean isSelected, boolean hasFocus,
									int row, int column) {
								super.getTableCellRendererComponent(table,
										value, isSelected, hasFocus, row,
										column);
								String str = "";
								if (value != null) {
									switch (Integer.parseInt(value.toString())) {
									case ImpExpType.DIRECT_IMPORT:
										str = "料件进口";
										break;
									case ImpExpType.TRANSFER_FACTORY_IMPORT:
										str = "料件转厂";
										break;
									case ImpExpType.BACK_FACTORY_REWORK:
										str = "退厂返工";
										break;
									case ImpExpType.GENERAL_TRADE_IMPORT:
										str = "一般贸易进口";
										break;
									case ImpExpType.DIRECT_EXPORT:
										str = "成品出口";
										break;
									case ImpExpType.TRANSFER_FACTORY_EXPORT:
										str = "转厂出口";
										break;
									case ImpExpType.BACK_MATERIEL_EXPORT:
										str = "退料出口";
										break;
									case ImpExpType.REWORK_EXPORT:
										str = "返工复出";
										break;
									case ImpExpType.REMIAN_MATERIAL_BACK_PORT:
										str = "边角料退港";
										break;
									case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES:
										str = "边角料内销";
										break;
									case ImpExpType.GENERAL_TRADE_EXPORT:
										str = "一般贸易出口";
										break;
									}
								}
								this.setText(str);
								return this;
							}
						});
				table.getColumnModel().getColumn(2)
						.setCellRenderer(new DefaultTableCellRenderer() {
							public Component getTableCellRendererComponent(
									JTable table, Object value,
									boolean isSelected, boolean hasFocus,
									int row, int column) {
								super.getTableCellRendererComponent(table,
										value, isSelected, hasFocus, row,
										column);
								String str = "";
								if (value != null) {
									if (DzscState.ORIGINAL.equals(value
											.toString())) {
										str = "草稿";
									} else if (DzscState.APPLY.equals(value
											.toString())) {
										str = "审批状态";
									} else if (DzscState.EXECUTE.equals(value
											.toString())) {
										str = "审批通过";
									} else if (DzscState.BACK_BILL.equals(value
											.toString())) {
										str = "审批未通过";
									}
								}
								this.setText(str);
								return this;
							}
						});
			}
		});

		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	// /**
	// * 获得所有关封单据
	// */
	// public Object getCustomsEnvelopBill(boolean impExpGoodsFlag) {
	// List list = new Vector();
	// list.add(new JTableListColumn("生效", "isAvailability", 50));
	// list.add(new JTableListColumn("关封号", "customsEnvelopBillNo", 100));
	// list
	// .add(new JTableListColumn("购销合同编号",
	// "purchaseAndSaleContractNo", 80));
	// list.add(new JTableListColumn("生效日期", "beginAvailability", 80));
	// list.add(new JTableListColumn("有效截止日期", "endAvailability", 80));
	// list.add(new JTableListColumn("客户/供应商名称", "scmCoc.name", 150));
	// list
	// .add(new JTableListColumn("转入企业帐册编号", "importEnterpriseEmsNo",
	// 100));
	// list
	// .add(new JTableListColumn("转出企业帐册编号", "exportEnterpriseEmsNo",
	// 100));
	// list.add(new JTableListColumn("审批海关", "customs.name", 60));
	// list.add(new JTableListColumn("是否结案", "isEndCase", 50));
	// list.add(new JTableListColumn("结案日期", "endCaseDate", 80));
	// list.add(new JTableListColumn("结转报关单号",
	// "carryForwardApplyToCustomsBillNo", 80));
	// list.add(new JTableListColumn("备注", "memo", 100));
	// DgCommonQuery.setTableColumns(list);
	// final DgCommonQuery dgCommonQuery = new DgCommonQuery();
	//
	// fptManageAction fptManageAction =
	// (fptManageAction) CommonVars
	// .getApplicationContext().getBean("fptManageAction");
	// List dataSource = fptManageAction.findCustomsEnvelopBill(
	// new Request(CommonVars.getCurrUser(), true), impExpGoodsFlag,
	// true);
	// dgCommonQuery.setDataSource(dataSource);
	// dgCommonQuery.setTitle("请选择报关清单");
	// dgCommonQuery.setLike(false);
	// DgCommonQuery.setSingleResult(true);
	// dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
	// public void windowOpened(java.awt.event.WindowEvent e) {
	// JTable table = dgCommonQuery.getJTable();
	// table.getColumnModel().getColumn(1).setCellRenderer(
	// new TableCheckBoxRender());
	// table.getColumnModel().getColumn(10).setCellRenderer(
	// new TableCheckBoxRender());
	// }
	// });
	//
	// dgCommonQuery.setVisible(true);
	// if (dgCommonQuery.isOk()) {
	// return dgCommonQuery.getReturnValue();
	// }
	// return null;
	// }

	// /**
	// * 获得所有关封单据
	// */
	// public Object getCustomsEnvelopBill() {
	// List list = new Vector();
	// list.add(new JTableListColumn("生效", "isAvailability", 50));
	// list.add(new JTableListColumn("关封号", "customsEnvelopBillNo", 100));
	// list
	// .add(new JTableListColumn("购销合同编号",
	// "purchaseAndSaleContractNo", 80));
	// list.add(new JTableListColumn("生效日期", "beginAvailability", 80));
	// list.add(new JTableListColumn("有效截止日期", "endAvailability", 80));
	// list.add(new JTableListColumn("客户/供应商名称", "scmCoc.name", 150));
	// list
	// .add(new JTableListColumn("转入企业帐册编号", "importEnterpriseEmsNo",
	// 100));
	// list
	// .add(new JTableListColumn("转出企业帐册编号", "exportEnterpriseEmsNo",
	// 100));
	// list.add(new JTableListColumn("审批海关", "customs.name", 60));
	// list.add(new JTableListColumn("是否结案", "isEndCase", 50));
	// list.add(new JTableListColumn("结案日期", "endCaseDate", 80));
	// list.add(new JTableListColumn("结转报关单号",
	// "carryForwardApplyToCustomsBillNo", 80));
	// list.add(new JTableListColumn("备注", "memo", 100));
	// DgCommonQuery.setTableColumns(list);
	// final DgCommonQuery dgCommonQuery = new DgCommonQuery();
	//
	// fptManageAction fptManageAction =
	// (fptManageAction) CommonVars
	// .getApplicationContext().getBean("fptManageAction");
	// List dataSource = fptManageAction
	// .findCustomsEnvelopBill(new Request(CommonVars.getCurrUser(),
	// true));
	// dgCommonQuery.setDataSource(dataSource);
	// dgCommonQuery.setTitle("请选择报关清单");
	// dgCommonQuery.setLike(false);
	// DgCommonQuery.setSingleResult(true);
	// dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
	// public void windowOpened(java.awt.event.WindowEvent e) {
	// JTable table = dgCommonQuery.getJTable();
	// table.getColumnModel().getColumn(1).setCellRenderer(
	// new TableCheckBoxRender());
	// table.getColumnModel().getColumn(10).setCellRenderer(
	// new TableCheckBoxRender());
	// }
	// });
	//
	// dgCommonQuery.setVisible(true);
	// if (dgCommonQuery.isOk()) {
	// return dgCommonQuery.getReturnValue();
	// }
	// return null;
	// }

	// /**
	// * 获得所有关封单据
	// */
	// public List getCustomsEnvelopCommodityInfoByScmcoc(ScmCoc scmCoc,
	// Boolean isCustomer) {
	// List list = new Vector();
	// list.add(new JTableListColumn("项目类型", "customsEnvelopBill.projectType",
	// 60));
	// list.add(new JTableListColumn("备案序号", "seqNum", 50));
	// list.add(new JTableListColumn("商品编码", "complex.code", 100));
	// list.add(new JTableListColumn("商品名称", "ptName", 150));
	// list.add(new JTableListColumn("商品规格", "ptSpec", 80));
	// list.add(new JTableListColumn("单价", "unitPrice", 50));
	// list.add(new JTableListColumn("币制", "curr.currSymb", 80));
	// DgCommonQuery.setTableColumns(list);
	// final DgCommonQuery dgCommonQueryEnvelop = new DgCommonQuery();
	//
	// List dataSource = fptManageAction
	// .findCustomsEnvelopCommodityInfoByScmCoc(new Request(CommonVars
	// .getCurrUser()), scmCoc, isCustomer);
	// dgCommonQueryEnvelop.setDataSource(dataSource);
	// dgCommonQueryEnvelop.setTitle("请选商品");
	// dgCommonQueryEnvelop.setLike(true);
	// dgCommonQueryEnvelop
	// .addWindowListener(new java.awt.event.WindowAdapter() {
	// public void windowOpened(WindowEvent e) {
	// JTable table = dgCommonQueryEnvelop.getJTable();
	// table.getColumnModel().getColumn(1).setCellRenderer(
	// new ProjectTypeRenderer());
	// }
	//
	// });
	// DgCommonQuery.setSingleResult(false);
	// dgCommonQueryEnvelop.setVisible(true);
	// if (dgCommonQueryEnvelop.isOk()) {
	// return dgCommonQueryEnvelop.getReturnList();
	// }
	// return new ArrayList();
	// }

	// /**
	// * 获得所有关封单据
	// */
	// public Object getCustomsEnvelopBill() {
	// List list = new Vector();
	// list.add(new JTableListColumn("生效", "isAvailability", 50));
	// list.add(new JTableListColumn("关封号", "customsEnvelopBillNo", 100));
	// list
	// .add(new JTableListColumn("购销合同编号",
	// "purchaseAndSaleContractNo", 80));
	// list.add(new JTableListColumn("生效日期", "beginAvailability", 80));
	// list.add(new JTableListColumn("有效截止日期", "endAvailability", 80));
	// list.add(new JTableListColumn("客户/供应商名称", "scmCoc.name", 150));
	// list
	// .add(new JTableListColumn("转入企业帐册编号", "importEnterpriseEmsNo",
	// 100));
	// list
	// .add(new JTableListColumn("转出企业帐册编号", "exportEnterpriseEmsNo",
	// 100));
	// list.add(new JTableListColumn("审批海关", "customs.name", 60));
	// list.add(new JTableListColumn("是否结案", "isEndCase", 50));
	// list.add(new JTableListColumn("结案日期", "endCaseDate", 80));
	// list.add(new JTableListColumn("结转报关单号",
	// "carryForwardApplyToCustomsBillNo", 80));
	// list.add(new JTableListColumn("备注", "memo", 100));
	// DgCommonQuery.setTableColumns(list);
	// final DgCommonQuery dgCommonQuery = new DgCommonQuery();
	//
	// fptManageAction fptManageAction =
	// (fptManageAction) CommonVars
	// .getApplicationContext().getBean("fptManageAction");
	// List dataSource = fptManageAction
	// .findCustomsEnvelopBill(new Request(CommonVars.getCurrUser(),
	// true));
	// dgCommonQuery.setDataSource(dataSource);
	// dgCommonQuery.setTitle("请选择报关清单");
	// dgCommonQuery.setLike(false);
	// DgCommonQuery.setSingleResult(true);
	// dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
	// public void windowOpened(java.awt.event.WindowEvent e) {
	// JTable table = dgCommonQuery.getJTable();
	// table.getColumnModel().getColumn(1).setCellRenderer(
	// new TableCheckBoxRender());
	// table.getColumnModel().getColumn(10).setCellRenderer(
	// new TableCheckBoxRender());
	// }
	// });
	//
	// dgCommonQuery.setVisible(true);
	// if (dgCommonQuery.isOk()) {
	// return dgCommonQuery.getReturnValue();
	// }
	// return null;
	// }

	// /**
	// * 获得所有关封单据
	// */
	// public List getCustomsEnvelopCommodityInfoByScmcoc(ScmCoc scmCoc,
	// Boolean isCustomer) {
	// List list = new Vector();
	// list.add(new JTableListColumn("项目类型", "customsEnvelopBill.projectType",
	// 60));
	// list.add(new JTableListColumn("备案序号", "seqNum", 50));
	// list.add(new JTableListColumn("商品编码", "complex.code", 100));
	// list.add(new JTableListColumn("商品名称", "ptName", 150));
	// list.add(new JTableListColumn("商品规格", "ptSpec", 80));
	// list.add(new JTableListColumn("单价", "unitPrice", 50));
	// list.add(new JTableListColumn("币制", "curr.currSymb", 80));
	// DgCommonQuery.setTableColumns(list);
	// final DgCommonQuery dgCommonQueryEnvelop = new DgCommonQuery();
	// List dataSource =null;
	// // List dataSource = fptManageAction
	// // .findCustomsEnvelopCommodityInfoByScmCoc(new Request(CommonVars
	// // .getCurrUser()), scmCoc, isCustomer);
	// dgCommonQueryEnvelop.setDataSource(dataSource);
	// dgCommonQueryEnvelop.setTitle("请选商品");
	// dgCommonQueryEnvelop.setLike(true);
	// dgCommonQueryEnvelop
	// .addWindowListener(new java.awt.event.WindowAdapter() {
	// public void windowOpened(WindowEvent e) {
	// JTable table = dgCommonQueryEnvelop.getJTable();
	// table.getColumnModel().getColumn(1).setCellRenderer(
	// new ProjectTypeRenderer());
	// }
	//
	// });
	// DgCommonQuery.setSingleResult(false);
	// dgCommonQueryEnvelop.setVisible(true);
	// if (dgCommonQueryEnvelop.isOk()) {
	// return dgCommonQueryEnvelop.getReturnList();
	// }
	// return new ArrayList();
	// }
	//
	// =======
	// >>>>>>> .r13749
	// class ProjectTypeRenderer extends DefaultTableCellRenderer {
	// public Component getTableCellRendererComponent(JTable table,
	// Object value, boolean isSelected, boolean hasFocus, int row,
	// int column) {
	// super.getTableCellRendererComponent(table, value, isSelected,
	// hasFocus, row, column);
	// String strvalue = "";
	// if (value == null) {
	// strvalue = "";
	// } else if (Integer.parseInt(value.toString()) == ProjectType.BCUS) {
	// strvalue = "联网监管";
	// } else if (Integer.parseInt(value.toString()) == ProjectType.BCS) {
	// strvalue = "纸质手册";
	// } else if (Integer.parseInt(value.toString()) == ProjectType.DZSC) {
	// strvalue = "电子手册";
	// }
	// this.setText(strvalue);
	// return this;
	// }
	// }

	/** 获得转厂单的表头 */
	public FptAppHead findFptAppHeadByNotExceute(FptAppHead outHead) {
		List dataSource = fptManageAction.findFptAppHeadByNotExceute(
				new Request(CommonVars.getCurrUser()), outHead);

		List<JTableListColumn> list = new Vector<JTableListColumn>();
		// list.add(new JTableListColumn("报关单号码", "customsDeclarationCode",
		// 100));
		// list.add(new JTableListColumn("金额", "money", 100));
		// list.add(new JTableListColumn("进出口类型", "impExpType", 100));
		// list.add(new JTableListColumn("报关单来源", "projectType", 100));

		list.add(new JTableListColumn("转入转出标记", "inOutFlag", 75));
		list.add(new JTableListColumn("申请表编号", "appNo", 100));
		list.add(new JTableListColumn("电子口岸统一编号", "seqNo", 80));
		list.add(new JTableListColumn("转出手册/账册编号", "emsNo", 100));
		list.add(new JTableListColumn("转出企业内部编号", "outCopAppNo", 100));
		// list.add(new JTableListColumn("申报状态", "declareState", 100));
		// list.add(new JTableListColumn("修改标记", "modifyMarkState", 100));
		// list.add(new JTableListColumn("申请表类型", "appClass", 80));
		list.add(new JTableListColumn("企业合同号", "contrNo", 80));
		list.add(new JTableListColumn("转出企业代码", "outTradeCode", 150));
		list.add(new JTableListColumn("转出企业名称", "outTradeName", 60));
		// list.add(new JTableListColumn("转出地", "outDistrict.name", 50));
		// list.add(new JTableListColumn("转入企业代码", "inTradeCode", 80));
		// list.add(new JTableListColumn("转入企业名称", "inTradeName", 80));

		// list.add(new JTableListColumn("转入地", "inDistrict.name", 100));
		// list.add(new JTableListColumn("转出地海关", "outCustoms.name", 100));

		// list.add(new JTableListColumn("转出企业批准证编号", "outLiceNo", 100));
		// list.add(new JTableListColumn("转出申报企业代码", "outTradeCode2", 100));
		// list.add(new JTableListColumn("转出申报企业名称", "outTradeName2", 100));
		// list.add(new JTableListColumn("申报日期", "outDate", 100));
		// list.add(new JTableListColumn("申报企业9位组织机构代码", "outAgentCode", 100));
		// list.add(new JTableListColumn("申报企业组织机构名称", "outAgentName", 100));
		// list.add(new JTableListColumn("预计运输耗时（天）", "conveyDay", 100));
		// list.add(new JTableListColumn("送货距离（公里）", "conveySpa", 100));
		// list.add(new JTableListColumn("转出企业法人/联系电话", "outCorp", 100));
		// list.add(new JTableListColumn("转出申报人/联系电话", "outDecl", 100));
		// list.add(new JTableListColumn("转出备注", "outNote", 100));

		list.add(new JTableListColumn("转入企业内部编号", "inCopAppNo", 100));
		// list.add(new JTableListColumn("转入地海关", "inCustoms.name", 100));
		// list.add(new JTableListColumn("转入申报企业9位组织机构代码", "inAgentCode", 100));
		// list.add(new JTableListColumn("转入申报企业组织机构名称", "inAgentName", 100));
		// list.add(new JTableListColumn("转入企业法人/联系电话", "inCorp", 100));
		// list.add(new JTableListColumn("转入申报人/联系电话", "inDecl", 100));
		// list.add(new JTableListColumn("转入备注", "inNote", 100));
		// list.add(new JTableListColumn("转入企业批准证编号", "inLiceNo", 100));
		// list.add(new JTableListColumn("转入申报日期", "inDate", 100));
		// list.add(new JTableListColumn("转入申报企业代码", "inTradeCode2", 100));
		// list.add(new JTableListColumn("转入申报企业名称", "inTradeName2", 100));
		// list.add(new JTableListColumn("录入员", "aclUser.name", 100));
		// list.add(new JTableListColumn("项目类型", "projectType", 100));

		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("获得不是正在执行转厂单的表头");
		dgCommonQuery.setLike(false);

		dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				JTable tb = dgCommonQuery.getJTable();
				tb.getColumnModel().getColumn(1)
						.setCellRenderer(new DefaultTableCellRenderer() {
							public Component getTableCellRendererComponent(
									JTable table, Object value,
									boolean isSelected, boolean hasFocus,
									int row, int column) {
								super.getTableCellRendererComponent(table,
										value, isSelected, hasFocus, row,
										column);
								super.setText((value == null) ? ""
										: castValue(value));
								return this;
							}

							private String castValue(Object value) {
								return FptInOutFlag.getNote(value.toString());
							}
						});
				tb.repaint();
			}
		});
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return (FptAppHead) dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/** 获得转厂单的表头 */
	public FptAppHead findFptAppHeadByInOutFlag(String fptInOutFlag,
			String fptDeclareState, String emsDeclareState) {
		List dataSource = new ArrayList();
		// if (fptDeclareState == null) {
		// dataSource = fptManageAction.findFptAppHeadByInOutFlag(new
		// Request(CommonVars.getCurrUser()), fptInOutFlag);
		// } else {
		dataSource = fptManageAction.findFptAppHeadByInOutFlag(new Request(
				CommonVars.getCurrUser()), fptInOutFlag, fptDeclareState,
				emsDeclareState);
		// }

		List<JTableListColumn> list = new Vector<JTableListColumn>();
		// list.add(new JTableListColumn("报关单号码", "customsDeclarationCode",
		// 100));
		// list.add(new JTableListColumn("金额", "money", 100));
		// list.add(new JTableListColumn("进出口类型", "impExpType", 100));
		// list.add(new JTableListColumn("报关单来源", "projectType", 100));

		list.add(new JTableListColumn("转入转出标记", "inOutFlag", 75));
		list.add(new JTableListColumn("申请表编号", "appNo", 100));
		list.add(new JTableListColumn("电子口岸统一编号", "seqNo", 130));
		list.add(new JTableListColumn("转出手册/账册编号", "emsNo", 100));
		list.add(new JTableListColumn("转出企业内部编号", "outCopAppNo", 150));
		// list.add(new JTableListColumn("申报状态", "declareState", 100));
		// list.add(new JTableListColumn("修改标记", "modifyMarkState", 100));
		// list.add(new JTableListColumn("申请表类型", "appClass", 80));
		list.add(new JTableListColumn("企业合同号", "contrNo", 100));
		list.add(new JTableListColumn("转出企业代码", "outTradeCode", 80));
		list.add(new JTableListColumn("转出企业名称", "outTradeName", 150));
		// list.add(new JTableListColumn("转出地", "outDistrict.name", 50));
		list.add(new JTableListColumn("转入手册/账册编号", "inEmsNo", 100));
		list.add(new JTableListColumn("转入企业代码", "inTradeCode", 80));
		list.add(new JTableListColumn("转入企业名称", "inTradeName", 150));
		// list.add(new JTableListColumn("转入地", "inDistrict.name", 100));
		// list.add(new JTableListColumn("转出地海关", "outCustoms.name", 100));

		// list.add(new JTableListColumn("转出企业批准证编号", "outLiceNo", 100));
		// list.add(new JTableListColumn("转出申报企业代码", "outTradeCode2", 100));
		// list.add(new JTableListColumn("转出申报企业名称", "outTradeName2", 100));
		// list.add(new JTableListColumn("申报日期", "outDate", 100));
		// list.add(new JTableListColumn("申报企业9位组织机构代码", "outAgentCode", 100));
		// list.add(new JTableListColumn("申报企业组织机构名称", "outAgentName", 100));
		// list.add(new JTableListColumn("预计运输耗时（天）", "conveyDay", 100));
		// list.add(new JTableListColumn("送货距离（公里）", "conveySpa", 100));
		// list.add(new JTableListColumn("转出企业法人/联系电话", "outCorp", 100));
		// list.add(new JTableListColumn("转出申报人/联系电话", "outDecl", 100));
		// list.add(new JTableListColumn("转出备注", "outNote", 100));

		list.add(new JTableListColumn("转入企业内部编号", "inCopAppNo", 150));
		// list.add(new JTableListColumn("转入地海关", "inCustoms.name", 100));
		// list.add(new JTableListColumn("转入申报企业9位组织机构代码", "inAgentCode", 100));
		// list.add(new JTableListColumn("转入申报企业组织机构名称", "inAgentName", 100));
		// list.add(new JTableListColumn("转入企业法人/联系电话", "inCorp", 100));
		// list.add(new JTableListColumn("转入申报人/联系电话", "inDecl", 100));
		// list.add(new JTableListColumn("转入备注", "inNote", 100));
		// list.add(new JTableListColumn("转入企业批准证编号", "inLiceNo", 100));
		// list.add(new JTableListColumn("转入申报日期", "inDate", 100));
		// list.add(new JTableListColumn("转入申报企业代码", "inTradeCode2", 100));
		// list.add(new JTableListColumn("转入申报企业名称", "inTradeName2", 100));
		// list.add(new JTableListColumn("录入员", "aclUser.name", 100));
		// list.add(new JTableListColumn("项目类型", "projectType", 100));

		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("获得申请表的表头");
		dgCommonQuery.setLike(false);

		dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				JTable tb = dgCommonQuery.getJTable();
				tb.getColumnModel().getColumn(1)
						.setCellRenderer(new DefaultTableCellRenderer() {
							public Component getTableCellRendererComponent(
									JTable table, Object value,
									boolean isSelected, boolean hasFocus,
									int row, int column) {
								super.getTableCellRendererComponent(table,
										value, isSelected, hasFocus, row,
										column);
								super.setText((value == null) ? ""
										: castValue(value));
								return this;
							}

							private String castValue(Object value) {
								return FptInOutFlag.getNote(value.toString());
							}
						});
				tb.repaint();
			}
		});
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return (FptAppHead) dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/** 获得转厂单的表头 */
	public List<FptAppHead> findFptAppHeadByOutQp(String outTradeCode) {
		List dataSource = fptManageAction.findFptAppHeadByQp(new Request(
				CommonVars.getCurrUser()), outTradeCode);

		List<JTableListColumn> list = new Vector<JTableListColumn>();
		// list.add(new JTableListColumn("报关单号码", "customsDeclarationCode",
		// 100));
		// list.add(new JTableListColumn("金额", "money", 100));
		// list.add(new JTableListColumn("进出口类型", "impExpType", 100));
		// list.add(new JTableListColumn("报关单来源", "projectType", 100));

		list.add(new JTableListColumn("转入转出标记", "inOutFlag", 75));
		list.add(new JTableListColumn("申请表编号", "appNo", 100));
		list.add(new JTableListColumn("电子口岸统一编号", "seqNo", 80));
		list.add(new JTableListColumn("转出手册/账册编号", "emsNo", 100));
		list.add(new JTableListColumn("转出企业内部编号", "outCopAppNo", 100));
		// list.add(new JTableListColumn("申报状态", "declareState", 100));
		// list.add(new JTableListColumn("修改标记", "modifyMarkState", 100));
		// list.add(new JTableListColumn("申请表类型", "appClass", 80));
		list.add(new JTableListColumn("企业合同号", "contrNo", 80));
		list.add(new JTableListColumn("转出企业代码", "outTradeCode", 150));
		list.add(new JTableListColumn("转出企业名称", "outTradeName", 60));
		// list.add(new JTableListColumn("转出地", "outDistrict.name", 50));
		// list.add(new JTableListColumn("转入企业代码", "inTradeCode", 80));
		// list.add(new JTableListColumn("转入企业名称", "inTradeName", 80));

		// list.add(new JTableListColumn("转入地", "inDistrict.name", 100));
		// list.add(new JTableListColumn("转出地海关", "outCustoms.name", 100));

		// list.add(new JTableListColumn("转出企业批准证编号", "outLiceNo", 100));
		// list.add(new JTableListColumn("转出申报企业代码", "outTradeCode2", 100));
		// list.add(new JTableListColumn("转出申报企业名称", "outTradeName2", 100));
		// list.add(new JTableListColumn("申报日期", "outDate", 100));
		// list.add(new JTableListColumn("申报企业9位组织机构代码", "outAgentCode", 100));
		// list.add(new JTableListColumn("申报企业组织机构名称", "outAgentName", 100));
		// list.add(new JTableListColumn("预计运输耗时（天）", "conveyDay", 100));
		// list.add(new JTableListColumn("送货距离（公里）", "conveySpa", 100));
		// list.add(new JTableListColumn("转出企业法人/联系电话", "outCorp", 100));
		// list.add(new JTableListColumn("转出申报人/联系电话", "outDecl", 100));
		// list.add(new JTableListColumn("转出备注", "outNote", 100));

		list.add(new JTableListColumn("转入企业内部编号", "inCopAppNo", 100));
		// list.add(new JTableListColumn("转入地海关", "inCustoms.name", 100));
		// list.add(new JTableListColumn("转入申报企业9位组织机构代码", "inAgentCode", 100));
		// list.add(new JTableListColumn("转入申报企业组织机构名称", "inAgentName", 100));
		// list.add(new JTableListColumn("转入企业法人/联系电话", "inCorp", 100));
		// list.add(new JTableListColumn("转入申报人/联系电话", "inDecl", 100));
		// list.add(new JTableListColumn("转入备注", "inNote", 100));
		// list.add(new JTableListColumn("转入企业批准证编号", "inLiceNo", 100));
		// list.add(new JTableListColumn("转入申报日期", "inDate", 100));
		// list.add(new JTableListColumn("转入申报企业代码", "inTradeCode2", 100));
		// list.add(new JTableListColumn("转入申报企业名称", "inTradeName2", 100));
		// list.add(new JTableListColumn("录入员", "aclUser.name", 100));
		// list.add(new JTableListColumn("项目类型", "projectType", 100));

		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("获得QP申请表的表头");
		dgCommonQuery.setLike(false);

		dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				JTable tb = dgCommonQuery.getJTable();
				tb.getColumnModel().getColumn(1)
						.setCellRenderer(new DefaultTableCellRenderer() {
							public Component getTableCellRendererComponent(
									JTable table, Object value,
									boolean isSelected, boolean hasFocus,
									int row, int column) {
								super.getTableCellRendererComponent(table,
										value, isSelected, hasFocus, row,
										column);
								super.setText((value == null) ? ""
										: castValue(value));
								return this;
							}

							private String castValue(Object value) {
								return FptInOutFlag.getNote(value.toString());
							}
						});
				tb.repaint();
			}
		});
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return (List<FptAppHead>) dgCommonQuery.getReturnList();
		}
		return null;
	}

	// //////////////////////////////////////////////////////////////////
	// 转厂申请表新增来自 DZSC,BCS,BCUS
	// //////////////////////////////////////////////////////////////////

	/**
	 * 获得正在执行的合同的料件 (bcs)
	 */
	public List<ContractImg> findContractImgByProcessExe(final String parentId,
			final String emsNo) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("料件序号", "seqNum", 60, Integer.class));
		list.add(new JTableListColumn("手册编号", "contract.emsNo", 100));
		list.add(new JTableListColumn("商品编码", "complex.code", 80));
		list.add(new JTableListColumn("商品名称", "name", 150));
		list.add(new JTableListColumn("规格型号", "spec", 100));
		list.add(new JTableListColumn("计量单位", "unit.name", 80));
		list.add(new JTableListColumn("单价", "declarePrice", 100));
		list.add(new JTableListColumn("数量", "amount", 100));

		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(200);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				FptManageAction fptManageAction = (FptManageAction) CommonVars
						.getApplicationContext().getBean("fptManageAction");
				return fptManageAction.findBcsContractDetailByProcessExe(
						new Request(CommonVars.getCurrUser(), true), parentId,
						emsNo, FptInOutFlag.IN, index, length, property, value,
						isLike);
			}
		};
		dgCommonQuery.setTitle("获得正在执行的合同的料件");
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 获得正在执行的合同的成品 (bcs)
	 */
	@SuppressWarnings("unchecked")
	public List<ContractExg> findContractExgByProcessExe(final String parentId,
			final String emsNo) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("成品序号", "seqNum", 60, Integer.class));
		list.add(new JTableListColumn("手册编号", "contract.emsNo", 100));
		list.add(new JTableListColumn("商品编码", "complex.code", 80));
		list.add(new JTableListColumn("商品名称", "name", 150));
		list.add(new JTableListColumn("规格型号", "spec", 100));
		list.add(new JTableListColumn("出口数量", "exportAmount", 100));
		list.add(new JTableListColumn("单位", "unit.name", 80));
		list.add(new JTableListColumn("单价", "unitPrice", 100));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(200);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				FptManageAction fptManageAction = (FptManageAction) CommonVars
						.getApplicationContext().getBean("fptManageAction");
				return fptManageAction.findBcsContractDetailByProcessExe(
						new Request(CommonVars.getCurrUser(), true), parentId,
						emsNo, FptInOutFlag.OUT, index, length, property,
						value, isLike);
			}
		};
		dgCommonQuery.setTitle("获得正在执行的合同的成品");
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/** 获得正在执行的电子手册通关备案里的料件 */
	public List<DzscEmsImgBill> findDzscEmsImgBillByProcessExe(
			final String parentId, final String emsNo) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("备案序号", "seqNum", 60, Integer.class));
		list.add(new JTableListColumn("手册编号", "dzscEmsPorHead.emsNo", 100));
		list.add(new JTableListColumn("商品编码", "complex.code", 80));
		list.add(new JTableListColumn("商品名称", "name", 150));
		list.add(new JTableListColumn("型号规格", "spec", 100));
		list.add(new JTableListColumn("单位", "unit.name", 40));
		list.add(new JTableListColumn("单价", "price", 70));
		list.add(new JTableListColumn("出口数量", "amount", 80));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(200);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				FptManageAction fptManageAction = (FptManageAction) CommonVars
						.getApplicationContext().getBean("fptManageAction");
				return fptManageAction.findDzscEmsPorHeadDetailByProcessExe(
						new Request(CommonVars.getCurrUser(), true), parentId,
						emsNo, FptInOutFlag.IN, index, length, property, value,
						isLike);
			}
		};
		dgCommonQuery.setTitle("获得正在执行的电子手册通关备案里的料件");

		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/** 获得正在执行的电子手册通关备案里的成品 */
	public List<DzscEmsExgBill> findDzscEmsExgBillByProcessExe(
			final String parentId, final String emsNo) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("备案序号", "seqNum", 60, Integer.class));
		list.add(new JTableListColumn("手册编号", "dzscEmsPorHead.emsNo", 100));
		list.add(new JTableListColumn("商品编码", "complex.code", 80));
		list.add(new JTableListColumn("商品名称", "name", 150));
		list.add(new JTableListColumn("型号规格", "spec", 100));
		list.add(new JTableListColumn("单位", "unit.name", 40));
		list.add(new JTableListColumn("单价", "price", 70));
		list.add(new JTableListColumn("出口数量", "amount", 80));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(200);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				FptManageAction fptManageAction = (FptManageAction) CommonVars
						.getApplicationContext().getBean("fptManageAction");
				return fptManageAction.findDzscEmsPorHeadDetailByProcessExe(
						new Request(CommonVars.getCurrUser(), true), parentId,
						emsNo, FptInOutFlag.OUT, index, length, property,
						value, isLike);
			}
		};
		dgCommonQuery.setTitle("获得正在执行的电子手册通关备案里的成品");

		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/** 获得正在执行的电子帐册里的料件 (Bcus) */
	public List<EmsHeadH2kImg> findEmsHeadH2kImgByProcessExe(
			final String parentId, final String emsNo) {

		List<JTableListColumn> list = new Vector<JTableListColumn>();

		list.add(new JTableListColumn("归并序号", "seqNum", 60, Integer.class));

		list.add(new JTableListColumn("帐册编号", "emsHeadH2k.emsNo", 100));

		list.add(new JTableListColumn("商品编码", "complex.code", 80));

		list.add(new JTableListColumn("商品名称", "name", 100));

		list.add(new JTableListColumn("型号规格", "spec", 100));

		list.add(new JTableListColumn("申报数量", "qty", 100));

		list.add(new JTableListColumn("计量单位", "unit.name", 80));

		list.add(new JTableListColumn("币制", "curr.name", 60));

		list.add(new JTableListColumn("企业申报单价", "declarePrice", 80));

		DgCommonQueryPage.setTableColumns(list);

		// DgCommonQueryPage.setLength(200);

		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {

			// 内部类实现抽象方法
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				FptManageAction fptManageAction = (FptManageAction) CommonVars
						.getApplicationContext().getBean("fptManageAction");

				return fptManageAction.findBcusEms2kDetailByProcessExe(
						new Request(CommonVars.getCurrUser(), true), parentId,
						emsNo, FptInOutFlag.IN, index, length, property, value,
						isLike);

			}

		};

		dgCommonQuery.setTitle("获得正在执行的电子帐册里的料件");

		DgCommonQueryPage.setSingleResult(false);

		dgCommonQuery.setVisible(true);

		if (dgCommonQuery.isOk()) {

			return dgCommonQuery.getReturnList();

		}
		return null;
	}

	/** 获得正在执行的电子帐册里的成品 (Bcus) */
	public List<EmsHeadH2kExg> findEmsHeadH2kExgByProcessExe(
			final String parentId, final String emsNo) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("归并序号", "seqNum", 60, Integer.class));
		list.add(new JTableListColumn("帐册编号", "emsHeadH2k.emsNo", 100));
		list.add(new JTableListColumn("商品编码", "complex.code", 80));
		list.add(new JTableListColumn("商品名称", "name", 100));
		list.add(new JTableListColumn("型号规格", "spec", 100));
		list.add(new JTableListColumn("申报数量", "qty", 100));
		list.add(new JTableListColumn("计量单位", "unit.name", 80));
		list.add(new JTableListColumn("币制", "curr.name", 60));
		list.add(new JTableListColumn("企业申报单价", "declarePrice", 80));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(200);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				FptManageAction fptManageAction = (FptManageAction) CommonVars
						.getApplicationContext().getBean("fptManageAction");
				return fptManageAction.findBcusEms2kDetailByProcessExe(
						new Request(CommonVars.getCurrUser(), true), parentId,
						emsNo, FptInOutFlag.OUT, index, length, property,
						value, isLike);
			}
		};
		dgCommonQuery.setTitle("获得正在执行的电子帐册里的成品");

		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	// /**
	// * 查询作废的发货单或收退货单
	// *
	// * @return
	// */
	// public List findFptBillHeadForCancel(String fptBusinessType, String
	// fptInOutFlag) {
	// List<JTableListColumn> list = new Vector<JTableListColumn>();
	// list.add(new JTableListColumn("收发货企业内部", "copNo", 120));
	// list.add(new JTableListColumn("申报状态", "appState", 60));
	// list.add(new JTableListColumn("收发货单编号", "billNo", 100));
	// list.add(new JTableListColumn("统一编号", "seqNo", 100));
	// list.add(new JTableListColumn("申请表编号", "appNo", 80));
	// list.add(new JTableListColumn("转出企业手册/账册号", "outEmsNo", 100));
	// list.add(new JTableListColumn("单据类型", "sysType", 60));
	// list.add(new JTableListColumn("转出标志", "billSort", 60));
	// list.add(new JTableListColumn("申报时间", "issueIsDeclaDate", 80));
	// DgCommonQuery.setTableColumns(list);
	// List dataSource = fptManageAction.findFptBillHeadForCancel(new Request(
	// CommonVars.getCurrUser()),fptBusinessType, fptInOutFlag);
	// final DgCommonQuery dgCommonQuery = new DgCommonQuery();
	// dgCommonQuery.setDataSource(dataSource);
	// dgCommonQuery.setTitle("结转单据");
	// dgCommonQuery.setLike(false);
	// DgCommonQuery.setSingleResult(false);
	// dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
	// public void windowOpened(java.awt.event.WindowEvent e) {
	// JTable tb = dgCommonQuery.getJTable();
	// tb.getColumnModel().getColumn(2).setCellRenderer(
	// new DefaultTableCellRenderer() {
	// public Component getTableCellRendererComponent(
	// JTable table, Object value,
	// boolean isSelected, boolean hasFocus,
	// int row, int column) {
	// super.getTableCellRendererComponent(table,
	// value, isSelected, hasFocus, row,
	// column);
	// super.setText((value == null) ? ""
	// : castValue(value));
	// return this;
	// }
	//
	// private String castValue(Object value) {
	// return DeclareState.getDeclareStateSpec(value
	// .toString());
	// }
	// });
	// tb.getColumnModel().getColumn(7).setCellRenderer(
	// new DefaultTableCellRenderer() {
	// public Component getTableCellRendererComponent(
	// JTable table, Object value,
	// boolean isSelected, boolean hasFocus,
	// int row, int column) {
	// super.getTableCellRendererComponent(table,
	// value, isSelected, hasFocus, row,
	// column);
	// super.setText((value == null) ? ""
	// : castValue(value));
	// return this;
	// }
	//
	// private String castValue(Object value) {
	// return FptBusinessType
	// .getFptBusinessTypeDesc(value
	// .toString());
	// }
	// });
	// tb.getColumnModel().getColumn(8).setCellRenderer(
	// new DefaultTableCellRenderer() {
	// public Component getTableCellRendererComponent(
	// JTable table, Object value,
	// boolean isSelected, boolean hasFocus,
	// int row, int column) {
	// super.getTableCellRendererComponent(table,
	// value, isSelected, hasFocus, row,
	// column);
	// super.setText((value == null) ? ""
	// : castValue(value));
	// return this;
	// }
	//
	// private String castValue(Object value) {
	// return FptInOutFlag.getNote(value.toString());
	// }
	// });
	// tb.repaint();
	// }
	// });
	// dgCommonQuery.setVisible(true);
	// if (dgCommonQuery.isOk()) {
	// return dgCommonQuery.getReturnList();
	// }
	// return null;
	// }
	//

	/**
	 * 查询作废的发货单或收退货单
	 * 
	 * @return
	 */
	public List findFptBillHeadForCancel(String fptBusinessType,
			String fptInOutFlag) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("收发货企业内部", "copNo", 120));
		list.add(new JTableListColumn("申报状态", "appState", 60));
		list.add(new JTableListColumn("收发货单编号", "billNo", 100));
		list.add(new JTableListColumn("统一编号", "seqNo", 100));
		list.add(new JTableListColumn("申请表编号", "appNo", 80));
		list.add(new JTableListColumn("转出企业手册/账册号", "outEmsNo", 100));
		list.add(new JTableListColumn("单据类型", "sysType", 60));
		list.add(new JTableListColumn("转出标志", "billSort", 60));
		list.add(new JTableListColumn("申报时间", "issueIsDeclaDate", 80));
		DgCommonQuery.setTableColumns(list);
		List dataSource = fptManageAction.findFptBillHeadForCancel(new Request(
				CommonVars.getCurrUser()), fptBusinessType, fptInOutFlag);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("结转单据");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				JTable tb = dgCommonQuery.getJTable();
				tb.getColumnModel().getColumn(2)
						.setCellRenderer(new DefaultTableCellRenderer() {
							public Component getTableCellRendererComponent(
									JTable table, Object value,
									boolean isSelected, boolean hasFocus,
									int row, int column) {
								super.getTableCellRendererComponent(table,
										value, isSelected, hasFocus, row,
										column);
								super.setText((value == null) ? ""
										: castValue(value));
								return this;
							}

							private String castValue(Object value) {
								return DeclareState.getDeclareStateSpec(value
										.toString());
							}
						});
				tb.getColumnModel().getColumn(7)
						.setCellRenderer(new DefaultTableCellRenderer() {
							public Component getTableCellRendererComponent(
									JTable table, Object value,
									boolean isSelected, boolean hasFocus,
									int row, int column) {
								super.getTableCellRendererComponent(table,
										value, isSelected, hasFocus, row,
										column);
								super.setText((value == null) ? ""
										: castValue(value));
								return this;
							}

							private String castValue(Object value) {
								return FptBusinessType
										.getFptBusinessTypeDesc(value
												.toString());
							}
						});
				tb.getColumnModel().getColumn(8)
						.setCellRenderer(new DefaultTableCellRenderer() {
							public Component getTableCellRendererComponent(
									JTable table, Object value,
									boolean isSelected, boolean hasFocus,
									int row, int column) {
								super.getTableCellRendererComponent(table,
										value, isSelected, hasFocus, row,
										column);
								super.setText((value == null) ? ""
										: castValue(value));
								return this;
							}

							private String castValue(Object value) {
								return FptInOutFlag.getNote(value.toString());
							}
						});
				tb.repaint();
			}
		});
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 查出所邮件中所需要的资料
	 * 
	 * @return
	 */
	public List findAppBillToFptEmail(String SysType, String state,
			String inoutFlag, String makeinout) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("申报状态", "appState", 60));
		list.add(new JTableListColumn("统一编号", "seqNo", 100));
		list.add(new JTableListColumn("申请表编号", "appNo", 80));
		list.add(new JTableListColumn("转出企业内部编号", "outCopAppNo", 80));
		list.add(new JTableListColumn("转出企业手册/账册号", "emsNo", 100));
		list.add(new JTableListColumn("发货单编号", "billNo", 100));
		list.add(new JTableListColumn("转出企业名称", "outName", 80));
		list.add(new JTableListColumn("转出企业编号", "outCode", 80));
		list.add(new JTableListColumn("收发类型", "sysType", 80));
		DgCommonQuery.setTableColumns(list);
		List dataSource = fptManageAction
				.findAppBillToFptEmail(new Request(CommonVars.getCurrUser()),
						SysType, state, inoutFlag, makeinout);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("结转单据");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				JTable tb = dgCommonQuery.getJTable();
				tb.getColumnModel().getColumn(1)
						.setCellRenderer(new DefaultTableCellRenderer() {
							public Component getTableCellRendererComponent(
									JTable table, Object value,
									boolean isSelected, boolean hasFocus,
									int row, int column) {
								super.getTableCellRendererComponent(table,
										value, isSelected, hasFocus, row,
										column);
								super.setText((value == null) ? ""
										: castValue(value));
								return this;
							}

							private String castValue(Object value) {
								return DeclareState.getDeclareStateSpec(value
										.toString());
							}
						});
				tb.getColumnModel().getColumn(9)
						.setCellRenderer(new DefaultTableCellRenderer() {
							public Component getTableCellRendererComponent(
									JTable table, Object value,
									boolean isSelected, boolean hasFocus,
									int row, int column) {
								super.getTableCellRendererComponent(table,
										value, isSelected, hasFocus, row,
										column);
								super.setText((value == null) ? ""
										: castValue(value));
								return this;
							}

							private String castValue(Object value) {
								return FptBusinessType
										.getFptBusinessTypeDesc(value
												.toString());
							}
						});
				tb.repaint();
			}
		});
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 获得所有关封单据
	 */
	public FptAppItem findFptAppItemsByOut(FptAppHead fptAppHead) {
		List list = new Vector();
		list.add(new JTableListColumn("序号", "listNo", 60));
		list.add(new JTableListColumn("商品项号", "trNo", 120));
		list.add(new JTableListColumn("商品编码", "codeTs.code", 120));
		list.add(new JTableListColumn("商品名称", "name", 60));
		list.add(new JTableListColumn("规格型号", "spec", 60));
		list.add(new JTableListColumn("计量单位", "unit.name", 60));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQueryEnvelop = new DgCommonQuery();

		List dataSource = fptManageAction
				.findFptAppItems(new Request(CommonVars.getCurrUser()),
						fptAppHead.getId(), FptInOutFlag.OUT);
		String title = "";
		title = "转厂申请表明细---转出";
		dgCommonQueryEnvelop.setDataSource(dataSource);
		dgCommonQueryEnvelop.setTitle(title);
		dgCommonQueryEnvelop.setLike(true);
		DgCommonQuery.setSingleResult(true);
		dgCommonQueryEnvelop.setVisible(true);
		if (dgCommonQueryEnvelop.isOk()) {
			return (FptAppItem) dgCommonQueryEnvelop.getReturnValue();
		}
		return null;
	}

	/**
	 * 查出所邮件中所需要的资料
	 * 
	 * @return
	 */
	public FptBillHead findFptBillHead(String casBillCode, String declareState,
			ScmCoc scmCoc, String appNo) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("客户/供应商", "customer.name", 90));
		list.add(new JTableListColumn("收货企业内部", "copNo", 180));
		list.add(new JTableListColumn("申报状态", "appState", 60));
		list.add(new JTableListColumn("收发退货单编号", "billNo", 100));
		list.add(new JTableListColumn("统一编号", "seqNo", 100));
		list.add(new JTableListColumn("申请表编号", "appNo", 80));
		list.add(new JTableListColumn("单据类型", "sysType", 60));
		list.add(new JTableListColumn("转入标志", "billSort", 60));
		list.add(new JTableListColumn("录入时间", "createDate", 60));
		list.add(new JTableListColumn("录入人员", "aclUser.userName", 80));
		list.add(new JTableListColumn("备注", "note", 100));
		DgCommonQuery.setTableColumns(list);
		// 转入转出标志
		// boolean isImport = false;
		// 收发货，退货标志
		// int sendFlag = 0;
		String sysType = null;
		String inOutFlag = null;
		if ("1004".equals(casBillCode)) {// 结转进口
			// isImport = true;
			// sendFlag = 0;
			sysType = FptBusinessType.FPT_BILL;
			inOutFlag = FptInOutFlag.IN;
		} else if ("1106".equals(casBillCode)) {// 结转料件退货单
			// isImport = true;
			// sendFlag = 1;
			sysType = FptBusinessType.FPT_BILL_BACK;
			inOutFlag = FptInOutFlag.IN;
		} else if ("2102".equals(casBillCode)) {// 结转出口
			// isImport = false;
			// sendFlag = 0;
			sysType = FptBusinessType.FPT_BILL;
			inOutFlag = FptInOutFlag.OUT;
		} else if ("2009".equals(casBillCode)) {// 结转成品退货单
			sysType = FptBusinessType.FPT_BILL_BACK;
			inOutFlag = FptInOutFlag.OUT;
			// isImport = false;
			// sendFlag = 1;
		}
		List dataSource = fptManageAction.findFptBillHead(new Request(
				CommonVars.getCurrUser()), sysType, inOutFlag, null, null,
				declareState, appNo, scmCoc);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("结转单据");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				JTable tb = dgCommonQuery.getJTable();
				tb.getColumnModel().getColumn(3)
						.setCellRenderer(new DefaultTableCellRenderer() {
							public Component getTableCellRendererComponent(
									JTable table, Object value,
									boolean isSelected, boolean hasFocus,
									int row, int column) {
								super.getTableCellRendererComponent(table,
										value, isSelected, hasFocus, row,
										column);
								super.setText((value == null) ? ""
										: castValue(value));
								return this;
							}

							private String castValue(Object value) {
								return DeclareState.getDeclareStateSpec(value
										.toString());
							}
						});
				tb.getColumnModel().getColumn(7)
						.setCellRenderer(new DefaultTableCellRenderer() {
							public Component getTableCellRendererComponent(
									JTable table, Object value,
									boolean isSelected, boolean hasFocus,
									int row, int column) {
								super.getTableCellRendererComponent(table,
										value, isSelected, hasFocus, row,
										column);
								super.setText((value == null) ? ""
										: FptBusinessType
												.getFptBusinessTypeDesc(value
														.toString()));
								return this;
							}
						});
				tb.getColumnModel().getColumn(8)
						.setCellRenderer(new DefaultTableCellRenderer() {
							public Component getTableCellRendererComponent(
									JTable table, Object value,
									boolean isSelected, boolean hasFocus,
									int row, int column) {
								super.getTableCellRendererComponent(table,
										value, isSelected, hasFocus, row,
										column);
								super.setText((value == null) ? ""
										: FptInOutFlag.getInOutFlagSpec(value
												.toString()));
								return this;
							}
						});
				tb.repaint();
			}
		});
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return (FptBillHead) dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 获得所有关封单据
	 */
	public List getCustomsComplex(ScmCoc scmCoc, Boolean isCustomer) {
		List list = new Vector();
		list.add(new JTableListColumn("商品编码", "code", 150));
		list.add(new JTableListColumn("商品名称", "name", 250));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQueryEnvelop = new DgCommonQuery();

		List dataSource = fptManageAction.findCustomsComplex(new Request(
				CommonVars.getCurrUser()), scmCoc);
		dgCommonQueryEnvelop.setDataSource(dataSource);
		dgCommonQueryEnvelop.setTitle("请选商品");
		dgCommonQueryEnvelop.setLike(true);
		DgCommonQuery.setSingleResult(false);
		dgCommonQueryEnvelop.setVisible(true);
		if (dgCommonQueryEnvelop.isOk()) {
			return dgCommonQueryEnvelop.getReturnList();
		}
		return new ArrayList();
	}

	/**
	 * 判断海关申报或者回执处理，是否在测试环境中进行
	 */
	public boolean isHttpUpload() {
		List list = systemAction.findCompanyOther(new Request(CommonVars
				.getCurrUser(), true));
		if (!list.isEmpty()) {
			CompanyOther p = (CompanyOther) list.get(0);
			if (p.getHttpAddress() != null
					&& p.getHttpAddress().indexOf("demo") >= 0) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}
}
