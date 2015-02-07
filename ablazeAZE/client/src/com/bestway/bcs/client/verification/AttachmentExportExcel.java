package com.bestway.bcs.client.verification;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.bestway.bcs.verification.action.VFVerificationAction;
import com.bestway.bcs.verification.entity.VFAnalyType;
import com.bestway.bcs.verification.entity.VFAttachmentManagement;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;

public class AttachmentExportExcel {
	private VFVerificationAction vfVerificationAction;
	private Request request = null;
	private VFSection section = null;
	private File file;
	private VFAttachmentManagement attachment = null;
	public AttachmentExportExcel(VFAttachmentManagement attachment,File file){
		vfVerificationAction = (VFVerificationAction) CommonVars.getApplicationContext()
				.getBean("vfVerificationAction");
		request = new Request(CommonVars.getCurrUser());
		section = attachment.getSection();
		this.attachment = attachment;
		this.file = file;
	}
	
	public void exportExcel(){
		if(VFAttachmentManagement.DECLARATION_COMMINFO_EXGX_IMP.equals(attachment.getOperateState())){
			exportECSDeclarationCommInfoExgxImp();
		}else if(VFAttachmentManagement.DECLARETION_COMMINFO_EXGX_EXP.equals(attachment.getOperateState())){
			exportECSDeclarationCommInfoExgxExp();
		}else if(VFAttachmentManagement.STOCK_EXG.equals(attachment.getOperateState())){
			exportECSStockExg();
		}else if(VFAttachmentManagement.STOCK_IMG.equals(attachment.getOperateState())){
			exportECSStockImg();
		}else if(VFAttachmentManagement.FINISHING_STOCK.equals(attachment.getOperateState())){
			exportECSFinishingStock();
		}else if(VFAttachmentManagement.STOCK_TRAVELING_IMG.equals(attachment.getOperateState())){
			exportECSStockTravelingImg();
		}else if(VFAttachmentManagement.STOCK_TRAVELING_EXG.equals(attachment.getOperateState())){
			exportECSStockTravelingExg();
		}else if(VFAttachmentManagement.STOCK_OUT_SEND_ANALYSE.equals(attachment.getOperateState())){
			exportECSStockOutSendAnalyse();
		}else if(VFAttachmentManagement.STOCK_BUY_IMG.equals(attachment.getOperateState())){
			exportECSStockBuyImg();
		}else if(VFAttachmentManagement.STOCK_OUT_FACTORY_IMG.equals(attachment.getOperateState())){
			exportECSStockOutFactoryImg();
		}else if(VFAttachmentManagement.STOCK_PROCESS_IMG.equals(attachment.getOperateState())){
			exportECSStockProcessImg();
		}else if(VFAttachmentManagement.BAD_STOCK.equals(attachment.getOperateState())){
			exportECSBadStock();
		}else if(VFAttachmentManagement.STOCK_SEMI_EXG_PT_HAD_STORE.equals(attachment.getOperateState())){
			exportECSStockSemiExgPtHadStore();
		}else if(VFAttachmentManagement.STOCK_EXG_CONVERT.equals(attachment.getOperateState())){
			exportECSStockExgConvert();
		}else if(VFAttachmentManagement.FINISHING_STOCK_CONVERT.equals(attachment.getOperateState())){
			exportECSFinishingStockConvert();
		}else if(VFAttachmentManagement.STOCK_TRAVELING_EXG_CONVERT.equals(attachment.getOperateState())){
			exportECSStockTravelingExgConvert();
		}else if(VFAttachmentManagement.STOCK_OUT_SEND_ANALYSE_CONVERT.equals(attachment.getOperateState())){
			exportECSStockOutSendAnalyseConvert();
		}else if(VFAttachmentManagement.SEMI_FINISHED_EXG.equals(attachment.getOperateState())){
			exportECSSemiFinishedExgConvert();
		}else if(VFAttachmentManagement.FINISHED_EXG.equals(attachment.getOperateState())){
			exportECSFinishedExgConvert();
		}else if(VFAttachmentManagement.STOCK_SEMI_EXG_PT_HAD_STORE_CONVERT.equals(attachment.getOperateState())){
			exportECSStockSemiExgPtHadStoreConvert();
		}else if(VFAttachmentManagement.ANALYSES.equals(attachment.getOperateState())){
			exportECSAnalyses();
		}else if(VFAttachmentManagement.STOCK_OUT_SEND_SEMI_EXG.equals(attachment.getOperateState())){
			exportECSStockOutSendSemiExgConvert();
		}else if(VFAttachmentManagement.CONTRACT_ANALYSE.equals(attachment.getOperateState())){
			exportVFContractAnalyse();
		}
	}
	
