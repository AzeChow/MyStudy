/*
 * Created on 2004-7-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bls.client.storagebill;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bls.action.StorageBillAction;
import com.bestway.bls.entity.StorageBillBefore;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Color;

/**
 * 仓单表体信息--据归并前界面  checked  by kcb   2009/1/10
 * 
 * @author kangbo
 */
public class DgStorageBillBefore extends JDialogBase {

	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JTextField tfPartNo = null;
	private JLabel jLabel1 = null;
	private JTextField tfSubpoenaNo = null;
	private JLabel jLabel2 = null;
	private JTextField tfPartNameE = null;
	private JLabel jLabel3 = null;
	private JTextField tfPartNameC = null;
	private JPanel jPanel1 = null;
	private JToolBar jJToolBarBar = null;
	private JButton btnEdit = null;
	private JButton btnSave = null;
	private JButton btnCancel = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JTextField tfClor = null;
	private JCustomFormattedTextField tfDetailQty = null;
	private int dataSate = DataState.ADD;
	private StorageBillAction storageBillAction;

	public int getDataSate() {
		return dataSate;
	}

	public void setDataSate(int dataSate) {
		this.dataSate = dataSate;
	}

	public StorageBillBefore getStorageBillBefore() {
		return storageBillBefore;
	}

	public void setStorageBillBefore(StorageBillBefore storageBillBefore) {
		this.storageBillBefore = storageBillBefore;
	}

	public JTableListModel getFathertableModel() {
		return fathertableModel;
	}

	public void setFathertableModel(JTableListModel fathertableModel) {
		this.fathertableModel = fathertableModel;
	}

