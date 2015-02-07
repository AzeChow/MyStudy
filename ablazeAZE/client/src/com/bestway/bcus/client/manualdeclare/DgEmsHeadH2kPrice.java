package com.bestway.bcus.client.manualdeclare;

import javax.swing.JPanel;

import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class DgEmsHeadH2kPrice extends JDialogBase {

	private JPanel jContentPane = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JTextField jTextField = null;
	private JLabel jLabel3 = null;
	private JTextField jTextField1 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private boolean	ok	= false;
	private Double a = Double.valueOf(0);
	private Double b = Double.valueOf(0);

	/**
	 * This is the default constructor
	 */
	public DgEmsHeadH2kPrice() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(360, 240);
		this.setContentPane(getJContentPane());
		this.setTitle("帐册单价自定义公式设置");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(44,20,251,25));
			jLabel.setForeground(java.awt.Color.red);
			jLabel.setText("成品单价 = 耗用料件总价 * （1 + A/100） + B");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(69,138,179,21));
			jLabel5.setForeground(java.awt.Color.blue);
			jLabel5.setText("浮动金额为数值，如15，-15等");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(45,115,235,21));
			jLabel4.setForeground(java.awt.Color.blue);
			jLabel4.setText("注：浮动比例为百分比，如20%，-20%等");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(212,56,19,24));
			jLabel3.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jLabel3.setText("%");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(44,81,69,27));
			jLabel2.setText("浮动金额(B)");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(44,54,69,24));
			jLabel1.setText("浮动比例(A)");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getJTextField1(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new java.awt.Rectangle(116,56,93,22));
		}
		return jTextField;
	}

	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(new java.awt.Rectangle(116,84,113,22));
		}
		return jTextField1;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new java.awt.Rectangle(106,167,64,27));
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setOk(true);
					DgEmsHeadH2kPrice.this.setA(jTextField.getText().equals("") ? Double.valueOf(0) : Double.valueOf(jTextField.getText().trim()));
					DgEmsHeadH2kPrice.this.setB(jTextField1.getText().equals("") ? Double.valueOf(0) : Double.valueOf(jTextField1.getText().trim()));
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
			jButton1.setBounds(new java.awt.Rectangle(188,167,64,27));
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public Double getA() {
		return a;
	}

	public void setA(Double a) {
		this.a = a;
	}

	public Double getB() {
		return b;
	}

	public void setB(Double b) {
		this.b = b;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
