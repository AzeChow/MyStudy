package com.bestway.bcus.client.checkstock;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.bestway.bcus.checkstock.action.ECSCheckStockAction;
import com.bestway.bcus.checkstock.entity.ECSAttachmentManagement;
import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;

public class AttachmentExportExcel {
	private ECSCheckStockAction checkStockAction = null;
	private Request request = null;
	private ECSSection section = null;
	private File file;
	private ECSCheckStockAction ecsCheckStockAction = null;
	ECSAttachmentManagement attachment = null;
	public AttachmentExportExcel(ECSAttachmentManagement attachment,File file){
		checkStockAction = (ECSCheckStockAction) CommonVars.getApplicationContext().getBean("ecsCheckStockAction");
		ecsCheckStockAction = (ECSCheckStockAction) CommonVars.getApplicationContext().getBean("ecsCheckStockAction");
		request = new Request(CommonVars.getCurrUser());
		section = attachment.getEcssection();
		this.attachment = attachment;
		this.file = file;
	}
	
	public void exportExcel(){
		System.out.println(attachment);
		if(ECSAttachmentManagement.DECLARATION_COMMINFO_EXGX_IMP.equals(attachment.getOperateState())){
			exportECSDeclarationCommInfoExgxImp();
		}else if(ECSAttachmentManagement.DECLARETION_COMMINFO_EXGX_EXP.equals(attachment.getOperateState())){
			exportECSDeclarationCommInfoExgxExp();
		}else if(ECSAttachmentManagement.STOCK_EXG.equals(attachment.getOperateState())){
			exportECSStockExg();
		}else if(ECSAttachmentManagement.STOCK_IMG.equals(attachment.getOperateState())){
			exportECSStockImg();
		}else if(ECSAttachmentManagement.FINISHING_STOCK.equals(attachment.getOperateState())){
			exportECSFinishingStock();
		}else if(ECSAttachmentManagement.STOCK_TRAVELING_IMG.equals(attachment.getOperateState())){
			exportECSStockTravelingImg();
		}else if(ECSAttachmentManagement.STOCK_TRAVELING_EXG.equals(attachment.getOperateState())){
			exportECSStockTravelingExg();
		}else if(ECSAttachmentManagement.STOCK_OUT_SEND_ANALYSE.equals(attachment.getOperateState())){
			exportECSStockOutSendAnalyse();
		}else if(ECSAttachmentManagement.STOCK_BUY_IMG.equals(attachment.getOperateState())){
			exportECSStockBuyImg();
		}else if(ECSAttachmentManagement.STOCK_OUT_FACTORY_IMG.equals(attachment.getOperateState())){
			exportECSStockOutFactoryImg();
		}else if(ECSAttachmentManagement.STOCK_PROCESS_IMG.equals(attachment.getOperateState())){
			exportECSStockProcessImg();
		}else if(ECSAttachmentManagement.BAD_STOCK.equals(attachment.getOperateState())){
			exportECSBadStock();
		}else if(ECSAttachmentManagement.STOCK_SEMI_EXG_PT_HAD_STORE.equals(attachment.getOperateState())){
			exportECSStockSemiExgPtHadStore();
		}else if(ECSAttachmentManagement.STOCK_EXG_CONVERT.equals(attachment.getOperateState())){
			exportECSStockExgConvert();
		}else if(ECSAttachmentManagement.FINISHING_STOCK_CONVERT.equals(attachment.getOperateState())){
			exportECSFinishingStockConvert();
		}else if(ECSAttachmentManagement.STOCK_TRAVELING_EXG_CONVERT.equals(attachment.getOperateState())){
			exportECSStockTravelingExgConvert();
		}else if(ECSAttachmentManagement.STOCK_OUT_SEND_ANALYSE_CONVERT.equals(attachment.getOperateState())){
			exportECSStockOutSendAnalyseConvert();
		}else if(ECSAttachmentManagement.SEMI_FINISHED_EXG.equals(attachment.getOperateState())){
			exportECSSemiFinishedExgConvert();
		}else if(ECSAttachmentManagement.FINISHED_EXG.equals(attachment.getOperateState())){
			exportECSFinishedExgConvert();
		}else if(ECSAttachmentManagement.STOCK_SEMI_EXG_PT_HAD_STORE_CONVERT.equals(attachment.getOperateState())){
			exportECSStockSemiExgPtHadStoreConvert();
		}else if(ECSAttachmentManagement.ANALYSES.equals(attachment.getOperateState())){
			exportECSAnalyses();
		}else if(ECSAttachmentManagement.STOCK_OUT_SEND_SEMI_EXG.equals(attachment.getOperateState())){
			exportECSStockOutSendSemiExgConvert();
		}
	}
	
