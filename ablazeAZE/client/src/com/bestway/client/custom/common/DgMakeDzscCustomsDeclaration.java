/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.custom.common;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.bestway.bcs.client.contractexe.PnMakeBcsCustomsDeclaration3;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.dzsc.contractexe.action.DzscContractExeAction;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclaration;
import com.bestway.dzsc.contractexe.entity.TempDzscImpExpCommodityInfo;
import com.bestway.ui.message.DgMessage;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated impExpType comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgMakeDzscCustomsDeclaration extends JDialogBase {

	private JPanel							jContentPane					= null;
	private JPanel							jPanel							= null;
	private JLabel							lbHeadText						= null;
	private JPanel							jPanel1							= null;
	private JButton							btnPrevious						= null;
	private JButton							btnNext							= null;
	private JButton							btnDone							= null;
	private JButton							btnExit							= null;
	private JPanel							pnContext						= null;
	private int								impExpType						= -1;
	private int								step							= 0;
	private boolean							isOk							= false;
	private boolean							isImportGoods					= true;
	private DzscCustomsDeclaration			dzscCustomsDeclaration			= null;  //  @jve:decl-index=0:
	private ScmCoc							scmCoc							= null;
	private List							parentList						= null;
	private JTableListModel					tableMode						= null;
	private List							commodityList					= null;
	private List							checkList						= null;
	private String							declareState					= null;
	private boolean							isNewRecord						= true;
	private PnMakeDzscCustomsDeclaration	pnMakeDzscCustomsDeclaration	= null;
	private PnMakeDzscCustomsDeclaration2	pnMakeDzscCustomsDeclaration2	= null;
	private PnMakeDzscCustomsDeclaration3	pnMakeDzscCustomsDeclaration3	= null;
	private int								materielProductType				= -1;
	private boolean							isInRequestBill					= true;	// 是来自申请单
	private DzscContractExeAction			dzsccontractExeAction			= null;
	private boolean isAutoSelectContract = false;
	/**
	 * @return Returns the isOk.
	 */
	public boolean isOk() {
		return isOk;
	}

	/**
	 * @return Returns the materielProductType.
	 */
	public int getMaterielProductType() {
		return materielProductType;
	}

	/**
	 * @param materielProductType
	 *            The materielProductType to set.
	 */
	public void setMaterielProductType(int materielProductType) {
		this.materielProductType = materielProductType;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgMakeDzscCustomsDeclaration() {
		super();
		initialize();
		dzsccontractExeAction = (DzscContractExeAction) CommonVars
				.getApplicationContext().getBean("dzscContractExeAction");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("进出口申请单转---报关单(电子手册)");
		this.setContentPane(getJContentPane());
		this.setSize(633, 396);
	}

	/**
	 * @return Returns the dzscCustomsDeclaration.
	 */
	public DzscCustomsDeclaration getDzscCustomsDeclaration() {
		return dzscCustomsDeclaration;
	}

	/**
	 * @param dzscCustomsDeclaration
	 *            The dzscCustomsDeclaration to set.
	 */
	public void setDzscCustomsDeclaration(
			DzscCustomsDeclaration dzscCustomsDeclaration) {
		this.dzscCustomsDeclaration = dzscCustomsDeclaration;
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
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJPanel1(), null);
			jContentPane.add(getPnContext(), null);
		}
		return jContentPane;
	}

	/**
	 * @return Returns the impExpType.
	 */
	public int getImpExpType() {
		return impExpType;
	}

	/**
	 * @param impExpType
	 *            The impExpType to set.
	 */
	public void setImpExpType(int type) {
		this.impExpType = type;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			lbHeadText = new JLabel();
			jPanel.setLayout(null);
			jPanel.setBounds(1, 1, 623, 40);
			jPanel.setBackground(java.awt.Color.white);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			lbHeadText.setBounds(3, 12, 609, 15);
			lbHeadText.setText("JLabel");
			jPanel.add(lbHeadText, null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(2, 320, 624, 41);
			jPanel1
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel1.add(getBtnPrevious(), null);
			jPanel1.add(getBtnNext(), null);
			jPanel1.add(getBtnDone(), null);
			jPanel1.add(getBtnExit(), null);
		}
		return jPanel1;
	}

	/**
	 * @return Returns the tableMode.
	 */
	public JTableListModel getTableMode() {
		return tableMode;
	}

	/**
	 * @param tableMode
	 *            The tableMode to set.
	 */
	public void setTableMode(JTableListModel tableMode) {
		this.tableMode = tableMode;
	}

	/**
	 * @return Returns the parentList.
	 */
	public List getParentList() {
		return parentList;
	}

	/**
	 * @param parentList
	 *            The parentList to set.
	 */
	public void setParentList(List parentList) {
		this.parentList = parentList;
	}

	/**
	 * @return Returns the pnMakeCustomsEnvelopBill3.
	 */
	public PnMakeDzscCustomsDeclaration3 getPnMakeDzscCustomsDeclaration3() {
		if (pnMakeDzscCustomsDeclaration3 == null) {
			pnMakeDzscCustomsDeclaration3 = new PnMakeDzscCustomsDeclaration3();
			pnMakeDzscCustomsDeclaration3.setBounds(0, 0, 630, 248);
		}
		return pnMakeDzscCustomsDeclaration3;
	}

	/**
	 * @return Returns the isImportGoods.
	 */
	public boolean isImportGoods() {
		return isImportGoods;
	}

	/**
	 * @param isImportGoods
	 *            The isImportGoods to set.
	 */
	public void setImportGoods(boolean isImportGoods) {
		this.isImportGoods = isImportGoods;
	}

	/**
	 * This method initializes btnPrevious
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setBounds(158, 9, 72, 24);
			btnPrevious.setText("上一步");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					step--;
					gotoStep(step);
				}
			});
		}
		return btnPrevious;
	}

	/**
	 * This method initializes btnNext
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setBounds(233, 9, 72, 24);
			btnNext.setText("下一步");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (step == 0) {// 开始

						if (!getPnMakeDzscCustomsDeclaration().vaildateData()) {
							return;
						}

					} else if (step == 1) {// 第一步
						if (!getPnMakeDzscCustomsDeclaration2().vaildateData()) {
							return;
						}
					} else if (step == 2) {
						PnMakeDzscCustomsDeclaration3 p3 = getPnMakeDzscCustomsDeclaration3();
						p3.setAutoSelectContract(isAutoSelectContract());
					}
					step++;
					gotoStep(step);
				}
			});
		}
		return btnNext;
	}

	/**
	 * This method initializes btnDone
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDone() {
		if (btnDone == null) {
			btnDone = new JButton();
			btnDone.setBounds(309, 9, 60, 24);
			btnDone.setText("执行");
			btnDone.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					done();
				}
			});
		}
		return btnDone;
	}

	/**
	 * 执行
	 */
