/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.impexprequest;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.enc.entity.MakeCusomsDeclarationParam;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.dzsc.customslist.action.DzscListAction;
import com.bestway.dzsc.customslist.entity.DzscCustomsBillList;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated impExpType comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgDzscMakeCustomsList extends JDialogBase {

	private JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JLabel lbHeadText = null;

	private JPanel jPanel1 = null;

	private JButton btnPrevious = null;

	private JButton btnNext = null;

	private JButton btnDone = null;

	private JButton btnExit = null;

	private JPanel pnContext = null;

	private int impExpType = -1;

	private int step = 0;

	private boolean isOk = false;

	private boolean isImportGoods = true;

	private DzscCustomsBillList applyToCustomsBillList = null;// 生成清单单据头

	private MakeCusomsDeclarationParam makeparam = null;

	private ScmCoc scmCoc = null;

	private List parentList = null;

	private JTableListModel tableMode = null;

	private List commodityList = null;// 获得申请单据体商品信息(已经备案过)

	private List checkList = null;

	private String declareState = null;

	private boolean isNewRecord = true;

	private PnDzscMakeCustomsList pnMakeApplyToCustoms = null;

	private PnDzscMakeCustomsList2 pnMakeApplyToCustoms2 = null;

	private PnDzscMakeCustomsList3 pnMakeApplyToCustoms3 = null;

	private DzscListAction dzscListAction = null;

	private int materielProductType = -1;

	private boolean isNewBgd = false;

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
	public DgDzscMakeCustomsList() {
		super();
		initialize();
		this.dzscListAction = (DzscListAction) CommonVars
				.getApplicationContext().getBean("dzscListAction");
		this.declareState = DeclareState.PROCESS_EXE;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("进出口申请单转---报关清单");
		this.setContentPane(getJContentPane());
		this.setSize(633, 396);
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
	public PnDzscMakeCustomsList3 getPnMakeApplyToCustomsBill3() {
		if (pnMakeApplyToCustoms3 == null) {
			pnMakeApplyToCustoms3 = new PnDzscMakeCustomsList3();
			pnMakeApplyToCustoms3.setBounds(0, 0, 630, 248);
		}
		return pnMakeApplyToCustoms3;
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
						if (getPnMakeApplyToCustoms()
								.validateImpExpTypeIsNull()) {
							JOptionPane.showMessageDialog(null, "进出口类型不可为空!",
									"提示", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (getPnMakeApplyToCustoms()
								.validateListTypeIsNull()) {
							JOptionPane.showMessageDialog(null, "清单类型不可为空!",
									"提示", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (getPnMakeApplyToCustoms()
								.validateApplyToCustomsBillListIsNull()) {
							JOptionPane.showMessageDialog(null, "报关清单对象不可为空!",
									"提示", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (getPnMakeApplyToCustoms().getDzscEmsPorHead() == null) {
							JOptionPane.showMessageDialog(null,
									"没有正在执行的电子手册,不能生成报关清单!", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}

					} else if (step == 1) {// 第一步
						if (getPnMakeApplyToCustoms2().getParentList() == null
								|| getPnMakeApplyToCustoms2().getParentList()
										.size() <= 0) {
							JOptionPane.showMessageDialog(null, "请选择进出口申请单据!",
									"提示", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					} else if (step == 2) {// 第二步
						commodityList = pnMakeApplyToCustoms3
								.getCommodityList();// 申请单据体
						if (commodityList == null || commodityList.size() <= 0) {
							JOptionPane.showMessageDialog(null,
									"请选择进出口申请单据商品信息!", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (!pnMakeApplyToCustoms3.vaildateData()) {
							JOptionPane.showMessageDialog(null,
									"选择的商品信息中,着色的记录没有对应的合同备案数据!", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						makeDzscCustomsBillList();
					}
					step++;
					gotoStep(step);
				}
			});
		}
		return btnNext;
	}

	// //注：此方法检查单据体是否已备案
	// private boolean isbeian(){
	// String tishi = "";
	// EmsHeadH2k emsHeadH2k = null;
	// //emsFrom 1:电子帐册备案，2：电子帐册正在执行，3：企业内部归并
	// String emsFrom = CommonVars.getEmsFrom();
	// List checkList = null;
	// if (!isMakeApplyToCustoms){ //不转报关单
	// if (emsFrom.equals("1")){ //电子帐册来自归并关系备案
	// declareState = DeclareState.APPLY_POR;
	// emsEdiMergerHead = manualDeclearAction
	// .findEmsEdiMergerHeadByDeclareState(new Request(CommonVars
	// .getCurrUser()), declareState);
	// if (emsEdiMergerHead == null){
	// JOptionPane.showMessageDialog(null,
	// "电子帐册物料来源参数设置来自：归并关系备案，备案数据为空不能继续下去!",
	// "提示", JOptionPane.INFORMATION_MESSAGE);
	// return false;
	// }
	// checkList = encAction.checkTempImpExpCommodityInfoByFinishProduct(new
	// Request(
	// CommonVars.getCurrUser()), commodityList,
	// emsEdiMergerHead,String.valueOf(materielProductType));
	// tishi = "归并关系备案";
	// } else if (emsFrom.equals("2")){ //电子帐册来自归并关系正在执行
	// declareState = DeclareState.PROCESS_EXE;
	// emsEdiMergerHead = manualDeclearAction
	// .findEmsEdiMergerHeadByDeclareState(new Request(CommonVars
	// .getCurrUser()), declareState);
	// if (emsEdiMergerHead == null){
	// JOptionPane.showMessageDialog(null,
	// "电子帐册物料来源参数设置来自：归并关系正在执行，执行数据为空不能继续下去!",
	// "提示", JOptionPane.INFORMATION_MESSAGE);
	// return false;
	// }
	// checkList = encAction.checkTempImpExpCommodityInfoByFinishProduct(new
	// Request(
	// CommonVars.getCurrUser()), commodityList,
	// emsEdiMergerHead,String.valueOf(materielProductType));
	// tishi = "归并关系正在执行";
	// } else { //电子帐册来自企业内部归并
	// checkList = encAction.checkTempImpExpCommodityInfoByFinishProduct(new
	// Request(
	// CommonVars.getCurrUser()), commodityList,
	// null,String.valueOf(materielProductType));
	// }
	// } else { //同时转报关单(使用企业：思瑞克斯)
	//        	
	// List list = manualDeclearAction.findEmsHeadH2kInExecuting(new Request(
	// CommonVars.getCurrUser(), true));
	// if (list == null || list.size() <= 0) {
	// JOptionPane.showMessageDialog(null, "没有正在执行的电子帐册，不能转换【报关单】!", "提示",
	// JOptionPane.INFORMATION_MESSAGE);
	// return false;
	// }
	// emsHeadH2k = (EmsHeadH2k) (list.get(0));//正在执行的电子帐册
	// if (emsFrom.equals("1")){//来自归并关系备案
	// declareState = DeclareState.APPLY_POR;
	// emsEdiMergerHead = manualDeclearAction
	// .findEmsEdiMergerHeadByDeclareState(new Request(CommonVars
	// .getCurrUser()), declareState);
	// if (emsEdiMergerHead == null){
	// JOptionPane.showMessageDialog(null,
	// "电子帐册物料来源参数设置来自：归并关系备案，备案数据为空不能继续下去!",
	// "提示", JOptionPane.INFORMATION_MESSAGE);
	// return false;
	// }
	// checkList = encAction
	// .checkTempImpExpCommodityInfoByFinishProduct2(new Request(
	// CommonVars.getCurrUser()), commodityList,emsHeadH2k,
	// emsEdiMergerHead,String.valueOf(materielProductType));
	// } else if (emsFrom.equals("2")){//来自归并关系正在执行
	// declareState = DeclareState.PROCESS_EXE;
	// emsEdiMergerHead = manualDeclearAction
	// .findEmsEdiMergerHeadByDeclareState(new Request(CommonVars
	// .getCurrUser()), declareState);
	// if (emsEdiMergerHead == null){
	// JOptionPane.showMessageDialog(null,
	// "电子帐册物料来源参数设置来自：归并关系正在执行，执行数据为空不能继续下去!",
	// "提示", JOptionPane.INFORMATION_MESSAGE);
	// return false;
	// }
	// checkList = encAction
	// .checkTempImpExpCommodityInfoByFinishProduct2(new Request(
	// CommonVars.getCurrUser()), commodityList,emsHeadH2k,
	// emsEdiMergerHead,String.valueOf(materielProductType));
	// } else if (emsFrom.equals("3")){//企业内部归并 (现在比较常用)
	// checkList = encAction
	// .checkTempImpExpCommodityInfoByFinishProduct2(new Request(
	// CommonVars.getCurrUser()), commodityList,emsHeadH2k,
	// null,String.valueOf(materielProductType));
	// }
	// tishi = "电子帐册";
	// }
	// if (checkList != null) {
	// if (checkList.size() > 0) {
	// pnMakeApplyToCustoms3.setValidateDataColor(checkList);
	// JOptionPane.showMessageDialog(null, "蓝色的数据记录在"+tishi+"中没有备案!!",
	// "提示", JOptionPane.INFORMATION_MESSAGE);
	// return false;
	// }
	// }
	// return true;
	// }

	private PnDzscMakeCustomsList3 getPnMakeApplyToCustoms3() {
		if (pnMakeApplyToCustoms3 == null) {
			pnMakeApplyToCustoms3 = new PnDzscMakeCustomsList3();
			pnMakeApplyToCustoms3.setBounds(0, 0, 630, 248);
		}
		return pnMakeApplyToCustoms3;
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
					// done();
				}
			});
		}
		return btnDone;
	}

	/**
	 * 生成报关清单
	 */
	private void makeDzscCustomsBillList() {
		try {
			boolean isMateriel = this.materielProductType == Integer
					.parseInt(MaterielType.MATERIEL);
			applyToCustomsBillList.setListNo(this.getMaxBillListNo()); // 清单序号
			List list = null;
			// String emsFrom = CommonVars.getEmsFrom();
			list = this.dzscListAction.makeApplyToCustomsRequestBillList(
					new Request(CommonVars.getCurrUser()),
					applyToCustomsBillList, pnMakeApplyToCustoms3
							.getCommodityList(), isMateriel, this.isNewRecord,true);
			applyToCustomsBillList = (DzscCustomsBillList) list.get(0);// 清单单据头
			List impExpRequestList = (List) list.get(1);// 申请单据头
			if (isNewBgd) {
				this.tableMode.updateRows(impExpRequestList);
			}
			JOptionPane.showMessageDialog(null, "报关清单生成成功!\n报关清单编号为:"
					+ applyToCustomsBillList.getListNo(), "提示",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "报关清单生成失败," + ex.getMessage(),
					"提示", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * 保存时获得最大的报关清单编号
	 * 
	 * @return
	 */
	private String getMaxBillListNo() {
		return dzscListAction.getApplyToCustomsBillListMaxNo(new Request(
				CommonVars.getCurrUser()));
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

	private PnDzscMakeCustomsList getPnMakeApplyToCustoms() {
		if (pnMakeApplyToCustoms == null) {
			pnMakeApplyToCustoms = new PnDzscMakeCustomsList();
			pnMakeApplyToCustoms.setBounds(0, 0, 630, 248);
			pnMakeApplyToCustoms.setImportGoods(isImportGoods);
			pnMakeApplyToCustoms.setType(impExpType);
			pnMakeApplyToCustoms.setVisible(true);
		}
		return pnMakeApplyToCustoms;
	}

	private PnDzscMakeCustomsList2 getPnMakeApplyToCustoms2() {
		if (pnMakeApplyToCustoms2 == null) {
			pnMakeApplyToCustoms2 = new PnDzscMakeCustomsList2();
			pnMakeApplyToCustoms2.setBounds(0, 0, 630, 248);
			pnMakeApplyToCustoms2.setVisible(true);
		}
		return pnMakeApplyToCustoms2;
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

	private void initP0() {
		this.pnContext.removeAll();
		this.pnContext.add(this.getPnMakeApplyToCustoms(), null);
		this.lbHeadText.setText("第一步：设置生成报关清单表头数据");
	}

	private void initP1() {
		this.pnContext.removeAll();
		this.pnContext.add(this.getPnMakeApplyToCustoms2(), null);
		this.impExpType = this.getPnMakeApplyToCustoms().getImpExpType();
		this.isNewRecord = this.getPnMakeApplyToCustoms().getIsNewRecord();
		this.applyToCustomsBillList = this.getPnMakeApplyToCustoms()
				.getApplyToCustomsBillList();
		this.makeparam = this.getPnMakeApplyToCustoms()
				.getMakeCusomsDeclarationParam();
		this.getPnMakeApplyToCustoms2().setImpExpType(this.impExpType);
		this.getPnMakeApplyToCustoms2().setVisible(true);
		this.lbHeadText.setText("第二步：选择需要生成报关清单的单据");
	}

	private void initP2() {
		this.pnContext.removeAll();
		this.pnContext.add(this.getPnMakeApplyToCustomsBill3(), null);
		this.parentList = this.getPnMakeApplyToCustoms2().getParentList();
		this.getPnMakeApplyToCustomsBill3().setParentList(parentList);
		this.getPnMakeApplyToCustomsBill3().setDzscEmsPorHead(
				this.getPnMakeApplyToCustoms().getDzscEmsPorHead());
		this.getPnMakeApplyToCustomsBill3().setVisible(true);
		this.lbHeadText.setText("第三步：选择需要生成报关清单商品信息");
	}

	// private void initP3(){
	// this.pnContext.removeAll();
	// this.pnContext.add(this.getPnMakeApplyToCustomsBill4(), null);
	// List list = dzscListAction.findAtcMergeAfterComInfoByListID(new
	// Request(CommonVars.getCurrUser()),applyToCustomsBillList);
	// this.getPnMakeApplyToCustomsBill4().setImpExpFlat(materielProductType);
	// this.getPnMakeApplyToCustomsBill4().setParentList(list);
	// this.getPnMakeApplyToCustomsBill4().setVisible(true);
	// this.lbHeadText.setText("第四步：选择需要生成报关单商品信息(电子帐册)");
	// }

	// private boolean makeList() {
	// // 生成报关清单
	// if (this.makeApplyToCustomsBill() == true) {
	// JOptionPane.showMessageDialog(null, "报关清单生成成功!\n报关清单编号为:"
	// + applyToCustomsBillList.getListNo(), "提示",
	// JOptionPane.INFORMATION_MESSAGE);
	// return true;
	// } else {
	// JOptionPane.showMessageDialog(null, "报关清单生成失败,数据可能没有在电子手册中备案!!",
	// "提示", JOptionPane.INFORMATION_MESSAGE);
	// return false;
	// }
	// }

	private void gotoStep(int step) {
		switch (step) {
		case 0:
			initP0();
			break;
		case 1:
			if (!this.isNewBgd) {
				initP0();
			}
			initP1();
			break;
		case 2:
			initP2();
			break;

		// case 3:
		// initP3();
		// break;
		}
		this.pnContext.repaint();
		this.pnContext.validate();
		setState();
	}

	private void setState() {
		// if (this.isNewBgd){
		// this.btnPrevious.setEnabled(this.step > 0);
		// } else {
		// this.btnPrevious.setEnabled(this.step > 1);
		// }
		this.btnPrevious.setEnabled(this.step > 0);
		this.btnNext.setEnabled(this.step < 3);
		this.btnDone.setEnabled(this.step == 3);
	}

	public void setVisible(boolean b) {
		if (b) {
			// if (!isNewBgd){ //直接从报关单中新增
			// this.isMakeApplyToCustoms = true;//转报关单
			// this.step = 1;
			// } else {
			this.step = 0;
			// }
			this.gotoStep(this.step);
		}
		super.setVisible(b);
	}

	/**
	 * @param isNewBgd
	 *            The isNewBgd to set.
	 */
	public void setNewBgd(boolean isNewBgd) {
		this.isNewBgd = isNewBgd;
	}
	// /**
	// * @param customs The customs to set.
	// */
	// public void setCustoms(CustomsDeclaration customs) {
	// this.customs = customs;
	// }
	// /**
	// * @return Returns the customs.
	// */
	// public CustomsDeclaration getCustoms() {
	// return customs;
	// }
} // @jve:decl-index=0:visual-constraint="10,10"
