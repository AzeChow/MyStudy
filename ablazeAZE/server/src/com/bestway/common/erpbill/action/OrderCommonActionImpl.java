package com.bestway.common.erpbill.action;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.bestway.bcs.contract.dao.ContractDao;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.logic.ContractLogic;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.common.Request;
import com.bestway.common.erpbill.dao.ErpBillDao;
import com.bestway.common.erpbill.dao.OrderCommonDao;
import com.bestway.common.erpbill.entity.CustomOrder;
import com.bestway.common.erpbill.entity.CustomOrderDetail;
import com.bestway.common.erpbill.entity.CustomOrderExg;
import com.bestway.common.erpbill.entity.Customparames;
import com.bestway.common.erpbill.entity.OrderDateIndex;
import com.bestway.common.erpbill.entity.TempDateCheck;
import com.bestway.common.erpbill.logic.ErpBillLogic;
import com.bestway.common.erpbill.logic.OrderCommonLogic;
import com.bestway.common.materialbase.dao.MaterialManageDao;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.logic.MaterialManageLogic;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.dzsc.dzscmanage.dao.DzscDao;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.dzscmanage.logic.DzscLogic;
import com.bestway.dzsc.innermerge.entity.DzscTenInnerMerge;

public class OrderCommonActionImpl implements OrderCommonAction {

	private MaterialManageDao materialManageDao;

	private MaterialManageLogic materialManageLogic;

	private ErpBillDao erpBillDao;

	private ErpBillLogic erpBillLogic;

	private ContractLogic contractLogic = null;

	private ContractDao contractDao = null;

	private DzscDao dzscDao = null;

	private DzscLogic dzscLogic = null;

	private OrderCommonLogic orderCommonLogic = null;

	private OrderCommonDao orderCommonDao = null;

	public ContractDao getContractDao() {
		return contractDao;
	}

	public void setContractDao(ContractDao contractDao) {
		this.contractDao = contractDao;
	}

	public ContractLogic getContractLogic() {
		return contractLogic;
	}

	public void setContractLogic(ContractLogic contractLogic) {
		this.contractLogic = contractLogic;
	}

	public DzscDao getDzscDao() {
		return dzscDao;
	}

	public void setDzscDao(DzscDao dzscDao) {
		this.dzscDao = dzscDao;
	}

	public DzscLogic getDzscLogic() {
		return dzscLogic;
	}

	public void setDzscLogic(DzscLogic dzscLogic) {
		this.dzscLogic = dzscLogic;
	}

	public ErpBillDao getErpBillDao() {
		return erpBillDao;
	}

	public void setErpBillDao(ErpBillDao erpBillDao) {
		this.erpBillDao = erpBillDao;
	}

	public ErpBillLogic getErpBillLogic() {
		return erpBillLogic;
	}

	public void setErpBillLogic(ErpBillLogic erpBillLogic) {
		this.erpBillLogic = erpBillLogic;
	}

	public MaterialManageDao getMaterialManageDao() {
		return materialManageDao;
	}

	public void setMaterialManageDao(MaterialManageDao materialManageDao) {
		this.materialManageDao = materialManageDao;
	}

	public MaterialManageLogic getMaterialManageLogic() {
		return materialManageLogic;
	}

	public void setMaterialManageLogic(MaterialManageLogic materialManageLogic) {
		this.materialManageLogic = materialManageLogic;
	}

	public OrderCommonLogic getOrderCommonLogic() {
		return orderCommonLogic;
	}

	public void setOrderCommonLogic(OrderCommonLogic orderCommonLogic) {
		this.orderCommonLogic = orderCommonLogic;
	}

	public OrderCommonDao getOrderCommonDao() {
		return orderCommonDao;
	}

	public void setOrderCommonDao(OrderCommonDao orderCommonDao) {
		this.orderCommonDao = orderCommonDao;
	}

	/**
	 * 取得物料信息资料
	 */
	public Materiel getmateriel(Request request, String bom) {
		return materialManageDao.findMaterielByPtNo(bom);
	}

