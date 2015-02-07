package com.bestway.client.fecav;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.FecavState;
import com.bestway.common.constant.ProjectType;
import com.bestway.fecav.action.FecavAction;
import com.bestway.fecav.entity.FecavBill;
import com.bestway.fecav.entity.TempCustomsDeclarationInfoForFecav;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class DgUseParallelFecavBill extends JDialogBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton jButton = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JToolBar jToolBar1 = null;

	private JToolBar jToolBar2 = null;

	private JScrollPane jScrollPane = null;

	private JTable tbFecavBill = null;

	private JScrollPane jScrollPane1 = null;

	private JTable tbExport = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;

	private JComboBox cbbFecavBill = null;

	private JTextField tfFecavBill = null;

	private JButton btnFecavBillQuery = null;

	private JComboBox cbbExport = null;

	private JTextField tfExport = null;

	private JButton btnExportQuery = null;

	private JPanel jPanel4 = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private static FecavAction fecavAction = null;

	private JTableListModel fecavBillTableModel = null;

	private JTableListModel exportTableModel = null;

	private List lsFecavBill = null;

	private List lsResult = new ArrayList();

	public List getLsResult() {
		return lsResult;
	}

	public void setLsFecavBill(List lsFecavBill) {
		this.lsFecavBill = lsFecavBill;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgUseParallelFecavBill() {
		super();
		initialize();
		fecavAction = (FecavAction) CommonVars.getApplicationContext().getBean(
				"fecavAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			this.initFecavBillTable(this.lsFecavBill);
			List lsExport = fecavAction
					.findCustomsDeclarationInfoForFecav(new Request(CommonVars
							.getCurrUser()));
			this.initExportTable(lsExport);
			CommonQuery.getInstance().addCommonFilter(this.cbbFecavBill,
					this.tfFecavBill, this.btnFecavBillQuery,
					this.fecavBillTableModel);
			CommonQuery.getInstance().addCommonFilter(this.cbbExport,
					this.tfExport, this.btnExportQuery, this.exportTableModel);
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(860, 505));
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("核销单和出口报关单对照");
		this.setContentPane(getJContentPane());

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
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.SOUTH);
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
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
			jToolBar = new JToolBar();
			jToolBar.setFloatable(false);
			jToolBar.add(getJButton());
			jToolBar.add(getJPanel4());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText(" ");
			jButton.setVisible(false);
		}
		return jButton;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(430);
			jSplitPane.setLeftComponent(getJPanel());
			jSplitPane.setRightComponent(getJPanel1());
			jSplitPane.setDividerSize(8);
		}
		return jSplitPane;
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
			jPanel.add(getJToolBar1(), java.awt.BorderLayout.NORTH);
			jPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
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
			jPanel1.add(getJToolBar2(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jLabel = new JLabel();
			jLabel.setText("核销单资料");
			jLabel.setForeground(java.awt.Color.blue);
			jToolBar1 = new JToolBar();
			jToolBar1.setFloatable(false);
			jToolBar1.add(jLabel);
			jToolBar1.add(getJPanel2());
			jToolBar1.add(getJButton1());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes jToolBar2
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar2() {
		if (jToolBar2 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("出口报关单资料");
			jLabel1.setForeground(java.awt.Color.blue);
			jToolBar2 = new JToolBar();
			jToolBar2.setFloatable(false);
			jToolBar2.add(jLabel1);
			jToolBar2.add(getJPanel3());
			jToolBar2.add(getJButton2());
		}
		return jToolBar2;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbFecavBill());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbFecavBill() {
		if (tbFecavBill == null) {
			tbFecavBill = new JTable();
		}
		return tbFecavBill;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbExport());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbExport() {
		if (tbExport == null) {
			tbExport = new JTable();
		}
		return tbExport;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText(" ");
			jButton1.setVisible(false);
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
			jButton2.setText(" ");
			jButton2.setVisible(false);
		}
		return jButton2;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.insets = new java.awt.Insets(3,3,4,6);
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.ipadx = 6;
			gridBagConstraints2.ipady = -5;
			gridBagConstraints2.gridx = 2;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.ipadx = 134;
			gridBagConstraints1.ipady = 1;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.insets = new java.awt.Insets(3,2,4,3);
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.ipadx = 97;
			gridBagConstraints.ipady = -4;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.insets = new java.awt.Insets(3,4,4,2);
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GridBagLayout());
			jPanel2.add(getCbbFecavBill(), gridBagConstraints);
			jPanel2.add(getTfFecavBill(), gridBagConstraints1);
			jPanel2.add(getBtnFecavBillQuery(), gridBagConstraints2);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.insets = new java.awt.Insets(3,3,4,12);
			gridBagConstraints5.gridy = 0;
			gridBagConstraints5.ipadx = 7;
			gridBagConstraints5.ipady = -5;
			gridBagConstraints5.gridx = 2;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints4.gridx = 1;
			gridBagConstraints4.gridy = 0;
			gridBagConstraints4.ipadx = 107;
			gridBagConstraints4.ipady = 1;
			gridBagConstraints4.weightx = 1.0;
			gridBagConstraints4.insets = new java.awt.Insets(3,2,4,3);
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.ipadx = 75;
			gridBagConstraints3.ipady = -4;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.insets = new java.awt.Insets(3,6,4,2);
			jPanel3 = new JPanel();
			jPanel3.setLayout(new GridBagLayout());
			jPanel3.add(getCbbExport(), gridBagConstraints3);
			jPanel3.add(getTfExport(), gridBagConstraints4);
			jPanel3.add(getBtnExportQuery(), gridBagConstraints5);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbFecavBill() {
		if (cbbFecavBill == null) {
			cbbFecavBill = new JComboBox();
		}
		return cbbFecavBill;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFecavBill() {
		if (tfFecavBill == null) {
			tfFecavBill = new JTextField();
		}
		return tfFecavBill;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFecavBillQuery() {
		if (btnFecavBillQuery == null) {
			btnFecavBillQuery = new JButton();
			btnFecavBillQuery.setText("查询");
		}
		return btnFecavBillQuery;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbExport() {
		if (cbbExport == null) {
			cbbExport = new JComboBox();
		}
		return cbbExport;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExport() {
		if (tfExport == null) {
			tfExport = new JTextField();
		}
		return tfExport;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExportQuery() {
		if (btnExportQuery == null) {
			btnExportQuery = new JButton();
			btnExportQuery.setText("查询");
		}
		return btnExportQuery;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.insets = new java.awt.Insets(2,125,3,253);
			gridBagConstraints7.gridy = 0;
			gridBagConstraints7.ipadx = 30;
			gridBagConstraints7.ipady = -3;
			gridBagConstraints7.gridx = 1;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.insets = new java.awt.Insets(2,154,3,125);
			gridBagConstraints6.gridy = 0;
			gridBagConstraints6.ipadx = 30;
			gridBagConstraints6.ipady = -3;
			gridBagConstraints6.gridx = 0;
			jPanel4 = new JPanel();
			jPanel4.setLayout(new GridBagLayout());
			jPanel4.add(getBtnOk(), gridBagConstraints6);
			jPanel4.add(getBtnCancel(), gridBagConstraints7);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (fecavBillTableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								DgUseParallelFecavBill.this, "请选择你要对应的核销单",
								"提示", JOptionPane.OK_OPTION);
						return;
					}
					if (exportTableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								DgUseParallelFecavBill.this, "请选择你要对应的出口报关单",
								"提示", JOptionPane.OK_OPTION);
						return;
					}
					FecavBill fecavBill = (FecavBill) fecavBillTableModel
							.getCurrentRow();
					TempCustomsDeclarationInfoForFecav temp = (TempCustomsDeclarationInfoForFecav) exportTableModel
							.getCurrentRow();
					fecavBill.setCustomsDeclarationId(temp
							.getCustomsDeclarationId());
					fecavBill.setCustomsDeclarationCode(temp
							.getCustomsDeclarationCode());
					fecavBill.setExportDate(temp.getExportDate());
					fecavBill.setDeclareDate(temp.getDeclareDate());
					fecavBill.setContractNo(temp.getContractNo());
					fecavBill.setEmsNo(temp.getEmsNo());
					fecavBill.setCurr(temp.getCurr());
					fecavBill.setTotalPrice(temp.getTotalPrice());
					fecavBill.setIsPrintWhite(temp.getIsPrintWhite());
					fecavBill.setIsPrintYellow(temp.getIsPrintYellow());
					fecavBill = fecavAction.saveFecavBill(new Request(
							CommonVars.getCurrUser()), fecavBill);
					lsResult.add(fecavBill);
					fecavBillTableModel.deleteRow(fecavBill);
					exportTableModel.deleteRow(temp);
				}
			});
		}
		return btnOk;
	}

	/**
	 * This method initializes jButton6
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("关闭");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	private void initFecavBillTable(List list) {
		fecavBillTableModel = new JTableListModel(tbFecavBill, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<Object> list = (List<Object>) (new Vector());
						list.add(addColumn("出口日期", "exportDate", 100));
						list.add(addColumn("申报日期", "declareDate", 100));
						list.add(addColumn("核销单号 ", "code", 200));
						list.add(addColumn("领单日期", "innerObtainDate", 100));
						list.add(addColumn("报关单号", "customsDeclarationCode",
								200));
						list.add(addColumn("合同号码", "contractNo", 100));
						list.add(addColumn("手册号码", "emsNo", 100));
						list.add(addColumn("币别", "curr.name", 100));
						list.add(addColumn("总金额", "totalPrice", 100));
						list.add(addColumn("标志", "billState", 100));
						return list;
					}
				});
		TableColumn column = this.tbFecavBill.getColumnModel().getColumn(10);
		column.setCellRenderer(new DefaultTableCellRenderer() {
			// JLabel label = new JLabel();
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				super.getTableCellRendererComponent(table, value, isSelected,
						hasFocus, row, column);
				int state = -1;
				if (value != null) {
					state = Integer.parseInt(value.toString());
				}
				switch (state) {
				case FecavState.INNER_OBTAIN:
					this.setText("未使用");
					break;
				}
				return this;
			}
		});
	}

	private void initExportTable(List list) {
		exportTableModel = new JTableListModel(tbExport, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<Object> list = (List<Object>) (new Vector());
						list.add(addColumn("出口日期", "exportDate", 100));
						list.add(addColumn("申报日期", "declareDate", 100));
						list.add(addColumn("报关单号", "customsDeclarationCode",
								100));
						list.add(addColumn("合同号码", "contractNo", 100));
						list.add(addColumn("手册号码", "emsNo", 100));
						list.add(addColumn("币别", "curr.name", 100));
						list.add(addColumn("总金额", "totalPrice", 100));
						list.add(addColumn("报关单来源", "projectType", 100));
						return list;
					}
				});
		TableColumn column = this.tbExport.getColumnModel().getColumn(8);
		column.setCellRenderer(new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				super.getTableCellRendererComponent(table, value, isSelected,
						hasFocus, row, column);
				String str = "";
				if (value != null) {
					Integer projectType = Integer.valueOf(value.toString());
					switch (projectType) {
					case ProjectType.BCUS:
						str = "BCUS";
						break;
					case ProjectType.BCS:
						str = "BCS";
						break;
					}
				}
				this.setText(str);
				return this;
			}
		});
	}
} // @jve:decl-index=0:visual-constraint="10,10"
