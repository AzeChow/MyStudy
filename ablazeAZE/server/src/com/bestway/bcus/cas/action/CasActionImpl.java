/**
 * Created on 2004-6-9
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 浏览工厂单据类型 1
 保存工厂单据类型 2
 删除工厂单据类型 3


 浏览工厂单据 4
 保存工厂单据 5
 删除工厂单据 6

 浏览工厂资料统计报表 7
 浏览海关资料统计报表 8
 浏览海关财务报表 9


 */
package com.bestway.bcus.cas.action;

import java.sql.SQLException;
import java.util.ArrayList;
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
import com.bestway.bcus.cas.dao.CasBillDao;
import com.bestway.bcus.cas.dao.CasDao;
import com.bestway.bcus.cas.dao.CasLeftoverMaterielDao;
import com.bestway.bcus.cas.dao.CasParameterSetDao;
import com.bestway.bcus.cas.dao.CasSpecificontrolDao;
import com.bestway.bcus.cas.entity.BalanceInfo;
import com.bestway.bcus.cas.entity.BalanceInfo2;
import com.bestway.bcus.cas.entity.BillFixingBase;
import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.cas.entity.BillType;
import com.bestway.bcus.cas.entity.CheckBalance;
import com.bestway.bcus.cas.entity.CheckBalanceConvertMateriel;
import com.bestway.bcus.cas.entity.CheckBalanceOfCustom;
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
import com.bestway.bcus.cas.entity.TempOfCheckBalanceOfCustom;
import com.bestway.bcus.cas.entity.TempStatCusNameRelation;
import com.bestway.bcus.cas.invoice.entity.CasInvoice;
import com.bestway.bcus.cas.invoice.entity.CasInvoiceInfo;
import com.bestway.bcus.cas.logic.BalanceInfoLogic;
import com.bestway.bcus.cas.logic.BalanceInfoLogic2;
import com.bestway.bcus.cas.logic.CasBillLogic;
import com.bestway.bcus.cas.logic.CasLeftoverMaterielLogic;
import com.bestway.bcus.cas.logic.CasLogic;
import com.bestway.bcus.cas.logic.CasParameterSetLogic;
import com.bestway.bcus.cas.logic.CasSpecificontrolLogic;
import com.bestway.bcus.cas.logic.ExgExportInfoLogic;
import com.bestway.bcus.cas.logic.FixingThingLogic;
import com.bestway.bcus.cas.logic.ImgOrgUseInfoLogic;
import com.bestway.bcus.cas.logic.ImgOrgUseMiddleInfoLogic;
import com.bestway.bcus.cas.logic.MakeCorrespondenceLogic;
import com.bestway.bcus.cas.logic.TransferInfoLogic;
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
import com.bestway.common.BaseActionImpl;
import com.bestway.common.Condition;
import com.bestway.common.ConsignQueryCondition;
import com.bestway.common.ProjectTypeParam;
import com.bestway.common.Request;
import com.bestway.common.dataimport.entity.ImportDataOrder;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.customs.common.entity.BaseEmsHead;

