package com.bestway.bcus.client.enc;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.BcusContractExeInfo;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.client.custom.common.DgBaseImportCustomsDeclarationCommInfo;
import com.bestway.client.custom.common.EncCommon;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;

public class DgBcusImportCustomsDeclarationCommInfo extends
		DgBaseImportCustomsDeclarationCommInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel pnContractAmount = null; // @jve:decl-index=0:visual-constraint="10,55"

	private JLabel lbContractAmount = null;

	private EmsHeadH2k emsHeadH2k = null;

	private BcusContractExeInfo info = null;

	private int impExpType;

	private String tradeCode;

	private double countAmount;

	ManualDeclareAction manualDecleareAction = (ManualDeclareAction) CommonVars
			.getApplicationContext().getBean("manualdeclearAction");

	String isAmountControl = manualDecleareAction.getBpara(new Request(
			CommonVars.getCurrUser()), BcusParameter.Ems_Amount_Control);

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

	public double getCountAmount() {
		return countAmount;
	}

	public void setCountAmount(double countAmount) {
		this.countAmount = countAmount;
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			this.setSize(this.getWidth(), 455);//this.getWidth()
			jContentPane.add(getPnContractAmount(), null);
		}
		super.setVisible(b);
	}

	public EmsHeadH2k getEmsHead() {
		return emsHeadH2k;
	}

	public void setEmsHead(EmsHeadH2k emsHead) {
		this.emsHeadH2k = emsHead;
	}

	@Override
	protected void showData() {
		super.showData();

		if (emsHeadH2k == null
				|| customsDeclarationCommInfo.getBaseCustomsDeclaration() == null
				|| customsDeclarationCommInfo.getBaseCustomsDeclaration()
						.getImpExpType() == null) {
			tfVersion.setVisible(false);
			lbVersion.setVisible(false);
		} else {
			if (customsDeclarationCommInfo.getBaseCustomsDeclaration() != null
					&& customsDeclarationCommInfo.getBaseCustomsDeclaration()
							.getImpExpType() == ImpExpType.BACK_FACTORY_REWORK) {
				tfVersion.setVisible(true);
				lbVersion.setVisible(true);
			}
		}
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
			lbContractAmount.setBounds(new java.awt.Rectangle(6, 5, 457, 21));
			lbContractAmount.setText("ContractAmount");

			lbContractAmount.setForeground(java.awt.Color.blue);
			pnContractAmount = new JPanel();
			pnContractAmount.setLayout(null);
			pnContractAmount
					.setBounds(new java.awt.Rectangle(39, 353, 468, 31));
			pnContractAmount.add(lbContractAmount, null);
		}
		return pnContractAmount;
	}

	private void showContractAmount() {
		if (emsHeadH2k == null
				|| customsDeclarationCommInfo.getCommSerialNo() == null) {
			lbContractAmount.setText("");
			return;
		}
		boolean isMaterial = EncCommon.isMaterial(this.impExpType);
		info = ((EncAction) this.baseEncAction).findBcusEmsHeadH2kExeInfo(
				new Request(CommonVars.getCurrUser(), true), isMaterial,
				impExpType, tradeCode, emsHeadH2k, customsDeclarationCommInfo
						.getCommSerialNo().toString());
		info.setCurrentRemain(info.getCurrentRemain()
				+ (customsDeclarationCommInfo.getCommAmount() == null ? 0.0
						: customsDeclarationCommInfo.getCommAmount()));
		String str = "";

		if (isAmountControl == null || !"1".equals(isAmountControl.trim())) {
			str = "";
		} else {
			if (impExpType == ImpExpType.BACK_FACTORY_REWORK) {
				// 可退厂返工量
				countAmount = baseEncAction.countRetreatNumbers(new Request(
						CommonVars.getCurrUser()), customsDeclarationCommInfo
						.getBaseCustomsDeclaration().getEmsHeadH2k(),
						customsDeclarationCommInfo.getComplex().getId(), false,
						customsDeclarationCommInfo.getVersion(),
						customsDeclarationCommInfo.getCommSerialNo());
				str = "可退厂返工量"
						+ CommonVars.formatDoubleToString(countAmount, 0, 5);
			} else if (impExpType == ImpExpType.DIRECT_IMPORT
					&& (tradeCode.equals("0300") || tradeCode.equals("0700"))) {
				// 查询未复进数据
				countAmount = baseEncAction.countReturnNumbers(new Request(
						CommonVars.getCurrUser()), customsDeclarationCommInfo
						.getBaseCustomsDeclaration().getEmsHeadH2k(),
						customsDeclarationCommInfo.getComplex().getId(), true,
						customsDeclarationCommInfo.getVersion(),
						customsDeclarationCommInfo.getCommSerialNo());
				str = "料件退换可进口数"
						+ CommonVars.formatDoubleToString(countAmount, 0, 5);
			} else if (impExpType == ImpExpType.MATERIAL_DOMESTIC_SALES) {
				lbContractAmount.setText("");
				// lbContractAmount.setText("可内销数量"
				// + CommonVars.formatDoubleToString(info.getCurrentRemain(),
				// 0, 5));
			} else {
				str = "";
			}
		}
		lbContractAmount.setText(str);
	}

	@Override
	protected boolean checkAmount() {
		String isAmountControl = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser()), BcusParameter.Ems_Amount_Control);
		if (isAmountControl == null || !"1".equals(isAmountControl.trim()))
			return true;
		else {
			if (this.getTfCommAmount().getValue() == null) {
				return true;
			}
			double commAmount = Double.parseDouble(this.getTfCommAmount()
					.getValue().toString());
			if (commAmount > countAmount) {
				String msg = "";
				if (impExpType == ImpExpType.BACK_FACTORY_REWORK) {
					msg = "数量不能大于退厂返工可进口数";
				} else if (impExpType == ImpExpType.DIRECT_IMPORT
						&& (tradeCode.equals("0300") || tradeCode
								.equals("0700"))) {
					msg = "数量不能大于料件退换可进口数";
				} else {
					return true;
				}

				if (CommonVars.getIsCustomAmountOut().booleanValue() == true) {
					String info = msg + ",\n确定要继续吗?";
					if (JOptionPane.showConfirmDialog(
							DgBcusImportCustomsDeclarationCommInfo.this, info,
							"严重警告!!!", JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
						return false;
					}
				} else {
					JOptionPane.showMessageDialog(
							DgBcusImportCustomsDeclarationCommInfo.this, msg,
							"提示", JOptionPane.INFORMATION_MESSAGE);
					return false;
				}
			}
			return true;
		}
	}
}