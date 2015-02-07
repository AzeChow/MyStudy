/*
 * Created on 2005-6-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.contractanalyse;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
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
import javax.swing.table.DefaultTableCellRenderer;

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
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.SearchType;
import com.bestway.dzsc.contractanalyse.action.DzscAnalyseAction;
import com.bestway.dzsc.contractanalyse.entity.TempDzscContractImg;
import com.bestway.dzsc.contractanalyse.entity.TempDzscContractNo;
import com.bestway.dzsc.contractanalyse.entity.TempDzscFinishProduct;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.message.action.DzscMessageAction;
import com.bestway.dzsc.message.entity.DzscParameterSet;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.FlowLayout;
import javax.swing.JToolBar;

/**
 * @author
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDzscMaterielExecuteAnalyse extends JDialogBase {

	private JPanel jContentPane = null;

	private JButton btnRefresh1 = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JDzscContractList jList11 = null;

	private JCheckBox cbContractExe = null;

	private JTable tbFirst1 = null;

	private JScrollPane jScrollPane = null;

	private JCheckBox cbContractCancel = null;

	private JScrollPane jScrollPane1 = null;

	private JButton btnPrint1 = null;

	private JButton btnExit1 = null;

	private JCalendarComboBox cbbBeginDate2 = null;

	private JCalendarComboBox cbbEndDate2 = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel pn1 = null;

	private JPanel pn2 = null;

	private JTable tbSecond = null;

	private JScrollPane jScrollPane2 = null;

	private JScrollPane jScrollPane3 = null;

	private JDzscContractList jListSecond = null;

	private JPanel jPanel3 = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JButton btnRefresh2 = null;

	private JButton btnPrint2 = null;

	private JButton btnExit2 = null;

	private JPanel jPanel7 = null;

	private JRadioButton rbMaterielName = null;

	private JRadioButton rbMaterielNameSpec = null;

	private JRadioButton rbMaterielNameSpecCode = null;

	private JRadioButton rbCode = null;

	private JRadioButton rbCodeName = null;

	private JRadioButton rbNameSpecCodeUnit = null;

	private JSplitPane jSplitPane2 = null;

	private JTable tbFirst2 = null;

	private JScrollPane jScrollPane9 = null;

	private ButtonGroup buttonGroup = null;

	private JTableListModel tableModelTbFirst1 = null;

	private JTableListModel tableModelTbFirst2 = null;

	private JTableListModel tableModelTbSecond = null;

	private DzscAnalyseAction contractAnalyseAction = null;

	private DzscAction contractAction = null;

	private static int SEARCH_TYPE = SearchType.NAME_SPEC_CODE_UNIT;

	private DzscParameterSet dzscParameterSet = null;

	private DzscMessageAction dzscMessageAction = null;

	private JSplitPane jSplitPane1 = null;

	private JSplitPane jSplitPane3 = null;

	private JPanel jPanel4 = null;

	private JToolBar jToolBar = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgDzscMaterielExecuteAnalyse() {
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
		this.cbbBeginDate2.setDate(CommonVars.getBeginDate());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("料件执行情况分析");
		this.setContentPane(getJContentPane());
		this.setSize(823, 539);
		this.getButtonGroup();

	}

	public void setVisible(boolean b) {
		if (b) {

		}
		super.setVisible(b);
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(rbMaterielName);
			buttonGroup.add(rbMaterielNameSpec);
			buttonGroup.add(rbMaterielNameSpecCode);
			buttonGroup.add(rbCode);
			buttonGroup.add(rbCodeName);
			buttonGroup.add(rbNameSpecCodeUnit);
		}
		return buttonGroup;
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
			jSplitPane.setLeftComponent(getJSplitPane2());
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
	 * This method initializes jList
	 * 
	 * @return javax.swing.JContractList
	 */
	private JDzscContractList getJList11() {
		if (jList11 == null) {
			jList11 = new JDzscContractList();
		}
		return jList11;
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
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbFirst1() {
		if (tbFirst1 == null) {
			tbFirst1 = new JTable();
		}
		return tbFirst1;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbFirst1());
		}
		return jScrollPane;
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
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJList11());
		}
		return jScrollPane1;
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
					DgDzscMaterielExecuteAnalyse.this.dispose();
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
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
			jTabbedPane.addTab("料件执行情况", null, getPn1(), null);
			jTabbedPane.addTab("进口料件统计", null, getPn2(), null);
			// jTabbedPane.addTab("料件余料结转情况", null, getPn3(), null);
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
			pn1.setToolTipText("料件执行情况");
			pn1.add(getJSplitPane(),java.awt.BorderLayout.CENTER);
			pn1.add(getJToolBar(), java.awt.BorderLayout.NORTH);
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
	private JTable getTbSecond() {
		if (tbSecond == null) {
			tbSecond = new JTable();
		}
		return tbSecond;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getTbSecond());
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
						cbbBeginDate2.setDate(beginDate);
					}
				}
			});
		}
		return jListSecond;
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
			jPanel3
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jLabel1.setText("按时间段查询");
			jLabel2.setText("至");
			jPanel3.add(jLabel1, null);
			jPanel3.add(getCbbBeginDate2(), null);
			jPanel3.add(jLabel2, null);
			jPanel3.add(getCbbEndDate2(), null);
			jPanel3.add(getBtnRefresh2(), null);
			jPanel3.add(getBtnPrint2(), null);
			jPanel3.add(getBtnExit2(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefresh2() {
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
					DgDzscMaterielExecuteAnalyse.this.dispose();
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
									"料件查询条件",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									null, null));
			jPanel7.add(getRbMaterielName(), null);
			jPanel7.add(getRbMaterielNameSpec(), null);
			jPanel7.add(getRbMaterielNameSpecCode(), null);
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
	private JRadioButton getRbMaterielName() {
		if (rbMaterielName == null) {
			rbMaterielName = new JRadioButton();
			rbMaterielName.setBounds(87, 13, 142, 16);
			rbMaterielName.setText("料件名称");
			rbMaterielName
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							SEARCH_TYPE = SearchType.NAME;
						}
					});
		}
		return rbMaterielName;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbMaterielNameSpec() {
		if (rbMaterielNameSpec == null) {
			rbMaterielNameSpec = new JRadioButton();
			rbMaterielNameSpec.setBounds(87, 31, 142, 16);
			rbMaterielNameSpec.setText("料件名称＋规格");
			rbMaterielNameSpec
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							SEARCH_TYPE = SearchType.NAME_SPEC;
						}
					});
		}
		return rbMaterielNameSpec;
	}

	/**
	 * This method initializes jRadioButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbMaterielNameSpecCode() {
		if (rbMaterielNameSpecCode == null) {
			rbMaterielNameSpecCode = new JRadioButton();
			rbMaterielNameSpecCode.setBounds(87, 48, 161, 16);
			rbMaterielNameSpecCode.setText("料件名称＋规格＋编码");
			rbMaterielNameSpecCode
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							SEARCH_TYPE = SearchType.NAME_SPEC_CODE;
						}
					});
		}
		return rbMaterielNameSpecCode;
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
			rbCodeName.setText("商品编码＋料件名称");
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
			rbNameSpecCodeUnit.setSelected(true);
			rbNameSpecCodeUnit
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							SEARCH_TYPE = SearchType.NAME_SPEC_CODE_UNIT;
						}
					});
			rbNameSpecCodeUnit.setText("料件名称＋规格＋编码＋单位");
		}
		return rbNameSpecCodeUnit;
	}

	/**
	 * This method initializes jSplitPane2
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane2() {
		if (jSplitPane2 == null) {
			jSplitPane2 = new JSplitPane();
			jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane2.setDividerLocation(175);
			jSplitPane2.setDividerSize(2);
			jSplitPane2.setTopComponent(getJScrollPane());
			jSplitPane2.setBottomComponent(getJScrollPane9());
		}
		return jSplitPane2;
	}

	/**
	 * This method initializes jTable5
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbFirst2() {
		if (tbFirst2 == null) {
			tbFirst2 = new JTable();
		}
		return tbFirst2;
	}

	/**
	 * This method initializes jScrollPane9
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane9() {
		if (jScrollPane9 == null) {
			jScrollPane9 = new JScrollPane();
			jScrollPane9.setViewportView(getTbFirst2());
		}
		return jScrollPane9;
	}

	/**
	 * 初始化数据
	 * 
	 */
	private void initUIComponents() {
		//
		// 初始化面板一
		//
		List list = contractAnalyseAction.findTempContractImg(new Request(
				CommonVars.getCurrUser()));
		initTbFirst1(list);
		initTbFrist2(new ArrayList());
		//
		// 初始化面板二
		//
		initTbSecond(new ArrayList());
	}

	/**
	 * 查询
	 */
	private void search() {
		if (this.jTabbedPane.getSelectedIndex() == 0) {
			List contracts = jList11.getSelectedContracts();
			if (contracts.size() <= 0) {
				JOptionPane.showMessageDialog(this, "没有选中的合同!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				return;
			}
			TempDzscContractImg tempContractImg = (TempDzscContractImg) tableModelTbFirst1
					.getCurrentRow();
			if (tempContractImg == null) {
				JOptionPane.showMessageDialog(this, "没有选中料件记录!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				return;
			}
			List list = this.contractAnalyseAction.findImpMaterielExeState(
					new Request(CommonVars.getCurrUser()), contracts,
					tempContractImg);
			initTbFrist2(list);
		} else if (this.jTabbedPane.getSelectedIndex() == 1) {
			List contracts = jListSecond.getSelectedContracts();
			if (contracts.size() <= 0) {
				JOptionPane.showMessageDialog(this, "没有选中的合同!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				return;
			}
			List list = new ArrayList();
			Date beginDate = cbbBeginDate2.getDate();
			Date endDate = cbbEndDate2.getDate();
			// if (beginDate != null && endDate != null) {
			// if (beginDate.before(endDate)) {
			list = this.contractAnalyseAction.findImpMaterielExeStat(
					new Request(CommonVars.getCurrUser()), contracts,
					beginDate, endDate, SEARCH_TYPE);
			// }
			//			}
			initTbSecond(list);
		}
	}

	/**
	 * 初始化料件表
	 * 
	 */
	private void initTbFirst1(List list) {
		tableModelTbFirst1 = new JTableListModel(tbFirst1, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("商品编码", "complexCode", 100));
						list.add(addColumn("商品名称", "name", 100));
						list.add(addColumn("规格型号", "spec", 100));
						list.add(addColumn("计量单位", "unit.name", 100));
						list.add(addColumn("单位重量", "unitWeight", 100));
						return list;
					}
				});

		// 显示小数位,默认2位
		Integer decimalLength = 2;
		if (dzscParameterSet != null
				&& dzscParameterSet.getReportDecimalLength() != null)
			decimalLength = dzscParameterSet.getReportDecimalLength();

		List<JTableListColumn> cm = tableModelTbFirst1.getColumns();
		cm.get(5).setFractionCount(decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModelTbFirst1.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());
	}

	private void initTbSecond(List list) {
		tableModelTbSecond = new JTableListModel(tbSecond, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("商品编码", "complexCode", 100));
						list.add(addColumn("品名规格", "nameSpec", 100));
						list.add(addColumn("单位", "unit.name", 100));
						list
								.add(addColumn("合同总定量", "contractTotalAmount",
										100));
						list.add(addColumn("总进口量", "totalImpAmount", 100));
						list.add(addColumn("大单进口量", "orderImpAmount", 100));
						list.add(addColumn("料件进口总量", "materielImpAmount", 100));
						list.add(addColumn("转厂进口总量", "transferImpAmount", 100));
						list.add(addColumn("退料出口总量", "backMaterielExpAmount",
								100));
						list.add(addColumn("退料复出总量", "reworkExpAmount", 100));
						list.add(addColumn("退料退换总量", "backMaterielExchange",
								100));
						list.add(addColumn("出口成品使用总量",
								"expFinishProductAmount", 100));
						list.add(addColumn("余料情况", "remainStat", 100));
						list.add(addColumn("缺料情况", "lackStat", 100));
						list.add(addColumn("可进口总量", "canImpAmount", 100));
						list.add(addColumn("比例", "proportion", 100));
						list.add(addColumn("状态", "state", 100));
						list.add(addColumn("余料结转转出", "remainForwordExpAmount",
								100));
						list.add(addColumn("余料结转转入", "remainForwordImpAmount",
								100));
						return list;
					}
				});
		TableColorRender.setTableRowColor(tbSecond, tableModelTbSecond
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
		// cm.get(13).setFractionCount(decimalLength);
		// cm.get(14).setFractionCount(decimalLength);
		// cm.get(15).setFractionCount(decimalLength);
		// cm.get(16).setFractionCount(decimalLength);
		// cm.get(18).setFractionCount(decimalLength);
		// cm.get(19).setFractionCount(decimalLength);

	}

	/**
	 * @param list
	 */
	private void initTbFrist2(List list) {
		tableModelTbFirst2 = new JTableListModel(tbFirst2, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("报关日期", "applyToCustomDate", 100));
						list.add(addColumn("报关单号", "applyToCustomBillNo", 100));
						list.add(addColumn("手册编号", "emsNo", 100));
						list.add(addColumn("进出口类型", "impExpType", 100));
						list.add(addColumn("说明", "explain", 100));
						list.add(addColumn("入(进口数量)", "impAmount", 100));
						list.add(addColumn("出(出口数量)", "expAmount", 100));
						return list;
					}
				});
		tbFirst2.getColumnModel().getColumn(4).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
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
							case ImpExpType.EQUIPMENT_IMPORT:
								str = "设备进口";
								break;
							case ImpExpType.BACK_PORT_REPAIR:
								str = "退港维修";
								break;
							case ImpExpType.EQUIPMENT_BACK_PORT:
								str = "设备退港";
								break;

							case ImpExpType.REMAIN_FORWARD_EXPORT:
								str = "余料结转";
								break;

							case ImpExpType.EXPORT_STORAGE:
								str = "出口仓储";
								break;

							case ImpExpType.IMPORT_STORAGE:
								str = "进口仓储";
								break;
							case ImpExpType.MATERIAL_DOMESTIC_SALES:
								str = "料件内销";
								break;
							}
						}
						this.setText(str);
						return this;
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
//		cm.get(6).setFractionCount(decimalLength);
//		cm.get(7).setFractionCount(decimalLength);
	}

	/**
	 * 选择合同项目
	 */
	private void selectContractItem() {
		jListSecond.showContractData(this.cbContractExe.isSelected(),
				this.cbContractCancel.isSelected());
	}

	/**
	 * 打印
	 */
	private void printReport() {
		String company = CommonVars.getCurrUser().getCompany().getName();
		// this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		int flag = this.jTabbedPane.getSelectedIndex();
		switch (flag) {
		case 0: // 
			try {
				//
				// 合同报表
				//
				List listContract = this.jList11.getSelectedContracts();
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
							+ contract.getIeContractNo();
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
				// 成品单耗报表 ？？ 要修改数据
				//
				TempDzscContractImg tempContractImg = (TempDzscContractImg) tableModelTbFirst1
						.getCurrentRow();
				List<TempDzscContractImg> TempContractImgList = new ArrayList<TempDzscContractImg>();
				TempContractImgList.add(tempContractImg);
				List<TempDzscFinishProduct> subList = this.contractAnalyseAction
						.findContractBomByContractImg(new Request(CommonVars
								.getCurrUser()), TempContractImgList,
								listContract);

				InputStream subReportStream = DgDzscMaterielExecuteAnalyse.class
						.getResourceAsStream("report/MaterielExecuteAnalyse1SubReport.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				CustomReportDataSource subDs = new CustomReportDataSource(
						subList);
				//
				// 主报表
				//

				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("contractSubReport", tempContractNoSubReport);
				parameters.put("contractSubReportDataSource", contractDs);
				parameters.put("subReport", subReport);
				parameters.put("subReportDataSource", subDs);
				parameters.put("company", company);

				if (tempContractImg != null) {
					parameters.put("complexCode", tempContractImg
							.getComplexCode());
					parameters.put("name", tempContractImg.getName());
					parameters.put("spce", tempContractImg.getSpec());
				}
				List list = tableModelTbFirst2.getList();
				if (list.size() <= 0) {
					JOptionPane.showMessageDialog(this, "没有数据可打印!", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					return;
				}
				CustomReportDataSource ds = new CustomReportDataSource(list);

				InputStream masterReportStream = DgDzscMaterielExecuteAnalyse.class
						.getResourceAsStream("report/MaterielExecuteAnalyse1.jasper");

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
				// 合同报表
				//
				List listContract = this.jListSecond.getSelectedContracts();
				List<TempDzscContractNo> tempContractNoList = new ArrayList<TempDzscContractNo>();
				if (listContract.isEmpty()) {
					JOptionPane.showMessageDialog(this, "请先选中合同记录!!!", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					return;
				}
				for (int i = 0; i < listContract.size(); i++) {
					TempDzscContractNo temp = new TempDzscContractNo();
					DzscEmsPorHead contract = (DzscEmsPorHead) listContract
							.get(i);
					String contractNo = contract.getEmsNo() + "/"
							+ contract.getIeContractNo();
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
						.dateToString(cbbBeginDate2.getDate()));
				parameters.put("searchEndDate", CommonVars
						.dateToString(cbbEndDate2.getDate()));
				List list = tableModelTbSecond.getList();
				if (list.size() <= 0) {
					JOptionPane.showMessageDialog(this, "没有数据可打印!", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					return;
				}
				CustomReportDataSource ds = new CustomReportDataSource(list);
				InputStream masterReportStream = DgDzscMaterielExecuteAnalyse.class
						.getResourceAsStream("report/MaterielExecuteAnalyse2.jasper");
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
			jSplitPane1.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
			jSplitPane1.setDividerLocation(550);
			jSplitPane1.setLeftComponent(getJSplitPane3());
			jSplitPane1.setRightComponent(getJPanel4());
			jSplitPane1.setDividerSize(5);
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes jSplitPane3	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane3() {
		if (jSplitPane3 == null) {
			jSplitPane3 = new JSplitPane();
			jSplitPane3.setDividerLocation(100);
			jSplitPane3.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane3.setTopComponent(getJPanel7());
			jSplitPane3.setBottomComponent(getJScrollPane2());
			jSplitPane3.setDividerSize(5);
		}
		return jSplitPane3;
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

	/**
	 * This method initializes jToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setBounds(new Rectangle(747, 5, 18, 10));
			jToolBar.add(getBtnPrint1());
			jToolBar.add(getBtnRefresh1());
			jToolBar.add(getBtnExit1());
		}
		return jToolBar;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"