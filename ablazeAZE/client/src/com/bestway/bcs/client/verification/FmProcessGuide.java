package com.bestway.bcs.client.verification;

import com.bestway.bcs.client.verification.contractanalyse.FmVFContractAnalyse;
import com.bestway.bcs.client.verification.contractanalyse.FmVFCustomsCountExg;
import com.bestway.bcs.client.verification.contractanalyse.FmVFCustomsCountExgConvert;
import com.bestway.bcs.client.verification.contractanalyse.FmVFCustomsCountImg;
import com.bestway.bcs.client.verification.contractanalyse.FmVFDeclarationCommInfoExg;
import com.bestway.bcs.client.verification.contractanalyse.FmVFDeclarationCommInfoImg;
import com.bestway.bcs.client.verification.factoryanalyse.FmVFBadExg;
import com.bestway.bcs.client.verification.factoryanalyse.FmVFBadImg;
import com.bestway.bcs.client.verification.factoryanalyse.FmVFBadStockAnalyse;
import com.bestway.bcs.client.verification.factoryanalyse.FmVFFinishingExg;
import com.bestway.bcs.client.verification.factoryanalyse.FmVFFinishingImg;
import com.bestway.bcs.client.verification.factoryanalyse.FmVFFinishingStock;
import com.bestway.bcs.client.verification.factoryanalyse.FmVFSemiBadExg;
import com.bestway.bcs.client.verification.factoryanalyse.FmVFStockAnalyse;
import com.bestway.bcs.client.verification.factoryanalyse.FmVFStockBuyImg;
import com.bestway.bcs.client.verification.factoryanalyse.FmVFStockExg;
import com.bestway.bcs.client.verification.factoryanalyse.FmVFStockHalfExg;
import com.bestway.bcs.client.verification.factoryanalyse.FmVFStockImg;
import com.bestway.bcs.client.verification.factoryanalyse.FmVFStockOutFactoryImg;
import com.bestway.bcs.client.verification.factoryanalyse.FmVFStockOutSendAnalyse;
import com.bestway.bcs.client.verification.factoryanalyse.FmVFStockOutSendExg;
import com.bestway.bcs.client.verification.factoryanalyse.FmVFStockOutSendImg;
import com.bestway.bcs.client.verification.factoryanalyse.FmVFStockOutSendSemiExg;
import com.bestway.bcs.client.verification.factoryanalyse.FmVFStockSemiExgHadStore;
import com.bestway.bcs.client.verification.factoryanalyse.FmVFStockTravelingExg;
import com.bestway.bcs.client.verification.factoryanalyse.FmVFStockTravelingImg;
import com.bestway.bcs.client.verification.transferanalyse.FmVFTransferDiffCount;
import com.bestway.bcs.client.verification.transferanalyse.FmVFTransferDiffExg;
import com.bestway.bcs.client.verification.transferanalyse.FmVFTransferDiffImg;
import com.bestway.bcs.verification.action.VFVerificationAction;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.windows.control.ShowFormControl;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

import javax.swing.JSplitPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
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
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.ui.winuicontrol.drawflow.FlowLine;
import com.bestway.ui.winuicontrol.drawflow.FlowButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JTextField;