	public void export(List<String[]> columns,List list){
		ExportExcel exportExecl = new ExportExcel();
		exportExecl.setColumns(columns);
		exportExecl.setDataSource(list);
		exportExecl.export(file);
	}
	
	/**
	 * 2、稽查期内进口报关单
	 */
	public void exportECSDeclarationCommInfoExgxImp(){
		if(section==null){
			return;
		}
		List list = checkStockAction.findECSDeclarationImgBySection(request, section, null);
		List<String[]> columns = new ArrayList<String[]>();
		columns.add(new String[]{"账册编号", "emsNo", "14"});
		columns.add(new String[]{"备案序号", "seqNum", "5"});
		columns.add(new String[]{"报关单号", "customsDeclarationCode", "14"});
		columns.add(new String[]{"申报日期", "declarationDate", "9"});
		columns.add(new String[]{"料件名称", "commName", "12"});
		columns.add(new String[]{"型号规格", "commSpec", "14"});
		columns.add(new String[]{"计量单位", "unit", "6"});
		columns.add(new String[]{"商品数量", "commAmount", "9"});
		columns.add(new String[]{"单价", "commUnitPrice", "9"});
		columns.add(new String[]{"总金额", "totalMoney", "9"});
		columns.add(new String[]{"进出口类型", "impExpType", "9"});
		columns.add(new String[]{"贸易方式", "tradeMode", "11"});
		export(columns,list);
	}
	
	/**
	 * 3、稽查期内出口报关单
	 */
	public void exportECSDeclarationCommInfoExgxExp(){
		if(section==null){
			return;
		}
		List list =checkStockAction.findECSDeclarationExgBySection(request, section, null);
		
		List<String[]> columns = new ArrayList<String[]>();
		columns.add(new String[]{"账册编号", "emsNo", "10"});
		columns.add(new String[]{"备案序号", "seqNum", "5"});
		columns.add(new String[]{"版本号", "version", "5"});
		columns.add(new String[]{"报关单号", "customsDeclarationCode", "14"});
		columns.add(new String[]{"申报日期", "declarationDate", "9"});
		columns.add(new String[]{"成品名称", "commName", "12"});
		columns.add(new String[]{"型号规格", "commSpec", "14"});
		columns.add(new String[]{"计量单位", "unit", "6"});
		columns.add(new String[]{"商品数量", "commAmount", "9"});
		columns.add(new String[]{"单价", "commUnitPrice", "9"});
		columns.add(new String[]{"总金额", "totalMoney", "9"});
		columns.add(new String[]{"进出口类型", "impExpType", "9"});
		columns.add(new String[]{"贸易方式", "tradeMode", "9"});
		
		export(columns,list);
	}
	
	/**
	 * 1、成品库存汇总表
	 */
	public void exportECSStockExg(){
		List list = ecsCheckStockAction.findECSStockExgs(request, section,null);
		List<String[]> columns = new ArrayList<String[]>();
		columns.add(new String[]{"料号", "ptNo", "11"});
		columns.add(new String[]{"工厂名称", "ptName", "12"});
		columns.add(new String[]{"工厂规格", "ptSpec", "12"});
		columns.add(new String[]{"计量单位", "ptUnit", "10"});
		columns.add(new String[]{"版本号", "version", "5"});
		columns.add(new String[]{"盘点库存", "ptAmount", "10"});
		columns.add(new String[]{"成品备案序号", "seqNum", "10"});
		columns.add(new String[]{"版本号", "version", "5"});
		columns.add(new String[]{"报关商品名称", "hsName", "12"});
		columns.add(new String[]{"报关商品规格", "hsSpec", "12"});
		columns.add(new String[]{"计量单位", "hsUnit", "5"});
		columns.add(new String[]{"折算报关数量", "hsAmount", "10"});
		columns.add(new String[]{"折算系数", "unitConvert", "10"});
		export(columns,list);
	}
	
