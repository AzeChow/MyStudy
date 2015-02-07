package com.bestway.client.fixasset;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.AgreementState;
import com.bestway.fixasset.action.FixAssetAction;
import com.bestway.fixasset.entity.Agreement;
import com.bestway.fixasset.entity.AgreementCommInfo;
import com.bestway.fixasset.entity.FixassetLocation;
import com.bestway.fixtureonorder.action.CheckFixAuthorityAction;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

public class FmAgreement extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnImport = null;

	private JButton btnAdd = null;

	private JScrollPane jScrollPane = null;

	private JTable tbAgreement = null;

	private JButton btnDelete = null;

	private JTableListModel tableModel;

	private FixAssetAction fixAssetAction;

	private CheckFixAuthorityAction checkFixAuthorityAction = null;

	private JButton btnEdit = null;

	private JButton btnClose = null;

	private JButton btnPOR = null;

	private JButton btnAddMoney = null;

	public FmAgreement() {
		super();
		initialize();
		fixAssetAction = (FixAssetAction) CommonVars.getApplicationContext()
				.getBean("fixAssetAction");
		checkFixAuthorityAction = (CheckFixAuthorityAction) CommonVars
				.getApplicationContext().getBean("checkFixAuthorityAction");
		List list = fixAssetAction.findAgreement(new Request(CommonVars
				.getCurrUser()));
		initTable(list);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(734, 360));
		this.setTitle("设备批文协议");
		this.setContentPane(getJContentPane());

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
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnImport());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnPOR());
			jToolBar.add(getJButton());
			jToolBar.add(getBtnClose());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImport() {
		if (btnImport == null) {
			btnImport = new JButton();
			btnImport.setText("导入");
			btnImport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction.checkImportAgreement(new Request(
							CommonVars.getCurrUser()));
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmAgreement.this,
								"请选择你要导入设备明细的批文", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}
					Agreement agreement = (Agreement) tableModel
							.getCurrentRow();
					DgImportAgreementCommInfo dg = new DgImportAgreementCommInfo();
					dg.setChange(false);
					dg.setAgreement(agreement);
					dg.setVisible(true);
					List list = fixAssetAction.findAgreement(new Request(
							CommonVars.getCurrUser()));
					initTable(list);
				}
			});
		}
		return btnImport;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction.checkAddAgreement(new Request(
							CommonVars.getCurrUser()));
					Agreement agreement = fixAssetAction
							.addAgreement(new Request(CommonVars.getCurrUser()));
					tableModel.addRow(agreement);
					DgAgreement dg = new DgAgreement();
					dg.setAgreement(agreement);
					dg.setVisible(true);
					agreement = dg.getAgreement();
					tableModel.updateRow(agreement);
				}
			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbAgreement());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbAgreement() {
		if (tbAgreement == null) {
			tbAgreement = new JTable();
			tbAgreement.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbAgreement
									.getModel();
							if (tableModel == null
									|| tableModel.getCurrentRow() == null) {
								return;
							}
							setState((Agreement) tableModel.getCurrentRow());
						}
					});
		}
		return tbAgreement;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction.checkDeleteAgreement(new Request(
							CommonVars.getCurrUser()));
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmAgreement.this,
								"请选择你要删除的批文", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}
					if (JOptionPane.showConfirmDialog(FmAgreement.this,
							"请选择你要删除的批文", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					Agreement agreement = (Agreement) tableModel
							.getCurrentRow();
					fixAssetAction.deleteAgreement(new Request(CommonVars
							.getCurrUser()), agreement);
					tableModel.deleteRow(agreement);
				}
			});
		}
		return btnDelete;
	}

	private void initTable(List list) {
		JTableListModel.dataBind(tbAgreement, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<Object> list = (List<Object>) (new Vector());
						list.add(addColumn("协议批文号", "sancEmsNo", 100));
						list.add(addColumn("确认书编号 ", "emsNo", 100));
						list.add(addColumn("协议状态", "declareState", 100));
						list.add(addColumn("经营单位代码", "tradeCode", 100));
						list.add(addColumn("经营单位名称", "tradeName", 100));
						list.add(addColumn("开始有效期", "beginDate", 100));
						list.add(addColumn("结束有效期", "endDate", 100));
						list.add(addColumn("审批日期", "approveDate", 100));
						list.add(addColumn("联系人", "linkMan", 100));
						list.add(addColumn("联系电话", "linkTel", 100));
						list.add(addColumn("币制", "curr.name", 100));
						list.add(addColumn("项目用汇额", "totalMoney", 100));
						list.add(addColumn("变更累计余额", "changeRemainMoney", 100));
						list.add(addColumn("企业地址", "address", 100));
						return list;
					}
				});
		tableModel = (JTableListModel) tbAgreement.getModel();
		tbAgreement.getColumnModel().getColumn(3).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							str = AgreementState.getStateDesc(Integer
									.parseInt(value.toString()));
						}
						super.setText(str);
						return this;
					}
				});
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
					checkFixAuthorityAction.checkEditAgreement(new Request(
							CommonVars.getCurrUser()));
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmAgreement.this,
								"请选择你要修改的批文", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}
					Agreement agreement = (Agreement) tableModel
							.getCurrentRow();
					DgAgreement dg = new DgAgreement();
					dg.setAgreement(agreement);
					dg.setVisible(true);
					agreement = dg.getAgreement();
					tableModel.updateRow(agreement);
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmAgreement.this.doDefaultCloseAction();
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
	private JButton getBtnPOR() {
		if (btnPOR == null) {
			btnPOR = new JButton();
			btnPOR.setText("备案");
			btnPOR.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction.checkPORAgreement(new Request(
							CommonVars.getCurrUser()));
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmAgreement.this,
								"请选择你要备案的批文或协议", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					Agreement agreement = (Agreement) tableModel
							.getCurrentRow();
					if (!checkPutOnRecord(agreement)) {
						return;
					}
					agreement = fixAssetAction.agreementPutOnRecord(
							new Request(CommonVars.getCurrUser()), agreement);
					tableModel.updateRow(agreement);
				}
			});
		}
		return btnPOR;
	}

	/**
	 * 备案时数据检查
	 * 
	 * @param agreement
	 * @return
	 */
	private boolean checkPutOnRecord(Agreement agreement) {
		if (AgreementState.PUT_ON_RECORD == agreement.getDeclareState()) {
			JOptionPane.showMessageDialog(FmAgreement.this, "协议正在执行，不能备案",
					"提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (AgreementState.EXECUTING == agreement.getDeclareState()) {
			JOptionPane.showMessageDialog(FmAgreement.this, "协议正在执行，不能备案",
					"提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (agreement.getEmsNo() == null
				|| "".equals(agreement.getEmsNo().trim())) {
			JOptionPane.showMessageDialog(FmAgreement.this, "手册号码不能为空，不能备案",
					"提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (agreement.getSancEmsNo() == null
				|| "".equals(agreement.getSancEmsNo().trim())) {
			JOptionPane.showMessageDialog(FmAgreement.this, "协议批文号不能为空，不能备案",
					"提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (agreement.getBeginDate() == null) {
			JOptionPane.showMessageDialog(FmAgreement.this, "开始日期不能为空，不能备案",
					"提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (agreement.getEndDate() == null) {
			JOptionPane.showMessageDialog(FmAgreement.this, "有效日期不能为空，不能备案",
					"提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (agreement.getCurr() == null) {
			JOptionPane.showMessageDialog(FmAgreement.this, "币值不能为空，不能备案",
					"提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (agreement.getInvestMoney() == null
				|| agreement.getInvestMoney() == 0) {
			JOptionPane.showMessageDialog(FmAgreement.this, "投资金额为空或为零，不能备案",
					"提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (agreement.getRegisteredMoney() == null
				|| agreement.getRegisteredMoney() == 0) {
			JOptionPane.showMessageDialog(FmAgreement.this, "注册资金为空或为零，不能备案",
					"提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (agreement.getTotalMoney() == null || agreement.getTotalMoney() == 0) {
			JOptionPane.showMessageDialog(FmAgreement.this,
					"表体中没有数据或有数据但没有数量或没有单价，请检查，不能备案", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		List list = fixAssetAction.findAgreementCommInfo(new Request(CommonVars
				.getCurrUser()), agreement);
		if(!list.isEmpty()){
			for(int i=0;i<list.size();i++){
				AgreementCommInfo agreementCommInfo=(AgreementCommInfo)list.get(i);
				if(agreementCommInfo.getUnit()==null){
					JOptionPane.showMessageDialog(this, "设备批文表体中有,单位为空的数据，不能备案", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return false;
				}
				if(agreementCommInfo.getUnitPrice()==null){
					JOptionPane.showMessageDialog(this, "设备批文表体中,有单价为空的数据，不能备案", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return false;
				}
				if(agreementCommInfo.getAmount()==null){
					JOptionPane.showMessageDialog(this, "设备批文表体中,有数量为空的数据，不能备案", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return false;
				}
				
			}
		}
		if (agreement.getTotalMoney() > agreement.getInvestMoney()) {
			JOptionPane.showMessageDialog(FmAgreement.this,
					"设备金额大于投资总额，请检查，不能备案", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		

		return true;
	}

	private void setState(Agreement agreement) {
		int declareState = (agreement.getDeclareState() == null ? -1
				: agreement.getDeclareState());
		this.btnDelete.setEnabled(AgreementState.ADDED == declareState);
		// this.btnEdit.setEnabled(AgreementState.ADDED == agreement
		// .getDeclareState());
		this.btnImport.setEnabled(AgreementState.ADDED == declareState);
		this.btnPOR.setEnabled(AgreementState.ADDED == declareState);
		this.btnAddMoney.setEnabled(AgreementState.ADDED != declareState);
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (btnAddMoney == null) {
			btnAddMoney = new JButton();
			btnAddMoney.setText("增资");
			btnAddMoney.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction.checkAddMoneyAgreement(new Request(
							CommonVars.getCurrUser()));
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmAgreement.this,
								"请选择你要增资的批文", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}
					Agreement agreement = (Agreement) tableModel
							.getCurrentRow();
					DgImportAgreementCommInfo dg = new DgImportAgreementCommInfo();
					dg.setChange(false);
					dg.setAddMoney(true);
					dg.setAgreement(agreement);
					dg.setVisible(true);
					List list = fixAssetAction.findAgreement(new Request(
							CommonVars.getCurrUser()));
					initTable(list);
				}
			});
		}
		return btnAddMoney;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
