package com.bestway.common.client.dataimport;

import java.util.Hashtable;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JButton;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.DBDataRoot;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgSelectFromDb extends JDialogBase {

	private JPanel jContentPane = null;
	private JCheckBox jCheckBox = null;
	private JCheckBox jCheckBox1 = null;
	private JComboBox jComboBox = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private boolean isOk = false;
	private DataImportAction dataImportAction = null;
	private Hashtable hs = new Hashtable();
	private DBDataRoot dbRoot = null;

	/**
	 * This is the default constructor
	 */
	public DgSelectFromDb() {
		super();
		dataImportAction = (DataImportAction) CommonVars
           .getApplicationContext().getBean("dataImportAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(474, 209);
		this.setContentPane(getJContentPane());
		this.setTitle("数据源选择");
		jCheckBox.setSelected(true);
		jCheckBox1.setSelected(false);
		
		jCheckBox.setEnabled(true);		
		jCheckBox1.setEnabled(true);		
		jComboBox.setEnabled(false);
		
		List ls = dataImportAction.findDBDataRoot(new Request(CommonVars.getCurrUser()));
		for (int i=0;i<ls.size();i++){
			DBDataRoot root = (DBDataRoot) ls.get(i);
			jComboBox.addItem(root.getName());
			hs.put(root.getName(),root);
		}
		jComboBox.setSelectedIndex(-1);
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
			jContentPane.add(getJCheckBox(), null);
			jContentPane.add(getJCheckBox1(), null);
			jContentPane.add(getJComboBox(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new java.awt.Rectangle(59,40,192,21));
			jCheckBox.setText("本地数据源");
			jCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jCheckBox1.setSelected(false);
					jComboBox.setEnabled(false);
					jComboBox.setSelectedIndex(-1);
				}
			});
		}
		return jCheckBox;
	}

	
	/**
	 * This method initializes jCheckBox1	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox1() {
		if (jCheckBox1 == null) {
			jCheckBox1 = new JCheckBox();
			jCheckBox1.setBounds(new java.awt.Rectangle(59,76,101,21));
			jCheckBox1.setText("选择数据源");
			jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jCheckBox.setSelected(false);
					jComboBox.setEnabled(true);		
				}
			});
		}
		return jCheckBox1;
	}

	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new java.awt.Rectangle(163,75,231,26));
		}
		return jComboBox;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new java.awt.Rectangle(117,128,103,26));
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
                    
					if (jCheckBox1.isSelected()){
						if (jComboBox.getSelectedItem() == null || jComboBox.getSelectedItem().equals("")){
							JOptionPane.showMessageDialog(DgSelectFromDb.this,
									"请选择数据源！", "确认", 2);
							return;
						}
						String name = jComboBox.getSelectedItem().toString();
						dbRoot = (DBDataRoot)hs.get(name);
						
					} else {
						dbRoot = null;
					}
					
					
					setOk(true);
					dispose();
					
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new java.awt.Rectangle(245,128,103,26));
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setOk(false);
					dispose();
				}
			});
		}
		return jButton1;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	public DBDataRoot getDbRoot() {
		return dbRoot;
	}

	public void setDbRoot(DBDataRoot dbRoot) {
		this.dbRoot = dbRoot;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
