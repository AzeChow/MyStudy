package com.bestway.bcs.client.message;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.bestway.bcs.contractanalyse.action.ContractAnalyseAction;
import com.bestway.bcs.message.action.BcsMessageAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.common.Request;
import com.bestway.common.client.message.FmCspMessageQuery;
import com.bestway.common.constant.BcsBusinessType;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.message.action.CspMessageAction;
import com.bestway.common.message.entity.CspMessageSend;

public class FmBcsMessageQuery extends FmCspMessageQuery {
	protected BcsMessageAction bcsMessageAction = null;
	
	@Override
	protected CspMessageAction getCspMessageAction() {
		bcsMessageAction = (BcsMessageAction) CommonVars.getApplicationContext()
		.getBean("bcsMessageAction");
		bcsMessageAction.checkMassageQueryAuthority(new Request(
				CommonVars.getCurrUser()));
		return (CspMessageAction) CommonVars.getApplicationContext().getBean(
				"bcsMessageAction");
	}

	@Override
	protected void makeDefaultReturnInfoMessage(String sysType,
			CspMessageSend messageSend) {
		String emsNo = null;
		String fileName = messageSend.getFileName();
		String delcareType = ((BcsMessageAction) cspMessageAction)
				.getCspMessageDeclareType(
						new Request(CommonVars.getCurrUser()), fileName);
		if ((BcsBusinessType.EMS_POR_WJ.equals(sysType) || BcsBusinessType.EMS_POR_BILL
				.equals(sysType))
				&& DelcareType.APPLICATION.equals(delcareType)) {
			DgBcsMessageInputEmsNo dg = new DgBcsMessageInputEmsNo();
			if (BcsBusinessType.EMS_POR_WJ.equals(sysType)) {
				dg.setEmsNoType("备案资料库编号");
			} else {
				dg.setEmsNoType("通关手册号");
			}
			dg.setVisible(true);
			if (dg.isOK()) {
				emsNo = dg.getEmsNo();
			}
		}
		try {
			Map<String,Object> mapData=new HashMap<String,Object>();
			mapData.put("emsNo", emsNo);
			cspMessageAction.makeDefaultReturnInfoMessage(new Request(
					CommonVars.getCurrUser()), fileName, mapData);
			JOptionPane.showMessageDialog(FmBcsMessageQuery.this, "生成模拟回执成功",
					"提示", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(FmBcsMessageQuery.this, "生成模拟回执失败 "
					+ ex.getMessage(), "提示", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	protected Vector getBusinessType() {
		Vector<ItemProperty> vector =new Vector<ItemProperty>();
		vector.add(new ItemProperty(String.valueOf(BcsBusinessType.EMS_POR_WJ),
				BcsBusinessType
						.getBcsBusinessTypeDesc(BcsBusinessType.EMS_POR_WJ)));
		vector.add(new ItemProperty(String
				.valueOf(BcsBusinessType.EMS_POR_BILL), BcsBusinessType
				.getBcsBusinessTypeDesc(BcsBusinessType.EMS_POR_BILL)));
		vector
				.add(new ItemProperty(
						String.valueOf(BcsBusinessType.CANCEL_AFTER_VERIFICA),
						BcsBusinessType
								.getBcsBusinessTypeDesc(BcsBusinessType.CANCEL_AFTER_VERIFICA)));
		return vector;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