/**
 * @author bestway // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class CasActionImpl extends BaseActionImpl implements CasAction {
	/**
	 * 海关帐Dao
	 */
	private CasDao casDao = null;
	/**
	 * 海关帐Logic
	 */
	private CasLogic casLogic = null;
	/**
	 * 海关帐单据Logic
	 */
	private CasBillLogic casBillLogic = null;
	/**
	 * 海关帐单据Dao
	 */
	private CasBillDao casBillDao = null;
	/**
	 * 原材料来源与使用情况Logic
	 */
	private ImgOrgUseInfoLogic imgOrgUseInfoLogic = null;
	/**
	 * 成品使用情况Logic
	 */
	private ExgExportInfoLogic exgExportInfoLogic = null;
	/**
	 * 结转信息Logic
	 */
	private TransferInfoLogic transferInfoLogic = null;
	/**
	 * 海关帐参数设置Dao
	 */
	private CasParameterSetDao casParameterSetDao = null;
	/**
	 * 海关帐特殊控制Dao
	 */
	private CasSpecificontrolDao casSpecificontrolDao = null;
	/**
	 * 海关帐特殊控制Logic
	 */
	private CasSpecificontrolLogic casSpecificontrolLogic = null;
	/**
	 * 自动生成相应单据Logic
	 */
	private MakeCorrespondenceLogic makeCorrespondenceLogic = null;
	/**
	 * 海关帐参数设置Logic
	 */
	private CasParameterSetLogic casParameterSetLogic = null;
	/**
	 * 设备信息Logic
	 */
	private FixingThingLogic fixingThingLogic = null;
	/**
	 * 海关帐边角料Dao
	 */
	private CasLeftoverMaterielDao casLeftoverMaterielDao = null;
	/**
	 * 海关帐边角料Logic
	 */
	private CasLeftoverMaterielLogic casLeftoverMaterielLogic = null;
	/**
	 * 短溢表Logic
	 */
	private BalanceInfoLogic balanceInfoLogic = null;
	private BalanceInfoLogic2 balanceInfoLogic2 = null;
	private ImgOrgUseMiddleInfoLogic imgOrgUseMiddleInfoLogic = null;

	/**
	 * 取得短溢表Logic内容
	 * 
	 * @return 短溢表Logic
	 */
	public BalanceInfoLogic getBalanceInfoLogic() {
		return balanceInfoLogic;
	}

	public BalanceInfoLogic2 getBalanceInfoLogic2() {
		return balanceInfoLogic2;
	}

	public void setBalanceInfoLogic2(BalanceInfoLogic2 balanceInfoLogic2) {
		this.balanceInfoLogic2 = balanceInfoLogic2;
	}

	/**
	 * 设计短溢表Logic内容
	 * 
	 * @param balanceInfoLogic
	 *            短溢表Logic
	 */
	public void setBalanceInfoLogic(BalanceInfoLogic balanceInfoLogic) {
		this.balanceInfoLogic = balanceInfoLogic;
	}

	/**
	 * 取得海关帐边角料Dao内容
	 * 
	 * @return 海关帐边角料Dao
	 */
	public CasLeftoverMaterielDao getCasLeftoverMaterielDao() {
		return casLeftoverMaterielDao;
	}

	/**
	 * 取得海关帐边角料Logic的内容
	 * 
	 * @return 海关帐边角料Logic
	 */
	public CasLeftoverMaterielLogic getCasLeftoverMaterielLogic() {
		return casLeftoverMaterielLogic;
	}

	/**
	 * 设计海关帐边角料Logic内容
	 * 
	 * @param casLeftoverMaterielLogic
	 *            海关帐边角料Logic
	 */
	public void setCasLeftoverMaterielLogic(
			CasLeftoverMaterielLogic casLeftoverMaterielLogic) {
		this.casLeftoverMaterielLogic = casLeftoverMaterielLogic;
	}

	/**
	 * 设计海关帐边角料Dao内容
	 * 
	 * @param casLeftoverMaterielDao
	 *            海关帐边角料Dao
	 */
	public void setCasLeftoverMaterielDao(
			CasLeftoverMaterielDao casLeftoverMaterielDao) {
		this.casLeftoverMaterielDao = casLeftoverMaterielDao;
	}

	/**
	 * 取得设备信息Logic内容
	 * 
	 * @return 设备信息Logic
	 */
	public FixingThingLogic getFixingThingLogic() {
		return fixingThingLogic;
	}

	/**
	 * 设计设备信息Logic内容
	 * 
	 * @param fixingThingLogic
	 *            设备信息Logic
	 */
	public void setFixingThingLogic(FixingThingLogic fixingThingLogic) {
		this.fixingThingLogic = fixingThingLogic;
	}

	/**
	 * 取得海关帐特殊控制Dao内容
	 * 
	 * @return 海关帐特殊控制Dao
	 */
	public CasSpecificontrolDao getCasSpecificontrolDao() {
		return casSpecificontrolDao;
	}

	/**
	 * 取得海关帐参数设置Logic内容
	 * 
	 * @return 海关帐参数设置Logic
	 */
	public CasParameterSetLogic getCasParameterSetLogic() {
		return casParameterSetLogic;
	}

	/**
	 * 设计海关帐参数设置Logic内容
	 * 
	 * @param casParameterSetLogic
	 *            海关帐参数设置Logic
	 */
	public void setCasParameterSetLogic(
			CasParameterSetLogic casParameterSetLogic) {
		this.casParameterSetLogic = casParameterSetLogic;
	}

	/**
	 * 取得自动生成相应单据Logic内容
	 * 
	 * @return 自动生成相应单据Logic
	 */
	public MakeCorrespondenceLogic getMakeCorrespondenceLogic() {
		return makeCorrespondenceLogic;
	}

	/**
	 * 设计自动生成相应单据Logic内容
	 * 
	 * @param makeCorrespondenceLogic
	 *            自动生成相应单据Logic
	 */
	public void setMakeCorrespondenceLogic(
			MakeCorrespondenceLogic makeCorrespondenceLogic) {
		this.makeCorrespondenceLogic = makeCorrespondenceLogic;
	}

	/**
	 * 取得海关帐单据Logic内容
	 * 
	 * @return casBillLogic 海关帐单据Logic
	 */
	public CasBillLogic getCasBillLogic() {
		return casBillLogic;
	}

	/**
	 * 设计海关帐单据Logic内容
	 * 
	 * @param casBillLogic
	 *            海关帐单据Logic
	 */
	public void setCasBillLogic(CasBillLogic casBillLogic) {
		this.casBillLogic = casBillLogic;
	}

	/**
	 * 取得海关帐单据Dao内容
	 * 
	 * @return casBillDao 海关帐单据Dao
	 */
	public CasBillDao getCasBillDao() {
		return casBillDao;
	}

	/**
	 * 设计海关帐单据Dao内容
	 * 
	 * @param casBillDao
	 *            海关帐单据Dao
	 */
	public void setCasBillDao(CasBillDao casBillDao) {
		this.casBillDao = casBillDao;
	}

	public ImgOrgUseMiddleInfoLogic getImgOrgUseMiddleInfoLogic() {
		return imgOrgUseMiddleInfoLogic;
	}

	public void setImgOrgUseMiddleInfoLogic(
			ImgOrgUseMiddleInfoLogic imgOrgUseMiddleInfoLogic) {
		this.imgOrgUseMiddleInfoLogic = imgOrgUseMiddleInfoLogic;
	}

	/**
	 * 取得海关帐特殊控制Dao内容
	 * 
	 * @param casSpecificontrolDao
	 *            海关帐特殊控制Dao
	 */
	public void setCasSpecificontrolDao(
			CasSpecificontrolDao casSpecificontrolDao) {
		this.casSpecificontrolDao = casSpecificontrolDao;
	}

	/**
	 * 显示常用物料主档
	 * 
	 * @param billType
	 *            单据类型
	 * @return list 与指定单据类型相匹配表名的表头
	 */
	public String findMateriel(List list) {
		return casBillLogic.checkFileData(list);
	}

	/**
	 * 取得成品使用情况Logic内容
	 * 
	 * @return 成品使用情况Logic
	 */
	public ExgExportInfoLogic getExgExportInfoLogic() {
		return exgExportInfoLogic;
	}

	/**
	 * 设计成品使用情况Logic内容
	 * 
	 * @param exgExportInfoLogic
	 *            成品使用情况Logic
	 */
	public void setExgExportInfoLogic(ExgExportInfoLogic exgExportInfoLogic) {
		this.exgExportInfoLogic = exgExportInfoLogic;
	}

	/**
	 * 取得海关帐参数设置Dao的内容
	 * 
	 * @return casParameterSetDao 海关帐参数设置Dao
	 */
	public CasParameterSetDao getCasParameterSetDao() {
		return casParameterSetDao;
	}

	/**
	 * 设计海关帐参数设置Dao内容
	 * 
	 * @param casParameterSetDao
	 *            海关帐参数设置Dao
	 */
	public void setCasParameterSetDao(CasParameterSetDao casParameterSetDao) {
		this.casParameterSetDao = casParameterSetDao;
	}

	/**
	 * 显示单据类型
	 * 
	 * @param request
	 *            请求控制
	 * @param billType
	 *            单据类型
	 * @return 单据类型
	 */
	public List findBillType(Request request, int billType) {
		return casDao.findBillType(billType);
	}

	/**
	 * 显示单据类型
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @return List 与指定的物料类型匹配的单据类型
	 */
	public List findBillTypeByProduceType(Request request, String materielType) {
		return casDao.findBillTypeByMaterielType(materielType);
	}

	/**
	 * 按单据名称返回整个单据
	 * 
	 * @param request
	 *            请求控制
	 * @param mostlyBillTypeName
	 *            类似的单据类型名称
	 * @param billTypeName
	 *            单据类型名称
	 * @return 与指定的单据类型名称匹配的临时单据
	 */
	public List findBillTempDetailByBillTypeName(Request request,
			String mostlyBillTypeName, String billTypeName, Date beginDate,
			Date endDate) {
		return casBillDao.findBillTempMasterByBillTypeName(mostlyBillTypeName,
				billTypeName, beginDate, endDate);
	}

	/**
	 * 保存单据类型
	 * 
	 * @param request
	 *            请求控制
	 * @param billType
	 *            单据类型
	 * @return 单据类型
	 */
	public BillType saveBillType(Request request, BillType billType) {
		casDao.saveBillType(billType);
		return billType;
	}

	/**
	 * 删除单据类型
	 * 
	 * @param request
	 *            请求控制
	 * @param billType
	 *            单据类型
	 */
	public void deleteBillType(Request request, BillType billType) {
		casDao.deleteBillType(billType);
	}

	/**
	 * 检查是否重复
	 * 
	 * @param request
	 *            请求控制
	 * @param code
	 *            单据类型代码
	 * @return 根据单据类型代码得到的单据类型
	 */
	public BillType findBillTypeByCode(Request request, String code) {
		return casDao.findBillTypeByCode(code);
	}

	/**
	 * 检查工厂名称是否重复
	 * 
	 * @param request
	 *            发送请求
	 * @param code
	 *            单据类型工厂名称
	 * @return 根据单据类型工厂名称得到的单据类型
	 */
	public List findBillTypeByFactoryName(Request request, String factoryName) {
		return casDao.findBillTypeByFactoryName(factoryName);
	}

	/**
	 * 取得海关帐Dao内容
	 * 
	 * @return 海关帐Dao
	 */
	public CasDao getCasDao() {
		return casDao;
	}

	/**
	 * 设计海关帐Dao内容
	 * 
	 * @param casDao
	 *            海关帐Dao
	 */
	public void setCasDao(CasDao casDao) {
		this.casDao = casDao;
	}

	/**
	 * 取得海关帐Logic内容
	 * 
	 * @return 海关帐Logic
	 */
	public CasLogic getCasLogic() {
		return casLogic;
	}

	/**
	 * 设计海关帐Logic内容
	 * 
	 * @param casLogic
	 *            海关帐Logic
	 */
	public void setCasLogic(CasLogic casLogic) {
		this.casLogic = casLogic;
	}

	/**
	 * 显示单据资料主表
	 * 
	 * @param request
	 *            请求控制
	 * @param billType
	 *            单据类型
	 * @return 与指定的单据类型匹配的单句表头资料
	 */
	public List findBillMaster(Request request, BillType billType) {
		return casBillDao.findBillMaster(billType);
	}

	/**
	 * 保存单据资料主表
	 * 
	 * @param request
	 *            请求控制
	 * @param billMaster
	 *            单据表头
	 * @return 单据资料主表表头
	 */
	public BillMaster saveBillMaster(Request request, BillMaster billMaster) {
		casBillDao.saveBillMaster(billMaster);
		return billMaster;
	}

	/**
	 * 删除单据主表
	 * 
	 * @param request
	 *            请求控制
	 * @param billMaster
	 *            单据表头
	 */
	public void deleteBillMaster(Request request, BillMaster billMaster) {
		casBillLogic.deleteBillMaster(billMaster);
	}

	/**
	 * 保存单据资料明细表
	 * 
	 * @param request
	 *            请求控制
	 * @param billDetail
	 *            单据明细资料
	 * @return 单据明细资料
	 */
	public BillDetail saveBillDetail(Request request, BillDetail billDetail) {
		casDao.saveBillDetail(billDetail);
		return billDetail;
	}
	/**
	 * 判断单据是否被引用
	 * @param billId 单据ID
	 * @return
	 * @author sxk
	 */
	public List findOwpBillAndBillDetail(Request request,String billId){
		return casDao.findOwpBillAndBillDetail(billId);
	}
	/**
	 * 查询该单据表头下的单据表体
	 * @param billMaster 单据表头
	 * @return
	 * @author sxk
	 */
	public List findBillDetailByMasterId(Request request,BillMaster billMaster){
		return casDao.findBillDetailByMasterId(billMaster);
	}
	/**
	 * 保存单据资料明细表
	 * 
	 * @param request
	 *            请求控制
	 * @param billDetailList
	 *            单句明细资料
	 * @return 单据明细资料
	 */
	public List<BillDetail> saveBillDetail(Request request,
			List<BillDetail> billDetailList) {
		casDao.saveBillDetail(billDetailList);
		return billDetailList;
	}

	/**
	 * 删除单据明细表
	 * 
	 * @param request
	 *            请求控制
	 * @param billDetail
	 *            单据明细资料
	 */
	public void deleteBillDetail(Request request, BillDetail billDetail) {
		casDao.deleteBillDetail(billDetail);
	}

	/**
	 * 删除单据明细表
	 * 
	 * @param request
	 *            请求控制
	 * @param listBillDetail
	 *            所有的单据明细资料
	 */
	public void deleteBillDetail(Request request,
			List<BillDetail> listBillDetail) {
		casDao.deleteBillDetail(listBillDetail);
	}

	/**
	 * 得到单据号码
	 * 
	 * @return 与指定单据类型匹配的单据表中单据号
	 */
	public String getBillNo(int sBillType) {
		return casDao.getBillNo(sBillType);
	}

	/**
	 * 删除表体通过表头
	 * 
	 * @param request
	 *            请求控制
	 * @param billMaster
	 *            单据表头
	 */
	public void deleteBillDetailByMaster(Request request, BillMaster billMaster) {
		casBillDao.deleteBillDetailByMaster(billMaster);
	}

	/**
	 * 返回详细的报关商品
	 * 
	 * @param request
	 *            请求控制
	 * @param produceType
	 *            物料类型
	 * @return 与指定物料类型匹配的表中的报关商品名称
	 */
	public List findDistinctHsName(Request request, String produceType) {
		return casBillDao.findDistinctHsName(produceType);
	}

	/**
	 * 返回详细的工厂商品
	 * 
	 * @param request
	 *            请求控制
	 * @param produceType
	 *            物料类型
	 * @return 与指定物料类型匹配的表中的工厂商品名称
	 */
	public List findDistinctPtName(Request request, String produceType) {
		return casBillDao.findDistinctPtName(produceType);
	}

	/**
	 * 返回详细的工厂商品（记帐）
	 * 
	 * @param request
	 *            请求控制
	 * @param produceType
	 *            物料类型
	 * @return 与指定的物料类型匹配的表中已记帐的工厂商品名称
	 */
	public List findDistinctPtNameAndWrite(Request request, String produceType) {
		return casBillDao.findDistinctPtNameAndWrite(produceType);
	}

	/**
	 * 组织公共查询
	 * 
	 * @param request
	 *            请求控制
	 * @param className
	 *            表名
	 * @param conditions
	 *            查询条件("and"或"or""(""值"")"等等)
	 * @return 与指定的表名 条件匹配且selects groupby leftouter为空的查询
	 */
	public List commonSearch(Request request, String className, List conditions) {
		return casDao.commonSearch("", className, conditions, "", "");
	}
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
			List conditions, String groupBy, String leftOuter) {
		return casDao.commonSearch( selects, className, conditions, groupBy,
				leftOuter);
	}
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
	public List commonSearchPageList(Request request,int index, int length, String selects, String className,
			List conditions, String groupBy, String leftOuter) {
		return casDao.commonSearchPageList( index,  length,selects, className, conditions, groupBy,
				leftOuter);
	}
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
			List conditions, String groupBy, String leftOuter) {
		return casDao.countCommonSearchPage( selects, className, conditions, groupBy,
				leftOuter);
	}
	/**
	 * 返回单据类型
	 * 
	 * @param request
	 *            请求控制
	 * @param produceType
	 *            物料类型
	 * @return 根据物料类型返回单据类型
	 */
	public List findBillMaterielType(Request request, String produceType) {
		return casDao.findBillMaterielType(produceType);
	}

	/**
	 * 显示临时单据
	 * 
	 * @param request
	 *            请求控制
	 * @return 临时单据
	 */
	public List findBillTemp(Request request) {
		return casDao.findBillTemp();
	}

	/**
	 * 显示设备(海关)
	 * 
	 * @param request
	 *            请求控制
	 * @return 所有设备内容
	 */
	public List findBillFixingThing(Request request) {
		return casDao.findBillFixingThing();
	}

	/**
	 * 显示设备(工厂)
	 * 
	 * @param request
	 *            请求控制
	 * @return 所有设备内容
	 */
	public List findBillFixing(Request request) {
		return casDao.findBillFixing();
	}

	/**
	 * 删除临时单据
	 * 
	 * @param request
	 *            请求控制
	 */
	public void deleteBillTemp(Request request) {
		casDao.deleteBillTemp();
	}

	/**
	 * 删除设备(海关)
	 * 
	 * @param request
	 *            请求控制
	 */
	public void deleteBillFixingThing(Request request) {
		casDao.deleteBillFixingThing();
	}

	/**
	 * 删除设备(工厂)
	 * 
	 * @param request
	 *            请求控制
	 */
	public void deleteBillFixing(Request request) {
		casDao.deleteBillFixing();
	}

	/**
	 * 保存临时单据
	 * 
	 * @param request
	 *            请求控制
	 * @param billTemp
	 *            临时单据
	 */
	public void saveBillTemp(Request request, BillTemp billTemp) {
		casDao.saveBillTemp(billTemp);
	}

	/**
	 * 保存设备单据
	 * 
	 * @param request
	 *            请求控制
	 * @param bill
	 *            设备单据
	 * @return BillFixingBase型的设备单据
	 */
	public BillFixingBase saveBillFixingBase(Request request,
			BillFixingBase bill) {
		casDao.saveBillFixingBase(bill);
		return bill;
	}

	/**
	 * 删除设备单据
	 * 
	 * @param request
	 *            请求控制
	 * @param bill
	 *            设备单据
	 */
	public void deleteBillFixingBase(Request request, List<BillFixingBase> bill) {
		casDao.deleteBillFixingBase(bill);
	}

	/**
	 * 显示客户来自单据表头
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @return 与指定的物料类型匹配的单句表头中的客户
	 */
	public List findScmCocFromBillMaster(Request request, String materielType) {
		return casBillDao.findScmCocFromBillMaster(materielType);
	}

	/**
	 * 显示仓库来自单据表头
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @return 与指定的物料类型匹配的单据表头中的仓库
	 */
	public List findWareSetFromBillMaster(Request request, String materielType) {
		return casBillDao.findWareSetFromBillMaster(materielType);
	}

	/**
	 * 查找物料或商品
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @return 根据指定的物料类型查出相应的单据表头中的物料或商品
	 */
	public List findGoodsFromBillMaster(Request request, String materielType) {
		return casBillDao.findGoodsFromBillMaster(materielType);
	}

	/**
	 * 返回指定仓库的进出单据明细记录
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @param wareSet
	 *            仓库
	 * @return 与指定的仓库匹配且根据物料类型查出的单据表头的进出单据明细记录
	 */
	public List findGoodsFromBillMasterByWareSet(Request request,
			String materielType, WareSet wareSet) {
		return casBillDao.findGoodsFromBillMasterByWareSet(materielType,
				wareSet);
	}

	/**
	 * 显示进出口商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param materialType
	 *            物料类型
	 * @param conditions
	 *            查询条件
	 * @return 按照查询条件查出进出口商品信息
	 */
	public List findImpExpInfos(Request request, String materialType,
			List conditions, Boolean isSplitBomVersion,Boolean isWareSet) {
		return casLogic.findImpExpInfos(materialType, conditions,
				isSplitBomVersion,isWareSet);
	}

	/**
	 * 查询进出口商品信息
	 * 
	 * @param materialType
	 *            物料类型
	 * @param conditions
	 *            查询条件
	 * @return 按照查询条件查出委外的进出口商品信息
	 */
	public List findImpExpInfos(Request request, String materialType,
			List<Condition> conditions, Boolean isSplitBomVersion,
			Date beginDate,Boolean isWareSet) {
		return casLogic.findImpExpInfos(materialType, conditions,
				isSplitBomVersion, beginDate,isWareSet);
	}

	/**
	 * 显示结转商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param materialType
	 *            物料类型
	 * @param conditions
	 *            查询条件
	 * @return 按照查询条件查出结转商品信息
	 */
	public List findCarryForwardInfos(Request request, String materialType,
			List conditions) {
		return casLogic.findCarryForwardInfos(materialType, conditions);
	}

	/**
	 * 料件，成品，设备库存情况统计表。
	 * 
	 * @param request
	 *            请求控制
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
	public List findStoreInfos(Request request, String materialType,
			Date beginDate, Date endDate, String beginPtNo, String endPtNo) {
		return casLogic.findStoreInfos(materialType, beginDate, endDate,
				beginPtNo, endPtNo);
	}

	/**
	 * 返回盘点表头
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @return 返回所有没有作废的盘点表头
	 */
	public List findCheckMaster(Request request, String materielType) {
		return casDao.findCheckMaster(materielType);
	}

	/**
	 * 保存盘点表头
	 * 
	 * @param request
	 *            请求控制
	 * @param checkMaster
	 *            盘点表表头
	 * @return 盘点表头
	 */
	public CheckMaster saveCheckMasters(Request request, CheckMaster checkMaster) {
		casDao.saveCheckMaster(checkMaster);
		return checkMaster;
	}

	/**
	 * 取得所有序号
	 * 
	 * @param request
	 *            请求控制
	 * @param className
	 *            类名
	 * @return 返回序号 从1开始
	 */
	public String getNum(Request request, String className) {
		return casDao.getNum(className);
	}

	/**
	 * 显示盘点表体
	 * 
	 * @param request
	 *            请求控制
	 * @param checkMaster
	 *            盘点表头
	 * @return 返回盘点表体
	 */
	public List findCheckDetail(Request request, CheckMaster checkMaster) {
		return casDao.findCheckDetail(checkMaster);
	}

	/**
	 * 保存盘点表体
	 * 
	 * @param request
	 *            请求控制
	 * @param checkDetail
	 *            盘点表体
	 * @return 盘点表体
	 */
	public CheckDetail saveCheckDetails(Request request, CheckDetail checkDetail) {
		casDao.saveCheckDetail(checkDetail);
		return checkDetail;
	}

	/**
	 * 删除盘点表明细
	 * 
	 * @param request
	 *            请求控制
	 * @param checkMaster
	 *            盘点表表头
	 */
	public void deleteCheckDetail(Request request, CheckMaster checkMaster) {
		casDao.deleteCheckDetail(checkMaster);
	}

	/**
	 * 返回现金流量表头
	 * 
	 * @param request
	 *            请求控制
	 * @return 没有作废的现金流量表头
	 */
	public List findMoneyMaster(Request request) {
		return casDao.findMoneyMaster();
	}

	/**
	 * 返回资产负债表头
	 * 
	 * @param request
	 *            请求控制
	 * @return 没有作废的资产负债表头
	 */
	public List findDebtMaster(Request request) {
		return casDao.findDebtMaster();
	}

	/**
	 * 返回利润表头
	 * 
	 * @param request
	 *            请求控制
	 * @return 没有作废的利润表头
	 */
	public List findMarginMaster(Request request) {
		return casDao.findMarginMaster();
	}

	/**
	 * 保存现金流量表头
	 * 
	 * @param request
	 *            请求控制
	 * @param moneyMaster
	 *            现金流量表头
	 * @return 现金流量表表头
	 */
	public MoneyMaster saveMoneyMaster(Request request, MoneyMaster moneyMaster) {
		casDao.saveMoneyMaster(moneyMaster);
		return moneyMaster;
	}

	/**
	 * 保存利润表头
	 * 
	 * @param request
	 *            请求控制
	 * @param marginMaster
	 *            利润表表头
	 * @return 利润表表头
	 */
	public MarginMaster saveMarginMaster(Request request,
			MarginMaster marginMaster) {
		casDao.saveMarginMaster(marginMaster);
		return marginMaster;
	}

	/**
	 * 保存资产负债表头
	 * 
	 * @param request
	 *            请求控制
	 * @param debtMaster
	 *            资产负债表表头
	 * @return 资产负债表表头
	 */
	public DebtMaster saveDebtMaster(Request request, DebtMaster debtMaster) {
		casDao.saveDebtMaster(debtMaster);
		return debtMaster;
	}

	/**
	 * 返回现金流量表体
	 * 
	 * @param request
	 *            请求控制
	 * @param moneyMaster
	 *            现金流量表表头
	 * @return 与表头内容相匹配的现金流量表表体
	 */
	public List findMoneyDetail(Request request, MoneyMaster moneyMaster) {
		return casDao.findMoneyDetail(moneyMaster);
	}

	/**
	 * 返回资产负债表体
	 * 
	 * @param request
	 *            请求控制
	 * @param debtMaster
	 *            资产负债表表头
	 * @return 与资产负债表表头对应的表体
	 */
	public List findDebtDetail(Request request, DebtMaster debtMaster) {
		return casDao.findDebtDetail(debtMaster);
	}

	/**
	 * 返回利润表体
	 * 
	 * @param request
	 *            请求控制
	 * @param marginMaster
	 *            利润表表头
	 * @return 与利润表表头对应的表体
	 */
	public List findMarginDetail(Request request, MarginMaster marginMaster) {
		return casDao.findMarginDetail(marginMaster);
	}

	/**
	 * 保存现金流量表体
	 * 
	 * @param request
	 *            请求控制
	 * @param moneyDetail
	 *            现金流量表表体
	 */
	public MoneyDetail saveMoneyDetail(Request request, MoneyDetail moneyDetail) {
		casDao.saveMoneyDetail(moneyDetail);
		return moneyDetail;
	}

	/**
	 * 保存资产负债表体
	 * 
	 * @param request
	 *            请求控制
	 * @param debtDetail
	 *            资产负债表表体
	 */
	public DebtDetail saveDebtDetail(Request request, DebtDetail debtDetail) {
		casDao.saveDebtDetail(debtDetail);
		return debtDetail;
	}

	/**
	 * 保存利润表体
	 * 
	 * @param request
	 *            请求控制
	 * @param marginDetail
	 *            利润表表体
	 */
	public MarginDetail saveMarginDetails(Request request,
			MarginDetail marginDetail) {
		casDao.saveMarginDetail(marginDetail);
		return marginDetail;
	}

	/**
	 * 增加现金流量表体
	 * 
	 * @param request
	 *            请求控制
	 * @param moneyMaster
	 *            现金流量表头
	 */
	public void MoneyDetailadd(Request request, MoneyMaster moneyMaster) {
		casLogic.moneyDetailadd(moneyMaster);
	}

	/**
	 * 增加资产负债表
	 * 
	 * @param request
	 *            请求控制
	 * @param debtMaster
	 *            资产负债表头
	 */
	public void DebtDetailadd(Request request, DebtMaster debtMaster) {
		casLogic.DebtDetailadd(debtMaster);
	}

	/**
	 * 增加利润表体
	 * 
	 * @param request
	 *            请求控制
	 * @param marginMaster
	 *            利润表头
	 */
	public void MarginDetailadd(Request request, MarginMaster marginMaster) {
		casLogic.MarginDetailadd(marginMaster);
	}

	/**
	 * 现金流量计算
	 * 
	 * @param request
	 *            请求控制
	 * @param moneyMaster
	 *            现金流量表头
	 * @param times
	 *            行次
	 */
	public void accout(Request request, MoneyMaster moneyMaster, String times) {
		casLogic.account(moneyMaster, times);
	}

	/**
	 * 利润表计算
	 * 
	 * @param request
	 *            请求控制
	 * @param marginMaster
	 *            利润表表头
	 */
	public void MarginAccount(Request request, MarginMaster marginMaster) {
		casLogic.marginAccount(marginMaster);
	}

	/**
	 * 资产负债统计情况
	 * 
	 * @param request
	 *            请求控制
	 * @param debtMaster
	 *            资产负债表头
	 * @param times
	 *            行次
	 * @param beginEnd
	 *            期初或期末数
	 */
	public void debtAccount(Request request, DebtMaster debtMaster,
			String times, String beginEnd) {
		casLogic.debtAccount(debtMaster, times, beginEnd);
	}

	/**
	 * 加工贸易原材料来源与使用情况表
	 * 
	 * @param request
	 *            请求控制
	 * @param conditions
	 *            conditions中包括对单据生效日期的过滤
	 * @return returnAsCustom false 工厂资料 true 海关资料
	 */
	public List findImgOrgUseInfos(Request request, Date beginDate,
			Date endDate, String taskId, Boolean isShowTranFact) {
		return imgOrgUseInfoLogic.findImgOrgUseInfos(beginDate, endDate,
				taskId, isShowTranFact);
	}

	/**
	 * 单据报关单对比
	 * 
	 * @param request
	 *            请求控制
	 * @param conditions
	 *            查询条件包括有效期
	 * @return 海关帐中存在的料件单据与进口报关单对比
	 */
	public List findBillCustomBillCmpImgInfos(Request request, Date beginDate,
			Date endDate) {
		return imgOrgUseInfoLogic.findBillCustomBillCmpImgInfos(beginDate,
				endDate);
	}

	/**
	 * 4． 已结转未收货: 当相同客户名称的 （ “结转进口” 单据折算报关数量的累加 －“结转料件退货单” 单据折算报关数量的累加 <
	 * “结转报关进口”数量的累加 ）时, 所有客户两者差额值的和。 carryForwardF3Map 已结转未收货
	 * 
	 * 5． 未结转已收货: 当相同客户名称的 （ “结转进口” 单据折算报关数量的累加 －“结转料件退货单” 单据折算报关数量的累加 >“结转报关进口”
	 * 数量的累加 ）时, 所有客户两者差额值的和。 carryForwardF4Map 未结转已收货 *
	 * 
	 * @param request
	 *            请求控制
	 * @param conditions
	 *            查询条件
	 * @param scmCoc
	 *            客户
	 * @return 加工贸易原材料来源与使用情况表中的结转收货对比信息
	 */
	public List findCarryForwardCmpImgInfos(Request request, Date beginDate,
			Date endDate, ScmCoc scmCoc) {
		return imgOrgUseInfoLogic.findCarryForwardCmpImgInfos(beginDate,
				endDate, scmCoc);
	}

	/**
	 * 显示保存出口成品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param conditions
	 *            conditions中包括对单据生效日期的过滤
	 * @return 生效日期过滤后的出口成品信息
	 */
	public List findExgExportInfos(Request request, Date beginDate,
			Date endDate, String taskId, Boolean isShowTranFact) {
		return exgExportInfoLogic.findExgExportInfos(beginDate, endDate,
				taskId, isShowTranFact);
	}

	/**
	 * 单据报关单对比
	 * 
	 * @param request
	 *            请求控制
	 * @param conditions
	 *            查询条件
	 * @return 海关帐中存在的有记录的单据与报关单比对
	 */
	public List findBillCustomBillCmpExgInfos(Request request, Date beginDate,
			Date endDate) {
		return exgExportInfoLogic.findBillCustomBillCmpExgInfos(beginDate,
				endDate);
	}

	/**
	 * 结转成品对比
	 * 
	 * @param request
	 *            请求控制
	 * @param conditions
	 *            查询条件
	 * @param scmCoc
	 *            客户/供应商
	 * @return 按客户分的结转成品比对
	 */
	public List findCarryForwardCmpExgInfos(Request request, Date beginDate,
			Date endDate, ScmCoc scmCoc) {
		return exgExportInfoLogic.findCarryForwardCmpExgInfos(beginDate,
				endDate, scmCoc);
	}

	/**
	 * @return Returns the imgOrgUseInfoLogic.
	 */
	public ImgOrgUseInfoLogic getImgOrgUseInfoLogic() {
		return imgOrgUseInfoLogic;
	}

	/**
	 * @param imgOrgUseInfoLogic
	 *            The imgOrgUseInfoLogic to set.
	 */
	public void setImgOrgUseInfoLogic(ImgOrgUseInfoLogic imgOrgUseInfoLogic) {
		this.imgOrgUseInfoLogic = imgOrgUseInfoLogic;
	}

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
	// String scmCocId) {
	// return this.transferInfoLogic
	// .findTempBillMasterIsAvailabilityToTFBByfe(scmCocId);
	// }
	//
	// /**
	// * 获得在单据中商品信息不是全部转为真的单据记录 Tfi == 结转进口
	// *
	// * @param request
	// * 请求控制
	// * @param scmCocId
	// * 客户id
	// * @return 单据类型为料件入库的单据中商品信息不是全部转为真的单据记录
	// */
	// public List findTempBillMasterIsAvailabilityToTFBByTfi(Request request,
	// String scmCocId) {
	// return this.transferInfoLogic
	// .findTempBillMasterIsAvailabilityToTFBByTfi(scmCocId);
	// }
	/**
	 * 显示单据明细来自已选商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            临时单据明细
	 * @return 根据已选商品单据获得其所有未转结转单的商品信息
	 */
	public List findBillDetailByParent(Request request, List list) {
		return this.transferInfoLogic.findBillDetailByParent(list);
	}
	
	/**
	 * @return Returns the transferInfoLogic.
	 */
	public TransferInfoLogic getTransferInfoLogic() {
		return transferInfoLogic;
	}

	/**
	 * @param transferInfoLogic
	 *            The transferInfoLogic to set.
	 */
	public void setTransferInfoLogic(TransferInfoLogic transferInfoLogic) {
		this.transferInfoLogic = transferInfoLogic;
	}

	/**
	 * 获得在单据中商品信息不是全部转为真的单据记录 IXRB == Import Exprot Request Bill 进出口申请单
	 * 
	 * @param request
	 *            请求控制 billCode 单据类型下的单据代号
	 * @param impExpType
	 *            单据类型
	 * @return 与指定类型匹配的单据商品信息不是全部转为真的单据记录
	 */
	public List findBillMasterIsAvailabilityToIXRB(Request request,
			int impExpType) {
		return this.transferInfoLogic
				.findBillMasterIsAvailabilityToIXRB(impExpType);
	}

	/**
	 * 根据已选商品单据获得其所有未转进出口的商品信息 IXRB=improt expromt request bill
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            临时单据表头明细
	 * @return 所有未转进出口的商品信息
	 */
	public List findBillDetailByParentToIMXP(Request request, List list) {
		return this.transferInfoLogic.findBillDetailByParentToIMXP(list);
	}

	/**
	 * 有数据已转进出口申请单来自海关账单据头
	 * 
	 * @param request
	 *            请求控制
	 * @param b
	 *            单据表头
	 * @return 所有已转进出口申请单
	 */
	public boolean hasDataTransferImpExpRequestBillByBillMaster(
			Request request, BillMaster b) {
		return this.casDao.hasDataTransferImpExpRequestBillByBillMaster(b);
	}

	/**
	 * 有数据已转结转单单据来自海关账单据体
	 * 
	 * @param request
	 *            请求控制
	 * @param b
	 *            单据头
	 * @return 所有已转结转单据单单据
	 */
	public boolean hasDataTransferTransferFactoryBillByBillMaster(
			Request request, BillMaster b) {
		return this.casDao.hasDataTransferTransferFactoryBillByBillMaster(b);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.cas.action.CasAction#saveMoneyDetails(com.bestway.common
	 * .Request, com.bestway.bcus.cas.entity.MoneyDetail)
	 */
	public MoneyDetail saveMoneyDetails(Request request, MoneyDetail moneyDetail) {
		// 
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.cas.action.CasAction#moneyDetailadd(com.bestway.common
	 * .Request, com.bestway.bcus.cas.entity.MoneyMaster)
	 */
	public void moneyDetailadd(Request request, MoneyMaster moneyMaster) {
		// 
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.cas.action.CasAction#account(com.bestway.common.Request,
	 * com.bestway.bcus.cas.entity.MoneyMaster, java.lang.String)
	 */
	public void account(Request request, MoneyMaster moneyMaster, String times) {
		// 
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.cas.action.CasAction#marginAccount(com.bestway.common
	 * .Request, com.bestway.bcus.cas.entity.MarginMaster)
	 */
	public void marginAccount(Request request, MarginMaster marginMaster) {
	}

	/**
	 * 显示资料主表 统计报关名称
	 * 
	 * @param request
	 *            请求控制
	 * @return 工厂大类内容
	 */
	public List findStatCusNameRelation(Request request) {
		return casDao.findStatCusNameRelation();
	}

	/**
	 * 保存大类内容
	 * 
	 * @param request
	 *            请求控制
	 * @param statCusNameRelation
	 *            大类内容
	 * @return 临时大类内容
	 */
	public List<TempStatCusNameRelation> saveStatCusNameRelation(
			Request request, StatCusNameRelation statCusNameRelation) {
		return this.casLogic.saveStatCusNameRelation(statCusNameRelation);
	}

	/**
	 * 删除资料主表
	 * 
	 * @param request
	 *            请求控制
	 * @param statCusNameRelation
	 *            大类内容资料主表
	 */
	public void deleteStatCusNameRelation(Request request,
			StatCusNameRelation statCusNameRelation) {
		casLogic.deleteStatCusNameRelation(statCusNameRelation);
	}

	/**
	 * 显示资料副表 工厂料件
	 * 
	 * @param request
	 *            请求控制
	 * @param statCusNameRelation
	 *            资料主表内容
	 * @return 与主表内容相对应的工厂料件
	 */
	public List findStatCusNameRelationMt(Request request,
			StatCusNameRelation statCusNameRelation) {
		return new ArrayList();
		// return casDao.findStatCusNameRelationMt(statCusNameRelation);
	}

	/**
	 * 保存资料副表 工厂料件
	 * 
	 * @param request
	 *            请求控制
	 * @param statCusNameRelationMt
	 *            工厂料件
	 */
	public StatCusNameRelationMt saveStatCusNameRelationMt(Request request,
			StatCusNameRelationMt statCusNameRelationMt) {
		casDao.saveStatCusNameRelationMt(statCusNameRelationMt);
		return statCusNameRelationMt;
	}

	/**
	 * 删除资料副表 工厂料件
	 * 
	 * @param request
	 *            请求控制
	 * @param statCusNameRelationMt
	 *            工厂料件
	 */
	public void deleteStatCusNameRelationMt(Request request,
			StatCusNameRelationMt statCusNameRelationMt) {
		casDao.deleteStatCusNameRelationMt(statCusNameRelationMt);
	}

	/**
	 * 显示资料副表 实际报关商品
	 * 
	 * @param request
	 *            请求控制
	 * @param statCusNameRelation
	 *            资料主表商品大类
	 * @return 与指定的商品大类对应的按序号排序的实际报关商品内容
	 */
	public List findStatCusNameRelationHsn(Request request,
			StatCusNameRelation statCusNameRelation) {
		return casDao.findStatCusNameRelationHsn(statCusNameRelation);
	}

	/**
	 * 保存资料副表 实际报关商品
	 * 
	 * @param request
	 *            请求控制
	 * @param statCusNameRelationHsn
	 *            实际报关商品
	 * @return 实际报关商品
	 */
	public StatCusNameRelationHsn saveStatCusNameRelationHsn(Request request,
			StatCusNameRelationHsn statCusNameRelationHsn) {
		casDao.saveStatCusNameRelationHsn(statCusNameRelationHsn);
		return statCusNameRelationHsn;
	}

	/**
	 * 删除资料副表 实际报关商品
	 * 
	 * @param request
	 *            请求控制
	 * @param statCusNameRelationHsn
	 *            实际报关商品
	 */
	public void deleteStatCusNameRelationHsn(Request request,
			StatCusNameRelationHsn statCusNameRelationHsn) {
		casDao.deleteStatCusNameRelationHsn(statCusNameRelationHsn);
	}

	/**
	 * 保存资料副表 实际报关商品
	 * 
	 * @param request
	 * @param statCusNameRelation
	 *            大类内容 资料主表
	 * @param lsTempCommInfo
	 *            临时的商品信息
	 * @return 与商品大类相关联的实际报关商品
	 */
	public List<TempStatCusNameRelation> saveStatCusNameRelationHsn(
			Request request, StatCusNameRelation statCusNameRelation,
			List lsTempCommInfo) {
		return this.casLogic.saveStatCusNameRelationHsn(statCusNameRelation,
				lsTempCommInfo);
	}

	/**
	 * 保存资料副表 工厂料件
	 * 
	 * @param request
	 * @param statCusNameRelation
	 *            商品大类 资料主表
	 * @param lsTempCommInfo
	 *            临时的商品信息 企业物料
	 * @return 与商品大类相关联的工厂料件
	 */
	public List<TempStatCusNameRelation> saveStatCusNameRelationMt(
			Request request, StatCusNameRelation statCusNameRelation,
			List lsTempCommInfo) {
		return this.casLogic.saveStatCusNameRelationMt(statCusNameRelation,
				lsTempCommInfo);
	}

	/**
	 * 删除资料副表 实际报关商品
	 * 
	 * @param request
	 *            请求控制
	 * @param lsRelation
	 *            临时关系对照表
	 * @return 实际报关商品
	 */
	public List<TempStatCusNameRelation> deleteStatCusNameRelationHsn(
			Request request, List lsRelation) {
		return this.casLogic.deleteStatCusNameRelationHsn(lsRelation);
	}

	/**
	 * * 删除资料副表 工厂料件
	 * 
	 * @param request
	 *            请求控制
	 * @param lsRelation
	 *            临时商品大类关系对照表
	 * @return 工厂物料
	 */
	public List<TempStatCusNameRelation> deleteStatCusNameRelationMt(
			Request request, List lsRelation) {
		return this.casLogic.deleteStatCusNameRelationMt(lsRelation);
	}

	/**
	 * 抓取对照表主表
	 * 
	 * @param request
	 *            请求控制
	 * @param materialType
	 *            物料类型
	 * @return 与物料类型匹配的对照主表
	 */
	public List findStatCusNameRelation(Request request, String materialType) {
		return this.casLogic.findStatCusNameRelation(materialType);
	}

	/**
	 * 新增对照关系时，选择物料
	 * 
	 * @param request
	 *            请求控制
	 * @param materialType
	 *            物料类型
	 * @return 与商品大类关联的物料内容
	 */
	public List findMaterialForRelation(Request request, String materialType) {
		return this.casLogic.findMaterialForRelation(materialType);
	}

	/**
	 * 新增商品大类时,进行大类编码查询
	 * 
	 * @param request
	 *            请求控制
	 * @param materialType
	 *            物料类型
	 * @return 不在商品大类中存在的商品编码
	 */
	public List findComplex(Request request, String materialType) {
		return this.casDao.findComplex(materialType);
	}

	public List findComplex(Request request) {
		return this.casDao.findComplex();
	}

	/**
	 * 新增对照关系时，选择报关商品
	 * 
	 * @param materialType
	 *            物料类型
	 * @return 与商品大类关联的实际报关商品内容
	 */
	public List findComplexForRelation(Request request, String materialType) {
		return this.casLogic.findComplexForRelation(materialType);
	}

	/**
	 * 分页查询所有的工厂物料
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
	 * @return 所有的工厂物料
	 */
	public List<StatCusNameRelationMt> findStatCusNameRelationMt(
			Request request, String materielType, int index, int length,
			String property, Object value, boolean isLike) {
		return this.casDao.findStatCusNameRelationMt(materielType, index,
				length, property, value, isLike);
	}

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
	public List<StatCusNameRelationHsn> findStatCusNameRelationHsn(
			Request request, String ptNo, int index, int length,
			String property, Object value, boolean isLike) {
		return this.casDao.findStatCusNameRelationHsn(ptNo, index, length,
				property, value, isLike);
	}

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
			String property, Object value, boolean isLike) {
		return this.casDao.findFactoryAndFactualCustomsRalation(materielType,
				index, length, property, value, isLike);
	}

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
			String ptNo, Date compareDate) {
		return this.casDao.findStatCusNameRelationHsn(ptNo, compareDate);
	}

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
			String ptNo, Date compareDate) {
		return this.casDao.findFactualCustoms(ptNo, compareDate);
	}

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
			boolean isLike) {
		return this.casDao.findFactualCustoms(ptNo, index, length, property,
				value, isLike);
	}

	/**
	 * 当删除物料或者报关商品的时候,检查所选择的资料是否属于同一个大类,如果true,允许删除, 否则不能删除
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            临时商品对照关系表
	 * @return 如果true,允许删除, 否则不能删除
	 */
	public boolean checkIsSameRelation(Request request,
			List<TempStatCusNameRelation> list) {
		return this.casLogic.checkIsSameRelation(list);
	}

	/**
	 * 当删除修改物料的时候,检查所选择的资料是否属于同一个物料,如果true,允许修改, 否则不能修改
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            对照关系表中的物料
	 * @return true,允许修改, 否则不能修改
	 */
	public boolean checkIsSameMt(Request request,
			List<TempStatCusNameRelation> list) {
		return this.casLogic.checkIsSameMt(list);
	}

	/**
	 * 当删除修改报关商品的时候,检查所选择的资料是否属于同一个报关商品,如果true,允许修改, 否则不能修改
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            对照关系中的实际报关商品
	 * @return true,允许修改, 否则不能修改
	 */
	public boolean checkIsSameHsn(Request request,
			List<TempStatCusNameRelation> list) {
		return this.casLogic.checkIsSameHsn(list);
	}

	/**
	 * 显示单据对应控制对象
	 * 
	 * @param request
	 *            请求控制
	 * @return 单据对应控制对象
	 */
	public BillCorrespondingControl findBillCorrespondingControl(Request request) {
		return this.casParameterSetLogic.findBillCorrespondingControl();
	}

	/**
	 * 显示海关帐基本单据项目设定
	 * 
	 * @param request
	 *            请求控制
	 * @return 海关帐单据项目设定 (单据是否允许重复 双击是否修改还是流览.......)
	 */
	public CasBillOption findCasBillOption(Request request) {
		return this.casParameterSetDao.findCasBillOption();
	}

	/**
	 * 显示制单号控制对象
	 * 
	 * @param request
	 *            请求控制
	 * @return 制单号控制对象 (车间入库单据录入时需要制造令 车间返回单据录入时需要制造令......)
	 */
	public LotControl findLotControl(Request request) {
		return this.casParameterSetDao.findLotControl();
	}

	/**
	 * 显示其它项目选项
	 * 
	 * @param request
	 *            请求控制
	 * @return 其他选项(名称规格是否重复 进出仓栏只显示月结存数......)
	 */
	public OtherOption findOtherOption(Request request) {
		return this.casParameterSetDao.findOtherOption();
	}

	/**
	 * 显示记帐年度对象
	 * 
	 * @param request
	 *            请求控制
	 * @return 记帐年度
	 */
	public WriteAccountYear findWriteAccountYear(Request request) {
		return this.casParameterSetLogic.findWriteAccountYear();
	}

	/**
	 * 保存单据对应控制对象
	 * 
	 * @param request
	 *            请求控制
	 * @param temp
	 *            单据对应控制对象
	 * @return 单据对应控制对象
	 */
	public BillCorrespondingControl saveBillCorrespondingControl(
			Request request, BillCorrespondingControl temp) {
		this.casParameterSetLogic.saveBillCorrespondingControl(temp);
		return temp;
	}

	/**
	 * 保存海关帐基本单据项目设定
	 * 
	 * @param request
	 *            请求控制
	 * @param temp
	 *            海关帐单据项目设定 (单据是否允许重复 双击是否修改还是流览.......)
	 * @return 海关帐基本单据项目设定
	 */
	public CasBillOption saveCasBillOption(Request request, CasBillOption temp) {
		this.casParameterSetDao.saveCasBillOption(temp);
		return temp;
	}

	/**
	 * 保存制单号控制对象
	 * 
	 * @param request
	 *            请求控制
	 * @param temp
	 *            制单号控制对象 (车间入库单据录入时需要制造令 车间返回单据录入时需要制造令......)
	 * @return 制单号控制对象
	 */
	public LotControl saveLotControl(Request request, LotControl temp) {
		this.casParameterSetDao.saveLotControl(temp);
		return temp;
	}

	/**
	 * 保存其它项目选项
	 * 
	 * @param request
	 *            请求控制
	 * @param temp
	 *            其他选项(名称规格是否重复 进出仓栏只显示月结存数......)
	 * @return 其它选项
	 */
	public OtherOption saveOtherOption(Request request, OtherOption temp) {
		this.casParameterSetDao.saveOtherOption(temp);
		return temp;
	}

	/**
	 * 保存记帐年度对象
	 * 
	 * @param request
	 *            请求控制
	 * @param temp
	 *            记帐年度
	 * @return 记帐年度
	 */
	public WriteAccountYear saveWriteAccountYear(Request request,
			WriteAccountYear temp) {
		this.casParameterSetLogic.saveWriteAccountYear(temp);
		return temp;
	}

	/**
	 * 显示单据对应选项
	 * 
	 * @param request
	 *            请求控制
	 * @return 单据对应选项
	 */
	public BillCorrespondingOption findBillCorrespondingOption(Request request) {
		return this.casParameterSetLogic.findBillCorrespondingOption();
	}

	/**
	 * 保存单据对应选项
	 * 
	 * @param request
	 *            请求控制
	 * @param temp
	 *            单据对应选项
	 * @return 单据对应选项
	 */
	public BillCorrespondingOption saveBillCorrespondingOption(Request request,
			BillCorrespondingOption temp) {
		this.casParameterSetLogic.saveBillCorrespondingOption(temp);
		return temp;
	}

	/**
	 * 显示单据的对应的单据明细(生效的记录)
	 * 
	 * @param request
	 *            请求控制
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
	public List<BillDetail> findBillDetail(Request request, Integer impExpType,
			ScmCoc scmCoc, Date beginData, Date endData, String nameSpec,Boolean isNameSpec) {
		return this.casSpecificontrolLogic.findBillDetail(impExpType, scmCoc,
				beginData, endData, nameSpec,isNameSpec);
	}

	public CasSpecificontrolLogic getCasSpecificontrolLogic() {
		return casSpecificontrolLogic;
	}

	public void setCasSpecificontrolLogic(
			CasSpecificontrolLogic casSpecificontrolLogic) {
		this.casSpecificontrolLogic = casSpecificontrolLogic;
	}

	/**
	 * 保存报关单商品信息与海关帐单据的对应
	 * 
	 * @param request
	 *            请求控制
	 * @param c
	 *            报关单商品信息与海关帐单据的对应
	 * @return 报关单商品信息与海关帐单据的对应
	 */
	public CustomsDeclarationCommInfoBillCorresponding saveCustomsDeclarationCommInfoBillCorresponding(
			Request request, CustomsDeclarationCommInfoBillCorresponding c) {
		this.casSpecificontrolDao
				.saveCustomsDeclarationCommInfoBillCorresponding(c);
		return c;
	}

	/**
	 * 显示报关单商品信息与海关帐单据的对应
	 * 
	 * @param request
	 *            请求控制
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
	 *@param isNameSpec 判断是选择名称(true)还是名称+规格(false)查询
	 * @return 已经对应的报关单商品信息和海关帐单据
	 */
	public List findCustomsDeclarationCommInfoBillCorresponding(
			Request request, Integer impExpType, ScmCoc scmCoc, Date beginData,
			Date endData, String name, String spec,Boolean isNameSpec) {
		return this.casSpecificontrolLogic
				.findCustomsDeclarationCommInfoBillCorresponding(impExpType,
						scmCoc, beginData, endData, name, spec,isNameSpec);
	}

	/**
	 * 显示对应表商品大类名称 注意：必须Distinct 因为名称有可能会重复。
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @return 与物料类型匹配的商品大类名称
	 */
	public List findStatCusNameRelationName(Request request, String materielType) {
		return this.casSpecificontrolDao
				.findStatCusNameRelationName(materielType);
	}
	/**
	 * 显示客户列表
	 */
	public List findIsCustomer(Request request){
		return this.casSpecificontrolDao.findIsCustomer();
	}
	/**
	 * 显示供应商列表
	 */
	public List findManufacturer(Request request){
		return this.casSpecificontrolDao.findIsManufacturer();
	}
	/**
	 * 显示对应表商品大类名称 注意：必须Distinct 因为名称有可能会重复。
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @return 与物料类型匹配的商品大类名称
	 */
	public List findStatCusNameRelationName2(Request request,
			String materielType) {
		return this.casSpecificontrolDao
				.findStatCusNameRelationName2(materielType);
	}

	/**
	 * 根据大类规格查找相对应的报关规格
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @param cusName
	 *            报关名称
	 * @return 与大类规格对应的报关规格
	 */
	public List findStatCusNameRelationSpec(Request request,
			String materielType, String cusName) {
		return this.casSpecificontrolDao.findStatCusNameRelationSpec(
				materielType, cusName);
	}

	/**
	 * 显示报关商品信息，来自对应的大类信息
	 * 
	 * @param request
	 *            请求控制
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
	public List findStatCusNameRelationHsn(Request request, String complexCode,
			String name, String spec, String materielType) {
		return this.casSpecificontrolDao.findStatCusNameRelationHsn(
				complexCode, name, spec, materielType);
	}

	/**
	 * 取消单据对应
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            生产单据对应的临时中间信息
	 */
	public void cancelCorresponding(Request request,
			List<MakeBillCorrespondingInfoBase> list) {
		this.casSpecificontrolLogic.cancelCorresponding(list);
	}

	/**
	 * 写入单据(手工批量对应操作)
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            单据表头
	 * @param corresponding
	 *            对应报关单号
	 */
	public void writeIn(Request request, List<BillMaster> list,
			String corresponding) {
		this.casSpecificontrolLogic.writeIn(list, corresponding);
	}

	/**
	 * 显示单据资料主表来自类型
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpType
	 *            进出口类型
	 * @return 与指定的进出口类型的单据资料主表
	 */
	public List findBillMaster(Request request, Integer impExpType) {
		return this.casSpecificontrolDao.findBillMaster(impExpType);
	}

	/**
	 * 显示所有合同协议号
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpType
	 *            进出口类型
	 * @return 与指定的进出口类型匹配且来自纸质手册的合同协议号
	 */
	public List findEmsNo(Request request) {
		return this.casSpecificontrolLogic.findEmsNo();
	}

	/**
	 * 显示所有报关单号
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpType
	 *            进出口类型
	 * @return 与指定的进出口类型相同且来自纸质手册和联网监管的报关单号
	 */
	public List findCustomsDeclarationCode(Request request, Integer impExpType,
			String emsNo) {
		return this.casSpecificontrolLogic.findCustomsDeclarationCode(
				impExpType, emsNo);
	}

	/**
	 * 依单据类型返回报关单列表
	 * 
	 * @param request
	 *            请求控制
	 * @param materialtype
	 *            物料类型
	 * @return 与指定的物料类型匹配并且没有记帐的单据
	 */
	public List findBillMasterWithMaterielType(Request request,
			String materialtype) {
		return this.casSpecificontrolDao
				.findBillMasterWithMaterielType(materialtype);
	}

	/**
	 * 记帐，和帐务记帐回卷
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            单据表头
	 * @param isEffective
	 *            是否记帐
	 * @return 保存记帐，和帐务记帐回卷的内容
	 */
	public List saveBillMaster(Request request, List<BillMaster> list,
			boolean isEffective) {
		this.casSpecificontrolLogic.saveBillMaster(list, isEffective);
		return list;
	}

	/**
	 * 显示单据是否记帐
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @param isEffective
	 *            是否生效
	 * @return 已记帐的单据
	 */
	public List findBillMaster(Request request, String materielType,
			boolean isEffective) {
		return this.casSpecificontrolDao.findBillMaster(materielType,
				isEffective);
	}

	/**
	 * 依单据类型回滚报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param billmasterlist
	 *            需要回滚的单据对象
	 * @return 返回回卷成功与出错的记录
	 */
	public List saveRollbackBillMaster(Request request, List billmasterlist) {
		this.casSpecificontrolDao.rollbackBillMasterAll(billmasterlist);
		return billmasterlist;
	}

	/**
	 * 执行 生效的单据表头
	 * 
	 * @param request
	 *            请求控制
	 * @param billMasterList
	 *            需要更新的对象
	 * @return 更新不成功的对象,全部成功返回size为0的List.
	 */
	public List saveEffectBillMasterAll(Request request,
			List<BillMaster> billMasterList) {
		this.casSpecificontrolDao.saveBillMasterAll(billMasterList);
		return billMasterList;
	}

	/**
	 * 保存生效单据
	 * 
	 * @param request
	 *            请求控制
	 * @param billType
	 *            单据类型
	 * @param isEffect
	 *            是否生效
	 */
	public void saveBillMasterEffect(Request request, BillType billType,
			boolean isEffect) {
		this.casBillDao.saveBillMasterEffect(billType, isEffect);
	}

	/**
	 * 批量删除单据 返回删除成功与出错的记录
	 * 
	 * @param request
	 *            请求控制
	 * @param billmasterlist
	 *            需要删除的单据对象
	 */
	public void deleteBatchBillMaster(Request request,
			List<BillMaster> billmasterlist) {
		this.casSpecificontrolLogic.deleteBatchBillMaster(billmasterlist);
	}

	/**
	 * 显示所有仓库的信息
	 * 
	 * @param request
	 *            请求控制
	 * @return 仓库信息
	 */
	public List<WareSet> findWareSet(Request request) {
		return this.casDao.findWareSet();
	}

	/**
	 * 显示所有工厂名称信息
	 * 
	 * @param request
	 *            请求控制
	 * @param kindCode
	 *            类型代码
	 * @return 工厂名称信息
	 */
	public List findFactoryNames(Request request, String kindCode) {
		return this.casSpecificontrolDao.findFactoryNames(kindCode);
	}

	/**
	 * 显示单据头来自单据明细
	 * 
	 * @param request
	 *            请求控制
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
	public List<BillMaster> findBillMasterFromBillDetail(Request request,
			String productkind, String billtype, String client, Date startdate,
			Date enddate) {
		return this.casSpecificontrolDao.findBillMasterFromBillDetail(
				productkind, billtype, client, startdate, enddate);
	}

	/**
	 * 显示单据资料明细表来自Id和类型
	 * 
	 * @param request
	 *            请求控制
	 * @param id
	 *            明细表id
	 * @param sBillType
	 *            单据类型
	 * @return 与指定表名对应的单据明细
	 */
	public BillDetail findBillDetailById(Request request, String id,
			int sBillType) {
		return this.casBillDao.findBillDetailById(id, sBillType);
	}

	/**
	 * 显示单据资料明细表来自表头id和单据类型
	 * 
	 * @param request
	 *            请求控制
	 * @param masterId
	 *            表头id
	 * @param sBillType
	 *            单据类型
	 * @return 工厂单据明细
	 */
	public List<BillDetail> findBillDetail(Request request, String masterId,
			int sBillType) {
		 return  this.casBillLogic.findBillDetail(masterId, sBillType);
	}

	/**
	 * 分页查询 显示单据资料表头来自类型 属性 实体值等
	 * 
	 * @param request
	 *            请求控制
	 * @param billType
	 *            单据类型
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
	 * @return 单据表头资料
	 */
	public List<BillMaster> findBillMaster(Request request, BillType billType,
			int index, int length, String property, Object value, boolean isLike) {
		return this.casBillDao.findBillMaster(billType, index, length,
				property, value, isLike);
	}
	/**
	 * 查询工厂单据
	 * @param billType
	 * @param billNo
	 * @param scmCoc
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<BillMaster> findBillMaster(Request request,BillType billType, String billNo,
			String scmCoc, Date beginDate, Date endDate){
		return this.casBillDao.findBillMaster(billType, billNo, scmCoc, beginDate, endDate);
	}
	/**
	 * 显示单据资料来自类型 是否生效等等
	 * 
	 * @param request
	 *            请求控制
	 * @param billType
	 *            单据类型
	 * @param isEffect
	 *            是否生效
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
	 * @return 与单据类型匹配的符合条件的单据资料
	 */
	public List<BillMaster> findBillMaster(Request request, BillType billType,
			boolean isEffect, int index, int length, String property,
			Object value, boolean isLike) {
		return this.casBillDao.findBillMaster(billType, isEffect, index,
				length, property, value, isLike);
	}

	/**
	 * 生成对应表
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @param param
	 *            模块类型
	 * @return 刷新所有对应表
	 */
	public boolean saveCorrespondence(Request request, String materielType,
			ProjectTypeParam param) {
		return false;
		//
		// update 2007/7/23 by luosheng
		//
		// return this.makeCorrespondenceLogic.makeCorrespondence(materielType,
		// param);
	}

	/**
	 * 抓取对照表主表
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
	 * @return 与指定物料类型匹配的商品大类
	 */
	public List<TempStatCusNameRelation> findStatCusNameRelation(
			Request request, String materielType, int index, int length,
			String property, Object value, boolean isLike) {
		return this.casLogic.findStatCusNameRelation(materielType, index,
				length, property, value, isLike);
	}

	/**
	 * 单据对应
	 * 
	 * @param request
	 *            请求控制
	 * @param listC
	 *            报关单商品信息与海关帐单据的对应
	 * @param list
	 *            临时单据明细
	 * @return 单据对应 报关单商品信息与海关帐单据的对应
	 */
	public TempResult billCorresponding(Request request,
			List<CustomsDeclarationCommInfoBillCorresponding> listC,
			List<BillDetail> list) {
		return this.casSpecificontrolLogic.billCorresponding(listC, list);
	}

	/**
	 * 清除单据对应
	 * 
	 * @param request
	 *            请求控制
	 */
	public void deleteBillCorresponding(Request request) {
		this.casParameterSetDao.clearBillCorresponding();
	}

	/**
	 * 取得海关帐单据的入出汇总
	 * 
	 * @param request
	 *            请求控制
	 * @param intOutFlat
	 *            物料类型
	 * @param conditions
	 *            查询条件
	 * @param conditions1
	 *            查询条件
	 * @return 单据明细
	 */
	public List getCasSum(Request request, String materielType,
			List<Condition> conditions, boolean showZeroStore,boolean showVersion) {
		return this.casLogic.getCasSum(materielType, conditions, showZeroStore,showVersion);
	}

	/**
	 * 取得本月数量
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
	public List getMonth(Request request, String materielType, Date beginDate,
			Date endDate, boolean isRefresh) {
		return this.casLogic.getMonth(materielType, beginDate, endDate,
				isRefresh);
	}

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
	public List getAllMonth(Request request, String materielType,
			List<Integer> notExistList, Date beginDate, Date endDate,
			boolean isRefresh) {
		return this.casLogic.getAllMonth(materielType, notExistList, beginDate,
				endDate, isRefresh);
	}

	/**
	 * 显示所有当点月存在的
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @param currentMonth
	 *            本月
	 * @return 当前月存在的
	 */
	public List<Integer> findFrontMonthIsNotExist(Request request,
			String materielType, int currentMonth) {
		return this.casLogic.findFrontMonthIsNotExist(materielType,
				currentMonth);
	}

	/**
	 * 取得本月数量
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
	public List getMonth2(Request request, String materielType, Date beginDate,
			Date endDate, boolean isRefresh) {
		return this.casLogic.getMonth2(materielType, beginDate, endDate,
				isRefresh);
	}

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
	public List getAllMonth2(Request request, String materielType,
			List<Integer> notExistList, Date beginDate, Date endDate,
			boolean isRefresh) {
		return this.casLogic.getAllMonth2(materielType, notExistList,
				beginDate, endDate, isRefresh);
	}

	/**
	 * 显示所有当点月存在的
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @param currentMonth
	 *            本月
	 * @return 当前月存在的
	 */
	public List<Integer> findFrontMonthIsNotExist2(Request request,
			String materielType, int currentMonth) {
		return this.casLogic.findFrontMonthIsNotExist2(materielType,
				currentMonth);
	}

	/**
	 * 成品出口PK对应
	 * 
	 * @param request
	 *            请求控制
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 */
	public void saveBillCorresByPK(Request request, Date beginDate, Date endDate) {
		this.casBillLogic.billCorresByPK(beginDate, endDate);
	}

	/**
	 * 直接进口单据对应集装箱
	 * 
	 * @param request
	 *            请求控制
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 */
	public void saveBillCorresByContainer(Request request, Date beginDate,
			Date endDate) {
		this.casBillLogic.billCorresByContainer(beginDate, endDate);
	}

	/**
	 * 返回工厂商品名称来自单据明细
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
	 * @return 与指定的物料类型等对应工厂商品名称
	 */
	public List<TempObject> findPtNameFromBillDetail(Request request,
			String materielType, int index, int length, String property,
			Object value, boolean isLike) {
		return this.casBillLogic.findPtNameFromBillDetail(materielType, index,
				length, property, value, isLike);
	}/**
	 * 返回工厂商品规格来自单据明细
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
	 * @return 与指定的物料类型等对应工厂商品名称
	 */
	public List<TempObject> findPtSpecFromBillDetail(Request request,
			String materielType, int index, int length, String property,
			Object value, boolean isLike,String ptName) {
		return this.casBillLogic.findPtSpecFromBillDetail(materielType, index,
				length, property, value, isLike,ptName);
	}

	public List<TempObject> findPtNameFromInvoice(Request request, int index,
			int length, String property, Object value, boolean isLike) {
		return this.casBillLogic.findPtNameFromInvoice(index, length, property,
				value, isLike);
	}

	/**
	 * 返回实际报关的商品名称来自实际报关商品
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
	public List<TempObject> findHsNameFromStatCusNameRelationHsn(
			Request request, String materielType, int index, int length,
			String property, Object value, boolean isLike) {
		return this.casBillLogic.findHsNameFromStatCusNameRelationHsn(
				materielType, index, length, property, value, isLike);
	}

	/**
	 * 返回实际报关的商品名称来自实际报关商品
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
	public List<TempObject> findHsNameFromFactoryAndFactualCustomsRalation(
			Request request, String materielType, int index, int length,
			String property, Object value, boolean isLike) {
		return this.casBillLogic
				.findHsNameFromFactoryAndFactualCustomsRalation(materielType,
						index, length, property, value, isLike);
	}
	
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
	public List<TempObject> findHsNameFromTransferFactoryBill(
			Request request, boolean isImp, int index, int length,
			String property, Object value, boolean isLike) {
		return this.casBillLogic
				.findHsNameFromTransferFactoryBill(isImp,
						index, length, property, value, isLike);
	}

	/**
	 * 返回商品大类报关规格来自实际报关商品
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
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
	public List<TempObject> findHsSpecFromStatCusNameRelationHsn(
			Request request, String materielType, String hsName, int index,
			int length, String property, Object value, boolean isLike) {
		return this.casBillLogic.findHsSpecFromStatCusNameRelationHsn(
				materielType, hsName, index, length, property, value, isLike);
	}
	
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
	public List<TempObject> findHsNameFromCustomsEnvelopCommodityInfo(
			Request request, String cusName, String hsName, int index,
			int length, String property, Object value, boolean isImp, boolean isName, boolean isLike) {
		return this.casBillLogic.findHsNameFromCustomsEnvelopCommodityInfo(
				cusName, hsName, index, length, property, value, isImp, isName, isLike);
	}

	/**
	 * 返回商品大类报关规格来自实际报关商品
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
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
	public List<TempObject> findHsSpecFromFactoryAndFactualCustomsRalation(
			Request request, String materielType, String hsName, int index,
			int length, String property, Object value, boolean isLike) {
		return this.casBillLogic
				.findHsSpecFromFactoryAndFactualCustomsRalation(materielType,
						hsName, index, length, property, value, isLike);
	}

	public List<TempObject> findHalfProductHsSpec(
			Request request, String materielType, String hsName, int index,
			int length, String property, Object value, boolean isLike) {
		return this.casBillLogic
				.findHalfProductHsSpec(materielType,
						hsName, index, length, property, value, isLike);
	}
	public List<TempObject> findDetailProductHsSpec(
			Request request, String materielType, String hsName, int index,
			int length, String property, Object value, boolean isLike) {
		return this.casBillLogic
				.findDetailProductHsSpec(materielType,
						hsName, index, length, property, value, isLike);
	}
	
	/**
	 * 成品名称
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
	public List<TempObject> findDetailProductHsName(
			Request request, String materielType, String hsName, int index,
			int length, String property, Object value, boolean isLike) {
		return this.casBillLogic
				.findDetailProductHsName(materielType,
						hsName, index, length, property, value, isLike);
	}
	/**
	 * 半成品名称
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
	public List<TempObject> findDetailMaterielHsName(
			Request request, String materielType, String hsName, int index,
			int length, String property, Object value, boolean isLike){
		return this.casBillLogic
				.findDetailMaterielHsName(materielType,
						hsName, index, length, property, value, isLike);
	}
	/**
	 * 残次品名称
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
	public List<TempObject> findDetailRemainProductHsName(
			Request request, String materielType, String hsName, int index,
			int length, String property, Object value, boolean isLike){
		return this.casBillLogic
				.findDetailRemainProductHsName(materielType,
						hsName, index, length, property, value, isLike);
	}
	/**
	 * 返回对照表中的报关商品单位来自实际报关商品
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
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
	 * @return 对照表中的报关单位
	 */
	public List<TempObject> findHsUnitFromFactoryAndFactualCustomsRalation(
			Request request, String materielType, String hsName, int index,
			int length, String property, Object value, boolean isLike) {
		return this.casBillLogic
				.findHsUnitFromFactoryAndFactualCustomsRalation(materielType,
						hsName, index, length, property, value, isLike);
	}

	/**
	 * 返回工厂商品名称来自工厂物料
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
	 * @return 工厂商品名称
	 */
	public List<TempObject> findPtNameFromStatCusNameRelationMt(
			Request request, String materielType, int index, int length,
			String property, Object value, boolean isLike) {
		return this.casBillLogic.findPtNameFromStatCusNameRelationMt(
				materielType, index, length, property, value, isLike);
	}

	/**
	 * 返回工厂商品名称来自工厂物料
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
	 * @return 工厂商品名称
	 */
	public List<TempObject> findPtNameFromFactoryAndFactualCustomsRalation(
			Request request, String materielType, int index, int length,
			String property, Object value, boolean isLike) {
		return this.casBillLogic
				.findPtNameFromFactoryAndFactualCustomsRalation(materielType,
						index, length, property, value, isLike);
	}

	public List<TempObject> findPWrokOrder(Request request, int index, int length,
			String property, Object value, boolean isLike){
		return this.casBillLogic.findPWrokOrder(
				index, length, property, value, isLike);
	}
	
	public List<TempObject> findMPWrokOrder(Request request, int index, int length,
			String property, Object value, boolean isLike){
		return this.casBillLogic.findMPWrokOrder(
				index, length, property, value, isLike);
	}
	
	public List<TempObject> findBWrokOrder(Request request, int index, int length,
			String property, Object value, boolean isLike){
				return this.casBillLogic.findBWrokOrder(
						index, length, property, value, isLike);
			}
	
	public List<TempObject> findMWrokOrder(Request request, int index, int length,
			String property, Object value, boolean isLike){
				return this.casBillLogic.findMWrokOrder(
						index, length, property, value, isLike);
			}
	/**
	 * 返回工厂商品规格来自工厂物料
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @param ptName
	 *            工厂商品名称
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
	 * @return 工厂商品名称规格
	 */
	public List<TempObject> findPtSpecFromStatCusNameRelationMt(
			Request request, String materielType, String ptName, int index,
			int length, String property, Object value, boolean isLike) {
		return this.casBillLogic.findPtSpecFromStatCusNameRelationMt(
				materielType, ptName, index, length, property, value, isLike);
	}

	/**
	 * 返回工厂商品规格来自工厂物料
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @param ptName
	 *            工厂商品名称
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
	 * @return 工厂商品名称规格
	 */
	public List<TempObject> findPtSpecFromFactoryAndFactualCustomsRalation(
			Request request, String materielType, String ptName, int index,
			int length, String property, Object value, boolean isLike) {
		return this.casBillLogic
				.findPtSpecFromFactoryAndFactualCustomsRalation(materielType,
						ptName, index, length, property, value, isLike);
	}

	/**
	 * 生成单据的折算报关数量(未折算报关数量的单据) 当前公司，由客户端调用
	 * 
	 * @param request
	 *            请求控制
	 * @param setupParameters
	 *            生成单据的折算报关数量(未折算报关数量的单据)
	 */
	public void makeBillHsAmount(Request request,
			TempMaterielTypeSetup setupParameters) {
		this.casSpecificontrolLogic.makeBillHsAmount(setupParameters);
	}

	/**
	 * 获得自定义统计资料(原材料统计报表)
	 * 
	 * @param request
	 *            请求控制
	 * @param year
	 *            年度
	 * @param isOverMillion
	 *            是否超过百万
	 * @return 指定年度内的符合条件(是否超过百万)的料件自定义统计报表
	 */
	public List findImgOrgUseInfos(Request request, String year,
			Boolean isOverMillion) {
		return this.imgOrgUseInfoLogic.findImgOrgUseInfos(year, isOverMillion);
	}

	/**
	 * 获得海关资料(原材料统计报表)
	 * 
	 * @param request
	 *            请求控制
	 * @param year
	 *            年度
	 * @param isOverMillion
	 *            是否超过百万
	 * @return 指定年度内的符合条件(是否超过百万)的料件统计报表
	 */
	public List findImgOrgUseInfoCustoms(Request request, String year,
			Boolean isOverMillion) {
		return this.imgOrgUseInfoLogic.findImgOrgUseInfoCustoms(year,
				isOverMillion);
	}

	/**
	 * 成品统计报表(自定义)
	 * 
	 * @param request
	 *            请求控制
	 * @param year
	 *            年度
	 * @param isOverMillion
	 *            是否超过百万
	 * @return 统计指定的年度的成品资料(自定义)
	 */
	public List findExgExportInfos(Request request, String year,
			Boolean isOverMillion) {
		return this.exgExportInfoLogic.findExgExportInfos(year, isOverMillion);
	}

	/**
	 * 成品统计报表(海关)
	 * 
	 * @param request
	 *            请求控制
	 * @param year
	 *            年度
	 * @param isOverMillion
	 *            是否超过百万
	 * @return 统计指定的年度的成品资料(报关)
	 */
	public List findExgExportInfoCustoms(Request request, String year,
			Boolean isOverMillion) {
		return this.exgExportInfoLogic.findExgExportInfoCustoms(year,
				isOverMillion);
	}

	/**
	 * 保存海关统计报表(成品)
	 * 
	 * @param request
	 *            请求控制
	 * @param base
	 *            加工贸易产品流向情况表
	 * @return 加工贸易产品流向情况表
	 */
	public ExgExportInfoBase saveExgExportInfoBase(Request request,
			ExgExportInfoBase base) {
		this.casDao.saveExgExportInfoBase(base);
		return base;
	}

	/**
	 * 重新载入海关资料(料件来自自定资料)
	 * 
	 * @param request
	 *            请求控制
	 */
	public void copyImgOrgUseInfoCustom(Request request) {
		this.imgOrgUseInfoLogic.copyImgOrgUseInfoCustom();
	}

	/**
	 * 重新载入海关资料(料件来自自定资料)成品
	 * 
	 * @param request
	 *            请求控制
	 */
	public void copyExgExportInfoCustom(Request request) {
		this.exgExportInfoLogic.copyExgExportInfoCustom();
	}

	/**
	 * 统计来自报关单的金额(出口成品) 成品出口 + 转厂出口 + 返工复出 + 一般贸易出口 - 退厂返工
	 * 
	 * @param request
	 *            请求控制
	 * @param isCustoms
	 *            是否是报关用的
	 * @return 报关单金额(出口成品)
	 */
	public List<ExgExportInfoBase> saveMoneyFromApplyToCustomsByProduct(
			Request request, Date beginDate, Date endDate, boolean isCustoms) {
		return this.exgExportInfoLogic.sumMoneyFromApplyToCustomsByProduct(
				beginDate, endDate, isCustoms);
	}

	/**
	 * 统计来自报关单的金额(进口料件) 料件进口 + 料件转厂 + 一般贸易进口 - 退料出口
	 * 
	 * @param request
	 *            请求控制
	 * @param isCustoms
	 *            是否是报关用
	 * @return 报关单金额 (进口料件)
	 */
	public List<ImgOrgUseInfoBase> saveMoneyFromApplyToCustomsByMateriel(
			Request request, Date beginDate, Date endDate, boolean isCustoms) {
		return this.imgOrgUseInfoLogic.sumMoneyFromApplyToCustomsByMateriel(
				beginDate, endDate, isCustoms);
	}

	/**
	 * 设备计算
	 * 
	 * @param request
	 *            请求控制
	 * @param conditions
	 *            conditions中包括对单据生效日期的过滤
	 * @return 设备单据
	 */
	public List calBillFixing(Request request, List<Condition> conditions) {
		return this.fixingThingLogic.findBillFixing(conditions);
	}

	/**
	 * 重新载入海关资料 设备
	 * 
	 * @param request
	 *            请求控制
	 */
	public void copyBillFixingThing(Request request) {
		this.fixingThingLogic.copyBillFixingThing();
	}

	/**
	 * 设备统计报表(自定义)
	 * 
	 * @param request
	 *            请求控制
	 * @param isOverMillion
	 *            是否超过百万
	 * @return 所有设备的内容
	 */
	public List findBillFixing(Request request, Boolean isOverMillion) {
		return this.fixingThingLogic.findBillFixing(isOverMillion);
	}

	/**
	 * 设备统计报表(海关)
	 * 
	 * @param request
	 *            请求控制
	 * @param isOverMillion
	 *            是否超过百万
	 * @return 海关用的设备资料
	 */
	public List findBillFixingThing(Request request, Boolean isOverMillion) {
		return this.fixingThingLogic.findBillFixingThing(isOverMillion);
	}

	/**
	 * 保存单据对应的物料的单价
	 * 
	 * @param request
	 *            请求控制
	 * @param billDetail
	 *            单据明细
	 */
	public void saveOneBillPrice(Request request, BillDetail billDetail) {
		casDao.saveOneBillPrice(billDetail);
	}

	/**
	 * 保存所有单据中的包含这个物料的单价
	 * 
	 * @param request
	 *            请求控制
	 * @param billDetail
	 *            单据明细
	 * @param materielType
	 *            物料类型
	 */
	public void saveAllBillPrice(Request request, BillDetail billDetail,
			String materielType) {
		casDao.saveAllBillPrice(billDetail, materielType);
	}

	/**
	 * 按单据临时列表的单据表头的单据类型查找单据明细
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            临时单据表头
	 * @param mostlyBillTypeName
	 *            接近单据类型名称
	 * @return 单据表头的单据类型查找到的单据明细
	 */
	public List findBillDetailByBillTypeName(Request request, List list,
			String mostlyBillTypeName) {
		return casBillDao
				.findBillDetailByBillTypeName(list, mostlyBillTypeName);
	}

	/**
	 * 保存由半成品转成料件(委外加工进库单据--->委外加工返回料件单据) 1103 == 委外加工返回料件单据 4003 == 委外加工进库单据
	 * 
	 * @param request
	 *            请求控制
	 */
	public void save1013By4003(Request request) {
		this.casSpecificontrolLogic.mark1013By4003();
	}

	/**
	 * 保存由半成品转成料件(委外加工出库单据--->车间领用单据) 1101 == 车间领用单据 4103 == 委外加工出库单据
	 * 
	 * @param request
	 *            请求控制
	 */
	public void save1110By4103(Request request) {
		this.casSpecificontrolLogic.make1110By4103();
	}

	public void save1101By4105(Request request) {
		this.casSpecificontrolLogic.mark1101By4105();
	}

	public void save1112By4104(Request request) {
		this.casSpecificontrolLogic.mark1112By4104();
	}

	/**
	 * 删除资料主表
	 * 
	 * @param request
	 *            请求控制
	 * @param selectedRows
	 *            选中的大类内容
	 */
	public void deleteStatCusNameRelation(Request request,
			List<TempStatCusNameRelation> selectedRows) {
		this.casLogic.deleteStatCusNameRelation(selectedRows);
	}

	/**
	 * 保存加工贸易原材料来源与使用情况表
	 * 
	 * @param request
	 *            请求控制
	 * @param base
	 *            加工贸易原材料来源与使用情况表
	 * @return 加工贸易原材料来源与使用情况表
	 */
	public ImgOrgUseInfoBase saveImgOrgUseInfoBase(Request request,
			ImgOrgUseInfoBase base) {
		this.casDao.saveImgOrgUseInfoBase(base);
		return base;
	}

	/**
	 * 跟据料号和类型获得相应的折算系数
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @param ptNo
	 *            料号
	 * @return 折算系数
	 */
	public Double getUnitConvertByPtNo(Request request, String materielType,
			String ptNo) {
		return this.casDao.getUnitConvertByPtNo(materielType, ptNo);
	}

	/**
	 * 获得提示信息
	 * 
	 * @return 生成单据报关数量提示信息
	 */
	public String getMakeBillHsAmountTipMessage() {
		return this.casSpecificontrolLogic.getMakeBillHsAmountTipMessage();
	}

	/**
	 * 委外进出仓帐查询
	 * 
	 * @param request
	 *            请求控制
	 * @param conditions
	 *            查询条件
	 * @return 委外的进出口商品信息
	 */
	public List findConsignQuery(Request request, List<Condition> conditions) {
		return this.casLogic.findConsignQuery(conditions);
	}

	/**
	 * 委外进出仓帐查询
	 * 
	 * @param request
	 *            请求控制
	 * @param conditions
	 *            查询条件
	 * @return 委外的进出口商品信息
	 */
	public List findConsignQueryInfo(Request request,
			ConsignQueryCondition condition) {
		return this.casLogic.findConsignQueryInfo(condition);
	}

	/**
	 * 显示单据的个数
	 * 
	 * @param materielType
	 *            物料类别
	 * @return 与物料类别匹配的单据个数
	 */
	public Integer findBillDetailCount(String materielType) {
		return this.casBillDao.findBillDetailCount(materielType);
	}

	/**
	 * 生成单据的折算报关数量(未折算报关数量的单据)
	 * 
	 * @param request
	 *            请求控制
	 * @param setupParameters
	 *            生成单据的折算报关数量(未折算报关数量的单据)
	 * @return 所有的生成单据的折算报关单的数量
	 */
	public synchronized String makeBillHsAmountByBatch(Request request,
			TempMaterielTypeSetup setupParameters) {
		return this.casSpecificontrolLogic
				.makeBillHsAmountByBatch(setupParameters);
	}

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
			Date beginDate, Date endDate, String ptNo, ScmCoc scmCoc, String nameStyle) {
		return this.casBillDao.findBillInfo(materielType, beginDate, endDate,
				ptNo, scmCoc, nameStyle);
	}

	/**
	 * 批量修改料号对应的报关商品
	 * 
	 * @param list
	 * @param map
	 * @return
	 */
	public List BatchUpdateHs(Request request, List<BillDetail> list,
			String taskId, Map<String, Object> map, String materielType) {
		return this.casBillLogic.BatchUpdateHs(list, taskId, map, materielType);
	}

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
	public List findHsByPtNo(Request request, String ptNos, int index,
			int length, String property, Object value, boolean isLike) {
		return this.casBillDao.findHsByPtNo(ptNos, index, length, property,
				value, isLike);
	}

	/**
	 * 获得边角料所有的数据
	 * 
	 * @param request
	 *            请求控制
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
	public List<LeftoverMateriel> findLeftoverMateriel(Request request,
			int index, int length, String property, Object value, boolean isLike) {
		return this.casLeftoverMaterielDao.findLeftoverMateriel(index, length,
				property, value, isLike);
	}

	/**
	 * 获得边角料所对应的料件数据
	 * 
	 * @param request
	 *            请求控制
	 * @param leftoverMateriel
	 *            边角料
	 * @return 边角料所对应的料件的资料
	 */
	public List<LeftoverMateriel> findLMateriel(Request request,
			LeftoverMateriel leftoverMateriel) {
		return this.casLeftoverMaterielDao.findLMateriel(leftoverMateriel);
	}

	/**
	 * 保存边角料查询报表内容
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            边角料查询报表
	 * @return 边角料查询报表
	 */
	public LeftoverMaterielBalanceInfo saveLeftoverMaterielBalanceInfo(
			Request request, LeftoverMaterielBalanceInfo obj) {
		this.casLeftoverMaterielDao.saveLeftoverMaterielBalanceInfo(obj);
		return obj;
	}

	/**
	 * 获得边角料查询报表内容
	 * 
	 * @param request
	 *            请求控制
	 * @return 边角料查询报表
	 */
	public List<LeftoverMaterielBalanceInfo> findLeftoverMaterielBalanceInfo(
			Request request) {
		return this.casLeftoverMaterielDao.findLeftoverMaterielBalanceInfo();
	}

	/**
	 * 查找并计算边角料(按报关名称分时间段进行查询)
	 * 
	 * @param request
	 *            请求控制
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param projectTypeParam
	 *            模块类型参数(是来自bcs还是bcus......)
	 * @return 边角料计算结果
	 */
	public List<LeftoverMaterielBalanceInfo> findCalLeftoverMateriel(
			Request request, Date beginDate, Date endDate,
			ProjectTypeParam projectTypeParam, boolean isCalcProductToWaste,
			String taskId) {
		return this.casLeftoverMaterielLogic.findCalLeftoverMateriel(beginDate,
				endDate, projectTypeParam, isCalcProductToWaste, taskId);
	}

	/**
	 * 查找并计算短溢表(按报关名称分时间段进行查询)
	 * 
	 * @param request
	 *            请求控制
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param projectTypeParam
	 *            模块类型参数(是来自bcs还是bcus......)
	 * @return 短溢表计算结果
	 */
	public List<BalanceInfo> findBalanceInfo(Request request, Date beginDate,
			Date endDate, ProjectTypeParam projectTypeParam, String taskId,
			boolean isFromCheckData) {
		return this.balanceInfoLogic.findBalanceInfo(beginDate, endDate,
				projectTypeParam, taskId, isFromCheckData);
	}
	/**
	 * 查找并计算短溢表(按报关名称分时间段进行查询)
	 * 
	 * @param request发送请求
	 * @param beginDate开始日期
	 * @param endDate 结束日期
	 * @param projectTypeParam 手册类型
	 * @param isFromCheckData 库存数据是否来源于盘点导入
	 * @param isAll 是否体现合同中所有料件的短溢情况
	 * @return 短溢表计算结果BalanceInfo
	 * @author wss2010.10.27 测试版本
	 */
	public List<BalanceInfo> findBalanceInfoTest(Request request, Date beginDate,
			Date endDate, ProjectTypeParam projectTypeParam, String taskId,
			boolean isFromCheckData,boolean isAll, boolean useNewContract){
		return this.balanceInfoLogic.findBalanceInfoTest(beginDate, endDate,
				projectTypeParam, taskId, isFromCheckData,isAll, useNewContract);
//		return null;
	}


	/**
	 * 查询短溢表信息
	 * 
	 * @param request
	 *            请求控制
	 * @return 工厂短溢表
	 */
	public List<BalanceInfo> findBalanceInfo(Request request) {
		return this.casLeftoverMaterielDao.findBalanceInfo();
	}

	/**
	 * 是否存在单据号
	 */
	public boolean isExistBillByBillNo(Request request, BillMaster billMaster) {
		return this.casBillDao.isExistBillByBillNo(billMaster);
	}

	/**
	 * 获得海关帐的计算中间信息
	 * 
	 * @param imgOrgUseInfo
	 * @return
	 */
	public List<TempImgOrgUseInfo> findTempImgOrgUseInfo(Request request,
			Date beginDate, Date endDate, ImgOrgUseInfoBase imgOrgUseInfo,
			String taskId) {
		return this.imgOrgUseMiddleInfoLogic.findTempImgOrgUseInfo(beginDate,
				endDate, imgOrgUseInfo, taskId);
	}

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
	public List findMakeBillCorrespondingInfo(Request request,
			Integer impExpType, ScmCoc scmCoc, Date beginData, Date endData,
			String nameSpec,Boolean isNameSpec) {
		return this.casSpecificontrolLogic.findMakeBillCorrespondingInfo(
				impExpType, scmCoc, beginData, endData, nameSpec,isNameSpec);
	}

	/**
	 * 查询已对应的单据与报关单信息
	 * 
	 * @param temp
	 * @return
	 */
	public List findMakeBillCorrespondingInfo(Request request,
			TempBillCorrespondingSearch temp) {
		return this.casSpecificontrolLogic.findMakeBillCorrespondingInfo(temp);
	}

	public void executeBillImport(Request request, List headList,
			List detailList, Hashtable hs) throws SQLException {
		this.casBillLogic.executeBillImport(headList, detailList, hs);
	}
	/**
	 * 保存转厂单据进出货单据导入
	 * @param request
	 * @param headList
	 * @param detailList
	 * @param hs
	 * @throws SQLException
	 */
	public void executeTransBillImport(Request request, List headList,
			List detailList,Hashtable hs) throws SQLException {
		this.casBillLogic.executeTransBillImport(headList, detailList,hs);
	}
	
	/**
	 * 检查转厂单据中进出货单据导入的手册号与备案号
	 * 
	 * @param customsEnvelopBillNo
	 * @param emsNo
	 * @param seqNum
	 * @return
	 */
	public List checkCustomsEnvelopCommodityInfoIsExistsEmsNoOrSeqNum(
			String customsEnvelopBillNo, String emsNo, Integer seqNum){
		return this.casBillDao.checkCustomsEnvelopCommodityInfoIsExistsEmsNoOrSeqNum( customsEnvelopBillNo,  emsNo,  seqNum);
	}
	public void executeTransBillImport(Request request, List headList,
			List detailList) throws SQLException {
		this.casBillLogic.executeTransBillImport(headList, detailList);
	}
	
	public List findBillDetailByMaterielType(Request request, String proNo,
			String materielType, Date begin, Date end, ScmCoc scmcoc) {
		return this.casLogic.findBillDetailByMaterielType(proNo, materielType,
				begin, end, scmcoc);
	}

	/**
	 * 从单据类型表里获取物料代码和物料的工厂名称
	 * 
	 * @return 用物料代码做key对应物料工厂名称的hashtable
	 */
	public Hashtable findCodeAndFactryNameFromBillType(Request request) {
		return this.casDao.findCodeAndFactryNameFromBillType();
	}
	/**
	 * 从单据类型表里获取物料代码和物料的工厂名称
	 * 
	 * @return 用物料代码做key对应物料工厂名称的hashtable
	 */
	/**
	 * 单据类型名称 与 单据类型代码 关联
	 */
	public Hashtable findCodeAndFactryNameByName(Request request,String typename)  {
		return this.casDao.findCodeAndFactryNameByName(typename);
	}
	/**
	 * 检查单据导入期初单时,报关资料是否在对应关系存在
	 * 
	 * @param materielType
	 * @return
	 */
	public Map<String, Object[]> findHsKeyForBill(Request requeset,
			String materielType) {
		return this.casBillDao.findHsKeyForBill(materielType);
	}

	public Double getPtAmountByproductNo(Request request,
			BillDetail billDetail, List billTypes, String productNo) {
		return this.casBillDao.getPtAmountByproductNo(billDetail, billTypes,
				productNo);
	}

	/**
	 * 返回一个用物料料号作key对应从数据库抓出的物料信息的hashMap
	 * 
	 * @param typename
	 * @return hashMap
	 */
	public Map<String, Object[]> findptNoandCodeInStatCusNameRelation(
			Request request, String typename) {
		return this.casBillLogic.findPtNoAndCodeInStatCusNameRelation(typename);
	}
	
	/**
	 * 返回一个用物料料号作key对应从数据库抓出的物料信息的hashMap
	 * (针对半成品，从报关常用工厂物料中抓取资料)
	 * 
	 * @param typename
	 * @return hashMap
	 * @author wss2010.09.28
	 */
	public Map<String, Object[]> fintPtNoAndCodeInMateriel(
			Request request, String typename){
		return this.casBillLogic.fintPtNoAndCodeInMateriel(typename);
	}
	

	/**
	 * 查找唯一的 FactoryAndFactualCustomsRalation 名称规格单位
	 * 
	 * @param typeName】
	 * @param isEmsNo 是否要加上 手册号
	 * @return key = value = 料号+ "/" + 报关名称+"/" + 报关规格+"/" + 报关单位
	 */
	public Map<String, String> findDistinctFFCByPtNoNameSpecUnit(
			Request request, String typeName,Boolean isEmsNo) {
		return this.casBillLogic.findDistinctFFCByPtNoNameSpecUnit(typeName,isEmsNo);
	}
	/**
	 * 查找唯一的 Mateiel 名称规格单位
	 * 
	 * @param typeName
	 * @return key = value = 料号+ "/" + 报关名称+"/" + 报关规格+"/" + 报关单位
	 * @author wss2010.09.28
	 */
	public Map<String, String> findDistinctMaterielByPtNoNameSpecUnit(
			Request request, String typeName){
		return this.casBillLogic.findDistinctMaterielByPtNoNameSpecUnit(typeName);
	}
	

	public BillMaster findBillMasterByBillTypeAndBillNo(Request request,
			BillType billType, String billNo) {
		return this.casDao.findBillMasterByBillTypeAndBillNo(billType, billNo);
	}

	public boolean makeAllContractImgExgToFactualCustoms(Request request,
			String materielType, int projectType, boolean isNeedSeqNum) {
		return makeCorrespondenceLogic.makeAllContractImgExgToFactualCustoms(
				materielType, projectType, isNeedSeqNum);
	}

	/**
	 * 处理归并资料设备的导入
	 * 
	 * @param request
	 * @param projectType
	 * @return
	 */
	public boolean makeAllInnerFixtureToFactualCustoms(Request request,
			int projectType) {
		return makeCorrespondenceLogic
				.makeAllInnerFixtureToFactualCustoms(projectType);
	}

	/**
	 * 保存发票单据导入数据
	 * 
	 * @param headList
	 * @param detailList
	 * @param taskId
	 * @throws SQLException
	 * @throws SQLException
	 * @throws SQLException
	 */
	public void importDataToCasInvoice(Request request, List headList,
			List detailList, String taskId) throws SQLException {
		this.casBillLogic.importDataToCasInvoice(headList, detailList, taskId);
	}

	public boolean makeAllFactoryAndFactualCustomsRalation(Request request,
			String materielType, int projectType) {
		return makeCorrespondenceLogic.makeAllFactoryAndFactualCustomsRalation(
				materielType, projectType);
	}

	public List findFactoryAndFactualCustomsRalation(Request request,
			String materielType) {
		return this.casDao.findFactoryAndFactualCustomsRalation(materielType);
	}

	public List findFactualCustoms(Request request, String materielType) {
		return this.casDao.findFactualCustoms(materielType);
	}

	public void deleteFactoryAndFactualCustomsRalation(Request request,
			FactoryAndFactualCustomsRalation ffc) {
		casDao.delete(ffc);
	}

	public void deleteFactoryAndFactualCustomsRalation(Request request,
			List ffcs) {
		for (int i = 0; i < ffcs.size(); i++) {
			casDao.delete(ffcs.get(i));
		}
	}

	public void saveFactoryAndFactualCustomsRalation(Request request,
			FactoryAndFactualCustomsRalation ffc, String mType,
			Boolean isUpdateMateriel,Boolean isUpdateHsNum,String emsNo) {
		this.casLogic.saveFactoryAndFactualCustomsRalation(ffc, mType,
				isUpdateMateriel,isUpdateHsNum,emsNo);
	}
	
	/**
	 * 单据中心右键 修改企业物料
	 * 
	 * @param ffc 修改后的对应关系
	 * @param materielType  对应关系 物料类型
	 * @param isUpdateDetail  是否更新单据
	 */
	public void modifyMaterielForFactoryAndFactualCustomsRalation(
			Request rquest,
			FactoryAndFactualCustomsRalation ffc, 
			String materielType,
			String updateType){
		casLogic.modifyMaterielForFactoryAndFactualCustomsRalation(
				 ffc, 
				 materielType,
				 updateType);
	}
	
	
	/**
	 * 单据中心右键 修改企业物料（品基）
	 * 
	 * @param ffc 修改后的对应关系
	 * @param materielType  对应关系 物料类型
	 */
	public int modifyMaterielForFactoryAndFactualCustomsRalationPJ(
			Request rquest,
			String materielType){
		return casLogic.modifyMaterielForFactoryAndFactualCustomsRalationPJ(
				 materielType);
	}
	
	/**
	 * 单据中心 更新报关资料
	 * @param ffc 新的对应关系
	 * @param materielType 对应关系物料类型
	 * @param isUpdateDetail 是否更新单据
	 * @param isNoChangeHsCode  没有变更报关编码
	 * @param ooMParameter 最旧的物料资料
	 * @param ooCusParameter 最旧的报关资料
	 * @param oCusParameter 变更的报关编码原先的属性
	 *
	 * @author wss:2010.06.25
	 */
	public void modifyCustomForFactoryAndFactualCustomsRalation(
					Request request,
					FactoryAndFactualCustomsRalation ffc, 
					String materielType,
					Boolean isUpdateDetail,
					Boolean isChangeHsCode,
					String[] oldMOldParameter,
					String[] oldCusOldParameter,
					String[] newCusOldParameter){
		casLogic.modifyCustomForFactoryAndFactualCustomsRalation(
				 ffc, 
				 materielType,
				 isUpdateDetail,
				 isChangeHsCode,
				 oldMOldParameter,
				 oldCusOldParameter,
				 newCusOldParameter);
		
	}

	public void saveFactoryAndFactualCustomsRalation(Request request,Boolean isWriteBackM,
			FactoryAndFactualCustomsRalation ffc) {
		this.casLogic.saveFactoryAndFactualCustomsRalation(isWriteBackM, ffc);
	}

	public List addFactoryAndFactualCustomsRalation(List<BcsInnerMerge> list) {
		return this.casBillLogic.addFactoryAndFactualCustomsRalation(list);
	}

