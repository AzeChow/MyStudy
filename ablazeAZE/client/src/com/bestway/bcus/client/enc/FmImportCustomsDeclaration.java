/*
 * Created on 2004-8-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.enc;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.manualdeclare.DgEmsEdiMerger;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.message.action.MessageAction;
import com.bestway.client.custom.common.FmBaseImportCustomsDeclaration;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author Administrator
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmImportCustomsDeclaration extends FmBaseImportCustomsDeclaration {

	protected ManualDeclareAction manualDeclareAction = null;

	public FmImportCustomsDeclaration() {
		super();
		baseEncAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		manualDeclareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		projectType = ProjectType.BCUS;
	}

	/**
	 * 
	 */
	protected void showCustomsDeclarationData(int dataState) {
		EmsHeadH2k ems = (EmsHeadH2k) this.cbbEmshead.getSelectedItem();
		if (ems == null) {
			JOptionPane.showMessageDialog(null, "没有正在执行的电子账册!!", "提示", 1);
			return;
		}
		if (ems.getEmsNo() == null || ems.getEmsNo().trim().equals("")) {
			JOptionPane.showMessageDialog(FmImportCustomsDeclaration.this,
					"电子帐册的编号不能为空", "提示", 0);
			return;
		}
		DgImportCustomsDeclaration dg = new DgImportCustomsDeclaration();
		BaseCustomsDeclaration customs = (BaseCustomsDeclaration) tableModel
				.getCurrentRow();
		customs = baseEncAction.findCustomsDeclaration(new Request(CommonVars
				.getCurrUser(), true), customs.getId());
		dg.setCustomsDeclarationModel(tableModel);
		dg.setDataState(dataState);
		dg.setEmsHead(ems);
		dg.setVisible(true);
		int nowRow = jTable.getSelectedRow();
		if(nowRow<0){
			return;
		}
		jTable.requestFocus(true);
		jTable.setColumnSelectionInterval(0, jTable.getColumnCount() - 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bestway.client.custom.common.FmBaseImportCustomsDeclaration#addCustomsDeclarationData()
	 */
	protected void addCustomsDeclarationData() {
		EmsHeadH2k ems = (EmsHeadH2k) this.cbbEmshead.getSelectedItem();
		if (ems == null) {
			JOptionPane.showMessageDialog(null, "没有正在执行的电子账册!!", "提示", 1);
			return;
		}
		if (ems.getEmsNo() == null || ems.getEmsNo().trim().equals("")) {
			JOptionPane.showMessageDialog(FmImportCustomsDeclaration.this,
					"电子帐册的编号不能为空", "提示", 0);
			return;
		}
		DgImportCustomsDeclaration dg = new DgImportCustomsDeclaration();
		dg.setCustomsDeclarationModel(tableModel);
		dg.setDataState(DataState.ADD);
		dg.setEmsHead(ems);
		dg.setVisible(true);

	}

	// protected List getDataSource() {
	// EmsHeadH2k emsHead=(EmsHeadH2k)this.cbbEmshead.getSelectedItem();
	// if(emsHead==null){
	// return new ArrayList();
	// }
	// return this.baseEncAction.findCustomsDeclaration(new Request(
	// CommonVars.getCurrUser()),ImpExpFlag.IMPORT,emsHead.getEmsNo(),null);
	// }
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bestway.client.custom.common.FmBaseImportCustomsDeclaration#initUIComponents()
	 */
	protected void initUIComponents() {
		// 电子帐册
		cbbEmshead.removeAllItems();
		List contracts = manualDeclareAction
				.findEmsHeadH2kInExecuting(new Request(
						CommonVars.getCurrUser(), true));
		if (contracts != null && contracts.size() > 0) {
			for (int i = 0; i < contracts.size(); i++) {
				this.cbbEmshead.addItem((EmsHeadH2k) contracts.get(i));
			}
			this.cbbEmshead.setRenderer(CustomBaseRender.getInstance()
					.getRender("preEmsNo", "emsNo", 0, 150));
		}
		if (this.cbbEmshead.getItemCount() > 0) {
			this.cbbEmshead.setSelectedIndex(0);
		}

		lbEmsHead.setText("正在执行的帐册号:");
	}
} // @jve:decl-index=0:visual-constraint="10,10"
