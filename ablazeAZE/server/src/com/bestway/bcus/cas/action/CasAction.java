/*
 * Created on 2004-10-28
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.action;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMerge;
import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillInit;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.cas.bill.entity.CasInnerMergeDataOrder;
import com.bestway.bcus.cas.entity.BalanceInfo;
import com.bestway.bcus.cas.entity.BalanceInfo2;
import com.bestway.bcus.cas.entity.BillFixingBase;
import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.cas.entity.BillType;
import com.bestway.bcus.cas.entity.CheckBalance;
import com.bestway.bcus.cas.entity.CheckBalanceConvertMateriel;
import com.bestway.bcus.cas.entity.CheckDetail;
import com.bestway.bcus.cas.entity.CheckMaster;
import com.bestway.bcus.cas.entity.DebtDetail;
import com.bestway.bcus.cas.entity.DebtMaster;
import com.bestway.bcus.cas.entity.ExgExportInfoBase;
import com.bestway.bcus.cas.entity.FactoryAndFactualCustomsRalation;
import com.bestway.bcus.cas.entity.ImgOrgUseInfoBase;
import com.bestway.bcus.cas.entity.LeftoverMateriel;
import com.bestway.bcus.cas.entity.LeftoverMaterielBalanceInfo;
import com.bestway.bcus.cas.entity.MarginDetail;
import com.bestway.bcus.cas.entity.MarginMaster;
import com.bestway.bcus.cas.entity.MoneyDetail;
import com.bestway.bcus.cas.entity.MoneyMaster;
import com.bestway.bcus.cas.entity.StatCusNameRelation;
import com.bestway.bcus.cas.entity.StatCusNameRelationHsn;
import com.bestway.bcus.cas.entity.StatCusNameRelationMt;
import com.bestway.bcus.cas.entity.TempImgOrgUseInfo;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.cas.entity.TempStatCusNameRelation;
import com.bestway.bcus.cas.invoice.entity.CasInvoice;
import com.bestway.bcus.cas.invoice.entity.CasInvoiceInfo;
import com.bestway.bcus.cas.parameterset.entity.BillCorrespondingControl;
import com.bestway.bcus.cas.parameterset.entity.BillCorrespondingOption;
import com.bestway.bcus.cas.parameterset.entity.CasBillOption;
import com.bestway.bcus.cas.parameterset.entity.LotControl;
import com.bestway.bcus.cas.parameterset.entity.OtherOption;
import com.bestway.bcus.cas.parameterset.entity.WriteAccountYear;
import com.bestway.bcus.cas.specificontrol.entity.CustomsDeclarationCommInfoBillCorresponding;
import com.bestway.bcus.cas.specificontrol.entity.MakeBillCorrespondingInfoBase;
import com.bestway.bcus.cas.specificontrol.entity.TempBillCorrespondingSearch;
import com.bestway.bcus.cas.specificontrol.entity.TempMaterielTypeSetup;
import com.bestway.bcus.cas.specificontrol.entity.TempResult;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.Condition;
import com.bestway.common.ConsignQueryCondition;
import com.bestway.common.ProjectTypeParam;
import com.bestway.common.Request;
import com.bestway.common.dataimport.entity.ImportDataOrder;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.customs.common.entity.BaseEmsHead;

/**
 * 海关帐接口
 * 
 * @author // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public interface CasAction {

	/**
	 * 保存单据资料主表
	 * 
	 * @param request
	 *            发送请求
	 * @param billMaster
	 *            单据表头
	 * @return 单据资料主表
	 */
	BillMaster saveBillMaster(Request request, BillMaster billMaster);

	/**
	 * 删除单据主表
	 * 
	 * @param request
	 *            发送请求
	 * @param billMaster
	 *            单据表头
	 */
	void deleteBillMaster(Request request, BillMaster billMaster);

	/**
	 * 保存单据资料明细表
	 * 
	 * @param request
	 *            发送请求
	 * @param billDetail
	 *            单据明细
	 * @return 单据明细
	 */
	BillDetail saveBillDetail(Request request, BillDetail billDetail);

	/**
	 * 判断单据是否被引用
	 * 
	 * @param billId
	 *            单据ID
	 * @return
	 * @author sxk
	 */
	List findOwpBillAndBillDetail(Request request, String billId);

	/**
	 * 查询该单据表头下的单据表体
	 * 
	 * @param billMaster
	 *            单据表头
	 * @return
	 * @author sxk
	 */
	List findBillDetailByMasterId(Request request, BillMaster billMaster);


	/**
	 * 保存单据明细资料
	 * 
	 * @param request
	 *            发送请求
	 * @param billDetailList
	 *            单据明细
	 * @return 单据明细
	 */
	List<BillDetail> saveBillDetail(Request request,
			List<BillDetail> billDetailList);

	/**
	 * 删除单据明细表 /**
	 * 
	 * @param request
	 *            发送请求
	 * @param billDetail
	 *            单据明细
	 */
	void deleteBillDetail(Request request, BillDetail billDetail);

	/**
	 * 删除单据明细表
	 * 
	 * @param request
	 *            发送请求
	 * @param listBillDetail
	 *            单据明细
	 */
	void deleteBillDetail(Request request, List<BillDetail> listBillDetail);

	/**
	 * 得到单据号码
	 * 
	 * @param sBillType
	 *            单据类型
	 * @return 与单据类型匹配的单据号
	 */
	String getBillNo(int sBillType);

	/**
	 * 删除表体通过表头
	 * 
	 * @param request
	 *            发送请求
	 * @param billMaster
	 *            单据表头
	 */
	void deleteBillDetailByMaster(Request request, BillMaster billMaster);

	/**
	 * 返回详细的报关商品
	 * 
	 * @param request
	 *            发送请求
	 * @param produceType
	 *            物料类型
	 * @return 与指定物料类型匹配的表中的报关商品名称
	 */
	List findDistinctHsName(Request request, String produceType);

	/**
	 * 返回详细的工厂商品
	 * 
	 * @param request
	 *            发送请求
	 * @param produceType
	 *            物料类型
	 * @return 与指定物料类型匹配的表中的工厂商品名称
	 */
	List findDistinctPtName(Request request, String produceType);

	/**
	 * 返回详细的工厂商品（记帐）
	 * 
	 * @param request
	 *            发送请求
	 * @param produceType
	 *            物料类型
	 * @return 与指定的物料类型匹配的表中已记帐的工厂商品名称
	 */
	List findDistinctPtNameAndWrite(Request request, String produceType);

	/**
	 * 组织公共查询
	 * 
	 * @param request
	 *            发送请求
	 * @param className
	 *            表名
	 * @param conditions
	 *            查询条件("and"或"or""(""值"")"等等)
	 * @return 与指定的表名 条件匹配且selects groupby leftouter为空的查询
	 */
	List commonSearch(Request request, String className, List conditions);

	/**
	 * 组织公共查询（带select group by）
	 * 
	 * @param request
	 *            请求控制
	 * @param selects
	 *            选择内容或范围 不为空是selects 为空是select a
	 * @param className
	 *            表名
	 * @param conditions
	 *            查询条件("and"或"or""(""值"")"等等)
	 * @param groupBy
	 *            分组
	 * @return 带 selects groupby的公共查询
	 */
	public List commonCount(Request request, String selects, String className,
			List conditions, String groupBy, String leftOuter) ;
	/**
	 * 统计公共查询的笔数
	 * 
	 * @param selects
	 * @param className
	 * @param conditions
	 * @param groupBy
	 * @param leftOuter
	 * @return
	 */
	public int countCommonSearchPage(Request request,String selects, String className,
			List conditions, String groupBy, String leftOuter);
	/**
	 * 组织公共查询（带select group by）
	 * 
	 * @param request
	 *            发送请求
	 * @param selects
	 *            选择内容或范围 不为空是selects 为空是select a
	 * @param className
	 *            表名
	 * @param conditions
	 *            查询条件("and"或"or""(""值"")"等等)
	 * @param groupBy
	 *            分组
	 * @return 带 selects groupby的公共查询
	 */
	 List commonSearchPageList(Request request,int index, int length, String selects, String className,
			List conditions, String groupBy, String leftOuter) ;

	/**
	 * 返回单据类型
	 * 
	 * @param request
	 *            发送请求
	 * @param produceType
	 *            物料类型
	 * @return 根据物料类型返回单据类型
	 */
	List findBillMaterielType(Request request, String produceType);

	/**
	 * 显示临时单据
	 * 
	 * @param request
	 *            发送请求
	 * @return 临时单据
	 */
	List findBillTemp(Request request);

	/**
	 * 显示设备(海关)
	 * 
	 * @param request
	 *            发送请求
	 * @return 所有设备内容
	 */
	List findBillFixingThing(Request request);

	/**
	 * 显示设备(工厂)
	 * 
	 * @param request
	 *            发送请求
	 * @return 所有设备内容
	 */
	List findBillFixing(Request request);

	/**
	 * 删除临时单据
	 * 
	 * @param request
	 *            发送请求
	 */
	void deleteBillTemp(Request request);

	/**
	 * 删除设备(海关)
	 * 
	 * @param request
	 *            发送请求
	 */
	void deleteBillFixingThing(Request request);

	/**
	 * 删除设备(工厂)
	 * 
	 * @param request
	 *            发送请求
	 */
	void deleteBillFixing(Request request);

	/**
	 * 保存临时单据
	 * 
	 * @param request
	 *            发送请求
	 * @param billTemp
	 *            临时单据
	 */
	void saveBillTemp(Request request, BillTemp billTemp);

	/**
	 * 保存设备单据
	 * 
	 * @param request
	 *            发送请求
	 * @param bill
	 *            设备单据
	 * @return BillFixingBase型的设备单据
	 */
	BillFixingBase saveBillFixingBase(Request request, BillFixingBase bill);

	/**
	 * 删除设备单据
	 * 
	 * @param request
	 *            发送请求
	 * @param bill
	 *            设备单据
	 */
	void deleteBillFixingBase(Request request, List<BillFixingBase> bill);

	/**
	 * 显示客户来自单据表头
	 * 
	 * @param request
	 *            发送请求
	 * @param materielType
	 *            物料类型
	 * @return 与指定的物料类型匹配的单句表头中的客户
	 */
	List findScmCocFromBillMaster(Request request, String materielType);

	/**
	 * 显示仓库来自单据表头
	 * 
	 * @param request
	 *            发送请求
	 * @param materielType
	 *            物料类型
	 * @return 与指定的物料类型匹配的单据表头中的仓库
	 */
	List findWareSetFromBillMaster(Request request, String materielType);

	/**
	 * 查找物料或商品
	 * 
	 * @param request
	 *            发送请求
	 * @param materielType
	 *            物料类型
	 * @return 根据指定的物料类型查出相应的单据表头中的物料或商品
	 */
	List findGoodsFromBillMaster(Request request, String materielType);

	/**
	 * 返回指定仓库的进出单据明细记录
	 * 
	 * @param request
	 *            发送请求
	 * @param materielType
	 *            物料类型
	 * @param wareSet
	 *            仓库
	 * @return 与指定的仓库匹配且根据物料类型查出的单据表头的进出单据明细记录
	 */
	List findGoodsFromBillMasterByWareSet(Request request, String materielType,
			WareSet wareSet);

	/**
	 * 显示进出口商品信息
	 * 
	 * @param request
	 *            发送请求
	 * @param materialType
	 *            物料类型
	 * @param conditions
	 *            查询条件
	 * @return 按照查询条件查出进出口商品信息
	 */
	List findImpExpInfos(Request request, String materialType, List conditions,
			Boolean isSplitBomVersion, Boolean isWareSet);

	/**
	 * 显示进出口商品信息
	 * 
	 * @param request
	 *            发送请求
	 * @param materialType
	 *            物料类型
	 * @param conditions
	 *            查询条件
	 * @return 按照查询条件查出进出口商品信息
	 */
	List findImpExpInfos(Request request, String materialType,
			List<Condition> conditions, Boolean isSplitBomVersion,
			Date beginDate, Boolean isWareSet);

	/**
	 * 显示结转商品信息
	 * 
	 * @param request
	 *            发送请求
	 * @param materialType
	 *            物料类型
	 * @param conditions
	 *            查询条件
	 * @return 按照查询条件查出结转商品信息
	 */
	List findCarryForwardInfos(Request request, String materialType,
			List conditions);

	/**
	 * 料件，成品，设备库存情况统计表。
	 * 
	 * @param request
	 *            发送请求
	 * @param materialType
	 *            物料类型
	 * @param endDate
	 *            截止日期
	 * @param beginPtNo
	 *            开始料号
	 * @param endPtNo
	 *            截止料号
	 * @return 库存统计结果
	 */
	List findStoreInfos(Request request, String materialType, Date beginDate,
			Date endDate, String beginPtNo, String endPtNo);

	/**
	 * 返回盘点表头
	 * 
	 * @param request
	 *            发送请求
	 * @param materielType
	 *            物料类型
	 * @return 返回所有没有作废的盘点表头
	 */
	List findCheckMaster(Request request, String materielType);

	/**
	 * 保存盘点表头
	 * 
	 * @param request
	 *            发送请求
	 * @param checkMaster
	 *            盘点表表头
	 * @return 盘点表头
	 */
	CheckMaster saveCheckMasters(Request request, CheckMaster checkMaster);

	/**
	 * 取得所有序号
	 * 
	 * @param request
	 *            发送请求
	 * @param className
	 *            类名
	 * @return 返回序号 从1开始
	 */
	String getNum(Request request, String className);

	/**
	 * 显示盘点表体
	 * 
	 * @param request
	 *            发送请求
	 * @param checkMaster
	 *            盘点表头
	 * @return 返回盘点表体
	 */
	List findCheckDetail(Request request, CheckMaster checkMaster);

	/**
	 * 保存盘点表体
	 * 
	 * @param request
	 *            发送请求
	 * @param checkDetail
	 *            盘点表体
	 * @return 盘点表体
	 */
	CheckDetail saveCheckDetails(Request request, CheckDetail checkDetail);

	/**
	 * 删除盘点表明细
	 * 
	 * @param request
	 *            发送请求
	 * @param checkMaster
	 *            盘点表表头
	 */
	void deleteCheckDetail(Request request, CheckMaster checkMaster);

	/**
	 * 返回现金流量表头
	 * 
	 * @param request
	 *            发送请求
	 * @return 没有作废的现金流量表头
	 */
	List findMoneyMaster(Request request);

	/**
	 * 返回资产负债表头
	 * 
	 * @param request
	 *            发送请求
	 * @return 没有作废的资产负债表头
	 */
	List findDebtMaster(Request request);

	/**
	 * 返回利润表头
	 * 
	 * @param request
	 *            发送请求
	 * @return 没有作废的利润表头
	 */
	List findMarginMaster(Request request);

	/**
	 * 保存现金流量表头
	 * 
	 * @param request
	 *            发送请求
	 * @param moneyMaster
	 *            现金流量表头
	 * @return 现金流量表表头
	 */
	MoneyMaster saveMoneyMaster(Request request, MoneyMaster moneyMaster);

	/**
	 * 保存利润表头
	 * 
	 * @param request
	 *            发送请求
	 * @param marginMaster
	 *            利润表表头
	 * @return 利润表表头
	 */
	MarginMaster saveMarginMaster(Request request, MarginMaster marginMaster);

	/**
	 * 保存资产负债表头
	 * 
	 * @param request
	 *            发送请求
	 * @param debtMaster
	 *            资产负债表表头
	 * @return 资产负债表表头
	 */
	DebtMaster saveDebtMaster(Request request, DebtMaster debtMaster);

	/**
	 * 返回现金流量表体
	 * 
	 * @param request
	 *            发送请求
	 * @param moneyMaster
	 *            现金流量表表头
	 * @return 与表头内容相匹配的现金流量表表体
	 */
	List findMoneyDetail(Request request, MoneyMaster moneyMaster);

	/**
	 * 返回资产负债表体
	 * 
	 * @param request
	 *            发送请求
	 * @param debtMaster
	 *            资产负债表表头
	 * @return 与资产负债表表头对应的表体
	 */
	List findDebtDetail(Request request, DebtMaster debtMaster);

	/**
	 * 返回利润表体
	 * 
	 * @param request
	 *            发送请求
	 * @param marginMaster
	 *            利润表表头
	 * @return 与利润表表头对应的表体
	 */
	List findMarginDetail(Request request, MarginMaster marginMaster);

	/**
	 * 保存现金流量表体
	 * 
	 * @param request
	 *            发送请求
	 * @param moneyDetail
	 *            现金流量表表体
	 */
	MoneyDetail saveMoneyDetails(Request request, MoneyDetail moneyDetail);

	/**
	 * 保存资产负债表体
	 * 
	 * @param request
	 *            发送请求
	 * @param debtDetail
	 *            资产负债表表体
	 */
	DebtDetail saveDebtDetail(Request request, DebtDetail debtDetail);

	/**
	 * 保存利润表体
	 * 
	 * @param request
	 *            发送请求
	 * @param marginDetail
	 *            利润表表体
	 */
	MarginDetail saveMarginDetails(Request request, MarginDetail marginDetail);

	/**
	 * 增加现金流量表体
	 * 
	 * @param request
	 *            发送请求
	 * @param moneyMaster
	 *            现金流量表头
	 */
	void moneyDetailadd(Request request, MoneyMaster moneyMaster);

	/**
	 * 增加资产负债表
	 * 
	 * @param request
	 *            发送请求
	 * @param debtMaster
	 *            资产负债表头
	 */
	void DebtDetailadd(Request request, DebtMaster debtMaster);

	/**
	 * 增加利润表体
	 * 
	 * @param request
	 *            发送请求
	 * @param marginMaster
	 *            利润表头
	 */
	void MarginDetailadd(Request request, MarginMaster marginMaster);

	/**
	 * 现金流量计算
	 * 
	 * @param request
	 *            发送请求
	 * @param moneyMaster
	 *            现金流量表头
	 * @param times
	 *            行次
	 */
	void account(Request request, MoneyMaster moneyMaster, String times);

	/**
	 * 利润表计算
	 * 
	 * @param request
	 *            发送请求
	 * @param marginMaster
	 *            利润表表头
	 */
	void marginAccount(Request request, MarginMaster marginMaster);

	/**
	 * 资产负债统计情况
	 * 
	 * @param request
	 *            发送请求
	 * @param debtMaster
	 *            资产负债表头
	 * @param times
	 *            行次
	 * @param beginEnd
	 *            期初或期末数
	 */
	void debtAccount(Request request, DebtMaster debtMaster, String times,
			String beginEnd);

	/**
	 * 加工贸易原材料来源与使用情况表
	 * 
	 * @param request
	 *            发送请求
	 * @param conditions
	 *            conditions中包括对单据生效日期的过滤
	 * @return returnAsCustom false 工厂资料 true 海关资料
	 */
	List findImgOrgUseInfos(Request request, Date beginDate, Date endDate,
			String taskId, Boolean isShowTranFact);

	/**
	 * 单据报关单对比
	 * 
	 * @param request
	 *            发送请求
	 * @param conditions
	 *            查询条件包括有效期
	 * @return 海关帐中存在的料件单据与进口报关单对比
	 */
	List findBillCustomBillCmpImgInfos(Request request, Date beginDate,
			Date endDate);

	/**
	 * 4． 已结转未收货: 当相同客户名称的 （ “结转进口” 单据折算报关数量的累加 －“结转料件退货单” 单据折算报关数量的累加 <
	 * “结转报关进口”数量的累加 ）时, 所有客户两者差额值的和。 carryForwardF3Map 已结转未收货
	 * 
	 * 5． 未结转已收货: 当相同客户名称的 （ “结转进口” 单据折算报关数量的累加 －“结转料件退货单” 单据折算报关数量的累加 >“结转报关进口”
	 * 数量的累加 ）时, 所有客户两者差额值的和。 carryForwardF4Map 未结转已收货 *
	 * 
	 * @param request
	 *            发送请求
	 * @param conditions
	 *            查询条件
	 * @param scmCoc
	 *            客户
	 * @return 加工贸易原材料来源与使用情况表中的结转收货对比信息
	 */
	List findCarryForwardCmpImgInfos(Request request, Date beginDate,
			Date endDate, ScmCoc scmCoc);

	/**
	 * 显示保存出口成品信息
	 * 
	 * @param request
	 *            发送请求
	 * @param conditions
	 *            conditions中包括对单据生效日期的过滤
	 * @return 生效日期过滤后的出口成品信息
	 */
	List findExgExportInfos(Request request, Date beginDate, Date endDate,
			String taskId, Boolean isShowTranFact);

	/**
	 * 单据报关单对比
	 * 
	 * @param request
	 *            发送请求
	 * @param conditions
	 *            查询条件
	 * @return 海关帐中存在的有记录的单据与报关单比对
	 */
	List findBillCustomBillCmpExgInfos(Request request, Date beginDate,
			Date endDate);

	/**
	 * 结转成品对比
	 * 
	 * @param request
	 *            发送请求
	 * @param conditions
	 *            查询条件
	 * @param scmCoc
	 *            客户/供应商
	 * @return 按客户分的结转成品比对
	 */
	List findCarryForwardCmpExgInfos(Request request, Date beginDate,
			Date endDate, ScmCoc scmCoc);

	// /**
	// * 获得在单据中商品信息不是全部转为真的单据记录 Tfe == 结转出口
	// *
	// * @param request
	// * 发送请求
	// * @param scmCocId
	// * 客户id
	// * @return 单据类型为成品出入库的单据中商品信息不是全部转为真的单据记录
	// */
	// List findTempBillMasterIsAvailabilityToTFBByTfe(Request request,
	// String scmCocId);
	//
	// /**
	// * 获得在单据中商品信息不是全部转为真的单据记录 Tfi == 结转进口
	// *
	// * @param request
	// * 发送请求
	// * @param scmCocId
	// * 客户id
	// * @return 单据类型为料件入库的单据中商品信息不是全部转为真的单据记录
	// */
	// List findTempBillMasterIsAvailabilityToTFBByTfi(Request request,
	// String scmCocId);

	/**
	 * 显示单据明细来自已选商品信息
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            临时单据明细
	 * @return 根据已选商品单据获得其所有未转结转单的商品信息
	 */
	List findBillDetailByParent(Request request, List list);

	/**
	 * 获得在单据中商品信息不是全部转为真的单据记录 IXRB == Import Exprot Request Bill 进出口申请单
	 * 
	 * @param request
	 *            发送请求
	 * @param impExpType
	 *            单据类型
	 * @return 与指定类型匹配的单据商品信息不是全部转为真的单据记录
	 */
	List findBillMasterIsAvailabilityToIXRB(Request request, int impExpType);

	/**
	 * 根据已选商品单据获得其所有未转进出口的商品信息 IXRB=improt expromt request bill
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            临时单据表头明细
	 * @return 所有未转进出口的商品信息
	 */
	List findBillDetailByParentToIMXP(Request request, List list);

	/**
	 * 有数据已转进出口申请单来自海关账单据头
	 * 
	 * @param request
	 *            发送请求
	 * @param b
	 *            单据表头
	 * @return 所有已转进出口申请单
	 */
	boolean hasDataTransferImpExpRequestBillByBillMaster(Request request,
			BillMaster b);

	/**
	 * 有数据已转结转单单据来自海关账单据体
	 * 
	 * @param request
	 *            发送请求
	 * @param b
	 *            单据头
	 * @return 所有已转结转单据单单据
	 */
	boolean hasDataTransferTransferFactoryBillByBillMaster(Request request,
			BillMaster b);

	/**
	 * 显示资料主表 统计报关名称
	 * 
	 * @param request
	 *            发送请求
	 * @return 工厂大类内容
	 */
	List findStatCusNameRelation(Request request);

	/**
	 * 保存大类内容
	 * 
	 * @param request
	 *            发送请求
	 * @param statCusNameRelation
	 *            大类内容
	 * @return 临时大类内容
	 */
	List<TempStatCusNameRelation> saveStatCusNameRelation(Request request,
			StatCusNameRelation statCusNameRelation);

	/**
	 * 删除资料主表
	 * 
	 * @param request
	 *            发送请求
	 * @param statCusNameRelation
	 *            大类内容资料主表
	 */
	void deleteStatCusNameRelation(Request request,
			StatCusNameRelation statCusNameRelation);

	/**
	 * 显示常用物料主档
	 * 
	 * @param billType
	 *            单据类型
	 * @return list 与指定单据类型相匹配表名的表头
	 */
	String findMateriel(List list);

	/**
	 * 显示资料副表 工厂料件
	 * 
	 * @param request
	 *            发送请求
	 * @param statCusNameRelation
	 *            资料主表内容
	 * @return 与主表内容相对应的工厂料件
	 */
	List findStatCusNameRelationMt(Request request,
			StatCusNameRelation statCusNameRelation);

	/**
	 * 保存资料副表 工厂料件
	 * 
	 * @param request
	 *            发送请求
	 * @param statCusNameRelationMt
	 *            工厂料件
	 */
	StatCusNameRelationMt saveStatCusNameRelationMt(Request request,
			StatCusNameRelationMt statCusNameRelationMt);

	/**
	 * 删除资料副表 工厂料件
	 * 
	 * @param request
	 *            发送请求
	 * @param statCusNameRelationMt
	 *            工厂料件
	 */
	void deleteStatCusNameRelationMt(Request request,
			StatCusNameRelationMt statCusNameRelationMt);

	/**
	 * 显示资料副表 实际报关商品
	 * 
	 * @param request
	 *            发送请求
	 * @param statCusNameRelation
	 *            资料主表商品大类
	 * @return 与指定的商品大类对应的按序号排序的实际报关商品内容
	 */
	List findStatCusNameRelationHsn(Request request,
			StatCusNameRelation statCusNameRelation);

	/**
	 * 保存资料副表 实际报关商品
	 * 
	 * @param request
	 *            发送请求
	 * @param statCusNameRelationHsn
	 *            实际报关商品
	 * @return 实际报关商品
	 */
	StatCusNameRelationHsn saveStatCusNameRelationHsn(Request request,
			StatCusNameRelationHsn statCusNameRelationHsn);

	/**
	 * 删除资料副表 实际报关商品
	 * 
	 * @param request
	 *            发送请求
	 * @param statCusNameRelationHsn
	 *            实际报关商品
	 */
	void deleteStatCusNameRelationHsn(Request request,
			StatCusNameRelationHsn statCusNameRelationHsn);

	/**
	 * 新增对照关系时，选择物料
	 * 
	 * @param request
	 *            发送请求
	 * @param materialType
	 *            物料类型
	 * @return 与商品大类关联的物料内容
	 */
	List findMaterialForRelation(Request request, String materialType);

	/**
	 * 保存资料副表 实际报关商品
	 * 
	 * @param request
	 *            发送请求
	 * @param statCusNameRelation
	 *            实际报关商品
	 * @param lsTempCommInfo
	 *            临时的商品信息 实际报关商品
	 * @return 实际报关商品
	 */
	List<TempStatCusNameRelation> saveStatCusNameRelationHsn(Request request,
			StatCusNameRelation statCusNameRelation, List lsTempCommInfo);

	/**
	 * 保存资料副表 工厂料件
	 * 
	 * @param request
	 *            发送请求
	 * @param statCusNameRelation
	 *            商品大类 资料主表
	 * @param lsTempCommInfo
	 *            临时的商品信息 企业物料
	 * @return 与商品大类相关联的工厂料件
	 */
	List<TempStatCusNameRelation> saveStatCusNameRelationMt(Request request,
			StatCusNameRelation statCusNameRelation, List lsTempCommInfo);

	/**
	 * 删除资料副表 实际报关商品
	 * 
	 * @param request
	 *            发送请求
	 * @param lsRelation
	 *            临时关系对照表
	 * @return 实际报关商品
	 */
	List<TempStatCusNameRelation> deleteStatCusNameRelationHsn(Request request,
			List lsRelation);

	/**
	 * * 删除资料副表 工厂料件
	 * 
	 * @param request
	 *            发送请求
	 * @param lsRelation
	 *            临时商品大类关系对照表
	 * @return 工厂物料
	 */
	List<TempStatCusNameRelation> deleteStatCusNameRelationMt(Request request,
			List lsRelation);

	/**
	 * 新增商品大类时,进行大类编码查询
	 * 
	 * @param request
	 *            发送请求
	 * @param materialType
	 *            物料类型
	 * @return 不在商品大类中存在的商品编码
	 */
	List findComplex(Request request, String materialType);

	List findComplex(Request request);

	List findUnit(Request request);

	/**
	 * 新增对照关系时，选择报关商品
	 * 
	 * @param request
	 *            发送请求
	 * @param materialType
	 *            物料类型
	 * @return 与商品大类关联的实际报关商品内容
	 */

	List findComplexForRelation(Request request, String materialType);

	/**
	 * 显示资料主表 统计报关名称
	 * 
	 * @param request
	 *            发送请求
	 * @return 工厂大类内容
	 */
	List findStatCusNameRelation(Request request, String materialType);

	/**
	 * 显示单据类型
	 * 
	 * @param request
	 *            发送请求
	 * @param materielType
	 *            物料类型
	 * @return List 与指定的物料类型匹配的单据类型
	 */
	public List findBillTypeByProduceType(Request request, String materielType);

	/**
	 * 显示单据类型
	 * 
	 * @param request
	 *            发送请求
	 * @param billType
	 *            单据类型
	 * @return 单据类型
	 */
	public List findBillType(Request request, int billType);

	/**
	 * 显示单据资料主表
	 * 
	 * @param request
	 *            发送请求
	 * @param billType
	 *            单据类型
	 * @return 与指定的单据类型匹配的单句表头资料
	 */
	public List findBillMaster(Request request, BillType billType);

	/**
	 * 检查是否重复
	 * 
	 * @param request
	 *            发送请求
	 * @param code
	 *            单据类型代码
	 * @return 根据单据类型代码得到的单据类型
	 */
	public BillType findBillTypeByCode(Request request, String code);

	/**
	 * 检查工厂名称是否重复
	 * 
	 * @param request
	 *            发送请求
	 * @param code
	 *            单据类型工厂名称
	 * @return 根据单据类型工厂名称得到的单据类型
	 */
	public List findBillTypeByFactoryName(Request request, String factoryName);

	/**
	 * 保存单据类型
	 * 
	 * @param request
	 *            发送请求
	 * @param billType
	 *            单据类型
	 * @return 单据类型
	 */
	public BillType saveBillType(Request request, BillType billType);

	/**
	 * 删除单据类型
	 * 
	 * @param request
	 *            发送请求
	 * @param billType
	 *            单据类型
	 */
	public void deleteBillType(Request request, BillType billType);

	/**
	 * 显示企业物料
	 * 
	 * @param request
	 *            发送请求
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            property的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 企业物料
	 */
	List<StatCusNameRelationMt> findStatCusNameRelationMt(Request request,
			String materielType, int index, int length, String property,
			Object value, boolean isLike);

	/**
	 * 分页查询所有的物料与报关资料对照关系表
	 * 
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 物料和物料与报关商品折算比的list
	 */
	public List<TempObject> findFactoryAndFactualCustomsRalation(
			Request request, String materielType, int index, int length,
			String property, Object value, boolean isLike);

	/**
	 * 工厂物料根据对应表自动对应报关商品
	 * 
	 * @param request
	 * @param ptNo
	 *            物料号
	 * @param compareDate
	 *            用来比较的日期
	 * @return
	 */
	public FactoryAndFactualCustomsRalation findFactualCustoms(Request request,
			String ptNo, Date compareDate);

	/**
	 * 分页查询工厂物料根据物料与报关商品对照表
	 * 
	 * @param ptNo
	 *            物料号
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return
	 */
	public List<FactoryAndFactualCustomsRalation> findFactualCustoms(Request request,
			String ptNo, int index, int length, String property, Object value,
			boolean isLike);

	/**
	 * 分页查询工厂物料根据大类所对应的十码资料
	 * 
	 * @param ptNo
	 *            物料号
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 大类所对应的十码资料
	 */
	List<StatCusNameRelationHsn> findStatCusNameRelationHsn(Request request,
			String ptNo, int index, int length, String property, Object value,
			boolean isLike);

	/**
	 * 工厂物料根据大类自动对应十码资料
	 * 
	 * @param request
	 * @param ptNo
	 *            物料号
	 * @param compareDate
	 *            用来比较的日期
	 * @return
	 */
	public StatCusNameRelationHsn findStatCusNameRelationHsn(Request request,
			String ptNo, Date compareDate);

	/**
	 * 当删除物料或者报关商品的时候,检查所选择的资料是否属于同一个大类,如果true,允许删除, 否则不能删除
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            临时商品对照关系表
	 * @return 如果true,允许删除, 否则不能删除
	 */
	boolean checkIsSameRelation(Request request,
			List<TempStatCusNameRelation> list);

	/**
	 * 当删除修改物料的时候,检查所选择的资料是否属于同一个物料,如果true,允许修改, 否则不能修改
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            对照关系表中的物料
	 * @return true,允许修改, 否则不能修改
	 */
	boolean checkIsSameMt(Request request, List<TempStatCusNameRelation> list);

	/**
	 * 当删除修改报关商品的时候,检查所选择的资料是否属于同一个报关商品,如果true,允许修改, 否则不能修改
	 * 
	 * @param request
	 *            发送请求 发送请求
	 * @param list
	 *            对照关系中的实际报关商品
	 * @return true,允许修改, 否则不能修改
	 */
	boolean checkIsSameHsn(Request request, List<TempStatCusNameRelation> list);

	/**
	 * 显示单据对应控制对象
	 * 
	 * @param request
	 *            发送请求
	 * @return 单据对应控制对象
	 */
	BillCorrespondingControl findBillCorrespondingControl(Request request);

	/**
	 * 显示海关帐基本单据项目设定
	 * 
	 * @param request
	 *            发送请求
	 * @return 海关帐单据项目设定 (单据是否允许重复 双击是否修改还是流览.......)
	 */
	CasBillOption findCasBillOption(Request request);

	/**
	 * 显示制单号控制对象
	 * 
	 * @param request
	 *            发送请求
	 * @return 制单号控制对象 (车间入库单据录入时需要制造令 车间返回单据录入时需要制造令......)
	 */
	LotControl findLotControl(Request request);

	/**
	 * 显示其它项目选项
	 * 
	 * @param request
	 *            发送请求
	 * @return 其他选项(名称规格是否重复 进出仓栏只显示月结存数......)
	 */
	OtherOption findOtherOption(Request request);

	/**
	 * 显示记帐年度对象
	 * 
	 * @param request
	 *            发送请求
	 * @return 记帐年度
	 */
	WriteAccountYear findWriteAccountYear(Request request);

	/**
	 * 保存单据对应控制对象
	 * 
	 * @param request
	 *            发送请求
	 * @param temp
	 *            单据对应控制对象
	 * @return 单据对应控制对象
	 */
	BillCorrespondingControl saveBillCorrespondingControl(Request request,
			BillCorrespondingControl temp);

	/**
	 * 保存海关帐基本单据项目设定
	 * 
	 * @param request
	 *            发送请求
	 * @param temp
	 *            海关帐单据项目设定 (单据是否允许重复 双击是否修改还是流览.......)
	 * @return 海关帐基本单据项目设定
	 */
	CasBillOption saveCasBillOption(Request request, CasBillOption temp);

	/**
	 * 保存制单号控制对象
	 * 
	 * @param request
	 *            发送请求
	 * @param temp
	 *            制单号控制对象 (车间入库单据录入时需要制造令 车间返回单据录入时需要制造令......)
	 * @return 制单号控制对象
	 */
	LotControl saveLotControl(Request request, LotControl temp);

	/**
	 * 保存其它项目选项
	 * 
	 * @param request
	 *            发送请求
	 * @param temp
	 *            其他选项(名称规格是否重复 进出仓栏只显示月结存数......)
	 * @return 其它选项
	 */
	OtherOption saveOtherOption(Request request, OtherOption temp);

	/**
	 * 保存记帐年度对象
	 * 
	 * @param request
	 *            发送请求
	 * @param temp
	 *            记帐年度
	 * @return 记帐年度
	 */
	WriteAccountYear saveWriteAccountYear(Request request, WriteAccountYear temp);

	/**
	 * 显示单据对应选项
	 * 
	 * @param request
	 *            发送请求
	 * @return 单据对应选项
	 */
	BillCorrespondingOption findBillCorrespondingOption(Request request);

	/**
	 * 保存单据对应选项
	 * 
	 * @param request
	 *            发送请求
	 * @param temp
	 *            单据对应选项
	 * @return 单据对应选项
	 */
	BillCorrespondingOption saveBillCorrespondingOption(Request request,
			BillCorrespondingOption temp);

	/**
	 * 显示单据的对应的单据明细(生效的记录)
	 * 
	 * @param request
	 *            发送请求
	 * @param impExpType
	 *            进出口类型
	 * @param scmCoc
	 *            客户/供应商
	 * @param beginData
	 *            开始日期
	 * @param endData
	 *            截止日期
	 * @param name
	 *            名称
	 * @param spec
	 *            规格
	 * @param isNameSpec 判断是选择名称(true)还是名称+规格(false)查询
	 * @return 临时单据明细
	 */
	List<BillDetail> findBillDetail(Request request, Integer impExpType,
			ScmCoc scmCoc, Date beginData, Date endData, String nameSpec,Boolean isNameSpec);

	/**
	 * 保存报关单商品信息与海关帐单据的对应
	 * 
	 * @param request
	 *            发送请求
	 * @param c
	 *            报关单商品信息与海关帐单据的对应
	 * @return 报关单商品信息与海关帐单据的对应
	 */
	CustomsDeclarationCommInfoBillCorresponding saveCustomsDeclarationCommInfoBillCorresponding(
			Request request, CustomsDeclarationCommInfoBillCorresponding c);

	/**
	 * 显示报关单商品信息与海关帐单据的对应
	 * 
	 * @param request
	 *            发送请求
	 * @param impExpType
	 *            进出口类型
	 * @param scmCoc
	 *            客户/供应商
	 * @param beginData
	 *            开始日期
	 * @param endData
	 *            截止日期
	 * @param name
	 *            名称
	 * @param spec
	 *            规格
	 * @param isNameSpec 判断是选择名称(true)还是名称+规格(false)查询
	 * @return 已经对应的报关单商品信息和海关帐单据
	 */
	List findCustomsDeclarationCommInfoBillCorresponding(Request request,
			Integer impExpType, ScmCoc scmCoc, Date beginData, Date endData,
			String name, String spec,Boolean isNameSpec);

	/**
	 * 显示对应表商品大类名称 注意：必须Distinct 因为名称有可能会重复。
	 * 
	 * @param request
	 *            发送请求
	 * @param materielType
	 *            物料类型
	 * @return 与物料类型匹配的商品大类名称
	 */
	List findStatCusNameRelationName(Request request, String materielType);

	/**
	 * 显示客户名称列表
	 */
	List findIsCustomer(Request request);
	/**
	 * 显示对应表商品大类名称 注意：必须Distinct 因为名称有可能会重复。
	 * 
	 * @param request
	 *            发送请求
	 * @param materielType
	 *            物料类型
	 * @return 与物料类型匹配的商品大类名称
	 */
	List findStatCusNameRelationName2(Request request, String materielType);

	/**
	 * 根据大类规格查找相对应的报关规格
	 * 
	 * @param request
	 *            发送请求
	 * @param materielType
	 *            物料类型
	 * @param cusName
	 *            报关名称
	 * @return 与大类规格对应的报关规格
	 */
	List findStatCusNameRelationSpec(Request request, String materielType,
			String cusName);

	/**
	 * 按单据名称返回整个单据
	 * 
	 * @param request
	 *            发送请求
	 * @param mostlyBillTypeName
	 *            类似的单据类型名称
	 * @param billTypeName
	 *            单据类型名称
	 * @return 与指定的单据类型名称匹配的临时单据
	 */
	List findBillTempDetailByBillTypeName(Request request,
			String mostlyBillTypeName, String billTypeName, Date beginDate,
			Date endDate);

	/**
	 * 显示报关商品信息，来自对应的大类信息
	 * 
	 * @param request
	 *            发送请求
	 * @param complexCode
	 *            商品编码
	 * @param name
	 *            名称
	 * @param spec
	 *            规格
	 * @param materielType
	 *            物料类型
	 * @return 大类信息对应的报关商品信息
	 */
	List findStatCusNameRelationHsn(Request request, String complexCode,
			String name, String spec, String materielType);

	/**
	 * 取消单据对应
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            生产单据对应的临时中间信息
	 */
	void cancelCorresponding(Request request,
			List<MakeBillCorrespondingInfoBase> list);

	/**
	 * 写入单据(手工批量对应操作)
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            单据表头
	 * @param corresponding
	 *            对应报关单号
	 */
	void writeIn(Request request, List<BillMaster> list, String corresponding);

	/**
	 * 显示单据资料主表来自类型
	 * 
	 * @param request
	 *            发送请求
	 * @param impExpType
	 *            进出口类型
	 * @return 与指定的进出口类型的单据资料主表
	 */
	List findBillMaster(Request request, Integer impExpType);

	/**
	 * 显示所有合同协议号
	 * 
	 * @param request
	 *            发送请求
	 * @param impExpType
	 *            进出口类型
	 * @return 与指定的进出口类型匹配且来自纸质手册的合同协议号
	 */
	List findEmsNo(Request request);

	/**
	 * 显示所有报关单号
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpType
	 *            进出口类型
	 * @return 与指定的进出口类型相同且来自纸质手册和联网监管的报关单号
	 */
	List findCustomsDeclarationCode(Request request, Integer impExpType,
			String emsNo);

	/**
	 * 记帐，和帐务记帐回卷
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            单据表头
	 * @param isEffective
	 *            是否记帐
	 * @return 保存记帐，和帐务记帐回卷的内容
	 */
	List saveBillMaster(Request request, List<BillMaster> list,
			boolean isEffective);

	/**
	 * 显示单据是否记帐
	 * 
	 * @param request
	 *            发送请求
	 * @param materielType
	 *            物料类型
	 * @param isEffective
	 *            是否生效
	 * @return 已记帐的单据
	 */
	List findBillMaster(Request request, String materielType,
			boolean isEffective);

	/**
	 * 依单据类型返回报关单列表
	 * 
	 * @param request
	 *            发送请求
	 * @param materialtype
	 *            物料类型
	 * @return 与指定的物料类型匹配并且没有记帐的单据
	 */
	List findBillMasterWithMaterielType(Request request, String materialtype);

	/**
	 * 依单据类型回滚报关单
	 * 
	 * @param request
	 *            发送请求
	 * @param billmasterlist
	 *            需要回滚的单据对象
	 * @return 返回回卷成功与出错的记录
	 */
	List saveRollbackBillMaster(Request request, List billmasterlist);

	/**
	 * 执行 生效的单据表头
	 * 
	 * @param request
	 *            发送请求
	 * @param billMasterList
	 *            需要更新的对象
	 * @return 更新不成功的对象,全部成功返回size为0的List.
	 */
	List saveEffectBillMasterAll(Request request,
			List<BillMaster> billMasterList);

	/**
	 * 保存生效单据
	 * 
	 * @param request
	 *            发送请求
	 * @param billType
	 *            单据类型
	 * @param isEffect
	 *            是否生效
	 */
	void saveBillMasterEffect(Request request, BillType billType,
			boolean isEffect);

	/**
	 * 批量删除单据 返回删除成功与出错的记录
	 * 
	 * @param request
	 *            发送请求
	 * @param billmasterlist
	 *            需要删除的单据对象
	 */
	void deleteBatchBillMaster(Request request, List<BillMaster> billmasterlist);

	/**
	 * 显示所有仓库的信息
	 * 
	 * @param request
	 *            发送请求
	 * @return 仓库信息
	 */
	List<WareSet> findWareSet(Request request);

	/**
	 * 保存料号级盘点导入
	 * 
	 * @param ls
	 */
	void saveCheckBalanceList(Request request, List ls);

	/**
	 * 显示所有工厂名称信息
	 * 
	 * @param request
	 *            发送请求
	 * @param kindCode
	 *            类型代码
	 * @return 工厂名称信息
	 */
	List findFactoryNames(Request request, String kindCode);

	/**
	 * 显示单据头来自单据明细
	 * 
	 * @param request
	 *            发送请求
	 * @param productkind
	 *            商口类型
	 * @param billtype
	 *            单据类型
	 * @param client
	 *            客户名称
	 * @param startdate
	 *            开始日期
	 * @param enddate
	 *            截止日期
	 * @return 与指定条件匹配的单据头
	 */
	List<BillMaster> findBillMasterFromBillDetail(Request request,
			String productkind, String billtype, String client, Date startdate,
			Date enddate);

	/**
	 * 显示单据资料明细表来自Id和类型
	 * 
	 * @param request
	 *            发送请求
	 * @param id
	 *            明细表id
	 * @param sBillType
	 *            单据类型
	 * @return 与指定表名对应的单据明细
	 */
	BillDetail findBillDetailById(Request request, String id, int sBillType);

	/**
	 * 显示单据资料明细表来自表头id和单据类型
	 * 
	 * @param request
	 *            发送请求
	 * @param masterId
	 *            表头id
	 * @param sBillType
	 *            单据类型
	 * @return 工厂单据明细
	 */
	List<BillDetail> findBillDetail(Request request, String masterId,
			int sBillType);

	/**
	 * 分页查询 显示单据资料表头来自类型 属性 实体值等
	 * 
	 * @param request
	 *            发送请求
	 * @param billType
	 *            单据类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            property的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 单据表头资料
	 */
	List<BillMaster> findBillMaster(Request request, BillType billType,
			int index, int length, String property, Object value, boolean isLike);

	/**
	 * 查询工厂单据
	 * 
	 * @param billType
	 * @param billNo
	 * @param scmCoc
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<BillMaster> findBillMaster(Request request, BillType billType,
			String billNo, String scmCoc, Date beginDate, Date endDate);

	/**
	 * 生成对应表
	 * 
	 * @param request
	 *            发送请求
	 * @param materielType
	 *            物料类型
	 * @param param
	 *            模块类型
	 * @return 刷新所有对应表
	 */
	boolean saveCorrespondence(Request request, String materielType,
			ProjectTypeParam param);

	/**
	 * 抓取对照表主表
	 * 
	 * @param request
	 *            发送请求
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 与指定物料类型匹配的商品大类
	 */
	List<TempStatCusNameRelation> findStatCusNameRelation(Request request,
			String materielType, int index, int length, String property,
			Object value, boolean isLike);

	/**
	 * 单据对应
	 * 
	 * @param request
	 *            发送请求
	 * @param listC
	 *            报关单商品信息与海关帐单据的对应
	 * @param list
	 *            临时单据明细
	 * @return 单据对应 报关单商品信息与海关帐单据的对应
	 */
	public TempResult billCorresponding(Request request,
			List<CustomsDeclarationCommInfoBillCorresponding> listC,
			List<BillDetail> list);

	/**
	 * 清除单据对应
	 * 
	 * @param request
	 */
	void deleteBillCorresponding(Request request);

	/**
	 * 取得海关帐单据的入出汇总
	 * 
	 * @param request
	 *            发送请求
	 * @param intOutFlat
	 *            物料类型
	 * @param conditions
	 *            查询条件
	 * @param conditions1
	 *            查询条件
	 * @return 单据明细
	 */
	List getCasSum(Request request, String materielType,
			List<Condition> conditions, boolean showZeroStore,
			boolean showVersion);

	/**
	 * 取得本月数量 月报表
	 * 
	 * @param request
	 *            发送请求
	 * @param materielType
	 *            物料类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isRefresh
	 *            重新计算本月前的库存数量(这里只计算当月的前一个月的库存数量)
	 * @return 返回本月数量
	 */
	List getMonth(Request request, String materielType, Date beginDate,
			Date endDate, boolean isRefresh);

	/**
	 * 取得本月数量 (notExistList ==null) 要全部重新生成以前月的记录 (notExistList !=null)
	 * 生成不存在的月报表
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isRefresh
	 *            重新计算本月前的库存数量(这里只计算当月的前一个月的库存数量)
	 * @return 返回本月数量
	 */
	List getAllMonth(Request request, String materielType,
			List<Integer> notExistList, Date beginDate, Date endDate,
			boolean isRefresh);

	/**
	 * 显示所有当点月存在的
	 * 
	 * @param request
	 *            发送请求
	 * @param materielType
	 *            物料类型
	 * @param currentMonth
	 *            本月
	 * @return 当前月存在的
	 */
	List<Integer> findFrontMonthIsNotExist(Request request,
			String materielType, int currentMonth);

	/**
	 * 取得本月数量 月报表
	 * 
	 * @param request
	 *            发送请求
	 * @param materielType
	 *            物料类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isRefresh
	 *            重新计算本月前的库存数量(这里只计算当月的前一个月的库存数量)
	 * @return 返回本月数量
	 */
	List getMonth2(Request request, String materielType, Date beginDate,
			Date endDate, boolean isRefresh);

	/**
	 * 取得本月数量 (notExistList ==null) 要全部重新生成以前月的记录 (notExistList !=null)
	 * 生成不存在的月报表
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isRefresh
	 *            重新计算本月前的库存数量(这里只计算当月的前一个月的库存数量)
	 * @return 返回本月数量
	 */
	List getAllMonth2(Request request, String materielType,
			List<Integer> notExistList, Date beginDate, Date endDate,
			boolean isRefresh);

	/**
	 * 显示所有当点月存在的
	 * 
	 * @param request
	 *            发送请求
	 * @param materielType
	 *            物料类型
	 * @param currentMonth
	 *            本月
	 * @return 当前月存在的
	 */
	List<Integer> findFrontMonthIsNotExist2(Request request,
			String materielType, int currentMonth);

	/**
	 * 成品出口PK对应
	 * 
	 * @param request
	 *            发送请求
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 */
	void saveBillCorresByPK(Request request, Date beginDate, Date endDate);

	/**
	 * 直接进口单据对应集装箱
	 * 
	 * @param request
	 *            发送请求
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 */
	void saveBillCorresByContainer(Request request, Date beginDate, Date endDate);

	/**
	 * 返回工厂商品名称来自单据明细
	 * 
	 * @param request
	 *            发送请求
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 与指定的物料类型等对应工厂商品名称
	 */
	List<TempObject> findPtNameFromBillDetail(Request request,
			String materielType, int index, int length, String property,
			Object value, boolean isLike);

	/**
	 * 返回工厂商品规格来自单据明细
	 * 
	 * @param request
	 *            发送请求
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 与指定的物料类型等对应工厂商品名称
	 */
	List<TempObject> findPtSpecFromBillDetail(Request request,
			String materielType, int index, int length, String property,
			Object value, boolean isLike, String ptName);

	List<TempObject> findPtNameFromInvoice(Request request, int index,
			int length, String property, Object value, boolean isLike);

	/**
	 * 返回实际报关的商品名称来自实际报关商品
	 * 
	 * @param request
	 *            发送请求
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 实际报关商品名称
	 */
	List<TempObject> findHsNameFromStatCusNameRelationHsn(Request request,
			String materielType, int index, int length, String property,
			Object value, boolean isLike);

	/**
	 * 返回实际报关的商品名称来自实际报关商品
	 * 
	 * @param request
	 *            发送请求
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 实际报关商品名称
	 */
	List<TempObject> findHsNameFromFactoryAndFactualCustomsRalation(
			Request request, String materielType, int index, int length,
			String property, Object value, boolean isLike);

	/**
	 * 查询转厂关封单据中的商品.
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 实际报关商品名称
	 */
	public List<TempObject> findHsNameFromTransferFactoryBill(Request request,
			boolean isImp, int index, int length, String property,
			Object value, boolean isLike);

	/**
	 * 返回报关商品名称规格来自关封
	 * 
	 * @param request
	 *            请求控制
	 * @param cusName
	 *            客户名称
	 * @param hsName
	 *            报关商品名称
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 商品大类报关规格
	 */
	List<TempObject> findHsNameFromCustomsEnvelopCommodityInfo(Request request,
			String cusName, String hsName, int index, int length,
			String property, Object value, boolean isImp, boolean isName,
			boolean isLike);

	/**
	 * 返回商品大类报关规格来自实际报关商品
	 * 
	 * @param request
	 *            发送请求
	 * @param materielType
	 *            物料类型
	 * @param hsName
	 *            保管商品名称
	 * @param index
	 *            数据开始的下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 商品大类报关规格
	 */
	List<TempObject> findHsSpecFromStatCusNameRelationHsn(Request request,
			String materielType, String hsName, int index, int length,
			String property, Object value, boolean isLike);

	/**
	 * 返回商品大类报关规格来自实际报关商品
	 * 
	 * @param request
	 *            发送请求
	 * @param materielType
	 *            物料类型
	 * @param hsName
	 *            保管商品名称
	 * @param index
	 *            数据开始的下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 商品大类报关规格
	 */
	List<TempObject> findHsSpecFromFactoryAndFactualCustomsRalation(
			Request request, String materielType, String hsName, int index,
			int length, String property, Object value, boolean isLike);

	List<TempObject> findHalfProductHsSpec(Request request,
			String materielType, String hsName, int index, int length,
			String property, Object value, boolean isLike);

	List<TempObject> findDetailProductHsSpec(Request request,
			String materielType, String hsName, int index, int length,
			String property, Object value, boolean isLike);

	/**
	 * 成品名称
	 * 
	 * @param request
	 * @param materielType
	 * @param hsName
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 * @author 石小凯
	 */
	List<TempObject> findDetailProductHsName(Request request,
			String materielType, String hsName, int index, int length,
			String property, Object value, boolean isLike);

	/**
	 * 半成品名称
	 * 
	 * @param request
	 * @param materielType
	 * @param hsName
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 * @author 石小凯
	 */
	List<TempObject> findDetailMaterielHsName(Request request,
			String materielType, String hsName, int index, int length,
			String property, Object value, boolean isLike);

	/**
	 * 残次品名称
	 * 
	 * @param request
	 * @param materielType
	 * @param hsName
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 * @author 石小凯
	 */
	List<TempObject> findDetailRemainProductHsName(Request request,
			String materielType, String hsName, int index, int length,
			String property, Object value, boolean isLike);

	/**
	 * 返回对照表中的报关商品单位来自实际报关商品
	 * 
	 * @param request
	 *            发送请求
	 * @param materielType
	 *            物料类型
	 * @param hsName
	 *            保管商品名称
	 * @param index
	 *            数据开始的下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 对照表中的报关单位
	 */
	List<TempObject> findHsUnitFromFactoryAndFactualCustomsRalation(
			Request request, String materielType, String hsName, int index,
			int length, String property, Object value, boolean isLike);

	/**
	 * 返回工厂商品名称来自工厂物料
	 * 
	 * @param request
	 *            发送请求
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据开始的下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 工厂商品名称
	 */
	List<TempObject> findPtNameFromStatCusNameRelationMt(Request request,
			String materielType, int index, int length, String property,
			Object value, boolean isLike);

	/**
	 * 返回工厂商品名称来自工厂物料
	 * 
	 * @param request
	 *            发送请求
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据开始的下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 工厂商品名称
	 */
	List<TempObject> findPtNameFromFactoryAndFactualCustomsRalation(
			Request request, String materielType, int index, int length,
			String property, Object value, boolean isLike);

	/**
	 * 返回工厂商品规格来自工厂物料
	 * 
	 * @param request
	 *            发送请求
	 * @param materielType
	 *            物料类型
	 * @param ptName
	 *            工厂名称
	 * @param index
	 *            数据开始的下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 工厂商品名称规格
	 */
	List<TempObject> findBWrokOrder(Request request, int index, int length,
			String property, Object value, boolean isLike);

	List<TempObject> findPWrokOrder(Request request, int index, int length,
			String property, Object value, boolean isLike);

	List<TempObject> findMPWrokOrder(Request request, int index, int length,
			String property, Object value, boolean isLike);

	List<TempObject> findMWrokOrder(Request request, int index, int length,
			String property, Object value, boolean isLike);

	/**
	 * 返回工厂商品规格来自工厂物料
	 * 
	 * @param request
	 *            发送请求
	 * @param materielType
	 *            物料类型
	 * @param ptName
	 *            工厂名称
	 * @param index
	 *            数据开始的下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 工厂商品名称规格
	 */
	List<TempObject> findPtSpecFromStatCusNameRelationMt(Request request,
			String materielType, String ptName, int index, int length,
			String property, Object value, boolean isLike);

	/**
	 * 返回工厂商品规格来自工厂物料
	 * 
	 * @param request
	 *            发送请求
	 * @param materielType
	 *            物料类型
	 * @param ptName
	 *            工厂名称
	 * @param index
	 *            数据开始的下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 工厂商品名称规格
	 */
	List<TempObject> findPtSpecFromFactoryAndFactualCustomsRalation(
			Request request, String materielType, String ptName, int index,
			int length, String property, Object value, boolean isLike);

	/**
	 * 生成单据的折算报关数量(未折算报关数量的单据) 当前公司，由客户端调用
	 * 
	 * @param request
	 *            发送请求
	 * @param setupParameters
	 *            生成单据的折算报关数量(未折算报关数量的单据)
	 */
	void makeBillHsAmount(Request request, TempMaterielTypeSetup setupParameters);

	/**
	 * 获得自定义统计资料(原材料统计报表)
	 * 
	 * @param request
	 *            发送请求
	 * @param year
	 *            年度
	 * @param isOverMillion
	 *            是否超过百万
	 * @return 指定年度内的符合条件(是否超过百万)的料件自定义统计报表
	 */
	List findImgOrgUseInfos(Request request, String year, Boolean isOverMillion);

	/**
	 * 获得海关资料(料原材料统计报表)
	 * 
	 * @param request
	 *            发送请求
	 * @param year
	 *            年度
	 * @param isOverMillion
	 *            是否超过百万
	 * @return 指定年度内的符合条件(是否超过百万)的料件统计报表
	 */
	List findImgOrgUseInfoCustoms(Request request, String year,
			Boolean isOverMillion);

	/**
	 * 成品统计报表(自定义)
	 * 
	 * @param request
	 *            发送请求
	 * @param year
	 *            年度
	 * @param isOverMillion
	 *            是否超过百万
	 * @return 统计指定的年度的成品资料(自定义)
	 */
	List findExgExportInfos(Request request, String year, Boolean isOverMillion);

	/**
	 * 成品统计报表(海关)
	 * 
	 * @param request
	 *            发送请求
	 * @param year
	 *            年度
	 * @param isOverMillion
	 *            是否超过百万
	 * @return 统计指定的年度的成品资料(报关)
	 */
	List findExgExportInfoCustoms(Request request, String year,
			Boolean isOverMillion);

	/**
	 * 保存海关统计报表(成品)
	 * 
	 * @param request
	 *            发送请求
	 * @param base
	 *            加工贸易产品流向情况表
	 * @return 加工贸易产品流向情况表
	 */
	ExgExportInfoBase saveExgExportInfoBase(Request request,
			ExgExportInfoBase base);

	/**
	 * 重新载入海关资料(料件来自自定资料)
	 * 
	 * @param request
	 *            发送请求
	 */
	void copyImgOrgUseInfoCustom(Request request);

	/**
	 * 重新载入海关资料(料件来自自定资料)成品
	 * 
	 * @param request
	 *            发送请求
	 */
	void copyExgExportInfoCustom(Request request);

	/**
	 * 统计来自报关单的金额(出口成品) 成品出口 + 转厂出口 + 返工复出 + 一般贸易出口 - 退厂返工
	 * 
	 * @param request
	 *            发送请求
	 * @param isCustoms
	 *            是否是报关用的
	 * @return 报关单金额(出口成品)
	 */
	List<ExgExportInfoBase> saveMoneyFromApplyToCustomsByProduct(
			Request request, Date beginDate, Date endDate, boolean isCustoms);

	/**
	 * 统计来自报关单的金额(进口料件) 料件进口 + 料件转厂 + 一般贸易进口 - 退料出口
	 * 
	 * @param request
	 *            发送请求
	 * @param isCustoms
	 *            是否是报关用
	 * @return 报关单金额 (进口料件)
	 */
	List<ImgOrgUseInfoBase> saveMoneyFromApplyToCustomsByMateriel(
			Request request, Date beginDate, Date endDate, boolean isCustoms);

	/**
	 * 设备计算
	 * 
	 * @param request
	 *            发送请求
	 * @param conditions
	 *            conditions中包括对单据生效日期的过滤
	 * @return 设备单据
	 */
	List calBillFixing(Request request, List<Condition> conditions);

	/**
	 * 重新载入海关资料 设备
	 * 
	 * @param request
	 *            发送请求
	 */
	void copyBillFixingThing(Request request);

	/**
	 * 设备统计报表(自定义)
	 * 
	 * @param request
	 *            发送请求
	 * @param isOverMillion
	 *            是否超过百万
	 * @return 所有设备的内容
	 */
	List findBillFixing(Request request, Boolean isOverMillion);

	/**
	 * 设备统计报表(海关)
	 * 
	 * @param request
	 *            发送请求
	 * @param isOverMillion
	 *            是否超过百万
	 * @return 海关用的设备资料
	 */
	List findBillFixingThing(Request request, Boolean isOverMillion);

	/**
	 * 保存单据对应的物料的单价
	 * 
	 * @param request
	 *            发送请求
	 * @param billDetail
	 *            单据明细
	 */
	void saveOneBillPrice(Request request, BillDetail billDetail);

	/**
	 * 保存所有单据中的包含这个物料的单价
	 * 
	 * @param request
	 *            发送请求
	 * @param billTempDetail
	 *            单据明细
	 * @param materielType
	 *            物料类型
	 */
	void saveAllBillPrice(Request request, BillDetail billTempDetail,
			String materielType);

	/**
	 * 按单据临时列表的单据表头的单据类型查找单据明细
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            临时单据表头
	 * @param mostlyBillTypeName
	 *            接近单据类型名称
	 * @return 单据表头的单据类型查找到的单据明细
	 */
	public List findBillDetailByBillTypeName(Request request, List list,
			String mostlyBillTypeName);

	/**
	 * 保存由半成品转成料件(委外加工进库单据--->委外加工返回料件单据) 1103 == 委外加工返回料件单据 4003 == 委外加工进库单据
	 * 
	 * @param request
	 *            发送请求
	 */
	void save1013By4003(Request request);

	/**
	 * 保存由半成品转成料件(委外加工出库单据--->车间领用单据) 1101 == 车间领用单据 4103 == 委外加工出库单据
	 * 
	 * @param request
	 *            发送请求
	 */
	void save1110By4103(Request request);

	void save1101By4105(Request request);

	void save1112By4104(Request request);

	/**
	 * 删除资料主表
	 * 
	 * @param request
	 *            发送请求
	 * @param selectedRows
	 *            选中的大类内容
	 */
	void deleteStatCusNameRelation(Request request,
			List<TempStatCusNameRelation> selectedRows);

	/**
	 * 保存加工贸易原材料来源与使用情况表
	 * 
	 * @param request
	 *            发送请求
	 * @param base
	 *            加工贸易原材料来源与使用情况表
	 * @return 加工贸易原材料来源与使用情况表
	 */
	ImgOrgUseInfoBase saveImgOrgUseInfoBase(Request request,
			ImgOrgUseInfoBase base);

	/**
	 * 跟据料号和类型获得相应的折算系数
	 * 
	 * @param request
	 *            发送请求
	 * @param materielType
	 *            物料类型
	 * @param ptNo
	 *            料号
	 * @return 折算系数
	 */
	Double getUnitConvertByPtNo(Request request, String materielType,
			String ptNo);

	/**
	 * 获得提示信息
	 * 
	 * @return 生成单据报关数量提示信息
	 */
	String getMakeBillHsAmountTipMessage();

	/**
	 * 委外进出仓帐查询
	 * 
	 * @param request
	 *            发送请求
	 * @param conditions
	 *            查询条件
	 * @return 委外的进出口商品信息
	 */
	List findConsignQuery(Request request, List<Condition> conditions);

	/**
	 * 委外进出仓帐查询
	 * 
	 * @param request
	 *            发送请求
	 * @param conditions
	 *            查询条件
	 * @return 委外的进出口商品信息
	 */
	List findConsignQueryInfo(Request request, ConsignQueryCondition condition);

	/**
	 * 显示单据的个数
	 * 
	 * @param materielType
	 *            物料类别
	 * @return 与物料类别匹配的单据个数
	 */
	Integer findBillDetailCount(String materielType);

	/**
	 * 分页查询 显示单据资料表头来自类型 属性 实体值等
	 * 
	 * @param request
	 *            发送请求
	 * @param billType
	 *            单据类型
	 * @param index
	 *            数据开始的下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 单据表头资料
	 */
	public List<BillMaster> findBillMaster(Request request, BillType billType,
			boolean isEffect, int index, int length, String property,
			Object value, boolean isLike);

	/**
	 * 生成单据的折算报关数量(未折算报关数量的单据)
	 * 
	 * @param request
	 *            发送请求
	 * @param setupParameters
	 *            生成单据的折算报关数量(未折算报关数量的单据)
	 * @return 所有的生成单据的折算报关单的数量
	 */
	String makeBillHsAmountByBatch(Request request,
			TempMaterielTypeSetup setupParameters);

	/**
	 * 根据条件查询单据信息,适用于批量修改料号对应的报关商品信息
	 * 
	 * @param materielType
	 * @param beginDate
	 * @param endDate
	 * @param ptNo
	 * @param scmCoc
	 * @return
	 */
	public List findBillInfo(Request request, String materielType,
			Date beginDate, Date endDate, String ptNo, ScmCoc scmCoc, String nameStyle);

	/**
	 * 批量修改料号对应的报关商品
	 * 
	 * @param list
	 * @param map
	 * @return
	 */
	List BatchUpdateHs(Request request, List<BillDetail> list, String taskId,
			Map<String, Object> map, String materielType);

	/**
	 * 根据料号，查询相对应的报关资料
	 * 
	 * @param ptNos 多个料号逗号分开
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List findHsByPtNo(Request request, String ptNo, int index,
			int length, String property, Object value, boolean isLike);

	/**
	 * 获得边角料所有的数据
	 * 
	 * @param request
	 *            发送请求
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 边角料的所有数据
	 */
	List<LeftoverMateriel> findLeftoverMateriel(Request request, int index,
			int length, String property, Object value, boolean isLike);

	/**
	 * 获得边角料所对应的料件数据
	 * 
	 * @param request
	 *            发送请求
	 * @param leftoverMateriel
	 *            边角料
	 * @return 边角料所对应的料件的资料
	 */
	List<LeftoverMateriel> findLMateriel(Request request,
			LeftoverMateriel leftoverMateriel);

	/**
	 * 保存边角料查询报表内容
	 * 
	 * @param request
	 *            发送请求
	 * @param obj
	 *            边角料查询报表
	 * @return 边角料查询报表
	 */
	LeftoverMaterielBalanceInfo saveLeftoverMaterielBalanceInfo(
			Request request, LeftoverMaterielBalanceInfo obj);

	/**
	 * 获得边角料查询报表内容
	 * 
	 * @param request
	 *            发送请求
	 * @return 边角料查询报表
	 */
	List<LeftoverMaterielBalanceInfo> findLeftoverMaterielBalanceInfo(
			Request request);

	/**
	 * 查找并计算边角料(按报关名称分时间段进行查询)
	 * 
	 * @param request
	 *            发送请求
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param projectTypeParam
	 *            模块类型参数(是来自bcs还是bcus......)
	 * @return 边角料计算结果
	 */
	List<LeftoverMaterielBalanceInfo> findCalLeftoverMateriel(Request request,
			Date beginDate, Date endDate, ProjectTypeParam projectTypeParam,
			boolean isCalcProductToWaste, String taskId);

	/**
	 * 查找并计算短溢表(按报关名称分时间段进行查询)
	 * 
	 * @param request
	 *            发送请求
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param projectTypeParam
	 *            模块类型参数(是来自bcs还是bcus......)
	 * @return 短溢表计算结果
	 */
	List<BalanceInfo> findBalanceInfo(Request request, Date beginDate,
			Date endDate, ProjectTypeParam projectTypeParam, String taskId,
			boolean isFromCheckData);

	/**
	 * 查找并计算短溢表(按报关名称分时间段进行查询)
	 * 
	 * @param request发送请求
	 * @param beginDate开始日期
	 * @param endDate
	 *            结束日期
	 * @param projectTypeParam
	 *            手册类型
	 * @param isFromCheckData
	 *            库存数据是否来源于盘点导入
	 * @param isAll
	 *            是否体现合同中所有料件的短溢情况
	 * @return 短溢表计算结果BalanceInfo
	 * @author wss2010.10.27 测试版本
	 */
	List<BalanceInfo> findBalanceInfoTest(Request request, Date beginDate,
			Date endDate, ProjectTypeParam projectTypeParam, String taskId,
			boolean isFromCheckData, boolean isAll, boolean useNewContract);

	/**
	 * 查询短溢表信息
	 * 
	 * @param request
	 *            发送请求
	 * @return 工厂短溢表
	 */
	List<BalanceInfo> findBalanceInfo(Request request);

	/**
	 * 是否存在单据号
	 */
	boolean isExistBillByBillNo(Request request, BillMaster billMaster);

	/**
	 * 获得海关帐的计算中间信息
	 * 
	 * @param imgOrgUseInfo
	 * @return
	 */
	List<TempImgOrgUseInfo> findTempImgOrgUseInfo(Request request,
			Date beginDate, Date endDate, ImgOrgUseInfoBase imgOrgUseInfo,
			String taskId);

	/**
	 * 查找出对应关系中一对多的料号
	 * 
	 * @param materielType
	 * @return
	 */
	List findOneToMorePtNo(Request request, String materielType);

	/**
	 * 获取已对应的单据
	 * 
	 * @param impExpType
	 * @param scmCoc
	 * @param beginData
	 * @param endData
	 * @param name
	 * @param spec
	 * @param isNameSpec 判断是选择名称(true)还是名称+规格(false)查询
	 * @return
	 */
	List findMakeBillCorrespondingInfo(Request request, Integer impExpType,
			ScmCoc scmCoc, Date beginData, Date endData, String nameSpec,Boolean isNameSpec);

	/**
	 * 查询已对应的单据与报关单信息
	 * 
	 * @param temp
	 * @return
	 */
	List findMakeBillCorrespondingInfo(Request request,
			TempBillCorrespondingSearch temp);

	/**
	 * 处理单据导入信息
	 * 
	 * @param request
	 * @param headList
	 * @param detailList
	 * @param hs
	 * @throws SQLException
	 */

	public void executeBillImport(Request request, List headList,
			List detailList, Hashtable hs) throws SQLException;

	public void executeTransBillImport(Request request, List headList,
			List detailList) throws SQLException;

	/**
	 * 检查转厂单据中进出货单据导入的手册号与备案号
	 * 
	 * @param customsEnvelopBillNo
	 * @param emsNo
	 * @param seqNum
	 * @return
	 */
	public List checkCustomsEnvelopCommodityInfoIsExistsEmsNoOrSeqNum(
			String customsEnvelopBillNo, String emsNo, Integer seqNum);

	/**
	 * 保存转厂单据进出货单据导入
	 * 
	 * @param request
	 * @param headList
	 * @param detailList
	 * @param hs
	 * @throws SQLException
	 */
	public void executeTransBillImport(Request request, List headList,
			List detailList, Hashtable hs) throws SQLException;

	List findBillDetailByMaterielType(Request request, String proNo,
			String materielType, Date begin, Date end, ScmCoc scmcoc);

	/**
	 * 从单据类型表里获取物料代码和物料的工厂名称
	 * 
	 * @return 用物料代码做key对应物料工厂名称的hashtable
	 */
	public Hashtable findCodeAndFactryNameFromBillType(Request request);

	/**
	 * 单据类型名称 与 单据类型代码 关联
	 */
	public Hashtable findCodeAndFactryNameByName(Request request,
			String typename);

	/**
	 * 检查单据导入期初单时,报关资料是否在对应关系存在
	 * 
	 * @param materielType
	 * @return
	 */
	Map<String, Object[]> findHsKeyForBill(Request requeset, String materielType);

	// 根据制单号获取相应的工厂数量
	public Double getPtAmountByproductNo(Request request,
			BillDetail billDetail, List billTypes, String productNo);

	/**
	 * 返回一个用物料料号作key对应从数据库抓出的物料信息的hashMap
	 * 
	 * @param typename
	 * @return hashMap
	 */
	public Map<String, Object[]> findptNoandCodeInStatCusNameRelation(
			Request request, String typename);

	/**
	 * 返回一个用物料料号作key对应从数据库抓出的物料信息的hashMap (针对半成品，从报关常用工厂物料中抓取资料)
	 * 
	 * @param typename
	 * @return hashMap
	 * @author wss2010.09.28
	 */
	public Map<String, Object[]> fintPtNoAndCodeInMateriel(Request request,
			String typename);

	/**
	 * 查找唯一的 FactoryAndFactualCustomsRalation 名称规格单位
	 * 
	 * @param typeName
	 * @param isEmsNo
	 *            是否要加上 手册号
	 * @return key = value = 料号+ "/" + 名称+"/"+规格+"/"+单位
	 */
	public Map<String, String> findDistinctFFCByPtNoNameSpecUnit(
			Request request, String typeName, Boolean isEmsNo);

	/**
	 * 查找唯一的 Mateiel 名称规格单位
	 * 
	 * @param typeName
	 * @return key = value = 料号+ "/" + 报关名称+"/" + 报关规格+"/" + 报关单位
	 * @author wss2010.09.28
	 */
	public Map<String, String> findDistinctMaterielByPtNoNameSpecUnit(
			Request request, String typeName);

	// 由billType和billNo查找出表头
	public BillMaster findBillMasterByBillTypeAndBillNo(Request request,
			BillType billType, String billNo);

	boolean makeAllContractImgExgToFactualCustoms(Request request,
			String materielType, int projectType, boolean isNeedSeqNum);

	/**
	 * 处理归并资料设备的导入
	 * 
	 * @param request
	 * @param projectType
	 * @return
	 */
	public boolean makeAllInnerFixtureToFactualCustoms(Request request,
			int projectType);

	/**
	 * 查找对应关系
	 * 
	 * @param request
	 * @return
	 */
	List findFactoryAndFactualCustomsRalationForBill(Request request,
			String materielType, int index, int length, String property,
			Object value, boolean isLike);

	List<Double> findFactoryAndFactualCustomsRalationMaterielForNetWeight(
			Request request, String materielType, String MaterielPtNo);

	/**
	 * 保存发票单据导入数据
	 * 
	 * @param headList
	 * @param detailList
	 * @param taskId
	 * @throws SQLException
	 * @throws SQLException
	 */
	void importDataToCasInvoice(Request request, List headList,
			List detailList, String taskId) throws SQLException;

	boolean makeAllFactoryAndFactualCustomsRalation(Request request,
			String materielType, int projectType);

	List findFactoryAndFactualCustomsRalation(Request request,
			String materielType);

	List findFactualCustoms(Request request, String materielType);

	void deleteFactoryAndFactualCustomsRalation(Request request,
			FactoryAndFactualCustomsRalation ffc);

	void deleteFactoryAndFactualCustomsRalation(Request request, List ffcs);

	void saveFactoryAndFactualCustomsRalation(Request request,
			FactoryAndFactualCustomsRalation ffc, String mType,
			Boolean isUpdateMateriel, Boolean isUpdateHsNum, String oldEmsNo);

	/**
	 * 单据中心右键 修改企业物料
	 * 
	 * @param ffc
	 *            修改后的对应关系
	 * @param materielType
	 *            对应关系 物料类型
	 * @param updateType
	 *            是否更新单据
	 */
	public void modifyMaterielForFactoryAndFactualCustomsRalation(
			Request request, FactoryAndFactualCustomsRalation ffc,
			String materielType, String updateType);
	
	/**
	 * 单据中心右键 修改企业物料（品基）
	 * 
	 * @param ffc
	 *            修改后的对应关系
	 * @param materielType
	 *            对应关系 物料类型
	 */
	public int modifyMaterielForFactoryAndFactualCustomsRalationPJ(
			Request request,
			String materielType);

	/**
	 * 单据中心 更新报关资料
	 * 
	 * @param ffc
	 *            新的对应关系
	 * @param materielType
	 *            对应关系物料类型
	 * @param isUpdateDetail
	 *            是否更新单据
	 * @param isNoChangeHsCode
	 *            没有变更报关编码
	 * @param ooMParameter
	 *            最旧的物料资料
	 * @param ooCusParameter
	 *            最旧的报关资料
	 * @param oCusParameter
	 *            变更的报关编码原先的属性
	 * 
	 * @author wss:2010.06.25
	 */
	public void modifyCustomForFactoryAndFactualCustomsRalation(
			Request request, FactoryAndFactualCustomsRalation ffc,
			String materielType, Boolean isUpdateDetail,
			Boolean isChangeHsCode, String[] oldMOldParameter,
			String[] oldCusOldParameter, String[] newCusOldParameter);

	void saveFactoryAndFactualCustomsRalation(Request request,
			Boolean isWriteBackM, FactoryAndFactualCustomsRalation ffc);

	// public List findImpExpInfosGetExpDj(Request request, String materialType,
	// List<Condition> conditions, List<Condition> conditions1,
	// Boolean isSplitBomVersion);

	public List findCasInvoice(Request request);

	public List findCasInvoiceInfo(Request request, CasInvoice head,
			Boolean canceled);

	List addFactoryAndFactualCustomsRalation(List<BcsInnerMerge> list);

	public List findChinBuyMaterielReport(Request request, String emsNo,
			ScmCoc scmCoc, String hsName);

	public List findCasInvoiceInfo(Request request, String emsNo,
			ScmCoc scmcoc, String hsName, Date beginDate, Date endDate,
			boolean isCancel);

	public List findCasInvoiceInfo(Request request, String emsNo,
			ScmCoc scmcoc, String hsName);

	public List findCasInvoiceInfoCancel(Request request, String emsNo,
			ScmCoc scmcoc, String hsName);

	public List invoiceCancel(Request request, List ls, BaseEmsHead head);

	public List invoiceRCancel(Request request, List ls, BaseEmsHead head);

	public List findAllEmsHeadH2k(Request request);

	public List findDistinctContract(Request request);

	public List findDistinctEmsNo(Request request);

	/**
	 * 根据条件查找发票与单据的对应关系
	 * 
	 * @param contractNo
	 * @param emsNo
	 * @param scmcoc
	 * @param hsName
	 * @return
	 */
	public List findInvoiceAndBillCorresponding(Request request, String emsNo,
			ScmCoc scmcoc, String hsName);

	/**
	 * 发货与发票开票关系列表
	 * 
	 * @param emsNo
	 * @param scmcoc
	 * @param hsName
	 * @return
	 */
	public List findSumBillAndInvoice(Request request, String emsNo,
			ScmCoc scmcoc, String hsName);

	/**
	 * 检查导入文件数据的正确性
	 * 
	 * @param request
	 * @param list
	 * @param ht
	 * @return
	 */
	public List checkImportData(Request request, List list, Hashtable ht);

	/**
	 * 检查导入文件数据的正确性（盘点平衡表）
	 * 
	 * @param request
	 * @param list
	 * @param ht
	 * @return
	 */
	public List checkFileDataForCheckBalance(Request request, List list,
			Hashtable ht);

	/**
	 * 查找盘点平衡表中所有数据
	 * 
	 * @param request
	 * @return
	 */
	public List findCheckBalance(Request request);

	/**
	 * 批量保存盘点平衡表导入数据
	 * 
	 * @param request
	 * @param list
	 */
	public List batchSaveCheckBalance(Request request, List<CheckBalance> list);

	/**
	 * 保存盘点平衡表
	 * 
	 * @param checkBalance
	 *            单条盘点平衡表数据
	 */
	public void saveCheckBalance(Request request, CheckBalance checkBalance);

	/**
	 * 删除盘点平衡表一条记录
	 * 
	 * @param List
	 *            <CheckBalance>
	 * @author wss
	 */
	public void deleteCheckBalance(Request request, List<CheckBalance> ls);

	/**
	 * 删除相应条件下的条件下的盘点平衡表
	 * 
	 * @param List
	 *            <CheckBalance>
	 * @author wss
	 */
	public void deleteAllCheckBalance(Request request, Date beginDate,
			Date endDate, String kcType, String ptNo, String wlType,
			List<String> lsWareSetCodes);

	/**
	 * 删除相应条件下的条件下的盘点平衡表
	 * 
	 * @param List
	 *            <CheckBalanceConvertMateriel>
	 * @author wss
	 */
	public void deleteAllCheckBalanceConvertMateriel(Request request,
			Date beginDate, Date endDate, String kcType, String ptNo,
			String wlType, List<String> lsWareSetCodes);

	/**
	 * 删除折算后的盘点
	 * 
	 * @param List
	 *            <Conditions>
	 * @author wss
	 */
	public void deleteCheckBalanceConvertMateriel(Request request,
			List<CheckBalanceConvertMateriel> ls);

	/**
	 * 删除盘点平衡表一条记录
	 * 
	 * @param checkBalance
	 */
	public void deleteCheckBalanceOneByOne(Request request,
			CheckBalance checkBalance);

	/**
	 * 删除盘点平衡表一条折料记录
	 * 
	 * @param CheckBalanceConvertMateriel
	 * @author wss
	 */
	public void deleteCheckBalanceConvertMateriel(Request request,
			CheckBalanceConvertMateriel ccm);

	/**
	 * 分页查询盘点平衡表
	 * 
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return
	 */
	public List<CheckBalance> findCheckBalance(Request request, int index,
			int length, String property, Object value, boolean isLike);

	// /**
	// * 分页查询盘点平衡表折BOM
	// *
	// * @param index
	// * 数据的开始下标
	// * @param length
	// * 数据长度
	// * @param property
	// * 属性
	// * @param value
	// * 属性的值
	// * @param isLike
	// * 查找时用“Like”还是“＝”
	// * @return
	// * @author wss2010.07.19
	// */
	// public List<CheckBalance> findCheckBalanceBom(Request request, int index,
	// int length, String property, Object value, boolean isLike);

	/**
	 * 自动获取对应的报关资料并计算，然后再保存在盘点平衡表中
	 * 
	 * @return
	 */
	public List calculateCheckBalance(Request request, List list, String taskId);

	/**
	 * 自动获取对应的报关资料并计算，然后再保存在盘点平衡表中
	 * 
	 * @return
	 * @author wss
	 */
	public void fillCheckBalanceHsInfoOneByOne(Request request, CheckBalance cb);

	/**
	 * 自动获取对应的报关资料并计算，然后再保存在盘点平衡表中 CheckBalanceBom
	 * 
	 * @return
	 * @author wss
	 */
	public List calculateCheckBalanceConvertMateriel(Request request, List list);

	/**
	 * 自动获取对应的报关资料并计算，然后再保存在盘点平衡表中 One by One CheckBalanceBom
	 * 
	 * @return
	 * @author wss
	 */
	public CheckBalanceConvertMateriel calculateCheckBalanceConvertMaterielOneByOne(
			Request request, CheckBalanceConvertMateriel ccm);

	/**
	 * 查找短溢表中存在的名称/规格/单位,用于数据导入时检查,避免重复导入
	 * 
	 * @return
	 */
	public List findBalanceInfoNameSpecUnitName(Request request);

	public List findImgFromBaseEmsHead(Request request, BaseEmsHead data,
			CasInvoice head);

	List findImgFromBaseEmsHead(Request request, BaseEmsHead data);

	Object savaOrUpdateObject(Request request, Object obj);

	/**
	 * 发票管理中开票与核销的对照关系
	 * 
	 * @param request
	 * @param emsNo
	 * @param scmcoc
	 * @param hsName
	 * @return
	 */
	public List SumCasInvoiceAndCancel(Request request, String emsNo,
			ScmCoc scmcoc, String hsName);

	public List findCasInvoiceByNo(String no);

	public List findBillDetailMaterielByNameAndScmCoc(Request request,
			String id, String name, ScmCoc scmCoc);

	public List<BalanceInfo2> findBalanceInfo2(Request request, Date beginDate,
			Date endDate, ProjectTypeParam projectTypeParam, String taskId,
			boolean isUpdateData, boolean isFromCheckData);

	public List<BalanceInfo2> findBalanceInfo2(Request request);

	/**
	 * BalanceInfo
	 * 
	 * @param obj
	 */
	public BalanceInfo2 saveBalanceInfo2(Request request, BalanceInfo2 obj);

	public List makeCasInvoiceAndBillRalation(Request request,
			CasInvoiceInfo invoice, List<BillDetail> details);

	public List findInvoiceAndBillCorrespondingByCasInvoiceInfo(
			Request request, CasInvoiceInfo info);

	public List findInvoiceAndBillCorrespondingCusName(Request request,
			ScmCoc p1);

	public List findInvoiceInfoCusName(Request request, ScmCoc p1);

	public void deleteOject(Request request, Object obj);

	public boolean deleteCasInvoice(Request request, CasInvoice obj);

	/**
	 * 获取保税设备清单
	 * 
	 * @param request
	 * @param stateCode
	 *            状态,空为无限制,001为未解除监管,002为解除监管
	 * @param beginDate
	 *            开始申报日期
	 * @param endDate
	 *            结束申报日期
	 * @return TempFixtureNotInTaxationReport
	 */
	public List getFixtureNotInTaxationReport(Request request,
			String stateCode, Date beginDate, Date endDate);

	/**
	 * 获取非保税设备清单
	 * 
	 * @param request
	 * @param beginDate
	 *            开始申报日期
	 * @param endDate
	 *            结束申报日期
	 * @return TempFixtureNotInTaxationReport
	 */
	public List getFixtureInTaxationReport(Request request, Date beginDate,
			Date endDate);

	/**
	 * 查询转厂收／送货商品名称
	 * 
	 * @return
	 */
	List findTransFactRecvSendCommName(Request request, boolean isImport,
			ScmCoc scmCoc, Date beginDate, Date endDate);

	/**
	 * 查询转厂收／送货商品名称
	 * 
	 * @return
	 */
	List findTransFactRecvSendCommName(Request request, boolean isImport,
			ScmCoc scmCoc, String beginMonth, String endMonth);

	/**
	 * 查询转厂收／送货明细表
	 * 
	 * @return
	 */
	List findTransFactRecvSendDetailInfo(Request request, boolean isImport,boolean isCustomNo,
			ScmCoc scmCoc, Date beginDate, Date endDate);

	/**
	 * 转厂差额明细表
	 * 
	 * @param isImg
	 * @param commName
	 * @param scmCoc
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List findTransFactCompareInfoOnDay(Request request, boolean isImg,
			String commName, ScmCoc scmCoc, Date beginDate, Date endDate);

	/**
	 * 结转对帐表
	 * 
	 * @param isImg
	 * @param commName
	 * @param commSpec
	 * @param scmCoc
	 * @param beginMonth
	 * @param endMonth
	 * @param groupCondition 分组条件
	 * @return
	 */
	List findTransFactCompareInfoOnMonth(Request request, boolean isImg,
			String commName, String commSpec,ScmCoc scmCoc, String beginMonth, String endMonth,List groupCondition);
	
	
	/**
	 * 结转对帐表
	 * 
	 * @param isMaterial
	 * @param commName
	 * @param scmCoc
	 * @param beginMonth
	 * @param endMonth
	 * @return
	 */
	public List findTransFactCompare(Request request, boolean isMaterial, String commName,
			String commSpec, ScmCoc scmCoc, Date beginMonth, Date endMonth,
			List<String> groupCondition);

	/**
	 * 转厂差额总表
	 * 
	 * @param endDate
	 * @return
	 */
	List findAllTransFactDiffInfo(Request request, boolean isImg,
			boolean isFindByScmcoc, ScmCoc scmCoc, Date endDate);

	/**
	 * 建立设备的对应关系
	 * 
	 * @param projectType
	 * @return
	 */
	public boolean makeAllFixFactoryAndFactualCustomsRalation(Request request,
			int projectType);

	/**
	 * 根据名称查找工厂单位资料
	 * 
	 * @param unitName
	 *            工厂单位名称
	 * @return
	 */
	public Unit findUnitByName(Request request, String unitName);
	/**
	 * 查询客户列表
	 * @param request
	 * @return
	 */
	public List findScmcoc(Request request);
	/**
	 * 查询供应商列表
	 */
	public List findManufacturer(Request request);
	/**
	 * 查找国内购买发票号码
	 * 
	 * @param request
	 * @return
	 */
	List findInvoiceNo(Request request);

	/**
	 * 检查各个模块的特殊报关单头中的合同协议栏位是否存在给定的协议号
	 * 
	 * @param request
	 * @param emsNo
	 *            要判断的协议好
	 * @param projectType
	 *            模块类型
	 * @return
	 */
	public Boolean isExitSpecialCustomsContractNo(Request request,
			String emsNo, Integer projectType);

	/**
	 * 根据给定的合同协议，查在各个模块的特殊报关单头中资料
	 * 
	 * @param emsNo
	 *            合同协议
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return
	 */
	public List getSpecialCustomsContractNo(Request request, String emsNo,
			int index, int length, String property, Object value, boolean isLike);

	/**
	 * 检查导入的数据的正确性，并输出错误提示
	 * 
	 * @param list
	 * @param ht
	 * @param taskId
	 * @param materielType
	 * @param isCover
	 * @return
	 */
	public List checkImportDataForRelation(Request request, List list,
			int[] ht, String taskId, String materielType,
			Map<String, Boolean> map);
	
	/**
	 * 检查导入的数据的正确性，并输出错误提示（品基）
	 * 
	 * @param list
	 * @param ht
	 * @param taskId
	 * @param materielType
	 * @param map
	 * @return
	 */
	public List checkImportDataForRelationPJ(Request request, List list,
			int[] ht, String taskId, String materielType,
			Map<String, Boolean> map);

	/**
	 * 批量保存对应关系表
	 * 
	 * @param list
	 *            从文件导入的对应关系数据
	 */
	public List<FactoryAndFactualCustomsRalation> batchSaveFactoryAndFactualCustomsRalation(
			Request request, List<FactoryAndFactualCustomsRalation> list,
			boolean isReWriteMateriel, String materielType, boolean isConver,Map map);

	public void deleteInvoiceAndBillCorresponding(Request request, List list);

	/**
	 * 获得在单据中客户供应商 转厂单据
	 * 
	 * @param sBillType
	 *            单据类型
	 * @param billCode
	 *            单据类型下的单据代号
	 */
	List findScmCocToFpt(Request request, String billCode);

	/**
	 * 获得在单据中商品信息不是全部转为真的单据记录 Tfi == 结转进口
	 * 
	 * @param request
	 *            请求控制
	 * @param scmCocId
	 *            客户id
	 * @return 单据类型为料件入库的单据中商品信息不是全部转为真的单据记录
	 */
	public List findTempBillMasterIsAvailabilityToFpt(Request request,
			String scmCocId, String appNo, String billCode, Date beginDate,
			Date endDate);

	/**
	 * 获得对应类型的单据表头
	 * 
	 * @param tablename
	 * @return
	 */
	public List getCasHeadForImport(Request request, String tablename);

	/**
	 * 按照单据导入的设定，返回一个客户HASHMAP
	 * 
	 * @param isName
	 * @return
	 */
	public HashMap getScmCocForImport(Request request, String isName);

	// /**
	// * 获得在单据中商品信息不是全部转为真的单据记录 Tfe == 结转出口
	// *
	// * @param request
	// * 请求控制
	// * @param scmCocId
	// * 客户id
	// * @return 单据类型为成品出入库的单据中商品信息不是全部转为真的单据记录
	// */
	// public List findTempBillMasterIsAvailabilityToTFBByTfe(Request request,
	// String scmCocId, String envelopNo);

	public List findBaseEmsImg(Request request, BaseEmsHead head);

	public List invoiceCancel(Request request, List ls, Integer seqNum,
			BaseEmsHead head);

	public CasInnerMergeDataOrder findCasInnerMergeDataOrder(Request request);

	public CasInnerMergeDataOrder saveCasInnerMergeDataOrder(Request request,
			CasInnerMergeDataOrder order);

	public void cpZsuanLj(Request request);

	/**
	 * 查找导入文件列顺序
	 * 
	 * @param tableName
	 * @return
	 */
	public List<ImportDataOrder> findImportDataOrder(Request request,
			String tableName);

	/**
	 * 检查导入的数据的正确性，并输出错误提示,用于实际报关资料导入
	 * 
	 * @param list
	 * @param ht
	 * @param taskId
	 * @param materielType
	 * @param isCover
	 * @return
	 */
	public List checkImportDataForHsn(Request request, List list, Hashtable ht,
			String taskId, String materielType);

	/**
	 * 保存导入文件列顺序
	 * 
	 * @param list
	 */
	public void batchUpdateDataOrder(Request request, List<ImportDataOrder> list);

	/**
	 * 批量保存实际报关资料
	 * 
	 * @param list
	 */
	public void batchUpdateStatCusNameRelationHsn(Request request,
			List<StatCusNameRelationHsn> list);

	/**
	 * 分页查询实际报关商品
	 * 
	 * @param ptNo
	 *            物料号
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return
	 */
	public List<StatCusNameRelationHsn> findStatCusNameRelationHsn(
			Request request, int index, int length, String property,
			Object value, boolean isLike, String materielType);

	/**
	 * 分页查询对应关系
	 * 
	 * @param ptNo
	 *            物料号
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return
	 */
	public List<FactoryAndFactualCustomsRalation> findFactoryAndFactualCustomsRalation(
			Request request, int index, int length, String property,
			Object value, boolean isLike, String materielType);

	/**
	 * 删除实际报关资料
	 * 
	 * @param list
	 */
	public void deleteStatCusNameRelationHsns(Request request,
			List<StatCusNameRelationHsn> list);

	/**
	 * 查找车间材料库存统计表
	 * 
	 * @param request
	 * @param isByPtNO
	 * @param taskId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List findF25OfBalanceInfo(Request request, Boolean isByPtNO,
			String taskId, Date beginDate, Date endDate);

	/**
	 * 改变记账年度时生成成品期初单
	 * 
	 * @param taskId
	 */
	public Map<String, BillInit> getProductF1ForBill(Request request,
			String taskId, Date beginDate, Date endDate);

	/**
	 * 生成单据头与体
	 * 
	 * @param map
	 * @param mMap
	 * @param fMap
	 * @param typeCode
	 */
	public void updateBillByMap(Request request, HashMap map, String typeCode);

	/**
	 * 改变记账年度是生成料件期初单(按客户)
	 * 
	 * @param taskId
	 * @return
	 */
	public Map<String, Map<String, Map<String, Double>>> getMaterielF24F25ForBillByCustom(
			Request request, String taskId, Date beginDate, Date endDate);

	/**
	 * 改变记账年度时生成成品期初单(按客户)
	 * 
	 * @param taskId
	 */
	public Map<String, Map<String, Double>> getProductF1ForBillByCustom(
			Request request, String taskId, Date beginDate, Date endDate);

	/**
	 * 生成单据头与体(按客户)
	 * 
	 * @param map
	 * @param mMap
	 * @param fMap
	 * @param typeCode
	 */
	public void updateBillByMapForCustom(Request request,
			HashMap<String, Map<String, Double>> map, String typeCode);

	/**
	 * 根据表头Id返回单据表头
	 * 
	 * @param id
	 * @param billType
	 * @return
	 */
	public BillMaster findBillMaster(Request request, String id, int billType);

	public Map getMaterialByType(Request request, String typename);

	/**
	 * 转抄单据
	 * 
	 * @param list
	 * @return
	 */
	public List<BillMaster> copyBillMaster(Request request,
			List<BillMaster> list);

	/**
	 * 
	 * @param billMaster
	 * @param propertyName
	 *            in BillDetail 制单号 String "productNo" , 仓库 WareSet "wareSet"
	 * @param propertyValue
	 *            into BillDetail value
	 * @return
	 */
	public List<BillDetail> saveAllBillDetailByMaster(Request request,
			BillMaster billMaster, String propertyName, Object propertyValue);

	/**
	 * 获得在单据中商品信息不是全部转为真的单据记录 Tfe == 结转出口
	 * 
	 * @param request
	 *            发送请求
	 * @param scmCocId
	 *            客户id
	 * @return 单据类型为成品出入库的单据中商品信息不是全部转为真的单据记录
	 */
	List findTempBillMasterIsAvailabilityToTFBByTfe(Request request,
			String scmCocId, String envelopNo, Date begin, Date end);

	/**
	 * 获得在单据中商品信息不是全部转为真的单据记录 Tfi == 结转进口
	 * 
	 * @param request
	 *            发送请求
	 * @param scmCocId
	 *            客户id
	 * @return 单据类型为料件入库的单据中商品信息不是全部转为真的单据记录
	 */
	List findTempBillMasterIsAvailabilityToTFBByTfi(Request request,
			String scmCocId, String envelopNo, Date begin, Date end);

	/**
	 * 生成料件期初,在产品期初,成品期初,边角料期初,残次品期初
	 */
	public void saveBillInit(String taskId,Request request);

	/**
	 * 生成料件期初单据,在产品期初单据,成品期初单据,边角料期初单据,残次品期初单据
	 */
	public void makeBillInit(Request request);

	/**
	 * 查找送货转厂耗料情况
	 * 
	 * @param request
	 * @param balanceInfo
	 * @return
	 */
	List findDeliveryToPlantDzsc(Request request, BalanceInfo balanceInfo,
			Date beginDate, Date endDate, String flag);

	/**
	 * 查找送货转厂耗料情况(电子化手册)
	 * 
	 * @param request
	 * @param balanceInfo
	 * @return
	 */
	public List findDeliveryToPlantBcs(Request request,
			BalanceInfo balanceInfo, Date beginDate, Date endDate, String flag);

	/**
	 * 出料件进仓的工厂总数量
	 * 
	 * @param ptNumber
	 * @param wareSet
	 * @param vaillDate
	 * @return
	 */
	public Double findWarehousesMaterielIn(Request request, String ptNumber,
			WareSet wareSet, Date vaillDate, Boolean isValid);

	/**
	 * 查找送货转厂耗料情况(电子帐册)
	 * 
	 * @param request
	 * @param balanceInfo
	 * @return
	 */
	public List findDeliveryToPlantBcus(Request request,
			BalanceInfo balanceInfo, Date beginDate, Date endDate, String flag);

	/**
	 * 出料件出仓的工厂总数量
	 * 
	 * @param ptNumber
	 * @param wareSet
	 * @param vaillDate
	 * @return
	 */
	public Double findWarehousesMaterielOut(Request request, String ptNumber,
			WareSet wareSet, Date vaillDate, Boolean isValid);

	/**
	 * 成品进口的工厂总数量
	 * 
	 * @param ptNumber
	 * @param wareSet
	 * @param vaillDate
	 * @return
	 */
	public Double findWarehousesProduceIn(Request request, String ptNumber,
			WareSet wareSet, Date vaillDate, Boolean isValid);

	/**
	 * 成品出口的工厂总数量
	 * 
	 * @param ptNumber
	 * @param wareSet
	 * @param vaillDate
	 * @return
	 */
	public Double findWarehousesProduceOut(Request request, String ptNumber,
			WareSet wareSet, Date vaillDate, Boolean isValid);

	public List getResultWaiFa(Request request, List<Condition> conditions,
			List<Condition> condition, List<Condition> conditionss);

	public List getResultWaiFaBack(Request request, List<Condition> conditions,
			List<Condition> condition, List<Condition> conditionss,
			List<Condition> tiaojian, List<Condition> tiaojian2);

	public List getResultWaiFaTrusteeInOut(Request request,
			List<Condition> conditions, List<Condition> condition,
			List<Condition> conditionss);

	public List getResultWaiFaTrusteeInOutBack(Request request,
			List<Condition> conditions, List<Condition> condition,
			List<Condition> conditionss, List<Condition> tiaojian,
			List<Condition> tiaojian2);

	/**
	 * 外发加工当日结存表
	 */
	public List getCurrentBillDetailOut(Request request,
			List<Condition> conditions, String orderBy, String billDetail,
			Boolean ishsTaotal, Boolean isShowLess, Boolean isGroup);

	/**
	 * 在产品当日结存表
	 */
	public List getCurrentBillDetailCurrDay(Request request,
			List<Condition> conditions, String orderBy, String billDetail,
			Boolean ishsTaotal, Boolean isShowLess, Boolean isGroup);

	List<TempObject> findAllWrokOrder(Request request, int index, int length,
			String property, Object value, boolean isLike);

	/**
	 * wss:(使用原生sql) 查询 生产设备使用情况表（海关资料统计报表）
	 * 
	 * @return
	 */
	public List calBillFixingBySql(Request request, Date beginDate, Date endDate);

	/**
	 * 判断是否存在 在产品期初单（半成品折过来的）
	 * 
	 * @author wss
	 */
	public boolean isExistInitBillMasterMadeBySemiProduct(Request request);

	/**
	 * 判断当前半成品单据是否已经折算为在产品期初单
	 * 
	 * @author wss2010.10.08
	 */
	public boolean isAlreadyConvertToInit(Request request, String billNo);

	/**
	 * 折算在产品期初单 (将半成品)
	 * 
	 * @author wss 2010.06.02
	 */
	public void convertToInitFromSemiProduct(Request request);

	/**
	 * 折算在产品期初单 (将半成品期初单)
	 * 
	 * @return 当前单据头
	 * @author wss 2010.10.08
	 */
	public BillMaster convertToInitFromSemiProductInit(Request request,
			BillMaster billMaster);

	/**
	 * 重新加载Hibernate SessionFactory
	 */
	public void reloadSessionFactory(Request request);

	/**
	 * 返回实际报关的商品名称来自实际报关商品
	 * 
	 * @param request
	 *            发送请求
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 实际报关商品名称
	 * 
	 * @author wss
	 */
	List<TempObject> findHsFromFactoryAndFactualCustomsRalation(
			Request request, String materielType, int index, int length,
			String property, Object value, boolean isLike);

	/**
	 * 查询CheckBalance
	 * 
	 * @author wss
	 */
	List findCheckBalance(Request request, List<Condition> conditions);

	/**
	 * 分页查询CheckBalance
	 * 
	 * @return
	 * @author wss2010.10.14
	 */
	List findCheckBalance(int index, int length, Request request,
			Date beginDate, Date endDate, String kcType, String ptNo,
			String wlType, List<String> lsWareSetCodes);

	/**
	 * 查询CheckBalanceConvertMateriel
	 * 
	 * @author wss
	 */
	List findCheckBalanceConvertM(Request request, List<Condition> conditions);

	/**
	 * 分页查询CheckBalanceConvertMateriel
	 * 
	 * @return
	 * @author wss2010.10.14
	 */
	List findCheckBalanceConvertMateriel(int index, int length,
			Request request, Date beginDate, Date endDate, String kcType,
			String ptNo, String wlType, List<String> lsWareSetCodes);

	/**
	 * 查询CheckBalance数量
	 * 
	 * @author wss2010.10.14
	 */
	int findCheckBalanceCount(Request request, Date beginDate, Date endDate,
			String kcType, String ptNo, String wlType,
			List<String> lsWareSetCodes);

	/**
	 * 查询CheckBalanceConvertMateriel数量
	 * 
	 * @author wss2010.10.14
	 */
	int findCheckBalanceConvertMaterielCount(Request request, Date beginDate,
			Date endDate, String kcType, String ptNo, String wlType,
			List<String> lsWareSetCodes);

	/**
	 * 成品折算料件（CheckBalance)
	 * 
	 * @return
	 * @author wss
	 */
	void convertCheckBalance(Request request, List list, String taskId,
			List<Condition> conditions);

	/**
	 * 成品折算料件（CheckBalance)一个一个来
	 * 
	 * @return
	 * @author wss
	 */
	void convertCheckBalanceToBomOneByOne(Request request, CheckBalance c);

	/**
	 * 根据客户查找 结转申请单(关封)号
	 * 
	 * @author wss
	 */
	List findEnvelopNoByScmCoc(Request request, String billCode, ScmCoc scmCoc);

	/**
	 * 查找所有的对应关系
	 * 
	 * @param request
	 * @return
	 * @author wss2010.09.24
	 */
	List findAllFactoryAndFactualCustomsRalation(Request request);

	/**
	 * 将对应关系中的手册事情更新至相应单据中
	 * 
	 * @param request
	 * @param ffc
	 * @return
	 * @author wss2010.09.24
	 */
	void updateEmsNoToBillDetail(Request request, StatCusNameRelationHsn s,
			String type);

	/**
	 * 将对应关系中的手册事情更新至相应单据中
	 * 
	 * @author wss2010.10.21用来测试
	 */
	void updateBillDetailEmsNo(Request request, String materielType);

	/**
	 * 检查单据
	 * 
	 * @param request
	 * @param billCategory
	 *            进出仓类别
	 * @param billType
	 *            单据类型
	 * @author wss2010.09.24
	 */
	List<BillDetail> checkBillDetail(Request request, int billCategory,
			BillType billType);

	/**
	 * 检查单据所有的
	 * 
	 * @param request
	 * @author wss2010.09.25
	 */
	List<BillDetail> checkAllBillDetail(Request request);

	/**
	 * 分页折算CheckBalance报关数量（获取报关资料）
	 * 
	 * @return
	 * @author wss2010.10.15只是用来实验的
	 */
	// void fillCheckBalanceHsInfoPerPage(int index,int length,Request request,
	// Date beginDate,Date endDate,String kcType,String ptNo,String
	// wlType,List<String> lsWareSetCodes);
	void fillCheckBalanceHsInfoPerPage(Request request, List list);

	/**
	 * 分页折BOMCheckBalance
	 * 
	 * @return
	 * @author wss2010.10.20只是用来实验的
	 */
	void convertCheckBalanceToBomPerPage(int index, int length,
			Request request, Date beginDate, Date endDate, String kcType,
			String ptNo, String wlType, List<String> lsWareSetCodes);

	/**
	 * 获取相应条件下通过折BOM(而非直接复制）的 CheckBalanceConvertMateriel 数量
	 * 
	 * @param request
	 * @param beginDate
	 * @param endDate
	 * @param fatherKcType
	 * @param fatherPtNo
	 * @param fatherWlType
	 * @return
	 * @author wss2010.10.20
	 */
	int getBalanceCheckConvertMaterielCount(Request request, Date beginDate,
			Date endDate, String fatherKcType, String fatherPtNo,
			String fatherWlType, List<String> lsWareSetCodes);

	/**
	 * 删除 通过折BOM的(而非直接复制的）的CheckBalanceConvertMateriel数据
	 * 
	 * @param request
	 * @param beginDate
	 * @param endDate
	 * @param fatherKcType
	 * @param ptNo
	 * @param wlType
	 * @author wss2010.10.20
	 */
	void deleteCheckBalanceConvertM(Request request, Date beginDate,
			Date endDate, String fatherKcType, String ptNo, String wlType,
			List<String> lsWareSetCodes);

	/**
	 * 查找在对应关系中有用到的报关资料
	 * 
	 * @param materielType
	 * @return
	 * @author wss2010.10.20
	 */
	List findStatCusNameRelationHsnInUsed(Request request, String materielType);

	/**
	 * 查找所有转厂单据
	 */
	List findTransFactoryBill(Request request);

	/**
	 * 根据单据号检查单据号是否存在重复
	 * 
	 * @param FactoryBillNo
	 * @return
	 */
	public List checkMulpTransFactoryBillFactoryBillNo(Request request,
			String factoryBillNo);

	/**
	 * 获得关封单据所有数据
	 */
	List findCustomsEnvelopBill(Request request, boolean isImpExpGoods);

	/**
	 * 根据单据类型查找单据体
	 * 
	 * @param billType
	 *            单据类型
	 * @return
	 * @author wss
	 */
	List findBillDetailByBillType(Request request, String billType);

	/**
	 * 显示 国内购买 进出口商品信息(统计上期结存)
	 * 
	 * @param request发送请求
	 * @param conditions查询条件
	 * @param beginDate
	 *            开始日期
	 * @return 按照查询条件查出进出口商品信息
	 * @author wss2010.11.23改写
	 */
	List findImpExpInfosOfInnerBuy(Request request, List<Condition> conditions,
			Date beginDate);

	/**
	 * 显示 国内购买 进出口商品信息
	 * 
	 * @param request发送请求
	 * @param conditions查询条件
	 * @param beginDate
	 *            开始日期
	 * @return 按照查询条件查出进出口商品信息
	 * @author wss2010.11.23改写
	 */
	List findImpExpInfosOfInnerBuy(Request request, List<Condition> conditions);

	/**
	 * 保存编码级盘点导入
	 */
	void saveBalanceList(Request request, List ls);

	/**
	 * 查询编码级盘点导入
	 */
	List findCheckBalanceOfCustom(int index, int length, Request request,
			Date beginDate, Date endDate, String kcType, String name,
			String spec, String unitName);

	/**
	 * 查询编码级盘点导入数量
	 */
	int findCheckBalanceCountOfCustom(Request request, Date beginDate,
			Date endDate, String kcType, String name, String spec,
			String unitName);

	/**
	 * 删除编码级盘点导入
	 */
	void deleteCheckBalanceOfCustom(Request request, List ls);

	/**
	 * 删除编码级盘点导入
	 */
	void deleteAllCheckBalanceOfCustom(Request request, Date beginDate,
			Date endDate, String kcType, String name, String spec,
			String unitName);

	/**
	 * 获取三种报关模式的料件Map（名称+规格+单位）
	 * 
	 * @return
	 */
	HashMap getCheckMap();

	/**
	 * 查找盘点平衡表中存在的盘点时间/工厂料号,用于数据导入时检查,避免重复导入
	 * 
	 * @return
	 */
	public List findCheckBalanceTimeAndPtNo(Request request);
	
	/**
	 * 查找StatCusNameRelationHsn的名称和规格组成的key
	 * @param materielType 物料编码
	 * materielType 取值：
	 * 
	 * 成品
	 * FINISHED_PRODUCT="0";
	 * 
	 * 半成品
 	 * SEMI_FINISHED_PRODUCT="1";
 	 * 
	 * 材料--主料
	 * MATERIEL="2";
	 * 
	 * @return
	 */
	public List findStatCusNameRelationHsnNameAndSpec(Request request, String materielType) ;

}
