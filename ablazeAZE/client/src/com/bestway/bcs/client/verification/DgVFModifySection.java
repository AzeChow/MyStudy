/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.verification;

 
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;




import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;













import com.bestway.bcs.verification.action.VFVerificationAction;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.apache.commons.lang.time.DateUtils;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgVFModifySection extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel jPanel2 = null;

	private JButton btnOK = null;

	private JButton btnCancel = null;
	
//	private FmVFSection vf;
	
	private boolean isAdd = true;
	
	private JTableListModel tableMode;
	
	private VFSection section;

	private int dataState = DataState.EDIT;
	
	
	public JTableListModel getTableModel() {
		return tableMode;
	}

	public void setTableModel(JTableListModel tableMode) {
		this.tableMode = tableMode;
	}

	public boolean isAdd() {
		return isAdd;
	}

	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}



	private VFVerificationAction vfAction = null;

//	public FmVFSection getVf() {
//		return vf;
//	}
//	
//	public void setVf(FmVFSection fmVFSection) {
//		this.vf = fmVFSection;
//	}

	/**
	 * This is the default constructor
	 */
	public DgVFModifySection(int dataState) {
		super();
		this.dataState = dataState;
		initialize();
		vfAction = (VFVerificationAction) CommonVars.getApplicationContext().getBean(
				"vfVerificationAction");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(330, 323);
		this.setTitle("修改批次号");
		this.setContentPane(getJPanel2());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				if (isAdd == false) {
//					cbbBeginDate.setVisible(false);
//					comboBox.setVisible(false);
//					tfMemo.setVisible(false);
					if (tableMode.getCurrentRow() != null) {
						section = (VFSection) tableMode.getCurrentRow();
					}
				fillWindow();
			}
			}
		});
		getJPanel2().add(getLblNewLabel());
		getJPanel2().add(getTfMemo());
		getJPanel2().add(getCbbBeginDate());
		getJPanel2().add(getcheckBox());
	}

	public void fillWindow(){
		if(section != null){
			this.cbbBeginDate.setDate(section.getEndDate());
			if(section.getIsImportHs() == true){
				this.comboBox.setSelectedIndex(1);
			}else{
				this.comboBox.setSelectedIndex(0);
			}
			this.tfMemo.setText(section.getMemo());
			if (section.getBuyIsCount() != null)
				this.cbBuyImgIsCount.setSelected(section.getBuyIsCount());
		}
	}
	
	private JPanel getJPanel2() {
		if (jPanel2 == null) {

			javax.swing.JLabel jLabel = new JLabel();
			jLabel.setHorizontalAlignment(SwingConstants.RIGHT);

			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jLabel.setText("\u7ED3\u675F\u65F6\u95F4");
			jLabel.setBounds(new Rectangle(30, 38, 48, 23));

			jPanel2.add(jLabel);
			
			jPanel2.add(getBtnOK());
			jPanel2.add(getBtnCancel());
			
			JLabel label = new JLabel("结转数据导入方式");
			label.setBounds(30, 84, 131, 15);
			jPanel2.add(label);
			
			
			jPanel2.add(getComboBox());
		}
		return jPanel2;
	}
	
	private JComboBox comboBox;
	private JLabel lblNewLabel;
	private JCheckBox cbBuyImgIsCount;
	private JTextField tfMemo;
	private JCalendarComboBox cbbBeginDate;
	private JComboBox getComboBox() {
		if(comboBox == null) {
			comboBox = new JComboBox();
			comboBox.addItem("料号级");
			comboBox.addItem("编码级");
			comboBox.setBounds(136, 80, 144, 23);
			comboBox.setEnabled(false);
		}
		return comboBox;
	}

	/**
	 * 
	 * This method initializes jButton
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setText("确定");
			btnOK.setBounds(new Rectangle(68, 223, 69, 26));
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
//					boolean isExists = checkRepeat(cbbBeginDate.getDate());
					try{
//						if(isExists){
//							getData();
//							fillWindow();
//							VFSection vfSection= 
							if (DgVFModifySection.this.cbBuyImgIsCount.isSelected()) {
								section.setBuyIsCount(new Boolean(true));
							} else {
								section.setBuyIsCount(new Boolean(false));
							}
							section.setMemo(tfMemo.getText().trim());
							section = vfAction.saveVFModifySection(new Request(CommonVars.getCurrUser()), section);
//							saveData(cbbBeginDate.getDate(), comboBox.getSelectedItem(),section.getMemo(),section.getBuyIsCount());
							JOptionPane.showMessageDialog(DgVFModifySection.this, "数据保存成功！", "提示!",JOptionPane.INFORMATION_MESSAGE);
							tableMode.updateRow(section);
//						}
					}catch (Exception ex){
						JOptionPane.showMessageDialog(DgVFModifySection.this, "数据保存失败！","info", JOptionPane.INFORMATION_MESSAGE);
					}finally{
//						if(isExists){
							dispose();
//						}
					}
				}
			});
		}
		return btnOK;
	}

//	private void getData(){
//		if (this.cbBuyImgIsCount.isSelected()) {
//			section.setBuyIsCount(new Boolean(true));
//		} else {
//			section.setBuyIsCount(new Boolean(false));
//		}
//		section.setMemo(tfMemo.getText().trim());
//	}
	/**
	 * 
	 * This method initializes jButton1
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.setBounds(new Rectangle(166, 223, 73, 25));
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgVFModifySection.this.dispose();
				}
			});
		}
		return btnCancel;
	}

	private boolean checkRepeat(Date date){
		VFSection vf = new VFSection();
		vf.setEndDate(date);
		List<VFSection> list = vfAction.findExistsVFSection(new Request(CommonVars.getCurrUser()), vf);
		if(!list.isEmpty()){
			JOptionPane.showMessageDialog(DgVFModifySection.this, "截止时间不允许与其他批次截止时间重复！", "提示!",JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		return true;
	}

	/**
	 * 保存数据
	 * 
	 */