	public void export(List<Object[]> columns,List list){
		ExportExcel exportExecl = new ExportExcel();
		exportExecl.setColumns(columns);
		exportExecl.setDataSource(list);
		exportExecl.export(file);
	}
	
	/**
	 * 1.合同分析
	 */
	public void exportVFContractAnalyse(){
		List list = vfVerificationAction.findVFContractAnalyseVFBySection(request,section,null);
		List<Object[]> columns = new ArrayList<Object[]>();
		columns.add(new Object[]{"归并序号", "mergerNo", "6"});
		columns.add(new Object[]{"海关料件名称", "hsName", "10"});
		columns.add(new Object[]{"海关料件规格", "hsSpec", "14"});
		columns.add(new Object[]{"海关计量单位", "hsUnit", "10"});
		columns.add(new Object[]{"料件进口平均单价", "unitPrice", "10"});
		columns.add(new Object[]{"进口数量(A)", "impAmount", "10"});
		columns.add(new Object[]{"出口数量(B)", "expAmount", "10"});
		columns.add(new Object[]{"合同应余数量", "contractLeaveNum", "10"});
		columns.add(new Object[]{"库存数量", "stockAmount", "10"});
		columns.add(new Object[]{"溢多数量(A-B)", "overflowAmount", "10"});
		columns.add(new Object[]{"短少数量(A-B)", "deficitAmount", "10"});
		columns.add(new Object[]{"溢多金额", "overflowPrice", "10"});
		columns.add(new Object[]{"短少金额", "deficitPrice", "10"});
		export(columns,list);
	}
	
	/**
	 * 2、稽查期内进口报关单
	 */
	public void exportECSDeclarationCommInfoExgxImp(){
		if(section==null){
			return;
		}
		long count = vfVerificationAction.countVFCustomsImgOrExgBySection(request, section, null, null, false, VFAnalyType.VFCUSTOMS_IMG);
		
		List list = vfVerificationAction.findPageVFByVFSection(
				request, section ,0, (int)count, null, null, false, VFAnalyType.VFCUSTOMS_IMG);
		
		List<Object[]> columns = new ArrayList<Object[]>();
		columns.add(new Object[]{"备案序号", "seqNum", 10});
		columns.add(new Object[]{"手册号",  "emsNo", 10});
		columns.add(new Object[]{"报关单号", "customsDeclarationCode", 10});
		columns.add(new Object[]{"企业名称", "companyName", 10});
		columns.add(new Object[]{"申报日期","declarationDate", 10});
		columns.add(new Object[]{"商品名称","commName", 10});
		columns.add(new Object[]{"商品规格", "commSpec", 10});
		columns.add(new Object[]{"计量单位", "unit", 10});
		columns.add(new Object[]{"商品单价", "commUnitPrice", 10});
		columns.add(new Object[]{"商品数量", "commAmount", 10});
		columns.add(new Object[]{"总金额", "totalMoney", 10});
		columns.add(new Object[]{"进出口类型", "impExpType", 10});
		columns.add(new Object[]{"贸易方式", "tradeMode", 10});
		columns.add(new Object[]{"归并序号", "mergerNo", 10});
		export(columns,list);
	}
	
	/**
	 * 3、稽查期内出口报关单
	 */
	public void exportECSDeclarationCommInfoExgxExp(){
		if(section==null){
			return;
		}
		
		long count = vfVerificationAction.countVFCustomsImgOrExgBySection(request, section, null, null, false, VFAnalyType.VFCUSTOMS_EXG);
		List list = vfVerificationAction.findPageVFByVFSection(request, section ,0, (int)count, null, null, false,VFAnalyType.VFCUSTOMS_EXG);
		
		List<Object[]> columns = new ArrayList<Object[]>();
		columns.add(new Object[]{"备案序号", "seqNum", 10});
		columns.add(new Object[]{"手册号",  "emsNo", 10});
		columns.add(new Object[]{"报关单号", "customsDeclarationCode", 10});
		columns.add(new Object[]{"企业名称","companyName", 10});
		columns.add(new Object[]{"申报日期","declarationDate", 10});
		columns.add(new Object[]{"商品名称","commName", 10});
		columns.add(new Object[]{"商品规格","commSpec", 10});
		columns.add(new Object[]{"商品数量","commAmount", 10});
		columns.add(new Object[]{"单位", "unit", 10});
		columns.add(new Object[]{"商品单价", "commUnitPrice", 10});
		columns.add(new Object[]{"总金额", "totalMoney", 10});
		columns.add(new Object[]{"进出口类型", "impExpType", 10});
		columns.add(new Object[]{"贸易方式", "tradeMode", 10});
		columns.add(new Object[]{"归并序号", "mergerNo", 10});
		export(columns,list);
	}
	