	/**
	 * 2、原材料库存汇总表
	 */
	public void exportECSStockImg(){
		List list = ecsCheckStockAction.findECSStockImgs(request, section,null);
		List<String[]> columns = new ArrayList<String[]>();
		columns.add(new String[]{"料号", "ptNo", "11"});
		columns.add(new String[]{"工厂名称", "ptName", "10"});
		columns.add(new String[]{"工厂规格", "ptSpec", "12"});
		columns.add(new String[]{"计量单位", "ptUnit", "5"});
		columns.add(new String[]{"库存数量", "ptAmount", "10"});
		columns.add(new String[]{"料件备案序号", "seqNum", "8"});
		columns.add(new String[]{"报关商品名称", "hsName", "10"});
		columns.add(new String[]{"报关商品规格", "hsSpec", "12"});
		columns.add(new String[]{"计量单位", "hsUnit", "5"});
		columns.add(new String[]{"折算报关数量", "hsAmount", "10"});
		columns.add(new String[]{"折算系数", "unitConvert", "6"});
		export(columns,list);
	}
	
	/**
	 * 3、在制品库存汇总表
	 */
	public void exportECSFinishingStock(){
		List list = ecsCheckStockAction.findECSFinishingStockResolves(request,section,null);;
		List<String[]> columns = new ArrayList<String[]>();
		columns.add(new String[]{"备案序号", "seqNum", "5"});
		columns.add(new String[]{"报关名称", "hsName", "15"});
		columns.add(new String[]{"报关规格", "hsSpec", "15"});
		columns.add(new String[]{"计量单位", "hsUnit", "5"});
		columns.add(new String[]{"原材料(A)", "imgHsAmount", "8"});
		columns.add(new String[]{"成品(B)", "exgHsAmount", "8"});
		columns.add(new String[]{"库存汇总(A+B)", "hsAmount", "12"});
		export(columns,list);
	}
	
	/**
	 * 4、在途原材料库存汇总表
	 */
	public void exportECSStockTravelingImg(){
		List list = ecsCheckStockAction.findECSStockTravelingImgs(request, section,null);
		List<String[]> columns = new ArrayList<String[]>();
		columns.add(new String[]{"料号", "ptNo", "11"});
		columns.add(new String[]{"工厂名称", "ptName", "10"});
		columns.add(new String[]{"工厂规格", "ptSpec", "10"});
		columns.add(new String[]{"计量单位", "ptUnit", "5"});
		columns.add(new String[]{"盘点库存", "ptAmount", "10"});
		columns.add(new String[]{"料件备案序号", "seqNum", "11"});
		columns.add(new String[]{"报关商品名称", "hsName", "10"});
		columns.add(new String[]{"报关商品规格", "hsSpec", "10"});
		columns.add(new String[]{"计量单位", "hsUnit", "5"});
		columns.add(new String[]{"折算报关数量", "hsAmount", "10"});
		columns.add(new String[]{"折算系数", "unitConvert", "10"});
		
		export(columns,list);
	}
	
	/**
	 * 5、在途成品库存汇总表
	 */
	public void exportECSStockTravelingExg(){
		List list = ecsCheckStockAction.findECSStockTravelingExgs(request, section,null);
		
		List<String[]> columns = new ArrayList<String[]>();
		columns.add(new String[]{"料号", "ptNo", "11"});
		columns.add(new String[]{"工厂名称", "ptName", "12"});
		columns.add(new String[]{"工厂规格", "ptSpec", "12"});
		columns.add(new String[]{"计量单位", "ptUnit", "10"});
		columns.add(new String[]{"版本号", "version", "5"});
		columns.add(new String[]{"盘点库存", "ptAmount", "10"});
		columns.add(new String[]{"成品备案序号", "seqNum", "10"});
		columns.add(new String[]{"版本号", "version", "5"});
		columns.add(new String[]{"报关商品名称", "hsName", "12"});
		columns.add(new String[]{"报关商品规格", "hsSpec", "12"});
		columns.add(new String[]{"计量单位", "hsUnit", "5"});
		columns.add(new String[]{"折算报关数量", "hsAmount", "10"});
		columns.add(new String[]{"折算系数", "unitConvert", "10"});
		
		export(columns,list);
	}
	
