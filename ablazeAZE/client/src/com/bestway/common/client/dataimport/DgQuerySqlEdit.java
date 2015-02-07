/*
 * Created on 2004-11-12
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataimport;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.DBQuerySql;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.ui.editor.SQLTextPane;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 *
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgQuerySqlEdit extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private DataImportAction dataImportAction = null;
	private boolean isAdd = true;
	private DBQuerySql dbView = null; // 视图参数设置
	private JTableListModel tableModel = null;
	private JToolBar jToolBar = null;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JTextField jTextField = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JButton jButton3 = null;
	private SQLTextPane SQLTextPane = null;

	private JPanel jPanel1 = null;

	/**
	 * This is the default constructor
	 */
	public DgQuerySqlEdit() {
		super();
		dataImportAction = (DataImportAction) CommonVars
				.getApplicationContext().getBean("dataImportAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {

		setTitle("新增视图参数设置");

		setSize(671, 344);

		setContentPane(getJContentPane());

		addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {

				if (isAdd == false) // 编辑
				{
					dbView = (DBQuerySql) tableModel.getCurrentRow();
					fillWindow();
				} else {

				}
			}
		});

	}

	private void fillWindow() {
		this.jTextField.setText(dbView.getName());
		this.SQLTextPane.setText(dbView.getSqlStr());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {

		if (jContentPane == null) {

			jContentPane = new javax.swing.JPanel();

			jContentPane.setLayout(new BorderLayout());

			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);

			jContentPane.add(getJPanel1(), java.awt.BorderLayout.CENTER);

		}
		return jContentPane;
	}

	private void fillData(DBQuerySql db) {
		db.setName(this.jTextField.getText());
		db.setSqlStr(this.SQLTextPane.getText());
	}

	private boolean checkisNull() {
		if (this.jTextField.getText().equals("")) {
			JOptionPane.showMessageDialog(DgQuerySqlEdit.this, "名称不可为空！",
					"提示！", 2);
			return false;
		}
		return true;
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * @return Returns the isAdd.
	 */
	public boolean isAdd() {
		return isAdd;
	}

	/**
	 * @param isAdd
	 *            The isAdd to set.
	 */
	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJButton());
			jToolBar.add(getJPanel());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel.setText("语句名称");
			jLabel.setBounds(11, 6, 48, 18);
			jPanel.add(jLabel, null);
			jPanel.add(getJTextField(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJButton3(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(64, 4, 159, 22);
		}
		return jTextField;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("最大化");
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("保存");
			jButton1.setBounds(231, 2, 58, 25);
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!checkisNull()) {
						return;
					}
					saveData();
					dispose();

				}
			});

		}
		return jButton1;
	}

	private void saveData() {
		if (DgQuerySqlEdit.this.isAdd) {
			DBQuerySql db = new DBQuerySql();
			fillData(db);
			db = dataImportAction.saveDBQuerySql(
					new Request(CommonVars.getCurrUser()), db);
			dbView = db;
			tableModel.addRow(db);
		} else {
			fillData(dbView);
			dbView = dataImportAction.saveDBQuerySql(
					new Request(CommonVars.getCurrUser()), dbView);
			tableModel.updateRow(dbView);
		}
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("取消");
			jButton3.setBounds(295, 2, 58, 25);
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton3;
	}

	/**
	 * This method initializes SQLTextPane
	 * 
	 * @return com.bestway.ui.editor.SQLTextPane
	 */
	private SQLTextPane getSQLTextPane() {
		if (SQLTextPane == null) {
			SQLTextPane = new SQLTextPane();
		}
		return SQLTextPane;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getSQLTextPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
