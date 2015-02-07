/*
 * Created on 2004-9-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataimport;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.manualdeclare.DgImpExpUseQueryCondition;
import com.bestway.bcus.client.system.FmParameterSet;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.DBDataRoot;
import com.bestway.bcus.dataimport.entity.ThreadList;
import com.bestway.client.windows.control.ShowFormControl;
import com.bestway.client.windows.form.FmMain;
import com.bestway.common.Request;
import com.bestway.common.client.dataimport.DgViewEdit.CheckListRenderer;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.JTextPane;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class FmOldBcsImport extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private ButtonGroup group = new ButtonGroup();
	private FmParameterSet fmParameterSet;
	private JPanel jPanel = null;
	private DataImportAction dataImportAction = null;
	private JButton jButton = null;
	private JLabel jLabel1 = null;
	private JTextField jTextField = null;
	private JLabel jLabel5 = null;
	private JTextField jTextField1 = null;
	private JLabel jLabel6 = null;
	private JTextField jTextField2 = null;
	private JLabel jLabel7 = null;
	private JTextField jTextField3 = null;
	private JList jList = null;
	private Vector vector = new Vector(); // 所有字段
	private JButton jButton1 = null;

	private JLabel jLabel8 = null;

	private JTextField jTextField4 = null;

	private JLabel jLabel9 = null;

	private JTextField jTextField5 = null;

	private JTextPane jTextPane = null;

	private JLabel jLabel10 = null;

	private JButton jButton2 = null;

	private JButton jButton3 = null;

	private JTextField jTextField6 = null;

	/**
	 * This is the default constructor
	 */
	public FmOldBcsImport() {
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
		this.setTitle("OLD BCS IMPORT");
		this.setSize(666, 554);
		this.setContentPane(getJContentPane());
		initUI();
	}

	private void initUI() {
		this.jList.removeAll();
		Vector<String> columnsName = new Vector<String>();
		columnsName.add("汇率");
		columnsName.add("包装种类");
		columnsName.add("商品编码");
		// columnsName.add("合同类型");
		columnsName.add("报关商品资料");
		columnsName.add("客户供应商");
		columnsName.add("合同备案");
		columnsName.add("进口报关单_纸质手册");
		columnsName.add("出口报关单_纸质手册");
		columnsName.add("特殊报关单_纸质手册");
		columnsName.add("进口报关单_电子帐册");
		columnsName.add("出口报关单_电子帐册");
		columnsName.add("特殊报关单_电子帐册");
		columnsName.add("单据头_单据中心");
		columnsName.add("单据体_单据中心");
		for (int j = 0; j < columnsName.size(); j++) {
			CheckableItem item = new CheckableItem(String.valueOf(j + 1),
					(String) columnsName.get(j));
			vector.add(item);
		}
		this.jList.setListData(vector);
	}

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
			setText("  " + item.getCode() + "  " + item.getTableName());
			return this;
		}
	}

	/**
	 * This method initializes jContentPane
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
	 * This method initializes jPanel2
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel10 = new JLabel();
			jLabel10.setVisible(false);

			jLabel10.setBounds(new java.awt.Rectangle(83, 357, 182, 19));
			jLabel10.setText("请输入源报关单最大流水号：");
			jLabel9 = new JLabel();
			jLabel9.setBounds(new java.awt.Rectangle(84, 331, 91, 19));
			jLabel9.setText("请输入手册号：");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new java.awt.Rectangle(83, 306, 107, 21));
			jLabel8.setText("请输入合同序号：");
			jLabel7 = new JLabel();
			jLabel6 = new JLabel();
			jLabel5 = new JLabel();
			jLabel1 = new JLabel();
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
			jLabel1.setBounds(128, 187, 58, 22);
			jLabel1.setText("服务器:");
			jLabel5.setBounds(128, 214, 59, 23);
			jLabel5.setText("数据库：");
			jLabel6.setBounds(128, 245, 59, 23);
			jLabel6.setText("用户名：");
			jLabel7.setBounds(128, 275, 61, 23);
			jLabel7.setText("密码：");
			jPanel2.add(jLabel, null);
			jPanel2.add(getJButton(), null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(getJTextField(), null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(getJTextField1(), null);
			jPanel2.add(jLabel6, null);
			jPanel2.add(getJTextField2(), null);
			jPanel2.add(jLabel7, null);
			jPanel2.add(getJTextField3(), null);
			jPanel2.add(getJList(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(jLabel8, null);
			jPanel2.add(getJTextField4(), null);
			jPanel2.add(jLabel9, null);
			jPanel2.add(getJTextField5(), null);
			jPanel2.add(getJTextPane(), null);
			jPanel2.add(jLabel10, null);
			jPanel2.add(getJButton2(), null);
			jPanel2.add(getJButton3(), null);
			jPanel2.add(getJTextField6(), null);
		}
		return jPanel2;
	}

	private class RadioActionListner implements ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			/*
			 * if (jRadioButton.isSelected()){ DgFieldCollate dg = new
			 * DgFieldCollate(); dg.setVisible(true); } else if
			 * (jRadioButton1.isSelected()){ fmParameterSet = new
			 * FmParameterSet();
			 * fmParameterSet.setMainFrame(FmOldBcsImport.this.getMainFrame());
			 * ShowFormControl.showForm(fmParameterSet,
			 * ((FmMain)FmOldBcsImport.this.getMainFrame()).getDeskPanel(),
			 * FmOldBcsImport.this.getMenu()); } else if
			 * (jRadioButton4.isSelected()){ DgTxtFormatFieldSetup dg = new
			 * DgTxtFormatFieldSetup(); dg.setVisible(true); } else if
			 * (jRadioButton5.isSelected()){ DgTxtTask dgTxtTaskEx = new
			 * DgTxtTask(); dgTxtTaskEx.setVisible(true); } else if
			 * (jRadioButton8.isSelected()){ DgDataTools dgDataTools = new
			 * DgDataTools(); dgDataTools.setImpType("txt");
			 * dgDataTools.setVisible(true); }
			 */
		}
	}

	/**
	 * 
	 * This method initializes jPanel
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			javax.swing.JLabel jLabel4 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jLabel2.setText("OLD BCS IMPORT");
			jLabel2
					.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 20));
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
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(365, 354, 113, 25);
			jButton.setText("开始导入");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jList.getSelectedValue() == null) {
						JOptionPane.showMessageDialog(FmOldBcsImport.this,
								"请选择要导入的表!", "提示", 2);
						return;
					}
					List list = new Vector();
					// Object[] selectedTables = jList.getSelectedValue();
					for (int i = 0; i < jList.getModel().getSize(); i++) {
						CheckableItem item = (CheckableItem) jList.getModel()
								.getElementAt(i);
						if (item.isSelected) {
							String x = item.getTableName();
							System.out.println("--系统准备导入:" + x);
							list.add(x);
						}
					}
					new find(list).start();
				}
			});
		}
		return jButton;
	}

	class find extends Thread {
		private List list = null;

		public find(List list) {
			this.list = list;
		}

		public void run() {
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正导入数据，请稍后...");
				dataImportAction.importData(new Request(CommonVars
						.getCurrUser()), list, jTextField.getText(),
						jTextField1.getText(), jTextField2.getText(),
						jTextField3.getText(), jTextField4.getText(),
						jTextField5.getText());
				CommonProgress.closeProgressDialog();
				CommonProgress.setMessage("导入完毕！，请稍后...");
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmOldBcsImport.this, "获取数据失败：！"
						+ e.getMessage(), "提示", 2);
			}
		}
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(191, 187, 134, 22);
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
			jTextField1.setBounds(191, 215, 134, 22);
		}
		return jTextField1;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setBounds(191, 247, 134, 22);
		}
		return jTextField2;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setBounds(191, 276, 133, 22);
		}
		return jTextField3;
	}

	class UserListCellRenderer extends DefaultListCellRenderer {
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected,
					cellHasFocus);
			String s = "";
			if (value != null) {
				s = ((DBDataRoot) value).getName();
				setIcon(CommonVars.getRcpIconSource().getIcon(
						"images.table.icon"));
			}
			setText(s);
			return this;
		}
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
			jList.setBounds(368, 0, 197, 352);
			jList.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					int index = jList.locationToIndex(e.getPoint());
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
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(272, 349, 135, 25);
			jButton1.setText("Copy Complex");
			jButton1.setVisible(false);
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new copyComplex().start();
				}
			});
		}
		return jButton1;
	}

	class copyComplex extends Thread {

		public void run() {
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正导入数据，请稍后...");
				dataImportAction.copyComplex(new Request(CommonVars
						.getCurrUser()));
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmOldBcsImport.this, "获取数据失败：！"
						+ e.getMessage(), "提示", 2);
			} finally {
				dispose();
			}
		}
	}

	/**
	 * This method initializes jTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
			jTextField4.setBounds(new java.awt.Rectangle(190, 307, 132, 22));
		}
		return jTextField4;
	}

	/**
	 * This method initializes jTextField5
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField5() {
		if (jTextField5 == null) {
			jTextField5 = new JTextField();
			jTextField5.setBounds(new java.awt.Rectangle(190, 332, 167, 21));
		}
		return jTextField5;
	}

	/**
	 * This method initializes jTextPane
	 * 
	 * @return javax.swing.JTextPane
	 */
	private JTextPane getJTextPane() {
		if (jTextPane == null) {
			jTextPane = new JTextPane();
			jTextPane.setBounds(new java.awt.Rectangle(14, 378, 416, 68));
			jTextPane.setEditable(false);
			jTextPane.setForeground(java.awt.Color.red);
			jTextPane
					.setText("输入合同序号 -- 导入合同备案通过合同序号筛选，为空导入所有合同；\n输入手册号     -- 导入进出口报关单通过手册号筛选，为空导入所有报关单；");
			// \n输入老系统报关单最大流水号与手册号 -- 删除进口报关单，出口报关单
		}
		return jTextPane;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new java.awt.Rectangle(439, 392, 138, 23));
			jButton2.setText("删除进口报关单");
			jButton2.setVisible(false);
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String emsNo = jTextField5.getText();
					String serialNum = jTextField6.getText();
					String coid = CommonVars.getCurrUser().getCompany().getId();
					ArrayList<String> list = new ArrayList<String>();
					String sqld = "delete from bcscustomsdeclarationcomminfo where coid = '"
							+ coid
							+ "' and customsdeclaration in "
							+ " (select id from bcscustomsdeclaration "
							+ " where emsheadh2k = '"
							+ emsNo
							+ "' and impexpflag = '0' and serialnumber <= "
							+ serialNum + " and coid = '" + coid + "')";
					list.add(sqld);
					String sqlc = "delete from bcscustomsdeclarationcontainer where coid = '"
							+ coid
							+ "' and customsdeclaration in "
							+ " (select id from bcscustomsdeclaration "
							+ " where emsheadh2k = '"
							+ emsNo
							+ "' and impexpflag = '0' and serialnumber <= "
							+ serialNum + " and coid = '" + coid + "')";
					list.add(sqlc);
					String sqlh = "delete from bcscustomsdeclaration "
							+ " where emsheadh2k = '" + emsNo
							+ "' and impexpflag = '0' and serialnumber <= "
							+ serialNum + " and coid = '" + coid + "'";
					list.add(sqlh);
					new executeHsql(list).start();

				}
			});
		}
		return jButton2;
	}

	class executeHsql extends Thread {
		List list;

		public executeHsql(List list) {
			this.list = list;
		}

		public void run() {
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正在执行文件资料，请稍后...");

				String sqlstr = "";
				int x = 0;
				long beginTime = System.currentTimeMillis();
				for (int i = 0; i < list.size(); i++) {
					String hsql = (String) list.get(i);
					sqlstr = sqlstr + hsql + "\n";
					try {
						System.out.println(" -- " + hsql);
						dataImportAction.execuFileSql(new Request(CommonVars
								.getCurrUser()), hsql);
						CommonProgress.closeProgressDialog();
					} catch (Exception ex) {
						CommonProgress.closeProgressDialog();
						JOptionPane.showMessageDialog(FmOldBcsImport.this,
								"执行有错误：\n" + ex + "\n" + "第 "
										+ String.valueOf(i + 1) + " 条语句" + "\n"
										+ "请将执行文件E-Mail至负责您公司的百思维顾问邮件中,谢谢!",
								"提示", 2);
						x = 1;
						break;
					}
				}
				long endTime = System.currentTimeMillis();
				if (x == 0) {
					JOptionPane.showMessageDialog(FmOldBcsImport.this,
							"执行成功:\n" + "执行语句：\n" + sqlstr + "共消耗时间: "
									+ (endTime - beginTime) + " 毫秒 ", "提示", 2);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
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
			jButton3.setBounds(new java.awt.Rectangle(439, 421, 138, 23));
			jButton3.setText("删除出口报关单");
			jButton3.setVisible(false);
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String emsNo = jTextField5.getText();
					String serialNum = jTextField6.getText();
					String coid = CommonVars.getCurrUser().getCompany().getId();
					ArrayList<String> list = new ArrayList<String>();
					String sqld = "delete from bcscustomsdeclarationcomminfo where coid = '"
							+ coid
							+ "' and customsdeclaration in "
							+ " (select id from bcscustomsdeclaration "
							+ " where emsheadh2k = '"
							+ emsNo
							+ "' and impexpflag = '1' and serialnumber <= "
							+ serialNum + " and coid = '" + coid + "')";
					list.add(sqld);
					String sqlc = "delete from bcscustomsdeclarationcontainer where coid = '"
							+ coid
							+ "' and customsdeclaration in "
							+ " (select id from bcscustomsdeclaration "
							+ " where emsheadh2k = '"
							+ emsNo
							+ "' and impexpflag = '1' and serialnumber <= "
							+ serialNum + " and coid = '" + coid + "')";
					list.add(sqlc);
					String sqlh = "delete from bcscustomsdeclaration "
							+ " where emsheadh2k = '" + emsNo
							+ "' and impexpflag = '1' and serialnumber <= "
							+ serialNum + " and coid = '" + coid + "'";
					list.add(sqlh);
					new executeHsql(list).start();
				}
			});
		}
		return jButton3;
	}

	/**
	 * This method initializes jTextField6
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField6() {
		if (jTextField6 == null) {
			jTextField6 = new JTextField();
			jTextField6.setBounds(new java.awt.Rectangle(267, 356, 88, 22));
			jTextField6.setVisible(false);
		}
		return jTextField6;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
