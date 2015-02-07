package com.bestway.common.fpt.btplsinput.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bestway.bcs.contractexe.entity.BcsCustomsDeclaration;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationCommInfo;
import com.bestway.bcus.custombase.entity.CustomBaseEntity;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.countrycode.PortLin;
import com.bestway.bcus.custombase.entity.countrycode.PreDock;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.custombase.entity.parametercode.Uses;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.enc.entity.CustomsDeclarationCommInfo;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.CommonUtils.GetKeyValue;
import com.bestway.common.GetKeyValueImpl;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.fpt.btplsinput.entity.BtplsDownloadPara;
import com.bestway.common.fpt.btplsinput.entity.CustomsDeclarationBodyTemp;
import com.bestway.common.fpt.btplsinput.entity.CustomsDeclarationHeadTemp;
import com.bestway.common.fpt.btplsinput.entity.GYSMessageIndentureMergerTemp;
import com.bestway.common.fpt.btplsinput.entity.GYSMessageIndentureTemp;
import com.bestway.common.fpt.btplsinput.entity.KHMessageIndentureMergerTemp;
import com.bestway.common.fpt.btplsinput.entity.KHMessageIndentureTemp;
import com.bestway.common.fpt.btplsinput.entity.MessageCustomsCoverBodyTemp;
import com.bestway.common.fpt.btplsinput.entity.MessageCustomsCoverHeadTemp;
import com.bestway.common.fpt.btplsinput.entity.MessageIndentureHeadTemp;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.common.fpt.entity.FptAppItem;
import com.bestway.common.fpt.entity.FptBillHead;
import com.bestway.common.fpt.entity.FptBillItem;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;

@SuppressWarnings("unchecked")
public class BtplsDownloadDao extends BaseDao  {
	
	public BtplsDownloadPara saveBtplsDownloadPara(BtplsDownloadPara dowloadPara) {
		this.saveOrUpdate(dowloadPara);
		return dowloadPara;
	}

	public void deleteBtplsDownloadPara(BtplsDownloadPara dowloadPara) {
		this.delete(dowloadPara);
	}

	public List findAllBtplsDownloadPara() {
		return this.find("select a from BtplsDownloadPara a"
				+ " where a.company.id=? ", new Object[] { CommonUtils
				.getCompany().getId() });
	}
	/**
	 * 获得某个深加工下载参数设置
	 * @param dowloadPara 深加工下载参数设置
	 * @return
	 */
	public BtplsDownloadPara findBtplsDownloadPara(BtplsDownloadPara dowloadPara){
		List list= new ArrayList();
		list =  this.find("select a from BtplsDownloadPara a"
				+ " where a.company.id=? and a.id = ? ", new Object[] { CommonUtils
				.getCompany().getId(),dowloadPara.getId() });
		if (list.size() > 0) {
			return (BtplsDownloadPara) list.get(0);
		} else {
			return null;
		}
	}
	/**
	 * 获得转厂客户供应商
	 */
	public List findScmManuFactory() 
	{
		return this.find("select a from ScmCoc a where  "
				+ "  a.company.id = ? ", new Object[] {
				CommonUtils.getCompany().getId()});
	}
	/**
	 * 获得深加工下载转厂客户供应商
	 */
	public List<BtplsDownloadPara> findScmManuFactoryFromBtplsDownloadPara() 
	{
		return this.find("select a from BtplsDownloadPara a where  "
				+ "  a.company.id = ? ", new Object[] {
				CommonUtils.getCompany().getId()});
	}
	/**
	 * 根据转厂客户供应商获得深加工下载参数设置
	 */
	public List findBtplsDownloadParaByScm(ScmCoc sc) 
	{
		List list = new ArrayList();
		list = this.find("select a from BtplsDownloadPara a where  "
				+ "  a.company.id = ? and a.scmCoc.id = ? ", new Object[] {
						CommonUtils.getCompany().getId(),sc.getId()});
		return list;
	}
	
	
	/**
	 * 根据临时下载对象生成正式报关单，然后保存到数据库。
	 * @param map
	 * @param projectType
	 * @return
	 */
	public List<BaseCustomsDeclaration> saveCustomsDeclaration(Map<CustomsDeclarationHeadTemp, List<CustomsDeclarationBodyTemp>> map, int projectType) {
		List<BaseCustomsDeclaration> result = new ArrayList<BaseCustomsDeclaration>();
		
		Map<String, Object> assistMap = this.getAssistMap();
		
		
		/*
		 * 保存报关单
		 */
		Set<CustomsDeclarationHeadTemp> set = map.keySet();
		List<CustomsDeclarationBodyTemp> list = null;
		CustomsDeclarationBodyTemp body = null;
		BaseCustomsDeclaration declaration = null;
		BaseCustomsDeclarationCommInfo commInfo = null;
		for (CustomsDeclarationHeadTemp head : set) {
			
			// 临时表头转化为正式表头
			declaration = toBaseCustomsDeclaration(head, projectType, assistMap);
			
			// 保存正式报关单表头
			this.saveOrUpdate(declaration);
			
			// 获取临时表体
			list = map.get(head);
			for (int i = 0; list != null && i < list.size(); i++) {
				body = list.get(i);
				
				// 临时表体转化为正式表体
				commInfo = this.toBaseCustomsDeclarationCommInfo(body, projectType, assistMap, declaration);
				
				// 保存正式报关单表体
				this.saveOrUpdate(commInfo);
			}
			
			result.add(declaration);
		}
		
		return result;
	}
	
