/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.checkcancel;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonQueryPage;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.CurrCode;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.util.List;
import java.util.Vector;

import javax.swing.JRadioButton;
/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgEmsAnalyPanDImgConvert extends JDialogBase {

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JTableListModel tableModel = null;

	private CommonBaseCodeAction commonBaseCodeObj = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;
	
	private ButtonGroup buttonGroup = new ButtonGroup();
	
	private boolean isFromUnitWear = true; 
	
	private boolean isOk = true;
	
	

	/**
	 * This is the default constructor
	 */
	public DgEmsAnalyPanDImgConvert() {
		super();
		initialize();
		buttonGroup.add(jRadioButton);
		buttonGroup.add(jRadioButton1);
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
		this.setSize(329, 202);
		this.setTitle("报关成品折算料件依据");

		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
				
				
			}
		});
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			javax.swing.JLabel jLabel = new JLabel();

			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jLabel.setText("报关成品折算料件依据：");
			jLabel.setForeground(new java.awt.Color(0,0,204));
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
			jLabel.setBounds(new java.awt.Rectangle(13,17,262,23));
			jPanel2.add(jLabel, null);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(getJRadioButton(), null);
			jPanel2.add(getJRadioButton1(), null);
		}
		return jPanel2;
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
			jButton.setBounds(new java.awt.Rectangle(76,119,71,26));
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jRadioButton.isSelected()){							
						DgEmsAnalyPanDImgConvert.this.setFromUnitWear(true);            
					} else {
						DgEmsAnalyPanDImgConvert.this.setFromUnitWear(false);            
					}
					DgEmsAnalyPanDImgConvert.this.setOk(true);
                    DgEmsAnalyPanDImgConvert.this.dispose();
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
			jButton1.setBounds(new java.awt.Rectangle(177,120,70,25));
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
                    
					DgEmsAnalyPanDImgConvert.this.setOk(false);
					DgEmsAnalyPanDImgConvert.this.dispose();

				}

			});

		}
		return jButton1;
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
	 * This method initializes jRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(new java.awt.Rectangle(57,53,207,21));
			jRadioButton.setText("帐册单耗表");
			jRadioButton.setSelected(true);
		}
		return jRadioButton;
	}

	/**
	 * This method initializes jRadioButton1	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setBounds(new java.awt.Rectangle(57,83,125,21));
			jRadioButton1.setText("报关常用BOM");
		}
		return jRadioButton1;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	public boolean isFromUnitWear() {
		return isFromUnitWear;
	}

	public void setFromUnitWear(boolean isFromUnitWear) {
		this.isFromUnitWear = isFromUnitWear;
	}

      }  //  @jve:decl-index=0:visual-constraint="7,5"
