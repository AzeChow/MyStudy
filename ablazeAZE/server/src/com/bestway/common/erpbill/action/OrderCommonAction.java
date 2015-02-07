package com.bestway.common.erpbill.action;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.common.Request;
import com.bestway.common.erpbill.entity.CustomOrder;
import com.bestway.common.erpbill.entity.CustomOrderDetail;
import com.bestway.common.erpbill.entity.CustomOrderExg;
import com.bestway.common.erpbill.entity.Customparames;
import com.bestway.common.erpbill.entity.OrderDateIndex;
import com.bestway.common.erpbill.entity.TempDateCheck;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.innermerge.entity.DzscTenInnerMerge;

public interface OrderCommonAction {

	/**
	 * 取得物料信息资料
	 */
	public Materiel getmateriel(Request request, String bom);

	/**
	 * 查询公司名称
	 */
	public ScmCoc findscmcoc(Request request, String scmname);

	/**
	 * 保存订单头
	 * 
	 * @param customOrder
	 * @return
	 */
	public CustomOrder saveCustomOrder(Request request, CustomOrder customOrder);

	/**
	 * 生效订单
	 * 
	 * @param customOrder
	 * @return
	 */
	public CustomOrder okorder(Request request, CustomOrder customOrder);

	/**
	 * 生效订单
	 * 
	 * @param request
	 * @param list
	 * @return
	 */
	public List okorder(Request request, List list);

	/**
	 * 回卷订单
	 * 
	 * @param customOrder
	 * @return
	 */
	public CustomOrder cancelorder(Request request, CustomOrder customOrder);

	/**
	 * 删除订单单头
	 * 
	 * @param customOrder
	 * @return
	 */
	public void dropordermaster(Request request, CustomOrder customOrder);

	/**
	 * 删除订单单头
	 * 
	 * @param customOrder
	 * @return
	 */
	public void dropordermaster(Request request, List list);

	/**
	 * 删除订单单头
	 * 
	 * @param request
	 * @param list
	 */
	public void delectCustomOrder(Request request, List list);

	/**
	 * 删除单据体
	 * 
	 * @param orderdetail
	 */
	public void droporderdetail(Request request, CustomOrderDetail orderdetail);

	/**
	 * 查询所有订单头
	 * 
	 * @return
	 */
	public List findallorder(Request request);

	/**
	 * 查询成品
	 * 
	 * @return
	 */
	public List findselectmater(Request request);

	/**
	 * 公司名称查询
	 * 
	 * @return
	 */
	public List findScmCocs(Request request);

	/**
	 * 查询订单单体
	 * 
	 * @param customOrder
	 * @return
	 */
	public List findCustomOrderDetail(Request request, CustomOrder customOrder);

	/**
	 * 保存订单体
	 * 
	 * @param orderdetail
	 * @return
	 */
	public CustomOrderDetail saveorderdetail(Request request,
			CustomOrderDetail orderdetail);

	/**
	 * 取得单位
	 * 
	 * @return
	 */
	public List findunit(Request request);

	/**
	 * 取得币别
	 * 
	 * @return
	 */
	public List findcurr(Request request);

	/**
	 * 生成新的合同
	 * 
	 * @param
	 */
	public Contract CreateNewEms(Request request, String contractno);

	/**
	 * 判断成品是否符合电子手册通关备案规则
	 * 
	 * @param dzscteninnermerge
	 * @return
	 */
	public boolean Checkdzsccp(Request request,
			DzscTenInnerMerge dzscteninnermerge, DzscEmsPorHead emshead);

	/**
	 * 新增电子手册信息
	 * 
	 * @param customOrder
	 * @param emshead
	 */
	public boolean Createdzscinfo(Request request, CustomOrder customOrder,
			BaseEmsHead emshead, boolean istest);

	/**
	 * 新增合同信息
	 * 
	 * @param customOrder
	 * @param emshead
	 * 
	 */
	public boolean Createcontractinfo(Request request, CustomOrder customOrder,
			BaseEmsHead emshead, boolean istest);

