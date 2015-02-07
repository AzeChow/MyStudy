package com.bestway.dzsc.client.dzscmanage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgDzscBOMExport extends JDialogBase {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JTextField tfBeginSeq = null;
	private JLabel jLabel1 = null;
	private JTextField tfEndSeq = null;
	private JButton btnQuery = null;
	private JButton btnCancel = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JTableListModel tbModel = null;
	private ContractAction contractAction = null; // 合同
	private DzscEmsPorHead head = null; // 头
	private DzscAction dzscAction = null;

	/**
	 * @param owner
	 */
	public DgDzscBOMExport() {
		super();
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
		initialize();
		initTable(new ArrayList());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(654, 449);
		this.setContentPane(getJContentPane());
	}

	private boolean checkData() {
		String bestr = getTfBeginSeq().getText();
		String enStr = getTfEndSeq().getText();
		int be = 0, en = 0;
		try {
			if (getTfBeginSeq().getText() != null
					&& !getTfBeginSeq().getText().equals("")) {
				be = Integer.parseInt(bestr);
			}
			if (getTfEndSeq().getText() != null
					&& !getTfEndSeq().getText().equals("")) {
				en = Integer.parseInt(enStr);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(DgDzscBOMExport.this,
					"输入非法整数，请重新输入！", "警告！", JOptionPane.WARNING_MESSAGE);
			return false;

		}
		if (be < 0 || en < 0) {
			JOptionPane.showMessageDialog(DgDzscBOMExport.this, "成品序号不能为负数！",
					"警告！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (en < be) {
			JOptionPane.showMessageDialog(DgDzscBOMExport.this,
					"后面序号应该大于或等于前面序号！", "警告！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	private void initTable(final List list) {
		this.tbModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("成品序号", "dzscEmsExgBill.seqNum", 80,
								Integer.class));
						list.add(addColumn("成品商品编码", "dzscEmsExgBill.complex.code", 100));
						list.add(addColumn("成品名称", "dzscEmsExgBill.name", 100));
						list.add(addColumn("成品规格", "dzscEmsExgBill.spec", 100));
						list.add(addColumn("成品单位", "dzscEmsExgBill.unit.name", 80));
						list.add(addColumn("料件序号", "imgSeqNum", 80,
								Integer.class));
						list.add(addColumn("料件编码", "complex.code", 80));
						list.add(addColumn("料件名称", "name", 80));
						list.add(addColumn("料件规格", "spec", 80));
						list
								.add(addColumn("料件商品单位", "unit.name", 80,
										Integer.class));
//						list.add(addColumn("数量", "amount", 200));
//						list.add(addColumn("单价", "price", 200));
						list.add(addColumn("单耗", "unitWare", 80));
						list.add(addColumn("损耗率", "ware", 80));
						return list;
					}
				});
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
			jContentPane.add(getJPanel(), BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(120, 6, 18, 24));
			jLabel1.setText("到");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(4, 6, 74, 24));
			jLabel.setText("成品序号从：");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setPreferredSize(new Dimension(540, 39));
			jPanel.add(jLabel, null);
			jPanel.add(getTfBeginSeq(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getTfEndSeq(), null);
			jPanel.add(getBtnQuery(), null);
			jPanel.add(getBtnCancel(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes tfBeginSeq
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBeginSeq() {
		if (tfBeginSeq == null) {
			tfBeginSeq = new JTextField();
			tfBeginSeq.setBounds(new Rectangle(79, 6, 40, 24));
		}
		return tfBeginSeq;
	}

	/**
	 * This method initializes tfEndSeq
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEndSeq() {
		if (tfEndSeq == null) {
			tfEndSeq = new JTextField();
			tfEndSeq.setBounds(new Rectangle(140, 6, 40, 24));
		}
		return tfEndSeq;
	}

	/**
	 * This method initializes btnQuery
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setBounds(new Rectangle(285, 6, 80, 24));
			btnQuery.setText("查询");
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					Integer be = 0, en = 0;
					if ((getTfBeginSeq().getText() == null || getTfBeginSeq()
							.getText().equals(""))
							&& (getTfEndSeq().getText() == null || getTfEndSeq()
									.getText().equals(""))) {
						List list = dzscAction.findDzscEmsBomBillBySeq(
								new Request(CommonVars.getCurrUser()), head,
								null, null);
						initTable(list);
					} else {
						if (!checkData()) {
							return;
						}
						be = (getTfBeginSeq().getText() == null || getTfBeginSeq()
								.getText().equals("")) ? null : Integer
								.parseInt(getTfBeginSeq().getText());
						en = (getTfEndSeq().getText() == null || getTfEndSeq()
								.getText().equals("")) ? null : Integer
								.parseInt(getTfEndSeq().getText());
						List list = dzscAction.findDzscEmsBomBillBySeq(
								new Request(CommonVars.getCurrUser()), head,
								be, en);
						initTable(list);
					}
				}
			});
		}
		return btnQuery;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new Rectangle(374, 6, 74, 24));
			btnCancel.setText("关闭");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
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

	public DzscEmsPorHead getHead() {
		return head;
	}

	public void setHead(DzscEmsPorHead head) {
		this.head = head;
	}

} // @jve:decl-index=0:visual-constraint="135,26"