	/**
	 * 1、成品库存汇总表
	 */
	public void exportECSStockExg(){
		List list = vfVerificationAction.findVFStockExgs(request, section);
		List<Object[]> columns = new ArrayList<Object[]>();
		columns.add(new Object[]{"成品料号", "ptNo", 16});
		columns.add(new Object[]{"工厂名称", "ptName", 16});
		columns.add(new Object[]{"工厂规格", "ptSpec", 16});
		columns.add(new Object[]{"工厂单位", "ptUnit", 8});
		columns.add(new Object[]{"库存数量", "storeAmount", 10});
		columns.add(new Object[]{"版本号", "version", 8});
		export(columns,list);
	}
	
	/**
	 * 2、原材料库存汇总表
	 */
	public void exportECSStockImg(){
		List list = vfVerificationAction.findVFStockImgs(request, section,null);
		List<Object[]> columns = new ArrayList<Object[]>();
		columns.add(new Object[]{"工厂料号", "ptNo", 8});
		columns.add(new Object[]{"工厂名称", "ptName", 10});
		columns.add(new Object[]{"工厂规格", "ptSpec", 10});
		columns.add(new Object[]{"工厂单位", "ptUnit", 6});
		columns.add(new Object[]{"库存数量", "storeAmount", 8});
		columns.add(new Object[]{"归并序号", "mergerNo", 8});
		columns.add(new Object[]{"报关商品名称", "hsName", 10});
		columns.add(new Object[]{"报关商品规格", "hsSpec", 10});
		columns.add(new Object[]{"计量单位", "hsUnit", 6});
		columns.add(new Object[]{"折算报关数量", "hsAmount", 8});
		columns.add(new Object[]{"折算系数", "unitConvert", 8});
		export(columns,list);
	}
	
	/**
	 * 3、在制品库存汇总表
	 */
	public void exportECSFinishingStock(){
		List list = vfVerificationAction.findVFFinishingStockAnalyse(request,section,null);;
		List<Object[]> columns = new ArrayList<Object[]>();
		columns.add(new Object[]{"归并序号", "mergerNo", "5"});
		columns.add(new Object[]{"报关名称", "hsName", "15"});
		columns.add(new Object[]{"报关规格", "hsSpec", "15"});
		columns.add(new Object[]{"计量单位", "hsUnit", "5"});
		columns.add(new Object[]{"原材料(A)", "imgHsAmount", "8"});
		columns.add(new Object[]{"成品(B)", "exgHsAmount", "8"});
		columns.add(new Object[]{"库存汇总(A+B)", "hsAmount", "12"});
		export(columns,list);
	}
	
	/**
	 * 4、在途原材料库存汇总表
	 */
	public void exportECSStockTravelingImg(){
//		List list = vfVerificationAction.findECSStockTravelingImgs(request, section,null);
		List<Object[]> columns = new ArrayList<Object[]>();
		columns.add(new Object[]{"料号", "ptNo", "11"});
		columns.add(new Object[]{"工厂名称", "ptName", "10"});
		columns.add(new Object[]{"工厂规格", "ptSpec", "10"});
		columns.add(new Object[]{"计量单位", "ptUnit", "5"});
		columns.add(new Object[]{"盘点库存", "ptAmount", "10"});
		columns.add(new Object[]{"料件备案序号", "seqNum", "11"});
		columns.add(new Object[]{"报关商品名称", "hsName", "10"});
		columns.add(new Object[]{"报关商品规格", "hsSpec", "10"});
		columns.add(new Object[]{"计量单位", "hsUnit", "5"});
		columns.add(new Object[]{"折算报关数量", "hsAmount", "10"});
		columns.add(new Object[]{"折算系数", "unitConvert", "10"});
		
//		export(columns,list);
	}
	
