package com.bestway.bls.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.commons.beanutils.BeanUtils;

import com.bestway.bls.dao.BlsInOutStockBillDao;
import com.bestway.bls.entity.BillMatchInfo;
import com.bestway.bls.entity.BillMatchSet;
import com.bestway.bls.entity.BillToWareHouseRelationExp;
import com.bestway.bls.entity.BillToWareHouseRelations;
import com.bestway.bls.entity.BlsIOStockBillIOF;
import com.bestway.bls.entity.BlsInOutStockBill;
import com.bestway.bls.entity.BlsInOutStockBillDetail;
import com.bestway.bls.entity.BlsInOutStockSwichParameterSet;
import com.bestway.bls.entity.BlsInnerMerge;
import com.bestway.bls.entity.BlsTenInnerMerge;
import com.bestway.bls.entity.StorageBill;
import com.bestway.bls.entity.StorageBillAfter;
import com.bestway.bls.entity.StorageBillBefore;
import com.bestway.bls.logic.BlsInOutStockBillLogic;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.WareSet;

/**
 * 进出仓单据接口实现
 * 
 * @author hw
 * 
 */
//保税物流-进出仓单据
@AuthorityClassAnnotation(caption = "保税仓管理", index = 17)
public class BlsInOutStockBillActionImpl extends BaseActionImpl implements BlsInOutStockBillAction {
	/**
	 * 进出仓单据logic类
	 */
	private BlsInOutStockBillLogic blsInOutStockBillLogic = null;

	/**
	 * 得到进出仓单据logic
	 * 
	 * @return
	 */
	public BlsInOutStockBillLogic getBlsInOutStockBillLogic() {
		return blsInOutStockBillLogic;
	}

	/**
	 * * 设置进出仓单据logic
	 * 
	 * @param blsInOutStockBillLogic
	 */
	public void setBlsInOutStockBillLogic(
			BlsInOutStockBillLogic blsInOutStockBillLogic) {
		this.blsInOutStockBillLogic = blsInOutStockBillLogic;
	}

	/**
	 * 进出仓单据DAO类
	 */
	private BlsInOutStockBillDao blsInOutStockBillDao = null;

	/**
	 * 得到进出仓单据Dao
	 * 
	 * @return
	 */
	public BlsInOutStockBillDao getBlsInOutStockBillDao() {
		return blsInOutStockBillDao;
	}

	/**
	 * 设置进出仓单据Dao
	 * 
	 * @param blsInOutStockBillDao
	 */
	public void setBlsInOutStockBillDao(
			BlsInOutStockBillDao blsInOutStockBillDao) {
		this.blsInOutStockBillDao = blsInOutStockBillDao;
	}

	/**
	 * 保存进出仓单数据头
	 * 
	 * @param blsInOutStockBill
	 *            进出仓单据头
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-保存", index = 1)
	public BlsInOutStockBill saveBlsInOutStockBill(Request request,
			BlsInOutStockBill blsInOutStockBill) {
		this.blsInOutStockBillDao.saveBlsInOutStockBill(blsInOutStockBill);
		return blsInOutStockBill;
	}

	/**
	 * 查找进出仓单据
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findBlsInOutStockBill(Request request) {
		return this.blsInOutStockBillDao.findBlsInOutStockBill();
	}

	/**
	 * 查找进仓单据
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findBlsInOutStockBillIn(Request request) {
		return this.blsInOutStockBillDao.findBlsInOutStockBillIn();
	}

	/**
	 * 根据当前日期查找进仓单据
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findBlsInOutStockBillInByCreateDate(Request request, Date date) {
		return this.blsInOutStockBillDao
				.findBlsInOutStockBillInByCreateDate(date);
	}

	/**
	 * 查找出仓单据
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findBlsInOutStockBillOut(Request request) {
		return this.blsInOutStockBillDao.findBlsInOutStockBillOut();
	}

	/**
	 * 根据当前日期查找出仓单据
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findBlsInOutStockBillOutByCreateDate(Request request, Date date) {
		return this.blsInOutStockBillDao
				.findBlsInOutStockBillOutByCreateDate(date);
	}

	/**
	 * 删除进出仓单据
	 * 
	 * @param data
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-删除", index = 7)
	public void deleteBlsInOutStockBill(Request request,
			BlsInOutStockBill blsInOutStockBill) {
		this.blsInOutStockBillDao.deleteBlsInOutStockBill(blsInOutStockBill);
	}

	/**
	 * 找出仓库编码
	 * 
	 * @param deliveryNo
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findBillNo(Request request, String billNo) {
		return this.blsInOutStockBillDao.findBillNo(billNo);
	}

	/**
	 * 抓出归并前物料所有数据
	 * 
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findMateriel(Request request) {
		return this.blsInOutStockBillDao.findMateriel();
	}

	/**
	 * 保存进出仓单表体
	 * 
	 * @param blsInOutStockBill
	 *            进出仓单据表体
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-保存", index = 1)
	public BlsInOutStockBillDetail saveBlsInOutStockBillDetail(Request request,
			BlsInOutStockBillDetail blsInOutStockBillDetail) {
		this.blsInOutStockBillDao
				.saveBlsInOutStockBillDetail(blsInOutStockBillDetail);
		return blsInOutStockBillDetail;
	}

	/**
	 * 根据对应信息查询对应的出仓单据
	 * 
	 * @param matchInfo
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public void deleteMatchInfo(Request request,
			List<BillMatchInfo> billMatchInfoS) {
		this.blsInOutStockBillLogic.deleteMatchInfo(billMatchInfoS);
	}

	/**
	 * 保存进出仓单表体
	 * 
	 * @param blsInOutStockBill
	 *            进出仓单据表体
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-保存", index = 1)
	public List<BlsInOutStockBillDetail> saveBlsInOutStockBillDetail(
			Request request,
			List<BlsInOutStockBillDetail> blsInOutStockBillDetailS) {
		this.blsInOutStockBillLogic
				.saveBlsInOutStockBillDetail(blsInOutStockBillDetailS);
		return blsInOutStockBillDetailS;
	}

	/**
	 * 根据单据头抓出对应单据体信息
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findBlsInOutStockBillDetail(Request request, String id) {
		return this.blsInOutStockBillDao.findBlsInOutStockBillDetail(id);
	}

	/**
	 * 删除出入仓单据表体信息
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-删除", index = 7)
	public void deleteBlsInOutStockBillDetail(Request request,
			BlsInOutStockBillDetail blsInOutStockBillDetail) {
		this.blsInOutStockBillDao
				.deleteBlsInOutStockBillDetail(blsInOutStockBillDetail);
	}

	/**
	 * 抓出现有所有的客户供应商
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findAllCorrOwner(Request request) {
		return this.blsInOutStockBillDao.findAllCorrOwner();
	}

	/**
	 * 抓出数据库中所有的单据编号
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findAllBillNo(Request request) {
		return this.blsInOutStockBillDao.findAllBillNo();
	}

	/**
	 * 根据单据编号客户供应商及录入日期查询出入仓单局表头
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findBlsInOutStockBillBySomeCondition(Request request,
			ScmCoc corrOwner, String billNo, Date beginDate, Date endDate) {
		return this.blsInOutStockBillDao.findBlsInOutStockBillBySomeCondition(
				corrOwner, billNo, beginDate, endDate);
	}

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
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findMaterielForBlsInnerMerge(Request request, int index,
			int length, String property, Object value, Boolean isLike) {// String
		// materielType,
		return this.blsInOutStockBillDao.findMaterielForBlsInnerMerge(index,
				length, property, value, isLike);
	}

	/**
	 * 导入出入仓商品明细(仓体信息)
	 * 
	 * @param materielList
	 *            是Materiel型，报关常用物料
	 * @param materielType
	 *            物料类型
	 * @return List 是BlsInOutStockBillDetail 型
	 */
	@AuthorityFunctionAnnotation(caption = "导入出入仓商品明细(仓体信息)", index = 19)
	public List importInnerMergeDataFromMateriel(Request request,
			BlsInOutStockBill bsb, List materielList) {
		return this.blsInOutStockBillLogic.importInnerMergeDataFromMateriel(
				bsb, materielList);
	}

