package com.bestway.client.fixasset;

import java.awt.Rectangle;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.bestway.fixasset.entity.ChangeLocaOptionParam;

public class PnChangeLocationOption extends JPanel {

	private static final long serialVersionUID = 1L;
	private JRadioButton rbCustomsInFact = null;
	private JRadioButton rbFactChangeLocation = null;
	private JRadioButton rbFactAdd = null;
	private JRadioButton rbFactSubtract = null;
	private JLabel jLabel = null;
	private ButtonGroup buttonGroup = null;  //  @jve:decl-index=0:visual-constraint="469,46"

	/**
	 * This is the default constructor
	 */
	public PnChangeLocationOption() {
		super();
		initialize();
	}
	
	/**
	 * This is the default constructor
	 */
	public PnChangeLocationOption(ItemListener itemListener) {
		super();
		initialize();
		this.rbCustomsInFact.addItemListener(itemListener);
		this.rbFactChangeLocation.addItemListener(itemListener);
		this.rbFactAdd.addItemListener(itemListener);
		this.rbFactSubtract.addItemListener(itemListener);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel = new JLabel();
		jLabel.setBounds(new Rectangle(43, 11, 130, 31));
		jLabel.setText("请选择操作类型");
		this.setSize(406, 235);
		this.setLayout(null);
		this.add(getRbCustomsInFact(), null);
		this.add(getRbFactChangeLocation(), null);
		this.add(getRbFactAdd(), null);
		this.add(getRbFactSubtract(), null);
		this.add(jLabel, null);
		this.getButtonGroup();
	}

	/**
	 * This method initializes jRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbCustomsInFact() {
		if (rbCustomsInFact == null) {
			rbCustomsInFact = new JRadioButton();
			rbCustomsInFact.setBounds(new Rectangle(84, 57, 143, 24));			
			rbCustomsInFact.setSelected(true);
			rbCustomsInFact.setActionCommand(String.valueOf(ChangeLocaOptionParam.CUSTOMS_IN_FACT));
			rbCustomsInFact.setText("设备报关进厂");			
		}
		return rbCustomsInFact;
	}

	/**
	 * This method initializes jRadioButton1	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbFactChangeLocation() {
		if (rbFactChangeLocation == null) {
			rbFactChangeLocation = new JRadioButton();
			rbFactChangeLocation.setBounds(new Rectangle(84, 92, 143, 24));
			rbFactChangeLocation.setActionCommand(String.valueOf(ChangeLocaOptionParam.FACT_CHANGE_LOCATION));
			rbFactChangeLocation.setText("设备厂内移位");
		}
		return rbFactChangeLocation;
	}

	/**
	 * This method initializes jRadioButton2	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbFactAdd() {
		if (rbFactAdd == null) {
			rbFactAdd = new JRadioButton();
			rbFactAdd.setBounds(new Rectangle(84, 128, 143, 24));
			rbFactAdd.setActionCommand(String.valueOf(ChangeLocaOptionParam.FACT_ADD));
			rbFactAdd.setText("设备厂内增加");
		}
		return rbFactAdd;
	}

	/**
	 * This method initializes jRadioButton3	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbFactSubtract() {
		if (rbFactSubtract == null) {
			rbFactSubtract = new JRadioButton();
			rbFactSubtract.setBounds(new Rectangle(84, 159, 143, 24));
			rbFactSubtract.setActionCommand(String.valueOf(ChangeLocaOptionParam.FACT_SUBTRACT));
			rbFactSubtract.setText("设备厂内减少");
		}
		return rbFactSubtract;
	}

	/**
	 * This method initializes buttonGroup	
	 * 	
	 * @return javax.swing.ButtonGroup	
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.getRbCustomsInFact());
			buttonGroup.add(this.getRbFactChangeLocation());
			buttonGroup.add(this.getRbFactAdd());
			buttonGroup.add(this.getRbFactSubtract());
		}
		return buttonGroup;
	}
	
	public int getChangeLocationOption(){
		if(this.rbCustomsInFact.isSelected()){
			return ChangeLocaOptionParam.CUSTOMS_IN_FACT;
		}else if(this.rbFactChangeLocation.isSelected()){
			return ChangeLocaOptionParam.FACT_CHANGE_LOCATION;
		}else if(this.rbFactAdd.isSelected()){
			return ChangeLocaOptionParam.FACT_ADD;
		}else if(this.rbFactSubtract.isSelected()){
			return ChangeLocaOptionParam.FACT_SUBTRACT;
		}
		return -1;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
