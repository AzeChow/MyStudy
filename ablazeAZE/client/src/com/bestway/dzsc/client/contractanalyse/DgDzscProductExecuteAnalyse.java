/*
 * Created on 2005-6-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.contractanalyse;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.TableColorRender;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.SearchType;
import com.bestway.dzsc.contractanalyse.action.DzscAnalyseAction;
import com.bestway.dzsc.contractanalyse.entity.TempDzscContractNo;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.message.action.DzscMessageAction;
import com.bestway.dzsc.message.entity.DzscParameterSet;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.Rectangle;

/**
 * @author
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDzscProductExecuteAnalyse extends JDialogBase {

	private JPanel jContentPane = null;

	private JButton btnRefresh1 = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JCheckBox cbContractExe = null;

	private JCheckBox cbContractCancel = null;

	private JPanel jPanel = null;

	private JButton btnPrint1 = null;

	private JButton btnExit1 = null;

	private JCalendarComboBox cbbBeginDate1 = null;

	private JCalendarComboBox cbbEndDate1 = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel pn1 = null;

	private JPanel pn2 = null;

	private JTable tbFirst = null;

	private JScrollPane jScrollPane2 = null;

	private JScrollPane jScrollPane3 = null;

	private JDzscContractList jListSecond = null;

	private JPanel jPanel3 = null;

	private JPanel jPanel6 = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JButton btnRefresh2 = null;

	private JButton btnPrint2 = null;

	private JButton btnExit2 = null;

	private JPanel jPanel7 = null;

	private JRadioButton rbProductName = null;

	private JRadioButton rbProductNameSpec = null;

	private JRadioButton rbProductNameSpecCode = null;

	private JRadioButton rbCode = null;

	private JRadioButton rbCodeName = null;

	private JRadioButton rbNameSpecCodeUnit = null;

	private JTableListModel tableModelTbFirst1 = null;

	private JTableListModel tableModelTbFirst2 = null;

	private JTableListModel tableModelTbFour2 = null;

	private JTableListModel tableModelTbFour1 = null;

	private DzscAnalyseAction contractAnalyseAction = null;

	private DzscAction contractAction = null;

	private static final int ALL_SELECT_ITEM = 123;

	private JTable tbSecond2 = null;

	private JScrollPane jScrollPane = null;

	private JTable tbSecond1 = null;

	private JScrollPane jScrollPane1 = null;

	private static int SEARCH_TYPE = SearchType.NAME_SPEC_CODE_UNIT;

	private ButtonGroup buttonGroup = null;

	private JLabel jLabel3 = null;

	private JCalendarComboBox cbbBeginDate2 = null;

	private JCalendarComboBox cbbEndDate2 = null;

	private JLabel jLabel4 = null;

	private DzscParameterSet dzscParameterSet = null;

	private DzscMessageAction dzscMessageAction = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgDzscProductExecuteAnalyse() {
		super();
		initialize();
		contractAnalyseAction = (DzscAnalyseAction) CommonVars
				.getApplicationContext().getBean("dzscAnalyseAction");
		contractAction = (DzscAction) CommonVars.getApplicationContext()
				.getBean("dzscAction");

		dzscMessageAction = (DzscMessageAction) CommonVars
				.getApplicationContext().getBean("dzscMessageAction");
		dzscParameterSet = dzscMessageAction.findDzscMessageDirSet(new Request(
				CommonVars.getCurrUser()));
		initUIComponents();
		this.cbbBeginDate1.setDate(CommonVars.getBeginDate());
		this.cbbBeginDate2.setDate(CommonVars.getBeginDate());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("成品执行情况分析");
		this.setContentPane(getJContentPane());
		this.setSize(755, 539);
		this.getButtonGroup();

	}

	public void setVisible(boolean b) {
		if (b) {
			JTableListModel tableModel = (JTableListModel) tbSecond1.getModel();
			List list = new ArrayList();
			if (tableModel != null && tableModel.getCurrentRow() != null) {
				DzscEmsPorHead contract = (DzscEmsPorHead) tableModel
						.getCurrentRow();
				cbbBeginDate2.setDate(contract.getBeginDate());
				String parentId = contract.getId();
				// list = contractAction
				// .findContractExgByParentId(
				// new Request(CommonVars
				// .getCurrUser(),true),
				// parentId);
			}
			initTbSecond2(list);
		}
		super.setVisible(b);
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
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefresh1() {
		if (btnRefresh1 == null) {
			btnRefresh1 = new JButton();
			btnRefresh1.setText("刷新");
			btnRefresh1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					search();
				}
			});
		}
		return btnRefresh1;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerSize(2);
			jSplitPane.setDividerLocation(555);
			jSplitPane.setRightComponent(getJPanel1());
			jSplitPane.setBounds(0, 0, 742, 432);
			jSplitPane.setLeftComponent(getJScrollPane());
		}
		return jSplitPane;
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
			jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0,
					0, 0));
			jPanel1.add(getJPanel6(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
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
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GridBagLayout());
			jPanel2
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.ipadx = 13;
			gridBagConstraints1.ipady = -11;
			gridBagConstraints1.insets = new java.awt.Insets(5, 7, 1, 5);
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.ipadx = 27;
			gridBagConstraints2.ipady = -8;
			gridBagConstraints2.insets = new java.awt.Insets(1, 7, 7, 15);
			jPanel2.add(getCbContractExe(), gridBagConstraints1);
			jPanel2.add(getCbContractCancel(), gridBagConstraints2);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbContractExe() {
		if (cbContractExe == null) {
			cbContractExe = new JCheckBox();
			cbContractExe.setText("正在执行的合同");
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
	 * This method initializes jCheckBox1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbContractCancel() {
		if (cbContractCancel == null) {
			cbContractCancel = new JCheckBox();
			cbContractCancel.setText("核销的合同");
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
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setAlignment(FlowLayout.LEFT);
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(flowLayout1);
			jPanel.setBounds(1, 433, 740, 46);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jLabel3.setText("报关日期范围:");
			jLabel4.setText("至");
			jPanel.add(jLabel3, null);
			jPanel.add(getCbbBeginDate2(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(getCbbEndDate2(), null);
			jPanel.add(getBtnPrint1(), null);
			jPanel.add(getBtnRefresh1(), null);
			jPanel.add(getBtnExit1(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint1() {
		if (btnPrint1 == null) {
			btnPrint1 = new JButton();
			btnPrint1.setText("打印");
			btnPrint1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					printReport();
				}
			});
		}
		return btnPrint1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit1() {
		if (btnExit1 == null) {
			btnExit1 = new JButton();
			btnExit1.setText("关闭");
			btnExit1.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					DgDzscProductExecuteAnalyse.this.dispose();
				}

			});
		}
		return btnExit1;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate1() {
		if (cbbBeginDate1 == null) {
			cbbBeginDate1 = new JCalendarComboBox();
		}
		return cbbBeginDate1;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate1() {
		if (cbbEndDate1 == null) {
			cbbEndDate1 = new JCalendarComboBox();
		}
		return cbbEndDate1;
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
			jTabbedPane.addTab("出口成品统计", null, getPn2(), null);
			jTabbedPane.addTab("出口成品执行进度总表", null, getPn1(), null);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn1() {
		if (pn1 == null) {
			pn1 = new JPanel();
			pn1.setLayout(new BorderLayout());
			pn1.setToolTipText("成品执行情况");
			pn1.add(getJSplitPane(),BorderLayout.CENTER);
			pn1.add(getJPanel(), BorderLayout.NORTH);
		}
		return pn1;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn2() {
		if (pn2 == null) {
			pn2 = new JPanel();
			pn2.setLayout(new BorderLayout());
			pn2.add(getJPanel3(), BorderLayout.NORTH);
			pn2.add(getJSplitPane1(), BorderLayout.CENTER);
		}
		return pn2;
	}

	/**
	 * This method initializes jTable1
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
	 * This method initializes jList1
	 * 
	 * @return javax.swing.JContractList
	 */
	private JDzscContractList getJListSecond() {
		if (jListSecond == null) {
			jListSecond = new JDzscContractList();
			jListSecond.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					Date beginDate = null;
					// Date endDate=null;
					int size = jListSecond.getModel().getSize();
					for (int i = 0; i < size; i++) {
						DzscEmsPorHead contract = (DzscEmsPorHead) jListSecond
								.getModel().getElementAt(i);
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
						cbbBeginDate1.setDate(beginDate);
					}
				}
			});
		}
		return jListSecond;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate2() {
		if (cbbBeginDate2 == null) {
			cbbBeginDate2 = new JCalendarComboBox();
		}
		return cbbBeginDate2;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate2() {
		if (cbbEndDate2 == null) {
			cbbEndDate2 = new JCalendarComboBox();
		}
		return cbbEndDate2;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jPanel3 = new JPanel();
			jPanel3.setLayout(flowLayout);
			jPanel3.setBounds(0, 430, 741, 50);
			jPanel3
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jLabel1.setText("按时间段查询");
			jLabel2.setText("至");
			jPanel3.add(getCbbBeginDate1(), null);
			jPanel3.add(getCbbEndDate1(), null);
			jPanel3.add(jLabel1, null);
			jPanel3.add(jLabel2, null);
			jPanel3.add(getBtnRefresh2(), null);
			jPanel3.add(getBtnPrint2(), null);
			jPanel3.add(getBtnExit2(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			jLabel = new JLabel();
			jPanel6 = new JPanel();
			jLabel.setText("正在执行的合同");
			jPanel6.add(jLabel, null);
		}
		return jPanel6;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefresh2() {
		dzscMessageAction = (DzscMessageAction) CommonVars
				.getApplicationContext().getBean("dzscMessageAction");
		dzscParameterSet = dzscMessageAction.findDzscMessageDirSet(new Request(
				CommonVars.getCurrUser()));
		if (btnRefresh2 == null) {
			btnRefresh2 = new JButton();
			btnRefresh2.setText("刷新");
			btnRefresh2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					search();
				}
			});
		}
		return btnRefresh2;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint2() {
		if (btnPrint2 == null) {
			btnPrint2 = new JButton();
			btnPrint2.setText("打印");
			btnPrint2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					printReport();
				}
			});
		}
		return btnPrint2;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit2() {
		if (btnExit2 == null) {
			btnExit2 = new JButton();
			btnExit2.setText("关闭");
			btnExit2.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					DgDzscProductExecuteAnalyse.this.dispose();
				}

			});
		}
		return btnExit2;
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
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbProductName() {
		if (rbProductName == null) {
			rbProductName = new JRadioButton();
			rbProductName.setBounds(87, 13, 142, 16);
			rbProductName.setText("成品名称");
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
	 * This method initializes jRadioButton1
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
	 * This method initializes jRadioButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbProductNameSpecCode() {
		if (rbProductNameSpecCode == null) {
			rbProductNameSpecCode = new JRadioButton();
			rbProductNameSpecCode.setBounds(87, 48, 161, 16);
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
	 * This method initializes jRadioButton3
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
	 * This method initializes jRadioButton4
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
	 * This method initializes jRadioButton5
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbNameSpecCodeUnit() {
		if (rbNameSpecCodeUnit == null) {
			rbNameSpecCodeUnit = new JRadioButton();
			rbNameSpecCodeUnit.setBounds(297, 48, 188, 16);
			rbNameSpecCodeUnit.setText("成品名称＋规格＋编码＋单位");
			rbNameSpecCodeUnit.setSelected(true);
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
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbSecond2() {
		if (tbSecond2 == null) {
			tbSecond2 = new JTable();
		}
		return tbSecond2;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbSecond2());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbSecond1() {
		if (tbSecond1 == null) {
			tbSecond1 = new JTable();
			tbSecond1.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbSecond1
									.getModel();
							if (tableModel == null) {
								return;
							}
							ListSelectionModel lsm = (ListSelectionModel) e
									.getSource();
							List list = new ArrayList();
							// if (!lsm.isSelectionEmpty()) {
							if (tableModel.getCurrentRow() != null) {
								DzscEmsPorHead contract = (DzscEmsPorHead) tableModel
										.getCurrentRow();
								cbbBeginDate2.setDate(contract.getBeginDate());
								String parentId = contract.getId();
								// list = contractAction
								// .findContractExgByParentId(
								// new Request(CommonVars
								// .getCurrUser(),true),
								// parentId);
							}
							// }
							initTbSecond2(list);
						}
					});
		}
		return tbSecond1;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbSecond1());
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
	}

	/**
	 * 查询
	 */
	private void search() {
		if (this.jTabbedPane.getSelectedIndex() == 0) {
			List contracts = jListSecond.getSelectedContracts();
			if (contracts.size() <= 0) {
				JOptionPane.showMessageDialog(this, "没有选中的合同!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			List list = new ArrayList();
			Date beginDate = cbbBeginDate1.getDate();
			Date endDate = cbbEndDate1.getDate();
//          if (beginDate != null && endDate != null) {
//          if (beginDate.before(endDate)) {
					list = this.contractAnalyseAction.findExpFinishProductStat(
							new Request(CommonVars.getCurrUser()), contracts,
                                     beginDate, endDate, SEARCH_TYPE);
//              }
//           }
			initTbFirst(list);
		} else if (this.jTabbedPane.getSelectedIndex() == 1) {
			JTableListModel tableModel = (JTableListModel) tbSecond1.getModel();
			DzscEmsPorHead contract = (DzscEmsPorHead) tableModel
					.getCurrentRow();
			if (contract == null) {
				JOptionPane.showMessageDialog(this, "没有选中的合同!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			List list = new ArrayList();
			Date beginDate = cbbBeginDate2.getDate();
			Date endDate = cbbEndDate2.getDate();
			if (beginDate != null && endDate != null) {
				if (beginDate.before(endDate)) {
					list = this.contractAnalyseAction.findExpProductExeTotal(
							new Request(CommonVars.getCurrUser()), contract,
							beginDate, endDate);
				}
			}
			initTbSecond2(list);
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

						return list;
					}
				});
		TableColorRender.setTableRowColor(tbFirst, tableModelTbSecond
				.getRowCount() - 1);
		// 显示小数位,默认2位
		Integer decimalLength = 2;
		if (dzscParameterSet != null
				&& dzscParameterSet.getReportDecimalLength() != null)
			decimalLength = dzscParameterSet.getReportDecimalLength();
		tableModelTbSecond.setAllColumnsFractionCount(decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModelTbSecond.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());
//		List<JTableListColumn> cm = tableModelTbSecond.getColumns();
//		cm.get(4).setFractionCount(decimalLength);
//		cm.get(5).setFractionCount(decimalLength);
//		cm.get(6).setFractionCount(decimalLength);
//		cm.get(7).setFractionCount(decimalLength);
//		cm.get(8).setFractionCount(decimalLength);
//		cm.get(9).setFractionCount(decimalLength);
//		cm.get(10).setFractionCount(decimalLength);

	}

	/**
	 * 初始化合同表(面板二,面板三)
	 * 
	 */
	JTableListModel tableModelTbFour3 = null;

	private JSplitPane jSplitPane1 = null;

	private JSplitPane jSplitPane2 = null;

	private JPanel jPanel4 = null;

	private void initTbFour3AndTbSecond1() {
		//
		// 查询没有审核的合同记录
		//
		List list = this.contractAction.findDzscEmsPorHeadExcu(new Request(
				CommonVars.getCurrUser(), true));

		tableModelTbFirst2 = new JTableListModel(tbSecond1, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("手册编号", "emsNo", 100));
						list.add(addColumn("进口合同号", "ieContractNo", 100));
						list.add(addColumn("出口合同号", "imContractNo", 100));
						list.add(addColumn("进口总金额", "imgAmount", 100));
						list.add(addColumn("出口总金额", "exgAmount", 100));
						list.add(addColumn("币制", "curr.name", 100));
						
						return list;
					}
				});
		// 显示小数位,默认2位
		Integer decimalLength = 2;
		if (dzscParameterSet != null
				&& dzscParameterSet.getReportDecimalLength() != null)
			decimalLength = dzscParameterSet.getReportDecimalLength();
		tableModelTbFirst2.setAllColumnsFractionCount(decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModelTbFirst2.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());
//		List<JTableListColumn> cm = tableModelTbFirst2.getColumns();
//		cm.get(4).setFractionCount(decimalLength);
//		cm.get(5).setFractionCount(decimalLength);
		
		tbSecond1
				.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		if (list.size() > 0) {
			tbSecond1.setRowSelectionInterval(0, 0);
		}
	}

	/**
	 * 初始化出品成品执行进度总表
	 * 
	 */
	private void initTbSecond2(List list) {
		tableModelTbFirst1 = new JTableListModel(tbSecond2, list,
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
						list.add(addColumn("币制", "contractExg.contract.curr.name", 100));
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

		// 显示小数位,默认2位
		Integer decimalLength = 2;
		if (dzscParameterSet != null
				&& dzscParameterSet.getReportDecimalLength() != null)
			decimalLength = dzscParameterSet.getReportDecimalLength();
		tableModelTbFirst1.setAllColumnsFractionCount(decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModelTbFirst1.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());
//		List<JTableListColumn> cm = tableModelTbFirst1.getColumns();
//		cm.get(5).setFractionCount(decimalLength);
//		cm.get(6).setFractionCount(decimalLength);
//		cm.get(9).setFractionCount(decimalLength);
//		cm.get(10).setFractionCount(decimalLength);
//		cm.get(11).setFractionCount(decimalLength);
//		cm.get(12).setFractionCount(decimalLength);
//		cm.get(13).setFractionCount(decimalLength);
//		cm.get(14).setFractionCount(decimalLength);
//		cm.get(15).setFractionCount(decimalLength);
//		cm.get(16).setFractionCount(decimalLength);
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
				List<TempDzscContractNo> tempContractNoList = new ArrayList<TempDzscContractNo>();
				for (int i = 0; i < listContract.size(); i++) {
					TempDzscContractNo temp = new TempDzscContractNo();
					DzscEmsPorHead contract = (DzscEmsPorHead) listContract
							.get(i);
					String contractNo = contract.getEmsNo()
							+ "/"+contract.getIeContractNo();
					temp.setContractNo(contractNo);
					tempContractNoList.add(temp);
				}
				CustomReportDataSource contractDs = new CustomReportDataSource(
						tempContractNoList);
				InputStream tempContractNoListSubReportStream = DgDzscMaterielExecuteAnalyse.class
						.getResourceAsStream("report/TempContractNoList.jasper");
				JasperReport tempContractNoSubReport = (JasperReport) JRLoader
						.loadObject(tempContractNoListSubReportStream);

				//
				// 主报表
				//
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("contractSubReport", tempContractNoSubReport);
				parameters.put("contractSubReportDataSource", contractDs);
				parameters.put("company", company);
				parameters.put("searchBeginDate", CommonVars
						.dateToString(cbbBeginDate1.getDate()));
				parameters.put("searchEndDate", CommonVars
						.dateToString(cbbEndDate1.getDate()));
				List list = tableModelTbSecond.getList();
				if (list.size() <= 0) {
					JOptionPane.showMessageDialog(this, "没有数据可打印!", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					return;
				}
				CustomReportDataSource ds = new CustomReportDataSource(list);
				InputStream masterReportStream = DgDzscMaterielExecuteAnalyse.class
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
				parameters.put("company", company);
				parameters.put("searchBeginDate", CommonVars
						.dateToString(cbbBeginDate2.getDate()));
				parameters.put("searchEndDate", CommonVars
						.dateToString(cbbEndDate2.getDate()));
				DzscEmsPorHead contract = (DzscEmsPorHead) tableModelTbFirst2
						.getCurrentRow();
				if (contract != null) {
					parameters.put("beginDate", CommonVars
							.dateToString(contract.getBeginDate()));
					parameters.put("contractNo", contract.getIeContractNo());
					parameters.put("effectiveDate", CommonVars
							.dateToString(contract.getBeginDate()));
					parameters.put("emsNo", contract.getEmsNo());
				}
				List list = tableModelTbFirst1.getList();
				if (list.size() <= 0) {
					JOptionPane.showMessageDialog(this, "没有数据可打印!", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					return;
				}
				CustomReportDataSource ds = new CustomReportDataSource(list);
				InputStream masterReportStream = DgDzscMaterielExecuteAnalyse.class
						.getResourceAsStream("report/ProductExecuteAnalyse2.jasper");
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
	 * This method initializes jSplitPane1	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setBounds(new Rectangle(-4, 1, 562, 431));
			jSplitPane1.setDividerSize(5);
			jSplitPane1.setDividerLocation(500);
			jSplitPane1.setRightComponent(getJPanel4());
			jSplitPane1.setLeftComponent(getJSplitPane2());
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes jSplitPane2	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane2() {
		if (jSplitPane2 == null) {
			jSplitPane2 = new JSplitPane();
			jSplitPane2.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane2.setDividerLocation(90);
			jSplitPane2.setTopComponent(getJPanel7());
			jSplitPane2.setBottomComponent(getJScrollPane2());
			jSplitPane2.setDividerSize(5);
		}
		return jSplitPane2;
	}

	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new BorderLayout());
			jPanel4.add(getJPanel2(), BorderLayout.NORTH);
			jPanel4.add(getJScrollPane3(), BorderLayout.CENTER);
		}
		return jPanel4;
	}

}
