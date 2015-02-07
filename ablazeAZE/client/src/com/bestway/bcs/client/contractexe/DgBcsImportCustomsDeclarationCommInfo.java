package com.bestway.bcs.client.contractexe;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractexe.action.ContractExeAction;
import com.bestway.bcs.contractexe.entity.BcsContractExeInfo;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.custom.common.DgBaseImportCustomsDeclarationCommInfo;
import com.bestway.client.custom.common.EncCommon;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;

public class DgBcsImportCustomsDeclarationCommInfo extends
		DgBaseImportCustomsDeclarationCommInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel pnContractAmount = null; // @jve:decl-index=0:visual-constraint="10,55"

	private JLabel lbContractAmount = null;

	private Contract contract = null;

	private BcsContractExeInfo info = null;

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

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			this.setSize(this.getWidth(), 455);
			jContentPane.add(getPnContractAmount(), null);
		}
		super.setVisible(b);
	}

	@Override
	protected void showData() {
		super.showData();
		lbProjectDept.setVisible(false);
		cbbProjectDept.setVisible(false);
		tfVersion.setVisible(false);
		lbVersion.setVisible(false);
		showContractAmount();
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnContractAmount() {
		if (pnContractAmount == null) {

			lbContractAmount = new JLabel();
			lbContractAmount.setBounds(new java.awt.Rectangle(1, 2, 457, 36));
			lbContractAmount.setText("ContractAmount");

			lbContractAmount.setForeground(java.awt.Color.blue);
			pnContractAmount = new JPanel();
			pnContractAmount.setLayout(null);
			pnContractAmount
					.setBounds(new java.awt.Rectangle(39, 353, 468, 36));
			pnContractAmount.add(lbContractAmount, null);
		}
		return pnContractAmount;
	}

	private void showContractAmount() {
		if (contract == null
				|| customsDeclarationCommInfo.getCommSerialNo() == null) {
			lbContractAmount.setText("");
			return;
		}
		boolean isMaterial = EncCommon.isMaterial(this.impExpType);
		info = ((ContractExeAction) this.baseEncAction).findBcsContractExeInfo(
				new Request(CommonVars.getCurrUser(), true), isMaterial,
				impExpType, tradeCode, contract, customsDeclarationCommInfo
						.getCommSerialNo().toString(),
						customsDeclarationCommInfo.getBaseCustomsDeclaration().getCustomsEnvelopBillNo());
		
		if(this.impExpType == ImpExpType.MATERIAL_DOMESTIC_SALES) {
			lbContractAmount.setText("余量:"
					+ CommonVars.formatDoubleToString(info.getCurrentRemain(),0,5)
					+ ";可内销数量"
					+ CommonVars.formatDoubleToString(info.getCanInSell(),0,5));
			
			return ;
		}
		
		
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
		} 
//		else if (impExpType == ImpExpType.MATERIAL_DOMESTIC_SALES) {
//			lbContractAmount.setText("合同定量:"
//					+ CommonVars.formatDoubleToString(info.getContractAmount(),0,5)
//					+ ";可内销数量"
//					+ CommonVars.formatDoubleToString(info.getCurrentRemain(),0,5));
//		} 
		else {
			lbContractAmount.setText("合同定量:"
					+ CommonVars.formatDoubleToString(info.getContractAmount(),0,5)
					+ ";合同余量:"
					+ CommonVars.formatDoubleToString(info.getContractRemain(),0,5)
					+ ";当前余量"
					+ CommonVars.formatDoubleToString(info.getCurrentRemain(),0,5));
		}
		String str = lbContractAmount.getText();
		// 当为料件进口时，可直接进口量 = 当前余量 - 关封余量
		if(impExpType == ImpExpType.DIRECT_IMPORT) {
			str += "<br/>关封数量：" + CommonUtils.formatDoubleByDigit(info.getCustomsNum(), 5);
			str += "；可直接进口量：" + CommonUtils.formatDoubleByDigit(info.getCurrentRemain()
					- info.getCustomsRemain(), 5);
		}
		// 当为料件转厂，（选择关封号）可直接进口量 = 关封余量。（没有选择关封号）可直接进口量 = 当前余量 - 关封余量
		if(impExpType == ImpExpType.TRANSFER_FACTORY_IMPORT) {
			if(customsDeclarationCommInfo.getBaseCustomsDeclaration().getCustomsEnvelopBillNo()==null||
					"".equals(customsDeclarationCommInfo.getBaseCustomsDeclaration().getCustomsEnvelopBillNo())) {
				str += "<br/>关封数量：" + CommonUtils.formatDoubleByDigit(info.getCustomsNum(), 5);
				str += "；可直接进口量：" + CommonUtils.formatDoubleByDigit(info.getCurrentRemain()
						- info.getCustomsRemain(), 5);
			} else {
				str += "<br/>关封数量：" + CommonUtils.formatDoubleByDigit(info.getCustomsNum(), 5);
				str += "；可直接进口量：" + CommonUtils.formatDoubleByDigit(info.getCustomsRemain(), 5);
			}
		}
		
		lbContractAmount.setText("<html><body>" + str + "</body></html>");
	}

	@Override
	protected boolean checkAmount() {
		if (this.getTfCommAmount().getValue() == null) {
			return true;
		}
		double commAmount = Double.parseDouble(this.getTfCommAmount()
				.getValue().toString());
		if (commAmount > CommonUtils.getDoubleByDigit(info.getCurrentRemain(), 5)) {
			String msg = "";
			if (impExpType == ImpExpType.BACK_FACTORY_REWORK) {
				msg = "数量不能大于可退厂返工量";
			} else if (impExpType == ImpExpType.DIRECT_IMPORT
					&& (tradeCode.equals("0300") || tradeCode.equals("0700"))) {
				msg = "数量不能大于可退料退还进口数";
			} else {
				msg = "数量不能大于当前余量";
			}

			if (CommonVars.getIsCustomAmountOut().booleanValue() == true) {
				String info = msg + ",\n确定要继续吗?";
				if (JOptionPane.showConfirmDialog(
						DgBcsImportCustomsDeclarationCommInfo.this, info,
						"严重警告!!!", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
					return false;
				}
			} else {
				JOptionPane.showMessageDialog(
						DgBcsImportCustomsDeclarationCommInfo.this, msg, "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		if(impExpType == ImpExpType.MATERIAL_DOMESTIC_SALES){
			if(commAmount > CommonUtils.getDoubleByDigit(info.getCanInSell(), 5)){
//				if(CommonVars.getIsCustomAmountOut().booleanValue() == true){
					if(JOptionPane.YES_OPTION == JOptionPane.showOptionDialog(getParent(), "数量不能大于可内销数量,\n确定要继续吗?","提示", JOptionPane.YES_NO_OPTION, 
							JOptionPane.INFORMATION_MESSAGE,null, new Object[]{"是","否"},"否")){
						return true;
					}
//				}else{
//					JOptionPane.showMessageDialog(
//							DgBcsImportCustomsDeclarationCommInfo.this, "数量不能大于可内销数量!", "提示",
//							JOptionPane.INFORMATION_MESSAGE);
//				}
				return false;
			}
		}
		return true;
	}
}
