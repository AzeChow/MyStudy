package com.bestway.client.owp;

import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.MaterialBomDetail;
import com.bestway.common.materialbase.entity.MaterialBomVersion;
import com.bestway.owp.action.OwpAnalyAction;
import com.bestway.owp.entity.OwpBomDetail;
import com.bestway.owp.entity.OwpBomVersion;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgOwpBomDetail extends JDialogBase {

	private JButton btnSave = null;

	private JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JCustomFormattedTextField tfBomVersion = null;

	private JButton btnCancel = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:visual-constraint=""

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:visual-constraint=""

	private OwpAnalyAction owpAnalyAction = null;

	private boolean isOk = false;

	private OwpBomVersion version = null;  //  @jve:decl-index=0:

	private OwpBomDetail detail = null;  //  @jve:decl-index=0:

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

	public OwpBomDetail getDetail() {
		return detail;
	}

	public void setDetail(OwpBomDetail detail) {
		this.detail = detail;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dateState) {
		this.dataState = dateState;
	}

	public OwpBomVersion getVersion() {
		return version;
	}

	public void setVersion(OwpBomVersion version) {
		this.version = version;
	}

	public boolean isOk() {
		return isOk;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgOwpBomDetail() {
		super();
		initialize();
		owpAnalyAction = (OwpAnalyAction) CommonVars
				.getApplicationContext().getBean("owpAnalyAction");
	}
    private void  setState(){
    	this.tfUnitUsed.setEditable( this.dataState !=DataState.BROWSE );
    	this.tfWaste.setEditable( this.dataState !=DataState.BROWSE );
    	this.tfUnitWaste.setEditable( this.dataState !=DataState.BROWSE );
    	this.tfNote.setEditable( this.dataState !=DataState.BROWSE );
    	this.tfBomVersion.setEditable( this.dataState !=DataState.BROWSE ); 
    	 this.btnSave.setEnabled(this.dataState!=DataState.BROWSE  );
    }
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
					detail = owpAnalyAction.saveMaterielBomDetail(
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
				JOptionPane.showMessageDialog(DgOwpBomDetail.this,
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
			DecimalFormat decimalFormat = new DecimalFormat(); // @jve:decl-index=0:
			decimalFormat.setMaximumFractionDigits(999);
			numberFormatter.setFormat(decimalFormat);
		}
		return numberFormatter;
	}

	private void showData() {
       detail = (OwpBomDetail) tableModel
		.getCurrentRow();
		if (detail == null) {
			return;
		}
		this.tfBomVersion.setValue(detail.getVersion().getVersion());
		this.tfPtNo.setText(detail.getCompentNo());
		this.tfPtName.setText(detail.getCompentName());
		this.tfPtSpec.setText(detail.getCompentSpec());
		this.tfUnitWaste.setValue(detail.getUnitWare());
		this.tfWaste.setValue(detail.getWare());
		this.tfUnitUsed.setValue(detail.getUnitUsed());
		this.tfNote.setText(detail.getNotes());
	}

	private void fillData(OwpBomDetail detail) {
		detail.setUnitWare(this.tfUnitWaste.getValue() == null ? 0 : Double
				.parseDouble(this.tfUnitWaste.getValue().toString()));
		detail.setWare(this.tfWaste.getValue() == null ? 0 : Double
				.parseDouble(this.tfWaste.getValue().toString()));
		detail.setUnitUsed(this.tfUnitUsed.getValue() == null ? 0 : Double
				.parseDouble(this.tfUnitUsed.getValue().toString()));
		detail.setNotes(this.tfNote.getText());
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
			DecimalFormat decimalFormat10 = new DecimalFormat();
			decimalFormat10.setGroupingSize(0);
			decimalFormat10.setMaximumFractionDigits(6);
			NumberFormatter numberFormatter10 = new NumberFormatter();
			numberFormatter10.setFormat(decimalFormat10);
			DecimalFormat decimalFormat7 = new DecimalFormat();
			decimalFormat7.setGroupingSize(0);
			decimalFormat7.setMaximumFractionDigits(6);
			NumberFormatter numberFormatter7 = new NumberFormatter();
			numberFormatter7.setFormat(decimalFormat7);
			DecimalFormat decimalFormat6 = new DecimalFormat();
			decimalFormat6.setGroupingSize(0);
			decimalFormat6.setMaximumFractionDigits(6);
			NumberFormatter numberFormatter6 = new NumberFormatter();
			numberFormatter6.setFormat(decimalFormat6);
			DecimalFormat decimalFormat1 = new DecimalFormat();
			decimalFormat1.setGroupingSize(0);
			decimalFormat1.setMaximumFractionDigits(6);
			NumberFormatter numberFormatter1 = new NumberFormatter();
			numberFormatter1.setFormat(decimalFormat1);
			DefaultFormatterFactory defaultFormatterFactory1 = new DefaultFormatterFactory();
			defaultFormatterFactory1.setDefaultFormatter(numberFormatter1);
			defaultFormatterFactory1.setDisplayFormatter(numberFormatter7);
			defaultFormatterFactory1.setNullFormatter(numberFormatter10);
			defaultFormatterFactory1.setEditFormatter(numberFormatter6);
			tfUnitWaste = new JCustomFormattedTextField();
			tfUnitWaste.setBounds(new java.awt.Rectangle(153,128,141,24));
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
			DecimalFormat decimalFormat8 = new DecimalFormat();
			decimalFormat8.setGroupingSize(0);
			decimalFormat8.setMaximumFractionDigits(6);
			NumberFormatter numberFormatter8 = new NumberFormatter();
			numberFormatter8.setFormat(decimalFormat8);
			DecimalFormat decimalFormat5 = new DecimalFormat();
			decimalFormat5.setGroupingSize(0);
			decimalFormat5.setMaximumFractionDigits(6);
			NumberFormatter numberFormatter5 = new NumberFormatter();
			numberFormatter5.setFormat(decimalFormat5);
			DecimalFormat decimalFormat4 = new DecimalFormat();
			decimalFormat4.setGroupingSize(0);
			decimalFormat4.setMaximumFractionDigits(6);
			NumberFormatter numberFormatter4 = new NumberFormatter();
			numberFormatter4.setFormat(decimalFormat4);
			DecimalFormat decimalFormat2 = new DecimalFormat();
			decimalFormat2.setGroupingSize(0);
			decimalFormat2.setMaximumFractionDigits(6);
			NumberFormatter numberFormatter2 = new NumberFormatter();
			numberFormatter2.setFormat(decimalFormat2);
			DefaultFormatterFactory defaultFormatterFactory2 = new DefaultFormatterFactory();
			defaultFormatterFactory2.setEditFormatter(numberFormatter2);
			defaultFormatterFactory2.setDisplayFormatter(numberFormatter5);
			defaultFormatterFactory2.setNullFormatter(numberFormatter8);
			defaultFormatterFactory2.setDefaultFormatter(numberFormatter4);
			tfWaste = new JCustomFormattedTextField();
			tfWaste.setBounds(new java.awt.Rectangle(153,149,141,24));
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
			DecimalFormat decimalFormat11 = new DecimalFormat();
			decimalFormat11.setGroupingSize(0);
			decimalFormat11.setMaximumFractionDigits(6);
			NumberFormatter numberFormatter11 = new NumberFormatter();
			numberFormatter11.setFormat(decimalFormat11);
			DecimalFormat decimalFormat9 = new DecimalFormat();
			decimalFormat9.setGroupingSize(0);
			decimalFormat9.setMaximumFractionDigits(6);
			NumberFormatter numberFormatter9 = new NumberFormatter();
			numberFormatter9.setFormat(decimalFormat9);
			DecimalFormat decimalFormat3 = new DecimalFormat();
			decimalFormat3.setGroupingSize(0);
			NumberFormatter numberFormatter3 = new NumberFormatter();
			numberFormatter3.setFormat(decimalFormat3);
			DefaultFormatterFactory defaultFormatterFactory3 = new DefaultFormatterFactory();
			defaultFormatterFactory3.setNullFormatter(new NumberFormatter());
			defaultFormatterFactory3.setDisplayFormatter(numberFormatter9);
			defaultFormatterFactory3.setEditFormatter(numberFormatter11);
			defaultFormatterFactory3.setDefaultFormatter(numberFormatter3);
			tfUnitUsed = new JCustomFormattedTextField();
			tfUnitUsed.setBounds(new java.awt.Rectangle(152,174,141,24));
			tfUnitUsed.setFormatterFactory(defaultFormatterFactory3);
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
					detail = owpAnalyAction.saveMaterielBomDetail(
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
					detail = owpAnalyAction.saveMaterielBomDetail(
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
