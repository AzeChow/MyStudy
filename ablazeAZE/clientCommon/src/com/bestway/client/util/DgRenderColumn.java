/*
 * Created on 2004-10-27
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgRenderColumn extends JDialogBase implements FocusListener {

	private JPanel							jContentPane	= null;
	private JButton							btnSelectAll	= null;
	private JButton							btnCancel		= null;
	private boolean							isFrist			= true;
	private JScrollPane						jScrollPane		= null;
	private JList							jList			= null;
	private JButton							btnNotSelectAll	= null;
	private JButton							btnOk			= null;
	private JLabel							jLabel			= null;
	private Map<String, Boolean>			initMap			= null;
	private Vector<RenderDataColumnItem>	vector			= null;
	private boolean							isOk			= false;

	/**
	 * This method initializes
	 * 
	 */
	public DgRenderColumn() {
		super();
		initialize();
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.addFocusListener(this);
	}

	public DgRenderColumn(JDialog jDialog) {
		super(jDialog, true);
		initialize();
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.addFocusListener(this);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("自定义显示列");
		this.setContentPane(getJContentPane());
		this.setSize(522, 393);

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
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(-3, -2, 522, 44));
			jLabel.setText("   自定义显示列");
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel.setForeground(new java.awt.Color(255, 153, 0));
			jLabel.setOpaque(true);
			jLabel.setBackground(Color.white);
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(getBtnSelectAll(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getBtnNotSelectAll(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(jLabel, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes btnSearch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSelectAll() {
		if (btnSelectAll == null) {
			btnSelectAll = new JButton();
			btnSelectAll.setText("全部选用");
			btnSelectAll.setBounds(new java.awt.Rectangle(179, 324, 83, 26));
			btnSelectAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectAll(true);
				}
			});
		}
		return btnSelectAll;
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
			btnCancel.setBounds(new java.awt.Rectangle(430, 324, 63, 26));
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isOk = false;
					DgRenderColumn.this.dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * 初始化组件
	 */
	private void initUIComponents() {
		//
		// init jList
		//
		this.jList.setListData(vector);
		//
		// 初始化 用于 isChange
		//
		initMap = new HashMap<String, Boolean>();
		for (int j = 0; j < this.jList.getModel().getSize(); j++) {
			RenderDataColumnItem item = (RenderDataColumnItem) this.jList
					.getModel().getElementAt(j);
			initMap.put(item.getCode(), item.isSelected());
		}
	}

	public List<RenderDataColumnItem> returnValue() {
		List<RenderDataColumnItem> list = new ArrayList<RenderDataColumnItem>();
		for (int j = 0; j < this.jList.getModel().getSize(); j++) {
			RenderDataColumnItem item = (RenderDataColumnItem) this.jList
					.getModel().getElementAt(j);
			list.add(item);
		}
		return list;
	}

	/**
	 * 是否修改过
	 * 
	 * @return
	 */
	public boolean isChange() {
		for (int j = 0; j < this.jList.getModel().getSize(); j++) {
			RenderDataColumnItem item = (RenderDataColumnItem) this.jList
					.getModel().getElementAt(j);

			boolean oldIsSelected = initMap.get(item.getCode());
			if (item.isSelected() != oldIsSelected) {
				return true;
			}
		}
		return false;
	}

	public boolean hasSelected() {
		boolean hasSelected = false;
		for (int j = 0; j < jList.getModel().getSize(); j++) {
			RenderDataColumnItem item = (RenderDataColumnItem) jList.getModel()
					.getElementAt(j);
			if (item.isSelected() == true) {
				hasSelected = true;
				break;
			}
		}
		return hasSelected;
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
			RenderDataColumnItem item = (RenderDataColumnItem) value;
			setSelected(item.isSelected());
			this.setSize(350, 17);
			setText("     " + item.getName());
			return this;
		}
	}

	private void selectAll(boolean isSelected) {
		int count = jList.getModel().getSize();
		for (int i = 0; i < count; i++) {
			RenderDataColumnItem item = (RenderDataColumnItem) jList.getModel()
					.getElementAt(i);
			item.setSelected(isSelected);
		}
		jList.repaint();
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
				DgRenderColumn.this.dispose();
			}
		}
	}

	public void focusGained(FocusEvent e) {

	}

	public void focusLost(FocusEvent e) {
		this.setVisible(false);
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new java.awt.Rectangle(0, 43, 515, 271));
			jScrollPane
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
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
			jList.setBackground(new java.awt.Color(238, 238, 238));
			jList.setFixedCellHeight(18);
			jList.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					// if(e.getX()>20);
					int index = jList.locationToIndex(e.getPoint());
					if (index == -1) {
						return;
					}
					RenderDataColumnItem item = (RenderDataColumnItem) jList
							.getModel().getElementAt(index);
					item.setSelected(!item.isSelected());
					Rectangle rect = jList.getCellBounds(index, index);
					jList.repaint(rect);
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
	private JButton getBtnNotSelectAll() {
		if (btnNotSelectAll == null) {
			btnNotSelectAll = new JButton();
			btnNotSelectAll.setBounds(new java.awt.Rectangle(269, 324, 83, 26));
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

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(new java.awt.Rectangle(359, 324, 63, 26));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					boolean hasSelected = hasSelected();
					if (hasSelected == false) {
						JOptionPane.showMessageDialog(DgRenderColumn.this,
								"不允许列,全部不选!!", "提示",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					isOk = isChange();
					DgRenderColumn.this.dispose();
				}
			});
		}
		return btnOk;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setVector(Vector<RenderDataColumnItem> vector) {
		this.vector = new Vector<RenderDataColumnItem>();	
		for(RenderDataColumnItem item : vector){
			RenderDataColumnItem newItem = new RenderDataColumnItem();
			newItem.setCode(item.getCode());
			newItem.setName(item.getName());
			newItem.setSelected(item.isSelected());
			this.vector.add(newItem);
		}
	}
}
