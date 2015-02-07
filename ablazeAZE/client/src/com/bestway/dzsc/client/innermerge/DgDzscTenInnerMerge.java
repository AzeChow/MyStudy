/*
 * Created on 2004-7-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.innermerge;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.Request;
import com.bestway.dzsc.innermerge.action.DzscInnerMergeAction;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeData;
import com.bestway.dzsc.innermerge.entity.DzscTenInnerMerge;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDzscTenInnerMerge extends JDialogBase {

	private JPanel jContentPane = null;

	private JTextField tfTenComplexCode = null;

	private JTextField tfTenPtName = null;

	private JTextField tfTenPtSpec = null;

	private JTextField tfTenUnit = null;

	private JButton btnTenComplex = null;

	private JButton btnTenUnit = null;

	private JButton btnOK = null;

	private JButton btnCancel = null;

	private JPanel jPanel = null;

	private List currentRows = null; // @jve:decl-index=0:

	private MultiSpanCellTable jTable = null;

	// private boolean isNew = false;

	private Unit tenUnit = null; // 十位商品单位

	// private CommonBaseCodeAction commonBaseCodeAction = null;

	private Complex complex = null;

	private DzscTenInnerMerge tenInnerMerge = null; // @jve:decl-index=0:

	private DzscInnerMergeAction dzscInnerMergeAction = null;

	// private DzscInnerMergeHead head = null;

	private boolean isCombineRows = false;

	private JLabel jLabel3 = null;

	private JTextField tfTenSeqNum = null;

	private String materialType = null; // @jve:decl-index=0:

	private boolean isChange = false;

	private boolean isEditTenMerge = false;

	private JButton btnTenInnerMerge = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="587,96"

	public boolean isEditTenMerge() {
		return isEditTenMerge;
	}

	public void setEditTenMerge(boolean isEditTenMerge) {
		this.isEditTenMerge = isEditTenMerge;
	}

	public boolean isChange() {
		return isChange;
	}

	public void setChange(boolean isChange) {
		this.isChange = isChange;
	}

	// public DzscTenInnerMerge getTenInnerMerge() {
	// return tenInnerMerge;
	// }
	//
	// public void setTenInnerMerge(DzscTenInnerMerge tenInnerMerge) {
	// this.tenInnerMerge = tenInnerMerge;
	// }

	/**
	 * This method initializes
	 * 
	 */
	public DgDzscTenInnerMerge() {
		super();
		// commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
		// .getApplicationContext().getBean("commonBaseCodeAction");
		dzscInnerMergeAction = (DzscInnerMergeAction) CommonVars
				.getApplicationContext().getBean("dzscInnerMergeAction");
		initialize();
	}

	public void setVisible(boolean b) {
		if (b) {
			setState();
		}
		super.setVisible(b);
	}

	private void setState() {
		if (isEditTenMerge) {
			this.jRadioButton.setVisible(false);
			this.jRadioButton1.setVisible(false);
			this.btnTenComplex.setVisible(true);
			this.btnTenInnerMerge.setVisible(false);
			jPanel.setBounds(12, 17, 291, 159);
			btnOK.setBounds(76, 189, 71, 25);
			btnCancel.setBounds(180, 189, 68, 25);
			this.setSize(336, 255);
			tenInnerMerge = ((DzscInnerMergeData) currentRows.get(0))
					.getDzscTenInnerMerge();
			 showData();
		} else {
			this.jRadioButton.setVisible(true);
			this.jRadioButton1.setVisible(true);
			if (this.jRadioButton.isSelected()) {
				this.btnTenComplex.setVisible(true);
				this.btnTenInnerMerge.setVisible(false);
				tenInnerMerge = null;
				 showData();
				tfTenSeqNum.setText(String.valueOf(dzscInnerMergeAction
						.findMaxInnerMergeNo(new Request(CommonVars
								.getCurrUser()), materialType, "tenSeqNum")));
			} else {
				this.btnTenComplex.setVisible(false);
				this.btnTenInnerMerge.setVisible(true);
				 showData();
			}
			jPanel.setBounds(12, 42, 291, 159);
			btnOK.setBounds(76, 214, 71, 25);
			btnCancel.setBounds(180, 214, 68, 25);
			this.setSize(336, 280);
		}
//		showData();
	}

	private void showData() {
		if (tenInnerMerge != null) {
			complex = tenInnerMerge.getTenComplex();
			tfTenPtName.setText(tenInnerMerge.getTenPtName());
			if (tenInnerMerge.getTenUnit() != null) {
				tenUnit = tenInnerMerge.getTenUnit();
				tfTenUnit.setText(tenUnit.getName());
			}
			tfTenSeqNum.setText(String.valueOf(tenInnerMerge.getTenSeqNum()));
			tfTenPtSpec.setText(tenInnerMerge.getTenPtSpec());
			tfTenComplexCode.setText(tenInnerMerge.getTenComplex().getCode());
		} else {
			tfTenPtName.setText("");
			tfTenUnit.setText("");
			tfTenSeqNum.setText("");
			tfTenPtSpec.setText("");
			tfTenComplexCode.setText("");
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("10位商品归并");
		this.setSize(336, 280);
		this.getButtonGroup();
		// this.addWindowListener(new java.awt.event.WindowAdapter() {
		// public void windowOpened(java.awt.event.WindowEvent e) {
		// if (!isNew()) {// 修改10位归并
		//
		// } else {// 新增10位归并
		//
		// }
		// }
		// });

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

			jContentPane.add(getBtnOK(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJRadioButton(), null);
			jContentPane.add(getJRadioButton1(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes tfMaterielCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTenComplexCode() {
		if (tfTenComplexCode == null) {
			tfTenComplexCode = new JTextField();
			tfTenComplexCode.setEditable(false);
			tfTenComplexCode.setBounds(116, 28, 126, 22);
		}
		return tfTenComplexCode;
	}

	/**
	 * This method initializes tfMaterielName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTenPtName() {
		if (tfTenPtName == null) {
			tfTenPtName = new JTextField();
			tfTenPtName.setEditable(true);
			tfTenPtName.setBounds(116, 58, 126, 22);
		}
		return tfTenPtName;
	}

	/**
	 * This method initializes tfMaterielSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTenPtSpec() {
		if (tfTenPtSpec == null) {
			tfTenPtSpec = new JTextField();
			// tfMaterielSpec.setEditable(false);
			tfTenPtSpec.setBounds(116, 89, 126, 22);
		}
		return tfTenPtSpec;
	}

	/**
	 * This method initializes tfDeclareUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTenUnit() {
		if (tfTenUnit == null) {
			tfTenUnit = new JTextField();
			tfTenUnit.setEditable(false);
			tfTenUnit.setBounds(116, 120, 126, 22);
		}
		return tfTenUnit;
	}

	/**
	 * This method initializes btnTenMaterielCode
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnTenComplex() {
		if (btnTenComplex == null) {
			btnTenComplex = new JButton();
			btnTenComplex.setBounds(245, 28, 24, 20);
			btnTenComplex.setText("...");
			btnTenComplex
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// complex = (Complex) DzscClientHelper.getInstance()
							// .getComplexForDzscInnerMerge();
							complex = (Complex) CommonQuery.getInstance()
									.getComplex();
							if (complex != null) {
								DgDzscTenInnerMerge.this
										.getTfTenComplexCode().setText(
												complex.getCode());
								DgDzscTenInnerMerge.this.getTfTenPtName()
										.setText(complex.getName());
								if (complex.getFirstUnit() != null) {
									tenUnit = complex.getFirstUnit();
									tfTenUnit.setText(tenUnit.getName());
								}
								// showData();
							}
						}
					});
		}
		return btnTenComplex;
	}

	/**
	 * This method initializes btnAfterMemoUnit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnTenUnit() {
		if (btnTenUnit == null) {
			btnTenUnit = new JButton();
			btnTenUnit.setEnabled(false);
			btnTenUnit.setBounds(245, 120, 24, 20);
			btnTenUnit.setText("...");
			btnTenUnit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Unit c = (Unit) CommonQuery.getInstance().getCustomUnit();
					if (c != null) {
						tenUnit = c;
						tfTenUnit.setText(c.getName());
					}
				}
			});
		}
		return btnTenUnit;
	}

	/**
	 * This method initializes btnOK
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setBounds(76, 214, 71, 25);
			btnOK.setText("确定");
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tfTenSeqNum.getText().trim().equals("")) {
						JOptionPane.showMessageDialog(
								DgDzscTenInnerMerge.this, "10位归并序号不能为空，请输入",
								"提示！", 0);
						return;
					}
					if (tfTenComplexCode.getText().trim().equals("")) {
						JOptionPane.showMessageDialog(
								DgDzscTenInnerMerge.this, "10位商品编码不能为空，请输入",
								"提示！", 0);
						return;
					}
					if (tfTenPtName.getText().trim().equals("")) {
						JOptionPane.showMessageDialog(
								DgDzscTenInnerMerge.this, "10位商品名称不能为空，请输入",
								"提示！", 0);
						return;
					}
					// Integer
					// maxSeqNum=dzscAction.findMaxInnerMergeTenSeqNum(new
					// Request(CommonVars
					// .getCurrUser()),type);
					Integer maxSeqNum = Integer.parseInt(tfTenSeqNum.getText()
							.trim());
					List ls = new ArrayList();
					if (isEditTenMerge) {
						if (tenInnerMerge == null) {
							return;
						}
						fillData(maxSeqNum);
						ls = dzscInnerMergeAction.editTenInnerMerge(
								new Request(CommonVars.getCurrUser()),
								tenInnerMerge, isChange);
					} else {
						if (jRadioButton.isSelected()) {
							tenInnerMerge = new DzscTenInnerMerge();
						}
						fillData(maxSeqNum);
						ls = dzscInnerMergeAction.tenInnerMerge(new Request(
								CommonVars.getCurrUser()), currentRows,
								tenInnerMerge, isChange);
					}
					// List ls = dzscInnerMergeAction.tenInnerMerge(new Request(
					// CommonVars.getCurrUser()), currentRows, maxSeqNum,
					// complex, tenUnit, tfTenPtName.getText(),
					// tfTenPtSpec.getText(), isNew());
					refreshTable(ls);
					dispose();
				}
			});
		}
		return btnOK;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(180, 214, 68, 25);
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgDzscTenInnerMerge.this.dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel3 = new JLabel();
			jPanel = new JPanel();
			javax.swing.JLabel jLabel = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel4 = new JLabel();
			jPanel.setLayout(null);
			jPanel.setBounds(12, 42, 291, 159);
			jLabel.setText("10位商品编码");
			jLabel.setBounds(23, 30, 81, 18);
			jLabel.setForeground(new java.awt.Color(0, 0, 204));
			jLabel1.setText("商品名称");
			jLabel1.setBounds(23, 60, 80, 18);
			jLabel1.setForeground(new java.awt.Color(0, 0, 204));
			jLabel2.setText("商品规格");
			jLabel2.setBounds(23, 89, 84, 18);
			jLabel4.setText("商品单位");
			jLabel4.setBounds(23, 123, 84, 18);
			jLabel4.setForeground(new java.awt.Color(0, 0, 204));
			jPanel.add(jLabel, null);
			jPanel.add(getTfTenComplexCode(), null);
			jPanel.add(getBtnTenComplex(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel4, null);
			jPanel.add(getTfTenPtName(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getBtnTenUnit(), null);
			jPanel.add(getTfTenUnit(), null);
			jPanel.add(getTfTenPtSpec(), null);
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					"", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION,
					new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12),
					new java.awt.Color(51, 51, 51)));
			jLabel3.setBounds(23, 2, 74, 18);
			jLabel3.setText("归并序号");
			jPanel.add(jLabel3, null);
			jPanel.add(getTfTenSeqNum(), null);
			jPanel.add(getBtnTenInnerMerge(), null);
		}
		return jPanel;
	}

	private void refreshTable(List ls) {
		AttributiveCellTableModel tableModel = (AttributiveCellTableModel) jTable
				.getModel();
		tableModel.updateRows(ls);
		if (this.isCombineRows) {
			((MultiSpanCellTable) jTable).combineRows(new int[] { 14, 9 },
					new int[] { 14, 15, 16, 17, 18 });
			((MultiSpanCellTable) jTable).combineRows(9, new int[] { 8, 9,
					10, 11, 12, 13 });
		} else {
			((MultiSpanCellTable) jTable).splitRows(new int[] { 9, 14 });
		}
	}

	//
	// /**
	// * @return Returns the isNew.
	// */
	// public boolean isNew() {
	// return isNew;
	// }
	//
	// /**
	// * @param isNew
	// * The isNew to set.
	// */
	// public void setNew(boolean isNew) {
	// this.isNew = isNew;
	// }

	/**
	 * @return Returns the jTable.
	 */
	public MultiSpanCellTable getJTable() {
		return jTable;
	}

	/**
	 * @param table
	 *            The jTable to set.
	 */
	public void setJTable(MultiSpanCellTable table) {
		jTable = table;
	}

	/**
	 * @return Returns the currentRows.
	 */
	public List getCurrentRows() {
		return currentRows;
	}

	/**
	 * @param currentRows
	 *            The currentRows to set.
	 */
	public void setCurrentRows(List currentRows) {
		this.currentRows = currentRows;
	}

	/**
	 * @return Returns the afterMemoUnit.
	 */
	public Unit getTenUnit() {
		return tenUnit;
	}

	/**
	 * @param afterMemoUnit
	 *            The afterMemoUnit to set.
	 */
	public void setTenUnit(Unit afterMemoUnit) {
		this.tenUnit = afterMemoUnit;
	}

	/**
	 * @param btnAfterMemoUnit
	 *            The btnAfterMemoUnit to set.
	 */
	public void setBtnTenUnit(JButton btnAfterMemoUnit) {
		this.btnTenUnit = btnAfterMemoUnit;
	}

	/**
	 * @param btnOK
	 *            The btnOK to set.
	 */
	public void setBtnOK(JButton btnOK) {
		this.btnOK = btnOK;
	}

	/**
	 * @param tfDeclareUnit
	 *            The tfDeclareUnit to set.
	 */
	public void setTfTenUnit(JTextField tfDeclareUnit) {
		this.tfTenUnit = tfDeclareUnit;
	}

	/**
	 * @param tfMaterielCode
	 *            The tfMaterielCode to set.
	 */
	public void setTfTenComplexCode(JTextField tfMaterielCode) {
		this.tfTenComplexCode = tfMaterielCode;
	}

	/**
	 * @param tfMaterielName
	 *            The tfMaterielName to set.
	 */
	public void setTfTenPtName(JTextField tfMaterielName) {
		this.tfTenPtName = tfMaterielName;
	}

	/**
	 * @param tfMaterielSpec
	 *            The tfMaterielSpec to set.
	 */
	public void setTfTenPtSpec(JTextField tfMaterielSpec) {
		this.tfTenPtSpec = tfMaterielSpec;
	}

	// /**
	// * @return Returns the head.
	// */
	// public DzscInnerMergeHead getHead() {
	// return head;
	// }
	//
	// /**
	// * @param head
	// * The head to set.
	// */
	// public void setHead(DzscInnerMergeHead head) {
	// this.head = head;
	// }

	public boolean isCombineRows() {
		return isCombineRows;
	}

	public void setCombineRows(boolean isCombineRows) {
		this.isCombineRows = isCombineRows;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTenSeqNum() {
		if (tfTenSeqNum == null) {
			tfTenSeqNum = new JTextField();
			tfTenSeqNum.setEditable(false);
			tfTenSeqNum.setBounds(116, 4, 126, 23);
		}
		return tfTenSeqNum;
	}

	/**
	 * @return Returns the type.
	 */
	public String getMaterialType() {
		return materialType;
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setMaterialType(String type) {
		this.materialType = type;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnTenInnerMerge() {
		if (btnTenInnerMerge == null) {
			btnTenInnerMerge = new JButton();
			btnTenInnerMerge.setBounds(new Rectangle(245, 4, 24, 20));
			btnTenInnerMerge.setText("...");
			btnTenInnerMerge
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							tenInnerMerge = DzscInnerMergeQuery.getInstance()
									.findDzscTenInnerMerge(materialType,isChange);
							showData();
						}
					});
		}
		return btnTenInnerMerge;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(new Rectangle(33, 10, 134, 25));
			jRadioButton.setSelected(true);
			jRadioButton.setText("归并到新10码");
			jRadioButton.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						setState();
					}
				}
			});
		}
		return jRadioButton;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setBounds(new Rectangle(166, 10, 137, 25));
			jRadioButton1.setText("归并到已有10码");
			jRadioButton1.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						setState();
					}
				}
			});
		}
		return jRadioButton1;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.getJRadioButton());
			buttonGroup.add(this.getJRadioButton1());
		}
		return buttonGroup;
	}

	private void fillData(Integer maxSeqNum) {
		tenInnerMerge.setTenSeqNum(maxSeqNum);
		tenInnerMerge.setTenComplex(complex);
		tenInnerMerge.setTenPtName(tfTenPtName.getText().trim());
		tenInnerMerge.setTenPtSpec(tfTenPtSpec.getText().trim());
		tenInnerMerge.setTenUnit(tenUnit);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