	/**
	 * 查询公司名称
	 */
	public ScmCoc findscmcoc(Request request, String scmname) {
		return materialManageDao.findScmCocByname(scmname);
	}

	/**
	 * 保存订单头
	 * 
	 * @param customOrder
	 * @return
	 */
	public CustomOrder saveCustomOrder(Request request, CustomOrder customOrder) {
		return this.erpBillDao.saveCustomOrder(customOrder);
	}

	/**
	 * 生效订单
	 * 
	 * @param customOrder
	 * @return
	 */
	public CustomOrder okorder(Request request, CustomOrder customOrder) {
		return this.orderCommonLogic.okorder(customOrder);
	}

	/**
	 * 回卷订单
	 * 
	 * @param customOrder
	 * @return
	 */
	public CustomOrder cancelorder(Request request, CustomOrder customOrder) {
		return this.orderCommonLogic.cancelorder(customOrder);
	}

	/**
	 * 删除订单单头
	 * 
	 * @param customOrder
	 * @return
	 */
	public void dropordermaster(Request request, CustomOrder customOrder) {

		this.orderCommonLogic.dropordermaster(customOrder);
	}

	/**
	 * 删除单据体
	 * 
	 * @param orderdetail
	 */
	public void droporderdetail(Request request, CustomOrderDetail orderdetail) {

		this.orderCommonLogic.droporderdetail(orderdetail);
	}

	/**
	 * 查询所有订单头
	 * 
	 * @return
	 */
	public List findallorder(Request request) {
		return erpBillDao.findCustomOrder();
	}

	public int findCustomOrderDetailCount(Request request, String parentId) {
		return orderCommonDao.findCustomOrderDetailCount(parentId);
	}

	/**
	 * 查询成品
	 * 
	 * @return
	 */
	public List findselectmater(Request request) {
		return materialManageDao.findMaterielByMaterielType("0");
	}

	/**
	 * 公司名称查询
	 * 
	 * @return
	 */
	public List findScmCocs(Request request) {
		return materialManageDao.findScmCocs();
	}

	/**
	 * 查询订单单体
	 * 
	 * @param customOrder
	 * @return
	 */
	public List findCustomOrderDetail(Request request, CustomOrder customOrder) {
		return erpBillDao.findCustomOrderDetail(customOrder);
	}

	/**
	 * 保存订单体
	 * 
	 * @param orderdetail
	 * @return
	 */
	public CustomOrderDetail saveorderdetail(Request request,
			CustomOrderDetail orderdetail) {

		return erpBillDao.saveCustomOrderDetail(orderdetail);
	}

	/**
	 * 取得单位
	 * 
	 * @return
	 */
	public List findunit(Request request) {
		return materialManageDao.findCalUnit();
	}

	/**
	 * 取得币别
	 * 
	 * @return
	 */
	public List findcurr(Request request) {
		return materialManageDao.findCurrCode();
	}

	/**
	 * 生成新的合同
	 * 
	 * @param
	 */
	public Contract CreateNewEms(Request request, String contractno) {
		return this.orderCommonLogic.CreateNewEms(contractno);

	}

	/**
	 * 判断成品是否符合电子手册通关备案规则
	 * 
	 * @param dzscteninnermerge
	 * @return
	 */
	public boolean Checkdzsccp(Request request,
			DzscTenInnerMerge dzscteninnermerge, DzscEmsPorHead emshead) {

		/*
		 * List list = dzscaction.findDzscEmsPorWjHeadByEmsNo( new
		 * Request(CommonVars.getCurrUser()),
		 * ((DzscEmsPorHead)emshead).getEmsNo() ); if (list.size() <=0){
		 * JOptionPane.showMessageDialog(null, "找不到手册号为:"+
		 * ((DzscEmsPorHead)emshead).getEmsNo()+"的合同!", "提示",
		 * JOptionPane.OK_OPTION); return false; }else{ DzscEmsPorWjHead wjhead
		 * = (DzscEmsPorWjHead)list.get(0); boolean isinems =
		 * dzscaction.FourInnerMergeineinDzscEmsPorWj( new
		 * Request(CommonVars.getCurrUser()), wjhead,
		 * dzscteninnermerge.getDzscFourInnerMerge(), false); if (!isinems){
		 * JOptionPane.showMessageDialog(null, "成品四位商品编码:"+
		 * dzscteninnermerge.getDzscFourInnerMerge().getFourSeqNum()+
		 * "不存在于合同"+wjhead.getSeqNum(), "提示", JOptionPane.OK_OPTION);
		 * 
		 * return false; } }
		 */

		return true;
	}