	/**
	 * 5、在途成品库存汇总表
	 */
	public void exportECSStockTravelingExg(){
//		List list = vfVerificationAction.findECSStockTravelingExgs(request, section,null);
		
		List<Object[]> columns = new ArrayList<Object[]>();
		columns.add(new Object[]{"料号", "ptNo", "11"});
		columns.add(new Object[]{"工厂名称", "ptName", "12"});
		columns.add(new Object[]{"工厂规格", "ptSpec", "12"});
		columns.add(new Object[]{"计量单位", "ptUnit", "10"});
		columns.add(new Object[]{"版本号", "version", "5"});
		columns.add(new Object[]{"盘点库存", "ptAmount", "10"});
		columns.add(new Object[]{"成品备案序号", "seqNum", "10"});
		columns.add(new Object[]{"版本号", "version", "5"});
		columns.add(new Object[]{"报关商品名称", "hsName", "12"});
		columns.add(new Object[]{"报关商品规格", "hsSpec", "12"});
		columns.add(new Object[]{"计量单位", "hsUnit", "5"});
		columns.add(new Object[]{"折算报关数量", "hsAmount", "10"});
		columns.add(new Object[]{"折算系数", "unitConvert", "10"});
		
//		export(columns,list);
	}
	
	/**
	 * 6、外发加工库存
	 */
	public void exportECSStockOutSendAnalyse(){
		List list = vfVerificationAction.findVFStockOutSendAnalyse(request, section,null);
		List<Object[]> columns = new ArrayList<Object[]>();
		columns.add(new Object[]{"备案序号", "mergerNo", "5"});
		columns.add(new Object[]{"报关名称", "hsName", "15"});
		columns.add(new Object[]{"报关规格", "hsSpec", "15"});
		columns.add(new Object[]{"计量单位", "hsUnit", "5"});
		columns.add(new Object[]{"原材料(A)", "imgHsAmount", "8"});
		columns.add(new Object[]{"半成品(B)", "semiExgHsAmount", "8"});
		columns.add(new Object[]{"成品(C)", "exgHsAmount", "8"});
		columns.add(new Object[]{"库存汇总(A+B+C)", "hsAmount", "12"});
		export(columns,list);
	}
	
	/**
	 * 7、内购库存
	 */
	public void exportECSStockBuyImg(){
		List list = vfVerificationAction.findVFStockBuyImgs(request, section,null);
		List<Object[]> columns = new ArrayList<Object[]>();
		columns.add(new Object[]{"料件料号", "ptNo", 10});
		columns.add(new Object[]{"工厂名称", "ptName", 10});
		columns.add(new Object[]{"工厂规格", "ptSpec", 10});
		columns.add(new Object[]{"工厂单位", "ptUnit", 10});
		columns.add(new Object[]{"库存数量", "storeAmount", 10});
		columns.add(new Object[]{"归并序号", "mergerNo", 10});  //mergerNo
		columns.add(new Object[]{"报关商品名称", "hsName", 10});
		columns.add(new Object[]{"报关商品规格", "hsSpec", 10});
		columns.add(new Object[]{"计量单位", "hsUnit", 10});
		columns.add(new Object[]{"折算报关数量", "hsAmount", 10});
		columns.add(new Object[]{"折算系数", "unitConvert", 10});
		export(columns,list);
	}
	
	/**
	 * 8、厂外库存
	 */
	public void exportECSStockOutFactoryImg(){
		List list = vfVerificationAction.findVFStockOutFactoryImgs(request, section,null);
		List<Object[]> columns = new ArrayList<Object[]>();
		columns.add(new Object[]{"料件料号", "ptNo", 10});
		columns.add(new Object[]{"工厂名称", "ptName", 10});
		columns.add(new Object[]{"工厂规格", "ptSpec", 10});
		columns.add(new Object[]{"工厂单位", "ptUnit", 10});
		columns.add(new Object[]{"库存数量", "storeAmount", 10});
		columns.add(new Object[]{"归并序号", "mergerNo", 10}); // mergerNo
		columns.add(new Object[]{"报关商品名称", "hsName", 10});
		columns.add(new Object[]{"报关商品规格", "hsSpec", 10});
		columns.add(new Object[]{"计量单位", "hsUnit", 10});
		columns.add(new Object[]{"折算报关数量", "hsAmount", 10});
		columns.add(new Object[]{"折算系数", "unitConvert", 10});
		export(columns,list);
	}
	
