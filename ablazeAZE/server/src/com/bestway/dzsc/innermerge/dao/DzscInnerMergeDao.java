package com.bestway.dzsc.innermerge.dao;

import java.util.ArrayList;
import java.util.List;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.dzsc.innermerge.entity.DzscCustomInnerMergeCondition;
import com.bestway.dzsc.innermerge.entity.DzscCustomsBomExg;
import com.bestway.dzsc.innermerge.entity.DzscFourInnerMerge;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeData;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeDataHistory;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeDataOrder;
import com.bestway.dzsc.innermerge.entity.DzscTenInnerMerge;

public class DzscInnerMergeDao extends BaseDao {
	/**
	 * 保存手册归并数据变更记录
	 * 
	 * @param data
	 *            存手册归并数据
	 */
	public void saveDzscInnerMergeData(DzscInnerMergeData data) {
		this.saveOrUpdate(data);
	}

	/**
	 * 保存手册归并10码数据
	 * 
	 * @param data
	 *            存手册归并数据
	 */
	public void saveDzscTenInnerMerge(DzscTenInnerMerge data) {
		this.saveOrUpdate(data);
	}

	/**
	 * 保存手册归并4码数据
	 * 
	 * @param data
	 *            存手册归并数据
	 */
	public void saveDzscFourInnerMerge(DzscFourInnerMerge data) {
		this.saveOrUpdate(data);
	}

