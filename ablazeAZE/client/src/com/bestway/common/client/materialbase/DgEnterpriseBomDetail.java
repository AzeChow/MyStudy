package com.bestway.common.client.materialbase;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.EnterpriseBomManage;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgEnterpriseBomDetail extends JDialogBase {

	private JButton btnSave = null;

	private JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JCustomFormattedTextField tfBomVersion = null;

	private JButton btnCancel = null;

//	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:visual-constraint=""

//	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:visual-constraint=""

	private MaterialManageAction materialManageAction = null;

	private boolean isOk = false;

	// private TempEntBomVersion version = null;

	private EnterpriseBomManage detail = null;

	private int dataState = DataState.BROWSE;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JTextField tfPtNo = null;

	private JTextField tfPtName = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JTextField tfPtSpec = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel6 = null;

	private JCustomFormattedTextField tfUnitWaste = null;

	private JCustomFormattedTextField tfWaste = null;

	private JCustomFormattedTextField tfUnitUsed = null;

	private JButton btnPrevious = null;

	private JButton btnNext = null;

	private JTableListModel tableModel = null;

	private JLabel jLabel7 = null;

	private JLabel jLabel8 = null;

	private JLabel jLabel9 = null;

	private JTextField tfChildVersionNo = null;

	private JCalendarComboBox cbbChildEffectDate = null;

	private JCalendarComboBox cbbChildEndDate = null;

	public EnterpriseBomManage getDetail() {
		return detail;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dateState) {
		this.dataState = dateState;
	}

	// public TempEntBomVersion getVersion() {
	// return version;
	// }
	//
	// public void setVersion(TempEntBomVersion version) {
	// this.version = version;
	// }

	public boolean isOk() {
		return isOk;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgEnterpriseBomDetail() {
		super();
		initialize();
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		CompanyOther other = CommonVars.getOther();
		// 保留小数位
		Integer d = 6;
		if (other != null) {
			d = (other.getBomChangeBit() == null) ? Integer.valueOf(0)
					: other.getBomChangeBit();
		}
		CustomFormattedTextFieldUtils.setFormatterFactory(tfWaste, d);
		CustomFormattedTextFieldUtils.setFormatterFactory(tfUnitWaste, d);
		CustomFormattedTextFieldUtils.setFormatterFactory(tfUnitUsed, d);
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			this.showData();
		}
		super.setVisible(b);
	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;

	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(489, 292));
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("BOM明细编辑");
		this.setContentPane(getJContentPane());

	}

	private void setState() {
		this.tfUnitUsed.setEditable(this.dataState != DataState.BROWSE);
		this.tfWaste.setEditable(this.dataState != DataState.BROWSE);
		this.tfUnitWaste.setEditable(this.dataState != DataState.BROWSE);
		this.btnSave.setEnabled(this.dataState != DataState.BROWSE);
		this.tfChildVersionNo.setEnabled(this.dataState != DataState.BROWSE);
		this.cbbChildEffectDate.setEnabled(this.dataState != DataState.BROWSE);
		this.cbbChildEndDate.setEnabled(this.dataState != DataState.BROWSE);

	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setBounds(new java.awt.Rectangle(92, 210, 60, 27));
			btnSave.setText("确定");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// if (!checkData()) {
					// return;
					// }
					dataState = DataState.BROWSE;
					setState();
					fillData(detail);
					detail = materialManageAction.saveEnterpriseBomManage(
							new Request(CommonVars.getCurrUser()), detail);
					tableModel.updateRow(detail);
					isOk = true;
					// dispose();
				}
			});
		}
		return btnSave;
	}

	private boolean checkData() {
		if (tfWaste.getValue() != null) {
			if (Double.parseDouble(tfWaste.getValue().toString()) >= 1) {
				JOptionPane.showMessageDialog(DgEnterpriseBomDetail.this,
						"损耗不能大于或等于1", "提示", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		return true;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel9 = new JLabel();
			jLabel9.setBounds(new java.awt.Rectangle(248, 150, 74, 20));
			jLabel9.setText("子件失效日期");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new java.awt.Rectangle(27, 148, 77, 24));
			jLabel8.setText("子件生效日期");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new java.awt.Rectangle(248, 119, 74, 22));
			jLabel7.setText("子件版本号");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new java.awt.Rectangle(27, 119, 77, 21));
			jLabel6.setText("单项用量");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(248, 90, 74, 21));
			jLabel5.setText("损耗");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(27, 92, 77, 21));
			jLabel4.setText("单耗");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(248, 62, 74, 21));
			jLabel3.setText("料件规格");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(27, 62, 77, 21));
			jLabel2.setText("料件名称");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(248, 30, 74, 21));
			jLabel1.setText("料件号码");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(27, 31, 77, 21));
			jLabel.setText("BOM版本号 ");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getTfBomVersion(), null);
			jContentPane.add(getBtnSave(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getTfPtNo(), null);
			jContentPane.add(getTfPtName(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getTfPtSpec(), null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(getTfUnitWaste(), null);
			jContentPane.add(getTfWaste(), null);
			jContentPane.add(getTfUnitUsed(), null);
			jContentPane.add(getBtnPrevious(), null);
			jContentPane.add(getBtnNext(), null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(jLabel8, null);
			jContentPane.add(jLabel9, null);
			jContentPane.add(getTfChildVersionNo(), null);
			jContentPane.add(getCbbChildEffectDate(), null);
			jContentPane.add(getCbbChildEndDate(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jCustomFormattedTextField
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfBomVersion() {
		if (tfBomVersion == null) {
			tfBomVersion = new JCustomFormattedTextField();
			tfBomVersion.setBounds(new java.awt.Rectangle(103, 31, 129, 22));
			tfBomVersion.setEditable(false);
//			tfBomVersion.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfBomVersion;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new java.awt.Rectangle(311, 210, 64, 27));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isOk = false;
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
//	private DefaultFormatterFactory getDefaultFormatterFactory() {
//		if (defaultFormatterFactory == null) {
//			defaultFormatterFactory = new DefaultFormatterFactory();
//			
////			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
////			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
////			defaultFormatterFactory.setNullFormatter(getNumberFormatter());
////			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
//		}
//		return defaultFormatterFactory;
//	}

//	/**
//	 * This method initializes numberFormatter
//	 * 
//	 * @return javax.swing.text.NumberFormatter
//	 */
//	private NumberFormatter getNumberFormatter() {
//		if (numberFormatter == null) {
//			numberFormatter = new NumberFormatter();
//			DecimalFormat decimalFormat = new DecimalFormat(); // @jve:decl-index=0:
//			CompanyOther other = CommonVars.getOther();
//			// 保留小数位
//			Integer d = 6;
//			if (other != null) {
//				d = (other.getBomChangeBit() == null) ? Integer.valueOf(0)
//						: other.getBomChangeBit();
//			}
//			decimalFormat.setMaximumFractionDigits(d);
//			numberFormatter.setFormat(decimalFormat);
//		}
//		return numberFormatter;
//	}

	private void showData() {
		detail = (EnterpriseBomManage) tableModel.getCurrentRow();
		if (detail == null) {
			return;
		}
		this.tfBomVersion.setValue(detail.getVersionNo() == null ? null
				: Integer.parseInt(detail.getVersionNo().trim()));
		this.tfPtNo.setText(detail.getCompentNo());
		this.tfPtName.setText(detail.getCompentName());
		this.tfPtSpec.setText(detail.getCompentSpec());
		this.tfUnitWaste.setValue(detail.getUnitWare());
		this.tfWaste.setValue(detail.getWare());
		this.tfUnitUsed.setValue(detail.getUnitDosage());

		this.tfChildVersionNo.setText(detail.getChildVersionNo());
		this.cbbChildEffectDate.setDate(detail.getChildEffectDate());
		this.cbbChildEndDate.setDate(detail.getChildEndDate());
		// this.tfNote.setText(detail.getNote());
	}

	private void fillData(EnterpriseBomManage detail) {
		detail.setUnitWare(this.tfUnitWaste.getValue() == null ? 0 : Double
				.parseDouble(this.tfUnitWaste.getText().toString()));
		detail.setWare(this.tfWaste.getValue() == null ? 0 : Double
				.parseDouble(this.tfWaste.getText().toString()));
		detail.setUnitDosage(this.tfUnitUsed.getValue() == null ? 0 : Double
				.parseDouble(this.tfUnitUsed.getText().toString()));
		detail.setChildEffectDate(this.cbbChildEffectDate.getDate());
		detail.setChildEndDate(this.cbbChildEndDate.getDate());
		detail.setChildVersionNo(this.tfChildVersionNo.getText().trim());
		// detail.set(this.tfNote.getText());
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtNo() {
		if (tfPtNo == null) {
			tfPtNo = new JTextField();
			tfPtNo.setBounds(new java.awt.Rectangle(323, 30, 127, 22));
			tfPtNo.setEditable(false);
		}
		return tfPtNo;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtName() {
		if (tfPtName == null) {
			tfPtName = new JTextField();
			tfPtName.setBounds(new java.awt.Rectangle(103, 62, 129, 22));
			tfPtName.setEditable(false);
		}
		return tfPtName;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtSpec() {
		if (tfPtSpec == null) {
			tfPtSpec = new JTextField();
			tfPtSpec.setBounds(new java.awt.Rectangle(323, 59, 127, 22));
			tfPtSpec.setEditable(false);
		}
		return tfPtSpec;
	}

	/**
	 * This method initializes jCustomFormattedTextField
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfUnitWaste() {
		if (tfUnitWaste == null) {
			tfUnitWaste = new JCustomFormattedTextField();
			tfUnitWaste.setBounds(new java.awt.Rectangle(103, 92, 129, 22));
//			tfUnitWaste.setFormatterFactory(getDefaultFormatterFactory());
			tfUnitWaste.getDocument().addDocumentListener(
					new DocumentListenerAdapter());
		}
		return tfUnitWaste;
	}

	/**
	 * This method initializes jCustomFormattedTextField1
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfWaste() {
		if (tfWaste == null) {
			tfWaste = new JCustomFormattedTextField();
			tfWaste.setBounds(new java.awt.Rectangle(323, 89, 127, 22));
//			tfWaste.setFormatterFactory(getDefaultFormatterFactory());
			tfWaste.getDocument().addDocumentListener(
					new DocumentListenerAdapter());
		}
		return tfWaste;
	}

	/**
	 * This method initializes jCustomFormattedTextField2
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfUnitUsed() {
		if (tfUnitUsed == null) {
			tfUnitUsed = new JCustomFormattedTextField();
			tfUnitUsed.setBounds(new java.awt.Rectangle(103, 118, 129, 22));
//			tfUnitUsed.setFormatterFactory(getDefaultFormatterFactory());
			tfUnitUsed.setEditable(false);
		}
		return tfUnitUsed;
	}

	private void calUnitUsed() {
		// if(!this.checkData()){
		// return;
		// }
		double unitWaste = this.tfUnitWaste.getValue() == null ? 0 : Double
				.parseDouble(this.tfUnitWaste.getValue().toString());
		double waste = this.tfWaste.getValue() == null ? 0 : Double
				.parseDouble(this.tfWaste.getValue().toString());
		double unitUsed = unitWaste / (1 - waste);
		this.tfUnitUsed.setValue(unitUsed);
	}

	class DocumentListenerAdapter implements DocumentListener {

		public void insertUpdate(DocumentEvent e) {
			calUnitUsed();
		}

		public void removeUpdate(DocumentEvent e) {
			calUnitUsed();
		}

		public void changedUpdate(DocumentEvent e) {
			calUnitUsed();
		}
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setBounds(new java.awt.Rectangle(155, 210, 76, 27));
			btnPrevious.setMnemonic(java.awt.event.KeyEvent.VK_UP);
			btnPrevious.setText("上笔↑");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!checkData()) {
						return;
					}
					// dataState=DataState.BROWSE ;
					// setState();
					fillData(detail);
					detail = materialManageAction.saveEnterpriseBomManage(
							new Request(CommonVars.getCurrUser()), detail);

					if (!tableModel.previousRow()) {
						btnPrevious.setEnabled(false);
						btnNext.setEnabled(true);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}
					showData();
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnPrevious;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setBounds(new java.awt.Rectangle(232, 210, 76, 27));
			btnNext.setMnemonic(java.awt.event.KeyEvent.VK_DOWN);
			btnNext.setText("下笔↓");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!checkData()) {
						return;
					}
					// dataState=DataState.BROWSE ;
					// setState();
					fillData(detail);
					detail = materialManageAction.saveEnterpriseBomManage(
							new Request(CommonVars.getCurrUser()), detail);

					if (!tableModel.nextRow()) {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(false);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}

					showData();
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnNext;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfChildVersionNo() {
		if (tfChildVersionNo == null) {
			tfChildVersionNo = new JTextField();
			tfChildVersionNo
					.setBounds(new java.awt.Rectangle(323, 118, 127, 24));
			tfChildVersionNo.setEditable(true);
		}
		return tfChildVersionNo;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbChildEffectDate() {
		if (cbbChildEffectDate == null) {
			cbbChildEffectDate = new JCalendarComboBox();
			cbbChildEffectDate.setBounds(new java.awt.Rectangle(103, 149, 129,
					22));
		}
		return cbbChildEffectDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbChildEndDate() {
		if (cbbChildEndDate == null) {
			cbbChildEndDate = new JCalendarComboBox();
			cbbChildEndDate
					.setBounds(new java.awt.Rectangle(323, 149, 127, 22));
		}
		return cbbChildEndDate;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
