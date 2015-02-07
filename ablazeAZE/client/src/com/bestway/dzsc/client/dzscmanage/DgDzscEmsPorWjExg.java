/*
 * Created on 2005-3-30
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.dzscmanage;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgWj;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgWj;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;

/**
 * @author
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDzscEmsPorWjExg extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel7 = null;

	private JLabel jLabel8 = null;

	private JLabel jLabel11 = null;

	private JLabel jLabel12 = null;

	private JTextField tfFourName = null;

	private JTextField tfFourSpec = null;

	private JComboBox cbbFirstUnit = null;

	private JComboBox cbbSecondUnit = null;

	private JTextField tfFourSeqNum = null;

	private JButton btnOK = null;

	private JButton btnClose = null;

	private DzscAction dzscAction = null;

	private JTableListModel tableModel = null;

	private DzscEmsExgWj exg = null;

	private JLabel jLabel14 = null;

	private NumberFormatter numberFormatter = null;

	private JLabel jLabel1 = null;

	private JTextField tfComplex = null;

	private JButton btnComplex = null;

	private JComboBox cbbUnit = null;

	private JButton btnPrevious = null;

	private JButton btnNext = null;

	private JLabel jLabel121 = null;

	private JTextField tfSeqNum = null;

	/**
	 * This is the default constructor
	 */
	public DgDzscEmsPorWjExg() {
		super();
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(403, 300);
		this.setContentPane(getJContentPane());
		this.setTitle("成品修改");
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {

			}
		});
	}

	public void setVisible(boolean b) {
		if (b) {
			if (tableModel != null && tableModel.getCurrentRow() != null) {
				exg = (DzscEmsExgWj) tableModel.getCurrentRow();
				initUI();
				showData();
			}
		}
		super.setVisible(b);
	}

	// 填充动态ComboBox
	public NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
		}
		return numberFormatter;
	}

	private void initUI() {
		List list = null;
		// 申报单位
		list = CustomBaseList.getInstance().getUnits();
		DzscClientLogic.intoComboBox(list, cbbUnit);
		// 第一法定单位
		list = CustomBaseList.getInstance().getUnits();
		DzscClientLogic.intoComboBox(list, cbbFirstUnit);
		// 第二法定单位
		list = CustomBaseList.getInstance().getUnits();
		DzscClientLogic.intoComboBox(list, cbbSecondUnit);
	}

	private void showData() {
		exg = (DzscEmsExgWj) tableModel.getCurrentRow();
		this.tfSeqNum.setText(String.valueOf(exg.getSeqNum()));
		tfFourSeqNum.setText(String.valueOf(exg.getFourSeqNum()));
		this.tfComplex.setText(exg.getFourComplex());
		tfFourName.setText(exg.getFourName());
		tfFourSpec.setText(exg.getFourSpec());
		this.cbbUnit.setSelectedItem(exg.getFourUnit());
		this.cbbFirstUnit.setSelectedItem(exg.getFirstUnit());
		this.cbbSecondUnit.setSelectedItem(exg.getSecondUnit());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel121 = new JLabel();
			jLabel121.setBounds(new Rectangle(42, 21, 61, 22));
			jLabel121.setText("备案序号");
			jLabel121.setForeground(Color.blue);
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(42, 50, 54, 22));
			jLabel1.setForeground(Color.blue);
			jLabel1.setText("商品编码");
			jLabel14 = new JLabel();
			jLabel12 = new JLabel();
			jLabel11 = new JLabel();
			jLabel8 = new JLabel();
			jLabel7 = new JLabel();
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel2.setBounds(42, 78, 62, 21);
			jLabel2.setText("商品名称");
			jLabel2.setForeground(Color.blue);
			jLabel3.setBounds(42, 105, 57, 21);
			jLabel3.setText("型号规格");
			jLabel7.setBounds(178, 136, 79, 22);
			jLabel7.setText("第一法定单位");
			jLabel7.setForeground(Color.blue);
			jLabel8.setBounds(42, 135, 31, 20);
			jLabel8.setForeground(Color.blue);
			jLabel8.setText("单位");
			jLabel11.setBounds(42, 168, 76, 23);
			jLabel11.setText("第二法定单位");
			jLabel11.setForeground(Color.black);
			jLabel12.setBounds(216, 21, 53, 22);