public class FmProcessGuide extends JInternalFrameBase {
	private JPanel panel;
	private JSplitPane splitPane;
	private JPanel panel_1;
	private JPanel panel_2;
	private JScrollPane scrollPane;
	private JTable table;
	private JTableListModel tableModel = null;
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
	private FlowButton flowButton_18;
	private FlowButton flowButton_28;
	private FlowButton button_18;
	private FlowButton flowButton_32;
	private FlowButton flowButton_17;
	private FlowButton flowButton_14;
	private FlowButton flowButton_15;
	private FlowButton flowButton_16;
	private FlowLine flowLine;
	private FlowLine flowLine_2;
	private FlowButton flowButton_20;
	private FlowButton flowButton_21;
	private FlowButton flowButton_22;
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
	private FlowLine flowLine_28;
	private FlowLine flowLine_32;
	private FlowLine flowLine_33;
	private FlowLine flowLine_34;
	private FlowLine flowLine_37;
	private Image image = null;
	private FlowButton flowButton;
	private JLabel lblNewLabel;
	private FlowButton flowButton_33;
	private JLabel label;
	private FlowButton flowButton_34;
	private FlowButton flowButton_35;
	private FlowLine flowLine_8;
	private VFVerificationAction vfVerificationAction;
	private JButton jButton1 = null;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_5;
	private JPanel panel_6;
	public FmProcessGuide() {
		this.setTitle("流程指引");
		vfVerificationAction = (VFVerificationAction) CommonVars.getApplicationContext()
				.getBean("vfVerificationAction");
		request = new Request(CommonVars.getCurrUser());
		image = CommonVars.getImageSource().getImage("background.gif");
		getContentPane().add(getPanel_2(), BorderLayout.CENTER);
		initialize();
	}
	private void initialize(){
		List list = vfVerificationAction.findVFSectionList(request);
		if (list == null) {
			list = new ArrayList();
		}
		initTable(list);
		VFSection section = (VFSection)tableModel.getCurrentRow();
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
			panel_2.add(getButton_9());
			panel_2.add(getFlowButton_28());
			panel_2.add(getButton_18());
			panel_2.add(getFlowButton_32());
			panel_2.add(getFlowLine_38());
			panel_2.add(getFlowLine_2());
			panel_2.add(getFlowLine_3_1());
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
			panel_2.add(getFlowLine_28());
			panel_2.add(getFlowLine_32_1());
			panel_2.add(getFlowLine_33());
			panel_2.add(getFlowLine_34());
			panel_2.add(getFlowLine_37());
			panel_2.add(getFlowButton());
			panel_2.add(getLblNewLabel());
			panel_2.add(getFlowButton_33());
			panel_2.add(getLabel());
			panel_2.add(getFlowButton_34());
			panel_2.add(getFlowButton_35());
			panel_2.add(getFlowLine_8());
			panel_2.add(getJButton1(), null);
			panel_2.add(getPanel_3());
			panel_2.add(getPanel_4());
			panel_2.add(getPanel_5());
			panel_2.add(getPanel_6());
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
							VFSection section = (VFSection)tableModel.getCurrentRow();
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
					list.add(addColumn("核查批次", "verificationNo", 60));
					list.add(addColumn("截止时间", "endDate", 100));
					list.add(addColumn("结转差额分析文本导入方式", "isImportHs", 200));
					list.add(addColumn("创建时间", "createDate", 100));
					list.add(addColumn("创建人", "createPeople", 100));
					list.add(addColumn("备注", "memo", 300));
					return list;
				}
			});
		
		table.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			/* (non-Javadoc)
			 * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
			 */
			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				String formatValue = "料号级";
				if("true".equals(value)) {
					formatValue = "编码级";
				}
				return super.getTableCellRendererComponent(table, formatValue, isSelected, hasFocus,
						row, column);
			}
		});
	}
	private FlowButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new FlowButton();
			btnNewButton.setForeground(new Color(6, 73, 201));
			btnNewButton.setBottomFilledColor(new Color(168, 244, 255));
			btnNewButton.setText("第二步：合同数据分析");
			btnNewButton.setBounds(345, 35, 167, 23);
		}
		return btnNewButton;
	}
	private FlowButton getFlowButton_1() {
		if (flowButton_1 == null) {
			flowButton_1 = new FlowButton();
			flowButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = (VFSection)tableModel.getCurrentRow();
					FmVFDeclarationCommInfoImg fm = new FmVFDeclarationCommInfoImg();
					fm.showData(section,null,null,null);
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
					VFSection section = (VFSection)tableModel.getCurrentRow();
					FmVFDeclarationCommInfoExg fm = new FmVFDeclarationCommInfoExg();
					fm.showData(section,null,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_2.setText("成品明细表");
			flowButton_2.setBounds(216, 97, 128, 23);
		}
		return flowButton_2;
	}
	private FlowButton getFlowButton_3() {
		if (flowButton_3 == null) {
			flowButton_3 = new FlowButton();
			flowButton_3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = (VFSection)tableModel.getCurrentRow();
					FmVFCustomsCountImg fm = new FmVFCustomsCountImg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_3.setText("料件情况统计表");
			flowButton_3.setBounds(345, 97, 128, 23);
		}
		return flowButton_3;
	}
	private FlowButton getFlowButton_4() {
		if (flowButton_4 == null) {
			flowButton_4 = new FlowButton();
			flowButton_4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = (VFSection)tableModel.getCurrentRow();
					FmVFCustomsCountExg fm = new FmVFCustomsCountExg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_4.setText("成品情况统计表");
			flowButton_4.setBounds(473, 97, 128, 23);
		}
		return flowButton_4;
	}
	private FlowButton getFlowButton_5() {
		if (flowButton_5 == null) {
			flowButton_5 = new FlowButton();
			flowButton_5.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = (VFSection)tableModel.getCurrentRow();
					FmVFContractAnalyse fm = new FmVFContractAnalyse();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_5.setText("合同分析");
			flowButton_5.setBounds(730, 97, 128, 23);
		}
		return flowButton_5;
	}
	private FlowButton getFlowButton_10() {
		if (flowButton_10 == null) {
			flowButton_10 = new FlowButton();
			flowButton_10.setBounds(10, 115, 142, 23);
			flowButton_10.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = (VFSection)tableModel.getCurrentRow();
					FmVFStockBuyImg fm = new FmVFStockBuyImg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_10.setText("内购库存");
		}
		return flowButton_10;
	}
	private FlowButton getFlowButton_9() {
		if (flowButton_9 == null) {
			flowButton_9 = new FlowButton();
			flowButton_9.setBounds(10, 91, 142, 23);
			flowButton_9.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = (VFSection)tableModel.getCurrentRow();
					FmVFStockOutFactoryImg fm = new FmVFStockOutFactoryImg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_9.setText("厂外库存");
		}
		return flowButton_9;
	}
	private FlowButton getFlowButton_8() {
		if (flowButton_8 == null) {
			flowButton_8 = new FlowButton();
			flowButton_8.setBounds(10, 67, 142, 23);
			flowButton_8.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = (VFSection)tableModel.getCurrentRow();
					FmVFStockExg fm = new FmVFStockExg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_8.setText("成品库存");
		}
		return flowButton_8;
	}
	private FlowButton getFlowButton_7() {
		if (flowButton_7 == null) {
			flowButton_7 = new FlowButton();
			flowButton_7.setBounds(10, 43, 142, 23);
			flowButton_7.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = (VFSection)tableModel.getCurrentRow();
					FmVFStockHalfExg fm = new FmVFStockHalfExg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_7.setText("在产品库存");
		}
		return flowButton_7;
	}
	private FlowButton getFlowButton_6() {
		if (flowButton_6 == null) {
			flowButton_6 = new FlowButton();
			flowButton_6.setBounds(10, 19, 142, 23);
			flowButton_6.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = (VFSection)tableModel.getCurrentRow();
					FmVFStockImg fm = new FmVFStockImg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_6.setText("料件库存");
		}
		return flowButton_6;
	}
	private FlowButton getButton_9() {
		if (button_9 == null) {
			button_9 = new FlowButton();
			button_9.setText("第三步：工厂盘点数据分析");
			button_9.setForeground(new Color(6, 73, 201));
			button_9.setBottomFilledColor(new Color(168, 244, 255));
			button_9.setBounds(345, 130, 167, 23);
		}
		return button_9;
	}
	private FlowButton getFlowButton_11() {
		if (flowButton_11 == null) {
			flowButton_11 = new FlowButton();
			flowButton_11.setBounds(10, 139, 142, 23);
			flowButton_11.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = (VFSection)tableModel.getCurrentRow();
					FmVFStockTravelingImg fm = new FmVFStockTravelingImg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_11.setText("在途料件");
		}
		return flowButton_11;
	}
	private FlowButton getFlowButton_12() {
		if (flowButton_12 == null) {
			flowButton_12 = new FlowButton();
			flowButton_12.setBounds(10, 163, 142, 23);
			flowButton_12.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = (VFSection)tableModel.getCurrentRow();
					FmVFStockTravelingExg fm = new FmVFStockTravelingExg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_12.setText("在途成品");
		}
		return flowButton_12;
	}
	private FlowButton getFlowButton_18() {
		if (flowButton_18 == null) {
			flowButton_18 = new FlowButton();
			flowButton_18.setBounds(10, 187, 142, 23);
			flowButton_18.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = (VFSection)tableModel.getCurrentRow();
					FmVFStockSemiExgHadStore fm = new FmVFStockSemiExgHadStore();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_18.setText("半成品库存(已入库)");
		}
		return flowButton_18;
	}
	private FlowButton getFlowButton_28() {
		if (flowButton_28 == null) {
			flowButton_28 = new FlowButton();
			flowButton_28.setText("工厂库存汇总表");
			flowButton_28.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = (VFSection)tableModel.getCurrentRow();
					FmVFStockAnalyse fm = new FmVFStockAnalyse();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_28.setBounds(680, 210, 101, 23);
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
					VFSection section = (VFSection)tableModel.getCurrentRow();
					FmVFAnalyse fm = new FmVFAnalyse();
					fm.showData(section);
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
			flowButton_17.setBounds(10, 91, 110, 23);
			flowButton_17.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = (VFSection)tableModel.getCurrentRow();
					FmVFStockOutSendAnalyse fm = new FmVFStockOutSendAnalyse();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_17.setText("外发库存汇总");
		}
		return flowButton_17;
	}
	private FlowButton getFlowButton_14() {
		if (flowButton_14 == null) {
			flowButton_14 = new FlowButton();
			flowButton_14.setBounds(10, 19, 110, 23);
			flowButton_14.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = (VFSection)tableModel.getCurrentRow();
					FmVFStockOutSendImg fm = new FmVFStockOutSendImg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_14.setText("原材料");
		}
		return flowButton_14;
	}
	private FlowButton getFlowButton_15() {
		if (flowButton_15 == null) {
			flowButton_15 = new FlowButton();
			flowButton_15.setBounds(10, 43, 110, 23);
			flowButton_15.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = (VFSection)tableModel.getCurrentRow();
					FmVFStockOutSendSemiExg fm = new FmVFStockOutSendSemiExg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_15.setText("半成品");
		}
		return flowButton_15;
	}
	private FlowButton getFlowButton_16() {
		if (flowButton_16 == null) {
			flowButton_16 = new FlowButton();
			flowButton_16.setBounds(10, 67, 110, 23);
			flowButton_16.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = (VFSection)tableModel.getCurrentRow();
					FmVFStockOutSendExg fm = new FmVFStockOutSendExg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_16.setText("成品");
		}
		return flowButton_16;
	}
	private FlowLine getFlowLine_38() {
		if (flowLine == null) {
			flowLine = new FlowLine();
			flowLine.setText("");
			flowLine.setOnlyDrawLine(true);
			flowLine.setArrowDirection(1);
			flowLine.setBounds(109, 164, 619, 11);
		}
		return flowLine;
	}
	private FlowLine getFlowLine_2() {
		if (flowLine_2 == null) {
			flowLine_2 = new FlowLine();
			flowLine_2.setText("");
			flowLine_2.setArrowDirection(3);
			flowLine_2.setBounds(415, 153, 22, 16);
		}
		return flowLine_2;
	}
	private FlowButton getFlowButton_20() {
		if (flowButton_20 == null) {
			flowButton_20 = new FlowButton();
			flowButton_20.setBounds(10, 19, 114, 23);
			flowButton_20.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = (VFSection)tableModel.getCurrentRow();
					FmVFFinishingImg fm = new FmVFFinishingImg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_20.setText("原材料");
		}
		return flowButton_20;
	}
	private FlowButton getFlowButton_21() {
		if (flowButton_21 == null) {
			flowButton_21 = new FlowButton();
			flowButton_21.setBounds(10, 43, 114, 23);
			flowButton_21.setText("成品");
			flowButton_21.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = (VFSection)tableModel.getCurrentRow();
					FmVFFinishingExg fm = new FmVFFinishingExg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
		}
		return flowButton_21;
	}
	private FlowButton getFlowButton_22() {
		if (flowButton_22 == null) {
			flowButton_22 = new FlowButton();
			flowButton_22.setBounds(10, 67, 114, 23);
			flowButton_22.setText("在制品库存汇总");
			flowButton_22.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = (VFSection)tableModel.getCurrentRow();
					FmVFFinishingStock fm = new FmVFFinishingStock();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
		}
		return flowButton_22;
	}
	private FlowLine getFlowLine_3_1() {
		if (flowLine_3 == null) {
			flowLine_3 = new FlowLine();
			flowLine_3.setText("");
			flowLine_3.setOnlyDrawLine(true);
			flowLine_3.setArrowDirection(1);
			flowLine_3.setBounds(146, 68, 640, 11);
		}
		return flowLine_3;
	}
	private FlowButton getFlowButton_24() {
		if (flowButton_24 == null) {
			flowButton_24 = new FlowButton();
			flowButton_24.setBounds(10, 19, 114, 23);
			flowButton_24.setText("原材料");
			flowButton_24.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = (VFSection)tableModel.getCurrentRow();
					FmVFBadImg fm = new FmVFBadImg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
		}
		return flowButton_24;
	}
	private FlowButton getFlowButton_25() {
		if (flowButton_25 == null) {
			flowButton_25 = new FlowButton();
			flowButton_25.setBounds(10, 43, 114, 23);
			flowButton_25.setText("半成品");
			flowButton_25.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = (VFSection)tableModel.getCurrentRow();
					FmVFSemiBadExg fm = new FmVFSemiBadExg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
		}
		return flowButton_25;
	}
	private FlowButton getButton_32_1() {
		if (flowButton_26 == null) {
			flowButton_26 = new FlowButton();
			flowButton_26.setBounds(10, 67, 114, 23);
			flowButton_26.setText("成品");
			flowButton_26.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = (VFSection)tableModel.getCurrentRow();
					FmVFBadExg fm = new FmVFBadExg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
		}
		return flowButton_26;
	}
	private FlowButton getButton_33_1() {
		if (flowButton_27 == null) {
			flowButton_27 = new FlowButton();
			flowButton_27.setBounds(10, 91, 114, 23);
			flowButton_27.setText("残次品库存汇总");
			flowButton_27.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = (VFSection)tableModel.getCurrentRow();
					FmVFBadStockAnalyse fm = new FmVFBadStockAnalyse();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
		}
		return flowButton_27;
	}
	private FlowButton getButton_34_1() {
		if (flowButton_31 == null) {
			flowButton_31 = new FlowButton();
			flowButton_31.setText("结转汇总情况表");
			flowButton_31.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = (VFSection)tableModel.getCurrentRow();
					FmVFTransferDiffCount fm = new FmVFTransferDiffCount();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_31.setBounds(505, 423, 128, 23);
		}
		return flowButton_31;
	}
	private FlowButton getButton_35_1() {
		if (flowButton_29 == null) {
			flowButton_29 = new FlowButton();
			flowButton_29.setText("料件结转情况表");
			flowButton_29.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = (VFSection)tableModel.getCurrentRow();
					FmVFTransferDiffImg fm = new FmVFTransferDiffImg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_29.setBounds(229, 423, 128, 23);
		}
		return flowButton_29;
	}
	private FlowButton getFlowButton_30() {
		if (flowButton_30 == null) {
			flowButton_30 = new FlowButton();
			flowButton_30.setText("成品结转情况表");
			flowButton_30.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = (VFSection)tableModel.getCurrentRow();
					FmVFTransferDiffExg fm = new FmVFTransferDiffExg();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_30.setBounds(367, 423, 128, 23);
		}
		return flowButton_30;
	}
	private FlowLine getFlowLine_1_1() {
		if (flowLine_1 == null) {
			flowLine_1 = new FlowLine();
			flowLine_1.setText("");
			flowLine_1.setArrowDirection(3);
			flowLine_1.setBounds(98, 169, 22, 23);
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
			flowLine_19.setBounds(655, 73, 22, 22);
		}
		return flowLine_19;
	}
	private FlowLine getFlowLine_20() {
		if (flowLine_20 == null) {
			flowLine_20 = new FlowLine();
			flowLine_20.setText("");
			flowLine_20.setArrowDirection(3);
			flowLine_20.setBounds(415, 380, 22, 16);
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
			flowLine_24.setBounds(273, 73, 22, 22);
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
			flowLine_26.setBounds(524, 73, 22, 22);
		}
		return flowLine_26;
	}
	private FlowLine getFlowLine_28() {
		if (flowLine_28 == null) {
			flowLine_28 = new FlowLine();
			flowLine_28.setText("");
			flowLine_28.setArrowDirection(3);
			flowLine_28.setBounds(263, 169, 22, 23);
		}
		return flowLine_28;
	}
	private FlowLine getFlowLine_32_1() {
		if (flowLine_32 == null) {
			flowLine_32 = new FlowLine();
			flowLine_32.setText("");
			flowLine_32.setArrowDirection(3);
			flowLine_32.setBounds(415, 169, 22, 23);
		}
		return flowLine_32;
	}
	private FlowLine getFlowLine_33() {
		if (flowLine_33 == null) {
			flowLine_33 = new FlowLine();
			flowLine_33.setText("");
			flowLine_33.setArrowDirection(3);
			flowLine_33.setBounds(573, 169, 22, 23);
		}
		return flowLine_33;
	}
	private FlowLine getFlowLine_34() {
		if (flowLine_34 == null) {
			flowLine_34 = new FlowLine();
			flowLine_34.setText("");
			flowLine_34.setArrowDirection(3);
			flowLine_34.setBounds(716, 169, 22, 31);
		}
		return flowLine_34;
	}
	private FlowLine getFlowLine_37() {
		if (flowLine_37 == null) {
			flowLine_37 = new FlowLine();
			flowLine_37.setText("");
			flowLine_37.setArrowDirection(3);
			flowLine_37.setBounds(415, 403, 22, 16);
		}
		return flowLine_37;
	}
	
	public void setColor(VFSection section){
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
		button[13] = this.flowButton_14;
		button[14] = this.flowButton_15;
		button[15] = this.flowButton_16;
		button[16] = this.flowButton_17;
		button[17] = this.flowButton_18;
		button[19] = this.flowButton_20;
		button[20] = this.flowButton_21;
		button[21] = this.flowButton_22;
		button[23] = this.flowButton_24;
		button[24] = this.flowButton_25;
		button[25] = this.flowButton_26;
		button[26] = this.flowButton_27;
		button[27] = this.flowButton_28;
		button[28] = this.flowButton_29;
		button[29] = this.flowButton_30;
		button[30] = this.flowButton_31;
		button[31] = this.flowButton_32;
		button[32] = this.flowButton_35;
		List<Integer> prcess = vfVerificationAction.findVFSectionByProcess(request,section);
		for (int i = 0; i < button.length; i++) {
			if(button[i]!=null){
				button[i].setBottomFilledColor(new Color(240,240,240));
			}
		}
		for (int i = 0; i < prcess.size(); i++) {
			if(prcess.get(i)!=null&&button[prcess.get(i)]!=null){
				button[prcess.get(i)].setBottomFilledColor(new Color(100,220,100));
			}
		}
	}
	private FlowButton getFlowButton() {
		if (flowButton == null) {
			flowButton = new FlowButton();
			flowButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = (VFSection)tableModel.getCurrentRow();
					FmVFAttachment fm = new FmVFAttachment();
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
			flowButton_33.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
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
	private FlowButton getFlowButton_35() {
		if (flowButton_35 == null) {
			flowButton_35 = new FlowButton();
			flowButton_35.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = (VFSection)tableModel.getCurrentRow();
					FmVFCustomsCountExgConvert fm = new FmVFCustomsCountExgConvert();
					fm.showData(section,null);
					ShowFormControl.refreshInteralForm(fm);
				}
			});
			flowButton_35.setText("成品折算统计表");
			flowButton_35.setBounds(600, 97, 128, 23);
		}
		return flowButton_35;
	}
	private FlowLine getFlowLine_8() {
		if (flowLine_8 == null) {
			flowLine_8 = new FlowLine();
			flowLine_8.setText("");
			flowLine_8.setArrowDirection(3);
			flowLine_8.setBounds(775, 73, 22, 22);
		}
		return flowLine_8;
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
	            VFSection section = (VFSection) tableModel.getCurrentRow();
	            if (section != null) {
	                setColor(section);
	            }
	        }
	}
	private JPanel getPanel_3() {
		if (panel_3 == null) {
			panel_3 = new JPanel();
			panel_3.setForeground(new Color(0, 0, 0));
			panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u4ED3\u5E93\u5E93\u5B58", TitledBorder.CENTER, TitledBorder.TOP, new java.awt.Font("新宋体", java.awt.Font.BOLD, 12), new Color(0, 101, 255)));
			panel_3.setBounds(30, 191, 159, 222);
			panel_3.setLayout(null);
			panel_3.add(getFlowButton_6());
			panel_3.add(getFlowButton_7());
			panel_3.add(getFlowButton_8());
			panel_3.add(getFlowButton_9());
			panel_3.add(getFlowButton_10());
			panel_3.add(getFlowButton_11());
			panel_3.add(getFlowButton_12());
			panel_3.add(getFlowButton_18());
		}
		return panel_3;
	}
	private JPanel getPanel_4() {
		if (panel_4 == null) {
			panel_4 = new JPanel();
			panel_4.setBorder(new TitledBorder(null, "\u5916\u53D1\u5E93\u5B58", TitledBorder.CENTER, TitledBorder.TOP, new java.awt.Font("新宋体", java.awt.Font.BOLD, 12), new Color(0, 101, 255)));
			panel_4.setBounds(210, 191, 128, 122);
			panel_4.setLayout(null);
			panel_4.add(getFlowButton_14());
			panel_4.add(getFlowButton_15());
			panel_4.add(getFlowButton_16());
			panel_4.add(getFlowButton_17());
		}
		return panel_4;
	}
	private JPanel getPanel_5() {
		if (panel_5 == null) {
			panel_5 = new JPanel();
			panel_5.setBorder(new TitledBorder(null, "\u5728\u5236\u54C1\u5E93\u5B58", TitledBorder.CENTER, TitledBorder.TOP, new java.awt.Font("新宋体", java.awt.Font.BOLD, 12), new Color(0, 101, 255)));
			panel_5.setBounds(361, 191, 132, 97);
			panel_5.setLayout(null);
			panel_5.add(getFlowButton_20());
			panel_5.add(getFlowButton_21());
			panel_5.add(getFlowButton_22());
		}
		return panel_5;
	}
	private JPanel getPanel_6() {
		if (panel_6 == null) {
			panel_6 = new JPanel();
			panel_6.setBorder(new TitledBorder(null, "\u6B8B\u6B21\u54C1\u5E93\u5B58", TitledBorder.CENTER, TitledBorder.TOP, new java.awt.Font("新宋体", java.awt.Font.BOLD, 12), new Color(0, 101, 255)));
			panel_6.setBounds(520, 191, 132, 122);
			panel_6.setLayout(null);
			panel_6.add(getFlowButton_24());
			panel_6.add(getFlowButton_25());
			panel_6.add(getButton_32_1());
			panel_6.add(getButton_33_1());
		}
		return panel_6;
	}
}
