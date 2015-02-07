/*
 * Created on 2005-6-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractanalyse;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractanalyse.action.ContractAnalyseAction;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.client.materialbase.MaterialQuery;
import com.bestway.dzsc.message.action.DzscMessageAction;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JCheckBox;
import java.awt.GridLayout;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgFinishedProductExport extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JLabel jLabel = null;

	private JScrollPane jScrollPane1 = null;

	private JContractList jList = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JTableListModel tableModel = null;

	private ContractAnalyseAction contractAnalyseAction = null;

	private CustomBaseAction customBaseAction = null;

	private JToolBar jToolBar = null;

	private JPanel jPanel1 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel2 = null;

	private DzscMessageAction dzscMessageAction = null;

	private BcsParameterSet parameterSet = null;

	private ContractAction contractAction = null;

	private JPanel jPanel21 = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	private ButtonGroup buttonGroup1 = null;

	private JPanel jPanel = null;

	private JPanel jPanel3 = null;

	private JCheckBox cbContractExe = null;

	private JCheckBox cbContractCancel = null;

	/**
	 * This is the default constructor
	 */
	public DgFinishedProductExport() {
		super();
		initialize();
		contractAnalyseAction = (ContractAnalyseAction) CommonVars
				.getApplicationContext().getBean("contractAnalyseAction");
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");

		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		parameterSet = contractAction.findBcsParameterSet(new Request(
				CommonVars.getCurrUser(), true));
	}

	public void setVisible(boolean b) {
		if (b) {
			initUIComponents();
			initTable(new ArrayList());
			this.cbbBeginDate.setDate(null);
			this.cbbEndDate.setDate(null);
			jSplitPane.setDividerLocation(0.8);
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
		this.setResizable(true);
		this.setTitle("料件耗用明细(编码级)");
		this.setSize(750, 510);
		this.setContentPane(getJContentPane());
		getButtonGroup1();
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
		}
		return jTable;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJList());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jList
	 * 
	 * @return javax.swing.JList
	 */
	private JContractList getJList() {
		if (jList == null) {
			jList = new JContractList();
			jList.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					Date beginDate = null;
					// Date endDate=null;
					int size = jList.getModel().getSize();
					for (int i = 0; i < size; i++) {
						Contract contract = (Contract) jList.getModel()
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
						cbbBeginDate.setDate(beginDate);
					}
				}
			});
		}
		return jList;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new java.awt.Rectangle(64, 3, 85, 23));
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
			cbbEndDate.setBounds(new java.awt.Rectangle(168, 3, 85, 23));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("查询");
			jButton.setBounds(new java.awt.Rectangle(435, 4, 63, 23));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					queryData();
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("打印");
			jButton1.setBounds(new java.awt.Rectangle(503, 4, 65, 23));
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel == null || tableModel.getList().size() <= 0) {
						JOptionPane.showMessageDialog(
								DgFinishedProductExport.this, "没有数据可打印", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					List contracts = jList.getSelectedContracts();
					// if(contracts.size()<1){
					// return;
					// }
					StringBuffer sb = new StringBuffer("");
					for (int i = 0; i < contracts.size(); i++) {
						Contract contract = (Contract) contracts.get(i);
						sb.append(contract.getImpContractNo() + "/"
								+ contract.getEmsNo()
								+ (i != contracts.size() - 1 ? ";" : ""));
					}
					CustomReportDataSource ds = new CustomReportDataSource(
							tableModel.getList());
					InputStream reportStream = DgFinishedProductExport.class
							.getResourceAsStream("report/FinishedProductExportAmount.jasper");
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd");
					Map<String, Object> parameters = new HashMap<String, Object>();

					JasperPrint jasperPrint = null;
					try {
						jasperPrint = JasperFillManager.fillReport(
								reportStream, parameters, ds);
						DgReportViewer viewer = new DgReportViewer(jasperPrint);
						viewer.setVisible(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("关闭");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton2;
	}

	private void queryData() {
		List contracts = jList.getSelectedContracts();
		if (contracts.size() <= 0) {
			CommonProgress.closeProgressDialog();
			JOptionPane.showMessageDialog(this, "没有选中合同!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);

			return;
		}

		// 获取所选合同中的料件信息
		List lsMateriel = MaterialQuery.getInstance().findMaterialInContract(
				contracts);
		// 通过获得的料件信息统计出口和耗用
		if (lsMateriel != null) {
			List list = contractAnalyseAction.findFinishedProductExportAmount(
					new Request(CommonVars.getCurrUser()), lsMateriel,
					contracts, cbbBeginDate.getDate(), cbbEndDate.getDate());
			initTable(list);
		}

	}

	/**
	 * 初始化数据Table
	 */
	private void initTable(List list) {
		if (list == null) {
			list = new ArrayList();
		}
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();//
						list.add(addColumn("料件记录号", "contractBom.imgCredenceNo", 80));
						list.add(addColumn("料件报关编码", "complexCode", 100));
						list.add(addColumn("料件报关名称", "name", 100));
						list.add(addColumn("料件报关规格", "spec", 100));
						list.add(addColumn("料件报关单位", "unit.name", 100));//contractBom.getContractExg().getCredenceNo()
						list.add(addColumn("成品记录号",
								"contractBom.contractExg.credenceNo", 80));
						list.add(addColumn("成品报关编码",
								"contractBom.contractExg.complex.code", 100));
						list.add(addColumn("成品报关名称",
								"contractBom.contractExg.name", 100));
						list.add(addColumn("成品报关规格",
								"contractBom.contractExg.spec", 100));
						list.add(addColumn("成品报关单位",
								"contractBom.contractExg.unit.name", 100));
						list
								.add(addColumn(
										"合同协议号",
										"contractBom.contractExg.contract.expContractNo",
										100));
						list.add(addColumn("单耗", "unitWaste", 100));
						list.add(addColumn("损耗", "waste", 100));
						list.add(addColumn("总出口量", "exportAmount", 100));
						list.add(addColumn("结转出口耗用", "transferExpUsedAmount",
								100));
						list
								.add(addColumn("直接出口耗用", "directExpUsedAmount",
										100));
						list.add(addColumn("总耗用量", "usedAmount", 100));
						return list;
					}
				});

		// 截取小数位
		Integer decimalLength = 2;
		if (parameterSet != null
				&& parameterSet.getReportDecimalLength() != null)
			decimalLength = parameterSet.getReportDecimalLength();
		tableModel.setAllColumnsFractionCount(decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModel.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());
		// List<JTableListColumn> cm = tableModel.getColumns();
		// cm.get(10).setFractionCount(decimalLength);
		// cm.get(11).setFractionCount(decimalLength);
		// cm.get(12).setFractionCount(decimalLength);
		// cm.get(13).setFractionCount(decimalLength);

	}

	private void initUIComponents() {

	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJPanel1());
			jToolBar.add(getJButton2());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(151, 3, 14, 24));
			jLabel5.setText("\u81f3");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(9, 5, 54, 19));
			jLabel4.setText("\u62a5\u5173\u65e5\u671f:");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.add(getCbbBeginDate(), null);
			jPanel1.add(jLabel4, null);
			jPanel1.add(jLabel5, null);
			jPanel1.add(getCbbEndDate(), null);
			jPanel1.add(getJButton(), null);
			jPanel1.add(getJButton1(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(550);
			jSplitPane.setDividerSize(6);
			jSplitPane.setLeftComponent(getJScrollPane());
			jSplitPane.setRightComponent(getJPanel2());
		}
		return jSplitPane;
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
			jPanel2.add(getJPanel(), BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel21
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel21() {
		if (jPanel21 == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(3);
			jPanel21 = new JPanel();
			jPanel21.setLayout(gridLayout);
			jPanel21.setPreferredSize(new Dimension(153, 56));
			jPanel21.add(getJPanel3(), null);
			jPanel21.add(getCbContractExe(), null);
			jPanel21.add(getCbContractCancel(), null);
		}
		return jPanel21;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setText("全选");
			jRadioButton.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (jRadioButton.isSelected()) {
						for (int i = 0; i < jList.getModel().getSize(); i++) {
							Contract contract = (Contract) jList.getModel()
									.getElementAt(i);
							contract.setSelected(true);
						}
						jList.repaint();
					}
				}
			});
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
			jRadioButton1.setText("全否");
			jRadioButton1.setSelected(true);
			jRadioButton1.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (jRadioButton1.isSelected()) {
						for (int i = 0; i < jList.getModel().getSize(); i++) {
							Contract contract = (Contract) jList.getModel()
									.getElementAt(i);
							contract.setSelected(false);
						}
						jList.repaint();
					}
				}
			});
		}
		return jRadioButton1;
	}

	/**
	 * This method initializes buttonGroup1
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup1() {
		if (buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
			buttonGroup1.add(getJRadioButton());
			buttonGroup1.add(getJRadioButton1());
		}
		return buttonGroup1;
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
			jPanel.add(getJPanel21(), BorderLayout.NORTH);
			jPanel.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new GridBagLayout());
			jPanel3.add(getJRadioButton1(), new GridBagConstraints());
			jPanel3.add(getJRadioButton(), new GridBagConstraints());
		}
		return jPanel3;
	}

	/**
	 * This method initializes cbContractExe	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbContractExe() {
		if (cbContractExe == null) {
			cbContractExe = new JCheckBox();
			cbContractExe.setText("\u6b63\u5728\u6267\u884c\u7684\u5408\u540c");
			cbContractExe.setSelected(true);
			cbContractExe
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (cbContractExe.isSelected()
							&& cbContractCancel.isSelected()) {
						jList.showContractData(true, true);
					} else if (cbContractExe.isSelected()
							&& !cbContractCancel.isSelected()) {
						jList.showContractData(true, false);
					} else if (!cbContractExe.isSelected()
							&& cbContractCancel.isSelected()) {
						jList.showContractData(false, true);
					} else {
						jList.showContractData(false, false);
					}
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
			cbContractCancel.setText("\u6838\u9500\u7684\u5408\u540c");
			cbContractCancel
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (cbContractExe.isSelected()
							&& cbContractCancel.isSelected()) {
						jList.showContractData(true, true);
					} else if (cbContractExe.isSelected()
							&& !cbContractCancel.isSelected()) {
						jList.showContractData(true, false);
					} else if (!cbContractExe.isSelected()
							&& cbContractCancel.isSelected()) {
						jList.showContractData(false, true);
					} else {
						jList.showContractData(false, false);
					}
				}
			});
		}
		return cbContractCancel;
	}
}
