/*
 * Created on 2005-6-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractanalyse;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contractanalyse.action.ContractAnalyseAction;
import com.bestway.bcs.contractanalyse.entity.TempContractImg;
import com.bestway.bcs.contractanalyse.entity.TempContractNo;
import com.bestway.bcs.contractanalyse.entity.TempFinishProduct;
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

/**
 * @author ls
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
/**
 * 查询条件是针对一本或多本合同相同(商编＋商品名称＋商品规格＋单位)的统计 合同总定量=所有合同的进口料件总量 总进口量=料件进口总量 +转厂进口总量 -
 * 退料出口+余料结转转入 大单进口量=料件进口总量 +转厂进口总量 料件进口总量=所有进口报关单进口类型为料件进口之和
 * 转厂进口总量=所有进口报关单进口类型为转厂进口之和 退料出口总量=退料复出 + 退料退换出口 退料复出总量＝所有进口报关单 [退料出口类型]
 * 并且贸易方式为 来料料件复出,进料料件复出 退料退换总量(退换出口量)＝所有出口报关单 [退料出口类型] 并且贸易方式为 来料料件退换,进料料件退换
 * 退换进口量＝所有进口报关单 [直接进口类型] 并且贸易方式为 来料料件退换,进料料件退换
 * 本期内销数量＝进口报关单中进口类型为海关批准内销,贸易方式为0644，0245的数量(没有计算)
 * 出口成品使用总量＝总出口量*单耗/((1-损耗)*0.01) 余料情况＝总进口量-成品使用量 (与进口料件情况统计表中的计算不统一) 库存数量＝余料数量
 * - 余料转出 可进口总量＝合同总定量－总进口量 余料结转转出＝所有出口报关单 [余料结转类型] 并且贸易方式为 来料余料结转,进料余料结转
 * 余料结转转入＝所有进口报关单 [料件进口类型] 并且贸易方式为 来料余料结转,进料余料结转 关封余量＝关封管理明细中的进货本厂数量—转厂进口量(没有计算)
 * 可直接进口量＝可进口数量-关封余量
 * 
 */
