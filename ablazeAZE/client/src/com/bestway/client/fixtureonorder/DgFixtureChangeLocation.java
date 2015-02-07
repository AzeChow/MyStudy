package com.bestway.client.fixtureonorder;

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
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.fixasset.entity.ChangeLocaOptionParam;

import com.bestway.fixtureonorder.action.FixtureContractAction;
import com.bestway.fixtureonorder.entity.FixtureLocation;
import com.bestway.fixtureonorder.entity.TempCustomsDeclarationFixture;
import com.bestway.fixtureonorder.entity.TempFixtureLocationChangeInfo;
import com.bestway.fixtureonorder.entity.TempOthersBillInfo;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author fhz
 *
 */
public class DgFixtureChangeLocation extends JDialogBase {

	private JPanel jContentPane = null;

	private JPanel pnHead = null;

	private JPanel jPanel1 = null;

	private JPanel pnContent = null;

	private JLabel lbInfo = null;

	private JLabel lbInfoDetail = null;

	private JButton btnPrevious = null;

	private JButton btnNext = null;

	private JButton btnClose = null;

	private FixtureContractAction fixtureContractAction;

	private int localOptionParam = ChangeLocaOptionParam.CUSTOMS_IN_FACT;

	private int step = 0;

	private File file = null;

	private FixtureLocation location = null;

	private TempOthersBillInfo otherBillInfo = null;

	private List lsCustomsBill = new ArrayList(); // @jve:decl-index=0:

	private List lsChangeFixture = new ArrayList(); // @jve:decl-index=0:

	private List lsChangeBillInfo = new ArrayList();

	private PnCustomsDeclarationFixture pnCustoms;

	private PnChangeLocationOption pnOption = null;

	private PnFixtureLocation pnLocation = null;

	private PnChangeFixture pnChangeFixture = null;

	private PnChangeOthersInfo pnOtherInfo = null;

	private PnFixtureChangeResult pnResult = null;

	private int index = 0;