	/**
	 * 6、外发加工库存
	 */
	public void exportECSStockOutSendAnalyse(){
		List list = ecsCheckStockAction.findECSStockOutSendAnalyses(request, section,null);
		List<String[]> columns = new ArrayList<String[]>();
		columns.add(new String[]{"备案序号", "seqNum", "5"});
		columns.add(new String[]{"报关名称", "hsName", "15"});
		columns.add(new String[]{"报关规格", "hsSpec", "15"});
		columns.add(new String[]{"计量单位", "hsUnit", "5"});
		columns.add(new String[]{"原材料(A)", "imgHsAmount", "8"});
		columns.add(new String[]{"半成品(B)", "semiExgHsAmount", "8"});
		columns.add(new String[]{"成品(C)", "exgHsAmount", "8"});
		columns.add(new String[]{"库存汇总(A+B+C)", "hsAmount", "12"});
		
		export(columns,list);
	}
	
	/**
	 * 7、内购库存
	 */
	public void exportECSStockBuyImg(){
		List list = ecsCheckStockAction.findECSStockBuyImgs(request, section,null);
		List<String[]> columns = new ArrayList<String[]>();
		columns.add(new String[]{"料号", "ptNo", "11"});
		columns.add(new String[]{"工厂名称", "ptName", "10"});
		columns.add(new String[]{"工厂规格", "ptSpec", "10"});
		columns.add(new String[]{"计量单位", "ptUnit", "5"});
		columns.add(new String[]{"库存数量", "ptAmount", "10"});
		columns.add(new String[]{"料件备案序号", "seqNum", "11"});
		columns.add(new String[]{"报关商品名称", "hsName", "10"});
		columns.add(new String[]{"报关商品规格", "hsSpec", "10"});
		columns.add(new String[]{"计量单位", "hsUnit", "5"});
		columns.add(new String[]{"折算报关数量", "hsAmount", "10"});
		columns.add(new String[]{"折算系数", "unitConvert", "10"});
		export(columns,list);
	}
	
	/**
	 * 8、厂外库存
	 */
	public void exportECSStockOutFactoryImg(){
		List list = ecsCheckStockAction.findECSStockOutSendAnalyses(request, section,null);
		List<String[]> columns = new ArrayList<String[]>();
		columns.add(new String[]{"备案序号", "seqNum", "5"});
		columns.add(new String[]{"报关名称", "hsName", "15"});
		columns.add(new String[]{"报关规格", "hsSpec", "15"});
		columns.add(new String[]{"计量单位", "hsUnit", "5"});
		columns.add(new String[]{"原材料(A)", "imgHsAmount", "8"});
		columns.add(new String[]{"半成品(B)", "semiExgHsAmount", "8"});
		columns.add(new String[]{"成品(C)", "exgHsAmount", "8"});
		columns.add(new String[]{"库存汇总(A+B+C)", "hsAmount", "12"});
		export(columns,list);
	}
	
	/**
	 * 9、在产品库存
	 */
	public void exportECSStockProcessImg(){
		List list = ecsCheckStockAction.findECSStockProcessImgs(request, section,null);
		List<String[]> columns = new ArrayList<String[]>();
		columns.add(new String[]{"料号", "ptNo", "11"});
		columns.add(new String[]{"工厂名称", "ptName", "10"});
		columns.add(new String[]{"工厂规格", "ptSpec", "12"});
		columns.add(new String[]{"计量单位", "ptUnit", "5"});
		columns.add(new String[]{"库存数量", "ptAmount", "10"});
		columns.add(new String[]{"料件备案序号", "seqNum", "11"});
		columns.add(new String[]{"报关商品名称", "hsName", "10"});
		columns.add(new String[]{"报关商品规格", "hsSpec", "12"});
		columns.add(new String[]{"计量单位", "hsUnit", "5"});
		columns.add(new String[]{"折算报关数量", "hsAmount", "10"});
		columns.add(new String[]{"折算系数", "unitConvert", "6"});
		export(columns,list);
	}
	