//	public List findImpExpInfosGetExpDj(Request request, String materialType,
//			List<Condition> conditions, List<Condition> conditions1,
//			Boolean isSplitBomVersion) {
//		return casLogic.findImpExpInfosGetExpDj(materialType, conditions,
//				conditions1, isSplitBomVersion);
//	}

	/**
	 * 国内购买原材料统计明细表
	 */
	public List findChinBuyMaterielReport(Request request, String emsNo,
			ScmCoc scmCoc, String hsName) {
		return casLogic.findChinBuyMaterielReport(emsNo, scmCoc, hsName);
	}

	public List findCasInvoiceInfo(Request request, String emsNo,
			ScmCoc scmcoc, String hsName) {
		return this.casDao.findCasInvoiceInfo(emsNo, scmcoc, hsName);
	}

	public List findCasInvoiceInfo(Request request, String emsNo,
			ScmCoc scmcoc, String hsName, Date beginDate, Date endDate,
			boolean isCancel) {
		return this.casDao.findCasInvoiceInfo(emsNo, scmcoc, hsName, beginDate,
				endDate, isCancel);
	}

	public List findCasInvoiceInfoCancel(Request request, String emsNo,
			ScmCoc scmcoc, String hsName) {
		return this.casDao.findCasInvoiceInfoCancel(emsNo, scmcoc, hsName);
	}

	public List invoiceCancel(Request request, List ls, BaseEmsHead head) {
		return this.casLogic.invoiceCancel(ls, head);
	}

	public List invoiceRCancel(Request request, List ls, BaseEmsHead head) {
		return this.casLogic.invoiceRCancel(ls, head);
	}

	public List findCasInvoice(Request request) {
		return this.casDao.findCasInvoice();
	}

	public List findAllEmsHeadH2k(Request request) {
		return this.casDao.findAllEmsHeadH2k();
	}

	public List findDistinctContract(Request request) {
		return this.casDao.findDistinctContract();
	}

	public List findDistinctEmsNo(Request request) {
		return this.casDao.findDistinctEmsNo();
	}

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
			ScmCoc scmcoc, String hsName) {
		return this.casLogic.findInvoiceAndBillCorresponding(emsNo, scmcoc,
				hsName);
	}

	/**
	 * 发货与发票开票关系列表
	 * 
	 * @param emsNo
	 * @param scmcoc
	 * @param hsName
	 * @return
	 */
	public List findSumBillAndInvoice(Request request, String emsNo,
			ScmCoc scmcoc, String hsName) {
		return this.casLogic.findSumBillAndInvoice(emsNo, scmcoc, hsName);
	}

	/**
	 * 检查导入文件数据的正确性
	 * 
	 * @param request
	 * @param list
	 * @param ht
	 * @return
	 */
	public List checkImportData(Request request, List list, Hashtable ht) {
		return casLogic.checkFileData(list, ht, request.getTaskId());
	}

	/**
	 * 检查导入文件数据的正确性（盘点平衡表）
	 * 
	 * @param request
	 * @param list
	 * @param ht
	 * @return
	 */
	public List checkFileDataForCheckBalance(Request request, List list,
			Hashtable ht) {
		return casLogic.checkFileDataForCheckBalance(list, ht, request
				.getTaskId());
	}

	/**
	 * 查找盘点平衡表中所有数据
	 * 
	 * @param request
	 * @return
	 */
	public List findCheckBalance(Request request) {
		return casDao.findCheckBalanceConvertMateriel(null, null);
	}

	/**
	 * 批量保存盘点平衡表导入数据
	 * 
	 * @param request
	 * @param list
	 */
	public List batchSaveCheckBalance(Request request, List<CheckBalance> list) {
		casDao.batchSaveOrUpdate(list);
		return list;
	}

	/**
	 * 保存盘点平衡表
	 * 
	 * @param checkBalance
	 *            单条盘点平衡表数据
	 */
	public void saveCheckBalance(Request request, CheckBalance checkBalance) {
		casDao.saveCheckBalance(checkBalance);
	}

	/**
	 * 删除盘点平衡表一条记录
	 * 
	 * @param list<CheckBalance>
	 */
	public void deleteCheckBalance(Request request, List<CheckBalance> ls) {
		casLogic.deleteCheckBalance(ls);
	}
	
	/**
	 * 删除相应条件下的条件下的盘点平衡表
	 * @param List<CheckBalance>
	 * @author wss2010.10.18
	 */
	public void deleteAllCheckBalance(Request request, 
			Date beginDate,Date endDate,String kcType,String ptNo,String wlType,List<String> lsWareSetCodes){
		this.casLogic.deleteAllCheckBalance(beginDate, endDate, kcType, ptNo, wlType,lsWareSetCodes);
	}
	
	/**
	 * 删除相应条件下的条件下的盘点平衡表
	 * @param List<CheckBalanceConvertMateriel>
	 * @author wss2010.10.18
	 */
	public void deleteAllCheckBalanceConvertMateriel(Request request, 
			Date beginDate,Date endDate,String kcType,String ptNo,String wlType,List<String> lsWareSetCodes){
		this.casLogic.deleteAllCheckBalanceConvertMateriel(beginDate, endDate, kcType, ptNo, wlType,lsWareSetCodes);
	}
	
	/**
	 * 删除折算后的盘点
	 * @param List<Conditions>
	 * @author wss
	 */
	public void deleteCheckBalanceConvertMateriel(Request request,List<CheckBalanceConvertMateriel> ls){
		casDao.deleteAll(ls);
	}
	
	/**
	 * 删除盘点平衡表一条记录
	 * 
	 * @param checkBalance
	 */
	public void deleteCheckBalanceOneByOne(Request request, CheckBalance checkBalance) {
		casLogic.deleteCheckBalance(checkBalance);
	}

	/**
	 * 删除盘点平衡表折料一条记录
	 * 
	 * @param checkBalanceConvertMateriel
	 * @author wss
	 */
	public void deleteCheckBalanceConvertMateriel(Request request, CheckBalanceConvertMateriel ccm){
		this.casDao.delete(ccm);
	}
	
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
			int length, String property, Object value, boolean isLike) {
		return this.casDao.findCheckBalance(index, length, property, value,
				isLike);
	}
	
	
