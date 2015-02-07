package com.bestway.client.custom.common.supplement;

import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.JCheckBox;
import java.awt.Point;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

import com.bestway.common.constant.RepDeclarationType;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

/**
 * 用于选择买卖双方之间存在的关系
 * @author Administrator
 *
 */
public class DgBuySellRelation  extends JDialogBase{

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JCheckBox cbI_BabRel0 = null;
	private JCheckBox cbI_BabRel1 = null;
	private JCheckBox cbI_BabRel2 = null;
	private JCheckBox cbI_BabRel3 = null;
	private JCheckBox cbI_BabRel4 = null;
	private JCheckBox cbI_BabRel5 = null;
	private JCheckBox cbI_BabRel6 = null;
	private JCheckBox cbI_BabRel7 = null;
	private JCheckBox cbI_BabRel8 = null;
	private JButton btnSave = null;
	/**
	 * @param owner
	 */
	public DgBuySellRelation(String bsr) {
		initialize();
		initUI(bsr);
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(471, 381);
		this.setTitle("买卖双方之间存在的关系");
		this.setContentPane(getJContentPane());
	}

	private void initUI(String relation){
		boolean isSelect = true;
		if(relation!=null){
			String value = RepDeclarationType.getI_BabRelToNum(relation);
			char[] babRel = value.toCharArray();
			if ("00000000".equals(value)) {
				cbI_BabRel0.setSelected(isSelect);
			}else {
				if("1".equals(String.valueOf(babRel[0]))){
					cbI_BabRel1.setSelected(isSelect);
				}if ("1".equals(String.valueOf(babRel[1]))) {
					cbI_BabRel2.setSelected(isSelect);
				}if ("1".equals(String.valueOf(babRel[2]))) {
					cbI_BabRel3.setSelected(isSelect);
				}if ("1".equals(String.valueOf(babRel[3]))) {
					cbI_BabRel4.setSelected(isSelect);
				}if ("1".equals(String.valueOf(babRel[4]))) {
					cbI_BabRel5.setSelected(isSelect);
				}if ("1".equals(String.valueOf(babRel[5]))) {
					cbI_BabRel6.setSelected(isSelect);
				}if ("1".equals(String.valueOf(babRel[6]))) {
					cbI_BabRel7.setSelected(isSelect);
				}if ("1".equals(String.valueOf(babRel[7]))) {
					cbI_BabRel8.setSelected(isSelect);
				}
			}
		}
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
			jContentPane.setBorder(BorderFactory.createTitledBorder(null, "1-8\u9879\u53ef\u591a\u9009\uff0c\u4f460\u4e0e1-8\u9879\u4e0d\u53ef\u540c\u65f6\u9009.", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jContentPane.add(getCbI_BabRel0(), null);
			jContentPane.add(getCbI_BabRel1(), null);
			jContentPane.add(getCbI_BabRel2(), null);
			jContentPane.add(getCbI_BabRel3(), null);
			jContentPane.add(getCbI_BabRel4(), null);
			jContentPane.add(getCbI_BabRel5(), null);
			jContentPane.add(getCbI_BabRel6(), null);
			jContentPane.add(getCbI_BabRel7(), null);
			jContentPane.add(getCbI_BabRel8(), null);
			jContentPane.add(getBtnSave(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes cbI_BabRel0	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbI_BabRel0() {
		if (cbI_BabRel0 == null) {
			cbI_BabRel0 = new JCheckBox();
			cbI_BabRel0.setBounds(new Rectangle(52, 26, 221, 21));
			cbI_BabRel0.setText("0.买卖双方无以下任何一种关系");
			cbI_BabRel0.addActionListener(new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectI_BabRel(e);
				}
		});
		}
		return cbI_BabRel0;
	}

	/**
	 * This method initializes cbI_BabRel1	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbI_BabRel1() {
		if (cbI_BabRel1 == null) {
			cbI_BabRel1 = new JCheckBox();
			cbI_BabRel1.setBounds(new Rectangle(52, 59, 223, 21));
			cbI_BabRel1.setText("1.买卖双方为同一家族成员");
			cbI_BabRel1.addActionListener(new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectI_BabRel(e);
				}
		});
		}
		return cbI_BabRel1;
	}

	/**
	 * This method initializes cbI_BabRel2	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbI_BabRel2() {
		if (cbI_BabRel2 == null) {
			cbI_BabRel2 = new JCheckBox();
			cbI_BabRel2.setSize(new Dimension(246, 21));
			cbI_BabRel2.setText("2.买卖双方互为商业上的高级职员或董事");
			cbI_BabRel2.setLocation(new Point(52, 90));
			cbI_BabRel2.addActionListener(new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectI_BabRel(e);
				}
		});
		}
		return cbI_BabRel2;
	}

	/**
	 * This method initializes cbI_BabRel3	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbI_BabRel3() {
		if (cbI_BabRel3 == null) {
			cbI_BabRel3 = new JCheckBox();
			cbI_BabRel3.setSize(new Dimension(236, 21));
			cbI_BabRel3.setText("3.一方直接或间接地受另一方控制");
			cbI_BabRel3.setLocation(new Point(52, 121));
			cbI_BabRel3.addActionListener(new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectI_BabRel(e);
				}
		});
		}
		return cbI_BabRel3;
	}

	/**
	 * This method initializes cbI_BabRel4	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbI_BabRel4() {
		if (cbI_BabRel4 == null) {
			cbI_BabRel4 = new JCheckBox();
			cbI_BabRel4.setSize(new Dimension(243, 21));
			cbI_BabRel4.setText("4.买卖双方都直接或间接地受第三方控制");
			cbI_BabRel4.setLocation(new Point(52, 154));
			cbI_BabRel4.addActionListener(new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectI_BabRel(e);
				}
		});
		}
		return cbI_BabRel4;
	}

	/**
	 * This method initializes cbI_BabRel5	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbI_BabRel5() {
		if (cbI_BabRel5 == null) {
			cbI_BabRel5 = new JCheckBox();
			cbI_BabRel5.setSize(new Dimension(233, 21));
			cbI_BabRel5.setText("5.买卖双方共同直接或间接地控制第三方");
			cbI_BabRel5.setLocation(new Point(52, 187));
			cbI_BabRel5.addActionListener(new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectI_BabRel(e);
				}
		});
		}
		return cbI_BabRel5;
	}

	/**
	 * This method initializes cbI_BabRel6	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbI_BabRel6() {
		if (cbI_BabRel6 == null) {
			cbI_BabRel6 = new JCheckBox();
			cbI_BabRel6.setBounds(new Rectangle(52, 216, 315, 35));
			cbI_BabRel6.setText("6.一方直接或间接地拥有控制或持有对方5%或以上<br>的公开发行的有表决权的股票或股份");
			cbI_BabRel6.addActionListener(new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectI_BabRel(e);
				}
		});
		}
		return cbI_BabRel6;
	}

	/**
	 * This method initializes cbI_BabRel7	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbI_BabRel7() {
		if (cbI_BabRel7 == null) {
			cbI_BabRel7 = new JCheckBox();
			cbI_BabRel7.setBounds(new Rectangle(52, 262, 267, 21));
			cbI_BabRel7.setText("7.一方是另一方雇员、高级职员或董事");
			cbI_BabRel7.addActionListener(new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectI_BabRel(e);
				}
		});
		}
		return cbI_BabRel7;
	}

	/**
	 * This method initializes cbI_BabRel8	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbI_BabRel8() {
		if (cbI_BabRel8 == null) {
			cbI_BabRel8 = new JCheckBox();
			cbI_BabRel8.setBounds(new Rectangle(52, 286, 244, 21));
			cbI_BabRel8.setText("8.买卖双方是同一合伙的成员");
			cbI_BabRel8.addActionListener(new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectI_BabRel(e);
				}
		});
		}
		return cbI_BabRel8;
	}
	
	private void selectI_BabRel(ActionEvent e){
		if(e.getSource()==cbI_BabRel0){
			cbI_BabRel1.setSelected(false);
			cbI_BabRel1.setEnabled(false);
			cbI_BabRel2.setSelected(false);
			cbI_BabRel2.setEnabled(false);
			cbI_BabRel3.setSelected(false);
			cbI_BabRel3.setEnabled(false);
			cbI_BabRel4.setSelected(false);
			cbI_BabRel4.setEnabled(false);
			cbI_BabRel5.setSelected(false);
			cbI_BabRel5.setEnabled(false);
			cbI_BabRel6.setSelected(false);
			cbI_BabRel6.setEnabled(false);
			cbI_BabRel7.setSelected(false);
			cbI_BabRel7.setEnabled(false);
			cbI_BabRel8.setSelected(false);
			cbI_BabRel8.setEnabled(false);
		}else{
			cbI_BabRel0.setSelected(false);
			cbI_BabRel0.setEnabled(false);
		}
	}

	/**
	 * This method initializes btnSave	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.setSize(new Dimension(63, 26));
			btnSave.setPreferredSize(new Dimension(36, 26));
			btnSave.setLocation(new Point(199, 316));
			btnSave.addActionListener(new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
			
		}
		return btnSave;
	}
	public String getLables(){
		String BabRelCon="";
		Boolean selectState = true;
		if(cbI_BabRel0.isSelected()){
			BabRelCon +=cbI_BabRel0.getText().trim()+",";
			selectState = false;
			}
		if(selectState){
			if(cbI_BabRel1.isSelected()){
				BabRelCon +=cbI_BabRel1.getText().trim()+",";
			}
			if(cbI_BabRel2.isSelected()){
				BabRelCon +=cbI_BabRel2.getText().trim()+",";
			}
			if(cbI_BabRel3.isSelected()){
				BabRelCon +=cbI_BabRel3.getText().trim()+",";
			}
			if(cbI_BabRel4.isSelected()){
				BabRelCon +=cbI_BabRel4.getText().trim()+",";
			}
			if(cbI_BabRel5.isSelected()){
				BabRelCon +=cbI_BabRel5.getText().trim()+",";
			}
			if(cbI_BabRel6.isSelected()){
				BabRelCon +=cbI_BabRel6.getText().trim()+",";
			}
			if(cbI_BabRel7.isSelected()){
				BabRelCon +=cbI_BabRel7.getText().trim()+",";
			}
			if(cbI_BabRel8.isSelected()){
				BabRelCon +=cbI_BabRel8.getText().trim()+",";
			}
		}
	    return BabRelCon;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