	/**
	 * 10、残次品库存
	 */
	public void exportECSBadStock(){
		List list = ecsCheckStockAction.findECSBadStockResolves(request, section,null);
		List<String[]> columns = new ArrayList<String[]>();
		columns.add(new String[]{"备案序号", "seqNum", "5"});
		columns.add(new String[]{"报关名称", "hsName", "15"});
		columns.add(new String[]{"报关规格", "hsSpec", "15"});
		columns.add(new String[]{"计量单位", "hsUnit", "5"});
		columns.add(new String[]{"原材料(A)", "imgHsAmount", "8"});
		columns.add(new String[]{"半成品(B)", "semiFinishedHsAmount", "8"});
		columns.add(new String[]{"成品(C)", "finishedHsAmount", "8"});
		columns.add(new String[]{"库存汇总(A+B+C)", "hsAmount", "12"});
		export(columns,list);
	}
	
	/**
	 * 11、半成品入库
	 */
	public void exportECSStockSemiExgPtHadStore(){
		List list = ecsCheckStockAction.findECSStockSemiExgPtHadStores(request, section, null);
		List<String[]> columns = new ArrayList<String[]>();
		columns.add(new String[]{"成品料号", "ptNo", "16"});
		columns.add(new String[]{"工厂名称", "ptName", "16"});
		columns.add(new String[]{"工厂规格", "ptSpec", "16"});
		columns.add(new String[]{"工厂单位", "ptUnit", "8"});
		columns.add(new String[]{"库存数量", "storeAmount", "10"});
		columns.add(new String[]{"版本号", "version", "8"});
		export(columns,list);
	}
	
	
	/**
	 * 1、成品库存折算表
	 */
	public void exportECSStockExgConvert(){
		System.out.println("section"+section+"request"+request);
		List list = ecsCheckStockAction.findECSStockExgResolves(request, section,null);
		System.out.println("==========="+list.size());
		List<String[]> columns = new ArrayList<String[]>();
		columns.add(new String[]{"成品料号", "baseExg.ptNo", "11"});
		columns.add(new String[]{"成品序号", "baseExg.seqNum", "5"});
		columns.add(new String[]{"成品版本", "baseExg.version", "5"});
		columns.add(new String[]{"料件序号", "seqNum", "5"});
		columns.add(new String[]{"料件名称", "hsName", "10"});
		columns.add(new String[]{"型号规格", "hsSpec", "16"});
		columns.add(new String[]{"计量单位", "hsUnit", "6"});
		columns.add(new String[]{"单耗", "unitWaste", "6"});
		columns.add(new String[]{"损耗", "waste", "6"});
		columns.add(new String[]{"单项用量", "unitUsed", "6"});
		columns.add(new String[]{"总耗用", "hsAmount", "10"});
		
		export(columns,list);
	}
	/**
	 * 2、在制品库存折算表
	 */
	public void exportECSFinishingStockConvert(){
		List list = ecsCheckStockAction.findECSFinishingExgResolves(request, section,null);
		List<String[]> columns = new ArrayList<String[]>();
		columns.add(new String[]{"成品料号", "baseStockExg.ptNo", "15"});
		columns.add(new String[]{"版本号", "baseStockExg.version", "6"});
		columns.add(new String[]{"库存数量", "baseStockExg.storeAmount", "6"});
		columns.add(new String[]{"料件料号", "ptNo", "15"});
		columns.add(new String[]{"工厂名称", "ptName", "10"});
		columns.add(new String[]{"工厂规格", "ptSpec", "10"});
		columns.add(new String[]{"工厂单位", "ptUnit", "5"});
		columns.add(new String[]{"单耗", "unitWaste", "10"});
		columns.add(new String[]{"损耗", "waste", "10"});
		columns.add(new String[]{"单项用量", "unitUsed", "10"});
		columns.add(new String[]{"成品耗用量", "usedAmount", "10"});
		columns.add(new String[]{"料件备案序号", "seqNum", "10"});
		columns.add(new String[]{"报关商品名称", "hsName", "15"});
		columns.add(new String[]{"报关商品规格", "hsSpec", "15"});
		columns.add(new String[]{"计量单位", "hsUnit", "8"});
		columns.add(new String[]{"折算报关数量", "hsAmount", "10"});
		columns.add(new String[]{"折算系数", "unitConvert", "10"});
		export(columns,list);
	}
	
