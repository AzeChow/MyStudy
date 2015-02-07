package com.bestway.bcus.client.cas.otherreport;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.CheckBalanceOfCustom;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.util.Map;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class DgCheckBalanceOfCustomEdit extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4354054379594576557L;
	private JPanel pnMain = null;
	private JLabel lb1 = null;
	private JLabel lb2 = null;
	private JLabel lb3 = null;
	private JLabel lb4 = null;
	private JLabel lb5 = null;
	private JTextField tfCheckAmount = null;
	private JTextField tfName = null;
	private JTextField tfSpec = null;
	private JTextField tfUnitName = null;
	private JButton btnYes = null;
	private JButton btnNo = null;
	private JComboBox cbbKcType = null;
	
	private CheckBalanceOfCustom balance=null;  //  @jve:decl-index=0:
	
	private CasAction casAction=null;  //  @jve:decl-index=0:
	
	private JTableListModel tableModel = null;
	/**
	 * This method initializes 
	 * 
	 */
	public DgCheckBalanceOfCustomEdit(CheckBalanceOfCustom balance) {
		super();
		this.balance=balance;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(315, 279));
        this.setTitle("盘点数据编辑");
        this.setContentPane(getPnMain());
      //库存类别
		this.cbbKcType.removeAllItems();
		this.cbbKcType.addItem(new ItemProperty("0", "料件库存"));
		this.cbbKcType.addItem(new ItemProperty("1", "成品库存"));
		this.cbbKcType.addItem(new ItemProperty("2", "在产品库存"));
		this.cbbKcType.addItem(new ItemProperty("4", "外发未收回"));
		this.cbbKcType.addItem(new ItemProperty("5", "残次品库存"));
		this.cbbKcType.addItem(new ItemProperty("6", "边角料库存"));
		initComboBoxRenderer(cbbKcType);
		//填充
		this.tfName.setText(balance.getName());
		this.tfSpec.setText(balance.getSpec());
		this.tfUnitName.setText(balance.getUnitName());
		this.tfCheckAmount.setText(String.valueOf(balance.getCheckAmount()));
		Integer anIndex=null;
		if(balance.getKcType()!=null){
		
			String code = balance.getKcType();
			if(code.equals("0"))
				anIndex=0;
			if(code.equals("1"))
				anIndex=1;
			if(code.equals("2"))
				anIndex=2;
			if(code.equals("4"))
				anIndex=3;
			if(code.equals("5"))
				anIndex=4;
			if(code.equals("6"))
				anIndex=5;
		}
		this.cbbKcType.setSelectedIndex(anIndex);
	}
	
	/**
	 * 初始化下拉框呈现
	 * @param cbb
	 */
	private void initComboBoxRenderer(JComboBox cbb) {
		cbb.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbb);
		cbb.setSelectedItem(null);
		/**
		 * 初始化海关帐远程接口
		 */
		casAction = (CasAction) CommonVars.getApplicationContext().getBean("casAction");
	}
	
	class saveThread extends Thread{
		public void run() {
			casAction.savaOrUpdateObject(new Request(),balance);
			DgCheckBalanceOfCustomEdit.this.dispose();
		}
	}
	
	/**
	 * This method initializes pnMain	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnMain() {
		if (pnMain == null) {
			lb5 = new JLabel();
			lb5.setBounds(new Rectangle(13, 159, 61, 23));
			lb5.setText("库存类别");
			lb4 = new JLabel();
			lb4.setBounds(new Rectangle(13, 124, 61, 23));
			lb4.setText("数量");
			lb3 = new JLabel();
			lb3.setBounds(new Rectangle(13, 83, 61, 23));
			lb3.setText("商品单位");
			lb2 = new JLabel();
			lb2.setBounds(new Rectangle(13, 51, 61, 23));
			lb2.setText("商品规格");
			lb1 = new JLabel();
			lb1.setBounds(new Rectangle(13, 12, 61, 23));
			lb1.setText("商品名称");
			pnMain = new JPanel();
			pnMain.setLayout(null);
			pnMain.add(lb1, null);
			pnMain.add(lb2, null);
			pnMain.add(lb3, null);
			pnMain.add(lb4, null);
			pnMain.add(lb5, null);
			pnMain.add(getTfCheckAmount(), null);
			pnMain.add(getTfName(), null);
			pnMain.add(getTfSpec(), null);
			pnMain.add(getTfUnitName(), null);
			pnMain.add(getBtnYes(), null);
			pnMain.add(getBtnNo(), null);
			pnMain.add(getCbbKcType(), null);
		}
		return pnMain;
	}

	/**
	 * This method initializes tfCheckAmount	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfCheckAmount() {
		if (tfCheckAmount == null) {
			tfCheckAmount = new JTextField();
			tfCheckAmount.setBounds(new Rectangle(85, 126, 160, 24));
		}
		return tfCheckAmount;
	}

	/**
	 * This method initializes tfName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(new Rectangle(85, 13, 160, 24));
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
			tfSpec.setBounds(new Rectangle(85, 51, 160, 24));
		}
		return tfSpec;
	}

	/**
	 * This method initializes tfUnitName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfUnitName() {
		if (tfUnitName == null) {
			tfUnitName = new JTextField();
			tfUnitName.setBounds(new Rectangle(85, 83, 160, 24));
		}
		return tfUnitName;
	}

	/**
	 * This method initializes btnYes	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnYes() {
		if (btnYes == null) {
			btnYes = new JButton();
			btnYes.setBounds(new Rectangle(48, 205, 83, 28));
			btnYes.setText("确定");
			btnYes.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					balance.setName(tfName.getText().trim());
					balance.setSpec(tfSpec.getText().trim());
					balance.setUnitName(tfUnitName.getText().trim());
					balance.setCheckAmount(Double.valueOf(tfCheckAmount.getText().trim()));
					balance.setKcType(((ItemProperty)cbbKcType.getSelectedItem()).getCode());
					new saveThread().start();
				}
			});
		}
		return btnYes;
	}

	/**
	 * This method initializes btnNo	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnNo() {
		if (btnNo == null) {
			btnNo = new JButton();
			btnNo.setBounds(new Rectangle(167, 205, 83, 28));
			btnNo.setText("取消");
			btnNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCheckBalanceOfCustomEdit.this.dispose();
				}
			});
		}
		return btnNo;
	}

	/**
	 * This method initializes cbbKcType	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbKcType() {
		if (cbbKcType == null) {
			cbbKcType = new JComboBox();
			cbbKcType.setBounds(new Rectangle(85, 159, 160, 24));
		}
		return cbbKcType;
	}

	public CheckBalanceOfCustom getBalance() {
		return balance;
	}

	public void setBalance(CheckBalanceOfCustom balance) {
		this.balance = balance;
	}
	
	
}  //  @jve:decl-index=0:visual-constraint="39,18"