	/**
	 * 新增电子手册信息
	 * 
	 * @param customOrder
	 * @param emshead
	 */
	public boolean Createdzscinfo(Request request, CustomOrder customOrder,
			BaseEmsHead emshead, boolean istest) {

		return this.orderCommonLogic.Createdzscinfo(customOrder, emshead,
				istest);
	}

	/**
	 * 新增合同信息
	 * 
	 * @param customOrder
	 * @param emshead
	 * 
	 */
	public boolean Createcontractinfo(Request request, CustomOrder customOrder,
			BaseEmsHead emshead, boolean istest) {

		return this.orderCommonLogic.Createcontractinfo(customOrder, emshead,
				istest);
	}

	/**
	 * 订单转合同
	 * 
	 * @param tableModelOrder
	 * @param isdzsc
	 * @param emshead
	 */
	public void OrderChangetoContract(Request request, List tableModelOrder,
			boolean isdzsc, BaseEmsHead emshead) {

		this.orderCommonLogic.OrderChangetoContract(tableModelOrder, isdzsc,
				emshead);

	}

	public boolean checkorders(Request request, List list) {
		return this.orderCommonLogic.checkorders(list);
	}

	/**
	 * 查询纸制手册合同
	 * 
	 * @return
	 */
	public List findcontracthead(Request request) {
		return this.orderCommonLogic.findcontracthead();
	}

	/**
	 * 查询电子手册合同头
	 * 
	 * @return
	 */
	public List finddzscContractHead(Request request) {
		return this.orderCommonLogic.findcontracthead();
	}

	/**
	 * 查询合同物料信息
	 * 
	 * @return
	 */
	public List findBcsInnerMergea(Request request) {
		return this.orderCommonLogic.findBcsInnerMergea();
	}

	/**
	 * 查询电子手册物料信息
	 * 
	 * @return
	 */
	public List findDzscInnerMergeData(Request request) {
		return this.orderCommonLogic.findDzscInnerMergeData();
	}

	public Customparames findCustomparames(Request request) {

		return this.orderCommonDao.findCustomparames();
	}

	public List findCustomOrder(Request request) {

		return this.orderCommonDao.findCustomOrder();
	}

	/**
	 * 查找定单ＢＯＭ
	 */
	public List findCustomOrderBom(Request request, CustomOrder customOrder) {

		return this.orderCommonDao.findCustomOrderBom(customOrder);
	}

	/**
	 * 查找定单成品
	 */
	public List findCustomOrderExg(Request request, CustomOrder customOrder) {

		return this.orderCommonDao.findCustomOrderExg(customOrder);
	}

	/**
	 * 查找定单料件
	 */
	public List findCutomOrderImg(Request request, CustomOrder customOrder) {
		return this.orderCommonDao.findCustomOrderImg(customOrder);
	}

	/**
	 * 新增定单成品
	 */
	public void saveCustomOrderExg(Request request, CustomOrder customOrder) {

		this.orderCommonLogic.saveCustomOrderExg(customOrder);

	}

	/**
	 * 新增定单料件
	 */
	public void saveCustomOrderImg(Request request, String orderNo) {
		// 

	}

	public CustomOrder findCustomOrder(Request request, String orderNo) {

		return this.orderCommonDao.findCustomOrder(orderNo);
	}

	public void saveCustomOrderBom(Request request) {
		// 

	}

	public List findCustomOrderByType(Request request, Integer type) {

		return this.orderCommonDao.findCustomOrderByType(type);
	}

