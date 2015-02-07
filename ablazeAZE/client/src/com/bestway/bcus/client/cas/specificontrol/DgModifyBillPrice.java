package com.bestway.bcus.client.cas.specificontrol;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillTempMaster;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author fhz
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */

public class DgModifyBillPrice extends JDialogBase {

	private 		JPanel 						jPanel 					= null;
	private 		JLabel 						jLabel1 				= null;
	private 		JLabel 						jLabel2 				= null;
	private 		JLabel 						jLabel3 				= null;
	private 		JLabel 						jLabel4					= null;
	private 		JLabel 						jLabel5					= null;
	private 		JLabel 						jLabel6 				= null;
	private 		JLabel 						jLabel7 				= null;
	private 		JTextField 					txComplexCode			= null;
	private 		JTextField 					txPtName 				= null;
	private 		JTextField 					txUnitName 				= null;
	private 		JFormattedTextField 		txHsPrice 				= null;
	private 		JTextField 					txPart 					= null;
	private 		JTextField 					txSpec 					= null;
	private 		JFormattedTextField 		txUnitPrice 			= null;
	private 		JCheckBox 					cbAllUpdate 			= null;
	private 		JButton 					bnConfirm 				= null;
	private 		JButton 					bnAbandon				= null;
	
	private 		JTableListModel       		tableModel;
	private			BillDetail					billDetail;
	
	
	private 		CasAction 			  		casAction				=null;
	
	private			String						materielType;

	/**
	 * This method initializes 
	 * 
	 */
	public DgModifyBillPrice(String tableName) {
		super();
		initialize();
		this.materielType=tableName;
		casAction = (CasAction) CommonVars.getApplicationContext().getBean("casAction");
	}
	
	
	public JTableListModel getTableModel(){
		return tableModel;
	}
	
