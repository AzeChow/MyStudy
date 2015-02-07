package com.bestway.client.fixasset;

import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.fixasset.entity.TempOtherBillInfo;

public class PnChangeOtherInfo extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JTextField tfBillCode = null;
	private JTextField tfHandMan = null;

	/**
	 * This is the default constructor
	 */
	public PnChangeOtherInfo() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel1 = new JLabel();
		jLabel1.setBounds(new Rectangle(52, 97, 48, 24));
		jLabel1.setText("经手人");
		jLabel = new JLabel();
		jLabel.setBounds(new Rectangle(52, 47, 48, 27));
		jLabel.setText("单据号");
		this.setSize(300, 200);
		this.setLayout(null);
		this.add(jLabel, null);
		this.add(jLabel1, null);
		this.add(getTfBillCode(), null);
		this.add(getTfHandMan(), null);
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfBillCode() {
		if (tfBillCode == null) {
			tfBillCode = new JTextField();
			tfBillCode.setBounds(new Rectangle(100, 48, 158, 24));
		}
		return tfBillCode;
	}

	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfHandMan() {
		if (tfHandMan == null) {
			tfHandMan = new JTextField();
			tfHandMan.setBounds(new Rectangle(100, 97, 158, 24));
		}
		return tfHandMan;
	}
	
	public TempOtherBillInfo getOtherBillInfo(){
		TempOtherBillInfo info=new TempOtherBillInfo();
		info.setBillCode(tfBillCode.getText().trim());
		info.setHandMan(tfHandMan.getText().trim());
		return info;
	}

}
