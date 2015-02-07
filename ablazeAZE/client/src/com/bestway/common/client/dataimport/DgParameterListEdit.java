/*
 * Created on 2004-6-25
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataimport;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.ParameterList;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.client.custom.common.DgBaseExportCustomsDeclaration;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.ScmCoi;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgParameterListEdit extends JDialogBase {

	private JPanel jContentPane = null;

	private JButton btnSave = null;

	private JTextField tfCoiCode = null;

	private JButton btnCancel = null;

	private JTableListModel tableModel = null;

	private boolean isAdd = true;

	private DataImportAction dataImportAction = null;
	private ParameterList obj = null;

	private JCheckBox jCheckBox = null;

	private JScrollPane jScrollPane = null;

	private JTextArea tfCoiName = null;
	/**
	 * @return Returns the isAdd.
	 */
	public boolean isAdd() {
		return isAdd;
	}

	/**
	 * @param isAdd
	 *            The isAdd to set.
	 */
	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
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
	 * This method initializes
	 *  
	 */
	public DgParameterListEdit() {
		super();
		dataImportAction = (DataImportAction) CommonVars
           .getApplicationContext().getBean("dataImportAction");
		initialize();
	}

	private void fillWindow() {
		if (obj != null) {
			this.tfCoiCode.setText(obj.getPname());
			this.tfCoiName.setText(obj.getPvalue());
			this.jCheckBox.setSelected(obj.getIsNowDate()!=null && obj.getIsNowDate());
		}
	}


	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setTitle("视图参数编辑");
		this.setSize(528, 367);
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {

				if (isAdd == false) {
					if (tableModel.getCurrentRow() != null) {
						obj = (ParameterList) tableModel.getCurrentRow();
					}
					fillWindow();
				} else {
					
				}
			}
		});

	}

	/**
	 * 
	 * This method initializes jContentPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 *  
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jLabel.setBounds(72, 25, 57, 26);
			jLabel.setText("参数名称");
			jLabel1.setBounds(72, 55, 56, 21);
			jLabel1.setText("参数值");
			jContentPane.add(getBtnSave(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getTfCoiCode(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getJCheckBox(), null);
			jContentPane.add(getJScrollPane(), null);
		}
		return jContentPane;
	}

	private void fillData(ParameterList obj) {
		obj.setCompany(CommonVars.getCurrUser().getCompany());
		obj.setPname(this.tfCoiCode.getText());
		obj.setPvalue(this.tfCoiName.getText());
		obj.setIsNowDate(this.jCheckBox != null && this.jCheckBox.isSelected());
	}

	private void saveData() {
		if (isAdd()) {
			ParameterList obj = new ParameterList();
			fillData(obj);
			obj = dataImportAction.saveParameterList(new Request(CommonVars
					.getCurrUser()), obj);
			tableModel.addRow(obj);
			emptyWindowData();
		} else {
			fillData(obj);
			obj = dataImportAction.saveParameterList(new Request(CommonVars
					.getCurrUser()), obj);
			tableModel.updateRow(obj);
			this.dispose();
		}
	}

	
	private void emptyWindowData() {
		this.tfCoiCode.setText("");
		this.tfCoiName.setText("");

	}

	/**
	 * 
	 * This method initializes btnSave
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setBounds(161, 292, 74, 26);
			btnSave.setText("确定");
			btnSave.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					saveData();
				}
			});

		}
		return btnSave;
	}

	/**
	 * 
	 * This method initializes tfCoiCode
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getTfCoiCode() {
		if (tfCoiCode == null) {
			tfCoiCode = new JTextField();
			tfCoiCode.setBounds(140, 26, 207, 26);
		}
		return tfCoiCode;
	}

	/**
	 * 
	 * This method initializes btnCancel
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(267, 292, 74, 26);
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgParameterListEdit.this.dispose();
				}
			});

		}
		return btnCancel;
	}

	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */    
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(360, 30, 148, 21);
			jCheckBox.setText("当前日期");
		}
		return jCheckBox;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new java.awt.Rectangle(71,78,394,202));
			jScrollPane.setViewportView(getTfCoiName());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getTfCoiName() {
		if (tfCoiName == null) {
			tfCoiName = new JTextArea();
		}
		return tfCoiName;
	}
 } //  @jve:decl-index=0:visual-constraint="10,10"
