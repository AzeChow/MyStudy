package com.bestway.bcus.client.checkstock;

import com.bestway.bcus.checkstock.action.ECSCheckStockAction;
import com.bestway.bcus.checkstock.action.ECSCheckStockAuthority;
import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.bcus.client.checkstock.contractanalyse.FmECSContractAnalyse;
import com.bestway.bcus.client.checkstock.contractanalyse.FmECSCustomsCountImg;
import com.bestway.bcus.client.checkstock.contractanalyse.FmECSDeclarationCommInfoExg;
import com.bestway.bcus.client.checkstock.contractanalyse.FmECSDeclarationCommInfoExgx;
import com.bestway.bcus.client.checkstock.contractanalyse.FmECSDeclarationCommInfoImg;
import com.bestway.bcus.client.checkstock.factoryanalyse.FmECSBadImg;
import com.bestway.bcus.client.checkstock.factoryanalyse.FmECSBadStock;
import com.bestway.bcus.client.checkstock.factoryanalyse.FmECSFinishedExg;
import com.bestway.bcus.client.checkstock.factoryanalyse.FmECSFinishingExg;
import com.bestway.bcus.client.checkstock.factoryanalyse.FmECSFinishingImg;
import com.bestway.bcus.client.checkstock.factoryanalyse.FmECSFinishingStock;
import com.bestway.bcus.client.checkstock.factoryanalyse.FmECSSemiFinishedExg;
import com.bestway.bcus.client.checkstock.factoryanalyse.FmECSStockAnalyse;
import com.bestway.bcus.client.checkstock.factoryanalyse.FmECSStockBuyImg;
import com.bestway.bcus.client.checkstock.factoryanalyse.FmECSStockExg;
import com.bestway.bcus.client.checkstock.factoryanalyse.FmECSStockImg;
import com.bestway.bcus.client.checkstock.factoryanalyse.FmECSStockOutFactoryImg;
import com.bestway.bcus.client.checkstock.factoryanalyse.FmECSStockOutSendAnalyse;
import com.bestway.bcus.client.checkstock.factoryanalyse.FmECSStockOutSendExgPt;
import com.bestway.bcus.client.checkstock.factoryanalyse.FmECSStockOutSendImg;
import com.bestway.bcus.client.checkstock.factoryanalyse.FmECSStockOutSendSemiExgPt;
import com.bestway.bcus.client.checkstock.factoryanalyse.FmECSStockProcessImg;
import com.bestway.bcus.client.checkstock.factoryanalyse.FmECSStockSemiExgPtHadStore;
import com.bestway.bcus.client.checkstock.factoryanalyse.FmECSStockTravelingExg;
import com.bestway.bcus.client.checkstock.factoryanalyse.FmECSStockTravelingImg;
import com.bestway.bcus.client.checkstock.transferanalyse.FmECSTransferCollectBalance;
import com.bestway.bcus.client.checkstock.transferanalyse.FmECSTransferExgBalance;
import com.bestway.bcus.client.checkstock.transferanalyse.FmECSTransferImgBalance;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.windows.control.ShowFormControl;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

import javax.swing.JSplitPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bestway.ui.winuicontrol.drawflow.FlowLine;
import com.bestway.ui.winuicontrol.drawflow.FlowButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

