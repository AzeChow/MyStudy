/*
 * Created on 2005-4-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.checkcancel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcs.client.contractcav.DgContractCav;
import com.bestway.bcs.client.dictpor.DgAllChangState;
import com.bestway.bcs.contractcav.entity.ContractCav;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.Request;
import com.bestway.common.constant.DzscBusinessType;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.dzsc.checkcancel.action.DzscContractCavAction;
import com.bestway.dzsc.checkcancel.entity.DzscContractBomCav;
import com.bestway.dzsc.checkcancel.entity.DzscContractCav;
import com.bestway.dzsc.checkcancel.entity.DzscContractCavTotalValue;
import com.bestway.dzsc.checkcancel.entity.DzscContractExgCav;
import com.bestway.dzsc.checkcancel.entity.DzscContractImgCav;
import com.bestway.dzsc.checkcancel.entity.DzscContractUnitWasteCav;
import com.bestway.dzsc.client.checkcancel.report.DzscContractcavReportVars;
import com.bestway.dzsc.client.dzscmanage.DgDzscEmsPor;
import com.bestway.dzsc.client.dzscmanage.DzscClientHelper;
import com.bestway.dzsc.client.message.DzscCommon;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.message.action.DzscMessageAction;
import com.bestway.dzsc.message.entity.DzscParameterSet;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import java.awt.Insets;

/**
 * 电子手册合同核销
 * 
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class DgDzscContractCav extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel pnHead = null;

	private JPanel pnExg = null;

	private JPanel pnImg = null;

	private JPopupMenu pmChangeModifyMark = null;

	private JPanel pnBom = null;

	private JPanel pnOther = null;

	private JToolBar jToolBar = null;

	private JRadioButton rbSelf = null;

	private JRadioButton rbCustoms = null;

	private ButtonGroup bgSelectModel = null; // @jve:decl-index=0:visual-constraint="233,5"

	private JButton btnClose = null;

	private JComboBox cbbPrint = null;

	private JButton btnPrint = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JTextField tfEmsNo = null;

	private JTextField tfContractNo = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JTextField tfCompanyName = null;

	private JTextField tfTelephone = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JTextField tfEndDate = null;

	private JTextField tfEmsApprNo = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel7 = null;

	private JLabel jLabel8 = null;

	private JFormattedTextField tfImpAmount = null;

	private JFormattedTextField tfExpAmount = null;

	private JFormattedTextField tfProcessTotalPrice = null;

	private JLabel lbCurrency = null;

	private JLabel jLabel10 = null;

	private JLabel jLabel11 = null;

	private JTextField tfImpCDCount = null;

	private JTextField tfExpCDCount = null;

	private JLabel jLabel12 = null;

	private JLabel jLabel13 = null;

	private JLabel jLabel14 = null;

	private JLabel jLabel15 = null;

	private JTextField tfInternalSaleWarrant = null;

	private JTextField tfInternalSaleTaxBill = null;

	private JFormattedTextField tfInternalSale = null;

	private JFormattedTextField tfInternalSaleTax = null;

	private JLabel jLabel16 = null;

	private JLabel jLabel17 = null;

	private JFormattedTextField tfRemainMoney = null;

	private JTextField tfRemainEmsNo = null;

	private JLabel jLabel18 = null;

	private JLabel jLabel19 = null;

	private JLabel jLabel20 = null;

	private JLabel jLabel21 = null;

	private JLabel jLabel22 = null;

	private JTextField tfCurrency = null;

	private JFormattedTextField tfCautionMoney = null;

	private JComboBox cbbIsSusceptivity = null;

	private JComboBox cbbIsLimit = null;

	private JButton btnSave = null;

	private JButton btnUndo = null;

	private JButton btnCheck = null;

	private JFormattedTextField tfTotalPages = null;

	private JTable tbExg = null;

	private JScrollPane jScrollPane = null;

	private JTable tbImg = null;

	private JScrollPane jScrollPane1 = null;

	private JTable tblBom = null;

	private JScrollPane jScrollPane2 = null;

	private JButton btnInteralBuy = null;

	private JButton btnDelete = null;

	private JButton btnLeftoverMaterial = null;

	private JCheckBox cbImg = null;

	private JRadioButton rbTotal = null;

	private JRadioButton rbWaste = null;

	private JButton btnRoundAmount = null;

	private JCheckBox cbBom1 = null;

	private JCheckBox cbBom2 = null;

	private JCheckBox cbBom3 = null;

	private ButtonGroup bgAmount = null; // @jve:decl-index=0:visual-constraint="334,2"

	private JPanel jPanel6 = null;

	private JLabel jLabel23 = null;

	private JLabel jLabel24 = null;

	private JLabel jLabel25 = null;

	private JLabel jLabel26 = null;

	private JLabel jLabel27 = null;

	private JLabel jLabel28 = null;

	private JLabel jLabel29 = null;

	private JLabel jLabel9 = null;

	private JTextField tfImpTotalValue = null;

	private JTextField tfExpTotalValue = null;

	private JTextField tfExpManufactureMoney = null;

	private JTextField tfExpTotalItems = null;

	private JTextField tfImpTotalGrossWeight = null;

	private JTextField tfImpNetWeight = null;

	private JTextField tfExpTotalGrossWeight = null;

	private JTextField tfExpNetWeight = null;

	private JButton btnCal = null;

	private DzscContractCavAction contractCavAction = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:

	// private DzscContractCav contractCavSelf = null;

	private DzscContractCav dzscContractCavCustoms = null; // @jve:decl-index=0:

	private DzscContractCav dzscContractCavSelf = null; // @jve:decl-index=0:

	private DzscEmsPorHead contract = null; // @jve:decl-index=0:

	private int dataState = DataState.BROWSE;

	private JButton btnEdit = null;

	private JTableListModel tableModelCD = null;

	private JTableListModel tableModelExg = null;

	private JTableListModel tableModelImg = null;

	private JTableListModel tableModelBom = null;

	private JButton btnReCal = null;

	private JPanel pnCustomsDeclaration = null; // @jve:decl-index=0:visual-constraint="584,10"

	private JScrollPane jScrollPane3 = null;

	private JTable tbCD = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="737,120"

	private JButton btnExgEdit = null;

	private JButton btnImgEdit = null;

	private JButton btnUnitWasteEdit = null;

	private JButton btnDeclare = null;

	private JButton btnProccess = null;

	private JPopupMenu jPopupMenuPrintReport = null;

	private JMenuItem miNonCoverPrintApplied = null;

	private JMenuItem miCoverPrintApplied = null;

	private JMenuItem miNonCoverPrintAppliedImg = null;

	private JMenuItem miCoverPrintAppliedImg = null;

	private JMenuItem miNonCoverPrintAppliedExg = null;

	private JMenuItem miCoverPrintAppliedExg = null;

	private JMenuItem miNonCoverPrintAppliedUnitConsumption = null;

	private JMenuItem miCoverPrintAppliedUnitConsumption = null;

	private JButton btnChangeImgModifyMark = null;

	private JMenuItem miNotModified;

	private JMenuItem miModified;

	private JMenuItem miDelete;

	private JMenuItem miAdd;

	private DzscParameterSet dzscParameterSet = null;

	private DzscMessageAction dzscMessageAction = null;

	/** 打印非套打核销表(新) */
	private JMenuItem miNonCoverPrintAppliedNewSmall = null;
	
	public DzscContractCav getDzscContractCavSelf() {
		return dzscContractCavSelf;
	}

	public void setDzscContractCavSelf(DzscContractCav dzscContractCavSelf) {
		this.dzscContractCavSelf = dzscContractCavSelf;
	}

	/**
	 * @return Returns the contract.
	 */
	public DzscEmsPorHead getContract() {
		return contract;
	}

	private JMenuItem getMiNotModified() {
		if (miNotModified == null) {
			miNotModified = new JMenuItem();
			miNotModified.setText("\u672a\u4fee\u6539");
		}
		return miNotModified;
	}

	/**
	 * @param contract
	 *            The contract to set.
	 */
	public void setContract(DzscEmsPorHead contract) {
		this.contract = contract;
	}

	/**
	 * @return Returns the contractCavCustoms.
	 */
	public DzscContractCav getDzscContractCavCustoms() {
		return dzscContractCavCustoms;
	}

	// /**
	// * @return Returns the contractCavSelf.
	// */
	// public DzscContractCav getContractCavSelf() {
	// return contractCavSelf;
	// }

	/**
	 * @param contractCavCustoms
	 *            The contractCavCustoms to set.
	 */
	public void setDzscContractCavCustoms(DzscContractCav contractCavCustoms) {
		this.dzscContractCavCustoms = contractCavCustoms;
	}

	// /**
	// * @param contractCavSelf
	// * The contractCavSelf to set.
	// */
	// public void setContractCavSelf(DzscContractCav contractCavSelf) {
	// this.contractCavSelf = contractCavSelf;
	// }

	/**
	 * This is the default constructor
	 */
	public DgDzscContractCav() {
		super();
		initialize();
		this.getBgAmount();
		this.getBgSelectModel();
		this.getButtonGroup();
		tblBom = new MultiSpanCellTable();
		contractCavAction = (DzscContractCavAction) CommonVars
				.getApplicationContext().getBean("dzscContractCavAction");
		dzscMessageAction = (DzscMessageAction) CommonVars
				.getApplicationContext().getBean("dzscMessageAction");
		dzscParameterSet = dzscMessageAction.findDzscMessageDirSet(new Request(
				CommonVars.getCurrUser()));
		tblBom.getColumnModel().addColumnModelListener(
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
						int[] columns = ((MultiSpanCellTable) tblBom)
								.getSelectedColumns();
						int[] rows = ((MultiSpanCellTable) tblBom)
								.getSelectedRows();
						// int[] columns = jTable.getSelectedColumns();
						// int[] rows = jTable.getSelectedRows();
						if (columns.length < 1 || rows.length < 1) {
							return;
						}
						// for (int i = 0; i < rows.length; i++) {
						// System.out.println("row:" + rows[i]);
						// }
						if (columns[0] >= 0 && columns[0] < 4) {
							tblBom.setColumnSelectionInterval(1, 3);
							return;
						} else if (columns[0] >= 4 && columns[0] < 10) {
							tblBom.setColumnSelectionInterval(4, 9);
							return;
						}
						// else if (columns[0] >= 12 && columns[0] <= 16) {
						// tblBom.setColumnSelectionInterval(12, 16);
						// return;
						// }
					}
				});
		jScrollPane2.setViewportView(tblBom);
		// this.setResizable(false);
	}

	public void setVisible(boolean b) {
		if (b) {
			InitUIComponents();
			if (rbSelf.isSelected()) {
				showData(dzscContractCavSelf);
			} else {
				showData(dzscContractCavCustoms);
			}
			setState();
			// switchSelfCustoms(this.rbCustoms.isSelected());
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("核销申请表计算");
		this.setSize(723, 560);
		this.setContentPane(getJContentPane());
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
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */

	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("自用核销表表头", null, getPnHead(), null);
			jTabbedPane
					.addTab("自用核销报关单", null, getPnCustomsDeclaration(), null);
			jTabbedPane.addTab("自用核销成品表", null, getPnExg(), null);
			jTabbedPane.addTab("自用核销料件表", null, getPnImg(), null);
			jTabbedPane.addTab("自用核销单耗表", null, getPnBom(), null);
			jTabbedPane.addTab("其他", null, getPnOther(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							switch (((JTabbedPane) e.getSource())
									.getSelectedIndex()) {
							case 0:
								if (rbCustoms.isSelected()) {
									showData(dzscContractCavCustoms);
								} else {
									showData(dzscContractCavSelf);
								}
								break;
							case 1:
								showCustomsDeclaration(rbCustoms.isSelected());
								break;
							case 2:
								showExgData(rbCustoms.isSelected());
								break;
							case 3:
								showImgData(rbCustoms.isSelected());
								break;
							case 4:
								showBomData(rbCustoms.isSelected());
								break;
							case 5:
								break;
							}
							setState();
						}
					});
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnHead() {
		if (pnHead == null) {
			jLabel22 = new JLabel();
			jLabel21 = new JLabel();
			jLabel20 = new JLabel();
			jLabel19 = new JLabel();
			jLabel18 = new JLabel();
			jLabel17 = new JLabel();
			jLabel16 = new JLabel();
			jLabel15 = new JLabel();
			jLabel14 = new JLabel();
			jLabel13 = new JLabel();
			jLabel12 = new JLabel();
			jLabel11 = new JLabel();
			jLabel10 = new JLabel();
			lbCurrency = new JLabel();
			jLabel8 = new JLabel();
			jLabel7 = new JLabel();
			jLabel6 = new JLabel();
			jLabel5 = new JLabel();
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			pnHead = new JPanel();
			pnHead.setLayout(null);
			jLabel.setText("手册号");
			jLabel.setBounds(new java.awt.Rectangle(47, 46, 89, 24));
			jLabel1.setText("合同号");
			jLabel1.setBounds(new java.awt.Rectangle(344, 46, 87, 24));
			jLabel2.setText("公司名称");
			jLabel2.setBounds(new java.awt.Rectangle(47, 78, 89, 24));
			jLabel3.setText("电话");
			jLabel3.setBounds(new java.awt.Rectangle(344, 78, 87, 24));
			jLabel4.setText("合同有效期");
			jLabel4.setBounds(new java.awt.Rectangle(47, 110, 89, 24));
			jLabel5.setText("合同批准证号");
			jLabel5.setBounds(new java.awt.Rectangle(344, 110, 87, 24));
			jLabel6.setText("进口总金额");
			jLabel6.setBounds(new java.awt.Rectangle(47, 143, 89, 24));
			jLabel7.setText("出口总金额");
			jLabel7.setBounds(new java.awt.Rectangle(270, 143, 63, 24));
			jLabel8.setText("加工费");
			jLabel8.setBounds(new java.awt.Rectangle(434, 143, 40, 24));
			lbCurrency.setText("JLabel");
			lbCurrency.setBounds(new java.awt.Rectangle(580, 143, 47, 24));
			jLabel10.setText("进口报关单份数");
			jLabel10.setBounds(new java.awt.Rectangle(47, 175, 89, 24));
			jLabel11.setText("出口报关单份数");
			jLabel11.setBounds(new java.awt.Rectangle(344, 175, 87, 24));
			jLabel12.setText("内销金额");
			jLabel12.setBounds(new java.awt.Rectangle(47, 209, 89, 24));
			jLabel13.setText("内销补税金额");
			jLabel13.setBounds(new java.awt.Rectangle(344, 209, 87, 24));
			jLabel14.setText("内销批准证号");
			jLabel14.setBounds(new java.awt.Rectangle(47, 243, 89, 24));
			jLabel15.setText("内销补税单号");
			jLabel15.setBounds(new java.awt.Rectangle(344, 243, 87, 24));
			jLabel16.setText("余料金额");
			jLabel16.setBounds(new java.awt.Rectangle(47, 279, 89, 24));
			jLabel17.setText("转入余料手册编号");
			jLabel17.setBounds(new java.awt.Rectangle(334, 279, 97, 24));
			jLabel18.setText("保证金额");
			jLabel18.setBounds(new java.awt.Rectangle(47, 315, 89, 24));
			jLabel19.setText("是否敏感产品");
			jLabel19.setBounds(new java.awt.Rectangle(270, 315, 76, 24));
			jLabel20.setText("是否限制产品");
			jLabel20.setBounds(new java.awt.Rectangle(434, 315, 77, 24));
			jLabel21.setText("总页数");
			jLabel21.setBounds(new java.awt.Rectangle(47, 349, 89, 24));
			jLabel22.setText("币制");
			jLabel22.setBounds(new java.awt.Rectangle(344, 349, 87, 24));
			pnHead.add(jLabel, null);
			pnHead.add(jLabel1, null);
			pnHead.add(getTfEmsNo(), null);
			pnHead.add(getTfContractNo(), null);
			pnHead.add(jLabel2, null);
			pnHead.add(jLabel3, null);
			pnHead.add(getTfCompanyName(), null);
			pnHead.add(getTfTelephone(), null);
			pnHead.add(jLabel4, null);
			pnHead.add(jLabel5, null);
			pnHead.add(getTfEndDate(), null);
			pnHead.add(getTfEmsApprNo(), null);
			pnHead.add(jLabel6, null);
			pnHead.add(jLabel7, null);
			pnHead.add(jLabel8, null);
			pnHead.add(getTfImpAmount(), null);
			pnHead.add(getTfExpAmount(), null);
			pnHead.add(getTfProcessTotalPrice(), null);
			pnHead.add(lbCurrency, null);
			pnHead.add(jLabel10, null);
			pnHead.add(jLabel11, null);
			pnHead.add(getTfImpCDCount(), null);
			pnHead.add(getTfExpCDCount(), null);
			pnHead.add(jLabel12, null);
			pnHead.add(jLabel13, null);
			pnHead.add(jLabel14, null);
			pnHead.add(jLabel15, null);
			pnHead.add(getTfInternalSaleWarrant(), null);
			pnHead.add(getTfInternalSaleTaxBill(), null);
			pnHead.add(getTfInternalSale(), null);
			pnHead.add(getTfInternalSaleTax(), null);
			pnHead.add(jLabel16, null);
			pnHead.add(jLabel17, null);
			pnHead.add(getTfRemainMoney(), null);
			pnHead.add(getTfRemainEmsNo(), null);
			pnHead.add(jLabel18, null);
			pnHead.add(jLabel19, null);
			pnHead.add(jLabel20, null);
			pnHead.add(jLabel21, null);
			pnHead.add(jLabel22, null);
			pnHead.add(getTfCurrency(), null);
			pnHead.add(getTfCautionMoney(), null);
			pnHead.add(getCbbIsSusceptivity(), null);
			pnHead.add(getCbbIsLimit(), null);
			pnHead.add(getTfTotalPages(), null);
			pnHead.add(getBtnSave(), null);
			pnHead.add(getBtnUndo(), null);
			pnHead.add(getBtnEdit(), null);
			pnHead.add(getBtnReCal(), null);
		}
		return pnHead;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnExg() {
		if (pnExg == null) {
			GridBagConstraints gridBagConstraints50 = new GridBagConstraints();
			gridBagConstraints50.insets = new java.awt.Insets(5, 13, 11, 599);
			gridBagConstraints50.gridy = 1;
			gridBagConstraints50.ipadx = 25;
			gridBagConstraints50.ipady = -1;
			gridBagConstraints50.gridx = 0;
			GridBagConstraints gridBagConstraints49 = new GridBagConstraints();
			gridBagConstraints49.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints49.gridx = 0;
			gridBagConstraints49.gridy = 0;
			gridBagConstraints49.ipadx = 242;
			gridBagConstraints49.ipady = -37;
			gridBagConstraints49.weightx = 1.0;
			gridBagConstraints49.weighty = 1.0;
			gridBagConstraints49.insets = new java.awt.Insets(1, 0, 5, 0);
			pnExg = new JPanel();
			pnExg.setLayout(new GridBagLayout());
			pnExg.add(getJScrollPane(), gridBagConstraints49);
			pnExg.add(getBtnExgEdit(), gridBagConstraints50);
		}
		return pnExg;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnImg() {
		if (pnImg == null) {
			GridBagConstraints gridBagConstraints56 = new GridBagConstraints();
			gridBagConstraints56.insets = new java.awt.Insets(6, 8, 8, 104);
			gridBagConstraints56.gridy = 1;
			gridBagConstraints56.ipadx = 6;
			gridBagConstraints56.ipady = -3;
			gridBagConstraints56.gridx = 3;
			GridBagConstraints gridBagConstraints55 = new GridBagConstraints();
			gridBagConstraints55.insets = new java.awt.Insets(6, 105, 8, 2);
			gridBagConstraints55.gridy = 1;
			gridBagConstraints55.ipady = -1;
			gridBagConstraints55.gridx = 4;
			GridBagConstraints gridBagConstraints54 = new GridBagConstraints();
			gridBagConstraints54.insets = new java.awt.Insets(6, 5, 8, 7);
			gridBagConstraints54.gridy = 1;
			gridBagConstraints54.ipadx = 3;
			gridBagConstraints54.ipady = -3;
			gridBagConstraints54.gridx = 2;
			GridBagConstraints gridBagConstraints53 = new GridBagConstraints();
			gridBagConstraints53.insets = new java.awt.Insets(6, 3, 8, 4);
			gridBagConstraints53.gridy = 1;
			gridBagConstraints53.ipadx = 5;
			gridBagConstraints53.ipady = -3;
			gridBagConstraints53.gridx = 1;
			GridBagConstraints gridBagConstraints52 = new GridBagConstraints();
			gridBagConstraints52.insets = new java.awt.Insets(6, 7, 8, 2);
			gridBagConstraints52.gridy = 1;
			gridBagConstraints52.ipady = -3;
			gridBagConstraints52.gridx = 0;
			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
			gridBagConstraints51.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints51.gridwidth = 5;
			gridBagConstraints51.gridx = 0;
			gridBagConstraints51.gridy = 0;
			gridBagConstraints51.ipadx = 256;
			gridBagConstraints51.ipady = -7;
			gridBagConstraints51.weightx = 1.0;
			gridBagConstraints51.weighty = 1.0;
			gridBagConstraints51.insets = new java.awt.Insets(3, 1, 6, 0);
			pnImg = new JPanel();
			pnImg.setLayout(new GridBagLayout());
			pnImg.add(getJScrollPane1(), gridBagConstraints51);
			pnImg.add(getBtnInteralBuy(), gridBagConstraints52);
			pnImg.add(getBtnDelete(), gridBagConstraints53);
			pnImg.add(getJButton3(), gridBagConstraints54);
			pnImg.add(getCbImg(), gridBagConstraints55);
			pnImg.add(getBtnImgEdit(), gridBagConstraints56);
		}
		return pnImg;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnBom() {
		if (pnBom == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 2;
			gridBagConstraints.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints.gridy = 1;
			GridBagConstraints gridBagConstraints64 = new GridBagConstraints();
			gridBagConstraints64.insets = new java.awt.Insets(8, 9, 10, 2);
			gridBagConstraints64.gridx = 0;
			gridBagConstraints64.gridy = 1;
			gridBagConstraints64.ipadx = 24;
			gridBagConstraints64.ipady = -3;
			gridBagConstraints64.gridheight = 3;
			GridBagConstraints gridBagConstraints63 = new GridBagConstraints();
			gridBagConstraints63.insets = new java.awt.Insets(0, 24, 3, 39);
			gridBagConstraints63.gridy = 3;
			gridBagConstraints63.ipadx = 25;
			gridBagConstraints63.ipady = -13;
			gridBagConstraints63.gridx = 3;
			GridBagConstraints gridBagConstraints62 = new GridBagConstraints();
			gridBagConstraints62.insets = new java.awt.Insets(13, 24, 0, 46);
			gridBagConstraints62.gridx = 3;
			gridBagConstraints62.gridy = 1;
			gridBagConstraints62.ipadx = 18;
			gridBagConstraints62.ipady = -12;
			gridBagConstraints62.anchor = GridBagConstraints.SOUTH;
			gridBagConstraints62.gridheight = 2;
			GridBagConstraints gridBagConstraints61 = new GridBagConstraints();
			gridBagConstraints61.insets = new java.awt.Insets(1, 24, 5, 61);
			gridBagConstraints61.gridy = 1;
			gridBagConstraints61.ipadx = 3;
			gridBagConstraints61.ipady = -13;
			gridBagConstraints61.anchor = GridBagConstraints.NORTH;
			gridBagConstraints61.gridx = 3;
			GridBagConstraints gridBagConstraints60 = new GridBagConstraints();
			gridBagConstraints60.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints60.gridx = 2;
			gridBagConstraints60.gridy = 3;
			gridBagConstraints60.ipadx = 8;
			gridBagConstraints60.ipady = -3;
			gridBagConstraints60.gridheight = 1;
			GridBagConstraints gridBagConstraints59 = new GridBagConstraints();
			gridBagConstraints59.insets = new java.awt.Insets(1, 3, 8, 0);
			gridBagConstraints59.gridx = 1;
			gridBagConstraints59.gridy = 2;
			gridBagConstraints59.ipadx = 10;
			gridBagConstraints59.ipady = -11;
			gridBagConstraints59.gridheight = 2;
			GridBagConstraints gridBagConstraints58 = new GridBagConstraints();
			gridBagConstraints58.insets = new java.awt.Insets(3, 3, 1, 1);
			gridBagConstraints58.gridy = 1;
			gridBagConstraints58.ipadx = 9;
			gridBagConstraints58.ipady = -11;
			gridBagConstraints58.gridx = 1;
			GridBagConstraints gridBagConstraints57 = new GridBagConstraints();
			gridBagConstraints57.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints57.gridwidth = 4;
			gridBagConstraints57.gridx = 0;
			gridBagConstraints57.gridy = 0;
			gridBagConstraints57.ipadx = 240;
			gridBagConstraints57.ipady = -35;
			gridBagConstraints57.weightx = 1.0;
			gridBagConstraints57.weighty = 1.0;
			gridBagConstraints57.insets = new java.awt.Insets(3, 1, 1, 0);
			pnBom = new JPanel();
			pnBom.setLayout(new GridBagLayout());
			pnBom.add(getJScrollPane2(), gridBagConstraints57);
			pnBom.add(getRbTotal(), gridBagConstraints58);
			pnBom.add(getRbWaste(), gridBagConstraints59);
			pnBom.add(getJButton4(), gridBagConstraints60);
			pnBom.add(getCbBom1(), gridBagConstraints61);
			pnBom.add(getCbBom2(), gridBagConstraints62);
			pnBom.add(getCbBom3(), gridBagConstraints63);
			pnBom.add(getBtnUnitWasteEdit(), gridBagConstraints64);
			pnBom.add(getJButton(), gridBagConstraints);
		}
		return pnBom;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnOther() {
		if (pnOther == null) {
			pnOther = new JPanel();
			pnOther.setLayout(null);
			pnOther.add(getJPanel6(), null);
			pnOther.add(getJButton5(), null);
		}
		return pnOther;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getRbSelf());
			jToolBar.add(getRbCustoms());
			jToolBar.add(getBtnCheck());
			jToolBar.add(getBtnDeclare());
			jToolBar.add(getBtnProccess());
			jToolBar.add(getBtnClose());
			jToolBar.add(getBtnPrint());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbSelf() {
		if (rbSelf == null) {
			rbSelf = new JRadioButton();
			rbSelf.setText("自用核销表");
			rbSelf.setSelected(true);
			rbSelf.setVisible(true);
			rbSelf.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						switch (jTabbedPane.getSelectedIndex()) {
						case 0:
							showData(dzscContractCavSelf);
							break;
						case 1:
							showCustomsDeclaration(false);
							break;
						case 2:
							showExgData(false);
							break;
						case 3:
							showImgData(false);
							break;
						case 4:
							showBomData(false);
							break;
						case 5:
							break;
						}
						setState();
					}
				}
			});
		}
		return rbSelf;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbCustoms() {
		if (rbCustoms == null) {
			rbCustoms = new JRadioButton();
			rbCustoms.setText("海关核销表");
			rbCustoms.setSelected(false);
			rbCustoms.setVisible(true);
			rbCustoms.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						switch (jTabbedPane.getSelectedIndex()) {
						case 0:
							// test
							System.out.println("显示进口总金额1："
									+ dzscContractCavCustoms.getImpAmount());
							showData(dzscContractCavCustoms);
							break;
						case 1:
							showCustomsDeclaration(true);
							break;
						case 2:
							showExgData(true);
							break;
						case 3:
							showImgData(true);
							break;
						case 4:
							showBomData(true);
							break;
						case 5:
							break;
						}
						setState();
					}
				}
			});
		}
		return rbCustoms;
	}

	/**
	 * This method initializes buttonGroup1
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getBgSelectModel() {
		if (bgSelectModel == null) {
			bgSelectModel = new ButtonGroup();
			bgSelectModel.add(this.getRbSelf());
			bgSelectModel.add(this.getRbCustoms());
		}
		return bgSelectModel;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getJPopupMenuPrintReport().show(btnPrint, 0,
							btnPrint.getHeight());
				}
			});
		}
		return btnPrint;
	}

	/*
	 * this.cbbPrint.addItem("非套打核销表头"); this.cbbPrint.addItem(" 套打核销表头");
	 * this.cbbPrint.addItem("非套打核销料件表"); this.cbbPrint.addItem(" 套打核销料件表");
	 * this.cbbPrint.addItem("非套打核销成品表"); this.cbbPrint.addItem(" 套打核销成品表");
	 * this.cbbPrint.addItem("非套打核销单耗表"); this.cbbPrint.addItem(" 套打核销单耗表");
	 */

	/**
	 * 0非套打核销表头
	 */
	public void printNonCoverPrintApplied() {
		DzscContractCav dzscContractCav = null;
		if (rbCustoms.isSelected()) {
			dzscContractCav = this.dzscContractCavCustoms;
		} else {
			dzscContractCav = this.dzscContractCavSelf;
		}
		if (dzscContractCav == null) {
			return;
		}
		// DzscContractCav contractCav = null;
		InputStream reportStream = null;
		List<Object> list = new ArrayList<Object>();
		Map<String, Object> parameters = new HashMap<String, Object>();
		CustomReportDataSource ds = new CustomReportDataSource(list);
		Company company = (Company) dzscContractCav.getCompany();
		list.add(dzscContractCav);
		reportStream = DgDzscContractCav.class
				.getResourceAsStream("report/ContractCavHead.jasper");
		parameters = new HashMap<String, Object>();
		parameters.put("companyCode", company.getCode());
		parameters.put("companyName", company.getName());
		parameters.put("companyTel", company.getTel());
		parameters.put("overprint", new Boolean(false));
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 1套打核销表头
	 */
	public void printMiCoverPrintApplied() {
		DzscContractCav dzscContractCav = null;
		if (rbCustoms.isSelected()) {
			dzscContractCav = this.dzscContractCavCustoms;
		} else {
			dzscContractCav = this.dzscContractCavSelf;
		}
		if (dzscContractCav == null) {
			return;
		}
		// DzscContractCav contractCav = null;
		InputStream reportStream = null;
		List<Object> list = new ArrayList<Object>();
		Map<String, Object> parameters = new HashMap<String, Object>();
		CustomReportDataSource ds = new CustomReportDataSource(list);
		Company company = (Company) dzscContractCav.getCompany();
		list.add(dzscContractCav);
		reportStream = DgDzscContractCav.class
				.getResourceAsStream("report/ContractCavHead.jasper");
		parameters.put("companyCode", company.getBuCode());
		parameters.put("companyName", company.getBuName());
		parameters.put("companyTel", company.getTel());
		parameters.put("overprint", new Boolean(true));
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 2非套打核销料件表头
	 */
	public void printMiNonCoverPrintAppliedImg() {
		DzscContractCav dzscContractCav = null;
		if (rbCustoms.isSelected()) {
			dzscContractCav = this.dzscContractCavCustoms;
		} else {
			dzscContractCav = this.dzscContractCavSelf;
		}
		if (dzscContractCav == null) {
			return;
		}
		// DzscContractCav contractCav = null;
		InputStream reportStream = null;
		List<Object> list = new ArrayList<Object>();
		Map<String, Object> parameters = new HashMap<String, Object>();
		CustomReportDataSource ds = new CustomReportDataSource(list);
		Company company = (Company) dzscContractCav.getCompany();
		String emsNo = "";
		list = contractCavAction.findContractImgCav(new Request(CommonVars
				.getCurrUser()), dzscContractCav);
		ds = new CustomReportDataSource(list);
		emsNo = contract.getEmsNo() == null ? "" : contract.getEmsNo();
		reportStream = DgDzscContractCav.class
				.getResourceAsStream("report/ContractImgCav.jasper");
		Integer decimalLength = 2;
		if (dzscParameterSet != null
				&& dzscParameterSet.getReportDecimalLength() != null)
			decimalLength = dzscParameterSet.getReportDecimalLength();
		parameters.put("size", decimalLength.toString());
		parameters.put("name", company.getName());
		parameters.put("emsNo", emsNo);

		parameters.put("overprint", new Boolean(false));

		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 3套打核销料件表头
	 */
	public void printMiCoverPrintAppliedImg() {
		DzscContractCav dzscContractCav = null;
		if (rbCustoms.isSelected()) {
			dzscContractCav = this.dzscContractCavCustoms;
		} else {
			dzscContractCav = this.dzscContractCavSelf;
		}
		if (dzscContractCav == null) {
			return;
		}
		// DzscContractCav contractCav = null;
		InputStream reportStream = null;
		List<Object> list = new ArrayList<Object>();
		Map<String, Object> parameters = new HashMap<String, Object>();
		CustomReportDataSource ds = new CustomReportDataSource(list);
		Company company = (Company) dzscContractCav.getCompany();
		String emsNo = "";
		list = contractCavAction.findContractImgCav(new Request(CommonVars
				.getCurrUser()), dzscContractCav);
		ds = new CustomReportDataSource(list);
		emsNo = contract.getEmsNo() == null ? "" : contract.getEmsNo();
		reportStream = DgDzscContractCav.class
				.getResourceAsStream("report/ContractImgCav.jasper");
		parameters.put("overprint", new Boolean(true));
		parameters.put("name", company.getName());
		parameters.put("emsNo", emsNo);

		Integer decimalLength = 2;
		if (dzscParameterSet != null
				&& dzscParameterSet.getReportDecimalLength() != null)
			decimalLength = dzscParameterSet.getReportDecimalLength();
		parameters.put("size", decimalLength.toString());
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 4非套打核销成品表头
	 */
	public void printNonCoverPrintAppliedExg() {
		DzscContractCav dzscContractCav = null;
		if (rbCustoms.isSelected()) {
			dzscContractCav = this.dzscContractCavCustoms;
		} else {
			dzscContractCav = this.dzscContractCavSelf;
		}
		if (dzscContractCav == null) {
			return;
		}
		// DzscContractCav contractCav = null;
		InputStream reportStream = null;
		List<Object> list = new ArrayList<Object>();
		Map<String, Object> parameters = new HashMap<String, Object>();
		CustomReportDataSource ds = new CustomReportDataSource(list);
		Company company = (Company) dzscContractCav.getCompany();
		String emsNo = "";
		list = contractCavAction.findContractExgCav(new Request(CommonVars
				.getCurrUser()), dzscContractCav);
		ds = new CustomReportDataSource(list);
		emsNo = contract.getEmsNo() == null ? "" : contract.getEmsNo();
		reportStream = DgDzscContractCav.class
				.getResourceAsStream("report/ContractExgCav.jasper");
		parameters.put("overprint", new Boolean(false));
		parameters.put("name", company.getName());
		parameters.put("emsNo", emsNo);

		Integer decimalLength = 2;
		if (dzscParameterSet != null
				&& dzscParameterSet.getReportDecimalLength() != null)
			decimalLength = dzscParameterSet.getReportDecimalLength();
		parameters.put("size", decimalLength.toString());
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 5套打核销成品表头
	 */
	public void printMiCoverPrintAppliedExg() {
		DzscContractCav dzscContractCav = null;
		if (rbCustoms.isSelected()) {
			dzscContractCav = this.dzscContractCavCustoms;
		} else {
			dzscContractCav = this.dzscContractCavSelf;
		}
		if (dzscContractCav == null) {
			return;
		}
		// DzscContractCav contractCav = null;
		InputStream reportStream = null;
		List<Object> list = new ArrayList<Object>();
		Map<String, Object> parameters = new HashMap<String, Object>();
		CustomReportDataSource ds = new CustomReportDataSource(list);
		Company company = (Company) dzscContractCav.getCompany();
		String emsNo = "";
		list = contractCavAction.findContractExgCav(new Request(CommonVars
				.getCurrUser()), dzscContractCav);
		ds = new CustomReportDataSource(list);
		emsNo = contract.getEmsNo() == null ? "" : contract.getEmsNo();
		reportStream = DgDzscContractCav.class
				.getResourceAsStream("report/ContractExgCav.jasper");
		parameters.put("overprint", new Boolean(true));
		parameters.put("name", company.getName());
		parameters.put("emsNo", emsNo);

		Integer decimalLength = 2;
		if (dzscParameterSet != null
				&& dzscParameterSet.getReportDecimalLength() != null)
			decimalLength = dzscParameterSet.getReportDecimalLength();
		parameters.put("size", decimalLength.toString());
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 6非套打核销单耗表头
	 */
	public void printNonCoverPrintAppliedUnitConsumption() {
		DzscContractCav dzscContractCav = null;
		if (rbCustoms.isSelected()) {
			dzscContractCav = this.dzscContractCavCustoms;
		} else {
			dzscContractCav = this.dzscContractCavSelf;
		}
		if (dzscContractCav == null) {
			return;
		}
		// DzscContractCav contractCav = null;
		InputStream reportStream = null;
		List<Object> list = new ArrayList<Object>();
		Map<String, Object> parameters = new HashMap<String, Object>();
		CustomReportDataSource ds = new CustomReportDataSource(list);
		Company company = (Company) dzscContractCav.getCompany();
		try {
			List<List> resultList = new ArrayList<List>();
			List<DzscContractUnitWasteCav> unitWasteList = new ArrayList<DzscContractUnitWasteCav>();
			List<DzscContractExgCav> exgCavList = new ArrayList<DzscContractExgCav>();
			int count = 0;
			String emsNo = "";
			System.out.println("dzscContractCav ==------------------------- "
					+ dzscContractCav);
			if (dzscContractCav != null) {
				String parentId = dzscContractCav.getId();
				resultList = contractCavAction.findDzscContractUnitWasteCav(
						new Request(CommonVars.getCurrUser()), dzscContractCav,
						-1, -1);
				exgCavList = resultList.get(0);
				unitWasteList = resultList.get(1);
				count = exgCavList.size();
				emsNo = contract.getEmsNo() == null ? "" : contract.getEmsNo();
			}
			DzscContractcavReportVars.setDzscContractExgCavList(exgCavList);
			CustomReportDataSource ds1 = new CustomReportDataSource(
					unitWasteList);
			InputStream masterReportStream = DgDzscContractCav.class
					.getResourceAsStream("report/ContractUnitWasteCav.jasper");
			parameters.put("isOverprint", new Boolean(false));
			parameters.put("count", count);
			parameters.put("name", company.getName());
			parameters.put("emsNo", emsNo);

			Integer decimalLength = 6;
			if (dzscParameterSet != null
					&& dzscParameterSet.getReportDecimalLength() != null)
				decimalLength = dzscParameterSet.getReportDecimalLength();
			parameters.put("size", decimalLength.toString());
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds1);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * ７套打核销单耗表头
	 */
	public void printCoverPrintAppliedUnitConsumption() {
		DzscContractCav dzscContractCav = null;
		if (rbCustoms.isSelected()) {
			dzscContractCav = this.dzscContractCavCustoms;
		} else {
			dzscContractCav = this.dzscContractCavSelf;
		}
		if (dzscContractCav == null) {
			return;
		}
		// DzscContractCav contractCav = null;
		InputStream reportStream = null;
		List<Object> list = new ArrayList<Object>();
		Map<String, Object> parameters = new HashMap<String, Object>();
		CustomReportDataSource ds = new CustomReportDataSource(list);
		Company company = (Company) dzscContractCav.getCompany();
		try {
			List<List> resultList = new ArrayList<List>();
			List<DzscContractUnitWasteCav> unitWasteList = new ArrayList<DzscContractUnitWasteCav>();
			List<DzscContractExgCav> exgCavList = new ArrayList<DzscContractExgCav>();
			int count = 0;
			String emsNo = "";
			if (dzscContractCav != null) {
				String parentId = dzscContractCav.getId();
				resultList = contractCavAction.findDzscContractUnitWasteCav(
						new Request(CommonVars.getCurrUser()), dzscContractCav,
						-1, -1);
				exgCavList = resultList.get(0);
				unitWasteList = resultList.get(1);
				for (int ss = 0; ss < exgCavList.size(); ss++) {

				}
				count = exgCavList.size();
				emsNo = contract.getEmsNo() == null ? "" : contract.getEmsNo();
			}
			DzscContractcavReportVars.setDzscContractExgCavList(exgCavList);
			CustomReportDataSource ds1 = new CustomReportDataSource(
					unitWasteList);
			unitWasteList = resultList.get(1);
			count = exgCavList.size();

			// if(resultList == null){
			// JOptionPane.showConfirmDialog(
			// DgDzscContractCav.this, "资料为空或不完整，无法打印",
			// "提示",JOptionPane.OK_OPTION);
			// }
			DzscContractcavReportVars.setDzscContractExgCavList(exgCavList);
			// CustomReportDataSource ds1 = new CustomReportDataSource(
			// unitWasteList);
			InputStream masterReportStream = DgDzscContractCav.class
					.getResourceAsStream("report/ContractUnitWasteCav.jasper");
			parameters.put("isOverprint", new Boolean(true));
			parameters.put("count", count);
			parameters.put("emsNo", emsNo);
			parameters.put("name", company.getName());

			Integer decimalLength = 2;
			if (dzscParameterSet != null
					&& dzscParameterSet.getReportDecimalLength() != null)
				decimalLength = dzscParameterSet.getReportDecimalLength();
			parameters.put("size", decimalLength.toString());
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds1);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * private void printReport() { DzscContractCav dzscContractCav = null;
	 * if(rbCustoms.isSelected()) { dzscContractCav =
	 * this.dzscContractCavCustoms; } else { dzscContractCav =
	 * this.dzscContractCavSelf; } if(dzscContractCav == null) { return; } //
	 * DzscContractCav contractCav = null; InputStream reportStream = null;
	 * List<Object> list = new ArrayList<Object>(); Map<String, Object>
	 * parameters = new HashMap<String, Object>(); CustomReportDataSource ds =
	 * new CustomReportDataSource(list); Company company = (Company)
	 * dzscContractCav.getCompany(); switch (cbbPrint.getSelectedIndex()) { case
	 * 0:// 非套打核销单头 // list=contractCavAction.findContractImgCav(new Request( //
	 * CommonVars.getCurrUser()), contractCavCustoms);
	 * list.add(dzscContractCav); reportStream = DgDzscContractCav.class
	 * .getResourceAsStream("report/ContractCavHead.jasper"); parameters = new
	 * HashMap<String, Object>(); parameters.put("companyCode",
	 * company.getCode()); parameters.put("companyName", company.getName());
	 * parameters.put("companyTel", company.getTel());
	 * parameters.put("overprint", new Boolean(false)); try { JasperPrint
	 * jasperPrint = JasperFillManager.fillReport( reportStream, parameters,
	 * ds); DgReportViewer viewer = new DgReportViewer(jasperPrint);
	 * viewer.setVisible(true); } catch (JRException e1) { e1.printStackTrace();
	 * } break; case 1:// 套打核销单头 //
	 * list=contractCavAction.findContractImgCav(new Request( //
	 * CommonVars.getCurrUser()), contractCavCustoms);
	 * list.add(dzscContractCav); reportStream = DgDzscContractCav.class
	 * .getResourceAsStream("report/ContractCavHead.jasper");
	 * parameters.put("companyCode", company.getBuCode());
	 * parameters.put("companyName", company.getBuName());
	 * parameters.put("companyTel", company.getTel());
	 * parameters.put("overprint", new Boolean(true)); try { JasperPrint
	 * jasperPrint = JasperFillManager.fillReport( reportStream, parameters,
	 * ds); DgReportViewer viewer = new DgReportViewer(jasperPrint);
	 * viewer.setVisible(true); } catch (JRException e1) { e1.printStackTrace();
	 * } break; case 2:// 非套打核销料件表 list =
	 * contractCavAction.findContractImgCav(new Request(CommonVars
	 * .getCurrUser()), dzscContractCav); ds = new CustomReportDataSource(list);
	 * reportStream = DgDzscContractCav.class
	 * .getResourceAsStream("report/ContractImgCav.jasper");
	 * parameters.put("overprint", new Boolean(false)); try { JasperPrint
	 * jasperPrint = JasperFillManager.fillReport( reportStream, parameters,
	 * ds); DgReportViewer viewer = new DgReportViewer(jasperPrint);
	 * viewer.setVisible(true); } catch (JRException e1) { e1.printStackTrace();
	 * } break; case 3:// 套打核销料件表 list =
	 * contractCavAction.findContractImgCav(new Request(CommonVars
	 * .getCurrUser()), dzscContractCav); ds = new CustomReportDataSource(list);
	 * reportStream = DgDzscContractCav.class
	 * .getResourceAsStream("report/ContractImgCav.jasper");
	 * parameters.put("overprint", new Boolean(true)); try { JasperPrint
	 * jasperPrint = JasperFillManager.fillReport( reportStream, parameters,
	 * ds); DgReportViewer viewer = new DgReportViewer(jasperPrint);
	 * viewer.setVisible(true); } catch (JRException e1) { e1.printStackTrace();
	 * } break; case 4:// 非套打核销成品表 list =
	 * contractCavAction.findContractExgCav(new Request(CommonVars
	 * .getCurrUser()), dzscContractCav); ds = new CustomReportDataSource(list);
	 * reportStream = DgDzscContractCav.class
	 * .getResourceAsStream("report/ContractExgCav.jasper");
	 * parameters.put("overprint", new Boolean(false)); try { JasperPrint
	 * jasperPrint = JasperFillManager.fillReport( reportStream, parameters,
	 * ds); DgReportViewer viewer = new DgReportViewer(jasperPrint);
	 * viewer.setVisible(true); } catch (JRException e1) { e1.printStackTrace();
	 * } break; case 5:// 套打核销成品表 list =
	 * contractCavAction.findContractExgCav(new Request(CommonVars
	 * .getCurrUser()), dzscContractCav); ds = new CustomReportDataSource(list);
	 * reportStream = DgDzscContractCav.class
	 * .getResourceAsStream("report/ContractExgCav.jasper");
	 * parameters.put("overprint", new Boolean(true)); try { JasperPrint
	 * jasperPrint = JasperFillManager.fillReport( reportStream, parameters,
	 * ds); DgReportViewer viewer = new DgReportViewer(jasperPrint);
	 * viewer.setVisible(true); } catch(JRException e1) { e1.printStackTrace();
	 * } break; case 6:// 非套打核销单耗表 try { List<List> resultList = new
	 * ArrayList<List>(); List<DzscContractUnitWasteCav> unitWasteList = new
	 * ArrayList<DzscContractUnitWasteCav>(); List<DzscContractExgCav>
	 * exgCavList = new ArrayList<DzscContractExgCav>(); int count = 0;
	 * System.out .println("dzscContractCav ==------------------------- " +
	 * dzscContractCav); if(dzscContractCav != null) { String parentId =
	 * dzscContractCav.getId(); resultList = contractCavAction
	 * .findDzscContractUnitWasteCav(new Request( CommonVars.getCurrUser()),
	 * dzscContractCav, -1, -1); exgCavList = resultList.get(0); unitWasteList =
	 * resultList.get(1); count = exgCavList.size(); }
	 * DzscContractcavReportVars.setDzscContractExgCavList(exgCavList);
	 * CustomReportDataSource ds1 = new CustomReportDataSource( unitWasteList);
	 * InputStream masterReportStream = DgDzscContractCav.class
	 * .getResourceAsStream("report/ContractUnitWasteCav.jasper");
	 * parameters.put("isOverprint", new Boolean(false));
	 * parameters.put("count", count); JasperPrint jasperPrint =
	 * JasperFillManager.fillReport( masterReportStream, parameters, ds1);
	 * DgReportViewer viewer = new DgReportViewer(jasperPrint);
	 * viewer.setVisible(true); } catch(Exception ex) { ex.printStackTrace(); }
	 * break; case 7:// 套打核销单耗表 try { List<List> resultList = new
	 * ArrayList<List>(); List<DzscContractUnitWasteCav> unitWasteList = new
	 * ArrayList<DzscContractUnitWasteCav>(); List<DzscContractExgCav>
	 * exgCavList = new ArrayList<DzscContractExgCav>(); int count = 0;
	 * if(dzscContractCav != null) { String parentId = dzscContractCav.getId();
	 * resultList = contractCavAction .findDzscContractUnitWasteCav(new Request(
	 * CommonVars.getCurrUser()), dzscContractCav, -1, -1); exgCavList =
	 * resultList.get(0); unitWasteList = resultList.get(1); for (int ss = 0; ss
	 * < exgCavList.size(); ss++) { } count = exgCavList.size(); }
	 * DzscContractcavReportVars.setDzscContractExgCavList(exgCavList);
	 * CustomReportDataSource ds1 = new CustomReportDataSource( unitWasteList);
	 * 
	 * unitWasteList = resultList.get(1); count = exgCavList.size(); //
	 * if(resultList == null){ // JOptionPane.showConfirmDialog( //
	 * DgDzscContractCav.this, "资料为空或不完整，无法打印", // "提示",JOptionPane.OK_OPTION);
	 * // } DzscContractcavReportVars.setDzscContractExgCavList(exgCavList); //
	 * CustomReportDataSource ds1 = new CustomReportDataSource( //
	 * unitWasteList); InputStream masterReportStream = DgDzscContractCav.class
	 * .getResourceAsStream("report/ContractUnitWasteCav.jasper");
	 * parameters.put("isOverprint", new Boolean(true)); parameters.put("count",
	 * count); JasperPrint jasperPrint = JasperFillManager.fillReport(
	 * masterReportStream, parameters, ds1); DgReportViewer viewer = new
	 * DgReportViewer(jasperPrint); viewer.setVisible(true); } catch(Exception
	 * ex) { ex.printStackTrace(); } break; } }
	 */

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setEditable(false);
			tfEmsNo.setBounds(new java.awt.Rectangle(140, 46, 195, 24));
			tfEmsNo.setBackground(java.awt.Color.white);
		}
		return tfEmsNo;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfContractNo() {
		if (tfContractNo == null) {
			tfContractNo = new JTextField();
			tfContractNo.setEditable(false);
			tfContractNo.setBounds(new java.awt.Rectangle(434, 46, 216, 24));
			tfContractNo.setBackground(java.awt.Color.white);
		}
		return tfContractNo;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCompanyName() {
		if (tfCompanyName == null) {
			tfCompanyName = new JTextField();
			tfCompanyName.setEditable(false);
			tfCompanyName.setBounds(new java.awt.Rectangle(140, 78, 195, 24));
			tfCompanyName.setBackground(java.awt.Color.white);
		}
		return tfCompanyName;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTelephone() {
		if (tfTelephone == null) {
			tfTelephone = new JTextField();
			tfTelephone.setEditable(false);
			tfTelephone.setBounds(new java.awt.Rectangle(434, 78, 216, 24));
			tfTelephone.setBackground(java.awt.Color.white);
		}
		return tfTelephone;
	}

	/**
	 * This method initializes jTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEndDate() {
		if (tfEndDate == null) {
			tfEndDate = new JTextField();
			tfEndDate.setEditable(false);
			tfEndDate.setBounds(new java.awt.Rectangle(140, 110, 195, 24));
			tfEndDate.setBackground(java.awt.Color.white);
		}
		return tfEndDate;
	}

	/**
	 * This method initializes jTextField5
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmsApprNo() {
		if (tfEmsApprNo == null) {
			tfEmsApprNo = new JTextField();
			tfEmsApprNo.setEditable(false);
			tfEmsApprNo.setBounds(new java.awt.Rectangle(434, 110, 216, 24));
			tfEmsApprNo.setBackground(java.awt.Color.white);
		}
		return tfEmsApprNo;
	}

	/**
	 * This method initializes jTextField6
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getTfImpAmount() {
		if (tfImpAmount == null) {
			tfImpAmount = new JFormattedTextField();
			// tfImpAmount.setEditable(false);
			tfImpAmount.setBounds(new java.awt.Rectangle(140, 143, 106, 24));
			tfImpAmount.setBackground(java.awt.Color.white);
			tfImpAmount.setToolTipText("国内购买不计算在内");
			tfImpAmount.setFormatterFactory(this.getDefaultFormatterFactory());
		}
		return tfImpAmount;
	}

	/**
	 * This method initializes jTextField7
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getTfExpAmount() {
		if (tfExpAmount == null) {
			tfExpAmount = new JFormattedTextField();
			// tfExpAmount.setEditable(false);
			tfExpAmount.setBounds(new java.awt.Rectangle(336, 143, 95, 24));
			tfExpAmount.setBackground(java.awt.Color.white);
			tfExpAmount.setFormatterFactory(this.getDefaultFormatterFactory());
		}
		return tfExpAmount;
	}

	/**
	 * This method initializes jTextField8
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getTfProcessTotalPrice() {
		if (tfProcessTotalPrice == null) {
			tfProcessTotalPrice = new JFormattedTextField();
			// tfProcessTotalPrice.setEditable(false);
			tfProcessTotalPrice.setBounds(new java.awt.Rectangle(473, 143, 83,
					24));
			tfProcessTotalPrice.setBackground(java.awt.Color.white);
			tfProcessTotalPrice.setFormatterFactory(this
					.getDefaultFormatterFactory());
		}
		return tfProcessTotalPrice;
	}

	/**
	 * This method initializes jTextField9
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfImpCDCount() {
		if (tfImpCDCount == null) {
			tfImpCDCount = new JTextField();
			tfImpCDCount.setEditable(false);
			tfImpCDCount.setBounds(new java.awt.Rectangle(140, 175, 195, 24));
			tfImpCDCount.setBackground(java.awt.Color.white);
		}
		return tfImpCDCount;
	}

	/**
	 * This method initializes jTextField10
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExpCDCount() {
		if (tfExpCDCount == null) {
			tfExpCDCount = new JTextField();
			tfExpCDCount.setEditable(false);
			tfExpCDCount.setBounds(new java.awt.Rectangle(434, 175, 216, 24));
			tfExpCDCount.setBackground(java.awt.Color.white);
		}
		return tfExpCDCount;
	}

	/**
	 * This method initializes jTextField12
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInternalSaleWarrant() {
		if (tfInternalSaleWarrant == null) {
			tfInternalSaleWarrant = new JTextField();
			tfInternalSaleWarrant.setBounds(new java.awt.Rectangle(140, 243,
					195, 24));
		}
		return tfInternalSaleWarrant;
	}

	/**
	 * This method initializes jTextField13
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInternalSaleTaxBill() {
		if (tfInternalSaleTaxBill == null) {
			tfInternalSaleTaxBill = new JTextField();
			tfInternalSaleTaxBill.setBounds(new java.awt.Rectangle(434, 243,
					216, 24));
		}
		return tfInternalSaleTaxBill;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfInternalSale() {
		if (tfInternalSale == null) {
			tfInternalSale = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfInternalSale.setBounds(new java.awt.Rectangle(140, 209, 195, 24));
			tfInternalSale.setFormatterFactory(getDefaultFormatterFactory());

		}
		return tfInternalSale;
	}

	/**
	 * This method initializes jFormattedTextField1
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfInternalSaleTax() {
		if (tfInternalSaleTax == null) {
			tfInternalSaleTax = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfInternalSaleTax.setBounds(new java.awt.Rectangle(434, 209, 216,
					24));
			tfInternalSaleTax.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfInternalSaleTax;
	}

	/**
	 * This method initializes jTextField11
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getTfRemainMoney() {
		if (tfRemainMoney == null) {
			tfRemainMoney = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			// tfRemainMoney.setEditable(false);
			tfRemainMoney.setBounds(new java.awt.Rectangle(140, 279, 195, 24));
			tfRemainMoney.setBackground(java.awt.Color.white);
			tfRemainMoney
					.setFormatterFactory(this.getDefaultFormatterFactory());
		}
		return tfRemainMoney;
	}

	/**
	 * This method initializes jTextField14
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfRemainEmsNo() {
		if (tfRemainEmsNo == null) {
			tfRemainEmsNo = new JTextField();
			// tfRemainEmsNo.setEditable(false);
			tfRemainEmsNo.setBounds(new java.awt.Rectangle(434, 279, 216, 24));
			tfRemainEmsNo.setBackground(java.awt.Color.white);
		}
		return tfRemainEmsNo;
	}

	/**
	 * This method initializes jTextField16
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCurrency() {
		if (tfCurrency == null) {
			tfCurrency = new JTextField();
			tfCurrency.setEditable(false);
			tfCurrency.setBounds(new java.awt.Rectangle(434, 349, 216, 24));
			tfCurrency.setBackground(java.awt.Color.white);
		}
		return tfCurrency;
	}

	/**
	 * This method initializes jTextField17
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getTfCautionMoney() {
		if (tfCautionMoney == null) {
			tfCautionMoney = new JFormattedTextField();
			// tfCautionMoney.setEditable(false);
			tfCautionMoney.setBounds(new java.awt.Rectangle(140, 315, 106, 24));
			tfCautionMoney.setBackground(java.awt.Color.white);
			tfCautionMoney.setFormatterFactory(this
					.getDefaultFormatterFactory());

		}
		return tfCautionMoney;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbIsSusceptivity() {
		if (cbbIsSusceptivity == null) {
			cbbIsSusceptivity = new JComboBox();
			cbbIsSusceptivity
					.setBounds(new java.awt.Rectangle(346, 315, 86, 24));
		}
		return cbbIsSusceptivity;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbIsLimit() {
		if (cbbIsLimit == null) {
			cbbIsLimit = new JComboBox();
			cbbIsLimit.setBounds(new java.awt.Rectangle(513, 315, 136, 24));
		}
		return cbbIsLimit;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.setBounds(new java.awt.Rectangle(39, 399, 64, 25));
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// test
					System.out.println("填充前进口总金额："
							+ dzscContractCavCustoms.getImpAmount());
					fillData(dzscContractCavCustoms);
					System.out.println("填充后进口总金额："
							+ dzscContractCavCustoms.getImpAmount());
					contractCavAction.saveContractCav(new Request(CommonVars
							.getCurrUser()), dzscContractCavCustoms);
					System.out.println("保存后进口总金额："
							+ dzscContractCavCustoms.getImpAmount());
					dataState = DataState.BROWSE;
					setState(); // actionPerformed()

					// test
					System.out.println("保存进口总金额："
							+ dzscContractCavCustoms.getImpAmount());
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUndo() {
		if (btnUndo == null) {
			btnUndo = new JButton();
			btnUndo.setText("取消");
			btnUndo.setBounds(new java.awt.Rectangle(110, 399, 60, 25));
			btnUndo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// if (rbSelf.isSelected()) {
					// showData(contractCavSelf);
					// } else {
					showData(dzscContractCavCustoms);
					// }
					dataState = DataState.BROWSE;
					setState(); // actionPerformed()
				}
			});
		}
		return btnUndo;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCheck() {
		if (btnCheck == null) {
			btnCheck = new JButton();
			btnCheck.setText("检查");
			btnCheck.setVisible(false);
			btnCheck.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (contractCavAction.checkDzscContractCavData(new Request(
							CommonVars.getCurrUser()), dzscContractCavCustoms)) {
						JOptionPane.showMessageDialog(DgDzscContractCav.this,
								"所有料件余料/进口数量小于20%!", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		return btnCheck;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfTotalPages() {
		if (tfTotalPages == null) {
			tfTotalPages = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfTotalPages.setBounds(new java.awt.Rectangle(140, 349, 195, 24));
			tfTotalPages.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfTotalPages;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbExg() {
		if (tbExg == null) {
			tbExg = new JTable();
		}
		return tbExg;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbExg());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbImg() {
		if (tbImg == null) {
			tbImg = new JTable();
		}
		return tbImg;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbImg());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable2
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTblBom() {
		if (tblBom == null) {
			tblBom = new JTable();
		}
		return tblBom;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getTblBom());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnInteralBuy() {
		if (btnInteralBuy == null) {
			btnInteralBuy = new JButton();
			btnInteralBuy.setText("国内购买");
			btnInteralBuy
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgDzscContractInteralBuy dgContractImgCav = new DgDzscContractInteralBuy();
							dgContractImgCav
									.setContractCav(dzscContractCavCustoms);
							dgContractImgCav.setTableModel(tableModelImg);
							dgContractImgCav.setVisible(true);
							// DzscContractImgCav contractImgCav =
							// dgContractImgCav
							// .getContractImgCav();
							// if (contractImgCav != null) {
							// tableModelImg.addRow(contractImgCav);
							// }
							// List list=tableModelImg.getList();
							// list.add(contractImgCav);
							// resortDataForInteralBuy(list);
							// if (contractImgCav != null) {
							// tableModelImg.setList(list);
							// }
							showImgData(true);
						}
					});
		}
		return btnInteralBuy;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelImg.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgDzscContractCav.this,
								"请选择要删除的数据", "提示", JOptionPane.OK_OPTION);
						return;
					}
					DzscContractImgCav contractImgCav = (DzscContractImgCav) tableModelImg
							.getCurrentRow();
					if (contractImgCav.getName().indexOf("(国内购买)") < 0) {
						JOptionPane.showMessageDialog(DgDzscContractCav.this,
								"只删除国内购买的数据", "提示", JOptionPane.OK_OPTION);
						return;
					}
					if (JOptionPane.showConfirmDialog(DgDzscContractCav.this,
							"提示", "你确定要删除此海关核销料件吗？",
							JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {

						contractCavAction.deleteContractImgCav(new Request(
								CommonVars.getCurrUser()), contractImgCav);
						tableModelImg.deleteRow(contractImgCav);
					}
				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (btnLeftoverMaterial == null) {
			btnLeftoverMaterial = new JButton();
			btnLeftoverMaterial.setText("计算边角料");
			btnLeftoverMaterial
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (dzscContractCavCustoms == null) {
								return;
							}
							contractCavAction.recalRemainMaterialAmount(
									new Request(CommonVars.getCurrUser()),
									dzscContractCavCustoms);
							new find().start();
							// showExgData(true);
						}
					});
		}
		return btnLeftoverMaterial;
	}

	/**
	 * This method initializes miModified
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiModified() {
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
	private JMenuItem getMiDelete() {
		if (miDelete == null) {
			miDelete = new JMenuItem();
			miDelete.setText("\u5df2\u5220\u9664");
		}
		return miDelete;
	}

	private JMenuItem getMiAdd() {
		if (miAdd == null) {
			miAdd = new JMenuItem();
			miAdd.setText("新  增");
		}
		return miAdd;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbImg() {
		if (cbImg == null) {
			cbImg = new JCheckBox();
			cbImg.setText("修改成品耗用不反算单耗");
			cbImg.setSelected(true);
		}
		return cbImg;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbTotal() {
		if (rbTotal == null) {
			rbTotal = new JRadioButton();
			rbTotal.setText("总用量");
			rbTotal.setSelected(true);
		}
		return rbTotal;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbWaste() {
		if (rbWaste == null) {
			rbWaste = new JRadioButton();
			rbWaste.setText("损耗量");
		}
		return rbWaste;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (btnRoundAmount == null) {
			btnRoundAmount = new JButton();
			btnRoundAmount.setText("数  量  取  整");
			btnRoundAmount.setActionCommand("数量取整  ");
			btnRoundAmount
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (JOptionPane.showConfirmDialog(
									DgDzscContractCav.this, "你确定要数量取整?", "提示",
									JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
								return;
							}
							new SwingWorker() {
								protected Object doInBackground()
										throws Exception {
									try {
										CommonProgress.showProgressDialog();
										CommonProgress
												.setMessage("系统正在执行任务，请稍候！");
										contractCavAction
												.convertWasteAmountToInteger(
														new Request(CommonVars
																.getCurrUser()),
														dzscContractCavCustoms,
														rbTotal.isSelected(),
														cbBom2.isSelected(),
														cbBom3.isSelected());
									} catch (Exception e) {
										CommonProgress.closeProgressDialog();
										e.printStackTrace();
									}
									return null;
								}

								@Override
								protected void done() {
									try {

										showBomData(rbCustoms.isSelected());
										CommonProgress.closeProgressDialog();
									} catch (Exception ignore) {
										CommonProgress.closeProgressDialog();
										ignore.printStackTrace();
									}
								}
							}.execute();

						}
					});
		}
		return btnRoundAmount;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbBom1() {
		if (cbBom1 == null) {
			cbBom1 = new JCheckBox();
			cbBom1.setText("修改单耗不反算损耗量，总用量，成品耗用量和料件余量");
			cbBom1.setSelected(true);
		}
		return cbBom1;
	}

	/**
	 * This method initializes jCheckBox1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbBom2() {
		if (cbBom2 == null) {
			cbBom2 = new JCheckBox();
			cbBom2.setText("修改总用量不反算损耗量，单耗，成品耗用量和料件余量");
			cbBom2.setSelected(true);
		}
		return cbBom2;
	}

	/**
	 * This method initializes jCheckBox2
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbBom3() {
		if (cbBom3 == null) {
			cbBom3 = new JCheckBox();
			cbBom3.setText("修改损耗量不反算损耗，总用量，成品耗用量和料件余量");
			cbBom3.setSelected(true);
		}
		return cbBom3;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getBgAmount() {
		if (bgAmount == null) {
			bgAmount = new ButtonGroup();
			bgAmount.add(this.getRbTotal());
			bgAmount.add(this.getRbWaste());
		}
		return bgAmount;
	}

	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			jPanel6 = new JPanel();
			jLabel23 = new JLabel();
			jLabel24 = new JLabel();
			jLabel25 = new JLabel();
			jLabel26 = new JLabel();
			jLabel27 = new JLabel();
			jLabel28 = new JLabel();
			jLabel29 = new JLabel();
			jLabel9 = new JLabel();
			jPanel6.setLayout(null);
			jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jPanel6.setBounds(new Rectangle(13, 37, 682, 88));
			jLabel23.setBounds(21, 51, 50, 21);
			jLabel23.setText("出口总值");
			jLabel24.setBounds(152, 17, 64, 22);
			jLabel24.setText("出口加工费");
			jLabel25.setBounds(151, 51, 65, 20);
			jLabel25.setText("出口总件数");
			jLabel26.setBounds(307, 17, 50, 22);
			jLabel26.setText("进口毛重");
			jLabel27.setBounds(306, 51, 50, 21);
			jLabel27.setText("出口毛重");
			jLabel28.setBounds(490, 17, 50, 20);
			jLabel28.setText("进口净重");
			jLabel29.setBounds(489, 51, 55, 17);
			jLabel29.setText("出口净重 ");
			jLabel9.setBounds(20, 17, 50, 24);
			jLabel9.setText("进口总值");
			jPanel6.add(jLabel23, null);
			jPanel6.add(jLabel24, null);
			jPanel6.add(jLabel25, null);
			jPanel6.add(jLabel26, null);
			jPanel6.add(jLabel27, null);
			jPanel6.add(jLabel28, null);
			jPanel6.add(jLabel29, null);
			jPanel6.add(jLabel9, null);
			jPanel6.add(getTfImpTotalValue(), null);
			jPanel6.add(getTfExpTotalValue(), null);
			jPanel6.add(getTfExpManufactureMoney(), null);
			jPanel6.add(getTfExpTotalItems(), null);
			jPanel6.add(getTfImpTotalGrossWeight(), null);
			jPanel6.add(getTfImpNetWeight(), null);
			jPanel6.add(getTfExpTotalGrossWeight(), null);
			jPanel6.add(getTfExpNetWeight(), null);
		}
		return jPanel6;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfImpTotalValue() {
		if (tfImpTotalValue == null) {
			tfImpTotalValue = new JTextField();
			tfImpTotalValue.setBounds(71, 17, 76, 22);
			tfImpTotalValue.setEditable(false);
			tfImpTotalValue.setBackground(java.awt.Color.white);
		}
		return tfImpTotalValue;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExpTotalValue() {
		if (tfExpTotalValue == null) {
			tfExpTotalValue = new JTextField();
			tfExpTotalValue.setBounds(72, 50, 76, 22);
			tfExpTotalValue.setEditable(false);
			tfExpTotalValue.setBackground(java.awt.Color.white);
		}
		return tfExpTotalValue;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExpManufactureMoney() {
		if (tfExpManufactureMoney == null) {
			tfExpManufactureMoney = new JTextField();
			tfExpManufactureMoney.setBounds(221, 17, 79, 22);
			tfExpManufactureMoney.setEditable(false);
			tfExpManufactureMoney.setBackground(java.awt.Color.white);
		}
		return tfExpManufactureMoney;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExpTotalItems() {
		if (tfExpTotalItems == null) {
			tfExpTotalItems = new JTextField();
			tfExpTotalItems.setBounds(221, 50, 80, 22);
			tfExpTotalItems.setEditable(false);
			tfExpTotalItems.setBackground(java.awt.Color.white);
		}
		return tfExpTotalItems;
	}

	/**
	 * This method initializes jTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfImpTotalGrossWeight() {
		if (tfImpTotalGrossWeight == null) {
			tfImpTotalGrossWeight = new JTextField();
			tfImpTotalGrossWeight.setBounds(355, 17, 123, 22);
			tfImpTotalGrossWeight.setEditable(false);
			tfImpTotalGrossWeight.setBackground(java.awt.Color.white);
		}
		return tfImpTotalGrossWeight;
	}

	/**
	 * This method initializes jTextField5
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfImpNetWeight() {
		if (tfImpNetWeight == null) {
			tfImpNetWeight = new JTextField();
			tfImpNetWeight.setBounds(543, 17, 125, 22);
			tfImpNetWeight.setEditable(false);
			tfImpNetWeight.setBackground(java.awt.Color.white);
		}
		return tfImpNetWeight;
	}

	/**
	 * This method initializes jTextField6
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExpTotalGrossWeight() {
		if (tfExpTotalGrossWeight == null) {
			tfExpTotalGrossWeight = new JTextField();
			tfExpTotalGrossWeight.setBounds(355, 50, 123, 22);
			tfExpTotalGrossWeight.setEditable(false);
			tfExpTotalGrossWeight.setBackground(java.awt.Color.white);
		}
		return tfExpTotalGrossWeight;
	}

	/**
	 * This method initializes jTextField7
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExpNetWeight() {
		if (tfExpNetWeight == null) {
			tfExpNetWeight = new JTextField();
			tfExpNetWeight.setBounds(543, 50, 125, 22);
			tfExpNetWeight.setEditable(false);
			tfExpNetWeight.setBackground(java.awt.Color.white);
		}
		return tfExpNetWeight;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton5() {
		if (btnCal == null) {
			btnCal = new JButton();
			btnCal.setText("计算");
			btnCal.setBounds(new Rectangle(572, 143, 108, 24));
			btnCal.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DzscContractCav contractCav = null;
					if (rbSelf.isSelected()) {
						contractCav = dzscContractCavSelf;
					} else if (rbCustoms.isSelected()) {
						contractCav = dzscContractCavCustoms;
					}
					if (contractCav == null) {
						return;
					}
					DzscContractCavTotalValue totalValue = contractCavAction
							.findCavTotalValue(new Request(CommonVars
									.getCurrUser()), contractCav);
					tfImpTotalValue
							.setText(totalValue.getImpAmount() == null ? ""
									: CommonVars.formatDoubleToString(
											totalValue.getImpAmount(), 999, 3));
					tfExpTotalValue
							.setText(totalValue.getExpAmount() == null ? ""
									: CommonVars.formatDoubleToString(
											totalValue.getExpAmount(), 999, 3));
					tfExpManufactureMoney.setText(totalValue
							.getProcessTotalPrice() == null ? "" : CommonVars
							.formatDoubleToString(totalValue
									.getProcessTotalPrice(), 999, 3));
					tfExpTotalItems
							.setText(totalValue.getExpTotalPieces() == null ? ""
									: CommonVars.formatDoubleToString(
											totalValue.getExpTotalPieces(),
											999, 3));
					tfImpTotalGrossWeight.setText(totalValue
							.getImpTotalGrossWeight() == null ? "" : CommonVars
							.formatDoubleToString(totalValue
									.getImpTotalGrossWeight(), 999, 3));
					tfExpTotalGrossWeight.setText(totalValue
							.getExpTotalGrossWeight() == null ? "" : CommonVars
							.formatDoubleToString(totalValue
									.getExpTotalGrossWeight(), 999, 3));
					tfImpNetWeight
							.setText(totalValue.getImpTotalNetWeight() == null ? ""
									: CommonVars.formatDoubleToString(
											totalValue.getImpTotalNetWeight(),
											999, 3));
					tfExpNetWeight
							.setText(totalValue.getExpTotalNetWeight() == null ? ""
									: CommonVars.formatDoubleToString(
											totalValue.getExpTotalNetWeight(),
											999, 3));
				}
			});
		}
		return btnCal;
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	/**
	 * This method initializes numberFormatter
	 * 
	 * @return javax.swing.text.NumberFormatter
	 */
	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
		}
		return numberFormatter;
	}

	private void showData(DzscContractCav contractCav) {
		if (contractCav == null) {
			return;
		}
		NumberFormat form = NumberFormat.getInstance();
		form.setMaximumFractionDigits(3);
		this.tfEmsNo.setText(contractCav.getEmsNo());
		this.tfContractNo.setText(contractCav.getContractNo());
		this.tfCompanyName.setText(contractCav.getCompany() == null ? ""
				: contractCav.getCompany().getName());
		this.tfTelephone.setText(contractCav.getCompany() == null ? ""
				: ((Company) contractCav.getCompany()).getTel());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		this.tfEndDate.setText(contractCav.getEndDate() == null ? ""
				: dateFormat.format(contractCav.getEndDate()));
		this.tfEmsApprNo.setText(contractCav.getEmsApprNo());
		// this.tfImpAmount.setText(contractCav.getImpAmount() == null ? ""
		// : CommonVars.formatDoubleToString(contractCav.getImpAmount(),
		// 999, 3));
		this.tfImpAmount
				.setValue((contractCav.getImpAmount() == null ? new Double(0)
						: contractCav.getImpAmount()));
		// test
		System.out.println("界面：" + this.tfImpAmount.getValue().toString());
		// if (contractCav.getExpAmount() == null) {
		// this.tfExpAmount.setText("");
		// } else {
		// this.tfExpAmount.setText(CommonVars.formatDoubleToString(
		// contractCav.getExpAmount(), 999, 3));
		// }
		this.tfExpAmount
				.setValue(contractCav.getExpAmount() == null ? new Double(0)
						: contractCav.getExpAmount());
		// this.tfExpAmount.setText(contractCav.getExpAmount() == null ? ""
		// : contractCav.getExpAmount().toString());
		// this.tfProcessTotalPrice
		// .setText(contractCav.getProcessTotalPrice() == null ? ""
		// : contractCav.getProcessTotalPrice().toString());
		this.tfProcessTotalPrice
				.setValue(contractCav.getProcessTotalPrice() == null ? new Double(
						0)
						: contractCav.getProcessTotalPrice());
		this.lbCurrency.setText(contractCav.getCurr() == null ? ""
				: contractCav.getCurr().getName());
		this.tfImpCDCount.setText(contractCav.getImpCDCount() == null ? ""
				: contractCav.getImpCDCount().toString());
		this.tfExpCDCount.setText(contractCav.getExpCDCount() == null ? ""
				: contractCav.getExpCDCount().toString());
		this.tfInternalSale
				.setValue(contractCav.getInternalSale() == null ? new Double(0)
						: contractCav.getInternalSale());
		// this.tfInternalSale.setText(contractCav.getInternalSale() == null ?
		// ""
		// : contractCav.getInternalSale().toString());
		this.tfInternalSaleTax
				.setValue(contractCav.getInternalSaleTax() == null ? new Double(
						0)
						: contractCav.getInternalSaleTax());
		// this.tfInternalSaleTax
		// .setText(contractCav.getInternalSaleTax() == null ? ""
		// : contractCav.getInternalSaleTax().toString());
		this.tfInternalSaleWarrant
				.setText(contractCav.getInternalSaleWarrant());
		this.tfInternalSaleTaxBill
				.setText(contractCav.getInternalSaleTaxBill());
		BigDecimal remainMoney = new BigDecimal(
				contractCav.getRemainMoney() == null ? 0d : contractCav
						.getRemainMoney());
		DecimalFormat df = new DecimalFormat();
		df.setMaximumIntegerDigits(3);
		this.tfRemainMoney.setValue(contractCav.getRemainMoney() == null ? 0d
				: contractCav.getRemainMoney());
		// this.tfRemainMoney.setText(df.format(remainMoney));
		this.tfRemainEmsNo.setText(contractCav.getRemainEmsNo());
		this.tfCautionMoney.setText(contractCav.getCautionMoney() == null ? ""
				: contractCav.getCautionMoney().toString());
		if (contractCav.getIsSusceptivity() == null) {
			this.cbbIsSusceptivity.setSelectedIndex(-1);
		} else {
			this.cbbIsSusceptivity.setSelectedIndex(ItemProperty
					.getIndexByCode(contractCav.getIsSusceptivity().toString(),
							this.cbbIsSusceptivity));
		}
		if (contractCav.getIsLimit() == null) {
			this.cbbIsLimit.setSelectedIndex(-1);
		} else {
			this.cbbIsLimit.setSelectedIndex(ItemProperty.getIndexByCode(
					contractCav.getIsLimit().toString(), this.cbbIsLimit));
		}
		this.tfTotalPages
				.setValue(contractCav.getTotalPages() == null ? new Integer(0)
						: contractCav.getTotalPages());
		// this.tfTotalPages.setText(contractCav.getTotalPages() == null ? ""
		// : contractCav.getTotalPages().toString());
		//		
		this.tfCurrency.setText(contractCav.getCurr() == null ? ""
				: contractCav.getCurr().getCode());
	}

	private void fillData(DzscContractCav contractCav) {
		if (contractCav == null) {
			return;
		}
		contractCav
				.setInternalSale(this.tfInternalSale.getValue() == null ? new Double(
						0)
						: Double.valueOf(this.tfInternalSale.getValue()
								.toString()));
		contractCav
				.setInternalSaleTax(this.tfInternalSaleTax.getValue() == null ? new Double(
						0)
						: Double.valueOf(this.tfInternalSaleTax.getValue()
								.toString()));
		contractCav
				.setInternalSaleWarrant(this.tfInternalSaleWarrant.getText());
		contractCav
				.setInternalSaleTaxBill(this.tfInternalSaleTaxBill.getText());
		contractCav
				.setTotalPages(this.tfTotalPages.getValue() == null ? new Integer(
						0)
						: Integer.valueOf(this.tfTotalPages.getValue()
								.toString()));
		if (this.cbbIsLimit.getSelectedIndex() > -1) {
			contractCav.setIsLimit(Boolean
					.valueOf(((ItemProperty) this.cbbIsLimit.getSelectedItem())
							.getCode()));
		} else {
			contractCav.setIsLimit(null);
		}
		if (this.cbbIsSusceptivity.getSelectedIndex() > -1) {
			contractCav.setIsSusceptivity(Boolean
					.valueOf(((ItemProperty) this.cbbIsSusceptivity
							.getSelectedItem()).getCode()));
		} else {
			contractCav.setIsSusceptivity(null);
		}
		contractCav
				.setProcessTotalPrice(Double
						.parseDouble(getTfProcessTotalPrice().getValue() == null ? "0.0"
								: getTfProcessTotalPrice().getValue()
										.toString()));
		contractCav.setRemainMoney(Double.parseDouble(getTfRemainMoney()
				.getValue() == null ? "0.0" : getTfRemainMoney().getValue()
				.toString()));
		contractCav.setCautionMoney(Double.parseDouble(getTfCautionMoney()
				.getValue() == null ? "0.0" : getTfCautionMoney().getValue()
				.toString()));
		contractCav.setRemainEmsNo(getTfRemainEmsNo().getText());
		contractCav.setImpAmount(Double
				.parseDouble(getTfImpAmount().getValue() == null ? "0.0"
						: getTfImpAmount().getValue().toString()));
		contractCav.setExpAmount(Double
				.parseDouble(getTfExpAmount().getValue() == null ? "0.0"
						: getTfExpAmount().getValue().toString()));
	}

	private void setState() {
		String stateMark = (dzscContractCavCustoms == null ? ""
				: ((dzscContractCavCustoms.getStateMark() == null || ""
						.equals(dzscContractCavCustoms.getStateMark().trim())) ? DzscState.ORIGINAL
						: dzscContractCavCustoms.getStateMark()));
		boolean isCustoms = rbCustoms.isSelected();
		this.rbSelf.setEnabled(dataState == DataState.BROWSE);
		this.rbCustoms.setEnabled(dataState == DataState.BROWSE);
		this.btnPrint.setEnabled(dataState == DataState.BROWSE);
		this.btnClose.setEnabled(dataState == DataState.BROWSE);

		this.btnEdit.setEnabled(dataState == DataState.BROWSE
				&& DzscState.ORIGINAL.equals(stateMark));
		this.btnSave.setEnabled(dataState != DataState.BROWSE
				&& DzscState.ORIGINAL.equals(stateMark));
		this.btnUndo.setEnabled(dataState != DataState.BROWSE
				&& DzscState.ORIGINAL.equals(stateMark));
		this.btnCheck.setEnabled(dataState == DataState.BROWSE);
		this.jTabbedPane.setEnabled(dataState == DataState.BROWSE);
		this.tfInternalSale.setEnabled(dataState != DataState.BROWSE);
		this.tfInternalSaleTax.setEnabled(dataState != DataState.BROWSE);

		this.tfRemainMoney.setEnabled(dataState != DataState.BROWSE);
		this.tfRemainEmsNo.setEnabled(dataState != DataState.BROWSE);
		this.tfCautionMoney.setEnabled(dataState != DataState.BROWSE);
		this.tfProcessTotalPrice.setEnabled(dataState != DataState.BROWSE);
		this.tfInternalSaleWarrant.setEnabled(dataState != DataState.BROWSE);
		this.tfInternalSaleTaxBill.setEnabled(dataState != DataState.BROWSE);
		this.tfTotalPages.setEnabled(dataState != DataState.BROWSE);
		this.cbbIsLimit.setEnabled(dataState != DataState.BROWSE);
		this.cbbIsLimit.setBackground(java.awt.Color.white);
		this.cbbIsLimit.setForeground(java.awt.Color.BLACK);
		this.cbbIsSusceptivity.setEnabled(dataState != DataState.BROWSE);
		this.cbbIsSusceptivity.setBackground(java.awt.Color.white);
		this.cbbIsSusceptivity.setForeground(java.awt.Color.BLACK);

		this.btnEdit.setVisible(isCustoms);
		this.btnSave.setVisible(isCustoms);
		this.btnUndo.setVisible(isCustoms);
		this.btnCheck.setVisible(isCustoms);
		this.btnInteralBuy.setVisible(isCustoms);
		this.btnDelete.setVisible(isCustoms);
		this.btnLeftoverMaterial.setVisible(isCustoms);
		this.cbImg.setVisible(isCustoms);
		this.rbTotal.setVisible(isCustoms);
		this.rbWaste.setVisible(isCustoms);
		this.btnRoundAmount.setVisible(isCustoms);
		this.btnChangeImgModifyMark.setVisible(isCustoms);
		this.cbBom1.setVisible(isCustoms);
		this.cbBom2.setVisible(isCustoms);
		this.cbBom3.setVisible(isCustoms);
		// this.btnCal.setVisible(isCustoms);
		this.btnExgEdit.setVisible(isCustoms);
		this.btnImgEdit.setVisible(isCustoms);
		this.btnUnitWasteEdit.setVisible(isCustoms);
		this.jTabbedPane.setTitleAt(0, isCustoms ? "海关核销表表头" : "自用核销表表头");
		this.jTabbedPane.setTitleAt(1, isCustoms ? "海关核销报关单" : "自用核销报关单");
		this.jTabbedPane.setTitleAt(2, isCustoms ? "海关核销成品表" : "自用核销成品表");
		this.jTabbedPane.setTitleAt(3, isCustoms ? "海关核销料件表" : "自用核销料件表");
		this.jTabbedPane.setTitleAt(4, isCustoms ? "海关核销单耗表" : "自用核销单耗表");
		this.btnReCal.setText(isCustoms ? "重新获取海关核销表" : "重新计算自用核销表");

		this.btnReCal.setEnabled(DzscState.ORIGINAL.equals(stateMark));
		this.btnDeclare.setEnabled(isCustoms
				&& DzscState.ORIGINAL.equals(stateMark));
		this.btnProccess.setEnabled(isCustoms
				&& DzscState.APPLY.equals(stateMark));

		this.btnExgEdit.setEnabled(DzscState.ORIGINAL.equals(stateMark));
		this.btnImgEdit.setEnabled(DzscState.ORIGINAL.equals(stateMark));
		this.btnUnitWasteEdit.setEnabled(DzscState.ORIGINAL.equals(stateMark));
		this.btnInteralBuy.setEnabled(DzscState.ORIGINAL.equals(stateMark));
		this.btnDelete.setEnabled(DzscState.ORIGINAL.equals(stateMark));
		this.btnLeftoverMaterial.setEnabled(DzscState.ORIGINAL
				.equals(stateMark));
		this.cbImg.setEnabled(DzscState.ORIGINAL.equals(stateMark));

		this.cbImg.setEnabled(DzscState.ORIGINAL.equals(stateMark));

		this.rbTotal.setEnabled(DzscState.ORIGINAL.equals(stateMark));
		this.rbWaste.setEnabled(DzscState.ORIGINAL.equals(stateMark));
		this.btnRoundAmount.setEnabled(DzscState.ORIGINAL.equals(stateMark));
		this.btnChangeImgModifyMark.setEnabled(DzscState.ORIGINAL
				.equals(stateMark));
		this.cbBom1.setEnabled(DzscState.ORIGINAL.equals(stateMark));
		this.cbBom2.setEnabled(DzscState.ORIGINAL.equals(stateMark));
		this.cbBom3.setEnabled(DzscState.ORIGINAL.equals(stateMark));
		getTfImpAmount().setEditable(dataState != DataState.BROWSE);
		getTfExpAmount().setEditable(dataState != DataState.BROWSE);
	} //

	// private void switchSelfCustoms(boolean isCustoms) {
	//		
	// }

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.setBounds(new java.awt.Rectangle(178, 399, 71, 25));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.EDIT;
					setState();
					// test
					System.out.println("修改进口总金额："
							+ dzscContractCavCustoms.getImpAmount());
				}
			});
		}
		return btnEdit;
	}

	private void InitUIComponents() {
		this.cbbIsSusceptivity.removeAllItems();
		this.cbbIsSusceptivity.addItem(new ItemProperty("true", "是"));
		this.cbbIsSusceptivity.addItem(new ItemProperty("false", "否"));

		this.cbbIsLimit.removeAllItems();
		this.cbbIsLimit.addItem(new ItemProperty("true", "是"));
		this.cbbIsLimit.addItem(new ItemProperty("false", "否"));

		// 初始化打印选项
		// this.cbbPrint.removeAllItems();
		// this.cbbPrint.addItem("非套打核销表头");
		// this.cbbPrint.addItem(" 套打核销表头");
		// this.cbbPrint.addItem("非套打核销料件表");
		// this.cbbPrint.addItem(" 套打核销料件表");
		// this.cbbPrint.addItem("非套打核销成品表");
		// this.cbbPrint.addItem(" 套打核销成品表");
		// this.cbbPrint.addItem("非套打核销单耗表");
		// this.cbbPrint.addItem(" 套打核销单耗表");
		// this.cbbPrint.setUI(new CustomBaseComboBoxUI(300));
	}

	private void showCustomsDeclaration(boolean isCustoms) {
		List list = new ArrayList();
		if (isCustoms) {
			if (dzscContractCavCustoms != null) {
				list = contractCavAction.findCustomsDeclarationCav(new Request(
						CommonVars.getCurrUser()), dzscContractCavCustoms);
			}
		} else {
			if (dzscContractCavSelf != null) {
				list = contractCavAction.findCustomsDeclarationCav(new Request(
						CommonVars.getCurrUser()), dzscContractCavSelf);
			}
		}

		tableModelCD = new JTableListModel(this.tbCD, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("报关单号", "customsDeclarationCode",
								150));
						list.add(addColumn("申报地海关", "declarationCustoms.name",
								100));
						list.add(addColumn("报关单类型", "impExpFlag", 100));
						list.add(addColumn("进出口标志", "customsImpExpType", 100));
						list.add(addColumn("申报日期", "declarationDate", 100));
						list.add(addColumn("进出口日期", "impExpDate", 100));
						list.add(addColumn("核扣方式", "deduc.name", 100));
						return list;
					}
				});
		tbCD.getColumnModel().getColumn(3).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							switch (Integer.parseInt(value.toString())) {
							case ImpExpFlag.IMPORT:
								str = "进口报关单";
								break;
							case ImpExpFlag.EXPORT:
								str = "出口报关单";
								break;
							case ImpExpFlag.SPECIAL:
								str = "特殊报关单";
								break;
							}
						}
						this.setText(str);
						return this;
					}
				});
	}

	class find extends Thread {
		public void run() {
			List list = new ArrayList();
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正获取数据，请稍后...");
				if (dzscContractCavCustoms != null) {
					list = contractCavAction.findContractExgCav(new Request(
							CommonVars.getCurrUser()), dzscContractCavCustoms);
				}
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgDzscContractCav.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				// tableModelExg = new JTableListModel(
				// DgDzscContractCav.this.tbExg, list,
				// new JTableListModelAdapter() {
				// public List InitColumns() {
				// List<JTableListColumn> list = new Vector<JTableListColumn>();
				// list.add(addColumn("商品名称", "name", 100));
				// list.add(addColumn("规格型号", "spec", 100));
				// list.add(addColumn("计量单位", "unit.name", 100));
				// list.add(addColumn("出口总数", "expTotal", 100));
				// list.add(addColumn("结转出口数量",
				// "transferExpAmount", 100));
				// list.add(addColumn("退厂返工", "backFactoryRework",
				// 100));
				// list
				// .add(addColumn("返工复出", "reworkExport",
				// 100));
				// return list;
				// }
				// });
				showExgData(true);
			}
		}
	}

	private void showExgData(boolean isCustoms) {
		List list = new ArrayList();
		if (isCustoms) {
			if (dzscContractCavCustoms != null) {
				list = contractCavAction.findContractExgCav(new Request(
						CommonVars.getCurrUser()), dzscContractCavCustoms);
			}
		} else {
			if (dzscContractCavSelf != null) {
				list = contractCavAction.findContractExgCav(new Request(
						CommonVars.getCurrUser()), dzscContractCavSelf);
			}
		}
		tableModelExg = new JTableListModel(this.tbExg, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("备案序号", "seqNum", 100));
						list.add(addColumn("商品名称", "name", 100));
						list.add(addColumn("规格型号", "spec", 100));
						list.add(addColumn("计量单位", "unit.name", 100));
						list.add(addColumn("出口总数", "expTotal", 100));
						list.add(addColumn("直接出口数量", "directExport", 100));
						list.add(addColumn("结转出口数量", "transferExpAmount", 100));
						list.add(addColumn("退厂返工", "backFactoryRework", 100));
						list.add(addColumn("返工复出", "reworkExport", 100));
						list.add(addColumn("库存数量", "stockAmount", 100));
						return list;
					}
				});
		// 显示小数位,默认2位
		// Integer decimalLength = 2;
		// if (dzscParameterSet != null
		// && dzscParameterSet.getReportDecimalLength() != null)
		// decimalLength = dzscParameterSet.getReportDecimalLength();
		// tableModelExg.setAllColumnsFractionCount(decimalLength);

		// List<JTableListColumn> tbs = tableModelExg.getColumns();
		// for (int i = 0; i < tbs.size(); i++) {
		// JTableListColumn bb = tbs.get(i);
		// bb.setFractionCount(4);
		// }
	}

	private void showImgData(boolean isCustoms) {
		// TODO "mastkcb"
		List list = new ArrayList();
		if (isCustoms) {
			if (dzscContractCavCustoms != null) {
				list = contractCavAction.findContractImgCav(new Request(
						CommonVars.getCurrUser()), dzscContractCavCustoms);
			}
		} else {
			if (dzscContractCavSelf != null) {
				list = contractCavAction.findContractImgCav(new Request(
						CommonVars.getCurrUser()), dzscContractCavSelf);
			}
		}
		tableModelImg = new JTableListModel(this.tbImg, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("备案序号", "seqNum", 100));
						list.add(addColumn("商品名称", "name", 150));
						list.add(addColumn("规格型号", "spec", 100));
						list.add(addColumn("计量单位", "unit.name", 100));
						list.add(addColumn("进口总数", "impTotal", 100));
						list.add(addColumn("料件直接进口", "directImport", 100));
						list
								.add(addColumn("料件转厂", "transferFactoryImport",
										100));
						list.add(addColumn("余料结转进口", "remainImport", 100));
						list.add(addColumn("余料结转出口", "remainExport", 100));
						list.add(addColumn("成品耗用", "productWaste", 100));
						list.add(addColumn("内销数量", "internalAmount", 100));
						list.add(addColumn("退运出口", "backExport", 100));
						list.add(addColumn("边角料", "leftoverMaterial", 100));
						list.add(addColumn("余料", "remainMaterial", 100));
						list.add(addColumn("库存数量", "stockAmount", 100));
						return list;
					}
				});
		// // 显示小数位,默认2位
		// Integer decimalLength = 2;
		// if (dzscParameterSet != null
		// && dzscParameterSet.getReportDecimalLength() != null)
		// decimalLength = dzscParameterSet.getReportDecimalLength();
		// System.out.println("小数位：="+decimalLength);
		// tableModelImg.setAllColumnsFractionCount(decimalLength);
		// List<JTableListColumn> tbs = tableModelImg.getColumns();
		// for (int i = 6; i < tbs.size(); i++) {
		// JTableListColumn bb = tbs.get(i);
		// bb.setFractionCount(4);
		// }
	}

	private void showBomData(boolean isCustoms) {
		List list = new ArrayList();
		if (isCustoms) {
			if (dzscContractCavCustoms != null) {
				list = contractCavAction.findContractBomCav(new Request(
						CommonVars.getCurrUser()), dzscContractCavCustoms);
			}
		} else {
			if (dzscContractCavSelf != null) {
				list = contractCavAction.findContractBomCav(new Request(
						CommonVars.getCurrUser()), dzscContractCavSelf);
			}
		}

		JTableListModelAdapter jt = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list
						.add(addColumn("成品序号", "seqProductNum", 100,
								Integer.class));
				list.add(addColumn("成品名称", "productName", 100));
				list.add(addColumn("成品规格", "productSpec", 100));
				list
						.add(addColumn("料件序号", "seqMaterialNum", 100,
								Integer.class));
				list.add(addColumn("料件名称", "materialName", 100));
				list.add(addColumn("料件规格", "materialSpec", 100));
				list.add(addColumn("单耗", "unitWaste", 100));
				list.add(addColumn("损耗量", "wasteAmount", 100));
				list.add(addColumn("总用量", "totalAmount", 100));
				list.add(addColumn("修改标志", "modifyMark", 100));
				return list;
			}
		};

		jt.setEditableColumn(7);
		jt.setEditableColumn(8);
		jt.setEditableColumn(9);
		tableModelBom = new AttributiveCellTableModel(
				(MultiSpanCellTable) this.tblBom, list, jt);
		// // 显示小数位,默认2位
		// Integer decimalLength = 2;
		// if (dzscParameterSet != null
		// && dzscParameterSet.getReportDecimalLength() != null)
		// decimalLength = dzscParameterSet.getReportDecimalLength();
		// tableModelBom.setAllColumnsFractionCount(decimalLength);

		tableModelBom.setAllowSetValue(true);
		tblBom.getColumnModel().getColumn(7).setCellEditor(
				new JTextFieldEditor(1));
		// tblBom.getColumnModel().getColumn(7).setCellRenderer(
		// new ForcedEditTableCellRenderer());
		tblBom.getColumnModel().getColumn(8).setCellEditor(
				new JTextFieldEditor(2));
		// tblBom.getColumnModel().getColumn(7).setCellRenderer(
		// new ForcedEditTableCellRenderer());
		tblBom.getColumnModel().getColumn(9).setCellEditor(
				new JTextFieldEditor(3));
		// tblBom.getColumnModel().getColumn(7).setCellRenderer(
		// new ForcedEditTableCellRenderer());

		// List<JTableListColumn> tbs = tableModelBom.getColumns();
		// for (int i = 0; i < tbs.size(); i++) {
		// JTableListColumn bb = tbs.get(i);
		// if (i == 7) {
		// bb.setFractionCount(5);
		// } else {
		// bb.setFractionCount(4);
		// }
		//
		// }
		this.tblBom.getColumnModel().getColumn(10).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String state = "";
						if (value != null) {
							state = value.toString();
						}
						if (state.equals(ModifyMarkState.UNCHANGE)) {
							this.setText("未修改");
						} else if (state.equals(ModifyMarkState.ADDED)) {
							this.setText("新增");
						}
						if (state.equals(ModifyMarkState.MODIFIED)) {
							this.setText("已修改");
						}
						if (state.equals(ModifyMarkState.DELETED)) {
							this.setText("已删除");
						}
						return this;
					}
				});
		refreshTable();
	}

	/**
	 * 
	 */
	private void refreshTable() {
		// if (cbCombineRows.isSelected()) {
		((MultiSpanCellTable) tblBom).combineRows(new int[] { 1, 4 },
				new int[] { 1, 2, 3 });
		// ((MultiSpanCellTable) tblBom).combineRows(7, new int[] { 7, 8, 9,
		// 10, 11 });
		// } else {
		// ((MultiSpanCellTable) jTable).splitRows(new int[] { 12, 7 });
		// }
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnReCal() {
		if (btnReCal == null) {
			btnReCal = new JButton();
			btnReCal.setText("重新计算");
			btnReCal.setBounds(new java.awt.Rectangle(492, 397, 151, 25));
			btnReCal.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new Thread() {
						public void run() {

							if (rbSelf.isSelected()) {
								if (JOptionPane.showConfirmDialog(
										DgDzscContractCav.this,
										"你确定想重新计算自用核销表吗?", "提示",
										JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
									String taskId = CommonStepProgress
											.getExeTaskId();
									CommonStepProgress
											.showStepProgressDialog(taskId);
									CommonStepProgress
											.setStepMessage("系统正在计算数据，请稍候...");
									Request request = new Request(CommonVars
											.getCurrUser());
									request.setTaskId(taskId);
									contractCavAction.reMakeSelfuseContractCav(
											request, contract.getEmsNo());
									dzscContractCavSelf = (DzscContractCav) contractCavAction
											.findContractCav(
													new Request(CommonVars
															.getCurrUser()),
													contract.getEmsNo(), false)
											.get(0);
									showData(dzscContractCavSelf);
									CommonStepProgress
											.closeStepProgressDialog();
								}
							} else if (rbCustoms.isSelected()) {
								if (JOptionPane.showConfirmDialog(
										DgDzscContractCav.this,
										"你确定想重新获取海关核销表吗?", "提示",
										JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
									String taskId = CommonStepProgress
											.getExeTaskId();
									CommonStepProgress
											.showStepProgressDialog(taskId);
									CommonStepProgress
											.setStepMessage("系统正在计算数据，请稍候...");
									Request request = new Request(CommonVars
											.getCurrUser());
									request.setTaskId(taskId);
									contractCavAction.reGetCustomsContractCav(
											request, contract.getEmsNo());
									dzscContractCavCustoms = (DzscContractCav) contractCavAction
											.findContractCav(
													new Request(CommonVars
															.getCurrUser()),
													contract.getEmsNo(), true)
											.get(0);
									showData(dzscContractCavCustoms);
									CommonStepProgress
											.closeStepProgressDialog();
								}
							}
						}
					}.start();

				}
			});
		}
		return btnReCal;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnCustomsDeclaration() {
		if (pnCustomsDeclaration == null) {
			pnCustomsDeclaration = new JPanel();
			pnCustomsDeclaration.setLayout(new BorderLayout());
			pnCustomsDeclaration.setSize(new java.awt.Dimension(8, 10));
			pnCustomsDeclaration.add(getJScrollPane3(),
					java.awt.BorderLayout.CENTER);
		}
		return pnCustomsDeclaration;
	}

	/**
	 * This method initializes jScrollPane3
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getTbCD());
		}
		return jScrollPane3;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbCD() {
		if (tbCD == null) {
			tbCD = new JTable();
		}
		return tbCD;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.rbCustoms);
			buttonGroup.add(this.rbSelf);
		}
		return buttonGroup;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExgEdit() {
		if (btnExgEdit == null) {
			btnExgEdit = new JButton();
			btnExgEdit.setText("修改");
			btnExgEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelExg.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgDzscContractCav.this,
								"请选择要修改的成品", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					DgDzscContractCavExg dg = new DgDzscContractCavExg();
					dg.setTableModel(tableModelExg);
					dg.setDataState(DataState.EDIT);
					dg.setVisible(true);
				}
			});

		}
		return btnExgEdit;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImgEdit() {
		if (btnImgEdit == null) {
			btnImgEdit = new JButton();
			btnImgEdit.setText("修改");
			btnImgEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelImg.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgDzscContractCav.this,
								"请选择要修改的料件", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					DgDzscContractCavImg dg = new DgDzscContractCavImg();
					dg.setTableModel(tableModelImg);
					dg.setDataState(DataState.EDIT);
					dg.setModifyProductUsedAmountWriteBack(!cbImg.isSelected());
					dg.setVisible(true);
				}
			});
		}
		return btnImgEdit;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUnitWasteEdit() {
		if (btnUnitWasteEdit == null) {
			btnUnitWasteEdit = new JButton();
			btnUnitWasteEdit.setText("保存");
			btnUnitWasteEdit
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							tblBom.getColumnModel().getColumn(7)
									.getCellEditor().stopCellEditing();
							tblBom.getColumnModel().getColumn(8)
									.getCellEditor().stopCellEditing();
							tblBom.getColumnModel().getColumn(9)
									.getCellEditor().stopCellEditing();

							new Thread() {
								public void run() {
									CommonProgress.showProgressDialog();
									CommonProgress.setMessage("系统正在保存数据，请稍候！");
									try {
										contractCavAction.saveContractBomCavs(
												new Request(CommonVars
														.getCurrUser()),
												tableModelBom.getList());
									} catch (Exception ex) {
										CommonProgress.closeProgressDialog();
										System.out.println(ex.getMessage());
										ex.printStackTrace();
										JOptionPane.showMessageDialog(
												DgDzscContractCav.this,
												"数据保存失败！", "提示！",
												JOptionPane.ERROR_MESSAGE);
									}
									CommonProgress.closeProgressDialog();
									JOptionPane.showMessageDialog(
											DgDzscContractCav.this, "数据保存成功！",
											"提示！",
											JOptionPane.INFORMATION_MESSAGE);
								}
							}.start();

						}
					});
		}
		return btnUnitWasteEdit;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeclare() {
		if (btnDeclare == null) {
			btnDeclare = new JButton();
			btnDeclare.setText("海关申报");
			btnDeclare.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(DgDzscContractCav.this,
							"你真的要申报此核销表吗？", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					// try {
					// String fileName = contractCavAction
					// .applyDzscContractCav(new Request(CommonVars
					// .getCurrUser()), dzscContractCavCustoms);
					// JOptionPane.showMessageDialog(DgDzscContractCav.this,
					// "申报成功，文件" + fileName, "提示",
					// JOptionPane.INFORMATION_MESSAGE);
					// dzscContractCavCustoms = contractCavAction
					// .findDzscContractCavById(new Request(CommonVars
					// .getCurrUser()), dzscContractCavCustoms
					// .getId());
					// setState();
					// } catch (Exception ex) {
					// JOptionPane.showMessageDialog(DgDzscContractCav.this,
					// "申报失败 " + ex.getMessage(), "提示",
					// JOptionPane.INFORMATION_MESSAGE);
					// }
					new ApplyThread().start();
				}
			});
		}
		return btnDeclare;
	}

	class ApplyThread extends Thread {
		public void run() {
			try {
				String taskId = CommonStepProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正获取数据，请稍后...");
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);

				try {
					DeclareFileInfo fileInfo = contractCavAction
							.applyDzscContractCav(new Request(CommonVars
									.getCurrUser()), dzscContractCavCustoms);
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(DgDzscContractCav.this,
							fileInfo.getFileInfoSpec(), "提示",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception ex) {
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(DgDzscContractCav.this,
							"系统申报失败 " + ex.getMessage(), "确认",
							JOptionPane.INFORMATION_MESSAGE);
				}
				dzscContractCavCustoms = contractCavAction
						.findDzscContractCavById(new Request(CommonVars
								.getCurrUser()), dzscContractCavCustoms.getId());
				setState();
			} catch (Exception ex) {
				System.out.println(ex.getStackTrace() + "\n-->"
						+ ex.getMessage());
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}
		}
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnProccess() {
		if (btnProccess == null) {
			btnProccess = new JButton();
			btnProccess.setText("处理回执");
			btnProccess.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// if (JOptionPane.showConfirmDialog(DgDzscContractCav.this,
					// "你真的要申报此核销表吗？", "提示", JOptionPane.YES_NO_OPTION) !=
					// JOptionPane.YES_OPTION) {
					// return;
					// }
					List lsReturnFile = DzscCommon.getInstance()
							.showDzscReceiptFile(
									DzscBusinessType.CANCEL_AFTER_VERIFICA,
									dzscContractCavCustoms.getCopEmsNo());
					if (lsReturnFile.size() <= 0) {
						return;
					}
					try {
						String result = contractCavAction
								.processDzscContractCav(new Request(CommonVars
										.getCurrUser()),
										dzscContractCavCustoms, lsReturnFile);
						JOptionPane.showMessageDialog(DgDzscContractCav.this,
								"处理回执成功\n" + result, "提示",
								JOptionPane.INFORMATION_MESSAGE);
						dzscContractCavCustoms = contractCavAction
								.findDzscContractCavById(new Request(CommonVars
										.getCurrUser()), dzscContractCavCustoms
										.getId());
						setState();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(DgDzscContractCav.this,
								"处理回执失败 " + ex.getMessage(), "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		return btnProccess;
	}

	private void editerUnitwast(DzscContractBomCav bom, Double unitWast) {
		if (bom == null || getCbBom1().isSelected()) {
			return;
		}
		unitWast = (unitWast == null ? 0.0 : unitWast);
		Double wast = (bom.getWaste() == null ? 0.0 : bom.getWaste()) / 100;
		Double proCount = bom.getExgExpTotalAmount() == null ? 0.0 : bom
				.getExgExpTotalAmount();
		if (((1 - wast) * proCount) != 0) {
			Double totalAmount = unitWast / (1 - wast) * proCount;
			Double wastAmount = totalAmount * wast;
			bom.setWasteAmount(wastAmount);
			bom.setTotalAmount(totalAmount);
		}
	}

	private void editerWastAmount(DzscContractBomCav bom, Double wastAmount) {
		if (bom == null || getCbBom3().isSelected()) {
			return;
		}
		wastAmount = (wastAmount == null ? 0.0 : wastAmount);
		Double totalAmount = bom.getTotalAmount() == null ? 0.0 : bom
				.getTotalAmount();
		Double proCount = bom.getExgExpTotalAmount() == null ? 0.0 : bom
				.getExgExpTotalAmount();
		Double unitWast = bom.getUnitWaste() == null ? 0.0 : bom.getUnitWaste();
		if (totalAmount != 0) {
			Double wast = wastAmount / totalAmount;
			if (((1 - wast) * proCount) != 0) {
				Double totalAmount1 = unitWast / (1 - wast) * proCount;
				bom.setTotalAmount(totalAmount1);
				bom.setWaste(wast);
			}
		}
	}

	private void editerTotalAmount(DzscContractBomCav bom, Double totalAmount) {
		if (bom == null || getCbBom2().isSelected()) {
			return;
		}
		totalAmount = (totalAmount == null ? 0.0 : totalAmount);
		Double proCount = bom.getExgExpTotalAmount() == null ? 0.0 : bom
				.getExgExpTotalAmount();
		Double wast = (bom.getWaste() == null ? 0.0 : bom.getWaste()) / 100;
		Double wasteAmount = totalAmount * wast;
		if ((proCount * (1 - wast)) != 0) {
			Double unitWast = totalAmount / proCount * (1 - wast);
			bom.setUnitWaste(unitWast);
			bom.setWasteAmount(wasteAmount);
		}
	}

	/**
	 * 编辑列
	 */
	class JTextFieldEditor extends DefaultCellEditor {
		private DzscContractBomCav info = null;

		private int editeType = 0;

		public JTextFieldEditor(int editeType) {
			super(new JTextField());
			this.editeType = editeType;
			((JTextField) editorComponent).addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					if (JTextFieldEditor.this.editeType == 1) {
						editerUnitwast(info, (Double) JTextFieldEditor.this
								.getCellEditorValue());
					} else if (JTextFieldEditor.this.editeType == 2) {
						editerWastAmount(info, (Double) JTextFieldEditor.this
								.getCellEditorValue());
					} else if (JTextFieldEditor.this.editeType == 3) {
						editerTotalAmount(info, (Double) JTextFieldEditor.this
								.getCellEditorValue());
					}
				}
			});
			((JTextField) editorComponent).getDocument().addDocumentListener(
					new DocumentListener() {
						public void insertUpdate(DocumentEvent e) {
							if (JTextFieldEditor.this.editeType == 1) {
								editerUnitwast(info,
										(Double) JTextFieldEditor.this
												.getCellEditorValue());
							} else if (JTextFieldEditor.this.editeType == 2) {
								editerWastAmount(info,
										(Double) JTextFieldEditor.this
												.getCellEditorValue());
							} else if (JTextFieldEditor.this.editeType == 3) {
								editerTotalAmount(info,
										(Double) JTextFieldEditor.this
												.getCellEditorValue());
							}

						}

						public void removeUpdate(DocumentEvent e) {
							if (JTextFieldEditor.this.editeType == 1) {
								editerUnitwast(info,
										(Double) JTextFieldEditor.this
												.getCellEditorValue());
							} else if (JTextFieldEditor.this.editeType == 2) {
								editerWastAmount(info,
										(Double) JTextFieldEditor.this
												.getCellEditorValue());
							} else if (JTextFieldEditor.this.editeType == 3) {
								editerTotalAmount(info,
										(Double) JTextFieldEditor.this
												.getCellEditorValue());
							}

						}

						public void changedUpdate(DocumentEvent e) {
							if (JTextFieldEditor.this.editeType == 1) {
								editerUnitwast(info,
										(Double) JTextFieldEditor.this
												.getCellEditorValue());
							} else if (JTextFieldEditor.this.editeType == 2) {
								editerWastAmount(info,
										(Double) JTextFieldEditor.this
												.getCellEditorValue());
							} else if (JTextFieldEditor.this.editeType == 3) {
								editerTotalAmount(info,
										(Double) JTextFieldEditor.this
												.getCellEditorValue());
							}

						}

					});
			this.setClickCountToStart(1);
		}

		@Override
		/* Implements the <code>TableCellEditor</code> interface. */
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			info = null;
			delegate.setValue(value);
			info = (DzscContractBomCav) ((JTableListModel) table.getModel())
					.getDataByRow(row);
			((JTextField) editorComponent).setEditable(getRbCustoms()
					.isSelected());
			return editorComponent;
		}

		@Override
		public Object getCellEditorValue() {
			String str = delegate.getCellEditorValue() == null ? "0.0"
					: delegate.getCellEditorValue().toString();
			try {
				Double.valueOf(str.trim().equals("") ? "0.0" : str);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(DgDzscContractCav.this,
						"输入非法数字！", "", JOptionPane.WARNING_MESSAGE);
				if (editeType == 1) {
					return info.getUnitWaste();
				} else if (editeType == 2) {
					return info.getWasteAmount();
				} else if (editeType == 3) {
					return info.getTotalAmount();
				} else
					return 0.0;
			}
			Double nval = Double.valueOf(str.trim().equals("") ? "0.0" : str);

			return nval;
		}
	}

	/**
	 * This method initializes jPopupMenuPrintReport
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getJPopupMenuPrintReport() {
		if (jPopupMenuPrintReport == null) {
			jPopupMenuPrintReport = new JPopupMenu();
			jPopupMenuPrintReport.add(getMiNonCoverPrintApplied());
			jPopupMenuPrintReport.add(getMiCoverPrintApplied());
			jPopupMenuPrintReport.add(getMiNonCoverPrintAppliedImg());
			jPopupMenuPrintReport.add(getMiCoverPrintAppliedImg());
			jPopupMenuPrintReport.add(getMiNonCoverPrintAppliedExg());
			jPopupMenuPrintReport.add(getMiCoverPrintAppliedExg());
			jPopupMenuPrintReport
					.add(getMiNonCoverPrintAppliedUnitConsumption());
			jPopupMenuPrintReport.add(getMiCoverPrintAppliedUnitConsumption());
			jPopupMenuPrintReport.add(getMiNonCoverPrintAppliedNewSmall());
		}
		return jPopupMenuPrintReport;
	}
	/**
	 * 打印非套打核销表(新)
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiNonCoverPrintAppliedNewSmall() {
		if (miNonCoverPrintAppliedNewSmall == null) {
			miNonCoverPrintAppliedNewSmall = new JMenuItem();
			miNonCoverPrintAppliedNewSmall.setText("打印非套打核销表(新1)");
			
			miNonCoverPrintAppliedNewSmall
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printNonCoverPrintAppliedNew(true);
						}
					});
		}
		return miNonCoverPrintAppliedNewSmall;
	}

	/**
	 * 8打印非套打核销料件表（新）
	 */
	private void printNonCoverPrintAppliedNew(Boolean small) {
		DzscContractCav dzscContractCav = null;
		if (rbCustoms.isSelected()) {
			dzscContractCav = this.dzscContractCavCustoms;
		} else {
			dzscContractCav = this.dzscContractCavSelf;
		}
		if (dzscContractCav == null) {
			return;
		}
		InputStream reportStream = null;
		List<Object> list = new ArrayList<Object>();
		Map<String, Object> parameters = new HashMap<String, Object>();
		CustomReportDataSource ds = new CustomReportDataSource(list);
		Company company = (Company) dzscContractCav.getCompany();
		list.add(dzscContractCav);
		ds = new CustomReportDataSource(list);
		if(small){
			reportStream = DgDzscContractCav.class
			.getResourceAsStream("report/ContractCavHeadNew.jasper");
		}else{
			reportStream = DgContractCav.class
			.getResourceAsStream("report/ContractCavHeadNewBig.jasper");
		}
		
		parameters = new HashMap<String, Object>();
		parameters.put("companyCode", company.getCode());
		parameters.put("companyName", company.getName());
		parameters.put("companyTel", company.getTel());
		parameters.put("companyCoLevel", company.getCoLevel());
		parameters.put("overprint", new Boolean(false));
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}
	/**
	 * 非套打核销表头
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiNonCoverPrintApplied() {
		if (miNonCoverPrintApplied == null) {
			miNonCoverPrintApplied = new JMenuItem();
			miNonCoverPrintApplied.setText("非套打核销表头");
			miNonCoverPrintApplied
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printNonCoverPrintApplied();
						}
					});
		}
		return miNonCoverPrintApplied;
	}

	/**
	 * 套打核销表头
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiCoverPrintApplied() {
		if (miCoverPrintApplied == null) {
			miCoverPrintApplied = new JMenuItem();
			miCoverPrintApplied.setText("套打核销表");
			miCoverPrintApplied
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printMiCoverPrintApplied();
						}
					});
		}
		return miCoverPrintApplied;
	}

	/**
	 * 非套打核销料件表
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiNonCoverPrintAppliedImg() {
		if (miNonCoverPrintAppliedImg == null) {
			miNonCoverPrintAppliedImg = new JMenuItem();
			miNonCoverPrintAppliedImg.setText("非套打核销料件表");
			miNonCoverPrintAppliedImg
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printMiNonCoverPrintAppliedImg();
						}
					});
		}
		return miNonCoverPrintAppliedImg;
	}

	/**
	 * This method initializes miCoverPrintAppliedImg
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiCoverPrintAppliedImg() {
		if (miCoverPrintAppliedImg == null) {
			miCoverPrintAppliedImg = new JMenuItem();
			miCoverPrintAppliedImg.setText("套打核销料件表");
			miCoverPrintAppliedImg
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printMiCoverPrintAppliedImg();
						}
					});
		}
		return miCoverPrintAppliedImg;
	}

	/**
	 * This method initializes miNonCoverPrintAppliedExg
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiNonCoverPrintAppliedExg() {
		if (miNonCoverPrintAppliedExg == null) {
			miNonCoverPrintAppliedExg = new JMenuItem();
			miNonCoverPrintAppliedExg.setText("非套打核销成品表");
			miNonCoverPrintAppliedExg
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printNonCoverPrintAppliedExg();
						}
					});
		}
		return miNonCoverPrintAppliedExg;
	}

	/**
	 * This method initializes miCoverPrintAppliedExg
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiCoverPrintAppliedExg() {
		if (miCoverPrintAppliedExg == null) {
			miCoverPrintAppliedExg = new JMenuItem();
			miCoverPrintAppliedExg.setText("套打核销成品表");
			miCoverPrintAppliedExg
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printMiCoverPrintAppliedExg();
						}
					});
		}
		return miCoverPrintAppliedExg;
	}

	/**
	 * 非套打核销单耗表
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiNonCoverPrintAppliedUnitConsumption() {
		if (miNonCoverPrintAppliedUnitConsumption == null) {
			miNonCoverPrintAppliedUnitConsumption = new JMenuItem();
			miNonCoverPrintAppliedUnitConsumption.setText("非套打核销单耗表");
			miNonCoverPrintAppliedUnitConsumption
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printNonCoverPrintAppliedUnitConsumption();
						}
					});
		}
		return miNonCoverPrintAppliedUnitConsumption;
	}

	/**
	 * 套打核销单耗表
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiCoverPrintAppliedUnitConsumption() {
		if (miCoverPrintAppliedUnitConsumption == null) {
			miCoverPrintAppliedUnitConsumption = new JMenuItem();
			miCoverPrintAppliedUnitConsumption.setText("套打核销单耗表");
			miCoverPrintAppliedUnitConsumption
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printCoverPrintAppliedUnitConsumption();
						}
					});
		}
		return miCoverPrintAppliedUnitConsumption;
	}

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

	private void addChangeModifyMarkListener(JMenuItem menuItem,
			ActionListener actionListener) {
		ActionListener[] actionListeners = menuItem.getActionListeners();
		for (int i = 0; i < actionListeners.length; i++) {
			menuItem.removeActionListener(actionListeners[i]);
		}
		menuItem.addActionListener(actionListener);
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (btnChangeImgModifyMark == null) {
			btnChangeImgModifyMark = new JButton();
			btnChangeImgModifyMark.setText("改变修改标志");
			btnChangeImgModifyMark.setToolTipText(ModifyMarkState
					.getToolTipText());
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
			if (tableModelBom.getCurrentRow() == null) {
				JOptionPane.showMessageDialog(DgDzscContractCav.this, "请选择BOM",
						"提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			int start = tableModelBom.getCurrRowCount();
			int size = tblBom.getSelectedRowCount();
			// System.out.println(start);
			// System.out.println(size);
			List list = new ArrayList();
			for (int i = 0; i < size; i++) {
				list.add(tableModelBom.getObjectByRow(start + i));
			}
			contractCavAction.changeContractImgModifyMark(new Request(
					CommonVars.getCurrUser()), list, modifyMark);
			showBomData(true);
			System.out.println("second modifyMark" + modifyMark);
		}

	}
}// @jve:decl-index=0:visual-constraint="10,23"

