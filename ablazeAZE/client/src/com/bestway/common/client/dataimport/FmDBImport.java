/*
 * Created on 2004-9-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataimport;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.license.LicenseClient;
import com.bestway.bcus.client.system.FmParameterSet;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.ThreadList;
import com.bestway.common.Request;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.common.tools.entity.TempNodeItem;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class FmDBImport extends JInternalFrameBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;

	private JPanel jPanel1 = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	private JRadioButton jRadioButton4 = null;

	private JRadioButton jRadioButton5 = null;

	private JRadioButton jRadioButton2 = null;

	private JRadioButton jRadioButton8 = null;

	private JPanel jPanel2 = null;

	private ButtonGroup group = new ButtonGroup();

	private FmParameterSet fmParameterSet;

	private JPanel jPanel = null;

	private DataImportAction dataImportAction = null;

	private JCheckBox jCheckBox1 = null;

	private JButton btnBackup = null;

	private JLabel jLabel5 = null;

	private JTextField tfFilePath = null;

	private JButton btnRever = null;

	private JRadioButton jRadioButton3 = null;

	private ToolsAction toolsAction = null;

	private Map<TempNodeItem, List<TempNodeItem>> dataDictMap = null;

	private Hashtable hs = new Hashtable();

	private JButton jButton = null;

	/**
	 * This is the default constructor
	 */
	public FmDBImport() {
		super();
		dataImportAction = (DataImportAction) CommonVars
				.getApplicationContext().getBean("dataImportAction");
		// dataImportAction.checkDBImportAuthority(new Request(CommonVars
		// .getCurrUser()));
		toolsAction = (ToolsAction) CommonVars.getApplicationContext().getBean(
				"toolsAction");
		initialize();
		CommonVars.setIstitleRow(new Boolean(true));
		dataImportAction.changeIsTitle(new Request(CommonVars.getCurrUser()),
				new Boolean(true));
		List list = dataImportAction.findThreadList(
				new Request(CommonVars.getCurrUser()), "db");
		if (list != null && !list.isEmpty()) {
			this.jCheckBox1.setSelected(true);
		} else {
			this.jCheckBox1.setSelected(false);
		}
		dataDictMap = toolsAction.getTableColumnMap();
		List<TempNodeItem> listitem = new ArrayList<TempNodeItem>();
		listitem.addAll(dataDictMap.keySet());
		Collections.sort(listitem);
		for (int i = 0; i < listitem.size(); i++) {
			TempNodeItem obj = (TempNodeItem) listitem.get(i);
			String className = forClassName(obj.getClassName());
			hs.put(className, obj.getClassName());
		}

	}

	private String forClassName(String value) {
		if (value != null) {
			String entityName = value.toString();
			int lastIndex = entityName.lastIndexOf(".");
			if (lastIndex > -1) {
				return entityName.substring(lastIndex + 1);
			}
		}
		return value;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("DB导入管理");
		this.setSize(925, 578);
		this.setContentPane(getJContentPane());
		this.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {

			public void internalFrameOpened(
					javax.swing.event.InternalFrameEvent e) {
				if (!LicenseClient.getInstance(
						CommonVars.getCurrUser().getCompany().getName())
						.checkDBImportPermisson()) {
					JOptionPane.showMessageDialog(FmDBImport.this,
							"没有使用DB导入功能的权限，如果需要请联系百思维！");
					doDefaultCloseAction();
				}
			}
		});
		group.add(jRadioButton);
		group.add(jRadioButton1);
		group.add(jRadioButton4);
		group.add(jRadioButton5);
		group.add(jRadioButton8);
		group.add(jRadioButton2);
		group.add(jRadioButton3);
	}

	/**
	 * This method initializes pnContent
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.setFont(new java.awt.Font("Dialog",
					java.awt.Font.PLAIN, 18));
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jPanel1
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJPanel2(), java.awt.BorderLayout.CENTER);
			jPanel1.add(getJPanel(), java.awt.BorderLayout.NORTH);
		}
		return jPanel1;
	}

	/**
	 * 
	 * This method initializes jRadioButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setText("数据源操作");
			jRadioButton.setBounds(258, 197, 123, 26);
			jRadioButton.setBackground(java.awt.Color.white);
			jRadioButton.addActionListener(new RadioActionListner());
		}
		return jRadioButton;
	}

	/**
	 * 
	 * This method initializes jRadioButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setText("视图操作");
			jRadioButton1.setBounds(303, 268, 113, 21);
			jRadioButton1.setBackground(java.awt.Color.white);
			jRadioButton1.addActionListener(new RadioActionListner());
		}
		return jRadioButton1;
	}

	/**
	 * 
	 * This method initializes jRadioButton4
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getJRadioButton4() {
		if (jRadioButton4 == null) {
			jRadioButton4 = new JRadioButton();
			jRadioButton4.setText("域定义管理");
			jRadioButton4.setBounds(324, 299, 107, 21);
			jRadioButton4.setBackground(java.awt.Color.white);
			jRadioButton4.addActionListener(new RadioActionListner());
		}
		return jRadioButton4;
	}

	/**
	 * 
	 * This method initializes jRadioButton5
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getJRadioButton5() {
		if (jRadioButton5 == null) {
			jRadioButton5 = new JRadioButton();
			jRadioButton5.setText("任务计划设置");
			jRadioButton5.setBounds(350, 332, 110, 21);
			jRadioButton5.setBackground(java.awt.Color.white);
			jRadioButton5.addActionListener(new RadioActionListner());
		}
		return jRadioButton5;
	}

	/**
	 * 
	 * This method initializes jPanel2
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel5 = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBorder(javax.swing.BorderFactory.createCompoundBorder(
					null, null));
			jPanel2.setBackground(java.awt.Color.white);
			jLabel.setBounds(-2, 3, 309, 97);
			jLabel.setText("");
			jLabel.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/dbimport.jpg")));
			jLabel1.setBounds(33, 87, 538, 313);
			jLabel1.setText("");
			jLabel1.setIcon(new ImageIcon(
					getClass()
							.getResource(
									"/com/bestway/bcus/client/resources/images/import_middle.jpg")));
			jLabel5.setBounds(312, 9, 65, 22);
			jLabel5.setText("备份路径");
			jPanel2.add(getJRadioButton(), null);
			jPanel2.add(getJRadioButton1(), null);
			jPanel2.add(getJRadioButton4(), null);
			jPanel2.add(getJRadioButton5(), null);
			jPanel2.add(getJRadioButton8(), null);
			jPanel2.add(getJRadioButton2(), null);
			jPanel2.add(getJRadioButton3(), null);

			jPanel2.add(jLabel, null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(getJCheckBox1(), null);
			jPanel2.add(getBtnBackup(), null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(getJTextField(), null);
			jPanel2.add(getBtnRever(), null);

			jPanel2.add(getJButton(), null);
		}
		return jPanel2;
	}

	/**
	 * 
	 * This method initializes jRadioButton8
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getJRadioButton8() {
		if (jRadioButton8 == null) {
			jRadioButton8 = new JRadioButton();
			jRadioButton8.setText("执行DB导入");
			jRadioButton8.setBounds(372, 364, 107, 21);
			jRadioButton8.setBackground(java.awt.Color.white);
			jRadioButton8.addActionListener(new RadioActionListner());
		}
		return jRadioButton8;
	}

	private class RadioActionListner implements ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			if (jRadioButton.isSelected()) {
				// 数据源操作
				dataImportAction.checkDataRootAuthority(new Request(CommonVars
						.getCurrUser()));
				DgDataRoot dg = new DgDataRoot();
				dg.setVisible(true);
			} else if (jRadioButton1.isSelected()) {// 视图操作
				dataImportAction.checkViewAuthority(new Request(CommonVars
						.getCurrUser()));
				DgView dg = new DgView();
				dg.setVisible(true);
			} else if (jRadioButton4.isSelected()) { // 域定义管理
				dataImportAction.checkFormatAuthority(new Request(CommonVars
						.getCurrUser()));
				DgFormat dg = new DgFormat();
				dg.setVisible(true);
			} else if (jRadioButton5.isSelected()) { // 任务计划设置
				dataImportAction.checkDBTaskAuthority(new Request(CommonVars
						.getCurrUser()));
				DgDBTask dgDBTaskEx = new DgDBTask();
				dgDBTaskEx.setVisible(true);
			} else if (jRadioButton8.isSelected()) { // 执行DB导入
				dataImportAction.checkDBImportAuthority(new Request(CommonVars
						.getCurrUser()));
				DgDataTools dgDataTools = new DgDataTools();
				dgDataTools.setHs(hs);
				dgDataTools.setImpType("db");
				dgDataTools.setVisible(true);
			} else if (jRadioButton2.isSelected()) { // 视图参数设置
				dataImportAction.checkParameterListAuthority(new Request(
						CommonVars.getCurrUser()));
				DgParameterList dg = new DgParameterList();
				dg.setVisible(true);
			} else if (jRadioButton3.isSelected()) { // 事件设置
				dataImportAction.checkDataExecuSqlAuthority(new Request(
						CommonVars.getCurrUser()));
				DgDataExecuSql dg = new DgDataExecuSql();
				dg.setVisible(true);
			}
		}
	}

	/**
	 * 
	 * This method initializes pn
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			javax.swing.JLabel jLabel4 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jLabel2.setText("DB导入管理");
			jLabel2.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
			jLabel2.setForeground(new java.awt.Color(255, 153, 0));
			jLabel3.setText("");
			jLabel3.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jLabel4.setText("");
			jLabel4.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jPanel.setBackground(java.awt.Color.white);
			jPanel.setBorder(javax.swing.BorderFactory
					.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel.add(jLabel2, java.awt.BorderLayout.CENTER);
			jPanel.add(jLabel3, java.awt.BorderLayout.EAST);
			jPanel.add(jLabel4, java.awt.BorderLayout.WEST);
		}
		return jPanel;
	}

	/**
	 * 
	 * This method initializes jCheckBox1
	 * 
	 * 
	 * 
	 * @return javax.swing.JCheckBox
	 * 
	 */
	private JCheckBox getJCheckBox1() {
		if (jCheckBox1 == null) {
			jCheckBox1 = new JCheckBox();
			jCheckBox1.setBounds(310, 37, 171, 21);
			jCheckBox1.setText("启动DB自动导入服务");
			jCheckBox1.setBackground(java.awt.Color.white);
			jCheckBox1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (jCheckBox1.isSelected()) {
						ThreadList threadList = new ThreadList();
						threadList.setCompany(CommonVars.getCurrUser()
								.getCompany());
						threadList.setType("db");
						dataImportAction.saveThreadList(
								new Request(CommonVars.getCurrUser()),
								threadList);
						dataImportAction.startThread(new Request(CommonVars
								.getCurrUser()));
					} else {
						dataImportAction.deleteThread(
								new Request(CommonVars.getCurrUser()), "db");
						dataImportAction.shutDownThread(new Request(CommonVars
								.getCurrUser()));
					}
				}
			});

		}
		return jCheckBox1;
	}

	/**
	 * This method initializes btnCustomSort
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnBackup() {
		if (btnBackup == null) {
			btnBackup = new JButton();
			btnBackup.setBounds(566, 9, 76, 24);
			btnBackup.setText("备份");
			btnBackup.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(FmDBImport.this,
							"这样会删除所有设置好的数据接口资料 \n 确定要备份?? ", "警告!!!",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
						if (tfFilePath.getText().trim().equals("")) {
							JOptionPane.showMessageDialog(FmDBImport.this,
									"备份路径为空!!!!!", "警告",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
						// new backup().start();
					}
				}
			});
		}
		return btnBackup;
	}

	class backup extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正准备备份数据，请稍后...");
				dataImportAction.exportBackUp(
						new Request(CommonVars.getCurrUser()),
						tfFilePath.getText(), "");
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmDBImport.this, "备份数据成功：！",
						"提示", 2);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmDBImport.this,
						"备份数据失败：！" + e.getMessage(), "提示", 2);
			}
		}
	}

	class rever extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正准备还原数据，请稍后...");
				dataImportAction.insertTable(
						new Request(CommonVars.getCurrUser()),
						tfFilePath.getText());
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmDBImport.this, "还原数据成功：！",
						"提示", 2);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmDBImport.this,
						"还原数据失败：！" + e.getMessage(), "提示", 2);
			}
		}
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (tfFilePath == null) {
			tfFilePath = new JTextField();
			tfFilePath.setBounds(385, 9, 170, 24);
		}
		return tfFilePath;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRever() {
		if (btnRever == null) {
			btnRever = new JButton();
			btnRever.setBounds(648, 9, 76, 24);
			btnRever.setText("还原");
			btnRever.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(FmDBImport.this,
							"这样会删除所有设置好的数据接口资料 \n 确定要还原??", "警告!!!",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
						if (tfFilePath.getText().trim().equals("")) {
							JOptionPane.showMessageDialog(FmDBImport.this,
									"备份路径为空!!!!!", "警告",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
						// new rever().start();
					}
				}
			});
		}
		return btnRever;
	}

	/**
	 * This method initializes jRadioButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton2() {
		if (jRadioButton2 == null) {
			jRadioButton2 = new JRadioButton();
			jRadioButton2.setText("视图参数设置");
			jRadioButton2.setBackground(java.awt.Color.white);
			jRadioButton2.setBounds(242, 167, 110, 21);
			jRadioButton2.addActionListener(new RadioActionListner());
		}
		return jRadioButton2;
	}

	/**
	 * This method initializes jRadioButton3
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton3() {
		if (jRadioButton3 == null) {
			jRadioButton3 = new JRadioButton();
			jRadioButton3.setBounds(279, 235, 91, 21);
			jRadioButton3.setText("事件设置");
			jRadioButton3.setBackground(java.awt.Color.white);
			jRadioButton3.addActionListener(new RadioActionListner());
		}
		return jRadioButton3;
	}

	/**
	 * This method initializes btnCustomSort
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setVisible(false);
			jButton.setBounds(new java.awt.Rectangle(315, 64, 162, 29));
			jButton.setText("公共查询");
			// jButton.addActionListener(new java.awt.event.ActionListener() {
			// public void actionPerformed(java.awt.event.ActionEvent e) {
			// DgQuerySql dg = new DgQuerySql();
			// dg.setVisible(true);
			// }
			// });
		}
		return jButton;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
