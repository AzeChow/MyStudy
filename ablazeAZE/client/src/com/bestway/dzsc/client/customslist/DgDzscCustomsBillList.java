/*
 * Created on 2004-7-27
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.customslist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.DzscListType;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.dzsc.customslist.action.DzscListAction;
import com.bestway.dzsc.customslist.entity.DzscBillListAfterCommInfo;
import com.bestway.dzsc.customslist.entity.DzscBillListBeforeCommInfo;
import com.bestway.dzsc.customslist.entity.DzscCustomsBillList;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDzscCustomsBillList extends JDialogBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JButton btnAdd = null;

	private JButton btnEdit = null;

	private JButton btnDelete = null;

	private JButton btnSave = null;

	private JButton btnCancel = null;

	private JComboBox cbbImpExpType = null;

	private JTextField tfBillListNo = null;

	private JTextField tfTradeName = null;

	private JComboBox cbbMaterialType = null;

	private JComboBox cbbTransportMode = null;

	private JTextField tfCreater = null;

	private JTextField tfCreateCompanyName = null;

	private JTextField tfMemo = null;

	private JTextField tfEmsNo = null;

	private JTextField tfTradeCode = null;

	private JTextField tfMaterialNum = null;

	private JTextField tfListState = null;

	private JTable tbMergeAfterCommInfo = null;

	private JScrollPane jScrollPane = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	private DateFormatter dateFormatter = null; // @jve:decl-index=0:parse

	private DateFormatSymbols dateFormatSymbols = null; // @jve:decl-index=0:parse

	private DateFormatter dateFormatter1 = null; // @jve:decl-index=0:parse

	private JComboBox cbbTradeMode = null;

	// private JTableListModel billListModel = null;

	private int impExpFlag = ImpExpFlag.IMPORT; // DzscCustomsBillList.IMPORT;

	private DzscEmsPorHead emsHeadH2k = null;// 电子手册 // @jve:decl-index=0:

	private DzscListAction dzscListAction = null;

	private CustomBaseAction customBaseAction = null;

	private JTableListModel billListTableModel = null;

	private JTableListModel beforeCommInfoTableModel = null;

	private JTableListModel afterCommInfoTableModel = null;

	private DzscCustomsBillList dzscCustomsBillList = null; // @jve:decl-index=0:

	private List beforeTableDataSource = null;

	private List afterTableDataSource = null;

	private int dataState = DataState.BROWSE;

	private JTable tbMergeBeforeCommInfo = null;

	private JScrollPane jScrollPane1 = null;

	private JSplitPane jSplitPane = null;

	private boolean isTransferAtc = false;// 是否产生报关单

	private JLabel jLabel3 = null;

	private JTextField tfMachCode = null;

	private JLabel jLabel18 = null;

	private JTextField tfMachName = null;

	private JComboBox cbbDeclareCIQ = null;

	private JComboBox cbbImpExpCIQ = null;

	private JLabel jLabel19 = null;

	private JTextField tfCopEmsNo = null;

	private JLabel jLabel20 = null;

	private JCalendarComboBox cbbImpExpDate = null;

	private JLabel jLabel21 = null;

	private JLabel jLabel22 = null;

	private JTextField tfCustomerOrderCode = null;

	private JTextField tfInvoiceCode = null;

	private JLabel jLabel171 = null;

	private JComboBox cbbListType = null;

	private JLabel jLabel23 = null;

	private JTextField tfCustomsSeqNo = null;

	private JTextField tfCreatedDate = null;

	private JTextField tfListDeclareDate = null;

	private JButton btnPrint = null;
	
	private JCheckBox jCheckBox = null;//显示所有
	
	private boolean isMerger = false;

	public boolean isTransferAtc() {
		return isTransferAtc;
	}

	public void setTransferAtc(boolean isTransferAtc) {
		this.isTransferAtc = isTransferAtc;
	}

	public DzscEmsPorHead getEmsHeadH2k() {
		return emsHeadH2k;
	}

	public void setEmsHeadH2k(DzscEmsPorHead emsHeadH2k) {
		this.emsHeadH2k = emsHeadH2k;
	}

	public JTableListModel getBillListTableModel() {
		return billListTableModel;
	}

	public void setBillListTableModel(JTableListModel tableModel) {
		this.billListTableModel = tableModel;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	public int getImpExpFlag() {
		return impExpFlag;
	}

	public void setImpExpFlag(int impExpFlag) {
		this.impExpFlag = impExpFlag;
	}

	public DgDzscCustomsBillList() {
		super();
		initialize();
		dzscListAction = (DzscListAction) CommonVars.getApplicationContext()
				.getBean("dzscListAction");
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		tbMergeAfterCommInfo.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting()) {
							return;
						}
						if (afterCommInfoTableModel == null) {
							return;
						}
						try {
							if (afterCommInfoTableModel.getCurrentRow() != null) {
								initBeforeTable(isMerger);
							}
						} catch (Exception ee) {
						}
					}
				});
		jScrollPane.setViewportView(tbMergeAfterCommInfo);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("报关清单编辑");
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setSize(663, 482);
		jCheckBox.setSelected(true);
	}

	public void setVisible(boolean b) {
		if (b) {
			initUIComponents();
			showPrimalData();
			setState();
		}
		super.setVisible(b);
	}

	private Brief getBriefByCode(String code) {
		List list = customBaseAction.findBrief("code", code);
		if (list.size() > 0) {
			return (Brief) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 当新增或删除时，显示最初数据
	 */
	private void showPrimalData() {
		if (dataState == DataState.ADD) {
			this.setTransferAtc(false);
			dzscCustomsBillList = new DzscCustomsBillList();
			// dzscCustomsBillList.setListNo(getMaxBillListNo());
			dzscCustomsBillList.setCopEmsNo(dzscCustomsBillList.getListNo());
			dzscCustomsBillList.setImpExpFlag(Integer.valueOf(getImpExpFlag()));
			dzscCustomsBillList.setEmsHeadH2k(emsHeadH2k.getEmsNo());
			dzscCustomsBillList.setTradeCode(emsHeadH2k.getTradeCode());
			dzscCustomsBillList.setTradeName(emsHeadH2k.getTradeName());
			dzscCustomsBillList.setCreatedUser(CommonVars.getCurrUser());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dzscCustomsBillList.setCreatedDate(java.sql.Date.valueOf(dateFormat
					.format(new Date())));
			dzscCustomsBillList
					.setCreatedCompany(getBriefByCode(((Company) CommonVars
							.getCurrUser().getCompany()).getCode()));
			dzscCustomsBillList.setListState(Integer
					.valueOf(DzscState.ORIGINAL));
			dzscCustomsBillList.setListType(DzscListType.NORMAL_DCR);
		} else if (dataState == DataState.EDIT || dataState == DataState.BROWSE) {
			dzscCustomsBillList = (DzscCustomsBillList) billListTableModel
					.getCurrentRow();
			if (dzscCustomsBillList.getIsGenerateDeclaration() != null
					&& dzscCustomsBillList.getIsGenerateDeclaration()) {
				this.setTransferAtc(true);
			}
		}
		showData(dzscCustomsBillList);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
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
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnCancel());
			jToolBar.add(getBtnPrint());
			jToolBar.add(getJCheckBox());
		}
		return jToolBar;
	}
	
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new java.awt.Rectangle(9, 7, 108, 18));
			jCheckBox.setForeground(java.awt.Color.blue);
			jCheckBox.setText("显示所有");
			jCheckBox.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (jCheckBox.isSelected()) {
						isMerger = true;
					} else {
						isMerger = false;
					}
					initBeforeTable(isMerger);
				}
			});
		}
		return jCheckBox;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * jTabbedPane.addTab(null, null, getJPanel1(), null);
	 * jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
			jTabbedPane.addTab("基本信息", null, getJPanel1(), null);
			jTabbedPane.addTab("商品信息", null, getJPanel(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							DgDzscCustomsBillList.this.setCursor(new Cursor(
									Cursor.WAIT_CURSOR));
							if (jTabbedPane.getSelectedIndex() == 0) {
								if (dataState == DataState.ADD) {
									showPrimalData();
								} else {
									if (billListTableModel.getCurrentRow() != null) {
										showPrimalData();
									} else {
										showEmptyData();
									}
								}
							} else {
								if (dataState != DataState.BROWSE) {
									if (!checkData()) {
										jTabbedPane.setSelectedIndex(0);
										return;
									}
									saveApplyToCustomsBillList();
								}
								initAfterTable();
								initBeforeTable(isMerger);
							}
							setState();
							DgDzscCustomsBillList.this.setCursor(new Cursor(
									Cursor.DEFAULT_CURSOR));
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
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel23 = new JLabel();
			jLabel23.setBounds(new Rectangle(331, 324, 86, 21));
			jLabel23.setText("报关单统一编号");
			jLabel171 = new JLabel();
			jLabel171.setBounds(new Rectangle(344, 268, 75, 24));
			jLabel171.setText("清单类型");
			jLabel22 = new JLabel();
			jLabel22.setBounds(new Rectangle(51, 324, 81, 23));
			jLabel22.setText("订单号");
			jLabel21 = new JLabel();
			jLabel21.setBounds(new Rectangle(51, 297, 81, 22));
			jLabel21.setText("发票号");
			jLabel20 = new JLabel();
			jLabel20.setBounds(new Rectangle(344, 129, 74, 24));
			jLabel20.setText("进出口日期");
			jLabel20.setForeground(Color.blue);
			jLabel19 = new JLabel();
			jLabel19.setBounds(new Rectangle(344, 46, 74, 22));
			jLabel19.setText("企业内部编号");
			jLabel19.setForeground(Color.blue);
			jLabel18 = new JLabel();
			jLabel18.setBounds(new Rectangle(344, 101, 74, 23));
			jLabel18.setText("加工单位名称");
			jLabel18.setForeground(Color.blue);
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(52, 102, 80, 21));
			jLabel3.setText("加工单位编码");
			jLabel3.setForeground(Color.blue);
			javax.swing.JLabel jLabel17 = new JLabel();
			javax.swing.JLabel jLabel16 = new JLabel();
			javax.swing.JLabel jLabel15 = new JLabel();
			javax.swing.JLabel jLabel14 = new JLabel();
			javax.swing.JLabel jLabel13 = new JLabel();
			javax.swing.JLabel jLabel12 = new JLabel();
			javax.swing.JLabel jLabel11 = new JLabel();
			javax.swing.JLabel jLabel10 = new JLabel();
			javax.swing.JLabel jLabel9 = new JLabel();
			javax.swing.JLabel jLabel8 = new JLabel();
			javax.swing.JLabel jLabel7 = new JLabel();
			javax.swing.JLabel jLabel6 = new JLabel();
			javax.swing.JLabel jLabel5 = new JLabel();
			javax.swing.JLabel jLabel4 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel = new JLabel();
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jLabel.setBounds(52, 19, 80, 20);
			jLabel.setText("进出口类型");
			jLabel1.setBounds(52, 45, 80, 20);
			jLabel1.setText("清单编号");
			jLabel1.setForeground(Color.blue);
			jLabel2.setBounds(344, 74, 74, 23);
			jLabel2.setText("经营单位名称");
			jLabel2.setForeground(Color.blue);
			jLabel4.setBounds(52, 157, 80, 20);
			jLabel4.setText("料件成品标志");
			jLabel4.setForeground(Color.blue);
			jLabel5.setBounds(52, 186, 80, 20);
			jLabel5.setText("申报地海关");
			jLabel6.setBounds(52, 214, 80, 20);
			jLabel6.setText("运输方式");
			jLabel6.setForeground(Color.blue);
			jLabel7.setBounds(52, 242, 80, 20);
			jLabel7.setText("录入人员名称");
			jLabel8.setBounds(52, 268, 80, 25);
			jLabel8.setText("录入单位名称");
			jLabel9.setBounds(51, 352, 74, 20);
			jLabel9.setText("备注");
			jLabel10.setBounds(344, 19, 74, 23);
			jLabel10.setText("备案号");
			jLabel10.setForeground(Color.blue);
			jLabel11.setBounds(52, 70, 80, 23);
			jLabel11.setText("经营单位编码");
			jLabel11.setForeground(Color.blue);
			jLabel12.setBounds(52, 129, 80, 23);
			jLabel12.setText("清单申报日期");
			jLabel12.setForeground(Color.blue);
			jLabel13.setBounds(344, 157, 74, 23);
			jLabel13.setText("商品项数");
			jLabel14.setBounds(344, 186, 74, 23);
			jLabel14.setText("进/出口岸");
			jLabel14.setForeground(Color.blue);
			jLabel15.setBounds(344, 214, 74, 23);
			jLabel15.setText("贸易方式");
			jLabel15.setForeground(Color.blue);
			jLabel16.setBounds(344, 242, 74, 20);
			jLabel16.setText("录入日期");
			jLabel16.setForeground(Color.blue);
			jLabel17.setBounds(344, 297, 74, 23);
			jLabel17.setText("清单状态");
			jPanel1.add(jLabel, null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(jLabel4, null);
			jPanel1.add(jLabel5, null);
			jPanel1.add(jLabel6, null);
			jPanel1.add(jLabel7, null);
			jPanel1.add(jLabel8, null);
			jPanel1.add(jLabel9, null);
			jPanel1.add(jLabel10, null);
			jPanel1.add(jLabel11, null);
			jPanel1.add(jLabel12, null);
			jPanel1.add(jLabel13, null);
			jPanel1.add(jLabel14, null);
			jPanel1.add(jLabel15, null);
			jPanel1.add(jLabel16, null);
			jPanel1.add(jLabel17, null);
			jPanel1.add(getCbbImpExpType(), null);
			jPanel1.add(getTfBillListNo(), null);
			jPanel1.add(getTfTradeName(), null);
			jPanel1.add(getCbbMaterialType(), null);
			jPanel1.add(getCbbTransportMode(), null);
			jPanel1.add(getTfCreater(), null);
			jPanel1.add(getTfCreateCompanyName(), null);
			jPanel1.add(getTfMemo(), null);
			jPanel1.add(getTfEmsNo(), null);
			jPanel1.add(getTfTradeCode(), null);
			jPanel1.add(getTfMaterialNum(), null);
			jPanel1.add(getTfListState(), null);
			jPanel1.add(getCbbTradeMode(), null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(getTfMachCode(), null);
			jPanel1.add(jLabel18, null);
			jPanel1.add(getTfMachName(), null);
			jPanel1.add(getCbbDeclareCIQ(), null);
			jPanel1.add(getCbbImpExpCIQ(), null);
			jPanel1.add(jLabel19, null);
			jPanel1.add(getTfCopEmsNo(), null);
			jPanel1.add(jLabel20, null);
			jPanel1.add(getCbbImpExpDate(), null);
			jPanel1.add(jLabel21, null);
			jPanel1.add(jLabel22, null);
			jPanel1.add(getTfCustomerOrderCode(), null);
			jPanel1.add(getTfInvoiceCode(), null);
			jPanel1.add(jLabel171, null);
			jPanel1.add(getCbbListType(), null);
			jPanel1.add(jLabel23, null);
			jPanel1.add(getTfCustomsSeqNo(), null);
			jPanel1.add(getTfCreatedDate(), null);
			jPanel1.add(getTfListDeclareDate(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes btnAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0) {
						dataState = DataState.ADD;
						showPrimalData();
						setState();
					} else {
						if (billListTableModel.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(
									DgDzscCustomsBillList.this, "请先新增基本信息！",
									"提示", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						Integer materielType = cbbMaterialType
								.getSelectedItem() == null ? null : Integer
								.valueOf(((ItemProperty) cbbMaterialType
										.getSelectedItem()).getCode());
						List list = DzscListQuery.getInstance()
								.getTempDeclaredCommInfo(dzscCustomsBillList,
										materielType);
						if (list != null) {
							dzscCustomsBillList = dzscListAction
									.saveAtcMergeBeforeComInfo(new Request(
											CommonVars.getCurrUser()), list,
											dzscCustomsBillList);
							billListTableModel.updateRow(dzscCustomsBillList);
							initAfterTable();
							initBeforeTable(isMerger);
						}
					}
				}
			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0) {
						dataState = DataState.EDIT;
						showPrimalData();
						setState();
					} else {
						if (beforeCommInfoTableModel.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(
									DgDzscCustomsBillList.this, "请选择要修改的资料",
									"提示", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						DgDzscListCommInfo dgAtcMergeCommInfo = new DgDzscListCommInfo();
						dgAtcMergeCommInfo
								.setTableModel(beforeCommInfoTableModel);
						dgAtcMergeCommInfo.setVisible(true);
						if (dgAtcMergeCommInfo.isOk()) {
							DzscBillListBeforeCommInfo beforeComInfo = (DzscBillListBeforeCommInfo) beforeCommInfoTableModel
									.getCurrentRow();
							afterCommInfoTableModel.updateRow(beforeComInfo
									.getAfterComInfo());
							// initAfterTable();
							// initBeforeTable();
						}
					}
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes btnDelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (beforeCommInfoTableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								DgDzscCustomsBillList.this, "请选择要删除的数据", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (JOptionPane.showConfirmDialog(
							DgDzscCustomsBillList.this, "是否确定删除数据!!!", "提示", 0) != 0) {
						return;
					}
					// AtcMergeBeforeComInfo commInfo = (AtcMergeBeforeComInfo)
					// commInfoTableModel
					// .getCurrentRow();
					DzscCustomsBillList billList = dzscListAction
							.deleteAtcMergeBeforeComInfo(new Request(CommonVars
									.getCurrUser()), beforeCommInfoTableModel
									.getCurrentRows());
					// commInfoTableModel.deleteRow(commInfo);
					billListTableModel.updateRow(billList);
					initAfterTable();
					initBeforeTable(isMerger);
				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					saveApplyToCustomsBillList();
					showPrimalData();
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.BROWSE;
					if (billListTableModel.getCurrentRow() != null) {
						showPrimalData();
					} else {
						dzscCustomsBillList = null;
						showEmptyData();
					}
					setState();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes cbbImpExpType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpExpType() {
		if (cbbImpExpType == null) {
			cbbImpExpType = new JComboBox();
			cbbImpExpType.setBounds(135, 19, 187, 22);
			cbbImpExpType.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (dataState != DataState.BROWSE
							&& e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						int impExpType = Integer
								.parseInt(((ItemProperty) cbbImpExpType
										.getSelectedItem()).getCode());
						switch (impExpType) {
						case ImpExpType.DIRECT_IMPORT:
							// cbbMaterielProductFlag.
							cbbMaterialType.setSelectedIndex(ItemProperty
									.getIndexByCode(MaterielType.MATERIEL,
											cbbMaterialType));
							break;
						case ImpExpType.TRANSFER_FACTORY_IMPORT:
							cbbMaterialType.setSelectedIndex(ItemProperty
									.getIndexByCode(MaterielType.MATERIEL,
											cbbMaterialType));
							break;
						case ImpExpType.BACK_FACTORY_REWORK:
							cbbMaterialType.setSelectedIndex(ItemProperty
									.getIndexByCode(
											MaterielType.FINISHED_PRODUCT,
											cbbMaterialType));
							break;
						case ImpExpType.GENERAL_TRADE_IMPORT:
							cbbMaterialType.setSelectedIndex(ItemProperty
									.getIndexByCode(MaterielType.MATERIEL,
											cbbMaterialType));
							break;
						case ImpExpType.DIRECT_EXPORT:
							cbbMaterialType.setSelectedIndex(ItemProperty
									.getIndexByCode(
											MaterielType.FINISHED_PRODUCT,
											cbbMaterialType));
							break;
						case ImpExpType.TRANSFER_FACTORY_EXPORT:
							cbbMaterialType.setSelectedIndex(ItemProperty
									.getIndexByCode(
											MaterielType.FINISHED_PRODUCT,
											cbbMaterialType));
							break;
						case ImpExpType.BACK_MATERIEL_EXPORT:
							cbbMaterialType.setSelectedIndex(ItemProperty
									.getIndexByCode(MaterielType.MATERIEL,
											cbbMaterialType));
							break;
						case ImpExpType.REWORK_EXPORT:
							cbbMaterialType.setSelectedIndex(ItemProperty
									.getIndexByCode(
											MaterielType.FINISHED_PRODUCT,
											cbbMaterialType));
							break;
						case ImpExpType.REMIAN_MATERIAL_BACK_PORT:
							cbbMaterialType.setSelectedIndex(ItemProperty
									.getIndexByCode(MaterielType.MATERIEL,
											cbbMaterialType));
							break;
						case ImpExpType.GENERAL_TRADE_EXPORT:
							cbbMaterialType.setSelectedIndex(ItemProperty
									.getIndexByCode(
											MaterielType.FINISHED_PRODUCT,
											cbbMaterialType));
							break;
						}
					}
				}
			});
		}
		return cbbImpExpType;
	}

	/**
	 * This method initializes tfBillListNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBillListNo() {
		if (tfBillListNo == null) {
			tfBillListNo = new JTextField();
			tfBillListNo.setBounds(135, 46, 187, 22);
			tfBillListNo.setEditable(false);
		}
		return tfBillListNo;
	}

	/**
	 * This method initializes tfTradeName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTradeName() {
		if (tfTradeName == null) {
			tfTradeName = new JTextField();
			tfTradeName.setBounds(417, 74, 190, 22);
			tfTradeName.setEditable(false);
		}
		return tfTradeName;
	}

	/**
	 * This method initializes cbbMaterielProductFlag
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbMaterialType() {
		if (cbbMaterialType == null) {
			cbbMaterialType = new JComboBox();
			cbbMaterialType.setBounds(135, 157, 187, 22);
		}
		return cbbMaterialType;
	}

	/**
	 * This method initializes cbbTransportMode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTransportMode() {
		if (cbbTransportMode == null) {
			cbbTransportMode = new JComboBox();
			cbbTransportMode.setBounds(135, 214, 187, 22);
		}
		return cbbTransportMode;
	}

	/**
	 * This method initializes tfCreater
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCreater() {
		if (tfCreater == null) {
			tfCreater = new JTextField();
			tfCreater.setBounds(135, 242, 187, 22);
			tfCreater.setEditable(false);
		}
		return tfCreater;
	}

	/**
	 * This method initializes tfCreateCompanyName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCreateCompanyName() {
		if (tfCreateCompanyName == null) {
			tfCreateCompanyName = new JTextField();
			tfCreateCompanyName.setBounds(135, 270, 187, 22);
			tfCreateCompanyName.setEditable(false);
		}
		return tfCreateCompanyName;
	}

	/**
	 * This method initializes tfMemo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMemo() {
		if (tfMemo == null) {
			tfMemo = new JTextField();
			tfMemo.setBounds(123, 351, 486, 22);
		}
		return tfMemo;
	}

	/**
	 * This method initializes tfEmsNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setBounds(417, 19, 190, 22);
			tfEmsNo.setEditable(false);
		}
		return tfEmsNo;
	}

	/**
	 * This method initializes tfTradeCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTradeCode() {
		if (tfTradeCode == null) {
			tfTradeCode = new JTextField();
			tfTradeCode.setBounds(135, 74, 187, 22);
			tfTradeCode.setEditable(false);
		}
		return tfTradeCode;
	}

	/**
	 * This method initializes tfMaterialNum
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMaterialNum() {
		if (tfMaterialNum == null) {
			tfMaterialNum = new JTextField();
			tfMaterialNum.setBounds(417, 157, 190, 22);
			tfMaterialNum.setEditable(false);
		}
		return tfMaterialNum;
	}

	/**
	 * This method initializes tfListState
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfListState() {
		if (tfListState == null) {
			tfListState = new JTextField();
			tfListState.setBounds(417, 297, 190, 22);
			tfListState.setEditable(false);
		}
		return tfListState;
	}

	/**
	 * This method initializes tbMergeAfterCommInfo
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbMergeAfterCommInfo() {
		if (tbMergeAfterCommInfo == null) {
			tbMergeAfterCommInfo = new JTable();
		}
		return tbMergeAfterCommInfo;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbMergeAfterCommInfo());
			jScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "归并后商品信息（不可修改）",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
		}
		return jScrollPane;
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDisplayFormatter(getDateFormatter());
			defaultFormatterFactory.setEditFormatter(getDateFormatter1());
		}
		return defaultFormatterFactory;
	}

	/**
	 * This method initializes dateFormatter
	 * 
	 * @return javax.swing.text.DateFormatter
	 */
	private DateFormatter getDateFormatter() {
		if (dateFormatter == null) {
			dateFormatter = new DateFormatter();
			java.text.SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(); // @jve:decl-index=0:parse
			simpleDateFormat1.setDateFormatSymbols(getDateFormatSymbols());
			dateFormatter.setFormat(simpleDateFormat1);
		}
		return dateFormatter;
	}

	/**
	 * This method initializes dateFormatSymbols
	 * 
	 * @return java.text.DateFormatSymbols
	 */
	private DateFormatSymbols getDateFormatSymbols() {
		if (dateFormatSymbols == null) {
			dateFormatSymbols = new DateFormatSymbols();
		}
		return dateFormatSymbols;
	}

	/**
	 * This method initializes dateFormatter1
	 * 
	 * @return javax.swing.text.DateFormatter
	 */
	private DateFormatter getDateFormatter1() {
		if (dateFormatter1 == null) {
			dateFormatter1 = new DateFormatter();
		}
		return dateFormatter1;
	}

	/**
	 * This method initializes cbbTradeMode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTradeMode() {
		if (cbbTradeMode == null) {
			cbbTradeMode = new JComboBox();
			cbbTradeMode.setBounds(417, 214, 190, 22);
		}
		return cbbTradeMode;
	}

	private void initUIComponents() {
		// 初始化进出口类型
		if (impExpFlag == ImpExpFlag.IMPORT) {
			this.cbbImpExpType.removeAllItems();
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.DIRECT_IMPORT).toString(), "料件进口"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.TRANSFER_FACTORY_IMPORT).toString(), "料件转厂"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.BACK_FACTORY_REWORK).toString(), "退厂返工"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.GENERAL_TRADE_IMPORT).toString(), "一般贸易进口"));

		} else {
			this.cbbImpExpType.removeAllItems();
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.DIRECT_EXPORT).toString(), "成品出口"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.TRANSFER_FACTORY_EXPORT).toString(), "转厂出口"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.BACK_MATERIEL_EXPORT).toString(), "退料出口"));

			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.REWORK_EXPORT).toString(), "返工复出"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.REMIAN_MATERIAL_BACK_PORT).toString(), "边角料退港"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES).toString(),
					"边角料内销"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.GENERAL_TRADE_EXPORT).toString(), "一般贸易出口"));
		}
		this.cbbImpExpType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbImpExpType);
		this.cbbImpExpType.setSelectedIndex(-1);
		// 初始化料件/成品标志
		this.cbbMaterialType.removeAllItems();
		this.cbbMaterialType.addItem(new ItemProperty(MaterielType.MATERIEL,
				"料件"));
		this.cbbMaterialType.addItem(new ItemProperty(
				MaterielType.FINISHED_PRODUCT, "成品"));
		this.cbbMaterialType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbMaterialType);
		this.cbbMaterialType.setSelectedIndex(-1);
		// 初始化申报地海关
		this.cbbDeclareCIQ.setModel(CustomBaseModel.getInstance()
				.getCustomModel());
		this.cbbDeclareCIQ.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbDeclareCIQ);
		this.cbbDeclareCIQ.setSelectedIndex(-1);
		// 初始化进出口岸
		this.cbbImpExpCIQ.setModel(CustomBaseModel.getInstance()
				.getCustomModel());
		this.cbbImpExpCIQ.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbImpExpCIQ);
		this.cbbImpExpCIQ.setSelectedIndex(-1);
		// 初始化运输方式
		this.cbbTransportMode.setModel(CustomBaseModel.getInstance()
				.getTransfModel());
		this.cbbTransportMode.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbTransportMode);
		this.cbbTransportMode.setSelectedIndex(-1);
		// 初始化贸易方式
		this.cbbTradeMode.setModel(CustomBaseModel.getInstance()
				.getTradeModel());
		this.cbbTradeMode.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbTradeMode);
		this.cbbTradeMode.setSelectedIndex(-1);

		this.cbbImpExpDate.setDate(null);

		// 清单类型
		this.cbbListType.removeAllItems();
		this.cbbListType.addItem(new ItemProperty(Integer.valueOf(
				DzscListType.NORMAL_DCR).toString(), DzscListType
				.getListTypeDesc(DzscListType.NORMAL_DCR)));
		this.cbbListType.addItem(new ItemProperty(Integer.valueOf(
				DzscListType.PRE_TRANS_DCR).toString(), DzscListType
				.getListTypeDesc(DzscListType.PRE_TRANS_DCR)));
		this.cbbListType.addItem(new ItemProperty(Integer.valueOf(
				DzscListType.SECOND_TRANS_DCR).toString(), DzscListType
				.getListTypeDesc(DzscListType.SECOND_TRANS_DCR)));
		this.cbbListType.addItem(new ItemProperty(Integer.valueOf(
				DzscListType.APANAGE_DCR).toString(), DzscListType
				.getListTypeDesc(DzscListType.APANAGE_DCR)));
		this.cbbListType
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbListType);
		this.cbbListType.setSelectedIndex(-1);
	}

	// private String getMaxBillListNo() {
	// return dzscListAction.getApplyToCustomsBillListMaxNo(new Request(
	// CommonVars.getCurrUser()));
	// }

	private void fillBillListData(DzscCustomsBillList data) {
		if (dataState == DataState.ADD) {
			data.setCompany(CommonVars.getCurrUser().getCompany());
			data.setImpExpType(Integer
					.valueOf(((ItemProperty) this.cbbImpExpType
							.getSelectedItem()).getCode()));
			// this.tfBillListNo.setText(getMaxBillListNo());
			data.setListNo(this.tfBillListNo.getText().trim());
			if (!this.tfListDeclareDate.getText().trim().equals("")) {
				try {
					data.setListDeclareDate(DateFormat.getInstance().parse(
							this.tfListDeclareDate.getText().trim()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			data.setTradeCode(this.tfTradeCode.getText().trim());
			data.setTradeName(this.tfTradeName.getText().trim());
			data.setMaterielProductFlag(Integer
					.valueOf(((ItemProperty) this.cbbMaterialType
							.getSelectedItem()).getCode()));
			// if (this.cbbDeclareCIQ.getSelectedItem() != null) {
			// data.setDeclareCIQ((Customs) this.cbbDeclareCIQ
			// .getSelectedItem());
			// } else {
			// data.setDeclareCIQ(null);
			// }
			// if (this.cbbImpExpCIQ.getSelectedItem() != null) {
			// data
			// .setImpExpCIQ((Customs) this.cbbImpExpCIQ
			// .getSelectedItem());
			// } else {
			// data.setImpExpCIQ(null);
			// }
			// if (this.cbbTransportMode.getSelectedItem() != null) {
			// data.setTransportMode((Transf) this.cbbTransportMode
			// .getSelectedItem());
			// } else {
			// data.setTransportMode(null);
			// }
			// if (this.cbbTradeMode.getSelectedItem() != null) {
			// data.setTradeMode((Trade) this.cbbTradeMode.getSelectedItem());
			// } else {
			// data.setTradeMode(null);
			// }
			data.setListState(Integer.valueOf(DzscState.ORIGINAL));
		}
		// else if (dataState == DataState.EDIT) {
		if (this.cbbDeclareCIQ.getSelectedItem() != null) {
			data.setDeclareCIQ((Customs) this.cbbDeclareCIQ.getSelectedItem());
		} else {
			data.setDeclareCIQ(null);
		}
		if (this.cbbImpExpCIQ.getSelectedItem() != null) {
			data.setImpExpCIQ((Customs) this.cbbImpExpCIQ.getSelectedItem());
		} else {
			data.setImpExpCIQ(null);
		}
		if (this.cbbTransportMode.getSelectedItem() != null) {
			data.setTransportMode((Transf) this.cbbTransportMode
					.getSelectedItem());
		} else {
			data.setTransportMode(null);
		}
		if (this.cbbTradeMode.getSelectedItem() != null) {
			data.setTradeMode((Trade) this.cbbTradeMode.getSelectedItem());
		} else {
			data.setTradeMode(null);
		}
		data.setImpExpDate(this.cbbImpExpDate.getDate());
		if (data.getCreatedDate() == null) {
			data.setCreatedDate(new Date());
		}
		if (data.getCreatedUser() == null) {
			data.setCreatedUser(CommonVars.getCurrUser());
		}
		data.setCopEmsNo(this.tfCopEmsNo.getText());
		if (this.cbbListType.getSelectedItem() != null) {
			data.setListType(Integer.valueOf(((ItemProperty) this.cbbListType
					.getSelectedItem()).getCode()));
		}
		// }
	}

	private void showData(DzscCustomsBillList data) {
		if (data.getImpExpType() != null) {
			this.cbbImpExpType.setSelectedIndex(ItemProperty.getIndexByCode(
					data.getImpExpType().toString(), this.cbbImpExpType));
		} else {
			this.cbbImpExpType.setSelectedIndex(-1);
		}
		this.tfEmsNo.setText(data.getEmsHeadH2k());

		this.tfTradeCode.setText(data.getTradeCode());
		this.tfTradeName.setText(data.getTradeName());

		this.tfBillListNo.setText(data.getListNo());
		this.tfCopEmsNo.setText(data.getCopEmsNo());
		if (data.getListDeclareDate() != null) {
			this.tfListDeclareDate
					.setText(data.getListDeclareDate().toString());
		} else {
			this.tfListDeclareDate.setText("");
		}
		if (data.getListType() != null) {
			this.cbbListType.setSelectedIndex(ItemProperty.getIndexByCode(data
					.getListType().toString(), this.cbbListType));
		} else {
			this.cbbListType.setSelectedIndex(-1);
		}
		if (data.getMaterielProductFlag() != null) {
			this.cbbMaterialType.setSelectedIndex(ItemProperty.getIndexByCode(
					data.getMaterielProductFlag().toString(),
					this.cbbMaterialType));
		} else {
			this.cbbMaterialType.setSelectedIndex(-1);
		}
		if (data.getMaterialNum() != null) {
			this.tfMaterialNum.setText(data.getMaterialNum().toString());
		} else {
			this.tfMaterialNum.setText("");
		}
		this.cbbDeclareCIQ.setSelectedItem(data.getDeclareCIQ());
		this.cbbImpExpCIQ.setSelectedItem(data.getImpExpCIQ());
		this.cbbTransportMode.setSelectedItem(data.getTransportMode());
		this.cbbTradeMode.setSelectedItem(data.getTradeMode());
		this.tfCreater.setText(data.getCreatedUser().getUserName());
		if (data.getCreatedDate() != null) {
			this.tfCreatedDate.setText(data.getCreatedDate().toString());
		} else {
			this.tfCreatedDate.setText("");
		}
		if (data.getCreatedCompany() != null) {
			this.tfCreateCompanyName
					.setText(data.getCreatedCompany().getName());
		} else {
			this.tfCreateCompanyName.setText("");
		}
		if (data.getListState() != null) {
			String listState = data.getListState().toString();
			if (listState.equals(DzscState.ORIGINAL)) {
				this.tfListState.setText("原始状态");
			} else if (listState.equals(DzscState.APPLY)) {
				this.tfListState.setText("正在申请");
			} else if (listState.equals(DzscState.EXECUTE)) {
				this.tfListState.setText("正在执行");
			}
		} else {
			this.tfListState.setText("");
		}
		this.tfMemo.setText(data.getMemos());

		Company company = (Company) CommonVars.getCurrUser().getCompany();
		this.tfMachCode.setText(company.getCode());
		this.tfMachName.setText(company.getName());

		this.cbbImpExpDate.setDate(data.getImpExpDate());
		this.tfCustomsSeqNo.setText(data.getCustomsSeqNo());
	}

	/**
	 * 显示空数据。
	 */
	private void showEmptyData() {
		this.cbbImpExpType.setSelectedIndex(-1);
		this.tfEmsNo.setText("");
		this.tfTradeCode.setText("");
		this.tfTradeName.setText("");
		this.tfBillListNo.setText("");
		this.tfListDeclareDate.setText("");
		this.cbbMaterialType.setSelectedIndex(-1);
		this.tfMaterialNum.setText("");
		this.cbbDeclareCIQ.setSelectedIndex(-1);
		this.cbbImpExpCIQ.setSelectedIndex(-1);
		this.cbbTransportMode.setSelectedIndex(-1);
		this.cbbTradeMode.setSelectedIndex(-1);
		this.tfCreater.setText("");
		this.tfCreatedDate.setText("");
		this.tfCreateCompanyName.setText("");
		this.tfListState.setText("");
		this.tfMemo.setText("");
	}

	/**
	 * 显示窗口上控件在不同编辑状态时的状态
	 */
	private void setState() {
		this.btnAdd.setEnabled(((dataState == DataState.BROWSE) && (jTabbedPane
				.getSelectedIndex() == 0))
				|| ((jTabbedPane.getSelectedIndex() == 1) && (!isTransferAtc)));
		this.btnEdit.setEnabled((dataState == DataState.BROWSE)
				&& (billListTableModel.getCurrentRow() != null)
				&& (!isTransferAtc));
		this.btnDelete.setEnabled((dataState == DataState.BROWSE)
				&& (!isTransferAtc));
		this.btnSave.setEnabled((!(dataState == DataState.BROWSE))
				&& (!isTransferAtc));
		this.btnCancel.setEnabled((!(dataState == DataState.BROWSE))
				&& (!isTransferAtc));
		this.btnDelete.setVisible(jTabbedPane.getSelectedIndex() == 1);
		this.btnSave.setVisible(jTabbedPane.getSelectedIndex() == 0);
		this.btnCancel.setVisible(jTabbedPane.getSelectedIndex() == 0);
		jCheckBox.setVisible(jTabbedPane.getSelectedIndex() == 1);
		// this.cbbImpExpType.setEnabled((!(dataState == DataState.BROWSE))
		// && (!isTransferAtc));
		this.cbbImpExpType.setEnabled((!(dataState == DataState.BROWSE))
				&& (!isTransferAtc)
				&& (dzscCustomsBillList == null ? true : dzscCustomsBillList
						.getMaterielProductFlag() == null));
		// this.tfBillListNo.setEditable((!(dataState == DataState.BROWSE))
		// && (!isTransferAtc));
		this.cbbListType.setEnabled((!(dataState == DataState.BROWSE))
				&& (!isTransferAtc));
		// 一旦料件成品标志确定后不能不修改
		this.cbbMaterialType.setEnabled((!(dataState == DataState.BROWSE))
				&& (!isTransferAtc)
				&& (dzscCustomsBillList == null ? true : dzscCustomsBillList
						.getMaterielProductFlag() == null));
		this.cbbDeclareCIQ.setEnabled((!(dataState == DataState.BROWSE))
				&& (!isTransferAtc));
		this.cbbImpExpCIQ.setEnabled((!(dataState == DataState.BROWSE))
				&& (!isTransferAtc));
		this.cbbTransportMode.setEnabled((!(dataState == DataState.BROWSE))
				&& (!isTransferAtc));
		this.cbbTradeMode.setEnabled((!(dataState == DataState.BROWSE))
				&& (!isTransferAtc));
		// this.tfCopEmsNo.setEditable((!(dataState == DataState.BROWSE))
		// && (!isTransferAtc));
		this.cbbImpExpDate.setEnabled((!(dataState == DataState.BROWSE))
				&& (!isTransferAtc));
		this.tfMemo.setEditable((!(dataState == DataState.BROWSE))
				&& (!isTransferAtc));
		// this.jTabbedPane.setEnabled(dataState == DataState.BROWSE);
	}

	/**
	 * 保存报关清单 *
	 */
	private void saveApplyToCustomsBillList() {
		if (!checkData()) {
			return;
		}
		fillBillListData(dzscCustomsBillList);
		dzscCustomsBillList = dzscListAction.saveApplyToCustomsBillList(
				new Request(CommonVars.getCurrUser()), dzscCustomsBillList);
		if (dataState == DataState.ADD) {
			billListTableModel.addRow(dzscCustomsBillList);
		} else if (dataState == DataState.EDIT) {
			billListTableModel.updateRow(dzscCustomsBillList);
		}
		dataState = DataState.BROWSE;
		setState();
	}

	private boolean checkData() {
		if (this.cbbImpExpType.getSelectedIndex() < 0) {
			JOptionPane.showMessageDialog(DgDzscCustomsBillList.this,
					"请选择进出口类型", "提示！", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		// if (this.tfBillListNo.getText().trim().equals("")) {
		// JOptionPane.showMessageDialog(DgDzscCustomsBillList.this,
		// "清单编号不能为空", "提示！", JOptionPane.INFORMATION_MESSAGE);
		// return false;
		// }
		if (this.cbbListType.getSelectedIndex() < 0) {
			JOptionPane.showMessageDialog(DgDzscCustomsBillList.this,
					"请选择清单类型！", "提示！", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (this.cbbMaterialType.getSelectedIndex() < 0) {
			JOptionPane.showMessageDialog(DgDzscCustomsBillList.this,
					"请选择料件成品标志！", "提示！", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		return true;
	}

	private void initBeforeTable(boolean isMerger) {
		DzscBillListAfterCommInfo afterCommInfo = null;
		if (afterCommInfoTableModel != null && afterCommInfoTableModel.getCurrentRow() != null) {
			afterCommInfo = (DzscBillListAfterCommInfo) afterCommInfoTableModel
					.getCurrentRow();
		}
		if (afterCommInfo == null) {
			beforeTableDataSource = new ArrayList();
		} else {
			if (!isMerger) {
				beforeTableDataSource = dzscListAction
						.findAtcMergeBeforeComInfoByAfterID(new Request(
								CommonVars.getCurrUser()), afterCommInfo);
			} else {
				beforeTableDataSource = dzscListAction
						.findAllAtcMergerBeforeComInfo(new Request(CommonVars
								.getCurrUser()), dzscCustomsBillList);
			}
			if (beforeTableDataSource == null
					|| beforeTableDataSource.size() < 0) {
				beforeTableDataSource = new ArrayList();
			}
		}
		final String materielType = ((afterCommInfo == null || afterCommInfo
				.getBillList().getMaterielProductFlag() == null) ? ""
				: afterCommInfo.getBillList().getMaterielProductFlag()
						.toString());
		beforeCommInfoTableModel = new JTableListModel(tbMergeBeforeCommInfo,
				beforeTableDataSource, new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("流水序号", "no", 70));
						list.add(addColumn("商品货号", "materiel.ptNo", 100));
						list
								.add(addColumn("商品名称", "materiel.factoryName",
										100));
						if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {
							list.add(addColumn("BOM版本", "bomVersion", 100));
						}
						list.add(addColumn("企业申报单价", "declaredPrice", 100));
						list.add(addColumn("申报数量", "declaredAmount", 100));
						list.add(addColumn("法定数量", "legalAmount", 100));
						list.add(addColumn("毛重", "grossWeight", 100));
						list.add(addColumn("净重", "netWeight", 100));
						return list;
					}
				});// ,ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
		// tbMergeBeforeCommInfo
		// .setSelectionMode();
	}

	private void initAfterTable() {
		if (dzscCustomsBillList == null) {
			afterTableDataSource = new ArrayList();
		} else {
			afterTableDataSource = dzscListAction
					.findAtcMergeAfterComInfoByListID(new Request(CommonVars
							.getCurrUser()), dzscCustomsBillList);
		}
		afterCommInfoTableModel = new JTableListModel(tbMergeAfterCommInfo,
				afterTableDataSource, new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("手册序号", "emsSerialNo", 70));
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("商品名称", "complexName", 100));
						list.add(addColumn("商品规格", "complexSpec", 100));
						list.add(addColumn("申报数量", "declaredAmount", 100));
						list.add(addColumn("法定数量", "legalAmount", 100));
						list.add(addColumn("毛重", "grossWeight", 100));
						list.add(addColumn("净重", "netWeight", 100));
						return list;
					}
				});
//		tbMergeAfterCommInfo.getColumnModel().getColumn(1).setCellRenderer(
//				new MultiRenderer());
	}
	
	class MultiRenderer extends DefaultTableCellRenderer {
		JCheckBox checkBox = new JCheckBox();

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (value == null) {
				checkBox.setSelected(false);
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
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				checkBox.setSelected(Boolean.parseBoolean(value.toString()));
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
			String str = (value == null) ? "" : value.toString();
			return super.getTableCellRendererComponent(table, str, isSelected,
					hasFocus, row, column);
		}
	}

	/**
	 * This method initializes tbMergeBeforeCommInfo
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbMergeBeforeCommInfo() {
		if (tbMergeBeforeCommInfo == null) {
			tbMergeBeforeCommInfo = new JTable();
			tbMergeBeforeCommInfo
					.addMouseListener(new java.awt.event.MouseAdapter() {
						public void mouseClicked(java.awt.event.MouseEvent e) {
							if (e.getClickCount() == 2) {
								if (beforeCommInfoTableModel.getCurrentRow() == null) {
									return;
								}
								DgDzscListCommInfo dgAtcMergeCommInfo = new DgDzscListCommInfo();
								dgAtcMergeCommInfo
										.setTableModel(beforeCommInfoTableModel);
								boolean isMakeCustomsDeclaration = dzscCustomsBillList
										.getIsGenerateDeclaration() == null ? false
										: dzscCustomsBillList
												.getIsGenerateDeclaration();
								dgAtcMergeCommInfo
										.setMakeCustomsDeclaration(isMakeCustomsDeclaration);
								dgAtcMergeCommInfo.setVisible(true);
								if (dgAtcMergeCommInfo.isOk()) {
									DzscBillListBeforeCommInfo beforeComInfo = (DzscBillListBeforeCommInfo) beforeCommInfoTableModel
											.getCurrentRow();
									afterCommInfoTableModel
											.updateRow(beforeComInfo
													.getAfterComInfo());
									// initAfterTable();
									// initBeforeTable();
								}
							}
						}
					});
		}
		return tbMergeBeforeCommInfo;
	}

	private void printReport() {
		List<DzscBillListAfterCommInfo> dsList = new ArrayList();
		List reDataSource = new ArrayList();
		if (afterCommInfoTableModel == null) {
			initAfterTable();
			initBeforeTable(isMerger);
		}
		dsList = afterCommInfoTableModel.getList();
		for (DzscBillListAfterCommInfo data : dsList) {
			reDataSource.addAll(dzscListAction
					.findAtcMergeBeforeComInfoByAfterID(new Request(CommonVars
							.getCurrUser()), data));
		}
		// dzscCustomsBillList
		CustomReportDataSource ds = new CustomReportDataSource(reDataSource);
		InputStream reportStream = DgDzscCustomsBillList.class
				.getResourceAsStream("report/DzscCustomsBillList.jasper");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("company.name", dzscCustomsBillList.getCompany()
				.getName());
		parameters.put("copEmsNo", dzscCustomsBillList.getCopEmsNo());
		Integer flag = dzscCustomsBillList.getCustomsImgExgFlag();

		// this.cbbMaterialType.addItem(new ItemProperty(MaterielType.MATERIEL,
		// "料件"));
		// this.cbbMaterialType.addItem(new ItemProperty(
		// MaterielType.FINISHED_PRODUCT, "成品"));
		if (flag == null) {
			parameters.put("customsImgExgFlag", "");
		} else {
			int in = flag.intValue();
			switch (in) {
			case 4:
				parameters.put("customsImgExgFlag", "成品");
				break;
			case 3:
				parameters.put("customsImgExgFlag", "料件");
				break;
			default:
				parameters.put("customsImgExgFlag", "");
				break;
			}
		}
		parameters.put("emsHeadH2k", dzscCustomsBillList.getEmsHeadH2k());
		parameters.put("impExpCIQ",
				dzscCustomsBillList.getImpExpCIQ() == null ? ""
						: dzscCustomsBillList.getImpExpCIQ().getName());
		Date impExpDate = dzscCustomsBillList.getImpExpDate();
		if (impExpDate == null) {
			parameters.put("impExpDate", null);
		} else {
			parameters.put("impExpDate", dateFormat.format(impExpDate));
		}
		Date listDeclareDate = dzscCustomsBillList.getListDeclareDate();
		if (listDeclareDate == null) {
			parameters.put("listDeclareDate", null);
		} else {
			parameters.put("listDeclareDate", dateFormat
					.format(listDeclareDate));
		}
		parameters.put("listNo", dzscCustomsBillList.getListNo());
		Integer type = dzscCustomsBillList.getListType();
		if (type == null) {
			parameters.put("listType", "");
		} else {
			switch (type) {
			case 0:
				parameters.put("listType", "一般报关");
				break;
			case 1:
				parameters.put("listType", "提前转关");
				break;
			case 2:
				parameters.put("listType", "二次转关");
				break;
			case 3:
				parameters.put("listType", "属地报关");
				break;

			default:
				break;
			}
		}

		parameters.put("tradeMode.name",
				dzscCustomsBillList.getTradeMode() == null ? ""
						: dzscCustomsBillList.getTradeMode().getName());

		JasperPrint jasperPrint = null;
		try {
			jasperPrint = JasperFillManager.fillReport(reportStream,
					parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbMergeBeforeCommInfo());
			jScrollPane1
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									null,
									"归并前商品信息（可修改）",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									null, null));
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setTopComponent(getJScrollPane());
			jSplitPane.setBottomComponent(getJScrollPane1());
			jSplitPane.setDividerLocation(120);
			jSplitPane.setDividerSize(8);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMachCode() {
		if (tfMachCode == null) {
			tfMachCode = new JTextField();
			tfMachCode.setBounds(new Rectangle(135, 102, 187, 22));
			tfMachCode.setText("");
			tfMachCode.setEditable(false);
		}
		return tfMachCode;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMachName() {
		if (tfMachName == null) {
			tfMachName = new JTextField();
			tfMachName.setBounds(new Rectangle(417, 102, 190, 22));
			tfMachName.setEditable(false);
			tfMachName.setText("");
		}
		return tfMachName;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbDeclareCIQ() {
		if (cbbDeclareCIQ == null) {
			cbbDeclareCIQ = new JComboBox();
			cbbDeclareCIQ.setBounds(new Rectangle(135, 184, 187, 22));
		}
		return cbbDeclareCIQ;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpExpCIQ() {
		if (cbbImpExpCIQ == null) {
			cbbImpExpCIQ = new JComboBox();
			cbbImpExpCIQ.setBounds(new Rectangle(417, 185, 190, 22));
		}
		return cbbImpExpCIQ;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCopEmsNo() {
		if (tfCopEmsNo == null) {
			tfCopEmsNo = new JTextField();
			tfCopEmsNo.setBounds(new Rectangle(417, 47, 190, 22));
			tfCopEmsNo.setEditable(false);
		}
		return tfCopEmsNo;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbImpExpDate() {
		if (cbbImpExpDate == null) {
			cbbImpExpDate = new JCalendarComboBox();
			cbbImpExpDate.setBounds(new Rectangle(417, 128, 190, 22));
		}
		return cbbImpExpDate;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCustomerOrderCode() {
		if (tfCustomerOrderCode == null) {
			tfCustomerOrderCode = new JTextField();
			tfCustomerOrderCode.setBounds(new Rectangle(135, 324, 187, 24));
		}
		return tfCustomerOrderCode;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInvoiceCode() {
		if (tfInvoiceCode == null) {
			tfInvoiceCode = new JTextField();
			tfInvoiceCode.setBounds(new Rectangle(135, 297, 187, 23));
		}
		return tfInvoiceCode;
	}

	/**
	 * This method initializes cbbTradeMode1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbListType() {
		if (cbbListType == null) {
			cbbListType = new JComboBox();
			cbbListType.setBounds(new Rectangle(417, 268, 190, 23));
		}
		return cbbListType;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCustomsSeqNo() {
		if (tfCustomsSeqNo == null) {
			tfCustomsSeqNo = new JTextField();
			tfCustomsSeqNo.setBounds(new Rectangle(417, 323, 190, 22));
			tfCustomsSeqNo.setEditable(false);
		}
		return tfCustomsSeqNo;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCreatedDate() {
		if (tfCreatedDate == null) {
			tfCreatedDate = new JTextField();
			tfCreatedDate.setBounds(new Rectangle(417, 241, 190, 21));
			tfCreatedDate.setEditable(false);
		}
		return tfCreatedDate;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfListDeclareDate() {
		if (tfListDeclareDate == null) {
			tfListDeclareDate = new JTextField();
			tfListDeclareDate.setBounds(new Rectangle(135, 129, 187, 23));
			tfListDeclareDate.setEditable(false);
		}
		return tfListDeclareDate;
	}

	/**
	 * This method initializes btnPrint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					printReport();
				}
			});
		}
		return btnPrint;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