	/**
	 * 根据进出仓标志查找出已生效且其表体有未转成仓单的的出入仓单头
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findBlsInOutStockBillNotSwitch(Request request, String ioFlag) {
		return this.blsInOutStockBillDao.findBlsInOutStockBillNotSwitch(ioFlag);
	}

	/**
	 * 根据已查找出来的已生效仓单头查找出其未转成仓单的的出入仓体
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findBlsInOutStockBillsNot(Request request,
			List<BlsInOutStockBill> list) {
		return this.blsInOutStockBillDao.findBlsInOutStockBillsNot(list);
	}

	/**
	 * 根据单据头抓出对应单据体(当单据体没被转过仓单)信息
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findBlsInOutStockBillDetailNot(Request request, String id) {
		return this.blsInOutStockBillDao.findBlsInOutStockBillDetailNot(id);
	}

	/**
	 * 根据进出仓标志查找出仓单信息
	 * 
	 * @param inout
	 *            进出仓标志位
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findStorageBillByInOut(Request request, String ioFlag) {
		return this.blsInOutStockBillDao.findStorageBillByInOut(ioFlag);
	}

	/**
	 * 根据仓单头抓取归并后的仓单体资料
	 * 
	 * @param storageBill
	 *            仓单头
	 * @return 归并后的仓单体资料
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findStorageBillAfter(Request request, StorageBill storageBill) {
		return this.blsInOutStockBillDao.findStorageBillAfter(storageBill);
	}

	/**
	 * 获得最大的单据号来自出入仓单据表
	 * 
	 * @param type
	 *            此参数没有被用到
	 * @return 最大的编号或者是0001
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public String getMaxBillNoByType(Request request) {
		return this.blsInOutStockBillLogic.getMaxBillNoByType();
	}

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
	@AuthorityFunctionAnnotation(caption = "转抄出入仓单据", index = 26)
	public BlsInOutStockBill copyBlsInOutStockBillAndCommodityInfo(
			Request request, BlsInOutStockBill blsInOutStockBill,
			Boolean copyInfo, String billNo) {
		return this.blsInOutStockBillLogic
				.copyBlsInOutStockBillAndCommodityInfo(blsInOutStockBill,
						copyInfo, billNo);
	}

	/**
	 * 通过归并序号取得进出仓单中归并后的最大值
	 * 
	 * @param storageBill
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public Integer findMaxStorageBillAfterSeqNo(Request request,
			StorageBill storageBill) {
		return this.blsInOutStockBillDao
				.findMaxStorageBillAfterSeqNo(storageBill);
	}

	/**
	 * 保存归并前和归并后的进出仓商品信息
	 * 
	 * @param request
	 * @param list
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-保存", index = 1)
	public List saveStorageBillAfterAndBefore(Request request, List list) {
		return this.blsInOutStockBillLogic.saveStorageBillAfterAndBefore(list);
	}

	// /**
	// * 保存仓单头信息
	// */
	// public void saveStorageBill(StorageBill storageBill) {
	// this.blsInOutStockBillDao.saveStorageBill(storageBill);
	// }

