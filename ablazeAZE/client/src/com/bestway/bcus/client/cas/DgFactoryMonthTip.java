package com.bestway.bcus.client.cas;

import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgFactoryMonthTip extends JDialogBase {

	private int			maxMonth		= 12;
	private JPanel		jContentPane	= null;
	private JScrollPane	jScrollPane		= null;
	private JList		jList			= null;
	private JButton		btnOk			= null;
	private JButton		btnCancel		= null;
	private JLabel		jLabel			= null;
	private JButton		btnSelectAll	= null;
	private JButton		btnNotSelectAll	= null;
	private boolean		isOk			= false;
	private boolean		isFrist			= true;

	public DgFactoryMonthTip(int maxMonth) {
		super();
		initialize();
		this.maxMonth = maxMonth;
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(377, 335));
		this.setTitle("选择要生成的月度的结存");
		this.setContentPane(getJContentPane());

	}

	public void setVisible(boolean isFlag) {
		if (isFlag) {
			initUIComponents();
			if (isFrist) {
				AddListener(this);
				isFrist = false;
			}
			isOk = false;
		}
		super.setVisible(isFlag);
	}

	/**
	 * 初始化组件
	 */
	private void initUIComponents() {
		//
		// init jList
		//		
		Vector<Item> vector = new Vector<Item>();
		for (int j = 0; j < this.maxMonth; j++) {
			Item item = new Item(j + 1, true);
			vector.add(item);
		}
		this.jList.setListData(vector);
	}

	/**
	 * 返回选项中的月份
	 * 
	 * @return
	 */
	public List<Integer> returnValue() {
		List<Integer> list = new ArrayList<Integer>();
		for (int j = 0; j < this.jList.getModel().getSize(); j++) {
			Item item = (Item) this.jList.getModel().getElementAt(j);
			list.add(item.getCode());
		}
		return list;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(13, 7, 340, 19));
			jLabel.setText("请选中重新生成的月度");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getBtnSelectAll(), null);
			jContentPane.add(getBtnNotSelectAll(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new java.awt.Rectangle(14, 29, 340, 224));
			jScrollPane.setBorder(javax.swing.BorderFactory
					.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
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
			jList.setCellRenderer(new CheckListRenderer());
			jList.setFixedCellHeight(18);
			jList.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					// if(e.getX()>20);
					int index = jList.locationToIndex(e.getPoint());
					if (index == -1) {
						return;
					}
					Item item = (Item) jList.getModel().getElementAt(index);
					item.setSelected(!item.isSelected());
					Rectangle rect = jList.getCellBounds(index, index);
					jList.repaint(rect);
				}
			});
		}
		return jList;
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
			Item item = (Item) value;
			setSelected(item.isSelected());
			this.setSize(350, 17);
			setText("     " + item.getCode());
			return this;
		}
	}

	public class Item {
		private Integer	code;
		private boolean	isSelected;

		public Item() {
		}

		public Item(Integer code, boolean isSelected) {
			this.code = code;
			this.isSelected = isSelected;
		}

		public void setSelected(boolean b) {
			isSelected = b;
		}

		public boolean isSelected() {
			return isSelected;
		}

		public String toString() {
			return code.toString();
		}

		public Integer getCode() {
			return code;
		}

		public void setCode(Integer code) {
			this.code = code;
		}
	}

	private void selectAll(boolean isSelected) {
		int count = jList.getModel().getSize();
		for (int i = 0; i < count; i++) {
			Item item = (Item) jList.getModel().getElementAt(i);
			item.setSelected(isSelected);
		}
		jList.repaint();
	}

	/**
	 * This method initializes btnOk
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(new java.awt.Rectangle(229, 269, 61, 23));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isOk = true;
					DgFactoryMonthTip.this.dispose();
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
			btnCancel.setBounds(new java.awt.Rectangle(295, 269, 61, 23));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isOk = false;
					DgFactoryMonthTip.this.dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSelectAll() {
		if (btnSelectAll == null) {
			btnSelectAll = new JButton();
			btnSelectAll.setBounds(new java.awt.Rectangle(44, 269, 89, 23));
			btnSelectAll.setText("全部选用");
			btnSelectAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectAll(true);
				}
			});
		}
		return btnSelectAll;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNotSelectAll() {
		if (btnNotSelectAll == null) {
			btnNotSelectAll = new JButton();
			btnNotSelectAll.setBounds(new java.awt.Rectangle(138, 269, 86, 23));
			btnNotSelectAll.setText("全部禁用");
			btnNotSelectAll
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							selectAll(false);
						}
					});
		}
		return btnNotSelectAll;
	}

	public void AddListener(Container container) {
		for (int i = 0; i < container.getComponentCount(); i++) {
			if (!(container.getComponent(i) instanceof Container)) {
				continue;
			}
			container.getComponent(i).addKeyListener(new KeyAdapterExtend());
			AddListener((Container) container.getComponent(i));
		}
	}

	class KeyAdapterExtend extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (KeyEvent.VK_ESCAPE == e.getKeyCode()) {
				DgFactoryMonthTip.this.dispose();
			}
		}
	}

	public boolean isOk() {
		return isOk;
	}
} 
