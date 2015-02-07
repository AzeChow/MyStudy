package com.bestway.bcs.client.contractexe;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractexe.action.ContractExeAction;
import com.bestway.bcs.contractexe.entity.BcsContractExeInfo;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.custom.common.DgBaseExportCustomsDeclarationCommInfo;
import com.bestway.client.custom.common.EncCommon;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;

public class DgBcsExportCustomsDeclarationCommInfo extends
		DgBaseExportCustomsDeclarationCommInfo {

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
		jLabel15.setVisible(false);
		jTextField.setVisible(false);
		jLabel16.setVisible(false);
		jComboBox.setVisible(false);
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
			lbContractAmount.setBounds(new java.awt.Rectangle(0, 0, 457, 36));
			lbContractAmount.setText("JLabel");
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
			return;
		}
		boolean isMaterial = EncCommon.isMaterial(this.impExpType);
		info = ((ContractExeAction) this.baseEncAction).findBcsContractExeInfo(
				new Request(CommonVars.getCurrUser(), true), isMaterial,
				impExpType, tradeCode, contract, customsDeclarationCommInfo
						.getCommSerialNo().toString(),
						customsDeclarationCommInfo.getBaseCustomsDeclaration().getCustomsEnvelopBillNo());
		info.setCurrentRemain(info.getCurrentRemain()
				+ (customsDeclarationCommInfo.getCommAmount() == null ? 0.0
						: customsDeclarationCommInfo.getCommAmount()));
		String str = "";
		if (impExpType == ImpExpType.BACK_MATERIEL_EXPORT) {
			str = ("合同定量:"
					+ CommonVars.formatDoubleToString(info.getContractAmount(),0,5)
					+ ";可退料出口数" + CommonVars.formatDoubleToString(info
					.getCurrentRemain(),0,5));

		} else if (impExpType == ImpExpType.REMAIN_FORWARD_EXPORT) {
			str = ("合同定量:"
					+ CommonVars.formatDoubleToString(info.getContractAmount(),0,5)
					+ ";可余料转出数" + CommonVars.formatDoubleToString(info
					.getCurrentRemain(),0,5));

		} else if (impExpType == ImpExpType.REWORK_EXPORT) {// 返工复出
			str = ("合同定量:" + CommonVars.formatDoubleToString(info.getContractAmount(),0,5)
					+ ";  可返工复出量" + CommonVars.formatDoubleToString(info
					.getCurrentRemain(),0,5));
		} else {
			str = "合同定量:" + CommonVars.formatDoubleToString(info.getContractAmount(),0,5)
			+ ";  合同余量:"
					+ CommonVars.formatDoubleToString(info.getContractRemain(),0,5) + ";  当前余量"
					+ CommonVars.formatDoubleToString(info.getCurrentRemain(),0,5);
		}
		
//		// 当为成品出口时，可直接出口量 = 当前余量 - 关封余量
//		if(impExpType == ImpExpType.DIRECT_EXPORT) {
//			str += "<br/>关封数量：" + CommonUtils.formatDoubleByDigit(info.getCustomsNum(), 5);
//			str += "；可直接出口量：" + CommonUtils.formatDoubleByDigit(info.getCurrentRemain()
//					- info.getCustomsRemain(), 5);
//		}
//		// 当为转厂出口，（选择关封号）可直接出口量 = 关封余量。（没有选择关封号）可直接出口量 = 当前余量 - 关封余量
//		if(impExpType == ImpExpType.TRANSFER_FACTORY_EXPORT) {
//			if(customsDeclarationCommInfo.getBaseCustomsDeclaration().getCustomsEnvelopBillNo()==null||
//					"".equals(customsDeclarationCommInfo.getBaseCustomsDeclaration().getCustomsEnvelopBillNo())) {
//				str += "<br/>关封数量：" + CommonUtils.formatDoubleByDigit(info.getCustomsNum(), 5);
//				str += "；可直接出口量：" + CommonUtils.formatDoubleByDigit(info.getCurrentRemain()
//						- info.getCustomsRemain(), 5);
//			} else {
//				str += "<br/>关封数量：" + CommonUtils.formatDoubleByDigit(info.getCustomsNum(), 5);
//				str += "；可直接出口量：" + CommonUtils.formatDoubleByDigit(info.getCustomsRemain(), 5);
//			}
//		}
		
		lbContractAmount.setText("<html><body>" + str + "</body></html>");
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
			if (impExpType == ImpExpType.BACK_MATERIEL_EXPORT) {
				msg = "数量不能大于可退料出口数";
			} else if (impExpType == ImpExpType.REMAIN_FORWARD_EXPORT) {
				msg = "数量不能大于可余料转出数";
			} else if (impExpType == ImpExpType.REWORK_EXPORT) {// 返工复出
				msg = "数量不能大于可返工复出量";
			}  else {
				msg = "数量不能大于当前余量";
			}

			if (CommonVars.getIsCustomAmountOut().booleanValue() == true) {
				String info = msg + ",\n确定要继续吗?";
				if (JOptionPane.showConfirmDialog(
						DgBcsExportCustomsDeclarationCommInfo.this, info,
						"严重警告", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
					return false;
				}

			} else {
				JOptionPane.showMessageDialog(
						DgBcsExportCustomsDeclarationCommInfo.this, msg, "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		return true;
	}
}
