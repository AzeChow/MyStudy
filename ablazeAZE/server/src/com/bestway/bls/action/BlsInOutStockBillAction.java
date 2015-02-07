package com.bestway.bls.action;

import java.util.Date;
import java.util.List;

import com.bestway.bls.entity.BillMatchInfo;
import com.bestway.bls.entity.BillMatchSet;
import com.bestway.bls.entity.BillToWareHouseRelationExp;
import com.bestway.bls.entity.BillToWareHouseRelations;
import com.bestway.bls.entity.BlsInOutStockBill;
import com.bestway.bls.entity.BlsInOutStockBillDetail;
import com.bestway.bls.entity.BlsInOutStockSwichParameterSet;
import com.bestway.bls.entity.BlsTenInnerMerge;
import com.bestway.bls.entity.StorageBill;
import com.bestway.bls.entity.StorageBillAfter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * 进出仓单据服务器端接口
 * 
 * @author hw
 * 
 */
public interface BlsInOutStockBillAction {

	/**
	 * 保存进出仓单数据头
	 * 
	 * @param blsInOutStockBill
	 *            进出仓单据头
	 */
	BlsInOutStockBill saveBlsInOutStockBill(Request request,
			BlsInOutStockBill blsInOutStockBill);

	/**
	 * 查找进出仓单据
	 */
	public List findBlsInOutStockBill(Request request);

	/**
	 * 查找进仓单据
	 */
	public List findBlsInOutStockBillIn(Request request);

	/**
	 * 根据当前日期查找进仓单据
	 */
	public List findBlsInOutStockBillInByCreateDate(Request request, Date date);

	/**
	 * 查找出仓单据
	 */
	public List findBlsInOutStockBillOut(Request request);

	/**
	 * 根据当前日期查找出仓单据
	 */
	public List findBlsInOutStockBillOutByCreateDate(Request request, Date date);

	/**
	 * 删除进出仓单据
	 * 
	 * @param data
	 */
	public void deleteBlsInOutStockBill(Request request,
			BlsInOutStockBill blsInOutStockBill);

	/**
	 * 找出仓库编码
	 * 
	 * @param deliveryNo
	 * @return
	 */
	public List findBillNo(Request request, String billNo);

	/**
	 * 抓出归并前物料所有数据
	 * 
	 * @return
	 */
	public List findMateriel(Request request);

	/**
	 * 保存进出仓单表体
	 * 
	 * @param blsInOutStockBill
	 *            进出仓单据表体
	 */
	public BlsInOutStockBillDetail saveBlsInOutStockBillDetail(Request request,
			BlsInOutStockBillDetail blsInOutStockBillDetail);

	/**
	 * 保存进出仓单表体
	 * 
	 * @param blsInOutStockBill
	 *            进出仓单据表体
	 */
	public List<BlsInOutStockBillDetail> saveBlsInOutStockBillDetail(
			Request request,
			List<BlsInOutStockBillDetail> blsInOutStockBillDetailS);

	/**
	 * 根据单据头抓出对应单据体信息
	 */
	public List findBlsInOutStockBillDetail(Request request, String id);

	/**
	 * 删除出入仓单据表体信息
	 */
	public void deleteBlsInOutStockBillDetail(Request request,
			BlsInOutStockBillDetail blsInOutStockBillDetail);

	/**
	 * 抓出现有所有的客户供应商
	 */
	public List findAllCorrOwner(Request request);

	/**
	 * 抓出数据库中所有的单据编号
	 */
	public List findAllBillNo(Request request);

	/**
	 * 根据单据编号客户供应商及录入日期查询出入仓单局表头
	 */
	public List findBlsInOutStockBillBySomeCondition(Request request,
			ScmCoc corrOwner, String billNo, Date beginDate, Date endDate);

	/**
	 * 查找不存在物料与报关对应表的物料来自料件类型
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
	 *            要查的表对应的属性
	 * @param value
	 *            属性对应的值
	 * @param isLike
	 *            属性的条件是使用"like"还是"="，true表示用"like"
	 * @return 是Materiel型，报关常用物料
	 */
	List findMaterielForBlsInnerMerge(Request request, // String materielType,
			int index, int length, String property, Object value, Boolean isLike);

