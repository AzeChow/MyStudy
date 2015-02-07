/*
 * Created on 2004-8-10
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.custom.common;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.custombase.entity.parametercode.LicenseDocu;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author bsway
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgAttachedBill extends JDialogBase {

	private JPanel jContentPane = null;
	private JList jList = null;
	private JScrollPane jScrollPane = null;
	private JButton btnOk = null;
	private JButton btnCancel = null;

	private boolean ok = false;
	private String attachedBill = "";

	/**
	 * This method initializes
	 * 
	 */
	public DgAttachedBill() {
		super();
		initialize();
		this.initComponents();
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	}

	public void setVisible(boolean isFalg) {
		if (isFalg) {
			this.showData(this.getAttachedBill());
		}
		super.setVisible(isFalg);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("选择当前用到的监管证件");
		this.setContentPane(getJContentPane());
		this.setSize(355, 310);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
		}
		return jContentPane;
	}

	/**
	 * @return Returns the ok.
	 */
	public boolean isOk() {
		return ok;
	}

	/**
	 * @param ok
	 *            The ok to set.
	 */
	public void setOk(boolean ok) {
		this.ok = ok;
	}

	/**
	 * @param attachedBill
	 *            The attachedBill to set.
	 */
	public void setAttachedBill(String attachedBill) {
		this.attachedBill = attachedBill;
	}

	public String getAttachedBill() {
		return this.attachedBill;
	}

	/**
	 * This method initializes jList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getJList() {
		if (jList == null) {
			jList = new JList();
			jList.setCellRenderer(new CheckListRenderer());
			jList.setFixedCellHeight(18);
			jList.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					int index = jList.locationToIndex(e.getPoint());
					if (index == -1) {
						return;
					}
					CheckableItem item = (CheckableItem) jList.getModel()
							.getElementAt(index);
					item.setSelected(!item.isSelected());
					Rectangle rect = jList.getCellBounds(index, index);
					jList.repaint(rect);
				}
			});
		}
		return jList;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(0, 0, 272, 281);
			jScrollPane.setViewportView(getJList());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes btnOk
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setText("确定");
			btnOk.setBounds(279, 14, 60, 26);
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setOk(true);
					DgAttachedBill.this.dispose();
				}
			});
		}
		return btnOk;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.setBounds(279, 50, 60, 26);
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setOk(false);
					DgAttachedBill.this.dispose();
				}
			});
		}
		return btnCancel;
	}

	private void initComponents() {
		this.fillJList();
	}

	private void fillJList() {
		Vector vector = new Vector();
		List list = CustomBaseList.getInstance().getLicensedocus();
		for (int i = 0; i < list.size(); i++) {
			LicenseDocu obj = (LicenseDocu) list.get(i);
			CheckableItem item = new CheckableItem(obj.getCode() == null ? ""
					: obj.getCode(), obj.getName() == null ? "" : obj.getName());
			vector.add(item);
		}
		this.jList.setListData(vector);
	}

	private void showData(String attachBill) {
		if (attachBill == null || attachBill.equals("")) {
			return;
		}
		char[] chars = attachBill.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			for (int j = 0; j < this.jList.getModel().getSize(); j++) {
				CheckableItem item = (CheckableItem) this.jList.getModel()
						.getElementAt(j);
				if (item.isSelected) {
					continue;
				}
				if (String.valueOf(chars[i]).equals(
						item.getCode().trim())) {
					item.setSelected(true);
				}
			}
		}
	}

	public String returnValue() {
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < this.jList.getModel().getSize(); j++) {
			CheckableItem item = (CheckableItem) this.jList.getModel()
					.getElementAt(j);
			if (item.isSelected) {
				sb.append(item.getCode().trim());
			}
		}
		return sb.toString();
	}

	class CheckableItem {
		private String code;
		private String name;
		private boolean isSelected;

		public CheckableItem(String str, String name) {
			this.code = str;
			this.name = name;
			isSelected = false;
		}

		public void setSelected(boolean b) {
			isSelected = b;
		}

		public boolean isSelected() {
			return isSelected;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}

	/**
	 * 设置JList呈现类
	 */

	class CheckListRenderer extends JCheckBox implements ListCellRenderer {

		public CheckListRenderer() {
			setBackground(UIManager.getColor("List.textBackground"));
			setForeground(UIManager.getColor("List.textForeground"));
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean hasFocus) {
			setEnabled(list.isEnabled());
			CheckableItem item = (CheckableItem) value;
			setSelected(item.isSelected());
			this.setSize(350, 17);
			setText("  " + item.getCode() + "    " + item.getName());
			return this;
		}
	}

} // @jve:decl-index=0:visual-constraint="10,10"