	/**
	 * This method initializes
	 * 
	 */
	public DgFixtureChangeLocation() {
		super();
		initialize();
		fixtureContractAction = (FixtureContractAction) CommonVars
				.getApplicationContext().getBean("fixtureContractAction");
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
							pnChangeFixture = null;
							pnOtherInfo = null;
							pnResult = null;
							break;
						case 1:
							pnLocation = null;
							pnChangeFixture = null;
							pnOtherInfo = null;
							pnResult = null;
							break;
						case 2:
							pnChangeFixture = null;
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
							pnChangeFixture = null;
							pnOtherInfo = null;
							pnResult = null;
							break;
						case 1:
							pnChangeFixture = null;
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
							pnChangeFixture = null;
							pnOtherInfo = null;
							pnResult = null;
							break;
						case 1:
							pnChangeFixture = null;
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
							pnChangeFixture = null;
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

	private JPanel getPnChangeFixture(List list, FixtureLocation location,
			Integer changeType) {
		if (pnChangeFixture == null) {
			pnChangeFixture = new PnChangeFixture(list, location, changeType);
		}
		return pnChangeFixture;
	}

	private PnCustomsDeclarationFixture getPnCustoms() {
		if (pnCustoms == null) {
			pnCustoms = new PnCustomsDeclarationFixture();
		}
		return pnCustoms;
	}

	private PnFixtureLocation getPnFixtureLocation() {
		if (pnLocation == null) {
			pnLocation = new PnFixtureLocation();
		}
		return pnLocation;
	}

	private PnChangeOthersInfo getPnOtherInfo() {
		if (pnOtherInfo == null) {
			pnOtherInfo = new PnChangeOthersInfo();
		}
		return pnOtherInfo;
	}

	private PnFixtureChangeResult getPnChangeResult(List contralDataList,List lsChangeFixture,
			TempOthersBillInfo otherInfo) {
		if (pnResult == null) {
			pnResult = new PnFixtureChangeResult(contralDataList,lsChangeFixture, otherInfo);
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
						.getCustomsDeclarationFixture();
				lbInfo.setText("目标位置设定");
				lbInfoDetail.setText("设定设备的异动方向");
				pnContent.add(this.getPnFixtureLocation());
			} else if (step == 3) {
				this.location = this.getPnFixtureLocation()
						.getFixtureLocation();
				if (location == null) {
					JOptionPane.showMessageDialog(DgFixtureChangeLocation.this,
							"请选择要修改的位置", "提示", JOptionPane.INFORMATION_MESSAGE);
					step--;
					step();
				}
				lbInfo.setText("设备设定");
				lbInfoDetail.setText("设定异动设备及其数量");
				pnContent.add(this.getPnChangeFixture(this.lsCustomsBill,
						this.location, this.localOptionParam));
			} else if (step == 4) {
				this.lsChangeFixture = this.pnChangeFixture.getChangeFixture();
				if (controlData(lsChangeFixture, pnChangeFixture
						.getContralDataList()) == false) {
					JOptionPane.showMessageDialog(DgFixtureChangeLocation.this,
							"第 " + index + " 条数据的数量大于本有的数量。", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					step--;
					step();
				}
				lbInfo.setText("异动单据信息");
				lbInfoDetail.setText("单据相关的栏位信息");
				pnContent.add(this.getPnOtherInfo());
			} else if (step == 5) {
				this.otherBillInfo = this.getPnOtherInfo().getOtherBillInfo();
				if (otherBillInfo == null) {
					JOptionPane.showMessageDialog(DgFixtureChangeLocation.this,
							"请输入异动单据信息", "提示", JOptionPane.INFORMATION_MESSAGE);
					step--;
					step();
				}
				lbInfo.setText("成功获得异动信息");
				lbInfoDetail.setText("以下是即将进行的异动操作");
				this.btnNext.setText("完成");
				pnContent.add(this.getPnChangeResult(pnChangeFixture
						.getContralDataList(),this.lsChangeFixture,
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
				pnContent.add(this.getPnFixtureLocation());
			} else if (step == 2) {
				this.location = this.getPnFixtureLocation()
						.getFixtureLocation();
				if (location == null) {
					JOptionPane.showMessageDialog(DgFixtureChangeLocation.this,
							"请选择要修改的位置", "提示", JOptionPane.INFORMATION_MESSAGE);
					step--;
					step();
				}
				lbInfo.setText("设备设定");
				lbInfoDetail.setText("设定异动设备及其数量");
				pnContent.add(this.getPnChangeFixture(this.lsCustomsBill,
						this.location, this.localOptionParam));
			} else if (step == 3) {
				this.lsChangeFixture = this.pnChangeFixture.getChangeFixture();
				if (controlData(lsChangeFixture, pnChangeFixture
						.getContralDataList()) == false) {
					JOptionPane.showMessageDialog(DgFixtureChangeLocation.this,
							"第 " + index + " 条数据的数量大于本有的数量。", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					step--;
					step();
				}
				lbInfo.setText("异动单据信息");
				lbInfoDetail.setText("单据相关的栏位信息");
				pnContent.add(this.getPnOtherInfo());
			} else if (step == 4) {
				this.otherBillInfo = this.getPnOtherInfo().getOtherBillInfo();
				if (otherBillInfo == null) {
					JOptionPane.showMessageDialog(DgFixtureChangeLocation.this,
							"请输入异动单据信息", "提示", JOptionPane.INFORMATION_MESSAGE);
					step--;
					step();
				}
				lbInfo.setText("成功获得异动信息");
				lbInfoDetail.setText("以下是即将进行的异动操作");
				this.btnNext.setText("完成");
				pnContent.add(this.getPnChangeResult(pnChangeFixture
						.getContralDataList(),this.lsChangeFixture,
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
				pnContent.add(this.getPnFixtureLocation());
			} else if (step == 2) {
				this.location = this.getPnFixtureLocation()
						.getFixtureLocation();
				if (location == null) {
					JOptionPane.showMessageDialog(DgFixtureChangeLocation.this,
							"请选择要修改的位置", "提示", JOptionPane.INFORMATION_MESSAGE);
					step--;
					step();
				}
				lbInfo.setText("设备设定");
				lbInfoDetail.setText("设定异动设备及其数量");
				pnContent.add(this.getPnChangeFixture(this.lsCustomsBill,
						this.location, this.localOptionParam));
			} else if (step == 3) {
				this.lsChangeFixture = this.pnChangeFixture.getChangeFixture();
				if (controlData(lsChangeFixture, pnChangeFixture
						.getContralDataList()) == false) {
					JOptionPane.showMessageDialog(DgFixtureChangeLocation.this,
							"第 " + index + " 条数据的数量大于本有的数量。", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					step--;
					step();
				}
				lbInfo.setText("异动单据信息");
				lbInfoDetail.setText("单据相关的栏位信息");
				pnContent.add(this.getPnOtherInfo());
			} else if (step == 4) {
				this.otherBillInfo = this.getPnOtherInfo().getOtherBillInfo();
				if (otherBillInfo == null) {
					JOptionPane.showMessageDialog(DgFixtureChangeLocation.this,
							"请输入异动单据信息", "提示", JOptionPane.INFORMATION_MESSAGE);
					step--;
					step();
				}
				lbInfo.setText("成功获得异动信息");
				lbInfoDetail.setText("以下是即将进行的异动操作");
				this.btnNext.setText("完成");
				pnContent.add(this.getPnChangeResult(pnChangeFixture
						.getContralDataList(),this.lsChangeFixture,
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
				pnContent.add(this.getPnChangeFixture(this.lsCustomsBill,
						this.location, this.localOptionParam));
			} else if (step == 2) {
				this.lsChangeFixture = this.pnChangeFixture.getChangeFixture();
				if (controlData(lsChangeFixture, pnChangeFixture
						.getContralDataList()) == false) {
					JOptionPane.showMessageDialog(DgFixtureChangeLocation.this,
							"第 " + index + " 条数据的数量大于本有的数量。", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					step--;
					step();
				}
				lbInfo.setText("异动单据信息");
				lbInfoDetail.setText("单据相关的栏位信息");
				pnContent.add(this.getPnOtherInfo());
			} else if (step == 3) {
				this.otherBillInfo = this.getPnOtherInfo().getOtherBillInfo();
				if (otherBillInfo == null) {
					JOptionPane.showMessageDialog(DgFixtureChangeLocation.this,
							"请输入异动单据信息", "提示", JOptionPane.INFORMATION_MESSAGE);
					step--;
					step();
				}
				lbInfo.setText("成功获得异动信息");
				lbInfoDetail.setText("以下是即将进行的异动操作");
				this.btnNext.setText("完成");
				pnContent.add(this.getPnChangeResult(pnChangeFixture
						.getContralDataList(),this.lsChangeFixture,
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
		fixtureContractAction.saveFixtureLocationChangeBillInfo(new Request(
				CommonVars.getCurrUser()), list, localOptionParam);
		dispose();
	}

	/**
	 * 判断修改后数量是否要超出已有保存的数量
	 * 
	 * @param lsChangeFixture
	 * @param contralDataList
	 * @return
	 */
	private boolean controlData(List lsChangeFixture, List contralDataList) {
		for (int i = 0; i < lsChangeFixture.size(); i++) {
			TempFixtureLocationChangeInfo changeFixture = (TempFixtureLocationChangeInfo) lsChangeFixture
					.get(i);
			if (contralDataList.contains(changeFixture)) {
				TempFixtureLocationChangeInfo contralChangeInfo = (TempFixtureLocationChangeInfo) contralDataList
						.get(contralDataList.indexOf(changeFixture));
				if(CommonUtils.getDoubleExceptNull(contralChangeInfo.getLocationAmount())
						<CommonUtils.getDoubleExceptNull(changeFixture.getLocationAmount())){
					index=i;
					return false;
				}
					
			}
//			contralDataList.remove(changeFixture);
		}
		return true;
	}
} 
