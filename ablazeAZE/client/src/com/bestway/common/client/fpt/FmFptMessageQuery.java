package com.bestway.common.client.fpt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.common.Request;
import com.bestway.common.client.message.FmCspMessageQuery;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.FptBusinessType;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.constant.FptProcessResult;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.action.FptMessageAction;
import com.bestway.common.message.action.CspMessageAction;
import com.bestway.common.message.entity.CspMessageSend;

public class FmFptMessageQuery extends FmCspMessageQuery {
	private FptManageAction fptManageAction = null;
	public FmFptMessageQuery(){
		fptManageAction = (FptManageAction) CommonVars
		.getApplicationContext().getBean("fptManageAction");
		fptManageAction.permissionCheckMessageQuery(new Request(CommonVars.getCurrUser()));
	}
	
	@Override
	protected String getFormatFileNameByType(String sysType,
			String messageFileName) {
		String formatFileName = ((FptMessageAction) cspMessageAction)
				.getFormatFileNameByType(new Request(CommonVars.getCurrUser()),
						sysType, messageFileName);
		System.out.println("formatFileName:" + formatFileName);
		return formatFileName;
	}

	@Override
	protected CspMessageAction getCspMessageAction() {
		return (CspMessageAction) CommonVars.getApplicationContext().getBean(
				"fptMessageAction");
	}

	@Override
	protected void makeDefaultReturnInfoMessage(String sysType,
			CspMessageSend messageSend) {
		String fileName = messageSend.getFileName();
		String inOutFlag = ((FptMessageAction) cspMessageAction).getInOutFlag(
				new Request(CommonVars.getCurrUser()), sysType, fileName);
		String seqNo = null;
		String customsNo = null;
		Date endDate=null;
		if ((FptBusinessType.FPT_APP.equals(sysType))) {
			String delcareType = ((FptMessageAction) cspMessageAction)
					.getCspMessageDeclareType(
							new Request(CommonVars.getCurrUser()), fileName);
			if (DelcareType.APPLICATION.equals(delcareType)) {
				if (FptInOutFlag.OUT.equals(inOutFlag)) {
					DgFptMessageInputNo dg = new DgFptMessageInputNo();
					dg.setEmsNoType("电子口岸统一编号");
					dg.setVisible(true);
					if (dg.isOK()) {
						seqNo = dg.getEmsNo();
					} else {
						return;
					}
				}
				DgFptMessageInputNo dg = new DgFptMessageInputNo();
				dg.setEmsNoType("申请表编号");
				dg.setVisible(true);
				if (dg.isOK()) {
					customsNo = dg.getEmsNo();
				} else {
					return;
				}
				
				//结束日期
				DgFptMessageEndDate dgEndDate = new DgFptMessageEndDate();
				dgEndDate.setVisible(true);
				if (dg.isOK()) {
					endDate = dgEndDate.getEndDate();
				} else {
					return;
				}
			}
		} else if ((FptBusinessType.FPT_BILL.equals(sysType) || FptBusinessType.FPT_BILL_BACK
				.equals(sysType))) {
			
			if (FptBusinessType.FPT_BILL.equals(sysType)
					&& FptInOutFlag.OUT.equals(inOutFlag)) {
				DgFptMessageInputNo dg = new DgFptMessageInputNo();
				dg.setEmsNoType("电子口岸统一编号");
				dg.setVisible(true);
				if (dg.isOK()) {
					seqNo = dg.getEmsNo();
				} else {
					return;
				}
				dg = new DgFptMessageInputNo();
				dg.setEmsNoType("发货单编号");
				dg.setVisible(true);
				if (dg.isOK()) {
					customsNo = dg.getEmsNo();
				} else {
					return;
				}
			} else if (FptBusinessType.FPT_BILL_BACK.equals(sysType)
					&& FptInOutFlag.IN.equals(inOutFlag)) {
				DgFptMessageInputNo dg = new DgFptMessageInputNo();
				dg.setEmsNoType("电子口岸统一编号");
				dg.setVisible(true);
				if (dg.isOK()) {
					seqNo = dg.getEmsNo();
				} else {
					return;
				}
				dg = new DgFptMessageInputNo();
				dg.setEmsNoType("发退货单编号");
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
			mapData.put("seqNo", seqNo);
			mapData.put("customsNo", customsNo);
			mapData.put("endDate", endDate);
			if ((FptBusinessType.FPT_APP.equals(sysType))) {
				if (FptInOutFlag.OUT.equals(inOutFlag)) {
					mapData.put("chkMark", FptProcessResult.IMP_CENTDB_SUCCESSD);
					cspMessageAction.makeDefaultReturnInfoMessage(new Request(
							CommonVars.getCurrUser()), fileName, mapData);
					mapData.put("chkMark", FptProcessResult.CHECK_PASS_ALL);
					cspMessageAction.makeDefaultReturnInfoMessage(new Request(
							CommonVars.getCurrUser()), fileName, mapData);
				} else {
					mapData.put("chkMark", FptProcessResult.CHECK_PASS_ALL);
					cspMessageAction.makeDefaultReturnInfoMessage(new Request(
							CommonVars.getCurrUser()), fileName, mapData);
				}
			} else {
				mapData.put("chkMark", FptProcessResult.CHECK_PASS_ALL);
				cspMessageAction.makeDefaultReturnInfoMessage(new Request(
						CommonVars.getCurrUser()), fileName, mapData);
			}
			JOptionPane.showMessageDialog(FmFptMessageQuery.this, "生成模拟回执成功",
					"提示", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(FmFptMessageQuery.this, "生成模拟回执失败 "
					+ ex.getMessage(), "提示", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	protected Vector getBusinessType() {
		Vector<ItemProperty> vector = new Vector<ItemProperty>();
		vector.add(new ItemProperty(String.valueOf(FptBusinessType.FPT_APP),
				FptBusinessType.getFptBusinessTypeDesc(FptBusinessType.FPT_APP)));
		vector.add(new ItemProperty(String.valueOf(FptBusinessType.FPT_BILL),
				FptBusinessType
						.getFptBusinessTypeDesc(FptBusinessType.FPT_BILL)));
		vector.add(new ItemProperty(String
				.valueOf(FptBusinessType.FPT_BILL_BACK), FptBusinessType
				.getFptBusinessTypeDesc(FptBusinessType.FPT_BILL_BACK)));
		vector.add(new ItemProperty(String
				.valueOf(FptBusinessType.FPT_CANCEL_BILL), FptBusinessType
				.getFptBusinessTypeDesc(FptBusinessType.FPT_CANCEL_BILL)));
		vector.add(new ItemProperty(String
				.valueOf(FptBusinessType.FPT_DOWN_DATA), FptBusinessType
				.getFptBusinessTypeDesc(FptBusinessType.FPT_DOWN_DATA)));
		return vector;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
