package com.bestway.common.client.fpt;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.common.Request;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JPanelBase;

public class PnCommon extends JPanelBase {
	public FptManageAction fptManageAction = null;

	public CommonBaseCodeAction commonBaseCodeAction = null;

	public MaterialManageAction materialManageAction = null;

	public ManualDeclareAction manualDeclearAction = null;

	public SystemAction systemAction = null;

	public EncAction encAction = null;

	public PnCommon() {
		super();
		// this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		try {
			this.fptManageAction = (FptManageAction) CommonVars
					.getApplicationContext().getBean(
							"fptManageAction");
			this.commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
					.getApplicationContext().getBean("commonBaseCodeAction");
			this.manualDeclearAction = (ManualDeclareAction) CommonVars
					.getApplicationContext().getBean("manualdeclearAction");
			this.systemAction = (SystemAction) CommonVars
					.getApplicationContext().getBean("systemAction");
			this.encAction = (EncAction) CommonVars.getApplicationContext()
					.getBean("encAction");
			materialManageAction = (MaterialManageAction) CommonVars
					.getApplicationContext().getBean("materialManageAction");
		} catch (Exception ex) {

		}
	}

	@Override
	public void setVisible(boolean isFlag) {
		super.setVisible(isFlag);
	}

	/**
	 * 获得是客户的对象列表
	 */
	public List getCustomer() {
		List list = this.materialManageAction.findScmCocs(new Request(
				CommonVars.getCurrUser()));
		List customerList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			ScmCoc scmCoc = (ScmCoc) list.get(i);
			if (scmCoc.getIsCustomer().booleanValue()
					&& scmCoc.getIsTransferFactoryOut() != null
					&& scmCoc.getIsTransferFactoryOut()
					&& scmCoc.getBrief() != null) {
				customerList.add(scmCoc);
			}
		}
		return customerList;
	}

	/**
	 * 获得是供应商的对象列表
	 */
	public List getManufacturer() {
		List list = this.materialManageAction.findScmCocs(new Request(
				CommonVars.getCurrUser()));
		List manufacturerList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			ScmCoc scmCoc = (ScmCoc) list.get(i);
			if (scmCoc.getIsManufacturer().booleanValue()
					&& scmCoc.getIsTransferFactoryIn() != null
					&& scmCoc.getIsTransferFactoryIn()
					&& scmCoc.getBrief() != null) {
				manufacturerList.add(scmCoc);
			}
		}
		return manufacturerList;
	}

	/**
	 * 转换Set到List
	 */
	public List convertSetToList(Set set) {
		ArrayList list = new ArrayList();
		if (set != null) {
			list.addAll(set);
		}
		return list;
	}
}