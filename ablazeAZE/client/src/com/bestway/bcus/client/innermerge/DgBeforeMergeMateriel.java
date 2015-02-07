/*
 * Created on 2004-6-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.innermerge;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.innermerge.entity.ReverseMergeBeforeData;
import com.bestway.bcus.innermerge.entity.ReverseMergeFourData;
import com.bestway.bcus.innermerge.entity.ReverseMergeTenData;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgBeforeMergeMateriel extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;
	private JToolBar jToolBar2 = null;
	private JButton btnSave = null;
	private JButton btnCancel = null;
	private JComboBox cbbMaterielType = null;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JPanel jPanel3 = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JTextField tf4No = null;
	private JTextField tf4CommodityCode = null;
	private JTextField tf4CommodityName = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JLabel jLabel6 = null;
	private JLabel jLabel7 = null;
	private JLabel jLabel8 = null;
	private JLabel jLabel9 = null;
	private JLabel jLabel10 = null;
	private JTextField tf10No = null;
	private JTextField tf10CommodityName = null;
	private JTextField tf10MemoUnit = null;
	private JTextField tf10LegalSecondUnit = null;
	private JTextField tf10CommodityCode = null;
	private JTextField tf10CommoditySpec = null;
	private JTextField tf10LegalFirstUnit = null;
	private JLabel jLabel12 = null;
	private JLabel jLabel13 = null;
	private JLabel jLabel14 = null;
	private JLabel jLabel15 = null;
	private JLabel jLabel16 = null;
	private JLabel jLabel17 = null;
	private JLabel jLabel18 = null;
	private JTextField tfMaterielName = null;
	private JTextField tfBeforeFactoryUnit = null;
	private JTextField tfBeforeMaterielCode = null;
	private JTextField tfBeforeMaterielSpec = null;
	private JFormattedTextField tfBeforeUnitWeight = null;
	private JFormattedTextField tfBeforUnitPrice = null;
	private JFormattedTextField tfBeforeConvertQuotiety = null;
	private DefaultFormatterFactory defaultFormatterFactory = null;
	private NumberFormatter numberFormatter = null;

	private JTableListModel tableModel = null;
	private String materielType = null;
	private ReverseMergeBeforeData reverseMergeBeforeData = null; // @jve:decl-index=0:
	private CommonBaseCodeAction commonBaseCodeAction = null;

	// private boolean isPutOnRecord = false;

	/**
	 * This is the default constructor
	 */
	public DgBeforeMergeMateriel() {
		super();
		commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		initialize();
		initUIComponents();

	}

	public void setVisible(boolean isFlag) {
		if (isFlag) {
			this.reverseMergeBeforeData = (ReverseMergeBeforeData) this.tableModel
					.getCurrentRow();
			showData();
			// setState();
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
		this.setTitle("归并前物料基本资料修改");
		this.setSize(547, 425);
		this.setContentPane(getJContentPane());

	}

	/**
	 * @return Returns the reverseMergeBeforeData.
	 */
	public ReverseMergeBeforeData getReverseMergeTenData() {
		return reverseMergeBeforeData;
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
	 * @param reverseMergeFourData
	 *            The reverseMergeFourData to set.
	 */
	public void setReverseMergeTenData(
			ReverseMergeBeforeData reverseMergeFourData) {
		this.reverseMergeBeforeData = reverseMergeFourData;
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

			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);

			jContentPane.add(getJToolBar2(), null);
			jContentPane.add(getJPanel1(), null);
			jContentPane.add(getJPanel2(), null);
			jContentPane.add(getJPanel3(), null);
		}
		return jContentPane;
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
	 * This method initializes jToolBar2
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar2() {
		if (jToolBar2 == null) {
			jToolBar2 = new JToolBar();
			jToolBar2.setBounds(0, 0, 538, 34);
			jToolBar2.add(getBtnSave());
			jToolBar2.add(getBtnCancel());
			jToolBar2.add(getJPanel());
		}
		return jToolBar2;
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
					DgBeforeMergeMateriel.this.dispose();
				}
			});
		}
		return btnCancel;
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
	 * This method initializes cbbMaterielType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbMaterielType() {
		if (cbbMaterielType == null) {
			cbbMaterielType = new JComboBox();
			cbbMaterielType.setEnabled(false);
			cbbMaterielType.setBounds(156, 5, 99, 20);
		}
		return cbbMaterielType;

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel.setBounds(93, 5, 55, 20);
			jLabel.setText("物料类别");
			jPanel.add(getCbbMaterielType(), null);
			jPanel.add(jLabel, null);
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
			jLabel1 = new JLabel();
			jLabel2 = new JLabel();
			jLabel3 = new JLabel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(7, 299, 526, 94);
			jPanel1
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
									"四位归并",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									new java.awt.Font("Dialog",
											java.awt.Font.PLAIN, 12),
									new java.awt.Color(227, 145, 0)));
			jLabel1.setBounds(28, 18, 67, 20);
			jLabel1.setText("4位备案序号");
			jLabel2.setBounds(28, 41, 67, 20);
			jLabel2.setText("4位商品编码");
			jLabel3.setBounds(28, 65, 67, 20);
			jLabel3.setText("4位商品名称");
			jPanel1.add(jLabel1, null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(getTf4No(), null);
			jPanel1.add(getTf4CommodityCode(), null);
			jPanel1.add(getTf4CommodityName(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jLabel4 = new JLabel();
			jLabel5 = new JLabel();
			jLabel6 = new JLabel();
			jLabel7 = new JLabel();
			jLabel8 = new JLabel();
			jLabel9 = new JLabel();
			jLabel10 = new JLabel();
			jPanel2.setLayout(null);
			jPanel2.setBounds(7, 166, 526, 132);
			jPanel2
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
									"归并后",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									new java.awt.Font("Dialog",
											java.awt.Font.PLAIN, 12),
									new java.awt.Color(227, 145, 0)));
			jLabel4.setBounds(25, 22, 75, 20);
			jLabel4.setText("10位备案序号");
			jLabel5.setBounds(23, 46, 77, 20);
			jLabel5.setText("10位商品名称");
			jLabel6.setBounds(51, 72, 49, 20);
			jLabel6.setText("备案单位");
			jLabel7.setBounds(39, 96, 61, 20);
			jLabel7.setText("法定单位二");
			jLabel8.setBounds(272, 22, 75, 20);
			jLabel8.setText("10位商品编码");
			jLabel9.setBounds(272, 46, 75, 20);
			jLabel9.setText("10位商品规格");
			jLabel10.setBounds(285, 72, 62, 20);
			jLabel10.setText("法定单位一");
			jPanel2.add(jLabel4, null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(jLabel6, null);
			jPanel2.add(jLabel7, null);
			jPanel2.add(jLabel8, null);
			jPanel2.add(jLabel9, null);
			jPanel2.add(jLabel10, null);
			jPanel2.add(getTf10No(), null);
			jPanel2.add(getTf10CommodityName(), null);
			jPanel2.add(getTf10MemoUnit(), null);
			jPanel2.add(getTf10LegalSecondUnit(), null);
			jPanel2.add(getTf10CommodityCode(), null);
			jPanel2.add(getTf10CommoditySpec(), null);
			jPanel2.add(getTf10LegalFirstUnit(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jLabel12 = new JLabel();
			jLabel13 = new JLabel();
			jLabel14 = new JLabel();
			jLabel15 = new JLabel();
			jLabel16 = new JLabel();
			jLabel17 = new JLabel();
			jLabel18 = new JLabel();
			jPanel3.setLayout(null);
			jPanel3.setBounds(7, 36, 526, 132);
			jPanel3
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
									"归并前",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									new java.awt.Font("Dialog",
											java.awt.Font.PLAIN, 12),
									new java.awt.Color(227, 145, 0)));
			jLabel12.setBounds(73, 45, 27, 20);
			jLabel12.setText("品名");
			jLabel13.setBounds(50, 71, 50, 20);
			jLabel13.setText("工厂单位");
			jLabel14.setBounds(323, 71, 26, 20);
			jLabel14.setText("单重");
			jLabel15.setBounds(76, 19, 24, 20);
			jLabel15.setText("料号");
			jLabel16.setBounds(300, 19, 48, 20);
			jLabel16.setText("规格型号");
			jLabel17.setBounds(322, 45, 26, 20);
			jLabel17.setText("单价");
			jLabel18.setBounds(50, 96, 50, 20);
			jLabel18.setText("换算系数");
			jPanel3.add(jLabel12, null);
			jPanel3.add(jLabel13, null);
			jPanel3.add(jLabel14, null);
			jPanel3.add(jLabel15, null);
			jPanel3.add(jLabel16, null);
			jPanel3.add(jLabel17, null);
			jPanel3.add(jLabel18, null);
			jPanel3.add(getTfMaterielName(), null);
			jPanel3.add(getTfBeforeFactoryUnit(), null);
			jPanel3.add(getTfBeforeMaterielCode(), null);
			jPanel3.add(getTfBeforeMaterielSpec(), null);
			jPanel3.add(getTfBeforeUnitWeight(), null);
			jPanel3.add(getTfBeforUnitPrice(), null);
			jPanel3.add(getTfBeforeConvertQuotiety(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes tf4No
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTf4No() {
		if (tf4No == null) {
			tf4No = new JTextField();
			tf4No.setBounds(100, 18, 90, 20);
			tf4No.setEditable(false);
			tf4No.setBackground(java.awt.Color.white);
		}
		return tf4No;
	}

	/**
	 * This method initializes tf4CommodityCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTf4CommodityCode() {
		if (tf4CommodityCode == null) {
			tf4CommodityCode = new JTextField();
			tf4CommodityCode.setBounds(100, 41, 143, 20);
			tf4CommodityCode.setEditable(false);
			tf4CommodityCode.setBackground(java.awt.Color.white);
		}
		return tf4CommodityCode;
	}

	/**
	 * This method initializes tf4CommodityName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTf4CommodityName() {
		if (tf4CommodityName == null) {
			tf4CommodityName = new JTextField();
			tf4CommodityName.setBounds(100, 65, 143, 20);
			tf4CommodityName.setEditable(false);
			tf4CommodityName.setBackground(java.awt.Color.white);
		}
		return tf4CommodityName;
	}

	/**
	 * This method initializes tf10No
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTf10No() {
		if (tf10No == null) {
			tf10No = new JTextField();
			tf10No.setBounds(102, 22, 111, 20);
			tf10No.setEditable(false);
			tf10No.setBackground(java.awt.Color.white);
		}
		return tf10No;
	}

	/**
	 * This method initializes tf10CommodityName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTf10CommodityName() {
		if (tf10CommodityName == null) {
			tf10CommodityName = new JTextField();
			tf10CommodityName.setBounds(102, 46, 141, 20);
			tf10CommodityName.setEditable(false);
			tf10CommodityName.setBackground(java.awt.Color.white);
		}
		return tf10CommodityName;
	}

	/**
	 * This method initializes tf10MemoUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTf10MemoUnit() {
		if (tf10MemoUnit == null) {
			tf10MemoUnit = new JTextField();
			tf10MemoUnit.setBounds(102, 72, 111, 20);
			tf10MemoUnit.setEditable(false);
			tf10MemoUnit.setBackground(java.awt.Color.white);
		}
		return tf10MemoUnit;
	}

	/**
	 * This method initializes tf10LegalSecondUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTf10LegalSecondUnit() {
		if (tf10LegalSecondUnit == null) {
			tf10LegalSecondUnit = new JTextField();
			tf10LegalSecondUnit.setBounds(102, 97, 111, 20);
			tf10LegalSecondUnit.setEditable(false);
			tf10LegalSecondUnit.setBackground(java.awt.Color.white);
		}
		return tf10LegalSecondUnit;
	}

	/**
	 * This method initializes tf10CommodityCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTf10CommodityCode() {
		if (tf10CommodityCode == null) {
			tf10CommodityCode = new JTextField();
			tf10CommodityCode.setBounds(349, 22, 125, 20);
			tf10CommodityCode.setEditable(false);
			tf10CommodityCode.setBackground(java.awt.Color.white);
		}
		return tf10CommodityCode;
	}

	/**
	 * This method initializes tf10CommoditySpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTf10CommoditySpec() {
		if (tf10CommoditySpec == null) {
			tf10CommoditySpec = new JTextField();
			tf10CommoditySpec.setBounds(349, 46, 154, 20);
			tf10CommoditySpec.setEditable(false);
			tf10CommoditySpec.setBackground(java.awt.Color.white);
		}
		return tf10CommoditySpec;
	}

	/**
	 * This method initializes tf10LegalFirstUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTf10LegalFirstUnit() {
		if (tf10LegalFirstUnit == null) {
			tf10LegalFirstUnit = new JTextField();
			tf10LegalFirstUnit.setBounds(349, 72, 114, 20);
			tf10LegalFirstUnit.setEditable(false);
			tf10LegalFirstUnit.setBackground(java.awt.Color.white);
		}
		return tf10LegalFirstUnit;
	}

	/**
	 * This method initializes tfMaterielName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMaterielName() {
		if (tfMaterielName == null) {
			tfMaterielName = new JTextField();
			tfMaterielName.setBounds(102, 45, 139, 20);
		}
		return tfMaterielName;
	}

	/**
	 * This method initializes tfBeforeFactoryUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBeforeFactoryUnit() {
		if (tfBeforeFactoryUnit == null) {
			tfBeforeFactoryUnit = new JTextField();
			tfBeforeFactoryUnit.setBounds(102, 71, 116, 20);
			tfBeforeFactoryUnit.setEditable(false);
			tfBeforeFactoryUnit.setBackground(java.awt.Color.white);
		}
		return tfBeforeFactoryUnit;
	}

	/**
	 * This method initializes tfBeforeMaterielCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBeforeMaterielCode() {
		if (tfBeforeMaterielCode == null) {
			tfBeforeMaterielCode = new JTextField();
			tfBeforeMaterielCode.setBounds(102, 19, 116, 20);
			tfBeforeMaterielCode.setEditable(false);
			tfBeforeMaterielCode.setBackground(java.awt.Color.white);
		}
		return tfBeforeMaterielCode;
	}

	/**
	 * This method initializes tfBeforeUnitWeight
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfBeforeUnitWeight() {
		if (tfBeforeUnitWeight == null) {
			tfBeforeUnitWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfBeforeUnitWeight.setBounds(351, 71, 116, 20);
			tfBeforeUnitWeight
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfBeforeUnitWeight;
	}

	/**
	 * This method initializes tfBeforUnitPrice
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfBeforUnitPrice() {
		if (tfBeforUnitPrice == null) {
			tfBeforUnitPrice = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfBeforUnitPrice.setBounds(350, 45, 116, 20);
			tfBeforUnitPrice.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfBeforUnitPrice;
	}

	/**
	 * This method initializes tfBeforeConvertQuotiety
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfBeforeConvertQuotiety() {
		if (tfBeforeConvertQuotiety == null) {
			tfBeforeConvertQuotiety = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfBeforeConvertQuotiety.setBounds(102, 96, 116, 20);
			tfBeforeConvertQuotiety
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfBeforeConvertQuotiety;
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	/**
	 * This method initializes numberFormatter
	 * 
	 * @return javax.swing.text.NumberFormatter
	 */
	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
		}
		return numberFormatter;
	}

	/**
	 * This method initializes tfBeforeMaterielSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBeforeMaterielSpec() {
		if (tfBeforeMaterielSpec == null) {
			tfBeforeMaterielSpec = new JTextField();
			tfBeforeMaterielSpec.setBounds(350, 19, 145, 20);
		}
		return tfBeforeMaterielSpec;
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
	}

	/**
	 * 显示数据
	 * 
	 */
	private void showData() {
		this.cbbMaterielType.setSelectedIndex(ItemProperty.getIndexByCode(
				this.materielType, this.cbbMaterielType));
		if (this.reverseMergeBeforeData == null) {
			return;
		}
		if (reverseMergeBeforeData.getMateriel().getCalUnit() != null) {
			this.tfBeforeFactoryUnit.setText(reverseMergeBeforeData
					.getMateriel().getCalUnit().getName());
		} else {
			this.tfBeforeFactoryUnit.setText("");
		}
		if (reverseMergeBeforeData.getMateriel() != null) {
			Materiel materiel = reverseMergeBeforeData.getMateriel();
			if (materiel.getPtNo() != null) {
				this.tfBeforeMaterielCode.setText(materiel.getPtNo());
			} else {
				this.tfBeforeMaterielCode.setText("");
			}
			if (materiel.getFactoryName() != null) {
				this.tfMaterielName.setText(materiel.getFactoryName());
			} else {
				this.tfMaterielName.setText("");
			}
			if (materiel.getFactorySpec() != null) {
				this.tfBeforeMaterielSpec.setText(materiel.getFactorySpec());
			} else {
				this.tfBeforeMaterielSpec.setText("");
			}
			if (materiel.getPtPrice() != null) {
				this.tfBeforUnitPrice.setValue(materiel.getPtPrice());
			} else {
				this.tfBeforUnitPrice.setValue(new Double(0));
			}
			//
			// 是净重
			//
			if (materiel.getPtNetWeight() != null) {
				this.tfBeforeUnitWeight.setValue(materiel.getPtNetWeight());
			} else {
				this.tfBeforeUnitWeight.setValue(new Double(0));
			}
			//
			// 换算系数还没有加入
			//
			if (materiel.getUnitConvert() != null) {
				this.tfBeforeConvertQuotiety
						.setValue(materiel.getUnitConvert());
			} else {
				this.tfBeforeConvertQuotiety.setValue(new Double(0));
			}
		}
		ReverseMergeTenData reverseMergeTenData = this.reverseMergeBeforeData
				.getReverseMergeTenData();
		if (reverseMergeTenData != null) {
			if (reverseMergeTenData.getHsAfterLegalUnit() != null) {
				this.tf10LegalFirstUnit.setText(reverseMergeTenData
						.getHsAfterLegalUnit().getName());
			} else {
				this.tf10LegalFirstUnit.setText("");
			}
			if (reverseMergeTenData.getHsAfterMaterielTenName() != null) {
				this.tf10CommodityName.setText(reverseMergeTenData
						.getHsAfterMaterielTenName());
			} else {
				this.tf10CommodityName.setText("");
			}
			if (reverseMergeTenData.getHsAfterMaterielTenSpec() != null) {
				this.tf10CommoditySpec.setText(reverseMergeTenData
						.getHsAfterMaterielTenSpec());
			} else {
				this.tf10CommoditySpec.setText("");
			}
			if (reverseMergeTenData.getHsAfterMemoUnit() != null) {
				this.tf10MemoUnit.setText(reverseMergeTenData
						.getHsAfterMaterielTenSpec());
			} else {
				this.tf10MemoUnit.setText("");
			}
			if (reverseMergeTenData.getHsAfterSecondLegalUnit() != null) {
				this.tf10LegalSecondUnit.setText(reverseMergeTenData
						.getHsAfterSecondLegalUnit().getName());
			} else {
				this.tf10LegalSecondUnit.setText("");
			}
			if (reverseMergeTenData.getHsAfterTenMemoNo() != null) {
				this.tf10No.setText(String.valueOf(reverseMergeTenData
						.getHsAfterTenMemoNo()));
			} else {
				this.tf10No.setText("");
			}
			if (reverseMergeTenData.getHsAfterComplex() != null) {
				this.tf10CommodityCode.setText(reverseMergeTenData
						.getHsAfterComplex().getCode());
			} else {
				this.tf10CommodityCode.setText("");
			}
		}
		if (reverseMergeTenData != null) {
			ReverseMergeFourData reverseMergeFourData = reverseMergeTenData
					.getReverseMergeFourData();
			if (reverseMergeFourData != null) {
				if (reverseMergeFourData.getHsFourNo() != null) {
					this.tf4No.setText(reverseMergeFourData.getHsFourNo()
							.toString());
				} else {
					this.tf4No.setText("");
				}
				if (reverseMergeFourData.getHsFourCode() != null) {
					this.tf4CommodityCode.setText(reverseMergeFourData
							.getHsFourCode());
				} else {
					this.tf4CommodityCode.setText("");
				}
				if (reverseMergeFourData.getHsFourMaterielName() != null) {
					this.tf4CommodityName.setText(reverseMergeFourData
							.getHsFourMaterielName());
				} else {
					this.tf4CommodityName.setText("");
				}
			}
		}
	}

	/**
	 * 填充对象
	 */
	private void fillData() {
		this.reverseMergeBeforeData.setCompany(CommonVars.getCurrUser()
				.getCompany());
		Materiel materiel = this.reverseMergeBeforeData.getMateriel();
		materiel.setPtNetWeight(new Double(this.tfBeforeUnitWeight.getValue()
				.toString()));
		materiel.setFactoryName(this.tfMaterielName.getText());
		materiel.setFactorySpec(this.tfBeforeMaterielSpec.getText());
		materiel.setPtPrice(new Double(this.tfBeforUnitPrice.getValue()
				.toString()));
		materiel.setUnitConvert(new Double(this.tfBeforeConvertQuotiety
				.getValue().toString()));
		reverseMergeBeforeData.setMateriel(materiel);
		//
		// 修改并保存
		//
		reverseMergeBeforeData = commonBaseCodeAction
				.saveReverseMergeBeforeData(new Request(CommonVars
						.getCurrUser()), reverseMergeBeforeData);
		this.tableModel.updateRow(reverseMergeBeforeData);
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
		if (this.tfBeforeMaterielCode.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "归并前料号不可为空", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (this.tfMaterielName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "归并前物料名称不可为空", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		return false;
	}

	/**
	 * 保存
	 * 
	 */
	private void save() {
		this.fillData();

	}

	private void setState() {
		// this.btnSave.setEnabled(!this.isPutOnRecord);
	}

} // @jve:decl-index=0:visual-constraint="10,10"