	/**
	 * 在产品折算表
	 */
	public void exportECSStockProcessImgsExgConvert(){
//		List list = ecsCheckStockAction.convertECSStockProcessImgs(request, section);
//		List<String[]> columns = new ArrayList<String[]>();
//		columns.add(new String[]{"料号", "ptNo", "11"});
//		columns.add(new String[]{"工厂名称", "ptName", "10"});
//		columns.add(new String[]{"工厂规格", "ptSpec", "12"});
//		columns.add(new String[]{"计量单位", "ptUnit", "5"});
//		columns.add(new String[]{"库存数量", "ptAmount", "10"});
//		columns.add(new String[]{"料件备案序号", "seqNum", "11"});
//		columns.add(new String[]{"报关商品名称", "hsName", "10"});
//		columns.add(new String[]{"报关商品规格", "hsSpec", "12"});
//		columns.add(new String[]{"计量单位", "hsUnit", "5"});
//		columns.add(new String[]{"折算报关数量", "hsAmount", "10"});
//		columns.add(new String[]{"折算系数", "unitConvert", "6"});
	}
	/**
	 * 3、在途成品库存折算表
	 */
	public void exportECSStockTravelingExgConvert(){
		List list = ecsCheckStockAction.findECSStockTravelingExgResolves(request, section,null);
		List<String[]> columns = new ArrayList<String[]>();
		columns.add(new String[]{"成品料号", "baseExg.ptNo", "11"});
		columns.add(new String[]{"成品序号", "baseExg.seqNum", "5"});
		columns.add(new String[]{"成品版本", "baseExg.version", "5"});
		columns.add(new String[]{"料件序号", "seqNum", "5"});
		columns.add(new String[]{"料件名称", "hsName", "10"});
		columns.add(new String[]{"型号规格", "hsSpec", "16"});
		columns.add(new String[]{"计量单位", "hsUnit", "6"});
		columns.add(new String[]{"单耗", "unitWaste", "6"});
		columns.add(new String[]{"损耗", "waste", "6"});
		columns.add(new String[]{"单项用量", "unitUsed", "6"});
		columns.add(new String[]{"总耗用", "hsAmount", "10"});
		export(columns,list);
	}
	/**
	 * 4、外发库存折算表
	 */
	public void exportECSStockOutSendAnalyseConvert(){
		List list = ecsCheckStockAction.findECSStockOutSendExgPtResolves(request, section, null);
		List<String[]> columns = new ArrayList<String[]>();
		columns.add(new String[]{"成品料号", "baseStockExg.ptNo", "15"});
		columns.add(new String[]{"版本号", "baseStockExg.version", "6"});
		columns.add(new String[]{"库存数量", "baseStockExg.storeAmount", "6"});
		columns.add(new String[]{"料件料号", "ptNo", "15"});
		columns.add(new String[]{"工厂名称", "ptName", "10"});
		columns.add(new String[]{"工厂规格", "ptSpec", "10"});
		columns.add(new String[]{"工厂单位", "ptUnit", "5"});
		columns.add(new String[]{"单耗", "unitWaste", "10"});
		columns.add(new String[]{"损耗", "waste", "10"});
		columns.add(new String[]{"单项用量", "unitUsed", "10"});
		columns.add(new String[]{"成品耗用量", "usedAmount", "10"});
		columns.add(new String[]{"料件备案序号", "seqNum", "10"});
		columns.add(new String[]{"报关商品名称", "hsName", "15"});
		columns.add(new String[]{"报关商品规格", "hsSpec", "15"});
		columns.add(new String[]{"计量单位", "hsUnit", "8"});
		columns.add(new String[]{"折算报关数量", "hsAmount", "10"});
		columns.add(new String[]{"折算系数", "unitConvert", "10"});
		export(columns,list);
	}
	
