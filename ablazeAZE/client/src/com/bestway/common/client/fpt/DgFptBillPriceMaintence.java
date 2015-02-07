package com.bestway.common.client.fpt;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.FptBillPriceMaintenance;

public class DgFptBillPriceMaintence extends DgCommon {
	public FptManageAction fptManageAction = null;

	private JTableListModel tableModel = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JTextField tfName = null;

	private JTextField tfCode = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private FptBillPriceMaintenance billPriceMaintenance = null; // @jve:decl-index=0:

	private JFormattedTextField tfBeforPrice = null;

	private JFormattedTextField tfAtferPrice = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgFptBillPriceMaintence() {
		super();
		initialize();
		this.fptManageAction = (FptManageAction) CommonVars
				.getApplicationContext().getBean("fptManageAction");
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(325, 247));
		this.setContentPane(getJPanel());

	}

	@Override
	public void setVisible(boolean isFlag) {
		billPriceMaintenance = (FptBillPriceMaintenance) getTableModel()
				.getCurrentRow();
		showData();
		super.setVisible(isFlag);
	}

	private void showData() {
		tfName.setText(billPriceMaintenance.getPtName());
		tfCode.setText(billPriceMaintenance.getComplex().getCode());
		tfBeforPrice.setValue(billPriceMaintenance.getUnitPrice());
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(46, 126, 82, 22));
			jLabel3.setText("更改后单价");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(46, 91, 82, 22));
			jLabel2.setText("更改前单价");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(46, 59, 82, 22));
			jLabel1.setText("商品编码");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(46, 25, 82, 22));
			jLabel.setText("商品名称");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(getTfName(), null);
			jPanel.add(getTfCode(), null);
			jPanel.add(getBtnOk(), null);
			jPanel.add(getBtnCancel(), null);
			jPanel.add(getTfBeforPrice(), null);
			jPanel.add(getTfAtferPrice(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes tfName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(new Rectangle(145, 25, 132, 22));
			tfName.setEditable(false);
		}
		return tfName;
	}

	/**
	 * This method initializes tfCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCode() {
		if (tfCode == null) {
			tfCode = new JTextField();
			tfCode.setBounds(new Rectangle(145, 59, 132, 22));
			tfCode.setEditable(false);
		}
		return tfCode;
	}

	/**
	 * This method initializes btnOk
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(new Rectangle(64, 172, 79, 25));
			btnOk.setText("确      定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						getPriceValue();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(
								DgFptBillPriceMaintence.this, "请输入正确的数字！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					new EiditThread().start();
				}
			});
		}
		return btnOk;
	}

	private Double getPriceValue() {
		return new Double(tfAtferPrice.getText());
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new Rectangle(184, 171, 79, 25));
			btnCancel.setText("退      出");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes tfBeforPrice
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfBeforPrice() {
		if (tfBeforPrice == null) {
			tfBeforPrice = new JFormattedTextField();
			tfBeforPrice.setBounds(new Rectangle(145, 91, 132, 22));
			tfBeforPrice.setEditable(false);
		}
		return tfBeforPrice;
	}

	/**
	 * This method initializes tfAtferPrice
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfAtferPrice() {
		if (tfAtferPrice == null) {
			tfAtferPrice = new JFormattedTextField();
			tfAtferPrice.setBounds(new Rectangle(145, 126, 132, 22));
		}
		return tfAtferPrice;
	}

	public JTableListModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	class EiditThread extends Thread {
		@Override
		public void run() {
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正在修改单价，请稍后...");
				fptManageAction.editBillPriceMaintenancePrice(
						new Request(CommonVars.getCurrUser()),
						billPriceMaintenance.getScmCoc(), billPriceMaintenance
								.getSeqNum(), billPriceMaintenance.getComplex()
								.getCode(), getPriceValue(),
						billPriceMaintenance.getProjectType(),
						billPriceMaintenance.getIsCustomer());
				billPriceMaintenance.setUnitPrice(getPriceValue());
				List list = new ArrayList();
				list.add(billPriceMaintenance);
				fptManageAction.saveBillPriceMaintenance(
						new Request(CommonVars.getCurrUser()), list);
				tableModel.updateRow(billPriceMaintenance);
			} catch (Exception e) {

				JOptionPane.showMessageDialog(DgFptBillPriceMaintence.this,
						"修改过程中失败:" + e.getMessage(), "提示",
						JOptionPane.ERROR_MESSAGE);
			} finally {
				DgFptBillPriceMaintence.this.dispose();
				CommonProgress.closeProgressDialog();
			}
		}
	}
} // @jve:decl-index=0:visual-constraint="242,56"

