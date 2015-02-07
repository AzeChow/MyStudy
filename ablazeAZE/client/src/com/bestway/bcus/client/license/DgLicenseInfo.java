package com.bestway.bcus.client.license;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.license.action.LicenseAction;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgLicenseInfo extends JDialogBase {

	private JPanel jContentPane = null;

	private JScrollPane jScrollPane = null;

	private LicenseAction licenseAction = null;

	private JTable jTable = null;

	public DgLicenseInfo() {
		super();
		initialize();
	}

	public DgLicenseInfo(boolean isModal) {
		super(isModal);
		initialize();
	}

	public DgLicenseInfo(JFrame owner, boolean isModal) {
		super(owner, isModal);
		initialize();
	}

	public DgLicenseInfo(Dialog owner, boolean isModal) {
		super(owner, isModal);
		initialize();
	}

	/**
	 * 获取注册的license详细信息
	 * 
	 * @return
	 */
	public List getLicenseInfoString() {
		return licenseAction.getLicenseInfoString();
	}

	public void setVisible(boolean b) {
		if (b) {
			List list = getLicenseInfoString();
			if (list.size() <= 0) {
				JOptionPane.showMessageDialog(DgLicenseInfo.this, "系统没有注册文件",
						"提示", JOptionPane.INFORMATION_MESSAGE);
				dispose();
				return;
			}
			JTableListModel.dataBind(this.getJTable(), list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List<Object> list = (List<Object>) (new Vector());
							list.add(addColumn("公司名称", "companyName", 200));
							//BY SXK
//							list.add(addColumn("版本", "edition", 50));
//							list.add(addColumn("试用到期日", "maturityDate", 80));
							list.add(addColumn("客户端允许最大数", "maxNum", 100));
							list.add(addColumn("已经登陆客户端IP", "loginedIP", 100));
							list.add(addColumn("已经登陆客户端数量", "loginedNum", 120));							
							return list;
						}
					});
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(570,396));
		this.setContentPane(getJContentPane());
		this.setTitle("License信息");
		licenseAction = (LicenseAction) CommonVars.getApplicationContext()
				.getBean("licenseAction");
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
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

} // @jve:decl-index=0:visual-constraint="10,10"