	/**
	 * 导入出入仓商品明细
	 * 
	 * @param materielList
	 *            是Materiel型，报关常用物料
	 * @param materielType
	 *            物料类型
	 * @return List 是BlsInOutStockBillDetail 型
	 */
	public List importInnerMergeDataFromMateriel(Request request,
			BlsInOutStockBill bsb, List materielList);

	/**
	 * 根据进出仓标志查找出已生效且其表体有未转成仓单的的出入仓单头
	 */
	public List findBlsInOutStockBillNotSwitch(Request request, String ioFlag);

	/**
	 * 根据已查找出来的已生效仓单头查找出其未转成仓单的的出入仓体
	 */
	public List findBlsInOutStockBillsNot(Request request,
			List<BlsInOutStockBill> list);

	/**
	 * 根据单据头抓出对应单据体(当单据体没被转过仓单)信息
	 */
	public List findBlsInOutStockBillDetailNot(Request request, String id);

	/**
	 * 根据进出仓标志查找出仓单信息
	 * 
	 * @param inout
	 *            进出仓标志位
	 * @return
	 */
	public List findStorageBillByInOut(Request request, String ioFlag);

	/**
	 * 根据仓单头抓取归并后的仓单体资料
	 * 
	 * @param storageBill
	 *            仓单头
	 * @return 归并后的仓单体资料
	 */
	public List findStorageBillAfter(Request request, StorageBill storageBill);

	/**
	 * 获得最大的单据号来自出入仓单据表
	 * 
	 * @param type
	 *            此参数没有被用到
	 * @return 最大的编号或者是0001
	 */
	public String getMaxBillNoByType(Request request);

	/**
	 * 转抄出入仓单据
	 * 
	 * @param BlsInOutStockBill
	 *            要转抄的出入仓单据表头
	 * @param copyInfo
	 *            是否也要转抄表体
	 * @param billNo
	 *            单据号
	 * @return 新的出入仓单据表头
	 */
	public BlsInOutStockBill copyBlsInOutStockBillAndCommodityInfo(
			Request request, BlsInOutStockBill blsInOutStockBill,
			Boolean copyInfo, String billNo);

	/**
	 * 通过归并序号取得进出仓单中归并后的最大值
	 * 
	 * @param storageBill
	 * @return
	 */
	public Integer findMaxStorageBillAfterSeqNo(Request request,
			StorageBill storageBill);

	/**
	 * 加入归并前的进出仓商品信息
	 * 
	 * @param request
	 * @param bList
	 * @param storageBill
	 */
	public void addStorageBillBefore(Request request, List bList,
			StorageBill storageBill, List afterList);

	/**
	 * 加入归并前的进出仓商品信息
	 * 
	 * @param request
	 * @param bList
	 * @param storageBill
	 */
	public void addStorageBillBefores(Request request, List bList,
			StorageBill storageBill);

	/**
	 * 根据料号找出相应的归并信息
	 */
	public List findBlsInnerMerge(Request request, Materiel materiel, int seqNum);

	/**
	 * 保存进出仓单据表头及表体,文本导入时使用.
	 */
	public List saveInOutStockBillAndImpExpCommodityInfo(Request request,
			List list, boolean isMergeCommInfo);

	/**
	 * 删除进出仓单据表头表体信息
	 * 
	 * @param blsInOutStockBill
	 */
	public void deleteBlsInOutStockBillHeadAndBody(Request request,
			BlsInOutStockBill blsInOutStockBill);

	/**
	 * 批量删除出入仓单据表体信息
	 */
	public void deleteBlsInOutStockBillDetails(Request request,
			List blsInOutStockBillDetail);

	/**
	 * 查找出所有的单据号
	 * 
	 * @return
	 */
	public List findBlsInOutBillBillNo(Request request);

