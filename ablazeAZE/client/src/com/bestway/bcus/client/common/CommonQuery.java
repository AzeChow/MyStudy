/*
 * Created on 2004-6-28
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.common;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contractcav.entity.ContractImgCav;
import com.bestway.bcs.contractexe.action.ContractExeAction;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclaration;
import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.entity.CancelHead;
import com.bestway.bcus.checkcancel.entity.CheckExg;
import com.bestway.bcus.checkcancel.entity.CheckHead;
import com.bestway.bcus.checkcancel.entity.CheckImg;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.ClassList;
import com.bestway.bcus.dataimport.entity.DBDataRoot;
import com.bestway.bcus.dataimport.entity.DBFormat;
import com.bestway.bcus.dataimport.entity.DBTaskEx;
import com.bestway.bcus.dataimport.entity.DBTaskSel;
import com.bestway.bcus.dataimport.entity.FieldList;
import com.bestway.bcus.dataimport.entity.TxtFormat;
import com.bestway.bcus.dataimport.entity.TxtTask;
import com.bestway.bcus.dataimport.entity.TxtTaskEx;
import com.bestway.bcus.dataimport.entity.TxtTaskSel;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.bcus.innermerge.entity.ReverseMergeFourData;
import com.bestway.bcus.innermerge.entity.TempMateriel;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgBefore;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgBom;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgBefore;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerVersion;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrExg;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrImg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kBom;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kFas;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kFasExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kFasImg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.bcus.system.entity.CustomsDeclarationSet;
import com.bestway.client.custom.common.EncCommon;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.authority.action.AuthorityAction;
import com.bestway.common.authority.entity.AclGroup;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.transferfactory.action.TransferFactoryManageAction;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.dzsc.checkcancel.entity.DzscContractImgCav;
import com.bestway.dzsc.contractexe.action.DzscContractExeAction;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclaration;
import com.bestway.dzsc.dzscmanage.action.DzscAction;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings({ "unchecked", "serial" })
public class CommonQuery {
	private static CommonQuery commonQuery = null;

	private CustomBaseAction customBaseAction = (CustomBaseAction) CommonVars
			.getApplicationContext().getBean("customBaseAction");

	protected CommonBaseCodeAction commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
			.getApplicationContext().getBean("commonBaseCodeAction");

	private CommonQuery() {

	}

	public synchronized static CommonQuery getInstance() {
		if (commonQuery == null) {
			commonQuery = new CommonQuery();
		}
		return commonQuery;
	}

	/*
	 * public Object getComplex(String scmcoi) { List list = new Vector();
	 * list.add(new JTableListColumn("凭证序号", "seqNum",60)); list.add(new
	 * JTableListColumn("商品编号", "code", 80)); list.add(new
	 * JTableListColumn("商品名称", "name", 120)); list.add(new
	 * JTableListColumn("常用单位", "comUnit.name", 60)); list.add(new
	 * JTableListColumn("第一单位编码", "firstUnit.code", 80)); list.add(new
	 * JTableListColumn("第二单位编码", "secondUnit.code", 80)); list.add(new
	 * JTableListColumn("类型", "scmCoi", 80));
	 * DgCommonQuery.setTableColumns(list); DgCommonQuery dgCommonQuery = new
	 * DgCommonQuery(); List list1 = customBaseAction.findComplexByType(scmcoi);
	 * dgCommonQuery.setDataSource(list1); dgCommonQuery.setTitle("选择商品编码");
	 * dgCommonQuery.setVisible(true); if (dgCommonQuery.isOk()) { return
	 * dgCommonQuery.getReturnValue(); } return null; }
	 */
	public Object getClassList() {
		List list = new Vector();
		list.add(new JTableListColumn("序号", "sortno", 60));
		list.add(new JTableListColumn("表中文名称", "name", 80));
		list.add(new JTableListColumn("类路径", "classPath", 150));
		DgCommonQuery.setTableColumns(list);
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		DataImportAction dataImportAction = (DataImportAction) CommonVars
				.getApplicationContext().getBean("dataImportAction");
		List list1 = dataImportAction.findClassList(new Request(CommonVars
				.getCurrUser()));
		dgCommonQuery.setDataSource(list1);
		dgCommonQuery.setTitle("选择目标表");
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	public Object getDBView(DBDataRoot root) {
		List list = new Vector();
		list.add(new JTableListColumn("视图名称", "name", 120));
		DgCommonQuery.setTableColumns(list);
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		DataImportAction dataImportAction = (DataImportAction) CommonVars
				.getApplicationContext().getBean("dataImportAction");
		List list1 = dataImportAction.findDBView(
				new Request(CommonVars.getCurrUser()), root);
		dgCommonQuery.setDataSource(list1);
		dgCommonQuery.setTitle("选择源数据表");
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 公共过滤
	 * 
	 * @param cbbFilterField
	 * @param tfFilterValue
	 * @param btnQuery
	 * @param tableModel
	 */
	public void addCommonFilter(final JComboBox cbbFilterField,
			final JTextField tfFilterValue, JButton btnQuery,
			final JTableListModel tableModel) {
		cbbFilterField.removeAllItems();
		int columnCount = tableModel.getColumnCount();
		for (int i = 1; i < columnCount; i++) {
			cbbFilterField.addItem(tableModel.getColumnName(i));
		}
		cbbFilterField.setSelectedIndex(0);
		ActionListener[] actionListeners = btnQuery.getActionListeners();
		int count = actionListeners.length;
		for (int i = count - 1; i >= 0; i--) {
			btnQuery.removeActionListener(actionListeners[i]);
		}
		btnQuery.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent e) {
				int col = cbbFilterField.getSelectedIndex() + 1;
				tableModel.filter(col, tfFilterValue.getText(), false);
			}
		});
	}

	public Object getCustomArea(Object object) {
		// List list = new Vector();
		// list.add(new JTableListColumn("aaaaaaaaaa", "code", 100));
		// list.add(new JTableListColumn("bbbbbb", "name", 150));
		// DgCommonQuery.setTableColumns(list);
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(CustomBaseList.getInstance().getCountrys());
		dgCommonQuery.setSelectedRow(object);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	public Object getCustomArea() {
		return getCustomArea(null);
	}

	public List getCustomAreas() {
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(CustomBaseList.getInstance().getCountrys());
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 获得物料对象-->关封申请单
	 */
	public List findMaterielByCustomsEnvelopRequestBillType(
			String materielType, String customsEnvelopRequestId) {
		List dataSource = null;
		CommonBaseCodeAction commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		dataSource = commonBaseCodeAction
				.findMaterielByCustomsEnvelopRequestBillType(new Request(
						CommonVars.getCurrUser(), true), materielType,
						customsEnvelopRequestId);
		String dialogTitle = "";
		if (materielType.equals(MaterielType.FINISHED_PRODUCT)) {
			dialogTitle = "进出口物料类别---成品";
		} else if (materielType.equals(MaterielType.MATERIEL)) {
			dialogTitle = "进出口物料类别---料件";
		}
		return getMaterielByType(dataSource, dialogTitle);
	}

	/**
	 * 获得物料对象-->转厂（结转单据)
	 */
	public List findMaterielByTransferFactoryBillType(String materielType) {
		List dataSource = null;
		CommonBaseCodeAction commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		dataSource = commonBaseCodeAction
				.findMaterielByTransferFactoryBillType(
						new Request(CommonVars.getCurrUser(), true),
						materielType);
		String dialogTitle = "";
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		if (materielType.equals(MaterielType.FINISHED_PRODUCT)) {
			dialogTitle = "进出口物料类别---成品";
		} else if (materielType.equals(MaterielType.MATERIEL)) {
			dialogTitle = "进出口物料类别---料件";
		}
		return getMaterielByType(dataSource, dialogTitle);
	}

	/**
	 * 获得物料对象-->转厂（结转初始化单据)
	 */
	public List findMaterielByTransferFactoryInitBillType(String materielType,
			String initBillId) {
		List dataSource = new ArrayList();
		CommonBaseCodeAction commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		dataSource = commonBaseCodeAction
				.findMaterielByTransferFactoryInitBillType(new Request(
						CommonVars.getCurrUser(), true), materielType,
						initBillId);
		String dialogTitle = "";
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		if (materielType.equals(MaterielType.FINISHED_PRODUCT)) {
			dialogTitle = "进出口物料类别---成品";
		} else if (materielType.equals(MaterielType.MATERIEL)) {
			dialogTitle = "进出口物料类别---料件";
		}
		return getMaterielByType(dataSource, dialogTitle);
	}

	/**
	 * 获得物料对象-->显示进出口商品信息中没有备案的数据
	 */
	public List getMaterielByList(List dataSource) {
		String dialogTitle = "进出口商品信息中没有备案的数据";
		return getMaterielByType(dataSource, dialogTitle);
	}

	/**
	 * 获得物料对象
	 */
	public List getMaterielNotInInnerMerge(String materielType) {
		List list = new Vector();
		list.add(new JTableListColumn("料号", "ptNo", 100));
		list.add(new JTableListColumn("物料编码", "complex.code", 80));
		list.add(new JTableListColumn("物料名称", "ptName", 100));
		list.add(new JTableListColumn("型号规格", "ptSpec", 100));
		list.add(new JTableListColumn("单位", "calUnit.name", 50));
		list.add(new JTableListColumn("单价", "ptPrice", 50));
		list.add(new JTableListColumn("净重", "ptNetWeight", 50));
		DgCommonQuery.setTableColumns(list);
		CommonBaseCodeAction commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		List dataSource = commonBaseCodeAction.findMaterielNotInInnerMerge(
				new Request(CommonVars.getCurrUser(), true), materielType);
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
	 * 获得物料对象
	 */
	public Object getMateriel() {
		List list = new Vector();
		list.add(new JTableListColumn("料号", "ptNo", 100));
		list.add(new JTableListColumn("物料编码", "complex.code", 80));
		list.add(new JTableListColumn("物料名称", "ptName", 100));
		list.add(new JTableListColumn("型号规格", "ptSpec", 100));
		list.add(new JTableListColumn("单位", "calUnit.name", 50));
		list.add(new JTableListColumn("单价", "ptPrice", 50));
		list.add(new JTableListColumn("净重", "ptNetWeight", 50));
		DgCommonQuery.setTableColumns(list);
		CommonBaseCodeAction commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		List dataSource = commonBaseCodeAction.findMateriel(new Request(
				CommonVars.getCurrUser(), true));
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("物料查询");
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 获得物料对象
	 */
	public List getMaterielByType(List dataSource, String dialogTitle) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("料号", "materiel.ptNo", 100));
		list.add(new JTableListColumn("序号", "seqNum", 50));
		list.add(new JTableListColumn("商品编码", "materiel.complex.code", 80));
		list.add(new JTableListColumn("商品名称", "materiel.factoryName", 100));
		list.add(new JTableListColumn("型号规格", "materiel.factorySpec", 100));
		list.add(new JTableListColumn("单位", "materiel.calUnit.name", 50));
		list.add(new JTableListColumn("单价", "materiel.ptPrice", 50));
		list.add(new JTableListColumn("净重", "materiel.ptNetWeight", 50));
		list.add(new JTableListColumn("内部归并备案", "isMemo", 100));
		list.add(new JTableListColumn("帐册是否备案", "isMemo", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle(dialogTitle);
		JTable table = dgCommonQuery.getJTable();
		dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				JTable table = dgCommonQuery.getJTable();
				table.getColumnModel().getColumn(9)
						.setCellRenderer(new checkBoxRenderer());
				table.getColumnModel().getColumn(10)
						.setCellRenderer(new checkBoxRenderer());
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
	 * 获得对照表的物料对象
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public List getMaterielByTypeBcus(String title, final String materielType,
			final String billId) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("料号", "materiel.ptNo", 100));
		list.add(new JTableListColumn("序号", "seqNum", 50, Integer.class));
		list.add(new JTableListColumn("商品编码", "materiel.complex.code", 80));
		list.add(new JTableListColumn("商品名称", "materiel.factoryName", 100));
		list.add(new JTableListColumn("型号规格", "materiel.factorySpec", 100));
		list.add(new JTableListColumn("单位", "materiel.calUnit.name", 50));

		list.add(new JTableListColumn("备案商品名称", "afterTenName", 100));
		list.add(new JTableListColumn("备案型号规格", "afterSpec", 100));
		list.add(new JTableListColumn("备案单位", "afterUnit", 50));

		list.add(new JTableListColumn("单价", "materiel.ptPrice", 50));
		list.add(new JTableListColumn("净重", "materiel.ptNetWeight", 50));
		list.add(new JTableListColumn("内部归并备案", "isMemo", 100));
		list.add(new JTableListColumn("帐册是否备案", "isEmsRecords", 100));
		NewDgCommonQueryPage.setTableColumns(list);
		NewDgCommonQueryPage newDgCommonQuery = new NewDgCommonQueryPage() {
			public JCheckBox jCheckBox = null;

			boolean isShowAll = false;

			@Override
			// 设置9与10列为checkbox
			public void doSomethingBeforeVisable(JTable table) {
				table.getColumnModel().getColumn(12)
						.setCellRenderer(new checkBoxRenderer());
				table.getColumnModel().getColumn(13)
						.setCellRenderer(new checkBoxRenderer());
			}

			// 查询全部物料
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				if (isShowAll) {
					List list = commonBaseCodeAction
							.findMaterielByImpExpRequestBillType(new Request(
									CommonVars.getCurrUser()), materielType,
									billId, false);
					System.out.println(getJCheckBox().isSelected() + ":---->a"
							+ list.size());
					return list;
				} else {
					CompanyOther other = CommonVars.getOther();
					boolean isFilter = false;
					if (Boolean.TRUE.equals(other.getIsFilter())) {
						isFilter = true;
					}
					List list = commonBaseCodeAction
							.findMaterielByImpExpRequestBillType(new Request(
									CommonVars.getCurrUser()), materielType,
									billId, isFilter);
					System.out.println(getJCheckBox().isSelected() + ":---->b"
							+ list.size());
					return list;
				}
			}

			// 根据查询条件，点击“查询”按钮查询物料
			@Override
			public List findMaterielByPara(int index, int length,
					String property, Object value, boolean isLike) {
				List list = new ArrayList();
				List newList = new ArrayList();
				if (property != null) {
					if (isShowAll) {
						list = commonBaseCodeAction
								.findMaterielByImpExpRequestBillType(
										new Request(CommonVars.getCurrUser()),
										materielType, billId, false);
						System.out.println(getJCheckBox().isSelected()
								+ ":---->a" + list.size());
					} else {
						CompanyOther other = CommonVars.getOther();
						boolean isFilter = false;
						if (Boolean.TRUE.equals(other.getIsFilter())) {
							isFilter = true;
						}
						list = commonBaseCodeAction
								.findMaterielByImpExpRequestBillType(
										new Request(CommonVars.getCurrUser()),
										materielType, billId, isFilter);
						System.out.println(getJCheckBox().isSelected()
								+ ":---->b" + list.size());
					}
					if (value != null) {
						if ("materiel.ptNo".equals(property)) {
							for (int i = 0; i < list.size(); i++) {
								TempMateriel tempMateriel = (TempMateriel) list
										.get(i);
								if (tempMateriel.getMateriel().getPtNo()
										.contains(value.toString())) {
									newList.add(tempMateriel);
								}
							}
						} else if ("seqNum".equals(property)) {
							for (int i = 0; i < list.size(); i++) {
								TempMateriel tempMateriel = (TempMateriel) list
										.get(i);
								if (tempMateriel.getSeqNum() != null) {
									if (tempMateriel.getSeqNum().toString()
											.contains(value.toString())) {
										newList.add(tempMateriel);
									}
								}

							}
						} else if ("materiel.complex.code".equals(property)) {
							for (int i = 0; i < list.size(); i++) {
								TempMateriel tempMateriel = (TempMateriel) list
										.get(i);
								if (tempMateriel.getMateriel() != null) {
									if (tempMateriel.getMateriel().getComplex() != null) {
										if (tempMateriel.getMateriel()
												.getComplex().getCode()
												.contains(value.toString())) {
											newList.add(tempMateriel);
										}
									}
								}
							}
						} else if ("materiel.factoryName".equals(property)) {
							for (int i = 0; i < list.size(); i++) {
								TempMateriel tempMateriel = (TempMateriel) list
										.get(i);
								if (tempMateriel.getMateriel().getFactoryName() != null) {
									if (tempMateriel.getMateriel()
											.getFactoryName()
											.contains(value.toString())) {
										newList.add(tempMateriel);
									}
								}

							}
						} else if ("materiel.factorySpec".equals(property)) {
							for (int i = 0; i < list.size(); i++) {
								TempMateriel tempMateriel = (TempMateriel) list
										.get(i);
								if (tempMateriel.getMateriel().getFactorySpec() != null) {
									if (tempMateriel.getMateriel()
											.getFactorySpec()
											.contains(value.toString())) {
										newList.add(tempMateriel);
									}
								}
							}
						} else if ("materiel.calUnit.name".equals(property)) {
							for (int i = 0; i < list.size(); i++) {
								TempMateriel tempMateriel = (TempMateriel) list
										.get(i);
								if (tempMateriel.getMateriel().getCalUnit() != null) {
									if (tempMateriel.getMateriel().getCalUnit()
											.getName()
											.contains(value.toString())) {
										newList.add(tempMateriel);
									}
								}
							}
						} else if ("afterTenName".equals(property)) {
							for (int i = 0; i < list.size(); i++) {
								TempMateriel tempMateriel = (TempMateriel) list
										.get(i);
								if (tempMateriel.getAfterTenName() != null) {
									if (tempMateriel.getAfterTenName() != null) {
										if (tempMateriel.getAfterTenName()
												.contains(value.toString())) {
											newList.add(tempMateriel);
										}
									}
								}
							}
						} else if ("afterSpec".equals(property)) {
							for (int i = 0; i < list.size(); i++) {
								TempMateriel tempMateriel = (TempMateriel) list
										.get(i);
								if (tempMateriel.getAfterSpec() != null) {
									if (tempMateriel.getAfterSpec().contains(
											value.toString())) {
										newList.add(tempMateriel);
									}
								}
							}
						} else if ("materiel.ptPrice".equals(property)) {
							for (int i = 0; i < list.size(); i++) {
								TempMateriel tempMateriel = (TempMateriel) list
										.get(i);
								if (tempMateriel.getMateriel().getPtPrice() != null) {
									if (tempMateriel.getMateriel().getPtPrice()
											.toString()
											.contains(value.toString())) {
										newList.add(tempMateriel);
									}
								}
							}
						} else if ("afterUnit".equals(property)) {
							for (int i = 0; i < list.size(); i++) {
								TempMateriel tempMateriel = (TempMateriel) list
										.get(i);
								if (tempMateriel.getAfterUnit().contains(
										value.toString())) {
									newList.add(tempMateriel);
								}
							}
						} else if ("materiel.ptNetWeight".equals(property)) {
							for (int i = 0; i < list.size(); i++) {
								TempMateriel tempMateriel = (TempMateriel) list
										.get(i);
								if (tempMateriel.getMateriel().getPtNetWeight() != null) {
									if (tempMateriel.getMateriel()
											.getPtNetWeight().toString()
											.contains(value.toString())) {
										newList.add(tempMateriel);
									}
								}
							}
						} else if ("isMemo".equals(property)) {
							for (int i = 0; i < list.size(); i++) {
								TempMateriel tempMateriel = (TempMateriel) list
										.get(i);
								if (tempMateriel.getIsMemo().toString()
										.contains(value.toString())) {
									newList.add(tempMateriel);
								}
							}
						} else if ("isEmsRecords".equals(property)) {
							for (int i = 0; i < list.size(); i++) {
								TempMateriel tempMateriel = (TempMateriel) list
										.get(i);
								if (tempMateriel.getIsEmsRecords().toString()
										.contains(value.toString())) {
									newList.add(tempMateriel);
								}
							}
						}
					} else {
						newList = list;
					}
				}
				return newList;
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
					jCheckBox.setVisible(false);
					CompanyOther other = CommonVars.getOther();
					jCheckBox.setSelected(Boolean.TRUE.equals(other
							.getIsFilter()));
					jCheckBox
							.addActionListener(new java.awt.event.ActionListener() {
								public void actionPerformed(
										java.awt.event.ActionEvent e) {
									isShowAll = !isShowAll;
									newpnCommonQueryPage.setInitState();
									doSomethingBeforeVisable(getJTable());
								}
							});
				}
				return jCheckBox;
			}
		};
		newDgCommonQuery.setTitle(title);
		newDgCommonQuery.setSize(820, 457);
		newDgCommonQuery.setSingleResult(false);

		newDgCommonQuery.setVisible(true);
		if (newDgCommonQuery.isOk()) {
			return newDgCommonQuery.getReturnList();
		}
		return null;

	}

	/**
	 * 获得对照表的物料对象(SMD)
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public List getMaterielByTypeBcusSMD(String title,
			final String materielType, final String billId) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("料号", "materiel.ptNo", 100));
		list.add(new JTableListColumn("序号", "seqNum", 50, Integer.class));
		list.add(new JTableListColumn("商品编码", "materiel.complex.code", 80));
		list.add(new JTableListColumn("商品名称", "materiel.factoryName", 100));
		list.add(new JTableListColumn("型号规格", "materiel.factorySpec", 100));
		list.add(new JTableListColumn("单位", "materiel.calUnit.name", 50));

		list.add(new JTableListColumn("备案商品名称", "afterTenName", 100));
		list.add(new JTableListColumn("备案型号规格", "afterSpec", 100));
		list.add(new JTableListColumn("备案单位", "afterUnit", 50));

		list.add(new JTableListColumn("单价", "materiel.ptPrice", 50));
		list.add(new JTableListColumn("净重", "materiel.ptNetWeight", 50));
		list.add(new JTableListColumn("内部归并备案", "isMemo", 100));
		list.add(new JTableListColumn("帐册是否备案", "isEmsRecords", 100));
		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			public JCheckBox jCheckBox = null;

			boolean isShowAll = true;

			@Override
			// 设置9与10列为checkbox
			public void doSomethingBeforeVisable(JTable table) {
				table.getColumnModel().getColumn(12)
						.setCellRenderer(new checkBoxRenderer());
				table.getColumnModel().getColumn(13)
						.setCellRenderer(new checkBoxRenderer());
			}

			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				List list = null;
				// if(value==null || value.equals("")) return list;

				if (isShowAll) {
					list = commonBaseCodeAction
							.findMaterielByImpExpRequestBillType(new Request(
									CommonVars.getCurrUser()), materielType,
									billId, false);
					System.out.println(getJCheckBox().isSelected() + ":---->a"
							+ list.size());
					return list;
				} else {
					list = commonBaseCodeAction
							.findMaterielByImpExpRequestBillType(new Request(
									CommonVars.getCurrUser()), materielType,
									billId, true);
					System.out.println(getJCheckBox().isSelected() + ":---->b"
							+ list.size());
					return list;
				}
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
					jCheckBox.setSelected(false);
					jCheckBox
							.addActionListener(new java.awt.event.ActionListener() {
								public void actionPerformed(
										java.awt.event.ActionEvent e) {
									isShowAll = !isShowAll;
									pnCommonQueryPage.setInitState();
									doSomethingBeforeVisable(getJTable());
								}
							});
				}
				return jCheckBox;
			}
		};
		dgCommonQuery.setTitle(title);
		dgCommonQuery.setSize(820, 457);
		DgCommonQueryPage.setSingleResult(false);

		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;

	}

	/**
	 * render table JchcckBox 列
	 */
	class checkBoxRenderer extends DefaultTableCellRenderer {
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

	/**
	 * 获得电子帐册物料对象在关封中商品信息的过滤
	 */
	public List getEmsMateriel(EmsHeadH2k emsHeadH2k, String parentId) {
		List dataSource = null;
		FptManageAction fptManageAction = (FptManageAction) CommonVars
				.getApplicationContext().getBean("fptManageAction");
		dataSource = fptManageAction.findEmsMateriel(
				new Request(CommonVars.getCurrUser(), true), emsHeadH2k,
				parentId);
		List list = new Vector();
		list.add(new JTableListColumn("成品序号", "seqNum", 50));
		list.add(new JTableListColumn("商品编码", "complex.code", 80));
		list.add(new JTableListColumn("商品名称", "name", 100));
		list.add(new JTableListColumn("型号规格", "spec", 100));
		list.add(new JTableListColumn("单位", "unit.name", 50));
		list.add(new JTableListColumn("币制", "curr.name", 50));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("电子帐册物料信息");
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 获得电子帐册成品在关封中商品信息的过滤
	 */
	public List getEmsFinishedProduct(EmsHeadH2k emsHeadH2k, String parentId) {
		List dataSource = null;
		FptManageAction fptManageAction = (FptManageAction) CommonVars
				.getApplicationContext().getBean("fptManageAction");
		dataSource = fptManageAction.findEmsFinishedProduct(new Request(
				CommonVars.getCurrUser(), true), emsHeadH2k, parentId);
		List list = new Vector();
		list.add(new JTableListColumn("成品序号", "seqNum", 50));
		list.add(new JTableListColumn("商品编码", "complex.code", 80));
		list.add(new JTableListColumn("商品名称", "name", 100));
		list.add(new JTableListColumn("型号规格", "spec", 100));
		list.add(new JTableListColumn("单位", "unit.name", 50));
		list.add(new JTableListColumn("币制", "curr.name", 50));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("电子帐册成品信息");
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 获得车架对象
	 */
	public Object getSrtTj(Object obj) {
		List list = new Vector();
		list.add(new JTableListColumn("编号", "code", 50));
		list.add(new JTableListColumn("名称", "name", 80));
		list.add(new JTableListColumn("英文名称", "bracketEnglishName", 80));
		list.add(new JTableListColumn("类型", "tjType", 80));
		list.add(new JTableListColumn("重量", "tjWeight", 80));
		DgCommonQuery.setTableColumns(list);
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(CustomBaseList.getInstance().getSrtTjs());
		dgCommonQuery.setTitle("选择车架型号");
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 获得车架对象
	 */
	public Object getSrtTj() {
		return getSrtTj(null);
	}

	/**
	 * 获得仓库对象
	 */
	public Object getDepot(Object obj) {
		MaterialManageAction materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource((materialManageAction
				.findWareSet(new Request(CommonVars.getCurrUser(), true))));
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 获得仓库对象
	 */
	public Object getDepot() {
		return getDepot(null);
	}

	/**
	 * 返回所有客户/供应商/合作伙伴资料
	 */
	public Object getCustomer(Object obj) {
		MaterialManageAction materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource((materialManageAction
				.findScmCocs(new Request(CommonVars.getCurrUser(), true))));
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 返回所有客户/供应商/合作伙伴资料
	 */
	public Object getCustomer() {
		return getCustomer(null);
	}

	/**
	 * 获得物流客户/供应商外键--关务海关注册公司对象
	 */
	public Object getCustomBrief(Object object) {
		// DgCommonQuery dgCommonQuery = new DgCommonQuery();
		// dgCommonQuery.setDataSource(CustomBaseList.getInstance().getBriefs());
		// dgCommonQuery.setSelectedRow(object);
		// dgCommonQuery.setVisible(true);
		// if (dgCommonQuery.isOk()) {
		// return dgCommonQuery.getReturnValue();
		// }
		// return null;
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
				return customBaseAction.findPageBriefList(new Request(
						CommonVars.getCurrUser(), true), index, length,
						property, value, isLike);
			}
		};

		dgCommonQuery.setTitle("海关注册公司");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	public Object getCustomBrief() {
		return getCustomBrief(null);
	}

	public List getCustomBriefs() {
		// DgCommonQuery dgCommonQuery = new DgCommonQuery();
		// dgCommonQuery.setDataSource(CustomBaseList.getInstance().getBriefs());
		// dgCommonQuery.setVisible(true);
		// if (dgCommonQuery.isOk()) {
		// return dgCommonQuery.getReturnList();
		// }
		// return null;
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
				return customBaseAction.findPageBriefList(new Request(
						CommonVars.getCurrUser(), true), index, length,
						property, value, isLike);
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

	/*
	 * 获得物流计量单位外键--关务计量单位对象
	 */
	public Object getCustomUnit(Object object) {
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(CustomBaseList.getInstance().getUnits());
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	public Object getCustomUnit() {
		return getCustomUnit(null);
	}

	public List getCustomUnits() {
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(CustomBaseList.getInstance().getUnits());
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/*
	 * 获得物流币制外键--关务币制对象
	 */
	public Object getCustomCurr(Object object) {
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(CustomBaseList.getInstance().getCurrs());
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	public Object getCustomCurr() {
		return getCustomCurr(null);
	}

	public List getCustomCurrs() {
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(CustomBaseList.getInstance().getCurrs());
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 获得海关商品编码complex对象//联网监管//bcs使用
	 */
	public Object getComplex(Object obj) {
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
				CustomBaseAction customBaseAction = (CustomBaseAction) CommonVars
						.getApplicationContext().getBean("customBaseAction");
				return customBaseAction.findComplex(
						new Request(CommonVars.getCurrUser(), true), index,
						length, property, value, isLike);
			}
		};

		dgCommonQuery.setTitle("商品编码查询");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}

	/**
	 * 获得海关商品编码complex对象//联网监管//bcs使用
	 */
	public Object getWrap() {
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
				return customBaseAction.findWrap();
			}
		};

		dgCommonQuery.setTitle("包装方式查询");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}

	/**
	 * 获得 工厂BOM管理版本资料
	 */
	public Object getEnterpriseBomVersion(final String parentNo,
			final int bomStructureType) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("料号", "parentNo", 100));
		list.add(new JTableListColumn("版本", "version", 150));
		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			private static final long serialVersionUID = 1L;

			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				MaterialManageAction action = (MaterialManageAction) CommonVars
						.getApplicationContext()
						.getBean("materialManageAction");
				return action.findEnterpriseBomVersion(
						new Request(CommonVars.getCurrUser(), true), parentNo,
						bomStructureType);
			}
		};

		dgCommonQuery.setTitle("工厂BOM管理版本资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}

	/**
	 * Bcs特殊报关单来源于报关商品
	 * 
	 * @param obj
	 * @return
	 */
	public Object getComplexBcsTenInnerMerge(final boolean isImport) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();

		list.add(new JTableListColumn("编码", "hsAfterComplex.code", 100));
		list.add(new JTableListColumn("归并序号", "hsAfterTenMemoNo", 50));
		list.add(new JTableListColumn("报关名称", "hsAfterMaterielTenName", 150));
		list.add(new JTableListColumn("报关规格", "hsAfterMaterielTenSpec", 150));
		list.add(new JTableListColumn("报关单位", "hsAfterLegalUnit.name", 80));
		list.add(new JTableListColumn("第一法定单位",
				"hsAfterComplex.firstUnit.name", 100));
		list.add(new JTableListColumn("第二法定单位",
				"hsAfterComplex.secondUnit.name", 100));

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
				return customBaseAction.findComplexBcsTenInnerMerge(
						new Request(CommonVars.getCurrUser(), true), index,
						length, property, value, isLike, isImport);
			}
		};

		dgCommonQuery.setTitle("报关商品资料查询");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}

	/**
	 * 获得各个模块的归并资料
	 */
	public Object getInnerDateForSpeFix(final int projectType,
			final boolean isImport) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("编码", "hsAfterComplex.code", 100));
		list.add(new JTableListColumn("归并序号", "hsAfterTenMemoNo", 50));
		list.add(new JTableListColumn("报关名称", "hsAfterMaterielTenName", 150));
		list.add(new JTableListColumn("报关规格", "hsAfterMaterielTenSpec", 150));
		list.add(new JTableListColumn("报关单位", "hsAfterLegalUnit.name", 80));
		list.add(new JTableListColumn("第一法定单位",
				"hsAfterComplex.firstUnit.name", 100));
		list.add(new JTableListColumn("第二法定单位",
				"hsAfterComplex.secondUnit.name", 100));
		// if (projectType == ProjectType.BCS) {
		// list.add(new JTableListColumn("编码", "hsAfterComplex.code", 100));
		// list.add(new JTableListColumn("归并序号", "hsAfterTenMemoNo", 50));
		// list.add(new JTableListColumn("报关名称", "hsAfterMaterielTenName",
		// 150));
		// list.add(new JTableListColumn("报关规格", "hsAfterMaterielTenSpec",
		// 150));
		// list.add(new JTableListColumn("报关单位", "hsAfterLegalUnit.name", 80));
		// list.add(new JTableListColumn("第一法定单位",
		// "hsAfterComplex.firstUnit.name", 100));
		// list.add(new JTableListColumn("第二法定单位",
		// "hsAfterComplex.secondUnit.name", 100));
		//
		// } else if (projectType == ProjectType.BCUS) {
		// list.add(new JTableListColumn("编码", "hsAfterComplex.code", 100));
		// list.add(new JTableListColumn("备案序号", "hsAfterTenMemoNo", 50));
		// list
		// .add(new JTableListColumn("报关名称", "hsAfterMaterielTenName",
		// 150));
		// list
		// .add(new JTableListColumn("报关规格", "hsAfterMaterielTenSpec",
		// 150));
		// list.add(new JTableListColumn("报关单位", "hsAfterLegalUnit.name", 80));
		// list.add(new JTableListColumn("第一法定单位",
		// "hsAfterComplex.firstUnit.name", 100));
		// list.add(new JTableListColumn("第二法定单位",
		// "hsAfterComplex.secondUnit.name", 100));
		// } else {
		// list.add(new JTableListColumn("编码","tenComplex.code", 100));
		// list.add(new JTableListColumn("归并序号","tenSeqNum", 50));
		// list.add(new JTableListColumn("报关名称","tenPtName", 150));
		// list.add(new JTableListColumn("报关规格","tenPtSpec", 150));
		// list.add(new JTableListColumn("报关单位","tenUnit.name", 80));
		// list.add(new JTableListColumn("第一法定单位","tenComplex.firstUnit.name",
		// 100));
		// list.add(new JTableListColumn("第二法定单位","tenComplex.secondUnit.name",
		// 100));
		// }

		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				EncAction encAction = (EncAction) CommonVars
						.getApplicationContext().getBean("encAction");
				// System.out.println("------------------"
				// + encAction.getInnerDateForSpeFix(
				// (new Request(CommonVars.getCurrUser(), true)),
				// projectType, index, length, property, value,
				// isLike).size());
				return encAction.getInnerDateForSpeFix(
						(new Request(CommonVars.getCurrUser(), true)),
						projectType, index, length, property, value, isLike,
						isImport);
			}
		};

		dgCommonQuery.setTitle("归并资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}

	public Object getComplex() {
		return getComplex(null);
	}

	/*
	 * 获得物流供应商scmcoc对象
	 */
	public Object getScmCoc(Object object) {
		MaterialManageAction materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		// dgCommonQuery
		// .setDataSource(materialManageAction
		// .findScmManufacturer(new Request(CommonVars
		// .getCurrUser(), true)));
		dgCommonQuery.setDataSource(materialManageAction
				.findScmCocs(new Request(CommonVars.getCurrUser(), true)));
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	public Object getScmCoc() {
		return getScmCoc(null);
	}

	/*
	 * 获得物流类别scmcoi对象
	 */
	public Object getScmCoi(Object object) {
		MaterialManageAction materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(materialManageAction
				.findScmCois(new Request(CommonVars.getCurrUser(), true)));
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	public Object getScmCoi() {
		return getScmCoi(null);
	}

	/*
	 * 获得物流单位calunit对象
	 */
	public Object getCalUnit(Object object) {
		MaterialManageAction materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(materialManageAction
				.findCalUnit(new Request(CommonVars.getCurrUser(), true)));
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	public Object getCalUnit() {
		return getCalUnit(null);
	}

	/*
	 * 获得关务地区代码District对象
	 */
	public Object getDistrict(Object object) {
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(CustomBaseList.getInstance().getDistrict());
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	public Object getDistrict() {
		return getDistrict(null);
	}

	/*
	 * 获得关务外经委RedDep对象
	 */
	public Object getRedDep(Object object) {
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(CustomBaseList.getInstance().getRedDeps());
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	public Object getRedDep() {
		return getRedDep(true);
	}

	/*
	 * 获得关务外经委RedDep对象
	 */
	public Object getApplicationMode(Object object) {
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(CustomBaseList.getInstance().getDModes());
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	public Object getApplicationMode() {
		return getApplicationMode(true);
	}

	/*
	 * 获得关务海关关区Customs对象
	 */
	public Object getCustoms(boolean single) {
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		DgCommonQuery.setSingleResult(single);
		dgCommonQuery.setDataSource(CustomBaseList.getInstance().getCustoms());
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			if (single) {
				return dgCommonQuery.getReturnValue();
			} else {
				return dgCommonQuery.getReturnList();
			}
		}
		return null;
	}

	public Object getCustoms() {
		return getCustoms(true);
	}

	/***************************************************************************
	 * 经营范围--得到归并料件4码--在内部归并表中 (料件)
	 **************************************************************************/
	public Object getImg4(boolean single, EmsEdiTrHead emsEdiTrHead,
			String helfType) {
		CommonBaseCodeAction commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List list = null;
		if (helfType.equals("")) {
			list = commonBaseCodeAction.findInnerMergeDataByType4(new Request(
					CommonVars.getCurrUser(), true), MaterielType.MATERIEL,
					emsEdiTrHead, helfType);
		} else {
			list = commonBaseCodeAction.findInnerMergeDataByType4(new Request(
					CommonVars.getCurrUser(), true),
					MaterielType.SEMI_FINISHED_PRODUCT, emsEdiTrHead, helfType);
		}
		List emsEdiTrImgs = new Vector();
		for (int i = 0; i < list.size(); i++) {
			EmsEdiTrImg emsEdiTrImg = new EmsEdiTrImg();
			emsEdiTrImg.setPtNo(Integer.valueOf((((Object[]) list.get(i))[0])
					.toString()));
			emsEdiTrImg.setComplex((String) ((Object[]) list.get(i))[1]);
			emsEdiTrImg.setName((String) ((Object[]) list.get(i))[2]);
			emsEdiTrImg.setNote("");
			emsEdiTrImgs.add(emsEdiTrImg);
		}

		dgCommonQuery.setDataSource(emsEdiTrImgs);
		List tableColumns = new Vector();
		tableColumns.add(new JTableListColumn("料件序号", "ptNo", 50));
		tableColumns.add(new JTableListColumn("商品编码", "complex", 80));
		tableColumns.add(new JTableListColumn("料件名称", "name", 100));
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(single);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			if (single) {
				return dgCommonQuery.getReturnValue();
			} else {
				return dgCommonQuery.getReturnList();
			}
		}
		return null;
	}

	/***************************************************************************
	 * 经营范围--得到归并成品4码--在内部归并表中 （成品）
	 **************************************************************************/
	public Object getExg4(boolean single, EmsEdiTrHead esmEdiTrHead,
			String helfType) {
		CommonBaseCodeAction commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List list = null;
		if (helfType.equals("")) {
			list = commonBaseCodeAction.findInnerMergeDataByType4(new Request(
					CommonVars.getCurrUser(), true),
					MaterielType.FINISHED_PRODUCT, esmEdiTrHead, helfType);
		} else {
			list = commonBaseCodeAction.findInnerMergeDataByType4(new Request(
					CommonVars.getCurrUser(), true),
					MaterielType.SEMI_FINISHED_PRODUCT, esmEdiTrHead, helfType);
		}
		List emsEdiTrExgs = new Vector();
		for (int i = 0; i < list.size(); i++) {
			EmsEdiTrExg emsEdiTrExg = new EmsEdiTrExg();
			emsEdiTrExg.setPtNo(new Integer((((Object[]) list.get(i))[0])
					.toString()));
			emsEdiTrExg.setComplex((String) ((Object[]) list.get(i))[1]);
			emsEdiTrExg.setName((String) ((Object[]) list.get(i))[2]);
			emsEdiTrExg.setNote("");
			emsEdiTrExgs.add(emsEdiTrExg);
		}

		dgCommonQuery.setDataSource(emsEdiTrExgs);
		List tableColumns = new Vector();
		tableColumns.add(new JTableListColumn("成品序号", "ptNo", 50));
		tableColumns.add(new JTableListColumn("商品编码", "complex", 80));
		tableColumns.add(new JTableListColumn("成品名称", "name", 100));
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(single);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			if (single) {
				return dgCommonQuery.getReturnValue();
			} else {
				return dgCommonQuery.getReturnList();
			}
		}
		return null;
	}

	public Object getEmsEdiMergerImgBefore(boolean single,
			final EmsEdiMergerHead emsHead) {
		List<JTableListColumn> tableColumns = new Vector<JTableListColumn>();
		tableColumns.add(new JTableListColumn("归并序号", "seqNum", 50,
				Integer.class));
		tableColumns.add(new JTableListColumn("商品货号", "ptNo", 120));
		tableColumns.add(new JTableListColumn("料件名称", "name", 100));
		tableColumns.add(new JTableListColumn("规格型号", "spec", 100));
		DgCommonQueryPage.setTableColumns(tableColumns);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CommonBaseCodeAction commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
						.getApplicationContext()
						.getBean("commonBaseCodeAction");
				long start = System.currentTimeMillis();
				List list = commonBaseCodeAction.findEmsEdiMergerImgBefore(
						new Request(CommonVars.getCurrUser(), true), emsHead);
				long end = System.currentTimeMillis();
				System.out.println("计算时间：" + (end - start));
				return list;
			}
		};
		DgCommonQueryPage.setSingleResult(single);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			if (single) {
				return dgCommonQuery.getReturnValue();
			} else {
				return dgCommonQuery.getReturnList();
			}
		}
		return null;
	}

	/***************************************************************************
	 * 归并关系--新增归并前料件--通过归并后料件--在内部归并表中
	 **************************************************************************/
	public Object getMergerImgBefore10(boolean single,
			final EmsEdiMergerImgAfter emsAfter, final EmsEdiMergerHead emsHead) {
		List<JTableListColumn> tableColumns = new Vector<JTableListColumn>();
		tableColumns.add(new JTableListColumn("归并序号", "seqNum", 50,
				Integer.class));
		tableColumns.add(new JTableListColumn("商品货号", "ptNo", 120));
		tableColumns.add(new JTableListColumn("料件名称", "name", 100));
		tableColumns.add(new JTableListColumn("规格型号", "spec", 100));
		DgCommonQueryPage.setTableColumns(tableColumns);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CommonBaseCodeAction commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
						.getApplicationContext()
						.getBean("commonBaseCodeAction");
				Integer seqNum = Integer.valueOf(emsAfter.getSeqNum());
				List list = commonBaseCodeAction
						.findInnerMergeDataAfterImgByType10(new Request(
								CommonVars.getCurrUser(), true),
								MaterielType.MATERIEL, seqNum, emsHead, index,
								length, property, value, isLike);
				List emsEdiMergers = new Vector();
				for (int i = 0; i < list.size(); i++) {
					EmsEdiMergerImgBefore emsEdiMerger = new EmsEdiMergerImgBefore();
					emsEdiMerger.setSeqNum(((InnerMergeData) list.get(i))
							.getHsAfterTenMemoNo());
					emsEdiMerger.setPtNo(((InnerMergeData) list.get(i))
							.getMateriel().getPtNo());
					emsEdiMerger.setComplex(((InnerMergeData) list.get(i))
							.getHsAfterComplex());
					emsEdiMerger.setName(((InnerMergeData) list.get(i))
							.getMateriel().getFactoryName());
					emsEdiMerger.setSpec(((InnerMergeData) list.get(i))
							.getMateriel().getFactorySpec());
					emsEdiMerger.setUnit(emsAfter.getUnit());// 与归并后单位相同
					/*
					 * emsEdiMerger.setLegalUnit(((InnerMergeData) list.get(i))
					 * .getHsAfterLegalUnit());
					 * emsEdiMerger.setLegalUnit2(((InnerMergeData) list.get(i))
					 * .getHsAfterSecondLegalUnit());
					 */
					emsEdiMerger.setInnerMergerData((InnerMergeData) list
							.get(i));
					emsEdiMergers.add(emsEdiMerger);
				}
				return emsEdiMergers;
			}
		};
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/***************************************************************************
	 * 归并关系--新增归并前成品--通过归并后成品--在内部归并表中
	 **************************************************************************/
	public Object getMergerExgBefore10(boolean single,
			final EmsEdiMergerExgAfter emsAfter, final EmsEdiMergerHead emsHead) {
		List<JTableListColumn> tableColumns = new Vector<JTableListColumn>();
		tableColumns.add(new JTableListColumn("归并序号", "seqNum", 50,
				Integer.class));
		tableColumns.add(new JTableListColumn("商品货号", "ptNo", 120));
		tableColumns.add(new JTableListColumn("成品名称", "name", 100));
		tableColumns.add(new JTableListColumn("规格型号", "spec", 100));
		DgCommonQueryPage.setTableColumns(tableColumns);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CommonBaseCodeAction commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
						.getApplicationContext()
						.getBean("commonBaseCodeAction");
				Integer seqNum = Integer.valueOf(emsAfter.getSeqNum());
				List list = commonBaseCodeAction
						.findInnerMergeDataAfterExgByType10(new Request(
								CommonVars.getCurrUser(), true),
								MaterielType.FINISHED_PRODUCT, seqNum, emsHead,
								index, length, property, value, isLike);
				List emsEdiMergers = new Vector();
				for (int i = 0; i < list.size(); i++) {
					EmsEdiMergerExgBefore emsEdiMerger = new EmsEdiMergerExgBefore();
					emsEdiMerger.setSeqNum(((InnerMergeData) list.get(i))
							.getHsAfterTenMemoNo());
					emsEdiMerger.setPtNo(((InnerMergeData) list.get(i))
							.getMateriel().getPtNo());
					emsEdiMerger.setComplex(((InnerMergeData) list.get(i))
							.getHsAfterComplex());
					emsEdiMerger.setName(((InnerMergeData) list.get(i))
							.getMateriel().getFactoryName());
					emsEdiMerger.setSpec(((InnerMergeData) list.get(i))
							.getMateriel().getFactorySpec());
					emsEdiMerger.setUnit(emsAfter.getUnit());// 与归并后单位相同
					/*
					 * emsEdiMerger.setLegalUnit(((InnerMergeData) list.get(i))
					 * .getHsAfterLegalUnit());
					 * emsEdiMerger.setLegalUnit2(((InnerMergeData) list.get(i))
					 * .getHsAfterSecondLegalUnit());
					 */
					System.out.println(((InnerMergeData) list.get(i))
							.getMateriel().getPtNo());
					emsEdiMerger.setInnerMergerData((InnerMergeData) list
							.get(i));
					emsEdiMergers.add(emsEdiMerger);
				}
				return emsEdiMergers;
			}
		};
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;

	}

	/***************************************************************************
	 * 归并关系--新增归并前成品--通过归并后成品--在内部归并表中
	 **************************************************************************/
	public Object getMergerExgBeforeTen(boolean single,
			final EmsEdiMergerExgAfter emsAfter, final EmsEdiMergerHead emsHead) {
		List<JTableListColumn> tableColumns = new Vector<JTableListColumn>();
		tableColumns.add(new JTableListColumn("归并序号", "seqNum", 50,
				Integer.class));
		tableColumns.add(new JTableListColumn("商品货号", "ptNo", 120));
		tableColumns.add(new JTableListColumn("成品名称", "name", 100));
		tableColumns.add(new JTableListColumn("规格型号", "spec", 100));
		DgCommonQueryPage.setTableColumns(tableColumns);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CommonBaseCodeAction commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
						.getApplicationContext()
						.getBean("commonBaseCodeAction");
				Integer seqNum = Integer.valueOf(emsAfter.getSeqNum());
				List list = commonBaseCodeAction
						.findInnerMergeDataAfterExgByType10(new Request(
								CommonVars.getCurrUser(), true),
								MaterielType.FINISHED_PRODUCT, seqNum, emsHead,
								index, length, property, value, isLike);
				List emsEdiMergers = new Vector();
				for (int i = 0; i < list.size(); i++) {
					EmsEdiMergerExgBefore emsEdiMerger = new EmsEdiMergerExgBefore();
					emsEdiMerger.setSeqNum(((InnerMergeData) list.get(i))
							.getHsAfterTenMemoNo());
					emsEdiMerger.setPtNo(((InnerMergeData) list.get(i))
							.getMateriel().getPtNo());
					emsEdiMerger.setComplex(((InnerMergeData) list.get(i))
							.getHsAfterComplex());
					emsEdiMerger.setName(((InnerMergeData) list.get(i))
							.getMateriel().getFactoryName());
					emsEdiMerger.setSpec(((InnerMergeData) list.get(i))
							.getMateriel().getFactorySpec());
					emsEdiMerger.setUnit(emsAfter.getUnit());// 与归并后单位相同
					/*
					 * emsEdiMerger.setLegalUnit(((InnerMergeData) list.get(i))
					 * .getHsAfterLegalUnit());
					 * emsEdiMerger.setLegalUnit2(((InnerMergeData) list.get(i))
					 * .getHsAfterSecondLegalUnit());
					 */
					System.out.println(((InnerMergeData) list.get(i))
							.getMateriel().getPtNo());
					emsEdiMerger.setInnerMergerData((InnerMergeData) list
							.get(i));
					emsEdiMergers.add(emsEdiMerger);
				}
				return emsEdiMergers;
			}
		};
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(false);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;

	}

	/***************************************************************************
	 * 归并关系--得到归并后料件--在内部归并表中 （料件）
	 **************************************************************************/
	public Object getMergerImg10(boolean single, EmsEdiTrHead emsEdiTrHead,
			EmsEdiMergerHead emsEdiMergerHead, String helfType) {
		CommonBaseCodeAction commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List list = null;
		if (helfType.equals("")) {
			list = commonBaseCodeAction.findInnerMergeDataByType10(new Request(
					CommonVars.getCurrUser(), true), MaterielType.MATERIEL,
					emsEdiTrHead, emsEdiMergerHead, helfType);
		} else {
			list = commonBaseCodeAction.findInnerMergeDataByType10(new Request(
					CommonVars.getCurrUser(), true),
					MaterielType.SEMI_FINISHED_PRODUCT, emsEdiTrHead,
					emsEdiMergerHead, helfType);
		}
		List emsEdiMergers = new Vector();
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			EmsEdiMergerImgAfter emsEdiMerger = new EmsEdiMergerImgAfter();
			emsEdiMerger.setSeqNum((Integer) (obj[0]));
			emsEdiMerger.setComplex(((Complex) obj[1]));
			emsEdiMerger.setName((String) obj[2]);
			emsEdiMerger.setSpec((String) obj[3]);
			emsEdiMerger.setUnit((Unit) obj[4]);

			emsEdiMerger.setLegalUnitGene((Double) obj[7]);
			emsEdiMerger.setLegalUnit2Gene((Double) obj[8]);
			emsEdiMerger.setWeigthUnitGene((Double) obj[9]);
			if ((Boolean) obj[10] != null) {
				emsEdiMerger.setIsMainImg((Boolean) obj[10]);
			}
			/*
			 * emsEdiMerger.setLegalUnit((Unit) obj[5]);
			 * emsEdiMerger.setLegalUnit2((Unit) obj[6]);
			 */
			emsEdiMerger.setMaxApprSpace(Double.valueOf(0));
			emsEdiMerger.setCurr(emsEdiTrHead.getCurr());
			emsEdiMergers.add(emsEdiMerger);
		}
		dgCommonQuery.setDataSource(emsEdiMergers);
		List tableColumns = new Vector();
		tableColumns.add(new JTableListColumn("归并序号", "seqNum", 50,
				Integer.class));
		tableColumns.add(new JTableListColumn("商品编码", "complex.code", 80));
		tableColumns.add(new JTableListColumn("料件名称", "name", 100));
		tableColumns.add(new JTableListColumn("规格型号", "spec", 100));
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(single);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			if (single) {
				return dgCommonQuery.getReturnValue();
			} else {
				return dgCommonQuery.getReturnList();
			}
		}
		return null;
	}

	/***************************************************************************
	 * 归并关系--得到归并后成品--在内部归并表中 （成品）
	 **************************************************************************/
	public Object getMergerExg10(boolean single, EmsEdiTrHead emsEdiTrHead,
			EmsEdiMergerHead emsEdiMergerHead, String helfType) {
		CommonBaseCodeAction commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List list = null;
		if (helfType.equals("")) {
			list = commonBaseCodeAction.findInnerMergeDataByType10(new Request(
					CommonVars.getCurrUser(), true),
					MaterielType.FINISHED_PRODUCT, emsEdiTrHead,
					emsEdiMergerHead, helfType);
			System.out.println("小凯测试,listt.size=" + list.size());
		} else {
			list = commonBaseCodeAction.findInnerMergeDataByType10(new Request(
					CommonVars.getCurrUser(), true),
					MaterielType.SEMI_FINISHED_PRODUCT, emsEdiTrHead,
					emsEdiMergerHead, helfType);
		}
		List emsEdiMergers = new Vector();
		List findExist = new Vector();
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			System.out.println("小凯测试,(Integer) (obj[0])" + (Integer) (obj[0]));
			findExist = commonBaseCodeAction.findEmsEdiMergerImgAfter(
					new Request(CommonVars.getCurrUser(), true),
					emsEdiMergerHead, (Integer) (obj[0]));
			System.out.println("小凯测试" + findExist.size());
			if (findExist.size() > 0)
				continue;
			EmsEdiMergerExgAfter emsEdiMerger = new EmsEdiMergerExgAfter();
			emsEdiMerger.setSeqNum((Integer) (obj[0]));
			emsEdiMerger.setComplex(((Complex) obj[1]));
			emsEdiMerger.setName((String) obj[2]);
			emsEdiMerger.setSpec((String) obj[3]);
			emsEdiMerger.setUnit((Unit) obj[4]);

			emsEdiMerger.setLegalUnitGene((Double) obj[7]);
			emsEdiMerger.setLegalUnit2Gene((Double) obj[8]);

			emsEdiMerger.setWeigthUnitGene((Double) obj[9]);
			/*
			 * emsEdiMerger.setLegalUnit((Unit) obj[5]);
			 * emsEdiMerger.setLegalUnit2((Unit) obj[6]);
			 */
			emsEdiMerger.setMaxApprSpace(Double.valueOf(0));
			emsEdiMerger.setCurr(emsEdiTrHead.getCurr());
			// emsEdiMerger.setEmsEdiMergerBefores(new HashSet());
			emsEdiMergers.add(emsEdiMerger);

		}
		dgCommonQuery.setDataSource(emsEdiMergers);
		List tableColumns = new Vector();
		tableColumns.add(new JTableListColumn("归并序号", "seqNum", 50,
				Integer.class));
		tableColumns.add(new JTableListColumn("商品编码", "complex.code", 80));
		tableColumns.add(new JTableListColumn("成品名称", "name", 100));
		tableColumns.add(new JTableListColumn("规格型号", "spec", 100));
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(single);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			if (single) {
				return dgCommonQuery.getReturnValue();
			} else {
				return dgCommonQuery.getReturnList();
			}
		}
		return null;
	}

	/***************************************************************************
	 * 归并关系新增BOM--得到归并前料件--来自归并前资料
	 **************************************************************************/
	public Object getMergerAfterImg(boolean single,
			EmsEdiMergerVersion emsEdiMergerVersion, EmsEdiMergerHead emsHead) {
		ManualDeclareAction manualdeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		DgCommonQuery dgCommonQuery = new DgCommonQuery();

		List list = manualdeclearAction.findEmsEdiMergerAfterImgToBom(
				new Request(CommonVars.getCurrUser(), true),
				emsEdiMergerVersion, emsHead);
		List emsEdiMergerAfterImgs = new Vector();
		for (int i = 0; i < list.size(); i++) {
			EmsEdiMergerExgBom emsEdiImgBom = new EmsEdiMergerExgBom();
			emsEdiImgBom.setPtNo(((EmsEdiMergerImgBefore) list.get(i))
					.getPtNo());
			emsEdiImgBom.setComplex(((EmsEdiMergerImgBefore) list.get(i))
					.getComplex());
			emsEdiImgBom.setName(((EmsEdiMergerImgBefore) list.get(i))
					.getName());
			emsEdiImgBom.setSpec(((EmsEdiMergerImgBefore) list.get(i))
					.getSpec());
			emsEdiImgBom.setUnit(((EmsEdiMergerImgBefore) list.get(i))
					.getUnit());
			emsEdiImgBom.setBeginDate(new Date());
			emsEdiImgBom.setUnitWaste(Double.valueOf(0));// 单耗
			emsEdiImgBom.setWaste(Double.valueOf(0)); // 损耗
			emsEdiMergerAfterImgs.add(emsEdiImgBom);
		}

		dgCommonQuery.setDataSource(emsEdiMergerAfterImgs);
		List tableColumns = new Vector();
		tableColumns.add(new JTableListColumn("料件货号", "ptNo", 150));
		tableColumns.add(new JTableListColumn("商品编码", "complex.code", 100));
		tableColumns.add(new JTableListColumn("料件名称", "name", 200));
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(single);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			if (single) {
				return dgCommonQuery.getReturnValue();
			} else {
				return dgCommonQuery.getReturnList();
			}
		}
		return null;
	}

	/*
	 * 获得加工种类MachiningType对象
	 */
	public Object getMachiningType(Object object) {
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(CustomBaseList.getInstance()
				.getMachiningTypes());
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	public Object getMachiningType() {
		return getMachiningType(null);
	}

	/*
	 * 获得保税方式PayMode对象
	 */
	public Object getpaymode(Object object) {
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(CustomBaseList.getInstance().getPayModes());
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	public Object getpaymode() {
		return getpaymode(null);
	}

	/*
	 * 获得征免方式LevyMode对象
	 */
	public Object getLevyMode(Object object) {
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(CustomBaseList.getInstance().getLevymode());
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	public Object getLevyMode() {
		return getLevyMode(null);
	}

	/*
	 * 获得征免性质LevyKind对象
	 */
	public Object getLevyKind(Object object) {
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(CustomBaseList.getInstance().getLevyKind());
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	public Object getLevyKind() {
		return getLevyKind(null);
	}

	/***************************************************************************
	 * 电子帐册新增料件--来自归并关系归并后料件 isNow 是否正在执行
	 **************************************************************************/
	public Object getMergerImgAfter(boolean single, EmsHeadH2k emsH2k,
			boolean isNow) {
		ManualDeclareAction manualdeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		SystemAction systemAction = (SystemAction) CommonVars
				.getApplicationContext().getBean("systemAction");
		List list = manualdeclearAction.findEmsEdiMergerHead(new Request(
				CommonVars.getCurrUser(), true));
		EmsEdiMergerHead emsImHead = null;
		for (int i = 0; i < list.size(); i++) {
			if ((((EmsEdiMergerHead) list.get(i)).getDeclareState().equals(
					DeclareState.PROCESS_EXE) && CommonVars.getEmsFrom()
					.equals("1"))
					|| (((EmsEdiMergerHead) list.get(i)).getDeclareState()
							.equals(DeclareState.APPLY_POR) && CommonVars
							.getEmsFrom().equals("2")))
				emsImHead = (EmsEdiMergerHead) list.get(i); // 获取正在执行的归并关系
		}
		List emsH2kList = new Vector();
		// if (emsImHead != null) {
		emsH2kList = manualdeclearAction.findEmsEdiMergerImgAfterToH2k(
				new Request(CommonVars.getCurrUser(), true), emsImHead, emsH2k,
				isNow);
		// }
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List emsEdiMergers = new Vector();
		CustomsDeclarationSet other = systemAction.findCustomsSet(new Request(
				CommonVars.getCurrUser()), ImpExpType.DIRECT_IMPORT);
		for (int i = 0; i < emsH2kList.size(); i++) {
			EmsHeadH2kImg emsH2kImg = new EmsHeadH2kImg();
			emsH2kImg.setSeqNum(((EmsEdiMergerImgAfter) emsH2kList.get(i))
					.getSeqNum());
			emsH2kImg.setComplex(((EmsEdiMergerImgAfter) emsH2kList.get(i))
					.getComplex());
			emsH2kImg.setName(((EmsEdiMergerImgAfter) emsH2kList.get(i))
					.getName());
			emsH2kImg.setSpec(((EmsEdiMergerImgAfter) emsH2kList.get(i))
					.getSpec());
			emsH2kImg.setUnit(((EmsEdiMergerImgAfter) emsH2kList.get(i))
					.getUnit());
			emsH2kImg.setLegalUnitGene(((EmsEdiMergerImgAfter) emsH2kList
					.get(i)).getLegalUnitGene());
			emsH2kImg.setLegalUnit2Gene(((EmsEdiMergerImgAfter) emsH2kList
					.get(i)).getLegalUnit2Gene());
			emsH2kImg.setWeigthUnitGene(((EmsEdiMergerImgAfter) emsH2kList
					.get(i)).getWeigthUnitGene());
			emsH2kImg.setCurr(((EmsEdiMergerImgAfter) emsH2kList.get(i))
					.getCurr());
			emsH2kImg
					.setMaxApprSpace(((EmsEdiMergerImgAfter) emsH2kList.get(i))
							.getMaxApprSpace());
			emsH2kImg.setIsMainImg(((EmsEdiMergerImgAfter) emsH2kList.get(i))
					.getIsMainImg());
			emsH2kImg.setLevyMode(other == null ? null : other.getLevyMode());
			emsEdiMergers.add(emsH2kImg);
		}
		dgCommonQuery.setDataSource(emsEdiMergers);
		List tableColumns = new Vector();
		tableColumns.add(new JTableListColumn("料件序号", "seqNum", 50,
				Integer.class));
		tableColumns.add(new JTableListColumn("商品编码", "complex.code", 80));
		tableColumns.add(new JTableListColumn("料件名称", "name", 100));
		tableColumns.add(new JTableListColumn("规格型号", "spec", 100));
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(single);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			if (single) {
				return dgCommonQuery.getReturnValue();
			} else {
				return dgCommonQuery.getReturnList();
			}
		}
		return null;
	}

	/***************************************************************************
	 * 电子帐册新增成品--来自归并关系归并后成品 isNow 是否正在执行
	 **************************************************************************/
	public Object getMergerExgAfter(boolean single, EmsHeadH2k emsH2k,
			boolean isNow) {
		ManualDeclareAction manualdeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		SystemAction systemAction = (SystemAction) CommonVars
				.getApplicationContext().getBean("systemAction");
		List list = manualdeclearAction.findEmsEdiMergerHead(new Request(
				CommonVars.getCurrUser(), true)); // 得到归并关系
		System.out.println("来自归并关系归并后成品list.size()=" + list.size());
		EmsEdiMergerHead emsImHead = null;
		for (int i = 0; i < list.size(); i++) {
			if ((((EmsEdiMergerHead) list.get(i)).getDeclareState().equals(
					DeclareState.PROCESS_EXE) && CommonVars.getEmsFrom()
					.equals("1"))
					|| (((EmsEdiMergerHead) list.get(i)).getDeclareState()
							.equals(DeclareState.APPLY_POR) && CommonVars
							.getEmsFrom().equals("2")))
				emsImHead = (EmsEdiMergerHead) list.get(i); // 获取正在执行的归并关系
			System.out.println("FOR里面emsImHead" + emsImHead);
		}
		System.out.println("FOR外面emsImHead" + emsImHead);
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List emsH2kList = new Vector();
		// if (emsImHead != null) {
		emsH2kList = manualdeclearAction.findEmsEdiMergerExgAfterToH2k(
				new Request(CommonVars.getCurrUser(), true), emsImHead, emsH2k,
				isNow);

		System.out.println("emsH2kList.size()=" + emsH2kList.size());
		// }
		List emsEdiMergers = new Vector();
		CustomsDeclarationSet other = systemAction.findCustomsSet(new Request(
				CommonVars.getCurrUser()), ImpExpType.DIRECT_EXPORT);
		for (int i = 0; i < emsH2kList.size(); i++) {
			int j = ((EmsEdiMergerExgAfter) emsH2kList.get(i)).getSeqNum();
			// List lists = manualdeclearAction
			// .findEmsHeadH2kImgExgCheck(new Request(CommonVars
			// .getCurrUser()), emsH2k, false,
			// "changeDate",
			// j);
			// System.out.println("小凯方法="+lists.size());
			// if(lists.size()>0)
			// continue;

			EmsHeadH2kExg emsH2kImg = new EmsHeadH2kExg();
			emsH2kImg.setSeqNum(((EmsEdiMergerExgAfter) emsH2kList.get(i))
					.getSeqNum());
			emsH2kImg.setComplex(((EmsEdiMergerExgAfter) emsH2kList.get(i))
					.getComplex());
			emsH2kImg.setName(((EmsEdiMergerExgAfter) emsH2kList.get(i))
					.getName());
			emsH2kImg.setSpec(((EmsEdiMergerExgAfter) emsH2kList.get(i))
					.getSpec());
			emsH2kImg.setUnit(((EmsEdiMergerExgAfter) emsH2kList.get(i))
					.getUnit());
			emsH2kImg.setLegalUnitGene(((EmsEdiMergerExgAfter) emsH2kList
					.get(i)).getLegalUnitGene());
			emsH2kImg.setLegalUnit2Gene(((EmsEdiMergerExgAfter) emsH2kList
					.get(i)).getLegalUnit2Gene());
			emsH2kImg.setWeigthUnitGene(((EmsEdiMergerExgAfter) emsH2kList
					.get(i)).getWeigthUnitGene());
			emsH2kImg.setCurr(((EmsEdiMergerExgAfter) emsH2kList.get(i))
					.getCurr());
			emsH2kImg
					.setMaxApprSpace(((EmsEdiMergerExgAfter) emsH2kList.get(i))
							.getMaxApprSpace());
			emsH2kImg.setLevyMode(other == null ? null : other.getLevyMode());
			emsEdiMergers.add(emsH2kImg);
		}
		dgCommonQuery.setDataSource(emsEdiMergers);
		List tableColumns = new Vector();
		tableColumns.add(new JTableListColumn("成品序号", "seqNum", 50,
				Integer.class));
		tableColumns.add(new JTableListColumn("商品编码", "complex.code", 80));
		tableColumns.add(new JTableListColumn("成品名称", "name", 100));
		tableColumns.add(new JTableListColumn("规格型号", "spec", 100));
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(single);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			if (single) {
				return dgCommonQuery.getReturnValue();
			} else {
				return dgCommonQuery.getReturnList();
			}
		}
		return null;
	}

	/***************************************************************************
	 * 电子分册新增料件--来自电子帐册料件
	 **************************************************************************/
	public Object getEmsH2kImg(boolean single, EmsHeadH2kFas emsH2kFas,
			EmsHeadH2k emsHead) {
		ManualDeclareAction manualdeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List emsH2kList = manualdeclearAction
				.findEmsHeadH2kImgToFas(new Request(CommonVars.getCurrUser(),
						true), emsHead, emsH2kFas);
		List emsEdiMergers = new Vector();
		for (int i = 0; i < emsH2kList.size(); i++) {
			EmsHeadH2kFasImg emsH2kFasImg = new EmsHeadH2kFasImg();
			EmsHeadH2kImg emsH2kImg = (EmsHeadH2kImg) emsH2kList.get(i);
			emsH2kFasImg.setSeqNum(emsH2kImg.getSeqNum());
			emsH2kFasImg.setComplex(emsH2kImg.getComplex());
			emsH2kFasImg.setName(emsH2kImg.getName());
			emsH2kFasImg.setSpec(emsH2kImg.getSpec());
			emsH2kFasImg.setUnit(emsH2kImg.getUnit());
			emsH2kFasImg.setCurr(emsH2kImg.getCurr());
			emsH2kFasImg.setAllowAmount(new Double(0));
			emsH2kFasImg.setDeclarePrice(new Double(0));
			emsEdiMergers.add(emsH2kFasImg);
		}
		dgCommonQuery.setDataSource(emsEdiMergers);
		List tableColumns = new Vector();
		tableColumns.add(new JTableListColumn("料件序号", "seqNum", 50,
				Integer.class));
		tableColumns.add(new JTableListColumn("商品编码", "complex.code", 80));
		tableColumns.add(new JTableListColumn("料件名称", "name", 100));
		tableColumns.add(new JTableListColumn("规格型号", "spec", 100));
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(single);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			if (single) {
				return dgCommonQuery.getReturnValue();
			} else {
				return dgCommonQuery.getReturnList();
			}
		}
		return null;
	}

	/***************************************************************************
	 * 电子分册新增料件--来自电子帐册成品
	 **************************************************************************/
	public Object getEmsH2kExg(boolean single, EmsHeadH2kFas emsH2kFas,
			EmsHeadH2k emsHead) {
		ManualDeclareAction manualdeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		List list = manualdeclearAction.findEmsHeadH2k(new Request(CommonVars
				.getCurrUser(), true));
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List emsH2kList = manualdeclearAction
				.findEmsHeadH2kExgToFas(new Request(CommonVars.getCurrUser(),
						true), emsHead, emsH2kFas);
		List emsEdiMergers = new Vector();
		for (int i = 0; i < emsH2kList.size(); i++) {
			EmsHeadH2kFasExg emsH2kFasExg = new EmsHeadH2kFasExg();
			EmsHeadH2kExg emsH2kExg = (EmsHeadH2kExg) emsH2kList.get(i);
			emsH2kFasExg.setSeqNum(emsH2kExg.getSeqNum());
			emsH2kFasExg.setComplex(emsH2kExg.getComplex());
			emsH2kFasExg.setName(emsH2kExg.getName());
			emsH2kFasExg.setSpec(emsH2kExg.getSpec());
			emsH2kFasExg.setUnit(emsH2kExg.getUnit());
			emsH2kFasExg.setCurr(emsH2kExg.getCurr());
			emsH2kFasExg.setAllowAmount(new Double(0));
			emsH2kFasExg.setDeclarePrice(new Double(0));
			emsEdiMergers.add(emsH2kFasExg);
		}
		dgCommonQuery.setDataSource(emsEdiMergers);
		List tableColumns = new Vector();
		tableColumns.add(new JTableListColumn("成品序号", "seqNum", 50,
				Integer.class));
		tableColumns.add(new JTableListColumn("商品编码", "complex.code", 80));
		tableColumns.add(new JTableListColumn("成品名称", "name", 100));
		tableColumns.add(new JTableListColumn("规格型号", "spec", 100));
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(single);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			if (single) {
				return dgCommonQuery.getReturnValue();
			} else {
				return dgCommonQuery.getReturnList();
			}
		}
		return null;
	}

	/***************************************************************************
	 * 中期核查--添加料件
	 **************************************************************************/
	public Object getImgFromInnerMerge(boolean single, CheckHead checkHead) {
		ManualDeclareAction manualdeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		CheckCancelAction checkCancelAction = (CheckCancelAction) CommonVars
				.getApplicationContext().getBean("checkCancelAction");
		List list = manualdeclearAction.findEmsHeadH2k(new Request(CommonVars
				.getCurrUser(), true));
		EmsHeadH2k emsHeadH2k = null;
		for (int i = 0; i < list.size(); i++) {
			if (((EmsHeadH2k) list.get(i)).getDeclareState().equals(
					DeclareState.PROCESS_EXE)
					&& !manualdeclearAction.getIsEmsH2kSend(new Request(
							CommonVars.getCurrUser(), true))) {
				emsHeadH2k = (EmsHeadH2k) list.get(i); // 获取正在执行的电子帐册
				break;
			} else if (manualdeclearAction.getIsEmsH2kSend(new Request(
					CommonVars.getCurrUser(), true))) {
				emsHeadH2k = (EmsHeadH2k) list.get(i); // 获取分段备案变更的电子帐册
				break;
			}
		}
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List checkImgList = checkCancelAction.findImgFromInnerMerge(
				new Request(CommonVars.getCurrUser(), true), emsHeadH2k,
				checkHead);
		List checkImgLists = new Vector();
		for (int i = 0; i < checkImgList.size(); i++) {
			CheckImg checkImg = new CheckImg();
			checkImg.setSeqNum(((InnerMergeData) checkImgList.get(i))
					.getHsFourNo().toString());
			checkImg.setComplex(((InnerMergeData) checkImgList.get(i))
					.getHsAfterComplex());
			Materiel mt = ((InnerMergeData) checkImgList.get(i)).getMateriel();
			checkImg.setPtNo(mt.getPtNo());
			checkImg.setName(mt.getFactoryName());
			checkImg.setSpec(mt.getFactorySpec());
			checkImg.setUnit(((InnerMergeData) checkImgList.get(i))
					.getHsAfterMemoUnit());
			checkImgLists.add(checkImg);
		}
		dgCommonQuery.setDataSource(checkImgLists);
		List tableColumns = new Vector();
		tableColumns.add(new JTableListColumn("料件序号", "seqNum", 50,
				Integer.class));
		tableColumns.add(new JTableListColumn("料件料号", "ptNo", 50));
		tableColumns.add(new JTableListColumn("商品编码", "complex.code", 80));
		tableColumns.add(new JTableListColumn("料件名称", "name", 100));
		tableColumns.add(new JTableListColumn("规格型号", "spec", 100));
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(single);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			if (single) {
				return dgCommonQuery.getReturnValue();
			} else {
				return dgCommonQuery.getReturnList();
			}
		}
		return null;
	}

	/***************************************************************************
	 * 中期核查--添加成品
	 **************************************************************************/
	public Object getExgFromInnerMerge(boolean single, CheckHead checkHead) {
		ManualDeclareAction manualdeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		CheckCancelAction checkCancelAction = (CheckCancelAction) CommonVars
				.getApplicationContext().getBean("checkCancelAction");
		List list = manualdeclearAction.findEmsHeadH2k(new Request(CommonVars
				.getCurrUser(), true));
		EmsHeadH2k emsHeadH2k = null;
		for (int i = 0; i < list.size(); i++) {
			if (((EmsHeadH2k) list.get(i)).getDeclareState().equals(
					DeclareState.PROCESS_EXE))
				emsHeadH2k = (EmsHeadH2k) list.get(i); // 获取正在执行的电子帐册
		}
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List checkExgList = checkCancelAction.findExgFromInnerMerge(
				new Request(CommonVars.getCurrUser(), true), emsHeadH2k,
				checkHead);
		List checkExgLists = new Vector();
		for (int i = 0; i < checkExgList.size(); i++) {
			CheckExg checkExg = new CheckExg();
			checkExg.setVersion("0");
			checkExg.setSeqNum(((InnerMergeData) checkExgList.get(i))
					.getHsFourNo().toString());
			Materiel mt = ((InnerMergeData) checkExgList.get(i)).getMateriel();
			checkExg.setPtNo(mt.getPtNo());
			checkExg.setName(mt.getFactoryName());
			checkExg.setSpec(mt.getFactorySpec());
			checkExg.setComplex(((InnerMergeData) checkExgList.get(i))
					.getHsAfterComplex());
			checkExg.setUnit(((InnerMergeData) checkExgList.get(i))
					.getHsAfterMemoUnit());
			checkExgLists.add(checkExg);
		}
		dgCommonQuery.setDataSource(checkExgLists);
		List tableColumns = new Vector();
		tableColumns.add(new JTableListColumn("成品序号", "seqNum", 50,
				Integer.class));
		tableColumns.add(new JTableListColumn("成品货号", "ptNo", 50));
		tableColumns.add(new JTableListColumn("商品编码", "complex.code", 80));
		tableColumns.add(new JTableListColumn("成品名称", "name", 100));
		tableColumns.add(new JTableListColumn("规格型号", "spec", 100));
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(single);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			if (single) {
				return dgCommonQuery.getReturnValue();
			} else {
				return dgCommonQuery.getReturnList();
			}
		}
		return null;
	}

	/***************************************************************************
	 * 核销--新增报关单
	 * 
	 * @return
	 **************************************************************************/
	public Object getCustomsDeclara(boolean single, CancelHead cancelHead,
			boolean isOwner) {

		DgQueryCustomsDeclara dg = new DgQueryCustomsDeclara(single,
				cancelHead, isOwner);
		dg.setVisible(true);
		return dg.getSelectList();
		// CheckCancelAction checkCancelAction = (CheckCancelAction) CommonVars
		// .getApplicationContext().getBean("checkCancelAction");
		//
		// final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		// List list = checkCancelAction.findCustomsDeclaration(new Request(
		// CommonVars.getCurrUser(), true), cancelHead,
		// isOwner,cancelHead.getBeginDate(),cancelHead.getEndDate());
		// List cancelCustomsDeclaras = new Vector();
		// if (list != null && !list.isEmpty()) {
		// for (int i = 0; i < list.size(); i++) {
		// CancelCustomsDeclara cancelCustomsDeclara = null;
		// if (isOwner) {
		// cancelCustomsDeclara = new CancelOwnerCustomsDeclara();
		// } else {
		// cancelCustomsDeclara = new CancelCusCustomsDeclara();
		// }
		// cancelCustomsDeclara.setCustomNo(((CustomsDeclaration) list
		// .get(i)).getCustomsDeclarationCode());
		// cancelCustomsDeclara.setCustom(((CustomsDeclaration) list
		// .get(i)).getDeclarationCustoms());
		// cancelCustomsDeclara.setTradeMode(((CustomsDeclaration) list
		// .get(i)).getTradeMode());
		// cancelCustomsDeclara.setDeclareDate(((CustomsDeclaration) list
		// .get(i)).getDeclarationDate());
		// cancelCustomsDeclara
		// .setInOutportDate(((CustomsDeclaration) list.get(i))
		// .getImpExpDate());
		// cancelCustomsDeclara.setNote(((CustomsDeclaration) list.get(i))
		// .getMemos());
		// cancelCustomsDeclara
		// .setInOutportType(((CustomsDeclaration) list.get(i))
		// .getImpExpType().intValue());
		//
		// if (((CustomsDeclaration) list.get(i)).getImpExpFlag() != null
		// && ((CustomsDeclaration) list.get(i)).getImpExpFlag()
		// .equals(ImpExpFlag.IMPORT)) {
		// cancelCustomsDeclara.setInOutportFlat("I");
		//
		// } else if (((CustomsDeclaration) list.get(i)).getImpExpFlag() != null
		// && ((CustomsDeclaration) list.get(i)).getImpExpFlag()
		// .equals(ImpExpFlag.EXPORT)) {
		// cancelCustomsDeclara.setInOutportFlat("E");
		// } else if (((CustomsDeclaration) list.get(i)).getImpExpType() != null
		// && EncCommon
		// .isImport(((CustomsDeclaration) list.get(i))
		// .getImpExpType().intValue())
		// && ((CustomsDeclaration) list.get(i)).getImpExpFlag()
		// .equals(ImpExpFlag.SPECIAL)) {
		// cancelCustomsDeclara.setInOutportFlat("I");
		// } else if (((CustomsDeclaration) list.get(i)).getImpExpType() != null
		// && !EncCommon.isImport(((CustomsDeclaration) list
		// .get(i)).getImpExpType().intValue())
		// && ((CustomsDeclaration) list.get(i)).getImpExpFlag()
		// .equals(ImpExpFlag.SPECIAL)) {
		// cancelCustomsDeclara.setInOutportFlat("E");
		// }
		// cancelCustomsDeclaras.add(cancelCustomsDeclara);
		// }
		// }
		// dgCommonQuery.setDataSource(cancelCustomsDeclaras);
		// List tableColumns = new Vector();
		// tableColumns.add(new JTableListColumn("报关单号", "customNo", 100));
		// tableColumns.add(new JTableListColumn("进出口类型", "inOutportFlat",
		// 100));
		// tableColumns.add(new JTableListColumn("报关地", "custom.name", 80));
		// tableColumns.add(new JTableListColumn("申报日期", "declareDate", 100));
		// tableColumns.add(new JTableListColumn("进出口日期", "inOutportDate",
		// 100));
		// tableColumns.add(new JTableListColumn("贸易方式", "tradeMode.name",
		// 100));
		// DgCommonQuery.setTableColumns(tableColumns);
		// DgCommonQuery.setSingleResult(single);
		// /*
		// * dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter()
		// {
		// * public void windowOpened(java.awt.event.WindowEvent e) { JTable
		// table
		// * = dgCommonQuery.getJTable();
		// * table.getColumnModel().getColumn(2).setCellRenderer( new
		// * DefaultTableCellRenderer() { public Component
		// * getTableCellRendererComponent( JTable table, Object value, boolean
		// * isSelected, boolean hasFocus, int row, int column) {
		// * super.getTableCellRendererComponent(table, value, isSelected,
		// * hasFocus, row, column); String str = ""; if (value != null) {
		// String
		// * tempStr = value.toString(); if
		// * (String.valueOf(value).trim().equals("")) { str = ""; } if
		// * (value.equals(String.valueOf(ImpExpFlag.IMPORT))) { str = "I"; }
		// else
		// * if (value.equals(String.valueOf(ImpExpFlag.EXPORT))) { str = "E"; }
		// }
		// * this.setText(str); return this; } }); } });
		// */
		// dgCommonQuery.setVisible(true);
		// if (dgCommonQuery.isOk()) {
		// if (single) {
		// return dgCommonQuery.getReturnValue();
		// } else {
		// return dgCommonQuery.getReturnList();
		// }
		// }
		// return null;
	}

	/**
	 * 取得临时申报商品信息
	 * 
	 * @return
	 */
	public List getTempDeclaredCommInfo(ApplyToCustomsBillList billList,
			Integer materielProductFlag) {
		List list = new Vector();
		list.add(new JTableListColumn("是否备案", "isPutOnRecord", 50));
		list.add(new JTableListColumn("帐册序号", "emsSerialNo", 100));
		list.add(new JTableListColumn("商品货号", "materiel.ptNo", 100));
		list.add(new JTableListColumn("商品编码", "complex.code", 80));
		list.add(new JTableListColumn("商品名称", "name", 100));
		list.add(new JTableListColumn("商品规格", "spec", 100));
		list.add(new JTableListColumn("单位", "unit.name", 50));
		list.add(new JTableListColumn("法定单位", "legalUnit.name", 50));
		list.add(new JTableListColumn("法定单位二", "legalUnit2.name", 50));
		list.add(new JTableListColumn("属性", "materielType", 50));
		DgCommonQuery.setTableColumns(list);
		EncAction encAction = (EncAction) CommonVars.getApplicationContext()
				.getBean("encAction");
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource((encAction.getTempDeclaredCommInfo(
				new Request(CommonVars.getCurrUser(), true), billList,
				materielProductFlag)));
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery
				.setTitle(materielProductFlag == null ? ""
						: materielProductFlag.toString().equals(
								MaterielType.MATERIEL) ? "料件" : "成品");
		dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				JTable table = dgCommonQuery.getJTable();
				table.getColumnModel().getColumn(1)
						.setCellRenderer(new checkBoxRenderer());
				table.getColumnModel().getColumn(9)
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
									String tempStr = value.toString();
									if (tempStr.equals(MaterielType.MATERIEL)) {
										str = "料件";
									} else if (tempStr
											.equals(MaterielType.FINISHED_PRODUCT)) {
										str = "成品";
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
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 取得大批量修改商品编的备案资料
	 * 
	 * @param isMaterial
	 * @return
	 */
	public List getBcsBatchUpdateComplex(String declareState,
			String contractNo, String emsType, boolean isMaterial) {
		List list = new Vector();
		list.add(new JTableListColumn("商品编码", "complex.code", 80));
		if (isMaterial) {
			list.add(new JTableListColumn("料件序号", "emsSerialNo", 60,
					Integer.class));
		} else {
			list.add(new JTableListColumn("成品序号", "emsSerialNo", 60,
					Integer.class));
		}
		list.add(new JTableListColumn("商品名称", "name", 200));
		list.add(new JTableListColumn("第一法定单位", "complex.firstUnit.name", 70));
		list.add(new JTableListColumn("第二法定单位", "complex.secondUnit.name", 70));
		if (!"2".equals(emsType)) {// 十码对应关系
			list.add(new JTableListColumn("记录号", "credenceNo", 70));
		}
		DgCommonQuery.setTableColumns(list);
		List lsDataSource = new ArrayList();
		ContractExeAction contractExeAction = (ContractExeAction) CommonVars
				.getApplicationContext().getBean("contractExeAction");
		lsDataSource = contractExeAction.getBatchUpdateComplex(new Request(
				CommonVars.getCurrUser(), true), declareState, contractNo,
				emsType, isMaterial);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(lsDataSource);
		dgCommonQuery.setLike(false);// 默认按精确查找
		dgCommonQuery.setDefaultAll(true);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 取得大批量修改商品编的备案资料
	 * 
	 * @param isMaterial
	 * @return
	 */
	public List getBcusBatchUpdateComplex(String emsType, boolean isMaterial) {
		List list = new Vector();
		list.add(new JTableListColumn("商品编码", "complex.code", 80));
		list.add(new JTableListColumn("帐册序号", "emsSerialNo", 60, Integer.class));
		list.add(new JTableListColumn("商品名称", "name", 200));
		list.add(new JTableListColumn("法定单位", "legalUnit.name", 70));
		list.add(new JTableListColumn("法定单位二", "legalUnit2.name", 70));
		DgCommonQuery.setTableColumns(list);
		List lsDataSource = new ArrayList();
		EncAction encAction = (EncAction) CommonVars.getApplicationContext()
				.getBean("encAction");
		lsDataSource = encAction.getBatchUpdateComplex(
				new Request(CommonVars.getCurrUser(), true), emsType,
				isMaterial);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(lsDataSource);
		dgCommonQuery.setLike(false);// 默认按精确查找
		dgCommonQuery.setDefaultAll(true);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 取得临时申报商品信息
	 * 
	 * @return
	 */
	public List getTempCustomsDeclarationCommInfo(boolean isMaterial,
			BaseCustomsDeclaration customsDeclaration) {
		ManualDeclareAction manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		String isQuery = null;
		isQuery = manualDecleareAction.getBpara(
				new Request(CommonVars.getCurrUser()),
				BcusParameter.BGD_ISQUERY);
		if (isQuery != null && "1".equals(isQuery)) {
			return getTempCustomsDeclarationCommInfoByPage(isMaterial,
					customsDeclaration);
		} else {
			return getTempCustomsDeclarationCommInfoNoPage(isMaterial,
					customsDeclaration);
		}
	}

	public List getTempCustomsDeclarationCommInfoNoPage(boolean isMaterial,
			BaseCustomsDeclaration customsDeclaration) {
		List list = new Vector();
		list.add(new JTableListColumn("帐册序号", "emsSerialNo", 60, Integer.class));
		if (customsDeclaration instanceof CustomsDeclaration
				&& !EncCommon.isMaterial(customsDeclaration.getImpExpType())) {
			list.add(new JTableListColumn("版本号", "version", 80));
		}
		list.add(new JTableListColumn("商品编码", "complex.code", 80));
		list.add(new JTableListColumn("商品名称", "name", 120));
		list.add(new JTableListColumn("商品规格", "spec", 120));
		list.add(new JTableListColumn("单位", "unit.name", 50));
		list.add(new JTableListColumn("法定单位", "legalUnit.name", 50));
		list.add(new JTableListColumn("法定单位二", "legalUnit2.name", 50));
		list.add(new JTableListColumn("法定数量", "legalAmount", 50));
		list.add(new JTableListColumn("法定数量二", "secondAmount", 50));
		if (customsDeclaration instanceof BcsCustomsDeclaration
				&& !EncCommon.isMaterial(customsDeclaration.getImpExpType())) {
			list.add(new JTableListColumn("加工费总价", "workUsd", 80));
		}
		DgCommonQuery.setTableColumns(list);
		List lsDataSource = new ArrayList();
		if (customsDeclaration instanceof CustomsDeclaration) {
			EncAction encAction = (EncAction) CommonVars
					.getApplicationContext().getBean("encAction");
			lsDataSource = encAction.getTempCustomsDeclarationCommInfo(
					new Request(CommonVars.getCurrUser(), true), isMaterial,
					(CustomsDeclaration) customsDeclaration);
		} else if (customsDeclaration instanceof BcsCustomsDeclaration) {
			ContractExeAction contractExeAction = (ContractExeAction) CommonVars
					.getApplicationContext().getBean("contractExeAction");
			lsDataSource = contractExeAction.getTempCustomsDeclarationCommInfo(
					new Request(CommonVars.getCurrUser(), true), isMaterial,
					(BcsCustomsDeclaration) customsDeclaration);
		} else if (customsDeclaration instanceof DzscCustomsDeclaration) {
			DzscContractExeAction dzscContractExeAction = (DzscContractExeAction) CommonVars
					.getApplicationContext().getBean("dzscContractExeAction");
			lsDataSource = dzscContractExeAction
					.getTempCustomsDeclarationCommInfo(
							new Request(CommonVars.getCurrUser(), true),
							isMaterial,
							(DzscCustomsDeclaration) customsDeclaration);
		}
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(lsDataSource);
		dgCommonQuery.setLike(false);// 默认按精确查找
		dgCommonQuery.setDefaultAll(true);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 取得临时申报商品信息 有待进一步修改
	 * 
	 * @return
	 */
	public List getTempCustomsDeclarationCommInfoByPage(
			final boolean isMaterial,
			final BaseCustomsDeclaration customsDeclaration) {
		List list = new Vector();
		list.add(new JTableListColumn("帐册序号", "emsSerialNo", 60, Integer.class));
		if (customsDeclaration instanceof CustomsDeclaration
				&& !EncCommon.isMaterial(customsDeclaration.getImpExpType())) {
			list.add(new JTableListColumn("版本号", "version", 80));
		}
		list.add(new JTableListColumn("商品编码", "complex.code", 80));
		list.add(new JTableListColumn("商品名称", "name", 120));
		list.add(new JTableListColumn("商品规格", "spec", 120));
		list.add(new JTableListColumn("单位", "unit.name", 50));
		list.add(new JTableListColumn("法定单位", "legalUnit.name", 50));
		list.add(new JTableListColumn("法定单位二", "legalUnit2.name", 50));
		list.add(new JTableListColumn("法定数量", "legalAmount", 50));
		list.add(new JTableListColumn("法定数量二", "secondAmount", 50));
		if (customsDeclaration instanceof BcsCustomsDeclaration
				&& !EncCommon.isMaterial(customsDeclaration.getImpExpType())) {
			list.add(new JTableListColumn("加工费总价", "workUsd", 80));
		}
		List lsDataSource = new ArrayList();
		if (customsDeclaration instanceof CustomsDeclaration) {// 电子帐册
			DgCommonByQuery dgCommonQuery = new DgCommonByQuery() {
				@Override
				public List getDataSource(String sfield, Object values) {
					//
					// 查询的方法
					//
					EncAction encAction = (EncAction) CommonVars
							.getApplicationContext().getBean("encAction");
					List list = encAction.getTempCustomsDeclarationCommInfo(
							new Request(CommonVars.getCurrUser(), true),
							isMaterial,
							(CustomsDeclaration) customsDeclaration, sfield,
							values);
					return list;
				}
			};
			DgCommonByQuery.setSingleResult(false);
			dgCommonQuery.setTableColumns(list);
			dgCommonQuery.setLike(false);// 默认按精确查找
			dgCommonQuery.setQuery(true);// 是否查询后再查找
			dgCommonQuery.setVisible(true);
			if (dgCommonQuery.isOk()) {
				return dgCommonQuery.getReturnList();
			}
		} else if (customsDeclaration instanceof BcsCustomsDeclaration) {// 电子化手册
			DgCommonQuery.setTableColumns(list);
			ContractExeAction contractExeAction = (ContractExeAction) CommonVars
					.getApplicationContext().getBean("contractExeAction");
			lsDataSource = contractExeAction.getTempCustomsDeclarationCommInfo(
					new Request(CommonVars.getCurrUser(), true), isMaterial,
					(BcsCustomsDeclaration) customsDeclaration);
			final DgCommonQuery dgCommonQuery = new DgCommonQuery();
			dgCommonQuery.setDataSource(lsDataSource);
			DgCommonQuery.setSingleResult(false);
			dgCommonQuery.setLike(false);// 默认按精确查找
			dgCommonQuery.setVisible(true);
			if (dgCommonQuery.isOk()) {
				return dgCommonQuery.getReturnList();
			}
		} else if (customsDeclaration instanceof DzscCustomsDeclaration) {
			DgCommonQuery.setTableColumns(list);
			DzscContractExeAction dzscContractExeAction = (DzscContractExeAction) CommonVars
					.getApplicationContext().getBean("dzscContractExeAction");
			lsDataSource = dzscContractExeAction
					.getTempCustomsDeclarationCommInfo(
							new Request(CommonVars.getCurrUser(), true),
							isMaterial,
							(DzscCustomsDeclaration) customsDeclaration);
			final DgCommonQuery dgCommonQuery = new DgCommonQuery();
			dgCommonQuery.setDataSource(lsDataSource);
			DgCommonQuery.setSingleResult(false);
			dgCommonQuery.setLike(false);// 默认按精确查找
			dgCommonQuery.setVisible(true);
			if (dgCommonQuery.isOk()) {
				return dgCommonQuery.getReturnList();
			}
		}
		return null;
	}

	// 海关帐表体增加
	public List getMergeData(boolean single, String materielType) {

		CommonBaseCodeAction commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		List mergeData = commonBaseCodeAction.findInnerMergeDataByType(
				new Request(CommonVars.getCurrUser(), true), materielType);
		for (int i = 0; i < mergeData.size(); i++) {
			InnerMergeData temp = (InnerMergeData) mergeData.get(i);

			if (temp.getImrType().equals("0")) {
				temp.setImrType("成品");
			} else if (temp.getImrType().equals("1")) {
				temp.setImrType("半成品");
			} else if (temp.getImrType().equals("2")) {
				temp.setImrType("料件");
			} else if (temp.getImrType().equals("3")) {
				temp.setImrType("设备");
			} else if (temp.getImrType().equals("4")) {
				temp.setImrType("边角料");
			} else if (temp.getImrType().equals("5")) {
				temp.setImrType("残次品");
			} else
				temp.setImrType("");
		}

		List list = new Vector();
		// list.add(new JTableListColumn("是否已经备案", "isPutOnRecord", 80));
		// list.add(new JTableListColumn("账册序号", "emsSerialNo", 80));
		list.add(new JTableListColumn("工厂料号", "materiel.ptNo", 80));
		list.add(new JTableListColumn("归并前品名", "materiel.ptName", 80));
		list.add(new JTableListColumn("归并前规格", "materiel.ptSpec", 80));
		list.add(new JTableListColumn("归并前法定单位", "hsBeforeLegalUnit.name", 100));
		list.add(new JTableListColumn("归并前企业单位", "hsBeforeEnterpriseUnit.name",
				100));
		list.add(new JTableListColumn("十位备案序号", "hsAfterTenMemoNo", 80));
		list.add(new JTableListColumn("十位商品名称", "hsAfterMaterielTenName", 80));
		list.add(new JTableListColumn("十位商品编码", "hsAfterComplex.code", 80));
		list.add(new JTableListColumn("十位商品规格型号", "hsAfterMaterielTenSpec", 100));
		list.add(new JTableListColumn("四位备案序号", "hsFourNo", 80));
		list.add(new JTableListColumn("四位商品名称", "hsFourMaterielName", 100));
		list.add(new JTableListColumn("四位商品编码", "hsFourCode", 80));
		// list.add(new JTableListColumn("类型", "imrType", 100));
		DgCommonQuery.setTableColumns(list);

		/*
		 * DgCommonQuery.getColumnModel().getColumn(3).setCellRenderer( new
		 * DefaultTableCellRenderer() { public Component
		 * getTableCellRendererComponent( JTable table, Object value, boolean
		 * isSelected, boolean hasFocus, int row, int column) {
		 * super.getTableCellRendererComponent(table, value, isSelected,
		 * hasFocus, row, column); super.setText((value == null) ? "" :
		 * castValue1(value)); return this; } });
		 */
		EncAction encAction = (EncAction) CommonVars.getApplicationContext()
				.getBean("encAction");
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(mergeData);
		DgCommonQuery.setSingleResult(single);
		dgCommonQuery.setVisible(true);
		if (!single) {
			return dgCommonQuery.getReturnList();
		} else {
			List result = null;
			if (dgCommonQuery.getReturnValue() != null) {
				result = new Vector();
				result.add(dgCommonQuery.getReturnValue());
			}
			return result;
		}
	}

	/**
	 * 获得物料中的成品
	 */
	public List getProductOrHalf(String materielType, boolean includeHalf) {
		List list = new Vector();
		list.add(new JTableListColumn("料号", "ptNo", 100));
		list.add(new JTableListColumn("商品编码", "complex.code", 80));
		list.add(new JTableListColumn("商品名称", "ptName", 100));
		list.add(new JTableListColumn("型号规格", "ptSpec", 100));
		list.add(new JTableListColumn("单位", "calUnit.name", 50));
		list.add(new JTableListColumn("单价", "ptPrice", 50));
		list.add(new JTableListColumn("净重", "ptNetWeight", 50));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();

		List dataSource = null;
		MaterialManageAction materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		dataSource = materialManageAction.findMaterielByMaterielType(
				new Request(CommonVars.getCurrUser(), true), materielType);
		if (includeHalf) {
			List halfProducts = materialManageAction.findMaterielByType(
					new Request(CommonVars.getCurrUser(), true),
					MaterielType.SEMI_FINISHED_PRODUCT);
			dataSource.addAll(halfProducts);
		}
		dgCommonQuery.setDataSource(dataSource);

		dgCommonQuery.setTitle("");

		// dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
		// public void windowOpened(java.awt.event.WindowEvent e) {
		// JTable table = dgCommonQuery.getJTable();
		// table.getColumnModel().getColumn(1).setCellRenderer(
		// new checkBoxRenderer());
		// }
		// });
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 获得字段列表
	 * 
	 * @param single
	 * @param classList
	 * @param txtTask
	 * @return
	 */
	public Object getFieldList(boolean single, ClassList classList,
			TxtTask txtTask) {
		DataImportAction dataImportAction = (DataImportAction) CommonVars
				.getApplicationContext().getBean("dataImportAction");
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List list = dataImportAction.findFieldListFromTxtFormat(new Request(
				CommonVars.getCurrUser(), true), classList, txtTask);
		List lists = new Vector();
		for (int i = 0; i < list.size(); i++) {
			TxtFormat txtFormat = new TxtFormat();
			txtFormat.setSortno(Integer.valueOf(dataImportAction
					.getFieldFormatNum(new Request(CommonVars.getCurrUser(),
							true), "TxtFormat", txtTask)));
			txtFormat.setName(((FieldList) list.get(i)).getName());
			txtFormat.setFieldname(((FieldList) list.get(i)).getFieldname());
			txtFormat.setFieldlen(((FieldList) list.get(i)).getFieldlen());
			txtFormat.setFieldtype(((FieldList) list.get(i)).getFieldtype());
			txtFormat.setFielddesc(((FieldList) list.get(i)).getFielddesc());
			txtFormat.setObjName(((FieldList) list.get(i)).getObjName());
			txtFormat.setIskey(((FieldList) list.get(i)).getIskey());
			txtFormat.setIsNull(((FieldList) list.get(i)).getIsNull());
			lists.add(txtFormat);
		}
		dgCommonQuery.setDataSource(lists);
		List tableColumns = new Vector();
		tableColumns.add(new JTableListColumn("字段名称", "name", 100));
		tableColumns.add(new JTableListColumn("字段类型", "fieldtype", 80));
		tableColumns.add(new JTableListColumn("字段长度", "fieldlen", 100));
		tableColumns.add(new JTableListColumn("是否主键", "iskey", 100));
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(single);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			if (single) {
				return dgCommonQuery.getReturnValue();
			} else {
				return dgCommonQuery.getReturnList();
			}
		}
		return null;
	}

	/**
	 * 获得格式
	 * 
	 * @param single
	 * @return
	 */
	public Object getTxtTask(boolean single, TxtTaskEx txtTaskEx) {
		DataImportAction dataImportAction = (DataImportAction) CommonVars
				.getApplicationContext().getBean("dataImportAction");
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List list = dataImportAction.findTxtTaskFromTxtTask(new Request(
				CommonVars.getCurrUser(), true), txtTaskEx);
		List lists = new Vector();
		for (int i = 0; i < list.size(); i++) {
			TxtTaskSel txtTaskSel = new TxtTaskSel();
			if (txtTaskEx.getIsParentTask() == null
					|| (!txtTaskEx.getIsParentTask())) {
				txtTaskSel.setTxttable((TxtTask) list.get(i));
			} else {
				txtTaskSel.setSubDBTaskEx((TxtTaskEx) list.get(i));
			}
			lists.add(txtTaskSel);

		}
		dgCommonQuery.setDataSource(lists);
		List tableColumns = new Vector();
		if (txtTaskEx.getIsParentTask() == null
				|| (!txtTaskEx.getIsParentTask())) {
			tableColumns.add(new JTableListColumn("文本导入格式名称",
					"txttable.taskname", 180));
			tableColumns.add(new JTableListColumn("目标表",
					"txttable.classList.name", 80));
			// tableColumns.add(new JTableListColumn("字符集转换", "txttable.gbflag",
			// 80));
			tableColumns.add(new JTableListColumn("格式创建者", "txttable.creator",
					80));
		} else {
			tableColumns.add(new JTableListColumn("任务名称",
					"subDBTaskEx.taskname", 180));
		}
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(single);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			if (single) {
				return dgCommonQuery.getReturnValue();
			} else {
				return dgCommonQuery.getReturnList();
			}
		}
		return null;
	}

	/**
	 * 获得域设置
	 * 
	 * @param single
	 * @return
	 */
	public Object getDBTask(boolean single, DBTaskEx dbTaskEx) {
		DataImportAction dataImportAction = (DataImportAction) CommonVars
				.getApplicationContext().getBean("dataImportAction");
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List list = dataImportAction.findDBTaskFromDBTask(new Request(
				CommonVars.getCurrUser(), true), dbTaskEx);
		List lists = new Vector();
		for (int i = 0; i < list.size(); i++) {
			DBTaskSel dbTaskSel = new DBTaskSel();
			if (dbTaskEx.getIsParentTask() == null
					|| (!dbTaskEx.getIsParentTask())) {
				dbTaskSel.setDbFormat((DBFormat) list.get(i));
			} else {
				dbTaskSel.setSubDBTaskEx((DBTaskEx) list.get(i));
			}
			lists.add(dbTaskSel);
		}
		dgCommonQuery.setDataSource(lists);
		List tableColumns = new Vector();
		if (dbTaskEx.getIsParentTask() == null || (!dbTaskEx.getIsParentTask())) {
			tableColumns.add(new JTableListColumn("DB导入域名称",
					"dbFormat.regionName", 180));
			tableColumns.add(new JTableListColumn("目标表",
					"dbFormat.classList.name", 100));
			tableColumns.add(new JTableListColumn("视图来源",
					"dbFormat.dbView.name", 80));
		} else {
			tableColumns.add(new JTableListColumn("任务名称",
					"subDBTaskEx.taskname", 180));
		}
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(single);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			if (single) {
				return dgCommonQuery.getReturnValue();
			} else {
				return dgCommonQuery.getReturnList();
			}
		}
		return null;
	}

	/**
	 * 新增事件
	 * 
	 * @param impExpGoodsFlag
	 * @return
	 */
	public List getExecuSql() {
		DataImportAction dataImportAction = (DataImportAction) CommonVars
				.getApplicationContext().getBean("dataImportAction");
		List list = new Vector();
		list.add(new JTableListColumn("数据源", "dataRoot.name", 100));
		list.add(new JTableListColumn("事件名称", "name", 300));
		DgCommonQuery.setTableColumns(list);
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List datasource = dataImportAction.findDbDataExecuSql(new Request(
				CommonVars.getCurrUser()));
		dgCommonQuery.setDataSource(datasource);
		dgCommonQuery.setTitle("选择执行事件");
		dgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	public Object getTransferFactoryBill(boolean impExpGoodsFlag) {
		List list = new Vector();
		list.add(new JTableListColumn("单据号", "transferFactoryBillNo", 150));
		list.add(new JTableListColumn("进出口类型", "isImpExpGoods", 50));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();

		TransferFactoryManageAction transferFactoryManageAction = (TransferFactoryManageAction) CommonVars
				.getApplicationContext().getBean("transferFactoryManageAction");
		List dataSource = transferFactoryManageAction
				.findTransferFactoryBillByImpExpGoodsFlag(new Request(
						CommonVars.getCurrUser(), true), impExpGoodsFlag);
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("请选择单据");
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				JTable table = dgCommonQuery.getJTable();
				table.getColumnModel().getColumn(2)
						.setCellRenderer(new checkBoxRenderer());
			}
		});

		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 查询公司资料
	 * 
	 * @return
	 */
	public Object findCompanies() {
		List list = new Vector();
		list.add(new JTableListColumn("公司代号", "code", 100));
		list.add(new JTableListColumn("公司名称", "name", 200));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();

		SystemAction systemAction = (SystemAction) CommonVars
				.getApplicationContext().getBean("systemAction");
		List dataSource = systemAction.findCompanies();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("请选择公司");
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 显示归并关系归并后成品和料件
	 * 
	 * @return
	 */
	public Object findEmsEdiMergerImgExg() {
		List list = new Vector();
		list.add(new JTableListColumn("备案序号", "seqNum", 50));
		list.add(new JTableListColumn("商品编码", "complex.code", 100));
		list.add(new JTableListColumn("商品名称", "name", 100));
		list.add(new JTableListColumn("商品规格", "spec", 100));
		list.add(new JTableListColumn("币别", "curr.name", 50));
		list.add(new JTableListColumn("单位", "unit.name", 50));
		list.add(new JTableListColumn("第一法定单位", "complex.firstUnit.name", 50));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();

		ManualDeclareAction manualDeclareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		List dataSource = manualDeclareAction
				.findEmsEdiMergerImgExg(new Request(CommonVars.getCurrUser(),
						true));
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("请选择资料");
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 获得委外商品明细对象
	 */
	public List getOutsourceLotCommodity(List dataSource) {
		List list = new Vector();
		list.add(new JTableListColumn("来源单号", "outsourceLot.billCode", 60));
		list.add(new JTableListColumn("父料号", "parentCommCode", 80));
		list.add(new JTableListColumn("料号", "commCode", 60));
		list.add(new JTableListColumn("物料名称", "commName", 50));
		list.add(new JTableListColumn("余量", "remainAmount", 80));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("请选择委外商品明细记录");
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 获得受托商品明细对象
	 */
	public List getBeCommissionedLotCommodity(List dataSource) {
		List list = new Vector();
		list.add(new JTableListColumn("来源单号", "beCommissionedLot.billCode", 60));
		list.add(new JTableListColumn("父料号", "parentCommCode", 80));
		list.add(new JTableListColumn("料号", "commCode", 60));
		list.add(new JTableListColumn("物料名称", "commName", 50));
		list.add(new JTableListColumn("余量", "remainAmount", 80));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("请选择受托商品明细记录");
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 获得受托发货单据商品明细
	 */
	public List getSendGoodsBillCommodity(List dataSource) {
		List list = new Vector();
		list.add(new JTableListColumn("来源单号", "outsourceMaterialBill.billNo",
				60));
		list.add(new JTableListColumn("成品号", "productFinishNo", 80));
		list.add(new JTableListColumn("料号", "ptNo", 60));
		list.add(new JTableListColumn("物料名称", "ptName", 50));
		list.add(new JTableListColumn("单价", "unitPrice", 80));
		list.add(new JTableListColumn("数量", "quantity", 80));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("请选择报关清单");
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 获得受托物料成品信息
	 */
	public List getBeCommissionedLotCommodityInfo(List dataSource) {
		List list = new Vector();
		list.add(new JTableListColumn("来源单号", "beCommissionedLot.billCode", 60));
		list.add(new JTableListColumn("货号", "commCode", 80));
		list.add(new JTableListColumn("货物名字", "commName", 60));
		list.add(new JTableListColumn("单价", "unitPrice", 80));
		list.add(new JTableListColumn("数量", "quantity", 80));
		list.add(new JTableListColumn("金额", "totalPrice", 80));
		list.add(new JTableListColumn("余量", "remainAmount", 80));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("请选择报关清单");
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 获得委托物料成品信息
	 */
	public List getOutsourceLotCommodityInfo(List dataSource) {
		List list = new Vector();
		list.add(new JTableListColumn("来源单号", "outsourceLot.billCode", 60));
		list.add(new JTableListColumn("货号", "commCode", 80));
		list.add(new JTableListColumn("货物名字", "commName", 60));
		list.add(new JTableListColumn("单价", "unitPrice", 80));
		list.add(new JTableListColumn("数量", "quantity", 80));
		list.add(new JTableListColumn("金额", "totalPrice", 80));
		list.add(new JTableListColumn("余量", "remainAmount", 80));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("请选择报关清单");
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	public Object getImgFromEmsHeadImg(boolean single, EmsHeadH2k emsHead,
			EmsHeadH2kVersion version) {
		ManualDeclareAction manualdeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List list = manualdeclearAction.findEmsHeadH2kImgToBom(new Request(
				CommonVars.getCurrUser(), true), emsHead, version);
		List imgLists = new Vector();
		for (int i = 0; i < list.size(); i++) {
			EmsHeadH2kImg img = (EmsHeadH2kImg) list.get(i);
			EmsHeadH2kBom bom = new EmsHeadH2kBom();
			bom.setComplex(img.getComplex());
			bom.setCompany(CommonVars.getCurrUser().getCompany());
			bom.setEmsHeadH2kVersion(version);
			bom.setModifyTimes(Integer.valueOf(0));
			bom.setName(img.getName());
			bom.setSpec(img.getSpec());
			bom.setSeqNum(img.getSeqNum());
			bom.setPrice(CommonVars.getEmsImgPrice(img));
			bom.setUnit(img.getUnit());
			imgLists.add(bom);
		}
		dgCommonQuery.setDataSource(imgLists);
		dgCommonQuery.setLike(false);
		List tableColumns = new Vector();
		tableColumns.add(new JTableListColumn("料件序号", "seqNum", 50));
		tableColumns.add(new JTableListColumn("料件料号", "ptNo", 50));
		tableColumns.add(new JTableListColumn("商品编码", "complex.code", 80));
		tableColumns.add(new JTableListColumn("料件名称", "name", 100));
		tableColumns.add(new JTableListColumn("规格型号", "spec", 100));
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(single);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			if (single) {
				return dgCommonQuery.getReturnValue();
			} else {
				return dgCommonQuery.getReturnList();
			}
		}
		return null;
	}

	/**
	 * 查询未进行4码归并的内部归并数据
	 * 
	 * @param fourData
	 * @param materielType
	 * @return
	 */
	public List getTenDataNotFourMerge(ReverseMergeFourData fourData,
			String materielType) {
		List list = new Vector();
		list.add(new JTableListColumn("十位备案序号", "hsAfterTenMemoNo", 100));
		list.add(new JTableListColumn("10位商品编码", "hsAfterComplex.code", 80));
		list.add(new JTableListColumn("归并后商品名称", "hsAfterMaterielTenName", 100));
		list.add(new JTableListColumn("规格,型号", "hsAfterMaterielTenSpec", 100));
		list.add(new JTableListColumn("备案单位", "hsAfterMemoUnit.name", 50));
		list.add(new JTableListColumn("第一法定单位", "hsAfterLegalUnit.name", 50));
		list.add(new JTableListColumn("第二法定单位", "hsAfterSecondLegalUnit.name",
				50));
		DgCommonQuery.setTableColumns(list);
		CommonBaseCodeAction commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		List dataSource = commonBaseCodeAction.findTenDataNotFourMerge(
				new Request(CommonVars.getCurrUser(), true), fourData,
				materielType);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("查询未进行4码归并的内部归并数据");
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 查询未禁用的内部归并数据
	 * 
	 * @param materielType
	 * @return
	 */
	public List getInnerMergeDataIsNotForbid(final String materielType) {
		List list = new Vector();
		list.add(new JTableListColumn("工厂料号", "materiel.ptNo", 100));
		list.add(new JTableListColumn("品名", "materiel.factoryName", 120));
		list.add(new JTableListColumn("规格型号", "materiel.factorySpec", 120));
		list.add(new JTableListColumn("企业单位", "materiel.calUnit.name", 60));
		list.add(new JTableListColumn("单价", "materiel.ptPrice", 50));
		list.add(new JTableListColumn("净重", "materiel.ptNetWeight", 50));
		list.add(new JTableListColumn("备案序号", "hsAfterTenMemoNo", 50));
		JTableListColumn revertClumn = new JTableListColumn("恢复日期",
				"revertDate", 120);
		revertClumn.setDataFormat("yyyy-MM-dd:HH:ss:mm");
		list.add(revertClumn);
		list.add(new JTableListColumn("恢复人", "revertUser", 80));
		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CommonBaseCodeAction commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
						.getApplicationContext()
						.getBean("commonBaseCodeAction");
				return commonBaseCodeAction.findInnerMergeDataIsForbid(
						new Request(CommonVars.getCurrUser()), null, null,
						materielType, false);
			}
		};

		dgCommonQuery.setTitle("查询未禁用的内部归并数据");
		dgCommonQuery.setSize(820, 457);
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 查询未进行10码归并的内部归并数据
	 * 
	 * @param materielType
	 * @return
	 */
	public List getBeforeDataNotTenMerge(String materielType) {
		List list = new Vector();
		list.add(new JTableListColumn("料号", "materiel.ptNo", 100));
		list.add(new JTableListColumn("物料编码", "materiel.complex.code", 80));
		list.add(new JTableListColumn("物料名称", "materiel.ptName", 100));
		list.add(new JTableListColumn("型号规格", "materiel.ptSpec", 100));
		list.add(new JTableListColumn("单价", "materiel.ptPrice", 50));
		list.add(new JTableListColumn("净重", "materiel.ptNetWeight", 50));
		list.add(new JTableListColumn("归并前法定单位", "hsBeforeLegalUnit.name", 50));
		list.add(new JTableListColumn("归并前法定单位", "hsBeforeEnterpriseUnit.name",
				50));
		DgCommonQuery.setTableColumns(list);
		CommonBaseCodeAction commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		List dataSource = commonBaseCodeAction.findBeforeDataNotTenMerge(
				new Request(CommonVars.getCurrUser(), true), materielType);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("查询未进行10码归并的内部归并数据");
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 获得所有前四码匹配的项目物料对象
	 */
	public Object getComplexByFourCode(String fourCode) {
		List list = new Vector();
		list.add(new JTableListColumn("编号", "code", 100));
		list.add(new JTableListColumn("名称", "name", 80));
		list.add(new JTableListColumn("第一法定单位", "firstUnit.name", 100));
		list.add(new JTableListColumn("第二法定单位", "secondUnit.name", 100));
		list.add(new JTableListColumn("备注", "note", 50));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		CommonBaseCodeAction commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		List dataSource = commonBaseCodeAction.findComplexByFourCode(
				new Request(CommonVars.getCurrUser(), true), fourCode);
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("海关商品对象");
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/***************************************************************************
	 * 电子帐册--新增料件--来自企业内部归并
	 **************************************************************************/
	public Object getMergerImgAfterFromMerger(boolean single,
			EmsEdiTrHead emsEdiTrHead, EmsHeadH2k emsH2k) {
		ManualDeclareAction manualdeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		CommonBaseCodeAction commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		SystemAction systemAction = (SystemAction) CommonVars
				.getApplicationContext().getBean("systemAction");
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List list = commonBaseCodeAction.findInnerMergeDataByType10ToEmsH2k(
				new Request(CommonVars.getCurrUser(), true),
				MaterielType.MATERIEL, emsEdiTrHead, emsH2k, "");
		List emsEdiMergers = new Vector();
		CustomsDeclarationSet other = systemAction.findCustomsSet(new Request(
				CommonVars.getCurrUser()), ImpExpType.DIRECT_IMPORT);
		List<EmsHeadH2kImg> lists = manualdeclearAction
				.findEmsHeadH2kImgExgCheck(
						new Request(CommonVars.getCurrUser()), emsH2k, true,
						"changeDate");
		Map<Integer, EmsHeadH2kImg> map = new HashMap<Integer, EmsHeadH2kImg>();
		Map<Integer, EmsHeadH2kImg> map2 = new HashMap<Integer, EmsHeadH2kImg>();
		if (lists.size() > 0) {
			for (int k = 0; k < lists.size(); k++) {
				EmsHeadH2kImg emsHeadH2kImg = lists.get(k);
				if (emsHeadH2kImg.getSeqNum() != null) {
					map.put(emsHeadH2kImg.getSeqNum(), emsHeadH2kImg);
				}
			}
		}
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			if (obj[0] == null)
				continue;
			int j = (Integer) (obj[0]);

			EmsHeadH2kImg s = map.get(j);
			if (s != null) {
				continue;
			}
			EmsHeadH2kImg emsH2kImg = new EmsHeadH2kImg();
			emsH2kImg.setSeqNum((Integer) (obj[0]));
			emsH2kImg.setComplex(((Complex) obj[1]));
			emsH2kImg.setName((String) obj[2]);
			emsH2kImg.setSpec((String) obj[3]);
			emsH2kImg.setUnit((Unit) obj[4]);
			emsH2kImg.setCurr(emsEdiTrHead.getCurr());
			emsH2kImg.setMaxApprSpace(Double.valueOf(0));
			emsH2kImg.setWeigthUnitGene((Double) (obj[7]));
			emsH2kImg.setIsMainImg(obj[8] == null ? false : Boolean
					.parseBoolean(obj[8].toString()));
			emsH2kImg.setLevyMode(other == null ? null : other.getLevyMode());
			if (map2.get((Integer) (obj[0])) == null) {
				emsEdiMergers.add(emsH2kImg);
				map2.put((Integer) (obj[0]), emsH2kImg);
			}

		}
		dgCommonQuery.setDataSource(emsEdiMergers);
		List tableColumns = new Vector();
		tableColumns.add(new JTableListColumn("料件序号", "seqNum", 50));
		tableColumns.add(new JTableListColumn("商品编码", "complex.code", 80));
		tableColumns.add(new JTableListColumn("料件名称", "name", 100));
		tableColumns.add(new JTableListColumn("规格型号", "spec", 100));
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(single);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			if (single) {
				return dgCommonQuery.getReturnValue();
			} else {
				return dgCommonQuery.getReturnList();
			}
		}
		return null;
	}

	/***************************************************************************
	 * 电子帐册--新增成品--来自企业内部归并
	 **************************************************************************/
	public Object getMergerExgAfterFromMerger(boolean single,
			EmsEdiTrHead emsEdiTrHead, EmsHeadH2k emsH2k) {
		ManualDeclareAction manualdeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		CommonBaseCodeAction commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		SystemAction systemAction = (SystemAction) CommonVars
				.getApplicationContext().getBean("systemAction");
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List list = commonBaseCodeAction.findInnerMergeDataByType10ToEmsH2k(
				new Request(CommonVars.getCurrUser(), true),
				MaterielType.FINISHED_PRODUCT, emsEdiTrHead, emsH2k, "");
		List emsEdiMergers = new Vector();
		CustomsDeclarationSet other = systemAction.findCustomsSet(new Request(
				CommonVars.getCurrUser()), ImpExpType.DIRECT_EXPORT);
		List<EmsHeadH2kExg> lists = manualdeclearAction
				.findEmsHeadH2kImgExgCheck(
						new Request(CommonVars.getCurrUser()), emsH2k, false,
						"changeDate");
		Map<Integer, EmsHeadH2kExg> map = new HashMap<Integer, EmsHeadH2kExg>();
		Map<Integer, EmsHeadH2kExg> map2 = new HashMap<Integer, EmsHeadH2kExg>();
		if (lists.size() > 0) {
			for (int k = 0; k < lists.size(); k++) {
				EmsHeadH2kExg emsHeadH2kExg = lists.get(k);
				if (emsHeadH2kExg.getSeqNum() != null) {
					map.put(emsHeadH2kExg.getSeqNum(), emsHeadH2kExg);
				}
			}
		}
		System.out.println("map.size=" + map.size());
		System.out.println("list.size=" + list.size());
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			int j = (Integer) (obj[0]);

			EmsHeadH2kExg s = map.get(j);
			if (s != null) {
				continue;
			}
			EmsHeadH2kExg emsH2kImg = new EmsHeadH2kExg();
			emsH2kImg.setSeqNum((Integer) (obj[0]));
			emsH2kImg.setComplex(((Complex) obj[1]));
			emsH2kImg.setName((String) obj[2]);
			emsH2kImg.setSpec((String) obj[3]);
			emsH2kImg.setUnit((Unit) obj[4]);
			emsH2kImg.setCurr(emsEdiTrHead.getCurr());
			emsH2kImg.setMaxApprSpace(Double.valueOf(0));
			emsH2kImg.setLevyMode(other == null ? null : other.getLevyMode());
			emsH2kImg.setWeigthUnitGene((Double) (obj[7]));
			if (map2.get((Integer) (obj[0])) == null) {
				emsEdiMergers.add(emsH2kImg);
				map2.put((Integer) (obj[0]), emsH2kImg);
			}
		}
		dgCommonQuery.setDataSource(emsEdiMergers);
		List tableColumns = new Vector();
		tableColumns.add(new JTableListColumn("成品序号", "seqNum", 50));
		tableColumns.add(new JTableListColumn("商品编码", "complex.code", 80));
		tableColumns.add(new JTableListColumn("料件名称", "name", 100));
		tableColumns.add(new JTableListColumn("规格型号", "spec", 100));
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(single);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			if (single) {
				return dgCommonQuery.getReturnValue();
			} else {
				return dgCommonQuery.getReturnList();
			}
		}
		return null;
	}

	private ContractAction contractAction = null;

	/**
	 * 合同审核，国内购买
	 * 
	 * @param dataSource
	 * @return
	 */
	public Object getContractImgCav(List dataSource) {
		List list = new Vector();
		list.add(new JTableListColumn("料件编号", "complex.code", 100));
		list.add(new JTableListColumn("料件名称", "name", 100));
		list.add(new JTableListColumn("料件规格", "spec", 100));
		list.add(new JTableListColumn("单位", "unit.name", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List lsDataSource = new ArrayList();
		lsDataSource.addAll(dataSource);
		for (int i = lsDataSource.size() - 1; i >= 0; i--) {
			ContractImgCav contractImgCav = (ContractImgCav) lsDataSource
					.get(i);
			if (contractImgCav.getName().indexOf("国内购买") >= 0) {
				lsDataSource.remove(i);
			}
		}
		dgCommonQuery.setDataSource(lsDataSource);
		dgCommonQuery.setTitle("料件增加");
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 合同审核，国内购买
	 * 
	 * @param dataSource
	 * @return
	 */
	public Object getDzscContractImgCav(List dataSource) {
		List list = new Vector();
		list.add(new JTableListColumn("料件编号", "complex.code", 100));
		list.add(new JTableListColumn("料件名称", "name", 100));
		list.add(new JTableListColumn("料件规格", "spec", 100));
		list.add(new JTableListColumn("单位", "unit.name", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List lsDataSource = new ArrayList();
		lsDataSource.addAll(dataSource);
		for (int i = lsDataSource.size() - 1; i >= 0; i--) {
			DzscContractImgCav contractImgCav = (DzscContractImgCav) lsDataSource
					.get(i);
			if (contractImgCav.getName().indexOf("国内购买") >= 0) {
				lsDataSource.remove(i);
			}
		}
		dgCommonQuery.setDataSource(lsDataSource);
		dgCommonQuery.setTitle("料件增加");
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	public List getMakeListExgList(List dataSource) {
		List list = new Vector();
		list.add(new JTableListColumn("货物名称", "name", 300));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("增加");
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 查找所有的合同料件来自不在Bom中存在的
	 */
	public List getContractImgNoInContractBom(ContractExg contractExg) {
		List dataSource = null;
		List list = new Vector();

		list.add(new JTableListColumn("料件序号", "seqNum", 100));
		list.add(new JTableListColumn("商品编码", "complex.code", 100));
		list.add(new JTableListColumn("商品名称", "name", 100));
		list.add(new JTableListColumn("规格型号", "spec", 100));
		list.add(new JTableListColumn("计量单位", "unit.name", 100));
		list.add(new JTableListColumn("企业申报单价", "declarePrice", 100));
		list.add(new JTableListColumn("凭证序号", "credenceNo", 100));
		list.add(new JTableListColumn("数量", "amount", 100));
		list.add(new JTableListColumn("损耗数量", "wasteAmount", 100));
		list.add(new JTableListColumn("总金额", "totalPrice", 100));
		list.add(new JTableListColumn("法定单位总量", "legalTotalAmount", 100));
		list.add(new JTableListColumn("法定单位数量", "legalAmount", 100));
		list.add(new JTableListColumn("法定单位", "legalUnit.name", 100));
		list.add(new JTableListColumn("单位重量", "unitWeight", 100));
		list.add(new JTableListColumn("总重量", "totalWeight", 100));
		list.add(new JTableListColumn("料件类型", "materialType", 100));
		list.add(new JTableListColumn("原产地", "country.name", 100));
		list.add(new JTableListColumn("废料是否报关", "isApplyToCustoms", 100));
		list.add(new JTableListColumn("备注", "note", 100));

		DgCommonQuery.setTableColumns(list);
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		dataSource = contractAction.findContractImgNoInContractBom(new Request(
				CommonVars.getCurrUser(), true), contractExg);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("当前合同的料件");
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				JTable table = dgCommonQuery.getJTable();
				table.getColumnModel().getColumn(18)
						.setCellRenderer(new TableCheckBoxRender());
				table.getColumnModel().getColumn(16)
						.setCellRenderer(new DefaultTableCellRenderer() {
							public Component getTableCellRendererComponent(
									JTable table, Object value,
									boolean isSelected, boolean hasFocus,
									int row, int column) {
								super.getTableCellRendererComponent(table,
										value, isSelected, hasFocus, row,
										column);
								super.setText((value == null) ? ""
										: castValue1(value));
								return this;
							}

							private String castValue1(Object value) {
								String returnValue = "";

								if (value.equals(MaterielType.FINISHED_PRODUCT)) {
									returnValue = "成品";
								} else if (value.equals(MaterielType.MACHINE)) {
									returnValue = "设备";
								} else if (value.equals(MaterielType.MATERIEL)) {
									returnValue = "主料";
								} else if (value
										.equals(MaterielType.ASSISTANT_MATERIAL)) {
									returnValue = "辅料";
								} else if (value
										.equals(MaterielType.WASTE_MATERIAL)) {
									returnValue = "消耗料";
								}
								return returnValue;
							}
						});
			}
		});
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 获得状态为草稿的报关清单来自进出口类型
	 */
	public Object getCustomsDeclarationByImpExpType(
			List<BaseCustomsDeclaration> dataSource, int impExpType) {
		List list = new Vector();
		list.add(new JTableListColumn("进出口类型", "impExpType", 100));
		list.add(new JTableListColumn("报关单流水号", "serialNumber", 100));
		list.add(new JTableListColumn("电子帐册编号", "emsHeadH2k", 80));
		list.add(new JTableListColumn("报关单号", "customsDeclarationCode", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("请选择报关单");
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
			}
		});

		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	public Object getExgVersion(List dataSource) {
		List list = new Vector();
		list.add(new JTableListColumn("版本号", "version", 300));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 取得临时申报商品信息（商品禁用功能）电子帐册
	 * 
	 * @return
	 */
	public List getTempCustomsDeclarationCommInfo(boolean isMaterial) {
		List list = new Vector();
		list.add(new JTableListColumn("帐册序号", "bill1", 60, Integer.class));
		if (!isMaterial) {
			list.add(new JTableListColumn("版本号", "bill6", 80));
		}
		list.add(new JTableListColumn("商品编码", "bill2", 80));
		list.add(new JTableListColumn("商品名称", "bill3", 120));
		list.add(new JTableListColumn("商品规格", "bill4", 100));
		list.add(new JTableListColumn("单位", "bill5", 50));
		DgCommonQuery.setTableColumns(list);
		List lsDataSource = new ArrayList();
		ManualDeclareAction manualdeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		lsDataSource = manualdeclearAction.getTempEmsEdiH2kForBid(new Request(
				CommonVars.getCurrUser()), isMaterial);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(lsDataSource);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 取得临时申报商品信息（商品禁用功能）电子化手册
	 * 
	 * @return
	 */
	public List getTempBcsCustomsDeclarationCommInfo(boolean isMaterial,
			String emsNo) {
		List list = new Vector();
		list.add(new JTableListColumn("帐册序号", "bill1", 60, Integer.class));
		list.add(new JTableListColumn("商品编码", "bill2", 80));
		list.add(new JTableListColumn("商品名称", "bill3", 120));
		list.add(new JTableListColumn("商品规格", "bill4", 100));
		list.add(new JTableListColumn("单位", "bill5", 50));
		DgCommonQuery.setTableColumns(list);
		List lsDataSource = new ArrayList();
		ContractAction contractAction = (ContractAction) CommonVars
				.getApplicationContext().getBean("contractAction");
		lsDataSource = contractAction.getTempContractForBid(new Request(
				CommonVars.getCurrUser()), isMaterial, emsNo);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(lsDataSource);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 取得临时申报商品信息（商品禁用功能）电子手册
	 * 
	 * @return
	 */
	public List getTempDzscCustomsDeclarationCommInfo(boolean isMaterial,
			String emsNo) {
		List list = new Vector();
		list.add(new JTableListColumn("帐册序号", "bill1", 60, Integer.class));
		list.add(new JTableListColumn("商品编码", "bill2", 80));
		list.add(new JTableListColumn("商品名称", "bill3", 120));
		list.add(new JTableListColumn("商品规格", "bill4", 100));
		list.add(new JTableListColumn("单位", "bill5", 50));
		DgCommonQuery.setTableColumns(list);
		List lsDataSource = new ArrayList();
		DzscAction dzscAction = (DzscAction) CommonVars.getApplicationContext()
				.getBean("dzscAction");
		lsDataSource = dzscAction.getTempContractForBid(
				new Request(CommonVars.getCurrUser()), isMaterial, emsNo);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(lsDataSource);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	public List getRestirictCommodity(boolean isMaterial) {
		List list = new Vector();
		list.add(new JTableListColumn("帐册序号", "bill1", 60, Integer.class));
		list.add(new JTableListColumn("商品编码", "bill2", 80));
		list.add(new JTableListColumn("商品名称", "bill3", 120));
		list.add(new JTableListColumn("商品规格", "bill4", 100));
		list.add(new JTableListColumn("单位", "bill5", 50));
		list.add(new JTableListColumn("数量", "bill7", 50));

		DgCommonQuery.setTableColumns(list);
		List lsDataSource = new ArrayList();
		ManualDeclareAction manualdeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		lsDataSource = manualdeclearAction.getTempEmsEdiH2kForBid(new Request(
				CommonVars.getCurrUser()), isMaterial);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(lsDataSource);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	public Object getVersionFromBgBom(String ptNo) {
		List list = new Vector();
		list.add(new JTableListColumn("版本号", "version", 100, Integer.class));
		DgCommonQuery.setTableColumns(list);
		List lsDataSource = new ArrayList();
		CommonBaseCodeAction commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		lsDataSource = commonBaseCodeAction.findVersionByCpNo(new Request(
				CommonVars.getCurrUser()), ptNo);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(lsDataSource);
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	public static List<AclGroup> getAclGroup(AclUser aclUser) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("群组名称", "groupName", 150));
		list.add(new JTableListColumn("群组描述", "groupDescription", 250));
		DgCommonQuery.setTableColumns(list);
		DgCommonQuery dgCommonQuery = new DgCommonQuery();

		AuthorityAction authorityAction = (AuthorityAction) CommonVars
				.getApplicationContext().getBean("authorityAction");

		List list1 = authorityAction.findAclGroupNotInUser(new Request(
				CommonVars.getCurrUser(), true), aclUser);
		dgCommonQuery.setDataSource(list1);
		dgCommonQuery.setTitle("群组名称");
		dgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 抓取正在执行的账册或手册
	 * 
	 * @return
	 */
	public BaseEmsHead findBaseEmsHeadExecuting(List dataSource) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("帐/手册编号 ", "emsNo", 200));
		list.add(new JTableListColumn("经营单位代码", "tradeCode", 100));
		list.add(new JTableListColumn("经营单位名称", "tradeName", 100));
		list.add(new JTableListColumn("开始有效期", "beginDate", 100));
		list.add(new JTableListColumn("结束有效期", "endDate", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("账册/手册");
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return (BaseEmsHead) dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 获取申请单资料
	 */
	public Object[] getImpExpCommodityInfoByPara(List dataSource,
			String dialogTitle) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("料号", "materiel.ptNo", 100));
		list.add(new JTableListColumn("名称", "materiel.factoryName", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle(dialogTitle);
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return (Object[]) dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 获取客户/供应商中的经营单位
	 */
	public ScmCoc getTradeCode() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("编码", "code", 100));
		list.add(new JTableListColumn("全称", "name", 150));
		list.add(new JTableListColumn("关务海关注册公司", "brief.name", 150));
		list.add(new JTableListColumn("简称", "fname", 100));
		// DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setTableColumns(list);
		try {
			MaterialManageAction materialManageAction = (MaterialManageAction) CommonVars
					.getApplicationContext().getBean("materialManageAction");
			dgCommonQuery.setDataSource(materialManageAction
					.findScmCocsIsWork(new Request(CommonVars.getCurrUser(),
							true)));
		} catch (UndeclaredThrowableException e) {
			e.printStackTrace();
			// System.out.println(e.getCause().getMessage());
			// TODO: handle exception
		}

		dgCommonQuery.setTitle("经营单位选择");
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return (ScmCoc) dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 获得授权公司
	 */
	public Company getManufacturer() {

		List<JTableListColumn> list = new Vector<JTableListColumn>();

		list.add(new JTableListColumn("单位编码", "code", 100));

		list.add(new JTableListColumn("单位名称", "name", 200));

		final DgCommonQuery dgCommonQuery = new DgCommonQuery();

		DgCommonQuery.setTableColumns(list);

		try {

			SystemAction service = (SystemAction) CommonVars
					.getApplicationContext().getBean("systemAction");

			dgCommonQuery.setDataSource(service.findCompanies());

		} catch (UndeclaredThrowableException e) {
			e.printStackTrace();

		}

		dgCommonQuery.setTitle("生产厂家选择");

		DgCommonQuery.setSingleResult(true);

		dgCommonQuery.setVisible(true);

		if (dgCommonQuery.isOk()) {
			return (Company) dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 取得物料与报关对应表中料件
	 * 
	 * @param isMaterial
	 *            是否料件
	 * @return
	 */
	public List getTempMaterielByTypeBcs(boolean isMaterial) {
		List list = new Vector();
		list.add(new JTableListColumn("归并序号", "bcsTenInnerMerge.seqNum", 100));
		list.add(new JTableListColumn("料号", "materiel.ptNo", 100));
		list.add(new JTableListColumn("商品编码", "materiel.complex.code", 100));
		list.add(new JTableListColumn("商品名称", "materiel.factoryName", 200));
		list.add(new JTableListColumn("型号规格", "materiel.factorySpec", 200));
		list.add(new JTableListColumn("单位", "materiel.calUnit.name", 50));
		list.add(new JTableListColumn("单价", "materiel.ptPrice", 50));
		list.add(new JTableListColumn("净重", "materiel.ptNetWeight", 50));
		DgCommonQuery.setTableColumns(list);
		List lsDataSource = new ArrayList();
		ContractExeAction contractExeAction = (ContractExeAction) CommonVars
				.getApplicationContext().getBean("contractExeAction");
		lsDataSource = contractExeAction.getTempMaterielByTypeBcs(new Request(
				CommonVars.getCurrUser()), isMaterial);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(lsDataSource);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

}