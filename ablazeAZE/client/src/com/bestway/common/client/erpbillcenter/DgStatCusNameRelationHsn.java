/**
 * 刘民添加部分注释
 * 
 * 修改时间： 2008-9-20
 * 
 * @author 余鹏// change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 * 
 */
package com.bestway.common.client.erpbillcenter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.FactoryAndFactualCustomsRalation;
import com.bestway.bcus.cas.entity.StatCusNameRelationHsn;
import com.bestway.bcus.client.cas.CasQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgStatCusNameRelationHsn extends JDialogBase {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	/**
	 * 物料类型
	 */
	private String materielType = "";
	/**
	 * 设置浏览状态 默认为浏览状态
	 */
	private int dataState = DataState.BROWSE;
	/**
	 * 工厂和实际客户对应表
	 */
	private FactoryAndFactualCustomsRalation f = null;  //  @jve:decl-index=0:
	
	/**
	 * 海关对帐表
	 */
	private StatCusNameRelationHsn tmp = null;  //  @jve:decl-index=0:
	private CasAction casAction = null;
	private CustomBaseAction customBaseAction = null;
	private AttributiveCellTableModel tableModel = null;
	/**
	 * 是否关闭
	 */
	private boolean isClose = false;
	
	/*
	 * 修改时是否更新相关单据
	 * wss
	 */
	
	private boolean isUpdateDetail = false;
	
	/*
	 * 修改时改变了报关编码
	 * wss
	 */
	private boolean isChangeHsCode = false;
	
	/*
	 * 所修改对应关系的物料资料
	 * 料号、工厂名称、工厂规格、手册号
	 * wss
	 */
	private String[] oldMOldParameter = new String[4];
	
	/*
	 *修改前对应关系的报关资料
	 *报关编码、报关名称、报关规格、手册号
	 * wss
	 */
	private String[] oldCusOldParameter = new String[4];
	
	/*
	 * 如果修改了报关编码，相应的报关资料
	 * 报关编码、报关名称、报关规格、手册号
	 * wss
	 */
	private String[] newCusOldParameter = new String[4];
	
	/*
	 * 报关资料id号
	 * wss用于判断是否变换了报关资料
	 */
	private String oldId = null;
	
	/*
	 * 报关资料id号
	 * wss用于判断是否变换了报关资料
	 */
	private String newId = null;
	
	/**
	 * this method is default constructor
	 */
	public DgStatCusNameRelationHsn() {
		super();
		initialize();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(549, 394);
		this.setTitle("编辑对应关系数据");
		this.setContentPane(getJContentPane());
		initUIComponents();
	}

	/**
	 * 设置组件的可见性
	 */

	@Override
	public void setVisible(boolean b) {
		if (b) {
			showData();
			setState();
		}
		super.setVisible(b);
	}

	// /////////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////////////

	/**
	 * 初始化数据
	 */
	private void initUIComponents() {
		this.cbbCusUnit.setModel(CustomBaseModel.getInstance().getUnitModel());
		this.cbbCusUnit.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCusUnit);
		this.cbbCusUnit.setSelectedItem(null);
	}

	/**
	 * 显示数据
	 */
	private void showData() {
		if (dataState == DataState.ADD) {
			this.tfPtName.setText(m.getFactoryName());
			this.tfPtPart.setText(m.getPtNo());
			this.tfPtSpec.setText(m.getFactorySpec());
			this.tfPtUnit.setText(m.getCalUnit() != null ? m.getCalUnit()
					.getName() : "");
		} else {
			tmp = f.getStatCusNameRelationHsn();// 实际报关
			if (tmp != null) {
				tfHsComplex.setText(tmp.getComplex().getCode());
				tfHsName.setText(tmp.getCusName());
				tfHsSpec.setText(tmp.getCusSpec());
				cbbCusUnit.setSelectedItem(tmp.getCusUnit());
				tfEmsNo.setText(tmp.getEmsNo());
				tfSeqNum.setValue(tmp.getSeqNum() == null ? 0.0 : tmp
						.getSeqNum());
//				tfUnitConvert.setValue(tmp.getUnitConvert() == null ? 0.0 : tmp
//						.getUnitConvert());
				//wss:2010-06-22修改
				tfUnitConvert.setValue(f.getUnitConvert());
				
				//wss:2010.06.25 所修改对应关系的报关资料
				oldCusOldParameter[0] = tmp.getComplex().getCode();
				oldCusOldParameter[1] = tmp.getCusName();
				oldCusOldParameter[2] = tmp.getCusSpec();
				oldCusOldParameter[3] = tmp.getEmsNo();
				oldMOldParameter[3] = tmp.getEmsNo();
				System.out.println("****wss****oldMOldParameter[3]" 
													+ oldMOldParameter[3]);
				
				oldId = tmp.getId();
				newId = tmp.getId();
			}
			m = f.getMateriel();
			if (m != null) {
				this.tfPtName.setText(m.getFactoryName());
				this.tfPtPart.setText(m.getPtNo());
				this.tfPtSpec.setText(m.getFactorySpec());
				this.tfPtUnit.setText(m.getCalUnit() != null ? m.getCalUnit()
						.getName() : "");
				oldMOldParameter[0] = m.getPtNo();
				oldMOldParameter[1] = m.getFactoryName();
				oldMOldParameter[2] = m.getFactorySpec();

			}
		}
	}

	/**
	 * 填充数据
	 */
	private void fillData() {
		if (tmp == null) {
			tmp = new StatCusNameRelationHsn();
		}
		tmp.setMaterielType(materielType);
		String complexCode = tfHsComplex.getText();
		Complex complex = customBaseAction.findComplexByCode(complexCode);
		tmp.setComplex(complex);
		tmp.setCusName(tfHsName.getText());
		tmp.setCusSpec(tfHsSpec.getText());
		tmp.setCusUnit((Unit) cbbCusUnit.getSelectedItem());
		tmp.setEmsNo(this.tfEmsNo.getText());
		Integer seqNum = this.tfSeqNum.getValue() == null ? null : Double
				.valueOf(this.tfSeqNum.getValue().toString()).intValue();
		tmp.setSeqNum(seqNum);
		Double unitConvert = this.tfUnitConvert.getValue() == null ? null
				: Double.valueOf(this.tfUnitConvert.getValue().toString());
		String remark = tfRemark.getText();
		if (f != null) {
			tmp.setProjectType(f.getStatCusNameRelationHsn().getProjectType());
		}
		
		//这里已经更新的报关资料
		tmp = casAction.saveStatCusNameRelationHsn(new Request(CommonVars
				.getCurrUser()), tmp);
		if (f == null) {

			f = new FactoryAndFactualCustomsRalation();
		}
		f.setMateriel(m);
		f.setStatCusNameRelationHsn(tmp);
		f.setUnitConvert(unitConvert);
		if(remark!=null){
			f.setRemark(remark);
		}
	}

	/**
	 * 验证数据
	 */
	private boolean validateData() {
		String complexCode = tfHsComplex.getText();
		if ("".equals(complexCode)) {
			JOptionPane.showMessageDialog(this, "商品编码不可为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		Complex complex = customBaseAction.findComplexByCode(complexCode);
		if (complex == null) {
			JOptionPane.showMessageDialog(this, "商品编码不存在!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		String hsName = tfHsName.getText();
		if ("".equals(hsName)) {
			JOptionPane.showMessageDialog(this, "报关名称不可为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (cbbCusUnit.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "报关单位不可为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (tfPtUnit.getText() != null
				&& tfPtUnit.getText().equals(
						((Unit) getCbbCusUnit().getSelectedItem()).getName())
				&& !"1".equals(tfUnitConvert.getText())) {
			JOptionPane.showMessageDialog(this, "当报关单位=工厂单位时，折算比必须为‘1’！",
					"提示！", JOptionPane.WARNING_MESSAGE);
			getTfUnitConvert().requestFocus();
		}
		
		
		// String emsNo = tfEmsNo.getText();
		// if ("".equals(emsNo)) {
		// JOptionPane.showMessageDialog(this, "手册号不可为空!!", "提示",
		// JOptionPane.INFORMATION_MESSAGE);
		// return false;
		// }
		// Double value = (Double)tfSeqNum.getValue();
		// if (value == null) {
		// JOptionPane.showMessageDialog(this, "备案序号不可为空!!", "提示",
		// JOptionPane.INFORMATION_MESSAGE);
		// return false;
		// }
		return true;
	}

	/**
	 * 保存数据
	 */
	private void saveData() {
		fillData();
		
		if (dataState == DataState.ADD) {
			casAction.saveFactoryAndFactualCustomsRalation(new Request(CommonVars
				.getCurrUser()), false, f);
			tableModel.addRow(f);
		} else if (dataState == DataState.EDIT) {
//			casAction.saveFactoryAndFactualCustomsRalation(new Request(CommonVars
//				.getCurrUser()), f, materielType,false,true,(oldEmsNo == null? "":oldEmsNo));
			
			//报关资料更换了
			System.out.println("wss: oldId = " + oldId);
			System.out.println("wss: newId = " + newId);
			
			if(!oldId.equals(newId)){
				isChangeHsCode = true;
			}
			
			System.out.println("****wss****oldMOldParameter[3]" 
					+ oldMOldParameter[3]);
			
			casAction.modifyCustomForFactoryAndFactualCustomsRalation(
					new Request(CommonVars.getCurrUser()),
					 f, 
					 materielType,
					 isUpdateDetail,
					 isChangeHsCode,
					 oldMOldParameter,
					 oldCusOldParameter,
					 newCusOldParameter);

			tableModel.updateRow(f);
		}
	}

	/**
	 * 清空数据
	 * 
	 */
	private void emptyData() {

	}

	/**
	 * 设置数据状态
	 */
	private void setState() {
		this.btnEdit.setEnabled(this.dataState == DataState.BROWSE);
		this.btnCancel.setEnabled(this.dataState != DataState.BROWSE);
		this.btnSave.setEnabled(this.dataState != DataState.BROWSE);
		this.btnGoodsNoImport.setEnabled(this.dataState != DataState.BROWSE);

		//tfHsComplex.setEditable(dataState != DataState.BROWSE);
		tfHsName.setEditable(dataState != DataState.BROWSE);
		tfHsSpec.setEditable(dataState != DataState.BROWSE);
		cbbCusUnit.setEditable(dataState != DataState.BROWSE);
		tfEmsNo.setEditable(dataState != DataState.BROWSE);
		tfSeqNum.setEditable(dataState != DataState.BROWSE);
		
		tfUnitConvert.setEditable(dataState != DataState.BROWSE);

//		tfUnitConvert.setEditable(dataState == DataState.ADD);//wss2010.11.02修改

	}

	/**
	 * 初始化数据表
	 */
	private void initTable() {

	}

	/**
	 * 打印报表
	 */
	private void printReport() {

	}

	// /////////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////////////

	private Materiel m = null;  //  @jve:decl-index=0:
	private JToolBar jToolBar = null;
	private JButton btnEdit = null;
	private JButton btnSave = null;
	private JButton btnCancel = null;
	private JButton btnExit = null;
	private JPanel jPanel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JFormattedTextField tfUnitConvert = null;
	private JLabel jLComplex = null;
	private JLabel jLHsName = null;
	private JLabel jLHsSpec = null;
	private JTextField tfHsComplex = null;
	private JTextField tfHsSpec = null;
	private JTextField tfHsName = null;
	private JButton btnGoodsNoImport = null;
	private JTextField tfEmsNo = null;
	private JLabel jLabel26 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel8 = null;
	private JLabel jLabel11 = null;
	private JLabel jLabel9 = null;
	private JTextField tfPtSpec = null;
	private JTextField tfPtUnit = null;
	private JTextField tfPtName = null;
	private JTextField tfPtPart = null;
	private JLabel jLabel261 = null;
	private JComboBox cbbCusUnit = null;
	private JFormattedTextField tfSeqNum = null;
	private JFormattedTextField tfRemark;
	private JLabel label;

	public void setMateriel(Materiel m) {
		this.m = m;
	}

	public void setMaterielType(String materielType) {
		this.materielType = materielType;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
			jContentPane.add(getJPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnCancel());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("\u4fee\u6539");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * （wss）如果修改对应关系：
	 * 					1.未变换报关资料，只是修改了其中的属性
	 * 					2.变换了报关资料
	 * 							a.未修改属性
	 * 							b.修改了属性
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!validateData()) {
						return;
					}
					if(DataState.EDIT == dataState){
						if(!updateToDetail()){
							return;
						}
						
					}
					saveData();
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnSave;
	}
	
	/**
	 * 提示用户:是否更新到单据
	 * @return
	 * @author wss
	 */
	private boolean updateToDetail(){
		int result = JOptionPane.showConfirmDialog(this, 
				"你修改了对应关系信息，是否更新到相应的单据中?", 
				"提示!", JOptionPane.YES_NO_CANCEL_OPTION);
		if(JOptionPane.YES_OPTION == result){
			isUpdateDetail = true;
			return true;
		}
		if(JOptionPane.NO_OPTION == result){
			isUpdateDetail = false;
			return true;
		}
		return false;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("\u53d6\u6d88");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.BROWSE;
					showData();
					setState();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("\u5173\u95ed");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isClose = true;
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel261 = new JLabel();
			jLabel261.setBounds(new Rectangle(60, 201, 54, 22));
			jLabel261.setText("备案序号");
			jLabel261.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel9 = new JLabel();
			jLabel9.setBounds(new Rectangle(302, 78, 52, 21));
			jLabel9.setText("单        位");
			jLabel9.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(59, 78, 54, 21));
			jLabel11.setText("\u89c4\u683c\u578b\u53f7");
			jLabel11.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(302, 49, 52, 21));
			jLabel8.setText("名        称");
			jLabel8.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(59, 49, 54, 21));
			jLabel3.setText("\u5546\u54c1\u6599\u53f7");
			jLabel3.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel26 = new JLabel();
			jLabel26.setBounds(new Rectangle(59, 174, 54, 22));
			jLabel26.setText("手  册  号");
			jLabel26.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLHsSpec = new JLabel();
			jLHsSpec.setBounds(new Rectangle(59, 149, 54, 22));
			jLHsSpec.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLHsSpec.setText("\u62a5\u5173\u89c4\u683c");
			jLHsName = new JLabel();
			jLHsName.setBounds(new Rectangle(302, 121, 52, 22));
			jLHsName.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLHsName.setText("\u62a5\u5173\u54c1\u540d");
			jLComplex = new JLabel();
			jLComplex.setBounds(new Rectangle(59, 121, 54, 22));
			jLComplex.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLComplex.setText("\u5546\u54c1\u7f16\u7801");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(302, 174, 52, 22));
			jLabel2.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel2.setText("\u6298  \u7b97  \u6bd4");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(302, 149, 52, 22));
			jLabel1.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel1.setText("\u62a5\u5173\u5355\u4f4d");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(getTfUnitConvert(), null);
			jPanel.add(jLComplex, null);
			jPanel.add(jLHsName, null);
			jPanel.add(jLHsSpec, null);
			jPanel.add(getTfHsComplex(), null);
			jPanel.add(getTfHsSpec(), null);
			jPanel.add(getTfHsName(), null);
			jPanel.add(getBtnGoodsNoImport(), null);
			jPanel.add(getTfEmsNo(), null);
			jPanel.add(jLabel26, null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel8, null);
			jPanel.add(jLabel11, null);
			jPanel.add(jLabel9, null);
			jPanel.add(getTfPtSpec(), null);
			jPanel.add(getTfPtUnit(), null);
			jPanel.add(getTfPtName(), null);
			jPanel.add(getTfPtPart(), null);
			jPanel.add(jLabel261, null);
			jPanel.add(getCbbCusUnit(), null);
			jPanel.add(getTfSeqNum(), null);
			jPanel.add(getTfRemark());
			jPanel.add(getLabel());
		}
		return jPanel;
	}

	/**
	 * This method initializes tfUnitConvert
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfUnitConvert() {
		if (tfUnitConvert == null) {
			DecimalFormat decimalFormat1 = new DecimalFormat();
			decimalFormat1.setMaximumFractionDigits(9);
			NumberFormatter numberFormatter = new NumberFormatter();
			numberFormatter.setFormat(decimalFormat1);
			DefaultFormatterFactory defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(numberFormatter);
			defaultFormatterFactory.setDisplayFormatter(numberFormatter);
			tfUnitConvert = new JFormattedTextField();
			tfUnitConvert.setBounds(new Rectangle(357, 174, 141, 22));
			tfUnitConvert.setFormatterFactory(defaultFormatterFactory);
			
		}
		return tfUnitConvert;
	}

	/**
	 * This method initializes tfHsComplex
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfHsComplex() {
		if (tfHsComplex == null) {
			tfHsComplex = new JTextField();
			tfHsComplex.setBounds(new Rectangle(120, 121, 141, 22));
			//wss2010.06.25只能让他选
			tfHsComplex.setEditable(false);
		}
		return tfHsComplex;
	}

	/**
	 * This method initializes tfHsSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfHsSpec() {
		if (tfHsSpec == null) {
			tfHsSpec = new JTextField();
			tfHsSpec.setEditable(true);
			tfHsSpec.setSize(new Dimension(141, 22));
			tfHsSpec.setLocation(new Point(120, 148));
		}
		return tfHsSpec;
	}

	/**
	 * This method initializes tfHsName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfHsName() {
		if (tfHsName == null) {
			tfHsName = new JTextField();
			tfHsName.setBounds(new Rectangle(357, 121, 141, 22));
			tfHsName.setEditable(true);
		}
		return tfHsName;
	}

	/**
	 * This method initializes btnGoodsNoImport
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnGoodsNoImport() {
		if (btnGoodsNoImport == null) {
			btnGoodsNoImport = new JButton();
			btnGoodsNoImport.setBounds(new Rectangle(262, 121, 20, 20));
			btnGoodsNoImport.setText("...");
			btnGoodsNoImport
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							List list = CasQuery.getInstance()
									.getStatCusNameRelationHsn(false,
											materielType);
							if (list != null && list.size() > 0) {
								tmp = (StatCusNameRelationHsn) list.get(0);
								tfHsComplex.setText(tmp.getComplex().getCode());
								tfHsName.setText(tmp.getCusName());
								tfHsSpec.setText(tmp.getCusSpec());
								cbbCusUnit.setSelectedItem(tmp.getCusUnit());
								tfEmsNo.setText(tmp.getEmsNo());
								tfSeqNum.setValue(tmp.getSeqNum() == null ? 0.0
										: tmp.getSeqNum());
								tfUnitConvert
										.setValue(tmp.getUnitConvert() == null ? 0.0
												: tmp.getUnitConvert());
								//wss
								if(DataState.EDIT == dataState){
									newCusOldParameter[0] = tmp.getComplex().getCode();
									newCusOldParameter[1] = tmp.getCusName();
									newCusOldParameter[2] = tmp.getCusSpec();
									newCusOldParameter[3] = tmp.getEmsNo();
									newId = tmp.getId();
								}
								
								
							}
						}
					});
		}
		return btnGoodsNoImport;
	}

	/**
	 * This method initializes tfEmsNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setBounds(new Rectangle(120, 174, 141, 22));
			tfEmsNo.setEditable(true);
		}
		return tfEmsNo;
	}

	/**
	 * This method initializes tfPtSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtSpec() {
		if (tfPtSpec == null) {
			tfPtSpec = new JTextField();
			tfPtSpec.setBounds(new Rectangle(120, 78, 141, 21));
			tfPtSpec.setEditable(false);
		}
		return tfPtSpec;
	}

	/**
	 * This method initializes tfPtUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtUnit() {
		if (tfPtUnit == null) {
			tfPtUnit = new JTextField();
			tfPtUnit.setBounds(new Rectangle(357, 78, 141, 21));
			tfPtUnit.setEditable(false);
		}
		return tfPtUnit;
	}

	/**
	 * This method initializes tfPtName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtName() {
		if (tfPtName == null) {
			tfPtName = new JTextField();
			tfPtName.setBounds(new Rectangle(357, 49, 141, 21));
			tfPtName.setEditable(false);
		}
		return tfPtName;
	}

	/**
	 * This method initializes tfPtPart
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtPart() {
		if (tfPtPart == null) {
			tfPtPart = new JTextField();
			tfPtPart.setBounds(new Rectangle(120, 49, 141, 21));
			tfPtPart.setEditable(false);
		}
		return tfPtPart;
	}

	/**
	 * This method initializes cbbCusUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCusUnit() {
		if (cbbCusUnit == null) {
			cbbCusUnit = new JComboBox();
			cbbCusUnit.setBounds(new Rectangle(357, 148, 141, 23));
		}
		return cbbCusUnit;
	}

	/**
	 * This method initializes tfSeqNum
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfSeqNum() {
		if (tfSeqNum == null) {
			DecimalFormat decimalFormat11 = new DecimalFormat();
			decimalFormat11.setMaximumFractionDigits(6);
			NumberFormatter numberFormatter1 = new NumberFormatter();
			numberFormatter1.setFormat(decimalFormat11);
			DefaultFormatterFactory defaultFormatterFactory1 = new DefaultFormatterFactory();
			defaultFormatterFactory1.setDefaultFormatter(numberFormatter1);
			defaultFormatterFactory1.setDisplayFormatter(numberFormatter1);
			tfSeqNum = new JFormattedTextField();
			tfSeqNum.setBounds(new Rectangle(120, 201, 141, 22));
			tfSeqNum.setFormatterFactory(defaultFormatterFactory1);
		}
		return tfSeqNum;
	}

	public void setF(FactoryAndFactualCustomsRalation f) {
		this.f = f;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	public AttributiveCellTableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(AttributiveCellTableModel tableModel) {
		this.tableModel = tableModel;
	}

	public boolean isClose() {
		return isClose;
	}

	public void setClose(boolean isClose) {
		this.isClose = isClose;
	}
	private JFormattedTextField getTfRemark() {
		if (tfRemark == null) {
			tfRemark = new JFormattedTextField();
			tfRemark.setBounds(new Rectangle(357, 174, 141, 22));
			tfRemark.setBounds(357, 201, 141, 22);
		}
		return tfRemark;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel();
			label.setText("备        注");
			label.setFont(new Font("Dialog", Font.PLAIN, 12));
			label.setBounds(new Rectangle(302, 174, 52, 22));
			label.setBounds(302, 201, 52, 22);
		}
		return label;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