	/**
	 * 5、外发库存（半成品）
	 */
	public void exportECSStockOutSendSemiExgConvert(){
		List list = ecsCheckStockAction.findECSStockOutSendSemiExgPtResolves(request,section, null);
		List<String[]> columns = new ArrayList<String[]>();
		columns.add(new String[]{"成品料号", "baseStockExg.ptNo", "15"});
		columns.add(new String[]{"版本号", "baseStockExg.version", "6"});
		columns.add(new String[]{"库存数量", "baseStockExg.storeAmount", "6"});
		columns.add(new String[]{"料件料号", "ptNo", "10"});
		columns.add(new String[]{"工厂名称", "ptName", "10"});
		columns.add(new String[]{"工厂规格", "ptSpec", "15"});
		columns.add(new String[]{"工厂单位", "ptUnit", "5"});
		columns.add(new String[]{"单耗", "unitWaste", "10"});
		columns.add(new String[]{"损耗", "waste", "10"});
		columns.add(new String[]{"单项用量", "unitUsed", "10"});
		columns.add(new String[]{"成品耗用量", "usedAmount", "10"});
		columns.add(new String[]{"料件备案序号", "seqNum", "10"});
		columns.add(new String[]{"报关商品名称", "hsName", "15"});
		columns.add(new String[]{"报关商品规格", "hsSpec", "15"});
		columns.add(new String[]{"计量单位", "hsUnit", "8"});
		columns.add(new String[]{"折算报关数量", "hsAmount", "10"});
		columns.add(new String[]{"折算系数", "unitConvert", "10"});
		export(columns,list);
	}
	
	/**
	 * 6、残次品库存折算表(半成品)
	 */
	public void exportECSSemiFinishedExgConvert(){
		List list = ecsCheckStockAction.findECSSemiFinishedExgResolve(request, section,null);
		List<String[]> columns = new ArrayList<String[]>();
		columns.add(new String[]{"成品料号", "baseStockExg.ptNo", "15"});
		columns.add(new String[]{"版本号", "baseStockExg.version", "6"});
		columns.add(new String[]{"库存数量", "baseStockExg.storeAmount", "6"});
		columns.add(new String[]{"料件料号", "ptNo", "10"});
		columns.add(new String[]{"工厂名称", "ptName", "10"});
		columns.add(new String[]{"工厂规格", "ptSpec", "15"});
		columns.add(new String[]{"工厂单位", "ptUnit", "5"});
		columns.add(new String[]{"单耗", "unitWaste", "10"});
		columns.add(new String[]{"损耗", "waste", "10"});
		columns.add(new String[]{"单项用量", "unitUsed", "10"});
		columns.add(new String[]{"成品耗用量", "usedAmount", "10"});
		columns.add(new String[]{"料件备案序号", "seqNum", "10"});
		columns.add(new String[]{"报关商品名称", "hsName", "15"});
		columns.add(new String[]{"报关商品规格", "hsSpec", "15"});
		columns.add(new String[]{"计量单位", "hsUnit", "8"});
		columns.add(new String[]{"折算报关数量", "hsAmount", "10"});
		columns.add(new String[]{"折算系数", "unitConvert", "10"});
		export(columns,list);
	}
	
	/**
	 * 7、残次品库存折算表(成品)
	 */
	public void exportECSFinishedExgConvert(){
		List list = ecsCheckStockAction.findECSFinishedExgResolves(request, section,null);
		List<String[]> columns = new ArrayList<String[]>();
		columns.add(new String[]{"成品料号", "baseStockExg.ptNo", "15"});
		columns.add(new String[]{"版本号", "baseStockExg.version", "6"});
		columns.add(new String[]{"库存数量", "baseStockExg.storeAmount", "6"});
		columns.add(new String[]{"料件料号", "ptNo", "15"});
		columns.add(new String[]{"工厂名称", "ptName", "10"});
		columns.add(new String[]{"工厂规格", "ptSpec", "10"});
		columns.add(new String[]{"工厂单位", "ptUnit", "5"});
		columns.add(new String[]{"单耗", "unitWaste", "10"});
		columns.add(new String[]{"损耗", "waste", "10"});
		columns.add(new String[]{"单项用量", "unitUsed", "10"});
		columns.add(new String[]{"成品耗用量", "usedAmount", "10"});
		columns.add(new String[]{"料件备案序号", "seqNum", "10"});
		columns.add(new String[]{"报关商品名称", "hsName", "15"});
		columns.add(new String[]{"报关商品规格", "hsSpec", "15"});
		columns.add(new String[]{"计量单位", "hsUnit", "8"});
		columns.add(new String[]{"折算报关数量", "hsAmount", "10"});
		columns.add(new String[]{"折算系数", "unitConvert", "10"});
		export(columns,list);
	}
	
