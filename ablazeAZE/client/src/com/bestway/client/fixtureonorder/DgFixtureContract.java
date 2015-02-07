/*
 * Created on 2005-3-23
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.fixtureonorder;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.Renderer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonQueryPage;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.bcus.custombase.entity.basecode.InvestMode;
import com.bestway.bcus.custombase.entity.basecode.MachiningType;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.FetchInMode;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.PayMode;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.ContractKind;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.CurrRate;
import com.bestway.fixtureonorder.action.CheckFixAuthorityAction;
import com.bestway.fixtureonorder.action.FixtureContractAction;
import com.bestway.fixtureonorder.entity.FixtureContract;
import com.bestway.fixtureonorder.entity.FixtureContractItems;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.Dimension;
import java.awt.Rectangle;

/**
 * @author fhz  设备管理
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 *         edit by 陈井彬
 */
public class DgFixtureContract extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnPrint = null;

	private JComboBox cbbPrint = null;

	private JButton btnSave = null;

	private JButton btnUndo = null;

	private JButton btnExit = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel pn1 = null;

	private JPanel pn3 = null;

	private JLabel jLabel2 = null;

	private JTextField tfDeclareState = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JComboBox cbbDeclareCustoms = null;

	private JLabel jLabel6 = null;

	private JTextField tfEmsNo = null;

	private JLabel jLabel7 = null; // @jve:decl-index=0:

	private JComboBox cbbIePort = null;

	private JLabel jLabel8 = null; // @jve:decl-index=0:

	private JLabel jLabel9 = null; // @jve:decl-index=0:

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbAvailabilityDate = null;

	private JLabel jLabel10 = null;

	private JLabel jLabel11 = null;

	private JComboBox cbbTrade = null;

	private JLabel jLabel12 = null;

	private JTextField tfMachName = null;

	private JLabel jLabel13 = null;

	private JComboBox cbbTradeCountry = null;

	private JLabel jLabel14 = null;

	private JCalendarComboBox cbbDeferDate = null;

	private JLabel jLabel15 = null;

	private JTextField tfMachCode = null;

	private JLabel jLabel16 = null;

	private JTextField tfEnterpriseAddress = null;

	private JLabel jLabel18 = null;

	private JTextField tfLinkMan = null;

	private JLabel jLabel19 = null;

	private JTextField tfContactTel = null;

	private JLabel jLabel20 = null;

	private JComboBox cbbLevyKind = null;

	private JLabel jLabel21 = null;

	private JTextField tfOutTradeCo = null;

	private JLabel jLabel22 = null;

	private JTextField tfSancEmsNo = null;

	private JLabel jLabel23 = null;

	private JTextField tfAgreementNo = null;

	private JLabel jLabel24 = null;

	private JTextField tfImpContractNo = null;

	private JLabel jLabel25 = null;

	private JTextField tfExpContractNo = null;

	private JLabel jLabel26 = null;

	private JLabel jLabel28 = null;

	private JComboBox cbbCurr = null;

	private JLabel jLabel29 = null;

	private JComboBox cbbWardshipRate = null;

	private JLabel jLabel30 = null;

	private JLabel jLabel31 = null;

	private JComboBox cbbTransac = null;

	private JLabel jLabel32 = null;

	private JComboBox cbbIePort2 = null;

	private JLabel jLabel33 = null;

	private JComboBox cbbIePort3 = null;

	private JLabel jLabel34 = null;

	private JComboBox cbbIePort4 = null;

	private JLabel jLabel35 = null;

	private JComboBox cbbIePort5 = null;

	private JLabel jLabel36 = null;

	private JTextField tfApprover = null;

	private JLabel jLabel37 = null;

	private JCalendarComboBox cbbApproveDate = null;

	private JLabel jLabel38 = null;

	private JComboBox cbbPayMode = null;

	private JLabel jLabel41 = null;

	private JTextField tfMemo = null;

	private JFormattedTextField tfImgAmount = null;

	private JFormattedTextField tfWardshipFee = null;

	private JLabel jLabel = null;

	private JToolBar jToolBar3 = null;

	private JTable tbMateriel = null;

	private JScrollPane jScrollPane2 = null;

	private JButton btnChangingContractItemsNo = null;

	private JButton btnContractItemsSort = null;

	private JButton btnAmountToInteger = null;

	private JButton btnEditContractItems = null;

	private JLabel jLabel43 = null;

	private FixtureContractAction fixtureContractAction = null; // 合同

	private CheckFixAuthorityAction checkFixAuthorityAction = null;

	private JTableListModel tableModelContract = null; // 表头

	private JTableListModel tableModelItem = null; // 合同设备

	private FixtureContract fixtureContract = null; // 合同对象

	private int dataState = DataState.BROWSE;

	private JPanel jPanel1 = null;

	private MaterialManageAction materialManageAction = null;

	private JButton btnAddContractItems = null;

	private JButton btnDeleteContractItems = null;

	private JButton btnEdit = null;

	private JComboBox cbbTradeCompany = null;

	private SystemAction systemAction = (SystemAction) CommonVars
			.getApplicationContext().getBean("systemAction");

	private JLabel jLabel45 = null;

	private JLabel jLabel51 = null;

	private JLabel jLabel52 = null;

	private JLabel jLabel53 = null;

	private JLabel jLabel54 = null;

	private JLabel jLabel55 = null;

	private JTextField tfFirstTrialer = null;

	private JCalendarComboBox cbbSaveDate = null;

	private JCalendarComboBox cbbChangeDate = null;

	private JCustomFormattedTextField tfMaterielItemCount = null;

	private JTextField tfEmphasisFlag = null;

	private JTextField tfAclUserName = null;

	private JButton btnChangeItemComplex = null;

	private JButton btnChangeContractItemslNameSpec = null;

	private JTextField tfProperty = null;

	private String titleString = "设备协议";

	private JButton btnContractImport = null;

	private JButton btnSerch = null;

	/**
	 * @throws java.awt.HeadlessException
	 */
	public DgFixtureContract() {
		super();
		fixtureContractAction = (FixtureContractAction) CommonVars
				.getApplicationContext().getBean("fixtureContractAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		checkFixAuthorityAction = (CheckFixAuthorityAction) CommonVars
				.getApplicationContext().getBean("checkFixAuthorityAction");

		initialize();
		initUIComponents();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(763, 511);
		// this.setResizable(false);
		this.setContentPane(getJContentPane());
		this.setHelpId("");
	}

	public void setVisible(boolean isFlag) {
		if (isFlag == true) {
			setTitle(titleString);
			showData();
			setState();
		}
		super.setVisible(isFlag);
	}

	/**
	 * @param dataState
	 *            The dataState to set.
	 */
	public void setDataState(int dataState) {
		this.dataState = dataState;
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
			jLabel = new JLabel();
			jToolBar = new JToolBar();
			jLabel
					.setText("                                                                      ");
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnUndo());
			jToolBar.add(getJPanel1());
			jToolBar.add(getJButton());
			jToolBar.add(getBtnExit());
			jToolBar.add(jLabel);
		}
		return jToolBar;
	}

	/**
	 * This method initializes btnAddFinishProduct
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印表格");
			btnPrint.setBounds(189, 0, 83, 24);
			btnPrint.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					printReport();
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
	private JComboBox getCbbPrint() {
		if (cbbPrint == null) {
			cbbPrint = new JComboBox();
			cbbPrint.setBounds(9, 0, 175, 24);
		}
		return cbbPrint;
	}

	/**
	 * This method initializes btnAddUnitWaste
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					saveData();
				}

			});
		}
		return btnSave;
	}

	private void saveData() {
		if (dataState == DataState.ADD) {
			setPreContractCode();
		}
		if (fixtureContract != null) {
			String emsNo = fixtureContract.getEmsNo();
			FixtureContract c = this.fixtureContractAction.findContractById(
					new Request(CommonVars.getCurrUser()), fixtureContract
							.getId());
			if (c == null) {
				JOptionPane.showMessageDialog(this,
						"合同 " + emsNo + " 已经删除不能修改", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if (c != null
					&& c.getDeclareState().equals(DeclareState.PROCESS_EXE)) {
				JOptionPane.showMessageDialog(this,
						"合同 " + emsNo + " 已经生效不能修改", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
		fillData();
		if (!DeclareState.CHANGING_EXE
				.equals(fixtureContract.getDeclareState())) {
			if (fixtureContractAction.isExistContractByEmsNo(new Request(
					CommonVars.getCurrUser()), fixtureContract)) {
				JOptionPane.showMessageDialog(DgFixtureContract.this,
						"合同手册编号重复!!!", "提示", JOptionPane.INFORMATION_MESSAGE);
				// return;
			}
		}
		fixtureContract = fixtureContractAction.saveContract(new Request(
				CommonVars.getCurrUser()), fixtureContract);
		if (dataState == DataState.ADD) {
			tableModelContract.addRow(fixtureContract);
			// dataState = DataState.EDIT;
		} else {
			tableModelContract.updateRow(fixtureContract);
		}
		dataState = DataState.BROWSE;
		setState();
		// setContainerEnabled(pn1, false);
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUndo() {
		if (btnUndo == null) {
			btnUndo = new JButton();
			btnUndo.setText("放弃");
			btnUndo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					showData();
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnUndo;
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgFixtureContract.this.dispose();
				}
			});
		}
		return btnExit;
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
			jTabbedPane.addTab("基本情况", null, getPn1(), null);
			jTabbedPane.addTab("设备总表", null, getPn3(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							JTabbedPane tab = (JTabbedPane) e.getSource();
							List list = new ArrayList();
							if (tab.getSelectedIndex() == 0) {
								if (fixtureContract != null) {
									if (!DeclareState.PROCESS_EXE
											.equals(fixtureContract
													.getDeclareState())) {
										dataState = DataState.EDIT;
										setState();
									}
									Double imgAmount = fixtureContractAction
											.getItemAmountSum(new Request(
													CommonVars.getCurrUser()),
													fixtureContract.getId());
									int materielItemCount = fixtureContractAction
											.findContractItemCount(new Request(
													CommonVars.getCurrUser()),
													fixtureContract.getId());
									int productItemCount = 0;
									tfMaterielItemCount
											.setValue(materielItemCount);

									tfImgAmount.setValue(imgAmount);
									fixtureContract
											.setMaterielItemCount(materielItemCount);
									fixtureContract
											.setProductItemCount(productItemCount);
									fixtureContract.setFixtureAmount(imgAmount);
									fixtureContract = fixtureContractAction
											.saveContract(new Request(
													CommonVars.getCurrUser()),
													fixtureContract);
									tableModelContract
											.updateRow(fixtureContract);
								}
							} else if (tab.getSelectedIndex() == 1) {
								if (dataState != DataState.BROWSE) {
									saveData();
								}
								if (fixtureContract != null) {
									String parentId = fixtureContract.getId();
									list = fixtureContractAction
											.findContractItemByParentId(
													new Request(CommonVars
															.getCurrUser()),
													parentId);
								}
								initTbContractItems(list);
							}

							setState();
						}
					});
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn1() {
		if (pn1 == null) {
			jLabel55 = new JLabel();
			jLabel55.setBounds(new Rectangle(480, 300, 61, 20));
			jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel55.setText("设备项数");
			jLabel54 = new JLabel();
			jLabel54.setBounds(new Rectangle(477, 106, 61, 20));
			jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel54.setText("输入时间");
			jLabel53 = new JLabel();
			jLabel53.setBounds(new Rectangle(263, 298, 61, 20));
			jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel53.setText("输入人");
			jLabel52 = new JLabel();
			jLabel52.setBounds(new Rectangle(486, 85, 53, 20));
			jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel52.setText("变更日期");
			jLabel51 = new JLabel();
			jLabel51.setBounds(new Rectangle(262, 213, 61, 20));
			jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel51.setText("重点标志");
			jLabel45 = new JLabel();
			jLabel45.setBounds(new Rectangle(48, 299, 61, 20));
			jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel45.setText("初审人");
			jLabel41 = new JLabel();
			jLabel38 = new JLabel();
			jLabel37 = new JLabel();
			jLabel36 = new JLabel();
			jLabel35 = new JLabel();
			jLabel34 = new JLabel();
			jLabel33 = new JLabel();
			jLabel32 = new JLabel();
			jLabel31 = new JLabel();
			jLabel30 = new JLabel();
			jLabel29 = new JLabel();
			jLabel28 = new JLabel();
			jLabel26 = new JLabel();
			jLabel25 = new JLabel();
			jLabel24 = new JLabel();
			jLabel23 = new JLabel();
			jLabel22 = new JLabel();
			jLabel21 = new JLabel();
			jLabel20 = new JLabel();
			jLabel19 = new JLabel();
			jLabel18 = new JLabel();
			jLabel16 = new JLabel();
			jLabel15 = new JLabel();
			jLabel14 = new JLabel();
			jLabel13 = new JLabel();
			jLabel12 = new JLabel();
			jLabel11 = new JLabel();
			jLabel10 = new JLabel();
			jLabel9 = new JLabel();
			jLabel8 = new JLabel();
			jLabel7 = new JLabel();
			jLabel6 = new JLabel();
			jLabel5 = new JLabel();
			jLabel4 = new JLabel();
			jLabel2 = new JLabel();
			pn1 = new JPanel();
			pn1.setLayout(null);
			jLabel2.setBounds(57, 44, 52, 20);
			jLabel2.setText("合同状态");
			jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel4.setBounds(49, 22, 60, 20);
			jLabel4.setForeground(java.awt.Color.blue);
			jLabel4.setText("申报关区号");
			jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel5.setBounds(272, 22, 52, 20);
			jLabel5.setForeground(java.awt.Color.blue);
			jLabel5.setText("合同性质");
			jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel6.setBounds(57, 65, 52, 20);
			jLabel6.setText("手册编号");
			jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel6.setForeground(java.awt.Color.blue);
			jLabel7.setBounds(272, 65, 52, 20);
			jLabel7.setText("进出口岸");
			jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel7.setForeground(java.awt.Color.blue);
			jLabel8.setBounds(486, 22, 52, 20);
			jLabel8.setForeground(java.awt.Color.blue);
			jLabel8.setText("起始日期");
			jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel9.setBounds(486, 43, 52, 20);
			jLabel9.setText("有效日期");
			jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel10.setBounds(58, 86, 51, 20);
			jLabel10.setText("经营单位");
			jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel10.setForeground(java.awt.Color.blue);
			jLabel11.setBounds(272, 86, 52, 20);
			jLabel11.setText("贸易方式");
			jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel11.setForeground(java.awt.Color.blue);
			jLabel12.setBounds(48, 107, 61, 20);
			jLabel12.setText("收货单位");
			jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel12.setForeground(java.awt.Color.blue);
			jLabel13.setBounds(261, 107, 63, 20);
			jLabel13.setForeground(java.awt.Color.blue);
			jLabel13.setText("贸易国别");
			jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel13
					.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel14.setBounds(486, 64, 52, 20);
			jLabel14.setText("延期期限");
			jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel15.setBounds(29, 128, 80, 20);
			jLabel15.setText("收货单位编码");
			jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel16.setBounds(272, 128, 52, 20);
			jLabel16.setText("企业地址");
			jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel18.setBounds(69, 149, 40, 20);
			jLabel18.setForeground(java.awt.Color.blue);
			jLabel18.setText("联系人");
			jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel19.setBounds(272, 149, 52, 20);
			jLabel19.setForeground(java.awt.Color.blue);
			jLabel19.setText("联系电话");
			jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel20.setBounds(488, 150, 52, 20);
			jLabel20.setForeground(java.awt.Color.blue);
			jLabel20.setText("征免性质");
			jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel21.setBounds(48, 170, 61, 20);
			jLabel21.setForeground(java.awt.Color.blue);
			jLabel21.setText("外商公司");
			jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel22.setBounds(489, 170, 52, 20);
			jLabel22.setText("批文号");
			jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel22.setForeground(java.awt.Color.blue);
			jLabel23.setBounds(49, 192, 60, 20);
			jLabel23.setText("协议书号");
			jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel23.setForeground(java.awt.Color.blue);
			jLabel24.setBounds(256, 192, 68, 20);
			jLabel24.setForeground(java.awt.Color.blue);
			jLabel24.setText("进口合同号");
			jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel25.setBounds(467, 192, 74, 20);
			jLabel25.setText("出口合同号");
			jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel26.setBounds(54, 213, 55, 20);
			jLabel26.setForeground(java.awt.Color.blue);
			jLabel26.setText("设备总值");
			jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel28.setBounds(489, 213, 52, 20);
			jLabel28.setForeground(java.awt.Color.blue);
			jLabel28.setText("币制");
			jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel29.setBounds(46, 234, 63, 20);
			jLabel29.setText("监管费率");
			jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel30.setBounds(272, 234, 52, 20);
			jLabel30.setText("监管费");
			jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel31.setBounds(489, 234, 52, 20);
			jLabel31.setText("成交方式");
			jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel31.setForeground(java.awt.Color.blue);
			jLabel32.setBounds(48, 257, 61, 20);
			jLabel32.setText("进出口岸2");
			jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel33.setBounds(261, 257, 63, 20);
			jLabel33.setText("进出口岸3");
			jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel34.setBounds(479, 257, 62, 20);
			jLabel34.setText("进出口岸4");
			jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel35.setBounds(48, 278, 61, 20);
			jLabel35.setText("进出口岸5");
			jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel36.setBounds(272, 278, 52, 20);
			jLabel36.setText("审批人");
			jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel37.setBounds(489, 278, 52, 20);
			jLabel37.setText("审批日期");
			jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel38.setBounds(481, 128, 61, 20);
			jLabel38.setText("保税方式");
			jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel41.setBounds(45, 322, 61, 20);
			jLabel41.setText("备注");
			jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			pn1.add(jLabel2, null);
			pn1.add(getTfDeclareState(), null);
			pn1.add(jLabel4, null);
			pn1.add(jLabel5, null);
			pn1.add(getCbbDeclareCustoms(), null);
			pn1.add(jLabel6, null);
			pn1.add(getTfEmsNo(), null);
			pn1.add(jLabel7, null);
			pn1.add(getCbbIePort(), null);
			pn1.add(jLabel8, null);
			pn1.add(jLabel9, null);
			pn1.add(getCbbBeginDate(), null);
			pn1.add(getCbbAvailabilityDate(), null);
			pn1.add(jLabel10, null);
			pn1.add(jLabel11, null);
			pn1.add(getCbbTrade(), null);
			pn1.add(jLabel12, null);
			pn1.add(getTfMachName(), null);
			pn1.add(jLabel13, null);
			pn1.add(getCbbTradeCountry(), null);
			pn1.add(jLabel14, null);
			pn1.add(getCbbDeferDate(), null);
			pn1.add(jLabel15, null);
			pn1.add(getTfMachCode(), null);
			pn1.add(jLabel16, null);
			pn1.add(getTfEnterpriseAddress(), null);
			pn1.add(jLabel18, null);
			pn1.add(getTfLinkMan(), null);
			pn1.add(jLabel19, null);
			pn1.add(getJTextField7(), null);
			pn1.add(jLabel20, null);
			pn1.add(getCbbLevyKind(), null);
			pn1.add(jLabel21, null);
			pn1.add(getTfOutTradeCo(), null);
			pn1.add(jLabel22, null);
			pn1.add(getTfSancEmsNo(), null);
			pn1.add(jLabel23, null);
			pn1.add(getTfAgreementNo(), null);
			pn1.add(jLabel24, null);
			pn1.add(getTfImpContractNo(), null);
			pn1.add(jLabel25, null);
			pn1.add(getTfExpContractNo(), null);
			pn1.add(jLabel26, null);
			pn1.add(jLabel28, null);
			pn1.add(getCbbCurr(), null);
			pn1.add(jLabel29, null);
			pn1.add(getCbbWardshipRate(), null);
			pn1.add(jLabel30, null);
			pn1.add(jLabel31, null);
			pn1.add(getCbbTransac(), null);
			pn1.add(jLabel32, null);
			pn1.add(getCbbIePort2(), null);
			pn1.add(jLabel33, null);
			pn1.add(getCbbIePort3(), null);
			pn1.add(jLabel34, null);
			pn1.add(getCbbIePort4(), null);
			pn1.add(jLabel35, null);
			pn1.add(getCbbIePort5(), null);
			pn1.add(jLabel36, null);
			pn1.add(getTfApprover(), null);
			pn1.add(jLabel37, null);
			pn1.add(getCbbApproveDate(), null);
			pn1.add(jLabel38, null);
			pn1.add(getCbbPayMode(), null);
			pn1.add(jLabel41, null);
			pn1.add(getTfMemo(), null);
			pn1.add(getTfImgAmount(), null);
			pn1.add(getTfWardshipFee(), null);
			pn1.add(getCbbTradeCompany(), null);
			pn1.add(jLabel45, null);
			pn1.add(jLabel51, null);
			pn1.add(jLabel52, null);
			pn1.add(jLabel53, null);
			pn1.add(jLabel54, null);
			pn1.add(jLabel55, null);
			pn1.add(getTfFirstTrialer(), null);
			pn1.add(getCbbSaveDate(), null);
			pn1.add(getCbbChangeDate(), null);
			pn1.add(getTfMaterielItemCount(), null);
			pn1.add(getTfEmphasisFlag(), null);
			pn1.add(getTfAclUserName(), null);
			pn1.add(getTfProperty(), null);
		}
		return pn1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn3() {
		if (pn3 == null) {
			pn3 = new JPanel();
			pn3.setLayout(new BorderLayout());
			pn3.add(getJToolBar3(), java.awt.BorderLayout.NORTH);
			pn3.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
		}
		return pn3;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfDeclareState() {
		if (tfDeclareState == null) {
			tfDeclareState = new JTextField();
			tfDeclareState.setEditable(false);
			tfDeclareState.setBounds(112, 44, 341, 20);
		}
		return tfDeclareState;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbDeclareCustoms() {
		if (cbbDeclareCustoms == null) {
			cbbDeclareCustoms = new JComboBox();
			cbbDeclareCustoms.setBounds(112, 22, 127, 20);
			cbbDeclareCustoms
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							if (e.getStateChange() == ItemEvent.SELECTED) {
								setPreContractCode();
							}
						}
					});
		}
		return cbbDeclareCustoms;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setBounds(112, 65, 127, 20);
		}
		return tfEmsNo;
	}

	/**
	 * This method initializes jComboBox4
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbIePort() {
		if (cbbIePort == null) {
			cbbIePort = new JComboBox();
			cbbIePort.setBounds(326, 65, 127, 20);
		}
		return cbbIePort;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(543, 22, 127, 20);
			cbbBeginDate.addChangeListener(new ChangeListener() {

				public void stateChanged(ChangeEvent arg0) {
					Calendar beginDate = cbbBeginDate.getCalendar();
					Calendar endDate = null;
					if (beginDate != null) {
						endDate = (Calendar) beginDate.clone();
						endDate.add(Calendar.YEAR, 5);

					}
					cbbAvailabilityDate.setCalendar(endDate);
					// cbbDestroyDate.setCalendar(destroyDate);
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
	private JCalendarComboBox getCbbAvailabilityDate() {
		if (cbbAvailabilityDate == null) {
			cbbAvailabilityDate = new JCalendarComboBox();
			cbbAvailabilityDate.setBounds(543, 43, 127, 20);
		}
		return cbbAvailabilityDate;
	}

	/**
	 * This method initializes jComboBox5
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTrade() {
		if (cbbTrade == null) {
			cbbTrade = new JComboBox();
			cbbTrade.setBounds(326, 86, 127, 20);
		}
		return cbbTrade;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMachName() {
		if (tfMachName == null) {
			tfMachName = new JTextField();
			tfMachName.setEditable(false);
			tfMachName.setBounds(112, 107, 127, 20);
		}
		return tfMachName;
	}

	/**
	 * This method initializes jComboBox7
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTradeCountry() {
		if (cbbTradeCountry == null) {
			cbbTradeCountry = new JComboBox();
			cbbTradeCountry.setBounds(326, 107, 127, 20);
		}
		return cbbTradeCountry;
	}

	/**
	 * This method initializes jCalendarComboBox2
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbDeferDate() {
		if (cbbDeferDate == null) {
			cbbDeferDate = new JCalendarComboBox();
			cbbDeferDate.setBounds(543, 64, 127, 20);
		}
		return cbbDeferDate;
	}

	/**
	 * This method initializes jTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMachCode() {
		if (tfMachCode == null) {
			tfMachCode = new JTextField();
			tfMachCode.setEditable(false);
			tfMachCode.setBounds(112, 128, 127, 20);
		}
		return tfMachCode;
	}

	/**
	 * This method initializes jTextField5
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEnterpriseAddress() {
		if (tfEnterpriseAddress == null) {
			tfEnterpriseAddress = new JTextField();
			tfEnterpriseAddress.setBounds(326, 128, 127, 20);
		}
		return tfEnterpriseAddress;
	}

	/**
	 * This method initializes jTextField6
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfLinkMan() {
		if (tfLinkMan == null) {
			tfLinkMan = new JTextField();
			tfLinkMan.setBounds(112, 149, 127, 20);
		}
		return tfLinkMan;
	}

	/**
	 * This method initializes jTextField7
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField7() {
		if (tfContactTel == null) {
			tfContactTel = new JTextField();
			tfContactTel.setBounds(326, 149, 127, 20);
		}
		return tfContactTel;
	}

	/**
	 * This method initializes jComboBox8
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbLevyKind() {
		if (cbbLevyKind == null) {
			cbbLevyKind = new JComboBox();
			cbbLevyKind.setBounds(545, 150, 127, 20);
		}
		return cbbLevyKind;
	}

	/**
	 * This method initializes jTextField8
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfOutTradeCo() {
		if (tfOutTradeCo == null) {
			tfOutTradeCo = new JTextField();
			tfOutTradeCo.setBounds(112, 170, 341, 20);
		}
		return tfOutTradeCo;
	}

	/**
	 * This method initializes jTextField9
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSancEmsNo() {
		if (tfSancEmsNo == null) {
			tfSancEmsNo = new JTextField();
			tfSancEmsNo.setBounds(546, 170, 127, 20);
		}
		return tfSancEmsNo;
	}

	/**
	 * This method initializes jTextField10
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfAgreementNo() {
		if (tfAgreementNo == null) {
			tfAgreementNo = new JTextField();
			tfAgreementNo.setBounds(112, 192, 127, 20);
		}
		return tfAgreementNo;
	}

	/**
	 * This method initializes jTextField11
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfImpContractNo() {
		if (tfImpContractNo == null) {
			tfImpContractNo = new JTextField();
			tfImpContractNo.setBounds(326, 192, 127, 20);
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
			tfExpContractNo.setBounds(546, 192, 127, 20);
		}
		return tfExpContractNo;
	}

	/**
	 * This method initializes jComboBox9
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCurr() {
		if (cbbCurr == null) {
			cbbCurr = new JComboBox();
			cbbCurr.setBounds(546, 213, 127, 20);
		}
		return cbbCurr;
	}

	/**
	 * This method initializes jComboBox10
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbWardshipRate() {
		if (cbbWardshipRate == null) {
			cbbWardshipRate = new JComboBox();
			cbbWardshipRate.setBounds(112, 234, 127, 20);
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
	private JComboBox getCbbTransac() {
		if (cbbTransac == null) {
			cbbTransac = new JComboBox();
			cbbTransac.setBounds(546, 234, 127, 20);
		}
		return cbbTransac;
	}

	/**
	 * This method initializes jComboBox12
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbIePort2() {
		if (cbbIePort2 == null) {
			cbbIePort2 = new JComboBox();
			cbbIePort2.setBounds(112, 257, 127, 20);
		}
		return cbbIePort2;
	}

	/**
	 * This method initializes jComboBox13
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbIePort3() {
		if (cbbIePort3 == null) {
			cbbIePort3 = new JComboBox();
			cbbIePort3.setBounds(326, 257, 127, 20);
		}
		return cbbIePort3;
	}

	/**
	 * This method initializes jComboBox14
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbIePort4() {
		if (cbbIePort4 == null) {
			cbbIePort4 = new JComboBox();
			cbbIePort4.setBounds(546, 257, 127, 20);
		}
		return cbbIePort4;
	}

	/**
	 * This method initializes jComboBox15
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbIePort5() {
		if (cbbIePort5 == null) {
			cbbIePort5 = new JComboBox();
			cbbIePort5.setBounds(112, 278, 127, 20);
		}
		return cbbIePort5;
	}

	/**
	 * This method initializes jTextField16
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfApprover() {
		if (tfApprover == null) {
			tfApprover = new JTextField();
			tfApprover.setBounds(326, 278, 127, 20);
		}
		return tfApprover;
	}

	/**
	 * This method initializes jCalendarComboBox4
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbApproveDate() {
		if (cbbApproveDate == null) {
			cbbApproveDate = new JCalendarComboBox();
			cbbApproveDate.setBounds(546, 278, 127, 20);
		}
		return cbbApproveDate;
	}

	/**
	 * This method initializes jComboBox16
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbPayMode() {
		if (cbbPayMode == null) {
			cbbPayMode = new JComboBox();
			cbbPayMode.setBounds(545, 128, 127, 20);
		}
		return cbbPayMode;
	}

	/**
	 * This method initializes jTextField17
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMemo() {
		if (tfMemo == null) {
			tfMemo = new JTextField();
			tfMemo.setBounds(111, 322, 563, 20);
		}
		return tfMemo;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfImgAmount() {
		if (tfImgAmount == null) {
			tfImgAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfImgAmount.setBounds(112, 213, 127, 20);
			tfImgAmount.setValue(new Double(0));
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
	 * This method initializes jFormattedTextField2
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfWardshipFee() {
		if (tfWardshipFee == null) {
			tfWardshipFee = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfWardshipFee.setBounds(326, 234, 127, 20);
			tfWardshipFee.setValue(new Double(0));
		}
		return tfWardshipFee;
	}

	/**
	 * @return Returns the tableModelContract.
	 */
	public JTableListModel getTableModelContract() {
		return tableModelContract;
	}

	/**
	 * @param tableModelContract
	 *            The tableModelContract to set.
	 */
	public void setTableModelContract(JTableListModel tableModelHead) {
		this.tableModelContract = tableModelHead;
	}

	/**
	 * This method initializes jToolBar3
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar3() {
		if (jToolBar3 == null) {
			jLabel43 = new JLabel();
			jToolBar3 = new JToolBar();
			jLabel43.setText("                 合同设备汇总表          ");
			jLabel43.setForeground(new java.awt.Color(227, 145, 0));
			jToolBar3.add(getBtnAddContractItems());
			jToolBar3.add(getBtnChangeItemComplex());
			jToolBar3.add(getBtnChangeContractItemslNameSpec());
			jToolBar3.add(getBtnEditContractItems());
			jToolBar3.add(getJButton2());
			jToolBar3.add(getBtnChangingContractItemsNo());
			jToolBar3.add(getBtnContractItemsSort());
			jToolBar3.add(getBtnAmountToInteger());
			jToolBar3.add(getBtnSerch());
			jToolBar3.add(jLabel43);
		}
		return jToolBar3;
	}

	/**
	 * This method initializes jTable2
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbMateriel() {
		if (tbMateriel == null) {
			tbMateriel = new JTable();
			tbMateriel.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbMateriel
									.getModel();
							if (tableModel == null) {
								return;
							}
							ListSelectionModel lsm = (ListSelectionModel) e
									.getSource();
							if (!lsm.isSelectionEmpty()) {

							}
						}
					});
		}
		return tbMateriel;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getTbMateriel());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jButton12
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChangingContractItemsNo() {
		if (btnChangingContractItemsNo == null) {
			btnChangingContractItemsNo = new JButton();
			btnChangingContractItemsNo.setText("变更序号");
			btnChangingContractItemsNo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							checkFixAuthorityAction
									.checkChangingContractItemsNo(new Request(
											CommonVars.getCurrUser()));
							if (tableModelItem == null
									|| tableModelItem.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										DgFixtureContract.this,
										"请选择要变更的设备记录!!!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							DgChangeItemsSeqNum dg = new DgChangeItemsSeqNum();
							dg.setTableModel(tableModelItem);
							dg.setVisible(true);
							if (dg.isOk()) {
								if (fixtureContract != null) {
									String parentId = fixtureContract.getId();
									List list = fixtureContractAction
											.findContractItemByParentId(
													new Request(CommonVars
															.getCurrUser()),
													parentId);
									initTbContractItems(list);
								}
							}
						}
					});
		}
		return btnChangingContractItemsNo;
	}

	/**
	 * This method initializes jButton13
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnContractItemsSort() {
		if (btnContractItemsSort == null) {
			btnContractItemsSort = new JButton();
			btnContractItemsSort.setText("设备自由排序");
			btnContractItemsSort
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							checkFixAuthorityAction
									.checkContractItemsSort(new Request(
											CommonVars.getCurrUser()));
							if (tableModelItem == null
									|| tableModelItem.getList().size() <= 0) {
								return;
							}
							List list = tableModelItem.getList();
							if (list.size() <= 0) {
								return;
							}
							if (JOptionPane.showConfirmDialog(
									DgFixtureContract.this, "是否将设备排序!!!", "提示",
									JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
								Vector vet = new Vector();
								int size = tableModelItem.getSize();
								for (int i = 0; i < size; i++) {
									vet.add(tableModelItem.getDataByRow(i));
								}
								DgFixtureContractItemsSort dg = new DgFixtureContractItemsSort();
								dg.setList(vet);
								dg.setContractAction(fixtureContractAction);
								dg.setVisible(true);
								if (dg.isOk()) {
									list = dg.getList();
									tableModelItem.updateRows(list);
								}
								if (fixtureContract != null) {
									String parentId = fixtureContract.getId();
									list = fixtureContractAction
											.findContractItemByParentId(
													new Request(CommonVars
															.getCurrUser()),
													parentId);
									initTbContractItems(list);
								}
							}
						}
					});
		}
		return btnContractItemsSort;
	}

	/**
	 * This method initializes jButton14
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAmountToInteger() {
		if (btnAmountToInteger == null) {
			btnAmountToInteger = new JButton();
			btnAmountToInteger.setText("数量取整");
			btnAmountToInteger
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							checkFixAuthorityAction
									.checkAmountToInteger(new Request(
											CommonVars.getCurrUser()));
							if (tableModelItem == null
									|| tableModelItem.getList().size() <= 0) {
								return;
							}
							List list = tableModelItem.getList();
							if (list.size() <= 0) {
								return;
							}
							if (JOptionPane.showConfirmDialog(
									DgFixtureContract.this, "是否将所有的记录的数量取整!!!",
									"提示", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
								list = fixtureContractAction
										.saveContractItemAmountInteger(
												new Request(CommonVars
														.getCurrUser()), list);
								tableModelItem.updateRows(list);
							}

						}
					});
		}

		return btnAmountToInteger;
	}

	/**
	 * This method initializes jButton15
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditContractItems() {
		if (btnEditContractItems == null) {
			btnEditContractItems = new JButton();
			btnEditContractItems.setText("修改设备");
			btnEditContractItems
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							checkFixAuthorityAction
									.checkEditContractItems(new Request(
											CommonVars.getCurrUser()));
							if (tableModelItem == null
									|| tableModelItem.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										DgFixtureContract.this,
										"请选择要修改的设备表记录!!!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							DgFixtureContractItems dg = new DgFixtureContractItems();
							dg.setFixtureContract(fixtureContract);
							dg.setTableModel(tableModelItem);
							dg.setDataState(DataState.EDIT);
							dg.setVisible(true);
						}
					});
		}
		return btnEditContractItems;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel1.add(getCbbPrint(), null);
			jPanel1.add(getBtnPrint(), null);
		}
		return jPanel1;
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
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnEdit;
	}

	/**
	 * 初始化设备总表
	 * 
	 */
	private void initTbContractItems(List list) {
		JTableListModel.dataBind(tbMateriel, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("分协议号", "secondProtocol", 100));
						list.add(addColumn("进口日期", "importDate", 100));
						list.add(addColumn("序号", "seqNum", 60, Integer.class));
//						list.add(addColumn("料号", "ptNo", 80));
						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("商品名称", "name", 150));
						list.add(addColumn("规格型号", "spec", 100));
						list.add(addColumn("计量单位", "unit.name", 80));
						list.add(addColumn("单价", "declarePrice", 100));
						list.add(addColumn("数量", "amount", 100));
						list.add(addColumn("总金额", "totalPrice", 100));
						list.add(addColumn("法定单位", "complex.firstUnit.name",
								100));
						list.add(addColumn("原产地", "country.name", 100));
						list.add(addColumn("设备类型", "fixKind", 100));
						list.add(addColumn("备注", "note", 100));
						return list;
					}
				});
		tbMateriel.getColumnModel().getColumn(13).setCellRenderer(
				new DefaultTableCellRenderer() {

					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						// TODO Auto-generated method stub
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						try {
							if (value != null
									&& Integer.valueOf(value.toString())
											.intValue() == 0) {
								str = "不作价设备";
							} else if (value != null
									&& Integer.valueOf(value.toString())
											.intValue() == 1) {
								str = "借用设备";
							}
						} catch (Exception e) {
							str = "";
						}
						this.setText(str);
						return this;
					}

				});
		tableModelItem = (JTableListModel) tbMateriel.getModel();
	}

	/**
	 * 初始化组件
	 * 
	 */
	private void initUIComponents() {
		// 申报关区号
		this.cbbDeclareCustoms.setModel(CustomBaseModel.getInstance()
				.getCustomModel());
		this.initComboBoxRenderer(cbbDeclareCustoms);
		// 进出口岸
		this.cbbIePort.setModel(CustomBaseModel.getInstance().getCustomModel());
		this.initComboBoxRenderer(cbbIePort);
		// 初始经营单位systemAction
		List companyList = systemAction.findCompanies();
		this.cbbTradeCompany.setModel(new DefaultComboBoxModel(companyList
				.toArray()));
		this.cbbTradeCompany.setRenderer(CustomBaseRender.getInstance()
				.getRender("buCode", "buName", 100, 170));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbTradeCompany, "buCode", "buName", 270);
		// 进出口岸2
		this.cbbIePort2
				.setModel(CustomBaseModel.getInstance().getCustomModel());
		this.initComboBoxRenderer(cbbIePort2);
		// 进出口岸3
		this.cbbIePort3
				.setModel(CustomBaseModel.getInstance().getCustomModel());
		this.initComboBoxRenderer(cbbIePort3);
		// 进出口岸4
		this.cbbIePort4
				.setModel(CustomBaseModel.getInstance().getCustomModel());
		this.initComboBoxRenderer(cbbIePort4);
		// 进出口岸5
		this.cbbIePort5
				.setModel(CustomBaseModel.getInstance().getCustomModel());
		this.initComboBoxRenderer(cbbIePort5);
		// 贸易方式
		this.cbbTrade.setModel(CustomBaseModel.getInstance().getTradeModel());
		this.initComboBoxRenderer(cbbTrade);
		// 贸易国别
		this.cbbTradeCountry.setModel(CustomBaseModel.getInstance()
				.getCountryModel());
		this.initComboBoxRenderer(cbbTradeCountry);
		// 征面性质
		this.cbbLevyKind.setModel(CustomBaseModel.getInstance()
				.getLevyKindModel());
		this.initComboBoxRenderer(cbbLevyKind);
		// 保税方式
		this.cbbPayMode.setModel(CustomBaseModel.getInstance()
				.getPayModeModel());
		this.initComboBoxRenderer(cbbPayMode);
		// 初始化币制
		this.cbbCurr.setModel(CustomBaseModel.getInstance().getCurrModel());
		this.initComboBoxRenderer(cbbCurr);
		// 成交方式
		this.cbbTransac.setModel(CustomBaseModel.getInstance()
				.getTransacModel());
		this.initComboBoxRenderer(cbbTransac);

		// 初始化创建日期

		this.cbbSaveDate.setValue(new Date());

		// 初始化开始日期,有效日期
		this.cbbBeginDate.setDate(new Date());
		Calendar beginCal = cbbBeginDate.getCalendar();
		beginCal.add(Calendar.MONTH, 6);
		cbbAvailabilityDate.setDate(beginCal.getTime());

		// 监管费用率
		cbbWardshipRate.removeAllItems();
		cbbWardshipRate.addItem("0.001");
		cbbWardshipRate.addItem("0.0015");
		cbbWardshipRate.addItem("0.003");
		this.initComboBoxRenderer(cbbWardshipRate);
		// 打印
		cbbPrint.removeAllItems();
		cbbPrint.addItem("    套打设备表");
		cbbPrint.addItem("  非套打设备表");
		cbbPrint.addItem("  打印加工合同备案情况表");
		// cbbPrint.addItem("  打印加工合同备案变更情况表");// 只打印增加了或减小了的数量

	}

	/**
	 * 产生新的预录入号
	 * 
	 */
	private void setPreContractCode() {
		String preContractCode = "";
		if (this.cbbDeclareCustoms.getSelectedItem() == null) {
			return;
		}
		String declareCustomsCode = ((Customs) this.cbbDeclareCustoms
				.getSelectedItem()).getCode();
		String declareCustomsTwoSuffix = declareCustomsCode.substring(
				declareCustomsCode.length() - 2, declareCustomsCode.length());
		String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		String yearOneSuffix = year.substring(year.length() - 1, year.length());

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
	 * 初始化Cbb呈现
	 * 
	 * @param cbb
	 */
	private void initComboBoxRenderer(JComboBox cbb) {
		cbb.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbb);
		cbb.setSelectedItem(null);
	}

	/**
	 * 清空数据
	 * 
	 */
	private void showEmptyData() {
		//
		// 显示日期
		//
		tfApprover.setText(null);
		tfEmsNo.setText(null);
		cbbTradeCompany.setSelectedItem(null);
		tfMachName.setText(null);
		tfMachCode.setText(null);
		tfEnterpriseAddress.setText(null);
		tfContactTel.setText(null);
		tfLinkMan.setText(null);
		tfOutTradeCo.setText(null);
		tfSancEmsNo.setText(null);
		tfExpContractNo.setText(null);
		tfImpContractNo.setText(null);
		tfAgreementNo.setText(null);
		tfImgAmount.setValue(new Double(0));
		tfWardshipFee.setValue(new Double(0));
		tfMemo.setText(null);
		// cbbBeginDate.setDate(null);
		// cbbAvailabilityDate.setDate(null);
		cbbApproveDate.setDate(null);
		cbbDeferDate.setDate(null);
		cbbWardshipRate.setSelectedItem(null);
		cbbDeclareCustoms.setSelectedItem(null);
		cbbTrade.setSelectedItem(null);
		cbbTradeCountry.setSelectedItem(null);
		cbbLevyKind.setSelectedItem(null);
		cbbPayMode.setSelectedItem(null);
		cbbCurr.setSelectedItem(null);
		cbbTransac.setSelectedItem(null);
		cbbIePort.setSelectedItem(null);
		cbbIePort2.setSelectedItem(null);
		cbbIePort3.setSelectedItem(null);
		cbbIePort4.setSelectedItem(null);
		cbbIePort5.setSelectedItem(null);

		tfFirstTrialer.setText(null);
		tfEmphasisFlag.setText(null);
		tfMaterielItemCount.setValue(new Double(0));
		tfAclUserName.setText(null);
		cbbChangeDate.setDate(null);
		// cbbSaveDate.setDate(null);
	}

	/**
	 * 显示数据
	 * 
	 */
	private void showData() {

		if (this.dataState == DataState.ADD && this.fixtureContract == null) {
			//
			// 显示最基本的附加数据
			//
			showEmptyData();
			Company company = (Company) CommonVars.getCurrUser().getCompany();
			tfContactTel.setText(company.getTel());
			tfLinkMan.setText(company.getOwner());
			tfOutTradeCo.setText(company.getOutTradeCo());
			this.cbbTradeCompany.setSelectedItem(company);
			this.tfEnterpriseAddress.setText(company.getAddress());
			this.tfMachName.setText(company.getName());
			this.tfMachCode.setText(company.getCode());
			this.tfDeclareState.setText("正在申请");
			this.tfAclUserName.setText(CommonVars.getCurrUser() == null ? ""
					: CommonVars.getCurrUser().getUserName());
			CompanyOther other = CommonVars.getOther();
			if (other != null) {
				cbbDeclareCustoms.setSelectedItem(other.getCustomNo());
				tfSancEmsNo.setText(other.getSancEmsNo());
				cbbIePort.setSelectedItem(other.getIePort1());
				cbbIePort2.setSelectedItem(other.getIePort2());
				cbbIePort3.setSelectedItem(other.getIePort3());
				cbbIePort4.setSelectedItem(other.getIePort4());
				cbbIePort5.setSelectedItem(other.getIePort5());
				cbbTrade.setSelectedItem(other.getTrade());
				cbbLevyKind.setSelectedItem(other.getLevyKind());
				cbbTradeCountry.setSelectedItem(other.getTradeCountry());
				tfMemo.setText(other.getNote());
				cbbTransac.setSelectedItem(other.getTransac());
				this.cbbCurr.setSelectedItem(other.getCommonCurr());
			}

			return;
		}
		if (this.dataState == DataState.EDIT
				|| this.dataState == DataState.BROWSE) {
			this.fixtureContract = (FixtureContract) this.tableModelContract
					.getCurrentRow();
		}

		//
		// 显示 textField 数据
		//
		if (DeclareState.APPLY_POR.equals(fixtureContract.getDeclareState())) {
			tfDeclareState.setText("正在申请");
		} else if (DeclareState.CHANGING_EXE.equals(fixtureContract
				.getDeclareState())) {
			tfDeclareState.setText("正在变更");
		} else if (DeclareState.PROCESS_EXE.equals(fixtureContract
				.getDeclareState())) {
			tfDeclareState.setText("正在执行");
		}
		tfApprover.setText(fixtureContract.getRetrialer());
		tfEmsNo.setText(fixtureContract.getEmsNo());
		if (fixtureContract.getTradeName() != null) {
			Company company = systemAction.findCompaniesByName(fixtureContract
					.getTradeName());
			cbbTradeCompany.setSelectedItem(company);
		}
		tfMachName.setText(fixtureContract.getMachName());
		tfMachCode.setText(fixtureContract.getMachCode());
		tfEnterpriseAddress.setText(fixtureContract.getEnterpriseAddress());
		tfContactTel.setText(fixtureContract.getContactTel());
		tfLinkMan.setText(fixtureContract.getLinkMan());
		tfOutTradeCo.setText(fixtureContract.getOutTradeCo());
		tfSancEmsNo.setText(fixtureContract.getSancEmsNo());
		tfExpContractNo.setText(fixtureContract.getExpContractNo());
		tfImpContractNo.setText(fixtureContract.getImpContractNo());
		tfAgreementNo.setText(fixtureContract.getAgreementNo());
		tfImgAmount
				.setValue(fixtureContract.getFixtureAmount() == null ? new Double(
						0)
						: fixtureContract.getFixtureAmount());
		tfWardshipFee
				.setValue(fixtureContract.getWardshipFee() == null ? new Double(
						0)
						: fixtureContract.getWardshipFee());
		tfMemo.setText(fixtureContract.getMemo());

		tfFirstTrialer.setText(fixtureContract.getFirstTrialer());
		tfEmphasisFlag.setText(fixtureContract.getEmphasisFlag());
		tfMaterielItemCount
				.setValue(fixtureContract.getMaterielItemCount() == null ? new Double(
						0)
						: fixtureContract.getMaterielItemCount());
		tfAclUserName.setText(fixtureContract.getAclUser() == null ? ""
				: fixtureContract.getAclUser().getUserName());

		cbbWardshipRate.setSelectedItem(fixtureContract.getWardshipRate());
		cbbDeclareCustoms.setSelectedItem(fixtureContract.getDeclareCustoms());
		cbbTrade.setSelectedItem(fixtureContract.getTrade());
		cbbTradeCountry.setSelectedItem(fixtureContract.getTradeCountry());
		cbbLevyKind.setSelectedItem(fixtureContract.getLevyKind());
		cbbPayMode.setSelectedItem(fixtureContract.getPayMode());
		cbbCurr.setSelectedItem(fixtureContract.getCurr());
		cbbTransac.setSelectedItem(fixtureContract.getTransac());
		cbbIePort.setSelectedItem(fixtureContract.getIePort1());
		cbbIePort2.setSelectedItem(fixtureContract.getIePort2());
		cbbIePort3.setSelectedItem(fixtureContract.getIePort3());
		cbbIePort4.setSelectedItem(fixtureContract.getIePort4());
		cbbIePort5.setSelectedItem(fixtureContract.getIePort5());

		//
		// 显示日期
		//
		cbbBeginDate.setDate(fixtureContract.getBeginDate());
		cbbAvailabilityDate.setDate(fixtureContract.getAvailabilityDate());
		cbbApproveDate.setDate(fixtureContract.getApproveDate());
		cbbDeferDate.setDate(fixtureContract.getDeferDate());
		cbbChangeDate.setDate(fixtureContract.getChangeDate());
		cbbSaveDate.setDate(fixtureContract.getSaveDate());
	}

	/**
	 * 填充数据
	 * 
	 * @param fixtureContract
	 */
	private void fillData() {
		if (this.fixtureContract == null) {
			fixtureContract = new FixtureContract();
			fixtureContract.setDeclareState(DeclareState.APPLY_POR);
		}
		fixtureContract.setEmsNo(tfEmsNo.getText());
		Company tradeCompany = (Company) cbbTradeCompany.getSelectedItem();
		if (tradeCompany != null) {
			fixtureContract.setTradeName(tradeCompany.getBuName());
			fixtureContract.setTradeCode(tradeCompany.getBuCode());
		} else {
			fixtureContract.setTradeName(null);
			fixtureContract.setTradeCode(null);
		}
		fixtureContract.setEnterpriseAddress(tfEnterpriseAddress.getText());
		fixtureContract.setMachName(this.tfMachName.getText());
		fixtureContract.setMachCode(this.tfMachCode.getText());
		fixtureContract.setLinkMan(tfLinkMan.getText());
		fixtureContract.setContactTel(tfContactTel.getText());
		fixtureContract.setSancEmsNo(tfSancEmsNo.getText());
		fixtureContract.setOutTradeCo(tfOutTradeCo.getText());
		fixtureContract.setAgreementNo(tfAgreementNo.getText());
		fixtureContract.setImpContractNo(tfImpContractNo.getText());
		fixtureContract.setExpContractNo(tfExpContractNo.getText());
		fixtureContract.setFixtureAmount(Double.valueOf((tfImgAmount.getValue()
				.toString())));
		fixtureContract.setMemo(tfMemo.getText());

		fixtureContract.setRetrialer(tfApprover.getText());
		if (cbbWardshipRate.getSelectedItem() != null
				&& !cbbWardshipRate.getSelectedItem().equals("")) {
			fixtureContract.setWardshipRate(Double.valueOf(cbbWardshipRate
					.getSelectedItem().toString()));
		}
		fixtureContract.setWardshipFee(Double.valueOf((tfWardshipFee.getValue()
				.toString())));

		fixtureContract.setFirstTrialer(tfFirstTrialer.getText());
		fixtureContract.setEmphasisFlag(tfEmphasisFlag.getText());
		fixtureContract.setMaterielItemCount(Double.valueOf(
				(tfMaterielItemCount.getValue().toString())).intValue());

		//
		// 填充引用对象
		//
		fixtureContract.setLevyKind((LevyKind) cbbLevyKind.getSelectedItem());
		fixtureContract.setCurr((Curr) cbbCurr.getSelectedItem());
		fixtureContract.setTradeCountry((Country) cbbTradeCountry
				.getSelectedItem());
		fixtureContract.setTrade((Trade) cbbTrade.getSelectedItem());
		fixtureContract.setIePort1((Customs) cbbIePort.getSelectedItem());
		fixtureContract.setDeclareCustoms((Customs) cbbDeclareCustoms
				.getSelectedItem());
		fixtureContract.setTransac((Transac) cbbTransac.getSelectedItem());
		fixtureContract.setIePort4((Customs) cbbIePort4.getSelectedItem());
		fixtureContract.setIePort3((Customs) cbbIePort3.getSelectedItem());
		fixtureContract.setIePort2((Customs) cbbIePort2.getSelectedItem());
		fixtureContract.setIePort5((Customs) cbbIePort5.getSelectedItem());
		fixtureContract.setPayMode((PayMode) cbbPayMode.getSelectedItem());
		fixtureContract.setCompany(CommonVars.getCurrUser().getCompany());
		fixtureContract.setAclUser(CommonVars.getCurrUser());
		//
		// 填充日期
		//
		fixtureContract.setBeginDate(cbbBeginDate.getDate());
		fixtureContract.setAvailabilityDate(cbbAvailabilityDate.getDate());
		// contract.setEndDate(cbbEndDate.getDate());
		fixtureContract.setApproveDate(cbbApproveDate.getDate());
		fixtureContract.setDeferDate(cbbDeferDate.getDate());
		fixtureContract.setChangeDate(cbbChangeDate.getDate());
		fixtureContract.setSaveDate(cbbSaveDate.getDate());
	}

	// private void setTabbedPaneChangeState() {
	//		
	// }

	/**
	 * 设置Components状态
	 */
	private void setState() {
		boolean isChanging = false;
		if (this.fixtureContract != null) {
			if (DeclareState.CHANGING_EXE.equals(this.fixtureContract
					.getDeclareState())) {
				isChanging = true;
			}
		}

		boolean isExeing = false;

		if (this.fixtureContract != null) {
			if (DeclareState.PROCESS_EXE.equals(this.fixtureContract
					.getDeclareState())) {
				isExeing = true;
			}
		}
		//
		// 合同基本信息面板
		//
		// setTabbedPaneChangeState();
		btnSave.setEnabled((jTabbedPane.getSelectedIndex() == 0)
				&& dataState != DataState.BROWSE); // 保存
		btnEdit.setEnabled((jTabbedPane.getSelectedIndex() == 0)
				&& dataState == DataState.BROWSE && !isExeing); // 修改
		btnUndo.setEnabled((jTabbedPane.getSelectedIndex() == 0)
				&& dataState != DataState.BROWSE); // 撤消
		cbbDeclareCustoms.setEnabled(dataState != DataState.BROWSE);
		cbbIePort.setEnabled(dataState != DataState.BROWSE);
		// tfPreEmsNo.setEditable(dataState != DataState.BROWSE);
		cbbBeginDate.setEnabled(dataState != DataState.BROWSE);
		cbbAvailabilityDate.setEnabled(dataState != DataState.BROWSE);
		cbbTrade.setEnabled(dataState != DataState.BROWSE);
		cbbTradeCountry.setEnabled(dataState != DataState.BROWSE);
		cbbTradeCompany.setEnabled(dataState != DataState.BROWSE);
		tfEnterpriseAddress.setEditable(dataState != DataState.BROWSE);
		cbbDeferDate.setEnabled(dataState != DataState.BROWSE);
		cbbLevyKind.setEnabled(dataState != DataState.BROWSE);
		tfContactTel.setEditable(dataState != DataState.BROWSE);
		tfLinkMan.setEditable(dataState != DataState.BROWSE);
		tfOutTradeCo.setEditable(dataState != DataState.BROWSE);
		tfSancEmsNo.setEditable(dataState != DataState.BROWSE);
		tfExpContractNo.setEditable(dataState != DataState.BROWSE);
		tfImpContractNo.setEditable(dataState != DataState.BROWSE);
		tfAgreementNo.setEditable(dataState != DataState.BROWSE);
		tfImgAmount.setEnabled(dataState != DataState.BROWSE);
		cbbCurr.setEnabled(dataState != DataState.BROWSE);
		cbbTransac.setEnabled(dataState != DataState.BROWSE);
		tfWardshipFee.setEnabled(dataState != DataState.BROWSE);
		cbbWardshipRate.setEnabled(dataState != DataState.BROWSE);
		cbbIePort2.setEnabled(dataState != DataState.BROWSE);
		cbbIePort3.setEnabled(dataState != DataState.BROWSE);
		cbbIePort4.setEnabled(dataState != DataState.BROWSE);
		cbbIePort5.setEnabled(dataState != DataState.BROWSE);
		tfApprover.setEditable(dataState != DataState.BROWSE);
		cbbApproveDate.setEnabled(dataState != DataState.BROWSE);
		cbbPayMode.setEnabled(dataState != DataState.BROWSE);
		tfMemo.setEditable(dataState != DataState.BROWSE);

		tfEmsNo.setEditable(dataState != DataState.BROWSE && !isChanging);
		btnContractImport.setEnabled(dataState != DataState.ADD);
		//  
		// 设备总表面板 btnChangingMaterielNo btnChangingFinishProductNo
		//

		this.btnDeleteContractItems.setEnabled(dataState == DataState.BROWSE
				&& !isExeing);
		this.btnAddContractItems.setEnabled(dataState == DataState.BROWSE
				&& !isExeing);
		this.btnAmountToInteger.setEnabled(dataState == DataState.BROWSE
				&& !isExeing);
		this.btnEditContractItems.setEnabled(dataState == DataState.BROWSE
				&& !isExeing);
		// this.btnChangingContractItemsNo
		// .setEnabled(dataState == DataState.BROWSE && !isExeing);
		this.btnContractItemsSort.setEnabled(dataState == DataState.BROWSE
				&& !isChanging && !isExeing);
		this.btnChangingContractItemsNo
				.setEnabled(dataState == DataState.BROWSE && !isExeing);
		// this.btnCal.setEnabled(dataState != DataState.BROWSE && !isChanging);
		this.btnChangeContractItemslNameSpec.setEnabled(!isExeing);
		this.btnChangeItemComplex.setEnabled(!isExeing);
	}

	/**
	 * 获得当前币制与 HDK和USD的汇率
	 * 
	 * @return
	 */
	private List<Double> getHDKAndUSDRate() {
		//
		// 汇率设置
		//       
		double rateHKD = 0;
		double rateUSD = 0;
		Curr currentCurr = this.fixtureContract.getCurr();
//        System.out.println("这空："+(currentCurr==null));
//        System.out.println("这空："+currentCurr.getName());
		if (currentCurr != null) {
			System.out.println("code"+currentCurr.getCode());
			if ("110".equals(currentCurr.getCode().trim())) {
				rateHKD = 1;
			} else if ("502".equals(currentCurr.getCode().trim())) {
				rateUSD = 1;
			}
			List currRateList = this.materialManageAction.findCurrRate(
					new Request(CommonVars.getCurrUser()), currentCurr
							.getCode());
			System.out.println(currRateList.size());
			for (int i = 0; i < currRateList.size(); i++) {
				CurrRate currRate = (CurrRate) currRateList.get(i);
				if ("110".equals(currRate.getCurr1().getCode().trim())) {
					rateHKD = currRate.getRate();
				} else if ("502".equals(currRate.getCurr1().getCode().trim())) {
					rateUSD = currRate.getRate();
				}
			}
		}
//		System.out.println("这空："+rateHKD);
//		System.out.println("这空："+rateUSD);
		if (rateHKD == 0) {
			if (currentCurr == null) {
				JOptionPane.showMessageDialog(DgFixtureContract.this,
						"当前合同没有设置币制!!!", "提示", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(DgFixtureContract.this,
						"汇率表中没设置当前币制与港币的汇率!!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
		if (rateUSD == 0) {
			if (currentCurr == null) {
				JOptionPane.showMessageDialog(DgFixtureContract.this,
						"当前合同没有设置币制!!!", "提示", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(DgFixtureContract.this,
						"汇率表中没设置当前币制与美元的汇率!!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
		List<Double> list = new ArrayList<Double>();
		list.add(rateHKD);
		list.add(rateUSD);
		return list;
	}

	/**
	 * 报表打印
	 */
	private void printReport() {
		if (fixtureContract == null) {
			JOptionPane.showMessageDialog(DgFixtureContract.this,
					"请先保存合同记录!!!", "提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		int flag = this.cbbPrint.getSelectedIndex();
		String emsNo = "";
		switch (flag) {
		case 0: // 套打设备表
			try {
				List list = new ArrayList();
				if (fixtureContract != null) {
					String parentId = fixtureContract.getId();
					// list = fixtureContractAction.findContractItemByParentId(
					// new Request(CommonVars.getCurrUser()), parentId);
					list = tableModelItem.getList();
					if (list == null || tableModelItem == null
							|| list.size() == 0) {
						JOptionPane.showMessageDialog(DgFixtureContract.this,
								"没有数据");
						return;
					}
					emsNo = fixtureContract.getImpContractNo() == null ? ""
							: fixtureContract.getImpContractNo();
				}
				InputStream subReportStream = DgFixtureContract.class
						.getResourceAsStream("report/FixtureSubReport.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				CustomReportDataSource ds = new CustomReportDataSource(list);
				List<Double> currRateList = this.getHDKAndUSDRate();
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("isOverprint", new Boolean(true));
				parameters.put("rateHDK", currRateList.get(0));
				parameters.put("rateUSD", currRateList.get(1));
				parameters.put("list", list);
				parameters.put("emsNo", emsNo);
				parameters.put("companyName",
						fixtureContract.getMachName() == null ? ""
								: fixtureContract.getMachName());
				parameters.put("currName",
						fixtureContract.getCurr() == null ? ""
								: fixtureContract.getCurr().getName());
				parameters.put("subReport", subReport);
				parameters.put("subReportDataSource", ds);
				InputStream masterReportStream = DgFixtureContract.class
						.getResourceAsStream("report/FixtureReport.jasper");
				List<String> tempList = new ArrayList<String>();
				tempList.add("tempData");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters,
						new CustomReportDataSource(tempList));
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;
		case 1: // 非套打设备表
			try {
				List list = new ArrayList();
				if (fixtureContract != null) {
					String parentId = fixtureContract.getId();
					// list = fixtureContractAction.findContractItemByParentId(
					// new Request(CommonVars.getCurrUser()), parentId);
					list = tableModelItem.getList();
					if (list == null || tableModelItem == null
							|| list.size() == 0) {
						JOptionPane.showMessageDialog(DgFixtureContract.this,
								"没有数据");
						return;
					}
					emsNo = fixtureContract.getImpContractNo() == null ? ""
							: fixtureContract.getImpContractNo();
				}
				InputStream subReportStream = DgFixtureContract.class
						.getResourceAsStream("report/FixtureSubReport.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				CustomReportDataSource ds = new CustomReportDataSource(list);
				List<Double> currRateList = this.getHDKAndUSDRate();
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("isOverprint", new Boolean(false));
				parameters.put("rateHDK", currRateList.get(0));
				parameters.put("rateUSD", currRateList.get(1));
				parameters.put("list", list);
				parameters.put("emsNo", emsNo);
				parameters.put("companyName", fixtureContract.getMachName());
				parameters.put("currName",
						fixtureContract.getCurr() == null ? ""
								: fixtureContract.getCurr().getName());
				parameters.put("subReport", subReport);
				parameters.put("subReportDataSource", ds);
				InputStream masterReportStream = DgFixtureContract.class
						.getResourceAsStream("report/FixtureReport.jasper");
				List<String> tempList = new ArrayList<String>();
				tempList.add("tempData");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters,
						new CustomReportDataSource(tempList));
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;
		case 2:// 打印加工合同备案情况表
			printPutOnRecordsContract(new Boolean(true));
			break;
		case 3: // 打印加工合同变更表
			printPutOnRecordsContract(new Boolean(false));
			break;
		default:
			break;
		}
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * 
	 * @param isPutOnRecordsContract
	 *            ==true 是合同备案,否则是预申报合同
	 */
	private void printPutOnRecordsContract(Boolean isPutOnRecordsContract) {
		try {
			// ==========================
			// 合同主报表 + 设备细表
			// ==========================

			//
			// 合同备案情况主报表数据源
			//            
			String emsNo = "手册编号: " + fixtureContract.getEmsNo();
			List<FixtureContract> contractList = new ArrayList<FixtureContract>();
			contractList.add(fixtureContract);
			CustomReportDataSource ds = new CustomReportDataSource(contractList);

			//
			// 设备明细列表数据源
			//
			Double totalPrice = 0.0;
			List fixtureList = new ArrayList();
			if (fixtureContract != null) {
				String parentId = fixtureContract.getId();
				if (isPutOnRecordsContract == true) {
					fixtureList = fixtureContractAction
							.findContractItemByParentId(new Request(CommonVars
									.getCurrUser()), parentId);
				} else {
					fixtureList = fixtureContractAction
							.findContractItemByParentIdAndChange(new Request(
									CommonVars.getCurrUser()), parentId);
				}
			}
			CustomReportDataSource fixtureDataSource = new CustomReportDataSource(
					fixtureList);
			//
			// 设备明细子报表
			//               
			InputStream fixtureSubReportStream = DgFixtureContract.class
					.getResourceAsStream("report/ContractItemSubPutOnRecordReport.jasper");
			JasperReport fixtureSubReport = (JasperReport) JRLoader
					.loadObject(fixtureSubReportStream);

			//
			// 合同备案情况主报表
			//
			SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyyMMdd");
			String availabilityDate = bartDateFormat.format(fixtureContract
					.getAvailabilityDate());
			availabilityDate = availabilityDate.substring(0, 4) + "年"
					+ availabilityDate.substring(4, 6) + "月"
					+ availabilityDate.substring(6, 8) + "日 止";
			String changeDate = "";
			if (fixtureContract.getChangeDate() != null)
				changeDate = bartDateFormat.format(fixtureContract
						.getChangeDate());
			Map<String, Object> contractMap = new HashMap<String, Object>();
			contractMap.put("fixtureSubReport", fixtureSubReport);
			contractMap.put("subReportDataSource", fixtureDataSource);
			contractMap.put("isPutOnRecordsContract", isPutOnRecordsContract);
			contractMap.put("emsNo", emsNo);
			contractMap.put("availabilityDate", availabilityDate);
			contractMap.put("changeDate", changeDate);
			InputStream masterReportStream = DgFixtureContract.class
					.getResourceAsStream("report/ContractPutOnRecordReport.jasper");
			JasperPrint jasperPrintContract = JasperFillManager.fillReport(
					masterReportStream, contractMap, ds);

			//
			// 显示报表
			//
			DgReportViewer viewer = new DgReportViewer(jasperPrintContract);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddContractItems() {
		if (btnAddContractItems == null) {
			btnAddContractItems = new JButton();
			btnAddContractItems.setText("新增设备");
			btnAddContractItems
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							checkFixAuthorityAction
									.checkAddFixtureContractItems(new Request(
											CommonVars.getCurrUser()));
							if (fixtureContract == null) {
								JOptionPane.showMessageDialog(
										DgFixtureContract.this, "请先保存合同记录!!!",
										"提示", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							List list = new ArrayList();
							List lsResult = new ArrayList();
							list = FixtureQuery.getInstance().getMachine();
							
							if (list == null || list.size() <= 0) {
								return;
							}
							lsResult = fixtureContractAction
									.saveComtractItemByComplex(new Request(
											CommonVars.getCurrUser()),
											fixtureContract, list);
							fixtureContract.setMachineCount(Integer
									.valueOf(lsResult.size()));
							fixtureContract = fixtureContractAction
									.saveContract(new Request(CommonVars
											.getCurrUser()), fixtureContract);
							tableModelItem.addRows(lsResult);
							DgFixtureContractItems dg = new DgFixtureContractItems();
							dg.setFixtureContract(fixtureContract);
							dg.setTableModel(tableModelItem);
							dg.setDataState(DataState.EDIT);
							dg.setIsEditable(true);// 控制 商品名称和型号规格是否可修改
							dg.setVisible(true);

						}
					});
		}
		return btnAddContractItems;
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (btnDeleteContractItems == null) {
			btnDeleteContractItems = new JButton();
			btnDeleteContractItems.setText("删除设备");
			btnDeleteContractItems
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							checkFixAuthorityAction
									.checkDeleteContractItems(new Request(
											CommonVars.getCurrUser()));
							if (tableModelItem == null
									|| tableModelItem.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										DgFixtureContract.this,
										"请选择要删除的设备记录!!!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							List deleteList = tableModelItem.getCurrentRows();
							if (DeclareState.APPLY_POR.equals(fixtureContract
									.getDeclareState())) {
								if (JOptionPane.showConfirmDialog(
										DgFixtureContract.this,
										"是否确定删除设备记录!!!", "提示",
										JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
									return;
								}
								fixtureContractAction.deleteContractItem(
										new Request(CommonVars.getCurrUser()),
										deleteList);
								fixtureContract.setMachineCount(CommonUtils
										.getIntegerExceptNull(fixtureContract
												.getMachineCount())
										- Integer.valueOf(deleteList.size()));
								fixtureContract = fixtureContractAction
										.saveContract(new Request(CommonVars
												.getCurrUser()),
												fixtureContract);
								tableModelItem.deleteRows(deleteList);

							} else if (DeclareState.CHANGING_EXE
									.equals(fixtureContract.getDeclareState())) {
								for (int i = deleteList.size() - 1; i >= 0; i--) {
									FixtureContractItems contractImg = (FixtureContractItems) deleteList
											.get(i);
									if (DeclareState.PROCESS_EXE
											.equals(contractImg
													.getDeclareState())) {
										deleteList.remove(contractImg);
									}
								}
								//
								// 如果有新增的数据提示用户是否删除
								//
								if (deleteList.size() > 0) {
									if (JOptionPane.showConfirmDialog(
											DgFixtureContract.this,
											"是否确定删除合同设备新增记录!!!", "提示",
											JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
										return;
									}
									fixtureContractAction
											.deleteContractItem(new Request(
													CommonVars.getCurrUser()),
													deleteList);
									fixtureContract
											.setMachineCount(fixtureContract
													.getMachineCount()
													- Integer
															.valueOf(deleteList
																	.size()));
									fixtureContract = fixtureContractAction
											.saveContract(new Request(
													CommonVars.getCurrUser()),
													fixtureContract);
									tableModelItem.deleteRows(deleteList);
								} else {
									JOptionPane.showMessageDialog(
											DgFixtureContract.this,
											"不能删除已备案的设备记录!!!", "提示",
											JOptionPane.INFORMATION_MESSAGE);
								}
							}

						}
					});
		}
		return btnDeleteContractItems;
	}

	/**
	 * 设置容器除JLabel外所有控件的enabled属性
	 */
	protected void setContainerEnabled(Container container, boolean isFlag) {
		for (int i = 0; i < container.getComponentCount(); i++) {
			Component component = container.getComponent(i);
			if (!(component instanceof JLabel)) {
				component.setEnabled(isFlag);
				if (component instanceof Container) {
					setContainerEnabled((Container) component, isFlag);
				}
			}
		}
	}

	/**
	 * This method initializes cbbTrader
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTradeCompany() {
		if (cbbTradeCompany == null) {
			cbbTradeCompany = new JComboBox();
			cbbTradeCompany.setBounds(new java.awt.Rectangle(112, 86, 127, 20));
		}
		return cbbTradeCompany;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFirstTrialer() {
		if (tfFirstTrialer == null) {
			tfFirstTrialer = new JTextField();
			tfFirstTrialer.setBounds(new Rectangle(112, 299, 127, 20));
		}
		return tfFirstTrialer;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbSaveDate() {
		if (cbbSaveDate == null) {
			cbbSaveDate = new JCalendarComboBox();
			cbbSaveDate.setBounds(new Rectangle(543, 106, 127, 20));
		}
		return cbbSaveDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbChangeDate() {
		if (cbbChangeDate == null) {
			cbbChangeDate = new JCalendarComboBox();
			cbbChangeDate.setBounds(new Rectangle(543, 85, 127, 20));
		}
		return cbbChangeDate;
	}

	/**
	 * This method initializes jCustomFormattedTextField2
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfMaterielItemCount() {
		if (tfMaterielItemCount == null) {
			tfMaterielItemCount = new JCustomFormattedTextField();
			tfMaterielItemCount.setBounds(new Rectangle(546, 300, 127, 20));
			tfMaterielItemCount.setValue(new Double(0));
		}
		return tfMaterielItemCount;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmphasisFlag() {
		if (tfEmphasisFlag == null) {
			tfEmphasisFlag = new JTextField();
			tfEmphasisFlag.setBounds(new Rectangle(325, 213, 127, 20));
		}
		return tfEmphasisFlag;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfAclUserName() {
		if (tfAclUserName == null) {
			tfAclUserName = new JTextField();
			tfAclUserName.setEditable(false);
			tfAclUserName.setBounds(new Rectangle(326, 298, 127, 20));
		}
		return tfAclUserName;
	}

	private JButton getBtnChangeContractItemslNameSpec() {
		if (btnChangeContractItemslNameSpec == null) {
			btnChangeContractItemslNameSpec = new JButton();
			btnChangeContractItemslNameSpec.setText("变更名称规格");
			btnChangeContractItemslNameSpec.setVisible(true);
			btnChangeContractItemslNameSpec
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							checkFixAuthorityAction
									.checkChangeContractItemslNameSpec(new Request(
											CommonVars.getCurrUser()));
							if (tableModelItem.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										btnChangeContractItemslNameSpec,
										"       请选择要修改的设备!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							DgChangeItemsNameSpec dg = new DgChangeItemsNameSpec();
							dg.setFixtureContract(fixtureContract);
							dg.setTableModel(tableModelItem);
							dg.setVisible(true);
						}
					});
		}
		return btnChangeContractItemslNameSpec;

	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChangeItemComplex() {
		if (btnChangeItemComplex == null) {
			btnChangeItemComplex = new JButton();
			btnChangeItemComplex.setText("变更编码");
			btnChangeItemComplex
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							checkFixAuthorityAction
									.checkChangeItemComplex(new Request(
											CommonVars.getCurrUser()));
							if (tableModelItem == null
									|| tableModelItem.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										DgFixtureContract.this,
										"请选择要变更商品编码的合同设备!!!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							Complex complex = (Complex) CommonQuery
									.getInstance().getComplex();
							if (complex == null) {
								return;
							}
							FixtureContractItems item = (FixtureContractItems) tableModelItem
									.getCurrentRow();
							item = fixtureContractAction
									.changeContractItemComplex(new Request(
											CommonVars.getCurrUser()), item,
											complex);
							tableModelItem.updateRow(item);
						}
					});
		}
		return btnChangeItemComplex;
	}

	/**
	 * This method initializes tfDeclareState1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfProperty() {
		if (tfProperty == null) {
			tfProperty = new JTextField("来料设备");
			tfProperty.setBounds(new Rectangle(326, 22, 127, 20));
			tfProperty.setEditable(false);
		}
		return tfProperty;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitleString(String title) {
		this.titleString = title;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (btnContractImport == null) {
			btnContractImport = new JButton();
			btnContractImport.setText("设备导入");
			btnContractImport
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgImportContract dgImportContract = new DgImportContract();
							dgImportContract
									.setFixtureContract(fixtureContract);
							dgImportContract.setVisible(true);
							System.out.println("run is here");
							jTabbedPane.setSelectedIndex(0);
							jTabbedPane.setSelectedIndex(1);
						}
					});
		}
		return btnContractImport;
	}

	/**
	 * This method initializes btnSerch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSerch() {
		if (btnSerch == null) {
			btnSerch = new JButton();
			btnSerch.setText("查询");
			btnSerch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//料号
					String ptNo="";
					// 分协议号
					String secondContractNo = "";
					// 商品名称
					String name = "";
					// 商品规格
					String spec = "";
					// 开始日期
					Date startDate = null;
					// 结束日期
					Date endDate = null;
					DgFixtureContractSerch dgSerch = new DgFixtureContractSerch();
					dgSerch.setVisible(true);
					ptNo=dgSerch.getPtNo();					
					secondContractNo = dgSerch.getSecondContractNo();
					name = dgSerch.getName();
					spec = dgSerch.getSpec();
					startDate = dgSerch.getStartDate();
					endDate = dgSerch.getEndDate();
					String parentId = fixtureContract.getId();
					// System.out.println(secondContractNo);
					System.out.println("测试得："+ptNo);
					List list = fixtureContractAction.findContractItem(
							new Request(CommonVars.getCurrUser()), ptNo, parentId,
							secondContractNo, name, spec, startDate, endDate);
//					System.out.println(list.size());
					initTbContractItems(list);
				}
			});
		}
		return btnSerch;
	}

} // @jve:decl-index=0:visual-constraint="-463,8"
