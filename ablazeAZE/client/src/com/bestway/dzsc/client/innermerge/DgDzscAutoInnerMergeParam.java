package com.bestway.dzsc.client.innermerge;

import java.awt.Dialog;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.bestway.dzsc.innermerge.entity.TempDzscAutoInnerMergeParam;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgDzscAutoInnerMergeParam extends JDialogBase {

	private JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JCheckBox jCheckBox = null;

	private JCheckBox jCheckBox1 = null;

	private JCheckBox jCheckBox2 = null;

	private JCheckBox jCheckBox3 = null;

	private JCheckBox jCheckBox4 = null;

	private JCheckBox jCheckBox5 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;
	
	private boolean isOK=false;

	private TempDzscAutoInnerMergeParam autoInnerMergeParam = null;

	public boolean isOK() {
		return isOK;
	}

	public TempDzscAutoInnerMergeParam getAutoInnerMergeParam() {
		return autoInnerMergeParam;
	}

	public void setAutoInnerMergeParam(TempDzscAutoInnerMergeParam innerMergeParam) {
		this.autoInnerMergeParam = innerMergeParam;
	}

	public DgDzscAutoInnerMergeParam() {
		super();
		initialize();
	}

	public DgDzscAutoInnerMergeParam(boolean isModal) {
		super(isModal);
		initialize();
	}

	public DgDzscAutoInnerMergeParam(JFrame owner, boolean isModal) {
		super(owner, isModal);
		initialize();
	}

	public DgDzscAutoInnerMergeParam(Dialog owner, boolean isModal) {
		super(owner, isModal);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(372, 288));
		this.setTitle("自动归并参数");
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());

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
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJPanel1(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(new java.awt.Rectangle(39, 16, 295, 91));
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					"归并前到10码的归并",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jPanel.add(getJCheckBox(), null);
			jPanel.add(getJCheckBox1(), null);
			jPanel.add(getJCheckBox2(), null);
		}
		return jPanel;
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
			jPanel1.setBounds(new java.awt.Rectangle(37, 128, 298, 92));
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "10码到4码的归并",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jPanel1.add(getJCheckBox3(), null);
			jPanel1.add(getJCheckBox4(), null);
			jPanel1.add(getJCheckBox5(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new java.awt.Rectangle(53, 20, 178, 20));
			jCheckBox.setSelected(true);
			jCheckBox.setText("商品编码相同");
		}
		return jCheckBox;
	}

	/**
	 * This method initializes jCheckBox1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox1() {
		if (jCheckBox1 == null) {
			jCheckBox1 = new JCheckBox();
			jCheckBox1.setBounds(new java.awt.Rectangle(53, 42, 178, 18));
			jCheckBox1.setSelected(false);
			jCheckBox1.setText("物料名称相同");
		}
		return jCheckBox1;
	}

	/**
	 * This method initializes jCheckBox2
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox2() {
		if (jCheckBox2 == null) {
			jCheckBox2 = new JCheckBox();
			jCheckBox2.setBounds(new java.awt.Rectangle(53, 63, 178, 19));
			jCheckBox2.setSelected(true);
			jCheckBox2.setText("物料单位相同");
		}
		return jCheckBox2;
	}

	/**
	 * This method initializes jCheckBox3
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox3() {
		if (jCheckBox3 == null) {
			jCheckBox3 = new JCheckBox();
			jCheckBox3.setBounds(new java.awt.Rectangle(60,19,190,20));
			jCheckBox3.setSelected(true);
			jCheckBox3.setText("商品编码相同");
		}
		return jCheckBox3;
	}

	/**
	 * This method initializes jCheckBox4
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox4() {
		if (jCheckBox4 == null) {
			jCheckBox4 = new JCheckBox();
			jCheckBox4.setBounds(new java.awt.Rectangle(60,43,190,19));
			jCheckBox4.setSelected(false);
			jCheckBox4.setText("商品名称相同");
		}
		return jCheckBox4;
	}

	/**
	 * This method initializes jCheckBox5
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox5() {
		if (jCheckBox5 == null) {
			jCheckBox5 = new JCheckBox();
			jCheckBox5.setBounds(new java.awt.Rectangle(60, 65, 190, 21));
			jCheckBox5.setSelected(true);
			jCheckBox5.setText("商品单位相同");
		}
		return jCheckBox5;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new java.awt.Rectangle(182, 227, 63, 24));
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isOK=true;
					autoInnerMergeParam=new TempDzscAutoInnerMergeParam();
					autoInnerMergeParam.setMaterialComplexSame(jCheckBox.isSelected());
					autoInnerMergeParam.setMaterialNameSame(jCheckBox1.isSelected());
					autoInnerMergeParam.setMaterialUnitSame(jCheckBox2.isSelected());
					autoInnerMergeParam.setTenComplexSame(jCheckBox3.isSelected());
					autoInnerMergeParam.setTenNameSame(jCheckBox4.isSelected());
					autoInnerMergeParam.setTenUnitSame(jCheckBox5.isSelected());
					dispose();
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new java.awt.Rectangle(270, 227, 63, 24));
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

} // @jve:decl-index=0:visual-constraint="19,13"
