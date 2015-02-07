package com.bestway.common.client.transferfactory;

import java.awt.Component;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.cas.invoice.entity.CustomsEnvelopCommodityInfoEntity;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.bcus.client.common.DgCommonQueryPage;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.client.util.DataType;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.Condition;
import com.bestway.common.Request;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.transferfactory.action.TransferFactoryManageAction;
import com.bestway.common.transferfactory.entity.CustomsEnvelopBill;
import com.bestway.common.transferfactory.entity.CustomsEnvelopCommodityInfo;
import com.bestway.dzsc.contractstat.action.DzscStatAction;
import com.bestway.dzsc.customslist.action.DzscListAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class TransferFactoryQuery {
	private static TransferFactoryQuery transferFactoryQuery = null;

	private static TransferFactoryManageAction transferFactoryManageAction = null;

	public static MaterialManageAction materialManageAction = null;
	private static DzscStatAction contractStatAction = null;

	public static TransferFactoryQuery getInstance() {
		if (transferFactoryQuery == null) {
			transferFactoryQuery = new TransferFactoryQuery();
			transferFactoryManageAction = (TransferFactoryManageAction) CommonVars
					.getApplicationContext().getBean(
							"transferFactoryManageAction");
			materialManageAction = (MaterialManageAction) CommonVars
					.getApplicationContext().getBean("materialManageAction");
			contractStatAction = (DzscStatAction) CommonVars
					.getApplicationContext().getBean("dzscStatAction");
		}
		return transferFactoryQuery;

	}

	public Object findCustomsEnvelopComplex(boolean isImport, boolean isSeqNum) {
		List dataSource = transferFactoryManageAction
				.findCustomsEnvelopComplex(new Request(
						CommonVars.getCurrUser(), true), isImport, isSeqNum);
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		if (isSeqNum) {
			list.add(new JTableListColumn("备案序号", "seqNum", 100));
		}
		list.add(new JTableListColumn("商品编码", "code", 100));
		list.add(new JTableListColumn("商品名称", "name", 100));
		list.add(new JTableListColumn("规格型号", "spec", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("报关商品资料");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 抓取出口报关单资料
	 * 
	 * @param projectType
	 * @return
	 */
	public Object findExportCustomsDeclaration(
			CustomsEnvelopBill customsEnvelopBill) {
		// List dataSource =
		// transferFactoryManageAction.findExportCustomsDeclaration(
		// new Request(CommonVars.getCurrUser()), customsEnvelopBill);
		List dataSource = transferFactoryManageAction.findCustomsEnvelopBill(
				new Request(CommonVars.getCurrUser()), false, null, null, null,
				null, null);
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("报关单号码", "customsDeclarationCode", 100));
		list.add(new JTableListColumn("金额", "money", 100));
		list.add(new JTableListColumn("进出口类型", "impExpType", 100));
		list.add(new JTableListColumn("报关单来源", "projectType", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("报关单资料");
		dgCommonQuery.setLike(false);
		dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				JTable table = dgCommonQuery.getJTable();
				table.getColumnModel().getColumn(3)
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
									Integer projectType = Integer.valueOf(value
											.toString());
									switch (projectType) {
									case ProjectType.BCUS:
										str = "BCUS";
										break;
									case ProjectType.BCS:
										str = "BCS";
										break;
									}
								}
								this.setText(str);
								return this;
							}
						});
			}
		});
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 抓取出口报关单资料
	 * 
	 * @param projectType
	 * @return
	 */
	public Object findImportCustomsDeclaration(
			CustomsEnvelopBill customsEnvelopBill) {
		// List dataSource =
		// transferFactoryManageAction.findExportCustomsDeclaration(
		// new Request(CommonVars.getCurrUser()), customsEnvelopBill);
		List dataSource = transferFactoryManageAction.findCustomsEnvelopBill(
				new Request(CommonVars.getCurrUser()), false, null, null, null,
				null, null);
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("报关单号码", "customsDeclarationCode", 100));
		list.add(new JTableListColumn("金额", "money", 100));
		list.add(new JTableListColumn("进出口类型", "impExpType", 100));
		list.add(new JTableListColumn("报关单来源", "projectType", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("报关单资料");
		dgCommonQuery.setLike(false);
		dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				JTable table = dgCommonQuery.getJTable();
				table.getColumnModel().getColumn(3)
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
									Integer projectType = Integer.valueOf(value
											.toString());
									switch (projectType) {
									case ProjectType.BCUS:
										str = "BCUS";
										break;
									case ProjectType.BCS:
										str = "BCS";
										break;
									}
								}
								this.setText(str);
								return this;
							}
						});
			}
		});
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 老方法 抓取报关单号为空的涉及转厂的关封资料
	 * 
	 * @param
	 * @return
	 * @author 石小凯
	 */
	public Object findAllEnvelopBill() {
		List dataSource = transferFactoryManageAction
				.findCustomsEnvelopBill(new Request(CommonVars.getCurrUser(),
						true));
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("关封号", "customsEnvelopBillNo", 150));
		list.add(new JTableListColumn("生效日期", "beginAvailability", 100));
		list.add(new JTableListColumn("供应商名称", "scmCoc.name", 150));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("关封资料");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 找出所有需要的关封单据
	 * 
	 * @param isImport
	 * @return
	 * @author 石小凯
	 */
	public List<CustomsEnvelopCommodityInfoEntity> findAllBill(boolean isImport) {
		String inoutType = "";
		if (isImport) {
			inoutType = "1";
		} else {
			inoutType = "0";
		}
		// 组织查询条件 :关封管理
		List<Condition> conditions = new ArrayList<Condition>();
		// 组织查询条件:转厂申请表
		List<Condition> conditions1 = new ArrayList<Condition>();

		conditions.add(new Condition("and", null,
				"customsEnvelopBill.isImpExpGoods", "=", isImport, null));

		conditions1.add(new Condition("and", null, "fptAppHead.inOutFlag", "=",
				inoutType, null));

		conditions1.add(new Condition("and", null, "fptAppHead.declareState",
				"=", "3", null));

		List<CustomsEnvelopCommodityInfoEntity> dataSource = null;

		if (isImport) {
			dataSource = transferFactoryManageAction.getEnvelopBillDetail(
					new Request(CommonVars.getCurrUser()), conditions,
					conditions1);
		} else {
			dataSource = transferFactoryManageAction.getEnvelopBillDetailout(
					new Request(CommonVars.getCurrUser()), conditions,
					conditions1);
		}
		return dataSource;
	}

	/**
	 * 抓取关封号
	 * 
	 * @param
	 * @return
	 * @author 石小凯
	 */
	public Object findAllEnvelopBillNew(boolean isImport) {
		List<CustomsEnvelopCommodityInfoEntity> dataSource = findAllBill(isImport);

		Map<String, CustomsEnvelopCommodityInfoEntity> Map = new HashMap<String, CustomsEnvelopCommodityInfoEntity>();

		// 去掉重复的关封号
		List<CustomsEnvelopCommodityInfoEntity> dataSource2 = new ArrayList();
		for (CustomsEnvelopCommodityInfoEntity cecie : dataSource) {
			CustomsEnvelopCommodityInfoEntity c = null;
			c = Map.get(cecie.getCustomsEnvelopBillNo());
			if (c == null && cecie.getCustomsEnvelopBillNo() != null) {
				Map.put(cecie.getCustomsEnvelopBillNo(), cecie);
				dataSource2.add(cecie);
			}
		}
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("关封号", "customsEnvelopBillNo", 150));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource2);
		dgCommonQuery.setTitle("关封资料");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	public Object findMaterialImportListNew(final boolean isImport,
			final List<DzscEmsPorHead> contracts) {
		// List<DzscCustomsDeclarationCommInfo> lists = new ArrayList();
		// //Integer seqNum,String ptName,
		// int seqNum=0;
		// String ptName="";
		// List<DzscCustomsDeclarationCommInfo> list=new ArrayList();
		// for (DzscEmsPorHead deph:contracts){
		//
		// list = contractStatAction
		// .findCustomsDeclarationCommInfoNew(
		// new Request(CommonVars.getCurrUser()), isImport,deph);
		// lists.addAll(list);
		// }
		// System.out.println("DzscCustomsDeclarationCommInfo.size()="+lists.size());
		// Map<String, DzscCustomsDeclarationCommInfo> Map = new HashMap<String,
		// DzscCustomsDeclarationCommInfo>();
		//
		// // 去掉重复的关封号
		// List<DzscCustomsDeclarationCommInfo> dataSource2=new ArrayList();
		// for (DzscCustomsDeclarationCommInfo cecie:lists){
		// DzscCustomsDeclarationCommInfo c=null;
		// c = Map.get(cecie.getCommName());
		// if(c==null&&cecie.getCommName()!=null){
		// Map.put(cecie.getCommName(), cecie);
		// dataSource2.add(cecie);
		// }
		// }
		// System.out.println("dataSource2.size="+dataSource2.size());
		// List<JTableListColumn> l = new Vector<JTableListColumn>();
		// l.add(new JTableListColumn("关封号", "commName", 150));
		// DgCommonQuery.setTableColumns(l);
		// final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		// dgCommonQuery.setDataSource(dataSource2);
		// dgCommonQuery.setTitle("关封资料");
		// dgCommonQuery.setLike(false);
		// DgCommonQuery.setSingleResult(true);
		// dgCommonQuery.setVisible(true);
		// if (dgCommonQuery.isOk()) {
		// return dgCommonQuery.getReturnValue();
		// }
		// return null;
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("商品名称", "object", "a.commName",
				DataType.STRING, 250));

		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				List<TempObject> lists = new ArrayList();
				// Integer seqNum,String ptName,
				int seqNum = 0;
				String ptName = "";
				List<TempObject> list = new ArrayList();
				for (DzscEmsPorHead deph : contracts) {

					list = contractStatAction
							.findCustomsDeclarationCommInfoNew(new Request(
									CommonVars.getCurrUser()), isImport, deph);
					lists.addAll(list);
				}
				Map<Object, TempObject> Map = new HashMap<Object, TempObject>();

				// 去掉重复的关封号
				List<TempObject> dataSource2 = new ArrayList();
				for (TempObject cecie : lists) {
					TempObject c = null;
					c = Map.get(cecie.getObject());
					if (c == null && cecie.getObject() != null) {
						Map.put(cecie.getObject(), cecie);
						dataSource2.add(cecie);
					}
				}
				return dataSource2;
			}
		};
		dgCommonQuery.setTitle("选择【商品名称】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}

	/**
	 * 抓取合同号
	 * 
	 * @param
	 * @return
	 * @author 石小凯
	 */
	public Object findEnvelopBillByContractNoNew(boolean isImport) {
		List<CustomsEnvelopCommodityInfoEntity> dataSource = findAllBill(isImport);

		Map<String, CustomsEnvelopCommodityInfoEntity> Map = new HashMap<String, CustomsEnvelopCommodityInfoEntity>();

		// 去掉重复的合同号
		List<CustomsEnvelopCommodityInfoEntity> dataSource2 = new ArrayList();
		for (CustomsEnvelopCommodityInfoEntity cecie : dataSource) {
			CustomsEnvelopCommodityInfoEntity c = null;
			c = Map.get(cecie.getPurchaseAndSaleContractNo());
			if (c == null && null != cecie.getPurchaseAndSaleContractNo()
					&& !"".equals(cecie.getPurchaseAndSaleContractNo())) {
				Map.put(cecie.getPurchaseAndSaleContractNo(), cecie);
				dataSource2.add(cecie);
			}
		}
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("合同号", "purchaseAndSaleContractNo", 150));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource2);
		dgCommonQuery.setTitle("关封资料");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 抓取报关单号
	 * 
	 * @param isImport
	 * @return
	 * @author 石小凯
	 */
	public Object findAllEnvelopBillByBillNoNew(boolean isImport) {
		List<CustomsEnvelopCommodityInfoEntity> dataSource = findAllBill(isImport);

		Map<String, CustomsEnvelopCommodityInfoEntity> Map = new HashMap<String, CustomsEnvelopCommodityInfoEntity>();

		// 去掉重复的报关单号
		List<CustomsEnvelopCommodityInfoEntity> dataSource2 = new ArrayList();
		for (CustomsEnvelopCommodityInfoEntity cecie : dataSource) {
			CustomsEnvelopCommodityInfoEntity c = null;
			c = Map.get(cecie.getCarryForwardApplyToCustomsBillNo());
			if (c == null
					&& null != cecie.getCarryForwardApplyToCustomsBillNo()
					&& !"".equals(cecie.getCarryForwardApplyToCustomsBillNo())) {
				Map.put(cecie.getCarryForwardApplyToCustomsBillNo(), cecie);
				dataSource2.add(cecie);
			}
		}
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("报关单号",
				"carryForwardApplyToCustomsBillNo", 150));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource2);
		dgCommonQuery.setTitle("关封资料");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 抓取商品名称
	 * 
	 * @param isImport
	 * @return
	 * @author 石小凯
	 */
	public Object findAllEnvelopShangpinNew(boolean isImport) {
		List<CustomsEnvelopCommodityInfoEntity> dataSource = findAllBill(isImport);

		Map<String, CustomsEnvelopCommodityInfoEntity> Map = new HashMap<String, CustomsEnvelopCommodityInfoEntity>();

		// 去掉重复的商品名称
		List<CustomsEnvelopCommodityInfoEntity> dataSource2 = new ArrayList();
		for (CustomsEnvelopCommodityInfoEntity cecie : dataSource) {
			CustomsEnvelopCommodityInfoEntity c = null;
			c = Map.get(cecie.getPtName());
			if (c == null && null != cecie.getPtName()
					&& !"".equals(cecie.getPtName())) {
				Map.put(cecie.getPtName(), cecie);
				dataSource2.add(cecie);
			}
		}
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("商品名称", "ptName", 150));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource2);
		dgCommonQuery.setTitle("关封资料");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 抓取商品规格
	 * 
	 * @param isImport
	 * @return
	 * @author 石小凯
	 */
	public Object findAllEnvelopSpec(boolean isImport) {
		List<CustomsEnvelopCommodityInfoEntity> dataSource = findAllBill(isImport);

		Map<String, CustomsEnvelopCommodityInfoEntity> Map = new HashMap<String, CustomsEnvelopCommodityInfoEntity>();

		// 去掉重复的商品名称
		List<CustomsEnvelopCommodityInfoEntity> dataSource2 = new ArrayList();
		for (CustomsEnvelopCommodityInfoEntity cecie : dataSource) {
			CustomsEnvelopCommodityInfoEntity c = null;
			c = Map.get(cecie.getPtSpec());
			if (c == null && null != cecie.getPtSpec()
					&& !"".equals(cecie.getPtSpec())) {
				Map.put(cecie.getPtSpec(), cecie);
				dataSource2.add(cecie);
			}
		}
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("商品规格", "ptSpec", 150));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource2);
		dgCommonQuery.setTitle("关封资料");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 老方法 抓取报关单号为空的涉及转厂的关封资料
	 * 
	 * @param
	 * @return
	 * @author 石小凯
	 */
	public Object findAllEnvelopBillByContractNo() {
		List dataSource = transferFactoryManageAction
				.findCustomsEnvelopBill(new Request(CommonVars.getCurrUser(),
						true));
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("合同号", "purchaseAndSaleContractNo", 150));
		list.add(new JTableListColumn("生效日期", "beginAvailability", 100));
		list.add(new JTableListColumn("供应商名称", "scmCoc.name", 150));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("关封资料");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 老方法 抓取关封商品
	 * 
	 * @param
	 * @return
	 * @author 石小凯
	 */
	public Object findAllEnvelopShangpin() {
		List dataSource = transferFactoryManageAction
				.findCustomsEnvelopCommodityInfo(new Request(CommonVars
						.getCurrUser(), true));
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("商品名称", "ptName", 150));
		list.add(new JTableListColumn("商品规格", "ptSpec", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("关封商品资料");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 抓取报关单号为空的涉及转厂的关封资料
	 * 
	 * @param
	 * @return
	 * @author 石小凯
	 */
	public Object findAllEnvelopBillByBillNo() {
		List dataSource = transferFactoryManageAction
				.findCustomsEnvelopBill(new Request(CommonVars.getCurrUser(),
						true));
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("报关单号",
				"carryForwardApplyToCustomsBillNo", 150));
		list.add(new JTableListColumn("生效日期", "beginAvailability", 100));
		list.add(new JTableListColumn("供应商名称", "scmCoc.name", 150));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("关封资料");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 抓取报关单号为空的涉及转厂的关封资料
	 * 
	 * @param
	 * @return
	 */
	public Object findCustomsEnvelopBill(boolean isImport,
			boolean isAvailability, ScmCoc scmCoc) {
		String scmCocName = (isImport ? "供应商名称" : "客户名称");
		List dataSource = transferFactoryManageAction.findCustomsEnvelopBill(
				new Request(CommonVars.getCurrUser(), true), isImport,
				isAvailability, scmCoc);
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("关封号", "customsEnvelopBillNo", 150));
		list.add(new JTableListColumn("生效日期", "beginAvailability", 100));
		list.add(new JTableListColumn(scmCocName, "scmCoc.name", 150));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("关封资料");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 抓取报关单号为空的涉及转厂的关封资料
	 * 
	 * @param
	 * @return
	 */
	public Object findCustomsEnvelopCommodityInfo(boolean isImport,
			boolean isAvailability, String emsNo, ScmCoc scmCoc) {
		String scmCocName = (isImport ? "供应商名称" : "客户名称");
		List dataSource = transferFactoryManageAction
				.findCustomsEnvelopCommodityInfo(
						new Request(CommonVars.getCurrUser()), isImport,
						isAvailability, emsNo, scmCoc);
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("关封号",
				"customsEnvelopBill.customsEnvelopBillNo", 100));
		list.add(new JTableListColumn("生效日期",
				"customsEnvelopBill.beginAvailability", 100));
		list.add(new JTableListColumn(scmCocName,
				"customsEnvelopBill.scmCoc.name", 100));
		list.add(new JTableListColumn("商品名称", "ptName", 100));
		list.add(new JTableListColumn("商品规格", "ptSpec", 100));
		list.add(new JTableListColumn("关封数量", "ownerQuantity", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("关封资料");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 取得没有在关封中的商品资料
	 * 
	 * @param projectType
	 * @param emsNo
	 * @param envelopBillId
	 * @param isMaterial
	 * @return
	 */
	public List findTempCustomsEnvelopCommInfo(int projectType, String emsNo,
			CustomsEnvelopBill customsEnvelopBill, boolean isMaterial) {
		List dataSource = transferFactoryManageAction
				.findTempCustomsEnvelopRequestCommInfo(
						new Request(CommonVars.getCurrUser(), true),
						projectType, emsNo, customsEnvelopBill, isMaterial);
		List<JTableListColumn> list = new Vector<JTableListColumn>();
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
		list.add(new JTableListColumn(str + "号", "emsNo", 100));
		list.add(new JTableListColumn(str + "序号", "seqNum", 100));
		list.add(new JTableListColumn("海关商品编码", "complex", 100));
		list.add(new JTableListColumn("商品名称", "ptName", 100));
		list.add(new JTableListColumn("商品规格", "ptSpec", 100));
		list.add(new JTableListColumn("单位", "unit.name", 100));
		list.add(new JTableListColumn("币制", "curr.name", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("商品资料");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return new ArrayList();
	}

	/**
	 * 查找转厂一般信息
	 * 
	 * @param isImport
	 * @param scmCoc
	 * @return
	 */
	public List findTempTransferFactoryCommInfo(boolean isImport, ScmCoc scmCoc) {

		// 查找转厂信息数据源
		List dataSource = transferFactoryManageAction
				.findTempTransferFactoryCommInfo(
						new Request(CommonVars.getCurrUser(), true), isImport,
						scmCoc);

		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("关封号",
				"customsEnvelopBill.customsEnvelopBillNo", 70));
		list.add(new JTableListColumn("生效日期",
				"customsEnvelopBill.beginAvailability", 80));
		list.add(new JTableListColumn("关封序号", "ceseqNum", 80));
		list.add(new JTableListColumn("海关商品编码", "complex.code", 80));
		list.add(new JTableListColumn("商品名称", "ptName", 120));
		list.add(new JTableListColumn("规格型号", "ptSpec", 120));
		list.add(new JTableListColumn("关封余量", "currentTransferQuantity", 100));// 暂时用来代关封余量
		list.add(new JTableListColumn("帐册/手册号", "emsNo", 100));
		list.add(new JTableListColumn("帐册/手册序号", "seqNum", 90));
		list.add(new JTableListColumn("单位", "unit.name", 60));
		list.add(new JTableListColumn("币制", "curr.name", 60));
		list.add(new JTableListColumn("供应商名称",
				"customsEnvelopBill.scmCoc.name", 150));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("关封商品资料");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return new ArrayList();
	}

	/**
	 * 查询不在转厂起初单的商品编码
	 * 
	 * @param initBillId
	 * @return
	 */
	public List findComplexNotInInitBill(String initBillId,
			String customsEnvelopBillCode) {
		List dataSource = transferFactoryManageAction.findComplexNotInInitBill(
				new Request(CommonVars.getCurrUser(), true), initBillId,
				customsEnvelopBillCode);
		List listData = checkSame(dataSource);
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("帐册/手册号", "emsNo", 100));
		list.add(new JTableListColumn("账册/手册序号", "seqNum", 50));
		list.add(new JTableListColumn("海关商品编码", "complex.code", 120));
		list.add(new JTableListColumn("商品名称", "ptName", 120));
		list.add(new JTableListColumn("单位", "unit.name", 60));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(listData);
		dgCommonQuery.setTitle("商品资料");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {

			return dgCommonQuery.getReturnList();
		}
		return new ArrayList();
	}

	/**
	 * 去掉相同的数据
	 * 
	 * @param dataSource
	 * @return
	 */
	private List checkSame(List<CustomsEnvelopCommodityInfo> dataSource) {
		List listData = new ArrayList();
		for (int i = 0; i < dataSource.size(); i++) {
			CustomsEnvelopCommodityInfo info_i = (CustomsEnvelopCommodityInfo) dataSource
					.get(i);
			for (int j = i + 1; j < dataSource.size(); j++) {
				if (j > dataSource.size())
					break;
				CustomsEnvelopCommodityInfo info_j = (CustomsEnvelopCommodityInfo) dataSource
						.get(j);
				if (info_j.getSeqNum().equals(info_i.getSeqNum())
						&& info_j.getPtName().equals(info_i.getPtName())
						&& info_j.getComplex().equals(info_i.getComplex())
						&& info_j.getUnit().equals(info_i.getUnit())) {
					dataSource.remove(j);

				}
			}
			listData.add((CustomsEnvelopCommodityInfo) dataSource.get(i));
		}
		return listData;
	}

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

	/**
	 * 获得所有关封单据
	 */
	public Object getCustomsEnvelopBill(boolean impExpGoodsFlag) {
		List list = new Vector();
		list.add(new JTableListColumn("生效", "isAvailability", 50));
		list.add(new JTableListColumn("关封号", "customsEnvelopBillNo", 100));
		list.add(new JTableListColumn("购销合同编号", "purchaseAndSaleContractNo", 80));
		list.add(new JTableListColumn("生效日期", "beginAvailability", 80));
		list.add(new JTableListColumn("有效截止日期", "endAvailability", 80));
		list.add(new JTableListColumn("客户/供应商名称", "scmCoc.name", 150));
		list.add(new JTableListColumn("转入企业帐册编号", "importEnterpriseEmsNo", 100));
		list.add(new JTableListColumn("转出企业帐册编号", "exportEnterpriseEmsNo", 100));
		list.add(new JTableListColumn("审批海关", "customs.name", 60));
		list.add(new JTableListColumn("是否结案", "isEndCase", 50));
		list.add(new JTableListColumn("结案日期", "endCaseDate", 80));
		list.add(new JTableListColumn("结转报关单号",
				"carryForwardApplyToCustomsBillNo", 80));
		list.add(new JTableListColumn("备注", "memo", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();

		TransferFactoryManageAction transferFactoryManageAction = (TransferFactoryManageAction) CommonVars
				.getApplicationContext().getBean("transferFactoryManageAction");
		List dataSource = transferFactoryManageAction.findCustomsEnvelopBill(
				new Request(CommonVars.getCurrUser(), true), impExpGoodsFlag,
				true);
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("请选择报关清单");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				JTable table = dgCommonQuery.getJTable();
				table.getColumnModel().getColumn(1)
						.setCellRenderer(new CheckBoxRenderer());
				table.getColumnModel().getColumn(10)
						.setCellRenderer(new CheckBoxRenderer());
			}
		});

		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 获得所有关封单据
	 */
	public Object getCustomsEnvelopBill() {
		List list = new Vector();
		list.add(new JTableListColumn("生效", "isAvailability", 50));
		list.add(new JTableListColumn("关封号", "customsEnvelopBillNo", 100));
		list.add(new JTableListColumn("购销合同编号", "purchaseAndSaleContractNo", 80));
		list.add(new JTableListColumn("生效日期", "beginAvailability", 80));
		list.add(new JTableListColumn("有效截止日期", "endAvailability", 80));
		list.add(new JTableListColumn("客户/供应商名称", "scmCoc.name", 150));
		list.add(new JTableListColumn("转入企业帐册编号", "importEnterpriseEmsNo", 100));
		list.add(new JTableListColumn("转出企业帐册编号", "exportEnterpriseEmsNo", 100));
		list.add(new JTableListColumn("审批海关", "customs.name", 60));
		list.add(new JTableListColumn("是否结案", "isEndCase", 50));
		list.add(new JTableListColumn("结案日期", "endCaseDate", 80));
		list.add(new JTableListColumn("结转报关单号",
				"carryForwardApplyToCustomsBillNo", 80));
		list.add(new JTableListColumn("备注", "memo", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();

		TransferFactoryManageAction transferFactoryManageAction = (TransferFactoryManageAction) CommonVars
				.getApplicationContext().getBean("transferFactoryManageAction");
		List dataSource = transferFactoryManageAction
				.findCustomsEnvelopBill(new Request(CommonVars.getCurrUser(),
						true));
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("请选择报关清单");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				JTable table = dgCommonQuery.getJTable();
				table.getColumnModel().getColumn(1)
						.setCellRenderer(new CheckBoxRenderer());
				table.getColumnModel().getColumn(10)
						.setCellRenderer(new CheckBoxRenderer());
			}
		});

		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 获得所有关封单据
	 */
	public List getCustomsEnvelopCommodityInfoByScmcoc(ScmCoc scmCoc,
			Boolean isCustomer) {
		List list = new Vector();
		list.add(new JTableListColumn("项目类型", "customsEnvelopBill.projectType",
				60));
		list.add(new JTableListColumn("备案序号", "seqNum", 50));
		list.add(new JTableListColumn("商品编码", "complex.code", 100));
		list.add(new JTableListColumn("商品名称", "ptName", 150));
		list.add(new JTableListColumn("商品规格", "ptSpec", 80));
		list.add(new JTableListColumn("单价", "unitPrice", 50));
		list.add(new JTableListColumn("币制", "curr.currSymb", 80));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQueryEnvelop = new DgCommonQuery();

		List dataSource = transferFactoryManageAction
				.findCustomsEnvelopCommodityInfoByScmCoc(
						new Request(CommonVars.getCurrUser()), scmCoc,
						isCustomer);
		dgCommonQueryEnvelop.setDataSource(dataSource);
		dgCommonQueryEnvelop.setTitle("请选商品");
		dgCommonQueryEnvelop.setLike(true);
		dgCommonQueryEnvelop
				.addWindowListener(new java.awt.event.WindowAdapter() {
					public void windowOpened(WindowEvent e) {
						JTable table = dgCommonQueryEnvelop.getJTable();
						table.getColumnModel().getColumn(1)
								.setCellRenderer(new ProjectTypeRenderer());
					}

				});
		DgCommonQuery.setSingleResult(false);
		dgCommonQueryEnvelop.setVisible(true);
		if (dgCommonQueryEnvelop.isOk()) {
			return dgCommonQueryEnvelop.getReturnList();
		}
		return new ArrayList();
	}

	/**
	 * render table JchcckBox 列
	 */
	class CheckBoxRenderer extends DefaultTableCellRenderer {
		JCheckBox checkBox = new JCheckBox();

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (value == null) {
				checkBox.setSelected(false);
			} else if (value.equals("")) {
				checkBox.setSelected(false);
			} else if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				checkBox.setSelected(Boolean.parseBoolean(value.toString()));
			}
			checkBox.setHorizontalAlignment(JLabel.CENTER);
			checkBox.setBackground(table.getBackground());
			if (isSelected) {
				checkBox.setForeground(table.getSelectionForeground());
				checkBox.setBackground(table.getSelectionBackground());
			} else {
				checkBox.setForeground(table.getForeground());
				checkBox.setBackground(table.getBackground());
			}
			return checkBox;
		}
	}

	class ProjectTypeRenderer extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			super.getTableCellRendererComponent(table, value, isSelected,
					hasFocus, row, column);
			String strvalue = "";
			if (value == null) {
				strvalue = "";
			} else if (Integer.parseInt(value.toString()) == ProjectType.BCUS) {
				strvalue = "联网监管";
			} else if (Integer.parseInt(value.toString()) == ProjectType.BCS) {
				strvalue = "纸质手册";
			} else if (Integer.parseInt(value.toString()) == ProjectType.DZSC) {
				strvalue = "电子手册";
			}
			this.setText(strvalue);
			return this;
		}
	}

}
