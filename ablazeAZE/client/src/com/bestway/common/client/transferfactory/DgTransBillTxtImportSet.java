package com.bestway.common.client.transferfactory;

import java.awt.Rectangle;
import java.util.Hashtable;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.bestway.bcus.cas.authority.CasParameterAction;
import com.bestway.bcus.cas.parameterset.entity.BillCorrespondingControl;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.ui.winuicontrol.JDialogBase;
/**
 * 刘民添加部分注释 修改时间： 2008-10-25
 * 
 * @author ?// change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates 单据导入参数设置
 * 
 */
public class DgTransBillTxtImportSet extends JDialogBase {

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

	private ButtonGroup group = new ButtonGroup();  //  @jve:decl-index=0:

	private CasParameterAction casParameterAction = null;
	/** 单据对应控制对象 */
	private BillCorrespondingControl billCorrespondingControl = null;

	private JCheckBox cbIsTitle = null;

	private JLabel lbCountry = null;

	private JComboBox cbbCountry = null;

	private JLabel lbCurr = null;

	private JComboBox cbbCurr = null;

	/**
	 * This is the default constructor
	 */
	public DgTransBillTxtImportSet() {
		super();
		casParameterAction = (CasParameterAction) CommonVars
				.getApplicationContext().getBean("casParameterAction");
		billCorrespondingControl = CasCommonVars.getBillCorrespondingControl();
		initialize();
		group.add(rbMaterials);
		group.add(rbProduct);
		
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(499, 271);
		this.setContentPane(getJContentPane());
		this.setTitle("转厂导入参数设置");
		initComponent();
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {
				if (hs != null && hs.size() > 0) {
					cbbScmCoc.setSelectedItem(hs.get(1));
					cbbWarehouse.setSelectedItem(hs.get(2));
					cbbCountry.setSelectedItem(hs.get(3));
					cbbCurr.setSelectedItem(hs.get(4));
					cbbValidDate.setSelectedItem(hs.get(6));
					
					cbIsTitle
							.setSelected(hs.get("isTitle") == null ? false
									: Boolean.parseBoolean(hs.get("isTitle")
											.toString()));
					String type = (String) hs.get(5);
					if (type != null && type.equals("进货")) {
						rbMaterials.setSelected(true);
					} else if (type != null && type.equals("出货")) {
						rbProduct.setSelected(true);
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
			
			cbIsTitle.setSelected(hs.get("isTitle") == null ? false : Boolean
					.parseBoolean(hs.get("isTitle").toString()));
			String type = (String) hs.get(5);
			if (type != null && type.equals("料件")) {
				rbMaterials.setSelected(true);
			} else if (type != null && type.equals("成品")) {
				rbProduct.setSelected(true);
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
			lbCurr = new JLabel();
			lbCurr.setBounds(new Rectangle(34, 139, 37, 18));
			lbCurr.setText("币制");
			lbCountry = new JLabel();
			lbCountry.setBounds(new Rectangle(36, 105, 63, 18));
			lbCountry.setText("产销国");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(248, 99, 58, 25));
			jLabel2.setText("生效日期");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(35, 65, 33, 25));
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
			jContentPane.add(getCbIsTitle(), null);
			jContentPane.add(lbCountry, null);
			jContentPane.add(getCbbCountry(), null);
			jContentPane.add(lbCurr, null);
			jContentPane.add(getCbbCurr(), null);
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
			cbbScmCoc.addItem("名称");
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
			cbbValidDate.setBounds(new Rectangle(310, 99, 139, 25));
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
			btnSaveSet.setBounds(new Rectangle(103, 186, 103, 25));
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
					// 3.产销国
					String country  = cbbCountry.getSelectedItem() == null ? ""
							: cbbCountry.getSelectedItem().toString();
					hs.put(3, country);
					// 4.币制
					String curr = cbbCurr.getSelectedItem() == null ?""
							: cbbCurr.getSelectedItem().toString();
					hs.put(4, curr);
					// 5.物料类型
					if (rbMaterials.isSelected()) {
						hs.put(5, "进货");
					} else if (rbProduct.isSelected()) {
						hs.put(5, "出货");
					} 
					// 6.生效日期
					String isvdate = cbbValidDate.getSelectedItem() == null ? ""
							: cbbValidDate.getSelectedItem().toString();
					hs.put(6, isvdate);
					// 第一行为标题行
					hs.put("isTitle", getCbIsTitle().isSelected());
					
					setOk(true);
					DgTransBillTxtImportSet.this.dispose();
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
			btnExit.setBounds(new Rectangle(270, 186, 81, 25));
			btnExit.setText("退出");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setOk(false);
					DgTransBillTxtImportSet.this.dispose();
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
			jPanel.setBounds(new Rectangle(241, 29, 238, 61));
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					"物料类别",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jPanel.add(getRbMaterials(), null);
			jPanel.add(getRbProduct(), null);
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
			rbMaterials.setBounds(new Rectangle(33, 25, 59, 21));
			rbMaterials.setText("进货");
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
			rbProduct.setBounds(new Rectangle(139, 24, 60, 21));
			rbProduct.setText("出货");
		}
		return rbProduct;
	}

	/**
	 * This method initializes cbIsTitle
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsTitle() {
		if (cbIsTitle == null) {
			cbIsTitle = new JCheckBox();
			cbIsTitle.setBounds(new Rectangle(251, 139, 145, 21));
			cbIsTitle.setText("第一行为标题行");
		}
		return cbIsTitle;
	}

	/**
	 * This method initializes cbbCountry	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbCountry() {
		if (cbbCountry == null) {
			cbbCountry = new JComboBox();
			cbbCountry.addItem("");
			cbbCountry.addItem("代码");
			cbbCountry.addItem("名称");
			cbbCountry.setSelectedIndex(2);
			cbbCountry.setBounds(new Rectangle(98, 101, 138, 24));
		}
		return cbbCountry;
	}

	/**
	 * This method initializes cbbCurr	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbCurr() {
		if (cbbCurr == null) {
			cbbCurr = new JComboBox();
			cbbCurr.addItem("");
			cbbCurr.addItem("代码");
			cbbCurr.addItem("名称");
			cbbCurr.setSelectedIndex(2);
			cbbCurr.setBounds(new Rectangle(99, 137, 138, 25));
		}
		return cbbCurr;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10" 
