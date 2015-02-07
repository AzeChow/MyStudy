/*
 * Created on 2005-6-3
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.custom.common;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.FptAppItem;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.transferfactory.action.TransferFactoryManageAction;
import com.bestway.common.transferfactory.entity.CustomsEnvelopCommodityInfo;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.TempTransFactInfo;

/**
 * @author ls // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class EncCommon {

	public static boolean savecheck(int projectType,
			BaseCustomsDeclaration customsDeclaration, Component component,
			CompanyOther other) {
		BaseEncAction baseEncAction = (EncAction) CommonVars
				.getApplicationContext().getBean("encAction");
		if (customsDeclaration == null) {
			return false;
		}
		boolean isRecustoms = false;
		if (customsDeclaration.getAuthorizeFile() != null
				&& !"".equals(customsDeclaration.getAuthorizeFile())) {
			isRecustoms = baseEncAction.isReCustoms(
					new Request(CommonVars.getCurrUser()), projectType,
					customsDeclaration.getSerialNumber(), "authorizeFile",
					customsDeclaration.getAuthorizeFile());
			if (isRecustoms) {
				JOptionPane.showMessageDialog(component, "外汇销销单号不可重复！", "提示",
						JOptionPane.YES_NO_OPTION);
				return true;
			}
		}
		if (customsDeclaration.getInvoiceCode() != null
				&& !"".equals(customsDeclaration.getInvoiceCode())) {
			//
			// 进口发票暂不考虑检查重复，因为台达用到发票栏位
			//
			if (customsDeclaration.getImpExpFlag() == ImpExpFlag.IMPORT) {
				return false;
			}
			isRecustoms = baseEncAction.isReCustoms(
					new Request(CommonVars.getCurrUser()), projectType,
					customsDeclaration.getSerialNumber(), "invoiceCode",
					customsDeclaration.getInvoiceCode());
			if (isRecustoms) {
				JOptionPane.showMessageDialog(component, "发票号不可重复！", "提示",
						JOptionPane.YES_NO_OPTION);
				return true;
			}
		}
		// 如果是料件转厂或转厂出口，不检查重复和13位
		if (customsDeclaration.getImpExpType() != null
				&& (customsDeclaration.getImpExpType().equals(
						Integer.valueOf(ImpExpType.TRANSFER_FACTORY_IMPORT)) || customsDeclaration
						.getImpExpType()
						.equals(Integer
								.valueOf(ImpExpType.TRANSFER_FACTORY_EXPORT)))) {
			return false;
		}
		// 检查运输工具位数时加上一额外自定义位数，在系统设置-其他设置-其他设置-运输工具额外位数
		// 若为空默认为14
		int addDigit = other.getTransportTool() == null ? 14 : (other
				.getTransportTool() + 1);
		if (customsDeclaration.getConveyance() != null
				&& !"".equals(customsDeclaration.getConveyance())) {
			String conveyance = customsDeclaration.getConveyance();
			if (conveyance.equals("@")) {
				if (conveyance.length() != 14 && conveyance.length() != 17
						&& conveyance.length() != addDigit) {
					JOptionPane.showMessageDialog(component,
							"司机纸号码应该为13位或者16位或用户自定义位!", "提示",
							JOptionPane.YES_NO_OPTION);
					return true;
				}
			}
		}
		if (other == null || other.getIsReConveyance() == null
				|| (new Boolean(false)).equals(other.getIsReConveyance())) {
			if (customsDeclaration.getConveyance() != null
					&& !"".equals(customsDeclaration.getConveyance())) {
				isRecustoms = baseEncAction.isReCustoms(
						new Request(CommonVars.getCurrUser()), projectType,
						customsDeclaration.getSerialNumber(), "conveyance",
						customsDeclaration.getConveyance());
				if (isRecustoms) {
					JOptionPane.showMessageDialog(component, "司机纸号码不可重复！",
							"提示", JOptionPane.YES_NO_OPTION);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 检查进口报关单数据
	 * 
	 * @return
	 */

	public static boolean checkImportCustomsDeclarationData(
			BaseCustomsDeclaration customsDeclaration, Component component) {
		ContractAction contractAction = (ContractAction) CommonVars
				.getApplicationContext().getBean("contractAction");
		if (customsDeclaration.getCurrency() == null) {
			JOptionPane.showMessageDialog(component, "币制不能为空", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		if (customsDeclaration.getDeclarationDate() == null) {
			JOptionPane.showMessageDialog(component, "申报日期不能为空", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		String curr = customsDeclaration.getCurrency().getCurrSymb().trim();
		// 申报日期
		Date date = customsDeclaration.getDeclarationDate();
		Calendar gc = Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DAY_OF_MONTH, 1);
		date = gc.getTime();
		if (null != customsDeclaration.getInsurCurr()
				&& !"".equals(customsDeclaration.getInsurCurr())) {
			if (!customsDeclaration
					.getInsurCurr()
					.getCurrSymb()
					.trim()
					.equals(customsDeclaration.getCurrency().getCurrSymb()
							.trim())) {
				String insuranceCurr = customsDeclaration.getInsurCurr()
						.getCurrSymb().trim();
				List list1 = contractAction.findCurrRateByDeclarationDate(
						new Request(CommonVars.getCurrUser()), curr,
						insuranceCurr, date);
				if (null == list1 || list1.size() < 1) {
					JOptionPane.showMessageDialog(component, "本币对保费币制汇率不存在！",
							"提示", JOptionPane.YES_NO_OPTION);
					return false;
				}
			}
		}
		if (null != customsDeclaration.getFeeCurr()
				&& !"".equals(customsDeclaration.getFeeCurr())) {
			if (!customsDeclaration
					.getFeeCurr()
					.getCurrSymb()
					.trim()
					.equals(customsDeclaration.getCurrency().getCurrSymb()
							.trim())) {
				String freightageCurr = customsDeclaration.getFeeCurr()
						.getCurrSymb().trim();
				List list2 = contractAction.findCurrRateByDeclarationDate(
						new Request(CommonVars.getCurrUser()), curr,
						freightageCurr, date);
				if (null == list2 || list2.size() < 1) {
					JOptionPane.showMessageDialog(component, "本币对运费币制汇率不存在！",
							"提示", JOptionPane.YES_NO_OPTION);
					return false;
				}
			}
		}
		if (customsDeclaration == null) {
			return false;
		}
		if (customsDeclaration.getImpExpType() == null) {
			JOptionPane.showMessageDialog(component, "进口类型不能为空", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		if (customsDeclaration.getCustoms() == null) {
			JOptionPane.showMessageDialog(component, "进口口岸不能为空", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		if (customsDeclaration.getImpExpDate() == null) {
			JOptionPane.showMessageDialog(component, "进口日期不能为空", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		if (customsDeclaration.getTradeName() == null
				|| customsDeclaration.getTradeName().equals("")) {
			JOptionPane.showMessageDialog(component, "经营单位不能为空", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		if (customsDeclaration.getTransferMode() == null) {
			JOptionPane.showMessageDialog(component, "运输方式不能为空", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		if (customsDeclaration.getDomesticDestinationOrSource() == null) {
			JOptionPane.showMessageDialog(null, "境内目的地不能为空", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		if (customsDeclaration.getMachName() == null
				|| customsDeclaration.getMachName().equals("")) {
			JOptionPane.showMessageDialog(component, "收货单位不能为空", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		if (customsDeclaration.getTradeMode() == null) {
			JOptionPane.showMessageDialog(component, "贸易方式不能为空", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		if (null != customsDeclaration.getCustoms()) {
			if ("皇岗海关".equals(customsDeclaration.getCustoms().getName())
					|| "文锦渡关".equals(customsDeclaration.getCustoms().getName())) {
				if (!"公路运输".equals(customsDeclaration.getTransferMode()
						.getName())) {
					JOptionPane.showMessageDialog(component, "运输方式错误，应为公路运输！",
							"提示", JOptionPane.YES_NO_OPTION);
					return false;
				}
			}
		}
		// 征免性质
		String tradeCode = customsDeclaration.getTradeMode().getCode();
		if (!"1234".equals(tradeCode)
				&& !"1200".equals(tradeCode)
				&& !"1233".equals(tradeCode)
				&& !"1139".equals(tradeCode) // 直接进口报关单贸易方式代码为1139可为空 edit by lm
				// 2009-12-10
				// 转厂报关单可为空 edit by cjb 2009.11.03 2009110202
				&& !customsDeclaration.getImpExpType().equals(
						ImpExpType.TRANSFER_FACTORY_IMPORT)
				&& !customsDeclaration.getImpExpType().equals(
						ImpExpType.BACK_FACTORY_REWORK)
				&& !customsDeclaration.getImpExpType().equals(
						ImpExpType.MATERIAL_DOMESTIC_SALES)
				&& !customsDeclaration.getImpExpType().equals(
						ImpExpType.REMAIN_FORWARD_IMPORT)) {
			if (customsDeclaration.getLevyKind() == null
					&& !(customsDeclaration.getImpExpType().equals(
							ImpExpType.DIRECT_IMPORT) && "0700"
							.equals(tradeCode))) {
				JOptionPane.showMessageDialog(component, "征免性质不能为空", "提示",
						JOptionPane.YES_NO_OPTION);
				return false;
			}
		}

		if (customsDeclaration.getCountryOfLoadingOrUnloading() == null) {
			JOptionPane.showMessageDialog(component, "起运国不能为空", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		if (customsDeclaration.getPortOfLoadingOrUnloading() == null) {
			JOptionPane.showMessageDialog(component,
					"装货港不能为空，如无实际进出境，填写“中国境内”，代码“0142”", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}

		if (customsDeclaration.getTransac() == null) {
			JOptionPane.showMessageDialog(component, "成交方式不能为空", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		int pieceNum = customsDeclaration.getCommodityNum() == null ? 0
				: customsDeclaration.getCommodityNum().intValue();
		if (pieceNum <= 0) {
			JOptionPane.showMessageDialog(component, "件数不能小于1", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		double grossWeight = customsDeclaration.getGrossWeight() == null ? 0
				: Double.parseDouble(customsDeclaration.getGrossWeight()
						.toString());
		if (grossWeight <= 0) {
			JOptionPane.showMessageDialog(component, "毛重不能小于或等于零", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		// 认QP允许报关单表头净重，毛重小于1的情况。 08年报关单填制规范是不足1KG的，填1KG，QP早就可以输小数位了，允许小于1。
		// if (grossWeight < 1) {
		// JOptionPane.showMessageDialog(component, "毛重不能小于1", "提示",
		// JOptionPane.YES_NO_OPTION);
		// return false;
		// }
		double netWeight = customsDeclaration.getNetWeight() == null ? 0
				: Double.parseDouble(customsDeclaration.getNetWeight()
						.toString());
		if (netWeight <= 0) {
			JOptionPane.showMessageDialog(component, "净重不能小于或等于零", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}

		/*
		 * hwy 2012-8-17
		 */
		if (netWeight < 1) {
			JOptionPane.showMessageDialog(component, "温馨提示：净重小于1并通过检查", "提示",
					JOptionPane.YES_NO_OPTION);
		}
		if (netWeight > grossWeight) {
			JOptionPane.showMessageDialog(component, "净重不能大于毛重", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		if (customsDeclaration.getContainerNum() == null
				|| customsDeclaration.getContainerNum().equals("")) {
			if (customsDeclaration.getTransferMode().getName().equals("物流园区")
					|| customsDeclaration.getTransferMode().getName()
							.equals("物流中心")
					|| customsDeclaration.getTransferMode().getName()
							.equals("其它运输")) {
			} else {
				JOptionPane.showMessageDialog(component, "集装箱号不能为空", "提示",
						JOptionPane.YES_NO_OPTION);
				return false;
			}
		}
		if (customsDeclaration.getDeclarationCompany() == null) {
			JOptionPane.showMessageDialog(component, "申报单位不可为空!!!", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		if (customsDeclaration.getWrapType() == null) {
			JOptionPane.showMessageDialog(component, "包装种类不能为空", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		if (customsDeclaration.getUses() == null) {
			JOptionPane.showMessageDialog(component, "用途不能为空", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		if (customsDeclaration.getCustomser() == null
				|| customsDeclaration.getCustomser().trim().equals("")) {
			JOptionPane.showMessageDialog(component, "报关员不能为空!!", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		if (customsDeclaration.getTelephone() == null
				|| customsDeclaration.getTelephone().trim().equals("")) {
			JOptionPane.showMessageDialog(component, "联系电话不能为空!!", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}

		// if (customsDeclaration.getImpExpType().intValue() !=
		// ImpExpType.TRANSFER_FACTORY_IMPORT
		// && !(customsDeclaration.getTradeMode().getName()
		// .indexOf("余料结转") > 0)) {
		// // edit by xxm 2005-9-28
		// if (!customsDeclaration.getTransferMode().getName().equals("非保税区")
		// && !customsDeclaration.getTransferMode().getName().equals(
		// "保税仓库")
		// && !customsDeclaration.getTransferMode().getName().equals(
		// "保税港区")) {
		// if (customsDeclaration.getConveyance() == null
		// || customsDeclaration.getConveyance().trim().equals("")) {
		// if (JOptionPane.showConfirmDialog(component,
		// "当运输方式不为非保税区或保税仓库或保税港区时候，运输工具不能为空。\n确定不能为空吗？",
		// "提示", 0) == 0) {
		// return false;
		// } else {
		// // return true;
		// }
		// }
		// }
		// }
		// 贸易方式为来/进料料件内销(0245,0644) 运输工具可为空
		//
		if ((customsDeclaration.getConveyance() == null || ""
				.equals(customsDeclaration.getConveyance()))
				&& ("0245".equals(customsDeclaration.getTradeMode().getCode()) || "0644"
						.equals(customsDeclaration.getTradeMode().getCode()))) {
		} else if ((customsDeclaration.getConveyance() == null// 下面情况运输工具可为空
				|| "".equals(customsDeclaration.getConveyance()))
				&& (customsDeclaration.getImpExpType().intValue() == ImpExpType.TRANSFER_FACTORY_IMPORT
						|| customsDeclaration.getTradeMode().getName()
								.indexOf("余料结转") > 0
						|| customsDeclaration.getTransferMode().getName()
								.equals("非保税区")
						|| customsDeclaration.getTransferMode().getName()
								.equals("保税仓库")
						|| customsDeclaration.getTransferMode().getName()
								.equals("保税港区")
						|| customsDeclaration.getTransferMode().getName()
								.equals("物流园区")
						|| customsDeclaration.getTransferMode().getName()
								.equals("物流中心") || customsDeclaration
						.getTransferMode().getName().equals("其它运输"))) {

		} else if ((customsDeclaration.getConveyance() == null// 贸易方式为来/进料深加工(0255,0654)
		|| "".equals(customsDeclaration.getConveyance()))
				&& customsDeclaration.getTradeMode() != null
				&& ("0255".equals(customsDeclaration.getTradeMode().getCode()) || "0654"
						.equals(customsDeclaration.getTradeMode().getCode()))) {
			if (JOptionPane.CANCEL_OPTION == JOptionPane.showConfirmDialog(
					component, "深加工结转，运输工具为空，是否继续！", "提示",
					JOptionPane.OK_CANCEL_OPTION)) {
				return false;
			}
		} else if (customsDeclaration.getConveyance() != null
				&& !"".equals(customsDeclaration.getConveyance())
				&& customsDeclaration.getConveyance().length() > 0) {
			String conveyance = customsDeclaration.getConveyance().substring(0,
					1);
			CompanyOther other = CommonVars.getOther();
			// 检查运输工具位数时加上一额外自定义位数，在系统设置-其他设置-其他设置-运输工具额外位数
			// 若为空默认为14
			int addDigit = other.getTransportTool() == null ? 14 : (other
					.getTransportTool() + 1);
			System.out.println("运输工具额外位数：" + addDigit);
			if (conveyance.equals("@")) {
				if (customsDeclaration.getConveyance().length() != 14
						&& customsDeclaration.getConveyance().length() != 17
						&& customsDeclaration.getConveyance().length() != addDigit) {
					JOptionPane.showMessageDialog(component,
							"司机纸号码应该为13位或者16位或用户自定义位", "提示",
							JOptionPane.YES_NO_OPTION);
					return false;
				}
			}
		} else {
				if(!customsDeclaration.getTransferMode().getName().trim().equals("监管仓库")){
				JOptionPane.showMessageDialog(component, "运输工具不能为空！", "提示",
						JOptionPane.YES_NO_OPTION);
				return false;
			}
		}
		// 判断当贸易方式为来料料件内销0245、进料料件内销0644、来料边角料内销0845
		// 报送海关前两位为‘52’时、码头为99其它业务、备注为[装卸口岸]:其它业务
		// BY 石小凯
		if (customsDeclaration.getImpExpFlag() != ImpExpFlag.SPECIAL) {
			if (null != customsDeclaration.getTradeMode()) {
				if ("0245".equals(customsDeclaration.getTradeMode().getCode())
						|| "0644".equals(customsDeclaration.getTradeMode()
								.getCode())
						|| "0845".equals(customsDeclaration.getTradeMode()
								.getCode())) {
					if (customsDeclaration.getDeclarationCustoms() != null) {
						if ("52".equals(customsDeclaration
								.getDeclarationCustoms().getCode()
								.substring(0, 2))) {
							if (customsDeclaration.getPredock() != null) {
								if (!customsDeclaration.getPredock().getName()
										.trim().equals("其它业务")) {
									JOptionPane.showMessageDialog(component,
											"装卸码头必须为‘【99】其它业务’", "提示",
											JOptionPane.YES_NO_OPTION);
									return false;
								}
							} else {
								JOptionPane.showMessageDialog(component,
										"装卸码头必须为‘【99】其它业务’", "提示",
										JOptionPane.YES_NO_OPTION);
								return false;
							}
						}
					}
				}
			}
		}
		// }
		/*
		 * if (customsDeclaration.getCustomer() == null) {
		 * JOptionPane.showMessageDialog(component, "客户名称不能为空", "提示",
		 * JOptionPane.YES_NO_OPTION); return false; }
		 */
		if (customsDeclaration.getDeclarationCustoms() == null) {
			JOptionPane.showMessageDialog(component, "报送海关不能为空", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}

		if ("52".equals(customsDeclaration.getDeclarationCustoms().getCode()
				.substring(0, 2))) {
			// 5220东莞物流，不检查
			if (!"5220".equals(customsDeclaration.getDeclarationCustoms()
					.getCode()))
				if (customsDeclaration.getPredock() == null) {
					JOptionPane.showMessageDialog(component, "装卸码头不能为空", "提示",
							JOptionPane.YES_NO_OPTION);
					return false;
				}
		}
		// 5220东莞物流，不检查
		if ("52".equals(customsDeclaration.getDeclarationCustoms().getCode()
				.substring(0, 2))
				&& !"5220".equals(customsDeclaration.getDeclarationCustoms()
						.getCode())) {
			// 2010-05-31hcl
			// 增加根据贸易方式，判断装卸码头
			// 来料深加工0255
			// 进料深加工0654
			// 进料余料结转0258
			// 来料余料结转0657
			// 企业申报转厂、结转等形式报关单，其货场代码为 "5297加贸业务"
			// 1.判断是否有“转自”字符
			// 2.判断备注栏的“转自” 与是否在”装卸口岸“前面
			//
			if (customsDeclaration.getTradeMode().getCode().equals("0255")
					|| customsDeclaration.getTradeMode().getCode()
							.equals("0654")
					|| customsDeclaration.getTradeMode().getCode()
							.equals("0258")
					|| customsDeclaration.getTradeMode().getCode()
							.equals("0657")) {
				if (!customsDeclaration.getPredock().getCode().equals("97")) {
					JOptionPane.showMessageDialog(component,
							"深加工结转报关单装卸口岸代码应为97", "提示",
							JOptionPane.YES_NO_OPTION);
					return false;
				}
				if (!customsDeclaration.getMemos().contains("转自")) {
					JOptionPane.showMessageDialog(component,
							"备注栏应在[装卸口岸]前填写转自+手册号+企业名称", "提示",
							JOptionPane.YES_NO_OPTION);
					return false;
				}

				if (customsDeclaration.getMemos().indexOf("装卸口岸") < customsDeclaration
						.getMemos().indexOf("转自")) {
					JOptionPane.showMessageDialog(component,
							"备注栏的[装卸口岸]xxxx应在“转自”之后", "提示",
							JOptionPane.YES_NO_OPTION);
					return false;
				}
			} else if (!customsDeclaration.getPredock().getCode().equals("98")
					&& !customsDeclaration.getPredock().getCode().equals("99")) {
				// 2010-06-01hcl
				// 根据“报送海关”判断，装卸口岸是否是属于该海关监管场所。
				// 如果不属于该海关监管场所，系统提示：装卸口岸对应的海关与报送海关不一致。"
				if (!customsDeclaration
						.getPredock()
						.getCusCode()
						.equals(customsDeclaration.getDeclarationCustoms()
								.getCode())) {
					JOptionPane.showMessageDialog(component, "装卸口岸关区（代码："
							+ customsDeclaration.getPredock().getCusCode()
							+ "） 与对应的海关与报送海关 （代码："
							+ customsDeclaration.getDeclarationCustoms()
									.getCode() + "） 不一致", "提示",
							JOptionPane.YES_NO_OPTION);
					return false;
				}
			}
			if (!customsDeclaration.getMemos().contains(
					"[装卸口岸]:" + customsDeclaration.getPredock().getShortName())) {
				JOptionPane.showMessageDialog(component, "备注栏应填写的[装卸口岸]简称不正确",
						"提示", JOptionPane.YES_NO_OPTION);
				return false;
			}
			if (customsDeclaration.getMemos().indexOf("[装卸口岸]") != customsDeclaration
					.getMemos().lastIndexOf("[装卸口岸]")) {
				JOptionPane.showMessageDialog(component, "备注栏的[装卸口岸]出现重复",
						"提示", JOptionPane.YES_NO_OPTION);
				return false;
			}
		}
		/**
		 * 2014-11-28因海关一体化改革，涉及公路舱单调整，当选择公路运输时，报送海关跟进出口岸可以不一致
		 */
//		if (!customsDeclaration.getCustoms().getName()
//				.equals(customsDeclaration.getDeclarationCustoms().getName())) {
//			if (customsDeclaration.getConveyance() != null
//					&& !customsDeclaration.getConveyance().equals("")
//					&& !customsDeclaration.getConveyance().substring(0, 1)
//							.equals("@")) {
//				JOptionPane.showMessageDialog(component,
//						"报送海关与进出口岸不同时，运输工具前应填@", "提示",
//						JOptionPane.YES_NO_OPTION);
//				return false;
//			}
//		}

		String transac = customsDeclaration.getTransac().getName().trim();
		if (transac.equals("CIF")) {
			if (customsDeclaration.getInsuranceType() != null
					|| (customsDeclaration.getInsurance() != null && customsDeclaration
							.getInsurance().doubleValue() != 0)
					|| customsDeclaration.getFreightageType() != null
					|| (customsDeclaration.getFreightage() != null && customsDeclaration
							.getFreightage().doubleValue() != 0)) {
				JOptionPane.showMessageDialog(component, "成交方式为CIF时，保费和运费必须为空",
						"提示", JOptionPane.YES_NO_OPTION);
				return false;
			}
		}
		if (transac.equals("C&F")) {
			// if (customsDeclaration.getInsuranceType() == null
			// || customsDeclaration.getInsurance() == null
			// || customsDeclaration.getInsurance().doubleValue() == 0
			// || customsDeclaration.getFreightageType() != null
			// || customsDeclaration.getFeeCurr() != null
			// || (customsDeclaration.getFreightage() != null &&
			// customsDeclaration
			// .getFreightage().doubleValue() != 0)) {
			// JOptionPane.showMessageDialog(component,
			// "成交方式为C&F时，保费不能为空，运费必须为空", "提示",
			// JOptionPane.YES_NO_OPTION);
			// return false;
			// }

			if(isMaterial1(customsDeclaration.getImpExpType())){
				if ((customsDeclaration.getInsuranceType() == null
						|| customsDeclaration.getInsurance() == null 
						|| customsDeclaration.getInsurance().doubleValue() == 0
						|| customsDeclaration.getInsurCurr() == null)) {
					JOptionPane.showMessageDialog(component, "成交方式为C&F时，保费不能为空",
							"提示", JOptionPane.YES_NO_OPTION);
					return false;
				}
				
				if (customsDeclaration.getFreightageType() != null
						|| (customsDeclaration.getFreightage() != null 
						&& customsDeclaration.getFreightage().doubleValue()!= 0)
						|| customsDeclaration.getFeeCurr() != null) {
					JOptionPane.showMessageDialog(component, "成交方式为C&F时,运费必须为空",
							"提示", JOptionPane.YES_NO_OPTION);
					return false;
				}
			}else{
				if ((customsDeclaration.getInsuranceType() != null
						|| (customsDeclaration.getInsurance() != null 
						&& customsDeclaration.getInsurance().doubleValue() != 0) 
						|| customsDeclaration
						.getInsurCurr() != null)) {
					JOptionPane.showMessageDialog(component, "成交方式为C&F时，保费必须为空",
							"提示", JOptionPane.YES_NO_OPTION);
					return false;
				}
				
				if (customsDeclaration.getFreightageType() == null
						|| (customsDeclaration.getFreightage() == null 
						|| customsDeclaration.getFreightage().doubleValue() == 0)
						|| customsDeclaration.getFeeCurr() == null) {
					JOptionPane.showMessageDialog(component, "成交方式为C&F时,运费不能为空",
							"提示", JOptionPane.YES_NO_OPTION);
					return false;
				}
			}
			
		}
		if (transac.equals("FOB")) {
			if (customsDeclaration.getInsuranceType() == null
					|| customsDeclaration.getInsurance() == null
					|| customsDeclaration.getInsurance().doubleValue() == 0
					|| customsDeclaration.getFreightageType() == null
					|| customsDeclaration.getFreightage() == null
					|| customsDeclaration.getFreightage().doubleValue() == 0) {
				JOptionPane.showMessageDialog(component, "成交方式为FOB时，保费和运费不能为空",
						"提示", JOptionPane.YES_NO_OPTION);
				return false;
			}
		}
		if (transac.equals("C&I")) {
			if (customsDeclaration.getInsuranceType() != null
					|| (customsDeclaration.getInsurance() != null && customsDeclaration
							.getInsurance().doubleValue() != 0)
					|| customsDeclaration.getFreightageType() == null
					|| customsDeclaration.getFreightage() == null
					|| customsDeclaration.getFreightage().doubleValue() == 0) {
				JOptionPane.showMessageDialog(component,
						"成交方式为C&I时，保费必须为空，运费不能为空", "提示",
						JOptionPane.YES_NO_OPTION);
				return false;
			}
		}

		// 判断进出口类型与贸易方式对应
		if (!isCorresponding(component, customsDeclaration.getImpExpType()
				.intValue(), customsDeclaration.getTradeMode().getName(),
				customsDeclaration)) {
			return false;
		}
		if (customsDeclaration.getImpExpType().intValue() == ImpExpType.TRANSFER_FACTORY_IMPORT
				&& (customsDeclaration.getTradeMode().getName().indexOf("余料结转") > 0)) {
			JOptionPane
					.showMessageDialog(component, "贸易方式是余料结转,那么进口类型应为料件进口!!",
							"提示", JOptionPane.YES_NO_OPTION);
			return false;

		}

		if (customsDeclaration.getImpExpType().intValue() == ImpExpType.TRANSFER_FACTORY_IMPORT) {
			if (!"中国".equals(customsDeclaration
					.getCountryOfLoadingOrUnloading().getName().trim())) {
				JOptionPane.showMessageDialog(component,
						"无实际进出境的,起运国应为“中国境内”，代码“0142”", "提示",
						JOptionPane.YES_NO_OPTION);
				return false;
			}
			if (!"中国境内".equals(customsDeclaration.getPortOfLoadingOrUnloading()
					.getName().trim())) {
				JOptionPane.showMessageDialog(component,
						"如无实际进出境，装货港应填写“中国境内”，代码“0142”", "提示",
						JOptionPane.YES_NO_OPTION);
				return false;
			}
			if (!transac.equals("CIF")) {
				JOptionPane.showMessageDialog(component, "如无实际进出境，进口成交方式应为CIF",
						"提示", JOptionPane.YES_NO_OPTION);
				return false;
			}
		}

		if (customsDeclaration.getTransferMode().getCode().trim().equals("2")) {
			if (customsDeclaration.getBillOfLading() == null
					|| "".equals(customsDeclaration.getBillOfLading())) {
				JOptionPane.showMessageDialog(component, "运输方式为江海运输时,提运单号不能为空",
						"提示", JOptionPane.YES_NO_OPTION);
				return false;
			}
		}
		if (customsDeclaration.getTransferMode().getCode().trim().equals("3")) {
			if (customsDeclaration.getBillOfLading() == null
					|| "".equals(customsDeclaration.getBillOfLading())) {
				JOptionPane.showMessageDialog(component, "运输方式为铁路运输时,提运单号不能为空",
						"提示", JOptionPane.YES_NO_OPTION);
				return false;
			}
		}
		if (customsDeclaration.getTransferMode().getCode().trim().equals("5")) {
			if (customsDeclaration.getBillOfLading() == null
					|| "".equals(customsDeclaration.getBillOfLading())) {
				JOptionPane.showMessageDialog(component, "运输方式为航空运输时,提运单号不能为空",
						"提示", JOptionPane.YES_NO_OPTION);
				return false;
			}
		}
		if (customsDeclaration.getTransferMode().getCode().trim().equals("6")) {
			if (customsDeclaration.getBillOfLading() == null
					|| "".equals(customsDeclaration.getBillOfLading())) {
				JOptionPane.showMessageDialog(component, "运输方式为邮件运输时,提运单号不能为空",
						"提示", JOptionPane.YES_NO_OPTION);
				return false;
			}
		}

		// double exchangeRate = customsDeclaration.getExchangeRate() == null ?
		// 0
		// : Double.parseDouble(customsDeclaration.getExchangeRate()
		// .toString());
		// if (exchangeRate <= 0) {
		// JOptionPane.showMessageDialog(component, "汇率不能小于或等于零", "提示",
		// JOptionPane.YES_NO_OPTION);
		// return false;
		// }
		if (customsDeclaration.getAttachedBill() != null
				&& !customsDeclaration.getAttachedBill().trim().equals("")) {
			String memos = customsDeclaration.getCertificateCode();
			if (memos == null || memos.trim().equals("")) {
				JOptionPane.showMessageDialog(component, "在备注中未发现所有监管证件的编号",
						"提示", JOptionPane.YES_NO_OPTION);
				return false;
			}
			char[] attachedBills = customsDeclaration.getAttachedBill()
					.toCharArray();
			for (int i = 0; i < attachedBills.length; i++) {
				if (memos.indexOf(String.valueOf(attachedBills[i]) + ":") < 0
						&& memos.indexOf(String.valueOf(attachedBills[i]) + "*") < 0) {
					JOptionPane.showMessageDialog(
							component,
							"在备注中未发现监管证件的编号 : "
									+ String.valueOf(attachedBills[i]), "提示",
							JOptionPane.YES_NO_OPTION);
					return false;
				}
			}
		} else {
			String memos = customsDeclaration.getCertificateCode();
			if (memos != null && !memos.trim().equals("")) {
				JOptionPane.showMessageDialog(component,
						"在备注中有证件编码，但随附单据没有选中证件编码!!", "提示",
						JOptionPane.YES_NO_OPTION);
				return false;
			}
		}
		if (customsDeclaration.getConveyance().length() > 0
				&& CommonVars.getIsVailCustomsOther() != null
				&& CommonVars.getIsVailCustomsOther()) {
			String conveyance = customsDeclaration.getConveyance().substring(0,
					1);
			if (conveyance.equals("@")) {
				if (customsDeclaration.getDomesticTransferMode() == null// 境内运输方式
						|| customsDeclaration.getDomesticConveyanceCode()// 境内运输工具编号
								.equals("")
						|| customsDeclaration.getDomesticConveyanceName()// 境内运输工具名字
								.equals("")) {
					JOptionPane.showMessageDialog(component, "转关运输时必须填写转关附加内容",
							"提示", JOptionPane.YES_NO_OPTION);
					return false;
				}
				// //判断转关运输的运输方式是否与报关单表头的运输方式一致! add 2009.4.1
				// if (customsDeclaration.getDomesticTransferMode()!=null
				// &&
				// !customsDeclaration.getDomesticTransferMode().equals(customsDeclaration.getTransferMode()))
				// {
				// JOptionPane.showMessageDialog(component,
				// "转关运输时运输方式必须报关单表头运输方式一致",
				// "提示", JOptionPane.YES_NO_OPTION);
				// return false;
				// }
			}

		}

		/*
		 * if (customsDeclaration.getAuthorizeFile() == null ||
		 * "".equals(customsDeclaration.getAuthorizeFile())) {
		 * JOptionPane.showMessageDialog(component, "外汇销销单号不可为空", "提示",
		 * JOptionPane.YES_NO_OPTION); return false; } else { BaseEncAction
		 * baseEncAction = (EncAction) CommonVars
		 * .getApplicationContext().getBean("encAction"); List list =
		 * baseEncAction.findExistAuthorizeFile(new Request(
		 * CommonVars.getCurrUser(), true), customsDeclaration
		 * .getAuthorizeFile(),customsDeclaration.getId()); if(list.size() > 0
		 * ){ BaseCustomsDeclaration base = (BaseCustomsDeclaration)list.get(0);
		 * String impExpFlagStr = ""; if(base.getImpExpFlag() ==
		 * ImpExpFlag.EXPORT){ impExpFlagStr = "出口"; }else
		 * if(base.getImpExpFlag() == ImpExpFlag.IMPORT){ impExpFlagStr = "进口";
		 * }else if(base.getImpExpFlag() == ImpExpFlag.SPECIAL){ impExpFlagStr =
		 * "特殊"; } String info ="第 "+ base.getSerialNumber()+" 份"+impExpFlagStr;
		 * JOptionPane.showMessageDialog(component, "外汇销销单号已被"+info+"报关单使用",
		 * "提示", JOptionPane.YES_NO_OPTION); return false; } }
		 */
		return true;
	}

	// ljl提供 edit by xxm 2006-1-12
	/*
	 * 料件进口：进料对口、来料加工、进料余料结转、来料余料结转、来料料件退换、进料料件退换 、保税区仓储转口、保税间货物、保税仓库货物
	 * 料件转厂：来料深加工、进料深加工 退厂返工：来料成品退换、进料成品退换 成品出口：进料对口、来料加工 转厂出口：来料深加工、进料深加工
	 * 退料出口：来料料件退换、来料料件复出、进料料件退换、进料料件复出 返工复出：来料成品退换、进料成品退换 余料结转：进料余料结转、来料余料结转
	 */

	private static boolean isCorresponding(Component component, int impExpType,
			String tradeModeName, BaseCustomsDeclaration customsDeclaration) {
		if (impExpType == ImpExpType.DIRECT_IMPORT) {// 料件进口 lm
			// 新加（保税区仓储转口，保税间货物，保税仓库货物、国轮油物料）四种类型
			// by 2009-12-10
			if (!tradeModeName.equals("进料对口") && !tradeModeName.equals("来料加工")
					&& !tradeModeName.equals("进料余料结转")
					&& !tradeModeName.equals("来料余料结转")
					&& !tradeModeName.equals("来料料件退换")
					&& !tradeModeName.equals("进料料件退换")
					&& !tradeModeName.equals("保税区仓储转口")
					&& !tradeModeName.equals("保税间货物")
					&& !tradeModeName.equals("保税仓库货物")
					&& !tradeModeName.equals("国轮油物料")) {
				JOptionPane
						.showMessageDialog(
								component,
								"进出口类型为料件进口：贸易方式应该为：进料对口、来料加工、进料余料结转、来料余料结转、来料料件退换、进料料件退换、保税区仓储转口、保税间货物、保税仓库货物、国轮油物料",
								"提示", JOptionPane.YES_NO_OPTION);
				return false;
			}
		} else if (impExpType == ImpExpType.TRANSFER_FACTORY_IMPORT) {// 料件转厂
			/*
			 * if (customsDeclaration instanceof BcsCustomsDeclaration) {// bcs
			 * // if (!tradeModeName.equals("来料深加工")) {
			 * JOptionPane.showMessageDialog(component,
			 * "进出口类型为料件转厂：贸易方式应该为：来料深加工", "提示", JOptionPane.YES_NO_OPTION);
			 * return false; } } else
			 */if (!tradeModeName.equals("来料深加工")
					&& !tradeModeName.equals("进料深加工")) {
				JOptionPane.showMessageDialog(component,
						"进出口类型为料件转厂：贸易方式应该为：来料深加工、进料深加工", "提示",
						JOptionPane.YES_NO_OPTION);
				return false;
			}
		} else if (impExpType == ImpExpType.BACK_FACTORY_REWORK) {// 退厂返工
			if (!tradeModeName.equals("来料成品退换")
					&& !tradeModeName.equals("进料成品退换")) {
				JOptionPane.showMessageDialog(component,
						"进出口类型为退厂返工：贸易方式应该为：来料成品退换、进料成品退换", "提示",
						JOptionPane.YES_NO_OPTION);
				return false;
			}
		} else if (impExpType == ImpExpType.DIRECT_EXPORT) {// 成品出口
			if (!tradeModeName.equals("进料对口") && !tradeModeName.equals("来料加工")
					&& !tradeModeName.equals("保税区仓储转口")
					&& !tradeModeName.equals("保税间货物")
					&& !tradeModeName.equals("保税仓库货物")
					&& !tradeModeName.equals("国轮油物料")) {
				JOptionPane
						.showMessageDialog(
								component,
								"进出口类型为成品出口：贸易方式应该为：进料对口、来料加工、保税区仓储转口、保税间货物、保税仓库货物、国轮油物料",
								"提示", JOptionPane.YES_NO_OPTION);
				return false;
			}
		} else if (impExpType == ImpExpType.TRANSFER_FACTORY_EXPORT) {// 转厂出口
			/*
			 * if (customsDeclaration instanceof BcsCustomsDeclaration) {// bcs
			 * if (!tradeModeName.equals("来料深加工")) {
			 * JOptionPane.showMessageDialog(component,
			 * "进出口类型为料件转厂：贸易方式应该为：来料深加工", "提示", JOptionPane.YES_NO_OPTION);
			 * return false; } } else
			 */if (!tradeModeName.equals("来料深加工")
					&& !tradeModeName.equals("进料深加工")) {
				JOptionPane.showMessageDialog(component,
						"进出口类型为转厂出口：贸易方式应该为：来料深加工、进料深加工", "提示",
						JOptionPane.YES_NO_OPTION);
				return false;
			}
		} else if (impExpType == ImpExpType.BACK_MATERIEL_EXPORT) {// 退料出口 lm
			// 新加（保税区仓储转口，保税间货物，保税仓库货物,国轮油物料）四种类型
			// by
			// 2009-12-10
			if (!tradeModeName.equals("来料料件退换")
					&& !tradeModeName.equals("来料料件复出")
					&& !tradeModeName.equals("进料料件退换")
					&& !tradeModeName.equals("进料料件复出")
					&& !tradeModeName.equals("保税区仓储转口")
					&& !tradeModeName.equals("保税间货物")
					&& !tradeModeName.equals("保税仓库货物")
					&& !tradeModeName.equals("国轮油物料")) {
				JOptionPane
						.showMessageDialog(
								component,
								"进出口类型为退料出口：贸易方式应该为：来料料件退换、来料料件复出、进料料件退换、进料料件复出、保税区仓储转口、保税间货物、保税仓库货物、国轮油物料",
								"提示", JOptionPane.YES_NO_OPTION);
				return false;
			}
		} else if (impExpType == ImpExpType.REWORK_EXPORT) {// 返工复出
			if (!tradeModeName.equals("来料成品退换")
					&& !tradeModeName.equals("进料成品退换")
					&& !tradeModeName.equals("保税区仓储转口")
					&& !tradeModeName.equals("保税间货物")
					&& !tradeModeName.equals("保税仓库货物")
					&& !tradeModeName.equals("国轮油物料")) {
				JOptionPane
						.showMessageDialog(
								component,
								"进出口类型为返工复出：贸易方式应该为：来料成品退换、进料成品退换、保税区仓储转口、保税间货物、保税仓库货物、国轮油物料",
								"提示", JOptionPane.YES_NO_OPTION);
				return false;
			}
		} else if (impExpType == ImpExpType.REMAIN_FORWARD_EXPORT) {// 余料结转
			if (!tradeModeName.equals("进料余料结转")
					&& !tradeModeName.equals("来料余料结转")) {
				JOptionPane.showMessageDialog(component,
						"进出口类型为余料结转：贸易方式应该为：进料余料结转、来料余料结转", "提示",
						JOptionPane.YES_NO_OPTION);
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查出口报关单数据
	 * 
	 * @return
	 */
	public static boolean checkExportCustomsDeclarationData(
			BaseCustomsDeclaration customsDeclaration, Component component) {
		if (customsDeclaration == null) {
			return false;
		}
		ContractAction contractAction = (ContractAction) CommonVars
				.getApplicationContext().getBean("contractAction");

		if (customsDeclaration.getCurrency() == null) {
			JOptionPane.showMessageDialog(component, "币制不能为空", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}

		if (customsDeclaration.getDeclarationDate() == null) {
			JOptionPane.showMessageDialog(component, "申报日期不能为空", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		String curr = customsDeclaration.getCurrency().getCurrSymb().trim();
		// 申报日期
		Date date = customsDeclaration.getDeclarationDate();
		Calendar gc = Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DAY_OF_MONTH, 1);
		date = gc.getTime();
		if (null != customsDeclaration.getInsurCurr()
				&& !"".equals(customsDeclaration.getInsurCurr())) {
			if (!customsDeclaration
					.getInsurCurr()
					.getCurrSymb()
					.trim()
					.equals(customsDeclaration.getCurrency().getCurrSymb()
							.trim())) {
				String insuranceCurr = customsDeclaration.getInsurCurr()
						.getCurrSymb().trim();
				List list1 = contractAction.findCurrRateByDeclarationDate(
						new Request(CommonVars.getCurrUser()), curr,
						insuranceCurr, date);
				if (null == list1 || list1.size() < 1) {
					JOptionPane.showMessageDialog(component, "本币对保费币制汇率不存在！",
							"提示", JOptionPane.YES_NO_OPTION);
					return false;
				}
			}
		}
		if (null != customsDeclaration.getFeeCurr()
				&& !"".equals(customsDeclaration.getFeeCurr())) {
			if (!customsDeclaration
					.getFeeCurr()
					.getCurrSymb()
					.trim()
					.equals(customsDeclaration.getCurrency().getCurrSymb()
							.trim())) {
				String freightageCurr = customsDeclaration.getFeeCurr()
						.getCurrSymb().trim();
				List list2 = contractAction.findCurrRateByDeclarationDate(
						new Request(CommonVars.getCurrUser()), curr,
						freightageCurr, date);
				if (null == list2 || list2.size() < 1) {
					JOptionPane.showMessageDialog(component, "本币对运费币制汇率不存在！",
							"提示", JOptionPane.YES_NO_OPTION);
					return false;
				}
			}
		}
		// 判断当贸易方式为来料料件内销0245、进料料件内销0644、来料边角料内销0845
		// 报送海关前两位为‘52’时、码头为99其它业务、备注为[装卸口岸]:其它业务
		// BY 石小凯
		if (customsDeclaration.getImpExpFlag() != ImpExpFlag.SPECIAL) {
			if (null != customsDeclaration.getTradeMode()) {
				if ("0245".equals(customsDeclaration.getTradeMode().getCode())
						|| "0644".equals(customsDeclaration.getTradeMode()
								.getCode())
						|| "0845".equals(customsDeclaration.getTradeMode()
								.getCode())) {
					if ("52".equals(customsDeclaration.getDeclarationCustoms()
							.getCode().substring(0, 2))) {
						if (customsDeclaration.getPredock() != null) {
							if (!customsDeclaration.getPredock().getName()
									.trim().equals("其它业务")) {
								JOptionPane.showMessageDialog(component,
										"装卸码头必须为‘【99】其它业务’", "提示",
										JOptionPane.YES_NO_OPTION);
								return false;
							}
						} else {
							JOptionPane.showMessageDialog(component,
									"装卸码头必须为‘【99】其它业务’", "提示",
									JOptionPane.YES_NO_OPTION);
							return false;
						}
					}
				}
			}
		}

		if (customsDeclaration.getImpExpType() == null) {
			JOptionPane.showMessageDialog(component, "出口类型不能为空", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		if (customsDeclaration.getCustoms() == null) {
			JOptionPane.showMessageDialog(component, "出口口岸不能为空", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		if (customsDeclaration.getImpExpDate() == null) {
			JOptionPane.showMessageDialog(component, "出口日期不能为空", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}

		if (customsDeclaration.getTradeName() == null
				|| customsDeclaration.getTradeName().equals("")) {
			JOptionPane.showMessageDialog(component, "经营单位不能为空", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		if (customsDeclaration.getTransferMode() == null) {
			JOptionPane.showMessageDialog(component, "运输方式不能为空", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		if (customsDeclaration.getDomesticDestinationOrSource() == null) {
			JOptionPane.showMessageDialog(null, "境内货源地不能为空", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		if (customsDeclaration.getMachName() == null
				|| customsDeclaration.getMachName().equals("")) {
			JOptionPane.showMessageDialog(component, "发货单位不能为空", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		if (customsDeclaration.getTradeMode() == null) {
			JOptionPane.showMessageDialog(component, "贸易方式不能为空", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}

		if (null != customsDeclaration.getCustoms()) {
			if ("皇岗海关".equals(customsDeclaration.getCustoms().getName())
					|| "文锦渡关".equals(customsDeclaration.getCustoms().getName())) {
				if (!"公路运输".equals(customsDeclaration.getTransferMode()
						.getName())) {
					JOptionPane.showMessageDialog(component, "运输方式错误，应为公路运输！",
							"提示", JOptionPane.YES_NO_OPTION);
					return false;
				}
			}
		}
		// 征免性质
		String tradeCode = customsDeclaration.getTradeMode().getCode();
		if (!"1234".equals(tradeCode)
				&& !"1200".equals(tradeCode)
				&& !"1233".equals(tradeCode)
				&& !"1239".equals(tradeCode)
				// 转厂报关单可为空 edit by cjb 2009.11.03 2009110202
				&& !customsDeclaration.getImpExpType().equals(
						ImpExpType.TRANSFER_FACTORY_EXPORT)
				// 返工复出报关单可为空 edit by cjb
				&& !customsDeclaration.getImpExpType().equals(
						ImpExpType.REWORK_EXPORT)
				&& !customsDeclaration.getImpExpType().equals(
						ImpExpType.BACK_MATERIEL_EXPORT)
				&& !customsDeclaration.getImpExpType().equals(
						ImpExpType.REMAIN_FORWARD_EXPORT)) {
			if (customsDeclaration.getLevyKind() == null) {
				JOptionPane.showMessageDialog(component, "征免性质不能为空", "提示",
						JOptionPane.YES_NO_OPTION);
				return false;
			}
		}

		if (customsDeclaration.getCountryOfLoadingOrUnloading() == null) {
			JOptionPane.showMessageDialog(component, "抵运国不能为空", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		if (customsDeclaration.getPortOfLoadingOrUnloading() == null) {
			JOptionPane.showMessageDialog(component,
					"指运港不能为空，如无实际进出境，填写“中国境内”，代码“0142”", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		// edit by xxm 熟悉业务后做
		/*
		 * if (customsDeclaration.getAuthorizeFile() == null ||
		 * customsDeclaration.getAuthorizeFile().length() <9){
		 * JOptionPane.showMessageDialog(component, "抵运国不能为空", "提示",
		 * JOptionPane.YES_NO_OPTION); return false; }
		 */

		if (customsDeclaration.getTransac() == null) {
			JOptionPane.showMessageDialog(component, "成交方式不能为空", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		int pieceNum = customsDeclaration.getCommodityNum() == null ? 0
				: customsDeclaration.getCommodityNum().intValue();
		if (pieceNum <= 0) {
			JOptionPane.showMessageDialog(component, "件数不能小于1", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		double grossWeight = customsDeclaration.getGrossWeight() == null ? 0
				: Double.parseDouble(customsDeclaration.getGrossWeight()
						.toString());
		if (grossWeight <= 0) {
			JOptionPane.showMessageDialog(component, "毛重不能小于或等于零", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		// if (grossWeight < 1) {
		// JOptionPane.showMessageDialog(component, "毛重不能小于1", "提示",
		// JOptionPane.YES_NO_OPTION);
		// return false;
		// }
		double netWeight = customsDeclaration.getNetWeight() == null ? 0
				: Double.parseDouble(customsDeclaration.getNetWeight()
						.toString());
		if (netWeight <= 0) {
			JOptionPane.showMessageDialog(component, "净重不能小于或等于零", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		/*
		 * hwy 2012-8-22
		 */
		if (netWeight < 1) {
			JOptionPane.showMessageDialog(component, "温馨提示：净重小于1并通过检查", "提示",
					JOptionPane.YES_NO_OPTION);
		}

		if (netWeight > grossWeight) {
			JOptionPane.showMessageDialog(component, "净重不能大于毛重", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}

		if (customsDeclaration.getDeclarationCompany() == null) {
			JOptionPane.showMessageDialog(component, "申报单位不可为空!!!", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		if (customsDeclaration.getWrapType() == null) {
			JOptionPane.showMessageDialog(component, "包装种类不能为空", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		// if (customsDeclaration.getUses() == null) {
		// JOptionPane.showMessageDialog(component, "用途不能为空", "提示",
		// JOptionPane.YES_NO_OPTION);
		// return false;
		// }
		if (customsDeclaration.getContainerNum() == null
				|| customsDeclaration.getContainerNum().trim().equals("")) {
			if (customsDeclaration.getTransferMode().getName().equals("物流园区")
					|| customsDeclaration.getTransferMode().getName()
							.equals("物流中心")
					|| customsDeclaration.getTransferMode().getName()
							.equals("其它运输")) {
			} else {
				JOptionPane.showMessageDialog(component, "集装箱号不能为空", "提示",
						JOptionPane.YES_NO_OPTION);
				return false;
			}
		}

		if (customsDeclaration.getCustomser() == null
				|| customsDeclaration.getCustomser().trim().equals("")) {
			JOptionPane.showMessageDialog(component, "报关员不能为空!!", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		if (customsDeclaration.getTelephone() == null
				|| customsDeclaration.getTelephone().trim().equals("")) {
			JOptionPane.showMessageDialog(component, "联系电话不能为空!!", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		// if (customsDeclaration.getImpExpType().intValue() !=
		// ImpExpType.TRANSFER_FACTORY_EXPORT
		// && customsDeclaration.getImpExpType().intValue() !=
		// ImpExpType.REMAIN_FORWARD_EXPORT) {
		// // edit by xxm 2005-9-28
		// if (!customsDeclaration.getTransferMode().getName().equals("非保税区")
		// && !customsDeclaration.getTransferMode().getName().equals(
		// "保税仓库")
		// && !customsDeclaration.getTransferMode().getName().equals(
		// "保税港区")
		// && !customsDeclaration.getTransferMode().getName().equals(
		// "监管仓库")) {
		// if (customsDeclaration.getConveyance() == null
		// || customsDeclaration.getConveyance().trim().equals("")) {
		// JOptionPane.showMessageDialog(component, "运输工具不能为空", "提示",
		// JOptionPane.YES_NO_OPTION);
		// return false;
		// }
		// }
		// }
		if ((customsDeclaration.getConveyance() == null// 下面情况运输工具可为空
				|| "".equals(customsDeclaration.getConveyance()))
				&& (customsDeclaration.getImpExpType().intValue() == ImpExpType.TRANSFER_FACTORY_IMPORT
						|| customsDeclaration.getTradeMode().getName()
								.indexOf("余料结转") > 0
						|| customsDeclaration.getTransferMode().getName()
								.equals("非保税区")
						|| customsDeclaration.getTransferMode().getName()
								.equals("保税仓库")
						|| customsDeclaration.getTransferMode().getName()
								.equals("保税港区")
						|| customsDeclaration.getTransferMode().getName()
								.equals("物流园区")
						|| customsDeclaration.getTransferMode().getName()
								.equals("物流中心")
						|| customsDeclaration.getTransferMode().getName()
								.equals("其它运输") || customsDeclaration
						.getTransferMode().getName().equals("出口加工"))) {

		} else if (customsDeclaration.getConveyance() != null
				&& !"".equals(customsDeclaration.getConveyance())
				&& customsDeclaration.getConveyance().length() > 0) {
			String conveyance = customsDeclaration.getConveyance().substring(0,
					1);
			CompanyOther other = CommonVars.getOther();
			// 检查运输工具位数时加上一额外自定义位数，在系统设置-其他设置-其他设置-运输工具额外位数
			// 若为空默认为14
			int addDigit = other.getTransportTool() == null ? 14 : (other
					.getTransportTool() + 1);
			if (conveyance.equals("@")) {
				if (customsDeclaration.getConveyance().length() != 14
						&& customsDeclaration.getConveyance().length() != 17
						&& customsDeclaration.getConveyance().length() != addDigit) {
					JOptionPane.showMessageDialog(component,
							"司机纸号码应该为13位或者16位或用户自定义位", "提示",
							JOptionPane.YES_NO_OPTION);
					return false;
				}
			}
		} else {
			if(!customsDeclaration.getTransferMode().getName().trim().equals("监管仓库")){
			JOptionPane.showMessageDialog(component, "运输工具不能为空！", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
			}
		}
		// Edit by xxm 2006-1-3
		/*
		 * if (customsDeclaration.getCustomer() == null) {
		 * JOptionPane.showMessageDialog(component, "客户名称不能为空", "提示",
		 * JOptionPane.YES_NO_OPTION); return false; }
		 */
		if (customsDeclaration.getDeclarationCustoms() == null) {
			JOptionPane.showMessageDialog(component, "报送海关不能为空", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		if ("52".equals(customsDeclaration.getDeclarationCustoms().getCode()
				.substring(0, 2))) {
			// 5220东莞物流，不检查
			if (!"5220".equals(customsDeclaration.getDeclarationCustoms()))
				if (customsDeclaration.getPredock() == null) {
					JOptionPane.showMessageDialog(component, "装卸码头不能为空", "提示",
							JOptionPane.YES_NO_OPTION);
					return false;
				}
		}
		// 5220东莞物流，不检查
		if ("52".equals(customsDeclaration.getDeclarationCustoms().getCode()
				.substring(0, 2))
				&& !"5220".equals(customsDeclaration.getDeclarationCustoms()
						.getCode())) {
			// 2010-05-31hcl
			// 增加根据贸易方式，判断装卸码头
			// 来料深加工0255
			// 进料深加工0654
			// 进料余料结转0258
			// 来料余料结转0657
			// 企业申报转厂、结转等形式报关单，其货场代码为 "5297加贸业务"

			// 2010-06-03hcl
			// 如果为转厂贸易
			// 1.判断是否有“转至”字符
			// 2.判断备注栏的“转至” 与是否在”装卸口岸“前面
			//
			if (customsDeclaration.getTradeMode().getCode().equals("0255")
					|| customsDeclaration.getTradeMode().getCode()
							.equals("0654")
					|| customsDeclaration.getTradeMode().getCode()
							.equals("0258")
					|| customsDeclaration.getTradeMode().getCode()
							.equals("0657")) {
				if (!customsDeclaration.getPredock().getCode().equals("97")) {
					JOptionPane.showMessageDialog(component,
							"深加工结转报关单装卸口岸代码应为97", "提示",
							JOptionPane.YES_NO_OPTION);
					return false;
				}
				if (!customsDeclaration.getMemos().contains("转至")) {
					JOptionPane.showMessageDialog(component,
							"备注栏应在[装卸口岸]前填写转至+手册号", "提示",
							JOptionPane.YES_NO_OPTION);
					return false;
				}

				if (customsDeclaration.getMemos().indexOf("装卸口岸") < customsDeclaration
						.getMemos().indexOf("转至")) {
					JOptionPane.showMessageDialog(component,
							"备注栏的[装卸口岸]xxxx应在“转至”之后", "提示",
							JOptionPane.YES_NO_OPTION);
					return false;
				}
			} else if (!customsDeclaration.getPredock().getCode().equals("98")
					&& !customsDeclaration.getPredock().getCode().equals("99")) {
				// 2010-06-01hcl
				// 根据“报送海关”判断，装卸口岸是否是属于该海关监管场所。
				// 如果不属于该海关监管场所，系统提示：装卸口岸对应的海关与报送海关不一致。"
				if (!customsDeclaration
						.getPredock()
						.getCusCode()
						.equals(customsDeclaration.getDeclarationCustoms()
								.getCode())) {
					JOptionPane.showMessageDialog(component, "装卸口岸关区（代码："
							+ customsDeclaration.getPredock().getCusCode()
							+ "） 与对应的海关与报送海关 （代码："
							+ customsDeclaration.getDeclarationCustoms()
									.getCode() + "） 不一致", "提示",
							JOptionPane.YES_NO_OPTION);
					return false;
				}
			}
			// else {
			// // 2010-06-01hcl
			// // 根据“报送海关”判断，装卸口岸是否是属于该海关监管场所。
			// // 如果不属于该海关监管场所，系统提示：装卸口岸对应的海关与报送海关不一致。"
			// if (!customsDeclaration.getPredock().getCusCode().equals(
			// customsDeclaration.getDeclarationCustoms().getCode())) {
			// JOptionPane.showMessageDialog(component, "装卸口岸关区（代码："
			// + customsDeclaration.getPredock().getCusCode()
			// + "） 与对应的海关与报送海关 （代码："
			// + customsDeclaration.getDeclarationCustoms()
			// .getCode() + "） 不一致", "提示",
			// JOptionPane.YES_NO_OPTION);
			// return false;
			// }
			// }
			if (!customsDeclaration.getMemos().contains(
					"[装卸口岸]:" + customsDeclaration.getPredock().getShortName())) {
				JOptionPane.showMessageDialog(component, "备注栏应填写的[装卸口岸]简称不正确",
						"提示", JOptionPane.YES_NO_OPTION);
				return false;
			}
			System.out.println("customsDeclaration.getMemos().indexOf()+"
					+ customsDeclaration.getMemos().indexOf("[装卸口岸]"));
			System.out.println("customsDeclaration.getMemos().lastIndexOf()+"
					+ customsDeclaration.getMemos().lastIndexOf("[装卸口岸]"));
			if (customsDeclaration.getMemos().indexOf("[装卸口岸]") != customsDeclaration
					.getMemos().lastIndexOf("[装卸口岸]")) {
				JOptionPane.showMessageDialog(component, "备注栏的[装卸口岸]出现重复",
						"提示", JOptionPane.YES_NO_OPTION);
				return false;
			}
		}

		if (customsDeclaration.getTradeMode().getCode().equals("0255")
				|| customsDeclaration.getTradeMode().getCode().equals("0654")) {

		}

		// 贸易方式为来/进料深加工(0255,0654) edit by cjb 2009.5.19
		if (customsDeclaration.getTradeMode() != null
				&& ("0255".equals(customsDeclaration.getTradeMode().getCode()) || "0654"
						.equals(customsDeclaration.getTradeMode().getCode()))) {
			if (customsDeclaration.getConveyance() == null
					|| "".equals(customsDeclaration.getConveyance())) {// 运输工具为空时
				if (JOptionPane.CANCEL_OPTION == JOptionPane.showConfirmDialog(
						component, "深加工结转，运输工具为空，是否继续！", "提示",
						JOptionPane.OK_CANCEL_OPTION)) {
					return false;
				}
			}
		} else if (!customsDeclaration.getCustoms().getName()
				.equals(customsDeclaration.getDeclarationCustoms().getName())) {
			if (customsDeclaration.getConveyance() != null
					&& !customsDeclaration.getConveyance().endsWith("")
					&& !customsDeclaration.getConveyance().substring(0, 1)
							.equals("@")) {
				JOptionPane.showMessageDialog(component,
						"报送海关与进出口岸不同时，运输工具前应填@", "提示",
						JOptionPane.YES_NO_OPTION);
				return false;
			}
		}
		String transac = customsDeclaration.getTransac().getName().trim();
		if (transac.equals("CIF")) {
			if (customsDeclaration.getInsuranceType() == null
					|| customsDeclaration.getInsurance() == null
					|| customsDeclaration.getInsurance().doubleValue() == 0
					|| customsDeclaration.getFreightageType() == null
					|| customsDeclaration.getFreightage() == null
					|| customsDeclaration.getFreightage().doubleValue() == 0) {
				JOptionPane.showMessageDialog(component, "成交方式为CIF时，保费和运费不能为空",
						"提示", JOptionPane.YES_NO_OPTION);
				return false;
			}
		}
		if (transac.equals("C&F")) {
			// if (customsDeclaration.getInsuranceType() == null
			// || customsDeclaration.getInsurance() == null
			// || customsDeclaration.getInsurance().doubleValue() == 0
			// || customsDeclaration.getFreightageType() != null
			// || customsDeclaration.getFeeCurr() != null
			// || (customsDeclaration.getFreightage() != null &&
			// customsDeclaration
			// .getFreightage().doubleValue() != 0)) {
			// JOptionPane.showMessageDialog(component,
			// "成交方式为C&F时，保费不能为空，运费必须为空", "提示",
			// JOptionPane.YES_NO_OPTION);
			// return false;
			// }

			if ((customsDeclaration.getInsuranceType() != null
					|| (customsDeclaration.getInsurance() != null && customsDeclaration
							.getInsurance().doubleValue() != 0) || customsDeclaration
						.getInsurCurr() != null)) {
				JOptionPane.showMessageDialog(component, "成交方式为C&F时，保费必须为空",
						"提示", JOptionPane.YES_NO_OPTION);
				return false;
			}

			if (customsDeclaration.getFreightageType() == null
					|| (customsDeclaration.getFreightage() == null || customsDeclaration
							.getFreightage().doubleValue() == 0)
					|| customsDeclaration.getFeeCurr() == null) {
				JOptionPane.showMessageDialog(component, "成交方式为C&F时,运费不能为空",
						"提示", JOptionPane.YES_NO_OPTION);
				return false;
			}
		}
		if (transac.equals("FOB")) {
			if (customsDeclaration.getInsuranceType() != null
					|| (customsDeclaration.getInsurance() != null && customsDeclaration
							.getInsurance().doubleValue() != 0)
					|| customsDeclaration.getFreightageType() != null
					|| (customsDeclaration.getFreightage() != null && customsDeclaration
							.getFreightage().doubleValue() != 0)) {
				JOptionPane.showMessageDialog(component, "成交方式为FOB时，保费和运费必须为空",
						"提示", JOptionPane.YES_NO_OPTION);
				return false;
			}
		}
		if (transac.equals("C&I")) {
			if (customsDeclaration.getInsuranceType() == null
					|| customsDeclaration.getInsurance() == null
					|| customsDeclaration.getInsurance().doubleValue() == 0
					|| customsDeclaration.getFreightageType() != null
					|| (customsDeclaration.getFreightage() != null && customsDeclaration
							.getFreightage().doubleValue() != 0)) {
				JOptionPane.showMessageDialog(component,
						"成交方式为C&I时，保费不能为空，运费必须为空", "提示",
						JOptionPane.YES_NO_OPTION);
				return false;
			}
		}

		if (!customsDeclaration.getTradeMode().getName().equals("来料料件退换")
				&& !customsDeclaration.getTradeMode().getName()
						.equals("进料料件退换")
				&& !customsDeclaration.getTradeMode().getName()
						.equals("进料成品退换")
				&& !customsDeclaration.getTradeMode().getName()
						.equals("来料成品退换")
				&& !customsDeclaration.getTradeMode().getName()
						.equals("进料余料结转")
				&& !customsDeclaration.getTradeMode().getName()
						.equals("来料余料结转")
				&& !customsDeclaration.getTradeMode().getName().equals("进料深加工")
				&& !customsDeclaration.getTradeMode().getName().equals("来料深加工")
				// lm 新加（保税区仓储转口、保税间货物、保税仓库货物、国轮油物料）四种贸易方式允许为空 by 2009-12-10
				&& !customsDeclaration.getTradeMode().getName()
						.equals("保税区仓储转口")
				&& !customsDeclaration.getTradeMode().getName().equals("保税间货物")
				&& !customsDeclaration.getTradeMode().getName()
						.equals("保税仓库货物")
				&& !customsDeclaration.getTradeMode().getName().equals("国轮油物料")) {
			if (customsDeclaration.getLevyKind() == null) {
				JOptionPane.showMessageDialog(component, "征免性质不能为空!", "提示",
						JOptionPane.YES_NO_OPTION);
				return false;
			}
		}
		// edit by xxm
		if (customsDeclaration.getBalanceMode() == null) {
			JOptionPane.showMessageDialog(component, "警告：结汇方式当前为空!", "提示",
					JOptionPane.YES_NO_OPTION);
		}

		if (customsDeclaration.getImpExpType().intValue() == ImpExpType.TRANSFER_FACTORY_EXPORT
				|| customsDeclaration.getImpExpType().intValue() == ImpExpType.REMAIN_FORWARD_EXPORT) {
			if (!"中国".equals(customsDeclaration
					.getCountryOfLoadingOrUnloading().getName().trim())) {
				JOptionPane.showMessageDialog(component,
						"无实际进出境的,运抵国应为中国  (142) !!!", "提示",
						JOptionPane.YES_NO_OPTION);
				return false;
			}
			if (!"中国境内".equals(customsDeclaration.getPortOfLoadingOrUnloading()
					.getName().trim())) {
				JOptionPane.showMessageDialog(component,
						"如无实际进出境，装货港应填写“中国境内”，代码“0142”", "提示",
						JOptionPane.YES_NO_OPTION);
				return false;
			}
			if (!transac.equals("FOB")) {
				JOptionPane.showMessageDialog(component, "如无实际进出境，进口成交方式应为FOB",
						"提示", JOptionPane.YES_NO_OPTION);
				return false;
			}

		}
		// 判断进出类型与贸易方式对应
		if (!isCorresponding(component, customsDeclaration.getImpExpType()
				.intValue(), customsDeclaration.getTradeMode().getName(),
				customsDeclaration)) {
			return false;
		}

		if (customsDeclaration.getTransferMode().getCode().trim().equals("2")) {
			if (customsDeclaration.getBillOfLading() == null
					|| "".equals(customsDeclaration.getBillOfLading())) {
				JOptionPane.showMessageDialog(component, "运输方式为江海运输时,提运单号不能为空",
						"提示", JOptionPane.YES_NO_OPTION);
				return false;
			}
		}
		if (customsDeclaration.getTransferMode().getCode().trim().equals("3")) {
			if (customsDeclaration.getBillOfLading() == null
					|| "".equals(customsDeclaration.getBillOfLading())) {
				JOptionPane.showMessageDialog(component, "运输方式为铁路运输时,提运单号不能为空",

				"提示", JOptionPane.YES_NO_OPTION);
				return false;
			}
		}
		if (customsDeclaration.getTransferMode().getCode().trim().equals("5")) {
			if (customsDeclaration.getBillOfLading() == null
					|| "".equals(customsDeclaration.getBillOfLading())) {
				JOptionPane.showMessageDialog(component, "运输方式为航空运输时,提运单号不能为空",
						"提示", JOptionPane.YES_NO_OPTION);
				return false;
			}
		}
		if (customsDeclaration.getTransferMode().getCode().trim().equals("6")) {
			if (customsDeclaration.getBillOfLading() == null
					|| "".equals(customsDeclaration.getBillOfLading())) {
				JOptionPane.showMessageDialog(component, "运输方式为邮件运输时,提运单号不能为空",
						"提示", JOptionPane.YES_NO_OPTION);
				return false;
			}
		}

		// double exchangeRate = customsDeclaration.getExchangeRate() == null ?
		// 0
		// : Double.parseDouble(customsDeclaration.getExchangeRate()
		// .toString());
		// if (exchangeRate <= 0) {
		// JOptionPane.showMessageDialog(component, "汇率不能小于或等于零", "提示",
		// JOptionPane.YES_NO_OPTION);
		// return false;
		// }
		if (customsDeclaration.getAttachedBill() != null
				&& !customsDeclaration.getAttachedBill().trim().equals("")) {
			String memos = customsDeclaration.getCertificateCode();
			if (memos == null || memos.trim().equals("")) {
				JOptionPane.showMessageDialog(component, "在备注中未发现所有监管证件的编号",
						"提示", JOptionPane.YES_NO_OPTION);
				return false;
			}
			char[] attachedBills = customsDeclaration.getAttachedBill()
					.toCharArray();
			for (int i = 0; i < attachedBills.length; i++) {
				if (memos.indexOf(String.valueOf(attachedBills[i]) + ":") < 0
						&& memos.indexOf(String.valueOf(attachedBills[i]) + "*") < 0) {
					JOptionPane.showMessageDialog(
							component,
							"在备注中未发现监管证件的编号 : "
									+ String.valueOf(attachedBills[i]), "提示",
							JOptionPane.YES_NO_OPTION);
					return false;
				}
			}
		} else {
			String memos = customsDeclaration.getCertificateCode();
			if (memos != null && !memos.trim().equals("")) {
				JOptionPane.showMessageDialog(component,
						"在备注中有证件编码，但随附单据没有选中证件编码!!", "提示",
						JOptionPane.YES_NO_OPTION);
				return false;
			}
		}
		if (customsDeclaration.getConveyance().length() > 0
				&& CommonVars.getIsVailCustomsOther() != null
				&& CommonVars.getIsVailCustomsOther()) {
			String conveyance = customsDeclaration.getConveyance().substring(0,
					1);
			if (conveyance.equals("@")) {
				if (customsDeclaration.getDomesticTransferMode() == null) {
					JOptionPane
							.showMessageDialog(component, "转关运输时,必须填写境内运输方式",
									"提示", JOptionPane.YES_NO_OPTION);
					return false;
				}
				if (customsDeclaration.getDomesticConveyanceCode() == null
						|| "".equals(customsDeclaration
								.getDomesticConveyanceCode())) {
					JOptionPane.showMessageDialog(component,
							"转关运输时,必须填写境内运输工具编号", "提示",
							JOptionPane.YES_NO_OPTION);
					return false;
				}
				if (customsDeclaration.getDomesticConveyanceName() == null
						|| "".equals(customsDeclaration
								.getDomesticConveyanceName())) {
					JOptionPane.showMessageDialog(component,
							"转关运输时,必须填写境内运输工具名称", "提示",
							JOptionPane.YES_NO_OPTION);
					return false;
				}
			}
			// //判断转关运输的运输方式是否与报关单表头的运输方式一致! add 2009.4.1
			// if (customsDeclaration.getDomesticTransferMode()!=null
			// &&
			// !customsDeclaration.getDomesticTransferMode().equals(customsDeclaration.getTransferMode()))
			// {
			// JOptionPane.showMessageDialog(component,
			// "转关运输时运输方式必须报关单表头运输方式一致",
			// "提示", JOptionPane.YES_NO_OPTION);
			// return false;
			// }
		}
		// 为空的条件：退料出口---1，来料料件退换，2，来料料件复出,3.进料成品退换
		// 币制为人民币
		/*
		 * if ((customsDeclaration.getAuthorizeFile().equals(""))) { if
		 * ((customsDeclaration.getAuthorizeFile() == null)) { if
		 * (customsDeclaration.getImpExpType() ==
		 * ImpExpType.BACK_MATERIEL_EXPORT) {// 退料出口 if
		 * (!customsDeclaration.getTradeMode().getName().equals( "来料料件退换") &&
		 * !customsDeclaration.getTradeMode().getName().equals( "进料料件退换") &&
		 * !customsDeclaration.getTradeMode().getName().equals( "来料料件复出") &&
		 * !customsDeclaration.getTradeMode().getName().equals( "进料料件复出") &&
		 * !customsDeclaration.getTradeMode().getName().equals( "保税间货物") &&
		 * !customsDeclaration.getTradeMode().getName().equals( "保税区仓储转口") &&
		 * !customsDeclaration.getTradeMode().getName().equals( "保税仓库货物") &&
		 * !customsDeclaration.getCurrency().getName().equals( "人民币")) {
		 * JOptionPane.showMessageDialog(component, "外汇核销单号不可为空", "提示",
		 * JOptionPane.YES_NO_OPTION); return false;
		 * 
		 * } // 类型：设备退港 ，贸易方式：加工设备退运 } else if
		 * (customsDeclaration.getImpExpType() ==
		 * ImpExpType.EQUIPMENT_BACK_PORT) { if
		 * (!customsDeclaration.getTradeMode().getName().equals( "加工设备退运") &&
		 * !customsDeclaration.getCurrency().getName().equals( "人民币")) {
		 * JOptionPane.showMessageDialog(component, "外汇核销单号不可为空", "提示",
		 * JOptionPane.YES_NO_OPTION); return false; } // 类型：返工复出，贸易方式：进料余料结转 }
		 * else if (customsDeclaration.getImpExpType() !=
		 * ImpExpType.REWORK_EXPORT // 返工复出 &&
		 * customsDeclaration.getImpExpType() !=
		 * ImpExpType.REMAIN_FORWARD_EXPORT // 余料转出
		 * 
		 * && !customsDeclaration.getTradeMode().getName().equals( "进料余料结转")
		 * 
		 * && !customsDeclaration.getTradeMode().getName().equals( "来料料件内销") &&
		 * !customsDeclaration.getTradeMode().getName().equals( "来料料件结转") &&
		 * !customsDeclaration.getTradeMode().getName().equals( "进料料件内销") &&
		 * !customsDeclaration.getTradeMode().getName().equals( "保税区仓储转口") &&
		 * !customsDeclaration.getTradeMode().getName().equals( "保税间货物") &&
		 * !customsDeclaration.getTradeMode().getName().equals( "保税仓库货物") &&
		 * !customsDeclaration.getTradeMode().getName().equals( "国轮油物料") &&
		 * !customsDeclaration.getCurrency().getName() .equals("人民币")) {
		 * JOptionPane.showMessageDialog(component, "外汇核销单号不可为空", "提示",
		 * JOptionPane.YES_NO_OPTION); return false; } } else
		 */
		if (customsDeclaration.getAuthorizeFile() != null
				&& !customsDeclaration.getAuthorizeFile().equals("")) {
			if (customsDeclaration.getAuthorizeFile().trim().toString()
					.length() != 9) {
				JOptionPane.showMessageDialog(component, "批准文号应该为9位,请重新填写！",
						"提示", JOptionPane.YES_NO_OPTION);
				return false;
			} else {
				BaseEncAction baseEncAction = (EncAction) CommonVars
						.getApplicationContext().getBean("encAction");
				List list = baseEncAction.findExistAuthorizeFile(new Request(
						CommonVars.getCurrUser(), true), customsDeclaration
						.getAuthorizeFile(), customsDeclaration.getId());
				if (list.size() > 0) {
					BaseCustomsDeclaration base = (BaseCustomsDeclaration) list
							.get(0);
					String impExpFlagStr = "";
					if (base.getImpExpFlag() == ImpExpFlag.EXPORT) {
						impExpFlagStr = "出口";
					} else if (base.getImpExpFlag() == ImpExpFlag.IMPORT) {
						impExpFlagStr = "进口";
					} else if (base.getImpExpFlag() == ImpExpFlag.SPECIAL) {
						impExpFlagStr = "特殊";
					}
					String info = "第  " + base.getSerialNumber() + "  份"
							+ impExpFlagStr;
					JOptionPane.showMessageDialog(component, "外汇核销单号已被" + info
							+ "报关单使用", "提示", JOptionPane.YES_NO_OPTION);
					return false;
				}

			}
		}
		return true;
	}

	/**
	 * 是进口还是出口
	 */
	public static boolean isImport(int billType) {
		boolean isImport = false;
		switch (billType) {
		case ImpExpType.DIRECT_IMPORT:
		case ImpExpType.TRANSFER_FACTORY_IMPORT:
		case ImpExpType.BACK_FACTORY_REWORK:
		case ImpExpType.GENERAL_TRADE_IMPORT:
		case ImpExpType.EQUIPMENT_IMPORT:
		case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES:// 边角料内销
		case ImpExpType.MATERIAL_DOMESTIC_SALES:// 料件内销
		case ImpExpType.REMAIN_FORWARD_IMPORT: //
			isImport = true;
			break;
		case ImpExpType.DIRECT_EXPORT:
		case ImpExpType.TRANSFER_FACTORY_EXPORT:
		case ImpExpType.BACK_MATERIEL_EXPORT:
		case ImpExpType.REWORK_EXPORT:
		case ImpExpType.REMIAN_MATERIAL_BACK_PORT:
		case ImpExpType.GENERAL_TRADE_EXPORT:
		case ImpExpType.EQUIPMENT_BACK_PORT:
		case ImpExpType.BACK_PORT_REPAIR:
		case ImpExpType.REMAIN_FORWARD_EXPORT:
			isImport = false;
			break;
		}
		return isImport;
	}

	/**
	 * 是料件还是成品
	 * 
	 * @param impExpType
	 * @return
	 */
	public static boolean isMaterial(int impExpType) {
		boolean isMaterial = false;
		int materielType = getMaterielTypeByBillType(impExpType);
		if (materielType == Integer.parseInt(MaterielType.MATERIEL)) {
			isMaterial = true;
		} else if (materielType == Integer
				.parseInt(MaterielType.FINISHED_PRODUCT)) {
			isMaterial = false;
		}
		return isMaterial;
	}

	/**
	 * 获得料件成品标识来自进出口申请单类型
	 */
	public static int getMaterielTypeByBillType(int billType) {
		int temp = Integer.parseInt(MaterielType.MATERIEL);
		switch (billType) {
		case ImpExpType.DIRECT_IMPORT:
		case ImpExpType.TRANSFER_FACTORY_IMPORT:
		case ImpExpType.GENERAL_TRADE_IMPORT:
		case ImpExpType.BACK_MATERIEL_EXPORT:
		case ImpExpType.REMIAN_MATERIAL_BACK_PORT:
		case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES:
		case ImpExpType.REMAIN_FORWARD_EXPORT:
			temp = Integer.parseInt(MaterielType.MATERIEL);
			break;
		case ImpExpType.BACK_FACTORY_REWORK:
		case ImpExpType.DIRECT_EXPORT:
		case ImpExpType.TRANSFER_FACTORY_EXPORT:
		case ImpExpType.REWORK_EXPORT:
		case ImpExpType.GENERAL_TRADE_EXPORT:
			temp = Integer.parseInt(MaterielType.FINISHED_PRODUCT);
			break;
		}
		return temp;
	}

	/**
	 * 是料件还是成品
	 * 
	 * @param impExpType
	 * @return
	 */
	public static boolean isMaterial1(int impExpType) {
		boolean isMaterial = false;
		int materielType = getMaterielTypeByBillType1(impExpType);
		if (materielType == Integer.parseInt(MaterielType.MATERIEL)) {
			isMaterial = true;
		} else if (materielType == Integer
				.parseInt(MaterielType.FINISHED_PRODUCT)) {
			isMaterial = false;
		}
		return isMaterial;
	}

	/**
	 * 获得料件成品标识来自进出口申请单类型
	 */
	public static int getMaterielTypeByBillType1(int billType) {
		int temp = Integer.parseInt(MaterielType.MATERIEL);
		switch (billType) {
		case ImpExpType.DIRECT_IMPORT:
		case ImpExpType.TRANSFER_FACTORY_IMPORT:
		case ImpExpType.GENERAL_TRADE_IMPORT:
		case ImpExpType.BACK_MATERIEL_EXPORT:
		case ImpExpType.REMIAN_MATERIAL_BACK_PORT:
		case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES:
		case ImpExpType.REMAIN_FORWARD_EXPORT:
		case ImpExpType.BACK_FACTORY_REWORK:
			temp = Integer.parseInt(MaterielType.MATERIEL);
			break;
		case ImpExpType.DIRECT_EXPORT:
		case ImpExpType.TRANSFER_FACTORY_EXPORT:
		case ImpExpType.REWORK_EXPORT:
		case ImpExpType.GENERAL_TRADE_EXPORT:
			temp = Integer.parseInt(MaterielType.FINISHED_PRODUCT);
			break;
		}
		return temp;
	}

	/**
	 * 检查特殊报关单数据
	 * 
	 * @return
	 */
	public static boolean checkSpecialCustomsDeclarationData(
			BaseCustomsDeclaration customsDeclaration, Component component) {
		if (customsDeclaration == null) {
			return false;
		}
		if (customsDeclaration.getImpExpType() == null) {
			JOptionPane.showMessageDialog(component, "进出口类型不能为空", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		// 判断当贸易方式为进料边角料内销0844、来料边角料内销0845
		// 报送海关前两位为‘52’时、码头为99其它业务、备注为[装卸口岸]:其它业务
		// BY 石小凯
		// if (customsDeclaration.getTradeMode() != null) {
		// if ("0844".equals(customsDeclaration.getTradeMode().getCode())
		// || "0845".equals(customsDeclaration.getTradeMode()
		// .getCode())) {
		// if ("52".equals(customsDeclaration.getDeclarationCustoms()
		// .getCode().substring(0, 2))) {
		// if (customsDeclaration.getPredock() != null) {
		// if (!customsDeclaration.getPredock().getName().trim()
		// .equals("其它业务")) {
		// JOptionPane.showMessageDialog(component,
		// "装卸码头必须为‘【99】其它业务’", "提示",
		// JOptionPane.YES_NO_OPTION);
		// return false;
		// }
		// } else {
		// JOptionPane.showMessageDialog(component,
		// "装卸码头必须为‘【99】其它业务’", "提示",
		// JOptionPane.YES_NO_OPTION);
		// return false;
		// }
		// if (!customsDeclaration.getMemos().contains(
		// "[装卸口岸]:"
		// + customsDeclaration.getPredock()
		// .getShortName())) {
		// JOptionPane.showMessageDialog(component,
		// "备注栏应填写的[装卸口岸]简称不正确", "提示",
		// JOptionPane.YES_NO_OPTION);
		// return false;
		// }
		// }
		// }
		// }

		System.out.println("customsDeclaration.getMemos().indexOf()+"
				+ customsDeclaration.getMemos().indexOf("[装卸口岸]"));
		System.out.println("customsDeclaration.getMemos().lastIndexOf()+"
				+ customsDeclaration.getMemos().lastIndexOf("[装卸口岸]"));
		if (customsDeclaration.getMemos().indexOf("[装卸口岸]") != customsDeclaration
				.getMemos().lastIndexOf("[装卸口岸]")) {
			JOptionPane.showMessageDialog(component, "备注栏的[装卸口岸]出现重复", "提示",
					JOptionPane.YES_NO_OPTION);
			return false;
		}
		boolean isImport = isImport(customsDeclaration.getImpExpType()
				.intValue());
		if (isImport == true) {
			return EncCommon.checkImportCustomsDeclarationData(
					customsDeclaration, component);
		} else {
			return EncCommon.checkExportCustomsDeclarationData(
					customsDeclaration, component);
		}
	}

	public static String substring(String str, int start, int end) {
		if (str != null && str.getBytes().length >= end) {
			return new String(str.getBytes(), start, end - start);
		} else if (str != null && str.getBytes().length > start) {
			return new String(str.getBytes(), start, str.getBytes().length
					- start);
		}
		return "";
	}

	/**
	 * 抓取报关单号为空的涉及转厂的关封资料
	 * 
	 * @param impExpType
	 * @param emsNo
	 * @param scmCoc
	 * @return
	 */
	public static TempTransFactInfo findTempTransFactInfo(int impExpType,
			String emsNo, ScmCoc scmCoc) {
		FptManageAction fptManageAction = (FptManageAction) CommonVars
				.getApplicationContext().getBean("fptManageAction");
		TransferFactoryManageAction transferFactoryManageAction = (TransferFactoryManageAction) CommonVars
				.getApplicationContext().getBean("transferFactoryManageAction");
		String scmCocName = "对方名称";// (isImport ? "供应商名称" : "客户名称");
		List<TempTransFactInfo> listAll = new ArrayList<TempTransFactInfo>();
		List listData = fptManageAction.findFptAppItemByCustomsDeclaration(
				new Request(CommonVars.getCurrUser()), impExpType, emsNo,
				scmCoc);
		for (int i = 0; i < listData.size(); i++) {
			FptAppItem fptAppItem = (FptAppItem) listData.get(i);
			TempTransFactInfo temp = new TempTransFactInfo();
			temp.setBillNo(fptAppItem.getFptAppHead().getAppNo());
			// if (ImpExpType.TRANSFER_FACTORY_IMPORT == impExpType) {
			// temp.setScmCocName(fptAppItem.getFptAppHead().getOutTradeName());
			// }else{
			// temp.setScmCocName(fptAppItem.getFptAppHead().getInTradeName());
			// }
			temp.setScmCocName(fptAppItem.getFptAppHead().getScmCoc().getName());
			temp.setComplexCode(fptAppItem.getCodeTs().getCode());
			temp.setName(fptAppItem.getName());
			temp.setSpec(fptAppItem.getSpec());
			temp.setQty(fptAppItem.getQty());
			temp.setMemo("深加工结转");
			listAll.add(temp);
		}
		boolean isImport = (ImpExpType.TRANSFER_FACTORY_IMPORT == impExpType ? true
				: false);
		listData = transferFactoryManageAction.findCustomsEnvelopCommodityInfo(
				new Request(CommonVars.getCurrUser()), isImport, true, emsNo,
				scmCoc);
		for (int i = 0; i < listData.size(); i++) {
			CustomsEnvelopCommodityInfo fptAppItem = (CustomsEnvelopCommodityInfo) listData
					.get(i);
			TempTransFactInfo temp = new TempTransFactInfo();
			temp.setBillNo(fptAppItem.getCustomsEnvelopBill()
					.getCustomsEnvelopBillNo());
			temp.setScmCocName(fptAppItem.getCustomsEnvelopBill().getScmCoc()
					.getName());
			temp.setComplexCode(fptAppItem.getComplex().getCode());
			temp.setName(fptAppItem.getPtName());
			temp.setSpec(fptAppItem.getPtSpec());
			temp.setQty(fptAppItem.getOwnerQuantity());
			temp.setMemo("转厂管理【原】");
			listAll.add(temp);
		}
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("结转申请单(关封)号", "billNo", 150));
		list.add(new JTableListColumn(scmCocName, "scmCocName", 150));
		list.add(new JTableListColumn("商品编码", "complexCode", 100));
		list.add(new JTableListColumn("商品名称", "name", 100));
		list.add(new JTableListColumn("商品规格", "spec", 100));
		list.add(new JTableListColumn("申请数量", "qty", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(listAll);
		dgCommonQuery.setTitle("结转申请单(关封)");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return (TempTransFactInfo) dgCommonQuery.getReturnValue();
		}
		return null;
	}

}