	/**
	 * 删除定单料件,成品,ＢＯＭ
	 */
	public void delCustomOrderAll(Request request, CustomOrder customOrder) {
		this.orderCommonLogic.delCustomOrderAll(customOrder);

	}

	/**
	 * 保存定单ＢＯＭ,料件,成品
	 */
	public void saveCustomOrderExg(CustomOrder customOrder) {
		this.orderCommonLogic.saveCustomOrderExg(customOrder);

	}

	public List isExitInCustomBom(Request request, CustomOrder customOrder,
			String materieltype) {
		return this.orderCommonLogic.isExitInCustomBom(customOrder,
				materieltype);
	}

	public List isExitInInner(Request request, CustomOrder customOrder,
			String materieltype) {
		return this.orderCommonLogic.isExitInInner(customOrder, materieltype);
	}

	public List isExitInRecords(Request request, CustomOrder customOrder,
			String materieltype) {

		return this.orderCommonLogic.isExitInRecords(customOrder, materieltype);
	}

	public List findTempCustomsOrder(Request request, Integer customType) {

		return this.orderCommonLogic.findTempCustomsOrder(customType);
	}

	public List findCustomOrderByType(Request request, Integer type,
			Boolean isTransfer) {

		return this.orderCommonDao.findCustomOrderByType(type, isTransfer);
	}

	public void PutMessage(Request request, String info) {

	}

	public Map<String, String> getLogList(Request request) {

		return null;
	}

	public void setLogList(Request request, Map<String, String> logList) {

	}

	public List findOrders(Request request, String type, ScmCoc scmcoc,
			Date dateBegin, Date dateEnd, String orderCode, Boolean ifzc,
			Integer customType, String groupingColumn) {

		return this.orderCommonLogic.findOrders(type, scmcoc, dateBegin,
				dateEnd, orderCode, ifzc, customType, groupingColumn);
	}

	public List findCustomOrder(Request request, Integer type, ScmCoc scmCoc,
			Date beginDate, Date endDate, String orderCode, Boolean isTransfer) {

		return this.orderCommonLogic.findCustomOrder(type, scmCoc, beginDate,
				endDate, orderCode, isTransfer);
	}

	public List findReport(Request request, String orderCode, String className) {

		return this.orderCommonDao.findReport(orderCode, className);
	}

	public List findCustomOrder(Request request, Date Startdate, Date enddate) {

		return this.orderCommonDao.findCustomOrder(Startdate, enddate);
	}

	public Integer findCustomOrderMaxImportcount(Request request,
			String billcode) {

		return this.orderCommonDao.findCustomOrderMaxImportcount(billcode);
	}

	public Customparames saveCustomparames(Request request, Customparames value) {

		return this.orderCommonDao.saveCustomparames(value);
	}

	public OrderDateIndex findOrderDateIndex(Request request) {

		return this.orderCommonDao.findOrderDateIndex();
	}

	/**
	 * 订单导入文件来自文件
	 * 
	 * @param list
	 */
	public void importDataFromTxtFile(Request request, List list,
			Boolean cbIsOverwrite) {
		this.orderCommonLogic.importDataFromTxtFile(list, request.getTaskId(),
				cbIsOverwrite);

	}

	public Materiel findMateriel(Request request, String code) {
		return this.orderCommonDao.findMateriel(code);
	}

	public Curr findCurr(Request request, String name) {
		return this.orderCommonDao.findCurr(name);
	}

	public CalUnit findCalUnit(Request request, String name) {
		return this.orderCommonDao.findCalUnit(name);
	}

	public void saveOrderDateIndex(Request request,
			OrderDateIndex orderDateIndex) {
		this.orderCommonDao.saveOrderDateIndex(orderDateIndex);

	}

	public void dropordermaster(Request request, List list) {
		this.orderCommonLogic.dropordermaster(list);

	}

	public List findCustomOrderBom(Request request, CustomOrder customOrder,
			CustomOrderExg customOrderExg) {
		return this.orderCommonDao.findCustomOrderBom(customOrder,
				customOrderExg);
	}

