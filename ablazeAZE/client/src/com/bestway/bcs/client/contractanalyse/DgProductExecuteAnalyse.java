/*
 * Created on 2005-6-8
 *
 * //lm checked by 2009-6-5
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractanalyse;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contractanalyse.action.ContractAnalyseAction;
import com.bestway.bcs.contractanalyse.entity.TempContractNo;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.TableColorRender;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.SearchType;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

/**
 * @author ls
 * lm checked by 2009-06-30
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
/**
 * 查询条件是针对一本或多本合同相同(商编＋商品名称＋商品规格＋单位)的统计 出口成品统计 合同总定量=合同中对应成品备案的数量 总出口量=成品出口总量 +
 * 转厂出口总量 + 返工复出总量 - 退厂返工总量 成品出口总量=所有使用该合同出口报关单出口类型为(成品出口)数量之和
 * 转厂出口总量=所有使用该合同出口报关单出口类型为(转厂出口)数量之和 退厂返工总量=所有使用该合同出口报关单出口类型为(退厂返工)数量之和
 * 返工复出总量=所有使用该合同出口报关单出口类型为(返工复出)数量之和 可出口总量=合同总订量 - 总出口量
 * 关封余量=关封管理明细中的进货本厂数量—转厂出口量(没计算) 可直接出口量=可进口数量-关封余量 查询条件是针对一本或多本合同相同 出口成品执行进度总表
 * 出口合计=成品出口数量 + 转厂出口数量 +返工复出总量 - 退厂返工总量 成品出口数量=所有使用该合同出口报关单出口类型为(成品出口)数量之和
 * 转厂出口数量=所有使用该合同出口报关单出口类型为(转厂出口)数量之和 退厂返工总量=所有使用该合同出口报关单出口类型为(退厂返工)数量之和
 * 返工复出总量=所有使用该合同出口报关单出口类型为(返工复出)数量之和 可出口总量=合同总定量 - 出口合计
 * 
 */
public class DgProductExecuteAnalyse extends JInternalFrameBase {

	private JPanel pnMainFirst = null;

	private JButton btnRefreshOne = null;

	private JSplitPane jSplitPane = null;

	private JPanel pnExeContract = null;

	private JPanel pnChoiseContract = null;

	private JCheckBox cbContractExe = null;

	private JCheckBox cbContractCancel = null;

	private JPanel jPanel = null;

	private JButton btnPrintOne = null;

	private JCalendarComboBox cbbBeginDateOne = null;

	private JCalendarComboBox cbbEndDateOne = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel pnMainSecond = null;

	private JPanel pnSecond = null;

	private JPanel pnTabel = null;

	private JTable tbFirst = null;

	private JScrollPane jScrollPane2 = null;

	private JScrollPane jScrollPane3 = null;

	private JContractList jListSecond = null;

	private JPanel jPanel3 = null;

	private JTable tbFour = null;

	private JScrollPane jScrollPane6 = null;

	private JPanel jPanel5 = null;

	private JPanel jPanel6 = null;

	private JLabel lbExe = null;

	private JLabel lbTime = null;

	private JLabel lbTo = null;

	private JButton btnRefresh = null;

	private JButton btnPrint = null;

	private JButton btnExit = null;

	private JPanel jPanel7 = null;

	private JRadioButton rbProductName = null;

	private JRadioButton rbProductNameSpec = null;

	private JRadioButton rbProductNameSpecCode = null;

	private JRadioButton rbCode = null;

	private JRadioButton rbCodeName = null;

	private JRadioButton rbNameSpecCodeUnit = null;

	private JLabel lbExcuteContract = null;

	private JLabel lbCustomsDate = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JLabel lbFor = null;

	private JCalendarComboBox cbbEndDate = null;

	private JButton btnPrintLn = null;

	private JComboBox cbbImpExpType = null;

	private JLabel lbImpAndExp = null;

	private JSplitPane jSplitPaneFirst = null;

	private JTable tbFourTwo = null;

	private JScrollPane jScrollPaneEighth = null;
	/** 合同备案表头 */
	private JTableListModel tableModelTbHead = null;
	/** 出品成品执行进度总表 */
	private JTableListModel tableModelTbExcute = null;
	/** 出品成品执行进度总表1 */
	private JTableListModel tableModelTbCancel = null;
	/** 成品总表 */
	private JTableListModel tableModelTbExgTotal = null;
	/** 成品总表 */
	private JTableListModel tableModelTbTotal = null;
	/** 报关分析Action */
	private ContractAnalyseAction contractAnalyseAction = null;
	/** 合同Action */
	private ContractAction contractAction = null;

	private JTable tbFourThird = null;

	private JScrollPane jScrollPaneFour = null;
	/** 全选 */
	private static final int ALL_SELECT_ITEM = 123;

	private JTable tbSecond = null;

	private JScrollPane jScrollPane = null;

	private JTable tbExe = null;

	private JScrollPane jScrollPane1 = null;
	/** 查询条件：名称+规格+编码+单位 */
	private static int SEARCH_TYPE = SearchType.NAME_SPEC_CODE_UNIT;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:

	private ButtonGroup buttonGroups = null;

	private JLabel lbDeclareDate = null;

	private JCalendarComboBox cbbBeginDateTwo = null;

	private JCalendarComboBox cbbEndDateTwo = null;

	private JLabel lbToFour = null;
	/** BCS参数设置 */
	private BcsParameterSet parameterSet = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgProductExecuteAnalyse() {
		super();
		initialize();
		contractAnalyseAction = (ContractAnalyseAction) CommonVars
				.getApplicationContext().getBean("contractAnalyseAction");
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		parameterSet = contractAction.findBcsParameterSet(new Request(
				CommonVars.getCurrUser(), true));
		initUIComponents();
		getButtonGroup1();
		this.cbbBeginDateOne.setDate(CommonVars.getBeginDate());
		this.cbbBeginDateTwo.setDate(CommonVars.getBeginDate());
		// this.cbbBeginDate4.setDate(CommonVars.getBeginDate());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("成品执行情况分析");
		this.setContentPane(getPnMainFirst());
		this.setSize(755, 539);
		this.getButtonGroup();
		this.getButtonGroup2();
		this.getButtonGroups();

	}

	/**
	 * 设置控件的可见性
	 */

