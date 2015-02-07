package com.bestway.common.client.erpbillcenter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.FactoryAndFactualCustomsRalation;
import com.bestway.bcus.cas.entity.StatCusNameRelationHsn;
import com.bestway.bcus.client.cas.CasQuery;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JCheckBox;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

public class DgEditUniteConvert extends JDialogBase {
	private FactoryAndFactualCustomsRalation fdata = null; // @jve:decl-index=0:

	/**
	 * 修改之前的手册号 wss2010.06.21新加
	 */
	private String oldEmsNo = null; // @jve:decl-index=0:

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JComboBox cbbFactory = null;

	private JTextField tfCustoms = null;
	
	private JTextField tfRemark = null;

	private JFormattedTextField tfUnitConvert = null;

	private JButton btnSave = null;

	private JButton btnExit = null;

	/**
	 * 数据填充是否OK?
	 */

	private JLabel jLabel4 = null;

	private JLabel lbRemark = null;
	
	private JLabel jLabel41 = null;

	private JLabel jLabel21 = null;

	private JLabel jLabel411 = null;

	private JFormattedTextField tfPtPrice = null;

	private JFormattedTextField tfNetWeight = null;

	private MaterialManageAction materialManageAction = null;

	/*
	 * 是否更新相应单据
	 */
	private String updateType = null;  //  @jve:decl-index=0:

	private JLabel jLabel3 = null;

	private JPanel jPanel1 = null;

	private JLabel jLabel5 = null;

	private JPanel jPanel11 = null;

	private JLabel jLComplex = null;

	private JLabel jLHsName = null;

	private JLabel jLHsSpec = null;

	private JTextField jTHsComplex = null;

	private JTextField jTHsSpec = null;

	private JTextField jTHsName = null;

	private JButton jButton = null;

	private JCheckBox jCheckBox = null;

	private StatCusNameRelationHsn hsInFo = null;

	private AttributiveCellTableModel tableModel = null;

	private String materielType = ""; // @jve:decl-index=0:

	private CasAction casAction = null;

	private JLabel jLabel6 = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgEditUniteConvert() {
		super();
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		initialize();
	}

	@Override
	public void setVisible(boolean f) {

		if (f) {

			initComponents();
		}
		super.setVisible(f);
	}

	private void initComponents() {
		if (fdata == null) {
			return;
		}
		// this.tfCustoms
		// .setText(fdata.getStatCusNameRelationHsn().getCusUnit() == null ? ""
		// : fdata.getStatCusNameRelationHsn().getCusUnit()
		// .getName());

		// 工厂单位
		List listunit = materialManageAction.findCalUnit(new Request(CommonVars
				.getCurrUser(), true));
		this.cbbFactory.setModel(new DefaultComboBoxModel(listunit.toArray()));
		this.cbbFactory.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbFactory, "code", "name");
		this.cbbFactory.setSelectedItem(fdata.getMateriel().getCalUnit());
		this.tfUnitConvert
				.setValue(fdata.getUnitConvert() == null ? new Double("0.0")
						: fdata.getUnitConvert());
		this.tfNetWeight
				.setValue(fdata.getMateriel().getPtNetWeight() == null ? new Double(
						"0.0")
						: fdata.getMateriel().getPtNetWeight());
		this.tfPtPrice
				.setValue(fdata.getMateriel().getPtPrice() == null ? new Double(
						"0.0")
						: fdata.getMateriel().getPtPrice());
		if (fdata.getStatCusNameRelationHsn() != null) {
			hsInFo = fdata.getStatCusNameRelationHsn();
			this.jTHsComplex.setText(hsInFo.getComplex().getCode());
			this.jTHsName.setText(hsInFo.getCusName());
			this.jTHsSpec.setText(hsInFo.getCusSpec());
			this.tfCustoms.setText(hsInFo.getCusUnit().getName());
		}
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
//		this.setSize(new Dimension(472, 288));
		this.setSize(new Dimension(472, 340));
		this.setContentPane(getJPanel());
		this.setTitle("对应关系修改");
		this.setContentPane(getJPanel());
		this.setResizable(false);
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(28, 140, 500, 18));
			jLabel6.setText("说明：修改工厂单位、单重、参考单价资料会回写报关常用工厂物料/工厂物料主档");
			jLHsSpec = new JLabel();