public class FmProcessGuide extends JInternalFrameBase {
	private JPanel panel;
	private JSplitPane splitPane;
	private JPanel panel_1;
	private JPanel panel_2;
	private JScrollPane scrollPane;
	private JTable table;
	private JTableListModel tableModel = null;
	private ECSCheckStockAction ecsCheckStockAction = null;
	private Request request;
	private FlowButton btnNewButton;
	private FlowButton flowButton_1;
	private FlowButton flowButton_2;
	private FlowButton flowButton_3;
	private FlowButton flowButton_4;
	private FlowButton flowButton_5;
	private FlowButton flowButton_10;
	private FlowButton flowButton_9;
	private FlowButton flowButton_8;
	private FlowButton flowButton_7;
	private FlowButton flowButton_6;
	private FlowButton button_9;
	private FlowButton flowButton_11;
	private FlowButton flowButton_12;
	private FlowButton flowButton_13;
	private FlowButton flowButton_18;
	private FlowButton flowButton_19;
	private FlowButton flowButton_23;
	private FlowButton flowButton_28;
	private FlowButton button_18;
	private FlowButton flowButton_32;
	private FlowButton flowButton_17;
	private FlowButton flowButton_14;
	private FlowButton flowButton_15;
	private FlowButton flowButton_16;
	private FlowLine flowLine;
	private FlowLine flowLine_2;
	private FlowLine flowLine_5;
	private FlowLine flowLine_6;
	private FlowLine flowLine_7;
	private FlowButton flowButton_20;
	private FlowLine flowLine_9;
	private FlowButton flowButton_21;
	private FlowButton flowButton_22;
	private FlowLine flowLine_11;
	private FlowLine flowLine_3;
	private FlowButton flowButton_24;
	private FlowButton flowButton_25;
	private FlowButton flowButton_26;
	private FlowButton flowButton_27;
	private FlowButton flowButton_31;
	private FlowButton flowButton_29;
	private FlowButton flowButton_30;
	private FlowLine flowLine_1;
	private FlowLine flowLine_17;
	private FlowLine flowLine_18;
	private FlowLine flowLine_19;
	private FlowLine flowLine_20;
	private FlowLine flowLine_21;
	private FlowLine flowLine_22;
	private FlowLine flowLine_23;
	private FlowLine flowLine_24;
	private FlowLine flowLine_25;
	private FlowLine flowLine_26;
	private FlowLine flowLine_27;
	private FlowLine flowLine_28;
	private FlowLine flowLine_30;
	private FlowLine flowLine_32;
	private FlowLine flowLine_33;
	private FlowLine flowLine_34;
	private FlowLine flowLine_37;
	private Image image = null;
	private FlowButton flowButton;
	private FlowLine flowLine_10;
	private FlowLine flowLine_38;
	private FlowLine flowLine_39;
	private FlowLine flowLine_40;
	private FlowLine flowLine_41;
	private FlowLine flowLine_42;
	private FlowLine flowLine_4;
	private FlowLine flowLine_43;
	private FlowLine flowLine_44;
	private FlowLine flowLine_45;
	private FlowLine flowLine_46;
	private JLabel lblNewLabel;
	private FlowButton flowButton_33;
	private JLabel label;
	private FlowButton flowButton_34;
	private JButton jButton1 = null;
	public FmProcessGuide() {
		this.setTitle("流程指引");
		request = new Request(CommonVars.getCurrUser());
		ecsCheckStockAction = (ECSCheckStockAction) CommonVars
				.getApplicationContext().getBean("ecsCheckStockAction");
		ECSCheckStockAuthority authority = (ECSCheckStockAuthority) CommonVars
				.getApplicationContext().getBean("ecsCheckStockAuthority");
		authority.checkAnalyse(request);
		image = CommonVars.getImageSource().getImage("background.gif");
		getContentPane().add(getPanel_2(), BorderLayout.CENTER);
		initialize();
	}
	private void initialize(){
		List list = ecsCheckStockAction.findEcsSection(request);
		if (list == null) {
			list = new ArrayList();
		}
		initTable(list);
		ECSSection section = (ECSSection)tableModel.getCurrentRow();
		if(section !=null){
			setColor(section);
		}
	}
	
