/*
 * Created on 2004-10-27
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bestway.client.common.CommonVariables;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgJarDataStore extends JDialogBase implements FocusListener {

	private JPanel					jContentPane			= null;
	private JButton					btnCancel				= null;
	private boolean					isFrist					= true;
	private JScrollPane				jScrollPane				= null;
	private JList					jList					= null;
	private JButton					btnOk					= null;
	private JLabel					jLabel					= null;
	private boolean					isOk					= false;
	private JLabel					jLabel1					= null;
	private JLabel					jLabel2					= null;
	private JTextField				tfFileName				= null;
	private JComboBox				cbbFileType				= null;
	private String					keyString				= null;
	public static int				SAVE_DIALOG				= 0;
	public static int				OPEN_DIALOG				= 1;
	private int						dialogState				= 0;
	private JarDataStoreInterface	jarDataStoreInterface	= CommonVariables
																	.getJarDataStoreInterface();
	private JButton					btnDelete				= null;

	/**
	 * This method initializes
	 * 
	 */
	public DgJarDataStore() {
		super();
		initialize();
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.addFocusListener(this);
	}

	public DgJarDataStore(JDialog jDialog) {
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
		this.setTitle("数据存储");
		this.setContentPane(getJContentPane());
		this.setSize(504, 351);

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
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(13, 248, 67, 23));
			jLabel2.setText("文件类型:");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(13, 224, 71, 23));
			jLabel1.setText("文件名:");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(13, -2, 468, 44));
			jLabel.setText(" 保存数据");
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel.setForeground(new java.awt.Color(255, 153, 0));
			jLabel.setOpaque(true);
			// jLabel.setBackground(Color.white);
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getTfFileName(), null);
			jContentPane.add(getCbbFileType(), null);
			jContentPane.add(getBtnDelete(), null);
		}
		return jContentPane;
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
			btnCancel.setBounds(new java.awt.Rectangle(325, 284, 63, 26));
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isOk = false;
					DgJarDataStore.this.dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * 初始化组件
	 */
	private void initUIComponents() {
		if (dialogState == SAVE_DIALOG) {
			this.jLabel.setText("保存数据");
			this.btnOk.setText("保存");

		} else if (dialogState == OPEN_DIALOG) {
			this.jLabel.setText("打开文件");
			this.btnOk.setText("打开");
			this.tfFileName.setEditable(false);
		}
		cbbFileType.setSelectedItem(null);
		cbbFileType.setSelectedIndex(0);
	}

	public JarItem returnValue() {
		if (dialogState == SAVE_DIALOG) {
			JarItem selectedItem = new JarItem();
			selectedItem.setFileName(this.tfFileName.getText());
			return selectedItem;

		} else {
			JarItem selectedItem = (JarItem) (jList.getSelectedValue());
			return selectedItem;
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
				DgJarDataStore.this.dispose();
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
			jScrollPane.setBounds(new java.awt.Rectangle(13, 43, 469, 172));
			jScrollPane
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jScrollPane
					.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			jScrollPane
					.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
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
			jList.setCellRenderer(new MultiColumnTextListCellRenderer());
			// jList.setFixedCellHeight(18);
			jList
					.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

			jList.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() >= 2 && dialogState == OPEN_DIALOG) {
						int index = jList.locationToIndex(e.getPoint());
						if (index == -1) {
							return;
						}
						if (tfFileName.getText().trim().equals("")) {
							isOk = false;
						} else {
							isOk = true;
						}
						DgJarDataStore.this.dispose();
					}
				}
			});

			jList.addListSelectionListener(new ListSelectionListener() {

				public void valueChanged(ListSelectionEvent e) {
					if (e.getValueIsAdjusting() == true) {
						return;
					}
					JarItem selectedItem = (JarItem) (((JList) e.getSource())
							.getSelectedValue());
					if (selectedItem != null) {
						tfFileName.setText(selectedItem.getFileName());
					} else {
						tfFileName.setText("");
					}
				}

			});
		}
		return jList;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(new java.awt.Rectangle(257, 284, 63, 26));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tfFileName.getText().trim().equals("")) {
						isOk = false;
					} else {
						isOk = true;
					}
					DgJarDataStore.this.dispose();
				}
			});
		}
		return btnOk;
	}

	public boolean isOk() {
		return isOk;
	}

	/**
	 * This method initializes tfFileName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFileName() {
		if (tfFileName == null) {
			tfFileName = new JTextField();
			tfFileName.setBounds(new java.awt.Rectangle(88, 224, 394, 23));
		}
		return tfFileName;
	}

	/**
	 * This method initializes cbbFileType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbFileType() {
		if (cbbFileType == null) {
			cbbFileType = new JComboBox();
			cbbFileType.addItem("*.report");
			cbbFileType.addItem("所有 *.report");
			cbbFileType.setSelectedItem(null);
			cbbFileType.setBounds(new java.awt.Rectangle(88, 249, 394, 23));
			cbbFileType.addItemListener(new ItemListener() {

				public void itemStateChanged(ItemEvent e) {
					if (cbbFileType.getSelectedItem() == null) {
						return;
					}
					if (e.getStateChange() == ItemEvent.SELECTED) {
						//
						// init jList
						//
						Vector<JarItem> vector = new Vector<JarItem>();
						List<JarItem> list = new ArrayList<JarItem>();
						if (cbbFileType.getSelectedIndex() == 0) {
							list = jarDataStoreInterface
									.getJarFileName(keyString);
						} else {
							list = jarDataStoreInterface.getJarFileName();
						}
						vector.addAll(list);
						jList.setListData(vector);
					}
				}

			});
		}
		return cbbFileType;
	}

	public void setKeyString(String keyString) {
		this.keyString = keyString;
	}

	public int getDialogState() {
		return dialogState;
	}

	public void setDialogState(int dialogState) {
		this.dialogState = dialogState;
	}

	/**
	 * This method initializes btnDelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setBounds(new java.awt.Rectangle(394, 284, 88, 26));
			btnDelete.setText("删除文件");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JarItem selectedItem = (JarItem) (jList.getSelectedValue());
					if (selectedItem == null) {
						JOptionPane.showMessageDialog(DgJarDataStore.this,
								"请选择要删除的文件!!", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (JOptionPane.showConfirmDialog(DgJarDataStore.this,
							"确定要删除的文件", "警告!!!", JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
						return;
					}
					boolean isSucceed = jarDataStoreInterface.deleteFile(
							selectedItem.getFileName(), selectedItem
									.getHashCode());
					if (isSucceed) {
						int selectedIndex = jList.getSelectedIndex();
						Vector vector = new Vector();
						ListModel listModel = jList.getModel();
						for (int i = 0; i < listModel.getSize(); i++) {
							if (selectedIndex != i) {
								vector.add(listModel.getElementAt(i));
							}
						}
						jList.setListData(vector);
					}
				}
			});
		}
		return btnDelete;
	}

	/**
	 * 多列文本数据呈现
	 */
	class MultiColumnTextListCellRenderer extends DefaultListCellRenderer {
		private JLabel	lbCode	= new JLabel();

		private JLabel	lbName	= new JLabel();

		public MultiColumnTextListCellRenderer() {
			lbCode.setBounds(0, 0, 180, 20);
			this.add(lbCode);
			lbName.setBounds(180, 0, 250, 20);
			this.add(lbName);
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {

			super.getListCellRendererComponent(list, value, index, isSelected,
					cellHasFocus);
			if (value != null) {
				if (value instanceof JarItem) {
					JarItem obj = (JarItem) value;
					this.lbCode.setText(obj.getFileName());
					this.lbName.setText(obj.getModifiyTime());
				}
			} else {
				this.lbCode.setText("");
				this.lbName.setText("");
			}
			this.setText("                     ");
			return this;
		}
	}

}