	/**
	 * 订单转合同
	 * 
	 * @param tableModelOrder
	 * @param isdzsc
	 * @param emshead
	 */
	// public void OrderChangetoContract(JTableListModel tableModelOrder,boolean
	// isdzsc,BaseEmsHead emshead);
	//
	// public boolean checkorders(JTableListModel tableModelOrder);
	/**
	 * 查询纸制手册合同
	 * 
	 * @return
	 */
	public List findcontracthead(Request request);

	/**
	 * 查询电子手册合同头
	 * 
	 * @return
	 */
	public List finddzscContractHead(Request request);

	/**
	 * 查询合同物料信息
	 * 
	 * @return
	 */
	public List findBcsInnerMergea(Request request);

	public List findBcsInnerMergea(Request request, int index, int length,
			String property, Object value, boolean isLike,int orderType);

	/**
	 * 查询电子手册物料信息
	 * 
	 * @return
	 */
	public List findDzscInnerMergeData(Request request);

	public List findDzscInnerMergeData(Request request, int index, int length,
			String property, Object value, boolean isLike);

	public Map<String, String> getLogList(Request request);

	public void setLogList(Request request, Map<String, String> logList);

	public void PutMessage(Request request, String info);

	// /**
	// * 刷新订单
	// * @param tableModelOrder
	// */
	// public void RefreshOrder(JTableListModel tableModelOrder);

	/**
	 * 获取参数设置信息
	 * 
	 */
	public Customparames findCustomparames(Request request);

	/**
	 * 查找定单
	 */
	public List findCustomOrder(Request request);

	/**
	 * 查找定单成品
	 */
	public List findCustomOrderExg(Request request, CustomOrder customOrder);

	/**
	 * 查找定单料件
	 */
	public List findCutomOrderImg(Request request, CustomOrder customOrder);

	/**
	 * 查找定单ＢＯＭ
	 */
	public List findCustomOrderBom(Request request, CustomOrder customOrder);

	/**
	 * 查找定单ＢＯＭ
	 */
	public List findCustomOrderBom(Request request, CustomOrder customOrder,
			CustomOrderExg customOrderExg);

	/**
	 * 新增定单成品
	 */
	public void saveCustomOrderExg(Request request, CustomOrder customOrder);

	/**
	 * 新增定单料件
	 */
	public void saveCustomOrderImg(Request request, String orderNo);

	/**
	 * 根据报关类型查询定单
	 * 
	 * @param request
	 * @param type
	 * @return
	 */
	public List findCustomOrderByType(Request request, Integer type,
			Boolean isTransfer);

	/**
	 * 删除定单ＢＯＭ,料件,成品
	 */
	public void delCustomOrderAll(Request request, CustomOrder customOrder);

	/**
	 * 逻辑检控：检查定单明细中的成品是否在报关常用ＢＯＭ存在
	 */
	public List isExitInCustomBom(Request request, CustomOrder customOrder,
			String materieltype);

	/**
	 * 逻辑检控：检查定单明细中的成品是否在归并关系存在 是否已备案
	 */
	public List isExitInInner(Request request, CustomOrder customOrder,
			String materieltype);

	/**
	 * 逻辑检控：检查定单明细中的成品是否已备案
	 */
	public List isExitInRecords(Request request, CustomOrder customOrder,
			String materieltype);

	public CustomOrder findCustomOrder(Request request, String billCode);

	public List findOrders(Request request, String type, ScmCoc scmcoc,
			Date dateBegin, Date dateEnd, String orderCode, Boolean ifzc,
			Integer customType, String groupingColumn);

	public List findCustomOrder(Request request, Integer type, ScmCoc scmcoc,
			Date dateBegin, Date dateEnd, String orderCode, Boolean ifzc);

	public List findReport(Request request, String orderCode, String className);

	public List findCustomOrder(Request request, Date Startdate, Date enddate);

	public Integer findCustomOrderMaxImportcount(Request request,
			String billcode);

	/**
	 * 保存参数设置
	 * 
	 * @param value
	 */
	public Customparames saveCustomparames(Request request, Customparames value);

