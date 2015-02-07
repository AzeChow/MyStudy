/*
 * Created on 2004-8-16
 * 说明：PK单转报关单
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator // change the template for this generated impExpType
 *         comment go to Window - Preferences - Java - Code Style - Code
 *         Templates
 */
public class DgMakeCustomsOrderToFptAppHead extends JDialogBase {

	private JPanel jContentPane = null;

	private JPanel jPanel1 = null;

	private JButton btnPrevious = null;

	private JButton btnNext = null;

	private JButton btnDone = null;

	private JButton btnExit = null;

	private JPanel pnContext = null;

	private JPanel jPanel11 = null;

	private JLabel jLabel17 = null;

	private JLabel lbHeadText = null;

	private JLabel jLabel19 = null;

	private int step = 1;
	private PnMakeCustomsOrderToFptAppHead1 pn1 = new PnMakeCustomsOrderToFptAppHead1();
	private PnMakeCustomsOrderToFptAppHead2 pn2 = new PnMakeCustomsOrderToFptAppHead2();
	private PnMakeCustomsOrderToFptAppHead3 pn3 = new PnMakeCustomsOrderToFptAppHead3();
	private FptManageAction fptManageAction = null;
	private boolean isRerictCommodity = true;
	private FptAppHead head = new FptAppHead();

	/**
	 * This method initializes
	 */
	public DgMakeCustomsOrderToFptAppHead() {
		super();
		this.fptManageAction = (FptManageAction) CommonVars
				.getApplicationContext().getBean("fptManageAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("订单转－转厂申请单");
		this.setContentPane(getJContentPane());
		this.setSize(578, 378);

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
			btnPrevious.setText("上一步");
			btnPrevious.setPreferredSize(new Dimension(70, 25));
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (step == 2) {
						doStepOne();
						step = 1;
					} else if (step == 3) {
						doStepTwo();
						step = 2;
					}
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
					if (step == 1) {
						if (!checkSetpOne()) {
							return;
						}
						doStepTwo();
						pn2.initPn2Table(pn1.getOderList());
						step = 2;
					}

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
			btnDone.setText("执行");
			btnDone.setPreferredSize(new Dimension(70, 25));
			btnDone.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (step == 2) {
						if (!checkSetpTwo()) {
							return;
						}
					}
					CommonProgress.showProgressDialog();
					CommonProgress.setMessage("正在执行任务，请耐心等待......");
					new Execute().start();
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

	private void setState() {
		this.btnPrevious.setEnabled(this.step > 0);
		this.btnNext.setEnabled(this.step < 3);
		this.btnDone.setEnabled(this.step == 3 && isRerictCommodity);
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			getPnContext().add(pn1, BorderLayout.CENTER);
			setFirstSate();
		}
		super.setVisible(b);
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

	private void setFirstSate() {
		btnPrevious.setEnabled(false);
		btnNext.setEnabled(true);
		btnDone.setEnabled(false);
		lbHeadText.setText("1，填写必要资料");
	}

	private void doStepOne() {
		getPnContext().remove(pn2);
		getPnContext().add(pn1, BorderLayout.CENTER);
		btnPrevious.setEnabled(false);
		btnNext.setEnabled(true);
		btnDone.setEnabled(false);
		lbHeadText.setText("1，填写必要资料");
		getPnContext().repaint();
	}

	private void doStepTwo() {
		getPnContext().remove(pn1);
		getPnContext().remove(pn3);
		getPnContext().add(pn2, BorderLayout.CENTER);
		btnPrevious.setEnabled(true);
		btnNext.setEnabled(false);
		btnDone.setEnabled(true);
		lbHeadText.setText("2，选择订单表体资料！");
		getPnContext().repaint();
	}

	private void doStepThree() {
		getPnContext().remove(pn2);
		getPnContext().add(pn3, BorderLayout.CENTER);
		btnPrevious.setEnabled(true);
		btnNext.setEnabled(false);
		btnDone.setEnabled(false);
		lbHeadText.setText("3，生成结果信息！");
		getPnContext().repaint();
		step = 3;
	}

	private boolean checkSetpOne() {
		if (this.pn1.getEmsNo() == null || this.pn1.getEmsNo().equals("")) {
			JOptionPane.showMessageDialog(this, "没有填写手册号！", "提示！",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (this.pn1.getOderList() == null || this.pn1.getOderList().isEmpty()) {
			JOptionPane.showMessageDialog(this, "没有填订单号！", "提示！",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	private boolean checkSetpTwo() {
		if (this.pn2.getTempFptAppheadAndOrderList() == null
				|| this.pn2.getTempFptAppheadAndOrderList().isEmpty()) {
			JOptionPane.showMessageDialog(this, "没有选择订单表体！", "提示！",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	class Execute extends Thread {
		boolean isOk = true;
		String message = "";

		@Override
		public void run() {
			try {
				List rlist = fptManageAction
						.makeCustomsOrderToFptAppHead(new Request(CommonVars
								.getCurrUser()), head, pn2
								.getTempFptAppheadAndOrderList(), pn1
								.getProjectType(), pn1.getMaterielType(), pn1
								.getEmsNo());
				if (rlist.get(0) != null) {
					FptAppHead  head=(FptAppHead)rlist.get(0);
					String  inOutFlag=head.getInOutFlag();
					String name="申请单企业内部编号为：";
					if(inOutFlag.equals(FptInOutFlag.IN)){
						name+=head.getInCopAppNo();
					}else if(inOutFlag.equals(FptInOutFlag.OUT)){
						name+=head.getOutCopAppNo();
					}
					message += "转厂申请单已成功生成！"+name+"\n";
				} else {
					message += "转厂申请单生成失败！\n";
					message += rlist.get(1) == null ? "" : rlist.get(1)
							.toString();
					isOk = false;
				}
			} catch (Exception e) {
				message = e.getMessage();
				isOk = false;
				e.printStackTrace();
			} finally {
				CommonProgress.closeProgressDialog();
				pn3.showMessage(message);
				doStepThree();
				if (isOk) {
					btnPrevious.setEnabled(false);
				}

			}

		}
	}
} // @jve:decl-index=0:visual-constraint="10,10"
