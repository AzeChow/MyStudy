package com.bestway.client.fixtureonorder;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.fixtureonorder.action.FixtureContractAction;
import com.bestway.fixtureonorder.entity.FixtureContract;
import com.bestway.fixtureonorder.entity.FixtureContractItems;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author fhz
 * 
 */
public class DgChangeItemsNameSpec extends JDialogBase {
	private JPanel jContentPane = null;

	private JTextField tfseqNum = null;

	private JTextField tfcomplex = null;

	private JTextField tfOldName = null;

	private JTextField tfOldSpec = null;

	private JTextField tfNewSpec = null;

	private JButton btnSave = null;

	private JButton btnCancel = null;

	private int dataState = DataState.EDIT;

	private JTextField tfNewName = null;

	private FixtureContract fixtureContract = null;

	private FixtureContractItems item = null;

	private JTableListModel tableModel = null; // 

	private FixtureContractAction fixtureContractAction = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgChangeItemsNameSpec() {
		super();
		initialize();
	}

	public void setVisible(boolean b) {
		if (b) {
			item = (FixtureContractItems) tableModel.getCurrentRow();
			showData();
			setState();
		}
		super.setVisible(b);
	}

	private void showData() {

		this.setTitle("设备名称规格修改");
		if (item.getSeqNum() == null) {
			JOptionPane.showConfirmDialog(this, "序号为空，数据有误!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		} else
			this.tfseqNum.setText(item.getSeqNum().toString());
		this.tfcomplex.setText(item.getComplex() == null ? "" : item
				.getComplex().getCode());
		this.tfOldName.setText(item.getName() == null ? "" : item.getName());
		this.tfNewName.setText(item.getName() == null ? "" : item.getName());
		this.tfOldSpec.setText(item.getSpec() == null ? "" : item.getSpec());
		this.tfNewSpec.setText(item.getSpec() == null ? "" : item.getSpec());

	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setTitle("设备名称规格修改");
		this.setSize(new java.awt.Dimension(401, 254));
		this.setLocation(220, 180);
		this.setResizable(false);
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
	}

	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			JLabel jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(32, 147, 61, 25));
			jLabel5.setText("修改后规格");
			JLabel jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(32, 116, 61, 23));
			jLabel4.setText("修改前规格");

			JLabel jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(32, 86, 61, 21));
			jLabel3.setText("修改后名称");
			JLabel jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(32, 57, 61, 23));
			jLabel2.setText("修改前名称");
			JLabel jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(185, 24, 49, 24));
			jLabel1.setText("商品编码");
			JLabel jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(32, 25, 59, 24));
			jLabel.setText("商品序号");
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getTfSeqNum(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getTfcomplex(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getTfOldName(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(getTfOldSpec(), null);
			jContentPane.add(getTfNewSpec(), null);
			jContentPane.add(getBtnSave(), null);
			jContentPane.add(getBtnCancel(), null);
			// jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getTfNewName(), null);
			jContentPane.add(getTfNewSpec(), null);
		}
		return jContentPane;
	}

	private void setState() {
		this.tfseqNum.setEditable(false);
		this.tfcomplex.setEditable(false);
		this.tfNewSpec.setEditable(this.dataState != DataState.BROWSE);
		this.tfOldSpec.setEditable(false);
		this.tfOldName.setEditable(false);
		this.tfNewName.setEditable(this.dataState != DataState.BROWSE);
		this.btnSave.setEnabled(this.dataState != DataState.BROWSE);
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSeqNum() {
		if (tfseqNum == null) {
			tfseqNum = new JTextField();
			tfseqNum.setBounds(new java.awt.Rectangle(97, 25, 82, 25));
		}
		return tfseqNum;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfcomplex() {
		if (tfcomplex == null) {
			tfcomplex = new JTextField();
			tfcomplex.setBounds(new java.awt.Rectangle(233, 24, 124, 24));
		}
		return tfcomplex;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfOldName() {
		if (tfOldName == null) {
			tfOldName = new JTextField();
			tfOldName.setBounds(new java.awt.Rectangle(97, 58, 260, 24));
		}
		return tfOldName;
	}

	/**
	 * This method initializes jTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfOldSpec() {
		if (tfOldSpec == null) {
			tfOldSpec = new JTextField();
			tfOldSpec.setBounds(new java.awt.Rectangle(97, 117, 260, 22));
		}
		return tfOldSpec;
	}

	/**
	 * This method initializes jTextField5
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfNewSpec() {
		if (tfNewSpec == null) {
			tfNewSpec = new JTextField();
			tfNewSpec.setBounds(new java.awt.Rectangle(97, 147, 260, 24));
		}
		return tfNewSpec;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton("保存");
			btnSave.setBounds(new java.awt.Rectangle(68, 181, 63, 25));
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					saveData();
					close();

				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("取 消");
			btnCancel.setBounds(new java.awt.Rectangle(257, 179, 65, 26));
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					close();
				}
			});
		}
		return btnCancel;
	}

	private void close() {
		this.dispose();
	}

	private void saveData() {
		fixtureContractAction = (FixtureContractAction) CommonVars
				.getApplicationContext().getBean("fixtureContractAction");
		int seqNum = (Integer.valueOf(this.tfseqNum.getText().toString())
				.intValue());
		FixtureContractItems contractItems = (FixtureContractItems) tableModel
				.getCurrentRow();
		String newName = (this.tfNewName.getText() == null ? ""
				: this.tfNewName.getText());
		String newSpec = (this.tfNewSpec.getText() == null ? ""
				: this.tfNewSpec.getText());
		Object obj = this.fixtureContractAction.changeContractCommNameSpec(
				new Request(CommonVars.getCurrUser()), this.fixtureContract,
				contractItems, seqNum, newName, newSpec);
		tableModel.updateRow(obj);
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfNewName() {
		if (tfNewName == null) {
			tfNewName = new JTextField();
			tfNewName.setBounds(new java.awt.Rectangle(97, 87, 260, 23));
		}
		return tfNewName;
	}

	public void setFixtureContract(FixtureContract contract) {
		this.fixtureContract = contract;
	}
	
	public JTableListModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

} // @jve:decl-index=0:visual-constraint="21,10"