	public List checkFileData(Request request, List list, Hashtable ht,
			Integer type, Integer rateSource) {

		return this.orderCommonLogic.checkFileData(list, ht, request
				.getTaskId(), type, rateSource);
	}

	public List findDzscInnerMergeData(Request request, int index, int length,
			String property, Object value, boolean isLike) {

		return this.orderCommonDao.findDzscInnerMergeData(index, length,
				property, value, isLike);
	}

	public List findBcsInnerMergea(Request request, int index, int length,
			String property, Object value, boolean isLike, int orderType) {

		return this.orderCommonDao.findBcsInnerMergea(index, length, property,
				value, isLike, orderType);
	}

	/**
	 * 
	 */
	public List okorder(Request request, List list) {
		return this.orderCommonLogic.okorder(list);
	}

	/**
	 * 分页查询订单表头信息
	 */
	public List<CustomOrder> findOrderMaster(Request request, int index,
			int length, String property, Object value, boolean isLike) {

		return this.orderCommonLogic.findOrderMaster(index, length, property,
				value, isLike);
	}

	/**
	 * 得到所有的订单明细
	 * 
	 * @return
	 */
	public Map<String, String> findAllCustomOrderDetail(Request request) {
		return this.orderCommonLogic.findAllCustomOrderDetail();
	}

	public void delectCustomOrder(Request request, List list) {
		this.orderCommonLogic.delectCustomOrder(list);

	}

	public List orderChangetoContract(Request request, CustomOrder customOrder) {

		return this.orderCommonLogic.orderChangetoContract(customOrder);
	}

	public List orderChangetoFactory(Request request, CustomOrder customOrder) {

		return this.orderCommonLogic.orderChangetoFactory(customOrder);
	}

	public Double getUnitConvert(Request request, String ptNo) {

		return this.orderCommonDao.getUnitConvert(ptNo);
	}

	/**
	 * 查询未转合同的订单表头
	 * 
	 * @param customOrder
	 * @return
	 */
	public List findCustomOrderHeadByNotChangeToContract(Request request,
			Integer orderType) {

		return this.orderCommonDao
				.findCustomOrderHeadByNotChangeToContract(orderType);
	}

	/**
	 * 查询未转合同的订单表体
	 * 
	 * @param customOrder
	 * @return
	 */
	public List findCustomOrderDetailByNotChangeToContract(Request request,
			List customOrder) {

		return this.orderCommonLogic
				.findCustomOrderDetailByNotChangeToContract(customOrder);
	}

	/**
	 * 订单转合的过程中实现报关资料
	 * 
	 * @param customOrder
	 * @return
	 */
	public List CustomOrderToContractByBgCommInfo(Request request,
			List customOrder, Integer customType) {

		return this.orderCommonLogic.CustomOrderToContractByBgCommInfo(
				customOrder, customType);
	}

	public List findContractByProcessExe(Request request, Integer customType) {

		return this.orderCommonDao.findContractByProcessExe(customType);
	}

	public TempDateCheck dateCheckedList(Request request, List list,
			Integer customType, String emsNo) {
		return this.orderCommonLogic.dateCheckedList(list, customType, emsNo);
	}

	/**
	 * 订单转合同
	 */
	public List ChangeToContract(Request request, List list,
			Integer customType, String emsNo) {
		return this.orderCommonLogic.ChangeToContract(list, customType, emsNo);

	}

	/**
	 * 旧的
	 */
	public void ChangeToContract(Request request, List list,
			Integer customType, String emsNo, Double toContractCount) {
		this.orderCommonLogic.ChangeToContract(list, customType, emsNo,
				toContractCount);

	}

	public List findMaterielBtAllInnerMeger(Request request, String materielType) {
		return this.orderCommonDao.findMaterielBtAllInnerMeger(materielType);
	}

	/**
	 * 统计是否已转合同
	 * 
	 * @param list
	 */
	public String countExsitsNotChangeContract(Request request,List list) {
		return this.orderCommonLogic.countExsitsNotChangeContract(list);
	}
}
