/*
 * Created on 2004-9-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataimport;

import java.awt.event.ActionListener;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.license.LicenseClient;
import com.bestway.bcus.client.manualdeclare.DgBcusPathSet;
import com.bestway.bcus.client.system.FmParameterSet;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.ThreadList;
import com.bestway.client.windows.control.ShowFormControl;
import com.bestway.client.windows.form.FmMain;
import com.bestway.common.Request;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.common.tools.entity.TempNodeItem;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.Font;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class FmTxtImport extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JPanel jPanel1 = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	private JRadioButton jRadioButton4 = null;

	private JRadioButton jRadioButton5 = null;

	private JPanel jPanel2 = null;

	private JRadioButton jRadioButton8 = null;

	private ButtonGroup group = new ButtonGroup();

	private JCheckBox jCheckBox = null;

	private FmParameterSet fmParameterSet;

	private JPanel jPanel = null;

	private DataImportAction dataImportAction = null;

	private JCheckBox jCheckBox1 = null;

	private Hashtable hs = new Hashtable();

	private ToolsAction toolsAction = null;

	private Map<TempNodeItem, List<TempNodeItem>> dataDictMap = null;

	private JButton jButton = null;

	/**
	 * This is the default constructor
	 */
	public FmTxtImport() {
		super();
		// if (!LicenseClient.getInstance(
		// CommonVars.getCurrUser().getCompany().getName())
		// .checkFileImportPermisson()) {
		// JOptionPane.showMessageDialog(FmTxtImport.this, "你没有文本导入的使用权限");
		// doDefaultCloseAction();
		// return;
		// }
		dataImportAction = (DataImportAction) CommonVars
				.getApplicationContext().getBean("dataImportAction");
//		dataImportAction.checkTxtImportAuthority(new Request(CommonVars
//				.getCurrUser()));
		toolsAction = (ToolsAction) CommonVars.getApplicationContext().getBean(
				"toolsAction");
		initialize();
		this.jCheckBox.setSelected(true);
		CommonVars.setIstitleRow(new Boolean(true));
		dataImportAction.changeIsTitle(new Request(CommonVars.getCurrUser()),
				new Boolean(true));
		List list = dataImportAction.findThreadList(new Request(CommonVars
				.getCurrUser()), "txt");
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
		this.setTitle("文本导入管理");
		this.setSize(666, 507);
		this.setContentPane(getJContentPane());
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {

					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						if (!LicenseClient
								.getInstance(
										CommonVars.getCurrUser().getCompany()
												.getName())
								.checkFileImportPermisson()) {
							JOptionPane.showMessageDialog(FmTxtImport.this,
									"没有使用文本导入功能的权限，如果需要请联系百思维！");
							doDefaultCloseAction();
						}
					}
				});
		group.add(jRadioButton);
		group.add(jRadioButton1);
		group.add(jRadioButton4);
		group.add(jRadioButton5);
		group.add(jRadioButton8);
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
			jRadioButton.setText("系统字段对照表");
			jRadioButton.setBounds(228, 178, 123, 26);
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
			jRadioButton1.setText("导入路径设置");
			jRadioButton1.setBounds(249, 212, 113, 21);
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
			jRadioButton4.setText("导入格式设置");
			jRadioButton4.setBounds(270, 243, 107, 21);
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
			jRadioButton5.setText("导入任务设置");
			jRadioButton5.setBounds(296, 276, 110, 21);
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
			jLabel1.setBounds(24, 109, 538, 281);
			jLabel1.setText("");
			jLabel1
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/com/bestway/bcus/client/resources/images/txt_import_middle.jpg")));
			jPanel2.add(getJRadioButton(), null);
			jPanel2.add(getJRadioButton1(), null);
			jPanel2.add(getJRadioButton4(), null);
			jPanel2.add(getJRadioButton5(), null);
			jPanel2.add(getJRadioButton8(), null);
			jPanel2.add(jLabel, null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(getJCheckBox(), null);
			jPanel2.add(getJCheckBox1(), null);
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
			jRadioButton8.setText("执行文本导入");
			jRadioButton8.setBounds(318, 308, 107, 21);
			jRadioButton8.setBackground(java.awt.Color.white);
			jRadioButton8.addActionListener(new RadioActionListner());
		}
		return jRadioButton8;
	}

	private class RadioActionListner implements ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			if (jRadioButton.isSelected()) {//系统字段对照表
				dataImportAction.checkFieldCollateAuthority(new Request(CommonVars
						.getCurrUser()));
				DgFieldCollate dg = new DgFieldCollate();
				dg.setVisible(true);
			} else if (jRadioButton1.isSelected()) {//导入路径设置
				dataImportAction.checkBcusPathSetAuthority(new Request(CommonVars
						.getCurrUser()));
				DgBcusPathSet dg = new DgBcusPathSet();
				dg.setVisible(true);
				// edit by sls 问题号:0000638
				// fmParameterSet = new FmParameterSet();
				// fmParameterSet.setMainFrame(FmTxtImport.this.getMainFrame());
				// ShowFormControl.showForm(fmParameterSet,
				// ((FmMain)FmTxtImport.this.getMainFrame()).getDeskPanel(),
				// FmTxtImport.this.getMenu(),null);
			} else if (jRadioButton4.isSelected()) {//导入格式设置
				dataImportAction.checkTxtFormatFieldSetupAuthority(new Request(CommonVars
						.getCurrUser()));
				DgTxtFormatFieldSetup dg = new DgTxtFormatFieldSetup();
				dg.setVisible(true);
			} else if (jRadioButton5.isSelected()) {//导入任务设置
				dataImportAction.checkTxtTaskAuthority(new Request(CommonVars
						.getCurrUser()));
				DgTxtTask dgTxtTaskEx = new DgTxtTask();
				dgTxtTaskEx.setVisible(true);
			} else if (jRadioButton8.isSelected()) {//执行文本导入
				dataImportAction.checkDataToolsAuthority(new Request(CommonVars
						.getCurrUser()));
				DgDataTools dgDataTools = new DgDataTools();
				dgDataTools.setImpType("txt");
				dgDataTools.setHs(hs);
				dgDataTools.setVisible(true);
			}
		}
	}

	/**
	 * 
	 * This method initializes jCheckBox
	 * 
	 * 
	 * 
	 * @return javax.swing.JCheckBox
	 * 
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBackground(java.awt.Color.white);
			jCheckBox.setText("文本第一行是否为标题行");
			jCheckBox.setBounds(444, 61, 174, 21);
			jCheckBox.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (FmTxtImport.this.jCheckBox.isSelected()) {
						CommonVars.setIstitleRow(new Boolean(true));
						dataImportAction.changeIsTitle(new Request(CommonVars
								.getCurrUser()), new Boolean(true));
					} else {
						CommonVars.setIstitleRow(new Boolean(false));
						dataImportAction.changeIsTitle(new Request(CommonVars
								.getCurrUser()), new Boolean(false));
					}
				}
			});

		}
		return jCheckBox;
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
			jLabel2.setText("文本导入管理");
			jLabel2
					.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
			jLabel2.setForeground(new java.awt.Color(255, 153, 0));
			jLabel3.setText("");
			jLabel3.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jLabel4.setText("");
			jLabel4
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jPanel.setBackground(java.awt.Color.white);
			jPanel
					.setBorder(javax.swing.BorderFactory
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
			jCheckBox1.setBounds(444, 36, 171, 21);
			jCheckBox1.setText("启动文本导入服务");
			jCheckBox1.setBackground(java.awt.Color.white);
			jCheckBox1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (jCheckBox1.isSelected()) {
						ThreadList threadList = new ThreadList();
						threadList.setType("txt");
						threadList.setCompany(CommonVars.getCurrUser()
								.getCompany());
						dataImportAction.saveThreadList(new Request(CommonVars
								.getCurrUser()), threadList);
					} else {
						dataImportAction.deleteThread(new Request(CommonVars
								.getCurrUser()), "txt");
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
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new java.awt.Rectangle(570, 305, 72, 30));
			jButton.setText("232");
			jButton.setVisible(false);
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgSuperTxtImport dg = new DgSuperTxtImport();
					dg.setVisible(true);
				}
			});
		}
		return jButton;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