//			jLHsSpec.setBounds(new Rectangle(26, 187, 52, 24));
			jLHsSpec.setBounds(new Rectangle(26, 227, 52, 24));
			jLHsSpec.setText("报关规格");
			jLHsName = new JLabel();
//			jLHsName.setBounds(new Rectangle(255, 152, 52, 24));
			jLHsName.setBounds(new Rectangle(255, 192, 52, 24));
			jLHsName.setText("报关品名");
			jLComplex = new JLabel();
//			jLComplex.setBounds(new Rectangle(26, 152, 52, 24));
			jLComplex.setBounds(new Rectangle(26, 192, 52, 24));
			jLComplex.setText("商品编码");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(7, 168, 105, 21));
			jLabel5.setText("报关资料");
			jLabel5.setForeground(Color.blue);
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(7, 4, 105, 21));
			jLabel3.setText("工厂物料资料");
			jLabel3.setForeground(Color.blue);
			jLabel411 = new JLabel();
			jLabel411.setBounds(new Rectangle(256, 36, 51, 22));
			jLabel411.setText("单       重");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(27, 68, 51, 23));
			jLabel4.setText("参考单价");
			lbRemark = new JLabel();
			lbRemark.setBounds(new Rectangle(27, 98, 51, 23));
			lbRemark.setText("备        注");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(256, 68, 51, 24));
			jLabel2.setText("折  算  比");
			jLabel1 = new JLabel();
