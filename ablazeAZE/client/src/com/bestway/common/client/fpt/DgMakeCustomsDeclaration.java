/*
 * Created on 2004-8-16
 *
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

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.enc.entity.MakeCusomsDeclarationParam;
import com.bestway.common.Request;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.fpt.entity.FptAppHead;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class DgMakeCustomsDeclaration extends DgCommon {

	private PnMakeFptBillList pnMakeCustomsDeclarationBillList = null;

	private PnMakeFptBillList1 pnMakeCustomsDeclarationBillList1 = null;
	
	private PnMakeFptBillListStep3 pnMakeCustomsDeclarationBillListStep3 = null;

	private PnMakeFptBillListParam pnMakeCustomsDeclarationParam = null;

	private int step = 0;

	private boolean isOk = false;

	private String sysType = ""; // 单据类型 // @jve:decl-index=0:
	
	private String backSysType= "";

	private String inOutFlag = ""; // @jve:decl-index=0:

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

	public String isImport() {
		return inOutFlag;
	}

	public void setImport(String isImport) {
		this.inOutFlag = isImport;
	}

	/**
	 * @return Returns the isOk.
	 */
	public boolean isOk() {
		return isOk;
	}
	
	

	public String getBackSysType() {
		return backSysType;
	}

	public void setBackSysType(String backSysType) {
		this.backSysType = backSysType;
	}

	public String getSysType() {
		return sysType;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgMakeCustomsDeclaration() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("结转单据转报关单");
		this.setContentPane(getJContentPane());
		this.setSize(670, 480);

	}

	private PnMakeFptBillList getPnMakeCustomsDeclarationBillList() {
		if (pnMakeCustomsDeclarationBillList == null) {
			pnMakeCustomsDeclarationBillList = new PnMakeFptBillList();
			pnMakeCustomsDeclarationBillList.setBounds(0, 0, 630, 248);
		}
		return pnMakeCustomsDeclarationBillList;
	}

	private PnMakeFptBillList1 getPnMakeCustomsDeclarationBillList1() {
		if (pnMakeCustomsDeclarationBillList1 == null) {
			pnMakeCustomsDeclarationBillList1 = new PnMakeFptBillList1();
			pnMakeCustomsDeclarationBillList1.setBounds(0, 0, 630, 248);
		}
		return pnMakeCustomsDeclarationBillList1;
	}
	private PnMakeFptBillListStep3 getPnMakeCustomsDeclarationBillListStep3() {
		if (pnMakeCustomsDeclarationBillListStep3 == null) {
			pnMakeCustomsDeclarationBillListStep3 = new PnMakeFptBillListStep3();
			pnMakeCustomsDeclarationBillListStep3.setBounds(0, 0, 630, 248);
		}
		return pnMakeCustomsDeclarationBillListStep3;
	}
	

	private PnMakeFptBillListParam getPnMakeCustomsDeclarationParam() {
		if (pnMakeCustomsDeclarationParam == null) {
			pnMakeCustomsDeclarationParam = new PnMakeFptBillListParam();
			pnMakeCustomsDeclarationParam.setBounds(0, 0, 630, 248);
		}
		return pnMakeCustomsDeclarationParam;
	}

	private void initP0() {
		this.pnContext.removeAll();
		this.getPnMakeCustomsDeclarationBillList().setIsImport(inOutFlag);
		this.pnContext.add(this.getPnMakeCustomsDeclarationBillList(),BorderLayout.CENTER);
		this.lbHeadText.setText("第一步：请选择你要生成报关单的结转单据");
	}

	private void initP1() {
		this.pnContext.removeAll();
		this.pnContext.add(this.getPnMakeCustomsDeclarationBillList1(),BorderLayout.CENTER);
		this.getPnMakeCustomsDeclarationBillList1().setIsImport(getPnMakeCustomsDeclarationBillList().getIsImport());
		this.getPnMakeCustomsDeclarationBillList1().setParentList(getPnMakeCustomsDeclarationBillList().getListData());
		this.getPnMakeCustomsDeclarationBillList1().setVisible(true);
		this.lbHeadText.setText("第二步：请选择你要生成的报关单的结转单据明细");
	}
	
	/**
	 * 后来增加第三步进行对归并后数据进行合并
	 */
	private void initP3() {
		this.pnContext.removeAll();
		this.pnContext.add(this.getPnMakeCustomsDeclarationBillListStep3(),BorderLayout.CENTER);
		this.getPnMakeCustomsDeclarationBillListStep3().setIsImport(getPnMakeCustomsDeclarationBillList().getIsImport());
		List BillList1 = pnMakeCustomsDeclarationBillList1.getCommodityList();
		this.getPnMakeCustomsDeclarationBillListStep3().setFptItemList(BillList1);
		System.out.println("第三步："+pnMakeCustomsDeclarationBillList1.getCommodityList().size());
		this.getPnMakeCustomsDeclarationBillListStep3().setVisible(true);
		this.lbHeadText.setText("第三步：对你【第二步】所选报关单据明细数量进行合并计算");
	}
	

	private void initP2() {
		this.pnContext.removeAll();
		this.pnContext.add(this.getPnMakeCustomsDeclarationParam(),	BorderLayout.CENTER);
//		this.pnMakeCustomsDeclarationParam.setParentHead(getPnMakeCustomsDeclarationBillList().getListData());
		this.getPnMakeCustomsDeclarationParam().setVisible(true);
		this.lbHeadText.setText("第四步：请选择一些基本信息，如果不选择可生成报关单后在报关单维护");
	}

	private void gotoStep(int step) {
		switch (step) {
		case 0:
			initP0();
			break;
		case 1:
			initP1();
			break;
		case 2:
			initP3();
			break;
		case 3:
			initP2();
			break;
		}
		this.pnContext.repaint();
		this.pnContext.validate();
		setState();
	}

	private void setState() {
		this.btnPrevious.setEnabled(this.step > 0);
		this.btnNext.setEnabled(this.step < 3);
		this.btnDone.setEnabled(this.step == 3);
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			this.gotoStep(this.step);
		}
		super.setVisible(b);
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
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.SOUTH);
			jContentPane.add(getPnContext(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJPanel11(), java.awt.BorderLayout.NORTH);
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
			jPanel1.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED));
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
			btnPrevious.setPreferredSize(new Dimension(70, 25));
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
			btnNext.setPreferredSize(new Dimension(70, 25));
			btnNext.setText("下一步");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (step == 0) {
						List list = getPnMakeCustomsDeclarationBillList()
								.getListData();
						if (list.size() < 1) {
							JOptionPane.showMessageDialog(
									DgMakeCustomsDeclaration.this,
									"请至少选择一个结转单据", "提示", 0);
							return;
						}
						PnMakeFptBillList p0 = getPnMakeCustomsDeclarationBillList();
						if (!p0.vaildateData()) {
							JOptionPane.showMessageDialog(
									DgMakeCustomsDeclaration.this,
									"所选手册/账册号/流水号与明细手册号不一致时!", "提示", 1);
							return;
						}
					} else if (step == 1) {
						List list = getPnMakeCustomsDeclarationBillList1()
								.getCommodityList();
						if (list.size() < 1) {
							JOptionPane.showMessageDialog(
									DgMakeCustomsDeclaration.this,
									"请至少选择一个结转单据明细", "提示", 0);
							return;
						}
						if (getPnMakeCustomsDeclarationBillList()
								.getJTextField1().getText() != null
								&& !""
										.equals(getPnMakeCustomsDeclarationBillList()
												.getJTextField1().getText())) {
							PnMakeFptBillList1 p0 = getPnMakeCustomsDeclarationBillList1();
							if(!inOutFlag.equals(FptInOutFlag.OUT)){
								if (!p0.vaildateData(getPnMakeCustomsDeclarationBillList()
										.getJTextField1().getText())) {
									JOptionPane.showMessageDialog(
											DgMakeCustomsDeclaration.this,
											"所选手册/账册号/流水号与明细手册号不一致时!", "提示", 0);
									return;
								}
							}
						}
					}else if(step==2){
						List list = getPnMakeCustomsDeclarationBillListStep3().getCommodityList();
						System.out.println("list size:"+list.size());
						if (list.size() < 1) {
							JOptionPane.showMessageDialog(
									DgMakeCustomsDeclaration.this,
									"请至少选择一个结转单据明细", "提示", 0);
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
			btnDone.setPreferredSize(new Dimension(70, 25));
			btnDone.setText("执行");
			btnDone.addActionListener(new java.awt.event.ActionListener() {
				@SuppressWarnings("rawtypes")
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (JOptionPane.showConfirmDialog(
							DgMakeCustomsDeclaration.this, "你确定要生成报关单吗?", "提示",
							JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					FptAppHead fptAppHead = getPnMakeCustomsDeclarationBillList().getSelectedFptAppHead();

					List billItems = getPnMakeCustomsDeclarationBillListStep3().getCommodityList();
					
					PnMakeFptBillListParam x = DgMakeCustomsDeclaration.this.getPnMakeCustomsDeclarationParam();
					@SuppressWarnings("unused")
					MakeCusomsDeclarationParam y = x.getMakeCusomsDeclarationParam();
					MakeCusomsDeclarationParam param = DgMakeCustomsDeclaration.this.getPnMakeCustomsDeclarationParam().getMakeCusomsDeclarationParam();
					List result = fptManageAction.makeCusomsDeclarationFromTransferBill(new Request(
									CommonVars.getCurrUser()), param, billItems,
									fptAppHead,
									getPnMakeCustomsDeclarationBillList().cm);
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
			btnExit.setPreferredSize(new Dimension(70, 25));
			btnExit.setText("关闭");
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
			pnContext.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		}
		return pnContext;
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
