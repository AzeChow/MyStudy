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
import com.bestway.bcus.enc.entity.MakeCusomsDeclarationParam;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgMakeCustomsDeclaration extends DgCommon {

	private JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JLabel lbHeadText = null;

	private JPanel jPanel1 = null;

	private JButton btnPrevious = null;

	private JButton btnNext = null;

	private JButton btnDone = null;

	private JButton btnExit = null;

	private PnMakeBillList pnMakeCustomsDeclarationBillList = null;

	private PnMakeListParam pnMakeCustomsDeclarationParam = null;

	private JPanel pnContext = null;

	private int step = 0;

//	private TransferFactoryManageAction transferFactoryManageAction = null;

	private boolean isOk = false;

	private String emsNo = "";

	private boolean isImport = false;

	private ScmCoc initScmCoc = null;

	// private Integer impExpFlag = null;

	public ScmCoc getInitScmCoc() {
		return initScmCoc;
	}

	public void setInitScmCoc(ScmCoc scmCoc) {
		this.initScmCoc = scmCoc;
	}

	public boolean isImport() {
		return isImport;
	}

	public void setImport(boolean isImport) {
		this.isImport = isImport;
	}

	public String getEmsNo() {
		return emsNo;
	}

	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	// public Integer getImpExpFlag() {
	// return impExpFlag;
	// }
	//
	// public void setImpExpFlag(Integer impExpFlag) {
	// this.impExpFlag = impExpFlag;
	// }

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
	public DgMakeCustomsDeclaration() {
		super();
		initialize();
		// transferFactoryManageAction = (TransferFactoryManageAction)
		// CommonVars
		// .getApplicationContext().getBean("transferFactoryManageAction");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("结转单据转报关单");
		this.setContentPane(getJContentPane());
		this.setSize(506, 364);

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
					if (step == 0) {
						if (getPnMakeCustomsDeclarationBillList().getScmCoc() == null) {
							JOptionPane.showMessageDialog(
									DgMakeCustomsDeclaration.this,
									"请选择一个供应商/客户", "提示", 0);
							return;
						}
						List list = getPnMakeCustomsDeclarationBillList()
								.getListData();
						if (list.size() < 1) {
							JOptionPane.showMessageDialog(
									DgMakeCustomsDeclaration.this,
									"请至少选择一个结转单据", "提示", 0);
							return;
						}
						// int checkResult = dzscListAction
						// .checkBillListForMakeCustomsDeclaration(
						// new Request(CommonVars.getCurrUser()),
						// list);
						// switch (checkResult) {
						// case 0:
						// break;
						// case -1:
						// JOptionPane.showMessageDialog(
						// DgMakeCustomsDeclaration.this,
						// "选择的报关清单不符合生成报关单的条件", "提示", 0);
						// return;
						// case -2:
						// return;
						// }
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
					if (JOptionPane.showConfirmDialog(
							DgMakeCustomsDeclaration.this, "你确定要生成报关单吗?", "提示",
							JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					List billList = DgMakeCustomsDeclaration.this
							.getPnMakeCustomsDeclarationBillList()
							.getListData();
					boolean isMergeOne = DgMakeCustomsDeclaration.this
							.getPnMakeCustomsDeclarationBillList().isMergeOne();
					MakeCusomsDeclarationParam param = DgMakeCustomsDeclaration.this
							.getPnMakeCustomsDeclarationParam()
							.getMakeCusomsDeclarationParam();
					List result = transferFactoryManageAction
							.makeCusomsDeclarationFromTransferBill(new Request(
									CommonVars.getCurrUser()), billList, param,
									isMergeOne);
					isOk = true;
					if (result.size() > 0) {
						StringBuffer sb = new StringBuffer("生成的报关单如下：" + "\n");
						for (int i = 0; i < result.size(); i++) {
							sb.append(result.get(i).toString() + "\n");
						}
						JOptionPane.showMessageDialog(
								DgMakeCustomsDeclaration.this, sb.toString(),
								"提示", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(
								DgMakeCustomsDeclaration.this, "没有生成报关单", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}
					dispose();
				}
			});
		}
		return btnDone;
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

	private PnMakeBillList getPnMakeCustomsDeclarationBillList() {
		if (pnMakeCustomsDeclarationBillList == null) {
			pnMakeCustomsDeclarationBillList = new PnMakeBillList(emsNo,
					isImport, isImport ? this.getManufacturer() : this
							.getCustomer(), this.initScmCoc);
			pnMakeCustomsDeclarationBillList.setBounds(0, 0, 500, 248);
		}
		return pnMakeCustomsDeclarationBillList;
	}

	private PnMakeListParam getPnMakeCustomsDeclarationParam() {
		if (pnMakeCustomsDeclarationParam == null) {
			pnMakeCustomsDeclarationParam = new PnMakeListParam(
					getPnMakeCustomsDeclarationBillList().getScmCoc());
			pnMakeCustomsDeclarationParam.setBounds(0, 0, 500, 248);
		}
		return pnMakeCustomsDeclarationParam;
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
			this.pnContext
					.add(this.getPnMakeCustomsDeclarationBillList(), null);
			this.lbHeadText.setText("第一步：请选择你要生成报关单的结转单据");
			break;
		case 1:
			this.pnContext.removeAll();
			this.pnContext.add(this.getPnMakeCustomsDeclarationParam(), null);
			this.lbHeadText.setText("第二步：请选择一些基本信息，如果不选择也可在生成报关单后在报关单维护界面中修改");
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
} // @jve:decl-index=0:visual-constraint="10,10"
