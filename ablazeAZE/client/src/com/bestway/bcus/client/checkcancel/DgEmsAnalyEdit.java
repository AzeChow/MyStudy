/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.checkcancel;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.entity.EmsAnalyHead;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.ui.winuicontrol.JDialogBase;


import java.awt.GridBagConstraints;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
/**
 * @author Administratorf
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgEmsAnalyEdit extends JDialogBase {

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JTextField jtname = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JTableListModel tableModel = null;

	private CheckCancelAction checkCancelAction = null;

	private JTextField jtcode = null;
	private JCalendarComboBox jCalendarComboBox = null;
	private JCalendarComboBox jCalendarComboBox1 = null;
	private EmsAnalyHead emsAnalyHead = null;
	/**
	 * This is the default constructor
	 */
	public DgEmsAnalyEdit() {
		super();
		initialize();
		checkCancelAction = (CheckCancelAction) CommonVars
		       .getApplicationContext().getBean("checkCancelAction");

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJPanel2());
		this.setSize(362, 215);
		this.setTitle("核销期设置");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				if (tableModel.getCurrentRow() != null) {
				  emsAnalyHead = (EmsAnalyHead) tableModel.getCurrentRow();
				  fillWindow();
				}
			}
		});
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			javax.swing.JLabel jLabel3 = new JLabel();

			java.awt.GridBagConstraints gridBagConstraints10 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints9 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints8 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints7 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints6 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints5 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints4 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints3 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints2 = new GridBagConstraints();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jLabel.setText("核销流水号");
			jLabel.setBounds(42, 24, 60, 23);
			jLabel2.setText("核销起始日");
			jLabel2.setBounds(42, 50, 84, 23);
			jLabel1.setText("核销截止日");
			jLabel1.setBounds(43, 82, 82, 22);
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.gridwidth = 2;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints2.ipadx = 106;
			gridBagConstraints2.insets = new java.awt.Insets(25, 1, 5, 44);
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.ipadx = 9;
			gridBagConstraints3.ipady = 5;
			gridBagConstraints3.insets = new java.awt.Insets(5, 42, 6, 25);
			gridBagConstraints4.gridx = 1;
			gridBagConstraints4.gridy = 1;
			gridBagConstraints4.gridwidth = 2;
			gridBagConstraints4.weightx = 1.0;
			gridBagConstraints4.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints4.ipadx = 105;
			gridBagConstraints4.insets = new java.awt.Insets(7, 1, 5, 45);
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 3;
			gridBagConstraints5.ipadx = 13;
			gridBagConstraints5.ipady = -2;
			gridBagConstraints5.insets = new java.awt.Insets(8, 48, 18, 5);
			gridBagConstraints6.gridx = 1;
			gridBagConstraints6.gridy = 3;
			gridBagConstraints6.ipadx = 12;
			gridBagConstraints6.ipady = -3;
			gridBagConstraints6.insets = new java.awt.Insets(9, 37, 18, 0);
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.gridy = 2;
			gridBagConstraints7.ipadx = 10;
			gridBagConstraints7.ipady = 4;
			gridBagConstraints7.insets = new java.awt.Insets(6, 41, 8, 1);
			gridBagConstraints8.gridx = 1;
			gridBagConstraints8.gridy = 2;
			gridBagConstraints8.gridwidth = 2;
			gridBagConstraints8.weightx = 1.0;
			gridBagConstraints8.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints8.ipadx = 104;
			gridBagConstraints8.insets = new java.awt.Insets(5, 2, 9, 45);
			gridBagConstraints9.gridx = 2;
			gridBagConstraints9.gridy = 2;
			gridBagConstraints9.ipadx = -18;
			gridBagConstraints9.ipady = -7;
			gridBagConstraints9.insets = new java.awt.Insets(5, 1, 10, 22);
			gridBagConstraints10.gridx = 1;
			gridBagConstraints10.gridy = 0;
			gridBagConstraints10.gridwidth = 2;
			gridBagConstraints10.weightx = 1.0;
			gridBagConstraints10.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints10.ipadx = 104;
			gridBagConstraints10.insets = new java.awt.Insets(23,1,7,46);
			jLabel3.setBounds(43, 110, 46, 20);
			jLabel3.setText("备注");
			jPanel2.add(jLabel, null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(getJTextField2(), null);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(getJtcode(), null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(getJCalendarComboBox(), null);
			jPanel2.add(getJCalendarComboBox1(), null);
		}
		return jPanel2;
	}


	/**
	 * 
	 * This method initializes jTextField2
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getJTextField2() {
		if (jtname == null) {
			jtname = new JTextField();
			jtname.setBounds(96, 110, 238, 22);
		}
		return jtname;
	}

	/**
	 * 
	 * This method initializes jButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("确定");
			jButton.setBounds(94, 146, 71, 23);
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {		
					    addData(emsAnalyHead);
						DgEmsAnalyEdit.this.dispose();
				}
			});

		}
		return jButton;
	}

	/**
	 * 
	 * This method initializes jButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("取消");
			jButton1.setBounds(207, 146, 70, 23);
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgEmsAnalyEdit.this.dispose();
				}

			});

		}
		return jButton1;
	}

	private void fillWindow() {
		if (emsAnalyHead != null) {
			this.jtcode.setText(String.valueOf(emsAnalyHead.getEmsNo()));
			this.jtname.setText(emsAnalyHead.getNote());
			jCalendarComboBox.setDate(emsAnalyHead.getBeginDate());
			jCalendarComboBox1.setDate(emsAnalyHead.getEndDate());
		}
	}
	private void addData(EmsAnalyHead obj) {
		fillData(obj);
		obj = checkCancelAction.SaveEmsAnalyHead(new Request(CommonVars
				.getCurrUser()), obj);
		tableModel.updateRow(obj);
		System.out.println("保存成功！");
	}
   private void fillData(EmsAnalyHead obj){
   	  obj.setBeginDate(this.jCalendarComboBox.getDate());
   	  obj.setEndDate(this.jCalendarComboBox1.getDate());
   	  obj.setNote(this.jtname.getText());
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

	 * This method initializes jtcode	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getJtcode() {
		if (jtcode == null) {
			jtcode = new JTextField();
			jtcode.setEditable(false);
			jtcode.setBounds(142, 24, 113, 22);
		}
		return jtcode;
	}

	/**
	 * @return Returns the jtname.
	 */
	public JTextField getJtname() {
		return jtname;
	}
	/**
	 * @param jtname The jtname to set.
	 */
	public void setJtname(JTextField jtname) {
		this.jtname = jtname;
	}
	/**
	 * This method initializes jCalendarComboBox	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */    
	private JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox.setBounds(142, 53, 113, 22);
		}
		return jCalendarComboBox;
	}
	/**
	 * This method initializes jCalendarComboBox1	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */    
	private JCalendarComboBox getJCalendarComboBox1() {
		if (jCalendarComboBox1 == null) {
			jCalendarComboBox1 = new JCalendarComboBox();
			jCalendarComboBox1.setBounds(142, 81, 113, 22);
		}
		return jCalendarComboBox1;
	}
  } //  @jve:decl-index=0:visual-constraint="10,10"
