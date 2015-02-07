package com.bestway.bcs.client.contract;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgContractNoUpdate extends JDialogBase {

	private final JPanel contentPanel = new JPanel();
	private JTextField tfImpContractNo;
	private JTextField tfExpContractNo;
	private Contract contract;
	private ContractAction contractAction = null;
	private boolean isOk = false;
	private boolean isCheck = false;

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	/**
	 * Create the dialog.
	 */
	public DgContractNoUpdate() {
		super();
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		initialize();
	}

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// try {
	// DgContractNoUpdate dialog = new DgContractNoUpdate();
	// dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	// dialog.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	/**
	 * 设置控件的可见性
	 */

	public void setVisible(boolean isFalg) {
		if (isFalg) {
			showData();
		}
		super.setVisible(isFalg);
	}

	private void showData() {
		if (contract != null) {
			String impContractNo = contract.getImpContractNo() == null ? ""
					: contract.getImpContractNo();
			String expContractNo = contract.getExpContractNo() == null ? ""
					: contract.getExpContractNo();
			this.tfImpContractNo.setText(impContractNo);
			this.tfExpContractNo.setText(expContractNo);
		}
	}

	private void fillData() {
		if (isCheck = checkData()) {
			contract.setImpContractNo(tfImpContractNo.getText().trim());
			contract.setExpContractNo(tfExpContractNo.getText().trim());
		}
	}

	private boolean checkData() {
		String impContractNo = tfImpContractNo.getText().trim();
		String expContractNo = tfExpContractNo.getText().trim();
		boolean isExist = contractAction.checkImpContractNoIsExist(new Request(
				CommonVars.getCurrUser()), impContractNo);
		// if(isExist){
		// JOptionPane.showMessageDialog(this, "已经存在该进口合同号", "提示", 2);
		// return false;
		// }
		if (isExist) {
			if (JOptionPane.YES_NO_OPTION != JOptionPane.showOptionDialog(
					getParent(), "已经存在该进口合同号,确定继续变更合同？", "提示",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, new Object[] { "是(Y)", "否(N)" }, "否(N)")) {
				return false;
			}
		}
		isExist = contractAction.checkExpContractNoIsExist(new Request(
				CommonVars.getCurrUser()), expContractNo);
		// if(isExist){
		// JOptionPane.showMessageDialog(this, "已经存在该出口合同号", "提示", 2);
		// return false;
		// }
		if (isExist) {
			if (JOptionPane.YES_NO_OPTION != JOptionPane.showOptionDialog(
					getParent(), "已经存在该出口合同号,确定继续变更合同？", "提示",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, new Object[] { "是(Y)", "否(N)" }, "否(N)")) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 初始化面板数据
	 * 
	 */
	private void initialize() {
		setTitle("通关手册合同号修改");
		setBounds(100, 100, 363, 216);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("进口合同号");
		lblNewLabel.setBounds(59, 40, 73, 15);
		contentPanel.add(lblNewLabel);

		JLabel label = new JLabel("出口合同号");
		label.setBounds(59, 77, 73, 15);
		contentPanel.add(label);

		tfImpContractNo = new JTextField();
		tfImpContractNo.setBounds(130, 37, 111, 21);
		contentPanel.add(tfImpContractNo);
		tfImpContractNo.setColumns(10);
		tfImpContractNo.getDocument().addDocumentListener(
				new DocumentListener() {

					public void insertUpdate(DocumentEvent e) {
						tfExpContractNo.setText(tfImpContractNo.getText());
					}

					public void removeUpdate(DocumentEvent e) {
						tfExpContractNo.setText(tfImpContractNo.getText());
					}

					public void changedUpdate(DocumentEvent e) {
						tfExpContractNo.setText(tfImpContractNo.getText());
					}

				});

		tfExpContractNo = new JTextField();
		tfExpContractNo.setBounds(130, 74, 111, 21);
		contentPanel.add(tfExpContractNo);
		tfExpContractNo.setColumns(10);
		tfExpContractNo.setEnabled(false);

		JLabel label_1 = new JLabel(
				"<html>注:新签合同,合同做变更时合同号填写<br/>按原进/出口合同号+2位的变更次数<html>");
		label_1.setBounds(28, 102, 213, 33);
		contentPanel.add(label_1);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("确定");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						changingData();
						isOk = true;
						DgContractNoUpdate.this.dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("取消");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						isOk = false;
						DgContractNoUpdate.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	/**
	 * 变更合同
	 * 
	 */
	private void changingData() {
		if (JOptionPane.showConfirmDialog(this, "是否确定变更合同号!", "提示",
				JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
			return;
		}
		fillData();
	}
}
