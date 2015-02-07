package com.bestway.bcs.client.contractexe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * 批量修改合同、备案资料库、对应关系
 * 
 * Checked by lxr
 * 
 * 
 * @author lxr
 * 
 */
public class DgBcsBatchUpdateComplex extends JDialogBase {

	private JPanel jContentPane = null;

	private JButton btnPrevious = null;
	private JButton btnNext = null;
	private JButton btnExit = null;

	private JPanel pnContext = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel11 = null;

	private JLabel jLabel17 = null;
	private JLabel jLabel19 = null;
	private JLabel lbHeadText = null;
	/**
	 * 设置步长
	 */
	private int step = 0;
	/**
	 * 出现的第一个界面
	 */
	private PnBatchUpdateComplex pnBatchUpdateComplex = null;
	/**
	 * 申请单头信息
	 */
	private PnBatchUpdateComplex1 pnBatchUpdateComplex1 = null;
	private ContractAction contractAction = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgBcsBatchUpdateComplex() {
		super();
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		contractAction
				.brownUpdateComplex(new Request(CommonVars.getCurrUser()));
		initialize();
	}

	/**
	 * 设置显示
	 */
	public void setVisible(boolean b) {
		if (b) {
			this.initPn(this.step);
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("批量修改商品编码与法定单位");
		this.setContentPane(getJContentPane());
		this.setSize(741, 459);

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
			jContentPane.add(getJPanel1(), BorderLayout.SOUTH);
			jContentPane.add(getPnContext(), BorderLayout.CENTER);
			jContentPane.add(getJPanel11(), BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new FlowLayout());
			jPanel1
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel1.add(getBtnPrevious(), null);
			jPanel1.add(getBtnNext(), null);
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
			btnPrevious.setText("上一步");
			btnPrevious.setPreferredSize(new Dimension(70, 25));
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					step--;
					initPn(step);
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
			btnNext.setText("下一步");
			btnNext.setPreferredSize(new Dimension(70, 25));
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (step == 0) {
						if (getPnBatchUpdateComplex().checkSelectType()) {
							JOptionPane.showMessageDialog(
									DgBcsBatchUpdateComplex.this, "请选择修改类型",
									"提示！", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (getPnBatchUpdateComplex().checkSelectMateriel()) {
							JOptionPane.showMessageDialog(
									DgBcsBatchUpdateComplex.this, "请选择修改物料类型",
									"提示！", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (!getPnBatchUpdateComplex().checkSelectType()
								&& getPnBatchUpdateComplex().checkExeType()) {
							JOptionPane.showMessageDialog(
									DgBcsBatchUpdateComplex.this, "请选择变更状态类型",
									"提示！", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					}
					step++;
					initPn(step);
				}
			});
		}
		return btnNext;
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
			btnExit.setPreferredSize(new Dimension(70, 25));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * @return Returns the PnMakeApplyToCustoms.
	 */
	private PnBatchUpdateComplex getPnBatchUpdateComplex() {
		if (pnBatchUpdateComplex == null) {
			pnBatchUpdateComplex = new PnBatchUpdateComplex();
			pnBatchUpdateComplex.setBounds(0, 0, 630, 248);
		}
		return pnBatchUpdateComplex;
	}

	/**
	 * @return Returns the PnMakeApplyToCustoms2.
	 */
	private PnBatchUpdateComplex1 getPnBatchUpdateComplex1() {
		if (pnBatchUpdateComplex1 == null) {
			pnBatchUpdateComplex1 = new PnBatchUpdateComplex1();
			pnBatchUpdateComplex1.setBounds(0, 0, 630, 248);
		}
		return pnBatchUpdateComplex1;
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

	/**
	 * 生成报关清单表头数据
	 */
	private void initP0() {
		this.pnContext.removeAll();
		this.pnContext.add(this.getPnBatchUpdateComplex(), BorderLayout.CENTER);
		this.getPnBatchUpdateComplex().setVisible(true);
		this.lbHeadText.setText("第一步：请设置需要修改的备案类型与物料类型");
	}

	/**
	 * 生成报关清单的申请单据头
	 */
	private void initP1() {
		this.pnContext.removeAll();
		this.pnContext
				.add(this.getPnBatchUpdateComplex1(), BorderLayout.CENTER);
		this.getPnBatchUpdateComplex1().setEmsType(
				getPnBatchUpdateComplex().emsType);
		this.getPnBatchUpdateComplex1().setIsMaterial(
				getPnBatchUpdateComplex().isMaterial);
		this.getPnBatchUpdateComplex1().setIsExe(
				getPnBatchUpdateComplex().cbbExe.isSelected());
		this.getPnBatchUpdateComplex1().setIsModify(
				getPnBatchUpdateComplex().cbbModify.isSelected());
		this.getPnBatchUpdateComplex1().setVisible(true);
		this.lbHeadText.setText("第二步：同步选择的商品编码到备案类型中的物料");
	}

	/**
	 * 初始化各个界面
	 * 
	 * @param step
	 */
	private void initPn(int step) {
		switch (step) {
		case 0:
			initP0();
			break;
		case 1:
			initP1();
			break;
		}
		this.pnContext.repaint();
		this.pnContext.validate();
		setState();
	}

	/**
	 * 设置各按钮状态
	 */
	private void setState() {
		this.btnPrevious.setEnabled(this.step > 0);
		this.btnNext.setEnabled(this.step < 1);
	}

	/**
	 * This method initializes jPanel11
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel11() {
		if (jPanel11 == null) {
			jLabel19 = new JLabel();
			jLabel19.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jLabel19.setText("");
			lbHeadText = new JLabel();
			lbHeadText.setFont(new Font("Dialog", Font.BOLD, 20));
			lbHeadText.setText(" ");
			lbHeadText.setForeground(new Color(255, 153, 0));
			jLabel17 = new JLabel();
			jLabel17
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jLabel17.setText("");
			jPanel11 = new JPanel();
			jPanel11.setLayout(new BorderLayout());
			jPanel11.setBackground(Color.white);
			jPanel11.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED));
			jPanel11.add(jLabel17, java.awt.BorderLayout.WEST);
			jPanel11.add(lbHeadText, java.awt.BorderLayout.CENTER);
			jPanel11.add(jLabel19, java.awt.BorderLayout.EAST);
		}
		return jPanel11;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
