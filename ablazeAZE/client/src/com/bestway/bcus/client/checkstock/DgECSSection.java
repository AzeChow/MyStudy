/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.checkstock;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.checkcancel.entity.CancelOwnerHead;
import com.bestway.bcus.checkstock.action.ECSCheckStockAction;
import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.DateValueListener;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

import javax.swing.JCheckBox;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
/**
 * @author Administratorf
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgECSSection extends JDialogBase {

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JTextField tfMemo = null;

	private JButton btnOk = null;

	private JButton jButton1 = null;

	private JTableListModel tableModel = null;

	private ECSCheckStockAction ecsCheckStockAction = null;

	private JTextField tfCancelHeadNo = null;
	
	private JCalendarComboBox jCalendarComboBox = null;
	
	private JCalendarComboBox jCalendarComboBox1 = null;
	
	private CancelOwnerHead head;
	
	private ECSSection section;
	/**
	 * 编辑状态
	 */
	private int dataState = DataState.ADD;
	private JLabel label;
	private JTextField tfSection;
	private JLabel label_1;
	private JComboBox cbbJImport;
	
	/**
	 * This is the default constructor
	 */
	public DgECSSection(int dataState) {
		super();
		this.dataState = dataState;
		ecsCheckStockAction = (ECSCheckStockAction)CommonVars.getApplicationContext().getBean("ecsCheckStockAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJPanel2());
		this.setSize(351, 413);
		this.setTitle("账册盘点核查批次编辑");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {				
				if (dataState != DataState.ADD && tableModel.getCurrentRow() != null) {
					section = (ECSSection) tableModel.getCurrentRow();
				}
				fillWindow();
			}
		});
		getJPanel2().add(getLabel());
		getJPanel2().add(getTfSection());
		getJPanel2().add(getLabel_1());
		getJPanel2().add(getCbbJImport());
		getJPanel2().add(getCbbPImport());
		getJPanel2().add(getCbbPriceImport());
		getJPanel2().add(getcheckBox());
		
	}
	
	public void setHead(CancelOwnerHead head) {
		this.head = head;
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			javax.swing.JLabel jLabel3 = new JLabel();
			java.awt.GridBagConstraints gridBagConstraints10 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints9 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints8 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints7 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints6 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints5 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints4 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints3 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints2 = new GridBagConstraints();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jLabel.setText("自用核销报核次数");
			jLabel.setBounds(26, 14, 97, 23);
			jLabel2.setText("核查起始日期");
			jLabel2.setBounds(26, 88, 97, 23);
			jLabel1.setText("核查截止日期");
			jLabel1.setBounds(26, 125, 97, 23);
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.gridwidth = 2;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints2.ipadx = 106;
			gridBagConstraints2.insets = new java.awt.Insets(25, 1, 5, 44);
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.ipadx = 9;
			gridBagConstraints3.ipady = 5;
			gridBagConstraints3.insets = new java.awt.Insets(5, 42, 6, 25);
			gridBagConstraints4.gridx = 1;
			gridBagConstraints4.gridy = 1;
			gridBagConstraints4.gridwidth = 2;
			gridBagConstraints4.weightx = 1.0;
			gridBagConstraints4.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints4.ipadx = 105;
			gridBagConstraints4.insets = new java.awt.Insets(7, 1, 5, 45);
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 3;
			gridBagConstraints5.ipadx = 13;
			gridBagConstraints5.ipady = -2;
			gridBagConstraints5.insets = new java.awt.Insets(8, 48, 18, 5);
			gridBagConstraints6.gridx = 1;
			gridBagConstraints6.gridy = 3;
			gridBagConstraints6.ipadx = 12;
			gridBagConstraints6.ipady = -3;
			gridBagConstraints6.insets = new java.awt.Insets(9, 37, 18, 0);
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.gridy = 2;
			gridBagConstraints7.ipadx = 10;
			gridBagConstraints7.ipady = 4;
			gridBagConstraints7.insets = new java.awt.Insets(6, 41, 8, 1);
			gridBagConstraints8.gridx = 1;
			gridBagConstraints8.gridy = 2;
			gridBagConstraints8.gridwidth = 2;
			gridBagConstraints8.weightx = 1.0;
			gridBagConstraints8.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints8.ipadx = 104;
			gridBagConstraints8.insets = new java.awt.Insets(5, 2, 9, 45);
			gridBagConstraints9.gridx = 2;
			gridBagConstraints9.gridy = 2;
			gridBagConstraints9.ipadx = -18;
			gridBagConstraints9.ipady = -7;
			gridBagConstraints9.insets = new java.awt.Insets(5, 1, 10, 22);
			gridBagConstraints10.gridx = 1;
			gridBagConstraints10.gridy = 0;
			gridBagConstraints10.gridwidth = 2;
			gridBagConstraints10.weightx = 1.0;
			gridBagConstraints10.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints10.ipadx = 104;
			gridBagConstraints10.insets = new java.awt.Insets(23,1,7,46);
			jLabel3.setBounds(26, 310, 97, 23);
			jLabel3.setText("备注");
			jPanel2.add(jLabel, null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(getJTextField2(), null);
			jPanel2.add(getBtnOk(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(getTfCancelHeadNo(), null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(getJCalendarComboBox(), null);
			jPanel2.add(getJCalendarComboBox1(), null);
			
			JLabel label_2 = new JLabel("结转数据导入方式");
			label_2.setBounds(26, 199, 97, 23);
			jPanel2.add(label_2);
			
			JLabel label_3 = new JLabel("短溢分析单价来源");
			label_3.setBounds(26, 236, 97, 23);
			jPanel2.add(label_3);
			
			JLabel lblNewLabel = new JLabel("内购库存参与统计分析");
			lblNewLabel.setBounds(25, 273, 130, 23);
			jPanel2.add(lblNewLabel);
		}
		return jPanel2;
	}


	/**
	 * 
	 * This method initializes jTextField2
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getJTextField2() {
		if (tfMemo == null) {
			tfMemo = new JTextField();
			tfMemo.setBounds(126, 310, 180, 23);
		}
		return tfMemo;
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
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setText("确定");
			btnOk.setBounds(67, 347, 71, 23);
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Request request =  new Request(CommonVars.getCurrUser());
					if(validateData()){
						ECSSection section = getData();												
						if(dataState == DataState.ADD){
							section = ecsCheckStockAction.saveEcsSection(request, section);
							tableModel.addRow(section);
						}else{
							if(ecsCheckStockAction.isExistEcsCheckDataBySection(request,section)){
								if(JOptionPane.showOptionDialog(DgECSSection.this,"本批批已存在盘查数据，是否继续修改批次信息?","提示",
										JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null, new Object[]{"是","否"},"否")
										!= JOptionPane.YES_OPTION)
								return;
							}
							section = ecsCheckStockAction.saveEcsSection(request, section);
							tableModel.updateRow(section);
						}
						DgECSSection.this.dispose();
					}
				}
			});

		}
		return btnOk;
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
			jButton1.setText("取消");
			jButton1.setBounds(205, 347, 70, 23);
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgECSSection.this.dispose();
				}
			});

		}
		return jButton1;
	}

	/**
	 * 填充界面数据
	 */
	private void fillWindow() {
		if (section != null) {
			tfCancelHeadNo.setText(section.getCancelOwnerHead().getCancelTimes());
			tfSection.setText(CommonUtils.getStringFromInteger(section.getVerificationNo()));
			jCalendarComboBox.setDate(section.getBeginDate());
			jCalendarComboBox1.setDate(section.getEndDate());
			tfMemo.setText(section.getMemo());
			if(Boolean.TRUE.equals(section.getTransImportIsHs())){
				cbbJImport.setSelectedIndex(1);
			}else{
				cbbJImport.setSelectedIndex(0);
			}
			if(Boolean.FALSE.equals(section.getStockImportIsHs()))
				cbbPImport.setSelectedIndex(0);
			else
				cbbPImport.setSelectedIndex(1);
			cbbPriceImport.setSelectedIndex(section.getPriceFromType()== null ? -1 : (section.getPriceFromType()-1));
			cbBuyImgIsCount.setSelected(Boolean.TRUE.equals(section.getBuyIsCount()));
		}else{
			cbbJImport.setSelectedIndex(1);
			cbbPImport.setSelectedIndex(0);
			tfCancelHeadNo.setText(head.getCancelTimes());
			jCalendarComboBox.setDate(head.getBeginDate());
			jCalendarComboBox1.setDate(null);
			cbbJImport.setSelectedIndex(0);
			
		}
	}
	/**
	 * 验证界面数据
	 * @return
	 */
	private boolean validateData(){
		if(jCalendarComboBox.getDate() == null){
			JOptionPane.showMessageDialog(this, "【核查起始日期】不能为空！","提示",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(jCalendarComboBox1.getDate() == null){
			JOptionPane.showMessageDialog(this, "【核查截止日期】不能为空！","提示",JOptionPane.WARNING_MESSAGE);
			return false;
		}else if(jCalendarComboBox1.getDate().before(jCalendarComboBox.getDate())){
			JOptionPane.showMessageDialog(this, "【核查截止日期】需在【自用核销报核周期之内】！","提示",JOptionPane.WARNING_MESSAGE);
			return false;
		}else if(jCalendarComboBox1.getDate().after(head.getEndDate())){
			JOptionPane.showMessageDialog(this, "【核查截止日期】需在【自用核销报核周期之内】！","提示",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
	/**
	 * 获取界面数据
	 * @return
	 */
   private ECSSection getData(){
		if(dataState == DataState.ADD){
			section = new ECSSection();
			section.setCancelOwnerHead(head);
		}else{
			section = (ECSSection)tableModel.getCurrentRow();
		}
		section.setBeginDate(this.jCalendarComboBox.getDate());
		section.setEndDate(this.jCalendarComboBox1.getDate());
		section.setMemo(this.tfMemo.getText());
		
		section.setBuyIsCount(this.cbBuyImgIsCount.isSelected());
		section.setPriceFromType(this.getValueForInt(this.cbbPriceImport.getSelectedItem().toString()));
		section.setTransImportIsHs( cbbJImport.getSelectedIndex() == 1);
		section.setStockImportIsHs(this.cbbPImport.getSelectedIndex() == 1);

		return section;
   }
	private Integer getValueForInt(String string) {
		Integer i = 0;		
		if ("本报核周期平均单价".equals(string)) {
			i = 1;
		}else if ("上一期报核周期平均单价".equals(string)) {
			i = 2;
		}else if("报关单单价".equals(string)) {
			i = 3;
		}
		return i;
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

	 * This method initializes jtcode	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getTfCancelHeadNo() {
		if (tfCancelHeadNo == null) {
			tfCancelHeadNo = new JTextField();
			tfCancelHeadNo.setEditable(false);
			tfCancelHeadNo.setBounds(126, 14, 180, 23);
		}
		return tfCancelHeadNo;
	}

	/**
	 * @return Returns the jtname.
	 */
	public JTextField getJtname() {
		return tfMemo;
	}
	/**
	 * @param jtname The jtname to set.
	 */
	public void setJtname(JTextField jtname) {
		this.tfMemo = jtname;
	}
	/**
	 * This method initializes jCalendarComboBox	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */    
	private JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox.setEnabled(true);
			jCalendarComboBox.setBounds(126, 88, 180, 23);
		}
		return jCalendarComboBox;
	}
	/**
	 * This method initializes jCalendarComboBox1	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */    
	private JCalendarComboBox getJCalendarComboBox1() {
		if (jCalendarComboBox1 == null) {
			jCalendarComboBox1 = new JCalendarComboBox();
			jCalendarComboBox1.addDateValueListener(new DateValueListener() {
				public void valueChanged(Date newDate) {					
					newDate =DateUtils.truncate(newDate, Calendar.DATE);
					if(head != null && newDate.after(head.getEndDate())){
						jCalendarComboBox1.setBackground(Color.RED);
						jCalendarComboBox1.setToolTipText("<html><font color='RED'> 不允许在本次自用核销报核结束日期之后，请重新选择！</font></html>");
					}else{
						jCalendarComboBox1.setBackground(Color.WHITE);
						jCalendarComboBox1.setToolTipText(null);
					}
				}
			});
			jCalendarComboBox1.setBounds(126, 125, 180, 23);
		}
		return jCalendarComboBox1;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel();
			label.setText("盘点核查批次");
			label.setBounds(26, 51, 97, 23);
		}
		return label;
	}
	private JTextField getTfSection() {
		if (tfSection == null) {
			tfSection = new JTextField();
			tfSection.setEditable(false);
			tfSection.setBounds(126, 51, 180, 23);
		}
		return tfSection;
	}
	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel();
			label_1.setText("盘点数据导入方式");
			label_1.setBounds(26, 162, 97, 23);
		}
		return label_1;
	}
	
	
	private JComboBox getCbbJImport() {
		if (cbbJImport == null) {
			cbbJImport = new JComboBox();
			cbbJImport.setBounds(126, 199, 180, 23);			
			cbbJImport.setModel(new DefaultComboBoxModel(new String[]{"料号级","编码级"}));
		}
		return cbbJImport;
	}
	
	private JComboBox getCbbPImport() {
		if (cbbPImport == null) {
			cbbPImport = new JComboBox();
			cbbPImport.setBounds(126, 162, 180, 23);		
			cbbPImport.setModel(new DefaultComboBoxModel(new String[]{"料号级","编码级"}));
		}
		return cbbPImport;
	}
	
	private JComboBox getCbbPriceImport() {
		if (cbbPriceImport == null) {
			cbbPriceImport = new JComboBox();
			cbbPriceImport.setBounds(126, 236, 180, 23);		
			cbbPriceImport.setModel(new DefaultComboBoxModel(new String[]{"本报核周期平均单价","上一期报核周期平均单价","报关单单价"}));
		}
		return cbbPriceImport;
	}
	
	private JCheckBox getcheckBox() {
		if (cbBuyImgIsCount == null) {
			cbBuyImgIsCount = new JCheckBox();
			cbBuyImgIsCount.setBounds(147, 273, 21, 23);	
		}
		return cbBuyImgIsCount;
	}
private JComboBox cbbPImport;
private JComboBox cbbPriceImport;
private JCheckBox cbBuyImgIsCount;
	
 }
