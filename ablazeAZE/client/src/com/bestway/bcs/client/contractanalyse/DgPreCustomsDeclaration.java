/*
 * Created on 2005-6-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractanalyse;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractanalyse.action.ContractAnalyseAction;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JRadioButton;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.ButtonGroup;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JSplitPane;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class DgPreCustomsDeclaration extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JScrollPane jScrollPane1 = null;

	private JList jList = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JCalendarComboBox cbbEndDate = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JTableListModel tableModel = null;

	private ContractAnalyseAction contractAnalyseAction = null;

	private JPanel jPanel1 = null;

	private JRadioButton rbYes = null;

	private JRadioButton rbNo = null;

	private JRadioButton rbAll = null;

	private ButtonGroup buttonGroup = null;  //  @jve:decl-index=0:visual-constraint="767,133"

	private JPanel jPanel2 = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	private ButtonGroup buttonGroup1 = null; // @jve:decl-index=0:visual-constraint="772,89"

	private JLabel jLabel3 = null;

	private JPanel jPanel = null;

	private JPanel jPanel3 = null;

	private JSplitPane jSplitPane = null;

	/**
	 * This is the default constructor
	 */
	public DgPreCustomsDeclaration() {
		super();
		initialize();
		contractAnalyseAction = (ContractAnalyseAction) CommonVars
				.getApplicationContext().getBean("contractAnalyseAction");

	}

	public void setVisible(boolean b) {
		if (b) {
			initUIComponents();
			cbbBeginDate.setDate(CommonVars.getBeginDate());
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
		this.setSize(new Dimension(772, 524));
		this.setTitle("报关单预录入库查询");
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
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel3(), BorderLayout.NORTH);
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
			jLabel1.setText("报关日期:");
			jLabel1.setBounds(new Rectangle(8, 10, 55, 23));
			jLabel2.setText("至");
			jLabel2.setBounds(new Rectangle(154, 10, 14, 23));
//			jContentPane.add(getJScrollPane(), null);
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
	private JList getJList() {
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
			cbbBeginDate.setBounds(new Rectangle(66, 10, 79, 23));
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
			cbbEndDate.setBounds(new Rectangle(170, 10, 79, 23));
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
			jButton.setBounds(new Rectangle(451, 10, 63, 23));
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
			jButton1.setBounds(new Rectangle(519, 10, 65, 23));
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel == null || tableModel.getList().size() <= 0) {
						JOptionPane.showMessageDialog(
								DgPreCustomsDeclaration.this, "没有数据可打印", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					CustomReportDataSource ds = new CustomReportDataSource(
							tableModel.getList());
					InputStream reportStream = DgPreCustomsDeclaration.class
							.getResourceAsStream("report/PreCustomsDeclaration.jasper");
					Map<String, String> parameters = new HashMap<String, String>();
					parameters.put("companyName", CommonVars.getCurrUser()
							.getCompany().getName());
					List contracts = ((JContractList) jList)
							.getSelectedContracts();
					StringBuffer sb = new StringBuffer("");
					for (int i = 0; i < contracts.size(); i++) {
						Contract contract = (Contract) contracts.get(i);
						sb.append(contract.getImpContractNo() + "/"
								+ contract.getEmsNo()
								+ (i != contracts.size() - 1 ? ";" : ""));
					}
					if ("".equals(sb.toString().trim())) {
						sb.append("所有手册");
					}
					parameters.put("emsNo", sb.toString());
					String beginEndDate = "";
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd");
					if (cbbBeginDate.getDate() != null) {
						beginEndDate += ("开始:" + dateFormat.format(cbbBeginDate
								.getDate()));
					}
					if (cbbEndDate.getDate() != null) {
						beginEndDate += ("  到:" + dateFormat.format(cbbEndDate
								.getDate()));
					}
					parameters.put("beginEndDate", beginEndDate);
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
		int state = -1;
		if (this.rbYes.isSelected()) {
			state = CustomsDeclarationState.EFFECTIVED;
		} else if (this.rbNo.isSelected()) {
			state = CustomsDeclarationState.NOT_EFFECTIVED;
		} else {
			state = CustomsDeclarationState.ALL;
		}
		List list = contractAnalyseAction.findPreCustomsDeclaration(
				new Request(CommonVars.getCurrUser()), contracts, cbbBeginDate
						.getDate(), cbbEndDate.getDate(), state);
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
						list.add(addColumn("海关编号", "customsDeclarationCode",
								150));
						list.add(addColumn("预录入号", "preCustomsDeclarationCode",
								100));
						list.add(addColumn("申报日期", "declarationDate", 100));
						list.add(addColumn("放行日期", "impExpDate", 100));
						list.add(addColumn("报关单类型", "impExpType", 100));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(5).setCellRenderer(
				new DefaultTableCellRenderer() {
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

	private void initUIComponents() {

		// 初始化包装种类
		// this.getCbbWrapType().setModel(
		// CustomBaseModel.getInstance().getWrapModel());
		// this.getCbbWrapType().setRenderer(
		// CustomBaseRender.getInstance().getRender());
		// CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
		// this.getCbbWrapType());
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
			jPanel1.setBounds(new Rectangle(253, 7, 195, 30));
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
			rbYes.setBounds(new Rectangle(5, 5, 62, 20));
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
			rbNo.setBounds(new Rectangle(63, 6, 66, 20));
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
			rbAll.setBounds(new Rectangle(128, 6, 52, 20));
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
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(2, 2, 153, 54));
			jLabel3.setText("正在执行的合同");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setPreferredSize(new Dimension(156, 64));
			jPanel2.add(getJRadioButton(), null);
			jPanel2.add(getJRadioButton1(), null);
			jPanel2.add(jLabel3, null);
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
			jRadioButton.setBounds(new Rectangle(3, 37, 67, 22));
			jRadioButton.setText("全选");
			jRadioButton.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (jRadioButton.isSelected()) {
						Date beginDate = null;
						for (int i = 0; i < jList.getModel().getSize(); i++) {
							Contract contract = (Contract) jList.getModel()
									.getElementAt(i);
							contract.setSelected(true);
							if (beginDate == null) {
								beginDate = contract.getBeginDate();
							} else {
								if (beginDate
										.compareTo(contract.getBeginDate()) > 0) {
									beginDate = contract.getBeginDate();
								}
							}

						}
						if (beginDate != null) {
							cbbBeginDate.setDate(beginDate);
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
			jRadioButton1.setBounds(new Rectangle(84, 37, 67, 22));
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

	class MyFindThread extends Thread {
		public void run() {
			CommonProgress.showProgressDialog();
			CommonProgress.setMessage("系统正获取数据，请稍后...");
			queryData();
			CommonProgress.closeProgressDialog();
		}
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
			jPanel.add(getJPanel2(), BorderLayout.NORTH);
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
			jPanel3.setLayout(null);
			jPanel3.setPreferredSize(new Dimension(611, 46));
			jPanel3.add(getJButton(), null);
			jPanel3.add(getJButton1(), null);
			jPanel3.add(getJPanel1(), null);
			jPanel3.add(getCbbEndDate(), null);
			jPanel3.add(getCbbBeginDate(), null);
			jPanel3.add(jLabel2, null);
			jPanel3.add(jLabel1, null);
		}
		return jPanel3;
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
			jSplitPane.setRightComponent(getJPanel());
			jSplitPane.setDividerLocation(600);
		}
		return jSplitPane;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