	public void setTableModel(JTableListModel tableModel){
		this.tableModel=tableModel;
	}
	
	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new java.awt.Dimension(459,285));
        this.setContentPane(getJPanel());
        this.setTitle("修改单价");
			
	}
	
	 /**
     * 初始化数据
     * 
     */
    public void initUIComponents() {
    	if (billDetail == null) {
            return;
        }
        this.txComplexCode.setText(billDetail.getComplex().getCode());
        this.txPtName.setText(billDetail.getPtName());
        this.txUnitName.setText(billDetail.getPtUnit().getName());
        this.txHsPrice.setValue(billDetail.getHsPrice());
        this.txPart.setText(billDetail.getPtPart());
        this.txSpec.setText(billDetail.getPtSpec());
        this.txUnitPrice.setValue(billDetail.getPtPrice());
       
    }
    
    public void setVisible(boolean b) {
        if (b) {
        	billDetail = (BillDetail) tableModel.getCurrentRow();
            this.initUIComponents();
        }
        super.setVisible(b);
    }
    
    
    
    private void saveData() {
        this.fillData();
        if(cbAllUpdate.isSelected()==true)
        	casAction.saveAllBillPrice(new Request(
    				CommonVars.getCurrUser()),billDetail,this.materielType);
        else
        	casAction.saveOneBillPrice(new Request(
    				CommonVars.getCurrUser()),billDetail);
        
//        tableModel.updateRow(commInfo);
    }
    
    
    private void fillData() {
    	returnPrompt();       
    	billDetail.setHsPrice(this.txHsPrice.getValue() == null ? null : Double.valueOf(this.txHsPrice.getValue().toString()));
    	billDetail.setPtPrice(this.txUnitPrice.getValue() == null ? null : Double.valueOf(this.txUnitPrice.getValue().toString()));
        
    }
    
    private void returnPrompt(){
    	 if (billDetail == null) {
         	 return;
         }
    	 if(txHsPrice.getValue().equals(0)){
    		 JOptionPane.showMessageDialog(DgModifyBillPrice.this,
    				 "合同单价为0", "提示", 0);
    		 return;
    	 }
    	 else if(txUnitPrice.getValue().equals(0)){
    		 JOptionPane.showMessageDialog(DgModifyBillPrice.this,
    				 "单价单价为0", "提示", 0);
    		 return;
    	 }
    }
    
    

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getJLabel1(), null);
			jPanel.add(getJLabel2(), null);
			jPanel.add(getJLabel3(), null);
			jPanel.add(getJLabel4(), null);
			jPanel.add(getJLabel5(), null);
			jPanel.add(getJLabel6(), null);
			jPanel.add(getJLabel7(), null);
			jPanel.add(getComplexCode(), null);
			jPanel.add(getPtName(), null);
			jPanel.add(getUnitName(), null);
			jPanel.add(getHsPrice(), null);
			jPanel.add(getPtPart(), null);
			jPanel.add(getPtSpec(), null);
			jPanel.add(getPtPrice(), null);
			jPanel.add(getJCheckBox(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
		}
		return jPanel;
	}
	
	private JLabel getJLabel1() {
		if(jLabel1 ==null){
			jLabel1=new JLabel();
			jLabel1.setText("商品编码");
			jLabel1.setBounds(new java.awt.Rectangle(15,25,58,20));
		}
		return jLabel1;
	}
	
	private JLabel getJLabel2() {
		if(jLabel2 ==null){
			jLabel2=new JLabel();
			jLabel2.setText("商品名称");
			jLabel2.setBounds(new java.awt.Rectangle(16,66,55,20));
		}
		return jLabel2;
	}
	
	private JLabel getJLabel3() {
		if(jLabel3 ==null){
			jLabel3=new JLabel();
			jLabel3.setText("单位");
			jLabel3.setBounds(new java.awt.Rectangle(37,95,27,18));
		}
		return jLabel3;
	}
	
	private JLabel getJLabel4() {
		if(jLabel4 ==null){
			jLabel4=new JLabel();
			jLabel4.setText("合同单价");
			jLabel4.setBounds(new java.awt.Rectangle(16,131,53,20));
		}
		return jLabel4;
	}
	
	private JLabel getJLabel5() {
		if(jLabel5 ==null){
			jLabel5=new JLabel();
			jLabel5.setText("BOM编码");
			jLabel5.setBounds(new java.awt.Rectangle(237,27,54,20));
		}
		return jLabel5;
	}
	
	private JLabel getJLabel6() {
		if(jLabel6 ==null){
			jLabel6=new JLabel();
			jLabel6.setText("型号规格");
			jLabel6.setBounds(new java.awt.Rectangle(238,64,55,20));
		}
		return jLabel6;
	}
	
	private JLabel getJLabel7() {
		if(jLabel7 ==null){
			jLabel7=new JLabel();
			jLabel7.setText("单价");
			jLabel7.setBounds(new java.awt.Rectangle(266,99,27,20));
		}
		return jLabel7;
	}

	/**
	 * This method initializes JFormattedTextField	
	 * 	
	 * @return javax.swing.JFormattedTextField	
	 */
	private JTextField getComplexCode() {
		if (txComplexCode == null) {
			txComplexCode = new JTextField();
			txComplexCode.setBounds(new java.awt.Rectangle(71,25,117,22));
			txComplexCode.setEditable(false);
		}
		return txComplexCode;
	}

	/**
	 * This method initializes ptName	
	 * 	
	 * @return javax.swing.JFormattedTextField	
	 */
	private JTextField getPtName() {
		if (txPtName == null) {
			txPtName = new JTextField();
			txPtName.setBounds(new java.awt.Rectangle(72,63,117,22));
			txPtName.setEditable(false);
		}
		return txPtName;
	}

	/**
	 * This method initializes unitName	
	 * 	
	 * @return javax.swing.JFormattedTextField	
	 */
	private JTextField getUnitName() {
		if (txUnitName == null) {
			txUnitName = new JTextField();
			txUnitName.setBounds(new java.awt.Rectangle(71,98,116,22));
			txUnitName.setEditable(false);
		}
		return txUnitName;
	}

	/**
	 * This method initializes hsPrice	
	 * 	
	 * @return javax.swing.JFormattedTextField	
	 */
	private JFormattedTextField getHsPrice() {
		if (txHsPrice == null) {
			txHsPrice= new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			txHsPrice.setBounds(new java.awt.Rectangle(72,130,116,22));
			
		}
		return txHsPrice;
	}

	/**
	 * This method initializes ptPart	
	 * 	
	 * @return javax.swing.JFormattedTextField	
	 */
	private JTextField getPtPart() {
		if (txPart == null) {
			txPart = new JTextField();
			txPart.setBounds(new java.awt.Rectangle(291,28,125,22));
			txPart.setEditable(false);
		}
		return txPart;
	}

	/**
	 * This method initializes ptSpec	
	 * 	
	 * @return javax.swing.JFormattedTextField	
	 */
	private JTextField getPtSpec() {
		if (txSpec == null) {
			txSpec = new JTextField();
			txSpec.setBounds(new java.awt.Rectangle(293,64,123,22));
			txSpec.setEditable(false);
		}
		return txSpec;
	}

	/**
	 * This method initializes ptPrice	
	 * 	
	 * @return javax.swing.JFormattedTextField	
	 */
	private JFormattedTextField getPtPrice() {
		if (txUnitPrice == null) {
			txUnitPrice =new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			txUnitPrice.setBounds(new java.awt.Rectangle(293,98,124,22));
		}
		return txUnitPrice;
	}

	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox() {
		if (cbAllUpdate == null) {
			cbAllUpdate = new JCheckBox();
			cbAllUpdate.setText("全部更新");
			cbAllUpdate.setBounds(new java.awt.Rectangle(255,131,118,21));
		}
		return cbAllUpdate;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (bnConfirm == null) {
			bnConfirm = new JButton();
			bnConfirm.setText("确定");
			bnConfirm.setBounds(new java.awt.Rectangle(135,194,63,28));
			bnConfirm.addActionListener(new ActionListener(){
	    			public void actionPerformed(ActionEvent e){
	    				saveData();
	    				dispose();
	    			}
	    		});
			
			
		}
		return bnConfirm;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (bnAbandon == null) {
			bnAbandon = new JButton();
			bnAbandon.setText("放弃");
			bnAbandon.setBounds(new java.awt.Rectangle(240,194,63,28));
			bnAbandon.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    dispose();
                }
            });
		}
		return bnAbandon;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
