package com.bestway.common.client.erpbillcenter;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.Hashtable;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.bestway.bcus.cas.authority.CasParameterAction;
import com.bestway.bcus.cas.parameterset.entity.BillCorrespondingControl;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
/**
 * 刘民添加部分注释 修改时间： 2008-10-25
 * 
 * @author ?// change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates 单据导入参数设置
 * 
 */
public class DgBillTxtImportSet extends JDialogBase {

	private JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JComboBox cbbScmCoc = null;

	private JLabel jLabel1 = null;

	private JComboBox cbbWarehouse = null;

	private JLabel jLabel2 = null;

	private JComboBox cbbValidDate = null;

	private JButton btnSaveSet = null;

	private JButton btnExit = null;

	public Hashtable hs = null; // @jve:decl-index=0:

	private boolean isOk = true;

	private JPanel jPanel = null;

	private JRadioButton rbMaterials = null;

	private JRadioButton rbProduct = null;

	private JRadioButton rbEquipment = null;

	private JRadioButton rbHalfProduct = null;

	private JRadioButton rbImperfections = null;

	private JRadioButton rbScrap = null;

	private ButtonGroup group = new ButtonGroup();  //  @jve:decl-index=0:

	private JLabel jLabel3 = null;

	private JCheckBox cbIsImportCustomNo = null;

	private CasParameterAction casParameterAction = null;
	/** 单据对应控制对象 */
	private BillCorrespondingControl billCorrespondingControl = null;

	private JCheckBox cbIsTitle = null;

	private JCheckBox cbIsCurrentDate = null;

	private JCheckBox cbIsValid = null;

	private JRadioButton rbImperfections2 = null;

