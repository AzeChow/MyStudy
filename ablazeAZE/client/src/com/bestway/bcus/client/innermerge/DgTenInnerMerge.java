/*
 * Created on 2004-7-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.innermerge;
//李扬更改程序
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgTenInnerMerge extends JDialogBase {

	private JPanel					jContentPane			= null;

	private JTextField				tfMaterielCode			= null;

	private JTextField				tfMaterielName			= null;

	private JTextField				tfMaterielSpec			= null;

	private JTextField				tfEnterpriseUnit		= null;

	private JTextField				tfDeclareUnit			= null;

	private JButton					btnTenMaterielCode		= null;

	private JButton					btnAfterMemoUnit		= null;

	private JButton					btnOK					= null;

	private JButton					btnCancel				= null;

	private JPanel					jPanel					= null;

	private Complex					afterComplex			= null;

	private String					afterTenMaterielName	= "";  //  @jve:decl-index=0:

	private String					afterTenMaterielSpec	= "";  //  @jve:decl-index=0:

	private CalUnit					beforeEnterpriseUnit	= null;	// 企业计量单位

	private List					currentRows				= null;  //  @jve:decl-index=0:

	private MultiSpanCellTable		jTable					= null;

	private boolean					isNew					= false;

	private Unit					afterMemoUnit			= null;	// 申报计量单位

	private Unit					afterLegalUnit			= null;	// 第一法定单位

	private Unit					afterSecondLegalUnit	= null;		// 第二法定单位

	private CommonBaseCodeAction	commonBaseCodeAction	= null;

	private JTextField				tfAfterLegalUnit		= null;

	private JTextField				tfAfterSecondLegalUnit	= null;

	private JButton					btnAfterLegalUnit		= null;

	private JButton					btnAfterSecondLegalUnit	= null;

	/**
	 * This method initializes
	 * 
	 */
	public DgTenInnerMerge() {
		super();
		commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		initialize();
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
		this.setSize(396, 321);
	}

	public void setVisible(boolean isFlag) {
		if (isFlag) {
			if (!isNew()) {// 添加10位归并
				if (currentRows != null) {
					for (int i = 0; i < currentRows.size(); i++) {
						InnerMergeData data = (InnerMergeData) currentRows
								.get(i);
						if (data.getHsAfterComplex() != null) {
							afterComplex = data.getHsAfterComplex();
						}
						if (data.getHsAfterMemoUnit() != null) {
							//更改疑问
							//beforeEnterpriseUnit =  data.getHsAfterMemoUnit();
							//afterMemoUnit = beforeEnterpriseUnit.getUnit();
							afterMemoUnit =  data.getHsAfterMemoUnit();
						}
						if (data.getHsAfterMaterielTenName() != null
								&& !data.getHsAfterMaterielTenName().equals("")) {
							afterTenMaterielName = data
									.getHsAfterMaterielTenName();
						}
						if (data.getHsAfterMaterielNameSpec() != null
								&& !data.getHsAfterMaterielNameSpec()
										.equals("")) {
							afterTenMaterielSpec = data
									.getHsAfterMaterielTenSpec();
						}
						if (data.getHsAfterLegalUnit() != null) {
							afterLegalUnit = data.getHsAfterLegalUnit();
						}
						if (data.getHsAfterSecondLegalUnit() != null) {
							afterSecondLegalUnit = data
									.getHsAfterSecondLegalUnit();
						}
					}
				}
				showData();
				tfMaterielCode.setEditable(false);
				tfMaterielName.setEditable(false);
				tfMaterielSpec.setEditable(false);
				tfEnterpriseUnit.setEditable(false);
				tfDeclareUnit.setEditable(false);
				btnTenMaterielCode.setEnabled(false);
			} else {// 新增10位归并
				if (currentRows != null) {
//					for (int i = 0; i < currentRows.size(); i++) {
						InnerMergeData data = (InnerMergeData) currentRows
								.get(0);
						if (data.getMateriel().getComplex() != null) {
							afterComplex = data.getMateriel().getComplex();
							afterLegalUnit = data.getMateriel().getComplex().getFirstUnit();
							afterSecondLegalUnit = data.getMateriel().getComplex().getSecondUnit();
							afterTenMaterielName = data.getMateriel().getComplex().getName();
							afterTenMaterielSpec= data.getMateriel().getFactorySpec();
							afterMemoUnit=data.getMateriel().getCalUnit().getUnit();
						}
						/*if (data.getHsBeforeEnterpriseUnit() != null) {
							beforeEnterpriseUnit = data
									.getHsBeforeEnterpriseUnit();
							afterMemoUnit = beforeEnterpriseUnit.getUnit();
						}*/
//					}
				}
				showData();
				/*if (afterComplex != null) {
					btnTenMaterielCode.setEnabled(false);
				}*/
				/*tfMaterielCode.setEditable(false);
				tfEnterpriseUnit.setEditable(false);
				tfDeclareUnit.setEditable(false);*/
			}
		}

		super.setVisible(isFlag);
	}

	private void showData() {
		if (afterComplex != null) {
			tfMaterielCode.setText(afterComplex.getCode());
		}
		if (afterMemoUnit != null) {
//			tfEnterpriseUnit.setText(beforeEnterpriseUnit.getName());
			tfDeclareUnit.setText(afterMemoUnit.getName());
		}
		if (afterTenMaterielName != null) {
			tfMaterielName.setText(afterTenMaterielName);
		}
		if (afterTenMaterielSpec != null) {
			tfMaterielSpec.setText(afterTenMaterielSpec);
		}
		if (afterLegalUnit != null) {
			tfAfterLegalUnit.setText(afterLegalUnit.getName());
		}
		if (afterSecondLegalUnit != null) {
			tfAfterSecondLegalUnit.setText(afterSecondLegalUnit.getName());
		}
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
		}
		return jContentPane;
	}

	/**
	 * This method initializes tfMaterielCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMaterielCode() {
		if (tfMaterielCode == null) {
			tfMaterielCode = new JTextField();
			tfMaterielCode.setBounds(148, 18, 126, 22);
		}
		return tfMaterielCode;
	}

	/**
	 * This method initializes tfMaterielName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMaterielName() {
		if (tfMaterielName == null) {
			tfMaterielName = new JTextField();
			tfMaterielName.setBounds(148, 48, 126, 22);
		}
		return tfMaterielName;
	}

	/**
	 * This method initializes tfMaterielSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMaterielSpec() {
		if (tfMaterielSpec == null) {
			tfMaterielSpec = new JTextField();
			tfMaterielSpec.setBounds(148, 79, 126, 22);
		}
		return tfMaterielSpec;
	}

	/**
	 * This method initializes tfEnterpriseUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEnterpriseUnit() {
		if (tfEnterpriseUnit == null) {
			tfEnterpriseUnit = new JTextField();
			tfEnterpriseUnit.setVisible(false);
			tfEnterpriseUnit.setBounds(148, 108, 126, 22);
		}
		return tfEnterpriseUnit;
	}

	/**
	 * This method initializes tfDeclareUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfDeclareUnit() {
		if (tfDeclareUnit == null) {
			tfDeclareUnit = new JTextField();
			tfDeclareUnit.setBounds(148, 133, 126, 22);
		}
		return tfDeclareUnit;
	}

	/**
	 * This method initializes btnTenMaterielCode
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnTenMaterielCode() {
		if (btnTenMaterielCode == null) {
			btnTenMaterielCode = new JButton();
			btnTenMaterielCode.setBounds(277, 18, 24, 20);
			btnTenMaterielCode.setText("...");
			btnTenMaterielCode
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							Complex complex = (Complex) CommonQuery
									.getInstance().getComplex();
							if (complex != null) {
								DgTenInnerMerge.this.afterComplex = complex;
								DgTenInnerMerge.this.afterTenMaterielName = complex
										.getName();
								DgTenInnerMerge.this.afterLegalUnit = complex
										.getFirstUnit();
								DgTenInnerMerge.this.afterSecondLegalUnit = complex
										.getSecondUnit();
								showData();
							}
						}
					});
		}
		return btnTenMaterielCode;
	}

	/**
	 * This method initializes btnAfterMemoUnit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAfterMemoUnit() {
		if (btnAfterMemoUnit == null) {
			btnAfterMemoUnit = new JButton();
			btnAfterMemoUnit.setBounds(277, 133, 24, 20);
			btnAfterMemoUnit.setText("...");
			btnAfterMemoUnit
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Unit c = (Unit) CommonQuery.getInstance()
									.getCustomUnit();
							if (c != null) {
								afterMemoUnit = c;
								tfDeclareUnit.setText(c.getName());
							}
						}
					});
		}
		return btnAfterMemoUnit;
	}

	/**
	 * This method initializes btnOK
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setBounds(188, 251, 71, 25);
			btnOK.setText("确定");
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (afterComplex == null) {
						JOptionPane.showMessageDialog(DgTenInnerMerge.this,
								"10位商品不能为空，请输入", "警告！", 0);
						return;
					}
					if (tfMaterielName.getText().trim().equals("")) {
						JOptionPane.showMessageDialog(DgTenInnerMerge.this,
								"商品名称不能为空，请输入", "警告！", 0);
						return;
					}
					afterTenMaterielName = tfMaterielName.getText();
					afterTenMaterielSpec = tfMaterielSpec.getText();
					List ls = commonBaseCodeAction.tenInnerMerge(new Request(
							CommonVars.getCurrUser()), currentRows,
							afterComplex, afterMemoUnit, afterTenMaterielName,
							afterTenMaterielSpec, afterLegalUnit,
							afterSecondLegalUnit, isNew());
					refreshTable(ls);
					DgTenInnerMerge.this.dispose();
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
			btnCancel.setBounds(292, 251, 68, 25);
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgTenInnerMerge.this.dispose();
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
			javax.swing.JLabel jLabel6 = new JLabel();
			javax.swing.JLabel jLabel5 = new JLabel();
			jPanel = new JPanel();
			javax.swing.JLabel jLabel = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();
			jLabel3.setVisible(false);
			javax.swing.JLabel jLabel4 = new JLabel();
			jPanel.setLayout(null);
			jPanel.setBounds(14, 11, 357, 231);
			jLabel.setText("商品编码");
			jLabel.setBounds(55, 20, 60, 18);
			jLabel1.setText("商品名称");
			jLabel1.setBounds(55, 50, 60, 18);
			jLabel2.setText("规格型号");
			jLabel2.setBounds(55, 79, 60, 18);
			jLabel3.setText("企业计量单位");
			jLabel3.setBounds(55, 107, 84, 18);
			jLabel4.setText("申报计量单位");
			jLabel4.setBounds(55, 136, 84, 18);
			jPanel.add(jLabel, null);
			jPanel.add(getTfMaterielCode(), null);
			jPanel.add(getBtnTenMaterielCode(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel4, null);
			jPanel.add(getTfMaterielName(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(getBtnAfterMemoUnit(), null);
			jPanel.add(getTfDeclareUnit(), null);
			jPanel.add(getTfEnterpriseUnit(), null);
			jPanel.add(getTfMaterielSpec(), null);
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					"", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION,
					new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12),
					new java.awt.Color(51, 51, 51)));
			jLabel5.setBounds(54, 164, 84, 22);
			jLabel5.setText("法定单位一");
			jLabel6.setBounds(54, 192, 86, 21);
			jLabel6.setText("法定单位二");
			jPanel.add(jLabel5, null);
			jPanel.add(getTfAfterLegalUnit(), null);
			jPanel.add(jLabel6, null);
			jPanel.add(getTfAfterSecondLegalUnit(), null);
			jPanel.add(getBtnAfterLegalUnit(), null);
			jPanel.add(getBtnAfterSecondLegalUnit(), null);
		}
		return jPanel;
	}

	private void refreshTable(List ls) {
		AttributiveCellTableModel tableModel = (AttributiveCellTableModel) jTable
				.getModel();
		tableModel.updateRows(ls);
		((MultiSpanCellTable) jTable).combineRows(new int[] { 11, 6 },
				new int[] { 11, 12, 13 });
		((MultiSpanCellTable) jTable).combineRows(6,
				new int[] { 6, 7, 8, 9, 10 });
	}

	/**
	 * @return Returns the isNew.
	 */
	public boolean isNew() {
		return isNew;
	}

	/**
	 * @param isNew
	 *            The isNew to set.
	 */
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

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
	 * This method initializes tfAfterLegalUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfAfterLegalUnit() {
		if (tfAfterLegalUnit == null) {
			tfAfterLegalUnit = new JTextField();
			tfAfterLegalUnit.setBounds(148, 162, 126, 22);
			tfAfterLegalUnit.setEditable(false);
		}
		return tfAfterLegalUnit;
	}

	/**
	 * This method initializes tfAfterSecondLegalUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfAfterSecondLegalUnit() {
		if (tfAfterSecondLegalUnit == null) {
			tfAfterSecondLegalUnit = new JTextField();
			tfAfterSecondLegalUnit.setBounds(148, 191, 126, 22);
			tfAfterSecondLegalUnit.setEditable(false);
		}
		return tfAfterSecondLegalUnit;
	}

	/**
	 * This method initializes btnAfterLegalUnit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAfterLegalUnit() {
		if (btnAfterLegalUnit == null) {
			btnAfterLegalUnit = new JButton();
			btnAfterLegalUnit.setBounds(277, 162, 24, 20);
			btnAfterLegalUnit.setText("...");
			btnAfterLegalUnit
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Unit c = (Unit) CommonQuery.getInstance()
									.getCustomUnit();
							if (c != null) {
								afterLegalUnit = c;
								tfAfterLegalUnit.setText(c.getName());
							}
						}
					});
		}
		return btnAfterLegalUnit;
	}

	/**
	 * This method initializes btnAfterSecondLegalUnit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAfterSecondLegalUnit() {
		if (btnAfterSecondLegalUnit == null) {
			btnAfterSecondLegalUnit = new JButton();
			btnAfterSecondLegalUnit.setBounds(277, 191, 24, 20);
			btnAfterSecondLegalUnit.setText("...");
			btnAfterSecondLegalUnit
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Unit c = (Unit) CommonQuery.getInstance()
									.getCustomUnit();
							if (c != null) {
								afterSecondLegalUnit = c;
								tfAfterSecondLegalUnit.setText(c.getName());
							}
						}
					});
		}
		return btnAfterSecondLegalUnit;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