	/**
	 * 通过起始时间和结束时间找到相应的出入仓单据头
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List findBlsInOutStockBillInByDate(Request request, String iOFlag,
			Date beginDate, Date endDate);

	/**
	 * 查询出仓单据里的没有对应转入单据商品，或不存在的对应转入单据商品信息
	 * 
	 * @param request
	 * @return
	 */
	public List findBlsOutStockBillDetailNoInputBill(Request request);

	/**
	 * 查询出仓单据商品停息
	 * 
	 * @param warehouseName商品名称
	 * @param spec商品规格
	 * @param scmCoc客户
	 * @param beginDate开始日期
	 * @param endDate结束日期
	 */
	public List findBlsOutStockBillDetail(Request request,
			String warehouseName, String spec, ScmCoc scmCoc, Date beginDate,
			Date endDate);

	/**
	 * 查询入仓单据商品停息
	 * 
	 * @param warehouseName商品名称
	 * @param spec商品规格
	 * @param scmCoc客户
	 * @param beginDate开始日期
	 * @param endDate结束日期
	 */
	public List findBlsInStockBillDetail(Request request, String warehouseName,
			String spec, ScmCoc scmCoc, Date beginDate, Date endDate);

	/**
	 * 查询单据对应信息
	 * 
	 * @param matchName商品名称
	 * @param matchspec商品规格
	 * @param customerName客户
	 * @param beginDate开始日期
	 * @param endDate结束日期
	 */
	public List findBillMatchInfo(Request request, String matchName,
			String matchspec, String customerName, Date beginDate, Date endDate);

	/**
	 * 新建或更新单据对应信息
	 * 
	 * @param matchInfo
	 * @return
	 */
	public BillMatchInfo saveBillMatchInfo(Request request,
			BillMatchInfo billMatchInfo);
	/**
	 * 单据对应
	 * @param request
	 * @param outBills
	 * @param firstBills
	 */
	public void matchInAndOutBill(Request request,List<BlsInOutStockBillDetail> outBills,List<BlsInOutStockBillDetail> firstBills);

	/**
	 * 新建或更新单据对应信息
	 * 
	 * @param matchInfo
	 * @return
	 */
	public List<BillMatchInfo> saveBillMatchInfo(Request request,
			List<BillMatchInfo> billMatchInfos);

	/**
	 * 根据对应信息查询对应的出仓单据
	 * 
	 * @param matchInfo
	 * @return
	 */
	public BlsInOutStockBillDetail findBlsOutStockBillDetailByMatchInfo(
			Request request, BillMatchInfo matchInfo);
	
	/**
	 * 根据对应信息查询对应的出仓单据
	 * 
	 * @param matchInfo
	 * @return
	 */
	public void deleteMatchInfo(
			Request request, List<BillMatchInfo> billMatchInfoS);

	/**
	 * 根据对应信息查询对应的入仓单据
	 * 
	 * @param matchInfo
	 * @return
	 */
	public BlsInOutStockBillDetail findBlsInStockBillDetailByMatchInfo(
			Request request, BillMatchInfo matchInfo);

	/**
	 * 删除对应关系
	 * 
	 * @param matchInfo
	 */
	public void deletesaveBillMatchInfo(BillMatchInfo matchInfo);

	/**
	 * 根据单据编号查找进仓单据
	 */
	public List findBlsInOutStockBillInByBillNo(Request request, String billNo);

	/**
	 * 查找仓单号归并后最大流水号
	 * 
	 * @param storageBill
	 * @return
	 */
	public Integer findMaxBlsInOutStockBillSeqNos(Request request,
			BlsInOutStockBill blsInOutStockBill);

	/**
	 * 通过进仓单的单据编号和进仓单表体中的商品的商品流水号抓出所对应商品的总数量
	 * 
	 * @param inBillNos
	 * @param inBillGoodNo
	 * @return
	 */
	public Double findDetailQtyByBlsInStockBillNos(Request request,
			String inBillNos, int inBillGoodNo);

	/**
	 * 根据期初单据查询对应商品对应数量共和
	 */
	public Double findFirstBillMatchQty(Request request,
			BlsInOutStockBillDetail firstBill);

	/**
	 * 保存，更新单据对应参数
	 * 
	 * @param set
	 */
	public BillMatchSet saveBillMatchSet(Request request, BillMatchSet set);

