package com.bestway.fecav.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.FecavState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.fecav.entity.BillOfExchange;
import com.bestway.fecav.entity.FecavBill;
import com.bestway.fecav.entity.FecavBillStrike;
import com.bestway.fecav.entity.FecavParameters;
import com.bestway.fecav.entity.ImpCustomsDeclaration;
import com.bestway.fecav.entity.StrikeBillOfExchange;
import com.bestway.fecav.entity.StrikeImpCustomsDeclaration;

public class FecavDao extends BaseDao {
	/**
	 * 查询外汇核销单参数
	 * 
	 * @return
	 */
	public FecavParameters findFecavParameters() {
		List list = this.find(
				"select a from FecavParameters a where a.company.id= ? ",
				new Object[] { CommonUtils.getCompany().getId() });
		if (list.size() > 0) {
			return (FecavParameters) list.get(0);
		} else {
			FecavParameters p = new FecavParameters();
			p.setImpLowestMoney(0.0);
			p.setImpWhiteBillUseOnce(false);
			p.setFecavControlDigitsNum(2);
			this.saveFecavParameters(p);
			return p;
		}
	}

	/**
	 * 保存外汇核销单参数
	 * 
	 * @param fecavParameters
	 */
	public void saveFecavParameters(FecavParameters fecavParameters) {
		this.saveOrUpdate(fecavParameters);
	}

	/**
	 * 根据状态查询外汇核销单
	 * 
	 * @param fecavState
	 *            单据状态
	 * @return 与指定的状态匹配没有遗失作废的外汇核销单
	 */
	public List findFecavBillByState(int fecavState) {
		return this.find("select a from FecavBill a where a.company.id= ? "
				+ " and a.billState=? "
				+ " and (a.isBlankOut=? or a.isBlankOut is null) ",
				new Object[] { CommonUtils.getCompany().getId(), fecavState,
						false });
	}

	/**
	 * 根据状态查询外汇核销单
	 * 
	 * @param fecavState
	 *            单据状态
	 * @param condition
	 *            查询条件
	 * @param lsParam
	 *            是否参数
	 * @return 与指定条件匹配的没有遗失作废的外汇核销单
	 */
	public List findFecavBillByState(int fecavState, String condition,
			List<Object> lsParam) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from FecavBill a where a.company.id= ? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (condition != null && !"".equals(condition.trim())) {
			hql += condition;
			parameters.addAll(lsParam);
		}
		hql += " and a.billState=?  and (a.isBlankOut=? or a.isBlankOut is null) ";
		parameters.add(fecavState);
		parameters.add(false);
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 查询外汇核销单(所有)
	 * 
	 * @return 没有遗失作废的所有外汇核销单
	 */
	public List findFecavBill() {
		return this.find("select a from FecavBill a where a.company.id= ? "
				+ " and (a.isBlankOut=? or a.isBlankOut is null) ",
				new Object[] { CommonUtils.getCompany().getId(), false });
	}

