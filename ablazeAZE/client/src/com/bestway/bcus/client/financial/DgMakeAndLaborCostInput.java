/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.financial;

import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.ui.winuicontrol.JDialogBase;
/**
 * @author Administratorf
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgMakeAndLaborCostInput extends JDialogBase {

	private JPanel jPanel2 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JFormattedTextField tfMakeCost = null;
	
	private JFormattedTextField tfLaborCost = null;

	private JLabel jLabel2 = null;
	
	private FmCostSummaryReport fmCostSummaryReport = null;
	
	private Double makeCost = null;  //  @jve:decl-index=0:
	
	private Double laborCost = null;  //  @jve:decl-index=0:
	/**
	 * This is the default constructor
	 */
	public DgMakeAndLaborCostInput() {
		super();
		initialize();
	}
	public void setVisible(Boolean b){
		tfMakeCost.setValue(makeCost);
		tfLaborCost.setValue(laborCost);
		super.setVisible(b);
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJPanel2());
		this.setSize(300, 200);
		this.setTitle("成本汇总表 制造费与人工费 输入");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				
			}
		});
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			javax.swing.JLabel jLabel = new JLabel();
			jLabel.setText("制造费总额：");
			jLabel.setBounds(42, 25, 75, 20);
			
			jLabel2 = new JLabel();
			jLabel2.setBounds(42, 50, 75, 20);
			jLabel2.setText("人工费总额：");
			
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			
			jPanel2.add(jLabel, null);
			jPanel2.add(getJTextField1(), null);
			jPanel2.add(getJTextField2(), null);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton1(), null);
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
	private JTextField getJTextField1() {
		if (tfMakeCost == null) {
			tfMakeCost = new JFormattedTextField();
			tfMakeCost.setBounds(117, 50, 130, 20);
			tfMakeCost.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfMakeCost;
	}
	private JTextField getJTextField2() {
		if (tfLaborCost == null) {
			tfLaborCost = new JFormattedTextField();
			tfLaborCost.setBounds(117, 25, 130, 20);
			tfLaborCost.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfLaborCost;
	}
	DefaultFormatterFactory defaultFormatterFactory = null;
	NumberFormatter numberFormatter = null;
	protected DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	public NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
			DecimalFormat decimalFormat1 = new DecimalFormat(); // @jve:decl-index=0:
			decimalFormat1.setMaximumFractionDigits(6);
			decimalFormat1.setGroupingSize(0);
			numberFormatter.setFormat(decimalFormat1);
		}
		return numberFormatter;
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
			jButton.setBounds(60, 90, 71, 23);
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(fmCostSummaryReport != null){
						Object makeCostObj = tfMakeCost.getValue();
						Object laborCostObj = tfLaborCost.getValue();
						Double makeCost = 0.00;
						Double laborCost = 0.00;
						if(makeCostObj != null)
							makeCost = Double.parseDouble(makeCostObj.toString());
						if(laborCostObj != null)
							laborCost = Double.parseDouble(laborCostObj.toString());
						
						if(makeCost.doubleValue() == 0.00){
							if(JOptionPane.showConfirmDialog(DgMakeAndLaborCostInput.this, "制造费输入为 0，是否继续？", "确认", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
								return;
						}
						if(laborCost.doubleValue() == 0.00){
							if(JOptionPane.showConfirmDialog(DgMakeAndLaborCostInput.this, "人工费输入为 0，是否继续？", "确认", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
								return;
						}						
						
						fmCostSummaryReport.inputCostOver(makeCost, laborCost);
					}
					DgMakeAndLaborCostInput.this.dispose();
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
			jButton1.setBounds(150, 90, 70, 23);
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgMakeAndLaborCostInput.this.dispose();
				}
			});

		}
		return jButton1;
	}

	private void addData() {
		
	}

	public FmCostSummaryReport getFmCostSummaryReport() {
		return fmCostSummaryReport;
	}

	public void setFmCostSummaryReport(FmCostSummaryReport fmCostSummaryReport) {
		this.fmCostSummaryReport = fmCostSummaryReport;
	}

	public Double getMakeCost() {
		return makeCost;
	}

	public void setMakeCost(Double makeCost) {
		this.makeCost = makeCost;
	}

	public Double getLaborCost() {
		return laborCost;
	}

	public void setLaborCost(Double laborCost) {
		this.laborCost = laborCost;
	}
	
}
