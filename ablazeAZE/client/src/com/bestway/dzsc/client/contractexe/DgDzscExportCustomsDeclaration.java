/*
 * Created on 2004-8-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.contractexe;

import java.util.Date;

import javax.swing.JOptionPane;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.client.custom.common.DgBaseExportCustomsDeclaration;
import com.bestway.client.custom.common.DgBaseExportCustomsDeclarationCommInfo;
import com.bestway.common.constant.ProjectType;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.dzsc.contractexe.action.DzscContractExeAction;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclaration;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDzscExportCustomsDeclaration extends
		DgBaseExportCustomsDeclaration {

	public DgDzscExportCustomsDeclaration() {
		super();
		lbEmsNo.setText("备案号");
		baseEncAction = (DzscContractExeAction) CommonVars
				.getApplicationContext().getBean("dzscContractExeAction");
		// DzscContractExeAction dzscContractExeAction = (DzscContractExeAction)
		// CommonVars
		// .getApplicationContext().getBean("dzsccontractExeAction");
		projectType = ProjectType.DZSC;
	}

	@Override
	protected void setState() {
		super.setState();
		this.getJButton1().setVisible(false);
		this.getJButton2().setVisible(false);
		this.getJButton4().setVisible(false);
	}

	// @Override
	// protected void initUIComponents() {
	// super.initUIComponents();
	// this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
	// ImpExpType.REMAIN_FORWARD_EXPORT).toString(), "余料结转"));
	// }

	/**
	 * 当新增或删除时，显示最初数据
	 */
	protected void showPrimalData() {
		super.showPrimalData();
		if (dataState == DataState.ADD) {
			customsDeclaration.setContract(((DzscEmsPorHead) emsHead)
					.getImContractNo());
			this.tfContract.setText(customsDeclaration.getContract());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bestway.client.custom.common.DgBaseImportCustomsDeclaration#newCustomsDeclaration()
	 */
	protected BaseCustomsDeclaration newCustomsDeclaration() {
		return new DzscCustomsDeclaration();
	}

	protected void addFromMateriel() {
		// DgDzscCustomsFromMaterielDialog dg = new
		// DgDzscCustomsFromMaterielDialog();
		// dg.setType(MaterielType.FINISHED_PRODUCT);
		// dg.setEmsNo(customsDeclaration.getEmsHeadH2k());
		// dg.setCustomsDeclaration(customsDeclaration);
		// dg.setVisible(true);
	}

	@Override
	protected void lookFromMateriel() {

	}

	@Override
	protected DgBaseExportCustomsDeclarationCommInfo getExportCustomsDeclarationCommInfo() {
		int impExpType = Integer.parseInt(((ItemProperty) this.cbbImpExpType
				.getSelectedItem()).getCode());
		String tradeCode = "";
		if (this.cbbTradeMode.getSelectedItem() != null) {
			tradeCode = ((Trade) cbbTradeMode.getSelectedItem()).getCode();
		}
		DgDzscExportCustomsDeclarationCommInfo dg = new DgDzscExportCustomsDeclarationCommInfo();
		dg.setContract((DzscEmsPorHead) emsHead);
		dg.setImpExpType(impExpType);
		dg.setTradeCode(tradeCode);
		return dg;
	}

	@Override
	protected boolean saveCustom() {
		Date declareDate = this.cbbDeclarationDate.getDate();
		Date beginDate = ((DzscEmsPorHead) emsHead).getBeginDate();
		Date endDate = ((DzscEmsPorHead) emsHead).getEndDate();
//		Date deferDate = ((DzscEmsPorHead) emsHead).getDeferDate();
		if (declareDate != null) {
			if (declareDate.compareTo(beginDate) < 0) {
				JOptionPane.showMessageDialog(
						DgDzscExportCustomsDeclaration.this,
						"申报日期不能小于手册开始生效日期", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
//		if (declareDate != null && deferDate != null) {
//			if (declareDate.compareTo(deferDate) > 0) {
//				JOptionPane.showMessageDialog(
//						DgDzscExportCustomsDeclaration.this, "申报日期不能大于手册延期期限",
//						"提示", JOptionPane.INFORMATION_MESSAGE);
//				return false;
//			}
//		}
		if (declareDate != null && endDate != null) {
			if (declareDate.compareTo(endDate) > 0) {
				JOptionPane.showMessageDialog(
						DgDzscExportCustomsDeclaration.this, "申报日期不能大于手册有效日期",
						"提示", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		return super.saveCustom();
	}
} // @jve:decl-index=0:visual-constraint="10,10"

