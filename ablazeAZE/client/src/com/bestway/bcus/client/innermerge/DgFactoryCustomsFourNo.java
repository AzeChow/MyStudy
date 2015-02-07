/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.innermerge;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.enc.report.DgImgExgRecord;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.CurrRate;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.ui.winuicontrol.JDialogBase;


import java.awt.GridBagConstraints;
import java.text.ParseException;
import java.util.Date;

import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
/**
 * @author Administratorf
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgFactoryCustomsFourNo extends JDialogBase {

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JTextField jtname = null;

	private JButton jButton1 = null;

	private JTableListModel tableModel = null;

	private CommonBaseCodeAction commonBaseCodeObj = null;
	private JLabel jLabel4 = null;
	private JTextField jTextField = null;
	private JLabel jLabel7 = null;
	private JLabel jLabel9 = null;
	private JTextField jTextField3 = null;
	private InnerMergeData data;  //  @jve:decl-index=0:
	
	private JButton jButton2 = null;

	private JButton jButton4 = null;
	/**
	 * This is the default constructor
	 */
	public DgFactoryCustomsFourNo() {
		super();
		initialize();
		commonBaseCodeObj = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJPanel2());
		this.setSize(470, 185);
		this.setTitle("四码报关资料编辑");

		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
				fillWindow();				
			}
		});
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel9 = new JLabel();
			jLabel7 = new JLabel();
			jLabel4 = new JLabel();
			java.awt.GridBagConstraints gridBagConstraints10 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints9 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints8 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints7 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints6 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints5 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints4 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints3 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints2 = new GridBagConstraints();

			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
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
			jLabel4.setBounds(33, 49, 53, 23);
			jLabel4.setText("报关编码");
			jLabel7.setBounds(244, 50, 57, 22);
			jLabel7.setText("报关名称");
			jLabel9.setBounds(15, 15, 88, 23);
			jLabel9.setText("四码报关资料");
			jLabel9.setForeground(java.awt.Color.red);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(getJTextField(), null);
			jPanel2.add(jLabel7, null);
			jPanel2.add(jLabel9, null);
			jPanel2.add(getJTextField32(), null);
			jPanel2.add(getJButton2(), null);
			jPanel2.add(getJButton4(), null);
		}
		return jPanel2;
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
			jButton1.setBounds(373, 89, 70, 23);
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgFactoryCustomsFourNo.this.dispose();

				}

			});

		}
		return jButton1;
	}

	private void fillWindow() {
		jTextField.setText(data.getHsFourCode());
		jTextField3.setText(data.getHsFourMaterielName());
	}

	private void clearData() {
		jTextField.setText("");
		jTextField3.setText("");
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
	 * @return Returns the commonBaseCodeObj.
	 */
	public CommonBaseCodeAction getCommonBaseCodeObj() {
		return commonBaseCodeObj;
	}

	/**
	 * @param commonBaseCodeObj
	 *            The commonBaseCodeObj to set.
	 */
	public void setCommonBaseCodeObj(CommonBaseCodeAction commonBaseCodeObj) {
		this.commonBaseCodeObj = commonBaseCodeObj;
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
		jtname = jtname;
	}
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setEditable(false);
			jTextField.setBounds(92, 50, 124, 22);
		}
		return jTextField;
	}
	/**
	 * This method initializes jTextField3	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField32() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			//jTextField3.setEditable(false);
			jTextField3.setBounds(306, 49, 137, 22);
		}
		return jTextField3;
	}
	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new java.awt.Rectangle(213,50,28,22));
			jButton2.setText("...");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Complex complex = (Complex) CommonQuery.getInstance().getComplex();
					if (complex != null) {
						jTextField.setText(getComplexFourNo(complex.getCode()));					
					}
				}
			});
		}
		return jButton2;
	}

	private String getComplexFourNo(String tenComplex){
		if (tenComplex == null){
			return "";
		}
		return tenComplex.substring(0,4);
	}
	/**
	 * This method initializes jButton4	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setBounds(new java.awt.Rectangle(245,89,116,23));
			jButton4.setText("更新报关商品");
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					 new find().start();
				}
			});
		}
		return jButton4;
	}
	
     class find extends Thread{		
		public void run(){
			 try {
	            CommonProgress.showProgressDialog(DgFactoryCustomsFourNo.this);
	            CommonProgress.setMessage("系统正更新数据，请稍后...");
	            commonBaseCodeObj.changeCustomsFourNo(new Request(CommonVars.getCurrUser()),jTextField.getText().trim(),jTextField3.getText().trim(),data.getImrType(),data.getHsFourNo());		            
	            CommonProgress.closeProgressDialog();
			 } catch (Exception e) {
		        CommonProgress.closeProgressDialog();
		        JOptionPane.showMessageDialog(DgFactoryCustomsFourNo.this,
		                "更新数据失败：！" + e.getMessage(), "提示", 2);    
			 } finally {
				 DgFactoryCustomsFourNo.this.dispose();
			 }
		}
	 }

	public void setData(InnerMergeData data) {
		this.data = data;
	}
             } //  @jve:decl-index=0:visual-constraint="10,10"
