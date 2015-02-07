package com.bestway.dzsc.client.contractexe;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.custom.common.DgBaseImportCustomsDeclarationCommInfo;
import com.bestway.client.custom.common.EncCommon;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.dzsc.contractexe.action.DzscContractExeAction;
import com.bestway.dzsc.contractexe.entity.DzscContractExeInfo;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;

public class DgDzscImportCustomsDeclarationCommInfo extends
		DgBaseImportCustomsDeclarationCommInfo {

	private JPanel pnContractMoney = null;

	private JLabel lbContractAmount = null;

	private DzscEmsPorHead contract = null;

	private DzscContractExeInfo info = null;

	private int impExpType;

	private String tradeCode;

	public int getImpExpType() {
		return impExpType;
	}

	public void setImpExpType(int impExpType) {
		this.impExpType = impExpType;
	}

	public String getTradeCode() {
		return tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	public DzscEmsPorHead getContract() {
		return contract;
	}

	public void setContract(DzscEmsPorHead contract) {
		this.contract = contract;
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			this.setSize(this.getWidth(), 455);
			jContentPane.add(getPnContractMoney(), null);
		}
		super.setVisible(b);
	}

	@Override
	protected void showData() {
		super.showData();
		tfVersion.setVisible(false);
		lbVersion.setVisible(false);
		showContractMoney();
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnContractMoney() {
		if (pnContractMoney == null) {
			lbContractAmount = new JLabel();
			lbContractAmount.setBounds(new java.awt.Rectangle(6, 5, 457, 21));
			lbContractAmount.setText("JLabel");
			lbContractAmount.setForeground(java.awt.Color.blue);
			pnContractMoney = new JPanel();
			pnContractMoney.setLayout(null);
			pnContractMoney.setBounds(new java.awt.Rectangle(39, 353, 468, 31));
			pnContractMoney.add(lbContractAmount, null);
		}
		return pnContractMoney;
	}

	private void showContractMoney() {
		if (contract == null
				|| customsDeclarationCommInfo.getCommSerialNo() == null) {
			lbContractAmount.setText("");
			return;
		}
		boolean isMaterial = EncCommon.isMaterial(this.impExpType);
		info = ((DzscContractExeAction) this.baseEncAction)
				.findDzscContractExeInfo(new Request(CommonVars.getCurrUser(),
						true), isMaterial, impExpType, tradeCode, contract,
						customsDeclarationCommInfo.getCommSerialNo());
		info.setCurrentRemain(info.getCurrentRemain()
				+ (customsDeclarationCommInfo.getCommAmount() == null ? 0.0
						: customsDeclarationCommInfo.getCommAmount()));
		
		if (impExpType == ImpExpType.BACK_FACTORY_REWORK) {
			lbContractAmount.setText("合同定量:"
					+ CommonVars.formatDoubleToString(info.getContractAmount(),0,5)
					+ ";可退厂返工量"
					+ CommonVars.formatDoubleToString(info.getCurrentRemain(),0,5));
		} else if (impExpType == ImpExpType.DIRECT_IMPORT
				&& (tradeCode.equals("0300") || tradeCode.equals("0700"))) {
			lbContractAmount.setText("合同定量:"
					+ CommonVars.formatDoubleToString(info.getContractAmount(),0,5)
					+ ";可退料退还进口数"
					+ CommonVars.formatDoubleToString(info.getCurrentRemain(),0,5));
		} else if (impExpType == ImpExpType.MATERIAL_DOMESTIC_SALES) {
			lbContractAmount.setText("合同定量:"
					+ CommonVars.formatDoubleToString(info.getContractAmount(),0,5)
					+ ";可内销数量"
					+ CommonVars.formatDoubleToString(info.getCurrentRemain(),0,5));
		} else {
			lbContractAmount.setText("合同定量:"
					+ CommonVars.formatDoubleToString(info.getContractAmount(),0,5)
					+ ";合同余量:"
					+ CommonVars.formatDoubleToString(info.getContractRemain(),0,5)
					+ ";当前余量"
					+ CommonVars.formatDoubleToString(info.getCurrentRemain(),0,5));
		}
	}

	@Override
	protected boolean checkAmount() {
		if (this.getTfCommAmount().getValue() == null) {
			return true;
		}
		double commAmount = Double.parseDouble(this.getTfCommAmount()
				.getValue().toString());
		if (commAmount > info.getCurrentRemain()) {
			String msg = "";
			if (impExpType == ImpExpType.BACK_FACTORY_REWORK) {
				msg = "数量不能大于可退厂返工数量";
			} else if (impExpType == ImpExpType.DIRECT_IMPORT
					&& (tradeCode.equals("0300") || tradeCode.equals("0700"))) {
				msg = "数量不能大于可退料退还进口数";
			} else if (impExpType == ImpExpType.MATERIAL_DOMESTIC_SALES) {
				msg = "数量不能大于可内销数量";
			} else {
				msg = "数量不能大于当前余量";
			}

			if (CommonVars.getIsCustomAmountOut().booleanValue() == true) {
				String info = msg + ",\n确定要继续吗？？";
				if (JOptionPane.showConfirmDialog(
						DgDzscImportCustomsDeclarationCommInfo.this, info,
						"严重警告!!!", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
					return false;
				}

			} else {
				JOptionPane.showMessageDialog(
						DgDzscImportCustomsDeclarationCommInfo.this, msg, "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		return true;
	}
}
