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
import com.bestway.common.materialbase.entity.MaterialBomDetail;
import com.bestway.common.materialbase.entity.MaterialBomVersion;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgCustomBomDetail extends JDialogBase {

	private JButton btnSave = null;

	private JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JCustomFormattedTextField tfBomVersion = null;

	private JButton btnCancel = null;

//	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:visual-constraint=""

//	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:visual-constraint=""

	private MaterialManageAction materialManageAction = null;

	private boolean isOk = false;

	private MaterialBomVersion version = null;

	private MaterialBomDetail detail = null;  //  @jve:decl-index=0:

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

	public MaterialBomDetail getDetail() {
		return detail;
	}

	public void setDetail(MaterialBomDetail detail) {
		this.detail = detail;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dateState) {
		this.dataState = dateState;
	}

	public MaterialBomVersion getVersion() {
		return version;
	}

	public void setVersion(MaterialBomVersion version) {
		this.version = version;
	}

	public boolean isOk() {
		return isOk;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgCustomBomDetail() {
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
    private void  setState(){
    	this.tfUnitUsed.setEditable( this.dataState !=DataState.BROWSE );
    	this.tfWaste.setEditable( this.dataState !=DataState.BROWSE );
    	this.tfUnitWaste.setEditable( this.dataState !=DataState.BROWSE );
    	this.tfNote.setEditable( this.dataState !=DataState.BROWSE );
    	this.tfBomVersion.setEditable( this.dataState !=DataState.BROWSE ); 
    	 this.btnSave.setEnabled(this.dataState!=DataState.BROWSE  );
    }
	@Override
	public void setVisible(boolean b) {
		if (b) {
			this.showData();
		}
		super.setVisible(b);
	}
	public void setTableModel(	JTableListModel tableModel){
		this.tableModel =tableModel;
		
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(353,314));
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
			btnSave.setBounds(new java.awt.Rectangle(19,232,63,25));
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!checkData()) {
						return;
					}
					dataState=DataState.BROWSE ;
					setState();
					fillData(detail);
					
					 long beginTime=System.currentTimeMillis();
					detail = materialManageAction.saveMaterielBomDetail(
							new Request(CommonVars.getCurrUser()), detail);
					long endTime=System.currentTimeMillis();
					System.out.println("------Save Time :"+(endTime-beginTime)/1000.0);
					
					tableModel.updateRow(detail);
					isOk = true;
//					dispose();
				}
			});
		}
		return btnSave;
	}
	private boolean checkData() {
		if (tfWaste.getValue() != null) {
			if (Double.parseDouble(tfWaste.getValue().toString()) >= 1) {
				JOptionPane.showMessageDialog(DgCustomBomDetail.this,
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
			jLabel7 = new JLabel();
			jLabel7.setBounds(new java.awt.Rectangle(49, 200, 70, 21));
			jLabel7.setText("备注");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new java.awt.Rectangle(49, 175, 70, 21));
			jLabel6.setText("单项用量");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(49, 150, 70, 21));
			jLabel5.setText("损耗");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(49, 125, 70, 21));
			jLabel4.setText("单耗");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(49, 99, 70, 21));
			jLabel3.setText("料件规格");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(49, 74, 70, 21));
			jLabel2.setText("料件名称");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(49, 48, 70, 21));
			jLabel1.setText("料件号码");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(49, 24, 70, 21));
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
			tfBomVersion.setBounds(new java.awt.Rectangle(153,22,141,24));
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
			btnCancel.setBounds(new java.awt.Rectangle(250,232,60,24));
			btnCancel.setText("关闭");
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
//			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
//			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
////			defaultFormatterFactory.setNullFormatter(getNumberFormatter());
////			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
//		}
//		return defaultFormatterFactory;
//	}

	/**
	 * This method initializes numberFormatter
	 * 
	 * @return javax.swing.text.NumberFormatter
	 */
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
       detail = (MaterialBomDetail) tableModel
		.getCurrentRow();
		if (detail == null) {
			return;
		}
		this.tfBomVersion.setValue(detail.getVersion().getVersion());
		this.tfPtNo.setText(detail.getMateriel().getPtNo());
		this.tfPtName.setText(detail.getMateriel().getFactoryName());
		this.tfPtSpec.setText(detail.getMateriel().getFactorySpec());
		this.tfUnitWaste.setValue(detail.getUnitWaste());
		this.tfWaste.setValue(detail.getWaste());
		this.tfUnitUsed.setValue(detail.getUnitUsed());
		this.tfNote.setText(detail.getNote());
	}

	private void fillData(MaterialBomDetail detail) {
		detail.setUnitWaste(this.tfUnitWaste.getValue() == null ? 0 : Double
				.parseDouble(this.tfUnitWaste.getText().toString()));
		detail.setWaste(this.tfWaste.getValue() == null ? 0 : Double
				.parseDouble(this.tfWaste.getText().toString()));
		detail.setUnitUsed(this.tfUnitUsed.getValue() == null ? 0 : Double
				.parseDouble(this.tfUnitUsed.getText().toString()));
		detail.setNote(this.tfNote.getText());
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtNo() {
		if (tfPtNo == null) {
			tfPtNo = new JTextField();
			tfPtNo.setBounds(new java.awt.Rectangle(153,49,141,24));
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
			tfPtName.setBounds(new java.awt.Rectangle(153,75,141,24));
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
			tfPtSpec.setBounds(new java.awt.Rectangle(153,101,141,24));
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
			tfUnitWaste.setBounds(new java.awt.Rectangle(153,128,141,24));
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
			tfWaste.setBounds(new java.awt.Rectangle(153,149,141,24));
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
			tfUnitUsed.setBounds(new java.awt.Rectangle(152,174,141,24));
//			tfUnitUsed.setFormatterFactory(getDefaultFormatterFactory());
			tfUnitUsed.setEditable(false);
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
			tfNote.setBounds(new java.awt.Rectangle(152,200,142,24));
		}
		return tfNote;
	}

	private void calUnitUsed() {
//		if (!checkData()) {
//			return;
//		}
		double unitWaste = this.tfUnitWaste.getValue() == null ? 0 : Double
				.parseDouble(this.tfUnitWaste.getValue().toString());
		double waste = this.tfWaste.getValue() == null ? 0 : Double
				.parseDouble(this.tfWaste.getValue().toString());
		double unitUsed = unitWaste / (1 - waste );
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
			btnPrevious.setBounds(new java.awt.Rectangle(94,232,71,24));
			btnPrevious.setMnemonic(java.awt.event.KeyEvent.VK_UP);
			btnPrevious.setText("上笔↑");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!checkData()) {
						return;
					}
//					dataState=DataState.BROWSE ;
//					setState();
					fillData(detail);
					detail = materialManageAction.saveMaterielBomDetail(
							new Request(CommonVars.getCurrUser()), detail);    
					
					if (!tableModel.previousRow()) {
						btnPrevious.setEnabled(false);
						btnNext.setEnabled(true);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}
					showData();
					dataState = DataState.EDIT ;
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
			btnNext.setBounds(new java.awt.Rectangle(169,232,72,24));
			btnNext.setMnemonic(java.awt.event.KeyEvent.VK_DOWN);
			btnNext.setText("下笔↓");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!checkData()) {
						return;
					}
//					dataState=DataState.BROWSE ;
//					setState();
					fillData(detail);
					detail = materialManageAction.saveMaterielBomDetail(
							new Request(CommonVars.getCurrUser()), detail);
					
						if (!tableModel.nextRow()) {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(false);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}
					
					showData();
					dataState = DataState.EDIT ;
					setState();
				}
			});
		}
		return btnNext;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
