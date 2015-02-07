/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.transferfactory;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.cas.entity.TempBillMaster;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgMakeTransferFactoryBill extends DgCommon {

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

	private List billMasterList = null; // @jve:decl-index=0:

	private List billDetailList = null; // @jve:decl-index=0:

	private boolean isImportGoods = true;

	private ScmCoc scmCoc = null;

	private PnMakeTransferFactoryBill pnMakeTransferFactoryBill = null;

	private PnMakeTransferFactoryBill2 pnMakeTransferFactoryBill2 = null;

	private JSplitPane jSplitPane = null;

	private JSplitPane jSplitPane1 = null;

	/**
	 * @return Returns the billDetailList.
	 */
	public List getBillDetailList() {
		return billDetailList;
	}

	/**
	 * @param billDetailList
	 *            The billDetailList to set.
	 */
	public void setBillDetailList(List billDetailList) {
		this.billDetailList = billDetailList;
	}

	/**
	 * @return Returns the billMasterList.
	 */
	public List getBillMasterList() {
		return billMasterList;
	}

	/**
	 * @param billMasterList
	 *            The billMasterList to set.
	 */
	public void setBillMasterList(List billMasterList) {
		this.billMasterList = billMasterList;
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
	public DgMakeTransferFactoryBill() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("海关帐工厂单据转---结转单据");
		this.setContentPane(getJContentPane());
		this.setSize(631, 459);
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
			jPanel = new JPanel();
			lbHeadText = new JLabel();
			jPanel.setLayout(null);
			jPanel.setBackground(java.awt.Color.white);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			lbHeadText.setBounds(2, 22, 492, 15);
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
			btnPrevious.setBounds(295, 20, 72, 24);
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
			btnNext.setBounds(370, 20, 72, 24);
			btnNext.setText("下一步");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (step == 0) {
//						String envelopCode = getPnMakeTransferFactoryBill()
//								.getSelectedEnvelopCode();
//						if (envelopCode == null || "".equals(envelopCode)) {
//							JOptionPane.showMessageDialog(
//									DgMakeTransferFactoryBill.this, "请选择关封号",
//									"提示", JOptionPane.INFORMATION_MESSAGE);
//							return;
//						}
						if (getPnMakeTransferFactoryBill()
								.getSelectedMasterList().size() <= 0) {
							JOptionPane.showMessageDialog(
									DgMakeTransferFactoryBill.this,
									isImportGoods == true ? "请选择要转换的进货单据!!"
											: "请选择要转换的出货单据!!", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					} else if(step == 1) {
						List<BillMaster> list = pnMakeTransferFactoryBill2.getTableModelMaster().getList();
						BillMaster billMaster = null;
						int count = 0;
						for (int i = 0; list != null && i < list.size(); i++) {
							billMaster = list.get(i);
							if(billMaster.getEnvelopNo() == null || "".equals(billMaster.getEnvelopNo())) {
								count++;
							}
						}
						if(count > 0) {
							throw new RuntimeException("您选择的资料中部分单据没有关封号，无法生成对应的结转单据，没有关封号的单据号有："
									+ count + "笔");
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
			btnDone.setBounds(446, 20, 60, 24);
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
		List commodityList = pnMakeTransferFactoryBill.getSelectedMasterList();
		if (commodityList == null || commodityList.size() <= 0) {
			JOptionPane.showMessageDialog(DgMakeTransferFactoryBill.this,
					"请选择企业物流单据!!", "提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		List<TempBillMaster> list = pnMakeTransferFactoryBill2.getTableModelMaster().getList();
		BillMaster billMaster = null;
		int count = 0;
		for (int i = 0; list != null && i < list.size(); i++) {
			billMaster = list.get(i).getBillMaster();
			if(billMaster.getEnvelopNo() == null || "".equals(billMaster.getEnvelopNo())) {
				count++;
			}
		}
		if(count > 0) {
			throw new RuntimeException("您选择的资料中部分单据没有关封号，无法生成对应的结转单据，没有关封号的单据号有："
					+ count + "笔。");
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
		String envelopCode = getPnMakeTransferFactoryBill()
				.getSelectedEnvelopCode();
		String emsNo = getPnMakeTransferFactoryBill().getSelectedEmsNo();
		try {
			super.transferFactoryManageAction.makeTransferFactoryBill(
					new Request(CommonVars.getCurrUser()), this.billMasterList,
					envelopCode, emsNo);
			// String billCodes="";
			// for(int i=0;i<this.billDetailList.size();i++){
			// BillMaster billMaster = ((TempBillMaster) billMasterList.get(i))
			// .getBillMaster();
			// billCodes+=billMaster.getBillNo()+",";
			// }
			JOptionPane.showMessageDialog(DgMakeTransferFactoryBill.this,
					"结转单据生成成功!", "提示", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(DgMakeTransferFactoryBill.this,
					"结转单据生成失败!  " + ex.getMessage(), "提示",
					JOptionPane.ERROR_MESSAGE);
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
			btnExit.setBounds(509, 20, 60, 24);
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	private PnMakeTransferFactoryBill getPnMakeTransferFactoryBill() {
		if (pnMakeTransferFactoryBill == null) {
			pnMakeTransferFactoryBill = new PnMakeTransferFactoryBill();
			pnMakeTransferFactoryBill.setBounds(0, 0, 500, 248);
		}
		return pnMakeTransferFactoryBill;
	}

	private PnMakeTransferFactoryBill2 getPnMakeTransferFactoryBill2() {
		if (pnMakeTransferFactoryBill2 == null) {
			pnMakeTransferFactoryBill2 = new PnMakeTransferFactoryBill2();
			pnMakeTransferFactoryBill2.setBounds(0, 0, 500, 248);
		}
		return pnMakeTransferFactoryBill2;
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

	private void gotoStep(int step) {
		switch (step) {
		case 0:
			this.pnContext.removeAll();
			this.pnContext.add(this.getPnMakeTransferFactoryBill(),
					BorderLayout.CENTER);
			this.getPnMakeTransferFactoryBill().setImportGoods(isImportGoods);
			this.getPnMakeTransferFactoryBill().setScmCoc(this.scmCoc);
			this.getPnMakeTransferFactoryBill().setVisible(true);
			if (this.isImportGoods == true) {
				this.lbHeadText.setText("第一步：生成进货结转单据");
			} else {
				this.lbHeadText.setText("第一步：生成出货结转单据");
			}
			break;
		case 1:
			this.pnContext.removeAll();
			this.pnContext.add(this.getPnMakeTransferFactoryBill2(),
					BorderLayout.CENTER);
			this.billMasterList = getPnMakeTransferFactoryBill()
					.getSelectedMasterList();
			getPnMakeTransferFactoryBill2().setBillMasterList(
					this.billMasterList);
			this.getPnMakeTransferFactoryBill2().setVisible(true);
			if (this.isImportGoods == true) {
				this.lbHeadText.setText("第二步：选择需要生成进货结转单据商品信息");
			} else {
				this.lbHeadText.setText("第二步：选择需要生成出货结转单据商品信息");
			}
			break;
		}
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
			jSplitPane1.setDividerLocation(300);
			jSplitPane1.setComponentOrientation(ComponentOrientation.UNKNOWN);
			jSplitPane1.setContinuousLayout(true);
			jSplitPane1.setOneTouchExpandable(true);
			jSplitPane1.setTopComponent(getPnContext());
			jSplitPane1.setBottomComponent(getJPanel1());
			jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		}
		return jSplitPane1;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