	/**
	 * 9、在产品库存
	 */
	public void exportECSStockProcessImg(){
		List list = vfVerificationAction.findVFStockProcessImgs(request, section,null);
		List<Object[]> columns = new ArrayList<Object[]>();
		columns.add(new Object[]{"工厂料号", "ptNo", 8});
		columns.add(new Object[]{"工厂名称", "ptName", 10});
		columns.add(new Object[]{"工厂规格", "ptSpec", 10});
		columns.add(new Object[]{"工厂单位", "ptUnit", 6});
		columns.add(new Object[]{"库存数量", "storeAmount", 8});
		columns.add(new Object[]{"归并序号", "mergerNo", 8});
		columns.add(new Object[]{"报关商品名称", "hsName", 10});
		columns.add(new Object[]{"报关商品规格", "hsSpec", 10});
		columns.add(new Object[]{"计量单位", "hsUnit", 6});
		columns.add(new Object[]{"折算报关数量", "hsAmount", 8});
		columns.add(new Object[]{"折算系数", "unitConvert", 8});
		export(columns,list);
	}
	
	/**
	 * 10、残次品库存
	 */
	public void exportECSBadStock(){
		List list = vfVerificationAction.findVFBadAnalyse(request, section,null);
		List<Object[]> columns = new ArrayList<Object[]>();
		columns.add(new Object[]{"归并序号", "mergerNo", "5"});
		columns.add(new Object[]{"报关名称", "hsName", "15"});
		columns.add(new Object[]{"报关规格", "hsSpec", "15"});
		columns.add(new Object[]{"计量单位", "hsUnit", "5"});
		columns.add(new Object[]{"原材料(A)", "imgHsAmount", "8"});
		columns.add(new Object[]{"半成品(B)", "semiExgHsAmount", "8"});
		columns.add(new Object[]{"成品(C)", "exgHsAmount", "8"});
		columns.add(new Object[]{"库存汇总(A+B+C)", "hsAmount", "12"});
		export(columns,list);
	}
	
	/**
	 * 11、半成品入库
	 */
	public void exportECSStockSemiExgPtHadStore(){
		List list = vfVerificationAction.findVFStockSemiExgHadStore(request, section);
		List<Object[]> columns = new ArrayList<Object[]>();
		columns.add(new Object[]{"成品料号", "ptNo", 16});
		columns.add(new Object[]{"工厂名称", "ptName", 16});
		columns.add(new Object[]{"工厂规格", "ptSpec", 16});
		columns.add(new Object[]{"工厂单位", "ptUnit", 8});
		columns.add(new Object[]{"库存数量", "storeAmount", 10});
		columns.add(new Object[]{"版本号", "version", 8});
		export(columns,list);
	}
	
	
	/**
	 * 1、成品库存折算表
	 */
	public void exportECSStockExgConvert(){
		List list = vfVerificationAction.findVFStockExgConverts(request, section,null);
		List<Object[]> columns = new ArrayList<Object[]>();
		columns.add(new Object[]{"成品料号", "stockExg.ptNo",10});
		columns.add(new Object[]{"版本号", "stockExg.version", 8});
		columns.add(new Object[]{"库存数量", "stockExg.storeAmount", 6});
		columns.add(new Object[]{"料件料号", "ptNo", 10});
		columns.add(new Object[]{"工厂名称", "ptName", 10});
		columns.add(new Object[]{"工厂规格", "ptSpec", 10});
		columns.add(new Object[]{"工厂单位", "ptUnit", 5});
		columns.add(new Object[]{"单耗", "unitWaste", 10});
		columns.add(new Object[]{"损耗", "waste", 10});
		columns.add(new Object[]{"单项用量", "unitUsed", 10});
		columns.add(new Object[]{"成品耗用量", "storeAmount", 10});
		columns.add(new Object[]{"归并序号", "mergerNo", 6}); 
		columns.add(new Object[]{"报关商品名称", "hsName", 10});
		columns.add(new Object[]{"报关商品规格", "hsSpec", 15});
		columns.add(new Object[]{"计量单位", "hsUnit", 8});
		columns.add(new Object[]{"折算报关数量", "hsAmount", 15});
		columns.add(new Object[]{"折算系数", "unitConvert", 10});
		export(columns,list);
	}
	/**
	 * 2、在制品库存折算表
	 */
	public void exportECSFinishingStockConvert(){
		List list = vfVerificationAction.findVFFinishingExgConverts(request, section,null);
		List<Object[]> columns = new ArrayList<Object[]>();
		columns.add(new Object[]{"成品料号", "stockExg.ptNo", 10});
		columns.add(new Object[]{"版本号", "stockExg.version", 8});
		columns.add(new Object[]{"库存数量", "stockExg.storeAmount", 6});
		columns.add(new Object[]{"料件料号", "ptNo", 10});
		columns.add(new Object[]{"工厂名称", "ptName", 10});
		columns.add(new Object[]{"工厂规格", "ptSpec", 10});
		columns.add(new Object[]{"工厂单位", "ptUnit", 5});
		columns.add(new Object[]{"单耗", "unitWaste", 10});
		columns.add(new Object[]{"损耗", "waste", 10});
		columns.add(new Object[]{"单项用量", "unitUsed", 10});
		columns.add(new Object[]{"成品耗用量", "storeAmount", 10});
		columns.add(new Object[]{"归并序号", "mergerNo", 6});
		columns.add(new Object[]{"报关商品名称", "hsName", 10});
		columns.add(new Object[]{"报关商品规格", "hsSpec", 15});
		columns.add(new Object[]{"计量单位", "hsUnit", 8});
		columns.add(new Object[]{"折算报关数量", "hsAmount", 15});
		columns.add(new Object[]{"折算系数", "unitConvert", 10});
		export(columns,list);
		System.out.println("--==--==--=111=--==--==--");
	}
	