	/**
	 * 根据参数查询订单明细的一些栏位
	 * 
	 * @param isShowTransfer
	 *            是否显示转厂
	 * @param startdate
	 *            开始日期
	 * @param enddate
	 *            结束日期
	 * @param customer
	 *            客户供应商
	 * @param billCode
	 *            订单号
	 * @param ptNo
	 *            BOM号
	 * @return
	 */
	// public List findSomeObjectInCustomOrderDetail(Request request,Boolean
	// isShowTransfer, Date startdate,
	// Date enddate, ScmCoc customer, String billCode, String ptNo);
	/**
	 * 查询定单导入文件栏位对应次序
	 */
	public OrderDateIndex findOrderDateIndex(Request request);

	/**
	 * 检查定单文档导入数据时,文本数据的正确性
	 * 
	 * @param request
	 * @param list
	 * @param ht
	 * @return
	 */
	List checkFileData(Request request, List list, Hashtable ht, Integer type,
			Integer rateSource);

	/**
	 * 订单导入文件来自文件
	 * 
	 * @param list
	 */
	void importDataFromTxtFile(Request request, List list, Boolean cbIsOverwrite);

	/**
	 * 保存文本导入的资料
	 * 
	 * @param request
	 * @param orderDateIndex
	 */
	void saveOrderDateIndex(Request request, OrderDateIndex orderDateIndex);

	/**
	 * 
	 * @param request
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List<CustomOrder> findOrderMaster(Request request, int index,
			int length, String property, Object value, boolean isLike);
	
	/**
	 * 得到所有的订单明细
	 * 
	 * @return
	 */
	public Map<String, String> findAllCustomOrderDetail(Request request);

	/**
	 * 
	 * @param request
	 * @param customOrder
	 * @return
	 */
	public List orderChangetoContract(Request request, CustomOrder customOrder);

	/**
	 * 
	 * @param request
	 * @param customOrder
	 * @return
	 */
	public List orderChangetoFactory(Request request, CustomOrder customOrder);

	/**
	 * 
	 * @param request
	 * @param ptNo
	 * @return
	 */
	public Double getUnitConvert(Request request, String ptNo);

	/**
	 * 查询未转合同的订单表头
	 * 
	 * @param customOrder
	 * @return
	 */
	public List findCustomOrderHeadByNotChangeToContract(Request request,
			Integer orderType);

	/**
	 * 查询未转合同的订单表体
	 * 
	 * @param customOrder
	 * @return
	 */
	public List findCustomOrderDetailByNotChangeToContract(Request request,
			List customOrder);

	/**
	 * 订单转合的过程中实现报关资料
	 * 
	 * @param customOrder
	 * @return
	 */
	public List CustomOrderToContractByBgCommInfo(Request request,
			List customOrder, Integer customType);

	public Materiel findMateriel(Request request,String code);

	public Curr findCurr(Request request,String name);

	public CalUnit findCalUnit(Request request,String name);

	/**
	 * 
	 * @param request
	 * @param customType
	 * @return
	 */
	public List findContractByProcessExe(Request request, Integer customType);

	/**
	 * 订单数据检查
	 * 
	 * @param request
	 * @param list
	 * @param customType
	 * @param emsNo
	 * @return
	 */
	public TempDateCheck dateCheckedList(Request request, List list,
			Integer customType, String emsNo);

	/**
	 * 订单转合同
	 * 
	 * @param request
	 * @param list
	 * @param customType
	 * @param emsNo
	 */
	public List ChangeToContract(Request request, List list,
			Integer customType, String emsNo);

	int findCustomOrderDetailCount(Request request, String parentId);

	public List findTempCustomsOrder(Request request, Integer customType);

	public void ChangeToContract(Request request, List list,
			Integer customType, String emsNo, Double toContractCount);

	public List findMaterielBtAllInnerMeger(Request request, String materielType);
	/**
	 * 统计是否已转合同
	 * 
	 * @param list
	 */
	public String countExsitsNotChangeContract(Request request,List list);
}