//	private void done() {
//		if (!getPnMakeDzscCustomsDeclaration3().vaildateData()) {
//			return;
//		}
//		commodityList = pnMakeDzscCustomsDeclaration3.getCommodityList();
//		//
//		// 生成报关单
//		//
//		try {
//			boolean isProduct = this.materielProductType == Integer
//					.parseInt(MaterielType.FINISHED_PRODUCT);
//			Double weightpara = dzsccontractExeAction
//					.findweightpara(new Request(CommonVars.getCurrUser()));
//			if(weightpara == null || 0==weightpara){
//				weightpara = 1.0;
//			}
//			List list = this.dzsccontractExeAction.makeDzscCustomsDeclaration(
//					new Request(CommonVars.getCurrUser()),
//					dzscCustomsDeclaration, parentList, commodityList,
//					isProduct, weightpara);
//			dzscCustomsDeclaration = (DzscCustomsDeclaration) list.get(0);
//			List impExpRequestList = (List) list.get(1);
//			if (this.isInRequestBill == true) {
//				this.tableMode.updateRows(impExpRequestList);
//			}
//			JOptionPane.showMessageDialog(this, "报关单生成成功!!\n报关单编号为:"
//					+ dzscCustomsDeclaration.getSerialNumber(), "提示",
//					JOptionPane.INFORMATION_MESSAGE);
//		} catch (Exception ex) {
//			JOptionPane.showMessageDialog(this, "报关清单生成失败,数据可能没有在电子帐册中备案!!",
//					"提示", JOptionPane.INFORMATION_MESSAGE);
//			ex.printStackTrace();
//			return;
//		}
//		this.dispose();
//	}
	
	private void done() {
		if (!getPnMakeDzscCustomsDeclaration3().vaildateData()) {
			return;
		}
		List<TempDzscImpExpCommodityInfo> temps = pnMakeDzscCustomsDeclaration3.getCommodityList();
		boolean isProduct = this.materielProductType == Integer.parseInt(MaterielType.FINISHED_PRODUCT);
		//生成报关单
		String message = 
			this.dzsccontractExeAction.makeDzscCustomsDeclarationFromTemp(new Request(CommonVars.getCurrUser()),dzscCustomsDeclaration,temps,isProduct,isNewRecord);
//		DgMessage dgMessage = new DgMessage("生成报关单", message);
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setBounds(372, 9, 60, 24);
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	private PnMakeDzscCustomsDeclaration getPnMakeDzscCustomsDeclaration() {
		if (pnMakeDzscCustomsDeclaration == null) {
			pnMakeDzscCustomsDeclaration = new PnMakeDzscCustomsDeclaration();
			pnMakeDzscCustomsDeclaration.setBounds(0, 0, 630, 248);
		}
		return pnMakeDzscCustomsDeclaration;
	}

	private PnMakeDzscCustomsDeclaration2 getPnMakeDzscCustomsDeclaration2() {
		if (pnMakeDzscCustomsDeclaration2 == null) {
			pnMakeDzscCustomsDeclaration2 = new PnMakeDzscCustomsDeclaration2();
			pnMakeDzscCustomsDeclaration2.setBounds(0, 0, 630, 248);
		}
		return pnMakeDzscCustomsDeclaration2;
	}

	/**
	 * This method initializes pnContext
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnContext() {
		if (pnContext == null) {
			pnContext = new JPanel();
			pnContext.setLayout(null);
			pnContext.setBounds(1, 41, 623, 278);
			pnContext.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,
					0, 0, 0));
		}
		return pnContext;
	}

	private void initPn0(){
		this.pnContext.removeAll();
		this.pnContext.add(this.getPnMakeDzscCustomsDeclaration(), null);
		this.getPnMakeDzscCustomsDeclaration()
				.setImportGoods(isImportGoods);
		this.getPnMakeDzscCustomsDeclaration().setIsfromCustoms(
				!isInRequestBill);
		this.getPnMakeDzscCustomsDeclaration().setDzscCustomsDeclaration(
				this.dzscCustomsDeclaration);
		this.getPnMakeDzscCustomsDeclaration().setType(impExpType);
		this.getPnMakeDzscCustomsDeclaration().setVisible(true);
		this.lbHeadText.setText("第一步：设置生成电子手册报关单表头数据");
	}
	private void initPn1(){
		this.pnContext.removeAll();
		this.pnContext.add(this.getPnMakeDzscCustomsDeclaration2(), null);
		this.impExpType = this.getPnMakeDzscCustomsDeclaration()
				.getImpExpType();
		this.isNewRecord = this.getPnMakeDzscCustomsDeclaration()
				.getIsNewRecord();
		this.dzscCustomsDeclaration = this
				.getPnMakeDzscCustomsDeclaration()
				.getDzscCustomsDeclaration();

		this.getPnMakeDzscCustomsDeclaration2().setImpExpType(
				this.impExpType);
		this.getPnMakeDzscCustomsDeclaration2().setVisible(true);
		this.lbHeadText.setText("第二步：选择需要生成电子手册报关单的单据");
	}
	private void initPn2(){
		this.pnContext.removeAll();
		this.pnContext.add(this.getPnMakeDzscCustomsDeclaration3(), null);
		this.parentList = this.getPnMakeDzscCustomsDeclaration2()
				.getParentList();
		this.getPnMakeDzscCustomsDeclaration3().setDzscEmsPorHead(
				this.getPnMakeDzscCustomsDeclaration().getDzscEmsPorHead());
		this.getPnMakeDzscCustomsDeclaration3().setParentList(parentList);
		this.getPnMakeDzscCustomsDeclaration3().setMaterielProductType(
				this.materielProductType);
		this.getPnMakeDzscCustomsDeclaration3().setVisible(true);
		this.getPnMakeDzscCustomsDeclaration3().setAutoSelectContract(
				this.getPnMakeDzscCustomsDeclaration().getIsAutoSelectContract());
		this.lbHeadText.setText("第三步：选择需要生成电子手册报关单的商品信息");
		this.getPnMakeDzscCustomsDeclaration3().clearNumAndMoneyMap();
		this.getPnMakeDzscCustomsDeclaration3().setNewRecord(this.getPnMakeDzscCustomsDeclaration().getIsNewRecord());
	}
	private void gotoStep(int step) {
		switch (step) {
		case 0:
			initPn0();
			break;
		case 1:
			initPn1();			
			break;
		case 2:
			initPn2();
			break;
		}
		this.pnContext.repaint();
		this.pnContext.validate();
		setState();
	}

	private void setState() {
		//this.btnPrevious.setEnabled(this.step > 0);
		this.btnPrevious.setEnabled(this.step > 0);
		this.btnNext.setEnabled(this.step < 2);
		this.btnDone.setEnabled(this.step == 2);
	}

	public void setVisible(boolean b) {
		if (b) {
			this.gotoStep(this.step);
		}
		super.setVisible(b);
	}

	/**
	 * @return Returns the isInRequestBill.
	 */
	public boolean isInRequestBill() {
		return isInRequestBill;
	}

	/**
	 * @param isInRequestBill
	 *            The isInRequestBill to set.
	 */
	public void setInRequestBill(boolean isInRequestBill) {
		this.isInRequestBill = isInRequestBill;
	}
	
	public boolean isAutoSelectContract() {
		return isAutoSelectContract;
	}

	public void setAutoSelectContract(boolean isAutoSelectContract) {
		this.isAutoSelectContract = isAutoSelectContract;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
