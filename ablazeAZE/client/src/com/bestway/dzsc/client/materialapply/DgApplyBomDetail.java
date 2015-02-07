package com.bestway.dzsc.client.materialapply;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.dzsc.materialapply.action.MaterialApplyAction;
import com.bestway.dzsc.materialapply.entity.MaterialBomDetailApply;
import com.bestway.dzsc.materialapply.entity.MaterialBomVersionApply;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Rectangle;

public class DgApplyBomDetail extends JDialogBase {

	private JButton btnSave = null;

	private JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JCustomFormattedTextField tfBomVersion = null;

	private JButton btnCancel = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:visual-constraint=""

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:visual-constraint=""

	private MaterialManageAction materialManageAction = null;

	private MaterialApplyAction materialApplyAction = null;

	private boolean isOk = false;

	private MaterialBomVersionApply version = null;

	private MaterialBomDetailApply detailApply = null;  //  @jve:decl-index=0:

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

	private JLabel jLabel7 = null;

	private JCustomFormattedTextField tfUnitWaste = null;

	private JCustomFormattedTextField tfWaste = null;

	private JCustomFormattedTextField tfUnitUsed = null;

	private JTextField tfNote = null;

	private JButton btnPrevious = null;

	private JButton btnNext = null;

	private JTableListModel tableModel = null;

	private boolean isChange = false;

	private JLabel jLabel8 = null;

	public boolean isChange() {
		return isChange;
	}

	public void setChange(boolean isChange) {
		this.isChange = isChange;
	}

	public MaterialBomDetailApply getDetailApply() {
		return detailApply;
	}

	public void setDetailApply(MaterialBomDetailApply detail) {
		this.detailApply = detail;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dateState) {
		this.dataState = dateState;
	}

	public MaterialBomVersionApply getVersion() {
		return version;
	}

	public void setVersion(MaterialBomVersionApply version) {
		this.version = version;
	}