	/**
	 * 临时表头转化为正式表头
	 * @param h
	 * @param projectType
	 * @param assistMap
	 * @return
	 */
	private BaseCustomsDeclaration toBaseCustomsDeclaration(CustomsDeclarationHeadTemp h, int projectType, Map<String, Object> assistMap) {
		BaseCustomsDeclaration d = null;
		if(projectType == ProjectType.BCUS) {
			d = new CustomsDeclaration();
		} else if(projectType == ProjectType.BCS) {
			d = new BcsCustomsDeclaration();
		} else {
			throw new RuntimeException("错误的报关模式！projectType=" + projectType);
		}
		
		// 币制
		Map<String, Curr> currMap = (Map<String, Curr>) assistMap.get("curr");
		
		// 港口
		Map<String, PortLin> portLinMap = (Map<String, PortLin>) assistMap.get("portLin");
		
		// 海关
		Map<String, Customs> customsMap = (Map<String, Customs>) assistMap.get("customs");
		
		// 码头
		Map<String, PreDock> preDockMap = (Map<String, PreDock>) assistMap.get("preDock");
		
		// 地区
		Map<String, District> districtMap = (Map<String, District>) assistMap.get("district");
		
		// 国家
		Map<String, Country> countryMap = (Map<String, Country>) assistMap.get("country");
		
		// 贸易方式
		Map<String, Trade> tradeMap = (Map<String, Trade>) assistMap.get("trade");
		
		// 运输方式
		Map<String, Transf> transfMap = (Map<String, Transf>) assistMap.get("transf");
		
		
		////////////////////////////////////
		if(h.getIsImport() == null) {
			h.setIsImport(true);
		}
		
		h.setEmsNo("C52042450007");
		
		////////////////////////////////////
		
		
	    // 报关单表头Id id;
	    d.setBtplsKey(h.getId() + "/" + h.getCompanyCode());
	    
	    // 进出口标志
		d
				.setImpExpFlag(h.getIsImport() ? ImpExpFlag.IMPORT
						: ImpExpFlag.EXPORT);
		// 进出口类型
		d.setImpExpType(h.getIsImport() ? ImpExpType.TRANSFER_FACTORY_IMPORT
				: ImpExpType.TRANSFER_FACTORY_EXPORT);		
		
		// 所属公司
		d.setCompany(CommonUtils.getCompany());
		
	    // 企业代码 companyCode;
	    System.out.println(h.getCompanyCode());
		
	    // 企业名称 companyName;
	    
	    // 企业海关编码 customsCode;
	    
	    
	    // 报关单流水号 serialNumber;
	    
	    // 帐册号 emsNo;
	    d.setEmsHeadH2k(h.getEmsNo());
	    
	    // 申请日期 sendDate;
	    d.setDeclarationDate(h.getSendDate());
	    
	    // 报关单号 rollCovorNO;
	    
	    // 运输方式 transf;
	    d.setTransferMode(transfMap.get(h.getTransf()));
	    
	    // 贸易方式 trade;
	    d.setTradeMode(tradeMap.get(h.getTrade()));
	    
	    // 起运国/运抵国 country;
	    d.setCountryOfLoadingOrUnloading(countryMap.get(h.getCountry()));	    
	    
	    // 转入境内目的地/转发出境内货源地 district;
	    d.setDomesticDestinationOrSource(districtMap.get(h.getDistrict()));
	    
	    // 转入装货港/转出指运港 portLin;
	    d.setPortOfLoadingOrUnloading(portLinMap.get(h.getPortLin()));
	    
	    // 转入币别/转出币别 currency;
	    d.setCurrency(currMap.get(h.getCurrency()));
	    
	    // 件数 pieces;
	    d.setCommodityNum(h.getPieces());
	    
	    // 毛重 grossWeight;
	    d.setGrossWeight(h.getGrossWeight());
	    
	    // 净重 netWeight;
	    d.setNetWeight(h.getNetWeight());
	    
	    // 报送海关 customs;
	    d.setDeclarationCustoms(customsMap.get(h.getCustoms()));
	    
	    // 码头 preDock;
	    d.setPredock(preDockMap.get(h.getPreDock()));
	    
	    // JBCUS是否下载过 isJBCUSDown;
		
		
		return d;
	}
	
