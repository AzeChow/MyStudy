package com.bestway.bcus.client.checkcancel;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.entity.FactoryCheckExg;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgFactoryCheckExg extends JDialogBase {
	private JPanel panel;
	private JLabel lblNewLabel;
	private JTextField tfPtNo;
	private JLabel label;
	private JTextField tfName;
	private JLabel label_1;
	private JTextField tfSpec;
	private JLabel label_2;
	private JTextField tfCalUnitName;
	private JLabel label_3;
	private JCustomFormattedTextField tfMaterStockNum;
	private JLabel label_4;
	private JTextField tfUnitName;
	private JLabel label_5;
	private JCustomFormattedTextField tfBgNum;
	private JToolBar toolBar;
	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnUp;
	private JButton btnDown;

	private int dataState = DataState.BROWSE;
	private JTableListModel tableModel;
	private CheckCancelAction checkCancelAction = null;
	private JLabel label_6;
	private JTextField tfVersion;

	public DgFactoryCheckExg(JTableListModel tableModel) {
		this.setTitle("工厂盘点成品");
		this.tableModel = tableModel;
		checkCancelAction = (CheckCancelAction) CommonVars
				.getApplicationContext().getBean("checkCancelAction");
		this.setBounds(new Rectangle(580, 300));
		getContentPane().add(getPanel(), BorderLayout.CENTER);
		getContentPane().add(getToolBar(), BorderLayout.NORTH);
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(null);
			panel.add(getLblNewLabel());
			panel.add(getTfPtNo());
			panel.add(getLabel());
			panel.add(getTfName());
			panel.add(getLabel_1());
			panel.add(getTfSpec());
			panel.add(getLabel_2());
			panel.add(getTfCalUnitName());
			panel.add(getLabel_3());
			panel.add(getTfMaterStockNum());
			panel.add(getLabel_4());
			panel.add(getTfUnitName());
			panel.add(getLabel_5());
			panel.add(getTfBgNum());
			panel.add(getLabel_6());
			panel.add(getTfVersion());
		}
		return panel;
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("料号");
			lblNewLabel.setBounds(10, 23, 108, 15);
		}
		return lblNewLabel;
	}

	private JTextField getTfPtNo() {
		if (tfPtNo == null) {
			tfPtNo = new JTextField();
			tfPtNo.setBounds(128, 23, 150, 21);
			tfPtNo.setColumns(10);
			tfPtNo.setEditable(false);
		}
		return tfPtNo;
	}

	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("料件名称");
			label.setBounds(304, 26, 76, 15);
		}
		return label;
	}

	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setColumns(10);
			tfName.setBounds(390, 23, 150, 21);
			tfName.setEditable(false);
		}
		return tfName;
	}

	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("规格型号");
			label_1.setBounds(10, 59, 108, 15);
		}
		return label_1;
	}

	private JTextField getTfSpec() {
		if (tfSpec == null) {
			tfSpec = new JTextField();
			tfSpec.setColumns(10);
			tfSpec.setBounds(128, 59, 150, 21);
			tfSpec.setEditable(false);
		}
		return tfSpec;
	}

	private JLabel getLabel_2() {
		if (label_2 == null) {
			label_2 = new JLabel("工厂计量单位");
			label_2.setBounds(304, 62, 76, 15);
		}
		return label_2;
	}

	private JTextField getTfCalUnitName() {
		if (tfCalUnitName == null) {
			tfCalUnitName = new JTextField();
			tfCalUnitName.setColumns(10);
			tfCalUnitName.setBounds(390, 59, 150, 21);
			tfCalUnitName.setEditable(false);
		}
		return tfCalUnitName;
	}

	private JLabel getLabel_3() {
		if (label_3 == null) {
			label_3 = new JLabel("原料库存数量");
			label_3.setBounds(10, 93, 108, 15);
		}
		return label_3;
	}

	private JCustomFormattedTextField getTfMaterStockNum() {
		if (tfMaterStockNum == null) {
			tfMaterStockNum = new JCustomFormattedTextField();
			tfMaterStockNum.setColumns(10);
			tfMaterStockNum.setBounds(128, 93, 150, 21);
			tfMaterStockNum.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfMaterStockNum;
	}

	private JLabel getLabel_4() {
		if (label_4 == null) {
			label_4 = new JLabel("海关单位");
			label_4.setBounds(304, 96, 76, 15);
		}
		return label_4;
	}

	private JTextField getTfUnitName() {
		if (tfUnitName == null) {
			tfUnitName = new JTextField();
			tfUnitName.setColumns(10);
			tfUnitName.setBounds(390, 93, 150, 21);
			tfUnitName.setEditable(false);
		}
		return tfUnitName;
	}

	private JLabel getLabel_5() {
		if (label_5 == null) {
			label_5 = new JLabel("海关折算库存数量");
			label_5.setBounds(10, 127, 108, 15);
		}
		return label_5;
	}

	private JCustomFormattedTextField getTfBgNum() {
		if (tfBgNum == null) {
			tfBgNum = new JCustomFormattedTextField();
			tfBgNum.setColumns(10);
			tfBgNum.setBounds(128, 127, 150, 21);
		}
		return tfBgNum;
	}

	private JToolBar getToolBar() {
		if (toolBar == null) {
			toolBar = new JToolBar();
			toolBar.add(getBtnEdit());
			toolBar.add(getBtnSave());
			toolBar.add(getBtnUp());
			toolBar.add(getBtnDown());
		}
		return toolBar;
	}

	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton("修改");
			btnEdit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnEdit;
	}

	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton("保存");
			btnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					FactoryCheckExg img = (FactoryCheckExg) tableModel
							.getCurrentRow();
					if (tfBgNum.getValue() != null) {
						img.setBgNum(Double.valueOf(tfBgNum.getValue()
								.toString()));
					}

					if (tfMaterStockNum.getValue() != null) {
						img.setMaterStockNum(Double.valueOf(tfMaterStockNum
								.getValue().toString()));
					}
					img = checkCancelAction.saveFactoryCheckExg(new Request(
							CommonVars.getCurrUser()), img);
					tableModel.updateRow(img);
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnSave;
	}

	private JButton getBtnUp() {
		if (btnUp == null) {
			btnUp = new JButton("上笔");
			btnUp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					FactoryCheckExg img = (FactoryCheckExg) tableModel
							.getDataByRow(tableModel.getTable()
									.getSelectedRow() - 1);
					dataState = DataState.EDIT;
					tableModel.setSelectRow(tableModel.getTable()
							.getSelectedRow() - 1);
					fillData(img);
					setState();
				}
			});
		}
		return btnUp;
	}

	private JButton getBtnDown() {
		if (btnDown == null) {
			btnDown = new JButton("下笔");
			btnDown.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					FactoryCheckExg img = (FactoryCheckExg) tableModel
							.getDataByRow(tableModel.getTable()
									.getSelectedRow() + 1);
					tableModel.setSelectRow(tableModel.getTable()
							.getSelectedRow() + 1);
					dataState = DataState.EDIT;
					fillData(img);
					setState();
				}
			});
		}
		return btnDown;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	@Override
	public void setVisible(boolean b) {
		if (b && tableModel != null && tableModel.getCurrentRow() != null) {
			FactoryCheckExg img = (FactoryCheckExg) tableModel.getCurrentRow();
			fillData(img);
			setState();
		}
		super.setVisible(b);
	}

	private void fillData(FactoryCheckExg img) {
		if (img == null) {
			return;
		}
		tfPtNo.setText(img.getPtNo());
		tfName.setText(img.getName());
		tfSpec.setText(img.getSpec());
		tfCalUnitName.setText(img.getCalUnit() == null ? "" : img.getCalUnit()
				.getName());
		tfMaterStockNum.setValue(img.getMaterStockNum());
		tfUnitName
				.setText(img.getUnit() == null ? "" : img.getUnit().getName());
		tfBgNum.setValue(img.getBgNum());
	}

	private void setState() {
		tfMaterStockNum.setEditable(DataState.BROWSE != dataState);
		tfBgNum.setEditable(DataState.BROWSE != dataState);

		if (tableModel.getTable().getSelectedRow() > 0) {
			btnUp.setEnabled(true);
		} else {
			btnUp.setEnabled(false);
		}
		if (tableModel.getTable().getSelectedRow() < tableModel.getRowCount() - 1) {
			btnDown.setEnabled(true);
		} else {
			btnDown.setEnabled(false);
		}
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	protected DefaultFormatterFactory getDefaultFormatterFactory() {
		DefaultFormatterFactory defaultFormatterFactory = new DefaultFormatterFactory();
		defaultFormatterFactory.setDisplayFormatter(new NumberFormatter());
		defaultFormatterFactory.setEditFormatter(new NumberFormatter());
		return defaultFormatterFactory;
	}

	private JLabel getLabel_6() {
		if (label_6 == null) {
			label_6 = new JLabel("版本号");
			label_6.setBounds(304, 127, 76, 15);
		}
		return label_6;
	}

	private JTextField getTfVersion() {
		if (tfVersion == null) {
			tfVersion = new JTextField();
			tfVersion.setEditable(false);
			tfVersion.setColumns(10);
			tfVersion.setBounds(390, 124, 150, 21);
		}
		return tfVersion;
	}
}