	private JPanel getPanel_2() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new BorderLayout(0, 0));
			panel.add(getSplitPane_1(), BorderLayout.CENTER);
		}
		return panel;
	}
	private JSplitPane getSplitPane_1() {
		if (splitPane == null) {
			splitPane = new JSplitPane();
			splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPane.setDividerLocation(135);
			splitPane.setLeftComponent(getPanel_1_1());
			splitPane.setRightComponent(getPanel_2_1());
		}
		return splitPane;
	}
	private JPanel getPanel_1_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setLayout(new BorderLayout(0, 0));
			panel_1.add(getScrollPane(), BorderLayout.CENTER);
		}
		return panel_1;
	}
	private JPanel getPanel_2_1() {
		if (panel_2 == null) {
			panel_2 = new JPanel();
			panel_2.setLayout(null);
			panel_2.add(getBtnNewButton());
			panel_2.add(getFlowButton_1());
			panel_2.add(getFlowButton_2());
			panel_2.add(getFlowButton_3());
			panel_2.add(getFlowButton_4());
			panel_2.add(getFlowButton_5());
			panel_2.add(getFlowButton_10());
			panel_2.add(getFlowButton_9());
			panel_2.add(getFlowButton_8());
			panel_2.add(getFlowButton_7());
			panel_2.add(getFlowButton_6());
			panel_2.add(getButton_9());
			panel_2.add(getFlowButton_11());
			panel_2.add(getFlowButton_12());
			panel_2.add(getFlowButton_13());
			panel_2.add(getFlowButton_18());
			panel_2.add(getFlowButton_19());
			panel_2.add(getFlowButton_23());
			panel_2.add(getFlowButton_28());
			panel_2.add(getButton_18());
			panel_2.add(getFlowButton_32());
			panel_2.add(getFlowButton_17());
			panel_2.add(getFlowButton_14());
			panel_2.add(getFlowButton_15());
			panel_2.add(getFlowButton_16());
			panel_2.add(getFlowLine_38());
			panel_2.add(getFlowLine_2());
			panel_2.add(getFlowLine_5());
			panel_2.add(getFlowLine_6());
			panel_2.add(getFlowLine_7());
			panel_2.add(getFlowButton_20());
			panel_2.add(getFlowLine_9());
			panel_2.add(getFlowButton_21());
			panel_2.add(getFlowButton_22());
			panel_2.add(getFlowLine_11());
			panel_2.add(getFlowLine_3_1());
			panel_2.add(getFlowButton_24());
			panel_2.add(getFlowButton_25());
			panel_2.add(getButton_32_1());
			panel_2.add(getButton_33_1());
			panel_2.add(getButton_34_1());
			panel_2.add(getButton_35_1());
			panel_2.add(getFlowButton_30());
			panel_2.add(getFlowLine_1_1());
			panel_2.add(getFlowLine_17());
			panel_2.add(getFlowLine_18());
			panel_2.add(getFlowLine_19());
			panel_2.add(getFlowLine_20());
			panel_2.add(getFlowLine_21());
			panel_2.add(getFlowLine_22());
			panel_2.add(getFlowLine_23());
			panel_2.add(getFlowLine_24());
			panel_2.add(getFlowLine_25());
			panel_2.add(getFlowLine_26());
			panel_2.add(getFlowLine_27());
			panel_2.add(getFlowLine_28());
			panel_2.add(getFlowLine_30());
			panel_2.add(getFlowLine_32_1());
			panel_2.add(getFlowLine_33());
			panel_2.add(getFlowLine_34());
			panel_2.add(getFlowLine_37());
			panel_2.add(getFlowButton());
			panel_2.add(getFlowLine_10());
			panel_2.add(getFlowLine_38_1());
			panel_2.add(getFlowLine_39());
			panel_2.add(getFlowLine_40());
			panel_2.add(getFlowLine_41());
			panel_2.add(getFlowLine_42());
			panel_2.add(getFlowLine_4_1());
			panel_2.add(getFlowLine_43());
			panel_2.add(getFlowLine_44());
			panel_2.add(getFlowLine_45());
			panel_2.add(getFlowLine_46());
			panel_2.add(getLblNewLabel());
			panel_2.add(getFlowButton_33());
			panel_2.add(getLabel());
			panel_2.add(getFlowButton_34());
			panel_2.add(getJButton1());
		}
		return panel_2;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	private JTable getTable() {
		if (table == null) {
			table = new JTable();
			table.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						@Override
						public void valueChanged(ListSelectionEvent e) {
							if(tableModel==null||tableModel.getCurrentRow()==null){
								return;
							}
							ECSSection section = (ECSSection)tableModel.getCurrentRow();
							setColor(section);
						}
					});
		}
		return table;
	}
	
	/**
	 * 
	 * @param jTable
	 * @param list
	 * @return
	 */
	private void initTable(List list) {
		tableModel = new JTableListModel(table, list,
			new JTableListModelAdapter() {
				public List InitColumns() {
					List<JTableListColumn> list = new ArrayList<JTableListColumn>();
					list.add(addColumn("报核次数","cancelOwnerHead.cancelTimes", 80));
					list.add(addColumn("批次号", "verificationNo", 60));
					list.add(addColumn("盘点开始日期", "beginDate", 120));
					list.add(addColumn("盘点结束日期", "endDate", 120));
					list.add(addColumn("报核开始日期","cancelOwnerHead.beginDate", 120));
					list.add(addColumn("报核结束日期", "cancelOwnerHead.endDate",
							120));
					return list;
				}
			});
	}
	private FlowButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new FlowButton();
			btnNewButton.setForeground(new Color(6, 73, 201));
			btnNewButton.setBottomFilledColor(new Color(168, 244, 255));
			btnNewButton.setText("第二步：帐册数据分析");
			btnNewButton.setBounds(345, 35, 167, 23);
		}
		return btnNewButton;
	}
	private FlowButton getFlowButton_1() {
		if (flowButton_1 == null) {
			flowButton_1 = new FlowButton();
			flowButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection section = (ECSSection)tableModel.getCurrentRow();
					FmECSDeclarationCommInfoImg fm = new FmECSDeclarationCommInfoImg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_1.setText("料件明细表");
			flowButton_1.setBounds(89, 97, 128, 23);
		}
		return flowButton_1;
	}
	private FlowButton getFlowButton_2() {
		if (flowButton_2 == null) {
			flowButton_2 = new FlowButton();
			flowButton_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection section = (ECSSection)tableModel.getCurrentRow();
					FmECSDeclarationCommInfoExgx fm = new FmECSDeclarationCommInfoExgx();
					fm.showData(section,null,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_2.setText("成品明细表");
			flowButton_2.setBounds(227, 97, 128, 23);
		}
		return flowButton_2;
	}
	private FlowButton getFlowButton_3() {
		if (flowButton_3 == null) {
			flowButton_3 = new FlowButton();
			flowButton_3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection section = (ECSSection)tableModel.getCurrentRow();
					FmECSCustomsCountImg fm = new FmECSCustomsCountImg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_3.setText("料件情况统计表");
			flowButton_3.setBounds(365, 97, 128, 23);
		}
		return flowButton_3;
	}
	private FlowButton getFlowButton_4() {
		if (flowButton_4 == null) {
			flowButton_4 = new FlowButton();
			flowButton_4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection section = (ECSSection)tableModel.getCurrentRow();
					FmECSDeclarationCommInfoExg fm = new FmECSDeclarationCommInfoExg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_4.setText("成品情况统计表");
			flowButton_4.setBounds(503, 97, 128, 23);
		}
		return flowButton_4;
	}
	private FlowButton getFlowButton_5() {
		if (flowButton_5 == null) {
			flowButton_5 = new FlowButton();
			flowButton_5.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection section = (ECSSection)tableModel.getCurrentRow();
					FmECSContractAnalyse fm = new FmECSContractAnalyse();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_5.setText("帐册分析");
			flowButton_5.setBounds(641, 97, 128, 23);
		}
		return flowButton_5;
	}
	private FlowButton getFlowButton_10() {
		if (flowButton_10 == null) {
			flowButton_10 = new FlowButton();
			flowButton_10.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection section = (ECSSection)tableModel.getCurrentRow();
					FmECSStockBuyImg fm = new FmECSStockBuyImg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_10.setText("内购库存");
			flowButton_10.setBounds(89, 323, 84, 23);
		}
		return flowButton_10;
	}
	private FlowButton getFlowButton_9() {
		if (flowButton_9 == null) {
			flowButton_9 = new FlowButton();
			flowButton_9.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection section = (ECSSection)tableModel.getCurrentRow();
					FmECSStockOutFactoryImg fm = new FmECSStockOutFactoryImg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_9.setText("厂外库存");
			flowButton_9.setBounds(89, 290, 84, 23);
		}
		return flowButton_9;
	}
	private FlowButton getFlowButton_8() {
		if (flowButton_8 == null) {
			flowButton_8 = new FlowButton();
			flowButton_8.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection section = (ECSSection)tableModel.getCurrentRow();
					FmECSStockExg fm = new FmECSStockExg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_8.setText("成品库存");
			flowButton_8.setBounds(89, 257, 84, 23);
		}
		return flowButton_8;
	}
	private FlowButton getFlowButton_7() {
		if (flowButton_7 == null) {
			flowButton_7 = new FlowButton();
			flowButton_7.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection section = (ECSSection)tableModel.getCurrentRow();
					FmECSStockProcessImg fm = new FmECSStockProcessImg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_7.setText("在产品库存");
			flowButton_7.setBounds(89, 224, 84, 23);
		}
		return flowButton_7;
	}
	private FlowButton getFlowButton_6() {
		if (flowButton_6 == null) {
			flowButton_6 = new FlowButton();
			flowButton_6.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection section = (ECSSection)tableModel.getCurrentRow();
					FmECSStockImg fm = new FmECSStockImg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_6.setText("料件库存");
			flowButton_6.setBounds(89, 191, 84, 23);
		}
		return flowButton_6;
	}
	private FlowButton getButton_9() {
		if (button_9 == null) {
			button_9 = new FlowButton();
			button_9.setText("第三步：盘点数据分析");
			button_9.setForeground(new Color(6, 73, 201));
			button_9.setBottomFilledColor(new Color(168, 244, 255));
			button_9.setBounds(345, 130, 167, 23);
		}
		return button_9;
	}
	private FlowButton getFlowButton_11() {
		if (flowButton_11 == null) {
			flowButton_11 = new FlowButton();
			flowButton_11.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection section = (ECSSection)tableModel.getCurrentRow();
					FmECSStockTravelingImg fm = new FmECSStockTravelingImg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_11.setText("在途料件");
			flowButton_11.setBounds(172, 191, 73, 23);
		}
		return flowButton_11;
	}
	private FlowButton getFlowButton_12() {
		if (flowButton_12 == null) {
			flowButton_12 = new FlowButton();
			flowButton_12.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection section = (ECSSection)tableModel.getCurrentRow();
					FmECSStockTravelingExg fm = new FmECSStockTravelingExg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_12.setText("在途成品");
			flowButton_12.setBounds(172, 224, 73, 23);
		}
		return flowButton_12;
	}
	private FlowButton getFlowButton_13() {
		if (flowButton_13 == null) {
			flowButton_13 = new FlowButton();
			flowButton_13.setText("外发库存");
			flowButton_13.setBounds(245, 191, 110, 23);
		}
		return flowButton_13;
	}
	private FlowButton getFlowButton_18() {
		if (flowButton_18 == null) {
			flowButton_18 = new FlowButton();
			flowButton_18.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection section = (ECSSection)tableModel.getCurrentRow();
					FmECSStockSemiExgPtHadStore fm = new FmECSStockSemiExgPtHadStore();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_18.setText("半成品库存(已入库)");
			flowButton_18.setBounds(357, 191, 141, 23);
		}
		return flowButton_18;
	}
	private FlowButton getFlowButton_19() {
		if (flowButton_19 == null) {
			flowButton_19 = new FlowButton();
			flowButton_19.setText("在制品库存");
			flowButton_19.setBounds(498, 191, 116, 23);
		}
		return flowButton_19;
	}
	private FlowButton getFlowButton_23() {
		if (flowButton_23 == null) {
			flowButton_23 = new FlowButton();
			flowButton_23.setText("残次品库存");
			flowButton_23.setBounds(614, 191, 114, 23);
		}
		return flowButton_23;
	}
	private FlowButton getFlowButton_28() {
		if (flowButton_28 == null) {
			flowButton_28 = new FlowButton();
			flowButton_28.setText("盘点总库存");
			flowButton_28.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection section = (ECSSection)tableModel.getCurrentRow();
					FmECSStockAnalyse fm = new FmECSStockAnalyse();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_28.setBounds(730, 191, 81, 23);
		}
		return flowButton_28;
	}
	private FlowButton getButton_18() {
		if (button_18 == null) {
			button_18 = new FlowButton();
			button_18.setText("第四步：结转数据分析");
			button_18.setForeground(new Color(6, 73, 201));
			button_18.setBottomFilledColor(new Color(168, 244, 255));
			button_18.setBounds(345, 356, 167, 23);
		}
		return button_18;
	}
	private FlowButton getFlowButton_32() {
		if (flowButton_32 == null) {
			flowButton_32 = new FlowButton();
			flowButton_32.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection section = (ECSSection)tableModel.getCurrentRow();
					FmECSAnalyse fm = new FmECSAnalyse();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_32.setText("第五步：短溢分析");
			flowButton_32.setForeground(new Color(6, 73, 201));
			flowButton_32.setBottomFilledColor(new Color(168, 244, 255));
			flowButton_32.setBounds(345, 454, 167, 23);
		}
		return flowButton_32;
	}
	private FlowButton getFlowButton_17() {
		if (flowButton_17 == null) {
			flowButton_17 = new FlowButton();
			flowButton_17.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection section = (ECSSection)tableModel.getCurrentRow();
					FmECSStockOutSendAnalyse fm = new FmECSStockOutSendAnalyse();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_17.setText("外发库存汇总");
			flowButton_17.setBounds(245, 323, 110, 23);
		}
		return flowButton_17;
	}
	private FlowButton getFlowButton_14() {
		if (flowButton_14 == null) {
			flowButton_14 = new FlowButton();
			flowButton_14.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection section = (ECSSection)tableModel.getCurrentRow();
					FmECSStockOutSendImg fm = new FmECSStockOutSendImg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_14.setText("原材料");
			flowButton_14.setBounds(245, 224, 110, 23);
		}
		return flowButton_14;
	}
	private FlowButton getFlowButton_15() {
		if (flowButton_15 == null) {
			flowButton_15 = new FlowButton();
			flowButton_15.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection section = (ECSSection)tableModel.getCurrentRow();
					FmECSStockOutSendSemiExgPt fm = new FmECSStockOutSendSemiExgPt();
//					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_15.setText("半成品");
			flowButton_15.setBounds(245, 257, 110, 23);
		}
		return flowButton_15;
	}
	private FlowButton getFlowButton_16() {
		if (flowButton_16 == null) {
			flowButton_16 = new FlowButton();
			flowButton_16.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection section = (ECSSection)tableModel.getCurrentRow();
					FmECSStockOutSendExgPt fm = new FmECSStockOutSendExgPt();
//					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_16.setText("成品");
			flowButton_16.setBounds(245, 290, 110, 23);
		}
		return flowButton_16;
	}
	private FlowLine getFlowLine_38() {
		if (flowLine == null) {
			flowLine = new FlowLine();
			flowLine.setText("");
			flowLine.setOnlyDrawLine(true);
			flowLine.setArrowDirection(1);
			flowLine.setBounds(126, 164, 642, 11);
		}
		return flowLine;
	}
	private FlowLine getFlowLine_2() {
		if (flowLine_2 == null) {
			flowLine_2 = new FlowLine();
			flowLine_2.setText("");
			flowLine_2.setArrowDirection(3);
			flowLine_2.setBounds(416, 153, 22, 16);
		}
		return flowLine_2;
	}
	private FlowLine getFlowLine_5() {
		if (flowLine_5 == null) {
			flowLine_5 = new FlowLine();
			flowLine_5.setText("");
			flowLine_5.setArrowDirection(3);
			flowLine_5.setBounds(284, 246, 22, 11);
		}
		return flowLine_5;
	}
	private FlowLine getFlowLine_6() {
		if (flowLine_6 == null) {
			flowLine_6 = new FlowLine();
			flowLine_6.setText("");
			flowLine_6.setArrowDirection(3);
			flowLine_6.setBounds(284, 281, 22, 11);
		}
		return flowLine_6;
	}
	private FlowLine getFlowLine_7() {
		if (flowLine_7 == null) {
			flowLine_7 = new FlowLine();
			flowLine_7.setText("");
			flowLine_7.setArrowDirection(3);
			flowLine_7.setBounds(284, 312, 22, 11);
		}
		return flowLine_7;
	}
	private FlowButton getFlowButton_20() {
		if (flowButton_20 == null) {
			flowButton_20 = new FlowButton();
			flowButton_20.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection section = (ECSSection)tableModel.getCurrentRow();
					FmECSFinishingImg fm = new FmECSFinishingImg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_20.setText("原材料");
			flowButton_20.setBounds(500, 224, 114, 23);
		}
		return flowButton_20;
	}
	private FlowLine getFlowLine_9() {
		if (flowLine_9 == null) {
			flowLine_9 = new FlowLine();
			flowLine_9.setText("");
			flowLine_9.setArrowDirection(3);
			flowLine_9.setBounds(546, 246, 22, 11);
		}
		return flowLine_9;
	}
	private FlowButton getFlowButton_21() {
		if (flowButton_21 == null) {
			flowButton_21 = new FlowButton();
			flowButton_21.setText("成品");
			flowButton_21.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection section = (ECSSection)tableModel.getCurrentRow();
					FmECSFinishingExg fm = new FmECSFinishingExg();
//					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_21.setBounds(500, 257, 114, 23);
		}
		return flowButton_21;
	}
	private FlowButton getFlowButton_22() {
		if (flowButton_22 == null) {
			flowButton_22 = new FlowButton();
			flowButton_22.setText("在制品库存汇总");
			flowButton_22.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection section = (ECSSection)tableModel.getCurrentRow();
					FmECSFinishingStock fm = new FmECSFinishingStock();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_22.setBounds(498, 290, 114, 23);
		}
		return flowButton_22;
	}
	private FlowLine getFlowLine_11() {
		if (flowLine_11 == null) {
			flowLine_11 = new FlowLine();
			flowLine_11.setText("");
			flowLine_11.setArrowDirection(3);
			flowLine_11.setBounds(546, 281, 22, 11);
		}
		return flowLine_11;
	}
	private FlowLine getFlowLine_3_1() {
		if (flowLine_3 == null) {
			flowLine_3 = new FlowLine();
			flowLine_3.setText("");
			flowLine_3.setOnlyDrawLine(true);
			flowLine_3.setArrowDirection(1);
			flowLine_3.setBounds(148, 68, 552, 11);
		}
		return flowLine_3;
	}
	private FlowButton getFlowButton_24() {
		if (flowButton_24 == null) {
			flowButton_24 = new FlowButton();
			flowButton_24.setText("原材料");
			flowButton_24.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection section = (ECSSection)tableModel.getCurrentRow();
					FmECSBadImg fm = new FmECSBadImg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_24.setBounds(614, 224, 114, 23);
		}
		return flowButton_24;
	}
	private FlowButton getFlowButton_25() {
		if (flowButton_25 == null) {
			flowButton_25 = new FlowButton();
			flowButton_25.setText("半成品");
			flowButton_25.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection section = (ECSSection)tableModel.getCurrentRow();
					FmECSSemiFinishedExg fm = new FmECSSemiFinishedExg();
//					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_25.setBounds(614, 257, 114, 23);
		}
		return flowButton_25;
	}
	private FlowButton getButton_32_1() {
		if (flowButton_26 == null) {
			flowButton_26 = new FlowButton();
			flowButton_26.setText("成品");
			flowButton_26.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection section = (ECSSection)tableModel.getCurrentRow();
					FmECSFinishedExg fm = new FmECSFinishedExg();
//					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_26.setBounds(614, 290, 114, 23);
		}
		return flowButton_26;
	}
	private FlowButton getButton_33_1() {
		if (flowButton_27 == null) {
			flowButton_27 = new FlowButton();
			flowButton_27.setText("残次品库存汇总");
			flowButton_27.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection section = (ECSSection)tableModel.getCurrentRow();
					FmECSBadStock fm = new FmECSBadStock();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_27.setBounds(612, 323, 114, 23);
		}
		return flowButton_27;
	}
	private FlowButton getButton_34_1() {
		if (flowButton_31 == null) {
			flowButton_31 = new FlowButton();
			flowButton_31.setText("结转汇总情况表");
			flowButton_31.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection section = (ECSSection)tableModel.getCurrentRow();
					FmECSTransferCollectBalance fm = new FmECSTransferCollectBalance();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_31.setBounds(492, 421, 128, 23);
		}
		return flowButton_31;
	}
	private FlowButton getButton_35_1() {
		if (flowButton_29 == null) {
			flowButton_29 = new FlowButton();
			flowButton_29.setText("料件结转情况表");
			flowButton_29.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection section = (ECSSection)tableModel.getCurrentRow();
					FmECSTransferImgBalance fm = new FmECSTransferImgBalance();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_29.setBounds(216, 421, 128, 23);
		}
		return flowButton_29;
	}
	private FlowButton getFlowButton_30() {
		if (flowButton_30 == null) {
			flowButton_30 = new FlowButton();
			flowButton_30.setText("成品结转情况表");
			flowButton_30.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection section = (ECSSection)tableModel.getCurrentRow();
					FmECSTransferExgBalance fm = new FmECSTransferExgBalance();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_30.setBounds(354, 421, 128, 23);
		}
		return flowButton_30;
	}
	private FlowLine getFlowLine_1_1() {
		if (flowLine_1 == null) {
			flowLine_1 = new FlowLine();
			flowLine_1.setText("");
			flowLine_1.setArrowDirection(3);
			flowLine_1.setBounds(114, 169, 22, 23);
		}
		return flowLine_1;
	}
	private FlowLine getFlowLine_17() {
		if (flowLine_17 == null) {
			flowLine_17 = new FlowLine();
			flowLine_17.setText("");
			flowLine_17.setArrowDirection(3);
			flowLine_17.setBounds(415, 57, 22, 16);
		}
		return flowLine_17;
	}
	private FlowLine getFlowLine_18() {
		if (flowLine_18 == null) {
			flowLine_18 = new FlowLine();
			flowLine_18.setText("");
			flowLine_18.setArrowDirection(3);
			flowLine_18.setBounds(135, 73, 22, 22);
		}
		return flowLine_18;
	}
	private FlowLine getFlowLine_19() {
		if (flowLine_19 == null) {
			flowLine_19 = new FlowLine();
			flowLine_19.setText("");
			flowLine_19.setArrowDirection(3);
			flowLine_19.setBounds(689, 73, 22, 22);
		}
		return flowLine_19;
	}
	private FlowLine getFlowLine_20() {
		if (flowLine_20 == null) {
			flowLine_20 = new FlowLine();
			flowLine_20.setText("");
			flowLine_20.setArrowDirection(3);
			flowLine_20.setBounds(404, 380, 22, 16);
		}
		return flowLine_20;
	}
	private FlowLine getFlowLine_21() {
		if (flowLine_21 == null) {
			flowLine_21 = new FlowLine();
			flowLine_21.setText("");
			flowLine_21.setOnlyDrawLine(true);
			flowLine_21.setArrowDirection(1);
			flowLine_21.setBounds(285, 391, 280, 22);
		}
		return flowLine_21;
	}
	private FlowLine getFlowLine_22() {
		if (flowLine_22 == null) {
			flowLine_22 = new FlowLine();
			flowLine_22.setText("");
			flowLine_22.setArrowDirection(3);
			flowLine_22.setBounds(273, 403, 22, 16);
		}
		return flowLine_22;
	}
	private FlowLine getFlowLine_23() {
		if (flowLine_23 == null) {
			flowLine_23 = new FlowLine();
			flowLine_23.setText("");
			flowLine_23.setArrowDirection(3);
			flowLine_23.setBounds(554, 403, 22, 16);
		}
		return flowLine_23;
	}
	private FlowLine getFlowLine_24() {
		if (flowLine_24 == null) {
			flowLine_24 = new FlowLine();
			flowLine_24.setText("");
			flowLine_24.setArrowDirection(3);
			flowLine_24.setBounds(284, 73, 22, 22);
		}
		return flowLine_24;
	}
	private FlowLine getFlowLine_25() {
		if (flowLine_25 == null) {
			flowLine_25 = new FlowLine();
			flowLine_25.setText("");
			flowLine_25.setArrowDirection(3);
			flowLine_25.setBounds(415, 73, 22, 22);
		}
		return flowLine_25;
	}
	private FlowLine getFlowLine_26() {
		if (flowLine_26 == null) {
			flowLine_26 = new FlowLine();
			flowLine_26.setText("");
			flowLine_26.setArrowDirection(3);
			flowLine_26.setBounds(546, 73, 22, 22);
		}
		return flowLine_26;
	}
	private FlowLine getFlowLine_27() {
		if (flowLine_27 == null) {
			flowLine_27 = new FlowLine();
			flowLine_27.setText("");
			flowLine_27.setArrowDirection(3);
			flowLine_27.setBounds(197, 169, 22, 23);
		}
		return flowLine_27;
	}
	private FlowLine getFlowLine_28() {
		if (flowLine_28 == null) {
			flowLine_28 = new FlowLine();
			flowLine_28.setText("");
			flowLine_28.setArrowDirection(3);
			flowLine_28.setBounds(285, 169, 22, 23);
		}
		return flowLine_28;
	}
	private FlowLine getFlowLine_30() {
		if (flowLine_30 == null) {
			flowLine_30 = new FlowLine();
			flowLine_30.setText("");
			flowLine_30.setArrowDirection(3);
			flowLine_30.setBounds(416, 169, 22, 23);
		}
		return flowLine_30;
	}
	private FlowLine getFlowLine_32_1() {
		if (flowLine_32 == null) {
			flowLine_32 = new FlowLine();
			flowLine_32.setText("");
			flowLine_32.setArrowDirection(3);
			flowLine_32.setBounds(547, 169, 22, 23);
		}
		return flowLine_32;
	}
	private FlowLine getFlowLine_33() {
		if (flowLine_33 == null) {
			flowLine_33 = new FlowLine();
			flowLine_33.setText("");
			flowLine_33.setArrowDirection(3);
			flowLine_33.setBounds(655, 169, 22, 23);
		}
		return flowLine_33;
	}
	private FlowLine getFlowLine_34() {
		if (flowLine_34 == null) {
			flowLine_34 = new FlowLine();
			flowLine_34.setText("");
			flowLine_34.setArrowDirection(3);
			flowLine_34.setBounds(757, 169, 22, 23);
		}
		return flowLine_34;
	}
	private FlowLine getFlowLine_37() {
		if (flowLine_37 == null) {
			flowLine_37 = new FlowLine();
			flowLine_37.setText("");
			flowLine_37.setArrowDirection(3);
			flowLine_37.setBounds(404, 403, 22, 16);
		}
		return flowLine_37;
	}
	
	public void setColor(ECSSection section){
		FlowButton[] button = new FlowButton[33];
		button[0] = this.flowButton_1;
		button[1] = this.flowButton_2;
		button[2] = this.flowButton_3;
		button[3] = this.flowButton_4;
		button[4] = this.flowButton_5;
		button[5] = this.flowButton_6;
		button[6] = this.flowButton_7;
		button[7] = this.flowButton_8;
		button[8] = this.flowButton_9;
		button[9] = this.flowButton_10;
		button[10] = this.flowButton_11;
		button[11] = this.flowButton_12;
		button[12] = this.flowButton_13;
		button[13] = this.flowButton_14;
		button[14] = this.flowButton_15;
		button[15] = this.flowButton_16;
		button[16] = this.flowButton_17;
		button[17] = this.flowButton_18;
		button[18] = this.flowButton_19;
		button[19] = this.flowButton_20;
		button[20] = this.flowButton_21;
		button[21] = this.flowButton_22;
		button[22] = this.flowButton_23;
		button[23] = this.flowButton_24;
		button[24] = this.flowButton_25;
		button[25] = this.flowButton_26;
		button[26] = this.flowButton_27;
		button[27] = this.flowButton_28;
		button[28] = this.flowButton_29;
		button[29] = this.flowButton_30;
		button[30] = this.flowButton_31;
		button[31] = this.flowButton_32;
		List<Integer> prcess = ecsCheckStockAction.findPrcess(request,section);
		for (int i = 0; i < button.length-1; i++) {
			button[i].setBottomFilledColor(new Color(240,240,240));
		}
		for (int i = 0; i < prcess.size(); i++) {
			if(prcess.get(i)!=null){
//				button[prcess.get(i)].setBottomFilledColor(new Color("#3cb371"));
//				button[prcess.get(i)].setBottomFilledColor(new Color(10,200,200));
				button[prcess.get(i)].setBottomFilledColor(new Color(100,220,100));
			}
		}
	}
	private FlowButton getFlowButton() {
		if (flowButton == null) {
			flowButton = new FlowButton();
			flowButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection section = (ECSSection)tableModel.getCurrentRow();
					FmECSAttachment fm = new FmECSAttachment();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton.setText("第一步：工作分配与资料管理");
			flowButton.setForeground(new Color(6, 73, 201));
			flowButton.setBottomFilledColor(new Color(168, 244, 255));
			flowButton.setBounds(298, 2, 270, 23);
		}
		return flowButton;
	}
	private FlowLine getFlowLine_10() {
		if (flowLine_10 == null) {
			flowLine_10 = new FlowLine();
			flowLine_10.setText("");
			flowLine_10.setArrowDirection(3);
			flowLine_10.setBounds(113, 213, 22, 11);
		}
		return flowLine_10;
	}
	private FlowLine getFlowLine_38_1() {
		if (flowLine_38 == null) {
			flowLine_38 = new FlowLine();
			flowLine_38.setText("");
			flowLine_38.setArrowDirection(3);
			flowLine_38.setBounds(113, 246, 22, 11);
		}
		return flowLine_38;
	}
	private FlowLine getFlowLine_39() {
		if (flowLine_39 == null) {
			flowLine_39 = new FlowLine();
			flowLine_39.setText("");
			flowLine_39.setArrowDirection(3);
			flowLine_39.setBounds(113, 278, 22, 11);
		}
		return flowLine_39;
	}
	private FlowLine getFlowLine_40() {
		if (flowLine_40 == null) {
			flowLine_40 = new FlowLine();
			flowLine_40.setText("");
			flowLine_40.setArrowDirection(3);
			flowLine_40.setBounds(113, 312, 22, 11);
		}
		return flowLine_40;
	}
	private FlowLine getFlowLine_41() {
		if (flowLine_41 == null) {
			flowLine_41 = new FlowLine();
			flowLine_41.setText("");
			flowLine_41.setArrowDirection(3);
			flowLine_41.setBounds(196, 213, 22, 11);
		}
		return flowLine_41;
	}
	private FlowLine getFlowLine_42() {
		if (flowLine_42 == null) {
			flowLine_42 = new FlowLine();
			flowLine_42.setText("");
			flowLine_42.setArrowDirection(3);
			flowLine_42.setBounds(284, 213, 22, 11);
		}
		return flowLine_42;
	}
	private FlowLine getFlowLine_4_1() {
		if (flowLine_4 == null) {
			flowLine_4 = new FlowLine();
			flowLine_4.setText("");
			flowLine_4.setArrowDirection(3);
			flowLine_4.setBounds(546, 213, 22, 11);
		}
		return flowLine_4;
	}
	private FlowLine getFlowLine_43() {
		if (flowLine_43 == null) {
			flowLine_43 = new FlowLine();
			flowLine_43.setText("");
			flowLine_43.setArrowDirection(3);
			flowLine_43.setBounds(655, 312, 22, 11);
		}
		return flowLine_43;
	}
	private FlowLine getFlowLine_44() {
		if (flowLine_44 == null) {
			flowLine_44 = new FlowLine();
			flowLine_44.setText("");
			flowLine_44.setArrowDirection(3);
			flowLine_44.setBounds(655, 281, 22, 11);
		}
		return flowLine_44;
	}
	private FlowLine getFlowLine_45() {
		if (flowLine_45 == null) {
			flowLine_45 = new FlowLine();
			flowLine_45.setText("");
			flowLine_45.setArrowDirection(3);
			flowLine_45.setBounds(655, 246, 22, 11);
		}
		return flowLine_45;
	}
	private FlowLine getFlowLine_46() {
		if (flowLine_46 == null) {
			flowLine_46 = new FlowLine();
			flowLine_46.setText("");
			flowLine_46.setArrowDirection(3);
			flowLine_46.setBounds(655, 213, 22, 11);
		}
		return flowLine_46;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("已计算：");
			lblNewLabel.setBounds(10, 39, 54, 15);
		}
		return lblNewLabel;
	}
	private FlowButton getFlowButton_33() {
		if (flowButton_33 == null) {
			flowButton_33 = new FlowButton();
			flowButton_33.setBounds(63, 35, 73, 23);
			flowButton_33.setBottomFilledColor(new Color(100,220,100));
		}
		return flowButton_33;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("未计算：");
			label.setBounds(10, 68, 54, 15);
		}
		return label;
	}
	private FlowButton getFlowButton_34() {
		if (flowButton_34 == null) {
			flowButton_34 = new FlowButton();
			flowButton_34.setBounds(63, 64, 73, 23);
		}
		return flowButton_34;
	}
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("刷新");
			jButton1.setBounds(10, 6, 73, 23);
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
//					initTable(null);
					setState();
				}
			});
		}
		return jButton1;
	}
	
	protected void setState() {
		 if (tableModel == null || tableModel.getCurrentRow() == null) {
	            return;
	        } else {
	        	ECSSection section = (ECSSection) tableModel.getCurrentRow();
	            if (section != null) {
	                setColor(section);
	            }
	        }
	}
}