	/**
	 * 临时表体转化为正式表体
	 * @param b
	 * @param projectType
	 * @param assistMap
	 * @param d
	 * @return
	 */
	private BaseCustomsDeclarationCommInfo toBaseCustomsDeclarationCommInfo(
			CustomsDeclarationBodyTemp b, int projectType,
			Map<String, Object> assistMap, BaseCustomsDeclaration d) {
		BaseCustomsDeclarationCommInfo i = null;
		if (projectType == ProjectType.BCUS) {
			i = new CustomsDeclarationCommInfo();
		} else if (projectType == ProjectType.BCS) {
			i = new BcsCustomsDeclarationCommInfo();
		} else {
			throw new RuntimeException("错误的报关模式！projectType=" + projectType);
		}
		
		// 国家
		Map<String, Country> countryMap = (Map<String, Country>) assistMap.get("country");
		
		// 单位
		Map<String, Unit> unitMap = (Map<String, Unit>) assistMap.get("unit");
		
		// 用途
		Map<String, Uses> usesMap = (Map<String, Uses>) assistMap.get("uses");
		
		// 征免方式
		Map<String, LevyMode> levyModeMap = (Map<String, LevyMode>) assistMap.get("levyMode");
		
		// 商品编码
		Map<String, Complex> complexMap = (Map<String, Complex>) assistMap.get("complex");
		
		// 所属公司
		i.setCompany(CommonUtils.getCompany());
		
		// 设置报关单表体关联的报关单表头
		i.setBaseCustomsDeclaration(d);

		// 报关单表头ID CustomsDeclarationHeadTemp customsDeclarationHeadTemp;

		// 备案序号 Integer commSerialNo;
		i.setCommSerialNo(b.getCommSerialNo());

		// 商品流水号 Integer seqNo;
		i.setSerialNumber(b.getSeqNo());

		// 商品编码 String complex;
		i.setComplex(complexMap.get(b.getComplex()));

		// 商品名称 String commName;
		i.setCommName(b.getCommName());

		// 商品规格 String commSpec;
		i.setCommSpec(b.getCommSpec());

		// 商品数量 Double commAmount;
		i.setCommAmount(b.getCommAmount());

		// 商品单价 Double commUnitPrice;
		i.setCommUnitPrice(b.getCommUnitPrice());

		// 商品总价 Double commTotalPrice;
		i.setCommTotalPrice(b.getCommTotalPrice());

		// 单位 String unit;
		i.setUnit(unitMap.get(b.getUnit()));

		// 法定单位 String legalUnit;
		i.setLegalUnit(unitMap.get(b.getLegalUnit()));

		// 第二法定单位 String secondLegalUnit;
		i.setSecondLegalUnit(unitMap.get(b.getSecondLegalUnit()));

		// 第一法定数量 Double firstAmount;
		i.setFirstAmount(b.getFirstAmount());

		// 第二法定数量 Double secondAmount;
		i.setSecondAmount(b.getSecondAmount());

		// 原产国 String country;
		i.setCountry(countryMap.get(b.getCountry()));

		// 用途 String uses;
		i.setUses(usesMap.get(b.getUses()));

		// 减免方式 String levyMode;
		i.setLevyMode(levyModeMap.get(b.getLevyMode()));

		// 毛重 Double grossWeight;
		i.setGrossWeight(b.getGrossWeight());
		
		// 净重 Double netWeight;
		i.setNetWeight(b.getNetWeight());

		return i;
	}
	
	
	private Map<String, Object> getAssistMap() {
		Map<String, Object> assistMap = new HashMap<String, Object>();
		String empty = "";
		
		GetKeyValue<CustomBaseEntity> gkv = new GetKeyValueImpl<CustomBaseEntity>(){

			public String getKey(CustomBaseEntity e) {
				
				return e.getCode();
			}
			public Object getValue(CustomBaseEntity e) {
				return e;
			}
		};
		
		// 贸易方式
		buildAssistMapItem(assistMap, "Trade", "trade", empty, empty, gkv);
		
		// 计量单位
		buildAssistMapItem(assistMap, "Unit", "unit", empty, empty, gkv);
		
		// 货币代码
		buildAssistMapItem(assistMap, "Curr", "curr", empty, empty, gkv);
		
		// 运输方式
		buildAssistMapItem(assistMap, "Transf", "transf", empty, empty, gkv);
		
		// 用途代码
		buildAssistMapItem(assistMap, "Uses", "uses", empty, empty, gkv);
		
		// 征免方式
		buildAssistMapItem(assistMap, "LevyMode", "levyMode", empty, empty, gkv);
		
		// 国家
		buildAssistMapItem(assistMap, "Country", "country", empty, empty, gkv);
		
		// 港口
		buildAssistMapItem(assistMap, "PortLin", "portLin", empty, empty, gkv);
		
		// 海关
		buildAssistMapItem(assistMap, "Customs", "customs", empty, empty, gkv);
		
		// 码头
		buildAssistMapItem(assistMap, "PreDock", "preDock", empty, empty, gkv);
		
		// 地区
		buildAssistMapItem(assistMap, "District", "district", empty, empty, gkv);
		
		
		GetKeyValue<Complex> gkv1 = new GetKeyValueImpl<Complex>(){

			public String getKey(Complex e) {
				
				return e.getCode();
			}
			public Object getValue(Complex e) {
				return e;
			}
		};
		
		// 商品编码
		List<Complex> list = this.find("select a from Complex a where (a.isOut <> '1' or a.isOut is null) ");
		Map<String, CustomBaseEntity> map = CommonUtils.listToMap(list, gkv1);
		assistMap.put("complex", map);
		

		return assistMap;
	}
	/**
	 * 保存报关单
	 * @param map
	 * @param p
	 * @return
	 */
	public List  saveBtplsCustomsCoverBody(Map<MessageCustomsCoverHeadTemp, List<MessageCustomsCoverBodyTemp>> map,Map p){
		/*
		 * 保存申请单
		 */
		List<FptAppItem> result = new ArrayList<FptAppItem>();		
		Set<MessageCustomsCoverHeadTemp> setTemp = map.keySet();		
		List<MessageCustomsCoverBodyTemp> blist = null;
		MessageCustomsCoverBodyTemp body = null;		
		FptAppHead fptAppHead = null;
		FptAppItem fptAppItem = null;
		Map<String, Object> assistMap = this.getAssistMap();

		for (MessageCustomsCoverHeadTemp head : setTemp) {
			fptAppHead = trunMessageCustomsCoverHeadTemp(head,assistMap,p);
			this.saveOrUpdate(fptAppHead);
				// 获取远程表体
				blist = map.get(head);
				for (int i = 0; blist != null && i < blist.size(); i++) {
					body = blist.get(i);				
					// 远程表体转化为本地表体
					fptAppItem = this.trunMessageCustomsCoverBodyTemp(body, assistMap, fptAppHead,i,p);					
					// 保存正式报关单表体
					this.saveOrUpdate(fptAppItem);
				}				
				result.add(fptAppItem);
		}		
		return result;
	}
	/**
	 * 保存收货单 
	 * @return
	 */
	public List saveBtplsIndentureBody(Map<MessageIndentureHeadTemp, List<List>> p,Map<String, Object> paramerter){
		List<FptBillItem> rs = new ArrayList<FptBillItem>();
		Set<MessageIndentureHeadTemp> setTemp = p.keySet();
		Map<String, Object> assistMap = this.getAssistMap();
		FptBillHead head = null;
		FptBillItem item = new FptBillItem();
		Integer index = (Integer) paramerter.get("selectIndex");
		//保存表头、表体
		for (MessageIndentureHeadTemp headTemp : setTemp) {
			head= trunMessageIndentureHeadCoverHeadTemp(headTemp);
			Iterator it = setTemp.iterator();
			List<List> dlist = p.get(headTemp);
			for (int i = 0; i < dlist.size(); i++) {
				if(index==0){//正常收发货
		            List<GYSMessageIndentureTemp> glist = dlist.get(0);
		            for (int ii = 0; glist != null && ii < glist.size(); ii++) {
		                GYSMessageIndentureTemp temp = glist.get(ii);
		                item = turnGysMessageIndentureTempToFptBillItemByOut(temp,head,ii,assistMap);
		                head.setBillSort(FptInOutFlag.OUT);
		                this.saveOrUpdate(item);
		            }
		            List<KHMessageIndentureTemp> klist = dlist.get(1);
		            for (int j = 0; klist != null && j < klist.size(); j++) {
		                KHMessageIndentureTemp temp = klist.get(j);
		                item = turnGysMessageIndentureTempToFptBillItemByIn(temp,head,j,assistMap);
		                this.saveOrUpdate(item);
		                head.setBillSort(FptInOutFlag.IN);
		            }
				}else if(index==1){ //退货
					 List<GYSMessageIndentureTemp> glist = dlist.get(0);
			            for (int ii = 0; glist != null && ii < glist.size(); ii++) {
			                GYSMessageIndentureTemp temp = glist.get(ii);
			                item = turnGysMessageIndentureTempToFptBillItemByOut(temp,head,ii,assistMap);
			                head.setBillSort(FptInOutFlag.OUT);
			                this.saveOrUpdate(item);
			            }
			            
					 List<KHMessageIndentureTemp> klist = dlist.get(1);
			            for (int j = 0; klist != null && j < klist.size(); j++) {
			                KHMessageIndentureTemp temp = klist.get(j);
			                item = turnGysMessageIndentureTempToFptBillItemByIn(temp,head,j,assistMap);
			                this.saveOrUpdate(item);
			                head.setBillSort(FptInOutFlag.IN);
			            }
				}
				 rs.add(item);
				
			}
			this.saveOrUpdate(head);
		}		
		return rs;
	}
	/**
	 * 保存申请表表体
	 * @param body
	 * @param assistMap
	 * @param fptAppHead
	 * @return
	 */
	private FptAppItem trunMessageCustomsCoverBodyTemp(MessageCustomsCoverBodyTemp body,Map<String, Object> assistMap,FptAppHead fptAppHead,int listNo,Map p){
		
		FptAppItem item = new FptAppItem();
		// 商品编码
		Map<String, Complex> complexMap = (Map<String, Complex>) assistMap.get("complex");
		// 单位
		Map<String, Unit> unitMap = (Map<String, Unit>) assistMap.get("unit");
		//表头信息
		item.setFptAppHead(fptAppHead);
		//修改信息
		item.setModifyMarkState(body.getApplModifyMarkState());
		//转出为0 &&  fptAppHead.getOutTradeCode().equals(p.get("currCompany"))
		if(fptAppHead.getOutTradeCode().length()==10  ){	
			item.setInEmsNo(body.getApplHandBookNO());
			item.setInOutFlag(FptInOutFlag.OUT);		
		}
		//转出为1 && fptAppHead.getOutTradeCode().equals(p.get("currCompany"))
		if(fptAppHead.getInTradeCode().length()==10 ){
			//转出  手册编号
			item.setInEmsNo(body.getCompHandBookNO());
			item.setInOutFlag(FptInOutFlag.IN);
		}
		//自然数编号
		item.setListNo(listNo);				
		//转入方必填  转出序号
		item.setInOutNo(body.getOutNO());
		//商品项号
		item.setTrNo(body.getCompItemNo());
		//商品编码
		item.setCodeTs(complexMap.get(body.getCompGoodsNO()));
		//商品名称
		item.setName(body.getCompGoodsName());
		//规格型号
		item.setSpec(body.getCompGoodsType());
		//申报单位
		item.setUnit(unitMap.get(body.getCompGoodsUnit()));
		//法定单位
		item.setUnit1(unitMap.get(body.getCompGoodsUnit1()));
		//申请数量
		item.setQty(body.getCompGoodsQuantity());
		//单价
		item.setUnitPrice(0.0);
		//币制
		
		//法定数量
		item.setQty1(body.getCompGoodsQuantity1());
		//备注   如果当前用户代码与在转入转出都没有，写到备注
		
		item.setNote(body.getCompNote());
		
		
		return item;		
	}
	/**
	 * 转换申请表表头信息并返回保存
	 * @param temp
	 * @param assistMap
	 * @return
	 */
	private FptAppHead trunMessageCustomsCoverHeadTemp(MessageCustomsCoverHeadTemp temp,Map<String, Object> assistMap,Map p){
		FptAppHead head = new FptAppHead();	
		
		// 地区 
		Map<String, District> districtMap = (Map<String, District>) assistMap.get("district");
		//转入、出海关
		Map<String, Customs> customsMap = (Map<String, Customs>) assistMap.get("customs");
		
		head.setProjectType(ProjectType.BCS);
		//申请单编号
		head.setAppNo(temp.getAppNo());
		//电子口岸统一编号
		head.setSeqNo(temp.getSeqNo());
		//对方手册到日期
		head.setOppositeEmsDate(temp.getOppositeEmsDate());
		//转出地
		head.setOutDistrict(districtMap.get(temp.getOutDistrict()));
		//申请表类型
		head.setAppClass(temp.getAppClass());
		//企业合同号
		head.setContrNo(temp.getContrNo());
		//转出企业代码  且如果当前登录公司用户代码=转出企业代码就放到转出标签  && temp.getOutTradeCode().equals(p.get("currCompany"))
		if(temp.getOutTradeCode().length()==10 ){ 
			head.setInOutFlag(FptInOutFlag.OUT);
		}
		head.setOutTradeCode(temp.getOutTradeCode());
		//转出企业名称
		head.setOutTradeName(temp.getOutTradeName());
		//转出手册、帐号
		head.setEmsNo(temp.getEmsNo());
		//转出企业内部编码
		head.setOutCopAppNo(temp.getOutCopAppNo());
		//转出地海关
		head.setOutCustoms(customsMap.get(temp.getOutCustoms()));
		//转出申报日期
		head.setOutDate(temp.getOutDate());
		//目的地
		head.setInDistrict(districtMap.get(temp.getInDistrict()));
		//转入企业代码  且如果当前登录用户代码=转入企业代码就放到转入标签
		if(temp.getInTradeCode().length()==10 ){ //&&  temp.getOutTradeCode().equals(p.get("currCompany"))
			head.setInOutFlag(FptInOutFlag.IN);
		}
		head.setInTradeCode(temp.getInTradeCode());
		//转入企业名称
		head.setInTradeName(temp.getInTradeName());
		//转出申报企业代码
		head.setOutTradeCode2(temp.getOutTradeCode2());
		//转出申报企业名称
		head.setOutTradeName2(temp.getOutTradeName2());
		//转出企业9位组织机构
		head.setOutAgentCode(temp.getOutAgentCode());
		//转出企业9位组织机构名称
		head.setOutAgentName(temp.getOutAgentName());
		//转出企业法人/电话
		head.setOutCorp(temp.getOutCorp());
		//转出申报人/电话
		head.setOutDecl(temp.getOutDecl());
		//预计耗用时
		head.setConveyDay(temp.getConveyDay());
		//送货距离
		head.setConveySpa(temp.getConveySpa());
		//转出企业批准证编号
		head.setOutLiceNo(temp.getOutLiceNo());
		//购销合同
		
		//转出备注 
		head.setOutNote(temp.getOutNote());
		//转入企业内部编号
		head.setInCopAppNo(temp.getInCopAppNo());
		//转入地海关
		head.setInCustoms(customsMap.get(temp.getInCustoms()));
		//转入申报日期
		head.setInDate(temp.getInDate());
		//转入企业批准证编号
		head.setInLiceNo(temp.getInLiceNo());
		//转入企业申报代码
		head.setInTradeCode2(temp.getInTradeCode2());
		//转入企业申报名称
		head.setInTradeName2(temp.getInTradeName2());
		//转入企业法人/电话
		head.setInCorp(temp.getInCorp());
		//转入申报人/电话
		head.setInDecl(temp.getInDecl());
		//转入企业9位组织代码
		head.setInAgentCode(temp.getInAgentCode());
		//转入企业9位组织名称
		head.setInAgentName(temp.getInAgentName());
		//转入手册号
		head.setInEmsNo(temp.getHandsBookIn());
		//申请表有效期
		head.setEndDate(temp.getOppositeEmsDate());		
		//转入备注 如果没找到当前公司的编码就写到备注栏
		head.setInNote(temp.getInNote());		
		
		return head;
	}
	/**
	 * 收发货表头转换为FptBillHead
	 * @return
	 */
	private FptBillHead trunMessageIndentureHeadCoverHeadTemp(MessageIndentureHeadTemp tHead){
		FptBillHead h = new FptBillHead();
		//收发货编号
		h.setBillNo(tHead.getBillNo());
		//项目类型
		h.setProjectType(ProjectType.BCUS);
		//单据类型 收发货单 2 退货单 3
		h.setSysType(String.valueOf(tHead.getMessageIndentureType()).equals("0")?"2":"3");
		//电子口岸统一编号
		h.setSeqNo(tHead.getSeqNo());
		//申请单编号
		h.setAppNo(tHead.getAppNo());
		//合同号
		
		//申报状态
		h.setAppState(tHead.getDeclareState());
		//发货企业编码
		h.setIssueTradeCod(tHead.getIssueTradeCod());
		//发货企业内部编号
		h.setIssueCopBillNo(tHead.getIssueCopBillNo());
		//发货企业名称
		h.setIssueTradeName(tHead.getIssueTradeName());
		//发货日期
		h.setIssueDate(tHead.getIssueDate());
		//发货申报人
		h.setIssueIsDeclaEm(tHead.getIssueIsDeclaEm());
		//发货申报时间
		h.setIssueIsDeclaDate(tHead.getIssueIsDeclaDate());
		//发货企业9位机构代码
		h.setIssueAgentCode(tHead.getIssueAgentCode());
		//发货企业9位机构名称
		h.setIssueAgentName(tHead.getIssueAgentName());
		//发货备注
		h.setIssueNote(tHead.getIssueNote());
		//收货企业内容编号
		h.setReceiveCopBillNo(tHead.getReceiveCopBillNo());
		//收货企业编码
		h.setReceiveTradeCod(tHead.getReceiveTradeCod());
		//收货企业名称
		h.setReceiveTradeName(tHead.getReceiveTradeName());
		//收货日期
		h.setReceiveDate(tHead.getReceiveDate());
		//收货申报人
		h.setReceiveIsDeclaEm(tHead.getReceiveIsDeclaEm());
		//收货申报时间
		h.setReceiveIsDeclaDate(tHead.getReceiveDate());
		//收货企业9位机构代码
		h.setReceiveAgentCode(tHead.getReceiveAgentCode());
		//收货企业9位机构名称
		h.setReceiveAgentName(tHead.getReceiveAgentName());
		//收货备注
		h.setReceiveNote(tHead.getReceiveNote());

		//录入日期
		h.setCreateDate(new Date());
		//操作员
		AclUser aclUser = (AclUser) CommonUtils.getAclUser();
		h.setAclUser(aclUser);
		
		
		return h;
	}
	//保存商品信息、归并前----正常收发货
	private FptBillItem turnGysMessageIndentureTempToFptBillItemByOut(GYSMessageIndentureTemp t,FptBillHead  h,int j,Map<String, Object> assistMap){
		FptBillItem item = new FptBillItem();
		//商品编码 
		Map<String, Complex> complexMap = (Map<String, Complex>) assistMap.get("complex");
		//单位
		Map<String, Unit> unitMap = (Map<String, Unit>) assistMap.get("unit");
		
		//增加表头关联
		item.setFptBillHead(h);
		//收发货标志
		item.setBillSort(FptInOutFlag.OUT);
		//自然流水号
		item.setListNo(j);
		//发货序号
		item.setOutNo(t.getOutNo());
		//手册号
		item.setInEmsNo(t.getInEmsNo());
		//料号
		item.setCopGName(t.getCopGName());
		
		item.setCopGNo(t.getCopGNo());
		//规格型号
		item.setCopGModel(t.getCopGModel());
		//申请单号
		item.setAppGNo(t.getAppGNo());
		//项号
		item.setTrGno(t.getTrGno());
		//商品编码
		item.setComplex(complexMap.get(t.getComplex()));
		//商品名称
		item.setCommName(t.getCommName());
		//商品规格
		item.setCommSpec(t.getCommSpec());
		//交易单位
		item.setUnit(unitMap.get(t.getUnit()));
		//交易数量
		item.setTradeQty(t.getTradeQty());
		//计量单位
		item.setTradeUnit(unitMap.get(t.getTradeUnit()));
		//申报数量
		item.setQty(t.getQty());
		//备注
		item.setNote(t.getNote());
		
		
		return item;
	}
	//保存商品信息、归并前----收发退货
	private FptBillItem turnGysMessageIndentureTempToFptBillItemByIn(KHMessageIndentureTemp t,FptBillHead  h,int j,Map<String, Object> assistMap){
		FptBillItem item = new FptBillItem();
		//商品编码 
				Map<String, Complex> complexMap = (Map<String, Complex>) assistMap.get("complex");
				//单位
				Map<String, Unit> unitMap = (Map<String, Unit>) assistMap.get("unit");
				
				//增加表头关联
				item.setFptBillHead(h);
				//收发货标志
				item.setBillSort(FptInOutFlag.IN);
				//自然流水号
				item.setListNo(j);
				//发货序号
				item.setOutNo(t.getOutNo());
				//手册号
				item.setInEmsNo(t.getInEmsNo());
				//料号
				item.setCopGName(t.getCopGName());
				
				item.setCopGNo(t.getCopGNo());
				//规格型号
				item.setCopGModel(t.getCopGModel());
				//申请单号
				item.setAppGNo(t.getAppGNo());
				//项号
				item.setTrGno(t.getTrGno());
				//商品编码
				item.setComplex(complexMap.get(t.getComplex()));
				//商品名称
				item.setCommName(t.getCommName());
				//商品规格
				item.setCommSpec(t.getCommSpec());
				//交易单位
				item.setUnit(unitMap.get(t.getUnit()));
				//交易数量
				item.setTradeQty(t.getTradeQty());
				//计量单位
				item.setTradeUnit(unitMap.get(t.getTradeUnit()));
				//申报数量
				item.setQty(t.getQty());
				//备注
				item.setNote(t.getNote());
		
		return item;
	}
	private void buildAssistMapItem(Map<String, Object> assistMap,
			String itemClass, String itemName, String field, String value,
			GetKeyValue gkv) {
		List<CustomBaseEntity> list = findCustoms(itemClass, field, value);
		Map<String, CustomBaseEntity> map = CommonUtils.listToMap(list, gkv);
		assistMap.put(itemName, map);
	}
}
