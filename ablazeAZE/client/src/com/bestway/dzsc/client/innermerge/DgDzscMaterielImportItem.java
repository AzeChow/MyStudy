/*
 * Created on 2004-8-10
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.innermerge;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

import com.bestway.common.MaterielType;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author bsway
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDzscMaterielImportItem extends JDialogBase {

	private JPanel jContentPane = null;

	private JList jList = null;

	private JScrollPane jScrollPane = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private boolean isOk = false;

	private JButton btnSelectAll = null;

	private JButton btnNotSelectAll = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgDzscMaterielImportItem() {
		super();
		initialize();
		this.initComponents();
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	}

	public void setVisible(boolean isFalg) {
		if (isFalg) {

		}
		super.setVisible(isFalg);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setResizable(false);
		this.setTitle("选择要导入物料项目");
		this.setContentPane(getJContentPane());
		this.setSize(265, 278);
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
			jContentPane.add(getBtnSelectAll(), null);
			jContentPane.add(getBtnNotSelectAll(), null);
		}
		return jContentPane;
	}

	/**
	 * @return Returns the ok.
	 */
	public boolean isOk() {
		return isOk;
	}

	/**
	 * @param ok
	 *            The ok to set.
	 */
	public void setOk(boolean ok) {
		this.isOk = ok;
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
			jScrollPane.setBounds(0, 0, 172, 253);
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
			btnOk.setBounds(186, 63, 58, 25);
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setOk(true);
					DgDzscMaterielImportItem.this.dispose();
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
			btnCancel.setBounds(186, 92, 58, 25);
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setOk(false);
					DgDzscMaterielImportItem.this.dispose();
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
		CheckableItem checkableItem = new CheckableItem(
				MaterielType.FINISHED_PRODUCT, "成品");
		vector.add(checkableItem);
		// checkableItem = new CheckableItem(MaterielType.SEMI_FINISHED_PRODUCT,
		// "半成品");
		// vector.add(checkableItem);
		checkableItem = new CheckableItem(MaterielType.MATERIEL, "物料");
		vector.add(checkableItem);
		checkableItem = new CheckableItem(MaterielType.MACHINE, "设备");
		vector.add(checkableItem);
		// checkableItem = new CheckableItem(MaterielType.REMAIN_MATERIEL,
		// "边角料");
		// vector.add(checkableItem);
		// checkableItem = new CheckableItem(MaterielType.BAD_PRODUCT, "残次品");
		// vector.add(checkableItem);
		this.jList.setListData(vector);
	}

	public List returnValue() {
		List list = new ArrayList();
		for (int j = 0; j < this.jList.getModel().getSize(); j++) {
			CheckableItem item = (CheckableItem) this.jList.getModel()
					.getElementAt(j);
			if (item.isSelected) {
				list.add(item.getCode().trim());
			}
		}
		return list;
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
			// setText(" " + item.getCode() + " " + item.getName());
			setText("     " + item.getName());
			return this;
		}
	}

	/**
	 * This method initializes btnSelectAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSelectAll() {
		if (btnSelectAll == null) {
			btnSelectAll = new JButton();
			btnSelectAll.setBounds(186, 4, 58, 25);
			btnSelectAll.setText("全选");
			btnSelectAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectAll(true);
				}
			});
		}
		return btnSelectAll;
	}

	private void selectAll(boolean isSelected) {
		int count = jList.getModel().getSize();
		for (int i = 0; i < count; i++) {
			CheckableItem item = (CheckableItem) jList.getModel().getElementAt(
					i);
			item.setSelected(isSelected);
		}
		jList.repaint();
	}

	/**
	 * This method initializes btnNotSelectAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNotSelectAll() {
		if (btnNotSelectAll == null) {
			btnNotSelectAll = new JButton();
			btnNotSelectAll.setBounds(186, 34, 58, 25);
			btnNotSelectAll.setText("全否");
			btnNotSelectAll
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							selectAll(false);
						}
					});
		}
		return btnNotSelectAll;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
