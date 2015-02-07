package com.bestway.client.fixtureonorder;

import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.util.Date;

import javax.swing.JTextField;
import javax.swing.JButton;
/**
 * 设备查询
 * @author chen
 *
 */
public class DgFixtureContractSerch extends JDialogBase {

	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JTextField tfSecendContractNo = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JTextField tfName = null;
	private JTextField tfSpec = null;
	private JCalendarComboBox cbbDeclareDate = null;
	private JButton btnSerch = null;
	//料号
	private String ptNo="";  //  @jve:decl-index=0:
	//分协议号
	private String secondContractNo ="";  //  @jve:decl-index=0:
	//商品名称
	private String name = "";  //  @jve:decl-index=0:
	//商品规格
    private String spec = "";  //  @jve:decl-index=0:
	//开始日期
	private Date startDate = null;
	//结束日期
	private Date endDate = null;
	private JLabel jLabel4 = null;
	private JCalendarComboBox cbbDeclareDate1 = null;
	private JLabel jLabel5 = null;
	private JTextField tfPtNo = null;
	/**
	 * This method initializes 
	 * 
	 */
	public DgFixtureContractSerch() {
		super();
		initialize();
		this.setResizable(false);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(339, 321));
        this.setContentPane(getJPanel());
        this.setTitle("设备查询");
			
	}
	
	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbDeclareDate() {
		if (cbbDeclareDate == null) {
			cbbDeclareDate = new JCalendarComboBox();
			cbbDeclareDate.setDate(null);
			cbbDeclareDate.setBounds(new Rectangle(131, 169, 170, 23));
		}
		return cbbDeclareDate;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("料号：");
			jLabel5.setBounds(new Rectangle(39, 35, 36, 18));
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(38, 203, 76, 18));
			jLabel4.setText("结束日期：");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(38, 169, 76, 18));
			jLabel3.setText("开始日期：");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(38, 137, 76, 18));
			jLabel2.setText("商品规格：");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(38, 102, 76, 18));
			jLabel1.setText("商品名称：");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(38, 67, 76, 18));
			jLabel.setText("分协议号：");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getTfSecendContractNo(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(getTfName(), null);
			jPanel.add(getTfSpec(), null);
			jPanel.add(getCbbDeclareDate(), null);
			jPanel.add(getBtnSerch(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(getCbbDeclareDate1(), null);
			jPanel.add(jLabel5, null);
			jPanel.add(getTfPtNo(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes tfSecendContractNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfSecendContractNo() {
		if (tfSecendContractNo == null) {
			tfSecendContractNo = new JTextField();
			tfSecendContractNo.setBounds(new Rectangle(130, 67, 171, 22));
		}
		return tfSecendContractNo;
	}

	/**
	 * This method initializes tfName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(new Rectangle(130, 102, 171, 22));
		}
		return tfName;
	}

	/**
	 * This method initializes tfSpec	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfSpec() {
		if (tfSpec == null) {
			tfSpec = new JTextField();
			tfSpec.setBounds(new Rectangle(130, 137, 171, 22));
		}
		return tfSpec;
	}

	/**
	 * This method initializes btnSerch	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnSerch() {
		if (btnSerch == null) {
			btnSerch = new JButton();
			btnSerch.setBounds(new Rectangle(132, 250, 73, 29));
			btnSerch.setText("查询");
			btnSerch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ptNo=tfPtNo.getText().trim();
					secondContractNo = tfSecendContractNo.getText().trim();
					name = tfName.getText().trim();
					spec = tfSpec.getText().trim();
					startDate = cbbDeclareDate.getDate();
					endDate = cbbDeclareDate1.getDate();
					DgFixtureContractSerch.this.dispose();
				}
			});
		}
		return btnSerch;
	}
	public String getPtNo() {
		return ptNo;
	}

	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}	

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}
	
	public String getSecondContractNo() {
		return secondContractNo;
	}

	public void setSecondContractNo(String secondContractNo) {
		this.secondContractNo = secondContractNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * This method initializes cbbDeclareDate1	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */
	private JCalendarComboBox getCbbDeclareDate1() {
		if (cbbDeclareDate1 == null) {
			cbbDeclareDate1 = new JCalendarComboBox();
			cbbDeclareDate1.setBounds(new Rectangle(131, 203, 170, 22));
			cbbDeclareDate1.setDate(null);
		}
		return cbbDeclareDate1;
	}

	/**
	 * This method initializes tfPtNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfPtNo() {
		if (tfPtNo == null) {
			tfPtNo = new JTextField();
			tfPtNo.setBounds(new Rectangle(132, 33, 169, 22));
		}
		return tfPtNo;
	}
	
	

}  //  @jve:decl-index=0:visual-constraint="10,10"