public class DgMaterielExecuteAnalyse extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JButton btnRefresh1 = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JContractList jList11 = null;

	private JCheckBox cbContractExe = null;

	private JTable tbFirst1 = null;

	private JScrollPane jScrollPane = null;

	private JCheckBox cbContractCancel = null;

	private JScrollPane jScrollPane1 = null;

	private JPanel jPanel = null;

	private JButton btnPrint1 = null;

	private JButton btnExit1 = null;

	private JCalendarComboBox cbbBeginDate2 = null;

	private JCalendarComboBox cbbEndDate2 = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel pn1 = null;

	private JPanel pn2 = null;

	private JPanel pn4 = null;

	private JTable tbSecond = null;

	private JScrollPane jScrollPane2 = null;

	private JScrollPane jScrollPane3 = null;

	private JContractList jListSecond = null;

	private JPanel jPanel3 = null;

	private JTable tbFour1 = null;

	private JScrollPane jScrollPane6 = null;

	private JPanel jPanel5 = null;

	private JPanel jPanel6 = null;

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

	private JLabel jLabel6 = null;

	private JLabel jLabel7 = null;

	private JCalendarComboBox cbbBeginDate4 = null;

	private JLabel jLabel8 = null;

	private JCalendarComboBox cbbEndDate4 = null;

	private JButton btnPrint4 = null;

	private JButton btnExit4 = null;

	private JComboBox cbbImpExpType = null;

	private JLabel jLabel9 = null;

	private JSplitPane jSplitPane1 = null;

	private JTable tbFour2 = null;

	private JScrollPane jScrollPane8 = null;

	private JSplitPane jSplitPane2 = null;

	private JTable tbFirst2 = null;

	private JScrollPane jScrollPane9 = null;

	private ButtonGroup buttonGroups = null;

	private JTableListModel tableModelTbFirst1 = null;

	private JTableListModel tableModelTbFirst2 = null;

	private JTableListModel tableModelTbFour3 = null; // @jve:decl-index=0:visual-constraint="778,213"

	private JTableListModel tableModelTbFour2 = null;

	private JTableListModel tableModelTbFour1 = null;

	private JTableListModel tableModelTbSecond = null;

	private ContractAnalyseAction contractAnalyseAction = null;

	private ContractAction contractAction = null;

	private JTable tbFour3 = null;

	private JScrollPane jScrollPane4 = null;

	private static final int ALL_SELECT_ITEM = 123;

	private static int SEARCH_TYPE = SearchType.NAME_SPEC_CODE_UNIT;

	private JPanel jPanel4 = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	private JRadioButton jRadioButton2 = null;

	private JPanel jPanel8 = null;

	private JRadioButton jRadioButton3 = null;

	private JRadioButton jRadioButton4 = null;

	private JRadioButton jRadioButton5 = null;

	private JPanel jPanel9 = null;

	private JRadioButton jRadioButton6 = null;

	private JRadioButton jRadioButton7 = null;

	private JRadioButton jRadioButton8 = null;

	private ButtonGroup buttonGroup = null;

	private JPanel jPanel10 = null;

	private JRadioButton jRadioButton9 = null;

	private JRadioButton jRadioButton10 = null;

	private JPanel jPanel11 = null;

	private JRadioButton jRadioButton11 = null;

	private JRadioButton jRadioButton12 = null;

	private JRadioButton jRadioButton13 = null;

	private JRadioButton jRadioButton14 = null;

	private ButtonGroup buttonGroup1 = null;

	private ButtonGroup buttonGroup2 = null;

	private ButtonGroup buttonGroup3 = null;

	private ButtonGroup buttonGroup4 = null;

	private BcsParameterSet parameterSet = null;

	private JPanel jPanel12 = null;

	private JSplitPane jSplitPane3 = null;

	private JButton btnFormulasearch = null;

	private JCheckBox cbContractExe1 = null;

	private JCheckBox cbContractCancel1 = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgMaterielExecuteAnalyse() {
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
		getButtonGroup2();
		getButtonGroup3();
		this.cbbBeginDate2.setDate(CommonVars.getBeginDate());
		// this.cbbBeginDate4.setDate(CommonVars.getBeginDate());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("料件执行情况分析");
		this.setContentPane(getJContentPane());
		this.setSize(755, 539);
		getButtonGroup();
		getButtonGroups();
		getButtonGroup4();

	}

	public void setVisible(boolean b) {
		if (b) {
			// JTableListModel tableModel = (JTableListModel)
			// tbFour3.getModel();
			// List list = new ArrayList();
			// if (tableModel != null && tableModel.getCurrentRow() != null) {
			// Contract contract = (Contract) tableModel.getCurrentRow();
			// if (contract != null) {
			// cbbBeginDate4.setDate(contract.getBeginDate());
			// String parentId = contract.getId();
			// list = contractAction.findContractImgByParentId(
			// new Request(CommonVars.getCurrUser(), true),
			// parentId);
			// }
			// initTbFour1(list);
			// } else {
			// initTbFour1(new Vector());
			// }
			getJSplitPane().setDividerLocation(0.8);
			getJSplitPane1().setDividerLocation(0.8);
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

	private ButtonGroup getButtonGroups() {
		if (buttonGroups == null) {
			buttonGroups = new ButtonGroup();
			buttonGroups.add(this.jRadioButton);
			buttonGroups.add(this.jRadioButton1);
			buttonGroups.add(this.jRadioButton2);

			// buttonGroups.add(this.jRadioButton6);
			// buttonGroups.add(this.jRadioButton7);
			// buttonGroups.add(this.jRadioButton8);

		}
		return buttonGroups;
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
			btnRefresh1.setText("查询");
			btnRefresh1.setBounds(392, 4, 73, 28);
			btnRefresh1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new MyFindThread().start();
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
			jPanel1.add(getJScrollPane1(), BorderLayout.CENTER);
			jPanel1.add(getJPanel6(), BorderLayout.NORTH);
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
			jPanel2.setBorder(javax.swing.BorderFactory
					.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel2.setBounds(new Rectangle(-20, 27, 185, 51));
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
	private JContractList getJList11() {
		if (jList11 == null) {
			jList11 = new JContractList();
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
			cbContractExe.setSelected(true);
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
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBorder(javax.swing.BorderFactory
					.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel.setPreferredSize(new Dimension(755, 40));
			jPanel.add(getBtnExit1(), null);
			jPanel.add(getBtnRefresh1(), null);
			jPanel.add(getBtnPrint1(), null);
			jPanel.add(getJPanel4(), null);
			// jPanel.add(getBtnFormulasearch(), null);
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
			btnPrint1.setBounds(497, 5, 72, 28);
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
			btnExit1.setBounds(602, 6, 68, 28);
			btnExit1.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					DgMaterielExecuteAnalyse.this.dispose();
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
			cbbBeginDate2.setBounds(99, 75, 87, 23);
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
			cbbEndDate2.setBounds(218, 75, 87, 23);
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
			// jTabbedPane.addTab("料件执行明细", null, getPn4(), null);
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
			pn1.add(getJSplitPane(), BorderLayout.CENTER);
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
			pn2.add(getJSplitPane3(), BorderLayout.CENTER);
		}
		return pn2;
	}

	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn4() {
		if (pn4 == null) {
			jLabel6 = new JLabel();
			pn4 = new JPanel();
			pn4.setLayout(null);
			jLabel6.setBounds(610, 5, 94, 21);
			jLabel6.setText("正在执行的合同");
			pn4.add(getJPanel5(), null);
			pn4.add(jLabel6, null);
			pn4.add(getJSplitPane1(), null);
			pn4.add(getJScrollPane4(), null);
		}
		return pn4;
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
								if (beginDate.compareTo(contract.getBeginDate()) > 0) {
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
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3.setBorder(javax.swing.BorderFactory
					.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel3.setPreferredSize(new Dimension(725, 106));
			jLabel1.setBounds(14, 75, 83, 23);
			jLabel1.setText("按时间段查询");
			jLabel2.setText("至");
			jPanel3.add(getCbbBeginDate2(), null);
			jPanel3.add(getCbbEndDate2(), null);
			jPanel3.add(jLabel1, null);
			jPanel3.add(jLabel2, null);
			jPanel3.add(getBtnRefresh2(), null);
			jPanel3.add(getBtnPrint2(), null);
			jPanel3.add(getBtnExit2(), null);
			jPanel3.add(getJPanel8(), null);
			jPanel3.add(getJPanel7(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jTable3
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbFour1() {
		if (tbFour1 == null) {
			tbFour1 = new JTable();
			tbFour1.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {

						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbFour1
									.getModel();
							if (tableModel == null) {
								return;
							}
							ListSelectionModel lsm = (ListSelectionModel) e
									.getSource();
							List list = new ArrayList();
							int state = -1;
							if (jRadioButton.isSelected()) {
								state = CustomsDeclarationState.EFFECTIVED;
							} else if (jRadioButton1.isSelected()) {
								state = CustomsDeclarationState.NOT_EFFECTIVED;
							} else if (jRadioButton2.isSelected()) {
								state = state = CustomsDeclarationState.ALL;
							}
							int impExpType = -1;
							if (cbbImpExpType.getSelectedItem() != null) {
								impExpType = Integer
										.valueOf(((ItemProperty) cbbImpExpType
												.getSelectedItem()).getCode());
							}
							if (!lsm.isSelectionEmpty()) {
								ContractImg contractImg = (ContractImg) tableModel
										.getCurrentRow();
								Date beginDate = cbbBeginDate4.getDate();
								Date endDate = cbbEndDate4.getDate();
								if (contractImg != null
										&& cbbImpExpType.getSelectedItem() != null
										&& beginDate != null && endDate != null) {
									if (beginDate.before(endDate)) {
										Integer seqNum = contractImg
												.getSeqNum();
										if (impExpType == ALL_SELECT_ITEM) {
											list = contractAnalyseAction.findImpMaterielExeDetail(
													new Request(CommonVars
															.getCurrUser()),
													contractImg, -1, beginDate,
													endDate, state);
										} else {
											list = contractAnalyseAction.findImpMaterielExeDetail(
													new Request(CommonVars
															.getCurrUser()),
													contractImg, impExpType,
													beginDate, endDate, state);
										}
									}

								}

							}
							initTbFour2(list, impExpType);
						}
					});
		}
		return tbFour1;
	}

	/**
	 * This method initializes jScrollPane6
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane6() {
		if (jScrollPane6 == null) {
			jScrollPane6 = new JScrollPane();
			jScrollPane6.setViewportView(getTbFour1());
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
			jLabel9 = new JLabel();
			jLabel8 = new JLabel();
			jLabel7 = new JLabel();
			jPanel5 = new JPanel();
			jPanel5.setLayout(null);
			jPanel5.setBounds(2, 429, 741, 50);
			jPanel5.setBorder(javax.swing.BorderFactory
					.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jLabel7.setText("报关日期范围");
			jLabel7.setBounds(7, 14, 81, 24);
			jLabel8.setBounds(167, 13, 17, 24);
			jLabel8.setText("至");
			jLabel9.setBounds(273, 13, 63, 22);
			jLabel9.setText("进出口类型");
			jPanel5.add(jLabel7, null);
			jPanel5.add(getCbbBeginDate4(), null);
			jPanel5.add(jLabel8, null);
			jPanel5.add(getCbbEndDate4(), null);
			jPanel5.add(getBtnPrint4(), null);
			jPanel5.add(getCbbImpExpType(), null);
			jPanel5.add(jLabel9, null);
			jPanel5.add(getJPanel9(), null);
			jPanel5.add(getBtnExit4(), null);
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
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(3);
			jPanel6 = new JPanel();
			jPanel6.setLayout(gridLayout);
			jPanel6.setPreferredSize(new Dimension(144, 63));
			jPanel6.add(getJPanel10(), null);
			jPanel6.add(getCbContractExe1(), null);
			jPanel6.add(getCbContractCancel1(), null);
		}
		return jPanel6;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefresh2() {
		if (btnRefresh2 == null) {
			btnRefresh2 = new JButton();
			btnRefresh2.setBounds(514, 72, 67, 28);
			btnRefresh2.setText("查询");
			btnRefresh2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new MyFindThread().start();
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
			btnPrint2.setBounds(582, 72, 66, 28);
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
			btnExit2.setBounds(651, 72, 67, 28);
			btnExit2.setText("关闭");
			btnExit2.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					DgMaterielExecuteAnalyse.this.dispose();
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
			jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(
					javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
					"料件查询条件",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jPanel7.setBounds(new Rectangle(12, 3, 724, 61));
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
			rbMaterielNameSpecCode.setBounds(300, 36, 161, 16);
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
			rbCodeName.setBounds(451, 10, 142, 16);
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
			rbNameSpecCodeUnit.setBounds(457, 37, 197, 16);
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
	 * This method initializes jCalendarComboBox2
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate4() {
		if (cbbBeginDate4 == null) {
			cbbBeginDate4 = new JCalendarComboBox();
			cbbBeginDate4.setBounds(79, 14, 87, 24);
		}
		return cbbBeginDate4;
	}

	/**
	 * This method initializes jCalendarComboBox3
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate4() {
		if (cbbEndDate4 == null) {
			cbbEndDate4 = new JCalendarComboBox();
			cbbEndDate4.setBounds(184, 13, 87, 24);
		}
		return cbbEndDate4;
	}

	/**
	 * This method initializes jButton6
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint4() {
		if (btnPrint4 == null) {
			btnPrint4 = new JButton();
			btnPrint4.setBounds(683, 12, 58, 24);
			btnPrint4.setText("打印");
			btnPrint4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					printReport();
				}
			});
		}
		return btnPrint4;
	}

	/**
	 * This method initializes jButton7
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit4() {
		if (btnExit4 == null) {
			btnExit4 = new JButton();
			btnExit4.setText("刷新");
			btnExit4.setBounds(new java.awt.Rectangle(616, 12, 69, 24));
			btnExit4.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					JTableListModel tableModel = (JTableListModel) tbFour1
							.getModel();
					if (tableModel == null) {
						return;
					}
					// ListSelectionModel lsm = (ListSelectionModel) e
					// .getSource();
					List list = new ArrayList();
					int state = -1;
					if (jRadioButton6.isSelected()) {
						state = CustomsDeclarationState.EFFECTIVED;
					} else if (jRadioButton7.isSelected()) {
						state = CustomsDeclarationState.NOT_EFFECTIVED;
					} else if (jRadioButton8.isSelected()) {
						state = state = CustomsDeclarationState.ALL;
					}
					int impExpType = -1;
					if (cbbImpExpType.getSelectedItem() != null) {
						impExpType = Integer
								.valueOf(((ItemProperty) cbbImpExpType
										.getSelectedItem()).getCode());
					}
					// if (!lsm.isSelectionEmpty()) {
					ContractImg contractImg = (ContractImg) tableModel
							.getCurrentRow();
					Date beginDate = cbbBeginDate4.getDate();
					Date endDate = cbbEndDate4.getDate();
					if (contractImg != null
							&& cbbImpExpType.getSelectedItem() != null
							&& beginDate != null && endDate != null) {
						if (beginDate.before(endDate)) {
							Integer seqNum = contractImg.getSeqNum();
							if (impExpType == ALL_SELECT_ITEM) {
								list = contractAnalyseAction
										.findImpMaterielExeDetail(new Request(
												CommonVars.getCurrUser()),
												contractImg, -1, beginDate,
												endDate, state);
							} else {
								list = contractAnalyseAction
										.findImpMaterielExeDetail(new Request(
												CommonVars.getCurrUser()),
												contractImg, impExpType,
												beginDate, endDate, state);
							}
						}

					}

					// }
					initTbFour2(list, impExpType);

				}

			});
		}
		return btnExit4;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpExpType() {
		if (cbbImpExpType == null) {
			cbbImpExpType = new JComboBox();
			cbbImpExpType.setBounds(336, 12, 87, 24);
			cbbImpExpType.addItemListener(new ItemListener() {

				public void itemStateChanged(ItemEvent e) {
					int state = -1;
					if (jRadioButton.isSelected()) {
						state = CustomsDeclarationState.EFFECTIVED;
					} else if (jRadioButton1.isSelected()) {
						state = CustomsDeclarationState.NOT_EFFECTIVED;
					} else if (jRadioButton2.isSelected()) {
						state = state = CustomsDeclarationState.ALL;
					}

					if (e.getStateChange() == ItemEvent.SELECTED) {
						cbbImpExpType.setCursor(new Cursor(Cursor.WAIT_CURSOR));
						JTableListModel tableModel = (JTableListModel) tbFour1
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
						ContractImg contractImg = (ContractImg) tableModel
								.getCurrentRow();
						Date beginDate = cbbBeginDate4.getDate();
						Date endDate = cbbEndDate4.getDate();

						if (contractImg != null
								&& cbbImpExpType.getSelectedItem() != null
								&& beginDate != null && endDate != null) {
							if (beginDate.before(endDate)) {
								Integer seqNum = contractImg.getSeqNum();
								if (impExpType == ALL_SELECT_ITEM) {
									list = contractAnalyseAction
											.findImpMaterielExeDetail(
													new Request(CommonVars
															.getCurrUser()),
													contractImg, -1, beginDate,
													endDate, state);
								} else {
									list = contractAnalyseAction
											.findImpMaterielExeDetail(
													new Request(CommonVars
															.getCurrUser()),
													contractImg, impExpType,
													beginDate, endDate, state);
								}
							}

						}
						initTbFour2(list, impExpType);
					}
					// cbbImpExpType.setSelectedIndex(0);
					cbbImpExpType.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}

			});
		}
		return cbbImpExpType;
	}

	/**
	 * This method initializes jSplitPane1
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setBounds(0, 0, 555, 429);
			jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane1.setTopComponent(getJScrollPane6());
			jSplitPane1.setDividerLocation(175);
			jSplitPane1.setDividerSize(2);
			jSplitPane1.setBottomComponent(getJScrollPane8());
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes jTable4
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbFour2() {
		if (tbFour2 == null) {
			tbFour2 = new JTable();
		}
		return tbFour2;
	}

	/**
	 * This method initializes jScrollPane8
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane8() {
		if (jScrollPane8 == null) {
			jScrollPane8 = new JScrollPane();
			jScrollPane8.setViewportView(getTbFour2());
		}
		return jScrollPane8;
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
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbFour3() {
		if (tbFour3 == null) {
			tbFour3 = new JTable();
			tbFour3.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbFour3
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
									cbbBeginDate4.setDate(contract
											.getBeginDate());
									String parentId = contract.getId();
									list = contractAction
											.findContractImgByParentId(
													new Request(CommonVars
															.getCurrUser(),
															true), parentId);
								}
							}
							initTbFour1(list);
						}
					});
		}
		return tbFour3;
	}

	/**
	 * This method initializes jScrollPane4
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane4() {
		if (jScrollPane4 == null) {
			jScrollPane4 = new JScrollPane();
			jScrollPane4.setBounds(556, 32, 186, 396);
			jScrollPane4.setViewportView(getTbFour3());
		}
		return jScrollPane4;
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
		//
		// 初始化面板三
		//
		// initTbFour3();
		// initTbFour1(new ArrayList());
		//
		// 初始化出口类型
		//
		// this.cbbImpExpType.removeAllItems();
		// this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
		// ImpExpType.DIRECT_EXPORT).toString(), "成品出口"));
		// this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
		// ImpExpType.TRANSFER_FACTORY_EXPORT).toString(), "转厂出口"));
		//
		// this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
		// ImpExpType.REWORK_EXPORT).toString(), "返工复出"));
		// this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
		// ALL_SELECT_ITEM).toString(), "全部"));
		// this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
		// ImpExpType.DIRECT_IMPORT).toString(), "料件进口"));
		// this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
		// ImpExpType.TRANSFER_FACTORY_IMPORT).toString(), "料件转厂"));
		// this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
		// ImpExpType.BACK_MATERIEL_EXPORT).toString(), "退料出口"));
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
		// 料件执行情况
		if (this.jTabbedPane.getSelectedIndex() == 0) {
			List contracts = jList11.getSelectedContracts();
			if (contracts.size() <= 0) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(this, "没有选中合同!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);

				return;
			}
			TempContractImg tempContractImg = (TempContractImg) tableModelTbFirst1
					.getCurrentRow();
			if (tempContractImg == null) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(this, "没有选中料件记录!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			/*
			 * 选择的 状态 (生效，未生效，全部)
			 */
			int state = -1;
			if (jRadioButton.isSelected()) {

				state = CustomsDeclarationState.EFFECTIVED;

			} else if (jRadioButton1.isSelected()) {
				state = CustomsDeclarationState.NOT_EFFECTIVED;
			} else if (jRadioButton2.isSelected()) {
				state = CustomsDeclarationState.ALL;
			}
			List list = this.contractAnalyseAction.findImpMaterielExeState(
					new Request(CommonVars.getCurrUser()), contracts,
					tempContractImg, state);
			initTbFrist2(list);
		} else if (this.jTabbedPane.getSelectedIndex() == 1) {// 进口料件统计
			List contracts = jListSecond.getSelectedContracts();
			if (contracts.size() <= 0) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(this, "没有选中合同!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			List list = new ArrayList();
			Date beginDate = cbbBeginDate2.getDate();
			Date endDate = cbbEndDate2.getDate();
			int state = -1;
			if (jRadioButton3.isSelected()) {
				state = CustomsDeclarationState.EFFECTIVED;
			} else if (jRadioButton4.isSelected()) {
				state = CustomsDeclarationState.NOT_EFFECTIVED;
			} else if (jRadioButton5.isSelected()) {
				state = state = CustomsDeclarationState.ALL;
			}
			list = this.contractAnalyseAction.findImpMaterielExeStat(
					new Request(CommonVars.getCurrUser()), contracts,
					beginDate, endDate, SEARCH_TYPE, state);

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
		// 截取小数位
		Integer decimalLength = 2;
		if (parameterSet != null
				&& parameterSet.getReportDecimalLength() != null)
			decimalLength = parameterSet.getReportDecimalLength();

		List<JTableListColumn> cm = tableModelTbFirst1.getColumns();
		// cm.get(5).setFractionCount(decimalLength);

	}

	private void initTbSecond(List list) {
		tableModelTbSecond = new JTableListModel(tbSecond, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("料件序号", "seqNum", 80));
						list.add(addColumn("记录号", "credenceNo", 80));
						list.add(addColumn("商品编码", "complexCode", 100));
						list.add(addColumn("品名规格", "nameSpec", 100));
						list.add(addColumn("单位", "unit.name", 100));
						list.add(addColumn("合同总定量", "contractTotalAmount", 100));
						list.add(addColumn("总进口量", "totalImpAmount", 100));
						list.add(addColumn("大单进口量", "orderImpAmount", 100));
						list.add(addColumn("料件进口总量", "materielImpAmount", 100));
						list.add(addColumn("转厂进口总量", "transferImpAmount", 100));
						list.add(addColumn("退料出口总量", "backMaterielExpAmount",
								100));
						list.add(addColumn("退料复出总量", "reworkExpAmount", 100));
						list.add(addColumn("退料退换总量(退换出口量)",
								"backMaterielExchange", 150));
						list.add(addColumn("退换进口量", "backMaterielExchangeImp",
								100));
						list.add(addColumn("本期内销数量", "saleInAmount", 100));
						// list.add(addColumn("出口成品使用总量",
						// "expFinishProductAmount", 100));
						list.add(addColumn("本期出口成品使用总量",
								"expProductStat.expTotalAmont", 100));
						list.add(addColumn("直接出口使用量",
								"expProductStat.directExport", 100));
						list.add(addColumn("转厂出口使用量",
								"expProductStat.transferFactoryExport", 100));
						list.add(addColumn("退厂返工使用量",
								"expProductStat.backFactoryRework", 100));
						list.add(addColumn("返工复出使用量",
								"expProductStat.reworkExport", 100));

						list.add(addColumn("余料情况", "remainStat", 100));
						list.add(addColumn("库存数量", "storeAmount", 100));
						// list.add(addColumn("缺料情况", "lackStat", 100));
						list.add(addColumn("可进口总量", "canImpAmount", 100));
						// list.add(addColumn("比例", "proportion", 100));
						list.add(addColumn("状态", "state", 100));
						list.add(addColumn("余料结转转出", "remainForwordExpAmount",
								100));
						list.add(addColumn("余料结转转入", "remainForwordImpAmount",
								100));
						list.add(addColumn("关封余量",
								"CustomsEnvelopRemainAmount", 100));
						list.add(addColumn("可直接进口量", "canDirectImpAmount", 100));
						list.add(addColumn("完成百分比", "completeScale", 60));//
						return list;
					}
				});

		// 截取小数位
		Integer decimalLength = 5;
		if (parameterSet != null && parameterSet.getCountBit() != null)
			decimalLength = parameterSet.getCountBit();
		tableModelTbSecond.setAllColumnsFractionCount(decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModelTbSecond.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());
		//
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
		// cm.get(17).setFractionCount(decimalLength);
		// cm.get(19).setFractionCount(decimalLength);
		// cm.get(20).setFractionCount(decimalLength);
		// cm.get(21).setFractionCount(decimalLength);
		// cm.get(22).setFractionCount(decimalLength);

		TableColorRender.setTableRowColor(tbSecond,
				tableModelTbSecond.getRowCount() - 1);
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

		// 截取小数位
		Integer decimalLength = 2;
		if (parameterSet != null
				&& parameterSet.getReportDecimalLength() != null)
			decimalLength = parameterSet.getReportDecimalLength();
		tableModelTbFirst2.setAllColumnsFractionCount(decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModelTbFirst2.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());
		// List<JTableListColumn> cm = tableModelTbFirst2.getColumns();
		// cm.get(6).setFractionCount(decimalLength);
		// cm.get(7).setFractionCount(decimalLength);

		tbFirst2.getColumnModel().getColumn(4)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							str = ImpExpType.getImpExpTypeDesc(Integer
									.valueOf(value.toString()));
						}
						this.setText(str);
						return this;
					}
				});
	}

	/**
	 * 选择合同项目
	 */
	private void selectContractItem() {
		jListSecond.showContractData(this.cbContractExe.isSelected(),
				this.cbContractCancel.isSelected());
	}

	private void initTbFour3() {
		//
		// 查询没有审核的合同记录
		//
		List list = this.contractAction.findContractByProcessExe(new Request(
				CommonVars.getCurrUser(), true));
		tableModelTbFour3 = new JTableListModel(tbFour3, list,
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
		tableModelTbFour3.setAllColumnsFractionCount(decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModelTbFour3.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());
		// List<JTableListColumn> cm = tableModelTbFour3.getColumns();
		// cm.get(4).setFractionCount(decimalLength);
		// cm.get(5).setFractionCount(decimalLength);

		tbFour3.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		if (list.size() > 0) {
			tbFour3.setRowSelectionInterval(0, 0);
		}
	}

	/**
	 * 初始化料件总表
	 * 
	 */
	private void initTbFour1(List list) {
		tableModelTbFour1 = new JTableListModel(tbFour1, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("料件序号", "seqNum", 100));
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("商品名称", "name", 100));
						list.add(addColumn("规格型号", "spec", 100));
						list.add(addColumn("计量单位", "unit.name", 100));
						list.add(addColumn("企业申报单价", "declarePrice", 100));
						list.add(addColumn("数量", "amount", 100));
						list.add(addColumn("单位重量", "unitWeight", 100));
						list.add(addColumn("凭证序号", "credenceNo", 100));
						return list;
					}
				});
		// 截取小数位
		Integer decimalLength = 2;
		if (parameterSet != null
				&& parameterSet.getReportDecimalLength() != null)
			decimalLength = parameterSet.getReportDecimalLength();

		List<JTableListColumn> cm = tableModelTbFour1.getColumns();
		cm.get(6).setFractionCount(decimalLength);
		cm.get(7).setFractionCount(decimalLength);
		cm.get(8).setFractionCount(decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModelTbFour1.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());

	}

	/**
	 * 初始化料件总表
	 * 
	 */
	private void initTbFour2(List list, int impExpType) {
		final int type = impExpType;
		tableModelTbFour2 = new JTableListModel(tbFour2, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("报关单号", "applyToCustomBillNo", 100));
						list.add(addColumn("报关日期", "applyToCustomDate", 100));
						list.add(addColumn("运输工具及装箱单号", "conveyance", 120));
						if (type == ALL_SELECT_ITEM
								|| type == ImpExpType.DIRECT_IMPORT) {
							list.add(addColumn("料件进口数量",
									"materielImportAmount", 100));
						}
						if (type == ALL_SELECT_ITEM
								|| type == ImpExpType.TRANSFER_FACTORY_IMPORT) {
							list.add(addColumn("料件转厂数量",
									"transferFactoryAmount", 100));
						}
						if (type == ALL_SELECT_ITEM
								|| type == ImpExpType.BACK_MATERIEL_EXPORT) {
							list.add(addColumn("退料出口数量",
									"backMaterielExportAmount", 100));
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

		List<JTableListColumn> cm = tableModelTbFour2.getColumns();
		if (type == ALL_SELECT_ITEM) {
			cm.get(4).setFractionCount(decimalLength);
			cm.get(5).setFractionCount(decimalLength);
			cm.get(6).setFractionCount(decimalLength);
		}
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModelTbFour2.setAllColumnsshowThousandthsign(other
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
				List listContract = this.jList11.getSelectedContracts();
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
					JOptionPane.showMessageDialog(
							DgMaterielExecuteAnalyse.this, "没有数据可打印", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					return;
				}
				CustomReportDataSource contractDs = new CustomReportDataSource(
						tempContractNoList);
				InputStream tempContractNoListSubReportStream = DgMaterielExecuteAnalyse.class
						.getResourceAsStream("report/TempContractNoList.jasper");
				JasperReport tempContractNoSubReport = (JasperReport) JRLoader
						.loadObject(tempContractNoListSubReportStream);
				//
				// 成品单耗报表 ？？ 要修改数据
				//
				TempContractImg tempContractImg = (TempContractImg) tableModelTbFirst1
						.getCurrentRow();
				List<TempContractImg> TempContractImgList = new ArrayList<TempContractImg>();
				TempContractImgList.add(tempContractImg);
				List<TempFinishProduct> subList = this.contractAnalyseAction
						.findContractBomByContractImg(
								new Request(CommonVars.getCurrUser()),
								TempContractImgList, listContract);

				InputStream subReportStream = DgMaterielExecuteAnalyse.class
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
					parameters.put("complexCode",
							tempContractImg.getComplexCode());
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
				InputStream masterReportStream = DgMaterielExecuteAnalyse.class
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
				List<TempContractNo> tempContractNoList = new ArrayList<TempContractNo>();
				if (listContract.isEmpty()) {
					JOptionPane.showMessageDialog(this, "请先选中合同记录!!!", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					return;
				}
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
					JOptionPane.showMessageDialog(
							DgMaterielExecuteAnalyse.this, "没有数据可打印", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
				parameters.put("company", company);
				parameters.put("searchBeginDate",
						CommonVars.dateToString(cbbBeginDate2.getDate()));
				parameters.put("searchEndDate",
						CommonVars.dateToString(cbbEndDate2.getDate()));
				List list = tableModelTbSecond.getList();
				if (list.size() <= 0) {
					JOptionPane.showMessageDialog(this, "没有数据可打印!", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					return;
				}
				CustomReportDataSource ds = new CustomReportDataSource(list);
				InputStream masterReportStream = DgMaterielExecuteAnalyse.class
						.getResourceAsStream("report/MaterielExecuteAnalyse2.jasper");
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
					parameters.put("beginDate",
							CommonVars.dateToString(contract.getBeginDate()));
					parameters.put("contractAmount",
							contract.getImgAmount() == null ? "" : contract
									.getImgAmount().toString());
					parameters.put("contractNo", contract.getImpContractNo());
					parameters.put("effectiveDate",
							CommonVars.dateToString(contract.getEndDate()));
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

				ContractImg contractImg = (ContractImg) tableModelTbFour1
						.getCurrentRow();
				if (contractImg != null) {
					parameters.put("name", contractImg.getName());
					parameters.put("no",
							contractImg.getCredenceNo() == null ? ""
									: contractImg.getCredenceNo().toString());
					parameters.put("spec", contractImg.getSpec());
					parameters.put("unit", contractImg.getUnit() == null ? ""
							: contractImg.getUnit().getName());
					parameters.put("unitPrice", "");
					parameters.put("unitWeight",
							contractImg.getUnitWeight() == null ? ""
									: contractImg.getUnitWeight().toString());
				}
				// paramters 还要改一下

				parameters.put("searchBeginDate",
						CommonVars.dateToString(cbbBeginDate4.getDate()));
				parameters.put("searchEndDate",
						CommonVars.dateToString(cbbEndDate4.getDate()));
				List list = tableModelTbFour2.getList();
				if (list == null || list.size() <= 0) {
					JOptionPane.showMessageDialog(
							DgMaterielExecuteAnalyse.this, "没有数据可打印", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					return;
				}
				CustomReportDataSource ds = new CustomReportDataSource(list);
				InputStream masterReportStream = DgMaterielExecuteAnalyse.class
						.getResourceAsStream("report/MaterielExecuteAnalyse3.jasper");
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
			jPanel4.setBounds(new Rectangle(24, 5, 307, 28));
			jPanel4.setBorder(BorderFactory.createTitledBorder(null, "",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			jPanel4.add(getJRadioButton(), null);
			jPanel4.add(getJRadioButton1(), null);
			jPanel4.add(getJRadioButton2(), null);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(new java.awt.Rectangle(25, 4, 71, 20));
			jRadioButton.setText("\u5df2\u751f\u6548");
			jRadioButton.setSelected(true);
		}
		return jRadioButton;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setBounds(new java.awt.Rectangle(111, 4, 86, 20));
			jRadioButton1.setText("\u672a\u751f\u6548");
		}
		return jRadioButton1;
	}

	/**
	 * This method initializes jRadioButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton2() {
		if (jRadioButton2 == null) {
			jRadioButton2 = new JRadioButton();
			jRadioButton2.setBounds(new java.awt.Rectangle(204, 4, 52, 20));
			jRadioButton2.setText("\u5168\u90e8");

		}
		return jRadioButton2;
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
			jPanel8.setBounds(new Rectangle(310, 64, 204, 37));
			jPanel8.setBorder(BorderFactory.createTitledBorder(null, "",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			jPanel8.add(getJRadioButton3(), null);
			jPanel8.add(getJRadioButton4(), null);
			jPanel8.add(getJRadioButton5(), null);
		}
		return jPanel8;
	}

	/**
	 * This method initializes jRadioButton3
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton3() {
		if (jRadioButton3 == null) {
			jRadioButton3 = new JRadioButton();
			jRadioButton3.setBounds(new Rectangle(9, 13, 67, 20));
			jRadioButton3.setText("\u5df2\u751f\u6548");
			jRadioButton3.setSelected(true);
		}
		return jRadioButton3;
	}

	/**
	 * This method initializes jRadioButton4
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton4() {
		if (jRadioButton4 == null) {
			jRadioButton4 = new JRadioButton();
			jRadioButton4.setBounds(new Rectangle(74, 13, 68, 20));
			jRadioButton4.setText("\u672a\u751f\u6548");
		}
		return jRadioButton4;
	}

	/**
	 * This method initializes jRadioButton5
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton5() {
		if (jRadioButton5 == null) {
			jRadioButton5 = new JRadioButton();
			jRadioButton5.setBounds(new Rectangle(143, 14, 52, 20));
			jRadioButton5.setText("\u5168\u90e8");

		}
		return jRadioButton5;
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
			jPanel9.setBounds(new java.awt.Rectangle(424, 12, 192, 24));
			jPanel9.setBorder(BorderFactory.createTitledBorder(null, "",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			jPanel9.add(getJRadioButton6(), null);
			jPanel9.add(getJRadioButton7(), null);
			jPanel9.add(getJRadioButton8(), null);
		}
		return jPanel9;
	}

	/**
	 * This method initializes jRadioButton6
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton6() {
		if (jRadioButton6 == null) {
			jRadioButton6 = new JRadioButton();
			jRadioButton6.setBounds(new java.awt.Rectangle(3, 2, 68, 20));
			jRadioButton6.setText("\u5df2\u751f\u6548");
		}
		return jRadioButton6;
	}

	/**
	 * This method initializes jRadioButton7
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton7() {
		if (jRadioButton7 == null) {
			jRadioButton7 = new JRadioButton();
			jRadioButton7.setBounds(new java.awt.Rectangle(72, 2, 63, 20));
			jRadioButton7.setText("\u672a\u751f\u6548");
		}
		return jRadioButton7;
	}

	/**
	 * This method initializes jRadioButton8
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton8() {
		if (jRadioButton8 == null) {
			jRadioButton8 = new JRadioButton();
			jRadioButton8.setBounds(new java.awt.Rectangle(144, 1, 52, 20));
			jRadioButton8.setText("\u5168\u90e8");
			jRadioButton8.setSelected(true);
		}
		return jRadioButton8;
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
			jPanel10.add(getJRadioButton9(), null);
			jPanel10.add(getJRadioButton10(), null);
		}
		return jPanel10;
	}

	/**
	 * This method initializes jRadioButton9
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton9() {
		if (jRadioButton9 == null) {
			jRadioButton9 = new JRadioButton();
			jRadioButton9.setBounds(new Rectangle(30, 1, 67, 22));
			jRadioButton9.setText("全选");
			jRadioButton9.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (jRadioButton9.isSelected()) {
						for (int i = 0; i < jList11.getModel().getSize(); i++) {
							Contract contract = (Contract) jList11.getModel()
									.getElementAt(i);
							contract.setSelected(true);
						}
						jList11.repaint();
					}
				}
			});
		}
		return jRadioButton9;
	}

	/**
	 * This method initializes jRadioButton10
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton10() {
		if (jRadioButton10 == null) {
			jRadioButton10 = new JRadioButton();
			jRadioButton10.setBounds(new Rectangle(97, 1, 67, 22));
			jRadioButton10.setText("全否");
			jRadioButton10.setSelected(true);
			jRadioButton10.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (jRadioButton10.isSelected()) {
						for (int i = 0; i < jList11.getModel().getSize(); i++) {
							Contract contract = (Contract) jList11.getModel()
									.getElementAt(i);
							contract.setSelected(false);
						}
						jList11.repaint();
					}
				}
			});
		}
		return jRadioButton10;
	}

	/**
	 * This method initializes jPanel11
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel11() {
		if (jPanel11 == null) {
			jPanel11 = new JPanel();
			jPanel11.setLayout(null);
			jPanel11.setPreferredSize(new Dimension(159, 81));
			jPanel11.add(getJRadioButton11(), null);
			jPanel11.add(getJRadioButton12(), null);
			jPanel11.add(getJPanel2(), null);
		}
		return jPanel11;
	}

	/**
	 * This method initializes jRadioButton11
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton11() {
		if (jRadioButton11 == null) {
			jRadioButton11 = new JRadioButton();
			jRadioButton11.setBounds(new Rectangle(6, 1, 61, 22));
			jRadioButton11.setText("\u5168\u9009");
			jRadioButton11.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (jRadioButton11.isSelected()) {
						for (int i = 0; i < getJListSecond().getModel()
								.getSize(); i++) {
							Contract contract = (Contract) jListSecond
									.getModel().getElementAt(i);
							contract.setSelected(true);
						}
						jListSecond.repaint();
					}
				}
			});
		}
		return jRadioButton11;
	}

	/**
	 * This method initializes jRadioButton12
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton12() {
		if (jRadioButton12 == null) {
			jRadioButton12 = new JRadioButton();
			jRadioButton12.setBounds(new Rectangle(65, 0, 67, 22));
			jRadioButton12.setText("\u5168\u5426");
			jRadioButton12.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (jRadioButton12.isSelected()) {
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
			jRadioButton12.setSelected(true);
		}
		return jRadioButton12;
	}

	/**
	 * This method initializes jRadioButton13
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton13() {
		if (jRadioButton13 == null) {
			jRadioButton13 = new JRadioButton();
			jRadioButton13.setBounds(new java.awt.Rectangle(3, 1, 67, 22));
			jRadioButton13.setText("\u5168\u9009");
		}
		return jRadioButton13;
	}

	/**
	 * This method initializes jRadioButton14
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton14() {
		if (jRadioButton14 == null) {
			jRadioButton14 = new JRadioButton();
			jRadioButton14.setBounds(new java.awt.Rectangle(84, 1, 67, 22));
			jRadioButton14.setText("\u5168\u5426");
		}
		return jRadioButton14;
	}

	/**
	 * This method initializes buttonGroup1
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup1() {
		if (buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
			buttonGroup1.add(this.getJRadioButton9());
			buttonGroup1.add(this.getJRadioButton10());
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
			buttonGroup2.add(getJRadioButton11());
			buttonGroup2.add(getJRadioButton12());
		}
		return buttonGroup2;
	}

	/**
	 * This method initializes buttonGroup3
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup3() {
		if (buttonGroup3 == null) {
			buttonGroup3 = new ButtonGroup();
			buttonGroup3.add(this.getJRadioButton13());
			buttonGroup3.add(this.getJRadioButton14());
		}
		return buttonGroup3;
	}

	/**
	 * This method initializes buttonGroup4
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup4() {
		if (buttonGroup4 == null) {
			buttonGroup4 = new ButtonGroup();
			buttonGroup4.add(this.jRadioButton3);
			buttonGroup4.add(this.jRadioButton4);
			buttonGroup4.add(this.jRadioButton5);
		}
		return buttonGroup4;
	}

	class MyFindThread extends Thread {
		public void run() {
			CommonProgress.showProgressDialog();
			CommonProgress.setMessage("系统正获取数据，请稍后...");
			search();
			CommonProgress.closeProgressDialog();
		}
	}

	/**
	 * This method initializes jPanel12
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel12() {
		if (jPanel12 == null) {
			jPanel12 = new JPanel();
			jPanel12.setLayout(new BorderLayout());
			jPanel12.add(getJPanel11(), BorderLayout.NORTH);
			jPanel12.add(getJScrollPane3(), BorderLayout.CENTER);
		}
		return jPanel12;
	}

	/**
	 * This method initializes jSplitPane3
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane3() {
		if (jSplitPane3 == null) {
			jSplitPane3 = new JSplitPane();
			jSplitPane3.setDividerLocation(800);
			jSplitPane3.setLeftComponent(getJScrollPane2());
			jSplitPane3.setRightComponent(getJPanel12());
			jSplitPane3.setDividerSize(2);
		}
		return jSplitPane3;
	}

	/**
	 * This method initializes cbContractExe1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbContractExe1() {
		if (cbContractExe1 == null) {
			cbContractExe1 = new JCheckBox();
			cbContractExe1
					.setText("\u6b63\u5728\u6267\u884c\u7684\u5408\u540c");
			cbContractExe1.setSelected(true);
			cbContractExe1
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (cbContractExe1.isSelected()
									&& cbContractCancel1.isSelected()) {
								jList11.showContractData(true, true);
							} else if (cbContractExe1.isSelected()
									&& !cbContractCancel1.isSelected()) {
								jList11.showContractData(true, false);
							} else if (!cbContractExe1.isSelected()
									&& cbContractCancel1.isSelected()) {
								jList11.showContractData(false, true);
							} else {
								jList11.showContractData(false, false);
							}
						}
					});
		}
		return cbContractExe1;
	}

	/**
	 * This method initializes cbContractCancel1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbContractCancel1() {
		if (cbContractCancel1 == null) {
			cbContractCancel1 = new JCheckBox();
			cbContractCancel1.setText("\u6838\u9500\u7684\u5408\u540c");
			cbContractCancel1
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (cbContractExe1.isSelected()
									&& cbContractCancel1.isSelected()) {
								jList11.showContractData(true, true);
							} else if (cbContractExe1.isSelected()
									&& !cbContractCancel1.isSelected()) {
								jList11.showContractData(true, false);
							} else if (!cbContractExe1.isSelected()
									&& cbContractCancel1.isSelected()) {
								jList11.showContractData(false, true);
							} else {
								jList11.showContractData(false, false);
							}
						}
					});
		}
		return cbContractCancel1;
	}

	/**
	 * This method initializes btnFormulasearch
	 * 
	 * @return javax.swing.JButton
	 */
	/*
	 * private JButton getBtnFormulasearch() { if (btnFormulasearch == null) {
	 * btnFormulasearch = new JButton(); btnFormulasearch.setBounds(new
	 * Rectangle(389, 6, 101, 28)); btnFormulasearch.setText("公式查询");
	 * btnFormulasearch.addActionListener(new java.awt.event.ActionListener() {
	 * public void actionPerformed(java.awt.event.ActionEvent e) { Formulasearch
	 * f=new Formulasearch(); f.setVisible(true); } }); } return
	 * btnFormulasearch; }
	 */
} // @jve:decl-index=0:visual-constraint="12,46"
