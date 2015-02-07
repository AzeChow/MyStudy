/*
 * Created on 2005-5-23
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.erpbill;

import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;


import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.common.Request;
import com.bestway.common.erpbill.action.ErpBillAction;
import com.bestway.common.erpbill.action.OrderCommonAction;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JTextField;
import java.awt.Rectangle;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgOrderQueryCondition extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel3 = null;

	private JComboBox cbbScmCoc = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JLabel jLabel4 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private EncAction encAction = null;

	private List lsResult = null;

	private boolean isImport;

	private JTextField tfOrderNo = null;

	private JLabel jLabel = null;

	private Integer type = 0;

	private MaterialManageAction materialManageAction = null;

	private JButton jButton2 = null;
	
	private boolean isTransfer = false;
	
	private OrderCommonAction orderCommonAction = null;  //  @jve:decl-index=0:


	/**
	 * @return Returns the isImport.
	 */
	public boolean isImport() {
		return isImport;
	}

	/**
	 * @param isImport
	 *            The isImport to set.
	 */
	public void setImport(boolean isImport) {
		this.isImport = isImport;
	}

	/**
	 * @return Returns the lsResult.
	 */
	public List getLsResult() {
		return lsResult;
	}

	/**
	 * This is the default constructor
	 */
	public DgOrderQueryCondition() {
		super();
		 orderCommonAction = (OrderCommonAction) CommonVars.getApplicationContext()
	    	.getBean("orderCommonAction");
		 materialManageAction = (MaterialManageAction) CommonVars.getApplicationContext()
	    	.getBean("materialManageAction");
		initialize();
		this.cbbBeginDate.setDate(null);
		this.cbbEndDate.setDate(null);
	}

	public void setVisible(boolean b) {
		if (b) {
			initUIComponents();
		}
		super.setVisible(b);
	}

	/**
	 * 初始化窗口上 控件的值
	 * 
	 */
	private void initUIComponents() {
		// 初始化客户commonBaseCodeAction
		List list = materialManageAction.findScmCocs(new Request(CommonVars
				.getCurrUser(), true));
		this.cbbScmCoc.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 100, 170));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbScmCoc, "code", "name", 270);
		cbbScmCoc.setSelectedIndex(-1);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("请输入检索条件");
		this.setSize(379, 248);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel = new JLabel();
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jLabel1 = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel1.setBounds(41, 25, 93, 24);
			jLabel1.setText("客户/供应商");
			jLabel3.setBounds(41, 87, 93, 24);
			jLabel3.setText("订单日期");
			jLabel4.setBounds(231, 89, 13, 18);
			jLabel4.setText("至");
			jLabel.setBounds(41, 56, 93, 20);
			jLabel.setText("订单号");
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getCbbScmCoc(), null);
			jContentPane.add(getCbbBeginDate(), null);
			jContentPane.add(getCbbEndDate(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getTfOrderNo(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getJButton2(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbScmCoc() {
		if (cbbScmCoc == null) {
			cbbScmCoc = new JComboBox();
			cbbScmCoc.setBounds(139, 25, 197, 22);
		}
		return cbbScmCoc;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(139, 88, 89, 24);
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(245, 88, 90, 23);
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(106, 166, 81, 25);
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ScmCoc scmCoc = null;
					String billNo = null;
					if (cbbScmCoc.getSelectedItem() != null) {
						scmCoc = (ScmCoc) cbbScmCoc.getSelectedItem();
					}
					if((!"".equals(tfOrderNo.getText()))||tfOrderNo.getText()!=null){
						billNo = tfOrderNo.getText().trim();
					}
					Date beginDate = cbbBeginDate.getDate();
					Date endDate = cbbEndDate.getDate();
					System.out.println("type=="+type+" isTransfer==  "+isTransfer+"   billNo="+billNo);
					lsResult = orderCommonAction.findCustomOrder(
							new Request(CommonVars.getCurrUser()), type,
							scmCoc, beginDate,endDate,billNo,isTransfer);
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
			jButton1.setBounds(214, 166, 83, 25);
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgOrderQueryCondition.this.dispose();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes tfOrderNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfOrderNo() {
		if (tfOrderNo == null) {
			tfOrderNo = new JTextField();
			tfOrderNo.setBounds(139, 56, 179, 22);
		}
		return tfOrderNo;
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setType(int type) {
		this.type = type;
	}
	
	public void setIsTransfer(boolean isTransfer){
		this.isTransfer = isTransfer;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(318, 57, 17, 21));
			jButton2.setText("...");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgOrderArrayConditionValue dg = new DgOrderArrayConditionValue();
					dg.setConditionValue(tfOrderNo.getText().trim());
					dg.setVisible(true);
					if (dg.isOK()) {
						tfOrderNo.setText(dg.getConditionValue());
					}
				}
			});
		}
		return jButton2;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