	public void setVisible(boolean b) {
		if (b) {
			JTableListModel tableModel = (JTableListModel) tbExe.getModel();
			List list = new ArrayList();
			if (tableModel != null && tableModel.getCurrentRow() != null) {
				Contract contract = (Contract) tableModel.getCurrentRow();
				cbbBeginDateTwo.setDate(contract.getBeginDate());
				String parentId = contract.getId();
			}
			tableModel = (JTableListModel) tbCancel.getModel();
			if (tableModel != null && tableModel.getCurrentRow() != null) {
				Contract contract = (Contract) tableModel.getCurrentRow();
				cbbBeginDateTwo.setDate(contract.getBeginDate());
				String parentId = contract.getId();
			}
			initTbSecond2(list);

			// tableModel = (JTableListModel) tbFourThird.getModel();
			// list = new ArrayList();
			// if (tableModel != null && tableModel.getCurrentRow() != null) {
			// Contract contract = (Contract) tableModel.getCurrentRow();
			// cbbBeginDate.setDate(contract.getBeginDate());
			// String parentId = contract.getId();
			// list = contractAction.findContractExgByParentId(new Request(
			// CommonVars.getCurrUser(), true), parentId);
			// }
			// initTbFour1(list);
			jSplitPane.setDividerLocation(0.8);
			getJSplitPane2().setDividerLocation(0.8);
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes pnMainFirst
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnMainFirst() {
		if (pnMainFirst == null) {
			pnMainFirst = new JPanel();
			pnMainFirst.setLayout(new BorderLayout());
			pnMainFirst.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return pnMainFirst;
	}

	/**
	 * This method initializes btnRefreshOne
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefreshOne() {
		if (btnRefreshOne == null) {
			btnRefreshOne = new JButton();
			btnRefreshOne.setText("查询");
			btnRefreshOne.setBounds(613, 11, 58, 23);
			btnRefreshOne.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new MyFindThread().start();
				}
			});
		}
		return btnRefreshOne;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerSize(4);
			jSplitPane.setDividerLocation(600);
			jSplitPane.setRightComponent(getPnExeContract());
			jSplitPane.setLeftComponent(getJScrollPane());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes pnExeContract
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnExeContract() {
		if (pnExeContract == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(2);
			pnExeContract = new JPanel();
			pnExeContract.setLayout(gridLayout);
			pnExeContract.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0,
					0, 0));
			pnExeContract.add(getJPanel1(), null);
			pnExeContract.add(getJPanel2(), null);
		}
		return pnExeContract;
	}

	/**
	 * This method initializes pnChoiseContract
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnChoiseContract() {
		if (pnChoiseContract == null) {
			pnChoiseContract = new JPanel();
			pnChoiseContract.setLayout(new BorderLayout());
			pnChoiseContract
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			pnChoiseContract.add(getJPanel10(), BorderLayout.NORTH);
			pnChoiseContract.add(getJScrollPane3(), BorderLayout.CENTER);
		}
		return pnChoiseContract;
	}

	/**
	 * This method initializes cbContractExe
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbContractExe() {
		if (cbContractExe == null) {
			cbContractExe = new JCheckBox();
			cbContractExe.setText("正在执行的合同");
			cbContractExe.setBounds(new Rectangle(3, 24, 122, 15));
			cbContractExe
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							selectContractItem();
						}
					});
		}
		return cbContractExe;
	}

	/**
	 * This method initializes cbContractCancel
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbContractCancel() {
		if (cbContractCancel == null) {
			cbContractCancel = new JCheckBox();
			cbContractCancel.setText("核销的合同");
			cbContractCancel.setBounds(new Rectangle(3, 45, 112, 18));
			cbContractCancel
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							selectContractItem();
						}
					});
		}
		return cbContractCancel;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			lbToFour = new JLabel();
			lbDeclareDate = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel.setPreferredSize(new Dimension(740, 43));
			lbDeclareDate.setBounds(6, 11, 83, 23);
			lbDeclareDate.setText("报关日期范围:");
			lbToFour.setBounds(251, 11, 19, 23);
			lbToFour.setText("至");
			jPanel.add(getBtnRefreshOne(), null);
			jPanel.add(getBtnPrintOne(), null);
			jPanel.add(lbDeclareDate, null);
			jPanel.add(getCbbBeginDateTwo(), null);
			jPanel.add(getCbbEndDateTwo(), null);
			jPanel.add(lbToFour, null);
			jPanel.add(getJPanel9(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes btnPrintOne
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrintOne() {
		if (btnPrintOne == null) {
			btnPrintOne = new JButton();
			btnPrintOne.setText("打印");
			btnPrintOne.setBounds(678, 11, 58, 23);
			btnPrintOne.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					printReport();
				}
			});
		}
		return btnPrintOne;
	}

	/**
	 * This method initializes cbbBeginDateOne
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDateOne() {
		if (cbbBeginDateOne == null) {
			cbbBeginDateOne = new JCalendarComboBox();
			cbbBeginDateOne.setBounds(90, 65, 87, 23);
		}
		return cbbBeginDateOne;
	}

	/**
	 * This method initializes cbbEndDateOne
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDateOne() {
		if (cbbEndDateOne == null) {
			cbbEndDateOne = new JCalendarComboBox();
			cbbEndDateOne.setBounds(209, 65, 87, 23);
		}
		return cbbEndDateOne;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
			jTabbedPane.addTab("出口成品统计", null, getPnSecond(), null);
			jTabbedPane.addTab("出口成品执行进度总表", null, getPnMainSecond(), null);
			// jTabbedPane.addTab("出口成品执行进度明细", null, getPn4(), null);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes pnMainSecond
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnMainSecond() {
		if (pnMainSecond == null) {
			pnMainSecond = new JPanel();
			pnMainSecond.setLayout(new BorderLayout());
			pnMainSecond.setToolTipText("成品执行情况");
			pnMainSecond.add(getJSplitPane(), BorderLayout.CENTER);
			pnMainSecond.add(getJPanel(), BorderLayout.NORTH);
		}
		return pnMainSecond;
	}

	/**
	 * This method initializes pnSecond
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnSecond() {
		if (pnSecond == null) {
			pnSecond = new JPanel();
			pnSecond.setLayout(new BorderLayout());
			// pnSecond.add(getJScrollPane2(), null);
			pnSecond.add(getJPanel3(), BorderLayout.NORTH);
			pnSecond.add(getJSplitPane2(), BorderLayout.CENTER);
			// pnSecond.add(getJScrollPane3(), null);
			// pnSecond.add(getJPanel2(), null);
			// pnSecond.add(getJPanel10(), null);
		}
		return pnSecond;
	}

	/**
	 * This method initializes pnTabel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnTabel() {
		if (pnTabel == null) {
			lbExcuteContract = new JLabel();
			pnTabel = new JPanel();
			pnTabel.setLayout(null);
			lbExcuteContract.setBounds(610, 5, 94, 21);
			lbExcuteContract.setText("正在执行的合同");
			pnTabel.add(getJPanel5(), null);
			pnTabel.add(lbExcuteContract, null);
			pnTabel.add(getJSplitPaneFirst(), null);
			pnTabel.add(getJScrollPaneFour(), null);
		}
		return pnTabel;
	}

	/**
	 * This method initializes tbFirst
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbFirst() {
		if (tbFirst == null) {
			tbFirst = new JTable();
		}
		return tbFirst;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getTbFirst());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jScrollPane3
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getJListSecond());
		}
		return jScrollPane3;
	}

	/**
	 * This method initializes jListSecond
	 * 
	 * @return javax.swing.JContractList
	 */
	private JContractList getJListSecond() {
		if (jListSecond == null) {
			jListSecond = new JContractList();
			jListSecond.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					Date beginDate = null;
					// Date endDate=null;
					int size = jListSecond.getModel().getSize();
					for (int i = 0; i < size; i++) {
						Contract contract = (Contract) jListSecond.getModel()
								.getElementAt(i);
						if (contract.isSelected()) {
							if (beginDate == null) {
								beginDate = contract.getBeginDate();
							} else {
								if (beginDate
										.compareTo(contract.getBeginDate()) > 0) {
									beginDate = contract.getBeginDate();
								}
							}
						}
					}
					if (beginDate != null) {
						cbbBeginDateOne.setDate(beginDate);
					}
				}
			});
		}
		return jListSecond;
	}