	/**
	 * 转仓单方法
	 */
	@AuthorityFunctionAnnotation(caption = "转仓单", index = 29)
	@SuppressWarnings("unchecked")
	public void addStorageBillBefore2(Request request, List bList,
			StorageBill sb, List afterList, List rlist) {
		List hasLists = new ArrayList();
		List<List> list = new ArrayList<List>();
		int pageCount = 20;
		int pages = afterList.size() / pageCount
				+ (afterList.size() % pageCount == 0 ? 0 : 1);

		BlsInOutStockBillDetail bsd = (BlsInOutStockBillDetail) rlist.get(0);
		BlsInOutStockBill bsb = bsd.getBsb();
		String inOutFlag = bsb.getIoFlag();
		for (int i = 0; i < pages; i++) {
			StorageBill sbNew = new StorageBill();
			if (i == 0) {
				sbNew = sb;

				double grossWeight = 0.0;

				double netWeight = 0.0;

				Integer packCounts = 0;

				Date validDate = null;
				for (int j = i * pageCount; j < (i + 1) * pageCount
						&& j < afterList.size(); j++) {
					// if (rlist.size() == 1) {
					BlsInOutStockBillDetail ss = (BlsInOutStockBillDetail) rlist
							.get(j);

					grossWeight += (ss.getGrossWeight() == null ? 0.0 : ss
							.getGrossWeight());
					// + (sss.getGrossWeight() == null ? 0.0 : sss
					// .getGrossWeight());
					System.out.println("ss.getGrossWeight()="
							+ ss.getGrossWeight());
					// System.out.println("sss.getGrossWeight()="+sss.getGrossWeight());
					netWeight += (ss.getNetWeight() == null ? 0.0 : ss
							.getNetWeight());
					// + (sss.getNetWeight() == null ? 0.0 : sss
					// .getNetWeight());
					System.out
							.println("ss.getNetWeight()=" + ss.getNetWeight());
					// System.out.println("sss.getNetWeight()="+sss.getNetWeight());
					packCounts += (ss.getPackCount() == null ? 0 : ss
							.getPackCount());
					// + (sss.getPackCount() == null ? 0 : sss
					// .getPackCount());
					System.out
							.println("ss.getPackCount()=" + ss.getPackCount());
					// System.out.println("sss.getPackCount()="+sss.getPackCount());
					validDate = ss.getBsb().getValidDate() == null ? null : ss
							.getBsb().getValidDate();
				}
				System.out.println("packCounts=" + packCounts);
				System.out.println("grossWeight=" + grossWeight);
				System.out.println("netWeight=" + netWeight);
				sbNew.setGrossWeight(grossWeight);
				sbNew.setNetWeight(netWeight);
				sbNew.setPackCount(packCounts);
				sbNew.setValidDate(validDate);
				this.saveStorageBill(request, sbNew);
				for (int j = i * pageCount; j < (i + 1) * pageCount
						&& j < afterList.size(); j++) {
					if (inOutFlag.equals(BlsIOStockBillIOF.IMPORT)) {
						StorageBillAfter storageBillAfter = (StorageBillAfter) afterList
								.get(j);
						bsd = (BlsInOutStockBillDetail) rlist.get(j);
						BillToWareHouseRelations bthr = new BillToWareHouseRelations();
						bthr.setBsd(bsd);
						bthr.setSb(sbNew);
						bthr.setSbAfter(storageBillAfter);
						bthr = this.saveBillToWareHouseRelations(request, bthr);
						bsd.setIsStockBill(true);
						this.saveBlsInOutStockBillDetail(request, bsd);
					} else if (inOutFlag.equals(BlsIOStockBillIOF.EXPORT)) {
						BillToWareHouseRelationExp bthe = new BillToWareHouseRelationExp();
						bsd = (BlsInOutStockBillDetail) rlist.get(j);
						bthe.setSb(sbNew);
						bthe.setBsd(bsd);
						this.saveBillToWareHouseRelationExp(request, bthe);
						bsd.setIsStockBill(true);
						this.saveBlsInOutStockBillDetail(request, bsd);
					}

					if (sb.getIoFlag().equals(BlsIOStockBillIOF.EXPORT)) {

						StorageBillAfter afts = (StorageBillAfter) afterList
								.get(j);

						String inBillNo = afts.getBlsDocuments();

						System.out.println("inBillNo=" + inBillNo);

						int inBillGoodNo = afts.getInBillGoodNo();

						System.out.println("inBillGoodNo=" + inBillGoodNo);

						List bsds = this.findBlsInOutStockBillInByinBillNo(
								request, inBillNo, inBillGoodNo);
						System.out.println("bsds.size()=" + bsds.size());
						if (bsds.size() != 0) {
							BlsInOutStockBillDetail temp = (BlsInOutStockBillDetail) bsds
									.get(0);
							// try {
							BillToWareHouseRelations billToWareHouseRelations = (BillToWareHouseRelations) this
									.findBlsInOutStockBillDeInByBlsInOutStockBillDetail(
											request, temp).get(0);
							StorageBill storageBill = billToWareHouseRelations
									.getSb();
							String billNo = storageBill.getBillNo();
							int seqNo = billToWareHouseRelations.getSbAfter()
									.getSeqNo();
							afts.setCorrBillNo(billNo);
							afts.setCorrBillGNo(seqNo);
							this.blsInOutStockBillDao
									.saveStorageBillAfter(afts);
							// } catch (Exception e) {
							// throw new RuntimeException(
							// "您所选择的出仓单据对应的入仓单据未转成入仓单，不能继续转仓单");
							// }
						}

						List alist = findStorageBillAfter(request, sbNew);

						System.out.println("alist=" + alist.size());

						for (int a = j; a < alist.size(); a++) {
							StorageBillAfter aft = (StorageBillAfter) alist
									.get(a);
							System.out.println("aft=" + aft);
							// if (i != 0) {
							// aft.setStorageBill(sbNew);
							// }
							BlsInnerMerge inner = (BlsInnerMerge) bList.get(a);
							Materiel m = inner.getMateriel();
							Double detailQty = aft.getQty() == null ? 0.0 : aft
									.getQty();
							String factoryName = m.getFactoryName() == null ? ""
									: m.getFactoryName();
							StorageBillBefore be = new StorageBillBefore();
							be.setPartNo(m);
							be.setDetailQty(detailQty);
							be.setStorageBillAfter(aft);
							be.setPartNameC(factoryName);
							hasLists.add(be);
						}
						System.out
								.println("hasLists.size()=" + hasLists.size());
						list.add(hasLists);
						saveStorageBillAfterAndBefore(request, list);
					} else {
						this.blsInOutStockBillDao
								.saveStorageBillAfter((StorageBillAfter) afterList
										.get(j));
						List alist = findStorageBillAfter(request, sbNew);
						for (int a = j; a < alist.size(); a++) {
							StorageBillAfter aft = (StorageBillAfter) alist
									.get(a);
							// if (i != 0) {
							// aft.setStorageBill(sbNew);
							// }
							BlsInnerMerge inner = (BlsInnerMerge) bList.get(a);
							System.out.println("inner=" + inner);
							Materiel m = inner.getMateriel();
							String factoryName = m.getFactoryName() == null ? ""
									: m.getFactoryName();
							Double detailQty = aft.getQty() == null ? 0.0 : aft
									.getQty();
							StorageBillBefore be = new StorageBillBefore();
							be.setPartNo(m);
							be.setDetailQty(detailQty);
							be.setStorageBillAfter(aft);
							be.setPartNameC(factoryName);
							hasLists.add(be);
						}
						list.add(hasLists);
					}
					System.out.println("list.size()=" + list.size());
				}
				saveStorageBillAfterAndBefore(request, list);
			} else {
				if (sb.getIoFlag().equals(BlsIOStockBillIOF.EXPORT)) {
					List list3 = new ArrayList();
					double grossWeight = 0.0;
					double netWeight = 0.0;
					Integer packCounts = 0;
					WareSet ws = sb.getWareHouseCode();
					for (int j = i * pageCount; j < (i + 1) * pageCount
							&& j < afterList.size(); j++) {
						System.out.println("afterList.size()="
								+ afterList.size());
						System.out.println("this j =" + j);
						BlsInOutStockBillDetail ss = (BlsInOutStockBillDetail) rlist
								.get(j);
						grossWeight += (ss.getGrossWeight() == null ? 0.0 : ss
								.getGrossWeight());
						netWeight += (ss.getNetWeight() == null ? 0.0 : ss
								.getNetWeight());
						packCounts += (ss.getPackCount() == null ? 0 : ss
								.getPackCount());
					}
					System.out.println("packCounts=" + packCounts);
					System.out.println("grossWeight=" + grossWeight);
					System.out.println("netWeight=" + netWeight);
					sbNew.setPackCount(packCounts);
					sbNew.setBillNo(sb.getBillNo()
							+ CommonUtils.convertIntToStringByLength(i, 3));
					sbNew.setWrap(sb.getWrap());
					sbNew.setCustomsCode(sb.getCustomsCode());
					sbNew.setIePort(sb.getIePort());
					sbNew.setEmsNo(sb.getEmsNo());
					sbNew.setBillType(sb.getBillType());
					sbNew.setWareHouseCode(ws);
					sbNew.setIoFlag(sb.getIoFlag());
					sbNew.setGrossWeight(grossWeight);
					sbNew.setNetWeight(netWeight);
					int b = 0;
					this.saveStorageBill(request, sbNew);
					for (int j = i * pageCount; j < (i + 1) * pageCount
							&& j < afterList.size(); j++) {
						BillToWareHouseRelationExp bthe = new BillToWareHouseRelationExp();
						bsd = (BlsInOutStockBillDetail) rlist.get(j);
						bthe.setBsd(bsd);
						bthe.setSb(sbNew);
						this.saveBillToWareHouseRelationExp(request, bthe);
						bsd.setIsStockBill(true);
						this.saveBlsInOutStockBillDetail(request, bsd);
						b++;
						System.out
								.println("afterList.size=" + afterList.size());
						StorageBillAfter afts = (StorageBillAfter) afterList
								.get(j);
						afts.setSeqNo(b);
						String inBillNo = afts.getBlsDocuments();

						int inBillGoodNo = afts.getInBillGoodNo();
						System.out.println("inBillNo=" + inBillNo);

						List bsds = this.findBlsInOutStockBillInByinBillNo(
								request, inBillNo, inBillGoodNo);
						BlsInOutStockBillDetail temp = (BlsInOutStockBillDetail) bsds
								.get(0);

						System.out.println("temp=" + temp);

						BillToWareHouseRelations billToWareHouseRelations = (BillToWareHouseRelations) this
								.findBlsInOutStockBillDeInByBlsInOutStockBillDetail(
										request, temp).get(0);
						StorageBill storageBill = billToWareHouseRelations
								.getSb();
						String billNo = storageBill.getBillNo();
						afts.setCorrBillNo(billNo);
						Integer seqNo = billToWareHouseRelations.getSbAfter()
								.getSeqNo();
						afts.setCorrBillGNo(seqNo);
						this.blsInOutStockBillDao.saveStorageBillAfter(afts);
						List alist = findStorageBillAfter(request, sbNew);
						for (int a = j; a < alist.size(); a++) {
							StorageBillAfter aft = (StorageBillAfter) alist
									.get(a);
							// if (i != 0) {
							// aft.setStorageBill(sbNew);
							// }
							aft.setStorageBill(sbNew);
							BlsInnerMerge inner = (BlsInnerMerge) bList.get(a);
							System.out.println("inner=" + inner);
							Materiel m = inner.getMateriel();
							String factoryName = m.getFactoryName() == null ? ""
									: m.getFactoryName();
							StorageBillBefore be = new StorageBillBefore();
							be.setPartNo(m);
							be.setStorageBillAfter(aft);
							be.setPartNameC(factoryName);
							hasLists.add(be);
						}
						list3.add(hasLists);
					}
					saveStorageBillAfterAndBefore(request, list3);
				}
				List list2 = new ArrayList();
				List hasLists2 = new ArrayList();
				double grossWeight = 0.0;
				double netWeight = 0.0;
				Integer packCounts = 0;
				Date validDate = null;
				WareSet ws = sb.getWareHouseCode();
				for (int j = i * pageCount; j < (i + 1) * pageCount
						&& j < afterList.size(); j++) {
					BlsInOutStockBillDetail ss = (BlsInOutStockBillDetail) rlist
							.get(j);
					grossWeight += (ss.getGrossWeight() == null ? 0.0 : ss
							.getGrossWeight());
					netWeight += (ss.getNetWeight() == null ? 0.0 : ss
							.getNetWeight());
					packCounts += (ss.getPackCount() == null ? 0 : ss
							.getPackCount());
					validDate = ss.getBsb().getValidDate() == null ? null : ss
							.getBsb().getValidDate();
				}
				sbNew.setWrap(sb.getWrap());
				sbNew.setIePort(sb.getIePort());
				sbNew.setEmsNo(sb.getEmsNo());
				sbNew.setCustomsCode(sb.getCustomsCode());
				sbNew.setBillType(sb.getBillType());
				sbNew.setPackCount(packCounts);
				sbNew.setWareHouseCode(ws);
				sbNew.setGrossWeight(grossWeight);
				sbNew.setNetWeight(netWeight);
				sbNew.setValidDate(validDate);
				sbNew.setBillNo(sb.getBillNo()
						+ CommonUtils.convertIntToStringByLength(i, 3));
				sbNew.setIoFlag(sb.getIoFlag());
				this.saveStorageBill(request, sbNew);
				int c = 0;
				for (int j = i * pageCount; j < (i + 1) * pageCount
						&& j < afterList.size(); j++) {
					c++;
					System.out.println("j=" + j);
					this.blsInOutStockBillDao
							.saveStorageBillAfter((StorageBillAfter) afterList
									.get(j));
					if (inOutFlag.equals(BlsIOStockBillIOF.IMPORT)) {
						bsd = (BlsInOutStockBillDetail) rlist.get(j);
						StorageBillAfter storageBillAfter = (StorageBillAfter) afterList
								.get(j);
						BillToWareHouseRelations bthr = new BillToWareHouseRelations();
						bthr.setBsd(bsd);
						bthr.setSb(sbNew);
						bthr.setSbAfter(storageBillAfter);
						bthr = this.saveBillToWareHouseRelations(request, bthr);
						bsd.setIsStockBill(true);
						this.saveBlsInOutStockBillDetail(request, bsd);
					}
					StorageBillAfter aft = (StorageBillAfter) afterList.get(j);
					aft.setSeqNo(c);
					aft.setStorageBill(sbNew);
					List alist = findStorageBillAfter(request, sbNew);
					BlsInnerMerge inner = (BlsInnerMerge) bList.get(j);
					System.out.println("inner=" + inner);
					Materiel m = inner.getMateriel();
					String factoryName = m.getFactoryName() == null ? "" : m
							.getFactoryName();
					Double detailQty = aft.getQty() == null ? 0.0 : aft
							.getQty();
					StorageBillBefore be = new StorageBillBefore();
					be.setPartNo(m);
					be.setStorageBillAfter(aft);
					be.setPartNameC(factoryName);
					be.setDetailQty(detailQty);
					hasLists2.add(be);
					list2.add(hasLists2);
				}
				saveStorageBillAfterAndBefore(request, list2);
			}
		}
	}