	private StorageBillBefore storageBillBefore = null; // @jve:decl-index=0:
	private JTableListModel fathertableModel;
	private JButton btnPrevious = null;
	private JButton btnNext = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgStorageBillBefore() {
		super();
		storageBillAction = (StorageBillAction) CommonVars
				.getApplicationContext().getBean("storageBillAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(350, 285));
		this.setContentPane(getJPanel1());
		this.setTitle("仓单原始清单");

	}

	@Override
	public void setVisible(boolean f) {
		if (f) {
			showData();
			setState();
		}
		super.setVisible(f);
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(36, 109, 79, 22));
			jLabel5.setText("数量");
			jLabel5.setForeground(Color.blue);
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(36, 137, 79, 22));
			jLabel4.setText("颜色");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(36, 52, 79, 22));
			jLabel3.setForeground(Color.blue);
			jLabel3.setText("中文名称");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(36, 81, 79, 22));
			jLabel2.setText("英文名称");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(36, 165, 79, 22));
			jLabel1.setText("送货传票");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(36, 21, 79, 22));
			jLabel.setForeground(Color.blue);
			jLabel.setText("企业内部货号");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getJTextField(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getJTextField1(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getJTextField2(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getJTextField3(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(jLabel5, null);
			jPanel.add(getJTextField4(), null);
			jPanel.add(getJTextField5(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (tfPartNo == null) {
			tfPartNo = new JTextField();
			tfPartNo.setBounds(new Rectangle(116, 22, 186, 22));
			tfPartNo.setEditable(false);
		}
		return tfPartNo;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (tfSubpoenaNo == null) {
			tfSubpoenaNo = new JTextField();
			tfSubpoenaNo.setBounds(new Rectangle(116, 165, 186, 22));
		}
		return tfSubpoenaNo;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField2() {
		if (tfPartNameE == null) {
			tfPartNameE = new JTextField();
			tfPartNameE.setBounds(new Rectangle(116, 81, 186, 22));
		}
		return tfPartNameE;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField3() {
		if (tfPartNameC == null) {
			tfPartNameC = new JTextField();
			tfPartNameC.setBounds(new Rectangle(116, 52, 186, 22));
		}
		return tfPartNameC;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJJToolBarBar(), BorderLayout.NORTH);
			jPanel1.add(getJPanel(), BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.add(getJButton());
			jJToolBarBar.add(getJButton1());
			jJToolBarBar.add(getJButton3());
			jJToolBarBar.add(getJButton4());
			jJToolBarBar.add(getJButton2());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataSate = DataState.EDIT;
					setState();
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!checkData()) {
						return;
					}
					fillData();
					storageBillBefore = (StorageBillBefore) storageBillAction
							.saveOrUpdateObject(new Request(CommonVars
									.getCurrUser()), storageBillBefore);
					fathertableModel.updateRow(storageBillBefore);
					dataSate = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnSave;
	}

	public boolean checkData() {
		if (tfPartNo.getText() == null
				|| tfPartNo.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(DgStorageBillBefore.this,
					"企业内部货号必需填写!", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (tfPartNameC.getText() == null
				|| tfPartNameC.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(DgStorageBillBefore.this,
					"中文名称必需填写!", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("关闭");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes jTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField4() {
		if (tfClor == null) {
			tfClor = new JTextField();
			tfClor.setBounds(new Rectangle(116, 137, 186, 22));
		}
		return tfClor;
	}

	/**
	 * This method initializes jTextField5
	 * 
	 * @return javax.swing.JTextField
	 */
	private JCustomFormattedTextField getJTextField5() {
		if (tfDetailQty == null) {
			tfDetailQty = new JCustomFormattedTextField();
			tfDetailQty.setBounds(new Rectangle(116, 109, 186, 22));
			CustomFormattedTextFieldUtils.setFormatterFactory(tfDetailQty, 4);
		}
		return tfDetailQty;
	}

	private void setState() {
		tfSubpoenaNo.setEditable(DataState.BROWSE != dataSate);
		tfClor.setEditable(DataState.BROWSE != dataSate);
		tfPartNameE.setEditable(DataState.BROWSE != dataSate);
		tfPartNameC.setEditable(DataState.BROWSE != dataSate);
		tfDetailQty.setEditable(DataState.BROWSE != dataSate);
		btnEdit.setEnabled(DataState.BROWSE == dataSate);
		btnSave.setEnabled(DataState.BROWSE != dataSate);
		btnNext.setEnabled(fathertableModel.hasNextRow());
		btnPrevious.setEnabled(fathertableModel.previousRow());
	}

	private void fillData() {
		storageBillBefore.setSubpoenaNo(tfSubpoenaNo.getText());
		storageBillBefore.setClor(tfClor.getText());
		storageBillBefore.setPartNameE(tfPartNameE.getText());
		storageBillBefore.setPartNameC(tfPartNameC.getText());
		storageBillBefore.setDetailQty(Double
				.parseDouble(tfDetailQty.getValue() == null ? "0.0"
						: tfDetailQty.getValue().toString()));
	}

	private void showData() {
		tfPartNo.setText(storageBillBefore.getPartNo().getPtNo());
		tfSubpoenaNo.setText(storageBillBefore.getSubpoenaNo());
		tfClor.setText(storageBillBefore.getClor());
		tfPartNameE.setText(storageBillBefore.getPartNameE());
		tfPartNameC.setText(storageBillBefore.getPartNameC());
		tfDetailQty.setValue(storageBillBefore.getDetailQty());

	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setText("上笔");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					 previous();
				}
			});
		}
		return btnPrevious;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setText("下笔");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					next() ;
				}
			});
		}
		return btnNext;
	}

	private void next() {
		if (fathertableModel.hasNextRow()) {
			fathertableModel.nextRow();
			storageBillBefore = (StorageBillBefore) fathertableModel
					.getCurrentRow();
			showData();
			setState();
		}
	}

	private void previous() {
		if (fathertableModel.hasPreviousRow()) {
			fathertableModel.previousRow();
			storageBillBefore = (StorageBillBefore) fathertableModel
					.getCurrentRow();
			showData();
			setState();
		}
	}
} // @jve:decl-index=0:visual-constraint="-29,5"
