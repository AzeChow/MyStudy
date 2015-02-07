/*
 * Created on 2004-8-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.enc;

import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.enc.entity.CustomsFromMateriel;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrHead;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.client.custom.common.DgBaseExportCustomsDeclaration;
import com.bestway.client.custom.common.DgBaseExportCustomsDeclarationCommInfo;
import com.bestway.client.custom.common.DgCustomsFromMaterielDialog;
import com.bestway.client.custom.common.DgFromMaterielEdit;
import com.bestway.client.custom.common.DgLookAppFromMateriel;
import com.bestway.client.custom.common.report.CustomsPrinter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.AddType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgExportCustomsDeclaration extends DgBaseExportCustomsDeclaration {
	private ManualDeclareAction manualDecleareAction = (ManualDeclareAction) CommonVars
			.getApplicationContext().getBean("manualdeclearAction");

	public DgExportCustomsDeclaration() {
		super();
		baseEncAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		
		projectType = ProjectType.BCUS;
	}

	private JMenuItem jMenuItem18 = null;

	/**
	 * This method initializes jMenuItem18
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem18() {
		if (jMenuItem18 == null) {
			jMenuItem18 = new JMenuItem();
			jMenuItem18.setText("出口货物发票");
			jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsPrinter.getInstance(getParaList()).doExportInvoice();
				}
			});
		}
		return jMenuItem18;
	}

	protected JPopupMenu getJPopupMenu() {
		JPopupMenu tmp = super.getJPopupMenu();
		tmp.add(getJMenuItem18());
		return tmp;
	}

	/**
	 * 当新增或删除时，显示最初数据
	 */
	protected void showData() {
		super.showData();
		this.tfCompanyEmsNo.setText(((EmsHeadH2k) emsHead).getCopEmsNo());
	}

	protected void showPrimalData() {
		super.showPrimalData();
		if (dataState == DataState.ADD) {
			Curr curr = null;
			EmsEdiTrHead head = manualDecleareAction
					.findEmsEdiTrHeadIng(new Request(CommonVars.getCurrUser()));
			if (head != null) {
				curr = head.getCurr();
			}
			if (customsDeclaration.getCurrency() == null) {
				this.customsDeclaration.setCurrency(curr);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.client.custom.common.DgBaseExportCustomsDeclaration#
	 * newCustomsDeclaration()
	 */
	protected BaseCustomsDeclaration newCustomsDeclaration() {
		return new CustomsDeclaration();
	}

	protected void addFromMateriel() {// 新增内部商品
		DgCustomsFromMaterielDialog dg = new DgCustomsFromMaterielDialog();
		dg.setProjectType(ProjectType.BCUS); // Bcus
		dg.setBaseEncAction(baseEncAction);
		dg.setMaterialType(MaterielType.FINISHED_PRODUCT); // 成品
		dg.setCustomsDeclaration(customsDeclaration);
		dg.setTitle("新增报关单来自归并关系");
		dg.setVisible(true);
	}

	protected void lookFromMateriel() {
		if (commInfoModel.getCurrentRow() == null) {
			return;
		}
		BaseCustomsDeclarationCommInfo info = (BaseCustomsDeclarationCommInfo) commInfoModel
				.getCurrentRow();
		if (info.getAddType() != null
				&& info.getAddType().equals(AddType.APPLY)) {// 申请单转报关单
			List list = baseEncAction
					.findAppFromMaterielByCustomsInfoAndNetGossWeight(
							new Request(CommonVars.getCurrUser()), info);
			Materiel m = baseEncAction.getNetGossWeight(new Request(CommonVars
					.getCurrUser()), list);// 计算总仓库净毛重

			DgLookAppFromMateriel dg = new DgLookAppFromMateriel();
			CustomsFromMateriel obj = null;
			List list1 = baseEncAction.getMaterielByCustoms(new Request(
					CommonVars.getCurrUser()), info);
			if (list1 != null && list1.size() > 0) {
				obj = (CustomsFromMateriel) list1.get(0);
			}
			if (obj == null) {
				return;
			}
			EmsHeadH2kImg img = ((EncAction) baseEncAction).getEmsHeadImg(
					new Request(CommonVars.getCurrUser()),
					obj.getBcusimgNo() == null ? 0 : obj.getBcusimgNo());
			EmsHeadH2kExg exg = ((EncAction) baseEncAction).getEmsHeadExg(
					new Request(CommonVars.getCurrUser()),
					obj.getBcusexgNo() == null ? 0 : obj.getBcusexgNo());
			dg.setBcusexgbill(exg);
			dg.setBcusimgbill(img);
			dg.setFromMateriel(obj);
			dg.setM(m);
			dg.setInfo(info);
			dg.setList(list);
			dg.setVisible(true);
		} else if (info.getAddType() != null
				&& info.getAddType().equals(AddType.INNER)) {// 内部商品
			List list = baseEncAction.getMaterielByCustoms(new Request(
					CommonVars.getCurrUser()), info);
			if (list == null) {
				return;
			}
			DgFromMaterielEdit dg = new DgFromMaterielEdit();
			dg.setProjectType(ProjectType.BCUS);
			dg.setCustomsDeclaration(customsDeclaration);
			dg.setInfo(info);
			dg.setList(list);
			dg.setBrowse(true);
			dg.setVisible(true);
		} else {// 其他
			DgBaseExportCustomsDeclarationCommInfo dg = new DgBaseExportCustomsDeclarationCommInfo();
			dg.setTableModel(commInfoModel);
			dg.setEffective(customsDeclaration.getEffective() == null ? false
					: customsDeclaration.getEffective().booleanValue());
			dg.setBaseEncAction(this.baseEncAction);
			dg.setDataState(DataState.BROWSE);
			dg.setBcusChaKan(false);
			dg.setVisible(true);
		}

	}

	@Override
	protected DgBaseExportCustomsDeclarationCommInfo getExportCustomsDeclarationCommInfo() {
		int impExpType = Integer.parseInt(((ItemProperty) this.cbbImpExpType
				.getSelectedItem()).getCode());
		String tradeCode = "";
		if (this.cbbTradeMode.getSelectedItem() != null) {
			tradeCode = ((Trade) cbbTradeMode.getSelectedItem()).getCode();
		}
		DgBcusExporCustomsDeclarationCommInfo dg = new DgBcusExporCustomsDeclarationCommInfo();
		dg.setEmsHeadH2k((EmsHeadH2k) emsHead);
		dg.setImpExpType(impExpType);
		dg.setTradeCode(tradeCode);
		return dg;
	}

} // @jve:decl-index=0:visual-constraint="10,10"

