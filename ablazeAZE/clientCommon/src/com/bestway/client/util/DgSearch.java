/*
 * Created on 2004-10-27
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.util;

import java.awt.Container;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgSearch extends JDialogBase implements FocusListener {

	private JPanel				jContentPane		= null;
	private JPanel				jPanel				= null;
	private JButton				btnSearch			= null;
	private JButton				btnCancel			= null;
	private JComboBox			cbbField			= null;
	private JTextField			tfSearchText		= null;
	private JRadioButton		rbUp				= null;
	private JRadioButton		rbDown				= null;
	private ButtonGroup			buttonGroup			= null;  //  @jve:decl-index=0:
	private javax.swing.JLabel	jLabel1				= null;
	private JTable				tb					= null;
	private JTableListModel		tableModel			= null;
	private boolean				isAddkeyListenered	= true;
	private int[]				matchingRows		= null;
	private JCheckBox			cbAllMatching		= null;
	private JCheckBox			cbMatchCase			= null;
	private JPanel				jPanel1				= null;
	private JCheckBox			cbIsTrue			= null;
	private JCheckBox			cbPrefixMatching	= null;
	private int selectedColumn = 0;
	/**
	 * This method initializes
	 * 
	 */
	public DgSearch() {
		super();
		initialize();
		getButtonGroup();
		this.setResizable(false);
		this.setModal(true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.addFocusListener(this);
	}

	public DgSearch(JDialog jDialog) {
		super(jDialog, true);
		initialize();
		this.setResizable(false);
		getButtonGroup();
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.addFocusListener(this);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("查找");
		this.setContentPane(getJContentPane());
		this.setSize(440, 180);

	}

	public void setVisible(boolean isFlag) {
		if (isFlag) {
			initUIComponents();
			setState();
			if (isAddkeyListenered) {
				AddListener(this);
				isAddkeyListenered = false;
			}
			tb.getTableHeader().addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent event) {
					matchingRows = null;
				}
			});
			matchingRows = null;
		}
		super.setVisible(isFlag);
	}

	/**
	 * @return Returns the tb.
	 */
	public JTable getTb() {
		return tb;
	}

	/**
	 * @param tb
	 *            The tb to set.
	 */
	public void setTb(JTable tb) {
		this.tb = tb;
	}

	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(getRbDown());
			buttonGroup.add(getRbUp());
		}
		return buttonGroup;
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
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getBtnSearch(), null);
			jContentPane.add(getBtnCancel(), null);
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
			javax.swing.JLabel jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(10, 11, 301, 133);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jLabel.setBounds(16, 11, 71, 20);
			jLabel.setText("查找字段");
			jLabel1.setBounds(16, 40, 71, 20);
			jLabel1.setText("查找字符串");
			jPanel.add(jLabel, null);
			jPanel.add(getCbbField(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getTfSearchText(), null);
			jPanel.add(getCbAllMatching(), null);
			jPanel.add(getCbMatchCase(), null);
			jPanel.add(getJPanel1(), null);
			jPanel.add(getCbIsTrue(), null);
			jPanel.add(getCbPrefixMatching(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes btnSearch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setBounds(321, 18, 100, 23);
			btnSearch.setText("查找下一个");
			btnSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					search();
				}
			});
		}
		return btnSearch;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(321, 48, 99, 23);
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgSearch.this.dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes cbbField
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbField() {
		if (cbbField == null) {
			cbbField = new JComboBox();
			cbbField.setBounds(87, 11, 198, 20);
			cbbField.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						JComboBox cbb = (JComboBox) e.getSource();
						if (cbb.getSelectedItem() == null) {
							return;
						}
						if (((Item) cbb.getSelectedItem()).getDataType() == DataType.BOOLEAN) {
							jLabel1.setVisible(false);
							tfSearchText.setVisible(false);
							cbIsTrue.setText(((Item) cbb.getSelectedItem())
									.getName());
							cbIsTrue.setVisible(true);
						} else {
							jLabel1.setVisible(true);
							tfSearchText.setVisible(true);
							cbIsTrue.setVisible(false);
						}
						matchingRows = null;
						setState();
					}
				}
			});
		}
		return cbbField;
	}

	/**
	 * This method initializes tfSearchText
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSearchText() {
		if (tfSearchText == null) {
			tfSearchText = new JTextField();
			try {
				tfSearchText.getDocument().addDocumentListener(
						new DocumentListener() {

							public void insertUpdate(DocumentEvent e) {
								matchingRows = null;
								setState();
							}

							public void removeUpdate(DocumentEvent e) {
								matchingRows = null;
								setState();
							}

							public void changedUpdate(DocumentEvent e) {
								matchingRows = null;
								setState();
							}
						});
			} catch (Exception e) {

			}
			tfSearchText.setBounds(87, 40, 198, 20);
		}
		return tfSearchText;
	}

	/**
	 * This method initializes cbAllMatching
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbAllMatching() {
		if (cbAllMatching == null) {
			cbAllMatching = new JCheckBox();
			cbAllMatching.setBounds(12, 88, 81, 17);
			cbAllMatching.setText("全字匹配");
			cbAllMatching
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							matchingRows = null;
							if (cbAllMatching.isSelected() == true) {
								cbPrefixMatching.setSelected(false);
							}
						}
					});
		}
		return cbAllMatching;
	}

	/**
	 * This method initializes cbMatchCase
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbMatchCase() {
		if (cbMatchCase == null) {
			cbMatchCase = new JCheckBox();
			cbMatchCase.setBounds(12, 110, 99, 15);
			cbMatchCase.setText("区分大小写");
			cbMatchCase.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					matchingRows = null;
				}
			});
		}
		return cbMatchCase;
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
			jPanel1.setBounds(114, 69, 174, 51);
			jPanel1
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
									"方向",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									new java.awt.Font("Dialog",
											java.awt.Font.BOLD, 12),
									new java.awt.Color(51, 51, 51)));
			jPanel1.add(getRbUp(), null);
			jPanel1.add(getRbDown(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes rbUp
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbUp() {
		if (rbUp == null) {
			rbUp = new JRadioButton();
			rbUp.setText("向上");
			rbUp.setBounds(30, 17, 51, 20);
		}
		return rbUp;
	}

	/**
	 * This method initializes rbDown
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbDown() {
		if (rbDown == null) {
			rbDown = new JRadioButton();
			rbDown.setText("向下");
			rbDown.setBounds(91, 17, 51, 20);
		}
		return rbDown;
	}

	/**
	 * This method initializes cbIsTrue
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsTrue() {
		if (cbIsTrue == null) {
			cbIsTrue = new JCheckBox();
			cbIsTrue.setVisible(false);
			cbIsTrue.setBounds(87, 40, 208, 19);
			cbIsTrue.setText("是否");
			cbIsTrue.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					matchingRows = null;
				}
			});
		}
		return cbIsTrue;
	}

	/**
	 * 初始化组件
	 */
	private void initUIComponents() {
		//
		// 初始化 cbbField (查询字段）
		//
		if (this.cbbField.getItemCount() > 0) {
			this.cbbField.removeAllItems();
		}
		tableModel = (JTableListModel) tb.getModel();
		for (int i = 1; i < tableModel.getColumnCount(); i++) {
			this.cbbField.addItem(new Item(this.getDataTypeByColumns(i),
					tableModel.getColumnName(i)));
		}		
		//
		// 只针对没有SerialColumn序号的字段的条件
		//
//		if (tb.getColumnCount() < tableModel.getColumnCount()) {
//			this.cbbField
//					.setSelectedIndex((this.tb.getSelectedColumn()) < 0 ? -1
//							: this.tb.getSelectedColumn());
//		} else {
//			this.cbbField
//					.setSelectedIndex((this.tb.getSelectedColumn() - 1) < 0 ? -1
//							: this.tb.getSelectedColumn() - 1);
//		}
		//
		// 只针对没有SerialColumn序号的字段的条件
		//
		if (tb.getColumnCount() < tableModel.getColumnCount()) {
			this.cbbField.setSelectedIndex(selectedColumn);
			tb.setColumnSelectionInterval(0, tb.getColumnCount()-1);
		} else {
			selectedColumn = selectedColumn-1;
			this.cbbField.setSelectedIndex(selectedColumn==-1?0:selectedColumn);
			tb.setColumnSelectionInterval(1, tb.getColumnCount()-1);
		}
			
		
		//
		// 初始化方向
		//
		this.rbDown.setSelected(true);
		this.cbAllMatching.setSelected(false);
		this.cbMatchCase.setSelected(false);
		this.tfSearchText.setText("");
	}

	/**
	 * 获得数据类型来自列
	 * 
	 * @param col
	 * @return
	 */
	private int getDataTypeByColumns(int col) {
		List list = tableModel.getList();
		if (list == null || list.size() <= 0) {
			return DataType.NULL;
		}
		String sProp = tableModel.getColumnProperty(col);
		int dataType = DataType.NULL;
		try {
			Object cls = PropertyUtils.getPropertyType(list.get(0), sProp);
			if (cls.equals(Integer.class) || cls.equals(Long.class)
					|| cls.equals(Short.class)) {
				dataType = DataType.INTEGER;
			} else if (cls.equals(Double.class) || cls.equals(Float.class)) {
				dataType = DataType.DOUBLE;
			} else if (cls.equals(String.class)) {
				dataType = DataType.STRING;
			} else if (cls.equals(Boolean.class)) {
				dataType = DataType.BOOLEAN;
			} else if (cls.equals(Date.class) || cls.equals(Calendar.class)) {
				dataType = DataType.DATE;
			}
		} catch (Exception ex) {

		}
		return dataType;
	}

	/**
	 * String sProp = ((JTableListColumn) columns.get(col)).getProperty();
	 * Object value = PropertyUtils.getNestedProperty(list.get(index),
	 * fieldDescription);
	 */

	/**
	 * 设置状态
	 * 
	 */
	private void setState() {
		this.btnSearch.setEnabled(this.cbIsTrue.isVisible() ? true
				: !this.tfSearchText.getText().equals(""));
	}

	/**
	 * 查询
	 */
	private void search() {
		if (this.cbbField.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "请选择要搜索的字段!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (matchingRows == null) {
			String searchText = "";
			if (this.cbIsTrue.isVisible() == true) {
				searchText = (new Boolean(this.cbIsTrue.isSelected()))
						.toString();
			} else {
				searchText = this.tfSearchText.getText();
			}
			matchingRows = this.tableModel.getMatchingRows(this.cbbField
					.getSelectedIndex() + 1, searchText, this.cbAllMatching
					.isSelected(), this.cbMatchCase.isSelected(),
					this.cbPrefixMatching.isSelected());
		}
		if (matchingRows.length <= 0) {
			JOptionPane.showMessageDialog(this, "没有匹配的记录!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		int nextRow = 0;
		int selectedRow = 0;
		if (this.rbDown.isSelected() == true) { // 如果向下查找
			nextRow = this.getDownNextMatchIndex();
			if (nextRow >= 0 && nextRow < matchingRows.length) {
				selectedRow = matchingRows[nextRow];
				tb.scrollRectToVisible(tb.getCellRect(selectedRow, 0, false));
				tb.setRowSelectionInterval(selectedRow, selectedRow);
			}
			if (nextRow <= -1) {
				JOptionPane.showMessageDialog(this, "表格向下搜索完毕!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (this.rbUp.isSelected() == true) { // 如果向上查找
			nextRow = this.getUpNextMatchIndex();
			if (nextRow >= 0 && nextRow < matchingRows.length) {
				selectedRow = matchingRows[nextRow];
				tb.scrollRectToVisible(tb.getCellRect(selectedRow, 0, false));
				tb.setRowSelectionInterval(selectedRow, selectedRow);
			}
			if (nextRow <= -1) {
				JOptionPane.showMessageDialog(this, "表格向上搜索完毕!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	/**
	 * 获得向上下一个匹配项的指数
	 * 
	 * @return
	 */
	private int getUpNextMatchIndex() {
		if (matchingRows == null || matchingRows.length <= 0) {
			return -1;
		}
		int index = -1;
		for (int i = matchingRows.length - 1; i >= 0; i--) {
			if (this.tb.getSelectedRow() > matchingRows[i]) {
				index = i;
				break;
			}
		}
		return index;
	}

	/**
	 * 获得向下下一个匹配项的指数
	 * 
	 * @return
	 */
	private int getDownNextMatchIndex() {
		if (matchingRows == null || matchingRows.length <= 0) {
			return -1;
		}
		int index = -1;
		for (int i = 0; i < matchingRows.length; i++) {
			if (this.tb.getSelectedRow() < matchingRows[i]) {
				index = i;
				break;
			}
		}
		return index;
	}

	public class Item {
		private int		dataType;

		private String	name;

		public Item(int dataType, String name) {
			this.dataType = dataType;
			this.name = name;
		}

		public String toString() {
			return name;
		}

		public int getDataType() {
			return dataType;
		}

		public void setCode(int dataType) {
			this.dataType = dataType;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
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
				DgSearch.this.setVisible(false);
			}
		}
	}

	/**
	 * This method initializes cbPrefixMatching
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbPrefixMatching() {
		if (cbPrefixMatching == null) {
			cbPrefixMatching = new JCheckBox();
			cbPrefixMatching.setBounds(12, 68, 81, 17);
			cbPrefixMatching.setText("前匹配");
			cbPrefixMatching
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							matchingRows = null;
							if (cbPrefixMatching.isSelected() == true) {
								cbAllMatching.setSelected(false);
							}
						}
					});
		}
		return cbPrefixMatching;
	}

	public void focusGained(FocusEvent e) {

	}

	public void focusLost(FocusEvent e) {
		this.setVisible(false);
	}

	public int getSelectedColumn() {
		return selectedColumn;
	}

	public void setSelectedColumn(int selectedColumn) {
		this.selectedColumn = selectedColumn;
	}
} 
