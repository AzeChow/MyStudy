/*
 * Created on 2005-8-2
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contract;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author yp
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgContractBomSort extends JDialogBase {

	private javax.swing.JPanel jContentPane = null; // @jve:decl-index=0:

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JScrollPane jScrollPane = null;

	private JList jList = null;

	private JButton btnUp = null; // @jve:decl-index=0:

	private JButton btnDown = null;

	private JButton btnOk = null;

	private Vector list = null;

	private JButton btnCancel = null;

	private ContractAction contractAction = null;

	private boolean isOk = false;

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	/**
	 * This is the default constructor
	 */
	public DgContractBomSort() {
		super();
		initialize();
	}

	public void setVisible(boolean b) {
		if (b) {
			Vector list = this.getList();
			Vector listS = new Vector();
			for (int i = 0; i < list.size(); i++) {
				ContractBom obj = (ContractBom) list.get(i);
				obj.setSeqNum(i + 1);
				listS.add(obj);
			}
			if (listS != null && listS.size() > 0) {
				jList.setListData(listS);
				jList.setSelectedIndex(0);
			}
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(433, 370);
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("排序");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerSize(0);
			jSplitPane.setDividerLocation(320);
			jSplitPane.setLeftComponent(getJPanel());
			jSplitPane.setRightComponent(getJPanel1());
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
			jPanel1.setLayout(null);
			jPanel1.add(getBtnUp(), null);
			jPanel1.add(getBtnDown(), null);
			jPanel1.add(getBtnOk(), null);
			jPanel1.add(getBtnCancel(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJList());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getJList() {
		if (jList == null) {
			jList = new JList();
			jList.setCellRenderer(new UserListCellRenderer());
			jList.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN,
					12));
		}
		return jList;
	}

	class UserListCellRenderer extends DefaultListCellRenderer {
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected,
					cellHasFocus);
			String s = "";
			if (value != null) {
				ContractBom obj = ((ContractBom) value);
				s = formatStringByLength(obj.getSeqNum().toString(), 5)
						+ formatStringByLength(obj.getComplex() == null ? ""
								: obj.getComplex().getCode(), 20)
						+ formatStringByLength(obj.getName(), 50)
						+ formatStringByLength(obj.getSpec(), 50);
			}
			setText(s);
			return this;
		}
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUp() {
		if (btnUp == null) {
			btnUp = new JButton();
			btnUp.setBounds(32, 93, 48, 22);
			btnUp.setText("↑");
			btnUp.setForeground(java.awt.Color.blue);
			btnUp.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					move(true);
				}
			});
		}
		return btnUp;
	}

	private void move(boolean isup) {
		if (jList.getSelectedValue() == null) {
			JOptionPane.showMessageDialog(DgContractBomSort.this, "请选择要移动的项!",
					"提示", 2);
			return;
		}
		int x = jList.getSelectedIndex();
		if ((isup && x == 0) || (!isup && x == list.size() - 1)) {
			return;
		}
		Object selectObj = jList.getSelectedValue();// 当前记录
		ContractBom selectinfo = null;
		ContractBom preinfo = null;
		if (isup) {// 向上移动
			selectinfo = (ContractBom) selectObj;
			selectinfo.setSeqNum(selectinfo.getSeqNum() - 1);
			preinfo = (ContractBom) jList.getModel().getElementAt(x - 1);
			preinfo.setSeqNum(preinfo.getSeqNum() + 1);
			list.setElementAt(selectinfo, x - 1);
			list.setElementAt(preinfo, x);
			jList.setListData(list);
			jList.setSelectedIndex(x - 1);
		} else { // 向下移动
			selectinfo = (ContractBom) selectObj;
			selectinfo.setSeqNum(selectinfo.getSeqNum() + 1);
			preinfo = (ContractBom) jList.getModel().getElementAt(x + 1);
			preinfo.setSeqNum(preinfo.getSeqNum() - 1);
			list.setElementAt(selectinfo, x + 1);
			list.setElementAt(preinfo, x);
			jList.setListData(list);
			jList.setSelectedIndex(x + 1);
		}

	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDown() {
		if (btnDown == null) {
			btnDown = new JButton();
			btnDown.setBounds(32, 131, 48, 22);
			btnDown.setText("↓");
			btnDown.setForeground(java.awt.Color.blue);
			btnDown.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					move(false);
				}
			});
		}
		return btnDown;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(24, 258, 63, 29);
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Vector list = DgContractBomSort.this.getList();
					contractAction.saveContractBom(new Request(CommonVars
							.getCurrUser()), list);
					isOk = true;
					dispose();
				}
			});
		}
		return btnOk;
	}

	/**
	 * @return Returns the list.
	 */
	public Vector getList() {
		return list;
	}

	/**
	 * @param list
	 *            The list to set.
	 */
	public void setList(Vector list) {
		this.list = list;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(24, 296, 63, 29);
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * @return Returns the baseEncAction.
	 */
	public ContractAction getContractAction() {
		return contractAction;
	}

	/**
	 * @param baseEncAction
	 *            The baseEncAction to set.
	 */
	public void setContractAction(ContractAction baseEncAction) {
		this.contractAction = baseEncAction;
	}

	private String formatStringByLength(String s, int len) {
		if (s == null) {
			s = "";
		}
		int m = len - s.length();
		for (int i = 0; i < m; i++) {
			s += " ";
		}
		return s;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
