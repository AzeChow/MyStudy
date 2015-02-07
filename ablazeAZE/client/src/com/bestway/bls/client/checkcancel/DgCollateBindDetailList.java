package com.bestway.bls.client.checkcancel;

import java.awt.BorderLayout;
import java.awt.Color;
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
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bls.action.BlsCheckCancelAction;
import com.bestway.bls.entity.CollateBindDetailList;
import com.bestway.bls.entity.ImpExp;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgCollateBindDetailList extends JDialogBase {

	private JToolBar jToolBar = null;
	private JButton btnEdit = null;
	// private JButton jbDelete = null;
	private JButton btnSave = null;
	private JButton btnClose = null;

	private Integer imExp; // @jve:decl-index=0:

	public Integer getImExp() {
		return imExp;
	}

	public void setImExp(Integer imExp) {
		this.imExp = imExp;
	}

	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel11 = null;
	private JLabel jLabel12 = null;
	private JLabel jLabel13 = null;
	private JTextField tfTradeCode = null;
	private JTextField tfFormId = null;
	private JCustomFormattedTextField tfGno = null;
	private JCustomFormattedTextField tfGcount = null;
	private JTextField tfNote = null;
	private JButton btnUp = null;
	private JButton btnDown = null;
	private JTableListModel tableModel = null;
	private BlsCheckCancelAction blsCheckCancelAction = null;
	private CollateBindDetailList collateBindDetailList = null; // @jve:decl-index=0:
	private int dataState = DataState.BROWSE;
	private JLabel jLabel2 = null;
	private JComboBox cbbUnit = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgCollateBindDetailList(Integer impExp) {
		super();
		this.imExp = impExp;
		blsCheckCancelAction = (BlsCheckCancelAction) CommonVars
				.getApplicationContext().getBean("blsCheckCancelAction");
		initialize();
		this.initUIComponents();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(466, 295));
		this.setTitle("核销捆绑关系核销项信息维护");
		this.setContentPane(getJContentPane());
	}
	
	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnEdit());
			// jToolBar.add(getJbDelete());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnUp());
			jToolBar.add(getBtnDown());
			jToolBar.add(getBtnClose());
		}
		return jToolBar;
	}

	public void setVisible(boolean b) {
		if (b) {
			
			if (tableModel.getCurrentRow() != null) {
				collateBindDetailList = (CollateBindDetailList) tableModel
						.getCurrentRow();
			}
			showData();
			// List dataSourceImg = this.blsCheckCancelAction
			// .findCollateBindDetailByHead(new Request(CommonVars
			// .getCurrUser()), CollateBindDetail.getId());
			// initTableImg(dataSourceImg);
			setState();
		}
		super.setVisible(b);
	}

	private void initUIComponents() {
		// 单位
		this.cbbUnit.setModel(CustomBaseModel.getInstance().getUnitModel());
		this.cbbUnit.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbUnit);
		this.cbbUnit.setSelectedItem(null);
	}

	/** show data */
	private void showData() {
		collateBindDetailList = (CollateBindDetailList) tableModel
				.getCurrentRow();
		this.tfFormId.setText(collateBindDetailList.getFormId());
		Double gcount = collateBindDetailList.getGcount() == null ? 0.0
				: collateBindDetailList.getGcount();
		this.tfGcount.setValue(gcount);
		this.tfTradeCode.setText(collateBindDetailList.getTradeCode());

		Integer gno = collateBindDetailList.getGno() == null ? 0
				: collateBindDetailList.getGno();
		this.tfGno.setValue(gno);
		this.tfNote.setText(collateBindDetailList.getNote());
		this.cbbUnit.setSelectedItem(collateBindDetailList.getUnit());

	}

	private void setState() {
		String state = DeclareState.APPLY_POR;
		if (collateBindDetailList != null) {
			state = collateBindDetailList.getCollateBindDetail()
					.getCollateBind().getDeclareState();
		}
		this.btnUp.setEnabled(tableModel.hasPreviousRow());
		this.btnDown.setEnabled(tableModel.hasNextRow());

		this.tfGno.setEnabled(dataState != DataState.BROWSE);
		this.tfNote.setEnabled(dataState != DataState.BROWSE);
		this.tfFormId.setEnabled(dataState != DataState.BROWSE);
		this.tfGcount.setEnabled(dataState != DataState.BROWSE);
		this.tfTradeCode.setEnabled(dataState != DataState.BROWSE);
		this.cbbUnit.setEnabled(dataState != DataState.BROWSE);

		// jbAdd.setEnabled((DeclareState.APPLY_POR.equals(state))); // 新增
		btnEdit.setEnabled((dataState == DataState.BROWSE)
				&& (DeclareState.APPLY_POR.equals(state)));
		// jbDelete.setEnabled((DeclareState.APPLY_POR.equals(state))); // 删除
		btnSave.setEnabled(dataState != DataState.BROWSE
				&& (DeclareState.APPLY_POR.equals(state))); // 保存
	}

	private boolean vaildatorDataIsNull() {
		Double gcount = Double.valueOf(this.tfGcount.getValue().toString());
		if (gcount == 0.0) {
			JOptionPane.showMessageDialog(null, "货物数量不可为空!!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (this.tfTradeCode.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "海关编码不可为空!!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (this.tfFormId.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "单证编号不可为空!!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (this.tfGno.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "序号不可为空!!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		return false;
	}

	/** fill data */
	private void fillData() {
		collateBindDetailList.setGno(Integer.valueOf(this.tfGno.getValue()
				.toString()));
		collateBindDetailList.setNote(this.tfNote.getText());

		collateBindDetailList.setFormId(this.tfFormId.getText());
		Double gcount = Double.valueOf(this.tfGcount.getValue().toString());
		collateBindDetailList.setGcount(gcount);
		collateBindDetailList.setTradeCode(this.tfTradeCode.getText());
		collateBindDetailList.setUnit((Unit)this.cbbUnit.getSelectedItem());
	}

	/** save data */
	private void saveData() {
		fillData();
		collateBindDetailList = blsCheckCancelAction.saveCollateBindDetailList(
				new Request(CommonVars.getCurrUser()), collateBindDetailList);
		tableModel.updateRow(collateBindDetailList);
		dataState = DataState.BROWSE;
		setState();
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("\u4fee\u6539");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnEdit;
	}

	// /**
	// * This method initializes jbDelete
	// *
	// * @return javax.swing.JButton
	// */
	// private JButton getJbDelete() {
	// if (jbDelete == null) {
	// jbDelete = new JButton();
	// jbDelete.setText("\u5220\u9664");
	// jbDelete.addActionListener(new java.awt.event.ActionListener() {
	// public void actionPerformed(java.awt.event.ActionEvent e) {
	// if (tableModelImg.getCurrentRow() == null) {
	// JOptionPane.showMessageDialog(DgCollateBindDetail2.this,
	// "请选择你要删除的资料", "确认", 1);
	// return;
	// }
	//
	// List checkHeads = tableModelImg.getCurrentRows();
	//
	// if (JOptionPane.showConfirmDialog(DgCollateBindDetail2.this,
	// "确定要删除此记录吗?\n注意：其表体将一并被删除", "确认", 0) == 0) {
	// // dzscContractCavAction.deleteAllCheckImgExg(new
	// // Request(
	// // CommonVars.getCurrUser()),checkHead);
	// blsCheckCancelAction.deleteCollateBindDetailList(
	// new Request(CommonVars.getCurrUser()),
	// checkHeads);
	// tableModelImg.deleteRows(checkHeads);
	// setState();
	// }
	// }
	// });
	// }
	// return jbDelete;
	// }

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("\u4fdd\u5b58");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!vaildatorDataIsNull()) {
						saveData();
					}
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("\u5173\u95ed");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
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
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
			jContentPane.add(getJPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(61, 132, 70, 24));
			jLabel2.setText("核扣单位");
			jLabel13 = new JLabel();
			jLabel13.setBounds(new Rectangle(59, 161, 73, 23));
			jLabel13.setText("备注");
			jLabel12 = new JLabel();
			jLabel12.setBounds(new Rectangle(60, 106, 73, 23));
			jLabel12.setForeground(Color.blue);
			jLabel12.setText("货物数量");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(60, 80, 73, 23));
			jLabel11.setForeground(Color.blue);
			jLabel11.setText("货物序号");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(60, 49, 73, 23));
			jLabel1.setForeground(Color.blue);
			if (this.imExp == ImpExp.IMP) {
				jLabel1.setText("进口报关单号");
			} else if (this.imExp == ImpExp.EXP) {
				jLabel1.setText("出口报关单号");
			} else if (this.imExp == ImpExp.IMP_EXP) {
				jLabel1.setText("入仓单号");
			}
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(60, 19, 73, 23));
			jLabel.setForeground(Color.blue);
			jLabel.setText("海关注册编码");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel11, null);
			jPanel.add(jLabel12, null);
			jPanel.add(jLabel13, null);
			jPanel.add(getTfTradeCode(), null);
			jPanel.add(getTfFormId(), null);
			jPanel.add(getTfGno(), null);
			jPanel.add(getTfGcount(), null);
			jPanel.add(getTfNote(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getCbbUnit(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes tfTradeCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTradeCode() {
		if (tfTradeCode == null) {
			tfTradeCode = new JTextField();
			// jTextField.sete
			tfTradeCode.setBounds(new Rectangle(154, 19, 172, 23));
		}
		return tfTradeCode;
	}

	/**
	 * This method initializes tfFormId
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFormId() {
		if (tfFormId == null) {
			tfFormId = new JTextField();
			tfFormId.setBounds(new Rectangle(154, 49, 172, 25));
		}
		return tfFormId;
	}

	/**
	 * This method initializes tfGno
	 * 
	 * @return javax.swing.JTextField
	 */
	private JCustomFormattedTextField getTfGno() {
		if (tfGno == null) {
			tfGno = new JCustomFormattedTextField();
			tfGno.setBounds(new Rectangle(154, 80, 172, 22));
		}
		return tfGno;
	}

	/**
	 * This method initializes tfGcount
	 * 
	 * @return javax.swing.JTextField
	 */
	private JCustomFormattedTextField getTfGcount() {
		if (tfGcount == null) {
			tfGcount = new JCustomFormattedTextField();
			tfGcount.setBounds(new Rectangle(154, 106, 172, 22));
		}
		return tfGcount;
	}

	/**
	 * This method initializes tfNote
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfNote() {
		if (tfNote == null) {
			tfNote = new JTextField();
			tfNote.setBounds(new Rectangle(151, 163, 172, 21));
		}
		return tfNote;
	}

	/**
	 * This method initializes btnUp
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUp() {
		if (btnUp == null) {
			btnUp = new JButton();
			btnUp.setText("\u4e0a\u7b14");
			btnUp.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!tableModel.previousRow()) {
						btnUp.setEnabled(false);
						btnDown.setEnabled(true);
					} else {
						btnUp.setEnabled(true);
						btnDown.setEnabled(true);
					}
					showData();
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnUp;
	}

	/**
	 * This method initializes btnDown
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDown() {
		if (btnDown == null) {
			btnDown = new JButton();
			btnDown.setText("\u4e0b\u7b14");
			btnDown.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!tableModel.nextRow()) {
						btnUp.setEnabled(true);
						btnDown.setEnabled(false);
					} else {
						btnUp.setEnabled(true);
						btnDown.setEnabled(true);
					}
					showData();
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnDown;
	}

	public void setTableModel(JTableListModel tableModelImg) {
		this.tableModel = tableModelImg;

	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbUnit() {
		if (cbbUnit == null) {
			cbbUnit = new JComboBox();
			cbbUnit.setBounds(new Rectangle(152, 133, 172, 24));
		}
		return cbbUnit;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