	/**
	 * 加入归并前的进出仓商品信息拆分
	 * 
	 * @param request
	 * @param bList
	 * @param sb
	 */
	@AuthorityFunctionAnnotation(caption = "加入归并前的进出仓商品信息拆分", index = 30)
	@SuppressWarnings("unchecked")
	public void addStorageBillBefore(Request request, List bList,
			StorageBill sb, List afterList) {
		if (sb == null) {
			sb = new StorageBill();
		}
		List hasLists = new ArrayList();
		List list2 = new ArrayList();

		for (int i = 0; i < afterList.size(); i++) {
			if (i != 0 && i % 20 == 0 && i < 2 * i) {
				System.out.println("i=" + i);

				System.out.println("2*i=" + 2 * i);
				// 超过二十项就拆分到另一个新的进仓单内
				try {
					// sb = (StorageBill) BeanUtils.cloneBean(sb);
					StorageBill sbNew = new StorageBill();
					sbNew.setBillNo(sb.getBillNo());
					sbNew.setIoFlag(sb.getIoFlag());
					// sb.setId(null);
					this.saveStorageBill(request, sbNew);
					System.out.println("sb=" + sb);
					List alist = findStorageBillAfter(request, sbNew);
					for (int b = i; b < 2 * i; b++) {
						System.out.println("b=" + b);
						StorageBillAfter aft = (StorageBillAfter) afterList
								.get(b);
						aft.setStorageBill(sbNew);
						BlsInnerMerge inner = (BlsInnerMerge) bList.get(i);
						System.out.println("inner=" + inner);
						Materiel m = inner.getMateriel();
						String factoryName = m.getFactoryName() == null ? ""
								: m.getFactoryName();
						StorageBillBefore be = new StorageBillBefore();
						be.setPartNo(m);
						be.setStorageBillAfter(aft);
						be.setPartNameC(factoryName);
						hasLists.add(be);
						list2.add(hasLists);
					}
					saveStorageBillAfterAndBefore(request, list2);
				} catch (Exception e) {
					// throw new RuntimeException(e.getMessage());
					e.printStackTrace();
				}
			} else {
				this.blsInOutStockBillDao
						.saveStorageBillAfter((StorageBillAfter) afterList
								.get(i));
				System.out.println("i=" + i);
				List alist = findStorageBillAfter(request, sb);
				if (alist.size() == 0) {
					StorageBill sbNew = new StorageBill();
					sbNew.setIoFlag(sb.getIoFlag());
					this.saveStorageBill(request, sbNew);
					StorageBillAfter aft = (StorageBillAfter) afterList.get(i);
					aft.setStorageBill(sbNew);
				}
				System.out.println("alist.size()=" + alist.size());
				int a = 0;
				List list = new ArrayList();
				for (int j = i; j < alist.size(); j++) {
					a++;
					System.out.println("a=" + a);
					if (afterList.size() > 20 && i <= 19) {
						System.out.println("this i=" + i);
						StorageBillAfter aft = (StorageBillAfter) alist.get(j);
						BlsInnerMerge inner = (BlsInnerMerge) bList.get(j);
						System.out.println("inner=" + inner);
						Materiel m = inner.getMateriel();
						String factoryName = m.getFactoryName() == null ? ""
								: m.getFactoryName();
						StorageBillBefore be = new StorageBillBefore();
						be.setPartNo(m);
						be.setStorageBillAfter(aft);
						be.setPartNameC(factoryName);
						hasLists.add(be);
						System.out.println("this hasLists.size()="
								+ hasLists.size());
					} else {
						StorageBillAfter aft = (StorageBillAfter) alist.get(j);
						BlsInnerMerge inner = (BlsInnerMerge) bList.get(j);
						System.out.println("inner=" + inner);
						Materiel m = inner.getMateriel();
						String factoryName = m.getFactoryName() == null ? ""
								: m.getFactoryName();
						StorageBillBefore be = new StorageBillBefore();
						be.setPartNo(m);
						be.setStorageBillAfter(aft);
						be.setPartNameC(factoryName);
						hasLists.add(be);
						System.out
								.println("hasLists.size()=" + hasLists.size());
						list.add(hasLists);
						saveStorageBillAfterAndBefore(request, list);
					}
				}
			}
		}
		List saveList = new ArrayList();
		saveList.add(hasLists);
		List clist = saveStorageBillAfterAndBefore(request, saveList);
		this.blsInOutStockBillLogic.saveOrUpdateObjects(afterList);
	}

