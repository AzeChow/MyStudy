package com.bestway.bcs.client.bcsinnermerge;

import com.bestway.bcs.bcsinnermerge.action.BcsInnerMergeAction;
import com.bestway.bcs.dictpor.action.BcsDictPorAction;
import com.bestway.bcs.dictpor.entity.BcsDictPorHead;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.jptds.client.common.CustomBaseComboBoxEditor;
import com.bestway.jptds.client.common.CustomBaseRender;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DgBcsTenInnerImport extends JDialogBase{
	private JPanel panel;
	private JComboBox comboBox;
	private JButton btnDetermine;
	private JButton btnCancel;
	private JLabel lblNewLabel;
	
	private BcsDictPorHead bcsDictPorHead = null;
	
	private boolean isOk = false;
	
	/**
	 * 内部归并接口
	 */
	private BcsInnerMergeAction bcsInnerMergeAction = null;
	
	private BcsDictPorAction bcsDictPorAction = null;
	
	public DgBcsTenInnerImport() {
		this.setBounds(new Rectangle(400,200));
		bcsInnerMergeAction = (BcsInnerMergeAction) CommonVars
				.getApplicationContext().getBean("bcsInnerMergeAction");
		bcsDictPorAction = (BcsDictPorAction) CommonVars
				.getApplicationContext().getBean("bcsDictPorAction");
		getContentPane().add(getPanel(), BorderLayout.CENTER);
		initComboBox();
	}

	
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(null);
			panel.add(getComboBox());
			panel.add(getBtnDetermine());
			panel.add(getBtnCancel());
			panel.add(getLblNewLabel());
		}
		return panel;
	}
	private JComboBox getComboBox() {
		if (comboBox == null) {
			comboBox = new JComboBox();
			comboBox.setBounds(181, 36, 111, 21);
		}
		return comboBox;
	}
	private JButton getBtnDetermine() {
		if (btnDetermine == null) {
			btnDetermine = new JButton("确定");
			btnDetermine.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					BcsDictPorHead bcsDictPorHead = (BcsDictPorHead)comboBox.getSelectedItem();
					setBcsDictPorHead(bcsDictPorHead);
					setIsOk(true);
					dispose();
				}
			});
			btnDetermine.setBounds(63, 93, 93, 23);
		}
		return btnDetermine;
	}
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("取消");
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setBcsDictPorHead(null);
					setIsOk(false);
					dispose();
				}
			});
			btnCancel.setBounds(199, 93, 93, 23);
		}
		return btnCancel;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("请选择备案资料库：");
			lblNewLabel.setBounds(63, 39, 108, 15);
		}
		return lblNewLabel;
	}
	
	private void initComboBox(){
		List list = bcsDictPorAction.findExingBcsDictPorHead(new Request(CommonVars
				.getCurrUser()));
		DefaultComboBoxModel DfComboBox = new DefaultComboBoxModel(list.toArray());
		this.comboBox.setModel(DfComboBox);
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(comboBox,"seqNum","dictPorEmsNo",150);
		this.comboBox.setRenderer(CustomBaseRender.getInstance().getRender(
				"seqNum","dictPorEmsNo", 40, 100));
		this.comboBox.setSelectedItem(null);
	}


	public BcsDictPorHead getBcsDictPorHead() {
		return bcsDictPorHead;
	}


	public void setBcsDictPorHead(BcsDictPorHead bcsDictPorHead) {
		this.bcsDictPorHead = bcsDictPorHead;
	}


	public boolean getIsOk() {
		return isOk;
	}


	public void setIsOk(boolean isOk) {
		this.isOk = isOk;
	}
	
	
}
