package com.bestway.dzsc.client.message;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.bestway.bcs.message.action.BcsMessageAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.common.Request;
import com.bestway.common.client.message.FmCspMessageQuery;
import com.bestway.common.constant.DzscBusinessType;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.message.action.CspMessageAction;
import com.bestway.common.message.entity.CspMessageSend;
import com.bestway.dzsc.message.action.DzscMessageAction;

public class FmDzscMessageQuery extends FmCspMessageQuery {
	protected DzscMessageAction dzscMessageAction = null;
	@Override
	protected CspMessageAction getCspMessageAction() {
		dzscMessageAction = (DzscMessageAction) CommonVars.getApplicationContext()
		.getBean("dzscMessageAction");
		dzscMessageAction.checkDzscMessageQueryAuthority(new Request(
				CommonVars.getCurrUser()));
		// TODO Auto-generated method stub
		return (DzscMessageAction) CommonVars.getApplicationContext().getBean(
				"dzscMessageAction");
	}

	@Override
	protected void makeDefaultReturnInfoMessage(String sysType,
			CspMessageSend messageSend) {
		String fileName = messageSend.getFileName();
		String delcareType = cspMessageAction.getCspMessageDeclareType(
				new Request(CommonVars.getCurrUser()), fileName);
		System.out.println(sysType + "---" + delcareType);
		String emsNo = "";
		String corrEmsNo = "";
		if (DzscBusinessType.EMS_POR_WJ.equals(sysType)
				&& DelcareType.APPLICATION.equals(delcareType)) {
			DgDzscMessageInputEmsNo dg = new DgDzscMessageInputEmsNo();
			dg.setVisible(true);
			if (dg.isOK()) {
				emsNo = dg.getEmsNo();
				corrEmsNo = dg.getCorrEmsNo();
			}else{
				return;
			}
		}
		try {
			Map<String, Object> mapData = new HashMap<String, Object>();
			mapData.put("emsNo", emsNo);
			mapData.put("corrEmsNo", corrEmsNo);
			cspMessageAction.makeDefaultReturnInfoMessage(new Request(
					CommonVars.getCurrUser()), fileName, mapData);
			JOptionPane.showMessageDialog(FmDzscMessageQuery.this, "生成模拟回执成功",
					"提示", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(FmDzscMessageQuery.this, "生成模拟回执失败 "
					+ ex.getMessage(), "提示", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	protected Vector getBusinessType() {
		Vector<ItemProperty> vector = new Vector<ItemProperty>();
		vector.add(new ItemProperty(String.valueOf(DzscBusinessType.MATERIAL),
				DzscBusinessType.getBusinessTypeDesc(DzscBusinessType.MATERIAL)));
		vector.add(new ItemProperty(String.valueOf(DzscBusinessType.INNER_MERGE),
				DzscBusinessType.getBusinessTypeDesc(DzscBusinessType.INNER_MERGE)));
		vector.add(new ItemProperty(String.valueOf(DzscBusinessType.MATERIAL_BOM),
				DzscBusinessType.getBusinessTypeDesc(DzscBusinessType.MATERIAL_BOM)));
		vector.add(new ItemProperty(String.valueOf(DzscBusinessType.EMS_POR_WJ),
				DzscBusinessType.getBusinessTypeDesc(DzscBusinessType.EMS_POR_WJ)));
		vector.add(new ItemProperty(String.valueOf(DzscBusinessType.EMS_POR_BILL),
				DzscBusinessType.getBusinessTypeDesc(DzscBusinessType.EMS_POR_BILL)));
		vector.add(new ItemProperty(String.valueOf(DzscBusinessType.FASCICULE),
				DzscBusinessType.getBusinessTypeDesc(DzscBusinessType.FASCICULE)));
		vector.add(new ItemProperty(String
				.valueOf(DzscBusinessType.CUSTOMS_BILL_LIST), DzscBusinessType
				.getBusinessTypeDesc(DzscBusinessType.CUSTOMS_BILL_LIST)));
		vector.add(new ItemProperty(String.valueOf(DzscBusinessType.CHECK),
				DzscBusinessType.getBusinessTypeDesc(DzscBusinessType.CHECK)));
		vector.add(new ItemProperty(String
				.valueOf(DzscBusinessType.CANCEL_AFTER_VERIFICA), DzscBusinessType
				.getBusinessTypeDesc(DzscBusinessType.CANCEL_AFTER_VERIFICA)));
		return vector;
	}

}