	// Map mapss = new HashMap();
	// List alist = findStorageBillAfter(request, sb);
	// List hasLists = new ArrayList();
	// for (int a = 0; a < alist.size(); a++) {
	// StorageBillAfter aft = (StorageBillAfter) alist.get(a);
	// BlsInnerMerge inner = (BlsInnerMerge) bList.get(a);
	// System.out.println("inner=" + inner);
	// Materiel m = inner.getMateriel();
	// String factoryName = m.getFactoryName() == null ? "" : m
	// .getFactoryName();
	//
	// StorageBillBefore be = new StorageBillBefore();
	// be.setPartNo(m);
	// be.setStorageBillAfter(aft);
	// be.setPartNameC(factoryName);
	// hasLists.add(be);
	// }
	// List saveList = new ArrayList();
	// saveList.add(hasLists);
	// // saveList.add(headList);
	// // saveList.add(nomap);
	// System.out.println("saveList.size()=" + saveList.size());
	// List clist = saveStorageBillAfterAndBefore(request, saveList);
	// if (bList == null) {
	// return;
	// } else {//
	// Map<String, BlsTenInnerMerge> map = new HashMap();
	// Integer max = findMaxStorageBillAfterSeqNo(request, sb);
	// System.out.println("max=" + max);
	// if (max + bList.size() > 20) {
	// throw new RuntimeException("该仓单已经走过20项商品!");
	// // JOptionPane.showMessageDialog(DgBaseStorage.this,
	// // "该仓单已经走过20项商品，请重新选择！", "提示！",
	// // JOptionPane.WARNING_MESSAGE);
	// // return;
	// }
	// Map nomap = new HashMap();
	// List hasList = new ArrayList();
	// List headList = new ArrayList();
	// for (int b = 0; b < bList.size(); b++) {
	// BlsInnerMerge inner = (BlsInnerMerge) bList.get(b);
	// System.out.println("inner=" + inner);
	// Materiel m = inner.getMateriel();
	// String factoryName = m.getFactoryName() == null ? "" : m
	// .getFactoryName();
	// System.out.println("m=" + m);
	// if (m == null) {
	// continue;
	// }
	// BlsTenInnerMerge teninner = inner.getBlsTenInnerMerge();
	// System.out.println("teninner=" + teninner);
	// // String keystrs = teninner.getComplex().getCode() + "/"
	// // + teninner.getSeqNum().toString() + "/"
	// // + teninner.getCountry().getName();
	// // System.out.println("keystr=" + keystrs);
	// if (teninner == null || teninner.getComplex() == null) {
	// continue;
	// }
	// if (teninner.getSeqNum() == null) {
	// continue;
	// }
	// String keystr = teninner.getComplex().getCode() + "/"
	// + teninner.getSeqNum().toString();
	//
	// System.out.println("keystr=" + keystr);
	// StorageBillAfter afts = (StorageBillAfter) mapss.get(keystr);
	// // afts.setStorageBill(storageBill);
	// System.out.println("afts=" + afts);
	// if (afts != null) {
	// System.out.println("old -----------------");
	// List arlist = storageBillAction
	// .findStorageBillBeforeForDelivery(new Request(
	// CommonVars.getCurrUser()), afts);
	// StorageBillBefore be = new StorageBillBefore();
	// be.setPartNo(m);
	// be.setStorageBillAfter(afts);
	// be.setPartNameC(factoryName);
	// hasList.add(be);
	// } else {
	// List ttlist = (List) nomap.get(keystr);
	// if (ttlist == null) {
	// ttlist = new ArrayList();
	// afts = new StorageBillAfter();
	// afts
	// .setCodeTS(inner.getBlsTenInnerMerge()
	// .getComplex());
	// afts.setStorageBill(sb);
	// afts.setCodeTS(teninner.getComplex());
	// afts.setName(teninner.getName());
	// afts.setModel(teninner.getSpec());
	// afts.setSeqNum(teninner.getSeqNum());
	// ++max;
	// afts.setSeqNo(max);
	// StorageBillBefore bef = new StorageBillBefore();
	// bef.setPartNo(m);
	// System.out.println("bef=" + bef);
	// ttlist.add(bef);
	// headList.add(afts);
	// nomap.put(keystr, ttlist);
	// } else {
	// StorageBillBefore bef = new StorageBillBefore();
	// bef.setPartNo(m);
	// ttlist.add(bef);
	// }
	// }
	// }
	// if (aft.getCodeTS() == null) {
	// continue;
	// }
	// if (aft.getSeqNum() == null) {
	// continue;
	// }
	// String str = aft.getCodeTS().getCode() + "/"
	// + aft.getSeqNum().toString();
	//
	// System.out.println("str=" + str);
	//
	// System.out.println("name=" + aft.getName());
	// // System.out.println("curr.name="+aft.getCurr().getName());
	// mapss.put(str, aft);
	// List saveList = new ArrayList();
	// saveList.add(hasLists);
	// saveList.add(headList);
	// saveList.add(nomap);
	// System.out.println("saveList.size()="+saveList.size());
	// List clist = saveStorageBillAfterAndBefore(request,
	// saveList);
	// System.out.println("clist.size()="+clist.size());
	// refreshTable();