//	/**
//	 * 分页查询盘点平衡表bom
//	 * 
//	 * @param index
//	 *            数据的开始下标
//	 * @param length
//	 *            数据长度
//	 * @param property
//	 *            属性
//	 * @param value
//	 *            属性的值
//	 * @param isLike
//	 *            查找时用“Like”还是“＝”
//	 * @return
//	 * 
//	 * @author wss2010.07.19
//	 */
//	public List<CheckBalance> findCheckBalanceBom(Request request, int index,
//			int length, String property, Object value, boolean isLike) {
//		return this.casDao.findCheckBalanceBom(index, length, property, value,
//				isLike);
//	}

	/**
	 * 自动获取对应的报关资料并计算，然后再保存在盘点平衡表中
	 * 
	 * @return
	 */
	public List calculateCheckBalance(Request request,List list,String taskId) {
		return casLogic.calculateCheckBalance(list,taskId);

	}
	
	/**
	 * 自动获取对应的报关资料并计算，然后再保存在盘点平衡表中 one by one
	 * @return
	 * @author wss
	 */
	public void fillCheckBalanceHsInfoOneByOne(Request request,CheckBalance cb) {
		casLogic.fillCheckBalanceHsInfoOneByOne(cb);
	}

	/**
	 * 自动获取对应的报关资料并计算，然后再保存在盘点平衡表中
	 * 
	 * @return
	 * @author wss
	 */
	public List calculateCheckBalanceConvertMateriel(Request request,List list) {
		return casLogic.calculateCheckBalanceConvertMateriel(list);
	}
	
	/**
	 * 自动获取对应的报关资料并计算，然后再保存在盘点平衡表中 One by One
	 * 
	 * @return
	 * @author wss
	 */
	public CheckBalanceConvertMateriel calculateCheckBalanceConvertMaterielOneByOne(Request request,CheckBalanceConvertMateriel ccm) {
		return casLogic.fillAndSaveCheckBalanceConvertMaterielOneByOne(ccm);
	}
	
	
	/**
	 * 查找短溢表中存在的名称/规格/单位,用于数据导入时检查,避免重复导入
	 * 
	 * @return
	 */
	public List findBalanceInfoNameSpecUnitName(Request request) {
		return casDao.findBalanceInfoNameSpecUnitName();
	}

	public List findImgFromBaseEmsHead(Request request, BaseEmsHead data,
			CasInvoice head) {
		return casLogic.findImgFromBaseEmsHead(data, head);
	}

	public List findImgFromBaseEmsHead(Request request, BaseEmsHead data) {
		return casDao.findFromBaseEmsHeadImg(data);
	}

	public Object savaOrUpdateObject(Request request, Object obj) {
		casDao.saveOrUpdate(obj);
		return obj;
	}

	public List findCasInvoiceInfo(Request request, CasInvoice head,
			Boolean canceled) {
		return casDao.findCasInvoiceInfo(head, canceled);
	}

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
			ScmCoc scmcoc, String hsName) {
		return casLogic.SumInvoiceAndCancel(emsNo, scmcoc, hsName);
	}

	public List findCasInvoiceByNo(String no) {
		return casDao.findCasInvoiceByNo(no);
	}

	public List findBillDetailMaterielByNameAndScmCoc(Request request,
			String id, String name, ScmCoc scmCoc) {
		return casDao.findBillDetailMaterielByNameAndScmCoc(id, name, scmCoc);
	}

	public List<BalanceInfo2> findBalanceInfo2(Request request, Date beginDate,
			Date endDate, ProjectTypeParam projectTypeParam, String taskId,
			boolean isUpdateData, boolean isFromCheckData) {
		return this.balanceInfoLogic2.findBalanceInfo2(beginDate, endDate,
				projectTypeParam, taskId, isUpdateData, isFromCheckData);
	}

	public List<BalanceInfo2> findBalanceInfo2(Request request) {
		return this.casLeftoverMaterielDao.findBalanceInfo2();
	}

	public List makeCasInvoiceAndBillRalation(Request request,
			CasInvoiceInfo invoice, List<BillDetail> details) {
		return this.casBillLogic
				.makeCasInvoiceAndBillRalation(invoice, details);
	}

	public List findInvoiceAndBillCorrespondingByCasInvoiceInfo(
			Request request, CasInvoiceInfo info) {
		return this.casDao
				.findInvoiceAndBillCorrespondingByCasInvoiceInfo(info);
	}

	public List findInvoiceAndBillCorrespondingCusName(Request request,
			ScmCoc p1) {
		return this.casDao.findInvoiceAndBillCorrespondingCusName(p1);
	}

	public List findInvoiceInfoCusName(Request request, ScmCoc p1) {
		return this.casDao.findInvoiceInfoCusName(p1);
	}

	/**
	 * BalanceInfo
	 * 
	 * @param obj
	 */
	public BalanceInfo2 saveBalanceInfo2(Request request, BalanceInfo2 obj) {
		this.casLeftoverMaterielDao.saveBalanceInfo2(obj);
		return obj;
	}

	public void deleteOject(Request request, Object obj) {
		this.casDao.delete(obj);
	}

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
			String stateCode, Date beginDate, Date endDate) {
		return fixingThingLogic.getFixtureNotInTaxationReport(stateCode,
				beginDate, endDate);
	}

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
			Date endDate) {
		return fixingThingLogic.getFixtureInTaxationReport(beginDate, endDate);
	}

	// /**
	// * 根据名称查询单位
	// *
	// * @param hsName
	// * @return
	// */
	// public Unit findUnitByHsName(Request request, String hsName) {
	// return this.transferInfoLogic.findUnitByHsName(hsName);
	// }
	/**
	 * 查询转厂收／送货商品名称
	 * 
	 * @return
	 */
	public List findTransFactRecvSendCommName(Request request,
			boolean isImport, ScmCoc scmCoc, Date beginDate, Date endDate) {
		return this.transferInfoLogic.findTransFactRecvSendCommName(isImport,
				scmCoc, null, beginDate, endDate);
	}

	/**
	 * 查询转厂收／送货商品名称
	 * 
	 * @return
	 */
	public List findTransFactRecvSendCommName(Request request,
			boolean isImport, ScmCoc scmCoc, String beginMonth, String endMonth) {
		return this.transferInfoLogic.findTransFactRecvSendCommName(isImport,
				scmCoc, beginMonth, endMonth);
	}

	/**
	 * 查询转厂收／送货明细表
	 * 
	 * @return
	 */
	public List findTransFactRecvSendDetailInfo(Request request,
			boolean isImport,boolean isCustomNo, ScmCoc scmCoc, Date beginDate, Date endDate) {
		return this.transferInfoLogic.findTransFactRecvSendDetailInfo(isImport,isCustomNo,
				scmCoc, beginDate, endDate);
	}

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
	public List findTransFactCompareInfoOnDay(Request request, boolean isImg,
			String commName, ScmCoc scmCoc, Date beginDate, Date endDate) {
		return this.transferInfoLogic.findTransFactCompareInfoOnDay(isImg,
				commName, scmCoc, beginDate, endDate);
	}

	/**
	 * 结转对帐表
	 * 
	 * @param isImg
	 * @param commName
	 * @param scmCoc
	 * @param beginMonth
	 * @param endMonth
	 * @param groupCondition 分组条件
	 * @return
	 */
	public List findTransFactCompareInfoOnMonth(Request request, boolean isImg,
			String commName,String commSpec, ScmCoc scmCoc, String beginMonth, String endMonth,List groupCondition) {
		return this.transferInfoLogic.findTransFactCompareInfoOnMonth(isImg,
				commName,commSpec, scmCoc, beginMonth, endMonth,groupCondition);
	}

	/**
	 * 转厂差额总表
	 * 
	 * @param endDate
	 * @return
	 */
	public List findAllTransFactDiffInfo(Request request, boolean isImg,
			boolean isFindByScmcoc, ScmCoc scmCoc, Date endDate) {
		return this.transferInfoLogic.findAllTransFactDiffInfo(isImg,
				isFindByScmcoc, scmCoc, endDate);
	}

	/**
	 * 建立设备的对应关系
	 * 
	 * @param projectType
	 * @return
	 */
	public boolean makeAllFixFactoryAndFactualCustomsRalation(Request request,
			int projectType) {
		return makeCorrespondenceLogic
				.makeAllFixFactoryAndFactualCustomsRalation(projectType);
	}

	public boolean deleteCasInvoice(Request request, CasInvoice obj) {
		return this.transferInfoLogic.deleteCasInvoice(obj);
	}

	/**
	 * 查找对应关系
	 * 
	 * @param request
	 * @return
	 */
	public List findFactoryAndFactualCustomsRalationForBill(Request request,
			String materielType, int index, int length, String property,
			Object value, boolean isLike) {
		return this.casDao.findFactoryAndFactualCustomsRalationForBill(
				materielType, index, length, property, value, isLike);
	}

	public List<Double> findFactoryAndFactualCustomsRalationMaterielForNetWeight(
			Request request, String materielType, String MaterielPtNo) {
		return this.casDao
				.findFactoryAndFactualCustomsRalationMaterielForNetWeight(
						materielType, MaterielPtNo);
	}

	/**
	 * 根据名称查找报关单位资料
	 * 
	 * @param unitName
	 *            报关厂单位名称
	 * @return
	 */
	public Unit findUnitByName(Request request, String unitName) {
		return this.casDao.findUnitByName(unitName);
	}

	public List findUnit(Request request) {
		return this.casDao.findUnit();
	}

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
			String emsNo, Integer projectType) {
		return this.casDao.isExitSpecialCustomsContractNo(emsNo, projectType);
	}

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
			int index, int length, String property, Object value, boolean isLike) {
		return casDao.getSpecialCustomsContractNo(emsNo, index, length,
				property, value, isLike);
	}

	/**
	 * 检查导入的数据的正确性，并输出错误提示
	 * 
	 * @param list
	 * @param ht
	 * @param taskId
	 * @param materielType
	 * @param map
	 * @return
	 */
	public List checkImportDataForRelation(Request request, List list,
			int[] ht, String taskId, String materielType,
			Map<String, Boolean> map) {
		List tmp = this.casBillLogic.checkImportDataForRelation(list, ht,
				taskId, materielType, map);
		System.gc();
		return tmp;
	}
	
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
			Map<String, Boolean> map) {
		List tmp = this.casBillLogic.checkImportDataForRelationPJ(list, ht,
				taskId, materielType, map);
		System.gc();
		return tmp;
	}

	/**
	 * 查找出对应关系中一对多的料号
	 * 
	 * @param materielType
	 * @return
	 */
	public List findOneToMorePtNo(Request request, String materielType) {
		return this.casBillDao.findOneToMorePtNo(materielType);
	}

	/**
	 * 批量保存对应关系表
	 * 
	 * @param list
	 *            从文件导入的对应关系数据
	 */
	public List<FactoryAndFactualCustomsRalation>  batchSaveFactoryAndFactualCustomsRalation(Request request,
			List<FactoryAndFactualCustomsRalation> list,  boolean isReWriteMateriel,
			String materielType,boolean isConver,Map map) {
		
		return casBillLogic.batchSaveFactoryAndFactualCustomsRalation(list, isReWriteMateriel,
				materielType,isConver,map, request.getTaskId());
	}

	public void deleteInvoiceAndBillCorresponding(Request request, List list) {
		this.casBillLogic.deleteInvoiceAndBillCorresponding(list);
	}

	/**
	 * 获得在单据中客户供应商 转厂单据
	 * 
	 * @param sBillType
	 *            单据类型
	 * @param billCode
	 *            单据类型下的单据代号
	 */
	public List findScmCocToFpt(Request request, String billCode) {
		return this.casDao.findScmCocToFpt(billCode);
	}

	/**
	 * 获得在单据中商品信息不是全部转为真的单据记录 Tfi == 结转
	 * 
	 * @param request
	 *            请求控制
	 * @param scmCocId
	 *            客户id
	 * @return 单据类型为料件入库的单据中商品信息不是全部转为真的单据记录
	 */
	public List findTempBillMasterIsAvailabilityToFpt(Request request,
			String scmCocId, String envelopNo, String billCode, Date beginDate,
			Date endDate) {
		return this.transferInfoLogic.findTempBillMasterIsAvailabilityToFpt(
				scmCocId, envelopNo, billCode, beginDate, endDate);
	}

	public List findScmcoc(Request request) {
		return this.casDao.findScmcoc();
	}

	/**
	 * 查找国内购买发票号码
	 * 
	 * @param request
	 * @return
	 */
	public List findInvoiceNo(Request request) {
		return this.casDao.findInvoiceNo();
	}

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
	// String scmCocId, String envelopNo) {
	// return this.transferInfoLogic
	// .findTempBillMasterIsAvailabilityToTFBByfe(scmCocId, envelopNo);
	// }
	public List findBaseEmsImg(Request request, BaseEmsHead head) {
		return this.casDao.findBaseEmsImg(head);
	}

	public List invoiceCancel(Request request, List ls, Integer seqNum,
			BaseEmsHead head) {
		return this.casLogic.invoiceCancel(ls, seqNum, head);
	}

	public CasInnerMergeDataOrder findCasInnerMergeDataOrder(Request request) {
		return this.casSpecificontrolDao.findCasInnerMergeDataOrder();
	}

	public CasInnerMergeDataOrder saveCasInnerMergeDataOrder(Request request,
			CasInnerMergeDataOrder order) {
		return this.casSpecificontrolDao.saveCasInnerMergeDataOrder(order);
	}

	public void cpZsuanLj(Request request) {
//		this.casLogic.cpZsuanLj();
		this.casLogic.convertCheckBalance();//wss2010.07.19折算料件
	}

	/**
	 * 查找导入文件列顺序
	 * 
	 * @param tableName
	 * @return
	 */
	public List<ImportDataOrder> findImportDataOrder(Request request,
			String tableName) {
		return this.casDao.findImportDataOrder(tableName);
	}

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
			String taskId, String materielType) {
		return this.casBillLogic.checkImportDataForHsn(list, ht, taskId,
				materielType);
	}

	/**
	 * 保存导入文件列顺序
	 * 
	 * @param list
	 */
	public void batchUpdateDataOrder(Request request, List<ImportDataOrder> list) {
		this.casDao.batchUpdateDataOrder(list);
	}

	/**
	 * 批量保存实际报关资料
	 * 
	 * @param list
	 */
	public void batchUpdateStatCusNameRelationHsn(Request request,
			List<StatCusNameRelationHsn> list) {
		this.casDao.batchUpdateStatCusNameRelationHsn(list);
	}

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
			Object value, boolean isLike, String materielType) {
		return this.casDao.findStatCusNameRelationHsn(index, length, property,
				value, isLike, materielType);
	}

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
			Object value, boolean isLike, String materielType) {
		return this.casDao.findFactoryAndFactualCustomsRalation(index, length,
				property, value, isLike, materielType);
	}

	/**
	 * 删除实际报关资料
	 * 
	 * @param list
	 */
	public void deleteStatCusNameRelationHsns(Request request,
			List<StatCusNameRelationHsn> list) {
		this.casDao.deleteStatCusNameRelationHsns(list);
	}

	/**
	 * 获得对应类型的单据表头
	 * 
	 * @param tablename
	 * @return
	 */
	public List getCasHeadForImport(Request request, String tablename) {
		return this.casBillLogic.getCasHeadForImport(tablename);
	}

	/**
	 * 按照单据导入的设定，返回一个客户HASHMAP
	 * 
	 * @param isName
	 * @return
	 */
	public HashMap getScmCocForImport(Request request, String isName) {
		return this.casBillLogic.getScmCocForImport(isName);
	}

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
			String taskId, Date beginDate, Date endDate) {
		if (isByPtNO) {// 按工厂品名查询
			return this.imgOrgUseInfoLogic.getF25ByPtNo(taskId, beginDate,
					endDate, null);
		} else {// 按报关品名查询
			return this.imgOrgUseInfoLogic.getF25ByHsKey2(taskId, beginDate,
					endDate);
		}
	}

	/**
	 * 改变记账年度时生成成品期初单
	 * 
	 * @param taskId
	 */
	public Map<String, BillInit> getProductF1ForBill(Request request,
			String taskId, Date beginDate, Date endDate) {
		return this.imgOrgUseInfoLogic.getProductF1ForBill(taskId, beginDate,
				endDate);
	}

	/**
	 * 生成单据头与体
	 * 
	 * @param map
	 * @param mMap
	 * @param fMap
	 * @param typeCode
	 */
	public void updateBillByMap(Request request, HashMap map, String typeCode) {
		this.imgOrgUseInfoLogic.updateBillByMap(map, typeCode);
	}

	/**
	 * 改变记账年度是生成料件期初单(按客户)
	 * 
	 * @param taskId
	 * @return
	 */
	public Map<String, Map<String, Map<String, Double>>> getMaterielF24F25ForBillByCustom(
			Request request, String taskId, Date beginDate, Date endDate) {
		return this.imgOrgUseInfoLogic.getMaterielF24F25ForBillByCustom(taskId,
				beginDate, endDate);
	}

	/**
	 * 改变记账年度时生成成品期初单(按客户)
	 * 
	 * @param taskId
	 */
	public Map<String, Map<String, Double>> getProductF1ForBillByCustom(
			Request request, String taskId, Date beginDate, Date endDate) {
		return this.imgOrgUseInfoLogic.getProductF1ForBillByCustom(taskId,
				beginDate, endDate);
	}

	/**
	 * 生成单据头与体(按客户)
	 * 
	 * @param map
	 * @param mMap
	 * @param fMap
	 * @param typeCode
	 */
	public void updateBillByMapForCustom(Request request,
			HashMap<String, Map<String, Double>> map, String typeCode) {
		this.imgOrgUseInfoLogic.updateBillByMapForCustom(map, typeCode);
	}

	/**
	 * 根据表头Id返回单据表头
	 * 
	 * @param id
	 * @param billType
	 * @return
	 */
	public BillMaster findBillMaster(Request request, String id, int billType) {
		return this.casDao.findBillMaster(id, billType);
	}

	public Map getMaterialByType(Request request, String typename) {
		return this.casBillLogic.getMaterialByType(typename);
	}

	/**
	 * 转抄单据
	 * 
	 * @param list
	 * @return
	 */
	public List<BillMaster> copyBillMaster(Request request,
			List<BillMaster> list) {
		return this.casLogic.copyBillMaster(list);
	}

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
			BillMaster billMaster, String propertyName, Object propertyValue) {
		return this.casLogic.saveAllBillDetailByMaster(billMaster,
				propertyName, propertyValue);
	}

	/**
	 * 获得在单据中商品信息不是全部转为真的单据记录 Tfe == 结转出口
	 * 
	 * @param request
	 *            请求控制
	 * @param scmCocId
	 *            客户id
	 * @return 单据类型为成品出入库的单据中商品信息不是全部转为真的单据记录
	 */
	public List findTempBillMasterIsAvailabilityToTFBByTfe(Request request,
			String scmCocId, String envelopNo, Date begin, Date end) {
		return this.transferInfoLogic
				.findTempBillMasterIsAvailabilityToTFBByfe(scmCocId, envelopNo, begin, end);
	}

	/**
	 * 获得在单据中商品信息不是全部转为真的单据记录 Tfi == 结转进口
	 * 
	 * @param request
	 *            请求控制
	 * @param scmCocId
	 *            客户id
	 * @return 单据类型为料件入库的单据中商品信息不是全部转为真的单据记录
	 */
	public List findTempBillMasterIsAvailabilityToTFBByTfi(Request request,
			String scmCocId, String envelopNo, Date begin, Date end) {
		return this.transferInfoLogic
				.findTempBillMasterIsAvailabilityToTFBByTfi(scmCocId, envelopNo, begin, end);
	}

	/**
	 * 生成料件期初,在产品期初,成品期初,边角料期初,残次品期初
	 */
	public void saveBillInit(String taskId,Request request) {
		this.imgOrgUseInfoLogic.saveBillInit(taskId);
	}

	/**
	 * 生成料件期初单据,在产品期初单据,成品期初单据,边角料期初单据,残次品期初单据
	 */
	public void makeBillInit(Request request) {
		this.imgOrgUseInfoLogic.makeBillInit();
	}

	/**
	 * 查找送货转厂耗料情况
	 * 
	 * @param request
	 * @param balanceInfo
	 * @return
	 */
	public List findDeliveryToPlantDzsc(Request request, BalanceInfo balanceInfo,
			Date beginDate,Date endDate,String taskId) {
		return this.balanceInfoLogic.findDeliveryToPlantDzsc(balanceInfo,  beginDate,endDate,taskId);
	}

	/**
	 * 查找送货转厂耗料情况(电子化手册)
	 * 
	 * @param request
	 * @param balanceInfo
	 * @return
	 */
	public List findDeliveryToPlantBcs(Request request,
			BalanceInfo balanceInfo, Date beginDate,Date endDate,String taskId) {
		return this.balanceInfoLogic.findDeliveryToPlantBcs(balanceInfo, beginDate,endDate,taskId);
	}

	/**
	 * 查找BCUS送货转厂耗料情况
	 * 
	 * @param request
	 * @param balanceInfo
	 * @return
	 */
	public List findDeliveryToPlantBcus(Request request, BalanceInfo balanceInfo,
			Date beginDate,Date endDate,String taskId) {
		return this.balanceInfoLogic.findDeliveryToPlantBcus(balanceInfo,  beginDate,endDate,taskId);
	}
	/**
	 * 出料件进仓的工厂总数量
	 * 
	 * @param ptNumber
	 * @param wareSet
	 * @param vaillDate
	 * @return
	 */
	public Double findWarehousesMaterielIn(Request request, String ptNumber,
			WareSet wareSet, Date vaillDate, Boolean isValid) {
		return this.casDao.findWarehousesMaterielIn(ptNumber, wareSet,
				vaillDate, isValid);
	}

	/**
	 * 出料件出仓的工厂总数量
	 * 
	 * @param ptNumber
	 * @param wareSet
	 * @param vaillDate
	 * @return
	 */
	public Double findWarehousesMaterielOut(Request request, String ptNumber,
			WareSet wareSet, Date vaillDate, Boolean isValid) {
		return this.casDao.findWarehousesMaterielOut(ptNumber, wareSet,
				vaillDate, isValid);
	}

	/**
	 * 成品进口的工厂总数量
	 * 
	 * @param ptNumber
	 * @param wareSet
	 * @param vaillDate
	 * @return
	 */
	public Double findWarehousesProduceIn(Request request, String ptNumber,
			WareSet wareSet, Date vaillDate, Boolean isValid) {
		return this.casDao.findWarehousesProduceIn(ptNumber, wareSet,
				vaillDate, isValid);
	}

	/**
	 * 成品出口的工厂总数量
	 * 
	 * @param ptNumber
	 * @param wareSet
	 * @param vaillDate
	 * @return
	 */
	public Double findWarehousesProduceOut(Request request, String ptNumber,
			WareSet wareSet, Date vaillDate, Boolean isValid) {
		return this.casDao.findWarehousesProduceOut(ptNumber, wareSet,
				vaillDate, isValid);
	}

	public List getResultWaiFa(Request request, List<Condition> conditions,
			List<Condition> condition, List<Condition> conditionss) {
		return this.casLogic.getResultWaiFa(conditions, condition, conditionss);
	}

	public List getResultWaiFaBack(Request request, List<Condition> conditions,
			List<Condition> condition, List<Condition> conditionss,
			List<Condition> tiaojian, List<Condition> tiaojian2) {
		return this.casLogic.getResultWaiFaBack(conditions, condition,
				conditionss, tiaojian, tiaojian2);
	}

	public List getResultWaiFaTrusteeInOut(Request request,
			List<Condition> conditions, List<Condition> condition,
			List<Condition> conditionss) {
		return this.casLogic.getResultWaiFaTrusteeInOut(conditions, condition,
				conditionss);
	}

	public List getResultWaiFaTrusteeInOutBack(Request request,
			List<Condition> conditions, List<Condition> condition,
			List<Condition> conditionss, List<Condition> tiaojian,
			List<Condition> tiaojian2) {
		return this.casLogic.getResultWaiFaTrusteeInOutBack(conditions,
				condition, conditionss, tiaojian, tiaojian2);
	}

	


	public List<TempObject> findAllWrokOrder(Request request,
			int index, int length, String property, Object value, boolean isLike) {
		return this.casBillLogic
		.findAllWrokOrder(
				index, length, property, value, isLike);
		}

	public List getCurrentBillDetailCurrDay(Request request,
			List<Condition> conditions, String orderBy, String billDetail,
			Boolean ishsTaotal, Boolean isShowLess, Boolean isGroup) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getCurrentBillDetailOut(Request request,
			List<Condition> conditions, String orderBy, String billDetail,
			Boolean ishsTaotal, Boolean isShowLess, Boolean isGroup) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * wss:(使用原生sql)
	 * 查询  生产设备使用情况表（海关资料统计报表）
	 * @return
	 */
	public List calBillFixingBySql( Request request,
									Date beginDate,
									Date endDate){
		return this.fixingThingLogic.calBillFixingBySql(beginDate,endDate);
	}

	/**
	 * 折算在产品期初单 (将半成品)
	 * @author wss
	 */
	public void convertToInitFromSemiProduct(Request request) {
		this.casLogic.convertToInitFromSemiProduct();
	}
	
	/**
	 * 折算在产品期初单 (将半成品期初单)
	 * @return 当前单据头
	 * @author wss 2010.10.08
	 */
	public BillMaster convertToInitFromSemiProductInit(Request request,BillMaster billMaster){
		return this.casLogic.convertToInitFromSemiProductInit(billMaster);
	}

	/**
	 * 判断是否存在 在产品期初单（半成品折过来的）
	 * @author wss
	 */
	public boolean isExistInitBillMasterMadeBySemiProduct(Request request) {
		// TODO Auto-generated method stub
		return this.casLogic.isExistInitBillMasterMadeBySemiProduct();
	}
	
	/**
	 * 判断当前半成品单据是否已经折算为在产品期初单
	 * @author wss2010.10.08
	 */
	public boolean isAlreadyConvertToInit(Request request,String billNo){
		return this.casLogic.isAlreadyConvertToInit(billNo);
	}
	
	/**
     * 重新加载Hibernate SessionFactory
     */
    public void reloadSessionFactory(Request request){
    	this.casParameterSetLogic.reloadSessionFactory();
    }
    
    
	/**
	 * 返回实际报关的商品名称来自实际报关商品
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
	 * @author wss
	 */
	public List<TempObject> findHsFromFactoryAndFactualCustomsRalation(
			Request request, String materielType, int index, int length,
			String property, Object value, boolean isLike) {
		return this.casBillLogic
				.findHsFromFactoryAndFactualCustomsRalation(materielType,
						index, length, property, value, isLike);
	}

	/**
	 * 查询CheckBalance
	 * @author wss
	 */
	public List findCheckBalance(Request request,List<Condition> conditions) {
		return casLogic.findCheckBalance(conditions);
	}

	/**
	 * 分页查询CheckBalance
	 * @return
	 * @author wss2010.10.14
	 */
	public List findCheckBalance(int index,int length,Request request,
			Date beginDate,Date endDate,String kcType,String ptNo,String wlType,List<String> lsWareSetCodes){
		return this.casLogic.findCheckBalance( index, length,
				 beginDate, endDate, kcType, ptNo, wlType,lsWareSetCodes);
	}
	
	/**
	 * 查询CheckBalanceConvertMateriel
	 * @author wss
	 */
	public List findCheckBalanceConvertM(Request request,List<Condition> conditions) {
		return casLogic.findCheckBalanceConvertM(conditions);
	}
	
	/**
	 * 分页查询CheckBalanceConvertMateriel
	 * @return
	 * @author wss2010.10.14
	 */
	public List findCheckBalanceConvertMateriel(int index,int length,Request request,
			Date beginDate,Date endDate,String kcType,String ptNo,String wlType,List<String> lsWareSetCodes){
		return this.casLogic.findCheckBalanceConvertMateriel( index, length, 
				 beginDate, endDate, kcType, ptNo, wlType,lsWareSetCodes);
	}
	

	/**
	 * 查询CheckBalance数量
	 * @author wss2010.10.14
	 */
	public int findCheckBalanceCount(Request request,
			Date beginDate,Date endDate,String kcType,String ptNo,String wlType,List<String> lsWareSetCodes){
		return casLogic.findCheckBalanceCount(
				 beginDate, endDate, kcType, ptNo, wlType,lsWareSetCodes);
	}
	
	/**
	 * 查询CheckBalanceConvertMateriel数量
	 * @author wss2010.10.14
	 */
	public int findCheckBalanceConvertMaterielCount(Request request,
			Date beginDate,Date endDate,String kcType,String ptNo,String wlType,List<String> lsWareSetCodes){
		return casLogic.findCheckBalanceConvertMaterielCount(
				 beginDate, endDate, kcType, ptNo, wlType,lsWareSetCodes);
	}
	
	/**
	 * 成品折算料件（CheckBalance)
	 * @return
	 * @author wss
	 */
	public void convertCheckBalance(Request request,List list,String taskId,List<Condition> conditions){
		casLogic.convertCheckBalance(list,taskId,conditions);
	}
	
	/**
	 * 成品折算料件（CheckBalance)One by One
	 * @return
	 * @author wss
	 */
	public void convertCheckBalanceToBomOneByOne(Request request,CheckBalance c){
		casLogic.convertCheckBalanceToBomOneByOne(c);
	}
	
	/**
	 * 根据客户查找  结转申请单(关封)号
	 * @author wss* 
	 */
	public List findEnvelopNoByScmCoc(Request request,String billCode,ScmCoc scmCoc){
		return casLogic.findEnvelopNoByScmCoc(billCode,scmCoc);
	}
	
	/**
	 * 查找所有的对应关系
	 * @param request
	 * @return
	 * @author wss2010.09.24
	 */
	public List findAllFactoryAndFactualCustomsRalation(Request request){
		return casDao.findAllFactoryAndFactualCustomsRalation();
	}
	
	/**
	 * 将对应关系中的手册事情更新至相应单据中
	 * @param request
	 * @param ffc
	 * @return
	 * @author wss2010.09.24
	 */
	public void updateEmsNoToBillDetail(Request request,StatCusNameRelationHsn s,String type){
		this.casLogic.updateEmsNoToBillDetail(s,type);
	}
	
	/**
	 * 将对应关系中的手册事情更新至相应单据中
	 * @author wss2010.10.21用来测试
	 */
	public void updateBillDetailEmsNo(Request request,String materielType){
		this.casLogic.updateBillDetailEmsNo(materielType);
	}
	
	/**
	 * 检查单据
	 * @param request
	 * @param billCategory 进出仓类别
	 * @param billType 单据类型
	 * @author wss2010.09.24
	 */
	public List<BillDetail> checkBillDetail(Request request,int billCategory,BillType billType){
		return casLogic.checkBillDetail(billCategory,billType);
	}
	
	/**
	 * 检查单据所有的
	 * @param request
	 * @author wss2010.09.25
	 */
	public List<BillDetail> checkAllBillDetail(Request request){
		return casLogic.checkAllBillDetail();
	}
		
	
	/**
	 * 分页折算CheckBalance报关数量（获取报关资料）
	 * @return
	 * @author wss2010.10.15只是用来实验的
	 */
	public void  fillCheckBalanceHsInfoPerPage(Request request,List list){
		 casLogic.fillCheckBalanceHsInfoPerPage(list);
	}
	
	/**
	 * 分页折BOMCheckBalance
	 * @return
	 * @author wss2010.10.20只是用来实验的
	 */
	public void  convertCheckBalanceToBomPerPage(int index,int length,Request request,
			Date beginDate,Date endDate,String kcType,String ptNo,String wlType,List<String> lsWareSetCodes){
		casLogic.convertCheckBalanceToBomPerPage( index, length, request,
				 beginDate, endDate, kcType, ptNo, wlType,lsWareSetCodes);
	}
	
	/**
	 * 获取相应条件下的 CheckBalanceConvertMateriel 数量
	 * @param request
	 * @param beginDate
	 * @param endDate
	 * @param fatherKcType
	 * @param fatherPtNo
	 * @param fatherWlType
	 * @return
	 * @author wss2010.10.20
	 */
	public int getBalanceCheckConvertMaterielCount(Request request,
			Date beginDate,Date endDate,String fatherKcType,String fatherPtNo,String fatherWlType,List<String> lsWareSetCodes){
		return casLogic.getBalanceCheckConvertMaterielCount(beginDate, endDate, fatherKcType, fatherPtNo, fatherWlType, lsWareSetCodes);
	}
	
	/**
	 * 删除通过折BOM折算的数据
	 * @param request
	 * @param beginDate
	 * @param endDate
	 * @param fatherKcType
	 * @param fatherPtNo
	 * @param fatherWlType
	 * @author wss2010.10.20
	 */
	public void deleteCheckBalanceConvertM(Request request,
			Date beginDate,Date endDate,String fatherKcType,String fatherPtNo,String fatherWlType,List<String> lsWareSetCodes){
		casLogic.deleteCheckBalanceConvertM(beginDate, endDate,  fatherKcType, fatherPtNo, fatherWlType,lsWareSetCodes);
	}
	
	/**
	 * 查找在对应关系中有用到的报关资料
	 * @param materielType
	 * @return
	 * @author wss2010.10.20
	 */
	public List findStatCusNameRelationHsnInUsed(Request request,String materielType){
		return casLogic.findStatCusNameRelationHsnInUsed(materielType);
	}
	/**
	 * 根据单据号检查单据号是否存在重复
	 * @param FactoryBillNo
	 * @return
	 */
	public List checkMulpTransFactoryBillFactoryBillNo (Request request,String factoryBillNo ) {
		return casDao.checkMulpTransFactoryBillFactoryBillNo(factoryBillNo);
	}
	/**
	 * 查找所有转厂单据
	 */
	public List findTransFactoryBill(Request request){
		return casDao.findTransFactoryBill();
	}
	/**
	 * 获得关封单据所有数据
	 */
	public List findCustomsEnvelopBill(Request request,boolean isImpExpGoods) {
		return casDao.findCustomsEnvelopBill(isImpExpGoods);
	}
	/**
	 * 根据单据类型查找单据体
	 * @param billType 单据类型
	 * @return
	 * @author wss
	 */
	public List findBillDetailByBillType(Request request,String billType){
		return casLogic.findBillDetailByBillType(billType);
	}
	
	/**
	 * 显示 国内购买 进出口商品信息(统计上期结存)
	 * 
	 * @param request发送请求
	 * @param conditions查询条件
	 * @param beginDate 开始日期
	 * @return 按照查询条件查出进出口商品信息
	 * @author wss2010.11.23改写
	 */
	public List findImpExpInfosOfInnerBuy(Request request,List<Condition> conditions,Date beginDate){
		return this.casLogic.findImpExpInfosOfInnerBuy(conditions, beginDate);
	}
	/**
	 * 显示 国内购买 进出口商品信息
	 * 
	 * @param request发送请求
	 * @param conditions查询条件
	 * @param beginDate 开始日期
	 * @return 按照查询条件查出进出口商品信息
	 * @author wss2010.11.23改写
	 */
	public List findImpExpInfosOfInnerBuy(Request request,List<Condition> conditions){
		return this.casLogic.findImpExpInfosOfInnerBuy(conditions);
	}
	/**
	 * 保存编码级盘点导入
	 * @param ls
	 */
	public void saveBalanceList(Request request,List ls) {

		for(int i=0;i<ls.size();i++){
			CheckBalanceOfCustom balance =((TempOfCheckBalanceOfCustom)ls.get(i)).getBalance();
			casDao.saveOrUpdate(balance);
		}
		
	}
	/**
	 * 保存料号级盘点导入
	 * @param ls
	 */
	public void saveCheckBalanceList(Request request,List ls) {
		for(int i=0;i<ls.size();i++){
			CheckBalance balance =((TempOfCheckBalanceOfCustom)ls.get(i)).getCheckBalance();
			casDao.saveOrUpdate(balance);
		}
		
	}
	/**
	 * 查询编码级盘点导入
	 */
	public List findCheckBalanceOfCustom(int index, int length,
			Request request, Date beginDate, Date endDate, String kcType,
			String name, String spec,String unitName) {
		return this.casLogic.findCheckBalanceOfCustom(index,length,beginDate,endDate,kcType,name,
				spec,unitName);
	}
	/**
	 * 查询编码级盘点导入数量
	 */
	public int findCheckBalanceCountOfCustom(Request request, Date beginDate,
			Date endDate, String kcType, String name, String spec,String unitName) {
		return this.casLogic.findCheckBalanceCountOfCustom(beginDate,endDate,kcType,name,
				spec,unitName);
	}
	/**
	 * 删除编码级盘点导入
	 */
	public void deleteCheckBalanceOfCustom(Request request, List ls) {
		 this.casLogic.deleteCheckBalanceOfCustom(ls);
	}
	/**
	 * 删除编码级盘点导入
	 */
	public void deleteAllCheckBalanceOfCustom(Request request, Date beginDate,
			Date endDate, String kcType, String name, String spec,String unitName) {
		this.casLogic.deleteAllCheckBalanceOfCustom(beginDate,endDate,kcType,name,
				spec,unitName);
		
	}
	/**
	 * 查找盘点平衡表中存在的盘点时间/工厂料号,用于数据导入时检查,避免重复导入
	 * 
	 * @return
	 */
	public List findCheckBalanceTimeAndPtNo(Request request){
		return casDao.findCheckBalanceTimeAndPtNo();
	}
	/**
	 * 获取三种报关模式的料件Map（名称+规格+单位）
	 * @return
	 */
	public HashMap getCheckMap() {
		return this.casLogic.getCheckMap();
	}

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
	public List findStatCusNameRelationHsnNameAndSpec(Request request, String materielType) {
		return casLogic.findStatCusNameRelationHsnNameAndSpec(materielType);
	}

	@Override
	public List findTransFactCompare(Request request, boolean isMaterial,
			String commName, String commSpec, ScmCoc scmCoc, Date beginMonth,
			Date endMonth, List<String> groupCondition) {
		return transferInfoLogic.findTransFactCompare(isMaterial, commName,
				commSpec, scmCoc, beginMonth, endMonth, groupCondition);
	}

}