	/**
	 * 在产品折算表
	 */
	public void exportECSStockProcessImgsExgConvert(){
		List list = vfVerificationAction.convertVFStockProcessImgs(request, section);
		List<Object[]> columns = new ArrayList<Object[]>();
		columns.add(new Object[]{"工厂料号", "ptNo", "11"});
		columns.add(new Object[]{"工厂名称", "ptName", "10"});
		columns.add(new Object[]{"工厂规格", "ptSpec", "12"});
		columns.add(new Object[]{"计量单位", "ptUnit", "5"});
		columns.add(new Object[]{"库存数量", "ptAmount", "10"});
		columns.add(new Object[]{"料件备案序号", "seqNum", "11"});
		columns.add(new Object[]{"报关商品名称", "hsName", "10"});
		columns.add(new Object[]{"报关商品规格", "hsSpec", "12"});
		columns.add(new Object[]{"计量单位", "hsUnit", "5"});
		columns.add(new Object[]{"折算报关数量", "hsAmount", "10"});
		columns.add(new Object[]{"折算系数", "unitConvert", "6"});
		export(columns,list);
	}
	/**
	 * 3、在途成品库存折算表
	 */
	public void exportECSStockTravelingExgConvert(){
//		List list = vfVerificationAction.findECSStockTravelingExgResolves(request, section,null);
		List<Object[]> columns = new ArrayList<Object[]>();
		columns.add(new Object[]{"成品料号", "baseExg.ptNo", "11"});
		columns.add(new Object[]{"成品序号", "baseExg.seqNum", "5"});
		columns.add(new Object[]{"成品版本", "baseExg.version", "5"});
		columns.add(new Object[]{"料件序号", "seqNum", "5"});
		columns.add(new Object[]{"料件名称", "hsName", "10"});
		columns.add(new Object[]{"型号规格", "hsSpec", "16"});
		columns.add(new Object[]{"计量单位", "hsUnit", "6"});
		columns.add(new Object[]{"单耗", "unitWaste", "6"});
		columns.add(new Object[]{"损耗", "waste", "6"});
		columns.add(new Object[]{"单项用量", "unitUsed", "6"});
		columns.add(new Object[]{"总耗用", "hsAmount", "10"});
//		export(columns,list);
	}
	/**
	 * 4、外发库存折算表
	 */
	public void exportECSStockOutSendAnalyseConvert(){
		List list = vfVerificationAction.findVFStockOutSendExgsConvert(request, section, null);
		List<Object[]> columns = new ArrayList<Object[]>();
		columns.add(new Object[]{"成品料号", "stockExg.ptNo", 10});
		columns.add(new Object[]{"版本号", "stockExg.version", 10});
		columns.add(new Object[]{"库存数量", "stockExg.storeAmount", 10});
		columns.add(new Object[]{"料件料号", "ptNo", 10});
		columns.add(new Object[]{"工厂名称", "ptName", 10});
		columns.add(new Object[]{"工厂规格", "ptSpec", 10});
		columns.add(new Object[]{"工厂单位", "ptUnit", 5});
		columns.add(new Object[]{"库存数量", "storeAmount", 10});
		columns.add(new Object[]{"单耗", "unitWaste", 10});
		columns.add(new Object[]{"损耗", "waste", 10});
		columns.add(new Object[]{"单项用量", "unitUsed", 10});
		columns.add(new Object[]{"成品耗用量", "storeAmount", 10});
		columns.add(new Object[]{"归并序号", "mergerNo", 10}); // mergerNo
		columns.add(new Object[]{"报关商品名称", "hsName", 10});
		columns.add(new Object[]{"报关商品规格", "hsSpec", 10});
		columns.add(new Object[]{"计量单位", "hsUnit", 5});
		columns.add(new Object[]{"折算报关数量", "hsAmount", 10});
		columns.add(new Object[]{"折算系数", "unitConvert", 10});
		export(columns,list);
	}
	
