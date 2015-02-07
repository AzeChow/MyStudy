/*
 * Created on 2005-3-23
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.dzscmanage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.text.NumberFormatter;

import com.bestway.bcs.client.contract.DgContract;
import com.bestway.bcs.client.contract.DgContractExgSort;
import com.bestway.bcs.client.contract.DgContractImgSort;
import com.bestway.bcs.client.dictpor.DgAllChangState;
import com.bestway.bcs.contract.entity.TempBankMode;
import com.bestway.bcus.client.common.CommonQueryPage;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.basecode.MachiningType;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.depcode.RedDep;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.PayMode;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.client.custom.common.DgShowCustomerError;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DeleteResultMark;
import com.bestway.common.constant.DzscEmsType;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.EquipMode;
import com.bestway.common.constant.LimitFlag;
import com.bestway.common.constant.ManageObject;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.customs.common.entity.EntityShowCustomerError;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsBomBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorMaterialExg;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorMaterialImg;
import com.bestway.ui.message.DgMessage;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.Dimension;
import java.awt.ComponentOrientation;

/**
 * @author // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgDzscEmsPor extends JDialogBase {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnPrint = null;

	// private JComboBox cbbPrint = null;

	private JButton btnSave = null;

	private JButton btnEdit = null;

	private JButton btnShowUnitWaste = null;

	private JButton btnCopyBill = null;

	private JButton jButton5 = null;

	private JPanel jPanel = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel pnBase = null;

	private JPanel pnImgBill = null;

	private JPanel pnExgBom = null;

	private JLabel jLabel2 = null;

	private JTextField tfEmsState = null;

	private JLabel jLabel3 = null;

	private JTextField tfPreEmsNo = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JComboBox cbbDeclareCustoms = null;

	private JComboBox cbbEmsType = null;

	private JLabel jLabel6 = null;

	private JTextField tfEmsNo = null;

	private JLabel jLabel8 = null;

	private JLabel jLabel9 = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JLabel jLabel10 = null;

	private JLabel jLabel11 = null;

	private JComboBox jComboBox5 = null;

	private JLabel jLabel12 = null;

	private JTextField jTextField3 = null;

	private JLabel jLabel13 = null;

	private JComboBox jComboBox7 = null;

	private JLabel jLabel15 = null;

	private JTextField jTextField4 = null;

	private JLabel jLabel16 = null;

	private JTextField jTextField5 = null;

	private JLabel jLabel17 = null;

	private JCalendarComboBox cbbDestroyDate = null;

	private JLabel jLabel18 = null;

	private JTextField jTextField6 = null;

	private JLabel jLabel19 = null;

	private JTextField jTextField7 = null;

	private JLabel jLabel20 = null;

	private JComboBox jComboBox8 = null;

	private JLabel jLabel21 = null;

	private JTextField jTextField8 = null;

	private JLabel jLabel22 = null;

	private JTextField jTextField9 = null;

	private JLabel jLabel23 = null;

	private JTextField jTextField10 = null;

	private JLabel jLabel24 = null;

	private JTextField tfImpContractNo = null;

	private JLabel jLabel25 = null;

	private JTextField tfExpContractNo = null;

	private JLabel jLabel26 = null;

	private JLabel jLabel27 = null;

	private JLabel jLabel28 = null;

	private JComboBox jComboBox9 = null;

	private JLabel jLabel29 = null;

	private JComboBox cbbWardshipRate = null;

	private JLabel jLabel30 = null;

	private JLabel jLabel31 = null;

	private JComboBox jComboBox11 = null;

	private JLabel jLabel36 = null;

	private JTextField jTextField16 = null;

	private JLabel jLabel37 = null;

	private JCalendarComboBox jCalendarComboBox4 = null;

	private JLabel jLabel38 = null;

	private JComboBox jComboBox16 = null;

	private JLabel jLabel39 = null;

	private JComboBox jComboBox17 = null;

	private JLabel jLabel40 = null;

	private JLabel jLabel41 = null;

	private JTextField jTextField17 = null;

	private DzscAction dzscAction = null;

	private JTableListModel tableModelHead = null; // 表头

	private JTableListModel tableModelImgBill = null; // 料件清单备案

	private JTableListModel tableModelExgBill = null; // 成品清单备案

	private JTableListModel tableModelBomBill = null; // 单耗清单备案

	private JTableListModel tableModelMaterialExg = null; // 归并成品

	private JTableListModel tableModelMaterialImg = null; // 归并料件

	private DzscEmsPorHead head = null; // 头 // @jve:decl-index=0:

	private JFormattedTextField tfImgAmount = null;

	private JFormattedTextField tfExgAmount = null;

	private JFormattedTextField tfWardshipFee = null;

	private JTextField jTextField13 = null;

	private int dataState = DataState.BROWSE;

	private boolean isEditBill = false;

	private JToolBar jToolBar6 = null;

	private JLabel jLabel44 = null;

	private JButton btnAddImgBill = null;

	private JButton jButton18 = null;

	private JButton jButton19 = null;

	private JTable tbImgBill = null;

	private JScrollPane jScrollPane2 = null;

	private JSplitPane jSplitPane1 = null;

	private JPanel jPanel7 = null;

	private JPanel jPanel8 = null;

	private JToolBar jToolBar7 = null;

	private JLabel jLabel45 = null;

	private JButton btnAddExgBill = null;

	private JButton jButton22 = null;

	private JButton jButton23 = null;

	private JToolBar jToolBar8 = null;

	private JButton jButton24 = null;

	private JLabel jLabel46 = null;

	private JButton jButton25 = null;

	private JButton jButton26 = null;

	private JButton jButton27 = null;

	private JButton jButton28 = null;

	private JTable tbExgBill = null;

	private JScrollPane jScrollPane3 = null;

	private JTable tbBomBill = null;

	private JScrollPane jScrollPane4 = null;

	private JLabel jLabel47 = null;

	private SystemAction service = null;

	private JButton btnUndo = null;

	private NumberFormatter numberFormatter = null;

	private JLabel lbExgInfo = null;

	private JPanel pnMaterialImg = null;

	private JPanel pnMaterialExg = null;

	private JToolBar jToolBar2 = null;

	private JButton btnAddMaterialImg = null;

	private JScrollPane jScrollPane = null;

	private JTable tbMaterialImg = null;

	private JToolBar jJToolBarBar = null;

	private JButton btnAddMaterialExg = null;

	private JScrollPane jScrollPane1 = null;

	private JTable tbMaterialExg = null;

	private JButton btnDeleteMaterialExg = null;

	private JButton btnDeleteMaterialImg = null;

	private JLabel jLabel42 = null;

	private JComboBox cbbManageObject = null;

	private JLabel jLabel1 = null;

	private JComboBox cbbReceiveArea = null;

	private JCheckBox jCheckBox = null; // @jve:decl-index=0:visual-constraint="594,600"

	private JCheckBox jCheckBox1 = null;

	private boolean isFirstTabbedPage = true;

	private JButton btnSaveDetail = null;

	private JButton btnSaveBom = null;

	private JButton btnBomUndo = null;

	private JButton btnUnitWareDiff = null;

	private JLabel jLabel43 = null;

	private JTextField tfCorrEmsNo = null;

	private JLabel jLabel48 = null;

	private JTextField tfCopEntNo = null;

	private JTextField tfTradeCode = null;

	private JLabel jLabel101 = null;

	private JTextField tfTradeName = null;

	private JLabel jLabel49 = null;

	private JComboBox cbbRedDep = null;

	private JButton jButton = null;

	private JButton btnCheck = null;

	private JPopupMenu pmImportUnitWaste = null; // @jve:decl-index=0:visual-constraint="781,235"

	private JMenuItem jMenuItem = null;

	private JMenuItem jMenuItem1 = null;

	private JLabel jLabel50 = null;

	private JComboBox cbbLimitFlag = null;

	private JButton btnBOMExport = null;

	private JButton btnChangeDeclareState = null;

	private JPopupMenu pmChangeDeclareState = null; // @jve:decl-index=0:visual-constraint="827,109"

	private JMenuItem miUndoDeclare = null;

	private JButton btnChangeImgModifyMark = null;

	private JButton btnChangeExgModifyMark = null;

	private JMenuItem miNotModified1 = null;

	private JMenuItem miModified1 = null;

	private JMenuItem miDelete1 = null;

	private JMenuItem miAdd1 = null;

	private JPopupMenu pmChangeModifyMark = null; // @jve:decl-index=0:visual-constraint="805,180"

	private JMenuItem miNotModified = null;

	private JMenuItem miModified = null;

	private JMenuItem miDelete = null;

	private JMenuItem miAdd = null;

	private JButton btnChangeBomModifyMark = null;

	private JPopupMenu jPopupMenuPrintReport = null;

	private JMenuItem miExportExgRecordationInventory = null;

	private JMenuItem miImportImgRecordationInventory = null;

	private JMenuItem miExportExgUnitConsumptionRecordationInventory = null;

	private JMenuItem miProcessContractBeforeHandContractCompose = null;

	private JMenuItem miProcessContractChangeBeforeHandContractCompose = null;

	private JMenuItem miExportExgRecordationInventoryChange = null;

	private JMenuItem miImportImgRecordationInventoryChange = null;

	private JMenuItem miExportExgUnitConsumptionRecordationInventoryChange = null;

	private JMenuItem miExportExgProcessCost = null;

	private JMenuItem miImgInletOutletBalanceCheck = null;

	private JComboBox cbbEquipMode = null;

	private JLabel jLabel = null;

	private JMenuItem miHeadPrint = null;

	private JButton btnMaterielSort = null;

	private JButton btnFinishProductSort = null;

	private JButton btnProcessPrice = null;

	private JButton btnCal = null;

	private JLabel jLabel14 = null;

	private JCalendarComboBox calendarSaveDate = null;

	private JButton btnIEPort = null;

	private JLabel jLabel7 = null;

	private JComboBox cbbBank = null;

	private JCheckBox cbSelectAllBom = null;

	/**
	 * @throws java.awt.HeadlessException
	 */
	public DgDzscEmsPor() {
		super();
		initialize();
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
		service = (SystemAction) CommonVars.getApplicationContext().getBean(
				"systemAction");
	}

	public NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
		}
		return numberFormatter;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	public Double objToDouble(Object value) { // 转换strToDouble 填充数据
		return value == null ? 0.0 : Double.parseDouble(value.toString());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(733, 596);
		this.setContentPane(getJContentPane());
		this.setTitle("通关备案修改");
		// this.setResizable(false);
		try {
			cellTable();
		} catch (Exception ex) {
		}
	}

	private void cellTable() {
		tbMaterialImg = new MultiSpanCellTable();
		tbMaterialExg = new MultiSpanCellTable();
		tbMaterialImg.getColumnModel().addColumnModelListener(
				new TableColumnModelListener() {
					/** Tells listeners that a column was added to the model. */
					public void columnAdded(TableColumnModelEvent e) {
					}

					/**
					 * Tells listeners that a column was removed from the model.
					 */
					public void columnRemoved(TableColumnModelEvent e) {
					}

					/** Tells listeners that a column was repositioned. */
					public void columnMoved(TableColumnModelEvent e) {
					}

					/**
					 * Tells listeners that a column was moved due to a margin
					 * change.
					 */
					public void columnMarginChanged(ChangeEvent e) {
					}

					public void columnSelectionChanged(ListSelectionEvent e) {
						int[] columns = ((MultiSpanCellTable) tbMaterialImg)
								.getSelectedColumns();
						int[] rows = ((MultiSpanCellTable) tbMaterialImg)
								.getSelectedRows();
						// int[] columns = jTable.getSelectedColumns();
						// int[] rows = jTable.getSelectedRows();
						if (columns.length < 1 || rows.length < 1) {
							return;
						}
						// for (int i = 0; i < rows.length; i++) {
						// System.out.println("row:" + rows[i]);
						// }
						if (columns[0] >= 0 && columns[0] <= 5) {
							tbMaterialImg.setColumnSelectionInterval(1, 5);
							return;
						} else if (columns[0] >= 6 && columns[0] <= 10) {
							tbMaterialImg.setColumnSelectionInterval(6, 10);
							return;
						}
					}
				});
		tbMaterialExg.getColumnModel().addColumnModelListener(
				new TableColumnModelListener() {
					/** Tells listeners that a column was added to the model. */
					public void columnAdded(TableColumnModelEvent e) {
					}

					/**
					 * Tells listeners that a column was removed from the model.
					 */
					public void columnRemoved(TableColumnModelEvent e) {
					}

					/** Tells listeners that a column was repositioned. */
					public void columnMoved(TableColumnModelEvent e) {
					}

					/**
					 * Tells listeners that a column was moved due to a margin
					 * change.
					 */
					public void columnMarginChanged(ChangeEvent e) {
					}

					public void columnSelectionChanged(ListSelectionEvent e) {
						int[] columns = ((MultiSpanCellTable) tbMaterialExg)
								.getSelectedColumns();
						int[] rows = ((MultiSpanCellTable) tbMaterialExg)
								.getSelectedRows();
						// int[] columns = jTable.getSelectedColumns();
						// int[] rows = jTable.getSelectedRows();
						if (columns.length < 1 || rows.length < 1) {
							return;
						}
						// for (int i = 0; i < rows.length; i++) {
						// System.out.println("row:" + rows[i]);
						// }
						if (columns[0] >= 0 && columns[0] <= 5) {
							tbMaterialExg.setColumnSelectionInterval(1, 5);
							return;
						} else if (columns[0] >= 6 && columns[0] <= 10) {
							tbMaterialExg.setColumnSelectionInterval(6, 10);
							return;
						}
					}
				});
		jScrollPane.setViewportView(getTbMaterialImg());
		jScrollPane1.setViewportView(getTbMaterialExg());
	}

	public boolean isEditBill() {
		return isEditBill;
	}

	public void setEditBill(boolean isEditBill) {
		this.isEditBill = isEditBill;
	}

	public void setVisible(boolean isFlag) {
		if (isFlag == true) {
			initUIComponents();
			if (tableModelHead.getCurrentRow() != null) {
				showData();
			}
		}
		super.setVisible(isFlag);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJPanel(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnPrint());
			jToolBar.add(getBtnUndo());
			jToolBar.add(getBtnCheck());
			jToolBar.add(getBtnBOMExport());
			jToolBar.add(getBtnCal());
			jToolBar.add(getBtnShowUnitWaste());
			jToolBar.add(getBtnChangeDeclareState());
			jToolBar.add(getBtnCopyBill());
			jToolBar.add(getJButton());
			jToolBar.add(getJButton5());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印表格");
			btnPrint.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// DzscClientHelper.getInstance().printDzscEmsPorReport(
					// DgDzscEmsPor.this, head,
					// cbbPrint.getSelectedIndex());
					getJPopupMenuPrintReport().show(btnPrint, 0,
							btnPrint.getHeight());
				}
			});
		}
		return btnPrint;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	/*
	 * private JComboBox getCbbPrint() { if (cbbPrint == null) { cbbPrint = new
	 * JComboBox(); cbbPrint.setBounds(new Rectangle(11, 3, 147, 24)); } return
	 * cbbPrint; }
	 */

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!saveHeadData()) {
						return;
					} else {
						dataState = DataState.BROWSE;
						setState();
						JOptionPane.showMessageDialog(DgDzscEmsPor.this,
								"通关备案表头保存成功", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}

				}
			});
		}
		return btnSave;
	}

	/**
	 * 判断手册号是否重复
	 * 
	 * @param head
	 * @return
	 */
	private boolean checkDzscEmsPorHeadDuple(DzscEmsPorHead head) {
		return this.dzscAction.checkDzscEmsPorHeadDuple(new Request(CommonVars
				.getCurrUser(), true), head);
	}

	/**
	 * 判断企业内部编号是否重复
	 * 
	 * @param head
	 * @return
	 */
	private boolean checkDzscCopEmsNoDuple(DzscEmsPorHead head) {
		return this.dzscAction.checkDzscEmsPorCopEmsNoDuple(new Request(
				CommonVars.getCurrUser(), true), head);
	}

	private boolean checkData() {
		// double imgAmount = this.objToDouble(this.tfImgAmount.getValue());
		// double exgAmount = this.objToDouble(this.tfExgAmount.getValue());
		// if (imgAmount <= 0) {
		// JOptionPane.showMessageDialog(DgDzscEmsPor.this, "进口金额不能为空或不能等于零",
		// "提示", JOptionPane.INFORMATION_MESSAGE);
		// return true;
		// }
		// if (imgAmount > exgAmount) {
		// JOptionPane.showMessageDialog(DgDzscEmsPor.this, "出口金额应该大于或等于进口金额",
		// "提示", JOptionPane.INFORMATION_MESSAGE);
		// return true;
		// }
		return false;
	}

	private List checkFrameData() {
		// 基本情况检查
		List list = new ArrayList();
		EntityShowCustomerError showerr = null;
		String err = "";
		if (cbbEmsType.getSelectedItem() == null) {// 合同性质
			showerr = new EntityShowCustomerError();
			showerr.setError("[基本情况]合同性质不能为空");
			list.add(showerr);
		}
		if (tfPreEmsNo.getText() == null || tfPreEmsNo.getText().equals("")) {// 企业内部编号
			showerr = new EntityShowCustomerError();
			showerr.setError("[基本情况]企业内部编号不能为空");
			list.add(showerr);
		}
		if (cbbDeclareCustoms.getSelectedItem() == null) {// 主管海关
			showerr = new EntityShowCustomerError();
			showerr.setError("[基本情况]主管海关不能为空");
			list.add(showerr);
		}
		if (cbbRedDep.getSelectedItem() == null) {// 主管外经贸部门
			showerr = new EntityShowCustomerError();
			showerr.setError("[基本情况]主管外经贸部门不能为空");
			list.add(showerr);
		}
		if (cbbManageObject.getSelectedItem() == null) {// 管理对象
			showerr = new EntityShowCustomerError();
			showerr.setError("[基本情况]管理对象不能为空");
			list.add(showerr);
		}
		if (cbbBeginDate.getDate() == null) {// 起始日期
			showerr = new EntityShowCustomerError();
			showerr.setError("[基本情况]起始日期不能为空");
			list.add(showerr);
		}
		if (jComboBox5.getSelectedItem() == null) {// 贸易方式
			showerr = new EntityShowCustomerError();
			showerr.setError("[基本情况]贸易方式不能为空");
			list.add(showerr);
		}
		// if (jComboBox7.getSelectedItem() == null) {// 贸易国别
		// showerr = new EntityShowCustomerError();
		// showerr.setError("[基本情况]贸易国别不能为空");
		// list.add(showerr);
		// }
		if (cbbEndDate.getDate() == null) {// 有效日期
			showerr = new EntityShowCustomerError();
			showerr.setError("[基本情况]有效日期不能为空");
			list.add(showerr);
		}
		if (jTextField5.getText() == null || jTextField5.getText().equals("")) {// 企业地址
			showerr = new EntityShowCustomerError();
			showerr.setError("[基本情况]企业地址不能为空");
			list.add(showerr);
		}
		// if (cbbDestroyDate.getDate() == null) {// 核销到期
		// showerr = new EntityShowCustomerError();
		// showerr.setError("[基本情况]核销到期不能为空");
		// list.add(showerr);
		// }
		if (jTextField6.getText() == null || jTextField5.getText().equals("")) {// 联系人
			showerr = new EntityShowCustomerError();
			showerr.setError("[基本情况]联系人不能为空");
			list.add(showerr);
		}
		if (jTextField7.getText() == null || jTextField7.getText().equals("")) {// 联系电话
			showerr = new EntityShowCustomerError();
			showerr.setError("[基本情况]联系电话不能为空");
			list.add(showerr);
		}
		if (jComboBox8.getSelectedItem() == null) {// 征免性质
			showerr = new EntityShowCustomerError();
			showerr.setError("[基本情况]征免性质不能为空");
			list.add(showerr);
		}
		if (jTextField8.getText() == null || jTextField8.getText().equals("")) {// 外商公司
			showerr = new EntityShowCustomerError();
			showerr.setError("[基本情况]外商公司不能为空");
			list.add(showerr);
		}
		if (tfImpContractNo.getText() == null
				|| tfImpContractNo.getText().equals("")) {// 进口合同号
			showerr = new EntityShowCustomerError();
			showerr.setError("[基本情况]进口合同号不能为空");
			list.add(showerr);
		}
		if (tfImgAmount.getText() == null || tfImgAmount.getText().equals("")) {// 进口总值
			showerr = new EntityShowCustomerError();
			showerr.setError("[基本情况]进口总值不能为空");
			list.add(showerr);
		}
		if (jComboBox9.getSelectedItem() == null) {// 币制
			showerr = new EntityShowCustomerError();
			showerr.setError("[基本情况]币制不能为空");
			list.add(showerr);
		}
		if (cbbReceiveArea.getSelectedItem() == null) {// 收货地区
			showerr = new EntityShowCustomerError();
			showerr.setError("[基本情况]收货地区不能为空");
			list.add(showerr);
		}
		if (jComboBox17.getSelectedItem() == null) {// 加工总类
			showerr = new EntityShowCustomerError();
			showerr.setError("[基本情况]加工总类不能为空");
			list.add(showerr);
		}
		if (jTextField13.getText() == null || jTextField13.getText().equals("")) {// 批准文号
			showerr = new EntityShowCustomerError();
			showerr.setError("[基本情况]批准文号不能为空");
			list.add(showerr);
		}

		// 料件备案检查
		if (tableModelImgBill == null) {
			tableModelImgBill = DzscClientLogic.initTableImgBill(tbImgBill,
					head); // 料件清单
		}
		DzscEmsImgBill checkdzscEmsImgBill = null;
		int i = 0;
		tableModelImgBill.setTableFirstSelectRow();
		for (i = 0; i < tableModelImgBill.getSize(); i++) {
			checkdzscEmsImgBill = (DzscEmsImgBill) tableModelImgBill
					.getCurrentRow();

			if (checkdzscEmsImgBill.getSeqNum() == null) {// 备案序号
				showerr = new EntityShowCustomerError();
				showerr.setError("[料件备案]存在空的备案序号");
				list.add(showerr);
				break;
			}

			if (checkdzscEmsImgBill.getComplex() == null) {// 海关编码
				showerr = new EntityShowCustomerError();
				showerr.setError("[料件备案:(备案序号:"
						+ checkdzscEmsImgBill.getSeqNum() + ")]商品编码不能为空");
				list.add(showerr);
			}

			if (checkdzscEmsImgBill.getName() == null) {// 名称
				showerr = new EntityShowCustomerError();
				showerr.setError("[料件备案:(备案序号:"
						+ checkdzscEmsImgBill.getSeqNum() + ")]商品名称不能为空");
				list.add(showerr);
			}

			if (checkdzscEmsImgBill.getUnit() == null) {// 单位
				showerr = new EntityShowCustomerError();
				showerr.setError("[料件备案:(备案序号:"
						+ checkdzscEmsImgBill.getSeqNum() + ")]商品单位不能为空");
				list.add(showerr);
			}

			if (checkdzscEmsImgBill.getAmount() == null
					|| checkdzscEmsImgBill.getAmount() == 0
					|| checkdzscEmsImgBill.getAmount() == 0.0) {// 数量
				showerr = new EntityShowCustomerError();
				showerr.setError("[料件备案:(备案序号:"
						+ checkdzscEmsImgBill.getSeqNum() + ")]商品数量不能为空或者为0");
				list.add(showerr);
			}
			checkdzscEmsImgBill = null;
			tableModelImgBill.nextRow();
		}

		checkdzscEmsImgBill = null;

		if (tableModelExgBill == null) {
			tableModelExgBill = DzscClientLogic.initTableExgBill(tbExgBill,
					head); // 成品备案
		} else {
			tableModelExgBill = null;
			tableModelExgBill = DzscClientLogic.initTableExgBill(tbExgBill,
					head); // 成品备案
		}

		DzscEmsExgBill dzscEmsExgBill = null;
		DzscEmsBomBill bom = null;

		tableModelExgBill.setTableFirstSelectRow();

		if (tableModelExgBill.getSize() > 0) {
			for (i = 0; i < tableModelExgBill.getSize(); i++) {
				dzscEmsExgBill = (DzscEmsExgBill) tableModelExgBill
						.getCurrentRow();
				if (dzscEmsExgBill.getSeqNum() == null) {// 备案序号
					showerr = new EntityShowCustomerError();
					showerr.setError("[成品及单耗表备案]存在空的备案序号");
					list.add(showerr);
					break;
				}

				if (dzscEmsExgBill.getComplex() == null) {// 海关编码
					showerr = new EntityShowCustomerError();
					showerr.setError("[成品及单耗表备案:(备案序号:"
							+ dzscEmsExgBill.getSeqNum() + ")]商品编码不能为空");
					list.add(showerr);
				}

				if (dzscEmsExgBill.getName() == null) {// 名称
					showerr = new EntityShowCustomerError();
					showerr.setError("[成品及单耗表备案:(备案序号:"
							+ dzscEmsExgBill.getSeqNum() + ")]商品名称不能为空");
					list.add(showerr);
				}

				if (dzscEmsExgBill.getUnit() == null) {// 单位
					showerr = new EntityShowCustomerError();
					showerr.setError("[成品及单耗表备案:(备案序号:"
							+ dzscEmsExgBill.getSeqNum() + ")]商品单位不能为空");
					list.add(showerr);
				}

				if (dzscEmsExgBill.getAmount() == null
						|| dzscEmsExgBill.getAmount() == 0) {// 数量
					showerr = new EntityShowCustomerError();
					showerr.setError("[成品及单耗表备案:(备案序号:"
							+ dzscEmsExgBill.getSeqNum() + ")]商品数量不能为空或者为0");
					list.add(showerr);
				}

				if (tableModelBomBill == null) {
					tableModelBomBill = DzscClientLogic.initTableBomBill(
							tbBomBill, (DzscEmsExgBill) tableModelExgBill
									.getCurrentRow());
				} else {
					tableModelBomBill = null;
					tableModelBomBill = DzscClientLogic.initTableBomBill(
							tbBomBill, (DzscEmsExgBill) tableModelExgBill
									.getCurrentRow());
				}
				tableModelBomBill.setTableFirstSelectRow();
				for (int j = 0; j < tableModelBomBill.getSize(); j++) {
					bom = (DzscEmsBomBill) tableModelBomBill.getCurrentRow();
					if (bom.getUnitWare() == null || bom.getUnitWare() == 0) {// 单耗
						showerr = new EntityShowCustomerError();
						showerr.setError("[成品及单耗表备案:备案序号:"
								+ dzscEmsExgBill.getSeqNum() + "(料件总表序号:"
								+ bom.getImgSeqNum() + ")]单耗不能为空或者为0");
						list.add(showerr);
					}

					if (bom.getWare() == null) {// 损耗率
						showerr = new EntityShowCustomerError();
						showerr.setError("[成品及单耗表备案:备案序号:"
								+ dzscEmsExgBill.getSeqNum() + "(料件总表序号:"
								+ bom.getImgSeqNum() + ")]损耗率不能为空");
						list.add(showerr);
					}

					if (bom.getUnitDosage() == null) {// 单项用量
						showerr = new EntityShowCustomerError();
						showerr.setError("[成品及单耗表备案:备案序号:"
								+ dzscEmsExgBill.getSeqNum() + "(料件总表序号:"
								+ bom.getImgSeqNum() + ")]单项用量不能为空");
						list.add(showerr);
					}
					bom = null;
					tableModelBomBill.nextRow();

				}
				dzscEmsExgBill = null;
				tableModelExgBill.nextRow();
			}

		}
		dzscEmsExgBill = null;
		bom = null;
		return list;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnShowUnitWaste() {
		if (btnShowUnitWaste == null) {
			btnShowUnitWaste = new JButton();
			btnShowUnitWaste.setText("显示单耗表");
			btnShowUnitWaste
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgDzscShowUnitWasteBill dialog = new DgDzscShowUnitWasteBill();
							dialog.setHead(head);
							dialog.setVisible(true);
						}
					});
		}
		return btnShowUnitWaste;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCopyBill() {
		if (btnCopyBill == null) {
			btnCopyBill = new JButton();
			btnCopyBill.setText("转抄通关备案资料");
			btnCopyBill.setVisible(false);
			btnCopyBill.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgDzscCopyBill dg = new DgDzscCopyBill();
					dg.setHead(head);
					dg.setVisible(true);
					setVisible(true);
				}
			});
		}
		return btnCopyBill;
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setText("关闭");
			jButton5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgDzscEmsPor.this.dispose();
				}
			});
		}
		return jButton5;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.TOP);
			jTabbedPane.addTab("基本情况", null, getPnBase(), null);
			jTabbedPane.addTab("料件备案", null, getPnImgBill(), null);
			jTabbedPane.addTab("成品及单耗表备案", null, getPnExgBom(), null);
			jTabbedPane.addTab("归并料件", null, getPnMaterialImg(), null);
			jTabbedPane.addTab("归并成品", null, getPnMaterialExg(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							if (jTabbedPane.getSelectedIndex() != 0
									&& dataState != DataState.BROWSE
									&& isFirstTabbedPage) {
								if (!saveHeadData()) {
									return;
								}
							}
							showData();
							refreshTable();
							setState();
						}
					});
		}
		return jTabbedPane;
	}

	private void showData() {
		if (head == null) {
			head = (DzscEmsPorHead) tableModelHead.getCurrentRow();
		}
		if (jTabbedPane.getSelectedIndex() == 0) {
			showHeadData();
		} else if (jTabbedPane.getSelectedIndex() == 1) {
			tableModelImgBill = DzscClientLogic.initTableImgBill(tbImgBill,
					head); // 料件清单
			sumMoney();
		} else if (jTabbedPane.getSelectedIndex() == 2) {
			tableModelExgBill = DzscClientLogic.initTableExgBill(tbExgBill,
					head);
			tableModelBomBill = DzscClientLogic.initTableBomBill(tbBomBill,
					(DzscEmsExgBill) tableModelExgBill.getCurrentRow());
		} else if (jTabbedPane.getSelectedIndex() == 3) {
			tableModelMaterialImg = DzscClientLogic.initTableMaterialImg(
					tbMaterialImg, head); // 料件清单
			refreshTable();
			// getJScrollPane2().setViewportView(tbMaterialImg);
		} else if (jTabbedPane.getSelectedIndex() == 4) {
			tableModelMaterialExg = DzscClientLogic.initTableMaterialExg(
					tbMaterialExg, head); // 料件清单
			refreshTable();
		}
		isFirstTabbedPage = jTabbedPane.getSelectedIndex() == 0;
		setState();
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnBase() {
		if (pnBase == null) {
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(276, 455, 48, 18));
			jLabel7.setText("台账银行");
			jLabel7.setForeground(Color.blue);
			jLabel14 = new JLabel();
			jLabel14.setBounds(new Rectangle(37, 452, 74, 22));
			jLabel14
					.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel14.setText("录入日期");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(249, 403, 75, 19));
			jLabel.setForeground(Color.blue);
			jLabel.setText("单耗申报环节");
			jLabel50 = new JLabel();
			jLabel50.setBounds(new Rectangle(45, 403, 66, 22));
			jLabel50.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel50.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel50.setForeground(Color.blue);
			jLabel50.setText("限制类标志");
			jLabel49 = new JLabel();
			jLabel49.setBounds(new Rectangle(240, 42, 86, 25));
			jLabel49.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel49.setText("主管外经贸部门");
			jLabel49.setForeground(Color.blue);
			jLabel101 = new JLabel();
			jLabel101.setBounds(new Rectangle(241, 73, 84, 25));
			jLabel101.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel101.setText("经营单位名称");
			jLabel101.setForeground(Color.black);
			jLabel48 = new JLabel();
			jLabel48.setBounds(new Rectangle(244, 377, 97, 22));
			jLabel48.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel48.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel48.setText("物料备案内部编号");
			jLabel43 = new JLabel();
			jLabel43.setBounds(new Rectangle(22, 378, 89, 22));
			jLabel43.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel43.setText("合同备案手册号");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(52, 351, 59, 22));
			jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel1.setText("\u6536\u8d27\u5730\u533a");
			jLabel1.setForeground(Color.blue);
			jLabel42 = new JLabel();
			jLabel42.setBounds(new java.awt.Rectangle(457, 44, 85, 22));
			jLabel42.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel42.setText("管理对象");
			jLabel42.setForeground(java.awt.Color.blue);
			jLabel41 = new JLabel();
			jLabel40 = new JLabel();
			jLabel39 = new JLabel();
			jLabel38 = new JLabel();
			jLabel37 = new JLabel();
			jLabel36 = new JLabel();
			jLabel31 = new JLabel();
			jLabel30 = new JLabel();
			jLabel29 = new JLabel();
			jLabel28 = new JLabel();
			jLabel27 = new JLabel();
			jLabel26 = new JLabel();
			jLabel25 = new JLabel();
			jLabel24 = new JLabel();
			jLabel23 = new JLabel();
			jLabel22 = new JLabel();
			jLabel21 = new JLabel();
			jLabel20 = new JLabel();
			jLabel19 = new JLabel();
			jLabel18 = new JLabel();
			jLabel17 = new JLabel();
			jLabel16 = new JLabel();
			jLabel15 = new JLabel();
			jLabel13 = new JLabel();
			jLabel12 = new JLabel();
			jLabel11 = new JLabel();
			jLabel10 = new JLabel();
			jLabel9 = new JLabel();
			jLabel8 = new JLabel();
			jLabel6 = new JLabel();
			jLabel5 = new JLabel();
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			pnBase = new JPanel();
			pnBase.setLayout(null);
			jLabel2.setBounds(53, 16, 57, 22);
			jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel2.setText("合同状态");
			jLabel3.setBounds(464, 16, 78, 22);
			jLabel3.setForeground(java.awt.Color.blue);
			jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel3.setText("企业内部编号");
			jLabel4.setBounds(50, 45, 60, 22);
			jLabel4.setForeground(Color.blue);
			jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel4.setText("主管海关");
			jLabel5.setBounds(266, 16, 58, 22);
			jLabel5.setForeground(java.awt.Color.blue);
			jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel5.setText("合同性质");
			jLabel6.setBounds(51, 130, 60, 22);
			jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel6.setText("手册编号");
			// jLabel6.setForeground(java.awt.Color.blue);
			jLabel8.setBounds(260, 131, 66, 22);
			jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel8.setText("起始日期");
			jLabel8.setForeground(java.awt.Color.blue);
			jLabel9.setBounds(480, 131, 64, 22);
			jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel9.setText("有效日期");
			jLabel9.setForeground(java.awt.Color.blue);
			jLabel10.setBounds(54, 73, 57, 22);
			jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel10.setText("经营单位");
			jLabel10.setForeground(Color.black);
			jLabel11.setBounds(51, 160, 60, 22);
			jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel11.setText("贸易方式");
			jLabel11.setForeground(java.awt.Color.blue);
			jLabel12.setBounds(50, 101, 61, 22);
			jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel12.setText("收货单位");
			jLabel13.setBounds(262, 161, 63, 22);
			jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel13.setText("贸易国别");
			jLabel15.setBounds(244, 103, 82, 22);
			jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel15.setText("收货单位名称");
			jLabel16.setBounds(49, 187, 62, 22);
			jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel16.setText("企业地址");
			jLabel16.setForeground(java.awt.Color.blue);
			jLabel17.setBounds(481, 190, 63, 22);
			jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel17.setText("核销到期");
			jLabel18.setBounds(48, 219, 63, 22);
			jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel18.setText("联系人");
			jLabel18.setForeground(java.awt.Color.blue);
			jLabel19.setBounds(265, 219, 60, 22);
			jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel19.setText("联系电话");
			jLabel19.setForeground(java.awt.Color.blue);
			jLabel20.setBounds(486, 219, 57, 22);
			jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel20.setText("征免性质");
			jLabel20.setForeground(java.awt.Color.blue);
			jLabel21.setBounds(50, 246, 61, 22);
			jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel21.setText("外商公司");
			jLabel21.setForeground(java.awt.Color.blue);
			jLabel22.setBounds(484, 246, 59, 22);
			jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel22.setText("批文号");
			jLabel23.setBounds(51, 272, 60, 22);
			jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel23.setText("协议书号");
			jLabel24.setBounds(257, 271, 68, 22);
			jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel24.setText("进口合同号");
			jLabel24.setForeground(java.awt.Color.blue);
			jLabel25.setBounds(469, 272, 74, 22);
			jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel25.setText("出口合同号");
			jLabel26.setBounds(56, 298, 55, 22);
			jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel26.setText("进口总值");
			jLabel26.setForeground(java.awt.Color.blue);
			jLabel27.setBounds(261, 297, 64, 22);
			jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel27.setText("出口总值");
			jLabel28.setBounds(495, 298, 48, 22);
			jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel28.setText("币制");
			jLabel28.setForeground(java.awt.Color.blue);
			jLabel29.setBounds(48, 324, 63, 22);
			jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel29.setText("监管费率");
			jLabel30.setBounds(261, 323, 64, 22);
			jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel30.setText("监管费");
			jLabel31.setBounds(481, 324, 62, 22);
			jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel31.setText("成交方式");
			jLabel36.setBounds(474, 380, 67, 22);
			jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel36.setText("审批人");
			jLabel37.setBounds(484, 404, 57, 22);
			jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel37.setText("审批日期");
			jLabel38.setBounds(480, 353, 61, 22);
			jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel38.setText("保税方式");
			jLabel39.setBounds(260, 352, 67, 22);
			jLabel39.setForeground(java.awt.Color.blue);
			jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel39.setText("加工种类");
			jLabel40.setBounds(487, 161, 55, 22);
			jLabel40.setForeground(java.awt.Color.blue);
			jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel40.setText("批准文号");
			jLabel41.setBounds(73, 428, 38, 22);
			jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel41.setText("备注");
			pnBase.add(jLabel2, null);
			pnBase.add(getTfEmsState(), null);
			pnBase.add(jLabel3, null);
			pnBase.add(getTfPreEmsNo(), null);
			pnBase.add(jLabel4, null);
			pnBase.add(jLabel5, null);
			pnBase.add(getCbbDeclareCustoms(), null);
			pnBase.add(getCbbEmsType(), null);
			pnBase.add(jLabel6, null);
			pnBase.add(getTfEmsNo(), null);
			pnBase.add(jLabel8, null);
			pnBase.add(jLabel9, null);
			pnBase.add(getJCalendarComboBox(), null);
			pnBase.add(getCbbEndDate(), null);
			pnBase.add(jLabel10, null);
			pnBase.add(jLabel11, null);
			pnBase.add(getJComboBox5(), null);
			pnBase.add(jLabel12, null);
			pnBase.add(getJTextField3(), null);
			pnBase.add(jLabel13, null);
			pnBase.add(getJComboBox7(), null);
			pnBase.add(jLabel15, null);
			pnBase.add(getJTextField4(), null);
			pnBase.add(jLabel16, null);
			pnBase.add(getJTextField5(), null);
			pnBase.add(jLabel17, null);
			pnBase.add(jLabel18, null);
			pnBase.add(getJTextField6(), null);
			pnBase.add(jLabel19, null);
			pnBase.add(getJTextField7(), null);
			pnBase.add(jLabel20, null);
			pnBase.add(getJComboBox8(), null);
			pnBase.add(jLabel21, null);
			pnBase.add(getJTextField8(), null);
			pnBase.add(jLabel22, null);
			pnBase.add(getJTextField9(), null);
			pnBase.add(jLabel23, null);
			pnBase.add(getJTextField10(), null);
			pnBase.add(jLabel24, null);
			pnBase.add(getTfImpContractNo(), null);
			pnBase.add(jLabel25, null);
			pnBase.add(getTfExpContractNo(), null);
			pnBase.add(jLabel26, null);
			pnBase.add(jLabel27, null);
			pnBase.add(jLabel28, null);
			pnBase.add(getJComboBox9(), null);
			pnBase.add(jLabel29, null);
			pnBase.add(getCbbWardshipRate(), null);
			pnBase.add(jLabel30, null);
			pnBase.add(jLabel31, null);
			pnBase.add(getJComboBox11(), null);
			pnBase.add(jLabel36, null);
			pnBase.add(getJTextField16(), null);
			pnBase.add(jLabel37, null);
			pnBase.add(getJCalendarComboBox4(), null);
			pnBase.add(jLabel38, null);
			pnBase.add(getJComboBox16(), null);
			pnBase.add(jLabel39, null);
			pnBase.add(getJComboBox17(), null);
			pnBase.add(jLabel40, null);
			pnBase.add(jLabel41, null);
			pnBase.add(getJTextField17(), null);
			pnBase.add(getTfImgAmount(), null);
			pnBase.add(getTfExgAmount(), null);
			pnBase.add(getTfWardshipFee(), null);
			pnBase.add(getJTextField13(), null);
			pnBase.add(jLabel42, null);
			pnBase.add(getCbbManageObject(), null);
			pnBase.add(jLabel1, null);
			pnBase.add(getCbbReceiveArea(), null);
			pnBase.add(getCbbDestroyDate(), null);
			pnBase.add(jLabel43, null);
			pnBase.add(getTfCorrEmsNo(), null);
			pnBase.add(jLabel48, null);
			pnBase.add(getTfCopEntNo(), null);
			pnBase.add(getTfTradeCode(), null);
			pnBase.add(jLabel101, null);
			pnBase.add(getTfTradeName(), null);
			pnBase.add(jLabel49, null);
			pnBase.add(getCbbRedDep(), null);
			pnBase.add(jLabel50, null);
			pnBase.add(getCbbLimitFlag(), null);
			pnBase.add(getCbbEquipMode(), null);
			pnBase.add(jLabel, null);
			pnBase.add(jLabel14, null);
			pnBase.add(getCalendarSaveDate(), null);
			pnBase.add(getBtnIEPort(), null);
			pnBase.add(jLabel7, null);
			pnBase.add(getCbbBank(), null);
		}
		return pnBase;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnImgBill() {
		if (pnImgBill == null) {
			pnImgBill = new JPanel();
			pnImgBill.setLayout(new BorderLayout());
			pnImgBill.add(getJToolBar6(), java.awt.BorderLayout.NORTH);
			pnImgBill.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
		}
		return pnImgBill;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnExgBom() {
		if (pnExgBom == null) {
			pnExgBom = new JPanel();
			pnExgBom.setLayout(new BorderLayout());
			pnExgBom.add(getJSplitPane1(), java.awt.BorderLayout.CENTER);
		}
		return pnExgBom;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmsState() {
		if (tfEmsState == null) {
			tfEmsState = new JTextField();
			tfEmsState.setEditable(false);
			tfEmsState.setBounds(112, 16, 127, 22);
		}
		return tfEmsState;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPreEmsNo() {
		if (tfPreEmsNo == null) {
			tfPreEmsNo = new JTextField();
			tfPreEmsNo.setEditable(false);
			tfPreEmsNo.setBounds(543, 16, 127, 22);
		}
		return tfPreEmsNo;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbDeclareCustoms() {
		if (cbbDeclareCustoms == null) {
			cbbDeclareCustoms = new JComboBox();
			cbbDeclareCustoms.setBounds(112, 45, 127, 22);
		}
		return cbbDeclareCustoms;
	}

	/**
	 * This method initializes jComboBox3
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbEmsType() {
		if (cbbEmsType == null) {
			cbbEmsType = new JComboBox();
			cbbEmsType.setBounds(325, 16, 127, 22);
		}
		return cbbEmsType;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setBounds(113, 130, 127, 22);
			tfEmsNo.setEditable(false);
		}
		return tfEmsNo;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(327, 131, 127, 22);
			cbbBeginDate.addChangeListener(new ChangeListener() {

				public void stateChanged(ChangeEvent arg0) {
					if (dataState == DataState.BROWSE) {
						return;
					}
					if (head == null || head.getIsImportFromQP() == null
							|| head.getIsImportFromQP()) {
						return;
					}
					Calendar beginDate = cbbBeginDate.getCalendar();
					Calendar endDate = null;
					Calendar destroyDate = null;
					if (beginDate != null) {
						endDate = (Calendar) beginDate.clone();
						endDate.add(Calendar.MONTH, 6);
						destroyDate = (Calendar) endDate.clone();
						destroyDate.add(Calendar.MONTH, 1);
					}
					cbbEndDate.setCalendar(endDate);
					cbbDestroyDate.setCalendar(destroyDate);
				}

			});
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(544, 131, 127, 22);
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jComboBox5
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox5() {
		if (jComboBox5 == null) {
			jComboBox5 = new JComboBox();
			jComboBox5.setBounds(113, 160, 127, 22);
		}
		return jComboBox5;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setEditable(false);
			jTextField3.setBounds(327, 103, 344, 22);
		}
		return jTextField3;
	}

	/**
	 * This method initializes jComboBox7
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox7() {
		if (jComboBox7 == null) {
			jComboBox7 = new JComboBox();
			jComboBox7.setBounds(326, 161, 127, 22);
		}
		return jComboBox7;
	}

	/**
	 * This method initializes jTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
			jTextField4.setEditable(false);
			jTextField4.setBounds(113, 102, 127, 22);
		}
		return jTextField4;
	}

	/**
	 * This method initializes jTextField5
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField5() {
		if (jTextField5 == null) {
			jTextField5 = new JTextField();
			jTextField5.setBounds(113, 188, 340, 22);
		}
		return jTextField5;
	}

	/**
	 * This method initializes jCalendarComboBox3
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbDestroyDate() {
		if (cbbDestroyDate == null) {
			cbbDestroyDate = new JCalendarComboBox();
			cbbDestroyDate.setBounds(new Rectangle(544, 190, 127, 22));
		}
		return cbbDestroyDate;
	}

	/**
	 * This method initializes jTextField6
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField6() {
		if (jTextField6 == null) {
			jTextField6 = new JTextField();
			jTextField6.setBounds(113, 219, 127, 22);
		}
		return jTextField6;
	}

	/**
	 * This method initializes jTextField7
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField7() {
		if (jTextField7 == null) {
			jTextField7 = new JTextField();
			jTextField7.setBounds(326, 219, 127, 22);
		}
		return jTextField7;
	}

	/**
	 * This method initializes jComboBox8
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox8() {
		if (jComboBox8 == null) {
			jComboBox8 = new JComboBox();
			jComboBox8.setBounds(544, 219, 127, 22);
		}
		return jComboBox8;
	}

	/**
	 * This method initializes jTextField8
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField8() {
		if (jTextField8 == null) {
			jTextField8 = new JTextField();
			jTextField8.setBounds(113, 246, 340, 22);
		}
		return jTextField8;
	}

	/**
	 * This method initializes jTextField9
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField9() {
		if (jTextField9 == null) {
			jTextField9 = new JTextField();
			jTextField9.setBounds(544, 246, 127, 22);
		}
		return jTextField9;
	}

	/**
	 * This method initializes jTextField10
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField10() {
		if (jTextField10 == null) {
			jTextField10 = new JTextField();
			jTextField10.setBounds(113, 272, 127, 22);
		}
		return jTextField10;
	}

	/**
	 * This method initializes jTextField11
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfImpContractNo() {
		if (tfImpContractNo == null) {
			tfImpContractNo = new JTextField();
			tfImpContractNo.setBounds(326, 271, 127, 22);
			tfImpContractNo.getDocument().addDocumentListener(
					new DocumentListener() {

						public void insertUpdate(DocumentEvent e) {
							tfExpContractNo.setText(tfImpContractNo.getText());
						}

						public void removeUpdate(DocumentEvent e) {
							tfExpContractNo.setText(tfImpContractNo.getText());
						}

						public void changedUpdate(DocumentEvent e) {
							tfExpContractNo.setText(tfImpContractNo.getText());
						}

					});
		}
		return tfImpContractNo;
	}

	/**
	 * This method initializes jTextField12
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExpContractNo() {
		if (tfExpContractNo == null) {
			tfExpContractNo = new JTextField();
			tfExpContractNo.setBounds(544, 272, 127, 22);
		}
		return tfExpContractNo;
	}

	/**
	 * This method initializes jComboBox9
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox9() {
		if (jComboBox9 == null) {
			jComboBox9 = new JComboBox();
			jComboBox9.setBounds(544, 298, 127, 22);
		}
		return jComboBox9;
	}

	/**
	 * This method initializes jComboBox10
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbWardshipRate() {
		if (cbbWardshipRate == null) {
			cbbWardshipRate = new JComboBox();
			cbbWardshipRate.setBounds(113, 324, 127, 22);
			cbbWardshipRate.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						setWardshipFee();
					}
				}
			});
		}
		return cbbWardshipRate;
	}

	/**
	 * This method initializes jComboBox11
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox11() {
		if (jComboBox11 == null) {
			jComboBox11 = new JComboBox();
			jComboBox11.setBounds(544, 324, 127, 22);
		}
		return jComboBox11;
	}

	/**
	 * This method initializes jTextField16
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField16() {
		if (jTextField16 == null) {
			jTextField16 = new JTextField();
			jTextField16.setBounds(544, 380, 127, 22);
		}
		return jTextField16;
	}

	/**
	 * This method initializes jCalendarComboBox4
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox4() {
		if (jCalendarComboBox4 == null) {
			jCalendarComboBox4 = new JCalendarComboBox();
			jCalendarComboBox4.setBounds(544, 404, 127, 22);
		}
		return jCalendarComboBox4;
	}

	/**
	 * This method initializes jComboBox16
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox16() {
		if (jComboBox16 == null) {
			jComboBox16 = new JComboBox();
			jComboBox16.setBounds(544, 354, 127, 22);
		}
		return jComboBox16;
	}

	/**
	 * This method initializes jComboBox17
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox17() {
		if (jComboBox17 == null) {
			jComboBox17 = new JComboBox();
			jComboBox17.setBounds(328, 352, 127, 22);
		}
		return jComboBox17;
	}

	/**
	 * This method initializes jTextField17
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField17() {
		if (jTextField17 == null) {
			jTextField17 = new JTextField();
			jTextField17.setBounds(113, 428, 343, 22);
		}
		return jTextField17;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfImgAmount() {
		if (tfImgAmount == null) {
			tfImgAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfImgAmount.setText("0");
			tfImgAmount.setBounds(113, 297, 127, 22);
			tfImgAmount.setToolTipText("国内购买不计算在内");
			tfImgAmount.getDocument().addDocumentListener(
					new DocumentListener() {

						public void insertUpdate(DocumentEvent e) {
							setWardshipFee();
						}

						public void removeUpdate(DocumentEvent e) {
							setWardshipFee();

						}

						public void changedUpdate(DocumentEvent e) {
							setWardshipFee();

						}

					});
		}
		return tfImgAmount;
	}

	/**
	 * This method initializes jFormattedTextField1
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfExgAmount() {
		if (tfExgAmount == null) {
			tfExgAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfExgAmount.setText("0");
			tfExgAmount.setBounds(326, 299, 127, 22);
		}
		return tfExgAmount;
	}

	/**
	 * This method initializes jFormattedTextField2
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfWardshipFee() {
		if (tfWardshipFee == null) {
			tfWardshipFee = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfWardshipFee.setText("0");
			tfWardshipFee.setBounds(326, 325, 127, 22);
		}
		return tfWardshipFee;
	}

	/**
	 * This method initializes jTextField13
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField13() {
		if (jTextField13 == null) {
			jTextField13 = new JTextField();
			jTextField13.setBounds(543, 160, 127, 22);
		}
		return jTextField13;
	}

	/**
	 * @return Returns the tableModelHead.
	 */
	public JTableListModel getTableModelHead() {
		return tableModelHead;
	}

	/**
	 * @param tableModelHead
	 *            The tableModelHead to set.
	 */
	public void setTableModelHead(JTableListModel tableModelHead) {
		this.tableModelHead = tableModelHead;
	}

	/**
	 * This method initializes jToolBar6
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar6() {
		if (jToolBar6 == null) {
			jLabel47 = new JLabel();
			jLabel44 = new JLabel();
			jToolBar6 = new JToolBar();
			jToolBar6.setFloatable(false);
			jLabel44.setText(" 合同料件汇总表     ");
			jLabel44.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
					14));
			jLabel44.setForeground(new java.awt.Color(0, 51, 255));
			jLabel47.setText("      金额合计：");
			jLabel47.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
					14));
			jLabel47.setForeground(java.awt.Color.blue);
			jToolBar6.add(jLabel44);
			// jToolBar6.add(getJButton15());
			// jToolBar6.add(getJButton16());
			jToolBar6.add(getBtnAddImgBill());
			jToolBar6.add(getJButton18());
			jToolBar6.add(getJButton19());
			jToolBar6.add(getBtnChangeImgModifyMark());
			jToolBar6.add(getBtnMaterielSort());
			jToolBar6.add(jLabel47);
		}
		return jToolBar6;
	}

	/**
	 * This method initializes jButton17
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddImgBill() {
		if (btnAddImgBill == null) {
			btnAddImgBill = new JButton();
			btnAddImgBill.setText("增加料件");// 料件清单
			btnAddImgBill
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// Integer dzscManageType = null;
							// DzscEmsPorWjHead wjHead = dzscAction
							// .findExingDzscEmsPorWjHeadByEmsNo(
							// new Request(CommonVars
							// .getCurrUser()), head
							// .getCorrEmsNo());
							// if (wjHead != null) {
							// dzscManageType = wjHead.getDzscManageType();
							// }
							// if (dzscManageType == null) {
							// dzscManageType = DzscCommon.getInstance()
							// .getDzscManageType();
							// }
							if (head.getCopEntNo() != null
									&& !"".equals(head.getCopEntNo().trim())) {
								DgDzscEmsPorCommFrom dg = new DgDzscEmsPorCommFrom();
								dg.setDzscEmsPorHead(head);
								dg.setMaterial(true);
								dg.setTitle("料件(来自于已备案的归并关系10码资料)");
								dg.setVisible(true);
								if (dg.isOk()) {
									List list = dg.getReturnList();
									if (list.size() > 0) {
										tableModelImgBill.addRows(list);
										sumMoney();
									}
								}
							} else {
								List list = CommonQueryPage.getInstance()
										.getComplex();
								if (list == null || list.size() <= 0) {
									return;
								}
								list = dzscAction
										.saveDzscEmsPorImgExgByComplex(
												new Request(CommonVars
														.getCurrUser()), head,
												true, list);
								tableModelImgBill.addRows(list);
							}
						}
					});
		}
		return btnAddImgBill;
	}

	/**
	 * This method initializes jButton18
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton18() {
		if (jButton18 == null) {
			jButton18 = new JButton();
			jButton18.setText("修改料件");
			jButton18.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelImgBill.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgDzscEmsPor.this,
								"请选中要修改的料件!", "提示", 2);
						return;
					}
					DgDzscEmsPorImgBill dg = new DgDzscEmsPorImgBill();
					dg.setHead(head);
					dg.setTableModel(tableModelImgBill);
					dg.setDataState(DataState.EDIT);
					dg.setVisible(true);
					sumMoney();
				}
			});
		}
		return jButton18;
	}

	/**
	 * This method initializes jButton19
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton19() {
		if (jButton19 == null) {
			jButton19 = new JButton();
			jButton19.setText("删除料件");// 清单
			jButton19.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelImgBill.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgDzscEmsPor.this,
								"请选择要删除的料件资料!", "提示", 2);
						return;
					}
					if (JOptionPane.showConfirmDialog(DgDzscEmsPor.this,
							"确定要删除吗?", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					List lsImg = tableModelImgBill.getCurrentRows();
					List<DzscEmsImgBill> overlap=dzscAction.findDzscEmsBomBill(new Request(CommonVars
							.getCurrUser()), lsImg);
					if (overlap.size() > 0) {
						DzscEmsImgBill dzscEmsImgBill = overlap.get(0);
						if (JOptionPane.showConfirmDialog(DgDzscEmsPor.this,
								"料件'"+dzscEmsImgBill.getComplex().getCode()+"'在成品BOM中存在，确定要删除吗?", "提示",
								JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
							return;
						}
					}
					Map<Integer, List<DzscEmsImgBill>> map = dzscAction
							.deleteDzscEmsImgBill(new Request(CommonVars
									.getCurrUser()), lsImg);
					List lsDelete = map.get(DeleteResultMark.DELETE);
					if (lsDelete != null && lsDelete.size() > 0) {
						tableModelImgBill.deleteRows(lsDelete);
					}
					List lsUpdate = map.get(DeleteResultMark.UPDATE);
					if (lsUpdate != null && lsUpdate.size() > 0) {
						tableModelImgBill.updateRows(lsUpdate);
					}
				}
			});
		}
		return jButton19;
	}

	/**
	 * This method initializes jTable2
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbImgBill() {
		if (tbImgBill == null) {
			tbImgBill = new JTable();
		}
		return tbImgBill;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getTbImgBill());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jSplitPane1
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane1.setDividerSize(5);
			jSplitPane1.setDividerLocation(220);
			jSplitPane1.setTopComponent(getJPanel7());
			jSplitPane1.setBottomComponent(getJPanel8());
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes jPanel7
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			jPanel7 = new JPanel();
			jPanel7.setLayout(new BorderLayout());
			jPanel7.add(getJToolBar7(), java.awt.BorderLayout.NORTH);
			jPanel7.add(getJScrollPane3(), java.awt.BorderLayout.CENTER);
		}
		return jPanel7;
	}

	/**
	 * This method initializes jPanel8
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel8() {
		if (jPanel8 == null) {
			jPanel8 = new JPanel();
			jPanel8.setLayout(new BorderLayout());
			jPanel8.add(getJToolBar8(), java.awt.BorderLayout.NORTH);
			jPanel8.add(getJScrollPane4(), java.awt.BorderLayout.CENTER);
		}
		return jPanel8;
	}

	/**
	 * This method initializes jToolBar7
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar7() {
		if (jToolBar7 == null) {
			jLabel45 = new JLabel();
			jToolBar7 = new JToolBar();
			jToolBar7.setFloatable(false);
			jLabel45.setText("  出口成品表   ");
			jLabel45.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
					14));
			jLabel45.setForeground(java.awt.Color.blue);
			jToolBar7.add(jLabel45);
			// jToolBar7.add(getJButton20());
			jToolBar7.add(getBtnAddExgBill());
			jToolBar7.add(getJButton22());
			jToolBar7.add(getBtnProcessPrice());
			jToolBar7.add(getJButton23());
			jToolBar7.add(getBtnChangeExgModifyMark());
			jToolBar7.add(getBtnSaveDetail());
			jToolBar7.add(getBtnUnitWareDiff());
			jToolBar7.add(getBtnFinishProductSort());
		}
		return jToolBar7;
	}

	/**
	 * This method initializes jButton21
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddExgBill() {
		if (btnAddExgBill == null) {
			btnAddExgBill = new JButton();
			btnAddExgBill.setText("增加成品");
			btnAddExgBill
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// Integer dzscManageType = null;
							// DzscEmsPorWjHead wjHead = dzscAction
							// .findExingDzscEmsPorWjHeadByEmsNo(
							// new Request(CommonVars
							// .getCurrUser()), head
							// .getCorrEmsNo());
							// if (wjHead != null) {
							// dzscManageType = wjHead.getDzscManageType();
							// }
							// if (dzscManageType == null) {
							// dzscManageType = DzscCommon.getInstance()
							// .getDzscManageType();
							// }
							if (head.getCopEntNo() != null
									&& !"".equals(head.getCopEntNo().trim())) {
								DgDzscEmsPorCommFrom dg = new DgDzscEmsPorCommFrom();
								dg.setDzscEmsPorHead(head);
								dg.setMaterial(false);
								dg.setTitle("成品(来自于已备案的归并关系10码资料)");
								dg.setVisible(true);
								if (dg.isOk()) {
									List list = dg.getReturnList();
									if (list.size() > 0) {
										tableModelExgBill.addRows(list);
										sumMoney();
									}
								}
							} else {
								List list = CommonQueryPage.getInstance()
										.getComplex();
								if (list == null || list.size() <= 0) {
									return;
								}
								list = dzscAction
										.saveDzscEmsPorImgExgByComplex(
												new Request(CommonVars
														.getCurrUser()), head,
												false, list);
								tableModelExgBill.addRows(list);
							}
						}
					});
		}
		return btnAddExgBill;
	}

	/**
	 * This method initializes jButton22
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton22() {
		if (jButton22 == null) {
			jButton22 = new JButton();
			jButton22.setText("修改成品");
			jButton22.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelExgBill.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgDzscEmsPor.this,
								"请选中要修改的成品!", "提示", 2);
						return;
					}
					DgDzscEmsPorExgBill dg = new DgDzscEmsPorExgBill();
					dg.setTableModel(tableModelExgBill);
					dg.setDataState(DataState.EDIT);
					dg.setVisible(true);
				}
			});
		}
		return jButton22;
	}

	/**
	 * This method initializes jButton23
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton23() {
		if (jButton23 == null) {
			jButton23 = new JButton();
			jButton23.setText("删除成品");
			jButton23.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelExgBill.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgDzscEmsPor.this,
								"请选中要删除的成品!", "提示", 2);
						return;
					}
					if (JOptionPane.showConfirmDialog(DgDzscEmsPor.this,
							"确定要删除吗?", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					List list = tableModelExgBill.getCurrentRows();
					Map<Integer, List<DzscEmsExgBill>> map = dzscAction
							.deleteDzscEmsExgBill(new Request(CommonVars
									.getCurrUser()), list);
					List lsDelete = map.get(DeleteResultMark.DELETE);
					if (lsDelete != null && lsDelete.size() > 0) {
						tableModelExgBill.deleteRows(lsDelete);
					}
					List lsUpdate = map.get(DeleteResultMark.UPDATE);
					if (lsUpdate != null && lsUpdate.size() > 0) {
						tableModelExgBill.updateRows(lsUpdate);
					}
					tableModelBomBill = DzscClientLogic.initTableBomBill(
							tbBomBill, (DzscEmsExgBill) tableModelExgBill
									.getCurrentRow());
				}
			});
		}
		return jButton23;
	}

	/**
	 * This method initializes jToolBar8
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar8() {
		if (jToolBar8 == null) {
			lbExgInfo = new JLabel();
			lbExgInfo.setText("");
			lbExgInfo.setForeground(java.awt.Color.blue);
			jLabel46 = new JLabel();
			jToolBar8 = new JToolBar();
			jToolBar8.setFloatable(false);
			jLabel46.setText("  单耗表   ");
			jLabel46.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
					14));
			jLabel46.setForeground(java.awt.Color.blue);
			jToolBar8.add(jLabel46);
			jToolBar8.add(getJButton24());
			jToolBar8.add(getJButton25());
			jToolBar8.add(getJButton26());
			jToolBar8.add(getJButton27());
			jToolBar8.add(getJButton28());
			jToolBar8.add(getBtnSaveBom());
			jToolBar8.add(getBtnBomUndo());
			jToolBar8.add(getBtnChangeBomModifyMark());
			jToolBar8.add(getCbSelectAllBom());
			jToolBar8.add(lbExgInfo);
		}
		return jToolBar8;
	}

	/**
	 * This method initializes jButton24
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton24() {
		if (jButton24 == null) {
			jButton24 = new JButton();
			jButton24.setText("导入单耗");
			jButton24.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getPmImportUnitWaste().show(jButton24, 0,
							jButton24.getHeight());
				}
			});
		}
		return jButton24;
	}

	/**
	 * This method initializes jButton25
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton25() {
		if (jButton25 == null) {
			jButton25 = new JButton();
			jButton25.setText("增加料件"); // 单耗表
			jButton25.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelExgBill.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgDzscEmsPor.this,
								"请先选中成品!", "提示", 2);
						return;
					}
					DzscEmsExgBill exg = (DzscEmsExgBill) tableModelExgBill
							.getCurrentRow();
					tableModelBomBill = DzscClientLogic.newEmsPorBomBill(exg,
							tableModelBomBill);
				}
			});
		}
		return jButton25;
	}

	/**
	 * This method initializes jButton26
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton26() {
		if (jButton26 == null) {
			jButton26 = new JButton();
			jButton26.setText("修改单耗");
			jButton26.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelBomBill.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgDzscEmsPor.this,
								"请选中要修改的Bom资料", "提示", 2);
						return;
					}
					DzscEmsExgBill exgBill = ((DzscEmsBomBill) tableModelBomBill
							.getCurrentRow()).getDzscEmsExgBill();
					DgDzscEmsPorBomBill dg = new DgDzscEmsPorBomBill();
					dg.setDzscEmsExgBill(exgBill);
					dg.setTableModel(tableModelBomBill);
					dg.setVisible(true);
					if (exgBill != null) {
						exgBill = (DzscEmsExgBill) dzscAction.load(
								DzscEmsExgBill.class, exgBill.getId());
						if (exgBill != null) {
							tableModelExgBill.updateRow(exgBill);
						}
					}
				}
			});
		}
		return jButton26;
	}

	/**
	 * This method initializes jButton27
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton27() {
		if (jButton27 == null) {
			jButton27 = new JButton(); // 单耗
			jButton27.setText("删除单耗");
			jButton27.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelBomBill.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgDzscEmsPor.this,
								"请选中要删除的Bom资料", "提示", 2);
						return;
					}
					DzscEmsBomBill bom = (DzscEmsBomBill) tableModelBomBill
							.getCurrentRow();
					DzscEmsExgBill exg = bom.getDzscEmsExgBill();

					List list = tableModelBomBill.getCurrentRows();
					if (JOptionPane.showConfirmDialog(DgDzscEmsPor.this,
							"确定要删除吗?", "提示", 2) == 0) {
						Map<Integer, List<DzscEmsBomBill>> map = dzscAction
								.deleteDzscEmsBomBill(new Request(CommonVars
										.getCurrUser()), list);
						List lsDelete = map.get(DeleteResultMark.DELETE);
						if (lsDelete != null && lsDelete.size() > 0) {
							tableModelBomBill.deleteRows(lsDelete);
						}
						List lsUpdate = map.get(DeleteResultMark.UPDATE);
						if (lsUpdate != null && lsUpdate.size() > 0) {
							tableModelBomBill.updateRows(lsUpdate);
						}
						if (exg != null) {
							exg = (DzscEmsExgBill) dzscAction.load(
									DzscEmsExgBill.class, exg.getId());
							if (exg != null) {
								tableModelExgBill.updateRow(exg);
							}
						}
					}
				}
			});
		}
		return jButton27;
	}

	protected void deleteBom() {
		DzscEmsExgBill exg = (DzscEmsExgBill)(tableModelExgBill.getCurrentRow());

		List list = tableModelBomBill.getList();
		Map<Integer, List<DzscEmsBomBill>> map = dzscAction
				.deleteDzscEmsBomBill(new Request(CommonVars.getCurrUser()),
						list);
		List lsDelete = map.get(DeleteResultMark.DELETE);
		if (lsDelete != null && lsDelete.size() > 0) {
			tableModelBomBill.deleteRows(lsDelete);
		}
		List lsUpdate = map.get(DeleteResultMark.UPDATE);
		if (lsUpdate != null && lsUpdate.size() > 0) {
			tableModelBomBill.updateRows(lsUpdate);
		}
		if (exg != null) {
			exg = (DzscEmsExgBill) dzscAction.load(DzscEmsExgBill.class, exg
					.getId());
			if (exg != null) {
				tableModelExgBill.updateRow(exg);
			}
		}

	}

	/**
	 * This method initializes jButton28
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton28() {
		if (jButton28 == null) {
			jButton28 = new JButton();
			jButton28.setText("转抄单耗");
			jButton28.setVisible(true);
			jButton28.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelExgBill.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgDzscEmsPor.this,
								"请选中要转抄的单耗资料", "提示", 2);
						return;
					}
					DzscEmsExgBill to = (DzscEmsExgBill) tableModelExgBill
							.getCurrentRow();
					List dataSource = new ArrayList();
					if (cbSelectAllBom.isSelected()) {
						dataSource.addAll(dzscAction.findAllDzscEmsExgBill());
					} else {
						dataSource.addAll(tableModelExgBill.getList());
					}
					dataSource.remove(to);
					DzscEmsExgBill from = DzscClientHelper.getInstance()
							.getDzscEmsBomBillForCopy(dataSource);
					if (from == null) {
						return;
					}
					if (JOptionPane.showConfirmDialog(DgDzscEmsPor.this,
							"确定要转抄单耗吗?", "提示", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
						return;
					}
					//删除成品原来bom
					deleteBom();
					if (!cbSelectAllBom.isSelected()) {
						dzscAction.copyDzscEmsPorBomBill(new Request(CommonVars
								.getCurrUser()), from, to);
					} else {
						dzscAction.copyOtherDzscEmsPorBomBill(new Request(
								CommonVars.getCurrUser()), from, to);
					}
					{
						Vector vet = new Vector();
						List list1 = dzscAction.findEmsPorImgBillModifyMark(
								new Request(CommonVars.getCurrUser()),
								head, ModifyMarkState.ADDED);
						int beginSeqNum = dzscAction
								.findMaxEmsPorImgSeqNumExceptAdd(
										new Request(CommonVars
												.getCurrUser()), head) + 1;
						int size = list1.size();
						for (int i = 0; i < size; i++) {
							vet.add(list1.get(i));
						}
					dzscAction.saveEmsPorImg(new Request(CommonVars
							.getCurrUser()), vet);
					
					}
					List list = dzscAction.findEmsPorBomBill(new Request(
							CommonVars.getCurrUser()), to);
					tableModelBomBill = DzscClientLogic.initTableBomBill(
							tbBomBill, list);
					to = (DzscEmsExgBill) dzscAction.load(DzscEmsExgBill.class,
							to.getId());
					if (to != null) {
						tableModelExgBill.updateRow(to);
					}
				}
			});
		}
		return jButton28;
	}

	/**
	 * This method initializes jTable3
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbExgBill() {
		if (tbExgBill == null) {
			tbExgBill = new JTable();
			tbExgBill.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if (tableModelExgBill == null)
								return;
							if (tableModelExgBill.getCurrentRow() == null)
								return;
							DzscEmsExgBill exg = (DzscEmsExgBill) tableModelExgBill
									.getCurrentRow();
							lbExgInfo.setText("当前成品序号为:" + exg.getSeqNum()
									+ "  名称为:" + exg.getName());
							List list = dzscAction.findEmsPorBomBill(
									new Request(CommonVars.getCurrUser()), exg);
							tableModelBomBill = DzscClientLogic
									.initTableBomBill(tbBomBill, list);
							// btnProcessPrice.setEnabled((DzscState.ORIGINAL.equals(head.getDeclareState())
							// ||
							// DzscState.CHANGE.equals(head.getDeclareState()))
							// &&
							// ModifyMarkState.ADDED.equals(exg.getModifyMark()));
							//							
						}
					});
		}
		return tbExgBill;
	}

	/**
	 * This method initializes jScrollPane3
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getTbExgBill());
		}
		return jScrollPane3;
	}

	/**
	 * This method initializes jTable4
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbBomBill() {
		if (tbBomBill == null) {
			tbBomBill = new JTable();
			InputMap inputMap = tbBomBill
					.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
			KeyStroke tab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0);
			KeyStroke entry = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

			KeyStroke sTab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 1);
			KeyStroke sEntry = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 1);

			Object tabOperObject = inputMap.get(tab);
			inputMap.remove(entry);
			inputMap.put(entry, tabOperObject);

			Object sTabOperObject = inputMap.get(sTab);
			inputMap.remove(sEntry);
			inputMap.put(sEntry, sTabOperObject);
		}
		return tbBomBill;
	}

	/**
	 * This method initializes jScrollPane4
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane4() {
		if (jScrollPane4 == null) {
			jScrollPane4 = new JScrollPane();
			jScrollPane4.setViewportView(getTbBomBill());
		}
		return jScrollPane4;
	}

	/**
	 * This method initializes btnUndo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUndo() {
		if (btnUndo == null) {
			btnUndo = new JButton();
			btnUndo.setText("放弃");
			btnUndo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.BROWSE;
					showHeadData();
					setState();
				}
			});
		}
		return btnUndo;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	private void sumMoney() {
		Double sumMoney = dzscAction.sumMoney(new Request(CommonVars
				.getCurrUser()), head);
		DecimalFormat myformat = new DecimalFormat("#.###");// 保存三位小数
		jLabel47.setText("      合计金额： " + myformat.format(sumMoney).toString());
	}

	/**
	 * 显示数据
	 * 
	 */
	private void showHeadData() {
		head = this.dzscAction.findDzscEmsPorHeadById(new Request(CommonVars
				.getCurrUser(), true), head.getId());
		if (head.getDeclareState().equals(DzscState.ORIGINAL)) {
			tfEmsState.setText("初始状态");
		} else if (head.getDeclareState().equals(DzscState.APPLY)) {
			tfEmsState.setText("正在申请");
		} else if (head.getDeclareState().equals(DzscState.CHANGE)) {
			tfEmsState.setText("正在变更");
		} else if (head.getDeclareState().equals(DzscState.EXECUTE)) {
			tfEmsState.setText("正在执行");
		}
		cbbDeclareCustoms.setSelectedItem(head.getCustomNo());
		cbbRedDep.setSelectedItem(head.getRedDep());
		if (head.getEmsType() != null) {
			cbbEmsType.setSelectedIndex(ItemProperty.getIndexByCode(head
					.getEmsType(), cbbEmsType));
		} else {
			cbbEmsType.setSelectedIndex(-1);
		}
		tfPreEmsNo.setText(head.getCopTrNo());
		tfEmsNo.setText(head.getEmsNo());
		cbbBeginDate.setDate(head.getBeginDate());
		cbbEndDate.setDate(head.getEndDate());
		jComboBox5.setSelectedItem(head.getTrade());
		jComboBox7.setSelectedItem(head.getTradeCountry());
		tfTradeCode.setText(head.getTradeCode());
		tfTradeName.setText(head.getTradeName());
		jTextField3.setText(head.getMachName());
		jTextField4.setText(head.getMachCode());
		jTextField5.setText(head.getEnterpriseAddress());
		// cbbDeferDate.setDate(head.getDeferDate());
		cbbDestroyDate.setDate(head.getDestroyDate());
		jComboBox8.setSelectedItem(head.getLevyKind());
		jTextField7.setText(head.getContactTel());
		jTextField6.setText(head.getLinkMan());
		jTextField8.setText(head.getOutTradeCo());
		jTextField9.setText(head.getSancEmsNo());
		tfExpContractNo.setText(head.getImContractNo());
		tfImpContractNo.setText(head.getIeContractNo());
		jTextField10.setText(head.getAgreementNo());
		tfImgAmount.setValue(head.getImgAmount());
		tfExgAmount.setValue(head.getExgAmount());
		jComboBox9.setSelectedItem(head.getCurr());
		jComboBox11.setSelectedItem(head.getTransac());
		tfWardshipFee.setValue(head.getWardshipFee());
		cbbWardshipRate.setSelectedItem(head.getWardshipRate());
		jTextField16.setText(head.getApprover());
		jCalendarComboBox4.setDate(head.getApproveDate());
		calendarSaveDate.setDate(head.getSaveDate());
		jComboBox16.setSelectedItem(head.getPayMode());
		jComboBox17.setSelectedItem(head.getMachiningType());
		jTextField17.setText(head.getNote());
		jTextField13.setText(head.getEmsApprNo());
		cbbReceiveArea.setSelectedItem(head.getReceiveArea());
		this.tfCorrEmsNo.setText(head.getCorrEmsNo());
		this.tfCopEntNo.setText(head.getCopEntNo());
		this.cbbManageObject.setSelectedIndex(ItemProperty.getIndexByCode(
				String.valueOf(head.getManageObject()), cbbManageObject));
		this.cbbLimitFlag.setSelectedIndex(ItemProperty.getIndexByCode(head
				.getLimitFlag(), cbbLimitFlag));
		this.cbbEquipMode.setSelectedIndex(ItemProperty.getIndexByCode(head
				.getEquipMode(), cbbEquipMode));

		this.cbbBank.setSelectedIndex(ItemProperty.getIndexByCode(head
				.getBankModel(), cbbBank));
	}

	/**
	 * 初始化UI
	 * 
	 */
	private void initUIComponents() {
		List list = null;
		// 收货地区
		list = CustomBaseList.getInstance().getDistrict();
		DzscClientLogic.intoComboBox(list, cbbReceiveArea);
		// 主管外经贸部门
		list = CustomBaseList.getInstance().getRedDeps();
		DzscClientLogic.intoComboBox(list, cbbRedDep);
		// 申报关区号
		list = CustomBaseList.getInstance().getCustoms();
		DzscClientLogic.intoComboBox(list, cbbDeclareCustoms);
		// 贸易方式
		list = CustomBaseList.getInstance().getTrades();
		DzscClientLogic.intoComboBox(list, jComboBox5);
		// 贸易国别
		list = CustomBaseList.getInstance().getCountrys();
		DzscClientLogic.intoComboBox(list, jComboBox7);
		// 征面性质
		list = CustomBaseList.getInstance().getLevyKind();
		DzscClientLogic.intoComboBox(list, jComboBox8);
		// 保税方式
		list = CustomBaseList.getInstance().getPayModes();
		DzscClientLogic.intoComboBox(list, jComboBox16);
		// 加工种类
		list = CustomBaseList.getInstance().getMachiningTypes();
		DzscClientLogic.intoComboBox(list, jComboBox17);
		// 币制
		list = CustomBaseList.getInstance().getCurrs();
		DzscClientLogic.intoComboBox(list, jComboBox9);
		// 成交方式
		list = CustomBaseList.getInstance().getTransacs();
		DzscClientLogic.intoComboBox(list, jComboBox11);

		// 合同性质
		cbbEmsType.removeAllItems();
		cbbEmsType.addItem(new ItemProperty(DzscEmsType.COME_PROCESS_EMS_BILL,
				"加工贸易来料手册"));
		cbbEmsType.addItem(new ItemProperty(
				DzscEmsType.IMPORT_PROCESS_EMS_BILL, "加工贸易进料手册"));
		cbbEmsType.addItem(new ItemProperty(
				DzscEmsType.MACHINE_PROCESS_EMS_BILL, "加工贸易设备手册"));
		cbbEmsType.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbEmsType);
		// 监管费用率
		cbbWardshipRate.removeAllItems();
		cbbWardshipRate.addItem("0.001");
		cbbWardshipRate.addItem("0.0015");
		cbbWardshipRate.addItem("0.003");
		cbbWardshipRate.setUI(new CustomBaseComboBoxUI(127));
		// 打印
		// cbbPrint.removeAllItems();
		// cbbPrint.addItem("出口成品备案清单");
		// cbbPrint.addItem("进口料件备案清单");
		// cbbPrint.addItem("出口成品单耗备案清单");
		// cbbPrint.addItem("加工合同预申报合同组成表");
		// cbbPrint.addItem("加工合同变更预申报合同组成表");
		// cbbPrint.addItem("出口成品备案清单变更表");
		// cbbPrint.addItem("进口料件备案清单变更表");
		// cbbPrint.addItem("出口成品单耗备案清单变更表");
		// cbbPrint.addItem("出口成品加工费表");
		// cbbPrint.addItem("料件进出平衡检查表");
		// cbbPrint.setUI(new CustomBaseComboBoxUI(250));
		// cbbPrint.setMaximumRowCount(15);

		// 初始化管理对象
		this.cbbManageObject.removeAllItems();
		this.cbbManageObject.addItem(new ItemProperty(ManageObject.MANAGE_COP,
				"经营单位"));
		this.cbbManageObject.addItem(new ItemProperty(
				ManageObject.MANUFACTURE_COP, "加工单位"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbManageObject);
		this.cbbManageObject.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		cbbManageObject.setSelectedIndex(-1);

		// 限制类标志
		cbbLimitFlag.removeAllItems();
		cbbLimitFlag.addItem(new ItemProperty(LimitFlag.ADJUST_BEFORE_EMS,
				"调整前旧手册"));
		cbbLimitFlag.addItem(new ItemProperty(LimitFlag.ADJUST_AFTER_EMS,
				"调整后新手册"));
		cbbLimitFlag.addItem(new ItemProperty(LimitFlag.ACOUNT_BOOK_USE,
				"台账专用手册"));
		cbbLimitFlag.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbLimitFlag);

		// 单耗申报环节
		cbbEquipMode.removeAllItems();
		cbbEquipMode.addItem(new ItemProperty(EquipMode.PUT_ON_RECORD,
				EquipMode.getEquipModeDesc(EquipMode.PUT_ON_RECORD)));
		cbbEquipMode.addItem(new ItemProperty(EquipMode.BEFORE_EXPORT,
				EquipMode.getEquipModeDesc(EquipMode.BEFORE_EXPORT)));
		cbbEquipMode.addItem(new ItemProperty(EquipMode.BEFORE_CANCEL,
				EquipMode.getEquipModeDesc(EquipMode.BEFORE_CANCEL)));
		cbbEquipMode.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbEquipMode);

		cbbBank.removeAllItems();
		cbbBank.addItem(new ItemProperty(TempBankMode.PAPER, TempBankMode
				.getBankModeDesc(TempBankMode.PAPER)));
		cbbBank.addItem(new ItemProperty(TempBankMode.ICBC, TempBankMode
				.getBankModeDesc(TempBankMode.ICBC)));
		cbbBank.addItem(new ItemProperty(TempBankMode.BANKOFCHINA, TempBankMode
				.getBankModeDesc(TempBankMode.BANKOFCHINA)));

		cbbBank.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbBank);
	}

	/**
	 * 填充数据
	 * 
	 * @param head
	 */
	private void fillData(DzscEmsPorHead head) {
		head.setCustomNo((Customs) cbbDeclareCustoms.getSelectedItem());
		head.setRedDep((RedDep) cbbRedDep.getSelectedItem());
		if (cbbEmsType.getSelectedItem() != null) {
			head.setEmsType(((ItemProperty) cbbEmsType.getSelectedItem())
					.getCode());
		} else {
			head.setEmsType(null);
		}
		head.setReceiveArea((District) cbbReceiveArea.getSelectedItem());
		head.setCopTrNo(tfPreEmsNo.getText());
		head.setEmsNo(tfEmsNo.getText());
		head.setBeginDate(cbbBeginDate.getDate());
		head.setEndDate(cbbEndDate.getDate());
		head.setTrade((Trade) jComboBox5.getSelectedItem());
		head.setTradeCountry((Country) jComboBox7.getSelectedItem());
		// head.setDeferDate(cbbDeferDate.getDate());
		head.setDestroyDate(cbbDestroyDate.getDate());
		head.setEnterpriseAddress(jTextField5.getText());
		head.setLinkMan(jTextField6.getText());
		head.setContactTel(jTextField7.getText());
		head.setLevyKind((LevyKind) jComboBox8.getSelectedItem());
		head.setSancEmsNo(jTextField9.getText());
		head.setOutTradeCo(jTextField8.getText());
		head.setAgreementNo(jTextField10.getText());
		head.setIeContractNo(tfImpContractNo.getText());
		head.setImContractNo(tfExpContractNo.getText());
		head.setCurr((Curr) jComboBox9.getSelectedItem());
		head.setExgAmount(objToDouble(tfExgAmount.getValue()));
		head.setImgAmount(objToDouble(tfImgAmount.getValue()));
		if (cbbWardshipRate.getSelectedItem() != null
				&& !cbbWardshipRate.getSelectedItem().equals("")) {
			head.setWardshipRate(Double.valueOf(cbbWardshipRate
					.getSelectedItem().toString()));
		}
		head.setWardshipFee(objToDouble(tfWardshipFee.getValue()));
		head.setTransac((Transac) jComboBox11.getSelectedItem());
		head.setApprover(jTextField16.getText());
		head.setApproveDate(jCalendarComboBox4.getDate());
		head.setSaveDate(calendarSaveDate.getDate());
		head.setEmsApprNo(jTextField13.getText());
		head.setMachiningType((MachiningType) jComboBox17.getSelectedItem());
		head.setPayMode((PayMode) jComboBox16.getSelectedItem());
		head.setCopEntNo(this.tfCopEntNo.getText().trim());
		head.setNote(jTextField17.getText());
		if (this.cbbManageObject.getSelectedItem() != null) {
			ItemProperty item = (ItemProperty) cbbManageObject
					.getSelectedItem();
			head.setManageObject(item.getCode());
		}
		if (this.cbbLimitFlag.getSelectedItem() != null) {
			head.setLimitFlag(((ItemProperty) cbbLimitFlag.getSelectedItem())
					.getCode());
		} else {
			head.setLimitFlag(null);
		}
		if (this.cbbEquipMode.getSelectedItem() != null) {
			head.setEquipMode(((ItemProperty) cbbEquipMode.getSelectedItem())
					.getCode());
		} else {
			head.setEquipMode(null);
		}
		if (this.cbbBank.getSelectedItem() != null) {
			head.setBankModel(((ItemProperty) cbbBank.getSelectedItem())
					.getCode());
		} else {
			head.setBankModel(null);
		}
		if (!ModifyMarkState.ADDED.equals(head.getModifyMark())) {
			head.setModifyMark(ModifyMarkState.MODIFIED);
		}
	}

	/**
	 * 设置Components状态
	 * 
	 */
	private void setState() {

		//
		// 合同面板
		//
		boolean isChange = (head.getDeclareState().equals(DzscState.CHANGE));
		boolean isExecute = (head.getDeclareState().equals(DzscState.EXECUTE));
		btnSave.setEnabled((jTabbedPane.getSelectedIndex() == 0)
				&& dataState != DataState.BROWSE && !isExecute); // 保存
		btnEdit.setEnabled((jTabbedPane.getSelectedIndex() == 0)
				&& dataState == DataState.BROWSE && !isExecute); // 修改
		btnUndo.setEnabled((jTabbedPane.getSelectedIndex() == 0)
				&& dataState != DataState.BROWSE && !isExecute); // 撤消

		this.btnCal.setVisible(DzscState.ORIGINAL.equals(this.head
				.getDeclareState())
				|| DzscState.CHANGE.equals(this.head.getDeclareState()));

		jButton.setEnabled(!isExecute);
		cbbReceiveArea.setEnabled(dataState != DataState.BROWSE);
		cbbDeclareCustoms.setEnabled(dataState != DataState.BROWSE);
		cbbRedDep.setEnabled(dataState != DataState.BROWSE);
		// tfPreEmsNo.setEditable(dataState != DataState.BROWSE);
		// tfEmsNo.setEditable(dataState != DataState.BROWSE && !isChange);
		cbbEmsType.setEnabled(dataState != DataState.BROWSE);
		cbbBeginDate.setEnabled(dataState != DataState.BROWSE);
		cbbEndDate.setEnabled(dataState != DataState.BROWSE);
		jComboBox5.setEnabled(dataState != DataState.BROWSE);
		jComboBox7.setEnabled(dataState != DataState.BROWSE);
		jTextField5.setEditable(dataState != DataState.BROWSE);
		// cbbDeferDate.setEnabled(dataState != DataState.BROWSE);
		cbbDestroyDate.setEnabled(dataState != DataState.BROWSE);
		jComboBox8.setEnabled(dataState != DataState.BROWSE);
		jTextField7.setEditable(dataState != DataState.BROWSE);
		jTextField6.setEditable(dataState != DataState.BROWSE);
		jTextField8.setEditable(dataState != DataState.BROWSE);
		jTextField9.setEditable(dataState != DataState.BROWSE);
		tfExpContractNo.setEditable(dataState != DataState.BROWSE);
		tfImpContractNo.setEditable(dataState != DataState.BROWSE);
		jTextField10.setEditable(dataState != DataState.BROWSE);
		tfImgAmount.setEnabled(dataState != DataState.BROWSE);
		tfExgAmount.setEnabled(dataState != DataState.BROWSE);
		jComboBox9.setEnabled(dataState != DataState.BROWSE);
		jComboBox11.setEnabled(dataState != DataState.BROWSE);
		tfWardshipFee.setEnabled(dataState != DataState.BROWSE);
		cbbWardshipRate.setEnabled(dataState != DataState.BROWSE);
		jTextField16.setEditable(dataState != DataState.BROWSE);
		jCalendarComboBox4.setEnabled(dataState != DataState.BROWSE);
		calendarSaveDate.setEnabled(dataState != DataState.BROWSE);
		jComboBox16.setEnabled(dataState != DataState.BROWSE);
		jComboBox17.setEnabled(dataState != DataState.BROWSE);
		jTextField17.setEditable(dataState != DataState.BROWSE);
		jTextField13.setEditable(dataState != DataState.BROWSE);
		cbbLimitFlag.setEnabled(dataState != DataState.BROWSE);
		this.cbbEquipMode.setEnabled(dataState != DataState.BROWSE);
		btnCopyBill.setEnabled(dataState != DataState.BROWSE && isEditBill);
		this.cbbBank.setEnabled(dataState != DataState.BROWSE);
		this.tfPreEmsNo.setEditable(dataState != DataState.BROWSE
				&& head.getDeclareState().equals(DzscState.ORIGINAL));
		this.tfCopEntNo.setEditable(dataState != DataState.BROWSE
				&& head.getDeclareState().equals(DzscState.ORIGINAL));
		this.cbbManageObject.setEnabled(dataState != DataState.BROWSE);
		//
		// 料件清单备案
		//
		btnAddImgBill.setEnabled(DzscState.ORIGINAL.equals(head
				.getDeclareState())
				|| DzscState.CHANGE.equals(head.getDeclareState()));
		jButton18.setEnabled(DzscState.ORIGINAL.equals(head.getDeclareState())
				|| DzscState.CHANGE.equals(head.getDeclareState()));
		jButton19.setEnabled(DzscState.ORIGINAL.equals(head.getDeclareState())
				|| DzscState.CHANGE.equals(head.getDeclareState()));
		//
		// 成品及单耗表清单备案
		//
		btnAddExgBill.setEnabled(DzscState.ORIGINAL.equals(head
				.getDeclareState())
				|| DzscState.CHANGE.equals(head.getDeclareState()));
		jButton22.setEnabled(DzscState.ORIGINAL.equals(head.getDeclareState())
				|| DzscState.CHANGE.equals(head.getDeclareState()));
		btnProcessPrice.setEnabled(DzscState.ORIGINAL.equals(head
				.getDeclareState())
				|| DzscState.CHANGE.equals(head.getDeclareState()));
		jButton23.setEnabled(DzscState.ORIGINAL.equals(head.getDeclareState())
				|| DzscState.CHANGE.equals(head.getDeclareState()));
		jButton24.setEnabled(DzscState.ORIGINAL.equals(head.getDeclareState())
				|| DzscState.CHANGE.equals(head.getDeclareState()));
		// && head.getCopEntNo() != null
		// && !"".equals(head.getCopEntNo().trim())
		jButton25.setEnabled(DzscState.ORIGINAL.equals(head.getDeclareState())
				|| DzscState.CHANGE.equals(head.getDeclareState()));
		jButton26.setEnabled(DzscState.ORIGINAL.equals(head.getDeclareState())
				|| DzscState.CHANGE.equals(head.getDeclareState()));
		jButton27.setEnabled(DzscState.ORIGINAL.equals(head.getDeclareState())
				|| DzscState.CHANGE.equals(head.getDeclareState()));
		jButton28.setEnabled(DzscState.ORIGINAL.equals(head.getDeclareState())
				|| DzscState.CHANGE.equals(head.getDeclareState()));

		// 归并料件
		this.btnAddMaterialImg.setEnabled((DzscState.ORIGINAL.equals(head
				.getDeclareState()) || DzscState.CHANGE.equals(head
				.getDeclareState()))
				&& !(head.getCopEntNo() == null || "".equals(head.getCopEntNo()
						.trim())));
		this.btnDeleteMaterialImg.setEnabled((DzscState.ORIGINAL.equals(head
				.getDeclareState()) || DzscState.CHANGE.equals(head
				.getDeclareState()))
				&& !(head.getCopEntNo() == null || "".equals(head.getCopEntNo()
						.trim())));

		// 归并成品
		this.btnAddMaterialExg.setEnabled((DzscState.ORIGINAL.equals(head
				.getDeclareState()) || DzscState.CHANGE.equals(head
				.getDeclareState()))
				&& !(head.getCopEntNo() == null || "".equals(head.getCopEntNo()
						.trim())));
		this.btnDeleteMaterialExg.setEnabled((DzscState.ORIGINAL.equals(head
				.getDeclareState()) || DzscState.CHANGE.equals(head
				.getDeclareState()))
				&& !(head.getCopEntNo() == null || "".equals(head.getCopEntNo()
						.trim())));
		this.btnSaveBom.setEnabled(DzscState.ORIGINAL.equals(head
				.getDeclareState())
				|| DzscState.CHANGE.equals(head.getDeclareState()));
		this.btnBomUndo.setEnabled(DzscState.ORIGINAL.equals(head
				.getDeclareState())
				|| DzscState.CHANGE.equals(head.getDeclareState()));

		this.getMiUndoDeclare().setEnabled(
				DzscState.APPLY.equals(head.getDeclareState()));
		this.btnChangeExgModifyMark.setEnabled(DzscState.ORIGINAL.equals(head
				.getDeclareState())
				|| DzscState.CHANGE.equals(head.getDeclareState()));
		this.btnChangeImgModifyMark.setEnabled(DzscState.ORIGINAL.equals(head
				.getDeclareState())
				|| DzscState.CHANGE.equals(head.getDeclareState()));
		this.btnChangeBomModifyMark.setEnabled(DzscState.ORIGINAL.equals(head
				.getDeclareState())
				|| DzscState.CHANGE.equals(head.getDeclareState()));
	}

	/**
	 * 设置监管费
	 * 
	 */
	private void setWardshipFee() {
		String amountStr = "0";
		try {
			double wardship = cbbWardshipRate.getSelectedItem() != null ? Double
					.valueOf(cbbWardshipRate.getSelectedItem().toString())
					: 0.0;
			double imgAmount = Double.valueOf((tfImgAmount.getValue()
					.toString()));

			BigDecimal bd = new BigDecimal(wardship * imgAmount);
			amountStr = bd.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			tfWardshipFee.setValue(Double.valueOf(amountStr));
		} catch (Exception ex) {
		}

	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnMaterialImg() {
		if (pnMaterialImg == null) {
			pnMaterialImg = new JPanel();
			pnMaterialImg.setLayout(new BorderLayout());
			pnMaterialImg.add(getJToolBar2(), java.awt.BorderLayout.NORTH);
			pnMaterialImg.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return pnMaterialImg;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnMaterialExg() {
		if (pnMaterialExg == null) {
			pnMaterialExg = new JPanel();
			pnMaterialExg.setLayout(new BorderLayout());
			pnMaterialExg.add(getJJToolBarBar(), java.awt.BorderLayout.NORTH);
			pnMaterialExg.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return pnMaterialExg;
	}

	/**
	 * This method initializes jToolBar2
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar2() {
		if (jToolBar2 == null) {
			jToolBar2 = new JToolBar();
			jToolBar2.add(getBtnAddMaterialImg());
			jToolBar2.add(getBtnDeleteMaterialImg());
			jToolBar2.add(getJCheckBox());
		}
		return jToolBar2;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddMaterialImg() {
		if (btnAddMaterialImg == null) {
			btnAddMaterialImg = new JButton();
			btnAddMaterialImg.setText("新增");
			btnAddMaterialImg
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							List list = DzscClientHelper.getInstance()
									.findInnerMergeNotInDzscEmsPor(
											MaterielType.MATERIEL, head);
							if (list != null && list.size() > 0) {
								dzscAction.addDzscEmsPorMaterialImg(
										new Request(CommonVars.getCurrUser()),
										head, list);
								showData();
							}
						}
					});
		}
		return btnAddMaterialImg;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbMaterialImg());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbMaterialImg() {
		if (tbMaterialImg == null) {
			tbMaterialImg = new JTable();

		}
		return tbMaterialImg;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.add(getBtnAddMaterialExg());
			jJToolBarBar.add(getBtnDeleteMaterialExg());
			jJToolBarBar.add(getJCheckBox1());

		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddMaterialExg() {
		if (btnAddMaterialExg == null) {
			btnAddMaterialExg = new JButton();
			btnAddMaterialExg.setText("新增");
			btnAddMaterialExg
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							List list = DzscClientHelper
									.getInstance()
									.findInnerMergeNotInDzscEmsPor(
											MaterielType.FINISHED_PRODUCT, head);
							if (list != null && list.size() > 0) {
								dzscAction.addDzscEmsPorMaterialExg(
										new Request(CommonVars.getCurrUser()),
										head, list);
								showData();
							}
						}
					});
		}
		return btnAddMaterialExg;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbMaterialExg());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbMaterialExg() {
		if (tbMaterialExg == null) {
			tbMaterialExg = new JTable();
		}
		return tbMaterialExg;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeleteMaterialExg() {
		if (btnDeleteMaterialExg == null) {
			btnDeleteMaterialExg = new JButton();
			btnDeleteMaterialExg.setText("删除");
			btnDeleteMaterialExg
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							List list = tableModelMaterialExg.getCurrentRows();
							if (list.size() <= 0) {
								JOptionPane.showMessageDialog(
										DgDzscEmsPor.this, "请选择你要删除的数据", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									DgDzscEmsPor.this, "你确定要删除这些数据吗", "提示",
									JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
								return;
							}
							Map<Integer, List<DzscEmsPorMaterialExg>> map = dzscAction
									.deleteDzscEmsPorMaterialExg(new Request(
											CommonVars.getCurrUser()), list);
							List lsDelete = map.get(DeleteResultMark.DELETE);
							if (lsDelete != null && lsDelete.size() > 0) {
								tableModelMaterialExg.deleteRows(lsDelete);
							}
							List lsUpdate = map.get(DeleteResultMark.UPDATE);
							if (lsUpdate != null && lsUpdate.size() > 0) {
								tableModelMaterialExg.updateRows(lsUpdate);
							}
						}
					});
		}
		return btnDeleteMaterialExg;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeleteMaterialImg() {
		if (btnDeleteMaterialImg == null) {
			btnDeleteMaterialImg = new JButton();
			btnDeleteMaterialImg.setText("删除");
			btnDeleteMaterialImg
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							List list = tableModelMaterialImg.getCurrentRows();
							if (list.size() <= 0) {
								JOptionPane.showMessageDialog(
										DgDzscEmsPor.this, "请选择你要删除的数据", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									DgDzscEmsPor.this, "你确定要删除这些数据吗", "提示",
									JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
								return;
							}
							Map<Integer, List<DzscEmsPorMaterialImg>> map = dzscAction
									.deleteDzscEmsPorMaterialImg(new Request(
											CommonVars.getCurrUser()), list);
							List lsDelete = map.get(DeleteResultMark.DELETE);
							if (lsDelete != null && lsDelete.size() > 0) {
								tableModelMaterialImg.deleteRows(lsDelete);
							}
							List lsUpdate = map.get(DeleteResultMark.UPDATE);
							if (lsUpdate != null && lsUpdate.size() > 0) {
								tableModelMaterialImg.updateRows(lsUpdate);
							}
						}
					});
		}
		return btnDeleteMaterialImg;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbManageObject() {
		if (cbbManageObject == null) {
			cbbManageObject = new JComboBox();
			cbbManageObject.setBounds(new java.awt.Rectangle(543, 44, 127, 22));
		}
		return cbbManageObject;
	}

	private boolean saveHeadData() {
		head = this.dzscAction.findDzscEmsPorHeadById(new Request(CommonVars
				.getCurrUser(), true), head.getId());
		fillData(head);
		if (checkData()) {
			this.jTabbedPane.setSelectedIndex(0);
			return false;
		}
		if (!tfEmsNo.getText().trim().equals("")
				&& !DzscState.CHANGE.equals(head.getDeclareState())) {
			if (checkDzscEmsPorHeadDuple(head)) {
				JOptionPane.showMessageDialog(DgDzscEmsPor.this, "手册编号不能重复",
						"提示", JOptionPane.INFORMATION_MESSAGE);
				this.jTabbedPane.setSelectedIndex(0);
				tfEmsNo.requestFocus();
				return false;
			}
		}
		if (!tfPreEmsNo.getText().trim().equals("")
				&& !DzscState.CHANGE.equals(head.getDeclareState())) {
			if (checkDzscCopEmsNoDuple(head)) {
				JOptionPane.showMessageDialog(DgDzscEmsPor.this, "企业内部编号不能重复",
						"提示", JOptionPane.INFORMATION_MESSAGE);
				this.jTabbedPane.setSelectedIndex(0);
				tfPreEmsNo.requestFocus();
				return false;
			}
		}
		if (!tfCopEntNo.getText().trim().equals("")
				&& !DzscState.CHANGE.equals(head.getDeclareState())) {
			if (!this.dzscAction.checkDzscEmsPorCopEntNoIsExist(new Request(
					CommonVars.getCurrUser(), true), head)) {
				JOptionPane.showMessageDialog(DgDzscEmsPor.this, "物料备案内部编号"
						+ tfCopEntNo.getText().trim() + "不存在", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				this.jTabbedPane.setSelectedIndex(0);
				tfCopEntNo.requestFocus();
				return false;
			}
		}
		// System.out.println(head.getExgAmount());
		// System.out.println(head.getImgAmount());
		head = dzscAction.saveDzscEmsPorHead(new Request(CommonVars
				.getCurrUser()), head);
		tableModelHead.updateRow(head);
		return true;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbReceiveArea() {
		if (cbbReceiveArea == null) {
			cbbReceiveArea = new JComboBox();
			cbbReceiveArea.setBounds(new Rectangle(113, 351, 127, 25));
		}
		return cbbReceiveArea;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox("合并显示");
			jCheckBox.setSize(new java.awt.Dimension(76, 22));
			jCheckBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					refreshTable();
				}
			});
		}
		return jCheckBox;
	}

	private void refreshTable() {
		if (this.jTabbedPane.getSelectedIndex() == 3) {
			if (jCheckBox.isSelected()) {
				((MultiSpanCellTable) tbMaterialImg).combineRows(6, new int[] {
						6, 7, 8, 9, 10, 11 });
			} else {
				((MultiSpanCellTable) tbMaterialImg).splitRows(6);
			}
		} else if (this.jTabbedPane.getSelectedIndex() == 4) {
			if (jCheckBox1.isSelected()) {
				((MultiSpanCellTable) tbMaterialExg).combineRows(6, new int[] {
						6, 7, 8, 9, 10, 11 });

			} else {
				((MultiSpanCellTable) tbMaterialExg).splitRows(6);
			}
		}
	}

	/**
	 * This method initializes jCheckBox1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox1() {
		if (jCheckBox1 == null) {
			jCheckBox1 = new JCheckBox("\u5408\u5e76\u663e\u793a");
			jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					refreshTable();
				}
			});
		}
		return jCheckBox1;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSaveDetail() {
		if (btnSaveDetail == null) {
			btnSaveDetail = new JButton();
			btnSaveDetail.setText("料件成品单耗");
			btnSaveDetail
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgDzscEditBomUnitWaste dg = new DgDzscEditBomUnitWaste();
							dg.setHead(head);
							dg.setVisible(true);
							showData();
						}
					});
		}
		return btnSaveDetail;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSaveBom() {
		if (btnSaveBom == null) {
			btnSaveBom = new JButton();
			btnSaveBom.setText("保存");
			btnSaveBom.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelBomBill != null) {
						if (tbBomBill.getCellEditor() != null) {
							tbBomBill.getCellEditor().stopCellEditing();
						}
						List list = tableModelBomBill.getList();
						for (int i = 0; i < list.size(); i++) {
							DzscEmsBomBill bom = (DzscEmsBomBill) list.get(i);
							bom = dzscAction.saveDzscEmsBomBill(new Request(
									CommonVars.getCurrUser()), bom);
							tableModelBomBill.updateRow(bom);
						}
						if (list.size() > 0) {
							DzscEmsExgBill exgBill = ((DzscEmsBomBill) list
									.get(0)).getDzscEmsExgBill();
							exgBill = (DzscEmsExgBill) dzscAction.load(
									DzscEmsExgBill.class, exgBill.getId());
							if (exgBill != null) {
								tableModelExgBill.updateRow(exgBill);
							}
						}
					}
				}
			});
		}
		return btnSaveBom;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnBomUndo() {
		if (btnBomUndo == null) {
			btnBomUndo = new JButton();
			btnBomUndo.setText("取消");
			btnBomUndo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelExgBill == null)
						return;
					if (tableModelExgBill.getCurrentRow() == null)
						return;
					DzscEmsExgBill exg = (DzscEmsExgBill) tableModelExgBill
							.getCurrentRow();
					lbExgInfo.setText("当前成品序号为:" + exg.getSeqNum() + "  名称为:"
							+ exg.getName());
					List list = dzscAction.findEmsPorBomBill(new Request(
							CommonVars.getCurrUser()), exg);
					tableModelBomBill = DzscClientLogic.initTableBomBill(
							tbBomBill, list);
				}
			});
		}
		return btnBomUndo;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUnitWareDiff() {
		if (btnUnitWareDiff == null) {
			btnUnitWareDiff = new JButton();
			btnUnitWareDiff.setText("计算单耗差异");
			btnUnitWareDiff
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DzscEmsExgBill exg = (DzscEmsExgBill) tableModelExgBill
									.getCurrentRow();
							if (exg == null) {
								JOptionPane.showMessageDialog(
										DgDzscEmsPor.this, "请先选中成品!", "提示", 2);
								return;
							}
							DgDzscCustomsFactoryUnitWasteDiff dg = new DgDzscCustomsFactoryUnitWasteDiff();
							dg.setExgBill(exg);
							dg.setVisible(true);
						}
					});
		}
		return btnUnitWareDiff;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCorrEmsNo() {
		if (tfCorrEmsNo == null) {
			tfCorrEmsNo = new JTextField();
			tfCorrEmsNo.setBounds(new Rectangle(113, 378, 128, 22));
			tfCorrEmsNo.setEditable(false);
		}
		return tfCorrEmsNo;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCopEntNo() {
		if (tfCopEntNo == null) {
			tfCopEntNo = new JTextField();
			tfCopEntNo.setBounds(new Rectangle(340, 378, 123, 21));
			tfCopEntNo.setEditable(false);
		}
		return tfCopEntNo;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTradeCode() {
		if (tfTradeCode == null) {
			tfTradeCode = new JTextField();
			tfTradeCode.setBounds(new Rectangle(112, 73, 128, 22));
			tfTradeCode.setEditable(false);
		}
		return tfTradeCode;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTradeName() {
		if (tfTradeName == null) {
			tfTradeName = new JTextField();
			tfTradeName.setBounds(new Rectangle(327, 73, 345, 22));
			tfTradeName.setEditable(false);
		}
		return tfTradeName;
	}

	/**
	 * This method initializes cbbDeclareCustoms1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbRedDep() {
		if (cbbRedDep == null) {
			cbbRedDep = new JComboBox();
			cbbRedDep.setBounds(new Rectangle(325, 44, 127, 22));
		}
		return cbbRedDep;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("导入");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgDzscInput dg = new DgDzscInput();
					dg.setHead(head);
					dg.setVisible(true);
					if (dg.isOk()) {
						showData();
					}
				}
			});
		}
		return jButton;
	}

	private JButton getBtnCheck() {
		if (btnCheck == null) {
			btnCheck = new JButton();
			btnCheck.setText("检查");
			btnCheck.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List errlist = new ArrayList();
					errlist = checkFrameData();
					if (errlist.size() == 0) {
						JOptionPane
								.showMessageDialog(DgDzscEmsPor.this, "检查没有错误",
										"提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					DgShowCustomerError dgShowCustomerError = new DgShowCustomerError(
							errlist, true);
				}
			});
		}
		return btnCheck;
	}

	/**
	 * This method initializes jPopupMenu
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmImportUnitWaste() {
		if (pmImportUnitWaste == null) {
			pmImportUnitWaste = new JPopupMenu();
			pmImportUnitWaste.add(getJMenuItem());
			pmImportUnitWaste.add(getJMenuItem1());
		}
		return pmImportUnitWaste;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem() {
		if (jMenuItem == null) {
			jMenuItem = new JMenuItem();
			jMenuItem.setText("从备案BOM导入");
			jMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DzscEmsExgBill exg = (DzscEmsExgBill) tableModelExgBill
							.getCurrentRow();
					if (exg == null) {
						JOptionPane.showMessageDialog(DgDzscEmsPor.this,
								"请先选中成品!", "提示", 2);
						return;
					}
					if (JOptionPane.showConfirmDialog(DgDzscEmsPor.this,
							"确定要导入单耗吗?", "提示", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
						return;
					}
					DgDzscAutoImpEmsBomFromFactoryBom dg = new DgDzscAutoImpEmsBomFromFactoryBom();
					dg.setExgBill(exg);
					dg.setVisible(true);
					// dzscAction.importDzscEmsPorBomBillFromBom(new Request(
					// CommonVars.getCurrUser()), exg);
					List list = dzscAction.findEmsPorBomBill(new Request(
							CommonVars.getCurrUser()), exg);
					tableModelBomBill = DzscClientLogic.initTableBomBill(
							tbBomBill, list);
				}
			});
		}
		return jMenuItem;
	}

	/**
	 * This method initializes jMenuItem1
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem1() {
		if (jMenuItem1 == null) {
			jMenuItem1 = new JMenuItem();
			jMenuItem1.setText("从报关单耗导入");
			jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DzscEmsExgBill exg = (DzscEmsExgBill) tableModelExgBill
							.getCurrentRow();
					if (exg == null) {
						JOptionPane.showMessageDialog(DgDzscEmsPor.this,
								"请先选中成品!", "提示", 2);
						return;
					}
					if (JOptionPane.showConfirmDialog(DgDzscEmsPor.this,
							"确定要导入单耗吗?", "提示", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
						return;
					}
					DgDzscAutoImpEmsBomFromCustomsBom dg = new DgDzscAutoImpEmsBomFromCustomsBom();
					dg.setExgBill(exg);
					dg.setVisible(true);
					List list = dzscAction.findEmsPorBomBill(new Request(
							CommonVars.getCurrUser()), exg);
					tableModelBomBill = DzscClientLogic.initTableBomBill(
							tbBomBill, list);
				}
			});
		}
		return jMenuItem1;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbLimitFlag() {
		if (cbbLimitFlag == null) {
			cbbLimitFlag = new JComboBox();
			cbbLimitFlag.setBounds(new Rectangle(113, 403, 127, 22));
		}
		return cbbLimitFlag;
	}

	/**
	 * This method initializes btnBOMExport
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnBOMExport() {
		if (btnBOMExport == null) {
			btnBOMExport = new JButton();
			btnBOMExport.setText("BOM导出");
			btnBOMExport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgDzscBOMExport dg = new DgDzscBOMExport();
					dg.setHead(head);
					dg.setVisible(true);
				}
			});
		}
		return btnBOMExport;
	}

	/**
	 * This method initializes btnChangeDeclareState
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChangeDeclareState() {
		if (btnChangeDeclareState == null) {
			btnChangeDeclareState = new JButton();
			btnChangeDeclareState.setText("改变申报状态");
			btnChangeDeclareState
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgAllChangState dg = new DgAllChangState();
							dg.setVisible(true);
							if (!dg.isOk()) {
								return;
							}
							if (dg.isCheckBoxState()) {
								Component comp = (Component) e.getSource();
								getPmChangeDeclareState().show(comp, 0,
										comp.getHeight());
							}

						}
					});
		}
		return btnChangeDeclareState;
	}

	private JPopupMenu getPmChangeDeclareState() {
		if (pmChangeDeclareState == null) {
			pmChangeDeclareState = new JPopupMenu();
			pmChangeDeclareState.add(getMiUndoDeclare());
		}
		return pmChangeDeclareState;
	}

	/**
	 * This method initializes pmChangeDeclareState
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmChangeDeclareState2() {
		if (pmChangeDeclareState == null) {
			pmChangeDeclareState = new JPopupMenu();
			pmChangeDeclareState.add(getMiUndoDeclare());
		}
		return pmChangeDeclareState;
	}

	/**
	 * This method initializes miUndoDeclare
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiUndoDeclare() {
		if (miUndoDeclare == null) {
			miUndoDeclare = new JMenuItem();
			miUndoDeclare.setText("取消申报");
			miUndoDeclare
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							String declareState = "";
							if (head.getEmsNo() != null
									&& !"".equals(head.getEmsNo().trim())) {
								declareState = DzscState.CHANGE;
							} else {
								declareState = DzscState.ORIGINAL;
							}
							head = dzscAction.changeDzscEmsPorHeadDeclareState(
									new Request(CommonVars.getCurrUser()),
									head, declareState);
							setState();
							if (tableModelHead != null) {
								tableModelHead.updateRow(head);
							}
						}
					});
		}
		return miUndoDeclare;
	}

	/**
	 * This method initializes btnChangeImgModifyMark
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChangeImgModifyMark() {
		if (btnChangeImgModifyMark == null) {
			btnChangeImgModifyMark = new JButton();
			btnChangeImgModifyMark.setToolTipText(ModifyMarkState
					.getToolTipText());
			btnChangeImgModifyMark.setText("改变修改标志");
			btnChangeImgModifyMark
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgAllChangState dg = new DgAllChangState();
							dg.setVisible(true);
							if (!dg.isOk()) {
								return;
							}
							if (dg.isCheckBoxState()) {
								Component comp = (Component) e.getSource();
								addChangeModifyMarkListener(getMiNotModified(),
										new ChangeImgModifyMarkListener(
												ModifyMarkState.UNCHANGE));
								addChangeModifyMarkListener(getMiModified(),
										new ChangeImgModifyMarkListener(
												ModifyMarkState.MODIFIED));
								addChangeModifyMarkListener(getMiDelete(),
										new ChangeImgModifyMarkListener(
												ModifyMarkState.DELETED));
								addChangeModifyMarkListener(getMiAdd(),
										new ChangeImgModifyMarkListener(
												ModifyMarkState.ADDED));
								getPmChangeModifyMark().show(comp, 0,
										comp.getHeight());
							}

						}
					});
		}
		return btnChangeImgModifyMark;
	}

	class ChangeImgModifyMarkListener implements ActionListener {
		private String modifyMark = "";

		public ChangeImgModifyMarkListener(String modifyMark) {
			this.modifyMark = modifyMark;
		}

		public void actionPerformed(ActionEvent e) {
			if (tableModelImgBill.getCurrentRow() == null) {
				JOptionPane.showMessageDialog(DgDzscEmsPor.this, "请选择料件", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			List list = tableModelImgBill.getCurrentRows();
			dzscAction.changeContractImgModifyMark(new Request(CommonVars
					.getCurrUser()), list, modifyMark);
			tableModelImgBill = DzscClientLogic.initTableImgBill(tbImgBill,
					head); // 料件清单
		}

	}

	class ChangeExgModifyMarkListener implements ActionListener {

		private String modifyMark = "";

		public ChangeExgModifyMarkListener(String modifyMark) {
			this.modifyMark = modifyMark;
		}

		public void actionPerformed(ActionEvent e) {
			if (tableModelExgBill.getCurrentRow() == null) {
				JOptionPane.showMessageDialog(DgDzscEmsPor.this, "请选择成品", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			List list = tableModelExgBill.getCurrentRows();
			dzscAction.changeContractExgModifyMark(new Request(CommonVars
					.getCurrUser()), list, modifyMark);
			tableModelExgBill = DzscClientLogic.initTableExgBill(tbExgBill,
					head);
		}

	}

	class ChangeBomModifyMarkListener implements ActionListener {

		private String modifyMark = "";

		public ChangeBomModifyMarkListener(String modifyMark) {
			this.modifyMark = modifyMark;
		}

		public void actionPerformed(ActionEvent e) {
			if (tableModelBomBill.getCurrentRow() == null) {
				JOptionPane.showMessageDialog(DgDzscEmsPor.this, "请选择单耗", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			List list = tableModelBomBill.getCurrentRows();
			dzscAction.changeContractBomModifyMark(new Request(CommonVars
					.getCurrUser()), list, modifyMark);
			tableModelBomBill = DzscClientLogic.initTableBomBill(tbBomBill,
					(DzscEmsExgBill) tableModelExgBill.getCurrentRow());
		}

	}

	private void addChangeModifyMarkListener(JMenuItem menuItem,
			ActionListener actionListener) {
		ActionListener[] actionListeners = menuItem.getActionListeners();
		for (int i = 0; i < actionListeners.length; i++) {
			menuItem.removeActionListener(actionListeners[i]);
		}
		menuItem.addActionListener(actionListener);
	}

	/**
	 * This method initializes btnChangeExgModifyMark
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChangeExgModifyMark() {
		if (btnChangeExgModifyMark == null) {
			btnChangeExgModifyMark = new JButton();
			btnChangeExgModifyMark.setToolTipText(ModifyMarkState
					.getToolTipText());
			btnChangeExgModifyMark.setText("改变修改标志");
			btnChangeExgModifyMark
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgAllChangState dg = new DgAllChangState();
							dg.setVisible(true);
							if (!dg.isOk()) {
								return;
							}
							if (dg.isCheckBoxState()) {
								Component comp = (Component) e.getSource();
								addChangeModifyMarkListener(getMiNotModified(),
										new ChangeExgModifyMarkListener(
												ModifyMarkState.UNCHANGE));
								addChangeModifyMarkListener(getMiModified(),
										new ChangeExgModifyMarkListener(
												ModifyMarkState.MODIFIED));
								addChangeModifyMarkListener(getMiDelete(),
										new ChangeExgModifyMarkListener(
												ModifyMarkState.DELETED));
								addChangeModifyMarkListener(getMiAdd(),
										new ChangeExgModifyMarkListener(
												ModifyMarkState.ADDED));
								getPmChangeModifyMark().show(comp, 0,
										comp.getHeight());
							}

						}
					});
		}
		return btnChangeExgModifyMark;
	}

	/**
	 * This method initializes pmChangeModifyMark
	 * 
	 * @return javax.swing.JPopupMenu
	 */

	/**
	 * This method initializes miNotModified1
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiNotModified() {
		if (miNotModified1 == null) {
			miNotModified1 = new JMenuItem();
			miNotModified1.setText("\u672a\u4fee\u6539");
		}
		return miNotModified1;
	}

	/**
	 * This method initializes miModified1
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiModified() {
		if (miModified1 == null) {
			miModified1 = new JMenuItem();
			miModified1.setText("\u5df2\u4fee\u6539");
		}
		return miModified1;
	}

	/**
	 * This method initializes miDelete1
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiDelete() {
		if (miDelete1 == null) {
			miDelete1 = new JMenuItem();
			miDelete1.setText("\u5df2\u5220\u9664");
		}
		return miDelete1;
	}

	/**
	 * This method initializes miAdd1
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiAdd() {
		if (miAdd1 == null) {
			miAdd1 = new JMenuItem();
			miAdd1.setText("\u65b0  \u589e");
		}
		return miAdd1;
	}

	/**
	 * This method initializes pmChangeModifyMark
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmChangeModifyMark() {
		if (pmChangeModifyMark == null) {
			pmChangeModifyMark = new JPopupMenu();
			pmChangeModifyMark.add(getMiNotModified());
			pmChangeModifyMark.add(getMiModified());
			pmChangeModifyMark.add(getMiDelete());
			pmChangeModifyMark.add(getMiAdd());
		}
		return pmChangeModifyMark;
	}

	/**
	 * This method initializes miNotModified
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiNotModified2() {
		if (miNotModified == null) {
			miNotModified = new JMenuItem();
			miNotModified.setText("\u672a\u4fee\u6539");
		}
		return miNotModified;
	}

	/**
	 * This method initializes miModified
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiModified2() {
		if (miModified == null) {
			miModified = new JMenuItem();
			miModified.setText("\u5df2\u4fee\u6539");
		}
		return miModified;
	}

	/**
	 * This method initializes miDelete
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiDelete2() {
		if (miDelete == null) {
			miDelete = new JMenuItem();
			miDelete.setText("\u5df2\u5220\u9664");
		}
		return miDelete;
	}

	/**
	 * This method initializes miAdd
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiAdd2() {
		if (miAdd == null) {
			miAdd = new JMenuItem();
			miAdd.setText("\u65b0  \u589e");
		}
		return miAdd;
	}

	/**
	 * This method initializes btnChangeBomModifyMark
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChangeBomModifyMark() {
		if (btnChangeBomModifyMark == null) {
			btnChangeBomModifyMark = new JButton();
			btnChangeBomModifyMark.setToolTipText(ModifyMarkState
					.getToolTipText());
			btnChangeBomModifyMark.setText("改变修改标志");
			btnChangeBomModifyMark
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgAllChangState dg = new DgAllChangState();
							dg.setVisible(true);
							if (!dg.isOk()) {
								return;
							}
							if (dg.isCheckBoxState()) {
								Component comp = (Component) e.getSource();
								addChangeModifyMarkListener(getMiNotModified(),
										new ChangeBomModifyMarkListener(
												ModifyMarkState.UNCHANGE));
								addChangeModifyMarkListener(getMiModified(),
										new ChangeBomModifyMarkListener(
												ModifyMarkState.MODIFIED));
								addChangeModifyMarkListener(getMiDelete(),
										new ChangeBomModifyMarkListener(
												ModifyMarkState.DELETED));
								addChangeModifyMarkListener(getMiAdd(),
										new ChangeBomModifyMarkListener(
												ModifyMarkState.ADDED));
								getPmChangeModifyMark().show(comp, 0,
										comp.getHeight());
							}
						}
					});
		}
		return btnChangeBomModifyMark;
	}

	/**
	 * This method initializes jPopupMenuPrintReport
	 * 
	 * @return javax.swing.JPopupMenu
	 */

	private JPopupMenu getJPopupMenuPrintReport() {
		if (jPopupMenuPrintReport == null) {
			jPopupMenuPrintReport = new JPopupMenu();
			jPopupMenuPrintReport.add(getMiHeadPrint());
			jPopupMenuPrintReport.add(getMiExportExgRecordationInventory());
			jPopupMenuPrintReport.add(getMiImportImgRecordationInventory());
			jPopupMenuPrintReport
					.add(getMiExportExgUnitConsumptionRecordationInventory());
			jPopupMenuPrintReport
					.add(getMiProcessContractBeforeHandContractCompose());
			jPopupMenuPrintReport
					.add(getMiProcessContractChangeBeforeHandContractCompose());
			jPopupMenuPrintReport
					.add(getMiExportExgRecordationInventoryChange());
			jPopupMenuPrintReport
					.add(getMiImportImgRecordationInventoryChange());
			jPopupMenuPrintReport
					.add(getMiExportExgUnitConsumptionRecordationInventoryChange());
			jPopupMenuPrintReport.add(getMiExportExgProcessCost());
			jPopupMenuPrintReport.add(getMiImgInletOutletBalanceCheck());

		}
		return jPopupMenuPrintReport;
	}

	/**
	 * This method initializes miExportExgRecordationInventory
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiExportExgRecordationInventory() {
		if (miExportExgRecordationInventory == null) {
			miExportExgRecordationInventory = new JMenuItem();
			miExportExgRecordationInventory.setText("出口成品备案表");
			miExportExgRecordationInventory
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DzscClientHelper.getInstance()
									.printExportExgRecordationInventory(
											DgDzscEmsPor.this, head);
						}
					});
		}
		return miExportExgRecordationInventory;
	}

	/**
	 * This method initializes miImportImgRecordationInventory
	 * 
	 * @return javax.swing.JMenuItem
	 */

	private JMenuItem getMiImportImgRecordationInventory() {
		if (miImportImgRecordationInventory == null) {
			miImportImgRecordationInventory = new JMenuItem();
			miImportImgRecordationInventory.setText("进口料件备案表");
			miImportImgRecordationInventory
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DzscClientHelper.getInstance()
									.printImportImgRecordationInventory(
											DgDzscEmsPor.this, head);
						}
					});
		}
		return miImportImgRecordationInventory;
	}

	/**
	 * This method initializes miExportExgUnitConsumptionRecordationInventory
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiExportExgUnitConsumptionRecordationInventory() {
		if (miExportExgUnitConsumptionRecordationInventory == null) {
			miExportExgUnitConsumptionRecordationInventory = new JMenuItem();
			miExportExgUnitConsumptionRecordationInventory.setText("出口成品单耗备案表");
			miExportExgUnitConsumptionRecordationInventory
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DzscClientHelper
									.getInstance()
									.printExportExgUnitConsumptionRecordationInventory(
											DgDzscEmsPor.this, head);
						}
					});
		}
		return miExportExgUnitConsumptionRecordationInventory;
	}

	/**
	 * This method initializes miProcessContractBeforeHandContractCompose
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiProcessContractBeforeHandContractCompose() {
		if (miProcessContractBeforeHandContractCompose == null) {
			miProcessContractBeforeHandContractCompose = new JMenuItem();
			miProcessContractBeforeHandContractCompose.setText("加工合同预申报合同组成表");
			miProcessContractBeforeHandContractCompose
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DzscClientHelper
									.getInstance()
									.printProcessContractBeforeHandContractCompose(
											DgDzscEmsPor.this, head);
						}
					});
		}
		return miProcessContractBeforeHandContractCompose;
	}

	/**
	 * This method initializes miProcessContractChangeBeforeHandContractCompose
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiProcessContractChangeBeforeHandContractCompose() {
		if (miProcessContractChangeBeforeHandContractCompose == null) {
			miProcessContractChangeBeforeHandContractCompose = new JMenuItem();
			miProcessContractChangeBeforeHandContractCompose
					.setText("加工合同变更预申报合同组成表");
			miProcessContractChangeBeforeHandContractCompose
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DzscClientHelper
									.getInstance()
									.printProcessContractChangeBeforeHandContractCompose(
											DgDzscEmsPor.this, head);
						}
					});
		}
		return miProcessContractChangeBeforeHandContractCompose;
	}

	/**
	 * This method initializes miExportExgRecordationInventoryChange
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiExportExgRecordationInventoryChange() {
		if (miExportExgRecordationInventoryChange == null) {
			miExportExgRecordationInventoryChange = new JMenuItem();
			miExportExgRecordationInventoryChange.setText("出口成品备案变更表");
			miExportExgRecordationInventoryChange
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DzscClientHelper.getInstance()
									.printExportExgRecordationInventoryChange(
											DgDzscEmsPor.this, head);
						}
					});
		}
		return miExportExgRecordationInventoryChange;
	}

	/**
	 * This method initializes miImportImgRecordationInventoryChange
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiImportImgRecordationInventoryChange() {
		if (miImportImgRecordationInventoryChange == null) {
			miImportImgRecordationInventoryChange = new JMenuItem();
			miImportImgRecordationInventoryChange.setText("进口料件备案变更表");
			miImportImgRecordationInventoryChange
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DzscClientHelper.getInstance()
									.printImportImgRecordationInventoryChange(
											DgDzscEmsPor.this, head);
						}
					});
		}
		return miImportImgRecordationInventoryChange;
	}

	/*
	 * cbbPrint.addItem("出口成品备案清单"); cbbPrint.addItem("进口料件备案清单");
	 * cbbPrint.addItem("出口成品单耗备案清单"); cbbPrint.addItem("加工合同预申报合同组成表");
	 * cbbPrint.addItem("加工合同变更预申报合同组成表"); cbbPrint.addItem("出口成品备案清单变更表");
	 * cbbPrint.addItem("进口料件备案清单变更表"); cbbPrint.addItem("出口成品单耗备案清单变更表");
	 * cbbPrint.addItem("出口成品加工费表"); cbbPrint.addItem("料件进出平衡检查表");
	 */

	/**
	 * This method initializes
	 * miExportExgUnitConsumptionRecordationInventoryChange
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiExportExgUnitConsumptionRecordationInventoryChange() {
		if (miExportExgUnitConsumptionRecordationInventoryChange == null) {
			miExportExgUnitConsumptionRecordationInventoryChange = new JMenuItem();
			miExportExgUnitConsumptionRecordationInventoryChange
					.setText("出口成品单耗备案变更表");
			miExportExgUnitConsumptionRecordationInventoryChange
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DzscClientHelper
									.getInstance()
									.printExportExgUnitConsumptionRecordationInventoryChange(
											DgDzscEmsPor.this, head);
						}
					});
		}
		return miExportExgUnitConsumptionRecordationInventoryChange;
	}

	/**
	 * This method initializes miExportExgProcessCost
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiExportExgProcessCost() {
		if (miExportExgProcessCost == null) {
			miExportExgProcessCost = new JMenuItem();
			miExportExgProcessCost.setText("出口成品加工费表");
			miExportExgProcessCost
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DzscClientHelper.getInstance()
									.printExportExgProcessCost(
											DgDzscEmsPor.this, head);
						}
					});
		}
		return miExportExgProcessCost;
	}

	/**
	 * This method initializes miImgInletOutletBalanceCheck
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiImgInletOutletBalanceCheck() {
		if (miImgInletOutletBalanceCheck == null) {
			miImgInletOutletBalanceCheck = new JMenuItem();
			miImgInletOutletBalanceCheck.setText("料件进出平衡检查表");
			miImgInletOutletBalanceCheck
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DzscClientHelper.getInstance()
									.printImgInletOutletBalanceCheck(
											DgDzscEmsPor.this, head);
						}
					});
		}
		return miImgInletOutletBalanceCheck;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbEquipMode() {
		if (cbbEquipMode == null) {
			cbbEquipMode = new JComboBox();
			cbbEquipMode.setBounds(new Rectangle(328, 403, 127, 22));
		}
		return cbbEquipMode;
	}

	/**
	 * This method initializes miHeadPrint
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiHeadPrint() {
		if (miHeadPrint == null) {
			miHeadPrint = new JMenuItem();
			miHeadPrint.setText("电子手册通关备案");
			miHeadPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DzscClientHelper.getInstance().printHead(DgDzscEmsPor.this,
							head);
				}
			});
		}
		return miHeadPrint;
	}

	/**
	 * This method initializes btnMaterielSort
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnMaterielSort() {
		if (btnMaterielSort == null) {
			btnMaterielSort = new JButton();
			btnMaterielSort.setText("料件排序");
			btnMaterielSort
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							if (tableModelImgBill == null
									|| tableModelImgBill.getList().size() <= 0) {
								return;
							}
							List list = tableModelImgBill.getList();
							if (list.size() <= 0) {
								return;
							}
							// if
							// (JOptionPane.showConfirmDialog(DgContract.this,
							// "是否将料件排序!!!", "提示",
							// JOptionPane.YES_NO_OPTION) ==
							// JOptionPane.YES_OPTION) {
							// list = contractAction
							// .saveContractImgAmountInteger(
							// new Request(CommonVars
							// .getCurrUser()), list);
							Vector vet = new Vector();
							list = dzscAction.findEmsPorImgBillModifyMark(
									new Request(CommonVars.getCurrUser()),
									head, ModifyMarkState.ADDED);
							int beginSeqNum = dzscAction
									.findMaxEmsPorImgSeqNumExceptAdd(
											new Request(CommonVars
													.getCurrUser()), head) + 1;
							int size = list.size();
							for (int i = 0; i < size; i++) {
								vet.add(list.get(i));
							}
							DgDzscImgSort dg = new DgDzscImgSort();
							dg.setList(vet);
							dg.setDzscAction(dzscAction);
							dg.setBeginSeqNum(beginSeqNum);
							dg.setVisible(true);
							if (dg.isOk()) {
								// list = dg.getList();
								// tableModelImg.updateRows(list);
								if (head != null) {
									tableModelImgBill = DzscClientLogic
											.initTableImgBill(tbImgBill, head);
								}
							}

						}
					});
		}
		return btnMaterielSort;
	}

	/**
	 * This method initializes btnFinishProductSort
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFinishProductSort() {
		if (btnFinishProductSort == null) {
			btnFinishProductSort = new JButton();
			btnFinishProductSort.setText("成品排序");
			btnFinishProductSort
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							if (tableModelExgBill == null
									|| tableModelExgBill.getList().size() <= 0) {
								return;
							}
							List list = tableModelExgBill.getList();
							if (list.size() <= 0) {
								return;
							}
							// if
							// (JOptionPane.showConfirmDialog(DgContract.this,
							// "是否将成品排序!!!", "提示",
							// JOptionPane.YES_NO_OPTION) ==
							// JOptionPane.YES_OPTION) {
							// list = contractAction
							// .saveContractImgAmountInteger(
							// new Request(CommonVars
							// .getCurrUser()), list);
							Vector vet = new Vector();
							list = dzscAction.findEmsPorExgBillModifyMark(
									new Request(CommonVars.getCurrUser()),
									head, ModifyMarkState.ADDED);

							int beginSeqNum = dzscAction
									.findMaxEmsPorExgSeqNumExceptAdd(
											new Request(CommonVars
													.getCurrUser()), head) + 1;
							int size = list.size();
							for (int i = 0; i < size; i++) {
								vet.add(list.get(i));
							}
							DgDzscExgSort dg = new DgDzscExgSort();
							dg.setList(vet);
							dg.setDzscAction(dzscAction);
							dg.setBeginSeqNum(beginSeqNum);
							dg.setVisible(true);
							if (dg.isOk()) {
								list = dg.getList();
								tableModelExgBill.updateRows(list);
							}
							if (head != null) {
								tableModelExgBill = DzscClientLogic
										.initTableExgBill(tbExgBill, head); // 成品备案
							}
							// }

						}
					});
		}
		return btnFinishProductSort;
	}

	/**
	 * This method initializes btnProcessPrice
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnProcessPrice() {
		if (btnProcessPrice == null) {
			btnProcessPrice = new JButton();
			btnProcessPrice.setText("计算成品单价");
			btnProcessPrice.setToolTipText("公式：sum(料件单项用量*料件单价)*(1+成品加工费比例))");
			btnProcessPrice
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							List exgBills = tableModelExgBill.getCurrentRows();
							if (exgBills == null || exgBills.size() == 0) {
								JOptionPane.showMessageDialog(
										DgDzscEmsPor.this, "请选择要计算的成品！");
								return;
							}
							// 计算单价
							List list = dzscAction.processDzscEmsExgBillPrice(
									new Request(CommonVars.getCurrUser()),
									exgBills);
							exgBills = (List) list.get(0);
							String message = (String) list.get(1);
							if (message != null && !"".equals(message)) {
								DgMessage dgMessage = new DgMessage("成品单价计算",
										message);
							}
							tableModelExgBill.updateRows(exgBills);
						}
					});
		}
		return btnProcessPrice;
	}

	/**
	 * This method initializes btnCal
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCal() {// 统计初始，变更状态中为新增状态的料件，成品，BOM
		if (btnCal == null) {
			btnCal = new JButton();
			btnCal.setText("重新计算");
			btnCal.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(DgDzscEmsPor.this,
							"是否确定根据成品数量和单耗重新反算成品单价和料件数量!!!", "提示",
							JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					try {
						dzscAction.reCalContractUnitWaste(new Request(
								CommonVars.getCurrUser()), head);
						if (jTabbedPane.getSelectedIndex() == 1) {
							tableModelImgBill = null;
							tableModelImgBill = DzscClientLogic
									.initTableImgBill(tbImgBill, head); // 料件清单
						} else if (jTabbedPane.getSelectedIndex() == 2) {
							tableModelExgBill = null;
							tableModelExgBill = DzscClientLogic
									.initTableExgBill(tbExgBill, head); // 成品备案
						}
						JOptionPane.showMessageDialog(DgDzscEmsPor.this,
								"重新计算成功!!!", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(DgDzscEmsPor.this,
								"重新计算失败!!!\n" + ex.getMessage(), "提示",
								JOptionPane.INFORMATION_MESSAGE);
						System.out.println(ex.getMessage());
						return;
					}
				}
			});
		}
		return btnCal;
	}

	/**
	 * This method initializes jCalendarComboBox41
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCalendarSaveDate() {
		if (calendarSaveDate == null) {
			calendarSaveDate = new JCalendarComboBox();
			calendarSaveDate.setBounds(new Rectangle(113, 453, 126, 22));
		}
		return calendarSaveDate;
	}

	/**
	 * This method initializes btnIEPort
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnIEPort() {
		if (btnIEPort == null) {
			btnIEPort = new JButton();
			btnIEPort.setBounds(new Rectangle(488, 428, 184, 22));
			btnIEPort.setText("进出口岸");
			btnIEPort.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgDzscEmsPorBillIEPort dg = new DgDzscEmsPorBillIEPort();
					dg.setDzscEmsPorHead(head);
					dg.setDataState(dataState);
					dg.setVisible(true);
				}
			});
		}
		return btnIEPort;
	}

	/**
	 * This method initializes cbbBank
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBank() {
		if (cbbBank == null) {
			cbbBank = new JComboBox();
			cbbBank.setBounds(new Rectangle(328, 453, 129, 22));
		}
		return cbbBank;
	}

	/**
	 * This method initializes cbSelectAllBom
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbSelectAllBom() {
		if (cbSelectAllBom == null) {
			cbSelectAllBom = new JCheckBox();
			cbSelectAllBom.setText("转抄时选择所有合同");
		}
		return cbSelectAllBom;
	}
} // @jve:decl-index=0:visual-constraint="10,10"

