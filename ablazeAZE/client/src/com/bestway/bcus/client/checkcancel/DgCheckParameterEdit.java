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
import com.bestway.bcus.checkcancel.entity.CheckParameter;
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
import java.util.Date;

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
public class DgCheckParameterEdit extends JDialogBase {

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
	private CheckParameter emsAnalyHead = null;

	private JLabel jLabel2 = null;
	/**
	 * This is the default constructor
	 */
	public DgCheckParameterEdit() {
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
		this.setSize(333, 244);
		this.setTitle("核查参数设定");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				if (tableModel.getCurrentRow() != null) {
				  emsAnalyHead = (CheckParameter) tableModel.getCurrentRow();
				  fillWindow();
				}
			}
		});
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(41,57,77,18));
			jLabel2.setText("核销开始日");
			javax.swing.JLabel jLabel3 = new JLabel();



			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jLabel.setText("核销次数");
			jLabel.setBounds(42, 24, 60, 23);
			jLabel1.setText("核查截止日");
			jLabel1.setBounds(42, 87, 82, 22);
			jLabel3.setBounds(42, 115, 46, 20);
			jLabel3.setText("备注");
			jPanel2.add(jLabel, null);
			jPanel2.add(getJTextField2(), null);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(getJtcode(), null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(getJCalendarComboBox(), null);
			jPanel2.add(getJCalendarComboBox1(), null);
			jPanel2.add(jLabel2, null);
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
			jtname.setBounds(95, 115, 161, 22);
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
			jButton.setBounds(60, 149, 71, 23);
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {		
					    addData(emsAnalyHead);
						DgCheckParameterEdit.this.dispose();
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
			jButton1.setBounds(173, 149, 70, 23);
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCheckParameterEdit.this.dispose();
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
	private void addData(CheckParameter obj) {
		fillData(obj);
		obj = checkCancelAction.SaveCheckParameter(new Request(CommonVars
				.getCurrUser()), obj);
		tableModel.updateRow(obj);
		System.out.println("保存成功！");
	}
   private void fillData(CheckParameter obj){
   	  obj.setBeginDate(jCalendarComboBox.getDate());
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
			jCalendarComboBox.setBounds(141, 54, 113, 22);
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
			jCalendarComboBox1.setBounds(141, 86, 113, 22);
		}
		return jCalendarComboBox1;
	}
  } //  @jve:decl-index=0:visual-constraint="10,10"
