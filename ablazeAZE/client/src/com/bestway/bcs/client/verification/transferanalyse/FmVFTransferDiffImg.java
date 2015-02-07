package com.bestway.bcs.client.verification.transferanalyse;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import com.bestway.bcs.verification.action.VFVerificationAction;
import com.bestway.bcs.verification.action.VFVerificationAuthority;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

public class FmVFTransferDiffImg extends JInternalFrameBase {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PnVFTransferDiffPtImg pnPtImg;
	private PnVFTransferDiffHsImg pnHsImg;
	private JComboBox cbsection;
	private VFSection section;
	private VFVerificationAction vfAction = null;
	/**
	 * Create the frame.
	 */
	public FmVFTransferDiffImg() {
		VFVerificationAuthority authority = (VFVerificationAuthority)CommonVars.getApplicationContext().getBean("vfVerificationAuthority");		
		vfAction = (VFVerificationAction) CommonVars.getApplicationContext().getBean("vfVerificationAction");
		authority.checkTransferDiffImg(new Request(CommonVars.getCurrUser()));
		initialize();
	}
	private void initialize() {
		this.setTitle("料件结转差额表");
		initCompontents();
	}
	/**
	 * 初始化控件数据
	 */
	private void initCompontents(){
		getCbsection();
		section = (VFSection) getCbsection().getSelectedItem();
		getPnHsImg();
		getPnPtImg();
		changeContextPn();
	}
	/**
	 * @return the pnPtImg
	 */
	public PnVFTransferDiffPtImg getPnPtImg() {
		if(pnPtImg == null) {
			pnPtImg = new PnVFTransferDiffPtImg(this);
		}
		
		return pnPtImg;
	}
	/**
	 * @return the pnHsImg
	 */
	public PnVFTransferDiffHsImg getPnHsImg() {
		if(pnHsImg == null) {
			pnHsImg = new PnVFTransferDiffHsImg(this);
			pnHsImg.setVisible(false);
		}
		return pnHsImg;
	}
	
	public JComboBox getCbsection() {
		if (cbsection == null) {
			cbsection = new JComboBox();
			cbsection.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					changeContextPn();
				}
			});
			cbsection.setPreferredSize(new Dimension(120, 27));
			cbsection.setEditable(true);
			
			//初始化批次下拉框
			List<VFSection> sections = vfAction.findVFSectionList(new Request(CommonVars.getCurrUser()));
			for(VFSection s : sections){
				if(s.getEndDate() != null){
					s.setEndDate(java.sql.Date.valueOf(CommonUtils.getDate(s.getEndDate(),"yyyy-MM-dd")));
				}
			}
			cbsection.setModel(new DefaultComboBoxModel(sections.toArray()));
			cbsection.setRenderer(CustomBaseRender.getInstance().getRender("verificationNoView", "endDate", 150, 100));
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbsection,"verificationNoView", "endDate",250);
		}
		return cbsection;
	}
	
	public void changeContextPn() {
		System.out.println("1-----itemStateChanged----1");
		section = (VFSection) cbsection.getSelectedItem();
		if(section == null) {
			return ;
		}
		if(Boolean.TRUE.equals(section.getIsImportHs())) {
			getContentPane().remove(pnPtImg);
			pnPtImg.setVisible(false);
			getContentPane().add(pnHsImg);
			pnHsImg.setVisible(true);
			this.setTitle("料件结转差额表(编码级)");
		} else {
			getContentPane().remove(pnHsImg);
			pnHsImg.setVisible(false);
			getContentPane().add(pnPtImg);
			pnPtImg.setVisible(true);
			this.setTitle("料件结转差额表");
		}
	}
	/**
	 * @return the section
	 */
	public VFSection getSection() {
		return section;
	}
	/**
	 * @param section the section to set
	 */
	public void setSection(VFSection section) {
		this.cbsection.setSelectedItem(section);
		this.section = section;
	}
	
	/**
	 * 显示查询结果
	 * @param section
	 * @param mergerNo
	 */
	public void showData(VFSection section, Integer mergerNo) {
//		cbsection.setSelectedItem(section);
		int count = cbsection.getItemCount();
		for (int i = 0; i < count; i++) {
			Object obj = cbsection.getItemAt(i);
			if(obj instanceof VFSection){
				VFSection vfsection = (VFSection)obj;
				if(vfsection.getId().equals(section.getId())){
					cbsection.setSelectedIndex(i);
				}
			}
		}
		if(section.getIsImportHs()){
			getPnHsImg().showData(section, mergerNo);
		}else{
			getPnPtImg().showData(section, mergerNo);
		}
	}
}
