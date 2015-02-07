package com.bestway.bcs.client.verification;

import com.bestway.bcs.verification.action.VFVerificationAction;
import com.bestway.bcs.verification.entity.VFBaseStockExg;
import com.bestway.bcs.verification.entity.VFBaseStockImg;
import com.bestway.bcus.client.common.CommonQueryPage;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import org.apache.commons.lang.math.NumberUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class DgVFBaseStockExgOrImgEdit extends JDialogBase {
	private JPanel panel;
	private JLabel lblNewLabel;
	private JLabel label;
	private JLabel label_1;
	private JTextField tfPtNo;
	private JTextField tfPtName;
	private JTextField tfPtSpec;
	private JLabel label_2;
	private JTextField tfMemo;
	private JLabel label_3;
	private JTextField tfPtUnit;
	private JLabel label_4;
	private JTextField tfStoreAmount;
	private JButton btnAddMateriel;
	private JButton btnIsOk;
	private JButton btnClose;
	
	public boolean isOk = false;
	private VFBaseStockExg baseStockExg = null;
	private VFBaseStockImg baseStockImg = null;

	public Request request;
	private VFVerificationAction vfVerificationAction;
	private String materielType;
	private JLabel lblNewLabel_1;
	
	public DgVFBaseStockExgOrImgEdit() {
		request = new Request(CommonVars.getCurrUser());
		vfVerificationAction = (VFVerificationAction) CommonVars
				.getApplicationContext().getBean("vfVerificationAction");
		this.setBounds(new Rectangle(500, 260));
		getContentPane().add(getPanel(), BorderLayout.CENTER);
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(null);
			panel.add(getLblNewLabel());
			panel.add(getLabel());
			panel.add(getLabel_1());
			panel.add(getTfPtNo());
			panel.add(getTfPtName());
			panel.add(getTfPtSpec());
			panel.add(getLabel_2());
			panel.add(getTfMemo());
			panel.add(getLabel_3());
			panel.add(getTfPtUnit());
			panel.add(getLabel_4());
			panel.add(getTfStoreAmount());
			panel.add(getBtnAddMateriel());
			panel.add(getBtnIsOk());
			panel.add(getBtnClose());
			panel.add(getLblNewLabel_1());
		}
		return panel;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("料号");
			lblNewLabel.setBounds(10, 29, 54, 15);
		}
		return lblNewLabel;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("名称");
			label.setBounds(10, 65, 54, 15);
		}
		return label;
	}
	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("规格");
			label_1.setBounds(10, 102, 54, 15);
		}
		return label_1;
	}
	private JTextField getTfPtNo() {
		if (tfPtNo == null) {
			tfPtNo = new JTextField();
			tfPtNo.setEditable(false);
			tfPtNo.setBounds(61, 26, 128, 21);
			tfPtNo.setColumns(10);
		}
		return tfPtNo;
	}
	private JTextField getTfPtName() {
		if (tfPtName == null) {
			tfPtName = new JTextField();
			tfPtName.setColumns(10);
			tfPtName.setBounds(61, 62, 128, 21);
			tfPtName.setEditable(false);
		}
		return tfPtName;
	}
	private JTextField getTfPtSpec() {
		if (tfPtSpec == null) {
			tfPtSpec = new JTextField();
			tfPtSpec.setColumns(10);
			tfPtSpec.setBounds(61, 99, 128, 21);
			tfPtSpec.setEditable(false);
		}
		return tfPtSpec;
	}
	private JLabel getLabel_2() {
		if (label_2 == null) {
			label_2 = new JLabel("备注");
			label_2.setBounds(264, 98, 54, 15);
		}
		return label_2;
	}
	private JTextField getTfMemo() {
		if (tfMemo == null) {
			tfMemo = new JTextField();
			tfMemo.setColumns(10);
			tfMemo.setBounds(323, 95, 128, 21);
		}
		return tfMemo;
	}
	private JLabel getLabel_3() {
		if (label_3 == null) {
			label_3 = new JLabel("单位");
			label_3.setBounds(264, 25, 54, 15);
		}
		return label_3;
	}
	private JTextField getTfPtUnit() {
		if (tfPtUnit == null) {
			tfPtUnit = new JTextField();
			tfPtUnit.setColumns(10);
			tfPtUnit.setBounds(323, 22, 128, 21);
			tfPtUnit.setEditable(false);
		}
		return tfPtUnit;
	}
	private JLabel getLabel_4() {
		if (label_4 == null) {
			label_4 = new JLabel("库存数量");
			label_4.setBounds(264, 62, 54, 15);
		}
		return label_4;
	}
	private JTextField getTfStoreAmount() {
		if (tfStoreAmount == null) {
			tfStoreAmount = new JTextField();
			tfStoreAmount.setColumns(10);
			tfStoreAmount.setBounds(323, 59, 128, 21);
		}
		return tfStoreAmount;
	}
	private JButton getBtnAddMateriel() {
		if (btnAddMateriel == null) {
			btnAddMateriel = new JButton("选择物料");
			btnAddMateriel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					List<Materiel> dataSource = CommonQueryPage.getInstance()
							.getMaterielAllOfCustomsRalation(materielType,false);
					if (dataSource == null || dataSource.size() <= 0) {
						return;
					}
					Materiel materiel = dataSource.get(0);
					tfPtNo.setText(materiel.getPtNo());
					tfPtName.setText(materiel.getFactoryName());
					tfPtSpec.setText(materiel.getFactorySpec());
					if(materiel.getCalUnit()!=null){
						tfPtUnit.setText(materiel.getCalUnit().getName());
					}
					if(MaterielType.FINISHED_PRODUCT.endsWith(materielType)){
						baseStockExg.setPtNo(materiel.getPtNo());
						baseStockExg.setPtName(materiel.getFactoryName());
						baseStockExg.setPtSpec(materiel.getFactorySpec());
						if(materiel.getCalUnit()!=null){
							baseStockExg.setPtUnit(materiel.getCalUnit().getName());
						}
					}else{
						baseStockImg.setPtNo(materiel.getPtNo());
						baseStockImg.setPtName(materiel.getFactoryName());
						baseStockImg.setPtSpec(materiel.getFactorySpec());
						if(materiel.getCalUnit()!=null){
							baseStockImg.setPtUnit(materiel.getCalUnit().getName());
						}
					}
				}
			});
			btnAddMateriel.setBounds(190, 25, 30, 23);
		}
		return btnAddMateriel;
	}
	private JButton getBtnIsOk() {
		if (btnIsOk == null) {
			btnIsOk = new JButton("保存");
			btnIsOk.setBounds(112, 189, 93, 23);
			btnIsOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					isOk = true;
					if(MaterielType.FINISHED_PRODUCT.endsWith(materielType)){
						baseStockExg.setStoreAmount(NumberUtils.toDouble(tfStoreAmount.getText()));
						baseStockExg.setMemo(tfMemo.getText());
						vfVerificationAction.saveVFBaseStockExg(request, baseStockExg);
					}else{
						baseStockImg.setStoreAmount(NumberUtils.toDouble(tfStoreAmount.getText()));
						baseStockImg.setMemo(tfMemo.getText());
						vfVerificationAction.saveVFBaseStockImg(request, baseStockImg);
					}
					dispose();
				}
			});
		}
		return btnIsOk;
	}
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton("取消");
			btnClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					isOk = false;
					dispose();
				}
			});
			btnClose.setBounds(239, 189, 93, 23);
		}
		return btnClose;
	}
	
	@Override
	public void setVisible(boolean b) {
		if(b){
			if(MaterielType.FINISHED_PRODUCT.endsWith(materielType)){
				this.setTitle("修改成品");
			}else{
				this.setTitle("修改料件");
			}
			fillData();
		}
		super.setVisible(b);
	}
	
	private void fillData(){
		if(MaterielType.FINISHED_PRODUCT.endsWith(materielType)){
			tfPtNo.setText(this.baseStockExg.getPtNo());
			tfPtName.setText(this.baseStockExg.getPtName());
			tfPtSpec.setText(this.baseStockExg.getPtSpec());
			tfPtUnit.setText(this.baseStockExg.getPtUnit());
			tfStoreAmount.setText(this.baseStockExg.getStoreAmount()+"");
			tfMemo.setText(this.baseStockExg.getMemo());
		}else{
			tfPtNo.setText(this.baseStockImg.getPtNo());
			tfPtName.setText(this.baseStockImg.getPtName());
			tfPtSpec.setText(this.baseStockImg.getPtSpec());
			tfPtUnit.setText(this.baseStockImg.getPtUnit());
			tfStoreAmount.setText(this.baseStockImg.getStoreAmount()+"");
			tfMemo.setText(this.baseStockImg.getMemo());
		}
	}

	public VFBaseStockExg getBaseStockExg() {
		return baseStockExg;
	}

	public void setBaseStockExg(VFBaseStockExg baseStockExg) {
		this.baseStockExg = baseStockExg;
	}

	public String getMaterielType() {
		return materielType;
	}

	public void setMaterielType(String materielType) {
		this.materielType = materielType;
	}
	
	public VFBaseStockImg getBaseStockImg() {
		return baseStockImg;
	}

	public void setBaseStockImg(VFBaseStockImg baseStockImg) {
		this.baseStockImg = baseStockImg;
	}
	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("注：库存数量修改后,报关数量需要从新折算才会更改！");
			lblNewLabel_1.setBounds(10, 144, 464, 15);
			lblNewLabel_1.setForeground(Color.BLUE);
		}
		return lblNewLabel_1;
	}
}
