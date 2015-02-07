package com.bestway.client.owp;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.common.Request;
import com.bestway.common.client.message.FmCspMessageQuery;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.OwpBusinessType;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.message.action.CspMessageAction;
import com.bestway.common.message.entity.CspMessageSend;
import com.bestway.owp.action.OwpMessageAction;

public class FmOwpMessageQuery extends FmCspMessageQuery {

	@Override
	protected String getFormatFileNameByType(String sysType,
			String messageFileName) {
		String formatFileName = ((OwpMessageAction) cspMessageAction)
				.getFormatFileNameByType(new Request(CommonVars.getCurrUser()),
						sysType);
		System.out.println("formatFileName:" + formatFileName);
		return formatFileName;
	}

	@Override
	protected CspMessageAction getCspMessageAction() {
		return (CspMessageAction) CommonVars.getApplicationContext().getBean(
				"owpMessageAction");
	}

	@Override
	protected void makeDefaultReturnInfoMessage(String sysType,
			CspMessageSend messageSend) {
		String fileName = messageSend.getFileName();
//		String inOutFlag = ((OwpMessageAction) cspMessageAction).getInOutFlag(
//				new Request(CommonVars.getCurrUser()), sysType, fileName);
		String seqNo = null;
		String customsNo = null;
		System.out.println("--------sysType:"+sysType);
		if ((OwpBusinessType.OWP_APP.equals(sysType))) {
			String delcareType = ((OwpMessageAction) cspMessageAction)
					.getCspMessageDeclareType(new Request(CommonVars
							.getCurrUser()), fileName);
			System.out.println("--------delcareType:"+delcareType);
			if (DelcareType.APPLICATION.equals(delcareType)) {
//				if (FptInOutFlag.OUT.equals(inOutFlag)) {
					DgOwpMessageInputNo dg = new DgOwpMessageInputNo();
					dg.setEmsNoType("电子口岸统一编号");
					dg.setVisible(true);
					if (dg.isOK()) {
						seqNo = dg.getEmsNo();
					} else {
						return;
					}
//				}
				DgOwpMessageInputNo dgEmsNo = new DgOwpMessageInputNo();
				dgEmsNo.setEmsNoType("申请表编号");
				dgEmsNo.setVisible(true);
				if (dgEmsNo.isOK()) {
					customsNo = dgEmsNo.getEmsNo();
				} else {
					return;
				}
			}
		} else if ((OwpBusinessType.OWP_BILL_SEND.equals(sysType) || OwpBusinessType.OWP_BILL_RECV
				.equals(sysType))) {
			DgOwpMessageInputNo dg = new DgOwpMessageInputNo();
			dg.setEmsNoType("电子口岸统一编号");
			dg.setVisible(true);
			if (dg.isOK()) {
				seqNo = dg.getEmsNo();
			} else {
				return;
			}
			if (OwpBusinessType.OWP_BILL_SEND.equals(sysType)) {
				dg = new DgOwpMessageInputNo();
				dg.setEmsNoType("发货单编号");
				dg.setVisible(true);
				if (dg.isOK()) {
					customsNo = dg.getEmsNo();
				} else {
					return;
				}
			} else if (OwpBusinessType.OWP_BILL_RECV.equals(sysType)) {
				dg = new DgOwpMessageInputNo();
				dg.setEmsNoType("收货单编号");
				dg.setVisible(true);
				if (dg.isOK()) {
					customsNo = dg.getEmsNo();
				} else {
					return;
				}
			}
		}
		try {
			Map<String, Object> mapData = new HashMap<String, Object>();
			if ((OwpBusinessType.OWP_APP.equals(sysType))) {
				mapData.put("seqNo", seqNo);
				mapData.put("customsNo", customsNo);
				cspMessageAction.makeDefaultReturnInfoMessage(new Request(
						CommonVars.getCurrUser()), fileName, mapData);
			} else if ((OwpBusinessType.OWP_BILL_SEND.equals(sysType) || OwpBusinessType.OWP_BILL_RECV
					.equals(sysType))) {
				mapData.put("seqNo", seqNo);
				mapData.put("customsNo", customsNo);
				cspMessageAction.makeDefaultReturnInfoMessage(new Request(
						CommonVars.getCurrUser()), fileName, mapData);
			} else {
				cspMessageAction.makeDefaultReturnInfoMessage(new Request(
						CommonVars.getCurrUser()), fileName, mapData);
			}
			JOptionPane.showMessageDialog(FmOwpMessageQuery.this, "生成模拟回执成功",
					"提示", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(FmOwpMessageQuery.this, "生成模拟回执失败 "
					+ ex.getMessage(), "提示", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	protected Vector getBusinessType() {
		Vector<ItemProperty> vector = new Vector<ItemProperty>();
		vector
				.add(new ItemProperty(
						String.valueOf(OwpBusinessType.OWP_APP),
						OwpBusinessType
								.getOwpBusinessTypeDesc(OwpBusinessType.OWP_APP)));
		vector.add(new ItemProperty(String.valueOf(OwpBusinessType.OWP_BILL_SEND),
				OwpBusinessType
						.getOwpBusinessTypeDesc(OwpBusinessType.OWP_BILL_SEND)));
		vector.add(new ItemProperty(String
				.valueOf(OwpBusinessType.OWP_BILL_RECV), OwpBusinessType
				.getOwpBusinessTypeDesc(OwpBusinessType.OWP_BILL_RECV)));
		vector.add(new ItemProperty(String
				.valueOf(OwpBusinessType.CANCEL_BILL), OwpBusinessType
				.getOwpBusinessTypeDesc(OwpBusinessType.CANCEL_BILL)));
		return vector;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