	/**
	 * 5、外发库存（半成品）
	 */
	public void exportECSStockOutSendSemiExgConvert(){
		List list = vfVerificationAction.findVFStockOutSendSemiExgConverts(request,section, null);
		List<Object[]> columns = new ArrayList<Object[]>();
		columns.add(new Object[]{"成品料号", "stockExg.ptNo", 10});
		columns.add(new Object[]{"版本号", "stockExg.version", 8});
		columns.add(new Object[]{"库存数量", "stockExg.storeAmount", 6});
		columns.add(new Object[]{"料件料号", "ptNo", 10});
		columns.add(new Object[]{"工厂名称", "ptName", 10});
		columns.add(new Object[]{"工厂规格", "ptSpec", 10});
		columns.add(new Object[]{"工厂单位", "ptUnit", 5});
		columns.add(new Object[]{"单耗", "unitWaste", 10});
		columns.add(new Object[]{"损耗", "waste", 10});
		columns.add(new Object[]{"单项用量", "unitUsed", 10});
		columns.add(new Object[]{"成品耗用量", "storeAmount", 10});
		columns.add(new Object[]{"归并序号", "mergerNo", 6});
		columns.add(new Object[]{"报关商品名称", "hsName", 10});
		columns.add(new Object[]{"报关商品规格", "hsSpec", 15});
		columns.add(new Object[]{"计量单位", "hsUnit", 8});
		columns.add(new Object[]{"折算报关数量", "hsAmount", 15});
		columns.add(new Object[]{"折算系数", "unitConvert", 10});
		export(columns,list);
	}
	
	/**
	 * 6、残次品库存折算表(半成品)
	 */
	public void exportECSSemiFinishedExgConvert(){
		List list = vfVerificationAction.findVFSemiBadExgConverts(request, section,null);
		List<Object[]> columns = new ArrayList<Object[]>();
		columns.add(new Object[]{"成品料号", "stockExg.ptNo", 10});
		columns.add(new Object[]{"版本号", "stockExg.version", 8});
		columns.add(new Object[]{"库存数量", "stockExg.storeAmount", 6});
		columns.add(new Object[]{"料件料号", "ptNo", 10});
		columns.add(new Object[]{"工厂名称", "ptName", 10});
		columns.add(new Object[]{"工厂规格", "ptSpec", 10});
		columns.add(new Object[]{"工厂单位", "ptUnit", 5});
		columns.add(new Object[]{"单耗", "unitWaste", 10});
		columns.add(new Object[]{"损耗", "waste", 10});
		columns.add(new Object[]{"单项用量", "unitUsed", 10});
		columns.add(new Object[]{"成品耗用量", "storeAmount", 10});
		columns.add(new Object[]{"归并序号", "mergerNo", 6});
		columns.add(new Object[]{"报关商品名称", "hsName", 10});
		columns.add(new Object[]{"报关商品规格", "hsSpec", 15});
		columns.add(new Object[]{"计量单位", "hsUnit", 8});
		columns.add(new Object[]{"折算报关数量", "hsAmount", 15});
		columns.add(new Object[]{"折算系数", "unitConvert", 10});
		export(columns,list);
	}
	
	/**
	 * 7、残次品库存折算表(成品)
	 */
	public void exportECSFinishedExgConvert(){
		List list = vfVerificationAction.findVFBadExgConverts(request, section,null);
		List<Object[]> columns = new ArrayList<Object[]>();
		columns.add(new Object[]{"成品料号", "stockExg.ptNo", 10});
		columns.add(new Object[]{"版本号", "stockExg.version", 8});;
		columns.add(new Object[]{"库存数量", "stockExg.storeAmount", 6});
		columns.add(new Object[]{"料件料号", "ptNo", 10});
		columns.add(new Object[]{"工厂名称", "ptName", 10});
		columns.add(new Object[]{"工厂规格", "ptSpec", 10});
		columns.add(new Object[]{"工厂单位", "ptUnit", 5});
		columns.add(new Object[]{"单耗", "unitWaste", 10});
		columns.add(new Object[]{"损耗", "waste", 10});
		columns.add(new Object[]{"单项用量", "unitUsed", 10});
		columns.add(new Object[]{"成品耗用量", "storeAmount", 10});
		columns.add(new Object[]{"归并序号", "mergerNo", 6}); 
		columns.add(new Object[]{"报关商品名称", "hsName", 10});
		columns.add(new Object[]{"报关商品规格", "hsSpec", 15});
		columns.add(new Object[]{"计量单位", "hsUnit", 8});
		columns.add(new Object[]{"折算报关数量", "hsAmount", 15});
		columns.add(new Object[]{"折算系数", "unitConvert", 10});
		export(columns,list);
	}
	
