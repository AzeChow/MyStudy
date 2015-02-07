package com.bestway.client.fixasset;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.fixasset.action.FixAssetAction;
import com.bestway.fixasset.entity.ChangeLocaOptionParam;
import com.bestway.fixasset.entity.FixassetLocation;
import com.bestway.fixasset.entity.TempOtherBillInfo;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgFixassetChangeLocation extends JDialogBase {

	private JPanel jContentPane = null;

	private JPanel pnHead = null;

	private JPanel jPanel1 = null;

	private JPanel pnContent = null;

	private JLabel lbInfo = null;

	private JLabel lbInfoDetail = null;

	private JButton btnPrevious = null;

	private JButton btnNext = null;

	private JButton btnClose = null;

	private FixAssetAction fixAssetAction;

	private int localOptionParam = ChangeLocaOptionParam.CUSTOMS_IN_FACT;

	private int step = 0;
	
	private File file =null;

	private FixassetLocation location = null;

	private TempOtherBillInfo otherBillInfo = null;

	private List lsCustomsBill = new ArrayList();

	private List lsChangeFixasset = new ArrayList(); // @jve:decl-index=0:

	private List lsChangeBillInfo = new ArrayList();

	private PnCustomsDeclarationFixasset pnCustoms;

	private PnChangeLocationOption pnOption = null;

	private PnFixassetLocation pnLocation = null;

	private PnChangeFixasset pnChangeFixasset = null;

	private PnChangeOtherInfo pnOtherInfo = null;

	private PnFixassetChangeResult pnResult = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgFixassetChangeLocation() {
		super();
		initialize();
		fixAssetAction = (FixAssetAction) CommonVars.getApplicationContext()
				.getBean("fixAssetAction");
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(582, 451));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("设备异动");
		this.setContentPane(getJContentPane());

	}

	public void setVisible(boolean b) {
		if (b) {
			step();
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
			jContentPane.setLayout(null);
			jContentPane.add(getPnHead(), null);
			jContentPane.add(getJPanel1(), null);
			jContentPane.add(getPnContent(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnHead() {
		if (pnHead == null) {
			lbInfoDetail = new JLabel();
			lbInfoDetail.setBounds(new Rectangle(68, 45, 408, 22));
			lbInfoDetail.setText("JLabel");
			lbInfo = new JLabel();
			lbInfo.setBounds(new Rectangle(6, 13, 214, 25));
			lbInfo.setFont(new Font("Dialog", Font.BOLD, 18));
			lbInfo.setText("JLabel");
			pnHead = new JPanel();
			pnHead.setLayout(null);
			pnHead.setBounds(new Rectangle(2, 2, 571, 82));
			pnHead.setBackground(Color.white);
			pnHead.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.RAISED));
			pnHead.add(lbInfo, null);
			pnHead.add(lbInfoDetail, null);
		}
		return pnHead;
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
			jPanel1.setBounds(new Rectangle(1, 354, 570, 62));
			jPanel1.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.RAISED));
			jPanel1.add(getBtnPrevious(), null);
			jPanel1.add(getBtnNext(), null);
			jPanel1.add(getBtnClose(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnContent() {
		if (pnContent == null) {
			pnContent = new JPanel();
			pnContent.setLayout(new BorderLayout());
			pnContent.setBounds(new Rectangle(3, 84, 569, 270));
		}
		return pnContent;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setBounds(new Rectangle(298, 18, 70, 27));
			btnPrevious.setText("上一步");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					step--;
					if (localOptionParam == ChangeLocaOptionParam.CUSTOMS_IN_FACT) {
						switch (step) {
						case 0:
							pnCustoms = null;
							pnLocation = null;
							pnChangeFixasset = null;
							pnOtherInfo = null;
							pnResult = null;
							break;
						case 1:
							pnLocation = null;
							pnChangeFixasset = null;
							pnOtherInfo = null;
							pnResult = null;
							break;
						case 2:
							pnChangeFixasset = null;
							pnOtherInfo = null;
							pnResult = null;
							break;
						case 3:
							pnOtherInfo = null;
							pnResult = null;
							break;
						case 4:
							pnResult = null;
							break;
						}
					} else if (localOptionParam == ChangeLocaOptionParam.FACT_CHANGE_LOCATION) {
						switch (step) {
						case 0:
							pnLocation = null;
							pnChangeFixasset = null;
							pnOtherInfo = null;
							pnResult = null;
							break;
						case 1:
							pnChangeFixasset = null;
							pnOtherInfo = null;
							pnResult = null;
							break;
						case 2:
							pnOtherInfo = null;
							pnResult = null;
							break;
						case 3:
							pnResult = null;
							break;						
						}
					} else if (localOptionParam == ChangeLocaOptionParam.FACT_ADD) {
						switch (step) {
						case 0:
							pnLocation = null;
							pnChangeFixasset = null;
							pnOtherInfo = null;
							pnResult = null;
							break;
						case 1:
							pnChangeFixasset = null;
							pnOtherInfo = null;
							pnResult = null;
							break;
						case 2:
							pnOtherInfo = null;
							pnResult = null;
							break;
						case 3:
							pnResult = null;
							break;						
						}
					} else if (localOptionParam == ChangeLocaOptionParam.FACT_SUBTRACT) {
						switch (step) {
						case 0:
							pnChangeFixasset = null;
							pnOtherInfo = null;
							pnResult = null;
							break;
						case 1:
							pnOtherInfo = null;
							pnResult = null;
							break;
						case 2:
							pnResult = null;
							break;						
						}
					}
					step();
				}
			});
		}
		return btnPrevious;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setBounds(new Rectangle(385, 18, 70, 27));
			btnNext.setText("下一步");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (localOptionParam == ChangeLocaOptionParam.CUSTOMS_IN_FACT) {
						if (step == 5) {
							saveFixassetLocationChangeBillInfo();
						}
					} else if (localOptionParam == ChangeLocaOptionParam.FACT_CHANGE_LOCATION) {
						if (step == 4) {
							saveFixassetLocationChangeBillInfo();
						}
					} else if (localOptionParam == ChangeLocaOptionParam.FACT_ADD) {
						if (step == 4) {
							saveFixassetLocationChangeBillInfo();
						}
					} else if (localOptionParam == ChangeLocaOptionParam.FACT_SUBTRACT) {
						if (step == 3) {
							saveFixassetLocationChangeBillInfo();
						}
					}
					step++;
					step();
				}
			});
		}
		return btnNext;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setBounds(new Rectangle(480, 18, 70, 27));
			btnClose.setText("取消");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}

	private JPanel getPnChangeLocationOption() {
		if (pnOption == null) {
			pnOption = new PnChangeLocationOption(
					new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
								String actionCommand = ((JRadioButton) e
										.getSource()).getActionCommand();
								if (actionCommand != null
										&& !"".equals(actionCommand.trim())) {
									localOptionParam = Integer
											.parseInt(actionCommand);
								}
							}
						}
					});
		}
		return pnOption;
	}

	private JPanel getPnChangeFixasset(List list, FixassetLocation location,
			Integer changeType) {
		if (pnChangeFixasset == null) {
			pnChangeFixasset = new PnChangeFixasset(list, location, changeType);
		}
		return pnChangeFixasset;
	}

	private PnCustomsDeclarationFixasset getPnCustoms() {
		if (pnCustoms == null) {
			pnCustoms = new PnCustomsDeclarationFixasset();
		}
		return pnCustoms;
	}

	private PnFixassetLocation getPnFixassetLocation() {
		if (pnLocation == null) {
			pnLocation = new PnFixassetLocation();
		}
		return pnLocation;
	}

	private PnChangeOtherInfo getPnOtherInfo() {
		if (pnOtherInfo == null) {
			pnOtherInfo = new PnChangeOtherInfo();
		}
		return pnOtherInfo;
	}

	private PnFixassetChangeResult getPnChangeResult(List list,
			TempOtherBillInfo otherInfo) {
		if (pnResult == null) {
			pnResult = new PnFixassetChangeResult(list, otherInfo);
		}
		return pnResult;
	}

	private void step() {
		this.btnNext.setText("下一步");
		if (this.localOptionParam == ChangeLocaOptionParam.CUSTOMS_IN_FACT) {
			pnContent.removeAll();
			if (step == 0) {
				lbInfo.setText("操作类型");
				lbInfoDetail.setText("根据异动设备的来源和去向选择相应的类型");
				pnContent.add(this.getPnChangeLocationOption());
			} else if (step == 1) {
				lbInfo.setText("选择进出口报关单项目");
				lbInfoDetail.setText("将报关设备入厂");
				pnContent.add(this.getPnCustoms());
			} else if (step == 2) {
				this.lsCustomsBill = this.pnCustoms
						.getCustomsDeclarationFixasset();
				lbInfo.setText("目标位置设定");
				lbInfoDetail.setText("设定设备的异动方向");
				pnContent.add(this.getPnFixassetLocation());
			} else if (step == 3) {
				this.location = this.getPnFixassetLocation()
						.getFixassetLocation();
				if (location == null) {
					JOptionPane.showMessageDialog(
							DgFixassetChangeLocation.this, "请选择要修改的位置", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					step--;
					step();
				}
				lbInfo.setText("设备设定");
				lbInfoDetail.setText("设定异动设备及其数量");
				pnContent.add(this.getPnChangeFixasset(this.lsCustomsBill,
						this.location, this.localOptionParam));
			} else if (step == 4) {
				this.lsChangeFixasset = this.pnChangeFixasset
						.getChangeFixasset();
				lbInfo.setText("异动单据信息");
				lbInfoDetail.setText("单据相关的栏位信息");
				pnContent.add(this.getPnOtherInfo());
			} else if (step == 5) {
				this.otherBillInfo = this.getPnOtherInfo().getOtherBillInfo();
				if (otherBillInfo == null) {
					JOptionPane.showMessageDialog(
							DgFixassetChangeLocation.this, "请输入异动单据信息", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					step--;
					step();
				}
				lbInfo.setText("成功获得异动信息");
				lbInfoDetail.setText("以下是即将进行的异动操作");
				this.btnNext.setText("完成");
				pnContent.add(this.getPnChangeResult(this.lsChangeFixasset,
						this.otherBillInfo));
			}
		} else if (this.localOptionParam == ChangeLocaOptionParam.FACT_CHANGE_LOCATION) {
			pnContent.removeAll();
			if (step == 0) {
				lbInfo.setText("操作类型");
				lbInfoDetail.setText("根据异动设备的来源和去向选择相应的类型");
				pnContent.add(this.getPnChangeLocationOption());
			} else if (step == 1) {
				lbInfo.setText("目标位置设定");
				lbInfoDetail.setText("设定设备的异动方向");
				pnContent.add(this.getPnFixassetLocation());
			} else if (step == 2) {
				this.location = this.getPnFixassetLocation()
						.getFixassetLocation();
				if (location == null) {
					JOptionPane.showMessageDialog(
							DgFixassetChangeLocation.this, "请选择要修改的位置", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					step--;
					step();
				}
				lbInfo.setText("设备设定");
				lbInfoDetail.setText("设定异动设备及其数量");
				pnContent.add(this.getPnChangeFixasset(this.lsCustomsBill,
						this.location, this.localOptionParam));
			} else if (step == 3) {
				this.lsChangeFixasset = this.pnChangeFixasset
						.getChangeFixasset();
				lbInfo.setText("异动单据信息");
				lbInfoDetail.setText("单据相关的栏位信息");
				pnContent.add(this.getPnOtherInfo());
			} else if (step == 4) {
				this.otherBillInfo = this.getPnOtherInfo().getOtherBillInfo();
				if (otherBillInfo == null) {
					JOptionPane.showMessageDialog(
							DgFixassetChangeLocation.this, "请输入异动单据信息", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					step--;
					step();
				}
				lbInfo.setText("成功获得异动信息");
				lbInfoDetail.setText("以下是即将进行的异动操作");
				this.btnNext.setText("完成");
				pnContent.add(this.getPnChangeResult(this.lsChangeFixasset,
						this.otherBillInfo));
			}
		} else if (this.localOptionParam == ChangeLocaOptionParam.FACT_ADD) {
			pnContent.removeAll();
			if (step == 0) {
				lbInfo.setText("操作类型");
				lbInfoDetail.setText("根据异动设备的来源和去向选择相应的类型");
				pnContent.add(this.getPnChangeLocationOption());
			} else if (step == 1) {
				lbInfo.setText("目标位置设定");
				lbInfoDetail.setText("设定设备的异动方向");
				pnContent.add(this.getPnFixassetLocation());
			} else if (step == 2) {
				this.location = this.getPnFixassetLocation()
						.getFixassetLocation();
				if (location == null) {
					JOptionPane.showMessageDialog(
							DgFixassetChangeLocation.this, "请选择要修改的位置", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					step--;
					step();
				}
				lbInfo.setText("设备设定");
				lbInfoDetail.setText("设定异动设备及其数量");
				pnContent.add(this.getPnChangeFixasset(this.lsCustomsBill,
						this.location, this.localOptionParam));
			} else if (step == 3) {
				this.lsChangeFixasset = this.pnChangeFixasset
						.getChangeFixasset();
				lbInfo.setText("异动单据信息");
				lbInfoDetail.setText("单据相关的栏位信息");
				pnContent.add(this.getPnOtherInfo());
			} else if (step == 4) {
				this.otherBillInfo = this.getPnOtherInfo().getOtherBillInfo();
				if (otherBillInfo == null) {
					JOptionPane.showMessageDialog(
							DgFixassetChangeLocation.this, "请输入异动单据信息", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					step--;
					step();
				}
				lbInfo.setText("成功获得异动信息");
				lbInfoDetail.setText("以下是即将进行的异动操作");
				this.btnNext.setText("完成");
				pnContent.add(this.getPnChangeResult(this.lsChangeFixasset,
						this.otherBillInfo));
			}
		} else if (this.localOptionParam == ChangeLocaOptionParam.FACT_SUBTRACT) {
			pnContent.removeAll();
			if (step == 0) {
				lbInfo.setText("操作类型");
				lbInfoDetail.setText("根据异动设备的来源和去向选择相应的类型");
				pnContent.add(this.getPnChangeLocationOption());
			} else if (step == 1) {
				lbInfo.setText("设备设定");
				lbInfoDetail.setText("设定异动设备及其数量");
				pnContent.add(this.getPnChangeFixasset(this.lsCustomsBill,
						this.location, this.localOptionParam));
			} else if (step == 2) {
				this.lsChangeFixasset = this.pnChangeFixasset
						.getChangeFixasset();
				lbInfo.setText("异动单据信息");
				lbInfoDetail.setText("单据相关的栏位信息");
				pnContent.add(this.getPnOtherInfo());
			} else if (step == 3) {
				this.otherBillInfo = this.getPnOtherInfo().getOtherBillInfo();
				if (otherBillInfo == null) {
					JOptionPane.showMessageDialog(
							DgFixassetChangeLocation.this, "请输入异动单据信息", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					step--;
					step();
				}
				lbInfo.setText("成功获得异动信息");
				lbInfoDetail.setText("以下是即将进行的异动操作");
				this.btnNext.setText("完成");
				pnContent.add(this.getPnChangeResult(this.lsChangeFixasset,
						this.otherBillInfo));
			}
		}
		setState();
		pnContent.repaint();
	}

	private void setState() {
		this.btnPrevious.setEnabled(step > 0);
		if (this.localOptionParam == ChangeLocaOptionParam.CUSTOMS_IN_FACT) {
			this.btnNext.setEnabled(step < 6);
		} else if (this.localOptionParam == ChangeLocaOptionParam.FACT_CHANGE_LOCATION) {
			this.btnNext.setEnabled(step < 5);
		} else if (this.localOptionParam == ChangeLocaOptionParam.FACT_ADD) {
			this.btnNext.setEnabled(step < 5);
		} else if (this.localOptionParam == ChangeLocaOptionParam.FACT_SUBTRACT) {
			this.btnNext.setEnabled(step < 4);
		}
	}

	private void saveFixassetLocationChangeBillInfo() {
		List list = pnResult.getChangeBillInfo();
		fixAssetAction.saveFixassetLocationChangeBillInfo(new Request(
				CommonVars.getCurrUser()), list, localOptionParam);
		dispose();
	}

} // @jve:decl-index=0:visual-constraint="10,10"
