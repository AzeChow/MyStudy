/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.verification;

import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
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
public class DgVFSection extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel jPanel2 = null;

	private JButton btnOK = null;

	private JButton btnCancel = null;

	// private FmVFSection vf;

	private JTableListModel tableModel;

	private VFSection section;

	private int dataState = DataState.ADD;

	public JTableListModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	private VFVerificationAction vfAction = null;


	/**
	 * This is the default constructor
	 */
	public DgVFSection(int dataState) {
		super();
		this.dataState = dataState;
		vfAction = (VFVerificationAction) CommonVars.getApplicationContext()
				.getBean("vfVerificationAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJPanel2());
		this.setSize(330, 323);
		this.setTitle("批次号");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				
			}
		});

		getJPanel2().add(getLblNewLabel());
		getJPanel2().add(getTfMemo());
		getJPanel2().add(getCbbBeginDate());
		getJPanel2().add(getcheckBox());
	}

	public void setVisible(boolean b) {
		if (b) {
			if (dataState == DataState.EDIT) {
				this.section = (VFSection) this.tableModel.getCurrentRow();
				if(this.section!=null){
					this.tfMemo.setText(this.section.getMemo());
					this.cbBuyImgIsCount.setSelected(this.section.getBuyIsCount() == null ? false:this.section.getBuyIsCount());
					this.cbbBeginDate.setValue(this.section.getEndDate());
					this.comboBox.setSelectedItem(this.section.getIsImportHs()!=null&&this.section.getIsImportHs()?"编码级":"料号级");
				}
			}
		}
		super.setVisible(b);
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
			if(dataState == DataState.EDIT){
				comboBox.setEnabled(false);
				getCbbBeginDate().setEnabled(false);
			}
		}
		return jPanel2;
	}

	private JComboBox comboBox;
	private JLabel lblNewLabel;
	private JCheckBox cbBuyImgIsCount;
	private JTextField tfMemo;
	private JCalendarComboBox cbbBeginDate;

	private JComboBox getComboBox() {
		if (comboBox == null) {
			comboBox = new JComboBox();
			comboBox.addItem("料号级");
			comboBox.addItem("编码级");
			comboBox.setBounds(136, 80, 144, 23);
		}
		return comboBox;
	}

	/**
	 * 
	 * This method initializes jButton
	 * 
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
					// boolean isExists = checkRepeat(cbbBeginDate.getDate());
					try {

						saveData(cbbBeginDate.getDate(), comboBox
								.getSelectedItem(), tfMemo.getText().trim(),
								cbBuyImgIsCount.isSelected());// section.getBuyIsCount()

						JOptionPane.showMessageDialog(DgVFSection.this,
								"数据保存成功！", "提示!",
								JOptionPane.INFORMATION_MESSAGE);
						// }
					} catch (Exception ex) {
						System.out.print(">>>>>>>>>>>>>>>>>>>>" + ex);
						JOptionPane.showMessageDialog(DgVFSection.this,
								"数据保存失败！", "info",
								JOptionPane.INFORMATION_MESSAGE);

					} finally {
						// if(isExists){
						dispose();
						// }
					}
				}
			});
		}
		return btnOK;
	}

	// private void getData(){
	// if(section == null){
	// section = new VFSection();
	// section.setVerificationNo(1);
	// section.setBuyIsCount(false);
	// }
	// if (this.cbBuyImgIsCount.isSelected()) {
	// section.setBuyIsCount(new Boolean(true));
	// } else {
	// section.setBuyIsCount(new Boolean(false));
	// }
	//
	// }
	/**
	 * 
	 * This method initializes jButton1
	 * 
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
					DgVFSection.this.dispose();
				}
			});
		}
		return btnCancel;
	}

	private boolean checkRepeat(Date date) {
		VFSection vf = new VFSection();
		vf.setEndDate(date);
		List<VFSection> list = vfAction.findExistsVFSection(new Request(
				CommonVars.getCurrUser()), vf);
		if (!list.isEmpty()) {
			JOptionPane.showMessageDialog(DgVFSection.this,
					"截止时间不允许与其他批次截止时间重复！", "提示!",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		return true;
	}

	/**
	 * 保存数据
	 * 
	 */
	private void saveData(Date date, Object importType, String memo,
			Boolean buyIsCount) {
		if (dataState == DataState.ADD) {
			section = new VFSection();
		}
		section.setCreateDate(date);
		section.setEndDate(date);
		section.setCreatePeople(CommonVars.getCurrUser().getLoginName());
		section.setMemo(memo);
		section.setBuyIsCount(buyIsCount);
		if ("编码级".equals(importType)) {
			section.setIsImportHs(Boolean.TRUE);
		}
	   section = vfAction.saveVFSection(new Request(CommonVars.getCurrUser()), section);
//		if (dataState == DataState.ADD) {
//			tableModel.addRow(section);
//		} else
	   if (dataState == DataState.EDIT) {
			tableModel.updateRow(section);
		}
	}

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
			// cbBuyImgIsCount.addItemListener(new ItemListener(){
			// public void itemStateChanged(ItemEvent e){
			// if(section == null){
			// section = new VFSection();
			// section.setBuyIsCount(false);
			// }
			// section.setBuyIsCount(cbBuyImgIsCount.isSelected());
			// }
			// });
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
		}
		return cbbBeginDate;
	}
	//
	// public VFSection getSection() {
	// return section;
	// }
	//
	// public void setSection(VFSection section) {
	// this.section = section;
	// }
}