	/**
	 * 抓取变更的归并记录
	 * 
	 * @param type
	 *            物料类型
	 * @param index
	 *            数据的开始下表
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            property的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return List 是DzscInnerMergeData型，手册商品归并资料
	 */
	public List findChangedInnerMergeData(String type, int index, int length,
			String property, Object value, boolean isLike) {

		List<Object> paramters = new ArrayList<Object>();

		String hsql = " select a from DzscInnerMergeData a left join fetch a.materiel "
				+ " left join fetch a.dzscTenInnerMerge "
				+ " left join fetch a.dzscTenInnerMerge.dzscFourInnerMerge "
				+ " where a.imrType = ? and a.company.id=? and a.isChange=?";
		paramters.add(type);
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(true);
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and a." + property + " like ? ";
				paramters.add("%" + value + "%");
			} else {
				hsql += " and a." + property + " = ? ";
				paramters.add(value);
			}
		}
		hsql += " order by a.dzscTenInnerMerge.dzscFourInnerMerge.fourSeqNum desc,"
				+ " a.dzscTenInnerMerge.tenSeqNum desc";
		return super.findPageList(hsql, paramters.toArray(), index, length);

	}

	/**
	 * 抓取所有的归并记录
	 * 
	 * @return List 是DzscInnerMergeData型，手册商品归并资料
	 */
	public List findInnerMergeDataAll() {
		return this
				.find(
						" select a from DzscInnerMergeData a left join fetch a.materiel "
								+ " left join fetch a.dzscTenInnerMerge "
								+ " left join fetch a.dzscTenInnerMerge.dzscFourInnerMerge "
								+ " where  a.company.id=? "
								+ " order by a.dzscTenInnerMerge.dzscFourInnerMerge.fourSeqNum,"
								+ " a.dzscTenInnerMerge.tenSeqNum",
						new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 抓取已生效归并记录的个数
	 * 
	 * @return List 是DzscInnerMergeData型，手册商品归并资料
	 */
	public int findInnerMergeDataEffectiveCount() {
		List list = this.find(" select count(a.id) from DzscInnerMergeData a "
				+ " where  a.company.id=? and a.stateMark=? ", new Object[] {
				CommonUtils.getCompany().getId(), DzscState.EXECUTE });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 抓取全部归并记录（不包含变更的）
	 * 
	 * @param type
	 *            物料类型
	 * @param index
	 *            数据的开始下表
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            property的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return List 是DzscInnerMergeData型，手册商品归并资料
	 */
	public List findInnerMergeDataByType(String type, int index, int length,
			String property, Object value, boolean isLike) {

		List<Object> paramters = new ArrayList<Object>();

		String hsql = " select a from DzscInnerMergeData a left join fetch a.materiel "
				+ " left join fetch a.dzscTenInnerMerge "
				+ " left join fetch a.dzscTenInnerMerge.dzscFourInnerMerge "
				+ " where a.imrType = ? and a.company.id=? and a.isChange=?";
		paramters.add(type);
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(false);
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and a." + property + " like ? ";
				paramters.add("%" + value + "%");
			} else {
				hsql += " and a." + property + " = ? ";
				paramters.add(value);
			}
		}
		hsql += " order by a.dzscTenInnerMerge.dzscFourInnerMerge.fourSeqNum desc,"
				+ " a.dzscTenInnerMerge.tenSeqNum desc";
		long beginTime = System.currentTimeMillis();
		List list = super
				.findPageList(hsql, paramters.toArray(), index, length);
		long endTime = System.currentTimeMillis();
		System.out.println(":::::::::::::::total time:" + (endTime - beginTime)
				/ 1000);
		return list;
	}

	/**
	 * 抓取10码归并记录（不包含变更的）
	 * 
	 * @param type
	 *            物料类型
	 * @param index
	 *            数据的开始下表
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            property的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return List 是DzscInnerMergeData型，手册商品归并资料
	 */
	public List findDzscExgTenInnerMerge(int index, int length,
			String property, Object value, boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = " select distinct a.dzscTenInnerMerge from DzscInnerMergeData a "
				+ " left join fetch a.dzscTenInnerMerge.dzscFourInnerMerge "
				+ " where a.imrType = ? and a.company.id=? and a.isChange=?";
		paramters.add(MaterielType.FINISHED_PRODUCT);
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(false);
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and a.dzscTenInnerMerge." + property + " like ? ";
				paramters.add("%" + value + "%");
			} else {
				hsql += " and a.dzscTenInnerMerge." + property + " = ? ";
				paramters.add(value);
			}
		}
		hsql += " and a.dzscTenInnerMerge is not null ";
		hsql += " order by a.dzscTenInnerMerge.tenSeqNum ";
		long beginTime = System.currentTimeMillis();
		List list = super
				.findPageList(hsql, paramters.toArray(), index, length);
		long endTime = System.currentTimeMillis();
		System.out.println(":::::::::::::::total time:" + (endTime - beginTime)
				/ 1000);
		return list;
	}

	/**
	 * 抓取全部归并记录（不包含变更的）
	 * 
	 * @param type
	 *            物料类型
	 * @param index
	 *            数据的开始下表
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            property的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return List 是DzscInnerMergeData型，手册商品归并资料
	 */
	public List findFourInnerMergeDataByType(String type, boolean isChange) {

		List<Object> paramters = new ArrayList<Object>();

		String hsql = " select distinct a.dzscTenInnerMerge.dzscFourInnerMerge"
				+ "  from DzscInnerMergeData a "
				+ " where a.imrType = ? and a.company.id=?  and a.isChange =? ";
		paramters.add(type);
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(isChange);
		hsql += " order by a.dzscTenInnerMerge.dzscFourInnerMerge.fourSeqNum ";
		// long beginTime = System.currentTimeMillis();
		List list = super.find(hsql, paramters.toArray());
		// long endTime = System.currentTimeMillis();
		return list;
	}

	/**
	 * 查找归并数据中的最大的seqNum
	 * 
	 * @param type
	 *            物料类型
	 * @param seqNum
	 *            所要查找的编码序号
	 * @return int 最大十位编码序号
	 */
	public int findMaxInnerMergeNo(String type, String seqNum) {
		int no = 1;
		String hql = "";
		if ("fourSeqNum".equals(seqNum)) {
			hql = "select max(a.dzscTenInnerMerge.dzscFourInnerMerge."
					+ seqNum
					+ ") from  DzscInnerMergeData a where a.imrType = ? and a.company.id = ?";
		} else if ("tenSeqNum".equals(seqNum)) {
			hql = "select max(a.dzscTenInnerMerge."
					+ seqNum
					+ ") from  DzscInnerMergeData a where a.imrType = ? and a.company.id = ?";
		} else {
			return 0;
		}
		List list = this.find(hql, new Object[] { type,
				CommonUtils.getCompany().getId() });

		if (list.size() < 1 || list.get(0) == null) {
			System.out.println("kcb");
			if (type.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
				no = 100000;
			}
		} else {
			no = Integer.valueOf(list.get(0).toString()).intValue() + 1;

		}
		if (no < 0) {
			no = 1;

		}
		return no;
	}

	/**
	 * 根据4码抓取归并前的资料
	 * 
	 * @param fourInnerMerge
	 * @return
	 */
	public List findDzscInnerMergeData(DzscFourInnerMerge fourInnerMerge) {
		return this
				.find(
						" select a from DzscInnerMergeData a left join fetch a.materiel "
								+ " left join fetch a.dzscTenInnerMerge "
								+ " left join fetch a.dzscTenInnerMerge.dzscFourInnerMerge "
								+ " where a.company.id=? "
								+ " and a.dzscTenInnerMerge.dzscFourInnerMerge.id=? ",
						new Object[] { CommonUtils.getCompany().getId(),
								fourInnerMerge.getId() });
	}

	/**
	 * 根据10码抓取归并前的资料
	 * 
	 * @param tenInnerMerge
	 * @return
	 */
	public List findDzscInnerMergeData(DzscTenInnerMerge tenInnerMerge) {
		return this
				.find(
						" select a from DzscInnerMergeData a left join fetch a.materiel "
								+ " left join fetch a.dzscTenInnerMerge "
								+ " left join fetch a.dzscTenInnerMerge.dzscFourInnerMerge "
								+ " where  a.company.id=? and a.dzscTenInnerMerge.id=? ",
						new Object[] { CommonUtils.getCompany().getId(),
								tenInnerMerge.getId() });
	}

	/**
	 * 抓取10码的资料
	 * 
	 * @param fourInnerMerge
	 * @return
	 */
	public List findDzscTenInnerMerge(String imrType, boolean isChange) {
		return this
				.find(
						" select distinct a.dzscTenInnerMerge from DzscInnerMergeData a "
								+ " left join fetch a.dzscTenInnerMerge.dzscFourInnerMerge "
								+ " left join fetch a.dzscTenInnerMerge.tenComplex "
								+ " left join fetch a.dzscTenInnerMerge.dzscFourInnerMerge "
								+ " where a.dzscTenInnerMerge is not null "
								+ " and a.company.id=? and a.imrType=? "
								+ " and a.isChange =? "
								+ " order by a.dzscTenInnerMerge.tenSeqNum",
						new Object[] { CommonUtils.getCompany().getId(),
								imrType, isChange });
	}

	/**
	 * 抓取10码的资料
	 * 
	 * @param fourInnerMerge
	 * @return
	 */
	public List findDzscTenInnerMerge(String imrType, boolean isChange,
			String stateMark) {
		return this
				.find(
						" select distinct a.dzscTenInnerMerge from DzscInnerMergeData a "
								+ " left join fetch a.dzscTenInnerMerge.dzscFourInnerMerge "
								+ " left join fetch a.dzscTenInnerMerge.tenComplex "
								+ " left join fetch a.dzscTenInnerMerge.dzscFourInnerMerge "
								+ " where a.dzscTenInnerMerge is not null "
								+ " and a.company.id=? and a.imrType=? "
								+ " and a.isChange =? "
								+ " and a.stateMark =? "
								+ " order by a.dzscTenInnerMerge.tenSeqNum",
						new Object[] { CommonUtils.getCompany().getId(),
								imrType, isChange, stateMark });
	}

	/**
	 * 根据4码抓取10码的资料
	 * 
	 * @param fourInnerMerge
	 * @return
	 */
	public List findDzscTenInnerMerge(DzscFourInnerMerge fourInnerMerge) {
		return this
				.find(
						" select a from DzscTenInnerMerge a left join fetch a.dzscFourInnerMerge "
								+ " left join fetch a.tenComplex "
								+ " left join fetch a.dzscFourInnerMerge "
								+ " where  a.company.id=? and a.dzscFourInnerMerge.id=? ",
						new Object[] { CommonUtils.getCompany().getId(),
								fourInnerMerge.getId() });
	}

	/**
	 * 根据10码抓取10码的资料
	 * 
	 * @param tenInnerMerge
	 * @return
	 */
	public DzscTenInnerMerge findDzscTenInnerMerge(
			DzscTenInnerMerge tenInnerMerge, String imrType, boolean isChange) {
		List list = this.find(
				" select a.dzscTenInnerMerge from DzscInnerMergeData a "
						+ " where  a.company.id=? and a.imrType=? "
						+ " and a.isChange=? "
						+ " and a.dzscTenInnerMerge.tenSeqNum=? "
						+ " and a.dzscTenInnerMerge.tenComplex=? "
						+ " and a.dzscTenInnerMerge is not null", new Object[] {
						CommonUtils.getCompany().getId(), imrType, isChange,
						tenInnerMerge.getTenSeqNum(),
						tenInnerMerge.getTenComplex() });
		if (list.size() > 0) {
			return (DzscTenInnerMerge) list.get(0);
		}
		return null;
	}

	/**
	 * 根据10码抓取变更后删处状态10码的资料
	 * 
	 * @param tenInnerMerge
	 * @return
	 */
	public DzscTenInnerMerge findDeleteDzscTenInnerMerge(
			DzscTenInnerMerge tenInnerMerge) {
		List list = this.find(" select a from DzscTenInnerMerge a "
				+ " where  a.company.id=? and  a.tenSeqNum=? "
				+ " and a.tenComplex=? and a.tenModifyMark=? ", new Object[] {
				CommonUtils.getCompany().getId(), tenInnerMerge.getTenSeqNum(),
				tenInnerMerge.getTenComplex(), ModifyMarkState.DELETED });
		if (list.size() > 0) {
			return (DzscTenInnerMerge) list.get(0);
		}
		return null;
	}

	/**
	 * 抓取变更后删处状态10码的资料
	 * 
	 * @param tenInnerMerge
	 * @return
	 */
	public List findDeleteDzscTenInnerMerge() {
		List list = this.find(" select a from DzscTenInnerMerge a "
				+ " where  a.company.id=? and a.tenModifyMark=? ",
				new Object[] { CommonUtils.getCompany().getId(),
						ModifyMarkState.DELETED });
		return list;
	}

	/**
	 * 查询所有10码ID
	 * 
	 * @return
	 */
	public List findAllDzscTenInnerMergeId() {
		List list = this.find("select distinct b.dzscTenInnerMerge.id "
				+ " from DzscInnerMergeData b where  b.company.id=? "
				+ " and b.dzscTenInnerMerge is not null ",
				new Object[] { CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 根据10码抓取归并前的资料的个数
	 * 
	 * @param tenInnerMerge
	 * @return
	 */
	public Integer findDzscInnerMergeCountExceptDeleted(
			DzscTenInnerMerge tenInnerMerge) {
		List list = this.find(" select count(a.id) from DzscInnerMergeData a "
				+ " where a.company.id=? and a.dzscTenInnerMerge.id=? "
				+ " and a.beforeModifyMark<>? ", new Object[] {
				CommonUtils.getCompany().getId(), tenInnerMerge.getId(),
				ModifyMarkState.DELETED });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 根据4码抓取10码的资料的个数
	 * 
	 * @param fourInnerMerge
	 * @return
	 */
	public Integer findDzscTenInnerMergeCountExceptDeleted(
			DzscFourInnerMerge fourInnerMerge) {
		List list = this.find(" select count(a.id) from DzscTenInnerMerge a "
				+ " where a.company.id=? and a.dzscFourInnerMerge.id=?"
				+ " and a.tenModifyMark<>?  ", new Object[] {
				CommonUtils.getCompany().getId(), fourInnerMerge.getId(),
				ModifyMarkState.DELETED });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 根据10码抓取归并前的资料的个数
	 * 
	 * @param tenInnerMerge
	 * @return
	 */
	public Integer findDzscInnerMergeCount(DzscTenInnerMerge tenInnerMerge) {
		List list = this.find(" select count(a.id) from DzscInnerMergeData a "
				+ " where a.company.id=? and a.dzscTenInnerMerge.id=? ",
				new Object[] { CommonUtils.getCompany().getId(),
						tenInnerMerge.getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 根据4码抓取10码的资料的个数
	 * 
	 * @param fourInnerMerge
	 * @return
	 */
	public Integer findDzscTenInnerMergeCount(DzscFourInnerMerge fourInnerMerge) {
		List list = this.find(" select count(a.id) from DzscTenInnerMerge a "
				+ " where a.company.id=? and a.dzscFourInnerMerge.id=?",
				new Object[] { CommonUtils.getCompany().getId(),
						fourInnerMerge.getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 删除手册归并数据
	 * 
	 * @param data
	 *            归并数据
	 */
	public void deleteDzscInnerMergeData(DzscInnerMergeData data) {
		this.delete(data);
	}

	/**
	 * 删除手册归并数据
	 * 
	 * @param data
	 *            归并数据
	 */
	public void deleteDzscTenInnerMerge(DzscTenInnerMerge data) {
		this.delete(data);
	}

	/**
	 * 删除手册归并数据
	 * 
	 * @param data
	 *            归并数据
	 */
	public void deleteDzscFourInnerMerge(DzscFourInnerMerge data) {
		this.delete(data);
	}

	/**
	 * 抓取不在正在执行的归并记录
	 * 
	 * @param type
	 *            物料类型
	 * @return List 是DzscInnerMergeData型，手册商品归并资料
	 */
	public List findInnerMergeDataAndChangeByType(String type) {
		return this
				.find(
						" select a from DzscInnerMergeData a left join fetch a.materiel"
								+ " left join fetch a.dzscTenInnerMerge "
								+ " left join fetch a.dzscTenInnerMerge.dzscFourInnerMerge "
								+ " where a.imrType = ? and a.company.id=? and a.stateMark <> ? "
								+ " and a.dzscTenInnerMerge.dzscFourInnerMerge is not null  "
								+ " order by a.dzscTenInnerMerge.dzscFourInnerMerge.fourSeqNum,"
								+ " a.dzscTenInnerMerge.tenSeqNum",
						new Object[] { type, CommonUtils.getCompany().getId(),
								DzscState.EXECUTE });
	}

	/**
	 * 抓取未变更的归并记录 根据物料类型
	 * 
	 * @param type
	 *            物料类型
	 * @return List 是DzscInnerMergeData型，手册商品归并资料
	 */
	public List findInnerMergeDataByTypeForPrint(String type) {
		return this
				.find(
						" select a from DzscInnerMergeData a left join fetch a.materiel"
								+ " left join fetch a.dzscTenInnerMerge "
								+ " left join fetch a.dzscTenInnerMerge.dzscFourInnerMerge "
								+ " where a.imrType = ? and a.company.id=? and a.isChange=? "
								// + " and a.dzscTenInnerMerge is not null "
								+ " and a.dzscTenInnerMerge.dzscFourInnerMerge is not null "
								+ " order by a.dzscTenInnerMerge.dzscFourInnerMerge.fourSeqNum,"
								+ " a.dzscTenInnerMerge.tenSeqNum",
						new Object[] { type, CommonUtils.getCompany().getId(),
								false });
	}

	/**
	 * 查找电子手册自定义归并条件
	 * 
	 * @param type
	 *            物料类型
	 * @return List 是DzscCustomInnerMergeCondition型，电子手册自定义归并条件
	 */
	public List findDzscCustomInnerMergeConditionByType(String type) {
		return this
				.find(
						"select a from DzscCustomInnerMergeCondition a where a.materielType = ? and a.company.id=? ",
						new Object[] { type, CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找电子手册自定义归并条件
	 * 
	 * @return List 是DzscCustomInnerMergeCondition型，电子手册自定义归并条件
	 */
	public List findDzscCustomInnerMergeCondition() {
		return this
				.find(
						"select a from DzscCustomInnerMergeCondition a where a.company.id=? ",
						new Object[] { CommonUtils.getCompany().getId() });
	}

	public List findDzscInnerMergeDataByTenSeqNum(String type, Integer seq) {
		return this
				.find(
						" select a from DzscInnerMergeData as a left join fetch a.materiel "
								+ " left join fetch a.dzscTenInnerMerge "
								+ " left join fetch a.dzscTenInnerMerge.dzscFourInnerMerge "
								+ " where a.imrType = ? and a.company.id=?  "
								+ " and a.dzscTenInnerMerge.tenSeqNum = ? "
								+ " and a.isChange=? ", new Object[] { type,
								CommonUtils.getCompany().getId(), seq, false });

	}

	/**
	 * 手册电子手册自定义归并条件
	 * 
	 * @param condition
	 *            电子手册自定义归并条件
	 */
	public void deleteDzscCustomInnerMergeCondition(
			DzscCustomInnerMergeCondition condition) {
		this.delete(condition);
	}

	/**
	 * 保存电子手册自定义归并条件
	 * 
	 * @param condition
	 *            电子手册自定义归并条件
	 */
	public void saveDzscCustomInnerMergeCondition(
			DzscCustomInnerMergeCondition condition) {
		this.saveOrUpdate(condition);
	}

	/**
	 * 查询未申报的归并记录
	 * 
	 * @return List 是DzscInnerMergeData型，手册商品归并资料
	 */
	public List findNotApplyInnerMergeData(String materielType) {
		return this
				.find(
						" select a from DzscInnerMergeData a left join fetch a.materiel "
								+ " left join fetch a.dzscTenInnerMerge "
								+ " left join fetch a.dzscTenInnerMerge.dzscFourInnerMerge "
								+ " where  a.company.id=? and (a.stateMark=? or a.stateMark=?) "
								+ " and a.dzscTenInnerMerge.dzscFourInnerMerge is not null "
								+ " and a.imrType=? "
								+ " order by a.dzscTenInnerMerge.dzscFourInnerMerge.fourSeqNum,"
								+ " a.dzscTenInnerMerge.tenSeqNum",
						new Object[] { CommonUtils.getCompany().getId(),
								DzscState.ORIGINAL, DzscState.CHANGE,
								materielType });
	}

	// /**
	// * 查询未申报的归并记录
	// *
	// * @return List 是DzscInnerMergeData型，手册商品归并资料
	// */
	// public List findEffectivedInnerMergeDataTenSeqNum(String materielType) {
	// return this
	// .find(
	// " select a.tenSeqNum from DzscInnerMergeData a "
	// + " where a.company.id=? and a.stateMark=? "
	// + " and a.imrType=? ",
	// new Object[] { CommonUtils.getCompany().getId(),
	// DzscState.EXECUTE,
	// materielType });
	// }

	/**
	 * 查询已申请,未生效的归并记录
	 * 
	 * @return List 是DzscInnerMergeData型，手册商品归并资料
	 */
	public List findAppliedInnerMergeData() {
		return this
				.find(
						" select a from DzscInnerMergeData a left join fetch a.materiel "
								+ " left join fetch a.dzscTenInnerMerge "
								+ " left join fetch a.dzscTenInnerMerge.dzscFourInnerMerge "
								+ " where  a.company.id=? and a.stateMark=? "
								+ " and a.dzscTenInnerMerge.dzscFourInnerMerge is not null "
								+ " order by a.dzscTenInnerMerge.dzscFourInnerMerge.fourSeqNum,"
								+ " a.dzscTenInnerMerge.tenSeqNum",
						new Object[] { CommonUtils.getCompany().getId(),
								DzscState.APPLY });
	}

	/**
	 * 根据物料号码,抓取未变更的归并数据
	 * 
	 * @param materialCode
	 *            料号
	 * @return DzscInnerMergeData 手册商品归并资料，返回符合突条件的第一条数据
	 */
	public DzscInnerMergeData findInnerMergeDataByMaterialCode(
			String materialCode) {
		List list = this.find(
				"select a from DzscInnerMergeData as a where a.company.id=?"
						+ " and a.materiel.ptNo=? and a.isChange=? ",
				new Object[] { CommonUtils.getCompany().getId(), materialCode,
						false });
		if (list.size() > 0) {
			return (DzscInnerMergeData) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据物料号码,抓取未变更的归并数据
	 * 
	 * @param customsNo
	 *            料号
	 * @return DzscInnerMergeData 手册商品归并资料，返回符合突条件的第一条数据
	 */
	public DzscInnerMergeData findInnerMergeDataByCustomsNo(String customsNo) {
		List list = this.find(
				"select a from DzscInnerMergeData as a where a.company.id=?"
						+ " and a.materiel.customsNo=? and a.isChange=? ",
				new Object[] { CommonUtils.getCompany().getId(), customsNo,
						false });
		if (list.size() > 0) {
			return (DzscInnerMergeData) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据物料号码,抓取未变更的归并数据
	 * 
	 * @param materialCode
	 *            料号
	 * @return DzscInnerMergeData 手册商品归并资料，返回符合突条件的第一条数据
	 */
	public DzscTenInnerMerge findTenInnerMergeByTenSeqNum(Integer tenSeqNum,
			Complex complex) {
		List list = this.find(
				"select distinct a.dzscTenInnerMerge from DzscInnerMergeData as a "
						+ " where a.company.id=?"
						+ " and a.dzscTenInnerMerge.tenSeqNum=? "
						+ " and d.dzscTenInnerMerge.tenComplex=? "
						+ " and a.isChange=? "
						+ " and a.dzscTenInnerMerge is not null", new Object[] {
						CommonUtils.getCompany().getId(), tenSeqNum, complex,
						false });
		if (list.size() > 0) {
			return (DzscTenInnerMerge) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据物料号码,抓取未变更的归并数据
	 * 
	 * @param materialCode
	 *            料号
	 * @return DzscInnerMergeData 手册商品归并资料，返回符合突条件的第一条数据
	 */
	public List findInnerMergeDataPtNo() {
		return this.find(
				"select distinct a.materiel.ptNo from DzscInnerMergeData as a "
						+ " where a.company.id=? and a.isChange=? ",
				new Object[] { CommonUtils.getCompany().getId(), false });
	}

	// public List findInnerMergedMeterialBytenSeqNum(String type, Integer
	// seqNum) {
	// return this.find(" select a from DzscInnerMergeData a "
	// + " left join fetch a.dzscTenInnerMerge "
	// + " left join fetch a.dzscTenInnerMerge.dzscFourInnerMerge "
	// + " where a.imrType = ? and a.company.id=? and a.isChange=? "
	// + " and a.dzscTenInnerMerge.tenSeqNum = ? "
	// + " order by a.dzscTenInnerMerge.tenSeqNum", new Object[] {
	// type, CommonUtils.getCompany().getId(), false, seqNum });
	// }

	/**
	 * 保存手册归并数据变更记录
	 * 
	 * @param history
	 *            手册归并数据变更记录
	 */
	public void saveDzscInnerMergeDataHistory(DzscInnerMergeDataHistory history) {
		this.saveOrUpdate(history);
	}

	/**
	 * 删除手册归并数据变更记录
	 * 
	 * @param history
	 *            手册归并数据变更记录
	 */
	public void deleteDzscInnerMergeDataHistory(
			DzscInnerMergeDataHistory history) {
		this.delete(history);
	}

	/**
	 * 根据料号抓取已变更的内部归并数据
	 * 
	 * @param materialCode
	 *            料号
	 * @return DzscInnerMergeData 归并数据，返回符合条件的第一条数据
	 */
	public DzscInnerMergeData findChangedMergeDataByMaterialCode(
			String materialCode) {
		List list = this.find(
				"select a from DzscInnerMergeData as a where a.company.id=?"
						+ " and a.materiel.ptNo=? and a.isChange=? ",
				new Object[] { CommonUtils.getCompany().getId(), materialCode,
						true });
		if (list.size() > 0) {
			return (DzscInnerMergeData) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 抓取通关备案料件基本资料（不在电子手册归并存在的）
	 * 
	 * @param type
	 *            物料类型
	 * @return List 是materiel型，电子手册的物料基本资料
	 */
	public List findMaterielForDzscInnerMerge(String type) {
		// return this
		// .find(
		// "select distinct a.materiel from MaterialApply as a"
		// + " left join fetch a.materiel.complex "
		// + " where a.materiel.id not in"
		// + " (select b.materiel.id from DzscInnerMergeData b where b.imrType =
		// ? and b.company.id=?) "
		// + " and a.materiel.scmCoi.coiProperty=? and a.company.id=? "
		// + " and a.stateMark=? "
		// + " order by a.materiel.ptNo", new Object[] {
		// type, CommonUtils.getCompany().getId(), type,
		// CommonUtils.getCompany().getId(),
		// DzscState.EXECUTE });
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select distinct a.materiel from MaterialApply as a"
				+ " left join fetch a.materiel.complex "
				+ " where a.materiel.scmCoi.coiProperty=? and a.company.id=? "
				+ " and (a.isForbidMerge=? or a.isForbidMerge is null) ";
		paramters.add(type);
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(false);
		if (!type.equals(MaterielType.MACHINE)) {// 设备资料不用申报
			hsql += " and a.stateMark=? ";
			paramters.add(DzscState.EXECUTE);
		}
		hsql += " order by a.materiel.ptNo ";
		return find(hsql, paramters.toArray());
	}

	/**
	 * 抓取电子手册的物料基本资料（不在电子手册归并存在的）
	 * 
	 * @param materielType
	 *            物料类别属性
	 * @param index
	 *            数据的开始下表
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            property的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return List 是materiel型，电子手册的物料基本资料
	 */
	public List findMaterielForDzscInnerMerge(String materielType, int index,
			int length, String property, Object value, Boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select distinct a.materiel from MaterialApply as a"
				+ " left join fetch a.materiel.complex "
				+ " where a.materiel.scmCoi.coiProperty=? and a.company.id=? "
				+ " and (a.isForbidMerge=? or a.isForbidMerge is null) ";
		// + " and a.materiel.ptNo not in"
		// + " (select b.materiel.ptNo from DzscInnerMergeData b where b.imrType
		// = ? and b.company.id=?)";
		paramters.add(materielType);
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(false);
		if (!materielType.equals(MaterielType.MACHINE)) {// 设备资料不用申报
			hsql += " and a.stateMark=? ";
			paramters.add(DzscState.EXECUTE);
		}
		// paramters.add(materielType);
		// paramters.add(CommonUtils.getCompany().getId());
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a.materiel." + property + " like ?  ";
				paramters.add("%" + value + "%");
			} else {
				hsql += " and  a.materiel." + property + " = ?  ";
				paramters.add(value);
			}
		}
		hsql += " order by a.materiel.ptNo";
		return this.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 查询已经存在归并的物料号码
	 * 
	 * @param materielType
	 * @return
	 */
	public List findExistedMaterialPtNoInInnerMerge(String materielType) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select distinct a.materiel.ptNo from DzscInnerMergeData a "
				+ " where a.imrType = ? and a.company.id=?";
		paramters.add(materielType);
		paramters.add(CommonUtils.getCompany().getId());
		return this.find(hsql, paramters.toArray());
	}

	/**
	 * 根据物料类型查找手册商品归并信息
	 * 
	 * @param type
	 *            物料类型
	 * @return List 是DzscInnerMergeData型，手册商品归并资料
	 */
	public List findDzscInnerMergeDataByTypeOrderby(String type) {
		return this
				.find(
						" select a from DzscInnerMergeData as a left join fetch a.materiel "
								+ " left join fetch a.dzscTenInnerMerge "
								+ " left join fetch a.dzscTenInnerMerge.dzscFourInnerMerge "
								+ " where a.imrType = ? and a.company.id=? "
								+ " order by a.materiel.complex.code asc,a.materiel.factoryName asc,"
								+ " a.materiel.ptUnit.code asc,"
								+ " a.dzscTenInnerMerge.tenSeqNum desc,"
								+ " a.dzscTenInnerMerge.dzscFourInnerMerge.fourSeqNum desc",
						new Object[] { type, CommonUtils.getCompany().getId() });
	}

	/**
	 * 返回手册商品归并资料中最大的十位编码序号
	 * 
	 * @param type
	 *            物料类型
	 * @return int 最大的十位编码序号
	 */
	public int findTenInnerMergeNo(String type) {
		int no = 0;
		List list = this.find(
				"select max(a.dzscTenInnerMerge.tenSeqNum) from DzscInnerMergeData a "
						+ " where a.imrType=? and  a.company.id=?",
				new Object[] { type, CommonUtils.getCompany().getId() });
		if (list.size() < 1 || list.get(0) == null) {
			if (type.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
				no = 100000;
			}
		} else {
			no = Integer.valueOf(list.get(0).toString()).intValue();
		}
		if (no < 0) {
			no = 0;
		}
		return no;
	}

	/**
	 * 返回手册商品归并资料中最大的四位编码序号
	 * 
	 * @param type
	 *            物料类型
	 * @return int 最大的四位编码序号
	 */
	public int findFourInnerMergeNo(String type) {
		int no = 0;
		List list = this
				.find(
						"select max(a.dzscTenInnerMerge.dzscFourInnerMerge.fourSeqNum)"
								+ "  from DzscInnerMergeData a where a.imrType=? and  a.company.id=?",
						new Object[] { type, CommonUtils.getCompany().getId() });
		if (list.size() < 1 || list.get(0) == null) {
			if (type.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
				no = 100000;
			}
		} else {
			no = Integer.valueOf(list.get(0).toString()).intValue();
		}
		if (no < 0) {
			no = 0;
		}
		return no;
	}

	/**
	 * 返回手册商品归并资料
	 * 
	 * @param type
	 *            物料类型
	 * @return List 是DzscInnerMergeData型，手册商品归并资料
	 */
	public List findDzscInnerMergeDataByType(String type) {
		return this
				.find(
						" select a from DzscInnerMergeData as a left join fetch a.materiel "
								+ " left join fetch a.dzscTenInnerMerge "
								+ " left join fetch a.dzscTenInnerMerge.dzscFourInnerMerge "
								+ "  where a.imrType = ? and a.company.id=? ",
						new Object[] { type, CommonUtils.getCompany().getId() });

	}

	/**
	 * 返回手册商品归并资料
	 * 
	 * @param type
	 *            物料类型
	 * @return List 是DzscInnerMergeData型，手册商品归并资料
	 */
	public List findDzscInnerMergeData(String type, String stateMark,
			boolean isChange) {
		return this
				.find(
						" select a from DzscInnerMergeData as a left join fetch a.materiel "
								+ " left join fetch a.dzscTenInnerMerge "
								+ " left join fetch a.dzscTenInnerMerge.dzscFourInnerMerge "
								+ "  where a.imrType = ? and a.stateMark=? "
								+ " and a.isChange =?  and a.company.id=? ",
						new Object[] { type, stateMark, isChange,
								CommonUtils.getCompany().getId() });

	}

	/**
	 * 返回手册商品归并资料
	 * 
	 * @param type
	 *            物料类型
	 * @return List 是DzscInnerMergeData型，手册商品归并资料
	 */
	public List findDzscInnerMergeData(String type, boolean isChange) {
		return this
				.find(
						" select a from DzscInnerMergeData as a left join fetch a.materiel "
								+ " left join fetch a.dzscTenInnerMerge "
								+ " left join fetch a.dzscTenInnerMerge.dzscFourInnerMerge "
								+ "  where a.imrType = ? "
								+ " and a.isChange =?  and a.company.id=? ",
						new Object[] { type, isChange,
								CommonUtils.getCompany().getId() });

	}

	public List findInnerMergedTenSeqNum(String type) {
		return this
				.find(
						" select distinct a.dzscTenInnerMerge from DzscInnerMergeData a "
								+ " left join fetch a.dzscTenInnerMerge.dzscFourInnerMerge "
								+ " where a.imrType = ? and a.company.id=? and a.isChange=? "
								+ " and a.dzscTenInnerMerge is not null"
								+ " order by a.dzscTenInnerMerge.tenSeqNum",
						new Object[] { type, CommonUtils.getCompany().getId(),
								false });
	}

	public List findInnerMergedTenSeqNum(String meterialYype, String sFields,
			Object obj) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = " select distinct a.dzscTenInnerMerge from DzscInnerMergeData a "
				+ " left join fetch a.dzscTenInnerMerge.dzscFourInnerMerge "
				+ " where a.imrType = ? and a.company.id=? and a.isChange=? "
				+ " and a.dzscTenInnerMerge is not null  ";
		parameters.add(meterialYype);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(false);
		if (obj != null) {
			if (sFields.equals("No")) {
				hql += " and a.dzscTenInnerMerge.tenSeqNum=? ";
				parameters.add(Integer.parseInt(obj.toString()));
			} else {
				hql += " and a.dzscTenInnerMerge.tenComplex.code=? ";
				parameters.add(obj.toString());
			}
		}
		hql += " order by a.dzscTenInnerMerge.tenSeqNum";
		// if (sFields.equals("No")) {
		// if (obj == null) {
		// str = " select distinct a.dzscTenInnerMerge from DzscInnerMergeData a
		// "
		// + " left join fetch a.dzscTenInnerMerge "
		// + " left join fetch a.dzscTenInnerMerge.dzscFourInnerMerge "
		// + " where a.imrType = ? and a.company.id=? and a.isChange=? "
		// + " and a.dzscTenInnerMerge is not null "
		// + " order by a.dzscTenInnerMerge.tenSeqNum";
		// objecs = new Object[] { meterialYype,
		// CommonUtils.getCompany().getId(), false };
		// } else {
		// str = " select distinct a.dzscTenInnerMerge from DzscInnerMergeData a
		// "
		// + " left join fetch a.dzscTenInnerMerge "
		// + " left join fetch a.dzscTenInnerMerge.dzscFourInnerMerge "
		// + " where a.imrType = ? and a.company.id=? and a.isChange=? "
		// + " and a.dzscTenInnerMerge.tenSeqNum=? "
		// + " order by a.dzscTenInnerMerge.tenSeqNum";
		// objecs = new Object[] { meterialYype,
		// CommonUtils.getCompany().getId(), false, (Integer) obj };
		// }
		// } else {
		// if (obj == null) {
		// str = " select distinct a.dzscTenInnerMerge from DzscInnerMergeData a
		// "
		// + " left join fetch a.dzscTenInnerMerge "
		// + " left join fetch a.dzscTenInnerMerge.dzscFourInnerMerge "
		// + " where a.imrType = ? and a.company.id=? and a.isChange=? "
		// + " and a.dzscTenInnerMerge is not null "
		// + " order by a.dzscTenInnerMerge.tenSeqNum";
		// objecs = new Object[] { meterialYype,
		// CommonUtils.getCompany().getId(), false };
		// } else {
		// str = " select distinct a.dzscTenInnerMerge from DzscInnerMergeData a
		// "
		// + " left join fetch a.dzscTenInnerMerge "
		// + " left join fetch a.dzscTenInnerMerge.dzscFourInnerMerge "
		// + " where a.imrType = ? and a.company.id=? and a.isChange=? "
		// + " and a.dzscTenInnerMerge.tenComplex.code=? "
		// + " order by a.dzscTenInnerMerge.tenSeqNum";
		// objecs = new Object[] { meterialYype,
		// CommonUtils.getCompany().getId(), false, (String) obj };
		// }
		// }
		return this.find(hql, parameters.toArray());

	}

	/**
	 * 海关帐表体新增
	 * 
	 * @param type
	 * @return
	 */
	public List findInnerMergeDataByType(String type) {
		return this
				.find(
						" select a from DzscInnerMergeData as a left join fetch a.materiel "
								+ "  where a.imrType = ? and a.isChange=?  and a.company.id=? ",
						new Object[] { type, false,
								CommonUtils.getCompany().getId() });

	}

	public List findDzscReverseMergeFourData(String materielType) {
		return this.find(
				" slect a from DzscReverseMergeFourData a where a.imrType=? "
						+ "adn and a.company.id=? ", new Object[] {
						materielType, CommonUtils.getCompany().getId() });
	}

	public List findDzscReverseMergeBeforeData(int materielType) {
		return this.find(
				" slect a from DzscReverseMergeBeforeData a where a.imrType=? "
						+ "adn and a.company.id=? ", new Object[] {
						materielType, CommonUtils.getCompany().getId() });

	}

	public List findDzscReverseMergeTenData(int materielType) {
		return this
				.find(
						" slect a from DzscReverseMergeTenData a where a.reverseMergeFourData.imrType=? "
								+ "adn and a.company.id=? ",
						new Object[] { materielType,
								CommonUtils.getCompany().getId() });

	}

	public int findMaxDzscInnerMergeDataSepNum(String type) {
		int no = 0;
		List list = this
				.find(
						"select max(a.hsFourNo) from DzscInnerMergeData a where a.imrType=? and  a.company.id=?",
						new Object[] { type, CommonUtils.getCompany().getId() });
		if (list.size() < 1 || list.get(0) == null) {
			if (type.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
				no = 100000;
			}
		} else {
			no = Integer.valueOf(list.get(0).toString()).intValue();
		}
		if (no < 0) {
			no = 0;
		}
		return no;
	}

	public List findDzscInnerMergeDataByTenSeqNum(Integer tenSeqNum,
			String materialType, String stateMark) {
		return this.find("select  a from  DzscInnerMergeData  a"
				+ " where a.company.id=? and a.dzscTenInnerMerge.tenSeqNum=?  "
				+ "and a.stateMark=?  and a.beforeModifyMark <> ? "
				+ " and a.imrType=? ", new Object[] {
				CommonUtils.getCompany().getId(), tenSeqNum, stateMark,
				ModifyMarkState.DELETED, materialType });
	}

	// public List findAllDzscInnerMergeDataByTenSeqNum(Integer tenSeqNum) {
	// return this
	// .find("select a from DzscInnerMergeData a"
	// + " where a.company.id=? and a.tenSeqNum=? "
	// + "and a.stateMark=? ", new Object[] {
	// CommonUtils.getCompany().getId(), tenSeqNum,
	// DzscState.CHANGE });
	// }

	/**
	 * 查询所有未归并的资料
	 * 
	 * @param materielType
	 * @param isChange
	 * @return
	 */
	public List findDzscNotMergeInnerMergeData(String materielType,
			boolean isChange) {
		return this
				.find(
						" select a from DzscInnerMergeData as a left join fetch a.materiel "
								+ "  where a.imrType = ? and a.isChange=?  and a.company.id=? "
								+ " and a.dzscTenInnerMerge is null ",
						new Object[] { materielType, isChange,
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 删除所有未归并的资料
	 * 
	 * @param materielType
	 * @param isChange
	 * @return
	 */
	public void deleteDzscNotMergeInnerMergeData(String materielType,
			boolean isChange) {
		this.batchUpdateOrDelete(" delete from DzscInnerMergeData  "
				+ "  where imrType = ? and isChange=?  and company.id=? "
				+ " and dzscTenInnerMerge is null ", new Object[] {
				materielType, isChange, CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询内部归并导入文件栏位对应次序
	 * 
	 * @return
	 */
	public DzscInnerMergeDataOrder findDzscInnerMergeDataOrder() {
		List list = this.find(" select a from DzscInnerMergeDataOrder as a "
				+ "  where a.company.id=? ", new Object[] { CommonUtils
				.getCompany().getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return (DzscInnerMergeDataOrder) list.get(0);
		} else {
			DzscInnerMergeDataOrder order = new DzscInnerMergeDataOrder();
			this.saveOrUpdate(order);
			return order;
		}
	}

	/**
	 * 保存内部归并导入文件栏位对应次序
	 * 
	 * @param order
	 */
	public void saveDzscInnerMergeDataOrder(DzscInnerMergeDataOrder order) {
		this.saveOrUpdate(order);
	}

	/**
	 * 根据10码抓取10码的资料
	 * 
	 * @param tenInnerMerge
	 * @return
	 */
	public List findDzscInnerMergeDataTenSeqNumAndMateriel(String imrType,
			String stateMark) {
		return this.find(" select a.materiel,a.dzscTenInnerMerge.tenSeqNum"
				+ " from DzscInnerMergeData a "
				+ " where a.company.id=? and a.imrType=? "
				+ " and a.stateMark=? ", new Object[] {
				CommonUtils.getCompany().getId(), imrType, stateMark });
	}

	/**
	 * 查询报关单耗表头成品资料
	 * 
	 * @return
	 */
	public List findDzscCustomsBomExg() {
		return this.find(" select a from DzscCustomsBomExg a "
				+ " where a.company.id=?", new Object[] { CommonUtils
				.getCompany().getId() });
	}

	/**
	 * 查询报关单耗表头成品资料
	 * 
	 * @return
	 */
	public List findDzscCustomsBomExgByTenSeqNum(Integer tenSeqNum) {
		return this.find(" select a from DzscCustomsBomExg a "
				+ " where a.company.id=? and a.tenSeqNum=? ", new Object[] {
				CommonUtils.getCompany().getId(), tenSeqNum });
	}

	/**
	 * 查询报关单耗表头成品资料
	 * 
	 * @return
	 */
	public List findDzscCustomsBomExgByComplex(Complex complex) {
		return this.find(" select a from DzscCustomsBomExg a "
				+ " where a.company.id=? and a.complex.id=? ", new Object[] {
				CommonUtils.getCompany().getId(), complex.getId() });
	}

	/**
	 * 查询报关单耗表头成品资料
	 * 
	 * @return
	 */
	public List findDzscCustomsBomExgTenSeqNum() {
		return this.find(" select a.tenSeqNum from DzscCustomsBomExg a "
				+ " where a.company.id=?", new Object[] { CommonUtils
				.getCompany().getId() });
	}

	/**
	 * 查询报关单耗明细资料
	 * 
	 * @param dzscCustomsBomExg
	 * @return
	 */
	public List findDzscCustomsBomDetail(DzscCustomsBomExg dzscCustomsBomExg) {
		return this.find(" select a from DzscCustomsBomDetail a "
				+ " where a.company.id=? and a.dzscCustomsBomExg.id=? ",
				new Object[] { CommonUtils.getCompany().getId(),
						dzscCustomsBomExg.getId() });
	}

	/**
	 * 查询报关单耗明细资料
	 * 
	 * @param dzscCustomsBomExg
	 * @return
	 */
	public List findDzscCustomsBomDetailTenSeqNum(
			DzscCustomsBomExg dzscCustomsBomExg) {
		return this.find(" select a.tenSeqNum from DzscCustomsBomDetail a "
				+ " where a.company.id=? and a.dzscCustomsBomExg.id=? ",
				new Object[] { CommonUtils.getCompany().getId(),
						dzscCustomsBomExg.getId() });
	}
}
