/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.innermerge;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administratorf
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgFactoryCustoms extends JDialogBase {

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JButton btnClose = null;

	private JTableListModel tableModel = null;

	private MaterialManageAction materialManageAction = null;
	private CommonBaseCodeAction commonBaseCodeAction = null;
	private JLabel jLabel4 = null;
	private JTextField tfTenComplex = null;
	private JLabel jLabel6 = null;
	private JTextField tfTenUnit = null;
	private JLabel jLabel7 = null;
	private JLabel jLabel11 = null;
	private JTextField tfTenName = null;
	private JTextField tfTenSpec = null;
	private InnerMergeData data;
	private Complex tenAfterComplex = null; // 商品编码 // @jve:decl-index=0:
	private Unit tenAfterUnit = null; // 申报计量单位 // @jve:decl-index=0:

	private JButton btnTenComplex = null;

	private JButton btnTenUnit = null;

	private JButton btnUpdataTenAndFour = null;

	private JLabel jLabel41 = null;

	private JCustomFormattedTextField tfTenSeqNum = null;

	private JLabel jLabel42 = null;

	private JTextField tfFourComplex = null;

	private JButton btnFourComplex = null;

	private JLabel jLabel71 = null;

	private JTextField tfFourName = null;

	private JLabel jLabel411 = null;

	private JFormattedTextField tfFourSeqNum = null;

	private JPanel jPanel11 = null;

	private JPanel jPanel12 = null;

	private List passlist = null; // @jve:decl-index=0:

	private JLabel jLabel = null;
	private Boolean isOk = false; // @jve:decl-index=0:
	private List[] returnList = null;

	public List[] getReturnList() {
		return returnList;
	}

	public Boolean getIsOk() {
		return isOk;
	}

	public void setIsOk(Boolean isOk) {
		this.isOk = isOk;
	}

	public List getPasslist() {
		return passlist;
	}

	public void setPasslist(List passlist) {
		this.passlist = passlist;
	}

	/**
	 * This is the default constructor
	 */
	public DgFactoryCustoms() {
		super();
		initialize();
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
	}

	public void setVisible(boolean f) {
		if (f) {
			if (tableModel.getCurrentRow() != null) {
				data = (InnerMergeData) tableModel.getCurrentRow();
			}
			showData();
		}
		super.setVisible(f);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJPanel2());
		this.setSize(530, 341);
		this.setTitle("报关/工厂资料编辑");

	}

	/**
	 * 
	 * This method initializes btnClose
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.setBounds(302, 271, 78, 23);
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgFactoryCustoms.this.dispose();
					isOk = false;
				}
			});

		}
		return btnClose;
	}

	/**
	 * 显示数据
	 */
	private void showData() {
		if (data == null) {
			return;
		}
		this.tenAfterComplex = data.getHsAfterComplex();
		this.tenAfterUnit = data.getHsAfterMemoUnit();

		tfTenSeqNum.setText(data.getHsAfterTenMemoNo() == null ? "" : String
				.valueOf(data.getHsAfterTenMemoNo()));
		tfTenComplex.setText(data.getHsAfterComplex().getCode());
		tfTenName.setText(data.getHsAfterMaterielTenName());
		tfTenUnit.setText(data.getHsAfterMemoUnit() == null ? "" : data
				.getHsAfterMemoUnit().getName());
		tfTenSpec.setText(data.getHsAfterMaterielTenSpec());

		tfFourSeqNum.setText(data.getHsFourNo() == null ? "" : String
				.valueOf(data.getHsFourNo()));
		tfFourComplex.setText(data.getHsFourCode());
		tfFourName.setText(data.getHsFourMaterielName());

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
	 * @return Returns the materialManageAction.
	 */
	public MaterialManageAction getCommonBaseCodeObj() {
		return materialManageAction;
	}

	/**
	 * @param materialManageAction
	 *            The materialManageAction to set.
	 */
	public void setCommonBaseCodeObj(MaterialManageAction materialManageAction) {
		this.materialManageAction = materialManageAction;
	}

	/**
	 * This method initializes tfTenComplex
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTenComplex() {
		if (tfTenComplex == null) {
			tfTenComplex = new JTextField();
			tfTenComplex.setEditable(false);
			tfTenComplex.setBounds(new Rectangle(74, 63, 122, 22));
		}
		return tfTenComplex;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField22() {
		if (tfTenUnit == null) {
			tfTenUnit = new JTextField();
			tfTenUnit.setEditable(false);
			tfTenUnit.setBounds(new Rectangle(74, 98, 122, 22));
		}
		return tfTenUnit;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField32() {
		if (tfTenName == null) {
			tfTenName = new JTextField();
			// jTextField3.setEditable(false);
			tfTenName.setBounds(new Rectangle(294, 63, 173, 22));
		}
		return tfTenName;
	}

	/**
	 * This method initializes tfTenSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTenSpec() {
		if (tfTenSpec == null) {
			tfTenSpec = new JTextField();
			// jTextField4.setEditable(false);
			tfTenSpec.setBounds(new Rectangle(294, 99, 173, 22));
		}
		return tfTenSpec;
	}

	/**
	 * This method initializes btnTenComplex
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnTenComplex() {
		if (btnTenComplex == null) {
			btnTenComplex = new JButton();
			btnTenComplex.setText("...");
			btnTenComplex.setBounds(new Rectangle(198, 62, 27, 22));
			btnTenComplex
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Complex complex = (Complex) CommonQuery
									.getInstance().getComplex();
							if (complex != null) {
								tenAfterComplex = complex;
								tfTenComplex.setText(complex.getCode());
								tfTenName.setText(complex.getName());
							}
						}
					});
		}
		return btnTenComplex;
	}

	/**
	 * This method initializes btnTenUnit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnTenUnit() {
		if (btnTenUnit == null) {
			btnTenUnit = new JButton();
			btnTenUnit.setText("...");
			btnTenUnit.setBounds(new Rectangle(198, 98, 27, 22));
			btnTenUnit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Unit c = (Unit) CommonQuery.getInstance().getCustomUnit();
					if (c != null) {
						tenAfterUnit = c;
						tfTenUnit.setText(c.getName());
					}
				}
			});
		}
		return btnTenUnit;
	}

	/**
	 * This method initializes btnUpdataTenAndFour
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUpdataTenAndFour() {
		if (btnUpdataTenAndFour == null) {
			btnUpdataTenAndFour = new JButton();
			btnUpdataTenAndFour.setBounds(new Rectangle(156, 272, 133, 23));
			btnUpdataTenAndFour.setText("开始更新报关资料");
			btnUpdataTenAndFour
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tfTenComplex.getText() == null
									|| "".equals(tfTenComplex.getText().trim())) {
								JOptionPane.showMessageDialog(
										DgFactoryCustoms.this,
										"四码报关编码不能为空", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							} else {
								String FoureComplex = getComplexFourNo(tfTenComplex
										.getText().trim());
								if (!tfFourComplex.getText().trim().equals(
										FoureComplex)) {
									JOptionPane.showMessageDialog(
											DgFactoryCustoms.this,
											"四码报关编码与十码报关编码的前四位不一致", "提示",
											JOptionPane.INFORMATION_MESSAGE);
									return;
								}
							}

							if (JOptionPane.showConfirmDialog(
									DgFactoryCustoms.this, "确定要更新报关资料吗？", "提示",
									JOptionPane.YES_NO_OPTION) == 1) {
								return;
							}
							new startUpdata().start();
						}
					});
		}
		return btnUpdataTenAndFour;
	}

	/**
	 * 开始执行更新
	 * 
	 * @author Administrator
	 * 
	 */
	class startUpdata extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog(DgFactoryCustoms.this);
				CommonProgress.setMessage("系统正更新数据，请稍后...");
				returnList = commonBaseCodeAction.updateTenAndFourCustoms(
						new Request(CommonVars.getCurrUser()), passlist, data
								.getImrType(), tenAfterComplex, tfTenName
								.getText().trim(), tfTenSpec.getText().trim(),
						tenAfterUnit, Integer.valueOf(tfTenSeqNum.getText()),
						tfFourComplex.getText(), tfFourName.getText().trim(),
						Integer.valueOf(tfFourSeqNum.getText()));
				CommonProgress.closeProgressDialog();
				isOk = true;
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgFactoryCustoms.this, "更新数据失败：！"
						+ e.getMessage(), "提示", 2);
			} finally {
				DgFactoryCustoms.this.dispose();
			}
		}
	}

	/**
	 * This method initializes tfTenSeqNum
	 * 
	 * @return javax.swing.JTextField
	 */
	private JCustomFormattedTextField getTfTenSeqNum() {
		if (tfTenSeqNum == null) {
			tfTenSeqNum = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfTenSeqNum.setBounds(new Rectangle(74, 32, 122, 22));
			tfTenSeqNum.addPropertyChangeListener("value",
					new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent evt) {
							setTenData();
						}

					});
		}
		return tfTenSeqNum;
	}

	private void setData(boolean isFalse) {
		if (tfTenSeqNum.getText().trim().equals(
				data.getHsAfterTenMemoNo().toString())) {
			return;
		}
		tfFourSeqNum.setEditable(isFalse);
		tfFourName.setEditable(isFalse);
		btnFourComplex.setEnabled(isFalse);

		tfTenName.setEditable(isFalse);
		tfTenSpec.setEditable(isFalse);
		btnTenComplex.setEnabled(isFalse);
		btnTenUnit.setEnabled(isFalse);

	}

	private void setTenData() {
		setData(true);
		List list = commonBaseCodeAction.findInnerMergeDataByTenNo(new Request(
				CommonVars.getCurrUser()), data.getImrType(), tfTenSeqNum
				.getText().trim());
		if (list != null && list.size() > 0) { // 已经存在新的序号则更新到旧的
			InnerMergeData obj = (InnerMergeData) list.get(0);
			tenAfterComplex = obj.getHsAfterComplex();
			tfTenComplex.setText(obj.getHsAfterComplex().getCode());
			tfTenName.setText(obj.getHsAfterMaterielTenName());
			tfTenSpec.setText(obj.getHsAfterMaterielTenSpec());
			tenAfterUnit = obj.getHsAfterMemoUnit();
			tfTenUnit.setText(obj.getHsAfterMemoUnit() == null ? "" : obj
					.getHsAfterMemoUnit().getName());
			tfTenSeqNum.setText(String.valueOf(obj.getHsAfterTenMemoNo()));

			tfFourComplex.setText(obj.getHsFourCode());
			tfFourName.setText(obj.getHsFourMaterielName());
			tfFourSeqNum.setText(obj.getHsFourNo() == null ? "" : String
					.valueOf(obj.getHsFourNo()));
			setData(false);
		} else {
			tfTenComplex.setText("");
			tfTenName.setText("");
			tfTenSpec.setText("");
			tfTenUnit.setText("");

			tfFourComplex.setText("");
			tfFourName.setText("");
			tfFourSeqNum.setText("");
		}
	}

	/**
	 * This method initializes tfFourComplex
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFourComplex() {
		if (tfFourComplex == null) {
			tfFourComplex = new JTextField();
			tfFourComplex.setEditable(false);
			tfFourComplex.setBounds(new Rectangle(75, 54, 122, 22));
		}
		return tfFourComplex;
	}

	/**
	 * This method initializes btnFourComplex
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFourComplex() {
		if (btnFourComplex == null) {
			btnFourComplex = new JButton();
			btnFourComplex.setText("...");
			btnFourComplex.setBounds(new Rectangle(199, 55, 28, 20));
			btnFourComplex
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Complex complex = (Complex) CommonQuery
									.getInstance().getComplex();
							if (complex != null) {
								tfFourComplex.setText(getComplexFourNo(complex
										.getCode()));
							}
						}
					});
		}
		return btnFourComplex;
	}

	private String getComplexFourNo(String tenComplex) {
		if (tenComplex == null) {
			return "";
		}
		return tenComplex.substring(0, 4);
	}

	/**
	 * This method initializes tfFourName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFourName() {
		if (tfFourName == null) {
			tfFourName = new JTextField();
			tfFourName.setBounds(new Rectangle(298, 53, 168, 22));
		}
		return tfFourName;
	}

	/**
	 * This method initializes tfFourSeqNum
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getTfFourSeqNum() {
		if (tfFourSeqNum == null) {
			tfFourSeqNum = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfFourSeqNum.setBounds(new Rectangle(75, 28, 121, 22));
			tfFourSeqNum.addPropertyChangeListener("value",
					new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent evt) {
							setFourData();

						}

					});
		}
		return tfFourSeqNum;
	}

	private void setData1() {
		if (tfFourSeqNum.getText().trim().equals(data.getHsFourNo().toString())) {
			return;
		}
		tfFourName.setEditable(false);
		btnFourComplex.setEnabled(false);

	}

	private void setFourData() {
		tfFourName.setEditable(true);
		btnFourComplex.setEnabled(true);
		List list = commonBaseCodeAction.findInnerMergeDataByFourNo(
				new Request(CommonVars.getCurrUser()), data.getImrType(),
				tfFourSeqNum.getText().trim());
		if (list != null && list.size() > 0) { // 已经存在新的序号则更新到旧的
			InnerMergeData obj = (InnerMergeData) list.get(0);
			tfFourComplex.setText(obj.getHsFourCode());
			tfFourName.setText(obj.getHsFourMaterielName());
			tfFourSeqNum.setText(obj.getHsFourNo() == null ? "" : String
					.valueOf(obj.getHsFourNo()));
			setData1();
		} else {
			tfFourName.setText("");
			tfFourComplex.setText("");
		}
	}

	/**
	 * This method initializes jPanel11
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel11() {
		if (jPanel11 == null) {
			jPanel11 = new JPanel();
			jPanel11.setLayout(null);
			jPanel11.setBounds(new Rectangle(24, 35, 480, 135));
			jPanel11.setBorder(BorderFactory.createTitledBorder(null,
					"\u5341\u7801\u62a5\u5173\u5546\u54c1\u8d44\u6599",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), Color.red));
			jPanel11.add(jLabel4, null);
			jPanel11.add(getTfTenComplex(), null);
			jPanel11.add(jLabel6, null);
			jPanel11.add(getJTextField22(), null);
			jPanel11.add(jLabel7, null);
			jPanel11.add(jLabel11, null);
			jPanel11.add(getJTextField32(), null);
			jPanel11.add(getTfTenSpec(), null);
			jPanel11.add(getBtnTenComplex(), null);
			jPanel11.add(getBtnTenUnit(), null);
			jPanel11.add(getTfTenSeqNum(), null);
			jPanel11.add(jLabel41, null);
		}
		return jPanel11;
	}

	/**
	 * This method initializes jPanel12
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel12() {
		if (jPanel12 == null) {
			jPanel12 = new JPanel();
			jPanel12.setLayout(null);
			jPanel12.setBounds(new Rectangle(20, 176, 484, 88));
			jPanel12.setBorder(BorderFactory.createTitledBorder(null,
					"\u56db\u7801\u62a5\u5173\u8d44\u6599",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), Color.red));
			jPanel12.add(jLabel42, null);
			jPanel12.add(getTfFourComplex(), null);
			jPanel12.add(getBtnFourComplex(), null);
			jPanel12.add(jLabel71, null);
			jPanel12.add(getTfFourName(), null);
			jPanel12.add(jLabel411, null);
			jPanel12.add(getTfFourSeqNum(), null);
		}
		return jPanel12;
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(38, 6, 419, 20));
			jLabel.setText("说明：输入十码或四码的序号如果存在则自动带出报关资料");
			jLabel411 = new JLabel();
			jLabel411.setText("四码序号");
			jLabel411.setBounds(new Rectangle(20, 28, 48, 18));
			jLabel71 = new JLabel();
			jLabel71.setText("报关名称");
			jLabel71.setBounds(new Rectangle(240, 55, 48, 18));
			jLabel42 = new JLabel();
			jLabel42.setText("\u62a5\u5173\u7f16\u7801");
			jLabel42.setBounds(new Rectangle(21, 54, 48, 18));
			jLabel41 = new JLabel();
			jLabel41.setText("十码序号");
			jLabel41.setBounds(new Rectangle(13, 31, 48, 18));
			jLabel11 = new JLabel();
			jLabel7 = new JLabel();
			jLabel6 = new JLabel();
			jLabel4 = new JLabel();

			java.awt.GridBagConstraints gridBagConstraints10 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints9 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints8 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints7 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints6 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints5 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints4 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints3 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.gridwidth = 2;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints2.ipadx = 106;
			gridBagConstraints2.insets = new java.awt.Insets(25, 1, 5, 44);
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.ipadx = 9;
			gridBagConstraints3.ipady = 5;
			gridBagConstraints3.insets = new java.awt.Insets(5, 42, 6, 25);
			gridBagConstraints4.gridx = 1;
			gridBagConstraints4.gridy = 1;
			gridBagConstraints4.gridwidth = 2;
			gridBagConstraints4.weightx = 1.0;
			gridBagConstraints4.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints4.ipadx = 105;
			gridBagConstraints4.insets = new java.awt.Insets(7, 1, 5, 45);
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 3;
			gridBagConstraints5.ipadx = 13;
			gridBagConstraints5.ipady = -2;
			gridBagConstraints5.insets = new java.awt.Insets(8, 48, 18, 5);
			gridBagConstraints6.gridx = 1;
			gridBagConstraints6.gridy = 3;
			gridBagConstraints6.ipadx = 12;
			gridBagConstraints6.ipady = -3;
			gridBagConstraints6.insets = new java.awt.Insets(9, 37, 18, 0);
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.gridy = 2;
			gridBagConstraints7.ipadx = 10;
			gridBagConstraints7.ipady = 4;
			gridBagConstraints7.insets = new java.awt.Insets(6, 41, 8, 1);
			gridBagConstraints8.gridx = 1;
			gridBagConstraints8.gridy = 2;
			gridBagConstraints8.gridwidth = 2;
			gridBagConstraints8.weightx = 1.0;
			gridBagConstraints8.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints8.ipadx = 104;
			gridBagConstraints8.insets = new java.awt.Insets(5, 2, 9, 45);
			gridBagConstraints9.gridx = 2;
			gridBagConstraints9.gridy = 2;
			gridBagConstraints9.ipadx = -18;
			gridBagConstraints9.ipady = -7;
			gridBagConstraints9.insets = new java.awt.Insets(5, 1, 10, 22);
			gridBagConstraints10.gridx = 1;
			gridBagConstraints10.gridy = 0;
			gridBagConstraints10.gridwidth = 2;
			gridBagConstraints10.weightx = 1.0;
			gridBagConstraints10.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints10.ipadx = 104;
			gridBagConstraints10.insets = new java.awt.Insets(23, 1, 7, 46);

			jLabel4.setText("报关编码");
			jLabel4.setBounds(new Rectangle(13, 62, 48, 18));
			jLabel6.setText("报关单位");
			jLabel6.setBounds(new Rectangle(13, 99, 48, 18));
			jLabel7.setText("报关名称");
			jLabel7.setBounds(new Rectangle(234, 64, 48, 18));
			jLabel11.setText("报关规格");
			jLabel11.setBounds(new Rectangle(232, 103, 48, 18));
			jPanel2.add(getBtnClose(), null);
			jPanel2.add(getBtnUpdataTenAndFour(), null);
			jPanel2.add(getJPanel11(), null);
			jPanel2.add(getJPanel12(), null);
			jPanel2.add(jLabel, null);
		}
		return jPanel2;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
