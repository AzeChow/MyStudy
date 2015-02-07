package com.bestway.bls.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bestway.bls.entity.BillMatchInfo;
import com.bestway.bls.entity.BillMatchSet;
import com.bestway.bls.entity.BillToWareHouseRelationExp;
import com.bestway.bls.entity.BillToWareHouseRelations;
import com.bestway.bls.entity.BlsIOStockBillIOF;
import com.bestway.bls.entity.BlsInOutStockBill;
import com.bestway.bls.entity.BlsInOutStockBillDetail;
import com.bestway.bls.entity.BlsInOutStockSwichParameterSet;
import com.bestway.bls.entity.StorageBill;
import com.bestway.bls.entity.StorageBillAfter;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * 进出仓单据DAO类
 * 
 * @author hw
 * 
 */
public class BlsInOutStockBillDao extends BaseDao {
	/**
	 * 保存进出仓单数据头
	 * 
	 * @param blsInOutStockBill
	 *            进出仓单据头
	 */
	public void saveBlsInOutStockBill(BlsInOutStockBill blsInOutStockBill) {
		this.saveOrUpdate(blsInOutStockBill);
	}

	/**
	 * 查找进出仓单据
	 */
	public List findBlsInOutStockBill() {
		return this.find(
				"select a from BlsInOutStockBill a where a.company.id=?",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找进仓单据
	 */
	public List findBlsInOutStockBillIn() {
		return this
				.find(
						"select a from BlsInOutStockBill a where a.company.id=? and a.ioFlag=?",
						new Object[] { CommonUtils.getCompany().getId(),
								BlsIOStockBillIOF.IMPORT });
	}

	/**
	 * 根据当前日期查找进仓单据
	 */
	public List findBlsInOutStockBillInByCreateDate(Date date) {
		return this
				.find(
						"select a from BlsInOutStockBill a where a.company.id=? and a.ioFlag=? and (a.createDate>=?  or a.createDate is null)",
						new Object[] { CommonUtils.getCompany().getId(),
								BlsIOStockBillIOF.IMPORT, date });
	}

	/**
	 * 查找出仓单据
	 */
	public List findBlsInOutStockBillOut() {
		return this
				.find(
						"select a from BlsInOutStockBill a where a.company.id=? and a.ioFlag=?",
						new Object[] { CommonUtils.getCompany().getId(),
								BlsIOStockBillIOF.EXPORT });
	}

	/**
	 * 根据当前日期查找出仓单据
	 */
	public List findBlsInOutStockBillOutByCreateDate(Date date) {
		return this
				.find(
						"select a from BlsInOutStockBill a where a.company.id=? and a.ioFlag=? and (a.createDate>=?  or a.createDate is null)",
						new Object[] { CommonUtils.getCompany().getId(),
								BlsIOStockBillIOF.EXPORT, date });
	}

	/**
	 * 通过起始时间和结束时间找到相应的出入仓单据头
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List findBlsInOutStockBillInByDate(String iOFlag, Date beginDate,
			Date endDate) {
		String hsql = "select a from BlsInOutStockBill a where a.company.id=? and a.ioFlag=?";
		List<Object> para = new ArrayList<Object>();
		para.add(CommonUtils.getCompany().getId());
		para.add(iOFlag);
		if (beginDate != null) {
			hsql += " and (a.createDate>=?  or a.createDate is null) ";
			para.add(beginDate);
		}

		if (endDate != null) {
			hsql += " and (a.createDate<=?  or a.createDate is null) ";
			para.add(endDate);
		}
		return this.find(hsql, para.toArray());
	}

	/**
	 * 删除进出仓单据
	 * 
	 * @param data
	 */
	public void deleteBlsInOutStockBill(BlsInOutStockBill blsInOutStockBill) {
		this.delete(blsInOutStockBill);
	}

	/**
	 * 找出仓库编码
	 * 
	 * @param deliveryNo
	 * @return
	 */
	public List findBillNo(String billNo) {
		List para = new ArrayList();
		String hsql = " select a from BlsInOutStockBill  a  where a.company.id =? ";
		para.add(CommonUtils.getCompany().getId());
		if (billNo != null && !billNo.equals("")) {
			hsql += " and  a.billNo =?  ";
			para.add(billNo.trim());
		}
		return this.find(hsql, para.toArray());
	}

	/**
	 * 抓出归并前物料所有数据
	 * 
	 * @return
	 */
	public List findMateriel() {
		return this.find(
				"select a.materiel from BlsInnerMerge a where a.company.id= ?",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 保存进出仓单表体
	 * 
	 * @param blsInOutStockBill
	 *            进出仓单据表体
	 */
	public void saveBlsInOutStockBillDetail(
			BlsInOutStockBillDetail blsInOutStockBillDetail) {

		// System.out.println("更新表体......");
		// BlsInOutStockBillDetail b = (BlsInOutStockBillDetail) this.load(
		// blsInOutStockBillDetail.getClass(), blsInOutStockBillDetail
		// .getId());
		// if (b != null) {
		// System.out.println("数据中存在");
		// System.out.println(b.getId());
		// System.out.println(b.getInBillNo());
		// System.out.println(b.getSeqNo());
		if (blsInOutStockBillDetail.getId() == null) {
			blsInOutStockBillDetail.setNowDetailQty(blsInOutStockBillDetail
					.getDetailQty());
		}
		// System.out.println(blsInOutStockBillDetail.getId());
		// System.out.println(blsInOutStockBillDetail.getInBillNo());
		// System.out.println(blsInOutStockBillDetail.getSeqNo());

		this.saveOrUpdate(blsInOutStockBillDetail);

	}

	/**
	 * 保存进出仓单表体
	 * 
	 * @param list
	 *            进出仓单据表体
	 */
	public void saveBlsInOutStockBillDetails(List list) {
		for (int i = 0; i < list.size(); i++) {
			BlsInOutStockBillDetail blsInOutStockBillDetail = (BlsInOutStockBillDetail) list
					.get(i);
			this.saveOrUpdate(blsInOutStockBillDetail);
		}
	}

	/**
	 * 根据单据头抓出对应单据体信息
	 */
	public List findBlsInOutStockBillDetail(String id) {
		List para = new ArrayList();
		para.add(id);
		return this.find(
				"select a from BlsInOutStockBillDetail a where a.bsb.id= ?",
				para.toArray());
	}

	/**
	 * 根据单据头抓出对应单据体(当单据体没被转过仓单)信息
	 */
	public List findBlsInOutStockBillDetailNot(String id) {
		List para = new ArrayList();
		para.add(id);
		para.add(new Boolean(false));
		return this.find(
				"select a from BlsInOutStockBillDetail a where a.bsb.id= ?"
						+ " and (a.isStockBill=? or a.isStockBill=null)", para
						.toArray());
	}

	/**
	 * 删除出入仓单据表体信息
	 */
	public void deleteBlsInOutStockBillDetail(
			BlsInOutStockBillDetail blsInOutStockBillDetail) {
		this.delete(blsInOutStockBillDetail);
	}

	/**
	 * 批量删除出入仓单据表体信息
	 */
	public void deleteBlsInOutStockBillDetails(List blsInOutStockBillDetail) {
		this.deleteAll(blsInOutStockBillDetail);
	}

	/**
	 * 抓出现有所有的客户供应商
	 */
	public List findAllCorrOwner() {
		return this
				.find(
						"select a.corrOwner from BlsInOutStockBill a where a.company.id= ? order by a.corrOwner.code",
						new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 抓出数据库中所有的单据编号
	 */
	public List findAllBillNo() {
		return this
				.find(
						"select a.billNo from BlsInOutStockBill a where a.company.id= ?",
						new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据单据编号客户供应商及录入日期查询出入仓单局表头
	 */
	public List findBlsInOutStockBillBySomeCondition(ScmCoc corrOwner,
			String billNo, Date beginDate, Date endDate) {
		String hsql = "select a from BlsInOutStockBill a where a.company.id= ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		if (corrOwner != null) {
			hsql += " and a.corrOwner=? ";
			parameters.add(corrOwner);
		}
		if (billNo != null) {
			hsql += "and a.billNo=? ";
			parameters.add(billNo);
		}
		if (beginDate != null) {
			hsql += " and (a.createDate>=?  or a.createDate is null) ";
			parameters.add(beginDate);
		}
		if (endDate != null) {
			hsql += " and (a.createDate<=?  or a.createDate is null) ";
			parameters.add(endDate);
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找不存在物料与报关对应表的物料来自料件类型
	 * 
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
	public List findMaterielForBlsInnerMerge(int index,// String materielType,
			int length, String property, Object value, Boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = " select a from Materiel as a"
				+ " left join fetch a.complex " + " left join fetch a.scmCoc"
				+ " left join fetch a.calUnit" + " where a.company.id=? "; // a.scmCoi.coiProperty=?
		// and
		// +
		// " and (a.isUseInBlsInnerMerge is null or a.isUseInBlsInnerMerge = ? )";
		// hsql += " and a.id not in" ISUSEINBCSINNERMERGE
		// + "( select b.materiel.id from BlsInnerMerge b where b.materielType =
		// ? and b.company.id=?) ";
		// paramters.add(materielType);
		paramters.add(CommonUtils.getCompany().getId());
		// paramters.add(false);
		// paramters.add(CommonUtils.getCompany().getId());

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
		}
		hsql += " order by a.ptNo";
		return this.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 根据进出仓标志查找出已生效且其表体有未转成仓单的的出入仓单头
	 */
	public List findBlsInOutStockBillNotSwitch(String ioFlag) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select distinct b.bsb from BlsInOutStockBillDetail b where b.bsb.company.id= ?"
				+ " and b.bsb.isEffective=? and (b.isStockBill=? or b.isStockBill is null) and b.bsb.ioFlag= ?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Boolean(true));
		parameters.add(new Boolean(false));
		parameters.add(ioFlag);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 根据已查找出来的已生效仓单头查找出其未转成仓单的的出入仓体
	 */
	public List findBlsInOutStockBillsNot(List<BlsInOutStockBill> list) {

		String hsql = "select b from BlsInOutStockBillDetail b where b.bsb.company.id= ?"
				+ " and b.bsb.isEffective=? "
				+ "and b.bsb.id=? "
				+ "and (b.isStockBill=? or b.isStockBill is null)";
		// BlsInOutStockBill bsb = null;
		for (int i = 0; i < list.size(); i++) {
			List<Object> parameters = new ArrayList<Object>();
			BlsInOutStockBill bsb = (BlsInOutStockBill) list.get(i);
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(new Boolean(true));
			parameters.add(bsb.getId());
			parameters.add(new Boolean(false));
			List list2 = this.find(hsql, parameters.toArray());
			return list2;
		}
		return null;
	}

	/**
	 * 根据进出仓标志查找出仓单信息
	 * 
	 * @param inout
	 *            进出仓标志位
	 * @return 仓单信息
	 */
	public List findStorageBillByInOut(String ioFlag) {
		List<Object> para = new ArrayList<Object>();
		String hsql = " select a from StorageBill  a  where a.company.id =?"
				+ " and a.ioFlag=? and a.effective=?";
		para.add(CommonUtils.getCompany().getId());
		para.add(ioFlag);
		para.add(new Boolean(true));
		return this.find(hsql, para.toArray());
	}

	/**
	 * 根据仓单头抓取归并后的仓单体资料
	 * 
	 * @param storageBill
	 *            仓单头
	 * @return 归并后的仓单体资料
	 */
	public List findStorageBillAfter(StorageBill storageBill) {
		if (storageBill.getId() == null) {
			System.out.println("the  storageBill   id  is null !");
			return new ArrayList();
		}
		String hsql = " select a from StorageBillAfter a where  a.company.id =?  and a.storageBill= ? ";
		List para = new ArrayList();
		para.add(CommonUtils.getCompany().getId());
		para.add(storageBill);
		return this.find(hsql, para.toArray());
	}

	/**
	 * 获得最大的单据号来自出入仓单据表
	 * 
	 * @param billNo
	 *            单据号
	 * @return 与指定单据号匹配的单据中最大的单据号
	 */
	public List getMaxBillNoByType(String billNo) {
		return this.find(
				"select max(a.billNo) from BlsInOutStockBill as a where a.company.id=?"
						+ " and a.billNo like ? ", new Object[] {
						CommonUtils.getCompany().getId(), billNo + "%" });
	}

	/**
	 * 通过归并序号取得进出仓单中归并后的最大值
	 * 
	 * @param storageBill
	 * @return
	 */
	public Integer findMaxStorageBillAfterSeqNo(StorageBill storageBill) {
		if (storageBill.getId() == null) {
			System.out.println("the  storageBill   id  is null !");
			return 0;
		}
		String hsql = " select MAX(a.seqNo) from StorageBillAfter a where  a.company.id =?  and a.storageBill= ? ";
		List para = new ArrayList();
		para.add(CommonUtils.getCompany().getId());
		para.add(storageBill);

		List list = this.find(hsql, para.toArray());
		if (list == null || list.isEmpty() || list.get(0) == null) {
			return 0;
		} else {
			return Integer.parseInt(list.get(0).toString());
		}
	}

	/**
	 * 根据归并后的表体信息找出归并前的标体信息
	 * 
	 * @param storageBillAfter
	 * @return
	 */
	public List findStorageBillBefore(StorageBillAfter storageBillAfter) {
		List para = new ArrayList();
		String hsql = " select a from StorageBillBefore  a  where a.company.id =? ";
		para.add(CommonUtils.getCompany().getId());
		hsql += " and  a.storageBillAfter =?  ";
		para.add(storageBillAfter);
		return this.find(hsql, para.toArray());
	}

	/**
	 * 找出归并前的进出仓表体信息
	 * 
	 * @return
	 */
	public List findStorageBillBefore() {
		String hsql = " select  a  from BlsInnerMerge a "
				+ " where  a.company.id =?  and a.blsTenInnerMerge  is not null ";
		// + " and   a.materiel  not in "
		// + " (  select a.partNo from StorageBillBefore a "
		// +
		// " where  a.company.id =?  and    a.storageBillAfter.storageBill =?  and  a.partNo  is not null  )  ";
		List para = new ArrayList();
		para.add(CommonUtils.getCompany().getId());
		// para.add(CommonUtils.getCompany().getId());
		// para.add(storageBill);
		return this.find(hsql, para.toArray());
	}

	/**
	 * 保存仓单头信息
	 */
	public void saveStorageBill(StorageBill storageBill) {
		this.saveOrUpdate(storageBill);
	}

	/**
	 * 根据料号找出相应的归并信息
	 */
	public List findBlsInnerMerge(Materiel materiel, int seqNum) {
		List para = new ArrayList();
		String hsql = "select a from BlsInnerMerge a where  a.company.id =? and a.materiel=? and (a.blsTenInnerMerge.seqNum=? or a.blsTenInnerMerge.seqNum=null)";
		para.add(CommonUtils.getCompany().getId());
		para.add(materiel);
		para.add(seqNum);
		return this.find(hsql, para.toArray());
	}

	/**
	 * 根据进出口申请单号码和客户/供应商 查找进出口申请单个数
	 * 
	 * @param billNo
	 *            单据号
	 */
	public int findInOutStockBillCountByBillNo(String billNo, ScmCoc scmCoc) {
		List para = new ArrayList();
		String hssql = "select count(a.id) from BlsInOutStockBill a"
				+ " where a.company.id=? and a.billNo=?  ";
		para.add(CommonUtils.getCompany().getId());
		para.add(billNo);
		if (scmCoc != null) {
			hssql += "   and a.corrOwner.id=? ";
			para.add(scmCoc.getId());
		}
		List list = this.find(hssql, para.toArray());
		if (list.isEmpty()) {
			return 0;
		} else if (list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString().trim());
		}
		return 0;
	}

	// public List findInputInOutStockBillOrder() {
	// return this
	// .find(
	// "select a.billNo from BlsInOutStockBill a where a.company.id=?",
	// new Object[] { CommonUtils.getCompany().getId() });
	// }
	/**
	 * 查找出所有的单据号
	 * 
	 * @return
	 */
	public List findBlsInOutBillBillNo() {
		return this
				.find(
						"select a.billNo from BlsInOutStockBill a where a.company.id=?",
						new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找所有进出口申请单AtcMergeBeforeComInfo
	 * 
	 * @return 进出口申请单内容
	 */
	public List findBlsInOutStockBills() {
		return this.find(
				"select a from BlsInOutStockBill as a where a.company.id=?",
				CommonUtils.getCompany().getId());
	}

	/**
	 * 查询出仓单据里的商品信息(生效,未转仓单)
	 * 
	 * @param request
	 * @return
	 */
	public List findBlsOutStockBillDetailNoInputBill() {
		// List list=
		return this
				.find(
						"select distinct a from BlsInOutStockBillDetail as a where a.bsb.ioFlag=? and a.bsb.isEffective=? and a.isStockBill=? and a.company.id=? ",
						new Object[] { "o", true, false,
								CommonUtils.getCompany().getId() });

	}

	/**
	 * 查询出入仓单据里的所有单据号(生效)
	 */
	public List findAllInBillNo() {
		// Long start = System.currentTimeMillis();
		// List list =
		return this
				.find(
						"select distinct a.billNo from BlsInOutStockBill as a where a.ioFlag=? and a.isEffective=? and a.company.id=? ",
						new Object[] { "I", true,
								CommonUtils.getCompany().getId() });
		// System.out.println("查询单据号发了："+(System.currentTimeMillis()-start));
		// return list;
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
	public List findBlsOutStockBillDetail(String warehouseName, String spec,
			ScmCoc scmCoc, Date beginDate, Date endDate) {
		String hsql = "select a from BlsInOutStockBillDetail as a where a.bsb.isEffective=? and a.bsb.ioFlag=? and a.isStockBill=? and a.company.id=? ";
		List parameter = new ArrayList();
		parameter.add(true);
		parameter.add("O");
		parameter.add(false);
		parameter.add(CommonUtils.getCompany().getId());
		if (warehouseName != null && !"".equals(warehouseName)) {
			hsql += " and a.warehouseName=? ";
			parameter.add(warehouseName);
		}
		if (spec != null && !"".equals(spec)) {
			hsql += " and a.spec=? ";
			parameter.add(spec);
		}
		if (scmCoc != null) {
			hsql = hsql + " and a.bsb.corrOwner.id=? ";
			parameter.add(scmCoc.getId());
		}
		if (beginDate != null && endDate == null) {
			hsql = hsql + " and a.bsb.validDate>=? ";
			parameter.add(beginDate);
		}
		if (beginDate == null && endDate != null) {
			hsql = hsql + " and a.bsb.validDate<=? ";
			parameter.add(endDate);
		}
		if (beginDate != null && endDate != null) {
			hsql = hsql + " and (a.bsb.validDate>? and a.bsb.validDate<?)";
			parameter.add(beginDate);
			parameter.add(endDate);
		}
		return this.find(hsql, parameter.toArray());
	}

	/**
	 * 根据对应信息查询对应的出仓单据
	 * 
	 * @param matchInfo
	 * @return
	 */
	public BlsInOutStockBillDetail findBlsOutStockBillDetailByMatchInfo(
			BillMatchInfo matchInfo) {
		String hsql = "from BlsInOutStockBillDetail as a where a.bsb.billNo=? and a.seqNo=? and a.bsb.ioFlag=? and a.bsb.isEffective=? "
				+ " and a.isStockBill=? and a.company.id=? ";
		List list = this.find(hsql, new Object[] { matchInfo.getOutBillNo(),
				matchInfo.getOutMatchNo(), "O", true, false,
				CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			return (BlsInOutStockBillDetail) list.get(0);
		}
		return null;
	}

	/**
	 * 根据对应信息查询对应的入仓单据
	 * 
	 * @param matchInfo
	 * @return
	 */
	public BlsInOutStockBillDetail findBlsInStockBillDetailByMatchInfo(
			BillMatchInfo matchInfo) {
		String hsql = "from BlsInOutStockBillDetail as a where a.bsb.ioFlag=? and a.bsb.billNo=? and a.bsb.isEffective=? "
				+ " and a.warehouseName=? and a.spec=? and a.seqNo=? and a.company.id=? ";
		List list = this.find(hsql, new Object[] { "I",
				matchInfo.getInBillNo(), true, matchInfo.getMatchName(),
				matchInfo.getMatchSpec(), matchInfo.getMatchNo(),
				CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			return (BlsInOutStockBillDetail) list.get(0);

		}
		return null;
	}

	/**
	 * 根据期初单据查询对应商品对应数量共和
	 */
	public Double findFirstBillMatchQty(BlsInOutStockBillDetail firstBill) {
		String hsql = "select sum(a.detailQty) from BillMatchInfo a where a.inBillNo=? and a.matchNo=? and a.company.id=? ";
		List<Double> list = this.find(hsql, new Object[] {
				firstBill.getBsb().getBillNo(), firstBill.getSeqNo(),
				CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
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
	public List findBlsInStockBillDetail(String warehouseName, String spec,
			ScmCoc scmCoc, Date beginDate, Date endDate) {
		String hsql = "select a from BlsInOutStockBillDetail as a where a.bsb.isEffective=? and a.bsb.ioFlag=? and a.bsb.isFirstBill=? and a.company.id=? ";
		List parameter = new ArrayList();
		parameter.add(true);
		parameter.add("I");
		parameter.add(true);
		// parameter.add(false);
		parameter.add(CommonUtils.getCompany().getId());
		if (warehouseName != null && !"".equals(warehouseName)) {
			hsql += " and a.warehouseName=? ";
			parameter.add(warehouseName);
		}
		if (spec != null && !"".equals(spec)) {
			hsql += " and a.spec=? ";
			parameter.add(spec);
		}
		if (scmCoc != null) {
			hsql = hsql + " and a.bsb.corrOwner.id=? ";
			parameter.add(scmCoc.getId());
		}
		if (beginDate != null && endDate == null) {
			hsql = hsql + " and a.bsb.validDate>=? ";
			parameter.add(beginDate);
		}
		if (beginDate == null && endDate != null) {
			hsql = hsql + " and a.bsb.validDate<=? ";
			parameter.add(endDate);
		}
		if (beginDate != null && endDate != null) {
			hsql = hsql + " and (a.bsb.validDate>=? and a.bsb.validDate<=?)";
			parameter.add(beginDate);
			parameter.add(endDate);
		}
		return this.find(hsql, parameter.toArray());
	}

	/**
	 * 新建或更新单据对应信息
	 * 
	 * @param matchInfo
	 * @return
	 */
	public void saveBillMatchInfo(BillMatchInfo matchInfo) {
		this.saveOrUpdate(matchInfo);
	}

	/**
	 * 删除对应关系
	 * 
	 * @param matchInfo
	 */
	public void deletesaveBillMatchInfo(BillMatchInfo matchInfo) {
		this.delete(matchInfo);
	}

	/**
	 * 查询对应关系
	 * 
	 * @param matchName
	 * @param matchspec
	 * @param customerName
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List findBillMatchInfo(String matchName, String matchspec,
			String customerName, Date beginDate, Date endDate) {
		String hsql = "select a from BillMatchInfo as a where a.company.id=? ";
		List parameter = new ArrayList();
		parameter.add(CommonUtils.getCompany().getId());
		if (matchName != null && !"".equals(matchName)) {
			hsql = hsql + " and a.matchName=? ";
			parameter.add(matchName);
		}
		if (matchspec != null && !"".equals(matchspec)) {
			hsql = hsql + " and a.matchSpec=? ";
			parameter.add(matchspec);
		}
		if (customerName != null && !"".equals(customerName)) {
			hsql += " and a.customerName=? ";
			parameter.add(customerName);
		}
		if (beginDate != null && endDate == null) {
			hsql = hsql + " and a.matchDate>=? ";
			parameter.add(beginDate);
		}
		if (beginDate == null && endDate != null) {
			hsql = hsql + " and a.matchDate<=? ";
			parameter.add(endDate);
		}
		if (beginDate != null && endDate != null) {
			hsql = hsql + " and (a.matchDate>=? and a.matchDate<=?)";
			parameter.add(beginDate);
			parameter.add(endDate);
		}
		return this.find(hsql, parameter.toArray());
	}

	/**
	 * 根据单据编号查找进仓单据
	 */
	public List findBlsInOutStockBillInByBillNo(String billNo) {
		return this
				.find(
						"select a from BlsInOutStockBill a where a.company.id=? and a.billNo=? and a.ioFlag=?",
						new Object[] { CommonUtils.getCompany().getId(),
								billNo, BlsIOStockBillIOF.IMPORT });
	}

	/**
	 * 根据入仓单据编号和入仓单据商品流水号查找进仓单据
	 */
	public List findBlsInOutStockBillInByBillNoAndSeqNo(String billNo,
			Integer seqNo) {
		return this
				.find(
						"select a from BlsInOutStockBillDetail a where a.company.id=? and a.bsb.billNo=? and a.seqNo=? and a.bsb.ioFlag=?",
						new Object[] { CommonUtils.getCompany().getId(),
								billNo, seqNo, BlsIOStockBillIOF.IMPORT });
	}

	/**
	 * 通过进仓单的单据编号和进仓单表体中的商品的商品流水号抓出所对应商品的总数量
	 * 
	 * @param inBillNos
	 * @param inBillGoodNo
	 * @return
	 */
	public Double findDetailQtyByBlsInStockBillNos(String inBillNos,
			int inBillGoodNo) {
		String hsql = "select a.detailQty from BlsInOutStockBillDetail a where a.company.id=? and a.bsb.billNo=? and a.seqNo=? and a.bsb.ioFlag=?";
		List list = this.find(hsql, new Object[] {
				CommonUtils.getCompany().getId(), inBillNos, inBillGoodNo,
				BlsIOStockBillIOF.IMPORT });
		if (list.size() == 0) {
			return 0.0;
		} else {
			return (Double) list.get(0);
		}
	}

	/**
	 * 查找单据归并后最大流水号
	 * 
	 * @param storageBill
	 * @return
	 */
	public Integer findMaxBlsInOutStockBillSeqNos(
			BlsInOutStockBill blsInOutStockBill) {
		if (blsInOutStockBill.getId() == null) {
			System.out.println("the  storageBill   id  is null !");
			return 0;
		}
		String hsql = " select MAX(a.seqNo) from BlsInOutStockBillDetail a where a.company.id =? and a.bsb= ? ";
		List para = new ArrayList();
		para.add(CommonUtils.getCompany().getId());
		para.add(blsInOutStockBill);

		List list = this.find(hsql, para.toArray());
		if (list == null || list.isEmpty() || list.get(0) == null) {
			return 0;
		} else {
			return Integer.parseInt(list.get(0).toString());
		}
	}

	/**
	 * 保存，更新单据对应参数
	 * 
	 * @param set
	 */
	public void saveBillMatchSet(BillMatchSet set) {
		this.saveOrUpdate(set);
	}

	/**
	 * 查询参数
	 * 
	 * @return
	 */
	public BillMatchSet findBillMatchSet() {
		List<BillMatchSet> list = this.find(
				"from BillMatchSet a where a.company.id =? ", CommonUtils
						.getCompany().getId());
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 通过料号查找出归并关系中对应的报关级商品信息
	 * 
	 * @return
	 */
	public List findBlsTenInnerMergeByMateriel(Materiel materiel) {
		String hsql = " select  a.blsTenInnerMerge  from BlsInnerMerge a "
				+ " where  a.company.id =?  and a.materiel=?";
		List para = new ArrayList();
		para.add(CommonUtils.getCompany().getId());
		para.add(materiel);
		return this.find(hsql, para.toArray());
	}

	/**
	 * 保存仓单归并后信息
	 */
	public void saveStorageBillAfter(StorageBillAfter storageBillAfter) {
		this.saveOrUpdate(storageBillAfter);
	}

	/**
	 * 保存入仓单据与入仓单的对应关系
	 */
	public void saveBillToWareHouseRelations(BillToWareHouseRelations bhr) {
		this.saveOrUpdate(bhr);
	}

	/**
	 * 保存出仓单据与出仓单的对应关系
	 */
	public void saveBillToWareHouseRelationExp(BillToWareHouseRelationExp bhre) {
		this.saveOrUpdate(bhre);
	}

	/**
	 * 根据单据编号查找进仓单据
	 */
	public List findBlsInOutStockBillInByinBillNo(String inbillNo, Integer seqNo) {
		return this
				.find(
						"select a from BlsInOutStockBillDetail a where a.company.id=? and a.bsb.billNo=? and a.seqNo=? and a.bsb.ioFlag=?",
						new Object[] { CommonUtils.getCompany().getId(),
								inbillNo, seqNo, BlsIOStockBillIOF.IMPORT });
	}

	/**
	 * 根据入仓单据明细查找进入仓单号码
	 */
	public List findBlsInOutStockBillDeInByBlsInOutStockBillDetail(
			BlsInOutStockBillDetail bsd) {
		return this
				.find(
						"select a from BillToWareHouseRelations a where a.company.id=? and a.bsd=?",
						new Object[] { CommonUtils.getCompany().getId(), bsd });
	}

	/**
	 * 根据入仓单查找进入仓单号码
	 */
	public List findBillToWareHouseRelationsInByStockBill(StorageBill sb) {
		return this
				.find(
						"select a from BillToWareHouseRelations a where a.company.id=? and a.sb=?",
						new Object[] { CommonUtils.getCompany().getId(), sb });
	}

	/**
	 * 根据出仓单查找对应关系
	 */
	public List findBillToWareHouseRelationExpInByStockBill(StorageBill sb) {
		return this
				.find(
						"select a from BillToWareHouseRelationExp a where a.company.id=? and a.sb=?",
						new Object[] { CommonUtils.getCompany().getId(), sb });
	}

	/**
	 * 删除入仓对应关系
	 * 
	 * @param matchInfo
	 */
	public void deleteBillToWareHouseRelations(
			BillToWareHouseRelations billToWareHouseRelations) {
		this.delete(billToWareHouseRelations);
	}

	/**
	 * 删除出仓对应关系
	 * 
	 * @param matchInfo
	 */
	public void deleteBillToWareHouseRelationExp(
			BillToWareHouseRelationExp billToWareHouseRelationExp) {
		this.delete(billToWareHouseRelationExp);
	}

	/**
	 * 根据入仓单查找进入仓单据表体明细
	 */
	public List findBlsInOutStockBillDetailByStockBill(StorageBill sb) {
		return this
				.find(
						"select a.bsd from BillToWareHouseRelations a where a.company.id=? and a.sb=?",
						new Object[] { CommonUtils.getCompany().getId(), sb });
	}

	/**
	 * 根据出仓单查找进出仓单据表体明细
	 */
	public List findBlsInOutStockBillDetailByStockBills(StorageBill sb) {
		return this
				.find(
						"select a.bsd from BillToWareHouseRelationExp a where a.company.id=? and a.sb=?",
						new Object[] { CommonUtils.getCompany().getId(), sb });
	}

	/**
	 * 保存转仓单参数设置
	 */
	public void saveBlsSwitchParameterSet(BlsInOutStockSwichParameterSet bosp) {
		this.saveOrUpdate(bosp);
	}

	/**
	 * 删除转仓单参数设置
	 */
	public void deleteBlsSwitchParameterSet(BlsInOutStockSwichParameterSet bosp) {
		this.delete(bosp);
	}

	/**
	 * 找出转仓单参数设置中所有的值
	 */
	public List findBlsSwitchParameterSet() {
		String hsql = "select a from BlsInOutStockSwichParameterSet a where a.company.id=? ";
		return this.find(hsql,
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据进出仓类型查找参数设置
	 */
	public BlsInOutStockSwichParameterSet findBlsSwitchParameterSetByInOutFlag(
			String inOutFlag) {
		String hsql = "select a from BlsInOutStockSwichParameterSet a where a.company.id=? and a.ioFlag=?";
		List list = this.find(hsql, new Object[] {
				CommonUtils.getCompany().getId(), inOutFlag });
		if(list == null || list.size() < 1) {
			throw new RuntimeException("出仓类型参数未设置！");
		} else {
			return (BlsInOutStockSwichParameterSet) list.get(0);
		}
	}

	/**
	 * 查找出仓当中所有账册编号
	 */
	public List findAllEmsNo() {
		String hsql = "select distinct a.emsNo from StorageBill a where a.company.id=?";
		List list = this.find(hsql, CommonUtils.getCompany().getId());
		return list;
	}
}