	/**
	 * This method initializes cbbBeginDateTwo
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDateTwo() {
		if (cbbBeginDateTwo == null) {
			cbbBeginDateTwo = new JCalendarComboBox();
			cbbBeginDateTwo.setBounds(97, 11, 147, 23);
		}
		return cbbBeginDateTwo;
	}

	/**
	 * This method initializes cbbEndDateTwo
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDateTwo() {
		if (cbbEndDateTwo == null) {
			cbbEndDateTwo = new JCalendarComboBox();
			cbbEndDateTwo.setBounds(277, 11, 147, 23);
		}
		return cbbEndDateTwo;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			lbTo = new JLabel();
			lbTime = new JLabel();
			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel3.setPreferredSize(new Dimension(743, 100));
			lbTime.setBounds(5, 65, 83, 23);
			lbTime.setText("按时间段查询");
			lbTo.setBounds(187, 65, 19, 23);
			lbTo.setText("至");
			jPanel3.add(getCbbBeginDateOne(), null);
			jPanel3.add(getCbbEndDateOne(), null);
			jPanel3.add(lbTime, null);
			jPanel3.add(lbTo, null);
			jPanel3.add(getBtnRefresh(), null);
			jPanel3.add(getBtnPrint(), null);
			jPanel3.add(getBtnExit(), null);
			jPanel3.add(getJPanel4(), null);
			jPanel3.add(getJPanel7(), null);
			// jPanel3.add(getBtnFormulas(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes tbFour
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbFour() {
		if (tbFour == null) {
			tbFour = new JTable();
			tbFour.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbFour
									.getModel();
							if (tableModel == null) {
								return;
							}
							ListSelectionModel lsm = (ListSelectionModel) e
									.getSource();
							List list = new ArrayList();
							int impExpType = -1;

							if (cbbImpExpType.getSelectedItem() != null) {
								impExpType = Integer
										.valueOf(((ItemProperty) cbbImpExpType
												.getSelectedItem()).getCode());
							}
							int state = -1;
							if (rbButton3.isSelected()) {
								state = CustomsDeclarationState.EFFECTIVED;
							} else if (rbButton4.isSelected()) {
								state = CustomsDeclarationState.NOT_EFFECTIVED;
							} else if (rbButton5.isSelected()) {
								state = state = CustomsDeclarationState.ALL;
							}
							if (!lsm.isSelectionEmpty()) {
								ContractExg contractExg = (ContractExg) tableModel
										.getCurrentRow();
								Date beginDate = cbbBeginDate.getDate();
								Date endDate = cbbEndDate.getDate();
								if (contractExg != null
										&& cbbImpExpType.getSelectedItem() != null
										&& beginDate != null && endDate != null) {
									if (beginDate.before(endDate)) {
										if (impExpType == ALL_SELECT_ITEM) {
											list = contractAnalyseAction
													.findExpProductExeDetail(
															new Request(
																	CommonVars
																			.getCurrUser()),
															contractExg, -1,
															beginDate, endDate,
															state);
										} else {
											list = contractAnalyseAction
													.findExpProductExeDetail(
															new Request(
																	CommonVars
																			.getCurrUser()),
															contractExg,
															impExpType,
															beginDate, endDate,
															state);
										}
									}

								}

							}
							initTbFour2(list, impExpType);
						}
					});
		}
		return tbFour;
	}

	/**
	 * This method initializes jScrollPane6
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane6() {
		if (jScrollPane6 == null) {
			jScrollPane6 = new JScrollPane();
			jScrollPane6.setViewportView(getTbFour());
		}
		return jScrollPane6;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			lbImpAndExp = new JLabel();
			lbFor = new JLabel();
			lbCustomsDate = new JLabel();
			jPanel5 = new JPanel();
			jPanel5.setLayout(null);
			jPanel5.setBounds(0, 430, 741, 50);
			jPanel5
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			lbCustomsDate.setText("报关日期范围");
			lbCustomsDate.setBounds(7, 14, 81, 24);
			lbFor.setBounds(197, 14, 17, 24);
			lbFor.setText("至");
			lbImpAndExp.setBounds(330, 14, 63, 22);
			lbImpAndExp.setText("进出口类型");
			jPanel5.add(lbCustomsDate, null);
			jPanel5.add(getCbbBeginDate(), null);
			jPanel5.add(lbFor, null);
			jPanel5.add(getCbbEndDate(), null);
			jPanel5.add(getBtnPrintLn(), null);
			jPanel5.add(getCbbImpExpType(), null);
			jPanel5.add(lbImpAndExp, null);
			jPanel5.add(getJPanel8(), null);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			lbExe = new JLabel();
			jPanel6 = new JPanel();
			jPanel6.setLayout(new BorderLayout());
			jPanel6.add(lbExe, BorderLayout.NORTH);
			lbExe.setText("正在执行的合同");
		}
		return jPanel6;
	}

	/**
	 * This method initializes btnRefresh
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefresh() {
		if (btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setBounds(521, 66, 69, 28);
			btnRefresh.setText("查询");
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new MyFindThread().start();
				}
			});
		}
		return btnRefresh;
	}

	/**
	 * This method initializes btnPrint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setBounds(597, 66, 66, 28);
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					printReport();
				}
			});
		}
		return btnPrint;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setBounds(669, 66, 65, 28);
			btnExit.setText("关闭");
			btnExit.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					DgProductExecuteAnalyse.this.dispose();
				}

			});
		}
		return btnExit;
	}

	/**
	 * This method initializes jPanel7
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			jPanel7 = new JPanel();
			jPanel7.setLayout(null);
			jPanel7
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
									"成品查询条件",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									null, null));
			jPanel7.setBounds(new Rectangle(6, 8, 729, 53));
			jPanel7.add(getRbProductName(), null);
			jPanel7.add(getRbProductNameSpec(), null);
			jPanel7.add(getRbProductNameSpecCode(), null);
			jPanel7.add(getRbCode(), null);
			jPanel7.add(getRbCodeName(), null);
			jPanel7.add(getRbNameSpecCodeUnit(), null);
		}
		return jPanel7;
	}

	/**
	 * This method initializes rbProductName
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbProductName() {
		if (rbProductName == null) {
			rbProductName = new JRadioButton();
			rbProductName.setBounds(87, 13, 142, 16);
			rbProductName.setText("成品名称");
			rbProductName.setSelected(true);
			rbProductName
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							SEARCH_TYPE = SearchType.NAME;
						}
					});
		}
		return rbProductName;
	}

	/**
	 * This method initializes rbProductNameSpec
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbProductNameSpec() {
		if (rbProductNameSpec == null) {
			rbProductNameSpec = new JRadioButton();
			rbProductNameSpec.setBounds(87, 31, 142, 16);
			rbProductNameSpec.setText("成品名称＋规格");
			rbProductNameSpec
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							SEARCH_TYPE = SearchType.NAME_SPEC;
						}
					});
		}
		return rbProductNameSpec;
	}

	/**
	 * This method initializes rbProductNameSpecCode
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbProductNameSpecCode() {
		if (rbProductNameSpecCode == null) {
			rbProductNameSpecCode = new JRadioButton();
			rbProductNameSpecCode.setBounds(469, 29, 161, 16);
			rbProductNameSpecCode.setText("成品名称＋规格＋编码");
			rbProductNameSpecCode
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							SEARCH_TYPE = SearchType.NAME_SPEC_CODE;
						}
					});
		}
		return rbProductNameSpecCode;
	}

	/**
	 * This method initializes rbCode
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbCode() {
		if (rbCode == null) {
			rbCode = new JRadioButton();
			rbCode.setBounds(297, 13, 142, 16);
			rbCode.setText("商品编码");
			rbCode.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					SEARCH_TYPE = SearchType.CODE;
				}
			});
		}
		return rbCode;
	}

	/**
	 * This method initializes rbCodeName
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbCodeName() {
		if (rbCodeName == null) {
			rbCodeName = new JRadioButton();
			rbCodeName.setBounds(297, 31, 142, 16);
			rbCodeName.setText("商品编码＋成品名称");
			rbCodeName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					SEARCH_TYPE = SearchType.CODE_NAME;
				}
			});
		}
		return rbCodeName;
	}

	/**
	 * This method initializes rbNameSpecCodeUnit
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbNameSpecCodeUnit() {
		if (rbNameSpecCodeUnit == null) {
			rbNameSpecCodeUnit = new JRadioButton();
			rbNameSpecCodeUnit.setBounds(469, 11, 249, 16);
			rbNameSpecCodeUnit.setText("成品名称＋规格＋编码＋单位");
			rbNameSpecCodeUnit
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							SEARCH_TYPE = SearchType.NAME_SPEC_CODE_UNIT;
						}
					});

		}
		return rbNameSpecCodeUnit;
	}

	/**
	 * This method initializes cbbBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(92, 14, 103, 23);
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes cbbEndDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(213, 14, 103, 23);
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes btnPrintLn
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrintLn() {
		if (btnPrintLn == null) {
			btnPrintLn = new JButton();
			btnPrintLn.setBounds(681, 16, 58, 23);
			btnPrintLn.setText("打印");
			btnPrintLn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					printReport();
				}
			});
		}
		return btnPrintLn;
	}

	/**
	 * This method initializes cbbImpExpType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpExpType() {
		if (cbbImpExpType == null) {
			cbbImpExpType = new JComboBox();
			cbbImpExpType.setBounds(395, 15, 103, 23);
			cbbImpExpType.addItemListener(new ItemListener() {

				public void itemStateChanged(ItemEvent e) {

					if (e.getStateChange() == ItemEvent.SELECTED) {
						cbbImpExpType.setCursor(new Cursor(Cursor.WAIT_CURSOR));
						JTableListModel tableModel = (JTableListModel) tbFour
								.getModel();
						if (tableModel == null) {
							return;
						}
						List list = new ArrayList();
						int impExpType = -1;
						if (cbbImpExpType.getSelectedItem() != null) {
							impExpType = Integer
									.valueOf(((ItemProperty) cbbImpExpType
											.getSelectedItem()).getCode());
						}
						int state = -1;
						if (rbButton3.isSelected()) {
							state = CustomsDeclarationState.EFFECTIVED;
						} else if (rbButton4.isSelected()) {
							state = CustomsDeclarationState.NOT_EFFECTIVED;
						} else if (rbButton5.isSelected()) {
							state = state = CustomsDeclarationState.ALL;
						}
						ContractExg contractExg = (ContractExg) tableModel
								.getCurrentRow();
						Date beginDate = cbbBeginDate.getDate();
						Date endDate = cbbEndDate.getDate();
						if (contractExg != null
								&& cbbImpExpType.getSelectedItem() != null
								&& beginDate != null && endDate != null) {
							if (beginDate.before(endDate)) {
								if (impExpType == ALL_SELECT_ITEM) {
									list = contractAnalyseAction
											.findExpProductExeDetail(
													new Request(CommonVars
															.getCurrUser()),
													contractExg, -1, beginDate,
													endDate, state);
								} else {
									list = contractAnalyseAction
											.findExpProductExeDetail(
													new Request(CommonVars
															.getCurrUser()),
													contractExg, impExpType,
													beginDate, endDate, state);
								}
							}

						}
						initTbFour2(list, impExpType);
					}
					cbbImpExpType.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}

			});
		}
		return cbbImpExpType;
	}

	/**
	 * This method initializes jSplitPaneFirst
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPaneFirst() {
		if (jSplitPaneFirst == null) {
			jSplitPaneFirst = new JSplitPane();
			jSplitPaneFirst.setBounds(0, 0, 555, 429);
			jSplitPaneFirst.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPaneFirst.setTopComponent(getJScrollPane6());
			jSplitPaneFirst.setDividerLocation(175);
			jSplitPaneFirst.setDividerSize(2);
			jSplitPaneFirst.setBottomComponent(getJScrollPaneEighth());
		}
		return jSplitPaneFirst;
	}

	/**
	 * This method initializes tbFourTwo
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbFourTwo() {
		if (tbFourTwo == null) {
			tbFourTwo = new JTable();
		}
		return tbFourTwo;
	}

	/**
	 * This method initializes jScrollPaneEighth
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneEighth() {
		if (jScrollPaneEighth == null) {
			jScrollPaneEighth = new JScrollPane();
			jScrollPaneEighth.setViewportView(getTbFourTwo());
		}
		return jScrollPaneEighth;
	}

	/**
	 * This method initializes tbFourThird
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbFourThird() {
		if (tbFourThird == null) {
			tbFourThird = new JTable();
			tbFourThird.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbFourThird
									.getModel();
							if (tableModel == null) {
								return;
							}
							// ListSelectionModel lsm = (ListSelectionModel) e
							// .getSource();
							List list = new ArrayList();
							// if (!lsm.isSelectionEmpty()) {
							if (tableModel.getCurrentRow() != null) {
								Contract contract = (Contract) tableModel
										.getCurrentRow();
								cbbBeginDate.setDate(contract.getBeginDate());
								String parentId = contract.getId();
								list = contractAction
										.findContractExgByParentId(
												new Request(CommonVars
														.getCurrUser(), true),
												parentId);
							}
							// }
							initTbFour1(list);
						}
					});
		}
		return tbFourThird;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbSecond() {
		if (tbSecond == null) {
			tbSecond = new JTable();
		}
		return tbSecond;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbSecond());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tbExe
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbExe() {
		if (tbExe == null) {
			tbExe = new JTable();
			tbExe.addFocusListener(new   FocusListener(){ 
			    public   void   focusGained(FocusEvent   e)   {
			    	 tbCancel.clearSelection(); 
			    } 
			    public   void   focusLost(FocusEvent   e)   { 
			    } 
			}); 
			tbExe.getSelectionModel().addListSelectionListener(
					
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbExe
									.getModel();
							if (tableModel == null) {
								return;
							}
							ListSelectionModel lsm = (ListSelectionModel) e
									.getSource();
							List list = new ArrayList();
							if (!lsm.isSelectionEmpty()) {
								if (tableModel.getCurrentRow() != null) {
									Contract contract = (Contract) tableModel
											.getCurrentRow();
									cbbBeginDateTwo.setDate(contract
											.getBeginDate());
									String parentId = contract.getId();
									// list = contractAction
									// .findContractExgByParentId(
									// new Request(CommonVars
									// .getCurrUser(),true),
									// parentId);
								}
							}
							initTbSecond2(list);
						}
					});
		}
		return tbExe;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbExe());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(rbProductName);
			buttonGroup.add(rbProductNameSpec);
			buttonGroup.add(rbProductNameSpecCode);
			buttonGroup.add(rbCode);
			buttonGroup.add(rbCodeName);
			buttonGroup.add(rbNameSpecCodeUnit);
		}
		return buttonGroup;
	}

	/**
	 * This method initializes buttonGroups
	 * 
	 * @return javax.swing.ButtonGroup
	 */