	/**
	 * 查询参数
	 * 
	 * @return
	 */
	public BillMatchSet findBillMatchSet(Request request);

	/**
	 * 通过料号查找出归并关系中对应的报关级商品信息
	 * 
	 * @return
	 */
	public List findBlsTenInnerMergeByMateriel(Request request,
			Materiel materiel);

	/**
	 * 保存仓单归并后信息
	 */
	public StorageBillAfter saveStorageBillAfter(Request request,
			StorageBillAfter storageBillAfter);

	/**
	 * 保存仓单头信息
	 */
	public StorageBill saveStorageBill(Request request, StorageBill storageBill);

	/**
	 * 归并料号相同的商品信息数量毛重净重已经合并原产国
	 */
	public List mergerBlsInOutStockCommodityInfo(Request request, List list,
			boolean isImp, boolean isExp);

	/**
	 * 转仓单方法
	 * @param request
	 * @param bList
	 * @param sb
	 * @param afterList
	 * @param rList
	 */
	public void addStorageBillBefore2(Request request, List bList,
			StorageBill sb, List afterList, List rList);

	/**
	 * 保存入仓单据与入仓单的对应关系
	 */
	public BillToWareHouseRelations saveBillToWareHouseRelations(
			Request request, BillToWareHouseRelations bhr);

	/**
	 * 根据进仓单据编号查找进仓单据
	 */
	public List findBlsInOutStockBillInByinBillNo(Request request,
			String inbillNo, Integer seqNO);

	/**
	 * 根据入仓单据明细查找进入仓单号码
	 */
	public List findBlsInOutStockBillDeInByBlsInOutStockBillDetail(
			Request request, BlsInOutStockBillDetail bsd);

	/**
	 * 根据入仓单查找进入仓单号码
	 */
	public List findBillToWareHouseRelationsInByStockBill(Request request,
			StorageBill sb);

	/**
	 * 删除对应关系
	 * 
	 * @param matchInfo
	 */
	public void deleteBillToWareHouseRelations(Request request,
			BillToWareHouseRelations billToWareHouseRelations);

	/**
	 * 根据入仓单查找进入仓单据表体明细
	 */
	public List findBlsInOutStockBillDetailByStockBill(Request request,
			StorageBill sb);

	/**
	 * 保存转仓单参数设置
	 */
	public BlsInOutStockSwichParameterSet saveBlsSwitchParameterSet(
			Request request, BlsInOutStockSwichParameterSet bosp);

	/**
	 * 删除转仓单参数设置
	 */
	public void deleteBlsSwitchParameterSet(Request request,
			BlsInOutStockSwichParameterSet bosp);

	/**
	 * 找出转仓单参数设置中所有的值
	 */
	public List findBlsSwitchParameterSet(Request request);

	/**
	 * 根据进出仓类型查找参数设置
	 */
	public BlsInOutStockSwichParameterSet findBlsSwitchParameterSetByInOutFlag(
			Request request, String inOutFlag);
	
	/**
	 * 保存出仓单据与出仓单的对应关系
	 */
	public void saveBillToWareHouseRelationExp(Request request,
			BillToWareHouseRelationExp bhre);
	
	/**
	 * 根据出仓单查找进出仓单据表体明细
	 */
	public List findBlsInOutStockBillDetailByStockBills(Request request,StorageBill sb);
	
	/**
	 * 根据出仓单查找对应关系
	 */
	public List findBillToWareHouseRelationExpInByStockBill(Request request,StorageBill sb);
	
	/**
	 * 删除出仓对应关系
	 * 
	 * @param matchInfo
	 */
	public void deleteBillToWareHouseRelationExp(Request request,
			BillToWareHouseRelationExp billToWareHouseRelationExp) ;
	
	/**
	 * 根据入仓单据编号和入仓单据商品流水号查找进仓单据
	 */
	public List findBlsInOutStockBillInByBillNoAndSeqNo(Request request,
			String billNo, Integer seqNo);
	/**
	 * 查找出仓当中所有账册编号
	 */
	public List findAllEmsNo(Request request);
}
