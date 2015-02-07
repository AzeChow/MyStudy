/*
 * Created on 2004-6-9
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.system;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.license.DgLicenseInfo;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityException;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author dream
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class FmCompany extends JInternalFrameBase {
	private JPanel jPanel = null;

	private JToolBar jToolBar = null;

	private JButton btnAdd = null;

	private JButton btnEdit = null;

	private JButton btnDelete = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private SystemAction systemAction = null;

	/**
	 * 
	 */
	public FmCompany() {

		super();

		service.checkFindCompanyAuthority(new Request(CommonVars.getCurrUser()));

		systemAction = (SystemAction) CommonVars.getApplicationContext()
				.getBean("systemAction");

		initialize();

		initCompanyData();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {

		setContentPane(getJPanel());

		setTitle("公司资料");

		setHelpId("company");

		setSize(793, 259);
		// jLabel.setText(" 当前登陆公司加工单位名称: "
		// + CommonVars.getCurrUser().getCompany().getName());

	}

	/**
	 * 
	 * This method initializes jPanel
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * 
	 * This method initializes jToolBar
	 * 
	 * 
	 * 
	 * @return javax.swing.JToolBar
	 * 
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jLabel = new JLabel();
			jToolBar = new JToolBar();
			jLabel.setText("   当前公司:");
			jLabel.setForeground(new java.awt.Color(0, 102, 0));
			jLabel.setVisible(false);
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getBtnPrint());
			jToolBar.add(getBtnLicenseInfo());
			jToolBar.add(getBtnEnable());
			jToolBar.add(getBtnExit());
			jToolBar.add(jLabel);
		}
		return jToolBar;
	}

	/**
	 * 
	 * This method initializes btnAdd
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnAdd() {

		if (btnAdd == null) {

			btnAdd = new JButton();

			btnAdd.setText("新增");

			btnAdd.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgCompany dgCompany = new DgCompany();

					dgCompany.setTableModel(tablemodel);

					dgCompany.setAdd(true);

					dgCompany.setVisible(true);

				}
			});
		}
		return btnAdd;
	}

	/**
	 * 
	 * This method initializes btnEdit
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnEdit() {

		if (btnEdit == null) {

			btnEdit = new JButton();

			btnEdit.setText("修改");

			btnEdit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tablemodel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmCompany.this,
								"请选择你将要修改的记录", "提示！", 0);
						return;
					}

					// CommonVars.checkPermission("System", "updata");
					DgCompany dgCompany = new DgCompany();

					dgCompany.setAdd(false);
					dgCompany.setTableModel(tablemodel);
					dgCompany.setVisible(true);
				}
			});
		}
		return btnEdit;
	}

	/**
	 * 
	 * This method initializes btnDelete
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					Company company = (Company) tablemodel.getCurrentRow();
					if (company == null) {
						JOptionPane.showMessageDialog(FmCompany.this,
								"您没有选中任何记录！", "提示信息", 0);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmCompany.this,
							"您确定要删除该条记录吗？", "提示信息", 0) == JOptionPane.YES_OPTION) {
						try {
							service.deleteCompany(
									new Request(CommonVars.getCurrUser()),
									company);
							getTableListModel().deleteRow(company);
						} catch (AuthorityException a) {
							JOptionPane.showMessageDialog(FmCompany.this,
									"你没有删除记录的权限，请与管理员联系！", "提示信息", 2);
						}

						catch (Exception r) {
							JOptionPane.showMessageDialog(FmCompany.this,
									"该公司已被引用，您不能删除！", "提示信息", 2);
						}

					}
				}
			});
		}
		return btnDelete;
	}

	/**
	 * 
	 * This method initializes jTable
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
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
								if (tableModel.getCurrentRow() != null) {
									Company com = (Company) tableModel
											.getCurrentRow();
									btnEnable.setText(Boolean.FALSE.equals(com
											.getIsEnable()) ? "启用" : "禁用");
								}
							} catch (Exception ee) {
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
	 * 
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	private void initCompanyData() {
		List list = null;
		list = service.findCompanies();
		tablemodel = new JTableListModel(jTable, list, new CompanyAdapter());
		jTable.getColumnModel().getColumn(8)
				.setCellRenderer(new checkBoxRenderer());
		jTable.getColumnModel().getColumn(9)
				.setCellRenderer(new checkBoxRenderer2());
	}

	private JTableListModel getTableListModel() {
		if (tablemodel == null) {
			List list = null;
			list = service.findCompanies();
			tablemodel = new JTableListModel(jTable, list, new CompanyAdapter());

		}
		return tablemodel;
	}

	private SystemAction service = (SystemAction) CommonVars
			.getApplicationContext().getBean("systemAction");

	private JTableListModel tablemodel = null;

	private JButton btnPrint = null;

	private JButton btnExit = null;

	private JLabel jLabel = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton btnLicenseInfo = null;
	private JButton btnEnable;

	/**
	 * This method initializes btnPrint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = null;
					Company compay = (Company) CommonVars.getCurrUser()
							.getCompany();
					CustomReportDataSource ds = null;
					list = new ArrayList();
					list.add(compay);
					ds = new CustomReportDataSource(list);
					try {
						InputStream masterReportStream = FmCompany.class
								.getResourceAsStream("report/CompanyReport.jasper");
						JasperPrint jasperPrint = JasperFillManager.fillReport(
								masterReportStream, null, ds);
						DgReportViewer viewer = new DgReportViewer(jasperPrint);
						viewer.setVisible(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return btnPrint;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmCompany.this.doDefaultCloseAction();
				}
			});
		}
		return btnExit;
	}

	/**
	 * 
	 * This method initializes jButton4
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton4() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tablemodel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmCompany.this,
								"请选择你将要打印的记录", "提示！", 0);
						return;
					}
					List list = null;
					Company compay = (Company) tablemodel.getCurrentRow();
					CustomReportDataSource ds = null;
					list = new ArrayList();
					list.add(compay);
					ds = new CustomReportDataSource(list);
					try {
						InputStream masterReportStream = FmCompany.class
								.getResourceAsStream("report/CompanyReport.jasper");
						JasperPrint jasperPrint = JasperFillManager.fillReport(
								masterReportStream, null, ds);
						DgReportViewer viewer = new DgReportViewer(jasperPrint);
						viewer.setVisible(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
				}
			});

		}
		return btnPrint;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("设置当前登陆公司");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Company obj = (Company) tablemodel.getCurrentRow();
					List companies = systemAction.findCompanies();
					for (int i = 0; i < companies.size(); i++) {
						Company company = (Company) companies.get(i);

						company.setIsCurrCompany(new Boolean(false));
						Company recompany = service.saveCompany(new Request(
								CommonVars.getCurrUser()), company);
						// tablemodel.updateRow(recompany);
					}
					if (tablemodel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmCompany.this, "请选择公司！",
								"提示", JOptionPane.WARNING_MESSAGE);
						return;
					}
					//
					obj.setIsCurrCompany(new Boolean(true));
					obj.setIsEnable(true);
					obj = service.saveCompany(
							new Request(CommonVars.getCurrUser()), obj);
					// tablemodel.updateRow(obj);
					initCompanyData();
					jLabel.setText("   当前登陆公司加工单位名称: " + obj.getName());
					jLabel.setVisible(true);
					JOptionPane.showMessageDialog(FmCompany.this, "设置当前公司成功！",
							"提示", JOptionPane.WARNING_MESSAGE);

				}
			});
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
			jButton1.setText("Ftp设置");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (FmCompany.this.tablemodel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmCompany.this,
								"    请选择公司！", "提示！",
								JOptionPane.WARNING_MESSAGE);
					}
					Company com = (Company) FmCompany.this.tablemodel
							.getCurrentRow();
					DgCompanyFtp dgftp = new DgCompanyFtp();
					dgftp.setCompany(com);
					dgftp.setVisible(true);
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnLicenseInfo() {
		if (btnLicenseInfo == null) {
			btnLicenseInfo = new JButton();
			btnLicenseInfo.setText("注册信息");
			btnLicenseInfo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgLicenseInfo dg = new DgLicenseInfo();
							dg.setVisible(true);
						}
					});
		}
		return btnLicenseInfo;
	}

	private JButton getBtnEnable() {
		if (btnEnable == null) {
			btnEnable = new JButton("启用");
			btnEnable.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Company company = (Company) tablemodel.getCurrentRow();
					if (Boolean.FALSE.equals(company.getIsEnable())) {
						company.setIsEnable(true);
						service.saveCompany(
								new Request(CommonVars.getCurrUser()), company);
						initCompanyData();
					} else {
						if (Boolean.TRUE.equals(company.getIsCurrCompany())) {
							JOptionPane.showMessageDialog(FmCompany.this,
									"当前使用公司不可以禁用");
							return;
						}
						if (JOptionPane.YES_OPTION != JOptionPane
								.showOptionDialog(getParent(), "确定禁用该公司吗？",
										"提示", JOptionPane.YES_NO_OPTION,
										JOptionPane.INFORMATION_MESSAGE, null,
										new Object[] { "是", "否" }, "否")) {
							return;
						}
						company.setIsEnable(false);
						service.saveCompany(
								new Request(CommonVars.getCurrUser()), company);
						initCompanyData();
					}
				}
			});
		}
		return btnEnable;
	}
} // @jve:decl-index=0:visual-constraint="10,10"

class CompanyAdapter extends JTableListModelAdapter {
	public CompanyAdapter() {
	}

	public List InitColumns() {
		List list = new Vector();
		list.add(addColumn("经营单位编码", "buCode", 80, String.class));
		list.add(addColumn("经营单位名称", "buName", 180, String.class));
		list.add(addColumn("加工单位编码", "code", 80, String.class));
		list.add(addColumn("加工单位名称", "name", 180, String.class));
		list.add(addColumn("加工单位管理类别", "coLevel", 60, String.class));
		// list.add(addColumn("企业性质", "ownerType.name", 60, String.class));
		list.add(addColumn("法人代表", "owner", 50, String.class));
		// list.add(addColumn("开户银行", "bank", 80, String.class));
		// list.add(addColumn("帐号", "account", 80, String.class));
		list.add(addColumn("联系电话", "tel", 80, String.class));
		list.add(addColumn("是否当前公司", "isCurrCompany", 100));
		list.add(addColumn("是否禁用", "isEnable", 100));
		return list;
	}

}

class checkBoxRenderer extends DefaultTableCellRenderer {
	JCheckBox checkBox = new JCheckBox();

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (value == null) {
			checkBox.setSelected(false);
		} else if (value.equals("")) {
			checkBox.setSelected(false);
		} else if (Boolean.valueOf(value.toString()) instanceof Boolean) {
			checkBox.setSelected(Boolean.parseBoolean(value.toString()));
		}
		checkBox.setHorizontalAlignment(JLabel.CENTER);
		checkBox.setBackground(table.getBackground());
		if (isSelected) {
			checkBox.setForeground(table.getSelectionForeground());
			checkBox.setBackground(table.getSelectionBackground());
		} else {
			checkBox.setForeground(table.getForeground());
			checkBox.setBackground(table.getBackground());
		}
		return checkBox;
	}
}

class checkBoxRenderer2 extends DefaultTableCellRenderer {
	JCheckBox checkBox = new JCheckBox();

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (value == null) {
			checkBox.setSelected(false);
		} else if (value.equals("")) {
			checkBox.setSelected(false);
		} else if (Boolean.valueOf(value.toString()) instanceof Boolean) {
			checkBox.setSelected(!Boolean.parseBoolean(value.toString()));
		}
		checkBox.setHorizontalAlignment(JLabel.CENTER);
		checkBox.setBackground(table.getBackground());
		if (isSelected) {
			checkBox.setForeground(table.getSelectionForeground());
			checkBox.setBackground(table.getSelectionBackground());
		} else {
			checkBox.setForeground(table.getForeground());
			checkBox.setBackground(table.getBackground());
		}
		return checkBox;
	}
}