	/**
	 * 8、半成品库存折算表
	 */
	public void exportECSStockSemiExgPtHadStoreConvert(){
		List list = ecsCheckStockAction.findECSStockSemiExgPtHadStoreResolves(request, section,null);
		List<String[]> columns = new ArrayList<String[]>();
		columns.add(new String[]{"成品料号", "baseStockExg.ptNo", "15"});
		columns.add(new String[]{"版本号", "baseStockExg.version", "6"});
		columns.add(new String[]{"库存数量", "baseStockExg.storeAmount", "6"});
		columns.add(new String[]{"料件料号", "ptNo", "10"});
		columns.add(new String[]{"工厂名称", "ptName", "10"});
		columns.add(new String[]{"工厂规格", "ptSpec", "15"});
		columns.add(new String[]{"工厂单位", "ptUnit", "5"});
		columns.add(new String[]{"单耗", "unitWaste", "10"});
		columns.add(new String[]{"损耗", "waste", "10"});
		columns.add(new String[]{"单项用量", "unitUsed", "10"});
		columns.add(new String[]{"成品耗用量", "usedAmount", "10"});
		columns.add(new String[]{"料件备案序号", "seqNum", "10"});
		columns.add(new String[]{"报关商品名称", "hsName", "15"});
		columns.add(new String[]{"报关商品规格", "hsSpec", "15"});
		columns.add(new String[]{"计量单位", "hsUnit", "8"});
		columns.add(new String[]{"折算报关数量", "hsAmount", "10"});
		columns.add(new String[]{"折算系数", "unitConvert", "10"});
		export(columns,list);
	}
	
	/**
	 * 短溢表
	 */
	public void exportECSAnalyses(){
		List list = ecsCheckStockAction.findECSAnalyses(request, section,null);
		List<String[]> columns = new ArrayList<String[]>();
		columns.add(new String[]{"备案序号", "seqNum", "5"});
		columns.add(new String[]{"物料名称", "hsName", "10"});
		columns.add(new String[]{"物料规格", "hsSpec", "12"});
		columns.add(new String[]{"计量单位", "hsUnit", "6"});
		columns.add(new String[]{"单价(P)", "price", "6"});
		columns.add(new String[]{"期初/进口数量", "importAmount", "10"});
		columns.add(new String[]{"出口耗用量", "exgWasteAmount", "10"});
		columns.add(new String[]{"账册结存(A)", "emsBalance", "10"});
		columns.add(new String[]{"料件库存","stockAmountImg", "10"});
		columns.add(new String[]{"在产品库存", "stockAmountProcessImg", "10"});
		columns.add(new String[]{"成品库存", "stockAmountExg", "10"});
		columns.add(new String[]{"厂外库存","stockAmountOutFactoryImg", "10"});
		columns.add(new String[]{"内购库存", "stockAmountBuyImg", "10"});
		columns.add(new String[]{"在途料件", "stockAmountTravelingImg", "10"});
		columns.add(new String[]{"在途成品","stockAmountTravelingExg", "10"});
		columns.add(new String[]{"外发库存汇总", "stockAmountOutSendExgPt", "10"});
		columns.add(new String[]{"半成品库存(已入库)", "stockAmountSemiExgHStore", "10"});
		columns.add(new String[]{"在制品库存汇总", "stockAmountFinishingExg", "10"});
		columns.add(new String[]{"残次品库存汇总", "stockAmountBadImg", "10"});
		columns.add(new String[]{"盘点总库存(B)", "stockBalance", "10"});
		columns.add(new String[]{"已送货未转厂数", "unTransHadSendNum", "10"});
		columns.add(new String[]{"已转厂未送货数", "unSendHadTransNum", "10"});
		columns.add(new String[]{"已收货未转厂数", "unTransHadReceiveNum", "10"});
		columns.add(new String[]{"已转厂未收货数", "unReceiveHadTransNum", "10"});
		columns.add(new String[]{"结转差额(C)", "transferBalance", "10"});
		columns.add(new String[]{"差异数(D)=B-A+C", "diffAmount", "12"});
		columns.add(new String[]{"关税(G)=D*P*税率", "usd", "12"});
		columns.add(new String[]{"增值税(Z)=(D*P+G)*17%", "usdAdd", "15"});
		export(columns,list);
		
	}
}
