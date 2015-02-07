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
import com.bestway.client.custom.common.DgBaseExportCustomsDeclarationCommInfo;
import com.bestway.client.custom.common.EncCommon;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;

public class DgBcusExporCustomsDeclarationCommInfo extends
		DgBaseExportCustomsDeclarationCommInfo {

	private JPanel pnContractAmount = null; // @jve:decl-index=0:visual-constraint="10,55"

	private JLabel lbContractAmount = null;

	private EmsHeadH2k emsHeadH2k = null;

	private BcusContractExeInfo info = null;

	private ManualDeclareAction manualDecleareAction = null;

	private int impExpType;

	private String tradeCode;

	private double countAmount = 0.0;

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

	public EmsHeadH2k getEmsHeadH2k() {
		return emsHeadH2k;
	}

	public void setEmsHeadH2k(EmsHeadH2k emsHeadH2k) {
		this.emsHeadH2k = emsHeadH2k;
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
			this.setSize(this.getWidth(), 455);
			jContentPane.add(getPnContractAmount(), null);
		}
		super.setVisible(b);
	}

	@Override
	protected void showData() {
		super.showData();
		manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
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
			lbContractAmount.setBounds(new java.awt.Rectangle(6, 5, 457, 36));
			lbContractAmount.setText("JLabel");
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
			return;
		}
		boolean isMaterial = EncCommon.isMaterial(this.impExpType);
		info = ((EncAction) this.baseEncAction).findBcusEmsHeadH2kExeInfo(
				new Request(CommonVars.getCurrUser(), true), isMaterial,
				impExpType, tradeCode, emsHeadH2k, customsDeclarationCommInfo
						.getCommSerialNo().toString());

		String str = "";
		String isAmountControl = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser()), BcusParameter.Ems_Amount_Control);
		if (isAmountControl == null || !"1".equals(isAmountControl.trim())) {
			str = "";
		} else {
			if (impExpType == ImpExpType.BACK_MATERIEL_EXPORT) {
				// 查询可退料出口数
				countAmount = baseEncAction.countRetreatNumbers(new Request(
						CommonVars.getCurrUser()), customsDeclarationCommInfo
						.getBaseCustomsDeclaration().getEmsHeadH2k(),
						customsDeclarationCommInfo.getComplex().getId(), true,
						customsDeclarationCommInfo.getVersion(),
						customsDeclarationCommInfo.getCommSerialNo());
				str = ("料件可退换数:" + CommonVars.formatDoubleToString(countAmount,
						0, 5));
			} else if (impExpType == ImpExpType.REWORK_EXPORT) {// 返工复出
				// 查询未复出数据
				countAmount = baseEncAction.countReturnNumbers(new Request(
						CommonVars.getCurrUser()), customsDeclarationCommInfo
						.getBaseCustomsDeclaration().getEmsHeadH2k(),
						customsDeclarationCommInfo.getComplex().getId(), false,
						customsDeclarationCommInfo.getVersion(),
						customsDeclarationCommInfo.getCommSerialNo());
				str = ("可返工复出量:" + CommonVars.formatDoubleToString(countAmount,
						0, 5));
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
		System.out.println("isAmountControl=" + isAmountControl);
		if (isAmountControl == null || !"1".equals(isAmountControl.trim())) {
			return true;
		} else {
			if (this.getTfCommAmount().getValue() == null) {
				return true;
			}
			double commAmount = Double.parseDouble(this.getTfCommAmount()
					.getValue().toString());
			if (commAmount > countAmount) {
				String msg = "";
				if (impExpType == ImpExpType.BACK_MATERIEL_EXPORT) {
					msg = "数量不能大于退换数量可进口数";
					/*	} else if (impExpType == ImpExpType.REMAIN_FORWARD_EXPORT) {
							msg = "数量不能大于可余料转出数";*/
				} else if (impExpType == ImpExpType.REWORK_EXPORT) {// 返工复出
					msg = "数量不能大于可返工复出量";
				} else {
					return true;
				}

				if (CommonVars.getIsCustomAmountOut().booleanValue() == true) {
					String info = msg + ",\n确定要继续吗?";
					if (JOptionPane.showConfirmDialog(
							DgBcusExporCustomsDeclarationCommInfo.this, info,
							"严重警告", JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
						return false;
					}

				} else {
					JOptionPane.showMessageDialog(
							DgBcusExporCustomsDeclarationCommInfo.this, msg,
							"提示", JOptionPane.INFORMATION_MESSAGE);
					return false;
				}
			}
			return true;
		}
	}
}
