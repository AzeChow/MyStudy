/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.materialbase;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.EnterpriseMaterial;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 *         修改单重
 */
public class DgEpEditUnitWeight extends JDialogBase {

	private JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	/**
	 * 净重
	 */
	private JFormattedTextField tfUnitWeight = null;

	/**
	 * 保存按钮
	 */
	private JButton btnSave = null;

	/**
	 * 取消按钮
	 */
	private JButton btnCancel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	/**
	 * 料号
	 */
	private JTextField tfCode = null;

	/**
	 * 名称
	 */
	private JTextField tfName = null;

	private CommonBaseCodeAction commonBaseCodeAction = null;

	/**
	 * 料件管理操作接口
	 */
	private MaterialManageAction materialManageAction = null;

	private JTableListModel tableModel = null;

	private Object materiel = null;

	// private EnterpriseMaterial enterpriseMateriel = null;
	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:

	/**
	 * This method initializes
	 * 
	 */
	public DgEpEditUnitWeight() {
		super();
		initialize();
		commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("修改物料单重");
		this.setContentPane(getJContentPane());
		this.setSize(366, 199);

	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			if (this.tableModel != null) {
				// if(this.tableModel.getCurrentRow() instanceof Materiel){
				this.materiel = this.tableModel.getCurrentRow();
				// }else if(this.tableModel.getCurrentRow() instanceof
				// EnterpriseMaterial){
				// this.enterpriseMateriel = (EnterpriseMaterial)
				// this.tableModel.getCurrentRow();
				// }
				// thi
			}
			showData();
		}
		super.setVisible(b);
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getBtnSave(), null);
			jContentPane.add(getBtnCancel(), null);
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
			jPanel = new JPanel();
			jLabel = new JLabel();
			jLabel1 = new JLabel();
			jLabel2 = new JLabel();
			jPanel.setLayout(null);
			jPanel.setBounds(27, 22, 298, 100);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jLabel.setText("净重");
			jLabel.setBounds(27, 68, 29, 23);
			jLabel1.setBounds(27, 10, 29, 23);
			jLabel1.setText("料号");
			jLabel2.setBounds(27, 39, 29, 23);
			jLabel2.setText("名称");
			jPanel.add(jLabel, null);
			jPanel.add(getTfUnitWeight(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(getTfCode(), null);
			jPanel.add(getTfName(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jFormattedTextField1
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfUnitWeight() {
		if (tfUnitWeight == null) {
			tfUnitWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfUnitWeight.setBounds(59, 68, 208, 23);
			tfUnitWeight.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfUnitWeight;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setBounds(190, 133, 65, 24);
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					saveData();
					DgEpEditUnitWeight.this.dispose();

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
			btnCancel = new JButton();
			btnCancel.setBounds(265, 133, 65, 24);
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgEpEditUnitWeight.this.dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCode() {
		if (tfCode == null) {
			tfCode = new JTextField();
			tfCode.setBounds(59, 10, 154, 23);
			tfCode.setEditable(false);
			tfCode.setBackground(java.awt.Color.white);
		}
		return tfCode;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(59, 38, 180, 23);
			tfName.setEditable(false);
			tfName.setBackground(java.awt.Color.white);
		}
		return tfName;
	}

	private void showData() {
		if (this.materiel == null) {
			return;
		}
		if (this.materiel instanceof Materiel) {
			Materiel m = ((Materiel) materiel);
			// if (m.getComplex() != null) {
			// this.tfName.setText(m.getComplex().getName());
			// }
			this.tfName.setText(m.getFactoryName());
			this.tfCode.setText(m.getPtNo());
			if (m.getPtNetWeight() != null) {
				this.tfUnitWeight.setValue(m.getPtNetWeight());
			} else {
				this.tfUnitWeight.setValue(new Double(0));
			}
		} else if (this.materiel instanceof EnterpriseMaterial) {
			EnterpriseMaterial e = (EnterpriseMaterial) this.materiel;
			// if (e.getComplex() != null) {
			// this.tfName.setText(e.getComplex().getName());
			// }
			this.tfName.setText(e.getFactoryName());
			this.tfCode.setText(e.getPtNo());
			if (e.getPtNetWeight() != null) {
				this.tfUnitWeight.setValue(e.getPtNetWeight());
			} else {
				this.tfUnitWeight.setValue(new Double(0));
			}
		}

	}

	private void fillData() {
		if (this.materiel == null) {
			return;
		}
		if (this.materiel instanceof Materiel) {
			Materiel m = ((Materiel) materiel);
			m.setPtNetWeight(Double.valueOf(this.tfUnitWeight.getValue()
					.toString()));
		} else if (this.materiel instanceof EnterpriseMaterial) {
			EnterpriseMaterial e = (EnterpriseMaterial) this.materiel;
			e.setPtNetWeight(Double.valueOf(this.tfUnitWeight.getValue()
					.toString()));
		}

	}

	private void saveData() {
		if (this.materiel == null) {
			return;
		}
		this.fillData();
		if (this.materiel instanceof Materiel) {
			materiel = materialManageAction.saveMateriel(new Request(CommonVars
					.getCurrUser()), (Materiel) materiel);
		} else if (this.materiel instanceof EnterpriseMaterial) {
			materiel = materialManageAction.saveEnterpriseMaterial(new Request(
					CommonVars.getCurrUser()), (EnterpriseMaterial) materiel);
		}
		tableModel.updateRow(materiel);
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
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
			defaultFormatterFactory.setNullFormatter(getNumberFormatter());
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
} // @jve:decl-index=0:visual-constraint="10,10"
