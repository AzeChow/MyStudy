/*
 * Created on 2005-5-26
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractstat;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractstat.action.ContractStatAction;
import com.bestway.bcs.contractstat.entity.CancelAfterVerification;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JSplitPane;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgCancelAfterVerificationList extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JScrollPane jScrollPane1 = null;

	private JList jList = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JButton jButton3 = null;

	private JLabel jLabel1 = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JLabel jLabel2 = null;

	private JCalendarComboBox cbbEndDate = null;

	private List lsCav = new ArrayList();

	private Contract contract = null;

	private JTableListModel tableModel = null;

	private ContractStatAction contractStatAction = null;

	private JPanel jPanel1 = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel2 = null;

	/**
	 * @return Returns the contract.
	 */
	public Contract getContract() {
		return contract;
	}

	/**
	 * @param contract
	 *            The contract to set.
	 */
	public void setContract(Contract contract) {
		this.contract = contract;
	}

	/**
	 * This is the default constructor
	 */
	public DgCancelAfterVerificationList() {
		super();
		initialize();
		contractStatAction = (ContractStatAction) CommonVars
				.getApplicationContext().getBean("contractStatAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			if (contract != null) {
				this.cbbBeginDate.setDate(contract.getBeginDate());
			} else {
				this.cbbBeginDate.setDate(null);
			}
			List list = new ArrayList();
			List lists=new ArrayList();
			lists.add(this.contract);
			if (this.contract != null) {
				list = this.contractStatAction.findCancelAfterVerificationList(
						new Request(CommonVars.getCurrUser()),lists,
						cbbBeginDate.getDate(), cbbEndDate.getDate());
			}
			initTable(list);			
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(750, 510);
		this.setTitle("核销单登记表");
		this.setContentPane(getJContentPane());
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
			jContentPane.add(getJPanel1(), BorderLayout.NORTH);
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
			jLabel1.setText("起始日期从");
			jLabel2.setText("到");
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
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						if (tableModel.getCurrentRow() == null) {
							return;
						}
						CancelAfterVerification cav = (CancelAfterVerification) tableModel
								.getCurrentRow();
						addCav(cav);
					}
				}
			});
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
			jList = new JList();
			jList.setCellRenderer(new DefaultListCellRenderer() {
				public Component getListCellRendererComponent(JList list,
						Object value, int index, boolean isSelected,
						boolean cellHasFocus) {
					super.getListCellRendererComponent(list, value, index,
							isSelected, cellHasFocus);
					String s = "";
					if (value != null) {
						s = ((CancelAfterVerification) value).getCavNo();
					}
					setText(s);
					return this;
				}
			});
		}
		return jList;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jLabel = new JLabel();
			jPanel.setLayout(new BorderLayout());
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
			jLabel.setText("已选核销单");
			jPanel.add(jLabel, java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("选中核销单");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						return;
					}
					CancelAfterVerification cav = (CancelAfterVerification) tableModel
							.getCurrentRow();
					addCav(cav);
				}
			});
		}
		return jButton;
	}

	private void addCav(CancelAfterVerification cav) {
		if (!lsCav.contains(cav)) {
			lsCav.add(cav);
		}
		jList.setListData(lsCav.toArray());
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("移除核销单");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int index = jList.getSelectedIndex();
					if (index < 0) {
						return;
					}
					lsCav.remove(index);
					jList.setListData(lsCav.toArray());
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
			jButton2.setText("查询");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = new ArrayList();
					List lists=new ArrayList();
					lists.add(contract);
					if (contract != null) {
						list = contractStatAction
								.findCancelAfterVerificationList(new Request(
										CommonVars.getCurrUser()), lists,
										cbbBeginDate.getDate(), cbbEndDate
												.getDate());
					}
					initTable(list);
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("打印");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (lsCav == null || lsCav.size() <= 0) {
						JOptionPane.showMessageDialog(
								DgCancelAfterVerificationList.this, "没有数据可打印",
								"提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					CustomReportDataSource ds = new CustomReportDataSource(
							lsCav);
					InputStream reportStream = null;
					reportStream = DgImpExpScheduleDetail.class
							.getResourceAsStream("report/CancelAfterVerification.jasper");
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
		return jButton3;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
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
		}
		return cbbEndDate;
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
						list
								.add(addColumn("报关单号", "customsDeclarationNo",
										100));
						list.add(addColumn("核销单号", "cavNo", 100));
						list.add(addColumn("总值", "totalPrice", 100));
						list.add(addColumn("加工费", "processPrice", 100));
						list.add(addColumn("料费", "materialPrice", 100));
						return list;
					}
				});
//		this.tableModel.setExcelFileName(this.contract.getEmsNo());
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			jPanel1 = new JPanel();
			jPanel1.setLayout(flowLayout);
			jPanel1.add(jLabel1, null);
			jPanel1.add(getCbbBeginDate(), null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(getCbbEndDate(), null);
			jPanel1.add(getJButton2(), null);
			jPanel1.add(getJButton(), null);
			jPanel1.add(getJButton1(), null);
			jPanel1.add(getJButton3(), null);
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
			jSplitPane.setDividerSize(5);
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
			jPanel2.add(getJPanel(), BorderLayout.NORTH);
			jPanel2.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return jPanel2;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
