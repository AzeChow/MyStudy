/*
 * Created on 2004-6-29
 *
 * //lm 于2008-9-9 修改
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.erpbillcenter;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.BillType;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.SBillType;
import com.bestway.common.constant.WareType;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgBillType extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JPanel pn = null;

	private JTextField tfTypeCode  = null;

	private JTextField tfTypeName = null;

	private JComboBox cbbSort = null;

	private JComboBox cbbInAndOutWarehouse = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private BillType billType = null; // 表头

	private boolean isAdd = true;

	private JTableListModel tableModel = null;

	private CasAction casAction = null;

	private int beginType = 1;//起始类型

	private JPanel pn1 = null;

	private JCheckBox cbIsTransferBill = null;

	private JCheckBox cbIsCustomsDeclarationCorresponding = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel = null;

	private JTextField tfFactoryName = null;

	private JLabel jLabel1 = null;

	/**
	 * This is the default constructor
	 */
	public DgBillType() {
		super();

		initialize();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(451, 281);
		this.setTitle("单据类型");
		this.setContentPane(getJContentPane());
	}

	/**
	 * 初始化组键及其状态
	 */
	@Override
	public void setVisible(boolean isFlag) {
		if (isFlag) {
			if (isAdd == false) {
				if (tableModel.getCurrentRow() != null) {
					billType = (BillType) tableModel.getCurrentRow();
				}
				initUI(); // 初始化控件
				initUIData();// 初始化数据
			} else {
				initUI(); // 初始化控件
			}
		}
		super.setVisible(isFlag);
	}

	/**
	 * 初始化数据
	 */
	private void initUIData() {
		if (this.billType.getBillType() != null) {
			cbbSort.setSelectedIndex(ItemProperty.getIndexByCode(String
					.valueOf(this.billType.getBillType()), cbbSort));
		} else {
			cbbSort.setSelectedIndex(-1);
		}

		if (this.billType.getWareType() != null) {
			cbbInAndOutWarehouse.setSelectedIndex(ItemProperty.getIndexByCode(String
					.valueOf(this.billType.getWareType()), cbbSort));
		} else {
			cbbInAndOutWarehouse.setSelectedIndex(-1);
		}

		tfTypeCode.setText(billType.getCode());
		tfTypeName.setText(billType.getName());
		cbIsTransferBill
				.setSelected(billType.getIsTransferBill() == null ? false
						: billType.getIsTransferBill());
		cbIsCustomsDeclarationCorresponding.setSelected(billType
				.getIsCustomsDeclarationCorresponding() == null ? false
				: billType.getIsCustomsDeclarationCorresponding());
		tfFactoryName.setText(billType.getFactoryBillName());
		//
		// 设置都不能修改
		//
		cbbSort.setEnabled(false);
		tfTypeCode.setEditable(false);
		cbbInAndOutWarehouse.setEnabled(false);
		tfTypeName.setEditable(false);

	}

	/**
	 * This method initializes UI
	 * 
	 * @return
	 */
	private void initUI() {
		this.cbbSort.removeAllItems();
		this.cbbSort.addItem(new ItemProperty(String
				.valueOf(SBillType.MATERIEL_IN), "料件入库"));
		this.cbbSort.addItem(new ItemProperty(String
				.valueOf(SBillType.MATERIEL_OUT), "料件出库"));
		this.cbbSort.addItem(new ItemProperty(String
				.valueOf(SBillType.PRODUCE_IN), "成品入库"));
		this.cbbSort.addItem(new ItemProperty(String
				.valueOf(SBillType.PRODUCE_OUT), "成品出库"));
		this.cbbSort.addItem(new ItemProperty(String
				.valueOf(SBillType.FIXTURE_IN), "设备入库"));
		this.cbbSort.addItem(new ItemProperty(String
				.valueOf(SBillType.FIXTURE_OUT), "设备出库"));
		this.cbbSort.addItem(new ItemProperty(String
				.valueOf(SBillType.HALF_PRODUCE_IN), "半成品入库"));
		this.cbbSort.addItem(new ItemProperty(String
				.valueOf(SBillType.HALF_PRODUCE_OUT), "半成品出库"));
		this.cbbSort.addItem(new ItemProperty(String
				.valueOf(SBillType.REMAIN_PRODUCE_IN), "残次品入库"));
		this.cbbSort.addItem(new ItemProperty(String
				.valueOf(SBillType.REMAIN_PRODUCE_OUT), "残次品出库"));
		this.cbbSort.addItem(new ItemProperty(String
				.valueOf(SBillType.LEFTOVER_MATERIEL_IN), "边角料入库"));
		this.cbbSort.addItem(new ItemProperty(String
				.valueOf(SBillType.LEFTOVER_MATERIEL_OUT), "边角料出库"));
		this.cbbSort.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbSort);
		cbbSort.setSelectedIndex(DgBillType.this.beginType);

		this.cbbInAndOutWarehouse.removeAllItems();
		this.cbbInAndOutWarehouse.addItem(new ItemProperty(String
				.valueOf(WareType.WARE_IN), "进仓"));
		this.cbbInAndOutWarehouse.addItem(new ItemProperty(String
				.valueOf(WareType.WARE_OUT), "出仓"));
		this.cbbInAndOutWarehouse
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbInAndOutWarehouse);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */

	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJPanel(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jPanel
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel() {
		if (pn == null) {
			pn = new JPanel();
			pn.setLayout(null);
			pn.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0,
					0, 0));
			pn.add(getBtnOk(), null);
			pn.add(getBtncancel(), null);
			pn.add(getPn1(), null);
		}
		return pn;
	}

	/**
	 * 
	 * This method initializes tfptNo
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfTypeCode() {
		if (tfTypeCode == null) {
			tfTypeCode = new JTextField();
			tfTypeCode.setBounds(new java.awt.Rectangle(262, 21, 133, 23));
		}
		return tfTypeCode;
	}

	/**
	 * 
	 * This method initializes tfptName
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfTypeName() {
		if (tfTypeName == null) {
			tfTypeName = new JTextField();
			tfTypeName.setBounds(new java.awt.Rectangle(57, 53, 133, 23));
		}
		return tfTypeName;
	}

	/**
	 * 
	 * This method initializes cftaxation
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 * 
	 */
	private JComboBox getCbbSort() {
		if (cbbSort == null) {
			cbbSort = new JComboBox();
			cbbSort.setBounds(new java.awt.Rectangle(57, 21, 133, 23));
		}
		return cbbSort;
	}

	/**
	 * 
	 * This method initializes cfproBonded
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 * 
	 */
	private JComboBox getCbbInAndOutWarehouse() {
		if (cbbInAndOutWarehouse == null) {
			cbbInAndOutWarehouse = new JComboBox();
			cbbInAndOutWarehouse.setBounds(new java.awt.Rectangle(262, 53, 133, 23));
		}
		return cbbInAndOutWarehouse;
	}

	/**
	 * 检查是否为空
	 * 
	 */
	private boolean checkNull() {
		if (this.tfTypeCode.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "代码不能为空！", "提示！", 2);
			return true;
		}
		if (this.tfTypeName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "名称不能为空！", "提示！", 2);
			return true;
		}
		return false;
	}

	/**
	 * 保存单据
	 * 
	 * @param billType
	 */

	public void fillData(BillType billType) {
		if (this.cbbSort.getSelectedItem() != null) {
			billType.setBillType(Integer
					.valueOf(((ItemProperty) this.cbbSort.getSelectedItem())
							.getCode()));
		}

		if (this.cbbInAndOutWarehouse.getSelectedItem() != null) {
			billType
					.setWareType(Integer
							.valueOf(((ItemProperty) this.cbbInAndOutWarehouse
									.getSelectedItem()).getCode()));
		}
		billType.setCode(tfTypeCode.getText());
		billType.setName(tfTypeName.getText());

		// billType.setCompany(CommonVars.getCurrUser().getCompany());
		String yy = ((ItemProperty) this.cbbSort.getSelectedItem())
				.getCode();
		if (yy.equals("1") || yy.equals("2")) {
			billType.setProduceType(Integer.valueOf(MaterielType.MATERIEL));
		} else if (yy.equals("3") || yy.equals("4")) {
			billType.setProduceType(Integer
					.valueOf(MaterielType.FINISHED_PRODUCT));
		} else if (yy.equals("5") || yy.equals("6")) {
			billType.setProduceType(Integer.valueOf(MaterielType.MACHINE));
		} else if (yy.equals("7") || yy.equals("8")) {
			billType.setProduceType(Integer
					.valueOf(MaterielType.SEMI_FINISHED_PRODUCT));
		} else if (yy.equals("9") || yy.equals("10")) {
			billType.setProduceType(Integer.valueOf(MaterielType.BAD_PRODUCT));
		} else if (yy.equals("11") || yy.equals("12")) {
			billType.setProduceType(Integer
					.valueOf(MaterielType.REMAIN_MATERIEL));
		}
		billType.setFactoryBillName(this.tfFactoryName.getText());
		billType.setIsTransferBill(cbIsTransferBill.isSelected());
		billType
				.setIsCustomsDeclarationCorresponding(cbIsCustomsDeclarationCorresponding
						.isSelected());

	}

	/**
	 * 清空数据
	 */

	private void clearData() {
		tfTypeCode.setText("");
		tfTypeName.setText("");
	}

	/**
	 * 检查单据类型
	 */

	private boolean checkMultiple() {
		BillType code = casAction.findBillTypeByCode(new Request(CommonVars
				.getCurrUser()), this.tfTypeCode.getText().trim());

		if (code != null) {
			if (!isAdd) {
				if (!code.getId().equals(billType.getId())) {
					JOptionPane.showMessageDialog(this, "代码不能重复,请重新输入!", "提示!",
							2);
					return true;
				}
			} else {
				JOptionPane.showMessageDialog(this, "代码不能重复,请重新输入!", "提示!", 2);
				return true;
			}
		}
		List<BillType> list = casAction.findBillTypeByFactoryName(new Request(
				CommonVars.getCurrUser()), this.tfFactoryName.getText());
		if (list.size() > 0) {
			for (BillType e : list) {
				if (!isAdd) {
					if (!e.getId().equals(billType.getId())) {
						JOptionPane.showMessageDialog(this, "工厂名称已存在,请重新输入!",
								"提示!", 2);
						return true;
					}
				} else {
					JOptionPane.showMessageDialog(this, "工厂名称已存在,请重新输入!",
							"提示!", 2);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * This method initializes btnOk
	 * 
	 * @return javax.swing.JButton
	 */

	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setText("确定");
			btnOk.setBounds(282, 213, 68, 26);
			btnOk.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (checkNull()) {
						return;
					}
					if (checkMultiple()) {
						return;
					}
					if (isAdd == true) {
						BillType billType = new BillType();
						fillData(billType);
						billType = casAction.saveBillType(new Request(
								CommonVars.getCurrUser()), billType);
						tableModel.addRow(billType);
						clearData();
					} else {
						fillData(billType);
						billType = casAction.saveBillType(new Request(
								CommonVars.getCurrUser()), billType);
						tableModel.updateRow(billType);
						DgBillType.this.dispose();
					}
				}
			});

		}
		return btnOk;
	}

	/**
	 * 
	 * This method initializes btncancel
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtncancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.setBounds(356, 213, 68, 26);
			btnCancel.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgBillType.this.dispose();

				}
			});

		}
		return btnCancel;
	}

	/**
	 * @return Returns the isAdd.
	 */
	public boolean isAdd() {
		return isAdd;
	}

	/**
	 * @param isAdd
	 *            The isAdd to set.
	 */
	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
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
	 * @return Returns the beginType.
	 */
	public int getBeginType() {
		return beginType;
	}

	/**
	 * @param beginType
	 *            The beginType to set.
	 */
	public void setBeginType(int beginType) {
		this.beginType = beginType;
	}

	/**
	 * This method initializes pnTest
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnTest() {
		return getPn1();
	}

	/**
	 * This method initializes pn1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn1() {
		if (pn1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setForeground(Color.blue);
			jLabel1.setBounds(new Rectangle(201, 81, 193, 23));
			jLabel1.setText("注：工厂名称是唯一的，不能够重复");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(7, 81, 51, 23));
			jLabel.setText("工厂名称");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new java.awt.Rectangle(7, 53, 51, 23));
			jLabel6.setText("类型名称");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(7, 21, 46, 23));
			jLabel5.setText("类别");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(201, 53, 55, 23));
			jLabel4.setText("进出仓");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(201, 21, 58, 23));
			jLabel2.setText("类型代码");
			pn1 = new JPanel();
			pn1.setLayout(null);
			pn1.setBounds(new java.awt.Rectangle(17, 18, 410, 183));
			pn1
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			pn1.add(getCbIsCustomsDeclarationCorresponding(), null);
			pn1.add(getCbbSort(), null);
			pn1.add(getTfTypeName(), null);
			pn1.add(getTfTypeCode(), null);
			pn1.add(getCbbInAndOutWarehouse(), null);
			pn1.add(jLabel2, null);
			pn1.add(jLabel4, null);
			pn1.add(jLabel5, null);
			pn1.add(jLabel6, null);
			pn1.add(getCbIsTransferBill(), null);
			pn1.add(jLabel, null);
			pn1.add(getTfFactoryName(), null);
			pn1.add(jLabel1, null);
		}
		return pn1;
	}

	/**
	 * This method initializes cbIsTransferBill
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsTransferBill() {
		if (cbIsTransferBill == null) {
			cbIsTransferBill = new JCheckBox();
			cbIsTransferBill
					.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
			cbIsTransferBill.setBounds(new java.awt.Rectangle(17, 110, 92, 23));
			cbIsTransferBill.setText("标识单据");
		}
		return cbIsTransferBill;
	}

	/**
	 * This method initializes cIisCustomsDeclarationCorresponding
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsCustomsDeclarationCorresponding() {
		if (cbIsCustomsDeclarationCorresponding == null) {
			cbIsCustomsDeclarationCorresponding = new JCheckBox();
			cbIsCustomsDeclarationCorresponding
					.setBounds(new java.awt.Rectangle(17, 141, 174, 23));
			cbIsCustomsDeclarationCorresponding
					.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
			cbIsCustomsDeclarationCorresponding.setText("是否能与报关单对应的单据");
		}
		return cbIsCustomsDeclarationCorresponding;
	}

	/**
	 * This method initializes tfFactoryName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFactoryName() {
		if (tfFactoryName == null) {
			tfFactoryName = new JTextField();
			tfFactoryName.setBounds(new Rectangle(57, 81, 133, 23));
		}
		return tfFactoryName;
	}

}