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

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractanalyse.action.ContractAnalyseAction;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JCheckBox;
import java.awt.GridLayout;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgWrapStat extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JScrollPane jScrollPane1 = null;

	private JContractList jList = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JCalendarComboBox cbbEndDate = null;

	private JLabel jLabel3 = null;

	private JComboBox cbbWrapType = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JTableListModel tableModel = null;

	private ContractAnalyseAction contractAnalyseAction = null;

	private CustomBaseAction customBaseAction = null;

	private JPanel jPanel1 = null;

	private JRadioButton rbYes = null;

	private JRadioButton rbNo = null;

	private JRadioButton rbAll = null;

	private ButtonGroup buttonGroup = null;

	private JPanel jPanel2 = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	private ButtonGroup buttonGroup1 = null;

	private BcsParameterSet parameterSet = null;

	private ContractAction contractAction = null;

	private JPanel jPanel3 = null;

	private JPanel jPanel4 = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel5 = null;

	private JCheckBox cbContractExe = null;

	private JCheckBox cbContractCancel = null;

	/**
	 * This is the default constructor
	 */
	public DgWrapStat() {
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
			this.cbbBeginDate.setDate(CommonVars.getBeginDate());
			queryData();
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
		this.setResizable(false);
		this.setTitle("进口包装统计");
		this.setSize(750, 510);
		this.setContentPane(getJContentPane());
		this.getButtonGroup();
		getButtonGroup1();
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
			jContentPane.add(getJPanel4(), BorderLayout.NORTH);
			jLabel1.setText("报关日期:");
			jLabel1.setBounds(new Rectangle(6, 7, 55, 23));
			jLabel2.setText("至");
			jLabel2.setBounds(new Rectangle(142, 7, 14, 23));
			jLabel3.setText("包装种类");
			jLabel3.setBounds(new Rectangle(249, 7, 52, 23));
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
			cbbBeginDate.setBounds(new Rectangle(62, 7, 79, 23));
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
			cbbEndDate.setBounds(new Rectangle(157, 7, 79, 23));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbWrapType() {
		if (cbbWrapType == null) {
			cbbWrapType = new JComboBox();
			cbbWrapType.setBounds(new Rectangle(304, 7, 104, 23));
		}
		return cbbWrapType;
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
			jButton.setBounds(new Rectangle(598, 7, 65, 23));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new MyFindThread().start();
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
			jButton1.setBounds(new Rectangle(665, 7, 65, 23));
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List contracts = ((JContractList) jList)
							.getSelectedContracts();
					StringBuffer sb = new StringBuffer("");
					for (int i = 0; i < contracts.size(); i++) {
						Contract contract = (Contract) contracts.get(i);
						sb.append(contract.getImpContractNo() + "/"
								+ contract.getEmsNo()
								+ (i != contracts.size() - 1 ? ";" : ""));
					}
					if (tableModel == null || tableModel.getList().size() <= 0) {
						JOptionPane.showMessageDialog(DgWrapStat.this,
								"没有数据可打印", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					CustomReportDataSource ds = new CustomReportDataSource(
							tableModel.getList());
					InputStream reportStream = DgWrapStat.class
							.getResourceAsStream("report/WrapStat.jasper");
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd");
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("beginDate",
							(cbbBeginDate.getDate() == null ? "" : dateFormat
									.format(cbbBeginDate.getDate())));
					parameters.put("endDate",
							(cbbEndDate.getDate() == null ? "" : dateFormat
									.format(cbbEndDate.getDate())));
					parameters.put("contractNo", sb.toString());
					String wrapName = "";
					if (cbbWrapType.getSelectedItem() != null) {
						Wrap wrap = (Wrap) cbbWrapType.getSelectedItem();
						wrapName = wrap.getName();
					}
					parameters.put("wrapType", wrapName);
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

	private void queryData() {
		List contracts = ((JContractList) jList).getSelectedContracts();
		String wrapCode = null;
		if (cbbWrapType.getSelectedItem() != null) {
			Wrap wrap = (Wrap) cbbWrapType.getSelectedItem();
			wrapCode = wrap.getCode();
		}
		int state = -1;
		if (this.rbYes.isSelected()) {
			state = CustomsDeclarationState.EFFECTIVED;
		} else if (this.rbNo.isSelected()) {
			state = CustomsDeclarationState.NOT_EFFECTIVED;
		} else {
			state = CustomsDeclarationState.ALL;
		}
		List list = contractAnalyseAction.findImportWrapStat(new Request(
				CommonVars.getCurrUser()), contracts, cbbBeginDate.getDate(),
				cbbEndDate.getDate(), wrapCode, state);
		initTable(list);
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
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("手册号", "emsNo", 120));
						list.add(addColumn("报关单号", "customsDeclarationCode",
								150));
						list.add(addColumn("包装名称", "wrapName", 100));
						list.add(addColumn("包材重量", "wrapWeight", 100));
						list.add(addColumn("单位", "wrapUnit", 100));
						list.add(addColumn("备注", "memos", 100));
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
	}

	private void initUIComponents() {

		// 初始化包装种类
		List listwarp = customBaseAction.findWrap();
		this.getCbbWrapType().setModel(
				new DefaultComboBoxModel(listwarp.toArray()));
		this.cbbWrapType.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbWrapType, "code", "name");
		this.cbbWrapType.setSelectedIndex(-1);
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
			jPanel1.setBorder(BorderFactory.createTitledBorder(null, "",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			jPanel1.setBounds(new Rectangle(411, 4, 186, 31));
			jPanel1.add(getRbYes(), null);
			jPanel1.add(getRbNo(), null);
			jPanel1.add(getRbAll(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbYes() {
		if (rbYes == null) {
			rbYes = new JRadioButton();
			rbYes.setBounds(new java.awt.Rectangle(5, 5, 62, 20));
			rbYes.setText("\u5df2\u751f\u6548");
			rbYes.setSelected(true);
		}
		return rbYes;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbNo() {
		if (rbNo == null) {
			rbNo = new JRadioButton();
			rbNo.setBounds(new java.awt.Rectangle(63, 6, 66, 20));
			rbNo.setText("\u672a\u751f\u6548");
		}
		return rbNo;
	}

	/**
	 * This method initializes jRadioButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbAll() {
		if (rbAll == null) {
			rbAll = new JRadioButton();
			rbAll.setBounds(new java.awt.Rectangle(128, 6, 52, 20));
			rbAll.setText("\u5168\u90e8");

		}
		return rbAll;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.rbAll);
			buttonGroup.add(this.rbYes);
			buttonGroup.add(this.rbNo);
		}
		return buttonGroup;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.add(getJRadioButton(), null);
			jPanel2.add(getJRadioButton1(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(new Rectangle(11, 1, 67, 22));
			jRadioButton.setText("\u5168\u9009");
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
			jRadioButton1.setBounds(new Rectangle(73, 1, 67, 22));
			jRadioButton1.setText("\u5168\u5426");
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

	class MyFindThread extends Thread {
		public void run() {
			CommonProgress.showProgressDialog();
			CommonProgress.setMessage("系统正获取数据，请稍后...");
			queryData();
			CommonProgress.closeProgressDialog();
		}
	}

	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(3);
			jPanel3 = new JPanel();
			jPanel3.setLayout(gridLayout);
			jPanel3.setPreferredSize(new Dimension(165, 61));
			jPanel3.add(getJPanel2(), null);
			jPanel3.add(getCbContractExe(), null);
			jPanel3.add(getCbContractCancel(), null);
		}
		return jPanel3;
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
//			jPanel4.setPreferredSize(new Dimension(742, 46));
			jPanel4.setPreferredSize(new Dimension(744, 38));
			jPanel4.add(jLabel1, null);
			jPanel4.add(getCbbBeginDate(), null);
			jPanel4.add(getCbbEndDate(), null);
			jPanel4.add(jLabel2, null);
			jPanel4.add(jLabel3, null);
			jPanel4.add(getCbbWrapType(), null);
			jPanel4.add(getJPanel1(), null);
			jPanel4.add(getJButton(), null);
			jPanel4.add(getJButton1(), null);
		}
		return jPanel4;
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
			jSplitPane.setLeftComponent(getJScrollPane());
			jSplitPane.setRightComponent(getJPanel5());
			jSplitPane.setDividerLocation(600);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel5	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(new BorderLayout());
			jPanel5.add(getJPanel3(), BorderLayout.NORTH);
			jPanel5.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return jPanel5;
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
} // @jve:decl-index=0:visual-constraint="10,10"
