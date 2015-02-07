package com.bestway.bcus.client.manualdeclare;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;

public class DgBomExport extends JDialogBase {

	private JPanel jContentPane = null;
	private JToolBar jToolBar = null;
	private JButton jButton = null;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JTextField jTextField = null;
	private JLabel jLabel1 = null;
	private JTextField jTextField1 = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private EmsEdiMergerHead head = null;
	private JTableListModel tableModel = null;
	private ManualDeclareAction manualDecleareAction = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JComboBox jComboBox = null;
	private JLabel jLabel2 = null;

	/**
	 * This is the default constructor
	 */
	public DgBomExport() {
		super();
		manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(783, 410);
		this.setContentPane(getJContentPane());
		this.setTitle("电子帐册归并关系BOM");
		ininCompent();
		initTable(new Vector());
	}

	private void ininCompent() {
		// 初始化版本号
		List list = manualDecleareAction.findAllVersion(new Request(CommonVars
				.getCurrUser()));

		for (int i = 0; i < list.size(); i++) {
			jComboBox.addItem(String.valueOf(list.get(i)));
		}
		this.jComboBox.setSelectedIndex(-1);
	}

	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list
								.add(addColumn("成品序号", "bill10", 80,
										Integer.class));
						list.add(addColumn("成品货号", "bill11", 100));
						list.add(addColumn("版本号", "bill12", 60));
						list.add(addColumn("企业版本号", "bill9", 60));
						list.add(addColumn("成品名称", "bill13", 100));
						list.add(addColumn("料件序号", "bill14", 100));
						list.add(addColumn("料件货号", "bill15", 100));
						list.add(addColumn("料件名称", "bill16", 120));
						list.add(addColumn("单耗", "bill17", 80));
						list.add(addColumn("损耗", "bill18", 80));
						return list;
					}
				});

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
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
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText(" ");
		}
		return jButton;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(241, 4, 37, 18));
			jLabel2.setText("版本号");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(144, 4, 23, 20));
			jLabel1.setText("至");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(7, 5, 65, 21));
			jLabel.setText("成品序号");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getJTextField(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getJTextField1(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJButton2(), null);
			jPanel.add(getJComboBox(), null);
			jPanel.add(jLabel2, null);
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
			jTextField.setBounds(new java.awt.Rectangle(75, 5, 65, 20));
		}
		return jTextField;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(new java.awt.Rectangle(170, 5, 67, 21));
		}
		return jTextField1;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(387, 2, 69, 21));
			jButton1.setText("查询");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTextField.getText().equals("")
							|| jTextField1.getText().equals("")) {
						return;
					}
					new bomexport().start();
				}
			});
		}
		return jButton1;
	}

	class bomexport extends Thread {
		public void run() {
			List list = new Vector();
			try {
				CommonProgress.showProgressDialog(DgBomExport.this);
				CommonProgress.setMessage("系统正在查询资料，请稍后...");
				String Bomversion = null;// 进出类型Code
				if (jComboBox.getSelectedItem() !=null){
					Bomversion=jComboBox.getSelectedItem().toString();
				}
				list = manualDecleareAction.findBomExport(new Request(
						CommonVars.getCurrUser()), head, jTextField.getText(),
						jTextField1.getText(),Bomversion );
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgBomExport.this, "查询失败：！"
						+ e.getMessage(), "提示", 2);
			} finally {
				initTable(list);
				tableModel.sortByColumns(new int[]{1,3,6});
			}
		}
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(462, 2, 71, 21));
			jButton2.setText("退出");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgBomExport.this.dispose();
				}
			});
		}
		return jButton2;
	}

	public void setHead(EmsEdiMergerHead head) {
		this.head = head;
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
		}
		return jTable;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new Rectangle(283, 4, 95, 18));
		}
		return jComboBox;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
