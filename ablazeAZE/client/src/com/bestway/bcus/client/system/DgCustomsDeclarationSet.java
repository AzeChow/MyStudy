/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.system;

import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.countrycode.PortLin;
import com.bestway.bcus.custombase.entity.countrycode.PreDock;
import com.bestway.bcus.custombase.entity.parametercode.BalanceMode;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.custombase.entity.parametercode.Uses;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.CustomsDeclarationSet;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.ui.winuicontrol.JDialogBase;

import java.awt.Dimension;
import java.awt.Rectangle;

/**
 * @author Administratorf
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class DgCustomsDeclarationSet extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel jPanel2 = null;

	private JTextField jtname = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JTableListModel tableModel = null;


	private boolean isAdd = true;


	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel7 = null;

	private JLabel jLabel8 = null;

	private JLabel jLabel9 = null;

	private JLabel jLabel10 = null;

	private JLabel jLabel11 = null;

	private JLabel jLabel12 = null;

	private JLabel jLabel13 = null;

	private JLabel jLabel14 = null;

	private JLabel jLabel15 = null;

	private JLabel jLabel16 = null;

	private JComboBox jComboBox = null;

	private JComboBox jComboBox1 = null;

	private JTextField jTextField = null;

	private JComboBox jComboBox2 = null;

	private JComboBox jComboBox3 = null;

	private JTextField jTextField1 = null;

	private JComboBox jComboBox4 = null;

	private JComboBox jComboBox5 = null;

	private JComboBox jComboBox6 = null;

	private JComboBox jComboBox7 = null;

	private JComboBox jComboBox8 = null;

	private JComboBox jComboBox9 = null;

	private JComboBox jComboBox10 = null;

	private JComboBox jComboBox11 = null;

	private JComboBox jComboBox12 = null;

	private JComboBox jComboBox13 = null;

	private JComboBox jComboBox14 = null;

	private JLabel jLabel17 = null;

	private JLabel jLabel18 = null;

	private JLabel jLabel19 = null;

	private JPanel jPanel = null;

	private JLabel jLabel20 = null;

	private JComboBox jComboBox15 = null;
	private SystemAction systemAction = null;
	protected CustomBaseAction customBaseAction = null;
	private CustomsDeclarationSet obj = null;

	private JLabel jLabel21 = null;

	private JComboBox jComboBox16 = null;

	private JLabel jLabel22 = null;

	private JComboBox cbbWrapType = null;
	
	/**
	 * This is the default constructor
	 */
	public DgCustomsDeclarationSet() {
		super();
		systemAction = (SystemAction) CommonVars.getApplicationContext()
		.getBean("systemAction");
		customBaseAction = (CustomBaseAction) CommonVars.getApplicationContext()
		.getBean("customBaseAction");
		initialize();
		initUI();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJPanel2());
		this.setSize(734, 470);
		this.setTitle("报关单参数设置编辑");

		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {

				if (isAdd == false) {
					if (tableModel.getCurrentRow() != null) {
						obj = (CustomsDeclarationSet) tableModel.getCurrentRow();
						fillWindow();						
					}					
				} else {
					
				}
			}
		});
	}
    //填充动态ComboBox
	public void intoComboBox(List list, JComboBox jComboBox) {
		jComboBox.setModel(new DefaultComboBoxModel(list.toArray()));
		jComboBox.setRenderer(CustomBaseRender.getInstance().getRender("code",
				"name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(jComboBox,
				"code", "name");
		jComboBox.setSelectedIndex(-1);
	}
	
	
	
	private void initUI() {
		List list = null;
		list = CustomBaseList.getInstance().getCustoms();
		intoComboBox(list, jComboBox);
		intoComboBox(list, jComboBox11);
		list = CustomBaseList.getInstance().getTrades();
		intoComboBox(list, jComboBox3);
		list = CustomBaseList.getInstance().getLevyKind();
		intoComboBox(list, jComboBox1);
		list = CustomBaseList.getInstance().getCountrys();
		intoComboBox(list, jComboBox5);
		intoComboBox(list, jComboBox12);
		list = CustomBaseList.getInstance().getTransacs();
		intoComboBox(list, jComboBox6);
		list = CustomBaseList.getInstance().getPreDocks();
		intoComboBox(list, jComboBox9);
		list = CustomBaseList.getInstance().getWraps();
		intoComboBox(list, jComboBox8);
		list = CustomBaseList.getInstance().getTransfs();
		intoComboBox(list, jComboBox7);
		list = CustomBaseList.getInstance().getDistrict();
		intoComboBox(list, jComboBox10);
		list = CustomBaseList.getInstance().getUses();
		intoComboBox(list, jComboBox13);
		list = CustomBaseList.getInstance().getLevymode();
		intoComboBox(list, jComboBox14);
		list = CustomBaseList.getInstance().getPortLins();
		intoComboBox(list, jComboBox4);
		list = CustomBaseList.getInstance().getBalanceMode();
		intoComboBox(list, jComboBox2);
		list = CustomBaseList.getInstance().getCurrs();
		intoComboBox(list, jComboBox16);
		
        //初始化进口类型
		this.jComboBox15.removeAllItems();
		this.jComboBox15.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.DIRECT_IMPORT).toString(), "料件进口"));
		this.jComboBox15.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.TRANSFER_FACTORY_IMPORT).toString(), "料件转厂"));
		this.jComboBox15.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.BACK_FACTORY_REWORK).toString(), "退厂返工"));
		this.jComboBox15.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.REMAIN_FORWARD_IMPORT).toString(), "余料结转进口"));
		this.jComboBox15.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.MATERIAL_DOMESTIC_SALES).toString(), ImpExpType
				.getImpExpTypeDesc(ImpExpType.MATERIAL_DOMESTIC_SALES)));
		this.jComboBox15.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.DIRECT_EXPORT).toString(), "成品出口"));
		this.jComboBox15.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.TRANSFER_FACTORY_EXPORT).toString(), "转厂出口"));
		this.jComboBox15.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.BACK_MATERIEL_EXPORT).toString(), "退料出口"));
		this.jComboBox15.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.REWORK_EXPORT).toString(), "返工复出"));
		this.jComboBox15.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.REMAIN_FORWARD_EXPORT).toString(), "余料结转"));
		
		this.jComboBox15.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.GENERAL_TRADE_IMPORT).toString(), ImpExpType
				.getImpExpTypeDesc(ImpExpType.GENERAL_TRADE_IMPORT)));
		this.jComboBox15.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.REMIAN_MATERIAL_BACK_PORT).toString(), ImpExpType
				.getImpExpTypeDesc(ImpExpType.REMIAN_MATERIAL_BACK_PORT)));
		this.jComboBox15.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES).toString(), ImpExpType
				.getImpExpTypeDesc(ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES)));
		this.jComboBox15.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.GENERAL_TRADE_EXPORT).toString(), ImpExpType
				.getImpExpTypeDesc(ImpExpType.GENERAL_TRADE_EXPORT)));
		this.jComboBox15.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.EQUIPMENT_IMPORT).toString(), ImpExpType
				.getImpExpTypeDesc(ImpExpType.EQUIPMENT_IMPORT)));
		this.jComboBox15.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.BACK_PORT_REPAIR).toString(), ImpExpType
				.getImpExpTypeDesc(ImpExpType.BACK_PORT_REPAIR)));
		this.jComboBox15.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.EQUIPMENT_BACK_PORT).toString(), ImpExpType
				.getImpExpTypeDesc(ImpExpType.EQUIPMENT_BACK_PORT)));
		
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox15);
		this.jComboBox15.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		jComboBox15.setSelectedIndex(-1);
		
		List listwarp = customBaseAction.findWrap();
		this.cbbWrapType.setModel(new DefaultComboBoxModel(listwarp.toArray()));
		this.cbbWrapType.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbWrapType, "code", "name");
		cbbWrapType.setSelectedItem(null);
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel22 = new JLabel();
			jLabel22.setBounds(new Rectangle(27, 349, 81, 18));
			jLabel22.setText("包装种类");
			jLabel21 = new JLabel();
			jLabel21.setBounds(new java.awt.Rectangle(26,249,83,25));
			jLabel21.setText("币制");
			jLabel16 = new JLabel();
			jLabel16.setBounds(new java.awt.Rectangle(27,201,71,21));
			jLabel16.setText("境内目的地");
			jLabel20 = new JLabel();
			jLabel20.setBounds(new java.awt.Rectangle(28,42,69,18));
			jLabel20.setText("进出口类型");
			jLabel19 = new JLabel();
			jLabel19.setBounds(new java.awt.Rectangle(27,314,81,18));
			jLabel19.setText("目的国(单体)");
			jLabel18 = new JLabel();
			jLabel18.setBounds(new java.awt.Rectangle(261,132,71,19));
			jLabel18.setText("运抵国(单头)");
			jLabel17 = new JLabel();
			jLabel17.setBounds(new java.awt.Rectangle(27,223,84,18));
			jLabel17.setText("或境内货源地");
			jLabel15 = new JLabel();
			jLabel15.setBounds(new java.awt.Rectangle(28,161,63,20));
			jLabel15.setText("运输方式");
			jLabel14 = new JLabel();
			jLabel14.setBounds(new java.awt.Rectangle(262,213,66,18));
			jLabel14.setText("进出口岸");
			jLabel13 = new JLabel();
			jLabel13.setBounds(new java.awt.Rectangle(261,161,61,21));
			jLabel13.setText("包装种类");
			jLabel12 = new JLabel();
			jLabel12.setBounds(new java.awt.Rectangle(28,115,43,18));
			jLabel12.setText("装货港");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new java.awt.Rectangle(490,210,37,18));
			jLabel11.setText("码头");
			jLabel10 = new JLabel();
			jLabel10.setBounds(new java.awt.Rectangle(261,114,66,18));
			jLabel10.setText("起运国");
			jLabel9 = new JLabel();
			jLabel9.setBounds(new java.awt.Rectangle(264,302,37,18));
			jLabel9.setText("用途");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new java.awt.Rectangle(490,118,63,18));
			jLabel8.setText("运输工具");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new java.awt.Rectangle(261,77,55,21));
			jLabel7.setText("贸易方式");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new java.awt.Rectangle(28,77,59,17));
			jLabel6.setText("结汇方式");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(490,79,63,18));
			jLabel5.setText("提运单号");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(490,42,54,18));
			jLabel4.setText("征免性质");
			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jLabel.setText("报送海关");
			jLabel.setBounds(261, 42, 57, 23);
			jLabel2.setText("减免方式");
			jLabel2.setBounds(492, 303, 55, 21);
			jLabel1.setText("成交方式");
			jLabel1.setBounds(490, 162, 55, 22);
			jLabel3.setBounds(28, 291, 56, 20);
			jLabel3.setText("原产国");
			jPanel2.add(jLabel, null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(jLabel6, null);
			jPanel2.add(jLabel7, null);
			jPanel2.add(jLabel8, null);
			jPanel2.add(jLabel9, null);
			jPanel2.add(jLabel10, null);
			jPanel2.add(jLabel11, null);
			jPanel2.add(jLabel12, null);
			jPanel2.add(jLabel13, null);
			jPanel2.add(jLabel14, null);
			jPanel2.add(jLabel15, null);
			jPanel2.add(jLabel16, null);
			jPanel2.add(jLabel17, null);
			jPanel2.add(jLabel18, null);
			jPanel2.add(jLabel19, null);
			jPanel2.add(getJPanel(), null);
			jPanel2.add(jLabel20, null);
			jPanel2.add(getJComboBox15(), null);
			jPanel2.add(getJComboBox(), null);
			jPanel2.add(getJComboBox1(), null);
			jPanel2.add(getJTextField(), null);
			jPanel2.add(getJComboBox2(), null);
			jPanel2.add(getJComboBox3(), null);
			jPanel2.add(getJTextField1(), null);
			jPanel2.add(getJComboBox4(), null);
			jPanel2.add(getJComboBox5(), null);
			jPanel2.add(getJComboBox6(), null);
			jPanel2.add(getJComboBox7(), null);
			jPanel2.add(getJComboBox8(), null);
			jPanel2.add(getJComboBox9(), null);
			jPanel2.add(getJComboBox10(), null);
			jPanel2.add(getJComboBox11(), null);
			jPanel2.add(getJComboBox12(), null);
			jPanel2.add(getJComboBox13(), null);
			jPanel2.add(getJComboBox14(), null);
			jPanel2.add(jLabel21, null);
			jPanel2.add(getJComboBox16(), null);
			jPanel2.add(jLabel22, null);
			jPanel2.add(getCbbWrapType(), null);
		}
		return jPanel2;
	}

	/**
	 * 
	 * This method initializes jButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("确定(S)");
			jButton.setMnemonic(java.awt.event.KeyEvent.VK_S);
			jButton.setActionCommand("确定(S)");
			jButton.setBounds(234, 388, 76, 23);
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkNull()) {
						return;
					}
					
					if (isAdd == true) {
						addData();
						DgCustomsDeclarationSet.this.dispose();
					} else {
						modifyData();
						DgCustomsDeclarationSet.this.dispose();
					}

				}
			});

		}
		return jButton;
	}

	/**
	 * 
	 * This method initializes jButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("取消(x)");
			jButton1.setMnemonic(java.awt.event.KeyEvent.VK_X);
			jButton1.setActionCommand("取消(X)");
			jButton1.setBounds(400, 388, 76, 23);
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgCustomsDeclarationSet.this.dispose();

				}

			});

		}
		return jButton1;
	}

	private void fillWindow() {
        //进口类型
		if (obj.getImpType() != null) {
			jComboBox15.setSelectedIndex(ItemProperty.getIndexByCode(obj
					.getImpType().toString(), jComboBox15));
		} else {
			jComboBox15.setSelectedIndex(-1);
		}
		jComboBox.setSelectedItem(obj.getDeclarationCustoms());
		jComboBox1.setSelectedItem(obj.getCustomlevyKind());
		jComboBox2.setSelectedItem(obj.getBalanceMode());
		jComboBox3.setSelectedItem(obj.getTradeMode());
		jTextField.setText(obj.getBillOfLading());
		jTextField1.setText(obj.getConveyance());
		jComboBox5.setSelectedItem(obj.getCoun());
		jComboBox4.setSelectedItem(obj.getPort());
		jComboBox7.setSelectedItem(obj.getTransferMode());
		jComboBox8.setSelectedItem(obj.getWrapType());
		jComboBox6.setSelectedItem(obj.getTransac());
		jComboBox9.setSelectedItem(obj.getPredock());
		jComboBox11.setSelectedItem(obj.getCustoms());
		
		jComboBox10.setSelectedItem(obj.getDistrict());
		jComboBox12.setSelectedItem(obj.getCountry());
		jComboBox13.setSelectedItem(obj.getUses());
		jComboBox14.setSelectedItem(obj.getLevyMode());
		jComboBox16.setSelectedItem(obj.getCurr());
		cbbWrapType.setSelectedItem(obj.getCommWrapType());
	}

	private void fillCalUnit(CustomsDeclarationSet obj) {
		obj.setBalanceMode((BalanceMode) jComboBox2.getSelectedItem());
		obj.setBillOfLading(jTextField.getText());
		obj.setCompany(CommonVars.getCurrUser().getCompany());
		obj.setConveyance(jTextField1.getText());
		obj.setCoun((Country)jComboBox5.getSelectedItem());
		obj.setCountry((Country) jComboBox12.getSelectedItem());
		obj.setCustomlevyKind((LevyKind) jComboBox1.getSelectedItem());
		obj.setCustoms((Customs) jComboBox11.getSelectedItem());
		obj.setDeclarationCustoms((Customs) jComboBox.getSelectedItem());
		obj.setDistrict((District) jComboBox10.getSelectedItem());
		if (jComboBox15.getSelectedItem() != null) {
			obj.setImpType(Integer.valueOf(((ItemProperty) jComboBox15
					.getSelectedItem()).getCode()));
		} else {
			obj.setImpType(null);
		}
		obj.setLevyMode((LevyMode) jComboBox14.getSelectedItem());
		obj.setPort((PortLin) jComboBox4.getSelectedItem());
		obj.setPredock((PreDock) jComboBox9.getSelectedItem());
		obj.setTradeMode((Trade) jComboBox3.getSelectedItem());
		obj.setTransac((Transac) jComboBox6.getSelectedItem());
		obj.setTransferMode((Transf) jComboBox7.getSelectedItem());
		obj.setUses((Uses) jComboBox13.getSelectedItem());
		obj.setWrapType((Wrap) jComboBox8.getSelectedItem());
		obj.setCurr((Curr) jComboBox16.getSelectedItem());
		obj.setCommWrapType((Wrap) cbbWrapType.getSelectedItem());
	}
	

	private boolean checkNull() {
		if (this.jComboBox15.getSelectedItem() == null || "".equals(jComboBox15.getSelectedItem())) {
			JOptionPane.showMessageDialog(this, "进出口类型不能为空,请输入!", "提示!", 1);
			return true;
		}		
		return false;
	}

	private void addData() {
		CustomsDeclarationSet obj = new CustomsDeclarationSet();
		fillCalUnit(obj);

		obj = systemAction.saveCustomsSet(
				new Request(CommonVars.getCurrUser()), obj);
		tableModel.addRow(obj);
	}

	private void modifyData() {
		fillCalUnit(obj);
		obj = systemAction.saveCustomsSet(
				new Request(CommonVars.getCurrUser()), obj);
		tableModel.updateRow(obj);

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
	 * @return Returns the isAdd.
	 */
	public boolean isAdd() {
		return isAdd;
	}

	/**
	 * @param isAdd
	 *            The isAdd to set.
	 */
	public void setIsAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}

	/**
	 * @return Returns the jtname.
	 */
	public JTextField getJtname() {
		return jtname;
	}


	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new java.awt.Rectangle(334,42,136,24));
		}
		return jComboBox;
	}

	/**
	 * This method initializes jComboBox1	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setBounds(new java.awt.Rectangle(564,42,129,24));
		}
		return jComboBox1;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new java.awt.Rectangle(564,80,128,24));
		}
		return jTextField;
	}

	/**
	 * This method initializes jComboBox2	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox2() {
		if (jComboBox2 == null) {
			jComboBox2 = new JComboBox();
			jComboBox2.setBounds(new java.awt.Rectangle(112,75,132,24));
		}
		return jComboBox2;
	}

	/**
	 * This method initializes jComboBox3	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox3() {
		if (jComboBox3 == null) {
			jComboBox3 = new JComboBox();
			jComboBox3.setBounds(new java.awt.Rectangle(334,77,135,24));
		}
		return jComboBox3;
	}

	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(new java.awt.Rectangle(564,118,128,24));
		}
		return jTextField1;
	}

	/**
	 * This method initializes jComboBox4	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox4() {
		if (jComboBox4 == null) {
			jComboBox4 = new JComboBox();
			jComboBox4.setBounds(new java.awt.Rectangle(112,113,132,24));
		}
		return jComboBox4;
	}

	/**
	 * This method initializes jComboBox5	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox5() {
		if (jComboBox5 == null) {
			jComboBox5 = new JComboBox();
			jComboBox5.setBounds(new java.awt.Rectangle(334,115,135,24));
		}
		return jComboBox5;
	}

	/**
	 * This method initializes jComboBox6	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox6() {
		if (jComboBox6 == null) {
			jComboBox6 = new JComboBox();
			jComboBox6.setBounds(new java.awt.Rectangle(564,162,128,24));
		}
		return jComboBox6;
	}

	/**
	 * This method initializes jComboBox7	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox7() {
		if (jComboBox7 == null) {
			jComboBox7 = new JComboBox();
			jComboBox7.setBounds(new java.awt.Rectangle(112,159,132,24));
		}
		return jComboBox7;
	}

	/**
	 * This method initializes jComboBox8	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox8() {
		if (jComboBox8 == null) {
			jComboBox8 = new JComboBox();
			jComboBox8.setBounds(new java.awt.Rectangle(334,161,135,24));
		}
		return jComboBox8;
	}

	/**
	 * This method initializes jComboBox9	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox9() {
		if (jComboBox9 == null) {
			jComboBox9 = new JComboBox();
			jComboBox9.setBounds(new java.awt.Rectangle(564,210,128,24));
		}
		return jComboBox9;
	}

	/**
	 * This method initializes jComboBox10	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox10() {
		if (jComboBox10 == null) {
			jComboBox10 = new JComboBox();
			jComboBox10.setBounds(new java.awt.Rectangle(112,209,132,24));
		}
		return jComboBox10;
	}

	/**
	 * This method initializes jComboBox11	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox11() {
		if (jComboBox11 == null) {
			jComboBox11 = new JComboBox();
			jComboBox11.setBounds(new java.awt.Rectangle(335,208,135,24));
		}
		return jComboBox11;
	}

	/**
	 * This method initializes jComboBox12	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox12() {
		if (jComboBox12 == null) {
			jComboBox12 = new JComboBox();
			jComboBox12.setBounds(new java.awt.Rectangle(112,299,132,24));
		}
		return jComboBox12;
	}

	/**
	 * This method initializes jComboBox13	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox13() {
		if (jComboBox13 == null) {
			jComboBox13 = new JComboBox();
			jComboBox13.setBounds(new java.awt.Rectangle(337,300,135,24));
		}
		return jComboBox13;
	}

	/**
	 * This method initializes jComboBox14	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox14() {
		if (jComboBox14 == null) {
			jComboBox14 = new JComboBox();
			jComboBox14.setBounds(new java.awt.Rectangle(565,299,128,24));
		}
		return jComboBox14;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setBounds(new java.awt.Rectangle(27,287,665,2));
			jPanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.activeCaption,5));
		}
		return jPanel;
	}

	/**
	 * This method initializes jComboBox15	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox15() {
		if (jComboBox15 == null) {
			jComboBox15 = new JComboBox();
			jComboBox15.setBounds(new java.awt.Rectangle(112,42,132,24));
			jComboBox15.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ItemProperty item = (ItemProperty) jComboBox15.getSelectedItem();
					if(item != null) {
						if(Integer.valueOf(item.getCode()) == ImpExpType.EQUIPMENT_IMPORT
								|| Integer.valueOf(item.getCode()) == ImpExpType.BACK_PORT_REPAIR
								|| Integer.valueOf(item.getCode()) == ImpExpType.EQUIPMENT_BACK_PORT) {
							cbbWrapType.setEnabled(false);
						} else {
							cbbWrapType.setEnabled(true);
						}
					}					
				}
			});
		}
		return jComboBox15;
	}

	/**
	 * This method initializes jComboBox16	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox16() {
		if (jComboBox16 == null) {
			jComboBox16 = new JComboBox();
			jComboBox16.setBounds(new java.awt.Rectangle(111,248,133,24));
		}
		return jComboBox16;
	}

	/**
	 * This method initializes cbbWrapType	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbWrapType() {
		if (cbbWrapType == null) {
			cbbWrapType = new JComboBox();
			cbbWrapType.setBounds(new Rectangle(112, 345, 132,24));
		}
		return cbbWrapType;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
