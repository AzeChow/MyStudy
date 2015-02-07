package com.bestway.dzsc.client.dzscmanage;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.common.Request;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorWjHead;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgDzscEmsPorWjIEPort extends JDialogBase {

	private JPanel jContentPane = null;
	private JButton btnOK = null;
	private JButton btnCancel = null;
	private JLabel jLabel7 = null;
	private JComboBox cbbIEPort1 = null;
	private JLabel jLabel32 = null;
	private JLabel jLabel321 = null;
	private JComboBox cbbIEPort2 = null;
	private JLabel jLabel34 = null;
	private JComboBox cbbIEPort4 = null;
	private JLabel jLabel35 = null;
	private JComboBox cbbIEPort5 = null;
	private JComboBox cbbIEPort3 = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JComboBox cbbIEPort6 = null;
	private JComboBox cbbIEPort7 = null;
	private JComboBox cbbIEPort8 = null;
	private JComboBox cbbIEPort9 = null;
	private JComboBox cbbIEPort10 = null;
	/**
	 * 电子手册通关备案表头实体对象
	 */
	private DzscEmsPorWjHead dzscEmsPorWjHead=null;  //  @jve:decl-index=0:
	
	private DzscAction dzscAction = null;
	
	private int dataState=DataState.BROWSE;

	public DzscEmsPorWjHead getDzscEmsPorWjHead() {
		return dzscEmsPorWjHead;
	}

	public void setDzscEmsPorWjHead(DzscEmsPorWjHead dzscEmsPorWjHead) {
		this.dzscEmsPorWjHead = dzscEmsPorWjHead;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	/**
	 * This method initializes 
	 * 
	 */
	public DgDzscEmsPorWjIEPort() {
		super();
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
		"dzscAction");
		initialize();
		initUIComponents();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(502, 283));
        this.setTitle("进出口岸");
        this.setContentPane(getJContentPane());
			
	}
	
	public void setVisible(boolean b){
		if(b){
			showData();
			setState();
		}
		super.setVisible(b);
	}
	/**
	 *
	 * 将电子手册通关手册表头实体对象的值绑定到窗口组件中
	 */
	private void showData(){
		if(this.dzscEmsPorWjHead!=null){
			this.cbbIEPort1.setSelectedItem(this.dzscEmsPorWjHead.getIePort1());
			this.cbbIEPort2.setSelectedItem(this.dzscEmsPorWjHead.getIePort2());
			this.cbbIEPort3.setSelectedItem(this.dzscEmsPorWjHead.getIePort3());
			this.cbbIEPort4.setSelectedItem(this.dzscEmsPorWjHead.getIePort4());
			this.cbbIEPort5.setSelectedItem(this.dzscEmsPorWjHead.getIePort5());
			this.cbbIEPort6.setSelectedItem(this.dzscEmsPorWjHead.getIePort6());
			this.cbbIEPort7.setSelectedItem(this.dzscEmsPorWjHead.getIePort7());
			this.cbbIEPort8.setSelectedItem(this.dzscEmsPorWjHead.getIePort8());
			this.cbbIEPort9.setSelectedItem(this.dzscEmsPorWjHead.getIePort9());
			this.cbbIEPort10.setSelectedItem(this.dzscEmsPorWjHead.getIePort10());
		}else{
			this.cbbIEPort1.setSelectedItem(null);
			this.cbbIEPort2.setSelectedItem(null);
			this.cbbIEPort3.setSelectedItem(null);
			this.cbbIEPort4.setSelectedItem(null);
			this.cbbIEPort5.setSelectedItem(null);
			this.cbbIEPort6.setSelectedItem(null);
			this.cbbIEPort7.setSelectedItem(null);
			this.cbbIEPort8.setSelectedItem(null);
			this.cbbIEPort9.setSelectedItem(null);
			this.cbbIEPort10.setSelectedItem(null);
			
		}
	}
	/**
	 * 将窗口组件中的值绑定到电子手册通关手册表头实体对象上
	 */
	public void fillData(){
		if(this.dzscEmsPorWjHead!=null){
			this.dzscEmsPorWjHead.setIePort1((Customs)this.cbbIEPort1.getSelectedItem());
			this.dzscEmsPorWjHead.setIePort2((Customs)this.cbbIEPort2.getSelectedItem());
			this.dzscEmsPorWjHead.setIePort3((Customs)this.cbbIEPort3.getSelectedItem());
			this.dzscEmsPorWjHead.setIePort4((Customs)this.cbbIEPort4.getSelectedItem());
			this.dzscEmsPorWjHead.setIePort5((Customs)this.cbbIEPort5.getSelectedItem());
			this.dzscEmsPorWjHead.setIePort6((Customs)this.cbbIEPort6.getSelectedItem());
			this.dzscEmsPorWjHead.setIePort7((Customs)this.cbbIEPort7.getSelectedItem());
			this.dzscEmsPorWjHead.setIePort8((Customs)this.cbbIEPort8.getSelectedItem());
			this.dzscEmsPorWjHead.setIePort9((Customs)this.cbbIEPort9.getSelectedItem());
			this.dzscEmsPorWjHead.setIePort10((Customs)this.cbbIEPort10.getSelectedItem());
		}
	}
	
	/**
	 * 初始化UI组件
	 * 
	 */
	private void initUIComponents() {
		List list = CustomBaseList.getInstance().getCustoms();
		DzscClientLogic.intoComboBox(list,this.cbbIEPort1);
		DzscClientLogic.intoComboBox(list,this.cbbIEPort2);
		DzscClientLogic.intoComboBox(list,this.cbbIEPort3);
		DzscClientLogic.intoComboBox(list,this.cbbIEPort4);
		DzscClientLogic.intoComboBox(list,this.cbbIEPort5);
		DzscClientLogic.intoComboBox(list,this.cbbIEPort6);
		DzscClientLogic.intoComboBox(list,this.cbbIEPort7);
		DzscClientLogic.intoComboBox(list,this.cbbIEPort8);
		DzscClientLogic.intoComboBox(list,this.cbbIEPort9);
		DzscClientLogic.intoComboBox(list,this.cbbIEPort10);
	}
	/**
	 * 设定窗口组件状态
	 */
	private void setState(){
		this.cbbIEPort1.setEnabled(dataState!=DataState.BROWSE);
		this.cbbIEPort2.setEnabled(dataState!=DataState.BROWSE);
		this.cbbIEPort3.setEnabled(dataState!=DataState.BROWSE);
		this.cbbIEPort4.setEnabled(dataState!=DataState.BROWSE);
		this.cbbIEPort5.setEnabled(dataState!=DataState.BROWSE);
		this.cbbIEPort6.setEnabled(dataState!=DataState.BROWSE);
		this.cbbIEPort7.setEnabled(dataState!=DataState.BROWSE);
		this.cbbIEPort8.setEnabled(dataState!=DataState.BROWSE);
		this.cbbIEPort9.setEnabled(dataState!=DataState.BROWSE);
		this.cbbIEPort10.setEnabled(dataState!=DataState.BROWSE);
		
		this.btnOK.setEnabled(dataState!=DataState.BROWSE);
	}

	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(256, 150, 64, 24));
			jLabel4.setHorizontalAlignment(SwingConstants.LEFT);
			jLabel4.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel4.setText("进出口岸10");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(41, 150, 58, 24));
			jLabel3.setHorizontalAlignment(SwingConstants.LEFT);
			jLabel3.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel3.setText("进出口岸9");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(256, 116, 64, 24));
			jLabel2.setHorizontalAlignment(SwingConstants.LEFT);
			jLabel2.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel2.setText("进出口岸8");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(41, 116, 58, 24));
			jLabel1.setHorizontalAlignment(SwingConstants.LEFT);
			jLabel1.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel1.setText("进出口岸7");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(256, 84, 64, 24));
			jLabel.setHorizontalAlignment(SwingConstants.LEFT);
			jLabel.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel.setText("进出口岸6");
			jLabel35 = new JLabel();
			jLabel35.setBounds(new Rectangle(41, 84, 58, 24));
			jLabel35.setText("\u8fdb\u51fa\u53e3\u5cb85");
			jLabel35.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel35.setHorizontalAlignment(SwingConstants.LEFT);
			jLabel34 = new JLabel();
			jLabel34.setBounds(new Rectangle(256, 51, 64, 24));
			jLabel34.setText("\u8fdb\u51fa\u53e3\u5cb84");
			jLabel34.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel34.setHorizontalAlignment(SwingConstants.LEFT);
			jLabel321 = new JLabel();
			jLabel321.setBounds(new Rectangle(41, 51, 58, 24));
			jLabel321.setText("进出口岸3");
			jLabel321.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel321.setHorizontalAlignment(SwingConstants.LEFT);
			jLabel32 = new JLabel();
			jLabel32.setBounds(new Rectangle(256, 18, 64, 24));
			jLabel32.setText("\u8fdb\u51fa\u53e3\u5cb82");
			jLabel32.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel32.setHorizontalAlignment(SwingConstants.LEFT);
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(41, 18, 58, 24));
			jLabel7.setText("进出口岸1");
			jLabel7.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel7.setHorizontalAlignment(SwingConstants.LEFT);
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getBtnOK(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(getCbbIEPort1(), null);
			jContentPane.add(jLabel32, null);
			jContentPane.add(jLabel321, null);
			jContentPane.add(getCbbIEPort2(), null);
			jContentPane.add(jLabel34, null);
			jContentPane.add(getCbbIEPort4(), null);
			jContentPane.add(jLabel35, null);
			jContentPane.add(getCbbIEPort5(), null);
			jContentPane.add(getCbbIEPort3(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getCbbIEPort6(), null);
			jContentPane.add(getCbbIEPort7(), null);
			jContentPane.add(getCbbIEPort8(), null);
			jContentPane.add(getCbbIEPort9(), null);
			jContentPane.add(getCbbIEPort10(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes btnOK	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setBounds(new Rectangle(116, 201, 76, 23));
			btnOK.setText("确定");
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fillData();
					dzscAction.saveEmsPorWj(new Request(CommonVars
							.getCurrUser()), dzscEmsPorWjHead);
					dispose();
				}
			});
		}
		return btnOK;
	}

	/**
	 * This method initializes btnCancel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new Rectangle(276, 201, 83, 24));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes cbbIEPort1	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbIEPort1() {
		if (cbbIEPort1 == null) {
			cbbIEPort1 = new JComboBox();
			cbbIEPort1.setBounds(new Rectangle(99, 18, 136, 24));
		}
		return cbbIEPort1;
	}

	/**
	 * This method initializes cbbIEPort2	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbIEPort2() {
		if (cbbIEPort2 == null) {
			cbbIEPort2 = new JComboBox();
			cbbIEPort2.setBounds(new Rectangle(318, 18, 135, 24));
		}
		return cbbIEPort2;
	}

	/**
	 * This method initializes cbbIEPort4	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbIEPort4() {
		if (cbbIEPort4 == null) {
			cbbIEPort4 = new JComboBox();
			cbbIEPort4.setBounds(new Rectangle(318, 51, 135, 24));
		}
		return cbbIEPort4;
	}

	/**
	 * This method initializes cbbIEPort5	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbIEPort5() {
		if (cbbIEPort5 == null) {
			cbbIEPort5 = new JComboBox();
			cbbIEPort5.setBounds(new Rectangle(99, 84, 136, 24));
		}
		return cbbIEPort5;
	}

	/**
	 * This method initializes cbbIEPort3	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbIEPort3() {
		if (cbbIEPort3 == null) {
			cbbIEPort3 = new JComboBox();
			cbbIEPort3.setBounds(new Rectangle(99, 51, 136, 24));
		}
		return cbbIEPort3;
	}

	/**
	 * This method initializes cbbIEPort6	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbIEPort6() {
		if (cbbIEPort6 == null) {
			cbbIEPort6 = new JComboBox();
			cbbIEPort6.setBounds(new Rectangle(318, 84, 135, 24));
		}
		return cbbIEPort6;
	}

	/**
	 * This method initializes cbbIEPort7	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbIEPort7() {
		if (cbbIEPort7 == null) {
			cbbIEPort7 = new JComboBox();
			cbbIEPort7.setBounds(new Rectangle(99, 116, 136, 24));
		}
		return cbbIEPort7;
	}

	/**
	 * This method initializes cbbIEPort8	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbIEPort8() {
		if (cbbIEPort8 == null) {
			cbbIEPort8 = new JComboBox();
			cbbIEPort8.setBounds(new Rectangle(318, 116, 135, 24));
		}
		return cbbIEPort8;
	}

	/**
	 * This method initializes cbbIEPort9	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbIEPort9() {
		if (cbbIEPort9 == null) {
			cbbIEPort9 = new JComboBox();
			cbbIEPort9.setBounds(new Rectangle(99, 150, 136, 24));
		}
		return cbbIEPort9;
	}

	/**
	 * This method initializes cbbIEPort10	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbIEPort10() {
		if (cbbIEPort10 == null) {
			cbbIEPort10 = new JComboBox();
			cbbIEPort10.setBounds(new Rectangle(318, 150, 135, 24));
		}
		return cbbIEPort10;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