	/**
	 * 查询外汇核销单(所有)
	 * 
	 * @param condition
	 *            查询条件
	 * @param lsParam
	 *            是否参数
	 * @return 与查询条件匹配按核销单号升序排列的没有遗失作废的外汇核销单
	 */
	public List findFecavBill(String condition, List<Object> lsParam) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from FecavBill a where a.company.id= ? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (condition != null) {
			hql += condition;
			parameters.addAll(lsParam);
		}
		hql += " and (a.isBlankOut=? or a.isBlankOut is null) order by a.code asc ";
		parameters.add(false);
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 查询外汇核销单不包括外部领用
	 * 
	 * @param condition
	 *            查询条件
	 * @param lsParam
	 *            是否参数
	 * @return 与条件匹配不包括外部领用的没有遗失作废的外汇核销单
	 */
	public List findFecavBillNotOuterObtain(String condition,
			List<Object> lsParam) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from FecavBill a  where a.company.id= ?  ";
		parameters.add(CommonUtils.getCompany().getId());
		if (condition != null) {
			hql += condition;
			parameters.addAll(lsParam);
		}
		hql += " and a.billState != ? and (a.isBlankOut=? or a.isBlankOut is null) order by a.code asc ";
		parameters.add(FecavState.OUTER_OBTAIN);
		parameters.add(false);
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 查找出口日期为空的使用单据
	 * 
	 * @param condition
	 *            查询条件
	 * @param lsParam
	 *            是否参数
	 * @return 正在使用的出口日期为空没有作废的外汇核销单
	 */
	public List findFecavBillNotUsed(String condition, List<Object> lsParam) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from FecavBill a where a.company.id= ? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (condition != null && !"".equals(condition.trim())) {
			hql += condition;
			parameters.addAll(lsParam);
		} else {
			hql += " and a.exportDate is null ";
		}
		hql += " and a.billState = ? order by a.code asc ";
		parameters.add(FecavState.USED);
		// parameters.add(false);
		return this.find(hql, parameters.toArray());
		// return this.find(" "
		// + " and a.billState=? and a.exportDate is null "
		// + " and (a.isBlankOut=? or a.isBlankOut is null)",
		// new Object[] { CommonUtils.getCompany().getId(),
		// FecavState.USED, false });
	}

	/**
	 * 查找已使用未输入出口日期的核销单
	 * 
	 * @param condition
	 *            查询条件
	 * @param lsParam
	 *            是否参数
	 * @return 正在使用的出口日期不为空的没有遗失作废的按外汇核销单号升序排列的外汇核销单
	 */
	public List findFecavBillByUsedBefore(String condition, List<Object> lsParam) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from FecavBill a where a.company.id= ? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (condition != null && !"".equals(condition.trim())) {
			hql += condition;
			parameters.addAll(lsParam);
		}
		hql += (" and a.billState = ? order by a.code asc ");
		parameters.add(FecavState.USED);
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 查找出口日期不为空的使用单据
	 * 
	 * @param condition
	 *            查询条件
	 * @param lsParam
	 *            是否参数
	 * @return 正在使用的出口日期不为空的没有遗失作废的按外汇核销单号升序排列的外汇核销单
	 */
	public List findFecavBillByUsedAfter(String condition, List<Object> lsParam) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from FecavBill a where a.company.id= ? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (condition != null && !"".equals(condition.trim())) {
			hql += condition;
			parameters.addAll(lsParam);
		}
		hql += (" and a.billState = ? and a.exportDate is not null  "
				+ " order by a.code asc ");
		parameters.add(FecavState.USED);
		// parameters.add(false);
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 根据ID查询外汇核销单
	 * 
	 * @param id
	 *            外汇核销单id
	 * @return 与指定id匹配的外汇核销单
	 */
	public FecavBill findFecavBillById(String id) {
		return (FecavBill) this.load(FecavBill.class, id);
	}

	/**
	 * 查询外汇核销单份数
	 * 
	 * @param code
	 *            外汇核销单号
	 * @return 外汇核销单份数
	 */
	public int findFecavBillCount(String code) {
		List list = this.find(
				"select count(*) from FecavBill a where a.company.id= ? "
						+ " and a.code=? ", new Object[] {
						CommonUtils.getCompany().getId(), code });

		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 查询外汇核销单的份数
	 * 
	 * @param code
	 *            外汇核销单号
	 * @return 出口日期不为空的外汇核销单的份数
	 */
	public int findFecavBillByCodeCount(String code) {
		List list = this.find(
				"select count(*) from FecavBill a where a.company.id= ? "
						+ " and a.code=? and a.exportDate is not null ",
				new Object[] { CommonUtils.getCompany().getId(), code });

		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 查找外汇核消单来自核销单号和核消单状态
	 * 
	 * @param code
	 *            核销单号
	 * @param fecavState
	 *            单据状态
	 * @return 与指定的状态和核销单号匹配没有遗失作废的外汇核销单
	 */
	public FecavBill findFecavBill(String code, int fecavState) {
		List list = this
				.find(
						"select a from FecavBill a where a.company.id= ? "
								+ " and a.billState=? "
								+ " and (a.isBlankOut=? or a.isBlankOut is null) and a.code = ? ",
						new Object[] { CommonUtils.getCompany().getId(),
								fecavState, false, code });
		if (list.size() > 0 && list.get(0) != null) {
			return (FecavBill) list.get(0);
		}
		return null;
	}

	/**
	 * 查找外汇核消单来自核销单号 内部领单
	 * 
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
	 * @return 内部领单没有遗失作废的核销单号
	 */
	public List findFecavBillNotCustomsDeclaration(int index, int length,
			String property, Object value, Boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from FecavBill a where a.company.id= ? "
				+ " and a.billState=? "
				+ " and (a.isBlankOut=? or a.isBlankOut is null) "
				+ " and (a.customsDeclarationId is null or a.customsDeclarationId  = ? )";
		// + " and a.code not in "
		// + " ( select c.authorizeFile from CustomsDeclaration as c where
		// c.company.id = ? and c.authorizeFile is not null and ( c.effective =
		// ? or c.effective is null ))"
		// + " and a.code not in "
		// + " ( select b.authorizeFile from BcsCustomsDeclaration as b where
		// b.company.id = ? and b.authorizeFile is not null and ( b.effective =
		// ? or b.effective is null ) ) "
		// + " and a.code not in "
		// + " ( select d.authorizeFile from DzbaCustomsDeclaration as d where
		// d.company.id = ? and d.authorizeFile is not null and ( d.effective =
		// ? or d.effective is null ) ) ";

		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(FecavState.INNER_OBTAIN);
		paramters.add(false);
		paramters.add("");
		// paramters.add(CommonUtils.getCompany());
		// paramters.add(false);
		// paramters.add(CommonUtils.getCompany());
		// paramters.add(false);
		// paramters.add(CommonUtils.getCompany());
		// paramters.add(false);

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
		}
		hsql += " order by a.code asc ";
		return this.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 分页查询核销单明细
	 * 
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
	 * @return 没有遗失作废的单据明细
	 */
	public List findFecavBillDetail(int index, int length, String property,
			Object value, Boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from FecavBill a where a.company.id= ? "
				+ " and (a.isBlankOut=? or a.isBlankOut is null) ";
		paramters.add(CommonUtils.getCompany().getId());
		// paramters.add(FecavState.INNER_OBTAIN);
		paramters.add(false);

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
		}
		hsql += " order by a.code asc ";
		return this.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 保存外汇核销单
	 * 
	 * @param fecavBill
	 *            外汇核销单
	 */
	public void saveFecavBill(FecavBill fecavBill) {
		this.saveOrUpdate(fecavBill);
	}

	/**
	 * 保存外汇核销单
	 * 
	 * @param fecavBillList
	 *            批量的外汇核销单
	 */
	public void saveFecavBill2(List fecavBillList) {
		for (int i = 0; i < fecavBillList.size(); i++) {
			FecavBill fecavBill = (FecavBill) fecavBillList.get(i);
			this.saveOrUpdate(fecavBill);
		}
	}

	/**
	 * 保存外汇核销单
	 * 
	 * @param fecavBillList
	 *            批量的外汇核销单
	 */
	public void saveFecavBill(List<FecavBill> fecavBillList) {
		for (FecavBill fecavBill : fecavBillList) {
			this.saveOrUpdate(fecavBill);
		}
	}

	/**
	 * 删除外汇核销单
	 * 
	 * @param fecavBill
	 *            外汇核销单
	 */
	public void deleteFecavBill(FecavBill fecavBill) {
		this.delete(fecavBill);
	}

	/**
	 * 取得未与外汇核销单建立联系的报关单信息
	 * 
	 * @param projectType
	 *            模块类型
	 * @return 没有与外汇核销单建立联系的报关单
	 */
	public List findCustomsDeclaration(int projectType) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a  from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = "select a  from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = "select a  from DzscCustomsDeclaration as a";
			break;
		default:
			hql = "select a  from CustomsDeclaration as a";
			break;
		}
		hql = hql
				+ " where a.company.id = ? and a.id not in"
				+ " (select b.customsDeclarationId from FecavBill as b"
				+ " where b.company.id = ? and b.customsDeclarationId is not null )"
				+ " and a.customsDeclarationCode is not null"
				+ " and a.customsDeclarationCode != ? and a.impExpFlag = ? ";
		return this.find(hql, new Object[] { CommonUtils.getCompany().getId(),
				CommonUtils.getCompany().getId(), "",
				Integer.valueOf(ImpExpFlag.EXPORT) });
	}

	/**
	 * 取得报关单商品总金额
	 * 
	 * @param projectType
	 *            模块类型
	 * @param customsDeclarationId
	 *            报关单id
	 * @return 与指定的报关单id匹配的报关单的商品总金额
	 */
	public double findCustomsDeclarationMoney(int projectType,
			String customsDeclarationId) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select sum(a.commTotalPrice) from CustomsDeclarationCommInfo a ";
			break;
		case ProjectType.BCS:
			hql = "select sum(a.commTotalPrice) from BcsCustomsDeclarationCommInfo a ";
			break;
		case ProjectType.DZSC:
			hql = "select sum(a.commTotalPrice) from DzscCustomsDeclarationCommInfo a ";
			break;
		default:
			hql = "select sum(a.commTotalPrice) from CustomsDeclarationCommInfo a ";
			break;
		}
		hql += " where a.baseCustomsDeclaration.company.id=?"
				+ " and a.baseCustomsDeclaration.id=? ";
		List list = this.find(hql, new Object[] {
				CommonUtils.getCompany().getId(), customsDeclarationId });
		if (list.size() < 0 || list.get(0) == null) {
			return 0.0;
		} else {
			return Double.parseDouble(list.get(0).toString());
		}
	}

	/**
	 * 查询银行汇票
	 * 
	 * @return 银行汇票
	 */
	public List findBillOfExchange() {
		return this.find(
				"select a from BillOfExchange a where a.company.id= ? ",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询银行汇票没有被使用的记录
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return 在有效期内没有被使用的银行汇票
	 */
	public List findBillOfExchangeNotUse(Date beginDate, Date endDate) {
		String hsql = "select a from BillOfExchange a where a.company.id= ?  "
				+ " and a.id not in (select s.billOfExchangeId "
				+ " from StrikeBillOfExchange s where s.company.id = ? )";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hsql += " and a.endDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.endDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 根据编号查询银行汇票数目
	 * 
	 * @param code
	 *            汇票号码
	 * @return 与指定汇票号码匹配的银行汇票
	 */
	public int findBillOfExchangeCountByCode(String code) {
		List list = this.find(
				"select a from BillOfExchange a where a.company.id= ? "
						+ " and a.code=? ", new Object[] {
						CommonUtils.getCompany().getId(), code });
		if (list.size() < 1 || list.get(0) == null) {
			return 0;
		} else {
			return Integer.parseInt(list.get(0).toString());
		}
	}

	/**
	 * 根据编号,操作员,汇日期查询银行汇票
	 * 
	 * @param code
	 *            汇票号码
	 * @param operator
	 *            操作员
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 与指定条件匹配的银行汇票
	 */
	public List findBillOfExchange(String code, String operator,
			Date beginDate, Date endDate) {
		List<Object> property = new ArrayList();
		String str = "select a from BillOfExchange a where a.company.id= ? ";
		property.add(CommonUtils.getCompany().getId());
		if (!(code == null || code.equals(""))) {
			str += " and a.code=? ";
			property.add(code);
		}
		if (!(operator == null || operator.equals(""))) {
			str += " and a.operator=? ";
			property.add(operator);
		}
		if (beginDate != null) {
			str += " and a.endDate>=? ";
			property.add(beginDate);
		}
		if (endDate != null) {
			str += " and a.endDate<=? ";
			property.add(endDate);
		}
		return this.find(str, property.toArray());
	}

	/**
	 * 根据编号查询银行汇票数目
	 * 
	 * @param id
	 *            银行汇票id
	 * @return 银行汇票
	 */
	public BillOfExchange findBillOfExchangeById(String id) {
		List list = this.find(
				"select a from BillOfExchange a where a.company.id= ? "
						+ " and a.id=? ", new Object[] {
						CommonUtils.getCompany().getId(), id });
		if (list.isEmpty()) {
			return null;
		} else {
			return (BillOfExchange) list.get(0);
		}
	}

	/**
	 * 保存汇票
	 * 
	 * @param bill
	 *            银行汇票
	 */
	public void saveBillOfExchange(BillOfExchange bill) {
		this.saveOrUpdate(bill);
	}

	/**
	 * 删除汇票
	 * 
	 * @param bill
	 *            银行汇票
	 */
	public void deleteBillOfExchange(BillOfExchange bill) {
		this.delete(bill);
	}

	/**
	 * 查询已经作废的外汇核销单
	 * 
	 * @param condition
	 *            查询条件
	 * @param lsParam
	 *            是否参数
	 * @return 已经作废的外汇核销单
	 */
	public List findIsBlankOutFecavBill(String condition, List<Object> lsParam) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from FecavBill a where a.company.id= ? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (condition != null && !"".equals(condition.trim())) {
			hql += condition;
			parameters.addAll(lsParam);
		}
		hql += " and a.isBlankOut=? ";
		parameters.add(true);
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 查询没有作废的外汇核销单
	 * 
	 * @return 没有作废的外汇核销单
	 */
	public List findNotBlankOutFecavBill() {
		return this.find("select a from FecavBill a where a.company.id= ? "
				+ " and (a.isBlankOut=? or a.isBlankOut is null)",
				new Object[] { CommonUtils.getCompany().getId(), false });
	}

	/**
	 * 根据外汇核销单抓取已冲销的进口报关单
	 * 
	 * @param fecavBillStrike
	 *            外汇核销单冲销
	 * @return 与指定的外汇核销单冲销id匹配的进出口报关单
	 */
	public List findBrikeImpCustomsDeclaration(FecavBillStrike fecavBillStrike) {
		return this
				.find(
						"select a from StrikeImpCustomsDeclaration a where a.fecavBillStrike.id= ? ",
						new Object[] { fecavBillStrike.getId() });
	}

	/**
	 * 查询已冲销的进口报关单的数量
	 * 
	 * @param customsDeclarationId
	 *            报关单id
	 * @return 白单号不为空的冲销的进出口报关单的数量
	 */
	public int findStrikeImpCustomsDeclarationCount(String customsDeclarationId) {
		List list = this.find(
				"select count(*) from StrikeImpCustomsDeclaration a where a.company.id= ? "
						+ "  and a.whiteBillNo is not null "
						+ " and a.customsDeclarationId = ? ",
				new Object[] { CommonUtils.getCompany().getId(),
						customsDeclarationId });

		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 查询外汇核销单的进口报关单冲销信息
	 * 
	 * @return 外汇核销单为空白单号不为空的进口报关单冲销信息
	 */
	public List<StrikeImpCustomsDeclaration> findBrikeImpCustomsDeclarationToStrike() {
		return this.find("select a from StrikeImpCustomsDeclaration a where "
				+ " a.fecavBillStrike is null and a.whiteBillNo is not null ");
		// and a.whiteBillNo != ?,
		// new Object[] { "" });
	}

	/**
	 * 根据条件查询外汇核销单的进口报关单冲销信息
	 * 
	 * @param condition
	 *            查询条件
	 * @param lsParam
	 *            是否参数
	 * @return 外汇核销单为空的进口报关单冲销信息
	 */
	public List<ImpCustomsDeclaration> findImpCustomsDeclarationNotCancel(
			String condition, List<Object> lsParam) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from ImpCustomsDeclaration a "
				+ "where a.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (condition != null && !"".equals(condition.trim())) {
			hql += condition;
			parameters.addAll(lsParam);
		}
		hql += " order by a.declareDate,a.customsDeclarationCode";
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 根据报关单id查询外汇核销单的进口报关单冲销信息
	 * 
	 * @param customsDeclarationId
	 *            报关单id
	 * @return 与指定的报关单id匹配且白单号为空的进口报关单冲销信息
	 */
	public List findStrikeImpCustomsDeclaration(String customsDeclarationId) {
		List list = this.find(
				"select a from StrikeImpCustomsDeclaration a where a.company.id= ? "
						+ " and ( a.whiteBillNo is null )"
						+ " and a.customsDeclarationId = ? ",
				new Object[] { CommonUtils.getCompany().getId(),
						customsDeclarationId });
		return list;
	}

	/**
	 * 根据白单号查询进口报关单冲销信息
	 * 
	 * @param whiteBillNo
	 *            白单号
	 * @return 与指定的白单号匹配的进口报关单冲销信息
	 */
	public List findImpCustomsDeclarationByWhiteBillNo(String whiteBillNo) {
		List list = this.find(
				"select a from ImpCustomsDeclaration a where a.company.id= ? "
						+ " and a.whiteBillNo = ? ", new Object[] {
						CommonUtils.getCompany().getId(), whiteBillNo });
		// if (list.size() > 0 && list.get(0) != null) {
		// return (StrikeImpCustomsDeclaration) list.get(0);
		// }
		// return null;
		return list;
	}

	/**
	 * 查询进口报关单的冲销信息
	 * 
	 * @param condition
	 *            查询条件
	 * @param lsParam
	 *            是否参数
	 * @return 外汇核销单不为空的进口报关单冲销信息
	 */
	public List<StrikeImpCustomsDeclaration> findImpCustomsDeclarationIsCancel(
			String condition, List<Object> lsParam) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from StrikeImpCustomsDeclaration a where "
				+ " a.company.id=?  ";
		parameters.add(CommonUtils.getCompany().getId());
		if (condition != null && !"".equals(condition.trim())) {
			hql += condition;
			parameters.addAll(lsParam);
		}
		hql += " and a.fecavBillStrike.fecavState<>? ";
		parameters.add(FecavState.CONTROL);
		hql += " order by a.declareDate,a.customsDeclarationCode";
		return this.find(hql, parameters.toArray());
	}

	public List<StrikeImpCustomsDeclaration> findImpCustomsDeclarationIsCancelCount(
			String customsDeclarationCode) {
		List parameters = new ArrayList();
		String hql = "select Count(a) from StrikeImpCustomsDeclaration a where "
				+ "  a.company.id=?   and  a.customsDeclarationCode = ?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(customsDeclarationCode);
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 查询进口报关单的冲销信息
	 * 
	 * @param condition
	 *            查询条件
	 * @param lsParam
	 *            是否参数
	 * @return 外汇核销单不为空的进口报关单冲销信息
	 */
	public void deleteOldImpCustomsDeclaration() {
		List parameters = new ArrayList();
		String hql = "delete from StrikeImpCustomsDeclaration where "
				+ "  company=?  and fecavBillStrike is null";
		parameters.add(CommonUtils.getCompany());
		this.batchUpdateOrDelete(hql, parameters.toArray());
	}

	/**
	 * 保存进口报关单冲销
	 * 
	 * @param imp
	 *            进口报关单冲销
	 */
	public void saveBrikeImpCustomsDeclaration(StrikeImpCustomsDeclaration imp) {
		this.saveOrUpdate(imp);
	}

	/**
	 * 保存进口报关单冲销
	 * 
	 * @param imp
	 *            进口报关单冲销
	 */
	public void saveImpCustomsDeclaration(ImpCustomsDeclaration imp) {
		this.saveOrUpdate(imp);
	}

	/**
	 * 保存进口报关单冲销
	 * 
	 * @param imp
	 *            批量进口报关单冲销
	 */
	public void saveBrikeImpCustomsDeclaration(
			List<StrikeImpCustomsDeclaration> imp) {
		for (StrikeImpCustomsDeclaration s : imp) {
			this.saveOrUpdate(s);
		}
	}

	/**
	 * 删除进口报关单冲销
	 * 
	 * @param customsDeclarationId
	 *            报关单id
	 */
	public void deleteImpCustomsDeclaration(String customsDeclarationId) {
		ImpCustomsDeclaration imp = this
				.findImpCustomsDeclaration(customsDeclarationId);
		this.delete(imp);
		List list = this.findStrikeImpCustomsDeclaration(customsDeclarationId);
		this.deleteAll(list);
	}

	/**
	 * 删除进口报关单冲销
	 * 
	 * @param imp
	 *            进口报关单冲销
	 */
	public void deleteBrikeImpCustomsDeclaration(StrikeImpCustomsDeclaration imp) {
		this.delete(imp);
	}

	/**
	 * 根据外汇核销单抓取已冲销的汇票
	 * 
	 * @param fecavBillStrike
	 *            外汇核销单
	 * @return 已冲销的汇票
	 */
	public List findBrikeBillOfExchange(FecavBillStrike fecavBillStrike) {
		return this
				.find(
						"select a from StrikeBillOfExchange a where a.fecavBillStrike.id= ? and a.company.id=? ",
						new Object[] { fecavBillStrike.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据外汇核销单抓取已冲销的汇票(所有)
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内所有已冲销的汇票
	 */
	public List findBrikeBillOfExchange(Date beginDate, Date endDate) {
		String hsql = "select a from BillOfExchange a "
				+ " where a.id in (select b.billOfExchangeId "
				+ " from StrikeBillOfExchange b "
				+ " where b.company.id=? ) and a.company.id= ?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hsql += " and a.endDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.endDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 保存汇票冲销
	 * 
	 * @param exchange
	 *            汇票冲销
	 */
	public void saveBrikeBillOfExchange(StrikeBillOfExchange exchange) {
		this.saveOrUpdate(exchange);
	}

	/**
	 * 删除汇票冲销
	 * 
	 * @param exchange
	 *            汇票冲销
	 */
	public void deleteBrikeBillOfExchange(StrikeBillOfExchange exchange) {
		this.delete(exchange);
	}

	public ImpCustomsDeclaration findImpCustomsDeclaration(
			String customsDeclarationId) {
		List list = this.find(
				"select a from ImpCustomsDeclaration a where a.company.id=?"
						+ " and a.customsDeclarationId=? ",
				new Object[] { CommonUtils.getCompany().getId(),
						customsDeclarationId });
		if (list.size() > 0) {
			return (ImpCustomsDeclaration) list.get(0);
		} else {
			return null;
		}
	}

	public List findStrikeBillStrikeSignInNo(String customsDeclarationId) {
		return this.find("select distinct a.fecavBillStrike.signInNo "
				+ " from StrikeImpCustomsDeclaration a where a.company.id=?"
				+ " and a.customsDeclarationId=? ", new Object[] {
				CommonUtils.getCompany().getId(), customsDeclarationId });
	}

	/**
	 * 取得未与外汇核销单建立冲销的进口报关单信息
	 * 
	 * @param projectType
	 *            系统模块
	 * @return 未与外汇核销单建立冲销的直接进口结转进口的报关单信息
	 */
	public List findNotUseImpCustomsDeclaration(int projectType) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a  from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = "select a  from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = "select a  from DzscCustomsDeclaration as a";
			break;
		default:
			hql = "select a  from CustomsDeclaration as a";
			break;
		}
		hql += " where a.company.id=?"
				+ " and a.id not in "
				+ "( select s.customsDeclarationId  from ImpCustomsDeclaration as s where s.company.id = ? )"
				+ " and a.effective =? "
				+ " and ( a.impExpType=? or a.impExpType = ?) "
				+ " order by a.declarationDate,a.customsDeclarationCode";
		return this.find(hql, new Object[] { CommonUtils.getCompany().getId(),
				CommonUtils.getCompany().getId(), true,
				ImpExpType.DIRECT_IMPORT, ImpExpType.TRANSFER_FACTORY_IMPORT });
	}

	/**
	 * 取得未与外汇核销单建立冲销的进口报关单信息
	 * 
	 * @param projectType
	 *            系统模块
	 * @return 未与外汇核销单建立冲销的直接进口结转进口的报关单信息
	 */
	public List findImpCustomsDeclaration() {
		String hql = "select a from ImpCustomsDeclaration as a"
				+ " where a.company.id=? order by a.declareDate, "
				+ " a.customsDeclarationCode ";
		return this
				.find(hql, new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 取得未与外汇核销单建立冲销的进口报关单信息
	 * 
	 * @param projectType
	 *            系统模块
	 * @return 未与外汇核销单建立冲销的直接进口结转进口的报关单信息
	 */
	public List findCanStrikeImpCustomsDeclaration() {
		String hql = "select a from ImpCustomsDeclaration as a"
				+ " where a.company.id=? and a.canStrike=? "
				+ " order by a.declareDate, a.customsDeclarationCode ";
		return this.find(hql, new Object[] { CommonUtils.getCompany().getId(),
				true });
	}

	/**
	 * 根据外汇核销单查出报关单信息
	 * 
	 * @param b
	 *            外汇核销单
	 * @return 与指定的外汇核销单匹配的报关单信息
	 */
	public List findCustomsDeclarationByFecavBill(FecavBill b) {
		List lsResult = new ArrayList();
		if (b.getCustomsDeclarationId() == null) {
			return new ArrayList();
		}
		List list = this
				.find(
						"select a  from CustomsDeclaration as a where a.company.id=? and a.id = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								b.getCustomsDeclarationId() });
		List list1 = this
				.find(
						"select a  from BcsCustomsDeclaration as a where a.company.id=? and a.id = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								b.getCustomsDeclarationId() });
		List list2 = this
				.find(
						"select a  from DzscCustomsDeclaration as a where a.company.id=? and a.id = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								b.getCustomsDeclarationId() });
		lsResult.addAll(list);
		lsResult.addAll(list1);
		lsResult.addAll(list2);
		// if (list.size() > 0) {
		// BaseCustomsDeclaration c = (BaseCustomsDeclaration) list.get(0);
		// c.setImpExpDate(b.getExportDate());
		// this.saveOrUpdate(c);
		// }
		return lsResult;
	}

	/**
	 * 根据外汇核销单查出报关单信息
	 * 
	 * @param b
	 *            外汇核销单
	 * @return 与指定的外汇核销单匹配的报关单信息
	 */
	public List findCustomsDeclarationByFecavBillCode(FecavBill b) {
		List lsResult = new ArrayList();
		if (b.getCustomsDeclarationId() == null) {
			return new ArrayList();
		}
		List list = this
				.find(
						"select a  from CustomsDeclaration as a where a.company.id=? and a.authorizeFile = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								b.getCode() });
		List list1 = this
				.find(
						"select a  from BcsCustomsDeclaration as a where a.company.id=? and a.authorizeFile = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								b.getCode() });
		List list2 = this
				.find(
						"select a  from DzscCustomsDeclaration as a where a.company.id=? and a.authorizeFile = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								b.getCode() });
		lsResult.addAll(list);
		lsResult.addAll(list1);
		lsResult.addAll(list2);
		// if (list.size() > 0) {
		// BaseCustomsDeclaration c = (BaseCustomsDeclaration) list.get(0);
		// c.setImpExpDate(b.getExportDate());
		// this.saveOrUpdate(c);
		// }
		return lsResult;
	}

	/**
	 * 取得进口报关单金额
	 * 
	 * @param projectType
	 *            系统模块
	 * @return 指定模块的进口报关单金额
	 */
	public List findImpCustomsDeclarationMoney(int projectType) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select a.baseCustomsDeclaration.id,sum(a.commTotalPrice) "
					+ " from CustomsDeclarationCommInfo as a ";
			break;
		case ProjectType.BCS:
			hql = " select a.baseCustomsDeclaration.id,sum(a.commTotalPrice) "
					+ " from BcsCustomsDeclarationCommInfo as a";
			break;
		case ProjectType.DZSC:
			hql = " select a.baseCustomsDeclaration.id,sum(a.commTotalPrice) "
					+ " from DzscCustomsDeclarationCommInfo as a";
			break;
		default:
			hql = " select a.baseCustomsDeclaration.id,sum(a.commTotalPrice) "
					+ " from CustomsDeclarationCommInfo as a";
			break;
		}
		hql += " where a.baseCustomsDeclaration.company.id=?"
				+ " and a.baseCustomsDeclaration.impExpFlag=? "
				+ " group by a.baseCustomsDeclaration.id";
		return this.find(hql, new Object[] { CommonUtils.getCompany().getId(),
				Integer.valueOf(ImpExpFlag.IMPORT) });
	}

	/**
	 * 取得进口报关单金额
	 * 
	 * @param projectType
	 *            系统模块
	 * @return 指定模块的进口报关单金额
	 */
	public List findStrikeImpCustomsDeclarationMoney() {
		String hql = " select a.customsDeclarationId,sum(a.strikeMoney) "
				+ " from StrikeImpCustomsDeclaration as a "
				+ " where a.company.id=? group by a.customsDeclarationId";
		return this
				.find(hql, new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 取得进口报关单金额
	 * 
	 * @param projectType
	 *            系统模块
	 * @return 指定模块的进口报关单金额
	 */
	public double findStrikeImpCustomsDeclarationMoney(
			String customsDeclarationId) {
		String hql = " select sum(a.strikeMoney) "
				+ " from StrikeImpCustomsDeclaration as a "
				+ " where a.company.id=? and a.customsDeclarationId=? ";
		List list = this.find(hql, new Object[] {
				CommonUtils.getCompany().getId(), customsDeclarationId });
		if (list.size() > 0 && list.get(0) != null) {
			return Double.parseDouble(list.get(0).toString());
		} else {
			return 0.0;
		}
	}

	/**
	 * 取得已冲销的汇票金额
	 * 
	 * @param projectType
	 *            系统模块
	 * @return 指定模块的进口报关单金额
	 */
	public double findStrikedExchangeMoney(String billOfExchangeId) {
		String hql = " select sum(a.strikeMoney) "
				+ " from StrikeBillOfExchange as a "
				+ " where a.company.id=? and a.billOfExchangeId=? ";
		List list = this.find(hql, new Object[] {
				CommonUtils.getCompany().getId(), billOfExchangeId });
		if (list.size() > 0 && list.get(0) != null) {
			return Double.parseDouble(list.get(0).toString());
		} else {
			return 0.0;
		}
	}

	/**
	 * 取得外汇核销单已经冲销的进口报关单金额
	 * 
	 * @return 已经冲销的进口报关单金额
	 */
	public List findFecavStrikeImpMoney() {
		String hql = " select a.customsDeclarationId,sum(a.strikeMoney) "
				+ " from StrikeImpCustomsDeclaration as a "
				+ " where a.fecavBillStrike.company.id=? "
				+ " group by a.customsDeclarationId";
		return this
				.find(hql, new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 取得外汇核销单已经冲销的汇票金额
	 * 
	 * @return 已经冲销的汇票金额
	 */
	public List findFecavStrikeExchangeMoney() {
		String hql = " select a.billOfExchangeId,sum(a.strikeMoney) "
				+ " from StrikeBillOfExchange as a "
				+ " where a.fecavBillStrike.company.id=? "
				+ " group by a.billOfExchangeId";
		return this
				.find(hql, new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 取得外汇核销单已经冲销的汇票金额
	 * 
	 * @param billOfExchangeId
	 *            汇票id
	 * @return 已经冲销的汇票金额
	 */
	public double findFecavStrikeExchangeMoney(String billOfExchangeId) {
		String hql = " select sum(a.strikeMoney) "
				+ " from StrikeBillOfExchange as a "
				+ " where a.fecavBillStrike.company.id=? "
				+ " and a.billOfExchangeId=?";
		List list = this.find(hql, new Object[] {
				CommonUtils.getCompany().getId(), billOfExchangeId });
		if (list.size() <= 0 || list.get(0) == null) {
			return 0.0;
		} else {
			return Double.parseDouble(list.get(0).toString());
		}
	}

	// /**
	// * 取得外汇核销单已经冲销的进口报关单金额
	// *
	// * @param projectType
	// * @return
	// */
	// public double findFecavStrikeImpMoney(FecavBillStrike fecavBillStrike) {
	// String hql = " select sum(a.strikeMoney) "
	// + " from StrikeImpCustomsDeclaration as a "
	// + " where a.fecavBillStrike.company.id=? "
	// + " and a.fecavBillStrike.id=? ";
	// List list = this.find(hql, new Object[] {
	// CommonUtils.getCompany().getId(), fecavBillStrike.getId() });
	// if (list.size() > 0 && list.get(0) != null) {
	// return Double.parseDouble(list.get(0).toString());
	// } else {
	// return 0.0;
	// }
	// }
	//
	// /**
	// * 取得外汇核销单已经冲销的汇票金额
	// *
	// * @param projectType
	// * @return
	// */
	// public double findFecavStrikeExchangeMoney(FecavBillStrike
	// fecavBillStrike) {
	// String hql = " select sum(a.strikeMoney) "
	// + " from StrikeBillOfExchange as a "
	// + " where a.fecavBillStrikes.company.id=? "
	// + " and a.fecavBillStrike.id=?";
	// List list = this.find(hql, new Object[] {
	// CommonUtils.getCompany().getId(), fecavBillStrike.getId() });
	// if (list.size() > 0 && list.get(0) != null) {
	// return Double.parseDouble(list.get(0).toString());
	// } else {
	// return 0.0;
	// }
	// }

	/**
	 * 根据状态查询外汇核销单签收单头
	 * 
	 * @param fecavState
	 *            单据状态
	 * @return 符合指定状态的外汇核销单签收单头
	 */
	public List findFecavBillStrikeByState(int fecavState) {
		return this.find(
				"select a from FecavBillStrike a where a.company.id= ? "
						+ " and a.fecavState=? ", new Object[] {
						CommonUtils.getCompany().getId(), fecavState });
	}

	/**
	 * 根据状态查询外汇核销单签收单头
	 * 
	 * @param fecavState
	 *            单据状态
	 * @param condition
	 *            查询条件
	 * @param lsParam
	 *            是否参数
	 * @return 符合指定状态的外汇核销单签收单头
	 */
	public List findFecavBillStrikeByState(int fecavState, String condition,
			List<Object> lsParam) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from FecavBillStrike a where a.company.id= ?";
		parameters.add(CommonUtils.getCompany().getId());
		if (condition != null && !"".equals(condition.trim())) {
			hql += condition;
			parameters.addAll(lsParam);
		}
		hql += " and a.fecavState=? ";
		parameters.add(fecavState);
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 根据ID查询外汇核销单领用单头
	 * 
	 * @param id
	 *            外汇核销单id
	 * @return 外汇核销单单头
	 */
	public FecavBillStrike findFecavBillStrikeById(String id) {
		return (FecavBillStrike) this.load(FecavBillStrike.class, id);
	}

	/**
	 * 根据状态查询外汇核销单
	 * 
	 * @param fecavBillStrike
	 *            外汇核销单冲销
	 * @return 外汇核销单
	 */
	public List findFecavBillByStrike(FecavBillStrike fecavBillStrike) {
		return this.find("select a from FecavBill a where "
				+ " a.fecavBillStrike.id=? ", new Object[] { fecavBillStrike
				.getId() });
	}

	/**
	 * 根据状态查询外汇核销单没有被核销的
	 * 
	 * @return 没有被核销的外汇核销单
	 */
	public List findFecavBillNotStrike() {
		return this.find("select a from FecavBill a where a.company.id= ? "
				+ " and a.billState=? "// and a.fecavBillStrike is null
				+ " order by a.code asc ", new Object[] {
				CommonUtils.getCompany().getId(), FecavState.HAND_IN_BILL });
	}

	/**
	 * 根据状态查询外汇核销单没有被核销的
	 * 
	 * @param condition
	 *            查询条件
	 * @param lsParam
	 *            是否参数
	 * @return 没有被核销的外汇核销单
	 */
	public List findFecavBillNotStrike(String condition, List<Object> lsParam) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from FecavBill a where a.company.id= ? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (condition != null && !"".equals(condition.trim())) {
			hql += condition;
			parameters.addAll(lsParam);
		}
		hql += " and a.billState=?  and (a.isBlankOut=? or a.isBlankOut is null) ";
		parameters.add(FecavState.HAND_IN_BILL);
		parameters.add(false);
		return this.find(hql, parameters.toArray());
		// return this.find("select a from FecavBill a where a.company.id= ? "
		// + " and a.billState=? "// and a.fecavBillStrike is null
		// hj
		// + " order by a.code asc ", new Object[] {
		// CommonUtils.getCompany().getId(),FecavState.HAND_IN_BILL });
	}

	/**
	 * 保存外汇核销单
	 * 
	 * @param fecavBillStrike
	 *            外汇核销单
	 */
	public void saveFecavBillStrike(FecavBillStrike fecavBillStrike) {
		this.saveOrUpdate(fecavBillStrike);
	}

	/**
	 * 批量保存外汇核销单
	 * 
	 * @param fecavBillStrike
	 *            外汇核销单
	 */
	public void saveFecavBillStrike(List fecavBillStrike) {
		for (int i = 0; i < fecavBillStrike.size(); i++) {
			this.saveOrUpdate(fecavBillStrike.get(i));
		}
	}

	/**
	 * 删除外汇核销单
	 * 
	 * @param fecavBillStrike
	 *            外汇核销单
	 */
	public void deleteFecavBillStrike(FecavBillStrike fecavBillStrike) {
		this.delete(fecavBillStrike);
	}

	/**
	 * 根据两种比别，获取汇率
	 * 
	 * @param curr
	 *            本地币值
	 * @param curr1
	 *            外地币值
	 * @param createDate
	 *            兑取日期
	 * @return 获取汇率
	 */
	public List findExchangeRate(Curr curr, Curr curr1, Date createDate) {
		return this.find("select a from CurrRate a where a.company.id= ? "
				+ " and a.curr.code=? and a.curr1.code=?"
				+ " and a.createDate<=?  order by a.createDate desc ",
				new Object[] { CommonUtils.getCompany().getId(),
						curr.getCode(), curr1.getCode(),
						CommonUtils.getEndDate(createDate) });
	}

	/**
	 * 抓取美元货币
	 * 
	 * @return
	 */
	public Curr findUsdCurr() {
		List list = this.find("select a from Curr a where a.code = ? ",
				new Object[] { "502" });
		if (list.size() > 0) {
			return (Curr) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 获得最大的核销单编号
	 * 
	 * @return 最大的核销单号
	 */
	public List getMaxSignInNo() {
		return this.find("select max(a.signInNo) from FecavBillStrike as a "
				+ " where a.company.id=?", new Object[] { CommonUtils
				.getCompany().getId() });
	}

	/**
	 * 根据状态查询外汇核销交单并没有被核销的
	 * 
	 * @return 没有被核销的外汇核销单
	 */
	public List findFecavBillNotCancel() {
		return this.find("select a from FecavBill a where a.company.id= ? "
				+ " and a.fecavBillStrike is not null "
				+ "and a.fecavBillStrike.fecavState=? order by a.emsNo ",
				new Object[] { CommonUtils.getCompany().getId(),
						FecavState.CONTROL });
	}

	/**
	 * 取得外汇核销单的手册号
	 * 
	 * @return 管制状态下的外汇核销单的手册号
	 */
	public List findFecavBillEmsNo() {
		return this
				.find(
						"select distinct a.emsNo from FecavBill a where a.company.id= ? "
								+ " and a.fecavBillStrike is not null and a.emsNo is not null "
								+ "and a.fecavBillStrike.fecavState=? order by a.emsNo ",
						new Object[] { CommonUtils.getCompany().getId(),
								FecavState.CONTROL });
	}

	/**
	 * 根据外汇核销单来自已核销签收(核销,财务签收,关帐)
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内没有遗失作废的外汇核销单
	 */
	public List<FecavBill> findFecavBillByCancel(Date beginDate, Date endDate) {
		String hsql = "select a from FecavBill a where a.company.id= ? "
				+ " and a.fecavBillStrike.fecavState in (?,?,?) "
				+ " and (a.isBlankOut=? or a.isBlankOut is null) ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(FecavState.CANCEL);
		parameters.add(FecavState.FINANCE_SIGN_IN);
		parameters.add(FecavState.CLOSE_ACCOUNT);
		parameters.add(false);
		if (beginDate != null) {
			hsql += " and a.fecavBillStrike.cancelDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.fecavBillStrike.cancelDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " order by a.code asc ";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 根据外汇核销单来自已[交单末核销]的单据
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 控制状态下没有遗失作废的外汇核销单
	 */
	public List<FecavBill> findFecavBillNoCancel(Date beginDate, Date endDate) {
		String hsql = "select a from FecavBill a "
				+ " where a.fecavBillStrike is not null "
				+ " and a.company.id= ? "
				+ " and a.fecavBillStrike.fecavState = ? "
				+ " and (a.isBlankOut=? or a.isBlankOut is null) ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(FecavState.CONTROL);
		parameters.add(false);
		if (beginDate != null) {
			hsql += " and a.fecavBillStrike.cancelDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.fecavBillStrike.cancelDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " order by a.code asc ";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 出口收汇核销单使用状况表(从外汇局领单部分)
	 * 
	 * @param beginDate
	 *            开始日期 外部领用
	 * @param endDate
	 *            截止日期 外部领用
	 * @return 有效期内的外汇核销单
	 */
	public List findOuterObtainFecavForStat(Date beginDate, Date endDate) {
		String hsql = "select a from FecavBill a where a.company.id= ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hsql += " and a.outerObtainDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.outerObtainDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " order by a.code,a.outerObtainDate ";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 出口收汇核销单使用状况表(使用状况)
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 使用状态的出口收汇核销单
	 */
	public List findUsedFecavForStat(Date beginDate, Date endDate) {
		String hsql = "select a from FecavBill a where a.company.id= ? and a.billState=? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(FecavState.USED);
		if (beginDate != null) {
			hsql += " and a.outerObtainDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.outerObtainDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " order by a.code,a.outerObtainDate ";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 出口收汇核销单使用状况表(遗失作废状况)
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 遗失作废的出口收汇核销单
	 */
	public List findBlankOutFecavForStat(Date beginDate, Date endDate) {
		String hsql = "select a from FecavBill a where a.company.id= ? and a.isBlankOut=? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Boolean(true));
		if (beginDate != null) {
			hsql += " and a.outerObtainDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.outerObtainDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " order by a.code,a.outerObtainDate ";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找所有的合同来自正在执行的
	 * 
	 * @return List 是Contract型，合同备案表头
	 */
	public List findContractByProcessExe() {
		return this
				.find(
						"select a from Contract a where a.company= ? and ( a.isCancel=? ) and a.declareState=? ",
						new Object[] { CommonUtils.getCompany(),
								new Boolean(false), DeclareState.PROCESS_EXE });
	}

	/**
	 * 电子手册通关备案表头--执行状态
	 * 
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findDzscEmsPorHeadExcu() {
		return this
				.find(
						"select a from DzscEmsPorHead a where a.company.id= ? and a.declareState = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								DzscState.EXECUTE });
	}

	/**
	 * 核销单登记表
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param emsNo
	 *            手册号
	 * @return List 是DzscCustomsDeclarationCommInfo型，报关单物料资料
	 */
	public List findDzscCancelAfterVerificationList(String emsNo,
			Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a from DzscCustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.impExpFlag=? "
				+ " and a.baseCustomsDeclaration.effective=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(ImpExpFlag.EXPORT);
		parameters.add(new Boolean(true));
		if (emsNo != null && !emsNo.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.emsHeadH2k=? ";
			parameters.add(emsNo);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " order by a.baseCustomsDeclaration.customsDeclarationCode";
		// hsql += " group by
		// a.baseCustomsDeclaration.customsDeclarationCode,a.baseCustomsDeclaration.authorizeFile";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 核销单登记表
	 * 
	 * @param emsNo
	 *            手册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 是BcsCustomsDeclarationCommInfo型，报关单物料
	 */
	public List findBcsCancelAfterVerificationList(String emsNo,
			Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a from BcsCustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.impExpFlag=? "
				+ " and a.baseCustomsDeclaration.effective=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(ImpExpFlag.EXPORT);
		parameters.add(new Boolean(true));
		if (emsNo != null && !emsNo.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.emsHeadH2k=? ";
			parameters.add(emsNo);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " order by a.baseCustomsDeclaration.customsDeclarationCode";
		// hsql += " group by
		// a.baseCustomsDeclaration.customsDeclarationCode,a.baseCustomsDeclaration.authorizeFile";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 核销单登记表
	 * 
	 * @param emsNo
	 *            手册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 是BcsCustomsDeclarationCommInfo型，报关单物料
	 */
	public List findJbcusCancelAfterVerificationList(String emsNo,
			Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a from CustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.impExpFlag=? "
				+ " and a.baseCustomsDeclaration.effective=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(ImpExpFlag.EXPORT);
		parameters.add(new Boolean(true));
		if (emsNo != null && !emsNo.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.emsHeadH2k=? ";
			parameters.add(emsNo);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " order by a.baseCustomsDeclaration.customsDeclarationCode";
		// hsql += " group by
		// a.baseCustomsDeclaration.customsDeclarationCode,a.baseCustomsDeclaration.authorizeFile";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查询通关备案成品分页
	 * 
	 * @param headId
	 *            通关备案通关备案表头Id
	 * @param seqNum
	 *            凭证序号
	 * @return List 是DzscEmsExgBill型，通关备案成品
	 */
	public List findDzscEmsExgBillBySeqNum(String headId, Integer seqNum) {
		return this
				.find(
						"select c from DzscEmsExgBill c where c.dzscEmsPorHead.id=? and c.seqNum=? ",
						new Object[] { headId, seqNum });
	}

	/**
	 * 查询通关备案成品分页
	 * 
	 * @param headId
	 *            通关备案通关备案表头Id
	 * @param seqNum
	 *            凭证序号
	 * @return List 是DzscEmsExgBill型，通关备案成品
	 */
	public List findJbcusEmsExgBillBySeqNum(String headId, String seqNum) {
		return this
				.find(
						"select c from EmsHeadH2kExg c where c.emsHeadH2k.id=? and c.seqNum=? ",
						new Object[] { headId, seqNum });
	}

	/**
	 * 根据帐册编号、成品序号查找执行的合同成品
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @param seqNum
	 *            成品序号
	 * @return List 是ContractExg型，合同成品
	 */
	public List findBcsContractExgBySeqNum(String emsNo, String seqNum) {
		return this
				.find(
						"select a from ContractExg a where a.company.id=? and a.contract.isCancel = ? "
								+ " and a.contract.declareState=? and a.contract.emsNo = ? and a.seqNum = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								new Boolean(false), DeclareState.PROCESS_EXE,
								emsNo, Integer.valueOf(seqNum) });
	}

	/**
	 * 根据帐册编号、成品序号查找执行的合同成品
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @param seqNum
	 *            成品序号
	 * @return List 是ContractExg型，合同成品
	 */
	public List findJbcusContractExgBySeqNum(String emsNo, String seqNum) {
		return this
				.find(
						"select a from   EmsHeadH2kExg a where a.company.id=? "
								+ " and a.emsHeadH2k.declareState=? and a.emsHeadH2k.emsNo = ? and a.seqNum = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								DeclareState.PROCESS_EXE, emsNo,
								Integer.valueOf(seqNum) });
	}

	/**
	 * 查找所有的合同来自正在执行的
	 * 
	 * @return List 是Contract型，合同备案表头
	 */
	public List findEmsHeadH2kByProcessExe() {
		return this
				.find(
						"select a from EmsHeadH2k a where a.company= ? and a.declareState=? ",
						new Object[] { CommonUtils.getCompany(),
								DeclareState.PROCESS_EXE });
	}

	public List findCustomsInofFecavBill() {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from FecavBill a where a.company.id= ? ";
		parameters.add(CommonUtils.getCompany().getId());
		hql += " and  a.impExpCIQ is null ";
		hql += " and a.billState=? ";
		parameters.add(FecavState.INNER_OBTAIN);
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 根据汇票抓取已冲销的汇票
	 * 
	 * @param fecavBillStrike
	 *            外汇核销单
	 * @return 已冲销的汇票
	 */
	public List findStrikeBillOfExchange(BillOfExchange billOfExchange) {
		return this
				.find(
						"select a from StrikeBillOfExchange a where a.billOfExchangeId= ? and a.company.id=? ",
						new Object[] { billOfExchange.getId(),
								CommonUtils.getCompany().getId() });
	}
	/**
	 * 根据外汇核销单的领用类型和是否作废状态获取核销单号最小的外汇核销单
	 * @param request
	 * @param obtain
	 * @param billState
	 * @return
	 */
	public FecavBill getMinFecavBill(Integer billState,Boolean isBlankOut){
		List list = this.find("select a from FecavBill a where a.billState=? and a.isBlankOut=? "+
				"and a.company.id=? order by a.code",
		new Object[]{billState,isBlankOut,
				CommonUtils.getCompany().getId()});
		if (list.size() > 0) {
			return (FecavBill) list.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * 取得出口报关单-lyh
	 * 
	 * @param projectType
	 *            系统模块
	 * @return 
	 */
	public List findNotCodeExpCustomsDeclaration(int projectType) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a  from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = "select a  from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = "select a  from DzscCustomsDeclaration as a";
			break;
		default:
			hql = "select a  from CustomsDeclaration as a";
			break;
		}
		hql += " where a.company.id=?"
				+ " and a.authorizeFile is not null  "
				+ " and a.customsDeclarationCode is not null  "
				+ " and a.effective =? "
				+ " and ( a.impExpType=? or a.impExpType = ?) "
				+ " order by a.declarationDate,a.customsDeclarationCode";
		return this.find(hql, new Object[] {CommonUtils.getCompany().getId(), true,
				ImpExpType.DIRECT_EXPORT, ImpExpType.TRANSFER_FACTORY_EXPORT });
	}
	/**取得出口报关单金额
	 * 
	 * @param projectType
	 *            系统模块
	 * @return 指定模块的进口报关单金额
	 */
	public List findExpCustomsDeclarationMoney(int projectType) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select a.baseCustomsDeclaration.id,sum(a.commTotalPrice) "
					+ " from CustomsDeclarationCommInfo as a ";
			break;
		case ProjectType.BCS:
			hql = " select a.baseCustomsDeclaration.id,sum(a.commTotalPrice) "
					+ " from BcsCustomsDeclarationCommInfo as a";
			break;
		case ProjectType.DZSC:
			hql = " select a.baseCustomsDeclaration.id,sum(a.commTotalPrice) "
					+ " from DzscCustomsDeclarationCommInfo as a";
			break;
		default:
			hql = " select a.baseCustomsDeclaration.id,sum(a.commTotalPrice) "
					+ " from CustomsDeclarationCommInfo as a";
			break;
		}
		hql += " where a.baseCustomsDeclaration.company.id=?"
				+ " and a.baseCustomsDeclaration.impExpFlag=? "
				+ " group by a.baseCustomsDeclaration.id";
		return this.find(hql, new Object[] { CommonUtils.getCompany().getId(),
				Integer.valueOf(ImpExpFlag.EXPORT) });
	}
}
