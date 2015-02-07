package com.bestway.client.custom.common.supplement;

import javax.swing.JPanel;
import java.awt.Frame;
import java.awt.BorderLayout;
import javax.swing.JDialog;

import com.bestway.common.constant.RepDeclarationType;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import javax.swing.JCheckBox;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JButton;

public class DgGoptions extends JDialogBase{

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JCheckBox cbGoptionsA = null;
	private JCheckBox cbGoptionsB = null;
	private JCheckBox cbGoptionsC = null;
	private JCheckBox cbGoptionsD = null;
	private JCheckBox cbGoptionsE = null;
	private JCheckBox cbGoptionsF = null;
	private JCheckBox cbGoptionsG = null;
	private JCheckBox cbGoptionsH = null;
	private JCheckBox cbGoptionsI = null;
	private JCheckBox cbGoptionsJ = null;
	private JCheckBox cbGoptionsK = null;
	private JCheckBox cbGoptionsL = null;
	private JCheckBox cbGoptionsM = null;
	private JButton btnSave = null;
	/**
	 * @param owner
	 */
	public DgGoptions() {
		super();
		initialize();
	}
	public DgGoptions(String goptions) {
		super();
		initialize();
		initUI(goptions);
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(422, 397);
		this.setTitle("商品信息选项");
		this.setContentPane(getJContentPane());
	}
	private void initUI(String goptions) {
		boolean isSelect = true;
		String  value = RepDeclarationType.getGoptionsToNum(goptions);
		char[] babRel = value.toCharArray();
		if ("1".equals(String.valueOf(babRel[0]))) {
			cbGoptionsA.setSelected(isSelect);
		}if("1".equals(String.valueOf(babRel[1]))){
			cbGoptionsB.setSelected(isSelect);
		}if ("1".equals(String.valueOf(babRel[2]))) {
			cbGoptionsC.setSelected(isSelect);
		}if ("1".equals(String.valueOf(babRel[3]))) {
			cbGoptionsD.setSelected(isSelect);
		}if ("1".equals(String.valueOf(babRel[4]))) {
			cbGoptionsE.setSelected(isSelect);
		}if ("1".equals(String.valueOf(babRel[5]))) {
			cbGoptionsF.setSelected(isSelect);
		}if ("1".equals(String.valueOf(babRel[6]))) {
			cbGoptionsJ.setSelected(isSelect);
		}if ("1".equals(String.valueOf(babRel[7]))) {
			cbGoptionsH.setSelected(isSelect);
		}
		if ("1".equals(String.valueOf(babRel[8]))) {
			cbGoptionsI.setSelected(isSelect);
		}
		if ("1".equals(String.valueOf(babRel[9]))) {
			cbGoptionsJ.setSelected(isSelect);
		}
		if ("1".equals(String.valueOf(babRel[10]))) {
			cbGoptionsK.setSelected(isSelect);
		}
		if ("1".equals(String.valueOf(babRel[11]))) {
			cbGoptionsL.setSelected(isSelect);
		}
		if ("1".equals(String.valueOf(babRel[12]))) {
			cbGoptionsM.setSelected(isSelect);
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
			jContentPane.add(getCbGoptionsA(), null);
			jContentPane.add(getCbGoptionsB(), null);
			jContentPane.add(getCbGoptionsC(), null);
			jContentPane.add(getCbGoptionsD(), null);
			jContentPane.add(getCbGoptionsE(), null);
			jContentPane.add(getCbGoptionsF(), null);
			jContentPane.add(getCbGoptionsG(), null);
			jContentPane.add(getCbGoptionsH(), null);
			jContentPane.add(getCbGoptionsI(), null);
			jContentPane.add(getCbGoptionsJ(), null);
			jContentPane.add(getCbGoptionsK(), null);
			jContentPane.add(getCbGoptionsL(), null);
			jContentPane.add(getCbGoptionsM(), null);
			jContentPane.add(getBtnSave(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes cbGoptionsA	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbGoptionsA() {
		if (cbGoptionsA == null) {
			cbGoptionsA = new JCheckBox();
			cbGoptionsA.setBounds(new Rectangle(87, 49, 129, 21));
			cbGoptionsA.setText("A成分及比例");
		}
		return cbGoptionsA;
	}

	/**
	 * This method initializes cbGoptionsB	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbGoptionsB() {
		if (cbGoptionsB == null) {
			cbGoptionsB = new JCheckBox();
			cbGoptionsB.setBounds(new Rectangle(87, 83, 129, 21));
			cbGoptionsB.setText("B原料及组成");
		}
		return cbGoptionsB;
	}

	/**
	 * This method initializes cbGoptionsC	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbGoptionsC() {
		if (cbGoptionsC == null) {
			cbGoptionsC = new JCheckBox();
			cbGoptionsC.setBounds(new Rectangle(87, 125, 129, 21));
			cbGoptionsC.setText("C生产加工工艺");
		}
		return cbGoptionsC;
	}

	/**
	 * This method initializes cbGoptionsD	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbGoptionsD() {
		if (cbGoptionsD == null) {
			cbGoptionsD = new JCheckBox();
			cbGoptionsD.setBounds(new Rectangle(87, 164, 129, 21));
			cbGoptionsD.setText("D构成");
		}
		return cbGoptionsD;
	}

	/**
	 * This method initializes cbGoptionsE	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbGoptionsE() {
		if (cbGoptionsE == null) {
			cbGoptionsE = new JCheckBox();
			cbGoptionsE.setBounds(new Rectangle(87, 201, 129, 21));
			cbGoptionsE.setText("E技术参数");
		}
		return cbGoptionsE;
	}

	/**
	 * This method initializes cbGoptionsF	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbGoptionsF() {
		if (cbGoptionsF == null) {
			cbGoptionsF = new JCheckBox();
			cbGoptionsF.setBounds(new Rectangle(87, 231, 129, 21));
			cbGoptionsF.setText("F具体规格");
		}
		return cbGoptionsF;
	}

	/**
	 * This method initializes cbGoptionsG	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbGoptionsG() {
		if (cbGoptionsG == null) {
			cbGoptionsG = new JCheckBox();
			cbGoptionsG.setBounds(new Rectangle(87, 264, 129, 21));
			cbGoptionsG.setText("G工作原理");
		}
		return cbGoptionsG;
	}

	/**
	 * This method initializes cbGoptionsH	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbGoptionsH() {
		if (cbGoptionsH == null) {
			cbGoptionsH = new JCheckBox();
			cbGoptionsH.setBounds(new Rectangle(230, 50, 129, 21));
			cbGoptionsH.setText("H车型、排量");
		}
		return cbGoptionsH;
	}

	/**
	 * This method initializes cbGoptionsI	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbGoptionsI() {
		if (cbGoptionsI == null) {
			cbGoptionsI = new JCheckBox();
			cbGoptionsI.setBounds(new Rectangle(230, 85, 129, 21));
			cbGoptionsI.setText("I功能");
		}
		return cbGoptionsI;
	}

	/**
	 * This method initializes cbGoptionsJ	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbGoptionsJ() {
		if (cbGoptionsJ == null) {
			cbGoptionsJ = new JCheckBox();
			cbGoptionsJ.setBounds(new Rectangle(230, 121, 129, 21));
			cbGoptionsJ.setText("J用途");
		}
		return cbGoptionsJ;
	}

	/**
	 * This method initializes cbGoptionsK	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbGoptionsK() {
		if (cbGoptionsK == null) {
			cbGoptionsK = new JCheckBox();
			cbGoptionsK.setBounds(new Rectangle(230, 159, 129, 21));
			cbGoptionsK.setText("K加工程度");
		}
		return cbGoptionsK;
	}

	/**
	 * This method initializes cbGoptionsL	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbGoptionsL() {
		if (cbGoptionsL == null) {
			cbGoptionsL = new JCheckBox();
			cbGoptionsL.setBounds(new Rectangle(230, 195, 129, 21));
			cbGoptionsL.setText("L性能指标");
		}
		return cbGoptionsL;
	}

	/**
	 * This method initializes cbGoptionsM	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbGoptionsM() {
		if (cbGoptionsM == null) {
			cbGoptionsM = new JCheckBox();
			cbGoptionsM.setBounds(new Rectangle(230, 226, 129, 21));
			cbGoptionsM.setText("M其他信息");
		}
		return cbGoptionsM;
	}

	/**
	 * This method initializes btnSave	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setBounds(new Rectangle(171, 310, 60, 23));
			btnSave.setText("确定");
			btnSave.addActionListener(new java.awt.event.ActionListener(){
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btnSave;
	}
	public String getStrGoptions(){
		String goptionsCon="";
		if(cbGoptionsA.isSelected()){
			goptionsCon +=cbGoptionsA.getText().trim()+",";
		}
		if(cbGoptionsB.isSelected()){
			goptionsCon +=cbGoptionsB.getText().trim()+",";
		}
		if(cbGoptionsC.isSelected()){
			goptionsCon +=cbGoptionsC.getText().trim()+",";
		}
		if(cbGoptionsD.isSelected()){
			goptionsCon +=cbGoptionsD.getText().trim()+",";
		}
		if(cbGoptionsE.isSelected()){
			goptionsCon +=cbGoptionsE.getText().trim()+",";
		}
		if(cbGoptionsF.isSelected()){
			goptionsCon +=cbGoptionsF.getText().trim()+",";
		}
		if(cbGoptionsG.isSelected()){
			goptionsCon +=cbGoptionsG.getText().trim()+",";
		}
		if(cbGoptionsH.isSelected()){
			goptionsCon +=cbGoptionsH.getText().trim()+",";
		}
		if(cbGoptionsI.isSelected()){
			goptionsCon +=cbGoptionsI.getText().trim()+",";
		}
		if(cbGoptionsJ.isSelected()){
			goptionsCon +=cbGoptionsJ.getText().trim()+",";
		}
		if(cbGoptionsK.isSelected()){
			goptionsCon +=cbGoptionsK.getText().trim()+",";
		}
		if(cbGoptionsL.isSelected()){
			goptionsCon +=cbGoptionsL.getText().trim()+",";
		}
		if(cbGoptionsM.isSelected()){
			goptionsCon +=cbGoptionsM.getText().trim()+",";
		}
	    return goptionsCon;
	}
	
}  
