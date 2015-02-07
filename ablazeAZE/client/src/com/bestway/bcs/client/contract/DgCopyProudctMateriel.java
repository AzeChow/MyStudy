/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contract;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgCopyProudctMateriel extends JDialogBase {

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

	private PnCopyProductMateriel pnCopyProductMateriel = null;

	private PnCopyProductMateriel2 pnCopyProductMateriel2 = null;

	private ContractAction contractAction = null; // 合同

	private Contract contract = null;

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
	public DgCopyProudctMateriel() {
		super();
		initialize();
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("转抄合同成品或料件");
		this.setContentPane(getJContentPane());
		this.setSize(506, 356);
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

		List<ContractExg> contractExgList = this.getPnCopyProductMateriel()
				.getSelectedContractExgList();
		List<ContractImg> contractImgList = this.getPnCopyProductMateriel2()
				.getSelectedContractImgList();

		if (contractExgList.size() <= 0 && contractImgList.size() <= 0) {
			if (JOptionPane.showConfirmDialog(null, "确定只转抄表头吗!!!", "提示",
					JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
				return;
			}
		}
		contract = this.contractAction.copyContractProductMateriel(new Request(
				CommonVars.getCurrUser(),true), contract, contractExgList,
				contractImgList);
		isOk = true;
		this.dispose();
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

	private PnCopyProductMateriel getPnCopyProductMateriel() {
		if (pnCopyProductMateriel == null) {
			pnCopyProductMateriel = new PnCopyProductMateriel();
			pnCopyProductMateriel.setBounds(0, 0, 500, 248);
		}
		return pnCopyProductMateriel;
	}

	private PnCopyProductMateriel2 getPnCopyProductMateriel2() {
		if (pnCopyProductMateriel2 == null) {
			pnCopyProductMateriel2 = new PnCopyProductMateriel2();
			pnCopyProductMateriel2.setBounds(0, 0, 500, 248);
		}
		return pnCopyProductMateriel2;
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
			this.pnContext.add(this.getPnCopyProductMateriel(), null);
			this.getPnCopyProductMateriel().setContract(contract);
			this.getPnCopyProductMateriel().setVisible(true);			
			this.lbHeadText.setText("第一步：选择转抄的成品（不带单耗）");
			break;
		case 1:
			this.pnContext.removeAll();
			this.pnContext.add(this.getPnCopyProductMateriel2(), null);
			this.getPnCopyProductMateriel2().setContract(contract);
			this.getPnCopyProductMateriel2().setVisible(true);			
			this.lbHeadText.setText("第二步：选择转抄的料件");
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

	public void setVisible(boolean b) {
		if (b) {
			this.gotoStep(this.step);
		}
		super.setVisible(b);
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

}