	// }
	/**
	 * 加入归并前的进出仓商品信息
	 * 
	 * @param request
	 * @param bList
	 * @param storageBill
	 */
	@AuthorityFunctionAnnotation(caption = "加入归并前的进出仓商品信息", index = 31)
	public void addStorageBillBefores(Request request, List bList,
			StorageBill storageBill) {
		if (storageBill == null) {
			storageBill = new StorageBill();
		}

		System.out.println("storageBill=" + storageBill);

		this.blsInOutStockBillDao.saveStorageBill(storageBill);

		Map mapss = new HashMap();

		System.out.println("bList.size()=" + bList.size());

		List alist = findStorageBillAfter(request, storageBill);

		System.out.println("alist.size()=" + alist.size());

		for (int i = 0; i < alist.size(); i++) {
			StorageBillAfter aft = (StorageBillAfter) alist.get(i);
			if (aft.getCodeTS() == null) {
				continue;
			}
			if (aft.getSeqNum() == null) {
				continue;
			}
			String str = aft.getCodeTS().getCode() + "/"
					+ aft.getSeqNum().toString();

			System.out.println("str=" + str);
			mapss.put(str, aft);
		}
		if (bList == null) {
			return;
		} else {// 
			Map<String, BlsTenInnerMerge> map = new HashMap();
			Integer max = findMaxStorageBillAfterSeqNo(request, storageBill);
			if (max == null) {
				max = 1;
			}
			System.out.println("max=" + max);
			if (max + bList.size() > 20) {
				throw new RuntimeException("该仓单已经走过20项商品!");
				// JOptionPane.showMessageDialog(DgBaseStorage.this,
				// "该仓单已经走过20项商品，请重新选择！", "提示！",
				// JOptionPane.WARNING_MESSAGE);
				// return;
			}
			Map nomap = new HashMap();
			List hasList = new ArrayList();
			List headList = new ArrayList();
			for (int i = 0; i < bList.size(); i++) {
				BlsInnerMerge inner = (BlsInnerMerge) bList.get(i);
				System.out.println("inner=" + inner);
				Materiel m = inner.getMateriel();
				System.out.println("m=" + m);
				if (m == null) {
					continue;
				}
				BlsTenInnerMerge teninner = inner.getBlsTenInnerMerge();
				System.out.println("teninner=" + teninner);
				if (teninner == null || teninner.getComplex() == null) {
					continue;
				}
				if (teninner.getSeqNum() == null) {
					continue;
				}
				String keystr = teninner.getComplex().getCode() + "/"
						+ teninner.getSeqNum().toString();
				StorageBillAfter afts = (StorageBillAfter) mapss.get(keystr);
				// afts.setStorageBill(storageBill);
				System.out.println("afts=" + afts);
				if (afts != null) {
					System.out.println("old -----------------");
					// List arlist = storageBillAction
					// .findStorageBillBeforeForDelivery(new Request(
					// CommonVars.getCurrUser()), afts);
					StorageBillBefore be = new StorageBillBefore();
					be.setPartNo(m);
					be.setStorageBillAfter(afts);
					hasList.add(be);
				} else {
					List ttlist = (List) nomap.get(keystr);
					if (ttlist == null) {
						ttlist = new ArrayList();
						afts = new StorageBillAfter();
						afts
								.setCodeTS(inner.getBlsTenInnerMerge()
										.getComplex());
						afts.setStorageBill(storageBill);
						afts.setCodeTS(teninner.getComplex());
						afts.setName(teninner.getName());
						afts.setModel(teninner.getSpec());
						afts.setSeqNum(teninner.getSeqNum());
						++max;
						afts.setSeqNo(max);
						StorageBillBefore bef = new StorageBillBefore();
						bef.setPartNo(m);
						System.out.println("bef=" + bef);
						ttlist.add(bef);
						headList.add(afts);
						nomap.put(keystr, ttlist);
					} else {
						StorageBillBefore bef = new StorageBillBefore();
						bef.setPartNo(m);
						ttlist.add(bef);
					}
				}
			}

			List saveList = new ArrayList();
			saveList.add(hasList);
			saveList.add(headList);
			saveList.add(nomap);
			List clist = saveStorageBillAfterAndBefore(request, saveList);
			// refreshTable();
		}

	}