	/**
	 * 8、半成品库存折算表
	 */
	public void exportECSStockSemiExgPtHadStoreConvert(){
		List list = vfVerificationAction.findVFStockSemiExgHadStoreConverts(request, section,null);
		List<Object[]> columns = new ArrayList<Object[]>();
		columns.add(new Object[]{"成品料号", "stockExg.ptNo", 10});
		columns.add(new Object[]{"版本号", "stockExg.version", 8});
		columns.add(new Object[]{"库存数量", "stockExg.storeAmount", 6});
		columns.add(new Object[]{"料件料号", "ptNo", 10});
		columns.add(new Object[]{"工厂名称", "ptName", 10});
		columns.add(new Object[]{"工厂规格", "ptSpec", 10});
		columns.add(new Object[]{"工厂单位", "ptUnit", 5});
		columns.add(new Object[]{"单耗", "unitWaste", 10});
		columns.add(new Object[]{"损耗", "waste", 10});
		columns.add(new Object[]{"单项用量", "unitUsed", 10});
		columns.add(new Object[]{"成品耗用量", "storeAmount", 10});
		columns.add(new Object[]{"归并序号", "mergerNo", 6}); 
		columns.add(new Object[]{"报关商品名称", "hsName", 10});
		columns.add(new Object[]{"报关商品规格", "hsSpec", 15});
		columns.add(new Object[]{"计量单位", "hsUnit", 8});
		columns.add(new Object[]{"折算报关数量", "hsAmount", 15});
		columns.add(new Object[]{"折算系数", "unitConvert", 10});
		export(columns,list);
	}
	
	/**
	 * 短溢表
	 */
	public void exportECSAnalyses(){
		List list = vfVerificationAction.findVFAnalyses(request, section,null);
		List<Object[]> columns = new ArrayList<Object[]>();
		columns.add(new Object[]{"归并序号  ", "mergerNo", 8});
		columns.add(new Object[]{"商品编码  ", "complex", 8});
		columns.add(new Object[]{"名称", "hsName", 10});
		columns.add(new Object[]{"规格", "hsSpec", 10});
		columns.add(new Object[]{"单位", "hsUnit", 4});
		columns.add(new Object[]{"进口数量(A)", "impAmount", 10});
		columns.add(new Object[]{"出口总耗用(B)", "expAmount", 10});
		columns.add(new Object[]{"合同应剩余(C=A-B)", "contractLeaveNum",12});
		columns.add(new Object[]{"料件库存(D)", "stockAmountImg", 8});
		columns.add(new Object[]{"在产品库存(E)", "stockAmountProcessImg", 8});
		columns.add(new Object[]{"成品库存(F)", "stockAmountExg", 8});
		columns.add(new Object[]{"外发库存(G)", "stockAmountOutSend", 8});
		columns.add(new Object[]{"厂外存放库存(H)", "stockAmountOutFactory", 12});
		columns.add(new Object[]{"内购库存(I)", "stockAmountBuy", 8});
		columns.add(new Object[]{"在途库存(J)", "stockAmountTraveling", 8});
		columns.add(new Object[]{"库存数量(K=D+E+F+G+H+I+J)", "stockTotalAmount", 18});
		columns.add(new Object[]{"已转厂未收货(L)", "unReceiveHadTransNum", 11});
		columns.add(new Object[]{"已送货未转厂(M)", "unTransHadSendNum", 11});
		columns.add(new Object[]{"已转厂未送货(N)", "unSendHadTransNum", 11});
		columns.add(new Object[]{"已收货未转厂(O)", "unTransHadReceiveNum", 11});
		columns.add(new Object[]{"结转差额(P=L+M-N-O)", "transferDiffNum", 12});
		columns.add(new Object[]{"短溢数量(Q=K-C+10)", "overOrshortNum", 12});
		columns.add(new Object[]{"单价(R)", "price", 8});
		columns.add(new Object[]{"关税(S=R*Q*税率)", "usd", 12});
		columns.add(new Object[]{"增值税(T=(R*Q+S)*17%)", "usdAdd", 15});
		export(columns,list);
		
	}
}
