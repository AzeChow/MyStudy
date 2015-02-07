package com.bestway.common.client.transferfactory;

import javax.swing.JPanel;
import java.awt.Frame;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.FptInitBillItem;
import com.bestway.common.transferfactory.entity.TransferFactoryInitBillCommodityInfo;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import javax.swing.JToolBar;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class DgTransGoodsModify extends JDialogBase {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane1 = null;
	private JToolBar jToolBar = null;
	private JButton btnEdit = null;
	private JButton btnSave = null;
	private JButton btnCancel = null;
	private JButton btnExit = null;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JTextField tfMaterialCode = null;
	private JLabel jLabel1 = null;
	private JTextField tfMaterialName = null;
	private JLabel jLabel2 = null;
	private JTextField tfMaterialSpec = null;
	private JLabel jLabel3 = null;
	private JTextField tfUnit = null;
	private JLabel jLabel4 = null;
	private JTextField tfNoTransferQuantity = null;
	private JLabel jLabel5 = null;
	private JTextField tfMemo = null;
	private FptInitBillItem       commInfo                    = null;  //  @jve:decl-index=0:
	private FptManageAction          fptManageAction = null;  //  @jve:decl-index=0:
	private int dataState = -1;
	private boolean isEffective=false;//  @jve:decl-index=0:parse
	/**
	 * @param owner
	 */
	public boolean isEffective() {
		return isEffective;
	}

	public void setEffective(boolean isEffective) {
		this.isEffective = isEffective;
	}
	public DgTransGoodsModify(Frame owner) {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(429, 272);
		this.setTitle("[进出货转厂期初单]商品信息编辑");
		this.setContentPane(getJContentPane1());
		fptManageAction = (FptManageAction) CommonVars.getApplicationContext().getBean("fptManageAction");
		commInfo = new FptInitBillItem();
	}

	/**
	 * This method initializes jContentPane1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane1() {
		if (jContentPane1 == null) {
			jContentPane1 = new JPanel();
			jContentPane1.setLayout(null);
			jContentPane1.add(getJToolBar(), null);
			jContentPane1.add(getJPanel(), null);
		}
		return jContentPane1;
	}

	/**
	 * This method initializes jToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setBounds(new Rectangle(-1, 1, 421, 35));
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnCancel());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * This method initializes btnEdit	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.setSize(new Dimension(36, 33));
			btnEdit.addActionListener(new ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent e) {		    	
                    saveData();
                    dataState = DataState.EDIT;
                    setState();
                }
			});
		}
		return btnEdit;
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
			btnSave.setSize(new Dimension(36, 33));
			btnSave.addActionListener(new ActionListener(){
				    public void actionPerformed(java.awt.event.ActionEvent e) {		    	
	                    saveData();
	                    /*commInfo = fptManageAction.saveTransferFactoryInitBillCommodityInfo(
                                new Request(CommonVars.getCurrUser()),
                                commInfo); */
	                    dataState = DataState.BROWSE;
	                    setState();
	                }	
			});
		}
		return btnSave;
	}
	private void saveData() {
	       commInfo.setNoTransferQuantity((tfNoTransferQuantity.getText()== null)||(tfNoTransferQuantity.getText()=="") ? new Double(
	                        0)
	                        : ("".equals(tfNoTransferQuantity.getText())?new Double(
	    	                        0):Double.valueOf(tfNoTransferQuantity.getText().trim()
	                                .toString()).doubleValue()));
	       commInfo.setMemo(this.tfMemo.getText()==null?"":this.tfMemo.getText());
	   }

	/**
	 * This method initializes btnCancel
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.setSize(new Dimension(36, 33));
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    showData();
                    dataState = DataState.BROWSE;
                    setState();
                }
            });
		}
		return btnCancel;
	}

	/**
	 * This method initializes btnExit	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.setSize(new Dimension(36, 33));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(15, 145, 54, 18));
			jLabel5.setText("备注");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(17, 105, 60, 18));
			jLabel4.setText("未转厂数量");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(208, 66, 61, 18));
			jLabel3.setText("单位");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(18, 69, 57, 18));
			jLabel2.setText("型号规格");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(209, 32, 59, 18));
			jLabel1.setText("物料名称");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(16, 32, 58, 18));
			jLabel.setText("料号");
			
			jPanel.setBounds(new Rectangle(0, 38, 417, 193));
			jPanel.add(jLabel, null);
			jPanel.add(getTfMaterialCode(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getTfMaterialName(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getTfMaterialSpec(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getTfUnit(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(getTfNoTransferQuantity(), null);
			jPanel.add(jLabel5, null);
			jPanel.add(getTfMemo(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes tfMaterialCode	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfMaterialCode() {
		if (tfMaterialCode == null) {
			tfMaterialCode = new JTextField();
			tfMaterialCode.setBounds(new Rectangle(88, 31, 112, 22));
			tfMaterialCode.setPreferredSize(new Dimension(4, 22));
			tfMaterialCode.setEditable(false);
		}
		return tfMaterialCode;
	}

	/**
	 * This method initializes tfMaterialName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfMaterialName() {
		if (tfMaterialName == null) {
			tfMaterialName = new JTextField();
			tfMaterialName.setBounds(new Rectangle(274, 29, 110, 22));
			tfMaterialName.setEditable(false);
		}
		return tfMaterialName;
	}

	/**
	 * This method initializes tfMaterialSpec	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfMaterialSpec() {
		if (tfMaterialSpec == null) {
			tfMaterialSpec = new JTextField();
			tfMaterialSpec.setBounds(89, 65, 110, 22);
			tfMaterialSpec.setEditable(false);
		}
		return tfMaterialSpec;
	}

	/**
	 * This method initializes tfUnit	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfUnit() {
		if (tfUnit == null) {
			tfUnit = new JTextField();
			tfUnit.setBounds(278, 63, 111, 22);
			tfUnit.setEditable(false);
		}
		return tfUnit;
	}

	/**
	 * This method initializes tfNoTransferQuantity	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfNoTransferQuantity() {
		if (tfNoTransferQuantity == null) {
			tfNoTransferQuantity = new JTextField();
			tfNoTransferQuantity.setBounds(91, 101, 170, 22);
		}
		return tfNoTransferQuantity;
	}

	/**
	 * This method initializes tfMemo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfMemo() {
		if (tfMemo == null) {
			tfMemo = new JTextField();
			tfMemo.setBounds(88, 142, 294, 22);
		}
		return tfMemo;
	}
	/**
	 * 设置[进出货转厂期初单]商品信息各个对象属性的值
	 * 
	 * @param currBillDetail
	 */
	public void setGoodBillDetail(TransferFactoryInitBillCommodityInfo initBillCommodityInfo) {
		if(initBillCommodityInfo==null)
		 {
			return;
		 }
		commInfo.setEmsNo(initBillCommodityInfo.getEmsNo());
		commInfo.setCommName(initBillCommodityInfo.getCommName());
		commInfo.setCommSpec(initBillCommodityInfo.getCommSpec());
		commInfo.setUnit(initBillCommodityInfo.getUnit());
	    commInfo.setMemo(initBillCommodityInfo.getMemo());
	    commInfo.setNoTransferQuantity(initBillCommodityInfo.getNoTransferQuantity()==null? new Double(0):initBillCommodityInfo.getNoTransferQuantity());
	    commInfo.setMemo(initBillCommodityInfo.getMemo());
		fillData(initBillCommodityInfo);
	}
	/**
	 * 把商品信息设置到控件上
	 * @param initBillCommodityInfo
	 */
	private void fillData(TransferFactoryInitBillCommodityInfo initBillCommodityInfo) {
		if(initBillCommodityInfo==null)
		 {
			return;
		 }
		tfMaterialCode.setText(initBillCommodityInfo.getEmsNo());
		tfMaterialName.setText(initBillCommodityInfo.getCommName());
		tfMaterialSpec.setText(initBillCommodityInfo.getCommSpec());
		tfUnit.setText(initBillCommodityInfo.getUnit()==null?"":initBillCommodityInfo.getUnit().getName());
		tfNoTransferQuantity.setText("");
		tfMemo.setText("");
		dataState = DataState.EDIT;
        setState();
	}
	
	/**
	 * 返回进出厂货物期初单商品信息
	 * @return
	 */
	public TransferFactoryInitBillCommodityInfo getInitBillCommodityInfo(){
		TransferFactoryInitBillCommodityInfo initBillCommodityInfo = new TransferFactoryInitBillCommodityInfo();	
		initBillCommodityInfo.setEmsNo(commInfo.getEmsNo().trim());
		initBillCommodityInfo.setCommName(commInfo.getCommName().trim());
		initBillCommodityInfo.setCommSpec(commInfo.getCommSpec().trim());
		initBillCommodityInfo.setUnit(commInfo.getUnit());
		initBillCommodityInfo.setMemo(commInfo.getMemo()==null?"":commInfo.getMemo().trim());
		initBillCommodityInfo.setNoTransferQuantity(commInfo.getNoTransferQuantity()==null?new Double(0):commInfo.getNoTransferQuantity());
		return initBillCommodityInfo;
	}
	
	private void showData() {
	        if (commInfo == null) {
	            return;
	        }
	        tfNoTransferQuantity.setText(commInfo.getNoTransferQuantity()+"");
	        tfMemo.setText(commInfo.getMemo());
	    }
	 /**
		 * 设置控件状态
		 * 
		 */
	private void setState() {
	        this.btnEdit.setEnabled(dataState == DataState.BROWSE&&!isEffective);
	        this.btnSave.setEnabled(dataState != DataState.BROWSE&&!isEffective);
	        this.btnCancel.setEnabled(dataState != DataState.BROWSE&&!isEffective);
	        this.btnExit.setEnabled(dataState == DataState.BROWSE&&!isEffective);
	        this.tfNoTransferQuantity.setEditable(dataState != DataState.BROWSE&&!isEffective);
	        this.tfMemo.setEditable(dataState != DataState.BROWSE&&!isEffective);
	    }



}  