//	private void saveData(Date date, Object importType,String memo,Boolean buyIsCount) {
//		VFSection vf = new VFSection();
//		vf.setCreateDate(date);
//		vf.setEndDate(date);
//		vf.setCreatePeople(CommonVars.getCurrUser().getLoginName());
//		vf.setMemo(memo);
//		vf.setBuyIsCount(buyIsCount);
//		if("编码级".equals(importType)) {
//			vf.setIsImportHs(Boolean.TRUE);
//		}
//		vf = vfAction.saveVFModifySection(new Request(CommonVars.getCurrUser()), vf);
//		tableMode.updateRow(vf);
//	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("备注");
			lblNewLabel.setBounds(30, 131, 54, 15);
		}
		return lblNewLabel;
	}
	private JCheckBox getcheckBox() {
		if (cbBuyImgIsCount == null) {
			cbBuyImgIsCount = new JCheckBox();
			cbBuyImgIsCount.setBounds(30, 177, 194, 23);	
			cbBuyImgIsCount.setText("内购库存参与统计分析");
			cbBuyImgIsCount.addItemListener(new ItemListener(){
                public void itemStateChanged(ItemEvent e){
                	section.setBuyIsCount(cbBuyImgIsCount.isSelected());
                }
           });
		}
		return cbBuyImgIsCount;
	}
	private JTextField getTfMemo() {
		if (tfMemo == null) {
			tfMemo = new JTextField();
			tfMemo.setBounds(136, 128, 144, 21);
			tfMemo.setColumns(10);
		}
		return tfMemo;
	}
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(133, 39, 147, 21);
			cbbBeginDate.setDate(DateUtils.truncate(new Date(), Calendar.DATE));
			cbbBeginDate.setEnabled(false);
		}
		return cbbBeginDate;
	}

	public VFSection getSection() {
		return section;
	}

	public void setSection(VFSection section) {
		this.section = section;
	}
} 
