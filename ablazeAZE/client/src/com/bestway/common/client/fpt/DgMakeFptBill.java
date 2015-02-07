/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.bestway.bcus.cas.entity.TempBillMaster;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.common.constant.FptBusinessType;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.common.fpt.entity.FptBillHead;
import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class DgMakeFptBill extends DgCommon {

	private JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JButton btnPrevious = null;

	private JButton btnNext = null;

	private JButton btnDone = null;

	private JButton btnExit = null;

	private JPanel pnContext = null;

	private int step = 0;

	private boolean isOk = false;

	// private List billMasterList = null; // @jve:decl-index=0:
	//
	// private List billDetailList = null; // @jve:decl-index=0:

	// private boolean isImportGoods = true;

	private ScmCoc scmCoc = null; // @jve:decl-index=0:

	private PnMakeFptBill pnMakeFptBill = null;

	private PnMakeFptBill2 pnMakeFptBill2 = null;

	private JSplitPane jSplitPane = null;

	private JSplitPane jSplitPane1 = null;

	private JButton jButton = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private boolean isNewFptBillHead = true;

	/**
	 * 只导入明细资料
	 */
	private boolean isOnlyImportDetail = false;

	private FptBillHead oldFptBillHead = null; // @jve:decl-index=0:

	public boolean isNewFptBillHead() {
		return isNewFptBillHead;
	}

	public void setNewFptBillHead(boolean isNewFptBillHead) {
		this.isNewFptBillHead = isNewFptBillHead;
	}

	public FptBillHead getOldFptBillHead() {
		return oldFptBillHead;
	}

	public void setOldFptBillHead(FptBillHead oldFptBillHead) {
		this.oldFptBillHead = oldFptBillHead;
		System.out.println("setOldFptBillHead");
	}

	public boolean isOnlyImportDetail() {
		return isOnlyImportDetail;
	}

	public void setOnlyImportDetail(boolean isOnlyImportDetail) {
		this.isOnlyImportDetail = isOnlyImportDetail;
	}

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
	public DgMakeFptBill() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("单据中心工厂单据转---结转单据");
		this.setContentPane(getJContentPane());
		this.setSize(724, 559);
	}

	/**
	 * @return Returns the scmCoc.
	 */
	public ScmCoc getScmCoc() {
		return scmCoc;
	}

	/**
	 * @param scmCoc
	 *            The scmCoc to set.
	 */
	public void setScmCoc(ScmCoc scmCoc) {
		this.scmCoc = scmCoc;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
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
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(70, 28, 250, 18));
			jLabel1.setText("第二步：选择需要生成进货结转单据商品信息");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(70, 11, 250, 14));
			jLabel.setText("第一步：选择单据中心的转厂单据");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBackground(java.awt.Color.white);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
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
			jPanel1
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel1.add(getBtnPrevious(), null);
			jPanel1.add(getBtnNext(), null);
			jPanel1.add(getBtnDone(), null);
			jPanel1.add(getBtnExit(), null);
			jPanel1.add(getJButton(), null);
		}
		return jPanel1;
	}

	// /**
	// * @return Returns the isImportGoods.
	// */
	// public boolean isImportGoods() {
	// return isImportGoods;
	// }
	//
	// /**
	// * @param isImportGoods
	// * The isImportGoods to set.
	// */
	// public void setImportGoods(boolean isImportGoods) {
	// this.isImportGoods = isImportGoods;
	// }

	/**
	 * This method initializes btnPrevious
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setBounds(389, 20, 72, 24);
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
			btnNext.setBounds(464, 20, 72, 24);
			btnNext.setText("下一步");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (step == 0) {
						String envelopCode = getPnMakeFptBill()
								.getSelectedCasBillCode();
						if (envelopCode == null || "".equals(envelopCode)) {
							JOptionPane.showMessageDialog(DgMakeFptBill.this,
									"请选择结转申请表", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						String emsNo = getPnMakeFptBill().getSelectedEmsNo();
						if (emsNo == null || "".equals(emsNo)) {
							JOptionPane.showMessageDialog(DgMakeFptBill.this,
									"请选择手册/账册号", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (getPnMakeFptBill().getSelectedMasterList().size() <= 0) {
							JOptionPane.showMessageDialog(DgMakeFptBill.this,
									"请选择要转换的单据!!", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						
						//wss2010.09.17加上检查单据号功能,单据号不能超出17位
						//******************************************************************************
						List<TempBillMaster> list = getPnMakeFptBill().getSelectedMasterList();
						
						String billNos = "";
						for(int i=0;i<list.size();i++){
							TempBillMaster temp = list.get(i);
							
							if (temp.getBillMaster().getBillNo().trim().length() > 17) {
								billNos += temp.getBillMaster().getBillNo() + "/";
							}
						}
						
						if(!"".equals(billNos)){
							JOptionPane.showMessageDialog(DgMakeFptBill.this,
										"单据号" + billNos + "不能超过17位!!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
						}
						//******************************************************************************
						
						if (!getPnMakeFptBill().isNewFptBillHead()
								&& getPnMakeFptBill().getFptBillHead() == null) {
							JOptionPane.showMessageDialog(DgMakeFptBill.this,
									"你选择的是追加加到原有的结转收发货单据，所以请选择要追加的收发货单据", "提示",
									JOptionPane.INFORMATION_MESSAGE);
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
			btnDone.setBounds(540, 20, 60, 24);
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
		List commodityList = pnMakeFptBill.getSelectedMasterList();
		if (commodityList == null || commodityList.size() <= 0) {
			JOptionPane.showMessageDialog(DgMakeFptBill.this, "请选择企业物流单据!!",
					"提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		//
		// 生成结转单据
		//
		this.makeCustomsEnvelopRequestBill(commodityList);
		this.dispose();
	}

	/**
	 * 生成结转单据
	 */
	private void makeCustomsEnvelopRequestBill(List commodityList) {
		String casBillCode = getPnMakeFptBill().getSelectedCasBillCode();
		String emsNo = getPnMakeFptBill().getSelectedEmsNo();
		FptAppHead fptAppHead = getPnMakeFptBill().getSelectedFptAppHead();
		boolean isComplex = this.getPnMakeFptBill().isComplex();
		isNewFptBillHead = this.getPnMakeFptBill().isNewFptBillHead();
		oldFptBillHead = this.getPnMakeFptBill().getFptBillHead();
		if (!isNewFptBillHead && oldFptBillHead == null) {
			JOptionPane.showMessageDialog(DgMakeFptBill.this,
					"你选择的是追加加到原有的结转收发货单据，所以请选择要追加的收发货单据", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		List billDetailList = this.getPnMakeFptBill2()
				.getSelectedCommodityList();
		try {
			FptBillHead fptBillHead = super.fptManageAction
					.makeFptBillFromCasBill(new Request(CommonVars
							.getCurrUser()), billDetailList, casBillCode,
							fptAppHead, emsNo, isComplex, isNewFptBillHead,
							oldFptBillHead);
			if (fptBillHead != null) {
				StringBuffer makeInfo = new StringBuffer("结转单据生成成功!已生成结转单据是:\n");
				if (FptBusinessType.FPT_BILL.equals(fptBillHead.getSysType())) {
					if (FptInOutFlag.OUT.equals(fptBillHead.getBillSort())) {
						makeInfo.append("发货单\n");
						makeInfo.append("内部编号是："
								+ fptBillHead.getIssueCopBillNo() + "\n");
					} else {
						makeInfo.append("收货单\n");
						makeInfo.append("内部编号是："
								+ fptBillHead.getReceiveCopBillNo() + "\n");
					}
				} else if (FptBusinessType.FPT_BILL_BACK.equals(fptBillHead
						.getSysType())) {
					if (FptInOutFlag.OUT.equals(fptBillHead.getBillSort())) {
						makeInfo.append("收退货单\n");
						makeInfo.append("内部编号是："
								+ fptBillHead.getReceiveCopBillNo() + "\n");
					} else {
						makeInfo.append("发退货单\n");
						makeInfo.append("内部编号是："
								+ fptBillHead.getIssueCopBillNo() + "\n");
					}
				}
				JOptionPane.showMessageDialog(DgMakeFptBill.this, makeInfo
						.toString(), "提示", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(DgMakeFptBill.this,
						"结转单据生成失败!  ", "提示", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(DgMakeFptBill.this, "结转单据生成失败!  "
					+ ex.getMessage(), "提示", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setBounds(603, 20, 60, 24);
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	private PnMakeFptBill getPnMakeFptBill() {
		if (pnMakeFptBill == null) {
			pnMakeFptBill = new PnMakeFptBill();
			pnMakeFptBill.setBounds(0, 0, 500, 248);
			pnMakeFptBill.setNewFptBillHead(this.isNewFptBillHead);
			pnMakeFptBill.setFptBillHead(this.oldFptBillHead);
			pnMakeFptBill.setOnlyImportDetail(this.isOnlyImportDetail);
		}
		return pnMakeFptBill;
	}

	private PnMakeFptBill2 getPnMakeFptBill2() {
		if (pnMakeFptBill2 == null) {
			pnMakeFptBill2 = new PnMakeFptBill2();
			pnMakeFptBill2.setBounds(0, 0, 500, 248);
		}
		return pnMakeFptBill2;
	}

	/**
	 * This method initializes pnContext
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnContext() {
		if (pnContext == null) {
			pnContext = new JPanel();
			pnContext.setLayout(new BorderLayout());
			pnContext.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,
					0, 0, 0));
		}
		return pnContext;
	}

	private void showCurrentStepInfo(int step) {
		this.jLabel.setForeground(step == 0 ? Color.RED : Color.BLACK);
		this.jLabel1.setForeground(step == 1 ? Color.RED : Color.BLACK);
	}

	private void gotoStep(int step) {
		switch (step) {
		case 0:
			this.pnContext.removeAll();
			this.pnContext.add(this.getPnMakeFptBill(), BorderLayout.CENTER);
			this.getPnMakeFptBill().setVisible(true);
			break;
		case 1:
			this.pnContext.removeAll();
			this.pnContext.add(this.getPnMakeFptBill2(), BorderLayout.CENTER);
			getPnMakeFptBill2().setBillMasterList(
					this.getPnMakeFptBill().getSelectedMasterList());
			this.getPnMakeFptBill2().setVisible(true);
			break;
		}
		showCurrentStepInfo(step);
		this.pnContext.repaint();
		this.pnContext.validate();
		setState();
	}

	private void setState() {
		this.btnPrevious.setEnabled(this.step > 0);
		this.btnNext.setEnabled(this.step < 1);
		this.btnDone.setEnabled(this.step == 1);
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			this.gotoStep(this.step);
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerLocation(60);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJSplitPane1());
			jSplitPane.setDividerSize(2);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jSplitPane1
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setDividerSize(2);
			jSplitPane1.setDividerLocation(400);
			jSplitPane1.setComponentOrientation(ComponentOrientation.UNKNOWN);
			jSplitPane1.setContinuousLayout(true);
			jSplitPane1.setOneTouchExpandable(true);
			jSplitPane1.setTopComponent(getPnContext());
			jSplitPane1.setBottomComponent(getJPanel1());
			jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(23, 18, 132, 24));
			jButton.setText("查询历史结转信息");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgFindMakeFptBill dg = new DgFindMakeFptBill();
					dg.setVisible(true);
				}
			});
		}
		return jButton;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