	private ButtonGroup getButtonGroups() {
		if (buttonGroups == null) {
			buttonGroups = new ButtonGroup();
			buttonGroups.add(rbButton);
			buttonGroups.add(rbButton1);
			buttonGroups.add(rbButton2);
			// buttonGroups.add(rbButton3);
			// buttonGroups.add(rbButton4);
			// buttonGroups.add(rbButton5);
			// buttonGroups.add(rbButton6);
			// buttonGroups.add(rbButton7);
			// buttonGroups.add(rbButton8);
		}
		return buttonGroups;
	}

	/**
	 * This method initializes jScrollPaneFour
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneFour() {
		if (jScrollPaneFour == null) {
			jScrollPaneFour = new JScrollPane();
			jScrollPaneFour.setBounds(556, 30, 185, 398);
			jScrollPaneFour.setViewportView(getTbFourThird());
		}
		return jScrollPaneFour;
	}

	/**
	 * 初始化数据
	 * 
	 */
	private void initUIComponents() {
		//
		// 初始化面板一
		//
		initTbFirst(new ArrayList());
		//
		// 初始化面板三 和面板一的合同表
		//
		initTbFour3AndTbSecond1();
		// initTbFour1(new ArrayList());
		//
		// 初始化出口类型
		//
		// this.cbbImpExpType.removeAllItems();
		// this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
		// ALL_SELECT_ITEM).toString(), "全部"));
		// this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
		// ImpExpType.DIRECT_EXPORT).toString(), "成品出口"));
		// this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
		// ImpExpType.TRANSFER_FACTORY_EXPORT).toString(), "转厂出口"));
		// this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
		// ImpExpType.BACK_FACTORY_REWORK).toString(), "退厂返工"));
		// this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
		// ImpExpType.REWORK_EXPORT).toString(), "返工复出"));
		// this.cbbImpExpType.setSelectedItem(null);
		// CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
		// this.cbbImpExpType);
		// this.cbbImpExpType.setRenderer(CustomBaseRender.getInstance()
		// .getRender());

	}