	public boolean isOk() {
		return isOk;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgApplyBomDetail() {
		super();
		initialize();
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		materialApplyAction = (MaterialApplyAction) CommonVars
				.getApplicationContext().getBean("materialApplyAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			this.showData();
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(373, 315));
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("BOM版本编辑");
		this.setContentPane(getJContentPane());

	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setBounds(new java.awt.Rectangle(29, 234, 62, 25));
			btnSave.setText("确定");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fillData(detailApply);
					// if (dataState == DataState.EDIT) {
					// detail = materialManageAction.saveMaterielBomDetail(
					// new Request(CommonVars.getCurrUser()), detail);
					// } else if (dataState == DataState.CHANGE) {
					detailApply = materialApplyAction
							.saveMaterialBomDetailApply(new Request(CommonVars
									.getCurrUser()), detailApply);
					// }
					tableModel.updateRow(detailApply);

					isOk = true;
					dataState = DataState.BROWSE;
					setState();
					// dispose();
				}
			});
		}
		return btnSave;
	}

	private void setState() {
		this.tfUnitUsed.setEditable(this.dataState != DataState.BROWSE);
		this.tfWaste.setEditable(this.dataState != DataState.BROWSE);
		this.tfUnitWaste.setEditable(this.dataState != DataState.BROWSE);
		this.tfNote.setEditable(this.dataState != DataState.BROWSE);
		this.btnSave.setEnabled(this.dataState != DataState.BROWSE);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(315, 150, 14, 23));
			jLabel8.setText("%");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new java.awt.Rectangle(48, 202, 70, 21));
			jLabel7.setText("备注");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new java.awt.Rectangle(48, 173, 70, 21));
			jLabel6.setText("单项用量");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(48, 148, 70, 21));
			jLabel5.setText("损耗率");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(48, 123, 70, 21));
			jLabel4.setText("单耗");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(48, 97, 70, 21));
			jLabel3.setText("料件规格");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(48, 72, 70, 21));
			jLabel2.setText("料件名称");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(48, 46, 70, 21));
			jLabel1.setText("料件号码");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(48, 22, 70, 21));
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
			jContentPane.add(jLabel7, null);
			jContentPane.add(getTfUnitWaste(), null);
			jContentPane.add(getTfWaste(), null);
			jContentPane.add(getTfUnitUsed(), null);
			jContentPane.add(getTfNote(), null);
			jContentPane.add(getBtnPrevious(), null);
			jContentPane.add(getBtnNext(), null);
			jContentPane.add(jLabel8, null);
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
			tfBomVersion.setBounds(new java.awt.Rectangle(173, 24, 142, 24));
			tfBomVersion.setEditable(false);
			tfBomVersion.setFormatterFactory(getDefaultFormatterFactory());
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
			btnCancel.setBounds(new java.awt.Rectangle(270, 234, 59, 25));
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

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;

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
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
			defaultFormatterFactory.setNullFormatter(getNumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
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

	private void showData() {
		MaterialBomDetailApply detail = (MaterialBomDetailApply) this.tableModel
				.getCurrentRow();
		if (detail == null) {
			return;
		}
		detailApply = detail;
		this.tfBomVersion.setValue(detail.getVersionApply().getVersion());
		this.tfPtNo.setText(detail.getMateriel().getPtNo());
		this.tfPtName.setText(detail.getMateriel().getFactoryName());
		this.tfPtSpec.setText(detail.getMateriel().getFactorySpec());
		this.tfUnitWaste.setValue(detail.getUnitWaste());
		this.tfWaste.setValue(detail.getWaste());
		this.tfUnitUsed.setValue(detail.getUnitUsed());
		this.tfNote.setText(detail.getNote());
	}

	private void fillData(MaterialBomDetailApply detail) {
		detail.setUnitWaste(this.tfUnitWaste.getValue() == null ? 0 : Double
				.parseDouble(this.tfUnitWaste.getValue().toString()));
		detail.setWaste(this.tfWaste.getValue() == null ? 0 : Double
				.parseDouble(this.tfWaste.getValue().toString()));
		detail.setUnitUsed(this.tfUnitUsed.getValue() == null ? 0 : Double
				.parseDouble(this.tfUnitUsed.getValue().toString()));
		detail.setNote(this.tfNote.getText());
//		if (isChange) {
//			if (!ModifyMarkState.ADDED.equals(detail.getModifyMark())) {
//				detail.setModifyMark(ModifyMarkState.MODIFIED);
//			}
//		}
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtNo() {
		if (tfPtNo == null) {
			tfPtNo = new JTextField();
			tfPtNo.setBounds(new java.awt.Rectangle(173, 49, 142, 24));
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
			tfPtName.setBounds(new java.awt.Rectangle(173, 74, 142, 24));
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
			tfPtSpec.setBounds(new java.awt.Rectangle(173, 99, 142, 24));
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
			DefaultFormatterFactory defaultFormatterFactory1 = new DefaultFormatterFactory();
			defaultFormatterFactory1.setNullFormatter(new NumberFormatter());
			defaultFormatterFactory1.setEditFormatter(new NumberFormatter());
			defaultFormatterFactory1.setDefaultFormatter(new NumberFormatter());
			defaultFormatterFactory1.setDisplayFormatter(new NumberFormatter());
			tfUnitWaste = new JCustomFormattedTextField();
			tfUnitWaste.setBounds(new java.awt.Rectangle(173, 124, 142, 24));
			tfUnitWaste.setFormatterFactory(defaultFormatterFactory1);
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
			DefaultFormatterFactory defaultFormatterFactory2 = new DefaultFormatterFactory();
			defaultFormatterFactory2.setNullFormatter(new NumberFormatter());
			defaultFormatterFactory2.setEditFormatter(new NumberFormatter());
			defaultFormatterFactory2.setDefaultFormatter(new NumberFormatter());
			defaultFormatterFactory2.setDisplayFormatter(new NumberFormatter());
			tfWaste = new JCustomFormattedTextField();
			tfWaste.setBounds(new java.awt.Rectangle(173, 149, 142, 24));
			tfWaste.setFormatterFactory(defaultFormatterFactory2);
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
			DefaultFormatterFactory defaultFormatterFactory3 = new DefaultFormatterFactory();
			defaultFormatterFactory3.setNullFormatter(new NumberFormatter());
			defaultFormatterFactory3.setEditFormatter(new NumberFormatter());
			defaultFormatterFactory3.setDefaultFormatter(new NumberFormatter());
			defaultFormatterFactory3.setDisplayFormatter(new NumberFormatter());
			tfUnitUsed = new JCustomFormattedTextField();
			tfUnitUsed.setBounds(new java.awt.Rectangle(173, 174, 142, 24));
			tfUnitUsed.setFormatterFactory(defaultFormatterFactory3);
		}
		return tfUnitUsed;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfNote() {
		if (tfNote == null) {
			tfNote = new JTextField();
			tfNote.setBounds(new java.awt.Rectangle(173, 199, 142, 24));
		}
		return tfNote;
	}

	private void calUnitUsed() {
		double unitWaste = this.tfUnitWaste.getValue() == null ? 0 : Double
				.parseDouble(this.tfUnitWaste.getValue().toString());
		double waste = this.tfWaste.getValue() == null ? 0 : Double
				.parseDouble(this.tfWaste.getValue().toString());
		double unitUsed = unitWaste / (1 - waste/100.0);
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
			btnPrevious.setBounds(new java.awt.Rectangle(101, 234, 74, 25));
			btnPrevious.setMnemonic(java.awt.event.KeyEvent.VK_UP);
			btnPrevious.setText("上笔↑");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fillData(detailApply);
					// if (dataState == DataState.EDIT) {
					// detail = materialManageAction.saveMaterielBomDetail(
					// new Request(CommonVars.getCurrUser()), detail);
					// } else if (dataState == DataState.CHANGE) {
					detailApply = materialApplyAction
							.saveMaterialBomDetailApply(new Request(CommonVars
									.getCurrUser()), detailApply);

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
			btnNext.setBounds(new java.awt.Rectangle(185, 234, 73, 25));
			btnNext.setMnemonic(java.awt.event.KeyEvent.VK_DOWN);
			btnNext.setText("下笔↓");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fillData(detailApply);
					// if (dataState == DataState.EDIT) {
					// detail = materialManageAction.saveMaterielBomDetail(
					// new Request(CommonVars.getCurrUser()), detail);
					// } else if (dataState == DataState.CHANGE) {
					detailApply = materialApplyAction
							.saveMaterialBomDetailApply(new Request(CommonVars
									.getCurrUser()), detailApply);
					tableModel.updateRow(detailApply);

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
} // @jve:decl-index=0:visual-constraint="10,10"
