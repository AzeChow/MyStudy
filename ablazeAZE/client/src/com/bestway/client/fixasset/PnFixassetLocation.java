package com.bestway.client.fixasset;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.fixasset.action.FixAssetAction;
import com.bestway.fixasset.entity.FixassetLocation;
import com.bestway.fixtureonorder.action.CheckFixAuthorityAction;

public class PnFixassetLocation extends JPanel {

	private static final long serialVersionUID = 1L;

	private JSplitPane jSplitPane = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JButton btnAdd = null;

	private JButton btnDelete = null;

	private JButton btnSave = null;

	private JButton btnCancel = null;

	private JTextField tfCode = null;

	private JTextField tfName = null;

	private JTableListModel tableModel;

	private FixAssetAction fixAssetAction;

	private JToolBar jToolBar = null;

	private JButton btnEdit = null;

	private int dataState = DataState.BROWSE;

	private CheckFixAuthorityAction checkFixAuthorityAction = null;

	/**
	 * This is the default constructor
	 */
	public PnFixassetLocation() {
		super();
		initialize();
		fixAssetAction = (FixAssetAction) CommonVars.getApplicationContext()
				.getBean("fixAssetAction");
		checkFixAuthorityAction = (CheckFixAuthorityAction) CommonVars
				.getApplicationContext().getBean("checkFixAuthorityAction");
		List list = fixAssetAction.findFixassetLocation(new Request(CommonVars
				.getCurrUser()));
		initTable(list);
		setState();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(437, 200);
		this.setLayout(new BorderLayout());
		this.add(getJSplitPane(), BorderLayout.CENTER);
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(200);
			jSplitPane.setDividerSize(2);
			jSplitPane.setLeftComponent(getJScrollPane());
			jSplitPane.setRightComponent(getJPanel());
		}
		return jSplitPane;
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
			jTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) jTable
									.getModel();
							if (tableModel == null) {
								return;
							}

							try {
								FixassetLocation fixassetLocation = (FixassetLocation) tableModel
										.getCurrentRow();
								showData(fixassetLocation);
							} catch (Exception cx) {

							}
						}
					});
		}
		return jTable;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(35, 103, 60, 19));
			jLabel1.setText("位置名称");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(35, 65, 60, 16));
			jLabel.setText("位置代码");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(getTfCode(), null);
			jPanel.add(getTfName(), null);
			jPanel.add(getJToolBar(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction.checkAddFixassetLocation(new Request(CommonVars.getCurrUser()));
					tfCode.setText("");
					tfName.setText("");
					dataState = DataState.ADD;
					setState();
				}
			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction.checkDeleteFixassetLocation(new Request(CommonVars.getCurrUser()));
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(PnFixassetLocation.this,
								"请选择要删除的位置", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					FixassetLocation location = (FixassetLocation) tableModel
							.getCurrentRow();
					fixAssetAction.deleteFixassetLocation(new Request(
							CommonVars.getCurrUser()), location);
					tableModel.deleteRow(location);
				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction.checkEditFixassetLocation(new Request(CommonVars.getCurrUser()));
					FixassetLocation location = null;
					if (dataState == DataState.ADD) {
						location = new FixassetLocation();
						fillData(location);
						location = fixAssetAction
								.saveFixassetLocation(new Request(CommonVars
										.getCurrUser()), location);
						tableModel.addRow(location);
					} else if (dataState == DataState.EDIT) {
						location = (FixassetLocation) tableModel
								.getCurrentRow();
						fillData(location);
						location = fixAssetAction
								.saveFixassetLocation(new Request(CommonVars
										.getCurrUser()), location);
						tableModel.updateRow(location);
					}
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes jButton21
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.BROWSE;
					FixassetLocation location = (FixassetLocation) tableModel
							.getCurrentRow();
					showData(location);
					setState();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCode() {
		if (tfCode == null) {
			tfCode = new JTextField();
			tfCode.setBounds(new Rectangle(95, 62, 122, 23));
		}
		return tfCode;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(new Rectangle(95, 101, 122, 24));
		}
		return tfName;
	}

	private void initTable(List list) {
		JTableListModel.dataBind(jTable, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<Object> list = (List<Object>) (new Vector());
				list.add(addColumn("编号", "code", 100));
				list.add(addColumn("名称 ", "name", 100));
				return list;
			}
		});
		tableModel = (JTableListModel) jTable.getModel();

	}

	public FixassetLocation getFixassetLocation() {
		if (tableModel != null) {
			return (FixassetLocation) tableModel.getCurrentRow();
		}
		return null;
	}

	private void showData(FixassetLocation location) {
		if (location == null) {
			tfCode.setText("");
			tfName.setText("");
		} else {
			this.tfCode.setText(location.getCode());
			this.tfName.setText(location.getName());
		}
	}

	private void fillData(FixassetLocation location) {
		if (location == null) {
			return;
		}
		location.setCode(tfCode.getText().trim());
		location.setName(tfName.getText().trim());
	}

	private void setState() {
		this.tfCode.setEnabled(dataState != DataState.BROWSE);
		this.tfName.setEnabled(dataState != DataState.BROWSE);
		this.btnAdd.setEnabled(dataState == DataState.BROWSE);
		this.btnEdit.setEnabled(dataState == DataState.BROWSE);
		this.btnSave.setEnabled(dataState != DataState.BROWSE);
		this.btnCancel.setEnabled(dataState != DataState.BROWSE);
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setBounds(new Rectangle(1, 1, 193, 35));
			jToolBar.setFloatable(false);
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnCancel());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction.checkEditFixassetLocation(new Request(CommonVars.getCurrUser()));
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(PnFixassetLocation.this,
								"请选择要修改的位置", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnEdit;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