	/**
	 * 查询
	 */
	private void search() {
		if (this.jTabbedPane.getSelectedIndex() == 0) {
			List contracts = jListSecond.getSelectedContracts();
			if (contracts.size() <= 0) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(this, "没有选中的合同!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			int state = -1;
			if (rbButton.isSelected()) {
				state = CustomsDeclarationState.EFFECTIVED;
			} else if (rbButton1.isSelected()) {
				state = CustomsDeclarationState.NOT_EFFECTIVED;
			} else if (rbButton2.isSelected()) {
				state = state = CustomsDeclarationState.ALL;
			}
//			List list = new ArrayList();
			Date beginDate = cbbBeginDateOne.getDate();
			Date endDate = cbbEndDateOne.getDate();
			// if (beginDate != null && endDate != null) {
			// if (beginDate.before(endDate)) {
			final List list = this.contractAnalyseAction.findExpFinishProductStat(
					new Request(CommonVars.getCurrUser()), contracts,
					beginDate, endDate, SEARCH_TYPE, state);
			// }
			// }
			
			
			SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    initTbFirst(list);
                   }
               });

		} else if (this.jTabbedPane.getSelectedIndex() == 1) {
			JTableListModel tableModel=null;
			if(tbExe.getSelectedRow()!=-1)
				tableModel = (JTableListModel) tbExe.getModel();
			else if(tbCancel.getSelectedRow()!=-1)
				tableModel = (JTableListModel) tbCancel.getModel();
			Contract contract = (Contract) tableModel.getCurrentRow();
			if (contract == null) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(this, "没有选中的合同!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			int state = -1;
			if (rbButton6.isSelected()) {
				state = CustomsDeclarationState.EFFECTIVED;
			} else if (rbButton7.isSelected()) {
				state = CustomsDeclarationState.NOT_EFFECTIVED;
			} else if (rbButton8.isSelected()) {
				state = state = CustomsDeclarationState.ALL;
			}
			List list = new ArrayList();
			Date beginDate = cbbBeginDateTwo.getDate();
			Date endDate = cbbEndDateTwo.getDate();
			final List list2 = this.contractAnalyseAction.findExpProductExeTotal(
					new Request(CommonVars.getCurrUser()), contract, beginDate,
					endDate, state);
			
			
			SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    initTbSecond2(list2);
                   }
               });
			
		} else if (this.jTabbedPane.getSelectedIndex() == 2) {

		}
	}

	/**
	 * 选择合同项目
	 */
	private void selectContractItem() {
		jListSecond.showContractData(this.cbContractExe.isSelected(),
				this.cbContractCancel.isSelected());
	}

	/**
	 * 初始出口成品统计 (面板一)
	 * 
	 * @param list
	 */
	JTableListModel tableModelTbSecond = null;

	private void initTbFirst(List list) {
		tableModelTbSecond = new JTableListModel(tbFirst, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();

						list.add(addColumn("商品编码", "complexCode", 100));
						list.add(addColumn("品名规格", "nameSpec", 100));
						list.add(addColumn("单位", "unit.name", 100));
						list
								.add(addColumn("合同总定量", "contractTotalAmount",
										100));
						list.add(addColumn("总出口量", "totalExpAmount", 100));
						list.add(addColumn("成品出口总量", "finishProductExpAmount",
								100));
						list.add(addColumn("转厂出口总量", "transferExpAmount", 100));
						list.add(addColumn("退厂返工总量", "backFactoryRework", 100));
						list.add(addColumn("返工复出总量", "reworkExpAmount", 100));

						list.add(addColumn("可出口总量", "canExpAmount", 100));
						list.add(addColumn("关封余量",
								"customsEnvelopRemainAmount", 100));
						list
								.add(addColumn("可直接出口量", "canDirectExpAmount",
										100));
						list.add(addColumn("完成百分比", "completeScale", 60));//
						return list;
					}
				});

		// 截取小数位
		Integer decimalLength = 2;
		if (parameterSet != null
				&& parameterSet.getReportDecimalLength() != null)
			decimalLength = parameterSet.getReportDecimalLength();
		tableModelTbSecond.setAllColumnsFractionCount(decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModelTbSecond.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());
		// List<JTableListColumn> cm = tableModelTbSecond.getColumns();
		// cm.get(4).setFractionCount(decimalLength);
		// cm.get(5).setFractionCount(decimalLength);
		// cm.get(6).setFractionCount(decimalLength);
		// cm.get(7).setFractionCount(decimalLength);
		// cm.get(8).setFractionCount(decimalLength);
		// cm.get(9).setFractionCount(decimalLength);
		// cm.get(10).setFractionCount(decimalLength);
		// cm.get(11).setFractionCount(decimalLength);
		// cm.get(12).setFractionCount(decimalLength);

		TableColorRender.setTableRowColor(tbFirst, tableModelTbSecond
				.getRowCount() - 1);
	}

	/** 初始化合同表(面板二,面板三) */
	JTableListModel tableModelTbFour3 = null;

	private JPanel jPanel4 = null;

	private JRadioButton rbButton = null;

	private JRadioButton rbButton1 = null;

	private JRadioButton rbButton2 = null;

	private JPanel jPanel8 = null;

	private JRadioButton rbButton3 = null;

	private JRadioButton rbButton4 = null;

	private JRadioButton rbButton5 = null;

	private JPanel jPanel9 = null;

	private JRadioButton rbButton6 = null;

	private JRadioButton rbButton7 = null;

	private JRadioButton rbButton8 = null;

	private JPanel jPanel10 = null;

	private JRadioButton rbButton9 = null;

	private JRadioButton rbButton10 = null;

	private ButtonGroup buttonGroup1 = null;

	private ButtonGroup buttonGroup2 = null;

	private JSplitPane jSplitPane2 = null;

	private JButton btnFormulas = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel11 = null;

	private JPanel jPanel61 = null;

	private JLabel lbCancel = null;

	private JPanel jPanel111 = null;

	private JScrollPane jScrollPane11 = null;

	private JTable tbCancel = null;

	/**
	 * 初始化合同备案表头
	 */

	private void initTbFour3AndTbSecond1() {
		//
		// 查询没有审核的合同记录
		//
		List list = this.contractAction.findContractByProcessExe(new Request(
				CommonVars.getCurrUser(), true));
		// tableModelTbFour3 = new JTableListModel(tbFourThird, list,
		// new JTableListModelAdapter() {
		// public List InitColumns() {
		// List<JTableListColumn> list = new Vector<JTableListColumn>();
		// list.add(addColumn("手册编号", "emsNo", 100));
		// list.add(addColumn("进口合同号", "impContractNo", 100));
		// list.add(addColumn("出口合同号", "expContractNo", 100));
		// list.add(addColumn("进口总金额", "imgAmount", 100));
		// list.add(addColumn("出口总金额", "exgAmount", 100));
		// list.add(addColumn("币制", "curr.name", 100));
		// return list;
		// }
		// });
		tableModelTbExcute = new JTableListModel(tbExe, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("手册编号", "emsNo", 100));
						list.add(addColumn("进口合同号", "impContractNo", 100));
						list.add(addColumn("出口合同号", "expContractNo", 100));
						list.add(addColumn("进口总金额", "imgAmount", 100));
						list.add(addColumn("出口总金额", "exgAmount", 100));
						list.add(addColumn("币制", "curr.name", 100));
						return list;
					}
				});
		
		// 截取小数位
		Integer decimalLength = 2;
		if (parameterSet != null
				&& parameterSet.getReportDecimalLength() != null)
			decimalLength = parameterSet.getReportDecimalLength();
		tableModelTbExcute.setAllColumnsFractionCount(decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModelTbExcute.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());
		// List<JTableListColumn> cm = tableModelTbExcuteAll.getColumns();
		// cm.get(4).setFractionCount(decimalLength);
		// cm.get(5).setFractionCount(decimalLength);

		// tbFourThird.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		tbExe
				.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		if (list.size() > 0) {
			// tbFourThird.setRowSelectionInterval(0, 0);
			tbExe.setRowSelectionInterval(0, 0);
		}
		
		//已核销合同
		List list1 = this.contractAction.findContract(new Request(CommonVars
				.getCurrUser(), true), true);
		tableModelTbCancel = new JTableListModel(tbCancel, list1,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("手册编号", "emsNo", 100));
						list.add(addColumn("进口合同号", "impContractNo", 100));
						list.add(addColumn("出口合同号", "expContractNo", 100));
						list.add(addColumn("进口总金额", "imgAmount", 100));
						list.add(addColumn("出口总金额", "exgAmount", 100));
						list.add(addColumn("币制", "curr.name", 100));
						return list;
					}
				});
		if (parameterSet != null
				&& parameterSet.getReportDecimalLength() != null)
			decimalLength = parameterSet.getReportDecimalLength();
		tableModelTbCancel.setAllColumnsFractionCount(decimalLength);
		 other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModelTbCancel.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());
		tbCancel
		.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
