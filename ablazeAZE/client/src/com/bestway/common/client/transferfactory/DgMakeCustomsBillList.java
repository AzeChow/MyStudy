/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.transferfactory;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.dzsc.customslist.entity.DzscCustomsBillList;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgMakeCustomsBillList extends DgCommon {

	private JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JLabel lbHeadText = null;

	private JPanel jPanel1 = null;

	private JButton btnPrevious = null;

	private JButton btnNext = null;

	private JButton btnDone = null;

	private JButton btnExit = null;

	private JPanel pnContext = null;

	private int step = 0;

	private boolean isOk = false;

	private boolean isImportGoods = true;

	private ApplyToCustomsBillList applyToCustomsBillList = null;

	private DzscCustomsBillList dzscCustomsBillList = null;

	private ScmCoc scmCoc = null;

	private String emsNo = null;

	private List parentList = null;

	private JTableListModel tableMode = null;

	private List commodityList = null;

	private List checkList = null;

	private EmsEdiMergerHead emsEdiMergerHead = null;

	private String declareState = null;

	private boolean isNewRecord = true;

	private PnMakeCustomsBillList pnMakeApplyToCustomsBill = null;

	private PnMakeCustomsBillList2 pnMakeApplyToCustomsBill2 = null;

	private PnMakeCustomsBillList3 pnMakeApplyToCustomsBill3 = null;

	/**
	 * @return Returns the isOk.
	 */
	public boolean isOk() {
		return isOk;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgMakeCustomsBillList() {
		super();
		initialize();
		//
		// 根据系统参数来初始化
		//
		this.declareState = DeclareState.PROCESS_EXE;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("结转单据转---报关清单");
		this.setContentPane(getJContentPane());
		this.setSize(506, 365);

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
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			lbHeadText = new JLabel();
			jPanel.setLayout(null);
			jPanel.setBounds(1, 1, 500, 40);
			jPanel.setBackground(java.awt.Color.white);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			lbHeadText.setBounds(3, 12, 496, 15);
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
			jPanel1.setBounds(1, 289, 500, 41);
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
	public PnMakeCustomsBillList3 getPnMakeApplyToCustomsBill3() {
		if (pnMakeApplyToCustomsBill3 == null) {
			pnMakeApplyToCustomsBill3 = new PnMakeCustomsBillList3();
			pnMakeApplyToCustomsBill3.setBounds(0, 0, 500, 248);
		}
		return pnMakeApplyToCustomsBill3;
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
			btnPrevious.setBounds(194, 9, 72, 24);
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
			btnNext.setBounds(269, 9, 72, 24);
			btnNext.setText("下一步");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (step == 0) {// 开始
						if (getPnMakeApplyToCustomsBill().getProjectType() < 0) {
							JOptionPane.showMessageDialog(null, "请先选择项目类型!!",
									"提示", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (getPnMakeApplyToCustomsBill().getEmsNo() == null
								|| "".equals(getPnMakeApplyToCustomsBill()
										.getEmsNo())) {
							JOptionPane.showMessageDialog(null,
									"没有正在执行的电子帐册/手册,不能生成报关清单!!", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (getPnMakeApplyToCustomsBill().validateDataIsNull()) {
							JOptionPane.showMessageDialog(null,
									isImportGoods == true ? "供应商不可为空!!"
											: "客户不可为空!!", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (getPnMakeApplyToCustomsBill()
								.validateApplyToCustomsBillListIsNull()) {
							JOptionPane.showMessageDialog(null, "报关清对象不可为空!!",
									"提示", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					} else if (step == 1) {// 第一步
						if (getPnMakeApplyToCustomsBill2().getParentList() == null
								|| getPnMakeApplyToCustomsBill2()
										.getParentList().size() <= 0) {
							JOptionPane.showMessageDialog(null, "请选择结转单据!!",
									"提示", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (!getPnMakeApplyToCustomsBill2().checkSelectedList()) {
							JOptionPane.showMessageDialog(null, "选中的转厂单不属于同一关封!!",
									"提示", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
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
			btnDone.setBounds(345, 9, 60, 24);
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
	private void done() {
		// if (pnMakeApplyToCustomsBill4.validateDataIsAvailability() == false)
		// {
		// return;
		// }
		commodityList = pnMakeApplyToCustomsBill3.getCommodityList();
		if (commodityList == null || commodityList.size() <= 0) {
			JOptionPane.showMessageDialog(null, "请选择转成报关清单的商品信息!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (getPnMakeApplyToCustomsBill().getProjectType() == ProjectType.BCUS) {
			emsEdiMergerHead = manualDeclearAction
					.findEmsEdiMergerHeadByDeclareState(new Request(CommonVars
							.getCurrUser()), declareState);
			if (emsEdiMergerHead == null) {
				JOptionPane.showMessageDialog(null, "归并关系中没有正在执行的状态记录!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
		// if (isImportGoods == true) {
		// checkList = transferFactoryManageAction
		// .checkTempTransferFactoryCommodityInfoByMateriel(
		// new Request(CommonVars
		// .getCurrUser()),
		// commodityList, emsEdiMergerHead);
		// } else {
		// checkList = transferFactoryManageAction
		// .checkTempTransferFactoryCommodityInfoByFinishProduct(
		// new Request(CommonVars
		// .getCurrUser()),
		// commodityList, emsEdiMergerHead);
		// }
		// if (checkList.size() > 0) {
		// pnMakeApplyToCustomsBill3
		// .setValidateDataColor(checkList);
		// JOptionPane.showMessageDialog(null,
		// "蓝色的数据记录在归并关系没有备案!!", "提示",
		// JOptionPane.INFORMATION_MESSAGE);
		// return;
		// }
		//
		// 生成报关清单
		//
//		if (this.makeApplyToCustomsBill() == true) {
//			JOptionPane.showMessageDialog(null, "报关清单生成成功!!", "提示",
//					JOptionPane.INFORMATION_MESSAGE);
//		} else {
//			JOptionPane.showMessageDialog(null, "报关清单生成失败!!", "提示",
//					JOptionPane.INFORMATION_MESSAGE);
//		}
		this.makeApplyToCustomsBill();
		this.dispose();
	}

	/**
	 * 生成报关清单
	 */
	private void makeApplyToCustomsBill() {
//		boolean isSucceed = false;
		String makeInfo="";
		try {
			switch (this.getPnMakeApplyToCustomsBill().getProjectType()) {
			case ProjectType.BCUS:
				emsEdiMergerHead = manualDeclearAction
						.findEmsEdiMergerHeadByDeclareState(new Request(
								CommonVars.getCurrUser()), declareState);
				if (applyToCustomsBillList.getListNo() == null
						|| "".equals(applyToCustomsBillList.getListNo().trim())) {
					applyToCustomsBillList.setListNo(this.getMaxBillListNo());
				}
				List list = super.transferFactoryManageAction
						.makeApplyToCustomsBill(new Request(CommonVars
								.getCurrUser()), applyToCustomsBillList,
								commodityList, emsEdiMergerHead);
				this.tableMode.updateRows(list);
				makeInfo="生成联网监管的报关清单，清淡号为:"+applyToCustomsBillList.getListNo();
				break;
			case ProjectType.DZSC:
				if (dzscCustomsBillList.getListNo() == null
						|| "".equals(dzscCustomsBillList.getListNo().trim())) {
					dzscCustomsBillList.setListNo(this.getMaxBillListNo());
				}
				list = super.transferFactoryManageAction
						.makeDzscCustomsBillList(new Request(CommonVars
								.getCurrUser()), dzscCustomsBillList,
								commodityList);
				this.tableMode.updateRows(list);
				makeInfo="生成电子手册的报关清单，清淡号为:"+dzscCustomsBillList.getListNo();
				break;
			}
//			isSucceed = true;
			JOptionPane.showMessageDialog(null, "报关清单生成成功!\n"+makeInfo, "提示",
			JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception ex) {
//			isSucceed = false;
			JOptionPane.showMessageDialog(null, "报关清单生成失败!\n", "提示",
			JOptionPane.INFORMATION_MESSAGE);
		}
//		return isSucceed;
	}

	private String getMaxBillListNo() {
		switch (this.getPnMakeApplyToCustomsBill().getProjectType()) {
		case ProjectType.BCUS:
			return encAction.getApplyToCustomsBillListMaxNo(new Request(
					CommonVars.getCurrUser()));
		case ProjectType.DZSC:
			return dzscListAction.getApplyToCustomsBillListMaxNo(new Request(
					CommonVars.getCurrUser()));
		}
		return "";
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setBounds(408, 9, 60, 24);
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	private PnMakeCustomsBillList getPnMakeApplyToCustomsBill() {
		if (pnMakeApplyToCustomsBill == null) {
			pnMakeApplyToCustomsBill = new PnMakeCustomsBillList();
			pnMakeApplyToCustomsBill.setBounds(0, 0, 500, 248);
		}
		return pnMakeApplyToCustomsBill;
	}

	private PnMakeCustomsBillList2 getPnMakeApplyToCustomsBill2() {
		if (pnMakeApplyToCustomsBill2 == null) {
			pnMakeApplyToCustomsBill2 = new PnMakeCustomsBillList2();
			pnMakeApplyToCustomsBill2.setBounds(0, 0, 500, 248);
		}
		return pnMakeApplyToCustomsBill2;
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
			pnContext.setBounds(1, 41, 500, 248);
			pnContext.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,
					0, 0, 0));
		}
		return pnContext;
	}

	private void gotoStep(int step) {
		switch (step) {
		case 0:
			this.pnContext.removeAll();
			this.pnContext.add(this.getPnMakeApplyToCustomsBill(), null);
			this.getPnMakeApplyToCustomsBill().setImportGoods(isImportGoods);
			this.getPnMakeApplyToCustomsBill().setVisible(true);
			this.lbHeadText.setText("第一步：设置生成报关请单表头数据");
			break;
		case 1:
			this.pnContext.removeAll();
			this.pnContext.add(this.getPnMakeApplyToCustomsBill2(), null);
			this.scmCoc = this.getPnMakeApplyToCustomsBill().getScmCoc();
			this.emsNo = this.getPnMakeApplyToCustomsBill().getEmsNo();
			this.isNewRecord = this.getPnMakeApplyToCustomsBill()
					.getIsNewRecord();
			switch (this.getPnMakeApplyToCustomsBill().getProjectType()) {
			case ProjectType.BCUS:
				this.applyToCustomsBillList = this
						.getPnMakeApplyToCustomsBill()
						.getApplyToCustomsBillList();
				break;
			case ProjectType.DZSC:
				this.dzscCustomsBillList = this.getPnMakeApplyToCustomsBill()
						.getDzscCustomsBillList();
				break;
			}
			this.getPnMakeApplyToCustomsBill2().setScmCoc(scmCoc);
			this.getPnMakeApplyToCustomsBill2().setEmsNo(emsNo);
			this.getPnMakeApplyToCustomsBill2().setVisible(true);
			this.lbHeadText.setText("第二步：选择需要生成报关请单的单据");
			break;
		case 2:
			this.pnContext.removeAll();
			this.pnContext.add(this.getPnMakeApplyToCustomsBill3(), null);
			this.parentList = this.getPnMakeApplyToCustomsBill2()
					.getParentList();
			this.getPnMakeApplyToCustomsBill3().setParentList(parentList);
			this.getPnMakeApplyToCustomsBill3().setVisible(true);
			this.lbHeadText.setText("第三步：选择需要生成报关请单商品信息");
			break;
		}
		this.pnContext.repaint();
		this.pnContext.validate();
		setState();
	}

	private void setState() {
		this.btnPrevious.setEnabled(this.step > 0);
		this.btnNext.setEnabled(this.step < 2);
		this.btnDone.setEnabled(this.step == 2);
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			this.gotoStep(this.step);
		}
		super.setVisible(b);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
