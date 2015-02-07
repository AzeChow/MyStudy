/*
 * Created on 2004-11-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataimport;

import java.awt.BorderLayout;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.DBDataRoot;
import com.bestway.bcus.dataimport.entity.DriverList;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 *
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgConnectStr extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanel2 = null;
	private JPanel jPanel3 = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private DataImportAction dataImportAction = null;
	private JTableListModel tableModel = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private JTextField jTextField = null;
	private JTextField jTextField1 = null;
	private JTextField jTextField3 = null;
	private JButton jButton3 = null;
	public String driverClassName = null;
	public String url = null;
	public String userName = null;
	public String password = null;
	public String num = null;
	public String serverName = null;
	public String dataName = null;
	private boolean isConnect = false;
	private boolean ok = false;
	private boolean isAdd = false;
	private DBDataRoot dbDataRoot = null;
	private JPasswordField jPasswordField = null;
	private JButton jButton4 = null;
	private JButton jButton5 = null;
	private DriverList driverList = null;

	/**
	 * This is the default constructor
	 */
	public DgConnectStr() {
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
		this.setTitle("数据链接属性");
		this.setSize(354, 391);
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				initData();
			}
		});

	}

	private void initData() {
		List list = dataImportAction.findDriverList(new Request(CommonVars
				.getCurrUser()));
		initTable(list);
		if (!this.isAdd) { // 修改
			jTabbedPane.setSelectedIndex(1);
			driverList = dataImportAction.findDriverListObj(new Request(
					CommonVars.getCurrUser()), Integer.valueOf(dbDataRoot
					.getFlat()));
			this.setNum(dbDataRoot.getFlat());
			fillWindow();
		}
	}

	private void fillWindow() {
		this.jTextField.setText(dbDataRoot.getServerName());
		this.jTextField1.setText(dbDataRoot.getUserName());
		this.jPasswordField.setText(dbDataRoot.getPassword());
		this.jTextField3.setText(dbDataRoot.getDataName());
	}

	private void initTable(List dataSource) {
		tableModel = new JTableListModel(jTable, dataSource,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("JDBC 提供程序", "name", 285));
						return list;
					}
				});
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
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jSplitPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerLocation(320);
			jSplitPane.setDividerSize(0);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
		}
		return jSplitPane;
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
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
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
			jPanel1.setLayout(null);
			jPanel1.setBorder(javax.swing.BorderFactory.createCompoundBorder(
					null, null));
			jPanel1.add(getJButton1(), null);
			jPanel1.add(getJButton2(), null);
		}
		return jPanel1;
	}

	/**
	 * 
	 * This method initializes jTabbedPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("提供程序", null, getJPanel2(), null);
			jTabbedPane.addTab("连接", null, getJPanel3(), null);
		}
		return jTabbedPane;
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
			javax.swing.JLabel jLabel = new JLabel();

			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jLabel.setBounds(10, 8, 147, 22);
			jLabel.setText("请选择您希望连接的数据：");
			jPanel2.add(jLabel, null);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJScrollPane(), null);
			jPanel2.add(getJButton4(), null);
			jPanel2.add(getJButton5(), null);
		}
		return jPanel2;
	}

	/**
	 * 
	 * This method initializes jPanel3
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			javax.swing.JLabel jLabel6 = new JLabel();

			javax.swing.JLabel jLabel5 = new JLabel();

			javax.swing.JLabel jLabel4 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jLabel1.setBounds(19, 15, 245, 20);
			jLabel1.setText("指定下列设置以连接到数据:");
			jLabel2.setBounds(19, 41, 194, 19);
			jLabel2.setText("1,输入服务器名称(ODBC输入别名)");
			jLabel3.setBounds(19, 91, 155, 20);
			jLabel3.setText("2,输入登录服务器的信息");
			jLabel4.setBounds(37, 117, 48, 19);
			jLabel4.setText("用户名");
			jLabel5.setBounds(37, 148, 45, 21);
			jLabel5.setText("密    码");
			jLabel6.setBounds(19, 176, 164, 20);
			jLabel6.setText("3,在服务器上输入数据库");
			jPanel3.add(jLabel1, null);
			jPanel3.add(jLabel2, null);
			jPanel3.add(getJTextField(), null);
			jPanel3.add(jLabel3, null);
			jPanel3.add(jLabel4, null);
			jPanel3.add(getJTextField1(), null);
			jPanel3.add(jLabel5, null);
			jPanel3.add(jLabel6, null);
			jPanel3.add(getJTextField3(), null);
			jPanel3.add(getJButton3(), null);
			jPanel3.add(getJPasswordField(), null);
		}
		return jPanel3;
	}

	/**
	 * 
	 * This method initializes jButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(249, 250, 77, 23);
			jButton.setText("下一步");
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgConnectStr.this,
								"请选择OLE DB 提供程序", "确认", 2);
						return;
					}
					driverList = (DriverList) tableModel.getCurrentRow();
					DgConnectStr.this.setNum(String
							.valueOf(((DriverList) tableModel.getCurrentRow())
									.getSortno()));
					jTabbedPane.setSelectedIndex(1);
				}
			});

		}
		return jButton;
	}

	/**
	 * 
	 * This method initializes jButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(189, 7, 67, 23);
			jButton1.setText("确定");
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (DgConnectStr.this.isConnect) {
						putIn();
					} else {
						if (!connect()) {
							return;
						} else {
							putIn();
						}
					}
				}
			});

		}
		return jButton1;
	}

	private void putIn() {
		DgConnectStr.this.setOk(true);
		DgConnectStr.this.dispose();
	}

	/**
	 * 
	 * This method initializes jButton2
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(266, 7, 67, 23);
			jButton2.setText("取消");
			jButton2.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgConnectStr.this.setOk(false);
					DgConnectStr.this.dispose();
				}
			});

		}
		return jButton2;
	}

	/**
	 * 
	 * This method initializes jTable
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
			jTable.setShowHorizontalLines(false);
			jTable.setShowVerticalLines(false);
			jTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (tableModel == null) {
								return;
							}
							if (tableModel.getCurrentRow() == null) {
								return;
							}
							driverList = (DriverList) tableModel
									.getCurrentRow();
							DgConnectStr.this.setNum(String.valueOf(driverList
									.getSortno()));
						}
					});
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {

				public void mouseClicked(java.awt.event.MouseEvent e) {

					if (e.getClickCount() == 2) {
						if (tableModel == null) {
							return;
						}
						if (tableModel.getCurrentRow() == null) {
							return;
						}
						driverList = (DriverList) tableModel.getCurrentRow();
						DgConnectStr.this.setNum(String.valueOf(driverList
								.getSortno()));
						jTabbedPane.setSelectedIndex(1);
					}
				}
			});
		}
		return jTable;
	}

	/**
	 * 
	 * This method initializes jScrollPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(10, 35, 320, 199);
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * 
	 * This method initializes jTextField
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(36, 64, 274, 22);
		}
		return jTextField;
	}

	/**
	 * 
	 * This method initializes jTextField1
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(85, 117, 226, 22);
		}
		return jTextField1;
	}

	/**
	 * 
	 * This method initializes jTextField3
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setBounds(37, 202, 272, 22);
		}
		return jTextField3;
	}

	/**
	 * 
	 * This method initializes jButton3
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(223, 251, 86, 24);
			jButton3.setText("测试连接");
			jButton3.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					connect();
				}
			});

		}
		return jButton3;
	}

	private boolean connect() {
		userName = DgConnectStr.this.jTextField1.getText();

		password = String.valueOf(jPasswordField.getPassword());

		String addre = null;

		String what = DgConnectStr.this.jTextField.getText();

		System.out.println("what **** : " + what);

		if (DgConnectStr.this.jTextField.getText().equals(".")) {

			addre = getComputerIp();

		} else {

			addre = DgConnectStr.this.jTextField.getText();

		}

		driverClassName = driverList.getDriverClassName();

		System.out.println("DRIVER CLASS NAME : **** " + driverClassName);

		System.out.println(driverList.getUrl() + " getURL() ------------");

		url = driverList.getUrl().replace("ADDRESS", addre);

		System.out.println(" **** url1 : " + url);

		url = url.replace("DATATABLE", DgConnectStr.this.jTextField3.getText());

		setServerName(addre);

		setDataName(DgConnectStr.this.jTextField3.getText());

		System.out.println(" **** url2 : " + url);

		if (!dataImportAction.isConnect(new Request(CommonVars.getCurrUser()),
				driverClassName, url, userName, password).equals("成功")) {

			String xx = dataImportAction.isConnect(
					new Request(CommonVars.getCurrUser()), driverClassName,
					url, userName, password);

			JOptionPane.showMessageDialog(DgConnectStr.this, xx, "数据链接错误", 2);

			return false;

		} else {
			JOptionPane
					.showMessageDialog(DgConnectStr.this, "连接成功!", "数据链接", 2);
			this.setConnect(true);
			return true;
		}
	}

	/**
	 * @return Returns the ok.
	 */
	public boolean isOk() {
		return ok;
	}

	/**
	 * @param ok
	 *            The ok to set.
	 */
	public void setOk(boolean ok) {
		this.ok = ok;
	}

	// 得到IP
	private String getComputerIp() {
		try {
			InetAddress localhost = InetAddress.getLocalHost();
			return localhost.getHostAddress();
		} catch (UnknownHostException uhe) {
			return "";
		}
	}

	/**
	 * @return Returns the isConnect.
	 */
	public boolean isConnect() {
		return isConnect;
	}

	/**
	 * @param isConnect
	 *            The isConnect to set.
	 */
	public void setConnect(boolean isConnect) {
		this.isConnect = isConnect;
	}

	/**
	 * @return Returns the driverClassName.
	 */
	public String getDriverClassName() {
		return driverClassName;
	}

	/**
	 * @param driverClassName
	 *            The driverClassName to set.
	 */
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return Returns the url.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            The url to set.
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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
	 * @return Returns the serverName.
	 */
	public String getServerName() {
		return serverName;
	}

	/**
	 * @param serverName
	 *            The serverName to set.
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	/**
	 * @return Returns the dataName.
	 */
	public String getDataName() {
		return dataName;
	}

	/**
	 * @param dataName
	 *            The dataName to set.
	 */
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	/**
	 * @return Returns the dbDataRoot.
	 */
	public DBDataRoot getDbDataRoot() {
		return dbDataRoot;
	}

	/**
	 * @param dbDataRoot
	 *            The dbDataRoot to set.
	 */
	public void setDbDataRoot(DBDataRoot dbDataRoot) {
		this.dbDataRoot = dbDataRoot;
	}

	/**
	 * 
	 * This method initializes jPasswordField
	 * 
	 * 
	 * 
	 * @return javax.swing.JPasswordField
	 */
	private JPasswordField getJPasswordField() {
		if (jPasswordField == null) {
			jPasswordField = new JPasswordField();
			jPasswordField.setBounds(87, 148, 224, 22);
		}
		return jPasswordField;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setBounds(87, 250, 68, 23);
			jButton4.setText("新增");
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgConnectAdd dg = new DgConnectAdd();
					dg.setTableModel(tableModel);
					dg.setIsAdd(true);
					dg.setVisible(true);
				}
			});
		}
		return jButton4;
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setBounds(169, 250, 68, 23);
			jButton5.setText("修改");
			jButton5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgConnectStr.this, "请选择",
								"提示", 2);
						return;
					}
					DgConnectAdd dg = new DgConnectAdd();
					dg.setTableModel(tableModel);
					dg.setIsAdd(false);
					dg.setVisible(true);
				}
			});
		}
		return jButton5;
	}

	/**
	 * @return Returns the num.
	 */
	public String getNum() {
		return num;
	}

	/**
	 * @param num
	 *            The num to set.
	 */
	public void setNum(String num) {
		this.num = num;
	}
} // @jve:decl-index=0:visual-constraint="-38,10"