if (list1.size() > 0) {
	// tbFourThird.setRowSelectionInterval(0, 0);
	tbCancel.setRowSelectionInterval(0, 0);
}
	}

	/**
	 * 初始化出品成品执行进度总表
	 * 
	 */
	private void initTbSecond2(List list) {
		tableModelTbHead = new JTableListModel(tbSecond, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("商品编码", "contractExg.complex.code",
								100));
						list.add(addColumn("商品名称", "contractExg.name", 100));
						list.add(addColumn("规格型号", "contractExg.spec", 100));
						list.add(addColumn("单位", "contractExg.unit.name", 100));
						list.add(addColumn("单价", "contractExg.unitPrice", 100));
						list.add(addColumn("合同定量", "contractExg.exportAmount",
								100));
						list.add(addColumn("币制",
								"contractExg.contract.curr.name", 100));
						list.add(addColumn("消费国", "contractExg.country.name",
								100));
						list.add(addColumn("加工费单价",
								"contractExg.processUnitPrice", 100));
						list.add(addColumn("加工费总价",
								"contractExg.processTotalPrice", 100));

						list.add(addColumn("出口合计", "expTotal", 100));

						list.add(addColumn("成品出口数量", "finishProductExpAmount",
								100));

						list.add(addColumn("转厂出口数量", "transferExpAmount", 100));

						list.add(addColumn("退厂返工总量", "backFactoryRework", 100));

						list.add(addColumn("返工复出总量", "reworkExpAmount", 100));
						list.add(addColumn("可出口总量", "canExpAmount", 100));
						return list;
					}
				});

		// 截取小数位
		Integer decimalLength = 2;
		if (parameterSet != null
				&& parameterSet.getReportDecimalLength() != null)
			decimalLength = parameterSet.getReportDecimalLength();
		tableModelTbHead.setAllColumnsFractionCount(decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModelTbHead.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());
		// List<JTableListColumn> cm = tableModelTbHead.getColumns();
		//
		// cm.get(5).setFractionCount(decimalLength);
		// cm.get(6).setFractionCount(decimalLength);
		// cm.get(9).setFractionCount(decimalLength);
		// cm.get(10).setFractionCount(decimalLength);
		// cm.get(11).setFractionCount(decimalLength);
		// cm.get(12).setFractionCount(decimalLength);
		// cm.get(13).setFractionCount(decimalLength);
		// cm.get(14).setFractionCount(decimalLength);
		// cm.get(15).setFractionCount(decimalLength);
		// cm.get(16).setFractionCount(decimalLength);
	}

	/**
	 * 初始化成品总表
	 * 
	 */
	private void initTbFour1(List list) {
		tableModelTbTotal = new JTableListModel(tbFour, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("成品序号", "seqNum", 100));
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("商品名称", "name", 100));
						list.add(addColumn("规格型号", "spec", 100));
						list.add(addColumn("单位", "unit.name", 100));
						list.add(addColumn("单位重量", "unitNetWeight", 100));
						list.add(addColumn("单价", "unitPrice", 100));
						list.add(addColumn("数量", "exportAmount", 100));
						list.add(addColumn("凭证序号", "credenceNo", 100));
						return list;
					}
				});

		// 截取小数位
		Integer decimalLength = 2;
		if (parameterSet != null
				&& parameterSet.getReportDecimalLength() != null)
			decimalLength = parameterSet.getReportDecimalLength();
		tableModelTbTotal.setAllColumnsFractionCount(decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModelTbTotal.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());
		// List<JTableListColumn> cm = tableModelTbTotal.getColumns();
		//
		// cm.get(6).setFractionCount(decimalLength);
		// cm.get(7).setFractionCount(decimalLength);
		// cm.get(8).setFractionCount(decimalLength);

	}

	/**
	 * 初始化成品总表
	 * 
	 */
	private void initTbFour2(List list, int impExpType) {
		final int type = impExpType;
		tableModelTbExgTotal = new JTableListModel(tbFourTwo, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("报关单号", "applyToCustomBillNo", 100));
						list.add(addColumn("报关日期", "applyToCustomDate", 100));
						list.add(addColumn("运输工具及装箱单号", "conveyance", 120));

						if (type == ALL_SELECT_ITEM
								|| type == ImpExpType.DIRECT_EXPORT) {
							list.add(addColumn("成品出口数量", "directExportAmount",
									100));
						}
						if (type == ALL_SELECT_ITEM
								|| type == ImpExpType.TRANSFER_FACTORY_EXPORT) {
							list.add(addColumn("转厂出口数量",
									"transferFactoryExport", 100));
						}
						if (type == ALL_SELECT_ITEM
								|| type == ImpExpType.BACK_FACTORY_REWORK) {
							list.add(addColumn("退厂返工数量", "backFactoryRework",
									100));
						}
						if (type == ALL_SELECT_ITEM
								|| type == ImpExpType.REWORK_EXPORT) {
							list.add(addColumn("返工复出数量", "reworkExport", 100));
						}
						list.add(addColumn("客户名称", "customer", 100));
						return list;
					}
				});

		// 截取小数位
		Integer decimalLength = 2;
		if (parameterSet != null
				&& parameterSet.getReportDecimalLength() != null)
			decimalLength = parameterSet.getReportDecimalLength();

		List<JTableListColumn> cm = tableModelTbExgTotal.getColumns();

		if (type == ALL_SELECT_ITEM) {
			cm.get(4).setFractionCount(decimalLength);
			cm.get(5).setFractionCount(decimalLength);
			cm.get(6).setFractionCount(decimalLength);
			cm.get(7).setFractionCount(decimalLength);

		}
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModelTbExgTotal.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());
	}

	/**
	 * 打印
	 */
	private void printReport() {
		String company = CommonVars.getCurrUser().getCompany().getName();
		this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		int flag = this.jTabbedPane.getSelectedIndex();
		switch (flag) {
		case 0: // 
			try {
				//
				// 合同报表
				//
				List listContract = this.jListSecond.getSelectedContracts();
				if (listContract.isEmpty()) {
					JOptionPane.showMessageDialog(this, "请先选中合同记录!!!", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					return;
				}
				List<TempContractNo> tempContractNoList = new ArrayList<TempContractNo>();
				for (int i = 0; i < listContract.size(); i++) {
					TempContractNo temp = new TempContractNo();
					Contract contract = (Contract) listContract.get(i);
					String contractNo = contract.getEmsNo()
							+ contract.getImpContractNo();
					temp.setContractNo(contractNo);
					tempContractNoList.add(temp);
				}
				if (tempContractNoList == null
						|| tempContractNoList.size() <= 0) {
					JOptionPane.showMessageDialog(DgProductExecuteAnalyse.this,
							"没有数据可打印", "提示", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				CustomReportDataSource contractDs = new CustomReportDataSource(
						tempContractNoList);
				InputStream tempContractNoListSubReportStream = DgMaterielExecuteAnalyse.class
						.getResourceAsStream("report/TempContractNoList.jasper");
				JasperReport tempContractNoSubReport = (JasperReport) JRLoader
						.loadObject(tempContractNoListSubReportStream);

				//
				// 主报表
				//
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("contractSubReport", tempContractNoSubReport);
				parameters.put("contractSubReportDataSource", contractDs);
				parameters.put("companyName", company);
				parameters.put("searchBeginDate", CommonVars
						.dateToString(cbbBeginDateOne.getDate()));
				parameters.put("searchEndDate", CommonVars
						.dateToString(cbbEndDateOne.getDate()));
				List list = tableModelTbSecond.getList();
				CustomReportDataSource ds = new CustomReportDataSource(list);
				InputStream masterReportStream = DgMaterielExecuteAnalyse.class
						.getResourceAsStream("report/ProductExecuteAnalyse1.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;
		case 1: // 非套打成品表
			try {

				//
				// 主报表
				//
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("companyName", company);
				parameters.put("searchBeginDate", CommonVars
						.dateToString(cbbBeginDateTwo.getDate()));
				parameters.put("searchEndDate", CommonVars
						.dateToString(cbbEndDateTwo.getDate()));
				Contract contract = (Contract) tableModelTbExcute
						.getCurrentRow();
				if (contract != null) {
					parameters.put("beginDate", CommonVars
							.dateToString(contract.getBeginDate()));
					parameters.put("contractNo", contract.getImpContractNo());
					parameters.put("effectiveDate", CommonVars
							.dateToString(contract.getEndDate()));
					parameters.put("emsNo", contract.getEmsNo());
				}
				List list = tableModelTbHead.getList();
				if (list == null || list.size() <= 0) {
					JOptionPane.showMessageDialog(DgProductExecuteAnalyse.this,
							"没有数据可打印", "提示", JOptionPane.INFORMATION_MESSAGE);
					this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					return;
				}
				CustomReportDataSource ds = new CustomReportDataSource(list);
				InputStream masterReportStream = DgMaterielExecuteAnalyse.class
						.getResourceAsStream("report/ProductExecuteAnalyse2.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);

			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;
		case 2: // 非套打成品表
			try {
				//
				// 主报表
				//
				Map<String, Object> parameters = new HashMap<String, Object>();
				Contract contract = (Contract) tableModelTbFour3
						.getCurrentRow();
				if (contract != null) {
					parameters.put("beginDate", CommonVars
							.dateToString(contract.getBeginDate()));
					parameters.put("contractNo", contract.getImpContractNo());
					parameters.put("effectiveDate", CommonVars
							.dateToString(contract.getEndDate()));
					parameters.put("emsNo", contract.getEmsNo());
				}
				parameters.put("companyName", company);
				parameters.put("complexCode", company);
				parameters.put("finalCanImportAmount", "0");
				ItemProperty itemProperty = ((ItemProperty) this.cbbImpExpType
						.getSelectedItem());
				if (itemProperty != null) {
					parameters.put("impExpType",
							((ItemProperty) this.cbbImpExpType
									.getSelectedItem()).getName());
				}

				ContractExg contractExg = (ContractExg) tableModelTbTotal
						.getCurrentRow();
				if (contractExg != null) {
					parameters.put("name", contractExg.getName());
					parameters.put("no",
							contractExg.getCredenceNo() == null ? ""
									: contractExg.getCredenceNo().toString());
					parameters.put("spec", contractExg.getSpec());
					parameters.put("unit", contractExg.getUnit() == null ? ""
							: contractExg.getUnit().getName());
					parameters.put("unitPrice", "");
					parameters
							.put("unitWeight",
									contractExg.getUnitNetWeight() == null ? ""
											: contractExg.getUnitNetWeight()
													.toString());
					parameters.put("unitPrice",
							contractExg.getUnitPrice() == null ? ""
									: contractExg.getUnitPrice().toString());
				}
				// paramters 还要改一下

				parameters.put("searchBeginDate", CommonVars
						.dateToString(cbbBeginDate.getDate()));
				parameters.put("searchEndDate", CommonVars
						.dateToString(cbbEndDate.getDate()));
				List list = tableModelTbExgTotal.getList();
				if (list == null || list.size() <= 0) {
					JOptionPane.showMessageDialog(DgProductExecuteAnalyse.this,
							"没有数据可打印", "提示", JOptionPane.INFORMATION_MESSAGE);
					this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					return;
				}
				CustomReportDataSource ds = new CustomReportDataSource(list);
				InputStream masterReportStream = DgMaterielExecuteAnalyse.class
						.getResourceAsStream("report/ProductExecuteAnalyse3.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);

			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;

		}
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(null);
			jPanel4.setBounds(new Rectangle(297, 66, 212, 28));
			jPanel4.setBorder(BorderFactory.createTitledBorder(null, "",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			jPanel4.add(getRbButton(), null);
			jPanel4.add(getRbButton1(), null);
			jPanel4.add(getRbButton2(), null);
		}
		return jPanel4;
	}

	/**
	 * This method initializes rbButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbButton() {
		if (rbButton == null) {
			rbButton = new JRadioButton();
			rbButton.setBounds(new java.awt.Rectangle(7, 3, 65, 22));
			rbButton.setText("\u5df2\u751f\u6548");
			rbButton.setSelected(true);
		}
		return rbButton;
	}

	/**
	 * This method initializes rbButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbButton1() {
		if (rbButton1 == null) {
			rbButton1 = new JRadioButton();
			rbButton1.setBounds(new java.awt.Rectangle(77, 3, 65, 20));
			rbButton1.setText("\u672a\u751f\u6548");
		}
		return rbButton1;
	}

	/**
	 * This method initializes rbButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbButton2() {
		if (rbButton2 == null) {
			rbButton2 = new JRadioButton();
			rbButton2.setBounds(new Rectangle(149, 3, 65, 20));
			rbButton2.setText("\u5168\u90e8");
		}
		return rbButton2;
	}

	/**
	 * This method initializes jPanel8
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel8() {
		if (jPanel8 == null) {
			jPanel8 = new JPanel();
			jPanel8.setLayout(null);
			jPanel8.setBounds(new java.awt.Rectangle(497, 15, 182, 24));
			jPanel8.setBorder(BorderFactory.createTitledBorder(null, "",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			jPanel8.add(getRbButton3(), null);
			jPanel8.add(getRbButton4(), null);
			jPanel8.add(getRbButton5(), null);
		}
		return jPanel8;
	}

	/**
	 * This method initializes rbButton3
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbButton3() {
		if (rbButton3 == null) {
			rbButton3 = new JRadioButton();
			rbButton3.setBounds(new java.awt.Rectangle(0, 3, 67, 21));
			rbButton3.setText("\u5df2\u751f\u6548");
		}
		return rbButton3;
	}

	/**
	 * This method initializes rbButton4
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbButton4() {
		if (rbButton4 == null) {
			rbButton4 = new JRadioButton();
			rbButton4.setBounds(new java.awt.Rectangle(66, 3, 69, 21));
			rbButton4.setText("\u672a\u751f\u6548");
		}
		return rbButton4;
	}

	/**
	 * This method initializes rbButton5
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbButton5() {
		if (rbButton5 == null) {
			rbButton5 = new JRadioButton();
			rbButton5.setBounds(new java.awt.Rectangle(137, 3, 64, 22));
			rbButton5.setText("\u5168\u90e8");
			rbButton5.setSelected(true);
		}
		return rbButton5;
	}

	/**
	 * This method initializes jPanel9
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel9() {
		if (jPanel9 == null) {
			jPanel9 = new JPanel();
			jPanel9.setLayout(null);
			jPanel9.setBounds(new java.awt.Rectangle(424, 11, 188, 23));
			jPanel9.setBorder(BorderFactory.createTitledBorder(null, "",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			jPanel9.add(getRbButton6(), null);
			jPanel9.add(getRbButton7(), null);
			jPanel9.add(getRbButton8(), null);
		}
		return jPanel9;
	}

	/**
	 * This method initializes rbButton6
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbButton6() {
		if (rbButton6 == null) {
			rbButton6 = new JRadioButton();
			rbButton6.setBounds(new java.awt.Rectangle(0, 3, 67, 21));
			rbButton6.setText("\u5df2\u751f\u6548");
			rbButton6.setSelected(true);
		}

		return rbButton6;
	}

	/**
	 * This method initializes rbButton7
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbButton7() {
		if (rbButton7 == null) {
			rbButton7 = new JRadioButton();
			rbButton7.setBounds(new java.awt.Rectangle(66, 3, 69, 21));
			rbButton7.setText("\u672a\u751f\u6548");
		}
		return rbButton7;
	}

	/**
	 * This method initializes rbButton8
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbButton8() {
		if (rbButton8 == null) {
			rbButton8 = new JRadioButton();
			rbButton8.setBounds(new java.awt.Rectangle(137, 3, 64, 22));
			rbButton8.setText("\u5168\u90e8");
		}
		return rbButton8;
	}

	/**
	 * This method initializes jPanel10
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel10() {
		if (jPanel10 == null) {
			jPanel10 = new JPanel();
			jPanel10.setLayout(null);
			// jPanel10.setBounds(new Rectangle(578, 51, 122, 73));

			jPanel10.setPreferredSize(new Dimension(158, 81));
			jPanel10.add(getRbButton9(), null);
			jPanel10.add(getRbButton10(), null);
			jPanel10.add(getCbContractExe(), null);
			jPanel10.add(getCbContractCancel(), null);
		}
		return jPanel10;
	}

	/**
	 * This method initializes rbButton9
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbButton9() {
		if (rbButton9 == null) {
			rbButton9 = new JRadioButton();
			rbButton9.setBounds(new Rectangle(3, 1, 55, 22));
			rbButton9.setText("\u5168\u9009");
			rbButton9.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (rbButton9.isSelected()) {
						for (int i = 0; i < jListSecond.getModel().getSize(); i++) {
							Contract contract = (Contract) jListSecond
									.getModel().getElementAt(i);
							contract.setSelected(true);
						}
						jListSecond.repaint();
					}
				}
			});
		}
		return rbButton9;
	}

	/**
	 * This method initializes rbButton10
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbButton10() {
		if (rbButton10 == null) {
			rbButton10 = new JRadioButton();
			rbButton10.setBounds(new Rectangle(58, 1, 67, 22));
			rbButton10.setText("\u5168\u5426");
			rbButton10.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (rbButton10.isSelected()) {
						for (int i = 0; i < getJListSecond().getModel()
								.getSize(); i++) {
							Contract contract = (Contract) jListSecond
									.getModel().getElementAt(i);
							contract.setSelected(false);
						}
						jListSecond.repaint();
					}
				}
			});
			rbButton10.setSelected(true);
		}
		return rbButton10;
	}

	/**
	 * This method initializes buttonGroup1
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup1() {
		if (buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
			buttonGroup1.add(getRbButton9());
			buttonGroup1.add(getRbButton10());

		}
		return buttonGroup1;
	}

	/**
	 * This method initializes buttonGroup2
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup2() {
		if (buttonGroup2 == null) {
			buttonGroup2 = new ButtonGroup();
			buttonGroup2.add(rbButton6);
			buttonGroup2.add(rbButton7);
			buttonGroup2.add(rbButton8);
		}
		return buttonGroup2;
	}

	/**
	 * 多线程
	 * 
	 * @author Administrator
	 * 
	 */

	class MyFindThread extends Thread {
		public void run() {
			CommonProgress.showProgressDialog();
			CommonProgress.setMessage("系统正获取数据，请稍后...");
			search();
			CommonProgress.closeProgressDialog();
		}
	}

	/**
	 * This method initializes jSplitPane2
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane2() {
		if (jSplitPane2 == null) {
			jSplitPane2 = new JSplitPane();
			jSplitPane2.setLeftComponent(getJScrollPane2());
			jSplitPane2.setRightComponent(getPnChoiseContract());
			jSplitPane2.setDividerSize(4);
			jSplitPane2.setDividerLocation(600);
		}
		return jSplitPane2;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJPanel6(), BorderLayout.NORTH);
			jPanel1.add(getJPanel11(), BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJPanel61(), BorderLayout.NORTH);
			jPanel2.add(getJPanel111(), BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel11	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel11() {
		if (jPanel11 == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.weightx = 1.0;
			jPanel11 = new JPanel();
			jPanel11.setLayout(new GridBagLayout());
			jPanel11.add(getJScrollPane1(), gridBagConstraints);
		}
		return jPanel11;
	}

	/**
	 * This method initializes jPanel61	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel61() {
		if (jPanel61 == null) {
			lbCancel = new JLabel();
			lbCancel.setText("已核销的合同");
			jPanel61 = new JPanel();
			jPanel61.setLayout(new BorderLayout());
			jPanel61.add(lbCancel, BorderLayout.NORTH);
		}
		return jPanel61;
	}

	/**
	 * This method initializes jPanel111	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel111() {
		if (jPanel111 == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.weighty = 1.0;
			gridBagConstraints1.weightx = 1.0;
			jPanel111 = new JPanel();
			jPanel111.setLayout(new GridBagLayout());
			jPanel111.add(getJScrollPane11(), gridBagConstraints1);
		}
		return jPanel111;
	}

	/**
	 * This method initializes jScrollPane11	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane11() {
		if (jScrollPane11 == null) {
			jScrollPane11 = new JScrollPane();
			jScrollPane11.setViewportView(getTbCancel());
		}
		return jScrollPane11;
	}

	/**
	 * This method initializes tbCancel	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getTbCancel() {
		if (tbCancel == null) {
			tbCancel = new JTable();
			tbCancel.addFocusListener(new   FocusListener(){ 
			    public   void   focusGained(FocusEvent   e)   {
			    	 tbExe.clearSelection(); 
			    } 
			    public   void   focusLost(FocusEvent   e)   { 
			     
			    } 
			}); 

			tbCancel.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbCancel
									.getModel();
							if (tableModel == null) {
								return;
							}
							ListSelectionModel lsm = (ListSelectionModel) e
									.getSource();
							List list = new ArrayList();
							if (!lsm.isSelectionEmpty()) {
								if (tableModel.getCurrentRow() != null) {
									Contract contract = (Contract) tableModel
											.getCurrentRow();
									cbbBeginDateTwo.setDate(contract
											.getBeginDate());
									String parentId = contract.getId();
									// list = contractAction
									// .findContractExgByParentId(
									// new Request(CommonVars
									// .getCurrUser(),true),
									// parentId);
								}
							}
							initTbSecond2(list);
						}
					});
		}
		return tbCancel;
	}

	/**
	 * This method initializes btnFormulas
	 * 
	 * @return javax.swing.JButton
	 */
	/*
	 * private JButton getBtnFormulas() { if (btnFormulas == null) { btnFormulas
	 * = new JButton(); btnFormulas.setBounds(new Rectangle(507, 65, 52, 28));
	 * btnFormulas.setText("公式查询"); btnRefresh.addActionListener(new
	 * java.awt.event.ActionListener() { public void
	 * actionPerformed(java.awt.event.ActionEvent e) { ProductFormulasearch
	 * pf=new ProductFormulasearch(); pf.setVisible(true); } }); } return
	 * btnFormulas; }
	 */
} // @jve:decl-index=0:visual-constraint="12,46"
