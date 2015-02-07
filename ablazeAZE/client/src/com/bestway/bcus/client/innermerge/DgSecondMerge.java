/*
 * Created on 2004-6-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.innermerge;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.innermerge.entity.ReverseMergeFourData;
import com.bestway.bcus.innermerge.entity.ReverseMergeTenData;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgSecondMerge extends JDialogBase {

	private javax.swing.JPanel		jContentPane			= null;
	private JLabel					jLabel2					= null;
	private JToolBar				jToolBar2				= null;
	private JButton					btnAdd					= null;
	private JButton					btnEdit					= null;
	private JButton					btnSave					= null;
	private JButton					btnCancel				= null;
	private JTextField				tfHsFourNo				= null;
	private JTextField				tfTenCommodityName		= null;
	private JTextField				tfHsTenCode				= null;
	private JLabel					jLabel3					= null;
	private JButton					btnComplex				= null;
	private JButton					jButton1				= null;
	private JComboBox				cbbMaterielType			= null;
	private JLabel					jLabel4					= null;
	private JTextField				tfTenMemoNo				= null;
	private JLabel					jLabel5					= null;
	private JTextField				tfTenCommoditySpec		= null;
	private JLabel					jLabel6					= null;
	private JComboBox				cbbMemoUnit				= null;
	private JLabel					jLabel7					= null;
	private JLabel					jLabel8					= null;
	private JTextField				tfFirstLegalUnit		= null;
	private JTextField				tfSecondLegalUnit		= null;

	private int						dataState				= DataState.BROWSE;
	private JTableListModel			tableModel				= null;
	private String					materielType			= null;
	private ReverseMergeTenData		reverseMergeTenData		= null;
	private ReverseMergeFourData	reverseMergeFourData	= null;
	private Complex					complex					= null;
	private CommonBaseCodeAction	commonBaseCodeAction	= null;
	private CustomBaseAction		customBaseAction		= null;
	private boolean					hasChildRecord			= false;

	/**
	 * This is the default constructor
	 */
	public DgSecondMerge() {
		super();
		commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		customBaseAction = (CustomBaseAction) CommonVars
		.getApplicationContext().getBean("customBaseAction");
		initialize();
		initUIComponents();

	}

	public void setVisible(boolean isFlag) {
		if (isFlag) {
			showData();
			if (dataState == DataState.EDIT) {
				int count = commonBaseCodeAction
						.findReverseMergeBeforeDataCountByTen(new Request(
								CommonVars.getCurrUser()),
								this.reverseMergeTenData);
				if (count > 0) {
					hasChildRecord = true;
				}
			}
			setState();

		}
		super.setVisible(isFlag);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("十码反归并管理");
		this.setSize(445, 284);
		this.setContentPane(getJContentPane());
	}

	/**
	 * @return Returns the reverseMergeTenData.
	 */
	public ReverseMergeTenData getReverseMergeTenData() {
		return reverseMergeTenData;
	}

	// /**
	// * @return Returns the isPutOnRecord.
	// */
	// public boolean isPutOnRecord() {
	// return isPutOnRecord;
	// }
	//
	// /**
	// * @param isPutOnRecord
	// * The isPutOnRecord to set.
	// */
	// public void setPutOnRecord(boolean isPutOnRecord) {
	// this.isPutOnRecord = isPutOnRecord;
	// }

	/**
	 * @return Returns the reverseMergeFourData.
	 */
	public ReverseMergeFourData getReverseMergeFourData() {
		return reverseMergeFourData;
	}

	/**
	 * @param reverseMergeFourData
	 *            The reverseMergeFourData to set.
	 */
	public void setReverseMergeFourData(
			ReverseMergeFourData reverseMergeFourData) {
		this.reverseMergeFourData = reverseMergeFourData;
	}

	/**
	 * @param reverseMergeFourData
	 *            The reverseMergeFourData to set.
	 */
	public void setReverseMergeTenData(ReverseMergeTenData reverseMergeFourData) {
		this.reverseMergeTenData = reverseMergeFourData;
	}

	/*
	 * jContentPane.add(getJTextField(), null); jConten
	 * jContentPane.add(jLabel3, null); jLabel3.setText("JLabel");
	 * tPane.add(getJTextField1(), null); jContentPane.add(getJTextField2(),
	 * null); jContentPane.add(getJTextField3(), null); return
	 * javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {

			jLabel8 = new JLabel();
			jLabel7 = new JLabel();
			jLabel6 = new JLabel();
			jLabel5 = new JLabel();
			jLabel4 = new JLabel();
			JLabel jLabel1 = new JLabel();
			JLabel jLabel = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);

			jLabel.setBounds(47, 53, 26, 20);
			jLabel.setText("类别");
			jLabel1.setBounds(229, 53, 68, 20);
			jLabel1.setText("4位备案序号");
			jContentPane.add(getJToolBar2(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jLabel3 = new JLabel();
			jLabel3.setBounds(25, 83, 48, 20);
			jContentPane.add(jLabel3, null);
			jLabel2 = new JLabel();
			jLabel2.setBounds(25, 114, 48, 20);
			jLabel2.setText("商品名称");
			jLabel3.setText("商品编码");
			jLabel4.setBounds(225, 83, 72, 20);
			jLabel4.setText("十位备案序号");
			jLabel5.setBounds(248, 114, 49, 20);
			jLabel5.setText("商品规格");
			jLabel6.setBounds(25, 145, 48, 20);
			jLabel6.setText("备案单位");
			jLabel7.setBounds(232, 145, 65, 20);
			jLabel7.setText(" 法定单位一");
			jLabel8.setBounds(10, 178, 63, 20);
			jLabel8.setText(" 法定单位二");
			jContentPane.add(jLabel2, null);
			jContentPane.add(getBtnComplex(), null);
			jContentPane.add(getTfHsFourNo(), null);
			jContentPane.add(getTfTenCommodityName(), null);
			jContentPane.add(getTfHsTenCode(), null);
			jContentPane.add(getCbbMaterielType(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getTfTenMemoNo(), null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(getTfTenCommoditySpec(), null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(getCbbMemoUnit(), null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(jLabel8, null);
			jContentPane.add(getTfFirstLegalUnit(), null);
			jContentPane.add(getTfSecondLegalUnit(), null);
		}
		return jContentPane;
	}

	/**
	 * @return Returns the dataState.
	 */
	public int getDataState() {
		return dataState;
	}

	/**
	 * @return Returns the materielType.
	 */
	public String getMaterielType() {
		return materielType;
	}

	/**
	 * @param materielType
	 *            The materielType to set.
	 */
	public void setMaterielType(String materielType) {
		this.materielType = materielType;
	}

	/**
	 * @param dataState
	 *            The dataState to set.
	 */
	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	/**
	 * This method initializes jToolBar2
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar2() {
		if (jToolBar2 == null) {
			jToolBar2 = new JToolBar();
			jToolBar2.setBounds(0, 0, 436, 34);
			jToolBar2.add(getBtnAdd());
			jToolBar2.add(getBtnEdit());
			jToolBar2.add(getBtnSave());
			jToolBar2.add(getBtnCancel());
			jToolBar2.add(getJButton1());
		}
		return jToolBar2;
	}

	/**
	 * This method initializes btnAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.ADD;
					showData();
					setState();
				}
			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.EDIT;
					showData();
					setState();
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (validateIsNull() == true) {
						return;
					}
					if (!validateIsExist()) {
						JOptionPane.showMessageDialog(DgSecondMerge.this,
								"商品编码不存在", "警告",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					save();
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (reverseMergeTenData != null) {
						dataState = DataState.EDIT;
						showData();
					}
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes tfHsFourNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfHsFourNo() {
		if (tfHsFourNo == null) {
			tfHsFourNo = new JTextField();
			tfHsFourNo.setBounds(297, 53, 117, 20);
			tfHsFourNo.setEditable(false);
			tfHsFourNo.setBackground(java.awt.Color.white);
		}
		return tfHsFourNo;
	}

	/**
	 * This method initializes tfTenCommodityName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTenCommodityName() {
		if (tfTenCommodityName == null) {
			tfTenCommodityName = new JTextField();
			tfTenCommodityName.setBounds(75, 114, 117, 20);
		}
		return tfTenCommodityName;
	}

	/**
	 * This method initializes tfHsTenCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfHsTenCode() {
		if (tfHsTenCode == null) {
			tfHsTenCode = new JTextField();
			tfHsTenCode.setBounds(75, 83, 99, 20);
			// tfHsTenCode.setDocument(new PlainDocument() {
			// public void insertString(int offs, String str, AttributeSet a)
			// throws BadLocationException {
			// if (str == null) {
			// return;
			// }
			// if (super.getLength() >= 10
			// || super.getLength() + str.length() > 10) {
			// return;
			// }
			// super.insertString(offs, str, a);
			// }
			// });
		}
		return tfHsTenCode;
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
	 * This method initializes btnComplex
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnComplex() {
		if (btnComplex == null) {
			btnComplex = new JButton();
			btnComplex.setBounds(173, 83, 18, 19);
			btnComplex.setText("...");
			btnComplex.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String fourCode = tfHsFourNo.getText().trim();
					if (fourCode.equals("")) {
						JOptionPane
								.showMessageDialog(DgSecondMerge.this,
										"四码为空!!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					Complex c = (Complex) CommonQuery.getInstance()
							.getComplexByFourCode(fourCode);
					if (c != null) {
						tfHsTenCode.setText(c.getCode().trim());
						tfTenCommodityName.setText(c.getName());
						tfFirstLegalUnit.setText(c.getFirstUnit() == null ? ""
								: c.getFirstUnit().getName());
						tfSecondLegalUnit
								.setText(c.getSecondUnit() == null ? "" : c
										.getSecondUnit().getName());
						complex = c;
					}
				}
			});
		}
		return btnComplex;
	}

	/**
	 * This method initializes cbbMaterielType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbMaterielType() {
		if (cbbMaterielType == null) {
			cbbMaterielType = new JComboBox();
			cbbMaterielType.setEnabled(false);
			cbbMaterielType.setBounds(75, 53, 117, 20);

			cbbMaterielType.setBackground(java.awt.Color.white);
		}
		return cbbMaterielType;

	}

	/**
	 * This method initializes tfTenMemoNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTenMemoNo() {
		if (tfTenMemoNo == null) {
			tfTenMemoNo = new JTextField();
			tfTenMemoNo.setEditable(false);
			tfTenMemoNo.setBackground(java.awt.Color.white);
			tfTenMemoNo.setBounds(297, 83, 117, 20);
		}
		return tfTenMemoNo;
	}

	/**
	 * This method initializes tfTenCommoditySpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTenCommoditySpec() {
		if (tfTenCommoditySpec == null) {
			tfTenCommoditySpec = new JTextField();
			tfTenCommoditySpec.setBounds(297, 114, 117, 20);
		}
		return tfTenCommoditySpec;
	}

	/**
	 * This method initializes cbbMemoUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbMemoUnit() {
		if (cbbMemoUnit == null) {
			cbbMemoUnit = new JComboBox();
			cbbMemoUnit.setBounds(75, 145, 117, 20);
		}
		return cbbMemoUnit;
	}

	/**
	 * This method initializes tfFirstLegalUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFirstLegalUnit() {
		if (tfFirstLegalUnit == null) {
			tfFirstLegalUnit = new JTextField();
			tfFirstLegalUnit.setBounds(297, 145, 117, 20);
			tfFirstLegalUnit.setEditable(false);
			tfFirstLegalUnit.setBackground(java.awt.Color.white);
		}
		return tfFirstLegalUnit;
	}

	/**
	 * This method initializes tfSecondLegalUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSecondLegalUnit() {
		if (tfSecondLegalUnit == null) {
			tfSecondLegalUnit = new JTextField();
			tfSecondLegalUnit.setBounds(75, 178, 117, 20);
			tfSecondLegalUnit.setEditable(false);
			tfSecondLegalUnit.setBackground(java.awt.Color.white);

		}
		return tfSecondLegalUnit;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("关闭");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgSecondMerge.this.dispose();
				}
			});
		}
		return jButton1;
	}

	/**
	 * 初始化组件
	 */
	private void initUIComponents() {
		cbbMaterielType.addItem(new ItemProperty(MaterielType.FINISHED_PRODUCT,
				"成品"));
		cbbMaterielType.addItem(new ItemProperty(
				MaterielType.SEMI_FINISHED_PRODUCT, "半成品"));
		cbbMaterielType.addItem(new ItemProperty(MaterielType.MATERIEL, "料件"));
		cbbMaterielType.addItem(new ItemProperty(MaterielType.MACHINE, "设备"));
		cbbMaterielType.addItem(new ItemProperty(MaterielType.REMAIN_MATERIEL,
				"边角料"));
		cbbMaterielType.addItem(new ItemProperty(MaterielType.BAD_PRODUCT,
				"残次品"));

		cbbMemoUnit.setModel(CustomBaseModel.getInstance().getUnitModel());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbMemoUnit,
				"code", "name");
		cbbMemoUnit.setRenderer(CustomBaseRender.getInstance().getRender());
	}

	/**
	 * 显示数据
	 * 
	 */
	private void showData() {
		if (this.dataState == DataState.ADD) {
			this.cbbMaterielType.setSelectedIndex(ItemProperty.getIndexByCode(
					this.materielType, this.cbbMaterielType));
			//
			// 设置最大的十位商品编码
			//
			int maxFourNo = this.commonBaseCodeAction.findMaxTenReverseMergeNo(
					new Request(CommonVars.getCurrUser()), this.materielType);
			this.tfTenMemoNo.setText(String.valueOf(maxFourNo + 1));
			if (this.reverseMergeFourData.getHsFourCode() != null) {
				this.tfHsFourNo.setText(this.reverseMergeFourData
						.getHsFourCode());
			} else {
				this.tfHsFourNo.setText("");
			}
			this.tfTenCommodityName.setText("");
			this.tfTenCommoditySpec.setText("");
			this.cbbMemoUnit.setSelectedItem(null);
			this.tfFirstLegalUnit.setText("");
			this.tfSecondLegalUnit.setText("");
			this.complex = null;

		} else if (this.dataState == DataState.EDIT) {
			if (this.reverseMergeTenData == null) {
				return;
			}
			if (this.reverseMergeTenData.getReverseMergeFourData() != null) {
				this.reverseMergeFourData = this.reverseMergeTenData
						.getReverseMergeFourData();
			}
			if (this.reverseMergeFourData != null) {
				if (this.reverseMergeFourData.getImrType() != null) {
					this.materielType = this.reverseMergeTenData
							.getReverseMergeFourData().getImrType();
					this.cbbMaterielType.setSelectedIndex(ItemProperty
							.getIndexByCode(this.materielType,
									this.cbbMaterielType));
				}
				if (this.reverseMergeFourData.getHsFourCode() != null) {
					this.tfHsFourNo.setText(this.reverseMergeFourData
							.getHsFourCode());
				} else {
					this.tfHsFourNo.setText("");
				}
			}
			if (this.reverseMergeTenData.getHsAfterTenMemoNo() != null) {
				this.tfTenMemoNo.setText(this.reverseMergeTenData
						.getHsAfterTenMemoNo().toString());
			} else {
				this.tfTenMemoNo.setText("");
			}
			if (this.reverseMergeTenData.getHsAfterComplex() != null) {
				this.tfHsTenCode.setText(this.reverseMergeTenData
						.getHsAfterComplex().getCode());
				this.complex = this.reverseMergeTenData.getHsAfterComplex();
			} else {
				this.tfHsTenCode.setText("");
			}
			if (this.reverseMergeTenData.getHsAfterMaterielTenName() != null) {
				this.tfTenCommodityName.setText(this.reverseMergeTenData
						.getHsAfterMaterielTenName());
			} else {
				this.tfTenCommodityName.setText("");
			}
			if (this.reverseMergeTenData.getHsAfterMaterielTenSpec() != null) {
				this.tfTenCommoditySpec.setText(this.reverseMergeTenData
						.getHsAfterMaterielTenSpec());
			} else {
				this.tfTenCommoditySpec.setText("");
			}
			this.cbbMemoUnit.setSelectedItem(this.reverseMergeTenData
					.getHsAfterMemoUnit());
			if (this.reverseMergeTenData.getHsAfterLegalUnit() != null) {
				this.tfFirstLegalUnit.setText(this.reverseMergeTenData
						.getHsAfterLegalUnit().getName());
			} else {
				this.tfFirstLegalUnit.setText("");
			}
			if (this.reverseMergeTenData.getHsAfterSecondLegalUnit() != null) {
				this.tfSecondLegalUnit.setText(this.reverseMergeTenData
						.getHsAfterSecondLegalUnit().getName());
			} else {
				this.tfSecondLegalUnit.setText("");
			}
		}
	}

	/**
	 * 填充对象
	 */
	private void fillData() {
		this.reverseMergeTenData.setCompany(CommonVars.getCurrUser()
				.getCompany());
		this.reverseMergeTenData
				.setReverseMergeFourData(this.reverseMergeFourData);
		this.reverseMergeTenData.setHsAfterComplex(this.complex);
		if (this.complex != null) {
			this.reverseMergeTenData.setHsAfterLegalUnit(this.complex
					.getFirstUnit());
			this.reverseMergeTenData.setHsAfterSecondLegalUnit(this.complex
					.getSecondUnit());
		}
		this.reverseMergeTenData
				.setHsAfterMaterielTenName(this.tfTenCommodityName.getText());
		this.reverseMergeTenData
				.setHsAfterMaterielTenSpec(this.tfTenCommoditySpec.getText());
		this.reverseMergeTenData.setHsAfterMemoUnit((Unit) this.cbbMemoUnit
				.getSelectedItem());

	}

	/**
	 * 设置状态
	 * 
	 */
	private void setState() {
		this.btnAdd.setEnabled(dataState == DataState.BROWSE);
		this.btnEdit.setEnabled(dataState == DataState.BROWSE);
		this.btnSave.setEnabled(dataState != DataState.BROWSE);
		this.btnCancel.setEnabled(dataState != DataState.BROWSE
				&& this.reverseMergeTenData != null);
		this.cbbMaterielType.setEnabled(false);
		this.tfHsFourNo.setEditable(false);
		this.tfHsTenCode.setEnabled(dataState != DataState.BROWSE
				&& !hasChildRecord);
		this.btnComplex.setEnabled(dataState != DataState.BROWSE
				&& !hasChildRecord);
		this.tfTenCommodityName.setEnabled(dataState != DataState.BROWSE);
	}

	/**
	 * 验证数据
	 */
	private boolean validateIsNull() {
		if (this.cbbMaterielType.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "类别不可为空", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (this.tfHsTenCode.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "商品编码不可为空", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (this.tfHsFourNo.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "4位备案序号不可为空", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (this.tfTenCommodityName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "商品名称不可为空", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		return false;
	}

	/**
	 * 验证商品编码是否存在
	 * 
	 * @return
	 */
	private boolean validateIsExist() {		
		String value = this.tfHsTenCode.getText().trim();
		Complex complex = customBaseAction.findComplexByCode(value);
		if(complex !=null){
			return true;
		}
		return false;
	}

	/**
	 * 保存
	 * 
	 */
	private void save() {
		if (dataState == DataState.ADD) {
			this.reverseMergeTenData = new ReverseMergeTenData();
			this.fillData();
			//
			// 设置最大的十位商品编码
			//
			int maxFourNo = this.commonBaseCodeAction.findMaxTenReverseMergeNo(
					new Request(CommonVars.getCurrUser()), this.materielType);
			this.reverseMergeTenData.setHsAfterTenMemoNo(new Integer(
					maxFourNo + 1));
			reverseMergeTenData = commonBaseCodeAction.saveReverseMergeTenData(
					new Request(CommonVars.getCurrUser()), reverseMergeTenData);
			this.tableModel.addRow(reverseMergeTenData);
		} else if (dataState == DataState.EDIT) {
			this.fillData();
			reverseMergeTenData = commonBaseCodeAction.saveReverseMergeTenData(
					new Request(CommonVars.getCurrUser()), reverseMergeTenData);
			this.tableModel.updateRow(reverseMergeTenData);
		}
		setDataState(DataState.BROWSE);
		setState();
	}

} // @jve:decl-index=0:visual-constraint="10,10"
