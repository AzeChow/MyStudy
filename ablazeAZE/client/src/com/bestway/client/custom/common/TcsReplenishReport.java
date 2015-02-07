package com.bestway.client.custom.common;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.message.entity.DecSupplementList;
import com.bestway.client.custom.common.supplement.DgReplenishDeclareCommonInfo;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.RepDeclarationType;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;

/**
 * TCS 补充申报打印专用类
 * @author Administrator
 *
 */
public class TcsReplenishReport {
	/**
	 * BcsClientHelper静态实例
	 */
	private static TcsReplenishReport tcsReplenishReport = null;
	private static BaseEncAction baseEncAction;

	/**
	 * 类方法用来返回上面的实例
	 * 
	 * @return
	 */
	public static TcsReplenishReport getInstance() {
		baseEncAction = (BaseEncAction) CommonVars.getApplicationContext()
		.getBean("encAction");
		if (tcsReplenishReport == null) {
			tcsReplenishReport = new TcsReplenishReport();
		}
		return tcsReplenishReport;
	}
	/**
	 * 打印价格补充申报
	 * 
	 * @param dialog
	 * @param contract
	 */
	public void printReplenish(DgReplenishDeclareCommonInfo dgReplenishDeclareCommonInfo,DecSupplementList decSupplement,Boolean isOverprint) {
		if (decSupplement == null) {
			JOptionPane.showMessageDialog(dgReplenishDeclareCommonInfo, "请先保存申报记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (!RepDeclarationType.SupTypePrice.equals(decSupplement.getSupType())) {
			JOptionPane.showMessageDialog(dgReplenishDeclareCommonInfo, "请先选择价格申报记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		BaseCustomsDeclarationCommInfo baseCustomsDeclarationCommInfo= baseEncAction.getBaseCustomsDeclarationCommInfoById(decSupplement.getBaseCustomsDeclarationCommInfo());
		try{
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("customsDeclarationCode",baseCustomsDeclarationCommInfo.getBaseCustomsDeclaration().getCustomsDeclarationCode());
		parameters.put("commName",baseCustomsDeclarationCommInfo.getCommName());
		parameters.put("code",baseCustomsDeclarationCommInfo.getComplex().getCode());
		parameters.put("commSpec",baseCustomsDeclarationCommInfo.getCommSpec());
		parameters.put("serialNumber", baseCustomsDeclarationCommInfo.getSerialNumber().toString());
		parameters.put("brandCN", decSupplement.getBrandCN());
		parameters.put("brandEN", decSupplement.getBrandEN());
		parameters.put("buyer", decSupplement.getBuyer());
		parameters.put("buyerContact", decSupplement.getBuyerContact());
		parameters.put("buyerAddr", decSupplement.getBuyerAddr());
		parameters.put("buyerTel", decSupplement.getBuyerTel());
		parameters.put("seller", decSupplement.getSeller());
		parameters.put("sellerContact", decSupplement.getSellerContact());
		parameters.put("sellerAddr", decSupplement.getSellerAddr());
		parameters.put("sellerTel", decSupplement.getSellerTel());
		parameters.put("factory", decSupplement.getFactory());
		parameters.put("factoryContact", decSupplement.getFactoryContact());
		parameters.put("factoryAddr", decSupplement.getFactoryAddr());
		parameters.put("factoryTel", decSupplement.getFactoryTel());
		parameters.put("contrNo", decSupplement.getContrNo());
		parameters.put("contrDate", CommonUtils.getDate(decSupplement.getContrDate(), "yyyy-MM-dd"));
		parameters.put("invoiceNo", decSupplement.getInvoiceNo());
		parameters.put("invoiceDate", CommonUtils.getDate(decSupplement.getInvoiceDate(),"yyyy-MM-dd"));
	
		//买卖双方之间的关系
		String i_BabRel = decSupplement.getI_BabRel();
		char[] i_BabRelChar = i_BabRel.toCharArray();
		for (int j = 0; j < i_BabRelChar.length; j++) {
			parameters.put("i_BabRel"+(j+1),String.valueOf(i_BabRelChar[j]));
		}	
		parameters.put("i_PriceEffect", decSupplement.getI_PriceEffect());
		
		//进出口货物的成交价格
		parameters.put("i_PriceClose", decSupplement.getI_PriceClose());
		parameters.put("i_OtherLimited", decSupplement.getI_OtherLimited());
		parameters.put("i_OtherEffect", decSupplement.getI_OtherEffect());
		parameters.put("isUsefee", decSupplement.getI_IsUsefee());
		parameters.put("i_IsProfit", decSupplement.getI_IsProfit());
		parameters.put("e_IsDutyDel", decSupplement.getE_IsDutyDel());
		parameters.put("isSecret", decSupplement.getIsSecret());
		parameters.put("agentType", decSupplement.getAgentType());
		parameters.put("i_PartPrice", decSupplement.getI_PartPrice());
		parameters.put("i_PartAmount", decSupplement.getI_PartAmount());
		parameters.put("i_PartNote", decSupplement.getI_PartNote());
		parameters.put("i_PackPrice", decSupplement.getI_PackPrice());
		parameters.put("i_PackAmount", decSupplement.getI_PackAmount());
		parameters.put("i_PackNote", decSupplement.getI_PackNote());
		parameters.put("curr", decSupplement.getCurr().getName());
		parameters.put("invoicePrice", decSupplement.getInvoicePrice());
		parameters.put("invoiceAmount", decSupplement.getInvoiceAmount());
		parameters.put("invoiceNote", decSupplement.getInvoiceNote());
		parameters.put("i_CommissionPrice", decSupplement.getI_CommissionPrice());
		parameters.put("i_CommissionAmount", decSupplement.getI_CommissionAmount());
		parameters.put("i_CommissionNote", decSupplement.getI_CommissionNote());
		parameters.put("goodsPrice", decSupplement.getGoodsPrice());
		parameters.put("goodsAmount", decSupplement.getGoodsAmount());
		parameters.put("goodsNote", decSupplement.getGoodsNote());
		parameters.put("i_ContainerPrice", decSupplement.getI_ContainerPrice());
		parameters.put("i_ContainerAmount", decSupplement.getI_ContainerAmount());
		parameters.put("i_ContainerNote", decSupplement.getI_ContainerNote());
		parameters.put("i_ToolPrice", decSupplement.getI_ToolPrice());
		parameters.put("i_ToolAmount", decSupplement.getI_ToolAmount());
		parameters.put("i_ToolNote", decSupplement.getI_ToolNote());
		parameters.put("i_LossPrice", decSupplement.getI_LossPrice());
		parameters.put("i_LossAmount", decSupplement.getI_LossAmount());
		parameters.put("i_LossNote", decSupplement.getI_LossNote());
		parameters.put("i_DesignPrice", decSupplement.getI_DesignPrice());
		parameters.put("i_DesignAmount", decSupplement.getI_DesignAmount());
		parameters.put("i_DesignNote", decSupplement.getI_DesignNote());
		parameters.put("i_UsefeePrice", decSupplement.getI_UsefeePrice());
		parameters.put("i_UsefeeAmount", decSupplement.getI_UsefeeAmount());
		parameters.put("i_UsefeeNote", decSupplement.getI_UsefeeNote());
		parameters.put("i_ProfitPrice", decSupplement.getI_ProfitPrice());
		parameters.put("i_ProfitAmount", decSupplement.getI_ProfitAmount());
		parameters.put("i_ProfitNote", decSupplement.getI_ProfitNote());
		parameters.put("i_FeePrice", decSupplement.getI_FeePrice());
		parameters.put("i_FeeAmount", decSupplement.getI_FeeAmount());
		parameters.put("i_FeeNote", decSupplement.getI_FeeNote());
		parameters.put("i_OtherPrice", decSupplement.getI_OtherPrice());
		parameters.put("i_OtherAmount", decSupplement.getI_OtherAmount());
		parameters.put("i_OtherNote", decSupplement.getI_OtherNote());
		parameters.put("i_InsurPrice", decSupplement.getI_InsurPrice());
		parameters.put("i_InsurAmount", decSupplement.getI_InsurAmount());
		parameters.put("i_InsurNote", decSupplement.getI_InsurNote());
		parameters.put("declAddr", decSupplement.getDeclAddr());
		parameters.put("declTel", decSupplement.getDeclTel());
		parameters.put("declPost", decSupplement.getDeclPost());
		parameters.put("companyName", baseCustomsDeclarationCommInfo.getCompany().getName());
		parameters.put("isOverprint",isOverprint);
		InputStream masterReportStream = TcsReplenishReport.class
		.getResourceAsStream("report/TCSImpExpCommodityPriceReplenishReport.jasper");
		InputStream masterReportStream2 = TcsReplenishReport.class
		.getResourceAsStream("report/TCSImpExpCommodityPriceReplenishReport2.jasper");
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				masterReportStream, parameters);
		JasperPrint jasperPrint2 = JasperFillManager.fillReport(
				masterReportStream2, parameters);
		jasperPrint.addPage((JRPrintPage)jasperPrint2.getPages().get(0));
		DgReportViewer viewer = new DgReportViewer(jasperPrint);
		viewer.setVisible(true);
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 打印归类补充申报
	 * 
	 * @param dialog
	 * @param contract
	 */
	public void mergerReplenish(DgReplenishDeclareCommonInfo dgReplenishDeclareCommonInfo,DecSupplementList decSupplement,Boolean isOverprint) {
		if (decSupplement == null) {
			JOptionPane.showMessageDialog(dgReplenishDeclareCommonInfo, "请先保存申报记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (!RepDeclarationType.SupTypeMerger.equals(decSupplement.getSupType())) {
			JOptionPane.showMessageDialog(dgReplenishDeclareCommonInfo, "请先选择归类申报记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		BaseCustomsDeclarationCommInfo baseCustomsDeclarationCommInfo= baseEncAction.getBaseCustomsDeclarationCommInfoById(decSupplement.getBaseCustomsDeclarationCommInfo());
		try{
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("customsDeclarationCode",baseCustomsDeclarationCommInfo.getBaseCustomsDeclaration().getCustomsDeclarationCode());
		parameters.put("commName",baseCustomsDeclarationCommInfo.getCommName());
		parameters.put("code",baseCustomsDeclarationCommInfo.getComplex().getCode());
		parameters.put("commSpec",baseCustomsDeclarationCommInfo.getCommSpec());
		parameters.put("serialNumber", baseCustomsDeclarationCommInfo.getSerialNumber().toString());
		parameters.put("brandCN", decSupplement.getBrandCN());
		parameters.put("brandEN", decSupplement.getBrandEN());
		parameters.put("buyer", decSupplement.getBuyer());
		parameters.put("buyerContact", decSupplement.getBuyerContact());
		parameters.put("buyerAddr", decSupplement.getBuyerAddr());
		parameters.put("buyerTel", decSupplement.getBuyerTel());
		parameters.put("seller", decSupplement.getSeller());
		parameters.put("sellerContact", decSupplement.getSellerContact());
		parameters.put("sellerAddr", decSupplement.getSellerAddr());
		parameters.put("sellerTel", decSupplement.getSellerTel());
		parameters.put("factory", decSupplement.getFactory());
		parameters.put("factoryContact", decSupplement.getFactoryContact());
		parameters.put("factoryAddr", decSupplement.getFactoryAddr());
		parameters.put("factoryTel", decSupplement.getFactoryTel());
		parameters.put("contrNo", decSupplement.getContrNo());
		parameters.put("contrDate", CommonUtils.getDate(decSupplement.getContrDate(), "yyyy-MM-dd"));
		parameters.put("invoiceNo", decSupplement.getInvoiceNo());
		parameters.put("invoiceDate", CommonUtils.getDate(decSupplement.getInvoiceDate(),"yyyy-MM-dd"));
		parameters.put("gNameOther", decSupplement.getGnameOther());
		parameters.put("codeTsOther", decSupplement.getCodeTsOther());
		parameters.put("isClassDecision", decSupplement.getIsClassDecision());
		parameters.put("decisionNO", decSupplement.getDecisionNo());
		parameters.put("codeTsDecision", decSupplement.getCodeTsDecision());
		parameters.put("decisionCus", decSupplement.getDecisionCus().getName());
		parameters.put("isSampleTest", decSupplement.getIsSampleTest());
		parameters.put("agentType", decSupplement.getAgentType());
		//商品信息选项
		String goptions = decSupplement.getGoptions();
		char[] goptionsChar = goptions.toCharArray();
		for (int j = 0; j < goptionsChar.length; j++) {
			parameters.put("goptions"+(j+1),String.valueOf(goptionsChar[j]));
		}
		parameters.put("agentType", decSupplement.getAgentType());
		parameters.put("isSecret", decSupplement.getIsSecret());
		parameters.put("declAddr", decSupplement.getDeclAddr());
		parameters.put("declTel", decSupplement.getDeclTel());
		parameters.put("declPost", decSupplement.getDeclPost());
		parameters.put("companyName", baseCustomsDeclarationCommInfo.getCompany().getName());
		parameters.put("isOverprint",isOverprint);
		InputStream masterReportStream = TcsReplenishReport.class
		.getResourceAsStream("report/TCSImpExpCommodityMergerReplenishReport.jasper");
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				masterReportStream, parameters);
		DgReportViewer viewer = new DgReportViewer(jasperPrint);
		viewer.setVisible(true);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 打印原产地补充申报
	 * 
	 * @param dialog
	 * @param contract
	 */
	public void originReplenish(DgReplenishDeclareCommonInfo dgReplenishDeclareCommonInfo,DecSupplementList decSupplement,Boolean isOverprint) {
		if (decSupplement == null) {
			JOptionPane.showMessageDialog(dgReplenishDeclareCommonInfo, "请先保存申报记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (!RepDeclarationType.SupTypeOrigin.equals(decSupplement.getSupType())) {
			JOptionPane.showMessageDialog(dgReplenishDeclareCommonInfo, "请先选择原产地申报记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		BaseCustomsDeclarationCommInfo baseCustomsDeclarationCommInfo= baseEncAction.getBaseCustomsDeclarationCommInfoById(decSupplement.getBaseCustomsDeclarationCommInfo());
		try{
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("customsDeclarationCode",baseCustomsDeclarationCommInfo.getBaseCustomsDeclaration().getCustomsDeclarationCode());
		parameters.put("commName",baseCustomsDeclarationCommInfo.getCommName());
		parameters.put("code",baseCustomsDeclarationCommInfo.getComplex().getCode());
		parameters.put("commSpec",baseCustomsDeclarationCommInfo.getCommSpec());
		parameters.put("attachedBill",baseCustomsDeclarationCommInfo.getBaseCustomsDeclaration().getAttachedBill());
		parameters.put("serialNumber", baseCustomsDeclarationCommInfo.getSerialNumber().toString());
		parameters.put("brandCN", decSupplement.getBrandCN());
		parameters.put("brandEN", decSupplement.getBrandEN());
		parameters.put("buyer", decSupplement.getBuyer());
		parameters.put("buyerContact", decSupplement.getBuyerContact());
		parameters.put("buyerAddr", decSupplement.getBuyerAddr());
		parameters.put("buyerTel", decSupplement.getBuyerTel());
		parameters.put("seller", decSupplement.getSeller());
		parameters.put("sellerContact", decSupplement.getSellerContact());
		parameters.put("sellerAddr", decSupplement.getSellerAddr());
		parameters.put("sellerTel", decSupplement.getSellerTel());
		parameters.put("factory", decSupplement.getFactory());
		parameters.put("factoryContact", decSupplement.getFactoryContact());
		parameters.put("factoryAddr", decSupplement.getFactoryAddr());
		parameters.put("factoryTel", decSupplement.getFactoryTel());
		parameters.put("contrNo", decSupplement.getContrNo());
		parameters.put("contrDate", CommonUtils.getDate(decSupplement.getContrDate(), "yyyy-MM-dd"));
		parameters.put("invoiceNo", decSupplement.getInvoiceNo());
		parameters.put("invoiceDate", CommonUtils.getDate(decSupplement.getInvoiceDate(),"yyyy-MM-dd"));
	
		parameters.put("i_PriceEffect", decSupplement.getI_PriceEffect());
		parameters.put("isDirectTraf", decSupplement.getIsDirectTraf());
		parameters.put("transitCountry", decSupplement.getTransitCountry().getName());
		parameters.put("destPort", decSupplement.getDestPort().getName());
		parameters.put("declPort", decSupplement.getDeclPort().getName());
		parameters.put("billNo", decSupplement.getBillNo());
		parameters.put("originCountry", decSupplement.getOriginCountry().getName());
		parameters.put("originMark", decSupplement.getOriginMark());
		parameters.put("certCountry", decSupplement.getCertCountry().getName());
		parameters.put("certNO", decSupplement.getCertNo());
		parameters.put("certStandard", decSupplement.getCertStandard());
		parameters.put("isSecret", decSupplement.getIsSecret());
		parameters.put("agentType", decSupplement.getAgentType());
		parameters.put("declAddr", decSupplement.getDeclAddr());
		parameters.put("declTel", decSupplement.getDeclTel());
		parameters.put("declPost", decSupplement.getDeclPost());
		parameters.put("companyName", baseCustomsDeclarationCommInfo.getCompany().getName());
		parameters.put("isOverprint",isOverprint);
		InputStream masterReportStream = TcsReplenishReport.class
		.getResourceAsStream("report/TCSImpExpCmmodityOriginReplenishReport.jasper");
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				masterReportStream, parameters);
		DgReportViewer viewer = new DgReportViewer(jasperPrint);
		viewer.setVisible(true);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
