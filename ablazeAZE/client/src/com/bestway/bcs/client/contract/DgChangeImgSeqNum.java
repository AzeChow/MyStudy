package com.bestway.bcs.client.contract;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Color;

public class DgChangeImgSeqNum extends JDialogBase {
	// public static int CHANGE_IMG=0;//变更料件
	//	
	// public static int CHANGE_EXG=1;//变更成品

	private JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private JFormattedTextField tfOldSeqNum = null;

	private JFormattedTextField tfNewSeqNum = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:visual-constraint=""

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:visual-constraint=""

	private ContractAction contractAction = null;

	private ContractImg obj = null;

	private JTableListModel tableModel = null;

	private boolean isOk = false;

	private int beginSeqNum = 1;

	private JLabel jLabel2 = null;
	private Contract contract = null;
	private String modifyMark =null;

	public void setBeginSeqNum(int beginSeqNum) {
		this.beginSeqNum = beginSeqNum;
	}

	public JTableListModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModel tableModelImg) {
		this.tableModel = tableModelImg;
	}

	public ContractAction getContractAction() {
		return contractAction;
	}

	public void setContractAction(ContractAction contractAction) {
		this.contractAction = contractAction;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgChangeImgSeqNum() {
		super();
		initialize();
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			this.obj = (ContractImg) tableModel.getCurrentRow();
			this.tfOldSeqNum.setValue(obj.getSeqNum());
			//this.jLabel2.setText("新序号从" + beginSeqNum + "开始");
			this.jLabel2.setText("不能有相同的备案序号！");
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(205, 181));
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("变更料件序号");

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(28, 76, 137, 23));
			jLabel2.setForeground(Color.blue);
			jLabel2.setText("JLabel");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(26, 49, 40, 22));
			jLabel1.setText("新序号");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(26, 17, 40, 23));
			jLabel.setText("旧序号");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getTfOldSeqNum(), null);
			jContentPane.add(getTfNewSeqNum(), null);
			jContentPane.add(jLabel2, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(new Rectangle(25, 108, 63, 26));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tfNewSeqNum.getValue() == null) {
						JOptionPane.showMessageDialog(DgChangeImgSeqNum.this,
								"请输入新序号", "提示", JOptionPane.OK_OPTION);
						return;
					}
					int oldSeqNum = (tfOldSeqNum.getValue() == null ? 0
							: Integer
									.valueOf(tfOldSeqNum.getValue().toString()));
					int newSeqNum = (tfNewSeqNum.getValue() == null ? 0
							: Integer
									.valueOf(tfNewSeqNum.getValue().toString()));
					if (newSeqNum == oldSeqNum) {
						JOptionPane.showMessageDialog(DgChangeImgSeqNum.this,
								"新序号不能与旧序号相等", "提示", JOptionPane.OK_OPTION);
						return;
					}
//					if (newSeqNum < beginSeqNum) {
//						JOptionPane.showMessageDialog(DgChangeImgSeqNum.this,
//								"新序号不能小于" + beginSeqNum, "提示",
//								JOptionPane.OK_OPTION);
//						return;
//					}
					// obj.setSeqNum(Integer.valueOf(tfNewSeqNum.getValue().toString()));
					/** **/
					contractAction.changeContractImgSeqNum(new Request(
							CommonVars.getCurrUser()), contract,obj, Integer
							.valueOf(tfNewSeqNum.getValue().toString()),modifyMark);
					
					// tableModel.updateRow(obj);
					isOk = true;
					dispose();
				}
			});
		}
		return btnOk;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new Rectangle(109, 108, 63, 26));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfOldSeqNum() {
		if (tfOldSeqNum == null) {
			tfOldSeqNum = new JFormattedTextField();
			tfOldSeqNum.setBounds(new java.awt.Rectangle(68, 17, 98, 26));
			tfOldSeqNum.setEditable(false);
		}
		return tfOldSeqNum;
	}

	/**
	 * This method initializes jFormattedTextField1
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfNewSeqNum() {
		if (tfNewSeqNum == null) {
			tfNewSeqNum = new JFormattedTextField();
			tfNewSeqNum.setBounds(new java.awt.Rectangle(68, 47, 98, 26));
			tfNewSeqNum.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfNewSeqNum;
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
			defaultFormatterFactory.setNullFormatter(getNumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	/**
	 * This method initializes numberFormatter
	 * 
	 * @return javax.swing.text.NumberFormatter
	 */
	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
		}
		return numberFormatter;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public String getModifyMark() {
		return modifyMark;
	}

	public void setModifyMark(String modifyMark) {
		this.modifyMark = modifyMark;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
