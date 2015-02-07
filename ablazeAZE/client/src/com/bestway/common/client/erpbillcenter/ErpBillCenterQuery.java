/*
 * Created on 2005-7-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.erpbillcenter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JToolBar;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.entity.FactoryAndFactualCustomsRalation;
import com.bestway.bcus.cas.entity.StatCusNameRelationHsn;
import com.bestway.bcus.cas.entity.StatCusNameRelationMt;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.cas.entity.TempRelationCommInfo;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.bcus.client.common.DgCommonQueryPage;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.client.util.DataType;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings({"unchecked","serial"})
public class ErpBillCenterQuery {
	private static ErpBillCenterQuery casQuery = null;

	private ErpBillCenterQuery() {

	}

	public static synchronized ErpBillCenterQuery getInstance() {
		if (casQuery == null) {
			casQuery = new ErpBillCenterQuery();
		}
		return casQuery;
	}

	/**
	 * 新增商品大类时,进行大类编码查询
	 * 
	 * @param request
	 * @param materialType
	 * @return
	 */
	public Object findComplex(Request request, String materialType) {
		CasAction casAction = (CasAction) CommonVars.getApplicationContext()
				.getBean("casAction");
		List dataSource = casAction.findComplex(new Request(CommonVars
				.getCurrUser(), true), materialType);
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 新增对照关系时，选择物料
	 * 
	 * @param materialType
	 * @return
	 */
	public List<TempRelationCommInfo> findMaterialForRelation(
			String materialType) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("料号", "ptNo", 100));
		list.add(new JTableListColumn("物料名称", "factoryName", 100));
		list.add(new JTableListColumn("型号规格", "factorySpec", 100));
		list.add(new JTableListColumn("单位", "calUnit.name", 50));
		DgCommonQuery.setTableColumns(list);
		CasAction casAction = (CasAction) CommonVars.getApplicationContext()
				.getBean("casAction");
		List dataSource = casAction.findMaterialForRelation(new Request(
				CommonVars.getCurrUser(), true), materialType);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("物料查询");
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 新增对照关系时，选择报关商品
	 * 
	 * @param materialType
	 * @return
	 */
	public List<TempRelationCommInfo> findComplexForRelation(String materialType) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("商品序号", "seqNum", 80));
		list.add(new JTableListColumn("商品号码", "cusCode", 100));
		list.add(new JTableListColumn("商品名称", "cusName", 100));
		list.add(new JTableListColumn("商品规格", "cusSpec", 100));
		list.add(new JTableListColumn("单位", "cusUnit.name", 50));
		DgCommonQuery.setTableColumns(list);
		CasAction casAction = (CasAction) CommonVars.getApplicationContext()
				.getBean("casAction");
		List dataSource = casAction.findComplexForRelation(new Request(
				CommonVars.getCurrUser(), true), materialType);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("报关商品查询");
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 获得对照表的物料对象
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public List<StatCusNameRelationMt> getStatCusNameRelationMt(
			boolean isSingle, final String materielType, List billDetailList) {

		final Map<String, BillDetail> map = new HashMap<String, BillDetail>();
		for (int i = 0; i < billDetailList.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetailList.get(i);
			map.put(billDetail.getPtPart(), billDetail);
		}
		// dataSource.re
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("料号", "materiel.ptNo", "a.materiel.ptNo",
				100));// a is StatCusNameRelationMt 别名
		list.add(new JTableListColumn("物料名称", "materiel.factoryName",
				"a.materiel.factoryName", 100));
		list
				.add(new JTableListColumn("规格", "materiel.factorySpec", 120,
						false));
		list.add(new JTableListColumn("工厂单位", "materiel.calUnit.name", 100,
				false));
		list
				.add(new JTableListColumn("海关单位", "materiel.unit.name", 100,
						false));
		list.add(new JTableListColumn("单位换算率", "unitConvert", 100, false));
		list.add(new JTableListColumn("十码编号",
				"statCusNameRelation.complex.code", "s.complex.code", 100));// s
		// is
		// StatCusNameRelation
		// 别名
		list.add(new JTableListColumn("十码名称", "statCusNameRelation.cusName",
				"s.cusName", 100));
		list.add(new JTableListColumn("十码规格", "statCusNameRelation.cusSpec",
				"s.cusSpec", 100));
		list.add(new JTableListColumn("十码单位",
				"statCusNameRelation.cusUnit.name", "s.cusUnit.name", 50));
		String tempStr = materielType.trim();
		String str = "";
		if (tempStr.equals(MaterielType.MATERIEL)) {
			str = "料件";
		} else if (tempStr.equals(MaterielType.FINISHED_PRODUCT)) {
			str = "成品";
		} else if (tempStr.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
			str = "半成品";
		} else if (tempStr.equals(MaterielType.MACHINE)) {
			str = "设备";
		} else if (tempStr.equals(MaterielType.REMAIN_MATERIEL)) {
			str = "边角料";
		} else if (tempStr.equals(MaterielType.BAD_PRODUCT)) {
			str = "残次品";
		}
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100 + billDetailList.size());
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			public boolean isShowAll = false;

			public JCheckBox jCheckBox = null; // @jve:decl-index=0:visual-constraint="580,304"

			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<StatCusNameRelationMt> dataSource = casAction
						.findStatCusNameRelationMt(new Request(CommonVars
								.getCurrUser(), true), materielType, index,
								length, property, value, isLike);
				for (int i = 0; i < dataSource.size(); i++) {
					StatCusNameRelationMt s = (StatCusNameRelationMt) dataSource
							.get(i);
					if (s.getMateriel().getPtNo() == null) {
						continue;
					}
					if (!isShowAll) {
						if (map.get(s.getMateriel().getPtNo()) != null) {
							dataSource.remove(s);
							i--;
						}
					}
				}
				return dataSource;
			}

			@Override
			protected JToolBar getJToolBar() {
				if (jToolBar == null) {
					jToolBar = new JToolBar();
					jToolBar.setFloatable(false);
					jToolBar.add(getPnCommonQueryPage());
					jToolBar.add(getJCheckBox());
				}
				return jToolBar;
			}

			private void initComponent() {
				// getButtonGroup();
				// jrbHideOld
				// .addActionListener(new java.awt.event.ActionListener() {
				// public void actionPerformed(
				// java.awt.event.ActionEvent e) {
				// if (getJrbHideOld().isSelected()) {
				// isShowAll = false;
				// pnCommonQueryPage.setInitState();
				// }
				// }
				// });
				// jrbShowAll
				// .addActionListener(new java.awt.event.ActionListener() {
				// public void actionPerformed(
				// java.awt.event.ActionEvent e) {
				// if (getJrbShowAll().isSelected()) {
				// isShowAll = true;
				// pnCommonQueryPage.setInitState();
				// }
				// }
				// });
			}

			public JCheckBox getJCheckBox() {
				if (jCheckBox == null) {
					jCheckBox = new JCheckBox("是否过滤");
					jCheckBox.setSelected(true);
					jCheckBox
							.addActionListener(new java.awt.event.ActionListener() {
								public void actionPerformed(
										java.awt.event.ActionEvent e) {
									isShowAll = !isShowAll;
									pnCommonQueryPage.setInitState();
								}
							});
				}
				return jCheckBox;
			}

			// /**
			// * This method initializes jrbShowAll
			// *
			// * @return javax.swing.JRadioButton
			// */
			// private JRadioButton getJrbShowAll() {
			// if (jrbShowAll == null) {
			// jrbShowAll = new JRadioButton();
			// jrbShowAll.setText("显示全部");
			// }
			// return jrbShowAll;
			// }

			// /**
			// * This method initializes jrbHideOld
			// *
			// * @return javax.swing.JRadioButton
			// */
			// private JRadioButton getJrbHideOld() {
			// if (jrbHideOld == null) {
			// jrbHideOld = new JRadioButton();
			// jrbHideOld.setText("料号过滤");
			// jrbHideOld.setSelected(true);
			// }
			// return jrbHideOld;
			// }

			// /**
			// * This method initializes buttonGroup
			// *
			// * @return javax.swing.ButtonGroup
			// */
			// private ButtonGroup getButtonGroup() {
			// if (buttonGroup == null) {
			// buttonGroup = new ButtonGroup();
			// buttonGroup.add(getJrbShowAll());
			// buttonGroup.add(getJrbHideOld());
			// }
			// return buttonGroup;
			// }

			/**
			 * This method initializes jPanel
			 * 
			 * @return javax.swing.JPanel
			 */
			// private JPanel getJPanel() {
			// if (jPanel == null) {
			// jPanel = new JPanel();
			// jPanel.setLayout(new FlowLayout());
			// jPanel.setSize(new Dimension(176, 28));
			// jPanel.setBorder(BorderFactory
			// .createLineBorder(Color.DARK_GRAY));// 边距为4
			// //
			// jPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory
			// // .createBevelBorder(javax.swing.border.BevelBorder.LOWERED),
			// // "是否过滤已存在的料件!", TitledBorder.DEFAULT_JUSTIFICATION,
			// // TitledBorder.DEFAULT_POSITION, null, Color.blue));// 边距为4
			// jPanel.add(getJrbShowAll(), null);
			// jPanel.add(getJrbHideOld(), null);
			// }
			// return jPanel;
			// }
		};

		dgCommonQuery.setTitle("选择【" + str + "】资料");
		dgCommonQuery.setSize(700, 457);
		DgCommonQueryPage.setSingleResult(isSingle);

		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;

	}

	/**
	 * 
	 * @param isSingle
	 * @param ptNo
	 * @param complexTen
	 * @return
	 */
	public List<StatCusNameRelationHsn> getStatCusNameRelationHsn(
			boolean isSingle, final String ptNo, final Complex complexTen) {

		// dataSource.re
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("十码编码", "complex.code", 100));
		list.add(new JTableListColumn("十码名称", "cusName", 100));
		list.add(new JTableListColumn("十码规格", "cusSpec", 100));
		list.add(new JTableListColumn("十码单位", "cusUnit.name", 50));
		list.add(new JTableListColumn("手册号", "emsNo", 120));
		list.add(new JTableListColumn("类别", "projectName", 50));
		list.add(new JTableListColumn("开始日期", "emsBeginDate", 80));
		list.add(new JTableListColumn("结束日期", "emsEndDate", 80));

		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			public boolean isShowAll = false;

			public JCheckBox jCheckBox = null; // @jve:decl-index=0:visual-constraint="580,304"

			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<StatCusNameRelationHsn> dataSource = casAction
						.findStatCusNameRelationHsn(new Request(CommonVars
								.getCurrUser(), true), ptNo, index, length,
								property, value, isLike);
				for (int i = 0; i < dataSource.size(); i++) {
					StatCusNameRelationHsn s = (StatCusNameRelationHsn) dataSource
							.get(i);
					if (s.getComplex() == null) {
						continue;
					}
					if (!isShowAll) {
						if (complexTen != null) {
							if (s.getComplex().getCode().equals(
									complexTen.getCode())) {
								dataSource.remove(s);
								i--;
							}
						}
					}
				}
				return dataSource;
			}

			@Override
			protected JToolBar getJToolBar() {
				if (jToolBar == null) {
					jToolBar = new JToolBar();
					jToolBar.setFloatable(false);
					jToolBar.add(getPnCommonQueryPage());
					jToolBar.add(getJCheckBox());
				}
				return jToolBar;
			}

			public JCheckBox getJCheckBox() {
				if (jCheckBox == null) {
					jCheckBox = new JCheckBox("是否过滤");
					jCheckBox.setSelected(true);
					jCheckBox
							.addActionListener(new java.awt.event.ActionListener() {
								public void actionPerformed(
										java.awt.event.ActionEvent e) {
									isShowAll = !isShowAll;
									pnCommonQueryPage.setInitState();
								}
							});
				}
				return jCheckBox;
			}
		};

		dgCommonQuery.setTitle("选择物料所对应的【十码】资料");
		dgCommonQuery.setSize(700, 457);
		DgCommonQueryPage.setSingleResult(isSingle);

		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;

	}

	/**
	 * 返回所有工厂名称资料
	 * 
	 * @param kindCode
	 *            商品种类
	 * @return Materiel
	 */
	public Object getFactoryName(String kindCode) {
		CasAction casAction = (CasAction) CommonVars.getApplicationContext()
				.getBean("casAction");
		List<JTableListColumn> columns = new ArrayList<JTableListColumn>();
		columns.add(new JTableListColumn("BOM编号", "ptVersion", 120));
		columns.add(new JTableListColumn("商品名称", "factoryName", 120));
		columns.add(new JTableListColumn("型号规格", "factorySpec", 120));
		columns.add(new JTableListColumn("单位", "calUnit.name", 60));
		DgCommonQuery.setTableColumns(columns);
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		String tempStr = kindCode.trim();
		String str = null;
		if (tempStr.equals(MaterielType.MATERIEL)) {
			str = "料件";
		} else if (tempStr.equals(MaterielType.FINISHED_PRODUCT)) {
			str = "成品";
		} else if (tempStr.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
			str = "半成品";
		} else if (tempStr.equals(MaterielType.MACHINE)) {
			str = "设备";
		} else if (tempStr.equals(MaterielType.REMAIN_MATERIEL)) {
			str = "边角料";
		} else if (tempStr.equals(MaterielType.BAD_PRODUCT)) {
			str = "残次品";
		}
		dgCommonQuery.setTitle(str);
		dgCommonQuery.setDataSource((casAction.findFactoryNames(new Request(
				CommonVars.getCurrUser()), kindCode)));
		dgCommonQuery.setSingleResult(true);
		dgCommonQuery.setAlwaysOnTop(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 获得对照表的物料对象
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public Object findPtNameFromBillDetail(final String materielType) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("物料名称", "object", "ptName",
				DataType.STRING, 100));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> dataSource = casAction
						.findPtNameFromBillDetail(new Request(CommonVars
								.getCurrUser(), true), materielType, index,
								length, property, value, isLike);
				return dataSource;
			}
		};
		dgCommonQuery.setTitle("选择【物料名称】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 获得对照表的报关名称
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public Object findHsNameFromStatCusNameRelationHsn(final String materielType) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("报关名称", "object", "cusName",
				DataType.STRING, 100));
		list.add(new JTableListColumn("报关规格", "object1", "cusSpec",
				DataType.STRING, 100));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> dataSource = casAction
						.findHsNameFromStatCusNameRelationHsn(new Request(
								CommonVars.getCurrUser(), true), materielType,
								index, length, property, value, isLike);
				return dataSource;
			}
		};
		dgCommonQuery.setTitle("选择【报关名称】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}

	/**
	 * 获得对照表的报关名称
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public Object findHsSpecFromStatCusNameRelationHsn(
			final String materielType, final String hsName) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("报关规格", "object", "cusSpec",
				DataType.STRING, 100));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> dataSource = casAction
						.findHsSpecFromStatCusNameRelationHsn(new Request(
								CommonVars.getCurrUser(), true), materielType,
								hsName, index, length, property, value, isLike);
				return dataSource;
			}
		};
		dgCommonQuery.setTitle("选择【报关规格】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}

	/**
	 * 返回返回工厂商品名称
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public Object findPtNameFromStatCusNameRelationMt(final String materielType) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("工厂名称", "object", "materiel.factoryName",
				DataType.STRING, 100));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> dataSource = casAction
						.findPtNameFromStatCusNameRelationMt(new Request(
								CommonVars.getCurrUser(), true), materielType,
								index, length, property, value, isLike);
				return dataSource;
			}
		};
		dgCommonQuery.setTitle("选择【工厂名称】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}

	/**
	 * 返回返回工厂商品名称
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public Object findPtSpecFromStatCusNameRelationMt(
			final String materielType, final String ptName) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("工厂规格", "object", "materiel.factorySpec",
				DataType.STRING, 100));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> dataSource = casAction
						.findPtSpecFromStatCusNameRelationMt(new Request(
								CommonVars.getCurrUser(), true), materielType,
								ptName, index, length, property, value, isLike);
				return dataSource;
			}
		};
		dgCommonQuery.setTitle("选择【工厂规格】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}

	/**
	 * 按照物料与报关商品对照表选取物料资料
	 * 
	 * @param isSingle
	 *            是否多选
	 * @param materielType
	 *            物料类型
	 * @param billDetailList
	 *            单据体明细列表（用于数据过滤）
	 * @return 选取的物料资料与单位换算率
	 */
	public List<TempObject> getFactoryAndFactualCustomsRalation(
			boolean isSingle, final String materielType, List billDetailList) {

		final Map<String, BillDetail> map = new HashMap<String, BillDetail>();// 用于过滤的MAP
		for (int i = 0; i < billDetailList.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetailList.get(i);
			map.put(billDetail.getPtPart(), billDetail);
		}
		// dataSource.re
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("料号", "object.ptNo", "a.materiel.ptNo",
				DataType.STRING, 100));// a is StatCusNameRelationMt 别名
		list.add(new JTableListColumn("物料名称", "object.factoryName",
				"a.materiel.factoryName", DataType.STRING, 100));
		list.add(new JTableListColumn("规格", "object.factorySpec",
				"a.materiel.factorySpec", DataType.STRING, 120));
		list.add(new JTableListColumn("工厂单位", "object.calUnit.name",
				"a.materiel.calUnit.name", DataType.STRING, 100));
		list.add(new JTableListColumn("单位换算率", "object1", "a.unitConvert",
				DataType.DOUBLE, 100));
		String tempStr = materielType.trim();
		String str = "";
		if (tempStr.equals(MaterielType.MATERIEL)) {
			str = "料件";
		} else if (tempStr.equals(MaterielType.FINISHED_PRODUCT)) {
			str = "成品";
		} else if (tempStr.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
			str = "半成品";
		} else if (tempStr.equals(MaterielType.MACHINE)) {
			str = "设备";
		} else if (tempStr.equals(MaterielType.REMAIN_MATERIEL)) {
			str = "边角料";
		} else if (tempStr.equals(MaterielType.BAD_PRODUCT)) {
			str = "残次品";
		}
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100 + billDetailList.size());
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			public boolean isShowAll = false;

			public JCheckBox jCheckBox = null; // @jve:decl-index=0:visual-constraint="580,304"

			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> dataSource = casAction
						.findFactoryAndFactualCustomsRalation(new Request(
								CommonVars.getCurrUser(), true), materielType,
								index, length, property, value, isLike);
				for (int i = 0; i < dataSource.size(); i++) {
					TempObject s = (TempObject) dataSource.get(i);
					Materiel m = (Materiel) s.getObject();
					if (m.getPtNo() == null) {
						continue;
					}
					if (!isShowAll) {// 过滤
						if (map.get(m.getPtNo()) != null) {
							dataSource.remove(s);
							i--;
						}
					}
				}
				return dataSource;
			}

			@Override
			protected JToolBar getJToolBar() {
				if (jToolBar == null) {
					jToolBar = new JToolBar();
					jToolBar.setFloatable(false);
					jToolBar.add(getPnCommonQueryPage());
					jToolBar.add(getJCheckBox());
				}
				return jToolBar;
			}

			public JCheckBox getJCheckBox() {
				if (jCheckBox == null) {
					jCheckBox = new JCheckBox("是否过滤");
					jCheckBox.setSelected(true);
					jCheckBox
							.addActionListener(new java.awt.event.ActionListener() {
								public void actionPerformed(
										java.awt.event.ActionEvent e) {
									isShowAll = !isShowAll;
									pnCommonQueryPage.setInitState();
								}
							});
				}
				return jCheckBox;
			}
		};

		dgCommonQuery.setTitle("选择【" + str + "】资料");
		dgCommonQuery.setSize(700, 457);
		DgCommonQueryPage.setSingleResult(isSingle);

		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;

	}

	public String getFactoryAndFactualCustomsRalationMaterielForNetWeight(
			final String materielType, String MaterielPtNo) {
		String MaterielNetWeight = "1";
		CasAction casAction = (CasAction) CommonVars.getApplicationContext()
				.getBean("casAction");
		List<Double> dataSource = casAction
				.findFactoryAndFactualCustomsRalationMaterielForNetWeight(
						new Request(CommonVars.getCurrUser(), true),
						materielType, MaterielPtNo);
		if (dataSource.size() > 0) {
			Object tmp = dataSource.get(0);
			if (tmp != null) {
				BigDecimal dg = new BigDecimal(Double.parseDouble(tmp
						.toString()));
				MaterielNetWeight = dg.setScale(6, BigDecimal.ROUND_HALF_UP)
						.toString();
			}
		}
		return MaterielNetWeight;
	}

	/**
	 * 按照物料与报关商品对照表选取物料资料
	 * 
	 * @param isSingle
	 *            是否多选
	 * @param materielType
	 *            物料类型
	 * @param billDetailList
	 *            单据体明细列表（用于数据过滤）
	 * @return 选取的物料资料与单位换算率
	 */
	public List<FactoryAndFactualCustomsRalation> getFactoryAndFactualCustomsRalationforBill(
			boolean isSingle, final String materielType, List billDetailList) {

		final Map<String, BillDetail> map = new HashMap<String, BillDetail>();// 用于过滤的MAP
		for (int i = 0; i < billDetailList.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetailList.get(i);
			map.put(billDetail.getPtPart(), billDetail);
		}
		// dataSource.re
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("料号", "materiel.ptNo", "a.materiel.ptNo",
				100));// a is StatCusNameRelationMt 别名
		list.add(new JTableListColumn("物料名称", "materiel.factoryName",
				"a.materiel.factoryName", 100));
		list.add(new JTableListColumn("规格", "materiel.factorySpec",
				"a.materiel.factorySpec", 120));
		list.add(new JTableListColumn("工厂单位", "materiel.calUnit.name",
				"a.materiel.calUnit.name", 100));
		list.add(new JTableListColumn("单位换算率", "unitConvert", "a.unitConvert",
				100));
		list.add(new JTableListColumn("报关编码",
				"statCusNameRelationHsn.complex.code",
				"a.statCusNameRelationHsn.complex.code", 100));
		list.add(new JTableListColumn("报关名称", "statCusNameRelationHsn.cusName",
				"a.statCusNameRelationHsn.cusName", 100));
		list.add(new JTableListColumn("报关规格", "statCusNameRelationHsn.cusSpec",
				"a.statCusNameRelationHsn.cusSpec", 100));
		list.add(new JTableListColumn("报关单位",
				"statCusNameRelationHsn.cusUnit.name",
				"a.statCusNameRelationHsn.cusUnit.name", 50));
		list.add(new JTableListColumn("归并序号", "statCusNameRelationHsn.seqNum",
				"a.statCusNameRelationHsn.seqNum", 50));
		list.add(new JTableListColumn("版本号", "statCusNameRelationHsn.version",
				"a.statCusNameRelationHsn.version", 80));
		list.add(new JTableListColumn("手册号", "statCusNameRelationHsn.emsNo",
				"a.statCusNameRelationHsn.emsNo", 120));
		list.add(new JTableListColumn("类别",
				"statCusNameRelationHsn.projectName",
				"a.statCusNameRelationHsn.projectName", 80));
		list.add(new JTableListColumn("单净重", "materiel.ptNetWeight",
				"a.materiel.ptNetWeight", 80));
		String tempStr = materielType.trim();
		String str = "";
		if (tempStr.equals(MaterielType.MATERIEL)) {
			str = "料件";
		} else if (tempStr.equals(MaterielType.FINISHED_PRODUCT)) {
			str = "成品";
		} else if (tempStr.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
			str = "半成品";
		} else if (tempStr.equals(MaterielType.MACHINE)) {
			str = "设备";
		} else if (tempStr.equals(MaterielType.REMAIN_MATERIEL)) {
			str = "边角料";
		} else if (tempStr.equals(MaterielType.BAD_PRODUCT)) {
			str = "残次品";
		}
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100 + billDetailList.size());
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			public boolean isShowAll = false;

			public JCheckBox jCheckBox = null; // @jve:decl-index=0:visual-constraint="580,304"

			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<FactoryAndFactualCustomsRalation> dataSource = casAction
						.findFactoryAndFactualCustomsRalationForBill(
								new Request(CommonVars.getCurrUser(), true),
								materielType, index, length, property, value,
								isLike);
				for (int i = 0; i < dataSource.size(); i++) {
					FactoryAndFactualCustomsRalation s = (FactoryAndFactualCustomsRalation) dataSource
							.get(i);
					Materiel m = s.getMateriel();
					if (m.getPtNo() == null) {
						continue;
					}
					if (!isShowAll) {// 过滤
						if (map.get(m.getPtNo()) != null) {
							dataSource.remove(s);
							i--;
						}
					}
				}
				return dataSource;
			}

			@Override
			protected JToolBar getJToolBar() {
				if (jToolBar == null) {
					jToolBar = new JToolBar();
					jToolBar.setFloatable(false);
					jToolBar.add(getPnCommonQueryPage());
					jToolBar.add(getJCheckBox());
				}
				return jToolBar;
			}

			public JCheckBox getJCheckBox() {
				if (jCheckBox == null) {
					jCheckBox = new JCheckBox("是否过滤");
					jCheckBox.setSelected(true);
					jCheckBox
							.addActionListener(new java.awt.event.ActionListener() {
								public void actionPerformed(
										java.awt.event.ActionEvent e) {
									isShowAll = !isShowAll;
									pnCommonQueryPage.setInitState();
								}
							});
				}
				return jCheckBox;
			}
		};

		dgCommonQuery.setTitle("选择【" + str + "】资料");
		dgCommonQuery.setSize(830, 460);// this.setSize(750, 460);
		DgCommonQueryPage.setSingleResult(isSingle);

		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;

	}

	/**
	 * 按照物料与报关商品对照表选取物料资料
	 * 
	 * @param isSingle
	 *            是否多选
	 * @param materielType
	 *            物料类型
	 * @param billDetailList
	 *            单据体明细列表（用于数据过滤）
	 * @return 选取的物料资料与单位换算率
	 */
	public List getBcpFromWlzd(boolean isSingle, final String materielType,
			List billDetailList) {

		final Map<String, BillDetail> map = new HashMap<String, BillDetail>();// 用于过滤的MAP
		for (int i = 0; i < billDetailList.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetailList.get(i);
			map.put(billDetail.getPtPart(), billDetail);
		}
		// dataSource.re
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		// list.add(new JTableListColumn("料号", "materiel.ptNo",
		// "a.materiel.ptNo",
		// 100));// a is StatCusNameRelationMt 别名
		// list.add(new JTableListColumn("物料名称", "materiel.factoryName",
		// "a.materiel.factoryName", 100));
		// list.add(new JTableListColumn("规格", "materiel.factorySpec",
		// "a.materiel.factorySpec", 120));
		// list.add(new JTableListColumn("工厂单位", "materiel.calUnit.name",
		// "a.materiel.calUnit.name", 100));
		// list.add(new JTableListColumn("单位换算率", "unitConvert",
		// "a.unitConvert",
		// 100));
		// list.add(new JTableListColumn("报关编码",
		// "statCusNameRelationHsn.complex.code",
		// "a.statCusNameRelationHsn.complex.code", 100));
		// list.add(new JTableListColumn("报关名称",
		// "statCusNameRelationHsn.cusName",
		// "a.statCusNameRelationHsn.cusName", 100));
		// list.add(new JTableListColumn("报关规格",
		// "statCusNameRelationHsn.cusSpec",
		// "a.statCusNameRelationHsn.cusSpec", 100));
		// list.add(new JTableListColumn("报关单位",
		// "statCusNameRelationHsn.cusUnit.name",
		// "a.statCusNameRelationHsn.cusUnit.name", 50));
		// list.add(new JTableListColumn("归并序号",
		// "statCusNameRelationHsn.seqNum",
		// "a.statCusNameRelationHsn.seqNum", 50));
		// list.add(new JTableListColumn("版本号",
		// "statCusNameRelationHsn.version",
		// "a.statCusNameRelationHsn.version", 80));
		// list.add(new JTableListColumn("手册号", "statCusNameRelationHsn.emsNo",
		// "a.statCusNameRelationHsn.emsNo", 120));
		// list.add(new JTableListColumn("类别",
		// "statCusNameRelationHsn.projectName",
		// "a.statCusNameRelationHsn.projectName", 80));
		// list.add(new JTableListColumn("单净重", "materiel.ptNetWeight",
		// "a.materiel.ptNetWeight", 80));

		list.add(new JTableListColumn("料号", "ptNo", 100));
		list.add(new JTableListColumn("物料编码", "complex.code", 80));
		list.add(new JTableListColumn("商品名称", "factoryName", 100));// 工厂名称
		list.add(new JTableListColumn("商品规格", "factorySpec", 100));
		list.add(new JTableListColumn("单位", "calUnit.name", 50));
		list.add(new JTableListColumn("单价", "ptPrice", 50));
		list.add(new JTableListColumn("净重", "ptNetWeight", 80));
		String tempStr = materielType.trim();
		String str = "";
		if (tempStr.equals(MaterielType.MATERIEL)) {
			str = "料件";
		} else if (tempStr.equals(MaterielType.FINISHED_PRODUCT)) {
			str = "成品";
		} else if (tempStr.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
			str = "半成品";
		} else if (tempStr.equals(MaterielType.MACHINE)) {
			str = "设备";
		} else if (tempStr.equals(MaterielType.REMAIN_MATERIEL)) {
			str = "边角料";
		} else if (tempStr.equals(MaterielType.BAD_PRODUCT)) {
			str = "残次品";
		}
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100 + billDetailList.size());
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			public boolean isShowAll = false;

			public JCheckBox jCheckBox = null; // @jve:decl-index=0:visual-constraint="580,304"

			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CommonBaseCodeAction commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
						.getApplicationContext()
						.getBean("commonBaseCodeAction");
				List dataSource = commonBaseCodeAction.findMaterielAll(
						new Request(CommonVars.getCurrUser(), true), "1",
						index, length, property, value, isLike);
				for (int i = 0; i < dataSource.size(); i++) {
					Materiel s = (Materiel) dataSource.get(i);
					if (s.getPtNo() == null) {
						continue;
					}
					if (!isShowAll) {// 过滤
						if (map.get(s.getPtNo()) != null) {
							dataSource.remove(s);
							i--;
						}
					}
				}
				return dataSource;
			}

			@Override
			protected JToolBar getJToolBar() {
				if (jToolBar == null) {
					jToolBar = new JToolBar();
					jToolBar.setFloatable(false);
					jToolBar.add(getPnCommonQueryPage());
					jToolBar.add(getJCheckBox());
				}
				return jToolBar;
			}

			public JCheckBox getJCheckBox() {
				if (jCheckBox == null) {
					jCheckBox = new JCheckBox("是否过滤");
					jCheckBox.setSelected(true);
					jCheckBox
							.addActionListener(new java.awt.event.ActionListener() {
								public void actionPerformed(
										java.awt.event.ActionEvent e) {
									isShowAll = !isShowAll;
									pnCommonQueryPage.setInitState();
								}
							});
				}
				return jCheckBox;
			}
		};

		dgCommonQuery.setTitle("选择【" + str + "】资料");
		dgCommonQuery.setSize(700, 457);
		DgCommonQueryPage.setSingleResult(isSingle);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 变更物料所对应的报关商品,弹出的此物料所对应的报关商品列表
	 * 
	 * @param isSingle
	 * @param ptNo
	 * @param complex
	 * @return
	 */
	public List<FactoryAndFactualCustomsRalation> getFacutalCustoms(boolean isSingle,
			final String ptNo, final Complex complex, final String hsSpec,
			final Unit hsUnit) {

		// dataSource.re
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("报关编码", "statCusNameRelationHsn.complex.code",
				"a.statCusNameRelationHsn.complex.code", 100));
		list.add(new JTableListColumn("报关名称", "statCusNameRelationHsn.cusName",
				"a.statCusNameRelationHsn.cusName", 100));
		list.add(new JTableListColumn("报关规格", "statCusNameRelationHsn.cusmSpec",
				"a.statCusNameRelationHsn.cusSpec", 100));
		list.add(new JTableListColumn("报关单位", "statCusNameRelationHsn.cusUnit.name",
				"a.statCusNameRelationHsn.cusUnit.name", 50));
		list.add(new JTableListColumn("归并序号", "statCusNameRelationHsn.seqNum",
				"a.statCusNameRelationHsn.seqNum", 50));
		list.add(new JTableListColumn("版本号", "statCusNameRelationHsn.version",
				"a.statCusNameRelationHsn.version", 80));
		list.add(new JTableListColumn("手册号", "statCusNameRelationHsn.emsNo",
				"a.statCusNameRelationHsn.emsNo", 120));
		list.add(new JTableListColumn("类别", "statCusNameRelationHsn.projectName", 80, false));
		list.add(new JTableListColumn("开始日期", "statCusNameRelationHsn.emsBeginDate",
				"a.statCusNameRelationHsn.emsBeginDate", 80));
		list.add(new JTableListColumn("结束日期", "statCusNameRelationHsn.emsEndDate",
				"a.statCusNameRelationHsn.emsEndDate", 80));

		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			public boolean isShowAll = false;

			public JCheckBox jCheckBox = null; // @jve:decl-index=0:visual-constraint="580,304"

			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<FactoryAndFactualCustomsRalation> dataSource = casAction
						.findFactualCustoms(new Request(CommonVars
								.getCurrUser(), true), ptNo, index, length,
								property, value, isLike);
				// 用于过滤比较
				String tenTemp = (complex == null ? "" : complex.getCode())
						+ (hsSpec == null ? "" : hsSpec)
						+ (hsUnit == null ? "" : hsUnit.getName());
				for (int i = 0; i < dataSource.size(); i++) {
					StatCusNameRelationHsn s = dataSource
							.get(i).getStatCusNameRelationHsn();
					if (s.getComplex() == null) {
						continue;
					}
					if (!isShowAll) {
						if (complex != null) {
							String tmp = s.getComplex().getCode()
									+ s.getCusSpec() == null ? "" : s
									.getCusSpec()
									+ s.getCusUnit() == null ? "" : s
									.getCusUnit().getName();

							if (tmp.equalsIgnoreCase(tenTemp)) {
								dataSource.remove(s);
								i--;
							}
						}
					}
				}
				return dataSource;
			}

			@Override
			protected JToolBar getJToolBar() {
				if (jToolBar == null) {
					jToolBar = new JToolBar();
					jToolBar.setFloatable(false);
					jToolBar.add(getPnCommonQueryPage());
					jToolBar.add(getJCheckBox());
				}
				return jToolBar;
			}

			public JCheckBox getJCheckBox() {
				if (jCheckBox == null) {
					jCheckBox = new JCheckBox("是否过滤");
					jCheckBox.setSelected(true);
					jCheckBox
							.addActionListener(new java.awt.event.ActionListener() {
								public void actionPerformed(
										java.awt.event.ActionEvent e) {
									isShowAll = !isShowAll;
									pnCommonQueryPage.setInitState();
								}
							});
				}
				return jCheckBox;
			}
		};

		dgCommonQuery.setTitle("选择物料所对应的【报关商品】资料");
		dgCommonQuery.setSize(700, 457);
		DgCommonQueryPage.setSingleResult(isSingle);

		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;

	}

	/**
	 * 变更物料所对应的报关商品,弹出的此物料所对应的报关商品列表
	 * 
	 * @param isSingle
	 * @param ptNo
	 * @param complex
	 * @return
	 */
	public List<FactoryAndFactualCustomsRalation> getHsByPtNo(boolean isSingle,
			final String ptNo) {

		// dataSource.re
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("报关编码",
				"statCusNameRelationHsn.complex.code", 100));
		list.add(new JTableListColumn("报关名称", "statCusNameRelationHsn.cusName",
				100));
		list.add(new JTableListColumn("报关规格", "statCusNameRelationHsn.cusSpec",
				100));
		list.add(new JTableListColumn("报关单位",
				"statCusNameRelationHsn.cusUnit.name", 50));
		list.add(new JTableListColumn("归并序号", "statCusNameRelationHsn.seqNum",
				50));
		list.add(new JTableListColumn("版本号", "statCusNameRelationHsn.version",
				80));
		list.add(new JTableListColumn("手册号", "statCusNameRelationHsn.emsNo",
				120));
		list.add(new JTableListColumn("类别",
				"statCusNameRelationHsn.projectName", 80, false));
		list.add(new JTableListColumn("开始日期",
				"statCusNameRelationHsn.emsBeginDate", 80));
		list.add(new JTableListColumn("结束日期",
				"statCusNameRelationHsn.emsEndDate", 80));
		list.add(new JTableListColumn("折算报关系数", "unitConvert", 80));

		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			public boolean isShowAll = false;

			public JCheckBox jCheckBox = null; // @jve:decl-index=0:visual-constraint="580,304"

			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<StatCusNameRelationHsn> dataSource = casAction
						.findHsByPtNo(new Request(CommonVars.getCurrUser(),
								true), ptNo, index, length, property, value,
								isLike);
				return dataSource;
			}

		};

		dgCommonQuery.setTitle("选择物料所对应的【报关商品】资料");
		dgCommonQuery.setSize(700, 457);
		DgCommonQueryPage.setSingleResult(isSingle);

		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;

	}

	/**
	 * 根据
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public BaseCustomsDeclaration getSpecialCustomsContractNo(final String emsNo) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("协议号", "contract", 120));
		list.add(new JTableListColumn("报关单号", "customsDeclarationCode", 150));
		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List dataSource = casAction.getSpecialCustomsContractNo(
						new Request(CommonVars.getCurrUser(), true), emsNo,
						index, length, property, value, isLike);
				return dataSource;
			}
		};
		dgCommonQuery.setTitle("选择【报关单号码】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return (BaseCustomsDeclaration) dgCommonQuery.getReturnValue();
		}
		return null;

	}

	/**
	 * 残次品 中 新增半成品 按钮
	 * 
	 * 抓报关常用工厂物料 中的 半成品及残次品
	 * 
	 * @param isSingle是否多选
	 * @param billDetailList
	 *            单据体明细列表（用于数据过滤）
	 * @return 选取的物料资料与单位换算率
	 * 
	 * @author wss
	 */
	public List getMForBadBillDetial(boolean isSingle, List billDetailList) {

		final Map<String, BillDetail> map = new HashMap<String, BillDetail>();// 用于过滤的MAP
		for (int i = 0; i < billDetailList.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetailList.get(i);
			map.put(billDetail.getPtPart(), billDetail);
		}
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();

		list.add(new JTableListColumn("料号", "ptNo", 100));
		list.add(new JTableListColumn("物料编码", "complex.code", 80));
		list.add(new JTableListColumn("商品名称", "factoryName", 100));// 工厂名称
		list.add(new JTableListColumn("商品规格", "factorySpec", 100));
		list.add(new JTableListColumn("单位", "calUnit.name", 50));
		list.add(new JTableListColumn("单价", "ptPrice", 50));
		list.add(new JTableListColumn("净重", "ptNetWeight", 80));

		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100 + billDetailList.size());
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			public boolean isShowAll = false;

			public JCheckBox jCheckBox = null; // @jve:decl-index=0:visual-constraint="580,304"

			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CommonBaseCodeAction commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
						.getApplicationContext()
						.getBean("commonBaseCodeAction");

				List list1 = commonBaseCodeAction.findMaterielAll(new Request(
						CommonVars.getCurrUser(), true), "1", index, length,
						property, value, isLike);

				List list2 = commonBaseCodeAction.findMaterielAll(new Request(
						CommonVars.getCurrUser(), true), "5", index, length,
						property, value, isLike);
				list2.addAll(list1);

				for (int i = 0; i < list2.size(); i++) {
					Materiel s = (Materiel) list2.get(i);
					if (s.getPtNo() == null) {
						continue;
					}
					if (!isShowAll) {// 过滤
						if (map.get(s.getPtNo()) != null) {
							list2.remove(s);
							i--;
						}
					}
				}
				return list2;
			}

			@Override
			protected JToolBar getJToolBar() {
				if (jToolBar == null) {
					jToolBar = new JToolBar();
					jToolBar.setFloatable(false);
					jToolBar.add(getPnCommonQueryPage());
					jToolBar.add(getJCheckBox());
				}
				return jToolBar;
			}

			public JCheckBox getJCheckBox() {
				if (jCheckBox == null) {
					jCheckBox = new JCheckBox("是否过滤");
					jCheckBox.setSelected(true);
					jCheckBox
							.addActionListener(new java.awt.event.ActionListener() {
								public void actionPerformed(
										java.awt.event.ActionEvent e) {
									isShowAll = !isShowAll;
									pnCommonQueryPage.setInitState();
								}
							});
				}
				return jCheckBox;
			}
		};

		dgCommonQuery.setTitle("选择【" + "报关常用工厂物料  (残次品or半成品)" + "】资料");
		dgCommonQuery.setSize(700, 457);
		DgCommonQueryPage.setSingleResult(isSingle);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}
}