	/**
	 * 根据料号找出相应的归并信息
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findBlsInnerMerge(Request request, Materiel materiel, int seqNum) {
		return this.blsInOutStockBillDao.findBlsInnerMerge(materiel, seqNum);
	}

	/**
	 * 保存进出仓单据表头及表体,文本导入时使用.
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-保存", index = 1)
	public List saveInOutStockBillAndImpExpCommodityInfo(Request request,
			List list, boolean isMergeCommInfo) {
		return this.blsInOutStockBillLogic
				.saveInOutStockBillAndImpExpCommodityInfo(list, isMergeCommInfo);
	}

	/**
	 * 删除进出仓单据表头表体信息
	 * 
	 * @param blsInOutStockBill
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-删除", index = 7)
	public void deleteBlsInOutStockBillHeadAndBody(Request request,
			BlsInOutStockBill blsInOutStockBill) {
		this.blsInOutStockBillLogic
				.deleteBlsInOutStockBillHeadAndBody(blsInOutStockBill);
	}

	/**
	 * 批量删除出入仓单据表体信息
	 */
	@AuthorityFunctionAnnotation(caption = "批量删除出入仓单据表体信息", index = 35)
	public void deleteBlsInOutStockBillDetails(Request request,
			List blsInOutStockBillDetail) {
		this.blsInOutStockBillDao
				.deleteBlsInOutStockBillDetails(blsInOutStockBillDetail);
	}

	/**
	 * 查找出所有的单据号
	 * 
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findBlsInOutBillBillNo(Request request) {
		return this.blsInOutStockBillDao.findBlsInOutBillBillNo();
	}

	/**
	 * 通过起始时间和结束时间找到相应的出入仓单据头
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findBlsInOutStockBillInByDate(Request request, String iOFlag,
			Date beginDate, Date endDate) {
		return this.blsInOutStockBillDao.findBlsInOutStockBillInByDate(iOFlag,
				beginDate, endDate);
	}

	/**
	 * 查询出仓单据里的没有对应转入单据商品，或不存在的对应转入单据商品信息
	 * 
	 * @param request
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findBlsOutStockBillDetailNoInputBill(Request request) {
		return this.blsInOutStockBillLogic
				.findBlsOutStockBillDetailNoInputBill();
	}

	/**
	 * 查询出仓单据商品停息
	 * 
	 * @param warehouseName商品名称
	 * @param spec商品规格
	 * @param scmCoc客户
	 * @param beginDate开始日期
	 * @param endDate结束日期
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findBlsOutStockBillDetail(Request request,
			String warehouseName, String spec, ScmCoc scmCoc, Date beginDate,
			Date endDate) {
		return this.blsInOutStockBillLogic.findBlsOutStockBillDetail(
				warehouseName, spec, scmCoc, beginDate, endDate);
	}

	/**
	 * 查询入仓单据商品停息
	 * 
	 * @param warehouseName商品名称
	 * @param spec商品规格
	 * @param scmCoc客户
	 * @param beginDate开始日期
	 * @param endDate结束日期
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findBlsInStockBillDetail(Request request, String warehouseName,
			String spec, ScmCoc scmCoc, Date beginDate, Date endDate) {
		return this.blsInOutStockBillDao.findBlsInStockBillDetail(
				warehouseName, spec, scmCoc, beginDate, endDate);
	}

	/**
	 * 查询单据对应信息
	 * 
	 * @param matchName商品名称
	 * @param matchspec商品规格
	 * @param customerName客户
	 * @param beginDate开始日期
	 * @param endDate结束日期
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findBillMatchInfo(Request request, String matchName,
			String matchspec, String customerName, Date beginDate, Date endDate) {
		return this.blsInOutStockBillDao.findBillMatchInfo(matchName,
				matchspec, customerName, beginDate, endDate);
	}

	/**
	 * 新建或更新单据对应信息
	 * 
	 * @param matchInfo
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "新建或更新单据对应信息", index = 42)
	public BillMatchInfo saveBillMatchInfo(Request request,
			BillMatchInfo billMatchInfo) {
		this.blsInOutStockBillDao.saveBillMatchInfo(billMatchInfo);
		return billMatchInfo;
	}

	/**
	 * 新建或更新单据对应信息
	 * 
	 * @param matchInfo
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "新建或更新单据对应信息", index = 43)
	public List<BillMatchInfo> saveBillMatchInfo(Request request,
			List<BillMatchInfo> billMatchInfos) {
		this.blsInOutStockBillLogic.saveBillMatchInfo(billMatchInfos);
		return billMatchInfos;
	}

	/**
	 * 根据对应信息查询对应的出仓单据
	 * 
	 * @param matchInfo
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public BlsInOutStockBillDetail findBlsOutStockBillDetailByMatchInfo(
			Request request, BillMatchInfo matchInfo) {
		return this.blsInOutStockBillDao
				.findBlsOutStockBillDetailByMatchInfo(matchInfo);
	}

	/**
	 * 根据对应信息查询对应的入仓单据
	 * 
	 * @param matchInfo
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public BlsInOutStockBillDetail findBlsInStockBillDetailByMatchInfo(
			Request request, BillMatchInfo matchInfo) {
		return this.blsInOutStockBillDao
				.findBlsInStockBillDetailByMatchInfo(matchInfo);
	}

	/**
	 * 删除对应关系
	 * 
	 * @param matchInfo
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-删除", index = 7)
	public void deletesaveBillMatchInfo(BillMatchInfo matchInfo) {
		this.blsInOutStockBillDao.deletesaveBillMatchInfo(matchInfo);
	}

	/**
	 * 根据单据编号查找进仓单据
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findBlsInOutStockBillInByBillNo(Request request, String billNo) {
		return this.blsInOutStockBillDao
				.findBlsInOutStockBillInByBillNo(billNo);
	}

	/**
	 * 查找仓单号归并后最大流水号
	 * 
	 * @param storageBill
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public Integer findMaxBlsInOutStockBillSeqNos(Request request,
			BlsInOutStockBill blsInOutStockBill) {
		return this.blsInOutStockBillDao
				.findMaxBlsInOutStockBillSeqNos(blsInOutStockBill);
	}

	/**
	 * 通过进仓单的单据编号和进仓单表体中的商品的商品流水号抓出所对应商品的总数量
	 * 
	 * @param inBillNos
	 * @param inBillGoodNo
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public Double findDetailQtyByBlsInStockBillNos(Request request,
			String inBillNos, int inBillGoodNo) {
		return this.blsInOutStockBillDao.findDetailQtyByBlsInStockBillNos(
				inBillNos, inBillGoodNo);
	}

	/**
	 * 根据期初单据查询对应商品对应数量共和
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public Double findFirstBillMatchQty(Request request,
			BlsInOutStockBillDetail firstBill) {
		return this.blsInOutStockBillDao.findFirstBillMatchQty(firstBill);
	}

	/**
	 * 保存，更新单据对应参数
	 * 
	 * @param set
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-保存", index = 1)
	public BillMatchSet saveBillMatchSet(Request request, BillMatchSet set) {
		this.blsInOutStockBillDao.saveOrUpdate(set);
		return set;
	}

	/**
	 * 查询参数
	 * 
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public BillMatchSet findBillMatchSet(Request request) {
		return blsInOutStockBillDao.findBillMatchSet();
	}

	/**
	 * 通过料号查找出归并关系中对应的报关级商品信息
	 * 
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findBlsTenInnerMergeByMateriel(Request request,
			Materiel materiel) {
		return this.blsInOutStockBillDao
				.findBlsTenInnerMergeByMateriel(materiel);
	}

	/**
	 * 保存仓单归并后信息
	 */
	@AuthorityFunctionAnnotation(caption = "保存仓单归并后信息", index = 54)
	public StorageBillAfter saveStorageBillAfter(Request request,
			StorageBillAfter storageBillAfter) {
		this.blsInOutStockBillDao.saveStorageBillAfter(storageBillAfter);
		return storageBillAfter;
	}