	private JRadioButton rbImperfections3 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	/**
	 * This is the default constructor
	 */
	public DgBillTxtImportSet() {
		super();
		casParameterAction = (CasParameterAction) CommonVars
				.getApplicationContext().getBean("casParameterAction");
		billCorrespondingControl = CasCommonVars.getBillCorrespondingControl();
		initialize();
		group.add(rbMaterials);
		group.add(rbProduct);
		group.add(rbEquipment);
		group.add(rbHalfProduct);
		group.add(rbImperfections);
		group.add(rbScrap);
		group.add(rbImperfections2);
		group.add(rbImperfections3);
		cbIsImportCustomNo
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						if (cbIsImportCustomNo.isSelected()) {
							if (billCorrespondingControl == null
									|| billCorrespondingControl
											.getIsHandContrl() == null
									|| (!billCorrespondingControl
											.getIsHandContrl())) {
								JOptionPane
										.showMessageDialog(
												DgBillTxtImportSet.this,
												"管理报表-参数设置-单据对应控制,\n对应关系不是手工控制，因此该选项不能生效！",
												"提示！",
												JOptionPane.WARNING_MESSAGE);
								cbIsImportCustomNo.setSelected(false);
							}
						}
					}
				});
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(507, 296);
		this.setContentPane(getJContentPane());
		this.setTitle("单据导入参数设置");
		initComponent();
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {
				if (hs != null && hs.size() > 0) {
					cbbScmCoc.setSelectedItem(hs.get(1));
					cbbWarehouse.setSelectedItem(hs.get(2));
					cbbValidDate.setSelectedItem(hs.get(3));
					cbIsImportCustomNo
							.setSelected(hs.get("isInputCustoms") == null ? false
									: Boolean.parseBoolean(hs.get(
											"isInputCustoms").toString()));
					cbIsTitle
							.setSelected(hs.get("isTitle") == null ? false
									: Boolean.parseBoolean(hs.get("isTitle")
											.toString()));
					cbIsCurrentDate
							.setSelected(hs.get("isCurrentDate") == null ? false
									: Boolean.parseBoolean(hs.get(
											"isCurrentDate").toString()));
					cbIsValid
					.setSelected(hs.get("isValid") == null ? false
							: Boolean.parseBoolean(hs.get(
									"isValid").toString()));
					String type = (String) hs.get(5);
					if (type != null && type.equals("料件")) {
						rbMaterials.setSelected(true);
					} else if (type != null && type.equals("成品")) {
						rbProduct.setSelected(true);
					} else if (type != null && type.equals("设备")) {
						rbEquipment.setSelected(true);
					} else if (type != null && type.equals("半成品")) {
						rbHalfProduct.setSelected(true);
					} else if (type != null && type.equals("残次品")) {
						rbImperfections.setSelected(true);
					} else if (type != null && type.equals("边角料")) {
						rbScrap.setSelected(true);
					}
				}
			}
		});
	}

	/**
	 * This method initializes component
	 */

	private void initComponent() {

		if (hs != null && hs.size() > 0) {
			cbbScmCoc.setSelectedItem(hs.get(1));
			cbbWarehouse.setSelectedItem(hs.get(2));
			cbbValidDate.setSelectedItem(hs.get(3));
			cbIsImportCustomNo
					.setSelected(hs.get("isInputCustoms") == null ? false
							: Boolean.parseBoolean(hs.get("isInputCustoms")
									.toString()));
			cbIsTitle.setSelected(hs.get("isTitle") == null ? false : Boolean
					.parseBoolean(hs.get("isTitle").toString()));
			cbIsCurrentDate.setSelected(hs.get("isCurrentDate") == null ? false
					: Boolean.parseBoolean(hs.get("isCurrentDate").toString()));
			cbIsValid.setSelected(hs.get("isValid") == null ? false
					: Boolean.parseBoolean(hs.get("isValid").toString()));
			String type = (String) hs.get(5);
			if (type != null && type.equals("料件")) {
				rbMaterials.setSelected(true);
			} else if (type != null && type.equals("成品")) {
				rbProduct.setSelected(true);
			} else if (type != null && type.equals("设备")) {
				rbEquipment.setSelected(true);
			} else if (type != null && type.equals("半成品")) {
				rbHalfProduct.setSelected(true);
			} else if (type != null && type.equals("残次品")) {
				rbImperfections.setSelected(true);
			} else if (type != null && type.equals("边角料")) {
				rbScrap.setSelected(true);
			}
		}

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(395, 197, 64, 22));
			jLabel5.setForeground(Color.blue);
			jLabel5.setText("自动识别。");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(321, 197, 74, 22));
			jLabel4.setForeground(Color.red);
			jLabel4.setText("工厂单据名称");
			jLabel3 = new JLabel();
			jLabel3.setForeground(Color.blue);
			jLabel3.setBounds(new Rectangle(31, 197, 289, 22));
			jLabel3.setText("注意：单据类别，请在单据管理里面设置，系统会根据");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(35, 95, 58, 25));
			jLabel2.setText("生效日期");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(35, 65, 32, 25));
			jLabel1.setText("仓库");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(35, 30, 61, 25));
			jLabel.setText("客户供应商");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getCbbScmCoc(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getCbbWarehouse(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getCbbValidDate(), null);
			jContentPane.add(getBtnSaveSet(), null);
			jContentPane.add(getBtnExit(), null);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getCbIsImportCustomNo(), null);
			jContentPane.add(getCbIsTitle(), null);
			jContentPane.add(getCbIsCurrentDate(), null);
			jContentPane.add(getCbIsValid(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel5, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes cbbScmCoc
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbScmCoc() {
		if (cbbScmCoc == null) {
			cbbScmCoc = new JComboBox();
			cbbScmCoc.addItem("");
			cbbScmCoc.addItem("代码");
			cbbScmCoc.addItem("名称全称");
			cbbScmCoc.addItem("名称简称");
			cbbScmCoc.setSelectedIndex(2);
			cbbScmCoc.setBounds(new Rectangle(99, 30, 139, 25));
		}
		return cbbScmCoc;
	}

	/**
	 * This method initializes cbbWarehouse
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbWarehouse() {
		if (cbbWarehouse == null) {
			cbbWarehouse = new JComboBox();
			cbbWarehouse.addItem("");
			cbbWarehouse.addItem("代码");
			cbbWarehouse.addItem("名称");
			cbbWarehouse.setSelectedIndex(2);
			cbbWarehouse.setBounds(new Rectangle(99, 64, 139, 25));
		}
		return cbbWarehouse;
	}

	/**
	 * This method initializes cbbValidDate
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbValidDate() {
		if (cbbValidDate == null) {
			cbbValidDate = new JComboBox();
			cbbValidDate.addItem("");
			cbbValidDate.addItem("yyyy-MM-dd");
			cbbValidDate.addItem("yyyy/MM/dd");
			cbbValidDate.addItem("yyyyMMdd");
			cbbValidDate.setSelectedIndex(1);
			cbbValidDate.setBounds(new Rectangle(99, 93, 139, 25));
		}
		return cbbValidDate;
	}

	/**
	 * This method initializes btnSaveSet
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSaveSet() {
		if (btnSaveSet == null) {
			btnSaveSet = new JButton();
			btnSaveSet.setBounds(new Rectangle(178, 227, 82, 23));
			btnSaveSet.setPreferredSize(new Dimension(85, 28));
			btnSaveSet.setText("保存设置");
			btnSaveSet.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (hs == null) {
						hs = new Hashtable();
					} else {
						hs.clear();
					}
					// 1.客户供应商
					String scmcoc = cbbScmCoc.getSelectedItem() == null ? ""
							: cbbScmCoc.getSelectedItem().toString();
					hs.put(1, scmcoc);
					// 2.仓库
					String ck = cbbWarehouse.getSelectedItem() == null ? ""
							: cbbWarehouse.getSelectedItem().toString();
					hs.put(2, ck);
					// 3.生效日期
					String isvdate = cbbValidDate.getSelectedItem() == null ? ""
							: cbbValidDate.getSelectedItem().toString();
					hs.put(3, isvdate);
					// 5.物料类型
					if (rbMaterials.isSelected()) {
						hs.put(5, "料件");
					} else if (rbProduct.isSelected()) {
						hs.put(5, "成品");
					} else if (rbEquipment.isSelected()) {
						hs.put(5, "设备");
					} else if (rbHalfProduct.isSelected()) {
						hs.put(5, "半成品");
					} else if (rbImperfections.isSelected()) {
						hs.put(5, "残次品(料件)");
					} else if (rbImperfections2.isSelected()) {
						hs.put(5, "残次品(成品)");
					} else if (rbImperfections3.isSelected()) {
						hs.put(5, "残次品(半成品)");
					} else if (rbScrap.isSelected()) {
						hs.put(5, "边角料");
					}
					hs.put("isInputCustoms", getCbIsImportCustomNo()
							.isSelected());
					hs.put("isTitle", getCbIsTitle().isSelected());
					hs.put("isCurrentDate", getCbIsCurrentDate().isSelected());
					hs.put("isValid", getCbIsValid().isSelected());
					setOk(true);
					DgBillTxtImportSet.this.dispose();
				}
			});
		}
		return btnSaveSet;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setBounds(new Rectangle(271, 227, 80, 21));
			btnExit.setPreferredSize(new Dimension(85, 28));
			btnExit.setText("退出");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setOk(false);
					DgBillTxtImportSet.this.dispose();
				}
			});
		}
		return btnExit;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	public void setHs(Hashtable hs) {
		this.hs = hs;
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
			jPanel.setBounds(new Rectangle(241, 29, 235, 95));
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					"物料类别",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jPanel.add(getRbMaterials(), null);
			jPanel.add(getRbProduct(), null);
			jPanel.add(getRbEquipment(), null);
			jPanel.add(getRbHalfProduct(), null);
			jPanel.add(getRbImperfections(), null);
			jPanel.add(getRbScrap(), null);
			jPanel.add(getRbImperfections2(), null);
			jPanel.add(getRbImperfections3(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes rbMaterials
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbMaterials() {
		if (rbMaterials == null) {
			rbMaterials = new JRadioButton();
			rbMaterials.setBounds(new Rectangle(21, 14, 59, 21));
			rbMaterials.setText("料件");
		}
		return rbMaterials;
	}

	/**
	 * This method initializes rbProduct
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbProduct() {
		if (rbProduct == null) {
			rbProduct = new JRadioButton();
			rbProduct.setBounds(new Rectangle(21, 32, 60, 21));
			rbProduct.setText("成品");
		}
		return rbProduct;
	}

	/**
	 * This method initializes rbEquipment
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbEquipment() {
		if (rbEquipment == null) {
			rbEquipment = new JRadioButton();
			rbEquipment.setBounds(new Rectangle(21, 68, 62, 21));
			rbEquipment.setText("设备");
		}
		return rbEquipment;
	}

	/**
	 * This method initializes rbHalfProduct
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbHalfProduct() {
		if (rbHalfProduct == null) {
			rbHalfProduct = new JRadioButton();
			rbHalfProduct.setBounds(new Rectangle(21, 50, 68, 21));
			rbHalfProduct.setText("半成品");
		}
		return rbHalfProduct;
	}

	/**
	 * This method initializes rbImperfections
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbImperfections() {
		if (rbImperfections == null) {
			rbImperfections = new JRadioButton();
			rbImperfections.setBounds(new Rectangle(93, 14, 126, 21));
			rbImperfections.setText("残次品(料件方式)");
		}
		return rbImperfections;
	}

	/**
	 * This method initializes rbScrap
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbScrap() {
		if (rbScrap == null) {
			rbScrap = new JRadioButton();
			rbScrap.setBounds(new Rectangle(93, 68, 68, 21));
			rbScrap.setText("边角料");
		}
		return rbScrap;
	}

	/**
	 * This method initializes cbIsImportCustomNo
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsImportCustomNo() {
		if (cbIsImportCustomNo == null) {
			cbIsImportCustomNo = new JCheckBox();
			cbIsImportCustomNo.setBounds(new Rectangle(32, 122, 147, 19));
			cbIsImportCustomNo.setText("导入对应报关单号");

		}
		return cbIsImportCustomNo;
	}

	/**
	 * This method initializes cbIsTitle
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsTitle() {
		if (cbIsTitle == null) {
			cbIsTitle = new JCheckBox();
			cbIsTitle.setBounds(new Rectangle(204, 122, 145, 21));
			cbIsTitle.setText("第一行为标题行");
		}
		return cbIsTitle;
	}

	/**
	 * This method initializes cbIsCurrentDate
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsCurrentDate() {
		if (cbIsCurrentDate == null) {
			cbIsCurrentDate = new JCheckBox();
			cbIsCurrentDate.setBounds(new Rectangle(32, 143, 260, 24));
			cbIsCurrentDate.setText("如果生效日期为空,以今天为生效日期");
		}
		return cbIsCurrentDate;
	}

	/**
	 * This method initializes cbIsValid	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbIsValid() {
		if (cbIsValid == null) {
			cbIsValid = new JCheckBox();
			cbIsValid.setBounds(new Rectangle(32, 169, 128, 23));
			cbIsValid.setText("导入生效");
		}
		return cbIsValid;
	}

	/**
	 * This method initializes rbImperfections2	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbImperfections2() {
		if (rbImperfections2 == null) {
			rbImperfections2 = new JRadioButton();
			rbImperfections2.setBounds(new Rectangle(93, 32, 117, 21));
			rbImperfections2.setText("残次品(成品方式)");
		}
		return rbImperfections2;
	}

	/**
	 * This method initializes rbImperfections3	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbImperfections3() {
		if (rbImperfections3 == null) {
			rbImperfections3 = new JRadioButton();
			rbImperfections3.setBounds(new Rectangle(93, 50, 133, 21));
			rbImperfections3.setText("残次品(半成品方式)");
		}
		return rbImperfections3;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10" 
