package com.bestway.client.custom.common;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.custombase.entity.hscode.CheckupComplex;
import com.bestway.common.Request;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.ui.winuicontrol.JDialogBase;

@SuppressWarnings({ "rawtypes", "serial", "unchecked" })
public class DgSortChecked extends JDialogBase {

	private javax.swing.JPanel jContentPane = null; // @jve:decl-index=0:
	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JScrollPane jScrollPane = null;
	private JList jList = null;
	private JButton jButton = null; // @jve:decl-index=0:
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private Vector list = null; // @jve:decl-index=0:
	private JButton jButton3 = null;
	private BaseEncAction baseEncAction = null; // @jve:decl-index=0:

	/**
	 * This is the default constructor
	 */
	public DgSortChecked() {
		super();
		initialize();
	}

	public String setDgVisible(boolean b) {
		if (b) {
			baseEncAction
					.userDefinedOrder(new Request(CommonVars.getCurrUser()));
			Vector list = this.getList();
			Vector listS = new Vector();

			Vector checkList = new Vector();
			Vector noCheckList = new Vector();


			for (int i = 0; i < list.size(); i++) {
				BaseCustomsDeclarationCommInfo obj = (BaseCustomsDeclarationCommInfo) list
						.get(i);
				CheckupComplex cc = baseEncAction.findCheckupComplexByCode(
						new Request(CommonVars.getCurrUser()), obj.getComplex()
								.getCode(),obj.getBaseCustomsDeclaration().getImpExpFlag());
				if (cc == null) {
//					errorInfo += "帐册序号：" + obj.getCommSerialNo() + " 的商品编码：\""
//							+ obj.getComplex().getCode() + "\"在商检编码及疫区库中不存在\n";
					noCheckList.add(obj);
				} else if (cc.getComplex().getCcontrol() == null
						|| "".equals(cc.getComplex().getCcontrol())) {
					noCheckList.add(obj);
				} else {
					checkList.add(obj);
				}
			}
//			if (!"".equals(errorInfo)) {
//				super.setVisible(b);
//				JOptionPane.showMessageDialog(DgSortChecked.this, errorInfo,
//						"按商检排序", JOptionPane.ERROR_MESSAGE);
//				DgSortChecked.this.dispose();
//				DgSortChecked.this.dispose();
//				return errorInfo;
//			}

			Collections.sort(checkList,new CompareByCommSerialNo());
			Collections.sort(noCheckList,new CompareByCommSerialNo());
			
			list.removeAllElements();
			list.addAll(checkList);
			list.addAll(noCheckList);
			
			for (int i = 0; i < list.size(); i++) {
				BaseCustomsDeclarationCommInfo obj = (BaseCustomsDeclarationCommInfo) list
						.get(i);
				obj.setSerialNumber(i + 1);
				listS.add(obj);
			}
			if (listS != null && listS.size() > 0) {
				jList.setListData(listS);
				jList.setSelectedIndex(0);
			}
		}
		super.setVisible(b);
		return "";
	}
	
	class CompareByCommSerialNo implements
			Comparator<BaseCustomsDeclarationCommInfo> {
		public int compare(BaseCustomsDeclarationCommInfo o1,
				BaseCustomsDeclarationCommInfo o2) {
			// TODO Auto-generated method stub
			if (o1.getBaseCustomsDeclaration().getImpExpFlag() == 2) {
				Integer ol1 =o1.getComplex().getCcontrol() == null ? 0 : o1.getComplex().getCcontrol().length();
				Integer ol2 =o2.getComplex().getCcontrol() == null ? 0 : o2.getComplex().getCcontrol().length();
				if(ol1 > ol2){
					return -1;
				}
				if(ol1 < ol2 ){
					return 1;
				}
			}
			else {
				if (o1.getCommSerialNo() > o2.getCommSerialNo()) {
					return 1;
				}
				if (o1.getCommSerialNo() < o2.getCommSerialNo()) {
					return -1;
				}
			}
			return 0;
		}

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(433, 370);
		this.setContentPane(getJContentPane());
		this.setTitle("按商检排序");
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
			jPanel1.add(getJButton(), null);
			jPanel1.add(getJButton1(), null);
			jPanel1.add(getJButton2(), null);
			jPanel1.add(getJButton3(), null);
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
				BaseCustomsDeclarationCommInfo obj = ((BaseCustomsDeclarationCommInfo) value);
				s = obj.getSerialNumber() + "   //" + obj.getCommSerialNo()
						+ "   //" + obj.getComplex().getCode() + "   //"
						+ obj.getCommName() + "   //" + obj.getCommSpec();
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
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(32, 93, 48, 22);
			jButton.setText("↑");
			jButton.setForeground(java.awt.Color.blue);
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					move(true);
				}
			});
		}
		return jButton;
	}

	private void move(boolean isup) {
		if (jList.getSelectedValue() == null) {
			JOptionPane.showMessageDialog(DgSortChecked.this, "请选择要移动的项!",
					"提示", 2);
			return;
		}
		int x = jList.getSelectedIndex();
		if ((isup && x == 0) || (!isup && x == list.size() - 1)) {
			return;
		}
		Object selectObj = jList.getSelectedValue();// 当前记录
		BaseCustomsDeclarationCommInfo selectinfo = null;
		BaseCustomsDeclarationCommInfo preinfo = null;
		if (isup) {// 向上移动
			selectinfo = (BaseCustomsDeclarationCommInfo) selectObj;
			selectinfo.setSerialNumber(selectinfo.getSerialNumber() - 1);
			preinfo = (BaseCustomsDeclarationCommInfo) jList.getModel()
					.getElementAt(x - 1);
			preinfo.setSerialNumber(preinfo.getSerialNumber() + 1);
			list.setElementAt(selectinfo, x - 1);
			list.setElementAt(preinfo, x);
			jList.setListData(list);
			jList.setSelectedIndex(x - 1);
		} else { // 向下移动
			selectinfo = (BaseCustomsDeclarationCommInfo) selectObj;
			selectinfo.setSerialNumber(selectinfo.getSerialNumber() + 1);
			preinfo = (BaseCustomsDeclarationCommInfo) jList.getModel()
					.getElementAt(x + 1);
			preinfo.setSerialNumber(preinfo.getSerialNumber() - 1);
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
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(32, 131, 48, 22);
			jButton1.setText("↓");
			jButton1.setForeground(java.awt.Color.blue);
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					move(false);
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
			jButton2.setBounds(24, 258, 63, 29);
			jButton2.setText("确定");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Vector list = DgSortChecked.this.getList();
					baseEncAction.saveCustomsInfo(new Request(CommonVars
							.getCurrUser()), list);
					dispose();
				}
			});
		}
		return jButton2;
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
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(24, 296, 63, 29);
			jButton3.setText("取消");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton3;
	}

	/**
	 * @return Returns the baseEncAction.
	 */
	public BaseEncAction getBaseEncAction() {
		return baseEncAction;
	}

	/**
	 * @param baseEncAction
	 *            The baseEncAction to set.
	 */
	public void setBaseEncAction(BaseEncAction baseEncAction) {
		this.baseEncAction = baseEncAction;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