//			jLabel1.setBounds(new Rectangle(255, 187, 52, 24));
			jLabel1.setBounds(new Rectangle(255, 227, 52, 24));
			jLabel1.setText("报关单位");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(26, 35, 52, 24));
			jLabel.setText("工厂单位");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(getCbbFactory(), null);
			jPanel.add(getTfCustoms(), null);
			jPanel.add(getTfUnitConvert(), null);
			jPanel.add(getBtnSave(), null);
			jPanel.add(getBtnExit(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(lbRemark,null);
			jPanel.add(getTfRemark(),null);
			jPanel.add(jLabel411, null);
			jPanel.add(getTfPtPrice(), null);
			jPanel.add(getTfNetWeight(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getJPanel1(), null);
			jPanel.add(jLabel5, null);
			jPanel.add(getJPanel11(), null);
			jPanel.add(jLComplex, null);
			jPanel.add(jLHsName, null);
			jPanel.add(jLHsSpec, null);
			jPanel.add(getJTHsComplex(), null);
			jPanel.add(getJTHsSpec(), null);
			jPanel.add(getJTHsName(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJCheckBox(), null);
			jPanel.add(jLabel6, null);
		}
		return jPanel;
	}

	private JTextField getTfRemark(){
		if(tfRemark==null){
			tfRemark = new JTextField();
			tfRemark.setBounds(new Rectangle(80, 98, 141, 24));
		}
		return tfRemark;
	}
	
	/**
	 * This method initializes tfFactory
	 * 
	 * @return javax.swing.JTextField
	 */
	private JComboBox getCbbFactory() {
		if (cbbFactory == null) {
			cbbFactory = new JComboBox();
			cbbFactory.setBounds(new Rectangle(80, 36, 141, 24));
		}
		return cbbFactory;
	}

	/**
	 * This method initializes tfCustoms
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCustoms() {
		if (tfCustoms == null) {
			tfCustoms = new JTextField();
//			tfCustoms.setBounds(new Rectangle(309, 187, 141, 24));
			tfCustoms.setBounds(new Rectangle(309, 227, 141, 24));
			tfCustoms.setEditable(false);
		}
		return tfCustoms;
	}

	/**
	 * This method initializes tfUnitConvert
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getTfUnitConvert() {
		if (tfUnitConvert == null) {
			tfUnitConvert = new JFormattedTextField();
			tfUnitConvert.setFormatterFactory(getDefaultFormatterFactory());
			tfUnitConvert.setBounds(new Rectangle(310, 68, 141, 24));
		}
		return tfUnitConvert;
	}

	/**
	 * 填充资料
	 */
	private boolean fillData() {
		Double dou = 1.0;
		Double ptPrice = 0.0;
		CalUnit calUnit = null;
		Double ptNetWight = 0.0;
		try {
			dou = Double.parseDouble(getTfUnitConvert().getText().toString());
			ptPrice = Double.parseDouble(getTfPtPrice().getText().toString());
			ptNetWight = Double.parseDouble(getTfNetWeight().getText()
					.toString());
			if (cbbFactory.getSelectedItem() == null) {
				JOptionPane.showMessageDialog(DgEditUniteConvert.this,
						"工厂单位不能为空！", "提示！", JOptionPane.WARNING_MESSAGE);
				return false;
			} else {
				calUnit = (CalUnit) cbbFactory.getSelectedItem();
				if (calUnit.getName().equals(getTfCustoms().getText())) {
					tfUnitConvert.setValue(new Double("1.0"));
					dou = Double.parseDouble(getTfUnitConvert().getValue()
							.toString());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(DgEditUniteConvert.this,
					"输入的单位折算不合法！", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		fdata.setUnitConvert(dou);
		fdata.getMateriel().setPtPrice(ptPrice);
		fdata.getMateriel().setPtNetWeight(ptNetWight);
		if (calUnit != null) {
			fdata.getMateriel().setCalUnit(calUnit);
		}
		fdata.setUnitConvert(Double.parseDouble(getTfUnitConvert().getValue()
				.toString()));
		if (hsInFo != null) {
			fdata.setStatCusNameRelationHsn(hsInFo);
		}
		if(tfRemark.getText()!=null&&tfRemark.getText().trim().length()>0){
			fdata.setRemark(tfRemark.getText());
		}
		return true;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
//			btnSave.setBounds(new Rectangle(134, 220, 64, 16));
			btnSave.setBounds(new Rectangle(134, 260, 64, 26));
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
//					if (tfCustoms.getText() != null
//							&& tfCustoms.getText().equals(
//									((CalUnit) getCbbFactory()
//											.getSelectedItem()).getName())
//							&& !"1".equals(tfUnitConvert.getText())) {
//						JOptionPane.showMessageDialog(DgEditUniteConvert.this,
//								"当报关单位=工厂单位时，折算比必须为‘1’！", "提示！",
//								JOptionPane.WARNING_MESSAGE);
//						getTfUnitConvert().requestFocus();
//						return;
//					}
					
					DgChooseUpdateType dg = new DgChooseUpdateType();
					dg.setVisible(true);
					
					if(dg.isOk()) {
						updateType = dg.getUpdateType();
					}
					if (fillData()) {// 填充修改的资料
						new SaveThread().execute();
					}
				}
			});
		}
		return btnSave;
	}

	class SaveThread extends SwingWorker {

		@Override
		protected Object doInBackground() throws Exception {

			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正更新数据，请稍后...");

				System.out.println("wss isUpdateDetail = " + updateType);

				casAction.modifyMaterielForFactoryAndFactualCustomsRalation(
						new Request(CommonVars.getCurrUser()), fdata,
						materielType, updateType);
				CommonProgress.setMessage("已保存对应关系");

				// if(isAccount){
				// casAction.saveFactoryAndFactualCustomsRalation(
				// new Request(CommonVars.getCurrUser()),
				// fdata, materielType,true,jCheckBox.isSelected(),(oldEmsNo ==
				// null ? "":oldEmsNo));
				// CommonProgress.setMessage("已同步更新报关常用工厂物料与工厂物料主档以及生效与未生效单据");
				// }else{
				// casAction.saveFactoryAndFactualCustomsRalation(
				// new Request(CommonVars.getCurrUser()),true,fdata);
				// CommonProgress.setMessage("已保存对应关系");
				// }

				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEditUniteConvert.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				CommonProgress.closeProgressDialog();
			}
			return null;
		}

		@Override
		protected void done() {
			tableModel.updateRow(fdata);
			dispose();
		}
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
//			btnExit.setBounds(new Rectangle(267, 220, 64, 26));
			btnExit.setBounds(new Rectangle(267, 260, 64, 26));
			btnExit.setText("退出");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	public FactoryAndFactualCustomsRalation getFdata() {
		return fdata;
	}

	public void setFdata(FactoryAndFactualCustomsRalation fdata) {
		this.fdata = fdata;
	}

	public String getOldEmsNo() {
		return oldEmsNo;
	}

	public void setOldEmsNo(String oldEmsNo) {
		this.oldEmsNo = oldEmsNo;
	}

	/**
	 * This method initializes tfUnitConvert11
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfPtPrice() {
		if (tfPtPrice == null) {
			tfPtPrice = new JFormattedTextField();
			tfPtPrice.setFormatterFactory(getDefaultFormatterFactory());
			tfPtPrice.setBounds(new Rectangle(81, 68, 141, 24));
		}
		return tfPtPrice;
	}

	/**
	 * This method initializes tfUnitConvert111
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfNetWeight() {
		if (tfNetWeight == null) {
			tfNetWeight = new JFormattedTextField();
			tfNetWeight.setFormatterFactory(getDefaultFormatterFactory());
			tfNetWeight.setBounds(new Rectangle(310, 36, 141, 24));
		}
		return tfNetWeight;
	}

	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
			DecimalFormat decimalFormat = new DecimalFormat();
			decimalFormat.setMaximumFractionDigits(9);
			decimalFormat.setGroupingSize(0);
			decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
			numberFormatter.setFormat(decimalFormat);
		}
		return numberFormatter;
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
			jPanel1.setBounds(new Rectangle(111, 12, 321, 2));
		}
		return jPanel1;
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
			jPanel11.setBounds(new Rectangle(110, 137, 321, 2));
		}
		return jPanel11;
	}

	/**
	 * This method initializes jTHsComplex
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTHsComplex() {
		if (jTHsComplex == null) {
			jTHsComplex = new JTextField();
//			jTHsComplex.setBounds(new Rectangle(80, 152, 141, 24));
			jTHsComplex.setBounds(new Rectangle(80, 192, 141, 24));
			jTHsComplex.setEditable(false);
		}
		return jTHsComplex;
	}

	/**
	 * This method initializes jTHsSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTHsSpec() {
		if (jTHsSpec == null) {
			jTHsSpec = new JTextField();
//			jTHsSpec.setBounds(new Rectangle(80, 187, 141, 24));
			jTHsSpec.setBounds(new Rectangle(80, 227, 141, 24));
			jTHsSpec.setEditable(false);
		}
		return jTHsSpec;
	}

	/**
	 * This method initializes jTHsName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTHsName() {
		if (jTHsName == null) {
			jTHsName = new JTextField();
//			jTHsName.setBounds(new Rectangle(309, 152, 141, 24));
			jTHsName.setBounds(new Rectangle(309, 192, 141, 24));
			jTHsName.setEditable(false);
		}
		return jTHsName;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
//			jButton.setBounds(new Rectangle(221, 152, 24, 23));
			jButton.setBounds(new Rectangle(221, 192, 24, 23));
			jButton.setText("...");
			jButton.setEnabled(false);
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = CasQuery.getInstance()
							.getStatCusNameRelationHsn(false, materielType);
					if (list != null && list.size() > 0) {
						StatCusNameRelationHsn tmp = (StatCusNameRelationHsn) list
								.get(0);
						// if(hsInFo != null){
						// tmp.setProjectType(hsInFo.getProjectType());
						// }
						hsInFo = tmp;
						DgEditUniteConvert.this.jTHsComplex.setText(hsInFo
								.getComplex().getCode());
						DgEditUniteConvert.this.jTHsName.setText(hsInFo
								.getCusName());
						DgEditUniteConvert.this.jTHsSpec.setText(hsInFo
								.getCusSpec());
						DgEditUniteConvert.this.tfCustoms.setText(hsInFo
								.getCusUnit().getName());
						// fillWinBySelectHs(tmp);
						// fillTenInfo(scrh,currBillDetail);
					}
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new Rectangle(26, 194, 208, 21));
			jCheckBox.setText("是否更新单据的折算报关数量");
			jCheckBox.setVisible(false);
		}
		return jCheckBox;
	}

	/**
	 * @return the tableModel
	 */
	public AttributiveCellTableModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel
	 *            the tableModel to set
	 */
	public void setTableModel(AttributiveCellTableModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * @return the materielType
	 */
	public String getMaterielType() {
		return materielType;
	}

	/**
	 * @param materielType
	 *            the materielType to set
	 */
	public void setMaterielType(String materielType) {
		this.materielType = materielType;
	}

} // @jve:decl-index=0:visual-constraint="224,29"
