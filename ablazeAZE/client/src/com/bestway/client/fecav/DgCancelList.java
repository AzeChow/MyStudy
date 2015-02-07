/*
 * Created on 2005-5-26
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.fecav;

import java.awt.Component;
import java.awt.Rectangle;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcs.client.contractanalyse.JContractList;
import com.bestway.bcs.client.contractstat.DgImpExpScheduleDetail;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractstat.action.ContractStatAction;
import com.bestway.bcs.contractstat.entity.CancelAfterVerification;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ProjectType;
import com.bestway.dzsc.client.contractanalyse.JDzscContractList;
import com.bestway.dzsc.client.contractstat.DgDzscImpExpScheduleDetail;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.fecav.action.FecavAction;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgCancelList extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JScrollPane jScrollPane1 = null;

	private JList jList = null;

	private JButton btnAdd = null;

	private JButton btnDelete = null;

	private JButton btnQuery = null;

	private JButton btnPrint = null;

	private JLabel jLabel1 = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JLabel jLabel2 = null;

	private JCalendarComboBox cbbEndDate = null;

	private List lsCav = new ArrayList();

	private Contract contract = null;

	private JTableListModel tableModel = null;

	private ContractStatAction contractStatAction = null;

	private FecavAction fecavAction = null;

	private JLabel jLabel3 = null;

	private JScrollPane jScrollPane2 = null;

	private JContractList jListBcs = null;

	private BcusContractList jListJbcus = null;

	private JDzscContractList jListDzsc = null;

	private JRadioButton rbBcs = null;

	private JRadioButton rbDzsc = null;

	private JRadioButton rbJbcus = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="777,57"

	private JPanel jPanel = null;

	private JRadioButton rbAll = null;

	private JRadioButton rbZero = null;

	private ButtonGroup buttonGroup1 = null; // @jve:decl-index=0:visual-constraint="863,54"

	/**
	 * This is the default constructor
	 */
	public DgCancelList() {
		super();
		initialize();
		contractStatAction = (ContractStatAction) CommonVars
				.getApplicationContext().getBean("contractStatAction");
		fecavAction = (FecavAction) CommonVars.getApplicationContext().getBean(
				"fecavAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			fecavAction.controlRepotByCancel(new Request(CommonVars
					.getCurrUser()));
			// if (contract != null) {
			// this.cbbBeginDate.setDate(contract.getBeginDate());
			// } else {
			// this.cbbBeginDate.setDate(null);
			// }
			// List list = new ArrayList();
			// if (this.contract != null) {
			// list = this.contractStatAction.findCancelAfterVerificationList(
			// new Request(CommonVars.getCurrUser()), this.contract,
			// cbbBeginDate.getDate(), cbbEndDate.getDate());
			// }
			// initTable(list);
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
		getButtonGroup();
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
			jLabel3.setBounds(new java.awt.Rectangle(515, 279, 79, 21));
			jLabel3.setText("\u5df2\u9009\u6838\u9500\u5355");
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel1.setBounds(14, 450, 66, 16);
			jLabel1.setText("起始日期从");
			jLabel2.setBounds(174, 450, 16, 19);
			jLabel2.setText("到");
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(getJScrollPane1(), null);
			jContentPane.add(getBtnAdd(), null);
			jContentPane.add(getBtnDelete(), null);
			jContentPane.add(getBtnQuery(), null);
			jContentPane.add(getBtnPrint(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getCbbBeginDate(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getCbbEndDate(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getJScrollPane2(), null);
			jContentPane.add(getRbBcs(), null);
			jContentPane.add(getRbDzsc(), null);
			jContentPane.add(getRbJbcus(), null);
			jContentPane.add(getJPanel(), null);
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
			jScrollPane.setBounds(1, 0, 514, 438);
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
			jScrollPane1.setBounds(515, 300, 227, 140);
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
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setBounds(397, 448, 100, 23);
			btnAdd.setText("选中核销单");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
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
		return btnAdd;
	}

	private void addCav(CancelAfterVerification cav) {
		if (!lsCav.contains(cav)) {
			lsCav.add(cav);
		}
		jList.setListData(lsCav.toArray());
	}

	private void removeCav() {

		for (int i = 0; i < lsCav.size(); i++) {
			lsCav.remove(i);
		}
		jList.setListData(lsCav.toArray());
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setBounds(520, 448, 100, 23);
			btnDelete.setText("移除核销单");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
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
		return btnDelete;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setBounds(298, 448, 70, 23);
			btnQuery.setText("查询");
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					search();
				}
			});
		}
		return btnQuery;
	}

	private void search() {
		List list = new ArrayList();
		if (rbBcs.isSelected()) {
			List contracts = jListBcs.getSelectedContracts();
			if (contracts.size() < 1) {
				return;
			}
			if (contracts != null) {
				list = this.fecavAction.findCustomsDeclarationByProjectType(
						new Request(CommonVars.getCurrUser()), contracts,
						cbbBeginDate.getDate(), cbbEndDate.getDate(),
						ProjectType.BCS);
			}
		} else if (rbDzsc.isSelected()) {
			List contracts = jListDzsc.getSelectedContracts();
			if (contracts.size() < 1) {
				return;
			}
			if (contracts != null) {
				list = this.fecavAction.findCustomsDeclarationByProjectType(
						new Request(CommonVars.getCurrUser()), contracts,
						cbbBeginDate.getDate(), cbbEndDate.getDate(),
						ProjectType.DZSC);
			}
		} else if (rbJbcus.isSelected()) {
			List contracts = jListJbcus.getSelectedContracts();
			if (contracts.size() < 1) {
				return;
			}
			if (contracts != null) {
				list = this.fecavAction.findCustomsDeclarationByProjectType(
						new Request(CommonVars.getCurrUser()), contracts,
						cbbBeginDate.getDate(), cbbEndDate.getDate(),
						ProjectType.BCUS);
			}
		}
		initTable(list);
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setBounds(644, 448, 68, 23);
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
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
		return btnPrint;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(85, 448, 83, 23);
			cbbBeginDate.setDate(null);
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
			cbbEndDate.setBounds(199, 448, 83, 23);
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
		// this.tableModel.setExcelFileName(this.contract.getEmsNo());
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setBounds(new java.awt.Rectangle(516, 31, 226, 240));
			jScrollPane2.setViewportView(getJListBcs());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jList1
	 * 
	 * @return javax.swing.JList
	 */
	private JList getJListBcs() {
		if (jListBcs == null) {
			jListBcs = new JContractList();
		}
		return jListBcs;
	}

	private JList getJListJbcus() {
		if (jListJbcus == null) {
			jListJbcus = new BcusContractList();
		}
		return jListJbcus;
	}

	private JList getJListDzsc() {
		if (jListDzsc == null) {
			jListDzsc = new JDzscContractList();
		}
		return jListDzsc;
	}

	/**
	 * This method initializes rbBcs
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbBcs() {
		if (rbBcs == null) {
			rbBcs = new JRadioButton();
			rbBcs.setBounds(new java.awt.Rectangle(517, 3, 74, 26));
			rbBcs.setText("纸质手册");
			rbBcs.setSelected(true);
			rbBcs.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					removeCav();
					jScrollPane2.setViewportView(getJListBcs());
					jScrollPane2.repaint();
					selectAllOrZero();
				}
			});
		}
		return rbBcs;
	}

	/**
	 * This method initializes rbDzsc
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbDzsc() {
		if (rbDzsc == null) {
			rbDzsc = new JRadioButton();
			rbDzsc.setBounds(new java.awt.Rectangle(593, 3, 76, 26));
			rbDzsc.setText("电子手册");
			rbDzsc.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					removeCav();
					jScrollPane2.setViewportView(getJListDzsc());
					jScrollPane2.repaint();
					selectAllOrZero();
				}
			});
		}
		return rbDzsc;
	}

	/**
	 * This method initializes rbJbcus
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbJbcus() {
		if (rbJbcus == null) {
			rbJbcus = new JRadioButton();
			rbJbcus.setBounds(new java.awt.Rectangle(666, 3, 76, 26));
			rbJbcus.setText("联网监管");
			rbJbcus.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (jList.getModel().getSize() > 0) {
						removeCav();
					}
					jScrollPane2.setViewportView(getJListJbcus());
					jScrollPane2.repaint();
				}
			});
		}
		return rbJbcus;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.getRbBcs());
			buttonGroup.add(this.getRbDzsc());
			buttonGroup.add(this.getRbJbcus());
		}
		return buttonGroup;
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
			jPanel.setBounds(new java.awt.Rectangle(594, 274, 141, 25));
			jPanel.add(getRbAll(), null);
			jPanel.add(getRbZero(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbAll() {
		if (rbAll == null) {
			rbAll = new JRadioButton();
			rbAll.setBounds(new Rectangle(3, 1, 67, 22));
			rbAll.setText("\u5168\u9009");
			rbAll.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					selectAllOrZero();
				}

			});
		}
		return rbAll;
	}

	private void selectAllOrZero() {
		if (rbBcs.isSelected()) {
			for (int i = 0; i < jListBcs.getModel().getSize(); i++) {
				Contract contract = (Contract) jListBcs.getModel()
						.getElementAt(i);
				if (rbAll.isSelected()) {
					contract.setSelected(true);
				} else {
					contract.setSelected(false);
				}
			}
			jListBcs.repaint();
		} else if (rbDzsc.isSelected()) {
			for (int i = 0; i < jListDzsc.getModel().getSize(); i++) {
				DzscEmsPorHead contract = (DzscEmsPorHead) jListDzsc.getModel()
						.getElementAt(i);
				if (rbAll.isSelected()) {
					contract.setSelected(true);
				} else {
					contract.setSelected(false);
				}
			}
			jListDzsc.repaint();
		} else if (rbJbcus.isSelected()) {
			for (int i = 0; i < jListJbcus.getModel().getSize(); i++) {
				EmsHeadH2k contract = (EmsHeadH2k) jListJbcus.getModel()
						.getElementAt(i);
				if (rbAll.isSelected()) {
					contract.setIsSelected(true);
				} else {
					contract.setIsSelected(false);
				}
			}
			jListJbcus.repaint();
		}
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbZero() {
		if (rbZero == null) {
			rbZero = new JRadioButton();
			rbZero.setBounds(new Rectangle(84, 1, 67, 22));
			rbZero.setText("\u5168\u5426");
			rbZero.setSelected(true);
			rbZero.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					selectAllOrZero();
				}

			});
		}
		return rbZero;
	}

	/**
	 * This method initializes buttonGroup1
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup1() {
		if (buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
			buttonGroup1.add(getRbAll());
			buttonGroup1.add(getRbZero());

		}
		return buttonGroup1;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
