package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.FptCancelBill;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Color;

public class DgFptCancelBill extends JDialogBase {
	private static final long serialVersionUID = 1L;

	private JPanel jPanel = null;

	private JTextField tfCustomUniteNumber = null;

	private JLabel jLabel = null;

	private JTextField tfTradeNumber = null;

	private JLabel jLabel1 = null;

	private JToolBar jJToolBarBar = null;

	private JComboBox cbbCanelState = null;

	private JLabel jLabel2 = null;

	private JButton btnSave = null;

	private JTableListModel tableModel = null;

	private FptManageAction fptManageAction = null; // @jve:decl-index=0:

	private int dataState = DataState.BROWSE;

	private FptCancelBill data = null;

	private JLabel jLabel3 = null;

	private JTextField tfNote = null;

	private JLabel jLabel4 = null;

	private JTextField tfAppNo = null;

	private JLabel jLabel5 = null;

	private JTextField tfOutCopNo = null;

	private JButton btnUndo = null;

	private JButton btnClose = null;

	private JButton btnEdit = null;

	// private FptCanelBill datae=null;
	/**
	 * This method initializes
	 * 
	 */
	public DgFptCancelBill() {
		super();
		initialize();
		initUIComponents();
	}

	private void initUIComponents() {
		cbbCanelState.removeAllItems();
		cbbCanelState.addItem(new ItemProperty("0", "撤消发货单"));
		cbbCanelState.addItem(new ItemProperty("1", "撤消退货单"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCanelState);
		this.cbbCanelState.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		cbbCanelState.setSelectedIndex(-1);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(389, 295));
		this.setContentPane(getJPanel());
		this.setTitle("输入要撤消的单据信息");
		fptManageAction = (FptManageAction) CommonVars
				.getApplicationContext().getBean("fptManageAction");
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(22, 111, 120, 24));
			jLabel5.setText("转出企业的内部编号：");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(22, 81, 120, 24));
			jLabel4.setText("申请单编号：");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(22, 206, 120, 25));
			jLabel3.setForeground(Color.blue);
			jLabel3.setText("备注：");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(22, 50, 120, 25));
			jLabel2.setText("选择撤消类型：");
			jLabel1 = new JLabel();
			jLabel1.setText("企业编号：");
			jLabel1.setBounds(new Rectangle(22, 176, 120, 25));
			jLabel = new JLabel();
			jLabel.setText("电子口岸统一编号：");
			jLabel.setBounds(new Rectangle(22, 143, 120, 25));
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getTfCustomUniteNumber(), BorderLayout.WEST);
			jPanel.add(jLabel, BorderLayout.NORTH);
			jPanel.add(getTfTradeNumber(), BorderLayout.EAST);
			jPanel.add(jLabel1, BorderLayout.CENTER);
			jPanel.add(getJJToolBarBar(), null);
			jPanel.add(getCbbCanelState(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(getTfNote(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(getTfAppNo(), null);
			jPanel.add(jLabel5, null);
			jPanel.add(getTfOutCopNo(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes tfCustomUniteNumber
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCustomUniteNumber() {
		if (tfCustomUniteNumber == null) {
			tfCustomUniteNumber = new JTextField();
			tfCustomUniteNumber.setBounds(new Rectangle(141, 143, 204, 25));
			tfCustomUniteNumber.setEditable(false);
		}
		return tfCustomUniteNumber;
	}

	/**
	 * This method initializes tfTradeNumber
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTradeNumber() {
		if (tfTradeNumber == null) {
			tfTradeNumber = new JTextField();
			tfTradeNumber.setBounds(new Rectangle(141, 176, 204, 25));
			tfTradeNumber.setEnabled(false);
			tfTradeNumber.setEditable(false);
		}
		return tfTradeNumber;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.setBounds(new Rectangle(2, 0, 609, 31));
			jJToolBarBar.add(getBtnEdit());
			jJToolBarBar.add(getBtnSave());
			jJToolBarBar.add(getBtnUndo());
			jJToolBarBar.add(getBtnClose());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes cbbCanelState
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCanelState() {
		if (cbbCanelState == null) {
			cbbCanelState = new JComboBox();
			cbbCanelState.setBounds(new Rectangle(141, 50, 204, 25));
			cbbCanelState.setEnabled(false);
		}
		return cbbCanelState;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if("".equals(tfNote.getText().trim())){
						JOptionPane.showMessageDialog(DgFptCancelBill.this, "请备注栏不允许为空，请输入撤销单据的原因！");
						return;
					}
					saveData();
					dataState=DataState.BROWSE;
					setState();
				}
			});
		}
		return btnSave;
	}

	private void saveData() {
		fillData(data);
		fptManageAction.saveFptCancelBill(new Request(CommonVars
				.getCurrUser()), data);
		tableModel.updateRow(data);

	}

	private void fillData(FptCancelBill fptCanelBill) {
		fptCanelBill.setAppNo(this.tfAppNo.getText().trim());
		fptCanelBill.setCopNo(this.tfOutCopNo.getText().trim());
		fptCanelBill.setSeqNo(this.tfCustomUniteNumber.getText());
		fptCanelBill.setTradeCode(this.tfTradeNumber.getText());
		fptCanelBill.setNote(this.tfNote.getText());
		if (fptCanelBill.getId() == null
				|| "".equals(fptCanelBill.getId().trim())) {
			fptCanelBill.setDeclareState(DeclareState.APPLY_POR);
		}
		if (cbbCanelState.getSelectedItem() != null) {
			fptCanelBill.setCanelSort(((ItemProperty) this.cbbCanelState
					.getSelectedItem()).getCode());
		} else {
			fptCanelBill.setCanelSort(null);
		}
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			if (tableModel != null) {
				data = (FptCancelBill) tableModel.getCurrentRow();
			}
			showHeadData(data);
			setState();
		}
		super.setVisible(b);
	}

	public void setVisibles(boolean b) {
		super.setVisible(b);
	}

	public void showHeadData(FptCancelBill fptCanelBill) {
		if (fptCanelBill == null) {
			return;
		}
		this.tfAppNo.setText(fptCanelBill.getAppNo());
		this.tfOutCopNo.setText(fptCanelBill.getCopNo());
		this.tfCustomUniteNumber.setText(fptCanelBill.getSeqNo());
		this.tfTradeNumber.setText(fptCanelBill.getTradeCode());
		this.tfNote.setText(fptCanelBill.getNote());
		this.cbbCanelState.setSelectedIndex(ItemProperty.getIndexByCode(
				fptCanelBill.getCanelSort(), cbbCanelState));
	}

	private void setState() {
		// this.tfAppNo.setEditable(dataState != DataState.BROWSE);
		// this.tfOutCopNo.setEditable(dataState != DataState.BROWSE);
		// this.tfCustomUniteNumber.setEditable(dataState != DataState.BROWSE);
		// this.tfTradeNumber.setEditable(dataState != DataState.BROWSE);
		// this.cbbCanelState.setEnabled(dataState != DataState.BROWSE);
		this.btnEdit
				.setEnabled(dataState == DataState.BROWSE
						&& (DeclareState.APPLY_POR.equals(data
								.getDeclareState()) || DeclareState.CHANGING_EXE
								.equals(data.getDeclareState())));
		this.btnSave.setEnabled(dataState != DataState.BROWSE);
		this.btnUndo.setEnabled(dataState != DataState.BROWSE);
		this.tfNote.setEnabled(dataState != DataState.BROWSE);
	}

	public void clearData() {
		this.tfAppNo.setText("");
		this.tfOutCopNo.setText("");
		this.tfCustomUniteNumber.setText("");
		this.tfTradeNumber.setText("");
		this.cbbCanelState.setSelectedItem(null);
	}

	public JTableListModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	/**
	 * This method initializes tfRemark
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfNote() {
		if (tfNote == null) {
			tfNote = new JTextField();
			tfNote.setBounds(new Rectangle(141, 206, 204, 25));
		}
		return tfNote;
	}

	public FptCancelBill getData() {
		return data;
	}

	public void setData(FptCancelBill data) {
		this.data = data;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfAppNo() {
		if (tfAppNo == null) {
			tfAppNo = new JTextField();
			tfAppNo.setBounds(new Rectangle(141, 81, 204, 25));
			tfAppNo.setEditable(false);
		}
		return tfAppNo;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfOutCopNo() {
		if (tfOutCopNo == null) {
			tfOutCopNo = new JTextField();
			tfOutCopNo.setBounds(new Rectangle(141, 112, 204, 25));
			tfOutCopNo.setEditable(false);
		}
		return tfOutCopNo;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUndo() {
		if (btnUndo == null) {
			btnUndo = new JButton();
			btnUndo.setText("取消");
			btnUndo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					showHeadData(data);
					dataState=DataState.BROWSE;
					setState();
				}
			});
		}
		return btnUndo;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnEdit;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