//			jLabel12.setForeground(Color.blue);
			jLabel12.setText("归并序号");
			jLabel14.setBounds(196, 194, 131, 20);
			jLabel14.setText("(注：蓝色字体为必填项)");
			jLabel14.setForeground(Color.blue);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(jLabel8, null);
			jContentPane.add(jLabel11, null);
			jContentPane.add(jLabel12, null);
			jContentPane.add(getTfFourName(), null);
			jContentPane.add(getTfFourSpec(), null);
			jContentPane.add(getCbbFirstUnit(), null);
			jContentPane.add(getCbbSecondUnit(), null);
			jContentPane.add(getTfFourSeqNum(), null);
			jContentPane.add(getBtnOK(), null);
			jContentPane.add(getBtnClose(), null);
			jContentPane.add(jLabel14, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getTfComplex(), null);
			jContentPane.add(getBtnComplex(), null);
			jContentPane.add(getCbbUnit(), null);
			jContentPane.add(getBtnPrevious(), null);
			jContentPane.add(getBtnNext(), null);
			jContentPane.add(jLabel121, null);
			jContentPane.add(getTfSeqNum(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFourName() {
		if (tfFourName == null) {
			tfFourName = new JTextField();
			tfFourName.setEditable(true);
			tfFourName.setBounds(103, 79, 253, 22);
		}
		return tfFourName;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFourSpec() {
		if (tfFourSpec == null) {
			tfFourSpec = new JTextField();
			tfFourSpec.setEditable(true);
			tfFourSpec.setBounds(103, 106, 253, 22);
		}
		return tfFourSpec;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbFirstUnit() {
		if (cbbFirstUnit == null) {
			cbbFirstUnit = new JComboBox();
			cbbFirstUnit.setBounds(257, 137, 100, 23);
		}
		return cbbFirstUnit;
	}

	/**
	 * This method initializes jComboBox3
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbSecondUnit() {
		if (cbbSecondUnit == null) {
			cbbSecondUnit = new JComboBox();
			cbbSecondUnit.setBounds(118, 168, 114, 22);
		}
		return cbbSecondUnit;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFourSeqNum() {
		if (tfFourSeqNum == null) {
			tfFourSeqNum = new JTextField();
			tfFourSeqNum.setEditable(false);
			tfFourSeqNum.setBounds(269, 21, 85, 22);
		}
		return tfFourSeqNum;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setBounds(45, 222, 65, 25);
			btnOK.setText("确定");
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (isEmpty()) {
						return;
					}
					fillData(exg);
					exg = dzscAction.saveDzscEmsExgWj(new Request(CommonVars
							.getCurrUser()), exg);
					tableModel.updateRow(exg);
					DgDzscEmsPorWjExg.this.dispose();
				}
			});
		}
		return btnOK;
	}

	private boolean isEmpty() {
		if (tfFourName.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "商品名称不能为空！", "提示", 2);
			return true;
		}
		if (cbbUnit.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "申报单位不能为空！", "提示", 2);
			return true;
		}
		if (cbbFirstUnit.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "第一法定单位不能为空！", "提示", 2);
			return true;
		}
		return false;
	}

	private void fillData(DzscEmsExgWj exg) {
		exg.setFourComplex(this.tfComplex.getText().trim());
		exg.setFourName(this.tfFourName.getText().trim());
		exg.setFourSpec(this.tfFourSpec.getText().trim());
		exg.setFourUnit((Unit) this.cbbUnit.getSelectedItem());
		exg.setFirstUnit((Unit) this.cbbFirstUnit.getSelectedItem());
		exg.setSecondUnit((Unit) this.cbbSecondUnit.getSelectedItem());
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setBounds(285, 222, 65, 25);
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgDzscEmsPorWjExg.this.dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfComplex() {
		if (tfComplex == null) {
			tfComplex = new JTextField();
			tfComplex.setBounds(new Rectangle(103, 51, 231, 22));
		}
		return tfComplex;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnComplex() {
		if (btnComplex == null) {
			btnComplex = new JButton();
			btnComplex.setBounds(new Rectangle(333, 51, 22, 21));
			btnComplex.setText("...");
			btnComplex.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Complex complex = (Complex) CommonQuery.getInstance()
							.getComplex();
					if (complex != null) {
						tfComplex.setText(complex.getCode());
						cbbFirstUnit.setSelectedItem(complex.getFirstUnit());
						cbbSecondUnit.setSelectedItem(complex.getSecondUnit());
						DzscEmsExgWj exg = (DzscEmsExgWj) tableModel
								.getCurrentRow();
						String modifyMark = exg.getModifyMark();
						if (ModifyMarkState.ADDED.equals(modifyMark)) {
							tfFourName.setText(complex.getName());
						}
					}
				}
			});
		}
		return btnComplex;
	}

	/**
	 * This method initializes jComboBox11
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbUnit() {
		if (cbbUnit == null) {
			cbbUnit = new JComboBox();
			cbbUnit.setBounds(new Rectangle(76, 135, 100, 22));
		}
		return cbbUnit;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setBounds(new Rectangle(128, 222, 65, 25));
			btnPrevious.setText("上笔");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fillData(exg);
					exg = dzscAction.saveDzscEmsExgWj(new Request(CommonVars
							.getCurrUser()), exg);
					tableModel.updateRow(exg);
					if (!tableModel.previousRow()) {
						btnPrevious.setEnabled(false);
						btnNext.setEnabled(true);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}
					showData();
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
			btnNext.setBounds(new Rectangle(202, 222, 65, 25));
			btnNext.setText("下笔");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fillData(exg);
					exg = dzscAction.saveDzscEmsExgWj(new Request(CommonVars
							.getCurrUser()), exg);
					tableModel.updateRow(exg);
					if (!tableModel.nextRow()) {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(false);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}
					showData();
				}
			});
		}
		return btnNext;
	}

	/**
	 * This method initializes tfFourSeqNum1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfSeqNum() {
		if (tfSeqNum == null) {
			tfSeqNum = new JTextField();
			tfSeqNum.setBounds(new Rectangle(105, 21, 103, 22));
			tfSeqNum.setEditable(false);
		}
		return tfSeqNum;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