	/**
	 * 保存仓单头信息
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-保存", index = 1)
	public StorageBill saveStorageBill(Request request, StorageBill storageBill) {
		this.blsInOutStockBillDao.saveStorageBill(storageBill);
		return storageBill;
	}

	/**
	 * 归并料号相同的商品信息数量毛重净重已经合并原产国
	 */
	@AuthorityFunctionAnnotation(caption = "归并料号相同的商品信息数量毛重净重已经合并原产国", index = 56)
	public List mergerBlsInOutStockCommodityInfo(Request request, List list,
			boolean isImp, boolean isExp) {
		return this.blsInOutStockBillLogic.mergerBlsInOutStockCommodityInfo(
				list, isImp, isExp);
	}

	/**
	 * 保存入仓单据与入仓单的对应关系
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-保存", index = 1)
	public BillToWareHouseRelations saveBillToWareHouseRelations(
			Request request, BillToWareHouseRelations bhr) {
		this.blsInOutStockBillDao.saveBillToWareHouseRelations(bhr);
		return bhr;
	}

	/**
	 * 保存出仓单据与出仓单的对应关系
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-保存", index = 1)
	public void saveBillToWareHouseRelationExp(Request request,
			BillToWareHouseRelationExp bhre) {
		this.blsInOutStockBillDao.saveBillToWareHouseRelationExp(bhre);
	}

	/**
	 * 根据进仓单据编号查找进仓单据
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findBlsInOutStockBillInByinBillNo(Request request,
			String inbillNo, Integer seqNo) {
		return this.blsInOutStockBillDao.findBlsInOutStockBillInByinBillNo(
				inbillNo, seqNo);
	}

	/**
	 * 根据入仓单据明细查找进入仓单号码
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findBlsInOutStockBillDeInByBlsInOutStockBillDetail(
			Request request, BlsInOutStockBillDetail bsd) {
		return this.blsInOutStockBillDao
				.findBlsInOutStockBillDeInByBlsInOutStockBillDetail(bsd);
	}

	/**
	 * 根据入仓单查找进入仓单号码
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findBillToWareHouseRelationsInByStockBill(Request request,
			StorageBill sb) {
		return this.blsInOutStockBillDao
				.findBillToWareHouseRelationsInByStockBill(sb);
	}

	/**
	 * 删除对应关系
	 * 
	 * @param matchInfo
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-删除", index = 7)
	public void deleteBillToWareHouseRelations(Request request,
			BillToWareHouseRelations billToWareHouseRelations) {
		this.blsInOutStockBillDao
				.deleteBillToWareHouseRelations(billToWareHouseRelations);
	}

	/**
	 * 根据入仓单查找进入仓单据表体明细
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findBlsInOutStockBillDetailByStockBill(Request request,
			StorageBill sb) {
		return this.blsInOutStockBillDao
				.findBlsInOutStockBillDetailByStockBill(sb);
	}

	/**
	 * 保存转仓单参数设置
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-保存", index = 1)
	public BlsInOutStockSwichParameterSet saveBlsSwitchParameterSet(
			Request request, BlsInOutStockSwichParameterSet bosp) {
		this.blsInOutStockBillDao.saveBlsSwitchParameterSet(bosp);
		return bosp;
	}

	/**
	 * 删除转仓单参数设置
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-删除", index = 7)
	public void deleteBlsSwitchParameterSet(Request request,
			BlsInOutStockSwichParameterSet bosp) {
		this.blsInOutStockBillDao.deleteBlsSwitchParameterSet(bosp);
	}

	/**
	 * 找出转仓单参数设置中所有的值
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findBlsSwitchParameterSet(Request request) {
		return this.blsInOutStockBillDao.findBlsSwitchParameterSet();
	}

	/**
	 * 根据进出仓类型查找参数设置
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public BlsInOutStockSwichParameterSet findBlsSwitchParameterSetByInOutFlag(
			Request request, String inOutFlag) {
		return this.blsInOutStockBillDao
				.findBlsSwitchParameterSetByInOutFlag(inOutFlag);
	}

	/**
	 * 根据出仓单查找进出仓单据表体明细
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findBlsInOutStockBillDetailByStockBills(Request request,
			StorageBill sb) {
		return this.blsInOutStockBillDao
				.findBlsInOutStockBillDetailByStockBills(sb);
	}

	/**
	 * 根据出仓单查找出仓单号码
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findBillToWareHouseRelationExpInByStockBill(Request request,
			StorageBill sb) {
		return this.blsInOutStockBillDao
				.findBillToWareHouseRelationExpInByStockBill(sb);
	}

	/**
	 * 删除出仓对应关系
	 * 
	 * @param matchInfo
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-删除", index = 7)
	public void deleteBillToWareHouseRelationExp(Request request,
			BillToWareHouseRelationExp billToWareHouseRelationExp) {
		this.blsInOutStockBillDao
				.deleteBillToWareHouseRelationExp(billToWareHouseRelationExp);
	}

	/**
	 * 根据入仓单据编号和入仓单据商品流水号查找进仓单据
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findBlsInOutStockBillInByBillNoAndSeqNo(Request request,
			String billNo, Integer seqNo) {
		return this.blsInOutStockBillDao
				.findBlsInOutStockBillInByBillNoAndSeqNo(billNo, seqNo);
	}

	/**
	 * 单据对应
	 * 
	 * @param request
	 * @param outBills
	 * @param firstBills
	 */
	@AuthorityFunctionAnnotation(caption = "单据对应", index = 72)
	public void matchInAndOutBill(Request request,
			List<BlsInOutStockBillDetail> outBills,
			List<BlsInOutStockBillDetail> firstBills) {
		this.blsInOutStockBillLogic.matchInAndOutBill(outBills, firstBills);
	}

	/**
	 * 查找出仓当中所有账册编号
	 */
	@AuthorityFunctionAnnotation(caption = "进出仓单据-查询", index = 2)
	public List findAllEmsNo(Request request) {
		return this.blsInOutStockBillDao.findAllEmsNo();
	}
}
